package com.zhpan.bannerview.indicator;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import androidx.annotation.DrawableRes;
import androidx.core.content.ContextCompat;
import com.huawei.hms.push.constant.RemoteMessageConst;
import com.plv.business.sub.danmaku.entity.PLVDanmakuInfo;
import com.umeng.analytics.pro.d;
import com.zhpan.indicator.base.BaseIndicatorView;
import kotlin.Metadata;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt___RangesKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0016\u0018\u00002\u00020\u0001:\u0001.B'\b\u0007\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ*\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u00072\u0006\u0010\u001b\u001a\u00020\u00072\b\u0010\u001c\u001a\u0004\u0018\u00010\fH\u0002J\u001a\u0010\u001d\u001a\u0004\u0018\u00010\f2\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u001e\u001a\u00020\u0007H\u0002J\b\u0010\u001f\u001a\u00020\u0017H\u0002J\u0010\u0010 \u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0019H\u0014J\u0018\u0010!\u001a\u00020\u00172\u0006\u0010\"\u001a\u00020\u00072\u0006\u0010#\u001a\u00020\u0007H\u0014J\u001a\u0010$\u001a\u00020\u00002\b\b\u0001\u0010%\u001a\u00020\u00072\b\b\u0001\u0010&\u001a\u00020\u0007J\u000e\u0010'\u001a\u00020\u00002\u0006\u0010(\u001a\u00020\u0007J&\u0010)\u001a\u00020\u00002\u0006\u0010*\u001a\u00020\u00072\u0006\u0010+\u001a\u00020\u00072\u0006\u0010,\u001a\u00020\u00072\u0006\u0010-\u001a\u00020\u0007R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0010\u001a\u0004\u0018\u00010\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0012\u001a\u0004\u0018\u00010\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006/"}, d2 = {"Lcom/zhpan/bannerview/indicator/DrawableIndicator;", "Lcom/zhpan/indicator/base/BaseIndicatorView;", d.R, "Landroid/content/Context;", "attrs", "Landroid/util/AttributeSet;", "defStyleAttr", "", "(Landroid/content/Context;Landroid/util/AttributeSet;I)V", "checkCanResize", "", "mCheckedBitmap", "Landroid/graphics/Bitmap;", "mCheckedBitmapHeight", "mCheckedBitmapWidth", "mIndicatorPadding", "mIndicatorSize", "Lcom/zhpan/bannerview/indicator/DrawableIndicator$IndicatorSize;", "mNormalBitmap", "mNormalBitmapHeight", "mNormalBitmapWidth", "normalCanResize", "drawIcon", "", "canvas", "Landroid/graphics/Canvas;", "left", PLVDanmakuInfo.FONTMODE_TOP, RemoteMessageConst.Notification.ICON, "getBitmapFromVectorDrawable", "drawableId", "initIconSize", "onDraw", "onMeasure", "widthMeasureSpec", "heightMeasureSpec", "setIndicatorDrawable", "normalDrawable", "checkedDrawable", "setIndicatorGap", "padding", "setIndicatorSize", "normalWidth", "normalHeight", "checkedWidth", "checkedHeight", "IndicatorSize", "mvvm_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes8.dex */
public final class DrawableIndicator extends BaseIndicatorView {
    private boolean checkCanResize;

    @Nullable
    private Bitmap mCheckedBitmap;
    private int mCheckedBitmapHeight;
    private int mCheckedBitmapWidth;
    private int mIndicatorPadding;

    @Nullable
    private IndicatorSize mIndicatorSize;

