package com.example.heremiStartup;

import static android.content.ContentValues.TAG;
import static android.content.Context.LOCATION_SERVICE;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.PendingResult;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.internal.PolylineEncoding;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LocationFragment extends Fragment implements
        OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnInfoWindowClickListener

{

    LocationRequest locationRequest;
    LoadingDia loadingMap;
    Boolean locationpermission;
    GoogleMap mGoogleMap;
    MapView mapView;
    Polyline polyline;
    modelPharmacy modelPharmacy;
    private FusedLocationProviderClient mlocation, mupdatingLocation;

    private int GPS_REQUEST_CODE = 9001;
    public LocationFragment(LoadingDia loadingMap, Boolean locationpermission) {
        this.loadingMap = loadingMap;
        this.locationpermission = locationpermission;

    }
    private ClusterManager<ClusterMarker> mClusterManager;
    private MyClusterManagerRenderer mClusterManagerRenderer;
    private ArrayList<ClusterMarker> mClusterMarkers = new ArrayList<>();
    List<modelPharmacy> pharmas = new ArrayList<modelPharmacy>();
    GeoApiContext mGeoApiContext;
    public static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";

    public LocationFragment(LoadingDia loadingMap,Boolean locationpermission, int pharma_id) {
        this.modelPharmacy = modelPharmacy;
        this.loadingMap = loadingMap;
        this.locationpermission = locationpermission;
        if(pharma_id!=0){
            getSpecificPharma(pharma_id);
        }

    }

    private void getSpecificPharma(int pharma_id) {
        Call<List<modelPharmacy>> call = apiClient.getDeclaration().getPharma(pharma_id);
        call.enqueue(new Callback<List<modelPharmacy>>() {
            @Override
            public void onResponse(Call<List<modelPharmacy>> call, Response<List<modelPharmacy>> response) {
                if(response.isSuccessful()){
                    if (response.body() != null) {
                        modelPharmacy = response.body().get(0);
                        getUpdatingLocation(modelPharmacy);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<modelPharmacy>> call, Throwable t) {

            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View parent = inflater.inflate(R.layout.fragment_location, container, false);
        mapView = (MapView) parent.findViewById(R.id.map_view);
        initGooglemaps(savedInstanceState);




        mlocation = LocationServices.getFusedLocationProviderClient(getContext());
        getLocation();





        return parent;
    }

    @SuppressLint("MissingPermission")
    private void getUpdatingLocation(modelPharmacy modelPharmacy) {
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(20);
        locationRequest.setFastestInterval(20);


        mupdatingLocation = LocationServices.getFusedLocationProviderClient(getContext());

        mupdatingLocation.requestLocationUpdates(locationRequest, new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                super.onLocationResult(locationResult);
                calculateDirections(locationResult.getLastLocation().getLatitude(), locationResult.getLastLocation().getLongitude(), modelPharmacy);
            }
        }, Looper.getMainLooper());


    }

    @SuppressLint("PotentialBehaviorOverride")
    private void initGooglemaps(Bundle savedInstanceState) {
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }
        if(locationpermission){
            if(isGPSenabled()){
                mapView.onCreate(mapViewBundle);
                mapView.getMapAsync(this);
                if(mGeoApiContext == null){
                    mGeoApiContext = new GeoApiContext.Builder()
                            .apiKey(BuildConfig.MAPS_API_KEY)
                            .build();
                }

            }

        }
    }

    private void getPharmacy() {
        Call<List<modelPharmacy>> modelpharmacycall = apiClient.getDeclaration().getPharma();

        modelpharmacycall.enqueue(new Callback<List<modelPharmacy>>() {
            @SuppressLint("PotentialBehaviorOverride")
            @Override
            public void onResponse(Call<List<modelPharmacy>> call, Response<List<modelPharmacy>> response) {
                pharmas = response.body();
                addMapMarkers();

            }

            @Override
            public void onFailure(Call<List<modelPharmacy>> call, Throwable t) {

            }
        });



    }



    private boolean isGPSenabled() {
        LocationManager locationManager = (LocationManager) getContext().getSystemService(LOCATION_SERVICE);
        boolean providerEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if(providerEnabled){
            System.out.println(true);
            return true;
        }else{
            AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                    .setTitle("GPS Permission")
                    .setMessage("GPS Permission Required")
                    .setPositiveButton("Yes",((dialogInterface,i)->{
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivityForResult(intent, GPS_REQUEST_CODE);
                    })).setCancelable(false).show();
        }
        return false;
    }


    @SuppressLint("MissingPermission")
    private void getLocation() {
        mlocation.getLastLocation().addOnCompleteListener(task -> {
           if(task.isSuccessful()){
               Location location = task.getResult();
               gotoLoc(location.getLatitude(),location.getLongitude());
           }
        });

    }

    private void gotoLoc(double latitude, double longitude) {
        LatLng latLng = new LatLng(latitude, longitude);

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng,18);
        mGoogleMap.moveCamera(cameraUpdate);
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);



    }
    private void addMapMarkers(){
        if(mGoogleMap != null){
            if(mClusterManager == null){
                mClusterManager = new ClusterManager<ClusterMarker>(getActivity().getApplicationContext(),mGoogleMap);
            }
            if(mClusterManagerRenderer==null){
                mClusterManagerRenderer = new MyClusterManagerRenderer(getActivity(),mGoogleMap,mClusterManager);
                mClusterManager.setRenderer(mClusterManagerRenderer);
            }

            for(int i= 0; i<pharmas.size();i++){
                String snippet = pharmas.get(i).getPharmacy_location();
                int avatar = R.drawable.pharma_1;

                ClusterMarker newCluster = new ClusterMarker(
                        new LatLng(Double.parseDouble(pharmas.get(i).getPharmacy_lat()),
                                Double.parseDouble(pharmas.get(i).getPharmacy_lng())),
                                pharmas.get(i).getPharmacy_name(),
                                snippet,
                                avatar,
                                pharmas.get(i)
                       );
                mClusterManager.addItem(newCluster);
                mClusterMarkers.add(newCluster);
            }
            mClusterManager.cluster();



        }
    }


    @SuppressLint({"MissingPermission", "PotentialBehaviorOverride"})
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mGoogleMap.setMyLocationEnabled(locationpermission);
        getPharmacy();
        loadingMap.dismissDialog();


    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        mapView.onSaveInstanceState(outState);
    }
    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();

    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }



    @Override
    public void onPause() {
        mapView.onPause();
        super.onPause();
        if(polyline!=null){
            polyline.remove();
        }
    }

    @Override
    public void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if(requestCode==GPS_REQUEST_CODE){
            LocationManager locationManager = (LocationManager) getContext().getSystemService(LOCATION_SERVICE);
            boolean providerEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            if(providerEnabled){
                Toast.makeText(getContext(), "GPS ENABLED", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getContext(), "GPS DISABLED", Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    public void onInfoWindowClick(@NonNull Marker marker) {
        Toast.makeText(getContext(), "Clicked", Toast.LENGTH_SHORT).show();
            final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(marker.getSnippet())
                    .setCancelable(true)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                           // calculateDirections(marker);
                            dialog.dismiss();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                            dialog.cancel();
                        }
                    });
            final AlertDialog alert = builder.create();
            alert.show();


    }

    private void calculateDirections(double latitude, double longitude, modelPharmacy modelPharmacy){


        com.google.maps.model.LatLng destination = new com.google.maps.model.LatLng(
                Double.parseDouble(modelPharmacy.getPharmacy_lat()),
                Double.parseDouble(modelPharmacy.getPharmacy_lng())
        );
        DirectionsApiRequest directions = new DirectionsApiRequest(mGeoApiContext);

        directions.alternatives(false);
        directions.origin(
                new com.google.maps.model.LatLng(
                        latitude, longitude
                )
        );

        directions.destination(destination).setCallback(new PendingResult.Callback<DirectionsResult>() {
            @Override
            public void onResult(DirectionsResult result) {


                addPolylinesToMap(result);
            }

            @Override
            public void onFailure(Throwable e) {
                Log.e(TAG, "onFailure: " + e.getMessage() );

            }
        });
    }
    private void addPolylinesToMap(final DirectionsResult result){
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "run: result routes: " + result.routes.length);

                for(DirectionsRoute route: result.routes){
                    Log.d(TAG, "run: leg: " + route.legs[0].toString());
                    System.out.println(""+route.legs[0].distance);
                    List<com.google.maps.model.LatLng> decodedPath = PolylineEncoding.decode(route.overviewPolyline.getEncodedPath());

                    List<LatLng> newDecodedPath = new ArrayList<>();

                    // This loops through all the LatLng coordinates of ONE polyline.
                    for(com.google.maps.model.LatLng latLng: decodedPath){

//                        Log.d(TAG, "run: latlng: " + latLng.toString());

                        newDecodedPath.add(new LatLng(
                                latLng.lat,
                                latLng.lng
                        ));
                    }
                    if(polyline!=null){
                        polyline.remove();
                    }
                    try {
                        polyline = mGoogleMap.addPolyline(new PolylineOptions().addAll(newDecodedPath));
                        polyline.setColor(ContextCompat.getColor(getActivity(), R.color.main));
                        polyline.setClickable(false);
                    }catch (NullPointerException nullPointerException){

                    }



                }
            }
        });
    }
}