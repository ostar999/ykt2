package com.easefun.polyv.businesssdk.api.common.player;

import android.content.Context;
import android.util.AttributeSet;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.plv.business.api.common.player.PLVBaseVideoView;
import com.plv.business.api.common.player.PLVVideoViewListener;

@Deprecated
/* loaded from: classes3.dex */
public abstract class PolyvBaseVideoView<T extends PLVVideoViewListener> extends PLVBaseVideoView<T> {
    public PolyvBaseVideoView(@NonNull Context context) {
        super(context);
    }

    public PolyvBaseVideoView(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public PolyvBaseVideoView(@NonNull Context context, @Nullable AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
    }
}
