package com.plv.thirdpart.litepal.tablemanager;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import com.plv.thirdpart.litepal.parser.LitePalAttr;
import com.plv.thirdpart.litepal.tablemanager.model.AssociationsModel;
import com.plv.thirdpart.litepal.tablemanager.model.ColumnModel;
import com.plv.thirdpart.litepal.tablemanager.model.GenericModel;
import com.plv.thirdpart.litepal.tablemanager.model.TableModel;
import com.plv.thirdpart.litepal.util.BaseUtility;
import com.plv.thirdpart.litepal.util.Const;
import com.plv.thirdpart.litepal.util.DBUtility;
import com.plv.thirdpart.litepal.util.LitePalLog;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes5.dex */
public abstract class AssociationUpdater extends Creator {
    public static final String TAG = "AssociationUpdater";
    private Collection<AssociationsModel> mAssociationModels;
    protected SQLiteDatabase mDb;

    private List<String> findForeignKeyToRemove(TableModel tableModel) {
        ArrayList arrayList = new ArrayList();
        List<String> foreignKeyColumns = getForeignKeyColumns(tableModel);
        String tableName = tableModel.getTableName();
        for (String str : foreignKeyColumns) {
            if (shouldDropForeignKey(tableName, DBUtility.getTableNameByForeignColumn(str))) {
                arrayList.add(str);
            }
        }
        LitePalLog.d(TAG, "findForeignKeyToRemove >> " + tableModel.getTableName() + " " + arrayList);
        return arrayList;
    }

    private List<String> findGenericTablesToDrop() {
        boolean z2;
        ArrayList arrayList = new ArrayList();
        for (String str : DBUtility.findAllTableNames(this.mDb)) {
            if (DBUtility.isGenericTable(str, this.mDb)) {
                Iterator<GenericModel> it = getGenericModels().iterator();
                while (true) {
                    if (!it.hasNext()) {
                        z2 = true;
                        break;
                    }
                    if (str.equalsIgnoreCase(it.next().getTableName())) {
                        z2 = false;
                        break;
                    }
                }
                if (z2) {
                    arrayList.add(str);
                }
            }
        }
        return arrayList;
    }

    private List<String> findIntermediateTablesToDrop() {
        ArrayList arrayList = new ArrayList();
        for (String str : DBUtility.findAllTableNames(this.mDb)) {
            if (DBUtility.isIntermediateTable(str, this.mDb)) {
                boolean z2 = true;
                for (AssociationsModel associationsModel : this.mAssociationModels) {
                    if (associationsModel.getAssociationType() == 3 && str.equalsIgnoreCase(DBUtility.getIntermediateTableName(associationsModel.getTableName(), associationsModel.getAssociatedTableName()))) {
                        z2 = false;
                    }
                }
                if (z2) {
                    arrayList.add(str);
                }
            }
        }
        LitePalLog.d(TAG, "findIntermediateTablesToDrop >> " + arrayList);
        return arrayList;
    }

    private String generateCreateNewTableSQL(Collection<String> collection, TableModel tableModel) {
        Iterator<String> it = collection.iterator();
        while (it.hasNext()) {
            tableModel.removeColumnModelByName(it.next());
        }
        return generateCreateTableSQL(tableModel);
    }

    private List<String> getRemoveColumnSQLs(Collection<String> collection, String str) {
        TableModel tableModelFromDB = getTableModelFromDB(str);
        String strGenerateAlterToTempTableSQL = generateAlterToTempTableSQL(str);
        LitePalLog.d(TAG, "generateRemoveColumnSQL >> " + strGenerateAlterToTempTableSQL);
        String strGenerateCreateNewTableSQL = generateCreateNewTableSQL(collection, tableModelFromDB);
        LitePalLog.d(TAG, "generateRemoveColumnSQL >> " + strGenerateCreateNewTableSQL);
        String strGenerateDataMigrationSQL = generateDataMigrationSQL(tableModelFromDB);
        LitePalLog.d(TAG, "generateRemoveColumnSQL >> " + strGenerateDataMigrationSQL);
        String strGenerateDropTempTableSQL = generateDropTempTableSQL(str);
        LitePalLog.d(TAG, "generateRemoveColumnSQL >> " + strGenerateDropTempTableSQL);
        List<String> listGenerateCreateIndexSQLs = generateCreateIndexSQLs(tableModelFromDB);
        ArrayList arrayList = new ArrayList();
        arrayList.add(strGenerateAlterToTempTableSQL);
        arrayList.add(strGenerateCreateNewTableSQL);
        arrayList.add(strGenerateDataMigrationSQL);
        arrayList.add(strGenerateDropTempTableSQL);
        arrayList.addAll(listGenerateCreateIndexSQLs);
        return arrayList;
    }

    private boolean isRelationCorrect(AssociationsModel associationsModel, String str, String str2) {
        return associationsModel.getTableName().equalsIgnoreCase(str) && associationsModel.getAssociatedTableName().equalsIgnoreCase(str2);
    }

    private void removeAssociations() throws SQLException {
        removeForeignKeyColumns();
        removeIntermediateTables();
        removeGenericTables();
    }

    private void removeForeignKeyColumns() throws SQLException {
        Iterator<String> it = LitePalAttr.getInstance().getClassNames().iterator();
        while (it.hasNext()) {
            TableModel tableModel = getTableModel(it.next());
            removeColumns(findForeignKeyToRemove(tableModel), tableModel.getTableName());
        }
    }

