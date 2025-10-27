package cn.hutool.core.bean.copier;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.convert.TypeConverter;
import cn.hutool.core.lang.Editor;
import cn.hutool.core.lang.func.Func1;
import cn.hutool.core.lang.func.LambdaUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;

/* loaded from: classes.dex */
public class CopyOptions implements Serializable {
    private static final long serialVersionUID = 1;
    protected TypeConverter converter;
    protected Class<?> editable;
    private Editor<String> fieldNameEditor;
    protected BiFunction<String, Object, Object> fieldValueEditor;
    protected boolean ignoreCase;
    protected boolean ignoreError;
    private Set<String> ignoreKeySet;
    protected boolean ignoreNullValue;
    protected boolean override;
    private BiPredicate<Field, Object> propertiesFilter;
    protected boolean transientSupport;

    public CopyOptions() {
        this.transientSupport = true;
        this.override = true;
        this.converter = new TypeConverter() { // from class: cn.hutool.core.bean.copier.f
            @Override // cn.hutool.core.convert.TypeConverter
            public final Object convert(Type type, Object obj) {
                return this.f2383c.lambda$new$0(type, obj);
            }
        };
    }

    public static CopyOptions create() {
        return new CopyOptions();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Object lambda$new$0(Type type, Object obj) {
        if (obj == null) {
            return null;
        }
        return obj instanceof IJSONTypeConverter ? ((IJSONTypeConverter) obj).toBean((Type) ObjectUtil.defaultIfNull((Class) type, Object.class)) : Convert.convertWithCheck(type, obj, null, this.ignoreError);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$new$1(Field field, Object obj) {
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ String lambda$setFieldMapping$2(Map map, String str) {
        return (String) map.getOrDefault(str, str);
    }

    public Object convertField(Type type, Object obj) {
        TypeConverter typeConverter = this.converter;
        return typeConverter != null ? typeConverter.convert(type, obj) : obj;
    }

    public String editFieldName(String str) {
        Editor<String> editor = this.fieldNameEditor;
        return editor != null ? editor.edit(str) : str;
    }

    public Object editFieldValue(String str, Object obj) {
        BiFunction<String, Object, Object> biFunction = this.fieldValueEditor;
        return biFunction != null ? biFunction.apply(str, obj) : obj;
    }

    public CopyOptions ignoreCase() {
        return setIgnoreCase(true);
    }

    public CopyOptions ignoreError() {
        return setIgnoreError(true);
    }

    public CopyOptions ignoreNullValue() {
        return setIgnoreNullValue(true);
    }

    public CopyOptions setConverter(TypeConverter typeConverter) {
        this.converter = typeConverter;
        return this;
    }

    public CopyOptions setEditable(Class<?> cls) {
        this.editable = cls;
        return this;
    }

    public CopyOptions setFieldMapping(final Map<String, String> map) {
        return setFieldNameEditor(new Editor() { // from class: cn.hutool.core.bean.copier.e
            @Override // cn.hutool.core.lang.Editor
            public final Object edit(Object obj) {
                return CopyOptions.lambda$setFieldMapping$2(map, (String) obj);
            }
        });
    }

    public CopyOptions setFieldNameEditor(Editor<String> editor) {
        this.fieldNameEditor = editor;
        return this;
    }

    public CopyOptions setFieldValueEditor(BiFunction<String, Object, Object> biFunction) {
        this.fieldValueEditor = biFunction;
        return this;
    }

    public CopyOptions setIgnoreCase(boolean z2) {
        this.ignoreCase = z2;
        return this;
    }

    public CopyOptions setIgnoreError(boolean z2) {
        this.ignoreError = z2;
        return this;
    }

    public CopyOptions setIgnoreNullValue(boolean z2) {
        this.ignoreNullValue = z2;
        return this;
    }

    public CopyOptions setIgnoreProperties(String... strArr) {
        this.ignoreKeySet = CollUtil.newHashSet(strArr);
        return this;
    }

    public CopyOptions setOverride(boolean z2) {
        this.override = z2;
        return this;
    }

    public CopyOptions setPropertiesFilter(BiPredicate<Field, Object> biPredicate) {
        this.propertiesFilter = biPredicate;
        return this;
    }

    public CopyOptions setTransientSupport(boolean z2) {
        this.transientSupport = z2;
        return this;
    }

    public boolean testKeyFilter(Object obj) {
        if (CollUtil.isEmpty((Collection<?>) this.ignoreKeySet)) {
            return true;
        }
        if (this.ignoreCase) {
            Iterator<String> it = this.ignoreKeySet.iterator();
            while (it.hasNext()) {
                if (CharSequenceUtil.equalsIgnoreCase(obj.toString(), it.next())) {
                    return false;
                }
            }
        }
        return !this.ignoreKeySet.contains(obj);
    }

    public boolean testPropertyFilter(Field field, Object obj) {
        BiPredicate<Field, Object> biPredicate = this.propertiesFilter;
        return biPredicate == null || biPredicate.test(field, obj);
    }

    public static CopyOptions create(Class<?> cls, boolean z2, String... strArr) {
        return new CopyOptions(cls, z2, strArr);
    }

    public <P, R> CopyOptions setIgnoreProperties(Func1<P, R>... func1Arr) {
        this.ignoreKeySet = ArrayUtil.mapToSet(func1Arr, new Function() { // from class: cn.hutool.core.bean.copier.g
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return LambdaUtil.getFieldName((Func1) obj);
            }
        });
        return this;
    }

    public CopyOptions(Class<?> cls, boolean z2, String... strArr) {
        this.transientSupport = true;
        this.override = true;
        this.converter = new TypeConverter() { // from class: cn.hutool.core.bean.copier.f
            @Override // cn.hutool.core.convert.TypeConverter
            public final Object convert(Type type, Object obj) {
                return this.f2383c.lambda$new$0(type, obj);
            }
        };
        this.propertiesFilter = new BiPredicate() { // from class: cn.hutool.core.bean.copier.h
            @Override // java.util.function.BiPredicate
            public final boolean test(Object obj, Object obj2) {
                return CopyOptions.lambda$new$1((Field) obj, obj2);
            }
        };
        this.editable = cls;
        this.ignoreNullValue = z2;
        setIgnoreProperties(strArr);
    }
}
