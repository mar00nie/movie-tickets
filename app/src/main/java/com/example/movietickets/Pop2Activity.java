package com.example.movietickets;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

public class Pop2Activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop2);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8), (int)(height*.8));

        TextView tv = findViewById(R.id.pikachu_desc);
        final String htmlText = "The story begins when ace private eye Harry Goodman goes " +
                "mysteriously missing, prompting his 21-year-old son Tim to find out " +
                "what happened. Aiding in the investigation is Harry's former Pokemon " +
                "partner, Detective Pikachu: a hilariously wise-cracking, adorable " +
                "super-sleuth who is a puzzlement even to himself. <p>Finding that they " +
                "are uniquely equipped to communicate with one another, Tim and Pikachu " +
                "join forces on a thrilling adventure to unravel the tangled mystery. </p>" +
                "<p> <i> Starring: Ryan Reynolds, Justice Smith, Kathryn Newton </p>" +
                "<p> Directed by: Rob Letterman </p><p> Running time: 1hr 44min </i></p>";
        tv.setText(Html.fromHtml(htmlText));

    }

    public void openPikachuPage(View view) {
        Intent intent = new Intent(this, PikachuA1Activity.class);
        startActivity(intent);
    }

}
