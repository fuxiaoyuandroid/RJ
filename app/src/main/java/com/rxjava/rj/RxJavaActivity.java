package com.rxjava.rj;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class RxJavaActivity extends AppCompatActivity {
    private ImageView iv;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            Bitmap bitmap = (Bitmap) msg.obj;
            iv.setImageBitmap(bitmap);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java);
        iv = (ImageView) findViewById(R.id.iv);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    URL url = new URL("https://p1.ssl.qhmsg.com/dr/705_705_/t01ff2a4973e1683142.webp?size=705x705");
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    InputStream inputStream = httpURLConnection.getInputStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                    bitmap = createImageMark(bitmap,"RxJava修炼手册");
                    Message message = Message.obtain();
                    message.obj = bitmap;
                    handler.sendMessage(message);
                }catch (Exception e){
                    e.printStackTrace();
                }


            }
        }).start();
    }

    private Bitmap createImageMark(Bitmap bitmap, String mark) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        Bitmap bmp = Bitmap.createBitmap(w,h,Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmp);
        Paint p = new Paint();
        p.setColor(Color.parseColor("#ff0000"));
        p.setTextSize(50);
        p.setAntiAlias(true);
        canvas.drawBitmap(bitmap,0,0,p);
        canvas.drawText(mark,0,h/2,p);
        canvas.save(Canvas.ALL_SAVE_FLAG);
        canvas.restore();
        return bmp;
    }
}
