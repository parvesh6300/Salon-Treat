package utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import dcube.com.salonseek.R;
import webservices.GlobalConstants;

/**
 * Created by Sagar on 01/12/16.
 */
public class SalonImagePopUpAdapter extends BaseAdapter {

    MyApplication global;

    private static LayoutInflater inflater = null;

    public Context ctx;
    int imageBackground;

//    Integer[] pics = {
//
//            R.drawable.zanjanin,
//            R.drawable.zanjanin,
//            R.drawable.zanjanin,
//            R.drawable.zanjanin,
//            R.drawable.zanjanin
//    };


    public SalonImagePopUpAdapter(Context c)
    {
        ctx = c;

        global = (MyApplication) ctx.getApplicationContext();
        inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public class Holder
    {
        ImageView iv;
    }

    @Override
    public View getView(final int pos, View convertView, ViewGroup parent) {

        Holder holder = new Holder();

        View rowView;
        rowView = inflater.inflate(R.layout.salon_images_list, null);

        holder.iv = (ImageView) rowView.findViewById(R.id.iv);

     //   ImageView iv = new ImageView(ctx);
        //iv.setImageResource(pics[arg0]);
//        iv.setPadding(3,3,3,3);
//        iv.setScaleType(ImageView.ScaleType.FIT_XY); // FIT_XY

        Log.e("Image","List Size "+global.getSalonImageListing().size());

        if(global.getSalonImageListing().size()==0)
        {
            //    iv.setImageResource(pics[pos]);
        }
        else
        {
//            Picasso.with(ctx)
//                    .load(GlobalConstants.SALON_IMAGES_URL + global.getSalonImageListing().get(pos)) //URL/FILE
//                    .into(holder.iv);

            Picasso.with(ctx)
                    .load(GlobalConstants.SALON_IMAGES_URL + global.getSalonImageListing().get(pos))
                    .placeholder(R.drawable.loader)
                    .into(holder.iv);
        }

       // iv.setLayoutParams(new Gallery.LayoutParams(280,250));

        holder.iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                imageBackground = pos;
                CustomDialog dialog = new CustomDialog(ctx);
                dialog.show();

            }
        });

        return rowView;
    }


    @Override
    public int getCount() {

        if(global.getSalonImageListing().size()==0)
            return 1;
        else
            return global.getSalonImageListing().size();
    }

    @Override
    public Object getItem(int position) {

        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }



    public class CustomDialog extends Dialog {

        TextView tv_close;

        ViewPager mViewPager;

        CustomSalonImages adapter;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            requestWindowFeature(Window.FEATURE_NO_TITLE);

            setContentView(R.layout.salon_image_pop_up);

            tv_close = (TextView) findViewById(R.id.tv_close);

            adapter = new CustomSalonImages(ctx);

            mViewPager = (ViewPager) findViewById(R.id.pager);
            mViewPager.setAdapter(adapter);

            tv_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cancel();
                }
            });


        }

        public CustomDialog(Context context) {
            super(context);
        }
    }


}
