package com.example.movietickets;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Button;
import android.graphics.drawable.ColorDrawable;
import android.widget.TextView;
import android.content.SharedPreferences;
import android.widget.Toast;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AladdinA2Activity extends AppCompatActivity {

    private String selectedDate;
    private String selectedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aladdin_a2);

        /* Get dates in a list to be used by first spinner */
        DateTime datetime = new DateTime();

        final List<String> dates = datetime.getDates();

        /* Create first spinner for dates */
        final ArrayAdapter<String> dateAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, dates);
        dateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner dateSpinner = findViewById(R.id.spin_date);
        dateSpinner.setAdapter(dateAdapter);

        /* Get times in a list to be used by second spinner */
        final List<String> times = datetime.getTimes(getResources().getString(R.string.aladdin_time2),
                getResources().getString(R.string.aladdin_time1));

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
                if (selectedDate.equals(dates.get(0)) && selectedTime.equals(times.get(1))) {
                    startActivity(new Intent(view.getContext(), AladdinA1Activity.class));
                }
                if (selectedDate.equals(dates.get(1)) && selectedTime.equals(times.get(1))) {
                    startActivity(new Intent(view.getContext(), AladdinB1Activity.class));
                }
                if (selectedDate.equals(dates.get(1)) && selectedTime.equals(times.get(0))) {
                    startActivity(new Intent(view.getContext(), AladdinB2Activity.class));
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
        /* If no seats selected, redirects to cancel page */
        if (quantity == 0) {
            Intent intent = new Intent(this, CancelActivity.class);
            startActivity(intent);
        }
        /* If at least one seat selected, redirects to thank you page */
        else {
            Intent intent = new Intent(this, ReserveActivity.class);
            startActivity(intent);
        }
    }

    /* Loads the color state of each button (by id) and displays the correct color on screen */
    private void showPreviousState() {

        final Button bA1 = findViewById(R.id.albA1);
        if (loadColor(R.id.albA1) == Color.RED) {
            bA1.setBackgroundColor(loadColor(R.id.albA1));
            quantity += 1;
        }

        final Button bA2 = findViewById(R.id.albA2);
        if (loadColor(R.id.albA2) == Color.RED) {
            bA2.setBackgroundColor(loadColor(R.id.albA2));
            quantity += 1;
        }

        final Button bA3 = findViewById(R.id.albA3);
        if (loadColor(R.id.albA3) == Color.RED) {
            bA3.setBackgroundColor(loadColor(R.id.albA3));
            quantity += 1;
        }

        final Button bA4 = findViewById(R.id.albA4);
        if (loadColor(R.id.albA4) == Color.RED) {
            bA4.setBackgroundColor(loadColor(R.id.albA4));
            quantity += 1;
        }

        final Button bA5 = findViewById(R.id.albA5);
        if (loadColor(R.id.albA5) == Color.RED) {
            bA5.setBackgroundColor(loadColor(R.id.albA5));
            quantity += 1;
        }

        final Button bA6 = findViewById(R.id.albA6);
        if (loadColor(R.id.albA6) == Color.RED) {
            bA6.setBackgroundColor(loadColor(R.id.albA6));
            quantity += 1;
        }

        final Button bB1 = findViewById(R.id.albB1);
        if (loadColor(R.id.albB1) == Color.RED) {
            bB1.setBackgroundColor(loadColor(R.id.albB1));
            quantity += 1;
        }

        final Button bB2 = findViewById(R.id.albB2);
        if (loadColor(R.id.albB2) == Color.RED) {
            bB2.setBackgroundColor(loadColor(R.id.albB2));
            quantity += 1;
        }

        final Button bB3 = findViewById(R.id.albB3);
        if (loadColor(R.id.albB3) == Color.RED) {
            bB3.setBackgroundColor(loadColor(R.id.albB3));
            quantity += 1;
        }

        final Button bB4 = findViewById(R.id.albB4);
        if (loadColor(R.id.albB4) == Color.RED) {
            bB4.setBackgroundColor(loadColor(R.id.albB4));
            quantity += 1;
        }

        final Button bB5 = findViewById(R.id.albB5);
        if (loadColor(R.id.albB5) == Color.RED) {
            bB5.setBackgroundColor(loadColor(R.id.albB5));
            quantity += 1;
        }

        final Button bB6 = findViewById(R.id.albB6);
        if (loadColor(R.id.albB6) == Color.RED) {
            bB6.setBackgroundColor(loadColor(R.id.albB6));
            quantity += 1;
        }

        final Button bC1 = findViewById(R.id.albC1);
        if (loadColor(R.id.albC1) == Color.RED) {
            bC1.setBackgroundColor(loadColor(R.id.albC1));
            quantity += 1;
        }

        final Button bC2 = findViewById(R.id.albC2);
        if (loadColor(R.id.albC2) == Color.RED) {
            bC2.setBackgroundColor(loadColor(R.id.albC2));
            quantity += 1;
        }

        final Button bC3 = findViewById(R.id.albC3);
        if (loadColor(R.id.albC3) == Color.RED) {
            bC3.setBackgroundColor(loadColor(R.id.albC3));
            quantity += 1;
        }

        final Button bC4 = findViewById(R.id.albC4);
        if (loadColor(R.id.albC4) == Color.RED) {
            bC4.setBackgroundColor(loadColor(R.id.albC4));
            quantity += 1;
        }

        final Button bC5 = findViewById(R.id.albC5);
        if (loadColor(R.id.albC5) == Color.RED) {
            bC5.setBackgroundColor(loadColor(R.id.albC5));
            quantity += 1;
        }

        final Button bC6 = findViewById(R.id.albC6);
        if (loadColor(R.id.albC6) == Color.RED) {
            bC6.setBackgroundColor(loadColor(R.id.albC6));
            quantity += 1;
        }

        final Button bD1 = findViewById(R.id.albD1);
        if (loadColor(R.id.albD1) == Color.RED) {
            bD1.setBackgroundColor(loadColor(R.id.albD1));
            quantity += 1;
        }

        final Button bD2 = findViewById(R.id.albD2);
        if (loadColor(R.id.albD2) == Color.RED) {
            bD2.setBackgroundColor(loadColor(R.id.albD2));
            quantity += 1;
        }

        final Button bD3 = findViewById(R.id.albD3);
        if (loadColor(R.id.albD3) == Color.RED) {
            bD3.setBackgroundColor(loadColor(R.id.albD3));
            quantity += 1;
        }

        final Button bD4 = findViewById(R.id.albD4);
        if (loadColor(R.id.albD4) == Color.RED) {
            bD4.setBackgroundColor(loadColor(R.id.albD4));
            quantity += 1;
        }

        final Button bD5 = findViewById(R.id.albD5);
        if (loadColor(R.id.albD5) == Color.RED) {
            bD5.setBackgroundColor(loadColor(R.id.albD5));
            quantity += 1;
        }

        final Button bD6 = findViewById(R.id.albD6);
        if (loadColor(R.id.albD6) == Color.RED) {
            bD6.setBackgroundColor(loadColor(R.id.albD6));
            quantity += 1;
        }

        final Button bE1 = findViewById(R.id.albE1);
        if (loadColor(R.id.albE1) == Color.RED) {
            bE1.setBackgroundColor(loadColor(R.id.albE1));
            quantity += 1;
        }

        final Button bE2 = findViewById(R.id.albE2);
        if (loadColor(R.id.albE2) == Color.RED) {
            bE2.setBackgroundColor(loadColor(R.id.albE2));
            quantity += 1;
        }

        final Button bE3 = findViewById(R.id.albE3);
        if (loadColor(R.id.albE3) == Color.RED) {
            bE3.setBackgroundColor(loadColor(R.id.albE3));
            quantity += 1;
        }

        final Button bE4 = findViewById(R.id.albE4);
        if (loadColor(R.id.albE4) == Color.RED) {
            bE4.setBackgroundColor(loadColor(R.id.albE4));
            quantity += 1;
        }

        final Button bE5 = findViewById(R.id.albE5);
        if (loadColor(R.id.albE5) == Color.RED) {
            bE5.setBackgroundColor(loadColor(R.id.albE5));
            quantity += 1;
        }

        final Button bE6 = findViewById(R.id.albE6);
        if (loadColor(R.id.albE6) == Color.RED) {
            bE6.setBackgroundColor(loadColor(R.id.albE6));
            quantity += 1;
        }

        final Button bF1 = findViewById(R.id.albF1);
        if (loadColor(R.id.albF1) == Color.RED) {
            bF1.setBackgroundColor(loadColor(R.id.albF1));
            quantity += 1;
        }

        final Button bF2 = findViewById(R.id.albF2);
        if (loadColor(R.id.albF2) == Color.RED) {
            bF2.setBackgroundColor(loadColor(R.id.albF2));
            quantity += 1;
        }

        final Button bF3 = findViewById(R.id.albF3);
        if (loadColor(R.id.albF3) == Color.RED) {
            bF3.setBackgroundColor(loadColor(R.id.albF3));
            quantity += 1;
        }

        final Button bF4 = findViewById(R.id.albF4);
        if (loadColor(R.id.albF4) == Color.RED) {
            bF4.setBackgroundColor(loadColor(R.id.albF4));
            quantity += 1;
        }

        final Button bF5 = findViewById(R.id.albF5);
        if (loadColor(R.id.albF5) == Color.RED) {
            bF5.setBackgroundColor(loadColor(R.id.albF5));
            quantity += 1;
        }

        final Button bF6 = findViewById(R.id.albF6);
        if (loadColor(R.id.albF6) == Color.RED) {
            bF6.setBackgroundColor(loadColor(R.id.albF6));
            quantity += 1;
        }

        /* Displays number of seats previously selected and saved */
        displayQuantity(quantity);
    }

}
