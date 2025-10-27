package com.angcyo.tablayout;

import android.view.View;
import android.widget.TextView;
import kotlin.Metadata;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0016\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J*\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\u000bH\u0016J*\u0010\f\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\u000bH\u0016J*\u0010\r\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u000e\u001a\u00020\u000b2\u0006\u0010\u000f\u001a\u00020\u000b2\u0006\u0010\n\u001a\u00020\u000bH\u0016J*\u0010\u0010\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u00112\u0006\u0010\u0012\u001a\u00020\u000b2\u0006\u0010\u0013\u001a\u00020\u000b2\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u001a\u0010\u0014\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0015\u001a\u00020\bH\u0016¨\u0006\u0016"}, d2 = {"Lcom/angcyo/tablayout/TabGradientCallback;", "", "()V", "onGradientColor", "", "view", "Landroid/view/View;", "startColor", "", "endColor", "percent", "", "onGradientIcoColor", "onGradientScale", "startScale", "endScale", "onGradientTextSize", "Landroid/widget/TextView;", "startTextSize", "endTextSize", "onUpdateIcoColor", "color", "TabLayout_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes2.dex */
public class TabGradientCallback {
    public void onGradientColor(@Nullable View view, int startColor, int endColor, float percent) {
        TextView textView = view instanceof TextView ? (TextView) view : null;
        if (textView != null) {
            textView.setTextColor(LibExKt.evaluateColor(percent, startColor, endColor));
        }
    }

    public void onGradientIcoColor(@Nullable View view, int startColor, int endColor, float percent) {
        onUpdateIcoColor(view, LibExKt.evaluateColor(percent, startColor, endColor));
    }

    public void onGradientScale(@Nullable View view, float startScale, float endScale, float percent) {
        if (view != null) {
            float f2 = startScale + ((endScale - startScale) * percent);
            view.setScaleX(f2);
            view.setScaleY(f2);
        }
    }

    public void onGradientTextSize(@Nullable TextView view, float startTextSize, float endTextSize, float percent) {
        if (view != null) {
            view.setTextSize(0, startTextSize + ((endTextSize - startTextSize) * percent));
        }
    }

    public void onUpdateIcoColor(@Nullable View view, int color) {
        if (view != null) {
            LibExKt.tintDrawableColor(view, color);
        }
    }
}
