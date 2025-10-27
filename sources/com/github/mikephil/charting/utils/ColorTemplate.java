package com.github.mikephil.charting.utils;

import android.content.res.Resources;
import android.graphics.Color;
import com.github.liuyueyi.quick.transfer.dictionary.DictionaryFactory;
import com.yikaobang.yixue.R2;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class ColorTemplate {
    public static final int COLOR_NONE = 1122867;
    public static final int COLOR_SKIP = 1122868;
    public static final int[] LIBERTY_COLORS = {Color.rgb(207, R2.attr.actionModeFindDrawable, R2.attr.actionModeCopyDrawable), Color.rgb(148, 212, 212), Color.rgb(136, 180, 187), Color.rgb(118, R2.anim.window_ios_in, R2.anim.window_ios_out), Color.rgb(42, 109, 130)};
    public static final int[] JOYFUL_COLORS = {Color.rgb(217, 80, 138), Color.rgb(254, 149, 7), Color.rgb(254, R2.attr.actionModeCutDrawable, 120), Color.rgb(106, 167, 134), Color.rgb(53, R2.array.ease_numbers_file_suffix, 209)};
    public static final int[] PASTEL_COLORS = {Color.rgb(64, 89, 128), Color.rgb(149, 165, 124), Color.rgb(217, 184, 162), Color.rgb(R2.array.ease_file_file_suffix, 134, 134), Color.rgb(179, 48, 80)};
    public static final int[] COLORFUL_COLORS = {Color.rgb(193, 37, 82), Color.rgb(255, 102, 0), Color.rgb(R2.attr.actionModeCloseDrawable, 199, 0), Color.rgb(106, 150, 31), Color.rgb(179, 100, 53)};
    public static final int[] VORDIPLOM_COLORS = {Color.rgb(192, 255, 140), Color.rgb(255, R2.attr.actionModeCutDrawable, 140), Color.rgb(255, 208, 140), Color.rgb(140, 234, 255), Color.rgb(255, 140, 157)};
    public static final int[] MATERIAL_COLORS = {rgb("#2ecc71"), rgb("#f1c40f"), rgb("#e74c3c"), rgb("#3498db")};

    public static int colorWithAlpha(int i2, int i3) {
        return (i2 & 16777215) | ((i3 & 255) << 24);
    }

    public static List<Integer> createColors(Resources resources, int[] iArr) {
        ArrayList arrayList = new ArrayList();
        for (int i2 : iArr) {
            arrayList.add(Integer.valueOf(resources.getColor(i2)));
        }
        return arrayList;
    }

    public static int getHoloBlue() {
        return Color.rgb(51, 181, 229);
    }

    public static int rgb(String str) {
        int i2 = (int) Long.parseLong(str.replace(DictionaryFactory.SHARP, ""), 16);
        return Color.rgb((i2 >> 16) & 255, (i2 >> 8) & 255, (i2 >> 0) & 255);
    }

    public static List<Integer> createColors(int[] iArr) {
        ArrayList arrayList = new ArrayList();
        for (int i2 : iArr) {
            arrayList.add(Integer.valueOf(i2));
        }
        return arrayList;
    }
}
