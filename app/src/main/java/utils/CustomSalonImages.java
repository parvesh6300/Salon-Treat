package utils;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;

import dcube.com.salonseek.R;
import webservices.GlobalConstants;

/**
 * Created by Sagar on 01/12/16.
 */
public class CustomSalonImages extends PagerAdapter {

    MyApplication global;

    Context mContext;
    LayoutInflater mLayoutInflater;

    public CustomSalonImages(Context context) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        global = (MyApplication) mContext.getApplicationContext();
    }

    @Override
    public int getCount() {
        return global.getSalonImageListing().size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        View itemView = mLayoutInflater.inflate(R.layout.pager_item, container, false);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);
    //    imageView.setImageResource(mResources[position]);

        Picasso.with(mContext) //Context
                    .load(GlobalConstants.SALON_IMAGES_URL + global.getSalonImageListing().get(position)) //URL/FILE
                    .placeholder(R.drawable.loading)
                    .into(imageView);

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }

}