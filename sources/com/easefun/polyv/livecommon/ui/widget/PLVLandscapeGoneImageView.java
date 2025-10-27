package com.easefun.polyv.livecommon.ui.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.widget.ImageView;
import androidx.annotation.Nullable;
import com.plv.thirdpart.blankj.utilcode.util.ScreenUtils;

/* loaded from: classes3.dex */
public class PLVLandscapeGoneImageView extends ImageView {
    public PLVLandscapeGoneImageView(Context context) {
        this(context, null);
    }

    @Override // android.view.View
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == 2) {
            setVisibility(8);
        } else {
            setVisibility(0);
        }
    }

    public PLVLandscapeGoneImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PLVLandscapeGoneImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setVisibility(ScreenUtils.isLandscape() ? 8 : 0);
    }
}
