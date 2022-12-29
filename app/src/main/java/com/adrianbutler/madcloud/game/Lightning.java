package com.adrianbutler.madcloud.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import com.adrianbutler.madcloud.R;

import java.util.Random;

public class Lightning {
    private Bitmap lightningBit;
    int x, y;
    int speed = 1;
    int maxY, minY;
    int maxX, minX;
    int uiSize = 50;
    Rect hitbox;

    public Lightning(Context context, int screenX, int screenY) {
        Bitmap original = BitmapFactory.decodeResource(context.getResources(), R.drawable.lightning);
        lightningBit = Bitmap.createScaledBitmap(original, 50, 50, true);
        maxY = screenY - uiSize;
        maxX = screenX;
        minY = uiSize;
        minX = 0;

        Random spawner = new Random();
        speed = spawner.nextInt(7) + 10;
        x = screenX;
        y = spawner.nextInt(maxY) - lightningBit.getHeight();
        hitbox = new Rect(x, y, lightningBit.getWidth(), lightningBit.getHeight());
    }

    public void update() {
        x -= speed;
        if (x < minX - lightningBit.getWidth()) {
            Random spawner = new Random();
            speed = spawner.nextInt(10) + 10;
            x = maxX;
            y = spawner.nextInt(maxY) - (lightningBit.getHeight() * 2);
        }
        hitbox.left = x;
        hitbox.top = y;
        hitbox.right = x + lightningBit.getWidth();
        hitbox.bottom = y + lightningBit.getHeight();
    }

    public Rect getHitbox() {
        return hitbox;
    }

    public Bitmap getLightningBit() {
        return lightningBit;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }
}
