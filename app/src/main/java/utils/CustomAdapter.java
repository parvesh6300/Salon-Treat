package utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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

import dcube.com.salonseek.R;
import dcube.com.salonseek.SalonActivity;
import webservices.GlobalConstants;
import webservices.WebServicesHandler;

public class CustomAdapter extends BaseAdapter{

    Context context;

    Activity activity;
    MyApplication global;

    ArrayList<String>  Rating = new ArrayList<>();
    ArrayList<String>  Review = new ArrayList<>();

    ArrayList<String> SalonName = new ArrayList<>();
    ArrayList<String> address = new ArrayList<>();
    ArrayList<String> lat = new ArrayList<>();
    ArrayList<String> longi = new ArrayList<>();
    ArrayList<String> image_url = new ArrayList<>();
    ArrayList<String> salonID = new ArrayList<>();
    ArrayList<String> open_time = new ArrayList<>();

    String str_open_time,second_time,third_time;

    WebServicesHandler ws;

    int[] imageId = {R.drawable.salonpic ,
            R.drawable.salonpic ,R.drawable.salonpic ,R.drawable.salonpic,
            R.drawable.salonpic,R.drawable.salonpic,R.drawable.salonpic} ;

    private static LayoutInflater inflater = null;


    public CustomAdapter(Activity homeActivity) {

        activity = homeActivity;

        global = (MyApplication) activity.getApplicationContext();

        context = homeActivity.getApplicationContext();
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        try {

            if (global.isFavorite_filter())
            {
                for (HashMap<String, String> hashMap : global.getAl_fav_salon()) {

                    lat.add(hashMap.get(GlobalConstants.LATITUDE));
                    address.add(hashMap.get(GlobalConstants.RES_ADDRESS));
                    longi.add(hashMap.get(GlobalConstants.LONGITUDE));
                    image_url.add(GlobalConstants.SALON_IMAGES_THUMBNAIL_URL + hashMap.get(GlobalConstants.RES_ICON));
                    SalonName.add(hashMap.get(GlobalConstants.FAV_SALON_NAME));
                    salonID.add(hashMap.get(GlobalConstants.RES_ID));
                    Review.add(hashMap.get(GlobalConstants.SALON_REVIEWS));
                    Rating.add(hashMap.get(GlobalConstants.OVERALL_RATING));
                    open_time.add(hashMap.get(GlobalConstants.SALON_OPEN));
                }

            }

            else if (global.isPrev_visit_filter())
            {
                for (HashMap<String, String> hashMap : global.getAl_prev_visit()) {

                    lat.add(hashMap.get(GlobalConstants.LATITUDE));
                    address.add(hashMap.get(GlobalConstants.RES_ADDRESS));
                    longi.add(hashMap.get(GlobalConstants.LONGITUDE));
                    image_url.add(GlobalConstants.SALON_IMAGES_THUMBNAIL_URL + hashMap.get(GlobalConstants.RES_ICON));
                    SalonName.add(hashMap.get(GlobalConstants.PRE_SALON_NAME));
                    salonID.add(hashMap.get(GlobalConstants.RES_ID));
                    Review.add(hashMap.get(GlobalConstants.SALON_REVIEWS));
                    Rating.add(hashMap.get(GlobalConstants.OVERALL_RATING));
                    open_time.add(hashMap.get(GlobalConstants.SALON_OPEN));
//                    close_time.add(hashMap.get(GlobalConstants.SALON_CLOSED));
                }
            }

            else
            {
                for (HashMap<String, String> hashMap : global.getSalonListing()) {

                    lat.add(hashMap.get(GlobalConstants.LATITUDE));
                    address.add(hashMap.get(GlobalConstants.RES_ADDRESS));
                    longi.add(hashMap.get(GlobalConstants.LONGITUDE));
                    image_url.add(GlobalConstants.SALON_IMAGES_THUMBNAIL_URL + hashMap.get(GlobalConstants.RES_ICON));
                    SalonName.add(hashMap.get(GlobalConstants.RES_NAME));
                    salonID.add(hashMap.get(GlobalConstants.RES_ID));
                    Review.add(hashMap.get(GlobalConstants.SALON_REVIEWS));
                    Rating.add(hashMap.get(GlobalConstants.OVERALL_RATING));
                    open_time.add(hashMap.get(GlobalConstants.SALON_OPEN));
//                    close_time.add(hashMap.get(GlobalConstants.SALON_CLOSED));
                }

            }


        } catch (Exception e) {

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
        TextView rating;
        TextView review;
        TextView location;
        TextView tv_open_time,tv_second_time,tv_third_time;
        CustomRatingBar rbar;
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder = new Holder();

        final View rowView;
        rowView = inflater.inflate(R.layout.saloon, null);

        holder.salonimage = (ImageView) rowView.findViewById(R.id.salonimage);
        holder.salonname = (TextView) rowView.findViewById(R.id.salonname);
        holder.rbar = (CustomRatingBar) rowView.findViewById(R.id.ratingbar);
        holder.rating = (TextView) rowView.findViewById(R.id.rating);
        holder.location = (TextView) rowView.findViewById(R.id.location);
        holder.review = (TextView) rowView.findViewById(R.id.review);
        holder.tv_open_time = (TextView) rowView.findViewById(R.id.tv_open_time);
        holder.tv_second_time = (TextView) rowView.findViewById(R.id.tv_second_time);
        holder.tv_third_time = (TextView) rowView.findViewById(R.id.tv_third_time);

        try {

            holder.salonname.setText(SalonName.get(position));
            holder.rbar.setScore(Float.parseFloat(Rating.get(position)));

            holder.rating.setText(Rating.get(position) + " / 5");
            holder.location.setText(address.get(position).trim());
            holder.review.setText(Review.get(position) + " Reviews");

            str_open_time = showOpenTime(open_time.get(position));

            holder.tv_open_time.setText(str_open_time);

            second_time = addTime(open_time.get(position));

            holder.tv_second_time.setText(second_time);

            third_time = addthirdTime(open_time.get(position));

            holder.tv_third_time.setText(third_time);

        }
        catch (Exception e)
        {

        }

        Picasso.with(context) //Context
                .load(image_url.get(position)) //URL/FILE
                .into(holder.salonimage);

        rowView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                global.setSelectedSalonID(salonID.get(position));
                global.setItem_selected(position);

                global.setSelected_salon(position);

                activity.startActivity(new Intent(activity, SalonActivity.class));
                activity.overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
            }
        });

        return rowView;
    }

    public String showOpenTime(String opentime)
    {
        String[] time = opentime.split(":");
        int hour = Integer.parseInt(time[0]);
        int min = Integer.parseInt(time[1]);

        String times;

        if (min == 60)
        {
            min = 00;
        }


        if (hour >12)
        {
            times  = hour+":"+min + " PM";
        }
        else {
            times = hour+":"+min + " AM";
        }

        return times;
    }


    public String addTime(String opentime)
    {
        String[] time = opentime.split(":");
        int hour = Integer.parseInt(time[0]);
        int min = Integer.parseInt(time[1]);

        String times;

        if (min > 45)
        {
            hour = hour + 1;
            min = min - 45;
        }
        else
        {
            min = min+15;
        }

        if (min == 60)
        {
            min = 00;
        }

        if (hour >12)
        {
           times  = hour+":"+min + " PM";
        }
        else
        {
           times = hour+":"+min + " AM";
        }

        return times;

    }


    public String addthirdTime(String opentime)
    {
        String[] time = opentime.split(":");
        int hour = Integer.parseInt(time[0]);
        int min = Integer.parseInt(time[1]);

        String times;

        if (min >= 30)
        {
            hour = hour + 1;
            min = min - 30;
        }
        else
        {
            min = min+30;
        }

        if (min == 60)
        {
            min = 0;
        }

        if (hour >12)
        {
            times  = hour+":"+min + " PM";
        }
        else {
            times = hour+":"+min + " AM";
        }

        return times;

    }


}