package com.psychiatrygarden.bean;

/* loaded from: classes5.dex */
public class EventBusMessage<T> {
    private String key;
    private String type;
    private T valueObj;

    public EventBusMessage(String key, T valueObj) {
        this.key = key;
        this.valueObj = valueObj;
    }

    public String getKey() {
        return this.key;
    }

    public String getType() {
        return this.type;
    }

    public T getValueObj() {
        return this.valueObj;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setValueObj(T valueObj) {
        this.valueObj = valueObj;
    }

    public EventBusMessage(String key, T valueObj, String type) {
        this.key = key;
        this.valueObj = valueObj;
        this.type = type;
    }
}
