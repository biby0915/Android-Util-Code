package com.zby.myapplication;


import android.media.projection.MediaProjectionManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.zby.myapplication.test.LoggerTest;
import com.zby.myapplication.test.RegexTest;
import com.zby.util.Logger;
import com.zby.util.ScreenUtil;
import com.zby.util.constant.PermissionConstant;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private ImageView iv;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iv = findViewById(R.id.iv);

//        System.out.println(AppUtil.getHostActivity(findViewById(R.id.tv).getContext()));

        new LoggerTest().run();
//        new PermissionTest().run();
        new RegexTest().run();


//        SpanUtil.with((TextView) findViewById(R.id.tv))
//                .append("123").setBackgroundColor(Color.CYAN)
//                .appendSpace(50)
//                .append("8765").setBold().setFontSize(80).setTextColor(Color.RED)
//                .create();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(PermissionConstant.GROUP_STORAGE, 1);
        }


        MediaProjectionManager projectionManager = (MediaProjectionManager) getSystemService(MEDIA_PROJECTION_SERVICE);
        startActivityForResult(projectionManager.createScreenCaptureIntent(), 10);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        Logger.D.log(TAG, requestCode + " " + permissions[0] + " " + grantResults[0]);
        iv.setImageBitmap(ScreenUtil.screenShot(this));

    }
}
