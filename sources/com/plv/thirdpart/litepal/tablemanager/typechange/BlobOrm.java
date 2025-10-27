package com.plv.thirdpart.litepal.tablemanager.typechange;

/* loaded from: classes5.dex */
public class BlobOrm extends OrmChange {
    @Override // com.plv.thirdpart.litepal.tablemanager.typechange.OrmChange
    public String object2Relation(String str) {
        if (str == null || !str.equals("[B")) {
            return null;
        }
        return "blob";
    }
}
