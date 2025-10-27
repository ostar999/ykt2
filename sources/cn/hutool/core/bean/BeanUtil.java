package cn.hutool.core.bean;

import cn.hutool.core.bean.copier.BeanCopier;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.bean.copier.ValueProvider;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.exceptions.UtilException;
import cn.hutool.core.lang.Editor;
import cn.hutool.core.lang.Filter;
import cn.hutool.core.map.CaseInsensitiveMap;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ModifierUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.beans.PropertyEditor;
import java.beans.PropertyEditorManager;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/* loaded from: classes.dex */
public class BeanUtil {
    public static Map<String, Object> beanToMap(Object obj, String... strArr) {
        int length;
        Editor editor;
        if (ArrayUtil.isNotEmpty((Object[]) strArr)) {
            length = strArr.length;
            final HashSet hashSet = CollUtil.set(false, strArr);
            editor = new Editor() { // from class: cn.hutool.core.bean.h
                @Override // cn.hutool.core.lang.Editor
                public final Object edit(Object obj2) {
                    return BeanUtil.lambda$beanToMap$4(hashSet, (String) obj2);
                }
            };
        } else {
            length = 16;
            editor = null;
        }
        return beanToMap(obj, (Map<String, Object>) new LinkedHashMap(length, 1.0f), false, (Editor<String>) editor);
    }

    public static <T> T copyProperties(Object obj, Class<T> cls, String... strArr) {
        if (obj == null) {
            return null;
        }
        T t2 = (T) ReflectUtil.newInstanceIfPossible(cls);
        copyProperties(obj, t2, CopyOptions.create().setIgnoreProperties(strArr));
        return t2;
    }

