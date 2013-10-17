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
    @Binding(value = Context.class, scope = Scope.REQUEST),
    @Binding(ProjectSettings.class)
})

// Declare assets
@Assets({
    @Asset(id = "jquery.js", value = "jquery_1_10_2/jquery.min.js"),
    @Asset(id = "bootstrap.js", value = "bootswatch_spacelab_3_0_0/js/bootstrap.min.js", depends = "jquery.js"),
    @Asset(id = "angular.js", value = "angular_1_1_4/angular.min.js"),
    @Asset(id = "underscore.js", value = "underscore_1_4_4/underscore.min.js"),
    @Asset(id = "restangular.js", value = "restangular_1_1_3/restangular.min.js", depends = {"angular.js", "underscore.js"}),
    @Asset(id = "ui-bootstrap.js", value = "ui_bootstrap_0_6_0/ui-bootstrap-tpls.min.js", depends = {"angular.js", "bootstrap.js"}),
    @Asset(id = "projects-admin.js", value = "administration/project.js", depends = {"restangular.js", "ui-bootstrap.js"}),
    @Asset(id = "bootstrap.css", value = "bootswatch_spacelab_3_0_0/css/bootstrap.min.css"),
    @Asset(id = "acceptance.css", value = "acceptance.css", depends = {"bootstrap.css"}),
    @Asset(id = "signin.css", value = "signin.css")
})

// Always use these assets
@WithAssets({"bootstrap.js", "acceptance.css"})

// Custom tags
@Tags({
    @Tag(name = "userRole", path = "userRole.gtmpl"),
    @Tag(name = "adminRole", path = "adminRole.gtmpl"),
    @Tag(name = "anonymousRole", path = "anonymousRole.gtmpl")
}) package org.exoplatform.acceptance;

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
import org.exoplatform.acceptance.controllers.Home;
import org.exoplatform.acceptance.model.Context;
import org.exoplatform.acceptance.model.Flash;
import org.exoplatform.acceptance.model.ProjectSettings;
import org.exoplatform.acceptance.security.CurrentUser;
