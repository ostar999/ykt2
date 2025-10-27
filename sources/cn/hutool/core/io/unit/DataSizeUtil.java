package cn.hutool.core.io.unit;

import java.text.DecimalFormat;

/* loaded from: classes.dex */
public class DataSizeUtil {
    public static String format(long j2) {
        if (j2 <= 0) {
            return "0";
        }
        String[] strArr = DataUnit.UNIT_NAMES;
        double d3 = j2;
        int iMin = Math.min(strArr.length - 1, (int) (Math.log10(d3) / Math.log10(1024.0d)));
        return new DecimalFormat("#,##0.##").format(d3 / Math.pow(1024.0d, iMin)) + " " + strArr[iMin];
    }

    public static long parse(String str) {
        return DataSize.parse(str).toBytes();
    }
}
