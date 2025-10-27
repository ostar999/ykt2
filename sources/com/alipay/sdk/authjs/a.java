package com.alipay.sdk.authjs;

import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public final class a {

    /* renamed from: a, reason: collision with root package name */
    public static final String f3168a = "CallInfo";

    /* renamed from: b, reason: collision with root package name */
    public static final String f3169b = "call";

    /* renamed from: c, reason: collision with root package name */
    public static final String f3170c = "callback";

    /* renamed from: d, reason: collision with root package name */
    public static final String f3171d = "bundleName";

    /* renamed from: e, reason: collision with root package name */
    public static final String f3172e = "clientId";

    /* renamed from: f, reason: collision with root package name */
    public static final String f3173f = "param";

    /* renamed from: g, reason: collision with root package name */
    public static final String f3174g = "func";

    /* renamed from: h, reason: collision with root package name */
    public static final String f3175h = "msgType";

    /* renamed from: i, reason: collision with root package name */
    public String f3176i;

    /* renamed from: j, reason: collision with root package name */
    public String f3177j;

    /* renamed from: k, reason: collision with root package name */
    public String f3178k;

    /* renamed from: l, reason: collision with root package name */
    public String f3179l;

    /* renamed from: m, reason: collision with root package name */
    public JSONObject f3180m;

    /* renamed from: n, reason: collision with root package name */
    private boolean f3181n = false;

    /* JADX WARN: $VALUES field not found */
    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* renamed from: com.alipay.sdk.authjs.a$a, reason: collision with other inner class name */
    public static final class EnumC0026a {

        /* renamed from: a, reason: collision with root package name */
        public static final int f3182a = 1;

        /* renamed from: b, reason: collision with root package name */
        public static final int f3183b = 2;

        /* renamed from: c, reason: collision with root package name */
        public static final int f3184c = 3;

        /* renamed from: d, reason: collision with root package name */
        public static final int f3185d = 4;

        /* renamed from: e, reason: collision with root package name */
        public static final int f3186e = 5;

        /* renamed from: f, reason: collision with root package name */
        private static final /* synthetic */ int[] f3187f = {1, 2, 3, 4, 5};

        private EnumC0026a(String str, int i2) {
        }

        public static int[] a() {
            return (int[]) f3187f.clone();
        }
    }

    public a(String str) {
        this.f3179l = str;
    }

    private static String a(int i2) {
        int i3 = b.f3188a[i2 - 1];
        return i3 != 1 ? i3 != 2 ? i3 != 3 ? "none" : "runtime error" : "invalid parameter" : "function not found";
    }

    private String b() {
        return this.f3176i;
    }

    private String c() {
        return this.f3177j;
    }

    private String d() {
        return this.f3178k;
    }

    private String e() {
        return this.f3179l;
    }

    private JSONObject f() {
        return this.f3180m;
    }

    private String g() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put(f3172e, this.f3176i);
        jSONObject.put(f3174g, this.f3178k);
        jSONObject.put("param", this.f3180m);
        jSONObject.put(f3175h, this.f3179l);
        return jSONObject.toString();
    }

    private boolean a() {
        return this.f3181n;
    }

    private void b(String str) {
        this.f3177j = str;
    }

    private void c(String str) {
        this.f3178k = str;
    }

    private void d(String str) {
        this.f3179l = str;
    }

    private void a(boolean z2) {
        this.f3181n = z2;
    }

    private void a(String str) {
        this.f3176i = str;
    }

    private void a(JSONObject jSONObject) {
        this.f3180m = jSONObject;
    }
}
