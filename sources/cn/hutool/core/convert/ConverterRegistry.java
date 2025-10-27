package cn.hutool.core.convert;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.impl.ArrayConverter;
import cn.hutool.core.convert.impl.AtomicBooleanConverter;
import cn.hutool.core.convert.impl.AtomicIntegerArrayConverter;
import cn.hutool.core.convert.impl.AtomicLongArrayConverter;
import cn.hutool.core.convert.impl.AtomicReferenceConverter;
import cn.hutool.core.convert.impl.BeanConverter;
import cn.hutool.core.convert.impl.BooleanConverter;
import cn.hutool.core.convert.impl.CalendarConverter;
import cn.hutool.core.convert.impl.CharacterConverter;
import cn.hutool.core.convert.impl.CharsetConverter;
import cn.hutool.core.convert.impl.ClassConverter;
import cn.hutool.core.convert.impl.CollectionConverter;
import cn.hutool.core.convert.impl.CurrencyConverter;
import cn.hutool.core.convert.impl.DateConverter;
import cn.hutool.core.convert.impl.DurationConverter;
import cn.hutool.core.convert.impl.EntryConverter;
import cn.hutool.core.convert.impl.EnumConverter;
import cn.hutool.core.convert.impl.LocaleConverter;
import cn.hutool.core.convert.impl.MapConverter;
import cn.hutool.core.convert.impl.NumberConverter;
import cn.hutool.core.convert.impl.OptConverter;
import cn.hutool.core.convert.impl.OptionalConverter;
import cn.hutool.core.convert.impl.PairConverter;
import cn.hutool.core.convert.impl.PathConverter;
import cn.hutool.core.convert.impl.PeriodConverter;
import cn.hutool.core.convert.impl.PrimitiveConverter;
import cn.hutool.core.convert.impl.ReferenceConverter;
import cn.hutool.core.convert.impl.StackTraceElementConverter;
import cn.hutool.core.convert.impl.StringConverter;
import cn.hutool.core.convert.impl.TemporalAccessorConverter;
import cn.hutool.core.convert.impl.TimeZoneConverter;
import cn.hutool.core.convert.impl.URIConverter;
import cn.hutool.core.convert.impl.URLConverter;
import cn.hutool.core.convert.impl.UUIDConverter;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.lang.Opt;
import cn.hutool.core.lang.Pair;
import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.map.SafeConcurrentHashMap;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.ServiceLoaderUtil;
import cn.hutool.core.util.TypeUtil;
import java.io.Serializable;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.MonthDay;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.Period;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalAccessor;
import java.util.Calendar;
import java.util.Collection;
import java.util.Currency;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.TimeZone;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicLongArray;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.DoubleAdder;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.Consumer;

/* loaded from: classes.dex */
public class ConverterRegistry implements Serializable {
    private static final long serialVersionUID = 1;
    private volatile Map<Type, Converter<?>> customConverterMap;
    private Map<Class<?>, Converter<?>> defaultConverterMap;

    public static class SingletonHolder {
        private static final ConverterRegistry INSTANCE = new ConverterRegistry();

        private SingletonHolder() {
        }
    }

    public ConverterRegistry() {
        defaultConverter();
        putCustomBySpi();
    }

    /* JADX WARN: Multi-variable type inference failed */
    private <T> T convertSpecial(Type type, Class<T> cls, Object obj, T t2) {
        if (cls == null) {
            return null;
        }
        if (Collection.class.isAssignableFrom(cls)) {
            return (T) new CollectionConverter(type).convert(obj, (Collection<?>) t2);
        }
        if (Map.class.isAssignableFrom(cls)) {
            return (T) new MapConverter(type).convert(obj, (Map) t2);
        }
        if (Map.Entry.class.isAssignableFrom(cls)) {
            return (T) new EntryConverter(type).convert(obj, (Map.Entry) t2);
        }
        if (cls.isInstance(obj)) {
            return obj;
        }
        if (cls.isEnum()) {
            return (T) new EnumConverter(cls).convert(obj, t2);
        }
        if (cls.isArray()) {
            return (T) new ArrayConverter(cls).convert(obj, t2);
        }
        if ("java.lang.Class".equals(cls.getName())) {
            return (T) new ClassConverter().convert(obj, (Class) t2);
        }
        return null;
    }

