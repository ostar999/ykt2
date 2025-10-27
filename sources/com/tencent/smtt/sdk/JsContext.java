package com.tencent.smtt.sdk;

import android.content.Context;
import com.tencent.smtt.export.external.jscore.interfaces.IX5JsContext;
import com.tencent.smtt.export.external.jscore.interfaces.IX5JsError;
import com.tencent.smtt.export.external.jscore.interfaces.IX5JsValue;
import java.net.URL;

/* loaded from: classes6.dex */
public final class JsContext {

    /* renamed from: a, reason: collision with root package name */
    private final JsVirtualMachine f20798a;

    /* renamed from: b, reason: collision with root package name */
    private final IX5JsContext f20799b;

    /* renamed from: c, reason: collision with root package name */
    private ExceptionHandler f20800c;

    /* renamed from: d, reason: collision with root package name */
    private String f20801d;

    public interface ExceptionHandler {
        void handleException(JsContext jsContext, JsError jsError);
    }

    public JsContext(Context context) {
        this(new JsVirtualMachine(context));
    }

    public JsContext(JsVirtualMachine jsVirtualMachine) {
        if (jsVirtualMachine == null) {
            throw new IllegalArgumentException("The virtualMachine value can not be null");
        }
        this.f20798a = jsVirtualMachine;
        IX5JsContext iX5JsContextA = jsVirtualMachine.a();
        this.f20799b = iX5JsContextA;
        try {
            iX5JsContextA.setPerContextData(this);
        } catch (AbstractMethodError unused) {
        }
    }

    public static JsContext current() {
        return (JsContext) X5JsCore.a();
    }

    public void addJavascriptInterface(Object obj, String str) {
        this.f20799b.addJavascriptInterface(obj, str);
    }

    public void destroy() {
        this.f20799b.destroy();
    }

    public void evaluateJavascript(String str, android.webkit.ValueCallback<String> valueCallback) {
        evaluateJavascript(str, valueCallback, null);
    }

    public void evaluateJavascript(String str, android.webkit.ValueCallback<String> valueCallback, URL url) {
        this.f20799b.evaluateJavascript(str, valueCallback, url);
    }

    public JsValue evaluateScript(String str) {
        return evaluateScript(str, null);
    }

    public JsValue evaluateScript(String str, URL url) {
        IX5JsValue iX5JsValueEvaluateScript = this.f20799b.evaluateScript(str, url);
        if (iX5JsValueEvaluateScript == null) {
            return null;
        }
        return new JsValue(this, iX5JsValueEvaluateScript);
    }

    public void evaluateScriptAsync(String str, final android.webkit.ValueCallback<JsValue> valueCallback, URL url) {
        this.f20799b.evaluateScriptAsync(str, valueCallback == null ? null : new android.webkit.ValueCallback<IX5JsValue>() { // from class: com.tencent.smtt.sdk.JsContext.1
            @Override // android.webkit.ValueCallback
            /* renamed from: a, reason: merged with bridge method [inline-methods] */
            public void onReceiveValue(IX5JsValue iX5JsValue) {
                valueCallback.onReceiveValue(iX5JsValue == null ? null : new JsValue(JsContext.this, iX5JsValue));
            }
        }, url);
    }

    public ExceptionHandler exceptionHandler() {
        return this.f20800c;
    }

    public byte[] getNativeBuffer(int i2) {
        return this.f20799b.getNativeBuffer(i2);
    }

    public int getNativeBufferId() {
        return this.f20799b.getNativeBufferId();
    }

    public String name() {
        return this.f20801d;
    }

    public void removeJavascriptInterface(String str) {
        this.f20799b.removeJavascriptInterface(str);
    }

    public void setExceptionHandler(ExceptionHandler exceptionHandler) {
        IX5JsContext iX5JsContext;
        android.webkit.ValueCallback<IX5JsError> valueCallback;
        this.f20800c = exceptionHandler;
        if (exceptionHandler == null) {
            iX5JsContext = this.f20799b;
            valueCallback = null;
        } else {
            iX5JsContext = this.f20799b;
            valueCallback = new android.webkit.ValueCallback<IX5JsError>() { // from class: com.tencent.smtt.sdk.JsContext.2
                @Override // android.webkit.ValueCallback
                /* renamed from: a, reason: merged with bridge method [inline-methods] */
                public void onReceiveValue(IX5JsError iX5JsError) {
                    JsContext.this.f20800c.handleException(JsContext.this, new JsError(iX5JsError));
                }
            };
        }
        iX5JsContext.setExceptionHandler(valueCallback);
    }

    public void setName(String str) {
        this.f20801d = str;
        this.f20799b.setName(str);
    }

    public int setNativeBuffer(int i2, byte[] bArr) {
        return this.f20799b.setNativeBuffer(i2, bArr);
    }

    public void stealValueFromOtherCtx(String str, JsContext jsContext, String str2) {
        this.f20799b.stealValueFromOtherCtx(str, jsContext.f20799b, str2);
    }

    public JsVirtualMachine virtualMachine() {
        return this.f20798a;
    }
}
