package com.psychiatrygarden.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes5.dex */
public class ServiceNoticeBean implements Serializable {
    private String code;
    private List<ServiceNoticeItemBean> data = new ArrayList();
    private String message;

    public class ServiceNoticeItemBean {
        private String app_id;
        private String content;
        private String cover;
        private String ctime;
        private String goods_id;
        private String goods_type;
        private String id;
        private String order_no;
        private String time_text;
        private String title;
        private String type;
        private String type_text;
        private String user_id;

        public ServiceNoticeItemBean() {
        }

        public String getApp_id() {
            return this.app_id;
        }

        public String getContent() {
            return this.content;
        }

        public String getCover() {
            return this.cover;
        }

        public String getCtime() {
            return this.ctime;
        }

        public String getGoods_id() {
            return this.goods_id;
        }

        public String getGoods_type() {
            return this.goods_type;
        }

        public String getId() {
            return this.id;
        }

        public String getOrder_no() {
            return this.order_no;
        }

        public String getTime_text() {
            return this.time_text;
        }

        public String getTitle() {
            return this.title;
        }

        public String getType() {
            return this.type;
        }

        public String getType_text() {
            return this.type_text;
        }

        public String getUser_id() {
            return this.user_id;
        }

        public void setApp_id(String app_id) {
            this.app_id = app_id;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public void setCtime(String ctime) {
            this.ctime = ctime;
        }

        public void setGoods_id(String goods_id) {
            this.goods_id = goods_id;
        }

        public void setGoods_type(String goods_type) {
            this.goods_type = goods_type;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setOrder_no(String order_no) {
            this.order_no = order_no;
        }

        public void setTime_text(String time_text) {
            this.time_text = time_text;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setType(String type) {
            this.type = type;
        }

        public void setType_text(String type_text) {
            this.type_text = type_text;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }
    }

    public String getCode() {
        return this.code;
    }

    public List<ServiceNoticeItemBean> getData() {
        return this.data;
    }

    public String getMessage() {
        return this.message;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setData(List<ServiceNoticeItemBean> data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
