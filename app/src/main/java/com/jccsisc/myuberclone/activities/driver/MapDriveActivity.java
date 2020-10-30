package com.jccsisc.myuberclone.activities.driver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.dynamic.IFragmentWrapper;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.jccsisc.myuberclone.R;
import com.jccsisc.myuberclone.activities.MainActivity;
import com.jccsisc.myuberclone.includes.MyToolbar;
import com.jccsisc.myuberclone.providers.AuthProvider;

public class MapDriveActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private SupportMapFragment mMapFragment;

    private final static int LOCATION_REQUEST_CODE = 1; //la utilizamos como bandera para saber si deberia de pedir los permisos de ubicacion

    AuthProvider mAuthProvider;

    private LocationRequest mLocationRequest;
    private FusedLocationProviderClient mFusedLocation;

    //callback que va a estar escuchando cada vez que el usuario se mueva
    private LocationCallback mLocationCallBack = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            //recorremos la propiedad de tipo location
            for (Location location: locationResult.getLocations()) {

                if (getApplicationContext() != null) {
                    //obtenemos la localizacion del usuario en tiempo real
                    mMap.moveCamera(CameraUpdateFactory.newCameraPosition(
                            new CameraPosition.Builder()
                            .target(new LatLng(location.getLatitude(), location.getLongitude()))
                            .zoom(17f)
                            .build()
                    ));
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_drive);

        MyToolbar.showToolbar(MapDriveActivity.this, "Conductor", false);

        //con esta propiedad vmos a poder iniciar o detener la ubicacion cada vez que sea conveniente
        mFusedLocation = LocationServices.getFusedLocationProviderClient(this);

        mAuthProvider = new AuthProvider();

        mMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mMapFragment.getMapAsync(this);

    }


    //metodo para pedir los permisos
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == LOCATION_REQUEST_CODE) {
            //en esta parte preguntamos si el usuario concedio los permisos
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    mFusedLocation.requestLocationUpdates(mLocationRequest, mLocationCallBack, Looper.myLooper());
                }
            }
        }
    }


    //metodo para inicializar a nuestro escuchador
    private void startLocation() {
        //validar si la version es superior a masmalow
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //volvlemos a preguntar si los permisos ya estan concedidos
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mFusedLocation.requestLocationUpdates(mLocationRequest, mLocationCallBack, Looper.myLooper());
            }else {
                checkLocationPermissions();
            }
        }else {
            mFusedLocation.requestLocationUpdates(mLocationRequest, mLocationCallBack, Looper.myLooper());
        }
    }

    //metodo para saber si concedió los permisos
    private void checkLocationPermissions() {
        //preguntamos si los permisos son diferentes de concedido
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                //utilizaremos un alertDialog
                new AlertDialog.Builder(this)
                        .setTitle("Proporciona los permisos para continuar")
                        .setMessage("Esta aplicación requiere de los permisos de ubicación para poder utilizarce")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //HABILITAMOS LOS PERMISOS PARA CONTINUAR
                                ActivityCompat.requestPermissions(MapDriveActivity.this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);
                            }
                        }).create().show();
            }else {
                ActivityCompat.requestPermissions(MapDriveActivity.this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);
            }
        }
    }


    //debemos de agregar la sig. propiedad al manifest dentro de aplication para que funcione la app
    //<meta-data android:name="com.google.android.geo.API_KEY" android:value="@string/google_api_key"/>
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);//seleccionamos el tipo de mapa que vamos a usar
        mMap.getUiSettings().setZoomControlsEnabled(true); //para que aparezcarn los controles para hacer zoom

        mLocationRequest = new LocationRequest();
        //le asiganmos unas propiedades
        mLocationRequest.setInterval(1000); //el intervalo de tiempo en que va a estar actualizando la posicion del usuario
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY); //para que haga uso dl GPS con la mayor presicion posible
        mLocationRequest.setSmallestDisplacement(5);

        //ejecutamos el metodo para inicializar nuestro escuchador
        startLocation();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.driver_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_logout) {
            logout();
        }

        return super.onOptionsItemSelected(item);
    }


    public void logout() {
        mAuthProvider.logout();

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }
}