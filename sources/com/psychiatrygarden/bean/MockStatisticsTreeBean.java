package com.psychiatrygarden.bean;

import java.util.List;

/* loaded from: classes5.dex */
public class MockStatisticsTreeBean {
    private String code;
    private List<MockStatisticsData> data;
    private String message;

    public class MockStatisticsData {
        private String accuracy;
        private List<MockStatisticsData> children;
        private String count;
        private String have;
        private String id;
        private String name;
        private String right_count;
        private String wrong_count;

        public MockStatisticsData() {
        }

        public String getAccuracy() {
            return this.accuracy;
        }

        public List<MockStatisticsData> getChildren() {
            return this.children;
        }

        public String getCount() {
            return this.count;
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

        public String getRight_count() {
            return this.right_count;
        }

        public String getWrong_count() {
            return this.wrong_count;
        }

        public void setAccuracy(String accuracy) {
            this.accuracy = accuracy;
        }

        public void setChildren(List<MockStatisticsData> children) {
            this.children = children;
        }

        public void setCount(String count) {
            this.count = count;
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

        public void setRight_count(String right_count) {
            this.right_count = right_count;
        }

        public void setWrong_count(String wrong_count) {
            this.wrong_count = wrong_count;
        }
    }

    public String getCode() {
        return this.code;
    }

    public List<MockStatisticsData> getData() {
        return this.data;
    }

    public String getMessage() {
        return this.message;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setData(List<MockStatisticsData> data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
