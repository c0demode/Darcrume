package com.walderman.darcrume;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

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
        public TextView mTextView_Brand;
        public TextView mTextView_Name;
        public TextView mTextView_ISO;
        public TextView mTextView_Exposures;

        public MyViewHolder(@NonNull View itemView, final OnItemClickListener listener){
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView1);
            mTextView_Brand = itemView.findViewById(R.id.textView_Brand);
            mTextView_Name = itemView.findViewById(R.id.textView_Name);
            mTextView_ISO = itemView.findViewById(R.id.textView_ISO);
            mTextView_Exposures = itemView.findViewById(R.id.textView_Exposures);

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
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, parent, false);
        MyViewHolder mvh = new MyViewHolder(v, listener);
        return mvh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position){
        Film currentItem = filmList.get(position);
        int drawableId=-1;
        //holder.imageView.setImageResource(currentItem.getImageResource());
        holder.mTextView_Brand.setText(currentItem.getBrand());
        holder.mTextView_Name.setText(currentItem.getName());
        holder.mTextView_ISO.setText("ISO: " + currentItem.getIso());
        holder.mTextView_Exposures.setText("Exp: " + currentItem.getExp());
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
