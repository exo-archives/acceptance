acceptance
==========

eXo Acceptance System

## How to configure

You need to add the crowd application password to test and use eXo Application

In your maven configuration file ```~.m2/settings.xmml``` add something like :

    <?xml version="1.0" encoding="UTF-8"?>
    <settings xmlns="http://maven.apache.org/SETTINGS/1.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0
                          http://maven.apache.org/xsd/settings-1.0.0.xsd">
      <profiles>
        <profile>
          <id>acceptance-settings</id>
          <properties>
            <acceptance.dev.crowd.application.password>XXXXXX</acceptance.dev.crowd.application.password>
          </properties>
        </profile>
      </profiles>
      <activeProfiles>
        <activeProfile>acceptance-settings</activeProfile>
      </activeProfiles>
    </settings>

## How to build

```mvn clean install```

## How to run

```mvn clean tomcat7:run```

and then go to http://localhost:8080/