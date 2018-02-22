package dcube.com.salonseek;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import utils.CustomPagerAdapter;

public class OnBoardingActivity extends Activity {

    private final static int NUM_PAGES = 4;

    private ViewPager mViewPager;
    private List<ImageView> dots;
    private static int pos = 0;

    private Button next;
    boolean lastPageChange = false;
    private CustomPagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        setContentView(R.layout.activity_on_boarding_acitivity);

        next = (Button) findViewById(R.id.nextbutton);

        next.setVisibility(View.GONE);

        mPagerAdapter = new CustomPagerAdapter(this);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mViewPager.setAdapter(mPagerAdapter);

        mViewPager.setOffscreenPageLimit(4);

        /*next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(pos < 3 )
                {
                    pos++;
                    mViewPager.setCurrentItem(pos);
                    selectDot(pos);
                }
                else
                {
                    startActivity(new Intent(OnBoardingActivity.this,LoginActivity.class));
                    finish();
                }
            }

        });*/

        addDots();
    }

    @Override
    protected void onResume() {
        super.onResume();
        pos = 0;
    }

    public void addDots()
    {

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

            dot.setPadding(8,8,8,8);
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

                int lastIdx = mPagerAdapter.getCount() - 1;

                int curItem = mViewPager.getCurrentItem();

                if(curItem==lastIdx  && state==1) {

                    lastPageChange = true;
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    finish();

                } else {
                    lastPageChange = false;
                }
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