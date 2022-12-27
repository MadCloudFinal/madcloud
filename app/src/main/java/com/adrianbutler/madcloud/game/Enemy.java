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

    private Bitmap enemyBit;

    int x, y;
    int speed = 1;
    int maxY, minY;
    int maxX, minX;
    Rect hitbox;

    public Enemy(Context context, int screenX, int screenY)
    {
        enemyBit = BitmapFactory.decodeResource(context.getResources(), R.drawable.enemy1);
        maxY = screenY;
        maxX = screenX;
        minY = 0;
        minX = 0;

        Random spawner =new Random();
        speed = spawner.nextInt(7) + 10;
        x = screenX;
        y = spawner.nextInt(maxY) - enemyBit.getHeight();

        hitbox = new Rect(x, y, enemyBit.getWidth(), enemyBit.getHeight());
    }

    public void update()
    {
        x -= speed;
        if(x < minX - enemyBit.getWidth())
        {
            Random spawner = new Random();
            speed = spawner.nextInt(10) + 10;
            x = maxX;
            y = spawner.nextInt(maxY) - (enemyBit.getHeight() * 2);
        }
        hitbox.left = x;
        hitbox.top = y;
        hitbox.right = x + enemyBit.getWidth();
        hitbox.bottom = y + enemyBit.getHeight();
    }

    public Bitmap getEnemyBit() {
        return enemyBit;
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
        return maxY;
    }

    public int getMinY() {
        return minY;
    }

    public int getMaxX() {
        return maxX;
    }

    public int getMinX() {
        return minX;
    }
}
