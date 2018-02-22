package utils;

/**
 * Created by yadi on 20/06/16.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import dcube.com.salonseek.AppointmentFragment;
import dcube.com.salonseek.FavouritesFragment;
import dcube.com.salonseek.ReviewsFragment;

public class BookingsPagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public BookingsPagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                AppointmentFragment tab1 = new AppointmentFragment();
                return tab1;
            case 1:
                ReviewsFragment tab2 = new ReviewsFragment();
                return tab2;
            case 2:
                FavouritesFragment tab3 = new FavouritesFragment();
                return tab3;
            default:
                return null;
        }
    }
    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}