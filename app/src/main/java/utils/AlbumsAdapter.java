package utils;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import dcube.com.salonseek.ProductActivity;
import dcube.com.salonseek.R;

/**
 * Created by Sagar on 18/07/17.
 */

public class AlbumsAdapter extends RecyclerView.Adapter<AlbumsAdapter.MyViewHolder> {

    private final String TAG = "AlbumsAdapter";

    private Context mContext;
    private List<Album> albumList;

    private boolean[] is_product_saved;
    private boolean[] is_product_slctd;

    MyApplication global;

    List<Integer> list_item_slctd_count;


    ArrayList<Album> al_slctd_product;


    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView title, product_cost,product_type;
        public ImageView thumbnail, iv_save , iv_tick;
        public RelativeLayout rel_card_layout,rel_product_image,rel_product_detail;
        public CardView card_view;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            product_cost = (TextView) view.findViewById(R.id.product_cost);
            product_type = (TextView) view.findViewById(R.id.product_type);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            iv_save = (ImageView) view.findViewById(R.id.iv_save);
            iv_tick = (ImageView) view.findViewById(R.id.iv_tick);

            rel_card_layout = (RelativeLayout) view.findViewById(R.id.rel_card_layout);
            rel_product_image = (RelativeLayout) view.findViewById(R.id.rel_product_image);
            rel_product_detail = (RelativeLayout) view.findViewById(R.id.rel_product_detail);

            card_view = (CardView) view.findViewById(R.id.card_view);


        }
    }



    public AlbumsAdapter(Context mContext, List<Album> albumList) {

        this.mContext = mContext;
        this.albumList = albumList;

        global = (MyApplication) mContext.getApplicationContext();

        is_product_slctd = new boolean[albumList.size()];
        is_product_saved = new boolean[albumList.size()];

        al_slctd_product = new ArrayList<>();

        list_item_slctd_count = new ArrayList<>();

    }


    @Override
    public AlbumsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.album_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final AlbumsAdapter.MyViewHolder holder, final int position) {

        final Album album = albumList.get(position);
        holder.title.setText(album.getName());
        holder.product_cost.setText("$"+album.getProduct_cost());

        holder.product_type.setText(album.getProduct_type());

        // loading album cover using Glide library
        Picasso.with(mContext).load(album.getThumbnail()).into(holder.thumbnail);

        holder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (is_product_slctd[position])
                {
                    holder.iv_tick.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.select_hdpi));
                    is_product_slctd[position] = false;

                    int pos = list_item_slctd_count.indexOf(position);

                    list_item_slctd_count.remove(pos);

                  //  holder.card_view.setCardElevation(0f);

                    holder.card_view.setCardElevation(3);  //getPixelsFromDPs(0)

                    int index = al_slctd_product.indexOf(album);

                    al_slctd_product.remove(index);

                    holder.rel_product_detail.setBackgroundColor(mContext.getResources().getColor(R.color.product_detail_bg));

                }
                else
                {
                    holder.iv_tick.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.selected_hdpi));
                    is_product_slctd[position] = true;

                    list_item_slctd_count.add(position);

                    holder.card_view.setCardElevation(30); //getPixelsFromDPs(6)

                    al_slctd_product.add(album);

                    holder.rel_product_detail.setBackgroundColor(mContext.getResources().getColor(R.color.product_slctd_bg));

                }

                Log.i(TAG,""+al_slctd_product.size());

                global.setAl_slctd_product(al_slctd_product);


                ((ProductActivity)mContext).updateCount(mContext,list_item_slctd_count.size());

            }
        });



        holder.iv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                showPopupMenu(holder.iv_save);

                if (is_product_saved[position])
                {
                    holder.iv_save.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.save_hdpi));
                    is_product_saved[position] = false;

                }
                else
                {
                    holder.iv_save.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.saved_hdpi));
                    is_product_saved[position] = true;
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }

    /**
     * Showing popup menu when tapping on 3 dots
     */
    private void showPopupMenu(View view) {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_album, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
        popup.show();
    }


    /**
     * Click listener for popup menu items
     */
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        public MyMenuItemClickListener() {
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_add_favourite:
                    Toast.makeText(mContext, "Add to favourite", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.action_play_next:
                    Toast.makeText(mContext, "Play next", Toast.LENGTH_SHORT).show();
                    return true;
                default:
            }
            return false;
        }
    }



    // Custom method for converting DP/DIP value to pixels
    protected int getPixelsFromDPs(int dps){
        Resources r = mContext.getResources();
        int  px = (int) (TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dps, r.getDisplayMetrics()));
        return px;
    }

}
