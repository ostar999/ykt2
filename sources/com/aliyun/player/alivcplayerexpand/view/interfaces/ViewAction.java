package com.aliyun.player.alivcplayerexpand.view.interfaces;

import com.aliyun.player.aliyunplayerbase.util.AliyunScreenMode;

/* loaded from: classes2.dex */
public interface ViewAction {

    public enum HideType {
        Normal,
        End
    }

    void hide(HideType hideType);

    void reset();

    void setScreenModeStatus(AliyunScreenMode aliyunScreenMode);

    void show();
}
