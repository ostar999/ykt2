package com.ruffian.library.widget.helper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TextView;
import androidx.annotation.ColorInt;
import androidx.annotation.StyleableRes;
import com.ruffian.library.widget.R;
import com.ruffian.library.widget.iface.ITextViewFeature;
import com.ruffian.library.widget.utils.TextViewUtils;

/* loaded from: classes6.dex */
public class RTextViewHelper extends RBaseHelper<TextView> implements ITextViewFeature {
    public static final int ICON_DIR_BOTTOM = 4;
    public static final int ICON_DIR_LEFT = 1;
    public static final int ICON_DIR_RIGHT = 3;
    public static final int ICON_DIR_TOP = 2;
    private String mCacheMultipleIconPaddingVale;
    private String mCacheSingleIconPaddingVale;
    private boolean mDrawableWithText;
    protected boolean mHasCheckedTextColor;
    protected boolean mHasPressedTextColor;
    protected boolean mHasSelectedTextColor;
    protected boolean mHasUnableTextColor;
    private Drawable mIcon;
    private Drawable mIconBottom;
    private Drawable mIconChecked;
    private Drawable mIconCheckedBottom;
    private Drawable mIconCheckedLeft;
    private Drawable mIconCheckedRight;
    private Drawable mIconCheckedTop;
    private int mIconDirection;
    private int mIconHeight;
    private int mIconHeightBottom;
    private int mIconHeightLeft;
    private int mIconHeightRight;
    private int mIconHeightTop;
    private Drawable mIconLeft;
    private Drawable mIconNormal;
    private Drawable mIconNormalBottom;
    private Drawable mIconNormalLeft;
    private Drawable mIconNormalRight;
    private Drawable mIconNormalTop;
    private Drawable mIconPressed;
    private Drawable mIconPressedBottom;
    private Drawable mIconPressedLeft;
    private Drawable mIconPressedRight;
    private Drawable mIconPressedTop;
    private Drawable mIconRight;
    private Drawable mIconSelected;
    private Drawable mIconSelectedBottom;
    private Drawable mIconSelectedLeft;
    private Drawable mIconSelectedRight;
    private Drawable mIconSelectedTop;
    private Drawable mIconTop;
    private Drawable mIconUnable;
    private Drawable mIconUnableBottom;
    private Drawable mIconUnableLeft;
    private Drawable mIconUnableRight;
    private Drawable mIconUnableTop;
    private int mIconWidth;
    private int mIconWidthBottom;
    private int mIconWidthLeft;
    private int mIconWidthRight;
    private int mIconWidthTop;
    protected int mPaddingBottom;
    protected int mPaddingLeft;
    protected int mPaddingRight;
    protected int mPaddingTop;
    protected int mTextColorChecked;
    protected int mTextColorNormal;
    protected int mTextColorPressed;
    protected int mTextColorSelected;
    protected ColorStateList mTextColorStateList;
    protected int mTextColorUnable;
    private String mTypefacePath;
    protected int[][] states;

    public RTextViewHelper(Context context, TextView textView, AttributeSet attributeSet) {
        super(context, textView, attributeSet);
        this.mIcon = null;
        this.mIconLeft = null;
        this.mIconTop = null;
        this.mIconBottom = null;
        this.mIconRight = null;
        this.mTextColorNormal = 0;
        this.mTextColorPressed = 0;
        this.mTextColorUnable = 0;
        this.mTextColorSelected = 0;
        this.mTextColorChecked = 0;
        this.states = new int[6][];
        this.mDrawableWithText = false;
        this.mHasPressedTextColor = false;
        this.mHasUnableTextColor = false;
        this.mHasSelectedTextColor = false;
        this.mHasCheckedTextColor = false;
        initAttributeSet(context, attributeSet);
    }

    private Drawable getDrawable(Context context, TypedArray typedArray, @StyleableRes int i2) {
        return typedArray.getDrawable(i2);
    }

