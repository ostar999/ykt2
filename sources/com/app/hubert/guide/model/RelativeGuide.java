package com.app.hubert.guide.model;

import android.graphics.RectF;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.annotation.LayoutRes;
import com.app.hubert.guide.core.Controller;
import com.app.hubert.guide.util.LogUtil;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes2.dex */
public class RelativeGuide {
    public int gravity;
    public HighLight highLight;

    @LayoutRes
    public int layout;
    public int padding;

    @Retention(RetentionPolicy.SOURCE)
    public @interface LimitGravity {
    }

    public static class MarginInfo {
        public int bottomMargin;
        public int gravity;
        public int leftMargin;
        public int rightMargin;
        public int topMargin;

        public String toString() {
            return "MarginInfo{leftMargin=" + this.leftMargin + ", topMargin=" + this.topMargin + ", rightMargin=" + this.rightMargin + ", bottomMargin=" + this.bottomMargin + ", gravity=" + this.gravity + '}';
        }
    }

    public RelativeGuide(@LayoutRes int i2, int i3) {
        this.layout = i2;
        this.gravity = i3;
    }

    private MarginInfo getMarginInfo(int i2, ViewGroup viewGroup, View view) {
        MarginInfo marginInfo = new MarginInfo();
        RectF rectF = this.highLight.getRectF(viewGroup);
        if (i2 == 3) {
            marginInfo.gravity = 5;
            marginInfo.rightMargin = (int) ((viewGroup.getWidth() - rectF.left) + this.padding);
            marginInfo.topMargin = (int) rectF.top;
        } else if (i2 == 5) {
            marginInfo.leftMargin = (int) (rectF.right + this.padding);
            marginInfo.topMargin = (int) rectF.top;
        } else if (i2 == 48) {
            marginInfo.gravity = 80;
            marginInfo.bottomMargin = (int) ((viewGroup.getHeight() - rectF.top) + this.padding);
            marginInfo.leftMargin = (int) rectF.left;
        } else if (i2 == 80) {
            marginInfo.topMargin = (int) (rectF.bottom + this.padding);
            marginInfo.leftMargin = (int) rectF.left;
        }
        return marginInfo;
    }

    public final View getGuideLayout(ViewGroup viewGroup, Controller controller) {
        View viewInflate = LayoutInflater.from(viewGroup.getContext()).inflate(this.layout, viewGroup, false);
        onLayoutInflated(viewInflate);
        onLayoutInflated(viewInflate, controller);
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) viewInflate.getLayoutParams();
        MarginInfo marginInfo = getMarginInfo(this.gravity, viewGroup, viewInflate);
        LogUtil.e(marginInfo.toString());
        offsetMargin(marginInfo, viewGroup, viewInflate);
        layoutParams.gravity = marginInfo.gravity;
        layoutParams.leftMargin += marginInfo.leftMargin;
        layoutParams.topMargin += marginInfo.topMargin;
        layoutParams.rightMargin += marginInfo.rightMargin;
        layoutParams.bottomMargin += marginInfo.bottomMargin;
        viewInflate.setLayoutParams(layoutParams);
        return viewInflate;
    }

    public void offsetMargin(MarginInfo marginInfo, ViewGroup viewGroup, View view) {
    }

    @Deprecated
    public void onLayoutInflated(View view) {
    }

    public void onLayoutInflated(View view, Controller controller) {
    }

    public RelativeGuide(@LayoutRes int i2, int i3, int i4) {
        this.layout = i2;
        this.gravity = i3;
        this.padding = i4;
    }
}