    public static <T> List<T> copyToList(Collection<?> collection, final Class<T> cls, final CopyOptions copyOptions) {
        if (collection == null) {
            return null;
        }
        return collection.isEmpty() ? new ArrayList(0) : (ClassUtil.isBasicType(cls) || String.class == cls) ? Convert.toList(cls, collection) : (List) collection.stream().map(new Function() { // from class: cn.hutool.core.bean.n
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return BeanUtil.lambda$copyToList$6(cls, copyOptions, obj);
            }
        }).collect(Collectors.toList());
    }

    public static DynaBean createDynaBean(Object obj) {
        return new DynaBean(obj);
    }

    public static void descForEach(Class<?> cls, Consumer<? super PropDesc> consumer) {
        getBeanDesc(cls).getProps().forEach(consumer);
    }

    public static <T> T edit(T t2, Editor<Field> editor) throws SecurityException {
        if (t2 == null) {
            return null;
        }
        for (Field field : ReflectUtil.getFields(t2.getClass())) {
            if (!ModifierUtil.isStatic(field)) {
                editor.edit(field);
            }
        }
        return t2;
    }

    public static <T> T fillBean(T t2, ValueProvider<String> valueProvider, CopyOptions copyOptions) {
        return valueProvider == null ? t2 : (T) BeanCopier.create(valueProvider, t2, copyOptions).copy();
    }

    public static <T> T fillBeanWithMap(Map<?, ?> map, T t2, boolean z2) {
        return (T) fillBeanWithMap(map, (Object) t2, false, z2);
    }

    public static <T> T fillBeanWithMapIgnoreCase(Map<?, ?> map, T t2, boolean z2) {
        return (T) fillBeanWithMap(map, t2, CopyOptions.create().setIgnoreCase(true).setIgnoreError(z2));
    }

    public static PropertyEditor findEditor(Class<?> cls) {
        return PropertyEditorManager.findEditor(cls);
    }

    public static BeanDesc getBeanDesc(Class<?> cls) {
        return BeanDescCache.INSTANCE.getBeanDesc(cls, new m(cls));
    }

    public static String getFieldName(String str) {
        if (str.startsWith("get") || str.startsWith("set")) {
            return CharSequenceUtil.removePreAndLowerFirst(str, 3);
        }
        if (str.startsWith("is")) {
            return CharSequenceUtil.removePreAndLowerFirst(str, 2);
        }
        throw new IllegalArgumentException("Invalid Getter or Setter name: " + str);
    }

    public static Object getFieldValue(Object obj, final String str) {
        if (obj == null || str == null) {
            return null;
        }
        if (obj instanceof Map) {
            return ((Map) obj).get(str);
        }
        if (obj instanceof Collection) {
            try {
                return CollUtil.get((Collection) obj, Integer.parseInt(str));
            } catch (NumberFormatException unused) {
                return CollUtil.map((Collection) obj, new Function() { // from class: cn.hutool.core.bean.f
                    @Override // java.util.function.Function
                    public final Object apply(Object obj2) {
                        return BeanUtil.lambda$getFieldValue$1(str, obj2);
                    }
                }, false);
            }
        }
        if (!ArrayUtil.isArray(obj)) {
            return ReflectUtil.getFieldValue(obj, str);
        }
        try {
            return ArrayUtil.get(obj, Integer.parseInt(str));
        } catch (NumberFormatException unused2) {
            return ArrayUtil.map(obj, Object.class, new Function() { // from class: cn.hutool.core.bean.g
                @Override // java.util.function.Function
                public final Object apply(Object obj2) {
                    return BeanUtil.lambda$getFieldValue$2(str, obj2);
                }
            });
        }
    }

    public static <T> T getProperty(Object obj, String str) {
        if (obj == null || CharSequenceUtil.isBlank(str)) {
            return null;
        }
        return (T) BeanPath.create(str).get(obj);
    }

    public static PropertyDescriptor getPropertyDescriptor(Class<?> cls, String str) throws BeanException {
        return getPropertyDescriptor(cls, str, false);
    }

    public static Map<String, PropertyDescriptor> getPropertyDescriptorMap(Class<?> cls, boolean z2) throws BeanException {
        return BeanInfoCache.INSTANCE.getPropertyDescriptorMap(cls, z2, new l(cls, z2));
    }

    public static PropertyDescriptor[] getPropertyDescriptors(Class<?> cls) throws BeanException {
        try {
            return (PropertyDescriptor[]) ArrayUtil.filter(Introspector.getBeanInfo(cls).getPropertyDescriptors(), new Filter() { // from class: cn.hutool.core.bean.i
                @Override // cn.hutool.core.lang.Filter
                public final boolean accept(Object obj) {
                    return BeanUtil.lambda$getPropertyDescriptors$0((PropertyDescriptor) obj);
                }
            });
        } catch (IntrospectionException e2) {
            throw new BeanException((Throwable) e2);
        }
    }

    public static boolean hasGetter(Class<?> cls) throws SecurityException {
        if (ClassUtil.isNormalClass(cls)) {
            for (Method method : cls.getMethods()) {
                if (method.getParameterCount() == 0) {
                    String name = method.getName();
                    if ((name.startsWith("get") || name.startsWith("is")) && !"getClass".equals(name)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean hasNullField(Object obj, String... strArr) throws SecurityException {
        if (obj == null) {
            return true;
        }
        for (Field field : ReflectUtil.getFields(obj.getClass())) {
            if (!ModifierUtil.isStatic(field) && !ArrayUtil.contains(strArr, field.getName()) && ReflectUtil.getFieldValue(obj, field) == null) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasPublicField(Class<?> cls) throws SecurityException {
        if (ClassUtil.isNormalClass(cls)) {
            for (Field field : cls.getFields()) {
                if (ModifierUtil.isPublic(field) && !ModifierUtil.isStatic(field)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean hasSetter(Class<?> cls) throws SecurityException {
        if (ClassUtil.isNormalClass(cls)) {
            for (Method method : cls.getMethods()) {
                if (method.getParameterCount() == 1 && method.getName().startsWith("set")) {
                    return true;
                }
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Map<String, PropertyDescriptor> internalGetPropertyDescriptorMap(Class<?> cls, boolean z2) throws BeanException {
        PropertyDescriptor[] propertyDescriptors = getPropertyDescriptors(cls);
        Map<String, PropertyDescriptor> caseInsensitiveMap = z2 ? new CaseInsensitiveMap<>(propertyDescriptors.length, 1.0f) : new HashMap<>(propertyDescriptors.length, 1.0f);
        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
            caseInsensitiveMap.put(propertyDescriptor.getName(), propertyDescriptor);
        }
        return caseInsensitiveMap;
    }

    public static boolean isBean(Class<?> cls) {
        return hasSetter(cls) || hasPublicField(cls);
    }

    public static boolean isCommonFieldsEqual(Object obj, Object obj2, String... strArr) {
        if (obj == null && obj2 == null) {
            return true;
        }
        if (obj == null || obj2 == null) {
            return false;
        }
        Map<String, Object> mapBeanToMap = beanToMap(obj, new String[0]);
        Map<String, Object> mapBeanToMap2 = beanToMap(obj2, new String[0]);
        Set<String> setKeySet = mapBeanToMap.keySet();
        setKeySet.removeAll(Arrays.asList(strArr));
        for (String str : setKeySet) {
            if (mapBeanToMap.containsKey(str) && mapBeanToMap2.containsKey(str) && ObjectUtil.notEqual(mapBeanToMap.get(str), mapBeanToMap2.get(str))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isEmpty(Object obj, String... strArr) throws SecurityException {
        if (obj == null) {
            return true;
        }
        for (Field field : ReflectUtil.getFields(obj.getClass())) {
            if (!ModifierUtil.isStatic(field) && !ArrayUtil.contains(strArr, field.getName()) && ReflectUtil.getFieldValue(obj, field) != null) {
                return false;
            }
        }
        return true;
    }

    public static boolean isMatchName(Object obj, String str, boolean z2) {
        if (obj == null || CharSequenceUtil.isBlank(str)) {
            return false;
        }
        String className = ClassUtil.getClassName(obj, z2);
        if (z2) {
            str = CharSequenceUtil.upperFirst(str);
        }
        return className.equals(str);
    }

    public static boolean isNotEmpty(Object obj, String... strArr) {
        return !isEmpty(obj, strArr);
    }

    public static boolean isReadableBean(Class<?> cls) {
        return hasGetter(cls) || hasPublicField(cls);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ String lambda$beanToMap$4(Set set, String str) {
        if (set.contains(str)) {
            return str;
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ String lambda$beanToMap$5(boolean z2, String str) {
        return z2 ? CharSequenceUtil.toUnderlineCase(str) : str;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Object lambda$copyToList$6(Class cls, CopyOptions copyOptions, Object obj) {
        Object objNewInstanceIfPossible = ReflectUtil.newInstanceIfPossible(cls);
        copyProperties(obj, objNewInstanceIfPossible, copyOptions);
        return objNewInstanceIfPossible;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ BeanDesc lambda$getBeanDesc$e7c7684d$1(Class cls) throws Exception {
        return new BeanDesc(cls);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Object lambda$getFieldValue$1(String str, Object obj) {
        return getFieldValue(obj, str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Object lambda$getFieldValue$2(String str, Object obj) {
        return getFieldValue(obj, str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$getPropertyDescriptors$0(PropertyDescriptor propertyDescriptor) {
        return !"class".equals(propertyDescriptor.getName());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Field lambda$trimStrFields$7(String[] strArr, Object obj, Field field) throws UtilException {
        String str;
        if ((strArr == null || !ArrayUtil.containsIgnoreCase(strArr, field.getName())) && String.class.equals(field.getType()) && (str = (String) ReflectUtil.getFieldValue(obj, field)) != null) {
            String strTrim = CharSequenceUtil.trim(str);
            if (!str.equals(strTrim)) {
                ReflectUtil.setFieldValue(obj, field, strTrim);
            }
        }
        return field;
    }

    @Deprecated
    public static <T> T mapToBean(Map<?, ?> map, Class<T> cls, boolean z2) {
        return (T) fillBeanWithMap(map, ReflectUtil.newInstanceIfPossible(cls), z2);
    }

    @Deprecated
    public static <T> T mapToBeanIgnoreCase(Map<?, ?> map, Class<T> cls, boolean z2) {
        return (T) fillBeanWithMapIgnoreCase(map, ReflectUtil.newInstanceIfPossible(cls), z2);
    }

    public static Object setFieldValue(Object obj, String str, Object obj2) throws UtilException {
        if (obj instanceof Map) {
            ((Map) obj).put(str, obj2);
        } else if (obj instanceof List) {
            ListUtil.setOrPadding((List) obj, Convert.toInt(str).intValue(), obj2);
        } else {
            if (ArrayUtil.isArray(obj)) {
                return ArrayUtil.setOrAppend(obj, Convert.toInt(str).intValue(), obj2);
            }
            ReflectUtil.setFieldValue(obj, str, obj2);
        }
        return obj;
    }

    public static void setProperty(Object obj, String str, Object obj2) {
        BeanPath.create(str).set(obj, obj2);
    }

    public static <T> T toBean(Object obj, Class<T> cls) {
        return (T) toBean(obj, cls, (CopyOptions) null);
    }

    public static <T> T toBeanIgnoreCase(Object obj, Class<T> cls, boolean z2) {
        return (T) toBean(obj, cls, CopyOptions.create().setIgnoreCase(true).setIgnoreError(z2));
    }

    public static <T> T toBeanIgnoreError(Object obj, Class<T> cls) {
        return (T) toBean(obj, cls, CopyOptions.create().setIgnoreError(true));
    }

    public static <T> T trimStrFields(final T t2, final String... strArr) {
        return (T) edit(t2, new Editor() { // from class: cn.hutool.core.bean.k
            @Override // cn.hutool.core.lang.Editor
            public final Object edit(Object obj) {
                return BeanUtil.lambda$trimStrFields$7(strArr, t2, (Field) obj);
            }
        });
    }

    public static <T> T fillBeanWithMap(Map<?, ?> map, T t2, boolean z2, boolean z3) {
        return (T) fillBeanWithMap(map, t2, z2, CopyOptions.create().setIgnoreError(z3));
    }

    public static PropertyDescriptor getPropertyDescriptor(Class<?> cls, String str, boolean z2) throws BeanException {
        Map<String, PropertyDescriptor> propertyDescriptorMap = getPropertyDescriptorMap(cls, z2);
        if (propertyDescriptorMap == null) {
            return null;
        }
        return propertyDescriptorMap.get(str);
    }

    @Deprecated
    public static <T> T mapToBean(Map<?, ?> map, Class<T> cls, CopyOptions copyOptions) {
        return (T) fillBeanWithMap(map, ReflectUtil.newInstanceIfPossible(cls), copyOptions);
    }

    public static <T> T toBean(Object obj, final Class<T> cls, CopyOptions copyOptions) {
        return (T) toBean(obj, new Supplier() { // from class: cn.hutool.core.bean.o
            @Override // java.util.function.Supplier
            public final Object get() {
                return ReflectUtil.newInstanceIfPossible(cls);
            }
        }, copyOptions);
    }

    public static void copyProperties(Object obj, Object obj2, String... strArr) {
        copyProperties(obj, obj2, CopyOptions.create().setIgnoreProperties(strArr));
    }

    public static <T> T fillBeanWithMap(Map<?, ?> map, T t2, CopyOptions copyOptions) {
        return (T) fillBeanWithMap(map, (Object) t2, false, copyOptions);
    }

    public static <T> T mapToBean(Map<?, ?> map, Class<T> cls, boolean z2, CopyOptions copyOptions) {
        return (T) fillBeanWithMap(map, ReflectUtil.newInstanceIfPossible(cls), z2, copyOptions);
    }

    public static <T> T toBean(Object obj, Supplier<T> supplier, CopyOptions copyOptions) {
        if (obj == null || supplier == null) {
            return null;
        }
        T t2 = (T) supplier.get();
        copyProperties(obj, t2, copyOptions);
        return t2;
    }

    public static void copyProperties(Object obj, Object obj2, boolean z2) {
        BeanCopier.create(obj, obj2, CopyOptions.create().setIgnoreCase(z2)).copy();
    }

    public static <T> T fillBeanWithMap(Map<?, ?> map, T t2, boolean z2, CopyOptions copyOptions) {
        if (MapUtil.isEmpty(map)) {
            return t2;
        }
        if (z2) {
            map = MapUtil.toCamelCaseMap(map);
        }
        copyProperties(map, t2, copyOptions);
        return t2;
    }

    public static void copyProperties(Object obj, Object obj2, CopyOptions copyOptions) {
        if (obj == null) {
            return;
        }
        BeanCopier.create(obj, obj2, (CopyOptions) ObjectUtil.defaultIfNull(copyOptions, new p())).copy();
    }

    public static <T> T toBean(Class<T> cls, ValueProvider<String> valueProvider, CopyOptions copyOptions) {
        if (cls == null || valueProvider == null) {
            return null;
        }
        return (T) fillBean(ReflectUtil.newInstanceIfPossible(cls), valueProvider, copyOptions);
    }

    public static Map<String, Object> beanToMap(Object obj, boolean z2, boolean z3) {
        if (obj == null) {
            return null;
        }
        return beanToMap(obj, new LinkedHashMap(), z2, z3);
    }

    public static Map<String, Object> beanToMap(Object obj, Map<String, Object> map, final boolean z2, boolean z3) {
        if (obj == null) {
            return null;
        }
        return beanToMap(obj, map, z3, (Editor<String>) new Editor() { // from class: cn.hutool.core.bean.j
            @Override // cn.hutool.core.lang.Editor
            public final Object edit(Object obj2) {
                return BeanUtil.lambda$beanToMap$5(z2, (String) obj2);
            }
        });
    }

    public static <T> List<T> copyToList(Collection<?> collection, Class<T> cls) {
        return copyToList(collection, cls, CopyOptions.create());
    }

    public static Map<String, Object> beanToMap(Object obj, Map<String, Object> map, boolean z2, Editor<String> editor) {
        if (obj == null) {
            return null;
        }
        return (Map) BeanCopier.create(obj, map, CopyOptions.create().setIgnoreNullValue(z2).setFieldNameEditor(editor)).copy();
    }

    public static Map<String, Object> beanToMap(Object obj, Map<String, Object> map, CopyOptions copyOptions) {
        if (obj == null) {
            return null;
        }
        return (Map) BeanCopier.create(obj, map, copyOptions).copy();
    }
}
