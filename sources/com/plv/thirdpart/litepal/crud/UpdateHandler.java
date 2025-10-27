package com.plv.thirdpart.litepal.crud;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import com.plv.thirdpart.litepal.annotation.Encrypt;
import com.plv.thirdpart.litepal.exceptions.LitePalSupportException;
import com.plv.thirdpart.litepal.util.BaseUtility;
import com.plv.thirdpart.litepal.util.DBUtility;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* loaded from: classes5.dex */
public class UpdateHandler extends DataHandler {
    public UpdateHandler(SQLiteDatabase sQLiteDatabase) {
        this.mDatabase = sQLiteDatabase;
    }

    private void analyzeAssociations(LitePalSupport litePalSupport) {
        try {
            analyzeAssociatedModels(litePalSupport, getAssociationInfo(litePalSupport.getClassName()));
        } catch (Exception e2) {
            throw new LitePalSupportException(e2.getMessage(), e2);
        }
    }

    private void convertContentValues(ContentValues contentValues) {
        HashMap map = new HashMap();
        for (String str : contentValues.keySet()) {
            if (DBUtility.isFieldNameConflictWithSQLiteKeywords(str)) {
                map.put(str, contentValues.get(str));
            }
        }
        for (String str2 : map.keySet()) {
            String strConvertToValidColumnName = DBUtility.convertToValidColumnName(str2);
            Object obj = contentValues.get(str2);
            contentValues.remove(str2);
            if (obj == null) {
                contentValues.putNull(strConvertToValidColumnName);
            } else {
                String name = obj.getClass().getName();
                if ("java.lang.Byte".equals(name)) {
                    contentValues.put(strConvertToValidColumnName, (Byte) obj);
                } else if ("[B".equals(name)) {
                    contentValues.put(strConvertToValidColumnName, (byte[]) obj);
                } else if ("java.lang.Boolean".equals(name)) {
                    contentValues.put(strConvertToValidColumnName, (Boolean) obj);
                } else if ("java.lang.String".equals(name)) {
                    contentValues.put(strConvertToValidColumnName, (String) obj);
                } else if ("java.lang.Float".equals(name)) {
                    contentValues.put(strConvertToValidColumnName, (Float) obj);
                } else if ("java.lang.Long".equals(name)) {
                    contentValues.put(strConvertToValidColumnName, (Long) obj);
                } else if ("java.lang.Integer".equals(name)) {
                    contentValues.put(strConvertToValidColumnName, (Integer) obj);
                } else if ("java.lang.Short".equals(name)) {
                    contentValues.put(strConvertToValidColumnName, (Short) obj);
                } else if ("java.lang.Double".equals(name)) {
                    contentValues.put(strConvertToValidColumnName, (Double) obj);
                }
            }
        }
    }

    private int doUpdateAllAction(String str, ContentValues contentValues, String... strArr) {
        BaseUtility.checkConditionsCorrect(strArr);
        if (contentValues.size() > 0) {
            return this.mDatabase.update(str, contentValues, getWhereClause(strArr), getWhereArgs(strArr));
        }
        return 0;
    }

    private int doUpdateAssociations(LitePalSupport litePalSupport, long j2, ContentValues contentValues) {
        analyzeAssociations(litePalSupport);
        updateSelfTableForeignKey(litePalSupport, contentValues);
        return updateAssociatedTableForeignKey(litePalSupport, j2) + 0;
    }

    private void putFieldsToDefaultValue(LitePalSupport litePalSupport, ContentValues contentValues, long... jArr) throws NoSuchFieldException {
        String str = null;
        try {
            try {
                LitePalSupport emptyModel = getEmptyModel(litePalSupport);
                Class<?> cls = emptyModel.getClass();
                String str2 = null;
                for (String str3 : litePalSupport.getFieldsToSetToDefault()) {
                    try {
                        if (!isIdColumn(str3)) {
                            try {
                                Field declaredField = cls.getDeclaredField(str3);
                                if (!isCollection(declaredField.getType())) {
                                    putContentValuesForUpdate(emptyModel, declaredField, contentValues);
                                } else if (jArr != null && jArr.length > 0 && BaseUtility.isGenericTypeSupported(getGenericTypeName(declaredField))) {
                                    String genericTableName = DBUtility.getGenericTableName(litePalSupport.getClassName(), declaredField.getName());
                                    String genericValueIdColumnName = DBUtility.getGenericValueIdColumnName(litePalSupport.getClassName());
                                    StringBuilder sb = new StringBuilder();
                                    int length = jArr.length;
                                    int i2 = 0;
                                    boolean z2 = false;
                                    while (i2 < length) {
                                        long j2 = jArr[i2];
                                        if (z2) {
                                            sb.append(" or ");
                                        }
                                        sb.append(genericValueIdColumnName);
                                        sb.append(" = ");
                                        sb.append(j2);
                                        i2++;
                                        z2 = true;
                                    }
                                    this.mDatabase.delete(genericTableName, sb.toString(), null);
                                }
                                str2 = str3;
                            } catch (NoSuchFieldException e2) {
                                e = e2;
                                str = str3;
                                throw new LitePalSupportException(LitePalSupportException.noSuchFieldExceptioin(litePalSupport.getClassName(), str), e);
                            }
                        }
                    } catch (NoSuchFieldException e3) {
                        e = e3;
                        str = str2;
                    }
                }
            } catch (Exception e4) {
                throw new LitePalSupportException(e4.getMessage(), e4);
            }
        } catch (NoSuchFieldException e5) {
            e = e5;
        }
    }

