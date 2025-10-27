package cn.hutool.core.lang;

import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.io.resource.Resource;
import cn.hutool.core.util.ObjectUtil;
import java.security.SecureClassLoader;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/* loaded from: classes.dex */
public class ResourceClassLoader<T extends Resource> extends SecureClassLoader {
    private final Map<String, Class<?>> cacheClassMap;
    private final Map<String, T> resourceMap;

    public ResourceClassLoader(ClassLoader classLoader, Map<String, T> map) {
        super((ClassLoader) ObjectUtil.defaultIfNull(classLoader, new cn.hutool.core.compiler.b()));
        this.resourceMap = (Map) ObjectUtil.defaultIfNull((HashMap) map, new HashMap());
        this.cacheClassMap = new HashMap();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Class<?> defineByName(String str) throws IORuntimeException {
        T t2 = this.resourceMap.get(str);
        if (t2 == null) {
            return null;
        }
        byte[] bytes = t2.readBytes();
        return defineClass(str, bytes, 0, bytes.length);
    }

    public ResourceClassLoader<T> addResource(T t2) {
        this.resourceMap.put(t2.getName(), t2);
        return this;
    }

    @Override // java.lang.ClassLoader
    public Class<?> findClass(String str) throws ClassNotFoundException {
        Class<?> cls = (Class) this.cacheClassMap.computeIfAbsent(str, new Function() { // from class: cn.hutool.core.lang.g0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f2487c.defineByName((String) obj);
            }
        });
        return cls == null ? super.findClass(str) : cls;
    }
}
