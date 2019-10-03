package com.walderman.darcrume;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatDialogFragment;

public class ExampleDialog extends AppCompatDialogFragment {
    private EditText editTextBrand;
    private EditText editTextName;
    private RadioButton radBw;
    private RadioButton radCol;
    private RadioButton rad24;
    private RadioButton rad36;
    private Spinner spinnerIso;
    private ExampleDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.customdialog_film, null);

        builder.setView(view)
                .setTitle("Film Manager")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Film film = new Film();
                        film.setBrand(editTextBrand.getText().toString());
                        film.setName(editTextName.getText().toString());

                        if(radBw.isChecked())
                            film.setType("BW");
                        else if(radCol.isChecked())
                            film.setType("COLOR");

                        film.setIso(Integer.parseInt(spinnerIso.getSelectedItem().toString()));

                        if(rad24.isChecked())
                            film.setExp(24);
                        else if(rad36.isChecked())
                            film.setExp(36);

                        listener.applyValues(film);

                    }
                });
        editTextBrand = view.findViewById(R.id.editTxtFilmDialogBrand);
        editTextName = view.findViewById(R.id.editTxtFilmDialogName);
        radBw = view.findViewById(R.id.radBtnFilmDialogBw);
        radCol = view.findViewById(R.id.radBtnFilmDialogCol);
        rad24 = view.findViewById(R.id.radBtnFilmDialog24Exp);
        rad36 = view.findViewById(R.id.radBtnFilmDialog36Exp);
        spinnerIso = view.findViewById(R.id.spinnerFilmDialogIso);
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (ExampleDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement ExampleDialogListener");
        }
    }

    public interface ExampleDialogListener{
        void applyValues(Film film);
    }
}