    private int updateAssociatedTableForeignKey(LitePalSupport litePalSupport, long j2) {
        Map<String, Set<Long>> associatedModelsMapWithFK = litePalSupport.getAssociatedModelsMapWithFK();
        ContentValues contentValues = new ContentValues();
        for (String str : associatedModelsMapWithFK.keySet()) {
            contentValues.clear();
            contentValues.put(getForeignKeyColumnName(litePalSupport.getTableName()), Long.valueOf(j2));
            Set<Long> set = associatedModelsMapWithFK.get(str);
            if (set != null && !set.isEmpty()) {
                return this.mDatabase.update(str, contentValues, getWhereOfIdsWithOr(set), null);
            }
        }
        return 0;
    }

    private void updateGenericTables(LitePalSupport litePalSupport, List<Field> list, long... jArr) throws IllegalAccessException, SecurityException, IllegalArgumentException, InvocationTargetException {
        Iterator it;
        Iterator<Field> it2;
        long[] jArr2 = jArr;
        if (jArr2 == null || jArr2.length <= 0) {
            return;
        }
        Iterator<Field> it3 = list.iterator();
        while (it3.hasNext()) {
            Field next = it3.next();
            Encrypt encrypt = (Encrypt) next.getAnnotation(Encrypt.class);
            String genericTypeName = getGenericTypeName(next);
            String strAlgorithm = (encrypt == null || !"java.lang.String".equals(genericTypeName)) ? null : encrypt.algorithm();
            next.setAccessible(true);
            Collection collection = (Collection) next.get(litePalSupport);
            if (collection != null && !collection.isEmpty()) {
                String genericTableName = DBUtility.getGenericTableName(litePalSupport.getClassName(), next.getName());
                String genericValueIdColumnName = DBUtility.getGenericValueIdColumnName(litePalSupport.getClassName());
                int length = jArr2.length;
                int i2 = 0;
                while (i2 < length) {
                    long j2 = jArr2[i2];
                    this.mDatabase.delete(genericTableName, genericValueIdColumnName + " = ?", new String[]{String.valueOf(j2)});
                    Iterator it4 = collection.iterator();
                    while (it4.hasNext()) {
                        Object next2 = it4.next();
                        ContentValues contentValues = new ContentValues();
                        contentValues.put(genericValueIdColumnName, Long.valueOf(j2));
                        Object objEncryptValue = encryptValue(strAlgorithm, next2);
                        if (litePalSupport.getClassName().equals(genericTypeName)) {
                            LitePalSupport litePalSupport2 = (LitePalSupport) objEncryptValue;
                            if (litePalSupport2 != null) {
                                long baseObjId = litePalSupport2.getBaseObjId();
                                if (baseObjId > 0) {
                                    contentValues.put(DBUtility.getM2MSelfRefColumnName(next), Long.valueOf(baseObjId));
                                    it = it4;
                                    it2 = it3;
                                }
                            }
                        } else {
                            it = it4;
                            it2 = it3;
                            DynamicExecutor.send(contentValues, "put", new Object[]{DBUtility.convertToValidColumnName(BaseUtility.changeCase(next.getName())), objEncryptValue}, contentValues.getClass(), new Class[]{String.class, getGenericTypeClass(next)});
                        }
                        this.mDatabase.insert(genericTableName, null, contentValues);
                        it4 = it;
                        it3 = it2;
                    }
                    i2++;
                    jArr2 = jArr;
                    it3 = it3;
                }
            }
            jArr2 = jArr;
            it3 = it3;
        }
    }

