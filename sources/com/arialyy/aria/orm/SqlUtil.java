package com.arialyy.aria.orm;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.arialyy.aria.core.AriaConfig;
import com.arialyy.aria.orm.annotation.Default;
import com.arialyy.aria.orm.annotation.Foreign;
import com.arialyy.aria.orm.annotation.Ignore;
import com.arialyy.aria.orm.annotation.Many;
import com.arialyy.aria.orm.annotation.NoNull;
import com.arialyy.aria.orm.annotation.One;
import com.arialyy.aria.orm.annotation.Primary;
import com.arialyy.aria.orm.annotation.Unique;
import com.arialyy.aria.orm.annotation.Wrapper;
import com.arialyy.aria.util.ALog;
import com.arialyy.aria.util.CommonUtil;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes2.dex */
final class SqlUtil {
    private static final String TAG = CommonUtil.getClassName("SqlUtil");

    private static Object checkData(String str, String str2) {
        if (str.equalsIgnoreCase("java.lang.String")) {
            return str2;
        }
        if (str.equalsIgnoreCase("int") || str.equals("java.lang.Integer")) {
            return Integer.valueOf(Integer.parseInt(str2));
        }
        if (str.equalsIgnoreCase("double") || str.equals("java.lang.Double")) {
            return Double.valueOf(Double.parseDouble(str2));
        }
        if (str.equalsIgnoreCase(TypedValues.Custom.S_FLOAT) || str.equals("java.lang.Float")) {
            return Float.valueOf(Float.parseFloat(str2));
        }
        return null;
    }

    public static boolean checkDataExist(SQLiteDatabase sQLiteDatabase, Class<? extends DbEntity> cls, String... strArr) {
        SQLiteDatabase sQLiteDatabaseCheckDb = checkDb(sQLiteDatabase);
        if (!CommonUtil.checkSqlExpression(strArr)) {
            return false;
        }
        String strReplace = String.format("SELECT rowid, * FROM %s WHERE %s ", CommonUtil.getClassName((Class) cls), strArr[0]).replace("?", "%s");
        int length = strArr.length - 1;
        String[] strArr2 = new String[length];
        int i2 = 0;
        while (i2 < length) {
            int i3 = i2 + 1;
            strArr2[i2] = String.format("'%s'", encodeStr(strArr[i3]));
            i2 = i3;
        }
        Cursor cursorRawQuery = sQLiteDatabaseCheckDb.rawQuery(String.format(strReplace, strArr2), null);
        boolean z2 = cursorRawQuery.getCount() > 0;
        closeCursor(cursorRawQuery);
        return z2;
    }

    public static SQLiteDatabase checkDb(SQLiteDatabase sQLiteDatabase) {
        return (sQLiteDatabase == null || !sQLiteDatabase.isOpen()) ? SqlHelper.getInstance().getDb() : sQLiteDatabase;
    }

    public static boolean checkList(Field field) {
        if (CommonUtil.getListParamType(field) == String.class) {
            return true;
        }
        ALog.d(TAG, "map参数错误，支持List<String>的参数字段");
        return false;
    }

    public static boolean checkMap(Field field) {
        Class cls;
        Class cls2;
        Class[] mapParamType = CommonUtil.getMapParamType(field);
        if (mapParamType != null && (cls = mapParamType[0]) != null && (cls2 = mapParamType[1]) != null && cls == String.class && cls2 == String.class) {
            return true;
        }
        ALog.d(TAG, "map参数错误，支持Map<String,String>的参数字段");
        return false;
    }

    public static void checkOrCreateTable(SQLiteDatabase sQLiteDatabase, Class<? extends DbEntity> cls) throws SecurityException, SQLException {
        if (tableExists(sQLiteDatabase, cls)) {
            return;
        }
        createTable(sQLiteDatabase, cls);
    }

