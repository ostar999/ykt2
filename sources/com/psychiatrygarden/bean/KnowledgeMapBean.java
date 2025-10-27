package com.psychiatrygarden.bean;

import java.util.List;

/* loaded from: classes5.dex */
public class KnowledgeMapBean {
    private String code;
    private KnowledgeMapDataBean data;
    private String message;

    public class KnowledgeMapDataBean {
        private String filter_desc;
        private String is_vip;
        private List<KnowledgeMapItemDataBean> list;
        private String top_desc;
        private String top_name;
        private String type;

        public KnowledgeMapDataBean() {
        }

        public String getFilter_desc() {
            return this.filter_desc;
        }

        public String getIs_vip() {
            return this.is_vip;
        }

        public List<KnowledgeMapItemDataBean> getList() {
            return this.list;
        }

        public String getTop_desc() {
            return this.top_desc;
        }

        public String getTop_name() {
            return this.top_name;
        }

        public String getType() {
            return this.type;
        }

        public void setFilter_desc(String filter_desc) {
            this.filter_desc = filter_desc;
        }

        public void setIs_vip(String is_vip) {
            this.is_vip = is_vip;
        }

        public void setList(List<KnowledgeMapItemDataBean> list) {
            this.list = list;
        }

        public void setTop_desc(String top_desc) {
            this.top_desc = top_desc;
        }

        public void setTop_name(String top_name) {
            this.top_name = top_name;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

    public class KnowledgeMapItemDataBean {
        private String all_correct_rate;
        private String category;
        private String chapter_id;
        private String has_children;
        private String id;
        private String knowledge_count;
        private String name;
        private String part_id;
        private String practice_count;
        private String practice_rate;
        private String question_count;
        private String user_correct_rate;

        public KnowledgeMapItemDataBean() {
        }

        public String getAll_correct_rate() {
            return this.all_correct_rate;
        }

        public String getCategory() {
            return this.category;
        }

        public String getChapter_id() {
            return this.chapter_id;
        }

        public String getHas_children() {
            return this.has_children;
        }

        public String getId() {
            return this.id;
        }

        public String getKnowledge_count() {
            return this.knowledge_count;
        }

        public String getName() {
            return this.name;
        }

        public String getPart_id() {
            return this.part_id;
        }

        public String getPractice_count() {
            return this.practice_count;
        }

        public String getPractice_rate() {
            return this.practice_rate;
        }

        public String getQuestion_count() {
            return this.question_count;
        }

        public String getUser_correct_rate() {
            return this.user_correct_rate;
        }

        public void setAll_correct_rate(String all_correct_rate) {
            this.all_correct_rate = all_correct_rate;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public void setChapter_id(String chapter_id) {
            this.chapter_id = chapter_id;
        }

        public void setHas_children(String has_children) {
            this.has_children = has_children;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setKnowledge_count(String knowledge_count) {
            this.knowledge_count = knowledge_count;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setPart_id(String part_id) {
            this.part_id = part_id;
        }

        public void setPractice_count(String practice_count) {
            this.practice_count = practice_count;
        }

        public void setPractice_rate(String practice_rate) {
            this.practice_rate = practice_rate;
        }

        public void setQuestion_count(String question_count) {
            this.question_count = question_count;
        }

        public void setUser_correct_rate(String user_correct_rate) {
            this.user_correct_rate = user_correct_rate;
        }
    }

    public String getCode() {
        return this.code;
    }

    public KnowledgeMapDataBean getData() {
        return this.data;
    }

    public String getMessage() {
        return this.message;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setData(KnowledgeMapDataBean data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
