package com.example.movietickets;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DumboB2Activity extends AppCompatActivity {

    private String selectedDate;
    private String selectedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dumbo_b2);

        /* Get dates in a list to be used by first spinner */
        DateTime datetime = new DateTime();

        final List<String> dates = datetime.getDates();
        /* Reorder dates list to put the day after tomorrow on top */
        final List<String> orderedDates = new ArrayList<>();
        orderedDates.add(dates.get(1));
        orderedDates.add(dates.get(0));
        orderedDates.add(dates.get(2));
        orderedDates.add(dates.get(3));

        /* Create first spinner for dates */
        final ArrayAdapter<String> dateAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, orderedDates);
        dateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner dateSpinner = findViewById(R.id.spin_date);
        dateSpinner.setAdapter(dateAdapter);

        /* Get times in a list to be used by second spinner */
        final List<String> times = datetime.getTimes(getResources().getString(R.string.dumbo_time2),
                getResources().getString(R.string.dumbo_time1));

        /* Create second spinner for times */
        final ArrayAdapter<String> timeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, times);
        timeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        final Spinner timeSpinner = findViewById(R.id.spin_time);

        /* When a value in first spinner is selected, second spinner is activated */
        dateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                selectedDate = parent.getItemAtPosition(position).toString();
                timeSpinner.setAdapter(timeAdapter);

                /* If the last two values in spinner are selected, show toast message */
                if (selectedDate.equals(dates.get(2)) || selectedDate.equals(dates.get(3))) {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.toast_message),
                            Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        timeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                selectedTime = parent.getItemAtPosition(position).toString();

                /* Redirects to the appropriate reservation page based on which values in
                    the spinners have been selected */
                if (selectedDate.equals(orderedDates.get(0)) && selectedTime.equals(times.get(1))) {
                    startActivity(new Intent(view.getContext(), DumboB1Activity.class));
                }
                if (selectedDate.equals(orderedDates.get(1)) && selectedTime.equals(times.get(1))) {
                    startActivity(new Intent(view.getContext(), DumboA1Activity.class));
                }
                if (selectedDate.equals(orderedDates.get(1)) && selectedTime.equals(times.get(0))) {
                    startActivity(new Intent(view.getContext(), DumboA2Activity.class));
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        /* Loads the previously saved preferences for which buttons were which colors */
        showPreviousState();

    }

    private int quantity;
    private Map<Integer, Integer> colorMap = new HashMap<>();

    /* Changes the seat button color when clicked on and stores button id and color to a HashMap */
    public void seatOnClick(View view) {

        int id = view.getId();
        Button btn = findViewById(id);
        ColorDrawable btnColor = (ColorDrawable) btn.getBackground();
        int colorId = btnColor.getColor();

        if (colorId == Color.GREEN) {
            btn.setBackgroundColor(Color.RED);
            quantity += 1;
            displayQuantity(quantity);
            colorMap.put(id, Color.RED);

        }
        else {
            btn.setBackgroundColor(Color.GREEN);
            quantity -= 1;
            displayQuantity(quantity);
            colorMap.put(id, Color.GREEN);
        }
    }

    /* Saves preferences for which button ids were changed to which colors from the HashMap */
    private void saveColor(int id, int color) {
        SharedPreferences sharedPreferences = getSharedPreferences("ButtonColor", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(Integer.toString(id), color);
        editor.apply();
    }

    /* Loads the saved preferences for button color */
    private int loadColor(int id) {
        SharedPreferences sharedPreferences = getSharedPreferences("ButtonColor", MODE_PRIVATE);
        return sharedPreferences.getInt(Integer.toString(id), Color.GREEN);
    }

    /* Shows the number of seats selected (red seats) */
    private void displayQuantity(int quantity) {

        TextView tv = findViewById(R.id.quantity);
        tv.setText(Integer.toString(quantity));
    }

    /* Redirects to another page upon button click */
    public void openResultPage(View view) {
        for (Map.Entry<Integer, Integer> entry : colorMap.entrySet()) {
            saveColor(entry.getKey(), entry.getValue());
        }
        if (quantity == 0) {
            Intent intent = new Intent(this, CancelActivity.class);
            startActivity(intent);
        }
        else {
            Intent intent = new Intent(this, ReserveActivity.class);
            startActivity(intent);
        }
    }

    /* Loads the color state of each button (by id) and displays the correct color on screen */
    private void showPreviousState() {

        final Button bA1 = findViewById(R.id.dbdA1);
        if (loadColor(R.id.dbdA1) == Color.RED) {
            bA1.setBackgroundColor(loadColor(R.id.dbdA1));
            quantity += 1;
        }

        final Button bA2 = findViewById(R.id.dbdA2);
        if (loadColor(R.id.dbdA2) == Color.RED) {
            bA2.setBackgroundColor(loadColor(R.id.dbdA2));
            quantity += 1;
        }

        final Button bA3 = findViewById(R.id.dbdA3);
        if (loadColor(R.id.dbdA3) == Color.RED) {
            bA3.setBackgroundColor(loadColor(R.id.dbdA3));
            quantity += 1;
        }

        final Button bA4 = findViewById(R.id.dbdA4);
        if (loadColor(R.id.dbdA4) == Color.RED) {
            bA4.setBackgroundColor(loadColor(R.id.dbdA4));
            quantity += 1;
        }

        final Button bA5 = findViewById(R.id.dbdA5);
        if (loadColor(R.id.dbdA5) == Color.RED) {
            bA5.setBackgroundColor(loadColor(R.id.dbdA5));
            quantity += 1;
        }

        final Button bA6 = findViewById(R.id.dbdA6);
        if (loadColor(R.id.dbdA6) == Color.RED) {
            bA6.setBackgroundColor(loadColor(R.id.dbdA6));
            quantity += 1;
        }

        final Button bB1 = findViewById(R.id.dbdB1);
        if (loadColor(R.id.dbdB1) == Color.RED) {
            bB1.setBackgroundColor(loadColor(R.id.dbdB1));
            quantity += 1;
        }

        final Button bB2 = findViewById(R.id.dbdB2);
        if (loadColor(R.id.dbdB2) == Color.RED) {
            bB2.setBackgroundColor(loadColor(R.id.dbdB2));
            quantity += 1;
        }

        final Button bB3 = findViewById(R.id.dbdB3);
        if (loadColor(R.id.dbdB3) == Color.RED) {
            bB3.setBackgroundColor(loadColor(R.id.dbdB3));
            quantity += 1;
        }

        final Button bB4 = findViewById(R.id.dbdB4);
        if (loadColor(R.id.dbdB4) == Color.RED) {
            bB4.setBackgroundColor(loadColor(R.id.dbdB4));
            quantity += 1;
        }

        final Button bB5 = findViewById(R.id.dbdB5);
        if (loadColor(R.id.dbdB5) == Color.RED) {
            bB5.setBackgroundColor(loadColor(R.id.dbdB5));
            quantity += 1;
        }

        final Button bB6 = findViewById(R.id.dbdB6);
        if (loadColor(R.id.dbdB6) == Color.RED) {
            bB6.setBackgroundColor(loadColor(R.id.dbdB6));
            quantity += 1;
        }

        final Button bC1 = findViewById(R.id.dbdC1);
        if (loadColor(R.id.dbdC1) == Color.RED) {
            bC1.setBackgroundColor(loadColor(R.id.dbdC1));
            quantity += 1;
        }

        final Button bC2 = findViewById(R.id.dbdC2);
        if (loadColor(R.id.dbdC2) == Color.RED) {
            bC2.setBackgroundColor(loadColor(R.id.dbdC2));
            quantity += 1;
        }

        final Button bC3 = findViewById(R.id.dbdC3);
        if (loadColor(R.id.dbdC3) == Color.RED) {
            bC3.setBackgroundColor(loadColor(R.id.dbdC3));
            quantity += 1;
        }

        final Button bC4 = findViewById(R.id.dbdC4);
        if (loadColor(R.id.dbdC4) == Color.RED) {
            bC4.setBackgroundColor(loadColor(R.id.dbdC4));
            quantity += 1;
        }

        final Button bC5 = findViewById(R.id.dbdC5);
        if (loadColor(R.id.dbdC5) == Color.RED) {
            bC5.setBackgroundColor(loadColor(R.id.dbdC5));
            quantity += 1;
        }

        final Button bC6 = findViewById(R.id.dbdC6);
        if (loadColor(R.id.dbdC6) == Color.RED) {
            bC6.setBackgroundColor(loadColor(R.id.dbdC6));
            quantity += 1;
        }

        final Button bD1 = findViewById(R.id.dbdD1);
        if (loadColor(R.id.dbdD1) == Color.RED) {
            bD1.setBackgroundColor(loadColor(R.id.dbdD1));
            quantity += 1;
        }

        final Button bD2 = findViewById(R.id.dbdD2);
        if (loadColor(R.id.dbdD2) == Color.RED) {
            bD2.setBackgroundColor(loadColor(R.id.dbdD2));
            quantity += 1;
        }

        final Button bD3 = findViewById(R.id.dbdD3);
        if (loadColor(R.id.dbdD3) == Color.RED) {
            bD3.setBackgroundColor(loadColor(R.id.dbdD3));
            quantity += 1;
        }

        final Button bD4 = findViewById(R.id.dbdD4);
        if (loadColor(R.id.dbdD4) == Color.RED) {
            bD4.setBackgroundColor(loadColor(R.id.dbdD4));
            quantity += 1;
        }

        final Button bD5 = findViewById(R.id.dbdD5);
        if (loadColor(R.id.dbdD5) == Color.RED) {
            bD5.setBackgroundColor(loadColor(R.id.dbdD5));
            quantity += 1;
        }

        final Button bD6 = findViewById(R.id.dbdD6);
        if (loadColor(R.id.dbdD6) == Color.RED) {
            bD6.setBackgroundColor(loadColor(R.id.dbdD6));
            quantity += 1;
        }

        final Button bE1 = findViewById(R.id.dbdE1);
        if (loadColor(R.id.dbdE1) == Color.RED) {
            bE1.setBackgroundColor(loadColor(R.id.dbdE1));
            quantity += 1;
        }

        final Button bE2 = findViewById(R.id.dbdE2);
        if (loadColor(R.id.dbdE2) == Color.RED) {
            bE2.setBackgroundColor(loadColor(R.id.dbdE2));
            quantity += 1;
        }

        final Button bE3 = findViewById(R.id.dbdE3);
        if (loadColor(R.id.dbdE3) == Color.RED) {
            bE3.setBackgroundColor(loadColor(R.id.dbdE3));
            quantity += 1;
        }

        final Button bE4 = findViewById(R.id.dbdE4);
        if (loadColor(R.id.dbdE4) == Color.RED) {
            bE4.setBackgroundColor(loadColor(R.id.dbdE4));
            quantity += 1;
        }

        final Button bE5 = findViewById(R.id.dbdE5);
        if (loadColor(R.id.dbdE5) == Color.RED) {
            bE5.setBackgroundColor(loadColor(R.id.dbdE5));
            quantity += 1;
        }

        final Button bE6 = findViewById(R.id.dbdE6);
        if (loadColor(R.id.dbdE6) == Color.RED) {
            bE6.setBackgroundColor(loadColor(R.id.dbdE6));
            quantity += 1;
        }

        final Button bF1 = findViewById(R.id.dbdF1);
        if (loadColor(R.id.dbdF1) == Color.RED) {
            bF1.setBackgroundColor(loadColor(R.id.dbdF1));
            quantity += 1;
        }

        final Button bF2 = findViewById(R.id.dbdF2);
        if (loadColor(R.id.dbdF2) == Color.RED) {
            bF2.setBackgroundColor(loadColor(R.id.dbdF2));
            quantity += 1;
        }

        final Button bF3 = findViewById(R.id.dbdF3);
        if (loadColor(R.id.dbdF3) == Color.RED) {
            bF3.setBackgroundColor(loadColor(R.id.dbdF3));
            quantity += 1;
        }

        final Button bF4 = findViewById(R.id.dbdF4);
        if (loadColor(R.id.dbdF4) == Color.RED) {
            bF4.setBackgroundColor(loadColor(R.id.dbdF4));
            quantity += 1;
        }

        final Button bF5 = findViewById(R.id.dbdF5);
        if (loadColor(R.id.dbdF5) == Color.RED) {
            bF5.setBackgroundColor(loadColor(R.id.dbdF5));
            quantity += 1;
        }

        final Button bF6 = findViewById(R.id.dbdF6);
        if (loadColor(R.id.dbdF6) == Color.RED) {
            bF6.setBackgroundColor(loadColor(R.id.dbdF6));
            quantity += 1;
        }

        /* Displays number of seats previously selected and saved */
        displayQuantity(quantity);
    }

}
