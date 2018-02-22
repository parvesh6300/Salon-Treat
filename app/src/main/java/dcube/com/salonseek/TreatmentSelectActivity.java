package dcube.com.salonseek;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import utils.MyApplication;
import utils.TreatmentSelectAdapter;
import webservices.WebServicesHandler;

public class TreatmentSelectActivity extends Activity {

    ListView salonsaround;
    TreatmentSelectAdapter adapter;
    EditText settreatment;
    ImageView back;
    MyApplication global;

    WebServicesHandler ws;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treatment_select);

        salonsaround = (ListView) findViewById(R.id.salonaroundlist);
        settreatment = (EditText) findViewById(R.id.settreatment);

        global=(MyApplication) getApplicationContext();

        back = (ImageView) findViewById(R.id.back);

//        adapter = new TreatmentSelectAdapter(this);
//        salonsaround.setAdapter(adapter);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });


        adapter = new TreatmentSelectAdapter(TreatmentSelectActivity.this);

        salonsaround.setAdapter(adapter);

        settreatment.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(settreatment.getText().length() >= 2){

                    global.setSrc_KeyWord(settreatment.getText().toString());

                    Thread timerThread = new Thread(){
                        public void run(){
                            try{

                                ws.TreatmentService(TreatmentSelectActivity.this);

                              runOnUiThread(new Runnable() {
                                  @Override
                                  public void run() {

                                      adapter = new TreatmentSelectAdapter(TreatmentSelectActivity.this);

                                      salonsaround.setAdapter(adapter);

                                  }
                              });



                            }catch(Exception e){

                                e.printStackTrace();
                            }
                        }
                    };
                    timerThread.start();

                }
            }
            @Override
            public void afterTextChanged(Editable s) {

                if (s.length() == 0)
                {
                    Thread timerThread = new Thread(){
                        public void run(){
                            try{

                                ws.AllTreatmentService(TreatmentSelectActivity.this);

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        adapter = new TreatmentSelectAdapter(TreatmentSelectActivity.this);

                                        salonsaround.setAdapter(adapter);

                                    }
                                });

                            }catch(Exception e){

                                e.printStackTrace();
                            }
                        }
                    };
                    timerThread.start();

                }

            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
