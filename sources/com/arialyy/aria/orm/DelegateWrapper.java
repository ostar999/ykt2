package com.arialyy.aria.orm;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import java.util.List;

/* loaded from: classes2.dex */
public class DelegateWrapper {
    private static volatile DelegateWrapper INSTANCE = null;
    private static final String TAG = "DelegateWrapper";
    private DelegateManager mDManager;
    private SQLiteDatabase mDb;

    private DelegateWrapper() {
    }

    public static DelegateWrapper getInstance() {
        if (INSTANCE != null) {
            return INSTANCE;
        }
        throw new NullPointerException("请在Application中调用init进行数据库工具注册注册");
    }

    public static DelegateWrapper init(Context context) {
        if (INSTANCE == null) {
            synchronized (DelegateWrapper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new DelegateWrapper(context);
                }
            }
        }
        return INSTANCE;
    }

    public boolean checkDataExist(Class cls, String... strArr) {
        return SqlUtil.checkDataExist(this.mDb, cls, strArr);
    }

    public <T extends DbEntity> void clean(Class<T> cls) throws SQLException {
        SqlUtil.clean(this.mDb, cls);
    }

    public <T extends DbEntity> void delData(Class<T> cls, String... strArr) {
        ((DelegateUpdate) this.mDManager.getDelegate(DelegateUpdate.class)).delData(this.mDb, cls, strArr);
    }

    public void exeSql(String str) throws SQLException {
        this.mDb.execSQL(str);
    }

    public <T extends DbEntity> List<T> findAllData(Class<T> cls) {
        return ((DelegateFind) this.mDManager.getDelegate(DelegateFind.class)).findAllData(this.mDb, cls);
    }

    public <T extends DbEntity> List<T> findData(Class<T> cls, String... strArr) {
        return ((DelegateFind) this.mDManager.getDelegate(DelegateFind.class)).findData(this.mDb, cls, strArr);
    }

    public <T extends DbEntity> List<T> findDataByFuzzy(Class<T> cls, String str) {
        return ((DelegateFind) this.mDManager.getDelegate(DelegateFind.class)).findDataByFuzzy(this.mDb, cls, str);
    }

    public <T extends AbsDbWrapper> List<T> findRelationData(Class<T> cls, String... strArr) {
        return ((DelegateFind) this.mDManager.getDelegate(DelegateFind.class)).findRelationData(this.mDb, cls, strArr);
    }

    public int[] getRowId(Class cls) {
        return ((DelegateFind) this.mDManager.getDelegate(DelegateFind.class)).getRowId(this.mDb, cls);
    }

    public void insertData(DbEntity dbEntity) {
        ((DelegateUpdate) this.mDManager.getDelegate(DelegateUpdate.class)).insertData(this.mDb, dbEntity);
    }

    public <T extends DbEntity> void insertManyData(List<T> list) {
        ((DelegateUpdate) this.mDManager.getDelegate(DelegateUpdate.class)).insertManyData(this.mDb, list);
    }

    public <T extends DbEntity> boolean isExist(Class<T> cls, long j2) {
        return ((DelegateFind) this.mDManager.getDelegate(DelegateFind.class)).itemExist(this.mDb, cls, j2);
    }

    public boolean tableExists(Class cls) {
        return SqlUtil.tableExists(this.mDb, (Class<? extends DbEntity>) cls);
    }

    public void updateData(DbEntity dbEntity) {
        ((DelegateUpdate) this.mDManager.getDelegate(DelegateUpdate.class)).updateData(this.mDb, dbEntity);
    }

    public <T extends DbEntity> void updateManyData(List<T> list) {
        ((DelegateUpdate) this.mDManager.getDelegate(DelegateUpdate.class)).updateManyData(this.mDb, list);
    }

    private DelegateWrapper(Context context) {
        this.mDb = SqlHelper.init(context.getApplicationContext()).getDb();
        this.mDManager = DelegateManager.getInstance();
    }

    public <T extends DbEntity> List<T> findData(Class<T> cls, int i2, int i3, String... strArr) {
        return ((DelegateFind) this.mDManager.getDelegate(DelegateFind.class)).findData(this.mDb, cls, i2, i3, strArr);
    }

    public <T extends DbEntity> List<T> findDataByFuzzy(Class<T> cls, int i2, int i3, String str) {
        return ((DelegateFind) this.mDManager.getDelegate(DelegateFind.class)).findDataByFuzzy(this.mDb, cls, i2, i3, str);
    }

    public <T extends AbsDbWrapper> List<T> findRelationData(Class<T> cls, int i2, int i3, String... strArr) {
        return ((DelegateFind) this.mDManager.getDelegate(DelegateFind.class)).findRelationData(this.mDb, cls, i2, i3, strArr);
    }

    public int getRowId(Class cls, Object[] objArr, Object[] objArr2) {
        return ((DelegateFind) this.mDManager.getDelegate(DelegateFind.class)).getRowId(this.mDb, cls, objArr, objArr2);
    }

    public boolean isExist(String str, long j2) {
        return ((DelegateFind) this.mDManager.getDelegate(DelegateFind.class)).itemExist(this.mDb, str, j2);
    }
}
