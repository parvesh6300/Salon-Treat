package dcube.com.salonseek;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;

import utils.CalendarView;

public class CalendarActivity extends Activity
{
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        back = (ImageView) findViewById(R.id.back);

        HashSet<Date> events = new HashSet<>();
        events.add(new Date());

        CalendarView cv = ((CalendarView)findViewById(R.id.calendar_view));
        cv.updateCalendar(events);

        // assign event handler
        cv.setEventHandler(new CalendarView.EventHandler()
        {
            @Override
            public void onDayLongPress(Date date)
            {
                // show returned day
                DateFormat df = SimpleDateFormat.getDateInstance();
                Toast.makeText(CalendarActivity.this, df.format(date), Toast.LENGTH_SHORT).show();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
    }
}
