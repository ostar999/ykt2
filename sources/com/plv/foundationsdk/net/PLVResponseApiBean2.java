package com.plv.foundationsdk.net;

import com.plv.foundationsdk.model.PLVFoundationVO;

/* loaded from: classes4.dex */
public class PLVResponseApiBean2<T> implements PLVFoundationVO {
    private Integer code;
    private T data;
    private String message;
    private String status;

    public Integer getCode() {
        return this.code;
    }

    public T getData() {
        return this.data;
    }

    public String getMessage() {
        return this.message;
    }

    public String getStatus() {
        return this.status;
    }

    public void setCode(Integer num) {
        this.code = num;
    }

    public void setData(T t2) {
        this.data = t2;
    }

    public void setMessage(String str) {
        this.message = str;
    }

    public void setStatus(String str) {
        this.status = str;
    }
}
