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

public class AvengersB1Activity extends AppCompatActivity {

    private String selectedDate;
    private String selectedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avengers_b1);

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

        final List<String> times = datetime.getTimes(getResources().getString(R.string.avengers_time1),
                getResources().getString(R.string.avengers_time2));

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
                    startActivity(new Intent(view.getContext(), AvengersB2Activity.class));
                }
                if (selectedDate.equals(orderedDates.get(1)) && selectedTime.equals(times.get(0))) {
                    startActivity(new Intent(view.getContext(), AvengersA1Activity.class));
                }
                if (selectedDate.equals(orderedDates.get(1)) && selectedTime.equals(times.get(1))) {
                    startActivity(new Intent(view.getContext(), AvengersA2Activity.class));
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

        final Button bA1 = findViewById(R.id.avcA1);
        if (loadColor(R.id.avcA1) == Color.RED) {
            bA1.setBackgroundColor(loadColor(R.id.avcA1));
            quantity += 1;
        }

        final Button bA2 = findViewById(R.id.avcA2);
        if (loadColor(R.id.avcA2) == Color.RED) {
            bA2.setBackgroundColor(loadColor(R.id.avcA2));
            quantity += 1;
        }

        final Button bA3 = findViewById(R.id.avcA3);
        if (loadColor(R.id.avcA3) == Color.RED) {
            bA3.setBackgroundColor(loadColor(R.id.avcA3));
            quantity += 1;
        }

        final Button bA4 = findViewById(R.id.avcA4);
        if (loadColor(R.id.avcA4) == Color.RED) {
            bA4.setBackgroundColor(loadColor(R.id.avcA4));
            quantity += 1;
        }

        final Button bA5 = findViewById(R.id.avcA5);
        if (loadColor(R.id.avcA5) == Color.RED) {
            bA5.setBackgroundColor(loadColor(R.id.avcA5));
            quantity += 1;
        }

        final Button bA6 = findViewById(R.id.avcA6);
        if (loadColor(R.id.avcA6) == Color.RED) {
            bA6.setBackgroundColor(loadColor(R.id.avcA6));
            quantity += 1;
        }

        final Button bB1 = findViewById(R.id.avcB1);
        if (loadColor(R.id.avcB1) == Color.RED) {
            bB1.setBackgroundColor(loadColor(R.id.avcB1));
            quantity += 1;
        }

        final Button bB2 = findViewById(R.id.avcB2);
        if (loadColor(R.id.avcB2) == Color.RED) {
            bB2.setBackgroundColor(loadColor(R.id.avcB2));
            quantity += 1;
        }

        final Button bB3 = findViewById(R.id.avcB3);
        if (loadColor(R.id.avcB3) == Color.RED) {
            bB3.setBackgroundColor(loadColor(R.id.avcB3));
            quantity += 1;
        }

        final Button bB4 = findViewById(R.id.avcB4);
        if (loadColor(R.id.avcB4) == Color.RED) {
            bB4.setBackgroundColor(loadColor(R.id.avcB4));
            quantity += 1;
        }

        final Button bB5 = findViewById(R.id.avcB5);
        if (loadColor(R.id.avcB5) == Color.RED) {
            bB5.setBackgroundColor(loadColor(R.id.avcB5));
            quantity += 1;
        }

        final Button bB6 = findViewById(R.id.avcB6);
        if (loadColor(R.id.avcB6) == Color.RED) {
            bB6.setBackgroundColor(loadColor(R.id.avcB6));
            quantity += 1;
        }

        final Button bC1 = findViewById(R.id.avcC1);
        if (loadColor(R.id.avcC1) == Color.RED) {
            bC1.setBackgroundColor(loadColor(R.id.avcC1));
            quantity += 1;
        }

        final Button bC2 = findViewById(R.id.avcC2);
        if (loadColor(R.id.avcC2) == Color.RED) {
            bC2.setBackgroundColor(loadColor(R.id.avcC2));
            quantity += 1;
        }

        final Button bC3 = findViewById(R.id.avcC3);
        if (loadColor(R.id.avcC3) == Color.RED) {
            bC3.setBackgroundColor(loadColor(R.id.avcC3));
            quantity += 1;
        }

        final Button bC4 = findViewById(R.id.avcC4);
        if (loadColor(R.id.avcC4) == Color.RED) {
            bC4.setBackgroundColor(loadColor(R.id.avcC4));
            quantity += 1;
        }

        final Button bC5 = findViewById(R.id.avcC5);
        if (loadColor(R.id.avcC5) == Color.RED) {
            bC5.setBackgroundColor(loadColor(R.id.avcC5));
            quantity += 1;
        }

        final Button bC6 = findViewById(R.id.avcC6);
        if (loadColor(R.id.avcC6) == Color.RED) {
            bC6.setBackgroundColor(loadColor(R.id.avcC6));
            quantity += 1;
        }

        final Button bD1 = findViewById(R.id.avcD1);
        if (loadColor(R.id.avcD1) == Color.RED) {
            bD1.setBackgroundColor(loadColor(R.id.avcD1));
            quantity += 1;
        }

        final Button bD2 = findViewById(R.id.avcD2);
        if (loadColor(R.id.avcD2) == Color.RED) {
            bD2.setBackgroundColor(loadColor(R.id.avcD2));
            quantity += 1;
        }

        final Button bD3 = findViewById(R.id.avcD3);
        if (loadColor(R.id.avcD3) == Color.RED) {
            bD3.setBackgroundColor(loadColor(R.id.avcD3));
            quantity += 1;
        }

        final Button bD4 = findViewById(R.id.avcD4);
        if (loadColor(R.id.avcD4) == Color.RED) {
            bD4.setBackgroundColor(loadColor(R.id.avcD4));
            quantity += 1;
        }

        final Button bD5 = findViewById(R.id.avcD5);
        if (loadColor(R.id.avcD5) == Color.RED) {
            bD5.setBackgroundColor(loadColor(R.id.avcD5));
            quantity += 1;
        }

        final Button bD6 = findViewById(R.id.avcD6);
        if (loadColor(R.id.avcD6) == Color.RED) {
            bD6.setBackgroundColor(loadColor(R.id.avcD6));
            quantity += 1;
        }

        final Button bE1 = findViewById(R.id.avcE1);
        if (loadColor(R.id.avcE1) == Color.RED) {
            bE1.setBackgroundColor(loadColor(R.id.avcE1));
            quantity += 1;
        }

        final Button bE2 = findViewById(R.id.avcE2);
        if (loadColor(R.id.avcE2) == Color.RED) {
            bE2.setBackgroundColor(loadColor(R.id.avcE2));
            quantity += 1;
        }

        final Button bE3 = findViewById(R.id.avcE3);
        if (loadColor(R.id.avcE3) == Color.RED) {
            bE3.setBackgroundColor(loadColor(R.id.avcE3));
            quantity += 1;
        }

        final Button bE4 = findViewById(R.id.avcE4);
        if (loadColor(R.id.avcE4) == Color.RED) {
            bE4.setBackgroundColor(loadColor(R.id.avcE4));
            quantity += 1;
        }

        final Button bE5 = findViewById(R.id.avcE5);
        if (loadColor(R.id.avcE5) == Color.RED) {
            bE5.setBackgroundColor(loadColor(R.id.avcE5));
            quantity += 1;
        }

        final Button bE6 = findViewById(R.id.avcE6);
        if (loadColor(R.id.avcE6) == Color.RED) {
            bE6.setBackgroundColor(loadColor(R.id.avcE6));
            quantity += 1;
        }

        final Button bF1 = findViewById(R.id.avcF1);
        if (loadColor(R.id.avcF1) == Color.RED) {
            bF1.setBackgroundColor(loadColor(R.id.avcF1));
            quantity += 1;
        }

        final Button bF2 = findViewById(R.id.avcF2);
        if (loadColor(R.id.avcF2) == Color.RED) {
            bF2.setBackgroundColor(loadColor(R.id.avcF2));
            quantity += 1;
        }

        final Button bF3 = findViewById(R.id.avcF3);
        if (loadColor(R.id.avcF3) == Color.RED) {
            bF3.setBackgroundColor(loadColor(R.id.avcF3));
            quantity += 1;
        }

        final Button bF4 = findViewById(R.id.avcF4);
        if (loadColor(R.id.avcF4) == Color.RED) {
            bF4.setBackgroundColor(loadColor(R.id.avcF4));
            quantity += 1;
        }

        final Button bF5 = findViewById(R.id.avcF5);
        if (loadColor(R.id.avcF5) == Color.RED) {
            bF5.setBackgroundColor(loadColor(R.id.avcF5));
            quantity += 1;
        }

        final Button bF6 = findViewById(R.id.avcF6);
        if (loadColor(R.id.avcF6) == Color.RED) {
            bF6.setBackgroundColor(loadColor(R.id.avcF6));
            quantity += 1;
        }

        /* Displays number of seats previously selected and saved */
        displayQuantity(quantity);
    }

}
