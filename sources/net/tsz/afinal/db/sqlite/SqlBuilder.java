package net.tsz.afinal.db.sqlite;

import android.text.TextUtils;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import net.tsz.afinal.db.table.Id;
import net.tsz.afinal.db.table.KeyValue;
import net.tsz.afinal.db.table.ManyToOne;
import net.tsz.afinal.db.table.Property;
import net.tsz.afinal.db.table.TableInfo;
import net.tsz.afinal.exception.DbException;

/* loaded from: classes9.dex */
public class SqlBuilder {
    public static SqlInfo buildDeleteSql(Object obj) {
        TableInfo tableInfo = TableInfo.get(obj.getClass());
        Id id = tableInfo.getId();
        Object value = id.getValue(obj);
        if (value == null) {
            throw new DbException("getDeleteSQL:" + obj.getClass() + " id value is null");
        }
        StringBuffer stringBuffer = new StringBuffer(getDeleteSqlBytableName(tableInfo.getTableName()));
        stringBuffer.append(" WHERE ");
        stringBuffer.append(id.getColumn());
        stringBuffer.append("=?");
        SqlInfo sqlInfo = new SqlInfo();
        sqlInfo.setSql(stringBuffer.toString());
        sqlInfo.addValue(value);
        return sqlInfo;
    }

    public static SqlInfo buildInsertSql(Object obj) {
        List<KeyValue> saveKeyValueListByEntity = getSaveKeyValueListByEntity(obj);
        StringBuffer stringBuffer = new StringBuffer();
        if (saveKeyValueListByEntity == null || saveKeyValueListByEntity.size() <= 0) {
            return null;
        }
        SqlInfo sqlInfo = new SqlInfo();
        stringBuffer.append("INSERT INTO ");
        stringBuffer.append(TableInfo.get(obj.getClass()).getTableName());
        stringBuffer.append(" (");
        for (KeyValue keyValue : saveKeyValueListByEntity) {
            stringBuffer.append(keyValue.getKey());
            stringBuffer.append(",");
            sqlInfo.addValue(keyValue.getValue());
        }
        stringBuffer.deleteCharAt(stringBuffer.length() - 1);
        stringBuffer.append(") VALUES ( ");
        int size = saveKeyValueListByEntity.size();
        for (int i2 = 0; i2 < size; i2++) {
            stringBuffer.append("?,");
        }
        stringBuffer.deleteCharAt(stringBuffer.length() - 1);
        stringBuffer.append(")");
        sqlInfo.setSql(stringBuffer.toString());
        return sqlInfo;
    }

    public static String getCreatTableSQL(Class<?> cls) {
        TableInfo tableInfo = TableInfo.get(cls);
        Id id = tableInfo.getId();
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("CREATE TABLE IF NOT EXISTS ");
        stringBuffer.append(tableInfo.getTableName());
        stringBuffer.append(" ( ");
        Class<?> dataType = id.getDataType();
        if (dataType == Integer.TYPE || dataType == Integer.class) {
            stringBuffer.append("\"");
            stringBuffer.append(id.getColumn());
            stringBuffer.append("\"    ");
            stringBuffer.append("INTEGER PRIMARY KEY AUTOINCREMENT,");
        } else {
            stringBuffer.append("\"");
            stringBuffer.append(id.getColumn());
            stringBuffer.append("\"    ");
            stringBuffer.append("TEXT PRIMARY KEY,");
        }
        for (Property property : tableInfo.propertyMap.values()) {
            stringBuffer.append("\"");
            stringBuffer.append(property.getColumn());
            stringBuffer.append("\",");
        }
        for (ManyToOne manyToOne : tableInfo.manyToOneMap.values()) {
            stringBuffer.append("\"");
            stringBuffer.append(manyToOne.getColumn());
            stringBuffer.append("\",");
        }
        stringBuffer.deleteCharAt(stringBuffer.length() - 1);
        stringBuffer.append(" )");
        return stringBuffer.toString();
    }

    private static String getDeleteSqlBytableName(String str) {
        return "DELETE FROM " + str;
    }

    private static String getPropertyStrSql(String str, Object obj) {
        StringBuffer stringBuffer = new StringBuffer(str);
        stringBuffer.append("=");
        if ((obj instanceof String) || (obj instanceof Date) || (obj instanceof java.sql.Date)) {
            stringBuffer.append("'");
            stringBuffer.append(obj);
            stringBuffer.append("'");
        } else {
            stringBuffer.append(obj);
        }
        return stringBuffer.toString();
    }

