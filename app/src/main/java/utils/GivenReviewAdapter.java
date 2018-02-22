package utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import dcube.com.salonseek.R;
import webservices.GlobalConstants;
import webservices.WebServicesHandler;

/**
 * Created by Sagar on 01/11/16.
 */
public class GivenReviewAdapter extends BaseAdapter {

    Activity activity;
    private static LayoutInflater inflater = null;
    MyApplication global;

    WebServicesHandler ws;
    boolean a[];

    public GivenReviewAdapter(Activity mactivity)
    {
        this.activity = mactivity;
        global = (MyApplication) activity.getApplicationContext();
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        a = new boolean[global.getAl_given_review().size()];
    }


    public class Holder
    {
        TextView tv_salon_name;
        TextView tv_created,tv_review;
        CustomRatingBarBlue customRatingBarBlue;
        RelativeLayout rel_review_detail,rel_remove;
        ImageView iv_arrow;
    }



    @Override
    public int getCount() {
        return global.getAl_given_review().size();
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
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        final Holder holder = new Holder();

        final View rowView;

        rowView = inflater.inflate(R.layout.givenreviewlist, parent, false);

        holder.tv_salon_name = (TextView) rowView.findViewById(R.id.tv_salon_name);
        holder.tv_created = (TextView) rowView.findViewById(R.id.tv_created);
        holder.tv_review = (TextView) rowView.findViewById(R.id.tv_review);
        holder.customRatingBarBlue = (CustomRatingBarBlue) rowView.findViewById(R.id.rating);
        holder.rel_review_detail = (RelativeLayout) rowView.findViewById(R.id.rel_review_detail);
        holder.iv_arrow = (ImageView) rowView.findViewById(R.id.iv_arrow);
        holder.rel_remove = (RelativeLayout) rowView.findViewById(R.id.rel_remove);

        holder.tv_salon_name.setText(global.getAl_given_review().get(position).get(GlobalConstants.REVIEW_SALON_NAME));
     //   holder.tv_created.setText(global.getAl_given_review().get(position).get(GlobalConstants.REVIEW_CREATE));
        holder.tv_created.setText(global.getAl_given_review().get(position).get(GlobalConstants.REVIEW_ID));
        holder.tv_review.setText(global.getAl_given_review().get(position).get(GlobalConstants.REVIEW_LABEL));
        holder.customRatingBarBlue.setScore(Float.parseFloat(global.getAl_given_review().get(position).get(GlobalConstants.REVIEW_RATING)));

        holder.rel_review_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (a[position])
                {
                    a[position] = false;

                    // CLose it

                    ViewGroup.LayoutParams params = rowView.getLayoutParams();

                    if (params != null) {
                        params.height = 300;
                    }

               //     holder.iv_arrow.setRotation( holder.iv_arrow.getRotation() - 90);

                    holder.iv_arrow.setRotation(0);

                    Log.e("ROtation",""+holder.iv_arrow.getRotation());

                    rowView.setLayoutParams(params);
                }

                else
                {
                    a[position] = true;

                    ViewGroup.LayoutParams params = rowView.getLayoutParams();

                    if (params != null) {
                        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    }

                 //   holder.iv_arrow.setRotation( holder.iv_arrow.getRotation() + 90);

                    holder.iv_arrow.setRotation(90);

                    Log.e("ROtation",""+holder.iv_arrow.getRotation());

                    rowView.setLayoutParams(params);
                }

            }
        });


        holder.rel_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                global.setReview_id(global.getAl_given_review().get(position).get(GlobalConstants.REVIEW_ID));

                Log.e("DeleteReview","DeleteReviewId "+global.getReview_id());

                holder.iv_arrow.setRotation(0);

                new AsyncTaskRunner().execute();

            }
        });


        return rowView;
    }


    private class AsyncTaskRunner extends AsyncTask<String, String, String> {

        private String resp;
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {

            progressDialog = ProgressDialog.show(activity,"Deleting Review..","Wait!");
        }

        @Override
        protected String doInBackground(String... params) {

            ws.DeleteReview(activity);
            ws.FetchGivenReviews(activity);
            return resp;
        }

        @Override
        protected void onPostExecute(String result) {
            // execution of result of Long time consuming operation

            notifyDataSetChanged();
            progressDialog.dismiss();
        }
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
}
