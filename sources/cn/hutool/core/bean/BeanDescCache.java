package cn.hutool.core.bean;

import cn.hutool.core.lang.func.Func0;
import cn.hutool.core.map.WeakConcurrentMap;
import java.util.function.Function;

/* loaded from: classes.dex */
public enum BeanDescCache {
    INSTANCE;

    private final WeakConcurrentMap<Class<?>, BeanDesc> bdCache = new WeakConcurrentMap<>();

    BeanDescCache() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ BeanDesc lambda$getBeanDesc$0(Func0 func0, Class cls) {
        return (BeanDesc) func0.callWithRuntimeException();
    }

    public void clear() {
        this.bdCache.clear();
    }

    public BeanDesc getBeanDesc(Class<?> cls, final Func0<BeanDesc> func0) {
        return this.bdCache.computeIfAbsent((WeakConcurrentMap<Class<?>, BeanDesc>) cls, (Function<? super WeakConcurrentMap<Class<?>, BeanDesc>, ? extends BeanDesc>) new Function() { // from class: cn.hutool.core.bean.c
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return BeanDescCache.lambda$getBeanDesc$0(func0, (Class) obj);
            }
        });
    }
}
