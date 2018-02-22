package dcube.com.salonseek;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import utils.Album;
import utils.CartAdapter;
import utils.CartProductsModel;
import utils.MyApplication;

public class CartActivity extends Activity implements View.OnClickListener {


    private RecyclerView cart_recycler_view;
    private CartAdapter adapter;
    private List<CartProductsModel> cartProductsModelList;

    ImageView iv_back;

    Context mContext;

    TextView tv_place_order,tv_apply_code;

    MyApplication global;

    int view_id;

    ArrayList<Album> al_sltd_product;

    RelativeLayout rel_cpn_layout,rel_apply_cpn;

    ImageView iv_offer_arow;

    EditText ed_copn;

    float sub_total=0;
    float total_bill=0;

    public static Handler h;

    TextView tv_total_label,tv_bag_total,tv_total_pybl,tv_total_footer,tv_items_count,tv_apply_copn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        mContext = this;

        global = (MyApplication)  mContext.getApplicationContext();

        cart_recycler_view = (RecyclerView) findViewById(R.id.cart_recycler_view);

        iv_offer_arow = (ImageView) findViewById(R.id.iv_offer_arow);
        iv_back = (ImageView) findViewById(R.id.iv_back);

        tv_place_order = (TextView) findViewById(R.id.tv_place_order);
        tv_total_label = (TextView) findViewById(R.id.tv_total_label);
        tv_bag_total = (TextView) findViewById(R.id.tv_bag_total);
        tv_total_pybl = (TextView) findViewById(R.id.tv_total_pybl);
        tv_total_footer = (TextView) findViewById(R.id.tv_total_footer);
        tv_items_count = (TextView) findViewById(R.id.tv_items_count);
        tv_apply_code = (TextView) findViewById(R.id.tv_apply_code);
        tv_apply_copn = (TextView) findViewById(R.id.tv_apply_copn);

        ed_copn = (EditText) findViewById(R.id.ed_copn);

        rel_cpn_layout = (RelativeLayout) findViewById(R.id.rel_cpn_layout);
        rel_apply_cpn = (RelativeLayout) findViewById(R.id.rel_apply_cpn);

        cartProductsModelList = new ArrayList<>();

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

        prepareData();


        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        cart_recycler_view.setLayoutManager(mLayoutManager);
     //   cart_recycler_view.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(1), true));
        cart_recycler_view.setItemAnimator(new DefaultItemAnimator());
        cart_recycler_view.setAdapter(adapter);


        rel_cpn_layout.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        tv_place_order.setOnClickListener(this);
        tv_apply_code.setOnClickListener(this);

    }

    /**
     * Adding few albums for testing
     */
    private void prepareData() {

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

        al_sltd_product = new ArrayList<>();

        al_sltd_product = global.getAl_slctd_product();

        CartProductsModel model;


        for (int i =0 ; i < al_sltd_product.size() ; i++ )
        {
            String name = al_sltd_product.get(i).getName();
            float cost = al_sltd_product.get(i).getProduct_cost();
            int thumbnail = al_sltd_product.get(i).getThumbnail();

            sub_total = cost + sub_total;

            model = new CartProductsModel(name,"Proleague",1,2,
                    "Hair Shampoo/Hair Conditioner",cost,thumbnail);

            cartProductsModelList.add(model);
        }

        tv_bag_total.setText("$ "+sub_total);
        tv_total_label.setText("TOTAL:$ "+sub_total);

        tv_total_pybl.setText("$"+String.valueOf(sub_total));

        tv_total_footer.setText("$"+String.valueOf(sub_total));

        total_bill = sub_total;

        tv_items_count.setText("ITEMS("+al_sltd_product.size()+")");

        adapter = new CartAdapter(this, cartProductsModelList);
        adapter.notifyDataSetChanged();
    }


    @Override
    public void onClick(View v) {

        view_id = v.getId();

        switch (view_id)
        {
            case R.id.iv_back:

                finish();
                overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);

                break;

            case R.id.tv_place_order:

                global.setProduct_total_cost(String.valueOf(total_bill));

                startActivity(new Intent(CartActivity.this,ProductPaymentActivity.class)); //OrderConfirmedActivity  PaymentMethods

                overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);

                break;


            case R.id.rel_cpn_layout:

                rel_cpn_layout.animate().translationX(-600).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);

                        rel_cpn_layout.setVisibility(View.INVISIBLE);
                        rel_apply_cpn.setVisibility(View.VISIBLE);

                    }
                });


                break;


            case R.id.tv_apply_code:

                if (validateCopnCode())
                {

                    rel_apply_cpn.setVisibility(View.INVISIBLE);

                    rel_apply_cpn.animate().translationX(0);

                    rel_cpn_layout.setVisibility(View.VISIBLE);

                    rel_cpn_layout.animate().translationX(0).setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);

                            rel_cpn_layout.setVisibility(View.VISIBLE);

                        }
                    });

                    ed_copn.setText("");

                    applyDiscount();

                }

                break;



        }



    }


    private boolean validateCopnCode()
    {
        String str_copn = ed_copn.getText().toString().trim();

        if(str_copn.isEmpty())
        {
            Toast.makeText(mContext, "Enter Coupon Code", Toast.LENGTH_SHORT).show();
        }
        else if (!str_copn.matches("555"))
        {
            Toast.makeText(mContext, "Invalid Coupon", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(mContext, "Coupon Applied", Toast.LENGTH_SHORT).show();
            return true;
        }


        return  false;
    }



    private void applyDiscount()
    {
        float discount = (sub_total/100)*10;

        total_bill = sub_total - discount;

        tv_total_pybl.setText("$"+String.valueOf(total_bill));

        tv_total_footer.setText("$"+String.valueOf(total_bill));

        tv_apply_copn.setText("10%");

    }


}
