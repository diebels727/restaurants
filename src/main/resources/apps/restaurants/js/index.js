function enterDebug() {
  debugger;
};

$(function() {
  function initialize() {
    var mapOptions = {
      center: {
         lat: 40.7903,
         lng: -73.9597
      },
      zoom: 10
    };

    var map = new google.maps.Map(document.getElementById('map-canvas'),
        mapOptions);

    var markers = [];
    for (var i = 0; i < 10000; i++) {
      var center = map.getCenter()

      var latLng = new google.maps.LatLng(center.lat() + Math.random(-0.01,0.01),
          center.lng()+Math.random(-0.01,0.01));
      var marker = new google.maps.Marker({'position': latLng});
      markers.push(marker);
    }

    var mc = new MarkerClusterer(map,markers);
  }

  google.maps.event.addDomListener(window, 'load', initialize);
});


