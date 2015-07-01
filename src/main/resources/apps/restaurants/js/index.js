

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
    $scope.koverseIsReady = KoverseApplication.EVENT_READY;
    $scope.dataCollection = null;

    // Koverse.addEventListener($scope.koverseIsReady, function() {
      // console.log("[Koverse] Ready1.");
      // Koverse.getDataCollectionByName('violators',function(dataCollectionResponse) {
      //   $scope.dataCollection = dataCollectionResponse.dataCollection;
      // });
    // });

    Koverse.addEventListener($scope.koverseIsReady, function() {
      console.log("[Koverse] Ready2.");

      Koverse.getDataCollectionByName('georestaurants',function(dataCollectionResponse) {
        $scope.dataCollection = dataCollectionResponse.dataCollection;
        Koverse.performQuery("{$any: *}",[$scope.dataCollection.id],function(response) {
          $scope.records = response.records;
          _.each($scope.records,function(record) {
            var fields = record.fields;
            var latLng = new google.maps.LatLng(fields.Latitude,fields.Longitude)
            var marker = new google.maps.Marker({
              'position': latLng
            });
            console.log("[Koverse] Will push marker.");
            $scope.markers.push(marker);
          });
          $scope.mc = new MarkerClusterer($scope.map,$scope.markers);
        },200,0,false);
      });



    });

    Koverse.addEventListener($scope.koverseIsReady, function() {
      console.log("[Koverse] Ready3.");
      $scope.mc = new MarkerClusterer($scope.map,$scope.markers);
    });

    $scope.mc = new MarkerClusterer($scope.map,$scope.markers);
    google.maps.event.addDomListener(window, 'load');

    $scope.debug = function() {
      debugger;
    };
}]);


