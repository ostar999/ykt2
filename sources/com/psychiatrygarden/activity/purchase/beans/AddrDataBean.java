package com.psychiatrygarden.activity.purchase.beans;

import java.io.Serializable;

/* loaded from: classes5.dex */
public class AddrDataBean implements Serializable {
    public String addr_id;
    public String quantity = "1";

    public String getAddr_id() {
        return this.addr_id;
    }

    public String getQuantity() {
        return this.quantity;
    }

    public void setAddr_id(String addr_id) {
        this.addr_id = addr_id;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
