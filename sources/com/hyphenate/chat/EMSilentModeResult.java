package com.hyphenate.chat;

import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMPushManager;
import com.hyphenate.chat.adapter.EMASilentModeItem;

/* loaded from: classes4.dex */
public class EMSilentModeResult extends EMBase<EMASilentModeItem> {
    /* JADX WARN: Multi-variable type inference failed */
    public EMSilentModeResult(EMASilentModeItem eMASilentModeItem) {
        this.emaObject = eMASilentModeItem;
    }

    @Override // com.hyphenate.chat.EMBase
    public /* bridge */ /* synthetic */ boolean equals(Object obj) {
        return super.equals(obj);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public String getConversationId() {
        return ((EMASilentModeItem) this.emaObject).getConversationId();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public EMConversation.EMConversationType getConversationType() {
        return EMConversation.EMConversationType.values()[((EMASilentModeItem) this.emaObject).getConversationType()];
    }

    /* JADX WARN: Multi-variable type inference failed */
    public long getExpireTimestamp() {
        return ((EMASilentModeItem) this.emaObject).getExpireTimestamp();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public EMPushManager.EMPushRemindType getRemindType() {
        return ((EMASilentModeItem) this.emaObject).getRemindType() != 0 ? EMPushManager.EMPushRemindType.values()[((EMASilentModeItem) this.emaObject).getRemindType() - 1] : EMPushManager.EMPushRemindType.ALL;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public EMSilentModeTime getSilentModeEndTime() {
        return new EMSilentModeTime(((EMASilentModeItem) this.emaObject).getSilentModeEndTime());
    }

    /* JADX WARN: Multi-variable type inference failed */
    public EMSilentModeTime getSilentModeStartTime() {
        return new EMSilentModeTime(((EMASilentModeItem) this.emaObject).getSilentModeStartTime());
    }

    @Override // com.hyphenate.chat.EMBase
    public /* bridge */ /* synthetic */ int hashCode() {
        return super.hashCode();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public boolean isConversationRemindTypeEnabled() {
        return ((EMASilentModeItem) this.emaObject).getRemindType() != 0;
    }
}
