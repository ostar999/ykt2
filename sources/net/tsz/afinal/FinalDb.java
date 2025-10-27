package net.tsz.afinal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import net.tsz.afinal.db.sqlite.CursorUtils;
import net.tsz.afinal.db.sqlite.DbModel;
import net.tsz.afinal.db.sqlite.OneToManyLazyLoader;
import net.tsz.afinal.db.sqlite.SqlBuilder;
import net.tsz.afinal.db.sqlite.SqlInfo;
import net.tsz.afinal.db.table.KeyValue;
import net.tsz.afinal.db.table.ManyToOne;
import net.tsz.afinal.db.table.OneToMany;
import net.tsz.afinal.db.table.TableInfo;
import net.tsz.afinal.exception.DbException;

/* loaded from: classes9.dex */
public class FinalDb {
    private static final String TAG = "FinalDb";
    private static HashMap<String, FinalDb> daoMap = new HashMap<>();
    private DaoConfig config;
    private SQLiteDatabase db;

    public static class DaoConfig {
        private DbUpdateListener dbUpdateListener;
        private String targetDirectory;
        private Context mContext = null;
        private String mDbName = "afinal.db";
        private int dbVersion = 1;
        private boolean debug = true;

        public Context getContext() {
            return this.mContext;
        }

        public String getDbName() {
            return this.mDbName;
        }

        public DbUpdateListener getDbUpdateListener() {
            return this.dbUpdateListener;
        }

        public int getDbVersion() {
            return this.dbVersion;
        }

        public String getTargetDirectory() {
            return this.targetDirectory;
        }

        public boolean isDebug() {
            return this.debug;
        }

        public void setContext(Context context) {
            this.mContext = context;
        }

        public void setDbName(String str) {
            this.mDbName = str;
        }

        public void setDbUpdateListener(DbUpdateListener dbUpdateListener) {
            this.dbUpdateListener = dbUpdateListener;
        }

        public void setDbVersion(int i2) {
            this.dbVersion = i2;
        }

        public void setDebug(boolean z2) {
            this.debug = z2;
        }

        public void setTargetDirectory(String str) {
            this.targetDirectory = str;
        }
    }

    public interface DbUpdateListener {
        void onUpgrade(SQLiteDatabase sQLiteDatabase, int i2, int i3);
    }

    public class SqliteDbHelper extends SQLiteOpenHelper {
        private DbUpdateListener mDbUpdateListener;

        public SqliteDbHelper(Context context, String str, int i2, DbUpdateListener dbUpdateListener) {
            super(context, str, (SQLiteDatabase.CursorFactory) null, i2);
            this.mDbUpdateListener = dbUpdateListener;
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onCreate(SQLiteDatabase sQLiteDatabase) {
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i2, int i3) throws SQLException {
            DbUpdateListener dbUpdateListener = this.mDbUpdateListener;
            if (dbUpdateListener != null) {
                dbUpdateListener.onUpgrade(sQLiteDatabase, i2, i3);
            } else {
                FinalDb.this.dropDb();
            }
        }
    }

    private FinalDb(DaoConfig daoConfig) {
        if (daoConfig == null) {
            throw new DbException("daoConfig is null");
        }
        if (daoConfig.getContext() == null) {
            throw new DbException("android context is null");
        }
        if (daoConfig.getTargetDirectory() == null || daoConfig.getTargetDirectory().trim().length() <= 0) {
            this.db = new SqliteDbHelper(daoConfig.getContext().getApplicationContext(), daoConfig.getDbName(), daoConfig.getDbVersion(), daoConfig.getDbUpdateListener()).getWritableDatabase();
        } else {
            this.db = createDbFileOnSDCard(daoConfig.getTargetDirectory(), daoConfig.getDbName());
        }
        this.config = daoConfig;
    }

    private void checkTableExist(Class<?> cls) throws SQLException {
        if (tableIsExist(TableInfo.get(cls))) {
            return;
        }
        String creatTableSQL = SqlBuilder.getCreatTableSQL(cls);
        debugSql(creatTableSQL);
        this.db.execSQL(creatTableSQL);
    }

