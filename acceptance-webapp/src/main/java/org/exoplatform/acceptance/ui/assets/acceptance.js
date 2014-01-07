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
// Errors Mgt
function displayErrors($scope, $response, $log) {
  if (typeof $scope.alerts != "undefined") {
    // Cleanup existing errors
    $scope.alerts.length = 0;
  }
  if (typeof $response.data.globalErrors != "undefined") {
    // Add Global errors
    $.each($response.data.globalErrors, function (index, value) {
      $scope.alerts.push({type: 'danger', context: value.objectName, msg: value.message});
    });
  }
  if (typeof $response.data.fieldErrors != "undefined") {
    // Add Field specific errors
    $.each($response.data.fieldErrors, function (index, value) {
      $scope.alerts.push({type: 'danger', context: value.field, msg: value.message});
    });
  }
  if ($scope.alerts.length == 0) {
    // We weren't able to extract some detailed errors. Let's add a generic error message.
    $scope.alerts.push({type: 'danger', context: 'Error', msg: 'Remote operation failed with status code ' + $response.status});
  }
}

function closeAlert(index) {
  $scope.alerts.splice(index, 1);
}