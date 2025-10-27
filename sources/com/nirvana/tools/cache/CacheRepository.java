package com.nirvana.tools.cache;

import com.nirvana.tools.cache.RepositoryTemplate;

/* loaded from: classes4.dex */
public abstract class CacheRepository<T extends RepositoryTemplate> {
    private T mTemplate;

    public CacheRepository(T t2) {
        this.mTemplate = t2;
    }

    public abstract void clear();

    public T getTemplate() {
        return this.mTemplate;
    }

    public abstract String read();

    public abstract void write(String str);
}
