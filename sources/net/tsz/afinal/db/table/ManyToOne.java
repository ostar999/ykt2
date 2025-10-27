package net.tsz.afinal.db.table;

/* loaded from: classes9.dex */
public class ManyToOne extends Property {
    private Class<?> manyClass;

    public Class<?> getManyClass() {
        return this.manyClass;
    }

    public void setManyClass(Class<?> cls) {
        this.manyClass = cls;
    }
}
