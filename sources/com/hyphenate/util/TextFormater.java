package com.hyphenate.util;

import android.content.Context;
import cn.hutool.core.text.CharPool;
import com.yikaobang.yixue.R2;
import java.text.DecimalFormat;
import org.eclipse.jetty.http.HttpHeaderValues;

/* loaded from: classes4.dex */
public class TextFormater {
    private static final int GB_SP_DIFF = 160;
    private static final int[] secPosvalueList = {1601, R2.attr.gou, R2.attr.ic_personal_card_ticket, R2.attr.isDeletable, R2.attr.layout_tab_height, R2.attr.lineHeight, R2.attr.materialCalendarHeaderTitle, R2.attr.newhot4, R2.attr.placeholder, R2.attr.seekBarIncrement, R2.attr.sliderTouchMode, R2.attr.tab_badge_offset_x, R2.attr.textAppearanceSearchResultSubtitle, R2.attr.ticksColorInactive, R2.attr.tikujia, R2.attr.trimMode, R2.attr.zx_color_card3_end_color, R2.color.N_holidayTextColor, R2.color.c_aecae9, R2.color.color_ef4d3f, R2.color.correct_font, R2.color.header, R2.color.m3_sys_color_dynamic_dark_on_primary, R2.color.pickerview_wheelview_textcolor_center};
    private static final char[] firstLetter = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'w', 'x', 'y', 'z'};

    private static char convert(byte[] bArr) {
        for (int i2 = 0; i2 < bArr.length; i2++) {
            bArr[i2] = (byte) (bArr[i2] - 160);
        }
        int i3 = (bArr[0] * 100) + bArr[1];
        for (int i4 = 0; i4 < 23; i4++) {
            int[] iArr = secPosvalueList;
            if (i3 >= iArr[i4] && i3 < iArr[i4 + 1]) {
                return firstLetter[i4];
            }
        }
        return CharPool.DASHED;
    }

    public static String formatStr(Context context, int i2, String str) {
        return String.format(context.getText(i2).toString(), str);
    }

    public static String getDataSize(long j2) {
        DecimalFormat decimalFormat = new DecimalFormat("###.00");
        if (j2 < 0) {
            return "error";
        }
        if (j2 < 1024) {
            return j2 + HttpHeaderValues.BYTES;
        }
        if (j2 < 1048576) {
            return decimalFormat.format(j2 / 1024.0f) + "KB";
        }
        if (j2 < 1073741824) {
            return decimalFormat.format((j2 / 1024.0f) / 1024.0f) + "MB";
        }
        return decimalFormat.format(((j2 / 1024.0f) / 1024.0f) / 1024.0f) + "GB";
    }

    public static String getFirstLetter(String str) {
        String lowerCase = str.toLowerCase();
        StringBuilder sb = new StringBuilder();
        for (int i2 = 0; i2 < lowerCase.length(); i2++) {
            char[] cArr = {lowerCase.charAt(i2)};
            byte[] bytes = new String(cArr).getBytes();
            byte b3 = bytes[0];
            if (b3 >= 128 || b3 <= 0) {
                sb.append(convert(bytes));
            } else {
                sb.append(cArr);
            }
        }
        return sb.toString().substring(0, 1);
    }

    public static String getKBDataSize(long j2) {
        DecimalFormat decimalFormat = new DecimalFormat("###.00");
        if (j2 < 1024) {
            return j2 + "KB";
        }
        if (j2 < 1048576) {
            return decimalFormat.format(j2 / 1024.0f) + "MB";
        }
        if (j2 >= 1073741824) {
            return "error";
        }
        return decimalFormat.format((j2 / 1024.0f) / 1024.0f) + "GB";
    }
}
