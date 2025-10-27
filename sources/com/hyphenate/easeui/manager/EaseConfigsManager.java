package com.hyphenate.easeui.manager;

import android.content.Context;
import android.content.res.Resources;
import com.hyphenate.easeui.R;

/* loaded from: classes4.dex */
public class EaseConfigsManager {
    private Context context;

    public EaseConfigsManager(Context context) {
        this.context = context;
    }

    public boolean enableSendChannelAck() {
        try {
            return this.context.getResources().getBoolean(R.bool.ease_enable_send_channel_ack);
        } catch (Resources.NotFoundException e2) {
            e2.printStackTrace();
            return false;
        }
    }
}
