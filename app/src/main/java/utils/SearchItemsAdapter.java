package utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import dcube.com.salonseek.HomeFragment;
import dcube.com.salonseek.R;
import webservices.GlobalConstants;
import webservices.WebServicesHandler;

/**
 * Created by Sagar on 15/11/16.
 */
public class SearchItemsAdapter extends BaseAdapter {

    public final String TAG = "SearchItemsAdapter";

    private static LayoutInflater inflater = null;

    Activity activity;

    MyApplication global;

    ArrayList<String> al_search;

    CustomAdapter adapter;

    HomeFragment homeFragment;

    String str_date_time,str_location,str_salon_name;

    //   HashMap<String,String> al_search = new HashMap<>();

    WebServicesHandler ws;

    public SearchItemsAdapter(Activity a, HomeFragment fragment) {

        this.activity = a;
     //   this.homeFragment = fragment;

        homeFragment = new HomeFragment();

        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        global = (MyApplication) a.getApplicationContext();

        al_search = new ArrayList<>();

        try {

            if (global.al_treatment_name.size() > 0)
            {
                for (int i = 0; i < global.al_treatment_name.size(); i++)
                {
                    al_search.add(global.al_treatment_name.get(i));
                }
            }

            str_date_time = global.al_search_items.get(GlobalConstants.Search_DATE_TIME);
            str_salon_name = global.al_search_items.get(GlobalConstants.Search_Salon_Name);
            str_location = global.al_search_items.get(GlobalConstants.Search_Location);

            if (str_date_time != null && !str_date_time.isEmpty() && !str_date_time.equals("null"))
            {
                al_search.add(str_date_time);
            }
            if (str_salon_name != null && !str_salon_name.isEmpty() && !str_salon_name.equals("null"))
            {
                al_search.add(str_salon_name);
            }
            if (str_location != null && !str_location.isEmpty() && !str_location.equals("null"))
            {
                al_search.add(str_location);
            }

            Log.e("AL_search", "Size " + al_search.size());

        } catch (Exception e) {

        }

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        Holder holder = new Holder();

        View rowView;
        rowView = inflater.inflate(R.layout.search_items, null);

        holder.tv_items = (TextView) rowView.findViewById(R.id.tv_items);
        holder.iv_cross = (ImageView) rowView.findViewById(R.id.iv_cross);

        try {

            holder.tv_items.setText(al_search.get(position));
          //  Log.i("SearchItem", "SearchItem " + al_search.get(position));

        } catch (Exception e) {

        }


        holder.iv_cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (global.al_treatment_name.size() > 0)
                {
                    if (position < global.al_treatment_name.size())
                    {
                        global.al_treatment_id.remove(position);
                        global.al_treatment_name.remove(position);
                    }
                }

                if (global.al_search_items.size() > 0 )
                {

                    if (position >= global.al_treatment_name.size())
                    {
                        String array_list_value = al_search.get(position);

                        String location = global.al_search_items.get(GlobalConstants.Search_Location);
                        String date_time = global.al_search_items.get(GlobalConstants.Search_DATE_TIME);
                        String salon_name = global.al_search_items.get(GlobalConstants.Search_Salon_Name);

                        if (array_list_value.equalsIgnoreCase(location))
                        {
                            global.al_search_items.remove(GlobalConstants.Search_Location);
                            Log.i("SearchAdapter","global.getLat() "+global.getLat());
                            Log.i("SearchAdapter","global.getLang() "+global.getLang());
                            global.setSrc_lat(global.getLat());
                            global.setSrc_long(global.getLang());
                            Log.e("Location","Removed");
                        }
                        if (array_list_value.equalsIgnoreCase(date_time))
                        {
                            global.al_search_items.remove(GlobalConstants.Search_DATE_TIME);
                            Log.e("Datetime","Removed");
                        }
                        if (array_list_value.equalsIgnoreCase(salon_name))
                        {
                            global.al_search_items.remove(GlobalConstants.Search_Salon_Name);
                            global.al_search_items.remove(GlobalConstants.Search_SALON_ID);
                            Log.e("Salon","Removed");
                        }
                    }

                }

                al_search.remove(position);

                new AsyncTaskRunner().execute();

            }
        });

        return rowView;
    }

    @Override
    public int getCount() {
        return al_search.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    public class Holder {
        TextView tv_items;
        ImageView iv_cross;
    }


    private class AsyncTaskRunner extends AsyncTask<String, String, String> {

        ProgressDialog progressDialog;
        private String resp;

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(activity, "Refreshing List", "Wait!");
        }

        @Override
        protected String doInBackground(String... params) {

            ws.SearchSlider(activity, false);
            return resp;

        }

        @Override
        protected void onPostExecute(String result) {
            // execution of result of Long time consuming operation

            progressDialog.dismiss();

            notifyDataSetChanged();

            homeFragment.setLayout(activity);

        }

    }


}
