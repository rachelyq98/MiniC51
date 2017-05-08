package com.example.minic51;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rachelliu on 2017-05-05.
 */

public class OffersAdapter extends RecyclerView.Adapter<OffersAdapter.MyViewHolder> {

    private List<Offer> offerList;
    private final Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView offerName;
        public ImageView icon;

        public MyViewHolder (View view){
            super (view);
            this.offerName = (TextView)view.findViewById(R.id.offer_title);
            this.icon =(ImageView) view.findViewById(R.id.offer_icon);
        }

        @Override
        public void onClick(View v) {
            Offer offer = offerList.get(getAdapterPosition());
        }
    }

    public OffersAdapter(Context context){
        this.context = context;
        this.offerList = new ArrayList<>();
    }

    public void setOffers (ArrayList<Offer> list){
        this.offerList = list;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from (parent.getContext()).inflate(R.layout.offers_list_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Offer offer=offerList.get(position);
        holder.offerName.setText(offer.getName());
        holder.icon.setImageBitmap(offer.getIcon());
    }

    @Override
    public int getItemCount() {
        return offerList.size();
    }
}
