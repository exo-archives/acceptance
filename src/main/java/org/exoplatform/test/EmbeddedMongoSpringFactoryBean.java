/*
 * Copyright (C) 2003-2013 eXo Platform SAS.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 3 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.exoplatform.test;

import java.io.IOException;
import java.util.logging.Level;


import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import de.flapdoodle.embed.mongo.Command;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.ArtifactStoreBuilder;
import de.flapdoodle.embed.mongo.config.DownloadConfigBuilder;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.config.RuntimeConfigBuilder;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.config.IRuntimeConfig;
import de.flapdoodle.embed.process.config.io.ProcessOutput;
import de.flapdoodle.embed.process.distribution.GenericVersion;
import de.flapdoodle.embed.process.distribution.IVersion;
import de.flapdoodle.embed.process.io.Processors;
import de.flapdoodle.embed.process.io.progress.LoggingProgressListener;
import de.flapdoodle.embed.process.runtime.Network;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.support.PersistenceExceptionTranslator;
import org.springframework.data.mongodb.core.MongoExceptionTranslator;
import org.springframework.util.Assert;

/**
 * {@linkplain org.springframework.beans.factory.FactoryBean} for EmbedMongo that runs MongoDB as managed process
 * and exposes preconfigured instance of {@link com.mongodb.Mongo}.
 * <p/>
 * <p/>
 * <p>This is not truly embedded Mongo as there's no Java implementation of the
 * MongoDB. EmbedMongo actually downloads original MongoDB binary for your
 * platform and executes it. EmbedMongo process is stopped automatically when
 * you close connection with {@link com.mongodb.Mongo}.</p>
 */
public class EmbeddedMongoSpringFactoryBean implements FactoryBean<Mongo>, InitializingBean, DisposableBean, PersistenceExceptionTranslator {

  private static final Logger LOG = LoggerFactory.getLogger(EmbeddedMongoSpringFactoryBean.class);

  private Mongo mongo;
  private MongodProcess mongod;

  private String version;
  private Integer port;
  private String host = "localhost";

  private PersistenceExceptionTranslator exceptionTranslator = new MongoExceptionTranslator();

  /**
   * The port MongoDB should run on. When no port is provided, then some free
   * server port is automatically assigned.
   *
   * @param port
   */
  public void setPort(int port) {
    Assert.isTrue(port > 0 && port <= 65535, "Port number must be between 0 and 65535");
    this.port = port;
  }

  public int getPort() {
    if (port == null) {
      try {
        port = Network.getFreeServerPort();
      } catch (IOException ex) {
        LOG.error("Could not get free server port");
      }
    }
    return port;
  }

  /**
   * An IP address for the MongoDB instance to be bound to during its execution.
   * Default is localhost.
   *
   * @param host IPv4 or IPv6 address
   */
  public void setHost(String host) {
    this.host = host;
  }

  public String getHost() {
    return host;
  }


  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  private IVersion parseVersion(String version) {
    if (version == null) {
      return Version.Main.PRODUCTION;
    }

    String versionEnumName = version.toUpperCase().replaceAll("\\.", "_");
    if (version.charAt(0) != 'V') {
      versionEnumName = "V" + versionEnumName;
    }
    try {
      return Version.Main.valueOf(versionEnumName);
    } catch (IllegalArgumentException ex) {
      LOG.warn("Unrecognised MongoDB version '{}', this might be a new version that we don't yet know about. " +
                   "Attempting download anyway...", version);
      return new GenericVersion(version);
    }
  }

  public Mongo getObject() throws Exception {
    return this.mongo;
  }

  /*
   * (non-Javadoc)
   * @see org.springframework.beans.factory.FactoryBean#getObjectType()
   */
  public Class<? extends Mongo> getObjectType() {
    return Mongo.class;
  }

  /*
   * (non-Javadoc)
   * @see org.springframework.beans.factory.FactoryBean#isSingleton()
   */
  public boolean isSingleton() {
    return true;
  }

  /*
   * (non-Javadoc)
   * @see org.springframework.dao.support.PersistenceExceptionTranslator#translateExceptionIfPossible(java.lang.RuntimeException)
   */
  public DataAccessException translateExceptionIfPossible(RuntimeException ex) {
    return exceptionTranslator.translateExceptionIfPossible(ex);
  }

  /*
   * (non-Javadoc)
   * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
   */
  @Override
  public void afterPropertiesSet() throws Exception {
    LOG.info("Initializing embedded MongoDB instance (" + parseVersion(getVersion()) + ")");

    IMongodConfig mongodConfig = new MongodConfigBuilder()
        .version(parseVersion(getVersion()))
        .net(new Net(getPort(), Network.localhostIsIPv6()))
        .build();

    // We configure Embed Mongo to use the java standard logging system
    // (Embedmogo require java.util logging system and no other framework)
    final java.util.logging.Logger MONGO_LOGGER = java.util.logging.Logger.getLogger("mongod.process");

    ProcessOutput processOutput = new ProcessOutput(Processors.logTo(MONGO_LOGGER, Level.FINE),
                                                    Processors.logTo(MONGO_LOGGER, Level.SEVERE),
                                                    Processors.named("[console>]", Processors.logTo(MONGO_LOGGER, Level.FINE)));


    IRuntimeConfig runtimeConfig = new RuntimeConfigBuilder()
        .defaultsWithLogger(Command.MongoD, MONGO_LOGGER)
        .processOutput(processOutput)
        .artifactStore(new ArtifactStoreBuilder()
                           .defaults(Command.MongoD)
                           .download(new DownloadConfigBuilder()
                                         .defaultsForCommand(Command.MongoD)
                                         .progressListener(new LoggingProgressListener(MONGO_LOGGER, Level.INFO))))
        .build();

    MongodStarter runtime = MongodStarter.getInstance(runtimeConfig);
    MongodExecutable mongodExecutable = runtime.prepare(mongodConfig);
    LOG.info("Starting embedded MongoDB instance");
    this.mongod = mongodExecutable.start();

    this.mongo = new MongoClient(getHost(), port);
  }

  /*
  * (non-Javadoc)
  * @see org.springframework.beans.factory.DisposableBean#destroy()
  */
  public void destroy() throws Exception {
    this.mongo.close();
    this.mongod.stop();

  }
}