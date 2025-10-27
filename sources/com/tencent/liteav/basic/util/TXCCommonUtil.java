package com.tencent.liteav.basic.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.wifi.WifiManager;
import cn.hutool.core.text.StrPool;
import com.tencent.liteav.basic.log.TXCLog;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import org.slf4j.Marker;

/* loaded from: classes6.dex */
public class TXCCommonUtil {
    private static final String TAG = "TXCCommonUtil";
    private static String mAppID = "";
    private static String mStrAppVersion = "";
    private static String mUserId = "";
    public static String pituLicencePath = "YTFaceSDK.licence";
    private static Context sApplicationContext;

    static {
        h.d();
    }

    public static boolean equals(Object obj, Object obj2) {
        return obj == obj2 || (obj != null && obj.equals(obj2));
    }

    public static Context getAppContext() {
        return sApplicationContext;
    }

    public static String getAppFilePath() {
        Context context = sApplicationContext;
        String absolutePath = context != null ? context.getFilesDir().getAbsolutePath() : "/sdcard/liteav";
        File file = new File(absolutePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        return absolutePath;
    }

    public static String getAppID() {
        return mAppID;
    }

    public static String getAppNameByStreamUrl(String str) {
        if (str != null && str.length() != 0) {
            int iIndexOf = str.indexOf("?");
            if (iIndexOf != -1) {
                str = str.substring(0, iIndexOf);
            }
            if (str != null && str.length() != 0) {
                int iLastIndexOf = str.lastIndexOf("/");
                if (iLastIndexOf != -1) {
                    str = str.substring(0, iLastIndexOf);
                }
                if (str != null && str.length() != 0) {
                    int iLastIndexOf2 = str.lastIndexOf("/");
                    if (iLastIndexOf2 != -1) {
                        str = str.substring(iLastIndexOf2 + 1);
                    }
                    if (str != null && str.length() != 0) {
                        return str;
                    }
                }
            }
        }
        return null;
    }

    public static String getAppPackageName() {
        return h.c(sApplicationContext);
    }

    public static String getAppVersion() {
        return mStrAppVersion;
    }

    public static ClassLoader getClassLoader() {
        return TXCCommonUtil.class.getClassLoader();
    }

    public static String getConfigCenterKey() {
        return nativeGetConfigCenterKey();
    }

    public static String getCurEnvProxyDomain() {
        return nativeGetCurEnvProxyDomain();
    }

    public static int getCurEnvProxySDKAppId() {
        return nativeGetCurEnvProxySDKAppId();
    }

    public static String getCurEnvServerConfigUrl() {
        return nativeGetCurEnvServerConfigUrl();
    }

    public static final String getDeviceInfo() {
        try {
            return TXCBuild.Brand() + StrPool.UNDERLINE + TXCBuild.Model() + StrPool.UNDERLINE + TXCBuild.Version();
        } catch (Exception unused) {
            return "unknown_device";
        }
    }

    public static String getFileExtension(String str) {
        int iLastIndexOf;
        if (str == null || str.length() <= 0 || (iLastIndexOf = str.lastIndexOf(46)) <= -1 || iLastIndexOf >= str.length() - 1) {
            return null;
        }
        return str.substring(iLastIndexOf + 1);
    }

    public static int getGateway() {
        Context context = sApplicationContext;
        if (context == null) {
            return 0;
        }
        try {
            return ((WifiManager) context.getSystemService("wifi")).getDhcpInfo().gateway;
        } catch (Exception e2) {
            TXCLog.e(TAG, "getGateway error ", e2);
            return 0;
        }
    }

    public static String getLogUploadPath() {
        File externalFilesDir;
        Context context = sApplicationContext;
        if (context == null || (externalFilesDir = context.getExternalFilesDir(null)) == null) {
            return "";
        }
        return externalFilesDir.getAbsolutePath() + "/log/liteav";
    }

    public static String getMD5(String str) throws NoSuchAlgorithmException {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(str.getBytes());
            byte[] bArrDigest = messageDigest.digest();
            StringBuffer stringBuffer = new StringBuffer("");
            for (int i2 = 0; i2 < bArrDigest.length; i2++) {
                int i3 = bArrDigest[i2];
                if (i3 < 0) {
                    i3 += 256;
                }
                if (i3 < 16) {
                    stringBuffer.append("0");
                }
                stringBuffer.append(Integer.toHexString(i3));
            }
            return stringBuffer.toString();
        } catch (Exception unused) {
            return str;
        }
    }

    public static int getSDKID() {
        return nativeGetSDKID();
    }

