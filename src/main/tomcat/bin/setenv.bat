@REM
@REM Copyright (C) 2003-2013 eXo Platform SAS.
@REM
@REM This is free software; you can redistribute it and/or modify it
@REM under the terms of the GNU Lesser General Public License as
@REM published by the Free Software Foundation; either version 3 of
@REM the License, or (at your option) any later version.
@REM
@REM This software is distributed in the hope that it will be useful,
@REM but WITHOUT ANY WARRANTY; without even the implied warranty of
@REM MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
@REM Lesser General Public License for more details.
@REM
@REM You should have received a copy of the GNU Lesser General Public
@REM License along with this software; if not, write to the Free
@REM Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
@REM 02110-1301 USA, or see the FSF site: http://www.fsf.org.
@REM

@ECHO off

REM # ---------------------------------------------------------------------------
REM # Logs customization (Managed by slf4J\logback instead of tomcat-juli & co)
REM # ---------------------------------------------------------------------------

REM # Deactivate j.u.l
SET LOGGING_MANAGER=-Dnop
REM # Add additional bootstrap entries for logging purpose using SLF4J+Logback
REM # SLF4J deps
SET CLASSPATH=%CLASSPATH%;%CATALINA_HOME%\lib\slf4j-api-${slf4j.version}.jar
SET CLASSPATH=%CLASSPATH%;%CATALINA_HOME%\lib\jul-to-slf4j-${slf4j.version}.jar
REM # LogBack deps
SET CLASSPATH=%CLASSPATH%;%CATALINA_HOME%\lib\logback-core-${logback.version}.jar
SET CLASSPATH=%CLASSPATH%;%CATALINA_HOME%\lib\logback-classic-${logback.version}.jar
REM # Jansi deps for colorized output on windows
SET CLASSPATH=%CLASSPATH%;%CATALINA_HOME%\lib\jansi-${jansi.version}.jar

REM # ---------------------------------------------------------------------------
REM # Compute the CATALINA_OPTS
REM # ---------------------------------------------------------------------------

REM # Logback configuration file
SET CATALINA_OPTS=%CATALINA_OPTS% -Dlogback.configurationFile="%CATALINA_BASE%\conf\logback.xml"

REM # Acceptance configuration file
SET CATALINA_OPTS=%CATALINA_OPTS% -Dacceptance.configurationFile="%CATALINA_BASE%\conf\acceptance.properties"

REM # Set the window name
SET TITLE=eXo Acceptance ${project.version}

:end
