package de.greenrobot.dao;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import de.greenrobot.dao.async.AsyncSession;
import de.greenrobot.dao.query.QueryBuilder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

/* loaded from: classes8.dex */
public class AbstractDaoSession {
    private final SQLiteDatabase db;
    private final Map<Class<?>, AbstractDao<?, ?>> entityToDao = new HashMap();

    public AbstractDaoSession(SQLiteDatabase sQLiteDatabase) {
        this.db = sQLiteDatabase;
    }

    public <V> V callInTx(Callable<V> callable) throws Exception {
        this.db.beginTransaction();
        try {
            V vCall = callable.call();
            this.db.setTransactionSuccessful();
            return vCall;
        } finally {
            this.db.endTransaction();
        }
    }

    public <V> V callInTxNoException(Callable<V> callable) {
        this.db.beginTransaction();
        try {
            try {
                V vCall = callable.call();
                this.db.setTransactionSuccessful();
                return vCall;
            } catch (Exception e2) {
                throw new DaoException("Callable failed", e2);
            }
        } finally {
            this.db.endTransaction();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public <T> void delete(T t2) {
        getDao(t2.getClass()).delete(t2);
    }

    public <T> void deleteAll(Class<T> cls) throws SQLException {
        getDao(cls).deleteAll();
    }

    public AbstractDao<?, ?> getDao(Class<? extends Object> cls) {
        AbstractDao<?, ?> abstractDao = this.entityToDao.get(cls);
        if (abstractDao != null) {
            return abstractDao;
        }
        throw new DaoException("No DAO registered for " + cls);
    }

    public SQLiteDatabase getDatabase() {
        return this.db;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public <T> long insert(T t2) {
        return getDao(t2.getClass()).insert(t2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public <T> long insertOrReplace(T t2) {
        return getDao(t2.getClass()).insertOrReplace(t2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public <T, K> T load(Class<T> cls, K k2) {
        return (T) getDao(cls).load(k2);
    }

    public <T, K> List<T> loadAll(Class<T> cls) {
        return (List<T>) getDao(cls).loadAll();
    }

    public <T> QueryBuilder<T> queryBuilder(Class<T> cls) {
        return (QueryBuilder<T>) getDao(cls).queryBuilder();
    }

    public <T, K> List<T> queryRaw(Class<T> cls, String str, String... strArr) {
        return (List<T>) getDao(cls).queryRaw(str, strArr);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public <T> void refresh(T t2) {
        getDao(t2.getClass()).refresh(t2);
    }

    public <T> void registerDao(Class<T> cls, AbstractDao<T, ?> abstractDao) {
        this.entityToDao.put(cls, abstractDao);
    }

    public void runInTx(Runnable runnable) {
        this.db.beginTransaction();
        try {
            runnable.run();
            this.db.setTransactionSuccessful();
        } finally {
            this.db.endTransaction();
        }
    }

    public AsyncSession startAsyncSession() {
        return new AsyncSession(this);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public <T> void update(T t2) {
        getDao(t2.getClass()).update(t2);
    }
}
