package com.plv.thirdpart.litepal.crud;

import android.database.sqlite.SQLiteDatabase;
import com.plv.thirdpart.litepal.Operator;
import com.plv.thirdpart.litepal.crud.async.SaveExecutor;
import com.plv.thirdpart.litepal.crud.async.UpdateOrDeleteExecutor;
import com.plv.thirdpart.litepal.exceptions.LitePalSupportException;
import com.plv.thirdpart.litepal.tablemanager.Connector;
import com.plv.thirdpart.litepal.util.BaseUtility;
import com.plv.thirdpart.litepal.util.DBUtility;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* loaded from: classes5.dex */
public class LitePalSupport {
    protected static final String AES = "AES";
    protected static final String MD5 = "MD5";
    Map<String, List<Long>> associatedModelsMapForJoinTable;
    private Map<String, Set<Long>> associatedModelsMapWithFK;
    private Map<String, Long> associatedModelsMapWithoutFK;
    long baseObjId;
    private List<String> fieldsToSetToDefault;
    private List<String> listToClearAssociatedFK;
    private List<String> listToClearSelfFK;

    private void clearFKNameList() {
        getListToClearSelfFK().clear();
        getListToClearAssociatedFK().clear();
    }

    private void clearIdOfModelForJoinTable() {
        Iterator<String> it = getAssociatedModelsMapForJoinTable().keySet().iterator();
        while (it.hasNext()) {
            this.associatedModelsMapForJoinTable.get(it.next()).clear();
        }
        this.associatedModelsMapForJoinTable.clear();
    }

    private void clearIdOfModelWithFK() {
        Iterator<String> it = getAssociatedModelsMapWithFK().keySet().iterator();
        while (it.hasNext()) {
            this.associatedModelsMapWithFK.get(it.next()).clear();
        }
        this.associatedModelsMapWithFK.clear();
    }

    private void clearIdOfModelWithoutFK() {
        getAssociatedModelsMapWithoutFK().clear();
    }

    public void addAssociatedModelForJoinTable(String str, long j2) {
        List<Long> list = getAssociatedModelsMapForJoinTable().get(str);
        if (list != null) {
            list.add(Long.valueOf(j2));
            return;
        }
        ArrayList arrayList = new ArrayList();
        arrayList.add(Long.valueOf(j2));
        this.associatedModelsMapForJoinTable.put(str, arrayList);
    }

    public void addAssociatedModelWithFK(String str, long j2) {
        Set<Long> set = getAssociatedModelsMapWithFK().get(str);
        if (set != null) {
            set.add(Long.valueOf(j2));
            return;
        }
        HashSet hashSet = new HashSet();
        hashSet.add(Long.valueOf(j2));
        this.associatedModelsMapWithFK.put(str, hashSet);
    }

    public void addAssociatedModelWithoutFK(String str, long j2) {
        getAssociatedModelsMapWithoutFK().put(str, Long.valueOf(j2));
    }

    public void addAssociatedTableNameToClearFK(String str) {
        List<String> listToClearAssociatedFK = getListToClearAssociatedFK();
        if (listToClearAssociatedFK.contains(str)) {
            return;
        }
        listToClearAssociatedFK.add(str);
    }

    public void addEmptyModelForJoinTable(String str) {
        if (getAssociatedModelsMapForJoinTable().get(str) == null) {
            this.associatedModelsMapForJoinTable.put(str, new ArrayList());
        }
    }

    public void addFKNameToClearSelf(String str) {
        List<String> listToClearSelfFK = getListToClearSelfFK();
        if (listToClearSelfFK.contains(str)) {
            return;
        }
        listToClearSelfFK.add(str);
    }

    public void assignBaseObjId(long j2) {
        this.baseObjId = j2;
    }

    public void clearAssociatedData() {
        clearIdOfModelWithFK();
        clearIdOfModelWithoutFK();
        clearIdOfModelForJoinTable();
        clearFKNameList();
    }

    public void clearSavedState() {
        this.baseObjId = 0L;
    }

    public int delete() {
        int iOnDelete;
        synchronized (LitePalSupport.class) {
            SQLiteDatabase database = Connector.getDatabase();
            database.beginTransaction();
            try {
                iOnDelete = new DeleteHandler(database).onDelete(this);
                this.baseObjId = 0L;
                database.setTransactionSuccessful();
            } finally {
                database.endTransaction();
            }
        }
        return iOnDelete;
    }

