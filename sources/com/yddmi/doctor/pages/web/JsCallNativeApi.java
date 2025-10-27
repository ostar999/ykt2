package com.yddmi.doctor.pages.web;

import android.webkit.JavascriptInterface;
import com.catchpig.utils.ext.LogExtKt;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import com.yddmi.doctor.config.YddConfig;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\b\u0018\u00002\u00020\u0001:\u0001\rB\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0001H\u0007J\u0010\u0010\b\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0001H\u0007J\u0010\u0010\t\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0001H\u0007J\u0010\u0010\n\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0001H\u0007J\u000e\u0010\u000b\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\u0004R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lcom/yddmi/doctor/pages/web/JsCallNativeApi;", "", "()V", "mListener", "Lcom/yddmi/doctor/pages/web/JsCallNativeApi$OnJsCallListener;", "closeWeb", "", "str", "getAppParams", "hangupPayment", "openWxService", "setOnJsCallListener", ServiceSpecificExtraArgs.CastExtraArgs.LISTENER, "OnJsCallListener", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class JsCallNativeApi {

    @Nullable
    private OnJsCallListener mListener;

    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\u001a\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\b\u0010\u0006\u001a\u0004\u0018\u00010\u0001H&¨\u0006\u0007"}, d2 = {"Lcom/yddmi/doctor/pages/web/JsCallNativeApi$OnJsCallListener;", "", "onJsCall", "", "action", "", "data", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public interface OnJsCallListener {
        void onJsCall(@NotNull String action, @Nullable Object data);
    }

    @JavascriptInterface
    public final void closeWeb(@NotNull Object str) {
        Intrinsics.checkNotNullParameter(str, "str");
        LogExtKt.logd("closeWeb " + str, YddConfig.TAG);
        OnJsCallListener onJsCallListener = this.mListener;
        if (onJsCallListener != null) {
            onJsCallListener.onJsCall("closeWeb", str);
        }
    }

    @JavascriptInterface
    public final void getAppParams(@NotNull Object str) {
        Intrinsics.checkNotNullParameter(str, "str");
        LogExtKt.logd("getAppParams", YddConfig.TAG);
        OnJsCallListener onJsCallListener = this.mListener;
        if (onJsCallListener != null) {
            onJsCallListener.onJsCall("getAppParams", "");
        }
    }

    @JavascriptInterface
    public final void hangupPayment(@NotNull Object str) {
        Intrinsics.checkNotNullParameter(str, "str");
        LogExtKt.logd("hangupPayment", YddConfig.TAG);
        OnJsCallListener onJsCallListener = this.mListener;
        if (onJsCallListener != null) {
            onJsCallListener.onJsCall("hangupPayment", "");
        }
    }

    @JavascriptInterface
    public final void openWxService(@NotNull Object str) {
        Intrinsics.checkNotNullParameter(str, "str");
        LogExtKt.logd("openWxService", YddConfig.TAG);
        OnJsCallListener onJsCallListener = this.mListener;
        if (onJsCallListener != null) {
            onJsCallListener.onJsCall("openWxService", "");
        }
    }

    public final void setOnJsCallListener(@NotNull OnJsCallListener listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        this.mListener = listener;
    }
}
