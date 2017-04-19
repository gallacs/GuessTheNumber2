package com.example.gallacs.guessthenumber;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

/**
 * Created by GallaCs on 2017. 04. 17..
 * This will represent the database
 */

public class Score_table extends Activity{

    public static SQLiteDatabase database;
    Button backButton;
    EditText scoreListEditText;
    //File database = getApplicationContext().getDatabasePath("Scores");

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

    public void getScores() {

        Cursor cursor = database.rawQuery("SELECT * FROM Scores ORDER BY score DESC", null);

        int nameColumn = cursor.getColumnIndex("name");
        int scoreColumn = cursor.getColumnIndex("score");

        cursor.moveToFirst();
        String scoreList = "";

        if(cursor != null && (cursor.getCount() > 0)){
            do{
                // Get the results and store them in a String
                String name = cursor.getString(nameColumn);
                int score = cursor.getInt(scoreColumn);
                scoreList = scoreList + name + " : " + score + "\n";
                // Keep getting results as long as they exist
            }while(cursor.moveToNext());
            scoreListEditText.setText(scoreList);
        } else {
            Toast.makeText(this, "No Results to Show", Toast.LENGTH_SHORT).show();
            scoreListEditText.setText("");
        }
    }

    public void deleteDatabase(){
        // Delete database
        this.deleteDatabase("Scores");

    }

    public void onBackClick(View view){
        if (view.getId() == R.id.backButton){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.score_table);

        backButton = (Button) findViewById(R.id.backButton);
        scoreListEditText = (EditText) findViewById(R.id.textScore);
        createDatabase();
        getScores();
    }

}
