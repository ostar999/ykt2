package com.plv.thirdpart.litepal.tablemanager.typechange;

import androidx.constraintlayout.core.motion.utils.TypedValues;

/* loaded from: classes5.dex */
public class BooleanOrm extends OrmChange {
    @Override // com.plv.thirdpart.litepal.tablemanager.typechange.OrmChange
    public String object2Relation(String str) {
        if (str == null) {
            return null;
        }
        if (str.equals(TypedValues.Custom.S_BOOLEAN) || str.equals("java.lang.Boolean")) {
            return TypedValues.Custom.S_INT;
        }
        return null;
    }
}
