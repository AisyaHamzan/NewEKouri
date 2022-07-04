package com.example.myekouri.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myekouri.Activity.ShowDetailActivity;
import com.example.myekouri.Domain.ViewProductDomain;
import com.example.myekouri.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ViewProductAdapter extends RecyclerView.Adapter<ViewProductAdapter.ViewHolder>  {

    Context context;
    List<ViewProductDomain> list;


    public ViewProductAdapter(Context context, List<ViewProductDomain> list){
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_product,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(list.get(position).getImage()).into(holder.image);
        holder.name.setText(list.get(position).getName());
        holder.price.setText(String.valueOf(list.get(position).getPrice()));


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ShowDetailActivity.class);
                intent.putExtra("detail", list.get(position));
                context.startActivity(intent);

            }
        });



    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name, price;
       ImageView image, nextBtn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.proPic);
            name = itemView.findViewById(R.id.proName);
            price = itemView.findViewById(R.id.proPrice);
            nextBtn = itemView.findViewById(R.id.next);
        }
    }
}
