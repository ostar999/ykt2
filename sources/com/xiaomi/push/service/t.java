package com.xiaomi.push.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.aliyun.vod.log.struct.AliyunLogKey;
import com.just.agentweb.DefaultWebClient;
import com.xiaomi.push.gd;

/* loaded from: classes6.dex */
public class t {

    /* renamed from: a, reason: collision with root package name */
    private static s f25718a;

    /* renamed from: a, reason: collision with other field name */
    private static a f1091a;

    public interface a {
        void a();
    }

    public static synchronized s a(Context context) {
        s sVar = f25718a;
        if (sVar != null) {
            return sVar;
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences("mipush_account", 0);
        String string = sharedPreferences.getString(AliyunLogKey.KEY_UUID, null);
        String string2 = sharedPreferences.getString("token", null);
        String string3 = sharedPreferences.getString("security", null);
        String string4 = sharedPreferences.getString("app_id", null);
        String string5 = sharedPreferences.getString("app_token", null);
        String string6 = sharedPreferences.getString("package_name", null);
        String string7 = sharedPreferences.getString("device_id", null);
        int i2 = sharedPreferences.getInt("env_type", 1);
        if (!TextUtils.isEmpty(string7) && string7.startsWith("a-")) {
            string7 = com.xiaomi.push.j.k(context);
            sharedPreferences.edit().putString("device_id", string7).commit();
        }
        if (TextUtils.isEmpty(string) || TextUtils.isEmpty(string2) || TextUtils.isEmpty(string3)) {
            return null;
        }
        String strK = com.xiaomi.push.j.k(context);
        if ("com.xiaomi.xmsf".equals(context.getPackageName()) || TextUtils.isEmpty(strK) || TextUtils.isEmpty(string7) || string7.equals(strK)) {
            s sVar2 = new s(string, string2, string3, string4, string5, string6, i2);
            f25718a = sVar2;
            return sVar2;
        }
        com.xiaomi.channel.commonutils.logger.b.d("erase the old account.");
        m764a(context);
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x004a  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0051 A[Catch: all -> 0x021c, TryCatch #1 {, blocks: (B:4:0x0003, B:6:0x0020, B:8:0x0028, B:10:0x003f, B:12:0x004b, B:14:0x0051, B:15:0x0057, B:17:0x0061, B:18:0x0066, B:21:0x006e, B:24:0x0077, B:27:0x0080, B:28:0x008b, B:34:0x009f, B:36:0x00a8, B:38:0x00d0, B:40:0x00dc, B:41:0x00ef, B:43:0x00f9, B:45:0x00ff, B:46:0x0113, B:48:0x0119, B:49:0x011e, B:51:0x0141, B:52:0x014b, B:54:0x0155, B:55:0x015e, B:57:0x018b, B:58:0x018f, B:60:0x0195, B:62:0x01a2, B:64:0x01c3, B:65:0x01d9, B:68:0x0208, B:31:0x0097), top: B:76:0x0003, inners: #0 }] */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0061 A[Catch: all -> 0x021c, TryCatch #1 {, blocks: (B:4:0x0003, B:6:0x0020, B:8:0x0028, B:10:0x003f, B:12:0x004b, B:14:0x0051, B:15:0x0057, B:17:0x0061, B:18:0x0066, B:21:0x006e, B:24:0x0077, B:27:0x0080, B:28:0x008b, B:34:0x009f, B:36:0x00a8, B:38:0x00d0, B:40:0x00dc, B:41:0x00ef, B:43:0x00f9, B:45:0x00ff, B:46:0x0113, B:48:0x0119, B:49:0x011e, B:51:0x0141, B:52:0x014b, B:54:0x0155, B:55:0x015e, B:57:0x018b, B:58:0x018f, B:60:0x0195, B:62:0x01a2, B:64:0x01c3, B:65:0x01d9, B:68:0x0208, B:31:0x0097), top: B:76:0x0003, inners: #0 }] */
    /* JADX WARN: Removed duplicated region for block: B:20:0x006c  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0075  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x007e  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x009f A[Catch: all -> 0x021c, TryCatch #1 {, blocks: (B:4:0x0003, B:6:0x0020, B:8:0x0028, B:10:0x003f, B:12:0x004b, B:14:0x0051, B:15:0x0057, B:17:0x0061, B:18:0x0066, B:21:0x006e, B:24:0x0077, B:27:0x0080, B:28:0x008b, B:34:0x009f, B:36:0x00a8, B:38:0x00d0, B:40:0x00dc, B:41:0x00ef, B:43:0x00f9, B:45:0x00ff, B:46:0x0113, B:48:0x0119, B:49:0x011e, B:51:0x0141, B:52:0x014b, B:54:0x0155, B:55:0x015e, B:57:0x018b, B:58:0x018f, B:60:0x0195, B:62:0x01a2, B:64:0x01c3, B:65:0x01d9, B:68:0x0208, B:31:0x0097), top: B:76:0x0003, inners: #0 }] */
    /* JADX WARN: Removed duplicated region for block: B:35:0x00a6  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x00d0 A[Catch: all -> 0x021c, TryCatch #1 {, blocks: (B:4:0x0003, B:6:0x0020, B:8:0x0028, B:10:0x003f, B:12:0x004b, B:14:0x0051, B:15:0x0057, B:17:0x0061, B:18:0x0066, B:21:0x006e, B:24:0x0077, B:27:0x0080, B:28:0x008b, B:34:0x009f, B:36:0x00a8, B:38:0x00d0, B:40:0x00dc, B:41:0x00ef, B:43:0x00f9, B:45:0x00ff, B:46:0x0113, B:48:0x0119, B:49:0x011e, B:51:0x0141, B:52:0x014b, B:54:0x0155, B:55:0x015e, B:57:0x018b, B:58:0x018f, B:60:0x0195, B:62:0x01a2, B:64:0x01c3, B:65:0x01d9, B:68:0x0208, B:31:0x0097), top: B:76:0x0003, inners: #0 }] */
    /* JADX WARN: Removed duplicated region for block: B:51:0x0141 A[Catch: all -> 0x021c, TryCatch #1 {, blocks: (B:4:0x0003, B:6:0x0020, B:8:0x0028, B:10:0x003f, B:12:0x004b, B:14:0x0051, B:15:0x0057, B:17:0x0061, B:18:0x0066, B:21:0x006e, B:24:0x0077, B:27:0x0080, B:28:0x008b, B:34:0x009f, B:36:0x00a8, B:38:0x00d0, B:40:0x00dc, B:41:0x00ef, B:43:0x00f9, B:45:0x00ff, B:46:0x0113, B:48:0x0119, B:49:0x011e, B:51:0x0141, B:52:0x014b, B:54:0x0155, B:55:0x015e, B:57:0x018b, B:58:0x018f, B:60:0x0195, B:62:0x01a2, B:64:0x01c3, B:65:0x01d9, B:68:0x0208, B:31:0x0097), top: B:76:0x0003, inners: #0 }] */
    /* JADX WARN: Removed duplicated region for block: B:54:0x0155 A[Catch: all -> 0x021c, TryCatch #1 {, blocks: (B:4:0x0003, B:6:0x0020, B:8:0x0028, B:10:0x003f, B:12:0x004b, B:14:0x0051, B:15:0x0057, B:17:0x0061, B:18:0x0066, B:21:0x006e, B:24:0x0077, B:27:0x0080, B:28:0x008b, B:34:0x009f, B:36:0x00a8, B:38:0x00d0, B:40:0x00dc, B:41:0x00ef, B:43:0x00f9, B:45:0x00ff, B:46:0x0113, B:48:0x0119, B:49:0x011e, B:51:0x0141, B:52:0x014b, B:54:0x0155, B:55:0x015e, B:57:0x018b, B:58:0x018f, B:60:0x0195, B:62:0x01a2, B:64:0x01c3, B:65:0x01d9, B:68:0x0208, B:31:0x0097), top: B:76:0x0003, inners: #0 }] */
    /* JADX WARN: Removed duplicated region for block: B:57:0x018b A[Catch: all -> 0x021c, TryCatch #1 {, blocks: (B:4:0x0003, B:6:0x0020, B:8:0x0028, B:10:0x003f, B:12:0x004b, B:14:0x0051, B:15:0x0057, B:17:0x0061, B:18:0x0066, B:21:0x006e, B:24:0x0077, B:27:0x0080, B:28:0x008b, B:34:0x009f, B:36:0x00a8, B:38:0x00d0, B:40:0x00dc, B:41:0x00ef, B:43:0x00f9, B:45:0x00ff, B:46:0x0113, B:48:0x0119, B:49:0x011e, B:51:0x0141, B:52:0x014b, B:54:0x0155, B:55:0x015e, B:57:0x018b, B:58:0x018f, B:60:0x0195, B:62:0x01a2, B:64:0x01c3, B:65:0x01d9, B:68:0x0208, B:31:0x0097), top: B:76:0x0003, inners: #0 }] */
    /* JADX WARN: Removed duplicated region for block: B:60:0x0195 A[Catch: all -> 0x021c, TryCatch #1 {, blocks: (B:4:0x0003, B:6:0x0020, B:8:0x0028, B:10:0x003f, B:12:0x004b, B:14:0x0051, B:15:0x0057, B:17:0x0061, B:18:0x0066, B:21:0x006e, B:24:0x0077, B:27:0x0080, B:28:0x008b, B:34:0x009f, B:36:0x00a8, B:38:0x00d0, B:40:0x00dc, B:41:0x00ef, B:43:0x00f9, B:45:0x00ff, B:46:0x0113, B:48:0x0119, B:49:0x011e, B:51:0x0141, B:52:0x014b, B:54:0x0155, B:55:0x015e, B:57:0x018b, B:58:0x018f, B:60:0x0195, B:62:0x01a2, B:64:0x01c3, B:65:0x01d9, B:68:0x0208, B:31:0x0097), top: B:76:0x0003, inners: #0 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static synchronized com.xiaomi.push.service.s a(android.content.Context r9, java.lang.String r10, java.lang.String r11, java.lang.String r12) {
        /*
            Method dump skipped, instructions count: 543
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.push.service.t.a(android.content.Context, java.lang.String, java.lang.String, java.lang.String):com.xiaomi.push.service.s");
    }

    /* renamed from: a, reason: collision with other method in class */
    public static String m763a(Context context) {
        StringBuilder sb;
        String str;
        String strA = com.xiaomi.push.service.a.a(context).a();
        if (com.xiaomi.push.ab.b()) {
            sb = new StringBuilder();
            sb.append(DefaultWebClient.HTTP_SCHEME);
            sb.append(gd.f24925b);
            str = ":9085";
        } else if (com.xiaomi.push.q.Global.name().equals(strA)) {
            sb = new StringBuilder();
            str = "https://register.xmpush.global.xiaomi.com";
        } else if (com.xiaomi.push.q.Europe.name().equals(strA)) {
            sb = new StringBuilder();
            str = "https://fr.register.xmpush.global.xiaomi.com";
        } else if (com.xiaomi.push.q.Russia.name().equals(strA)) {
            sb = new StringBuilder();
            str = "https://ru.register.xmpush.global.xiaomi.com";
        } else if (com.xiaomi.push.q.India.name().equals(strA)) {
            sb = new StringBuilder();
            str = "https://idmb.register.xmpush.global.xiaomi.com";
        } else {
            sb = new StringBuilder();
            sb.append(DefaultWebClient.HTTPS_SCHEME);
            str = com.xiaomi.push.ab.m193a() ? "sandbox.xmpush.xiaomi.com" : "register.xmpush.xiaomi.com";
        }
        sb.append(str);
        sb.append("/pass/v2/register");
        return sb.toString();
    }

    public static void a() {
        a aVar = f1091a;
        if (aVar != null) {
            aVar.a();
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    public static void m764a(Context context) {
        context.getSharedPreferences("mipush_account", 0).edit().clear().commit();
        f25718a = null;
        a();
    }

    public static void a(Context context, s sVar) {
        SharedPreferences.Editor editorEdit = context.getSharedPreferences("mipush_account", 0).edit();
        editorEdit.putString(AliyunLogKey.KEY_UUID, sVar.f1090a);
        editorEdit.putString("security", sVar.f25714c);
        editorEdit.putString("token", sVar.f25713b);
        editorEdit.putString("app_id", sVar.f25715d);
        editorEdit.putString("package_name", sVar.f25717f);
        editorEdit.putString("app_token", sVar.f25716e);
        editorEdit.putString("device_id", com.xiaomi.push.j.k(context));
        editorEdit.putInt("env_type", sVar.f25712a);
        editorEdit.commit();
        a();
    }

    public static void a(a aVar) {
        f1091a = aVar;
    }

    /* renamed from: a, reason: collision with other method in class */
    private static boolean m765a(Context context) {
        return context.getPackageName().equals("com.xiaomi.xmsf");
    }
}
