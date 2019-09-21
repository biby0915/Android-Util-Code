package com.zby.myapplication;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.zby.myapplication.test.LoggerTest;
import com.zby.myapplication.test.RegexTest;
import com.zby.util.ImageUtil;
import com.zby.util.Logger;
import com.zby.util.ScreenUtil;
import com.zby.util.TextUtil;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private ImageView iv;
    private SeekBar seekBar;
    Bitmap bitmap;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iv = findViewById(R.id.iv);
        seekBar = findViewById(R.id.seekBar);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;
        options.inMutable = true;
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.picture, options);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                throw new RuntimeException("crash");
                long s = System.currentTimeMillis();
                iv.setImageBitmap(ImageUtil.renderBlur(bitmap, progress / 5, false));
                Logger.D.log(TAG, "cost:" + (System.currentTimeMillis() - s));

                Logger.E.log(TAG, String.valueOf(System.currentTimeMillis()));

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

//        System.out.println(AppUtil.getHostActivity(findViewById(R.id.tv).getContext()));

        new LoggerTest().run();
//        new PermissionTest().run();
        new RegexTest().run();


//        SpanUtil.with((TextView) findViewById(R.id.tv))
//                .append("123").setBackgroundColor(Color.CYAN)
//                .appendSpace(50)
//                .append("8765").setBold().setFontSize(80).setTextColor(Color.RED)
//                .create();

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            requestPermissions(PermissionConstant.GROUP_STORAGE, 1);
//        }

        Logger.I.log(TAG, TextUtil.getPinYin("第三方接口和接开关即可很快就好转换是尽快哈萨克大V这接口花几十块"));


//        MediaProjectionManager projectionManager = (MediaProjectionManager) getSystemService(MEDIA_PROJECTION_SERVICE);
//        startActivityForResult(projectionManager.createScreenCaptureIntent(), 10);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        Logger.D.log(TAG, requestCode + " " + permissions[0] + " " + grantResults[0]);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        iv.setImageBitmap(ImageUtil.stackBlur(ScreenUtil.screenShot(this), 20, false));
    }
}
