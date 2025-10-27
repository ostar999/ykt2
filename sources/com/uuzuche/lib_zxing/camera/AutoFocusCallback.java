package com.uuzuche.lib_zxing.camera;

import android.hardware.Camera;
import android.os.Handler;
import android.util.Log;

/* loaded from: classes6.dex */
public final class AutoFocusCallback implements Camera.AutoFocusCallback {
    private static final long AUTOFOCUS_INTERVAL_MS = 1500;
    private static final String TAG = "AutoFocusCallback";
    private Handler autoFocusHandler;
    private int autoFocusMessage;

    @Override // android.hardware.Camera.AutoFocusCallback
    public void onAutoFocus(boolean z2, Camera camera) {
        Handler handler = this.autoFocusHandler;
        if (handler == null) {
            Log.d(TAG, "Got auto-focus callback, but no handler for it");
            return;
        }
        this.autoFocusHandler.sendMessageDelayed(handler.obtainMessage(this.autoFocusMessage, Boolean.valueOf(z2)), AUTOFOCUS_INTERVAL_MS);
        this.autoFocusHandler = null;
    }

    public void setHandler(Handler handler, int i2) {
        this.autoFocusHandler = handler;
        this.autoFocusMessage = i2;
    }
}
