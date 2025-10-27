package com.aliyun.svideo.common.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import com.aliyun.svideo.common.R;

/* loaded from: classes2.dex */
public class SwitchButton extends View {
    private static final int STATE_SWITCH_OFF = 1;
    private static final int STATE_SWITCH_OFF2 = 2;
    private static final int STATE_SWITCH_ON = 4;
    private static final int STATE_SWITCH_ON2 = 3;
    protected float animationSpeed;
    private float bAnim;
    private float bLeft;
    private float bOff2LeftX;
    private float bOffLeftX;
    private float bOffset;
    private float bOn2LeftX;
    private float bOnLeftX;
    private final Path bPath;
    private float bRadius;
    private final RectF bRectF;
    private float bRight;
    private float bStrokeWidth;
    private float bWidth;
    protected int colorOff;
    protected int colorOffDark;
    protected int colorPrimary;
    protected int colorPrimaryDark;
    protected int colorShadow;
    protected boolean hasShadow;
    private final AccelerateInterpolator interpolator;
    private boolean isCanVisibleDrawing;
    protected boolean isOpened;
    private int lastState;
    private OnStateChangedListener listener;
    private View.OnClickListener mOnClickListener;
    private final Paint paint;
    protected float ratioAspect;
    private float sAnim;
    private float sCenterX;
    private float sCenterY;
    private final Path sPath;
    private float sRight;
    private float sScale;
    private RadialGradient shadowGradient;
    private float shadowReservedHeight;
    private int state;

    public interface OnStateChangedListener {
        void toggleToOff(SwitchButton switchButton);

        void toggleToOn(SwitchButton switchButton);
    }

