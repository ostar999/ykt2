package com.psychiatrygarden.bean;

import java.util.List;

/* loaded from: classes5.dex */
public class ForumFilterBean {
    private String code;
    private List<FilterDataBean> data;
    private String message;

    public static class FilterDataBean {
        private String app_id;
        private String category_id;
        private List<FilterDataBean> children;
        private String ctime;
        private String file_type;
        private boolean isSelected;
        private String key;
        private List<FilterDataBean> list;
        private String mTempKey;
        private int mTmpPos;
        private String ranking;
        private String score_type;
        private String source_type;
        private String time_range;
        private String title;
        private String tmpParentTitle;
        private String type;

        public String getApp_id() {
            return this.app_id;
        }

        public String getCategory_id() {
            return this.category_id;
        }

        public List<FilterDataBean> getChildren() {
            return this.children;
        }

        public String getCtime() {
            return this.ctime;
        }

        public String getFile_type() {
            return this.file_type;
        }

        public String getKey() {
            return this.key;
        }

        public List<FilterDataBean> getList() {
            return this.list;
        }

        public String getRanking() {
            return this.ranking;
        }

        public String getScore_type() {
            return this.score_type;
        }

        public String getSource_type() {
            return this.source_type;
        }

        public String getTime_range() {
            return this.time_range;
        }

        public String getTitle() {
            return this.title;
        }

        public String getTmpParentTitle() {
            return this.tmpParentTitle;
        }

        public String getType() {
            return this.type;
        }

        public String getmTempKey() {
            return this.mTempKey;
        }

        public int getmTmpPos() {
            return this.mTmpPos;
        }

        public boolean isSelected() {
            return this.isSelected;
        }

        public void setApp_id(String app_id) {
            this.app_id = app_id;
        }

        public void setCategory_id(String category_id) {
            this.category_id = category_id;
        }

        public void setChildren(List<FilterDataBean> children) {
            this.children = children;
        }

        public void setCtime(String ctime) {
            this.ctime = ctime;
        }

        public void setFile_type(String file_type) {
            this.file_type = file_type;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public void setList(List<FilterDataBean> list) {
            this.list = list;
        }

        public void setRanking(String ranking) {
            this.ranking = ranking;
        }

        public void setScore_type(String score_type) {
            this.score_type = score_type;
        }

        public void setSelected(boolean selected) {
            this.isSelected = selected;
        }

        public void setSource_type(String source_type) {
            this.source_type = source_type;
        }

        public void setTime_range(String time_range) {
            this.time_range = time_range;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setTmpParentTitle(String tmpParentTitle) {
            this.tmpParentTitle = tmpParentTitle;
        }

        public void setType(String type) {
            this.type = type;
        }

        public void setmTempKey(String mTempKey) {
            this.mTempKey = mTempKey;
        }

        public void setmTmpPos(int mTmpPos) {
            this.mTmpPos = mTmpPos;
        }
    }

    public String getCode() {
        return this.code;
    }

    public List<FilterDataBean> getData() {
        return this.data;
    }

    public String getMessage() {
        return this.message;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setData(List<FilterDataBean> data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
