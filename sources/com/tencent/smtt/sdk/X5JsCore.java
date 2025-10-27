package com.tencent.smtt.sdk;

import android.content.Context;
import android.os.Looper;
import android.util.Log;
import com.tencent.smtt.export.external.jscore.interfaces.IX5JsVirtualMachine;
import java.nio.ByteBuffer;

/* loaded from: classes6.dex */
public class X5JsCore {

    /* renamed from: a, reason: collision with root package name */
    private static a f21103a;

    /* renamed from: b, reason: collision with root package name */
    private static a f21104b;

    /* renamed from: c, reason: collision with root package name */
    private static a f21105c;

    /* renamed from: d, reason: collision with root package name */
    private final Context f21106d;

    /* renamed from: e, reason: collision with root package name */
    private Object f21107e;

    /* renamed from: f, reason: collision with root package name */
    private WebView f21108f;

    public enum a {
        UNINITIALIZED,
        UNAVAILABLE,
        AVAILABLE
    }

    static {
        a aVar = a.UNINITIALIZED;
        f21103a = aVar;
        f21104b = aVar;
        f21105c = aVar;
    }

    @Deprecated
    public X5JsCore(Context context) {
        Object objA;
        this.f21107e = null;
        this.f21108f = null;
        this.f21106d = context;
        if (canUseX5JsCore(context) && (objA = a("createX5JavaBridge", new Class[]{Context.class}, context)) != null) {
            this.f21107e = objA;
            return;
        }
        Log.e("X5JsCore", "X5JsCore create X5JavaBridge failure, use fallback!");
        WebView webView = new WebView(context);
        this.f21108f = webView;
        webView.getSettings().setJavaScriptEnabled(true);
    }

    public static IX5JsVirtualMachine a(Context context, Looper looper) {
        Object objA;
        if (canUseX5JsCore(context) && (objA = a("createX5JsVirtualMachine", new Class[]{Context.class, Looper.class}, context, looper)) != null) {
            return (IX5JsVirtualMachine) objA;
        }
        Log.e("X5JsCore", "X5JsCore#createVirtualMachine failure!");
        return null;
    }

    public static Object a() {
        return a("currentContextData", new Class[0], new Object[0]);
    }

