@REM
@REM Copyright (C) 2011-2014 eXo Platform SAS.
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

REM # Logback configuration file
SET CATALINA_OPTS=%CATALINA_OPTS% -Dlogback.configurationFile="%CATALINA_BASE%\conf\logback.xml"

REM # Acceptance configuration file
SET CATALINA_OPTS=%CATALINA_OPTS% -Dapplication.configurationFile="%CATALINA_BASE%\conf\acceptance.properties"

REM # Set the window name
SET TITLE=eXo Acceptance ${project.version}

:end
