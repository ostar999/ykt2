package com.plv.thirdpart.litepal.crud;

import com.plv.thirdpart.litepal.crud.model.AssociationsInfo;
import com.plv.thirdpart.litepal.util.DBUtility;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes5.dex */
public class One2OneAnalyzer extends AssociationsAnalyzer {
    private void bidirectionalCondition(LitePalSupport litePalSupport, LitePalSupport litePalSupport2) {
        if (litePalSupport2.isSaved()) {
            litePalSupport.addAssociatedModelWithFK(litePalSupport2.getTableName(), litePalSupport2.getBaseObjId());
            litePalSupport.addAssociatedModelWithoutFK(litePalSupport2.getTableName(), litePalSupport2.getBaseObjId());
        }
    }

    private void dealAssociatedModel(LitePalSupport litePalSupport, LitePalSupport litePalSupport2, AssociationsInfo associationsInfo) {
        if (associationsInfo.getAssociateSelfFromOtherModel() != null) {
            bidirectionalCondition(litePalSupport, litePalSupport2);
        } else {
            unidirectionalCondition(litePalSupport, litePalSupport2);
        }
    }

    private void unidirectionalCondition(LitePalSupport litePalSupport, LitePalSupport litePalSupport2) {
        dealsAssociationsOnTheSideWithoutFK(litePalSupport, litePalSupport2);
    }

    public void analyze(LitePalSupport litePalSupport, AssociationsInfo associationsInfo) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        LitePalSupport associatedModel = getAssociatedModel(litePalSupport, associationsInfo);
        if (associatedModel == null) {
            litePalSupport.addAssociatedTableNameToClearFK(DBUtility.getTableNameByClassName(associationsInfo.getAssociatedClassName()));
        } else {
            buildBidirectionalAssociations(litePalSupport, associatedModel, associationsInfo);
            dealAssociatedModel(litePalSupport, associatedModel, associationsInfo);
        }
    }
}
