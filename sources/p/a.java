package p;

import android.app.Activity;
import android.content.Intent;
import b.d;
import c.h;
import core.interfaces.DataProvider;
import core.interfaces.RtcNotification;
import f.e;

/* loaded from: classes.dex */
public final /* synthetic */ class a {
    public static b.a a(b.b bVar) {
        d dVarK = d.k(0);
        dVarK.b(bVar);
        return dVarK;
    }

    public static String b() {
        return "1.1.7_ cbe5c583";
    }

    public static void c(DataProvider dataProvider) {
        h.a(d.H, "onScreenCaptureResult CoreRtcEngineImpl.getInstance");
        d.k(0).b(dataProvider);
    }

    public static void d(RtcNotification rtcNotification) {
        h.a(d.H, "regScreenCaptureNotification CoreRtcEngineImpl.getInstance");
        d.k(0).b(rtcNotification);
    }

    public static b.a e() {
        return d.k(0);
    }

    public static void f() {
        h.a(d.H, "destroy engine start");
        d.destroy();
        h.a(d.H, "destroy engine finish");
        h.f();
    }

    public static void g(String str) {
        e.f26883l = str;
        e.n();
    }

    public static void h(Intent intent) {
        h.a(d.H, "onScreenCaptureResult CoreRtcEngineImpl.getInstance");
        d.k(0).a(intent);
    }

    public static void i(Activity activity) {
        h.a(d.H, "requestScreenCapture CoreRtcEngineImpl.getInstance");
        d.k(0).requestScreenCapture(activity);
    }
}
