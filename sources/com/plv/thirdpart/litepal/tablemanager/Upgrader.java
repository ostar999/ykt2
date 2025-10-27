package com.plv.thirdpart.litepal.tablemanager;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.plv.thirdpart.litepal.crud.model.AssociationsInfo;
import com.plv.thirdpart.litepal.tablemanager.model.ColumnModel;
import com.plv.thirdpart.litepal.tablemanager.model.TableModel;
import com.plv.thirdpart.litepal.util.DBUtility;
import com.plv.thirdpart.litepal.util.LitePalLog;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes5.dex */
public class Upgrader extends AssociationUpdater {
    private boolean hasConstraintChanged;
    protected TableModel mTableModel;
    protected TableModel mTableModelDB;

    private void addColumns(List<ColumnModel> list) throws SQLException {
        LitePalLog.d(AssociationUpdater.TAG, "do addColumn");
        execute(getAddColumnSQLs(list), this.mDb);
        Iterator<ColumnModel> it = list.iterator();
        while (it.hasNext()) {
            this.mTableModelDB.addColumnModel(it.next());
        }
    }

    private void changeColumnsConstraints() throws SQLException {
        if (this.hasConstraintChanged) {
            LitePalLog.d(AssociationUpdater.TAG, "do changeColumnsConstraints");
            execute(getChangeColumnsConstraintsSQL(), this.mDb);
        }
    }

    private void changeColumnsType(List<ColumnModel> list) throws SQLException {
        LitePalLog.d(AssociationUpdater.TAG, "do changeColumnsType");
        ArrayList arrayList = new ArrayList();
        if (list != null && !list.isEmpty()) {
            Iterator<ColumnModel> it = list.iterator();
            while (it.hasNext()) {
                arrayList.add(it.next().getColumnName());
            }
        }
        removeColumns(arrayList);
        addColumns(list);
    }

    private List<ColumnModel> findColumnTypesToChange() {
        ArrayList arrayList = new ArrayList();
        for (ColumnModel columnModel : this.mTableModelDB.getColumnModels()) {
            for (ColumnModel columnModel2 : this.mTableModel.getColumnModels()) {
                if (columnModel.getColumnName().equalsIgnoreCase(columnModel2.getColumnName())) {
                    if (!columnModel.getColumnType().equalsIgnoreCase(columnModel2.getColumnType()) && (!columnModel2.getColumnType().equalsIgnoreCase("blob") || !TextUtils.isEmpty(columnModel.getColumnType()))) {
                        arrayList.add(columnModel2);
                    }
                    if (!this.hasConstraintChanged) {
                        LitePalLog.d(AssociationUpdater.TAG, "default value db is:" + columnModel.getDefaultValue() + ", default value is:" + columnModel2.getDefaultValue());
                        if (columnModel.isNullable() != columnModel2.isNullable() || !columnModel.getDefaultValue().equalsIgnoreCase(columnModel2.getDefaultValue()) || columnModel.hasIndex() != columnModel2.hasIndex() || (columnModel.isUnique() && !columnModel2.isUnique())) {
                            this.hasConstraintChanged = true;
                        }
                    }
                }
            }
        }
        return arrayList;
    }

    private List<ColumnModel> findColumnsToAdd() {
        ArrayList arrayList = new ArrayList();
        for (ColumnModel columnModel : this.mTableModel.getColumnModels()) {
            if (!this.mTableModelDB.containsColumn(columnModel.getColumnName())) {
                arrayList.add(columnModel);
            }
        }
        return arrayList;
    }

    private List<String> findColumnsToRemove() {
        String tableName = this.mTableModel.getTableName();
        ArrayList arrayList = new ArrayList();
        Iterator<ColumnModel> it = this.mTableModelDB.getColumnModels().iterator();
        while (it.hasNext()) {
            String columnName = it.next().getColumnName();
            if (isNeedToRemove(columnName)) {
                arrayList.add(columnName);
            }
        }
        LitePalLog.d(AssociationUpdater.TAG, "remove columns from " + tableName + " >> " + arrayList);
        return arrayList;
    }

