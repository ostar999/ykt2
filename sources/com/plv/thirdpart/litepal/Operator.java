package com.plv.thirdpart.litepal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import com.plv.thirdpart.litepal.crud.DeleteHandler;
import com.plv.thirdpart.litepal.crud.LitePalSupport;
import com.plv.thirdpart.litepal.crud.QueryHandler;
import com.plv.thirdpart.litepal.crud.SaveHandler;
import com.plv.thirdpart.litepal.crud.UpdateHandler;
import com.plv.thirdpart.litepal.crud.async.AverageExecutor;
import com.plv.thirdpart.litepal.crud.async.CountExecutor;
import com.plv.thirdpart.litepal.crud.async.FindExecutor;
import com.plv.thirdpart.litepal.crud.async.FindMultiExecutor;
import com.plv.thirdpart.litepal.crud.async.SaveExecutor;
import com.plv.thirdpart.litepal.crud.async.UpdateOrDeleteExecutor;
import com.plv.thirdpart.litepal.parser.LitePalAttr;
import com.plv.thirdpart.litepal.parser.LitePalParser;
import com.plv.thirdpart.litepal.tablemanager.Connector;
import com.plv.thirdpart.litepal.tablemanager.callback.DatabaseListener;
import com.plv.thirdpart.litepal.util.BaseUtility;
import com.plv.thirdpart.litepal.util.Const;
import com.plv.thirdpart.litepal.util.DBUtility;
import com.plv.thirdpart.litepal.util.SharedUtil;
import com.plv.thirdpart.litepal.util.cipher.CipherUtil;
import java.io.File;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes5.dex */
public class Operator {
    private static Handler handler = new Handler(Looper.getMainLooper());
    private static DatabaseListener dbListener = null;

    public static void aesKey(String str) {
        CipherUtil.aesKey = str;
    }

    public static double average(Class<?> cls, String str) {
        return average(BaseUtility.changeCase(DBUtility.getTableNameByClassName(cls.getName())), str);
    }

    @Deprecated
    public static AverageExecutor averageAsync(Class<?> cls, String str) {
        return averageAsync(BaseUtility.changeCase(DBUtility.getTableNameByClassName(cls.getName())), str);
    }

    public static void beginTransaction() {
        getDatabase().beginTransaction();
    }

    public static int count(Class<?> cls) {
        return count(BaseUtility.changeCase(DBUtility.getTableNameByClassName(cls.getName())));
    }

    @Deprecated
    public static CountExecutor countAsync(Class<?> cls) {
        return countAsync(BaseUtility.changeCase(DBUtility.getTableNameByClassName(cls.getName())));
    }

    public static int delete(Class<?> cls, long j2) {
        int iOnDelete;
        synchronized (LitePalSupport.class) {
            SQLiteDatabase database = Connector.getDatabase();
            database.beginTransaction();
            try {
                iOnDelete = new DeleteHandler(database).onDelete(cls, j2);
                database.setTransactionSuccessful();
            } finally {
                database.endTransaction();
            }
        }
        return iOnDelete;
    }

    public static int deleteAll(Class<?> cls, String... strArr) {
        int iOnDeleteAll;
        synchronized (LitePalSupport.class) {
            SQLiteDatabase database = Connector.getDatabase();
            database.beginTransaction();
            try {
                iOnDeleteAll = new DeleteHandler(database).onDeleteAll(cls, strArr);
                database.setTransactionSuccessful();
            } finally {
                database.endTransaction();
            }
        }
        return iOnDeleteAll;
    }

    @Deprecated
    public static UpdateOrDeleteExecutor deleteAllAsync(final Class<?> cls, final String... strArr) {
        final UpdateOrDeleteExecutor updateOrDeleteExecutor = new UpdateOrDeleteExecutor();
        updateOrDeleteExecutor.submit(new Runnable() { // from class: com.plv.thirdpart.litepal.Operator.11
            @Override // java.lang.Runnable
            public void run() {
                synchronized (LitePalSupport.class) {
                    final int iDeleteAll = Operator.deleteAll((Class<?>) cls, strArr);
                    if (updateOrDeleteExecutor.getListener() != null) {
                        Operator.getHandler().post(new Runnable() { // from class: com.plv.thirdpart.litepal.Operator.11.1
                            @Override // java.lang.Runnable
                            public void run() {
                                updateOrDeleteExecutor.getListener().onFinish(iDeleteAll);
                            }
                        });
                    }
                }
            }
        });
        return updateOrDeleteExecutor;
    }

