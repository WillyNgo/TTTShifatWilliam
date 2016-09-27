package com.williamngo.tttshifatwilliam;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public boolean againstDroid = true;
    public boolean playersTurn = true;
    public ImageButton[] imgBtnArray = new ImageButton[9];
    public int[] dataArray = new int[9];


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
        playersTurn = true;
        //Resets all images back to the cover
        setUpImageButtons();

        //Resets all values of array to 0
        for (int i : dataArray)
            i = 0;
    }

    /**
     * Once player has played his/her/its turn, set appropriate value into the dataArray,
     * Switch turn and disables the clicking of the image button that was clicked
     *
     * @param view
     */
    public void playedTurn(View view)
    {
        ImageButton ib = (ImageButton)findViewById(view.getId());
        int index = (Integer) ib.getTag();

        //Check the players turn before associating correct image
        //Insert value into dataArray according to the player
        if(playersTurn) {
            dataArray[index] = 1;
            ib.setImageResource(R.drawable.cross);
        }
        else {
            dataArray[index] = 0;
            ib.setImageResource(R.drawable.tlzino);
        }

        //give turn to other player
        playersTurn = !playersTurn;

        //Disable image button
        view.setOnClickListener(null);
    }

    /**
     * This methods reset the images of all ImageButton to their default
     * cover images and sets all the onClickListener
     */
    public void setUpImageButtons()
    {
        for (ImageButton imgBtn : imgBtnArray){
            imgBtn.setImageResource(R.drawable.quest2);
            imgBtn.setOnClickListener(myListener);
        }

    }

    public void switchMode(View view)
    {
        againstDroid = !againstDroid;
        TextView t = (TextView)findViewById(R.id.playmode_text);

        if(againstDroid)
            t.setText(R.string.vsDroid);
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
        }
    };

}
