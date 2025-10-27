package cn.hutool.core.builder;

import cn.hutool.core.lang.Pair;
import cn.hutool.core.util.ArrayUtil;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/* loaded from: classes.dex */
public class EqualsBuilder implements Builder<Boolean> {
    private static final ThreadLocal<Set<Pair<IDKey, IDKey>>> REGISTRY = new ThreadLocal<>();
    private static final long serialVersionUID = 1;
    private boolean isEquals = true;

    public static Pair<IDKey, IDKey> getRegisterPair(Object obj, Object obj2) {
        return new Pair<>(new IDKey(obj), new IDKey(obj2));
    }

    public static Set<Pair<IDKey, IDKey>> getRegistry() {
        return REGISTRY.get();
    }

    public static boolean isRegistered(Object obj, Object obj2) {
        Set<Pair<IDKey, IDKey>> registry = getRegistry();
        Pair<IDKey, IDKey> registerPair = getRegisterPair(obj, obj2);
        return registry != null && (registry.contains(registerPair) || registry.contains(new Pair(registerPair.getKey(), registerPair.getValue())));
    }

    private static void reflectionAppend(Object obj, Object obj2, Class<?> cls, EqualsBuilder equalsBuilder, boolean z2, String[] strArr) {
        if (isRegistered(obj, obj2)) {
            return;
        }
        try {
            register(obj, obj2);
            Field[] declaredFields = cls.getDeclaredFields();
            AccessibleObject.setAccessible(declaredFields, true);
            for (int i2 = 0; i2 < declaredFields.length && equalsBuilder.isEquals; i2++) {
                Field field = declaredFields[i2];
                if (!ArrayUtil.contains(strArr, field.getName()) && field.getName().indexOf(36) == -1 && ((z2 || !Modifier.isTransient(field.getModifiers())) && !Modifier.isStatic(field.getModifiers()))) {
                    try {
                        equalsBuilder.append(field.get(obj), field.get(obj2));
                    } catch (IllegalAccessException unused) {
                        throw new InternalError("Unexpected IllegalAccessException");
                    }
                }
            }
        } finally {
            unregister(obj, obj2);
        }
    }

    public static boolean reflectionEquals(Object obj, Object obj2, Collection<String> collection) {
        return reflectionEquals(obj, obj2, (String[]) ArrayUtil.toArray((Collection) collection, String.class));
    }

    public static void register(Object obj, Object obj2) {
        synchronized (EqualsBuilder.class) {
            if (getRegistry() == null) {
                REGISTRY.set(new HashSet());
            }
        }
        getRegistry().add(getRegisterPair(obj, obj2));
    }

    public static void unregister(Object obj, Object obj2) {
        Set<Pair<IDKey, IDKey>> registry = getRegistry();
        if (registry != null) {
            registry.remove(getRegisterPair(obj, obj2));
            synchronized (EqualsBuilder.class) {
                Set<Pair<IDKey, IDKey>> registry2 = getRegistry();
                if (registry2 != null && registry2.isEmpty()) {
                    REGISTRY.remove();
                }
            }
        }
    }

    public EqualsBuilder append(Object obj, Object obj2) {
        return (this.isEquals && obj != obj2) ? (obj == null || obj2 == null) ? setEquals(false) : ArrayUtil.isArray(obj) ? setEquals(ArrayUtil.equals(obj, obj2)) : setEquals(obj.equals(obj2)) : this;
    }

    public EqualsBuilder appendSuper(boolean z2) {
        if (!this.isEquals) {
            return this;
        }
        this.isEquals = z2;
        return this;
    }

    public boolean isEquals() {
        return this.isEquals;
    }

    public void reset() {
        this.isEquals = true;
    }

    public EqualsBuilder setEquals(boolean z2) {
        this.isEquals = z2;
        return this;
    }

