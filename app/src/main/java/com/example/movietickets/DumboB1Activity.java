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

public class DumboB1Activity extends AppCompatActivity {

    private String selectedDate;
    private String selectedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dumbo_b1);

        /* Get dates in a list to be used by first spinner */
        DateTime datetime = new DateTime();

        /* Get times in a list to be used by second spinner */
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

        final List<String> times = datetime.getTimes(getResources().getString(R.string.dumbo_time1),
                getResources().getString(R.string.dumbo_time2));

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
                    startActivity(new Intent(view.getContext(), DumboB2Activity.class));
                }
                if (selectedDate.equals(orderedDates.get(1)) && selectedTime.equals(times.get(0))) {
                    startActivity(new Intent(view.getContext(), DumboA1Activity.class));
                }
                if (selectedDate.equals(orderedDates.get(1)) && selectedTime.equals(times.get(1))) {
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

        /* Button that was green now changes to red */
        if (colorId == Color.GREEN) {
            btn.setBackgroundColor(Color.RED);
            quantity += 1;
            displayQuantity(quantity);
            colorMap.put(id, Color.RED);
        }
        /* Button that was red now changes to green */
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

        final Button bA1 = findViewById(R.id.dbcA1);
        if (loadColor(R.id.dbcA1) == Color.RED) {
            bA1.setBackgroundColor(loadColor(R.id.dbcA1));
            quantity += 1;
        }

        final Button bA2 = findViewById(R.id.dbcA2);
        if (loadColor(R.id.dbcA2) == Color.RED) {
            bA2.setBackgroundColor(loadColor(R.id.dbcA2));
            quantity += 1;
        }

        final Button bA3 = findViewById(R.id.dbcA3);
        if (loadColor(R.id.dbcA3) == Color.RED) {
            bA3.setBackgroundColor(loadColor(R.id.dbcA3));
            quantity += 1;
        }

        final Button bA4 = findViewById(R.id.dbcA4);
        if (loadColor(R.id.dbcA4) == Color.RED) {
            bA4.setBackgroundColor(loadColor(R.id.dbcA4));
            quantity += 1;
        }

        final Button bA5 = findViewById(R.id.dbcA5);
        if (loadColor(R.id.dbcA5) == Color.RED) {
            bA5.setBackgroundColor(loadColor(R.id.dbcA5));
            quantity += 1;
        }

        final Button bA6 = findViewById(R.id.dbcA6);
        if (loadColor(R.id.dbcA6) == Color.RED) {
            bA6.setBackgroundColor(loadColor(R.id.dbcA6));
            quantity += 1;
        }

        final Button bB1 = findViewById(R.id.dbcB1);
        if (loadColor(R.id.dbcB1) == Color.RED) {
            bB1.setBackgroundColor(loadColor(R.id.dbcB1));
            quantity += 1;
        }

        final Button bB2 = findViewById(R.id.dbcB2);
        if (loadColor(R.id.dbcB2) == Color.RED) {
            bB2.setBackgroundColor(loadColor(R.id.dbcB2));
            quantity += 1;
        }

        final Button bB3 = findViewById(R.id.dbcB3);
        if (loadColor(R.id.dbcB3) == Color.RED) {
            bB3.setBackgroundColor(loadColor(R.id.dbcB3));
            quantity += 1;
        }

        final Button bB4 = findViewById(R.id.dbcB4);
        if (loadColor(R.id.dbcB4) == Color.RED) {
            bB4.setBackgroundColor(loadColor(R.id.dbcB4));
            quantity += 1;
        }

        final Button bB5 = findViewById(R.id.dbcB5);
        if (loadColor(R.id.dbcB5) == Color.RED) {
            bB5.setBackgroundColor(loadColor(R.id.dbcB5));
            quantity += 1;
        }

        final Button bB6 = findViewById(R.id.dbcB6);
        if (loadColor(R.id.dbcB6) == Color.RED) {
            bB6.setBackgroundColor(loadColor(R.id.dbcB6));
            quantity += 1;
        }

        final Button bC1 = findViewById(R.id.dbcC1);
        if (loadColor(R.id.dbcC1) == Color.RED) {
            bC1.setBackgroundColor(loadColor(R.id.dbcC1));
            quantity += 1;
        }

        final Button bC2 = findViewById(R.id.dbcC2);
        if (loadColor(R.id.dbcC2) == Color.RED) {
            bC2.setBackgroundColor(loadColor(R.id.dbcC2));
            quantity += 1;
        }

        final Button bC3 = findViewById(R.id.dbcC3);
        if (loadColor(R.id.dbcC3) == Color.RED) {
            bC3.setBackgroundColor(loadColor(R.id.dbcC3));
            quantity += 1;
        }

        final Button bC4 = findViewById(R.id.dbcC4);
        if (loadColor(R.id.dbcC4) == Color.RED) {
            bC4.setBackgroundColor(loadColor(R.id.dbcC4));
            quantity += 1;
        }

        final Button bC5 = findViewById(R.id.dbcC5);
        if (loadColor(R.id.dbcC5) == Color.RED) {
            bC5.setBackgroundColor(loadColor(R.id.dbcC5));
            quantity += 1;
        }

        final Button bC6 = findViewById(R.id.dbcC6);
        if (loadColor(R.id.dbcC6) == Color.RED) {
            bC6.setBackgroundColor(loadColor(R.id.dbcC6));
            quantity += 1;
        }

        final Button bD1 = findViewById(R.id.dbcD1);
        if (loadColor(R.id.dbcD1) == Color.RED) {
            bD1.setBackgroundColor(loadColor(R.id.dbcD1));
            quantity += 1;
        }

        final Button bD2 = findViewById(R.id.dbcD2);
        if (loadColor(R.id.dbcD2) == Color.RED) {
            bD2.setBackgroundColor(loadColor(R.id.dbcD2));
            quantity += 1;
        }

        final Button bD3 = findViewById(R.id.dbcD3);
        if (loadColor(R.id.dbcD3) == Color.RED) {
            bD3.setBackgroundColor(loadColor(R.id.dbcD3));
            quantity += 1;
        }

        final Button bD4 = findViewById(R.id.dbcD4);
        if (loadColor(R.id.dbcD4) == Color.RED) {
            bD4.setBackgroundColor(loadColor(R.id.dbcD4));
            quantity += 1;
        }

        final Button bD5 = findViewById(R.id.dbcD5);
        if (loadColor(R.id.dbcD5) == Color.RED) {
            bD5.setBackgroundColor(loadColor(R.id.dbcD5));
            quantity += 1;
        }

        final Button bD6 = findViewById(R.id.dbcD6);
        if (loadColor(R.id.dbcD6) == Color.RED) {
            bD6.setBackgroundColor(loadColor(R.id.dbcD6));
            quantity += 1;
        }

        final Button bE1 = findViewById(R.id.dbcE1);
        if (loadColor(R.id.dbcE1) == Color.RED) {
            bE1.setBackgroundColor(loadColor(R.id.dbcE1));
            quantity += 1;
        }

        final Button bE2 = findViewById(R.id.dbcE2);
        if (loadColor(R.id.dbcE2) == Color.RED) {
            bE2.setBackgroundColor(loadColor(R.id.dbcE2));
            quantity += 1;
        }

        final Button bE3 = findViewById(R.id.dbcE3);
        if (loadColor(R.id.dbcE3) == Color.RED) {
            bE3.setBackgroundColor(loadColor(R.id.dbcE3));
            quantity += 1;
        }

        final Button bE4 = findViewById(R.id.dbcE4);
        if (loadColor(R.id.dbcE4) == Color.RED) {
            bE4.setBackgroundColor(loadColor(R.id.dbcE4));
            quantity += 1;
        }

        final Button bE5 = findViewById(R.id.dbcE5);
        if (loadColor(R.id.dbcE5) == Color.RED) {
            bE5.setBackgroundColor(loadColor(R.id.dbcE5));
            quantity += 1;
        }

        final Button bE6 = findViewById(R.id.dbcE6);
        if (loadColor(R.id.dbcE6) == Color.RED) {
            bE6.setBackgroundColor(loadColor(R.id.dbcE6));
            quantity += 1;
        }

        final Button bF1 = findViewById(R.id.dbcF1);
        if (loadColor(R.id.dbcF1) == Color.RED) {
            bF1.setBackgroundColor(loadColor(R.id.dbcF1));
            quantity += 1;
        }

        final Button bF2 = findViewById(R.id.dbcF2);
        if (loadColor(R.id.dbcF2) == Color.RED) {
            bF2.setBackgroundColor(loadColor(R.id.dbcF2));
            quantity += 1;
        }

        final Button bF3 = findViewById(R.id.dbcF3);
        if (loadColor(R.id.dbcF3) == Color.RED) {
            bF3.setBackgroundColor(loadColor(R.id.dbcF3));
            quantity += 1;
        }

        final Button bF4 = findViewById(R.id.dbcF4);
        if (loadColor(R.id.dbcF4) == Color.RED) {
            bF4.setBackgroundColor(loadColor(R.id.dbcF4));
            quantity += 1;
        }

        final Button bF5 = findViewById(R.id.dbcF5);
        if (loadColor(R.id.dbcF5) == Color.RED) {
            bF5.setBackgroundColor(loadColor(R.id.dbcF5));
            quantity += 1;
        }

        final Button bF6 = findViewById(R.id.dbcF6);
        if (loadColor(R.id.dbcF6) == Color.RED) {
            bF6.setBackgroundColor(loadColor(R.id.dbcF6));
            quantity += 1;
        }

        /* Displays number of seats previously selected and saved */
        displayQuantity(quantity);
    }

}
