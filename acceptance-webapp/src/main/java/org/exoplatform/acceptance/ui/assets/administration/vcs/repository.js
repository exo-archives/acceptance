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
var app = angular.module('repository', ['ngRoute', 'restangular', 'ui.bootstrap']).
    config(function ($routeProvider, RestangularProvider) {
             $routeProvider.
                 when('/', {
                        controller: ListCtrl,
                        templateUrl: 'list.html'
                      }).
                 when('/edit/:repositoryId', {
                        controller: EditCtrl,
                        templateUrl: 'detail.html',
                        resolve: {
                          repository: function (Restangular, $route) {
                            return Restangular.one('repository', $route.current.params.repositoryId).get();
                          }
                        }
                      }).
                 when('/new', {controller: CreateCtrl, templateUrl: 'detail.html'}).
                 otherwise({redirectTo: '/'});
             RestangularProvider.setBaseUrl('/api/admin/vcs');
           });

var loadCredentialsList = function ($http, $scope) {
  $http.get('/api/admin/credential').
      success(function (data, status, headers, config) {
                $scope.credentials = data;
              }).
      error(function (data, status, headers, config) {
              // called asynchronously if an error occurs
              // or server returns response with an error status.
            });
};

// Controllers
function ListCtrl($scope, Restangular, $log) {
  $scope.alerts = [];
  $scope.closeAlert = closeAlert;
  $scope.repositories = [];
  Restangular.all("repository").getList().then(function (result) {
    // Everything is ok.
    $scope.repositories = result;
  }, function (response) {
    displayErrors($scope, response, $log);
  });
}

function CreateCtrl($scope, $location, Restangular, $log, $http) {
  $scope.alerts = [];
  $scope.closeAlert = closeAlert;
  $scope.repository = {};
  $scope.repository.type = "GIT";
  $scope.addRemote = function () {
    if (typeof $scope.repository.remoteRepositories == "undefined") {
      // Create an empty array
      $scope.repository.remoteRepositories = [];
    }
    $scope.repository.remoteRepositories.push({});
  };
  $scope.removeRemote = function ($index) {
    $scope.repository.remoteRepositories.splice($index, 1);
  };
  $scope.noRemote = function () {
    return typeof $scope.repository.remoteRepositories === "undefined" || $scope.repository.remoteRepositories.length == 0;
  };
  loadCredentialsList($http, $scope);

  $scope.save = function () {
    Restangular.all('repository').post($scope.repository).then(function (repository) {
      $location.path('/');
    }, function (response) {
      displayErrors($scope, response, $log);
    });
  }
}

function EditCtrl($scope, $location, Restangular, repository, $log, $http) {
  $scope.alerts = [];
  $scope.closeAlert = closeAlert;
  var original = repository;
  $scope.repository = Restangular.copy(original);
  $scope.addRemote = function () {
    if (typeof $scope.repository.remoteRepositories == "undefined") {
      // Create an empty array
      $scope.repository.remoteRepositories = [];
    }
    $scope.repository.remoteRepositories.push({});
  };
  $scope.removeRemote = function ($index) {
    $scope.repository.remoteRepositories.splice($index, 1);
  };
  $scope.noRemote = function () {
    return typeof $scope.repository.remoteRepositories === "undefined" || $scope.repository.remoteRepositories.length == 0;
  };
  loadCredentialsList($http, $scope);

  $scope.isClean = function () {
    return angular.equals(original, $scope.repository);
  };


  $scope.destroy = function () {
    original.remove().then(function () {
      $location.path('/');
    }, function (response) {
      displayErrors($scope, response, $log);
    });
  };

  $scope.save = function () {
    $scope.repository.put().then(function () {
      $location.path('/');
    }, function (response) {
      displayErrors($scope, response, $log);
    });
  };

}