    public static boolean reflectionEquals(Object obj, Object obj2, String... strArr) {
        return reflectionEquals(obj, obj2, false, null, strArr);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // cn.hutool.core.builder.Builder
    public Boolean build() {
        return Boolean.valueOf(isEquals());
    }

    public static boolean reflectionEquals(Object obj, Object obj2, boolean z2) {
        return reflectionEquals(obj, obj2, z2, null, new String[0]);
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x002d  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0039 A[Catch: IllegalArgumentException -> 0x0061, TryCatch #0 {IllegalArgumentException -> 0x0061, blocks: (B:21:0x0033, B:23:0x0039, B:24:0x003d, B:25:0x0046, B:28:0x004e), top: B:34:0x0033 }] */
    /* JADX WARN: Removed duplicated region for block: B:24:0x003d A[Catch: IllegalArgumentException -> 0x0061, TryCatch #0 {IllegalArgumentException -> 0x0061, blocks: (B:21:0x0033, B:23:0x0039, B:24:0x003d, B:25:0x0046, B:28:0x004e), top: B:34:0x0033 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean reflectionEquals(java.lang.Object r11, java.lang.Object r12, boolean r13, java.lang.Class<?> r14, java.lang.String... r15) {
        /*
            if (r11 != r12) goto L4
            r11 = 1
            return r11
        L4:
            r0 = 0
            if (r11 == 0) goto L61
            if (r12 != 0) goto Lb
            goto L61
        Lb:
            java.lang.Class r1 = r11.getClass()
            java.lang.Class r2 = r12.getClass()
            boolean r3 = r1.isInstance(r12)
            if (r3 == 0) goto L20
            boolean r3 = r2.isInstance(r11)
            if (r3 != 0) goto L2e
            goto L2d
        L20:
            boolean r3 = r2.isInstance(r11)
            if (r3 == 0) goto L61
            boolean r3 = r1.isInstance(r12)
            if (r3 != 0) goto L2d
            goto L2e
        L2d:
            r1 = r2
        L2e:
            cn.hutool.core.builder.EqualsBuilder r10 = new cn.hutool.core.builder.EqualsBuilder
            r10.<init>()
            boolean r2 = r1.isArray()     // Catch: java.lang.IllegalArgumentException -> L61
            if (r2 == 0) goto L3d
            r10.append(r11, r12)     // Catch: java.lang.IllegalArgumentException -> L61
            goto L5c
        L3d:
            r4 = r11
            r5 = r12
            r6 = r1
            r7 = r10
            r8 = r13
            r9 = r15
            reflectionAppend(r4, r5, r6, r7, r8, r9)     // Catch: java.lang.IllegalArgumentException -> L61
        L46:
            java.lang.Class r2 = r1.getSuperclass()     // Catch: java.lang.IllegalArgumentException -> L61
            if (r2 == 0) goto L5c
            if (r1 == r14) goto L5c
            java.lang.Class r1 = r1.getSuperclass()     // Catch: java.lang.IllegalArgumentException -> L61
            r2 = r11
            r3 = r12
            r4 = r1
            r5 = r10
            r6 = r13
            r7 = r15
            reflectionAppend(r2, r3, r4, r5, r6, r7)     // Catch: java.lang.IllegalArgumentException -> L61
            goto L46
        L5c:
            boolean r11 = r10.isEquals()
            return r11
        L61:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.hutool.core.builder.EqualsBuilder.reflectionEquals(java.lang.Object, java.lang.Object, boolean, java.lang.Class, java.lang.String[]):boolean");
    }

    public EqualsBuilder append(long j2, long j3) {
        if (!this.isEquals) {
            return this;
        }
        this.isEquals = j2 == j3;
        return this;
    }

    public EqualsBuilder append(int i2, int i3) {
        if (!this.isEquals) {
            return this;
        }
        this.isEquals = i2 == i3;
        return this;
    }

    public EqualsBuilder append(short s2, short s3) {
        if (!this.isEquals) {
            return this;
        }
        this.isEquals = s2 == s3;
        return this;
    }

    public EqualsBuilder append(char c3, char c4) {
        if (!this.isEquals) {
            return this;
        }
        this.isEquals = c3 == c4;
        return this;
    }

    public EqualsBuilder append(byte b3, byte b4) {
        if (!this.isEquals) {
            return this;
        }
        this.isEquals = b3 == b4;
        return this;
    }

    public EqualsBuilder append(double d3, double d4) {
        return !this.isEquals ? this : append(Double.doubleToLongBits(d3), Double.doubleToLongBits(d4));
    }

    public EqualsBuilder append(float f2, float f3) {
        return !this.isEquals ? this : append(Float.floatToIntBits(f2), Float.floatToIntBits(f3));
    }

    public EqualsBuilder append(boolean z2, boolean z3) {
        if (!this.isEquals) {
            return this;
        }
        this.isEquals = z2 == z3;
        return this;
    }
}
