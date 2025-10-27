package cn.hutool.core.bean;

import cn.hutool.core.lang.func.Func0;
import cn.hutool.core.map.ReferenceConcurrentMap;
import cn.hutool.core.map.WeakConcurrentMap;
import java.beans.PropertyDescriptor;
import java.util.Map;
import java.util.function.Function;

/* loaded from: classes.dex */
public enum BeanInfoCache {
    INSTANCE;

    private final WeakConcurrentMap<Class<?>, Map<String, PropertyDescriptor>> pdCache = new WeakConcurrentMap<>();
    private final WeakConcurrentMap<Class<?>, Map<String, PropertyDescriptor>> ignoreCasePdCache = new WeakConcurrentMap<>();

    BeanInfoCache() {
    }

    private ReferenceConcurrentMap<Class<?>, Map<String, PropertyDescriptor>> getCache(boolean z2) {
        return z2 ? this.ignoreCasePdCache : this.pdCache;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Map lambda$getPropertyDescriptorMap$0(Func0 func0, Class cls) {
        return (Map) func0.callWithRuntimeException();
    }

    public void clear() {
        this.pdCache.clear();
        this.ignoreCasePdCache.clear();
    }

    public Map<String, PropertyDescriptor> getPropertyDescriptorMap(Class<?> cls, boolean z2) {
        return getCache(z2).get(cls);
    }

    public void putPropertyDescriptorMap(Class<?> cls, Map<String, PropertyDescriptor> map, boolean z2) {
        getCache(z2).put(cls, map);
    }

    public Map<String, PropertyDescriptor> getPropertyDescriptorMap(Class<?> cls, boolean z2, final Func0<Map<String, PropertyDescriptor>> func0) {
        return getCache(z2).computeIfAbsent((ReferenceConcurrentMap<Class<?>, Map<String, PropertyDescriptor>>) cls, (Function<? super ReferenceConcurrentMap<Class<?>, Map<String, PropertyDescriptor>>, ? extends Map<String, PropertyDescriptor>>) new Function() { // from class: cn.hutool.core.bean.d
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return BeanInfoCache.lambda$getPropertyDescriptorMap$0(func0, (Class) obj);
            }
        });
    }
}
