package com.tencent.liteav.basic.datareport;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.util.TXCBuild;
import com.tencent.liteav.basic.util.TXCCommonUtil;
import com.tencent.liteav.basic.util.h;
import java.security.MessageDigest;
import java.util.UUID;

/* loaded from: classes6.dex */
public class TXCDRApi {
    private static final String TAG = "TXCDRApi";
    private static String g_simulate_idfa = "";
    private static String mAppName = "";
    private static String mDevId = "";
    private static String mDevType = "";
    private static String mDevUUID = "";
    private static String mNetType = "";
    private static String mPackageName = "";
    private static String mSysVersion = "";
    private static final char[] DIGITS_LOWER = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    static boolean initRpt = false;

    static {
        h.d();
        nativeInitDataReport();
    }

    public static void InitEvent(Context context, String str, int i2, int i3, TXCDRExtInfo tXCDRExtInfo) {
        setCommonInfo(context);
        if (str == null) {
            return;
        }
        nativeInitEventInternal(str, i2, i3, tXCDRExtInfo);
    }

    private static String byteArrayToHexString(byte[] bArr) {
        char[] cArr = new char[bArr.length << 1];
        int i2 = 0;
        for (byte b3 : bArr) {
            int i3 = i2 + 1;
            char[] cArr2 = DIGITS_LOWER;
            cArr[i2] = cArr2[(b3 & 240) >>> 4];
            i2 = i3 + 1;
            cArr[i3] = cArr2[b3 & 15];
        }
        return new String(cArr);
    }

    public static String getDevUUID(Context context, String str) {
        return getSimulateIDFA(context);
    }

    /* JADX WARN: Removed duplicated region for block: B:32:0x0095  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x00a6  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x00ba A[LOOP:0: B:40:0x00b3->B:42:0x00ba, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:45:0x00e9 A[LOOP:1: B:44:0x00e7->B:45:0x00e9, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:53:0x017a A[Catch: Exception -> 0x01ae, TryCatch #1 {Exception -> 0x01ae, blocks: (B:51:0x015a, B:53:0x017a, B:54:0x017d, B:56:0x019b, B:57:0x019e), top: B:69:0x015a }] */
    /* JADX WARN: Removed duplicated region for block: B:56:0x019b A[Catch: Exception -> 0x01ae, TryCatch #1 {Exception -> 0x01ae, blocks: (B:51:0x015a, B:53:0x017a, B:54:0x017d, B:56:0x019b, B:57:0x019e), top: B:69:0x015a }] */
    /* JADX WARN: Removed duplicated region for block: B:64:0x01d0  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x015a A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String getSimulateIDFA(android.content.Context r23) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 477
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.liteav.basic.datareport.TXCDRApi.getSimulateIDFA(android.content.Context):java.lang.String");
    }

    public static int getStatusReportInterval() {
        return nativeGetStatusReportInterval();
    }

    public static void initCrashReport(Context context) {
        String sDKVersionStr;
        try {
            synchronized (TXCDRApi.class) {
                if (!initRpt && context != null && (sDKVersionStr = TXCCommonUtil.getSDKVersionStr()) != null) {
                    SharedPreferences.Editor editorEdit = context.getSharedPreferences("BuglySdkInfos", 0).edit();
                    editorEdit.putString("8e50744bf0", sDKVersionStr);
                    editorEdit.commit();
                    initRpt = true;
                }
            }
        } catch (Exception e2) {
            TXCLog.e(TAG, "init crash report failed.", e2);
        }
    }

    public static native int nativeGetStatusReportInterval();

    private static native void nativeInitDataReport();

    private static native void nativeInitEventInternal(String str, int i2, int i3, TXCDRExtInfo tXCDRExtInfo);

    public static native void nativeReportAVRoomEvent(int i2, long j2, String str, int i3, int i4, String str2, String str3);

    private static native void nativeReportDAUInterval(int i2, int i3, String str);

    public static native void nativeReportEvent(String str, int i2);

    public static native void nativeReportEvent40003(String str, int i2, int i3, String str2, String str3);

    public static native void nativeSetCommonValue(String str, String str2);

    private static native void nativeSetEventValueInterval(String str, int i2, String str2, String str3);

    private static native void nativeUninitDataReport();

    public static void reportAVRoomEvent(int i2, long j2, String str, int i3, int i4, String str2, String str3) {
        nativeReportAVRoomEvent(i2, j2, str, i3, i4, str2, str3);
    }

    public static void reportEvent40003(String str, int i2, int i3, String str2, String str3) {
        nativeReportEvent40003(str, i2, i3, str2, str3);
    }

    public static void setCommonInfo(Context context) {
        if (TextUtils.isEmpty(mDevType)) {
            mDevType = TXCBuild.Model();
        }
        mNetType = Integer.toString(h.e(context));
        if (TextUtils.isEmpty(mDevId)) {
            mDevId = getSimulateIDFA(context);
        }
        if (TextUtils.isEmpty(mDevUUID)) {
            mDevUUID = h.f(context);
        }
        if (TextUtils.isEmpty(mPackageName)) {
            mPackageName = h.c(context);
        }
        if (TextUtils.isEmpty(mAppName)) {
            mAppName = h.a(context, mPackageName) + ":" + mPackageName;
        }
        if (TextUtils.isEmpty(mSysVersion)) {
            mSysVersion = String.valueOf(TXCBuild.VersionInt());
        }
        txSetCommonInfo();
    }

    public static String string2Md5(String str) {
        String strByteArrayToHexString;
        if (str == null) {
            return "";
        }
        try {
            strByteArrayToHexString = byteArrayToHexString(MessageDigest.getInstance("MD5").digest(str.getBytes("UTF-8")));
        } catch (Exception e2) {
            TXCLog.e(TAG, "string2Md5 failed.", e2);
            strByteArrayToHexString = "";
        }
        return strByteArrayToHexString == null ? "" : strByteArrayToHexString;
    }

    public static String txCreateToken() {
        return UUID.randomUUID().toString();
    }

    public static void txReportDAU(Context context, int i2) {
        if (context != null) {
            setCommonInfo(context);
        }
        nativeReportDAUInterval(i2, 0, "");
    }

    public static void txSetAppVersion(String str) {
        if (str != null) {
            nativeSetCommonValue(a.f18385k, str);
        }
    }

    public static void txSetCommonInfo() {
        if (mDevType != null) {
            nativeSetCommonValue(a.f18380f, mDevType);
        }
        if (mNetType != null) {
            nativeSetCommonValue(a.f18381g, mNetType);
        }
        if (mDevId != null) {
            nativeSetCommonValue(a.f18382h, mDevId);
        }
        if (mDevUUID != null) {
            nativeSetCommonValue(a.f18383i, mDevUUID);
        }
        if (mAppName != null) {
            nativeSetCommonValue(a.f18384j, mAppName);
        }
        if (mSysVersion != null) {
            nativeSetCommonValue(a.f18386l, mSysVersion);
        }
    }

    public static void txSetEventIntValue(String str, int i2, String str2, long j2) {
        nativeSetEventValueInterval(str, i2, str2, "" + j2);
    }

    public static void txSetEventValue(String str, int i2, String str2, String str3) {
        nativeSetEventValueInterval(str, i2, str2, str3);
    }

    public static void txReportDAU(Context context, int i2, int i3, String str) {
        if (context != null) {
            setCommonInfo(context);
        }
        nativeReportDAUInterval(i2, i3, str);
    }
}
