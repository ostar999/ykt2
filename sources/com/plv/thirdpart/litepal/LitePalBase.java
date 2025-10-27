package com.plv.thirdpart.litepal;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.plv.thirdpart.litepal.annotation.Column;
import com.plv.thirdpart.litepal.crud.LitePalSupport;
import com.plv.thirdpart.litepal.crud.model.AssociationsInfo;
import com.plv.thirdpart.litepal.exceptions.DatabaseGenerateException;
import com.plv.thirdpart.litepal.parser.LitePalAttr;
import com.plv.thirdpart.litepal.tablemanager.model.AssociationsModel;
import com.plv.thirdpart.litepal.tablemanager.model.ColumnModel;
import com.plv.thirdpart.litepal.tablemanager.model.GenericModel;
import com.plv.thirdpart.litepal.tablemanager.model.TableModel;
import com.plv.thirdpart.litepal.tablemanager.typechange.BlobOrm;
import com.plv.thirdpart.litepal.tablemanager.typechange.BooleanOrm;
import com.plv.thirdpart.litepal.tablemanager.typechange.DateOrm;
import com.plv.thirdpart.litepal.tablemanager.typechange.DecimalOrm;
import com.plv.thirdpart.litepal.tablemanager.typechange.NumericOrm;
import com.plv.thirdpart.litepal.tablemanager.typechange.OrmChange;
import com.plv.thirdpart.litepal.tablemanager.typechange.TextOrm;
import com.plv.thirdpart.litepal.util.BaseUtility;
import com.plv.thirdpart.litepal.util.DBUtility;
import com.umeng.analytics.pro.aq;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* loaded from: classes5.dex */
public abstract class LitePalBase {
    private static final int GET_ASSOCIATIONS_ACTION = 1;
    private static final int GET_ASSOCIATION_INFO_ACTION = 2;
    public static final String TAG = "LitePalBase";
    private Collection<AssociationsInfo> mAssociationInfos;
    private Collection<AssociationsModel> mAssociationModels;
    private Collection<GenericModel> mGenericModels;
    private OrmChange[] typeChangeRules = {new NumericOrm(), new TextOrm(), new BooleanOrm(), new DecimalOrm(), new DateOrm(), new BlobOrm()};
    private Map<String, List<Field>> classFieldsMap = new HashMap();
    private Map<String, List<Field>> classGenericFieldsMap = new HashMap();

    private void addIntoAssociationInfoCollection(String str, String str2, String str3, Field field, Field field2, int i2) {
        AssociationsInfo associationsInfo = new AssociationsInfo();
        associationsInfo.setSelfClassName(str);
        associationsInfo.setAssociatedClassName(str2);
        associationsInfo.setClassHoldsForeignKey(str3);
        associationsInfo.setAssociateOtherModelFromSelf(field);
        associationsInfo.setAssociateSelfFromOtherModel(field2);
        associationsInfo.setAssociationType(i2);
        this.mAssociationInfos.add(associationsInfo);
    }

    private void addIntoAssociationModelCollection(String str, String str2, String str3, int i2) {
        AssociationsModel associationsModel = new AssociationsModel();
        associationsModel.setTableName(DBUtility.getTableNameByClassName(str));
        associationsModel.setAssociatedTableName(DBUtility.getTableNameByClassName(str2));
        associationsModel.setTableHoldsForeignKey(DBUtility.getTableNameByClassName(str3));
        associationsModel.setAssociationType(i2);
        this.mAssociationModels.add(associationsModel);
    }

    private void analyzeClassFields(String str, int i2) {
        Column column;
        try {
            for (Field field : Class.forName(str).getDeclaredFields()) {
                if (isNonPrimitive(field) && ((column = (Column) field.getAnnotation(Column.class)) == null || !column.ignore())) {
                    oneToAnyConditions(str, field, i2);
                    manyToAnyConditions(str, field, i2);
                }
            }
        } catch (ClassNotFoundException e2) {
            e2.printStackTrace();
            throw new DatabaseGenerateException(DatabaseGenerateException.CLASS_NOT_FOUND + str);
        }
    }

    private ColumnModel convertFieldToColumnModel(Field field) {
        boolean zNullable;
        boolean zUnique;
        String strDefaultValue;
        boolean zIndex;
        String columnType = getColumnType(field.getType().getName());
        Column column = (Column) field.getAnnotation(Column.class);
        if (column != null) {
            zNullable = column.nullable();
            zUnique = column.unique();
            strDefaultValue = column.defaultValue();
            zIndex = column.index();
        } else {
            zNullable = true;
            zUnique = false;
            strDefaultValue = "";
            zIndex = false;
        }
        ColumnModel columnModel = new ColumnModel();
        columnModel.setColumnName(DBUtility.convertToValidColumnName(field.getName()));
        columnModel.setColumnType(columnType);
        columnModel.setNullable(zNullable);
        columnModel.setUnique(zUnique);
        columnModel.setDefaultValue(strDefaultValue);
        columnModel.setHasIndex(zIndex);
        return columnModel;
    }

