package com.tencent.open.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.DisplayMetrics;
import androidx.core.content.FileProvider;
import com.hjq.permissions.Permission;
import com.just.agentweb.DefaultWebClient;
import com.tencent.connect.common.Constants;
import com.tencent.open.log.SLog;
import com.tencent.smtt.sdk.TbsConfig;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes6.dex */
public class k {

    /* renamed from: a, reason: collision with root package name */
    private static String f20688a = "";

    /* renamed from: b, reason: collision with root package name */
    private static String f20689b = "";

    /* renamed from: c, reason: collision with root package name */
    private static String f20690c = "";

    /* renamed from: d, reason: collision with root package name */
    private static String f20691d = "";

    /* renamed from: e, reason: collision with root package name */
    private static int f20692e = -1;

    /* renamed from: f, reason: collision with root package name */
    private static String f20693f = "0123456789ABCDEF";

    private static char a(int i2) {
        int i3 = i2 & 15;
        return (char) (i3 < 10 ? i3 + 48 : (i3 - 10) + 97);
    }

    public static Bundle a(String str) {
        Bundle bundle = new Bundle();
        if (str == null) {
            return bundle;
        }
        try {
            for (String str2 : str.split("&")) {
                String[] strArrSplit = str2.split("=");
                if (strArrSplit.length == 2) {
                    bundle.putString(URLDecoder.decode(strArrSplit[0]), URLDecoder.decode(strArrSplit[1]));
                }
            }
            return bundle;
        } catch (Exception unused) {
            return null;
        }
    }

    public static Bundle b(String str) {
        try {
            URL url = new URL(str.replace("auth://", DefaultWebClient.HTTP_SCHEME));
            Bundle bundleA = a(url.getQuery());
            bundleA.putAll(a(url.getRef()));
            return bundleA;
        } catch (MalformedURLException unused) {
            return new Bundle();
        }
    }

    public static JSONObject c(String str) throws JSONException {
        try {
            URL url = new URL(str.replace("auth://", DefaultWebClient.HTTP_SCHEME));
            JSONObject jSONObjectA = a((JSONObject) null, url.getQuery());
            a(jSONObjectA, url.getRef());
            return jSONObjectA;
        } catch (MalformedURLException unused) {
            return new JSONObject();
        }
    }

    public static JSONObject d(String str) throws JSONException {
        if (str.equals(k.a.f27524v)) {
            str = "{value : false}";
        }
        if (str.equals(k.a.f27523u)) {
            str = "{value : true}";
        }
        if (str.contains("allback(")) {
            str = str.replaceFirst("[\\s\\S]*allback\\(([\\s\\S]*)\\);[^\\)]*\\z", "$1").trim();
        }
        if (str.contains("online[0]=")) {
            str = "{online:" + str.charAt(str.length() - 2) + "}";
        }
        return new JSONObject(str);
    }

    public static boolean e(String str) {
        return str == null || str.length() == 0;
    }

    public static String f(String str) {
        try {
            return URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e2) {
            SLog.e("openSDK_LOG.Util", "urlEncode: UnsupportedEncodingException", e2);
            return "";
        }
    }

