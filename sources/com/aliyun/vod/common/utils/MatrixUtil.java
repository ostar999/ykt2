package com.aliyun.vod.common.utils;

import android.graphics.Matrix;

/* loaded from: classes2.dex */
public class MatrixUtil {
    public static float[] getTransform(Matrix matrix) {
        float[] fArr = new float[9];
        matrix.getValues(fArr);
        return fArr;
    }
}
