package com.plv.thirdpart.litepal.tablemanager;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import com.plv.thirdpart.litepal.tablemanager.model.TableModel;
import com.plv.thirdpart.litepal.util.BaseUtility;
import com.plv.thirdpart.litepal.util.Const;
import com.plv.thirdpart.litepal.util.LitePalLog;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes5.dex */
public class Dropper extends AssociationUpdater {
    private Collection<TableModel> mTableModels;

    private void dropTables() throws SQLException {
        List<String> listFindTablesToDrop = findTablesToDrop();
        dropTables(listFindTablesToDrop, this.mDb);
        clearCopyInTableSchema(listFindTablesToDrop);
    }

    private List<String> findTablesToDrop() {
        ArrayList arrayList = new ArrayList();
        Cursor cursorQuery = null;
        try {
            try {
                cursorQuery = this.mDb.query(Const.TableSchema.TABLE_NAME, null, null, null, null, null, null);
                if (cursorQuery.moveToFirst()) {
                    do {
                        String string = cursorQuery.getString(cursorQuery.getColumnIndexOrThrow("name"));
                        if (shouldDropThisTable(string, cursorQuery.getInt(cursorQuery.getColumnIndexOrThrow("type")))) {
                            LitePalLog.d(AssociationUpdater.TAG, "need to drop " + string);
                            arrayList.add(string);
                        }
                    } while (cursorQuery.moveToNext());
                }
            } catch (Exception e2) {
                e2.printStackTrace();
                if (cursorQuery != null) {
                }
            }
            cursorQuery.close();
            return arrayList;
        } catch (Throwable th) {
            if (cursorQuery != null) {
                cursorQuery.close();
            }
            throw th;
        }
    }

    private List<String> pickTableNamesFromTableModels() {
        ArrayList arrayList = new ArrayList();
        Iterator<TableModel> it = this.mTableModels.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().getTableName());
        }
        return arrayList;
    }

    private boolean shouldDropThisTable(String str, int i2) {
        return !BaseUtility.containsIgnoreCases(pickTableNamesFromTableModels(), str) && i2 == 0;
    }

    @Override // com.plv.thirdpart.litepal.tablemanager.AssociationUpdater, com.plv.thirdpart.litepal.tablemanager.Creator, com.plv.thirdpart.litepal.tablemanager.AssociationCreator, com.plv.thirdpart.litepal.tablemanager.Generator
    public void createOrUpgradeTable(SQLiteDatabase sQLiteDatabase, boolean z2) {
        this.mTableModels = getAllTableModels();
        this.mDb = sQLiteDatabase;
        dropTables();
    }
}
