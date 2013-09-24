#!/bin/sh
#
# Copyright (C) 2011-2013 eXo Platform SAS.
#
# This is free software; you can redistribute it and/or modify it
# under the terms of the GNU Lesser General Public License as
# published by the Free Software Foundation; either version 3 of
# the License, or (at your option) any later version.
#
# This software is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
# Lesser General Public License for more details.
#
# You should have received a copy of the GNU Lesser General Public
# License along with this software; if not, write to the Free
# Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
# 02110-1301 USA, or see the FSF site: http://www.fsf.org.
#

# -----------------------------------------------------------------------------
# Logs customization (Managed by slf4J/logback instead of tomcat-juli & co)
# -----------------------------------------------------------------------------

# Deactivate j.u.l
LOGGING_MANAGER="-Dnop"
# Add additional bootstrap entries for logging purpose using SLF4J+Logback
# SLF4J deps
CLASSPATH="$CLASSPATH":"$CATALINA_HOME/lib/slf4j-api-${slf4j.version}.jar"
CLASSPATH="$CLASSPATH":"$CATALINA_HOME/lib/jcl-over-slf4j-${slf4j.version}.jar"
CLASSPATH="$CLASSPATH":"$CATALINA_HOME/lib/jul-to-slf4j-${slf4j.version}.jar"
CLASSPATH="$CLASSPATH":"$CATALINA_HOME/lib/log4j-over-slf4j-${slf4j.version}.jar"
# LogBack deps
CLASSPATH="$CLASSPATH":"$CATALINA_HOME/lib/logback-core-${logback.version}.jar"
CLASSPATH="$CLASSPATH":"$CATALINA_HOME/lib/logback-classic-${logback.version}.jar"

# -----------------------------------------------------------------------------
# Compute the CATALINA_OPTS
# -----------------------------------------------------------------------------

# Logback configuration file
CATALINA_OPTS="$CATALINA_OPTS -Dlogback.configurationFile=\"$CATALINA_BASE/conf/logback.xml\""

# Acceptance configuration file
CATALINA_OPTS="$CATALINA_OPTS -Dacceptance.configurationFile=\"$CATALINA_BASE/conf/acceptance.properties\""
