package de.greenrobot.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import de.greenrobot.dao.identityscope.IdentityScope;
import de.greenrobot.dao.internal.DaoConfig;

/* loaded from: classes8.dex */
public class InternalUnitTestDaoAccess<T, K> {
    private final AbstractDao<T, K> dao;

    public InternalUnitTestDaoAccess(SQLiteDatabase sQLiteDatabase, Class<AbstractDao<T, K>> cls, IdentityScope<?, ?> identityScope) throws Exception {
        DaoConfig daoConfig = new DaoConfig(sQLiteDatabase, cls);
        daoConfig.setIdentityScope(identityScope);
        this.dao = cls.getConstructor(DaoConfig.class).newInstance(daoConfig);
    }

    public AbstractDao<T, K> getDao() {
        return this.dao;
    }

    public K getKey(T t2) {
        return this.dao.getKey(t2);
    }

    public Property[] getProperties() {
        return this.dao.getProperties();
    }

    public boolean isEntityUpdateable() {
        return this.dao.isEntityUpdateable();
    }

    public T readEntity(Cursor cursor, int i2) {
        return this.dao.readEntity(cursor, i2);
    }

    public K readKey(Cursor cursor, int i2) {
        return this.dao.readKey(cursor, i2);
    }
}
