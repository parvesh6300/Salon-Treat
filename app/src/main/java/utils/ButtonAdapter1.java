package utils;

/**
 * Created by yadi on 15/09/16.
 */

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import dcube.com.salonseek.R;

public class ButtonAdapter1 extends BaseAdapter{

    ArrayList<String> timings;

    Context context;
    MyApplication global;

    RadioGroup rgp;

    ArrayList<String> Timings = new ArrayList<>();

    private TextView mSelectedRB;
    private ImageView check;
    private RelativeLayout back;
    private int mSelectedPosition = -1;

    Activity act;

    private static LayoutInflater inflater = null;

    public ButtonAdapter1(Activity activity , ArrayList<String> timings) {
        // TODO Auto-generated constructor stub

        context = activity;
        act = activity;

        global =(MyApplication)context.getApplicationContext();
        Timings = timings;
        inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return global.getTomorrowListing().size();
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
        TextView rb;
        ImageView circle;
        RelativeLayout backview;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final Holder holder = new Holder();

        View view = inflater.inflate(R.layout.radiobutton, parent , false);

        holder.rb = (TextView) view.findViewById(R.id.radioButton);
        holder.circle = (ImageView) view.findViewById(R.id.circle);

        holder.backview = (RelativeLayout) view.findViewById(R.id.backview);

        holder.rb.setText(global.getTomorrowListing().get(position));


        holder.backview.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Calendar cal = Calendar.getInstance();
                System.out.println("Current time => " + cal.getTime());

                cal.add(Calendar.DAY_OF_YEAR, 1);

                System.out.println("Current time => " + cal.getTime());

                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                String formattedDate = df.format(cal.getTime()) + " ";

                global.setBookingTime(formattedDate + global.getTomorrowListing().get(position));

                if ((position != mSelectedPosition && mSelectedRB != null)) {

                    //v.setBackgroundColor(act.getResources().getColor(R.color.white));

                    mSelectedRB.setTextColor(act.getResources().getColor(R.color.colorblue));
                    mSelectedRB.setBackgroundColor(act.getResources().getColor(R.color.white));

                    check.setBackgroundColor(act.getResources().getColor(R.color.white));
                    check.setImageResource(R.drawable.bluecircle);

                    //holder.circle_image.setImageResource(R.drawable.ok);
                    //holder.backview.setBackgroundColor(act.getResources().getColor(R.color.colorblue));
                }

                mSelectedPosition = position;
                mSelectedRB = (TextView) v.findViewById(R.id.radioButton);
                check = (ImageView) v.findViewById(R.id.circle);

                check.setImageResource(R.drawable.ok);
                check.setBackgroundColor(act.getResources().getColor(R.color.colorblue));

                //holder.circle_image.setImageResource(R.drawable.bluecircle);
                //holder.backview.setBackgroundColor(act.getResources().getColor(R.color.white));

                mSelectedRB.setTextColor(act.getResources().getColor(R.color.white));

                //v.setBackgroundColor(act.getResources().getColor(R.color.colorblue));

                mSelectedRB.setBackgroundColor(act.getResources().getColor(R.color.colorblue));
            }
        });

       /* if (mSelectedPosition != position) {
            holder.rb.setChecked(false);
        } else {
            holder.rb.setChecked(true);
            if (mSelectedRB != null && holder.rb != mSelectedRB) {
                mSelectedRB = holder.rb;
            }
        }*/

        return view;
    }
}