    private boolean isNonPrimitive(Field field) {
        return !field.getType().isPrimitive();
    }

    private void manyToAnyConditions(String str, Field field, int i2) throws ClassNotFoundException {
        if (isCollection(field.getType())) {
            String genericTypeName = getGenericTypeName(field);
            if (!LitePalAttr.getInstance().getClassNames().contains(genericTypeName)) {
                if (BaseUtility.isGenericTypeSupported(genericTypeName) && i2 == 1) {
                    GenericModel genericModel = new GenericModel();
                    genericModel.setTableName(DBUtility.getGenericTableName(str, field.getName()));
                    genericModel.setValueColumnName(DBUtility.convertToValidColumnName(field.getName()));
                    genericModel.setValueColumnType(getColumnType(genericTypeName));
                    genericModel.setValueIdColumnName(DBUtility.getGenericValueIdColumnName(str));
                    this.mGenericModels.add(genericModel);
                    return;
                }
                return;
            }
            boolean z2 = false;
            for (Field field2 : Class.forName(genericTypeName).getDeclaredFields()) {
                if (!Modifier.isStatic(field2.getModifiers())) {
                    Class<?> type = field2.getType();
                    if (str.equals(type.getName())) {
                        if (i2 == 1) {
                            addIntoAssociationModelCollection(str, genericTypeName, genericTypeName, 2);
                        } else if (i2 == 2) {
                            addIntoAssociationInfoCollection(str, genericTypeName, genericTypeName, field, field2, 2);
                        }
                    } else if (isCollection(type) && str.equals(getGenericTypeName(field2))) {
                        if (i2 == 1) {
                            if (str.equalsIgnoreCase(genericTypeName)) {
                                GenericModel genericModel2 = new GenericModel();
                                genericModel2.setTableName(DBUtility.getGenericTableName(str, field.getName()));
                                genericModel2.setValueColumnName(DBUtility.getM2MSelfRefColumnName(field));
                                genericModel2.setValueColumnType(TypedValues.Custom.S_INT);
                                genericModel2.setValueIdColumnName(DBUtility.getGenericValueIdColumnName(str));
                                this.mGenericModels.add(genericModel2);
                            } else {
                                addIntoAssociationModelCollection(str, genericTypeName, null, 3);
                            }
                        } else if (i2 == 2 && !str.equalsIgnoreCase(genericTypeName)) {
                            addIntoAssociationInfoCollection(str, genericTypeName, null, field, field2, 3);
                        }
                    }
                    z2 = true;
                }
            }
            if (z2) {
                return;
            }
            if (i2 == 1) {
                addIntoAssociationModelCollection(str, genericTypeName, genericTypeName, 2);
            } else if (i2 == 2) {
                addIntoAssociationInfoCollection(str, genericTypeName, genericTypeName, field, null, 2);
            }
        }
    }

    private void oneToAnyConditions(String str, Field field, int i2) throws ClassNotFoundException {
        Class<?> type = field.getType();
        if (LitePalAttr.getInstance().getClassNames().contains(type.getName())) {
            boolean z2 = false;
            for (Field field2 : Class.forName(type.getName()).getDeclaredFields()) {
                if (!Modifier.isStatic(field2.getModifiers())) {
                    Class<?> type2 = field2.getType();
                    if (str.equals(type2.getName())) {
                        if (i2 == 1) {
                            addIntoAssociationModelCollection(str, type.getName(), type.getName(), 1);
                        } else if (i2 == 2) {
                            addIntoAssociationInfoCollection(str, type.getName(), type.getName(), field, field2, 1);
                        }
                    } else if (isCollection(type2) && str.equals(getGenericTypeName(field2))) {
                        if (i2 == 1) {
                            addIntoAssociationModelCollection(str, type.getName(), str, 2);
                        } else if (i2 == 2) {
                            addIntoAssociationInfoCollection(str, type.getName(), str, field, field2, 2);
                        }
                    }
                    z2 = true;
                }
            }
            if (z2) {
                return;
            }
            if (i2 == 1) {
                addIntoAssociationModelCollection(str, type.getName(), type.getName(), 1);
            } else if (i2 == 2) {
                addIntoAssociationInfoCollection(str, type.getName(), type.getName(), field, null, 1);
            }
        }
    }

    private void recursiveSupportedFields(Class<?> cls, List<Field> list) {
        if (cls == LitePalSupport.class || cls == Object.class) {
            return;
        }
        Field[] declaredFields = cls.getDeclaredFields();
        if (declaredFields.length > 0) {
            for (Field field : declaredFields) {
                Column column = (Column) field.getAnnotation(Column.class);
                if ((column == null || !column.ignore()) && !Modifier.isStatic(field.getModifiers()) && BaseUtility.isFieldTypeSupported(field.getType().getName())) {
                    list.add(field);
                }
            }
        }
        recursiveSupportedFields(cls.getSuperclass(), list);
    }

