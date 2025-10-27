package cn.hutool.core.builder;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ArrayUtil;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/* loaded from: classes.dex */
public class HashCodeBuilder implements Builder<Integer> {
    private static final int DEFAULT_INITIAL_VALUE = 17;
    private static final int DEFAULT_MULTIPLIER_VALUE = 37;
    private static final ThreadLocal<Set<IDKey>> REGISTRY = new ThreadLocal<>();
    private static final long serialVersionUID = 1;
    private final int iConstant;
    private int iTotal;

    public HashCodeBuilder() {
        this.iConstant = 37;
        this.iTotal = 17;
    }

    private static Set<IDKey> getRegistry() {
        return REGISTRY.get();
    }

    private static boolean isRegistered(Object obj) {
        Set<IDKey> registry = getRegistry();
        return registry != null && registry.contains(new IDKey(obj));
    }

    private static void reflectionAppend(Object obj, Class<?> cls, HashCodeBuilder hashCodeBuilder, boolean z2, String[] strArr) {
        if (isRegistered(obj)) {
            return;
        }
        try {
            register(obj);
            Field[] declaredFields = cls.getDeclaredFields();
            AccessibleObject.setAccessible(declaredFields, true);
            for (Field field : declaredFields) {
                if (!ArrayUtil.contains(strArr, field.getName()) && field.getName().indexOf(36) == -1 && ((z2 || !Modifier.isTransient(field.getModifiers())) && !Modifier.isStatic(field.getModifiers()))) {
                    try {
                        hashCodeBuilder.append(field.get(obj));
                    } catch (IllegalAccessException unused) {
                        throw new InternalError("Unexpected IllegalAccessException");
                    }
                }
            }
        } finally {
            unregister(obj);
        }
    }

    public static int reflectionHashCode(int i2, int i3, Object obj) {
        return reflectionHashCode(i2, i3, obj, false, null, new String[0]);
    }

    public static void register(Object obj) {
        synchronized (HashCodeBuilder.class) {
            if (getRegistry() == null) {
                REGISTRY.set(new HashSet());
            }
        }
        getRegistry().add(new IDKey(obj));
    }

    public static void unregister(Object obj) {
        Set<IDKey> registry = getRegistry();
        if (registry != null) {
            registry.remove(new IDKey(obj));
            synchronized (HashCodeBuilder.class) {
                Set<IDKey> registry2 = getRegistry();
                if (registry2 != null && registry2.isEmpty()) {
                    REGISTRY.remove();
                }
            }
        }
    }

    public HashCodeBuilder append(boolean z2) {
        this.iTotal = (this.iTotal * this.iConstant) + (!z2 ? 1 : 0);
        return this;
    }

    public HashCodeBuilder appendSuper(int i2) {
        this.iTotal = (this.iTotal * this.iConstant) + i2;
        return this;
    }

    public int hashCode() {
        return toHashCode();
    }

    public int toHashCode() {
        return this.iTotal;
    }

    public static int reflectionHashCode(int i2, int i3, Object obj, boolean z2) {
        return reflectionHashCode(i2, i3, obj, z2, null, new String[0]);
    }

