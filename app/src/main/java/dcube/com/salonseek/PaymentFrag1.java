package dcube.com.salonseek;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import utils.MyApplication;
import utils.PaymentAdapter;

/**
 * Created by yadi on 27/07/16.
 */

public class PaymentFrag1 extends Fragment{

    ListView listView;
    PaymentAdapter adapter;
    RelativeLayout addpayment;

    MyApplication global;

    TextView cost;

    Double cost_value;

    int price;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.payment1, container, false);


        listView = (ListView) v.findViewById(R.id.list);
        addpayment = (RelativeLayout) v.findViewById(R.id.addpayment);
        cost= (TextView)v.findViewById(R.id.cost);

        global= (MyApplication)getActivity().getApplicationContext();
        adapter = new PaymentAdapter(getActivity());
        listView.setAdapter(adapter);


        try {
            for (String costdetail : global.getSelected_treatment_price()) {


                Log.e("Cost","Cost "+costdetail);

                cost_value += Double.parseDouble(costdetail);

                price += Integer.parseInt(costdetail);

                Log.e("int","Cost "+price);

            }
        }
        catch (Exception e)
        {
            Log.e("Exception","Exception in adding cost");
        }



        addpayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getActivity().startActivity(new Intent(getActivity(),AddPaymentActivity.class));

            }
        });

        cost.setText( price + ".00");

        return v;
    }

    public static PaymentFrag1 newInstance(String text) {

        PaymentFrag1 f = new PaymentFrag1();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }

}
