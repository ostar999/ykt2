package com.youth.banner.util;

import android.content.res.Resources;
import android.graphics.Outline;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

/* loaded from: classes8.dex */
public class BannerUtils {
    public static int dp2px(float f2) {
        return (int) TypedValue.applyDimension(1, f2, Resources.getSystem().getDisplayMetrics());
    }

    public static int getRealPosition(boolean z2, int i2, int i3) {
        if (!z2) {
            return i2;
        }
        if (i2 == 0) {
            return i3 - 1;
        }
        if (i2 == i3 + 1) {
            return 0;
        }
        return i2 - 1;
    }

    public static View getView(@NonNull ViewGroup viewGroup, @LayoutRes int i2) {
        View viewInflate = LayoutInflater.from(viewGroup.getContext()).inflate(i2, viewGroup, false);
        ViewGroup.LayoutParams layoutParams = viewInflate.getLayoutParams();
        if (layoutParams.height != -1 || layoutParams.width != -1) {
            layoutParams.height = -1;
            layoutParams.width = -1;
            viewInflate.setLayoutParams(layoutParams);
        }
        return viewInflate;
    }

    @RequiresApi(api = 21)
    public static void setBannerRound(View view, final float f2) {
        view.setOutlineProvider(new ViewOutlineProvider() { // from class: com.youth.banner.util.BannerUtils.1
            @Override // android.view.ViewOutlineProvider
            public void getOutline(View view2, Outline outline) {
                outline.setRoundRect(0, 0, view2.getWidth(), view2.getHeight(), f2);
            }
        });
        view.setClipToOutline(true);
    }
}
