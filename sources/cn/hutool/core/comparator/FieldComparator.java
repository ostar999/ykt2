package cn.hutool.core.comparator;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ReflectUtil;
import java.lang.reflect.Field;
import java.util.function.Function;

/* loaded from: classes.dex */
public class FieldComparator<T> extends FuncComparator<T> {
    private static final long serialVersionUID = 9157326766723846313L;

    public FieldComparator(Class<T> cls, String str) {
        this(getNonNullField(cls, str));
    }

    private static Field getNonNullField(Class<?> cls, String str) throws SecurityException {
        Field declaredField = ClassUtil.getDeclaredField(cls, str);
        if (declaredField != null) {
            return declaredField;
        }
        throw new IllegalArgumentException(CharSequenceUtil.format("Field [{}] not found in Class [{}]", str, cls.getName()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Comparable lambda$new$0(Field field, Object obj) {
        return (Comparable) ReflectUtil.getFieldValue(obj, (Field) Assert.notNull(field, "Field must be not null!", new Object[0]));
    }

    public FieldComparator(Field field) {
        this(true, true, field);
    }

    public FieldComparator(boolean z2, boolean z3, final Field field) {
        super(z2, z3, new Function() { // from class: cn.hutool.core.comparator.d
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return FieldComparator.lambda$new$0(field, obj);
            }
        });
    }
}
