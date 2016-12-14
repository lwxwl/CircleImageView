package com.example.lwxwl.circleimageview2;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.MotionEvent;


/**
 * Created by lwxwl on 2016/12/9.
 */

public class View extends AppCompatImageView {

    private static final int default_border_width = 0;
    private static final int default_border_color = Color.BLACK;
    private int border_width = default_border_width;
    private int border_color = default_border_color;
    private Bitmap bitmap;
    private Paint paint;

    public View(Context context) {
        this(context, null);
    }

    public View(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public View(Context context, AttributeSet attributeSet, int defStyleAttr) {
        super(context, attributeSet, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attributeSet, R.styleable.View, defStyleAttr, 0);
        border_width = array.getDimensionPixelSize(R.styleable.View_border_width,default_border_width);
        border_color = array.getColor(R.styleable.View_border_color, default_border_color);
        array.recycle();
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);
        paint.setAlpha((int) 0.5f);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    public Bitmap circleBitmap(Bitmap bitmap) {
        Bitmap bmp = Bitmap.createBitmap(border_width, border_width, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmp);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setAlpha((int) 0.5f);
        canvas.drawCircle(getWidth() / 2, getWidth() / 2, getWidth() / 5, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        return bmp;
    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                paint.setAlpha(0);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                paint.setAlpha(0);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:

                break;
            default:
                paint.setAlpha(0);
                invalidate();
                break;
        }
        return super.onTouchEvent(event);
    }


    @Override
    protected void onDraw(Canvas canvas) {

        Bitmap bm = null;
        Drawable drawable = getDrawable();
        if (drawable != null) {
            BitmapDrawable bd = (BitmapDrawable) drawable;
            bm = bd.getBitmap();
        }
        if (bitmap != null) {
            bm = bitmap;
        }
        float scaleWidth = (getWidth()) * 1.0f / bm.getWidth();

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleWidth);

        Bitmap newbmp = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getWidth(), matrix, true);

        int src = canvas.saveLayer(0, 0, getWidth(), getHeight(), null, Canvas.MATRIX_SAVE_FLAG |
                Canvas.CLIP_SAVE_FLAG |
                Canvas.HAS_ALPHA_LAYER_SAVE_FLAG |
                Canvas.FULL_COLOR_LAYER_SAVE_FLAG |
                Canvas.CLIP_TO_LAYER_SAVE_FLAG);

        canvas.drawBitmap(newbmp, 0, 0, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        canvas.drawBitmap(circleBitmap(), 0, 0, paint);
        paint.setXfermode(null);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeWidth(border_width);
        paint.setColor(border_color);
        canvas.drawCircle(getWidth() / 2, getWidth() / 2, getWidth() / 5, paint);
        canvas.restoreToCount(src);
    }
}
