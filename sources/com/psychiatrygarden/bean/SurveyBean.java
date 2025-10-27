package com.psychiatrygarden.bean;

import java.io.Serializable;
import java.util.List;

/* loaded from: classes5.dex */
public class SurveyBean implements Serializable {
    private String code;
    private List<SurveyDataBean> data;
    private String message;

    public class SurveyDataBean implements Serializable {
        private String describe;
        private String id;
        private String img_url;
        private String title;

        public SurveyDataBean() {
        }

        public String getDescribe() {
            return this.describe;
        }

        public String getId() {
            return this.id;
        }

        public String getImg_url() {
            return this.img_url;
        }

        public String getTitle() {
            return this.title;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    public String getCode() {
        return this.code;
    }

    public List<SurveyDataBean> getData() {
        return this.data;
    }

    public String getMessage() {
        return this.message;
    }
}
