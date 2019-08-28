package com.zby.myapplication;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.zby.util.Logger;
import com.zby.util.PermissionUtil;
import com.zby.util.TimeUtil;
import com.zby.util.constant.PermissionConstant;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        System.out.println(AppUtil.getHostActivity(findViewById(R.id.tv).getContext()));
//
//        System.out.println("ssid:" + NetUtil.getWifiSSID(getApplicationContext()));
//        System.out.println("bssid:" + NetUtil.getWifiBSSID(getApplicationContext()));

//        System.out.println(RegexUtil.isFloat("1234.567"));
//        System.out.println(RegexUtil.isFloat("1234.567f"));
//        System.out.println(RegexUtil.isFloat("1234"));
//        System.out.println(RegexUtil.isFloat("afds"));

//        PermissionUtil.hasAllPermissions(getApplicationContext(), PermissionConstant.GROUP_STORAGE);
//        PermissionUtil.hasAllPermissions(getApplicationContext(), PermissionConstant.GROUP_CAMERA);
//        PermissionUtil.hasAllPermissions(getApplicationContext(), PermissionConstant.GROUP_CALENDAR);
//        PermissionUtil.hasAllPermissions(getApplicationContext(), PermissionConstant.GROUP_CONTACTS);
//        PermissionUtil.hasAllPermissions(getApplicationContext(), PermissionConstant.GROUP_LOCATION);
//        PermissionUtil.hasAllPermissions(getApplicationContext(), PermissionConstant.GROUP_SENSORS);

//        Logger.list("list", Arrays.asList("asd","add","asf"));


        Map<String, Entity> map = new HashMap<>();
        map.put("hehe", new Entity("hehehe", "he"));
        map.put("haha", new Entity("hahahah", "ha"));
        map.put("xixi", new Entity("xixixi", "xi"));
        Logger.map(TAG, map);

//        Logger.json(TAG, "{\"code\":200,\"data\":{\"valid\":true,\"sceneType\":\"IFTTT\",\"enable\":true,\"name\":\"0430wct摄像头测试-报警开关 - 关\",\"icon\":\"http://gaic.alicdn.com/ztms/cloud-intelligence-icons/icons/scene_img_choice_icon_4.png\",\"iconColor\":\"#03D0C4\",\"actionsJson\":[\"{\"params\":{\"identifier\":\"AlarmSwitch\",\"iotId\":\"iUqMoj7358CjcpSXevpA000101\",\"localizedProductName\":\"0430wct摄像头测试\",\"productImage\":\"http://iotx-paas-admin.oss-cn-shanghai.aliyuncs.com/publish/image/1559631717673.png\",\"propertyName\":\"AlarmSwitch\",\"localizedPropertyName\":\"报警开关\",\"deviceNickName\":\"0430wct摄像头测试\",\"propertyValue\":0,\"localizedCompareValueName\":\"关\",\"productKey\":\"a1i80V6Jhbu\",\"propertyItems\":{\"AlarmSwitch\":0},\"deviceName\":\"VD_3RI4Ygffu9\"},\"uri\":\"action/device/setProperty\",\"status\":1}\"],\"id\":\"8adf98031c0c4eadbff9a35ff50ddc5c\",\"caConditionsJson\":[]},\"id\":\"3ec242a1-a2c1-4d4a-9d1e-b1829ea0ddc3\"}");

        try {
            Logger.d(TAG, String.valueOf(TimeUtil.parseDate("2009-02-14 07:31:31", TimeUtil.YMDHMS).getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long current = System.currentTimeMillis();

        try {
            for (int i = 0; i < 100000; i++) {
                SimpleDateFormat df = new SimpleDateFormat(TimeUtil.YMDHMS);
                df.parse("2019-08-28 12:34:56");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(System.currentTimeMillis() - current);

        current = System.currentTimeMillis();

        try {
            for (int i = 0; i < 100000; i++) {
                TimeUtil.parseDate("2019-08-28 12:34:56", TimeUtil.YMDHMS);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        System.out.println(System.currentTimeMillis() - current);
    }
}