    @Deprecated
    public UpdateOrDeleteExecutor deleteAsync() {
        final UpdateOrDeleteExecutor updateOrDeleteExecutor = new UpdateOrDeleteExecutor();
        updateOrDeleteExecutor.submit(new Runnable() { // from class: com.plv.thirdpart.litepal.crud.LitePalSupport.1
            @Override // java.lang.Runnable
            public void run() {
                synchronized (LitePalSupport.class) {
                    final int iDelete = LitePalSupport.this.delete();
                    if (updateOrDeleteExecutor.getListener() != null) {
                        Operator.getHandler().post(new Runnable() { // from class: com.plv.thirdpart.litepal.crud.LitePalSupport.1.1
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

    public Map<String, List<Long>> getAssociatedModelsMapForJoinTable() {
        if (this.associatedModelsMapForJoinTable == null) {
            this.associatedModelsMapForJoinTable = new HashMap();
        }
        return this.associatedModelsMapForJoinTable;
    }

    public Map<String, Set<Long>> getAssociatedModelsMapWithFK() {
        if (this.associatedModelsMapWithFK == null) {
            this.associatedModelsMapWithFK = new HashMap();
        }
        return this.associatedModelsMapWithFK;
    }

    public Map<String, Long> getAssociatedModelsMapWithoutFK() {
        if (this.associatedModelsMapWithoutFK == null) {
            this.associatedModelsMapWithoutFK = new HashMap();
        }
        return this.associatedModelsMapWithoutFK;
    }

    public long getBaseObjId() {
        return this.baseObjId;
    }

    public String getClassName() {
        return getClass().getName();
    }

    public List<String> getFieldsToSetToDefault() {
        if (this.fieldsToSetToDefault == null) {
            this.fieldsToSetToDefault = new ArrayList();
        }
        return this.fieldsToSetToDefault;
    }

    public List<String> getListToClearAssociatedFK() {
        if (this.listToClearAssociatedFK == null) {
            this.listToClearAssociatedFK = new ArrayList();
        }
        return this.listToClearAssociatedFK;
    }

    public List<String> getListToClearSelfFK() {
        if (this.listToClearSelfFK == null) {
            this.listToClearSelfFK = new ArrayList();
        }
        return this.listToClearSelfFK;
    }

    public String getTableName() {
        return BaseUtility.changeCase(DBUtility.getTableNameByClassName(getClassName()));
    }

    public boolean isSaved() {
        return this.baseObjId > 0;
    }

    public boolean save() {
        try {
            saveThrows();
            return true;
        } catch (Exception e2) {
            e2.printStackTrace();
            return false;
        }
    }

    @Deprecated
    public SaveExecutor saveAsync() {
        final SaveExecutor saveExecutor = new SaveExecutor();
        saveExecutor.submit(new Runnable() { // from class: com.plv.thirdpart.litepal.crud.LitePalSupport.4
            @Override // java.lang.Runnable
            public void run() {
                synchronized (LitePalSupport.class) {
                    final boolean zSave = LitePalSupport.this.save();
                    if (saveExecutor.getListener() != null) {
                        Operator.getHandler().post(new Runnable() { // from class: com.plv.thirdpart.litepal.crud.LitePalSupport.4.1
                            @Override // java.lang.Runnable
                            public void run() {
                                saveExecutor.getListener().onFinish(zSave);
                            }
                        });
                    }
                }
            }
        });
        return saveExecutor;
    }

    public boolean saveOrUpdate(String... strArr) {
        synchronized (LitePalSupport.class) {
            if (strArr != null) {
                if (strArr.length != 0) {
                    List listFind = Operator.where(strArr).find(getClass());
                    if (listFind.isEmpty()) {
                        return save();
                    }
                    SQLiteDatabase database = Connector.getDatabase();
                    database.beginTransaction();
                    try {
                        Iterator it = listFind.iterator();
                        while (it.hasNext()) {
                            this.baseObjId = ((LitePalSupport) it.next()).getBaseObjId();
                            new SaveHandler(database).onSave(this);
                            clearAssociatedData();
                        }
                        database.setTransactionSuccessful();
                        return true;
                    } catch (Exception e2) {
                        e2.printStackTrace();
                        return false;
                    } finally {
                        database.endTransaction();
                    }
                }
            }
            return save();
        }
    }

    @Deprecated
    public SaveExecutor saveOrUpdateAsync(final String... strArr) {
        final SaveExecutor saveExecutor = new SaveExecutor();
        saveExecutor.submit(new Runnable() { // from class: com.plv.thirdpart.litepal.crud.LitePalSupport.5
            @Override // java.lang.Runnable
            public void run() {
                synchronized (LitePalSupport.class) {
                    final boolean zSaveOrUpdate = LitePalSupport.this.saveOrUpdate(strArr);
                    if (saveExecutor.getListener() != null) {
                        Operator.getHandler().post(new Runnable() { // from class: com.plv.thirdpart.litepal.crud.LitePalSupport.5.1
                            @Override // java.lang.Runnable
                            public void run() {
                                saveExecutor.getListener().onFinish(zSaveOrUpdate);
                            }
                        });
                    }
                }
            }
        });
        return saveExecutor;
    }

    public void saveThrows() {
        synchronized (LitePalSupport.class) {
            SQLiteDatabase database = Connector.getDatabase();
            database.beginTransaction();
            try {
                try {
                    new SaveHandler(database).onSave(this);
                    clearAssociatedData();
                    database.setTransactionSuccessful();
                } catch (Exception e2) {
                    throw new LitePalSupportException(e2.getMessage(), e2);
                }
            } finally {
                database.endTransaction();
            }
        }
    }

    public void setToDefault(String str) {
        getFieldsToSetToDefault().add(str);
    }

    public int update(long j2) {
        int iOnUpdate;
        synchronized (LitePalSupport.class) {
            SQLiteDatabase database = Connector.getDatabase();
            database.beginTransaction();
            try {
                try {
                    iOnUpdate = new UpdateHandler(Connector.getDatabase()).onUpdate(this, j2);
                    getFieldsToSetToDefault().clear();
                    database.setTransactionSuccessful();
                } catch (Exception e2) {
                    throw new LitePalSupportException(e2.getMessage(), e2);
                }
            } finally {
                database.endTransaction();
            }
        }
        return iOnUpdate;
    }

    public int updateAll(String... strArr) {
        int iOnUpdateAll;
        synchronized (LitePalSupport.class) {
            SQLiteDatabase database = Connector.getDatabase();
            database.beginTransaction();
            try {
                try {
                    iOnUpdateAll = new UpdateHandler(Connector.getDatabase()).onUpdateAll(this, strArr);
                    getFieldsToSetToDefault().clear();
                    database.setTransactionSuccessful();
                } catch (Exception e2) {
                    throw new LitePalSupportException(e2.getMessage(), e2);
                }
            } finally {
                database.endTransaction();
            }
        }
        return iOnUpdateAll;
    }

    @Deprecated
    public UpdateOrDeleteExecutor updateAllAsync(final String... strArr) {
        final UpdateOrDeleteExecutor updateOrDeleteExecutor = new UpdateOrDeleteExecutor();
        updateOrDeleteExecutor.submit(new Runnable() { // from class: com.plv.thirdpart.litepal.crud.LitePalSupport.3
            @Override // java.lang.Runnable
            public void run() {
                synchronized (LitePalSupport.class) {
                    final int iUpdateAll = LitePalSupport.this.updateAll(strArr);
                    if (updateOrDeleteExecutor.getListener() != null) {
                        Operator.getHandler().post(new Runnable() { // from class: com.plv.thirdpart.litepal.crud.LitePalSupport.3.1
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
    public UpdateOrDeleteExecutor updateAsync(final long j2) {
        final UpdateOrDeleteExecutor updateOrDeleteExecutor = new UpdateOrDeleteExecutor();
        updateOrDeleteExecutor.submit(new Runnable() { // from class: com.plv.thirdpart.litepal.crud.LitePalSupport.2
            @Override // java.lang.Runnable
            public void run() {
                synchronized (LitePalSupport.class) {
                    final int iUpdate = LitePalSupport.this.update(j2);
                    if (updateOrDeleteExecutor.getListener() != null) {
                        Operator.getHandler().post(new Runnable() { // from class: com.plv.thirdpart.litepal.crud.LitePalSupport.2.1
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
}
