package utils;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import dcube.com.salonseek.R;
import webservices.WebServicesHandler;

/**
 * Created by Sagar on 07/11/16.
 */
public class MobDelAdapter extends BaseAdapter {

    private static LayoutInflater inflater = null;
    Context context;
    Activity activity;
    MyApplication global;

    WebServicesHandler ws;

    public MobDelAdapter(Context mcontext)
    {
        context = mcontext;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        global = (MyApplication) context.getApplicationContext();
    }

    public class Holder {
        TextView tv_mob_label;
        TextView tv_contact_no;
        ImageView iv_cancel;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Holder holder = new Holder();

        View rowView;
        rowView = inflater.inflate(R.layout.user_del_mobile, null);

        holder.tv_mob_label = (TextView) rowView.findViewById(R.id.tv_mob_label);
        holder.tv_contact_no = (TextView) rowView.findViewById(R.id.tv_contact_no);
        holder.iv_cancel = (ImageView) rowView.findViewById(R.id.iv_cancel);

        try
        {
            holder.tv_contact_no.setText(global.getUser_contact_no().get(position));
        }
        catch (Exception e)
        { }

        holder.iv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                global.setDelete_number(holder.tv_contact_no.getText().toString());

                new AsyncTask().execute();

//                Thread thread = new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//
//
//                    }
//                });
//                thread.start();
//
//
//                notifyDataSetChanged();

            }
        });

        return rowView;
    }

    @Override
    public int getCount() {
        return global.getUser_contact_no().size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }


    public class AsyncTask extends android.os.AsyncTask<String,String,String>
    {

        @Override
        protected String doInBackground(String... params) {

            ws.DeleteNumberOfUser(context);

            ws.GetProfileService(context);

            return null;
        }


        @Override
        protected void onPostExecute(String s) {

            notifyDataSetChanged();

        }

    }

}
