package com.adrianbutler.madcloud.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.AnimationDrawable;
import android.provider.ContactsContract;
import android.widget.ImageView;

import com.adrianbutler.madcloud.R;

public class Player {

    private Bitmap[] playerAnimation;  // Array to store the series of images
    private int currentFrame;   // Index of the current frame in the animation

    private Rect hitbox;
    private int uiSize = 50;

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

        // Load the series of images into the array
        playerAnimation = new Bitmap[5];
        playerAnimation[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.cloud);
        playerAnimation[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.cloud_2);
        playerAnimation[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.cloud_3);
        playerAnimation[3] = BitmapFactory.decodeResource(context.getResources(), R.drawable.cloud_4);
        playerAnimation[4] = BitmapFactory.decodeResource(context.getResources(), R.drawable.cloud_5);
        currentFrame = 0;  // Set the initial frame to the first image in the array

        maxY = screenY - playerAnimation[currentFrame].getHeight();
        minY = uiSize;
        hitbox = new Rect(x,y,playerAnimation[currentFrame].getWidth(),playerAnimation[currentFrame].getHeight());
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

        hitbox.top = y;
        hitbox.bottom = y + playerAnimation[currentFrame].getHeight();
        hitbox.left = x;
        hitbox.right = x + playerAnimation[currentFrame].getWidth();

        // Update the current frame of the animation
        currentFrame = (currentFrame + 1) % playerAnimation.length;
    }

//public class Player {
//
//    private Bitmap bitmap;
//     private Rect hitbox;
//     private int uiSize = 50;
//
//    private Bitmap[] playerAnimation = new Bitmap[5];
//
//    public Bitmap[] getPlayerAnimation() {
//        return playerAnimation;
//    }
//
//    private int x;
//    private int y;
//    private int maxY;
//    private int minY;
//
//    private int speed = 0;
//
//    // limit the speed
//    private final int MIN_SPEED = 1;
//    private final int MAX_SPEED = 20;
//
//    public boolean floating;
//
//    private final int GRAVITY = -10;
//
//    public  void setFloating(){floating = true;}
//    public void stopFloating(){floating = false;}
//
//    public Player(Context context, int screenX, int screenY) {
//        x = 75;
//        y = 50;
//        speed = 1;
//
//        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.cloud);
//
//        playerAnimation[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.cloud);
//        playerAnimation[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.cloud_2);
//        playerAnimation[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.cloud_3);
//        playerAnimation[3] = BitmapFactory.decodeResource(context.getResources(), R.drawable.cloud_4);
//        playerAnimation[4] = BitmapFactory.decodeResource(context.getResources(), R.drawable.cloud_5);
//
//        maxY = screenY - bitmap.getHeight();
//        minY = uiSize;
//        hitbox = new Rect(x,y,bitmap.getWidth(),bitmap.getHeight());
//        floating = false;
//    }
//
//    // this will update player movement
//    public void update() {
//        // if the cloud floats
//        if(floating){
//            speed += 2;
//        } else {
//            speed -= 5;
//        }
//
//        // max floating speed logic
//        if(speed > MAX_SPEED){
//            speed = MAX_SPEED;
//        }
//
//
//        if(speed < MIN_SPEED){
//            speed = MIN_SPEED;
//        }
//
//        // moves ship down due to gravity
//        y -= speed + GRAVITY;
//
//        // keeps the ship on screen
//        if(y < minY){
//            y = minY;
//        }
//
//        if(y > maxY){
//            y = maxY;
//        }
//
//        hitbox.top = y;
//        hitbox.bottom = y + bitmap.getHeight();
//        hitbox.left = x;
//        hitbox.right = x + bitmap.getWidth();


    public Bitmap getBitmap() {
        return playerAnimation[currentFrame];
    }

    public Rect getHitbox() {
        return hitbox;
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

