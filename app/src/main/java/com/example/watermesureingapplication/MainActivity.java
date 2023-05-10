package com.example.watermesureingapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import java.text.DateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    TextView waterCount, dateView;
    MaterialButton watersip, water250, water500, water1l, resetButton;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Calendar calendar = Calendar.getInstance();
        String currDate = DateFormat.getDateInstance().format(calendar.getTime());

        waterCount = findViewById(R.id.water_count);
        dateView = findViewById(R.id.date_view);
        dateView.setText(currDate);

        assignId(watersip, R.id.sip_button);
        assignId(water250, R.id.water250_button);
        assignId(water500, R.id.water500_button);
        assignId(water1l, R.id.water1l_button);
        assignId(resetButton, R.id.reset_button);

        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String lastTextValue = prefs.getString("amount", "");
        if (lastTextValue.isEmpty()) {
            waterCount.setText("0ml");
        } else {
            waterCount.setText(lastTextValue);
        }


        /*if(sharedPreferences.contains("amount")){
            waterCount.setText(sharedPreferences.getString("amount", ""));
            if(sharedPreferences.getString("date", "").equals(currDate)){
                //dateView.setText(currDate);
                waterCount.setText(sharedPreferences.getString("amount", ""));
            }
        }*/

        /*sharedPreferences = getSharedPreferences("date", Context.MODE_PRIVATE);
        String myValue = sharedPreferences.getString("myKey", "");*/
    }

    void assignId(MaterialButton btn, int id){
        btn = findViewById(id);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        MaterialButton button = (MaterialButton) view;
        String buttonText = button.getText().toString();

        if(buttonText.equals("1 Sip")){
            String amount = waterCount.getText().toString();
            int count = Integer.parseInt(amount.replace("ml", ""));
            count += 50;
            waterCount.setText(Integer.toString(count) + "ml");
            return;
        }

        if(buttonText.equals("250ml")){
            String amount = waterCount.getText().toString();
            int count = Integer.parseInt(amount.replace("ml", ""));
            /*int count = Integer.parseInt(waterCount.getText().toString());*/
            count += 250;
            waterCount.setText(Integer.toString(count) + "ml");
            return;
        }

        if(buttonText.equals("500ml")){
            String amount = waterCount.getText().toString();
            int count = Integer.parseInt(amount.replace("ml", ""));
            count += 500;
            waterCount.setText(Integer.toString(count) + "ml");
            return;
        }

        if(buttonText.equals("1l")){
            String amount = waterCount.getText().toString();
            int count = Integer.parseInt(amount.replace("ml", ""));
            count += 1000;
            waterCount.setText((Integer.toString(count)) + "ml");
            return;
        }

        if(buttonText.equals("reset")){
            showConfirmationDialog();
        }

        /*SharedPreferences.Editor editor = sharedPreferences.edit();
        //editor.putString("date", dateView.getText().toString());
        editor.putString("amount", waterCount.getText().toString());
        editor.apply();*/
    }

    private void showConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to reset?")
                .setTitle("Confirmation")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK button
                        waterCount.setText("0ml");
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }


    private void saveWaterAmount(){
        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("amount", waterCount.getText().toString());
        editor.apply();
    }

    @Override
    protected void onStop() {
        super.onStop();
        saveWaterAmount();
    }
}