    public static FinalDb create(Context context) {
        DaoConfig daoConfig = new DaoConfig();
        daoConfig.setContext(context);
        return create(daoConfig);
    }

    private SQLiteDatabase createDbFileOnSDCard(String str, String str2) {
        File file = new File(str, str2);
        if (file.exists()) {
            return SQLiteDatabase.openOrCreateDatabase(file, (SQLiteDatabase.CursorFactory) null);
        }
        try {
            if (file.createNewFile()) {
                return SQLiteDatabase.openOrCreateDatabase(file, (SQLiteDatabase.CursorFactory) null);
            }
            return null;
        } catch (IOException e2) {
            throw new DbException("数据库文件创建失败", e2);
        }
    }

    private void debugSql(String str) {
        DaoConfig daoConfig = this.config;
        if (daoConfig == null || !daoConfig.isDebug()) {
            return;
        }
        Log.d("Debug SQL", ">>>>>>  " + str);
    }

    private void exeSqlInfo(SqlInfo sqlInfo) throws SQLException {
        if (sqlInfo == null) {
            Log.e(TAG, "sava error:sqlInfo is null");
        } else {
            debugSql(sqlInfo.getSql());
            this.db.execSQL(sqlInfo.getSql(), sqlInfo.getBindArgsAsArray());
        }
    }

    private static synchronized FinalDb getInstance(DaoConfig daoConfig) {
        FinalDb finalDb;
        finalDb = daoMap.get(daoConfig.getDbName());
        if (finalDb == null) {
            finalDb = new FinalDb(daoConfig);
            daoMap.put(daoConfig.getDbName(), finalDb);
        }
        return finalDb;
    }

    private void insertContentValues(List<KeyValue> list, ContentValues contentValues) {
        if (list == null || contentValues == null) {
            Log.w(TAG, "insertContentValues: List<KeyValue> is empty or ContentValues is empty!");
            return;
        }
        for (KeyValue keyValue : list) {
            contentValues.put(keyValue.getKey(), keyValue.getValue().toString());
        }
    }

    private boolean tableIsExist(TableInfo tableInfo) {
        if (tableInfo.isCheckDatabese()) {
            return true;
        }
        Cursor cursorRawQuery = null;
        try {
            try {
                String str = "SELECT COUNT(*) AS c FROM sqlite_master WHERE type ='table' AND name ='" + tableInfo.getTableName() + "' ";
                debugSql(str);
                cursorRawQuery = this.db.rawQuery(str, null);
            } catch (Exception e2) {
                e2.printStackTrace();
                if (cursorRawQuery != null) {
                }
            }
            if (cursorRawQuery == null || !cursorRawQuery.moveToNext() || cursorRawQuery.getInt(0) <= 0) {
                return false;
            }
            tableInfo.setCheckDatabese(true);
            cursorRawQuery.close();
            return true;
        } finally {
            if (cursorRawQuery != null) {
                cursorRawQuery.close();
            }
        }
    }

    public void delete(Object obj) throws SQLException {
        checkTableExist(obj.getClass());
        exeSqlInfo(SqlBuilder.buildDeleteSql(obj));
    }

    public void deleteById(Class<?> cls, Object obj) throws SQLException {
        checkTableExist(cls);
        exeSqlInfo(SqlBuilder.buildDeleteSql(cls, obj));
    }

    public void deleteByWhere(Class<?> cls, String str) throws SQLException {
        checkTableExist(cls);
        String strBuildDeleteSql = SqlBuilder.buildDeleteSql(cls, str);
        debugSql(strBuildDeleteSql);
        this.db.beginTransaction();
        try {
            this.db.execSQL(strBuildDeleteSql);
            this.db.setTransactionSuccessful();
        } catch (Exception e2) {
            e2.printStackTrace();
            this.db.endTransaction();
        }
        this.db.endTransaction();
    }

