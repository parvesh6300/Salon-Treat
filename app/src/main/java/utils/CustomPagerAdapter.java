package utils;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import dcube.com.salonseek.R;
import webservices.GlobalConstants;

/**
 * Created by yadi on 27/05/16.
 */

public class CustomPagerAdapter extends PagerAdapter {

    public MyApplication global;
    ViewGroup layout;
    private Context mContext;

    public CustomPagerAdapter(Context context) {
        mContext = context;

        global = (MyApplication) mContext.getApplicationContext();
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {

        CustomPagerEnum customPagerEnum = CustomPagerEnum.values()[position];

        LayoutInflater inflater = LayoutInflater.from(mContext);

        layout = (ViewGroup) inflater.inflate(customPagerEnum.getLayoutResId(), collection, false);

        ImageView imgflag = (ImageView) layout.findViewById(R.id.iv); // need this ImageView

        String url = GlobalConstants.SALON_IMAGES_URL +
                global.getOnBoardListing().get(position).get(GlobalConstants.PHOTO_OnBoard);

        //  CustomLayout mCustomLayout = (CustomLayout) layout.findViewById(R.id.back);

        Log.e("URL", "Pager Url "+ url);

        Picasso.with(mContext).load(url).placeholder(R.drawable.loading).into(imgflag);  //loaderonboard

        collection.addView(layout);

        return layout;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public int getCount() {
        return CustomPagerEnum.values().length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        CustomPagerEnum customPagerEnum = CustomPagerEnum.values()[position];
        return mContext.getString(customPagerEnum.getTitleResId());
    }

    public enum CustomPagerEnum {

        DISCOVER(R.string.discover, R.layout.discover),
        INSTANT(R.string.instant, R.layout.time),
        TIME(R.string.time, R.layout.instant),
        GROUP(R.string.group, R.layout.group);

        private int mTitleResId;
        private int mLayoutResId;

        CustomPagerEnum(int titleResId, int layoutResId) {
            mTitleResId = titleResId;
            mLayoutResId = layoutResId;

        }

        public int getTitleResId() {
            return mTitleResId;
        }

        public int getLayoutResId() {
            return mLayoutResId;
        }

    }
}