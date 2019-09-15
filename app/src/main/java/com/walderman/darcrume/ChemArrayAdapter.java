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

public class ChemArrayAdapter extends ArrayAdapter<Chem> {
    public ChemArrayAdapter(@NonNull Context context, ArrayList<Chem> chemList) {
        super(context, 0, chemList);
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
                    R.layout.spinner_chemitem, parent, false
            );
        }

        ImageView imageView = convertView.findViewById(R.id.imageViewChem1);
        TextView textView_Brand = convertView.findViewById(R.id.textView_ChemBrand);
        TextView textView_Name = convertView.findViewById(R.id.textView_ChemName);
        TextView textView_ChemText1 = convertView.findViewById(R.id.textView_ChemText1);
        TextView textView_ChemText2 = convertView.findViewById(R.id.textView_ChemText2);

        Chem currentItem = getItem(position);

        imageView.setImageResource(R.drawable.chem_placeholder);

        textView_Brand.setText(currentItem.getBrand());
        textView_Name.setText(currentItem.getName());
        textView_ChemText1.setText("placeholder");
        textView_ChemText2.setText("placeholder2");

        return convertView;
    }
}
