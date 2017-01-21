package com.problemonute.problemonute.views;

import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.mapbox.mapboxsdk.MapboxAccountManager;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.annotations.PolylineOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.services.commons.utils.TextUtils;
import com.problemonute.problemonute.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class GameMapActivity extends AppCompatActivity implements OnMapReadyCallback{

    private MapView mapView;
    private MapboxMap mapboxMap;
    ArrayList<LatLng> points = new ArrayList<>();
    double maxLat = -0.17744;
    double minLat = -0.18163;
    double maxLng = -78.50435;
    double minLng = -78.50837;
    double minDist = 0.0001;
    AlertDialog.Builder builder;
    AlertDialog dialog;
    private final int REQCODE_PROBLEM_ACTIVITY = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MapboxAccountManager.start(this,getString(R.string.access_token));

        setContentView(R.layout.activity_game_map);

        builder = new AlertDialog.Builder(this);
        builder.setTitle("Fuera del perímetro")
                .setMessage("Estás saliendo del perímetro permitido, deseas salir o continuar?")
                .setPositiveButton("CONTINUAR",
                        (dialog,id)->{
                            dialog.cancel();
                        })
                .setNegativeButton("SALIR",
                        (dialog,id)->{
                            finish();
                        });
        dialog = builder.create();


        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);


    }

    @Override
    public void onMapReady(final MapboxMap mapboxMap) {
        this.mapboxMap = mapboxMap;


        // Customize map with markers, polylines, etc.
        mapboxMap.setMyLocationEnabled(true);


        loadPoints();

        for(int i = 0; i<60; i++)
        {
            mapboxMap.addMarker(new MarkerOptions()
                    .position(randomCoordinate())
                    .snippet("Acercate! Quieres probar mi poder!!!"));
        }


        // Load and Draw the GeoJSON
//        new DrawGeoJson().execute();




        mapboxMap.setOnMyLocationChangeListener(
            location -> {
                CameraPosition position = new CameraPosition.Builder()
                        .target(new LatLng(location.getLatitude(),location.getLongitude())) // Sets the new camera position
                        .zoom(19) // Sets the zoom
                        .build(); // Creates a CameraPosition from the builder

                mapboxMap.animateCamera(CameraUpdateFactory
                        .newCameraPosition(position), 3000);



                if(!insidePolygon(location.getLatitude(),location.getLongitude()))
                {

                    if(!dialog.isShowing())
                    dialog.show();
                    loadPoints();
                    Log.e("Area UTE", "Fuera del perimetro");
                }
            }
        );



        mapboxMap.setOnMarkerClickListener(
                marker -> {
                    if(isNear(marker.getPosition(),mapboxMap.getMyLocation(),minDist))
                    {
                        Intent intent = new Intent(this, ProblemActivity.class);
                        intent.putExtra("KEY_MARKER", String.valueOf(marker.getId()));
                        startActivityForResult(intent,REQCODE_PROBLEM_ACTIVITY);
                    }
                    else{
//                        marker.showInfoWindow(mapboxMap,mapView);
                        Toast.makeText(this, "Quieres probar mi poder? Acercate!!", Toast.LENGTH_SHORT).show();
                    }
                    return true;
                }
        );

        mapboxMap.setOnScrollListener(
                () -> {
                    CameraPosition position = new CameraPosition.Builder()
                            .target(new LatLng(mapboxMap.getMyLocation().getLatitude(),mapboxMap.getMyLocation().getLongitude()))
                            .zoom(19) // Sets the zoom
                            .build(); // Creates a CameraPosition from the builder

                    mapboxMap.animateCamera(CameraUpdateFactory
                            .newCameraPosition(position), 2000);
                }
        );



    }


    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQCODE_PROBLEM_ACTIVITY:
                if(resultCode == RESULT_OK) {
                    String message = data.getStringExtra("KEY_MARKER_RETURN");
                    Log.e("Marcador:", message);
                    final Marker marker = mapboxMap.getMarkers().get(Integer.parseInt(message));

                    ValueAnimator markerAnimator = ObjectAnimator.ofObject(marker, "position",
                            new LatLngEvaluator(), marker.getPosition(), randomCoordinate());
                    markerAnimator.setDuration(2000);
                    markerAnimator.start();
                }
                break;
            default:
                break;
        }

    }

    private LatLng randomCoordinate()
    {
        Random r = new Random();
        LatLng c = new LatLng();
        double lat = minLat + (maxLat-minLat)*r.nextDouble();
        double lng = minLng + (maxLng-minLng)*r.nextDouble();
        if(insidePolygon(lat,lng))
        c = new LatLng(lat,lng);
        else
            randomCoordinate();
        return c;
    }



    protected void loadPoints()
    {
        try {
            // Load GeoJSON file
            InputStream inputStream = getAssets().open("geodata/university_area.geojson");
            BufferedReader rd = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
            StringBuilder sb = new StringBuilder();
            int cp;
            while ((cp = rd.read()) != -1) {
                sb.append((char) cp);
            }

            inputStream.close();

            // Parse JSON
            JSONObject json = new JSONObject(sb.toString());
            JSONArray features = json.getJSONArray("features");
            JSONObject feature = features.getJSONObject(0);
            JSONObject geometry = feature.getJSONObject("geometry");
            if (geometry != null) {
                String type = geometry.getString("type");

                // Our GeoJSON only has one feature: a line string
                if (!TextUtils.isEmpty(type) && type.equalsIgnoreCase("LineString")) {

                    // Get the Coordinates
                    JSONArray coords = geometry.getJSONArray("coordinates");
                    for (int lc = 0; lc < coords.length(); lc++) {
                        JSONArray coord = coords.getJSONArray(lc);
                        LatLng latLng = new LatLng(coord.getDouble(1), coord.getDouble(0));
                        points.add(latLng);
                    }
                }
            }
        } catch (Exception exception) {
            Log.e("UTE Polylin", "Exception Loading GeoJSON: " + exception.toString());
        }
    }

    private class DrawGeoJson extends AsyncTask<Void, Void, List<LatLng>> {
        @Override
        protected List<LatLng> doInBackground(Void... voids) {

            loadPoints();
            return points;
        }

        @Override
        protected void onPostExecute(List<LatLng> points) {
            super.onPostExecute(points);

            if (points.size() > 0) {

                // Draw polyline on map
                mapboxMap.addPolyline(new PolylineOptions()
                        .addAll(points)
                        .color(Color.parseColor("#3bb2d0"))
                        .width(5));
            }
        }
    }



    private boolean insidePolygon(double x, double y)
    {
        int counter = 0;
        int n = points.size();
        double xinters;
        LatLng point = new LatLng(x,y);
        LatLng p1, p2;
        p1 = points.get(0);
        for(int i=1;i<=n;i++)
        {
            p2= points.get(i%n);
            if(point.getLongitude() > Math.min(p1.getLongitude(),p2.getLongitude()))
            {
                if(point.getLongitude()<=Math.max(p1.getLongitude(),p2.getLongitude()))
                {
                    if(point.getLatitude()<=Math.max(p1.getLatitude(),p2.getLatitude()))
                    {
                        if(p1.getLongitude()!=p2.getLongitude())
                        {
                            xinters = (point.getLongitude()-p1.getLongitude())*(p2.getLatitude()-p1.getLatitude())/(p2.getLongitude()-p1.getLongitude())+p1.getLatitude();
                            if(p1.getLatitude()==p2.getLatitude()||point.getLatitude()<=xinters)
                                counter++;
                        }
                    }
                }
            }
            p1=p2;
        }

        return counter % 2 != 0;
    }

    private boolean isNear(LatLng marker, Location my, double minDist)
    {
        boolean result = false;
        double x1 = my.getLatitude();
        double y1 = my.getLongitude();
        double x2 = marker.getLatitude();
        double y2 = marker.getLongitude();
        double dist = Math.sqrt(Math.pow(x1-x2,2)+Math.pow(y1-y2,2));
        if(dist<=minDist)
            result = true;
        return  result;
    }

    private static class LatLngEvaluator implements TypeEvaluator<LatLng> {
        // Method is used to interpolate the marker animation.

        private LatLng latLng = new LatLng();

        @Override
        public LatLng evaluate(float fraction, LatLng startValue, LatLng endValue) {
            latLng.setLatitude(startValue.getLatitude()
                    + ((endValue.getLatitude() - startValue.getLatitude()) * fraction));
            latLng.setLongitude(startValue.getLongitude()
                    + ((endValue.getLongitude() - startValue.getLongitude()) * fraction));
            return latLng;
        }
    }
}
