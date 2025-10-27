package com.lxj.xpopup.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import com.lxj.xpopup.util.XPopupUtils;

/* loaded from: classes4.dex */
public class BubbleLayout extends FrameLayout {
    boolean isLookPositionCenter;
    private int mArrowDownLeftRadius;
    private int mArrowDownRightRadius;
    private int mArrowTopLeftRadius;
    private int mArrowTopRightRadius;
    private int mBottom;
    private int mBubbleBgRes;
    private int mBubbleBorderColor;
    private Paint mBubbleBorderPaint;
    private int mBubbleBorderSize;
    private int mBubbleColor;
    private Bitmap mBubbleImageBg;
    private Paint mBubbleImageBgBeforePaint;
    private RectF mBubbleImageBgDstRectF;
    private Paint mBubbleImageBgPaint;
    private Rect mBubbleImageBgSrcRect;
    private int mBubblePadding;
    private int mBubbleRadius;
    private int mHeight;
    private int mLDR;
    private int mLTR;
    private int mLeft;
    private Look mLook;
    private int mLookLength;
    private int mLookPosition;
    private int mLookWidth;
    private Paint mPaint;
    private Path mPath;
    private int mRDR;
    private int mRTR;
    private int mRight;
    private int mShadowColor;
    private int mShadowRadius;
    private int mShadowX;
    private int mShadowY;
    private int mTop;
    private int mWidth;

