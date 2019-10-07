package com.walderman.darcrume;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewFilmAdapter extends RecyclerView.Adapter<RecyclerViewFilmAdapter.MyViewHolder> {
    private ArrayList<Film> filmList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageView;
        public TextView textView_Brand;
        public TextView textView_Name;
        public TextView textView_ISO;
        public TextView textView_Exposures;
        public ConstraintLayout constraintLayout;

        public MyViewHolder(@NonNull View itemView, final OnItemClickListener listener){
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView1);
            textView_Brand = itemView.findViewById(R.id.textView_ItemBrand);
            textView_Name = itemView.findViewById(R.id.textView_ItemName);
            textView_ISO = itemView.findViewById(R.id.textView_ItemText1);
            textView_Exposures = itemView.findViewById(R.id.textView_ItemText2);
            constraintLayout = itemView.findViewById(R.id.recyclerView_Item_ConstraintLayout);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    public RecyclerViewFilmAdapter(ArrayList<Film> filmList){
        this.filmList = filmList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, parent, false);
        MyViewHolder mvh = new MyViewHolder(v, listener);
        return mvh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position){
        Film currentItem = filmList.get(position);
        int drawableId;
        //holder.imageView.setImageResource(currentItem.getImageResource());
        holder.textView_Brand.setText(currentItem.getBrand());
        holder.textView_Name.setText(currentItem.getName());
        holder.textView_ISO.setText("ISO: " + currentItem.getIso());
        holder.textView_Exposures.setText("Exp: " + currentItem.getExp());
        if(currentItem.getType().equals("BW")){
            drawableId=R.drawable.ic_camera_roll_bw;
        }else{
            drawableId=R.drawable.ic_camera_roll_color;
        }
        holder.imageView.setImageResource(drawableId);
    }

    @Override
    public int getItemCount(){
        return filmList.size();
    }
}
