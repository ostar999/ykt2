package com.hyphenate.easeui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import com.hyphenate.easeui.R;

/* loaded from: classes4.dex */
public class EaseSearchTextView extends AppCompatTextView {
    private int DEFAULT_DRAWABLE_PADDING;
    private float DEFAULT_SIZE;
    private Context mContext;
    private float mLeftHeight;
    private float mLeftWidth;
    private float mRightHeight;
    private float mRightWidth;

    public EaseSearchTextView(Context context) {
        this(context, null);
    }

    private float dip2px(float f2) {
        return TypedValue.applyDimension(1, f2, getContext().getResources().getDisplayMetrics());
    }

    private void init(Context context, AttributeSet attributeSet) {
        if (attributeSet != null) {
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.EaseSearchTextView);
            this.mLeftHeight = typedArrayObtainStyledAttributes.getDimension(R.styleable.EaseSearchTextView_search_drawable_left_height, this.DEFAULT_SIZE);
            this.mLeftWidth = typedArrayObtainStyledAttributes.getDimension(R.styleable.EaseSearchTextView_search_drawable_left_width, this.DEFAULT_SIZE);
            this.mRightHeight = typedArrayObtainStyledAttributes.getDimension(R.styleable.EaseSearchTextView_search_drawable_right_height, 0.0f);
            this.mRightWidth = typedArrayObtainStyledAttributes.getDimension(R.styleable.EaseSearchTextView_search_drawable_right_width, 0.0f);
            typedArrayObtainStyledAttributes.recycle();
        }
        setGravity(16);
        if (TextUtils.isEmpty(getHint())) {
            setHint(getResources().getString(R.string.ease_search_text_hint));
        }
        setDrawable();
    }

    private void setDrawable() {
        Drawable[] compoundDrawablesRelative = getCompoundDrawablesRelative();
        Drawable drawable = compoundDrawablesRelative[0];
        if (drawable != null || compoundDrawablesRelative[2] != null) {
            setCompoundDrawablesRelativeWithIntrinsicBounds(drawable, compoundDrawablesRelative[1], compoundDrawablesRelative[2], compoundDrawablesRelative[3]);
            return;
        }
        Drawable[] compoundDrawables = getCompoundDrawables();
        Drawable drawable2 = compoundDrawables[0];
        Drawable drawable3 = compoundDrawables[2];
        if (drawable2 == null) {
            drawable2 = ContextCompat.getDrawable(this.mContext, R.drawable.ease_search_icon);
        }
        if (drawable2 != null) {
            float f2 = this.mLeftWidth;
            if (f2 != 0.0f) {
                float f3 = this.mLeftHeight;
                if (f3 != 0.0f) {
                    drawable2.setBounds(0, 0, (int) f2, (int) f3);
                }
            }
        }
        if (drawable3 != null) {
            float f4 = this.mRightWidth;
            if (f4 != 0.0f) {
                float f5 = this.mRightHeight;
                if (f5 != 0.0f) {
                    drawable3.setBounds(0, 0, (int) f4, (int) f5);
                }
            }
        }
        if (drawable2 == null) {
            drawable2 = compoundDrawables[0];
        }
        Drawable drawable4 = compoundDrawables[1];
        if (drawable3 == null) {
            drawable3 = compoundDrawables[2];
        }
        setCompoundDrawables(drawable2, drawable4, drawable3, compoundDrawables[3]);
        if (getBackground() == null) {
            setBackground(ContextCompat.getDrawable(this.mContext, R.drawable.ease_search_bg_shape));
        }
        if (getCompoundDrawablePadding() == 0) {
            setCompoundDrawablePadding(this.DEFAULT_DRAWABLE_PADDING);
        }
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        if (paddingLeft == 0 || paddingRight == 0) {
            setPadding((int) dip2px(16.0f), paddingTop, (int) dip2px(16.0f), paddingBottom);
        }
    }

    public EaseSearchTextView(Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public EaseSearchTextView(Context context, @Nullable AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.DEFAULT_SIZE = dip2px(18.0f);
        this.DEFAULT_DRAWABLE_PADDING = (int) dip2px(6.0f);
        this.mContext = context;
        init(context, attributeSet);
    }
}
