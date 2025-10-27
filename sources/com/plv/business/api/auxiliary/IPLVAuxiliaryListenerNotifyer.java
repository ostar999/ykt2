package com.plv.business.api.auxiliary;

import android.widget.ImageView;
import com.plv.business.api.common.player.PLVPlayError;

/* loaded from: classes4.dex */
public interface IPLVAuxiliaryListenerNotifyer {
    void nontifyAuxiliaryonVisibilityChange(boolean z2);

    void notifyAuxiliaryOnLoadImage(String str, ImageView imageView, String str2);

    void notifyAuxiliaryPlayAfterEndListener();

    void notifyAuxiliaryPlayBeforeEndListener(boolean z2);

    void notifyAuxiliaryPlayCompletion(int i2);

    void notifyAuxiliaryPlayError(PLVPlayError pLVPlayError);

    void notifyAuxiliaryonCountdown(int i2, int i3, int i4);
}