    public void dropDb() throws SQLException {
        Cursor cursorRawQuery = this.db.rawQuery("SELECT name FROM sqlite_master WHERE type ='table'", null);
        if (cursorRawQuery != null) {
            while (cursorRawQuery.moveToNext()) {
                try {
                    this.db.execSQL("DROP TABLE " + cursorRawQuery.getString(0));
                } catch (SQLException e2) {
                    Log.e(TAG, e2.getMessage());
                }
            }
        }
        if (cursorRawQuery != null) {
            cursorRawQuery.close();
        }
    }

    public <T> List<T> findAll(Class<T> cls) throws SQLException {
        checkTableExist(cls);
        return findAllBySql(cls, SqlBuilder.getSelectSQL(cls));
    }

    public <T> List<T> findAllBySql(Class<T> cls, String str) throws SQLException {
        checkTableExist(cls);
        debugSql(str);
        Cursor cursorRawQuery = this.db.rawQuery(str, null);
        try {
            try {
                ArrayList arrayList = new ArrayList();
                while (cursorRawQuery.moveToNext()) {
                    arrayList.add(CursorUtils.getEntity(cursorRawQuery, cls, this));
                }
                cursorRawQuery.close();
                return arrayList;
            } catch (Exception e2) {
                e2.printStackTrace();
                if (cursorRawQuery != null) {
                    cursorRawQuery.close();
                }
                return null;
            }
        } catch (Throwable th) {
            if (cursorRawQuery != null) {
                cursorRawQuery.close();
            }
            throw th;
        }
    }

    public <T> List<T> findAllByWhere(Class<T> cls, String str) throws SQLException {
        checkTableExist(cls);
        return findAllBySql(cls, SqlBuilder.getSelectSQLByWhere(cls, str));
    }

