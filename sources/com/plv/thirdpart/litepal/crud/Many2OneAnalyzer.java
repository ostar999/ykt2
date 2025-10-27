package com.plv.thirdpart.litepal.crud;

import com.plv.thirdpart.litepal.crud.model.AssociationsInfo;
import com.plv.thirdpart.litepal.util.DBUtility;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

/* loaded from: classes5.dex */
class Many2OneAnalyzer extends AssociationsAnalyzer {
    private void analyzeManySide(LitePalSupport litePalSupport, AssociationsInfo associationsInfo) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        LitePalSupport associatedModel = getAssociatedModel(litePalSupport, associationsInfo);
        if (associatedModel == null) {
            mightClearFKValue(litePalSupport, associationsInfo);
            return;
        }
        Collection<LitePalSupport> collectionCheckAssociatedModelCollection = checkAssociatedModelCollection(getReverseAssociatedModels(associatedModel, associationsInfo), associationsInfo.getAssociateSelfFromOtherModel());
        setReverseAssociatedModels(associatedModel, associationsInfo, collectionCheckAssociatedModelCollection);
        dealAssociatedModelOnManySide(collectionCheckAssociatedModelCollection, litePalSupport, associatedModel);
    }

    private void analyzeOneSide(LitePalSupport litePalSupport, AssociationsInfo associationsInfo) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        Collection<LitePalSupport> associatedModels = getAssociatedModels(litePalSupport, associationsInfo);
        if (associatedModels == null || associatedModels.isEmpty()) {
            litePalSupport.addAssociatedTableNameToClearFK(DBUtility.getTableNameByClassName(associationsInfo.getAssociatedClassName()));
            return;
        }
        for (LitePalSupport litePalSupport2 : associatedModels) {
            buildBidirectionalAssociations(litePalSupport, litePalSupport2, associationsInfo);
            dealAssociatedModelOnOneSide(litePalSupport, litePalSupport2);
        }
    }

    private void dealAssociatedModelOnManySide(Collection<LitePalSupport> collection, LitePalSupport litePalSupport, LitePalSupport litePalSupport2) {
        if (!collection.contains(litePalSupport)) {
            collection.add(litePalSupport);
        }
        if (litePalSupport2.isSaved()) {
            litePalSupport.addAssociatedModelWithoutFK(litePalSupport2.getTableName(), litePalSupport2.getBaseObjId());
        }
    }

    private void dealAssociatedModelOnOneSide(LitePalSupport litePalSupport, LitePalSupport litePalSupport2) {
        dealsAssociationsOnTheSideWithoutFK(litePalSupport, litePalSupport2);
    }

    public void analyze(LitePalSupport litePalSupport, AssociationsInfo associationsInfo) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        if (litePalSupport.getClassName().equals(associationsInfo.getClassHoldsForeignKey())) {
            analyzeManySide(litePalSupport, associationsInfo);
        } else {
            analyzeOneSide(litePalSupport, associationsInfo);
        }
    }
}