    private ConverterRegistry defaultConverter() {
        SafeConcurrentHashMap safeConcurrentHashMap = new SafeConcurrentHashMap();
        this.defaultConverterMap = safeConcurrentHashMap;
        Class cls = Integer.TYPE;
        safeConcurrentHashMap.put(cls, new PrimitiveConverter(cls));
        Map<Class<?>, Converter<?>> map = this.defaultConverterMap;
        Class<?> cls2 = Long.TYPE;
        map.put(cls2, new PrimitiveConverter(cls2));
        Map<Class<?>, Converter<?>> map2 = this.defaultConverterMap;
        Class<?> cls3 = Byte.TYPE;
        map2.put(cls3, new PrimitiveConverter(cls3));
        Map<Class<?>, Converter<?>> map3 = this.defaultConverterMap;
        Class<?> cls4 = Short.TYPE;
        map3.put(cls4, new PrimitiveConverter(cls4));
        Map<Class<?>, Converter<?>> map4 = this.defaultConverterMap;
        Class<?> cls5 = Float.TYPE;
        map4.put(cls5, new PrimitiveConverter(cls5));
        Map<Class<?>, Converter<?>> map5 = this.defaultConverterMap;
        Class<?> cls6 = Double.TYPE;
        map5.put(cls6, new PrimitiveConverter(cls6));
        Map<Class<?>, Converter<?>> map6 = this.defaultConverterMap;
        Class<?> cls7 = Character.TYPE;
        map6.put(cls7, new PrimitiveConverter(cls7));
        Map<Class<?>, Converter<?>> map7 = this.defaultConverterMap;
        Class<?> cls8 = Boolean.TYPE;
        map7.put(cls8, new PrimitiveConverter(cls8));
        this.defaultConverterMap.put(Number.class, new NumberConverter());
        this.defaultConverterMap.put(Integer.class, new NumberConverter(Integer.class));
        this.defaultConverterMap.put(AtomicInteger.class, new NumberConverter(AtomicInteger.class));
        this.defaultConverterMap.put(Long.class, new NumberConverter(Long.class));
        this.defaultConverterMap.put(LongAdder.class, new NumberConverter(LongAdder.class));
        this.defaultConverterMap.put(AtomicLong.class, new NumberConverter(AtomicLong.class));
        this.defaultConverterMap.put(Byte.class, new NumberConverter(Byte.class));
        this.defaultConverterMap.put(Short.class, new NumberConverter(Short.class));
        this.defaultConverterMap.put(Float.class, new NumberConverter(Float.class));
        this.defaultConverterMap.put(Double.class, new NumberConverter(Double.class));
        this.defaultConverterMap.put(DoubleAdder.class, new NumberConverter(DoubleAdder.class));
        this.defaultConverterMap.put(Character.class, new CharacterConverter());
        this.defaultConverterMap.put(Boolean.class, new BooleanConverter());
        this.defaultConverterMap.put(AtomicBoolean.class, new AtomicBooleanConverter());
        this.defaultConverterMap.put(BigDecimal.class, new NumberConverter(BigDecimal.class));
        this.defaultConverterMap.put(BigInteger.class, new NumberConverter(BigInteger.class));
        this.defaultConverterMap.put(CharSequence.class, new StringConverter());
        this.defaultConverterMap.put(String.class, new StringConverter());
        this.defaultConverterMap.put(URI.class, new URIConverter());
        this.defaultConverterMap.put(URL.class, new URLConverter());
        this.defaultConverterMap.put(Calendar.class, new CalendarConverter());
        this.defaultConverterMap.put(Date.class, new DateConverter(Date.class));
        this.defaultConverterMap.put(DateTime.class, new DateConverter(DateTime.class));
        this.defaultConverterMap.put(java.sql.Date.class, new DateConverter(java.sql.Date.class));
        this.defaultConverterMap.put(Time.class, new DateConverter(Time.class));
        this.defaultConverterMap.put(Timestamp.class, new DateConverter(Timestamp.class));
        this.defaultConverterMap.put(TemporalAccessor.class, new TemporalAccessorConverter(Instant.class));
        this.defaultConverterMap.put(Instant.class, new TemporalAccessorConverter(Instant.class));
        this.defaultConverterMap.put(LocalDateTime.class, new TemporalAccessorConverter(LocalDateTime.class));
        this.defaultConverterMap.put(LocalDate.class, new TemporalAccessorConverter(LocalDate.class));
        this.defaultConverterMap.put(LocalTime.class, new TemporalAccessorConverter(LocalTime.class));
        this.defaultConverterMap.put(ZonedDateTime.class, new TemporalAccessorConverter(ZonedDateTime.class));
        this.defaultConverterMap.put(OffsetDateTime.class, new TemporalAccessorConverter(OffsetDateTime.class));
        this.defaultConverterMap.put(OffsetTime.class, new TemporalAccessorConverter(OffsetTime.class));
        this.defaultConverterMap.put(DayOfWeek.class, new TemporalAccessorConverter(DayOfWeek.class));
        this.defaultConverterMap.put(Month.class, new TemporalAccessorConverter(Month.class));
        this.defaultConverterMap.put(MonthDay.class, new TemporalAccessorConverter(MonthDay.class));
        this.defaultConverterMap.put(Period.class, new PeriodConverter());
        this.defaultConverterMap.put(Duration.class, new DurationConverter());
        this.defaultConverterMap.put(WeakReference.class, new ReferenceConverter(WeakReference.class));
        this.defaultConverterMap.put(SoftReference.class, new ReferenceConverter(SoftReference.class));
        this.defaultConverterMap.put(AtomicReference.class, new AtomicReferenceConverter());
        this.defaultConverterMap.put(AtomicIntegerArray.class, new AtomicIntegerArrayConverter());
        this.defaultConverterMap.put(AtomicLongArray.class, new AtomicLongArrayConverter());
        this.defaultConverterMap.put(TimeZone.class, new TimeZoneConverter());
        this.defaultConverterMap.put(Locale.class, new LocaleConverter());
        this.defaultConverterMap.put(Charset.class, new CharsetConverter());
        this.defaultConverterMap.put(Path.class, new PathConverter());
        this.defaultConverterMap.put(Currency.class, new CurrencyConverter());
        this.defaultConverterMap.put(UUID.class, new UUIDConverter());
        this.defaultConverterMap.put(StackTraceElement.class, new StackTraceElementConverter());
        this.defaultConverterMap.put(Optional.class, new OptionalConverter());
        this.defaultConverterMap.put(Opt.class, new OptConverter());
        this.defaultConverterMap.put(Pair.class, new PairConverter(Pair.class));
        return this;
    }

