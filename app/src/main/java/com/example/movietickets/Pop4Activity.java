package com.example.movietickets;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

public class Pop4Activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop4);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8), (int)(height*.8));

        TextView tv = findViewById(R.id.dumbo_desc);
        final String htmlText = "Disney's new live-action feature film 'Dumbo' introduces Holt Farrier, " +
                "a former circus star who finds his life turned upside down when he returns from " +
                "the war. <p> Circus owner Max Medici enlists Holt to care for a newborn elephant " +
                "whose oversized ears make him a laughingstock in an already struggling circus. " +
                "But when Holt's children discover that Dumbo can fly, persuasive entrepreneur " +
                "V.A. Vandevere (Keaton) and an aerial artist named Colette Marchant swoop in to " +
                "make the peculiar pachyderm a star. </p><p> <i> Starring: Colin Farrell, " +
                "Danny DeVito, Eva Green </p><p> Directed by: Tim Burton </p><p> " +
                "Running time: 1hr 52min </i></p>";
        tv.setText(Html.fromHtml(htmlText));

    }

    public void openDumboPage(View view) {
        Intent intent = new Intent(this, DumboA1Activity.class);
        startActivity(intent);
    }

}
