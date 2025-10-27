package com.zhpan.indicator;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import com.plv.business.sub.danmaku.entity.PLVDanmakuInfo;
import com.umeng.analytics.pro.d;
import com.zhpan.indicator.base.BaseIndicatorView;
import com.zhpan.indicator.drawer.BaseDrawer;
import com.zhpan.indicator.drawer.DrawerProxy;
import com.zhpan.indicator.option.AttrsController;
import com.zhpan.indicator.option.IndicatorOptions;
import kotlin.Metadata;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(bv = {1, 0, 3}, d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B%\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\b\u0010\u000b\u001a\u00020\fH\u0016J\u0010\u0010\r\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\u000fH\u0014J0\u0010\u0010\u001a\u00020\f2\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00072\u0006\u0010\u0014\u001a\u00020\u00072\u0006\u0010\u0015\u001a\u00020\u00072\u0006\u0010\u0016\u001a\u00020\u0007H\u0014J\u0018\u0010\u0017\u001a\u00020\f2\u0006\u0010\u0018\u001a\u00020\u00072\u0006\u0010\u0019\u001a\u00020\u0007H\u0014J\u0010\u0010\u001a\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\u000fH\u0002J\u0010\u0010\u001b\u001a\u00020\f2\u0006\u0010\u001c\u001a\u00020\u001dH\u0016J\u000e\u0010\u001e\u001a\u00020\f2\u0006\u0010\u001f\u001a\u00020\u0007R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006 "}, d2 = {"Lcom/zhpan/indicator/IndicatorView;", "Lcom/zhpan/indicator/base/BaseIndicatorView;", d.R, "Landroid/content/Context;", "attrs", "Landroid/util/AttributeSet;", "defStyleAttr", "", "(Landroid/content/Context;Landroid/util/AttributeSet;I)V", "mDrawerProxy", "Lcom/zhpan/indicator/drawer/DrawerProxy;", "notifyDataChanged", "", "onDraw", "canvas", "Landroid/graphics/Canvas;", "onLayout", "changed", "", "left", PLVDanmakuInfo.FONTMODE_TOP, "right", PLVDanmakuInfo.FONTMODE_BOTTOM, "onMeasure", "widthMeasureSpec", "heightMeasureSpec", "rotateCanvas", "setIndicatorOptions", "options", "Lcom/zhpan/indicator/option/IndicatorOptions;", "setOrientation", "orientation", "indicator_release"}, k = 1, mv = {1, 1, 16})
/* loaded from: classes8.dex */
public final class IndicatorView extends BaseIndicatorView {
    private DrawerProxy mDrawerProxy;

    @JvmOverloads
    public IndicatorView(@NotNull Context context) {
        this(context, null, 0, 6, null);
    }

    @JvmOverloads
    public IndicatorView(@NotNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0, 4, null);
    }

    public /* synthetic */ IndicatorView(Context context, AttributeSet attributeSet, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i3 & 2) != 0 ? null : attributeSet, (i3 & 4) != 0 ? 0 : i2);
    }

    private final void rotateCanvas(Canvas canvas) {
        if (getMIndicatorOptions().getOrientation() == 1) {
            canvas.rotate(90.0f, getWidth() / 2.0f, getWidth() / 2.0f);
        } else if (getMIndicatorOptions().getOrientation() == 3) {
            canvas.rotate(180.0f, getWidth() / 2.0f, getHeight() / 2.0f);
        }
    }

    @Override // com.zhpan.indicator.base.BaseIndicatorView, com.zhpan.indicator.base.IIndicator
    public void notifyDataChanged() {
        this.mDrawerProxy = new DrawerProxy(getMIndicatorOptions());
        super.notifyDataChanged();
    }

    @Override // android.view.View
    public void onDraw(@NotNull Canvas canvas) {
        Intrinsics.checkParameterIsNotNull(canvas, "canvas");
        super.onDraw(canvas);
        rotateCanvas(canvas);
        this.mDrawerProxy.onDraw(canvas);
    }

    @Override // android.view.View
    public void onLayout(boolean changed, int left, int top2, int right, int bottom) {
        super.onLayout(changed, left, top2, right, bottom);
        this.mDrawerProxy.onLayout(changed, left, top2, right, bottom);
    }

    @Override // android.view.View
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        BaseDrawer.MeasureResult measureResultOnMeasure = this.mDrawerProxy.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(measureResultOnMeasure.getMeasureWidth(), measureResultOnMeasure.getMeasureHeight());
    }

    @Override // com.zhpan.indicator.base.BaseIndicatorView, com.zhpan.indicator.base.IIndicator
    public void setIndicatorOptions(@NotNull IndicatorOptions options) {
        Intrinsics.checkParameterIsNotNull(options, "options");
        super.setIndicatorOptions(options);
        this.mDrawerProxy.setIndicatorOptions(options);
    }

    public final void setOrientation(int orientation) {
        getMIndicatorOptions().setOrientation(orientation);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    @JvmOverloads
    public IndicatorView(@NotNull Context context, @Nullable AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        Intrinsics.checkParameterIsNotNull(context, "context");
        AttrsController.initAttrs(context, attributeSet, getMIndicatorOptions());
        this.mDrawerProxy = new DrawerProxy(getMIndicatorOptions());
    }
}
