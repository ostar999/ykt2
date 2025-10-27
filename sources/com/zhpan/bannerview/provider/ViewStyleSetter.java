package com.zhpan.bannerview.provider;

import android.view.View;
import androidx.annotation.RequiresApi;

/* loaded from: classes8.dex */
public class ViewStyleSetter {
    @RequiresApi(api = 21)
    public static void applyRoundCorner(View view, float f2) {
        if (view == null) {
            return;
        }
        view.setClipToOutline(true);
        view.setOutlineProvider(new RoundViewOutlineProvider(f2));
    }
}
