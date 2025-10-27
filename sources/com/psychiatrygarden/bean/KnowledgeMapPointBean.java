package com.psychiatrygarden.bean;

import java.util.List;

/* loaded from: classes5.dex */
public class KnowledgeMapPointBean {
    private String code;
    private KnowledgeMapPointDataBean data;
    private String message;

    public class ChildItemData {
        private String count;
        private String label;
        private String practice_count;
        private String rate;
        private String title;

        public ChildItemData() {
        }

        public String getCount() {
            return this.count;
        }

        public String getLabel() {
            return this.label;
        }

        public String getPractice_count() {
            return this.practice_count;
        }

        public String getRate() {
            return this.rate;
        }

        public String getTitle() {
            return this.title;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public void setPractice_count(String practice_count) {
            this.practice_count = practice_count;
        }

        public void setRate(String rate) {
            this.rate = rate;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    public class KnowledgeMapPointDataBean {
        private String all_correct_rate;
        private List<ChildItemData> category_list;
        private List<ChildItemData> frequency_list;
        private String knowledge_count;
        private String practice_count;
        private List<ChildItemData> star_list;
        private String user_correct_rate;

        public KnowledgeMapPointDataBean() {
        }

        public String getAll_correct_rate() {
            return this.all_correct_rate;
        }

        public List<ChildItemData> getCategory_list() {
            return this.category_list;
        }

        public List<ChildItemData> getFrequency_list() {
            return this.frequency_list;
        }

        public String getKnowledge_count() {
            return this.knowledge_count;
        }

        public String getPractice_count() {
            return this.practice_count;
        }

        public List<ChildItemData> getStar_list() {
            return this.star_list;
        }

        public String getUser_correct_rate() {
            return this.user_correct_rate;
        }

        public void setAll_correct_rate(String all_correct_rate) {
            this.all_correct_rate = all_correct_rate;
        }

        public void setCategory_list(List<ChildItemData> category_list) {
            this.category_list = category_list;
        }

        public void setFrequency_list(List<ChildItemData> frequency_list) {
            this.frequency_list = frequency_list;
        }

        public void setKnowledge_count(String knowledge_count) {
            this.knowledge_count = knowledge_count;
        }

        public void setPractice_count(String practice_count) {
            this.practice_count = practice_count;
        }

        public void setStar_list(List<ChildItemData> star_list) {
            this.star_list = star_list;
        }

        public void setUser_correct_rate(String user_correct_rate) {
            this.user_correct_rate = user_correct_rate;
        }
    }

    public String getCode() {
        return this.code;
    }

    public KnowledgeMapPointDataBean getData() {
        return this.data;
    }

    public String getMessage() {
        return this.message;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setData(KnowledgeMapPointDataBean data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
