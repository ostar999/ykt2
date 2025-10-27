package com.ruffian.library.widget.helper;

import android.R;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.graphics.drawable.shapes.RoundRectShape;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewTreeObserver;
import androidx.annotation.ColorInt;
import androidx.annotation.RequiresApi;
import androidx.annotation.StyleableRes;
import androidx.core.internal.view.SupportMenu;
import androidx.core.text.TextUtilsCompat;
import com.ruffian.library.widget.clip.ClipHelper;
import com.ruffian.library.widget.clip.ClipPathManager;
import com.ruffian.library.widget.clip.IClip;
import com.ruffian.library.widget.shadow.ShadowBitmapDrawable;
import com.ruffian.library.widget.utils.RippleDrawableUtils;
import java.util.Locale;

/* loaded from: classes6.dex */
public class RBaseHelper<T extends View> implements IClip, ViewTreeObserver.OnGlobalLayoutListener {
    private GradientDrawable mBackgroundChecked;
    private Drawable mBackgroundCheckedBmp;
    private int[] mBackgroundColorCheckedArray;
    private int[] mBackgroundColorNormalArray;
    private int[] mBackgroundColorPressedArray;
    private int[] mBackgroundColorSelectedArray;
    private int[] mBackgroundColorUnableArray;
    private Drawable mBackgroundDrawable;
    private GradientDrawable mBackgroundNormal;
    private Drawable mBackgroundNormalBmp;
    private GradientDrawable mBackgroundPressed;
    private Drawable mBackgroundPressedBmp;
    private GradientDrawable mBackgroundSelected;
    private Drawable mBackgroundSelectedBmp;
    private GradientDrawable mBackgroundUnable;
    private Drawable mBackgroundUnableBmp;
    protected Context mContext;
    private float mCornerRadius;
    private float mCornerRadiusBottomLeft;
    private float mCornerRadiusBottomRight;
    private float mCornerRadiusTopLeft;
    private float mCornerRadiusTopRight;
    private float mGradientCenterX;
    private float mGradientCenterY;
    private float mGradientRadius;
    private int mRippleColor;
    private Drawable mRippleMaskDrawable;
    private int mRippleMaskStyle;
    private int mShadowColor;
    private ShadowBitmapDrawable mShadowDrawable;
    private int mShadowDx;
    private int mShadowDy;
    private int mShadowRadius;
    private StateListDrawable mStateBackground;
    private int mTouchSlop;
    private boolean mUseRipple;
    protected T mView;
    private Drawable mViewBackground;
    protected final int _C = 0;
    protected final int _S = -1;
    protected int BG_TYPE_COLOR = 1;
    protected int BG_TYPE_COLOR_ARRAY = 2;
    protected int BG_TYPE_IMG = 3;
    private float mBorderDashWidth = -1.0f;
    private float mBorderDashGap = -1.0f;
    private int mBorderWidthNormal = -1;
    private int mBorderWidthPressed = -1;
    private int mBorderWidthUnable = -1;
    private int mBorderWidthChecked = -1;
    private int mBorderWidthSelected = -1;
    private int mBorderColorNormal = 0;
    private int mBorderColorPressed = 0;
    private int mBorderColorUnable = 0;
    private int mBorderColorChecked = 0;
    private int mBorderColorSelected = 0;
    private int mBackgroundColorNormal = 0;
    private int mBackgroundColorPressed = 0;
    private int mBackgroundColorUnable = 0;
    private int mBackgroundColorChecked = 0;
    private int mBackgroundColorSelected = 0;
    private int mGradientType = 0;
    private GradientDrawable.Orientation mGradientOrientation = GradientDrawable.Orientation.TOP_BOTTOM;
    private boolean mIsEnabled = true;
    private final int MASK_STYLE_NULL = 1;
    private final int MASK_STYLE_NORMAL = 2;
    private final int MASK_STYLE_DRAWABLE = 3;
    private int[][] states = new int[6][];
    private float[] mBorderRadii = new float[8];
    private boolean mHasPressedBgColor = false;
    private boolean mHasPressedBgBmp = false;
    private boolean mHasUnableBgColor = false;
    private boolean mHasUnableBgBmp = false;
    private boolean mHasCheckedBgColor = false;
    private boolean mHasSelectedBgColor = false;
    private boolean mHasCheckedBgBmp = false;
    private boolean mHasSelectedBgBmp = false;
    private boolean mHasPressedBorderColor = false;
    private boolean mHasUnableBorderColor = false;
    private boolean mHasCheckedBorderColor = false;
    private boolean mHasSelectedBorderColor = false;
    private boolean mHasPressedBorderWidth = false;
    private boolean mHasUnableBorderWidth = false;
    private boolean mHasCheckedBorderWidth = false;
    private boolean mHasSelectedBorderWidth = false;
    protected ClipHelper mClipHelper = new ClipHelper();
    private boolean mClipLayout = false;

    public RBaseHelper(Context context, T t2, AttributeSet attributeSet) throws Resources.NotFoundException, SecurityException {
        this.mView = t2;
        this.mContext = context;
        this.mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        initAttributeSet(context, attributeSet);
        addOnGlobalLayoutListener();
    }

