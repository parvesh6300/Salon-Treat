package dcube.com.salonseek;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import utils.BookingsPagerAdapter;
import webservices.WebServicesHandler;

/**
 * Created by yadi on 20/06/16.
 */

public class BookingFrag extends Fragment{

    WebServicesHandler ws;

    public BookingFrag(){}

    public static BookingFrag newInstance() {
        return new BookingFrag();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_booking, container, false);

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Appointments"));
        tabLayout.addTab(tabLayout.newTab().setText("Reviews"));
        tabLayout.addTab(tabLayout.newTab().setText("Favourites"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        new AsyncTaskRunner().execute();

        final ViewPager viewPager = (ViewPager) view.findViewById(R.id.pager);
        viewPager.setOffscreenPageLimit(1);
        BookingsPagerAdapter adapter = new BookingsPagerAdapter(getChildFragmentManager(), tabLayout.getTabCount());

        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return view;
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onAttach(Context context) {
        context = getActivity();
        super.onAttach(context);
    }




    public class AsyncTaskRunner extends AsyncTask<String, String, String> {

        private String resp;
        ProgressDialog progressDialog;

        Context context;

        @Override
        protected void onPreExecute() {

            context = getActivity();

            progressDialog = ProgressDialog.show(getActivity(), "Fetching Data", "Wait!");
        }

        @Override
        protected String doInBackground(String... params) {

            ws.GetBookingsService(context);
            ws.FetchGivenReviews(context);
            ws.GetFavoriteSalon(context);
            return resp;
        }

        @Override
        protected void onPostExecute(String result) {
            // execution of result of Long time consuming operation

            progressDialog.dismiss();
        }


    }



}