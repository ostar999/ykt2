package com.easefun.polyv.livecommon.module.modules.player;

import androidx.annotation.DrawableRes;
import com.plv.foundationsdk.annos.Sp;

/* loaded from: classes3.dex */
public interface IPLVPlayErrorView {
    void setChangeLinesViewVisibility(int visibility);

    void setPlaceHolderImg(@DrawableRes int resId);

    void setPlaceHolderText(String text);

    void setPlaceHolderTextSize(@Sp float size);

    void setRefreshViewVisibility(int visibility);

    void setViewVisibility(int visibility);
}
