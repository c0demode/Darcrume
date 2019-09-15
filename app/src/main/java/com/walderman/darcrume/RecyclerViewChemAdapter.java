package com.walderman.darcrume;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewChemAdapter extends RecyclerView.Adapter<RecyclerViewChemAdapter.MyViewHolder> {
    private ArrayList<Chem> chemList;
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
        public TextView textView_ItemText1;
        public TextView textView_ItemText2;

        public MyViewHolder(@NonNull View itemView, final OnItemClickListener listener){
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView1);
            textView_Brand = itemView.findViewById(R.id.textView_ItemBrand);
            textView_Name = itemView.findViewById(R.id.textView_ItemName);
            textView_ItemText1 = itemView.findViewById(R.id.textView_ItemText1);
            textView_ItemText2 = itemView.findViewById(R.id.textView_ItemText2);

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

    public RecyclerViewChemAdapter(ArrayList<Chem> chemList){
        this.chemList = chemList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item, parent, false);
        MyViewHolder mvh = new MyViewHolder(v, listener);
        return mvh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position){
        Chem currentItem = chemList.get(position);
        int drawableId=-1;
        //holder.imageView.setImageResource(currentItem.getImageResource());
        holder.textView_Brand.setText(currentItem.getBrand());
        holder.textView_Name.setText(currentItem.getName());
        holder.textView_ItemText1.setText("ISO: " + currentItem.getBw_Color());
        holder.textView_ItemText2.setText("Exp: " + currentItem.getChemRole());
        if(currentItem.getBw_Color().equals("BW")){
            drawableId=R.drawable.bw;
        }else{
            drawableId=R.drawable.color;
        }
        holder.imageView.setImageResource(drawableId);
    }

    @Override
    public int getItemCount(){
        return chemList.size();
    }
}
