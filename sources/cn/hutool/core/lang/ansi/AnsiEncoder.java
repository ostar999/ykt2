package cn.hutool.core.lang.ansi;

/* loaded from: classes.dex */
public abstract class AnsiEncoder {
    private static final String ENCODE_END = "m";
    private static final String ENCODE_JOIN = ";";
    private static final String ENCODE_START = "\u001b[";
    private static final String RESET = "0;" + AnsiColor.DEFAULT;

    private static void buildEnabled(StringBuilder sb, Object[] objArr) {
        int length = objArr.length;
        int i2 = 0;
        boolean z2 = false;
        boolean z3 = false;
        while (true) {
            if (i2 >= length) {
                break;
            }
            Object obj = objArr[i2];
            if (obj != null) {
                if (obj instanceof AnsiElement) {
                    z2 = true;
                    if (z3) {
                        sb.append(";");
                    } else {
                        sb.append(ENCODE_START);
                        z3 = true;
                    }
                } else if (z3) {
                    sb.append(ENCODE_END);
                    z3 = false;
                }
                sb.append(obj);
            }
            i2++;
        }
        if (z2) {
            sb.append(z3 ? ";" : ENCODE_START);
            sb.append(RESET);
            sb.append(ENCODE_END);
        }
    }

    public static String encode(Object... objArr) {
        StringBuilder sb = new StringBuilder();
        buildEnabled(sb, objArr);
        return sb.toString();
    }
}
