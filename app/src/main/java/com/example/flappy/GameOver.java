package com.example.flappy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class GameOver extends AppCompatActivity {
    TextView points;
    TextView highestPoints;
    SharedPreferences sharedPreferences;

    // Called when the activity is first created.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Setting the layout for the Game Over screen.
        setContentView(R.layout.game_over);

        // Initializing TextViews for points and highest points.
        points = findViewById(R.id.points);
        highestPoints = findViewById(R.id.highest_points);

        // Retrieving the score from the previous activity.
        int score = getIntent().getExtras().getInt("Score");
        points.setText("" + score);

        // Initializing SharedPreferences for storing/retrieving game data.
        sharedPreferences = getSharedPreferences("my_pref",0);

        // Retrieving the highest score from SharedPreferences.
        int highest_points = sharedPreferences.getInt("highest", 0);

        // Displaying the highest score on the screen.
        highestPoints.setText(""+highest_points);
    }

    // Method to restart the game. It's called when a specific view (button) is clicked.
    public void restart(View view){
        // Creating an Intent to start the PlayMenu activity.
        Intent intent = new Intent(GameOver.this, PlayMenu.class);
        // Starting the PlayMenu activity.
        startActivity(intent);
        // Finishing the current activity (GameOver).
        finish();
    }

    // Method to exit the game over screen. It's called when a back view (button) is clicked.
    public void back(View view){
        // Finishing the current activity (GameOver), returning to the previous screen.
        finish();
    }

}
