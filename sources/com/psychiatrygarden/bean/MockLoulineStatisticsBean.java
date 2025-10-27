package com.psychiatrygarden.bean;

import java.util.List;

/* loaded from: classes5.dex */
public class MockLoulineStatisticsBean {
    private String code;
    private MockOutlineStatisticsData data;
    private String message;

    public class MockOutlineData {
        private String accuracy;
        private String chapter_id;
        private List<MockOutlineData> children;
        private String count;
        private String have;
        private String parent_id;
        private String right_count;
        private String title;
        private String wrong_count;

        public MockOutlineData() {
        }

        public String getAccuracy() {
            return this.accuracy;
        }

        public String getChapter_id() {
            return this.chapter_id;
        }

        public List<MockOutlineData> getChildren() {
            return this.children;
        }

        public String getCount() {
            return this.count;
        }

        public String getHave() {
            return this.have;
        }

        public String getParent_id() {
            return this.parent_id;
        }

        public String getRight_count() {
            return this.right_count;
        }

        public String getTitle() {
            return this.title;
        }

        public String getWrong_count() {
            return this.wrong_count;
        }

        public void setAccuracy(String accuracy) {
            this.accuracy = accuracy;
        }

        public void setChapter_id(String chapter_id) {
            this.chapter_id = chapter_id;
        }

        public void setChildren(List<MockOutlineData> children) {
            this.children = children;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public void setHave(String have) {
            this.have = have;
        }

        public void setParent_id(String parent_id) {
            this.parent_id = parent_id;
        }

        public void setRight_count(String right_count) {
            this.right_count = right_count;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setWrong_count(String wrong_count) {
            this.wrong_count = wrong_count;
        }
    }

    public class MockOutlineStatisticsData {
        private List<MockOutlineData> list;
        private String share_img;

        public MockOutlineStatisticsData() {
        }

        public List<MockOutlineData> getList() {
            return this.list;
        }

        public String getShare_img() {
            return this.share_img;
        }

        public void setList(List<MockOutlineData> list) {
            this.list = list;
        }

        public void setShare_img(String share_img) {
            this.share_img = share_img;
        }
    }

    public String getCode() {
        return this.code;
    }

    public MockOutlineStatisticsData getData() {
        return this.data;
    }

    public String getMessage() {
        return this.message;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setData(MockOutlineStatisticsData data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
