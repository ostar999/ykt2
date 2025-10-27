package org.slf4j.helpers;

import cn.hutool.core.text.StrPool;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes9.dex */
public final class MessageFormatter {
    static final char DELIM_START = '{';
    static final char DELIM_STOP = '}';
    static final String DELIM_STR = "{}";
    private static final char ESCAPE_CHAR = '\\';

    public static final FormattingTuple arrayFormat(String str, Object[] objArr) {
        Throwable throwableCandidate = getThrowableCandidate(objArr);
        if (throwableCandidate != null) {
            objArr = trimmedCopy(objArr);
        }
        return arrayFormat(str, objArr, throwableCandidate);
    }

    private static void booleanArrayAppend(StringBuilder sb, boolean[] zArr) {
        sb.append('[');
        int length = zArr.length;
        for (int i2 = 0; i2 < length; i2++) {
            sb.append(zArr[i2]);
            if (i2 != length - 1) {
                sb.append(", ");
            }
        }
        sb.append(']');
    }

    private static void byteArrayAppend(StringBuilder sb, byte[] bArr) {
        sb.append('[');
        int length = bArr.length;
        for (int i2 = 0; i2 < length; i2++) {
            sb.append((int) bArr[i2]);
            if (i2 != length - 1) {
                sb.append(", ");
            }
        }
        sb.append(']');
    }

    private static void charArrayAppend(StringBuilder sb, char[] cArr) {
        sb.append('[');
        int length = cArr.length;
        for (int i2 = 0; i2 < length; i2++) {
            sb.append(cArr[i2]);
            if (i2 != length - 1) {
                sb.append(", ");
            }
        }
        sb.append(']');
    }

    private static void deeplyAppendParameter(StringBuilder sb, Object obj, Map<Object[], Object> map) {
        if (obj == null) {
            sb.append("null");
            return;
        }
        if (!obj.getClass().isArray()) {
            safeObjectAppend(sb, obj);
            return;
        }
        if (obj instanceof boolean[]) {
            booleanArrayAppend(sb, (boolean[]) obj);
            return;
        }
        if (obj instanceof byte[]) {
            byteArrayAppend(sb, (byte[]) obj);
            return;
        }
        if (obj instanceof char[]) {
            charArrayAppend(sb, (char[]) obj);
            return;
        }
        if (obj instanceof short[]) {
            shortArrayAppend(sb, (short[]) obj);
            return;
        }
        if (obj instanceof int[]) {
            intArrayAppend(sb, (int[]) obj);
            return;
        }
        if (obj instanceof long[]) {
            longArrayAppend(sb, (long[]) obj);
            return;
        }
        if (obj instanceof float[]) {
            floatArrayAppend(sb, (float[]) obj);
        } else if (obj instanceof double[]) {
            doubleArrayAppend(sb, (double[]) obj);
        } else {
            objectArrayAppend(sb, (Object[]) obj, map);
        }
    }

    private static void doubleArrayAppend(StringBuilder sb, double[] dArr) {
        sb.append('[');
        int length = dArr.length;
        for (int i2 = 0; i2 < length; i2++) {
            sb.append(dArr[i2]);
            if (i2 != length - 1) {
                sb.append(", ");
            }
        }
        sb.append(']');
    }

    private static void floatArrayAppend(StringBuilder sb, float[] fArr) {
        sb.append('[');
        int length = fArr.length;
        for (int i2 = 0; i2 < length; i2++) {
            sb.append(fArr[i2]);
            if (i2 != length - 1) {
                sb.append(", ");
            }
        }
        sb.append(']');
    }

    public static final FormattingTuple format(String str, Object obj) {
        return arrayFormat(str, new Object[]{obj});
    }

    public static final Throwable getThrowableCandidate(Object[] objArr) {
        if (objArr != null && objArr.length != 0) {
            Object obj = objArr[objArr.length - 1];
            if (obj instanceof Throwable) {
                return (Throwable) obj;
            }
        }
        return null;
    }

