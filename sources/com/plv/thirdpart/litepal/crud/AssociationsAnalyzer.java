package com.plv.thirdpart.litepal.crud;

import com.plv.thirdpart.litepal.crud.model.AssociationsInfo;
import com.plv.thirdpart.litepal.exceptions.LitePalSupportException;
import com.plv.thirdpart.litepal.util.DBUtility;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

/* loaded from: classes5.dex */
abstract class AssociationsAnalyzer extends DataHandler {
    private String getForeignKeyName(AssociationsInfo associationsInfo) {
        return getForeignKeyColumnName(DBUtility.getTableNameByClassName(associationsInfo.getAssociatedClassName()));
    }

    public void buildBidirectionalAssociations(LitePalSupport litePalSupport, LitePalSupport litePalSupport2, AssociationsInfo associationsInfo) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        setFieldValue(litePalSupport2, associationsInfo.getAssociateSelfFromOtherModel(), litePalSupport);
    }

    public Collection<LitePalSupport> checkAssociatedModelCollection(Collection<LitePalSupport> collection, Field field) {
        Collection<LitePalSupport> hashSet;
        if (isList(field.getType())) {
            hashSet = new ArrayList<>();
        } else {
            if (!isSet(field.getType())) {
                throw new LitePalSupportException(LitePalSupportException.WRONG_FIELD_TYPE_FOR_ASSOCIATIONS);
            }
            hashSet = new HashSet<>();
        }
        if (collection != null) {
            hashSet.addAll(collection);
        }
        return hashSet;
    }

    public void dealsAssociationsOnTheSideWithoutFK(LitePalSupport litePalSupport, LitePalSupport litePalSupport2) {
        if (litePalSupport2 != null) {
            if (litePalSupport2.isSaved()) {
                litePalSupport.addAssociatedModelWithFK(litePalSupport2.getTableName(), litePalSupport2.getBaseObjId());
            } else if (litePalSupport.isSaved()) {
                litePalSupport2.addAssociatedModelWithoutFK(litePalSupport.getTableName(), litePalSupport.getBaseObjId());
            }
        }
    }

    public Collection<LitePalSupport> getReverseAssociatedModels(LitePalSupport litePalSupport, AssociationsInfo associationsInfo) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        return (Collection) getFieldValue(litePalSupport, associationsInfo.getAssociateSelfFromOtherModel());
    }

    public void mightClearFKValue(LitePalSupport litePalSupport, AssociationsInfo associationsInfo) {
        litePalSupport.addFKNameToClearSelf(getForeignKeyName(associationsInfo));
    }

    public void setReverseAssociatedModels(LitePalSupport litePalSupport, AssociationsInfo associationsInfo, Collection<LitePalSupport> collection) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        setFieldValue(litePalSupport, associationsInfo.getAssociateSelfFromOtherModel(), collection);
    }
}
