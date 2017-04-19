package com.example.gallacs.guessthenumber;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by GallaCs on 2017. 04. 17..
 * This will show the rules of the game
 */

public class Rules extends Activity {

    Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rules);

        backButton = (Button) findViewById(R.id.backButton);

    }

    public void onBackClick(View view){
        if (view.getId() == R.id.backButton){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }
}
