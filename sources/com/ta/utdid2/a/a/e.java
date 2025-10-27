package com.ta.utdid2.a.a;

import android.content.Context;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.aliyun.vod.log.core.AliyunLogCommon;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import java.util.Random;

/* loaded from: classes6.dex */
public class e {
    public static String e(Context context) throws ClassNotFoundException {
        TelephonyManager telephonyManager;
        String strF = null;
        if (!c.c() && context != null) {
            try {
                if (com.ta.a.b.d.b(context) && (telephonyManager = (TelephonyManager) context.getSystemService(AliyunLogCommon.TERMINAL_TYPE)) != null) {
                    strF = telephonyManager.getDeviceId();
                }
            } catch (Exception unused) {
            }
        }
        if (f.b(strF)) {
            strF = m();
        }
        if (f.b(strF)) {
            strF = f(context);
        }
        return f.b(strF) ? l() : strF;
    }

    private static String f(Context context) {
        String string;
        String str = "";
        try {
            string = Settings.Secure.getString(context.getContentResolver(), SocializeProtocolConstants.PROTOCOL_KEY_ANDROID_ID);
        } catch (Throwable unused) {
        }
        try {
            if (!TextUtils.isEmpty(string) && !string.equalsIgnoreCase("a5f5faddde9e9f02") && !string.equalsIgnoreCase("8e17f7422b35fbea") && !string.equalsIgnoreCase("ece1e988e8bf71f2") && !string.equalsIgnoreCase("9e3ecdf2be3b9a69")) {
                if (!string.equalsIgnoreCase("0000000000000000")) {
                    return string;
                }
            }
            return "";
        } catch (Throwable unused2) {
            str = string;
            return str;
        }
    }

    public static String l() {
        int iCurrentTimeMillis = (int) (System.currentTimeMillis() / 1000);
        int iNanoTime = (int) System.nanoTime();
        int iNextInt = new Random().nextInt();
        int iNextInt2 = new Random().nextInt();
        byte[] bytes = d.getBytes(iCurrentTimeMillis);
        byte[] bytes2 = d.getBytes(iNanoTime);
        byte[] bytes3 = d.getBytes(iNextInt);
        byte[] bytes4 = d.getBytes(iNextInt2);
        byte[] bArr = new byte[16];
        System.arraycopy(bytes, 0, bArr, 0, 4);
        System.arraycopy(bytes2, 0, bArr, 4, 4);
        System.arraycopy(bytes3, 0, bArr, 8, 4);
        System.arraycopy(bytes4, 0, bArr, 12, 4);
        return b.encodeToString(bArr, 2);
    }

    private static String m() throws ClassNotFoundException {
        String str = g.get("ro.aliyun.clouduuid", "");
        if (TextUtils.isEmpty(str)) {
            str = g.get("ro.sys.aliyun.clouduuid", "");
        }
        return TextUtils.isEmpty(str) ? n() : str;
    }

    private static String n() {
        try {
            return (String) Class.forName("com.yunos.baseservice.clouduuid.CloudUUID").getMethod("getCloudUUID", new Class[0]).invoke(null, new Object[0]);
        } catch (Exception unused) {
            return "";
        }
    }
}