    @Deprecated
    public static UpdateOrDeleteExecutor deleteAsync(final Class<?> cls, final long j2) {
        final UpdateOrDeleteExecutor updateOrDeleteExecutor = new UpdateOrDeleteExecutor();
        updateOrDeleteExecutor.submit(new Runnable() { // from class: com.plv.thirdpart.litepal.Operator.10
            @Override // java.lang.Runnable
            public void run() {
                synchronized (LitePalSupport.class) {
                    final int iDelete = Operator.delete(cls, j2);
                    if (updateOrDeleteExecutor.getListener() != null) {
                        Operator.getHandler().post(new Runnable() { // from class: com.plv.thirdpart.litepal.Operator.10.1
                            @Override // java.lang.Runnable
                            public void run() {
                                updateOrDeleteExecutor.getListener().onFinish(iDelete);
                            }
                        });
                    }
                }
            }
        });
        return updateOrDeleteExecutor;
    }

    public static boolean deleteDatabase(String str) {
        synchronized (LitePalSupport.class) {
            if (TextUtils.isEmpty(str)) {
                return false;
            }
            if (!str.endsWith(".db")) {
                str = str + ".db";
            }
            File databasePath = LitePalApplication.getContext().getDatabasePath(str);
            if (databasePath.exists()) {
                boolean zDelete = databasePath.delete();
                if (zDelete) {
                    removeVersionInSharedPreferences(str);
                    Connector.clearLitePalOpenHelperInstance();
                }
                return zDelete;
            }
            boolean zDelete2 = new File((LitePalApplication.getContext().getExternalFilesDir("") + "/databases/") + str).delete();
            if (zDelete2) {
                removeVersionInSharedPreferences(str);
                Connector.clearLitePalOpenHelperInstance();
            }
            return zDelete2;
        }
    }

    public static void endTransaction() {
        getDatabase().endTransaction();
    }

    public static <T> T find(Class<T> cls, long j2) {
        return (T) find(cls, j2, false);
    }

    public static <T> List<T> findAll(Class<T> cls, long... jArr) {
        return findAll(cls, false, jArr);
    }

    @Deprecated
    public static <T> FindMultiExecutor<T> findAllAsync(Class<T> cls, long... jArr) {
        return findAllAsync(cls, false, jArr);
    }

    @Deprecated
    public static <T> FindExecutor<T> findAsync(Class<T> cls, long j2) {
        return findAsync(cls, j2, false);
    }

    public static Cursor findBySQL(String... strArr) {
        synchronized (LitePalSupport.class) {
            BaseUtility.checkConditionsCorrect(strArr);
            String[] strArr2 = null;
            if (strArr == null) {
                return null;
            }
            if (strArr.length <= 0) {
                return null;
            }
            if (strArr.length != 1) {
                strArr2 = new String[strArr.length - 1];
                System.arraycopy(strArr, 1, strArr2, 0, strArr.length - 1);
            }
            return Connector.getDatabase().rawQuery(strArr[0], strArr2);
        }
    }

    public static <T> T findFirst(Class<T> cls) {
        return (T) findFirst(cls, false);
    }

    @Deprecated
    public static <T> FindExecutor<T> findFirstAsync(Class<T> cls) {
        return findFirstAsync(cls, false);
    }

    public static <T> T findLast(Class<T> cls) {
        return (T) findLast(cls, false);
    }

    @Deprecated
    public static <T> FindExecutor<T> findLastAsync(Class<T> cls) {
        return findLastAsync(cls, false);
    }

    public static DatabaseListener getDBListener() {
        return dbListener;
    }

