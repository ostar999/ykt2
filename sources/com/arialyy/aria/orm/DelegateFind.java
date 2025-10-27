package com.arialyy.aria.orm;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.SparseArray;
import cn.hutool.core.text.StrPool;
import com.arialyy.aria.orm.annotation.Many;
import com.arialyy.aria.orm.annotation.One;
import com.arialyy.aria.util.ALog;
import com.arialyy.aria.util.CommonUtil;
import com.umeng.analytics.pro.am;
import java.lang.reflect.Field;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes2.dex */
class DelegateFind extends AbsDelegate {
    private final String PARENT_COLUMN_ALIAS = "p";
    private final String CHILD_COLUMN_ALIAS = am.aF;

    private DelegateFind() {
    }

    private <T extends DbEntity> T createChild(Class<T> cls, List<Field> list, Cursor cursor) throws IllegalAccessException, InstantiationException, SecurityException, IllegalArgumentException {
        T tNewInstance = cls.newInstance();
        tNewInstance.rowID = cursor.getInt(cursor.getColumnIndex(am.aF.concat("rowid")));
        for (Field field : list) {
            field.setAccessible(true);
            setFieldValue(field.getType(), field, cursor.getColumnIndex(am.aF.concat(field.getName())), cursor, tNewInstance);
        }
        return tNewInstance;
    }

    private <T extends DbEntity> List<T> createChildren(SQLiteDatabase sQLiteDatabase, Class<T> cls, List<Field> list, String str, String str2, DbEntity dbEntity) throws IllegalAccessException, SecurityException, IllegalArgumentException {
        for (Field field : list) {
            field.setAccessible(true);
            if (field.getName().equals(str2)) {
                Object objEncode = field.get(dbEntity);
                if (objEncode instanceof String) {
                    objEncode = URLEncoder.encode((String) objEncode);
                }
                return findData(sQLiteDatabase, cls, str + "='" + objEncode + "'");
            }
        }
        return new ArrayList();
    }

    private <T extends DbEntity> T createParent(int i2, Class<T> cls, List<Field> list, Cursor cursor) throws IllegalAccessException, InstantiationException, SecurityException, IllegalArgumentException {
        T tNewInstance = cls.newInstance();
        tNewInstance.rowID = i2;
        for (Field field : list) {
            field.setAccessible(true);
            setFieldValue(field.getType(), field, cursor.getColumnIndex("p".concat(field.getName())), cursor, tNewInstance);
        }
        return tNewInstance;
    }

    private <T extends DbEntity> List<T> exeNormalDataSql(SQLiteDatabase sQLiteDatabase, Class<T> cls, String str, String[] strArr) throws SecurityException, SQLException {
        Cursor cursorRawQuery;
        SqlUtil.checkOrCreateTable(sQLiteDatabase, cls);
        if (strArr != null) {
            String[] strArr2 = new String[strArr.length];
            int i2 = 0;
            for (String str2 : strArr) {
                strArr2[i2] = SqlUtil.encodeStr(str2);
                i2++;
            }
            cursorRawQuery = sQLiteDatabase.rawQuery(str, strArr2);
        } else {
            cursorRawQuery = sQLiteDatabase.rawQuery(str, null);
        }
        List<T> listNewInstanceEntity = cursorRawQuery.getCount() > 0 ? newInstanceEntity(cls, cursorRawQuery) : null;
        closeCursor(cursorRawQuery);
        return listNewInstanceEntity;
    }

