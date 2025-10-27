package kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* loaded from: classes8.dex */
final class Java16SealedRecordLoader {

    @NotNull
    public static final Java16SealedRecordLoader INSTANCE = new Java16SealedRecordLoader();

    @Nullable
    private static Cache _cache;

    public static final class Cache {

        @Nullable
        private final Method getPermittedSubclasses;

        @Nullable
        private final Method getRecordComponents;

        @Nullable
        private final Method isRecord;

        @Nullable
        private final Method isSealed;

        public Cache(@Nullable Method method, @Nullable Method method2, @Nullable Method method3, @Nullable Method method4) {
            this.isSealed = method;
            this.getPermittedSubclasses = method2;
            this.isRecord = method3;
            this.getRecordComponents = method4;
        }

        @Nullable
        public final Method getGetPermittedSubclasses() {
            return this.getPermittedSubclasses;
        }

        @Nullable
        public final Method getGetRecordComponents() {
            return this.getRecordComponents;
        }

        @Nullable
        public final Method isRecord() {
            return this.isRecord;
        }

        @Nullable
        public final Method isSealed() {
            return this.isSealed;
        }
    }

    private Java16SealedRecordLoader() {
    }

    private final Cache buildCache() {
        try {
            return new Cache(Class.class.getMethod("isSealed", new Class[0]), Class.class.getMethod("getPermittedSubclasses", new Class[0]), Class.class.getMethod("isRecord", new Class[0]), Class.class.getMethod("getRecordComponents", new Class[0]));
        } catch (NoSuchMethodException unused) {
            return new Cache(null, null, null, null);
        }
    }

    private final Cache initCache() {
        Cache cache = _cache;
        if (cache != null) {
            return cache;
        }
        Cache cacheBuildCache = buildCache();
        _cache = cacheBuildCache;
        return cacheBuildCache;
    }

    @Nullable
    public final Class<?>[] loadGetPermittedSubclasses(@NotNull Class<?> clazz) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Intrinsics.checkNotNullParameter(clazz, "clazz");
        Method getPermittedSubclasses = initCache().getGetPermittedSubclasses();
        if (getPermittedSubclasses == null) {
            return null;
        }
        Object objInvoke = getPermittedSubclasses.invoke(clazz, new Object[0]);
        Intrinsics.checkNotNull(objInvoke, "null cannot be cast to non-null type kotlin.Array<java.lang.Class<*>>");
        return (Class[]) objInvoke;
    }

    @Nullable
    public final Object[] loadGetRecordComponents(@NotNull Class<?> clazz) {
        Intrinsics.checkNotNullParameter(clazz, "clazz");
        Method getRecordComponents = initCache().getGetRecordComponents();
        if (getRecordComponents == null) {
            return null;
        }
        return (Object[]) getRecordComponents.invoke(clazz, new Object[0]);
    }

    @Nullable
    public final Boolean loadIsRecord(@NotNull Class<?> clazz) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Intrinsics.checkNotNullParameter(clazz, "clazz");
        Method methodIsRecord = initCache().isRecord();
        if (methodIsRecord == null) {
            return null;
        }
        Object objInvoke = methodIsRecord.invoke(clazz, new Object[0]);
        Intrinsics.checkNotNull(objInvoke, "null cannot be cast to non-null type kotlin.Boolean");
        return (Boolean) objInvoke;
    }

    @Nullable
    public final Boolean loadIsSealed(@NotNull Class<?> clazz) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Intrinsics.checkNotNullParameter(clazz, "clazz");
        Method methodIsSealed = initCache().isSealed();
        if (methodIsSealed == null) {
            return null;
        }
        Object objInvoke = methodIsSealed.invoke(clazz, new Object[0]);
        Intrinsics.checkNotNull(objInvoke, "null cannot be cast to non-null type kotlin.Boolean");
        return (Boolean) objInvoke;
    }
}
