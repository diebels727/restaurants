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
  $scope.markers = [
    {id: 41670166,coords: {longitude: -73.97387341,latitude: 40.68614555},icon: 'images/blue_marker.png' },
    {id: 41670166,coords: {longitude: -72.97387341,latitude: 41.68614555},icon: 'images/blue_marker.png' },
    {id: 41670151,coords: {longitude: -73.98124063,latitude: 40.76397698},icon: 'images/blue_marker.png' }
  ];

  $scope.enterDebug = function() {
    debugger;
  }

  $scope.map = { center: { latitude: 40.7903, longitude: -73.9597 }, zoom: 10 };
}]);

app.controller('markerController',['$scope',function($scope) {

}]);