package com.noober.background.drawable;

import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import com.noober.background.R;

/* loaded from: classes4.dex */
public class PressDrawableCreator implements ICreateDrawable {
    private GradientDrawable drawable;
    private TypedArray pressTa;
    private TypedArray typedArray;

    public PressDrawableCreator(GradientDrawable gradientDrawable, TypedArray typedArray, TypedArray typedArray2) {
        this.drawable = gradientDrawable;
        this.pressTa = typedArray2;
        this.typedArray = typedArray;
    }

    @Override // com.noober.background.drawable.ICreateDrawable
    public Drawable create() throws Exception {
        StateListDrawable stateListDrawable = new StateListDrawable();
        for (int i2 = 0; i2 < this.pressTa.getIndexCount(); i2++) {
            int index = this.pressTa.getIndex(i2);
            if (index == R.styleable.background_press_bl_pressed_color) {
                int color = this.pressTa.getColor(index, 0);
                GradientDrawable drawable = DrawableFactory.getDrawable(this.typedArray);
                drawable.setColor(color);
                stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, drawable);
            } else if (index == R.styleable.background_press_bl_unpressed_color) {
                this.drawable.setColor(this.pressTa.getColor(index, 0));
                stateListDrawable.addState(new int[]{-16842919}, this.drawable);
            }
        }
        return stateListDrawable;
    }
}
