package utils;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import dcube.com.salonseek.R;

public class DealsAdapter extends RecyclerView.Adapter<DealsAdapter.MyViewHolder> {

    private List<Deal> dealList;

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        public TextView dealname, dealprice;

        public MyViewHolder(View view) {
            super(view);
            dealname = (TextView) view.findViewById(R.id.dealname);
            dealprice = (TextView) view.findViewById(R.id.dealprice);
        }
    }

    public DealsAdapter(List<Deal> moviesList) {
        this.dealList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Deal deal = dealList.get(position);
        holder.dealname.setText(deal.getDeal());
        holder.dealprice.setText(deal.getPrice());
    }

    @Override
    public int getItemCount() {
        return dealList.size();
    }
}
