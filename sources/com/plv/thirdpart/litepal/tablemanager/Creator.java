package com.plv.thirdpart.litepal.tablemanager;

import android.database.sqlite.SQLiteDatabase;
import com.plv.thirdpart.litepal.tablemanager.model.TableModel;
import com.plv.thirdpart.litepal.util.DBUtility;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes5.dex */
class Creator extends AssociationCreator {
    public static final String TAG = "Creator";

    private String generateDropTableSQL(TableModel tableModel) {
        return generateDropTableSQL(tableModel.getTableName());
    }

    @Override // com.plv.thirdpart.litepal.tablemanager.AssociationCreator, com.plv.thirdpart.litepal.tablemanager.Generator
    public void createOrUpgradeTable(SQLiteDatabase sQLiteDatabase, boolean z2) {
        Iterator<TableModel> it = getAllTableModels().iterator();
        while (it.hasNext()) {
            createOrUpgradeTable(it.next(), sQLiteDatabase, z2);
        }
    }

    public List<String> generateCreateIndexSQLs(TableModel tableModel) {
        return generateCreateIndexSQLs(tableModel.getTableName(), tableModel.getColumnModels());
    }

    public String generateCreateTableSQL(TableModel tableModel) {
        return generateCreateTableSQL(tableModel.getTableName(), tableModel.getColumnModels(), true);
    }

    public List<String> getCreateTableSQLs(TableModel tableModel, SQLiteDatabase sQLiteDatabase, boolean z2) {
        ArrayList arrayList = new ArrayList();
        if (z2) {
            arrayList.add(generateDropTableSQL(tableModel));
            arrayList.add(generateCreateTableSQL(tableModel));
        } else {
            if (DBUtility.isTableExists(tableModel.getTableName(), sQLiteDatabase)) {
                return null;
            }
            arrayList.add(generateCreateTableSQL(tableModel));
        }
        arrayList.addAll(generateCreateIndexSQLs(tableModel));
        return arrayList;
    }

    public void createOrUpgradeTable(TableModel tableModel, SQLiteDatabase sQLiteDatabase, boolean z2) throws Throwable {
        execute(getCreateTableSQLs(tableModel, sQLiteDatabase, z2), sQLiteDatabase);
        giveTableSchemaACopy(tableModel.getTableName(), 0, sQLiteDatabase);
    }
}
