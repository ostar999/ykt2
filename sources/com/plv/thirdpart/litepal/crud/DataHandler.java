package com.plv.thirdpart.litepal.crud;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.util.SparseArray;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.plv.thirdpart.litepal.LitePalBase;
import com.plv.thirdpart.litepal.Operator;
import com.plv.thirdpart.litepal.annotation.Column;
import com.plv.thirdpart.litepal.annotation.Encrypt;
import com.plv.thirdpart.litepal.crud.model.AssociationsInfo;
import com.plv.thirdpart.litepal.exceptions.DatabaseGenerateException;
import com.plv.thirdpart.litepal.exceptions.LitePalSupportException;
import com.plv.thirdpart.litepal.tablemanager.model.GenericModel;
import com.plv.thirdpart.litepal.util.BaseUtility;
import com.plv.thirdpart.litepal.util.DBUtility;
import com.plv.thirdpart.litepal.util.cipher.CipherUtil;
import com.umeng.analytics.pro.aq;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes5.dex */
abstract class DataHandler extends LitePalBase {
    public static final String TAG = "DataHandler";
    private List<AssociationsInfo> fkInCurrentModel;
    private List<AssociationsInfo> fkInOtherModel;
    SQLiteDatabase mDatabase;
    private LitePalSupport tempEmptyModel;

    public static class QueryInfoCache {
        Field field;
        String getMethodName;
    }

    private void analyzeAssociations(String str) {
        Collection<AssociationsInfo> associationInfo = getAssociationInfo(str);
        List<AssociationsInfo> list = this.fkInCurrentModel;
        if (list == null) {
            this.fkInCurrentModel = new ArrayList();
        } else {
            list.clear();
        }
        List<AssociationsInfo> list2 = this.fkInOtherModel;
        if (list2 == null) {
            this.fkInOtherModel = new ArrayList();
        } else {
            list2.clear();
        }
        for (AssociationsInfo associationsInfo : associationInfo) {
            if (associationsInfo.getAssociationType() == 2 || associationsInfo.getAssociationType() == 1) {
                if (associationsInfo.getClassHoldsForeignKey().equals(str)) {
                    this.fkInCurrentModel.add(associationsInfo);
                } else {
                    this.fkInOtherModel.add(associationsInfo);
                }
            } else if (associationsInfo.getAssociationType() == 3) {
                this.fkInOtherModel.add(associationsInfo);
            }
        }
    }

    private String genGetColumnMethod(Field field) {
        return genGetColumnMethod(isCollection(field.getType()) ? getGenericTypeClass(field) : field.getType());
    }

