package d;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import c.e;
import c.h;
import h.g;

/* loaded from: classes8.dex */
public class b {

    /* renamed from: a, reason: collision with root package name */
    public static final String f26713a = "RtcEnvHelper";

    /* renamed from: b, reason: collision with root package name */
    public static Application f26714b;

    public static class a {

        /* renamed from: a, reason: collision with root package name */
        public static boolean f26715a = true;

        /* renamed from: b, reason: collision with root package name */
        public static boolean f26716b = true;

        /* renamed from: c, reason: collision with root package name */
        public static boolean f26717c = false;

        /* renamed from: d, reason: collision with root package name */
        public static boolean f26718d = false;

        /* renamed from: e, reason: collision with root package name */
        public static int f26719e = 1;

        /* renamed from: f, reason: collision with root package name */
        public static int f26720f = 0;

        /* renamed from: g, reason: collision with root package name */
        public static int f26721g = 8;

        /* renamed from: h, reason: collision with root package name */
        public static boolean f26722h = false;

        /* renamed from: i, reason: collision with root package name */
        public static int f26723i = 0;

        /* renamed from: j, reason: collision with root package name */
        public static long f26724j = 10485760;

        /* renamed from: k, reason: collision with root package name */
        public static boolean f26725k = false;

        /* renamed from: l, reason: collision with root package name */
        public static int f26726l = 1;

        /* renamed from: m, reason: collision with root package name */
        public static String f26727m = "1";

        /* renamed from: n, reason: collision with root package name */
        public static boolean f26728n = false;

        /* renamed from: o, reason: collision with root package name */
        public static int f26729o = 0;

        /* renamed from: p, reason: collision with root package name */
        public static boolean f26730p = false;

        /* renamed from: q, reason: collision with root package name */
        public static boolean f26731q = false;

        /* renamed from: r, reason: collision with root package name */
        public static boolean f26732r = false;

        /* renamed from: s, reason: collision with root package name */
        public static int f26733s = 0;

        /* renamed from: t, reason: collision with root package name */
        public static boolean f26734t = true;

        /* renamed from: u, reason: collision with root package name */
        public static int f26735u = 0;

        /* renamed from: v, reason: collision with root package name */
        public static boolean f26736v = false;
    }

    public static void a(Context context) {
        Log.w(f26713a, "initEnv: " + context);
        e.a(context);
        if (context instanceof Application) {
            f26714b = (Application) context;
            Log.w(f26713a, "initEnv setApplication" + f26714b);
        }
    }

    public static void b(String str) {
        e.c(str);
    }

    public static void c(String str) {
        e.d(str);
    }

    public static void d(int i2) {
        h.a(i2);
    }

    public static void e(String str) {
        e.e(str);
    }

    public static void f(boolean z2) {
        a.f26715a = z2;
    }

    public static void g(int i2) {
        a.f26721g = i2;
    }

    public static void h(int i2) {
        e.c(i2);
    }

    public static void i(boolean z2) {
        a.f26725k = z2;
    }

    public static int j() {
        return a.f26721g;
    }

    public static long k() {
        return a.f26724j;
    }

    public static int l() {
        return e.f();
    }

    public static boolean m() {
        return a.f26725k;
    }

    public static int n() {
        return a.f26735u;
    }

    public static boolean o() {
        return a.f26730p;
    }

    public static int p() {
        return a.f26729o;
    }

    public static boolean q() {
        return a.f26734t;
    }

    public static boolean r() {
        return a.f26731q;
    }

    public static boolean s() {
        return a.f26717c;
    }

    public static boolean t() {
        return a.f26718d;
    }

    public static boolean u() {
        return a.f26715a;
    }

    public static boolean v() {
        return a.f26728n;
    }

    public static boolean w() {
        return true;
    }

    public static boolean x() {
        return a.f26716b;
    }

    public static void y() {
        e.a((Context) null);
    }

    public static void b(int i2) {
        a.f26726l = i2;
    }

    public static void c(int i2) {
        a.f26720f = i2;
    }

    public static int d() {
        return a.f26720f;
    }

    public static void e(int i2) {
        a.f26719e = i2;
    }

    public static void f(int i2) {
        a.f26723i = i2;
    }

    public static String g() {
        return a.f26727m;
    }

    public static int h() {
        return a.f26719e;
    }

    public static int i() {
        return a.f26723i;
    }

    public static void j(int i2) {
        a.f26729o = i2;
    }

    public static void k(boolean z2) {
        a.f26734t = z2;
    }

    public static void l(boolean z2) {
        a.f26716b = z2;
        h.a(z2);
    }

    public static void b(boolean z2) {
        a.f26732r = z2;
    }

    public static void c(boolean z2) {
        a.f26717c = z2;
    }

    public static void d(boolean z2) {
        a.f26718d = z2;
    }

    public static boolean e() {
        return a.f26732r;
    }

    public static boolean f() {
        return a.f26736v;
    }

    public static void g(boolean z2) {
        a.f26728n = z2;
    }

    public static void h(boolean z2) {
        a.f26722h = z2;
        a.f26715a = !z2;
    }

    public static void i(int i2) {
        a.f26735u = i2;
    }

    public static void j(boolean z2) {
        a.f26730p = z2;
    }

    public static int b() {
        return a.f26733s;
    }

    public static int c() {
        return a.f26726l;
    }

    public static void d(String str) {
        g.f27082h = str;
    }

    public static void e(boolean z2) {
        a.f26736v = z2;
    }

    public static Application a() {
        return f26714b;
    }

    public static void a(long j2) {
        a.f26724j = j2;
    }

    public static void a(String str) {
        a.f26727m = str;
    }

    public static void a(boolean z2) {
        a.f26731q = z2;
    }

    public static void a(int i2) {
        a.f26733s = i2;
    }
}