    public static ConverterRegistry getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$putCustomBySpi$0(Converter converter) {
        try {
            Type typeArgument = TypeUtil.getTypeArgument(ClassUtil.getClass(converter));
            if (typeArgument != null) {
                putCustom(typeArgument, (Converter<?>) converter);
            }
        } catch (Exception unused) {
        }
    }

    private void putCustomBySpi() {
        ServiceLoaderUtil.load(Converter.class).forEach(new Consumer() { // from class: cn.hutool.core.convert.c
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f2452c.lambda$putCustomBySpi$0((Converter) obj);
            }
        });
    }

    /* JADX WARN: Multi-variable type inference failed */
    public <T> T convert(Type type, Object obj, T t2, boolean z2) throws ConvertException {
        if (TypeUtil.isUnknown(type) && t2 == null) {
            return obj;
        }
        if (ObjectUtil.isNull(obj)) {
            return t2;
        }
        if (TypeUtil.isUnknown(type)) {
            type = t2.getClass();
        }
        boolean z3 = obj instanceof Opt;
        TypeConverter typeConverter = obj;
        if (z3) {
            TypeConverter typeConverter2 = (T) ((Opt) obj).get();
            boolean zIsNull = ObjectUtil.isNull(typeConverter2);
            typeConverter = typeConverter2;
            if (zIsNull) {
                return t2;
            }
        }
        boolean z4 = typeConverter instanceof Optional;
        TypeConverter typeConverter3 = typeConverter;
        if (z4) {
            TypeConverter typeConverter4 = (T) ((Optional) typeConverter).orElse(null);
            boolean zIsNull2 = ObjectUtil.isNull(typeConverter4);
            typeConverter3 = typeConverter4;
            if (zIsNull2) {
                return t2;
            }
        }
        if (type instanceof TypeReference) {
            type = ((TypeReference) type).getType();
        }
        if (typeConverter3 instanceof TypeConverter) {
            return (T) ObjectUtil.defaultIfNull(typeConverter3.convert(type, typeConverter3), t2);
        }
        Converter converter = getConverter(type, z2);
        if (converter != null) {
            return (T) converter.convert(typeConverter3, t2);
        }
        Class<?> cls = TypeUtil.getClass(type);
        if (cls == null) {
            if (t2 == null) {
                return (T) typeConverter3;
            }
            cls = t2.getClass();
        }
        T t3 = (T) convertSpecial(type, cls, typeConverter3, t2);
        if (t3 != null) {
            return t3;
        }
        if (BeanUtil.isBean(cls)) {
            return new BeanConverter(type).convert(typeConverter3, t2);
        }
        throw new ConvertException("Can not Converter from [{}] to [{}]", typeConverter3.getClass().getName(), type.getTypeName());
    }

    public <T> Converter<T> getConverter(Type type, boolean z2) {
        if (z2) {
            Converter<T> customConverter = getCustomConverter(type);
            return customConverter == null ? getDefaultConverter(type) : customConverter;
        }
        Converter<T> defaultConverter = getDefaultConverter(type);
        return defaultConverter == null ? getCustomConverter(type) : defaultConverter;
    }

    public <T> Converter<T> getCustomConverter(Type type) {
        if (this.customConverterMap == null) {
            return null;
        }
        return (Converter) this.customConverterMap.get(type);
    }

    public <T> Converter<T> getDefaultConverter(Type type) {
        Map<Class<?>, Converter<?>> map = this.defaultConverterMap;
        if (map == null) {
            return null;
        }
        return (Converter) map.get(TypeUtil.getClass(type));
    }

    public ConverterRegistry putCustom(Type type, Class<? extends Converter<?>> cls) {
        return putCustom(type, (Converter<?>) ReflectUtil.newInstance(cls, new Object[0]));
    }

    public ConverterRegistry putCustom(Type type, Converter<?> converter) {
        if (this.customConverterMap == null) {
            synchronized (this) {
                if (this.customConverterMap == null) {
                    this.customConverterMap = new SafeConcurrentHashMap();
                }
            }
        }
        this.customConverterMap.put(type, converter);
        return this;
    }

    public <T> T convert(Type type, Object obj, T t2) throws ConvertException {
        return (T) convert(type, obj, t2, true);
    }

    public <T> T convert(Type type, Object obj) throws ConvertException {
        return (T) convert(type, obj, null);
    }
}
