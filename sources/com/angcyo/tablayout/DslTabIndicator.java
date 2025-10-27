package com.angcyo.tablayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import com.angcyo.tablayout.DslTabLayout;
import com.meizu.cloud.pushsdk.notification.model.NotifyType;
import java.util.Arrays;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000j\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\n\n\u0002\u0010\u000b\n\u0002\b\u0011\n\u0002\u0018\u0002\n\u0002\b)\n\u0002\u0010\u0007\n\u0002\b\n\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0016\u0018\u0000 \u0086\u00012\u00020\u0001:\u0002\u0086\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u000e\u0010U\u001a\u00020\u00062\u0006\u0010V\u001a\u00020\u0006J\u0010\u0010W\u001a\u00020X2\u0006\u0010Y\u001a\u00020ZH\u0016J\u000e\u0010[\u001a\u00020X2\u0006\u0010Y\u001a\u00020ZJ>\u0010\\\u001a\u00020X2\u0006\u0010]\u001a\u00020#2\u0006\u0010Y\u001a\u00020Z2\u0006\u0010^\u001a\u00020\u00062\u0006\u0010_\u001a\u00020\u00062\u0006\u0010`\u001a\u00020\u00062\u0006\u0010a\u001a\u00020\u00062\u0006\u0010b\u001a\u00020MJF\u0010c\u001a\u00020X2\u0006\u0010]\u001a\u00020#2\u0006\u0010Y\u001a\u00020Z2\u0006\u0010^\u001a\u00020\u00062\u0006\u0010_\u001a\u00020\u00062\u0006\u0010`\u001a\u00020\u00062\u0006\u0010a\u001a\u00020\u00062\u0006\u0010d\u001a\u00020\u00062\u0006\u0010b\u001a\u00020MJF\u0010e\u001a\u00020X2\u0006\u0010]\u001a\u00020#2\u0006\u0010Y\u001a\u00020Z2\u0006\u0010^\u001a\u00020\u00062\u0006\u0010_\u001a\u00020\u00062\u0006\u0010`\u001a\u00020\u00062\u0006\u0010a\u001a\u00020\u00062\u0006\u0010f\u001a\u00020\u00062\u0006\u0010b\u001a\u00020MJ\u000e\u0010g\u001a\u00020X2\u0006\u0010Y\u001a\u00020ZJ\u0010\u0010h\u001a\u00020\u00062\u0006\u0010i\u001a\u00020jH\u0016J\u0010\u0010k\u001a\u00020\u00062\u0006\u0010i\u001a\u00020jH\u0016J\u0010\u0010l\u001a\u00020\u00062\u0006\u0010i\u001a\u00020jH\u0016J\u0010\u0010m\u001a\u00020\u00062\u0006\u0010i\u001a\u00020jH\u0016J\u0010\u0010n\u001a\u00020\u00062\u0006\u0010i\u001a\u00020jH\u0016J\u0010\u0010o\u001a\u00020\u00062\u0006\u0010i\u001a\u00020jH\u0016J\u001a\u0010p\u001a\u00020\u00062\u0006\u0010V\u001a\u00020\u00062\b\b\u0002\u0010q\u001a\u00020\u0006H\u0016J\u001a\u0010r\u001a\u00020\u00062\u0006\u0010V\u001a\u00020\u00062\b\b\u0002\u0010q\u001a\u00020\u0006H\u0016J\u0010\u0010s\u001a\u00020\u00062\u0006\u0010V\u001a\u00020\u0006H\u0016J\u0010\u0010t\u001a\u00020\u00062\u0006\u0010V\u001a\u00020\u0006H\u0016J\u0012\u0010u\u001a\u0004\u0018\u00010j2\u0006\u0010i\u001a\u00020jH\u0016J\u001a\u0010v\u001a\u00020X2\u0006\u0010w\u001a\u00020x2\b\u0010y\u001a\u0004\u0018\u00010zH\u0016JK\u0010{\u001a\u00020X2\u0006\u0010V\u001a\u00020\u000629\u0010|\u001a5\u0012\u0013\u0012\u00110j¢\u0006\f\b~\u0012\b\b\u007f\u0012\u0004\b\b(i\u0012\u0016\u0012\u0014\u0018\u00010j¢\u0006\r\b~\u0012\t\b\u007f\u0012\u0005\b\b(\u0080\u0001\u0012\u0004\u0012\u00020X0}H\u0016J\u001f\u0010\u0081\u0001\u001a\u0004\u0018\u00010#2\t\u0010\u0082\u0001\u001a\u0004\u0018\u00010#2\u0007\u0010\u0083\u0001\u001a\u00020\u0006H\u0016J\f\u0010\u0084\u0001\u001a\u0005\u0018\u00010\u0085\u0001H\u0016R\u0011\u0010\u0005\u001a\u00020\u00068F¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\b\"\u0004\b\u000b\u0010\fR\u001a\u0010\r\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\b\"\u0004\b\u000f\u0010\fR\u001a\u0010\u0010\u001a\u00020\u0011X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u001a\u0010\u0016\u001a\u00020\u0011X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0013\"\u0004\b\u0018\u0010\u0015R$\u0010\u001a\u001a\u00020\u00062\u0006\u0010\u0019\u001a\u00020\u0006@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001b\u0010\b\"\u0004\b\u001c\u0010\fR\u001a\u0010\u001d\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001e\u0010\b\"\u0004\b\u001f\u0010\fR\u001a\u0010 \u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b!\u0010\b\"\u0004\b\"\u0010\fR(\u0010$\u001a\u0004\u0018\u00010#2\b\u0010\u0019\u001a\u0004\u0018\u00010#@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b%\u0010&\"\u0004\b'\u0010(R\u001a\u0010)\u001a\u00020\u0011X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b*\u0010\u0013\"\u0004\b+\u0010\u0015R\u001a\u0010,\u001a\u00020\u0011X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b-\u0010\u0013\"\u0004\b.\u0010\u0015R\u001a\u0010/\u001a\u00020\u0011X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b0\u0010\u0013\"\u0004\b1\u0010\u0015R\u001a\u00102\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b3\u0010\b\"\u0004\b4\u0010\fR\u001a\u00105\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b6\u0010\b\"\u0004\b7\u0010\fR\u001a\u00108\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b9\u0010\b\"\u0004\b:\u0010\fR\u001a\u0010;\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b<\u0010\b\"\u0004\b=\u0010\fR\u001a\u0010>\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b?\u0010\b\"\u0004\b@\u0010\fR\u001a\u0010A\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bB\u0010\b\"\u0004\bC\u0010\fR\u001a\u0010D\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bE\u0010\b\"\u0004\bF\u0010\fR\u001a\u0010G\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bH\u0010\b\"\u0004\bI\u0010\fR\u001a\u0010J\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bK\u0010\b\"\u0004\bL\u0010\fR$\u0010N\u001a\u00020M2\u0006\u0010\u0019\u001a\u00020M@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bO\u0010P\"\u0004\bQ\u0010RR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\bS\u0010T¨\u0006\u0087\u0001"}, d2 = {"Lcom/angcyo/tablayout/DslTabIndicator;", "Lcom/angcyo/tablayout/DslGradientDrawable;", "tabLayout", "Lcom/angcyo/tablayout/DslTabLayout;", "(Lcom/angcyo/tablayout/DslTabLayout;)V", "_indicatorDrawStyle", "", "get_indicatorDrawStyle", "()I", "_targetIndex", "get_targetIndex", "set_targetIndex", "(I)V", "currentIndex", "getCurrentIndex", "setCurrentIndex", "ignoreChildPadding", "", "getIgnoreChildPadding", "()Z", "setIgnoreChildPadding", "(Z)V", "indicatorAnim", "getIndicatorAnim", "setIndicatorAnim", "value", "indicatorColor", "getIndicatorColor", "setIndicatorColor", "indicatorContentId", "getIndicatorContentId", "setIndicatorContentId", "indicatorContentIndex", "getIndicatorContentIndex", "setIndicatorContentIndex", "Landroid/graphics/drawable/Drawable;", "indicatorDrawable", "getIndicatorDrawable", "()Landroid/graphics/drawable/Drawable;", "setIndicatorDrawable", "(Landroid/graphics/drawable/Drawable;)V", "indicatorEnableFlash", "getIndicatorEnableFlash", "setIndicatorEnableFlash", "indicatorEnableFlashClip", "getIndicatorEnableFlashClip", "setIndicatorEnableFlashClip", "indicatorEnableFlow", "getIndicatorEnableFlow", "setIndicatorEnableFlow", "indicatorFlowStep", "getIndicatorFlowStep", "setIndicatorFlowStep", "indicatorGravity", "getIndicatorGravity", "setIndicatorGravity", "indicatorHeight", "getIndicatorHeight", "setIndicatorHeight", "indicatorHeightOffset", "getIndicatorHeightOffset", "setIndicatorHeightOffset", "indicatorStyle", "getIndicatorStyle", "setIndicatorStyle", "indicatorWidth", "getIndicatorWidth", "setIndicatorWidth", "indicatorWidthOffset", "getIndicatorWidthOffset", "setIndicatorWidthOffset", "indicatorXOffset", "getIndicatorXOffset", "setIndicatorXOffset", "indicatorYOffset", "getIndicatorYOffset", "setIndicatorYOffset", "", "positionOffset", "getPositionOffset", "()F", "setPositionOffset", "(F)V", "getTabLayout", "()Lcom/angcyo/tablayout/DslTabLayout;", "_childConvexHeight", "index", "draw", "", "canvas", "Landroid/graphics/Canvas;", "drawHorizontal", "drawIndicator", "indicator", NotifyType.LIGHTS, "t", "r", "b", "offset", "drawIndicatorClipHorizontal", "endWidth", "drawIndicatorClipVertical", "endHeight", "drawVertical", "getChildTargetHeight", "childView", "Landroid/view/View;", "getChildTargetPaddingBottom", "getChildTargetPaddingLeft", "getChildTargetPaddingRight", "getChildTargetPaddingTop", "getChildTargetWidth", "getChildTargetX", "gravity", "getChildTargetY", "getIndicatorDrawHeight", "getIndicatorDrawWidth", "indicatorContentView", "initAttribute", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "attributeSet", "Landroid/util/AttributeSet;", "targetChildView", "onChildView", "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", "contentChildView", "tintDrawableColor", "drawable", "color", "updateOriginDrawable", "Landroid/graphics/drawable/GradientDrawable;", "Companion", "TabLayout_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes2.dex */
public class DslTabIndicator extends DslGradientDrawable {
    public static final int INDICATOR_GRAVITY_CENTER = 4;
    public static final int INDICATOR_GRAVITY_END = 2;
    public static final int INDICATOR_GRAVITY_START = 1;
    public static final int INDICATOR_STYLE_BOTTOM = 2;
    public static final int INDICATOR_STYLE_CENTER = 4;
    public static final int INDICATOR_STYLE_FOREGROUND = 4096;
    public static final int INDICATOR_STYLE_NONE = 0;
    public static final int INDICATOR_STYLE_TOP = 1;
    public static final int NO_COLOR = -2;
    private int _targetIndex;
    private int currentIndex;
    private boolean ignoreChildPadding;
    private boolean indicatorAnim;
    private int indicatorColor;
    private int indicatorContentId;
    private int indicatorContentIndex;

