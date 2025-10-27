package androidx.room;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.database.Cursor;
import android.os.CancellationSignal;
import android.os.Looper;
import android.util.Log;
import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.annotation.WorkerThread;
import androidx.arch.core.executor.ArchTaskExecutor;
import androidx.room.migration.Migration;
import androidx.room.util.SneakyThrow;
import androidx.sqlite.db.SimpleSQLiteQuery;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.SupportSQLiteQuery;
import androidx.sqlite.db.SupportSQLiteStatement;
import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/* loaded from: classes.dex */
public abstract class RoomDatabase {
    private static final String DB_IMPL_SUFFIX = "_Impl";

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public static final int MAX_BIND_PARAMETER_CNT = 999;
    private boolean mAllowMainThreadQueries;

    @Nullable
    @Deprecated
    protected List<Callback> mCallbacks;

    @Deprecated
    protected volatile SupportSQLiteDatabase mDatabase;
    private SupportSQLiteOpenHelper mOpenHelper;
    private Executor mQueryExecutor;
    private Executor mTransactionExecutor;
    boolean mWriteAheadLoggingEnabled;
    private final ReentrantReadWriteLock mCloseLock = new ReentrantReadWriteLock();
    private final ThreadLocal<Integer> mSuspendingTransactionId = new ThreadLocal<>();
    private final Map<String, Object> mBackingFieldMap = new ConcurrentHashMap();
    private final InvalidationTracker mInvalidationTracker = createInvalidationTracker();

    public static class Builder<T extends RoomDatabase> {
        private boolean mAllowDestructiveMigrationOnDowngrade;
        private boolean mAllowMainThreadQueries;
        private ArrayList<Callback> mCallbacks;
        private final Context mContext;
        private String mCopyFromAssetPath;
        private File mCopyFromFile;
        private final Class<T> mDatabaseClass;
        private SupportSQLiteOpenHelper.Factory mFactory;
        private Set<Integer> mMigrationStartAndEndVersions;
        private Set<Integer> mMigrationsNotRequiredFrom;
        private boolean mMultiInstanceInvalidation;
        private final String mName;
        private Executor mQueryExecutor;
        private Executor mTransactionExecutor;
        private JournalMode mJournalMode = JournalMode.AUTOMATIC;
        private boolean mRequireMigration = true;
        private final MigrationContainer mMigrationContainer = new MigrationContainer();

        public Builder(@NonNull Context context, @NonNull Class<T> cls, @Nullable String str) {
            this.mContext = context;
            this.mDatabaseClass = cls;
            this.mName = str;
        }

        @NonNull
        public Builder<T> addCallback(@NonNull Callback callback) {
            if (this.mCallbacks == null) {
                this.mCallbacks = new ArrayList<>();
            }
            this.mCallbacks.add(callback);
            return this;
        }

        @NonNull
        public Builder<T> addMigrations(@NonNull Migration... migrationArr) {
            if (this.mMigrationStartAndEndVersions == null) {
                this.mMigrationStartAndEndVersions = new HashSet();
            }
            for (Migration migration : migrationArr) {
                this.mMigrationStartAndEndVersions.add(Integer.valueOf(migration.startVersion));
                this.mMigrationStartAndEndVersions.add(Integer.valueOf(migration.endVersion));
            }
            this.mMigrationContainer.addMigrations(migrationArr);
            return this;
        }

        @NonNull
        public Builder<T> allowMainThreadQueries() {
            this.mAllowMainThreadQueries = true;
            return this;
        }

