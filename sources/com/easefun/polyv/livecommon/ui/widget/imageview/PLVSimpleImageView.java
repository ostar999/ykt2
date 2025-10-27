package com.easefun.polyv.livecommon.ui.widget.imageview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import androidx.annotation.Nullable;

/* loaded from: classes3.dex */
public class PLVSimpleImageView extends ImageView {
    private IPLVVisibilityChangedListener visibilityChangedListener;

    public PLVSimpleImageView(Context context) {
        super(context);
    }

    @Override // android.widget.ImageView, android.view.View
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        IPLVVisibilityChangedListener iPLVVisibilityChangedListener = this.visibilityChangedListener;
        if (iPLVVisibilityChangedListener != null) {
            iPLVVisibilityChangedListener.onChanged(visibility);
        }
    }

    public void setVisibilityChangedListener(IPLVVisibilityChangedListener listener) {
        this.visibilityChangedListener = listener;
    }

    public PLVSimpleImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PLVSimpleImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
