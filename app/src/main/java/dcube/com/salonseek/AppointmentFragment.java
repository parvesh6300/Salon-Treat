package dcube.com.salonseek;

/**
 * Created by yadi on 20/06/16.
 */
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import utils.AppointmentAdapter;
import utils.MyApplication;
import webservices.WebServicesHandler;

public class AppointmentFragment extends Fragment {

    ListView appointmentlist;
    WebServicesHandler ws;

    AppointmentAdapter appointmentAdapter;

    MyApplication global;

    RelativeLayout rel_no_aptmt;

    TextView book;

    HomeTestActivity home;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

           new AsyncTaskRunner().execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_appointment, container, false);

        global = (MyApplication) getActivity().getApplicationContext();

        appointmentlist = (ListView) view.findViewById(R.id.listasd);

        rel_no_aptmt = (RelativeLayout) view.findViewById(R.id.rel_no_aptmt);
        book = (TextView) view.findViewById(R.id.book);

        home = new HomeTestActivity();

        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager() ;
                fragmentManager.beginTransaction().replace(R.id.fragmentholder, HomeFragment.newInstance(), "rageComicList").commit();

                home.changeIcons(getActivity());

            }
        });


        Log.e("Appointment","Appointment");

        return view;

    }

    public void onAttach(Context context) {

        super.onAttach(context);

    }


    private class AsyncTaskRunner extends AsyncTask<String, String, String> {

        private String resp;

        Context context = getActivity();

        @Override
        protected String doInBackground(String... params) {

            ws.GetBookingsService(context);
            return resp;
        }

        @Override
        protected void onPostExecute(String result) {

            // execution of result of Long time consuming operation

            try {

                if (global.getBookedListing().size() <= 0)
                {
                    appointmentlist.setVisibility(View.GONE);
                    rel_no_aptmt.setVisibility(View.VISIBLE);
                }
                else
                {
                    appointmentAdapter = new AppointmentAdapter(getParentFragment().getActivity());
                    appointmentlist.setAdapter(appointmentAdapter);
                    appointmentAdapter.notifyDataSetChanged();

                    rel_no_aptmt.setVisibility(View.GONE);
                    appointmentlist.setVisibility(View.VISIBLE);
                }
            }
            catch (Exception e)
            {

            }


        }


    }


    @Override
    public void onResume() {

        super.onResume();

        new AsyncTaskRunner().execute();

    }

}