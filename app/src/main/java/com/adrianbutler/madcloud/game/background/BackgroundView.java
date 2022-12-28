package com.adrianbutler.madcloud.game.background;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.os.Handler;
import android.view.Display;
import android.view.SurfaceView;
import android.view.View;

import com.adrianbutler.madcloud.R;

public class BackgroundView extends SurfaceView {
    int screenWidth, screenHeight, newWidth, newHeight;
    int skyX = 0,loneCloudX = 0,cloudBgX = 0, mountainX = 0, cloudMg1X = 0, cloudMg2X = 0, cloudMg3X = 0;
    Bitmap sky,cloudBg,loneCloud, mountain, cloudMg1, cloudMg2, cloudMg3;
    Handler handler;
    Runnable runnable;
    final long UPDATE_MILLIS = 1000;

    public BackgroundView(Context context) {
        super(context);
        setLayerType(LAYER_TYPE_HARDWARE, null);

sky=BitmapFactory.decodeResource(getResources(),R.drawable.sky_lightened);
        cloudBg = BitmapFactory.decodeResource(getResources(), R.drawable.clouds_bg);
        loneCloud = BitmapFactory.decodeResource(getResources(), R.drawable.cloud_lonely);
        mountain = BitmapFactory.decodeResource(getResources(), R.drawable.glacial_mountains_lightened);
        cloudMg3 = BitmapFactory.decodeResource(getResources(), R.drawable.clouds_mg_3);
        cloudMg2 = BitmapFactory.decodeResource(getResources(), R.drawable.clouds_mg_2);
        cloudMg1 = BitmapFactory.decodeResource(getResources(), R.drawable.clouds_mg_1_lightened);
        Display display = ((Activity) getContext()).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;
        float height = sky.getHeight();
        float width = sky.getWidth();
        float ratio = width / height;
        newHeight = screenHeight;
        newWidth = (int) (ratio * screenHeight * 1.5);
        sky = Bitmap.createScaledBitmap(sky,newWidth,newHeight,false);
        cloudBg = Bitmap.createScaledBitmap(cloudBg,newWidth,newHeight,false);
        loneCloud = Bitmap.createScaledBitmap(loneCloud,newWidth,newHeight,false);
        mountain = Bitmap.createScaledBitmap(mountain,newWidth,newHeight,false);
        cloudMg3 = Bitmap.createScaledBitmap(cloudMg3,newWidth,newHeight,false);
        cloudMg2 = Bitmap.createScaledBitmap(cloudMg2,newWidth,newHeight,false);
        cloudMg1 = Bitmap.createScaledBitmap(cloudMg1,newWidth,newHeight,false);
        handler = new Handler();
        runnable = this::invalidate;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (skyX < -newWidth) {
            skyX = 0;
        }
        canvas.drawBitmap(sky, skyX, 0, null);
        if (skyX < screenWidth - newWidth) {
            canvas.drawBitmap(sky, skyX + newWidth, 0, null);
        }
        cloudBgX -= 2;
        if (cloudBgX < -newWidth) {
            cloudBgX = 0;
        }
        canvas.drawBitmap(cloudBg, cloudBgX, 0, null);
        if (cloudBgX < screenWidth - newWidth) {
            canvas.drawBitmap(cloudBg, cloudBgX + newWidth, 0, null);
        }
        loneCloudX -= 3;
        if (loneCloudX < -newWidth) {
            loneCloudX = 0;
        }
        canvas.drawBitmap(loneCloud, loneCloudX, 0, null);
        if (loneCloudX < screenWidth - newWidth) {
            canvas.drawBitmap(loneCloud, loneCloudX + newWidth, 0, null);
        }
        mountainX -= 1;
        if (mountainX < -newWidth) {
            mountainX = 0;
        }
        canvas.drawBitmap(mountain, mountainX, 0, null);
        if (mountainX < screenWidth - newWidth) {
            canvas.drawBitmap(mountain, mountainX + newWidth, 0, null);

        }
        cloudMg3X -= 5;
        if (cloudMg3X < -newWidth) {
            cloudMg3X = 0;
        }
        canvas.drawBitmap(cloudMg3, cloudMg3X, 0, null);
        if (cloudMg3X < screenWidth - newWidth) {
            canvas.drawBitmap(cloudMg3, cloudMg3X + newWidth, 0, null);
        }
        cloudMg2X -= 3;
        if (cloudMg2X < -newWidth) {
            cloudMg2X = 0;
        }
        canvas.drawBitmap(cloudMg2, cloudMg2X, 0, null);
        if (cloudMg2X < screenWidth - newWidth) {
            canvas.drawBitmap(cloudMg2, cloudMg2X + newWidth, 0, null);
        }
        cloudMg1X -= 5;
        if (cloudMg1X < -newWidth) {
            cloudMg1X = 0;
        }
        canvas.drawBitmap(cloudMg1, cloudMg1X, 0, null);
        if (cloudMg1X < screenWidth - newWidth) {
            canvas.drawBitmap(cloudMg1, cloudMg1X + newWidth, 0, null);
        }
        invalidate();
    }

    public Bitmap getSky() {
        return sky;
    }

    public Bitmap getMountain() {
        return mountain;
    }
}

