package utils;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import dcube.com.salonseek.R;
import webservices.GlobalConstants;

public class ReviewsAdapter extends BaseAdapter{

    Context context;
    Activity activity;
    MyApplication global;

    ArrayList<String> Clientinfo = new ArrayList<>();
    ArrayList<String> Comments = new ArrayList<>();
    ArrayList<String> Review_Time = new ArrayList<>();
    ArrayList<String> Rating = new ArrayList<>();

    private static LayoutInflater inflater = null;


    public ReviewsAdapter(Activity activity) {

        this.activity = activity;

        global = (MyApplication) activity.getApplicationContext();

        context = activity.getApplicationContext();
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        for (HashMap<String, String> hashMap : global.getReviewListing()) {

            Rating.add(hashMap.get(GlobalConstants.REVIEW_RATING));
            Comments.add(hashMap.get(GlobalConstants.REVIEW_CONTENT));
            Clientinfo.add(hashMap.get(GlobalConstants.REVIEW_BY));
            Review_Time.add(hashMap.get(GlobalConstants.REVIEW_CREATED));
        }

        Comments.add("Fantastic staff. Great haircut");
        Comments.add("Good Haircut, although I wasnt completely satisfied");

        Clientinfo.add("by Salonseek client, 3rd of December, 2015");
        Clientinfo.add("by Salonseek client, 1st of December, 2015");

        Rating.add("5.0");
        Rating.add("3.5");

    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return Rating.size();
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
        TextView clientinfo;
        TextView comments;
        CustomRatingBarBlue customRatingBarBlue;
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        final Holder holder = new Holder();

        final View rowView;
        rowView = inflater.inflate(R.layout.reviews, parent, false);

        holder.clientinfo = (TextView) rowView.findViewById(R.id.clientinfo);
        holder.comments = (TextView) rowView.findViewById(R.id.comments);
        holder.customRatingBarBlue = (CustomRatingBarBlue) rowView.findViewById(R.id.rating);

        holder.clientinfo.setText("by "+Clientinfo.get(position));
        holder.comments.setText(Comments.get(position));
        holder.customRatingBarBlue.setScore(Float.parseFloat(Rating.get(position)));

        return rowView;
    }
}