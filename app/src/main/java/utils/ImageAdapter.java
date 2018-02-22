package utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import dcube.com.salonseek.R;
import webservices.GlobalConstants;

/**
 * Created by yadi on 17/06/16.
 */
public class ImageAdapter extends BaseAdapter {

    MyApplication global;

    private Context ctx;
    int imageBackground;

    Integer[] pics = {

            R.drawable.zanjanin,
            R.drawable.zanjanin,
            R.drawable.zanjanin,
            R.drawable.zanjanin,
            R.drawable.zanjanin
    };



    public ImageAdapter(Context c) {
               ctx = c;

        global = (MyApplication) ctx.getApplicationContext();


        //TypedArray ta = c.obtainStyledAttributes(R.styleable.Gallery1);
        //imageBackground = ta.getResourceId(R.styleable.Gallery1_android_galleryItemBackground, 1);
       // ta.recycle();
    }

    @Override
    public int getCount() {

        if(global.getSalonImageListing().size()==0)
            return 1;
        else
        return
            global.getSalonImageListing().size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(final int pos, View arg1, ViewGroup arg2) {

        ImageView iv = new ImageView(ctx);
        //iv.setImageResource(pics[arg0]);
        iv.setPadding(3,3,3,3);
        iv.setScaleType(ImageView.ScaleType.FIT_XY);

        if(global.getSalonImageListing().size()==0)
        {
            iv.setImageResource(pics[pos]);
        }
        else
        {
            Picasso.with(ctx) //Context
                    .load(GlobalConstants.SALON_IMAGES_URL + global.getSalonImageListing().get(pos)) //URL/FILE
                    .into(iv);
        }

       // iv.setLayoutParams(new Gallery.LayoutParams(280,250));

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                imageBackground = pos;
                CustomDialog dialog = new CustomDialog(ctx);
                dialog.show();

            }
        });

        //iv.setBackgroundResource(imageBackground);
        return iv;
    }

    public class CustomDialog extends Dialog {

        ImageView iv_images,iv_close_dialog;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            requestWindowFeature(Window.FEATURE_NO_TITLE);

            setContentView(R.layout.imagedialog);

            iv_images = (ImageView) findViewById(R.id.iv_images);
            iv_close_dialog =(ImageView) findViewById(R.id.iv_close_dialog);

            Picasso.with(ctx) //Context
                    .load(GlobalConstants.SALON_IMAGES_URL + global.getSalonImageListing().get(imageBackground)) //URL/FILE
                    .into(iv_images);


            iv_close_dialog.setOnClickListener(new View.OnClickListener() {
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