    public static SQLiteDatabase getDatabase() {
        return Connector.getDatabase();
    }

    public static Handler getHandler() {
        return handler;
    }

    public static void initialize(Context context) {
        LitePalApplication.sContext = context;
    }

    private static boolean isDefaultDatabase(String str) {
        if (!BaseUtility.isLitePalXMLExists()) {
            return false;
        }
        if (!str.endsWith(".db")) {
            str = str + ".db";
        }
        String dbName = LitePalParser.parseLitePalConfiguration().getDbName();
        if (!dbName.endsWith(".db")) {
            dbName = dbName + ".db";
        }
        return str.equalsIgnoreCase(dbName);
    }

    public static <T> boolean isExist(Class<T> cls, String... strArr) {
        return strArr != null && where(strArr).count((Class<?>) cls) > 0;
    }

    public static FluentQuery limit(int i2) {
        FluentQuery fluentQuery = new FluentQuery();
        fluentQuery.mLimit = String.valueOf(i2);
        return fluentQuery;
    }

    public static <T extends LitePalSupport> void markAsDeleted(Collection<T> collection) {
        Iterator<T> it = collection.iterator();
        while (it.hasNext()) {
            it.next().clearSavedState();
        }
    }

    public static <T> T max(Class<?> cls, String str, Class<T> cls2) {
        return (T) max(BaseUtility.changeCase(DBUtility.getTableNameByClassName(cls.getName())), str, cls2);
    }

    @Deprecated
    public static <T> FindExecutor<T> maxAsync(Class<?> cls, String str, Class<T> cls2) {
        return maxAsync(BaseUtility.changeCase(DBUtility.getTableNameByClassName(cls.getName())), str, cls2);
    }

    public static <T> T min(Class<?> cls, String str, Class<T> cls2) {
        return (T) min(BaseUtility.changeCase(DBUtility.getTableNameByClassName(cls.getName())), str, cls2);
    }

    @Deprecated
    public static <T> FindExecutor<T> minAsync(Class<?> cls, String str, Class<T> cls2) {
        return minAsync(BaseUtility.changeCase(DBUtility.getTableNameByClassName(cls.getName())), str, cls2);
    }

    public static FluentQuery offset(int i2) {
        FluentQuery fluentQuery = new FluentQuery();
        fluentQuery.mOffset = String.valueOf(i2);
        return fluentQuery;
    }

    public static FluentQuery order(String str) {
        FluentQuery fluentQuery = new FluentQuery();
        fluentQuery.mOrderBy = str;
        return fluentQuery;
    }

    public static void registerDatabaseListener(DatabaseListener databaseListener) {
        dbListener = databaseListener;
    }

    private static void removeVersionInSharedPreferences(String str) {
        if (isDefaultDatabase(str)) {
            SharedUtil.removeVersion(null);
        } else {
            SharedUtil.removeVersion(str);
        }
    }

    public static <T extends LitePalSupport> boolean saveAll(Collection<T> collection) {
        synchronized (LitePalSupport.class) {
            SQLiteDatabase database = Connector.getDatabase();
            database.beginTransaction();
            try {
                new SaveHandler(database).onSaveAll(collection);
                database.setTransactionSuccessful();
            } catch (Exception e2) {
                e2.printStackTrace();
                return false;
            } finally {
                database.endTransaction();
            }
        }
        return true;
    }

    @Deprecated
    public static <T extends LitePalSupport> SaveExecutor saveAllAsync(final Collection<T> collection) {
        final SaveExecutor saveExecutor = new SaveExecutor();
        saveExecutor.submit(new Runnable() { // from class: com.plv.thirdpart.litepal.Operator.15
            @Override // java.lang.Runnable
            public void run() {
                final boolean z2;
                synchronized (LitePalSupport.class) {
                    try {
                        Operator.saveAll(collection);
                        z2 = true;
                    } catch (Exception unused) {
                        z2 = false;
                    }
                    if (saveExecutor.getListener() != null) {
                        Operator.getHandler().post(new Runnable() { // from class: com.plv.thirdpart.litepal.Operator.15.1
                            @Override // java.lang.Runnable
                            public void run() {
                                saveExecutor.getListener().onFinish(z2);
                            }
                        });
                    }
                }
            }
        });
        return saveExecutor;
    }

