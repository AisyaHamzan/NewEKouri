package com.example.myekouri.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myekouri.Activity.PopularDetailActivity;
import com.example.myekouri.Activity.ShowDetailActivity;
import com.example.myekouri.Domain.FoodDomain;
import com.example.myekouri.Domain.PopularDomain;
import com.example.myekouri.R;

import java.util.ArrayList;
import java.util.List;

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.ViewHolder> {
      Context context;
    List<PopularDomain> popularDomainList;

    public PopularAdapter(Context context, List<PopularDomain> popularDomainList) {
        this.context = context;
        this.popularDomainList = popularDomainList;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
     return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_popular,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull PopularAdapter.ViewHolder holder, int position) {
        Glide.with(context).load(popularDomainList.get(position).getImage()).into(holder.pic);
        holder.title.setText(popularDomainList.get(position).getName());
        holder.fee.setText(String.valueOf(popularDomainList.get(position).getPrice()));

        holder.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PopularDetailActivity.class);
                intent.putExtra("popdetail", popularDomainList.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return popularDomainList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, fee;
        ImageView pic;
        TextView addBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            fee = itemView.findViewById(R.id.fee);
            pic = itemView.findViewById(R.id.pic);
            addBtn = itemView.findViewById(R.id.addBtn);
        }
    }
}
