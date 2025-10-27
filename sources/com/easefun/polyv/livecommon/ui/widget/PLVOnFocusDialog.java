package com.easefun.polyv.livecommon.ui.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/* loaded from: classes3.dex */
public class PLVOnFocusDialog extends Dialog {
    private OnWindowFocusChangeListener onWindowFocusChangeListener;

    public interface OnWindowFocusChangeListener {
        void onWindowFocusChanged(boolean hasFocus);
    }

    public PLVOnFocusDialog(@NonNull Context context) {
        super(context);
    }

    @Override // android.app.Dialog, android.view.Window.Callback
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        OnWindowFocusChangeListener onWindowFocusChangeListener = this.onWindowFocusChangeListener;
        if (onWindowFocusChangeListener != null) {
            onWindowFocusChangeListener.onWindowFocusChanged(hasFocus);
        }
    }

    public void setOnWindowFocusChangedListener(OnWindowFocusChangeListener listener) {
        this.onWindowFocusChangeListener = listener;
    }

    public PLVOnFocusDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    public PLVOnFocusDialog(@NonNull Context context, boolean cancelable, @Nullable DialogInterface.OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }
}
