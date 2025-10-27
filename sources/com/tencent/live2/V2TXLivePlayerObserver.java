package com.tencent.live2;

import android.graphics.Bitmap;
import android.os.Bundle;
import com.tencent.live2.V2TXLiveDef;

/* loaded from: classes6.dex */
public abstract class V2TXLivePlayerObserver {
    public void onAudioPlayStatusUpdate(V2TXLivePlayer v2TXLivePlayer, V2TXLiveDef.V2TXLivePlayStatus v2TXLivePlayStatus, V2TXLiveDef.V2TXLiveStatusChangeReason v2TXLiveStatusChangeReason, Bundle bundle) {
    }

    public void onError(V2TXLivePlayer v2TXLivePlayer, int i2, String str, Bundle bundle) {
    }

    public void onPlayoutVolumeUpdate(V2TXLivePlayer v2TXLivePlayer, int i2) {
    }

    public void onReceiveSeiMessage(V2TXLivePlayer v2TXLivePlayer, int i2, byte[] bArr) {
    }

    public void onRenderVideoFrame(V2TXLivePlayer v2TXLivePlayer, V2TXLiveDef.V2TXLiveVideoFrame v2TXLiveVideoFrame) {
    }

    public void onSnapshotComplete(V2TXLivePlayer v2TXLivePlayer, Bitmap bitmap) {
    }

    public void onStatisticsUpdate(V2TXLivePlayer v2TXLivePlayer, V2TXLiveDef.V2TXLivePlayerStatistics v2TXLivePlayerStatistics) {
    }

    public void onVideoPlayStatusUpdate(V2TXLivePlayer v2TXLivePlayer, V2TXLiveDef.V2TXLivePlayStatus v2TXLivePlayStatus, V2TXLiveDef.V2TXLiveStatusChangeReason v2TXLiveStatusChangeReason, Bundle bundle) {
    }

    public void onWarning(V2TXLivePlayer v2TXLivePlayer, int i2, String str, Bundle bundle) {
    }
}
