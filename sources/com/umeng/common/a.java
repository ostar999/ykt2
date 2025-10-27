package com.umeng.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Base64;
import com.umeng.commonsdk.debug.UMRTLog;
import com.umeng.commonsdk.statistics.common.DataHelper;
import com.umeng.commonsdk.utils.UMUtils;

/* loaded from: classes6.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    private static String f23004a = null;

    /* renamed from: b, reason: collision with root package name */
    private static final String f23005b = "umeng+";

    /* renamed from: c, reason: collision with root package name */
    private static final String f23006c = "ek__id";

    /* renamed from: d, reason: collision with root package name */
    private static final String f23007d = "ek_key";

    /* renamed from: e, reason: collision with root package name */
    private static String f23008e = "";

    /* renamed from: f, reason: collision with root package name */
    private static final String f23009f = "umeng_subprocess_info";

    /* renamed from: g, reason: collision with root package name */
    private static String f23010g = "";

    /* renamed from: h, reason: collision with root package name */
    private static a f23011h;

    private a() {
    }

    public static a a() {
        if (f23011h == null) {
            synchronized (a.class) {
                if (f23011h == null) {
                    f23011h = new a();
                }
            }
        }
        return f23011h;
    }

    private String c(String str) {
        String string = "";
        try {
            String strSubstring = str.substring(1, 9);
            StringBuilder sb = new StringBuilder();
            for (int i2 = 0; i2 < strSubstring.length(); i2++) {
                char cCharAt = strSubstring.charAt(i2);
                if (!Character.isDigit(cCharAt)) {
                    sb.append(cCharAt);
                } else if (Integer.parseInt(Character.toString(cCharAt)) == 0) {
                    sb.append(0);
                } else {
                    sb.append(10 - Integer.parseInt(Character.toString(cCharAt)));
                }
            }
            string = sb.toString();
            return string + new StringBuilder(string).reverse().toString();
        } catch (Throwable unused) {
            return string;
        }
    }

    public String b(String str) {
        String str2;
        String str3;
        try {
            return TextUtils.isEmpty(f23004a) ? str : new String(DataHelper.decrypt(Base64.decode(str.getBytes(), 0), f23004a.getBytes()));
        } catch (Exception unused) {
            UMRTLog.e(UMRTLog.RTLOG_TAG, "--->>> 子进程事件数据解密失败!");
            String str4 = null;
            if (TextUtils.isEmpty(f23008e)) {
                return null;
            }
            UMRTLog.e(UMRTLog.RTLOG_TAG, "--->>> 子进程事件数据解密失败，换老秘钥重试");
            try {
                String str5 = new String(DataHelper.decrypt(Base64.decode(str.getBytes(), 0), f23008e.getBytes()));
                try {
                    UMRTLog.e(UMRTLog.RTLOG_TAG, "--->>> 子进程事件数据解密失败，换老秘钥重试成功。");
                    return str5;
                } catch (Exception unused2) {
                    str4 = str5;
                    UMRTLog.e(UMRTLog.RTLOG_TAG, "--->>> 子进程事件数据解密失败，换老秘钥重试失败。换子进程备份key重试。");
                    try {
                        str3 = new String(DataHelper.decrypt(Base64.decode(str.getBytes(), 0), f23010g.getBytes()));
                    } catch (Throwable unused3) {
                        str2 = str4;
                    }
                    try {
                        UMRTLog.e(UMRTLog.RTLOG_TAG, "--->>> 子进程事件数据解密失败，子进程备份key重试成功。");
                        return str3;
                    } catch (Throwable unused4) {
                        str2 = str3;
                        UMRTLog.e(UMRTLog.RTLOG_TAG, "--->>> 子进程事件数据解密失败，子进程备份key重试失败。");
                        return str2;
                    }
                }
            } catch (Exception unused5) {
            }
        }
    }

    public void a(Context context) {
        try {
            if (TextUtils.isEmpty(f23004a)) {
                String multiProcessSP = UMUtils.getMultiProcessSP(context, f23006c);
                if (!TextUtils.isEmpty(multiProcessSP)) {
                    f23008e = c(multiProcessSP);
                    UMRTLog.e(UMRTLog.RTLOG_TAG, "--->>>> primaryKey: " + f23008e);
                }
                SharedPreferences sharedPreferences = context.getApplicationContext().getSharedPreferences(f23009f, 0);
                if (sharedPreferences != null) {
                    f23010g = sharedPreferences.getString(f23006c, null);
                    UMRTLog.e(UMRTLog.RTLOG_TAG, "--->>> 子进程备份秘钥：主进程key: " + f23010g);
                }
                f23004a = c(UMUtils.genId());
                UMRTLog.e(UMRTLog.RTLOG_TAG, "--->>>> 正式秘钥：key: " + f23004a);
            }
        } catch (Throwable unused) {
        }
    }

    public String a(String str) {
        try {
            return TextUtils.isEmpty(f23004a) ? str : Base64.encodeToString(DataHelper.encrypt(str.getBytes(), f23004a.getBytes()), 0);
        } catch (Exception unused) {
            return null;
        }
    }
}
