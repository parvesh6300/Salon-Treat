package utils;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import dcube.com.salonseek.R;
/**
 * Created by yadi on 25/07/16.
 */
public class BookNowDateListAdapter extends PagerAdapter {

    private Context mContext;
    private ArrayList<CustomDate> dates;
    private LayoutInflater inflater;
    private int currentItemPos;

    public BookNowDateListAdapter(Context context, int resourceId,
                                  ArrayList<CustomDate> objects) {
        this.mContext = context;
        this.dates = objects;

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setCurrentItem(int item) {
        this.currentItemPos = item;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ViewHolder holder;

        CustomDate date = this.dates.get(position);

        View convertView = inflater.inflate(R.layout.layout_date_item,
                container, false);
        holder = new ViewHolder();

        holder.dateTextView = (TextView) convertView
                .findViewById(R.id.layout_date_text);
        holder.dayTextview = (TextView) convertView
                .findViewById(R.id.layout_date_day);

        holder.bottom = (ImageView) convertView
                .findViewById(R.id.bottom);

        //holder.monthTextView = (TextView) convertView.findViewById(R.id.layout_date_month);

        //holder.outerLayout = (LinearLayout) convertView.findViewById(R.id.layout_date_item_outer_layout);

        convertView.setTag(Integer.valueOf(position));

        holder.dateTextView.setText(date.getDate());
        holder.dayTextview.setText(date.getDay());
        //holder.monthTextView.setText(date.getMonth());

        convertView.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {

             //   HomeFragment.getInstance().onPagerItemClick(v,(Integer) v.getTag());
                // TODO Auto-generated method stub
                /*((DemoActivity) mContext).onPagerItemClick(v,
                        (Integer) v.getTag());
                 */
            }
        });

        if (position == currentItemPos) {

            //holder.outerLayout.setBackgroundColor(Color.parseColor("#EC522C"));

            holder.dateTextView.setTextColor(Color.parseColor("#438bc5"));
            holder.dayTextview.setTextColor(Color.parseColor("#438bc5"));
            holder.bottom.setVisibility(View.VISIBLE);

        } else {

            //holder.outerLayout.setBackgroundColor(Color.parseColor("#ffffff"));
        }

        ((ViewPager) container).addView(convertView);

        return convertView;
    }

    private class ViewHolder {

        private TextView monthTextView;
        private TextView dayTextview;
        private TextView dateTextView;
        private ImageView bottom;
        private LinearLayout outerLayout;
    }

    @Override
    public int getCount() {
        return dates.size();
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