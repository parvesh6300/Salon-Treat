package dcube.com.salonseek;

/**
 * Created by yadi on 20/06/16.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import utils.FavouritesAdapter;
import utils.MyApplication;
import webservices.WebServicesHandler;

public class FavouritesFragment extends Fragment {

    ListView favouritelist;
    FavouritesAdapter adapter;
    WebServicesHandler ws;
    MyApplication global;
    RelativeLayout rel_no_fav;

    HomeTestActivity home;
    TextView book;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_favourites, container, false);

        home = new HomeTestActivity();

        favouritelist = (ListView) view.findViewById(R.id.listasd);

        global = (MyApplication) getActivity().getApplicationContext();

        book = (TextView) view.findViewById(R.id.book);

        rel_no_fav = (RelativeLayout) view.findViewById(R.id.rel_no_fav);

        Log.e("Favorite","Size "+global.getAl_fav_salon().size());

        try {
            if (global.getAl_fav_salon().size() == 0)
            {
                favouritelist.setVisibility(View.GONE);
                rel_no_fav.setVisibility(View.VISIBLE);
            }
            else
            {
                favouritelist.setVisibility(View.VISIBLE);
                rel_no_fav.setVisibility(View.GONE);

                adapter = new FavouritesAdapter(getParentFragment().getActivity());
                favouritelist.setAdapter(adapter);
            }

        }
        catch (Exception e)
        {

        }

        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager() ;
                fragmentManager.beginTransaction().replace(R.id.fragmentholder, HomeFragment.newInstance(), "rageComicList").commit();


                home.changeIcons(getActivity());

            }
        });

        return view;

    }


}