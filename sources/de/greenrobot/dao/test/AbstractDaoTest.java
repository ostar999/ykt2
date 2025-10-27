package de.greenrobot.dao.test;

import android.database.sqlite.SQLiteDatabase;
import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.DaoLog;
import de.greenrobot.dao.InternalUnitTestDaoAccess;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.identityscope.IdentityScope;

/* loaded from: classes8.dex */
public abstract class AbstractDaoTest<D extends AbstractDao<T, K>, T, K> extends DbTest {
    protected D dao;
    protected InternalUnitTestDaoAccess<T, K> daoAccess;
    protected final Class<D> daoClass;
    protected IdentityScope<K, T> identityScopeForDao;
    protected Property pkColumn;

    public AbstractDaoTest(Class<D> cls) {
        this(cls, true);
    }

    public void clearIdentityScopeIfAny() {
        IdentityScope<K, T> identityScope = this.identityScopeForDao;
        if (identityScope == null) {
            DaoLog.d("No identity scope to clear");
        } else {
            identityScope.clear();
            DaoLog.d("Identity scope cleared");
        }
    }

    public void logTableDump() {
        logTableDump(this.dao.getTablename());
    }

    public void setIdentityScopeBeforeSetUp(IdentityScope<K, T> identityScope) {
        this.identityScopeForDao = identityScope;
    }

    @Override // de.greenrobot.dao.test.DbTest
    public void setUp() throws Exception {
        super.setUp();
        try {
            setUpTableForDao();
            InternalUnitTestDaoAccess<T, K> internalUnitTestDaoAccess = new InternalUnitTestDaoAccess<>(this.db, this.daoClass, this.identityScopeForDao);
            this.daoAccess = internalUnitTestDaoAccess;
            this.dao = internalUnitTestDaoAccess.getDao();
        } catch (Exception e2) {
            throw new RuntimeException("Could not prepare DAO Test", e2);
        }
    }

    public void setUpTableForDao() throws Exception {
        try {
            this.daoClass.getMethod("createTable", SQLiteDatabase.class, Boolean.TYPE).invoke(null, this.db, Boolean.FALSE);
        } catch (NoSuchMethodException unused) {
            DaoLog.i("No createTable method");
        }
    }

    public AbstractDaoTest(Class<D> cls, boolean z2) {
        super(z2);
        this.daoClass = cls;
    }
}
