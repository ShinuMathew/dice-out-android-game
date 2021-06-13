package com.example.androiddemo;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    // Instances of widget classes
    TextView rollResult;
    TextView congratText;
    FloatingActionButton rollButton;
    FloatingActionButton refreshButton;

    // Field to hold the score
    int score;
    Random rand;
    int die1;
    int die2;
    int die3;
    int currentScore;

    ArrayList<Integer> dice;
    ArrayList<ImageView> diceImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Set initial score
        score = 0;
        currentScore = 0;

        // Create greeting
        Toast.makeText(getApplicationContext(), "Welcome to DiceOut", Toast.LENGTH_LONG).show();
        rollResult = findViewById(R.id.rollResult);
        congratText = findViewById((R.id.congratText));
        rollButton = findViewById(R.id.rollButton);
        refreshButton = findViewById(R.id.refreshButton);
        refreshButton.setEnabled(false);
        rollResult.setText("Start!");

        // Init random number
        rand = new Random();
        dice = new ArrayList<>();

        ImageView die1Image = findViewById(R.id.die1Image);
        ImageView die2Image = findViewById(R.id.die2Image);
        ImageView die3Image = findViewById(R.id.die3Image);

        diceImageView = new ArrayList<>();
        diceImageView.add(die1Image);
        diceImageView.add(die2Image);
        diceImageView.add(die3Image);

        rollButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rollDice(view);
            }
        });

        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refreshDice(view);
            }
        });
    }

    public void rollDice(View v) {
        congratText.setText("");
        rollResult.setText("Let's Play! :)");
        refreshButton.setEnabled(true);

        die1 = rand.nextInt(6) + 1;
        die2 = rand.nextInt(6) + 1;
        die3 = rand.nextInt(6) + 1;

        System.out.println("The die's are : " +die1 +", "+ die2 + ", " + die3);

        dice.clear();
        dice.add(die1);
        dice.add(die2);
        dice.add(die3);

        for(int dieOfSet = 0; dieOfSet < 3; dieOfSet++) {
            System.out.println("dice found: "+dice.get(dieOfSet));
            String imageName = "die_"+dice.get(dieOfSet)+".png";
            try {
                InputStream stream = getAssets().open(imageName);
                Drawable d = Drawable.createFromStream(stream, null);
                diceImageView.get(dieOfSet).setImageDrawable(d);

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        getScore(die1, die2, die3);
        String msg = "Score : "+currentScore;
        rollResult.setText(msg);

//        Toast.makeText(getApplicationContext(), randomVal, Toast.LENGTH_LONG).show();
    }

    public void refreshDice(View v) {
        rollButton.setEnabled(true);
        refreshButton.setEnabled(false);
        currentScore = 0;
        this.recreate();
        rollResult.setText("Start");
        congratText.setText("");
    }

    public void getScore(int dice1, int dice2, int dice3) {
        System.out.println(dice1 +", "+ dice2 + ", " + dice3 + " = "+currentScore);
        if(dice1 == dice2 && dice2 == dice3) {
            congratText.setText("Congratulations!! You gained 200 points for triples :) ");
            currentScore += 200;
        }
        else if(dice1 == dice2 || dice1 == dice3 || dice2 == dice3) {
            congratText.setText("Congratulations!! You gained 100 points for doubles :) ");
            currentScore += 100;
        }
    }
}