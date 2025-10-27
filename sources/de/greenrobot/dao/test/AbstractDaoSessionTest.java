package de.greenrobot.dao.test;

import android.database.sqlite.SQLiteDatabase;
import de.greenrobot.dao.AbstractDaoMaster;
import de.greenrobot.dao.AbstractDaoSession;

/* loaded from: classes8.dex */
public abstract class AbstractDaoSessionTest<T extends AbstractDaoMaster, S extends AbstractDaoSession> extends DbTest {
    protected T daoMaster;
    private final Class<T> daoMasterClass;
    protected S daoSession;

    public AbstractDaoSessionTest(Class<T> cls) {
        this(cls, true);
    }

    @Override // de.greenrobot.dao.test.DbTest
    public void setUp() throws Exception {
        super.setUp();
        try {
            this.daoMaster = this.daoMasterClass.getConstructor(SQLiteDatabase.class).newInstance(this.db);
            this.daoMasterClass.getMethod("createAllTables", SQLiteDatabase.class, Boolean.TYPE).invoke(null, this.db, Boolean.FALSE);
            this.daoSession = (S) this.daoMaster.newSession();
        } catch (Exception e2) {
            throw new RuntimeException("Could not prepare DAO session test", e2);
        }
    }

    public AbstractDaoSessionTest(Class<T> cls, boolean z2) {
        super(z2);
        this.daoMasterClass = cls;
    }
}
