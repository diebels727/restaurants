function enterDebug() {
  debugger;
};


var app = angular.module('restApp',[])

app.controller('applicationController',['$scope',function($scope) {
    $scope.mapOptions = {
      center: {
         lat: 40.7903,
         lng: -73.9597
      },
      zoom: 10
    };

    $scope.map = new google.maps.Map(document.getElementById('map-canvas'),$scope.mapOptions);

    $scope.markers = [];
    for (var i = 0; i < 10000; i++) {
      var center = $scope.map.getCenter()

      var latLng = new google.maps.LatLng(center.lat() + Math.random(-0.01,0.01),center.lng()+Math.random(-0.01,0.01));
      var marker = new google.maps.Marker({'position': latLng});
      $scope.markers.push(marker);
    }

    $scope.mc = new MarkerClusterer($scope.map,$scope.markers);
    google.maps.event.addDomListener(window, 'load');
}]);