    public static List<KeyValue> getSaveKeyValueListByEntity(Object obj) {
        ArrayList arrayList = new ArrayList();
        TableInfo tableInfo = TableInfo.get(obj.getClass());
        Object value = tableInfo.getId().getValue(obj);
        if (!(value instanceof Integer) && (value instanceof String) && value != null) {
            arrayList.add(new KeyValue(tableInfo.getId().getColumn(), value));
        }
        Iterator<Property> it = tableInfo.propertyMap.values().iterator();
        while (it.hasNext()) {
            KeyValue keyValueProperty2KeyValue = property2KeyValue(it.next(), obj);
            if (keyValueProperty2KeyValue != null) {
                arrayList.add(keyValueProperty2KeyValue);
            }
        }
        Iterator<ManyToOne> it2 = tableInfo.manyToOneMap.values().iterator();
        while (it2.hasNext()) {
            KeyValue keyValueManyToOne2KeyValue = manyToOne2KeyValue(it2.next(), obj);
            if (keyValueManyToOne2KeyValue != null) {
                arrayList.add(keyValueManyToOne2KeyValue);
            }
        }
        return arrayList;
    }

    public static String getSelectSQL(Class<?> cls, Object obj) {
        TableInfo tableInfo = TableInfo.get(cls);
        StringBuffer stringBuffer = new StringBuffer(getSelectSqlByTableName(tableInfo.getTableName()));
        stringBuffer.append(" WHERE ");
        stringBuffer.append(getPropertyStrSql(tableInfo.getId().getColumn(), obj));
        return stringBuffer.toString();
    }

    public static String getSelectSQLByWhere(Class<?> cls, String str) {
        StringBuffer stringBuffer = new StringBuffer(getSelectSqlByTableName(TableInfo.get(cls).getTableName()));
        if (!TextUtils.isEmpty(str)) {
            stringBuffer.append(" WHERE ");
            stringBuffer.append(str);
        }
        return stringBuffer.toString();
    }

    public static SqlInfo getSelectSqlAsSqlInfo(Class<?> cls, Object obj) {
        TableInfo tableInfo = TableInfo.get(cls);
        StringBuffer stringBuffer = new StringBuffer(getSelectSqlByTableName(tableInfo.getTableName()));
        stringBuffer.append(" WHERE ");
        stringBuffer.append(tableInfo.getId().getColumn());
        stringBuffer.append("=?");
        SqlInfo sqlInfo = new SqlInfo();
        sqlInfo.setSql(stringBuffer.toString());
        sqlInfo.addValue(obj);
        return sqlInfo;
    }

    private static String getSelectSqlByTableName(String str) {
        StringBuffer stringBuffer = new StringBuffer("SELECT * FROM ");
        stringBuffer.append(str);
        return stringBuffer.toString();
    }

    public static SqlInfo getUpdateSqlAsSqlInfo(Object obj) {
        TableInfo tableInfo = TableInfo.get(obj.getClass());
        Object value = tableInfo.getId().getValue(obj);
        if (value == null) {
            throw new DbException("this entity[" + obj.getClass() + "]'s id value is null");
        }
        ArrayList<KeyValue> arrayList = new ArrayList();
        Iterator<Property> it = tableInfo.propertyMap.values().iterator();
        while (it.hasNext()) {
            KeyValue keyValueProperty2KeyValue = property2KeyValue(it.next(), obj);
            if (keyValueProperty2KeyValue != null) {
                arrayList.add(keyValueProperty2KeyValue);
            }
        }
        Iterator<ManyToOne> it2 = tableInfo.manyToOneMap.values().iterator();
        while (it2.hasNext()) {
            KeyValue keyValueManyToOne2KeyValue = manyToOne2KeyValue(it2.next(), obj);
            if (keyValueManyToOne2KeyValue != null) {
                arrayList.add(keyValueManyToOne2KeyValue);
            }
        }
        if (arrayList.size() == 0) {
            return null;
        }
        SqlInfo sqlInfo = new SqlInfo();
        StringBuffer stringBuffer = new StringBuffer("UPDATE ");
        stringBuffer.append(tableInfo.getTableName());
        stringBuffer.append(" SET ");
        for (KeyValue keyValue : arrayList) {
            stringBuffer.append(keyValue.getKey());
            stringBuffer.append("=?,");
            sqlInfo.addValue(keyValue.getValue());
        }
        stringBuffer.deleteCharAt(stringBuffer.length() - 1);
        stringBuffer.append(" WHERE ");
        stringBuffer.append(tableInfo.getId().getColumn());
        stringBuffer.append("=?");
        sqlInfo.addValue(value);
        sqlInfo.setSql(stringBuffer.toString());
        return sqlInfo;
    }

