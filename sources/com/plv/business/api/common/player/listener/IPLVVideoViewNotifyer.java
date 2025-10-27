package com.plv.business.api.common.player.listener;

import com.plv.business.api.common.player.PLVPlayError;

/* loaded from: classes4.dex */
public interface IPLVVideoViewNotifyer {
    void notifyOnBufferingUpdate(int i2);

    void notifyOnCompletion();

    boolean notifyOnError(int i2, int i3);

    boolean notifyOnError(PLVPlayError pLVPlayError);

    boolean notifyOnInfo(int i2, int i3);

    void notifyOnPrepared();

    void notifyOnPreparing();

    void notifyOnSEIRefresh(int i2, byte[] bArr);

    void notifyOnSeekComplete();

    void notifyOnVideoSizeChanged(int i2, int i3, int i4, int i5);
}
