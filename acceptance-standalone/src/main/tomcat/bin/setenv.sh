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

# Logback configuration file
CATALINA_OPTS="$CATALINA_OPTS -Dlogback.configurationFile=\"$CATALINA_BASE/conf/logback.xml\""

# Acceptance configuration file
CATALINA_OPTS="$CATALINA_OPTS -Dapplication.configurationFile=\"$CATALINA_BASE/conf/acceptance.properties\""
