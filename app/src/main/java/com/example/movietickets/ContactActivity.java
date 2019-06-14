package com.example.movietickets;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.text.TextUtils;

public class ContactActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        findViewById(R.id.contact_main).requestFocus();

    }

    /* Redirects to a page displaying a thank you message for submitting the contact form */
    public void openMessagePage(View view) {

        final EditText nameText = findViewById(R.id.name_field);
        final EditText emailText = findViewById(R.id.email_field);

        String name = nameText.getText().toString();
        String email = emailText.getText().toString();

        /* Name field must not be empty */
        if (TextUtils.isEmpty(name)) {
            nameText.setError("Enter Your Name");
            nameText.requestFocus();
            return;
        }
        /* Email field must not be empty */
        if (TextUtils.isEmpty(email)) {
            emailText.setError("Enter Your Email");
            emailText.requestFocus();
            return;
        }

        Intent intent = new Intent(this, MessageActivity.class);
        startActivity(intent);
    }
}
