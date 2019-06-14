package com.example.movietickets;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

public class Pop1Activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop1);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8), (int)(height*.8));

        TextView tv = findViewById(R.id.avengers_desc);
        final String htmlText = "Adrift in space with no food or water, Tony Stark sends a message to " +
                "Pepper Potts as his oxygen supply starts to dwindle. <p> Meanwhile, the " +
                "remaining Avengers -- Thor, Black Widow, Captain America and Bruce Banner -- " +
                "must figure out a way to bring back their vanquished allies for an epic showdown " +
                "with Thanos -- the evil demigod who decimated the planet and the universe. </p><p> " +
                "<i> Starring: Chris Evans, Scarlett Johansson, Robert Downey Jr, Chadwick Boseman, " +
                "Mark Ruffalo, Chris Hemsworth, Brie Larson, Tom Holland </p><p>" +
                "Directed by: Anthony Russo, Joe Russo </p><p> Running time: 3hrs 1min </i></p>";
        tv.setText(Html.fromHtml(htmlText));

    }

    public void openAvengersPage(View view) {
        Intent intent = new Intent(this, AvengersA1Activity.class);
        startActivity(intent);
    }

}
