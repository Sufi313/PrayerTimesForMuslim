package com.sufi.prayertimes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.sufi.prayertimes.models.PlaceArrayAdapter;
import com.sufi.prayertimes.util.PrayTime;
import com.sufi.prayertimes.util.TimezoneMapper;

import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;


public class SerachCityActivity extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks{

    private String foundCityTimeZone ="";
    private static final String LOG_TAG = SerachCityActivity.class.getSimpleName();
    private static final int GOOGLE_API_CLIENT_ID = 0;
    private AutoCompleteTextView mAutocompleteTextView;
    private TextView mNameTextView;
    private TextView mAddressTextView;
    private TextView mAttTextView;
    private TextView mTimeZoneChangeTV;
    private GoogleApiClient mGoogleApiClient;
    private PlaceArrayAdapter mPlaceArrayAdapter;
    private static final LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(
            new LatLng(37.398160, -122.180831), new LatLng(37.430610, -121.972090));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serach_city);
        mGoogleApiClient = new GoogleApiClient.Builder(SerachCityActivity.this)
                .addApi(Places.GEO_DATA_API)
                .enableAutoManage(this, GOOGLE_API_CLIENT_ID, this)
                .addConnectionCallbacks(this)
                .build();
        mAutocompleteTextView = findViewById(R.id.autoCompleteTextView);
        mAutocompleteTextView.setThreshold(3);
        AutocompleteFilter filter = new AutocompleteFilter.Builder()
                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_CITIES)
                .build();
        mNameTextView = findViewById(R.id.name);
        mAddressTextView = findViewById(R.id.address);
        mAttTextView = findViewById(R.id.att);
        mTimeZoneChangeTV = findViewById(R.id.mTimeZoneChangeTV);

        mTimeZoneChangeTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdjustTimeZone();
            }
        });

        mAutocompleteTextView.setOnItemClickListener(mAutocompleteClickListener);
        mPlaceArrayAdapter = new PlaceArrayAdapter(this, android.R.layout.simple_list_item_1, BOUNDS_MOUNTAIN_VIEW, filter);
        mAutocompleteTextView.setAdapter(mPlaceArrayAdapter);

    }

    private void AdjustTimeZone() {



    }

    private AdapterView.OnItemClickListener mAutocompleteClickListener
            = new AdapterView.OnItemClickListener() {
        @SuppressLint("ClickableViewAccessibility")
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            final PlaceArrayAdapter.PlaceAutocomplete item = mPlaceArrayAdapter.getItem(position);
            final String placeId = String.valueOf(item.placeId);
            Log.i(LOG_TAG, "Selected: " + item.description);
            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi.getPlaceById(mGoogleApiClient, placeId);
            placeResult.setResultCallback(mUpdatePlaceDetailsCallback);
            Log.i(LOG_TAG, "Fetching details for ID: " + item.placeId);
            mTimeZoneChangeTV.setVisibility(View.VISIBLE);
            mAutocompleteTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_sharp_close, 0);
            mAutocompleteTextView.setPadding(15,0,10,0);

            mAutocompleteTextView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    final int DRAWABLE_LEFT = 0;
                    final int DRAWABLE_TOP = 1;
                    final int DRAWABLE_RIGHT = 2;
                    final int DRAWABLE_BOTTOM = 3;

                    if(event.getAction() == MotionEvent.ACTION_UP) {
                        if(event.getRawX() >= (mAddressTextView.getRight() - mAutocompleteTextView.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                            mAutocompleteTextView.setText("");
                            mAutocompleteTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_search, 0, 0, 0);
                            mAutocompleteTextView.setPadding(10,0,0,0);
                            mAutocompleteTextView.setOnTouchListener(null);
                            return true;
                        }
                    }
                    return false;
                }
            });

        }
    };

    private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback
            = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(PlaceBuffer places) {
            if (!places.getStatus().isSuccess()) {
                Log.e(LOG_TAG, "Place query did not complete. Error: " +
                        places.getStatus().toString());
                return;
            }
            // Selecting the first object buffer.
            final Place place = places.get(0);
            CharSequence attributions = places.getAttributions();
            final LatLng location = place.getLatLng();
            mNameTextView.setText(LocalDate.now().toString("EEE dd MMMM YYYY"));
            mAddressTextView.setText(foundCityTimeZone);
//            mAddressTextView.setText(location.latitude + "\n" + location.longitude);
            mAttTextView.setText(place.getAddress());
            foundCityTimeZone = TimezoneMapper.latLngToTimezoneString(location.latitude,location.longitude);
            if (foundCityTimeZone == null && foundCityTimeZone == "" && foundCityTimeZone == "unknown")
            {
                Toast.makeText(SerachCityActivity.this, "An Error Found", Toast.LENGTH_SHORT).show();
                return;
            }
            else
            {
                TimeZone defaultTz = TimeZone.getTimeZone(foundCityTimeZone);
                Calendar defaultCalc = Calendar.getInstance(defaultTz);

                //Get offset from UTC, accounting for DST
                float defaultTzOffsetMs = defaultCalc.get(Calendar.ZONE_OFFSET) + defaultCalc.get(Calendar.DST_OFFSET);
                double timezone = defaultTzOffsetMs / (1000 * 60 * 60);

                String gmt = getString(R.string.gmt,String.valueOf(timezone));
                mAddressTextView.setText(gmt);

                setTimePrayer(location.latitude, location.longitude, timezone);
            }
            if (attributions != null) {
                mAttTextView.setText(Html.fromHtml(attributions.toString()));
            }
        }
    };

    @Override
    public void onConnected(Bundle bundle) {
        mPlaceArrayAdapter.setGoogleApiClient(mGoogleApiClient);
        Log.i(LOG_TAG, "Google Places API connected.");

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e(LOG_TAG, "Google Places API connection failed with error code: "
                + connectionResult.getErrorCode());

        Toast.makeText(this,
                "Google Places API connection failed with error code:" +
                        connectionResult.getErrorCode(),
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onConnectionSuspended(int i) {
        mPlaceArrayAdapter.setGoogleApiClient(null);
        Log.e(LOG_TAG, "Google Places API connection suspended.");
    }

    public void setTimePrayer(double lat, double lng, double cityTimeZone) {

//        Long tsLong = System.currentTimeMillis() / 1000;
//        String ts = tsLong.toString();
//        Log.wtf("TimeStamp------>", ts);


        // Test Prayer times here
        PrayTime prayers = new PrayTime();

        prayers.setTimeFormat(PrayTime.TIME_12);
        prayers.setCalcMethod(PrayTime.KARACHI);
        prayers.setAsrJuristic(PrayTime.HANAFI);
        prayers.setAdjustHighLats(PrayTime.ANGLE_BASED);

        int[] offsets = {0, 0, 0, 0, 0, 0, 0}; // {Fajr,Sunrise,Dhuhr,Asr,Sunset,Maghrib,Isha}
        prayers.tune(offsets);

        Date now = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(now);


        TextView fajr = findViewById(R.id.searchCityFajr);
        TextView dhuhr = findViewById(R.id.searchCityDhuhr);
        TextView asr = findViewById(R.id.searchCityAsr);
        TextView maghrib = findViewById(R.id.searchCityMaghrib);
        TextView isha = findViewById(R.id.searchCityIsha);

        ArrayList<String> prayerTimes = prayers.getPrayerTimes(cal,
                lat, lng, cityTimeZone);

        fajr.setText(prayerTimes.get(0));
        dhuhr.setText(prayerTimes.get(2));
        asr.setText(prayerTimes.get(3));
        maghrib.setText(prayerTimes.get(5));
        isha.setText(prayerTimes.get(6));


//        LinkedHashMap<String, String> prayerTimes = PrayTime.getPrayerTimes(SerachCityActivity.this, 0, lat, lng);

//        fajr.setText(prayerTimes.get("Fajr"));
//        dhuhr.setText(prayerTimes.get("Dhuhr"));
//        asr.setText(prayerTimes.get("Asr"));
//        maghrib.setText(prayerTimes.get("Maghrib"));
//        isha.setText(prayerTimes.get("Isha"));

    }

}