package com.example.movietickets;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import java.util.ArrayList;

public class PriceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price);

        /* Creates an ArrayList of different prices and descriptions */
        ArrayList<Prices> priceList = new ArrayList<>();

        priceList.add(new Prices("Adult (Ages 18+)", "£10.50"));
        priceList.add(new Prices("Child (12 years and under)", "£7.00"));
        priceList.add(new Prices("Teen (Ages 13-17)", "£7.50"));
        priceList.add(new Prices("Student (Valid student ID required)", "£8.00"));
        priceList.add(new Prices("Senior (Ages 60+)", "£8.00"));
        priceList.add(new Prices("Military (Valid military ID required)", "£7.50"));
        priceList.add(new Prices("Family of 4 (1 adult, 3 children or 2 adults, 2 children)", "£28.50"));

        PricesAdapter adapter = new PricesAdapter(this, priceList);

        /* Shows the list of prices and descriptions in ListView on screen */
        ListView lv = findViewById(R.id.list);

        lv.setAdapter(adapter);

    }

}
