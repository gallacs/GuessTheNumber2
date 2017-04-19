package com.example.gallacs.guessthenumber;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {

//    SQLiteDatabase database = null;
    Button startButton, rulesButton, exitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton = (Button) findViewById(R.id.startButton);
        rulesButton = (Button) findViewById(R.id.rulesButton);
        exitButton = (Button) findViewById(R.id.exitButton);
    }
//
//    public void createDatabase(View view) {
//
//        try{
//            database = this.openOrCreateDatabase("Scores", MODE_PRIVATE, null);
//            database.execSQL("CREATE TABLE IF NOT EXISTS Scores " +
//                    "(id integer primary key, name VARCHAR, score integer);");
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

    public void onStartClick(View view){
        if (view.getId() == R.id.startButton){
            //createDatabase(view);
            Intent i = new Intent(MainActivity.this, Game.class);
            startActivity(i);
        }
    }

    public void onRulesClick(View view){
        if (view.getId() == R.id.rulesButton){
            Intent i = new Intent(MainActivity.this, Rules.class);
            startActivity(i);
        }

    }

    public void onExitClick(View view){
        if (view.getId() == R.id.exitButton){
            finish();
            System.exit(0);
        }
    }
}