package com.williamngo.tttshifatwilliam;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

public class score extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.score);
        showScore(getApplicationContext());
    }

    /**
     *  Gets shared preferences value and displays them
     *
     */
    public void showScore(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);

        //Get all values from every player
        int p1wins = prefs.getInt("p1wins", 0);
        int cpuwins = prefs.getInt("cpuwins", 0);
        int p2wins = prefs.getInt("p2wins", 0);

        int cpulosses = prefs.getInt("cpulosses", 0);
        int p2losses = prefs.getInt("p2losses", 0);
        int p1losses = prefs.getInt("p1losses", 0);

        int cputies = prefs.getInt("cputies", 0);
        int p2ties = prefs.getInt("p2ties", 0);
        int p1ties = prefs.getInt("p1ties", 0);

        //Get textview of all playres
        TextView p1wins_textview = (TextView) findViewById(R.id.p1_wins);
        p1wins_textview.setText(p1wins+"");
        TextView p2wins_textview = (TextView) findViewById(R.id.p2_wins);
        p2wins_textview.setText(p2wins+"");
        TextView cpuwins_textview = (TextView) findViewById(R.id.cpu_wins);
        cpuwins_textview.setText(cpuwins+"");

        //Get losses textview and update them
        TextView cpulosses_textview = (TextView) findViewById(R.id.cpu_losses);
        cpulosses_textview.setText(cpulosses+"");
        TextView p2losses_textview = (TextView) findViewById(R.id.p2_losses);
        p2losses_textview.setText(p2losses+"");
        TextView p1losses_textview = (TextView) findViewById(R.id.p1_losses);
        p1losses_textview.setText(p1losses+"");

        //Get ties textview and update them
        TextView cputies_textview = (TextView) findViewById(R.id.cpu_ties);
        cputies_textview.setText(cputies+"");
        TextView p2ties_textview = (TextView) findViewById(R.id.p2_ties);
        p2ties_textview.setText(p2ties+"");
        TextView p1ties_textview = (TextView) findViewById(R.id.p1_ties);
        p1ties_textview.setText(p1ties+"");
    }
}
