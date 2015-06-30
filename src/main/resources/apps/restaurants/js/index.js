/** This method will be called when JQuery is ready for use */

// $(function() {
//
//
//   // Listen for the koverse JS to be ready
//   Koverse.addEventListener(KoverseApplication.EVENT_READY, function() {
//
//     // Example koverse call - retrieve data collections
//     Koverse.getDataCollections(function(response){
//
//       // Check that the response was successful
//       if(!response.success) {
//         $(document.body).append("<span class='error'>Error retrieving data collections: "  + response.failureMessage + "</span>");
//         return;
//       }
//
//       // sort the collections
//       var dataCollectionsSorted = response.dataCollections.sortBy("name");
//
//       // Loop over the data collections in response
//       $.each(dataCollectionsSorted, function(index, dataCollection){
//
//         // Append to list of data collections
//         $("#dataCollectionList").append("<li><a href='" + KoverseUtil.getDataCollectionURL(dataCollection.id) + "'>" + dataCollection.name + "</a></li>");
//
//       });
//
//     });
//
//   });
//
// });

function enterDebug() {
  debugger;
};

function initialize() {
  var mapOptions = {
    center: { lat: -34.397, lng: 150.644},
    zoom: 8
  };
  var map = new google.maps.Map(document.getElementById('map-canvas'),
      mapOptions);
}
google.maps.event.addDomListener(window, 'load', initialize);


