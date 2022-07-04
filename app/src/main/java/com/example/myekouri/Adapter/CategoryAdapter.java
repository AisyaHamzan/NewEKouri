package com.example.myekouri.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myekouri.Activity.ViewProductActivity;
import com.example.myekouri.Domain.CategoryDomain;
import com.example.myekouri.R;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    List<CategoryDomain> categoryDomains;
    Context context;


    public CategoryAdapter(Context context, List<CategoryDomain> categoryDomains) {
        this.categoryDomains = categoryDomains;
        this.context = context;
    }




    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_category,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder holder,int position) {
        Glide.with(context)
                .load(categoryDomains.get(position).getImage()).
                into(holder.categoryPic);
                holder.categoryName.setText(categoryDomains.get(position).getName());

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, ViewProductActivity.class);
                        intent.putExtra("Category",categoryDomains.get(position).getCategory());
                        context.startActivity(intent);
                    }
                });

    }

    @Override
    public int getItemCount() {
        return categoryDomains.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView categoryName;
        ImageView categoryPic;
        ConstraintLayout mainLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.categoryName);
            categoryPic = itemView.findViewById(R.id.categoryPic);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }
}
