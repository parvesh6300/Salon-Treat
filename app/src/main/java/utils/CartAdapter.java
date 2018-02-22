package utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import dcube.com.salonseek.R;

/**
 * Created by Sagar on 19/07/17.
 */

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder>  {


    private Context mContext;
    private List<CartProductsModel> cart_product_List;

    Integer[] qty_ary = {1,2,3,4,5,6,7,8,9,10};

    ArrayAdapter<Integer> qty_adapter;


    public CartAdapter(Context mContext, List<CartProductsModel> cart_product_List) {

        this.mContext = mContext;
        this.cart_product_List = cart_product_List;

        qty_adapter = new ArrayAdapter<Integer>(mContext,android.R.layout.simple_list_item_1,qty_ary);

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_cart_product, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        CartProductsModel cart_product = cart_product_List.get(position);

        holder.product_name.setText(cart_product.getProduct_name());
        holder.product_cost.setText("Price: $"+cart_product.getProduct_cost());
        holder.product_type.setText(cart_product.getProduct_type());
        holder.product_seller.setText("Sold By : "+cart_product.getProduct_seller());
        holder.product_qty_in_stock.setText("Only "+cart_product.getProduct_qty_in_stock()+" unit left in stock");

        holder.sp_qty.setAdapter(qty_adapter);

        // loading album cover using Glide library
        Picasso.with(mContext).load(cart_product.getThumbnail()).into(holder.product_pic);

    }

    @Override
    public int getItemCount() {
        return cart_product_List.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView product_name, product_cost,product_type,product_seller,product_qty_label,product_qty_in_stock;
        public ImageView product_pic;
        public TextView product_remove,product_move_to_wish;

        public Spinner sp_qty;

        public MyViewHolder(View view) {
            super(view);

            product_name = (TextView) view.findViewById(R.id.product_name);
            product_cost = (TextView) view.findViewById(R.id.product_cost);
            product_type = (TextView) view.findViewById(R.id.product_type);
            product_seller = (TextView) view.findViewById(R.id.product_seller);
            product_qty_label = (TextView) view.findViewById(R.id.product_qty_label);
            product_qty_in_stock = (TextView) view.findViewById(R.id.product_qty_in_stock);
            product_remove = (TextView) view.findViewById(R.id.product_remove);
            product_move_to_wish = (TextView) view.findViewById(R.id.product_move_to_wish);

            product_pic = (ImageView) view.findViewById(R.id.product_pic);

            sp_qty = (Spinner) view.findViewById(R.id.sp_qty);

        }
    }



}
