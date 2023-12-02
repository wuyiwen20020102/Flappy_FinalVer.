package com.example.flappy;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;


public class PlayMenu extends AppCompatActivity implements OnClickListener{
    private Button touchButton;
    private Button voiceButton;
    private ImageButton hutaoButton;
    private ImageButton paimonButton;
    private ImageButton appleButton;
    private int gameState;
    private int character_up, character_down, character_normal;

    // Called when the activity is first created.
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_menu); // Set the content view to the play menu layout.
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON); // Keep the screen on while this activity is running.

        // Initialize the buttons and assign them to the views with the specified IDs.
        touchButton = (Button) findViewById(R.id.touch);
        voiceButton = (Button) findViewById(R.id.voice);
        hutaoButton = (ImageButton) findViewById(R.id.hutao);
        paimonButton = (ImageButton) findViewById(R.id.paimon);
        appleButton = (ImageButton) findViewById(R.id.apple);

        // Set the current instance as the click listener for all the buttons.
        touchButton.setOnClickListener(this);
        voiceButton.setOnClickListener(this);
        hutaoButton.setOnClickListener(this);
        paimonButton.setOnClickListener(this);
        appleButton.setOnClickListener(this);

        //Default character
        character_up = GameProperty.APPLE_UP;
        character_down = GameProperty.APPLE_DOWN;
        character_normal = GameProperty.APPLE_NORMAL;
    }

    // Called when a click event occurs on any view this activity is listening to.
    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.touch) {
            gameState = GameProperty.GAME_TOUCH;
            openGame(gameState);
        }
        else if(v.getId() == R.id.voice){
            gameState = GameProperty.GAME_VOICE;
            openGame(gameState);
        }
        else if(v.getId() == R.id.hutao){
            character_up = GameProperty.HUTAO_UP;
            character_down = GameProperty.HUTAO_DOWN;
            character_normal = GameProperty.HUTAO_NORMAL;
        }
        else if(v.getId() == R.id.paimon){
            character_up = GameProperty.PAIMON_UP;
            character_down = GameProperty.PAIMON_DOWN;
            character_normal = GameProperty.PAIMON_NORMAL;
        }
        else if(v.getId() == R.id.apple){
            character_up = GameProperty.APPLE_UP;
            character_down = GameProperty.APPLE_DOWN;
            character_normal = GameProperty.APPLE_NORMAL;
        }
    }

    // Opens the game with the selected game mode and character states.
    public void openGame(int gameState){
        if(gameState == GameProperty.GAME_TOUCH) {
            GameView_Touch gameView = new GameView_Touch(this, character_up, character_down, character_normal);
            setContentView(gameView);
        }
        else if(gameState == GameProperty.GAME_VOICE){
            GameView_Voice gameView = new GameView_Voice(this, character_up, character_down, character_normal);
            setContentView(gameView);
        }
    }
}
