/*
 * Copyright (C) 2011-2014 eXo Platform SAS.
 *
 * This file is part of eXo Acceptance Webapp.
 *
 * eXo Acceptance Webapp is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 3 of
 * the License, or (at your option) any later version.
 *
 * eXo Acceptance Webapp software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with eXo Acceptance Webapp; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see <http://www.gnu.org/licenses/>.
 */
var app = angular.module('credential', ['ngRoute', 'restangular', 'ui.bootstrap']).
    config(function ($routeProvider, RestangularProvider) {
             $routeProvider.
                 when('/', {
                        controller: ListCtrl,
                        templateUrl: 'list.html'
                      }).
                 when('/edit/:credentialId', {
                        controller: EditCtrl,
                        templateUrl: 'detail.html',
                        resolve: {
                          credential: function (Restangular, $route) {
                            return Restangular.one('credential', $route.current.params.credentialId).get();
                          }
                        }
                      }).
                 when('/new', {controller: CreateCtrl, templateUrl: 'detail.html'}).
                 otherwise({redirectTo: '/'});
             RestangularProvider.setBaseUrl('/api/admin');
           });

app.filter('filterByNameOrType', function () {
  /* array is first argument, each additional argument is prefixed by a ":" in filter markup */
  return function (dataArray, searchTerm) {
    if (!dataArray) return;
    /* when term is cleared, return full array */
    if (!searchTerm) {
      return dataArray
    } else {
      /* otherwise filter the array */
      var term = searchTerm.toLowerCase();
      return dataArray.filter(function (item) {
        return item.name.toLowerCase().indexOf(term) > -1 || item.type.toLowerCase().indexOf(term) > -1;
      });
    }
  }
});

// Controllers
function ListCtrl($scope, Restangular, $log) {
  $scope.alerts = [];
  $scope.closeAlert = closeAlert;
  $scope.credentials = [];
  Restangular.all("credential").getList().then(function (result) {
    // Everything is ok.
    $scope.credentials = result;
  }, function (response) {
    displayErrors($scope, response, $log);
  });
  $scope.showEdit = function (credential) {
    return credential.type !== "NONE"
  }
}

function CreateCtrl($scope, $location, Restangular, $log) {
  $scope.alerts = [];
  $scope.closeAlert = closeAlert;
  $scope.credential = {};
  $scope.credential.type = "PASSWORD";
  $scope.save = function () {
    $scope.credential._jsonType = $scope.credential.type;
    Restangular.all('credential').post($scope.credential).then(function (credential) {
      $location.path('/');
    }, function (response) {
      displayErrors($scope, response, $log);
    });
  }
}

function EditCtrl($scope, $location, Restangular, credential, $log) {
  $scope.alerts = [];
  $scope.closeAlert = closeAlert;
  var original = credential;
  $scope.credential = Restangular.copy(original);

  $scope.isClean = function () {
    return angular.equals(original, $scope.credential);
  };

  $scope.destroy = function () {
    original.remove().then(function () {
      $location.path('/');
    }, function (response) {
      displayErrors($scope, response, $log);
    });
  };

  $scope.save = function () {
    $scope.credential.put().then(function () {
      $location.path('/');
    }, function (response) {
      displayErrors($scope, response, $log);
    });
  };

}
