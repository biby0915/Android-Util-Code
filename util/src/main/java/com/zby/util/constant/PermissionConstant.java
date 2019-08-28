package com.zby.util.constant;

import android.Manifest;
import android.os.Build;
import android.support.annotation.RequiresApi;

/**
 * @author ZhuBingYang
 * @date 2019-08-22
 */
@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
public class PermissionConstant {
    public static final String[] GROUP_CALENDAR = {
            Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_CALENDAR
    };
    public static final String[] GROUP_CAMERA = {
            Manifest.permission.CAMERA
    };
    public static final String[] GROUP_CONTACTS = {
            Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS, Manifest.permission.GET_ACCOUNTS
    };
    public static final String[] GROUP_LOCATION = {
            Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
    };
    public static final String[] GROUP_MICROPHONE = {
            Manifest.permission.RECORD_AUDIO
    };
    public static final String[] GROUP_PHONE = {
            Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_PHONE_NUMBERS, Manifest.permission.CALL_PHONE,
            Manifest.permission.READ_CALL_LOG, Manifest.permission.WRITE_CALL_LOG, Manifest.permission.ADD_VOICEMAIL,
            Manifest.permission.USE_SIP, Manifest.permission.PROCESS_OUTGOING_CALLS, Manifest.permission.ANSWER_PHONE_CALLS
    };
    public static final String[] GROUP_PHONE_BELOW_O = {
            Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_PHONE_NUMBERS, Manifest.permission.CALL_PHONE,
            Manifest.permission.READ_CALL_LOG, Manifest.permission.WRITE_CALL_LOG, Manifest.permission.ADD_VOICEMAIL,
            Manifest.permission.USE_SIP, Manifest.permission.PROCESS_OUTGOING_CALLS
    };
    public static final String[] GROUP_SENSORS = {
            Manifest.permission.BODY_SENSORS
    };
    public static final String[] GROUP_SMS = {
            Manifest.permission.SEND_SMS, Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_SMS,
            Manifest.permission.RECEIVE_WAP_PUSH, Manifest.permission.RECEIVE_MMS,
    };
    public static final String[] GROUP_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
}
