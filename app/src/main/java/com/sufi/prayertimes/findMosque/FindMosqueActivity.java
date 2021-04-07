package com.sufi.prayertimes.findMosque;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.directions.route.AbstractRouting;
import com.directions.route.Route;
import com.directions.route.RouteException;
import com.directions.route.Routing;
import com.directions.route.RoutingListener;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.sufi.prayertimes.R;
import com.sufi.prayertimes.constantAndInterfaces.Constants;



import com.sufi.prayertimes.util.GetData;
import com.sufi.prayertimes.util.GpsTracker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.annotations.NonNull;

public class FindMosqueActivity extends FragmentActivity implements OnMapReadyCallback, GetData.GetDataListener,
        RoutingListener, FindMosqueAdapter.RecyclerViewItemClickListener {

    ArrayList<Route> routes;
    private GoogleMap mMap;
    Location mLastLocation;
    LocationRequest mLocationRequest;
    //    Marker pickupMarker;
    GpsTracker gpsTracker;
    RecyclerView recyclerView;
    List<MosqueDataModel> list;
    FindMosqueAdapter adapter;
    private LatLng destinationLatLng;

    private List<Polyline> polylines;
    private static final int[] COLORS = new int[]{R.color.primary_dark_material_light};


    private FusedLocationProviderClient mFusedLocationClient;

    private Button mRideStatus, mHistory;

    private int status = 0;


    private SupportMapFragment mapFragment;


    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_mosque);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        recyclerView = findViewById(R.id.recyclerView_findMosque);
        recyclerView.setHasFixedSize(true);
        gpsTracker = new GpsTracker(this);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        HashMap<String, String> params = new HashMap<>();
        params.put("lat", String.valueOf(gpsTracker.getLatitude()));
        params.put("lng", String.valueOf(gpsTracker.getLongitude()));
        params.put("radius", "30");
        new GetData("http://192.168.0.47:81/abs/find_mosque_api.php", params, 10, this).execute();

    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        MapsInitializer.initialize(this);
        mMap.setMyLocationEnabled(true);
        LatLng latLng = new LatLng(gpsTracker.getLatitude(), gpsTracker.getLongitude());
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

    }


    LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            for (Location location : locationResult.getLocations()) {
                if (getApplicationContext() != null) {

                    mLastLocation = location;


                    LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                    mMap.animateCamera(CameraUpdateFactory.zoomTo(15));

                }
            }
        }
    };

    protected void createMarker(double latitude, double longitude, String title, String snippet) {

        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(latitude, longitude))
                .anchor(0.5f, 0.5f)
                .title(title)
                .snippet(snippet)
                .icon(bitmapDescriptorFromVector(this)));
    }

    @Override
    public void getDownloadData(String result, int request) {
        if (request == 10) {
            Log.d("DATA", "getDownloadData: " + result);
            list = new ArrayList<>();
            try {
                JSONObject object = new JSONObject(result);
                if (!object.getBoolean("error")) {

                    JSONArray mosqueList = object.getJSONArray("list");
                    for (int i = 0; i < mosqueList.length(); i++) {
                        object = mosqueList.getJSONObject(i);
                        list.add(new MosqueDataModel(
                                object.getInt("id"),
                                object.getString("name"),
                                object.getString("address"),
                                object.getString("image"),
                                object.getDouble("lat"),
                                object.getDouble("lng"),
                                object.getDouble("distance")
                        ));
                        createMarker(Double.parseDouble(object.getString("lat")), Double.parseDouble(object.getString("lng")),
                                object.getString("name"), object.getString("address"));
                    }
                    SnapHelper mSnapHelper = new PagerSnapHelper();
                    mSnapHelper.attachToRecyclerView(recyclerView);
                    adapter = new FindMosqueAdapter(this, list, this);
                    recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
                    recyclerView.setAdapter(adapter);
                    recyclerView.setVisibility(View.VISIBLE);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

   /* private boolean isGooglePlayServicesAvailable() {
        int status = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this);
        if (ConnectionResult.SUCCESS == status) {
            return true;
        } else {
            GooglePlayServicesUtil.getErrorDialog(status, this, 0).show();
            return false;
        }
    } */

    @Override
    public void onRoutingFailure(RouteException e) {
        if (e != null) {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            Log.e("Routing Error", e.getMessage());
        } else {
            Toast.makeText(this, "Something went wrong, Try again", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRoutingStart() {
        Toast.makeText(this, "Routing Start", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRoutingSuccess(ArrayList<Route> arrayList, int shortestRouteIndex) {
        if (polylines.size() > 0) {
            for (Polyline poly : polylines) {
                poly.remove();
            }
        }

        polylines = new ArrayList<>();
        //add route(s) to the map.
        for (int i = 0; i < arrayList.size(); i++) {

            //In case of more than 5 alternative routes
            int colorIndex = i % COLORS.length;

            PolylineOptions polyOptions = new PolylineOptions();
            polyOptions.color(getResources().getColor(COLORS[colorIndex]));
            polyOptions.width(10 + i * 3);
            polyOptions.addAll(arrayList.get(i).getPoints());
            Polyline polyline = mMap.addPolyline(polyOptions);
            polylines.add(polyline);

        }
    }

    @Override
    public void onRoutingCancelled() {
        Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();
    }

//    RECYLERVIEW ITEM CLICK IMPLEMENTATION

    @Override
    public void onNextButtonClick(View v, int position) {
        recyclerView.smoothScrollToPosition(position + 1);
    }

    @Override
    public void onBackButtonClick(View v, int position) {
        recyclerView.smoothScrollToPosition(position - 1);
    }

    @Override
    public void onImageButtonClick(View v, int position) {
        if (polylines != null) {
            erasePolylines();
        }
        if (destinationLatLng != null) {
            destinationLatLng = null;
        }
        destinationLatLng = new LatLng(list.get(position).getLat(), list.get(position).getLng());
        getRouteToMarker(destinationLatLng);
    }

    private BitmapDescriptor bitmapDescriptorFromVector(Context context) {
        Drawable background = ContextCompat.getDrawable(context, R.drawable.ic_mosque);
        background.setBounds(0, 0, background.getIntrinsicWidth(), background.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(background.getIntrinsicWidth(), background.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        background.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    private void getRouteToMarker(LatLng destinationLatLng) {
        if (destinationLatLng != null && gpsTracker != null) {
            Routing routing = new Routing.Builder()
                    .travelMode(AbstractRouting.TravelMode.DRIVING)
                    .withListener(this)
                    .alternativeRoutes(false)
                    .waypoints(new LatLng(gpsTracker.getLatitude(), gpsTracker.getLongitude()), destinationLatLng)
                    .build();
            routing.execute();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Constants.REQUEST_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
                    mMap.setMyLocationEnabled(true);
                }
            } else {
                onBackPressed();
            }
        }
    }

    private void erasePolylines() {
        for (Polyline line : polylines) {
            line.remove();
        }
        polylines.clear();
    }

}
