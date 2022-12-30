package com.adrianbutler.madcloud.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.Display;

import androidx.appcompat.app.AppCompatActivity;

import com.adrianbutler.madcloud.R;

import java.util.Random;

public class Enemy{

    private Bitmap[] enemyBit;  // Array to store the series of images
    private int currentFrame;   // Index of the current frame in the animation

    int x, y;
    int speed = 1;
    int maxY, minY;
    int maxX, minX;
    int minStartingY;
    int maxStartingY;
    int uiSize = 50;
    Rect hitbox;

    public Enemy(Context context, int screenX, int screenY)
    {
        // Load the series of images into the array
        enemyBit = new Bitmap[21];
        enemyBit[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.raven_black0001);
        enemyBit[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.raven_black0002);
        enemyBit[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.raven_black0003);
        enemyBit[3] = BitmapFactory.decodeResource(context.getResources(), R.drawable.raven_black0004);
        enemyBit[4] = BitmapFactory.decodeResource(context.getResources(), R.drawable.raven_black0005);
        enemyBit[5] = BitmapFactory.decodeResource(context.getResources(), R.drawable.raven_black0006);
        enemyBit[6] = BitmapFactory.decodeResource(context.getResources(), R.drawable.raven_black0007);
        enemyBit[7] = BitmapFactory.decodeResource(context.getResources(), R.drawable.raven_black0008);
        enemyBit[8] = BitmapFactory.decodeResource(context.getResources(), R.drawable.raven_black0009);
        enemyBit[9] = BitmapFactory.decodeResource(context.getResources(), R.drawable.raven_black0010);
        enemyBit[10] = BitmapFactory.decodeResource(context.getResources(), R.drawable.raven_black0012);
        enemyBit[11] = BitmapFactory.decodeResource(context.getResources(), R.drawable.raven_black0010);
        enemyBit[12] = BitmapFactory.decodeResource(context.getResources(), R.drawable.raven_black0009);
        enemyBit[13] = BitmapFactory.decodeResource(context.getResources(), R.drawable.raven_black0008);
        enemyBit[14] = BitmapFactory.decodeResource(context.getResources(), R.drawable.raven_black0007);
        enemyBit[15] = BitmapFactory.decodeResource(context.getResources(), R.drawable.raven_black0006);
        enemyBit[16] = BitmapFactory.decodeResource(context.getResources(), R.drawable.raven_black0005);
        enemyBit[17] = BitmapFactory.decodeResource(context.getResources(), R.drawable.raven_black0004);
        enemyBit[18] = BitmapFactory.decodeResource(context.getResources(), R.drawable.raven_black0003);
        enemyBit[19] = BitmapFactory.decodeResource(context.getResources(), R.drawable.raven_black0002);
        enemyBit[20] = BitmapFactory.decodeResource(context.getResources(), R.drawable.raven_black0001);

        Random random = new Random();
        currentFrame = random.nextInt(enemyBit.length);  // Set the initial frame to the first image in the array


        for (int i = 0; i < enemyBit.length; i++) {
            enemyBit[i] = Bitmap.createScaledBitmap(enemyBit[i], 100, 100, false);
        }

        maxY = screenY - uiSize;
        maxX = screenX;
        minY = uiSize;
        minX = 0;

        hitbox = new Rect(x, y, enemyBit[0].getWidth(), enemyBit[0].getHeight());
        speed = random.nextInt(7) + 10;
        y = random.nextInt(maxY) - enemyBit[0].getHeight();
        x = screenX;




//        speed = random.nextInt(7) + 3;
//        x = screenX;
//        y = random.nextInt(maxStartingY - minStartingY + 1);
//
//        hitbox = new Rect(x, y, enemyBit[currentFrame].getWidth(), enemyBit[currentFrame].getHeight());
    }

    public void update()
    {
        x -= speed;
        if(x < minX - enemyBit[currentFrame].getWidth())
        {
            Random spawner = new Random();
            speed = spawner.nextInt(10) + 10;
            x = maxX;
            y = spawner.nextInt(maxY) - (enemyBit[currentFrame].getHeight() * 2);
        }
        hitbox.left = x;
        hitbox.top = y;
        hitbox.right = x + enemyBit[currentFrame].getWidth();
        hitbox.bottom = y + enemyBit[currentFrame].getHeight();

        // Update the current frame of the animation
        currentFrame = (currentFrame + 1) % enemyBit.length;
    }

    public Bitmap getEnemyBit() {
        return enemyBit[currentFrame];  // Return the current frame of the animation
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public Rect getHitbox() {
        return hitbox;
    }

    public int getSpeed() {
        return speed;
    }

    public int getMaxY() {

            return minY;
        }

        public int getMaxX() {
            return maxX;
        }

        public int getMinX() {
            return minX;
        }
    }


//    int x, y;
//    int speed = 1;
//    int maxY, minY;
//    int maxX, minX;
//    int uiSize = 50;
//    Rect hitbox;
//
//    public Enemy(Context context, int screenX, int screenY)
//    {
//        enemyBit = BitmapFactory.decodeResource(context.getResources(), R.drawable.enemy1);
//        maxY = screenY - uiSize;
//        maxX = screenX;
//        minY = uiSize;
//        minX = 0;
//
//        Random spawner =new Random();
//        speed = spawner.nextInt(7) + 10;
//        x = screenX;
//        y = spawner.nextInt(maxY) - enemyBit.getHeight();
//
//        hitbox = new Rect(x, y, enemyBit.getWidth(), enemyBit.getHeight());
//    }
//
//    public void update()
//    {
//        x -= speed;
//        if(x < minX - enemyBit.getWidth())
//        {
//            Random spawner = new Random();
//            speed = spawner.nextInt(10) + 10;
//            x = maxX;
//            y = spawner.nextInt(maxY) - (enemyBit.getHeight() * 2);
//        }
//        hitbox.left = x;
//        hitbox.top = y;
//        hitbox.right = x + enemyBit.getWidth();
//        hitbox.bottom = y + enemyBit.getHeight();
//    }
//
//    public Bitmap getEnemyBit() {
//        return enemyBit;
//    }
//
//    public int getX() {
//        return x;
//    }
//
//    public int getY() {
//        return y;
//    }
//
//    public void setX(int x) {
//        this.x = x;
//    }
//
//    public Rect getHitbox() {
//        return hitbox;
//    }
//
//    public int getSpeed() {
//        return speed;
//    }
//
//    public int getMaxY() {
//        return maxY;
//    }
//
//    public int getMinY() {
//        return minY;
//    }
//
//    public int getMaxX() {
//        return maxX;
//    }
//
//    public int getMinX() {
//        return minX;
//    }

