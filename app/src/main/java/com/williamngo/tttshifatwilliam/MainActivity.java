package com.williamngo.tttshifatwilliam;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

import static com.williamngo.tttshifatwilliam.R.string.p2ties;
import static com.williamngo.tttshifatwilliam.R.string.vsDroid;

public class MainActivity extends AppCompatActivity {
    public boolean againstDroid = true; //Determines whether user is playing against the droid or another human
    public boolean playersTurn = true; //Determines whose turn it currently is
    boolean winner = false; //Determines is there's a winner or not
    public ImageButton[] imgBtnArray = new ImageButton[9]; //Array holding all the imgBtns
    public int[] dataArray = new int[9]; //Array containing values 0 and 1 that represents X and O
    public int turnCounter = 0; //Counter checks for tie game when it reaches 9 and there is no winner



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Set all image button in array
        imgBtnArray[0] = (ImageButton) findViewById(R.id.imageButton0);
        imgBtnArray[1] = (ImageButton) findViewById(R.id.imageButton1);
        imgBtnArray[2] = (ImageButton) findViewById(R.id.imageButton2);
        imgBtnArray[3] = (ImageButton) findViewById(R.id.imageButton3);
        imgBtnArray[4] = (ImageButton) findViewById(R.id.imageButton4);
        imgBtnArray[5] = (ImageButton) findViewById(R.id.imageButton5);
        imgBtnArray[6] = (ImageButton) findViewById(R.id.imageButton6);
        imgBtnArray[7] = (ImageButton) findViewById(R.id.imageButton7);
        imgBtnArray[8] = (ImageButton) findViewById(R.id.imageButton8);

        //Set Tag for all image
        imgBtnArray[0].setTag(0);
        imgBtnArray[1].setTag(1);
        imgBtnArray[2].setTag(2);
        imgBtnArray[3].setTag(3);
        imgBtnArray[4].setTag(4);
        imgBtnArray[5].setTag(5);
        imgBtnArray[6].setTag(6);
        imgBtnArray[7].setTag(7);
        imgBtnArray[8].setTag(8);


        //Set event listener and set up image buttons to their cover images
        setUpImageButtons();

