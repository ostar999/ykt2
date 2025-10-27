package com.easefun.polyv.livecommon.ui.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import androidx.annotation.Nullable;
import com.easefun.polyv.livecommon.R;
import com.plv.thirdpart.blankj.utilcode.util.ScreenUtils;

/* loaded from: classes3.dex */
public class PLVOrientationSensibleLinearLayout extends LinearLayout {
    private Runnable onLandscape;
    private Runnable onPortrait;
    private boolean showOnLandscape;
    private boolean showOnPortrait;

    public PLVOrientationSensibleLinearLayout(Context context) {
        super(context);
        this.showOnPortrait = true;
        this.showOnLandscape = true;
        initView(null);
    }

    private void initView(@Nullable AttributeSet attributeSet) {
        parseAttrs(attributeSet);
        post(new Runnable() { // from class: com.easefun.polyv.livecommon.ui.widget.PLVOrientationSensibleLinearLayout.1
            @Override // java.lang.Runnable
            public void run() {
                PLVOrientationSensibleLinearLayout.this.updateVisibleStatus();
            }
        });
    }

    private void parseAttrs(@Nullable AttributeSet attributeSet) {
        if (attributeSet == null) {
            return;
        }
        TypedArray typedArrayObtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R.styleable.PLVOrientationSensibleLinearLayout);
        this.showOnPortrait = typedArrayObtainStyledAttributes.getBoolean(R.styleable.PLVOrientationSensibleLinearLayout_plv_show_on_portrait, this.showOnPortrait);
        this.showOnLandscape = typedArrayObtainStyledAttributes.getBoolean(R.styleable.PLVOrientationSensibleLinearLayout_plv_show_on_landscape, this.showOnLandscape);
        typedArrayObtainStyledAttributes.recycle();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateVisibleStatus() {
        boolean zIsLandscape = ScreenUtils.isLandscape();
        setVisibility((zIsLandscape && this.showOnLandscape) || (!zIsLandscape && this.showOnPortrait) ? 0 : 8);
    }

    @Override // android.view.View
    public void onConfigurationChanged(Configuration newConfig) {
        Runnable runnable;
        int i2 = newConfig.orientation;
        if (i2 == 1) {
            Runnable runnable2 = this.onPortrait;
            if (runnable2 != null) {
                runnable2.run();
            }
        } else if (i2 == 2 && (runnable = this.onLandscape) != null) {
            runnable.run();
        }
        updateVisibleStatus();
    }

    public void setOnLandscape(Runnable onLandscape) {
        this.onLandscape = onLandscape;
    }

    public void setOnPortrait(Runnable onPortrait) {
        this.onPortrait = onPortrait;
    }

    public PLVOrientationSensibleLinearLayout setShowOnLandscape(boolean showOnLandscape) {
        this.showOnLandscape = showOnLandscape;
        return this;
    }

    public PLVOrientationSensibleLinearLayout setShowOnPortrait(boolean showOnPortrait) {
        this.showOnPortrait = showOnPortrait;
        return this;
    }

    public PLVOrientationSensibleLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.showOnPortrait = true;
        this.showOnLandscape = true;
        initView(attrs);
    }

    public PLVOrientationSensibleLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.showOnPortrait = true;
        this.showOnLandscape = true;
        initView(attrs);
    }
}
