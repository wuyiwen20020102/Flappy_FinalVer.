package com.example.flappy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;

import static android.Manifest.permission.RECORD_AUDIO;

public class StartingMenu extends AppCompatActivity implements OnClickListener{
    private Button playButton;
    private Button optionButton;
    private Button quitButton;

    // Called when the activity is first created.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.starting_menu);// Set the content view to the starting menu layout.
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);// Keep the screen on while this activity is running.
        if (ContextCompat.checkSelfPermission(this, RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) { // Request audio recording permissions if not already granted.
            ActivityCompat.requestPermissions(this, new String[]{RECORD_AUDIO}, 1);
        }

        // Initialize the buttons and assign them to the views with specified IDs.
        playButton = (Button) findViewById(R.id.play);
        optionButton = (Button) findViewById(R.id.option);
        quitButton = (Button) findViewById(R.id.quit);

        // Set the current instance as the click listener for all the buttons.
        playButton.setOnClickListener(this);
        optionButton.setOnClickListener(this);
        quitButton.setOnClickListener(this);
    }

    // Called when a click event occurs on any view this activity is listening to.
    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.play) { // Open the play menu.
            openPlay();
        }
        else if(v.getId() == R.id.option){ // Open the options/instructions menu.
            openOption();
        }
        else if(v.getId() == R.id.quit){ // Quit the application.
            quit();
        }
    }

    // Opens the PlayMenu activity.
    public void openPlay(){
        Intent playActivity = new Intent(StartingMenu.this, PlayMenu.class);
        startActivity(playActivity);
    }

    // Opens the InstructionMenu (options) activity.
    public void openOption(){
        Intent optionActivty = new Intent(StartingMenu.this, InstructionMenu.class);
        startActivity(optionActivty);
    }

    // Quits the current activity, effectively closing the app.
    public void quit(){
        finish();
    }
}