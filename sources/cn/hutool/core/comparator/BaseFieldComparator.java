package cn.hutool.core.comparator;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Comparator;

@Deprecated
/* loaded from: classes.dex */
public abstract class BaseFieldComparator<T> implements Comparator<T>, Serializable {
    private static final long serialVersionUID = -3482464782340308755L;

    private int compare(T t2, T t3, Comparable comparable, Comparable comparable2) {
        int iCompare = ObjectUtil.compare(comparable, comparable2);
        return iCompare == 0 ? CompareUtil.compare((Object) t2, (Object) t3, true) : iCompare;
    }

    public int compareItem(T t2, T t3, Field field) {
        if (t2 == t3) {
            return 0;
        }
        if (t2 == null) {
            return 1;
        }
        if (t3 == null) {
            return -1;
        }
        try {
            return compare(t2, t3, (Comparable) ReflectUtil.getFieldValue(t2, field), (Comparable) ReflectUtil.getFieldValue(t3, field));
        } catch (Exception e2) {
            throw new ComparatorException(e2);
        }
    }
}