    private void updateSelfTableForeignKey(LitePalSupport litePalSupport, ContentValues contentValues) {
        Map<String, Long> associatedModelsMapWithoutFK = litePalSupport.getAssociatedModelsMapWithoutFK();
        for (String str : associatedModelsMapWithoutFK.keySet()) {
            contentValues.put(getForeignKeyColumnName(str), associatedModelsMapWithoutFK.get(str));
        }
    }

    public int onUpdate(LitePalSupport litePalSupport, long j2) throws IllegalAccessException, NoSuchFieldException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        List<Field> supportedFields = getSupportedFields(litePalSupport.getClassName());
        updateGenericTables(litePalSupport, getSupportedGenericFields(litePalSupport.getClassName()), j2);
        ContentValues contentValues = new ContentValues();
        putFieldsValue(litePalSupport, supportedFields, contentValues);
        putFieldsToDefaultValue(litePalSupport, contentValues, j2);
        if (contentValues.size() <= 0) {
            return 0;
        }
        return this.mDatabase.update(litePalSupport.getTableName(), contentValues, "id = " + j2, null);
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x005e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int onUpdateAll(com.plv.thirdpart.litepal.crud.LitePalSupport r9, java.lang.String... r10) throws java.lang.IllegalAccessException, java.lang.NoSuchFieldException, java.lang.NoSuchMethodException, java.lang.SecurityException, java.lang.IllegalArgumentException, java.lang.reflect.InvocationTargetException {
        /*
            r8 = this;
            com.plv.thirdpart.litepal.util.BaseUtility.checkConditionsCorrect(r10)
            r0 = 0
            if (r10 == 0) goto L11
            int r1 = r10.length
            if (r1 <= 0) goto L11
            r1 = r10[r0]
            java.lang.String r1 = com.plv.thirdpart.litepal.util.DBUtility.convertWhereClauseToColumnName(r1)
            r10[r0] = r1
        L11:
            java.lang.String r1 = r9.getClassName()
            java.util.List r1 = r8.getSupportedFields(r1)
            java.lang.String r2 = r9.getClassName()
            java.util.List r2 = r8.getSupportedGenericFields(r2)
            boolean r3 = r2.isEmpty()
            if (r3 != 0) goto L5e
            java.lang.String r3 = "id"
            java.lang.String[] r3 = new java.lang.String[]{r3}
            com.plv.thirdpart.litepal.FluentQuery r3 = com.plv.thirdpart.litepal.Operator.select(r3)
            com.plv.thirdpart.litepal.FluentQuery r3 = r3.where(r10)
            java.lang.Class r4 = r9.getClass()
            java.util.List r3 = r3.find(r4)
            int r4 = r3.size()
            if (r4 <= 0) goto L5e
            int r4 = r3.size()
            long[] r5 = new long[r4]
        L49:
            if (r0 >= r4) goto L5a
            java.lang.Object r6 = r3.get(r0)
            com.plv.thirdpart.litepal.crud.LitePalSupport r6 = (com.plv.thirdpart.litepal.crud.LitePalSupport) r6
            long r6 = r6.getBaseObjId()
            r5[r0] = r6
            int r0 = r0 + 1
            goto L49
        L5a:
            r8.updateGenericTables(r9, r2, r5)
            goto L5f
        L5e:
            r5 = 0
        L5f:
            android.content.ContentValues r0 = new android.content.ContentValues
            r0.<init>()
            r8.putFieldsValue(r9, r1, r0)
            r8.putFieldsToDefaultValue(r9, r0, r5)
            java.lang.String r9 = r9.getTableName()
            int r9 = r8.doUpdateAllAction(r9, r0, r10)
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.plv.thirdpart.litepal.crud.UpdateHandler.onUpdateAll(com.plv.thirdpart.litepal.crud.LitePalSupport, java.lang.String[]):int");
    }

    public int onUpdate(Class<?> cls, long j2, ContentValues contentValues) {
        if (contentValues.size() <= 0) {
            return 0;
        }
        convertContentValues(contentValues);
        return this.mDatabase.update(getTableName(cls), contentValues, "id = " + j2, null);
    }

    public int onUpdateAll(String str, ContentValues contentValues, String... strArr) {
        BaseUtility.checkConditionsCorrect(strArr);
        if (strArr != null && strArr.length > 0) {
            strArr[0] = DBUtility.convertWhereClauseToColumnName(strArr[0]);
        }
        convertContentValues(contentValues);
        return doUpdateAllAction(str, contentValues, strArr);
    }
}
