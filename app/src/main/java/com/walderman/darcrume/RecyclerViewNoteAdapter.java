package com.walderman.darcrume;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewNoteAdapter extends RecyclerView.Adapter<RecyclerViewNoteAdapter.MyViewHolder> {
    private ArrayList<Note> noteList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView noteDate;
        public TextView noteFilm;
        public TextView noteText;
        public ConstraintLayout constraintLayout;

        public MyViewHolder(@NonNull View itemView, final OnItemClickListener listener){
            super(itemView);
            noteDate = itemView.findViewById(R.id.tvSpinnerNoteDate);
            noteFilm = itemView.findViewById(R.id.tvSpinnerNoteFilm);
            noteText = itemView.findViewById(R.id.tvSpinnerNoteNote);
            constraintLayout = itemView.findViewById(R.id.rvNoteConstraintLayout);

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

    public RecyclerViewNoteAdapter(ArrayList<Note> noteList){
        this.noteList = noteList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_noteitem, parent, false);
        MyViewHolder mvh = new MyViewHolder(v, listener);
        return mvh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position){
        Note currentItem = noteList.get(position);
        holder.noteDate.setText(currentItem.getNoteDate());
        holder.noteFilm.setText(currentItem.getNoteFilm());
        holder.noteText.setText(currentItem.getNoteText());
    }

    @Override
    public int getItemCount(){
        return noteList.size();
    }
}
