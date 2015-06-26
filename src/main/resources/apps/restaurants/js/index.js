/** This method will be called when JQuery is ready for use */
$(function() {


  // Listen for the koverse JS to be ready
  Koverse.addEventListener(KoverseApplication.EVENT_READY, function() {

    // Example koverse call - retrieve data collections
    Koverse.getDataCollections(function(response){

      // Check that the response was successful
      if(!response.success) {
        $(document.body).append("<span class='error'>Error retrieving data collections: "  + response.failureMessage + "</span>");
        return;
      }

      // sort the collections
      var dataCollectionsSorted = response.dataCollections.sortBy("name");

      // Loop over the data collections in response
      $.each(dataCollectionsSorted, function(index, dataCollection){

        // Append to list of data collections
        $("#dataCollectionList").append("<li><a href='" + KoverseUtil.getDataCollectionURL(dataCollection.id) + "'>" + dataCollection.name + "</a></li>");

      });

    });

  });

});


var app = angular.module('restApp',['uiGmapgoogle-maps']);

app.controller('mapController',['$scope',function($scope) {
  $scope.markers = [];

  $scope.enterDebug = function() {
    debugger;
  }

  $scope.loadRestaurantLocations = function(numberOfRestaurants) {
    Koverse.getDataCollectionByName('geocoded-restaurants',function(dataCollectionResponse) {
      $scope.dataCollection = dataCollectionResponse.dataCollection;
      Koverse.performQuery("{$any: *}",[$scope.dataCollection.id],function(response) {
        if ( !response.success) { debugger };
        $scope.markersPrevLength = $scope.markers.length;
        $scope.records = response.records;
        $scope.markers = []
        $scope.markers = _.map($scope.records,function(record) {
          var fields = record.fields;
          var record = { id: fields.CAMIS, coords: { longitude: fields.Longitude,latitude: fields.Latitude} };
          return record;
        });
      },numberOfRestaurants,0,false);
    });
  }


  $scope.map = {
    control: {},
    center: {
      latitude: 40.7903,
      longitude: -73.9597
      },
    zoom: 10,
    refresh: function() {
      return $scope.markers.length != $scope.markersPrevLength;
    }
  };

}]);

app.controller('markerController',['$scope',function($scope) {

}]);