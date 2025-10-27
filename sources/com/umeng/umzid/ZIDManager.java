package com.umeng.umzid;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.aliyun.vod.log.struct.AliyunLogKey;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import java.util.UUID;
import org.json.JSONObject;

/* loaded from: classes6.dex */
public class ZIDManager {

    /* renamed from: c, reason: collision with root package name */
    public static ZIDManager f23868c;

    /* renamed from: a, reason: collision with root package name */
    public boolean f23869a = false;

    /* renamed from: b, reason: collision with root package name */
    public boolean f23870b = false;

    public class a implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ Context f23871a;

        /* renamed from: b, reason: collision with root package name */
        public final /* synthetic */ IZIDCompletionCallback f23872b;

        public a(Context context, IZIDCompletionCallback iZIDCompletionCallback) {
            this.f23871a = context;
            this.f23872b = iZIDCompletionCallback;
        }

        @Override // java.lang.Runnable
        public void run() {
            String strA = ZIDManager.a(ZIDManager.this, this.f23871a);
            if (TextUtils.isEmpty(strA)) {
                IZIDCompletionCallback iZIDCompletionCallback = this.f23872b;
                if (iZIDCompletionCallback != null) {
                    iZIDCompletionCallback.onFailure("1002", "获取zid失败");
                    return;
                }
                return;
            }
            IZIDCompletionCallback iZIDCompletionCallback2 = this.f23872b;
            if (iZIDCompletionCallback2 != null) {
                iZIDCompletionCallback2.onSuccess(strA);
            }
        }
    }

    public class b implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ Context f23874a;

        public b(Context context) {
            this.f23874a = context;
        }

        /* JADX WARN: Removed duplicated region for block: B:10:0x0027  */
        @Override // java.lang.Runnable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void run() {
            /*
                r12 = this;
                java.lang.String r0 = "aaid"
                com.umeng.umzid.ZIDManager r1 = com.umeng.umzid.ZIDManager.this
                android.content.Context r2 = r12.f23874a
                boolean r3 = r1.f23870b
                if (r3 == 0) goto Lc
                goto L10f
            Lc:
                r3 = 1
                r1.f23870b = r3
                org.json.JSONObject r4 = new org.json.JSONObject
                r4.<init>()
                java.lang.String r5 = "zdata"
                java.lang.String r6 = ""
                if (r2 == 0) goto L27
                android.content.SharedPreferences r7 = com.umeng.umzid.a.a(r2)     // Catch: java.lang.Throwable -> L10c
                if (r7 == 0) goto L27
                r8 = 0
                java.lang.String r7 = r7.getString(r5, r8)     // Catch: java.lang.Throwable -> L10c
                goto L28
            L27:
                r7 = r6
            L28:
                java.lang.String r8 = com.umeng.umzid.Spy.getID()     // Catch: java.lang.Throwable -> L10c
                r4.put(r5, r8)     // Catch: java.lang.Throwable -> L10c
                java.lang.String r5 = "old_zdata"
                r4.put(r5, r7)     // Catch: java.lang.Throwable -> L10c
                java.lang.String r5 = "oaid"
                if (r2 == 0) goto L4d
                android.content.SharedPreferences r7 = com.umeng.umzid.a.a(r2)     // Catch: java.lang.Throwable -> L10c
                if (r7 == 0) goto L4d
                java.lang.String r7 = r7.getString(r5, r6)     // Catch: java.lang.Throwable -> L10c
                boolean r9 = com.umeng.umzid.c.c(r7)     // Catch: java.lang.Throwable -> L10c
                if (r9 == 0) goto L4e
                java.lang.String r7 = com.umeng.umzid.c.a(r7)     // Catch: java.lang.Throwable -> L10c
                goto L4e
            L4d:
                r7 = r6
            L4e:
                java.lang.String r9 = com.umeng.umzid.c.c(r2)     // Catch: java.lang.Throwable -> L10c
                java.lang.String r10 = "old_oaid"
                r4.put(r10, r7)     // Catch: java.lang.Throwable -> L10c
                r4.put(r5, r9)     // Catch: java.lang.Throwable -> L10c
                java.lang.String r5 = "mac"
                if (r2 == 0) goto L73
                android.content.SharedPreferences r7 = com.umeng.umzid.a.a(r2)     // Catch: java.lang.Throwable -> L10c
                if (r7 == 0) goto L73
                java.lang.String r7 = r7.getString(r5, r6)     // Catch: java.lang.Throwable -> L10c
                boolean r10 = com.umeng.umzid.c.c(r7)     // Catch: java.lang.Throwable -> L10c
                if (r10 == 0) goto L74
                java.lang.String r7 = com.umeng.umzid.c.a(r7)     // Catch: java.lang.Throwable -> L10c
                goto L74
            L73:
                r7 = r6
            L74:
                java.lang.String r10 = com.umeng.umzid.c.b(r2)     // Catch: java.lang.Throwable -> L10c
                r4.put(r5, r10)     // Catch: java.lang.Throwable -> L10c
                java.lang.String r5 = "old_mac"
                r4.put(r5, r7)     // Catch: java.lang.Throwable -> L10c
                r1.a(r2, r4)     // Catch: java.lang.Throwable -> L10c
                java.lang.String r5 = com.umeng.umzid.c.a(r2)     // Catch: java.lang.Throwable -> L10c
                r4.put(r0, r5)     // Catch: java.lang.Throwable -> L10c
                java.lang.String r5 = "uabc"
                if (r2 == 0) goto L9a
                android.content.SharedPreferences r7 = com.umeng.umzid.a.a(r2)     // Catch: java.lang.Throwable -> L10c
                if (r7 == 0) goto L9a
                java.lang.String r7 = r7.getString(r5, r6)     // Catch: java.lang.Throwable -> L10c
                goto L9b
            L9a:
                r7 = r6
            L9b:
                r4.put(r5, r7)     // Catch: java.lang.Throwable -> L10c
                java.lang.String r7 = "resetToken"
                if (r2 == 0) goto Lad
                android.content.SharedPreferences r11 = com.umeng.umzid.a.a(r2)     // Catch: java.lang.Throwable -> L10c
                if (r11 == 0) goto Lad
                java.lang.String r6 = r11.getString(r7, r6)     // Catch: java.lang.Throwable -> L10c
            Lad:
                boolean r11 = android.text.TextUtils.isEmpty(r6)     // Catch: java.lang.Throwable -> L10c
                if (r11 != 0) goto Lb6
                r4.put(r7, r6)     // Catch: java.lang.Throwable -> L10c
            Lb6:
                java.lang.String r6 = "https://aaid.umeng.com/api/updateZdata"
                java.lang.String r4 = r4.toString()     // Catch: java.lang.Throwable -> L10c
                java.lang.String r4 = com.umeng.umzid.a.a(r6, r4)     // Catch: java.lang.Throwable -> L10c
                boolean r6 = android.text.TextUtils.isEmpty(r4)     // Catch: java.lang.Throwable -> L10c
                if (r6 != 0) goto L10c
                org.json.JSONObject r6 = new org.json.JSONObject     // Catch: java.lang.Throwable -> L10c
                r6.<init>(r4)     // Catch: java.lang.Throwable -> L10c
                java.lang.String r4 = "suc"
                boolean r4 = r6.optBoolean(r4)     // Catch: java.lang.Throwable -> L10c
                java.lang.Boolean r4 = java.lang.Boolean.valueOf(r4)     // Catch: java.lang.Throwable -> L10c
                boolean r4 = r4.booleanValue()     // Catch: java.lang.Throwable -> L10c
                if (r4 != r3) goto L10c
                com.umeng.umzid.c.f(r2, r8)     // Catch: java.lang.Throwable -> L10c
                com.umeng.umzid.c.a(r2, r10)     // Catch: java.lang.Throwable -> L10c
                com.umeng.umzid.c.b(r2, r9)     // Catch: java.lang.Throwable -> L10c
                java.lang.String r0 = r6.optString(r0)     // Catch: java.lang.Throwable -> L10c
                boolean r3 = android.text.TextUtils.isEmpty(r0)     // Catch: java.lang.Throwable -> L10c
                if (r3 != 0) goto Lf2
                com.umeng.umzid.c.e(r2, r0)     // Catch: java.lang.Throwable -> L10c
            Lf2:
                java.lang.String r0 = r6.getString(r5)     // Catch: java.lang.Throwable -> L10c
                boolean r3 = android.text.TextUtils.isEmpty(r0)     // Catch: java.lang.Throwable -> L10c
                if (r3 != 0) goto Lff
                com.umeng.umzid.c.d(r2, r0)     // Catch: java.lang.Throwable -> L10c
            Lff:
                java.lang.String r0 = r6.getString(r7)     // Catch: java.lang.Throwable -> L10c
                boolean r3 = android.text.TextUtils.isEmpty(r0)     // Catch: java.lang.Throwable -> L10c
                if (r3 != 0) goto L10c
                com.umeng.umzid.c.c(r2, r0)     // Catch: java.lang.Throwable -> L10c
            L10c:
                r0 = 0
                r1.f23870b = r0
            L10f:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.umeng.umzid.ZIDManager.b.run():void");
        }
    }

    public class c implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ Context f23876a;

        public c(Context context) {
            this.f23876a = context;
        }

        @Override // java.lang.Runnable
        public void run() {
            ZIDManager.a(ZIDManager.this, this.f23876a);
        }
    }

    public static /* synthetic */ String a(ZIDManager zIDManager, Context context) {
        String strOptString = null;
        if (!zIDManager.f23869a) {
            zIDManager.f23869a = true;
            JSONObject jSONObject = new JSONObject();
            try {
                String id = Spy.getID();
                jSONObject.put("zdata", id);
                String strB = com.umeng.umzid.c.b(context);
                jSONObject.put(SocializeProtocolConstants.PROTOCOL_KEY_MAC, strB);
                String strC = com.umeng.umzid.c.c(context);
                jSONObject.put("oaid", strC);
                zIDManager.a(context, jSONObject);
                String strA = com.umeng.umzid.a.a("https://aaid.umeng.com/api/postZdata", jSONObject.toString());
                if (!TextUtils.isEmpty(strA)) {
                    JSONObject jSONObject2 = new JSONObject(strA);
                    if (Boolean.valueOf(jSONObject2.optBoolean("suc")).booleanValue()) {
                        com.umeng.umzid.c.f(context, id);
                        com.umeng.umzid.c.a(context, strB);
                        com.umeng.umzid.c.b(context, strC);
                        strOptString = jSONObject2.optString("aaid");
                        if (!TextUtils.isEmpty(strOptString)) {
                            com.umeng.umzid.c.e(context, strOptString);
                        }
                        String string = jSONObject2.getString("uabc");
                        if (!TextUtils.isEmpty(string)) {
                            com.umeng.umzid.c.d(context, string);
                        }
                        String string2 = jSONObject2.getString("resetToken");
                        if (!TextUtils.isEmpty(string2)) {
                            com.umeng.umzid.c.c(context, string2);
                        }
                    }
                }
            } catch (Throwable unused) {
            }
            zIDManager.f23869a = false;
        }
        return strOptString;
    }

    public static synchronized ZIDManager getInstance() {
        if (f23868c == null) {
            f23868c = new ZIDManager();
        }
        return f23868c;
    }

    public static String getSDKVersion() {
        return "1.4.1";
    }

    public synchronized String getZID(Context context) {
        if (context == null) {
            return "";
        }
        Context applicationContext = context.getApplicationContext();
        String strA = com.umeng.umzid.c.a(applicationContext);
        if (!TextUtils.isEmpty(strA)) {
            return strA;
        }
        com.umeng.umzid.b.a(new c(applicationContext));
        return "";
    }

    public synchronized void init(Context context, String str, IZIDCompletionCallback iZIDCompletionCallback) {
        SharedPreferences sharedPreferencesA;
        SharedPreferences.Editor editorEdit;
        if (context == null) {
            if (iZIDCompletionCallback != null) {
                iZIDCompletionCallback.onFailure("1001", "传入参数Context为null");
            }
            return;
        }
        if (TextUtils.isEmpty(str)) {
            if (iZIDCompletionCallback != null) {
                iZIDCompletionCallback.onFailure("1003", "传入参数appkey为空");
            }
            return;
        }
        Context applicationContext = context.getApplicationContext();
        if (applicationContext != null && str != null && !TextUtils.isEmpty(str) && (sharedPreferencesA = com.umeng.umzid.a.a(applicationContext)) != null && (editorEdit = sharedPreferencesA.edit()) != null) {
            editorEdit.putString("appkey", str).commit();
        }
        String strA = com.umeng.umzid.c.a(applicationContext);
        if (strA == null || TextUtils.isEmpty(strA)) {
            com.umeng.umzid.b.a(new a(applicationContext, iZIDCompletionCallback));
        } else {
            com.umeng.umzid.b.a(new b(applicationContext));
            if (iZIDCompletionCallback != null) {
                iZIDCompletionCallback.onSuccess(strA);
            }
        }
        SharedPreferences sharedPreferencesA2 = com.umeng.umzid.a.a(context);
        if (TextUtils.isEmpty(sharedPreferencesA2 != null ? sharedPreferencesA2.getString(AliyunLogKey.KEY_UUID, "") : "")) {
            String string = "";
            SharedPreferences sharedPreferencesA3 = com.umeng.umzid.a.a(context);
            try {
                string = UUID.randomUUID().toString();
            } catch (Throwable unused) {
            }
            if (sharedPreferencesA3 != null) {
                sharedPreferencesA3.edit().putString(AliyunLogKey.KEY_UUID, string).commit();
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0036  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x00a2  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x00d9  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final org.json.JSONObject a(android.content.Context r11, org.json.JSONObject r12) throws org.json.JSONException, java.lang.NoSuchMethodException, android.content.res.Resources.NotFoundException, java.lang.SecurityException {
        /*
            Method dump skipped, instructions count: 515
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.umzid.ZIDManager.a(android.content.Context, org.json.JSONObject):org.json.JSONObject");
    }
}
