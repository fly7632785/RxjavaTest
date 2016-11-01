package com.jafir.mytestscroll;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.uniquestudio.lowpoly.LowPoly;

public class MainActivity extends AppCompatActivity {

    int[] res = new int[]{R.mipmap.splash4, R.mipmap.splash5, R.mipmap.splash6, R.mipmap.splash7, R.mipmap.splash8};

    public int index;
    private int i = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final ImageView imageView = (ImageView) findViewById(R.id.iv);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 4;
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), res[index % res.length], options);
//        imageView.setImageBitmap(bitmap);
                Bitmap out = LowPoly.generate(bitmap, i);
                imageView.setImageBitmap(out);
                index++;
            }
        });

        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (1 > 3) {
                    i -= 2;
                }
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 4;
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), res[index % res.length], options);
//        imageView.setImageBitmap(bitmap);
                Bitmap out = LowPoly.generate(bitmap, i);
                imageView.setImageBitmap(out);
            }
        });
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i +=3;
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 4;
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), res[index % res.length], options);
//        imageView.setImageBitmap(bitmap);
                Bitmap out = LowPoly.generate(bitmap, i);
                imageView.setImageBitmap(out);
            }
        });

    }
}