    private static Object a(String str, Class<?>[] clsArr, Object... objArr) {
        try {
            w wVarA = w.a();
            if (wVarA != null && wVarA.b()) {
                return wVarA.c().b().invokeStaticMethod("com.tencent.tbs.tbsshell.WebCoreProxy", str, clsArr, objArr);
            }
            Log.e("X5JsCore", "X5Jscore#" + str + " - x5CoreEngine is null or is not x5core.");
            return null;
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public static boolean canUseX5JsCore(Context context) {
        if (f21103a != a.UNINITIALIZED) {
            return f21103a == a.AVAILABLE;
        }
        f21103a = a.UNAVAILABLE;
        Object objA = a("canUseX5JsCore", new Class[]{Context.class}, context);
        if (objA == null || !(objA instanceof Boolean) || !((Boolean) objA).booleanValue()) {
            return false;
        }
        a("setJsValueFactory", new Class[]{Object.class}, JsValue.a());
        f21103a = a.AVAILABLE;
        return true;
    }

    public static boolean canUseX5JsCoreNewAPI(Context context) {
        if (f21105c != a.UNINITIALIZED) {
            return f21105c == a.AVAILABLE;
        }
        f21105c = a.UNAVAILABLE;
        Object objA = a("canUseX5JsCoreNewAPI", new Class[]{Context.class}, context);
        if (objA == null || !(objA instanceof Boolean) || !((Boolean) objA).booleanValue()) {
            return false;
        }
        f21105c = a.AVAILABLE;
        return true;
    }

    public static boolean canX5JsCoreUseNativeBuffer(Context context) {
        Object objA;
        if (f21104b != a.UNINITIALIZED) {
            return f21104b == a.AVAILABLE;
        }
        f21104b = a.UNAVAILABLE;
        if (!canUseX5JsCore(context) || (objA = a("canX5JsCoreUseBuffer", new Class[]{Context.class}, context)) == null || !(objA instanceof Boolean) || !((Boolean) objA).booleanValue()) {
            return false;
        }
        f21104b = a.AVAILABLE;
        return true;
    }

    @Deprecated
    public void addJavascriptInterface(Object obj, String str) {
        Object obj2 = this.f21107e;
        if (obj2 != null) {
            a("addJavascriptInterface", new Class[]{Object.class, String.class, Object.class}, obj, str, obj2);
            return;
        }
        WebView webView = this.f21108f;
        if (webView != null) {
            webView.addJavascriptInterface(obj, str);
            this.f21108f.loadUrl("about:blank");
        }
    }

    @Deprecated
    public void destroy() {
        Object obj = this.f21107e;
        if (obj != null) {
            a("destroyX5JsCore", new Class[]{Object.class}, obj);
            this.f21107e = null;
            return;
        }
        WebView webView = this.f21108f;
        if (webView != null) {
            webView.clearHistory();
            this.f21108f.clearCache(true);
            this.f21108f.loadUrl("about:blank");
            this.f21108f.freeMemory();
            this.f21108f.pauseTimers();
            this.f21108f.destroy();
            this.f21108f = null;
        }
    }

    @Deprecated
    public void evaluateJavascript(String str, ValueCallback<String> valueCallback) {
        Object obj = this.f21107e;
        if (obj != null) {
            a("evaluateJavascript", new Class[]{String.class, android.webkit.ValueCallback.class, Object.class}, str, valueCallback, obj);
            return;
        }
        WebView webView = this.f21108f;
        if (webView != null) {
            webView.evaluateJavascript(str, valueCallback);
        }
    }

    @Deprecated
    public ByteBuffer getNativeBuffer(int i2) {
        Object objA;
        if (this.f21107e == null || !canX5JsCoreUseNativeBuffer(this.f21106d) || (objA = a("getNativeBuffer", new Class[]{Object.class, Integer.TYPE}, this.f21107e, Integer.valueOf(i2))) == null || !(objA instanceof ByteBuffer)) {
            return null;
        }
        return (ByteBuffer) objA;
    }

    @Deprecated
    public int getNativeBufferId() {
        Object objA;
        if (this.f21107e == null || !canX5JsCoreUseNativeBuffer(this.f21106d) || (objA = a("getNativeBufferId", new Class[]{Object.class}, this.f21107e)) == null || !(objA instanceof Integer)) {
            return -1;
        }
        return ((Integer) objA).intValue();
    }

    @Deprecated
    public void pause() {
        Object obj = this.f21107e;
        if (obj != null) {
            a("pause", new Class[]{Object.class}, obj);
        }
    }

    @Deprecated
    public void pauseTimers() {
        Object obj = this.f21107e;
        if (obj != null) {
            a("pauseTimers", new Class[]{Object.class}, obj);
        }
    }

    @Deprecated
    public void removeJavascriptInterface(String str) {
        Object obj = this.f21107e;
        if (obj != null) {
            a("removeJavascriptInterface", new Class[]{String.class, Object.class}, str, obj);
            return;
        }
        WebView webView = this.f21108f;
        if (webView != null) {
            webView.removeJavascriptInterface(str);
        }
    }

    @Deprecated
    public void resume() {
        Object obj = this.f21107e;
        if (obj != null) {
            a("resume", new Class[]{Object.class}, obj);
        }
    }

    @Deprecated
    public void resumeTimers() {
        Object obj = this.f21107e;
        if (obj != null) {
            a("resumeTimers", new Class[]{Object.class}, obj);
        }
    }

    @Deprecated
    public void setNativeBuffer(int i2, ByteBuffer byteBuffer) {
        if (this.f21107e == null || !canX5JsCoreUseNativeBuffer(this.f21106d)) {
            return;
        }
        a("setNativeBuffer", new Class[]{Object.class, Integer.TYPE, ByteBuffer.class}, this.f21107e, Integer.valueOf(i2), byteBuffer);
    }
}
