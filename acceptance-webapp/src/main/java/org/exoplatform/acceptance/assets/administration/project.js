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
