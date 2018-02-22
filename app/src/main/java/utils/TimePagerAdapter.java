package utils;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import dcube.com.salonseek.R;

/**
 * Created by yadi on 25/07/16.
 */
public class TimePagerAdapter extends PagerAdapter {

    private Context mContext;
    private ArrayList<String> times;
    private LayoutInflater inflater;
    private int currentItemPos;

    public TimePagerAdapter(Context context, int resourceId,
                                  ArrayList<String> objects) {
        this.mContext = context;
        this.times = objects;

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setCurrentItem(int item) {
        this.currentItemPos = item;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ViewHolder holder;

        String time = this.times.get(position);

        View convertView = inflater.inflate(R.layout.layout_time_item,
                container, false);
        holder = new ViewHolder();

        holder.time = (TextView) convertView
                .findViewById(R.id.layout_time);

        holder.bottom = (ImageView) convertView
                .findViewById(R.id.bottom);

        //holder.monthTextView = (TextView) convertView.findViewById(R.id.layout_date_month);

        //holder.outerLayout = (LinearLayout) convertView.findViewById(R.id.layout_date_item_outer_layout);

        convertView.setTag(Integer.valueOf(position));
        holder.time.setText(time);

        /*convertView.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {

                HomeFragment.newInstance().onPagerItemClick(v,(Integer) v.getTag());
                // TODO Auto-generated method stub
                ((DemoActivity) mContext).onPagerItemClick(v,
                        (Integer) v.getTag());
            }
        });*/

        if (position == currentItemPos) {

            //holder.outerLayout.setBackgroundColor(Color.parseColor("#EC522C"));

            holder.time.setTextColor(Color.parseColor("#438bc5"));
            holder.bottom.setVisibility(View.VISIBLE);

        } else {

            //holder.outerLayout.setBackgroundColor(Color.parseColor("#ffffff"));
        }

        ((ViewPager) container).addView(convertView);

        return convertView;
    }

    private class ViewHolder {

        private TextView time;
        private ImageView bottom;

    }

    @Override
    public int getCount() {
        return times.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        // TODO Auto-generated method stub
        return view == (object);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        ((ViewPager) container).removeView(view);
        view = null;
    }

    public float getPageWidth(int position) {
        return 0.2f;
    }
}