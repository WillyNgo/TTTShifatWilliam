package com.williamngo.tttshifatwilliam;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Set OnClick event to all imageButtons
        ImageButton ib1 = (ImageButton) findViewById(R.id.imageButton1);
        ImageButton ib2 = (ImageButton) findViewById(R.id.imageButton2);
        ImageButton ib3 = (ImageButton) findViewById(R.id.imageButton3);
        ImageButton ib4 = (ImageButton) findViewById(R.id.imageButton4);
        ImageButton ib5 = (ImageButton) findViewById(R.id.imageButton5);
        ImageButton ib6 = (ImageButton) findViewById(R.id.imageButton6);
        ImageButton ib7 = (ImageButton) findViewById(R.id.imageButton7);
        ImageButton ib8 = (ImageButton) findViewById(R.id.imageButton8);
        ImageButton ib9 = (ImageButton) findViewById(R.id.imageButton9);

        ib1.setOnClickListener(myListener);
        ib2.setOnClickListener(myListener);
        ib3.setOnClickListener(myListener);
        ib4.setOnClickListener(myListener);
        ib5.setOnClickListener(myListener);
        ib6.setOnClickListener(myListener);
        ib7.setOnClickListener(myListener);
        ib8.setOnClickListener(myListener);
        ib9.setOnClickListener(myListener);

    }

    public void LaunchAbout(View view)
    {
        Intent intent = new Intent(this, about.class);
        startActivity(intent);
    }

    public void LaunchScore(View view)
    {
        Intent intent = new Intent(this, score.class);
        startActivity(intent);
    }

    public View.OnClickListener myListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ImageButton ib = (ImageButton)findViewById(v.getId());
            ib.setImageResource(R.drawable.cross);
        }
    };

}
