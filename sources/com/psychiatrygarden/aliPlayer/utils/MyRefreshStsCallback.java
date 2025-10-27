package com.psychiatrygarden.aliPlayer.utils;

import com.aliyun.player.alivcplayerexpand.listener.RefreshStsCallback;
import com.aliyun.player.source.VidSts;

/* loaded from: classes5.dex */
public class MyRefreshStsCallback implements RefreshStsCallback {
    @Override // com.aliyun.player.alivcplayerexpand.listener.RefreshStsCallback
    public VidSts refreshSts(String vid, String quality, String format, String title, boolean encript) {
        VidSts vidSts = AliPlayUtils.getVidSts(vid);
        if (vidSts == null) {
            return null;
        }
        vidSts.setVid(vid);
        vidSts.setQuality(quality, false);
        vidSts.setTitle(title);
        return vidSts;
    }
}
