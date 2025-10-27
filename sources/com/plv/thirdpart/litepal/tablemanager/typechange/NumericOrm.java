package com.plv.thirdpart.litepal.tablemanager.typechange;

import androidx.constraintlayout.core.motion.utils.TypedValues;

/* loaded from: classes5.dex */
public class NumericOrm extends OrmChange {
    @Override // com.plv.thirdpart.litepal.tablemanager.typechange.OrmChange
    public String object2Relation(String str) {
        if (str == null) {
            return null;
        }
        if (str.equals("int") || str.equals("java.lang.Integer") || str.equals("long") || str.equals("java.lang.Long") || str.equals("short") || str.equals("java.lang.Short")) {
            return TypedValues.Custom.S_INT;
        }
        return null;
    }
}
