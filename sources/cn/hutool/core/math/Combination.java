package cn.hutool.core.math;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.NumberUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes.dex */
public class Combination implements Serializable {
    private static final long serialVersionUID = 1;
    private final String[] datas;

    public Combination(String[] strArr) {
        this.datas = strArr;
    }

    public static long count(int i2, int i3) {
        if (i3 == 0 || i2 == i3) {
            return 1L;
        }
        if (i2 > i3) {
            return NumberUtil.factorial(i2, i2 - i3) / NumberUtil.factorial(i3);
        }
        return 0L;
    }

    public static long countAll(int i2) {
        if (i2 < 0 || i2 > 63) {
            throw new IllegalArgumentException(CharSequenceUtil.format("countAll must have n >= 0 and n <= 63, but got n={}", Integer.valueOf(i2)));
        }
        if (i2 == 63) {
            return Long.MAX_VALUE;
        }
        return (1 << i2) - 1;
    }

    public List<String[]> select(int i2) {
        ArrayList arrayList = new ArrayList((int) count(this.datas.length, i2));
        select(0, new String[i2], 0, arrayList);
        return arrayList;
    }

    public List<String[]> selectAll() {
        ArrayList arrayList = new ArrayList((int) countAll(this.datas.length));
        for (int i2 = 1; i2 <= this.datas.length; i2++) {
            arrayList.addAll(select(i2));
        }
        return arrayList;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void select(int i2, String[] strArr, int i3, List<String[]> list) {
        int length = strArr.length;
        int i4 = i3 + 1;
        if (i4 > length) {
            list.add(Arrays.copyOf(strArr, strArr.length));
            return;
        }
        while (true) {
            String[] strArr2 = this.datas;
            if (i2 >= (strArr2.length + i4) - length) {
                return;
            }
            strArr[i3] = strArr2[i2];
            i2++;
            select(i2, strArr, i4, list);
        }
    }
}
