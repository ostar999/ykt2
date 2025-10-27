package com.easefun.polyv.livecommon.ui.widget.blurview;

import android.R;
import android.app.Activity;
import android.view.ViewGroup;

/* loaded from: classes3.dex */
public class PLVBlurUtils {
    public static void initBlurView(PLVBlurView blurView) {
        blurView.setupWith((ViewGroup) ((Activity) blurView.getContext()).findViewById(R.id.content)).setFrameClearDrawable(null).setBlurAlgorithm(new SupportRenderScriptBlur(blurView.getContext())).setBlurRadius(1.0f).setHasFixedTransformationMatrix(false);
    }
}
