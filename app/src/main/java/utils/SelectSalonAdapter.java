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

public class SelectSalonAdapter extends BaseAdapter{

    Context context;
    Activity activity;
    MyApplication global;

    ArrayList<String> Name = new ArrayList<>();
    ArrayList<String> Id = new ArrayList<>();

    private int selectedIndex = -1;

    private static LayoutInflater inflater = null;

    public SelectSalonAdapter(Activity activity) {

        this.activity = activity;

        global = (MyApplication) activity.getApplicationContext();

        context = activity.getApplicationContext();
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        Name.clear();

        try{

            for (HashMap<String, String> hashMap : global.getSalonListing()) {

                Name.add(hashMap.get(GlobalConstants.Search_Salon_Name));
                Id.add(hashMap.get(GlobalConstants.RES_ID));
            }

        }
        catch (Exception e) {

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
        rowView = inflater.inflate(R.layout.salonselect, parent, false);

        holder.name = (TextView) rowView.findViewById(R.id.name);
        holder.selected = (ImageView) rowView.findViewById(R.id.selected);

        holder.name.setText(Name.get(position));

        holder.selected.setVisibility(View.INVISIBLE);



        /*

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(holder.selected.getVisibility() == View.INVISIBLE )
                {
                    holder.selected.setVisibility(View.VISIBLE);

                    Log.e("SalonName","SalonName "+holder.name.getText().toString());

                    global.al_search_salon.add(holder.name.getText().toString());
                    global.al_search_salon_id.add(Id.get(position));

                    global.setSrc_salon_id(Id.get(position));
                    global.setSrc_salon_name(Name.get(position));

                    global.al_search_items.put(GlobalConstants.Search_SALON_ID,Id.get(position));
                    global.al_search_items.put(GlobalConstants.Search_Salon_Name,Name.get(position));

                }
                else
                {
                    global.setSrc_salon_id("");
                    global.setSrc_salon_name("");

                    global.al_search_salon.remove(Name.get(position));
                    global.al_search_salon_id.remove(Id.get(position));

                    global.al_search_items.remove(GlobalConstants.Search_SALON_ID);
                    global.al_search_items.remove(GlobalConstants.Search_Salon_Name);

                    holder.selected.setVisibility(View.INVISIBLE);

                }


            }
        });
*/

        return rowView;
    }

    public void setSelectedIndex(int index){
        selectedIndex = index;
    }



}