    private void removeGenericTables() throws SQLException {
        List<String> listFindGenericTablesToDrop = findGenericTablesToDrop();
        dropTables(listFindGenericTablesToDrop, this.mDb);
        clearCopyInTableSchema(listFindGenericTablesToDrop);
    }

    private void removeIntermediateTables() throws SQLException {
        List<String> listFindIntermediateTablesToDrop = findIntermediateTablesToDrop();
        dropTables(listFindIntermediateTablesToDrop, this.mDb);
        clearCopyInTableSchema(listFindIntermediateTablesToDrop);
    }

    private boolean shouldDropForeignKey(String str, String str2) {
        for (AssociationsModel associationsModel : this.mAssociationModels) {
            if (associationsModel.getAssociationType() != 1) {
                if (associationsModel.getAssociationType() == 2 && isRelationCorrect(associationsModel, str2, str)) {
                    return false;
                }
            } else if (!str.equalsIgnoreCase(associationsModel.getTableHoldsForeignKey())) {
                continue;
            } else if (associationsModel.getTableName().equalsIgnoreCase(str)) {
                if (isRelationCorrect(associationsModel, str, str2)) {
                    return false;
                }
            } else if (associationsModel.getAssociatedTableName().equalsIgnoreCase(str) && isRelationCorrect(associationsModel, str2, str)) {
                return false;
            }
        }
        return true;
    }

    @Override // com.plv.thirdpart.litepal.tablemanager.AssociationCreator, com.plv.thirdpart.litepal.tablemanager.Generator
    public void addOrUpdateAssociation(SQLiteDatabase sQLiteDatabase, boolean z2) {
        this.mAssociationModels = getAllAssociations();
        this.mDb = sQLiteDatabase;
        removeAssociations();
    }

    public void clearCopyInTableSchema(List<String> list) throws SQLException {
        if (list == null || list.isEmpty()) {
            return;
        }
        StringBuilder sb = new StringBuilder("delete from ");
        sb.append(Const.TableSchema.TABLE_NAME);
        sb.append(" where");
        boolean z2 = false;
        for (String str : list) {
            if (z2) {
                sb.append(" or ");
            }
            sb.append(" lower(");
            sb.append("name");
            sb.append(") ");
            sb.append("=");
            sb.append(" lower('");
            sb.append(str);
            sb.append("')");
            z2 = true;
        }
        LitePalLog.d(TAG, "clear table schema value sql is " + ((Object) sb));
        ArrayList arrayList = new ArrayList();
        arrayList.add(sb.toString());
        execute(arrayList, this.mDb);
    }

    @Override // com.plv.thirdpart.litepal.tablemanager.Creator, com.plv.thirdpart.litepal.tablemanager.AssociationCreator, com.plv.thirdpart.litepal.tablemanager.Generator
    public abstract void createOrUpgradeTable(SQLiteDatabase sQLiteDatabase, boolean z2);

    public void dropTables(List<String> list, SQLiteDatabase sQLiteDatabase) throws SQLException {
        if (list == null || list.isEmpty()) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < list.size(); i2++) {
            arrayList.add(generateDropTableSQL(list.get(i2)));
        }
        execute(arrayList, sQLiteDatabase);
    }

    public String generateAlterToTempTableSQL(String str) {
        return "alter table " + str + " rename to " + getTempTableName(str);
    }

    public String generateDataMigrationSQL(TableModel tableModel) {
        String tableName = tableModel.getTableName();
        Collection<ColumnModel> columnModels = tableModel.getColumnModels();
        if (columnModels.isEmpty()) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("insert into ");
        sb.append(tableName);
        sb.append("(");
        boolean z2 = false;
        boolean z3 = false;
        for (ColumnModel columnModel : columnModels) {
            if (z3) {
                sb.append(", ");
            }
            sb.append(columnModel.getColumnName());
            z3 = true;
        }
        sb.append(") ");
        sb.append("select ");
        for (ColumnModel columnModel2 : columnModels) {
            if (z2) {
                sb.append(", ");
            }
            sb.append(columnModel2.getColumnName());
            z2 = true;
        }
        sb.append(" from ");
        sb.append(getTempTableName(tableName));
        return sb.toString();
    }

    public String generateDropTempTableSQL(String str) {
        return generateDropTableSQL(getTempTableName(str));
    }

    public List<String> getForeignKeyColumns(TableModel tableModel) {
        ArrayList arrayList = new ArrayList();
        for (ColumnModel columnModel : getTableModelFromDB(tableModel.getTableName()).getColumnModels()) {
            String columnName = columnModel.getColumnName();
            if (isForeignKeyColumnFormat(columnModel.getColumnName()) && !tableModel.containsColumn(columnName)) {
                LitePalLog.d(TAG, "getForeignKeyColumnNames >> foreign key column is " + columnName);
                arrayList.add(columnName);
            }
        }
        return arrayList;
    }

    public TableModel getTableModelFromDB(String str) {
        return DBUtility.findPragmaTableInfo(str, this.mDb);
    }

    public String getTempTableName(String str) {
        return str + "_temp";
    }

    public boolean isForeignKeyColumn(TableModel tableModel, String str) {
        return BaseUtility.containsIgnoreCases(getForeignKeyColumns(tableModel), str);
    }

    public void removeColumns(Collection<String> collection, String str) throws SQLException {
        if (collection == null || collection.isEmpty()) {
            return;
        }
        execute(getRemoveColumnSQLs(collection, str), this.mDb);
    }
}
