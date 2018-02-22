package utils;

/**
 * Created by yadi on 26/07/16.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import dcube.com.salonseek.R;

/**
 * Created by yadi on 26/07/16.
 */
public class DateAdapter extends ArrayAdapter<String> {


    public DateAdapter(Context context, ArrayList<String> users) {
        super(context, 0, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position

        String user = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.layout_time_item, parent, false);
        }

        // Lookup view for data population
        TextView time = (TextView) convertView.findViewById(R.id.layout_time);
        ImageView bottom = (ImageView) convertView.findViewById(R.id.bottom);
        // Populate the data into the template view using the data object
        time.setText(user);
        // Return the completed view to render on screen

        /*if (position == currentItemPos) {

            time.setTextColor(Color.parseColor("#438bc5"));
            bottom.setVisibility(View.VISIBLE);

        } else {

            //holder.outerLayout.setBackgroundColor(Color.parseColor("#ffffff"));
        }*/

        return convertView;
    }
}
