package com.example.myekouri.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myekouri.Activity.ReceiptActivity;
import com.example.myekouri.Domain.ReceiptDomain;
import com.example.myekouri.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class ReceiptAdapter extends RecyclerView.Adapter<ReceiptAdapter.ViewHolder> {

    Context context;
    List<ReceiptDomain> receiptDomains;



    public ReceiptAdapter(Context context, List<ReceiptDomain> receiptDomains) {
        this.context = context;
        this.receiptDomains = receiptDomains;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_receipt,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(receiptDomains.get(position).getProductName());
        holder.price.setText(String.valueOf(receiptDomains.get(position).getTotalPrice()));
        holder.quantity.setText(String.valueOf(receiptDomains.get(position).getTotalQuantity()));

    }

    @Override
    public int getItemCount() {
        return receiptDomains.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, price, quantity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.proName);
            price = itemView.findViewById(R.id.totalPriceEach);
            quantity = itemView.findViewById(R.id.quantity);


        }
    }
}
