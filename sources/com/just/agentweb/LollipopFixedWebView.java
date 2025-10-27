package com.just.agentweb;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

/* loaded from: classes4.dex */
public class LollipopFixedWebView extends WebView {
    public LollipopFixedWebView(Context context) {
        super(getFixedContext(context));
    }

    public LollipopFixedWebView(Context context, AttributeSet attributeSet) {
        super(getFixedContext(context), attributeSet);
    }

    public LollipopFixedWebView(Context context, AttributeSet attributeSet, int i2) {
        super(getFixedContext(context), attributeSet, i2);
    }

    @TargetApi(21)
    public LollipopFixedWebView(Context context, AttributeSet attributeSet, int i2, int i3) {
        super(getFixedContext(context), attributeSet, i2, i3);
    }

    public LollipopFixedWebView(Context context, AttributeSet attributeSet, int i2, boolean z2) {
        super(getFixedContext(context), attributeSet, i2, z2);
    }

    public static Context getFixedContext(Context context) {
        return context;
    }
}
