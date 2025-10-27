package com.noober.background.drawable;

import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import androidx.annotation.AttrRes;
import com.noober.background.R;

/* loaded from: classes4.dex */
public class SelectorDrawableCreator implements ICreateDrawable {
    private TypedArray selectorTa;
    private TypedArray typedArray;

    public SelectorDrawableCreator(TypedArray typedArray, TypedArray typedArray2) {
        this.typedArray = typedArray;
        this.selectorTa = typedArray2;
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
        for (int i2 = 0; i2 < this.selectorTa.getIndexCount(); i2++) {
            int index = this.selectorTa.getIndex(i2);
            if (index == R.styleable.background_selector_bl_checkable_drawable) {
                setSelectorDrawable(this.typedArray, this.selectorTa, stateListDrawable, index, android.R.attr.state_checkable);
            } else if (index == R.styleable.background_selector_bl_unCheckable_drawable) {
                setSelectorDrawable(this.typedArray, this.selectorTa, stateListDrawable, index, -16842911);
            } else if (index == R.styleable.background_selector_bl_checked_drawable) {
                setSelectorDrawable(this.typedArray, this.selectorTa, stateListDrawable, index, android.R.attr.state_checked);
            } else if (index == R.styleable.background_selector_bl_unChecked_drawable) {
                setSelectorDrawable(this.typedArray, this.selectorTa, stateListDrawable, index, -16842912);
            } else if (index == R.styleable.background_selector_bl_enabled_drawable) {
                setSelectorDrawable(this.typedArray, this.selectorTa, stateListDrawable, index, android.R.attr.state_enabled);
            } else if (index == R.styleable.background_selector_bl_unEnabled_drawable) {
                setSelectorDrawable(this.typedArray, this.selectorTa, stateListDrawable, index, -16842910);
            } else if (index == R.styleable.background_selector_bl_selected_drawable) {
                setSelectorDrawable(this.typedArray, this.selectorTa, stateListDrawable, index, android.R.attr.state_selected);
            } else if (index == R.styleable.background_selector_bl_unSelected_drawable) {
                setSelectorDrawable(this.typedArray, this.selectorTa, stateListDrawable, index, -16842913);
            } else if (index == R.styleable.background_selector_bl_pressed_drawable) {
                setSelectorDrawable(this.typedArray, this.selectorTa, stateListDrawable, index, android.R.attr.state_pressed);
            } else if (index == R.styleable.background_selector_bl_unPressed_drawable) {
                setSelectorDrawable(this.typedArray, this.selectorTa, stateListDrawable, index, -16842919);
            } else if (index == R.styleable.background_selector_bl_focused_drawable) {
                setSelectorDrawable(this.typedArray, this.selectorTa, stateListDrawable, index, android.R.attr.state_focused);
            } else if (index == R.styleable.background_selector_bl_unFocused_drawable) {
                setSelectorDrawable(this.typedArray, this.selectorTa, stateListDrawable, index, -16842908);
            } else if (index == R.styleable.background_selector_bl_focused_hovered) {
                setSelectorDrawable(this.typedArray, this.selectorTa, stateListDrawable, index, android.R.attr.state_hovered);
            } else if (index == R.styleable.background_selector_bl_unFocused_hovered) {
                setSelectorDrawable(this.typedArray, this.selectorTa, stateListDrawable, index, -16843623);
            } else if (index == R.styleable.background_selector_bl_focused_activated) {
                setSelectorDrawable(this.typedArray, this.selectorTa, stateListDrawable, index, android.R.attr.state_activated);
            } else if (index == R.styleable.background_selector_bl_unFocused_activated) {
                setSelectorDrawable(this.typedArray, this.selectorTa, stateListDrawable, index, -16843518);
            }
        }
        return stateListDrawable;
    }
}
