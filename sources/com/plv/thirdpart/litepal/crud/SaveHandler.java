package com.plv.thirdpart.litepal.crud;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.plv.thirdpart.litepal.annotation.Encrypt;
import com.plv.thirdpart.litepal.crud.model.AssociationsInfo;
import com.plv.thirdpart.litepal.exceptions.LitePalSupportException;
import com.plv.thirdpart.litepal.util.BaseUtility;
import com.plv.thirdpart.litepal.util.DBUtility;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* loaded from: classes5.dex */
public class SaveHandler extends DataHandler {
    private ContentValues values = new ContentValues();

    public SaveHandler(SQLiteDatabase sQLiteDatabase) {
        this.mDatabase = sQLiteDatabase;
    }

    private void afterSave(LitePalSupport litePalSupport, List<Field> list, List<Field> list2, long j2) throws IllegalAccessException, SecurityException, IllegalArgumentException, InvocationTargetException {
        throwIfSaveFailed(j2);
        assignIdValue(litePalSupport, getIdField(list), j2);
        updateGenericTables(litePalSupport, list2, j2);
        updateAssociatedTableWithFK(litePalSupport);
        insertIntermediateJoinTableValue(litePalSupport, false);
    }

    private void afterUpdate(LitePalSupport litePalSupport, List<Field> list) throws IllegalAccessException, SecurityException, IllegalArgumentException, InvocationTargetException {
        updateGenericTables(litePalSupport, list, litePalSupport.getBaseObjId());
        updateAssociatedTableWithFK(litePalSupport);
        insertIntermediateJoinTableValue(litePalSupport, true);
        clearFKValueInAssociatedTable(litePalSupport);
    }

    private void assignIdValue(LitePalSupport litePalSupport, Field field, long j2) {
        try {
            giveBaseObjIdValue(litePalSupport, j2);
            if (field != null) {
                giveModelIdValue(litePalSupport, field.getName(), field.getType(), j2);
            }
        } catch (Exception e2) {
            throw new LitePalSupportException(e2.getMessage(), e2);
        }
    }

