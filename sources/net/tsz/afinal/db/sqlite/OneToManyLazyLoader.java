package net.tsz.afinal.db.sqlite;

import java.util.ArrayList;
import java.util.List;
import net.tsz.afinal.FinalDb;

/* loaded from: classes9.dex */
public class OneToManyLazyLoader<O, M> {
    FinalDb db;
    List<M> entities;
    Class<M> listItemClazz;
    Class<O> ownerClazz;
    O ownerEntity;

    public OneToManyLazyLoader(O o2, Class<O> cls, Class<M> cls2, FinalDb finalDb) {
        this.ownerEntity = o2;
        this.ownerClazz = cls;
        this.listItemClazz = cls2;
        this.db = finalDb;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public List<M> getList() {
        if (this.entities == null) {
            this.db.loadOneToMany(this.ownerEntity, this.ownerClazz, this.listItemClazz);
        }
        if (this.entities == null) {
            this.entities = new ArrayList();
        }
        return this.entities;
    }

    public void setList(List<M> list) {
        this.entities = list;
    }
}
