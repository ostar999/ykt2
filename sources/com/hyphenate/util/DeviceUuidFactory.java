package com.hyphenate.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import java.util.Map;
import java.util.UUID;

/* loaded from: classes4.dex */
public class DeviceUuidFactory {
    protected static final String PREFS_DEVICE_ID = "device_id";
    protected static final String PREFS_DEVICE_ID_BAK = "device_id_bak";
    protected static final String PREFS_DEVICE_ID_BAK_FILE = "prefs_device_id_bak";
    protected static final String PREFS_FILE = "device_id.xml";
    protected static UUID uuid;

    public DeviceUuidFactory(Context context) {
        if (uuid == null) {
            SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_FILE, 0);
            String string = sharedPreferences.getString("device_id", null);
            if (string != null) {
                uuid = UUID.fromString(string);
                return;
            }
            EMLog.e("DeviceUuidFactory", "Need to generate device uuid");
            uuid = generateDeviceUuid(context);
            sharedPreferences.edit().putString("device_id", uuid.toString()).commit();
        }
    }

    private UUID generateDeviceUuid(Context context) {
        return UUID.randomUUID();
    }

    public static String getBakDeviceId(Context context) {
        return context == null ? "" : context.getSharedPreferences(PREFS_DEVICE_ID_BAK_FILE, 0).getString(PREFS_DEVICE_ID_BAK, "");
    }

    private static boolean isEmpty(Object obj) {
        if (obj == null) {
            return true;
        }
        if ((obj instanceof String) && ((String) obj).trim().length() == 0) {
            return true;
        }
        if (obj instanceof Map) {
            return ((Map) obj).isEmpty();
        }
        return false;
    }

    public static void saveBakDeviceId(Context context, String str) {
        if (context == null || TextUtils.isEmpty(str)) {
            return;
        }
        EMLog.e("DeviceUuidFactory", "saveBakDeviceId");
        context.getSharedPreferences(PREFS_DEVICE_ID_BAK_FILE, 0).edit().putString(PREFS_DEVICE_ID_BAK, str).commit();
    }

    public UUID getDeviceUuid() {
        return uuid;
    }
}
