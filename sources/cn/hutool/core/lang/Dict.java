package cn.hutool.core.lang;

import cn.hutool.core.bean.BeanPath;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.getter.BasicTypeGetter;
import cn.hutool.core.lang.func.Func0;
import cn.hutool.core.lang.func.LambdaUtil;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

/* loaded from: classes.dex */
public class Dict extends LinkedHashMap<String, Object> implements BasicTypeGetter<String> {
    static final int DEFAULT_INITIAL_CAPACITY = 16;
    static final float DEFAULT_LOAD_FACTOR = 0.75f;
    private static final long serialVersionUID = 6135423866861206530L;
    private boolean caseInsensitive;

    public Dict() {
        this(false);
    }

    public static Dict create() {
        return new Dict();
    }

    private String customKey(String str) {
        return (!this.caseInsensitive || str == null) ? str : str.toLowerCase();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setFields$0(Func0 func0) {
        set(LambdaUtil.getFieldName(func0), func0.callWithRuntimeException());
    }

    @SafeVarargs
    public static Dict of(Pair<String, Object>... pairArr) {
        Dict dictCreate = create();
        for (Pair<String, Object> pair : pairArr) {
            dictCreate.put(pair.getKey(), pair.getValue());
        }
        return dictCreate;
    }

    public static <T> Dict parse(T t2) {
        return create().parseBean(t2);
    }

    @Override // java.util.HashMap, java.util.Map
    public /* bridge */ /* synthetic */ Object compute(Object obj, BiFunction biFunction) {
        return compute((String) obj, (BiFunction<? super String, ? super Object, ?>) biFunction);
    }

    @Override // java.util.HashMap, java.util.Map
    public /* bridge */ /* synthetic */ Object computeIfAbsent(Object obj, Function function) {
        return computeIfAbsent((String) obj, (Function<? super String, ?>) function);
    }

    @Override // java.util.HashMap, java.util.Map
    public /* bridge */ /* synthetic */ Object computeIfPresent(Object obj, BiFunction biFunction) {
        return computeIfPresent((String) obj, (BiFunction<? super String, ? super Object, ?>) biFunction);
    }

    @Override // java.util.HashMap, java.util.AbstractMap, java.util.Map
    public boolean containsKey(Object obj) {
        return super.containsKey(customKey((String) obj));
    }

    public Dict filter(String... strArr) {
        Dict dict = new Dict(strArr.length, 1.0f);
        for (String str : strArr) {
            if (containsKey(str)) {
                dict.put(str, get(str));
            }
        }
        return dict;
    }

    public <T> T get(String str, T t2) {
        T t3 = (T) get(str);
        return t3 != null ? t3 : t2;
    }

    public <T> T getBean(String str) {
        return (T) get(str, null);
    }

    public <T> T getByPath(String str) {
        return (T) BeanPath.create(str).get(this);
    }

    public byte[] getBytes(String str) {
        return (byte[]) get(str, null);
    }

    public Number getNumber(String str) {
        return (Number) get(str, null);
    }

    @Override // java.util.LinkedHashMap, java.util.HashMap, java.util.Map
    public Object getOrDefault(Object obj, Object obj2) {
        return super.getOrDefault(customKey((String) obj), obj2);
    }

    public Time getTime(String str) {
        return (Time) get(str, null);
    }

    public Timestamp getTimestamp(String str) {
        return (Timestamp) get(str, null);
    }

    @Override // java.util.HashMap, java.util.Map
    public /* bridge */ /* synthetic */ Object merge(Object obj, Object obj2, BiFunction biFunction) {
        return merge((String) obj, obj2, (BiFunction<? super Object, ? super Object, ?>) biFunction);
    }

    public <T> Dict parseBean(T t2) throws IllegalArgumentException {
        Assert.notNull(t2, "Bean class must be not null", new Object[0]);
        putAll(BeanUtil.beanToMap(t2, new String[0]));
        return this;
    }

    @Override // java.util.HashMap, java.util.AbstractMap, java.util.Map
    public void putAll(Map<? extends String, ?> map) {
        map.forEach(new BiConsumer() { // from class: cn.hutool.core.lang.v
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f2518c.put((String) obj, obj2);
            }
        });
    }