    private void addOnGlobalLayoutListener() {
        T t2 = this.mView;
        if (t2 == null) {
            return;
        }
        t2.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() { // from class: com.ruffian.library.widget.helper.RBaseHelper.1
            @Override // android.view.View.OnAttachStateChangeListener
            public void onViewAttachedToWindow(View view) {
                view.getViewTreeObserver().addOnGlobalLayoutListener(RBaseHelper.this);
            }

            @Override // android.view.View.OnAttachStateChangeListener
            public void onViewDetachedFromWindow(View view) {
                view.getViewTreeObserver().removeGlobalOnLayoutListener(RBaseHelper.this);
                view.removeOnAttachStateChangeListener(this);
            }
        });
    }

    private Drawable getBackgroundDrawable(boolean z2, int i2) {
        if (!isUseRipple()) {
            return this.mStateBackground;
        }
        Object[] rippleDrawableWithTag = getRippleDrawableWithTag(z2, i2);
        RippleDrawable rippleDrawable = (RippleDrawable) rippleDrawableWithTag[0];
        if (((Boolean) rippleDrawableWithTag[1]).booleanValue()) {
            return rippleDrawable;
        }
        StateListDrawable stateListDrawable = new StateListDrawable();
        int[] iArr = {-16842910};
        int[][] iArr2 = {iArr, new int[]{R.attr.state_checked}, new int[]{R.attr.state_selected}, new int[]{R.attr.state_enabled}};
        Drawable drawable = this.mBackgroundUnableBmp;
        if (drawable == null) {
            stateListDrawable.addState(iArr, this.mBackgroundUnable);
        } else {
            stateListDrawable.addState(iArr, drawable);
        }
        Drawable drawable2 = this.mBackgroundCheckedBmp;
        if (drawable2 == null) {
            stateListDrawable.addState(iArr2[1], this.mBackgroundChecked);
        } else {
            stateListDrawable.addState(iArr2[1], drawable2);
        }
        Drawable drawable3 = this.mBackgroundSelectedBmp;
        if (drawable3 == null) {
            stateListDrawable.addState(iArr2[2], this.mBackgroundSelected);
        } else {
            stateListDrawable.addState(iArr2[2], drawable3);
        }
        stateListDrawable.addState(iArr2[3], rippleDrawable);
        return stateListDrawable;
    }

    private Object[] getBackgroundInfo(TypedArray typedArray, @StyleableRes int i2) throws Resources.NotFoundException {
        Drawable drawable;
        int color;
        int i3 = this.BG_TYPE_COLOR;
        int resourceId = typedArray.getResourceId(i2, 0);
        int[] iArr = null;
        if (resourceId != 0) {
            String resourceTypeName = this.mContext.getResources().getResourceTypeName(resourceId);
            if ("array".equals(resourceTypeName)) {
                i3 = this.BG_TYPE_COLOR_ARRAY;
                String[] stringArray = this.mContext.getResources().getStringArray(resourceId);
                int[] intArray = this.mContext.getResources().getIntArray(resourceId);
                int iMin = Math.min(intArray.length, stringArray.length);
                int[] iArr2 = new int[iMin];
                for (int i4 = 0; i4 < iMin; i4++) {
                    String str = stringArray[i4];
                    int color2 = intArray[i4];
                    if (!TextUtils.isEmpty(str)) {
                        color2 = Color.parseColor(str);
                    }
                    iArr2[i4] = color2;
                }
                color = 0;
                drawable = null;
                iArr = iArr2;
            } else if ("color".equals(resourceTypeName)) {
                color = typedArray.getColor(i2, 0);
                i3 = this.BG_TYPE_COLOR;
            } else if ("mipmap".equals(resourceTypeName) || "drawable".equals(resourceTypeName)) {
                i3 = this.BG_TYPE_IMG;
                drawable = typedArray.getDrawable(i2);
                color = 0;
            } else {
                color = 0;
            }
            return new Object[]{Integer.valueOf(i3), Integer.valueOf(color), iArr, drawable};
        }
        color = typedArray.getColor(i2, 0);
        i3 = this.BG_TYPE_COLOR;
        drawable = null;
        return new Object[]{Integer.valueOf(i3), Integer.valueOf(color), iArr, drawable};
    }

    private GradientDrawable.Orientation getGradientOrientation(TypedArray typedArray) {
        GradientDrawable.Orientation orientation = GradientDrawable.Orientation.BL_TR;
        switch (typedArray.getInt(com.ruffian.library.widget.R.styleable.RBaseView_gradient_orientation, 0)) {
            case 0:
                return GradientDrawable.Orientation.TOP_BOTTOM;
            case 1:
                return GradientDrawable.Orientation.TR_BL;
            case 2:
                return GradientDrawable.Orientation.RIGHT_LEFT;
            case 3:
                return GradientDrawable.Orientation.BR_TL;
            case 4:
                return GradientDrawable.Orientation.BOTTOM_TOP;
            case 5:
                return GradientDrawable.Orientation.BL_TR;
            case 6:
                return GradientDrawable.Orientation.LEFT_RIGHT;
            case 7:
                return GradientDrawable.Orientation.TL_BR;
            default:
                return orientation;
        }
    }

    @RequiresApi(api = 21)
    private Object[] getRippleDrawableWithTag(boolean z2, int i2) {
        Object obj;
        Drawable shapeDrawable = null;
        if (z2) {
            obj = this.mBackgroundNormalBmp;
            if (obj == null) {
                obj = this.mBackgroundNormal;
            }
        } else {
            obj = null;
        }
        int i3 = this.mRippleMaskStyle;
        if (i3 != 1) {
            if (i3 != 2) {
                if (i3 == 3) {
                    shapeDrawable = this.mRippleMaskDrawable;
                }
            } else if (z2) {
                Drawable drawable = this.mBackgroundNormalBmp;
                shapeDrawable = drawable != null ? drawable : new ShapeDrawable(new RoundRectShape(this.mBorderRadii, null, null));
            } else {
                shapeDrawable = new ShapeDrawable(new RectShape());
            }
        }
        return new Object[]{new RippleDrawableUtils(obj, shapeDrawable).getRippleDrawable(new ColorStateList(new int[][]{new int[]{R.attr.state_pressed}, new int[]{R.attr.state_focused}, new int[]{R.attr.state_activated}, new int[0]}, new int[]{i2, i2, i2, i2})), Boolean.valueOf(obj == null && shapeDrawable == null)};
    }

    private void initAttributeSet(Context context, AttributeSet attributeSet) throws Resources.NotFoundException, SecurityException {
        if (context == null || attributeSet == null) {
            setup();
            return;
        }
        TypedArray typedArrayObtainStyledAttributes = this.mView.getContext().obtainStyledAttributes(attributeSet, com.ruffian.library.widget.R.styleable.RBaseView);
        this.mCornerRadius = typedArrayObtainStyledAttributes.getDimensionPixelSize(com.ruffian.library.widget.R.styleable.RBaseView_corner_radius, -1);
        this.mCornerRadiusTopLeft = typedArrayObtainStyledAttributes.getDimensionPixelSize(com.ruffian.library.widget.R.styleable.RBaseView_corner_radius_top_left, 0);
        this.mCornerRadiusTopRight = typedArrayObtainStyledAttributes.getDimensionPixelSize(com.ruffian.library.widget.R.styleable.RBaseView_corner_radius_top_right, 0);
        this.mCornerRadiusBottomLeft = typedArrayObtainStyledAttributes.getDimensionPixelSize(com.ruffian.library.widget.R.styleable.RBaseView_corner_radius_bottom_left, 0);
        this.mCornerRadiusBottomRight = typedArrayObtainStyledAttributes.getDimensionPixelSize(com.ruffian.library.widget.R.styleable.RBaseView_corner_radius_bottom_right, 0);
        this.mBorderDashWidth = typedArrayObtainStyledAttributes.getDimensionPixelSize(com.ruffian.library.widget.R.styleable.RBaseView_border_dash_width, -1);
        this.mBorderDashGap = typedArrayObtainStyledAttributes.getDimensionPixelSize(com.ruffian.library.widget.R.styleable.RBaseView_border_dash_gap, -1);
        this.mBorderWidthNormal = typedArrayObtainStyledAttributes.getDimensionPixelSize(com.ruffian.library.widget.R.styleable.RBaseView_border_width_normal, -1);
        this.mBorderWidthPressed = typedArrayObtainStyledAttributes.getDimensionPixelSize(com.ruffian.library.widget.R.styleable.RBaseView_border_width_pressed, -1);
        this.mBorderWidthUnable = typedArrayObtainStyledAttributes.getDimensionPixelSize(com.ruffian.library.widget.R.styleable.RBaseView_border_width_unable, -1);
        this.mBorderWidthChecked = typedArrayObtainStyledAttributes.getDimensionPixelSize(com.ruffian.library.widget.R.styleable.RBaseView_border_width_checked, -1);
        this.mBorderWidthSelected = typedArrayObtainStyledAttributes.getDimensionPixelSize(com.ruffian.library.widget.R.styleable.RBaseView_border_width_selected, -1);
        this.mBorderColorNormal = typedArrayObtainStyledAttributes.getColor(com.ruffian.library.widget.R.styleable.RBaseView_border_color_normal, 0);
        this.mBorderColorPressed = typedArrayObtainStyledAttributes.getColor(com.ruffian.library.widget.R.styleable.RBaseView_border_color_pressed, 0);
        this.mBorderColorUnable = typedArrayObtainStyledAttributes.getColor(com.ruffian.library.widget.R.styleable.RBaseView_border_color_unable, 0);
        this.mBorderColorChecked = typedArrayObtainStyledAttributes.getColor(com.ruffian.library.widget.R.styleable.RBaseView_border_color_checked, 0);
        this.mBorderColorSelected = typedArrayObtainStyledAttributes.getColor(com.ruffian.library.widget.R.styleable.RBaseView_border_color_selected, 0);
        Object[] backgroundInfo = getBackgroundInfo(typedArrayObtainStyledAttributes, com.ruffian.library.widget.R.styleable.RBaseView_background_normal);
        this.mBackgroundColorNormal = ((Integer) backgroundInfo[1]).intValue();
        this.mBackgroundColorNormalArray = (int[]) backgroundInfo[2];
        this.mBackgroundNormalBmp = (Drawable) backgroundInfo[3];
        Object[] backgroundInfo2 = getBackgroundInfo(typedArrayObtainStyledAttributes, com.ruffian.library.widget.R.styleable.RBaseView_background_pressed);
        this.mBackgroundColorPressed = ((Integer) backgroundInfo2[1]).intValue();
        this.mBackgroundColorPressedArray = (int[]) backgroundInfo2[2];
        this.mBackgroundPressedBmp = (Drawable) backgroundInfo2[3];
        Object[] backgroundInfo3 = getBackgroundInfo(typedArrayObtainStyledAttributes, com.ruffian.library.widget.R.styleable.RBaseView_background_unable);
        this.mBackgroundColorUnable = ((Integer) backgroundInfo3[1]).intValue();
        this.mBackgroundColorUnableArray = (int[]) backgroundInfo3[2];
        this.mBackgroundUnableBmp = (Drawable) backgroundInfo3[3];
        Object[] backgroundInfo4 = getBackgroundInfo(typedArrayObtainStyledAttributes, com.ruffian.library.widget.R.styleable.RBaseView_background_checked);
        this.mBackgroundColorChecked = ((Integer) backgroundInfo4[1]).intValue();
        this.mBackgroundColorCheckedArray = (int[]) backgroundInfo4[2];
        this.mBackgroundCheckedBmp = (Drawable) backgroundInfo4[3];
        Object[] backgroundInfo5 = getBackgroundInfo(typedArrayObtainStyledAttributes, com.ruffian.library.widget.R.styleable.RBaseView_background_selected);
        this.mBackgroundColorSelected = ((Integer) backgroundInfo5[1]).intValue();
        this.mBackgroundColorSelectedArray = (int[]) backgroundInfo5[2];
        this.mBackgroundSelectedBmp = (Drawable) backgroundInfo5[3];
        this.mGradientType = typedArrayObtainStyledAttributes.getInt(com.ruffian.library.widget.R.styleable.RBaseView_gradient_type, 0);
        this.mGradientOrientation = getGradientOrientation(typedArrayObtainStyledAttributes);
        this.mGradientRadius = typedArrayObtainStyledAttributes.getDimensionPixelSize(com.ruffian.library.widget.R.styleable.RBaseView_gradient_radius, -1);
        this.mGradientCenterX = typedArrayObtainStyledAttributes.getFloat(com.ruffian.library.widget.R.styleable.RBaseView_gradient_centerX, 0.5f);
        this.mGradientCenterY = typedArrayObtainStyledAttributes.getFloat(com.ruffian.library.widget.R.styleable.RBaseView_gradient_centerY, 0.5f);
        this.mIsEnabled = typedArrayObtainStyledAttributes.getBoolean(com.ruffian.library.widget.R.styleable.RBaseView_enabled, true);
        this.mUseRipple = typedArrayObtainStyledAttributes.getBoolean(com.ruffian.library.widget.R.styleable.RBaseView_ripple, false);
        this.mRippleColor = typedArrayObtainStyledAttributes.getColor(com.ruffian.library.widget.R.styleable.RBaseView_ripple_color, SupportMenu.CATEGORY_MASK);
        this.mRippleMaskDrawable = typedArrayObtainStyledAttributes.getDrawable(com.ruffian.library.widget.R.styleable.RBaseView_ripple_mask);
        this.mRippleMaskStyle = typedArrayObtainStyledAttributes.getInt(com.ruffian.library.widget.R.styleable.RBaseView_ripple_mask_style, 2);
        this.mShadowDx = typedArrayObtainStyledAttributes.getDimensionPixelSize(com.ruffian.library.widget.R.styleable.RBaseView_shadow_dx, 0);
        this.mShadowDy = typedArrayObtainStyledAttributes.getDimensionPixelSize(com.ruffian.library.widget.R.styleable.RBaseView_shadow_dy, 0);
        this.mShadowColor = typedArrayObtainStyledAttributes.getColor(com.ruffian.library.widget.R.styleable.RBaseView_shadow_color, -7829368);
        this.mShadowRadius = typedArrayObtainStyledAttributes.getDimensionPixelSize(com.ruffian.library.widget.R.styleable.RBaseView_shadow_radius, -1);
        this.mClipLayout = typedArrayObtainStyledAttributes.getBoolean(com.ruffian.library.widget.R.styleable.RBaseView_clip_layout, false);
        typedArrayObtainStyledAttributes.recycle();
        setup();
    }

    private void initClip() {
        this.mClipHelper.initClip(this.mView, this.mClipLayout, new ClipPathManager.ClipPathCreator() { // from class: com.ruffian.library.widget.helper.RBaseHelper.2
            @Override // com.ruffian.library.widget.clip.ClipPathManager.ClipPathCreator
            public Path createClipPath(int i2, int i3) {
                Path path = new Path();
                path.addRoundRect(new RectF(0.0f, 0.0f, i2, i3), RBaseHelper.this.mBorderRadii, Path.Direction.CCW);
                return path;
            }
        });
    }

    public static boolean isRtl() {
        return TextUtilsCompat.getLayoutDirectionFromLocale(Locale.getDefault()) == 1;
    }

    private boolean isUseRipple() {
        return this.mUseRipple;
    }

    private void setBackgroundState() throws SecurityException {
        boolean z2 = (!(this.mBackgroundColorNormal == 0 && this.mBackgroundColorUnable == 0 && this.mBackgroundColorPressed == 0 && this.mBackgroundColorChecked == 0 && this.mBackgroundColorSelected == 0) || !(this.mBackgroundColorNormalArray == null && this.mBackgroundColorUnableArray == null && this.mBackgroundColorPressedArray == null && this.mBackgroundColorCheckedArray == null && this.mBackgroundColorSelectedArray == null) || !(this.mBackgroundNormalBmp == null && this.mBackgroundPressedBmp == null && this.mBackgroundUnableBmp == null && this.mBackgroundCheckedBmp == null && this.mBackgroundSelectedBmp == null)) || ((this.mCornerRadius > (-1.0f) ? 1 : (this.mCornerRadius == (-1.0f) ? 0 : -1)) != 0 || (this.mCornerRadiusTopLeft > 0.0f ? 1 : (this.mCornerRadiusTopLeft == 0.0f ? 0 : -1)) != 0 || (this.mCornerRadiusTopRight > 0.0f ? 1 : (this.mCornerRadiusTopRight == 0.0f ? 0 : -1)) != 0 || (this.mCornerRadiusBottomLeft > 0.0f ? 1 : (this.mCornerRadiusBottomLeft == 0.0f ? 0 : -1)) != 0 || (this.mCornerRadiusBottomRight > 0.0f ? 1 : (this.mCornerRadiusBottomRight == 0.0f ? 0 : -1)) != 0) || ((this.mBorderDashWidth > (-1.0f) ? 1 : (this.mBorderDashWidth == (-1.0f) ? 0 : -1)) != 0 || (this.mBorderDashGap > (-1.0f) ? 1 : (this.mBorderDashGap == (-1.0f) ? 0 : -1)) != 0 || this.mBorderWidthNormal != -1 || this.mBorderWidthPressed != -1 || this.mBorderWidthUnable != -1 || this.mBorderWidthChecked != -1 || this.mBorderWidthSelected != -1 || this.mBorderColorNormal != 0 || this.mBorderColorPressed != 0 || this.mBorderColorUnable != 0 || this.mBorderColorChecked != 0 || this.mBorderColorSelected != 0);
        if (z2 || useShadow() || useRipple()) {
            this.mBackgroundDrawable = getBackgroundDrawable(z2, this.mRippleColor);
            if (useShadow()) {
                if (this.mShadowDrawable == null) {
                    this.mShadowDrawable = new ShadowBitmapDrawable();
                }
                this.mShadowDrawable.updateParameter(this.mShadowColor, this.mShadowRadius, this.mShadowDx, this.mShadowDy, this.mBorderRadii);
                int iAbs = this.mShadowRadius + Math.abs(this.mShadowDx);
                int iAbs2 = this.mShadowRadius + Math.abs(this.mShadowDx);
                int iAbs3 = this.mShadowRadius + Math.abs(this.mShadowDy);
                int iAbs4 = this.mShadowRadius + Math.abs(this.mShadowDy);
                LayerDrawable layerDrawable = new LayerDrawable(new Drawable[]{this.mShadowDrawable, this.mBackgroundDrawable});
                layerDrawable.setLayerInset(1, iAbs, iAbs3, iAbs2, iAbs4);
                this.mBackgroundDrawable = layerDrawable;
            }
        } else {
            this.mBackgroundDrawable = this.mViewBackground;
        }
        this.mView.setBackground(this.mBackgroundDrawable);
    }

    private void setBorder() {
        this.mBackgroundNormal.setStroke(this.mBorderWidthNormal, this.mBorderColorNormal, this.mBorderDashWidth, this.mBorderDashGap);
        this.mBackgroundPressed.setStroke(this.mBorderWidthPressed, this.mBorderColorPressed, this.mBorderDashWidth, this.mBorderDashGap);
        this.mBackgroundUnable.setStroke(this.mBorderWidthUnable, this.mBorderColorUnable, this.mBorderDashWidth, this.mBorderDashGap);
        this.mBackgroundChecked.setStroke(this.mBorderWidthChecked, this.mBorderColorChecked, this.mBorderDashWidth, this.mBorderDashGap);
        this.mBackgroundSelected.setStroke(this.mBorderWidthSelected, this.mBorderColorSelected, this.mBorderDashWidth, this.mBorderDashGap);
    }

    private GradientDrawable setColors(GradientDrawable gradientDrawable, int[] iArr) {
        if (gradientDrawable == null) {
            gradientDrawable = new GradientDrawable();
        }
        gradientDrawable.setOrientation(this.mGradientOrientation);
        gradientDrawable.setColors(iArr);
        return gradientDrawable;
    }

    private void setGradient() {
        this.mBackgroundNormal.setGradientType(this.mGradientType);
        this.mBackgroundNormal.setGradientRadius(this.mGradientRadius);
        this.mBackgroundNormal.setGradientCenter(this.mGradientCenterX, this.mGradientCenterY);
        this.mBackgroundPressed.setGradientType(this.mGradientType);
        this.mBackgroundPressed.setGradientRadius(this.mGradientRadius);
        this.mBackgroundPressed.setGradientCenter(this.mGradientCenterX, this.mGradientCenterY);
        this.mBackgroundUnable.setGradientType(this.mGradientType);
        this.mBackgroundUnable.setGradientRadius(this.mGradientRadius);
        this.mBackgroundUnable.setGradientCenter(this.mGradientCenterX, this.mGradientCenterY);
        this.mBackgroundChecked.setGradientType(this.mGradientType);
        this.mBackgroundChecked.setGradientRadius(this.mGradientRadius);
        this.mBackgroundChecked.setGradientCenter(this.mGradientCenterX, this.mGradientCenterY);
        this.mBackgroundSelected.setGradientType(this.mGradientType);
        this.mBackgroundSelected.setGradientRadius(this.mGradientRadius);
        this.mBackgroundSelected.setGradientCenter(this.mGradientCenterX, this.mGradientCenterY);
    }

    private void setRadius() {
        float f2 = this.mCornerRadius;
        if (f2 >= 0.0f) {
            float[] fArr = this.mBorderRadii;
            fArr[0] = f2;
            fArr[1] = f2;
            fArr[2] = f2;
            fArr[3] = f2;
            fArr[4] = f2;
            fArr[5] = f2;
            fArr[6] = f2;
            fArr[7] = f2;
        } else {
            boolean zIsRtl = isRtl();
            float[] fArr2 = this.mBorderRadii;
            fArr2[0] = zIsRtl ? this.mCornerRadiusTopRight : this.mCornerRadiusTopLeft;
            fArr2[1] = zIsRtl ? this.mCornerRadiusTopRight : this.mCornerRadiusTopLeft;
            fArr2[2] = zIsRtl ? this.mCornerRadiusTopLeft : this.mCornerRadiusTopRight;
            fArr2[3] = zIsRtl ? this.mCornerRadiusTopLeft : this.mCornerRadiusTopRight;
            fArr2[4] = zIsRtl ? this.mCornerRadiusBottomLeft : this.mCornerRadiusBottomRight;
            fArr2[5] = zIsRtl ? this.mCornerRadiusBottomLeft : this.mCornerRadiusBottomRight;
            fArr2[6] = zIsRtl ? this.mCornerRadiusBottomRight : this.mCornerRadiusBottomLeft;
            fArr2[7] = zIsRtl ? this.mCornerRadiusBottomRight : this.mCornerRadiusBottomLeft;
        }
        this.mBackgroundNormal.setCornerRadii(this.mBorderRadii);
        this.mBackgroundPressed.setCornerRadii(this.mBorderRadii);
        this.mBackgroundUnable.setCornerRadii(this.mBorderRadii);
        this.mBackgroundChecked.setCornerRadii(this.mBorderRadii);
        this.mBackgroundSelected.setCornerRadii(this.mBorderRadii);
    }

    private void setStateListDrawable() {
        StateListDrawable stateListDrawable = new StateListDrawable();
        this.mStateBackground = stateListDrawable;
        Drawable drawable = this.mBackgroundUnableBmp;
        if (drawable == null) {
            stateListDrawable.addState(this.states[0], this.mBackgroundUnable);
        } else {
            stateListDrawable.addState(this.states[0], drawable);
        }
        Drawable drawable2 = this.mBackgroundPressedBmp;
        if (drawable2 == null) {
            this.mStateBackground.addState(this.states[1], this.mBackgroundPressed);
        } else {
            this.mStateBackground.addState(this.states[1], drawable2);
        }
        Drawable drawable3 = this.mBackgroundPressedBmp;
        if (drawable3 == null) {
            this.mStateBackground.addState(this.states[2], this.mBackgroundPressed);
        } else {
            this.mStateBackground.addState(this.states[2], drawable3);
        }
        Drawable drawable4 = this.mBackgroundCheckedBmp;
        if (drawable4 == null) {
            this.mStateBackground.addState(this.states[3], this.mBackgroundChecked);
        } else {
            this.mStateBackground.addState(this.states[3], drawable4);
        }
        Drawable drawable5 = this.mBackgroundSelectedBmp;
        if (drawable5 == null) {
            this.mStateBackground.addState(this.states[4], this.mBackgroundSelected);
        } else {
            this.mStateBackground.addState(this.states[4], drawable5);
        }
        Drawable drawable6 = this.mBackgroundNormalBmp;
        if (drawable6 == null) {
            this.mStateBackground.addState(this.states[5], this.mBackgroundNormal);
        } else {
            this.mStateBackground.addState(this.states[5], drawable6);
        }
    }

    private void setup() throws SecurityException {
        if (this.mView.isEnabled()) {
            this.mView.setEnabled(this.mIsEnabled);
        }
        this.mBackgroundNormal = new GradientDrawable();
        this.mBackgroundPressed = new GradientDrawable();
        this.mBackgroundUnable = new GradientDrawable();
        this.mBackgroundChecked = new GradientDrawable();
        this.mBackgroundSelected = new GradientDrawable();
        this.mViewBackground = this.mView.getBackground();
        this.mStateBackground = new StateListDrawable();
        int[][] iArr = this.states;
        iArr[0] = new int[]{-16842910};
        iArr[1] = new int[]{R.attr.state_focused};
        iArr[2] = new int[]{R.attr.state_pressed};
        iArr[3] = new int[]{R.attr.state_checked};
        iArr[4] = new int[]{R.attr.state_selected};
        iArr[5] = new int[]{R.attr.state_enabled};
        setupDefaultValue(true);
        setGradient();
        setStateListDrawable();
        setBorder();
        setRadius();
        setBackgroundState();
    }

    private void setupDefaultValue(boolean z2) {
        if (z2) {
            this.mHasPressedBgColor = (this.mBackgroundColorPressed == 0 && this.mBackgroundColorPressedArray == null) ? false : true;
            this.mHasUnableBgColor = (this.mBackgroundColorUnable == 0 && this.mBackgroundColorUnableArray == null) ? false : true;
            this.mHasCheckedBgColor = (this.mBackgroundColorChecked == 0 && this.mBackgroundColorCheckedArray == null) ? false : true;
            this.mHasSelectedBgColor = (this.mBackgroundColorSelected == 0 && this.mBackgroundColorSelectedArray == null) ? false : true;
            this.mHasPressedBgBmp = this.mBackgroundPressedBmp != null;
            this.mHasUnableBgBmp = this.mBackgroundUnableBmp != null;
            this.mHasCheckedBgBmp = this.mBackgroundCheckedBmp != null;
            this.mHasSelectedBgBmp = this.mBackgroundSelectedBmp != null;
            this.mHasPressedBorderColor = this.mBorderColorPressed != 0;
            this.mHasUnableBorderColor = this.mBorderColorUnable != 0;
            this.mHasCheckedBorderColor = this.mBorderColorChecked != 0;
            this.mHasSelectedBorderColor = this.mBorderColorSelected != 0;
            this.mHasPressedBorderWidth = this.mBorderWidthPressed != -1;
            this.mHasUnableBorderWidth = this.mBorderWidthUnable != -1;
            this.mHasCheckedBorderWidth = this.mBorderWidthChecked != -1;
            this.mHasSelectedBorderWidth = this.mBorderWidthSelected != -1;
        }
        if (!this.mHasPressedBgColor) {
            this.mBackgroundColorPressed = this.mBackgroundColorNormal;
            this.mBackgroundColorPressedArray = this.mBackgroundColorNormalArray;
        }
        if (!this.mHasPressedBgBmp) {
            this.mBackgroundPressedBmp = this.mBackgroundNormalBmp;
        }
        if (!this.mHasUnableBgColor) {
            this.mBackgroundColorUnable = this.mBackgroundColorNormal;
            this.mBackgroundColorUnableArray = this.mBackgroundColorNormalArray;
        }
        if (!this.mHasUnableBgBmp) {
            this.mBackgroundUnableBmp = this.mBackgroundNormalBmp;
        }
        if (!this.mHasCheckedBgColor) {
            this.mBackgroundColorChecked = this.mBackgroundColorNormal;
            this.mBackgroundColorCheckedArray = this.mBackgroundColorNormalArray;
        }
        if (!this.mHasSelectedBgColor) {
            this.mBackgroundColorSelected = this.mBackgroundColorNormal;
            this.mBackgroundColorSelectedArray = this.mBackgroundColorNormalArray;
        }
        if (!this.mHasCheckedBgBmp) {
            this.mBackgroundCheckedBmp = this.mBackgroundNormalBmp;
        }
        if (!this.mHasSelectedBgBmp) {
            this.mBackgroundSelectedBmp = this.mBackgroundNormalBmp;
        }
        int[] iArr = this.mBackgroundColorNormalArray;
        if (iArr == null || iArr.length <= 0) {
            GradientDrawable gradientDrawable = this.mBackgroundNormal;
            int i2 = this.mBackgroundColorNormal;
            this.mBackgroundNormal = setColors(gradientDrawable, new int[]{i2, i2});
        } else {
            this.mBackgroundNormal = setColors(this.mBackgroundNormal, iArr);
        }
        int[] iArr2 = this.mBackgroundColorPressedArray;
        if (iArr2 == null || iArr2.length <= 0) {
            GradientDrawable gradientDrawable2 = this.mBackgroundPressed;
            int i3 = this.mBackgroundColorPressed;
            this.mBackgroundPressed = setColors(gradientDrawable2, new int[]{i3, i3});
        } else {
            this.mBackgroundPressed = setColors(this.mBackgroundPressed, iArr2);
        }
        int[] iArr3 = this.mBackgroundColorUnableArray;
        if (iArr3 == null || iArr3.length <= 0) {
            GradientDrawable gradientDrawable3 = this.mBackgroundUnable;
            int i4 = this.mBackgroundColorUnable;
            this.mBackgroundUnable = setColors(gradientDrawable3, new int[]{i4, i4});
        } else {
            this.mBackgroundUnable = setColors(this.mBackgroundUnable, iArr3);
        }
        int[] iArr4 = this.mBackgroundColorCheckedArray;
        if (iArr4 == null || iArr4.length <= 0) {
            GradientDrawable gradientDrawable4 = this.mBackgroundChecked;
            int i5 = this.mBackgroundColorChecked;
            this.mBackgroundChecked = setColors(gradientDrawable4, new int[]{i5, i5});
        } else {
            this.mBackgroundChecked = setColors(this.mBackgroundChecked, iArr4);
        }
        int[] iArr5 = this.mBackgroundColorSelectedArray;
        if (iArr5 == null || iArr5.length <= 0) {
            GradientDrawable gradientDrawable5 = this.mBackgroundSelected;
            int i6 = this.mBackgroundColorSelected;
            this.mBackgroundSelected = setColors(gradientDrawable5, new int[]{i6, i6});
        } else {
            this.mBackgroundSelected = setColors(this.mBackgroundSelected, iArr5);
        }
        if (!this.mHasPressedBorderWidth) {
            this.mBorderWidthPressed = this.mBorderWidthNormal;
        }
        if (!this.mHasUnableBorderWidth) {
            this.mBorderWidthUnable = this.mBorderWidthNormal;
        }
        if (!this.mHasCheckedBorderWidth) {
            this.mBorderWidthChecked = this.mBorderWidthNormal;
        }
        if (!this.mHasSelectedBorderWidth) {
            this.mBorderWidthSelected = this.mBorderWidthNormal;
        }
        if (!this.mHasPressedBorderColor) {
            this.mBorderColorPressed = this.mBorderColorNormal;
        }
        if (!this.mHasUnableBorderColor) {
            this.mBorderColorUnable = this.mBorderColorNormal;
        }
        if (!this.mHasCheckedBorderColor) {
            this.mBorderColorChecked = this.mBorderColorNormal;
        }
        if (this.mHasSelectedBorderColor) {
            return;
        }
        this.mBorderColorSelected = this.mBorderColorNormal;
    }

    private void updateBackgroundValue() throws SecurityException {
        setupDefaultValue(false);
        setStateListDrawable();
        setBackgroundState();
    }

    private void updateBorderValue() throws SecurityException {
        setupDefaultValue(false);
        setBorder();
        setBackgroundState();
    }

    private void updateRadiusValue() throws SecurityException {
        setRadius();
        setBackgroundState();
    }

    @Override // com.ruffian.library.widget.clip.IClip
    public void dispatchDraw(Canvas canvas) {
        this.mClipHelper.dispatchDraw(canvas);
    }

    public float dp2px(int i2) {
        return TypedValue.applyDimension(1, i2, this.mContext.getResources().getDisplayMetrics());
    }

    public int getBackgroundColorChecked() {
        return this.mBackgroundColorChecked;
    }

    public int[] getBackgroundColorCheckedArray() {
        return this.mBackgroundColorCheckedArray;
    }

    public int getBackgroundColorNormal() {
        return this.mBackgroundColorNormal;
    }

    public int[] getBackgroundColorNormalArray() {
        return this.mBackgroundColorNormalArray;
    }

    public int getBackgroundColorPressed() {
        return this.mBackgroundColorPressed;
    }

    public int[] getBackgroundColorPressedArray() {
        return this.mBackgroundColorPressedArray;
    }

    public int getBackgroundColorSelected() {
        return this.mBackgroundColorSelected;
    }

    public int[] getBackgroundColorSelectedArray() {
        return this.mBackgroundColorSelectedArray;
    }

    public int getBackgroundColorUnable() {
        return this.mBackgroundColorUnable;
    }

    public int[] getBackgroundColorUnableArray() {
        return this.mBackgroundColorUnableArray;
    }

    public Drawable getBackgroundDrawableChecked() {
        return this.mBackgroundCheckedBmp;
    }

    public Drawable getBackgroundDrawableNormal() {
        return this.mBackgroundNormalBmp;
    }

    public Drawable getBackgroundDrawablePressed() {
        return this.mBackgroundPressedBmp;
    }

    public Drawable getBackgroundDrawableSelected() {
        return this.mBackgroundSelectedBmp;
    }

    public Drawable getBackgroundDrawableUnable() {
        return this.mBackgroundUnableBmp;
    }

    public int getBorderColorChecked() {
        return this.mBorderColorChecked;
    }

    public int getBorderColorNormal() {
        return this.mBorderColorNormal;
    }

    public int getBorderColorPressed() {
        return this.mBorderColorPressed;
    }

    public int getBorderColorSelected() {
        return this.mBorderColorSelected;
    }

    public int getBorderColorUnable() {
        return this.mBorderColorUnable;
    }

    public float getBorderDashGap() {
        return this.mBorderDashGap;
    }

    public float getBorderDashWidth() {
        return this.mBorderDashWidth;
    }

    public int getBorderWidthChecked() {
        return this.mBorderWidthChecked;
    }

    public int getBorderWidthNormal() {
        return this.mBorderWidthNormal;
    }

    public int getBorderWidthPressed() {
        return this.mBorderWidthPressed;
    }

    public int getBorderWidthSelected() {
        return this.mBorderWidthSelected;
    }

    public int getBorderWidthUnable() {
        return this.mBorderWidthUnable;
    }

    public float getCornerRadius() {
        return this.mCornerRadius;
    }

    public float getCornerRadiusBottomLeft() {
        return this.mCornerRadiusBottomLeft;
    }

    public float getCornerRadiusBottomRight() {
        return this.mCornerRadiusBottomRight;
    }

    public float getCornerRadiusTopLeft() {
        return this.mCornerRadiusTopLeft;
    }

    public float getCornerRadiusTopRight() {
        return this.mCornerRadiusTopRight;
    }

    public float getGradientCenterX() {
        return this.mGradientCenterX;
    }

    public float getGradientCenterY() {
        return this.mGradientCenterY;
    }

    public float getGradientRadius() {
        return this.mGradientRadius;
    }

    public int getGradientType() {
        return this.mGradientType;
    }

    public int getRippleColor() {
        return this.mRippleColor;
    }

    public Drawable getRippleMaskDrawable() {
        return this.mRippleMaskDrawable;
    }

    public int getShadowColor() {
        return this.mShadowColor;
    }

    public int getShadowDx() {
        return this.mShadowDx;
    }

    public int getShadowDy() {
        return this.mShadowDy;
    }

    public int getShadowRadius() {
        return this.mShadowRadius;
    }

    public boolean isOutsideView(int i2, int i3) {
        if (i2 >= 0 - this.mTouchSlop) {
            int width = this.mView.getWidth();
            int i4 = this.mTouchSlop;
            if (i2 < width + i4 && i3 >= 0 - i4 && i3 < this.mView.getHeight() + this.mTouchSlop) {
                return false;
            }
        }
        return true;
    }

    @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
    public void onGlobalLayout() throws SecurityException {
        this.mView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
        if (this.mGradientRadius <= 0.0f) {
            setGradientRadius(Math.min(this.mView.getWidth(), this.mView.getHeight()) / 2.0f);
        }
        initClip();
    }

    @Override // com.ruffian.library.widget.clip.IClip
    public void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
        this.mClipHelper.onLayout(z2, i2, i3, i4, i5);
    }

    public RBaseHelper setBackgroundColorChecked(@ColorInt int i2) throws SecurityException {
        this.mBackgroundColorChecked = i2;
        this.mBackgroundColorCheckedArray = null;
        this.mBackgroundCheckedBmp = null;
        this.mHasCheckedBgColor = true;
        this.mHasCheckedBgBmp = false;
        updateBackgroundValue();
        return this;
    }

    public RBaseHelper setBackgroundColorCheckedArray(int[] iArr) throws SecurityException {
        this.mBackgroundColorCheckedArray = iArr;
        this.mBackgroundColorChecked = 0;
        this.mBackgroundCheckedBmp = null;
        this.mHasCheckedBgColor = true;
        this.mHasCheckedBgBmp = false;
        updateBackgroundValue();
        return this;
    }

    public RBaseHelper setBackgroundColorNormal(@ColorInt int i2) throws SecurityException {
        this.mBackgroundColorNormal = i2;
        this.mBackgroundColorNormalArray = null;
        this.mBackgroundNormalBmp = null;
        updateBackgroundValue();
        return this;
    }

    public RBaseHelper setBackgroundColorNormalArray(int[] iArr) throws SecurityException {
        this.mBackgroundColorNormalArray = iArr;
        this.mBackgroundColorNormal = 0;
        this.mBackgroundNormalBmp = null;
        updateBackgroundValue();
        return this;
    }

    public RBaseHelper setBackgroundColorPressed(@ColorInt int i2) throws SecurityException {
        this.mBackgroundColorPressed = i2;
        this.mBackgroundColorPressedArray = null;
        this.mBackgroundPressedBmp = null;
        this.mHasPressedBgColor = true;
        this.mHasPressedBgBmp = false;
        updateBackgroundValue();
        return this;
    }

    public RBaseHelper setBackgroundColorPressedArray(int[] iArr) throws SecurityException {
        this.mBackgroundColorPressedArray = iArr;
        this.mBackgroundColorPressed = 0;
        this.mBackgroundPressedBmp = null;
        this.mHasPressedBgColor = true;
        this.mHasPressedBgBmp = false;
        updateBackgroundValue();
        return this;
    }

    public RBaseHelper setBackgroundColorSelected(@ColorInt int i2) throws SecurityException {
        this.mBackgroundColorSelected = i2;
        this.mBackgroundColorSelectedArray = null;
        this.mBackgroundSelectedBmp = null;
        this.mHasSelectedBgColor = true;
        this.mHasSelectedBgBmp = false;
        updateBackgroundValue();
        return this;
    }

    public RBaseHelper setBackgroundColorSelectedArray(int[] iArr) throws SecurityException {
        this.mBackgroundColorSelectedArray = iArr;
        this.mBackgroundColorSelected = 0;
        this.mBackgroundSelectedBmp = null;
        this.mHasSelectedBgColor = true;
        this.mHasSelectedBgBmp = false;
        updateBackgroundValue();
        return this;
    }

    public RBaseHelper setBackgroundColorUnable(@ColorInt int i2) throws SecurityException {
        this.mBackgroundColorUnable = i2;
        this.mBackgroundColorUnableArray = null;
        this.mBackgroundUnableBmp = null;
        this.mHasUnableBgColor = true;
        this.mHasUnableBgBmp = false;
        updateBackgroundValue();
        return this;
    }

    public RBaseHelper setBackgroundColorUnableArray(int[] iArr) throws SecurityException {
        this.mBackgroundColorUnableArray = iArr;
        this.mBackgroundColorUnable = 0;
        this.mBackgroundUnableBmp = null;
        this.mHasUnableBgColor = true;
        this.mHasUnableBgBmp = false;
        updateBackgroundValue();
        return this;
    }

    public RBaseHelper setBackgroundDrawableChecked(Drawable drawable) throws SecurityException {
        this.mBackgroundCheckedBmp = drawable;
        this.mBackgroundColorChecked = 0;
        this.mBackgroundColorCheckedArray = null;
        this.mHasCheckedBgColor = false;
        this.mHasCheckedBgBmp = true;
        updateBackgroundValue();
        return this;
    }

    public RBaseHelper setBackgroundDrawableNormal(Drawable drawable) throws SecurityException {
        this.mBackgroundNormalBmp = drawable;
        this.mBackgroundColorNormalArray = null;
        this.mBackgroundColorNormal = 0;
        updateBackgroundValue();
        return this;
    }

    public RBaseHelper setBackgroundDrawablePressed(Drawable drawable) throws SecurityException {
        this.mBackgroundPressedBmp = drawable;
        this.mBackgroundColorPressedArray = null;
        this.mBackgroundColorPressed = 0;
        this.mHasPressedBgColor = false;
        this.mHasPressedBgBmp = true;
        updateBackgroundValue();
        return this;
    }

    public RBaseHelper setBackgroundDrawableSelected(Drawable drawable) throws SecurityException {
        this.mBackgroundSelectedBmp = drawable;
        this.mBackgroundColorSelected = 0;
        this.mBackgroundColorSelectedArray = null;
        this.mHasSelectedBgColor = false;
        this.mHasSelectedBgBmp = true;
        updateBackgroundValue();
        return this;
    }

    public RBaseHelper setBackgroundDrawableUnable(Drawable drawable) throws SecurityException {
        this.mBackgroundUnableBmp = drawable;
        this.mBackgroundColorUnable = 0;
        this.mBackgroundColorUnableArray = null;
        this.mHasUnableBgColor = false;
        this.mHasUnableBgBmp = true;
        updateBackgroundValue();
        return this;
    }

    public RBaseHelper setBorderColor(@ColorInt int i2, @ColorInt int i3, @ColorInt int i4, @ColorInt int i5, @ColorInt int i6) throws SecurityException {
        this.mBorderColorNormal = i2;
        this.mBorderColorPressed = i3;
        this.mBorderColorUnable = i4;
        this.mBorderColorChecked = i5;
        this.mBorderColorSelected = i6;
        this.mHasPressedBorderColor = true;
        this.mHasUnableBorderColor = true;
        this.mHasCheckedBorderColor = true;
        this.mHasSelectedBorderColor = true;
        updateBorderValue();
        return this;
    }

    public RBaseHelper setBorderColorChecked(@ColorInt int i2) throws SecurityException {
        this.mBorderColorChecked = i2;
        this.mHasCheckedBorderColor = true;
        updateBorderValue();
        return this;
    }

    public RBaseHelper setBorderColorNormal(@ColorInt int i2) throws SecurityException {
        this.mBorderColorNormal = i2;
        updateBorderValue();
        return this;
    }

    public RBaseHelper setBorderColorPressed(@ColorInt int i2) throws SecurityException {
        this.mBorderColorPressed = i2;
        this.mHasPressedBorderColor = true;
        updateBorderValue();
        return this;
    }

    public RBaseHelper setBorderColorSelected(@ColorInt int i2) throws SecurityException {
        this.mBorderColorSelected = i2;
        this.mHasSelectedBorderColor = true;
        updateBorderValue();
        return this;
    }

    public RBaseHelper setBorderColorUnable(@ColorInt int i2) throws SecurityException {
        this.mBorderColorUnable = i2;
        this.mHasUnableBorderColor = true;
        updateBorderValue();
        return this;
    }

    public RBaseHelper setBorderDash(float f2, float f3) throws SecurityException {
        this.mBorderDashWidth = f2;
        this.mBorderDashGap = f3;
        updateBorderValue();
        return this;
    }

    public RBaseHelper setBorderDashGap(float f2) throws SecurityException {
        this.mBorderDashGap = f2;
        updateBorderValue();
        return this;
    }

    public RBaseHelper setBorderDashWidth(float f2) throws SecurityException {
        this.mBorderDashWidth = f2;
        updateBorderValue();
        return this;
    }

    public RBaseHelper setBorderWidth(int i2, int i3, int i4, int i5, int i6) throws SecurityException {
        this.mBorderWidthNormal = i2;
        this.mBorderWidthPressed = i3;
        this.mBorderWidthUnable = i4;
        this.mBorderWidthChecked = i5;
        this.mBorderWidthSelected = i6;
        this.mHasPressedBorderWidth = true;
        this.mHasUnableBorderWidth = true;
        this.mHasCheckedBorderWidth = true;
        this.mHasSelectedBorderWidth = true;
        updateBorderValue();
        return this;
    }

    public RBaseHelper setBorderWidthChecked(int i2) throws SecurityException {
        this.mBorderWidthChecked = i2;
        this.mHasCheckedBorderWidth = true;
        updateBorderValue();
        return this;
    }

    public RBaseHelper setBorderWidthNormal(int i2) throws SecurityException {
        this.mBorderWidthNormal = i2;
        updateBorderValue();
        return this;
    }

    public RBaseHelper setBorderWidthPressed(int i2) throws SecurityException {
        this.mBorderWidthPressed = i2;
        this.mHasPressedBorderWidth = true;
        updateBorderValue();
        return this;
    }

    public RBaseHelper setBorderWidthSelected(int i2) throws SecurityException {
        this.mBorderWidthSelected = i2;
        this.mHasSelectedBorderWidth = true;
        updateBorderValue();
        return this;
    }

    public RBaseHelper setBorderWidthUnable(int i2) throws SecurityException {
        this.mBorderWidthUnable = i2;
        this.mHasUnableBorderWidth = true;
        updateBorderValue();
        return this;
    }

    public void setCornerRadius(float f2) throws SecurityException {
        this.mCornerRadius = f2;
        updateRadiusValue();
    }

    public RBaseHelper setCornerRadiusBottomLeft(float f2) throws SecurityException {
        this.mCornerRadius = -1.0f;
        this.mCornerRadiusBottomLeft = f2;
        updateRadiusValue();
        return this;
    }

    public RBaseHelper setCornerRadiusBottomRight(float f2) throws SecurityException {
        this.mCornerRadius = -1.0f;
        this.mCornerRadiusBottomRight = f2;
        updateRadiusValue();
        return this;
    }

    public RBaseHelper setCornerRadiusTopLeft(float f2) throws SecurityException {
        this.mCornerRadius = -1.0f;
        this.mCornerRadiusTopLeft = f2;
        updateRadiusValue();
        return this;
    }

    public RBaseHelper setCornerRadiusTopRight(float f2) throws SecurityException {
        this.mCornerRadius = -1.0f;
        this.mCornerRadiusTopRight = f2;
        updateRadiusValue();
        return this;
    }

    public RBaseHelper setGradientCenterX(float f2) throws SecurityException {
        this.mGradientCenterX = f2;
        setGradient();
        setBackgroundState();
        return this;
    }

    public RBaseHelper setGradientCenterY(float f2) throws SecurityException {
        this.mGradientCenterY = f2;
        setGradient();
        setBackgroundState();
        return this;
    }

    public RBaseHelper setGradientOrientation(GradientDrawable.Orientation orientation) throws SecurityException {
        this.mGradientOrientation = orientation;
        setGradient();
        setBackgroundState();
        return this;
    }

    public RBaseHelper setGradientRadius(float f2) throws SecurityException {
        this.mGradientRadius = f2;
        setGradient();
        setBackgroundState();
        return this;
    }

    public RBaseHelper setGradientType(int i2) throws SecurityException {
        if (i2 < 0 || i2 > 2) {
            i2 = 0;
        }
        this.mGradientType = i2;
        setGradient();
        setBackgroundState();
        return this;
    }

    public RBaseHelper setRippleColor(@ColorInt int i2) throws SecurityException {
        this.mRippleColor = i2;
        this.mUseRipple = true;
        setBackgroundState();
        return this;
    }

    public RBaseHelper setRippleMaskDrawable(Drawable drawable) throws SecurityException {
        this.mRippleMaskDrawable = drawable;
        this.mUseRipple = true;
        this.mRippleMaskStyle = 3;
        setBackgroundState();
        return this;
    }

    public RBaseHelper setShadowColor(int i2) throws SecurityException {
        this.mShadowColor = i2;
        setBackgroundState();
        return this;
    }

    public RBaseHelper setShadowDx(int i2) throws SecurityException {
        this.mShadowDx = i2;
        setBackgroundState();
        return this;
    }

    public RBaseHelper setShadowDy(int i2) throws SecurityException {
        this.mShadowDy = i2;
        setBackgroundState();
        return this;
    }

    public RBaseHelper setShadowRadius(int i2) throws SecurityException {
        this.mShadowRadius = i2;
        setBackgroundState();
        return this;
    }

    public RBaseHelper setStateBackgroundColor(@ColorInt int i2, @ColorInt int i3, @ColorInt int i4, @ColorInt int i5, @ColorInt int i6) throws SecurityException {
        this.mBackgroundColorNormal = i2;
        this.mBackgroundColorPressed = i3;
        this.mBackgroundColorUnable = i4;
        this.mBackgroundColorChecked = i5;
        this.mBackgroundColorSelected = i6;
        this.mBackgroundColorNormalArray = null;
        this.mBackgroundColorPressedArray = null;
        this.mBackgroundColorUnableArray = null;
        this.mBackgroundColorCheckedArray = null;
        this.mBackgroundColorSelectedArray = null;
        this.mBackgroundNormalBmp = null;
        this.mBackgroundPressedBmp = null;
        this.mBackgroundSelectedBmp = null;
        this.mBackgroundCheckedBmp = null;
        this.mHasPressedBgColor = true;
        this.mHasCheckedBgColor = true;
        this.mHasSelectedBgColor = true;
        this.mHasUnableBgColor = true;
        this.mHasPressedBgBmp = false;
        this.mHasCheckedBgBmp = false;
        this.mHasSelectedBgBmp = false;
        this.mHasUnableBgBmp = false;
        updateBackgroundValue();
        return this;
    }

    public RBaseHelper setStateBackgroundColorArray(int[] iArr, int[] iArr2, int[] iArr3, int[] iArr4, int[] iArr5) throws SecurityException {
        this.mBackgroundColorNormalArray = iArr;
        this.mBackgroundColorPressedArray = iArr2;
        this.mBackgroundColorUnableArray = iArr3;
        this.mBackgroundColorCheckedArray = iArr4;
        this.mBackgroundColorSelectedArray = iArr5;
        this.mBackgroundColorNormal = 0;
        this.mBackgroundColorPressed = 0;
        this.mBackgroundColorUnable = 0;
        this.mBackgroundColorChecked = 0;
        this.mBackgroundColorSelected = 0;
        this.mBackgroundNormalBmp = null;
        this.mBackgroundPressedBmp = null;
        this.mBackgroundSelectedBmp = null;
        this.mBackgroundCheckedBmp = null;
        this.mHasPressedBgColor = true;
        this.mHasCheckedBgColor = true;
        this.mHasSelectedBgColor = true;
        this.mHasUnableBgColor = true;
        this.mHasPressedBgBmp = false;
        this.mHasCheckedBgBmp = false;
        this.mHasSelectedBgBmp = false;
        this.mHasUnableBgBmp = false;
        updateBackgroundValue();
        return this;
    }

    public RBaseHelper setUseRipple(boolean z2) throws SecurityException {
        this.mUseRipple = z2;
        setBackgroundState();
        return this;
    }

    public boolean useRipple() {
        return this.mUseRipple;
    }

    public boolean useShadow() {
        return this.mShadowRadius >= 0;
    }

    public RBaseHelper setCornerRadius(float f2, float f3, float f4, float f5) throws SecurityException {
        this.mCornerRadius = -1.0f;
        this.mCornerRadiusTopLeft = f2;
        this.mCornerRadiusTopRight = f3;
        this.mCornerRadiusBottomRight = f4;
        this.mCornerRadiusBottomLeft = f5;
        updateRadiusValue();
        return this;
    }

    public RBaseHelper setStateBackgroundColor(Drawable drawable, Drawable drawable2, Drawable drawable3, Drawable drawable4, Drawable drawable5) throws SecurityException {
        this.mBackgroundNormalBmp = drawable;
        this.mBackgroundPressedBmp = drawable2;
        this.mBackgroundUnableBmp = drawable3;
        this.mBackgroundCheckedBmp = drawable4;
        this.mBackgroundSelectedBmp = drawable5;
        this.mBackgroundColorNormalArray = null;
        this.mBackgroundColorPressedArray = null;
        this.mBackgroundColorUnableArray = null;
        this.mBackgroundColorCheckedArray = null;
        this.mBackgroundColorSelectedArray = null;
        this.mBackgroundColorNormal = 0;
        this.mBackgroundColorPressed = 0;
        this.mBackgroundColorUnable = 0;
        this.mBackgroundColorChecked = 0;
        this.mBackgroundColorSelected = 0;
        this.mHasPressedBgColor = false;
        this.mHasCheckedBgColor = false;
        this.mHasSelectedBgColor = false;
        this.mHasUnableBgColor = false;
        this.mHasPressedBgBmp = true;
        this.mHasCheckedBgBmp = true;
        this.mHasSelectedBgBmp = true;
        this.mHasUnableBgBmp = true;
        updateBackgroundValue();
        return this;
    }
}