    private String[] getCustomizedColumns(String[] strArr, List<Field> list, List<AssociationsInfo> list2) {
        if (strArr == null || strArr.length <= 0) {
            return null;
        }
        ArrayList arrayList = new ArrayList(Arrays.asList(strArr));
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        ArrayList arrayList4 = new ArrayList();
        ArrayList arrayList5 = new ArrayList();
        Iterator<Field> it = list.iterator();
        while (it.hasNext()) {
            arrayList2.add(it.next().getName());
        }
        boolean z2 = false;
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            String str = (String) arrayList.get(i2);
            if (BaseUtility.containsIgnoreCases(arrayList2, str)) {
                arrayList3.add(Integer.valueOf(i2));
            } else if (isIdColumn(str)) {
                if (aq.f22519d.equalsIgnoreCase(str)) {
                    arrayList.set(i2, BaseUtility.changeCase("id"));
                }
                z2 = true;
            }
        }
        for (int size = arrayList3.size() - 1; size >= 0; size--) {
            arrayList4.add((String) arrayList.remove(((Integer) arrayList3.get(size)).intValue()));
        }
        for (Field field : list) {
            if (BaseUtility.containsIgnoreCases(arrayList4, field.getName())) {
                arrayList5.add(field);
            }
        }
        list.clear();
        list.addAll(arrayList5);
        if (list2 != null && list2.size() > 0) {
            for (int i3 = 0; i3 < list2.size(); i3++) {
                arrayList.add(getForeignKeyColumnName(DBUtility.getTableNameByClassName(list2.get(i3).getAssociatedClassName())));
            }
        }
        if (!z2) {
            arrayList.add(BaseUtility.changeCase("id"));
        }
        return (String[]) arrayList.toArray(new String[0]);
    }

    private Object getInitParamValue(Class<?> cls, Class<?> cls2) {
        String name = cls2.getName();
        if (TypedValues.Custom.S_BOOLEAN.equals(name) || "java.lang.Boolean".equals(name)) {
            return Boolean.FALSE;
        }
        if (TypedValues.Custom.S_FLOAT.equals(name) || "java.lang.Float".equals(name)) {
            return Float.valueOf(0.0f);
        }
        if ("double".equals(name) || "java.lang.Double".equals(name)) {
            return Double.valueOf(0.0d);
        }
        if ("int".equals(name) || "java.lang.Integer".equals(name)) {
            return 0;
        }
        if ("long".equals(name) || "java.lang.Long".equals(name)) {
            return 0L;
        }
        if ("short".equals(name) || "java.lang.Short".equals(name)) {
            return 0;
        }
        if ("char".equals(name) || "java.lang.Character".equals(name)) {
            return ' ';
        }
        if ("[B".equals(name) || "[Ljava.lang.Byte;".equals(name)) {
            return new byte[0];
        }
        if ("java.lang.String".equals(name)) {
            return "";
        }
        if (cls == cls2) {
            return null;
        }
        return createInstanceFromClass(cls2);
    }

    private Class<?> getObjectType(Class<?> cls) {
        if (cls == null || !cls.isPrimitive()) {
            return null;
        }
        String name = cls.getName();
        if (name.equals("double")) {
            return Double.class;
        }
        if (name.equals("int")) {
            return Integer.class;
        }
        if (name.equals("char")) {
            return Character.class;
        }
        if (name.equals("long")) {
            return Long.class;
        }
        if (name.equals(TypedValues.Custom.S_BOOLEAN)) {
            return Boolean.class;
        }
        if (name.equals(TypedValues.Custom.S_FLOAT)) {
            return Float.class;
        }
        if (name.equals("short")) {
            return Short.class;
        }
        return null;
    }

    private boolean isCharType(Field field) {
        String name = field.getType().getName();
        return name.equals("char") || name.endsWith("Character");
    }

    private boolean isFieldWithDefaultValue(LitePalSupport litePalSupport, Field field) throws IllegalAccessException, SecurityException, IllegalArgumentException {
        LitePalSupport emptyModel = getEmptyModel(litePalSupport);
        Object fieldValue = getFieldValue(litePalSupport, field);
        Object fieldValue2 = getFieldValue(emptyModel, field);
        return (fieldValue == null || fieldValue2 == null) ? fieldValue == fieldValue2 : fieldValue.toString().equals(fieldValue2.toString());
    }

    private boolean isPrimitiveBooleanType(Field field) {
        return TypedValues.Custom.S_BOOLEAN.equals(field.getType().getName());
    }

    private boolean isSaving() {
        return SaveHandler.class.getName().equals(getClass().getName());
    }

    private boolean isUpdating() {
        return UpdateHandler.class.getName().equals(getClass().getName());
    }

    private void putFieldsValueDependsOnSaveOrUpdate(LitePalSupport litePalSupport, Field field, ContentValues contentValues) throws IllegalAccessException, SecurityException, IllegalArgumentException, InvocationTargetException {
        if (isUpdating()) {
            if (isFieldWithDefaultValue(litePalSupport, field)) {
                return;
            }
            putContentValuesForUpdate(litePalSupport, field, contentValues);
        } else if (isSaving()) {
            putContentValuesForSave(litePalSupport, field, contentValues);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:53:0x017d  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x0198  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void setAssociatedModel(com.plv.thirdpart.litepal.crud.LitePalSupport r27) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 413
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.plv.thirdpart.litepal.crud.DataHandler.setAssociatedModel(com.plv.thirdpart.litepal.crud.LitePalSupport):void");
    }

    private void setToModelByReflection(Object obj, Field field, int i2, String str, Cursor cursor) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        Class<?> cls = cursor.getClass();
        if (cursor.isNull(i2)) {
            return;
        }
        Object objInvoke = cls.getMethod(str, Integer.TYPE).invoke(cursor, Integer.valueOf(i2));
        if (field.getType() == Boolean.TYPE || field.getType() == Boolean.class) {
            if ("0".equals(String.valueOf(objInvoke))) {
                objInvoke = Boolean.FALSE;
            } else if ("1".equals(String.valueOf(objInvoke))) {
                objInvoke = Boolean.TRUE;
            }
        } else if (field.getType() == Character.TYPE || field.getType() == Character.class) {
            objInvoke = Character.valueOf(((String) objInvoke).charAt(0));
        } else if (field.getType() == Date.class) {
            long jLongValue = ((Long) objInvoke).longValue();
            objInvoke = jLongValue == Long.MAX_VALUE ? null : new Date(jLongValue);
        }
        if (!isCollection(field.getType())) {
            Encrypt encrypt = (Encrypt) field.getAnnotation(Encrypt.class);
            if (encrypt != null && "java.lang.String".equals(field.getType().getName())) {
                objInvoke = decryptValue(encrypt.algorithm(), objInvoke);
            }
            DynamicExecutor.setField(obj, field.getName(), objInvoke, obj.getClass());
            return;
        }
        Collection arrayList = (Collection) DynamicExecutor.getField(obj, field.getName(), obj.getClass());
        if (arrayList == null) {
            arrayList = isList(field.getType()) ? new ArrayList() : new HashSet();
            DynamicExecutor.setField(obj, field.getName(), arrayList, obj.getClass());
        }
        String genericTypeName = getGenericTypeName(field);
        if ("java.lang.String".equals(genericTypeName)) {
            Encrypt encrypt2 = (Encrypt) field.getAnnotation(Encrypt.class);
            if (encrypt2 != null) {
                objInvoke = decryptValue(encrypt2.algorithm(), objInvoke);
            }
        } else if (obj.getClass().getName().equals(genericTypeName) && ((objInvoke instanceof Long) || (objInvoke instanceof Integer))) {
            objInvoke = Operator.find(obj.getClass(), ((Long) objInvoke).longValue());
        }
        arrayList.add(objInvoke);
    }

    public void analyzeAssociatedModels(LitePalSupport litePalSupport, Collection<AssociationsInfo> collection) {
        try {
            for (AssociationsInfo associationsInfo : collection) {
                if (associationsInfo.getAssociationType() == 2) {
                    new Many2OneAnalyzer().analyze(litePalSupport, associationsInfo);
                } else if (associationsInfo.getAssociationType() == 1) {
                    new One2OneAnalyzer().analyze(litePalSupport, associationsInfo);
                } else if (associationsInfo.getAssociationType() == 3) {
                    new Many2ManyAnalyzer().analyze(litePalSupport, associationsInfo);
                }
            }
        } catch (Exception e2) {
            throw new LitePalSupportException(e2.getMessage(), e2);
        }
    }

    public Object createInstanceFromClass(Class<?> cls) {
        try {
            Constructor<?> constructorFindBestSuitConstructor = findBestSuitConstructor(cls);
            return constructorFindBestSuitConstructor.newInstance(getConstructorParams(cls, constructorFindBestSuitConstructor));
        } catch (Exception e2) {
            throw new LitePalSupportException(e2.getMessage(), e2);
        }
    }

    public Object decryptValue(String str, Object obj) {
        return (str == null || obj == null || !"AES".equalsIgnoreCase(str)) ? obj : CipherUtil.aesDecrypt((String) obj);
    }

    public Object encryptValue(String str, Object obj) {
        return (str == null || obj == null) ? obj : "AES".equalsIgnoreCase(str) ? CipherUtil.aesEncrypt((String) obj) : "MD5".equalsIgnoreCase(str) ? CipherUtil.md5Encrypt((String) obj) : obj;
    }

    public Constructor<?> findBestSuitConstructor(Class<?> cls) throws SecurityException {
        Constructor<?>[] declaredConstructors = cls.getDeclaredConstructors();
        if (declaredConstructors.length == 0) {
            throw new LitePalSupportException(cls.getName() + " has no constructor. LitePal could not handle it");
        }
        int length = declaredConstructors.length;
        Constructor<?> constructor = null;
        int length2 = Integer.MAX_VALUE;
        int i2 = 0;
        while (true) {
            boolean z2 = true;
            if (i2 >= length) {
                break;
            }
            Constructor<?> constructor2 = declaredConstructors[i2];
            Class<?>[] parameterTypes = constructor2.getParameterTypes();
            for (Class<?> cls2 : parameterTypes) {
                if (cls2 == cls || (cls2.getName().startsWith("com.android") && cls2.getName().endsWith("InstantReloadException"))) {
                    z2 = false;
                    break;
                }
            }
            if (z2 && parameterTypes.length < length2) {
                length2 = parameterTypes.length;
                constructor = constructor2;
            }
            i2++;
        }
        if (constructor != null) {
            constructor.setAccessible(true);
            return constructor;
        }
        StringBuilder sb = new StringBuilder(cls.getName());
        sb.append(" has no suited constructor to new instance. Constructors defined in class:");
        for (Constructor<?> constructor3 : declaredConstructors) {
            sb.append("\n");
            sb.append(constructor3.toString());
        }
        throw new LitePalSupportException(sb.toString());
    }

    public LitePalSupport getAssociatedModel(LitePalSupport litePalSupport, AssociationsInfo associationsInfo) throws IllegalAccessException, SecurityException, IllegalArgumentException {
        return (LitePalSupport) getFieldValue(litePalSupport, associationsInfo.getAssociateOtherModelFromSelf());
    }

    public Collection<LitePalSupport> getAssociatedModels(LitePalSupport litePalSupport, AssociationsInfo associationsInfo) throws IllegalAccessException, SecurityException, IllegalArgumentException {
        return (Collection) getFieldValue(litePalSupport, associationsInfo.getAssociateOtherModelFromSelf());
    }

    public Object[] getConstructorParams(Class<?> cls, Constructor<?> constructor) {
        Class<?>[] parameterTypes = constructor.getParameterTypes();
        Object[] objArr = new Object[parameterTypes.length];
        for (int i2 = 0; i2 < parameterTypes.length; i2++) {
            objArr[i2] = getInitParamValue(cls, parameterTypes[i2]);
        }
        return objArr;
    }

    public LitePalSupport getEmptyModel(LitePalSupport litePalSupport) {
        LitePalSupport litePalSupport2 = this.tempEmptyModel;
        if (litePalSupport2 != null) {
            return litePalSupport2;
        }
        String className = null;
        try {
            className = litePalSupport.getClassName();
            LitePalSupport litePalSupport3 = (LitePalSupport) Class.forName(className).newInstance();
            this.tempEmptyModel = litePalSupport3;
            return litePalSupport3;
        } catch (ClassNotFoundException unused) {
            throw new DatabaseGenerateException(DatabaseGenerateException.CLASS_NOT_FOUND + className);
        } catch (InstantiationException e2) {
            throw new LitePalSupportException(className + LitePalSupportException.INSTANTIATION_EXCEPTION, e2);
        } catch (Exception e3) {
            throw new LitePalSupportException(e3.getMessage(), e3);
        }
    }

    public Object getFieldValue(LitePalSupport litePalSupport, Field field) throws IllegalAccessException, SecurityException, IllegalArgumentException {
        if (shouldGetOrSet(litePalSupport, field)) {
            return DynamicExecutor.getField(litePalSupport, field.getName(), litePalSupport.getClass());
        }
        return null;
    }

    public List<AssociationsInfo> getForeignKeyAssociations(String str, boolean z2) {
        if (!z2) {
            return null;
        }
        analyzeAssociations(str);
        return this.fkInCurrentModel;
    }

    public String getIntermediateTableName(LitePalSupport litePalSupport, String str) {
        return BaseUtility.changeCase(DBUtility.getIntermediateTableName(litePalSupport.getTableName(), str));
    }

    public Class<?>[] getParameterTypes(Field field, Object obj, Object[] objArr) {
        Class<?>[] clsArr;
        if (isCharType(field)) {
            objArr[1] = String.valueOf(obj);
            return new Class[]{String.class, String.class};
        }
        if (field.getType().isPrimitive()) {
            clsArr = new Class[]{String.class, getObjectType(field.getType())};
        } else {
            if ("java.util.Date".equals(field.getType().getName())) {
                return new Class[]{String.class, Long.class};
            }
            clsArr = new Class[]{String.class, field.getType()};
        }
        return clsArr;
    }

    public String getTableName(Class<?> cls) {
        return BaseUtility.changeCase(DBUtility.getTableNameByClassName(cls.getName()));
    }

    public String[] getWhereArgs(String... strArr) {
        if (isAffectAllLines(strArr) || strArr == null || strArr.length <= 1) {
            return null;
        }
        String[] strArr2 = new String[strArr.length - 1];
        System.arraycopy(strArr, 1, strArr2, 0, strArr.length - 1);
        return strArr2;
    }

    public String getWhereClause(String... strArr) {
        if (isAffectAllLines(strArr) || strArr == null || strArr.length <= 0) {
            return null;
        }
        return strArr[0];
    }

    public String getWhereOfIdsWithOr(Collection<Long> collection) {
        StringBuilder sb = new StringBuilder();
        Iterator<Long> it = collection.iterator();
        boolean z2 = false;
        while (it.hasNext()) {
            long jLongValue = it.next().longValue();
            if (z2) {
                sb.append(" or ");
            }
            sb.append("id = ");
            sb.append(jLongValue);
            z2 = true;
        }
        return BaseUtility.changeCase(sb.toString());
    }

    public void giveBaseObjIdValue(LitePalSupport litePalSupport, long j2) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
        if (j2 > 0) {
            DynamicExecutor.set(litePalSupport, "baseObjId", Long.valueOf(j2), LitePalSupport.class);
        }
    }

    public boolean isAffectAllLines(Object... objArr) {
        return objArr != null && objArr.length == 0;
    }

    public String makeGetterMethodName(Field field) {
        String str;
        String name = field.getName();
        if (isPrimitiveBooleanType(field)) {
            if (name.matches("^is[A-Z]{1}.*$")) {
                name = name.substring(2);
            }
            str = "is";
        } else {
            str = "get";
        }
        if (name.matches("^[a-z]{1}[A-Z]{1}.*")) {
            return str + name;
        }
        return str + BaseUtility.capitalize(name);
    }

    public String makeSetterMethodName(Field field) {
        if (isPrimitiveBooleanType(field) && field.getName().matches("^is[A-Z]{1}.*$")) {
            return "set" + field.getName().substring(2);
        }
        if (field.getName().matches("^[a-z]{1}[A-Z]{1}.*")) {
            return "set" + field.getName();
        }
        return "set" + BaseUtility.capitalize(field.getName());
    }

    public <T> T mathQuery(String str, String[] strArr, String[] strArr2, Class<T> cls) throws Throwable {
        BaseUtility.checkConditionsCorrect(strArr2);
        Cursor cursor = (T) null;
        try {
            try {
                Cursor cursorQuery = this.mDatabase.query(str, strArr, getWhereClause(strArr2), getWhereArgs(strArr2), null, null, null);
                try {
                    if (cursorQuery.moveToFirst()) {
                        cursor = (T) cursorQuery.getClass().getMethod(genGetColumnMethod((Class<?>) cls), Integer.TYPE).invoke(cursorQuery, 0);
                    }
                    cursorQuery.close();
                    return (T) cursor;
                } catch (Exception e2) {
                    e = e2;
                    cursor = (T) cursorQuery;
                    throw new LitePalSupportException(e.getMessage(), e);
                } catch (Throwable th) {
                    th = th;
                    cursor = cursorQuery;
                    if (cursor != null) {
                        cursor.close();
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (Exception e3) {
            e = e3;
        }
    }

    public void putContentValuesForSave(LitePalSupport litePalSupport, Field field, ContentValues contentValues) throws IllegalAccessException, SecurityException, IllegalArgumentException, InvocationTargetException {
        Object fieldValue = getFieldValue(litePalSupport, field);
        if ("java.util.Date".equals(field.getType().getName())) {
            if (fieldValue != null) {
                fieldValue = Long.valueOf(((Date) fieldValue).getTime());
            } else {
                Column column = (Column) field.getAnnotation(Column.class);
                if (column != null) {
                    String strDefaultValue = column.defaultValue();
                    if (!strDefaultValue.isEmpty()) {
                        try {
                            fieldValue = Long.valueOf(Long.parseLong(strDefaultValue));
                        } catch (NumberFormatException unused) {
                            Log.w(TAG, field + " in " + litePalSupport.getClass() + " with invalid defaultValue. So we use null instead");
                        }
                    }
                }
                if (fieldValue == null) {
                    fieldValue = Long.MAX_VALUE;
                }
            }
        }
        if (fieldValue != null) {
            Encrypt encrypt = (Encrypt) field.getAnnotation(Encrypt.class);
            if (encrypt != null && "java.lang.String".equals(field.getType().getName())) {
                fieldValue = encryptValue(encrypt.algorithm(), fieldValue);
            }
            Object[] objArr = {BaseUtility.changeCase(DBUtility.convertToValidColumnName(field.getName())), fieldValue};
            DynamicExecutor.send(contentValues, "put", objArr, contentValues.getClass(), getParameterTypes(field, fieldValue, objArr));
        }
    }

    public void putContentValuesForUpdate(LitePalSupport litePalSupport, Field field, ContentValues contentValues) throws IllegalAccessException, SecurityException, IllegalArgumentException, InvocationTargetException {
        Object fieldValue = getFieldValue(litePalSupport, field);
        if ("java.util.Date".equals(field.getType().getName())) {
            fieldValue = fieldValue != null ? Long.valueOf(((Date) fieldValue).getTime()) : Long.MAX_VALUE;
        }
        Encrypt encrypt = (Encrypt) field.getAnnotation(Encrypt.class);
        if (encrypt != null && "java.lang.String".equals(field.getType().getName())) {
            fieldValue = encryptValue(encrypt.algorithm(), fieldValue);
        }
        Object[] objArr = {BaseUtility.changeCase(DBUtility.convertToValidColumnName(field.getName())), fieldValue};
        DynamicExecutor.send(contentValues, "put", objArr, contentValues.getClass(), getParameterTypes(field, fieldValue, objArr));
    }

    public void putFieldsValue(LitePalSupport litePalSupport, List<Field> list, ContentValues contentValues) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        for (Field field : list) {
            if (!isIdColumn(field.getName())) {
                putFieldsValueDependsOnSaveOrUpdate(litePalSupport, field, contentValues);
            }
        }
    }

    public <T> List<T> query(Class<T> cls, String[] strArr, String str, String[] strArr2, String str2, String str3, String str4, String str5, List<AssociationsInfo> list) {
        ArrayList arrayList = new ArrayList();
        Cursor cursorQuery = null;
        try {
            try {
                List<Field> supportedFields = getSupportedFields(cls.getName());
                List<Field> supportedGenericFields = getSupportedGenericFields(cls.getName());
                String[] strArrConvertSelectClauseToValidNames = DBUtility.convertSelectClauseToValidNames(getCustomizedColumns(strArr, supportedGenericFields, list));
                cursorQuery = this.mDatabase.query(getTableName(cls), strArrConvertSelectClauseToValidNames, str, strArr2, str2, str3, str4, str5);
                if (cursorQuery.moveToFirst()) {
                    SparseArray<QueryInfoCache> sparseArray = new SparseArray<>();
                    HashMap map = new HashMap();
                    do {
                        Object objCreateInstanceFromClass = createInstanceFromClass(cls);
                        giveBaseObjIdValue((LitePalSupport) objCreateInstanceFromClass, cursorQuery.getLong(cursorQuery.getColumnIndexOrThrow("id")));
                        setValueToModel(objCreateInstanceFromClass, supportedFields, list, cursorQuery, sparseArray);
                        setGenericValueToModel((LitePalSupport) objCreateInstanceFromClass, supportedGenericFields, map);
                        if (list != null) {
                            setAssociatedModel((LitePalSupport) objCreateInstanceFromClass);
                        }
                        arrayList.add(objCreateInstanceFromClass);
                    } while (cursorQuery.moveToNext());
                    sparseArray.clear();
                    map.clear();
                }
                cursorQuery.close();
                return arrayList;
            } catch (Exception e2) {
                throw new LitePalSupportException(e2.getMessage(), e2);
            }
        } catch (Throwable th) {
            if (cursorQuery != null) {
                cursorQuery.close();
            }
            throw th;
        }
    }

    public void setFieldValue(LitePalSupport litePalSupport, Field field, Object obj) throws IllegalAccessException, SecurityException, IllegalArgumentException {
        if (shouldGetOrSet(litePalSupport, field)) {
            DynamicExecutor.setField(litePalSupport, field.getName(), obj, litePalSupport.getClass());
        }
    }

    public void setGenericValueToModel(LitePalSupport litePalSupport, List<Field> list, Map<Field, GenericModel> map) throws Throwable {
        String tableName;
        String valueIdColumnName;
        String getMethodName;
        String str;
        Cursor cursorQuery;
        String strConvertToValidColumnName;
        String strGenGetColumnMethod;
        for (Field field : list) {
            GenericModel genericModel = map.get(field);
            if (genericModel == null) {
                if (litePalSupport.getClassName().equals(getGenericTypeName(field))) {
                    strConvertToValidColumnName = DBUtility.getM2MSelfRefColumnName(field);
                    strGenGetColumnMethod = "getLong";
                } else {
                    strConvertToValidColumnName = DBUtility.convertToValidColumnName(field.getName());
                    strGenGetColumnMethod = genGetColumnMethod(field);
                }
                tableName = DBUtility.getGenericTableName(litePalSupport.getClassName(), field.getName());
                valueIdColumnName = DBUtility.getGenericValueIdColumnName(litePalSupport.getClassName());
                GenericModel genericModel2 = new GenericModel();
                genericModel2.setTableName(tableName);
                genericModel2.setValueColumnName(strConvertToValidColumnName);
                genericModel2.setValueIdColumnName(valueIdColumnName);
                genericModel2.setGetMethodName(strGenGetColumnMethod);
                map.put(field, genericModel2);
                str = strConvertToValidColumnName;
                getMethodName = strGenGetColumnMethod;
            } else {
                tableName = genericModel.getTableName();
                String valueColumnName = genericModel.getValueColumnName();
                valueIdColumnName = genericModel.getValueIdColumnName();
                getMethodName = genericModel.getGetMethodName();
                str = valueColumnName;
            }
            String str2 = tableName;
            Cursor cursor = null;
            try {
                cursorQuery = this.mDatabase.query(str2, null, valueIdColumnName + " = ?", new String[]{String.valueOf(litePalSupport.getBaseObjId())}, null, null, null);
            } catch (Throwable th) {
                th = th;
            }
            try {
                if (cursorQuery.moveToFirst()) {
                    do {
                        int columnIndex = cursorQuery.getColumnIndex(BaseUtility.changeCase(str));
                        if (columnIndex != -1) {
                            setToModelByReflection(litePalSupport, field, columnIndex, getMethodName, cursorQuery);
                        }
                    } while (cursorQuery.moveToNext());
                }
                cursorQuery.close();
            } catch (Throwable th2) {
                th = th2;
                cursor = cursorQuery;
                if (cursor != null) {
                    cursor.close();
                }
                throw th;
            }
        }
    }

    public void setValueToModel(Object obj, List<Field> list, List<AssociationsInfo> list2, Cursor cursor, SparseArray<QueryInfoCache> sparseArray) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        int size = sparseArray.size();
        if (size > 0) {
            for (int i2 = 0; i2 < size; i2++) {
                int iKeyAt = sparseArray.keyAt(i2);
                QueryInfoCache queryInfoCache = sparseArray.get(iKeyAt);
                setToModelByReflection(obj, queryInfoCache.field, iKeyAt, queryInfoCache.getMethodName, cursor);
            }
        } else {
            for (Field field : list) {
                String strGenGetColumnMethod = genGetColumnMethod(field);
                int columnIndex = cursor.getColumnIndex(BaseUtility.changeCase(isIdColumn(field.getName()) ? "id" : DBUtility.convertToValidColumnName(field.getName())));
                if (columnIndex != -1) {
                    setToModelByReflection(obj, field, columnIndex, strGenGetColumnMethod, cursor);
                    QueryInfoCache queryInfoCache2 = new QueryInfoCache();
                    queryInfoCache2.getMethodName = strGenGetColumnMethod;
                    queryInfoCache2.field = field;
                    sparseArray.put(columnIndex, queryInfoCache2);
                }
            }
        }
        if (list2 != null) {
            for (AssociationsInfo associationsInfo : list2) {
                int columnIndex2 = cursor.getColumnIndex(getForeignKeyColumnName(DBUtility.getTableNameByClassName(associationsInfo.getAssociatedClassName())));
                if (columnIndex2 != -1) {
                    try {
                        LitePalSupport litePalSupport = (LitePalSupport) Operator.find(Class.forName(associationsInfo.getAssociatedClassName()), cursor.getLong(columnIndex2));
                        if (litePalSupport != null) {
                            setFieldValue((LitePalSupport) obj, associationsInfo.getAssociateOtherModelFromSelf(), litePalSupport);
                        }
                    } catch (ClassNotFoundException e2) {
                        e2.printStackTrace();
                    }
                }
            }
        }
    }

    public boolean shouldGetOrSet(LitePalSupport litePalSupport, Field field) {
        return (litePalSupport == null || field == null) ? false : true;
    }

    private String genGetColumnMethod(Class<?> cls) {
        String simpleName;
        String str;
        if (cls.isPrimitive()) {
            simpleName = BaseUtility.capitalize(cls.getName());
        } else {
            simpleName = cls.getSimpleName();
        }
        str = "get" + simpleName;
        str.hashCode();
        switch (str) {
            case "getInteger":
            case "getBoolean":
                return "getInt";
            case "getChar":
            case "getCharacter":
                return "getString";
            case "getDate":
                return "getLong";
            default:
                return str;
        }
    }

    public String getWhereOfIdsWithOr(long... jArr) {
        StringBuilder sb = new StringBuilder();
        int length = jArr.length;
        int i2 = 0;
        boolean z2 = false;
        while (i2 < length) {
            long j2 = jArr[i2];
            if (z2) {
                sb.append(" or ");
            }
            sb.append("id = ");
            sb.append(j2);
            i2++;
            z2 = true;
        }
        return BaseUtility.changeCase(sb.toString());
    }
}
