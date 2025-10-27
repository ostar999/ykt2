package net.tsz.afinal.db.table;

import java.text.SimpleDateFormat;
import java.util.Date;

/* loaded from: classes9.dex */
public class KeyValue {
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private String key;
    private Object value;

    public KeyValue(String str, Object obj) {
        this.key = str;
        this.value = obj;
    }

    public String getKey() {
        return this.key;
    }

    public Object getValue() {
        Object obj = this.value;
        return ((obj instanceof Date) || (obj instanceof java.sql.Date)) ? sdf.format(obj) : obj;
    }

    public void setKey(String str) {
        this.key = str;
    }

    public void setValue(Object obj) {
        this.value = obj;
    }

    public KeyValue() {
    }
}