    public static int[] getSDKVersion() {
        String[] strArrSplit = nativeGetSDKVersion().split("\\.");
        int[] iArr = new int[strArrSplit.length];
        for (int i2 = 0; i2 < strArrSplit.length; i2++) {
            try {
                iArr[i2] = Integer.parseInt(strArrSplit[i2]);
            } catch (NumberFormatException e2) {
                TXCLog.e(TAG, "parse version failed.", e2);
                iArr[i2] = 0;
            }
        }
        return iArr;
    }

    public static String getSDKVersionStr() {
        return nativeGetSDKVersion();
    }

    public static String getStreamIDByStreamUrl(String str) {
        if (str != null && str.length() != 0) {
            int iIndexOf = str.indexOf("?");
            if (iIndexOf != -1) {
                str = str.substring(0, iIndexOf);
            }
            if (str != null && str.length() != 0) {
                int iLastIndexOf = str.lastIndexOf("/");
                if (iLastIndexOf != -1) {
                    str = str.substring(iLastIndexOf + 1);
                }
                if (str != null && str.length() != 0) {
                    int iIndexOf2 = str.indexOf(StrPool.DOT);
                    if (iIndexOf2 != -1) {
                        str = str.substring(0, iIndexOf2);
                    }
                    if (str != null && str.length() != 0) {
                        return str;
                    }
                }
            }
        }
        return null;
    }

    public static String getUserId() {
        return mUserId;
    }

    public static boolean isUrlEncoded(String str) {
        if (str == null) {
            return true;
        }
        try {
            return !str.replace(Marker.ANY_NON_NULL_MARKER, " ").equals(URLDecoder.decode(str, "UTF-8").replace(Marker.ANY_NON_NULL_MARKER, " "));
        } catch (Exception e2) {
            TXCLog.e(TAG, "isUrlEncoded error : ", e2);
            return false;
        }
    }

    public static String loadString(String str) {
        Context context = sApplicationContext;
        if (context == null) {
            return "";
        }
        try {
            return context.getSharedPreferences("TXCCommonConfig", 0).getString(str, "");
        } catch (Exception e2) {
            TXCLog.e(TAG, "load string failed.", e2);
            return "";
        }
    }

    public static long loadUInt64(String str) {
        Context context = sApplicationContext;
        if (context == null) {
            return 0L;
        }
        try {
            return context.getSharedPreferences("TXCCommonConfig", 0).getLong(str, 0L);
        } catch (Exception e2) {
            TXCLog.e(TAG, "load uint64 failed.", e2);
            return 0L;
        }
    }

    private static native String nativeGetConfigCenterKey();

    private static native String nativeGetCurEnvProxyDomain();

    private static native int nativeGetCurEnvProxySDKAppId();

    private static native String nativeGetCurEnvServerConfigUrl();

    private static native int nativeGetSDKID();

    private static native String nativeGetSDKVersion();

    private static native int nativeSetGlobalEnv(String str);

    private static native int nativeSetSocks5Proxy(String str, int i2, String str2, String str3);

    public static void saveString(String str, String str2) {
        Context context = sApplicationContext;
        if (context == null) {
            return;
        }
        try {
            SharedPreferences.Editor editorEdit = context.getSharedPreferences("TXCCommonConfig", 0).edit();
            editorEdit.putString(str, str2);
            editorEdit.commit();
        } catch (Exception e2) {
            TXCLog.e(TAG, "save string failed", e2);
        }
    }

    public static void saveUInt64(String str, long j2) {
        Context context = sApplicationContext;
        if (context == null) {
            return;
        }
        try {
            SharedPreferences.Editor editorEdit = context.getSharedPreferences("TXCCommonConfig", 0).edit();
            editorEdit.putLong(str, j2);
            editorEdit.commit();
        } catch (Exception e2) {
            TXCLog.e(TAG, "save uint64 failed.", e2);
        }
    }

    public static void setAppContext(Context context) {
        if (context == null) {
            return;
        }
        sApplicationContext = context.getApplicationContext();
    }

    public static void setAppID(String str) {
        mAppID = str;
    }

    public static void setAppVersion(String str) {
        mStrAppVersion = str;
    }

    public static boolean setExtID(String str, String str2) {
        TXCLog.i(TAG, "setExtID: [" + str + " : " + str2 + StrPool.BRACKET_END);
        if (!Arrays.asList("buildModel", "buildBrand", "buildManufacturer", "buildHardware", "buildVersion", "buildVersionInt", "buildBoard").contains(str)) {
            return false;
        }
        if (str.equals("buildModel")) {
            TXCBuild.SetModel(str2);
            return true;
        }
        if (str.equals("buildBrand")) {
            TXCBuild.SetBrand(str2);
            return true;
        }
        if (str.equals("buildManufacturer")) {
            TXCBuild.SetManufacturer(str2);
            return true;
        }
        if (str.equals("buildHardware")) {
            TXCBuild.SetHardware(str2);
            return true;
        }
        if (str.equals("buildVersion")) {
            TXCBuild.SetVersion(str2);
            return true;
        }
        if (str.equals("buildVersionInt")) {
            try {
                TXCBuild.SetVersionInt(Integer.parseInt(str2));
                return true;
            } catch (Exception unused) {
                return false;
            }
        }
        if (str.equals("buildBoard")) {
            TXCBuild.SetBoard(str2);
        }
        return true;
    }

