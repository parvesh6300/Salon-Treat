package dcube.com.salonseek;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import utils.CustomRatingBar;
import utils.MyApplication;
import webservices.GlobalConstants;
import webservices.WebServicesHandler;

public class AppointmentActivity extends Activity implements OnMapReadyCallback {

    // Google Map
    private GoogleMap mMap;

    TextView salonName;
    TextView salonAddress;
    TextView treatmentType;
    TextView treatmentPrice;
    TextView treatmentTime;
    TextView treatmentStylist;

    CustomRatingBar ratingbar;
    ImageView salonImage,iv_cancel;

    RelativeLayout mapcontainer;

    int n;
    MyApplication global;

    WebServicesHandler ws;

    float lat, longi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_appointment);

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        global = (MyApplication) getApplicationContext();

        n = global.getItem_selected();

        salonName = (TextView) findViewById(R.id.salonname);
        salonAddress = (TextView) findViewById(R.id.location);
        treatmentType = (TextView) findViewById(R.id.type);
        treatmentTime = (TextView) findViewById(R.id.time);
        treatmentPrice = (TextView) findViewById(R.id.price);
        treatmentStylist = (TextView) findViewById(R.id.stylist);

        ratingbar = (CustomRatingBar) findViewById(R.id.ratingbar);
        salonImage = (ImageView) findViewById(R.id.salonimage);
        iv_cancel = (ImageView) findViewById(R.id.iv_cancel);

        salonName.setText(global.getBookedListing().get(n).get(GlobalConstants.BOOKED_SALON_NAME));
        salonAddress.setText(global.getBookedListing().get(n).get(GlobalConstants.BOOKED_SALON_ADDRESS).trim());
        treatmentType.setText(global.getBookedListing().get(n).get(GlobalConstants.BOOKED_TREATMENTS));
        treatmentPrice.setText("$"+global.getBookedListing().get(n).get(GlobalConstants.BOOKED_TREATMENT_TOTAL) + ".00");
        treatmentTime.setText(global.getBookedListing().get(n).get(GlobalConstants.BOOK_DATE_TIME));
        treatmentStylist.setText(global.getBookedListing().get(n).get(GlobalConstants.BOOKED_SALON_STYLIST));

        ratingbar.setScore(Float.parseFloat(global.getBookedListing().get(n).get(GlobalConstants.OVERALL_RATING)));

        lat = Float.parseFloat(global.getBookedListing().get(n).get(GlobalConstants.BOOKED_SALON_LAT));
        longi = Float.parseFloat(global.getBookedListing().get(n).get(GlobalConstants.BOOKED_SALON_LONG));

        mapcontainer = (RelativeLayout) findViewById(R.id.mapcontainer);

        mapcontainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(AppointmentActivity.this, FullMapActivity.class));
            }
        });

        iv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                ConfirmDialog dialog = new ConfirmDialog(AppointmentActivity.this);
                dialog.show();


            }
        });

        Picasso.with(AppointmentActivity.this) //Context
                .load(GlobalConstants.SALON_IMAGES_THUMBNAIL_URL + global.getBookedListing().get(n).get(GlobalConstants.BOOKED_SALON_PROFILE_IMAGE)) //URL/FILE
                .into(salonImage);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        LatLng salon = new LatLng(lat, longi);

        mMap.addMarker(new MarkerOptions()
                .title(global.getBookedListing().get(n).get(GlobalConstants.BOOKED_SALON_NAME))
                .snippet(global.getBookedListing().get(n).get(GlobalConstants.BOOKED_SALON_ADDRESS))
                .position(salon)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.pin)));

        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(salon,13));

    }


    public class CancelBooking extends AsyncTask<String, String, String> {

        private String resp;
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {

            progressDialog = ProgressDialog.show(AppointmentActivity.this, "Updating", "Wait!");
        }

        @Override
        protected String doInBackground(String... params) {

            ws.CancelBooking(AppointmentActivity.this,global.getBookedListing().get(n).get(GlobalConstants.BOOKING_ID));

            return resp;
        }

        @Override
        protected void onPostExecute(String result) {
            // execution of result of Long time consuming operation
            progressDialog.dismiss();

            if (global.getBookedListing().size() > 0)
            {
                global.getBookedListing().clear();
            }

            finish();
        }


    }


    public class ConfirmDialog extends Dialog
    {

        TextView tv_cancel,tv_ok;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            requestWindowFeature(Window.FEATURE_NO_TITLE);

            setContentView(R.layout.cancel_aptmt_dialog);

            tv_ok = (TextView) findViewById(R.id.tv_ok);
            tv_cancel = (TextView) findViewById(R.id.tv_cancel);

            tv_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });

            tv_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    dismiss();
                    new CancelBooking().execute();
                }
            });


        }

        public ConfirmDialog(Context context) {
            super(context);
        }

    }



}