package utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

import dcube.com.salonseek.AppointmentActivity;
import dcube.com.salonseek.R;
import webservices.GlobalConstants;

public class AppointmentAdapter extends BaseAdapter {

    Context context;
    Activity activity;

    MyApplication global;

    ArrayList<String> SalonName = new ArrayList<>();
    ArrayList<String> Time = new ArrayList<>();

    ArrayList<String>  Rating = new ArrayList<>();
    ArrayList<String>  Review = new ArrayList<>();

    ArrayList<String> address = new ArrayList<>();
    ArrayList<String> lat = new ArrayList<>();
    ArrayList<String> longi = new ArrayList<>();
    ArrayList<String> image_url = new ArrayList<>();
    ArrayList<String> salonID = new ArrayList<>();

    private static LayoutInflater inflater = null;

    public AppointmentAdapter(Activity activity){

        this.activity = activity;

        context = activity.getApplicationContext();
        global = (MyApplication) activity.getApplicationContext();

        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        Log.v("ac" , activity.toString());

        try {

            for (HashMap<String, String> hashMap : global.getBookedListing()) {

                SalonName.add(hashMap.get(GlobalConstants.BOOKED_SALON_NAME));
                image_url.add(GlobalConstants.SALON_IMAGES_THUMBNAIL_URL + hashMap.get(GlobalConstants.BOOKED_SALON_PROFILE_IMAGE));
                Time.add(hashMap.get(GlobalConstants.BOOK_DATE_TIME));
            }

        }catch(Exception e)
        {

        }

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return SalonName.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder
    {
        ImageView salonimage;
        TextView salonname;
        TextView time;
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder = new Holder();

        final View rowView;

        rowView = inflater.inflate(R.layout.appointment, parent , false);

        holder.salonimage = (ImageView) rowView.findViewById(R.id.salonimage);
        holder.salonname = (TextView) rowView.findViewById(R.id.salonname);
        holder.time = (TextView) rowView.findViewById(R.id.time);

        Picasso.with(context) //Context
                .load(image_url.get(position)) //URL/FILE
                .into(holder.salonimage);

        holder.salonname.setText(SalonName.get(position));
        holder.time.setText(Time.get(position));

        rowView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                activity.startActivity(new Intent(activity , AppointmentActivity.class));
                activity.overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);

                global.setItem_selected(position);
            }
        });

        return rowView;
    }
}