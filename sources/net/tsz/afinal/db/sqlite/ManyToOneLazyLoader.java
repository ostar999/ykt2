package net.tsz.afinal.db.sqlite;

import net.tsz.afinal.FinalDb;

/* loaded from: classes9.dex */
public class ManyToOneLazyLoader<M, O> {
    FinalDb db;
    boolean hasLoaded = false;
    Class<M> manyClazz;
    M manyEntity;
    Class<O> oneClazz;
    O oneEntity;

    public ManyToOneLazyLoader(M m2, Class<M> cls, Class<O> cls2, FinalDb finalDb) {
        this.manyEntity = m2;
        this.manyClazz = cls;
        this.oneClazz = cls2;
        this.db = finalDb;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public O get() {
        if (this.oneEntity == null && !this.hasLoaded) {
            this.db.loadManyToOne(this.manyEntity, this.manyClazz, this.oneClazz);
            this.hasLoaded = true;
        }
        return this.oneEntity;
    }

    public void set(O o2) {
        this.oneEntity = o2;
    }
}