    private void recursiveSupportedGenericFields(Class<?> cls, List<Field> list) {
        if (cls == LitePalSupport.class || cls == Object.class) {
            return;
        }
        Field[] declaredFields = cls.getDeclaredFields();
        if (declaredFields.length > 0) {
            for (Field field : declaredFields) {
                Column column = (Column) field.getAnnotation(Column.class);
                if ((column == null || !column.ignore()) && !Modifier.isStatic(field.getModifiers()) && isCollection(field.getType())) {
                    String genericTypeName = getGenericTypeName(field);
                    if (BaseUtility.isGenericTypeSupported(genericTypeName) || cls.getName().equalsIgnoreCase(genericTypeName)) {
                        list.add(field);
                    }
                }
            }
        }
        recursiveSupportedGenericFields(cls.getSuperclass(), list);
    }

    public Collection<AssociationsInfo> getAssociationInfo(String str) {
        if (this.mAssociationInfos == null) {
            this.mAssociationInfos = new HashSet();
        }
        this.mAssociationInfos.clear();
        analyzeClassFields(str, 2);
        return this.mAssociationInfos;
    }

    public Collection<AssociationsModel> getAssociations(List<String> list) {
        if (this.mAssociationModels == null) {
            this.mAssociationModels = new HashSet();
        }
        if (this.mGenericModels == null) {
            this.mGenericModels = new HashSet();
        }
        this.mAssociationModels.clear();
        this.mGenericModels.clear();
        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            analyzeClassFields(it.next(), 1);
        }
        return this.mAssociationModels;
    }

    public String getColumnType(String str) {
        for (OrmChange ormChange : this.typeChangeRules) {
            String strObject2Relation = ormChange.object2Relation(str);
            if (strObject2Relation != null) {
                return strObject2Relation;
            }
        }
        return null;
    }

    public String getForeignKeyColumnName(String str) {
        return BaseUtility.changeCase(str + aq.f22519d);
    }

    public Collection<GenericModel> getGenericModels() {
        return this.mGenericModels;
    }

    public Class<?> getGenericTypeClass(Field field) {
        Type genericType = field.getGenericType();
        if (genericType == null || !(genericType instanceof ParameterizedType)) {
            return null;
        }
        return (Class) ((ParameterizedType) genericType).getActualTypeArguments()[0];
    }

    public String getGenericTypeName(Field field) {
        Class<?> genericTypeClass = getGenericTypeClass(field);
        if (genericTypeClass != null) {
            return genericTypeClass.getName();
        }
        return null;
    }

    public List<Field> getSupportedFields(String str) {
        List<Field> list = this.classFieldsMap.get(str);
        if (list != null) {
            return list;
        }
        ArrayList arrayList = new ArrayList();
        try {
            recursiveSupportedFields(Class.forName(str), arrayList);
            this.classFieldsMap.put(str, arrayList);
            return arrayList;
        } catch (ClassNotFoundException unused) {
            throw new DatabaseGenerateException(DatabaseGenerateException.CLASS_NOT_FOUND + str);
        }
    }

    public List<Field> getSupportedGenericFields(String str) {
        List<Field> list = this.classGenericFieldsMap.get(str);
        if (list != null) {
            return list;
        }
        ArrayList arrayList = new ArrayList();
        try {
            recursiveSupportedGenericFields(Class.forName(str), arrayList);
            this.classGenericFieldsMap.put(str, arrayList);
            return arrayList;
        } catch (ClassNotFoundException unused) {
            throw new DatabaseGenerateException(DatabaseGenerateException.CLASS_NOT_FOUND + str);
        }
    }

    public TableModel getTableModel(String str) {
        String tableNameByClassName = DBUtility.getTableNameByClassName(str);
        TableModel tableModel = new TableModel();
        tableModel.setTableName(tableNameByClassName);
        tableModel.setClassName(str);
        Iterator<Field> it = getSupportedFields(str).iterator();
        while (it.hasNext()) {
            tableModel.addColumnModel(convertFieldToColumnModel(it.next()));
        }
        return tableModel;
    }

    public boolean isCollection(Class<?> cls) {
        return isList(cls) || isSet(cls);
    }

    public boolean isIdColumn(String str) {
        return aq.f22519d.equalsIgnoreCase(str) || "id".equalsIgnoreCase(str);
    }

    public boolean isList(Class<?> cls) {
        return List.class.isAssignableFrom(cls);
    }

    public boolean isPrivate(Field field) {
        return Modifier.isPrivate(field.getModifiers());
    }

    public boolean isSet(Class<?> cls) {
        return Set.class.isAssignableFrom(cls);
    }
}
