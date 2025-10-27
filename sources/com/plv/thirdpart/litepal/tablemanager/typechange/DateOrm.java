package com.plv.thirdpart.litepal.tablemanager.typechange;

import androidx.constraintlayout.core.motion.utils.TypedValues;

/* loaded from: classes5.dex */
public class DateOrm extends OrmChange {
    @Override // com.plv.thirdpart.litepal.tablemanager.typechange.OrmChange
    public String object2Relation(String str) {
        if (str == null || !str.equals("java.util.Date")) {
            return null;
        }
        return TypedValues.Custom.S_INT;
    }
}
