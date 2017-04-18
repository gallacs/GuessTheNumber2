package com.example.gallacs.guessthenumber;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    Button guessButton;
    NumberPicker first, second, third;
    int guess1, guess2, guess3;
    int[] randomNumbers = new int[3];

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        guessButton = (Button) findViewById(R.id.guessButton);
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

        Toast.makeText(this,randomNumbers[0] + " " + randomNumbers[1] + " " + randomNumbers[2], Toast.LENGTH_LONG).show();
    }

    public void onButtonClick(View view){
        boolean end = false;
        int[] guessEs = new int[3];
            guessEs[0] = guess1 = first.getValue();
            guessEs[1] = guess2 = second.getValue();
            guessEs[2] = guess3 = third.getValue();
        int match = 0;
        int goodPlaceToo = 0;

        while(!end) {

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

            Toast.makeText(this,"Found numbers: " + match + " In place: " + goodPlaceToo, Toast.LENGTH_LONG).show();

            if (match == 3 && goodPlaceToo == 3){
                Toast.makeText(this,"You were great! Start a new game!", Toast.LENGTH_SHORT).show();
                randomNumbers = randomMaker();
                Toast.makeText(this,randomNumbers[0] + " " + randomNumbers[1] + " " + randomNumbers[2], Toast.LENGTH_LONG).show();
            }
            match = 0;
            goodPlaceToo = 0;
            end = true;
        }
    }
}
