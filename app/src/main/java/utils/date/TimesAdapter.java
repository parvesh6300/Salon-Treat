package utils.date;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

import dcube.com.salonseek.R;
import utils.MyApplication;

/**
 * Created by Sagar on 04/10/16.
 */

public class TimesAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<String> times;
    private LayoutInflater inflater;
    private int currentItemPos;
    int selectedIndex = -1;
    MyApplication global;

    public TimesAdapter(Context context,int resourceId,ArrayList<String> objects){

        mContext=context;
        this.times = objects;

        global= (MyApplication) mContext.getApplicationContext();

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public void setSelectedIndex(int index){
        selectedIndex = index;
    }

    private class ViewHolder {

        private TextView time;
        private ImageView bottom;

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder mViewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.layout_time_item, parent, false);
            mViewHolder = new ViewHolder();
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }


        mViewHolder.time = (TextView) convertView.findViewById(R.id.layout_time);

        mViewHolder.bottom = (ImageView) convertView.findViewById(R.id.bottom);

        mViewHolder.time.setText(times.get(position));

        mViewHolder.bottom = (ImageView) convertView.findViewById(R.id.bottom);


        if (position==0){

            mViewHolder.time.setTextColor(Color.parseColor("#438bc5"));

            mViewHolder.bottom.setVisibility(View.VISIBLE);

        }



        if(selectedIndex == position){

            mViewHolder.time.setTextColor(Color.parseColor("#438bc5"));

            mViewHolder.bottom.setVisibility(View.VISIBLE);

            Calendar calendar=Calendar.getInstance();
            calendar.add(Calendar.MINUTE, position * 15);

       //     SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
        //    String format = sdf.format(calendar.getTime());


            String time=mViewHolder.time.getText().toString().trim();

       //     String format=sdf.format(time);

            global.setTime(time);

            Log.e("Time","Format Time "+time);

        }
        else{

            mViewHolder.time.setTextColor(mContext.getResources().getColor(R.color.colortextblack));

            mViewHolder.bottom.setVisibility(View.INVISIBLE);

        }

        return convertView;

    }


    @Override
    public int getCount() {
        return times.size();
    }

    @Override
    public Object getItem(int position) {
        return times.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


}
