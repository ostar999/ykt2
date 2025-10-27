package com.umeng.socialize.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import com.umeng.socialize.Config;
import com.umeng.socialize.common.SocializeConstants;
import com.umeng.socialize.net.dplus.cache.DplueCache;
import com.yikaobang.yixue.R2;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import org.json.JSONObject;

/* loaded from: classes6.dex */
public class SocializeUtils {
    protected static final String TAG = "SocializeUtils";
    private static final char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    public static Set<Uri> deleteUris = new HashSet();
    private static Pattern mDoubleByte_Pattern = null;
    private static int smDip = 0;

    public static byte[] File2byte(File file) throws IOException {
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] bArr = new byte[1024];
            while (true) {
                int i2 = fileInputStream.read(bArr);
                if (i2 == -1) {
                    fileInputStream.close();
                    byteArrayOutputStream.close();
                    return byteArrayOutputStream.toByteArray();
                }
                byteArrayOutputStream.write(bArr, 0, i2);
            }
        } catch (FileNotFoundException e2) {
            SLog.error(e2);
            return null;
        } catch (IOException e3) {
            SLog.error(e3);
            return null;
        }
    }

    public static boolean assertBinaryInvalid(byte[] bArr) {
        return bArr != null && bArr.length > 0;
    }

    public static Map<String, String> bundleTomap(Bundle bundle) {
        if (bundle == null || bundle.isEmpty()) {
            return null;
        }
        Set<String> setKeySet = bundle.keySet();
        HashMap map = new HashMap();
        for (String str : setKeySet) {
            if (str.equals("com.sina.weibo.intent.extra.USER_ICON")) {
                map.put("icon_url", bundle.getString(str));
            }
            map.put(str, bundle.getString(str));
        }
        return map;
    }

    public static int countContentLength(String str) {
        String strTrim = str.trim();
        int i2 = 0;
        while (getDoubleBytePattern().matcher(strTrim).find()) {
            i2++;
        }
        int length = strTrim.length() - i2;
        return i2 + (length % 2 != 0 ? (length + 1) / 2 : length / 2);
    }

    public static Bundle decodeUrl(String str) {
        Bundle bundle = new Bundle();
        if (str != null) {
            for (String str2 : str.split("&")) {
                String[] strArrSplit = str2.split("=");
                bundle.putString(URLDecoder.decode(strArrSplit[0]), URLDecoder.decode(strArrSplit[1]));
            }
        }
        return bundle;
    }

    public static int dip2Px(Context context, float f2) {
        return (int) ((f2 * context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static String getAppkey(Context context) throws PackageManager.NameNotFoundException {
        Object obj;
        if (context == null) {
            return "";
        }
        String str = SocializeConstants.APPKEY;
        if (!TextUtils.isEmpty(str)) {
            return str;
        }
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128);
            return (applicationInfo == null || (obj = applicationInfo.metaData.get("UMENG_APPKEY")) == null) ? str : obj.toString();
        } catch (Exception e2) {
            SLog.error(e2);
            return str;
        }
    }

    private static Pattern getDoubleBytePattern() {
        if (mDoubleByte_Pattern == null) {
            mDoubleByte_Pattern = Pattern.compile("[^\\x00-\\xff]");
        }
        return mDoubleByte_Pattern;
    }

    public static int[] getFloatWindowSize(Context context) {
        return context == null ? new int[2] : new int[]{R2.attr.bl_enabled_gradient_gradientRadius, R2.attr.arcLabelPaddingBottom};
    }

    public static String hexdigest(String str) {
        try {
            return md5(str.getBytes());
        } catch (Exception e2) {
            SLog.error(e2);
            return null;
        }
    }

    public static Uri insertImage(Context context, String str) {
        if (!TextUtils.isEmpty(str) && new File(str).exists()) {
            try {
                String strInsertImage = MediaStore.Images.Media.insertImage(context.getContentResolver(), str, "umeng_social_shareimg", (String) null);
                if (TextUtils.isEmpty(strInsertImage)) {
                    return null;
                }
                return Uri.parse(strInsertImage);
            } catch (Throwable th) {
                SLog.error(th);
            }
        }
        return null;
    }

    public static boolean isFloatWindowStyle(Context context) {
        if (context != null && SocializeConstants.SUPPORT_PAD) {
            if (smDip == 0) {
                WindowManager windowManager = (WindowManager) context.getSystemService("window");
                Display defaultDisplay = windowManager.getDefaultDisplay();
                int width = defaultDisplay.getWidth();
                int height = defaultDisplay.getHeight();
                if (width > height) {
                    width = height;
                }
                DisplayMetrics displayMetrics = new DisplayMetrics();
                windowManager.getDefaultDisplay().getMetrics(displayMetrics);
                smDip = (int) ((width / displayMetrics.density) + 0.5f);
            }
            if ((context.getResources().getConfiguration().screenLayout & 15) >= 3 && smDip >= 550) {
                return true;
            }
        }
        return false;
    }

    public static boolean isHasDplusCache() {
        File filePath = DplueCache.getFilePath("s_e");
        File filePath2 = DplueCache.getFilePath("auth");
        File filePath3 = DplueCache.getFilePath("userinfo");
        File filePath4 = DplueCache.getFilePath("dau");
        File filePath5 = DplueCache.getFilePath("stats");
        return ((filePath == null || filePath.listFiles() == null || filePath.listFiles().length <= 0) && (filePath2 == null || filePath2.listFiles() == null || filePath2.listFiles().length <= 0) && ((filePath3 == null || filePath3.listFiles() == null || filePath3.listFiles().length <= 0) && ((filePath4 == null || filePath4.listFiles() == null || filePath4.listFiles().length <= 0) && (filePath5 == null || filePath5.listFiles() == null || filePath5.listFiles().length <= 0)))) ? false : true;
    }

    public static boolean isToday(long j2) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(System.currentTimeMillis()));
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(new Date(j2));
        return calendar2.get(1) == calendar.get(1) && calendar2.get(6) - calendar.get(6) == 0;
    }

    public static JSONObject jsonObjectExt(String str) {
        new HashMap();
        try {
            return new JSONObject(str);
        } catch (Exception e2) {
            SLog.error(e2);
            return null;
        }
    }

    public static Map<String, String> jsonToMap(String str) {
        HashMap map = new HashMap();
        try {
            JSONObject jSONObject = new JSONObject(str);
            Iterator<String> itKeys = jSONObject.keys();
            while (itKeys.hasNext()) {
                String next = itKeys.next();
                map.put(next, jSONObject.get(next) + "");
            }
        } catch (Exception e2) {
            SLog.error(e2);
        }
        return map;
    }

    public static Bundle mapToBundle(Map<String, String> map) {
        Bundle bundle = new Bundle();
        for (String str : map.keySet()) {
            bundle.putString(str, map.get(str));
        }
        return bundle;
    }

    public static String md5(byte[] bArr) throws NoSuchAlgorithmException {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(bArr);
            byte[] bArrDigest = messageDigest.digest();
            char[] cArr = new char[32];
            int i2 = 0;
            for (int i3 = 0; i3 < 16; i3++) {
                byte b3 = bArrDigest[i3];
                int i4 = i2 + 1;
                char[] cArr2 = hexDigits;
                cArr[i2] = cArr2[(b3 >>> 4) & 15];
                i2 = i4 + 1;
                cArr[i4] = cArr2[b3 & 15];
            }
            return new String(cArr);
        } catch (Exception e2) {
            SLog.error(e2);
            return null;
        }
    }

    public static void openApplicationMarket(Context context, String str) throws Exception {
        if (Config.isJumptoAppStore) {
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.setData(Uri.parse("market://details?id=" + str));
            context.startActivity(intent);
        }
    }

    public static Bundle parseUrl(String str) {
        try {
            URL url = new URL(str);
            Bundle bundleDecodeUrl = decodeUrl(url.getQuery());
            bundleDecodeUrl.putAll(decodeUrl(url.getRef()));
            return bundleDecodeUrl;
        } catch (MalformedURLException unused) {
            return new Bundle();
        }
    }

    public static void safeCloseDialog(Dialog dialog) {
        if (dialog != null) {
            try {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
            } catch (Exception e2) {
                SLog.error(e2);
            }
        }
    }

    public static void safeShowDialog(Dialog dialog) {
        if (dialog != null) {
            try {
                if (dialog.isShowing()) {
                    return;
                }
                dialog.show();
            } catch (Exception e2) {
                SLog.error(e2);
            }
        }
    }
}
