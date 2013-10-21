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
             RestangularProvider.setBaseUrl('/api/admin');
           });

function ListCtrl($scope, Restangular) {
  $scope.projects = Restangular.all("project").getList();
  $scope.query = '';
  $scope.search = function (item) {
    if (item.name.indexOf($scope.query) != -1 || item.description.indexOf($scope.query) != -1) {
      return true;
    }
    return false;
  };
}

function CreateCtrl($scope, $location, Restangular, $log) {
  $scope.save = function () {
    Restangular.all('project').post($scope.project).then(function (project) {
      $location.path('/');
    }, function (response) {
      $log.error("Error while creating", response);
    });
  }
}

function EditCtrl($scope, $location, Restangular, project, $log) {
  var original = project;
  $scope.project = Restangular.copy(original);

  $scope.isClean = function () {
    return angular.equals(original, $scope.project);
  };

  $scope.destroy = function () {
    original.remove().then(function () {
      $location.path('/');
    }, function (response) {
      $log.error("Error while deleting", response);
    });
  };

  $scope.save = function () {
    $scope.project.put().then(function () {
      $location.path('/');
    }, function (response) {
      $log.error("Error while saving", response);
    });
  };
}
