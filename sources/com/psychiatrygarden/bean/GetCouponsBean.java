package com.psychiatrygarden.bean;

import com.psychiatrygarden.bean.RedEnvelopeCouponsBean;
import java.util.List;

/* loaded from: classes5.dex */
public class GetCouponsBean {
    private String code;
    private List<RedEnvelopeCouponsBean.RedEnvelopeCouponsDataItem> data;
    private String message;

    public String getCode() {
        return this.code;
    }

    public List<RedEnvelopeCouponsBean.RedEnvelopeCouponsDataItem> getData() {
        return this.data;
    }

    public String getMessage() {
        return this.message;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setData(List<RedEnvelopeCouponsBean.RedEnvelopeCouponsDataItem> data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
