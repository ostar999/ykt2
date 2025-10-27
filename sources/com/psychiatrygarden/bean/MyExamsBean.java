package com.psychiatrygarden.bean;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.psychiatrygarden.http.CodeParseJsonAdapter;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes5.dex */
public class MyExamsBean {
    private ArrayList<MyExamDataBean> data;
    private String message;
    private long prc_time;

    @SerializedName("code")
    @JsonAdapter(CodeParseJsonAdapter.class)
    private boolean success;

    public ArrayList<MyExamDataBean> getData() {
        return this.data;
    }

    public String getMessage() {
        return this.message;
    }

    public long getPrc_time() {
        return this.prc_time;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public void setData(ArrayList<MyExamDataBean> data) {
        this.data = data;
        if (data == null || data.isEmpty()) {
            return;
        }
        Iterator<MyExamDataBean> it = data.iterator();
        while (it.hasNext()) {
            it.next().setServerTime(this.prc_time);
        }
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setPrc_time(long prc_time) {
        this.prc_time = prc_time;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
