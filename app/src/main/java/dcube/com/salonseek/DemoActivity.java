package dcube.com.salonseek;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import utils.BookNowDateListAdapter;
import utils.CustomDate;

public class DemoActivity extends Activity {

    BookNowDateListAdapter adapter;
    ViewPager pager;
    private ArrayList<CustomDate> dates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        initViewPager();
    }
    private void initViewPager() {
        adapter = new BookNowDateListAdapter(this, R.layout.layout_date_item,
                getDates());
        adapter.setCurrentItem(0);

        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);

        // set intial position.
        onPagerItemClick(pager.getChildAt(0), 0);
    }

    public void onPagerItemClick(View view, int index) {
        System.out.println("" + index);
        adapter.setCurrentItem(index);
        pager.setAdapter(adapter);
    }

    private ArrayList<CustomDate> getDates() {
        dates = new ArrayList<CustomDate>();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        for (int index = 1; index < 365; index++) {

            CustomDate date = new CustomDate();
            date.setDate("" + calendar.get(Calendar.DATE));
            date.setDay(getDay(calendar.get(Calendar.DAY_OF_WEEK)));
            date.setYear("" + calendar.get(Calendar.YEAR));
            date.setMonth("" + getMonth(calendar.get(Calendar.MONTH)));

            date.setFormattedDate(calendar.get(Calendar.YEAR) + "-"
                    + (calendar.get(Calendar.MONTH) + 1) + "-"
                    + calendar.get(Calendar.DATE));

            dates.add(date);

            calendar.add(Calendar.DATE, 1);

        }


        return dates;
    }


    private String getDay(int index) {
        switch (index) {
            case Calendar.SUNDAY:
                return "SUN";
            case Calendar.MONDAY:
                return "MON";
            case Calendar.TUESDAY:
                return "TUE";
            case Calendar.WEDNESDAY:
                return "WED";
            case Calendar.THURSDAY:
                return "THUR";
            case Calendar.FRIDAY:
                return "FRI";
            case Calendar.SATURDAY:
                return "SAT";
        }
        return "";
    }

    private String getMonth(int index) {
        switch (index) {
            case Calendar.JANUARY:
                return "JANUARY";
            case Calendar.FEBRUARY:
                return "FEBRUARY";
            case Calendar.MARCH:
                return "MARCH";
            case Calendar.APRIL:
                return "APRIL";
            case Calendar.MAY:
                return "MAY";
            case Calendar.JUNE:
                return "JUNE";
            case Calendar.JULY:
                return "JULY";
            case Calendar.AUGUST:
                return "AUGUST";
            case Calendar.SEPTEMBER:
                return "SEPTEMBER";
            case Calendar.OCTOBER:
                return "OCTOBER";
            case Calendar.NOVEMBER:
                return "NOVEMBER";
            case Calendar.DECEMBER:
                return "DECEMBER";
        }
        return "";
    }


}
