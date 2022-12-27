package com.adrianbutler.madcloud.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements Runnable {

    // this checks if the game is being played
    volatile boolean isPlaying;

    private Player player;
    Enemy[] birds;
    int difficuty = 5;


    //Drawing objects
    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;

    // this will track the gameThread
    private Thread gameThread = null;

    public GameView(Context context, int screenX, int screenY){
        super(context);

        player = new Player(context, screenX, screenY);
        birds = new Enemy[difficuty];

        // these are our initialized drawing objects
        surfaceHolder = getHolder();
        paint = new Paint();

        for(int i = 0; i < difficuty; i++)
        {
            birds[i] = new Enemy(context, screenX, screenY);
        }
    }

    @Override
    public void run() {
        //this is our game loop
        while(isPlaying){

            for(int i = 0; i < difficuty; i++)
            {
                birds[i].update();
            }

            //updates the frame
            update();

            // draws the frame
            draw();

            control();
        }
    }

    private void update(){
        //Update the players position
        player.update();
    }

    private void draw(){
        // checking if the drawing surface is valid
        if(surfaceHolder.getSurface().isValid()){


            //Draw the player

            // locks our canvas
            canvas = surfaceHolder.lockCanvas();

            // sets background color for canvas
            canvas.drawColor(Color.WHITE);

            canvas.drawBitmap(player.getBitmap(), player.getX(), player.getY(), paint);

            for(int i = 0; i < difficuty; i++)
            {
                canvas.drawBitmap( birds[i].getEnemyBit(), birds[i].getX(), birds[i].getY(), paint);
            }

                //unlocks the canvas
                surfaceHolder.unlockCanvasAndPost(canvas);
            }
    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);


    }

    private  void control(){
        try {
            gameThread.sleep(17);
        }catch (InterruptedException event){
            event.printStackTrace();
        }
    }

    public void stopped(){
        //when the game is paused or when the player dies stop the thread
        isPlaying = false;
        try {
            //stop thread logic
            gameThread.join();
        }catch (InterruptedException event){

        }
    }

    public void resume(){
        isPlaying = true;
        gameThread = new Thread(this);
        gameThread.start();
    }


    // PLayer controls
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent){

        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK){
            case MotionEvent.ACTION_UP:
                // stops floating when released
                player.stopFloating();
                break;
            case MotionEvent.ACTION_DOWN:
                // starts floating when touched
                player.setFloating();
                break;
        }
        return true;
    }
}
