package com.walderman.darcrume;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.MyViewHolder> {
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

        public MyViewHolder(@NonNull View itemView, final OnItemClickListener listener){
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView1);
            textView_Brand = itemView.findViewById(R.id.textView_Brand);
            textView_Name = itemView.findViewById(R.id.textView_Name);
            textView_ISO = itemView.findViewById(R.id.textView_ISO);
            textView_Exposures = itemView.findViewById(R.id.textView_Exposures);

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

    public RVAdapter(ArrayList<Film> filmList){
        this.filmList = filmList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_filmitem, parent, false);
        MyViewHolder mvh = new MyViewHolder(v, listener);
        return mvh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position){
        Film currentItem = filmList.get(position);
        int drawableId=-1;
        //holder.imageView.setImageResource(currentItem.getImageResource());
        holder.textView_Brand.setText(currentItem.getBrand());
        holder.textView_Name.setText(currentItem.getName());
        holder.textView_ISO.setText("ISO: " + currentItem.getIso());
        holder.textView_Exposures.setText("Exp: " + currentItem.getExp());
        if(currentItem.getType().equals("BW")){
            drawableId=R.drawable.bw;
        }else{
            drawableId=R.drawable.color;
        }
        holder.imageView.setImageResource(drawableId);
    }

    @Override
    public int getItemCount(){
        return filmList.size();
    }
}
