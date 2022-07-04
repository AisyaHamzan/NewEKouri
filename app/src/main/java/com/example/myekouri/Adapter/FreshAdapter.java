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
import com.example.myekouri.Activity.FreshDetailActivity;
import com.example.myekouri.Activity.PopularDetailActivity;
import com.example.myekouri.Domain.FreshDomain;
import com.example.myekouri.R;

import java.util.List;

public class FreshAdapter extends RecyclerView.Adapter<FreshAdapter.ViewHolder> {

    Context context;
    List<FreshDomain> freshDomainList;

    public FreshAdapter(Context context, List<FreshDomain> freshDomainList) {
        this.context = context;
        this.freshDomainList = freshDomainList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_fresh,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(freshDomainList.get(position).getImage()).into(holder.pic);
        holder.title.setText(freshDomainList.get(position).getName());
        holder.fee.setText(String.valueOf(freshDomainList.get(position).getPrice()));

        holder.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, FreshDetailActivity.class);
                intent.putExtra("freshdetail", freshDomainList.get(position));
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return freshDomainList.size();
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
