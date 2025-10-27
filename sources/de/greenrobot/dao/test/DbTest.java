package de.greenrobot.dao.test;

import android.app.Application;
import android.app.Instrumentation;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;
import de.greenrobot.dao.DbUtils;
import java.util.Random;

/* loaded from: classes8.dex */
public abstract class DbTest extends AndroidTestCase {
    public static final String DB_NAME = "greendao-unittest-db.temp";
    private Application application;
    protected SQLiteDatabase db;
    protected final boolean inMemory;
    protected final Random random;

    public DbTest() {
        this(true);
    }

    public <T extends Application> T createApplication(Class<T> cls) {
        AndroidTestCase.assertNull("Application already created", this.application);
        try {
            T t2 = (T) Instrumentation.newApplication(cls, getContext());
            t2.onCreate();
            this.application = t2;
            return t2;
        } catch (Exception e2) {
            throw new RuntimeException("Could not create application " + cls, e2);
        }
    }

    public SQLiteDatabase createDatabase() {
        if (this.inMemory) {
            return SQLiteDatabase.create(null);
        }
        getContext().deleteDatabase(DB_NAME);
        return getContext().openOrCreateDatabase(DB_NAME, 0, null);
    }

    public <T extends Application> T getApplication() {
        AndroidTestCase.assertNotNull("Application not yet created", this.application);
        return (T) this.application;
    }

    public void logTableDump(String str) {
        DbUtils.logTableDump(this.db, str);
    }

    public void setUp() throws Exception {
        super.setUp();
        this.db = createDatabase();
    }

    public void tearDown() throws Exception {
        if (this.application != null) {
            terminateApplication();
        }
        this.db.close();
        if (!this.inMemory) {
            getContext().deleteDatabase(DB_NAME);
        }
        super.tearDown();
    }

    public void terminateApplication() {
        AndroidTestCase.assertNotNull("Application not yet created", this.application);
        this.application.onTerminate();
        this.application = null;
    }

    public DbTest(boolean z2) {
        this.inMemory = z2;
        this.random = new Random();
    }
}
