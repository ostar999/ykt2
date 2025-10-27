package com.plv.business.api.auxiliary;

import android.widget.ImageView;
import androidx.annotation.MainThread;
import com.plv.business.api.common.player.PLVPlayError;
import com.plv.business.api.common.player.listener.IPLVVideoViewListenerEvent;

/* loaded from: classes4.dex */
public interface IPLVAuxiliaryVideoViewListenerEvent extends IPLVVideoViewListenerEvent {

    public interface IPLVAuxliaryVideoViewPlayStatusListener {
        void onCompletion(int i2);

        void onCountdown(int i2, int i3, int i4);

        void onError(PLVPlayError pLVPlayError);
    }

    public interface IPLVOnAuxiliaryPlayEndListener {
        @MainThread
        void onAfterEnd();

        @MainThread
        void onBeforeEnd(boolean z2);
    }

    public interface IPLVOnSubVideoViewCountdownListener {
        void onCountdown(int i2, int i3, int i4);

        void onVisibilityChange(boolean z2);
    }

    public interface IPLVOnSubVideoViewLoadImage {
        void onLoad(String str, ImageView imageView, String str2);
    }
}
