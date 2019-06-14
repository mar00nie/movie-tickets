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

public class PikachuB1Activity extends AppCompatActivity {

    private String selectedDate;
    private String selectedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pikachu_b1);

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

        final List<String> times = datetime.getTimes(getResources().getString(R.string.pikachu_time1),
                getResources().getString(R.string.pikachu_time2));

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
                    startActivity(new Intent(view.getContext(), PikachuB2Activity.class));
                }
                if (selectedDate.equals(orderedDates.get(1)) && selectedTime.equals(times.get(0))) {
                    startActivity(new Intent(view.getContext(), PikachuA1Activity.class));
                }
                if (selectedDate.equals(orderedDates.get(1)) && selectedTime.equals(times.get(1))) {
                    startActivity(new Intent(view.getContext(), PikachuA2Activity.class));
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

        final Button bA1 = findViewById(R.id.pkcA1);
        if (loadColor(R.id.pkcA1) == Color.RED) {
            bA1.setBackgroundColor(loadColor(R.id.pkcA1));
            quantity += 1;
        }

        final Button bA2 = findViewById(R.id.pkcA2);
        if (loadColor(R.id.pkcA2) == Color.RED) {
            bA2.setBackgroundColor(loadColor(R.id.pkcA2));
            quantity += 1;
        }

        final Button bA3 = findViewById(R.id.pkcA3);
        if (loadColor(R.id.pkcA3) == Color.RED) {
            bA3.setBackgroundColor(loadColor(R.id.pkcA3));
            quantity += 1;
        }

        final Button bA4 = findViewById(R.id.pkcA4);
        if (loadColor(R.id.pkcA4) == Color.RED) {
            bA4.setBackgroundColor(loadColor(R.id.pkcA4));
            quantity += 1;
        }

        final Button bA5 = findViewById(R.id.pkcA5);
        if (loadColor(R.id.pkcA5) == Color.RED) {
            bA5.setBackgroundColor(loadColor(R.id.pkcA5));
            quantity += 1;
        }

        final Button bA6 = findViewById(R.id.pkcA6);
        if (loadColor(R.id.pkcA6) == Color.RED) {
            bA6.setBackgroundColor(loadColor(R.id.pkcA6));
            quantity += 1;
        }

        final Button bB1 = findViewById(R.id.pkcB1);
        if (loadColor(R.id.pkcB1) == Color.RED) {
            bB1.setBackgroundColor(loadColor(R.id.pkcB1));
            quantity += 1;
        }

        final Button bB2 = findViewById(R.id.pkcB2);
        if (loadColor(R.id.pkcB2) == Color.RED) {
            bB2.setBackgroundColor(loadColor(R.id.pkcB2));
            quantity += 1;
        }

        final Button bB3 = findViewById(R.id.pkcB3);
        if (loadColor(R.id.pkcB3) == Color.RED) {
            bB3.setBackgroundColor(loadColor(R.id.pkcB3));
            quantity += 1;
        }

        final Button bB4 = findViewById(R.id.pkcB4);
        if (loadColor(R.id.pkcB4) == Color.RED) {
            bB4.setBackgroundColor(loadColor(R.id.pkcB4));
            quantity += 1;
        }

        final Button bB5 = findViewById(R.id.pkcB5);
        if (loadColor(R.id.pkcB5) == Color.RED) {
            bB5.setBackgroundColor(loadColor(R.id.pkcB5));
            quantity += 1;
        }

        final Button bB6 = findViewById(R.id.pkcB6);
        if (loadColor(R.id.pkcB6) == Color.RED) {
            bB6.setBackgroundColor(loadColor(R.id.pkcB6));
            quantity += 1;
        }

        final Button bC1 = findViewById(R.id.pkcC1);
        if (loadColor(R.id.pkcC1) == Color.RED) {
            bC1.setBackgroundColor(loadColor(R.id.pkcC1));
            quantity += 1;
        }

        final Button bC2 = findViewById(R.id.pkcC2);
        if (loadColor(R.id.pkcC2) == Color.RED) {
            bC2.setBackgroundColor(loadColor(R.id.pkcC2));
            quantity += 1;
        }

        final Button bC3 = findViewById(R.id.pkcC3);
        if (loadColor(R.id.pkcC3) == Color.RED) {
            bC3.setBackgroundColor(loadColor(R.id.pkcC3));
            quantity += 1;
        }

        final Button bC4 = findViewById(R.id.pkcC4);
        if (loadColor(R.id.pkcC4) == Color.RED) {
            bC4.setBackgroundColor(loadColor(R.id.pkcC4));
            quantity += 1;
        }

        final Button bC5 = findViewById(R.id.pkcC5);
        if (loadColor(R.id.pkcC5) == Color.RED) {
            bC5.setBackgroundColor(loadColor(R.id.pkcC5));
            quantity += 1;
        }

        final Button bC6 = findViewById(R.id.pkcC6);
        if (loadColor(R.id.pkcC6) == Color.RED) {
            bC6.setBackgroundColor(loadColor(R.id.pkcC6));
            quantity += 1;
        }

        final Button bD1 = findViewById(R.id.pkcD1);
        if (loadColor(R.id.pkcD1) == Color.RED) {
            bD1.setBackgroundColor(loadColor(R.id.pkcD1));
            quantity += 1;
        }

        final Button bD2 = findViewById(R.id.pkcD2);
        if (loadColor(R.id.pkcD2) == Color.RED) {
            bD2.setBackgroundColor(loadColor(R.id.pkcD2));
            quantity += 1;
        }

        final Button bD3 = findViewById(R.id.pkcD3);
        if (loadColor(R.id.pkcD3) == Color.RED) {
            bD3.setBackgroundColor(loadColor(R.id.pkcD3));
            quantity += 1;
        }

        final Button bD4 = findViewById(R.id.pkcD4);
        if (loadColor(R.id.pkcD4) == Color.RED) {
            bD4.setBackgroundColor(loadColor(R.id.pkcD4));
            quantity += 1;
        }

        final Button bD5 = findViewById(R.id.pkcD5);
        if (loadColor(R.id.pkcD5) == Color.RED) {
            bD5.setBackgroundColor(loadColor(R.id.pkcD5));
            quantity += 1;
        }

        final Button bD6 = findViewById(R.id.pkcD6);
        if (loadColor(R.id.pkcD6) == Color.RED) {
            bD6.setBackgroundColor(loadColor(R.id.pkcD6));
            quantity += 1;
        }

        final Button bE1 = findViewById(R.id.pkcE1);
        if (loadColor(R.id.pkcE1) == Color.RED) {
            bE1.setBackgroundColor(loadColor(R.id.pkcE1));
            quantity += 1;
        }

        final Button bE2 = findViewById(R.id.pkcE2);
        if (loadColor(R.id.pkcE2) == Color.RED) {
            bE2.setBackgroundColor(loadColor(R.id.pkcE2));
            quantity += 1;
        }

        final Button bE3 = findViewById(R.id.pkcE3);
        if (loadColor(R.id.pkcE3) == Color.RED) {
            bE3.setBackgroundColor(loadColor(R.id.pkcE3));
            quantity += 1;
        }

        final Button bE4 = findViewById(R.id.pkcE4);
        if (loadColor(R.id.pkcE4) == Color.RED) {
            bE4.setBackgroundColor(loadColor(R.id.pkcE4));
            quantity += 1;
        }

        final Button bE5 = findViewById(R.id.pkcE5);
        if (loadColor(R.id.pkcE5) == Color.RED) {
            bE5.setBackgroundColor(loadColor(R.id.pkcE5));
            quantity += 1;
        }

        final Button bE6 = findViewById(R.id.pkcE6);
        if (loadColor(R.id.pkcE6) == Color.RED) {
            bE6.setBackgroundColor(loadColor(R.id.pkcE6));
            quantity += 1;
        }

        final Button bF1 = findViewById(R.id.pkcF1);
        if (loadColor(R.id.pkcF1) == Color.RED) {
            bF1.setBackgroundColor(loadColor(R.id.pkcF1));
            quantity += 1;
        }

        final Button bF2 = findViewById(R.id.pkcF2);
        if (loadColor(R.id.pkcF2) == Color.RED) {
            bF2.setBackgroundColor(loadColor(R.id.pkcF2));
            quantity += 1;
        }

        final Button bF3 = findViewById(R.id.pkcF3);
        if (loadColor(R.id.pkcF3) == Color.RED) {
            bF3.setBackgroundColor(loadColor(R.id.pkcF3));
            quantity += 1;
        }

        final Button bF4 = findViewById(R.id.pkcF4);
        if (loadColor(R.id.pkcF4) == Color.RED) {
            bF4.setBackgroundColor(loadColor(R.id.pkcF4));
            quantity += 1;
        }

        final Button bF5 = findViewById(R.id.pkcF5);
        if (loadColor(R.id.pkcF5) == Color.RED) {
            bF5.setBackgroundColor(loadColor(R.id.pkcF5));
            quantity += 1;
        }

        final Button bF6 = findViewById(R.id.pkcF6);
        if (loadColor(R.id.pkcF6) == Color.RED) {
            bF6.setBackgroundColor(loadColor(R.id.pkcF6));
            quantity += 1;
        }

        /* Displays number of seats previously selected and saved */
        displayQuantity(quantity);
    }

}
