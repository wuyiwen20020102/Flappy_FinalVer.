package com.example.flappy;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;

import androidx.appcompat.app.AppCompatActivity;

public class InstructionMenu extends AppCompatActivity implements OnClickListener{
    private Button button;

    // Called when the activity is first created.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.instruct_menu);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        button = (Button) findViewById(R.id.back_ins);

        button.setOnClickListener(this);
    }

    // Called when a click event occurs on any view this activity is listening to.
    // End the activity and return to the previous screen.
    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.back_ins){
            finish();
        }
    }
}
