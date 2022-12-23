package com.adrianbutler.madcloud.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;

import com.adrianbutler.madcloud.R;

public class Player {

    private Bitmap bitmap;
    AnimationDrawable testPlayer;

    private int x;
    private int y;
    private int maxY;
    private int minY;

    private int speed = 0;
    // limit the speed
    private final int MIN_SPEED = 1;
    private final int MAX_SPEED = 20;

    public boolean floating;

    private final int GRAVITY = -10;

    public  void setFloating(){floating = true;}
    public void stopFloating(){floating = false;}

    public Player(Context context, int screenX, int screenY) {
        x = 75;
        y = 50;
        speed = 1;

        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.cloud);

//        testPlayer = sprite.createAnimation(1);


        maxY = screenY - bitmap.getHeight();
        minY = 0;

        floating = false;
    }

    // this will update player movement
    public void update() {
        // if the cloud floats
        if(floating){
            speed += 2;
        } else {
            speed -= 5;
        }

        // max floating speed logic
        if(speed > MAX_SPEED){
            speed = MAX_SPEED;
        }


        if(speed < MIN_SPEED){
            speed = MIN_SPEED;
        }

        // moves ship down due to gravity
        y -= speed + GRAVITY;

        // keeps the ship on screen
        if(y < minY){
            y = minY;
        }

        if(y > maxY){
            y = maxY;
        }
    }

    public AnimationDrawable getTestPlayer() {
        return testPlayer;
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

    public int getSpeed() {
        return speed;
    }
}