    private static KeyValue manyToOne2KeyValue(ManyToOne manyToOne, Object obj) {
        String column = manyToOne.getColumn();
        Object value = manyToOne.getValue(obj);
        if (value != null) {
            Object value2 = value.getClass() == ManyToOneLazyLoader.class ? TableInfo.get(manyToOne.getManyClass()).getId().getValue(value) : TableInfo.get(value.getClass()).getId().getValue(value);
            if (column != null && value2 != null) {
                return new KeyValue(column, value2);
            }
        }
        return null;
    }

    private static KeyValue property2KeyValue(Property property, Object obj) {
        String column = property.getColumn();
        Object value = property.getValue(obj);
        if (value != null) {
            return new KeyValue(column, value);
        }
        if (property.getDefaultValue() == null || property.getDefaultValue().trim().length() == 0) {
            return null;
        }
        return new KeyValue(column, property.getDefaultValue());
    }

    public static String getSelectSQL(Class<?> cls) {
        return getSelectSqlByTableName(TableInfo.get(cls).getTableName());
    }

    public static SqlInfo buildDeleteSql(Class<?> cls, Object obj) {
        TableInfo tableInfo = TableInfo.get(cls);
        Id id = tableInfo.getId();
        if (obj != null) {
            StringBuffer stringBuffer = new StringBuffer(getDeleteSqlBytableName(tableInfo.getTableName()));
            stringBuffer.append(" WHERE ");
            stringBuffer.append(id.getColumn());
            stringBuffer.append("=?");
            SqlInfo sqlInfo = new SqlInfo();
            sqlInfo.setSql(stringBuffer.toString());
            sqlInfo.addValue(obj);
            return sqlInfo;
        }
        throw new DbException("getDeleteSQL:idValue is null");
    }

    public static String buildDeleteSql(Class<?> cls, String str) {
        StringBuffer stringBuffer = new StringBuffer(getDeleteSqlBytableName(TableInfo.get(cls).getTableName()));
        if (!TextUtils.isEmpty(str)) {
            stringBuffer.append(" WHERE ");
            stringBuffer.append(str);
        }
        return stringBuffer.toString();
    }

    public static SqlInfo getUpdateSqlAsSqlInfo(Object obj, String str) {
        TableInfo tableInfo = TableInfo.get(obj.getClass());
        ArrayList<KeyValue> arrayList = new ArrayList();
        Iterator<Property> it = tableInfo.propertyMap.values().iterator();
        while (it.hasNext()) {
            KeyValue keyValueProperty2KeyValue = property2KeyValue(it.next(), obj);
            if (keyValueProperty2KeyValue != null) {
                arrayList.add(keyValueProperty2KeyValue);
            }
        }
        Iterator<ManyToOne> it2 = tableInfo.manyToOneMap.values().iterator();
        while (it2.hasNext()) {
            KeyValue keyValueManyToOne2KeyValue = manyToOne2KeyValue(it2.next(), obj);
            if (keyValueManyToOne2KeyValue != null) {
                arrayList.add(keyValueManyToOne2KeyValue);
            }
        }
        if (arrayList.size() != 0) {
            SqlInfo sqlInfo = new SqlInfo();
            StringBuffer stringBuffer = new StringBuffer("UPDATE ");
            stringBuffer.append(tableInfo.getTableName());
            stringBuffer.append(" SET ");
            for (KeyValue keyValue : arrayList) {
                stringBuffer.append(keyValue.getKey());
                stringBuffer.append("=?,");
                sqlInfo.addValue(keyValue.getValue());
            }
            stringBuffer.deleteCharAt(stringBuffer.length() - 1);
            if (!TextUtils.isEmpty(str)) {
                stringBuffer.append(" WHERE ");
                stringBuffer.append(str);
            }
            sqlInfo.setSql(stringBuffer.toString());
            return sqlInfo;
        }
        throw new DbException("this entity[" + obj.getClass() + "] has no property");
    }
}