    public static int setGlobalEnv(String str) {
        return nativeSetGlobalEnv(str);
    }

    public static void setPituLicencePath(String str) {
        pituLicencePath = str;
    }

    public static int setSocks5Proxy(String str, int i2, String str2, String str3) {
        return nativeSetSocks5Proxy(str, i2, str2, str3);
    }

    public static void setUserId(String str) {
        mUserId = str;
    }

    public static void sleep(int i2) throws InterruptedException {
        try {
            Thread.sleep(i2);
        } catch (InterruptedException unused) {
        }
    }

    public static String tryEncodeUrl(String str) throws UnsupportedEncodingException {
        try {
            if (isUrlEncoded(str)) {
                TXCLog.w(TAG, "URL has been encoded");
                return str;
            }
            byte[] bytes = str.getBytes("UTF-8");
            StringBuilder sb = new StringBuilder(bytes.length);
            for (int i2 = 0; i2 < bytes.length; i2++) {
                int i3 = bytes[i2];
                if (i3 < 0) {
                    i3 += 256;
                }
                if (i3 <= 32 || i3 >= 127 || i3 == 34 || i3 == 37 || i3 == 60 || i3 == 62 || i3 == 91 || i3 == 125 || i3 == 92 || i3 == 93 || i3 == 94 || i3 == 96 || i3 == 123 || i3 == 124) {
                    sb.append(String.format("%%%02X", Integer.valueOf(i3)));
                } else {
                    sb.append((char) i3);
                }
            }
            return sb.toString();
        } catch (Exception e2) {
            TXCLog.e(TAG, "tryEncodeUrl failed.", e2);
            return str;
        }
    }

    public static boolean unzip(String str, String str2) throws IOException {
        ZipInputStream zipInputStream;
        FileInputStream fileInputStream = null;
        try {
            FileInputStream fileInputStream2 = new FileInputStream(str);
            try {
                zipInputStream = new ZipInputStream(new BufferedInputStream(fileInputStream2));
                while (true) {
                    try {
                        ZipEntry nextEntry = zipInputStream.getNextEntry();
                        if (nextEntry == null) {
                            d.a(fileInputStream2);
                            d.a(zipInputStream);
                            return true;
                        }
                        File file = new File(str2, nextEntry.getName());
                        if (nextEntry.isDirectory()) {
                            file.mkdirs();
                        } else {
                            file.getParentFile().mkdirs();
                            d.a(zipInputStream, file.getAbsolutePath());
                            zipInputStream.closeEntry();
                        }
                    } catch (Throwable th) {
                        th = th;
                        fileInputStream = fileInputStream2;
                        try {
                            TXCLog.e(TAG, "unzip " + str + " failed.", th);
                            d.a(fileInputStream);
                            d.a(zipInputStream);
                            return false;
                        } catch (Throwable th2) {
                            d.a(fileInputStream);
                            d.a(zipInputStream);
                            throw th2;
                        }
                    }
                }
            } catch (Throwable th3) {
                th = th3;
                zipInputStream = null;
            }
        } catch (Throwable th4) {
            th = th4;
            zipInputStream = null;
        }
    }

