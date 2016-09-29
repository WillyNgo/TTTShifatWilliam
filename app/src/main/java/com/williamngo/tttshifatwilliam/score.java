package com.williamngo.tttshifatwilliam;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

public class score extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.score);

        //Get intent from MainActivity class
        Intent receiveIntent = getIntent();
        int winner = receiveIntent.getIntExtra("player", 9);
        boolean againstDroid = receiveIntent.getBooleanExtra("againstDroid", false);
        updateScore(winner, againstDroid);
    }

    /**
     *
     * @param winner - Represents winner of the game. 1 is user, 2 is opponent, 9 is tie game
     * @param againstDroid - Determines whether the game was played against the droid or not
     */
    public void updateScore(int winner, boolean againstDroid)
    {
        //Increment wins
        if(winner == 1) // If user wins
        {
            //Get textview of player 1's wins and increment
            TextView p1wins_textview = (TextView)findViewById(R.id.p1_wins);
            int p1wins = Integer.parseInt(p1wins_textview.getText().toString());
            p1wins++;
            p1wins_textview.setText(p1wins);

            //Increment losses of player 2
            TextView p2losses_textview = (TextView)findViewById(R.id.p2_losses);
            int p2losses = Integer.parseInt(p2losses_textview.getText().toString());
            p2losses++;
            p2losses_textview.setText(p2losses);
        }

        if(winner == 2) //If opponent wins
        {
            if(againstDroid)// If user played against droid
            {
                TextView cpuwins_textview = (TextView)findViewById(R.id.cpu_wins);
                int cpuwins = Integer.parseInt(cpuwins_textview.getText().toString());
                cpuwins++;
                cpuwins_textview.setText(cpuwins);
            }
            else // means user played against a human
            {
                //Get textview of player 2's wins and increment
                TextView p2wins_textview = (TextView)findViewById(R.id.p2_wins);
                int p2wins = Integer.parseInt(p2wins_textview.getText().toString());
                p2wins++;
                p2wins_textview.setText(p2wins);
            }


            //Increment losses of player 1
            TextView p1losses_textview = (TextView)findViewById(R.id.p1_losses);
            int p1losses = Integer.parseInt(p1losses_textview.getText().toString());
            p1losses++;
            p1losses_textview.setText(p1losses);
        }

        if(winner == 9)//Tie game;
        {
            if(againstDroid)
            {
                TextView cputies_textview = (TextView)findViewById(R.id.cpu_ties);
                int cputies = Integer.parseInt(cputies_textview.getText().toString());
                cputies++;
                cputies_textview.setText(cputies);
            }
            else
            {
                TextView p2ties_textview = (TextView)findViewById(R.id.p2_ties);
                int p2ties = Integer.parseInt(p2ties_textview.getText().toString());
                p2ties++;
                p2ties_textview.setText(p2ties);
            }

            TextView p1ties_textview = (TextView)findViewById(R.id.p1_ties);
            int p1ties = Integer.parseInt(p1ties_textview.getText().toString());
            p1ties++;
            p1ties_textview.setText(p1ties);
        }


        //Show win message

        //End this activity
        finish();
    }
}
