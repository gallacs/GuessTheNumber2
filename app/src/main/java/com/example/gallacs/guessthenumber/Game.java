package com.example.gallacs.guessthenumber;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by GallaCs on 2017. 04. 17..
 * This is the main game class
 */

public class Game extends Activity {
    int tries = 0;
    int score = 11;
    int allScore = 0;
    public static String scoreName="";
    public static SQLiteDatabase database;

    Button guessButton, endButton;
    TextView textYourScore, scoreText, textYourTries, triesText;
    NumberPicker first, second, third;
    int guess1, guess2, guess3;
    int[] randomNumbers = new int[3];

    public void createDatabase() {

        try{
            database = this.openOrCreateDatabase("Scores", MODE_PRIVATE, null);
            database.execSQL("CREATE TABLE IF NOT EXISTS Scores " +
                    "(name VARCHAR, score INTEGER);");
            File database = getApplicationContext().getDatabasePath("Scores");
            if (database.exists()) {
                Toast.makeText(this, "Database Created", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Database Missing", Toast.LENGTH_SHORT).show();
            }
        } catch(Exception e){
            Log.e("CONTACTS ERROR", "Error Creating Database");
        }
    }

    public void deleteDatabase(View view){
        // Delete database
        this.deleteDatabase("Scores");

    }

    public int[] randomMaker(){
        int[] randomnums = new int[3];

        ArrayList<Integer> number = new ArrayList<>();
        for (int i = 1; i < 9; ++i) number.add(i);
        Collections.shuffle(number);

        for (int i = 0; i<randomnums.length; i++){
            randomnums[i] = number.get(i);
        }
        return randomnums;
    }

    public String isThereDouble(int[] array){
        String message = null;
        for(int i =0; i<array.length-1; i++){
            if(array[i] == array[i+1]){
                message = "There  is no 2 or more same numbers";
            }
        }
        return message;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);

        textYourScore = (TextView) findViewById(R.id.textYourScore);
        scoreText = (TextView) findViewById(R.id.scoreText);
        textYourTries = (TextView) findViewById(R.id.textYourTries);
        triesText = (TextView) findViewById(R.id.triesText);

        guessButton = (Button) findViewById(R.id.guessButton);
        endButton = (Button) findViewById(R.id.endButton);
        first = (NumberPicker) findViewById(R.id.first);
            first.setMaxValue(9);
            first.setMinValue(1);
        second = (NumberPicker) findViewById(R.id.second);
            second.setMaxValue(9);
            second.setMinValue(1);
        third = (NumberPicker) findViewById(R.id.third);
            third.setMaxValue(9);
            third.setMinValue(1);
        randomNumbers = randomMaker();

        //Toast.makeText(this,randomNumbers[0] + " " + randomNumbers[1] + " " + randomNumbers[2], Toast.LENGTH_LONG).show();
    }

    public void addContact(String name, int scoreDB) {

        // Execute SQL statement to insert new data
        database.execSQL("INSERT INTO Scores (name, score) VALUES ('" + name + "', " + scoreDB + ");");

    }

    public void onButtonClick(View view){

        //deleteDatabase(view);
        int[] guessEs = new int[3];
        guessEs[0] = guess1 = first.getValue();
        guessEs[1] = guess2 = second.getValue();
        guessEs[2] = guess3 = third.getValue();
        int match = 0;
        int goodPlaceToo = 0;
        tries++;
        score--;
        if (isThereDouble(guessEs) != null) {
            Toast.makeText(this, isThereDouble(guessEs), Toast.LENGTH_SHORT).show();
        }
        for (int i = 0; i < randomNumbers.length; i++) {
            for (int j = 0; j < guessEs.length; j++) {
                if (randomNumbers[i] == guessEs[j]) {
                    match++;
                }
            }
            if (randomNumbers[i] == guessEs[i]) {
                goodPlaceToo++;
            }
        }
        //Toast.makeText(this, "Tries: " + tries, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "Found numbers: " + match + " In place: " + goodPlaceToo, Toast.LENGTH_SHORT).show();

        if(tries == 10){
            AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
            dlgAlert.setMessage("You can not find the solution." +
                    "\nTries number: " + tries +
                    "\nYour score for this turn: "  + score +
                    "\nAll Score: " + allScore +
                    "\nPress Ok for a new game!");
            dlgAlert.setTitle("Try again!");
            dlgAlert.setPositiveButton("Ok", null);
            dlgAlert.setCancelable(false);
            dlgAlert.create().show();
            tries = 0;
            score = 11;
        }
        if (match == 3 && goodPlaceToo == 3) {
            allScore += score;

            AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
            dlgAlert.setMessage("You were great! You found every numbers." +
                    "\nTries number: " + tries +
                    "\nYour score for this turn: "  + score +
                    "\nAll Score: " + allScore +
                    "\nPress Ok for a new game!");
            dlgAlert.setTitle("Congratulations!");
            dlgAlert.setPositiveButton("Ok", null);
            dlgAlert.setCancelable(false);
            dlgAlert.create().show();
            tries = 0;
            score = 11;

            randomNumbers = randomMaker();
            //Toast.makeText(this,randomNumbers[0] + " " + randomNumbers[1] + " " + randomNumbers[2], Toast.LENGTH_SHORT).show();
        }
        scoreText.setText(String.valueOf(allScore));
        triesText.setText(String.valueOf(tries));
    }

//    public void countdown(){
//        new CountDownTimer(10000, 1000){
//            public void onTick(long millisec){
//                counterText.setText("Remaining time: " + millisec/1000);
//            }
//            public void onFinish(){
//                counterText.setText("Time is up!");
//                onEndClick(null);
//            }
//        }.start();
//    }

    public void onEndClick(View view){
        createDatabase();
//        countdown();
        if (view == null || view.getId() == R.id.endButton) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Your Name");
            final EditText input = new EditText(this);
            input.setInputType(InputType.TYPE_CLASS_TEXT);
            builder.setView(input);

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    scoreName = input.getText().toString();
                    addContact(scoreName,allScore);

                    Intent i = new Intent(Game.this, Score_table.class);
                    startActivity(i);
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.show();
        }
    }
}