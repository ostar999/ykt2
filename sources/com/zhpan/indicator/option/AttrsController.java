package com.zhpan.indicator.option;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.zhpan.indicator.R;
import com.zhpan.indicator.utils.IndicatorUtils;

/* loaded from: classes8.dex */
public class AttrsController {
    public static void initAttrs(@NonNull Context context, @Nullable AttributeSet attributeSet, IndicatorOptions indicatorOptions) {
        if (attributeSet != null) {
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.IndicatorView);
            int i2 = typedArrayObtainStyledAttributes.getInt(R.styleable.IndicatorView_vpi_slide_mode, 0);
            int i3 = typedArrayObtainStyledAttributes.getInt(R.styleable.IndicatorView_vpi_style, 0);
            int color = typedArrayObtainStyledAttributes.getColor(R.styleable.IndicatorView_vpi_slider_checked_color, Color.parseColor("#6C6D72"));
            int color2 = typedArrayObtainStyledAttributes.getColor(R.styleable.IndicatorView_vpi_slider_normal_color, Color.parseColor("#8C18171C"));
            int i4 = typedArrayObtainStyledAttributes.getInt(R.styleable.IndicatorView_vpi_orientation, 0);
            float dimension = typedArrayObtainStyledAttributes.getDimension(R.styleable.IndicatorView_vpi_slider_radius, IndicatorUtils.dp2px(8.0f));
            indicatorOptions.setCheckedColor(color);
            indicatorOptions.setNormalSliderColor(color2);
            indicatorOptions.setOrientation(i4);
            indicatorOptions.setIndicatorStyle(i3);
            indicatorOptions.setSlideMode(i2);
            float f2 = dimension * 2.0f;
            indicatorOptions.setSliderWidth(f2, f2);
            typedArrayObtainStyledAttributes.recycle();
        }
    }
}
