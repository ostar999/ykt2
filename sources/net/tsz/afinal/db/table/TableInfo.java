package net.tsz.afinal.db.table;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import net.tsz.afinal.exception.DbException;
import net.tsz.afinal.utils.ClassUtils;
import net.tsz.afinal.utils.FieldUtils;

/* loaded from: classes9.dex */
public class TableInfo {
    private static final HashMap<String, TableInfo> tableInfoMap = new HashMap<>();
    private boolean checkDatabese;
    private String className;
    private Id id;
    private String tableName;
    public final HashMap<String, Property> propertyMap = new HashMap<>();
    public final HashMap<String, OneToMany> oneToManyMap = new HashMap<>();
    public final HashMap<String, ManyToOne> manyToOneMap = new HashMap<>();

    private TableInfo() {
    }

    public static TableInfo get(Class<?> cls) {
        if (cls == null) {
            throw new DbException("table info get error,because the clazz is null");
        }
        TableInfo tableInfo = tableInfoMap.get(cls.getName());
        if (tableInfo == null) {
            tableInfo = new TableInfo();
            tableInfo.setTableName(ClassUtils.getTableName(cls));
            tableInfo.setClassName(cls.getName());
            Field primaryKeyField = ClassUtils.getPrimaryKeyField(cls);
            if (primaryKeyField == null) {
                throw new DbException("the class[" + cls + "]'s idField is null , \n you can define _id,id property or use annotation @id to solution this exception");
            }
            Id id = new Id();
            id.setColumn(FieldUtils.getColumnByField(primaryKeyField));
            id.setFieldName(primaryKeyField.getName());
            id.setSet(FieldUtils.getFieldSetMethod(cls, primaryKeyField));
            id.setGet(FieldUtils.getFieldGetMethod(cls, primaryKeyField));
            id.setDataType(primaryKeyField.getType());
            tableInfo.setId(id);
            List<Property> propertyList = ClassUtils.getPropertyList(cls);
            if (propertyList != null) {
                for (Property property : propertyList) {
                    if (property != null) {
                        tableInfo.propertyMap.put(property.getColumn(), property);
                    }
                }
            }
            List<ManyToOne> manyToOneList = ClassUtils.getManyToOneList(cls);
            if (manyToOneList != null) {
                for (ManyToOne manyToOne : manyToOneList) {
                    if (manyToOne != null) {
                        tableInfo.manyToOneMap.put(manyToOne.getColumn(), manyToOne);
                    }
                }
            }
            List<OneToMany> oneToManyList = ClassUtils.getOneToManyList(cls);
            if (oneToManyList != null) {
                for (OneToMany oneToMany : oneToManyList) {
                    if (oneToMany != null) {
                        tableInfo.oneToManyMap.put(oneToMany.getColumn(), oneToMany);
                    }
                }
            }
            tableInfoMap.put(cls.getName(), tableInfo);
        }
        return tableInfo;
    }

    public String getClassName() {
        return this.className;
    }

    public Id getId() {
        return this.id;
    }

    public String getTableName() {
        return this.tableName;
    }

    public boolean isCheckDatabese() {
        return this.checkDatabese;
    }

    public void setCheckDatabese(boolean z2) {
        this.checkDatabese = z2;
    }

    public void setClassName(String str) {
        this.className = str;
    }

    public void setId(Id id) {
        this.id = id;
    }

    public void setTableName(String str) {
        this.tableName = str;
    }

    public static TableInfo get(String str) {
        try {
            return get(Class.forName(str));
        } catch (ClassNotFoundException e2) {
            e2.printStackTrace();
            return null;
        }
    }
}
