package utils;

/**
 * Created by yadi on 28/07/16.
 */

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import dcube.com.salonseek.R;
import webservices.GlobalConstants;

public class PaymentAdapter extends BaseAdapter {

    private static LayoutInflater inflater = null;
    Context context;
    Activity activity;
    MyApplication global;
    int[] imageId = {R.drawable.paypalicon, R.drawable.visaicon, R.drawable.mastercard, R.drawable.americanexpress};

    public PaymentAdapter(Activity activity) {

        this.activity = activity;

        context = activity.getApplicationContext();
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        global = (MyApplication) context.getApplicationContext();
    }

    public class Holder {
        TextView name;
        TextView type;
        ImageView card;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return global.getAl_card_details().size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder = new Holder();

        View rowView;
        rowView = inflater.inflate(R.layout.payment, parent, false);

        holder.name = (TextView) rowView.findViewById(R.id.name);
        holder.type = (TextView) rowView.findViewById(R.id.type);
        holder.card = (ImageView) rowView.findViewById(R.id.card_type);

        try {

            holder.name.setText(global.getAl_card_details().get(position).get(GlobalConstants.CARD_HOLDER));
            holder.type.setText(global.getAl_card_details().get(position).get(GlobalConstants.Card_Number));

            String str_card_type = global.getAl_card_details().get(position).get(GlobalConstants.Card_type);

            Log.e("CardType", "CardType " + str_card_type);

            switch (str_card_type) {
                case "paypal":
                    holder.card.setImageResource(imageId[0]);
                    break;

                case "visa":
                    holder.card.setImageResource(imageId[1]);
                    break;

                case "mastercard":
                    holder.card.setImageResource(imageId[2]);
                    break;

                case "amex":
                    holder.card.setImageResource(imageId[3]);
                    break;

                default:
                    holder.card.setImageResource(imageId[1]);
                    break;

            }

        } catch (Exception e) {
            Log.e("PaymentAdapter", "Exception Occurs");
        }

        return rowView;
    }



}