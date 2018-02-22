package utils;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import dcube.com.salonseek.R;

/**
 * Created by yadi on 28/07/16.
 */

public class PaymentFinalAdapter extends BaseAdapter {

    Context context;
    Activity activity;

    ArrayList<String> Name = new ArrayList<>();
    ArrayList<String> Type = new ArrayList<>();

    int[] imageId = {R.drawable.paypalicon,
                     R.drawable.dryerblack ,
                     R.drawable.stylistblack,
                     R.drawable.clockblack} ;


    private static LayoutInflater inflater = null;

    public PaymentFinalAdapter(Activity activity) {

        this.activity = activity;

        context = activity.getApplicationContext();
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        Name.add("Paypal");
        Name.add("Treatment");
        Name.add("Stylist");
        Name.add("Time & Date");


        Type.add("user123@gmail.com");
        Type.add("Regular Cut");
        Type.add("Any");
        Type.add("11:00 am, 3/9/15");


    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return Name.size();
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

    public class Holder
    {
        TextView name;
        TextView type;
        ImageView card;
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        final Holder holder = new Holder();

        final View rowView;
        rowView = inflater.inflate(R.layout.paymentfinal, parent, false);

        holder.name = (TextView) rowView.findViewById(R.id.name);
        holder.type = (TextView) rowView.findViewById(R.id.type);
        holder.card = (ImageView) rowView.findViewById(R.id.stylist);

        holder.card.setImageResource(imageId[position]);

        holder.name.setText(Name.get(position));
        holder.type.setText(Type.get(position));

        return rowView;
    }

}
