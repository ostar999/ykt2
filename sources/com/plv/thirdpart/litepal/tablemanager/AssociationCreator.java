package com.plv.thirdpart.litepal.tablemanager;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.plv.thirdpart.litepal.exceptions.DatabaseGenerateException;
import com.plv.thirdpart.litepal.tablemanager.model.AssociationsModel;
import com.plv.thirdpart.litepal.tablemanager.model.ColumnModel;
import com.plv.thirdpart.litepal.tablemanager.model.GenericModel;
import com.plv.thirdpart.litepal.util.BaseUtility;
import com.plv.thirdpart.litepal.util.Const;
import com.plv.thirdpart.litepal.util.DBUtility;
import com.plv.thirdpart.litepal.util.LitePalLog;
import com.umeng.analytics.pro.aq;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

/* loaded from: classes5.dex */
public abstract class AssociationCreator extends Generator {
    private void addAssociations(Collection<AssociationsModel> collection, SQLiteDatabase sQLiteDatabase, boolean z2) throws Throwable {
        for (AssociationsModel associationsModel : collection) {
            if (2 == associationsModel.getAssociationType() || 1 == associationsModel.getAssociationType()) {
                addForeignKeyColumn(associationsModel.getTableName(), associationsModel.getAssociatedTableName(), associationsModel.getTableHoldsForeignKey(), sQLiteDatabase);
            } else if (3 == associationsModel.getAssociationType()) {
                createIntermediateTable(associationsModel.getTableName(), associationsModel.getAssociatedTableName(), sQLiteDatabase, z2);
            }
        }
        Iterator<GenericModel> it = getGenericModels().iterator();
        while (it.hasNext()) {
            createGenericTable(it.next(), sQLiteDatabase, z2);
        }
    }

    private void createGenericTable(GenericModel genericModel, SQLiteDatabase sQLiteDatabase, boolean z2) throws Throwable {
        String tableName = genericModel.getTableName();
        String valueColumnName = genericModel.getValueColumnName();
        String valueColumnType = genericModel.getValueColumnType();
        String valueIdColumnName = genericModel.getValueIdColumnName();
        ArrayList arrayList = new ArrayList();
        ColumnModel columnModel = new ColumnModel();
        columnModel.setColumnName(valueColumnName);
        columnModel.setColumnType(valueColumnType);
        ColumnModel columnModel2 = new ColumnModel();
        columnModel2.setColumnName(valueIdColumnName);
        columnModel2.setColumnType(TypedValues.Custom.S_INT);
        arrayList.add(columnModel);
        arrayList.add(columnModel2);
        List<String> arrayList2 = new ArrayList<>();
        if (!DBUtility.isTableExists(tableName, sQLiteDatabase)) {
            arrayList2.add(generateCreateTableSQL(tableName, arrayList, false));
        } else if (z2) {
            arrayList2.add(generateDropTableSQL(tableName));
            arrayList2.add(generateCreateTableSQL(tableName, arrayList, false));
        }
        execute(arrayList2, sQLiteDatabase);
        giveTableSchemaACopy(tableName, 2, sQLiteDatabase);
    }

    private void createIntermediateTable(String str, String str2, SQLiteDatabase sQLiteDatabase, boolean z2) throws Throwable {
        ArrayList arrayList = new ArrayList();
        ColumnModel columnModel = new ColumnModel();
        columnModel.setColumnName(str + aq.f22519d);
        columnModel.setColumnType(TypedValues.Custom.S_INT);
        ColumnModel columnModel2 = new ColumnModel();
        columnModel2.setColumnName(str2 + aq.f22519d);
        columnModel2.setColumnType(TypedValues.Custom.S_INT);
        arrayList.add(columnModel);
        arrayList.add(columnModel2);
        String intermediateTableName = DBUtility.getIntermediateTableName(str, str2);
        List<String> arrayList2 = new ArrayList<>();
        if (!DBUtility.isTableExists(intermediateTableName, sQLiteDatabase)) {
            arrayList2.add(generateCreateTableSQL(intermediateTableName, arrayList, false));
        } else if (z2) {
            arrayList2.add(generateDropTableSQL(intermediateTableName));
            arrayList2.add(generateCreateTableSQL(intermediateTableName, arrayList, false));
        }
        execute(arrayList2, sQLiteDatabase);
        giveTableSchemaACopy(intermediateTableName, 1, sQLiteDatabase);
    }

