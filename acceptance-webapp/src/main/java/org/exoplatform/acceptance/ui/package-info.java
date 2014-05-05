/*
 * Copyright (C) 2011-2014 eXo Platform SAS.
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
/**
 * Provides eXo acceptance front-end (using Juzu).
 *
 * @since 2.0
 */

// Application

@Application(
    defaultController = HomeController.class,
    name = "AcceptanceApplication")

// This is a webapp
@Servlet("/")

// Injection Bindings
@Bindings(
    {
        @Binding(value = CurrentUser.class, scope = Scope.SESSION),
        @Binding(value = Flash.class, scope = Scope.FLASH),
        @Binding(value = Context.class, scope = Scope.REQUEST)
    })


@WebJars(
    {
        @WebJar(value = "angular-ui-bootstrap",
                stripVersion = true),
        @WebJar(value = "angularjs",
                version = "1.2.16",
                stripVersion = true),
        @WebJar(value = "bootswatch-spacelab",
                stripVersion = true),
        @WebJar(value = "font-awesome",
                stripVersion = true),
        @WebJar(value = "jquery",
                version = "2.1.0",
                stripVersion = true),
        @WebJar(value = "lodash",
                version = "2.4.1",
                stripVersion = true),
        @WebJar(value = "restangular",
                stripVersion = true)
    })

// Declare assets
@Stylesheets(
    {
        @Stylesheet(id = "pace.css",
                    value = "external/pace/0_4_17/pace.css"),
        @Stylesheet(id = "bootstrap.css",
                    value = "bootswatch-spacelab/css/bootstrap.css",
                    minified = "bootswatch-spacelab/css/bootstrap.min.css"),
        @Stylesheet(id = "font-awesome.css",
                    value = "font-awesome/css/font-awesome.css",
                    minified = "font-awesome/css/font-awesome.min.css"),
        @Stylesheet(value = "acceptance.css",
                    depends = {"bootstrap.css", "font-awesome.css"})
    })
@Scripts(
    {
        @Script(id = "pace.js",
                value = "external/pace/0_4_17/pace.min.js"),
        @Script(id = "jquery.js",
                value = "jquery/jquery.js",
                minified = "jquery/jquery.min.js"),
        @Script(id = "angular.js",
                value = "angularjs/angular.js",
                minified = "angularjs/angular.min.js"),
        @Script(id = "angular-route.js",
                value = "angularjs/angular-route.js",
                minified = "angularjs/angular-route.min.js"),
        @Script(id = "lodash.js",
                value = "lodash/lodash.min.js",
                minified = "lodash/lodash.min.js"),
        @Script(id = "bootstrap.js",
                value = "bootswatch-spacelab/js/bootstrap.js",
                minified = "bootswatch-spacelab/js/bootstrap.min.js",
                depends = {"jquery.js"}),
        @Script(id = "restangular.js",
                value = "restangular/restangular.js",
                minified = "restangular/restangular.min.js",
                depends = {"angular.js", "angular-route.js", "lodash.js"}),
        @Script(id = "ui-bootstrap.js",
                value = "angular-ui-bootstrap/ui-bootstrap-tpls.js",
                minified = "angular-ui-bootstrap/ui-bootstrap-tpls.min.js",
                depends = {"angular.js", "bootstrap.js"}),
        @Script(value = "acceptance.js",
                depends = {"bootstrap.js"}),
        @Script(value = "sources.js",
                depends = {"ui-bootstrap.js"}),
        @Script(value = "administration/credential/index.js",
                depends = {"restangular.js", "ui-bootstrap.js"}),
        @Script(value = "administration/vcs/repository.js",
                depends = {"restangular.js", "ui-bootstrap.js"})
    })

// Always use these assets
@Assets({"acceptance.js", "acceptance.css", "pace.js", "pace.css"})

// Custom tags
@Tags({
          @Tag(name = "adminMenu", path = "adminMenu.gtmpl")
      }) package org.exoplatform.acceptance.ui;

import org.exoplatform.acceptance.ui.controllers.HomeController;
import org.exoplatform.acceptance.ui.model.Context;
import org.exoplatform.acceptance.ui.model.CurrentUser;
import org.exoplatform.acceptance.ui.model.Flash;

import juzu.Application;
import juzu.Scope;
import juzu.plugin.asset.Assets;
import juzu.plugin.asset.Script;
import juzu.plugin.asset.Scripts;
import juzu.plugin.asset.Stylesheet;
import juzu.plugin.asset.Stylesheets;
import juzu.plugin.binding.Binding;
import juzu.plugin.binding.Bindings;
import juzu.plugin.servlet.Servlet;
import juzu.plugin.webjars.WebJar;
import juzu.plugin.webjars.WebJars;
import juzu.template.Tag;
import juzu.template.Tags;
