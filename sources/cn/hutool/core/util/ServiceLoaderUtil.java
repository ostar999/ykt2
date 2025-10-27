package cn.hutool.core.util;

import cn.hutool.core.collection.ListUtil;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;

/* loaded from: classes.dex */
public class ServiceLoaderUtil {
    public static <T> ServiceLoader<T> load(Class<T> cls) {
        return load(cls, null);
    }

    public static <T> T loadFirst(Class<T> cls) {
        Iterator it = load(cls).iterator();
        if (it.hasNext()) {
            return (T) it.next();
        }
        return null;
    }

    public static <T> T loadFirstAvailable(Class<T> cls) {
        Iterator it = load(cls).iterator();
        while (it.hasNext()) {
            try {
                return (T) it.next();
            } catch (ServiceConfigurationError unused) {
            }
        }
        return null;
    }

    public static <T> List<T> loadList(Class<T> cls) {
        return loadList(cls, null);
    }

    public static <T> ServiceLoader<T> load(Class<T> cls, ClassLoader classLoader) {
        return ServiceLoader.load(cls, (ClassLoader) ObjectUtil.defaultIfNull(classLoader, new cn.hutool.core.compiler.b()));
    }

    public static <T> List<T> loadList(Class<T> cls, ClassLoader classLoader) {
        return ListUtil.list(false, (Iterable) load(cls, classLoader));
    }
}
