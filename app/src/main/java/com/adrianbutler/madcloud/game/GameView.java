package com.adrianbutler.madcloud.game;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.adrianbutler.madcloud.ads.AdManager;
import com.adrianbutler.madcloud.game.background.BackgroundView;

public class GameView extends SurfaceView implements Runnable {

    public Integer score;

    // this checks if the game is being played
    volatile boolean isPlaying;
    int uiSize = 50;

    private Player player;
    Enemy[] birds;
    int difficulty = 5;
    Lightning[] lightning;

    //Drawing objects
    private Paint paint;

    public Integer getScore() {
        return score;
    }

    private Canvas canvas;
    private SurfaceHolder surfaceHolder;

    BackgroundView backgroundView;

    Activity addActivity;
    GameActivity gameActivity;
    AdManager adManager;
    GameOver gameOver;

    // this will track the gameThread

    Thread gameThread = null;
    Thread addThread;

    public GameView(Context context, int screenX, int screenY, GameActivity gameActivity) {
        super(context);
        setLayerType(LAYER_TYPE_HARDWARE, null);

        adManager = new AdManager(context, false);
        addActivity = (Activity) context;

        this.gameActivity = gameActivity;


        score = 0;

        player = new Player(context, screenX, screenY);
        birds = new Enemy[difficulty];
        lightning = new Lightning[difficulty - 2];

        // these are our initialized drawing objects
        surfaceHolder = getHolder();
        paint = new Paint();
        backgroundView = new BackgroundView(context);

        for (int i = 0; i < (difficulty - 2); i++) {
            lightning[i] = new Lightning(context, screenX, screenY);
        }

        for (int i = 0; i < difficulty; i++) {
            birds[i] = new Enemy(context, screenX, screenY);
        }
    }

    @Override
    public void run() {

        //this is our game loop
        while (isPlaying) {

            for (int i = 0; i < difficulty; i++) {
                birds[i].update();
                if (Rect.intersects(player.getHitbox(), birds[i].getHitbox())) {
                    birds[i].setX(-300);

                    Intent intent = new Intent(getContext(), GameOver.class);
                    intent.putExtra("score", score.toString());
                    getContext().startActivity(intent);

                    try
                    {
                        gameThread.join();
                    } catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
            //updates the frame
            update();
            // draws the frame
            draw();
            control();
        }
    }

    private void update() {

        //Update the players position
        player.update();
        for (int i = 0; i < (difficulty - 2); i++) {
            lightning[i].update();
            if (Rect.intersects(player.getHitbox(), lightning[i].getHitbox())) {
                score += 5;
                lightning[i].setX(-200);
            }
        }
    }

    private void draw() {

        // checking if the drawing surface is valid
        if (surfaceHolder.getSurface().isValid()) {
            // locks our canvas
            canvas = surfaceHolder.lockCanvas();

//            backgroundView.draw(canvas);
             canvas.drawBitmap(backgroundView.getSky(), 0,0,null);
             canvas.drawBitmap(backgroundView.getMountain(),0,0, null);

            paint.setTextSize(40);
            canvas.drawText("Score " + score, 100, 50, paint);

            canvas.drawBitmap(player.getBitmap(), player.getX(), player.getY(), paint);

            for (int i = 0; i < difficulty; i++) {
                canvas.drawBitmap(birds[i].getEnemyBit(), birds[i].getX(), birds[i].getY(), paint);
            }
            for (int i = 0; i < (difficulty - 2); i++) {
                canvas.drawBitmap(lightning[i].getLightningBit(), lightning[i].getX(), lightning[i].getY(), paint);
            }
            //unlocks the canvas
            surfaceHolder.unlockCanvasAndPost(canvas);
        }

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }

    private void control() {
        try {
            gameThread.sleep(20);

        } catch (InterruptedException event) {
            event.printStackTrace();
        }
    }

    public void stopped() {
        //when the game is paused or when the player dies stop the thread
        isPlaying = false;

//        try {
//            gameThread.join();
//        }catch (InterruptedException exception){}

    }

    public void resume() {
        isPlaying = true;
        gameThread = new Thread(this);
        gameThread.start();
    }


    // PLayer controls
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {

        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
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
