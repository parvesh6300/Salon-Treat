package utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import dcube.com.salonseek.R;
import dcube.com.salonseek.SalonActivity;
import dcube.com.salonseek.WebDirectActivity;
import de.hdodenhof.circleimageview.CircleImageView;
import webservices.GlobalConstants;
import webservices.WebServicesHandler;


public class FavouritesAdapter extends BaseAdapter {

    private static LayoutInflater inflater = null;
    Context context;
    Activity activity;
    MyApplication global;
    WebServicesHandler ws;
    ArrayList<String> SalonName = new ArrayList<>();
    ArrayList<String> Address = new ArrayList<>();
    String url;
    int[] imageId = {R.drawable.salonpic};

    public FavouritesAdapter(Activity activity) {

        this.activity = activity;

        context = activity.getApplicationContext();
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        global = (MyApplication) this.activity.getApplicationContext();

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return global.getAl_fav_salon().size();
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

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder = new Holder();

        final View rowView;

        rowView = inflater.inflate(R.layout.favourites, parent, false);

        holder.salonimage = (CircleImageView) rowView.findViewById(R.id.salonimage);
        holder.salonname = (TextView) rowView.findViewById(R.id.salonname);
        holder.address = (TextView) rowView.findViewById(R.id.address);

        holder.rel_salon_type = (RelativeLayout) rowView.findViewById(R.id.rel_salon_type);
        holder.rel_call = (RelativeLayout) rowView.findViewById(R.id.rel_call);
        holder.rel_get_direct = (RelativeLayout) rowView.findViewById(R.id.rel_get_direct);
        holder.rel_rebook = (RelativeLayout) rowView.findViewById(R.id.rel_rebook);

        holder.iv_fav_icon = (ImageView) rowView.findViewById(R.id.iv_fav_icon);

        String url = GlobalConstants.SALON_IMAGES_URL + global.getAl_fav_salon().get(position).get(GlobalConstants.FAV_SALON_IMG);

        Picasso.with(context).load(url).into(holder.salonimage);

        holder.salonname.setText(global.getAl_fav_salon().get(position).get(GlobalConstants.FAV_SALON_NAME));
        holder.address.setText(global.getAl_fav_salon().get(position).get(GlobalConstants.FAV_SALON_ADDRESS));


        holder.rel_salon_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                global.setSelectedSalonID(global.getAl_fav_salon().get(position).get(GlobalConstants.FAV_SALON_ID));

                activity.startActivity(new Intent(activity, SalonActivity.class));
                activity.overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
            }
        });


        holder.rel_get_direct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(activity, WebDirectActivity.class);
                Bundle mBundle = new Bundle();
                mBundle.putBoolean("isFavorite", true);
                mBundle.putInt("position", position);
                i.putExtras(mBundle);
                activity.startActivity(i);
                activity.overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);


            }
        });


        holder.rel_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String contact = global.getAl_fav_salon().get(position).get(GlobalConstants.FAV_SALON_CONTACT);

                Uri number = Uri.parse("tel:" + contact);
                Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
                activity.startActivity(callIntent);
            }
        });

        holder.rel_rebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                global.setSelectedSalonID(global.getAl_fav_salon().get(position).get(GlobalConstants.FAV_SALON_ID));
                activity.startActivity(new Intent(activity, SalonActivity.class));
                activity.overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);

            }
        });


        holder.iv_fav_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                global.setUser_id(global.getLoginListing().get(0).get(GlobalConstants.USER_ID));
                global.setMake_salon_fav_id(global.getAl_fav_salon().get(position).get(GlobalConstants.FAV_SALON_ID));

                new AsyncTaskRunner().execute();
            }
        });

        return rowView;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    public class Holder {
        ImageView iv_fav_icon;
        TextView salonname;
        TextView address;

        CircleImageView salonimage;

        RelativeLayout rel_salon_type, rel_call, rel_get_direct, rel_rebook;

    }

    public class AsyncTaskRunner extends AsyncTask<String, String, String> {

        ProgressDialog progressDialog;
        private String resp;

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(activity, "Refreshing List", "Wait!");
        }

        @Override
        protected String doInBackground(String... params) {

            ws.MakeSalonUnFav(activity);

            ws.GetFavoriteSalon(activity);

            return resp;

        }

        @Override
        protected void onPostExecute(String result) {
            // execution of result of Long time consuming operation

            if (progressDialog.isShowing()) {
                progressDialog.cancel();
            }

            notifyDataSetChanged();

        }

    }


}