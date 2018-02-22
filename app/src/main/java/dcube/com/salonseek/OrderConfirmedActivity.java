package dcube.com.salonseek;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.RelativeLayout;

public class OrderConfirmedActivity extends Activity implements View.OnClickListener{

    RelativeLayout rel_rtrn_home;

    int view_id;

    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirmed);

        rel_rtrn_home = (RelativeLayout) findViewById(R.id.rel_rtrn_home);

        handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                finish();
                overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);

            }
        }, 2000);




        rel_rtrn_home.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        view_id = v.getId();

        switch (view_id)
        {
            case R.id.rel_rtrn_home:

                finish();
                overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);

                break;

        }

    }
}
