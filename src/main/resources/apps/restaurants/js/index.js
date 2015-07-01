

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
    $scope.onMarkerClick = function(pq) {
      console.log("[Koverse] Will click.");
      var title = this.title;

      Koverse.getDataCollectionByName('violators',function(violatorsResponse) {
        var violatorsCollection = violatorsResponse.dataCollection;
        console.log("[Koverse] violatorsCollection.id: " + violatorsCollection.id);
        var query = "group: "+title;
        console.log("[Koverse] query: "+query);

        Koverse.performQuery(query,[violatorsCollection.id], function(response) {
          $scope.violatorResponse = response;
          $scope.violatorRecords = response.records.pop();
          $scope.violations = $scope.violatorRecords.fields.restaurants;
          $scope.$apply(function() {
            $scope.v2 = $scope.violations;
          });
        },20,0,false);

      });

      console.log("[Koverse] Did click.");
    };

    Koverse.addEventListener($scope.koverseIsReady, function() {
      console.log("[Koverse] Ready2.");

      Koverse.getDataCollectionByName('georestaurants',function(dataCollectionResponse) {
        $scope.dataCollection = dataCollectionResponse.dataCollection;
        Koverse.performQuery("{$any: *}",[$scope.dataCollection.id],function(response) {
          $scope.records = response.records;
          _.each($scope.records,function(record) {
            var fields = record.fields;
            var camis = fields.CAMIS.toString();
            var latLng = new google.maps.LatLng(fields.Latitude,fields.Longitude)
            var marker = new google.maps.Marker({
              position: latLng,
              title: camis
            });

            google.maps.event.addListener(marker, 'click',$scope.onMarkerClick);

            $scope.markers.push(marker);
          });
          $scope.mc = new MarkerClusterer($scope.map,$scope.markers);
        },500,0,false);
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


