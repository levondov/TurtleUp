package me.levondovlatyan.turtleup;

import android.view.SurfaceView;
import android.view.SurfaceHolder;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Color;
import android.content.Context;
import android.view.MotionEvent;
import java.util.ArrayList;
import android.graphics.Rect;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap;

public class GameView extends SurfaceView implements Runnable{

    //boolean variable to track if the game is playing or not
    volatile boolean playing;
    private int X;
    private int Y;

    //the game thread
    private Thread gameThread = null;

    //player
    private Player player;

    //platforms
    private PlatformSelection pselection;
    // starting platform
    private Platform_Basic platform_starting;
    // array of platforms
    private Platform[] platforms;
    private int totalplatforms = 10;
    private int currentplatforms = 0;

    //These objects will be used for drawing
    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;

    //background img
    private Bitmap bitmap;
    private Rect rect;

    //timers
    private long starttime = System.currentTimeMillis();
    private int prevSecond = 0;
    private int platformSpawnTime = 2;

    //Class constructor
    public GameView(Context context, int screenX, int screenY) {
        super(context);

        //initializing drawing objects
        surfaceHolder = getHolder();
        paint = new Paint();

        // background image
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.background1);
        rect = new Rect(0,0,bitmap.getWidth(),bitmap.getHeight());



        //init player
        player = new Player(context, screenX, screenY);
        // init starting platform
        platform_starting = new Platform_Basic(context, screenX, screenY);
        platform_starting.setX(screenX/2);
        platform_starting.setY(screenY-50);

        // init platform selector
        pselection = new PlatformSelection(context, screenX, screenY);

        // init some platforms
        platforms = new Platform[totalplatforms];
        for(int i=0;i<totalplatforms;i++) {
            platforms[i] = null;
        }

        X = screenX;
        Y = screenY;


    }

    @Override
    public void run() {
        while (playing) {
            //to update the frame
            update();

            //to draw the frame
            draw();

            //to control
            control();
        }
    }


    private void update() {

        // update timer
        long millis = System.currentTimeMillis() - starttime;
        int seconds = (int) (millis / 1000);

        // add a platform every X second
        if (seconds-platformSpawnTime == prevSecond) {
            prevSecond = seconds;
            if (currentplatforms == totalplatforms-1) {
                // reset count since we can only have totalplatforms on the screen at a time.
                currentplatforms = 0;
            }
            platforms[currentplatforms] = pselection.getPlatform();
            currentplatforms++;
        }

        // update player
        player.update();

        // update starting platform
        platform_starting.update();
        if (Rect.intersects(player.getDetectCollision(),platform_starting.getDetectCollision())) {
            player.setY(platform_starting.getY()-player.getHeight());
        }

        // check all other platforms
        for(int i=0; i<totalplatforms;i++) {
            if(platforms[i] != null) {
                platforms[i].update();
                if (Rect.intersects(player.getDetectCollision(),platforms[i].getDetectCollision())) {
                    player.setY(platforms[i].getY()-player.getHeight());
                }
            }
        }

    }

    private void draw() {
        //checking if surface is valid
        if (surfaceHolder.getSurface().isValid()) {
            //locking the canvas
            canvas = surfaceHolder.lockCanvas();
             //redraw background
            canvas.drawBitmap(bitmap, rect,new Rect(0,0,canvas.getWidth(),canvas.getHeight()), null);

            //Drawing the player
            canvas.drawBitmap(
                    player.getBitmap(),
                    player.getX(),
                    player.getY(),
                    paint);

            //Drawing starting platform
            canvas.drawBitmap(
                    platform_starting.getBitmap(),
                    platform_starting.getX(),
                    platform_starting.getY(),
                    paint);

            //Drawing all platforms
            for (int i = 0; i < totalplatforms; i++) {
                if (platforms[i] != null) {
                    canvas.drawBitmap(
                            platforms[i].getBitmap(),
                            platforms[i].getX(),
                            platforms[i].getY(),
                            paint
                    );
                }
            }
            //Unlocking the canvas
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    private void control() {
        try {
            gameThread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void pause() {
        //when the game is paused
        //setting the variable to false
        playing = false;
        try {
            //stopping the thread
            gameThread.join();
        } catch (InterruptedException e) {
        }
    }

    public void resume() {
        //when the game is resumed
        //starting the thread again
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {

        float x = e.getX(e.getActionIndex());
        float y = e.getY(e.getActionIndex());

        switch (e.getActionMasked() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:
                //When the user presses on the screen
                //we will do something here
                if (x < X/2) {
                    player.moveleft(false);
                }
                if (x >= X/2) {
                    player.moveright(false);
                }
                break;
            case MotionEvent.ACTION_POINTER_UP:
                //When the user presses on the screen
                //we will do something here
                if (x < X/2) {
                    player.moveleft(false);
                }
                if (x >= X/2) {
                    player.moveright(false);
                }
                break;
            case MotionEvent.ACTION_DOWN:
                //When the user releases the screen
                //do something here
                if (x < X/2) {
                    player.moveleft(true);
                }
                if (x >= X/2) {
                    player.moveright(true);
                }
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                if (x < X/2) {
                    player.moveleft(true);
                    player.moveright(false);
                }
                if (x >= X/2) {
                    player.moveright(true);
                    player.moveleft(false);
                }
                break;
        }
        return true;
    }
}
