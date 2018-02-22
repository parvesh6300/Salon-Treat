package dcube.com.salonseek;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.kyleduo.switchbutton.SwitchButton;

public class SettingActivity extends Activity {

    SwitchButton upcoming;
    SwitchButton special;
    SwitchButton feature;

    TextView done;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        upcoming = (SwitchButton) findViewById(R.id.upcoming);
        special = (SwitchButton) findViewById(R.id.special);
        feature = (SwitchButton) findViewById(R.id.update);

        done =  (TextView) findViewById(R.id.done);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
