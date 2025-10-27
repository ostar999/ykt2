package com.psychiatrygarden.bean;

import java.util.List;

/* loaded from: classes5.dex */
public class BookBean {
    private String code;
    private List<BookDataBean> data;
    private String message;

    public class BookDataBean {
        private String book_id;
        private String book_title;
        private String end_time;
        private String id;
        private String last_chapter_id;
        private String last_chapter_title;
        private String renew;
        private String sort;
        private String thumbnail;
        private String user_id;

        public BookDataBean() {
        }

        public String getBook_id() {
            return this.book_id;
        }

        public String getBook_title() {
            return this.book_title;
        }

        public String getEnd_time() {
            return this.end_time;
        }

        public String getId() {
            return this.id;
        }

        public String getLast_chapter_id() {
            return this.last_chapter_id;
        }

        public String getLast_chapter_title() {
            return this.last_chapter_title;
        }

        public String getRenew() {
            return this.renew;
        }

        public String getSort() {
            return this.sort;
        }

        public String getThumbnail() {
            return this.thumbnail;
        }

        public String getUser_id() {
            return this.user_id;
        }

        public void setBook_id(String book_id) {
            this.book_id = book_id;
        }

        public void setBook_title(String book_title) {
            this.book_title = book_title;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setLast_chapter_id(String last_chapter_id) {
            this.last_chapter_id = last_chapter_id;
        }

        public void setLast_chapter_title(String last_chapter_title) {
            this.last_chapter_title = last_chapter_title;
        }

        public void setRenew(String renew) {
            this.renew = renew;
        }

        public void setSort(String sort) {
            this.sort = sort;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }
    }

    public String getCode() {
        return this.code;
    }

    public List<BookDataBean> getData() {
        return this.data;
    }

    public String getMessage() {
        return this.message;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setData(List<BookDataBean> data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
