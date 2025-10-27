package com.tencent.tbs.one.impl.e;

/* loaded from: classes6.dex */
public final class e<T> {

    /* renamed from: a, reason: collision with root package name */
    public a f22176a;

    /* renamed from: b, reason: collision with root package name */
    public T f22177b;

    /* renamed from: c, reason: collision with root package name */
    public Object f22178c;

    public enum a {
        EXISTING,
        BUILTIN,
        BUILTIN_ASSETS,
        LOCAL,
        ONLINE,
        EXTENSION,
        LOCAL_FILE,
        LEGACY_LOCAL_FILE,
        SHARING,
        LEGACY_SHARING
    }

    public static <T> e<T> a(a aVar, T t2) {
        e<T> eVar = new e<>();
        eVar.f22176a = aVar;
        eVar.f22177b = t2;
        return eVar;
    }
}
