package com.umeng.socialize.tracker;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.psychiatrygarden.utils.MimeTypes;
import com.umeng.commonsdk.debug.UMRTLog;
import com.umeng.socialize.tracker.utils.HttpRequestListener;
import com.umeng.socialize.tracker.utils.b;
import com.umeng.socialize.tracker.utils.c;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

/* loaded from: classes6.dex */
class b {

    /* renamed from: a, reason: collision with root package name */
    private static final String f23817a = "TrackerManagerImpl";

    /* renamed from: b, reason: collision with root package name */
    private static final int f23818b = 3000;

    /* renamed from: c, reason: collision with root package name */
    private static final String f23819c = "_$";

    /* renamed from: d, reason: collision with root package name */
    private static final int f23820d = 16;

    /* renamed from: e, reason: collision with root package name */
    private static final int f23821e = 64;

    /* renamed from: f, reason: collision with root package name */
    private static final String f23822f = "自定义属性名长度不能超过16个字符。";

    /* renamed from: g, reason: collision with root package name */
    private static final String f23823g = "自定义属性值长度不能超过64个字符。";

    public static class a {

        /* renamed from: a, reason: collision with root package name */
        private static final b f23829a = new b();

        private a() {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String d(String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            return "错误详情 [" + (jSONObject.has("code") ? jSONObject.optInt("code") : 0) + "]: " + (jSONObject.has("msg") ? jSONObject.optString("msg") : "");
        } catch (Throwable unused) {
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String e(String str) {
        JSONObject jSONObjectOptJSONObject;
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (jSONObject.has("data") && (jSONObjectOptJSONObject = jSONObject.optJSONObject("data")) != null && jSONObjectOptJSONObject.has(com.umeng.socialize.tracker.a.f23815l)) {
                return jSONObjectOptJSONObject.optString(com.umeng.socialize.tracker.a.f23815l);
            }
            return null;
        } catch (Throwable unused) {
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean f(String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            return (jSONObject.has("code") ? jSONObject.getInt("code") : 0) == 200;
        } catch (Throwable unused) {
            return false;
        }
    }

    private b() {
    }

    public static b a() {
        return a.f23829a;
    }

    public void a(Context context, String str, String str2, String str3, String str4, Map<String, String> map, final int i2, final TrackerResultHandler trackerResultHandler) {
        HashMap map2 = new HashMap();
        if (map != null && !map.isEmpty()) {
            for (String str5 : map.keySet()) {
                String str6 = map.get(str5);
                if (!TextUtils.isEmpty(str5)) {
                    if (str5.length() > 16) {
                        Log.e("UMLog", f23822f);
                    } else if (TextUtils.isEmpty(str6)) {
                        Log.e("UMLog", "自定义属性[" + str5 + "]值为null或者空字符串。");
                    } else if (str6.length() > 64) {
                        Log.e("UMLog", f23823g);
                    } else {
                        map2.put(str5, str6);
                    }
                }
            }
        }
        JSONObject jSONObject = new JSONObject();
        JSONObject jSONObject2 = new JSONObject();
        JSONObject jSONObject3 = new JSONObject();
        try {
            for (String str7 : map2.keySet()) {
                if (!TextUtils.isEmpty(str7)) {
                    jSONObject3.put(str7, map2.get(str7));
                }
            }
            jSONObject2.put(com.umeng.socialize.tracker.a.f23807d, str);
            jSONObject2.put(com.umeng.socialize.tracker.a.f23808e, str2);
            if (!TextUtils.isEmpty(str4)) {
                jSONObject2.put(com.umeng.socialize.tracker.a.f23810g, str4);
            }
            if (!TextUtils.isEmpty(str3)) {
                jSONObject2.put(com.umeng.socialize.tracker.a.f23809f, str3);
            } else {
                jSONObject2.put(com.umeng.socialize.tracker.a.f23809f, (Object) null);
            }
            jSONObject.put(com.umeng.socialize.tracker.a.f23806c, jSONObject2);
            jSONObject.put(com.umeng.socialize.tracker.a.f23811h, jSONObject3);
            final String string = jSONObject.toString();
            context.getApplicationContext();
            c.a(new Runnable() { // from class: com.umeng.socialize.tracker.b.1
                @Override // java.lang.Runnable
                public void run() {
                    String str8 = string;
                    HashMap map3 = new HashMap();
                    map3.put("Content-Type", MimeTypes.APP_JSON);
                    com.umeng.socialize.tracker.utils.b bVar = new com.umeng.socialize.tracker.utils.b(com.umeng.socialize.tracker.a.f23804a, b.a.POST, map3, new HttpRequestListener() { // from class: com.umeng.socialize.tracker.b.1.1
                        @Override // com.umeng.socialize.tracker.utils.HttpRequestListener
                        public void a() {
                        }

                        @Override // com.umeng.socialize.tracker.utils.HttpRequestListener
                        public void a(String str9) {
                            if (TextUtils.isEmpty(str9)) {
                                trackerResultHandler.codeGenerateFailed(new Throwable(com.umeng.socialize.tracker.utils.a.EmptyResponse.a()));
                                return;
                            }
                            UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>> utc:onRequestSuccess: " + str9);
                            if (b.f(str9)) {
                                String strE = b.e(str9);
                                UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>> utc:success:rootTrackCode: " + strE);
                                trackerResultHandler.codeGenerateSuccess(strE);
                                return;
                            }
                            String strD = b.d(str9);
                            UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>> utc:failed:error: " + strD);
                            trackerResultHandler.codeGenerateFailed(new Throwable(strD));
                        }

                        @Override // com.umeng.socialize.tracker.utils.HttpRequestListener
                        public void a(Throwable th) {
                            trackerResultHandler.codeGenerateFailed(th);
                        }
                    });
                    int i3 = i2;
                    bVar.a((i3 <= 0 || i3 > 10) ? 3000 : i3 * 1000, str8);
                }
            });
        } catch (Throwable unused) {
            trackerResultHandler.codeGenerateFailed(new Throwable(com.umeng.socialize.tracker.utils.a.ErrorMakeRequestBody.a()));
        }
    }
}