    public static final class SavedState extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() { // from class: com.aliyun.svideo.common.widget.SwitchButton.SavedState.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SavedState[] newArray(int i2) {
                return new SavedState[i2];
            }
        };
        private boolean isOpened;

        @Override // android.view.AbsSavedState, android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i2) {
            super.writeToParcel(parcel, i2);
            parcel.writeInt(this.isOpened ? 1 : 0);
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        private SavedState(Parcel parcel) {
            super(parcel);
            this.isOpened = 1 == parcel.readInt();
        }
    }

    public SwitchButton(Context context) {
        this(context, null);
    }

    private void calcBPath(float f2) {
        this.bPath.reset();
        RectF rectF = this.bRectF;
        float f3 = this.bLeft;
        float f4 = this.bStrokeWidth;
        rectF.left = f3 + (f4 / 2.0f);
        rectF.right = this.bRight - (f4 / 2.0f);
        this.bPath.arcTo(rectF, 90.0f, 180.0f);
        RectF rectF2 = this.bRectF;
        float f5 = this.bLeft;
        float f6 = this.bOffset;
        float f7 = this.bStrokeWidth;
        rectF2.left = f5 + (f2 * f6) + (f7 / 2.0f);
        rectF2.right = (this.bRight + (f2 * f6)) - (f7 / 2.0f);
        this.bPath.arcTo(rectF2, 270.0f, 180.0f);
        this.bPath.close();
    }

    /* JADX WARN: Removed duplicated region for block: B:34:0x0061  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private float calcBTranslate(float r7) {
        /*
            r6 = this;
            int r0 = r6.state
            int r1 = r6.lastState
            int r1 = r0 - r1
            r2 = -3
            if (r1 == r2) goto L63
            r2 = -2
            r3 = 2
            r4 = 1
            if (r1 == r2) goto L53
            r2 = -1
            r5 = 3
            if (r1 == r2) goto L47
            r2 = 4
            if (r1 == r4) goto L3b
            if (r1 == r3) goto L2d
            if (r1 == r5) goto L23
            if (r0 != r4) goto L1e
            float r7 = r6.bOffLeftX
            goto L6b
        L1e:
            if (r0 != r2) goto L61
            float r7 = r6.bOnLeftX
            goto L6b
        L23:
            float r0 = r6.bOnLeftX
            float r1 = r6.bOffLeftX
        L27:
            float r1 = r0 - r1
            float r1 = r1 * r7
            float r7 = r0 - r1
            goto L6b
        L2d:
            if (r0 != r2) goto L34
            float r0 = r6.bOnLeftX
            float r1 = r6.bOffLeftX
            goto L27
        L34:
            if (r0 != r5) goto L61
            float r0 = r6.bOn2LeftX
            float r1 = r6.bOffLeftX
            goto L27
        L3b:
            if (r0 != r3) goto L40
            float r7 = r6.bOffLeftX
            goto L6b
        L40:
            if (r0 != r2) goto L61
            float r0 = r6.bOnLeftX
            float r1 = r6.bOn2LeftX
            goto L27
        L47:
            if (r0 != r5) goto L4e
            float r0 = r6.bOn2LeftX
            float r1 = r6.bOnLeftX
            goto L67
        L4e:
            if (r0 != r4) goto L61
            float r7 = r6.bOffLeftX
            goto L6b
        L53:
            if (r0 != r4) goto L5a
            float r0 = r6.bOffLeftX
            float r1 = r6.bOn2LeftX
            goto L67
        L5a:
            if (r0 != r3) goto L61
            float r0 = r6.bOff2LeftX
            float r1 = r6.bOnLeftX
            goto L67
        L61:
            r7 = 0
            goto L6b
        L63:
            float r0 = r6.bOffLeftX
            float r1 = r6.bOnLeftX
        L67:
            float r1 = r1 - r0
            float r1 = r1 * r7
            float r7 = r0 + r1
        L6b:
            float r0 = r6.bOffLeftX
            float r7 = r7 - r0
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.aliyun.svideo.common.widget.SwitchButton.calcBTranslate(float):float");
    }

    private void refreshState(int i2) {
        boolean z2 = this.isOpened;
        if (!z2 && i2 == 4) {
            this.isOpened = true;
        } else if (z2 && i2 == 1) {
            this.isOpened = false;
        }
        this.lastState = this.state;
        this.state = i2;
        postInvalidate();
    }

    public boolean isOpened() {
        return this.isOpened;
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.isCanVisibleDrawing) {
            boolean z2 = true;
            this.paint.setAntiAlias(true);
            int i2 = this.state;
            boolean z3 = i2 == 4 || i2 == 3;
            this.paint.setStyle(Paint.Style.FILL);
            this.paint.setColor(z3 ? this.colorPrimary : this.colorOff);
            canvas.drawPath(this.sPath, this.paint);
            float f2 = this.sAnim;
            float f3 = this.animationSpeed;
            float f4 = f2 - f3 > 0.0f ? f2 - f3 : 0.0f;
            this.sAnim = f4;
            float f5 = this.bAnim;
            this.bAnim = f5 - f3 > 0.0f ? f5 - f3 : 0.0f;
            float interpolation = this.interpolator.getInterpolation(f4);
            float interpolation2 = this.interpolator.getInterpolation(this.bAnim);
            float f6 = this.sScale * (z3 ? interpolation : 1.0f - interpolation);
            float f7 = (this.sRight - this.sCenterX) - this.bRadius;
            if (z3) {
                interpolation = 1.0f - interpolation;
            }
            canvas.save();
            canvas.scale(f6, f6, this.sCenterX + (f7 * interpolation), this.sCenterY);
            this.paint.setColor(-1);
            canvas.drawPath(this.sPath, this.paint);
            canvas.restore();
            canvas.save();
            canvas.translate(calcBTranslate(interpolation2), this.shadowReservedHeight);
            int i3 = this.state;
            if (i3 != 3 && i3 != 2) {
                z2 = false;
            }
            if (z2) {
                interpolation2 = 1.0f - interpolation2;
            }
            calcBPath(interpolation2);
            if (this.hasShadow) {
                this.paint.setStyle(Paint.Style.FILL);
                this.paint.setShader(this.shadowGradient);
                canvas.drawPath(this.bPath, this.paint);
                this.paint.setShader(null);
            }
            canvas.translate(0.0f, -this.shadowReservedHeight);
            float f8 = this.bWidth;
            canvas.scale(0.98f, 0.98f, f8 / 2.0f, f8 / 2.0f);
            this.paint.setStyle(Paint.Style.FILL);
            this.paint.setColor(-1);
            canvas.drawPath(this.bPath, this.paint);
            this.paint.setStyle(Paint.Style.STROKE);
            this.paint.setStrokeWidth(this.bStrokeWidth * 0.5f);
            this.paint.setColor(z3 ? this.colorPrimaryDark : this.colorOffDark);
            canvas.drawPath(this.bPath, this.paint);
            canvas.restore();
            this.paint.reset();
            if (this.sAnim > 0.0f || this.bAnim > 0.0f) {
                invalidate();
            }
        }
    }

    @Override // android.view.View
    public void onMeasure(int i2, int i3) {
        int size = View.MeasureSpec.getSize(i2);
        int mode = View.MeasureSpec.getMode(i2);
        if (mode != 1073741824) {
            int paddingLeft = ((int) ((getResources().getDisplayMetrics().density * 56.0f) + 0.5f)) + getPaddingLeft() + getPaddingRight();
            size = mode == Integer.MIN_VALUE ? Math.min(paddingLeft, size) : paddingLeft;
        }
        int size2 = View.MeasureSpec.getSize(i3);
        int mode2 = View.MeasureSpec.getMode(i3);
        if (mode2 != 1073741824) {
            int paddingTop = ((int) (size * this.ratioAspect)) + getPaddingTop() + getPaddingBottom();
            size2 = mode2 == Integer.MIN_VALUE ? Math.min(paddingTop, size2) : paddingTop;
        }
        setMeasuredDimension(size, size2);
    }

    @Override // android.view.View
    public void onRestoreInstanceState(Parcelable parcelable) {
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        boolean z2 = savedState.isOpened;
        this.isOpened = z2;
        this.state = z2 ? 4 : 1;
        invalidate();
    }

    @Override // android.view.View
    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.isOpened = this.isOpened;
        return savedState;
    }

    @Override // android.view.View
    public void onSizeChanged(int i2, int i3, int i4, int i5) {
        int paddingLeft;
        int width;
        int paddingTop;
        int height;
        super.onSizeChanged(i2, i3, i4, i5);
        boolean z2 = i2 > getPaddingLeft() + getPaddingRight() && i3 > getPaddingTop() + getPaddingBottom();
        this.isCanVisibleDrawing = z2;
        if (z2) {
            int paddingLeft2 = (i2 - getPaddingLeft()) - getPaddingRight();
            int paddingTop2 = (i3 - getPaddingTop()) - getPaddingBottom();
            float f2 = paddingLeft2;
            float f3 = this.ratioAspect;
            float f4 = paddingTop2;
            if (f2 * f3 < f4) {
                paddingLeft = getPaddingLeft();
                width = i2 - getPaddingRight();
                int i6 = ((int) (f4 - (f2 * this.ratioAspect))) / 2;
                paddingTop = getPaddingTop() + i6;
                height = (getHeight() - getPaddingBottom()) - i6;
            } else {
                int i7 = ((int) (f2 - (f4 / f3))) / 2;
                paddingLeft = getPaddingLeft() + i7;
                width = (getWidth() - getPaddingRight()) - i7;
                paddingTop = getPaddingTop();
                height = getHeight() - getPaddingBottom();
            }
            float f5 = (int) ((height - paddingTop) * 0.07f);
            this.shadowReservedHeight = f5;
            float f6 = paddingLeft;
            float f7 = paddingTop + f5;
            float f8 = width;
            this.sRight = f8;
            float f9 = height - f5;
            float f10 = f9 - f7;
            this.sCenterX = (f8 + f6) / 2.0f;
            float f11 = (f9 + f7) / 2.0f;
            this.sCenterY = f11;
            this.bLeft = f6;
            this.bWidth = f10;
            this.bRight = f6 + f10;
            float f12 = f10 / 2.0f;
            float f13 = 0.95f * f12;
            this.bRadius = f13;
            float f14 = 0.2f * f13;
            this.bOffset = f14;
            float f15 = (f12 - f13) * 2.0f;
            this.bStrokeWidth = f15;
            float f16 = f8 - f10;
            this.bOnLeftX = f16;
            this.bOn2LeftX = f16 - f14;
            this.bOffLeftX = f6;
            this.bOff2LeftX = f14 + f6;
            this.sScale = 1.0f - (f15 / f10);
            this.sPath.reset();
            RectF rectF = new RectF();
            rectF.top = f7;
            rectF.bottom = f9;
            rectF.left = f6;
            rectF.right = f6 + f10;
            this.sPath.arcTo(rectF, 90.0f, 180.0f);
            float f17 = this.sRight;
            rectF.left = f17 - f10;
            rectF.right = f17;
            this.sPath.arcTo(rectF, 270.0f, 180.0f);
            this.sPath.close();
            RectF rectF2 = this.bRectF;
            float f18 = this.bLeft;
            rectF2.left = f18;
            float f19 = this.bRight;
            rectF2.right = f19;
            float f20 = this.bStrokeWidth;
            rectF2.top = f7 + (f20 / 2.0f);
            rectF2.bottom = f9 - (f20 / 2.0f);
            float f21 = (f19 + f18) / 2.0f;
            int i8 = this.colorShadow;
            int i9 = (i8 >> 16) & 255;
            int i10 = (i8 >> 8) & 255;
            int i11 = i8 & 255;
            this.shadowGradient = new RadialGradient(f21, f11, this.bRadius, Color.argb(200, i9, i10, i11), Color.argb(25, i9, i10, i11), Shader.TileMode.CLAMP);
        }
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        int i2 = this.state;
        if ((i2 == 4 || i2 == 1) && this.sAnim * this.bAnim == 0.0f) {
            int action = motionEvent.getAction();
            if (action == 0) {
                return true;
            }
            if (action == 1) {
                int i3 = this.state;
                this.lastState = i3;
                this.bAnim = 1.0f;
                if (i3 == 1) {
                    refreshState(2);
                    this.listener.toggleToOn(this);
                } else if (i3 == 4) {
                    refreshState(3);
                    this.listener.toggleToOff(this);
                }
                View.OnClickListener onClickListener = this.mOnClickListener;
                if (onClickListener != null) {
                    onClickListener.onClick(this);
                }
            }
        }
        return super.onTouchEvent(motionEvent);
    }

    public void setColor(int i2, int i3) {
        setColor(i2, i3, this.colorOff, this.colorOffDark);
    }

    @Override // android.view.View
    public void setOnClickListener(View.OnClickListener onClickListener) {
        super.setOnClickListener(onClickListener);
        this.mOnClickListener = onClickListener;
    }

    public void setOnStateChangedListener(OnStateChangedListener onStateChangedListener) {
        if (onStateChangedListener == null) {
            throw new IllegalArgumentException("empty listener");
        }
        this.listener = onStateChangedListener;
    }

    public void setOpened(boolean z2) {
        int i2 = z2 ? 4 : 1;
        if (i2 == this.state) {
            return;
        }
        refreshState(i2);
    }

    public void setShadow(boolean z2) {
        this.hasShadow = z2;
        invalidate();
    }

    public void toggleSwitch(boolean z2) {
        int i2 = z2 ? 4 : 1;
        int i3 = this.state;
        if (i2 == i3) {
            return;
        }
        if ((i2 == 4 && (i3 == 1 || i3 == 2)) || (i2 == 1 && (i3 == 4 || i3 == 3))) {
            this.sAnim = 1.0f;
        }
        this.bAnim = 1.0f;
        refreshState(i2);
    }

    @TargetApi(11)
    public SwitchButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.interpolator = new AccelerateInterpolator(2.0f);
        this.paint = new Paint();
        this.sPath = new Path();
        this.bPath = new Path();
        this.bRectF = new RectF();
        this.ratioAspect = 0.68f;
        this.animationSpeed = 0.1f;
        this.isCanVisibleDrawing = false;
        this.listener = new OnStateChangedListener() { // from class: com.aliyun.svideo.common.widget.SwitchButton.1
            @Override // com.aliyun.svideo.common.widget.SwitchButton.OnStateChangedListener
            public void toggleToOff(SwitchButton switchButton) {
                SwitchButton.this.toggleSwitch(false);
            }

            @Override // com.aliyun.svideo.common.widget.SwitchButton.OnStateChangedListener
            public void toggleToOn(SwitchButton switchButton) {
                SwitchButton.this.toggleSwitch(true);
            }
        };
        setLayerType(1, null);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.SwitchButton);
        this.colorPrimary = typedArrayObtainStyledAttributes.getColor(R.styleable.SwitchButton_primaryColor, -11806877);
        this.colorPrimaryDark = typedArrayObtainStyledAttributes.getColor(R.styleable.SwitchButton_primaryColorDark, -12925358);
        this.colorOff = typedArrayObtainStyledAttributes.getColor(R.styleable.SwitchButton_offColor, -1842205);
        this.colorOffDark = typedArrayObtainStyledAttributes.getColor(R.styleable.SwitchButton_offColorDark, -4210753);
        this.colorShadow = typedArrayObtainStyledAttributes.getColor(R.styleable.SwitchButton_shadowColor, -13421773);
        this.ratioAspect = typedArrayObtainStyledAttributes.getFloat(R.styleable.SwitchButton_ratioAspect, 0.68f);
        this.hasShadow = typedArrayObtainStyledAttributes.getBoolean(R.styleable.SwitchButton_hasShadow, true);
        boolean z2 = typedArrayObtainStyledAttributes.getBoolean(R.styleable.SwitchButton_isOpened, false);
        this.isOpened = z2;
        int i2 = z2 ? 4 : 1;
        this.state = i2;
        this.lastState = i2;
        typedArrayObtainStyledAttributes.recycle();
        if (this.colorPrimary == -11806877 && this.colorPrimaryDark == -12925358) {
            try {
                TypedValue typedValue = new TypedValue();
                context.getTheme().resolveAttribute(android.R.attr.colorPrimary, typedValue, true);
                int i3 = typedValue.data;
                if (i3 > 0) {
                    this.colorPrimary = i3;
                }
                TypedValue typedValue2 = new TypedValue();
                context.getTheme().resolveAttribute(android.R.attr.colorPrimaryDark, typedValue2, true);
                int i4 = typedValue2.data;
                if (i4 > 0) {
                    this.colorPrimaryDark = i4;
                }
            } catch (Exception unused) {
            }
        }
    }

    public void setColor(int i2, int i3, int i4, int i5) {
        setColor(i2, i3, i4, i5, this.colorShadow);
    }

    public void setColor(int i2, int i3, int i4, int i5, int i6) {
        this.colorPrimary = i2;
        this.colorPrimaryDark = i3;
        this.colorOff = i4;
        this.colorOffDark = i5;
        this.colorShadow = i6;
        invalidate();
    }
}