    @Override // java.util.HashMap, java.util.AbstractMap, java.util.Map
    public Object remove(Object obj) {
        return super.remove(customKey((String) obj));
    }

    public <T extends Dict> void removeEqual(T t2, String... strArr) {
        HashSet hashSetNewHashSet = CollUtil.newHashSet(strArr);
        for (Map.Entry entry : t2.entrySet()) {
            if (!hashSetNewHashSet.contains(entry.getKey()) && Objects.equals(get(entry.getKey()), entry.getValue())) {
                remove(entry.getKey());
            }
        }
    }

    public Dict set(String str, Object obj) {
        put(str, obj);
        return this;
    }

    public Dict setFields(Func0<?>... func0Arr) {
        Arrays.stream(func0Arr).forEach(new Consumer() { // from class: cn.hutool.core.lang.w
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f2519c.lambda$setFields$0((Func0) obj);
            }
        });
        return this;
    }

    public Dict setIgnoreNull(String str, Object obj) {
        if (str != null && obj != null) {
            set(str, obj);
        }
        return this;
    }

    public <T> T toBean(T t2) {
        return (T) toBean(t2, false);
    }

    public <T> T toBeanIgnoreCase(T t2) {
        BeanUtil.fillBeanWithMapIgnoreCase(this, t2, false);
        return t2;
    }

    public <T> T toBeanWithCamelCase(T t2) {
        BeanUtil.fillBeanWithMap((Map<?, ?>) this, (Object) t2, true, false);
        return t2;
    }

    public Dict(boolean z2) {
        this(16, z2);
    }

    @Override // java.util.HashMap, java.util.AbstractMap
    public Dict clone() {
        return (Dict) super.clone();
    }

    public Object compute(String str, BiFunction<? super String, ? super Object, ?> biFunction) {
        return super.compute((Dict) customKey(str), (BiFunction<? super Dict, ? super V, ? extends V>) biFunction);
    }

    public Object computeIfAbsent(String str, Function<? super String, ?> function) {
        return super.computeIfAbsent((Dict) customKey(str), (Function<? super Dict, ? extends V>) function);
    }

    public Object computeIfPresent(String str, BiFunction<? super String, ? super Object, ?> biFunction) {
        return super.computeIfPresent((Dict) customKey(str), (BiFunction<? super Dict, ? super V, ? extends V>) biFunction);
    }

    @Override // java.util.LinkedHashMap, java.util.HashMap, java.util.AbstractMap, java.util.Map
    public Object get(Object obj) {
        return super.get(customKey((String) obj));
    }

    @Override // cn.hutool.core.getter.BasicTypeGetter
    public BigDecimal getBigDecimal(String str) {
        return Convert.toBigDecimal(get(str));
    }

    @Override // cn.hutool.core.getter.BasicTypeGetter
    public BigInteger getBigInteger(String str) {
        return Convert.toBigInteger(get(str));
    }

    @Override // cn.hutool.core.getter.BasicTypeGetter
    public Boolean getBool(String str) {
        return Convert.toBool(get(str), null);
    }

    public <T> T getByPath(String str, Class<T> cls) {
        return (T) Convert.convert((Class) cls, getByPath(str));
    }

    @Override // cn.hutool.core.getter.BasicTypeGetter
    public Byte getByte(String str) {
        return Convert.toByte(get(str), null);
    }

    @Override // cn.hutool.core.getter.BasicTypeGetter
    public Character getChar(String str) {
        return Convert.toChar(get(str), null);
    }

    @Override // cn.hutool.core.getter.BasicTypeGetter
    public Date getDate(String str) {
        return (Date) get(str, null);
    }

    @Override // cn.hutool.core.getter.BasicTypeGetter
    public Double getDouble(String str) {
        return Convert.toDouble(get(str), null);
    }

    @Override // cn.hutool.core.getter.BasicTypeGetter
    public <E extends Enum<E>> E getEnum(Class<E> cls, String str) {
        return (E) Convert.toEnum(cls, get(str));
    }

    @Override // cn.hutool.core.getter.BasicTypeGetter
    public Float getFloat(String str) {
        return Convert.toFloat(get(str), null);
    }

    @Override // cn.hutool.core.getter.BasicTypeGetter
    public Integer getInt(String str) {
        return Convert.toInt(get(str), null);
    }

    @Override // cn.hutool.core.getter.BasicTypeGetter
    public Long getLong(String str) {
        return Convert.toLong(get(str), null);
    }

    @Override // cn.hutool.core.getter.BasicTypeGetter
    public Object getObj(String str) {
        return super.get(str);
    }

    @Override // cn.hutool.core.getter.BasicTypeGetter
    public Short getShort(String str) {
        return Convert.toShort(get(str), null);
    }

    @Override // cn.hutool.core.getter.BasicTypeGetter
    public String getStr(String str) {
        return Convert.toStr(get(str), null);
    }

    public Object merge(String str, Object obj, BiFunction<? super Object, ? super Object, ?> biFunction) {
        return super.merge((Dict) customKey(str), (String) obj, (BiFunction<? super String, ? super String, ? extends String>) biFunction);
    }

    @Override // java.util.HashMap, java.util.AbstractMap, java.util.Map
    public Object put(String str, Object obj) {
        return super.put((Dict) customKey(str), (String) obj);
    }

    @Override // java.util.HashMap, java.util.Map
    public Object putIfAbsent(String str, Object obj) {
        return super.putIfAbsent((Dict) customKey(str), (String) obj);
    }

    @Override // java.util.HashMap, java.util.Map
    public boolean remove(Object obj, Object obj2) {
        return super.remove(customKey((String) obj), obj2);
    }

    public <T> T toBean(T t2, boolean z2) {
        BeanUtil.fillBeanWithMap((Map<?, ?>) this, (Object) t2, z2, false);
        return t2;
    }

    public <T> T toBeanIgnoreCase(Class<T> cls) {
        return (T) BeanUtil.toBeanIgnoreCase(this, cls, false);
    }

    public Dict(int i2) {
        this(i2, false);
    }

    public <T> Dict parseBean(T t2, boolean z2, boolean z3) throws IllegalArgumentException {
        Assert.notNull(t2, "Bean class must be not null", new Object[0]);
        putAll(BeanUtil.beanToMap(t2, z2, z3));
        return this;
    }

    @Override // java.util.HashMap, java.util.Map
    public boolean replace(String str, Object obj, Object obj2) {
        return super.replace((Dict) customKey(str), obj, obj2);
    }

    public <T> T toBean(Class<T> cls) {
        return (T) BeanUtil.toBean(this, cls);
    }

    public Dict(int i2, boolean z2) {
        this(i2, 0.75f, z2);
    }

    public static Dict of(Object... objArr) {
        Dict dictCreate = create();
        String str = null;
        for (int i2 = 0; i2 < objArr.length; i2++) {
            if (i2 % 2 == 0) {
                str = Convert.toStr(objArr[i2]);
            } else {
                dictCreate.put(str, objArr[i2]);
            }
        }
        return dictCreate;
    }

    @Override // java.util.HashMap, java.util.Map
    public Object replace(String str, Object obj) {
        return super.replace((Dict) customKey(str), (String) obj);
    }

    public Dict(int i2, float f2) {
        this(i2, f2, false);
    }

    public Dict(int i2, float f2, boolean z2) {
        super(i2, f2);
        this.caseInsensitive = z2;
    }

    public Dict(Map<String, Object> map) {
        super(map == null ? new HashMap<>() : map);
    }
}
