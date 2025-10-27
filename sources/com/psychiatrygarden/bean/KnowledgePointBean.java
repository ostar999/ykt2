package com.psychiatrygarden.bean;

import java.util.List;

/* loaded from: classes5.dex */
public class KnowledgePointBean {
    private String code;
    private List<KnowledgePointData> data;
    private String message;

    public class KnowledgePointData {
        private String category;
        private String category_str;
        private List<KnowledgePointData> child;
        private String frequency;
        private String frequency_str;
        private String global_have;
        private String have;
        private String id;
        private String name;
        private String parent_id;
        private String practice_num;
        private String question_num;
        private String right_rate;
        private String sort;
        private String star;
        private String star_str;

        public KnowledgePointData() {
        }

        public String getCategory() {
            return this.category;
        }

        public String getCategory_str() {
            return this.category_str;
        }

        public List<KnowledgePointData> getChild() {
            return this.child;
        }

        public String getFrequency() {
            return this.frequency;
        }

        public String getFrequency_str() {
            return this.frequency_str;
        }

        public String getGlobal_have() {
            return this.global_have;
        }

        public String getHave() {
            return this.have;
        }

        public String getId() {
            return this.id;
        }

        public String getName() {
            return this.name;
        }

        public String getParent_id() {
            return this.parent_id;
        }

        public String getPractice_num() {
            return this.practice_num;
        }

        public String getQuestion_num() {
            return this.question_num;
        }

        public String getRight_rate() {
            return this.right_rate;
        }

        public String getSort() {
            return this.sort;
        }

        public String getStar() {
            return this.star;
        }

        public String getStar_str() {
            return this.star_str;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public void setCategory_str(String category_str) {
            this.category_str = category_str;
        }

        public void setChild(List<KnowledgePointData> child) {
            this.child = child;
        }

        public void setFrequency(String frequency) {
            this.frequency = frequency;
        }

        public void setFrequency_str(String frequency_str) {
            this.frequency_str = frequency_str;
        }

        public void setGlobal_have(String global_have) {
            this.global_have = global_have;
        }

        public void setHave(String have) {
            this.have = have;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setParent_id(String parent_id) {
            this.parent_id = parent_id;
        }

        public void setPractice_num(String practice_num) {
            this.practice_num = practice_num;
        }

        public void setQuestion_num(String question_num) {
            this.question_num = question_num;
        }

        public void setRight_rate(String right_rate) {
            this.right_rate = right_rate;
        }

        public void setSort(String sort) {
            this.sort = sort;
        }

        public void setStar(String star) {
            this.star = star;
        }

        public void setStar_str(String star_str) {
            this.star_str = star_str;
        }
    }

    public String getCode() {
        return this.code;
    }

    public List<KnowledgePointData> getData() {
        return this.data;
    }

    public String getMessage() {
        return this.message;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setData(List<KnowledgePointData> data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