    public static FluentQuery select(String... strArr) {
        FluentQuery fluentQuery = new FluentQuery();
        fluentQuery.mColumns = strArr;
        return fluentQuery;
    }

    public static void setTransactionSuccessful() {
        getDatabase().setTransactionSuccessful();
    }

    public static <T> T sum(Class<?> cls, String str, Class<T> cls2) {
        return (T) sum(BaseUtility.changeCase(DBUtility.getTableNameByClassName(cls.getName())), str, cls2);
    }

    @Deprecated
    public static <T> FindExecutor<T> sumAsync(Class<?> cls, String str, Class<T> cls2) {
        return sumAsync(BaseUtility.changeCase(DBUtility.getTableNameByClassName(cls.getName())), str, cls2);
    }

    public static int update(Class<?> cls, ContentValues contentValues, long j2) {
        int iOnUpdate;
        synchronized (LitePalSupport.class) {
            iOnUpdate = new UpdateHandler(Connector.getDatabase()).onUpdate(cls, j2, contentValues);
        }
        return iOnUpdate;
    }

    public static int updateAll(Class<?> cls, ContentValues contentValues, String... strArr) {
        return updateAll(BaseUtility.changeCase(DBUtility.getTableNameByClassName(cls.getName())), contentValues, strArr);
    }

    @Deprecated
    public static UpdateOrDeleteExecutor updateAllAsync(Class<?> cls, ContentValues contentValues, String... strArr) {
        return updateAllAsync(BaseUtility.changeCase(DBUtility.getTableNameByClassName(cls.getName())), contentValues, strArr);
    }

    @Deprecated
    public static UpdateOrDeleteExecutor updateAsync(final Class<?> cls, final ContentValues contentValues, final long j2) {
        final UpdateOrDeleteExecutor updateOrDeleteExecutor = new UpdateOrDeleteExecutor();
        updateOrDeleteExecutor.submit(new Runnable() { // from class: com.plv.thirdpart.litepal.Operator.13
            @Override // java.lang.Runnable
            public void run() {
                synchronized (LitePalSupport.class) {
                    final int iUpdate = Operator.update(cls, contentValues, j2);
                    if (updateOrDeleteExecutor.getListener() != null) {
                        Operator.getHandler().post(new Runnable() { // from class: com.plv.thirdpart.litepal.Operator.13.1
                            @Override // java.lang.Runnable
                            public void run() {
                                updateOrDeleteExecutor.getListener().onFinish(iUpdate);
                            }
                        });
                    }
                }
            }
        });
        return updateOrDeleteExecutor;
    }

    public static void use(LitePalDB litePalDB) {
        synchronized (LitePalSupport.class) {
            LitePalAttr litePalAttr = LitePalAttr.getInstance();
            litePalAttr.setDbName(litePalDB.getDbName());
            litePalAttr.setVersion(litePalDB.getVersion());
            litePalAttr.setStorage(litePalDB.getStorage());
            litePalAttr.setClassNames(litePalDB.getClassNames());
            if (!isDefaultDatabase(litePalDB.getDbName())) {
                litePalAttr.setExtraKeyName(litePalDB.getDbName());
                litePalAttr.setCases(Const.Config.CASES_LOWER);
            }
            Connector.clearLitePalOpenHelperInstance();
        }
    }

    public static void useDefault() {
        synchronized (LitePalSupport.class) {
            LitePalAttr.clearInstance();
            Connector.clearLitePalOpenHelperInstance();
        }
    }

    public static FluentQuery where(String... strArr) {
        FluentQuery fluentQuery = new FluentQuery();
        fluentQuery.mConditions = strArr;
        return fluentQuery;
    }

    public static double average(String str, String str2) {
        double dAverage;
        synchronized (LitePalSupport.class) {
            dAverage = new FluentQuery().average(str, str2);
        }
        return dAverage;
    }

