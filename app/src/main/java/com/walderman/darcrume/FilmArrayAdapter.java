package com.walderman.darcrume;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class FilmArrayAdapter extends ArrayAdapter<Film> {
    public FilmArrayAdapter(@NonNull Context context, ArrayList<Film> filmList) {
        super(context, 0, filmList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    private View initView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.spinner_filmitem, parent, false
            );
        }

        ImageView imageView = convertView.findViewById(R.id.imageView1);
        TextView textView_Brand = convertView.findViewById(R.id.textView_ItemBrand);
        TextView textView_Name = convertView.findViewById(R.id.textView_ItemName);
        TextView textView_ISO = convertView.findViewById(R.id.textView_ItemText1);
        TextView textView_Exposures = convertView.findViewById(R.id.textView_ItemText2);

        Film currentItem = getItem(position);

        if(currentItem.getType().equals("BW")){
            imageView.setImageResource(R.drawable.ic_camera_roll_bw);
        }else{
            imageView.setImageResource(R.drawable.ic_camera_roll_color);
        }

        textView_Brand.setText(currentItem.getBrand());
        textView_Name.setText(currentItem.getName());
        textView_ISO.setText("ISO: " + currentItem.getIso());
        textView_Exposures.setText("Exp: " + currentItem.getExp());

        return convertView;
    }
}