    @Nullable
    private Drawable indicatorDrawable;
    private boolean indicatorEnableFlash;
    private boolean indicatorEnableFlashClip;
    private boolean indicatorEnableFlow;
    private int indicatorFlowStep;
    private int indicatorGravity;
    private int indicatorHeight;
    private int indicatorHeightOffset;
    private int indicatorStyle;
    private int indicatorWidth;
    private int indicatorWidthOffset;
    private int indicatorXOffset;
    private int indicatorYOffset;
    private float positionOffset;

    @NotNull
    private final DslTabLayout tabLayout;

    public DslTabIndicator(@NotNull DslTabLayout tabLayout) {
        Intrinsics.checkNotNullParameter(tabLayout, "tabLayout");
        this.tabLayout = tabLayout;
        this.indicatorGravity = 4;
        this.indicatorEnableFlashClip = true;
        this.indicatorFlowStep = 1;
        this.indicatorColor = -2;
        this.indicatorContentIndex = -1;
        this.indicatorContentId = -1;
        this.indicatorAnim = true;
        this.ignoreChildPadding = true;
        setCallback(tabLayout);
        this.currentIndex = -1;
        this._targetIndex = -1;
    }

    public static /* synthetic */ int getChildTargetX$default(DslTabIndicator dslTabIndicator, int i2, int i3, int i4, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: getChildTargetX");
        }
        if ((i4 & 2) != 0) {
            i3 = dslTabIndicator.indicatorGravity;
        }
        return dslTabIndicator.getChildTargetX(i2, i3);
    }

    public static /* synthetic */ int getChildTargetY$default(DslTabIndicator dslTabIndicator, int i2, int i3, int i4, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: getChildTargetY");
        }
        if ((i4 & 2) != 0) {
            i3 = dslTabIndicator.indicatorGravity;
        }
        return dslTabIndicator.getChildTargetY(i2, i3);
    }

    public final int _childConvexHeight(int index) {
        if (!(getAttachView() instanceof ViewGroup)) {
            return 0;
        }
        View attachView = getAttachView();
        Intrinsics.checkNotNull(attachView, "null cannot be cast to non-null type android.view.ViewGroup");
        ViewGroup.LayoutParams layoutParams = ((ViewGroup) attachView).getChildAt(index).getLayoutParams();
        DslTabLayout.LayoutParams layoutParams2 = layoutParams instanceof DslTabLayout.LayoutParams ? (DslTabLayout.LayoutParams) layoutParams : null;
        if (layoutParams2 != null) {
            return layoutParams2.getLayoutConvexHeight();
        }
        return 0;
    }

    @Override // com.angcyo.tablayout.DslGradientDrawable, com.angcyo.tablayout.AbsDslDrawable, android.graphics.drawable.Drawable
    public void draw(@NotNull Canvas canvas) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        if (!isVisible() || get_indicatorDrawStyle() == 0 || this.indicatorDrawable == null) {
            return;
        }
        if (this.tabLayout.isHorizontal()) {
            drawHorizontal(canvas);
        } else {
            drawVertical(canvas);
        }
    }

    public final void drawHorizontal(@NotNull Canvas canvas) {
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        boolean z2;
        int paddingTop;
        Drawable drawable;
        int i7;
        int i8;
        int i9;
        int i10;
        int i11;
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        int size = this.tabLayout.getDslSelector().getVisibleViewList().size();
        int iMax = this.currentIndex;
        int i12 = this._targetIndex;
        if (i12 >= 0 && i12 < size) {
            iMax = Math.max(0, iMax);
        }
        if (iMax >= 0 && iMax < size) {
            int childTargetX$default = getChildTargetX$default(this, iMax, 0, 2, null);
            int indicatorDrawWidth = getIndicatorDrawWidth(iMax);
            int indicatorDrawHeight = getIndicatorDrawHeight(iMax);
            int i13 = (childTargetX$default - (indicatorDrawWidth / 2)) + this.indicatorXOffset;
            int childTargetX$default2 = getChildTargetX$default(this, this._targetIndex, 0, 2, null);
            int indicatorDrawWidth2 = getIndicatorDrawWidth(this._targetIndex);
            int i14 = (childTargetX$default2 - (indicatorDrawWidth2 / 2)) + this.indicatorXOffset;
            int i15 = this._targetIndex;
            if (!(i15 >= 0 && i15 < size) || i15 == iMax) {
                i2 = size;
                i3 = indicatorDrawWidth;
                i4 = i13;
                i5 = indicatorDrawWidth2;
                i6 = 0;
            } else {
                int indicatorDrawHeight2 = getIndicatorDrawHeight(i15);
                if (this.indicatorEnableFlash) {
                    float f2 = this.positionOffset;
                    i8 = (int) (indicatorDrawWidth * (1 - f2));
                    i9 = (int) (indicatorDrawWidth2 * f2);
                    i4 = (childTargetX$default - (i8 / 2)) + this.indicatorXOffset;
                    i7 = indicatorDrawHeight2;
                    i2 = size;
                } else {
                    if (!this.indicatorEnableFlow || Math.abs(this._targetIndex - iMax) > this.indicatorFlowStep) {
                        i7 = indicatorDrawHeight2;
                        i2 = size;
                        i4 = (int) (this._targetIndex > iMax ? i13 + ((i14 - i13) * this.positionOffset) : i13 - ((i13 - i14) * this.positionOffset));
                        i8 = (int) (indicatorDrawWidth + ((indicatorDrawWidth2 - indicatorDrawWidth) * this.positionOffset));
                    } else {
                        if (this._targetIndex > iMax) {
                            int i16 = i14 - i13;
                            i10 = i16 + indicatorDrawWidth2;
                            float f3 = this.positionOffset;
                            i7 = indicatorDrawHeight2;
                            if (f3 >= 0.5d) {
                                i2 = size;
                                i11 = (int) (i13 + ((i16 * (f3 - 0.5d)) / 0.5f));
                            } else {
                                i2 = size;
                                i11 = i13;
                            }
                        } else {
                            i7 = indicatorDrawHeight2;
                            i2 = size;
                            int i17 = i13 - i14;
                            i10 = i17 + indicatorDrawWidth;
                            float f4 = this.positionOffset;
                            i11 = ((double) f4) >= 0.5d ? i14 : (int) (i13 - ((i17 * f4) / 0.5f));
                        }
                        i4 = i11;
                        int i18 = i10;
                        float f5 = this.positionOffset;
                        i8 = ((double) f5) >= 0.5d ? (int) (i18 - (((i18 - indicatorDrawWidth2) * (f5 - 0.5d)) / 0.5f)) : (int) (indicatorDrawWidth + (((i18 - indicatorDrawWidth) * f5) / 0.5f));
                    }
                    i9 = indicatorDrawWidth2;
                }
                i6 = (int) ((i7 - indicatorDrawHeight) * this.positionOffset);
                i5 = i9;
                i3 = i8;
            }
            int i19 = get_indicatorDrawStyle();
            if (i19 != 1) {
                paddingTop = i19 != 2 ? ((((getPaddingTop() + (getViewDrawHeight() / 2)) - (indicatorDrawHeight / 2)) + this.indicatorYOffset) - i6) + ((this.tabLayout.get_maxConvexHeight() - _childConvexHeight(iMax)) / 2) : (getViewHeight() - indicatorDrawHeight) - this.indicatorYOffset;
                z2 = false;
            } else {
                z2 = false;
                paddingTop = this.indicatorYOffset + 0;
            }
            Drawable drawable2 = this.indicatorDrawable;
            if (drawable2 != null) {
                if (!this.indicatorEnableFlash) {
                    drawIndicator(drawable2, canvas, i4, paddingTop, i4 + i3, indicatorDrawHeight + paddingTop + i6, 1 - this.positionOffset);
                    return;
                }
                if (this.indicatorEnableFlashClip) {
                    drawable = drawable2;
                    drawIndicatorClipHorizontal(drawable2, canvas, i13, paddingTop, i13 + indicatorDrawWidth, paddingTop + indicatorDrawHeight + i6, i3, 1 - this.positionOffset);
                } else {
                    drawable = drawable2;
                    drawIndicator(drawable, canvas, i4, paddingTop, i4 + i3, paddingTop + indicatorDrawHeight + i6, 1 - this.positionOffset);
                }
                int i20 = this._targetIndex;
                if (i20 >= 0 && i20 < i2) {
                    z2 = true;
                }
                if (z2) {
                    if (this.indicatorEnableFlashClip) {
                        drawIndicatorClipHorizontal(drawable, canvas, i14, paddingTop, i14 + indicatorDrawWidth2, indicatorDrawHeight + paddingTop + i6, i5, this.positionOffset);
                    } else {
                        drawIndicator(drawable, canvas, i14, paddingTop, i14 + i5, indicatorDrawHeight + paddingTop + i6, this.positionOffset);
                    }
                }
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void drawIndicator(@NotNull Drawable indicator, @NotNull Canvas canvas, int l2, int t2, int r2, int b3, float offset) {
        Intrinsics.checkNotNullParameter(indicator, "indicator");
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        if (indicator instanceof ITabIndicatorDraw) {
            indicator.setBounds(l2, t2, r2, b3);
            ((ITabIndicatorDraw) indicator).onDrawTabIndicator(this, canvas, offset);
            return;
        }
        indicator.setBounds(0, 0, r2 - l2, b3 - t2);
        int iSave = canvas.save();
        try {
            canvas.translate(l2, t2);
            indicator.draw(canvas);
        } finally {
            canvas.restoreToCount(iSave);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void drawIndicatorClipHorizontal(@NotNull Drawable indicator, @NotNull Canvas canvas, int l2, int t2, int r2, int b3, int endWidth, float offset) {
        Intrinsics.checkNotNullParameter(indicator, "indicator");
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        canvas.save();
        int i2 = ((r2 - l2) - endWidth) / 2;
        canvas.clipRect(l2 + i2, t2, r2 - i2, b3);
        indicator.setBounds(l2, t2, r2, b3);
        if (indicator instanceof ITabIndicatorDraw) {
            ((ITabIndicatorDraw) indicator).onDrawTabIndicator(this, canvas, offset);
        } else {
            indicator.draw(canvas);
        }
        canvas.restore();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void drawIndicatorClipVertical(@NotNull Drawable indicator, @NotNull Canvas canvas, int l2, int t2, int r2, int b3, int endHeight, float offset) {
        Intrinsics.checkNotNullParameter(indicator, "indicator");
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        canvas.save();
        int i2 = ((b3 - t2) - endHeight) / 2;
        canvas.clipRect(l2, t2 + i2, r2, b3 - i2);
        indicator.setBounds(l2, t2, r2, b3);
        if (indicator instanceof ITabIndicatorDraw) {
            ((ITabIndicatorDraw) indicator).onDrawTabIndicator(this, canvas, offset);
        } else {
            indicator.draw(canvas);
        }
        canvas.restore();
    }

    public final void drawVertical(@NotNull Canvas canvas) {
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        boolean z2;
        int paddingLeft;
        Drawable drawable;
        int i7;
        int i8;
        int i9;
        int i10;
        int i11;
        int i12;
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        int size = this.tabLayout.getDslSelector().getVisibleViewList().size();
        int iMax = this.currentIndex;
        int i13 = this._targetIndex;
        if (i13 >= 0 && i13 < size) {
            iMax = Math.max(0, iMax);
        }
        if (iMax >= 0 && iMax < size) {
            int childTargetY$default = getChildTargetY$default(this, iMax, 0, 2, null);
            int indicatorDrawWidth = getIndicatorDrawWidth(iMax);
            int indicatorDrawHeight = getIndicatorDrawHeight(iMax);
            int i14 = (childTargetY$default - (indicatorDrawHeight / 2)) + this.indicatorYOffset;
            int childTargetY$default2 = getChildTargetY$default(this, this._targetIndex, 0, 2, null);
            int indicatorDrawHeight2 = getIndicatorDrawHeight(this._targetIndex);
            int i15 = (childTargetY$default2 - (indicatorDrawHeight2 / 2)) + this.indicatorYOffset;
            int i16 = this._targetIndex;
            if (!(i16 >= 0 && i16 < size) || i16 == iMax) {
                i2 = indicatorDrawHeight;
                i3 = i14;
                i4 = indicatorDrawHeight2;
                i5 = i15;
                i6 = 0;
            } else {
                int indicatorDrawWidth2 = getIndicatorDrawWidth(i16);
                if (this.indicatorEnableFlash) {
                    float f2 = this.positionOffset;
                    i2 = (int) (indicatorDrawHeight * (1 - f2));
                    i8 = (int) (indicatorDrawHeight2 * f2);
                    int i17 = this.indicatorXOffset;
                    i7 = (childTargetY$default - (i2 / 2)) + i17;
                    i9 = (childTargetY$default2 - (i8 / 2)) + i17;
                } else {
                    if (!this.indicatorEnableFlow || Math.abs(this._targetIndex - iMax) > this.indicatorFlowStep) {
                        i7 = (int) (this._targetIndex > iMax ? i14 + ((i15 - i14) * this.positionOffset) : i14 - ((i14 - i15) * this.positionOffset));
                        i2 = (int) (indicatorDrawHeight + ((indicatorDrawHeight2 - indicatorDrawHeight) * this.positionOffset));
                    } else {
                        if (this._targetIndex > iMax) {
                            int i18 = i15 - i14;
                            int i19 = i18 + indicatorDrawHeight2;
                            float f3 = this.positionOffset;
                            if (f3 >= 0.5d) {
                                i10 = indicatorDrawHeight;
                                i12 = (int) (i14 + ((i18 * (f3 - 0.5d)) / 0.5f));
                            } else {
                                i10 = indicatorDrawHeight;
                                i12 = i14;
                            }
                            i7 = i12;
                            i11 = i19;
                        } else {
                            i10 = indicatorDrawHeight;
                            int i20 = i14 - i15;
                            i11 = i20 + i10;
                            float f4 = this.positionOffset;
                            i7 = ((double) f4) >= 0.5d ? i15 : (int) (i14 - ((i20 * f4) / 0.5f));
                        }
                        float f5 = this.positionOffset;
                        if (f5 >= 0.5d) {
                            i2 = (int) (i11 - (((i11 - indicatorDrawHeight2) * (f5 - 0.5d)) / 0.5f));
                            indicatorDrawHeight = i10;
                        } else {
                            indicatorDrawHeight = i10;
                            i2 = (int) (indicatorDrawHeight + (((i11 - indicatorDrawHeight) * f5) / 0.5f));
                        }
                    }
                    i8 = indicatorDrawHeight2;
                    i9 = i15;
                }
                i6 = (int) ((indicatorDrawWidth2 - indicatorDrawWidth) * this.positionOffset);
                i5 = i9;
                i3 = i7;
                i4 = i8;
            }
            int i21 = get_indicatorDrawStyle();
            if (i21 != 1) {
                paddingLeft = i21 != 2 ? ((getPaddingLeft() + this.indicatorXOffset) + ((getViewDrawWidth() / 2) - (indicatorDrawWidth / 2))) - ((this.tabLayout.get_maxConvexHeight() - _childConvexHeight(iMax)) / 2) : (getViewWidth() - indicatorDrawWidth) - this.indicatorXOffset;
                z2 = false;
            } else {
                z2 = false;
                paddingLeft = this.indicatorXOffset + 0;
            }
            Drawable drawable2 = this.indicatorDrawable;
            if (drawable2 != null) {
                if (!this.indicatorEnableFlash) {
                    drawIndicator(drawable2, canvas, paddingLeft, i3, paddingLeft + indicatorDrawWidth + i6, i2 + i3, 1 - this.positionOffset);
                    return;
                }
                if (this.indicatorEnableFlashClip) {
                    drawable = drawable2;
                    drawIndicatorClipVertical(drawable2, canvas, paddingLeft, i14, paddingLeft + indicatorDrawWidth + i6, i14 + indicatorDrawHeight, i2, 1 - this.positionOffset);
                } else {
                    drawable = drawable2;
                    drawIndicator(drawable, canvas, paddingLeft, i3, paddingLeft + indicatorDrawWidth + i6, i2 + i3, 1 - this.positionOffset);
                }
                int i22 = this._targetIndex;
                if (i22 >= 0 && i22 < size) {
                    z2 = true;
                }
                if (z2) {
                    if (this.indicatorEnableFlashClip) {
                        drawIndicatorClipVertical(drawable, canvas, paddingLeft, i15, paddingLeft + indicatorDrawWidth + i6, i15 + indicatorDrawHeight2, i4, this.positionOffset);
                    } else {
                        drawIndicator(drawable, canvas, paddingLeft, i5, paddingLeft + indicatorDrawWidth + i6, i5 + i4, this.positionOffset);
                    }
                }
            }
        }
    }

    public int getChildTargetHeight(@NotNull View childView) {
        Intrinsics.checkNotNullParameter(childView, "childView");
        return this.ignoreChildPadding ? LibExKt.getViewDrawHeight(childView) : childView.getMeasuredHeight();
    }

    public int getChildTargetPaddingBottom(@NotNull View childView) {
        Intrinsics.checkNotNullParameter(childView, "childView");
        if (this.ignoreChildPadding) {
            return childView.getPaddingBottom();
        }
        return 0;
    }

    public int getChildTargetPaddingLeft(@NotNull View childView) {
        Intrinsics.checkNotNullParameter(childView, "childView");
        if (this.ignoreChildPadding) {
            return childView.getPaddingLeft();
        }
        return 0;
    }

    public int getChildTargetPaddingRight(@NotNull View childView) {
        Intrinsics.checkNotNullParameter(childView, "childView");
        if (this.ignoreChildPadding) {
            return childView.getPaddingRight();
        }
        return 0;
    }

    public int getChildTargetPaddingTop(@NotNull View childView) {
        Intrinsics.checkNotNullParameter(childView, "childView");
        if (this.ignoreChildPadding) {
            return childView.getPaddingTop();
        }
        return 0;
    }

    public int getChildTargetWidth(@NotNull View childView) {
        Intrinsics.checkNotNullParameter(childView, "childView");
        return this.ignoreChildPadding ? LibExKt.getViewDrawWidth(childView) : childView.getMeasuredWidth();
    }

    public int getChildTargetX(int index, final int gravity) {
        final Ref.IntRef intRef = new Ref.IntRef();
        intRef.element = index > 0 ? this.tabLayout.getMaxWidth() : 0;
        targetChildView(index, new Function2<View, View, Unit>() { // from class: com.angcyo.tablayout.DslTabIndicator.getChildTargetX.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Unit invoke(View view, View view2) {
                invoke2(view, view2);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(@NotNull View childView, @Nullable View view) {
                int left;
                int left2;
                int left3;
                Intrinsics.checkNotNullParameter(childView, "childView");
                Ref.IntRef intRef2 = intRef;
                if (view == null) {
                    int i2 = gravity;
                    left3 = i2 != 1 ? i2 != 2 ? childView.getLeft() + this.getChildTargetPaddingLeft(childView) + (this.getChildTargetWidth(childView) / 2) : childView.getRight() : childView.getLeft();
                } else {
                    int i3 = gravity;
                    if (i3 == 1) {
                        left = childView.getLeft();
                        left2 = view.getLeft();
                    } else if (i3 != 2) {
                        left = childView.getLeft() + view.getLeft() + this.getChildTargetPaddingLeft(view);
                        left2 = this.getChildTargetWidth(view) / 2;
                    } else {
                        left = childView.getLeft();
                        left2 = view.getRight();
                    }
                    left3 = left2 + left;
                }
                intRef2.element = left3;
            }
        });
        return intRef.element;
    }

    public int getChildTargetY(int index, final int gravity) {
        final Ref.IntRef intRef = new Ref.IntRef();
        intRef.element = index > 0 ? this.tabLayout.getMaxHeight() : 0;
        targetChildView(index, new Function2<View, View, Unit>() { // from class: com.angcyo.tablayout.DslTabIndicator.getChildTargetY.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Unit invoke(View view, View view2) {
                invoke2(view, view2);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(@NotNull View childView, @Nullable View view) {
                int top2;
                int top3;
                int top4;
                int top5;
                int bottom;
                Intrinsics.checkNotNullParameter(childView, "childView");
                Ref.IntRef intRef2 = intRef;
                if (view == null) {
                    int i2 = gravity;
                    if (i2 == 1) {
                        top4 = childView.getTop();
                    } else if (i2 != 2) {
                        top5 = childView.getTop() + this.getChildTargetPaddingTop(childView);
                        bottom = this.getChildTargetHeight(childView) / 2;
                        top4 = top5 + bottom;
                    } else {
                        top4 = childView.getBottom();
                    }
                } else {
                    int i3 = gravity;
                    if (i3 == 1) {
                        top2 = childView.getTop();
                        top3 = view.getTop();
                    } else if (i3 != 2) {
                        top2 = childView.getTop() + view.getTop() + this.getChildTargetPaddingTop(view);
                        top3 = this.getChildTargetHeight(view) / 2;
                    } else {
                        top5 = childView.getTop();
                        bottom = childView.getBottom();
                        top4 = top5 + bottom;
                    }
                    top4 = top3 + top2;
                }
                intRef2.element = top4;
            }
        });
        return intRef.element;
    }

    public final int getCurrentIndex() {
        return this.currentIndex;
    }

    public final boolean getIgnoreChildPadding() {
        return this.ignoreChildPadding;
    }

    public final boolean getIndicatorAnim() {
        return this.indicatorAnim;
    }

    public final int getIndicatorColor() {
        return this.indicatorColor;
    }

    public final int getIndicatorContentId() {
        return this.indicatorContentId;
    }

    public final int getIndicatorContentIndex() {
        return this.indicatorContentIndex;
    }

    public int getIndicatorDrawHeight(int index) {
        View view;
        int childTargetHeight = this.indicatorHeight;
        if (childTargetHeight == -2) {
            View view2 = (View) CollectionsKt___CollectionsKt.getOrNull(this.tabLayout.getDslSelector().getVisibleViewList(), index);
            if (view2 != null) {
                View viewIndicatorContentView = indicatorContentView(view2);
                if (viewIndicatorContentView != null) {
                    view2 = viewIndicatorContentView;
                }
                childTargetHeight = getChildTargetHeight(view2);
            }
        } else if (childTargetHeight == -1 && (view = (View) CollectionsKt___CollectionsKt.getOrNull(this.tabLayout.getDslSelector().getVisibleViewList(), index)) != null) {
            childTargetHeight = view.getMeasuredHeight();
        }
        return childTargetHeight + this.indicatorHeightOffset;
    }

    public int getIndicatorDrawWidth(int index) {
        View view;
        int childTargetWidth = this.indicatorWidth;
        if (childTargetWidth == -2) {
            View view2 = (View) CollectionsKt___CollectionsKt.getOrNull(this.tabLayout.getDslSelector().getVisibleViewList(), index);
            if (view2 != null) {
                View viewIndicatorContentView = indicatorContentView(view2);
                if (viewIndicatorContentView != null) {
                    view2 = viewIndicatorContentView;
                }
                childTargetWidth = getChildTargetWidth(view2);
            }
        } else if (childTargetWidth == -1 && (view = (View) CollectionsKt___CollectionsKt.getOrNull(this.tabLayout.getDslSelector().getVisibleViewList(), index)) != null) {
            childTargetWidth = view.getMeasuredWidth();
        }
        return childTargetWidth + this.indicatorWidthOffset;
    }

    @Nullable
    public final Drawable getIndicatorDrawable() {
        return this.indicatorDrawable;
    }

    public final boolean getIndicatorEnableFlash() {
        return this.indicatorEnableFlash;
    }

    public final boolean getIndicatorEnableFlashClip() {
        return this.indicatorEnableFlashClip;
    }

    public final boolean getIndicatorEnableFlow() {
        return this.indicatorEnableFlow;
    }

    public final int getIndicatorFlowStep() {
        return this.indicatorFlowStep;
    }

    public final int getIndicatorGravity() {
        return this.indicatorGravity;
    }

    public final int getIndicatorHeight() {
        return this.indicatorHeight;
    }

    public final int getIndicatorHeightOffset() {
        return this.indicatorHeightOffset;
    }

    public final int getIndicatorStyle() {
        return this.indicatorStyle;
    }

    public final int getIndicatorWidth() {
        return this.indicatorWidth;
    }

    public final int getIndicatorWidthOffset() {
        return this.indicatorWidthOffset;
    }

    public final int getIndicatorXOffset() {
        return this.indicatorXOffset;
    }

    public final int getIndicatorYOffset() {
        return this.indicatorYOffset;
    }

    public final float getPositionOffset() {
        return this.positionOffset;
    }

    @NotNull
    public final DslTabLayout getTabLayout() {
        return this.tabLayout;
    }

    public final int get_indicatorDrawStyle() {
        return LibExKt.remove(this.indicatorStyle, 4096);
    }

    public final int get_targetIndex() {
        return this._targetIndex;
    }

    @Nullable
    public View indicatorContentView(@NotNull View childView) {
        Intrinsics.checkNotNullParameter(childView, "childView");
        ViewGroup.LayoutParams layoutParams = childView.getLayoutParams();
        Intrinsics.checkNotNull(layoutParams, "null cannot be cast to non-null type com.angcyo.tablayout.DslTabLayout.LayoutParams");
        DslTabLayout.LayoutParams layoutParams2 = (DslTabLayout.LayoutParams) layoutParams;
        int indicatorContentId = layoutParams2.getIndicatorContentId() != -1 ? layoutParams2.getIndicatorContentId() : this.indicatorContentId;
        if (indicatorContentId != -1) {
            return childView.findViewById(indicatorContentId);
        }
        int indicatorContentIndex = layoutParams2.getIndicatorContentIndex() >= 0 ? layoutParams2.getIndicatorContentIndex() : this.indicatorContentIndex;
        if (indicatorContentIndex >= 0 && (childView instanceof ViewGroup)) {
            boolean z2 = false;
            if (indicatorContentIndex >= 0 && indicatorContentIndex < ((ViewGroup) childView).getChildCount()) {
                z2 = true;
            }
            if (z2) {
                return ((ViewGroup) childView).getChildAt(indicatorContentIndex);
            }
        }
        return null;
    }

    @Override // com.angcyo.tablayout.AbsDslDrawable
    public void initAttribute(@NotNull Context context, @Nullable AttributeSet attributeSet) {
        int[] iArr_fillColor;
        Intrinsics.checkNotNullParameter(context, "context");
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.DslTabLayout);
        Intrinsics.checkNotNullExpressionValue(typedArrayObtainStyledAttributes, "context.obtainStyledAttr…R.styleable.DslTabLayout)");
        setIndicatorDrawable(typedArrayObtainStyledAttributes.getDrawable(R.styleable.DslTabLayout_tab_indicator_drawable));
        setIndicatorColor(typedArrayObtainStyledAttributes.getColor(R.styleable.DslTabLayout_tab_indicator_color, this.indicatorColor));
        this.indicatorStyle = typedArrayObtainStyledAttributes.getInt(R.styleable.DslTabLayout_tab_indicator_style, this.tabLayout.isHorizontal() ? 2 : 1);
        this.indicatorGravity = typedArrayObtainStyledAttributes.getInt(R.styleable.DslTabLayout_tab_indicator_gravity, this.indicatorGravity);
        if (LibExKt.have(this.indicatorStyle, 4096)) {
            this.indicatorWidth = typedArrayObtainStyledAttributes.getLayoutDimension(R.styleable.DslTabLayout_tab_indicator_width, this.tabLayout.isHorizontal() ? -1 : LibExKt.getDpi() * 3);
            this.indicatorHeight = typedArrayObtainStyledAttributes.getLayoutDimension(R.styleable.DslTabLayout_tab_indicator_height, this.tabLayout.isHorizontal() ? LibExKt.getDpi() * 3 : -1);
            this.indicatorXOffset = typedArrayObtainStyledAttributes.getDimensionPixelOffset(R.styleable.DslTabLayout_tab_indicator_x_offset, this.tabLayout.isHorizontal() ? 0 : LibExKt.getDpi() * 2);
            this.indicatorYOffset = typedArrayObtainStyledAttributes.getDimensionPixelOffset(R.styleable.DslTabLayout_tab_indicator_y_offset, this.tabLayout.isHorizontal() ? LibExKt.getDpi() * 2 : 0);
        } else {
            if (this.tabLayout.isHorizontal()) {
                this.indicatorWidth = -1;
                this.indicatorHeight = -1;
            } else {
                this.indicatorHeight = -1;
                this.indicatorWidth = -1;
            }
            this.indicatorWidth = typedArrayObtainStyledAttributes.getLayoutDimension(R.styleable.DslTabLayout_tab_indicator_width, this.indicatorWidth);
            this.indicatorHeight = typedArrayObtainStyledAttributes.getLayoutDimension(R.styleable.DslTabLayout_tab_indicator_height, this.indicatorHeight);
            this.indicatorXOffset = typedArrayObtainStyledAttributes.getDimensionPixelOffset(R.styleable.DslTabLayout_tab_indicator_x_offset, this.indicatorXOffset);
            this.indicatorYOffset = typedArrayObtainStyledAttributes.getDimensionPixelOffset(R.styleable.DslTabLayout_tab_indicator_y_offset, this.indicatorYOffset);
        }
        this.ignoreChildPadding = typedArrayObtainStyledAttributes.getBoolean(R.styleable.DslTabLayout_tab_indicator_ignore_child_padding, !LibExKt.have(this.indicatorStyle, 4));
        this.indicatorFlowStep = typedArrayObtainStyledAttributes.getInt(R.styleable.DslTabLayout_tab_indicator_flow_step, this.indicatorFlowStep);
        this.indicatorEnableFlow = typedArrayObtainStyledAttributes.getBoolean(R.styleable.DslTabLayout_tab_indicator_enable_flow, this.indicatorEnableFlow);
        this.indicatorEnableFlash = typedArrayObtainStyledAttributes.getBoolean(R.styleable.DslTabLayout_tab_indicator_enable_flash, this.indicatorEnableFlash);
        this.indicatorEnableFlashClip = typedArrayObtainStyledAttributes.getBoolean(R.styleable.DslTabLayout_tab_indicator_enable_flash_clip, this.indicatorEnableFlashClip);
        this.indicatorWidthOffset = typedArrayObtainStyledAttributes.getDimensionPixelOffset(R.styleable.DslTabLayout_tab_indicator_width_offset, this.indicatorWidthOffset);
        this.indicatorHeightOffset = typedArrayObtainStyledAttributes.getDimensionPixelOffset(R.styleable.DslTabLayout_tab_indicator_height_offset, this.indicatorHeightOffset);
        this.indicatorContentIndex = typedArrayObtainStyledAttributes.getInt(R.styleable.DslTabLayout_tab_indicator_content_index, this.indicatorContentIndex);
        this.indicatorContentId = typedArrayObtainStyledAttributes.getResourceId(R.styleable.DslTabLayout_tab_indicator_content_id, this.indicatorContentId);
        this.indicatorAnim = typedArrayObtainStyledAttributes.getBoolean(R.styleable.DslTabLayout_tab_indicator_anim, this.indicatorAnim);
        setGradientShape(typedArrayObtainStyledAttributes.getInt(R.styleable.DslTabLayout_tab_indicator_shape, getGradientShape()));
        setGradientSolidColor(typedArrayObtainStyledAttributes.getColor(R.styleable.DslTabLayout_tab_indicator_solid_color, getGradientSolidColor()));
        setGradientStrokeColor(typedArrayObtainStyledAttributes.getColor(R.styleable.DslTabLayout_tab_indicator_stroke_color, getGradientStrokeColor()));
        setGradientStrokeWidth(typedArrayObtainStyledAttributes.getDimensionPixelOffset(R.styleable.DslTabLayout_tab_indicator_stroke_width, getGradientStrokeWidth()));
        setGradientDashWidth(typedArrayObtainStyledAttributes.getDimensionPixelOffset(R.styleable.DslTabLayout_tab_indicator_dash_width, (int) getGradientDashWidth()));
        setGradientDashGap(typedArrayObtainStyledAttributes.getDimensionPixelOffset(R.styleable.DslTabLayout_tab_indicator_dash_gap, (int) getGradientDashGap()));
        int dimensionPixelOffset = typedArrayObtainStyledAttributes.getDimensionPixelOffset(R.styleable.DslTabLayout_tab_indicator_radius, 0);
        if (dimensionPixelOffset > 0) {
            Arrays.fill(getGradientRadii(), dimensionPixelOffset);
        } else {
            String string = typedArrayObtainStyledAttributes.getString(R.styleable.DslTabLayout_tab_indicator_radii);
            if (string != null) {
                _fillRadii(getGradientRadii(), string);
            }
        }
        String string2 = typedArrayObtainStyledAttributes.getString(R.styleable.DslTabLayout_tab_indicator_gradient_colors);
        if (string2 == null || string2.length() == 0) {
            int color = typedArrayObtainStyledAttributes.getColor(R.styleable.DslTabLayout_tab_indicator_gradient_start_color, 0);
            int color2 = typedArrayObtainStyledAttributes.getColor(R.styleable.DslTabLayout_tab_indicator_gradient_end_color, 0);
            iArr_fillColor = color != color2 ? new int[]{color, color2} : getGradientColors();
        } else {
            iArr_fillColor = _fillColor(string2);
            if (iArr_fillColor == null) {
                iArr_fillColor = getGradientColors();
            }
        }
        setGradientColors(iArr_fillColor);
        typedArrayObtainStyledAttributes.recycle();
        if (this.indicatorDrawable == null && isValidConfig()) {
            updateOriginDrawable();
        }
    }

    public final void setCurrentIndex(int i2) {
        this.currentIndex = i2;
    }

    public final void setIgnoreChildPadding(boolean z2) {
        this.ignoreChildPadding = z2;
    }

    public final void setIndicatorAnim(boolean z2) {
        this.indicatorAnim = z2;
    }

    public final void setIndicatorColor(int i2) {
        this.indicatorColor = i2;
        setIndicatorDrawable(this.indicatorDrawable);
    }

    public final void setIndicatorContentId(int i2) {
        this.indicatorContentId = i2;
    }

    public final void setIndicatorContentIndex(int i2) {
        this.indicatorContentIndex = i2;
    }

    public final void setIndicatorDrawable(@Nullable Drawable drawable) {
        this.indicatorDrawable = tintDrawableColor(drawable, this.indicatorColor);
    }

    public final void setIndicatorEnableFlash(boolean z2) {
        this.indicatorEnableFlash = z2;
    }

    public final void setIndicatorEnableFlashClip(boolean z2) {
        this.indicatorEnableFlashClip = z2;
    }

    public final void setIndicatorEnableFlow(boolean z2) {
        this.indicatorEnableFlow = z2;
    }

    public final void setIndicatorFlowStep(int i2) {
        this.indicatorFlowStep = i2;
    }

    public final void setIndicatorGravity(int i2) {
        this.indicatorGravity = i2;
    }

    public final void setIndicatorHeight(int i2) {
        this.indicatorHeight = i2;
    }

    public final void setIndicatorHeightOffset(int i2) {
        this.indicatorHeightOffset = i2;
    }

    public final void setIndicatorStyle(int i2) {
        this.indicatorStyle = i2;
    }

    public final void setIndicatorWidth(int i2) {
        this.indicatorWidth = i2;
    }

    public final void setIndicatorWidthOffset(int i2) {
        this.indicatorWidthOffset = i2;
    }

    public final void setIndicatorXOffset(int i2) {
        this.indicatorXOffset = i2;
    }

    public final void setIndicatorYOffset(int i2) {
        this.indicatorYOffset = i2;
    }

    public final void setPositionOffset(float f2) {
        this.positionOffset = f2;
        invalidateSelf();
    }

    public final void set_targetIndex(int i2) {
        this._targetIndex = i2;
    }

    public void targetChildView(int index, @NotNull Function2<? super View, ? super View, Unit> onChildView) {
        Intrinsics.checkNotNullParameter(onChildView, "onChildView");
        View view = (View) CollectionsKt___CollectionsKt.getOrNull(this.tabLayout.getDslSelector().getVisibleViewList(), index);
        if (view != null) {
            onChildView.invoke(view, indicatorContentView(view));
        }
    }

    @Nullable
    public Drawable tintDrawableColor(@Nullable Drawable drawable, int color) {
        return (drawable == null || color == -2) ? drawable : LibExKt.tintDrawableColor(drawable, color);
    }

    @Override // com.angcyo.tablayout.DslGradientDrawable
    @Nullable
    public GradientDrawable updateOriginDrawable() {
        GradientDrawable gradientDrawableUpdateOriginDrawable = super.updateOriginDrawable();
        setIndicatorDrawable(getOriginDrawable());
        return gradientDrawableUpdateOriginDrawable;
    }
}