    private boolean isContainsOnlyIdField(Collection<ColumnModel> collection) {
        Iterator<ColumnModel> it = collection.iterator();
        while (it.hasNext()) {
            if (!it.next().isIdColumn()) {
                return false;
            }
        }
        return true;
    }

    private boolean isNeedtoGiveACopy(Cursor cursor, String str) {
        return (isValueExists(cursor, str) || isSpecialTable(str)) ? false : true;
    }

    private boolean isSpecialTable(String str) {
        return Const.TableSchema.TABLE_NAME.equalsIgnoreCase(str);
    }

    private boolean isValueExists(Cursor cursor, String str) {
        if (cursor.moveToFirst()) {
            while (!cursor.getString(cursor.getColumnIndexOrThrow("name")).equalsIgnoreCase(str)) {
                if (!cursor.moveToNext()) {
                }
            }
            return true;
        }
        return false;
    }

    public void addForeignKeyColumn(String str, String str2, String str3, SQLiteDatabase sQLiteDatabase) throws SQLException {
        if (!DBUtility.isTableExists(str, sQLiteDatabase)) {
            throw new DatabaseGenerateException(DatabaseGenerateException.TABLE_DOES_NOT_EXIST + str);
        }
        if (!DBUtility.isTableExists(str2, sQLiteDatabase)) {
            throw new DatabaseGenerateException(DatabaseGenerateException.TABLE_DOES_NOT_EXIST + str2);
        }
        String foreignKeyColumnName = str.equals(str3) ? getForeignKeyColumnName(str2) : str2.equals(str3) ? getForeignKeyColumnName(str) : null;
        if (DBUtility.isColumnExists(foreignKeyColumnName, str3, sQLiteDatabase)) {
            LitePalLog.d(Generator.TAG, "column " + foreignKeyColumnName + " is already exist, no need to add one");
            return;
        }
        ColumnModel columnModel = new ColumnModel();
        columnModel.setColumnName(foreignKeyColumnName);
        columnModel.setColumnType(TypedValues.Custom.S_INT);
        ArrayList arrayList = new ArrayList();
        arrayList.add(generateAddColumnSQL(str3, columnModel));
        execute(arrayList, sQLiteDatabase);
    }

    @Override // com.plv.thirdpart.litepal.tablemanager.Generator
    public void addOrUpdateAssociation(SQLiteDatabase sQLiteDatabase, boolean z2) {
        addAssociations(getAllAssociations(), sQLiteDatabase, z2);
    }

    @Override // com.plv.thirdpart.litepal.tablemanager.Generator
    public abstract void createOrUpgradeTable(SQLiteDatabase sQLiteDatabase, boolean z2);

    public String generateAddColumnSQL(String str, ColumnModel columnModel) {
        StringBuilder sb = new StringBuilder();
        sb.append("alter table ");
        sb.append(str);
        sb.append(" add column ");
        sb.append(columnModel.getColumnName());
        sb.append(" ");
        sb.append(columnModel.getColumnType());
        if (!columnModel.isNullable()) {
            sb.append(" not null");
        }
        if (columnModel.isUnique()) {
            sb.append(" unique");
        }
        String defaultValue = columnModel.getDefaultValue();
        if (!TextUtils.isEmpty(defaultValue)) {
            sb.append(" default ");
            sb.append(defaultValue);
        } else if (!columnModel.isNullable()) {
            if (TypedValues.Custom.S_INT.equalsIgnoreCase(columnModel.getColumnType())) {
                defaultValue = "0";
            } else if ("text".equalsIgnoreCase(columnModel.getColumnType())) {
                defaultValue = "''";
            } else if ("real".equalsIgnoreCase(columnModel.getColumnType())) {
                defaultValue = "0.0";
            }
            sb.append(" default ");
            sb.append(defaultValue);
        }
        LitePalLog.d(Generator.TAG, "add column sql is >> " + ((Object) sb));
        return sb.toString();
    }

