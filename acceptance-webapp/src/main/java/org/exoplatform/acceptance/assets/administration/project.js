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
angular.module('project', ['restangular', 'ui.bootstrap']).
    config(function ($routeProvider, RestangularProvider) {
             $routeProvider.
                 when('/', {
                        controller: ListCtrl,
                        templateUrl: 'list.html'
                      }).
                 when('/edit/:projectId', {
                        controller: EditCtrl,
                        templateUrl: 'detail.html',
                        resolve: {
                          project: function (Restangular, $route) {
                            return Restangular.one('project', $route.current.params.projectId).get();
                          }
                        }
                      }).
                 when('/new', {controller: CreateCtrl, templateUrl: 'detail.html'}).
                 otherwise({redirectTo: '/'});
             RestangularProvider.setBaseUrl('/rest');
             // Now let's configure the response extractor for each request
             RestangularProvider.setResponseExtractor(function (response, operation, what, url) {
               var newResponse;
               // This is a get for a list
               if (operation === "getList") {
                 // We'll get the list from the content attribute due to paging informations in the result
                 newResponse = response.content;
                 newResponse._meta = {};
                 newResponse._meta["totalElements"] = response.totalElements;
               } else {
                 // otherwise we return the result
                 newResponse = response;
               }
               return newResponse;
             });
           });

function ListCtrl($scope, Restangular) {
  $scope.projects = Restangular.all("project").getList();
  // This is a promise of a number value. You can show it in the UI
  var metaPromise = $scope.projects.get("_meta");
  metaPromise.then(function (meta) {
    // Here the length is the real length value of the returned collection of projects
    $scope.projectsLength = meta["totalElements"];
  });
}

function CreateCtrl($scope, $location, Restangular) {
  $scope.save = function () {
    Restangular.all('project').post($scope.project).then(function (project) {
      $location.path('/');
    });
  }
}

function EditCtrl($scope, $location, Restangular, project) {
  var original = project;
  $scope.project = Restangular.copy(original);


  $scope.isClean = function () {
    return angular.equals(original, $scope.project);
  }

  $scope.destroy = function () {
    original.remove().then(function () {
      $location.path('/');
    });
  };

  $scope.save = function () {
    $scope.project.put().then(function () {
      $location.path('/');
    });
  };
}
