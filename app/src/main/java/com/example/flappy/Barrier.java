package com.example.flappy;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Barrier {
    private Bitmap barrier;
    private int x, y;
    private int screenW, screenH;
    private int type;
    private boolean isOver = false;
    private boolean isTake = false;
    private static int speed;

    // Constructor: Initializes a new Barrier object with specified attributes.
    public Barrier(Bitmap barrier, int x, int y, int screenW, int screenH, int type) {
        this.barrier = barrier;
        this.x = x;
        this.y = y;
        this.screenW = screenW;
        this.screenH = screenH;
        this.type = type;
        speed = GameProperty.barrierSpeed;
    }

    // Draws the barrier on the canvas using the specified paint.
    public void draw(Canvas canvas, Paint paint) {
        canvas.drawBitmap(barrier,x,y,paint);
    }

    // Updates the barrier's position and checks if it has been passed by the bird.
    public void logic() {
        x-=speed;
        if(x+barrier.getWidth()<=screenW/6 && type == GameProperty.BARRIER_NORMAL && isTake == false){
            isOver=true;
        }
    }

    // Increases the speed at which barriers move, making the game harder.
    public static void increaseSpeed(){
        GameProperty.barrierSpeed += 2;
        speed = GameProperty.barrierSpeed;
    }

    // Checks if the barrier has collided with the bird.
    public boolean isCollide(Bird bird){
        if(bird.getXCoordinate() + bird.getElementAtBitmap(bird.getState()).getWidth()<x){//bird did not collide on the right
            return false;
        }
        else if(bird.getYCoordinate()>y+barrier.getHeight()){//bird did not collide on the top
            return false;
        }
        else if(bird.getYCoordinate()+bird.getElementAtBitmap(bird.getState()).getHeight()<y) {//bird did not collide on the bottom
            return false;
        }
        else if(bird.getXCoordinate()>barrier.getWidth()+x){//bird did not collide on the left
            return false;
        }
        return true;
    }

    // Resets the speed of barriers to the initial speed.
    public static void resetSpeed(){
        GameProperty.barrierSpeed = 15;
    }

    // Getter for the barrier type.
    public int getType(){
        return type;
    }

    // Setter for the isOver flag indicating whether the bird has passed the barrier.
    public void setIsOver(boolean tf){
        isOver =  tf;
    }

    // Getter for the isOver flag.
    public boolean getIsOver(){
        return isOver;
    }

    // Setter for the isTake flag.
    public void setIsTake(boolean tf) {
        isTake = tf;
    }

    // Getter for the x-coordinate of the barrier.
    public int getX(){ return x; }
}
