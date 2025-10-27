package com.psychiatrygarden.bean;

import com.google.gson.annotations.SerializedName;
import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import java.io.Serializable;
import java.util.List;

/* loaded from: classes5.dex */
public class HandoutNewBean implements Serializable {
    private String code;
    private DataBean data;
    private String message;
    private String server_time;
    private String time;

    public static class DataBean {
        private List<TopBean> list;
        private List<HandoutImage> sider;

        /* renamed from: top, reason: collision with root package name */
        private List<TopBean> f15298top;

        public static class TopBean {
            private String cid;
            private String cname;
            private String comment_count;
            private String content;
            private List<String> cover;

            @SerializedName(alternate = {CrashHianalyticsData.TIME}, value = "comment_time")
            private String ctime;
            private String hot_img;
            private String html_path;
            private String id;
            private String is_focus;
            private String is_long;
            private String is_read;
            private String is_rich_text;
            private String is_share;
            private String json_path;
            private String long_figure;
            private String new_img;
            private String title;
            private String top_img;
            private String h5_path = "0";
            private String author_looked = "0";
            private String zhiding = "0";

            public String getAuthor_looked() {
                return this.author_looked;
            }

            public String getCid() {
                return this.cid;
            }

            public String getCname() {
                return this.cname;
            }

            public String getComment_count() {
                return this.comment_count;
            }

            public String getContent() {
                return this.content;
            }

            public List<String> getCover() {
                return this.cover;
            }

            public String getCtime() {
                return this.ctime;
            }

            public String getH5_path() {
                return this.h5_path;
            }

            public String getHot_img() {
                return this.hot_img;
            }

            public String getHtml_path() {
                return this.html_path;
            }

            public String getId() {
                return this.id;
            }

            public String getIs_focus() {
                return this.is_focus;
            }

            public String getIs_long() {
                return this.is_long;
            }

            public String getIs_read() {
                return this.is_read;
            }

            public String getIs_rich_text() {
                return this.is_rich_text;
            }

            public String getIs_share() {
                return this.is_share;
            }

            public String getJson_path() {
                return this.json_path;
            }

            public String getLong_figure() {
                return this.long_figure;
            }

            public String getNew_img() {
                return this.new_img;
            }

            public String getTitle() {
                return this.title;
            }

            public String getTop_img() {
                return this.top_img;
            }

            public String getZhiding() {
                return this.zhiding;
            }

            public void setAuthor_looked(String author_looked) {
                this.author_looked = author_looked;
            }

            public void setCid(String cid) {
                this.cid = cid;
            }

            public void setCname(String cname) {
                this.cname = cname;
            }

            public void setComment_count(String comment_count) {
                this.comment_count = comment_count;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public void setCover(List<String> cover) {
                this.cover = cover;
            }

            public void setCtime(String ctime) {
                this.ctime = ctime;
            }

            public void setH5_path(String h5_path) {
                this.h5_path = h5_path;
            }

            public void setHot_img(String hot_img) {
                this.hot_img = hot_img;
            }

            public void setHtml_path(String html_path) {
                this.html_path = html_path;
            }

            public void setId(String id) {
                this.id = id;
            }

            public void setIs_focus(String is_focus) {
                this.is_focus = is_focus;
            }

            public void setIs_long(String is_long) {
                this.is_long = is_long;
            }

            public void setIs_read(String is_read) {
                this.is_read = is_read;
            }

            public void setIs_rich_text(String is_rich_text) {
                this.is_rich_text = is_rich_text;
            }

            public void setIs_share(String is_share) {
                this.is_share = is_share;
            }

            public void setJson_path(String json_path) {
                this.json_path = json_path;
            }

            public void setLong_figure(String long_figure) {
                this.long_figure = long_figure;
            }

            public void setNew_img(String new_img) {
                this.new_img = new_img;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public void setTop_img(String top_img) {
                this.top_img = top_img;
            }

            public void setZhiding(String zhiding) {
                this.zhiding = zhiding;
            }
        }

        public List<TopBean> getList() {
            return this.list;
        }

        public List<HandoutImage> getSider() {
            return this.sider;
        }

        public List<TopBean> getTop() {
            return this.f15298top;
        }

        public void setList(List<TopBean> list) {
            this.list = list;
        }

        public void setSider(List<HandoutImage> sider) {
            this.sider = sider;
        }

        public void setTop(List<TopBean> top2) {
            this.f15298top = top2;
        }
    }

    public String getCode() {
        return this.code;
    }

    public DataBean getData() {
        return this.data;
    }

    public String getMessage() {
        return this.message;
    }

    public String getServer_time() {
        return this.server_time;
    }

    public String getTime() {
        return this.time;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setServer_time(String server_time) {
        this.server_time = server_time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
