package com.tencent.liteav.basic.module;

/* loaded from: classes6.dex */
public class a {
    private String mID = "";

    public void clearID() {
        synchronized (this) {
            if (this.mID.length() != 0) {
                TXCStatus.b(this.mID);
                this.mID = "";
            }
        }
    }

    public void finalize() throws Throwable {
        clearID();
        super.finalize();
    }

    public double getDoubleValue(int i2) {
        return TXCStatus.d(this.mID, i2);
    }

    public String getID() {
        return this.mID;
    }

    public int getIntValue(int i2) {
        return TXCStatus.c(this.mID, i2);
    }

    public long getLongValue(int i2) {
        return TXCStatus.a(this.mID, i2);
    }

    public String getStringValue(int i2) {
        return TXCStatus.b(this.mID, i2);
    }

    public void setID(String str) {
        clearID();
        synchronized (this) {
            if (str.length() != 0) {
                this.mID = str;
                TXCStatus.a(str);
            }
        }
    }

    public boolean setStatusValue(int i2, Object obj) {
        return TXCStatus.a(this.mID, i2, obj);
    }

    public double getDoubleValue(int i2, int i3) {
        return TXCStatus.d(this.mID, i2, i3);
    }

    public int getIntValue(int i2, int i3) {
        return TXCStatus.c(this.mID, i2, i3);
    }

    public long getLongValue(int i2, int i3) {
        return TXCStatus.a(this.mID, i2, i3);
    }

    public String getStringValue(int i2, int i3) {
        return TXCStatus.b(this.mID, i2, i3);
    }

    public boolean setStatusValue(int i2, int i3, Object obj) {
        return TXCStatus.a(this.mID, i2, i3, obj);
    }
}
