package com.example.juan_.meinteresa;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;

import com.example.juan_.meinteresa.constantes.Sentencias;
import com.example.juan_.meinteresa.entidad.Ubicacion;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    double longMapeo=0;
    double latMapeo=0;
    ArrayList<Ubicacion> ubicaciones;
    ArrayList<Puntos> puntos;
    ArrayList<LatLng> latLngsPuntos;
    ConexionSQLiteHelper conn;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        conn = new ConexionSQLiteHelper(this,"db_ubicacion",null,1);


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());
        if (status == ConnectionResult.SUCCESS) {
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
        } else {


            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, (Activity) getApplicationContext(), 10);
            dialog.show();

        }
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
       /* mMap = googleMap;
        googleMap.setMapType(googleMap.MAP_TYPE_NORMAL);


        // Add a marker in Sydney and move the camera

        LocationManager locationManager = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        googleMap.setMyLocationEnabled(true);
        Location location = locationManager.getLastKnownLocation(locationManager
                .getBestProvider(criteria, false));

        if(location!= null){
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            LatLng ubicacion = new LatLng(latitude, longitude);

            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ubicacion,16));
        }else{
           locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER,0,0, (LocationListener) this);
        }*/

        mMap = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 2;


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);

        } else {

            mMap.setMyLocationEnabled(true);
            LocationManager locationManager;
            locationManager = (LocationManager) getSystemService(getApplicationContext().LOCATION_SERVICE);
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (location != null) {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                LatLng ubicacion = new LatLng(latitude, longitude);
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ubicacion, 12));
                consultarLista();
                latLngsPuntos=new ArrayList<>();
                if(puntos!=null){
                for (int i = 0;i<puntos.size();i++){

                    LatLng punto = new LatLng(puntos.get(i).getLatitud(), puntos.get(i).getLongitud());

                    latLngsPuntos.add(punto);


                }
                for (int i = 0;i<puntos.size();i++) {

                    mMap.addMarker(new MarkerOptions().position(latLngsPuntos.get(i)).title(puntos.get(i).getTitulo()).icon(BitmapDescriptorFactory.defaultMarker(
                            BitmapDescriptorFactory.HUE_RED))

                    );
                }

                }

            }


        }


    }

    private void consultarLista() {
        db= conn.getReadableDatabase();
        Ubicacion ubicacion = null;

        ubicaciones=new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM "+ Sentencias.tablaNombreUb,null);


        while(cursor.moveToNext()){
            ubicacion = new Ubicacion();
            ubicacion.setId(cursor.getInt(0));
            ubicacion.setLatitud(cursor.getDouble(1));
            ubicacion.setLongitud(cursor.getDouble(2));
            ubicacion.setTitulo(cursor.getString(4));
            ubicacion.setDescripcion(cursor.getString(5));
            ubicacion.setFecha(cursor.getString(3));

            ubicaciones.add(ubicacion);
        }
        crearPuntos();
    }

    private void crearPuntos() {
        puntos =new ArrayList<Puntos>();

        for (int i=0;i<ubicaciones.size();i++){
            Puntos pu = new Puntos();
            pu.setLatitud(ubicaciones.get(i).getLatitud());
            pu.setLongitud(ubicaciones.get(i).getLongitud());
            pu.setTitulo(ubicaciones.get(i).getTitulo());
            puntos.add(pu);

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
                    LocationManager locationManager;
                    locationManager = (LocationManager) getSystemService(getApplicationContext().LOCATION_SERVICE);
                    Location location =locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    if(location!=null){
                        double latitude = location.getLatitude();
                        double longitude = location.getLongitude();
                        LatLng ubicacion = new LatLng(latitude, longitude);
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ubicacion,12));
                        consultarLista();
                        latLngsPuntos=new ArrayList<>();
                        if(puntos!=null){
                        for (int i = 0;i<puntos.size();i++){

                            LatLng punto = new LatLng(puntos.get(i).getLatitud(), puntos.get(i).getLongitud());

                            latLngsPuntos.add(punto);


                        }
                        for (int i = 0;i<puntos.size();i++) {

                            mMap.addMarker(new MarkerOptions().position(latLngsPuntos.get(i)).title(puntos.get(i).getTitulo()).icon(BitmapDescriptorFactory.defaultMarker(
                                    BitmapDescriptorFactory.HUE_RED))

                            );
                        }

                        }
                    }else{}


                } else {
                }
                return;
            }
        }
    }

}

class Puntos{
    private Double latitud;
    private Double longitud;
    private String titulo;

    public Puntos() {
    }

    public Puntos(Double latitud, Double longitud,String titulo) {
        this.latitud = latitud;
        this.longitud = longitud;
        this.titulo=titulo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }
}

