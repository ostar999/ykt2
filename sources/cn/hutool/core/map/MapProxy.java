package cn.hutool.core.map;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.getter.OptNullBasicTypeFromObjectGetter;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.ClassLoaderUtil;
import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Set;

/* loaded from: classes.dex */
public class MapProxy implements Map<Object, Object>, OptNullBasicTypeFromObjectGetter<Object>, InvocationHandler, Serializable {
    private static final long serialVersionUID = 1;
    Map map;

    public MapProxy(Map<?, ?> map) {
        this.map = map;
    }

    public static MapProxy create(Map<?, ?> map) {
        return map instanceof MapProxy ? (MapProxy) map : new MapProxy(map);
    }

    @Override // java.util.Map
    public void clear() {
        this.map.clear();
    }

    @Override // java.util.Map
    public boolean containsKey(Object obj) {
        return this.map.containsKey(obj);
    }

    @Override // java.util.Map
    public boolean containsValue(Object obj) {
        return this.map.containsValue(obj);
    }

    @Override // java.util.Map
    public Set<Map.Entry<Object, Object>> entrySet() {
        return this.map.entrySet();
    }

    @Override // java.util.Map
    public Object get(Object obj) {
        return this.map.get(obj);
    }

    @Override // cn.hutool.core.getter.OptNullBasicTypeGetter, cn.hutool.core.getter.BasicTypeGetter
    public /* synthetic */ BigDecimal getBigDecimal(Object obj) {
        return x.c.a(this, obj);
    }

    @Override // cn.hutool.core.getter.OptNullBasicTypeFromObjectGetter, cn.hutool.core.getter.OptBasicTypeGetter
    public /* synthetic */ BigDecimal getBigDecimal(Object obj, BigDecimal bigDecimal) {
        return x.a.a(this, obj, bigDecimal);
    }

    @Override // cn.hutool.core.getter.OptNullBasicTypeGetter, cn.hutool.core.getter.BasicTypeGetter
    public /* synthetic */ BigInteger getBigInteger(Object obj) {
        return x.c.b(this, obj);
    }

    @Override // cn.hutool.core.getter.OptNullBasicTypeFromObjectGetter, cn.hutool.core.getter.OptBasicTypeGetter
    public /* synthetic */ BigInteger getBigInteger(Object obj, BigInteger bigInteger) {
        return x.a.b(this, obj, bigInteger);
    }

    @Override // cn.hutool.core.getter.OptNullBasicTypeGetter, cn.hutool.core.getter.BasicTypeGetter
    public /* synthetic */ Boolean getBool(Object obj) {
        return x.c.c(this, obj);
    }

    @Override // cn.hutool.core.getter.OptNullBasicTypeFromObjectGetter, cn.hutool.core.getter.OptBasicTypeGetter
    public /* synthetic */ Boolean getBool(Object obj, Boolean bool) {
        return x.a.c(this, obj, bool);
    }

    @Override // cn.hutool.core.getter.OptNullBasicTypeGetter, cn.hutool.core.getter.BasicTypeGetter
    public /* synthetic */ Byte getByte(Object obj) {
        return x.c.d(this, obj);
    }

    @Override // cn.hutool.core.getter.OptNullBasicTypeFromObjectGetter, cn.hutool.core.getter.OptBasicTypeGetter
    public /* synthetic */ Byte getByte(Object obj, Byte b3) {
        return x.a.d(this, obj, b3);
    }

    @Override // cn.hutool.core.getter.OptNullBasicTypeGetter, cn.hutool.core.getter.BasicTypeGetter
    public /* synthetic */ Character getChar(Object obj) {
        return x.c.e(this, obj);
    }

    @Override // cn.hutool.core.getter.OptNullBasicTypeFromObjectGetter, cn.hutool.core.getter.OptBasicTypeGetter
    public /* synthetic */ Character getChar(Object obj, Character ch) {
        return x.a.e(this, obj, ch);
    }

    @Override // cn.hutool.core.getter.OptNullBasicTypeGetter, cn.hutool.core.getter.BasicTypeGetter
    public /* synthetic */ Date getDate(Object obj) {
        return x.c.f(this, obj);
    }

    @Override // cn.hutool.core.getter.OptNullBasicTypeFromObjectGetter, cn.hutool.core.getter.OptBasicTypeGetter
    public /* synthetic */ Date getDate(Object obj, Date date) {
        return x.a.f(this, obj, date);
    }

    @Override // cn.hutool.core.getter.OptNullBasicTypeGetter, cn.hutool.core.getter.BasicTypeGetter
    public /* synthetic */ Double getDouble(Object obj) {
        return x.c.g(this, obj);
    }

    @Override // cn.hutool.core.getter.OptNullBasicTypeFromObjectGetter, cn.hutool.core.getter.OptBasicTypeGetter
    public /* synthetic */ Double getDouble(Object obj, Double d3) {
        return x.a.g(this, obj, d3);
    }

    @Override // cn.hutool.core.getter.OptNullBasicTypeGetter, cn.hutool.core.getter.BasicTypeGetter
    public /* synthetic */ Enum getEnum(Class cls, Object obj) {
        return x.c.h(this, cls, obj);
    }

    @Override // cn.hutool.core.getter.OptNullBasicTypeFromObjectGetter, cn.hutool.core.getter.OptBasicTypeGetter
    public /* synthetic */ Enum getEnum(Class cls, Object obj, Enum r3) {
        return x.a.h(this, cls, obj, r3);
    }

    @Override // cn.hutool.core.getter.OptNullBasicTypeGetter, cn.hutool.core.getter.BasicTypeGetter
    public /* synthetic */ Float getFloat(Object obj) {
        return x.c.i(this, obj);
    }

