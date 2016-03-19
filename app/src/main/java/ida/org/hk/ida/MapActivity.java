package ida.org.hk.ida;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import clas.Restaurant;
import clas.ServerHandler;

public class MapActivity extends ToolbarActivity
        implements OnMapReadyCallback, GoogleMap.OnMyLocationChangeListener, GoogleMap.OnMarkerClickListener,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, ResultCallback {
    private GoogleMap mMap;
    private GoogleApiClient googleApiClient;
    private LocationRequest request;
    private static final long MIN_TIME = 400;
    private static final float MIN_DISTANCE = 1000;
    private static final int REQUEST_CHECK_SETTINGS = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContent(R.layout.activity_nearby);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        googleApiClient.connect();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            // Check for the integer request code originally supplied to startResolutionForResult().
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_CANCELED:
                        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                                .findFragmentById(R.id.map);
                        SpannableStringBuilder snackbarText = new SpannableStringBuilder();
                        snackbarText.append("Turning GPS On");
                        int boldStart = snackbarText.length();
                        snackbarText.append("Error");
                        snackbarText.setSpan(new ForegroundColorSpan(0xFFFF0000), boldStart, snackbarText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        snackbarText.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), boldStart, snackbarText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                        Snackbar.make(mapFragment.getView()
                                , snackbarText, Snackbar.LENGTH_LONG).show();
                        break;
                }
                break;
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
        mMap = googleMap;
        mMap.setMyLocationEnabled(true);
        mMap.setOnMyLocationChangeListener(this);
        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(-34, 151);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        /*
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(22.3964, 114.109), 12));
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (locationManager != null) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME, MIN_DISTANCE, this);
        }
        */
        new LoadMapTask().execute();
    }

    @Override
    public void onMyLocationChange(Location location) {
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        if (mMap != null) {
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));
            mMap.setOnMyLocationChangeListener(null);
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        String restId = marker.getSnippet();
        Intent i = new Intent(this, RestDetailsActivity.class);
        i.putExtra(RestDetailsActivity.EXTRA_REST_ID, restId);
        startActivity(i);
        return false;
    }

    @Override
    public void onConnected(Bundle bundle) {
        buildLocationRequest();
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(request);
        LocationSettingsRequest locationSettingsRequest = builder.build();
        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(
                        googleApiClient,
                        locationSettingsRequest
                );
        result.setResultCallback(this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onResult(Result result) {
        final Status status = result.getStatus();
        switch (status.getStatusCode()) {
            case LocationSettingsStatusCodes.SUCCESS:
                //Log.i(TAG, "All location settings are satisfied.");
                //startLocationUpdates();
                break;
            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                //Log.i(TAG, "Location settings are not satisfied. Show the user a dialog to" + "upgrade location settings ");

                try {
                    // Show the dialog by calling startResolutionForResult(), and check the result
                    // in onActivityResult().
                    status.startResolutionForResult(this, REQUEST_CHECK_SETTINGS);
                } catch (IntentSender.SendIntentException e) {
                    //Log.i(TAG, "PendingIntent unable to execute request.");
                }
                break;
            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                //Log.i(TAG, "Location settings are inadequate, and cannot be fixed here. Dialog " + "not created.");
                break;
        }
    }

    private void buildLocationRequest() {
        request = LocationRequest.create();
        request.setFastestInterval(LocationRequest.PRIORITY_HIGH_ACCURACY);
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    private class LoadMapTask extends AsyncTask<Void, Void, List<Map<String, Object>>> {

        @Override
        protected List<Map<String, Object>> doInBackground(Void... params) {
            ServerHandler handler = new ServerHandler();
            List<Restaurant> restaurants = handler.getAllRestaurants();
            List<Map<String, Object>> mapList = new ArrayList<>();
            for (Restaurant rest : restaurants) {
                if (rest.getAddress() != null) {
                    LatLng latLng = handler.getLatlngbyaddress(rest.getAddress());
                    if (latLng != null) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("latLng", latLng);
                        map.put("rest", rest);
                        mapList.add(map);
                    }
                }
            }
            return mapList;
        }

        @Override
        protected void onPostExecute(List<Map<String, Object>> mapList) {
            for (Map<String, Object> map : mapList) {
                LatLng latLng = (LatLng) map.get("latLng");
                Restaurant rest = (Restaurant) map.get("rest");
                mMap.addMarker(new MarkerOptions().position(latLng).title(rest.getRestName()).snippet(rest.getRestID()));
                mMap.setOnMarkerClickListener(MapActivity.this);
            }
        }
    }
}
