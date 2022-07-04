package com.example.myekouri.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myekouri.Domain.CartListDomain;
import com.example.myekouri.Domain.FoodDomain;
import com.example.myekouri.Helper.ManagementCart;
import com.example.myekouri.Interface.ChangeNumberItemsListener;
import com.example.myekouri.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.ViewHolder> {

    Context context;
    List<CartListDomain> cartListDomainList;
    double totalPrice;
    FirebaseFirestore mStore;
    FirebaseAuth mAuth;


    public CartListAdapter(Context context, List<CartListDomain> cartListDomainList) {
        this.context = context;
        this.cartListDomainList = cartListDomainList;
        mStore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_cart,parent,false));
    }

   // @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull CartListAdapter.ViewHolder holder, int position) {
        Glide.with(context).load(cartListDomainList.get(position).getImage()).into(holder.pic);
        holder.title.setText(cartListDomainList.get(position).getProductName());
        holder.feeEachItem.setText(String.valueOf(cartListDomainList.get(position).getProductPrice()));
        holder.totalEachItem.setText(String.valueOf(cartListDomainList.get(position).getTotalPrice()));
       // holder.totalEachItem.setText(String.valueOf(Math.round((cartListDomainList.get(position).getTotalQuantity() * cartListDomainList.get(position).getProductPrice()) * 100) / 100));
        holder.num.setText(String.valueOf(cartListDomainList.get(position).getTotalQuantity()));

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mStore.collection("Cart").document(mAuth.getCurrentUser().getUid())
                        .collection("users")
                        .document(cartListDomainList.get(position).getDocumentId())
                        .delete()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    cartListDomainList.remove(cartListDomainList.get(position));
                                    notifyDataSetChanged();
                                    Toast.makeText(context, "Produk dipadam", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(context, "Error"+task.getException(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });


    }

    @Override
    public int getItemCount() {

        return cartListDomainList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, feeEachItem;
        ImageView pic, delete;
        TextView totalEachItem, num;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.titleTxt);
            feeEachItem = itemView.findViewById(R.id.feeEachItem);
            pic = itemView.findViewById(R.id.picCart);
            totalEachItem = itemView.findViewById(R.id.totalEachItem);
            num = itemView.findViewById(R.id.numberItemTxt);
            delete = itemView.findViewById(R.id.delete);
           // plusItem = itemView.findViewById(R.id.plusCardBtn);
            //minusItem = itemView.findViewById(R.id.minusCartBtn);
        }
    }
}
