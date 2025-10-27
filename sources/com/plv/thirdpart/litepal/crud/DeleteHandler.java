package com.plv.thirdpart.litepal.crud;

import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import com.plv.thirdpart.litepal.Operator;
import com.plv.thirdpart.litepal.crud.model.AssociationsInfo;
import com.plv.thirdpart.litepal.exceptions.LitePalSupportException;
import com.plv.thirdpart.litepal.util.BaseUtility;
import com.plv.thirdpart.litepal.util.DBUtility;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes5.dex */
public class DeleteHandler extends DataHandler {
    private List<String> foreignKeyTableToDelete;

    public DeleteHandler(SQLiteDatabase sQLiteDatabase) {
        this.mDatabase = sQLiteDatabase;
    }

    private void analyzeAssociations(Class<?> cls) {
        for (AssociationsInfo associationsInfo : getAssociationInfo(cls.getName())) {
            String tableNameByClassName = DBUtility.getTableNameByClassName(associationsInfo.getAssociatedClassName());
            if (associationsInfo.getAssociationType() == 2 || associationsInfo.getAssociationType() == 1) {
                if (!cls.getName().equals(associationsInfo.getClassHoldsForeignKey())) {
                    getForeignKeyTableToDelete().add(tableNameByClassName);
                }
            } else if (associationsInfo.getAssociationType() == 3) {
                getForeignKeyTableToDelete().add(BaseUtility.changeCase(DBUtility.getIntermediateTableName(getTableName(cls), tableNameByClassName)));
            }
        }
    }

    private String buildConditionString(String... strArr) {
        int length = strArr.length - 1;
        int i2 = 0;
        String strReplaceFirst = strArr[0];
        while (i2 < length) {
            StringBuilder sb = new StringBuilder();
            sb.append("'");
            i2++;
            sb.append(strArr[i2]);
            sb.append("'");
            strReplaceFirst = strReplaceFirst.replaceFirst("\\?", sb.toString());
        }
        return strReplaceFirst;
    }

    private void clearAssociatedModelSaveState(LitePalSupport litePalSupport, Collection<AssociationsInfo> collection) {
        LitePalSupport associatedModel;
        try {
            for (AssociationsInfo associationsInfo : collection) {
                if (associationsInfo.getAssociationType() == 2 && !litePalSupport.getClassName().equals(associationsInfo.getClassHoldsForeignKey())) {
                    Collection<LitePalSupport> associatedModels = getAssociatedModels(litePalSupport, associationsInfo);
                    if (associatedModels != null && !associatedModels.isEmpty()) {
                        for (LitePalSupport litePalSupport2 : associatedModels) {
                            if (litePalSupport2 != null) {
                                litePalSupport2.clearSavedState();
                            }
                        }
                    }
                } else if (associationsInfo.getAssociationType() == 1 && (associatedModel = getAssociatedModel(litePalSupport, associationsInfo)) != null) {
                    associatedModel.clearSavedState();
                }
            }
        } catch (Exception e2) {
            throw new LitePalSupportException(e2.getMessage(), e2);
        }
    }

    private int deleteAllCascade(Class<?> cls, String... strArr) {
        int iDelete = 0;
        for (String str : getForeignKeyTableToDelete()) {
            String tableName = getTableName(cls);
            String foreignKeyColumnName = getForeignKeyColumnName(tableName);
            StringBuilder sb = new StringBuilder();
            sb.append(foreignKeyColumnName);
            sb.append(" in (select id from ");
            sb.append(tableName);
            if (strArr != null && strArr.length > 0) {
                sb.append(" where ");
                sb.append(buildConditionString(strArr));
            }
            sb.append(")");
            iDelete += this.mDatabase.delete(str, BaseUtility.changeCase(sb.toString()), null);
        }
        return iDelete;
    }

    private int deleteAssociatedForeignKeyRows(LitePalSupport litePalSupport) {
        int iDelete = 0;
        for (String str : litePalSupport.getAssociatedModelsMapWithFK().keySet()) {
            String foreignKeyColumnName = getForeignKeyColumnName(litePalSupport.getTableName());
            iDelete += this.mDatabase.delete(str, foreignKeyColumnName + " = " + litePalSupport.getBaseObjId(), null);
        }
        return iDelete;
    }

    private int deleteAssociatedJoinTableRows(LitePalSupport litePalSupport) {
        Iterator<String> it = litePalSupport.getAssociatedModelsMapForJoinTable().keySet().iterator();
        int iDelete = 0;
        while (it.hasNext()) {
            String intermediateTableName = DBUtility.getIntermediateTableName(litePalSupport.getTableName(), it.next());
            String foreignKeyColumnName = getForeignKeyColumnName(litePalSupport.getTableName());
            iDelete += this.mDatabase.delete(intermediateTableName, foreignKeyColumnName + " = " + litePalSupport.getBaseObjId(), null);
        }
        return iDelete;
    }

