package com.example.thefire;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;


import java.util.Arrays;
import java.util.Random;

public class FireView extends View {
    private static final int[] firePalette = {
            0xff070707,
            0xff1F0707,
            0xff2F0F07,
            0xff470F07,
            0xff571707,
            0xff671F07,
            0xff771F07,
            0xff8F2707,
            0xff9F2F07,
            0xffAF3F07,
            0xffBF4707,
            0xffC74707,
            0xffDF4F07,
            0xffDF5707,
            0xffDF5707,
            0xffD75F07,
            0xffD75F07,
            0xffD7670F,
            0xffCF6F0F,
            0xffCF770F,
            0xffCF7F0F,
            0xffCF8717,
            0xffC78717,
            0xffC78F17,
            0xffC7971F,
            0xffBF9F1F,
            0xffBF9F1F,
            0xffBFA727,
            0xffBFA727,
            0xffBFAF2F,
            0xffB7AF2F,
            0xffB7B72F,
            0xffB7B737,
            0xffCFCF6F,
            0xffDFDF9F,
            0xffEFEFC7,
            0xffFFFFFF
    };

    private final Paint paint = new Paint();
    private Bitmap bitmap;
    Random rand = new Random();
    private byte[][] temp;

    public FireView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        int fireW = w / 4;
        int fireH = h / 4;
        temp = new byte[fireH][fireW];
        Arrays.fill(temp[fireH - 1], (byte) (firePalette.length - 1));

        bitmap = Bitmap.createBitmap(fireW, fireH, Bitmap.Config.RGB_565);

    }


    @Override
    public void onDraw(Canvas canvas) {
        for (int y = 0; y < temp.length - 1; y++) {
            for (int x = 0; x < temp[y].length; x++) {
                int dt = rand.nextInt(2);
                int dx = rand.nextInt(3) - 1;
                int dy = rand.nextInt(6);

                int x1 = Math.max(0, Math.min(x + dx, temp[y].length - 1));
                int y1 = Math.min(temp.length - 1, y + dy);

                temp[y][x] = (byte) (Math.max(0, (temp[y1][x1] - dt)));
            }
        }

        for (int y = 0; y < temp.length - 1; y++) {
            for (int x = 0; x < temp[y].length; x++) {
                int color = firePalette[temp[y][x]];
                bitmap.setPixel(x, y, color);

            }
        }
        float scaleX = getWidth() / bitmap.getWidth();
        float scaleY = getHeight() / bitmap.getHeight();
        canvas.scale(scaleX, scaleY);
        canvas.drawBitmap(bitmap, 0, 0, paint);
        invalidate();
    }

}

