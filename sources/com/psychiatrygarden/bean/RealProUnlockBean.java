package com.psychiatrygarden.bean;

import java.io.Serializable;
import java.util.List;

/* loaded from: classes5.dex */
public class RealProUnlockBean implements Serializable {
    private int code;
    private List<DataBean> data;
    private String message;
    private int server_time;

    public static class DataBean implements Serializable {
        private int activity_id;
        private int chapter_id;
        private int pass = 0;

        public int getActivity_id() {
            return this.activity_id;
        }

        public int getChapter_id() {
            return this.chapter_id;
        }

        public int getPass() {
            return this.pass;
        }

        public void setActivity_id(int activity_id) {
            this.activity_id = activity_id;
        }

        public void setChapter_id(int chapter_id) {
            this.chapter_id = chapter_id;
        }

        public void setPass(int pass) {
            this.pass = pass;
        }
    }

    public int getCode() {
        return this.code;
    }

    public List<DataBean> getData() {
        return this.data;
    }

    public String getMessage() {
        return this.message;
    }

    public int getServer_time() {
        return this.server_time;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setServer_time(int server_time) {
        this.server_time = server_time;
    }
}
