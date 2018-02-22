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

/**
 * Created by Sagar on 12/10/16.
 */
public class DialogAdapter extends BaseAdapter {

    Context context;
    Activity activity;
    MyApplication global;
    int selectedIndex = -1;
    private LayoutInflater inflater;

    ArrayList<String> rowList;

    public DialogAdapter(Context context, ArrayList<String> list)
    {
        this.context= context;
        this.rowList= list;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public class ViewHolder
    {
        TextView name;
        ImageView selected;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder holder;


        if (convertView == null) {
            convertView = inflater.inflate(R.layout.treatmentselect, parent, false);
            holder = new ViewHolder();
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        holder.name = (TextView) convertView.findViewById(R.id.name);
        holder.selected = (ImageView) convertView.findViewById(R.id.selected);



            holder.name.setText(rowList.get(position).toString());
            holder.selected.setVisibility(View.INVISIBLE);




        if (selectedIndex == position)
        {
            if(holder.selected.getVisibility() == View.INVISIBLE ) {

                holder.selected.setVisibility(View.VISIBLE);

                Log.e("SalonType","SalonType "+holder.name.getText().toString());



            }
            else
            {

                holder.selected.setVisibility(View.INVISIBLE);
            }
        }

        return convertView;
    }

    @Override
    public int getCount() {
        return rowList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setSelectedIndex(int index){
        selectedIndex = index;
    }

}
