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
// Our Application module
var sourcesApp = angular.module('sourcesApp', ['ui.bootstrap']).
    config(
    function ($locationProvider) {
      //Let's configure the location provider (use html5 if possible, otherwise hash with bang character
      $locationProvider.html5Mode(true).hashPrefix('!');
    });

// Let's add a controller
sourcesApp.controller(
    'SourcesCtrl',
    function SourcesCtrl($scope, $timeout, $location, $anchorScroll) {
      // Let's fill a first time our data
      $scope.branches = computeData();
      // Create a function to recursively updateData and wait for a timeout (1min)
      var updateData = function () {
        $timeout(function () {
          $scope.branches = computeData();
          updateData();
        }, 60000);
      };
      // Function used to highlight and scroll to the row on which the user clicked or
      // which is defined by the URL hash
      $scope.highlight = function (event, id) {
        // We get the element (a TD on which we click)
        var elem = angular.element(event.srcElement);
        // We add the highlight class on its parent tr and we remove it for its siblings
        $(elem).parent("tr").addClass('success').siblings().removeClass('success');
        $location.hash(id);
        $anchorScroll();
      };
      // Function to check if a branch is the current one passed as hash in the URL (and thus must be highlighted
      $scope.highlighted = function (branch) {
        return $location.hash() === branch;
      };
      // Let's launch the background update of data
      updateData();
    });

// This function is used to compute data to display in the page
var computeData = function () {
  var branches = [];
  var randNumMin = 0;
  var randNumMax = 100;

  for (var i = 0; i < 20; i++) {
    var projects = [];
    for (var j = 0; j < 10; j++) {
      projects[j] = {
        'name': 'project' + j,
        'branch': 'branch' + i,
        'ref': 'master',
        'behind': (Math.floor(Math.random() * (randNumMax - randNumMin + 1)) + randNumMin),
        'ahead': (Math.floor(Math.random() * (randNumMax - randNumMin + 1)) + randNumMin),
        'url': 'https://github.com/exodev/project' + j + '/tree/branch' + i
      };
    }
    branches[i] = {'name': 'branch' + i, 'projects': projects};
  }
  return branches;
};