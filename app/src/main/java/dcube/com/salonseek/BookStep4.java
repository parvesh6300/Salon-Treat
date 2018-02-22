package dcube.com.salonseek;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class BookStep4 extends FragmentActivity {

    private final static int NUM_PAGES = 2;

    private List<ImageView> dots;
    private static int pos = 0;

    private Button next;
    boolean lastPageChange = false;
    ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_step4);

        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mViewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));

        addDots();
    }




    private class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int pos) {
            switch(pos) {

                case 0: return PaymentFrag1.newInstance("FirstFragment, Instance 1");
                case 1: return PaymentFrag2.newInstance("SecondFragment, Instance 1");

                default: return PaymentFrag1.newInstance("ThirdFragment, Default");
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        pos = 0;
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

    public void selectDot(int idx) {

        Resources res = getResources();

        for(int i = 0; i < NUM_PAGES; i++) {
            int drawableId = (i==idx)?(R.drawable.pager_dot_selected):(R.drawable.pager_dot_not_selected);
            Drawable drawable = res.getDrawable(drawableId);
            dots.get(i).setImageDrawable(drawable);
        }
    }



}
