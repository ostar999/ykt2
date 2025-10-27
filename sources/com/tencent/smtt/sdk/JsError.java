package com.tencent.smtt.sdk;

import com.tencent.smtt.export.external.jscore.interfaces.IX5JsError;

/* loaded from: classes6.dex */
public class JsError {

    /* renamed from: a, reason: collision with root package name */
    private final IX5JsError f20805a;

    public JsError(IX5JsError iX5JsError) {
        this.f20805a = iX5JsError;
    }

    public String getMessage() {
        return this.f20805a.getMessage();
    }

    public String getStack() {
        return this.f20805a.getStack();
    }
}