    private <T extends AbsDbWrapper> List<T> exeRelationSql(SQLiteDatabase sQLiteDatabase, Class<T> cls, int i2, int i3, String... strArr) throws ClassNotFoundException, SecurityException, SQLException {
        String string;
        boolean z2;
        Cursor cursorRawQuery;
        List<T> listNewInstanceEntity;
        String str = "p";
        SQLiteDatabase sQLiteDatabaseCheckDb = checkDb(sQLiteDatabase);
        if (!SqlUtil.isWrapper(cls)) {
            ALog.e("AbsDelegate", "查询数据失败，实体类没有使用@Wrapper 注解");
            return null;
        }
        Field[] oneAndManyField = getOneAndManyField(cls);
        if (oneAndManyField == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        Field field = oneAndManyField[0];
        Field field2 = oneAndManyField[1];
        try {
            Many many = (Many) field2.getAnnotation(Many.class);
            Class<?> cls2 = Class.forName(field.getType().getName());
            Class<?> cls3 = Class.forName(CommonUtil.getListParamType(field2).getName());
            SqlUtil.checkOrCreateTable(sQLiteDatabaseCheckDb, cls2);
            SqlUtil.checkOrCreateTable(sQLiteDatabaseCheckDb, cls3);
            String simpleName = cls2.getSimpleName();
            String simpleName2 = cls3.getSimpleName();
            List<Field> allNotIgnoreField = SqlUtil.getAllNotIgnoreField(cls2);
            List<Field> allNotIgnoreField2 = SqlUtil.getAllNotIgnoreField(cls3);
            StringBuilder sb2 = new StringBuilder();
            StringBuilder sb3 = new StringBuilder();
            if (allNotIgnoreField != null) {
                try {
                    sb2.append(simpleName.concat(".rowid AS ").concat("p").concat("rowid,"));
                    Iterator<Field> it = allNotIgnoreField.iterator();
                    while (it.hasNext()) {
                        Field next = it.next();
                        Iterator<Field> it2 = it;
                        String strConcat = str.concat(next.getName());
                        sb2.append(simpleName.concat(StrPool.DOT).concat(next.getName()));
                        sb2.append(" AS ");
                        sb2.append(strConcat);
                        sb2.append(",");
                        it = it2;
                        str = str;
                    }
                } catch (ClassNotFoundException e2) {
                    e = e2;
                    e.printStackTrace();
                    return null;
                }
            }
            if (allNotIgnoreField2 != null) {
                sb2.append(simpleName2.concat(".rowid AS ").concat(am.aF).concat("rowid,"));
                for (Field field3 : allNotIgnoreField2) {
                    String strConcat2 = am.aF.concat(field3.getName());
                    sb3.append(simpleName2.concat(StrPool.DOT).concat(field3.getName()));
                    sb3.append(" AS ");
                    sb3.append(strConcat2);
                    sb3.append(",");
                }
            }
            String string2 = sb2.toString();
            String string3 = sb3.toString();
            if (!TextUtils.isEmpty(string2)) {
                string2 = string2.substring(0, string2.length() - 1);
            }
            if (!TextUtils.isEmpty(string3)) {
                string3 = string3.substring(0, string3.length() - 1);
            }
            sb.append("SELECT ");
            if (!TextUtils.isEmpty(string2)) {
                sb.append(string2);
                sb.append(",");
            }
            if (!TextUtils.isEmpty(string3)) {
                sb.append(string3);
            }
            if (TextUtils.isEmpty(string2) && TextUtils.isEmpty(string3)) {
                sb.append(" * ");
            }
            sb.append(" FROM ");
            sb.append(simpleName);
            sb.append(" INNER JOIN ");
            sb.append(simpleName2);
            sb.append(" ON ");
            sb.append(simpleName.concat(StrPool.DOT).concat(many.parentColumn()));
            sb.append(" = ");
            sb.append(simpleName2.concat(StrPool.DOT).concat(many.entityColumn()));
            if (strArr == null || strArr.length <= 0) {
                string = sb.toString();
            } else {
                if (!CommonUtil.checkSqlExpression(strArr)) {
                    return null;
                }
                sb.append(" WHERE ");
                sb.append(strArr[0]);
                sb.append(" ");
                String strReplace = sb.toString().replace("?", "%s");
                int i4 = 1;
                int length = strArr.length - 1;
                String[] strArr2 = new String[length];
                int i5 = 0;
                while (i5 < length) {
                    Object[] objArr = new Object[i4];
                    int i6 = i5 + 1;
                    objArr[0] = SqlUtil.encodeStr(strArr[i6]);
                    strArr2[i5] = String.format("'%s'", objArr);
                    i5 = i6;
                    i4 = 1;
                }
                string = String.format(strReplace, strArr2);
            }
            if (i2 == -1 || i3 == -1) {
                z2 = false;
            } else {
                string = string.concat(String.format(" Group by %s LIMIT %s,%s", simpleName.concat(StrPool.DOT).concat(many.parentColumn()), Integer.valueOf((i2 - 1) * i3), Integer.valueOf(i3)));
                z2 = true;
            }
            cursorRawQuery = sQLiteDatabaseCheckDb.rawQuery(string, null);
            listNewInstanceEntity = newInstanceEntity(cls, cls2, cls3, cursorRawQuery, allNotIgnoreField, allNotIgnoreField2, z2, sQLiteDatabaseCheckDb, many.entityColumn(), many.parentColumn());
        } catch (ClassNotFoundException e3) {
            e = e3;
        }
        try {
            closeCursor(cursorRawQuery);
            return listNewInstanceEntity;
        } catch (ClassNotFoundException e4) {
            e = e4;
            e.printStackTrace();
            return null;
        }
    }

    private Field[] getOneAndManyField(Class cls) {
        Field[] fieldArr = new Field[2];
        Field field = null;
        Field field2 = null;
        boolean z2 = false;
        boolean z3 = false;
        for (Field field3 : cls.getDeclaredFields()) {
            if (SqlUtil.isOne(field3)) {
                if (z2) {
                    ALog.w("AbsDelegate", "查询数据失败，实体中有多个@One 注解");
                    return null;
                }
                z2 = true;
                field = field3;
            }
            if (SqlUtil.isMany(field3)) {
                if (z3) {
                    ALog.w("AbsDelegate", "查询数据失败，实体中有多个@Many 注解");
                    return null;
                }
                if (!field3.getType().isAssignableFrom(List.class)) {
                    ALog.w("AbsDelegate", "查询数据失败，@Many 注解的类型不是List");
                    return null;
                }
                z3 = true;
                field2 = field3;
            }
        }
        if (field == null || field2 == null) {
            ALog.w("AbsDelegate", "查询数据失败，实体中没有@One或@Many注解");
            return null;
        }
        if (field2.getType() != List.class) {
            ALog.w("AbsDelegate", "查询数据失败，@Many注解的字段必须是List");
            return null;
        }
        fieldArr[0] = field;
        fieldArr[1] = field2;
        return fieldArr;
    }

    private synchronized <T extends AbsDbWrapper, P extends DbEntity, C extends DbEntity> List<T> newInstanceEntity(Class<T> cls, Class<P> cls2, Class<C> cls3, Cursor cursor, List<Field> list, List<Field> list2, boolean z2, SQLiteDatabase sQLiteDatabase, String str, String str2) {
        ArrayList arrayList = new ArrayList();
        SparseArray sparseArray = new SparseArray();
        SparseArray sparseArray2 = new SparseArray();
        while (cursor.moveToNext()) {
            try {
                int i2 = cursor.getInt(cursor.getColumnIndex("p".concat("rowid")));
                if (sparseArray.get(i2) == null) {
                    sparseArray.put(i2, new ArrayList());
                    sparseArray2.put(i2, createParent(i2, cls2, list, cursor));
                }
                if (z2) {
                    List listCreateChildren = createChildren(sQLiteDatabase, cls3, list, str, str2, (DbEntity) sparseArray2.get(i2));
                    if (listCreateChildren != null) {
                        ((List) sparseArray.get(i2)).addAll(listCreateChildren);
                    }
                } else {
                    ((List) sparseArray.get(i2)).add(createChild(cls3, list2, cursor));
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        List<Field> allNotIgnoreField = SqlUtil.getAllNotIgnoreField(cls);
        if (allNotIgnoreField != null && !allNotIgnoreField.isEmpty()) {
            for (int i3 = 0; i3 < sparseArray2.size(); i3++) {
                int iKeyAt = sparseArray2.keyAt(i3);
                T tNewInstance = cls.newInstance();
                boolean z3 = false;
                boolean z4 = false;
                for (Field field : allNotIgnoreField) {
                    if (!z3 && field.getAnnotation(One.class) != null) {
                        field.set(tNewInstance, sparseArray2.get(iKeyAt));
                        z3 = true;
                    }
                    if (!z4 && field.getAnnotation(Many.class) != null) {
                        field.set(tNewInstance, sparseArray.get(iKeyAt));
                        z4 = true;
                    }
                }
                tNewInstance.handleConvert();
                arrayList.add(tNewInstance);
            }
            return arrayList;
        }
        return null;
    }

    private void setFieldValue(Class cls, Field field, int i2, Cursor cursor, DbEntity dbEntity) throws IllegalAccessException, IllegalArgumentException {
        if (cursor == null || cursor.isClosed()) {
            ALog.e("AbsDelegate", "cursor没有初始化");
            return;
        }
        if (cls == String.class) {
            String string = cursor.getString(i2);
            if (TextUtils.isEmpty(string)) {
                return;
            }
            field.set(dbEntity, URLDecoder.decode(string));
            return;
        }
        if (cls == Integer.TYPE || cls == Integer.class) {
            field.setInt(dbEntity, cursor.getInt(i2));
            return;
        }
        if (cls == Float.TYPE || cls == Float.class) {
            field.setFloat(dbEntity, cursor.getFloat(i2));
            return;
        }
        if (cls == Double.TYPE || cls == Double.class) {
            field.setDouble(dbEntity, cursor.getDouble(i2));
            return;
        }
        if (cls == Long.TYPE || cls == Long.class) {
            field.setLong(dbEntity, cursor.getLong(i2));
            return;
        }
        if (cls == Boolean.TYPE || cls == Boolean.class) {
            if (TextUtils.isEmpty(cursor.getString(i2))) {
                field.setBoolean(dbEntity, false);
                return;
            } else {
                field.setBoolean(dbEntity, !r2.equalsIgnoreCase(k.a.f27524v));
                return;
            }
        }
        if (cls == Date.class || cls == java.sql.Date.class) {
            field.set(dbEntity, new Date(URLDecoder.decode(cursor.getString(i2))));
            return;
        }
        if (cls == byte[].class) {
            field.set(dbEntity, cursor.getBlob(i2));
            return;
        }
        if (cls == Map.class) {
            String string2 = cursor.getString(i2);
            if (TextUtils.isEmpty(string2)) {
                return;
            }
            field.set(dbEntity, SqlUtil.str2Map(URLDecoder.decode(string2)));
            return;
        }
        if (cls == List.class) {
            String string3 = cursor.getString(i2);
            if (TextUtils.isEmpty(string3)) {
                return;
            }
            field.set(dbEntity, SqlUtil.str2List(URLDecoder.decode(string3), field));
        }
    }

    public <T extends DbEntity> List<T> findAllData(SQLiteDatabase sQLiteDatabase, Class<T> cls) {
        return exeNormalDataSql(checkDb(sQLiteDatabase), cls, String.format("SELECT rowid, * FROM %s", CommonUtil.getClassName((Class) cls)), null);
    }

    public <T extends DbEntity> List<T> findData(SQLiteDatabase sQLiteDatabase, Class<T> cls, String... strArr) {
        SQLiteDatabase sQLiteDatabaseCheckDb = checkDb(sQLiteDatabase);
        if (!CommonUtil.checkSqlExpression(strArr)) {
            return null;
        }
        String str = String.format("SELECT rowid, * FROM %s WHERE %s", CommonUtil.getClassName((Class) cls), strArr[0]);
        int length = strArr.length - 1;
        String[] strArr2 = new String[length];
        try {
            System.arraycopy(strArr, 1, strArr2, 0, length);
            return exeNormalDataSql(sQLiteDatabaseCheckDb, cls, str, strArr2);
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public <T extends DbEntity> List<T> findDataByFuzzy(SQLiteDatabase sQLiteDatabase, Class<T> cls, String str) {
        SQLiteDatabase sQLiteDatabaseCheckDb = checkDb(sQLiteDatabase);
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("sql语句表达式不能为null或\"\"");
        }
        if (str.toUpperCase().contains("LIKE")) {
            return exeNormalDataSql(sQLiteDatabaseCheckDb, cls, String.format("SELECT rowid, * FROM %s, WHERE %s", CommonUtil.getClassName((Class) cls), str), null);
        }
        throw new IllegalArgumentException("sql语句表达式未包含LIEK");
    }

    public <T extends AbsDbWrapper> List<T> findRelationData(SQLiteDatabase sQLiteDatabase, Class<T> cls, String... strArr) {
        return exeRelationSql(sQLiteDatabase, cls, 1, Integer.MAX_VALUE, strArr);
    }

    public int[] getRowId(SQLiteDatabase sQLiteDatabase, Class cls) {
        Cursor cursorRawQuery = checkDb(sQLiteDatabase).rawQuery("SELECT rowid, * FROM " + CommonUtil.getClassName(cls), null);
        int[] iArr = new int[cursorRawQuery.getCount()];
        int i2 = 0;
        while (cursorRawQuery.moveToNext()) {
            iArr[i2] = cursorRawQuery.getInt(cursorRawQuery.getColumnIndex("rowid"));
            i2++;
        }
        cursorRawQuery.close();
        return iArr;
    }

    public <T extends DbEntity> boolean itemExist(SQLiteDatabase sQLiteDatabase, Class<T> cls, long j2) {
        return itemExist(sQLiteDatabase, CommonUtil.getClassName((Class) cls), j2);
    }

    public <T extends AbsDbWrapper> List<T> findRelationData(SQLiteDatabase sQLiteDatabase, Class<T> cls, int i2, int i3, String... strArr) {
        if (i2 >= 1 && i3 >= 1) {
            return exeRelationSql(sQLiteDatabase, cls, i2, i3, strArr);
        }
        ALog.w("AbsDelegate", "page，num 小于1");
        return null;
    }

    public boolean itemExist(SQLiteDatabase sQLiteDatabase, String str, long j2) {
        Cursor cursorRawQuery = checkDb(sQLiteDatabase).rawQuery("SELECT rowid FROM " + str + " WHERE rowid=" + j2, null);
        boolean z2 = cursorRawQuery.getCount() > 0;
        cursorRawQuery.close();
        return z2;
    }

    public int getRowId(SQLiteDatabase sQLiteDatabase, Class cls, Object[] objArr, Object[] objArr2) {
        SQLiteDatabase sQLiteDatabaseCheckDb = checkDb(sQLiteDatabase);
        if (objArr.length > 0 && objArr2.length > 0) {
            if (objArr.length != objArr2.length) {
                ALog.e("AbsDelegate", "groupHash 和 vaule 长度不相等");
                return -1;
            }
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT rowid FROM ");
            sb.append(CommonUtil.getClassName(cls));
            sb.append(" WHERE ");
            int i2 = 0;
            for (Object obj : objArr) {
                sb.append(obj);
                sb.append("=");
                sb.append("'");
                sb.append(objArr2[i2]);
                sb.append("'");
                sb.append(i2 >= objArr.length + (-1) ? "" : ",");
                i2++;
            }
            Cursor cursorRawQuery = sQLiteDatabaseCheckDb.rawQuery(sb.toString(), null);
            int columnIndex = cursorRawQuery.getColumnIndex("rowid");
            cursorRawQuery.close();
            return columnIndex;
        }
        ALog.e("AbsDelegate", "请输入删除条件");
        return -1;
    }

    public <T extends DbEntity> List<T> findData(SQLiteDatabase sQLiteDatabase, Class<T> cls, int i2, int i3, String... strArr) {
        if (i2 >= 1 && i3 >= 1) {
            SQLiteDatabase sQLiteDatabaseCheckDb = checkDb(sQLiteDatabase);
            if (!CommonUtil.checkSqlExpression(strArr)) {
                return null;
            }
            String str = String.format("SELECT rowid, * FROM %s WHERE %s LIMIT %s,%s", CommonUtil.getClassName((Class) cls), strArr[0], Integer.valueOf((i2 - 1) * i3), Integer.valueOf(i3));
            int length = strArr.length - 1;
            String[] strArr2 = new String[length];
            try {
                System.arraycopy(strArr, 1, strArr2, 0, length);
                return exeNormalDataSql(sQLiteDatabaseCheckDb, cls, str, strArr2);
            } catch (Exception e2) {
                e2.printStackTrace();
                return null;
            }
        }
        ALog.w("AbsDelegate", "page, bum 小于1");
        return null;
    }

    public <T extends DbEntity> List<T> findDataByFuzzy(SQLiteDatabase sQLiteDatabase, Class<T> cls, int i2, int i3, String str) {
        if (i2 >= 1 && i3 >= 1) {
            SQLiteDatabase sQLiteDatabaseCheckDb = checkDb(sQLiteDatabase);
            if (!TextUtils.isEmpty(str)) {
                if (str.toUpperCase().contains("LIKE")) {
                    return exeNormalDataSql(sQLiteDatabaseCheckDb, cls, String.format("SELECT rowid, * FROM %s WHERE %s LIMIT %s,%s", CommonUtil.getClassName((Class) cls), str, Integer.valueOf((i2 - 1) * i3), Integer.valueOf(i3)), null);
                }
                throw new IllegalArgumentException("sql语句表达式未包含LIEK");
            }
            throw new IllegalArgumentException("sql语句表达式不能为null或\"\"");
        }
        ALog.w("AbsDelegate", "page, bum 小于1");
        return null;
    }

    private synchronized <T extends DbEntity> List<T> newInstanceEntity(Class<T> cls, Cursor cursor) {
        ArrayList arrayList;
        List<Field> allFields = CommonUtil.getAllFields(cls);
        arrayList = new ArrayList();
        if (allFields != null && allFields.size() > 0) {
            while (cursor.moveToNext()) {
                try {
                    T tNewInstance = cls.newInstance();
                    String name = "";
                    for (Field field : allFields) {
                        field.setAccessible(true);
                        if (!SqlUtil.isIgnore(field)) {
                            Class<?> type = field.getType();
                            if (SqlUtil.isPrimary(field) && (type == Integer.TYPE || type == Integer.class)) {
                                name = field.getName();
                            }
                            String str = name;
                            int columnIndex = cursor.getColumnIndex(field.getName());
                            if (columnIndex != -1) {
                                setFieldValue(type, field, columnIndex, cursor, tNewInstance);
                            }
                            name = str;
                        }
                    }
                    if (TextUtils.isEmpty(name)) {
                        name = "rowid";
                    }
                    tNewInstance.rowID = cursor.getInt(cursor.getColumnIndex(name));
                    arrayList.add(tNewInstance);
                } catch (IllegalAccessException e2) {
                    e2.printStackTrace();
                } catch (InstantiationException e3) {
                    e3.printStackTrace();
                }
            }
            closeCursor(cursor);
        }
        return arrayList;
    }
}