    public String generateCreateIndexSQL(String str, ColumnModel columnModel) {
        StringBuilder sb = new StringBuilder();
        if (columnModel.hasIndex()) {
            sb.append("create index ");
            sb.append(DBUtility.getIndexName(str, columnModel.getColumnName()));
            sb.append(" on ");
            sb.append(str);
            sb.append(" (");
            sb.append(columnModel.getColumnName());
            sb.append(")");
            LitePalLog.d(Generator.TAG, "create table index sql is >> " + ((Object) sb));
        }
        return sb.toString();
    }

    public List<String> generateCreateIndexSQLs(String str, Collection<ColumnModel> collection) {
        ArrayList arrayList = new ArrayList();
        for (ColumnModel columnModel : collection) {
            if (columnModel.hasIndex()) {
                arrayList.add(generateCreateIndexSQL(str, columnModel));
            }
        }
        return arrayList;
    }

    public String generateCreateTableSQL(String str, Collection<ColumnModel> collection, boolean z2) {
        StringBuilder sb = new StringBuilder("create table ");
        sb.append(str);
        sb.append(" (");
        if (z2) {
            sb.append("id integer primary key autoincrement,");
        }
        if (isContainsOnlyIdField(collection)) {
            sb.deleteCharAt(sb.length() - 1);
        }
        boolean z3 = false;
        for (ColumnModel columnModel : collection) {
            if (!columnModel.isIdColumn()) {
                if (z3) {
                    sb.append(", ");
                }
                sb.append(columnModel.getColumnName());
                sb.append(" ");
                sb.append(columnModel.getColumnType());
                if (!columnModel.isNullable()) {
                    sb.append(" not null");
                }
                if (columnModel.isUnique()) {
                    sb.append(" unique");
                }
                String defaultValue = columnModel.getDefaultValue();
                if (!TextUtils.isEmpty(defaultValue)) {
                    sb.append(" default ");
                    sb.append(defaultValue);
                }
                z3 = true;
            }
        }
        sb.append(")");
        LitePalLog.d(Generator.TAG, "create table sql is >> " + ((Object) sb));
        return sb.toString();
    }

    public String generateDropTableSQL(String str) {
        return "drop table if exists " + str;
    }

    public void giveTableSchemaACopy(String str, int i2, SQLiteDatabase sQLiteDatabase) throws Throwable {
        StringBuilder sb = new StringBuilder("select * from ");
        sb.append(Const.TableSchema.TABLE_NAME);
        LitePalLog.d(Generator.TAG, "giveTableSchemaACopy SQL is >> " + ((Object) sb));
        Cursor cursor = null;
        try {
            try {
                Cursor cursorRawQuery = sQLiteDatabase.rawQuery(sb.toString(), null);
                try {
                    if (isNeedtoGiveACopy(cursorRawQuery, str)) {
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("name", BaseUtility.changeCase(str));
                        contentValues.put("type", Integer.valueOf(i2));
                        sQLiteDatabase.insert(Const.TableSchema.TABLE_NAME, null, contentValues);
                    }
                    if (cursorRawQuery != null) {
                        cursorRawQuery.close();
                    }
                } catch (Exception e2) {
                    e = e2;
                    cursor = cursorRawQuery;
                    e.printStackTrace();
                    if (cursor != null) {
                        cursor.close();
                    }
                } catch (Throwable th) {
                    th = th;
                    cursor = cursorRawQuery;
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

    public boolean isForeignKeyColumnFormat(String str) {
        return (TextUtils.isEmpty(str) || !str.toLowerCase(Locale.US).endsWith(aq.f22519d) || str.equalsIgnoreCase(aq.f22519d)) ? false : true;
    }
}
