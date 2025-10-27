package com.arialyy.aria.orm;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import com.arialyy.aria.orm.annotation.Primary;
import com.arialyy.aria.util.ALog;
import com.arialyy.aria.util.CommonUtil;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/* loaded from: classes2.dex */
class DelegateUpdate extends AbsDelegate {
    private DelegateUpdate() {
    }

    private ContentValues createValues(DbEntity dbEntity) throws SecurityException {
        List<Field> allFields = CommonUtil.getAllFields(dbEntity.getClass());
        if (allFields.size() > 0) {
            ContentValues contentValues = new ContentValues();
            try {
                for (Field field : allFields) {
                    field.setAccessible(true);
                    if (!isIgnore(dbEntity, field)) {
                        Class<?> type = field.getType();
                        contentValues.put(field.getName(), SqlUtil.encodeStr((type == Map.class && SqlUtil.checkMap(field)) ? SqlUtil.map2Str((Map) field.get(dbEntity)) : (type == List.class && SqlUtil.checkList(field)) ? SqlUtil.list2Str(dbEntity, field) : field.get(dbEntity) != null ? field.get(dbEntity).toString() : null));
                    }
                }
                return contentValues;
            } catch (IllegalAccessException e2) {
                e2.printStackTrace();
            }
        }
        return null;
    }

    private boolean isIgnore(Object obj, Field field) throws IllegalAccessException {
        Object obj2;
        if (SqlUtil.isIgnore(field) || (obj2 = field.get(obj)) == null) {
            return true;
        }
        if ((obj2 instanceof String) && TextUtils.isEmpty(String.valueOf(obj2))) {
            return true;
        }
        if ((obj2 instanceof List) && ((List) obj2).size() == 0) {
            return true;
        }
        if ((obj2 instanceof Map) && ((Map) obj2).size() == 0) {
            return true;
        }
        if (SqlUtil.isPrimary(field)) {
            return ((Primary) field.getAnnotation(Primary.class)).autoincrement();
        }
        return false;
    }

    public synchronized <T extends DbEntity> void delData(SQLiteDatabase sQLiteDatabase, Class<T> cls, String... strArr) {
        SqlUtil.checkOrCreateTable(sQLiteDatabase, cls);
        SQLiteDatabase sQLiteDatabaseCheckDb = checkDb(sQLiteDatabase);
        if (CommonUtil.checkSqlExpression(strArr)) {
            String strReplace = ("DELETE FROM " + CommonUtil.getClassName((Class) cls) + " WHERE " + strArr[0] + " ").replace("?", "%s");
            int length = strArr.length - 1;
            String[] strArr2 = new String[length];
            int i2 = 0;
            while (i2 < length) {
                int i3 = i2 + 1;
                strArr2[i2] = String.format("'%s'", SqlUtil.encodeStr(strArr[i3]));
                i2 = i3;
            }
            sQLiteDatabaseCheckDb.execSQL(String.format(strReplace, strArr2));
        }
    }

    public synchronized void insertData(SQLiteDatabase sQLiteDatabase, DbEntity dbEntity) {
        SqlUtil.checkOrCreateTable(sQLiteDatabase, dbEntity.getClass());
        SQLiteDatabase sQLiteDatabaseCheckDb = checkDb(sQLiteDatabase);
        ContentValues contentValuesCreateValues = createValues(dbEntity);
        if (contentValuesCreateValues != null) {
            dbEntity.rowID = sQLiteDatabaseCheckDb.insert(CommonUtil.getClassName(dbEntity), null, contentValuesCreateValues);
        } else {
            ALog.e("AbsDelegate", "保存记录失败，记录没有属性字段");
        }
    }

    public synchronized <T extends DbEntity> void insertManyData(SQLiteDatabase sQLiteDatabase, List<T> list) {
        SQLiteDatabase sQLiteDatabaseCheckDb = checkDb(sQLiteDatabase);
        sQLiteDatabaseCheckDb.beginTransaction();
        try {
            try {
                Class<?> cls = null;
                String className = null;
                for (T t2 : list) {
                    if (cls == null || cls != t2.getClass() || className == null) {
                        cls = t2.getClass();
                        className = CommonUtil.getClassName((Class) cls);
                        SqlUtil.checkOrCreateTable(sQLiteDatabaseCheckDb, cls);
                    }
                    ContentValues contentValuesCreateValues = createValues(t2);
                    if (contentValuesCreateValues == null) {
                        ALog.e("AbsDelegate", "保存记录失败，记录没有属性字段");
                    } else {
                        t2.rowID = sQLiteDatabaseCheckDb.insert(className, null, contentValuesCreateValues);
                    }
                }
                sQLiteDatabaseCheckDb.setTransactionSuccessful();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        } finally {
            sQLiteDatabaseCheckDb.endTransaction();
        }
    }

    public synchronized void updateData(SQLiteDatabase sQLiteDatabase, DbEntity dbEntity) {
        SqlUtil.checkOrCreateTable(sQLiteDatabase, dbEntity.getClass());
        SQLiteDatabase sQLiteDatabaseCheckDb = checkDb(sQLiteDatabase);
        ContentValues contentValuesCreateValues = createValues(dbEntity);
        if (contentValuesCreateValues != null) {
            sQLiteDatabaseCheckDb.update(CommonUtil.getClassName(dbEntity), contentValuesCreateValues, "rowid=?", new String[]{String.valueOf(dbEntity.rowID)});
        } else {
            ALog.e("AbsDelegate", "更新记录失败，记录没有属性字段");
        }
    }

    public synchronized <T extends DbEntity> void updateManyData(SQLiteDatabase sQLiteDatabase, List<T> list) {
        SQLiteDatabase sQLiteDatabaseCheckDb = checkDb(sQLiteDatabase);
        sQLiteDatabaseCheckDb.beginTransaction();
        try {
            try {
                Class<?> cls = null;
                String className = null;
                for (T t2 : list) {
                    if (cls == null || cls != t2.getClass() || className == null) {
                        cls = t2.getClass();
                        className = CommonUtil.getClassName((Class) cls);
                    }
                    ContentValues contentValuesCreateValues = createValues(t2);
                    if (contentValuesCreateValues == null) {
                        ALog.e("AbsDelegate", "更新记录失败，记录没有属性字段");
                    } else {
                        sQLiteDatabaseCheckDb.update(className, contentValuesCreateValues, "rowid=?", new String[]{String.valueOf(t2.rowID)});
                    }
                }
                sQLiteDatabaseCheckDb.setTransactionSuccessful();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        } finally {
            sQLiteDatabaseCheckDb.endTransaction();
        }
    }
}
