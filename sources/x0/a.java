package x0;

import com.hjq.http.config.ILogStrategy;

/* loaded from: classes4.dex */
public final /* synthetic */ class a {
    public static void a(ILogStrategy iLogStrategy) {
        iLogStrategy.print("--------------------");
    }

    public static String b(String str) {
        if (str == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        int length = str.length();
        int i2 = 0;
        int i3 = 0;
        char c3 = 0;
        while (i2 < length) {
            char cCharAt = str.charAt(i2);
            if (cCharAt == '{') {
                i3++;
                sb.append(cCharAt);
                sb.append("\n");
                sb.append(c(i3));
            } else if (cCharAt == '}') {
                i3--;
                sb.append("\n");
                sb.append(c(i3));
                sb.append(cCharAt);
            } else if (cCharAt == ',') {
                int iLastIndexOf = str.lastIndexOf(":", i2);
                if (iLastIndexOf == -1 || str.lastIndexOf(":\"", i2) != iLastIndexOf || str.charAt(i2 + (-1)) == '\"') {
                    sb.append(cCharAt);
                    sb.append("\n");
                    sb.append(c(i3));
                } else {
                    sb.append(cCharAt);
                }
            } else if (cCharAt == ':') {
                if (i2 <= 0 || str.charAt(i2 - 1) != '\"') {
                    sb.append(cCharAt);
                } else {
                    sb.append(" ");
                    sb.append(cCharAt);
                    sb.append(" ");
                }
            } else if (cCharAt == '[') {
                i3++;
                if (str.charAt(i2 + 1) == ']') {
                    sb.append(cCharAt);
                } else {
                    sb.append(cCharAt);
                    sb.append("\n");
                    sb.append(c(i3));
                }
            } else if (cCharAt == ']') {
                i3--;
                if (c3 == '[') {
                    sb.append(cCharAt);
                } else {
                    sb.append("\n");
                    sb.append(c(i3));
                    sb.append(cCharAt);
                }
            } else {
                sb.append(cCharAt);
            }
            i2++;
            c3 = cCharAt;
        }
        return sb.toString();
    }

    public static String c(int i2) {
        StringBuilder sb = new StringBuilder();
        for (int i3 = 0; i3 < i2; i3++) {
            sb.append('\t');
        }
        return sb.toString();
    }
}
