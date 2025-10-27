package com.hyphenate.chat;

import com.hyphenate.chat.EMPushManager;
import com.hyphenate.chat.adapter.EMAPushConfigs;

/* loaded from: classes4.dex */
public class EMPushConfigs extends EMBase<EMAPushConfigs> {
    /* JADX WARN: Multi-variable type inference failed */
    public EMPushConfigs(EMAPushConfigs eMAPushConfigs) {
        this.emaObject = eMAPushConfigs;
    }

    @Override // com.hyphenate.chat.EMBase
    public /* bridge */ /* synthetic */ boolean equals(Object obj) {
        return super.equals(obj);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public String getDisplayNickname() {
        return ((EMAPushConfigs) this.emaObject).getDisplayNickname();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public EMPushManager.DisplayStyle getDisplayStyle() {
        return EMPushManager.DisplayStyle.values()[((EMAPushConfigs) this.emaObject).getDisplayStyle()];
    }

    @Deprecated
    public int getNoDisturbEndHour() {
        return getSilentModeEnd();
    }

    @Deprecated
    public int getNoDisturbStartHour() {
        return getSilentModeStart();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Deprecated
    public int getSilentModeEnd() {
        return ((EMAPushConfigs) this.emaObject).getNoDisturbEndHour();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Deprecated
    public int getSilentModeStart() {
        return ((EMAPushConfigs) this.emaObject).getNoDisturbStartHour();
    }

    @Override // com.hyphenate.chat.EMBase
    public /* bridge */ /* synthetic */ int hashCode() {
        return super.hashCode();
    }

    @Deprecated
    public boolean isNoDisturbOn() {
        return silentModeEnabled();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Deprecated
    public boolean silentModeEnabled() {
        return ((EMAPushConfigs) this.emaObject).isNoDisturbOn();
    }
}
