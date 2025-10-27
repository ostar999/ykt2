package com.contrarywind.timer;

import android.os.Handler;
import android.os.Message;
import com.contrarywind.view.WheelView;

/* loaded from: classes3.dex */
public final class MessageHandler extends Handler {
    public static final int WHAT_INVALIDATE_LOOP_VIEW = 1000;
    public static final int WHAT_ITEM_SELECTED = 3000;
    public static final int WHAT_SMOOTH_SCROLL = 2000;
    private final WheelView wheelView;

    public MessageHandler(WheelView wheelView) {
        this.wheelView = wheelView;
    }

    @Override // android.os.Handler
    public final void handleMessage(Message message) {
        int i2 = message.what;
        if (i2 == 1000) {
            this.wheelView.invalidate();
        } else if (i2 == 2000) {
            this.wheelView.smoothScroll(WheelView.ACTION.FLING);
        } else {
            if (i2 != 3000) {
                return;
            }
            this.wheelView.onItemSelected();
        }
    }
}
