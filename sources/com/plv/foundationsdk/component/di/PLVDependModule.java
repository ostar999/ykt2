package com.plv.foundationsdk.component.di;

import androidx.annotation.NonNull;
import com.plv.foundationsdk.utils.PLVReflectionUtil;
import com.plv.foundationsdk.utils.PLVSugarUtil;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes4.dex */
public class PLVDependModule {
    final Map<String, LazyProvider> dependencies = new HashMap(16);

    public static abstract class LazyProvider<T> {
        private final Class<T> paramClass = (Class) PLVSugarUtil.requireNotNull(getSuperclassTypeParameter(PLVReflectionUtil.getClassNoInline(this)));

        private Class<T> getSuperclassTypeParameter(Class<?> cls) {
            Type genericSuperclass = cls.getGenericSuperclass();
            if (genericSuperclass instanceof ParameterizedType) {
                return (Class) ((ParameterizedType) genericSuperclass).getActualTypeArguments()[0];
            }
            return null;
        }

        @NonNull
        public abstract T onProvide();
    }

    @NonNull
    public final synchronized <T> T get(@NonNull Class<T> cls) {
        return (T) get(cls.getName());
    }

    public final <T> void provide(@NonNull LazyProvider<T> lazyProvider) {
        provide(((LazyProvider) lazyProvider).paramClass.getName(), lazyProvider);
    }

    @NonNull
    public final synchronized <T> T get(@NonNull String str) {
        return (T) PLVDependManager.getInstance().get(str);
    }

    public final <T> void provide(@NonNull Class<T> cls, @NonNull LazyProvider<T> lazyProvider) {
        provide(cls.getName(), lazyProvider);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final <T> void provide(@NonNull String str, @NonNull LazyProvider<T> lazyProvider) {
        if (!this.dependencies.containsKey(str)) {
            this.dependencies.put(PLVSugarUtil.requireNotNull(str), PLVSugarUtil.requireNotNull(lazyProvider));
            return;
        }
        throw new PLVDependencyConflictException("Dependency " + str + " already exists.");
    }
}
