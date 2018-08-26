package me.levondovlatyan.turtleup;

import android.content.Context;
import android.graphics.Rect;

import java.util.Random;
import java.lang.String;
import android.graphics.Bitmap;

public abstract class Platform {
    // abstract class for all platforms

    public Platform() {
    }

    abstract void update();

    abstract Bitmap getBitmap();
    abstract int getY();
    abstract int getX();

    abstract Rect getDetectCollision();

}
