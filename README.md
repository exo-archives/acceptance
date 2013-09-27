eXo Acceptance
==============

## DESCRIPTION

eXo Acceptance allows to manage a set of automated deployments.

TBD

## LICENSE

[LGPLv3](http://www.gnu.org/licenses/lgpl.html)

## SYSTEM REQUIREMENTS

- Java 7+ (Build & Run)
- Apache Maven 3.0.4+ (Build)
- [Atlassian Crowd](https://www.atlassian.com/software/crowd/) 2.6+ (Run)
- [MongoDB](http://www.mongodb.org/) 2.4+ (Run)

## QUICKSTART

    git clone git@github.com:exoplatform/acceptance.git && mvn -f acceptance -pl acceptance-webapp -am tomcat7:run

And then go to http://localhost:8080

## SOURCES

The project is composed of 3 modules :

- acceptance-agent : The agent used to manage an acceptance node
- acceptance-webapp : The acceptance server (war) and its front-end
- acceptance-standalone : A Tomcat 7.x bundle embedding the acceptance web application and its configuration (see : ```${catalina.home}/conf/acceptance.properties```

## BUILD (AND AUTOMATED TESTS)

To build the project you requires Apache Maven 3.0.4 and Java 7, and then you launch

    mvn verify

You can additionally activate the execution of integration tests (which are using [Arquillian](http://arquillian.org/) ) with

    mvn verify -Prun-its

To deactivate all automated tests

    mvn verify -DskipTests

## MANUAL TESTS

### Development mode

From the directory ```acceptance-webapp``` you can start the front-end with a development profile (fake authentication with users user/user or admin/admin, embedded MongoDB, Juzu in development mode ...) with

    mvn tomcat7:run

### Production mode

You may also want to test the application in its ```production``` mode, in that case you'll need some addition pre-requisites :

- a [MongoDB](http://www.mongodb.org/) instance
- an [Atlassian Crowd](https://www.atlassian.com/software/crowd/) instance with :
  - An [application](https://confluence.atlassian.com/display/CROWD/Adding+an+Application) to let acceptance logon on it
  - Two groups of users : ```acceptance-users``` and ```acceptance-administrators```

In your maven configuration file ```~.m2/settings.xml``` you may have to define some of these properties to adapt them to your environment :

    <?xml version="1.0" encoding="UTF-8"?>
    <settings xmlns="http://maven.apache.org/SETTINGS/1.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0
                          http://maven.apache.org/xsd/settings-1.0.0.xsd">
      <profiles>
        <profile>
          <id>acceptance-settings</id>
          <properties>
            <!-- The URL to use when connecting with the integration libraries to communicate with the Crowd server. ex : http(s)://your_crowd_server_host:your_crowd_server_port/services/ -->
            <acceptance.dev.crowd.server.url>https://identity.exoplatform.org/services/</acceptance.dev.crowd.server.url>
            <!-- The name that the application will use when authenticating with the Crowd server. -->
            <acceptance.dev.crowd.application.name>acceptanceng.exoplatform.org</acceptance.dev.crowd.application.name>
            <!-- The password that the application will use when authenticating with the Crowd server. -->
            <acceptance.dev.crowd.application.password>TO_BE_DEFINED</acceptance.dev.crowd.application.password>
            <!-- The MongoDB hostname -->
            <acceptance.dev.mongodb.host>localhost</acceptance.dev.mongodb.host>
            <!-- The MongoDB port -->
            <acceptance.dev.mongodb.port>27017</acceptance.dev.mongodb.port>
            <!-- The MongoDB instance name -->
            <acceptance.dev.mongodb.name>acceptance-dev</acceptance.dev.mongodb.name>
            <!-- The MongoDB username -->
            <acceptance.dev.mongodb.username/>
            <!-- The MongoDB password -->
            <acceptance.dev.mongodb.password/>
          </properties>
        </profile>
      </profiles>
      <activeProfiles>
        <activeProfile>acceptance-settings</activeProfile>
      </activeProfiles>
    </settings>

Then from the directory ```acceptance-standalone``` you can start the front-end with a production profile with

    mvn cargo:run