    /* JADX WARN: Can't wrap try/catch for region: R(9:7|71|8|(9:75|13|68|14|(3:15|(1:17)(0)|56)|28|29|77|76)|66|21|78|76|5) */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r10v11, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r10v12 */
    /* JADX WARN: Type inference failed for: r10v13 */
    /* JADX WARN: Type inference failed for: r10v15 */
    /* JADX WARN: Type inference failed for: r10v20 */
    /* JADX WARN: Type inference failed for: r10v8, types: [java.io.InputStream] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void zip(java.util.ArrayList<java.lang.String> r9, java.lang.String r10) throws java.lang.Throwable {
        /*
            java.lang.String r0 = "TXCCommonUtil"
            java.io.File r1 = new java.io.File
            r1.<init>(r10)
            r10 = 0
            java.util.zip.ZipOutputStream r2 = new java.util.zip.ZipOutputStream     // Catch: java.lang.Throwable -> L89 java.io.FileNotFoundException -> L8b
            java.io.FileOutputStream r3 = new java.io.FileOutputStream     // Catch: java.lang.Throwable -> L89 java.io.FileNotFoundException -> L8b
            r3.<init>(r1)     // Catch: java.lang.Throwable -> L89 java.io.FileNotFoundException -> L8b
            r2.<init>(r3)     // Catch: java.lang.Throwable -> L89 java.io.FileNotFoundException -> L8b
            java.lang.String r1 = "LiteAV log"
            r2.setComment(r1)     // Catch: java.lang.Throwable -> L84 java.io.FileNotFoundException -> L87
            java.util.Iterator r9 = r9.iterator()     // Catch: java.lang.Throwable -> L84 java.io.FileNotFoundException -> L87
        L1b:
            boolean r1 = r9.hasNext()     // Catch: java.lang.Throwable -> L84 java.io.FileNotFoundException -> L87
            if (r1 == 0) goto L80
            java.lang.Object r1 = r9.next()     // Catch: java.lang.Throwable -> L84 java.io.FileNotFoundException -> L87
            java.lang.String r1 = (java.lang.String) r1     // Catch: java.lang.Throwable -> L84 java.io.FileNotFoundException -> L87
            java.io.File r3 = new java.io.File     // Catch: java.lang.Throwable -> L84 java.io.FileNotFoundException -> L87
            r3.<init>(r1)     // Catch: java.lang.Throwable -> L84 java.io.FileNotFoundException -> L87
            long r4 = r3.length()     // Catch: java.lang.Throwable -> L69 java.lang.Exception -> L6b
            r6 = 0
            int r1 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r1 == 0) goto L65
            long r4 = r3.length()     // Catch: java.lang.Throwable -> L69 java.lang.Exception -> L6b
            r6 = 8388608(0x800000, double:4.144523E-317)
            int r1 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r1 <= 0) goto L42
            goto L65
        L42:
            java.io.FileInputStream r1 = new java.io.FileInputStream     // Catch: java.lang.Throwable -> L69 java.lang.Exception -> L6b
            r1.<init>(r3)     // Catch: java.lang.Throwable -> L69 java.lang.Exception -> L6b
            java.util.zip.ZipEntry r10 = new java.util.zip.ZipEntry     // Catch: java.lang.Exception -> L63 java.lang.Throwable -> L7a
            java.lang.String r3 = r3.getName()     // Catch: java.lang.Exception -> L63 java.lang.Throwable -> L7a
            r10.<init>(r3)     // Catch: java.lang.Exception -> L63 java.lang.Throwable -> L7a
            r2.putNextEntry(r10)     // Catch: java.lang.Exception -> L63 java.lang.Throwable -> L7a
            r10 = 8192(0x2000, float:1.148E-41)
            byte[] r10 = new byte[r10]     // Catch: java.lang.Exception -> L63 java.lang.Throwable -> L7a
        L57:
            int r3 = r1.read(r10)     // Catch: java.lang.Exception -> L63 java.lang.Throwable -> L7a
            r4 = -1
            if (r3 == r4) goto L75
            r4 = 0
            r2.write(r10, r4, r3)     // Catch: java.lang.Exception -> L63 java.lang.Throwable -> L7a
            goto L57
        L63:
            r10 = move-exception
            goto L6f
        L65:
            r10.close()     // Catch: java.lang.Exception -> L1b java.lang.Throwable -> L84
            goto L1b
        L69:
            r9 = move-exception
            goto L7c
        L6b:
            r1 = move-exception
            r8 = r1
            r1 = r10
            r10 = r8
        L6f:
            java.lang.String r3 = "zip failed."
            com.tencent.liteav.basic.log.TXCLog.e(r0, r3, r10)     // Catch: java.lang.Throwable -> L7a
        L75:
            r1.close()     // Catch: java.lang.Exception -> L78 java.lang.Throwable -> L84
        L78:
            r10 = r1
            goto L1b
        L7a:
            r9 = move-exception
            r10 = r1
        L7c:
            r10.close()     // Catch: java.lang.Exception -> L7f java.lang.Throwable -> L84
        L7f:
            throw r9     // Catch: java.lang.Throwable -> L84 java.io.FileNotFoundException -> L87
        L80:
            r2.close()     // Catch: java.lang.Exception -> L94
            goto L94
        L84:
            r9 = move-exception
            r10 = r2
            goto L95
        L87:
            r10 = r2
            goto L8b
        L89:
            r9 = move-exception
            goto L95
        L8b:
            java.lang.String r9 = "zip log error"
            com.tencent.liteav.basic.log.TXCLog.w(r0, r9)     // Catch: java.lang.Throwable -> L89
            r10.close()     // Catch: java.lang.Exception -> L94
        L94:
            return
        L95:
            r10.close()     // Catch: java.lang.Exception -> L98
        L98:
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.liteav.basic.util.TXCCommonUtil.zip(java.util.ArrayList, java.lang.String):void");
    }

    public static byte[] getMD5(byte[] bArr) throws NoSuchAlgorithmException {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(bArr);
            return messageDigest.digest();
        } catch (Exception unused) {
            return null;
        }
    }
}
