package com.example.gallacs.guessthenumber;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
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
    CountDownTimer timer;

    Button guessButton, endButton;
    TextView textYourScore, scoreText, textYourTries, triesText, timeText;
    NumberPicker first, second, third;
    int guess1, guess2, guess3;
    int[] randomNumbers = new int[3];

//    public void createDatabase() {
//
//        try{
//            database = this.openOrCreateDatabase("Scores", MODE_PRIVATE, null);
//            database.execSQL("CREATE TABLE IF NOT EXISTS Scores " +
//                    "(name VARCHAR, score INTEGER);");
//            database.execSQL("CREATE TABLE IF NOT EXISTS Tries " +
//                    "( firstG INTEGER, secondG INTEGER, thirdG INTEGER, match INTEGER, goodPlaceToo INTEGER );");
//            File database = getApplicationContext().getDatabasePath("Scores");
//            if (database.exists()) {
//                Toast.makeText(this, "Database Created", Toast.LENGTH_SHORT).show();
//            } else {
//                Toast.makeText(this, "Database Missing", Toast.LENGTH_SHORT).show();
//            }
//        } catch(Exception e){
//            Log.e("CONTACTS ERROR", "Error Creating Database");
//        }
//    }

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

    public void addContact(String name, int scoreDB) {
        // Execute SQL statement to insert new data
        database.execSQL("INSERT INTO Scores (name, score) VALUES ('" + name + "', " + scoreDB + ");");
    }

//    public void addTries(int first, int second, int third, int matches, int goodplacetoo) {
//        // Execute SQL statement to insert new data
//        database.execSQL("INSERT INTO Scores (name, score) VALUES (" + first + ", " + second + ", " + third + ", " + matches + ", " + goodplacetoo + ");");
//    }

//    public void getTries() {
//
//        Cursor cursor = database.rawQuery("SELECT * FROM Tries", null);
//
//        int firstColumn = cursor.getColumnIndex("firstG");
//        int secondColumn = cursor.getColumnIndex("secondG");
//        int thirdColumn = cursor.getColumnIndex("thirdG");
//
//        int matchColumn = cursor.getColumnIndex("match");
//        int goodPlaceTooColumn = cursor.getColumnIndex("goodPlaceToo");
//
//        cursor.moveToFirst();
//        String triesList = "";
//
//        if(cursor != null && (cursor.getCount() > 0)){
//            do{
//                // Get the results and store them in an int
//                int first = cursor.getInt(firstColumn);
//                int second = cursor.getInt(secondColumn);
//                int third = cursor.getInt(thirdColumn);
//                int match = cursor.getInt(matchColumn);
//                int goodPlaceToo = cursor.getInt(goodPlaceTooColumn);
//
//                triesList = triesList + first + second + third + " : " + match + ", " + goodPlaceToo + "\n";
//                // Keep getting results as long as they exist
//            }while(cursor.moveToNext());
//            guessesText.setText(triesList);
//        } else {
//            Toast.makeText(this, "No Results to Show", Toast.LENGTH_SHORT).show();
//            guessesText.setText("");
//        }
//    }

    public void deleteDatabase(View view){
        this.deleteDatabase("Scores");
        Toast.makeText(this, "The database was deleted", Toast.LENGTH_SHORT).show();
    }

//    public void dropTriesTable(){
//        database.execSQL("DROP TABLE IF EXISTS Tries");
//    }

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

    public void timerStart(){
        timeText.setText("60");
        timer : new CountDownTimer(60 * 1000, 1000){
            @Override
            public void onTick(long millisecund){
                timeText.setText("" + millisecund / 1000);
            }
            @Override
            public void onFinish(){
                timeText.setText("Time is up!");
            }
        }.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);

        textYourScore = (TextView) findViewById(R.id.textYourScore);
        scoreText = (TextView) findViewById(R.id.scoreText);
        textYourTries = (TextView) findViewById(R.id.textYourTries);
        triesText = (TextView) findViewById(R.id.triesText);
        timeText = (TextView) findViewById(R.id.timeText);

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
        timerStart();

        //Toast.makeText(this,randomNumbers[0] + " " + randomNumbers[1] + " " + randomNumbers[2], Toast.LENGTH_LONG).show();
    }

    public void onButtonClick(View view){
        String remainingTime = timeText.getText().toString();


        //deleteDatabase(view);
        int[] guessEs = new int[3];
        guessEs[0] = guess1 = first.getValue();
        guessEs[1] = guess2 = second.getValue();
        guessEs[2] = guess3 = third.getValue();
        int match = 0;
        int goodPlaceToo = 0;
        boolean game = true;

        //createDatabase();
        //addTries(guessEs[0], guessEs[1], guessEs[2], match, goodPlaceToo);

        if (isThereDouble(guessEs) != null) {
            Toast.makeText(this, isThereDouble(guessEs), Toast.LENGTH_SHORT).show();
            game = false;
        }
        while(game){
            tries++;
            score--;
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

            if(tries == 10 || remainingTime == "Time is up!"){
                AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
                dlgAlert.setMessage("You can not find the solution." +
                        "\nThe solution: " + randomNumbers[0] + randomNumbers[1] + randomNumbers[2] +
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
                        "\nThe solution: " + randomNumbers[0] + randomNumbers[1] + randomNumbers[2] +
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
            game = false;
        }
        //getTries();     //Get the tries values
    }

//    public void countdown(View view){
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
        //deleteDatabase(view);
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
        //dropTriesTable();
    }
}