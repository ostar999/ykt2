package com.plv.thirdpart.litepal.tablemanager.model;

import android.text.TextUtils;
import com.umeng.analytics.pro.aq;

/* loaded from: classes5.dex */
public class ColumnModel {
    private String columnName;
    private String columnType;
    private boolean isNullable = true;
    private boolean isUnique = false;
    private String defaultValue = "";
    private boolean hasIndex = false;

    public String getColumnName() {
        return this.columnName;
    }

    public String getColumnType() {
        return this.columnType;
    }

    public String getDefaultValue() {
        return this.defaultValue;
    }

    public boolean hasIndex() {
        return this.hasIndex;
    }

    public boolean isIdColumn() {
        return aq.f22519d.equalsIgnoreCase(this.columnName) || "id".equalsIgnoreCase(this.columnName);
    }

    public boolean isNullable() {
        return this.isNullable;
    }

    public boolean isUnique() {
        return this.isUnique;
    }

    public void setColumnName(String str) {
        this.columnName = str;
    }

    public void setColumnType(String str) {
        this.columnType = str;
    }

    public void setDefaultValue(String str) {
        if (!"text".equalsIgnoreCase(this.columnType)) {
            this.defaultValue = str;
            return;
        }
        if (TextUtils.isEmpty(str)) {
            return;
        }
        this.defaultValue = "'" + str + "'";
    }

    public void setHasIndex(boolean z2) {
        this.hasIndex = z2;
    }

    public void setNullable(boolean z2) {
        this.isNullable = z2;
    }

    public void setUnique(boolean z2) {
        this.isUnique = z2;
    }
}
