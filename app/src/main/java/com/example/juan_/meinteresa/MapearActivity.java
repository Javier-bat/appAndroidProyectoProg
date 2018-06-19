package com.example.juan_.meinteresa;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.juan_.meinteresa.constantes.DirectionsParser;
import com.google.android.gms.common.util.Strings;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapearActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Double longitud;
    private Double latitud;
    private String titulo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapear);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        longitud = getIntent().getExtras().getDouble("longitud");
        latitud = getIntent().getExtras().getDouble("latitud");
        titulo = getIntent().getExtras().getString("titulo");

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 2;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        } else {
            mMap.setMyLocationEnabled(true);

            LatLng punto = new LatLng(latitud, longitud);
            mMap.addMarker(new MarkerOptions().position(punto).title(titulo));
            // mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(punto,17));
            LocationManager locationManager;
            locationManager = (LocationManager) getSystemService(getApplicationContext().LOCATION_SERVICE);
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (location != null) {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                LatLng ubicacion = new LatLng(latitude, longitude);
                mMap.addMarker(new MarkerOptions().position(ubicacion).title("Ubicacion").icon(BitmapDescriptorFactory.defaultMarker(
                        BitmapDescriptorFactory.HUE_BLUE))

                );
                String url = getRequestUrl(ubicacion, punto);
                TaskRequestDirection taskRequestDirection = new TaskRequestDirection();
                taskRequestDirection.execute(url);
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ubicacion, 17));
            } else {

                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(punto, 17));
                Toast.makeText(getApplicationContext(), "Ha ocurrido un error al trazar la ruta, compruebe si tiene la ubicacion activada", Toast.LENGTH_LONG).show();

            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 2: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                        return;
                    }
                    mMap.setMyLocationEnabled(true);

                    LatLng punto = new LatLng(latitud, longitud);
                    mMap.addMarker(new MarkerOptions().position(punto).title(titulo));
                    // mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(punto,17));
                    LocationManager locationManager;
                    locationManager = (LocationManager) getSystemService(getApplicationContext().LOCATION_SERVICE);
                    Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    if (location != null) {
                        double latitude = location.getLatitude();
                        double longitude = location.getLongitude();
                        LatLng ubicacion = new LatLng(latitude, longitude);
                        mMap.addMarker(new MarkerOptions().position(ubicacion).title("Ubicacion").icon(BitmapDescriptorFactory.defaultMarker(
                                BitmapDescriptorFactory.HUE_BLUE))

                        );
                        String url = getRequestUrl(ubicacion,punto);
                        TaskRequestDirection taskRequestDirection = new TaskRequestDirection();
                        taskRequestDirection.execute(url);
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ubicacion,17));
                    }else{

                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(punto,17));
                        Toast.makeText(getApplicationContext(),"Ha ocurrido un error al trazar la ruta, compruebe si tiene la ubicacion activada" ,Toast.LENGTH_LONG).show();

                    }

                    }else{
                    LatLng punto = new LatLng(latitud, longitud);
                    mMap.addMarker(new MarkerOptions().position(punto).title(titulo));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(punto,17));
                    Toast.makeText(getApplicationContext(),"Acepte los permisos para poder marcar la ruta" ,Toast.LENGTH_LONG).show();

                }


                }
                return;
            }
        }

    private String getRequestUrl(LatLng origen, LatLng destino) {
        String str_org = "origin="+origen.latitude +","+origen.longitude;

        String str_des = "destination="+destino.latitude+","+destino.longitude;

        String sensor ="sensor=false";

        String mode = "mode=driving";

        String param =str_org+"&"+str_des+"&"+sensor+"&"+mode;

        String salida = "json";

        String url ="https://maps.googleapis.com/maps/api/directions/"+salida+"?"+param;

        return url;
    }
    private String requestDirection(String requestUrl) throws IOException {

        String responseString ="";

        InputStream inputstream =null;

        HttpURLConnection con = null;
        try{
            URL url =new URL(requestUrl);
            con= (HttpURLConnection) url.openConnection();
            con.connect();
            inputstream = con.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputstream);

            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            StringBuffer stringBuffer = new StringBuffer();

            String line = "";

            while ((line = bufferedReader.readLine())!=null){

                stringBuffer.append(line);
            }

            responseString = stringBuffer.toString();
            bufferedReader.close();
            inputStreamReader.close();

        }catch(Exception e){
            e.printStackTrace();

        }finally {
            if(inputstream!=null){

                inputstream.close();
            }
            con.disconnect();
        }



        return responseString;
    }

    public class TaskRequestDirection extends AsyncTask<String, Void, String>{
        @Override
        protected String doInBackground(String... strings) {
            String responseString = "";
            try {
                responseString = requestDirection(strings[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return responseString;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            TaskParse taskParse = new TaskParse();
            taskParse.execute(s);
        }

    }
    public class TaskParse extends AsyncTask<String,Void,List<List<HashMap<String, String>>> >{


        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... strings) {
            JSONObject jsonObject=null;
            List<List<HashMap<String, String>>> routes= null;
            try {
                jsonObject = new JSONObject(strings[0]);

                DirectionsParser directionsParser = new DirectionsParser();
                routes = directionsParser.parse(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return routes;
        }

        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> lists) {
            ArrayList points=null;
            PolylineOptions polylineOptions=null;

            for (List<HashMap<String, String>> path: lists){
                points = new ArrayList();
                polylineOptions = new PolylineOptions();
                for (HashMap<String, String> point: path){
                    double lat = Double.parseDouble(point.get("lat"));
                    double lon = Double.parseDouble(point.get("lon"));
                    points.add(new LatLng(lat,lon));

                }
                polylineOptions.addAll(points);
                polylineOptions.width(15);
                polylineOptions.color(Color.BLUE);
                polylineOptions.geodesic(true);

            }
            if(polylineOptions!=null){

                mMap.addPolyline(polylineOptions);
            }else{

                Toast.makeText(getApplicationContext(),"No se pudo encontrar camino",Toast.LENGTH_LONG);
            }
        }
    }
}
