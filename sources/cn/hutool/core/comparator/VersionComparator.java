package cn.hutool.core.comparator;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.PatternPool;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReUtil;
import java.io.Serializable;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;

/* loaded from: classes.dex */
public class VersionComparator implements Comparator<String>, Serializable {
    public static final VersionComparator INSTANCE = new VersionComparator();
    private static final long serialVersionUID = 8083701245147495562L;

    @Override // java.util.Comparator
    public int compare(String str, String str2) {
        if (ObjectUtil.equal(str, str2)) {
            return 0;
        }
        if (str == null && str2 == null) {
            return 0;
        }
        if (str == null) {
            return -1;
        }
        if (str2 == null) {
            return 1;
        }
        List<String> listSplit = CharSequenceUtil.split((CharSequence) str, '.');
        List<String> listSplit2 = CharSequenceUtil.split((CharSequence) str2, '.');
        int iMin = Math.min(listSplit.size(), listSplit2.size());
        int iIntValue = 0;
        for (int i2 = 0; i2 < iMin; i2++) {
            String str3 = listSplit.get(i2);
            String str4 = listSplit2.get(i2);
            int length = str3.length() - str4.length();
            if (length == 0) {
                iIntValue = str3.compareTo(str4);
            } else {
                Pattern pattern = PatternPool.NUMBERS;
                iIntValue = Convert.toInt(ReUtil.get(pattern, str3, 0), 0).intValue() - Convert.toInt(ReUtil.get(pattern, str4, 0), 0).intValue();
                if (iIntValue == 0) {
                    iIntValue = length;
                }
            }
            if (iIntValue != 0) {
                break;
            }
        }
        return iIntValue != 0 ? iIntValue : listSplit.size() - listSplit2.size();
    }
}
