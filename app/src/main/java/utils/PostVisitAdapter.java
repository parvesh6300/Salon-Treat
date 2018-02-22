package utils;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import dcube.com.salonseek.R;
import webservices.GlobalConstants;

/**
 * Created by Sagar on 21/11/16.
 */
public class PostVisitAdapter extends BaseAdapter {

    private static LayoutInflater inflater = null;
    Context context;
    Activity activity;
    MyApplication global;
    ArrayList<String> al_salon_name = new ArrayList<>();
    ArrayList<String> al_salon_distance = new ArrayList<>();

    public PostVisitAdapter(Activity mactivity, ArrayList<String> salon_name, ArrayList<String> salon_distance) {
        this.activity = mactivity;

        context = activity.getApplicationContext();
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        al_salon_name = new ArrayList<>();
        al_salon_distance = new ArrayList<>();

//        al_salon_name = salon_name;
//        al_salon_distance = salon_distance;

        addValues();

        global = (MyApplication) context.getApplicationContext();
    }

    public class Holder {
        TextView tv_salon_name;
        TextView tv_salon_distance;
        ImageView iv_marker;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        Holder holder = new Holder();

        View rowView;
        rowView = inflater.inflate(R.layout.post_visit_list, parent, false);

        holder.tv_salon_name = (TextView) rowView.findViewById(R.id.tv_salon_name);
        holder.tv_salon_distance = (TextView) rowView.findViewById(R.id.tv_salon_distance);
        holder.iv_marker = (ImageView) rowView.findViewById(R.id.iv_marker);

//        holder.tv_salon_name.setText(al_salon_name.get(position)+" Salon");
//        holder.tv_salon_distance.setText(al_salon_distance.get(position)+" km");

        holder.tv_salon_name.setText(global.getAl_prev_visit().get(position).get(GlobalConstants.PRE_SALON_NAME));
        holder.tv_salon_distance.setText(al_salon_distance.get(position) + " km");

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                global.setSrc_salon_id(global.getAl_prev_visit().get(position).get(GlobalConstants.PRE_SALON_ID));
                Log.e("Salon id","Salon id "+global.getSrc_salon_id());

            }
        });


        return rowView;

    }

    @Override
    public int getCount() {
        return global.getAl_prev_visit().size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void addValues() {
        al_salon_name.add("AhAna");
        al_salon_name.add("Anmol");
        al_salon_name.add("Sk");

        al_salon_distance.add("5.3");
        al_salon_distance.add("2.3");
        al_salon_distance.add("4.4");
    }



}
