package utils.date;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import dcube.com.salonseek.R;
import utils.CustomDate;
import utils.MyApplication;

/**
 * Created by Sagar on 04/10/16.
 */
public class DateAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<CustomDate> dates;
    private LayoutInflater inflater;
    private int currentItemPos;

    int selectedIndex = -1;

    Calendar calendar=Calendar.getInstance();
    MyApplication global;


    public DateAdapter(Context context,int resourceId,ArrayList<CustomDate> objects){

        mContext=context;
        this.dates = objects;

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public void setSelectedIndex(int index){
        selectedIndex = index;
    }

    private class ViewHolder {

        private TextView monthTextView;
        private TextView dayTextview;
        private TextView dateTextView;
        private ImageView bottom;
        private LinearLayout outerLayout;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder mViewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.layout_date_item, parent, false);
            mViewHolder = new ViewHolder();
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }

        mViewHolder.dateTextView = (TextView) convertView.findViewById(R.id.layout_date_text);
        mViewHolder.dayTextview = (TextView) convertView.findViewById(R.id.layout_date_day);

        mViewHolder.bottom = (ImageView) convertView.findViewById(R.id.bottom);


        mViewHolder.dateTextView.setText(dates.get(position).getDate());
        mViewHolder.dayTextview.setText(dates.get(position).getDay());

        if (position==0){

            mViewHolder.dateTextView.setTextColor(Color.parseColor("#438bc5"));
            mViewHolder.dayTextview.setTextColor(Color.parseColor("#438bc5"));
            mViewHolder.bottom.setVisibility(View.VISIBLE);

        }



        if(selectedIndex == position ){

            mViewHolder.dateTextView.setTextColor(Color.parseColor("#438bc5"));
            mViewHolder.dayTextview.setTextColor(Color.parseColor("#438bc5"));

            mViewHolder.bottom.setVisibility(View.VISIBLE);

            Calendar calendar=Calendar.getInstance();
            calendar.add(Calendar.DATE, position);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-dd-MM");
            String format = sdf.format(calendar.getTime());

            global=(MyApplication)mContext.getApplicationContext();
            Log.e("Date","Format Date "+format);

            global.setDate(format);

        }
        else
        {
            mViewHolder.dateTextView.setTextColor(mContext.getResources().getColor(R.color.colortextblack));
            mViewHolder.dayTextview.setTextColor(mContext.getResources().getColor(R.color.colortextblack));
            mViewHolder.bottom.setVisibility(View.INVISIBLE);
        }

        return convertView;
    }


    @Override
    public int getCount() {
        return dates.size();
    }

    @Override
    public Object getItem(int position) {
        return dates.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


}
