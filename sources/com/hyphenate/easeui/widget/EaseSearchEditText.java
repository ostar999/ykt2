package com.hyphenate.easeui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.EditText;
import com.hyphenate.easeui.R;

/* loaded from: classes4.dex */
public class EaseSearchEditText extends EditText {
    private float mLeftHeight;
    private float mLeftWidth;
    private float mRightHeight;
    private float mRightWidth;

    public EaseSearchEditText(Context context) {
        this(context, null);
    }

    private void init(Context context, AttributeSet attributeSet) {
        if (attributeSet != null) {
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.EaseSearchEditText);
            this.mLeftHeight = typedArrayObtainStyledAttributes.getDimension(R.styleable.EaseSearchEditText_search_edit_drawable_left_height, 0.0f);
            this.mLeftWidth = typedArrayObtainStyledAttributes.getDimension(R.styleable.EaseSearchEditText_search_edit_drawable_left_width, 0.0f);
            this.mRightHeight = typedArrayObtainStyledAttributes.getDimension(R.styleable.EaseSearchEditText_search_edit_drawable_right_height, 0.0f);
            this.mRightWidth = typedArrayObtainStyledAttributes.getDimension(R.styleable.EaseSearchEditText_search_edit_drawable_right_width, 0.0f);
            typedArrayObtainStyledAttributes.recycle();
        }
        requestFocus();
        setDrawables();
    }

    private void setDrawables() {
        Drawable[] compoundDrawables = getCompoundDrawables();
        Drawable drawable = compoundDrawables[0];
        Drawable drawable2 = compoundDrawables[2];
        if (drawable != null) {
            float f2 = this.mLeftWidth;
            if (f2 != 0.0f) {
                float f3 = this.mLeftHeight;
                if (f3 != 0.0f) {
                    drawable.setBounds(0, 0, (int) f2, (int) f3);
                }
            }
        }
        if (drawable2 != null) {
            drawable2.setBounds(0, 0, 0, 0);
        }
        if (drawable == null) {
            drawable = compoundDrawables[0];
        }
        Drawable drawable3 = compoundDrawables[1];
        if (drawable2 == null) {
            drawable2 = compoundDrawables[2];
        }
        setCompoundDrawables(drawable, drawable3, drawable2, compoundDrawables[3]);
    }

    public EaseSearchEditText(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public EaseSearchEditText(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        init(context, attributeSet);
    }
}
