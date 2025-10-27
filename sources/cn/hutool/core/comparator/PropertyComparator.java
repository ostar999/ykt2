package cn.hutool.core.comparator;

import cn.hutool.core.bean.BeanUtil;
import java.util.function.Function;

/* loaded from: classes.dex */
public class PropertyComparator<T> extends FuncComparator<T> {
    private static final long serialVersionUID = 9157326766723846313L;

    public PropertyComparator(String str) {
        this(str, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Comparable lambda$new$0(String str, Object obj) {
        return (Comparable) BeanUtil.getProperty(obj, str);
    }

    public PropertyComparator(final String str, boolean z2) {
        super(z2, new Function() { // from class: cn.hutool.core.comparator.g
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return PropertyComparator.lambda$new$0(str, obj);
            }
        });
    }
}
