package com.example.movietickets;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

public class Pop3Activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop3);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8), (int)(height*.8));

        TextView tv = findViewById(R.id.aladdin_desc);
        final String htmlText = "A thrilling and vibrant live-action adaptation of " +
                "Disney’s animated classic, “Aladdin” is the exciting tale of " +
                "the charming street rat Aladdin, the courageous and self-determined " +
                "Princess Jasmine and the Genie who may be the key to their future. <p>" +
                "Aladdin is a street urchin, living in Agrabah with his pet monkey, " +
                "Abu. His life changes, though, when he is tasked with retrieving a " +
                "magical lamp from the Cave of Wonder. </p><p> <i> Starring: Naomi Scott, " +
                "Mena Massoud, Will Smith </p><p> Directed by: Guy Ritchie </p>" +
                "<p> Running time: 2hrs 8min </i></p>";
        tv.setText(Html.fromHtml(htmlText));

    }

    public void openAladdinPage(View view) {
        Intent intent = new Intent(this, AladdinA1Activity.class);
        startActivity(intent);
    }

}
