package com.plv.thirdpart.litepal.crud;

import android.database.Cursor;
import com.plv.thirdpart.litepal.crud.model.AssociationsInfo;
import com.plv.thirdpart.litepal.tablemanager.Connector;
import com.plv.thirdpart.litepal.util.BaseUtility;
import com.plv.thirdpart.litepal.util.DBUtility;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

/* loaded from: classes5.dex */
public class Many2ManyAnalyzer extends AssociationsAnalyzer {
    private void addNewModelForAssociatedModel(Collection<LitePalSupport> collection, LitePalSupport litePalSupport) {
        if (collection.contains(litePalSupport)) {
            return;
        }
        collection.add(litePalSupport);
    }

    private void dealAssociatedModel(LitePalSupport litePalSupport, LitePalSupport litePalSupport2) {
        if (litePalSupport2.isSaved()) {
            litePalSupport.addAssociatedModelForJoinTable(litePalSupport2.getTableName(), litePalSupport2.getBaseObjId());
        }
    }

    private void declareAssociations(LitePalSupport litePalSupport, AssociationsInfo associationsInfo) {
        litePalSupport.addEmptyModelForJoinTable(getAssociatedTableName(associationsInfo));
    }

    private String getAssociatedTableName(AssociationsInfo associationsInfo) {
        return BaseUtility.changeCase(DBUtility.getTableNameByClassName(associationsInfo.getAssociatedClassName()));
    }

    private String getJoinTableName(LitePalSupport litePalSupport, LitePalSupport litePalSupport2) {
        return getIntermediateTableName(litePalSupport, litePalSupport2.getTableName());
    }

    private String getSelection(LitePalSupport litePalSupport, LitePalSupport litePalSupport2) {
        return getForeignKeyColumnName(litePalSupport.getTableName()) + " = ? and " + getForeignKeyColumnName(litePalSupport2.getTableName()) + " = ?";
    }

    private String[] getSelectionArgs(LitePalSupport litePalSupport, LitePalSupport litePalSupport2) {
        return new String[]{String.valueOf(litePalSupport.getBaseObjId()), String.valueOf(litePalSupport2.getBaseObjId())};
    }

    @Deprecated
    private boolean isDataExists(LitePalSupport litePalSupport, LitePalSupport litePalSupport2) {
        Cursor cursorQuery = null;
        try {
            cursorQuery = Connector.getDatabase().query(getJoinTableName(litePalSupport, litePalSupport2), null, getSelection(litePalSupport, litePalSupport2), getSelectionArgs(litePalSupport, litePalSupport2), null, null, null);
            return cursorQuery.getCount() > 0;
        } catch (Exception e2) {
            e2.printStackTrace();
            return true;
        } finally {
            cursorQuery.close();
        }
    }

    public void analyze(LitePalSupport litePalSupport, AssociationsInfo associationsInfo) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        Collection<LitePalSupport> associatedModels = getAssociatedModels(litePalSupport, associationsInfo);
        declareAssociations(litePalSupport, associationsInfo);
        if (associatedModels != null) {
            for (LitePalSupport litePalSupport2 : associatedModels) {
                Collection<LitePalSupport> collectionCheckAssociatedModelCollection = checkAssociatedModelCollection(getReverseAssociatedModels(litePalSupport2, associationsInfo), associationsInfo.getAssociateSelfFromOtherModel());
                addNewModelForAssociatedModel(collectionCheckAssociatedModelCollection, litePalSupport);
                setReverseAssociatedModels(litePalSupport2, associationsInfo, collectionCheckAssociatedModelCollection);
                dealAssociatedModel(litePalSupport, litePalSupport2);
            }
        }
    }
}