    private void beforeSave(LitePalSupport litePalSupport, List<Field> list, ContentValues contentValues) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        putFieldsValue(litePalSupport, list, contentValues);
        putForeignKeyValue(contentValues, litePalSupport);
    }

    private void beforeUpdate(LitePalSupport litePalSupport, List<Field> list, ContentValues contentValues) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        putFieldsValue(litePalSupport, list, contentValues);
        putForeignKeyValue(contentValues, litePalSupport);
        Iterator<String> it = litePalSupport.getListToClearSelfFK().iterator();
        while (it.hasNext()) {
            contentValues.putNull(it.next());
        }
    }

    private void clearFKValueInAssociatedTable(LitePalSupport litePalSupport) {
        for (String str : litePalSupport.getListToClearAssociatedFK()) {
            String foreignKeyColumnName = getForeignKeyColumnName(litePalSupport.getTableName());
            ContentValues contentValues = new ContentValues();
            contentValues.putNull(foreignKeyColumnName);
            this.mDatabase.update(str, contentValues, foreignKeyColumnName + " = " + litePalSupport.getBaseObjId(), null);
        }
    }

    private void doSaveAction(LitePalSupport litePalSupport, List<Field> list, List<Field> list2) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        this.values.clear();
        beforeSave(litePalSupport, list, this.values);
        afterSave(litePalSupport, list, list2, saving(litePalSupport, this.values));
    }

    private void doUpdateAction(LitePalSupport litePalSupport, List<Field> list, List<Field> list2) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        this.values.clear();
        beforeUpdate(litePalSupport, list, this.values);
        updating(litePalSupport, this.values);
        afterUpdate(litePalSupport, list2);
    }

    private Field getIdField(List<Field> list) {
        for (Field field : list) {
            if (isIdColumn(field.getName())) {
                return field;
            }
        }
        return null;
    }

    private String getWhereForJoinTableToDelete(LitePalSupport litePalSupport) {
        return getForeignKeyColumnName(litePalSupport.getTableName()) + " = ?";
    }

    private void giveModelIdValue(LitePalSupport litePalSupport, String str, Class<?> cls, long j2) throws IllegalAccessException, SecurityException, IllegalArgumentException {
        Object objValueOf;
        if (shouldGiveModelIdValue(str, cls, j2)) {
            if (cls == Integer.TYPE || cls == Integer.class) {
                objValueOf = Integer.valueOf((int) j2);
            } else {
                if (cls != Long.TYPE && cls != Long.class) {
                    throw new LitePalSupportException(LitePalSupportException.ID_TYPE_INVALID_EXCEPTION);
                }
                objValueOf = Long.valueOf(j2);
            }
            DynamicExecutor.setField(litePalSupport, str, objValueOf, litePalSupport.getClass());
        }
    }

    private void insertIntermediateJoinTableValue(LitePalSupport litePalSupport, boolean z2) {
        Map<String, List<Long>> associatedModelsMapForJoinTable = litePalSupport.getAssociatedModelsMapForJoinTable();
        ContentValues contentValues = new ContentValues();
        for (String str : associatedModelsMapForJoinTable.keySet()) {
            String intermediateTableName = getIntermediateTableName(litePalSupport, str);
            if (z2) {
                this.mDatabase.delete(intermediateTableName, getWhereForJoinTableToDelete(litePalSupport), new String[]{String.valueOf(litePalSupport.getBaseObjId())});
            }
            List<Long> list = associatedModelsMapForJoinTable.get(str);
            if (list != null) {
                Iterator<Long> it = list.iterator();
                while (it.hasNext()) {
                    long jLongValue = it.next().longValue();
                    contentValues.clear();
                    contentValues.put(getForeignKeyColumnName(litePalSupport.getTableName()), Long.valueOf(litePalSupport.getBaseObjId()));
                    contentValues.put(getForeignKeyColumnName(str), Long.valueOf(jLongValue));
                    this.mDatabase.insert(intermediateTableName, null, contentValues);
                }
            }
        }
    }

    private void putForeignKeyValue(ContentValues contentValues, LitePalSupport litePalSupport) {
        Map<String, Long> associatedModelsMapWithoutFK = litePalSupport.getAssociatedModelsMapWithoutFK();
        for (String str : associatedModelsMapWithoutFK.keySet()) {
            contentValues.put(getForeignKeyColumnName(str), associatedModelsMapWithoutFK.get(str));
        }
    }

    private long saving(LitePalSupport litePalSupport, ContentValues contentValues) {
        if (contentValues.size() == 0) {
            contentValues.putNull("id");
        }
        return this.mDatabase.insert(litePalSupport.getTableName(), null, contentValues);
    }

    private boolean shouldGiveModelIdValue(String str, Class<?> cls, long j2) {
        return (str == null || cls == null || j2 <= 0) ? false : true;
    }

    private void throwIfSaveFailed(long j2) {
        if (j2 == -1) {
            throw new LitePalSupportException(LitePalSupportException.SAVE_FAILED);
        }
    }

    private void updateAssociatedTableWithFK(LitePalSupport litePalSupport) {
        Map<String, Set<Long>> associatedModelsMapWithFK = litePalSupport.getAssociatedModelsMapWithFK();
        ContentValues contentValues = new ContentValues();
        for (String str : associatedModelsMapWithFK.keySet()) {
            contentValues.clear();
            contentValues.put(getForeignKeyColumnName(litePalSupport.getTableName()), Long.valueOf(litePalSupport.getBaseObjId()));
            Set<Long> set = associatedModelsMapWithFK.get(str);
            if (set != null && !set.isEmpty()) {
                this.mDatabase.update(str, contentValues, getWhereOfIdsWithOr(set), null);
            }
        }
    }

    private void updateGenericTables(LitePalSupport litePalSupport, List<Field> list, long j2) throws IllegalAccessException, SecurityException, IllegalArgumentException, InvocationTargetException {
        for (Field field : list) {
            Encrypt encrypt = (Encrypt) field.getAnnotation(Encrypt.class);
            String genericTypeName = getGenericTypeName(field);
            String strAlgorithm = (encrypt == null || !"java.lang.String".equals(genericTypeName)) ? null : encrypt.algorithm();
            char c3 = 1;
            field.setAccessible(true);
            Collection collection = (Collection) field.get(litePalSupport);
            if (collection != null) {
                Log.d(DataHandler.TAG, "updateGenericTables: class name is " + litePalSupport.getClassName() + " , field name is " + field.getName());
                String genericTableName = DBUtility.getGenericTableName(litePalSupport.getClassName(), field.getName());
                String genericValueIdColumnName = DBUtility.getGenericValueIdColumnName(litePalSupport.getClassName());
                this.mDatabase.delete(genericTableName, genericValueIdColumnName + " = ?", new String[]{String.valueOf(j2)});
                for (Object obj : collection) {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(genericValueIdColumnName, Long.valueOf(j2));
                    Object objEncryptValue = encryptValue(strAlgorithm, obj);
                    if (litePalSupport.getClassName().equals(genericTypeName)) {
                        LitePalSupport litePalSupport2 = (LitePalSupport) objEncryptValue;
                        if (litePalSupport2 != null) {
                            long baseObjId = litePalSupport2.getBaseObjId();
                            if (baseObjId > 0) {
                                contentValues.put(DBUtility.getM2MSelfRefColumnName(field), Long.valueOf(baseObjId));
                            }
                        }
                    } else {
                        Object[] objArr = new Object[2];
                        objArr[0] = BaseUtility.changeCase(DBUtility.convertToValidColumnName(field.getName()));
                        objArr[c3] = objEncryptValue;
                        Class[] clsArr = new Class[2];
                        clsArr[0] = String.class;
                        clsArr[c3] = getGenericTypeClass(field);
                        DynamicExecutor.send(contentValues, "put", objArr, contentValues.getClass(), clsArr);
                    }
                    this.mDatabase.insert(genericTableName, null, contentValues);
                    c3 = 1;
                }
            }
        }
    }

    private void updating(LitePalSupport litePalSupport, ContentValues contentValues) {
        if (contentValues.size() > 0) {
            this.mDatabase.update(litePalSupport.getTableName(), contentValues, "id = ?", new String[]{String.valueOf(litePalSupport.getBaseObjId())});
        }
    }

    public void onSave(LitePalSupport litePalSupport) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        String className = litePalSupport.getClassName();
        List<Field> supportedFields = getSupportedFields(className);
        List<Field> supportedGenericFields = getSupportedGenericFields(className);
        Collection<AssociationsInfo> associationInfo = getAssociationInfo(className);
        if (litePalSupport.isSaved()) {
            analyzeAssociatedModels(litePalSupport, associationInfo);
            doUpdateAction(litePalSupport, supportedFields, supportedGenericFields);
        } else {
            analyzeAssociatedModels(litePalSupport, associationInfo);
            doSaveAction(litePalSupport, supportedFields, supportedGenericFields);
            analyzeAssociatedModels(litePalSupport, associationInfo);
        }
    }

    public <T extends LitePalSupport> void onSaveAll(Collection<T> collection) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        if (collection == null || collection.size() <= 0) {
            return;
        }
        LitePalSupport[] litePalSupportArr = (LitePalSupport[]) collection.toArray(new LitePalSupport[0]);
        String className = litePalSupportArr[0].getClassName();
        List<Field> supportedFields = getSupportedFields(className);
        List<Field> supportedGenericFields = getSupportedGenericFields(className);
        Collection<AssociationsInfo> associationInfo = getAssociationInfo(className);
        for (LitePalSupport litePalSupport : litePalSupportArr) {
            if (litePalSupport.isSaved()) {
                analyzeAssociatedModels(litePalSupport, associationInfo);
                doUpdateAction(litePalSupport, supportedFields, supportedGenericFields);
            } else {
                analyzeAssociatedModels(litePalSupport, associationInfo);
                doSaveAction(litePalSupport, supportedFields, supportedGenericFields);
                analyzeAssociatedModels(litePalSupport, associationInfo);
            }
            litePalSupport.clearAssociatedData();
        }
    }
}
