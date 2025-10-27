package com.plv.livescenes.previous;

import com.plv.livescenes.chatroom.PLVChatApiRequestHelper;

/* loaded from: classes5.dex */
public class PLVPreviousManager {
    private static final String TAG = "PLVPreviousManager";
    private static PLVPreviousManager plvPreviousManager;

    private PLVPreviousManager() {
    }

    public static PLVPreviousManager getInstance() {
        if (plvPreviousManager == null) {
            synchronized (PLVPreviousManager.class) {
                if (plvPreviousManager == null) {
                    plvPreviousManager = new PLVPreviousManager();
                }
            }
        }
        return plvPreviousManager;
    }

    public PLVChatApiRequestHelper getPLVChatApi() {
        return PLVChatApiRequestHelper.getInstance();
    }
}
