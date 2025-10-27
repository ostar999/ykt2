package net.tsz.afinal.db.sqlite;

import java.util.HashMap;

/* loaded from: classes9.dex */
public class DbModel {
    private HashMap<String, Object> dataMap = new HashMap<>();

    public Object get(String str) {
        return this.dataMap.get(str);
    }

    public boolean getBoolean(String str) {
        return Boolean.valueOf(getString(str)).booleanValue();
    }

    public HashMap<String, Object> getDataMap() {
        return this.dataMap;
    }

    public double getDouble(String str) {
        return Double.valueOf(getString(str)).doubleValue();
    }

    public float getFloat(String str) {
        return Float.valueOf(getString(str)).floatValue();
    }

    public int getInt(String str) {
        return Integer.valueOf(getString(str)).intValue();
    }

    public long getLong(String str) {
        return Long.valueOf(getString(str)).longValue();
    }

    public String getString(String str) {
        return String.valueOf(get(str));
    }

    public void set(String str, Object obj) {
        this.dataMap.put(str, obj);
    }
}