    public static void clean(SQLiteDatabase sQLiteDatabase, Class<? extends DbEntity> cls) throws SQLException {
        SQLiteDatabase sQLiteDatabaseCheckDb = checkDb(sQLiteDatabase);
        String className = CommonUtil.getClassName((Class) cls);
        if (tableExists(sQLiteDatabaseCheckDb, cls)) {
            sQLiteDatabaseCheckDb.execSQL("DELETE FROM " + className);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0013 A[Catch: all -> 0x0015, DONT_GENERATE, TryCatch #0 {, blocks: (B:5:0x0005, B:7:0x000b, B:10:0x0010, B:11:0x0013), top: B:16:0x0005, inners: #1 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void closeCursor(android.database.Cursor r2) {
        /*
            java.lang.Class<com.arialyy.aria.orm.AbsDelegate> r0 = com.arialyy.aria.orm.AbsDelegate.class
            monitor-enter(r0)
            if (r2 == 0) goto L13
            boolean r1 = r2.isClosed()     // Catch: java.lang.Throwable -> L15
            if (r1 != 0) goto L13
            r2.close()     // Catch: android.database.SQLException -> Lf java.lang.Throwable -> L15
            goto L13
        Lf:
            r2 = move-exception
            r2.printStackTrace()     // Catch: java.lang.Throwable -> L15
        L13:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L15
            return
        L15:
            r2 = move-exception
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L15
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.arialyy.aria.orm.SqlUtil.closeCursor(android.database.Cursor):void");
    }

    public static void createTable(SQLiteDatabase sQLiteDatabase, Class<? extends DbEntity> cls) throws SecurityException, SQLException {
        Class<?> type;
        String columnType;
        SQLiteDatabase sQLiteDatabaseCheckDb = checkDb(sQLiteDatabase);
        List<Field> allFields = CommonUtil.getAllFields(cls);
        if (allFields == null || allFields.size() <= 0) {
            return;
        }
        ArrayList<Field> arrayList = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE IF NOT EXISTS ");
        sb.append(CommonUtil.getClassName((Class) cls));
        sb.append(" (");
        for (Field field : allFields) {
            field.setAccessible(true);
            if (!isIgnore(field) && (columnType = getColumnType((type = field.getType()))) != null) {
                sb.append(field.getName());
                sb.append(" ");
                sb.append(columnType);
                if (isPrimary(field)) {
                    Primary primary = (Primary) field.getAnnotation(Primary.class);
                    sb.append(" PRIMARY KEY");
                    if (primary.autoincrement() && (type == Integer.TYPE || type == Integer.class)) {
                        sb.append(" AUTOINCREMENT");
                    }
                }
                if (isForeign(field)) {
                    arrayList.add(field);
                }
                if (isNoNull(field)) {
                    sb.append(" NOT NULL");
                }
                if (isDefault(field)) {
                    Default r4 = (Default) field.getAnnotation(Default.class);
                    if (!TextUtils.isEmpty(r4.value())) {
                        sb.append(" ERROR ");
                        sb.append("'");
                        sb.append(r4.value());
                        sb.append("'");
                    }
                }
                if (isUnique(field)) {
                    sb.append(" UNIQUE");
                }
                sb.append(",");
            }
        }
        for (Field field2 : arrayList) {
            Foreign foreign = (Foreign) field2.getAnnotation(Foreign.class);
            sb.append("FOREIGN KEY (");
            sb.append(field2.getName());
            sb.append(") REFERENCES ");
            sb.append(CommonUtil.getClassName((Class) foreign.parent()));
            sb.append("(");
            sb.append(foreign.column());
            sb.append(")");
            ActionPolicy actionPolicyOnUpdate = foreign.onUpdate();
            ActionPolicy actionPolicyOnDelete = foreign.onDelete();
            ActionPolicy actionPolicy = ActionPolicy.NO_ACTION;
            if (actionPolicyOnUpdate != actionPolicy) {
                sb.append(" ON UPDATE ");
                sb.append(actionPolicyOnUpdate.function);
            }
            if (actionPolicyOnDelete != actionPolicy) {
                sb.append(" ON DELETE ");
                sb.append(actionPolicyOnUpdate.function);
            }
            sb.append(",");
        }
        String string = sb.toString();
        String str = string.substring(0, string.length() - 1) + ");";
        ALog.d(TAG, "创建表的sql：" + str);
        sQLiteDatabaseCheckDb.execSQL(str);
    }

    public static void dropTable(SQLiteDatabase sQLiteDatabase, String str) throws SQLException {
        checkDb(sQLiteDatabase).execSQL(String.format("DROP TABLE IF EXISTS %s", str));
    }

    public static String encodeStr(String str) {
        return URLEncoder.encode(str.replaceAll("\\+", "%2B"));
    }

    public static List<Field> getAllNotIgnoreField(Class cls) throws SecurityException {
        List<Field> allFields = CommonUtil.getAllFields(cls);
        ArrayList arrayList = new ArrayList();
        if (allFields == null || allFields.size() <= 0) {
            return null;
        }
        for (Field field : allFields) {
            field.setAccessible(true);
            if (!isIgnore(field)) {
                arrayList.add(field);
            }
        }
        return arrayList;
    }

    public static String getColumnType(Class cls) {
        if (cls == String.class || cls.isEnum()) {
            return "VARCHAR";
        }
        if (cls == Integer.TYPE || cls == Integer.class) {
            return "INTEGER";
        }
        if (cls == Float.TYPE || cls == Float.class) {
            return "FLOAT";
        }
        if (cls == Double.TYPE || cls == Double.class) {
            return "DOUBLE";
        }
        if (cls == Long.TYPE || cls == Long.class) {
            return "BIGINT";
        }
        if (cls == Boolean.TYPE || cls == Boolean.class) {
            return "BOOLEAN";
        }
        if (cls == Date.class || cls == java.sql.Date.class) {
            return "DATA";
        }
        if (cls == Byte.TYPE || cls == Byte.class) {
            return "BLOB";
        }
        if (cls == Map.class || cls == List.class) {
            return "TEXT";
        }
        return null;
    }

    public static String getColumnTypeByFieldName(Class cls, String str) {
        for (Field field : CommonUtil.getAllFields(cls)) {
            if (field.getName().equals(str)) {
                return getColumnType(field.getType());
            }
        }
        return null;
    }

    public static List<String> getColumns(Class<? extends DbEntity> cls) throws SecurityException {
        ArrayList arrayList = new ArrayList();
        for (Field field : CommonUtil.getAllFields(cls)) {
            field.setAccessible(true);
            if (!isIgnore(field)) {
                arrayList.add(field.getName());
            }
        }
        return arrayList;
    }

    public static String getPrimaryName(Class<? extends DbEntity> cls) throws SecurityException {
        List<Field> allFields = CommonUtil.getAllFields(cls);
        if (allFields == null || allFields.isEmpty()) {
            return null;
        }
        for (Field field : allFields) {
            field.setAccessible(true);
            if (isPrimary(field)) {
                return field.getName();
            }
        }
        return null;
    }

    public static boolean isDefault(Field field) {
        return ((Default) field.getAnnotation(Default.class)) != null;
    }

    public static boolean isForeign(Field field) {
        return ((Foreign) field.getAnnotation(Foreign.class)) != null;
    }

    public static boolean isIgnore(Field field) {
        Ignore ignore = (Ignore) field.getAnnotation(Ignore.class);
        int modifiers = field.getModifiers();
        String name = field.getName();
        return (ignore != null && ignore.value()) || name.equals("rowID") || name.equals(AriaConfig.IGNORE_CLASS_KLASS) || name.equals(AriaConfig.IGNORE_CLASS_MONITOR) || field.isSynthetic() || Modifier.isStatic(modifiers) || Modifier.isFinal(modifiers);
    }

    public static boolean isMany(Field field) {
        return ((Many) field.getAnnotation(Many.class)) != null;
    }

    public static boolean isNoNull(Field field) {
        return ((NoNull) field.getAnnotation(NoNull.class)) != null;
    }

    public static boolean isOne(Field field) {
        return ((One) field.getAnnotation(One.class)) != null;
    }

    public static boolean isPrimary(Field field) {
        return ((Primary) field.getAnnotation(Primary.class)) != null;
    }

    public static boolean isUnique(Field field) {
        return ((Unique) field.getAnnotation(Unique.class)) != null;
    }

    public static boolean isWrapper(Class<? extends AbsDbWrapper> cls) {
        return ((Wrapper) cls.getAnnotation(Wrapper.class)) != null;
    }

    public static String list2Str(DbEntity dbEntity, Field field) throws IllegalAccessException {
        List list = (List) field.get(dbEntity);
        if (list == null || list.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            sb.append(it.next());
            sb.append("$$");
        }
        return sb.toString();
    }

    public static String map2Str(Map<String, String> map) {
        StringBuilder sb = new StringBuilder();
        for (String str : map.keySet()) {
            sb.append(CommonUtil.encryptBASE64(str));
            sb.append("$");
            sb.append(CommonUtil.encryptBASE64(map.get(str)));
            sb.append(",");
        }
        String string = sb.toString();
        if (!TextUtils.isEmpty(string)) {
            string = string.substring(0, string.length() - 1);
        }
        if (map.size() == 0) {
            return string;
        }
        return string + "_&_decode_&_";
    }

    public static List str2List(String str, Field field) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        String[] strArrSplit = str.split("\\$\\$");
        ArrayList arrayList = new ArrayList();
        Class listParamType = CommonUtil.getListParamType(field);
        if (listParamType != null) {
            String name = listParamType.getName();
            for (String str2 : strArrSplit) {
                arrayList.add(checkData(name, str2));
            }
        }
        return arrayList;
    }

    public static Map<String, String> str2Map(String str) {
        boolean z2;
        HashMap map = new HashMap();
        if (TextUtils.isEmpty(str)) {
            return map;
        }
        if (str.endsWith("_&_decode_&_")) {
            str = str.substring(0, str.length() - 12);
            z2 = true;
        } else {
            z2 = false;
        }
        for (String str2 : str.split(",")) {
            String[] strArrSplit = str2.split("\\$");
            if (z2) {
                map.put(CommonUtil.decryptBASE64(strArrSplit[0]), CommonUtil.decryptBASE64(strArrSplit[1]));
            } else {
                map.put(strArrSplit[0], strArrSplit[1]);
            }
        }
        return map;
    }

    public static boolean tableExists(SQLiteDatabase sQLiteDatabase, Class<? extends DbEntity> cls) {
        return tableExists(sQLiteDatabase, CommonUtil.getClassName((Class) cls));
    }

    public static boolean tableExists(SQLiteDatabase sQLiteDatabase, String str) {
        Cursor cursorRawQuery = null;
        try {
            try {
                cursorRawQuery = checkDb(sQLiteDatabase).rawQuery(String.format("SELECT COUNT(*) FROM sqlite_master WHERE type='table' AND name='%s'", str), null);
                if (cursorRawQuery != null && cursorRawQuery.moveToNext()) {
                    if (cursorRawQuery.getInt(0) > 0) {
                        return true;
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            return false;
        } finally {
            closeCursor(cursorRawQuery);
        }
    }
}
