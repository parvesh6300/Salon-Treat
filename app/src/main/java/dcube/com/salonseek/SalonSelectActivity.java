package dcube.com.salonseek;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import utils.MyApplication;
import utils.SelectSalonAdapter;
import webservices.GlobalConstants;
import webservices.WebServicesHandler;

public class SalonSelectActivity extends Activity  {

    SelectSalonAdapter adapter;
    ListView salonsaround;
    EditText setsalonname;

    ImageView back;

    MyApplication global;

    WebServicesHandler ws;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_salon_select);

        global=(MyApplication) getApplicationContext();

        setsalonname = (EditText) findViewById(R.id.setsalonname);

        back = (ImageView) findViewById(R.id.back);

        global.al_search_salon.clear();

        global.al_search_items.remove(GlobalConstants.Search_Salon_Name);
        global.al_search_items.remove(GlobalConstants.Search_SALON_ID);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

        salonsaround = (ListView) findViewById(R.id.salonaroundlist);

        adapter = new SelectSalonAdapter(SalonSelectActivity.this);
        salonsaround.setAdapter(adapter);


        salonsaround.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

              //  adapter.setSelectedIndex(position);
              //  adapter.notifyDataSetChanged();

                global.setSrc_salon_id(global.getSalonListing().get(position).get(GlobalConstants.RES_ID));
                global.setSrc_salon_name(global.getSalonListing().get(position).get(GlobalConstants.Search_Salon_Name));

                global.al_search_items.put(GlobalConstants.Search_SALON_ID,global.getSrc_salon_id());
                global.al_search_items.put(GlobalConstants.Search_Salon_Name,global.getSrc_salon_name());

                finish();

            }
        });


        setsalonname.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(setsalonname.getText().length() >= 2){

                    global.setSrc_KeyWord(setsalonname.getText().toString());

                    Thread timerThread = new Thread(){
                        public void run(){
                            try{

                                ws.SearchService(SalonSelectActivity.this,true);

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        adapter = new SelectSalonAdapter(SalonSelectActivity.this);
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
            public void afterTextChanged(Editable s) {}
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        });

    }


    @Override
    protected void onResume() {
        super.onResume();
    }
}
