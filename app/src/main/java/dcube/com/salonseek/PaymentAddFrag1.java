package dcube.com.salonseek;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import utils.MyApplication;

/**
 * Created by yadi on 29/07/16.
 */

public class PaymentAddFrag1 extends Fragment {

    RelativeLayout rel_visa;

    TextView cost;

    ViewPager mViewPager;

    MyApplication global;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.addpayment1, container, false);

        global=(MyApplication) getActivity().getApplicationContext();

        cost=(TextView)v.findViewById(R.id.cost);

        rel_visa=(RelativeLayout)v.findViewById(R.id.rel_visa);


        rel_visa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PaymentAddFrag2.newInstance("SecondFragment, Instance 1");

            }
        });



        return v;
    }

    public static PaymentAddFrag1 newInstance(String text) {

        PaymentAddFrag1 f = new PaymentAddFrag1();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }

}