    @SuppressLint({"NewApi"})
    private void initAttributeSet(Context context, AttributeSet attributeSet) {
        if (context == null || attributeSet == null) {
            setup();
            return;
        }
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.RTextView);
        this.mIconNormalLeft = getDrawable(context, typedArrayObtainStyledAttributes, R.styleable.RTextView_icon_normal_left);
        this.mIconPressedLeft = getDrawable(context, typedArrayObtainStyledAttributes, R.styleable.RTextView_icon_pressed_left);
        this.mIconUnableLeft = getDrawable(context, typedArrayObtainStyledAttributes, R.styleable.RTextView_icon_unable_left);
        this.mIconSelectedLeft = getDrawable(context, typedArrayObtainStyledAttributes, R.styleable.RTextView_icon_selected_left);
        this.mIconCheckedLeft = getDrawable(context, typedArrayObtainStyledAttributes, R.styleable.RTextView_icon_checked_left);
        this.mIconNormalRight = getDrawable(context, typedArrayObtainStyledAttributes, R.styleable.RTextView_icon_normal_right);
        this.mIconPressedRight = getDrawable(context, typedArrayObtainStyledAttributes, R.styleable.RTextView_icon_pressed_right);
        this.mIconUnableRight = getDrawable(context, typedArrayObtainStyledAttributes, R.styleable.RTextView_icon_unable_right);
        this.mIconSelectedRight = getDrawable(context, typedArrayObtainStyledAttributes, R.styleable.RTextView_icon_selected_right);
        this.mIconCheckedRight = getDrawable(context, typedArrayObtainStyledAttributes, R.styleable.RTextView_icon_checked_right);
        this.mIconNormalTop = getDrawable(context, typedArrayObtainStyledAttributes, R.styleable.RTextView_icon_normal_top);
        this.mIconPressedTop = getDrawable(context, typedArrayObtainStyledAttributes, R.styleable.RTextView_icon_pressed_top);
        this.mIconUnableTop = getDrawable(context, typedArrayObtainStyledAttributes, R.styleable.RTextView_icon_unable_top);
        this.mIconSelectedTop = getDrawable(context, typedArrayObtainStyledAttributes, R.styleable.RTextView_icon_selected_top);
        this.mIconCheckedTop = getDrawable(context, typedArrayObtainStyledAttributes, R.styleable.RTextView_icon_checked_top);
        this.mIconNormalBottom = getDrawable(context, typedArrayObtainStyledAttributes, R.styleable.RTextView_icon_normal_bottom);
        this.mIconPressedBottom = getDrawable(context, typedArrayObtainStyledAttributes, R.styleable.RTextView_icon_pressed_bottom);
        this.mIconUnableBottom = getDrawable(context, typedArrayObtainStyledAttributes, R.styleable.RTextView_icon_unable_bottom);
        this.mIconSelectedBottom = getDrawable(context, typedArrayObtainStyledAttributes, R.styleable.RTextView_icon_selected_bottom);
        this.mIconCheckedBottom = getDrawable(context, typedArrayObtainStyledAttributes, R.styleable.RTextView_icon_checked_bottom);
        Drawable drawable = getDrawable(context, typedArrayObtainStyledAttributes, R.styleable.RTextView_android_drawableLeft);
        Drawable drawable2 = getDrawable(context, typedArrayObtainStyledAttributes, R.styleable.RTextView_android_drawableRight);
        Drawable drawable3 = getDrawable(context, typedArrayObtainStyledAttributes, R.styleable.RTextView_android_drawableTop);
        Drawable drawable4 = getDrawable(context, typedArrayObtainStyledAttributes, R.styleable.RTextView_android_drawableBottom);
        Drawable drawable5 = getDrawable(context, typedArrayObtainStyledAttributes, R.styleable.RTextView_android_drawableStart);
        Drawable drawable6 = getDrawable(context, typedArrayObtainStyledAttributes, R.styleable.RTextView_android_drawableEnd);
        this.mIconNormal = getDrawable(context, typedArrayObtainStyledAttributes, R.styleable.RTextView_icon_src_normal);
        this.mIconPressed = getDrawable(context, typedArrayObtainStyledAttributes, R.styleable.RTextView_icon_src_pressed);
        this.mIconUnable = getDrawable(context, typedArrayObtainStyledAttributes, R.styleable.RTextView_icon_src_unable);
        this.mIconSelected = getDrawable(context, typedArrayObtainStyledAttributes, R.styleable.RTextView_icon_src_selected);
        this.mIconChecked = getDrawable(context, typedArrayObtainStyledAttributes, R.styleable.RTextView_icon_src_checked);
        if (RBaseHelper.isRtl()) {
            if (drawable6 != null) {
                drawable = drawable6;
            }
            if (drawable5 != null) {
                drawable2 = drawable5;
            }
        } else {
            if (drawable5 != null) {
                drawable = drawable5;
            }
            if (drawable6 != null) {
                drawable2 = drawable6;
            }
        }
        if (drawable != null) {
            this.mIconNormalLeft = drawable;
        }
        if (drawable2 != null) {
            this.mIconNormalRight = drawable2;
        }
        if (drawable3 != null) {
            this.mIconNormalTop = drawable3;
        }
        if (drawable4 != null) {
            this.mIconNormalBottom = drawable4;
        }
        this.mIconWidthLeft = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.RTextView_icon_width_left, 0);
        this.mIconHeightLeft = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.RTextView_icon_height_left, 0);
        this.mIconWidthRight = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.RTextView_icon_width_right, 0);
        this.mIconHeightRight = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.RTextView_icon_height_right, 0);
        this.mIconWidthBottom = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.RTextView_icon_width_bottom, 0);
        this.mIconHeightBottom = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.RTextView_icon_height_bottom, 0);
        this.mIconWidthTop = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.RTextView_icon_width_top, 0);
        this.mIconHeightTop = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.RTextView_icon_height_top, 0);
        this.mIconWidth = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.RTextView_icon_width, 0);
        this.mIconHeight = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.RTextView_icon_height, 0);
        this.mIconDirection = typedArrayObtainStyledAttributes.getInt(R.styleable.RTextView_icon_direction, 1);
        this.mTextColorNormal = typedArrayObtainStyledAttributes.getColor(R.styleable.RTextView_text_color_normal, ((TextView) this.mView).getCurrentTextColor());
        this.mTextColorPressed = typedArrayObtainStyledAttributes.getColor(R.styleable.RTextView_text_color_pressed, 0);
        this.mTextColorUnable = typedArrayObtainStyledAttributes.getColor(R.styleable.RTextView_text_color_unable, 0);
        this.mTextColorSelected = typedArrayObtainStyledAttributes.getColor(R.styleable.RTextView_text_color_selected, 0);
        this.mTextColorChecked = typedArrayObtainStyledAttributes.getColor(R.styleable.RTextView_text_color_checked, 0);
        this.mTypefacePath = typedArrayObtainStyledAttributes.getString(R.styleable.RTextView_text_typeface);
        this.mDrawableWithText = typedArrayObtainStyledAttributes.getBoolean(R.styleable.RTextView_icon_with_text, false);
        typedArrayObtainStyledAttributes.recycle();
        setup();
    }

    private void setCompoundDrawables(Drawable drawable, Drawable drawable2, Drawable drawable3, Drawable drawable4) {
        if (drawable != null) {
            drawable.setBounds(0, 0, this.mIconWidthLeft, this.mIconHeightLeft);
        }
        if (drawable2 != null) {
            drawable2.setBounds(0, 0, this.mIconWidthRight, this.mIconHeightRight);
        }
        if (drawable3 != null) {
            drawable3.setBounds(0, 0, this.mIconWidthTop, this.mIconHeightTop);
        }
        if (drawable4 != null) {
            drawable4.setBounds(0, 0, this.mIconWidthBottom, this.mIconHeightBottom);
        }
        boolean zIsRtl = RBaseHelper.isRtl();
        TextView textView = (TextView) this.mView;
        Drawable drawable5 = zIsRtl ? drawable2 : drawable;
        if (!zIsRtl) {
            drawable = drawable2;
        }
        textView.setCompoundDrawables(drawable5, drawable3, drawable, drawable4);
    }

    private void setMultipleIconWithText() {
        T t2;
        if (!this.mDrawableWithText || (t2 = this.mView) == 0 || ((TextView) t2).getWidth() == 0) {
            return;
        }
        int compoundDrawablePadding = ((TextView) this.mView).getCompoundDrawablePadding();
        int i2 = this.mIconLeft != null ? compoundDrawablePadding + 0 : 0;
        if (this.mIconRight != null) {
            i2 += compoundDrawablePadding;
        }
        int i3 = this.mIconTop != null ? compoundDrawablePadding + 0 : 0;
        if (this.mIconBottom != null) {
            i3 += compoundDrawablePadding;
        }
        int i4 = i3;
        int i5 = this.mIconWidthLeft + this.mIconWidthRight;
        int i6 = this.mIconHeightTop + this.mIconHeightBottom;
        int width = ((int) ((((TextView) this.mView).getWidth() - (this.mPaddingLeft + this.mPaddingRight)) - ((TextViewUtils.get().getTextWidth((TextView) this.mView, i5, this.mPaddingLeft, this.mPaddingRight, i2) + i5) + i2))) / 2;
        if (width < 0) {
            width = 0;
        }
        int height = ((int) ((((TextView) this.mView).getHeight() - (this.mPaddingTop + this.mPaddingBottom)) - ((Math.max(TextViewUtils.get().getTextHeight((TextView) this.mView, i6, this.mPaddingTop, this.mPaddingBottom, i4), Math.max(this.mIconHeightLeft, this.mIconHeightRight)) + i6) + i4))) / 2;
        int i7 = height >= 0 ? height : 0;
        StringBuilder sb = new StringBuilder();
        sb.append(((TextView) this.mView).getWidth());
        sb.append(((TextView) this.mView).getHeight());
        sb.append(width);
        sb.append(this.mPaddingLeft);
        sb.append(i7);
        sb.append(this.mPaddingTop);
        sb.append(width);
        sb.append(this.mPaddingRight);
        sb.append(i7);
        sb.append(this.mPaddingBottom);
        String string = sb.toString();
        if (string.equals(this.mCacheMultipleIconPaddingVale)) {
            return;
        }
        this.mCacheMultipleIconPaddingVale = string;
        ((TextView) this.mView).setPadding(this.mPaddingLeft + width, this.mPaddingTop + i7, width + this.mPaddingRight, i7 + this.mPaddingBottom);
    }

    @Deprecated
    private void setSingleCompoundDrawable(Drawable drawable, int i2, int i3, int i4) {
        if (drawable != null) {
            drawable.setBounds(0, 0, i2, i3);
        }
        if (RBaseHelper.isRtl()) {
            TextView textView = (TextView) this.mView;
            Drawable drawable2 = i4 == 3 ? drawable : null;
            Drawable drawable3 = i4 == 2 ? drawable : null;
            Drawable drawable4 = i4 == 1 ? drawable : null;
            if (i4 != 4) {
                drawable = null;
            }
            textView.setCompoundDrawables(drawable2, drawable3, drawable4, drawable);
            return;
        }
        TextView textView2 = (TextView) this.mView;
        Drawable drawable5 = i4 == 1 ? drawable : null;
        Drawable drawable6 = i4 == 2 ? drawable : null;
        Drawable drawable7 = i4 == 3 ? drawable : null;
        if (i4 != 4) {
            drawable = null;
        }
        textView2.setCompoundDrawables(drawable5, drawable6, drawable7, drawable);
    }

    private void setSingleIconWithText() {
        T t2;
        int i2;
        if (!this.mDrawableWithText || (t2 = this.mView) == 0 || ((TextView) t2).getWidth() == 0) {
            return;
        }
        int compoundDrawablePadding = ((TextView) this.mView).getCompoundDrawablePadding();
        int i3 = this.mIconWidth;
        int i4 = this.mIconHeight;
        int i5 = this.mIconDirection;
        if (i5 == 1 || i5 == 3) {
            i4 = 0;
            i2 = 0;
        } else {
            i2 = compoundDrawablePadding;
        }
        if (i5 == 2 || i5 == 4) {
            compoundDrawablePadding = 0;
            i3 = 0;
        }
        int width = ((int) ((((TextView) this.mView).getWidth() - (this.mPaddingLeft + this.mPaddingRight)) - ((TextViewUtils.get().getTextWidth((TextView) this.mView, i3, this.mPaddingLeft, this.mPaddingRight, compoundDrawablePadding) + i3) + compoundDrawablePadding))) / 2;
        if (width < 0) {
            width = 0;
        }
        int height = ((int) ((((TextView) this.mView).getHeight() - (this.mPaddingTop + this.mPaddingBottom)) - ((Math.max(TextViewUtils.get().getTextHeight((TextView) this.mView, i4, this.mPaddingTop, this.mPaddingBottom, i2), Math.max(this.mIconHeightLeft, this.mIconHeightRight)) + i4) + i2))) / 2;
        int i6 = height >= 0 ? height : 0;
        StringBuilder sb = new StringBuilder();
        sb.append(((TextView) this.mView).getWidth());
        sb.append(((TextView) this.mView).getHeight());
        sb.append(width);
        sb.append(this.mPaddingLeft);
        sb.append(i6);
        sb.append(this.mPaddingTop);
        sb.append(width);
        sb.append(this.mPaddingRight);
        sb.append(i6);
        sb.append(this.mPaddingBottom);
        String string = sb.toString();
        if (string.equals(this.mCacheSingleIconPaddingVale)) {
            return;
        }
        this.mCacheSingleIconPaddingVale = string;
        ((TextView) this.mView).setPadding(this.mPaddingLeft + width, this.mPaddingTop + i6, width + this.mPaddingRight, i6 + this.mPaddingBottom);
    }

    private void setup() {
        if (!((TextView) this.mView).isEnabled()) {
            this.mIcon = this.mIconUnable;
            this.mIconLeft = this.mIconUnableLeft;
            this.mIconRight = this.mIconUnableRight;
            this.mIconTop = this.mIconUnableTop;
            this.mIconBottom = this.mIconUnableBottom;
        } else if (((TextView) this.mView).isSelected()) {
            this.mIcon = this.mIconSelected;
            this.mIconLeft = this.mIconSelectedLeft;
            this.mIconRight = this.mIconSelectedRight;
            this.mIconTop = this.mIconSelectedTop;
            this.mIconBottom = this.mIconSelectedBottom;
        } else if (isCompoundButtonChecked()) {
            this.mIcon = this.mIconChecked;
            this.mIconLeft = this.mIconCheckedLeft;
            this.mIconRight = this.mIconCheckedRight;
            this.mIconTop = this.mIconCheckedTop;
            this.mIconBottom = this.mIconCheckedBottom;
        } else {
            this.mIcon = this.mIconNormal;
            this.mIconLeft = this.mIconNormalLeft;
            this.mIconRight = this.mIconNormalRight;
            this.mIconTop = this.mIconNormalTop;
            this.mIconBottom = this.mIconNormalBottom;
        }
        int[][] iArr = this.states;
        iArr[0] = new int[]{-16842910};
        iArr[1] = new int[]{android.R.attr.state_focused};
        iArr[2] = new int[]{android.R.attr.state_pressed};
        iArr[3] = new int[]{android.R.attr.state_checked};
        iArr[4] = new int[]{android.R.attr.state_selected};
        iArr[5] = new int[]{android.R.attr.state_enabled};
        setupDefaultValue(true);
        setTextColor();
        setIcon();
        setTypeface();
    }

    private void setupDefaultValue(boolean z2) {
        if (z2) {
            this.mHasPressedTextColor = this.mTextColorPressed != 0;
            this.mHasUnableTextColor = this.mTextColorUnable != 0;
            this.mHasSelectedTextColor = this.mTextColorSelected != 0;
            this.mHasCheckedTextColor = this.mTextColorChecked != 0;
        }
        if (!this.mHasPressedTextColor) {
            this.mTextColorPressed = this.mTextColorNormal;
        }
        if (!this.mHasUnableTextColor) {
            this.mTextColorUnable = this.mTextColorNormal;
        }
        if (!this.mHasSelectedTextColor) {
            this.mTextColorSelected = this.mTextColorNormal;
        }
        if (this.mHasCheckedTextColor) {
            return;
        }
        this.mTextColorChecked = this.mTextColorNormal;
    }

    private void updateTextColor() {
        setupDefaultValue(false);
        setTextColor();
    }

    @Override // com.ruffian.library.widget.iface.ITextViewFeature
    public void drawIconWithText() {
        if (isSingleDirection()) {
            setSingleIconWithText();
        } else {
            setMultipleIconWithText();
        }
    }

    @Deprecated
    public Drawable getIconChecked() {
        return this.mIconChecked;
    }

    public Drawable getIconCheckedBottom() {
        return this.mIconCheckedBottom;
    }

    public Drawable getIconCheckedLeft() {
        return this.mIconCheckedLeft;
    }

    public Drawable getIconCheckedRight() {
        return this.mIconCheckedRight;
    }

    public Drawable getIconCheckedTop() {
        return this.mIconCheckedTop;
    }

    @Deprecated
    public int getIconDirection() {
        return this.mIconDirection;
    }

    @Deprecated
    public int getIconHeight() {
        return this.mIconHeight;
    }

    public int getIconHeightBottom() {
        return this.mIconHeightBottom;
    }

    public int getIconHeightLeft() {
        return this.mIconHeightLeft;
    }

    public int getIconHeightRight() {
        return this.mIconHeightRight;
    }

    public int getIconHeightTop() {
        return this.mIconHeightTop;
    }

    @Deprecated
    public Drawable getIconNormal() {
        return this.mIconNormal;
    }

    public Drawable getIconNormalBottom() {
        return this.mIconNormalBottom;
    }

    public Drawable getIconNormalLeft() {
        return this.mIconNormalLeft;
    }

    public Drawable getIconNormalRight() {
        return this.mIconNormalRight;
    }

    public Drawable getIconNormalTop() {
        return this.mIconNormalTop;
    }

    @Deprecated
    public Drawable getIconPressed() {
        return this.mIconPressed;
    }

    public Drawable getIconPressedBottom() {
        return this.mIconPressedBottom;
    }

    public Drawable getIconPressedLeft() {
        return this.mIconPressedLeft;
    }

    public Drawable getIconPressedRight() {
        return this.mIconPressedRight;
    }

    public Drawable getIconPressedTop() {
        return this.mIconPressedTop;
    }

    @Deprecated
    public Drawable getIconSelected() {
        return this.mIconSelected;
    }

    public Drawable getIconSelectedBottom() {
        return this.mIconSelectedBottom;
    }

    public Drawable getIconSelectedLeft() {
        return this.mIconSelectedLeft;
    }

    public Drawable getIconSelectedRight() {
        return this.mIconSelectedRight;
    }

    public Drawable getIconSelectedTop() {
        return this.mIconSelectedTop;
    }

    @Deprecated
    public Drawable getIconUnable() {
        return this.mIconUnable;
    }

    public Drawable getIconUnableBottom() {
        return this.mIconUnableBottom;
    }

    public Drawable getIconUnableLeft() {
        return this.mIconUnableLeft;
    }

    public Drawable getIconUnableRight() {
        return this.mIconUnableRight;
    }

    public Drawable getIconUnableTop() {
        return this.mIconUnableTop;
    }

    @Deprecated
    public int getIconWidth() {
        return this.mIconWidth;
    }

    public int getIconWidthBottom() {
        return this.mIconWidthBottom;
    }

    public int getIconWidthLeft() {
        return this.mIconWidthLeft;
    }

    public int getIconWidthRight() {
        return this.mIconWidthRight;
    }

    public int getIconWidthTop() {
        return this.mIconWidthTop;
    }

    public int getTextColorChecked() {
        return this.mTextColorChecked;
    }

    public int getTextColorNormal() {
        return this.mTextColorNormal;
    }

    public int getTextColorPressed() {
        return this.mTextColorPressed;
    }

    public int getTextColorSelected() {
        return this.mTextColorSelected;
    }

    public int getTextColorUnable() {
        return this.mTextColorUnable;
    }

    public String getTypefacePath() {
        return this.mTypefacePath;
    }

    public boolean isCompoundButtonChecked() {
        return false;
    }

    public boolean isSingleDirection() {
        return (this.mIconNormal == null && this.mIconPressed == null && this.mIconUnable == null && this.mIconSelected == null && this.mIconChecked == null) ? false : true;
    }

    @Override // com.ruffian.library.widget.helper.RBaseHelper, android.view.ViewTreeObserver.OnGlobalLayoutListener
    public void onGlobalLayout() throws SecurityException {
        super.onGlobalLayout();
        this.mPaddingLeft = ((TextView) this.mView).getPaddingLeft();
        this.mPaddingRight = ((TextView) this.mView).getPaddingRight();
        this.mPaddingTop = ((TextView) this.mView).getPaddingTop();
        this.mPaddingBottom = ((TextView) this.mView).getPaddingBottom();
    }

    @Override // com.ruffian.library.widget.iface.ITextViewFeature
    public void onTouchEvent(MotionEvent motionEvent) {
        if (!((TextView) this.mView).isEnabled() || isCompoundButtonChecked() || ((TextView) this.mView).isSelected()) {
            return;
        }
        int action = motionEvent.getAction();
        if (action == 0) {
            Drawable drawable = this.mIconPressedLeft;
            if (drawable != null) {
                this.mIconLeft = drawable;
            }
            Drawable drawable2 = this.mIconPressedRight;
            if (drawable2 != null) {
                this.mIconRight = drawable2;
            }
            Drawable drawable3 = this.mIconPressedTop;
            if (drawable3 != null) {
                this.mIconTop = drawable3;
            }
            Drawable drawable4 = this.mIconPressedBottom;
            if (drawable4 != null) {
                this.mIconBottom = drawable4;
            }
            Drawable drawable5 = this.mIconPressed;
            if (drawable5 != null) {
                this.mIcon = drawable5;
            }
            setIcon();
            return;
        }
        if (action != 1) {
            if (action == 2) {
                if (isOutsideView((int) motionEvent.getX(), (int) motionEvent.getY())) {
                    this.mIconLeft = this.mIconNormalLeft;
                    this.mIconRight = this.mIconNormalRight;
                    this.mIconTop = this.mIconNormalTop;
                    this.mIconBottom = this.mIconNormalBottom;
                    this.mIcon = this.mIconNormal;
                    setIcon();
                    return;
                }
                return;
            }
            if (action != 3) {
                return;
            }
        }
        this.mIconLeft = this.mIconNormalLeft;
        this.mIconRight = this.mIconNormalRight;
        this.mIconTop = this.mIconNormalTop;
        this.mIconBottom = this.mIconNormalBottom;
        this.mIcon = this.mIconNormal;
        setIcon();
    }

    public void setChecked(boolean z2) {
        setIconLeft(z2 ? this.mIconCheckedLeft : getIconNormalLeft());
        setIconRight(z2 ? this.mIconCheckedRight : getIconNormalRight());
        setIconTop(z2 ? this.mIconCheckedTop : getIconNormalTop());
        setIconBottom(z2 ? this.mIconCheckedBottom : getIconNormalBottom());
        setIcon(z2 ? this.mIconChecked : getIconNormal());
    }

    @Override // com.ruffian.library.widget.iface.ITextViewFeature
    public void setEnabled(boolean z2) {
        Drawable drawable;
        Drawable drawable2;
        Drawable drawable3;
        Drawable drawable4;
        Drawable drawable5;
        if (z2 || (drawable = this.mIconUnableLeft) == null) {
            drawable = this.mIconNormalLeft;
        }
        this.mIconLeft = drawable;
        if (z2 || (drawable2 = this.mIconUnableRight) == null) {
            drawable2 = this.mIconNormalRight;
        }
        this.mIconRight = drawable2;
        if (z2 || (drawable3 = this.mIconUnableTop) == null) {
            drawable3 = this.mIconNormalTop;
        }
        this.mIconTop = drawable3;
        if (z2 || (drawable4 = this.mIconUnableBottom) == null) {
            drawable4 = this.mIconNormalBottom;
        }
        this.mIconBottom = drawable4;
        if (z2 || (drawable5 = this.mIconUnable) == null) {
            drawable5 = this.mIconNormal;
        }
        this.mIcon = drawable5;
        setIcon();
    }

    @Deprecated
    public void setIcon(Drawable drawable) {
        this.mIcon = drawable;
        setIcon();
    }

    public void setIconBottom(Drawable drawable) {
        this.mIconBottom = drawable;
        setIcon();
    }

    @Deprecated
    public RTextViewHelper setIconChecked(Drawable drawable) {
        this.mIconChecked = drawable;
        this.mIcon = drawable;
        setIcon(drawable);
        return this;
    }

    public RTextViewHelper setIconCheckedBottom(Drawable drawable) {
        this.mIconCheckedBottom = drawable;
        setIconBottom(drawable);
        return this;
    }

    public RTextViewHelper setIconCheckedLeft(Drawable drawable) {
        this.mIconCheckedLeft = drawable;
        setIconLeft(drawable);
        return this;
    }

    public RTextViewHelper setIconCheckedRight(Drawable drawable) {
        this.mIconCheckedRight = drawable;
        setIconRight(drawable);
        return this;
    }

    public RTextViewHelper setIconCheckedTop(Drawable drawable) {
        this.mIconCheckedTop = drawable;
        setIconTop(drawable);
        return this;
    }

    @Deprecated
    public RTextViewHelper setIconDirection(int i2) {
        this.mIconDirection = i2;
        setIcon();
        return this;
    }

    @Deprecated
    public RTextViewHelper setIconHeight(int i2) {
        this.mIconHeight = i2;
        setIcon();
        return this;
    }

    public void setIconLeft(Drawable drawable) {
        this.mIconLeft = drawable;
        setIcon();
    }

    @Deprecated
    public RTextViewHelper setIconNormal(Drawable drawable) {
        this.mIconNormal = drawable;
        this.mIcon = drawable;
        setIcon();
        return this;
    }

    public RTextViewHelper setIconNormalBottom(Drawable drawable) {
        this.mIconNormalBottom = drawable;
        this.mIconBottom = drawable;
        setIcon();
        return this;
    }

    public RTextViewHelper setIconNormalLeft(Drawable drawable) {
        this.mIconNormalLeft = drawable;
        this.mIconLeft = drawable;
        setIcon();
        return this;
    }

    public RTextViewHelper setIconNormalRight(Drawable drawable) {
        this.mIconNormalRight = drawable;
        this.mIconRight = drawable;
        setIcon();
        return this;
    }

    public RTextViewHelper setIconNormalTop(Drawable drawable) {
        this.mIconNormalTop = drawable;
        this.mIconTop = drawable;
        setIcon();
        return this;
    }

    @Deprecated
    public RTextViewHelper setIconPressed(Drawable drawable) {
        this.mIconPressed = drawable;
        this.mIcon = drawable;
        setIcon();
        return this;
    }

    public RTextViewHelper setIconPressedBottom(Drawable drawable) {
        this.mIconPressedBottom = drawable;
        this.mIconBottom = drawable;
        setIcon();
        return this;
    }

    public RTextViewHelper setIconPressedLeft(Drawable drawable) {
        this.mIconPressedLeft = drawable;
        this.mIconLeft = drawable;
        setIcon();
        return this;
    }

    public RTextViewHelper setIconPressedRight(Drawable drawable) {
        this.mIconPressedRight = drawable;
        this.mIconRight = drawable;
        setIcon();
        return this;
    }

    public RTextViewHelper setIconPressedTop(Drawable drawable) {
        this.mIconPressedTop = drawable;
        this.mIconTop = drawable;
        setIcon();
        return this;
    }

    public void setIconRight(Drawable drawable) {
        this.mIconRight = drawable;
        setIcon();
    }

    @Deprecated
    public RTextViewHelper setIconSelected(Drawable drawable) {
        this.mIconSelected = drawable;
        this.mIcon = drawable;
        setIcon();
        return this;
    }

    public RTextViewHelper setIconSelectedBottom(Drawable drawable) {
        this.mIconSelectedBottom = drawable;
        this.mIconBottom = drawable;
        setIcon();
        return this;
    }

    public RTextViewHelper setIconSelectedLeft(Drawable drawable) {
        this.mIconSelectedLeft = drawable;
        this.mIconLeft = drawable;
        setIcon();
        return this;
    }

    public RTextViewHelper setIconSelectedRight(Drawable drawable) {
        this.mIconSelectedRight = drawable;
        this.mIconRight = drawable;
        setIcon();
        return this;
    }

    public RTextViewHelper setIconSelectedTop(Drawable drawable) {
        this.mIconSelectedTop = drawable;
        this.mIconTop = drawable;
        setIcon();
        return this;
    }

    @Deprecated
    public RTextViewHelper setIconSize(int i2, int i3) {
        this.mIconWidth = i2;
        this.mIconHeight = i3;
        setIcon();
        return this;
    }

    public RTextViewHelper setIconSizeBottom(int i2, int i3) {
        this.mIconWidthBottom = i2;
        this.mIconHeightBottom = i3;
        setIcon();
        return this;
    }

    public RTextViewHelper setIconSizeLeft(int i2, int i3) {
        this.mIconWidthLeft = i2;
        this.mIconHeightLeft = i3;
        setIcon();
        return this;
    }

    public RTextViewHelper setIconSizeRight(int i2, int i3) {
        this.mIconWidthRight = i2;
        this.mIconHeightRight = i3;
        setIcon();
        return this;
    }

    public RTextViewHelper setIconSizeTop(int i2, int i3) {
        this.mIconWidthTop = i2;
        this.mIconHeightTop = i3;
        setIcon();
        return this;
    }

    public void setIconTop(Drawable drawable) {
        this.mIconTop = drawable;
        setIcon();
    }

    @Deprecated
    public RTextViewHelper setIconUnable(Drawable drawable) {
        this.mIconUnable = drawable;
        this.mIcon = drawable;
        setIcon();
        return this;
    }

    public RTextViewHelper setIconUnableBottom(Drawable drawable) {
        this.mIconUnableBottom = drawable;
        this.mIconBottom = drawable;
        setIcon();
        return this;
    }

    public RTextViewHelper setIconUnableLeft(Drawable drawable) {
        this.mIconUnableLeft = drawable;
        this.mIconLeft = drawable;
        setIcon();
        return this;
    }

    public RTextViewHelper setIconUnableRight(Drawable drawable) {
        this.mIconUnableRight = drawable;
        this.mIconRight = drawable;
        setIcon();
        return this;
    }

    public RTextViewHelper setIconUnableTop(Drawable drawable) {
        this.mIconUnableTop = drawable;
        this.mIconTop = drawable;
        setIcon();
        return this;
    }

    @Deprecated
    public RTextViewHelper setIconWidth(int i2) {
        this.mIconWidth = i2;
        setIcon();
        return this;
    }

    @Override // com.ruffian.library.widget.iface.ITextViewFeature
    public void setSelected(boolean z2) {
        Drawable drawable;
        Drawable drawable2;
        Drawable drawable3;
        Drawable drawable4;
        Drawable drawable5;
        if (((TextView) this.mView).isEnabled()) {
            if (!z2 || (drawable = this.mIconSelectedLeft) == null) {
                drawable = this.mIconNormalLeft;
            }
            this.mIconLeft = drawable;
            if (!z2 || (drawable2 = this.mIconSelectedRight) == null) {
                drawable2 = this.mIconNormalRight;
            }
            this.mIconRight = drawable2;
            if (!z2 || (drawable3 = this.mIconSelectedTop) == null) {
                drawable3 = this.mIconNormalTop;
            }
            this.mIconTop = drawable3;
            if (!z2 || (drawable4 = this.mIconSelectedBottom) == null) {
                drawable4 = this.mIconNormalBottom;
            }
            this.mIconBottom = drawable4;
            if (!z2 || (drawable5 = this.mIconSelected) == null) {
                drawable5 = this.mIconNormal;
            }
            this.mIcon = drawable5;
            setIcon();
        }
    }

    public RTextViewHelper setTextColor(@ColorInt int i2, @ColorInt int i3, @ColorInt int i4, @ColorInt int i5, @ColorInt int i6) {
        this.mTextColorNormal = i2;
        this.mTextColorPressed = i3;
        this.mTextColorUnable = i4;
        this.mTextColorSelected = i5;
        this.mTextColorChecked = i6;
        this.mHasPressedTextColor = true;
        this.mHasUnableTextColor = true;
        this.mHasSelectedTextColor = true;
        this.mHasCheckedTextColor = true;
        updateTextColor();
        return this;
    }

    public RTextViewHelper setTextColorChecked(@ColorInt int i2) {
        this.mTextColorChecked = i2;
        this.mHasCheckedTextColor = true;
        updateTextColor();
        return this;
    }

    public RTextViewHelper setTextColorNormal(@ColorInt int i2) {
        this.mTextColorNormal = i2;
        updateTextColor();
        return this;
    }

    public RTextViewHelper setTextColorPressed(@ColorInt int i2) {
        this.mTextColorPressed = i2;
        this.mHasPressedTextColor = true;
        updateTextColor();
        return this;
    }

    public RTextViewHelper setTextColorSelected(@ColorInt int i2) {
        this.mTextColorSelected = i2;
        this.mHasSelectedTextColor = true;
        updateTextColor();
        return this;
    }

    public RTextViewHelper setTextColorUnable(@ColorInt int i2) {
        this.mTextColorUnable = i2;
        this.mHasUnableTextColor = true;
        updateTextColor();
        return this;
    }

    public RTextViewHelper setTypeface(String str) {
        this.mTypefacePath = str;
        setTypeface();
        return this;
    }

    private void setIcon() {
        Drawable drawable;
        Drawable drawable2;
        Drawable drawable3;
        Drawable drawable4;
        Drawable drawable5;
        if (this.mIconHeightLeft == 0 && this.mIconWidthLeft == 0 && (drawable5 = this.mIconLeft) != null) {
            this.mIconWidthLeft = drawable5.getIntrinsicWidth();
            this.mIconHeightLeft = this.mIconLeft.getIntrinsicHeight();
        }
        if (this.mIconHeightRight == 0 && this.mIconWidthRight == 0 && (drawable4 = this.mIconRight) != null) {
            this.mIconWidthRight = drawable4.getIntrinsicWidth();
            this.mIconHeightRight = this.mIconRight.getIntrinsicHeight();
        }
        if (this.mIconHeightTop == 0 && this.mIconWidthTop == 0 && (drawable3 = this.mIconTop) != null) {
            this.mIconWidthTop = drawable3.getIntrinsicWidth();
            this.mIconHeightTop = this.mIconTop.getIntrinsicHeight();
        }
        if (this.mIconHeightBottom == 0 && this.mIconWidthBottom == 0 && (drawable2 = this.mIconBottom) != null) {
            this.mIconWidthBottom = drawable2.getIntrinsicWidth();
            this.mIconHeightBottom = this.mIconBottom.getIntrinsicHeight();
        }
        if (this.mIconHeight == 0 && this.mIconWidth == 0 && (drawable = this.mIcon) != null) {
            this.mIconWidth = drawable.getIntrinsicWidth();
            this.mIconHeight = this.mIcon.getIntrinsicHeight();
        }
        if (isSingleDirection()) {
            setSingleCompoundDrawable(this.mIcon, this.mIconWidth, this.mIconHeight, this.mIconDirection);
        } else {
            setCompoundDrawables(this.mIconLeft, this.mIconRight, this.mIconTop, this.mIconBottom);
        }
    }

    private void setTypeface() {
        if (TextUtils.isEmpty(this.mTypefacePath)) {
            return;
        }
        ((TextView) this.mView).setTypeface(Typeface.createFromAsset(this.mContext.getAssets(), this.mTypefacePath));
    }

    public void setTextColor() {
        int i2 = this.mTextColorPressed;
        ColorStateList colorStateList = new ColorStateList(this.states, new int[]{this.mTextColorUnable, i2, i2, this.mTextColorChecked, this.mTextColorSelected, this.mTextColorNormal});
        this.mTextColorStateList = colorStateList;
        ((TextView) this.mView).setTextColor(colorStateList);
    }
}