    @Deprecated
    public static AverageExecutor averageAsync(final String str, final String str2) {
        final AverageExecutor averageExecutor = new AverageExecutor();
        averageExecutor.submit(new Runnable() { // from class: com.plv.thirdpart.litepal.Operator.2
            @Override // java.lang.Runnable
            public void run() {
                synchronized (LitePalSupport.class) {
                    final double dAverage = Operator.average(str, str2);
                    if (averageExecutor.getListener() != null) {
                        Operator.getHandler().post(new Runnable() { // from class: com.plv.thirdpart.litepal.Operator.2.1
                            @Override // java.lang.Runnable
                            public void run() {
                                averageExecutor.getListener().onFinish(dAverage);
                            }
                        });
                    }
                }
            }
        });
        return averageExecutor;
    }

    public static int count(String str) {
        int iCount;
        synchronized (LitePalSupport.class) {
            iCount = new FluentQuery().count(str);
        }
        return iCount;
    }

    @Deprecated
    public static CountExecutor countAsync(final String str) {
        final CountExecutor countExecutor = new CountExecutor();
        countExecutor.submit(new Runnable() { // from class: com.plv.thirdpart.litepal.Operator.1
            @Override // java.lang.Runnable
            public void run() {
                synchronized (LitePalSupport.class) {
                    final int iCount = Operator.count(str);
                    if (countExecutor.getListener() != null) {
                        Operator.getHandler().post(new Runnable() { // from class: com.plv.thirdpart.litepal.Operator.1.1
                            @Override // java.lang.Runnable
                            public void run() {
                                countExecutor.getListener().onFinish(iCount);
                            }
                        });
                    }
                }
            }
        });
        return countExecutor;
    }

    public static <T> T find(Class<T> cls, long j2, boolean z2) {
        T t2;
        synchronized (LitePalSupport.class) {
            t2 = (T) new QueryHandler(Connector.getDatabase()).onFind(cls, j2, z2);
        }
        return t2;
    }

    public static <T> List<T> findAll(Class<T> cls, boolean z2, long... jArr) {
        List<T> listOnFindAll;
        synchronized (LitePalSupport.class) {
            listOnFindAll = new QueryHandler(Connector.getDatabase()).onFindAll(cls, z2, jArr);
        }
        return listOnFindAll;
    }

    @Deprecated
    public static <T> FindMultiExecutor<T> findAllAsync(final Class<T> cls, final boolean z2, final long... jArr) {
        final FindMultiExecutor<T> findMultiExecutor = new FindMultiExecutor<>();
        findMultiExecutor.submit(new Runnable() { // from class: com.plv.thirdpart.litepal.Operator.9
            @Override // java.lang.Runnable
            public void run() {
                synchronized (LitePalSupport.class) {
                    final List listFindAll = Operator.findAll(cls, z2, jArr);
                    if (findMultiExecutor.getListener() != null) {
                        Operator.getHandler().post(new Runnable() { // from class: com.plv.thirdpart.litepal.Operator.9.1
                            @Override // java.lang.Runnable
                            public void run() {
                                findMultiExecutor.getListener().onFinish(listFindAll);
                            }
                        });
                    }
                }
            }
        });
        return findMultiExecutor;
    }

    @Deprecated
    public static <T> FindExecutor<T> findAsync(final Class<T> cls, final long j2, final boolean z2) {
        final FindExecutor<T> findExecutor = new FindExecutor<>();
        findExecutor.submit(new Runnable() { // from class: com.plv.thirdpart.litepal.Operator.6
            @Override // java.lang.Runnable
            public void run() {
                synchronized (LitePalSupport.class) {
                    final Object objFind = Operator.find(cls, j2, z2);
                    if (findExecutor.getListener() != null) {
                        Operator.getHandler().post(new Runnable() { // from class: com.plv.thirdpart.litepal.Operator.6.1
                            @Override // java.lang.Runnable
                            public void run() {
                                findExecutor.getListener().onFinish(objFind);
                            }
                        });
                    }
                }
            }
        });
        return findExecutor;
    }

