package com.example.lwxwl.circleimageview2;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView img;
    private Button btn;
    private Intent intent;
    private int bitmapWidth;
    private int bitmapHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        img = (ImageView) findViewById(R.id.img);
        btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        intent = new Intent(MainActivity.this,View.class);
        startActivityForResult(intent,1);
    }

    public Bitmap circleBitmap(Bitmap bitmap) {
        bitmapWidth = bitmap.getWidth();
        bitmapHeight = bitmap.getHeight();
        int radius = (bitmapWidth < bitmapHeight ? bitmapWidth : bitmapHeight) / 2;
        Bitmap bmp = Bitmap.createBitmap(bitmapWidth, bitmapHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmp);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setAlpha((int) 0.5f);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        RectF rect = new RectF(0, 0, radius, radius);
        canvas.drawRoundRect(rect, radius/2, radius/2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, null, rect, paint);
        return bmp;
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    byte[] bytes = data.getByteArrayExtra("bitmap");
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    img.setImageBitmap(circleBitmap(bitmap));
                }
                break;
            default:
        }
    }
}

/*
  形象地来说就是先定义一块画布，再设置画笔的属性，后通过原矩形的坐标值来定义圆的坐标值，最终获得圆形的画布。
  于是到此为止画布就定义好啦，接下来就要往画布上作画了hh……手动跳转至Crop V(^_^)V
 */