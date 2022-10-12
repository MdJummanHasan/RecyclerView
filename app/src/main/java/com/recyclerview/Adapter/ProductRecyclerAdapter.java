package com.recyclerview.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.recyclerview.Common.UploadProductModel;
import com.recyclerview.R;
import com.squareup.picasso.Picasso;

public class ProductRecyclerAdapter extends FirebaseRecyclerAdapter<UploadProductModel, ProductRecyclerAdapter.myViewHolder> {



    public ProductRecyclerAdapter(@NonNull FirebaseRecyclerOptions<UploadProductModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ProductRecyclerAdapter.myViewHolder holder, int position, @NonNull UploadProductModel model) {

        Picasso.get().load(model.getImage()).placeholder(R.drawable.home_24).into(holder.imageView);
        holder.title.setText(model.getTitle());
        holder.description.setText(model.getDescription());
        holder.price.setText(model.getPrice());



    }

    @NonNull
    @Override
    public ProductRecyclerAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_recycler, parent, false);
        return new myViewHolder(view);
    }

    public class myViewHolder extends RecyclerView.ViewHolder {

        TextView title, price, description;
        ImageView imageView;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.textDescription);
            price = itemView.findViewById(R.id.price);
            imageView = itemView.findViewById(R.id.Image);
        }
    }
}