    @Nullable
    private Bitmap mNormalBitmap;
    private int mNormalBitmapHeight;
    private int mNormalBitmapWidth;
    private boolean normalCanResize;

    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u000f\b\u0000\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003¢\u0006\u0002\u0010\u0007R\u001a\u0010\u0006\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u001a\u0010\u0005\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\t\"\u0004\b\r\u0010\u000bR\u001a\u0010\u0004\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\t\"\u0004\b\u000f\u0010\u000bR\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\t\"\u0004\b\u0011\u0010\u000b¨\u0006\u0012"}, d2 = {"Lcom/zhpan/bannerview/indicator/DrawableIndicator$IndicatorSize;", "", "normalWidth", "", "normalHeight", "checkedWidth", "checkedHeight", "(IIII)V", "getCheckedHeight", "()I", "setCheckedHeight", "(I)V", "getCheckedWidth", "setCheckedWidth", "getNormalHeight", "setNormalHeight", "getNormalWidth", "setNormalWidth", "mvvm_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class IndicatorSize {
        private int checkedHeight;
        private int checkedWidth;
        private int normalHeight;
        private int normalWidth;

        public IndicatorSize(int i2, int i3, int i4, int i5) {
            this.normalWidth = i2;
            this.normalHeight = i3;
            this.checkedWidth = i4;
            this.checkedHeight = i5;
        }

        public final int getCheckedHeight() {
            return this.checkedHeight;
        }

        public final int getCheckedWidth() {
            return this.checkedWidth;
        }

        public final int getNormalHeight() {
            return this.normalHeight;
        }

        public final int getNormalWidth() {
            return this.normalWidth;
        }

        public final void setCheckedHeight(int i2) {
            this.checkedHeight = i2;
        }

        public final void setCheckedWidth(int i2) {
            this.checkedWidth = i2;
        }

        public final void setNormalHeight(int i2) {
            this.normalHeight = i2;
        }

        public final void setNormalWidth(int i2) {
            this.normalWidth = i2;
        }
    }

    @JvmOverloads
    public DrawableIndicator(@Nullable Context context) {
        this(context, null, 0, 6, null);
    }

    @JvmOverloads
    public DrawableIndicator(@Nullable Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0, 4, null);
    }

    public /* synthetic */ DrawableIndicator(Context context, AttributeSet attributeSet, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i3 & 2) != 0 ? null : attributeSet, (i3 & 4) != 0 ? 0 : i2);
    }

    private final void drawIcon(Canvas canvas, int left, int top2, Bitmap icon) {
        if (icon == null) {
            return;
        }
        canvas.drawBitmap(icon, left, top2, (Paint) null);
    }

    private final Bitmap getBitmapFromVectorDrawable(Context context, int drawableId) {
        Drawable drawable = ContextCompat.getDrawable(context, drawableId);
        if (drawable == null) {
            return null;
        }
        Intrinsics.checkNotNull(drawable);
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmapCreateBitmap;
    }

    private final void initIconSize() {
        Bitmap bitmap = this.mCheckedBitmap;
        if (bitmap != null) {
            if (this.mIndicatorSize != null) {
                Intrinsics.checkNotNull(bitmap);
                if (bitmap.isMutable() && this.checkCanResize) {
                    Bitmap bitmap2 = this.mCheckedBitmap;
                    Intrinsics.checkNotNull(bitmap2);
                    IndicatorSize indicatorSize = this.mIndicatorSize;
                    Intrinsics.checkNotNull(indicatorSize);
                    bitmap2.setWidth(indicatorSize.getCheckedWidth());
                    Bitmap bitmap3 = this.mCheckedBitmap;
                    Intrinsics.checkNotNull(bitmap3);
                    IndicatorSize indicatorSize2 = this.mIndicatorSize;
                    Intrinsics.checkNotNull(indicatorSize2);
                    bitmap3.setHeight(indicatorSize2.getCheckedHeight());
                } else {
                    Bitmap bitmap4 = this.mCheckedBitmap;
                    Intrinsics.checkNotNull(bitmap4);
                    int width = bitmap4.getWidth();
                    Bitmap bitmap5 = this.mCheckedBitmap;
                    Intrinsics.checkNotNull(bitmap5);
                    int height = bitmap5.getHeight();
                    Intrinsics.checkNotNull(this.mIndicatorSize);
                    Intrinsics.checkNotNull(this.mIndicatorSize);
                    Matrix matrix = new Matrix();
                    matrix.postScale(r0.getCheckedWidth() / width, r1.getCheckedHeight() / height);
                    Bitmap bitmap6 = this.mCheckedBitmap;
                    Intrinsics.checkNotNull(bitmap6);
                    this.mCheckedBitmap = Bitmap.createBitmap(bitmap6, 0, 0, width, height, matrix, true);
                }
            }
            Bitmap bitmap7 = this.mCheckedBitmap;
            Intrinsics.checkNotNull(bitmap7);
            this.mCheckedBitmapWidth = bitmap7.getWidth();
            Bitmap bitmap8 = this.mCheckedBitmap;
            Intrinsics.checkNotNull(bitmap8);
            this.mCheckedBitmapHeight = bitmap8.getHeight();
        }
        Bitmap bitmap9 = this.mNormalBitmap;
        if (bitmap9 != null) {
            if (this.mIndicatorSize != null) {
                Intrinsics.checkNotNull(bitmap9);
                if (bitmap9.isMutable() && this.normalCanResize) {
                    Bitmap bitmap10 = this.mNormalBitmap;
                    Intrinsics.checkNotNull(bitmap10);
                    IndicatorSize indicatorSize3 = this.mIndicatorSize;
                    Intrinsics.checkNotNull(indicatorSize3);
                    bitmap10.setWidth(indicatorSize3.getNormalWidth());
                    Bitmap bitmap11 = this.mNormalBitmap;
                    Intrinsics.checkNotNull(bitmap11);
                    IndicatorSize indicatorSize4 = this.mIndicatorSize;
                    Intrinsics.checkNotNull(indicatorSize4);
                    bitmap11.setHeight(indicatorSize4.getNormalHeight());
                } else {
                    Bitmap bitmap12 = this.mNormalBitmap;
                    Intrinsics.checkNotNull(bitmap12);
                    int width2 = bitmap12.getWidth();
                    Bitmap bitmap13 = this.mNormalBitmap;
                    Intrinsics.checkNotNull(bitmap13);
                    int height2 = bitmap13.getHeight();
                    IndicatorSize indicatorSize5 = this.mIndicatorSize;
                    Intrinsics.checkNotNull(indicatorSize5);
                    float normalWidth = indicatorSize5.getNormalWidth();
                    Intrinsics.checkNotNull(this.mNormalBitmap);
                    float width3 = normalWidth / r1.getWidth();
                    IndicatorSize indicatorSize6 = this.mIndicatorSize;
                    Intrinsics.checkNotNull(indicatorSize6);
                    float normalHeight = indicatorSize6.getNormalHeight();
                    Intrinsics.checkNotNull(this.mNormalBitmap);
                    Matrix matrix2 = new Matrix();
                    matrix2.postScale(width3, normalHeight / r2.getHeight());
                    Bitmap bitmap14 = this.mNormalBitmap;
                    Intrinsics.checkNotNull(bitmap14);
                    this.mNormalBitmap = Bitmap.createBitmap(bitmap14, 0, 0, width2, height2, matrix2, true);
                }
            }
            Bitmap bitmap15 = this.mNormalBitmap;
            Intrinsics.checkNotNull(bitmap15);
            this.mNormalBitmapWidth = bitmap15.getWidth();
            Bitmap bitmap16 = this.mNormalBitmap;
            Intrinsics.checkNotNull(bitmap16);
            this.mNormalBitmapHeight = bitmap16.getHeight();
        }
    }

    @Override // android.view.View
    public void onDraw(@NotNull Canvas canvas) {
        int i2;
        int measuredHeight;
        int i3;
        int measuredHeight2;
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        super.onDraw(canvas);
        if (getPageSize() <= 1 || this.mCheckedBitmap == null || this.mNormalBitmap == null) {
            return;
        }
        int pageSize = getPageSize() + 1;
        for (int i4 = 1; i4 < pageSize; i4++) {
            Bitmap bitmap = this.mNormalBitmap;
            int i5 = i4 - 1;
            if (i5 < getCurrentPosition()) {
                i2 = i5 * (this.mNormalBitmapWidth + this.mIndicatorPadding);
                measuredHeight = getMeasuredHeight() / 2;
                i3 = this.mNormalBitmapHeight / 2;
            } else if (i5 == getCurrentPosition()) {
                i2 = i5 * (this.mNormalBitmapWidth + this.mIndicatorPadding);
                measuredHeight2 = (getMeasuredHeight() / 2) - (this.mCheckedBitmapHeight / 2);
                bitmap = this.mCheckedBitmap;
                drawIcon(canvas, i2, measuredHeight2, bitmap);
            } else {
                i2 = (i5 * this.mIndicatorPadding) + ((i4 - 2) * this.mNormalBitmapWidth) + this.mCheckedBitmapWidth;
                measuredHeight = getMeasuredHeight() / 2;
                i3 = this.mNormalBitmapHeight / 2;
            }
            measuredHeight2 = measuredHeight - i3;
            drawIcon(canvas, i2, measuredHeight2, bitmap);
        }
    }

    @Override // android.view.View
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(this.mCheckedBitmapWidth + ((this.mNormalBitmapWidth + this.mIndicatorPadding) * (getPageSize() - 1)), RangesKt___RangesKt.coerceAtLeast(this.mCheckedBitmapHeight, this.mNormalBitmapHeight));
    }

    @NotNull
    public final DrawableIndicator setIndicatorDrawable(@DrawableRes int normalDrawable, @DrawableRes int checkedDrawable) {
        this.mNormalBitmap = BitmapFactory.decodeResource(getResources(), normalDrawable);
        this.mCheckedBitmap = BitmapFactory.decodeResource(getResources(), checkedDrawable);
        if (this.mNormalBitmap == null) {
            Context context = getContext();
            Intrinsics.checkNotNullExpressionValue(context, "context");
            this.mNormalBitmap = getBitmapFromVectorDrawable(context, normalDrawable);
            this.normalCanResize = false;
        }
        if (this.mCheckedBitmap == null) {
            Context context2 = getContext();
            Intrinsics.checkNotNullExpressionValue(context2, "context");
            this.mCheckedBitmap = getBitmapFromVectorDrawable(context2, checkedDrawable);
            this.checkCanResize = false;
        }
        initIconSize();
        postInvalidate();
        return this;
    }

    @NotNull
    public final DrawableIndicator setIndicatorGap(int padding) {
        if (padding >= 0) {
            this.mIndicatorPadding = padding;
            postInvalidate();
        }
        return this;
    }

    @NotNull
    public final DrawableIndicator setIndicatorSize(int normalWidth, int normalHeight, int checkedWidth, int checkedHeight) {
        this.mIndicatorSize = new IndicatorSize(normalWidth, normalHeight, checkedWidth, checkedHeight);
        initIconSize();
        postInvalidate();
        return this;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    @JvmOverloads
    public DrawableIndicator(@Nullable Context context, @Nullable AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        Intrinsics.checkNotNull(context);
        this.normalCanResize = true;
        this.checkCanResize = true;
    }
}
