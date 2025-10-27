package com.noober.background.drawable;

import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import androidx.annotation.AttrRes;
import com.noober.background.R;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes4.dex */
public class GradientDrawableCreator implements ICreateDrawable {
    private int gradientState;
    private TypedArray typedArray;

    public GradientDrawableCreator(TypedArray typedArray) {
        this.gradientState = -1;
        this.typedArray = typedArray;
    }

    private boolean hasSetRadius(float[] fArr) {
        for (float f2 : fArr) {
            if (f2 != 0.0f) {
                return true;
            }
        }
        return false;
    }

    @Override // com.noober.background.drawable.ICreateDrawable
    public GradientDrawable create() throws XmlPullParserException, IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
        GradientDrawable gradientDrawable = new GradientDrawable();
        float[] fArr = new float[8];
        Rect rect = new Rect();
        int i2 = 0;
        int i3 = 0;
        int color = 0;
        int color2 = 0;
        int color3 = 0;
        int color4 = 0;
        int color5 = 0;
        int i4 = 0;
        int integer = 0;
        float dimension = 0.0f;
        float dimension2 = 0.0f;
        float dimension3 = -1.0f;
        float dimension4 = 0.0f;
        float dimension5 = 0.0f;
        float f2 = 0.0f;
        float f3 = 0.0f;
        while (i3 < this.typedArray.getIndexCount()) {
            int index = this.typedArray.getIndex(i3);
            if (index == R.styleable.background_bl_shape) {
                gradientDrawable.setShape(this.typedArray.getInt(index, i2));
            } else if (index == R.styleable.background_bl_solid_color) {
                color = this.typedArray.getColor(index, i2);
            } else if (index == R.styleable.background_bl_corners_radius) {
                gradientDrawable.setCornerRadius(this.typedArray.getDimension(index, 0.0f));
            } else if (index == R.styleable.background_bl_corners_bottomLeftRadius) {
                fArr[6] = this.typedArray.getDimension(index, 0.0f);
                fArr[7] = this.typedArray.getDimension(index, 0.0f);
            } else if (index == R.styleable.background_bl_corners_bottomRightRadius) {
                fArr[4] = this.typedArray.getDimension(index, 0.0f);
                fArr[5] = this.typedArray.getDimension(index, 0.0f);
            } else if (index == R.styleable.background_bl_corners_topLeftRadius) {
                fArr[0] = this.typedArray.getDimension(index, 0.0f);
                fArr[1] = this.typedArray.getDimension(index, 0.0f);
            } else if (index == R.styleable.background_bl_corners_topRightRadius) {
                fArr[2] = this.typedArray.getDimension(index, 0.0f);
                fArr[3] = this.typedArray.getDimension(index, 0.0f);
            } else if (index == R.styleable.background_bl_corners_leftRadius) {
                fArr[0] = this.typedArray.getDimension(index, 0.0f);
                fArr[1] = this.typedArray.getDimension(index, 0.0f);
                fArr[6] = this.typedArray.getDimension(index, 0.0f);
                fArr[7] = this.typedArray.getDimension(index, 0.0f);
            } else if (index == R.styleable.background_bl_corners_topRadius) {
                fArr[0] = this.typedArray.getDimension(index, 0.0f);
                fArr[1] = this.typedArray.getDimension(index, 0.0f);
                fArr[2] = this.typedArray.getDimension(index, 0.0f);
                fArr[3] = this.typedArray.getDimension(index, 0.0f);
            } else if (index == R.styleable.background_bl_corners_rightRadius) {
                fArr[2] = this.typedArray.getDimension(index, 0.0f);
                fArr[3] = this.typedArray.getDimension(index, 0.0f);
                fArr[4] = this.typedArray.getDimension(index, 0.0f);
                fArr[5] = this.typedArray.getDimension(index, 0.0f);
            } else if (index == R.styleable.background_bl_corners_bottomRadius) {
                fArr[4] = this.typedArray.getDimension(index, 0.0f);
                fArr[5] = this.typedArray.getDimension(index, 0.0f);
                fArr[6] = this.typedArray.getDimension(index, 0.0f);
                fArr[7] = this.typedArray.getDimension(index, 0.0f);
            } else if (index == R.styleable.background_bl_gradient_angle || index == R.styleable.background_bl_checkable_gradient_angle || index == R.styleable.background_bl_unCheckable_gradient_angle || index == R.styleable.background_bl_checked_gradient_angle || index == R.styleable.background_bl_unChecked_gradient_angle || index == R.styleable.background_bl_enabled_gradient_angle || index == R.styleable.background_bl_unEnabled_gradient_angle || index == R.styleable.background_bl_selected_gradient_angle || index == R.styleable.background_bl_unSelected_gradient_angle || index == R.styleable.background_bl_pressed_gradient_angle || index == R.styleable.background_bl_unPressed_gradient_angle || index == R.styleable.background_bl_focused_gradient_angle || index == R.styleable.background_bl_unFocused_gradient_angle) {
                int i5 = this.gradientState;
                if (i5 == -1) {
                    integer = this.typedArray.getInteger(index, 0);
                } else if (i5 == 16842911 && index == R.styleable.background_bl_checkable_gradient_angle) {
                    integer = this.typedArray.getInteger(index, 0);
                } else if (i5 == -16842911 && index == R.styleable.background_bl_unCheckable_gradient_angle) {
                    integer = this.typedArray.getInteger(index, 0);
                } else if (i5 == 16842912 && index == R.styleable.background_bl_checked_gradient_angle) {
                    integer = this.typedArray.getInteger(index, 0);
                } else if (i5 == -16842912 && index == R.styleable.background_bl_unChecked_gradient_angle) {
                    integer = this.typedArray.getInteger(index, 0);
                } else if (i5 == 16842910 && index == R.styleable.background_bl_enabled_gradient_angle) {
                    integer = this.typedArray.getInteger(index, 0);
                } else if (i5 == -16842910 && index == R.styleable.background_bl_unEnabled_gradient_angle) {
                    integer = this.typedArray.getInteger(index, 0);
                } else if (i5 == 16842913 && index == R.styleable.background_bl_selected_gradient_angle) {
                    integer = this.typedArray.getInteger(index, 0);
                } else if (i5 == -16842913 && index == R.styleable.background_bl_unSelected_gradient_angle) {
                    integer = this.typedArray.getInteger(index, 0);
                } else if (i5 == 16842919 && index == R.styleable.background_bl_pressed_gradient_angle) {
                    integer = this.typedArray.getInteger(index, 0);
                } else if (i5 == -16842919 && index == R.styleable.background_bl_unPressed_gradient_angle) {
                    integer = this.typedArray.getInteger(index, 0);
                } else if (i5 == 16842908 && index == R.styleable.background_bl_focused_gradient_angle) {
                    integer = this.typedArray.getInteger(index, 0);
                } else if (i5 == -16842908 && index == R.styleable.background_bl_unFocused_gradient_angle) {
                    integer = this.typedArray.getInteger(index, 0);
                }
            } else if (index == R.styleable.background_bl_gradient_centerX || index == R.styleable.background_bl_checkable_gradient_centerX || index == R.styleable.background_bl_unCheckable_gradient_centerX || index == R.styleable.background_bl_checked_gradient_centerX || index == R.styleable.background_bl_unChecked_gradient_centerX || index == R.styleable.background_bl_enabled_gradient_centerX || index == R.styleable.background_bl_unEnabled_gradient_centerX || index == R.styleable.background_bl_selected_gradient_centerX || index == R.styleable.background_bl_unSelected_gradient_centerX || index == R.styleable.background_bl_pressed_gradient_centerX || index == R.styleable.background_bl_unPressed_gradient_centerX || index == R.styleable.background_bl_focused_gradient_centerX || index == R.styleable.background_bl_unFocused_gradient_centerX) {
                int i6 = this.gradientState;
                if (i6 == -1) {
                    f2 = this.typedArray.getFloat(index, -1.0f);
                } else if (i6 == 16842911 && index == R.styleable.background_bl_checkable_gradient_centerX) {
                    f2 = this.typedArray.getFloat(index, -1.0f);
                } else if (i6 == -16842911 && index == R.styleable.background_bl_unCheckable_gradient_centerX) {
                    f2 = this.typedArray.getFloat(index, -1.0f);
                } else if (i6 == 16842912 && index == R.styleable.background_bl_checked_gradient_centerX) {
                    f2 = this.typedArray.getFloat(index, -1.0f);
                } else if (i6 == -16842912 && index == R.styleable.background_bl_unChecked_gradient_centerX) {
                    f2 = this.typedArray.getFloat(index, -1.0f);
                } else if (i6 == 16842910 && index == R.styleable.background_bl_enabled_gradient_centerX) {
                    f2 = this.typedArray.getFloat(index, -1.0f);
                } else if (i6 == -16842910 && index == R.styleable.background_bl_unEnabled_gradient_centerX) {
                    f2 = this.typedArray.getFloat(index, -1.0f);
                } else if (i6 == 16842913 && index == R.styleable.background_bl_selected_gradient_centerX) {
                    f2 = this.typedArray.getFloat(index, -1.0f);
                } else if (i6 == -16842913 && index == R.styleable.background_bl_unSelected_gradient_centerX) {
                    f2 = this.typedArray.getFloat(index, -1.0f);
                } else if (i6 == 16842919 && index == R.styleable.background_bl_pressed_gradient_centerX) {
                    f2 = this.typedArray.getFloat(index, -1.0f);
                } else if (i6 == -16842919 && index == R.styleable.background_bl_unPressed_gradient_centerX) {
                    f2 = this.typedArray.getFloat(index, -1.0f);
                } else if (i6 == 16842908 && index == R.styleable.background_bl_focused_gradient_centerX) {
                    f2 = this.typedArray.getFloat(index, -1.0f);
                } else if (i6 == -16842908 && index == R.styleable.background_bl_unFocused_gradient_centerX) {
                    f2 = this.typedArray.getFloat(index, -1.0f);
                }
            } else if (index == R.styleable.background_bl_gradient_centerY || index == R.styleable.background_bl_checkable_gradient_centerY || index == R.styleable.background_bl_unCheckable_gradient_centerY || index == R.styleable.background_bl_checked_gradient_centerY || index == R.styleable.background_bl_unChecked_gradient_centerY || index == R.styleable.background_bl_enabled_gradient_centerY || index == R.styleable.background_bl_unEnabled_gradient_centerY || index == R.styleable.background_bl_selected_gradient_centerY || index == R.styleable.background_bl_unSelected_gradient_centerY || index == R.styleable.background_bl_pressed_gradient_centerY || index == R.styleable.background_bl_unPressed_gradient_centerY || index == R.styleable.background_bl_focused_gradient_centerY || index == R.styleable.background_bl_unFocused_gradient_centerY) {
                int i7 = this.gradientState;
                if (i7 == -1) {
                    f3 = this.typedArray.getFloat(index, -1.0f);
                } else if (i7 == 16842911 && index == R.styleable.background_bl_checkable_gradient_centerY) {
                    f3 = this.typedArray.getFloat(index, -1.0f);
                } else if (i7 == -16842911 && index == R.styleable.background_bl_unCheckable_gradient_centerY) {
                    f3 = this.typedArray.getFloat(index, -1.0f);
                } else if (i7 == 16842912 && index == R.styleable.background_bl_checked_gradient_centerY) {
                    f3 = this.typedArray.getFloat(index, -1.0f);
                } else if (i7 == -16842912 && index == R.styleable.background_bl_unChecked_gradient_centerY) {
                    f3 = this.typedArray.getFloat(index, -1.0f);
                } else if (i7 == 16842910 && index == R.styleable.background_bl_enabled_gradient_centerY) {
                    f3 = this.typedArray.getFloat(index, -1.0f);
                } else if (i7 == -16842910 && index == R.styleable.background_bl_unEnabled_gradient_centerY) {
                    f3 = this.typedArray.getFloat(index, -1.0f);
                } else if (i7 == 16842913 && index == R.styleable.background_bl_selected_gradient_centerY) {
                    f3 = this.typedArray.getFloat(index, -1.0f);
                } else if (i7 == -16842913 && index == R.styleable.background_bl_unSelected_gradient_centerY) {
                    f3 = this.typedArray.getFloat(index, -1.0f);
                } else if (i7 == 16842919 && index == R.styleable.background_bl_pressed_gradient_centerY) {
                    f3 = this.typedArray.getFloat(index, -1.0f);
                } else if (i7 == -16842919 && index == R.styleable.background_bl_unPressed_gradient_centerY) {
                    f3 = this.typedArray.getFloat(index, -1.0f);
                } else if (i7 == 16842908 && index == R.styleable.background_bl_focused_gradient_centerY) {
                    f3 = this.typedArray.getFloat(index, -1.0f);
                } else if (i7 == -16842908 && index == R.styleable.background_bl_unFocused_gradient_centerY) {
                    f3 = this.typedArray.getFloat(index, -1.0f);
                }
            } else if (index == R.styleable.background_bl_gradient_centerColor || index == R.styleable.background_bl_checkable_gradient_centerColor || index == R.styleable.background_bl_unCheckable_gradient_centerColor || index == R.styleable.background_bl_checked_gradient_centerColor || index == R.styleable.background_bl_unChecked_gradient_centerColor || index == R.styleable.background_bl_enabled_gradient_centerColor || index == R.styleable.background_bl_unEnabled_gradient_centerColor || index == R.styleable.background_bl_selected_gradient_centerColor || index == R.styleable.background_bl_unSelected_gradient_centerColor || index == R.styleable.background_bl_pressed_gradient_centerColor || index == R.styleable.background_bl_unPressed_gradient_centerColor || index == R.styleable.background_bl_focused_gradient_centerColor || index == R.styleable.background_bl_unFocused_gradient_centerColor) {
                int i8 = this.gradientState;
                if (i8 == -1) {
                    color3 = this.typedArray.getColor(index, 0);
                } else if (i8 == 16842911 && index == R.styleable.background_bl_checkable_gradient_centerColor) {
                    color3 = this.typedArray.getColor(index, 0);
                } else if (i8 == -16842911 && index == R.styleable.background_bl_unCheckable_gradient_centerColor) {
                    color3 = this.typedArray.getColor(index, 0);
                } else if (i8 == 16842912 && index == R.styleable.background_bl_checked_gradient_centerColor) {
                    color3 = this.typedArray.getColor(index, 0);
                } else if (i8 == -16842912 && index == R.styleable.background_bl_unChecked_gradient_centerColor) {
                    color3 = this.typedArray.getColor(index, 0);
                } else if (i8 == 16842910 && index == R.styleable.background_bl_enabled_gradient_centerColor) {
                    color3 = this.typedArray.getColor(index, 0);
                } else if (i8 == -16842910 && index == R.styleable.background_bl_unEnabled_gradient_centerColor) {
                    color3 = this.typedArray.getColor(index, 0);
                } else if (i8 == 16842913 && index == R.styleable.background_bl_selected_gradient_centerColor) {
                    color3 = this.typedArray.getColor(index, 0);
                } else if (i8 == -16842913 && index == R.styleable.background_bl_unSelected_gradient_centerColor) {
                    color3 = this.typedArray.getColor(index, 0);
                } else if (i8 == 16842919 && index == R.styleable.background_bl_pressed_gradient_centerColor) {
                    color3 = this.typedArray.getColor(index, 0);
                } else if (i8 == -16842919 && index == R.styleable.background_bl_unPressed_gradient_centerColor) {
                    color3 = this.typedArray.getColor(index, 0);
                } else if (i8 == 16842908 && index == R.styleable.background_bl_focused_gradient_centerColor) {
                    color3 = this.typedArray.getColor(index, 0);
                } else if (i8 == -16842908 && index == R.styleable.background_bl_unFocused_gradient_centerColor) {
                    color3 = this.typedArray.getColor(index, 0);
                }
            } else if (index == R.styleable.background_bl_gradient_endColor || index == R.styleable.background_bl_checkable_gradient_endColor || index == R.styleable.background_bl_unCheckable_gradient_endColor || index == R.styleable.background_bl_checked_gradient_endColor || index == R.styleable.background_bl_unChecked_gradient_endColor || index == R.styleable.background_bl_enabled_gradient_endColor || index == R.styleable.background_bl_unEnabled_gradient_endColor || index == R.styleable.background_bl_selected_gradient_endColor || index == R.styleable.background_bl_unSelected_gradient_endColor || index == R.styleable.background_bl_pressed_gradient_endColor || index == R.styleable.background_bl_unPressed_gradient_endColor || index == R.styleable.background_bl_focused_gradient_endColor || index == R.styleable.background_bl_unFocused_gradient_endColor) {
                int i9 = this.gradientState;
                if (i9 == -1) {
                    color5 = this.typedArray.getColor(index, 0);
                } else if (i9 == 16842911 && index == R.styleable.background_bl_checkable_gradient_endColor) {
                    color5 = this.typedArray.getColor(index, 0);
                } else if (i9 == -16842911 && index == R.styleable.background_bl_unCheckable_gradient_endColor) {
                    color5 = this.typedArray.getColor(index, 0);
                } else if (i9 == 16842912 && index == R.styleable.background_bl_checked_gradient_endColor) {
                    color5 = this.typedArray.getColor(index, 0);
                } else if (i9 == -16842912 && index == R.styleable.background_bl_unChecked_gradient_endColor) {
                    color5 = this.typedArray.getColor(index, 0);
                } else if (i9 == 16842910 && index == R.styleable.background_bl_enabled_gradient_endColor) {
                    color5 = this.typedArray.getColor(index, 0);
                } else if (i9 == -16842910 && index == R.styleable.background_bl_unEnabled_gradient_endColor) {
                    color5 = this.typedArray.getColor(index, 0);
                } else if (i9 == 16842913 && index == R.styleable.background_bl_selected_gradient_endColor) {
                    color5 = this.typedArray.getColor(index, 0);
                } else if (i9 == -16842913 && index == R.styleable.background_bl_unSelected_gradient_endColor) {
                    color5 = this.typedArray.getColor(index, 0);
                } else if (i9 == 16842919 && index == R.styleable.background_bl_pressed_gradient_endColor) {
                    color5 = this.typedArray.getColor(index, 0);
                } else if (i9 == -16842919 && index == R.styleable.background_bl_unPressed_gradient_endColor) {
                    color5 = this.typedArray.getColor(index, 0);
                } else if (i9 == 16842908 && index == R.styleable.background_bl_focused_gradient_endColor) {
                    color5 = this.typedArray.getColor(index, 0);
                } else if (i9 == -16842908 && index == R.styleable.background_bl_unFocused_gradient_endColor) {
                    color5 = this.typedArray.getColor(index, 0);
                }
            } else if (index == R.styleable.background_bl_gradient_startColor || index == R.styleable.background_bl_checkable_gradient_startColor || index == R.styleable.background_bl_unCheckable_gradient_startColor || index == R.styleable.background_bl_checked_gradient_startColor || index == R.styleable.background_bl_unChecked_gradient_startColor || index == R.styleable.background_bl_enabled_gradient_startColor || index == R.styleable.background_bl_unEnabled_gradient_startColor || index == R.styleable.background_bl_selected_gradient_startColor || index == R.styleable.background_bl_unSelected_gradient_startColor || index == R.styleable.background_bl_pressed_gradient_startColor || index == R.styleable.background_bl_unPressed_gradient_startColor || index == R.styleable.background_bl_focused_gradient_startColor || index == R.styleable.background_bl_unFocused_gradient_startColor) {
                int i10 = this.gradientState;
                if (i10 == -1) {
                    color4 = this.typedArray.getColor(index, 0);
                } else if (i10 == 16842911 && index == R.styleable.background_bl_checkable_gradient_startColor) {
                    color4 = this.typedArray.getColor(index, 0);
                } else if (i10 == -16842911 && index == R.styleable.background_bl_unCheckable_gradient_startColor) {
                    color4 = this.typedArray.getColor(index, 0);
                } else if (i10 == 16842912 && index == R.styleable.background_bl_checked_gradient_startColor) {
                    color4 = this.typedArray.getColor(index, 0);
                } else if (i10 == -16842912 && index == R.styleable.background_bl_unChecked_gradient_startColor) {
                    color4 = this.typedArray.getColor(index, 0);
                } else if (i10 == 16842910 && index == R.styleable.background_bl_enabled_gradient_startColor) {
                    color4 = this.typedArray.getColor(index, 0);
                } else if (i10 == -16842910 && index == R.styleable.background_bl_unEnabled_gradient_startColor) {
                    color4 = this.typedArray.getColor(index, 0);
                } else if (i10 == 16842913 && index == R.styleable.background_bl_selected_gradient_startColor) {
                    color4 = this.typedArray.getColor(index, 0);
                } else if (i10 == -16842913 && index == R.styleable.background_bl_unSelected_gradient_startColor) {
                    color4 = this.typedArray.getColor(index, 0);
                } else if (i10 == 16842919 && index == R.styleable.background_bl_pressed_gradient_startColor) {
                    color4 = this.typedArray.getColor(index, 0);
                } else if (i10 == -16842919 && index == R.styleable.background_bl_unPressed_gradient_startColor) {
                    color4 = this.typedArray.getColor(index, 0);
                } else if (i10 == 16842908 && index == R.styleable.background_bl_focused_gradient_startColor) {
                    color4 = this.typedArray.getColor(index, 0);
                } else if (i10 == -16842908 && index == R.styleable.background_bl_unFocused_gradient_startColor) {
                    color4 = this.typedArray.getColor(index, 0);
                }
            } else if (index == R.styleable.background_bl_gradient_gradientRadius || index == R.styleable.background_bl_checkable_gradient_gradientRadius || index == R.styleable.background_bl_unCheckable_gradient_gradientRadius || index == R.styleable.background_bl_checked_gradient_gradientRadius || index == R.styleable.background_bl_unChecked_gradient_gradientRadius || index == R.styleable.background_bl_enabled_gradient_gradientRadius || index == R.styleable.background_bl_unEnabled_gradient_gradientRadius || index == R.styleable.background_bl_selected_gradient_gradientRadius || index == R.styleable.background_bl_unSelected_gradient_gradientRadius || index == R.styleable.background_bl_pressed_gradient_gradientRadius || index == R.styleable.background_bl_unPressed_gradient_gradientRadius || index == R.styleable.background_bl_focused_gradient_gradientRadius || index == R.styleable.background_bl_unFocused_gradient_gradientRadius) {
                int i11 = this.gradientState;
                if (i11 == -1) {
                    gradientDrawable.setGradientRadius(this.typedArray.getDimension(index, 0.0f));
                } else if (i11 == 16842911 && index == R.styleable.background_bl_checkable_gradient_gradientRadius) {
                    gradientDrawable.setGradientRadius(this.typedArray.getDimension(index, 0.0f));
                } else if (i11 == -16842911 && index == R.styleable.background_bl_unCheckable_gradient_gradientRadius) {
                    gradientDrawable.setGradientRadius(this.typedArray.getDimension(index, 0.0f));
                } else if (i11 == 16842912 && index == R.styleable.background_bl_checked_gradient_gradientRadius) {
                    gradientDrawable.setGradientRadius(this.typedArray.getDimension(index, 0.0f));
                } else if (i11 == -16842912 && index == R.styleable.background_bl_unChecked_gradient_gradientRadius) {
                    gradientDrawable.setGradientRadius(this.typedArray.getDimension(index, 0.0f));
                } else if (i11 == 16842910 && index == R.styleable.background_bl_enabled_gradient_gradientRadius) {
                    gradientDrawable.setGradientRadius(this.typedArray.getDimension(index, 0.0f));
                } else if (i11 == -16842910 && index == R.styleable.background_bl_unEnabled_gradient_gradientRadius) {
                    gradientDrawable.setGradientRadius(this.typedArray.getDimension(index, 0.0f));
                } else if (i11 == 16842913 && index == R.styleable.background_bl_selected_gradient_gradientRadius) {
                    gradientDrawable.setGradientRadius(this.typedArray.getDimension(index, 0.0f));
                } else if (i11 == -16842913 && index == R.styleable.background_bl_unSelected_gradient_gradientRadius) {
                    gradientDrawable.setGradientRadius(this.typedArray.getDimension(index, 0.0f));
                } else if (i11 == 16842919 && index == R.styleable.background_bl_pressed_gradient_gradientRadius) {
                    gradientDrawable.setGradientRadius(this.typedArray.getDimension(index, 0.0f));
                } else if (i11 == -16842919 && index == R.styleable.background_bl_unPressed_gradient_gradientRadius) {
                    gradientDrawable.setGradientRadius(this.typedArray.getDimension(index, 0.0f));
                } else if (i11 == 16842908 && index == R.styleable.background_bl_focused_gradient_gradientRadius) {
                    gradientDrawable.setGradientRadius(this.typedArray.getDimension(index, 0.0f));
                } else if (i11 == -16842908 && index == R.styleable.background_bl_unFocused_gradient_gradientRadius) {
                    gradientDrawable.setGradientRadius(this.typedArray.getDimension(index, 0.0f));
                }
            } else if (index == R.styleable.background_bl_gradient_type || index == R.styleable.background_bl_checkable_gradient_type || index == R.styleable.background_bl_unCheckable_gradient_type || index == R.styleable.background_bl_checked_gradient_type || index == R.styleable.background_bl_unChecked_gradient_type || index == R.styleable.background_bl_enabled_gradient_type || index == R.styleable.background_bl_unEnabled_gradient_type || index == R.styleable.background_bl_selected_gradient_type || index == R.styleable.background_bl_unSelected_gradient_type || index == R.styleable.background_bl_pressed_gradient_type || index == R.styleable.background_bl_unPressed_gradient_type || index == R.styleable.background_bl_focused_gradient_type || index == R.styleable.background_bl_unFocused_gradient_type) {
                int i12 = this.gradientState;
                if (i12 == -1) {
                    i4 = this.typedArray.getInt(index, 0);
                } else if (i12 == 16842911 && index == R.styleable.background_bl_checkable_gradient_type) {
                    i4 = this.typedArray.getInt(index, 0);
                } else if (i12 == -16842911 && index == R.styleable.background_bl_unCheckable_gradient_type) {
                    i4 = this.typedArray.getInt(index, 0);
                } else if (i12 == 16842912 && index == R.styleable.background_bl_checked_gradient_type) {
                    i4 = this.typedArray.getInt(index, 0);
                } else if (i12 == -16842912 && index == R.styleable.background_bl_unChecked_gradient_type) {
                    i4 = this.typedArray.getInt(index, 0);
                } else if (i12 == 16842910 && index == R.styleable.background_bl_enabled_gradient_type) {
                    i4 = this.typedArray.getInt(index, 0);
                } else if (i12 == -16842910 && index == R.styleable.background_bl_unEnabled_gradient_type) {
                    i4 = this.typedArray.getInt(index, 0);
                } else if (i12 == 16842913 && index == R.styleable.background_bl_selected_gradient_type) {
                    i4 = this.typedArray.getInt(index, 0);
                } else if (i12 == -16842913 && index == R.styleable.background_bl_unSelected_gradient_type) {
                    i4 = this.typedArray.getInt(index, 0);
                } else if (i12 == 16842919 && index == R.styleable.background_bl_pressed_gradient_type) {
                    i4 = this.typedArray.getInt(index, 0);
                } else if (i12 == -16842919 && index == R.styleable.background_bl_unPressed_gradient_type) {
                    i4 = this.typedArray.getInt(index, 0);
                } else if (i12 == 16842908 && index == R.styleable.background_bl_focused_gradient_type) {
                    i4 = this.typedArray.getInt(index, 0);
                } else if (i12 == -16842908 && index == R.styleable.background_bl_unFocused_gradient_type) {
                    i4 = this.typedArray.getInt(index, 0);
                }
                int i13 = i4;
                gradientDrawable.setGradientType(i13);
                i4 = i13;
            } else if (index == R.styleable.background_bl_gradient_useLevel || index == R.styleable.background_bl_checkable_gradient_useLevel || index == R.styleable.background_bl_unCheckable_gradient_useLevel || index == R.styleable.background_bl_checked_gradient_useLevel || index == R.styleable.background_bl_unChecked_gradient_useLevel || index == R.styleable.background_bl_enabled_gradient_useLevel || index == R.styleable.background_bl_unEnabled_gradient_useLevel || index == R.styleable.background_bl_selected_gradient_useLevel || index == R.styleable.background_bl_unSelected_gradient_useLevel || index == R.styleable.background_bl_pressed_gradient_useLevel || index == R.styleable.background_bl_unPressed_gradient_useLevel || index == R.styleable.background_bl_focused_gradient_useLevel || index == R.styleable.background_bl_unFocused_gradient_useLevel) {
                int i14 = this.gradientState;
                if (i14 == -1) {
                    gradientDrawable.setUseLevel(this.typedArray.getBoolean(index, false));
                } else if (i14 == 16842911 && index == R.styleable.background_bl_checkable_gradient_useLevel) {
                    gradientDrawable.setUseLevel(this.typedArray.getBoolean(index, false));
                } else if (i14 == -16842911 && index == R.styleable.background_bl_unCheckable_gradient_useLevel) {
                    gradientDrawable.setUseLevel(this.typedArray.getBoolean(index, false));
                } else if (i14 == 16842912 && index == R.styleable.background_bl_checked_gradient_useLevel) {
                    gradientDrawable.setUseLevel(this.typedArray.getBoolean(index, false));
                } else if (i14 == -16842912 && index == R.styleable.background_bl_unChecked_gradient_useLevel) {
                    gradientDrawable.setUseLevel(this.typedArray.getBoolean(index, false));
                } else if (i14 == 16842910 && index == R.styleable.background_bl_enabled_gradient_useLevel) {
                    gradientDrawable.setUseLevel(this.typedArray.getBoolean(index, false));
                } else if (i14 == -16842910 && index == R.styleable.background_bl_unEnabled_gradient_useLevel) {
                    gradientDrawable.setUseLevel(this.typedArray.getBoolean(index, false));
                } else if (i14 == 16842913 && index == R.styleable.background_bl_selected_gradient_useLevel) {
                    gradientDrawable.setUseLevel(this.typedArray.getBoolean(index, false));
                } else if (i14 == -16842913 && index == R.styleable.background_bl_unSelected_gradient_useLevel) {
                    gradientDrawable.setUseLevel(this.typedArray.getBoolean(index, false));
                } else if (i14 == 16842919 && index == R.styleable.background_bl_pressed_gradient_useLevel) {
                    gradientDrawable.setUseLevel(this.typedArray.getBoolean(index, false));
                } else if (i14 == -16842919 && index == R.styleable.background_bl_unPressed_gradient_useLevel) {
                    gradientDrawable.setUseLevel(this.typedArray.getBoolean(index, false));
                } else if (i14 == 16842908 && index == R.styleable.background_bl_focused_gradient_useLevel) {
                    gradientDrawable.setUseLevel(this.typedArray.getBoolean(index, false));
                } else if (i14 == -16842908 && index == R.styleable.background_bl_unFocused_gradient_useLevel) {
                    gradientDrawable.setUseLevel(this.typedArray.getBoolean(index, false));
                }
            } else if (index == R.styleable.background_bl_padding_left) {
                rect.left = (int) this.typedArray.getDimension(index, 0.0f);
            } else if (index == R.styleable.background_bl_padding_top) {
                rect.top = (int) this.typedArray.getDimension(index, 0.0f);
            } else if (index == R.styleable.background_bl_padding_right) {
                rect.right = (int) this.typedArray.getDimension(index, 0.0f);
            } else if (index == R.styleable.background_bl_padding_bottom) {
                rect.bottom = (int) this.typedArray.getDimension(index, 0.0f);
            } else if (index == R.styleable.background_bl_size_width) {
                dimension = this.typedArray.getDimension(index, 0.0f);
            } else if (index == R.styleable.background_bl_size_height) {
                dimension2 = this.typedArray.getDimension(index, 0.0f);
            } else if (index == R.styleable.background_bl_stroke_width) {
                dimension3 = this.typedArray.getDimension(index, 0.0f);
            } else if (index == R.styleable.background_bl_stroke_color) {
                color2 = this.typedArray.getColor(index, 0);
            } else if (index == R.styleable.background_bl_stroke_dashWidth) {
                dimension4 = this.typedArray.getDimension(index, 0.0f);
            } else if (index == R.styleable.background_bl_stroke_dashGap) {
                dimension5 = this.typedArray.getDimension(index, 0.0f);
            }
            i3++;
            i2 = 0;
        }
        if (hasSetRadius(fArr)) {
            gradientDrawable.setCornerRadii(fArr);
        }
        if (this.typedArray.hasValue(R.styleable.background_bl_size_width) && this.typedArray.hasValue(R.styleable.background_bl_size_height)) {
            gradientDrawable.setSize((int) dimension, (int) dimension2);
        }
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        TypedArray typedArray = this.typedArray;
        int i15 = R.styleable.background_bl_pressed_solid_color;
        if (typedArray.hasValue(i15)) {
            arrayList.add(Integer.valueOf(android.R.attr.state_pressed));
            arrayList2.add(Integer.valueOf(this.typedArray.getColor(i15, 0)));
        }
        TypedArray typedArray2 = this.typedArray;
        int i16 = R.styleable.background_bl_unPressed_solid_color;
        if (typedArray2.hasValue(i16)) {
            arrayList.add(-16842919);
            arrayList2.add(Integer.valueOf(this.typedArray.getColor(i16, 0)));
        }
        TypedArray typedArray3 = this.typedArray;
        int i17 = R.styleable.background_bl_checkable_solid_color;
        if (typedArray3.hasValue(i17)) {
            arrayList.add(Integer.valueOf(android.R.attr.state_checkable));
            arrayList2.add(Integer.valueOf(this.typedArray.getColor(i17, 0)));
        }
        TypedArray typedArray4 = this.typedArray;
        int i18 = R.styleable.background_bl_unCheckable_solid_color;
        if (typedArray4.hasValue(i18)) {
            arrayList.add(-16842911);
            arrayList2.add(Integer.valueOf(this.typedArray.getColor(i18, 0)));
        }
        TypedArray typedArray5 = this.typedArray;
        int i19 = R.styleable.background_bl_checked_solid_color;
        if (typedArray5.hasValue(i19)) {
            arrayList.add(Integer.valueOf(android.R.attr.state_checked));
            arrayList2.add(Integer.valueOf(this.typedArray.getColor(i19, 0)));
        }
        TypedArray typedArray6 = this.typedArray;
        int i20 = R.styleable.background_bl_unChecked_solid_color;
        if (typedArray6.hasValue(i20)) {
            arrayList.add(-16842912);
            arrayList2.add(Integer.valueOf(this.typedArray.getColor(i20, 0)));
        }
        TypedArray typedArray7 = this.typedArray;
        int i21 = R.styleable.background_bl_enabled_solid_color;
        if (typedArray7.hasValue(i21)) {
            arrayList.add(Integer.valueOf(android.R.attr.state_enabled));
            arrayList2.add(Integer.valueOf(this.typedArray.getColor(i21, 0)));
        }
        TypedArray typedArray8 = this.typedArray;
        int i22 = R.styleable.background_bl_unEnabled_solid_color;
        if (typedArray8.hasValue(i22)) {
            arrayList.add(-16842910);
            arrayList2.add(Integer.valueOf(this.typedArray.getColor(i22, 0)));
        }
        TypedArray typedArray9 = this.typedArray;
        int i23 = R.styleable.background_bl_selected_solid_color;
        if (typedArray9.hasValue(i23)) {
            arrayList.add(Integer.valueOf(android.R.attr.state_selected));
            arrayList2.add(Integer.valueOf(this.typedArray.getColor(i23, 0)));
        }
        TypedArray typedArray10 = this.typedArray;
        int i24 = R.styleable.background_bl_unSelected_solid_color;
        if (typedArray10.hasValue(i24)) {
            arrayList.add(-16842913);
            arrayList2.add(Integer.valueOf(this.typedArray.getColor(i24, 0)));
        }
        TypedArray typedArray11 = this.typedArray;
        int i25 = R.styleable.background_bl_focused_solid_color;
        if (typedArray11.hasValue(i25)) {
            arrayList.add(Integer.valueOf(android.R.attr.state_focused));
            arrayList2.add(Integer.valueOf(this.typedArray.getColor(i25, 0)));
        }
        TypedArray typedArray12 = this.typedArray;
        int i26 = R.styleable.background_bl_unFocused_solid_color;
        if (typedArray12.hasValue(i26)) {
            arrayList.add(-16842908);
            arrayList2.add(Integer.valueOf(this.typedArray.getColor(i26, 0)));
        }
        if (arrayList.size() > 0) {
            int size = arrayList.size();
            if (this.typedArray.hasValue(R.styleable.background_bl_solid_color)) {
                size++;
            }
            int[][] iArr = new int[size][];
            int[] iArr2 = new int[size];
            int i27 = 0;
            for (Iterator it = arrayList.iterator(); it.hasNext(); it = it) {
                iArr[i27] = new int[]{((Integer) it.next()).intValue()};
                iArr2[i27] = ((Integer) arrayList2.get(i27)).intValue();
                i27++;
            }
            if (this.typedArray.hasValue(R.styleable.background_bl_solid_color)) {
                iArr[i27] = new int[0];
                iArr2[i27] = color;
            }
            gradientDrawable.setColor(new ColorStateList(iArr, iArr2));
        } else if (this.typedArray.hasValue(R.styleable.background_bl_solid_color)) {
            gradientDrawable.setColor(color);
        }
        if (this.typedArray.hasValue(R.styleable.background_bl_stroke_width)) {
            ArrayList arrayList3 = new ArrayList();
            ArrayList arrayList4 = new ArrayList();
            TypedArray typedArray13 = this.typedArray;
            int i28 = R.styleable.background_bl_pressed_stroke_color;
            if (typedArray13.hasValue(i28)) {
                TypedArray typedArray14 = this.typedArray;
                int i29 = R.styleable.background_bl_unPressed_stroke_color;
                if (typedArray14.hasValue(i29)) {
                    arrayList3.add(Integer.valueOf(android.R.attr.state_pressed));
                    arrayList3.add(-16842919);
                    arrayList4.add(Integer.valueOf(this.typedArray.getColor(i28, 0)));
                    arrayList4.add(Integer.valueOf(this.typedArray.getColor(i29, 0)));
                }
            }
            TypedArray typedArray15 = this.typedArray;
            int i30 = R.styleable.background_bl_checkable_stroke_color;
            if (typedArray15.hasValue(i30)) {
                TypedArray typedArray16 = this.typedArray;
                int i31 = R.styleable.background_bl_unCheckable_stroke_color;
                if (typedArray16.hasValue(i31)) {
                    arrayList3.add(Integer.valueOf(android.R.attr.state_checkable));
                    arrayList3.add(-16842911);
                    arrayList4.add(Integer.valueOf(this.typedArray.getColor(i30, 0)));
                    arrayList4.add(Integer.valueOf(this.typedArray.getColor(i31, 0)));
                }
            }
            TypedArray typedArray17 = this.typedArray;
            int i32 = R.styleable.background_bl_checked_stroke_color;
            if (typedArray17.hasValue(i32)) {
                TypedArray typedArray18 = this.typedArray;
                int i33 = R.styleable.background_bl_unChecked_stroke_color;
                if (typedArray18.hasValue(i33)) {
                    arrayList3.add(Integer.valueOf(android.R.attr.state_checked));
                    arrayList3.add(-16842912);
                    arrayList4.add(Integer.valueOf(this.typedArray.getColor(i32, 0)));
                    arrayList4.add(Integer.valueOf(this.typedArray.getColor(i33, 0)));
                }
            }
            TypedArray typedArray19 = this.typedArray;
            int i34 = R.styleable.background_bl_enabled_stroke_color;
            if (typedArray19.hasValue(i34)) {
                TypedArray typedArray20 = this.typedArray;
                int i35 = R.styleable.background_bl_unEnabled_stroke_color;
                if (typedArray20.hasValue(i35)) {
                    arrayList3.add(Integer.valueOf(android.R.attr.state_enabled));
                    arrayList3.add(-16842910);
                    arrayList4.add(Integer.valueOf(this.typedArray.getColor(i34, 0)));
                    arrayList4.add(Integer.valueOf(this.typedArray.getColor(i35, 0)));
                }
            }
            TypedArray typedArray21 = this.typedArray;
            int i36 = R.styleable.background_bl_selected_stroke_color;
            if (typedArray21.hasValue(i36)) {
                TypedArray typedArray22 = this.typedArray;
                int i37 = R.styleable.background_bl_unSelected_stroke_color;
                if (typedArray22.hasValue(i37)) {
                    arrayList3.add(Integer.valueOf(android.R.attr.state_selected));
                    arrayList3.add(-16842913);
                    arrayList4.add(Integer.valueOf(this.typedArray.getColor(i36, 0)));
                    arrayList4.add(Integer.valueOf(this.typedArray.getColor(i37, 0)));
                }
            }
            TypedArray typedArray23 = this.typedArray;
            int i38 = R.styleable.background_bl_focused_stroke_color;
            if (typedArray23.hasValue(i38)) {
                TypedArray typedArray24 = this.typedArray;
                int i39 = R.styleable.background_bl_unFocused_stroke_color;
                if (typedArray24.hasValue(i39)) {
                    arrayList3.add(Integer.valueOf(android.R.attr.state_focused));
                    arrayList3.add(-16842908);
                    arrayList4.add(Integer.valueOf(this.typedArray.getColor(i38, 0)));
                    arrayList4.add(Integer.valueOf(this.typedArray.getColor(i39, 0)));
                }
            }
            if (arrayList3.size() > 0) {
                int[][] iArr3 = new int[arrayList3.size()][];
                int[] iArr4 = new int[arrayList3.size()];
                Iterator it2 = arrayList3.iterator();
                int i40 = 0;
                while (it2.hasNext()) {
                    iArr3[i40] = new int[]{((Integer) it2.next()).intValue()};
                    iArr4[i40] = ((Integer) arrayList4.get(i40)).intValue();
                    i40++;
                }
                gradientDrawable.setStroke((int) dimension3, new ColorStateList(iArr3, iArr4), dimension4, dimension5);
            } else if (this.typedArray.hasValue(R.styleable.background_bl_stroke_color)) {
                gradientDrawable.setStroke((int) dimension3, color2, dimension4, dimension5);
            }
        }
        if ((this.typedArray.hasValue(R.styleable.background_bl_gradient_centerX) && this.typedArray.hasValue(R.styleable.background_bl_gradient_centerY)) || ((this.typedArray.hasValue(R.styleable.background_bl_checkable_gradient_centerX) && this.typedArray.hasValue(R.styleable.background_bl_unCheckable_gradient_centerY)) || ((this.typedArray.hasValue(R.styleable.background_bl_checked_gradient_centerX) && this.typedArray.hasValue(R.styleable.background_bl_unChecked_gradient_centerY)) || ((this.typedArray.hasValue(R.styleable.background_bl_enabled_gradient_centerX) && this.typedArray.hasValue(R.styleable.background_bl_unEnabled_gradient_centerY)) || ((this.typedArray.hasValue(R.styleable.background_bl_selected_gradient_centerX) && this.typedArray.hasValue(R.styleable.background_bl_unSelected_gradient_centerY)) || ((this.typedArray.hasValue(R.styleable.background_bl_pressed_gradient_centerX) && this.typedArray.hasValue(R.styleable.background_bl_unPressed_gradient_centerY)) || (this.typedArray.hasValue(R.styleable.background_bl_focused_gradient_centerX) && this.typedArray.hasValue(R.styleable.background_bl_unFocused_gradient_centerY)))))))) {
            gradientDrawable.setGradientCenter(f2, f3);
        }
        if ((this.typedArray.hasValue(R.styleable.background_bl_gradient_startColor) && this.typedArray.hasValue(R.styleable.background_bl_gradient_endColor)) || ((this.typedArray.hasValue(R.styleable.background_bl_checkable_gradient_startColor) && this.typedArray.hasValue(R.styleable.background_bl_unCheckable_gradient_endColor)) || ((this.typedArray.hasValue(R.styleable.background_bl_checked_gradient_startColor) && this.typedArray.hasValue(R.styleable.background_bl_unChecked_gradient_endColor)) || ((this.typedArray.hasValue(R.styleable.background_bl_enabled_gradient_startColor) && this.typedArray.hasValue(R.styleable.background_bl_unEnabled_gradient_endColor)) || ((this.typedArray.hasValue(R.styleable.background_bl_selected_gradient_startColor) && this.typedArray.hasValue(R.styleable.background_bl_unSelected_gradient_endColor)) || ((this.typedArray.hasValue(R.styleable.background_bl_pressed_gradient_startColor) && this.typedArray.hasValue(R.styleable.background_bl_unPressed_gradient_endColor)) || (this.typedArray.hasValue(R.styleable.background_bl_focused_gradient_startColor) && this.typedArray.hasValue(R.styleable.background_bl_unFocused_gradient_endColor)))))))) {
            gradientDrawable.setColors(this.typedArray.hasValue(R.styleable.background_bl_gradient_centerColor) ? new int[]{color4, color3, color5} : new int[]{color4, color5});
        }
        if (i4 == 0 && (this.typedArray.hasValue(R.styleable.background_bl_gradient_angle) || this.typedArray.hasValue(R.styleable.background_bl_checkable_gradient_angle) || this.typedArray.hasValue(R.styleable.background_bl_checked_gradient_angle) || this.typedArray.hasValue(R.styleable.background_bl_enabled_gradient_angle) || this.typedArray.hasValue(R.styleable.background_bl_selected_gradient_angle) || this.typedArray.hasValue(R.styleable.background_bl_pressed_gradient_angle) || this.typedArray.hasValue(R.styleable.background_bl_focused_gradient_angle) || this.typedArray.hasValue(R.styleable.background_bl_unCheckable_gradient_angle) || this.typedArray.hasValue(R.styleable.background_bl_unChecked_gradient_angle) || this.typedArray.hasValue(R.styleable.background_bl_unEnabled_gradient_angle) || this.typedArray.hasValue(R.styleable.background_bl_unSelected_gradient_angle) || this.typedArray.hasValue(R.styleable.background_bl_unPressed_gradient_angle) || this.typedArray.hasValue(R.styleable.background_bl_unFocused_gradient_angle))) {
            int i41 = integer % 360;
            if (i41 % 45 != 0) {
                throw new XmlPullParserException(this.typedArray.getPositionDescription() + "<gradient> tag requires 'angle' attribute to be a multiple of 45");
            }
            GradientDrawable.Orientation orientation = GradientDrawable.Orientation.LEFT_RIGHT;
            if (i41 != 0) {
                if (i41 == 45) {
                    orientation = GradientDrawable.Orientation.BL_TR;
                } else if (i41 == 90) {
                    orientation = GradientDrawable.Orientation.BOTTOM_TOP;
                } else if (i41 == 135) {
                    orientation = GradientDrawable.Orientation.BR_TL;
                } else if (i41 == 180) {
                    orientation = GradientDrawable.Orientation.RIGHT_LEFT;
                } else if (i41 == 225) {
                    orientation = GradientDrawable.Orientation.TR_BL;
                } else if (i41 == 270) {
                    orientation = GradientDrawable.Orientation.TOP_BOTTOM;
                } else if (i41 == 315) {
                    orientation = GradientDrawable.Orientation.TL_BR;
                }
            }
            gradientDrawable.setOrientation(orientation);
        }
        if (this.typedArray.hasValue(R.styleable.background_bl_padding_left) && this.typedArray.hasValue(R.styleable.background_bl_padding_top) && this.typedArray.hasValue(R.styleable.background_bl_padding_right) && this.typedArray.hasValue(R.styleable.background_bl_padding_bottom)) {
            if (Build.VERSION.SDK_INT >= 29) {
                gradientDrawable.setPadding(rect.left, rect.top, rect.right, rect.bottom);
            } else {
                try {
                    Field declaredField = gradientDrawable.getClass().getDeclaredField("mPadding");
                    declaredField.setAccessible(true);
                    declaredField.set(gradientDrawable, rect);
                } catch (IllegalAccessException e2) {
                    e2.printStackTrace();
                } catch (NoSuchFieldException e3) {
                    e3.printStackTrace();
                }
            }
        }
        return gradientDrawable;
    }

    public GradientDrawableCreator(TypedArray typedArray, @AttrRes int i2) {
        this.typedArray = typedArray;
        this.gradientState = i2;
    }
}
