package com.psychiatrygarden.bean;

import java.util.List;

/* loaded from: classes5.dex */
public class WordTranslateBean {
    private int code;
    private DataDTO data;
    private String message;
    private int prc_time;

    public static class DataDTO {
        private List<String> explain;
        private String isCollection;
        private String pronunciation;
        private String symbols;
        private String word;

        public List<String> getExplain() {
            return this.explain;
        }

        public String getIsCollection() {
            return this.isCollection;
        }

        public String getPronunciation() {
            return this.pronunciation;
        }

        public String getSymbols() {
            return this.symbols;
        }

        public String getWord() {
            return this.word;
        }

        public void setExplain(List<String> explain) {
            this.explain = explain;
        }

        public void setIsCollection(String isCollection) {
            this.isCollection = isCollection;
        }

        public void setPronunciation(String pronunciation) {
            this.pronunciation = pronunciation;
        }

        public void setSymbols(String symbols) {
            this.symbols = symbols;
        }

        public void setWord(String word) {
            this.word = word;
        }
    }

    public int getCode() {
        return this.code;
    }

    public DataDTO getData() {
        return this.data;
    }

    public String getMessage() {
        return this.message;
    }

    public int getPrc_time() {
        return this.prc_time;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setData(DataDTO data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setPrc_time(int prc_time) {
        this.prc_time = prc_time;
    }
}
