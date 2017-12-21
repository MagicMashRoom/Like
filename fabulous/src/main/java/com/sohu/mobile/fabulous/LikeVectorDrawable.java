package com.sohu.mobile.fabulous;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;

import java.util.Random;

/**
 * Created by dafengluo on 2017/12/21.
 */

public class LikeVectorDrawable extends VectorDrawable{
    public static final int DEFAULT_HEART_SIZE = 256;
    private final Context context;

    private static final int ALPHA = 255;
    private Paint paint;

    public LikeVectorDrawable(Context context) {
        this.context = context;
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setDither(true);
        paint.setColor(Color.argb(ALPHA, 244, 92, 71));
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        drawHeart(canvas);
        canvas.save();
        canvas.restore();
        paint.reset();
        paint.setColor(Color.argb(ALPHA, 244, 92, 71));
    }

    @Override
    public void setAlpha(int i) {
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }
    @Override
    public int getIntrinsicHeight() {
        return 72 * 3;
    }

    @Override
    public int getIntrinsicWidth() {
        return 72 * 3;
    }

    private void drawHeart(Canvas canvas) {
        if (canvas == null) {
            return;
        }
        Random random = new Random();
        Matrix matrix = new Matrix();
        matrix.setRotate((float) (random.nextInt(90) - 45), 108, 108);
        Bitmap bitmap = getBitmapFromVectorDrawable(context, R.drawable.ic_favorite_black_24dp);
        canvas.drawBitmap(bitmap, matrix, paint);
    }

    public Bitmap getBitmapFromVectorDrawable(Context context, int drawableId) {
        Drawable drawable = ContextCompat.getDrawable(context, drawableId);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            drawable = (DrawableCompat.wrap(drawable)).mutate();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);

        return bitmap;
    }
}