    /* renamed from: com.lxj.xpopup.widget.BubbleLayout$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$lxj$xpopup$widget$BubbleLayout$Look;

        static {
            int[] iArr = new int[Look.values().length];
            $SwitchMap$com$lxj$xpopup$widget$BubbleLayout$Look = iArr;
            try {
                iArr[Look.BOTTOM.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$lxj$xpopup$widget$BubbleLayout$Look[Look.TOP.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$lxj$xpopup$widget$BubbleLayout$Look[Look.LEFT.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$lxj$xpopup$widget$BubbleLayout$Look[Look.RIGHT.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public enum Look {
        LEFT(1),
        TOP(2),
        RIGHT(3),
        BOTTOM(4);

        int value;

        Look(int i2) {
            this.value = i2;
        }

        public static Look getType(int i2) {
            return i2 != 1 ? i2 != 2 ? i2 != 3 ? BOTTOM : RIGHT : TOP : LEFT;
        }
    }

    public BubbleLayout(Context context) {
        this(context, null);
    }

    private void initAttr() {
        this.mLook = Look.BOTTOM;
        this.mLookPosition = 0;
        this.mLookWidth = XPopupUtils.dp2px(getContext(), 10.0f);
        this.mLookLength = XPopupUtils.dp2px(getContext(), 9.0f);
        this.mShadowRadius = 0;
        this.mShadowX = 0;
        this.mShadowY = 0;
        this.mBubbleRadius = XPopupUtils.dp2px(getContext(), 8.0f);
        this.mLTR = -1;
        this.mRTR = -1;
        this.mRDR = -1;
        this.mLDR = -1;
        this.mArrowTopLeftRadius = XPopupUtils.dp2px(getContext(), 3.0f);
        this.mArrowTopRightRadius = XPopupUtils.dp2px(getContext(), 3.0f);
        this.mArrowDownLeftRadius = XPopupUtils.dp2px(getContext(), 6.0f);
        this.mArrowDownRightRadius = XPopupUtils.dp2px(getContext(), 6.0f);
        this.mBubblePadding = XPopupUtils.dp2px(getContext(), 4.0f);
        this.mShadowColor = -12303292;
        this.mBubbleColor = Color.parseColor("#3b3c3d");
        this.mBubbleBorderColor = 0;
        this.mBubbleBorderSize = 0;
    }

    private void initData() {
        int i2;
        int i3;
        initPadding();
        if (this.isLookPositionCenter) {
            Look look = this.mLook;
            if (look == Look.LEFT || look == Look.RIGHT) {
                i2 = this.mHeight / 2;
                i3 = this.mLookLength;
            } else {
                i2 = this.mWidth / 2;
                i3 = this.mLookWidth;
            }
            this.mLookPosition = i2 - (i3 / 2);
        }
        this.mPaint.setShadowLayer(this.mShadowRadius, this.mShadowX, this.mShadowY, this.mShadowColor);
        this.mBubbleBorderPaint.setColor(this.mBubbleBorderColor);
        this.mBubbleBorderPaint.setStrokeWidth(this.mBubbleBorderSize);
        this.mBubbleBorderPaint.setStyle(Paint.Style.STROKE);
        int i4 = this.mShadowRadius;
        int i5 = this.mShadowX;
        int i6 = (i5 < 0 ? -i5 : 0) + i4;
        Look look2 = this.mLook;
        this.mLeft = i6 + (look2 == Look.LEFT ? this.mLookLength : 0);
        int i7 = this.mShadowY;
        this.mTop = (i7 < 0 ? -i7 : 0) + i4 + (look2 == Look.TOP ? this.mLookLength : 0);
        this.mRight = ((this.mWidth - i4) + (i5 > 0 ? -i5 : 0)) - (look2 == Look.RIGHT ? this.mLookLength : 0);
        this.mBottom = ((this.mHeight - i4) + (i7 > 0 ? -i7 : 0)) - (look2 == Look.BOTTOM ? this.mLookLength : 0);
        this.mPaint.setColor(this.mBubbleColor);
        this.mPath.reset();
        int i8 = this.mLookPosition;
        int i9 = this.mLookLength + i8;
        int i10 = this.mBottom;
        if (i9 > i10) {
            i8 = i10 - this.mLookWidth;
        }
        int iMax = Math.max(i8, this.mShadowRadius);
        int i11 = this.mLookPosition;
        int i12 = this.mLookLength + i11;
        int i13 = this.mRight;
        if (i12 > i13) {
            i11 = i13 - this.mLookWidth;
        }
        int iMax2 = Math.max(i11, this.mShadowRadius);
        int i14 = AnonymousClass1.$SwitchMap$com$lxj$xpopup$widget$BubbleLayout$Look[this.mLook.ordinal()];
        if (i14 == 1) {
            if (iMax2 >= getLDR() + this.mArrowDownRightRadius) {
                this.mPath.moveTo(iMax2 - r1, this.mBottom);
                Path path = this.mPath;
                int i15 = this.mArrowDownRightRadius;
                int i16 = this.mLookWidth;
                int i17 = this.mLookLength;
                path.rCubicTo(i15, 0.0f, ((i16 / 2.0f) - this.mArrowTopRightRadius) + i15, i17, (i16 / 2.0f) + i15, i17);
            } else {
                this.mPath.moveTo(iMax2 + (this.mLookWidth / 2.0f), this.mBottom + this.mLookLength);
            }
            int i18 = this.mLookWidth + iMax2;
            int rdr = this.mRight - getRDR();
            int i19 = this.mArrowDownLeftRadius;
            if (i18 < rdr - i19) {
                Path path2 = this.mPath;
                float f2 = this.mArrowTopLeftRadius;
                int i20 = this.mLookWidth;
                int i21 = this.mLookLength;
                path2.rCubicTo(f2, 0.0f, i20 / 2.0f, -i21, (i20 / 2.0f) + i19, -i21);
                this.mPath.lineTo(this.mRight - getRDR(), this.mBottom);
            }
            Path path3 = this.mPath;
            int i22 = this.mRight;
            path3.quadTo(i22, this.mBottom, i22, r4 - getRDR());
            this.mPath.lineTo(this.mRight, this.mTop + getRTR());
            this.mPath.quadTo(this.mRight, this.mTop, r1 - getRTR(), this.mTop);
            this.mPath.lineTo(this.mLeft + getLTR(), this.mTop);
            Path path4 = this.mPath;
            int i23 = this.mLeft;
            path4.quadTo(i23, this.mTop, i23, r4 + getLTR());
            this.mPath.lineTo(this.mLeft, this.mBottom - getLDR());
            if (iMax2 >= getLDR() + this.mArrowDownRightRadius) {
                this.mPath.quadTo(this.mLeft, this.mBottom, r1 + getLDR(), this.mBottom);
            } else {
                this.mPath.quadTo(this.mLeft, this.mBottom, iMax2 + (this.mLookWidth / 2.0f), r3 + this.mLookLength);
            }
        } else if (i14 == 2) {
            if (iMax2 >= getLTR() + this.mArrowDownLeftRadius) {
                this.mPath.moveTo(iMax2 - r1, this.mTop);
                Path path5 = this.mPath;
                int i24 = this.mArrowDownLeftRadius;
                int i25 = this.mLookWidth;
                int i26 = this.mLookLength;
                path5.rCubicTo(i24, 0.0f, ((i25 / 2.0f) - this.mArrowTopLeftRadius) + i24, -i26, (i25 / 2.0f) + i24, -i26);
            } else {
                this.mPath.moveTo(iMax2 + (this.mLookWidth / 2.0f), this.mTop - this.mLookLength);
            }
            int i27 = this.mLookWidth + iMax2;
            int rtr = this.mRight - getRTR();
            int i28 = this.mArrowDownRightRadius;
            if (i27 < rtr - i28) {
                Path path6 = this.mPath;
                float f3 = this.mArrowTopRightRadius;
                int i29 = this.mLookWidth;
                int i30 = this.mLookLength;
                path6.rCubicTo(f3, 0.0f, i29 / 2.0f, i30, (i29 / 2.0f) + i28, i30);
                this.mPath.lineTo(this.mRight - getRTR(), this.mTop);
            }
            Path path7 = this.mPath;
            int i31 = this.mRight;
            path7.quadTo(i31, this.mTop, i31, r4 + getRTR());
            this.mPath.lineTo(this.mRight, this.mBottom - getRDR());
            this.mPath.quadTo(this.mRight, this.mBottom, r1 - getRDR(), this.mBottom);
            this.mPath.lineTo(this.mLeft + getLDR(), this.mBottom);
            Path path8 = this.mPath;
            int i32 = this.mLeft;
            path8.quadTo(i32, this.mBottom, i32, r4 - getLDR());
            this.mPath.lineTo(this.mLeft, this.mTop + getLTR());
            if (iMax2 >= getLTR() + this.mArrowDownLeftRadius) {
                this.mPath.quadTo(this.mLeft, this.mTop, r1 + getLTR(), this.mTop);
            } else {
                this.mPath.quadTo(this.mLeft, this.mTop, iMax2 + (this.mLookWidth / 2.0f), r3 - this.mLookLength);
            }
        } else if (i14 == 3) {
            if (iMax >= getLTR() + this.mArrowDownRightRadius) {
                this.mPath.moveTo(this.mLeft, iMax - r2);
                Path path9 = this.mPath;
                int i33 = this.mArrowDownRightRadius;
                int i34 = this.mLookLength;
                int i35 = this.mLookWidth;
                path9.rCubicTo(0.0f, i33, -i34, i33 + ((i35 / 2.0f) - this.mArrowTopRightRadius), -i34, (i35 / 2.0f) + i33);
            } else {
                this.mPath.moveTo(this.mLeft - this.mLookLength, iMax + (this.mLookWidth / 2.0f));
            }
            int i36 = this.mLookWidth + iMax;
            int ldr = this.mBottom - getLDR();
            int i37 = this.mArrowDownLeftRadius;
            if (i36 < ldr - i37) {
                Path path10 = this.mPath;
                float f4 = this.mArrowTopLeftRadius;
                int i38 = this.mLookLength;
                int i39 = this.mLookWidth;
                path10.rCubicTo(0.0f, f4, i38, i39 / 2.0f, i38, (i39 / 2.0f) + i37);
                this.mPath.lineTo(this.mLeft, this.mBottom - getLDR());
            }
            this.mPath.quadTo(this.mLeft, this.mBottom, r2 + getLDR(), this.mBottom);
            this.mPath.lineTo(this.mRight - getRDR(), this.mBottom);
            Path path11 = this.mPath;
            int i40 = this.mRight;
            path11.quadTo(i40, this.mBottom, i40, r4 - getRDR());
            this.mPath.lineTo(this.mRight, this.mTop + getRTR());
            this.mPath.quadTo(this.mRight, this.mTop, r2 - getRTR(), this.mTop);
            this.mPath.lineTo(this.mLeft + getLTR(), this.mTop);
            if (iMax >= getLTR() + this.mArrowDownRightRadius) {
                Path path12 = this.mPath;
                int i41 = this.mLeft;
                path12.quadTo(i41, this.mTop, i41, r3 + getLTR());
            } else {
                this.mPath.quadTo(this.mLeft, this.mTop, r2 - this.mLookLength, iMax + (this.mLookWidth / 2.0f));
            }
        } else if (i14 == 4) {
            if (iMax >= getRTR() + this.mArrowDownLeftRadius) {
                this.mPath.moveTo(this.mRight, iMax - r2);
                Path path13 = this.mPath;
                int i42 = this.mArrowDownLeftRadius;
                int i43 = this.mLookLength;
                int i44 = this.mLookWidth;
                path13.rCubicTo(0.0f, i42, i43, i42 + ((i44 / 2.0f) - this.mArrowTopLeftRadius), i43, (i44 / 2.0f) + i42);
            } else {
                this.mPath.moveTo(this.mRight + this.mLookLength, iMax + (this.mLookWidth / 2.0f));
            }
            int i45 = this.mLookWidth + iMax;
            int rdr2 = this.mBottom - getRDR();
            int i46 = this.mArrowDownRightRadius;
            if (i45 < rdr2 - i46) {
                Path path14 = this.mPath;
                float f5 = this.mArrowTopRightRadius;
                int i47 = this.mLookLength;
                int i48 = this.mLookWidth;
                path14.rCubicTo(0.0f, f5, -i47, i48 / 2.0f, -i47, (i48 / 2.0f) + i46);
                this.mPath.lineTo(this.mRight, this.mBottom - getRDR());
            }
            this.mPath.quadTo(this.mRight, this.mBottom, r2 - getRDR(), this.mBottom);
            this.mPath.lineTo(this.mLeft + getLDR(), this.mBottom);
            Path path15 = this.mPath;
            int i49 = this.mLeft;
            path15.quadTo(i49, this.mBottom, i49, r4 - getLDR());
            this.mPath.lineTo(this.mLeft, this.mTop + getLTR());
            this.mPath.quadTo(this.mLeft, this.mTop, r2 + getLTR(), this.mTop);
            this.mPath.lineTo(this.mRight - getRTR(), this.mTop);
            if (iMax >= getRTR() + this.mArrowDownLeftRadius) {
                Path path16 = this.mPath;
                int i50 = this.mRight;
                path16.quadTo(i50, this.mTop, i50, r3 + getRTR());
            } else {
                this.mPath.quadTo(this.mRight, this.mTop, r2 + this.mLookLength, iMax + (this.mLookWidth / 2.0f));
            }
        }
        this.mPath.close();
    }

    public int getArrowDownLeftRadius() {
        return this.mArrowDownLeftRadius;
    }

    public int getArrowDownRightRadius() {
        return this.mArrowDownRightRadius;
    }

    public int getArrowTopLeftRadius() {
        return this.mArrowTopLeftRadius;
    }

    public int getArrowTopRightRadius() {
        return this.mArrowTopRightRadius;
    }

    public int getBubbleColor() {
        return this.mBubbleColor;
    }

    public int getBubbleRadius() {
        return this.mBubbleRadius;
    }

    public int getLDR() {
        int i2 = this.mLDR;
        return i2 == -1 ? this.mBubbleRadius : i2;
    }

    public int getLTR() {
        int i2 = this.mLTR;
        return i2 == -1 ? this.mBubbleRadius : i2;
    }

    public Look getLook() {
        return this.mLook;
    }

    public int getLookLength() {
        return this.mLookLength;
    }

    public int getLookPosition() {
        return this.mLookPosition;
    }

    public int getLookWidth() {
        return this.mLookWidth;
    }

    public Paint getPaint() {
        return this.mPaint;
    }

    public Path getPath() {
        return this.mPath;
    }

    public int getRDR() {
        int i2 = this.mRDR;
        return i2 == -1 ? this.mBubbleRadius : i2;
    }

    public int getRTR() {
        int i2 = this.mRTR;
        return i2 == -1 ? this.mBubbleRadius : i2;
    }

    public int getShadowColor() {
        return this.mShadowColor;
    }

    public int getShadowRadius() {
        return this.mShadowRadius;
    }

    public int getShadowX() {
        return this.mShadowX;
    }

    public int getShadowY() {
        return this.mShadowY;
    }

    public void initPadding() {
        int i2 = this.mBubblePadding + this.mShadowRadius;
        int i3 = AnonymousClass1.$SwitchMap$com$lxj$xpopup$widget$BubbleLayout$Look[this.mLook.ordinal()];
        if (i3 == 1) {
            setPadding(i2, i2, this.mShadowX + i2, this.mLookLength + i2 + this.mShadowY);
            return;
        }
        if (i3 == 2) {
            setPadding(i2, this.mLookLength + i2, this.mShadowX + i2, this.mShadowY + i2);
        } else if (i3 == 3) {
            setPadding(this.mLookLength + i2, i2, this.mShadowX + i2, this.mShadowY + i2);
        } else {
            if (i3 != 4) {
                return;
            }
            setPadding(i2, i2, this.mLookLength + i2 + this.mShadowX, this.mShadowY + i2);
        }
    }

    @Override // android.view.View
    public void invalidate() {
        initData();
        super.invalidate();
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(this.mPath, this.mPaint);
        if (this.mBubbleImageBg != null) {
            this.mPath.computeBounds(this.mBubbleImageBgDstRectF, true);
            int iSaveLayer = canvas.saveLayer(this.mBubbleImageBgDstRectF, null, 31);
            canvas.drawPath(this.mPath, this.mBubbleImageBgBeforePaint);
            float fWidth = this.mBubbleImageBgDstRectF.width() / this.mBubbleImageBgDstRectF.height();
            if (fWidth > (this.mBubbleImageBg.getWidth() * 1.0f) / this.mBubbleImageBg.getHeight()) {
                int height = (int) ((this.mBubbleImageBg.getHeight() - (this.mBubbleImageBg.getWidth() / fWidth)) / 2.0f);
                this.mBubbleImageBgSrcRect.set(0, height, this.mBubbleImageBg.getWidth(), ((int) (this.mBubbleImageBg.getWidth() / fWidth)) + height);
            } else {
                int width = (int) ((this.mBubbleImageBg.getWidth() - (this.mBubbleImageBg.getHeight() * fWidth)) / 2.0f);
                this.mBubbleImageBgSrcRect.set(width, 0, ((int) (this.mBubbleImageBg.getHeight() * fWidth)) + width, this.mBubbleImageBg.getHeight());
            }
            canvas.drawBitmap(this.mBubbleImageBg, this.mBubbleImageBgSrcRect, this.mBubbleImageBgDstRectF, this.mBubbleImageBgPaint);
            canvas.restoreToCount(iSaveLayer);
        }
        if (this.mBubbleBorderSize != 0) {
            canvas.drawPath(this.mPath, this.mBubbleBorderPaint);
        }
    }

    @Override // android.view.View
    public void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof Bundle)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        Bundle bundle = (Bundle) parcelable;
        this.mLookPosition = bundle.getInt("mLookPosition");
        this.mLookWidth = bundle.getInt("mLookWidth");
        this.mLookLength = bundle.getInt("mLookLength");
        this.mShadowColor = bundle.getInt("mShadowColor");
        this.mShadowRadius = bundle.getInt("mShadowRadius");
        this.mShadowX = bundle.getInt("mShadowX");
        this.mShadowY = bundle.getInt("mShadowY");
        this.mBubbleRadius = bundle.getInt("mBubbleRadius");
        this.mLTR = bundle.getInt("mLTR");
        this.mRTR = bundle.getInt("mRTR");
        this.mRDR = bundle.getInt("mRDR");
        this.mLDR = bundle.getInt("mLDR");
        this.mBubblePadding = bundle.getInt("mBubblePadding");
        this.mArrowTopLeftRadius = bundle.getInt("mArrowTopLeftRadius");
        this.mArrowTopRightRadius = bundle.getInt("mArrowTopRightRadius");
        this.mArrowDownLeftRadius = bundle.getInt("mArrowDownLeftRadius");
        this.mArrowDownRightRadius = bundle.getInt("mArrowDownRightRadius");
        this.mWidth = bundle.getInt("mWidth");
        this.mHeight = bundle.getInt("mHeight");
        this.mLeft = bundle.getInt("mLeft");
        this.mTop = bundle.getInt("mTop");
        this.mRight = bundle.getInt("mRight");
        this.mBottom = bundle.getInt("mBottom");
        int i2 = bundle.getInt("mBubbleBgRes");
        this.mBubbleBgRes = i2;
        if (i2 != -1) {
            this.mBubbleImageBg = BitmapFactory.decodeResource(getResources(), this.mBubbleBgRes);
        }
        this.mBubbleBorderSize = bundle.getInt("mBubbleBorderSize");
        this.mBubbleBorderColor = bundle.getInt("mBubbleBorderColor");
        super.onRestoreInstanceState(bundle.getParcelable("instanceState"));
    }

    @Override // android.view.View
    public Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("instanceState", super.onSaveInstanceState());
        bundle.putInt("mLookPosition", this.mLookPosition);
        bundle.putInt("mLookWidth", this.mLookWidth);
        bundle.putInt("mLookLength", this.mLookLength);
        bundle.putInt("mShadowColor", this.mShadowColor);
        bundle.putInt("mShadowRadius", this.mShadowRadius);
        bundle.putInt("mShadowX", this.mShadowX);
        bundle.putInt("mShadowY", this.mShadowY);
        bundle.putInt("mBubbleRadius", this.mBubbleRadius);
        bundle.putInt("mLTR", this.mLTR);
        bundle.putInt("mRTR", this.mRTR);
        bundle.putInt("mRDR", this.mRDR);
        bundle.putInt("mLDR", this.mLDR);
        bundle.putInt("mBubblePadding", this.mBubblePadding);
        bundle.putInt("mArrowTopLeftRadius", this.mArrowTopLeftRadius);
        bundle.putInt("mArrowTopRightRadius", this.mArrowTopRightRadius);
        bundle.putInt("mArrowDownLeftRadius", this.mArrowDownLeftRadius);
        bundle.putInt("mArrowDownRightRadius", this.mArrowDownRightRadius);
        bundle.putInt("mWidth", this.mWidth);
        bundle.putInt("mHeight", this.mHeight);
        bundle.putInt("mLeft", this.mLeft);
        bundle.putInt("mTop", this.mTop);
        bundle.putInt("mRight", this.mRight);
        bundle.putInt("mBottom", this.mBottom);
        bundle.putInt("mBubbleBgRes", this.mBubbleBgRes);
        bundle.putInt("mBubbleBorderColor", this.mBubbleBorderColor);
        bundle.putInt("mBubbleBorderSize", this.mBubbleBorderSize);
        return bundle;
    }

    @Override // android.view.View
    public void onSizeChanged(int i2, int i3, int i4, int i5) {
        super.onSizeChanged(i2, i3, i4, i5);
        this.mWidth = i2;
        this.mHeight = i3;
        initData();
    }

    @Override // android.view.View
    public void postInvalidate() {
        initData();
        super.postInvalidate();
    }

    public void setArrowDownLeftRadius(int i2) {
        this.mArrowDownLeftRadius = i2;
    }

    public void setArrowDownRightRadius(int i2) {
        this.mArrowDownRightRadius = i2;
    }

    public void setArrowTopLeftRadius(int i2) {
        this.mArrowTopLeftRadius = i2;
    }

    public void setArrowTopRightRadius(int i2) {
        this.mArrowTopRightRadius = i2;
    }

    public void setBubbleBorderColor(int i2) {
        this.mBubbleBorderColor = i2;
    }

    public void setBubbleBorderSize(int i2) {
        this.mBubbleBorderSize = i2;
    }

    public void setBubbleColor(int i2) {
        this.mBubbleColor = i2;
    }

    public void setBubbleImageBg(Bitmap bitmap) {
        this.mBubbleImageBg = bitmap;
    }

    public void setBubbleImageBgRes(int i2) {
        this.mBubbleImageBg = BitmapFactory.decodeResource(getResources(), i2);
    }

    public void setBubblePadding(int i2) {
        this.mBubblePadding = i2;
    }

    public void setBubbleRadius(int i2) {
        this.mBubbleRadius = i2;
    }

    public void setLDR(int i2) {
        this.mLDR = i2;
    }

    public void setLTR(int i2) {
        this.mLTR = i2;
    }

    public void setLook(Look look) {
        this.mLook = look;
        initPadding();
    }

    public void setLookLength(int i2) {
        this.mLookLength = i2;
        initPadding();
    }

    public void setLookPosition(int i2) {
        this.mLookPosition = i2;
    }

    public void setLookPositionCenter(boolean z2) {
        this.isLookPositionCenter = z2;
    }

    public void setLookWidth(int i2) {
        this.mLookWidth = i2;
    }

    public void setRDR(int i2) {
        this.mRDR = i2;
    }

    public void setRTR(int i2) {
        this.mRTR = i2;
    }

    public void setShadowColor(int i2) {
        this.mShadowColor = i2;
    }

    public void setShadowRadius(int i2) {
        this.mShadowRadius = i2;
    }

    public void setShadowX(int i2) {
        this.mShadowX = i2;
    }

    public void setShadowY(int i2) {
        this.mShadowY = i2;
    }

    public BubbleLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public BubbleLayout(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.mLDR = -1;
        this.mBubbleBgRes = -1;
        this.mBubbleImageBg = null;
        this.mBubbleImageBgDstRectF = new RectF();
        this.mBubbleImageBgSrcRect = new Rect();
        this.mBubbleImageBgPaint = new Paint(5);
        this.mBubbleImageBgBeforePaint = new Paint(5);
        this.mBubbleBorderColor = -16777216;
        this.mBubbleBorderSize = 0;
        this.mBubbleBorderPaint = new Paint(5);
        setLayerType(1, null);
        setWillNotDraw(false);
        initAttr();
        Paint paint = new Paint(5);
        this.mPaint = paint;
        paint.setStyle(Paint.Style.FILL);
        this.mPath = new Path();
        this.mBubbleImageBgPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
    }
}