    @Override // cn.hutool.core.getter.OptNullBasicTypeFromObjectGetter, cn.hutool.core.getter.OptBasicTypeGetter
    public /* synthetic */ Float getFloat(Object obj, Float f2) {
        return x.a.i(this, obj, f2);
    }

    @Override // cn.hutool.core.getter.OptNullBasicTypeGetter, cn.hutool.core.getter.BasicTypeGetter
    public /* synthetic */ Integer getInt(Object obj) {
        return x.c.j(this, obj);
    }

    @Override // cn.hutool.core.getter.OptNullBasicTypeFromObjectGetter, cn.hutool.core.getter.OptBasicTypeGetter
    public /* synthetic */ Integer getInt(Object obj, Integer num) {
        return x.a.j(this, obj, num);
    }

    @Override // cn.hutool.core.getter.OptNullBasicTypeGetter, cn.hutool.core.getter.BasicTypeGetter
    public /* synthetic */ Long getLong(Object obj) {
        return x.c.k(this, obj);
    }

    @Override // cn.hutool.core.getter.OptNullBasicTypeFromObjectGetter, cn.hutool.core.getter.OptBasicTypeGetter
    public /* synthetic */ Long getLong(Object obj, Long l2) {
        return x.a.k(this, obj, l2);
    }

    @Override // cn.hutool.core.getter.OptNullBasicTypeGetter, cn.hutool.core.getter.BasicTypeGetter
    public /* synthetic */ Object getObj(Object obj) {
        return x.c.l(this, obj);
    }

    @Override // cn.hutool.core.getter.OptBasicTypeGetter
    public Object getObj(Object obj, Object obj2) {
        Object obj3 = this.map.get(obj);
        return obj3 != null ? obj3 : obj2;
    }

    @Override // cn.hutool.core.getter.OptNullBasicTypeGetter, cn.hutool.core.getter.BasicTypeGetter
    public /* synthetic */ Short getShort(Object obj) {
        return x.c.m(this, obj);
    }

    @Override // cn.hutool.core.getter.OptNullBasicTypeFromObjectGetter, cn.hutool.core.getter.OptBasicTypeGetter
    public /* synthetic */ Short getShort(Object obj, Short sh) {
        return x.a.l(this, obj, sh);
    }

    @Override // cn.hutool.core.getter.OptNullBasicTypeGetter, cn.hutool.core.getter.BasicTypeGetter
    public /* synthetic */ String getStr(Object obj) {
        return x.c.n(this, obj);
    }

    @Override // cn.hutool.core.getter.OptNullBasicTypeFromObjectGetter, cn.hutool.core.getter.OptBasicTypeGetter
    public /* synthetic */ String getStr(Object obj, String str) {
        return x.a.m(this, obj, str);
    }

    @Override // java.lang.reflect.InvocationHandler
    public Object invoke(Object obj, Method method, Object[] objArr) {
        String strRemovePreAndLowerFirst;
        Class<?>[] parameterTypes = method.getParameterTypes();
        if (ArrayUtil.isEmpty((Object[]) parameterTypes)) {
            Class<?> returnType = method.getReturnType();
            if (Void.TYPE != returnType) {
                String name = method.getName();
                if (name.startsWith("get")) {
                    strRemovePreAndLowerFirst = CharSequenceUtil.removePreAndLowerFirst(name, 3);
                } else if (BooleanUtil.isBoolean(returnType) && name.startsWith("is")) {
                    strRemovePreAndLowerFirst = CharSequenceUtil.removePreAndLowerFirst(name, 2);
                } else {
                    if ("hashCode".equals(name)) {
                        return Integer.valueOf(hashCode());
                    }
                    if ("toString".equals(name)) {
                        return toString();
                    }
                    strRemovePreAndLowerFirst = null;
                }
                if (CharSequenceUtil.isNotBlank(strRemovePreAndLowerFirst)) {
                    if (!containsKey(strRemovePreAndLowerFirst)) {
                        strRemovePreAndLowerFirst = CharSequenceUtil.toUnderlineCase(strRemovePreAndLowerFirst);
                    }
                    return Convert.convert(method.getGenericReturnType(), get(strRemovePreAndLowerFirst));
                }
            }
        } else if (1 == parameterTypes.length) {
            String name2 = method.getName();
            if (name2.startsWith("set")) {
                String strRemovePreAndLowerFirst2 = CharSequenceUtil.removePreAndLowerFirst(name2, 3);
                if (CharSequenceUtil.isNotBlank(strRemovePreAndLowerFirst2)) {
                    put(strRemovePreAndLowerFirst2, objArr[0]);
                    if (method.getReturnType().isInstance(obj)) {
                        return obj;
                    }
                }
            } else if ("equals".equals(name2)) {
                return Boolean.valueOf(equals(objArr[0]));
            }
        }
        throw new UnsupportedOperationException(method.toGenericString());
    }

    @Override // java.util.Map
    public boolean isEmpty() {
        return this.map.isEmpty();
    }

    @Override // java.util.Map
    public Set<Object> keySet() {
        return this.map.keySet();
    }

    @Override // java.util.Map
    public Object put(Object obj, Object obj2) {
        return this.map.put(obj, obj2);
    }

    @Override // java.util.Map
    public void putAll(Map<? extends Object, ? extends Object> map) {
        this.map.putAll(map);
    }

    @Override // java.util.Map
    public Object remove(Object obj) {
        return this.map.remove(obj);
    }

    @Override // java.util.Map
    public int size() {
        return this.map.size();
    }

    public <T> T toProxyBean(Class<T> cls) {
        return (T) Proxy.newProxyInstance(ClassLoaderUtil.getClassLoader(), new Class[]{cls}, this);
    }

    @Override // java.util.Map
    public Collection<Object> values() {
        return this.map.values();
    }
}
