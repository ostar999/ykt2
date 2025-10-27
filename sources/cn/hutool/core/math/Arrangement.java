package cn.hutool.core.math;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.NumberUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes.dex */
public class Arrangement implements Serializable {
    private static final long serialVersionUID = 1;
    private final String[] datas;

    public Arrangement(String[] strArr) {
        this.datas = strArr;
    }

    public static long count(int i2) {
        return count(i2, i2);
    }

    public static long countAll(int i2) {
        long jCount = 0;
        for (int i3 = 1; i3 <= i2; i3++) {
            jCount += count(i2, i3);
        }
        return jCount;
    }

    public List<String[]> select() {
        return select(this.datas.length);
    }

    public List<String[]> selectAll() {
        ArrayList arrayList = new ArrayList((int) countAll(this.datas.length));
        for (int i2 = 1; i2 <= this.datas.length; i2++) {
            arrayList.addAll(select(i2));
        }
        return arrayList;
    }

    public static long count(int i2, int i3) {
        if (i2 == i3) {
            return NumberUtil.factorial(i2);
        }
        if (i2 > i3) {
            return NumberUtil.factorial(i2, i2 - i3);
        }
        return 0L;
    }

    public List<String[]> select(int i2) {
        ArrayList arrayList = new ArrayList((int) count(this.datas.length, i2));
        select(this.datas, new String[i2], 0, arrayList);
        return arrayList;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void select(String[] strArr, String[] strArr2, int i2, List<String[]> list) {
        if (i2 >= strArr2.length) {
            if (list.contains(strArr2)) {
                return;
            }
            list.add(Arrays.copyOf(strArr2, strArr2.length));
        } else {
            for (int i3 = 0; i3 < strArr.length; i3++) {
                strArr2[i2] = strArr[i3];
                select((String[]) ArrayUtil.remove((Object[]) strArr, i3), strArr2, i2 + 1, list);
            }
        }
    }
}
