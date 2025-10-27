package com.tencent.smtt.sdk;

import com.tencent.smtt.export.external.jscore.interfaces.IX5JsValue;
import java.nio.ByteBuffer;

/* loaded from: classes6.dex */
public class JsValue {

    /* renamed from: a, reason: collision with root package name */
    private final JsContext f20806a;

    /* renamed from: b, reason: collision with root package name */
    private final IX5JsValue f20807b;

    public static class a implements IX5JsValue.JsValueFactory {
        private a() {
        }

        @Override // com.tencent.smtt.export.external.jscore.interfaces.IX5JsValue.JsValueFactory
        public String getJsValueClassName() {
            return JsValue.class.getName();
        }

        @Override // com.tencent.smtt.export.external.jscore.interfaces.IX5JsValue.JsValueFactory
        public IX5JsValue unwrap(Object obj) {
            if (obj == null || !(obj instanceof JsValue)) {
                return null;
            }
            return ((JsValue) obj).f20807b;
        }

        @Override // com.tencent.smtt.export.external.jscore.interfaces.IX5JsValue.JsValueFactory
        public Object wrap(IX5JsValue iX5JsValue) {
            JsContext jsContextCurrent;
            if (iX5JsValue == null || (jsContextCurrent = JsContext.current()) == null) {
                return null;
            }
            return new JsValue(jsContextCurrent, iX5JsValue);
        }
    }

    public JsValue(JsContext jsContext, IX5JsValue iX5JsValue) {
        this.f20806a = jsContext;
        this.f20807b = iX5JsValue;
    }

    public static IX5JsValue.JsValueFactory a() {
        return new a();
    }

    private JsValue a(IX5JsValue iX5JsValue) {
        if (iX5JsValue == null) {
            return null;
        }
        return new JsValue(this.f20806a, iX5JsValue);
    }

    public JsValue call(Object... objArr) {
        return a(this.f20807b.call(objArr));
    }

    public JsValue construct(Object... objArr) {
        return a(this.f20807b.construct(objArr));
    }

    public JsContext context() {
        return this.f20806a;
    }

    public boolean isArray() {
        return this.f20807b.isArray();
    }

    public boolean isArrayBufferOrArrayBufferView() {
        return this.f20807b.isArrayBufferOrArrayBufferView();
    }

    public boolean isBoolean() {
        return this.f20807b.isBoolean();
    }

    public boolean isFunction() {
        return this.f20807b.isFunction();
    }

    public boolean isInteger() {
        return this.f20807b.isInteger();
    }

    public boolean isJavascriptInterface() {
        return this.f20807b.isJavascriptInterface();
    }

    public boolean isNull() {
        return this.f20807b.isNull();
    }

    public boolean isNumber() {
        return this.f20807b.isNumber();
    }

    public boolean isObject() {
        return this.f20807b.isObject();
    }

    public boolean isPromise() {
        return this.f20807b.isPromise();
    }

    public boolean isString() {
        return this.f20807b.isString();
    }

    public boolean isUndefined() {
        return this.f20807b.isUndefined();
    }

    public void reject(Object obj) {
        this.f20807b.resolveOrReject(obj, false);
    }

    public void resolve(Object obj) {
        this.f20807b.resolveOrReject(obj, true);
    }

    public boolean toBoolean() {
        return this.f20807b.toBoolean();
    }

    public ByteBuffer toByteBuffer() {
        return this.f20807b.toByteBuffer();
    }

    public int toInteger() {
        return this.f20807b.toInteger();
    }

    public Object toJavascriptInterface() {
        return this.f20807b.toJavascriptInterface();
    }

    public Number toNumber() {
        return this.f20807b.toNumber();
    }

    public <T> T toObject(Class<T> cls) {
        return (T) this.f20807b.toObject(cls);
    }

    public String toString() {
        return this.f20807b.toString();
    }
}
