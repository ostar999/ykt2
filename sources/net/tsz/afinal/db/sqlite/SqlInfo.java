package net.tsz.afinal.db.sqlite;

import java.util.LinkedList;

/* loaded from: classes9.dex */
public class SqlInfo {
    private LinkedList<Object> bindArgs;
    private String sql;

    public void addValue(Object obj) {
        if (this.bindArgs == null) {
            this.bindArgs = new LinkedList<>();
        }
        this.bindArgs.add(obj);
    }

    public LinkedList<Object> getBindArgs() {
        return this.bindArgs;
    }

    public Object[] getBindArgsAsArray() {
        LinkedList<Object> linkedList = this.bindArgs;
        if (linkedList != null) {
            return linkedList.toArray();
        }
        return null;
    }

    public String[] getBindArgsAsStringArray() {
        LinkedList<Object> linkedList = this.bindArgs;
        if (linkedList == null) {
            return null;
        }
        String[] strArr = new String[linkedList.size()];
        for (int i2 = 0; i2 < this.bindArgs.size(); i2++) {
            strArr[i2] = this.bindArgs.get(i2).toString();
        }
        return strArr;
    }

    public String getSql() {
        return this.sql;
    }

    public void setBindArgs(LinkedList<Object> linkedList) {
        this.bindArgs = linkedList;
    }

    public void setSql(String str) {
        this.sql = str;
    }
}
