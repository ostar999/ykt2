package com.plv.thirdpart.litepal;

import com.plv.thirdpart.litepal.parser.LitePalConfig;
import com.plv.thirdpart.litepal.parser.LitePalParser;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes5.dex */
public class LitePalDB {
    private List<String> classNames;
    private String dbName;
    private boolean isExternalStorage = false;
    private String storage;
    private int version;

    public LitePalDB(String str, int i2) {
        this.dbName = str;
        this.version = i2;
    }

    public static LitePalDB fromDefault(String str) {
        LitePalConfig litePalConfiguration = LitePalParser.parseLitePalConfiguration();
        LitePalDB litePalDB = new LitePalDB(str, litePalConfiguration.getVersion());
        litePalDB.setStorage(litePalConfiguration.getStorage());
        litePalDB.setClassNames(litePalConfiguration.getClassNames());
        return litePalDB;
    }

    public void addClassName(String str) {
        getClassNames().add(str);
    }

    public List<String> getClassNames() {
        List<String> list = this.classNames;
        if (list == null) {
            ArrayList arrayList = new ArrayList();
            this.classNames = arrayList;
            arrayList.add("com.plv.thirdpart.litepal.model.Table_Schema");
        } else if (list.isEmpty()) {
            this.classNames.add("com.plv.thirdpart.litepal.model.Table_Schema");
        }
        return this.classNames;
    }

    public String getDbName() {
        return this.dbName;
    }

    public String getStorage() {
        return this.storage;
    }

    public int getVersion() {
        return this.version;
    }

    public boolean isExternalStorage() {
        return this.isExternalStorage;
    }

    public void setClassNames(List<String> list) {
        this.classNames = list;
    }

    public void setExternalStorage(boolean z2) {
        this.isExternalStorage = z2;
    }

    public void setStorage(String str) {
        this.storage = str;
    }
}
