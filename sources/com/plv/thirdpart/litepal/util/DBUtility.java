package com.plv.thirdpart.litepal.util;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Pair;
import cn.hutool.core.text.StrPool;
import com.plv.thirdpart.litepal.exceptions.DatabaseGenerateException;
import com.plv.thirdpart.litepal.tablemanager.model.ColumnModel;
import com.plv.thirdpart.litepal.tablemanager.model.TableModel;
import com.psychiatrygarden.utils.Constants;
import com.umeng.analytics.pro.aq;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes5.dex */
public class DBUtility {
    private static final String KEYWORDS_COLUMN_SUFFIX = "_lpcolumn";
    private static final String REG_COLLECTION = "\\s+(not\\s+)?(in)\\s*\\(";
    private static final String REG_FUZZY = "\\s+(not\\s+)?(like|between)\\s+";
    private static final String REG_OPERATOR = "\\s*(=|!=|<>|<|>)";
    private static final String SQLITE_KEYWORDS = ",abort,add,after,all,alter,and,as,asc,autoincrement,before,begin,between,by,cascade,check,collate,column,commit,conflict,constraint,create,cross,database,deferrable,deferred,delete,desc,distinct,drop,each,end,escape,except,exclusive,exists,foreign,from,glob,group,having,in,index,inner,insert,intersect,into,is,isnull,join,like,limit,match,natural,not,notnull,null,of,offset,on,or,order,outer,plan,pragma,primary,query,raise,references,regexp,reindex,release,rename,replace,restrict,right,rollback,row,savepoint,select,set,table,temp,temporary,then,to,transaction,trigger,union,unique,update,using,vacuum,values,view,virtual,when,where,";
    private static final String TAG = "DBUtility";

    private DBUtility() {
    }

    public static String convertOrderByClauseToValidName(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        String lowerCase = str.trim().toLowerCase(Locale.US);
        if (!lowerCase.contains(",")) {
            return convertOrderByItem(lowerCase);
        }
        String[] strArrSplit = lowerCase.split(",");
        StringBuilder sb = new StringBuilder();
        int length = strArrSplit.length;
        int i2 = 0;
        boolean z2 = false;
        while (i2 < length) {
            String str2 = strArrSplit[i2];
            if (z2) {
                sb.append(",");
            }
            sb.append(convertOrderByItem(str2));
            i2++;
            z2 = true;
        }
        return sb.toString();
    }

    private static String convertOrderByItem(String str) {
        String str2 = "";
        if (str.endsWith(Constants.CIRCLE_ORDER_TYPE.ORDER_ASC)) {
            str = str.replace(Constants.CIRCLE_ORDER_TYPE.ORDER_ASC, "").trim();
            str2 = " asc";
        } else if (str.endsWith("desc")) {
            str = str.replace("desc", "").trim();
            str2 = " desc";
        }
        return convertToValidColumnName(str) + str2;
    }

    public static String[] convertSelectClauseToValidNames(String[] strArr) {
        if (strArr == null || strArr.length <= 0) {
            return null;
        }
        String[] strArr2 = new String[strArr.length];
        for (int i2 = 0; i2 < strArr.length; i2++) {
            strArr2[i2] = convertToValidColumnName(strArr[i2]);
        }
        return strArr2;
    }

    public static String convertToValidColumnName(String str) {
        if (!isFieldNameConflictWithSQLiteKeywords(str)) {
            return str;
        }
        return str + KEYWORDS_COLUMN_SUFFIX;
    }

