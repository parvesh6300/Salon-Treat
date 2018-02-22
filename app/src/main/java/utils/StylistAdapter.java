package utils;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

import dcube.com.salonseek.R;
import webservices.GlobalConstants;

public class StylistAdapter extends BaseAdapter {

    Context context;
    Activity activity;

    MyApplication global;

    private int selectedIndex = -1;

    public ArrayList<String>  Name = new ArrayList<>();
    public ArrayList<String> Type = new ArrayList<>();
    public ArrayList<String> offer = new ArrayList<>();
    public ArrayList<String> image = new ArrayList<>();
    public ArrayList<String> id = new ArrayList<>();


    private static LayoutInflater inflater = null;

    public StylistAdapter(Activity activity)
    {
        this.activity = activity;

        global = (MyApplication) activity.getApplicationContext();

        context = activity.getApplicationContext();

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        try
        {




            for (HashMap<String, String> hashMap : global.getStylistListing())
            {

                Name.add(hashMap.get(GlobalConstants.AVL_STL_TITLE));
                Type.add(hashMap.get(GlobalConstants.AVL_STL_TYPE));
                offer.add(hashMap.get(GlobalConstants.AVL_STL_OFFER));
                id.add(hashMap.get(GlobalConstants.AVL_STL_ID));
                image.add(GlobalConstants.SALON_IMAGES_THUMBNAIL_URL + hashMap.get(GlobalConstants.AVL_STL_IMAGE));


            }


        }
        catch (Exception e){
            Log.e("StylistAdapter",""+e.getMessage());
        }

    }


    public class Holder
    {
        TextView name;
        TextView type;
        TextView offer;
        ImageView selected;
        ImageView stylist_image;
        RelativeLayout backview;
    }



    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return  Name.size();
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
    public View getView(int position, View convertView, ViewGroup parent) {

        Holder holder = new Holder();

        convertView = inflater.inflate(R.layout.stlylist, parent, false);

        holder.name = (TextView) convertView.findViewById(R.id.name);
        holder.type = (TextView) convertView.findViewById(R.id.type);
        holder.offer = (TextView) convertView.findViewById(R.id.offer);
        holder.selected = (ImageView) convertView.findViewById(R.id.selected);
        holder.stylist_image = (ImageView) convertView.findViewById(R.id.stylist);
        holder.backview = (RelativeLayout) convertView.findViewById(R.id.backview);

        holder.name.setText(Name.get(position));
        holder.type.setText(Type.get(position));
        holder.offer.setText(offer.get(position) + " % off");

        Picasso.with(context) //Context
                .load(image.get(position)) //URL/FILE
                .into(holder.stylist_image);


//        if(position == 0)
//        {
//            holder.stylist_image.setVisibility(View.GONE);
//        }
//        else
//        {
//            Picasso.with(context) //Context
//                    .load(image.get(position)) //URL/FILE
//                    .into(holder.stylist_image);
//        }

        if(selectedIndex == position)
        {
            holder.name.setTextColor(context.getResources().getColor(R.color.white));
            holder.type.setTextColor(context.getResources().getColor(R.color.white));
            holder.offer.setTextColor(context.getResources().getColor(R.color.white));

            holder.backview.setBackgroundColor(context.getResources().getColor(R.color.colorblue));
        }
        else
        {
            holder.name.setTextColor(context.getResources().getColor(R.color.black));
            holder.type.setTextColor(context.getResources().getColor(R.color.graytext));
            holder.offer.setTextColor(context.getResources().getColor(R.color.graytext));

            holder.backview.setBackgroundColor(context.getResources().getColor(R.color.white));
        }

        return convertView;

    }

    public void setSelectedIndex(int index){
        selectedIndex = index;
    }


}