        @NonNull
        @SuppressLint({"RestrictedApi"})
        public T build() {
            Executor executor;
            if (this.mContext == null) {
                throw new IllegalArgumentException("Cannot provide null context for the database.");
            }
            if (this.mDatabaseClass == null) {
                throw new IllegalArgumentException("Must provide an abstract class that extends RoomDatabase");
            }
            Executor executor2 = this.mQueryExecutor;
            if (executor2 == null && this.mTransactionExecutor == null) {
                Executor iOThreadExecutor = ArchTaskExecutor.getIOThreadExecutor();
                this.mTransactionExecutor = iOThreadExecutor;
                this.mQueryExecutor = iOThreadExecutor;
            } else if (executor2 != null && this.mTransactionExecutor == null) {
                this.mTransactionExecutor = executor2;
            } else if (executor2 == null && (executor = this.mTransactionExecutor) != null) {
                this.mQueryExecutor = executor;
            }
            Set<Integer> set = this.mMigrationStartAndEndVersions;
            if (set != null && this.mMigrationsNotRequiredFrom != null) {
                for (Integer num : set) {
                    if (this.mMigrationsNotRequiredFrom.contains(num)) {
                        throw new IllegalArgumentException("Inconsistency detected. A Migration was supplied to addMigration(Migration... migrations) that has a start or end version equal to a start version supplied to fallbackToDestructiveMigrationFrom(int... startVersions). Start version: " + num);
                    }
                }
            }
            if (this.mFactory == null) {
                this.mFactory = new FrameworkSQLiteOpenHelperFactory();
            }
            String str = this.mCopyFromAssetPath;
            if (str != null || this.mCopyFromFile != null) {
                if (this.mName == null) {
                    throw new IllegalArgumentException("Cannot create from asset or file for an in-memory database.");
                }
                if (str != null && this.mCopyFromFile != null) {
                    throw new IllegalArgumentException("Both createFromAsset() and createFromFile() was called on this Builder but the database can only be created using one of the two configurations.");
                }
                this.mFactory = new SQLiteCopyOpenHelperFactory(str, this.mCopyFromFile, this.mFactory);
            }
            Context context = this.mContext;
            DatabaseConfiguration databaseConfiguration = new DatabaseConfiguration(context, this.mName, this.mFactory, this.mMigrationContainer, this.mCallbacks, this.mAllowMainThreadQueries, this.mJournalMode.resolve(context), this.mQueryExecutor, this.mTransactionExecutor, this.mMultiInstanceInvalidation, this.mRequireMigration, this.mAllowDestructiveMigrationOnDowngrade, this.mMigrationsNotRequiredFrom, this.mCopyFromAssetPath, this.mCopyFromFile);
            T t2 = (T) Room.getGeneratedImplementation(this.mDatabaseClass, RoomDatabase.DB_IMPL_SUFFIX);
            t2.init(databaseConfiguration);
            return t2;
        }

        @NonNull
        public Builder<T> createFromAsset(@NonNull String str) {
            this.mCopyFromAssetPath = str;
            return this;
        }

        @NonNull
        public Builder<T> createFromFile(@NonNull File file) {
            this.mCopyFromFile = file;
            return this;
        }

        @NonNull
        public Builder<T> enableMultiInstanceInvalidation() {
            this.mMultiInstanceInvalidation = this.mName != null;
            return this;
        }

        @NonNull
        public Builder<T> fallbackToDestructiveMigration() {
            this.mRequireMigration = false;
            this.mAllowDestructiveMigrationOnDowngrade = true;
            return this;
        }

        @NonNull
        public Builder<T> fallbackToDestructiveMigrationFrom(int... iArr) {
            if (this.mMigrationsNotRequiredFrom == null) {
                this.mMigrationsNotRequiredFrom = new HashSet(iArr.length);
            }
            for (int i2 : iArr) {
                this.mMigrationsNotRequiredFrom.add(Integer.valueOf(i2));
            }
            return this;
        }

        @NonNull
        public Builder<T> fallbackToDestructiveMigrationOnDowngrade() {
            this.mRequireMigration = true;
            this.mAllowDestructiveMigrationOnDowngrade = true;
            return this;
        }

        @NonNull
        public Builder<T> openHelperFactory(@Nullable SupportSQLiteOpenHelper.Factory factory) {
            this.mFactory = factory;
            return this;
        }

        @NonNull
        public Builder<T> setJournalMode(@NonNull JournalMode journalMode) {
            this.mJournalMode = journalMode;
            return this;
        }

        @NonNull
        public Builder<T> setQueryExecutor(@NonNull Executor executor) {
            this.mQueryExecutor = executor;
            return this;
        }

        @NonNull
        public Builder<T> setTransactionExecutor(@NonNull Executor executor) {
            this.mTransactionExecutor = executor;
            return this;
        }
    }

    public static abstract class Callback {
        public void onCreate(@NonNull SupportSQLiteDatabase supportSQLiteDatabase) {
        }

        public void onDestructiveMigration(@NonNull SupportSQLiteDatabase supportSQLiteDatabase) {
        }

        public void onOpen(@NonNull SupportSQLiteDatabase supportSQLiteDatabase) {
        }
    }

    public enum JournalMode {
        AUTOMATIC,
        TRUNCATE,
        WRITE_AHEAD_LOGGING;

        private static boolean isLowRamDevice(@NonNull ActivityManager activityManager) {
            return activityManager.isLowRamDevice();
        }

        @SuppressLint({"NewApi"})
        public JournalMode resolve(Context context) {
            if (this != AUTOMATIC) {
                return this;
            }
            ActivityManager activityManager = (ActivityManager) context.getSystemService(PushConstants.INTENT_ACTIVITY_NAME);
            return (activityManager == null || isLowRamDevice(activityManager)) ? TRUNCATE : WRITE_AHEAD_LOGGING;
        }
    }

    public static class MigrationContainer {
        private HashMap<Integer, TreeMap<Integer, Migration>> mMigrations = new HashMap<>();

