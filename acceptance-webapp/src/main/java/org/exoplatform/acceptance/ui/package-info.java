/*
 * Copyright (C) 2011-2013 eXo Platform SAS.
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
    defaultController = Home.class,
    name = "AcceptanceApplication")

// This is a webapp
@Servlet("/")

// Injection Bindings
@Bindings({
              @Binding(value = CurrentUser.class, scope = Scope.SESSION),
              @Binding(value = Flash.class, scope = Scope.FLASH),
              @Binding(value = Context.class, scope = Scope.REQUEST)
          })

// Declare assets
@Assets(
    {
        @Asset(
            id = "jquery.js",
            value = "jquery_1_10_2/jquery.min.js"),
        @Asset(
            id = "bootstrap.js", value = "bootswatch_spacelab_3_0_0/js/bootstrap.min.js",
            depends = "jquery.js"),
        @Asset(
            id = "angular.js", value = "angular_1_1_5/angular.min.js"),
        @Asset(
            id = "underscore.js", value = "underscore_1_5_2/underscore.min.js"),
        @Asset(
            id = "restangular.js", value = "restangular_1_1_6/restangular.min.js",
            depends = {"angular.js", "underscore.js"}),
        @Asset(
            id = "ui-bootstrap.js", value = "ui_bootstrap_0_6_0/ui-bootstrap-tpls.min.js",
            depends = {"angular.js", "bootstrap.js"}),
        @Asset(
            id = "acceptance.js", value = "acceptance.js"),
        @Asset(
            id = "credential-admin.js", value = "administration/credential/index.js",
            depends = {"restangular.js", "ui-bootstrap.js"}),
        @Asset(
            id = "vcsRepository-admin.js", value = "administration/vcs/repository.js",
            depends = {"restangular.js", "ui-bootstrap.js"}),
        @Asset(
            id = "sources.js", value = "sources.js",
            depends = {"angular.js", "ui-bootstrap.js"}),
        @Asset(
            id = "bootstrap.css", value = "bootswatch_spacelab_3_0_0/css/bootstrap.min.css"),
        @Asset(
            id = "font-awesome.css", value = "font_awesome_4_0_0/css/font-awesome.min.css"),
        @Asset(
            id = "acceptance.css", value = "acceptance.css",
            depends = {"bootstrap.css", "font-awesome.css"})

    })

// Always use these assets
@WithAssets({"bootstrap.js", "acceptance.js", "acceptance.css"})

// Custom tags
@Tags({
          @Tag(name = "adminMenu", path = "adminMenu.gtmpl")
      }) package org.exoplatform.acceptance.ui;

import org.exoplatform.acceptance.ui.controllers.Home;
import org.exoplatform.acceptance.ui.model.Context;
import org.exoplatform.acceptance.ui.model.CurrentUser;
import org.exoplatform.acceptance.ui.model.Flash;

import juzu.Application;
import juzu.Scope;
import juzu.plugin.asset.Asset;
import juzu.plugin.asset.Assets;
import juzu.plugin.asset.WithAssets;
import juzu.plugin.binding.Binding;
import juzu.plugin.binding.Bindings;
import juzu.plugin.servlet.Servlet;
import juzu.template.Tag;
import juzu.template.Tags;
