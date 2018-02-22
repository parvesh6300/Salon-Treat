package utils;

import android.app.Activity;
import android.content.Context;
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

public class TreatmentSelectAdapter extends BaseAdapter{

    Context context;
    Activity activity;
    MyApplication global;
    int selectedIndex = -1;

    ArrayList<String> Name = new ArrayList<>();
    ArrayList<String> al_treat_id=new ArrayList<>();

    public void setSelectedIndex(int index){
        selectedIndex = index;
    }

    private static LayoutInflater inflater = null;
    public TreatmentSelectAdapter(Activity activity) {

        this.activity = activity;

        global = (MyApplication) activity.getApplicationContext();

        context = activity.getApplicationContext();
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        Name.clear();
        al_treat_id.clear();

        String str = "";

        try {
            for (HashMap<String, String> hashMap : global.getTreatmentListing()) {

                Name.add(hashMap.get(GlobalConstants.TREATMENTS_Name));
                al_treat_id.add(hashMap.get(GlobalConstants.TREATMENTS_ID));

            }
        } catch (Exception e) {
            e.printStackTrace();
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

    public class Holder
    {
        TextView name;
        ImageView selected;
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        final Holder holder = new Holder();

        final View rowView;
        rowView = inflater.inflate(R.layout.treatmentselect, parent, false);

        holder.name = (TextView) rowView.findViewById(R.id.name);
        holder.selected = (ImageView) rowView.findViewById(R.id.selected);

        holder.name.setText(Name.get(position));
        holder.selected.setVisibility(View.INVISIBLE);

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(holder.selected.getVisibility() == View.INVISIBLE ) {

                    holder.selected.setVisibility(View.VISIBLE);

                    global.setTreatmentID(al_treat_id.get(position));
                    global.setTreatmentName(holder.name.getText().toString());

                   // Name.get(position)
                    global.al_treatment_name.add(holder.name.getText().toString());
                    global.al_treatment_id.add(al_treat_id.get(position));

//                    global.al_search_items.put(GlobalConstants.Search_TREATMENT_ID,al_treat_id.get(position));
//                    global.al_search_items.put(GlobalConstants.Search_Treatment_Name,Name.get(position));

                }
                else
                {
                    global.al_treatment_name.remove(Name.get(position));
                    global.al_treatment_id.remove(al_treat_id.get(position));

//                    global.al_search_items.remove(GlobalConstants.Search_TREATMENT_ID);
//                    global.al_search_items.remove(GlobalConstants.Search_SALON_ID)
                    holder.selected.setVisibility(View.INVISIBLE);
                }
            }
        });

        return rowView;
    }
}