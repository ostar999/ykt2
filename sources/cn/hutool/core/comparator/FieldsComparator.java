package cn.hutool.core.comparator;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ClassUtil;
import java.lang.reflect.Field;
import java.util.Comparator;

/* loaded from: classes.dex */
public class FieldsComparator<T> extends NullComparator<T> {
    private static final long serialVersionUID = 8649196282886500803L;

    public FieldsComparator(Class<T> cls, String... strArr) {
        this(true, cls, strArr);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ int lambda$new$0(String[] strArr, Class cls, Object obj, Object obj2) throws SecurityException, IllegalArgumentException {
        for (String str : strArr) {
            Field declaredField = ClassUtil.getDeclaredField(cls, str);
            Assert.notNull(declaredField, "Field [{}] not found in Class [{}]", str, cls.getName());
            int iCompare = new FieldComparator(true, false, declaredField).compare(obj, obj2);
            if (iCompare != 0) {
                return iCompare;
            }
        }
        return 0;
    }

    public FieldsComparator(boolean z2, final Class<T> cls, final String... strArr) {
        super(z2, new Comparator() { // from class: cn.hutool.core.comparator.e
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return FieldsComparator.lambda$new$0(strArr, cls, obj, obj2);
            }
        });
    }
}
