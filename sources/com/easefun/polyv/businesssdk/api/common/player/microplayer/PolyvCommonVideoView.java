package com.easefun.polyv.businesssdk.api.common.player.microplayer;

import android.content.Context;
import android.util.AttributeSet;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.plv.business.api.common.player.PLVVideoViewListener;
import com.plv.business.api.common.player.microplayer.PLVCommonVideoView;

@Deprecated
/* loaded from: classes3.dex */
public abstract class PolyvCommonVideoView<R, T extends PLVVideoViewListener> extends PLVCommonVideoView<R, T> {
    public PolyvCommonVideoView(@NonNull Context context) {
        super(context);
    }

    public PolyvCommonVideoView(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public PolyvCommonVideoView(@NonNull Context context, @Nullable AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
    }
}
