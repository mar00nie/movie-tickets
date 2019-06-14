package com.example.movietickets;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Opens a pop up with descriptions of this movie */
        Button btn_movie1 = findViewById(R.id.btn_movie1);
        btn_movie1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Pop1Activity.class);
                startActivity(intent);
            }
        });

        /* Opens a pop up with descriptions of this movie */
        Button btn_movie2 = findViewById(R.id.btn_movie2);
        btn_movie2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Pop2Activity.class);
                startActivity(intent);
            }
        });

        /* Opens a pop up with descriptions of this movie */
        Button btn_movie3 = findViewById(R.id.btn_movie3);
        btn_movie3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Pop3Activity.class);
                startActivity(intent);
            }
        });

        /* Opens a pop up with descriptions of this movie */
        Button btn_movie4 = findViewById(R.id.btn_movie4);
        btn_movie4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Pop4Activity.class);
                startActivity(intent);
            }
        });

        /* Redirects to a page showing a list of prices */
        TextView btn_price = findViewById(R.id.btn_price);
        btn_price.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PriceActivity.class);
                startActivity(intent);
            }
        });

        /* Redirects to a page to submit a contact form */
        TextView btn_contact = findViewById(R.id.btn_contact);
        btn_contact.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ContactActivity.class);
                startActivity(intent);
            }
        });


    }

}