    public static <T> T findFirst(Class<T> cls, boolean z2) {
        T t2;
        synchronized (LitePalSupport.class) {
            t2 = (T) new QueryHandler(Connector.getDatabase()).onFindFirst(cls, z2);
        }
        return t2;
    }

    @Deprecated
    public static <T> FindExecutor<T> findFirstAsync(final Class<T> cls, final boolean z2) {
        final FindExecutor<T> findExecutor = new FindExecutor<>();
        findExecutor.submit(new Runnable() { // from class: com.plv.thirdpart.litepal.Operator.7
            @Override // java.lang.Runnable
            public void run() {
                synchronized (LitePalSupport.class) {
                    final Object objFindFirst = Operator.findFirst(cls, z2);
                    if (findExecutor.getListener() != null) {
                        Operator.getHandler().post(new Runnable() { // from class: com.plv.thirdpart.litepal.Operator.7.1
                            @Override // java.lang.Runnable
                            public void run() {
                                findExecutor.getListener().onFinish(objFindFirst);
                            }
                        });
                    }
                }
            }
        });
        return findExecutor;
    }

    public static <T> T findLast(Class<T> cls, boolean z2) {
        T t2;
        synchronized (LitePalSupport.class) {
            t2 = (T) new QueryHandler(Connector.getDatabase()).onFindLast(cls, z2);
        }
        return t2;
    }

    @Deprecated
    public static <T> FindExecutor<T> findLastAsync(final Class<T> cls, final boolean z2) {
        final FindExecutor<T> findExecutor = new FindExecutor<>();
        findExecutor.submit(new Runnable() { // from class: com.plv.thirdpart.litepal.Operator.8
            @Override // java.lang.Runnable
            public void run() {
                synchronized (LitePalSupport.class) {
                    final Object objFindLast = Operator.findLast(cls, z2);
                    if (findExecutor.getListener() != null) {
                        Operator.getHandler().post(new Runnable() { // from class: com.plv.thirdpart.litepal.Operator.8.1
                            @Override // java.lang.Runnable
                            public void run() {
                                findExecutor.getListener().onFinish(objFindLast);
                            }
                        });
                    }
                }
            }
        });
        return findExecutor;
    }

    public static <T> T max(String str, String str2, Class<T> cls) {
        T t2;
        synchronized (LitePalSupport.class) {
            t2 = (T) new FluentQuery().max(str, str2, cls);
        }
        return t2;
    }

    @Deprecated
    public static <T> FindExecutor<T> maxAsync(final String str, final String str2, final Class<T> cls) {
        final FindExecutor<T> findExecutor = new FindExecutor<>();
        findExecutor.submit(new Runnable() { // from class: com.plv.thirdpart.litepal.Operator.3
            @Override // java.lang.Runnable
            public void run() {
                synchronized (LitePalSupport.class) {
                    final Object objMax = Operator.max(str, str2, (Class<Object>) cls);
                    if (findExecutor.getListener() != null) {
                        Operator.getHandler().post(new Runnable() { // from class: com.plv.thirdpart.litepal.Operator.3.1
                            @Override // java.lang.Runnable
                            public void run() {
                                findExecutor.getListener().onFinish(objMax);
                            }
                        });
                    }
                }
            }
        });
        return findExecutor;
    }

    public static <T> T min(String str, String str2, Class<T> cls) {
        T t2;
        synchronized (LitePalSupport.class) {
            t2 = (T) new FluentQuery().min(str, str2, cls);
        }
        return t2;
    }

    @Deprecated
    public static <T> FindExecutor<T> minAsync(final String str, final String str2, final Class<T> cls) {
        final FindExecutor<T> findExecutor = new FindExecutor<>();
        findExecutor.submit(new Runnable() { // from class: com.plv.thirdpart.litepal.Operator.4
            @Override // java.lang.Runnable
            public void run() {
                synchronized (LitePalSupport.class) {
                    final Object objMin = Operator.min(str, str2, (Class<Object>) cls);
                    if (findExecutor.getListener() != null) {
                        Operator.getHandler().post(new Runnable() { // from class: com.plv.thirdpart.litepal.Operator.4.1
                            @Override // java.lang.Runnable
                            public void run() {
                                findExecutor.getListener().onFinish(objMin);
                            }
                        });
                    }
                }
            }
        });
        return findExecutor;
    }