    public HashCodeBuilder append(boolean[] zArr) {
        if (zArr == null) {
            this.iTotal *= this.iConstant;
        } else {
            for (boolean z2 : zArr) {
                append(z2);
            }
        }
        return this;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // cn.hutool.core.builder.Builder
    public Integer build() {
        return Integer.valueOf(toHashCode());
    }

    public static <T> int reflectionHashCode(int i2, int i3, T t2, boolean z2, Class<? super T> cls, String... strArr) {
        if (t2 != null) {
            HashCodeBuilder hashCodeBuilder = new HashCodeBuilder(i2, i3);
            Class<?> superclass = t2.getClass();
            reflectionAppend(t2, superclass, hashCodeBuilder, z2, strArr);
            while (superclass.getSuperclass() != null && superclass != cls) {
                superclass = superclass.getSuperclass();
                reflectionAppend(t2, superclass, hashCodeBuilder, z2, strArr);
            }
            return hashCodeBuilder.toHashCode();
        }
        throw new IllegalArgumentException("The object to build a hash code for must not be null");
    }

    public HashCodeBuilder(int i2, int i3) throws Throwable {
        Assert.isTrue(i2 % 2 != 0, "HashCodeBuilder requires an odd initial value", new Object[0]);
        Assert.isTrue(i3 % 2 != 0, "HashCodeBuilder requires an odd multiplier", new Object[0]);
        this.iConstant = i3;
        this.iTotal = i2;
    }

    public HashCodeBuilder append(byte b3) {
        this.iTotal = (this.iTotal * this.iConstant) + b3;
        return this;
    }

    public HashCodeBuilder append(byte[] bArr) {
        if (bArr == null) {
            this.iTotal *= this.iConstant;
        } else {
            for (byte b3 : bArr) {
                append(b3);
            }
        }
        return this;
    }

    public HashCodeBuilder append(char c3) {
        this.iTotal = (this.iTotal * this.iConstant) + c3;
        return this;
    }

    public HashCodeBuilder append(char[] cArr) {
        if (cArr == null) {
            this.iTotal *= this.iConstant;
        } else {
            for (char c3 : cArr) {
                append(c3);
            }
        }
        return this;
    }

    public static int reflectionHashCode(Object obj, boolean z2) {
        return reflectionHashCode(17, 37, obj, z2, null, new String[0]);
    }

    public static int reflectionHashCode(Object obj, Collection<String> collection) {
        return reflectionHashCode(obj, (String[]) ArrayUtil.toArray((Collection) collection, String.class));
    }

    public static int reflectionHashCode(Object obj, String... strArr) {
        return reflectionHashCode(17, 37, obj, false, null, strArr);
    }

    public HashCodeBuilder append(double d3) {
        return append(Double.doubleToLongBits(d3));
    }

    public HashCodeBuilder append(double[] dArr) {
        if (dArr == null) {
            this.iTotal *= this.iConstant;
        } else {
            for (double d3 : dArr) {
                append(d3);
            }
        }
        return this;
    }

    public HashCodeBuilder append(float f2) {
        this.iTotal = (this.iTotal * this.iConstant) + Float.floatToIntBits(f2);
        return this;
    }

    public HashCodeBuilder append(float[] fArr) {
        if (fArr == null) {
            this.iTotal *= this.iConstant;
        } else {
            for (float f2 : fArr) {
                append(f2);
            }
        }
        return this;
    }

    public HashCodeBuilder append(int i2) {
        this.iTotal = (this.iTotal * this.iConstant) + i2;
        return this;
    }

    public HashCodeBuilder append(int[] iArr) {
        if (iArr == null) {
            this.iTotal *= this.iConstant;
        } else {
            for (int i2 : iArr) {
                append(i2);
            }
        }
        return this;
    }

    public HashCodeBuilder append(long j2) {
        this.iTotal = (this.iTotal * this.iConstant) + ((int) (j2 ^ (j2 >> 32)));
        return this;
    }

    public HashCodeBuilder append(long[] jArr) {
        if (jArr == null) {
            this.iTotal *= this.iConstant;
        } else {
            for (long j2 : jArr) {
                append(j2);
            }
        }
        return this;
    }

    public HashCodeBuilder append(Object obj) {
        if (obj == null) {
            this.iTotal *= this.iConstant;
        } else if (obj.getClass().isArray()) {
            if (obj instanceof long[]) {
                append((long[]) obj);
            } else if (obj instanceof int[]) {
                append((int[]) obj);
            } else if (obj instanceof short[]) {
                append((short[]) obj);
            } else if (obj instanceof char[]) {
                append((char[]) obj);
            } else if (obj instanceof byte[]) {
                append((byte[]) obj);
            } else if (obj instanceof double[]) {
                append((double[]) obj);
            } else if (obj instanceof float[]) {
                append((float[]) obj);
            } else if (obj instanceof boolean[]) {
                append((boolean[]) obj);
            } else {
                append((Object[]) obj);
            }
        } else {
            this.iTotal = (this.iTotal * this.iConstant) + obj.hashCode();
        }
        return this;
    }

    public HashCodeBuilder append(Object[] objArr) {
        if (objArr == null) {
            this.iTotal *= this.iConstant;
        } else {
            for (Object obj : objArr) {
                append(obj);
            }
        }
        return this;
    }

    public HashCodeBuilder append(short s2) {
        this.iTotal = (this.iTotal * this.iConstant) + s2;
        return this;
    }

    public HashCodeBuilder append(short[] sArr) {
        if (sArr == null) {
            this.iTotal *= this.iConstant;
        } else {
            for (short s2 : sArr) {
                append(s2);
            }
        }
        return this;
    }
}
