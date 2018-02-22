package dcube.com.salonseek;

/**
 * Created by yadi on 20/06/16.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import utils.GivenReviewAdapter;
import utils.MyApplication;
import webservices.WebServicesHandler;

public class ReviewsFragment extends Fragment {

    TextView book;

    WebServicesHandler ws;
    GivenReviewAdapter adapter;
    static ListView list_review;
    MyApplication global;

    RelativeLayout rel_cut;

    HomeTestActivity home;

    private static ReviewsFragment mInstance = null;

    public static ReviewsFragment newInstance() {

        mInstance = new ReviewsFragment();
        return mInstance;
    }

    public static ReviewsFragment getInstance() {

        if(mInstance == null)
        {
            mInstance = new ReviewsFragment();
        }

        return mInstance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_reviews, container, false);

        book = (TextView) view.findViewById(R.id.book);

        home = new HomeTestActivity();

        list_review = (ListView) view.findViewById(R.id.list_review);
        rel_cut = (RelativeLayout) view.findViewById(R.id.rel_cut);

        global = (MyApplication) getActivity().getApplicationContext();

        adapter = new GivenReviewAdapter(getActivity());

        try {

            if (global.getAl_given_review().size() <= 0)
            {
                list_review.setVisibility(View.GONE);
                rel_cut.setVisibility(View.VISIBLE);
            }
            else
            {
                adapter = new GivenReviewAdapter(getActivity());
                list_review.setAdapter(adapter);

                rel_cut.setVisibility(View.GONE);
                list_review.setVisibility(View.VISIBLE);
            }
        }
        catch (Exception e)
        {

        }



        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fragmentManager = getParentFragment().getFragmentManager() ;
                fragmentManager.beginTransaction().replace(R.id.fragmentholder, HomeFragment.newInstance(), "rageComicList").commit();

                home.changeIcons(getActivity());
            }
        });

        return view;
    }



}