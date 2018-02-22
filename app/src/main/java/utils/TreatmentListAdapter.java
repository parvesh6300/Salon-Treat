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
import java.util.HashMap;

import dcube.com.salonseek.R;
import webservices.GlobalConstants;

public class TreatmentListAdapter extends BaseAdapter {

    private static LayoutInflater inflater = null;
    Context context;
    Activity activity;
    MyApplication global;

    ArrayList<String> Name = new ArrayList<>();
    ArrayList<String> Type = new ArrayList<>();
    ArrayList<String> Price = new ArrayList<>();
    ArrayList<String> Treatmentid = new ArrayList<>();

    ArrayList<String> selected_Treatment_id;
    ArrayList<String> selected_Treatment_price;
    ArrayList<String> selected_Treatment_name;

    public TreatmentListAdapter(Activity activity) {

        this.activity = activity;

        global = (MyApplication) activity.getApplicationContext();

        context = activity.getApplicationContext();
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        selected_Treatment_id = new ArrayList<>();
        selected_Treatment_price = new ArrayList<>();
        selected_Treatment_name = new ArrayList<>();

        try {
            for (HashMap<String, String> hashMap : global.getNormalTreatmentListing()) {

                Name.add(hashMap.get(GlobalConstants.TITLE));
                Type.add(hashMap.get(GlobalConstants.DESCRIPTION));
                Price.add(hashMap.get(GlobalConstants.PRICE));
                Treatmentid.add(hashMap.get(GlobalConstants.TREATMENT_ID));
            }
        } catch (Exception e) {
        }
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return Name.size();
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
        final Holder holder = new Holder();

        global = (MyApplication) context.getApplicationContext();

        final View rowView;
        rowView = inflater.inflate(R.layout.treatment, parent, false);

        holder.name = (TextView) rowView.findViewById(R.id.name);
        holder.type = (TextView) rowView.findViewById(R.id.type);
        holder.price = (TextView) rowView.findViewById(R.id.price);
        holder.selected = (ImageView) rowView.findViewById(R.id.selected);

        holder.name.setText(Name.get(position));
        holder.type.setText(Type.get(position));
        holder.price.setText("$ " + Price.get(position));

        holder.selected.setVisibility(View.INVISIBLE);


        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    if (selected_Treatment_id.contains(Treatmentid.get(position))) {

                        selected_Treatment_id.remove(Treatmentid.get(position));
                        selected_Treatment_price.remove(Price.get(position));
                        selected_Treatment_name.remove(Name.get(position));

                        global.setTreatmentSelected(false);

                    } else {

                        selected_Treatment_id.add(Treatmentid.get(position));
                        selected_Treatment_price.add(Price.get(position));
                        selected_Treatment_name.add(Name.get(position));

                        global.setTreatmentSelected(true);
                    }
                } catch (Exception e) {
                }

                global.setSelected_treatment_id(selected_Treatment_id);
                global.setSelected_treatment_price(selected_Treatment_price);
                global.setSelected_treatment_name(selected_Treatment_name);

                Log.e("GLobal", "id " + global.getSelected_treatment_id());

                if (holder.selected.getVisibility() == View.INVISIBLE && global.readonly) {

                    holder.selected.setVisibility(View.VISIBLE);
                    rowView.setBackgroundColor(rowView.getResources().getColor(R.color.colorblue));
                    holder.name.setTextColor(rowView.getResources().getColor(R.color.white));
                    holder.type.setTextColor(rowView.getResources().getColor(R.color.white));
                    holder.price.setTextColor(rowView.getResources().getColor(R.color.white));

                } else {
                    holder.selected.setVisibility(View.INVISIBLE);
                    holder.name.setTextColor(rowView.getResources().getColor(R.color.black));
                    holder.type.setTextColor(rowView.getResources().getColor(R.color.graytext));
                    holder.price.setTextColor(rowView.getResources().getColor(R.color.black));
                    rowView.setBackgroundColor(rowView.getResources().getColor(R.color.colorPrimary));
                }
            }
        });

        return rowView;
    }

    public class Holder {
        TextView name;
        TextView type;
        TextView price;
        ImageView selected;
    }
}