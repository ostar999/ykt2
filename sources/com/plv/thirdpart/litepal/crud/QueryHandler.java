package com.plv.thirdpart.litepal.crud;

import android.database.sqlite.SQLiteDatabase;
import com.plv.thirdpart.litepal.util.BaseUtility;
import com.plv.thirdpart.litepal.util.DBUtility;
import java.util.List;

/* loaded from: classes5.dex */
public class QueryHandler extends DataHandler {
    public QueryHandler(SQLiteDatabase sQLiteDatabase) {
        this.mDatabase = sQLiteDatabase;
    }

    public double onAverage(String str, String str2, String[] strArr) {
        BaseUtility.checkConditionsCorrect(strArr);
        if (strArr != null && strArr.length > 0) {
            strArr[0] = DBUtility.convertWhereClauseToColumnName(strArr[0]);
        }
        return ((Double) mathQuery(str, new String[]{"avg(" + str2 + ")"}, strArr, Double.TYPE)).doubleValue();
    }

    public int onCount(String str, String[] strArr) {
        BaseUtility.checkConditionsCorrect(strArr);
        if (strArr != null && strArr.length > 0) {
            strArr[0] = DBUtility.convertWhereClauseToColumnName(strArr[0]);
        }
        return ((Integer) mathQuery(str, new String[]{"count(1)"}, strArr, Integer.TYPE)).intValue();
    }

    public <T> T onFind(Class<T> cls, long j2, boolean z2) {
        List<T> listQuery = query(cls, null, "id = ?", new String[]{String.valueOf(j2)}, null, null, null, null, getForeignKeyAssociations(cls.getName(), z2));
        if (listQuery.size() > 0) {
            return listQuery.get(0);
        }
        return null;
    }

    public <T> List<T> onFindAll(Class<T> cls, boolean z2, long... jArr) {
        return isAffectAllLines(jArr) ? query(cls, null, null, null, null, null, "id", null, getForeignKeyAssociations(cls.getName(), z2)) : query(cls, null, getWhereOfIdsWithOr(jArr), null, null, null, "id", null, getForeignKeyAssociations(cls.getName(), z2));
    }

    public <T> T onFindFirst(Class<T> cls, boolean z2) {
        List<T> listQuery = query(cls, null, null, null, null, null, "id", "1", getForeignKeyAssociations(cls.getName(), z2));
        if (listQuery.size() > 0) {
            return listQuery.get(0);
        }
        return null;
    }

    public <T> T onFindLast(Class<T> cls, boolean z2) {
        List<T> listQuery = query(cls, null, null, null, null, null, "id desc", "1", getForeignKeyAssociations(cls.getName(), z2));
        if (listQuery.size() > 0) {
            return listQuery.get(0);
        }
        return null;
    }

    public <T> T onMax(String str, String str2, String[] strArr, Class<T> cls) {
        BaseUtility.checkConditionsCorrect(strArr);
        if (strArr != null && strArr.length > 0) {
            strArr[0] = DBUtility.convertWhereClauseToColumnName(strArr[0]);
        }
        return (T) mathQuery(str, new String[]{"max(" + str2 + ")"}, strArr, cls);
    }

    public <T> T onMin(String str, String str2, String[] strArr, Class<T> cls) {
        BaseUtility.checkConditionsCorrect(strArr);
        if (strArr != null && strArr.length > 0) {
            strArr[0] = DBUtility.convertWhereClauseToColumnName(strArr[0]);
        }
        return (T) mathQuery(str, new String[]{"min(" + str2 + ")"}, strArr, cls);
    }

    public <T> T onSum(String str, String str2, String[] strArr, Class<T> cls) {
        BaseUtility.checkConditionsCorrect(strArr);
        if (strArr != null && strArr.length > 0) {
            strArr[0] = DBUtility.convertWhereClauseToColumnName(strArr[0]);
        }
        return (T) mathQuery(str, new String[]{"sum(" + str2 + ")"}, strArr, cls);
    }

    public <T> List<T> onFind(Class<T> cls, String[] strArr, String[] strArr2, String str, String str2, boolean z2) {
        BaseUtility.checkConditionsCorrect(strArr2);
        if (strArr2 != null && strArr2.length > 0) {
            strArr2[0] = DBUtility.convertWhereClauseToColumnName(strArr2[0]);
        }
        return query(cls, strArr, getWhereClause(strArr2), getWhereArgs(strArr2), null, null, DBUtility.convertOrderByClauseToValidName(str), str2, getForeignKeyAssociations(cls.getName(), z2));
    }
}