    private int deleteCascade(Class<?> cls, long j2) {
        int iDelete = 0;
        for (String str : getForeignKeyTableToDelete()) {
            String foreignKeyColumnName = getForeignKeyColumnName(getTableName(cls));
            iDelete += this.mDatabase.delete(str, foreignKeyColumnName + " = " + j2, null);
        }
        return iDelete;
    }

    private void deleteGenericData(Class<?> cls, List<Field> list, long... jArr) {
        int i2;
        Iterator<Field> it = list.iterator();
        while (it.hasNext()) {
            String genericTableName = DBUtility.getGenericTableName(cls.getName(), it.next().getName());
            String genericValueIdColumnName = DBUtility.getGenericValueIdColumnName(cls.getName());
            int length = jArr.length;
            int i3 = (length - 1) / 500;
            int i4 = 0;
            while (i4 <= i3) {
                StringBuilder sb = new StringBuilder();
                int i5 = 500 * i4;
                boolean z2 = false;
                while (true) {
                    i2 = i4 + 1;
                    if (i5 >= 500 * i2 || i5 >= length) {
                        break;
                    }
                    long j2 = jArr[i5];
                    if (z2) {
                        sb.append(" or ");
                    }
                    sb.append(genericValueIdColumnName);
                    sb.append(" = ");
                    sb.append(j2);
                    i5++;
                    z2 = true;
                }
                if (!TextUtils.isEmpty(sb.toString())) {
                    this.mDatabase.delete(genericTableName, sb.toString(), null);
                }
                i4 = i2;
            }
        }
    }

    private List<String> getForeignKeyTableToDelete() {
        if (this.foreignKeyTableToDelete == null) {
            this.foreignKeyTableToDelete = new ArrayList();
        }
        return this.foreignKeyTableToDelete;
    }

    public int onDelete(LitePalSupport litePalSupport) {
        if (!litePalSupport.isSaved()) {
            return 0;
        }
        deleteGenericData(litePalSupport.getClass(), getSupportedGenericFields(litePalSupport.getClassName()), litePalSupport.getBaseObjId());
        Collection<AssociationsInfo> collectionAnalyzeAssociations = analyzeAssociations(litePalSupport);
        int iDeleteCascade = deleteCascade(litePalSupport) + this.mDatabase.delete(litePalSupport.getTableName(), "id = " + litePalSupport.getBaseObjId(), null);
        clearAssociatedModelSaveState(litePalSupport, collectionAnalyzeAssociations);
        return iDeleteCascade;
    }

    public int onDeleteAll(String str, String... strArr) {
        BaseUtility.checkConditionsCorrect(strArr);
        if (strArr != null && strArr.length > 0) {
            strArr[0] = DBUtility.convertWhereClauseToColumnName(strArr[0]);
        }
        return this.mDatabase.delete(str, getWhereClause(strArr), getWhereArgs(strArr));
    }

    private int deleteCascade(LitePalSupport litePalSupport) {
        return deleteAssociatedForeignKeyRows(litePalSupport) + deleteAssociatedJoinTableRows(litePalSupport);
    }

    public int onDeleteAll(Class<?> cls, String... strArr) {
        BaseUtility.checkConditionsCorrect(strArr);
        if (strArr != null && strArr.length > 0) {
            strArr[0] = DBUtility.convertWhereClauseToColumnName(strArr[0]);
        }
        List<Field> supportedGenericFields = getSupportedGenericFields(cls.getName());
        if (!supportedGenericFields.isEmpty()) {
            List listFind = Operator.select("id").where(strArr).find(cls);
            if (listFind.size() > 0) {
                int size = listFind.size();
                long[] jArr = new long[size];
                for (int i2 = 0; i2 < size; i2++) {
                    jArr[i2] = ((LitePalSupport) listFind.get(i2)).getBaseObjId();
                }
                deleteGenericData(cls, supportedGenericFields, jArr);
            }
        }
        analyzeAssociations(cls);
        int iDeleteAllCascade = deleteAllCascade(cls, strArr) + this.mDatabase.delete(getTableName(cls), getWhereClause(strArr), getWhereArgs(strArr));
        getForeignKeyTableToDelete().clear();
        return iDeleteAllCascade;
    }

    public int onDelete(Class<?> cls, long j2) {
        deleteGenericData(cls, getSupportedGenericFields(cls.getName()), j2);
        analyzeAssociations(cls);
        int iDeleteCascade = deleteCascade(cls, j2) + this.mDatabase.delete(getTableName(cls), "id = " + j2, null);
        getForeignKeyTableToDelete().clear();
        return iDeleteCascade;
    }

    private Collection<AssociationsInfo> analyzeAssociations(LitePalSupport litePalSupport) {
        try {
            Collection<AssociationsInfo> associationInfo = getAssociationInfo(litePalSupport.getClassName());
            analyzeAssociatedModels(litePalSupport, associationInfo);
            return associationInfo;
        } catch (Exception e2) {
            throw new LitePalSupportException(e2.getMessage(), e2);
        }
    }
}