    public static <T> T sum(String str, String str2, Class<T> cls) {
        T t2;
        synchronized (LitePalSupport.class) {
            t2 = (T) new FluentQuery().sum(str, str2, cls);
        }
        return t2;
    }

    @Deprecated
    public static <T> FindExecutor<T> sumAsync(final String str, final String str2, final Class<T> cls) {
        final FindExecutor<T> findExecutor = new FindExecutor<>();
        findExecutor.submit(new Runnable() { // from class: com.plv.thirdpart.litepal.Operator.5
            @Override // java.lang.Runnable
            public void run() {
                synchronized (LitePalSupport.class) {
                    final Object objSum = Operator.sum(str, str2, (Class<Object>) cls);
                    if (findExecutor.getListener() != null) {
                        Operator.getHandler().post(new Runnable() { // from class: com.plv.thirdpart.litepal.Operator.5.1
                            @Override // java.lang.Runnable
                            public void run() {
                                findExecutor.getListener().onFinish(objSum);
                            }
                        });
                    }
                }
            }
        });
        return findExecutor;
    }

    public static int updateAll(String str, ContentValues contentValues, String... strArr) {
        int iOnUpdateAll;
        synchronized (LitePalSupport.class) {
            iOnUpdateAll = new UpdateHandler(Connector.getDatabase()).onUpdateAll(str, contentValues, strArr);
        }
        return iOnUpdateAll;
    }

    @Deprecated
    public static UpdateOrDeleteExecutor updateAllAsync(final String str, final ContentValues contentValues, final String... strArr) {
        final UpdateOrDeleteExecutor updateOrDeleteExecutor = new UpdateOrDeleteExecutor();
        updateOrDeleteExecutor.submit(new Runnable() { // from class: com.plv.thirdpart.litepal.Operator.14
            @Override // java.lang.Runnable
            public void run() {
                synchronized (LitePalSupport.class) {
                    final int iUpdateAll = Operator.updateAll(str, contentValues, strArr);
                    if (updateOrDeleteExecutor.getListener() != null) {
                        Operator.getHandler().post(new Runnable() { // from class: com.plv.thirdpart.litepal.Operator.14.1
                            @Override // java.lang.Runnable
                            public void run() {
                                updateOrDeleteExecutor.getListener().onFinish(iUpdateAll);
                            }
                        });
                    }
                }
            }
        });
        return updateOrDeleteExecutor;
    }

    @Deprecated
    public static UpdateOrDeleteExecutor deleteAllAsync(final String str, final String... strArr) {
        final UpdateOrDeleteExecutor updateOrDeleteExecutor = new UpdateOrDeleteExecutor();
        updateOrDeleteExecutor.submit(new Runnable() { // from class: com.plv.thirdpart.litepal.Operator.12
            @Override // java.lang.Runnable
            public void run() {
                synchronized (LitePalSupport.class) {
                    final int iDeleteAll = Operator.deleteAll(str, strArr);
                    if (updateOrDeleteExecutor.getListener() != null) {
                        Operator.getHandler().post(new Runnable() { // from class: com.plv.thirdpart.litepal.Operator.12.1
                            @Override // java.lang.Runnable
                            public void run() {
                                updateOrDeleteExecutor.getListener().onFinish(iDeleteAll);
                            }
                        });
                    }
                }
            }
        });
        return updateOrDeleteExecutor;
    }

    public static int deleteAll(String str, String... strArr) {
        int iOnDeleteAll;
        synchronized (LitePalSupport.class) {
            iOnDeleteAll = new DeleteHandler(Connector.getDatabase()).onDeleteAll(str, strArr);
        }
        return iOnDeleteAll;
    }
}
