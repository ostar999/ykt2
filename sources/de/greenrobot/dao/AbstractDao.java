package de.greenrobot.dao;

import android.database.CrossProcessCursor;
import android.database.Cursor;
import android.database.CursorWindow;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import cn.hutool.core.text.CharPool;
import de.greenrobot.dao.identityscope.IdentityScope;
import de.greenrobot.dao.identityscope.IdentityScopeLong;
import de.greenrobot.dao.internal.DaoConfig;
import de.greenrobot.dao.internal.FastCursor;
import de.greenrobot.dao.internal.TableStatements;
import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes8.dex */
public abstract class AbstractDao<T, K> {
    protected final DaoConfig config;
    protected final SQLiteDatabase db;
    protected IdentityScope<K, T> identityScope;
    protected IdentityScopeLong<T> identityScopeLong;
    protected final int pkOrdinal;
    protected final AbstractDaoSession session;
    protected TableStatements statements;

    public AbstractDao(DaoConfig daoConfig) {
        this(daoConfig, null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void deleteByKeyInsideSynchronized(K k2, SQLiteStatement sQLiteStatement) {
        if (k2 instanceof Long) {
            sQLiteStatement.bindLong(1, ((Long) k2).longValue());
        } else {
            if (k2 == 0) {
                throw new DaoException("Cannot delete entity, key is null");
            }
            sQLiteStatement.bindString(1, k2.toString());
        }
        sQLiteStatement.execute();
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x003e A[Catch: all -> 0x003a, TryCatch #1 {all -> 0x003a, blocks: (B:10:0x001f, B:11:0x0023, B:13:0x0029, B:15:0x0036, B:19:0x003e, B:20:0x0042, B:22:0x0048, B:24:0x0051), top: B:48:0x001f, outer: #0 }] */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0061 A[Catch: all -> 0x0079, Merged into TryCatch #2 {all -> 0x007c, blocks: (B:3:0x000e, B:34:0x0065, B:36:0x006c, B:38:0x0070, B:43:0x007b, B:4:0x000f, B:6:0x0013, B:30:0x005d, B:32:0x0061, B:33:0x0064, B:26:0x0055, B:28:0x0059, B:29:0x005c, B:10:0x001f, B:11:0x0023, B:13:0x0029, B:15:0x0036, B:19:0x003e, B:20:0x0042, B:22:0x0048, B:24:0x0051), top: B:50:0x000e }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void deleteInTxInternal(java.lang.Iterable<T> r4, java.lang.Iterable<K> r5) {
        /*
            r3 = this;
            r3.assertSinglePk()
            de.greenrobot.dao.internal.TableStatements r0 = r3.statements
            android.database.sqlite.SQLiteStatement r0 = r0.getDeleteStatement()
            android.database.sqlite.SQLiteDatabase r1 = r3.db
            r1.beginTransaction()
            monitor-enter(r0)     // Catch: java.lang.Throwable -> L7c
            de.greenrobot.dao.identityscope.IdentityScope<K, T> r1 = r3.identityScope     // Catch: java.lang.Throwable -> L79
            if (r1 == 0) goto L1c
            r1.lock()     // Catch: java.lang.Throwable -> L79
            java.util.ArrayList r1 = new java.util.ArrayList     // Catch: java.lang.Throwable -> L79
            r1.<init>()     // Catch: java.lang.Throwable -> L79
            goto L1d
        L1c:
            r1 = 0
        L1d:
            if (r4 == 0) goto L3c
            java.util.Iterator r4 = r4.iterator()     // Catch: java.lang.Throwable -> L3a
        L23:
            boolean r2 = r4.hasNext()     // Catch: java.lang.Throwable -> L3a
            if (r2 == 0) goto L3c
            java.lang.Object r2 = r4.next()     // Catch: java.lang.Throwable -> L3a
            java.lang.Object r2 = r3.getKeyVerified(r2)     // Catch: java.lang.Throwable -> L3a
            r3.deleteByKeyInsideSynchronized(r2, r0)     // Catch: java.lang.Throwable -> L3a
            if (r1 == 0) goto L23
            r1.add(r2)     // Catch: java.lang.Throwable -> L3a
            goto L23
        L3a:
            r4 = move-exception
            goto L55
        L3c:
            if (r5 == 0) goto L5d
            java.util.Iterator r4 = r5.iterator()     // Catch: java.lang.Throwable -> L3a
        L42:
            boolean r5 = r4.hasNext()     // Catch: java.lang.Throwable -> L3a
            if (r5 == 0) goto L5d
            java.lang.Object r5 = r4.next()     // Catch: java.lang.Throwable -> L3a
            r3.deleteByKeyInsideSynchronized(r5, r0)     // Catch: java.lang.Throwable -> L3a
            if (r1 == 0) goto L42
            r1.add(r5)     // Catch: java.lang.Throwable -> L3a
            goto L42
        L55:
            de.greenrobot.dao.identityscope.IdentityScope<K, T> r5 = r3.identityScope     // Catch: java.lang.Throwable -> L79
            if (r5 == 0) goto L5c
            r5.unlock()     // Catch: java.lang.Throwable -> L79
        L5c:
            throw r4     // Catch: java.lang.Throwable -> L79
        L5d:
            de.greenrobot.dao.identityscope.IdentityScope<K, T> r4 = r3.identityScope     // Catch: java.lang.Throwable -> L79
            if (r4 == 0) goto L64
            r4.unlock()     // Catch: java.lang.Throwable -> L79
        L64:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L79
            android.database.sqlite.SQLiteDatabase r4 = r3.db     // Catch: java.lang.Throwable -> L7c
            r4.setTransactionSuccessful()     // Catch: java.lang.Throwable -> L7c
            if (r1 == 0) goto L73
            de.greenrobot.dao.identityscope.IdentityScope<K, T> r4 = r3.identityScope     // Catch: java.lang.Throwable -> L7c
            if (r4 == 0) goto L73
            r4.remove(r1)     // Catch: java.lang.Throwable -> L7c
        L73:
            android.database.sqlite.SQLiteDatabase r4 = r3.db
            r4.endTransaction()
            return
        L79:
            r4 = move-exception
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L79
            throw r4     // Catch: java.lang.Throwable -> L7c
        L7c:
            r4 = move-exception
            android.database.sqlite.SQLiteDatabase r5 = r3.db
            r5.endTransaction()
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: de.greenrobot.dao.AbstractDao.deleteInTxInternal(java.lang.Iterable, java.lang.Iterable):void");
    }

    private long executeInsert(T t2, SQLiteStatement sQLiteStatement) {
        long jExecuteInsert;
        if (this.db.isDbLockedByCurrentThread()) {
            synchronized (sQLiteStatement) {
                bindValues(sQLiteStatement, t2);
                jExecuteInsert = sQLiteStatement.executeInsert();
            }
        } else {
            this.db.beginTransaction();
            try {
                synchronized (sQLiteStatement) {
                    bindValues(sQLiteStatement, t2);
                    jExecuteInsert = sQLiteStatement.executeInsert();
                }
                this.db.setTransactionSuccessful();
            } finally {
                this.db.endTransaction();
            }
        }
        updateKeyAfterInsertAndAttach(t2, jExecuteInsert, true);
        return jExecuteInsert;
    }

    private void executeInsertInTx(SQLiteStatement sQLiteStatement, Iterable<T> iterable, boolean z2) {
        this.db.beginTransaction();
        try {
            synchronized (sQLiteStatement) {
                IdentityScope<K, T> identityScope = this.identityScope;
                if (identityScope != null) {
                    identityScope.lock();
                }
                try {
                    for (T t2 : iterable) {
                        bindValues(sQLiteStatement, t2);
                        if (z2) {
                            updateKeyAfterInsertAndAttach(t2, sQLiteStatement.executeInsert(), false);
                        } else {
                            sQLiteStatement.execute();
                        }
                    }
                } finally {
                    IdentityScope<K, T> identityScope2 = this.identityScope;
                    if (identityScope2 != null) {
                        identityScope2.unlock();
                    }
                }
            }
            this.db.setTransactionSuccessful();
        } finally {
            this.db.endTransaction();
        }
    }

    public void assertSinglePk() {
        if (this.config.pkColumns.length == 1) {
            return;
        }
        throw new DaoException(this + " (" + this.config.tablename + ") does not have a single-column primary key");
    }

    public void attachEntity(T t2) {
    }

    public final void attachEntity(K k2, T t2, boolean z2) {
        attachEntity(t2);
        IdentityScope<K, T> identityScope = this.identityScope;
        if (identityScope == null || k2 == null) {
            return;
        }
        if (z2) {
            identityScope.put(k2, t2);
        } else {
            identityScope.putNoLock(k2, t2);
        }
    }

    public abstract void bindValues(SQLiteStatement sQLiteStatement, T t2);

    public long count() {
        return DatabaseUtils.queryNumEntries(this.db, CharPool.SINGLE_QUOTE + this.config.tablename + CharPool.SINGLE_QUOTE);
    }

    public void delete(T t2) {
        assertSinglePk();
        deleteByKey(getKeyVerified(t2));
    }

    public void deleteAll() throws SQLException {
        this.db.execSQL("DELETE FROM '" + this.config.tablename + "'");
        IdentityScope<K, T> identityScope = this.identityScope;
        if (identityScope != null) {
            identityScope.clear();
        }
    }

    public void deleteByKey(K k2) {
        assertSinglePk();
        SQLiteStatement deleteStatement = this.statements.getDeleteStatement();
        if (this.db.isDbLockedByCurrentThread()) {
            synchronized (deleteStatement) {
                deleteByKeyInsideSynchronized(k2, deleteStatement);
            }
        } else {
            this.db.beginTransaction();
            try {
                synchronized (deleteStatement) {
                    deleteByKeyInsideSynchronized(k2, deleteStatement);
                }
                this.db.setTransactionSuccessful();
            } finally {
                this.db.endTransaction();
            }
        }
        IdentityScope<K, T> identityScope = this.identityScope;
        if (identityScope != null) {
            identityScope.remove((IdentityScope<K, T>) k2);
        }
    }

    public void deleteByKeyInTx(Iterable<K> iterable) {
        deleteInTxInternal(null, iterable);
    }

    public void deleteInTx(Iterable<T> iterable) {
        deleteInTxInternal(iterable, null);
    }

    public boolean detach(T t2) {
        if (this.identityScope == null) {
            return false;
        }
        return this.identityScope.detach(getKeyVerified(t2), t2);
    }

    public String[] getAllColumns() {
        return this.config.allColumns;
    }

    public SQLiteDatabase getDatabase() {
        return this.db;
    }

    public abstract K getKey(T t2);

    public K getKeyVerified(T t2) {
        K key = getKey(t2);
        if (key != null) {
            return key;
        }
        if (t2 == null) {
            throw new NullPointerException("Entity may not be null");
        }
        throw new DaoException("Entity has no key");
    }

    public String[] getNonPkColumns() {
        return this.config.nonPkColumns;
    }

    public String[] getPkColumns() {
        return this.config.pkColumns;
    }

    public Property getPkProperty() {
        return this.config.pkProperty;
    }

    public Property[] getProperties() {
        return this.config.properties;
    }

    public AbstractDaoSession getSession() {
        return this.session;
    }

    public TableStatements getStatements() {
        return this.config.statements;
    }

    public String getTablename() {
        return this.config.tablename;
    }

    public long insert(T t2) {
        return executeInsert(t2, this.statements.getInsertStatement());
    }

    public void insertInTx(Iterable<T> iterable) {
        insertInTx(iterable, isEntityUpdateable());
    }

    public long insertOrReplace(T t2) {
        return executeInsert(t2, this.statements.getInsertOrReplaceStatement());
    }

    public void insertOrReplaceInTx(Iterable<T> iterable, boolean z2) {
        executeInsertInTx(this.statements.getInsertOrReplaceStatement(), iterable, z2);
    }

    public long insertWithoutSettingPk(T t2) {
        long jExecuteInsert;
        SQLiteStatement insertStatement = this.statements.getInsertStatement();
        if (this.db.isDbLockedByCurrentThread()) {
            synchronized (insertStatement) {
                bindValues(insertStatement, t2);
                jExecuteInsert = insertStatement.executeInsert();
            }
        } else {
            this.db.beginTransaction();
            try {
                synchronized (insertStatement) {
                    bindValues(insertStatement, t2);
                    jExecuteInsert = insertStatement.executeInsert();
                }
                this.db.setTransactionSuccessful();
            } finally {
                this.db.endTransaction();
            }
        }
        return jExecuteInsert;
    }

    public abstract boolean isEntityUpdateable();

    public T load(K k2) {
        T t2;
        assertSinglePk();
        if (k2 == null) {
            return null;
        }
        IdentityScope<K, T> identityScope = this.identityScope;
        return (identityScope == null || (t2 = identityScope.get(k2)) == null) ? loadUniqueAndCloseCursor(this.db.rawQuery(this.statements.getSelectByKey(), new String[]{k2.toString()})) : t2;
    }

    public List<T> loadAll() {
        return loadAllAndCloseCursor(this.db.rawQuery(this.statements.getSelectAll(), null));
    }

    public List<T> loadAllAndCloseCursor(Cursor cursor) {
        try {
            return loadAllFromCursor(cursor);
        } finally {
            cursor.close();
        }
    }

    public List<T> loadAllFromCursor(Cursor cursor) {
        CursorWindow window;
        int count = cursor.getCount();
        ArrayList arrayList = new ArrayList(count);
        if ((cursor instanceof CrossProcessCursor) && (window = ((CrossProcessCursor) cursor).getWindow()) != null) {
            if (window.getNumRows() == count) {
                cursor = new FastCursor(window);
            } else {
                DaoLog.d("Window vs. result size: " + window.getNumRows() + "/" + count);
            }
        }
        if (cursor.moveToFirst()) {
            IdentityScope<K, T> identityScope = this.identityScope;
            if (identityScope != null) {
                identityScope.lock();
                this.identityScope.reserveRoom(count);
            }
            do {
                try {
                    arrayList.add(loadCurrent(cursor, 0, false));
                } finally {
                    IdentityScope<K, T> identityScope2 = this.identityScope;
                    if (identityScope2 != null) {
                        identityScope2.unlock();
                    }
                }
            } while (cursor.moveToNext());
        }
        return arrayList;
    }

    public T loadByRowId(long j2) {
        return loadUniqueAndCloseCursor(this.db.rawQuery(this.statements.getSelectByRowId(), new String[]{Long.toString(j2)}));
    }

    public final T loadCurrent(Cursor cursor, int i2, boolean z2) {
        if (this.identityScopeLong != null) {
            if (i2 != 0 && cursor.isNull(this.pkOrdinal + i2)) {
                return null;
            }
            long j2 = cursor.getLong(this.pkOrdinal + i2);
            IdentityScopeLong<T> identityScopeLong = this.identityScopeLong;
            T t2 = z2 ? identityScopeLong.get2(j2) : identityScopeLong.get2NoLock(j2);
            if (t2 != null) {
                return t2;
            }
            T entity = readEntity(cursor, i2);
            attachEntity(entity);
            if (z2) {
                this.identityScopeLong.put2(j2, entity);
            } else {
                this.identityScopeLong.put2NoLock(j2, entity);
            }
            return entity;
        }
        if (this.identityScope == null) {
            if (i2 != 0 && readKey(cursor, i2) == null) {
                return null;
            }
            T entity2 = readEntity(cursor, i2);
            attachEntity(entity2);
            return entity2;
        }
        K key = readKey(cursor, i2);
        if (i2 != 0 && key == null) {
            return null;
        }
        IdentityScope<K, T> identityScope = this.identityScope;
        T noLock = z2 ? identityScope.get(key) : identityScope.getNoLock(key);
        if (noLock != null) {
            return noLock;
        }
        T entity3 = readEntity(cursor, i2);
        attachEntity(key, entity3, z2);
        return entity3;
    }

    public final <O> O loadCurrentOther(AbstractDao<O, ?> abstractDao, Cursor cursor, int i2) {
        return abstractDao.loadCurrent(cursor, i2, true);
    }

    public T loadUnique(Cursor cursor) {
        if (!cursor.moveToFirst()) {
            return null;
        }
        if (cursor.isLast()) {
            return loadCurrent(cursor, 0, true);
        }
        throw new DaoException("Expected unique result, but count was " + cursor.getCount());
    }

    public T loadUniqueAndCloseCursor(Cursor cursor) {
        try {
            return loadUnique(cursor);
        } finally {
            cursor.close();
        }
    }

    public QueryBuilder<T> queryBuilder() {
        return QueryBuilder.internalCreate(this);
    }

    public List<T> queryRaw(String str, String... strArr) {
        return loadAllAndCloseCursor(this.db.rawQuery(this.statements.getSelectAll() + str, strArr));
    }

    public Query<T> queryRawCreate(String str, Object... objArr) {
        return queryRawCreateListArgs(str, Arrays.asList(objArr));
    }

    public Query<T> queryRawCreateListArgs(String str, Collection<Object> collection) {
        return Query.internalCreate(this, this.statements.getSelectAll() + str, collection.toArray());
    }

    public abstract T readEntity(Cursor cursor, int i2);

    public abstract void readEntity(Cursor cursor, T t2, int i2);

    public abstract K readKey(Cursor cursor, int i2);

    public void refresh(T t2) {
        assertSinglePk();
        K keyVerified = getKeyVerified(t2);
        Cursor cursorRawQuery = this.db.rawQuery(this.statements.getSelectByKey(), new String[]{keyVerified.toString()});
        try {
            if (!cursorRawQuery.moveToFirst()) {
                throw new DaoException("Entity does not exist in the database anymore: " + t2.getClass() + " with key " + keyVerified);
            }
            if (cursorRawQuery.isLast()) {
                readEntity(cursorRawQuery, t2, 0);
                attachEntity(keyVerified, t2, true);
            } else {
                throw new DaoException("Expected unique result, but count was " + cursorRawQuery.getCount());
            }
        } finally {
            cursorRawQuery.close();
        }
    }

    public void update(T t2) {
        assertSinglePk();
        SQLiteStatement updateStatement = this.statements.getUpdateStatement();
        if (this.db.isDbLockedByCurrentThread()) {
            synchronized (updateStatement) {
                updateInsideSynchronized(t2, updateStatement, true);
            }
            return;
        }
        this.db.beginTransaction();
        try {
            synchronized (updateStatement) {
                updateInsideSynchronized(t2, updateStatement, true);
            }
            this.db.setTransactionSuccessful();
        } finally {
            this.db.endTransaction();
        }
    }

    public void updateInTx(Iterable<T> iterable) {
        SQLiteStatement updateStatement = this.statements.getUpdateStatement();
        this.db.beginTransaction();
        try {
            synchronized (updateStatement) {
                IdentityScope<K, T> identityScope = this.identityScope;
                if (identityScope != null) {
                    identityScope.lock();
                }
                try {
                    Iterator<T> it = iterable.iterator();
                    while (it.hasNext()) {
                        updateInsideSynchronized(it.next(), updateStatement, false);
                    }
                } finally {
                    IdentityScope<K, T> identityScope2 = this.identityScope;
                    if (identityScope2 != null) {
                        identityScope2.unlock();
                    }
                }
            }
            this.db.setTransactionSuccessful();
        } finally {
            this.db.endTransaction();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void updateInsideSynchronized(T t2, SQLiteStatement sQLiteStatement, boolean z2) {
        bindValues(sQLiteStatement, t2);
        int length = this.config.allColumns.length + 1;
        Object key = getKey(t2);
        if (key instanceof Long) {
            sQLiteStatement.bindLong(length, ((Long) key).longValue());
        } else {
            if (key == null) {
                throw new DaoException("Cannot update entity without key - was it inserted before?");
            }
            sQLiteStatement.bindString(length, key.toString());
        }
        sQLiteStatement.execute();
        attachEntity(key, t2, z2);
    }

    public abstract K updateKeyAfterInsert(T t2, long j2);

    public void updateKeyAfterInsertAndAttach(T t2, long j2, boolean z2) {
        if (j2 != -1) {
            attachEntity(updateKeyAfterInsert(t2, j2), t2, z2);
        } else {
            DaoLog.w("Could not insert row (executeInsert returned -1)");
        }
    }

    public AbstractDao(DaoConfig daoConfig, AbstractDaoSession abstractDaoSession) {
        this.config = daoConfig;
        this.session = abstractDaoSession;
        this.db = daoConfig.db;
        IdentityScopeLong<T> identityScopeLong = (IdentityScope<K, T>) daoConfig.getIdentityScope();
        this.identityScope = identityScopeLong;
        if (identityScopeLong instanceof IdentityScopeLong) {
            this.identityScopeLong = identityScopeLong;
        }
        this.statements = daoConfig.statements;
        Property property = daoConfig.pkProperty;
        this.pkOrdinal = property != null ? property.ordinal : -1;
    }

    public void deleteByKeyInTx(K... kArr) {
        deleteInTxInternal(null, Arrays.asList(kArr));
    }

    public void deleteInTx(T... tArr) {
        deleteInTxInternal(Arrays.asList(tArr), null);
    }

    public void insertInTx(T... tArr) {
        insertInTx(Arrays.asList(tArr), isEntityUpdateable());
    }

    public void insertInTx(Iterable<T> iterable, boolean z2) {
        executeInsertInTx(this.statements.getInsertStatement(), iterable, z2);
    }

    public void insertOrReplaceInTx(Iterable<T> iterable) {
        insertOrReplaceInTx(iterable, isEntityUpdateable());
    }

    public void insertOrReplaceInTx(T... tArr) {
        insertOrReplaceInTx(Arrays.asList(tArr), isEntityUpdateable());
    }

    public void updateInTx(T... tArr) {
        updateInTx(Arrays.asList(tArr));
    }
}
