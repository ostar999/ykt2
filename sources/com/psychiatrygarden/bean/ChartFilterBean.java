package com.psychiatrygarden.bean;

import java.util.List;

/* loaded from: classes5.dex */
public class ChartFilterBean {
    private String name;
    private String type;
    private List<ChartFilterValue> value;

    public static class ChartFilterValue {
        private String frequency;
        private String key;
        private String name;
        private boolean select;
        private List<String> star;
        private String title;
        private String type;
        private String value;

        public String getFrequency() {
            return this.frequency;
        }

        public String getKey() {
            return this.key;
        }

        public String getName() {
            return this.name;
        }

        public List<String> getStar() {
            return this.star;
        }

        public String getTitle() {
            return this.title;
        }

        public String getType() {
            return this.type;
        }

        public String getValue() {
            return this.value;
        }

        public boolean isSelect() {
            return this.select;
        }

        public void setFrequency(String frequency) {
            this.frequency = frequency;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setSelect(boolean select) {
            this.select = select;
        }

        public void setStar(List<String> star) {
            this.star = star;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setType(String type) {
            this.type = type;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public String getName() {
        return this.name;
    }

    public String getType() {
        return this.type;
    }

    public List<ChartFilterValue> getValue() {
        return this.value;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setValue(List<ChartFilterValue> value) {
        this.value = value;
    }
}
