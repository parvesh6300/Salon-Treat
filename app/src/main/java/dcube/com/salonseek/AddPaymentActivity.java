package dcube.com.salonseek;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AddPaymentActivity extends FragmentActivity {

    private final static int NUM_PAGES = 2;

    private List<ImageView> dots;
    private static int pos = 0;

    private Button next;
    boolean lastPageChange = false;
    ViewPager mViewPager;

    TextView tv_close;
    ImageView cancel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_payment);

        tv_close = (TextView) findViewById(R.id.tv_close);
        cancel = (ImageView) findViewById(R.id.cancel);

        mViewPager = (ViewPager) findViewById(R.id.mviewpager);
        mViewPager.setAdapter(new MyNewPagerAdapter(getSupportFragmentManager()));

        addDots();


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

    }




    private class MyNewPagerAdapter extends FragmentPagerAdapter {

        public MyNewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int pos) {
            switch(pos) {

                case 0: return PaymentAddFrag1.newInstance("FirstFragment, Instance 1");
                case 1: return PaymentAddFrag2.newInstance("SecondFragment, Instance 1");

                default: return PaymentAddFrag1.newInstance("ThirdFragment, Default");
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    }


    public void addDots() {

        dots = new ArrayList<>();

        LinearLayout dotsLayout = (LinearLayout)findViewById(R.id.dots);
        dotsLayout.bringToFront();

        for(int i = 0; i < NUM_PAGES; i++) {

            ImageView dot = new ImageView(this);

            if(i == 0)
                dot.setImageDrawable(getResources().getDrawable(R.drawable.pager_dot_selected));
            else
                dot.setImageDrawable(getResources().getDrawable(R.drawable.pager_dot_not_selected));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(

                    40,40
                    //LinearLayout.LayoutParams.WRAP_CONTENT,
                    //LinearLayout.LayoutParams.WRAP_CONTENT
            );

            dot.setPadding(10,10,10,10);
            dotsLayout.addView(dot, params);
            dots.add(dot);
        }

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener(){
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {



            }

            @Override
            public void onPageSelected(int position) {

                selectDot(position);
                pos = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

                int curItem = mViewPager.getCurrentItem();
            }

        });
    }

    public void selectDot(int idx)
    {
        Resources res = getResources();

        for(int i = 0; i < NUM_PAGES; i++) {
            int drawableId = (i==idx)?(R.drawable.pager_dot_selected):(R.drawable.pager_dot_not_selected);
            Drawable drawable = res.getDrawable(drawableId);
            dots.get(i).setImageDrawable(drawable);
        }
    }

}
