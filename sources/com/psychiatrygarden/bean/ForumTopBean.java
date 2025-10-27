package com.psychiatrygarden.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes5.dex */
public class ForumTopBean implements Serializable {
    private String code;
    private ForumTopDataBean data;
    private String message;

    public class ForumTopDataBean {
        private List<TopModule> bbs_module;
        private ArrayList<KeyWordsData> bbs_promotion;
        private List<KeyWordsData> keywords_recommend;

        public ForumTopDataBean() {
        }

        public List<TopModule> getBbs_module() {
            return this.bbs_module;
        }

        public ArrayList<KeyWordsData> getBbs_promotion() {
            return this.bbs_promotion;
        }

        public List<KeyWordsData> getKeywords_recommend() {
            return this.keywords_recommend;
        }

        public void setBbs_module(List<TopModule> bbs_module) {
            this.bbs_module = bbs_module;
        }

        public void setBbs_promotion(ArrayList<KeyWordsData> bbs_promotion) {
            this.bbs_promotion = bbs_promotion;
        }

        public void setKeywords_recommend(List<KeyWordsData> keywords_recommend) {
            this.keywords_recommend = keywords_recommend;
        }
    }

    public static class KeyWordsData {
        private String jpush_type;
        private String keywords;
        private String label;
        private TarGetParamBean target_params;
        private String title;

        public String getJpush_type() {
            return this.jpush_type;
        }

        public String getKeywords() {
            return this.keywords;
        }

        public String getLabel() {
            return this.label;
        }

        public TarGetParamBean getTarget_params() {
            return this.target_params;
        }

        public String getTitle() {
            return this.title;
        }

        public void setJpush_type(String jpush_type) {
            this.jpush_type = jpush_type;
        }

        public void setKeywords(String keywords) {
            this.keywords = keywords;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public void setTarget_params(TarGetParamBean target_params) {
            this.target_params = target_params;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    public static class TopModule {
        private String code;
        private String icon;
        private String label;
        private String name;
        private String redirect;
        private String type;

        public String getCode() {
            return this.code;
        }

        public String getIcon() {
            return this.icon;
        }

        public String getLabel() {
            return this.label;
        }

        public String getName() {
            return this.name;
        }

        public String getRedirect() {
            return this.redirect;
        }

        public String getType() {
            return this.type;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setRedirect(String redirect) {
            this.redirect = redirect;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

    public String getCode() {
        return this.code;
    }

    public ForumTopDataBean getData() {
        return this.data;
    }

    public String getMessage() {
        return this.message;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setData(ForumTopDataBean data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
