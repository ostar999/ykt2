package com.arialyy.aria.orm;

import android.database.SQLException;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public abstract class DbEntity {
    private static final Object LOCK = new Object();
    public long rowID = -1;

    public static boolean checkDataExist(Class cls, String... strArr) {
        return DelegateWrapper.getInstance().checkDataExist(cls, strArr);
    }

    private static <T extends DbEntity> void checkListData(List<T> list) {
        if (list == null || list.isEmpty()) {
            throw new NullPointerException("列表数据为空");
        }
    }

    public static <T extends DbEntity> void clean(Class<T> cls) throws SQLException {
        DelegateWrapper.getInstance().clean(cls);
    }

    public static void exeSql(String str) throws SQLException {
        DelegateWrapper.getInstance().exeSql(str);
    }

    public static <T extends DbEntity> List<T> findAllData(Class<T> cls) {
        return DelegateWrapper.getInstance().findAllData(cls);
    }

    public static <T extends DbEntity> List<T> findDataByFuzzy(Class<T> cls, String str) {
        return DelegateWrapper.getInstance().findDataByFuzzy(cls, str);
    }

    public static <T extends DbEntity> List<T> findDatas(Class<T> cls, String... strArr) {
        return DelegateWrapper.getInstance().findData(cls, strArr);
    }

    public static <T extends DbEntity> T findFirst(Class<T> cls) {
        List listFindAllData = findAllData(cls);
        if (listFindAllData == null || listFindAllData.size() == 0) {
            return null;
        }
        return (T) listFindAllData.get(0);
    }

    public static <T extends AbsDbWrapper> List<T> findRelationData(Class<T> cls, String... strArr) {
        return DelegateWrapper.getInstance().findRelationData(cls, strArr);
    }

    public static <T extends DbEntity> void insertManyData(List<T> list) {
        checkListData(list);
        DelegateWrapper.getInstance().insertManyData(list);
    }

    public static <T extends DbEntity> void saveAll(List<T> list) {
        checkListData(list);
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        DelegateWrapper delegateWrapper = DelegateWrapper.getInstance();
        for (T t2 : list) {
            if (t2.rowID == -1) {
                arrayList.add(t2);
            } else if (delegateWrapper.isExist(t2.getClass(), t2.rowID)) {
                arrayList2.add(t2);
            } else {
                arrayList.add(t2);
            }
        }
        if (!arrayList.isEmpty()) {
            delegateWrapper.insertManyData(arrayList);
        }
        if (arrayList2.isEmpty()) {
            return;
        }
        delegateWrapper.updateManyData(arrayList2);
    }

    public static boolean tableExists(Class<DbEntity> cls) {
        return DelegateWrapper.getInstance().tableExists(cls);
    }

    private boolean thisIsExist() {
        return this.rowID != -1 && DelegateWrapper.getInstance().isExist(getClass(), this.rowID);
    }

    public static <T extends DbEntity> void updateManyData(List<T> list) {
        checkListData(list);
        DelegateWrapper.getInstance().updateManyData(list);
    }

    public void deleteData() {
        deleteData(getClass(), "rowid=?", this.rowID + "");
    }

    public long getRowID() {
        return this.rowID;
    }

    public void insert() {
        DelegateWrapper.getInstance().insertData(this);
    }

    public void save() {
        synchronized (LOCK) {
            if (thisIsExist()) {
                update();
            } else {
                insert();
            }
        }
    }

    public void update() {
        DelegateWrapper.getInstance().updateData(this);
    }

    public static <T extends DbEntity> void deleteData(Class<T> cls, String... strArr) {
        DelegateWrapper.getInstance().delData(cls, strArr);
    }

    public static <T extends DbEntity> List<T> findDataByFuzzy(Class<T> cls, int i2, int i3, String str) {
        return DelegateWrapper.getInstance().findDataByFuzzy(cls, i2, i3, str);
    }

    public static <T extends DbEntity> List<T> findDatas(Class<T> cls, int i2, int i3, String... strArr) {
        if (i2 < 1 || i3 < 1) {
            return null;
        }
        return DelegateWrapper.getInstance().findData(cls, i2, i3, strArr);
    }

    public static <T extends AbsDbWrapper> List<T> findRelationData(Class<T> cls, int i2, int i3, String... strArr) {
        if (i2 < 1 || i3 < 1) {
            return null;
        }
        return DelegateWrapper.getInstance().findRelationData(cls, i2, i3, strArr);
    }

    public static <T extends DbEntity> T findFirst(Class<T> cls, String... strArr) {
        List<T> listFindData = DelegateWrapper.getInstance().findData(cls, strArr);
        if (listFindData != null && listFindData.size() > 0) {
            return listFindData.get(0);
        }
        return null;
    }
}
