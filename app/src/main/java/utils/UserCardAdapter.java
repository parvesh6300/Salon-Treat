package utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import dcube.com.salonseek.R;
import webservices.GlobalConstants;
import webservices.WebServicesHandler;

/**
 * Created by Sagar on 03/11/16.
 */
public class UserCardAdapter extends BaseAdapter {

    private static LayoutInflater inflater = null;
    Context context;
    MyApplication global;
    WebServicesHandler ws;

    int[] imageId = {R.drawable.paypalicon, R.drawable.visaicon, R.drawable.mastercard, R.drawable.americanexpress};

    public UserCardAdapter(Context mcontext)
    {
        this.context = mcontext;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        global = (MyApplication) context.getApplicationContext();
    }

    public class Holder
    {
        TextView tv_card_number,tv_expiry,tv_holder_name;
        ImageView iv_card_type,iv_closeicon;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        Holder holder = new Holder();

        View rowView;
        rowView = inflater.inflate(R.layout.usercardlist, null);

        holder.tv_card_number = (TextView) rowView.findViewById(R.id.tv_card_number);
        holder.tv_expiry = (TextView) rowView.findViewById(R.id.tv_expiry);
        holder.tv_holder_name = (TextView) rowView.findViewById(R.id.tv_holder_name);
        holder.iv_card_type = (ImageView) rowView.findViewById(R.id.iv_card_type);
        holder.iv_closeicon = (ImageView) rowView.findViewById(R.id.iv_closeicon);


        try {

            holder.tv_holder_name.setText(global.getAl_card_details().get(position).get(GlobalConstants.CARD_HOLDER));
            holder.tv_card_number.setText(global.getAl_card_details().get(position).get(GlobalConstants.Card_Number));
            holder.tv_expiry.setText(global.getAl_card_details().get(position).get(GlobalConstants.CARD_Expiry));

            String str_card_type = global.getAl_card_details().get(position).get(GlobalConstants.Card_type);

            Log.e("CardType", "CardType " + str_card_type);

            switch (str_card_type) {

                case "paypal":
                    holder.iv_card_type.setImageResource(imageId[0]);
                    break;

                case "visa":
                    holder.iv_card_type.setImageResource(imageId[1]);
                    break;

                case "mastercard":
                    holder.iv_card_type.setImageResource(imageId[2]);
                    break;

                case "amex":
                    holder.iv_card_type.setImageResource(imageId[3]);
                    break;

                default:
                    holder.iv_card_type.setImageResource(imageId[1]);
                    break;

            }

        } catch (Exception e) {
            Log.e("PaymentAdapter", "Exception Occurs");
        }


        holder.iv_closeicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                global.setCard_id(global.getAl_card_details().get(position).get(GlobalConstants.Card_id));

                Log.e("DElete","Cardid "+global.getCard_id() );
                new AsyncTaskRunner().execute();

            }
        });

        return rowView;
    }

    @Override
    public int getCount() {
        return global.getAl_card_details().size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class AsyncTaskRunner extends AsyncTask<String, String, String> {

        ProgressDialog progressDialog;
        private String resp;

        @Override
        protected void onPreExecute() {

            progressDialog = ProgressDialog.show(context, "Deleting ... ", "Wait!");
        }

        @Override
        protected String doInBackground(String... params) {

            try {

                ws.DeleteCard(context);
                ws.FetchCardDetails(context);

            } catch (Exception e) {

            }

            return resp;
        }

        @Override
        protected void onPostExecute(String result) {

            if (progressDialog.isShowing()) {
                progressDialog.cancel();
            }

            notifyDataSetChanged();
        }

    }


    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
}
