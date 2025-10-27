package com.ykb.ebook.page.delegate;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Region;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.view.MotionEvent;
import androidx.core.app.NotificationCompat;
import com.huawei.hms.support.hianalytics.HiAnalyticsConstant;
import com.ykb.ebook.common.ReadConfig;
import com.ykb.ebook.extensions.ViewExtensionsKt;
import com.ykb.ebook.model.BookInfo;
import com.ykb.ebook.model.Chapter;
import com.ykb.ebook.page.PageDirection;
import com.ykb.ebook.page.ReadBook;
import com.ykb.ebook.util.Log;
import com.ykb.ebook.weight.ReadView;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\u0086\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0015\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\b\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0014\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u0002\n\u0002\b\u0013\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\t\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010@\u001a\u00020A2\u0006\u0010B\u001a\u00020 2\u0006\u0010C\u001a\u00020 H\u0002J\b\u0010D\u001a\u00020AH\u0002J\u001a\u0010E\u001a\u00020A2\u0006\u0010\u0005\u001a\u00020\u00062\b\u0010F\u001a\u0004\u0018\u00010\bH\u0002J\u001a\u0010G\u001a\u00020A2\u0006\u0010\u0005\u001a\u00020\u00062\b\u0010F\u001a\u0004\u0018\u00010\bH\u0002J\u0010\u0010H\u001a\u00020A2\u0006\u0010\u0005\u001a\u00020\u0006H\u0002J\u001a\u0010I\u001a\u00020A2\u0006\u0010\u0005\u001a\u00020\u00062\b\u0010F\u001a\u0004\u0018\u00010\bH\u0002J(\u0010J\u001a\u00020\u00122\u0006\u0010K\u001a\u00020\u00122\u0006\u0010L\u001a\u00020\u00122\u0006\u0010M\u001a\u00020\u00122\u0006\u0010N\u001a\u00020\u0012H\u0002J\u0010\u0010O\u001a\u00020A2\u0006\u0010P\u001a\u00020\u001dH\u0016J\b\u0010Q\u001a\u00020AH\u0016J\u0010\u0010R\u001a\u00020A2\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u0010\u0010S\u001a\u00020A2\u0006\u0010T\u001a\u00020UH\u0016J\b\u0010V\u001a\u00020AH\u0016J\u0010\u0010W\u001a\u00020A2\u0006\u0010X\u001a\u00020YH\u0016J\u001a\u0010Z\u001a\u00020A2\b\u0010[\u001a\u0004\u0018\u00010\b2\b\u0010\\\u001a\u0004\u0018\u00010\bJ\u000e\u0010]\u001a\u00020A2\u0006\u0010^\u001a\u00020\bJ\u0018\u0010_\u001a\u00020A2\u0006\u0010`\u001a\u00020\u001d2\u0006\u0010a\u001a\u00020\u001dH\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u0004\u0018\u00010\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u0004\u0018\u00010\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u000b\u001a\u0004\u0018\u00010\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0012X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0012X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u001bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u001dX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u001dX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020 X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010!\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\"\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010#\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010$\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010%\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010&\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010'\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010(\u001a\u00020)X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010*\u001a\u00020+X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010,\u001a\u00020-X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010.\u001a\u00020 X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010/\u001a\u00020 X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u00100\u001a\u00020 X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u00101\u001a\u000202X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u00103\u001a\u000204X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u00105\u001a\u000204X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u00106\u001a\u00020 X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u00107\u001a\u00020 X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u00108\u001a\u00020 X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u00109\u001a\u0004\u0018\u00010\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010:\u001a\u00020)8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b;\u0010<R\u0014\u0010=\u001a\u00020)8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b>\u0010<R\u0010\u0010?\u001a\u0004\u0018\u00010\bX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006b"}, d2 = {"Lcom/ykb/ebook/page/delegate/SimulationPageDelegate;", "Lcom/ykb/ebook/page/delegate/HorizontalPageDelegate;", "readView", "Lcom/ykb/ebook/weight/ReadView;", "(Lcom/ykb/ebook/weight/ReadView;)V", "canvas", "Landroid/graphics/Canvas;", "curBitmap", "Landroid/graphics/Bitmap;", "firstPageBitMap", "lastPageBitMap", "lastPrePageBitMap", "mBackShadowColors", "", "mBackShadowDrawableLR", "Landroid/graphics/drawable/GradientDrawable;", "mBackShadowDrawableRL", "mBezierControl1", "Landroid/graphics/PointF;", "mBezierControl2", "mBezierEnd1", "mBezierEnd2", "mBezierStart1", "mBezierStart2", "mBezierVertex1", "mBezierVertex2", "mColorMatrixFilter", "Landroid/graphics/ColorMatrixColorFilter;", "mCornerX", "", "mCornerY", "mDegrees", "", "mFolderShadowDrawableLR", "mFolderShadowDrawableRL", "mFrontShadowColors", "mFrontShadowDrawableHBT", "mFrontShadowDrawableHTB", "mFrontShadowDrawableVLR", "mFrontShadowDrawableVRL", "mIsRtOrLb", "", "mMatrix", "Landroid/graphics/Matrix;", "mMatrixArray", "", "mMaxLength", "mMiddleX", "mMiddleY", "mPaint", "Landroid/graphics/Paint;", "mPath0", "Landroid/graphics/Path;", "mPath1", "mTouchToCornerDis", "mTouchX", "mTouchY", "nextBitmap", "nextCondition", "getNextCondition", "()Z", "preCondition", "getPreCondition", "prevBitmap", "calcCornerXY", "", "x", "y", "calcPoints", "drawCurrentBackArea", "bitmap", "drawCurrentPageArea", "drawCurrentPageShadow", "drawNextPageAreaAndShadow", "getCross", "p1", "p2", "p3", "p4", "onAnimStart", "animationSpeed", "onAnimStop", "onDraw", "onTouch", NotificationCompat.CATEGORY_EVENT, "Landroid/view/MotionEvent;", "setBitmap", "setDirection", HiAnalyticsConstant.HaKey.BI_KEY_DIRECTION, "Lcom/ykb/ebook/page/PageDirection;", "setFirstLastPageBitMap", "first", "last", "setLastPreBitMap", "b", "setViewSize", "width", "height", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nSimulationPageDelegate.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SimulationPageDelegate.kt\ncom/ykb/ebook/page/delegate/SimulationPageDelegate\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,687:1\n1#2:688\n*E\n"})
/* loaded from: classes7.dex */
public final class SimulationPageDelegate extends HorizontalPageDelegate {

    @NotNull
    private Canvas canvas;

    @Nullable
    private Bitmap curBitmap;

    @Nullable
    private Bitmap firstPageBitMap;

    @Nullable
    private Bitmap lastPageBitMap;

    @Nullable
    private Bitmap lastPrePageBitMap;

    @NotNull
    private int[] mBackShadowColors;

    @NotNull
    private GradientDrawable mBackShadowDrawableLR;

    @NotNull
    private GradientDrawable mBackShadowDrawableRL;

    @NotNull
    private final PointF mBezierControl1;

    @NotNull
    private final PointF mBezierControl2;

    @NotNull
    private PointF mBezierEnd1;

    @NotNull
    private PointF mBezierEnd2;

    @NotNull
    private final PointF mBezierStart1;

    @NotNull
    private final PointF mBezierStart2;

    @NotNull
    private final PointF mBezierVertex1;

    @NotNull
    private final PointF mBezierVertex2;

    @NotNull
    private ColorMatrixColorFilter mColorMatrixFilter;
    private int mCornerX;
    private int mCornerY;
    private float mDegrees;

    @NotNull
    private GradientDrawable mFolderShadowDrawableLR;

    @NotNull
    private GradientDrawable mFolderShadowDrawableRL;

    @NotNull
    private int[] mFrontShadowColors;

    @NotNull
    private GradientDrawable mFrontShadowDrawableHBT;

    @NotNull
    private GradientDrawable mFrontShadowDrawableHTB;

    @NotNull
    private GradientDrawable mFrontShadowDrawableVLR;

    @NotNull
    private GradientDrawable mFrontShadowDrawableVRL;
    private boolean mIsRtOrLb;

    @NotNull
    private final Matrix mMatrix;

    @NotNull
    private final float[] mMatrixArray;
    private float mMaxLength;
    private float mMiddleX;
    private float mMiddleY;

    @NotNull
    private final Paint mPaint;

    @NotNull
    private final Path mPath0;

    @NotNull
    private final Path mPath1;
    private float mTouchToCornerDis;
    private float mTouchX;
    private float mTouchY;

    @Nullable
    private Bitmap nextBitmap;

    @Nullable
    private Bitmap prevBitmap;

    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[PageDirection.values().length];
            try {
                iArr[PageDirection.PREV.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[PageDirection.NEXT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SimulationPageDelegate(@NotNull ReadView readView) {
        super(readView);
        Intrinsics.checkNotNullParameter(readView, "readView");
        this.mTouchX = 0.1f;
        this.mTouchY = 0.1f;
        this.mCornerX = 1;
        this.mCornerY = 1;
        this.mPath0 = new Path();
        this.mPath1 = new Path();
        this.mBezierStart1 = new PointF();
        this.mBezierControl1 = new PointF();
        this.mBezierVertex1 = new PointF();
        this.mBezierEnd1 = new PointF();
        this.mBezierStart2 = new PointF();
        this.mBezierControl2 = new PointF();
        this.mBezierVertex2 = new PointF();
        this.mBezierEnd2 = new PointF();
        this.mColorMatrixFilter = new ColorMatrixColorFilter(new ColorMatrix(new float[]{1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f}));
        this.mMatrix = new Matrix();
        this.mMatrixArray = new float[]{0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f};
        this.mMaxLength = (float) Math.hypot(getViewWidth(), getViewHeight());
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        this.mPaint = paint;
        this.canvas = new Canvas();
        int[] iArr = {3355443, -1338821837};
        GradientDrawable gradientDrawable = new GradientDrawable(GradientDrawable.Orientation.RIGHT_LEFT, iArr);
        this.mFolderShadowDrawableRL = gradientDrawable;
        gradientDrawable.setGradientType(0);
        GradientDrawable gradientDrawable2 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, iArr);
        this.mFolderShadowDrawableLR = gradientDrawable2;
        gradientDrawable2.setGradientType(0);
        this.mBackShadowColors = new int[]{-15658735, 1118481};
        GradientDrawable gradientDrawable3 = new GradientDrawable(GradientDrawable.Orientation.RIGHT_LEFT, this.mBackShadowColors);
        this.mBackShadowDrawableRL = gradientDrawable3;
        gradientDrawable3.setGradientType(0);
        GradientDrawable gradientDrawable4 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, this.mBackShadowColors);
        this.mBackShadowDrawableLR = gradientDrawable4;
        gradientDrawable4.setGradientType(0);
        this.mFrontShadowColors = new int[]{-2146365167, 1118481};
        GradientDrawable gradientDrawable5 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, this.mFrontShadowColors);
        this.mFrontShadowDrawableVLR = gradientDrawable5;
        gradientDrawable5.setGradientType(0);
        GradientDrawable gradientDrawable6 = new GradientDrawable(GradientDrawable.Orientation.RIGHT_LEFT, this.mFrontShadowColors);
        this.mFrontShadowDrawableVRL = gradientDrawable6;
        gradientDrawable6.setGradientType(0);
        GradientDrawable gradientDrawable7 = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, this.mFrontShadowColors);
        this.mFrontShadowDrawableHTB = gradientDrawable7;
        gradientDrawable7.setGradientType(0);
        GradientDrawable gradientDrawable8 = new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP, this.mFrontShadowColors);
        this.mFrontShadowDrawableHBT = gradientDrawable8;
        gradientDrawable8.setGradientType(0);
    }

    private final void calcCornerXY(float x2, float y2) {
        boolean z2 = false;
        this.mCornerX = x2 <= ((float) (getViewWidth() / 2)) ? 0 : getViewWidth();
        int viewHeight = y2 <= ((float) (getViewHeight() / 2)) ? 0 : getViewHeight();
        this.mCornerY = viewHeight;
        if ((this.mCornerX == 0 && viewHeight == getViewHeight()) || (this.mCornerY == 0 && this.mCornerX == getViewWidth())) {
            z2 = true;
        }
        this.mIsRtOrLb = z2;
    }

    private final void calcPoints() {
        this.mTouchX = getTouchX();
        float touchY = getTouchY();
        this.mTouchY = touchY;
        float f2 = this.mTouchX;
        int i2 = this.mCornerX;
        float f3 = 2;
        float f4 = (i2 + f2) / f3;
        this.mMiddleX = f4;
        int i3 = this.mCornerY;
        float f5 = (touchY + i3) / f3;
        this.mMiddleY = f5;
        PointF pointF = this.mBezierControl1;
        pointF.x = f4 - (((i3 - f5) * (i3 - f5)) / (i2 - f4));
        pointF.y = i3;
        PointF pointF2 = this.mBezierControl2;
        pointF2.x = i2;
        if (((float) i3) - f5 == 0.0f) {
            pointF2.y = f5 - (((i2 - f4) * (i2 - f4)) / 0.1f);
        } else {
            pointF2.y = f5 - (((i2 - f4) * (i2 - f4)) / (i3 - f5));
        }
        PointF pointF3 = this.mBezierStart1;
        float f6 = pointF.x;
        pointF3.x = f6 - ((i2 - f6) / f3);
        pointF3.y = i3;
        if (f2 > 0.0f && f2 < getViewWidth()) {
            float f7 = this.mBezierStart1.x;
            if (f7 < 0.0f || f7 > getViewWidth()) {
                PointF pointF4 = this.mBezierStart1;
                if (pointF4.x < 0.0f) {
                    pointF4.x = getViewWidth() - this.mBezierStart1.x;
                }
                float fAbs = Math.abs(this.mCornerX - this.mTouchX);
                float fAbs2 = Math.abs(this.mCornerX - ((getViewWidth() * fAbs) / this.mBezierStart1.x));
                this.mTouchX = fAbs2;
                float fAbs3 = Math.abs(this.mCornerY - ((Math.abs(this.mCornerX - fAbs2) * Math.abs(this.mCornerY - this.mTouchY)) / fAbs));
                this.mTouchY = fAbs3;
                float f8 = this.mTouchX;
                int i4 = this.mCornerX;
                float f9 = (f8 + i4) / f3;
                this.mMiddleX = f9;
                int i5 = this.mCornerY;
                float f10 = (fAbs3 + i5) / f3;
                this.mMiddleY = f10;
                PointF pointF5 = this.mBezierControl1;
                pointF5.x = f9 - (((i5 - f10) * (i5 - f10)) / (i4 - f9));
                pointF5.y = i5;
                PointF pointF6 = this.mBezierControl2;
                pointF6.x = i4;
                if (((float) i5) - f10 == 0.0f) {
                    pointF6.y = f10 - (((i4 - f9) * (i4 - f9)) / 0.1f);
                } else {
                    pointF6.y = f10 - (((i4 - f9) * (i4 - f9)) / (i5 - f10));
                }
                PointF pointF7 = this.mBezierStart1;
                float f11 = pointF5.x;
                pointF7.x = f11 - ((i4 - f11) / f3);
            }
        }
        PointF pointF8 = this.mBezierStart2;
        pointF8.x = this.mCornerX;
        float f12 = this.mBezierControl2.y;
        pointF8.y = f12 - ((this.mCornerY - f12) / f3);
        this.mTouchToCornerDis = (float) Math.hypot(this.mTouchX - r1, this.mTouchY - r3);
        this.mBezierEnd1 = getCross(new PointF(this.mTouchX, this.mTouchY), this.mBezierControl1, this.mBezierStart1, this.mBezierStart2);
        PointF cross = getCross(new PointF(this.mTouchX, this.mTouchY), this.mBezierControl2, this.mBezierStart1, this.mBezierStart2);
        this.mBezierEnd2 = cross;
        PointF pointF9 = this.mBezierVertex1;
        PointF pointF10 = this.mBezierStart1;
        float f13 = pointF10.x;
        PointF pointF11 = this.mBezierControl1;
        float f14 = f13 + (pointF11.x * f3);
        PointF pointF12 = this.mBezierEnd1;
        float f15 = 4;
        pointF9.x = (f14 + pointF12.x) / f15;
        pointF9.y = (((pointF11.y * f3) + pointF10.y) + pointF12.y) / f15;
        PointF pointF13 = this.mBezierVertex2;
        PointF pointF14 = this.mBezierStart2;
        float f16 = pointF14.x;
        PointF pointF15 = this.mBezierControl2;
        pointF13.x = ((f16 + (pointF15.x * f3)) + cross.x) / f15;
        pointF13.y = (((f3 * pointF15.y) + pointF14.y) + cross.y) / f15;
    }

    private final void drawCurrentBackArea(Canvas canvas, Bitmap bitmap) {
        int i2;
        int i3;
        GradientDrawable gradientDrawable;
        if (bitmap == null) {
            return;
        }
        float f2 = this.mBezierStart1.x;
        float f3 = 2;
        float fAbs = Math.abs(((int) ((f2 + r1) / f3)) - this.mBezierControl1.x);
        float f4 = this.mBezierStart2.y;
        float fMin = Math.min(fAbs, Math.abs(((int) ((f4 + r3) / f3)) - this.mBezierControl2.y));
        this.mPath1.reset();
        Path path = this.mPath1;
        PointF pointF = this.mBezierVertex2;
        path.moveTo(pointF.x, pointF.y);
        Path path2 = this.mPath1;
        PointF pointF2 = this.mBezierVertex1;
        path2.lineTo(pointF2.x, pointF2.y);
        Path path3 = this.mPath1;
        PointF pointF3 = this.mBezierEnd1;
        path3.lineTo(pointF3.x, pointF3.y);
        this.mPath1.lineTo(this.mTouchX, this.mTouchY);
        Path path4 = this.mPath1;
        PointF pointF4 = this.mBezierEnd2;
        path4.lineTo(pointF4.x, pointF4.y);
        this.mPath1.close();
        if (this.mIsRtOrLb) {
            float f5 = this.mBezierStart1.x;
            float f6 = 1;
            i2 = (int) (f5 - f6);
            i3 = (int) (f5 + fMin + f6);
            gradientDrawable = this.mFolderShadowDrawableLR;
        } else {
            float f7 = this.mBezierStart1.x;
            float f8 = 1;
            i2 = (int) ((f7 - fMin) - f8);
            i3 = (int) (f7 + f8);
            gradientDrawable = this.mFolderShadowDrawableRL;
        }
        canvas.save();
        canvas.clipPath(this.mPath0);
        if (Build.VERSION.SDK_INT >= 26) {
            canvas.clipPath(this.mPath1);
        } else {
            canvas.clipPath(this.mPath1, Region.Op.INTERSECT);
        }
        this.mPaint.setColorFilter(this.mColorMatrixFilter);
        float fHypot = (float) Math.hypot(this.mCornerX - this.mBezierControl1.x, this.mBezierControl2.y - this.mCornerY);
        float f9 = (this.mCornerX - this.mBezierControl1.x) / fHypot;
        float f10 = (this.mBezierControl2.y - this.mCornerY) / fHypot;
        float[] fArr = this.mMatrixArray;
        float f11 = 1;
        fArr[0] = f11 - ((f3 * f10) * f10);
        float f12 = f3 * f9;
        float f13 = f10 * f12;
        fArr[1] = f13;
        fArr[3] = f13;
        fArr[4] = f11 - (f12 * f9);
        this.mMatrix.reset();
        this.mMatrix.setValues(this.mMatrixArray);
        Matrix matrix = this.mMatrix;
        PointF pointF5 = this.mBezierControl1;
        matrix.preTranslate(-pointF5.x, -pointF5.y);
        Matrix matrix2 = this.mMatrix;
        PointF pointF6 = this.mBezierControl1;
        matrix2.postTranslate(pointF6.x, pointF6.y);
        canvas.drawColor(-1);
        canvas.drawBitmap(bitmap, this.mMatrix, this.mPaint);
        this.mPaint.setColorFilter(null);
        float f14 = this.mDegrees;
        PointF pointF7 = this.mBezierStart1;
        canvas.rotate(f14, pointF7.x, pointF7.y);
        float f15 = this.mBezierStart1.y;
        gradientDrawable.setBounds(i2, (int) f15, i3, (int) (f15 + this.mMaxLength));
        gradientDrawable.draw(canvas);
        canvas.restore();
    }

    private final void drawCurrentPageArea(Canvas canvas, Bitmap bitmap) {
        if (bitmap == null) {
            return;
        }
        this.mPath0.reset();
        Path path = this.mPath0;
        PointF pointF = this.mBezierStart1;
        path.moveTo(pointF.x, pointF.y);
        Path path2 = this.mPath0;
        PointF pointF2 = this.mBezierControl1;
        float f2 = pointF2.x;
        float f3 = pointF2.y;
        PointF pointF3 = this.mBezierEnd1;
        path2.quadTo(f2, f3, pointF3.x, pointF3.y);
        this.mPath0.lineTo(this.mTouchX, this.mTouchY);
        Path path3 = this.mPath0;
        PointF pointF4 = this.mBezierEnd2;
        path3.lineTo(pointF4.x, pointF4.y);
        Path path4 = this.mPath0;
        PointF pointF5 = this.mBezierControl2;
        float f4 = pointF5.x;
        float f5 = pointF5.y;
        PointF pointF6 = this.mBezierStart2;
        path4.quadTo(f4, f5, pointF6.x, pointF6.y);
        this.mPath0.lineTo(this.mCornerX, this.mCornerY);
        this.mPath0.close();
        canvas.save();
        if (Build.VERSION.SDK_INT >= 26) {
            canvas.clipOutPath(this.mPath0);
        } else {
            canvas.clipPath(this.mPath0, Region.Op.XOR);
        }
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, (Paint) null);
        canvas.restore();
    }

    private final void drawCurrentPageShadow(Canvas canvas) {
        double dAtan2;
        int i2;
        int i3;
        GradientDrawable gradientDrawable;
        int i4;
        int i5;
        GradientDrawable gradientDrawable2;
        if (this.mIsRtOrLb) {
            PointF pointF = this.mBezierControl1;
            dAtan2 = Math.atan2(pointF.y - this.mTouchY, this.mTouchX - pointF.x);
        } else {
            float f2 = this.mTouchY;
            PointF pointF2 = this.mBezierControl1;
            dAtan2 = Math.atan2(f2 - pointF2.y, this.mTouchX - pointF2.x);
        }
        double d3 = 0.7853981633974483d - ((float) dAtan2);
        double dCos = Math.cos(d3) * 35.35d;
        double dSin = Math.sin(d3) * 35.35d;
        float f3 = (float) (this.mTouchX + dCos);
        float f4 = (float) (this.mIsRtOrLb ? this.mTouchY + dSin : this.mTouchY - dSin);
        this.mPath1.reset();
        this.mPath1.moveTo(f3, f4);
        this.mPath1.lineTo(this.mTouchX, this.mTouchY);
        Path path = this.mPath1;
        PointF pointF3 = this.mBezierControl1;
        path.lineTo(pointF3.x, pointF3.y);
        Path path2 = this.mPath1;
        PointF pointF4 = this.mBezierStart1;
        path2.lineTo(pointF4.x, pointF4.y);
        this.mPath1.close();
        canvas.save();
        int i6 = Build.VERSION.SDK_INT;
        if (i6 >= 26) {
            canvas.clipOutPath(this.mPath0);
        } else {
            canvas.clipPath(this.mPath0, Region.Op.XOR);
        }
        canvas.clipPath(this.mPath1, Region.Op.INTERSECT);
        if (this.mIsRtOrLb) {
            float f5 = this.mBezierControl1.x;
            i2 = (int) f5;
            i3 = (int) (f5 + 25);
            gradientDrawable = this.mFrontShadowDrawableVLR;
        } else {
            float f6 = this.mBezierControl1.x;
            i2 = (int) (f6 - 25);
            i3 = (int) (f6 + 1);
            gradientDrawable = this.mFrontShadowDrawableVRL;
        }
        float f7 = this.mTouchX;
        PointF pointF5 = this.mBezierControl1;
        float degrees = (float) Math.toDegrees((float) Math.atan2(f7 - pointF5.x, pointF5.y - this.mTouchY));
        PointF pointF6 = this.mBezierControl1;
        canvas.rotate(degrees, pointF6.x, pointF6.y);
        float f8 = this.mBezierControl1.y;
        gradientDrawable.setBounds(i2, (int) (f8 - this.mMaxLength), i3, (int) f8);
        gradientDrawable.draw(canvas);
        canvas.restore();
        this.mPath1.reset();
        this.mPath1.moveTo(f3, f4);
        this.mPath1.lineTo(this.mTouchX, this.mTouchY);
        Path path3 = this.mPath1;
        PointF pointF7 = this.mBezierControl2;
        path3.lineTo(pointF7.x, pointF7.y);
        Path path4 = this.mPath1;
        PointF pointF8 = this.mBezierStart2;
        path4.lineTo(pointF8.x, pointF8.y);
        this.mPath1.close();
        canvas.save();
        if (i6 >= 26) {
            canvas.clipOutPath(this.mPath0);
        } else {
            canvas.clipPath(this.mPath0, Region.Op.XOR);
        }
        canvas.clipPath(this.mPath1);
        if (this.mIsRtOrLb) {
            float f9 = this.mBezierControl2.y;
            i4 = (int) f9;
            i5 = (int) (f9 + 25);
            gradientDrawable2 = this.mFrontShadowDrawableHTB;
        } else {
            float f10 = this.mBezierControl2.y;
            i4 = (int) (f10 - 25);
            i5 = (int) (f10 + 1);
            gradientDrawable2 = this.mFrontShadowDrawableHBT;
        }
        PointF pointF9 = this.mBezierControl2;
        float degrees2 = (float) Math.toDegrees((float) Math.atan2(pointF9.y - this.mTouchY, pointF9.x - this.mTouchX));
        PointF pointF10 = this.mBezierControl2;
        canvas.rotate(degrees2, pointF10.x, pointF10.y);
        float viewHeight = this.mBezierControl2.y;
        if (viewHeight < 0.0f) {
            viewHeight -= getViewHeight();
        }
        double dHypot = Math.hypot(this.mBezierControl2.x, viewHeight);
        float f11 = this.mMaxLength;
        if (dHypot > f11) {
            float f12 = this.mBezierControl2.x;
            gradientDrawable2.setBounds((int) ((f12 - 25) - dHypot), i4, (int) ((f12 + f11) - dHypot), i5);
        } else {
            float f13 = this.mBezierControl2.x;
            gradientDrawable2.setBounds((int) (f13 - f11), i4, (int) f13, i5);
        }
        gradientDrawable2.draw(canvas);
        canvas.restore();
    }

    private final void drawNextPageAreaAndShadow(Canvas canvas, Bitmap bitmap) {
        int i2;
        int i3;
        GradientDrawable gradientDrawable;
        if (bitmap == null) {
            return;
        }
        this.mPath1.reset();
        Path path = this.mPath1;
        PointF pointF = this.mBezierStart1;
        path.moveTo(pointF.x, pointF.y);
        Path path2 = this.mPath1;
        PointF pointF2 = this.mBezierVertex1;
        path2.lineTo(pointF2.x, pointF2.y);
        Path path3 = this.mPath1;
        PointF pointF3 = this.mBezierVertex2;
        path3.lineTo(pointF3.x, pointF3.y);
        Path path4 = this.mPath1;
        PointF pointF4 = this.mBezierStart2;
        path4.lineTo(pointF4.x, pointF4.y);
        this.mPath1.lineTo(this.mCornerX, this.mCornerY);
        this.mPath1.close();
        this.mDegrees = (float) Math.toDegrees(Math.atan2(this.mBezierControl1.x - this.mCornerX, this.mBezierControl2.y - this.mCornerY));
        if (this.mIsRtOrLb) {
            float f2 = this.mBezierStart1.x;
            i2 = (int) f2;
            i3 = (int) (f2 + (this.mTouchToCornerDis / 4));
            gradientDrawable = this.mBackShadowDrawableLR;
        } else {
            float f3 = this.mBezierStart1.x;
            i2 = (int) (f3 - (this.mTouchToCornerDis / 4));
            i3 = (int) f3;
            gradientDrawable = this.mBackShadowDrawableRL;
        }
        canvas.save();
        canvas.clipPath(this.mPath0);
        if (Build.VERSION.SDK_INT >= 26) {
            canvas.clipPath(this.mPath1);
        } else {
            canvas.clipPath(this.mPath1, Region.Op.INTERSECT);
        }
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, (Paint) null);
        float f4 = this.mDegrees;
        PointF pointF5 = this.mBezierStart1;
        canvas.rotate(f4, pointF5.x, pointF5.y);
        float f5 = this.mBezierStart1.y;
        gradientDrawable.setBounds(i2, (int) f5, i3, (int) (this.mMaxLength + f5));
        gradientDrawable.draw(canvas);
        canvas.restore();
    }

    private final PointF getCross(PointF p12, PointF p2, PointF p3, PointF p4) {
        PointF pointF = new PointF();
        float f2 = p2.y;
        float f3 = p12.y;
        float f4 = p2.x;
        float f5 = p12.x;
        float f6 = (f2 - f3) / (f4 - f5);
        float f7 = ((f2 * f5) - (f3 * f4)) / (f5 - f4);
        float f8 = p4.y;
        float f9 = p3.y;
        float f10 = p4.x;
        float f11 = p3.x;
        float f12 = ((((f8 * f11) - (f9 * f10)) / (f11 - f10)) - f7) / (f6 - ((f8 - f9) / (f10 - f11)));
        pointF.x = f12;
        pointF.y = (f6 * f12) + f7;
        return pointF;
    }

    private final boolean getNextCondition() {
        List<Chapter> chapterList;
        if (this.lastPageBitMap == null) {
            return false;
        }
        ReadBook readBook = ReadBook.INSTANCE;
        int durChapterIndex = readBook.getDurChapterIndex();
        BookInfo book = readBook.getBook();
        return durChapterIndex == ((book == null || (chapterList = book.getChapterList()) == null) ? 0 : chapterList.size()) - 1 && readBook.getDurPageIndex() == readBook.getCurrentChapterTotalPages() - 1 && !ReadConfig.INSTANCE.getLastPage();
    }

    private final boolean getPreCondition() {
        if (this.firstPageBitMap != null) {
            ReadBook readBook = ReadBook.INSTANCE;
            if (readBook.getDurChapterIndex() == 0 && readBook.getDurPageIndex() == 0 && !ReadConfig.INSTANCE.getFirstPage()) {
                return true;
            }
        }
        return false;
    }

    @Override // com.ykb.ebook.page.delegate.PageDelegate
    public void onAnimStart(int animationSpeed) {
        float viewWidth;
        float viewHeight;
        float touchY;
        float f2;
        Log.INSTANCE.logD("onAnimStart", "isCancel = " + getIsCancel());
        if (getIsCancel()) {
            viewWidth = (this.mCornerX <= 0 || getMDirection() != PageDirection.NEXT) ? -getTouchX() : getViewWidth() - getTouchX();
            if (getMDirection() != PageDirection.NEXT) {
                viewWidth = -(getViewWidth() + getTouchX());
            }
            if (this.mCornerY <= 0) {
                f2 = -getTouchY();
                startScroll((int) getTouchX(), (int) getTouchY(), (int) viewWidth, (int) f2, animationSpeed);
            } else {
                viewHeight = getViewHeight();
                touchY = getTouchY();
            }
        } else {
            viewWidth = (this.mCornerX <= 0 || getMDirection() != PageDirection.NEXT) ? getViewWidth() - getTouchX() : -(getViewWidth() + getTouchX());
            if (this.mCornerY > 0) {
                viewHeight = getViewHeight();
                touchY = getTouchY();
            } else {
                viewHeight = 1;
                touchY = getTouchY();
            }
        }
        f2 = viewHeight - touchY;
        startScroll((int) getTouchX(), (int) getTouchY(), (int) viewWidth, (int) f2, animationSpeed);
    }

    @Override // com.ykb.ebook.page.delegate.PageDelegate
    public void onAnimStop() throws SecurityException, NumberFormatException {
        if (getIsCancel()) {
            return;
        }
        getReadView().fillPage(getMDirection());
    }

    @Override // com.ykb.ebook.page.delegate.PageDelegate
    public void onDraw(@NotNull Canvas canvas) {
        Bitmap bitmap;
        Bitmap bitmap2;
        List<Chapter> chapterList;
        List<Chapter> chapterList2;
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        if (getIsRunning()) {
            int i2 = WhenMappings.$EnumSwitchMapping$0[getMDirection().ordinal()];
            boolean z2 = true;
            if (i2 == 1) {
                calcPoints();
                if (getPreCondition()) {
                    drawCurrentPageArea(canvas, this.firstPageBitMap);
                } else {
                    if (!ReadConfig.INSTANCE.getLastPage() || (bitmap = this.lastPrePageBitMap) == null) {
                        bitmap = this.prevBitmap;
                    }
                    drawCurrentPageArea(canvas, bitmap);
                }
                drawNextPageAreaAndShadow(canvas, this.curBitmap);
                drawCurrentPageShadow(canvas);
                if (getPreCondition()) {
                    drawCurrentBackArea(canvas, this.firstPageBitMap);
                    return;
                } else if (!ReadConfig.INSTANCE.getLastPage() || (bitmap2 = this.lastPrePageBitMap) == null) {
                    drawCurrentBackArea(canvas, this.prevBitmap);
                    return;
                } else {
                    drawCurrentBackArea(canvas, bitmap2);
                    return;
                }
            }
            if (i2 != 2) {
                return;
            }
            calcPoints();
            drawCurrentPageArea(canvas, this.curBitmap);
            ReadBook readBook = ReadBook.INSTANCE;
            BookInfo book = readBook.getBook();
            int size = ((book == null || (chapterList2 = book.getChapterList()) == null) ? 0 : chapterList2.size()) - 1;
            if (readBook.getDurChapterIndex() == size) {
                BookInfo book2 = readBook.getBook();
                Chapter chapter = (book2 == null || (chapterList = book2.getChapterList()) == null) ? null : chapterList.get(size);
                if ((chapter == null || chapter.isPay()) ? false : true) {
                    z2 = false;
                }
            }
            if (getNextCondition() || !z2) {
                drawNextPageAreaAndShadow(canvas, this.lastPageBitMap);
            } else {
                drawNextPageAreaAndShadow(canvas, this.nextBitmap);
            }
            drawCurrentPageShadow(canvas);
            drawCurrentBackArea(canvas, this.curBitmap);
        }
    }

    @Override // com.ykb.ebook.page.delegate.HorizontalPageDelegate, com.ykb.ebook.page.delegate.PageDelegate
    public void onTouch(@NotNull MotionEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        super.onTouch(event);
        int action = event.getAction();
        if (action == 0) {
            calcCornerXY(event.getX(), event.getY());
            return;
        }
        if (action != 2) {
            return;
        }
        if ((getStartY() > getViewHeight() / 3 && getStartY() < (getViewHeight() * 2) / 3) || getMDirection() == PageDirection.PREV) {
            getReadView().setTouchY(getViewHeight());
        }
        if (getStartY() <= getViewHeight() / 3 || getStartY() >= getViewHeight() / 2 || getMDirection() != PageDirection.NEXT) {
            return;
        }
        getReadView().setTouchY(1.0f);
    }

    @Override // com.ykb.ebook.page.delegate.HorizontalPageDelegate
    public void setBitmap() {
        int i2 = WhenMappings.$EnumSwitchMapping$0[getMDirection().ordinal()];
        if (i2 == 1) {
            this.prevBitmap = ViewExtensionsKt.screenshot(getPrevPage(), this.prevBitmap, this.canvas);
            this.curBitmap = ViewExtensionsKt.screenshot(getCurPage(), this.curBitmap, this.canvas);
        } else {
            if (i2 != 2) {
                return;
            }
            if (ReadConfig.INSTANCE.getFirstPage()) {
                getNextPage().hideFirstPage();
            }
            this.nextBitmap = ViewExtensionsKt.screenshot(getNextPage(), this.nextBitmap, this.canvas);
            this.curBitmap = ViewExtensionsKt.screenshot(getCurPage(), this.curBitmap, this.canvas);
        }
    }

    @Override // com.ykb.ebook.page.delegate.HorizontalPageDelegate, com.ykb.ebook.page.delegate.PageDelegate
    public void setDirection(@NotNull PageDirection direction) {
        Intrinsics.checkNotNullParameter(direction, "direction");
        super.setDirection(direction);
        int i2 = WhenMappings.$EnumSwitchMapping$0[direction.ordinal()];
        if (i2 != 1) {
            if (i2 == 2 && getViewWidth() / 2 > getStartX()) {
                calcCornerXY(getViewWidth() - getStartX(), getStartY());
                return;
            }
            return;
        }
        if (getStartX() > getViewWidth() / 2) {
            calcCornerXY(getStartX(), getViewHeight());
        } else {
            calcCornerXY(getViewWidth() - getStartX(), getViewHeight());
        }
    }

    public final void setFirstLastPageBitMap(@Nullable Bitmap first, @Nullable Bitmap last) {
        if (first != null) {
            this.firstPageBitMap = first;
        }
        if (last != null) {
            this.lastPageBitMap = last;
        }
    }

    public final void setLastPreBitMap(@NotNull Bitmap b3) {
        Intrinsics.checkNotNullParameter(b3, "b");
        this.lastPrePageBitMap = b3;
    }

    @Override // com.ykb.ebook.page.delegate.PageDelegate
    public void setViewSize(int width, int height) {
        super.setViewSize(width, height);
        this.mMaxLength = (float) Math.hypot(getViewWidth(), getViewHeight());
    }
}