        // Restore any data that were in previous state.
        if(savedInstanceState != null){
            if(savedInstanceState.getIntArray("dataArray") != null) {
                // Restore the dataArray
                this.dataArray = savedInstanceState.getIntArray("dataArray");

                // Restore image buttons that were already in play
                restoreImageButtons();
            }

            // Restore some parameters.
            this.turnCounter = savedInstanceState.getInt("turnCounter");
            this.againstDroid = savedInstanceState.getBoolean("againstDroid");
            this.playersTurn = savedInstanceState.getBoolean("playersTurn");
            this.winner = savedInstanceState.getBoolean("winner");

            // If there's a winner, disable all image buttons.
            if(winner){
                disableAllImageButtons();
            }
        }
    }

    /**
     * Restore the images' state as they were.
     */
    private void restoreImageButtons()
    {
        // Check which buttons where already in play
        // and set the images accordingly.
        for(int i = 0; i < dataArray.length; i++){
            switch(dataArray[i]){
                case 1:
                    imgBtnArray[i].setImageResource(R.drawable.dkrath);
                    imgBtnArray[i].setOnClickListener(null);
                    break;
                case 2:
                    imgBtnArray[i].setImageResource(R.drawable.tlzino);
                    imgBtnArray[i].setOnClickListener(null);
                    break;
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle b)
    {
        super.onSaveInstanceState(b);

        b.putIntArray("dataArray", dataArray);
        b.putInt("turnCounter", turnCounter);
        b.putBoolean("againstDroid", againstDroid);
        b.putBoolean("playersTurn", playersTurn);
        b.putBoolean("winner", winner);
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

    /*******************Gameplay related methods********************************/

    /**
     * Resets board by putting every image back to cover and setting onClickListener
     * as well as resetting the dataArray values backt to 0
     */
    public void resetBoard_onClick(View view) {
        resetBoard();
    }

    public void resetBoard()
    {
        winner = false;
        playersTurn = true;
        //Resets all images back to the cover
        setUpImageButtons();

        //Resets all values of array to 0
        for (int i = 0; i < dataArray.length; i++) {
            dataArray[i] = 0;
        }
        //reset turn counter;
        turnCounter = 0;
    }

    /**
     *  Delete past scores.
     * @param view
     */
    public void resetScores(View view)
    {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = prefs.edit();

        editor.clear();
        editor.commit();
    }

    /**
     * Once player has played his/her/its turn, set appropriate value into the dataArray,
     * Switch turn and disables the clicking of the image button that was clicked.
     * Afterwards, check across the board if there is a winner.
     *
     * @param view
     */
    public void playedTurn(View view)
    {
        ImageButton ib = (ImageButton)findViewById(view.getId());
        int index = (Integer) ib.getTag();

        if(againstDroid){
            playerTurn(view);
            droidTurn();
        }
        else{
            //Check the players turn before associating correct image
            //Insert value into dataArray according to the player
            playerTurn(view);
        }
    }

    /**
     * Method to handle a player's turn
     */
    private void playerTurn(View view)
    {
        ImageButton ib = (ImageButton)findViewById(view.getId());
        int index = (Integer) ib.getTag();

        if(playersTurn)
        {
            dataArray[index] = 1;
            ib.setImageResource(R.drawable.dkrath);
        }
        else
        {
            dataArray[index] = 2;
            ib.setImageResource(R.drawable.tlzino);
        }



        //Increment turn counter
        turnCounter++;
        System.out.println("Turn counter: " + turnCounter);

        //give turn to other player
        playersTurn = !playersTurn;

        //Disable image button
        view.setOnClickListener(null);

        checkWinner();

    }

    /**
     * Algorithm for a bot's decision for choosing a box.
     */
    private void droidTurn()
    {
        // Indicates if the CPU chose a valid box.
        boolean validChoice = false;

        Random rn = new Random();
        int choice = -1;

        // If the board isn't full yet and there's no winner, play a turn
        if(turnCounter != 9 && !winner)
        {
            while(!validChoice)
            {
                // Get a random number between 0 and 8 inclusively.
                choice = rn.nextInt(8 + 1);

                if(dataArray[choice] == 0)
                {
                    validChoice = true;
                    dataArray[choice] = 2;
                }
            }

            turnCounter++;
            playersTurn = !playersTurn;
            imgBtnArray[choice].setImageResource(R.drawable.tlzino);
            imgBtnArray[choice].setOnClickListener(null);
            checkWinner();
        }
    }

    /**
     * This methods checks for a winner after every turn has been played.
     * It checks every row and column as well as both diagonal for a series of 3 consecutive symbols
     * Once it finds a match, it ends the game and increments the scores
     * If after 9 turns and there is still no winner, the game ends in a tie
     */
    public void checkWinner()
    {
        int player;

        if(!playersTurn)
            player = 1;
        else
            player = 2;

        //Checks for each row, cols, and diagonals if there are 3 symbols in a row for each player

            //Checking for each row
            if(dataArray[0] == player && dataArray[1] == player && dataArray[2] == player)
                winner = true;
            if(dataArray[3] == player && dataArray[4] == player && dataArray[5] == player)
                winner = true;
            if(dataArray[6] == player && dataArray[7] == player && dataArray[8] == player)
                winner = true;

            //Checking for each column
            if(dataArray[0] == player && dataArray[3] == player && dataArray[6] == player)
                winner = true;
            if(dataArray[1] == player && dataArray[4] == player && dataArray[7] == player)
                winner = true;
            if(dataArray[2] == player && dataArray[5] == player && dataArray[8] == player)
                winner = true;

            //Checking for both diagonals
            if(dataArray[0] == player && dataArray[4] == player && dataArray[8] == player)
                winner = true;
            if(dataArray[2] == player && dataArray[4] == player && dataArray[6] == player)
                winner = true;

            //If a winner has been found, end game and exit loop
            if(winner) {
                endGame(player);

                // Display message for the winner
                switch (player){
                    case 1:
                        Toast.makeText(this, R.string.p1winMsg, Toast.LENGTH_LONG).show();
                        break;
                    case 2:
                        if(againstDroid)
                            Toast.makeText(this, R.string.cpuwinMsg, Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(this, R.string.p2winMsg, Toast.LENGTH_LONG).show();
                        break;
                    default:
                        System.out.println("Error: Invalid winner");
                }


            }

        //If played all pieces and there is no winner, end game in a tie
        if(turnCounter == 9 && winner == false) {
            endGame(9);
            Toast.makeText(this, R.string.tieMsg, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * When the game ends, this method checks which player has won. If the value is 9 then that means
     * there is no winner. Increments appropriate values for winner and decrement for loser.
     *
     * @param winner - The player who has won the game. If the value is 9, then it's a tie game
     */
    public void endGame(int winner) {
        //Game has ended, so disable all remaining buttons
        disableAllImageButtons();

        updateScore(winner, getApplicationContext());
    }

    /**
     * Disables all the image buttons so it cannot be clicked anymore.
     */
    private void disableAllImageButtons() {
        //Disables all buttons
        for (ImageButton imgBtn : imgBtnArray)
            imgBtn.setOnClickListener(null);
    }

    /**
     *
     * @param winner - Represents winner of the game. 1 is user, 2 is opponent, 9 is tie game
     */
    public void updateScore(int winner, Context context)
    {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        //Increment wins
        if(winner == 1) // If user wins
        {
            //Shared Prefs
            int p1wins = prefs.getInt("p1wins", 0);
            p1wins++;
            editor.putInt("p1wins", p1wins);

            if(againstDroid)
            {
                int cpulosses = prefs.getInt("cpulosses", 0);
                cpulosses++;

                //Commit player 2's lossess
                editor.putInt("cpulosses", cpulosses);
            }
            else{
                int p2losses = prefs.getInt("p2losses", 0);
                p2losses++;

                //Commit player 2's lossess
                editor.putInt("p2losses", p2losses);
            }
            editor.commit();
        }
        if(winner == 2) //If opponent wins
        {
            if(againstDroid)// If user played against droid
            {
                //Shared Prefs
                //SharedPreferences prefs = getPreferences(MODE_PRIVATE);
                int cpuwins = prefs.getInt("cpuwins", 0);
                cpuwins++;

                //Commit cpu wins
                editor.putInt("cpuwins", cpuwins);
            }
            else // means user played against a human
            {
                //Get textview of player 2's wins and increment
                int p2wins = prefs.getInt("p2wins", 0);
                p2wins++;

                //Commit for player 2
                editor.putInt("p2wins", p2wins);
            }

            //Increment losses of player 1
            int p1losses = prefs.getInt("p1losses", 0);
            p1losses++;

            //Commit player1's losses
            editor.putInt("p1losses", p1losses);
            editor.commit();
        }

        if(winner == 9)//Tie game;
        {
            if(againstDroid)
            {
                int cputies = prefs.getInt("cputies", 0);
                cputies++;
                editor.putInt("cputies", cputies);
            }
            else
            {
                int p2ties = prefs.getInt("p2ties", 0);
                p2ties++;
                System.out.println("p2 ties: " + p2ties);
                editor.putInt("p2ties", p2ties);
            }
            int p1ties = prefs.getInt("p1ties", 0);
            p1ties++;

            //Testing
            System.out.println("p1 ties: " + p1ties);

            editor.putInt("p1ties", p1ties);
            editor.commit();
        }
    }

    /**
     * This methods reset the images of all ImageButton to their default
     * cover images and sets all the onClickListener
     */
    public void setUpImageButtons()
    {
        //Sets every imgBtns src to the cover picture
        //and adds onClick listener to enable user to click
        for (ImageButton imgBtn : imgBtnArray){
            imgBtn.setImageResource(R.drawable.quest2);
            imgBtn.setOnClickListener(myListener);
        }
    }

    /**
     * Switches between vs the CPU and vs a Human
     * Resets the board while doing so.
     * @param view
     */
    public void switchMode(View view)
    {
        againstDroid = !againstDroid;
        TextView t = (TextView)findViewById(R.id.playmode_text);

        if(againstDroid)
            t.setText(vsDroid);
        else
            t.setText(R.string.vsHuman);

        resetBoard();
    }



    /**
     * This listener acts when a player has played its turn
     */
    public View.OnClickListener myListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            playedTurn(v);
            //Testing
            System.out.println("Has onclick Listener!");
        }
    };

}