    private static void intArrayAppend(StringBuilder sb, int[] iArr) {
        sb.append('[');
        int length = iArr.length;
        for (int i2 = 0; i2 < length; i2++) {
            sb.append(iArr[i2]);
            if (i2 != length - 1) {
                sb.append(", ");
            }
        }
        sb.append(']');
    }

    public static final boolean isDoubleEscaped(String str, int i2) {
        return i2 >= 2 && str.charAt(i2 - 2) == '\\';
    }

    public static final boolean isEscapedDelimeter(String str, int i2) {
        return i2 != 0 && str.charAt(i2 - 1) == '\\';
    }

    private static void longArrayAppend(StringBuilder sb, long[] jArr) {
        sb.append('[');
        int length = jArr.length;
        for (int i2 = 0; i2 < length; i2++) {
            sb.append(jArr[i2]);
            if (i2 != length - 1) {
                sb.append(", ");
            }
        }
        sb.append(']');
    }

    private static void objectArrayAppend(StringBuilder sb, Object[] objArr, Map<Object[], Object> map) {
        sb.append('[');
        if (map.containsKey(objArr)) {
            sb.append("...");
        } else {
            map.put(objArr, null);
            int length = objArr.length;
            for (int i2 = 0; i2 < length; i2++) {
                deeplyAppendParameter(sb, objArr[i2], map);
                if (i2 != length - 1) {
                    sb.append(", ");
                }
            }
            map.remove(objArr);
        }
        sb.append(']');
    }

    private static void safeObjectAppend(StringBuilder sb, Object obj) {
        try {
            sb.append(obj.toString());
        } catch (Throwable th) {
            Util.report("SLF4J: Failed toString() invocation on an object of type [" + obj.getClass().getName() + StrPool.BRACKET_END, th);
            sb.append("[FAILED toString()]");
        }
    }

    private static void shortArrayAppend(StringBuilder sb, short[] sArr) {
        sb.append('[');
        int length = sArr.length;
        for (int i2 = 0; i2 < length; i2++) {
            sb.append((int) sArr[i2]);
            if (i2 != length - 1) {
                sb.append(", ");
            }
        }
        sb.append(']');
    }

    private static Object[] trimmedCopy(Object[] objArr) {
        if (objArr == null || objArr.length == 0) {
            throw new IllegalStateException("non-sensical empty or null argument array");
        }
        int length = objArr.length - 1;
        Object[] objArr2 = new Object[length];
        System.arraycopy(objArr, 0, objArr2, 0, length);
        return objArr2;
    }

    public static final FormattingTuple format(String str, Object obj, Object obj2) {
        return arrayFormat(str, new Object[]{obj, obj2});
    }

    public static final FormattingTuple arrayFormat(String str, Object[] objArr, Throwable th) {
        int i2;
        if (str == null) {
            return new FormattingTuple(null, objArr, th);
        }
        if (objArr == null) {
            return new FormattingTuple(str);
        }
        StringBuilder sb = new StringBuilder(str.length() + 50);
        int i3 = 0;
        int i4 = 0;
        while (i3 < objArr.length) {
            int iIndexOf = str.indexOf("{}", i4);
            if (iIndexOf == -1) {
                if (i4 == 0) {
                    return new FormattingTuple(str, objArr, th);
                }
                sb.append((CharSequence) str, i4, str.length());
                return new FormattingTuple(sb.toString(), objArr, th);
            }
            if (isEscapedDelimeter(str, iIndexOf)) {
                if (!isDoubleEscaped(str, iIndexOf)) {
                    i3--;
                    sb.append((CharSequence) str, i4, iIndexOf - 1);
                    sb.append('{');
                    i2 = iIndexOf + 1;
                    i4 = i2;
                    i3++;
                } else {
                    sb.append((CharSequence) str, i4, iIndexOf - 1);
                    deeplyAppendParameter(sb, objArr[i3], new HashMap());
                }
            } else {
                sb.append((CharSequence) str, i4, iIndexOf);
                deeplyAppendParameter(sb, objArr[i3], new HashMap());
            }
            i2 = iIndexOf + 2;
            i4 = i2;
            i3++;
        }
        sb.append((CharSequence) str, i4, str.length());
        return new FormattingTuple(sb.toString(), objArr, th);
    }
}