    public <T> T findById(Object obj, Class<T> cls) throws SQLException {
        checkTableExist(cls);
        SqlInfo selectSqlAsSqlInfo = SqlBuilder.getSelectSqlAsSqlInfo(cls, obj);
        if (selectSqlAsSqlInfo == null) {
            return null;
        }
        debugSql(selectSqlAsSqlInfo.getSql());
        Cursor cursorRawQuery = this.db.rawQuery(selectSqlAsSqlInfo.getSql(), selectSqlAsSqlInfo.getBindArgsAsStringArray());
        try {
            try {
                if (cursorRawQuery.moveToNext()) {
                    return (T) CursorUtils.getEntity(cursorRawQuery, cls, this);
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            return null;
        } finally {
            cursorRawQuery.close();
        }
    }

    public DbModel findDbModelBySQL(String str) {
        debugSql(str);
        Cursor cursorRawQuery = this.db.rawQuery(str, null);
        try {
            try {
                if (cursorRawQuery.moveToNext()) {
                    return CursorUtils.getDbModel(cursorRawQuery);
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            return null;
        } finally {
            cursorRawQuery.close();
        }
    }

    public List<DbModel> findDbModelListBySQL(String str) {
        debugSql(str);
        Cursor cursorRawQuery = this.db.rawQuery(str, null);
        ArrayList arrayList = new ArrayList();
        while (cursorRawQuery.moveToNext()) {
            try {
                try {
                    arrayList.add(CursorUtils.getDbModel(cursorRawQuery));
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            } finally {
                cursorRawQuery.close();
            }
        }
        return arrayList;
    }

    public <T> List<T> findTop(Class<T> cls, int i2, String str) throws SQLException {
        checkTableExist(cls);
        return findAllBySql(cls, SqlBuilder.getSelectSQL(cls) + " ORDER BY " + str + " limit " + i2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public <T> T findWithManyToOneById(Object obj, Class<T> cls) throws SQLException {
        checkTableExist(cls);
        String selectSQL = SqlBuilder.getSelectSQL(cls, obj);
        debugSql(selectSQL);
        DbModel dbModelFindDbModelBySQL = findDbModelBySQL(selectSQL);
        if (dbModelFindDbModelBySQL != null) {
            return (T) loadManyToOne(CursorUtils.dbModel2Entity(dbModelFindDbModelBySQL, cls), cls, new Class[0]);
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public <T> T findWithOneToManyById(Object obj, Class<T> cls) throws SQLException {
        checkTableExist(cls);
        String selectSQL = SqlBuilder.getSelectSQL(cls, obj);
        debugSql(selectSQL);
        DbModel dbModelFindDbModelBySQL = findDbModelBySQL(selectSQL);
        if (dbModelFindDbModelBySQL != null) {
            return (T) loadOneToMany(CursorUtils.dbModel2Entity(dbModelFindDbModelBySQL, cls), cls, new Class[0]);
        }
        return null;
    }

    public <T> T loadManyToOne(T t2, Class<T> cls, Class<?>... clsArr) {
        Object objFindById;
        if (t2 != null) {
            try {
                for (ManyToOne manyToOne : TableInfo.get((Class<?>) cls).manyToOneMap.values()) {
                    Object value = manyToOne.getValue(t2);
                    if (value != null) {
                        int i2 = 0;
                        boolean z2 = true;
                        boolean z3 = clsArr == null || clsArr.length == 0;
                        int length = clsArr.length;
                        while (true) {
                            if (i2 >= length) {
                                z2 = z3;
                                break;
                            }
                            if (manyToOne.getManyClass() == clsArr[i2]) {
                                break;
                            }
                            i2++;
                        }
                        if (z2 && (objFindById = findById(Integer.valueOf(value.toString()), manyToOne.getDataType())) != null) {
                            manyToOne.setValue(t2, objFindById);
                        }
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return t2;
    }

    public <T> T loadOneToMany(T t2, Class<T> cls, Class<?>... clsArr) {
        if (t2 != null) {
            try {
                Collection<OneToMany> collectionValues = TableInfo.get((Class<?>) cls).oneToManyMap.values();
                Object value = TableInfo.get((Class<?>) cls).getId().getValue(t2);
                for (OneToMany oneToMany : collectionValues) {
                    int i2 = 0;
                    boolean z2 = true;
                    boolean z3 = clsArr == null || clsArr.length == 0;
                    int length = clsArr.length;
                    while (true) {
                        if (i2 >= length) {
                            z2 = z3;
                            break;
                        }
                        if (oneToMany.getOneClass() == clsArr[i2]) {
                            break;
                        }
                        i2++;
                    }
                    if (z2) {
                        List<T> listFindAllByWhere = findAllByWhere(oneToMany.getOneClass(), oneToMany.getColumn() + "=" + value);
                        if (listFindAllByWhere != null) {
                            if (oneToMany.getDataType() == OneToManyLazyLoader.class) {
                                ((OneToManyLazyLoader) oneToMany.getValue(t2)).setList(listFindAllByWhere);
                            } else {
                                oneToMany.setValue(t2, listFindAllByWhere);
                            }
                        }
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return t2;
    }

    public void save(Object obj) throws SQLException {
        checkTableExist(obj.getClass());
        exeSqlInfo(SqlBuilder.buildInsertSql(obj));
    }

    public boolean saveBindId(Object obj) throws IllegalAccessException, SecurityException, SQLException, IllegalArgumentException, InvocationTargetException {
        checkTableExist(obj.getClass());
        List<KeyValue> saveKeyValueListByEntity = SqlBuilder.getSaveKeyValueListByEntity(obj);
        if (saveKeyValueListByEntity == null || saveKeyValueListByEntity.size() <= 0) {
            return false;
        }
        TableInfo tableInfo = TableInfo.get(obj.getClass());
        ContentValues contentValues = new ContentValues();
        insertContentValues(saveKeyValueListByEntity, contentValues);
        Long lValueOf = Long.valueOf(this.db.insert(tableInfo.getTableName(), null, contentValues));
        if (lValueOf.longValue() == -1) {
            return false;
        }
        tableInfo.getId().setValue(obj, lValueOf);
        return true;
    }

    public void update(Object obj) throws SQLException {
        checkTableExist(obj.getClass());
        exeSqlInfo(SqlBuilder.getUpdateSqlAsSqlInfo(obj));
    }

    public <T> List<T> findAll(Class<T> cls, String str) throws SQLException {
        checkTableExist(cls);
        return findAllBySql(cls, SqlBuilder.getSelectSQL(cls) + " ORDER BY " + str);
    }

    public <T> List<T> findAllByWhere(Class<T> cls, String str, String str2) throws SQLException {
        checkTableExist(cls);
        return findAllBySql(cls, SqlBuilder.getSelectSQLByWhere(cls, str) + " ORDER BY " + str2);
    }

    public void update(Object obj, String str) throws SQLException {
        checkTableExist(obj.getClass());
        exeSqlInfo(SqlBuilder.getUpdateSqlAsSqlInfo(obj, str));
    }

    public static FinalDb create(Context context, boolean z2) {
        DaoConfig daoConfig = new DaoConfig();
        daoConfig.setContext(context);
        daoConfig.setDebug(z2);
        return create(daoConfig);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public <T> T findWithManyToOneById(Object obj, Class<T> cls, Class<?>... clsArr) throws SQLException {
        checkTableExist(cls);
        String selectSQL = SqlBuilder.getSelectSQL(cls, obj);
        debugSql(selectSQL);
        DbModel dbModelFindDbModelBySQL = findDbModelBySQL(selectSQL);
        if (dbModelFindDbModelBySQL != null) {
            return (T) loadManyToOne(CursorUtils.dbModel2Entity(dbModelFindDbModelBySQL, cls), cls, clsArr);
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public <T> T findWithOneToManyById(Object obj, Class<T> cls, Class<?>... clsArr) throws SQLException {
        checkTableExist(cls);
        String selectSQL = SqlBuilder.getSelectSQL(cls, obj);
        debugSql(selectSQL);
        DbModel dbModelFindDbModelBySQL = findDbModelBySQL(selectSQL);
        if (dbModelFindDbModelBySQL != null) {
            return (T) loadOneToMany(CursorUtils.dbModel2Entity(dbModelFindDbModelBySQL, cls), cls, clsArr);
        }
        return null;
    }

    public static FinalDb create(Context context, String str) {
        DaoConfig daoConfig = new DaoConfig();
        daoConfig.setContext(context);
        daoConfig.setDbName(str);
        return create(daoConfig);
    }

    public static FinalDb create(Context context, String str, boolean z2) {
        DaoConfig daoConfig = new DaoConfig();
        daoConfig.setContext(context);
        daoConfig.setDbName(str);
        daoConfig.setDebug(z2);
        return create(daoConfig);
    }

    public static FinalDb create(Context context, String str, String str2) {
        DaoConfig daoConfig = new DaoConfig();
        daoConfig.setContext(context);
        daoConfig.setDbName(str2);
        daoConfig.setTargetDirectory(str);
        return create(daoConfig);
    }

    public static FinalDb create(Context context, String str, String str2, boolean z2) {
        DaoConfig daoConfig = new DaoConfig();
        daoConfig.setContext(context);
        daoConfig.setTargetDirectory(str);
        daoConfig.setDbName(str2);
        daoConfig.setDebug(z2);
        return create(daoConfig);
    }

    public static FinalDb create(Context context, String str, boolean z2, int i2, DbUpdateListener dbUpdateListener) {
        DaoConfig daoConfig = new DaoConfig();
        daoConfig.setContext(context);
        daoConfig.setDbName(str);
        daoConfig.setDebug(z2);
        daoConfig.setDbVersion(i2);
        daoConfig.setDbUpdateListener(dbUpdateListener);
        return create(daoConfig);
    }

    public static FinalDb create(Context context, String str, String str2, boolean z2, int i2, DbUpdateListener dbUpdateListener) {
        DaoConfig daoConfig = new DaoConfig();
        daoConfig.setContext(context);
        daoConfig.setTargetDirectory(str);
        daoConfig.setDbName(str2);
        daoConfig.setDebug(z2);
        daoConfig.setDbVersion(i2);
        daoConfig.setDbUpdateListener(dbUpdateListener);
        return create(daoConfig);
    }

    public static FinalDb create(DaoConfig daoConfig) {
        return getInstance(daoConfig);
    }
}
