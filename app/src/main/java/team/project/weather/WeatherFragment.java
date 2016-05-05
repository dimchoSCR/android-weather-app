package team.project.weather;

import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import team.project.weather.databinding.FragmentMainBinding;
import team.project.weather.model.Day;
import team.project.weather.model.Model;

public class WeatherFragment extends Fragment
        implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static final int UPDATE_INTERVAL_HOURS = 2;
    public static final int REQUEST_CHECK_SETTINGS = 1;

    private View weatherView;
    private CacheManager manager;
    private GoogleApiClient googleApiClient;
    private Location lastKnownLocation;
    private Location currentLocation;
    private Model model;
    private LocationRequest locationRequest;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create an instance of the GoogleApiClient
        // and add the LocationServices.API
        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(getActivity().getApplicationContext())
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

       // Initialize a location request
        initializeLocationRequest();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentMainBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_main, container, false);

        weatherView = binding.getRoot();

        Day day;
        manager = new CacheManager();

        // Retrieve data from cache if possible
        try {
            day = manager.retrieve();
        } catch (Exception err) {
            Log.e("Cache", "Error retrieving data from cache", err);
            Toast.makeText(weatherView.getContext(),
                    "Unable to load stored weather data. Please wait for an update from the network"
                    , Toast.LENGTH_LONG).show();
            day = new Day();
        }

        model = new Model();
        model.currentDay.set(day);
        binding.setModel(model);

        return weatherView;
    }

    @Override
    public void onStart() {
        super.onStart();
        // Connect to the Google Play Services
        googleApiClient.connect();

        // Read location settings on the device
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest)
                .setAlwaysShow(true);

        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi
                        .checkLocationSettings(googleApiClient,builder.build());

        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(@NonNull LocationSettingsResult result) {
                final Status status = result.getStatus();
                //final LocationSettingsStates lSettingsState = result.getLocationSettingsStates();

                switch(status.getStatusCode()){
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        // Show a dialog to change current settings
                        try {
                            status.startResolutionForResult(getActivity(),REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException err) {
                            Log.e("Intent","Could not resolve the the result!",err);
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        Toast.makeText(weatherView.getContext()
                                ,"Could not change Location settings!"
                                ,Toast.LENGTH_LONG)
                                .show();
                        break;
                }
            }
        });
    }

    @Override
    // Handle the result of the settings change
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == REQUEST_CHECK_SETTINGS){
            if(resultCode == Activity.RESULT_OK){
                Toast.makeText(weatherView.getContext()
                        , "Settings changed successfully!"
                        , Toast.LENGTH_LONG)
                        .show();
            }else{
                Toast.makeText(weatherView.getContext()
                        , "Settings change was unsuccessful!"
                        , Toast.LENGTH_LONG)
                        .show();
            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        // Disconnect from the Google Play Services
        googleApiClient.disconnect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(weatherView.getContext()
                ,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(weatherView.getContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            // Required only for API 23(android 6.0) and above
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        lastKnownLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);

        if(lastKnownLocation != null){
            currentLocation = lastKnownLocation;
            Log.d("LastLocation",String.valueOf(lastKnownLocation.getLatitude()));
            Log.d("LastLocation",String.valueOf(lastKnownLocation.getLongitude()));
        }else{
            Log.d("Location","Previous location unknown");
        }

        // Setting up a scheduled Executor inorder to execute the thread
        // within the UPDATE_INTERVAL_HOURS
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(new UpdaterThread(model), 0, UPDATE_INTERVAL_HOURS, TimeUnit.HOURS);

    }

    @Override
    public void onConnectionSuspended(int i) {
        //TODO do something when connection to google services is suspended
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        //TODO do something when connection to google services fails
    }

    private void initializeLocationRequest(){
        locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(500);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    private class UpdaterThread implements Runnable, LocationListener{
        private Model model;
        private OpenWeatherService openWeatherService;

        UpdaterThread(Model model){
            this.model = model;
            openWeatherService = new OpenWeatherService();

            // Start receiving location updates
            startLocationUpdates();
        }

        @Override
        public void run(){
            try {
                // Test the location coordinates
                Log.d("Location",String.valueOf(currentLocation.getLatitude()));
                Log.d("Location",String.valueOf(currentLocation.getLongitude()));

                // No need to synchronize with the UI thread because of the data binding
                model.currentDay.set(openWeatherService.getCurrentDay
                        (currentLocation.getLatitude()
                                ,currentLocation.getLongitude()));

                manager.store(model.currentDay.get());


            } catch (Exception err) {
                Log.e("WeatherUpdate","Could not update weather!",err);
                // Post the toast on the UI thread's MessageQueue
                MainActivity.toastHandler.post(new Runnable(){
                    @Override
                    public void run(){
                        Toast.makeText(getActivity().getApplicationContext(),
                                "Filed to update weather! Please check your internet connection.",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });
            }
        }
        @Override
        public void onLocationChanged(Location location){
            currentLocation = location;
        }

        private void startLocationUpdates(){
            // TODO Handle Android 6 permissions
            // Catching an exception for now
            try {
                LocationServices.FusedLocationApi
                        .requestLocationUpdates(googleApiClient, locationRequest, this);
            }catch (SecurityException err){
                Log.d("Permissions","Permission error",err);
            }
        }
    }
}
