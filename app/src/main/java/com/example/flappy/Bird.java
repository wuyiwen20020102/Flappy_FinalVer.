package com.example.flappy;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Bird {
    private Bitmap[] bitmaps;
    private int ScreenW, ScreenH;
    private boolean isUp = false;//determine up/down of the bird
    private int x, y;
    private int state = 1;

    // Constructor: Initializes a new Bird object with specified attributes.
    public Bird(Bitmap[] bitmaps, int screenW, int screenH) {
        this.bitmaps = bitmaps;
        this.ScreenW = screenW;
        this.ScreenH = screenH;
        x = screenW/6;
        y = screenH/2;
    }

    // Draws the bird on the canvas using the specified paint.
    public void draw(Canvas canvas, Paint paint){canvas.drawBitmap(bitmaps[state], (float)x,y,paint);
    }

    // Updates the bird's position based on game logic.
    public void logic() {
        if(y<=ScreenH-100){
            y+=35;
        }
    }

    // Handles the bird's upward movement when the screen is touched.
    public void toUpTouch() throws InterruptedException {
        if(y>=0){
            int upCount = 80;
            while(upCount > 0){
                y = y - 6;
                upCount--;
                Thread.sleep(1);
            }
        }
    }

    // Handles the bird's upward movement in response to voice input.
    public void toUpVoice(int volume){
        if (volume >= 60) {
            if (y >= 0) {
                y -= 70; // Adjust this value to control the speed of ascent
            }
        }
    }

    // Resets the bird's position and state to default.
    public void reset() {
        x = ScreenW / 6; // Resetting the X position
        y = ScreenH / 2; // Resetting the Y position
        state = 1; // Resetting the state
        isUp = false; // Resetting the isUp flag
        // Reset any other variables if necessary
    }

    // Setter for the bird's state (animation or position state).
    public void setState(int s){
        state = s;
    }

    // Getter for the bird's state.
    public int getState(){
        return state;
    }

    // Getter for the bird's X-coordinate.
    public int getXCoordinate(){
        return x;
    }

    // Getter for the bird's Y-coordinate.
    public float getYCoordinate(){
        return y;
    }

    // Returns the Bitmap object at the specified index from the bird's Bitmap array.
    public Bitmap getElementAtBitmap(int index){
        return bitmaps[index];
    }

}