    private List<String> generateAddColumnSQLs(ColumnModel columnModel) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(generateAddColumnSQL(this.mTableModel.getTableName(), columnModel));
        if (columnModel.hasIndex()) {
            arrayList.add(generateCreateIndexSQL(this.mTableModel.getTableName(), columnModel));
        }
        return arrayList;
    }

    private List<String> generateAddForeignKeySQL() {
        ArrayList arrayList = new ArrayList();
        for (String str : getForeignKeyColumns(this.mTableModel)) {
            if (!this.mTableModel.containsColumn(str)) {
                ColumnModel columnModel = new ColumnModel();
                columnModel.setColumnName(str);
                columnModel.setColumnType(TypedValues.Custom.S_INT);
                arrayList.add(generateAddColumnSQL(this.mTableModel.getTableName(), columnModel));
            }
        }
        return arrayList;
    }

    private List<String> getAddColumnSQLs(List<ColumnModel> list) {
        ArrayList arrayList = new ArrayList();
        Iterator<ColumnModel> it = list.iterator();
        while (it.hasNext()) {
            arrayList.addAll(generateAddColumnSQLs(it.next()));
        }
        return arrayList;
    }

    private List<String> getChangeColumnsConstraintsSQL() {
        String strGenerateAlterToTempTableSQL = generateAlterToTempTableSQL(this.mTableModel.getTableName());
        String strGenerateCreateTableSQL = generateCreateTableSQL(this.mTableModel);
        List<String> listGenerateAddForeignKeySQL = generateAddForeignKeySQL();
        String strGenerateDataMigrationSQL = generateDataMigrationSQL(this.mTableModelDB);
        String strGenerateDropTempTableSQL = generateDropTempTableSQL(this.mTableModel.getTableName());
        List<String> listGenerateCreateIndexSQLs = generateCreateIndexSQLs(this.mTableModel);
        ArrayList arrayList = new ArrayList();
        arrayList.add(strGenerateAlterToTempTableSQL);
        arrayList.add(strGenerateCreateTableSQL);
        arrayList.addAll(listGenerateAddForeignKeySQL);
        arrayList.add(strGenerateDataMigrationSQL);
        arrayList.add(strGenerateDropTempTableSQL);
        arrayList.addAll(listGenerateCreateIndexSQLs);
        LitePalLog.d(AssociationUpdater.TAG, "generateChangeConstraintSQL >> ");
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            LitePalLog.d(AssociationUpdater.TAG, (String) it.next());
        }
        LitePalLog.d(AssociationUpdater.TAG, "<< generateChangeConstraintSQL");
        return arrayList;
    }

    private boolean hasNewUniqueOrNotNullColumn() {
        for (ColumnModel columnModel : this.mTableModel.getColumnModels()) {
            if (!columnModel.isIdColumn()) {
                ColumnModel columnModelByName = this.mTableModelDB.getColumnModelByName(columnModel.getColumnName());
                if (columnModel.isUnique() && (columnModelByName == null || !columnModelByName.isUnique())) {
                    return true;
                }
                if (columnModelByName != null && !columnModel.isNullable() && columnModelByName.isNullable()) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isNeedToRemove(String str) {
        return (!isRemovedFromClass(str) || isIdColumn(str) || isForeignKeyColumn(this.mTableModel, str)) ? false : true;
    }

    private boolean isRemovedFromClass(String str) {
        return !this.mTableModel.containsColumn(str);
    }

    private void removeColumns(List<String> list) throws SQLException {
        LitePalLog.d(AssociationUpdater.TAG, "do removeColumns " + list);
        removeColumns(list, this.mTableModel.getTableName());
        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            this.mTableModelDB.removeColumnModelByName(it.next());
        }
    }

    private void upgradeTable() throws Throwable {
        if (!hasNewUniqueOrNotNullColumn()) {
            this.hasConstraintChanged = false;
            removeColumns(findColumnsToRemove());
            addColumns(findColumnsToAdd());
            changeColumnsType(findColumnTypesToChange());
            changeColumnsConstraints();
            return;
        }
        createOrUpgradeTable(this.mTableModel, this.mDb, true);
        for (AssociationsInfo associationsInfo : getAssociationInfo(this.mTableModel.getClassName())) {
            if (associationsInfo.getAssociationType() == 2 || associationsInfo.getAssociationType() == 1) {
                if (associationsInfo.getClassHoldsForeignKey().equalsIgnoreCase(this.mTableModel.getClassName())) {
                    addForeignKeyColumn(this.mTableModel.getTableName(), DBUtility.getTableNameByClassName(associationsInfo.getAssociatedClassName()), this.mTableModel.getTableName(), this.mDb);
                }
            }
        }
    }

    @Override // com.plv.thirdpart.litepal.tablemanager.AssociationUpdater, com.plv.thirdpart.litepal.tablemanager.Creator, com.plv.thirdpart.litepal.tablemanager.AssociationCreator, com.plv.thirdpart.litepal.tablemanager.Generator
    public void createOrUpgradeTable(SQLiteDatabase sQLiteDatabase, boolean z2) {
        this.mDb = sQLiteDatabase;
        for (TableModel tableModel : getAllTableModels()) {
            this.mTableModel = tableModel;
            this.mTableModelDB = getTableModelFromDB(tableModel.getTableName());
            LitePalLog.d(AssociationUpdater.TAG, "createOrUpgradeTable: model is " + this.mTableModel.getTableName());
            upgradeTable();
        }
    }
}
