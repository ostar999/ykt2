package com.hjq.toast;

import android.os.Handler;
import android.os.Message;
import android.view.WindowManager;

/* loaded from: classes4.dex */
final class SafeHandler extends Handler {
    private final Handler mHandler;

    public SafeHandler(Handler handler) {
        this.mHandler = handler;
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
        try {
            this.mHandler.handleMessage(message);
        } catch (WindowManager.BadTokenException | IllegalStateException e2) {
            e2.printStackTrace();
        }
    }
}
