package dcube.com.salonseek;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import utils.Album;
import utils.AlbumsAdapter;
import utils.MyApplication;

public class ProductActivity extends AppCompatActivity implements View.OnClickListener {

    private final String TAG = "ProductActivity";

    private Context mContext;
    private RecyclerView recyclerView;
    private AlbumsAdapter adapter;
    private List<Album> albumList;

    MyApplication global;

    ImageView iv_back;

    TextView tv_item_selected,tv_check_out;

    int view_id;

    public static android.os.Handler h;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_product);

        try {
            getSupportActionBar().hide();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        mContext = this;

        global = (MyApplication) mContext.getApplicationContext();

        iv_back = (ImageView) findViewById(R.id.iv_back);

        tv_check_out = (TextView) findViewById(R.id.tv_check_out);
        tv_item_selected = (TextView) findViewById(R.id.tv_item_selected);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        albumList = new ArrayList<>();
        prepareAlbums();

      //  adapter = new AlbumsAdapter(this, albumList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(0), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);


        h =new Handler()
        {
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                switch(msg.what) {

                    case 0:
                        finish();
                        break;

                }
            }

        };

        // prepareAlbums();

        tv_check_out.setOnClickListener(this);
        iv_back.setOnClickListener(this);

    }


    /**
     * Adding few albums for testing
     */
    private void prepareAlbums() {
        int[] covers = new int[]{
                R.drawable.product1,
                R.drawable.product2,
                R.drawable.product3,
                R.drawable.product4,
                R.drawable.product1,
                R.drawable.product2,
                R.drawable.product3,
                R.drawable.product4,
                R.drawable.product1,
                R.drawable.product2,
                R.drawable.product3
        };

        Album a = new Album("Smooth Intense Conditioner", 17, covers[0],"Hair Shampoo/Hair Conditioner");
        albumList.add(a);

        a = new Album("Smooth Intense Conditioner", 19, covers[1],"Hair Shampoo/Hair Conditioner");
        albumList.add(a);

        a = new Album("Smooth Intense Conditioner", 12, covers[2],"Hair Shampoo/Hair Conditioner");
        albumList.add(a);

        a = new Album("Smooth Intense Conditioner", 15, covers[3],"Hair Shampoo/Hair Conditioner");
        albumList.add(a);

        a = new Album("Smooth Intense Conditioner", 20, covers[4],"Hair Shampoo/Hair Conditioner");
        albumList.add(a);

        a = new Album("Smooth Intense Conditioner", 17, covers[5],"Hair Shampoo/Hair Conditioner");
        albumList.add(a);

        adapter = new AlbumsAdapter(this, albumList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {

        view_id = v.getId();

        switch (view_id)
        {
            case R.id.tv_check_out:

                Log.i(TAG,""+global.getAl_slctd_product().size());

                if (global.getAl_slctd_product().size() > 0)
                {
                    startActivity(new Intent(ProductActivity.this,CartActivity.class));
                    overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
                }
                else
                {
                    Toast.makeText(mContext, "Chose atleast one product", Toast.LENGTH_SHORT).show();
                }


                break;


            case R.id.iv_back:

                finish();
                overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);

                break;

        }




    }


    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }



    public void updateCount(Context ctx,int count)
    {
        tv_item_selected.setText(String.valueOf(count)+" Item(s)\nSelected");

    }



}
