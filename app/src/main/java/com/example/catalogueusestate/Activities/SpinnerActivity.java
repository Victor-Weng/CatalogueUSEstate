/*package com.example.catalogueusestate.Activities;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class SpinnerActivity extends MainActivity implements AdapterView.OnItemSelectedListener {


    //THIS CLASS IS NOT USED FOR NOW
   String StateCode = "AL";

    //ERROR
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        try {


            StateCode = parent.getItemAtPosition(position).toString();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        //nothing for now
    }

    public String getState()
    {

        return StateCode;

    }

}*/
