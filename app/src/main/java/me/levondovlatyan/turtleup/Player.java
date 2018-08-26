package me.levondovlatyan.turtleup;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;


public class Player {
    //Bitmap to get character from image
    private Bitmap bitmap;

    //coordinates
    private int x;
    private int y;
    //motion speed of the character
    private int speed;

    //Gravity Value to add gravity effect on the ship
    private final int GRAVITY = -10;
    private boolean moveL;
    private boolean moveR;

    //Controlling Y coordinate so that ship won't go outside the screen
    private int maxX;
    private int minX;

    private Rect detectCollision;

    //constructor
    public Player(Context context, int screenX, int screenY) {
        x = screenX/2;
        y = screenY/3;
        speed = 20;

        //Getting bitmap from drawable resource
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.crate_rs);

        //calculating maxY
        maxX = screenX - bitmap.getWidth();
        minX = 0;

        moveL = false;
        moveR = false;


        //initializing rect object
        detectCollision =  new Rect(x, y, bitmap.getWidth(), bitmap.getHeight());
    }

    //Method to update coordinate of character
    public void update() {

        //moving the ship down
        y -= GRAVITY;

        if (moveL) {
            x -= speed;
        }
        if (moveR) {
            x += speed;
        }

        //but controlling it also so that it won't go off the screen
        if (x < minX) {
            x = minX;
        }
        if (x > maxX) {
            x = maxX;
        }

        //adding top, left, bottom and right to the rect object
        detectCollision.left = x;
        detectCollision.top = y;
        detectCollision.right = x + bitmap.getWidth();
        detectCollision.bottom = y + bitmap.getHeight();
    }

    //one more getter for getting the rect object
    public Rect getDetectCollision() {
        return detectCollision;
    }

    public void moveleft(boolean move) {
        moveL = move;
    }
    public void moveright(boolean move) {
        moveR = move;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getHeight() {
        return bitmap.getHeight();
    }

    public void setY(int ynew) {
        y = ynew;
    }

    public int getSpeed() {
        return speed;
    }

}

