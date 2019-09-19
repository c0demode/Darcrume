package com.walderman.darcrume;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * TODO
 * add ability to Add / Save / Delete / Copy
 * How easy to add circle "+" symbol for Add?
 */

public class Controller_ManageChems extends AppCompatActivity {
    private DatabaseHelper db;
    private RecyclerView recyclerView;
    private RecyclerViewChemAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Chem> chemList;
    private ArrayList<Chem> filteredChemList = new ArrayList<>();
    private Button btnChemSave;
    private Button btnChemClear;
    private Button btnChemAdd;
    private CheckBox cbBw;
    private CheckBox cbColor;
    private CheckBox cbBwDev;
    private CheckBox cbBwStop;
    private CheckBox cbBwFix;
    private CheckBox cbColDev;
    private CheckBox cbColBlix;
    private CheckBox cbColStab;
    private EditText editText_ChemBrand;
    private EditText editText_ChemName;
    private RadioGroup radioGroupChemBwCol;
    private RadioButton radBtnChemBw;
    private RadioButton radBtnChemCol;
    private Spinner spinnerChems;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_chems);

        db = new DatabaseHelper(getApplicationContext());

        configureVariables();

        //addSampleChem();
        createChemList();
        buildRecyclerView();
    }

    private void configureVariables() {
        editText_ChemBrand = findViewById(R.id.editTextChemsMfr);
        editText_ChemName = findViewById(R.id.editTextChemsName);

        cbBw = findViewById(R.id.cbBW);
        cbColor = findViewById(R.id.cbColor);
        cbBwDev = findViewById(R.id.cbBwDev);
        cbBwStop = findViewById(R.id.cbBwStop);
        cbBwFix = findViewById(R.id.cbBwFixer);
        cbColDev = findViewById(R.id.cbColorDeveloper);
        cbColBlix = findViewById(R.id.cbColorBlix);
        cbColStab = findViewById(R.id.cbColorStab);

        spinnerChems = findViewById(R.id.spinnerChems);
        btnChemSave = findViewById(R.id.btnChemSave);
        btnChemClear = findViewById(R.id.btnChemClear);
        btnChemAdd = findViewById(R.id.btnChemAdd);

        radioGroupChemBwCol = findViewById(R.id.radioGroupChemBwCol);
        radBtnChemBw = findViewById(R.id.radBtnChemBw);
        radBtnChemCol = findViewById(R.id.radBtnChemCol);

        //set checkboxes to disabled state
        toggleAvailableCheckboxes();

        //configure click listeners
        cbBw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleAvailableCheckboxes();
                filterArrayList(chemList);
            }
        });

        cbColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleAvailableCheckboxes();
                filterArrayList(chemList);
            }
        });

        cbBwDev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filterArrayList(chemList);
            }
        });

        cbBwStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filterArrayList(chemList);
            }
        });

        cbBwFix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filterArrayList(chemList);
            }
        });

        cbColDev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filterArrayList(chemList);
            }
        });

        cbColBlix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filterArrayList(chemList);
            }
        });

        cbColStab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filterArrayList(chemList);
            }
        });

        radBtnChemBw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //can i assign spinner entries to use another array?
            }
        });

        btnChemClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearFields();
            }
        });

        btnChemAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewChem();
            }
        });
    }


    private void buildRecyclerView() {
        recyclerView = findViewById(R.id.recyclerViewChem);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        adapter = new RecyclerViewChemAdapter(filteredChemList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new RecyclerViewChemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //create a method and call it here
                //that brings up more detail and option to edit item
                getSelectedChemInfo(position);
            }
        });
    }

    private void refreshRecyclerView(int position) {
        adapter.notifyItemChanged(position);
    }

    private void createChemList() {
        //db.truncateChemsTable();
        chemList = db.getAllChems();
    }

    private void toggleAvailableCheckboxes(){
        //enable or disable checkboxes for black and white chems
        if(cbBw.isChecked()){
            //enable the checkboxes for black and white chems
            cbBwDev.setEnabled(true);
            cbBwStop.setEnabled(true);
            cbBwFix.setEnabled(true);
        }else{
            //disable the checkboxes
            cbBwDev.setEnabled(false);
            cbBwStop.setEnabled(false);
            cbBwFix.setEnabled(false);
            //set them to un-checked
            cbBwDev.setChecked(false);
            cbBwStop.setChecked(false);
            cbBwFix.setChecked(false);
        }

        //enable or disable checkboxes for color chems
        if(cbColor.isChecked()){
            //enable the color chem checkboxes
            cbColDev.setEnabled(true);
            cbColBlix.setEnabled(true);
            cbColStab.setEnabled(true);
        }else{
            //disable the checkboxes
            cbColDev.setEnabled(false);
            cbColBlix.setEnabled(false);
            cbColStab.setEnabled(false);
            //set them to un-checked
            cbColDev.setChecked(false);
            cbColBlix.setChecked(false);
            cbColStab.setChecked(false);
        }
    }

    /**
     * Populate editable fields with data from Chem object clicked by user
     *
     * @param position
     */
    private void getSelectedChemInfo(final int position) {
        final Chem selectedChem = filteredChemList.get(position);

        editText_ChemBrand.setText(selectedChem.getBrand());
        editText_ChemName.setText(selectedChem.getName());

        switch (selectedChem.getChemRole()) {
            case "BWDEV":
                spinnerChems.setSelection(1);
                break;
            case "BWSTB":
                spinnerChems.setSelection(2);
                break;
            case "BWFIX":
                spinnerChems.setSelection(3);
                break;
            case "CLRDEV":
                spinnerChems.setSelection(4);
                break;
            case "CLRBLX":
                spinnerChems.setSelection(5);
                break;
            case "CLRSTB":
                spinnerChems.setSelection(6);
                break;
        }

        if(selectedChem.getBw_Color().equals("BW")){
            radBtnChemBw.setChecked(true);
        }else{
            radBtnChemCol.setChecked(true);
        }

        btnChemSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateInput()){
                    saveChem(position);
                }
            }
        });
    }

    private boolean validateInput() {
        if(editText_ChemBrand.getText().toString().trim().length()<1){
            return false;
        }
        if(editText_ChemName.getText().toString().trim().length()<1){
            return false;
        }
        if(radBtnChemBw.isChecked() == false && radBtnChemCol.isChecked() == false){
            return false;
        }
        //do not allow color chems to be selected from spinner if bw chem radio button is selected
        if(radBtnChemBw.isChecked() &&(spinnerChems.getSelectedItem().toString().contains("Color"))){
            Toast.makeText(this, "Mismatch radio buttons and spinner BW to Col",Toast.LENGTH_SHORT).show();
            return false;
        }
        //do not allow bw chems to be selected from spinner if color chem radio button is selected
        if(radBtnChemCol.isChecked() && (spinnerChems.getSelectedItem().toString().contains("BW"))){
            Toast.makeText(this, "Mismatch radio buttons and spinner Col to BW",Toast.LENGTH_SHORT).show();
            return false;
        }

        //do not allow selection of "Select a chem..." item from spinner
        if(spinnerChems.getSelectedItemPosition() == 0){
            Toast.makeText(this, "Select a chemistry role.",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void saveChem(int position) {
        Chem chem = filteredChemList.get(position);
        chem.setName(editText_ChemName.getText().toString());
        chem.setBrand(editText_ChemBrand.getText().toString());
        if(radBtnChemBw.isChecked()){
            chem.setBw_Color("BW");
        }
        if(radBtnChemCol.isChecked()){
            chem.setBw_Color("Color");
        }
        chem.setChemRole(spinnerChems.getSelectedItem().toString());
        Chem updatedChem = db.updateChem(chem);
        chemList.set(position, updatedChem);
        refreshRecyclerView(position);
    }

    private void clearFields() {
        editText_ChemBrand.setText("");
        editText_ChemName.setText("");
        radBtnChemBw.setChecked(false);
        radBtnChemCol.setChecked(false);
        spinnerChems.setSelection(0);
    }

    private void addNewChem(){
        if(validateInput()){
            Chem newChem = new Chem();
            newChem.setBrand(editText_ChemBrand.getText().toString().trim());
            newChem.setName(editText_ChemName.getText().toString().trim());
            if(cbBw.isChecked())newChem.setBw_Color("BW");
            if(cbColor.isChecked())newChem.setBw_Color("Color");
            switch(spinnerChems.getSelectedItem().toString()){
                case"BW Developer":
                    newChem.setChemRole("BWDEV");
                    break;
                case"BW Stop Bath":
                    newChem.setChemRole("BWSTP");
                    break;
                case"BW Fixer":
                    newChem.setChemRole("BWFIX");
                    break;
                case"Color Developer":
                    newChem.setChemRole("CLRDEV");
                    break;
                case"Color Blix":
                    newChem.setChemRole("CLRBLX");
                    break;
                case"Color Stabilizer":
                    newChem.setChemRole("CLRSTB");
                    break;
            }
            db.insertNewChem(newChem);
            filterArrayList(db.getAllChems());
            adapter.notifyDataSetChanged();
        }
    }

    private ArrayList<Chem> filterArrayList(ArrayList<Chem> chemListToFilter){
        if(filteredChemList.size() >0){
            filteredChemList.clear();
        }
        //check each item in chemListToFilter.
        //if the item matches one of the checkbox filters,
        //add item to new list 'filteredList'
        for(int i = 0; i < chemListToFilter.size(); i++){
            //check each checkbox, if checked, include "type" in result list

            if(cbBwDev.isChecked() && chemListToFilter.get(i).getChemRole().equals("BWDEV")){
                filteredChemList.add(chemListToFilter.get(i));
            }else if(cbBwStop.isChecked() && chemListToFilter.get(i).getChemRole().equals("BWSTP")){
                filteredChemList.add(chemListToFilter.get(i));
            }else if(cbBwFix.isChecked() && chemListToFilter.get(i).getChemRole().equals("BWFIX")){
                filteredChemList.add(chemListToFilter.get(i));
            }else if(cbColDev.isChecked() && chemListToFilter.get(i).getChemRole().equals("CLRDEV")){
                filteredChemList.add(chemListToFilter.get(i));
            }else if(cbColBlix.isChecked() && chemListToFilter.get(i).getChemRole().equals("CLRBLX")){
                filteredChemList.add(chemListToFilter.get(i));
            }else if(cbColStab.isChecked() && chemListToFilter.get(i).getChemRole().equals("CLRSTB")){
                filteredChemList.add(chemListToFilter.get(i));
            }
        }
        buildRecyclerView();
        return filteredChemList;
    }

}
