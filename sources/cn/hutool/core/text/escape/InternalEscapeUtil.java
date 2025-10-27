package cn.hutool.core.text.escape;

import java.lang.reflect.Array;

/* loaded from: classes.dex */
class InternalEscapeUtil {
    public static String[][] invert(String[][] strArr) {
        String[][] strArr2 = (String[][]) Array.newInstance((Class<?>) String.class, strArr.length, 2);
        for (int i2 = 0; i2 < strArr.length; i2++) {
            strArr2[i2][0] = strArr[i2][1];
            strArr2[i2][1] = strArr[i2][0];
        }
        return strArr2;
    }
}
