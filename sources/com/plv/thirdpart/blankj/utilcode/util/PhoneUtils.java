package com.plv.thirdpart.blankj.utilcode.util;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.SystemClock;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import com.aliyun.vod.log.core.AliyunLogCommon;
import com.google.android.exoplayer2.C;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes5.dex */
public final class PhoneUtils {
    private PhoneUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    @Deprecated
    public static void call(String str) {
        LogUtils.e("拨打电话方法涉及权限，需要由开发者自行实现");
    }

    public static void dial(String str) {
        Utils.getApp().startActivity(IntentUtils.getDialIntent(str));
    }

    public static List<HashMap<String, String>> getAllContactInfo() {
        SystemClock.sleep(C.DEFAULT_MAX_SEEK_TO_PREVIOUS_POSITION_MS);
        ArrayList arrayList = new ArrayList();
        ContentResolver contentResolver = Utils.getApp().getContentResolver();
        Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");
        Uri uri2 = Uri.parse("content://com.android.contacts/data");
        Cursor cursorQuery = contentResolver.query(uri, new String[]{"contact_id"}, null, null, null);
        if (cursorQuery != null) {
            while (cursorQuery.moveToNext()) {
                try {
                    String string = cursorQuery.getString(0);
                    if (!StringUtils.isEmpty(string)) {
                        Cursor cursorQuery2 = contentResolver.query(uri2, new String[]{"data1", "mimetype"}, "raw_contact_id=?", new String[]{string}, null);
                        HashMap map = new HashMap();
                        if (cursorQuery2 != null) {
                            while (cursorQuery2.moveToNext()) {
                                String string2 = cursorQuery2.getString(0);
                                String string3 = cursorQuery2.getString(1);
                                if (string3.equals("vnd.android.cursor.item/phone_v2")) {
                                    map.put(AliyunLogCommon.TERMINAL_TYPE, string2);
                                } else if (string3.equals("vnd.android.cursor.item/name")) {
                                    map.put("name", string2);
                                }
                            }
                        }
                        arrayList.add(map);
                        if (cursorQuery2 != null) {
                            cursorQuery2.close();
                        }
                    }
                } finally {
                    cursorQuery.close();
                }
            }
        }
        if (cursorQuery != null) {
        }
        return arrayList;
    }

    @Deprecated
    public static void getAllSMS() {
        LogUtils.e("获取手机短信方法涉及权限，需要由开发者自行实现");
    }

    public static void getContactNum() {
        Log.d("tips", "U should copy the following code.");
    }

    @SuppressLint({"HardwareIds"})
    public static String getIMEI() {
        TelephonyManager telephonyManager = (TelephonyManager) Utils.getApp().getSystemService(AliyunLogCommon.TERMINAL_TYPE);
        if (telephonyManager != null) {
            return telephonyManager.getDeviceId();
        }
        return null;
    }

    @SuppressLint({"HardwareIds"})
    public static String getIMSI() {
        TelephonyManager telephonyManager = (TelephonyManager) Utils.getApp().getSystemService(AliyunLogCommon.TERMINAL_TYPE);
        if (telephonyManager != null) {
            return telephonyManager.getSubscriberId();
        }
        return null;
    }

    @SuppressLint({"HardwareIds"})
    public static String getPhoneStatus() {
        TelephonyManager telephonyManager = (TelephonyManager) Utils.getApp().getSystemService(AliyunLogCommon.TERMINAL_TYPE);
        return (((((((((((((("DeviceId(IMEI) = " + telephonyManager.getDeviceId() + "\n") + "DeviceSoftwareVersion = " + telephonyManager.getDeviceSoftwareVersion() + "\n") + "Line1Number = " + telephonyManager.getLine1Number() + "\n") + "NetworkCountryIso = " + telephonyManager.getNetworkCountryIso() + "\n") + "NetworkOperator = " + telephonyManager.getNetworkOperator() + "\n") + "NetworkOperatorName = " + telephonyManager.getNetworkOperatorName() + "\n") + "NetworkType = " + telephonyManager.getNetworkType() + "\n") + "PhoneType = " + telephonyManager.getPhoneType() + "\n") + "SimCountryIso = " + telephonyManager.getSimCountryIso() + "\n") + "SimOperator = " + telephonyManager.getSimOperator() + "\n") + "SimOperatorName = " + telephonyManager.getSimOperatorName() + "\n") + "SimSerialNumber = " + telephonyManager.getSimSerialNumber() + "\n") + "SimState = " + telephonyManager.getSimState() + "\n") + "SubscriberId(IMSI) = " + telephonyManager.getSubscriberId() + "\n") + "VoiceMailNumber = " + telephonyManager.getVoiceMailNumber() + "\n";
    }

    public static int getPhoneType() {
        TelephonyManager telephonyManager = (TelephonyManager) Utils.getApp().getSystemService(AliyunLogCommon.TERMINAL_TYPE);
        if (telephonyManager != null) {
            return telephonyManager.getPhoneType();
        }
        return -1;
    }

    public static String getSimOperatorByMnc() {
        TelephonyManager telephonyManager = (TelephonyManager) Utils.getApp().getSystemService(AliyunLogCommon.TERMINAL_TYPE);
        String simOperator = telephonyManager != null ? telephonyManager.getSimOperator() : null;
        if (simOperator == null) {
            return null;
        }
        switch (simOperator) {
            case "46000":
            case "46002":
            case "46007":
                return "中国移动";
            case "46001":
                return "中国联通";
            case "46003":
                return "中国电信";
            default:
                return simOperator;
        }
    }

    public static String getSimOperatorName() {
        TelephonyManager telephonyManager = (TelephonyManager) Utils.getApp().getSystemService(AliyunLogCommon.TERMINAL_TYPE);
        if (telephonyManager != null) {
            return telephonyManager.getSimOperatorName();
        }
        return null;
    }

    public static boolean isPhone() {
        TelephonyManager telephonyManager = (TelephonyManager) Utils.getApp().getSystemService(AliyunLogCommon.TERMINAL_TYPE);
        return (telephonyManager == null || telephonyManager.getPhoneType() == 0) ? false : true;
    }

    public static boolean isSimCardReady() {
        TelephonyManager telephonyManager = (TelephonyManager) Utils.getApp().getSystemService(AliyunLogCommon.TERMINAL_TYPE);
        return telephonyManager != null && telephonyManager.getSimState() == 5;
    }

    public static void sendSms(String str, String str2) {
        Utils.getApp().startActivity(IntentUtils.getSendSmsIntent(str, str2));
    }

    public static void sendSmsSilent(String str, String str2) {
        if (StringUtils.isEmpty(str2)) {
            return;
        }
        PendingIntent broadcast = PendingIntent.getBroadcast(Utils.getApp(), 0, new Intent(), 0);
        SmsManager smsManager = SmsManager.getDefault();
        if (str2.length() < 70) {
            smsManager.sendTextMessage(str, null, str2, broadcast, null);
            return;
        }
        Iterator<String> it = smsManager.divideMessage(str2).iterator();
        while (it.hasNext()) {
            smsManager.sendTextMessage(str, null, it.next(), broadcast, null);
        }
    }
}
