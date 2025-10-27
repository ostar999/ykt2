package com.psychiatrygarden.widget.glideUtil.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import androidx.annotation.RequiresApi;
import androidx.core.view.MotionEventCompat;
import java.lang.reflect.Array;

/* loaded from: classes6.dex */
public class BlurUtils {
    public static Bitmap blur(Bitmap toTransform, int radius) {
        int[] iArr;
        int i2 = radius;
        if (i2 < 1) {
            return null;
        }
        int width = toTransform.getWidth();
        int height = toTransform.getHeight();
        int i3 = width * height;
        int[] iArr2 = new int[i3];
        toTransform.getPixels(iArr2, 0, width, 0, 0, width, height);
        int i4 = width - 1;
        int i5 = height - 1;
        int i6 = i2 + i2 + 1;
        int[] iArr3 = new int[i3];
        int[] iArr4 = new int[i3];
        int[] iArr5 = new int[i3];
        int[] iArr6 = new int[Math.max(width, height)];
        int i7 = (i6 + 1) >> 1;
        int i8 = i7 * i7;
        int i9 = i8 * 256;
        int[] iArr7 = new int[i9];
        for (int i10 = 0; i10 < i9; i10++) {
            iArr7[i10] = i10 / i8;
        }
        int[][] iArr8 = (int[][]) Array.newInstance((Class<?>) Integer.TYPE, i6, 3);
        int i11 = i2 + 1;
        int i12 = 0;
        int i13 = 0;
        int i14 = 0;
        while (i12 < height) {
            int i15 = -i2;
            int i16 = 0;
            int i17 = 0;
            int i18 = 0;
            int i19 = 0;
            int i20 = 0;
            int i21 = 0;
            int i22 = 0;
            int i23 = 0;
            int i24 = 0;
            while (i15 <= i2) {
                int i25 = i5;
                int i26 = height;
                int i27 = iArr2[i13 + Math.min(i4, Math.max(i15, 0))];
                int[] iArr9 = iArr8[i15 + i2];
                iArr9[0] = (i27 & 16711680) >> 16;
                iArr9[1] = (i27 & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8;
                iArr9[2] = i27 & 255;
                int iAbs = i11 - Math.abs(i15);
                int i28 = iArr9[0];
                i16 += i28 * iAbs;
                int i29 = iArr9[1];
                i17 += i29 * iAbs;
                int i30 = iArr9[2];
                i18 += iAbs * i30;
                if (i15 > 0) {
                    i22 += i28;
                    i23 += i29;
                    i24 += i30;
                } else {
                    i19 += i28;
                    i20 += i29;
                    i21 += i30;
                }
                i15++;
                height = i26;
                i5 = i25;
            }
            int i31 = i5;
            int i32 = height;
            int i33 = i2;
            int i34 = 0;
            while (i34 < width) {
                iArr3[i13] = iArr7[i16];
                iArr4[i13] = iArr7[i17];
                iArr5[i13] = iArr7[i18];
                int i35 = i16 - i19;
                int i36 = i17 - i20;
                int i37 = i18 - i21;
                int[] iArr10 = iArr8[((i33 - i2) + i6) % i6];
                int i38 = i19 - iArr10[0];
                int i39 = i20 - iArr10[1];
                int i40 = i21 - iArr10[2];
                if (i12 == 0) {
                    iArr = iArr7;
                    iArr6[i34] = Math.min(i34 + i2 + 1, i4);
                } else {
                    iArr = iArr7;
                }
                int i41 = iArr2[i14 + iArr6[i34]];
                int i42 = (i41 & 16711680) >> 16;
                iArr10[0] = i42;
                int i43 = (i41 & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8;
                iArr10[1] = i43;
                int i44 = i41 & 255;
                iArr10[2] = i44;
                int i45 = i22 + i42;
                int i46 = i23 + i43;
                int i47 = i24 + i44;
                i16 = i35 + i45;
                i17 = i36 + i46;
                i18 = i37 + i47;
                i33 = (i33 + 1) % i6;
                int[] iArr11 = iArr8[i33 % i6];
                int i48 = iArr11[0];
                i19 = i38 + i48;
                int i49 = iArr11[1];
                i20 = i39 + i49;
                int i50 = iArr11[2];
                i21 = i40 + i50;
                i22 = i45 - i48;
                i23 = i46 - i49;
                i24 = i47 - i50;
                i13++;
                i34++;
                iArr7 = iArr;
            }
            i14 += width;
            i12++;
            height = i32;
            i5 = i31;
        }
        int i51 = i5;
        int i52 = height;
        int[] iArr12 = iArr7;
        int i53 = 0;
        while (i53 < width) {
            int i54 = -i2;
            int i55 = i54 * width;
            int i56 = 0;
            int i57 = 0;
            int i58 = 0;
            int i59 = 0;
            int i60 = 0;
            int i61 = 0;
            int i62 = 0;
            int i63 = 0;
            int i64 = 0;
            while (i54 <= i2) {
                int[] iArr13 = iArr6;
                int iMax = Math.max(0, i55) + i53;
                int[] iArr14 = iArr8[i54 + i2];
                iArr14[0] = iArr3[iMax];
                iArr14[1] = iArr4[iMax];
                iArr14[2] = iArr5[iMax];
                int iAbs2 = i11 - Math.abs(i54);
                i56 += iArr3[iMax] * iAbs2;
                i57 += iArr4[iMax] * iAbs2;
                i58 += iArr5[iMax] * iAbs2;
                if (i54 > 0) {
                    i62 += iArr14[0];
                    i63 += iArr14[1];
                    i64 += iArr14[2];
                } else {
                    i59 += iArr14[0];
                    i60 += iArr14[1];
                    i61 += iArr14[2];
                }
                int i65 = i51;
                if (i54 < i65) {
                    i55 += width;
                }
                i54++;
                i51 = i65;
                iArr6 = iArr13;
            }
            int[] iArr15 = iArr6;
            int i66 = i51;
            int i67 = i2;
            int i68 = i53;
            int i69 = i52;
            int i70 = 0;
            while (i70 < i69) {
                iArr2[i68] = (iArr2[i68] & (-16777216)) | (iArr12[i56] << 16) | (iArr12[i57] << 8) | iArr12[i58];
                int i71 = i56 - i59;
                int i72 = i57 - i60;
                int i73 = i58 - i61;
                int[] iArr16 = iArr8[((i67 - i2) + i6) % i6];
                int i74 = i59 - iArr16[0];
                int i75 = i60 - iArr16[1];
                int i76 = i61 - iArr16[2];
                if (i53 == 0) {
                    iArr15[i70] = Math.min(i70 + i11, i66) * width;
                }
                int i77 = iArr15[i70] + i53;
                int i78 = iArr3[i77];
                iArr16[0] = i78;
                int i79 = iArr4[i77];
                iArr16[1] = i79;
                int i80 = iArr5[i77];
                iArr16[2] = i80;
                int i81 = i62 + i78;
                int i82 = i63 + i79;
                int i83 = i64 + i80;
                i56 = i71 + i81;
                i57 = i72 + i82;
                i58 = i73 + i83;
                i67 = (i67 + 1) % i6;
                int[] iArr17 = iArr8[i67];
                int i84 = iArr17[0];
                i59 = i74 + i84;
                int i85 = iArr17[1];
                i60 = i75 + i85;
                int i86 = iArr17[2];
                i61 = i76 + i86;
                i62 = i81 - i84;
                i63 = i82 - i85;
                i64 = i83 - i86;
                i68 += width;
                i70++;
                i2 = radius;
            }
            i53++;
            i2 = radius;
            i52 = i69;
            i51 = i66;
            iArr6 = iArr15;
        }
        toTransform.setPixels(iArr2, 0, width, 0, 0, width, i52);
        return toTransform;
    }

    @RequiresApi(api = 17)
    public static Bitmap rsBlur(Context context, Bitmap toTransform, int radius) {
        RenderScript renderScriptCreate = RenderScript.create(context);
        Allocation allocationCreateFromBitmap = Allocation.createFromBitmap(renderScriptCreate, toTransform);
        Allocation allocationCreateTyped = Allocation.createTyped(renderScriptCreate, allocationCreateFromBitmap.getType());
        ScriptIntrinsicBlur scriptIntrinsicBlurCreate = ScriptIntrinsicBlur.create(renderScriptCreate, Element.U8_4(renderScriptCreate));
        scriptIntrinsicBlurCreate.setInput(allocationCreateFromBitmap);
        scriptIntrinsicBlurCreate.setRadius(radius);
        scriptIntrinsicBlurCreate.forEach(allocationCreateTyped);
        allocationCreateTyped.copyTo(toTransform);
        renderScriptCreate.destroy();
        return toTransform;
    }
}
