package com.noober.background.drawable;

import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import androidx.annotation.AttrRes;
import com.noober.background.R;

/* loaded from: classes4.dex */
public class ButtonDrawableCreator implements ICreateDrawable {
    private TypedArray buttonTa;
    private TypedArray typedArray;

    public ButtonDrawableCreator(TypedArray typedArray, TypedArray typedArray2) {
        this.typedArray = typedArray;
        this.buttonTa = typedArray2;
    }

    private void setSelectorDrawable(TypedArray typedArray, TypedArray typedArray2, StateListDrawable stateListDrawable, int i2, @AttrRes int i3) throws Exception {
        int color;
        Drawable drawable;
        try {
            color = typedArray2.getColor(i2, 0);
            if (color == 0) {
                try {
                    drawable = typedArray2.getDrawable(i2);
                } catch (Exception unused) {
                    drawable = typedArray2.getDrawable(i2);
                    if (drawable == null) {
                    }
                    stateListDrawable.addState(new int[]{i3}, drawable);
                    return;
                }
            } else {
                drawable = null;
            }
        } catch (Exception unused2) {
            color = 0;
        }
        if (drawable == null || color == 0) {
            stateListDrawable.addState(new int[]{i3}, drawable);
            return;
        }
        GradientDrawable drawable2 = DrawableFactory.getDrawable(typedArray);
        drawable2.setColor(color);
        stateListDrawable.addState(new int[]{i3}, drawable2);
    }

    @Override // com.noober.background.drawable.ICreateDrawable
    public Drawable create() throws Exception {
        StateListDrawable stateListDrawable = new StateListDrawable();
        for (int i2 = 0; i2 < this.buttonTa.getIndexCount(); i2++) {
            int index = this.buttonTa.getIndex(i2);
            if (index == R.styleable.background_button_drawable_bl_checked_button_drawable) {
                setSelectorDrawable(this.typedArray, this.buttonTa, stateListDrawable, index, android.R.attr.state_checked);
            } else if (index == R.styleable.background_button_drawable_bl_unChecked_button_drawable) {
                setSelectorDrawable(this.typedArray, this.buttonTa, stateListDrawable, index, -16842912);
            }
        }
        return stateListDrawable;
    }
}
