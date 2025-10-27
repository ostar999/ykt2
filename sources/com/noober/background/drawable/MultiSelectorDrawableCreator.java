package com.noober.background.drawable;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import com.noober.background.R;
import com.noober.background.common.MultiSelector;
import com.noober.background.common.ResourceUtils;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes4.dex */
public class MultiSelectorDrawableCreator implements ICreateDrawable {
    private Context context;
    private GradientDrawable gradientDrawable;
    private TypedArray selectorTa;
    TypedArray typedArray;

    public MultiSelectorDrawableCreator(Context context, TypedArray typedArray, TypedArray typedArray2) {
        this.selectorTa = typedArray;
        this.context = context;
        this.typedArray = typedArray2;
    }

    private void addState(StateListDrawable stateListDrawable, int i2) {
        String string = this.selectorTa.getString(i2);
        if (string != null) {
            String[] strArrSplit = string.split(",");
            if (strArrSplit.length < 2) {
                throw new IllegalArgumentException("Attributes and drawable must be set at the same time");
            }
            int[] iArr = new int[strArrSplit.length - 1];
            Drawable drawable = null;
            for (int i3 = 0; i3 < strArrSplit.length; i3++) {
                String str = strArrSplit[i3];
                if (i3 == strArrSplit.length - 1) {
                    int color = ResourceUtils.getColor(this.context, str);
                    if (this.typedArray.getIndexCount() > 0) {
                        try {
                            this.gradientDrawable = DrawableFactory.getDrawable(this.typedArray);
                        } catch (XmlPullParserException e2) {
                            e2.printStackTrace();
                        }
                    }
                    GradientDrawable gradientDrawable = this.gradientDrawable;
                    if (gradientDrawable == null || color == -1) {
                        drawable = ResourceUtils.getDrawable(this.context, str);
                    } else {
                        gradientDrawable.setColor(color);
                        drawable = this.gradientDrawable;
                    }
                    if (drawable == null) {
                        throw new IllegalArgumentException("cannot find drawable from the last attribute");
                    }
                } else {
                    MultiSelector multiAttr = MultiSelector.getMultiAttr(str.replace("-", ""));
                    if (multiAttr == null) {
                        throw new IllegalArgumentException("the attribute of bl_multi_selector only support state_checkable, state_checked, state_enabled, state_selected, state_pressed, state_focused, state_hovered, state_activated");
                    }
                    if (str.contains("-")) {
                        iArr[i3] = -multiAttr.id;
                    } else {
                        iArr[i3] = multiAttr.id;
                    }
                }
            }
            stateListDrawable.addState(iArr, drawable);
        }
    }

    @Override // com.noober.background.drawable.ICreateDrawable
    public Drawable create() {
        StateListDrawable stateListDrawable = new StateListDrawable();
        for (int i2 = 0; i2 < this.selectorTa.getIndexCount(); i2++) {
            int index = this.selectorTa.getIndex(i2);
            if (index == R.styleable.background_multi_selector_bl_multi_selector1) {
                addState(stateListDrawable, index);
            } else if (index == R.styleable.background_multi_selector_bl_multi_selector2) {
                addState(stateListDrawable, index);
            } else if (index == R.styleable.background_multi_selector_bl_multi_selector3) {
                addState(stateListDrawable, index);
            } else if (index == R.styleable.background_multi_selector_bl_multi_selector4) {
                addState(stateListDrawable, index);
            } else if (index == R.styleable.background_multi_selector_bl_multi_selector5) {
                addState(stateListDrawable, index);
            } else if (index == R.styleable.background_multi_selector_bl_multi_selector6) {
                addState(stateListDrawable, index);
            }
        }
        return stateListDrawable;
    }
}