    private static boolean g(Context context) throws PackageManager.NameNotFoundException, NoSuchAlgorithmException {
        Signature[] signatureArr;
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(TbsConfig.APP_QB, 64);
            String str = packageInfo.versionName;
            if (i.a(str, "4.3") >= 0 && !str.startsWith("4.4") && (signatureArr = packageInfo.signatures) != null) {
                try {
                    MessageDigest messageDigest = MessageDigest.getInstance("MD5");
                    messageDigest.update(signatureArr[0].toByteArray());
                    String strA = a(messageDigest.digest());
                    messageDigest.reset();
                    if (strA.equals("d8391a394d4a179e6fe7bdb8a301258b")) {
                        return true;
                    }
                } catch (NoSuchAlgorithmException e2) {
                    SLog.e("openSDK_LOG.Util", "isQQBrowerAvailable has exception: " + e2.getMessage());
                }
            }
        } catch (PackageManager.NameNotFoundException unused) {
        }
        return false;
    }

    public static final boolean h(String str) {
        if (str == null) {
            return false;
        }
        return str.startsWith(DefaultWebClient.HTTP_SCHEME) || str.startsWith(DefaultWebClient.HTTPS_SCHEME);
    }

    public static boolean i(String str) {
        return str != null && new File(str).exists();
    }

    public static byte[] j(String str) {
        try {
            return str.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e2) {
            SLog.e("openSDK_LOG.Util", "getBytesUTF8: UnsupportedEncodingException", e2);
            return new byte[0];
        }
    }

    public static String k(String str) {
        if (str == null) {
            return null;
        }
        return Base64.encodeToString(a(str.getBytes(), "JCPTZXEZ"), 3);
    }

    public static String l(String str) {
        return a(str, 2);
    }

    public static File m(String str) throws IOException {
        File file = new File(str);
        if (!file.exists()) {
            if (file.getParentFile() == null || file.getParentFile().exists() || file.getParentFile().mkdirs()) {
                file.createNewFile();
            } else {
                SLog.d("openSDK_LOG.Util", "createFile failed" + str);
            }
        }
        return file;
    }

    public static boolean n(String str) {
        String strB = b();
        return (TextUtils.isEmpty(str) || TextUtils.isEmpty(strB) || !str.contains(strB)) ? false : true;
    }

    public static String e(Context context, String str) throws PackageManager.NameNotFoundException {
        if (context == null) {
            return "";
        }
        String strD = d(context, str);
        f20690c = strD;
        return strD;
    }

    public static File h(Context context, String str) {
        File[] externalFilesDirs;
        if (context == null || (externalFilesDirs = context.getExternalFilesDirs(str)) == null || externalFilesDirs.length <= 0) {
            return null;
        }
        return externalFilesDirs[0];
    }

    public static boolean e(Context context) {
        return i.c(context, "8.1.8") >= 0;
    }

    public static boolean f(Context context, String str) {
        boolean z2 = !c(context) || i.a(context, Constants.PACKAGE_QQ_PAD) == null;
        if (z2 && i.a(context, Constants.PACKAGE_TIM) != null) {
            z2 = false;
        }
        if (z2 && i.a(context, Constants.PACKAGE_QQ_SPEED) != null) {
            z2 = false;
        }
        if (z2) {
            return i.c(context, str) < 0;
        }
        return z2;
    }

    public static boolean b(Context context) {
        ConnectivityManager connectivityManager;
        if (context == null || context.checkSelfPermission("android.permission.ACCESS_NETWORK_STATE") != 0 || (connectivityManager = (ConnectivityManager) context.getSystemService("connectivity")) == null) {
            return true;
        }
        NetworkInfo[] allNetworkInfo = connectivityManager.getAllNetworkInfo();
        if (allNetworkInfo.length == 0) {
            return true;
        }
        for (NetworkInfo networkInfo : allNetworkInfo) {
            if (networkInfo.isConnectedOrConnecting()) {
                return true;
            }
        }
        return false;
    }

    public static String c(Context context, String str) throws PackageManager.NameNotFoundException {
        if (context == null) {
            return "";
        }
        b(context, str);
        return f20689b;
    }

    public static JSONObject a(JSONObject jSONObject, String str) throws JSONException {
        if (jSONObject == null) {
            jSONObject = new JSONObject();
        }
        if (str != null) {
            for (String str2 : str.split("&")) {
                String[] strArrSplit = str2.split("=");
                if (strArrSplit.length == 2) {
                    try {
                        strArrSplit[0] = URLDecoder.decode(strArrSplit[0]);
                        strArrSplit[1] = URLDecoder.decode(strArrSplit[1]);
                    } catch (Exception unused) {
                    }
                    try {
                        jSONObject.put(strArrSplit[0], strArrSplit[1]);
                    } catch (JSONException e2) {
                        SLog.e("openSDK_LOG.Util", "decodeUrlToJson has exception: " + e2.getMessage());
                    }
                }
            }
        }
        return jSONObject;
    }

    public static boolean f(Context context) {
        return i.c(context, "5.9.5") >= 0 || i.a(context, Constants.PACKAGE_QQ_SPEED) != null;
    }

    public static boolean c(Context context) {
        double dSqrt;
        try {
            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            dSqrt = Math.sqrt(Math.pow(displayMetrics.widthPixels / displayMetrics.xdpi, 2.0d) + Math.pow(displayMetrics.heightPixels / displayMetrics.ydpi, 2.0d));
        } catch (Throwable unused) {
            dSqrt = 0.0d;
        }
        return dSqrt > 6.5d;
    }

    public static String d(Context context, String str) throws PackageManager.NameNotFoundException {
        if (context == null) {
            return "";
        }
        b(context, str);
        return f20688a;
    }

    public static boolean d(Context context) {
        return i.c(context, "8.1.5") >= 0;
    }

    public static void b(Context context, String str) throws PackageManager.NameNotFoundException {
        if (context == null) {
            return;
        }
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(str, 0);
            String str2 = packageInfo.versionName;
            f20689b = str2;
            f20688a = str2.substring(0, str2.lastIndexOf(46));
            String str3 = f20689b;
            f20691d = str3.substring(str3.lastIndexOf(46) + 1, f20689b.length());
            f20692e = packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e2) {
            SLog.e("openSDK_LOG.Util", "getPackageInfo has exception: " + e2.getMessage());
        } catch (Exception e3) {
            SLog.e("openSDK_LOG.Util", "getPackageInfo has exception: " + e3.getMessage());
        }
    }

    public static String g(String str) throws NoSuchAlgorithmException {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(j(str));
            byte[] bArrDigest = messageDigest.digest();
            if (bArrDigest == null) {
                return str;
            }
            StringBuilder sb = new StringBuilder();
            for (byte b3 : bArrDigest) {
                sb.append(a(b3 >>> 4));
                sb.append(a(b3));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e2) {
            SLog.e("openSDK_LOG.Util", "encrypt has exception: " + e2.getMessage());
            return str;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:125:0x01c7 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:127:0x01e4 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:129:0x0201 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:143:? A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String c(android.content.Context r12, android.net.Uri r13) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 541
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.open.utils.k.c(android.content.Context, android.net.Uri):java.lang.String");
    }

    public static boolean a(Context context, String str) {
        boolean zG;
        try {
            zG = g(context);
        } catch (Exception unused) {
            zG = false;
        }
        try {
            if (zG) {
                a(context, TbsConfig.APP_QB, "com.tencent.mtt.MainActivity", str);
            } else {
                a(context, "com.android.browser", "com.android.browser.BrowserActivity", str);
            }
            return true;
        } catch (Exception unused2) {
            if (zG) {
                try {
                    try {
                        try {
                            a(context, "com.android.browser", "com.android.browser.BrowserActivity", str);
                            return true;
                        } catch (Exception unused3) {
                            a(context, "com.google.android.browser", "com.android.browser.BrowserActivity", str);
                            return true;
                        }
                    } catch (Exception unused4) {
                        a(context, "com.android.chrome", "com.google.android.apps.chrome.Main", str);
                        return true;
                    }
                } catch (Exception unused5) {
                    return false;
                }
            }
            try {
                try {
                    a(context, "com.google.android.browser", "com.android.browser.BrowserActivity", str);
                    return true;
                } catch (Exception unused6) {
                    a(context, "com.android.chrome", "com.google.android.apps.chrome.Main", str);
                    return true;
                }
            } catch (Exception unused7) {
                return false;
            }
        }
    }

    @SuppressLint({"NewApi"})
    public static String b(Context context, Uri uri) {
        Uri uri2;
        if (uri == null) {
            return null;
        }
        if (DocumentsContract.isDocumentUri(context, uri)) {
            String authority = uri.getAuthority();
            if ("com.android.externalstorage.documents".equals(authority)) {
                String[] strArrSplit = DocumentsContract.getDocumentId(uri).split(":");
                String str = strArrSplit[0];
                if ("primary".equals(str)) {
                    return Environment.getExternalStorageDirectory().getAbsolutePath().concat("/").concat(strArrSplit[1]);
                }
                return "/storage/".concat(str).concat("/").concat(strArrSplit[1]);
            }
            if ("com.android.providers.downloads.documents".equals(authority)) {
                String documentId = DocumentsContract.getDocumentId(uri);
                if (documentId.startsWith("raw:")) {
                    return documentId.replaceFirst("raw:", "");
                }
                return c(context, ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.parseLong(documentId)));
            }
            if ("com.android.providers.media.documents".equals(authority)) {
                String[] strArrSplit2 = DocumentsContract.getDocumentId(uri).split(":");
                String str2 = strArrSplit2[0];
                if ("image".equals(str2)) {
                    uri2 = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(str2)) {
                    uri2 = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(str2)) {
                    uri2 = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                return c(context, ContentUris.withAppendedId(uri2, Long.parseLong(strArrSplit2[1])));
            }
            return null;
        }
        String scheme = uri.getScheme();
        if ("content".equals(scheme)) {
            return c(context, uri);
        }
        if ("file".equals(scheme)) {
            return uri.getPath();
        }
        return null;
    }

    public static boolean g(Context context, String str) {
        boolean z2 = !c(context) || i.a(context, Constants.PACKAGE_QQ_PAD) == null;
        if (z2 && i.a(context, Constants.PACKAGE_QQ_SPEED) != null) {
            z2 = false;
        }
        if (z2) {
            return i.c(context, str) < 0;
        }
        return z2;
    }

    private static void a(Context context, String str, String str2, String str3) {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(str, str2));
        intent.setAction("android.intent.action.VIEW");
        intent.addFlags(1073741824);
        intent.addFlags(268435456);
        intent.setData(Uri.parse(str3));
        context.startActivity(intent);
    }

    public static boolean a() {
        return (Environment.getExternalStorageState().equals("mounted") ? Environment.getExternalStorageDirectory() : null) != null;
    }

    public static String a(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder(bArr.length * 2);
        for (byte b3 : bArr) {
            String string = Integer.toString(b3 & 255, 16);
            if (string.length() == 1) {
                string = "0" + string;
            }
            sb.append(string);
        }
        return sb.toString();
    }

    public static final String a(Context context) {
        CharSequence applicationLabel;
        if (context == null || (applicationLabel = context.getPackageManager().getApplicationLabel(context.getApplicationInfo())) == null) {
            return null;
        }
        return applicationLabel.toString();
    }

    public static final String a(String str, int i2, String str2, String str3) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        if (TextUtils.isEmpty(str2)) {
            str2 = "UTF-8";
        }
        try {
            if (str.getBytes(str2).length <= i2) {
                return str;
            }
            int i3 = 0;
            int length = 0;
            while (i3 < str.length()) {
                int i4 = i3 + 1;
                length += str.substring(i3, i4).getBytes(str2).length;
                if (length > i2) {
                    String strSubstring = str.substring(0, i3);
                    if (TextUtils.isEmpty(str3)) {
                        return strSubstring;
                    }
                    return strSubstring + str3;
                }
                i3 = i4;
            }
            return str;
        } catch (Exception e2) {
            SLog.e("openSDK_LOG.Util", "Util.subString has exception: " + e2.getMessage());
            return str;
        }
    }

    public static String b() {
        File fileE = f.e();
        if (fileE == null) {
            return null;
        }
        if (!fileE.exists()) {
            fileE.mkdirs();
        }
        return fileE.toString();
    }

    public static Bundle a(String str, String str2, String str3, String str4, String str5, String str6) {
        return a(str, str3, str4, str2, str5, str6, "", "", "", "", "", "");
    }

    public static Bundle a(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, String str11, String str12) {
        Bundle bundle = new Bundle();
        bundle.putString("openid", str);
        bundle.putString("report_type", str2);
        bundle.putString("act_type", str3);
        bundle.putString(SocializeProtocolConstants.PROTOCOL_KEY_VERIFY_MEDIA, str4);
        bundle.putString("app_id", str5);
        bundle.putString("result", str6);
        bundle.putString("type", str7);
        bundle.putString("login_status", str8);
        bundle.putString("need_user_auth", str9);
        bundle.putString("to_uin", str10);
        bundle.putString("call_source", str11);
        bundle.putString("to_type", str12);
        bundle.putString("platform", "1");
        return bundle;
    }

    public static boolean c() {
        Context contextA = f.a();
        return contextA != null && contextA.getPackageManager().checkPermission(Permission.WRITE_EXTERNAL_STORAGE, contextA.getPackageName()) == 0;
    }

    public static boolean a(Context context, boolean z2) {
        return (c(context) && i.a(context, Constants.PACKAGE_QQ_PAD) != null) || i.c(context, "4.1") >= 0 || i.a(context, Constants.PACKAGE_TIM) != null || i.a(context, Constants.PACKAGE_QQ_SPEED) != null;
    }

    public static long a(Context context, Uri uri) {
        Cursor cursorQuery = context.getContentResolver().query(uri, new String[]{"_size"}, null, null, null);
        if (cursorQuery != null) {
            try {
            } catch (Exception e2) {
                SLog.e("openSDK_LOG.Util", "cursor exception", e2);
            }
            if (cursorQuery.getCount() != 0) {
                try {
                    try {
                        j = cursorQuery.moveToFirst() ? cursorQuery.getLong(cursorQuery.getColumnIndexOrThrow("_size")) : 0L;
                        cursorQuery.close();
                    } catch (Exception e3) {
                        SLog.e("openSDK_LOG.Util", "cursor exception", e3);
                        cursorQuery.close();
                    }
                    return j;
                } catch (Throwable th) {
                    try {
                        cursorQuery.close();
                    } catch (Exception e4) {
                        SLog.e("openSDK_LOG.Util", "cursor exception", e4);
                    }
                    throw th;
                }
            }
        }
        return 0L;
    }

    private static byte[] a(byte[] bArr, String str) {
        if (bArr != null) {
            try {
                char[] charArray = str.toCharArray();
                int length = bArr.length;
                byte[] bArr2 = new byte[length];
                for (int i2 = 0; i2 < length; i2++) {
                    bArr2[i2] = (byte) (bArr[i2] ^ charArray[i2 % charArray.length]);
                }
                return bArr2;
            } catch (Throwable th) {
                SLog.e("Util", "xor Exception! ", th);
            }
        }
        return bArr;
    }

    public static String a(String str, int i2) {
        if (!TextUtils.isEmpty(str)) {
            try {
                return Base64.encodeToString(str.getBytes("UTF-8"), i2);
            } catch (UnsupportedEncodingException e2) {
                SLog.e("openSDK_LOG.Util", "convert2Base64String exception: " + e2.getMessage());
            }
        }
        return "";
    }

    public static Drawable a(String str, Context context) throws Throwable {
        InputStream inputStreamOpen;
        StringBuilder sb;
        InputStream inputStream = null;
        drawableCreateFromStream = null;
        Drawable drawableCreateFromStream = null;
        if (context == null) {
            SLog.e("openSDK_LOG.Util", "context null!");
            return null;
        }
        try {
            inputStreamOpen = context.getAssets().open(str);
        } catch (IOException e2) {
            e = e2;
            inputStreamOpen = null;
        } catch (Throwable th) {
            th = th;
            try {
                inputStream.close();
            } catch (Exception e3) {
                SLog.e("openSDK_LOG.Util", "inputStream close exception: " + e3.getMessage());
            }
            throw th;
        }
        try {
            try {
                drawableCreateFromStream = Drawable.createFromStream(inputStreamOpen, str);
            } catch (Throwable th2) {
                th = th2;
                inputStream = inputStreamOpen;
                inputStream.close();
                throw th;
            }
        } catch (IOException e4) {
            e = e4;
            SLog.e("openSDK_LOG.Util", "getDrawable exception: " + e.getMessage());
            try {
                inputStreamOpen.close();
            } catch (Exception e5) {
                e = e5;
                sb = new StringBuilder();
                sb.append("inputStream close exception: ");
                sb.append(e.getMessage());
                SLog.e("openSDK_LOG.Util", sb.toString());
                return drawableCreateFromStream;
            }
            return drawableCreateFromStream;
        }
        try {
            inputStreamOpen.close();
        } catch (Exception e6) {
            e = e6;
            sb = new StringBuilder();
            sb.append("inputStream close exception: ");
            sb.append(e.getMessage());
            SLog.e("openSDK_LOG.Util", sb.toString());
            return drawableCreateFromStream;
        }
        return drawableCreateFromStream;
    }

    public static boolean a(String str, String str2) {
        File file = new File(str);
        if (file.exists()) {
            try {
                return a(file, m(str2));
            } catch (IOException e2) {
                SLog.d("openSDK_LOG.Util", "copy fail from " + str + " to " + str2 + " ", e2);
            }
        }
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r7v0, types: [java.io.File] */
    /* JADX WARN: Type inference failed for: r7v1 */
    /* JADX WARN: Type inference failed for: r7v10 */
    /* JADX WARN: Type inference failed for: r7v11, types: [java.io.BufferedInputStream] */
    /* JADX WARN: Type inference failed for: r7v12 */
    /* JADX WARN: Type inference failed for: r7v13 */
    /* JADX WARN: Type inference failed for: r7v14 */
    /* JADX WARN: Type inference failed for: r7v15 */
    /* JADX WARN: Type inference failed for: r7v16 */
    /* JADX WARN: Type inference failed for: r7v17 */
    /* JADX WARN: Type inference failed for: r7v18, types: [java.io.BufferedInputStream, java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r7v19 */
    /* JADX WARN: Type inference failed for: r7v2, types: [java.io.BufferedInputStream] */
    /* JADX WARN: Type inference failed for: r7v20 */
    /* JADX WARN: Type inference failed for: r7v21 */
    /* JADX WARN: Type inference failed for: r7v22 */
    /* JADX WARN: Type inference failed for: r7v4 */
    /* JADX WARN: Type inference failed for: r7v5 */
    /* JADX WARN: Type inference failed for: r7v6 */
    /* JADX WARN: Type inference failed for: r7v7 */
    /* JADX WARN: Type inference failed for: r7v8, types: [java.io.BufferedInputStream] */
    /* JADX WARN: Type inference failed for: r7v9 */
    public static boolean a(File file, File file2) throws Throwable {
        FileOutputStream fileOutputStream = null;
        try {
            try {
                try {
                    if (file2.exists()) {
                        file2.delete();
                    }
                    if (file2.getParentFile() != null && !file2.getParentFile().exists()) {
                        file2.getParentFile().mkdirs();
                    }
                    FileOutputStream fileOutputStream2 = new FileOutputStream((File) file2);
                    try {
                        file2 = new BufferedInputStream(new FileInputStream(file));
                        try {
                            byte[] bArr = new byte[102400];
                            while (true) {
                                int i2 = file2.read(bArr);
                                if (i2 != -1) {
                                    fileOutputStream2.write(bArr, 0, i2);
                                    fileOutputStream2.flush();
                                } else {
                                    try {
                                        break;
                                    } catch (IOException e2) {
                                        SLog.e("openSDK_LOG.Util", "copyFile error, ", e2);
                                    }
                                }
                            }
                            fileOutputStream2.close();
                            try {
                                file2.close();
                            } catch (IOException e3) {
                                SLog.e("openSDK_LOG.Util", "copyFile error, ", e3);
                            }
                            return true;
                        } catch (IOException e4) {
                            e = e4;
                            fileOutputStream = fileOutputStream2;
                            file2 = file2;
                            SLog.e("openSDK_LOG.Util", "copyFile error, ", e);
                            if (fileOutputStream != null) {
                                try {
                                    fileOutputStream.close();
                                } catch (IOException e5) {
                                    SLog.e("openSDK_LOG.Util", "copyFile error, ", e5);
                                }
                            }
                            if (file2 != 0) {
                                file2.close();
                                file2 = file2;
                            }
                            return false;
                        } catch (OutOfMemoryError e6) {
                            e = e6;
                            fileOutputStream = fileOutputStream2;
                            file2 = file2;
                            SLog.e("openSDK_LOG.Util", "copyFile error, ", e);
                            if (fileOutputStream != null) {
                                try {
                                    fileOutputStream.close();
                                } catch (IOException e7) {
                                    SLog.e("openSDK_LOG.Util", "copyFile error, ", e7);
                                }
                            }
                            if (file2 != 0) {
                                file2.close();
                                file2 = file2;
                            }
                            return false;
                        } catch (Throwable th) {
                            th = th;
                            fileOutputStream = fileOutputStream2;
                            if (fileOutputStream != null) {
                                try {
                                    fileOutputStream.close();
                                } catch (IOException e8) {
                                    SLog.e("openSDK_LOG.Util", "copyFile error, ", e8);
                                }
                            }
                            if (file2 != 0) {
                                try {
                                    file2.close();
                                    throw th;
                                } catch (IOException e9) {
                                    SLog.e("openSDK_LOG.Util", "copyFile error, ", e9);
                                    throw th;
                                }
                            }
                            throw th;
                        }
                    } catch (IOException e10) {
                        e = e10;
                        file2 = 0;
                    } catch (OutOfMemoryError e11) {
                        e = e11;
                        file2 = 0;
                    } catch (Throwable th2) {
                        th = th2;
                        file2 = 0;
                    }
                } catch (IOException e12) {
                    e = e12;
                    file2 = 0;
                } catch (OutOfMemoryError e13) {
                    e = e13;
                    file2 = 0;
                } catch (Throwable th3) {
                    th = th3;
                    file2 = 0;
                }
            } catch (IOException e14) {
                SLog.e("openSDK_LOG.Util", "copyFile error, ", e14);
                return false;
            }
        } catch (Throwable th4) {
            th = th4;
        }
    }

    public static boolean a(Context context, String str, String str2) {
        boolean zA = a(str, str2);
        SLog.i("openSDK_LOG.Util", "copyFileByCheckPermission() copy success:" + zA);
        return zA;
    }

    public static String a(String str, Activity activity, String str2, IUiListener iUiListener) {
        String str3;
        try {
            boolean zN = n(str2);
            SLog.i("openSDK_LOG.Util", "doPublishMood() check file: isAppSpecificDir=" + zN + ",hasSDPermission=" + c());
            if (!zN) {
                File fileA = f.a("Images");
                if (fileA != null) {
                    str3 = fileA.getAbsolutePath() + File.separator + Constants.QQ_SHARE_TEMP_DIR;
                } else {
                    File cacheDir = f.a().getCacheDir();
                    if (cacheDir == null) {
                        SLog.e("openSDK_LOG.Util", "getMediaFileUri error, cacheDir is null");
                        return null;
                    }
                    str3 = cacheDir.getAbsolutePath() + File.separator + Constants.QQ_SHARE_TEMP_DIR;
                }
                File file = new File(str2);
                String absolutePath = file.getAbsolutePath();
                String str4 = str3 + File.separator + file.getName();
                str2 = a(absolutePath, str4) ? str4 : null;
            }
            Uri uriA = a(activity, str, str2);
            if (uriA == null) {
                return null;
            }
            return uriA.toString();
        } catch (Exception e2) {
            SLog.e("openSDK_LOG.Util", "getMediaFileUri error", e2);
            return null;
        }
    }

    public static boolean a(Map<String, Object> map, String str, boolean z2) {
        if (map == null) {
            SLog.e("openSDK_LOG.Util", "getBoolean error, params==null");
            return z2;
        }
        if (!map.containsKey(str)) {
            SLog.e("openSDK_LOG.Util", "getBoolean error, not comtain : " + str);
            return z2;
        }
        Object obj = map.get(str);
        return obj instanceof Boolean ? ((Boolean) obj).booleanValue() : z2;
    }

    public static String a(Map<String, Object> map, String str, String str2) {
        if (map == null) {
            SLog.e("openSDK_LOG.Util", "getString error, params==null");
            return str2;
        }
        if (!map.containsKey(str)) {
            SLog.e("openSDK_LOG.Util", "getString error, not comtain : " + str);
            return str2;
        }
        Object obj = map.get(str);
        return obj instanceof String ? (String) obj : str2;
    }

    public static Uri a(Activity activity, String str, String str2) {
        if (TextUtils.isEmpty(str2)) {
            SLog.e("openSDK_LOG.Util", "grantUriPermissionToAllQQVersion -- stringForFileUri is empty");
            return null;
        }
        try {
            String authorities = Tencent.getAuthorities(str);
            if (TextUtils.isEmpty(authorities)) {
                return null;
            }
            Uri uriForFile = FileProvider.getUriForFile(activity, authorities, new File(str2));
            activity.grantUriPermission("com.tencent.mobileqq", uriForFile, 3);
            activity.grantUriPermission(Constants.PACKAGE_TIM, uriForFile, 3);
            activity.grantUriPermission(Constants.PACKAGE_QQ_PAD, uriForFile, 3);
            activity.grantUriPermission(Constants.PACKAGE_QQ_SPEED, uriForFile, 3);
            return uriForFile;
        } catch (Exception e2) {
            SLog.e("openSDK_LOG.Util", "grantUriPermissionToAllQQVersion exception:", e2);
            return null;
        }
    }
}