        private void addMigration(Migration migration) {
            int i2 = migration.startVersion;
            int i3 = migration.endVersion;
            TreeMap<Integer, Migration> treeMap = this.mMigrations.get(Integer.valueOf(i2));
            if (treeMap == null) {
                treeMap = new TreeMap<>();
                this.mMigrations.put(Integer.valueOf(i2), treeMap);
            }
            Migration migration2 = treeMap.get(Integer.valueOf(i3));
            if (migration2 != null) {
                Log.w("ROOM", "Overriding migration " + migration2 + " with " + migration);
            }
            treeMap.put(Integer.valueOf(i3), migration);
        }

        /* JADX WARN: Removed duplicated region for block: B:19:0x003e  */
        /* JADX WARN: Removed duplicated region for block: B:30:0x0016 A[SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:35:0x0047 A[SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:41:? A[LOOP:1: B:13:0x0026->B:41:?, LOOP_END, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:9:0x0017  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        private java.util.List<androidx.room.migration.Migration> findUpMigrationPath(java.util.List<androidx.room.migration.Migration> r7, boolean r8, int r9, int r10) {
            /*
                r6 = this;
            L0:
                if (r8 == 0) goto L5
                if (r9 >= r10) goto L59
                goto L7
            L5:
                if (r9 <= r10) goto L59
            L7:
                java.util.HashMap<java.lang.Integer, java.util.TreeMap<java.lang.Integer, androidx.room.migration.Migration>> r0 = r6.mMigrations
                java.lang.Integer r1 = java.lang.Integer.valueOf(r9)
                java.lang.Object r0 = r0.get(r1)
                java.util.TreeMap r0 = (java.util.TreeMap) r0
                r1 = 0
                if (r0 != 0) goto L17
                return r1
            L17:
                if (r8 == 0) goto L1e
                java.util.NavigableSet r2 = r0.descendingKeySet()
                goto L22
            L1e:
                java.util.Set r2 = r0.keySet()
            L22:
                java.util.Iterator r2 = r2.iterator()
            L26:
                boolean r3 = r2.hasNext()
                r4 = 0
                if (r3 == 0) goto L56
                java.lang.Object r3 = r2.next()
                java.lang.Integer r3 = (java.lang.Integer) r3
                int r3 = r3.intValue()
                r5 = 1
                if (r8 == 0) goto L40
                if (r3 > r10) goto L45
                if (r3 <= r9) goto L45
            L3e:
                r4 = r5
                goto L45
            L40:
                if (r3 < r10) goto L45
                if (r3 >= r9) goto L45
                goto L3e
            L45:
                if (r4 == 0) goto L26
                java.lang.Integer r9 = java.lang.Integer.valueOf(r3)
                java.lang.Object r9 = r0.get(r9)
                androidx.room.migration.Migration r9 = (androidx.room.migration.Migration) r9
                r7.add(r9)
                r9 = r3
                r4 = r5
            L56:
                if (r4 != 0) goto L0
                return r1
            L59:
                return r7
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.room.RoomDatabase.MigrationContainer.findUpMigrationPath(java.util.List, boolean, int, int):java.util.List");
        }

        public void addMigrations(@NonNull Migration... migrationArr) {
            for (Migration migration : migrationArr) {
                addMigration(migration);
            }
        }

        @Nullable
        public List<Migration> findMigrationPath(int i2, int i3) {
            if (i2 == i3) {
                return Collections.emptyList();
            }
            return findUpMigrationPath(new ArrayList(), i3 > i2, i2, i3);
        }
    }

    private static boolean isMainThread() {
        return Looper.getMainLooper().getThread() == Thread.currentThread();
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public void assertNotMainThread() {
        if (!this.mAllowMainThreadQueries && isMainThread()) {
            throw new IllegalStateException("Cannot access database on the main thread since it may potentially lock the UI for a long period of time.");
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void assertNotSuspendingTransaction() {
        if (!inTransaction() && this.mSuspendingTransactionId.get() != null) {
            throw new IllegalStateException("Cannot access database on a different coroutine context inherited from a suspending transaction.");
        }
    }

    @Deprecated
    public void beginTransaction() {
        assertNotMainThread();
        SupportSQLiteDatabase writableDatabase = this.mOpenHelper.getWritableDatabase();
        this.mInvalidationTracker.syncTriggers(writableDatabase);
        writableDatabase.beginTransaction();
    }

    @WorkerThread
    public abstract void clearAllTables();

    public void close() {
        if (isOpen()) {
            ReentrantReadWriteLock.WriteLock writeLock = this.mCloseLock.writeLock();
            writeLock.lock();
            try {
                this.mInvalidationTracker.stopMultiInstanceInvalidation();
                this.mOpenHelper.close();
            } finally {
                writeLock.unlock();
            }
        }
    }

    public SupportSQLiteStatement compileStatement(@NonNull String str) {
        assertNotMainThread();
        assertNotSuspendingTransaction();
        return this.mOpenHelper.getWritableDatabase().compileStatement(str);
    }

    @NonNull
    public abstract InvalidationTracker createInvalidationTracker();

    @NonNull
    public abstract SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration databaseConfiguration);

    @Deprecated
    public void endTransaction() {
        this.mOpenHelper.getWritableDatabase().endTransaction();
        if (inTransaction()) {
            return;
        }
        this.mInvalidationTracker.refreshVersionsAsync();
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public Map<String, Object> getBackingFieldMap() {
        return this.mBackingFieldMap;
    }

    public Lock getCloseLock() {
        return this.mCloseLock.readLock();
    }

    @NonNull
    public InvalidationTracker getInvalidationTracker() {
        return this.mInvalidationTracker;
    }

    @NonNull
    public SupportSQLiteOpenHelper getOpenHelper() {
        return this.mOpenHelper;
    }

    @NonNull
    public Executor getQueryExecutor() {
        return this.mQueryExecutor;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public ThreadLocal<Integer> getSuspendingTransactionId() {
        return this.mSuspendingTransactionId;
    }

    @NonNull
    public Executor getTransactionExecutor() {
        return this.mTransactionExecutor;
    }

    public boolean inTransaction() {
        return this.mOpenHelper.getWritableDatabase().inTransaction();
    }

    @CallSuper
    public void init(@NonNull DatabaseConfiguration databaseConfiguration) {
        SupportSQLiteOpenHelper supportSQLiteOpenHelperCreateOpenHelper = createOpenHelper(databaseConfiguration);
        this.mOpenHelper = supportSQLiteOpenHelperCreateOpenHelper;
        if (supportSQLiteOpenHelperCreateOpenHelper instanceof SQLiteCopyOpenHelper) {
            ((SQLiteCopyOpenHelper) supportSQLiteOpenHelperCreateOpenHelper).setDatabaseConfiguration(databaseConfiguration);
        }
        boolean z2 = databaseConfiguration.journalMode == JournalMode.WRITE_AHEAD_LOGGING;
        this.mOpenHelper.setWriteAheadLoggingEnabled(z2);
        this.mCallbacks = databaseConfiguration.callbacks;
        this.mQueryExecutor = databaseConfiguration.queryExecutor;
        this.mTransactionExecutor = new TransactionExecutor(databaseConfiguration.transactionExecutor);
        this.mAllowMainThreadQueries = databaseConfiguration.allowMainThreadQueries;
        this.mWriteAheadLoggingEnabled = z2;
        if (databaseConfiguration.multiInstanceInvalidation) {
            this.mInvalidationTracker.startMultiInstanceInvalidation(databaseConfiguration.context, databaseConfiguration.name);
        }
    }

    public void internalInitInvalidationTracker(@NonNull SupportSQLiteDatabase supportSQLiteDatabase) {
        this.mInvalidationTracker.internalInit(supportSQLiteDatabase);
    }

    public boolean isOpen() {
        SupportSQLiteDatabase supportSQLiteDatabase = this.mDatabase;
        return supportSQLiteDatabase != null && supportSQLiteDatabase.isOpen();
    }

    @NonNull
    public Cursor query(@NonNull String str, @Nullable Object[] objArr) {
        return this.mOpenHelper.getWritableDatabase().query(new SimpleSQLiteQuery(str, objArr));
    }

    public void runInTransaction(@NonNull Runnable runnable) {
        beginTransaction();
        try {
            runnable.run();
            setTransactionSuccessful();
        } finally {
            endTransaction();
        }
    }

    @Deprecated
    public void setTransactionSuccessful() {
        this.mOpenHelper.getWritableDatabase().setTransactionSuccessful();
    }

    @NonNull
    public Cursor query(@NonNull SupportSQLiteQuery supportSQLiteQuery) {
        return query(supportSQLiteQuery, (CancellationSignal) null);
    }

    @NonNull
    public Cursor query(@NonNull SupportSQLiteQuery supportSQLiteQuery, @Nullable CancellationSignal cancellationSignal) {
        assertNotMainThread();
        assertNotSuspendingTransaction();
        if (cancellationSignal != null) {
            return this.mOpenHelper.getWritableDatabase().query(supportSQLiteQuery, cancellationSignal);
        }
        return this.mOpenHelper.getWritableDatabase().query(supportSQLiteQuery);
    }

    public <V> V runInTransaction(@NonNull Callable<V> callable) {
        beginTransaction();
        try {
            try {
                V vCall = callable.call();
                setTransactionSuccessful();
                return vCall;
            } catch (RuntimeException e2) {
                throw e2;
            } catch (Exception e3) {
                SneakyThrow.reThrow(e3);
                endTransaction();
                return null;
            }
        } finally {
            endTransaction();
        }
    }
}
