package dcube.com.salonseek;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import utils.MyApplication;
import utils.TreatmentListAdapter;

public class TreatmentActivity extends Activity {

    ListView treatmentlist;
    TreatmentListAdapter adapter;

    MyApplication global;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treatment);

        global = (MyApplication) getApplicationContext();
        global.setReadonly(false);

        treatmentlist = (ListView) findViewById(R.id.treatmentlist);
        adapter = new TreatmentListAdapter(this);

        try{
            treatmentlist.setAdapter(adapter);
        }
        catch (Exception e){

        }


    }
}
