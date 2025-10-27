package com.gxx.audioplaylibrary.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import com.huawei.hms.support.api.entity.core.CommonCode;
import com.umeng.analytics.pro.d;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001:\u0001\rB\u0005¢\u0006\u0002\u0010\u0002J\u001c\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\b2\b\u0010\t\u001a\u0004\u0018\u00010\nH\u0016J\u000e\u0010\u000b\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\u0004R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lcom/gxx/audioplaylibrary/broadcastreceiver/HeadsetChangeReceiver;", "Landroid/content/BroadcastReceiver;", "()V", "mOnHeadsetChangeReceiverListener", "Lcom/gxx/audioplaylibrary/broadcastreceiver/HeadsetChangeReceiver$OnHeadsetChangeReceiverListener;", "onReceive", "", d.R, "Landroid/content/Context;", CommonCode.Resolution.HAS_RESOLUTION_FROM_APK, "Landroid/content/Intent;", "setOnHeadsetChangeReceiverListener", ServiceSpecificExtraArgs.CastExtraArgs.LISTENER, "OnHeadsetChangeReceiverListener", "mvvm_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes4.dex */
public final class HeadsetChangeReceiver extends BroadcastReceiver {

    @Nullable
    private OnHeadsetChangeReceiverListener mOnHeadsetChangeReceiverListener;

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&¨\u0006\u0006"}, d2 = {"Lcom/gxx/audioplaylibrary/broadcastreceiver/HeadsetChangeReceiver$OnHeadsetChangeReceiverListener;", "", "onHeadsetChangeReceiver", "", "isEarpiece", "", "mvvm_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public interface OnHeadsetChangeReceiverListener {
        void onHeadsetChangeReceiver(boolean isEarpiece);
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(@Nullable Context context, @Nullable Intent intent) {
        OnHeadsetChangeReceiverListener onHeadsetChangeReceiverListener;
        if (intent != null && Intrinsics.areEqual(intent.getAction(), "android.intent.action.HEADSET_PLUG")) {
            int intExtra = intent.getIntExtra("state", 0);
            if (intExtra != 0) {
                if (intExtra == 1 && (onHeadsetChangeReceiverListener = this.mOnHeadsetChangeReceiverListener) != null) {
                    onHeadsetChangeReceiverListener.onHeadsetChangeReceiver(true);
                    return;
                }
                return;
            }
            OnHeadsetChangeReceiverListener onHeadsetChangeReceiverListener2 = this.mOnHeadsetChangeReceiverListener;
            if (onHeadsetChangeReceiverListener2 != null) {
                onHeadsetChangeReceiverListener2.onHeadsetChangeReceiver(false);
            }
        }
    }

    public final void setOnHeadsetChangeReceiverListener(@NotNull OnHeadsetChangeReceiverListener listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        this.mOnHeadsetChangeReceiverListener = listener;
    }
}
