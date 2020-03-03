package com.example.movierating;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ItemListViewHolder> {


    ArrayList<ItemList> itemListArrayList;
    ListItemClickListener itemClickListener;
    Context context;

    public interface ListItemClickListener {
        void onListItemClickListener(int clickedItemIndex);
    }

    public RecyclerAdapter(Context context, ArrayList<ItemList> itemListArrayList,ListItemClickListener itemClickListener) {
        this.context = context;
        this.itemListArrayList = itemListArrayList;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ItemListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_view,parent,false);

        ItemListViewHolder ItemListViewHolder = new ItemListViewHolder(view);

        return ItemListViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemListViewHolder holder, int position) {

        ItemList list = itemListArrayList.get(position);
        holder.MovieTitle.setText(""+list.title);
        holder.MovieGenres.setText(""+list.genres);
        holder.rating.setText(""+list.rating);
        if (list.getImageAdress() != null) {
            Glide.with(context).load(list.getImageAdress()).into(holder.MovieImage);
        }
    }

    @Override
    public int getItemCount() {
        return itemListArrayList.size();
    }

    public class ItemListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView MovieTitle,MovieGenres,rating;
        ImageView MovieImage;

         public ItemListViewHolder(@NonNull View itemView) {
            super(itemView);

            MovieTitle= itemView.findViewById(R.id.m_title);
            MovieGenres= itemView.findViewById(R.id.genres);
            rating= itemView.findViewById(R.id.rating);
            MovieImage= itemView.findViewById(R.id.m_image);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            ItemList model = itemListArrayList.get(getAdapterPosition());
            String message = model.getTitle();
            Toast.makeText(v.getContext(), message, Toast.LENGTH_LONG).show();

        }
    }

}