package dcube.com.salonseek;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class AddPaymentMethod extends Activity implements View.OnClickListener{

    ImageView cancel;
    TextView tv_close;
    RelativeLayout rel_visa,rel_amex,rel_master,rel_paypal;

    public static int card_type=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_add_payment_method);


        tv_close = (TextView)findViewById(R.id.tv_close);
        cancel = (ImageView) findViewById(R.id.cancel);
        rel_visa = (RelativeLayout) findViewById(R.id.rel_visa);
        rel_master = (RelativeLayout) findViewById(R.id.rel_master);
        rel_amex = (RelativeLayout) findViewById(R.id.rel_amex);
        rel_paypal = (RelativeLayout) findViewById(R.id.rel_paypal);


        tv_close.setOnClickListener(this);
        cancel.setOnClickListener(this);
        rel_visa.setOnClickListener(this);
        rel_master.setOnClickListener(this);
        rel_amex.setOnClickListener(this);
        rel_paypal.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {

        if (v == cancel)
        {
            finish();
            overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
        }

        if (v == tv_close)
        {
            finish();
        }

        if (v == rel_amex)
        {
            card_type = 3;
            openPaymentScreen();
        }

        if (v == rel_master)
        {
            card_type = 2;
            openPaymentScreen();
        }

        if (v == rel_visa)
        {
            card_type = 1;
            openPaymentScreen();
        }

        if (v == rel_paypal)
        {
            card_type = 0;

            Intent i = new Intent(AddPaymentMethod.this,AddPaypalActivity.class);
            startActivity(i);
            overridePendingTransition(R.anim.pull_in_right,R.anim.pull_in_right);
        }


    }

    public void openPaymentScreen()
    {
        startActivity(new Intent(AddPaymentMethod.this,CardDetails.class));
        overridePendingTransition(R.anim.pull_in_right,R.anim.pull_in_right);
     //   finish();
    }

}
