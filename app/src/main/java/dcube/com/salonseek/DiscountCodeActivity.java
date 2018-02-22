package dcube.com.salonseek;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DiscountCodeActivity extends Activity {

    ImageView iv_cancel;

    EditText ed_code;
    TextView tv_close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_discount_code);

        iv_cancel = (ImageView) findViewById(R.id.iv_cancel);

        ed_code = (EditText) findViewById(R.id.ed_code);
        tv_close = (TextView) findViewById(R.id.tv_close);

        iv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ed_code.setText("");

            }
        });

        tv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                overridePendingTransition(R.anim.pull_in_left, R.anim.pull_in_left);
                finish();
            }
        });

        ed_code.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_SEND)
                {
                    Toast.makeText(DiscountCodeActivity.this, ""+ed_code.getText().toString(), Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            }
        });




    }




}
