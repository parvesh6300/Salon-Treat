package dcube.com.salonseek;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import utils.ReviewsAdapter;

public class ReviewsActivity extends Activity {

    ListView list;
    ReviewsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);

        list = (ListView) findViewById(R.id.list);
        adapter = new ReviewsAdapter(this);

        list.setAdapter(adapter);
    }
}
