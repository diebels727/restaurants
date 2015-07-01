var app = angular.module('restApp',[]);

app.controller('restAppController',['$scope',function($scope) {
  $scope.map = { center: { latitude: 45, longitude: -73 }, zoom: 8 };
}]);