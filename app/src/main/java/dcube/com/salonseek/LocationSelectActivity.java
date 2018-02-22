package dcube.com.salonseek;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import utils.MyApplication;
import webservices.GlobalConstants;
import webservices.WebServicesHandler;


public class LocationSelectActivity  extends FragmentActivity implements PlaceSelectionListener {

    private TextView mPlaceDetailsText;

    private TextView mPlaceAttribution;

    WebServicesHandler ws;

    MyApplication global;

    TextView tv_current_location,tv_location;

    ImageView iv_back;

    PlaceAutocompleteFragment autocompleteFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_location);

        global=(MyApplication) getApplicationContext();

        tv_current_location=(TextView)findViewById(R.id.tv_current_location);

        tv_location=(TextView)findViewById(R.id.tv_location);

        iv_back=(ImageView)findViewById(R.id.back);

        global.al_search_items.remove(GlobalConstants.Search_Location);

        // Retrieve the PlaceAutocompleteFragment.
        autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        // Register a listener to receive callbacks when a place has been selected or an error has
        // occurred.
        autocompleteFragment.setOnPlaceSelectedListener(this);

        tv_current_location.setText(global.getPlace_name());

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                global.setPlace_name(tv_current_location.getText().toString());

                global.al_search_items.put(GlobalConstants.Search_Location,tv_current_location.getText().toString());

                finish();
            }
        });


        tv_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                global.setSrc_lat(global.getLat());
                global.setSrc_long(global.getLang());

                double dbl_lat=0d,dbl_long=0d;

                if (global.getSrc_lat().isEmpty() || global.getSrc_long().isEmpty())
                {
                    Toast.makeText(global, "Error Occured", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    dbl_lat=Double.parseDouble(global.getSrc_lat());
                    dbl_long= Double.parseDouble(global.getSrc_long());
                }


                Geocoder geocoder = new Geocoder(LocationSelectActivity.this, Locale.getDefault());
                List<Address> addresses = null;
                try {
                    addresses = geocoder.getFromLocation(dbl_lat, dbl_long,1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String cityName = addresses.get(0).getAddressLine(0);
                String stateName = addresses.get(0).getAddressLine(1);
                String countryName = addresses.get(0).getAddressLine(2);

                global.setPlace_name(cityName);

                tv_current_location.setText(global.getPlace_name());

                Log.e("Location","Location,"+global.getPlace_name());

                autocompleteFragment.setText("");
            }
        });

    }

    /**
     * Callback invoked when a place has been selected from the PlaceAutocompleteFragment.
     */
    @Override
    public void onPlaceSelected(Place place) {

        Log.i("tag", "Place Selected: " + place.getName());

        Log.v("lat long" , place.getLatLng().toString());

        String lat_long= place.getLatLng().toString();

        String[] location= lat_long.substring(lat_long.indexOf("(") +1, lat_long.length()-2).split(",");

        String lat=location[0];
        String lng= location[1];

        global.setPlace_name(place.getName().toString());

        tv_current_location.setText(global.getPlace_name());

        global.setSrc_lat(lat);
        global.setSrc_long(lng);

        Log.e("Lat","Lat "+global.getSrc_lat());
        Log.e("Long","Long "+global.getSrc_long());

    }

    /**
     * Callback invoked when PlaceAutocompleteFragment encounters an error.
     */
    @Override
    public void onError(Status status) {
        Log.e("error", "onError: Status = " + status.toString());

        Toast.makeText(this, "Place selection failed: " + status.getStatusMessage(),
                Toast.LENGTH_SHORT).show();
    }

}
