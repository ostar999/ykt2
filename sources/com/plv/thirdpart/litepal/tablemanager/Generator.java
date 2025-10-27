package com.plv.thirdpart.litepal.tablemanager;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import com.plv.thirdpart.litepal.LitePalBase;
import com.plv.thirdpart.litepal.exceptions.DatabaseGenerateException;
import com.plv.thirdpart.litepal.parser.LitePalAttr;
import com.plv.thirdpart.litepal.tablemanager.model.AssociationsModel;
import com.plv.thirdpart.litepal.tablemanager.model.TableModel;
import com.plv.thirdpart.litepal.util.BaseUtility;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes5.dex */
public abstract class Generator extends LitePalBase {
    public static final String TAG = "Generator";
    private Collection<AssociationsModel> mAllRelationModels;
    private Collection<TableModel> mTableModels;

    private static void addAssociation(SQLiteDatabase sQLiteDatabase, boolean z2) {
        new Creator().addOrUpdateAssociation(sQLiteDatabase, z2);
    }

    private boolean canUseCache() {
        Collection<TableModel> collection = this.mTableModels;
        return collection != null && collection.size() == LitePalAttr.getInstance().getClassNames().size();
    }

    private static void create(SQLiteDatabase sQLiteDatabase, boolean z2) {
        new Creator().createOrUpgradeTable(sQLiteDatabase, z2);
    }

    private static void drop(SQLiteDatabase sQLiteDatabase) {
        new Dropper().createOrUpgradeTable(sQLiteDatabase, false);
    }

    private static void updateAssociations(SQLiteDatabase sQLiteDatabase) {
        new Upgrader().addOrUpdateAssociation(sQLiteDatabase, false);
    }

    public static void upgrade(SQLiteDatabase sQLiteDatabase) {
        drop(sQLiteDatabase);
        create(sQLiteDatabase, false);
        updateAssociations(sQLiteDatabase);
        upgradeTables(sQLiteDatabase);
        addAssociation(sQLiteDatabase, false);
    }

    private static void upgradeTables(SQLiteDatabase sQLiteDatabase) {
        new Upgrader().createOrUpgradeTable(sQLiteDatabase, false);
    }

    public abstract void addOrUpdateAssociation(SQLiteDatabase sQLiteDatabase, boolean z2);

    public abstract void createOrUpgradeTable(SQLiteDatabase sQLiteDatabase, boolean z2);

    public void execute(List<String> list, SQLiteDatabase sQLiteDatabase) throws SQLException {
        String strChangeCase = "";
        if (list != null) {
            try {
                if (list.isEmpty()) {
                    return;
                }
                for (String str : list) {
                    if (!TextUtils.isEmpty(str)) {
                        strChangeCase = BaseUtility.changeCase(str);
                        sQLiteDatabase.execSQL(strChangeCase);
                    }
                }
            } catch (SQLException unused) {
                throw new DatabaseGenerateException(DatabaseGenerateException.SQL_ERROR + strChangeCase);
            }
        }
    }

    public Collection<AssociationsModel> getAllAssociations() {
        Collection<AssociationsModel> collection = this.mAllRelationModels;
        if (collection == null || collection.isEmpty()) {
            this.mAllRelationModels = getAssociations(LitePalAttr.getInstance().getClassNames());
        }
        return this.mAllRelationModels;
    }

    public Collection<TableModel> getAllTableModels() {
        if (this.mTableModels == null) {
            this.mTableModels = new ArrayList();
        }
        if (!canUseCache()) {
            this.mTableModels.clear();
            Iterator<String> it = LitePalAttr.getInstance().getClassNames().iterator();
            while (it.hasNext()) {
                this.mTableModels.add(getTableModel(it.next()));
            }
        }
        return this.mTableModels;
    }

    public static void create(SQLiteDatabase sQLiteDatabase) {
        create(sQLiteDatabase, true);
        addAssociation(sQLiteDatabase, true);
    }
}
