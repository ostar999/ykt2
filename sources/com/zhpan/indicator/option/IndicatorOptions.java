package com.zhpan.indicator.option;

import android.graphics.Color;
import com.aliyun.player.aliyunplayerbase.net.ServiceCommon;
import com.zhpan.indicator.utils.IndicatorUtils;
import kotlin.Metadata;

@Metadata(bv = {1, 0, 3}, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u0007\n\u0002\b\u0017\n\u0002\u0010\u000b\n\u0002\b\u0011\n\u0002\u0010\u0002\n\u0002\b\b\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u000e\u00103\u001a\u0002042\u0006\u00105\u001a\u00020\u0004J\u0016\u00106\u001a\u0002042\u0006\u00107\u001a\u00020\u00042\u0006\u00105\u001a\u00020\u0004J\u000e\u00108\u001a\u0002042\u0006\u00109\u001a\u00020\nJ\u0016\u00108\u001a\u0002042\u0006\u0010:\u001a\u00020\n2\u0006\u0010;\u001a\u00020\nR\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001a\u0010\u000f\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0006\"\u0004\b\u0011\u0010\bR\u001a\u0010\u0012\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0006\"\u0004\b\u0014\u0010\bR\u001a\u0010\u0015\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0006\"\u0004\b\u0017\u0010\bR\u001a\u0010\u0018\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\f\"\u0004\b\u001a\u0010\u000eR\u001a\u0010\u001b\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u0006\"\u0004\b\u001d\u0010\bR\u001a\u0010\u001e\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010\u0006\"\u0004\b \u0010\bR\u001a\u0010!\u001a\u00020\"X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b#\u0010$\"\u0004\b%\u0010&R\u001a\u0010'\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b(\u0010\u0006\"\u0004\b)\u0010\bR\u001a\u0010*\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b+\u0010\f\"\u0004\b,\u0010\u000eR\u001a\u0010-\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b.\u0010\f\"\u0004\b/\u0010\u000eR\u001c\u00100\u001a\u00020\n8FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b1\u0010\f\"\u0004\b2\u0010\u000e¨\u0006<"}, d2 = {"Lcom/zhpan/indicator/option/IndicatorOptions;", "", "()V", "checkedSliderColor", "", "getCheckedSliderColor", "()I", "setCheckedSliderColor", "(I)V", "checkedSliderWidth", "", "getCheckedSliderWidth", "()F", "setCheckedSliderWidth", "(F)V", "currentPosition", "getCurrentPosition", "setCurrentPosition", "indicatorStyle", "getIndicatorStyle", "setIndicatorStyle", "normalSliderColor", "getNormalSliderColor", "setNormalSliderColor", "normalSliderWidth", "getNormalSliderWidth", "setNormalSliderWidth", "orientation", "getOrientation", "setOrientation", ServiceCommon.RequestKey.FORM_KEY_PAGE_SIZE, "getPageSize", "setPageSize", "showIndicatorOneItem", "", "getShowIndicatorOneItem", "()Z", "setShowIndicatorOneItem", "(Z)V", "slideMode", "getSlideMode", "setSlideMode", "slideProgress", "getSlideProgress", "setSlideProgress", "sliderGap", "getSliderGap", "setSliderGap", "sliderHeight", "getSliderHeight", "setSliderHeight", "setCheckedColor", "", "checkedColor", "setSliderColor", "normalColor", "setSliderWidth", "sliderWidth", "normalIndicatorWidth", "checkedIndicatorWidth", "indicator_release"}, k = 1, mv = {1, 1, 16})
/* loaded from: classes8.dex */
public final class IndicatorOptions {
    private int checkedSliderColor;
    private float checkedSliderWidth;
    private int currentPosition;
    private int indicatorStyle;
    private int normalSliderColor;
    private float normalSliderWidth;
    private int orientation;
    private int pageSize;
    private boolean showIndicatorOneItem;
    private int slideMode;
    private float slideProgress;
    private float sliderGap;
    private float sliderHeight;

    public IndicatorOptions() {
        float fDp2px = IndicatorUtils.dp2px(8.0f);
        this.normalSliderWidth = fDp2px;
        this.checkedSliderWidth = fDp2px;
        this.sliderGap = fDp2px;
        this.normalSliderColor = Color.parseColor("#8C18171C");
        this.checkedSliderColor = Color.parseColor("#8C6C6D72");
        this.slideMode = 0;
    }

    public final int getCheckedSliderColor() {
        return this.checkedSliderColor;
    }

    public final float getCheckedSliderWidth() {
        return this.checkedSliderWidth;
    }

    public final int getCurrentPosition() {
        return this.currentPosition;
    }

    public final int getIndicatorStyle() {
        return this.indicatorStyle;
    }

    public final int getNormalSliderColor() {
        return this.normalSliderColor;
    }

    public final float getNormalSliderWidth() {
        return this.normalSliderWidth;
    }

    public final int getOrientation() {
        return this.orientation;
    }

    public final int getPageSize() {
        return this.pageSize;
    }

    public final boolean getShowIndicatorOneItem() {
        return this.showIndicatorOneItem;
    }

    public final int getSlideMode() {
        return this.slideMode;
    }

    public final float getSlideProgress() {
        return this.slideProgress;
    }

    public final float getSliderGap() {
        return this.sliderGap;
    }

    public final float getSliderHeight() {
        float f2 = this.sliderHeight;
        return f2 > ((float) 0) ? f2 : this.normalSliderWidth / 2;
    }

    public final void setCheckedColor(int checkedColor) {
        this.checkedSliderColor = checkedColor;
    }

    public final void setCheckedSliderColor(int i2) {
        this.checkedSliderColor = i2;
    }

    public final void setCheckedSliderWidth(float f2) {
        this.checkedSliderWidth = f2;
    }

    public final void setCurrentPosition(int i2) {
        this.currentPosition = i2;
    }

    public final void setIndicatorStyle(int i2) {
        this.indicatorStyle = i2;
    }

    public final void setNormalSliderColor(int i2) {
        this.normalSliderColor = i2;
    }

    public final void setNormalSliderWidth(float f2) {
        this.normalSliderWidth = f2;
    }

    public final void setOrientation(int i2) {
        this.orientation = i2;
    }

    public final void setPageSize(int i2) {
        this.pageSize = i2;
    }

    public final void setShowIndicatorOneItem(boolean z2) {
        this.showIndicatorOneItem = z2;
    }

    public final void setSlideMode(int i2) {
        this.slideMode = i2;
    }

    public final void setSlideProgress(float f2) {
        this.slideProgress = f2;
    }

    public final void setSliderColor(int normalColor, int checkedColor) {
        this.normalSliderColor = normalColor;
        this.checkedSliderColor = checkedColor;
    }

    public final void setSliderGap(float f2) {
        this.sliderGap = f2;
    }

    public final void setSliderHeight(float f2) {
        this.sliderHeight = f2;
    }

    public final void setSliderWidth(float normalIndicatorWidth, float checkedIndicatorWidth) {
        this.normalSliderWidth = normalIndicatorWidth;
        this.checkedSliderWidth = checkedIndicatorWidth;
    }

    public final void setSliderWidth(float sliderWidth) {
        setSliderWidth(sliderWidth, sliderWidth);
    }
}
