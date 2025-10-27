package com.psychiatrygarden.bean;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/* loaded from: classes5.dex */
public class BookStackBean {
    private String code;
    private List<BookStackData> data;
    private String message;

    public static class BookStackData {
        private String app_id;
        private String app_name;

        @SerializedName("book_title")
        private String bookTitle;
        private String book_count;
        private String book_review_count;
        private String describe;
        private String file_type;
        private String id;
        private String read_count;
        private String thumbnail;

        public String getApp_id() {
            return this.app_id;
        }

        public String getApp_name() {
            return this.app_name;
        }

        public String getBookTitle() {
            return this.bookTitle;
        }

        public String getBook_count() {
            return this.book_count;
        }

        public String getBook_review_count() {
            return this.book_review_count;
        }

        public String getDescribe() {
            return this.describe;
        }

        public String getFile_type() {
            return this.file_type;
        }

        public String getId() {
            return this.id;
        }

        public String getRead_count() {
            return this.read_count;
        }

        public String getThumbnail() {
            return this.thumbnail;
        }

        public void setApp_id(String app_id) {
            this.app_id = app_id;
        }

        public void setApp_name(String app_name) {
            this.app_name = app_name;
        }

        public void setBookTitle(String bookTitle) {
            this.bookTitle = bookTitle;
        }

        public void setBook_count(String book_count) {
            this.book_count = book_count;
        }

        public void setBook_review_count(String book_review_count) {
            this.book_review_count = book_review_count;
        }

        public void setDescribe(String describe) {
            this.describe = describe;
        }

        public void setFile_type(String file_type) {
            this.file_type = file_type;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setRead_count(String read_count) {
            this.read_count = read_count;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }
    }

    public String getCode() {
        return this.code;
    }

    public List<BookStackData> getData() {
        return this.data;
    }

    public String getMessage() {
        return this.message;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setData(List<BookStackData> data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
