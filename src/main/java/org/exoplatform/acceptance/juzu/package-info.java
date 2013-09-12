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
@Application(defaultController = Home.class)

@Bindings(value = {
    @Binding(User.class),
    @Binding(Flash.class)
})

@Servlet("/")

@Assets(scripts = {
    @Script(id = "jquery", src = "//code.jquery.com/jquery-1.10.2.min.js", location = AssetLocation.URL),
    @Script(id = "backbone", src = "//cdnjs.cloudflare.com/ajax/libs/backbone.js/1.0.0/backbone-min.js", location = AssetLocation.URL, depends = "underscore"),
    @Script(id = "underscore", src = "//cdnjs.cloudflare.com/ajax/libs/underscore.js/1.5.2/underscore-min.js", location = AssetLocation.URL),
    @Script(id = "bootstrap", src = "//netdna.bootstrapcdn.com/bootstrap/3.0.0/js/bootstrap.min.js", location = AssetLocation.URL, depends = "jquery"),
    @Script(id = "acceptance", src = "acceptance.js", depends = {"jquery"}),
}, stylesheets = {
    @Stylesheet(src = "acceptance.css"),
    @Stylesheet(src = "//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css", location = AssetLocation.URL),
    @Stylesheet(src = "//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap-theme.min.css", location = AssetLocation.URL)
}, declaredStylesheets = {
    @Stylesheet(id = "signin", src = "signin.css")
}) package org.exoplatform.acceptance.juzu;

import juzu.Application;
import juzu.asset.AssetLocation;
import juzu.plugin.asset.Assets;
import juzu.plugin.asset.Script;
import juzu.plugin.asset.Stylesheet;
import juzu.plugin.binding.Binding;
import juzu.plugin.binding.Bindings;
import juzu.plugin.servlet.Servlet;
import org.exoplatform.acceptance.juzu.controllers.Home;
import org.exoplatform.acceptance.juzu.model.Flash;
import org.exoplatform.acceptance.juzu.model.User;