    public static String convertWhereClauseToColumnName(String str) {
        if (!TextUtils.isEmpty(str)) {
            try {
                StringBuffer stringBuffer = new StringBuffer();
                Matcher matcher = Pattern.compile("(\\w+\\s*(=|!=|<>|<|>)|\\w+\\s+(not\\s+)?(like|between)\\s+|\\w+\\s+(not\\s+)?(in)\\s*\\()").matcher(str);
                while (matcher.find()) {
                    String strGroup = matcher.group();
                    String strReplaceAll = strGroup.replaceAll("(\\s*(=|!=|<>|<|>)|\\s+(not\\s+)?(like|between)\\s+|\\s+(not\\s+)?(in)\\s*\\()", "");
                    String strReplace = strGroup.replace(strReplaceAll, "");
                    matcher.appendReplacement(stringBuffer, convertToValidColumnName(strReplaceAll) + strReplace);
                }
                matcher.appendTail(stringBuffer);
                return stringBuffer.toString();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return str;
    }

    public static List<String> findAllTableNames(SQLiteDatabase sQLiteDatabase) {
        ArrayList arrayList = new ArrayList();
        Cursor cursorRawQuery = null;
        try {
            try {
                cursorRawQuery = sQLiteDatabase.rawQuery("select * from sqlite_master where type = ?", new String[]{"table"});
                if (cursorRawQuery.moveToFirst()) {
                    do {
                        String string = cursorRawQuery.getString(cursorRawQuery.getColumnIndexOrThrow("tbl_name"));
                        if (!arrayList.contains(string)) {
                            arrayList.add(string);
                        }
                    } while (cursorRawQuery.moveToNext());
                }
                cursorRawQuery.close();
                return arrayList;
            } catch (Exception e2) {
                e2.printStackTrace();
                throw new DatabaseGenerateException(e2.getMessage());
            }
        } catch (Throwable th) {
            if (cursorRawQuery != null) {
                cursorRawQuery.close();
            }
            throw th;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:43:0x00ab  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x00b0  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static android.util.Pair<java.util.Set<java.lang.String>, java.util.Set<java.lang.String>> findIndexedColumns(java.lang.String r10, android.database.sqlite.SQLiteDatabase r11) throws java.lang.Throwable {
        /*
            java.lang.String r0 = "name"
            java.lang.String r1 = ")"
            java.util.HashSet r2 = new java.util.HashSet
            r2.<init>()
            java.util.HashSet r3 = new java.util.HashSet
            r3.<init>()
            r4 = 0
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L96 java.lang.Exception -> L99
            r5.<init>()     // Catch: java.lang.Throwable -> L96 java.lang.Exception -> L99
            java.lang.String r6 = "pragma index_list("
            r5.append(r6)     // Catch: java.lang.Throwable -> L96 java.lang.Exception -> L99
            r5.append(r10)     // Catch: java.lang.Throwable -> L96 java.lang.Exception -> L99
            r5.append(r1)     // Catch: java.lang.Throwable -> L96 java.lang.Exception -> L99
            java.lang.String r10 = r5.toString()     // Catch: java.lang.Throwable -> L96 java.lang.Exception -> L99
            android.database.Cursor r10 = r11.rawQuery(r10, r4)     // Catch: java.lang.Throwable -> L96 java.lang.Exception -> L99
            boolean r5 = r10.moveToFirst()     // Catch: java.lang.Throwable -> L8e java.lang.Exception -> L92
            if (r5 == 0) goto L80
            r5 = r4
        L2e:
            java.lang.String r6 = "unique"
            int r6 = r10.getColumnIndexOrThrow(r6)     // Catch: java.lang.Throwable -> L7c java.lang.Exception -> L7e
            int r6 = r10.getInt(r6)     // Catch: java.lang.Throwable -> L7c java.lang.Exception -> L7e
            r7 = 1
            if (r6 != r7) goto L3c
            goto L3d
        L3c:
            r7 = 0
        L3d:
            int r6 = r10.getColumnIndexOrThrow(r0)     // Catch: java.lang.Throwable -> L7c java.lang.Exception -> L7e
            java.lang.String r6 = r10.getString(r6)     // Catch: java.lang.Throwable -> L7c java.lang.Exception -> L7e
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L7c java.lang.Exception -> L7e
            r8.<init>()     // Catch: java.lang.Throwable -> L7c java.lang.Exception -> L7e
            java.lang.String r9 = "pragma index_info("
            r8.append(r9)     // Catch: java.lang.Throwable -> L7c java.lang.Exception -> L7e
            r8.append(r6)     // Catch: java.lang.Throwable -> L7c java.lang.Exception -> L7e
            r8.append(r1)     // Catch: java.lang.Throwable -> L7c java.lang.Exception -> L7e
            java.lang.String r6 = r8.toString()     // Catch: java.lang.Throwable -> L7c java.lang.Exception -> L7e
            android.database.Cursor r5 = r11.rawQuery(r6, r4)     // Catch: java.lang.Throwable -> L7c java.lang.Exception -> L7e
            boolean r6 = r5.moveToFirst()     // Catch: java.lang.Throwable -> L7c java.lang.Exception -> L7e
            if (r6 == 0) goto L74
            int r6 = r5.getColumnIndexOrThrow(r0)     // Catch: java.lang.Throwable -> L7c java.lang.Exception -> L7e
            java.lang.String r6 = r5.getString(r6)     // Catch: java.lang.Throwable -> L7c java.lang.Exception -> L7e
            if (r7 == 0) goto L71
            r3.add(r6)     // Catch: java.lang.Throwable -> L7c java.lang.Exception -> L7e
            goto L74
        L71:
            r2.add(r6)     // Catch: java.lang.Throwable -> L7c java.lang.Exception -> L7e
        L74:
            boolean r6 = r10.moveToNext()     // Catch: java.lang.Throwable -> L7c java.lang.Exception -> L7e
            if (r6 != 0) goto L2e
            r4 = r5
            goto L80
        L7c:
            r11 = move-exception
            goto L90
        L7e:
            r11 = move-exception
            goto L94
        L80:
            r10.close()
            if (r4 == 0) goto L88
            r4.close()
        L88:
            android.util.Pair r10 = new android.util.Pair
            r10.<init>(r2, r3)
            return r10
        L8e:
            r11 = move-exception
            r5 = r4
        L90:
            r4 = r10
            goto La9
        L92:
            r11 = move-exception
            r5 = r4
        L94:
            r4 = r10
            goto L9b
        L96:
            r11 = move-exception
            r5 = r4
            goto La9
        L99:
            r11 = move-exception
            r5 = r4
        L9b:
            r11.printStackTrace()     // Catch: java.lang.Throwable -> La8
            com.plv.thirdpart.litepal.exceptions.DatabaseGenerateException r10 = new com.plv.thirdpart.litepal.exceptions.DatabaseGenerateException     // Catch: java.lang.Throwable -> La8
            java.lang.String r11 = r11.getMessage()     // Catch: java.lang.Throwable -> La8
            r10.<init>(r11)     // Catch: java.lang.Throwable -> La8
            throw r10     // Catch: java.lang.Throwable -> La8
        La8:
            r11 = move-exception
        La9:
            if (r4 == 0) goto Lae
            r4.close()
        Lae:
            if (r5 == 0) goto Lb3
            r5.close()
        Lb3:
            throw r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.plv.thirdpart.litepal.util.DBUtility.findIndexedColumns(java.lang.String, android.database.sqlite.SQLiteDatabase):android.util.Pair");
    }

    public static TableModel findPragmaTableInfo(String str, SQLiteDatabase sQLiteDatabase) throws Throwable {
        if (!isTableExists(str, sQLiteDatabase)) {
            throw new DatabaseGenerateException(DatabaseGenerateException.TABLE_DOES_NOT_EXIST_WHEN_EXECUTING + str);
        }
        Pair<Set<String>, Set<String>> pairFindIndexedColumns = findIndexedColumns(str, sQLiteDatabase);
        Set set = (Set) pairFindIndexedColumns.first;
        Set set2 = (Set) pairFindIndexedColumns.second;
        TableModel tableModel = new TableModel();
        tableModel.setTableName(str);
        Cursor cursorRawQuery = null;
        try {
            try {
                cursorRawQuery = sQLiteDatabase.rawQuery("pragma table_info(" + str + ")", null);
                if (cursorRawQuery.moveToFirst()) {
                    do {
                        ColumnModel columnModel = new ColumnModel();
                        String string = cursorRawQuery.getString(cursorRawQuery.getColumnIndexOrThrow("name"));
                        String string2 = cursorRawQuery.getString(cursorRawQuery.getColumnIndexOrThrow("type"));
                        boolean z2 = true;
                        if (cursorRawQuery.getInt(cursorRawQuery.getColumnIndexOrThrow("notnull")) == 1) {
                            z2 = false;
                        }
                        boolean zContains = set2.contains(string);
                        boolean zContains2 = set.contains(string);
                        String string3 = cursorRawQuery.getString(cursorRawQuery.getColumnIndexOrThrow("dflt_value"));
                        columnModel.setColumnName(string);
                        columnModel.setColumnType(string2);
                        columnModel.setNullable(z2);
                        columnModel.setUnique(zContains);
                        columnModel.setHasIndex(zContains2);
                        columnModel.setDefaultValue(string3 != null ? string3.replace("'", "") : "");
                        tableModel.addColumnModel(columnModel);
                    } while (cursorRawQuery.moveToNext());
                }
                cursorRawQuery.close();
                return tableModel;
            } catch (Exception e2) {
                e2.printStackTrace();
                throw new DatabaseGenerateException(e2.getMessage());
            }
        } catch (Throwable th) {
            if (cursorRawQuery != null) {
                cursorRawQuery.close();
            }
            throw th;
        }
    }

    public static String getGenericTableName(String str, String str2) {
        return BaseUtility.changeCase(getTableNameByClassName(str) + StrPool.UNDERLINE + str2);
    }

    public static String getGenericValueIdColumnName(String str) {
        return BaseUtility.changeCase(getTableNameByClassName(str) + aq.f22519d);
    }

    public static String getIndexName(String str, String str2) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return null;
        }
        return str + StrPool.UNDERLINE + str2 + "_index";
    }

    public static String getIntermediateTableName(String str, String str2) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return null;
        }
        Locale locale = Locale.US;
        if (str.toLowerCase(locale).compareTo(str2.toLowerCase(locale)) <= 0) {
            return str + StrPool.UNDERLINE + str2;
        }
        return str2 + StrPool.UNDERLINE + str;
    }

    public static String getM2MSelfRefColumnName(Field field) {
        return BaseUtility.changeCase(field.getName() + aq.f22519d);
    }

    public static String getTableNameByClassName(String str) {
        if (TextUtils.isEmpty(str) || '.' == str.charAt(str.length() - 1)) {
            return null;
        }
        return str.substring(str.lastIndexOf(StrPool.DOT) + 1);
    }

    public static String getTableNameByForeignColumn(String str) {
        if (TextUtils.isEmpty(str) || !str.toLowerCase(Locale.US).endsWith(aq.f22519d)) {
            return null;
        }
        return str.substring(0, str.length() - 3);
    }

    public static List<String> getTableNameListByClassNameList(List<String> list) {
        ArrayList arrayList = new ArrayList();
        if (list != null && !list.isEmpty()) {
            Iterator<String> it = list.iterator();
            while (it.hasNext()) {
                arrayList.add(getTableNameByClassName(it.next()));
            }
        }
        return arrayList;
    }

    public static boolean isColumnExists(String str, String str2, SQLiteDatabase sQLiteDatabase) {
        boolean z2 = false;
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return false;
        }
        Cursor cursorRawQuery = null;
        try {
            try {
                cursorRawQuery = sQLiteDatabase.rawQuery("pragma table_info(" + str2 + ")", null);
                if (cursorRawQuery.moveToFirst()) {
                    while (true) {
                        if (str.equalsIgnoreCase(cursorRawQuery.getString(cursorRawQuery.getColumnIndexOrThrow("name")))) {
                            z2 = true;
                            break;
                        }
                        if (!cursorRawQuery.moveToNext()) {
                            break;
                        }
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
                if (cursorRawQuery != null) {
                    break;
                }
            }
            cursorRawQuery.close();
            return z2;
        } catch (Throwable th) {
            if (cursorRawQuery != null) {
                cursorRawQuery.close();
            }
            throw th;
        }
    }

    public static boolean isFieldNameConflictWithSQLiteKeywords(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return SQLITE_KEYWORDS.contains("," + str.toLowerCase(Locale.US) + ",");
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x003d, code lost:
    
        if (r0.getInt(r0.getColumnIndexOrThrow("type")) != 2) goto L24;
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x003f, code lost:
    
        r0.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x0043, code lost:
    
        return true;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean isGenericTable(java.lang.String r9, android.database.sqlite.SQLiteDatabase r10) {
        /*
            boolean r0 = android.text.TextUtils.isEmpty(r9)
            if (r0 != 0) goto L5d
            java.lang.String r0 = "[0-9a-zA-Z]+_[0-9a-zA-Z]+"
            boolean r0 = r9.matches(r0)
            if (r0 == 0) goto L5d
            r0 = 0
            java.lang.String r2 = "table_schema"
            r3 = 0
            r4 = 0
            r5 = 0
            r6 = 0
            r7 = 0
            r8 = 0
            r1 = r10
            android.database.Cursor r0 = r1.query(r2, r3, r4, r5, r6, r7, r8)     // Catch: java.lang.Throwable -> L4b java.lang.Exception -> L4d
            boolean r10 = r0.moveToFirst()     // Catch: java.lang.Throwable -> L4b java.lang.Exception -> L4d
            if (r10 == 0) goto L53
        L22:
            java.lang.String r10 = "name"
            int r10 = r0.getColumnIndexOrThrow(r10)     // Catch: java.lang.Throwable -> L4b java.lang.Exception -> L4d
            java.lang.String r10 = r0.getString(r10)     // Catch: java.lang.Throwable -> L4b java.lang.Exception -> L4d
            boolean r10 = r9.equalsIgnoreCase(r10)     // Catch: java.lang.Throwable -> L4b java.lang.Exception -> L4d
            if (r10 == 0) goto L44
            java.lang.String r9 = "type"
            int r9 = r0.getColumnIndexOrThrow(r9)     // Catch: java.lang.Throwable -> L4b java.lang.Exception -> L4d
            int r9 = r0.getInt(r9)     // Catch: java.lang.Throwable -> L4b java.lang.Exception -> L4d
            r10 = 2
            if (r9 != r10) goto L53
            r0.close()
            r9 = 1
            return r9
        L44:
            boolean r10 = r0.moveToNext()     // Catch: java.lang.Throwable -> L4b java.lang.Exception -> L4d
            if (r10 != 0) goto L22
            goto L53
        L4b:
            r9 = move-exception
            goto L57
        L4d:
            r9 = move-exception
            r9.printStackTrace()     // Catch: java.lang.Throwable -> L4b
            if (r0 == 0) goto L5d
        L53:
            r0.close()
            goto L5d
        L57:
            if (r0 == 0) goto L5c
            r0.close()
        L5c:
            throw r9
        L5d:
            r9 = 0
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.plv.thirdpart.litepal.util.DBUtility.isGenericTable(java.lang.String, android.database.sqlite.SQLiteDatabase):boolean");
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x003d, code lost:
    
        if (r0.getInt(r0.getColumnIndexOrThrow("type")) != 1) goto L24;
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x003f, code lost:
    
        r0.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x0042, code lost:
    
        return true;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean isIntermediateTable(java.lang.String r9, android.database.sqlite.SQLiteDatabase r10) {
        /*
            boolean r0 = android.text.TextUtils.isEmpty(r9)
            if (r0 != 0) goto L5c
            java.lang.String r0 = "[0-9a-zA-Z]+_[0-9a-zA-Z]+"
            boolean r0 = r9.matches(r0)
            if (r0 == 0) goto L5c
            r0 = 0
            java.lang.String r2 = "table_schema"
            r3 = 0
            r4 = 0
            r5 = 0
            r6 = 0
            r7 = 0
            r8 = 0
            r1 = r10
            android.database.Cursor r0 = r1.query(r2, r3, r4, r5, r6, r7, r8)     // Catch: java.lang.Throwable -> L4a java.lang.Exception -> L4c
            boolean r10 = r0.moveToFirst()     // Catch: java.lang.Throwable -> L4a java.lang.Exception -> L4c
            if (r10 == 0) goto L52
        L22:
            java.lang.String r10 = "name"
            int r10 = r0.getColumnIndexOrThrow(r10)     // Catch: java.lang.Throwable -> L4a java.lang.Exception -> L4c
            java.lang.String r10 = r0.getString(r10)     // Catch: java.lang.Throwable -> L4a java.lang.Exception -> L4c
            boolean r10 = r9.equalsIgnoreCase(r10)     // Catch: java.lang.Throwable -> L4a java.lang.Exception -> L4c
            if (r10 == 0) goto L43
            java.lang.String r9 = "type"
            int r9 = r0.getColumnIndexOrThrow(r9)     // Catch: java.lang.Throwable -> L4a java.lang.Exception -> L4c
            int r9 = r0.getInt(r9)     // Catch: java.lang.Throwable -> L4a java.lang.Exception -> L4c
            r10 = 1
            if (r9 != r10) goto L52
            r0.close()
            return r10
        L43:
            boolean r10 = r0.moveToNext()     // Catch: java.lang.Throwable -> L4a java.lang.Exception -> L4c
            if (r10 != 0) goto L22
            goto L52
        L4a:
            r9 = move-exception
            goto L56
        L4c:
            r9 = move-exception
            r9.printStackTrace()     // Catch: java.lang.Throwable -> L4a
            if (r0 == 0) goto L5c
        L52:
            r0.close()
            goto L5c
        L56:
            if (r0 == 0) goto L5b
            r0.close()
        L5b:
            throw r9
        L5c:
            r9 = 0
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.plv.thirdpart.litepal.util.DBUtility.isIntermediateTable(java.lang.String, android.database.sqlite.SQLiteDatabase):boolean");
    }

    public static boolean isTableExists(String str, SQLiteDatabase sQLiteDatabase) {
        try {
            return BaseUtility.containsIgnoreCases(findAllTableNames(sQLiteDatabase), str);
        } catch (Exception e2) {
            e2.printStackTrace();
            return false;
        }
    }
}
