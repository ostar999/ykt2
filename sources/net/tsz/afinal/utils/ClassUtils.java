package net.tsz.afinal.utils;

import com.umeng.analytics.pro.aq;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import net.tsz.afinal.annotation.sqlite.Id;
import net.tsz.afinal.annotation.sqlite.Table;
import net.tsz.afinal.db.sqlite.ManyToOneLazyLoader;
import net.tsz.afinal.db.table.ManyToOne;
import net.tsz.afinal.db.table.OneToMany;
import net.tsz.afinal.db.table.Property;
import net.tsz.afinal.exception.DbException;

/* loaded from: classes9.dex */
public class ClassUtils {
    public static List<ManyToOne> getManyToOneList(Class<?> cls) {
        ArrayList arrayList = new ArrayList();
        try {
            for (Field field : cls.getDeclaredFields()) {
                if (!FieldUtils.isTransient(field) && FieldUtils.isManyToOne(field)) {
                    ManyToOne manyToOne = new ManyToOne();
                    if (field.getType() == ManyToOneLazyLoader.class) {
                        Class<?> cls2 = (Class) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[1];
                        if (cls2 != null) {
                            manyToOne.setManyClass(cls2);
                        }
                    } else {
                        manyToOne.setManyClass(field.getType());
                    }
                    manyToOne.setColumn(FieldUtils.getColumnByField(field));
                    manyToOne.setFieldName(field.getName());
                    manyToOne.setDataType(field.getType());
                    manyToOne.setSet(FieldUtils.getFieldSetMethod(cls, field));
                    manyToOne.setGet(FieldUtils.getFieldGetMethod(cls, field));
                    arrayList.add(manyToOne);
                }
            }
            return arrayList;
        } catch (Exception e2) {
            throw new RuntimeException(e2.getMessage(), e2);
        }
    }

    public static List<OneToMany> getOneToManyList(Class<?> cls) {
        ArrayList arrayList = new ArrayList();
        try {
            for (Field field : cls.getDeclaredFields()) {
                if (!FieldUtils.isTransient(field) && FieldUtils.isOneToMany(field)) {
                    OneToMany oneToMany = new OneToMany();
                    oneToMany.setColumn(FieldUtils.getColumnByField(field));
                    oneToMany.setFieldName(field.getName());
                    if (!(field.getGenericType() instanceof ParameterizedType)) {
                        throw new DbException("getOneToManyList Exception:" + field.getName() + "'s type is null");
                    }
                    ParameterizedType parameterizedType = (ParameterizedType) field.getGenericType();
                    if (parameterizedType.getActualTypeArguments().length == 1) {
                        Class<?> cls2 = (Class) parameterizedType.getActualTypeArguments()[0];
                        if (cls2 != null) {
                            oneToMany.setOneClass(cls2);
                        }
                    } else {
                        Class<?> cls3 = (Class) parameterizedType.getActualTypeArguments()[1];
                        if (cls3 != null) {
                            oneToMany.setOneClass(cls3);
                        }
                    }
                    oneToMany.setDataType(field.getType());
                    oneToMany.setSet(FieldUtils.getFieldSetMethod(cls, field));
                    oneToMany.setGet(FieldUtils.getFieldGetMethod(cls, field));
                    arrayList.add(oneToMany);
                }
            }
            return arrayList;
        } catch (Exception e2) {
            throw new RuntimeException(e2.getMessage(), e2);
        }
    }

    public static String getPrimaryKeyColumn(Class<?> cls) {
        Field field;
        Field[] declaredFields = cls.getDeclaredFields();
        if (declaredFields == null) {
            throw new RuntimeException("this model[" + cls + "] has no field");
        }
        int length = declaredFields.length;
        int i2 = 0;
        Id id = null;
        while (true) {
            if (i2 >= length) {
                field = null;
                break;
            }
            Field field2 = declaredFields[i2];
            Id id2 = (Id) field2.getAnnotation(Id.class);
            if (id2 != null) {
                field = field2;
                id = id2;
                break;
            }
            i2++;
            id = id2;
        }
        if (id != null) {
            String strColumn = id.column();
            return (strColumn == null || strColumn.trim().length() == 0) ? field.getName() : strColumn;
        }
        for (Field field3 : declaredFields) {
            if (aq.f22519d.equals(field3.getName())) {
                return aq.f22519d;
            }
        }
        for (Field field4 : declaredFields) {
            if ("id".equals(field4.getName())) {
                return "id";
            }
        }
        return null;
    }

    public static Field getPrimaryKeyField(Class<?> cls) {
        Field field;
        Field[] declaredFields = cls.getDeclaredFields();
        if (declaredFields == null) {
            throw new RuntimeException("this model[" + cls + "] has no field");
        }
        int length = declaredFields.length;
        int i2 = 0;
        while (true) {
            if (i2 >= length) {
                field = null;
                break;
            }
            field = declaredFields[i2];
            if (field.getAnnotation(Id.class) != null) {
                break;
            }
            i2++;
        }
        if (field == null) {
            int length2 = declaredFields.length;
            int i3 = 0;
            while (true) {
                if (i3 >= length2) {
                    break;
                }
                Field field2 = declaredFields[i3];
                if (aq.f22519d.equals(field2.getName())) {
                    field = field2;
                    break;
                }
                i3++;
            }
        }
        if (field != null) {
            return field;
        }
        for (Field field3 : declaredFields) {
            if ("id".equals(field3.getName())) {
                return field3;
            }
        }
        return field;
    }

    public static String getPrimaryKeyFieldName(Class<?> cls) {
        Field primaryKeyField = getPrimaryKeyField(cls);
        if (primaryKeyField == null) {
            return null;
        }
        return primaryKeyField.getName();
    }

    public static Object getPrimaryKeyValue(Object obj) {
        return FieldUtils.getFieldValue(obj, getPrimaryKeyField(obj.getClass()));
    }

    public static List<Property> getPropertyList(Class<?> cls) {
        ArrayList arrayList = new ArrayList();
        try {
            Field[] declaredFields = cls.getDeclaredFields();
            String primaryKeyFieldName = getPrimaryKeyFieldName(cls);
            for (Field field : declaredFields) {
                if (!FieldUtils.isTransient(field) && FieldUtils.isBaseDateType(field) && !field.getName().equals(primaryKeyFieldName)) {
                    Property property = new Property();
                    property.setColumn(FieldUtils.getColumnByField(field));
                    property.setFieldName(field.getName());
                    property.setDataType(field.getType());
                    property.setDefaultValue(FieldUtils.getPropertyDefaultValue(field));
                    property.setSet(FieldUtils.getFieldSetMethod(cls, field));
                    property.setGet(FieldUtils.getFieldGetMethod(cls, field));
                    property.setField(field);
                    arrayList.add(property);
                }
            }
            return arrayList;
        } catch (Exception e2) {
            throw new RuntimeException(e2.getMessage(), e2);
        }
    }

    public static String getTableName(Class<?> cls) {
        Table table = (Table) cls.getAnnotation(Table.class);
        return (table == null || table.name().trim().length() == 0) ? cls.getName().replace('.', '_') : table.name();
    }
}
