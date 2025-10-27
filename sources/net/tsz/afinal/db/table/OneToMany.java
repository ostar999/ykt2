package net.tsz.afinal.db.table;

/* loaded from: classes9.dex */
public class OneToMany extends Property {
    private Class<?> oneClass;

    public Class<?> getOneClass() {
        return this.oneClass;
    }

    public void setOneClass(Class<?> cls) {
        this.oneClass = cls;
    }
}
