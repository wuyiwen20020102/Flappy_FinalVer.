package com.example.flappy;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import java.util.Random;
import java.util.Vector;

public class GameView_Voice extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    private AudioRecorder audioRecorder;
    private Context context;
    private SurfaceHolder holder;
    private Resources resources;
    private Thread th;
    private boolean flag;
    private Canvas canvas;
    private Paint paint;
    private Bitmap bg;
    private Rect bgRect;
    private Bird bird;
    private Vector<Barrier> barriers;
    private static int GameState = GameProperty.GAME_ING;
    private int score;
    private int lastSpeedIncreaseScore = 0;
    private int volumePitch;
    private int characterUp, characterDown, characterNormal;
    private int[] background = {GameProperty.GAME_BG1,GameProperty.GAME_BG2,GameProperty.GAME_BG3,
            GameProperty.GAME_BG4,GameProperty.GAME_BG5,GameProperty.GAME_BG6,
            GameProperty.GAME_BG7,GameProperty.GAME_BG8};

    // Constructor: Initializes a new GameView_Touch object with specified attributes.
    public GameView_Voice(Context context, int characterUp, int characterDown, int characterNormal) {
        super(context);
        this.context = context;
        this.characterUp = characterUp;
        this.characterDown = characterDown;
        this.characterNormal = characterNormal;
        holder = getHolder();
        resources = getResources();
        holder.addCallback(this);
        setFocusable(true);
        volumePitch = 0;
        audioRecorder = new AudioRecorder();
        audioRecorder.getNoiseLevel();
    }


    // Called when the surface is created.
    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        initGame(); //initialize game
    }

    // Initializes game elements and starts the game thread.
    public void initGame() {
        Random random = new Random();
        int randomBg = random.nextInt(8) + 1;
        bg = BitmapFactory.decodeResource(resources, background[randomBg]); //initialize the background
        th = new Thread(this); //initialize thread
        bgRect = new Rect(0, 0, getWidth(), getHeight());
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(100);
        initBird();//initialize the bird
        initBarrier();
        score = 0;
        flag = true;
        th.start();
    }

    // Initializes the bird with bitmaps for different states.
    private void initBird() {
        Bitmap cDown = BitmapFactory.decodeResource(resources, characterDown);
        Bitmap cNormal = BitmapFactory.decodeResource(resources, characterNormal);
        Bitmap cUp = BitmapFactory.decodeResource(resources, characterUp);
        Bitmap[] bitmaps = new Bitmap[]{cDown, cNormal, cUp};
        bird = new Bird(bitmaps, getWidth(), getHeight());
    }

    // Initializes barriers at the start of the game.
    private void initBarrier() {
        barriers = new Vector<>();
        for (int i = 0; i < 4; i++) {
            Random random = new Random();
            Bitmap barrier01 = BitmapFactory.decodeResource(resources, GameProperty.BARRIER_UP);
            Bitmap barrier02 = BitmapFactory.decodeResource(resources, GameProperty.BARRIER_DOWN);
            int ratio = random.nextInt(4) + 2;
            int k = random.nextInt(getHeight() / 8) + getHeight() / ratio;
            int gap = random.nextInt(300) - 150;
            Barrier barrierUp = new Barrier(barrier01, getWidth() / 2 + getWidth() / 2 * i + 1000, k + gap + 500, getWidth(), getWidth(), GameProperty.BARRIER_NORMAL);
            Barrier barrierDown = new Barrier(barrier02, getWidth() / 2 + getWidth() / 2 * i + 1000, k + gap - 500, getWidth(), getWidth(), GameProperty.BARRIER_NORMAL);
            barriers.add(barrierUp);
            barriers.add(barrierDown);
            for (int j = 1; j < 23; j++) {
                Bitmap barrierZ = BitmapFactory.decodeResource(resources, GameProperty.BARRIER);
                Barrier barrierup = new Barrier(barrierZ, getWidth() / 2 + getWidth() / 2 * i + 999, k + gap - 500 - barrierZ.getHeight() * j  , getWidth(), getWidth(), GameProperty.BARRIER_SPECIAL);
                Barrier barrierdown = new Barrier(barrierZ, getWidth()  / 2 + getWidth() / 2 * i + 1000, k + gap + 500 + barrierZ.getHeight() * j, getWidth(), getWidth(), GameProperty.BARRIER_SPECIAL);
                barriers.add(barrierup);
                barriers.add(barrierdown);
            }
        }
    }

    // Adds new barriers to the game as it progresses.
    private void addBarrier(){
        Random random = new Random();
        Bitmap barrier01 = BitmapFactory.decodeResource(resources, GameProperty.BARRIER_UP);
        Bitmap barrier02 = BitmapFactory.decodeResource(resources, GameProperty.BARRIER_DOWN);
        int ratio = random.nextInt(4) + 2;
        int k = random.nextInt(getHeight() / 8) + getHeight() / ratio;
        int gap = random.nextInt(300) - 150;
        int nextBarrierXPosition;
        if (barriers.size() > 0) {
            Barrier lastBarrier = barriers.lastElement();
            nextBarrierXPosition = getWidth() / 4 + lastBarrier.getX();
        } else {
            nextBarrierXPosition = 0; // Position of the first barrier
        }

        Barrier barrierUp = new Barrier(barrier01, getWidth() / 4 + nextBarrierXPosition, k + gap + 500, getWidth(), getWidth(), GameProperty.BARRIER_NORMAL);
        Barrier barrierDown = new Barrier(barrier02, getWidth() / 4 + nextBarrierXPosition, k + gap - 500, getWidth(), getWidth(), GameProperty.BARRIER_NORMAL);
        barriers.add(barrierUp);
        barriers.add(barrierDown);
        for (int j = 1; j < 23; j++) {
            Bitmap barrierZ = BitmapFactory.decodeResource(resources, GameProperty.BARRIER);
            Barrier barrierup = new Barrier(barrierZ, getWidth() / 4 + nextBarrierXPosition - 1, k + gap - 500 - barrierZ.getHeight() * j, getWidth(), getWidth(), GameProperty.BARRIER_SPECIAL);
            Barrier barrierdown = new Barrier(barrierZ, getWidth()  / 4 + nextBarrierXPosition, k + gap + 500 + barrierZ.getHeight() * j, getWidth(), getWidth(), GameProperty.BARRIER_SPECIAL);
            barriers.add(barrierup);
            barriers.add(barrierdown);
        }
    }

    // Called when the surface is changed.
    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    // Called when the surface is destroyed.
    // Implement actions if needed when the surface is destroyed.
    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {

    }

    // The main game loop running in a separate thread.
    // Continuously update and render the game as long as the flag is true.
    @Override
    public void run() {
        while (flag) {
            volumePitch = audioRecorder.getMvolume(); //Update user voice decibel
            Log.d("Volume", "Volume" + volumePitch);

            myDraw();//Draw function
            logic();//Draw logic
            voiceControl(volumePitch);

            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    // Updates the logic of the game including the bird and barriers.
    // Handle game logic like bird movement and barrier position updates.
    public void logic() {
        bird.logic();
        for (int i = 0; i < barriers.size(); i++) { //the barrier will always move to left, so it looks like the bird is moving to right
            Barrier barrier = barriers.elementAt(i);
            barrier.logic();

            if (barrier.getX() < 0) {
                barriers.remove(i);
                i--; // Adjust the loop index after removing an element
            }

            if (barriers.size() <= 138) {
                addBarrier();
            }
        }

        if ((score / 2) % 5 == 0 && (score / 2) != 0 && lastSpeedIncreaseScore != score / 2) {//change the barrier speed for every 10 scores
            Barrier.increaseSpeed();
            lastSpeedIncreaseScore = score / 2;
        }

        logicCollisions();
        isOver();
    }

    // Checks if the game is over based on barrier positions.
    // Update the score and check for game over conditions.
    private void isOver() {
        for (int i = 0; i < barriers.size(); i++) {
            Barrier barrier = barriers.elementAt(i);
            if (barrier.getIsOver() && barrier.getType() == GameProperty.BARRIER_NORMAL) {
                score++;
                barrier.setIsOver(false);
                barrier.setIsTake(true);
            }
        }
    }

    // Handles the drawing of game elements.
    // Draw the background, bird, barriers, and display the score.
    private void myDraw() {
        try {
            canvas = holder.lockCanvas();
            canvas.drawBitmap(bg, null, bgRect, paint);
            switch (GameState) {
                case GameProperty.GAME_ING: //Game continues
                    bird.draw(canvas, paint);
                    for (int i = 0; i < barriers.size(); i++) {
                        Barrier barrier = barriers.elementAt(i);
                        barrier.draw(canvas, paint);
                    }

                    canvas.drawText("Score:" + score / 2, 80, 100, paint);
                    break;
                case GameProperty.GAME_LOSS://Game ends
                    canvas.drawText("Game Over", getWidth() / 2 - 200, getHeight() / 2, paint);
                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            holder.unlockCanvasAndPost(canvas);
        }
    }

    // Method for controlling the bird's movement based on voice input (volume).
    // If the volume is equal to or exceeds 60, make the bird go up.
    public boolean voiceControl(int volume) {
        if (volume >= 60) {
            bird.setState(2);
            bird.toUpVoice(volume);//function of bird going up
        } else if (volume < 60) {
            bird.setState(0);
        } else {
            bird.setState(1);
        }
        return true;
    }

    // Checks for collisions between the bird and barriers.
    // Check if the bird has collided with any barrier.
    public boolean logicCollisions() {
        for (int i = 0; i < barriers.size(); i++) {
            Barrier barrier = barriers.elementAt(i);
            if (barrier.isCollide(bird)) {
                GameState = GameProperty.GAME_LOSS;
                gameOver();
                resetGame();
                return true;
            }
        }
        return false;
    }

    // Handles actions when the game is over.
    // Save the high score and transition to the Game Over screen.
    private void gameOver() {
        SharedPreferences prefs = context.getSharedPreferences("my_pref", Context.MODE_PRIVATE);
        int highestScore = prefs.getInt("highest", 0);
        if (score / 2 > highestScore) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("highest", score / 2);
            editor.apply();
        }

        Intent intent = new Intent(context, GameOver.class);
        intent.putExtra("Score", score / 2); // Pass the score to GameOver activity
        context.startActivity(intent);
        ((Activity) context).finish();
        flag = false; // Stop the game loop
    }

    // Resets the game to start a new.
    // Reset all game elements to their initial state for a new game.
    public void resetGame() {
        th = new Thread();
        Random random = new Random();
        int randomBg = random.nextInt(8) + 1;
        bg = BitmapFactory.decodeResource(resources, background[randomBg]); //initialize the background
        initBird(); // Reinitialize the bird
        barriers.clear(); // Clear existing barriers
        initBarrier(); // Reinitialize barriers
        score = 0; // Reset score
        lastSpeedIncreaseScore = 0; // Reset last speed increase score
        GameState = GameProperty.GAME_ING; // Reset game state
        Barrier.resetSpeed(); // Reset the speed of barriers if you have such functionality
        bird.reset();
        th.start();
    }
}
