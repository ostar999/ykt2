package com.catchpig.mvvm.utils;

/* loaded from: classes2.dex */
public class BusEvent {
    private String action;
    private int code;
    private Object data;
    private String message;

    public BusEvent(String str) {
        this.code = 0;
        this.message = "";
        this.data = null;
        this.action = str;
    }

    public String getAction() {
        return this.action;
    }

    public int getCode() {
        return this.code;
    }

    public Object getData() {
        return this.data;
    }

    public String getMessage() {
        return this.message;
    }

    public void setAction(String str) {
        this.action = str;
    }

    public void setCode(int i2) {
        this.code = i2;
    }

    public void setData(Object obj) {
        this.data = obj;
    }

    public void setMessage(String str) {
        this.message = str;
    }

    public BusEvent(String str, String str2) {
        this.code = 0;
        this.data = null;
        this.action = str;
        this.message = str2;
    }

    public BusEvent(String str, int i2) {
        this.message = "";
        this.data = null;
        this.action = str;
        this.code = i2;
    }

    public BusEvent(String str, String str2, int i2) {
        this.data = null;
        this.action = str;
        this.message = str2;
        this.code = i2;
    }

    public BusEvent(String str, String str2, int i2, Object obj) {
        this.action = str;
        this.message = str2;
        this.code = i2;
        this.data = obj;
    }
}
