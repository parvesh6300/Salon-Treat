package dcube.com.salonseek;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SlidingDrawer;
import android.widget.TextView;
import android.widget.Toast;
import utils.CustomAdapter;

public class HomeActivity extends Activity {

    DrawerLayout drawer;
    ListView lv;
    ImageView profileview;
    ImageView search;
    ImageView filter;
    SlidingDrawer searchslider;
    SlidingDrawer filterslider;
    TextView seektext;
    ImageView bottomslide;
    ImageView appointment;

    Button showSalon;
    SeekBar seekBar;
    CustomAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new CustomAdapter(HomeActivity.this);

        searchslider = (SlidingDrawer) findViewById(R.id.searchDrawer);
        filterslider = (SlidingDrawer) findViewById(R.id.filterdrawer);
        showSalon = (Button) findViewById(R.id.showsalon);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        lv = (ListView) findViewById(R.id.list);
        profileview = (ImageView) findViewById(R.id.profile);
        bottomslide = (ImageView) findViewById(R.id.bottomslide);

        appointment = (ImageView) findViewById(R.id.appointment);

        seekBar = (SeekBar) findViewById(R.id.seekbar);
        seektext = (TextView) findViewById(R.id.seekvalue);

        LayoutInflater inflater = getLayoutInflater();
        View addNew = inflater.inflate(R.layout.searchandfilter, lv, false);
        lv.addHeaderView(addNew, null, false);

        search = (ImageView) addNew.findViewById(R.id.search);
        filter = (ImageView) addNew.findViewById(R.id.filter);

        lv.setAdapter(adapter);
        adapter.notifyDataSetChanged();


        appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(HomeActivity.this,AppointmentActivity.class));
            }
        });


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                seektext.setText(String.valueOf(progress) + "km");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchslider.animateOpen();
                searchslider.setClickable(true);
             }
        });
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterslider.animateOpen();
                filterslider.setClickable(true);
            }
        });

        showSalon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchslider.animateClose();
                searchslider.setClickable(false);
            }
        });

        profileview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(GravityCompat.END);
            }
        });

        NavigationView rightNavigationView = (NavigationView) findViewById(R.id.nav_right_view);
        assert rightNavigationView != null;
        if (rightNavigationView != null) {
            rightNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(MenuItem item) {
                    // Handle Right navigation view item clicks here.
                    int id = item.getItemId();

                    if (id == R.id.nav_settings) {
                        Toast.makeText(HomeActivity.this, "Right Drawer - Account Details", Toast.LENGTH_SHORT).show();
                    } else if (id == R.id.nav_share) {
                        Toast.makeText(HomeActivity.this, "Right Drawer - Share & Rate Us", Toast.LENGTH_SHORT).show();
                    } else if (id == R.id.nav_settings) {
                        Toast.makeText(HomeActivity.this, "Right Drawer - Settings", Toast.LENGTH_SHORT).show();
                    } else if (id == R.id.nav_about) {
                        Toast.makeText(HomeActivity.this, "Right Drawer - About SalonSeek", Toast.LENGTH_SHORT).show();
                    }

                    drawer.closeDrawer(GravityCompat.END); /*Important Line*/
                    return true;
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (drawer.isDrawerOpen(GravityCompat.END)) {  /*Closes the Appropriate Drawer*/
            drawer.closeDrawer(GravityCompat.END);
        } else {
            super.onBackPressed();
            System.exit(0);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_openRight) {
            //drawer.openDrawer(GravityCompat.END);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = MeasureSpec.makeMeasureSpec(listView.getWidth(), MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }
}