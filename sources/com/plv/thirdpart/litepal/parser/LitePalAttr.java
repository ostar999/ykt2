package com.plv.thirdpart.litepal.parser;

import android.text.TextUtils;
import com.plv.thirdpart.litepal.exceptions.InvalidAttributesException;
import com.plv.thirdpart.litepal.util.BaseUtility;
import com.plv.thirdpart.litepal.util.Const;
import com.plv.thirdpart.litepal.util.SharedUtil;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes5.dex */
public final class LitePalAttr {
    private static LitePalAttr litePalAttr;
    private String cases;
    private List<String> classNames;
    private String dbName;
    private String extraKeyName;
    private String storage;
    private int version;

    private LitePalAttr() {
    }

    public static void clearInstance() {
        litePalAttr = null;
    }

    public static LitePalAttr getInstance() {
        if (litePalAttr == null) {
            synchronized (LitePalAttr.class) {
                if (litePalAttr == null) {
                    litePalAttr = new LitePalAttr();
                    loadLitePalXMLConfiguration();
                }
            }
        }
        return litePalAttr;
    }

    private static void loadLitePalXMLConfiguration() {
        if (BaseUtility.isLitePalXMLExists()) {
            LitePalConfig litePalConfiguration = LitePalParser.parseLitePalConfiguration();
            litePalAttr.setDbName(litePalConfiguration.getDbName());
            litePalAttr.setVersion(litePalConfiguration.getVersion());
            litePalAttr.setClassNames(litePalConfiguration.getClassNames());
            litePalAttr.setCases(litePalConfiguration.getCases());
            litePalAttr.setStorage(litePalConfiguration.getStorage());
        }
    }

    public void addClassName(String str) {
        getClassNames().add(str);
    }

    public void checkSelfValid() {
        if (TextUtils.isEmpty(this.dbName)) {
            loadLitePalXMLConfiguration();
            if (TextUtils.isEmpty(this.dbName)) {
                throw new InvalidAttributesException(InvalidAttributesException.DBNAME_IS_EMPTY_OR_NOT_DEFINED);
            }
        }
        if (!this.dbName.endsWith(".db")) {
            this.dbName += ".db";
        }
        int i2 = this.version;
        if (i2 < 1) {
            throw new InvalidAttributesException(InvalidAttributesException.VERSION_OF_DATABASE_LESS_THAN_ONE);
        }
        if (i2 < SharedUtil.getLastVersion(this.extraKeyName)) {
            throw new InvalidAttributesException(InvalidAttributesException.VERSION_IS_EARLIER_THAN_CURRENT);
        }
        if (TextUtils.isEmpty(this.cases)) {
            this.cases = Const.Config.CASES_LOWER;
            return;
        }
        if (this.cases.equals(Const.Config.CASES_UPPER) || this.cases.equals(Const.Config.CASES_LOWER) || this.cases.equals(Const.Config.CASES_KEEP)) {
            return;
        }
        throw new InvalidAttributesException(this.cases + InvalidAttributesException.CASES_VALUE_IS_INVALID);
    }

    public String getCases() {
        return this.cases;
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

    public String getExtraKeyName() {
        return this.extraKeyName;
    }

    public String getStorage() {
        return this.storage;
    }

    public int getVersion() {
        return this.version;
    }

    public void setCases(String str) {
        this.cases = str;
    }

    public void setClassNames(List<String> list) {
        this.classNames = list;
    }

    public void setDbName(String str) {
        this.dbName = str;
    }

    public void setExtraKeyName(String str) {
        this.extraKeyName = str;
    }

    public void setStorage(String str) {
        this.storage = str;
    }

    public void setVersion(int i2) {
        this.version = i2;
    }
}
