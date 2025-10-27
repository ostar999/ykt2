package com.psychiatrygarden.bean;

import java.io.Serializable;
import java.util.List;

/* loaded from: classes5.dex */
public class CirclrListBean implements Serializable {
    private String code;
    private List<DataBean> data;
    private String message;
    private String server_time;
    private String time;

    public static class DataBean implements Serializable {
        private String author_id;
        private String author_looked;
        private String avatar;
        private String book_review_count;
        private String canme;
        private String cid;
        private String cname;
        private String comment_time;
        private String content;
        private List<String> cover;
        private String ctime;
        private String describe;
        private String download_count;
        private List<PushBookData> ebook_data;
        private String exp_id;
        private String heat_top;
        private String hot_img;
        private String icon;
        private List<String> icon_img;
        private String id;
        private String introduction;
        private String is_enclosure;
        private String is_long;
        private String is_prohibit;
        private String is_read;
        private String is_rights;
        private String is_show_more;
        private String module_type;
        private String new_img;
        private String nickname;
        private String origin_id;
        private List<DataBean> push_data;
        private String read_count;
        private String school;
        private String size;
        private int sort;
        private String thumbnail;
        private String title;
        private String top_img;
        private String type_name;
        private String url;
        private String user_identity;
        private String user_identity_color;
        private String comment_count = "0";
        private String no_access = "0";
        private String json_path = "";
        private String html_path = "";
        private String h5_path = "";
        private String is_rich_text = "0";
        private String app_id = "";
        private String type = "";
        private boolean isSelected = false;
        private String is_authentication = "";
        private String is_vip = "";
        private String is_svip = "";

        public String getApp_id() {
            return this.app_id;
        }

        public String getAuthor_id() {
            return this.author_id;
        }

        public String getAuthor_looked() {
            return this.author_looked;
        }

        public String getAvatar() {
            return this.avatar;
        }

        public String getBook_review_count() {
            return this.book_review_count;
        }

        public String getCanme() {
            return this.canme;
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

        public String getComment_time() {
            return this.comment_time;
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

        public String getDescribe() {
            return this.describe;
        }

        public String getDownload_count() {
            return this.download_count;
        }

        public List<PushBookData> getEbook_data() {
            return this.ebook_data;
        }

        public String getExp_id() {
            return this.exp_id;
        }

        public String getH5_path() {
            return this.h5_path;
        }

        public String getHeat_top() {
            return this.heat_top;
        }

        public String getHot_img() {
            return this.hot_img;
        }

        public String getHtml_path() {
            return this.html_path;
        }

        public String getIcon() {
            return this.icon;
        }

        public List<String> getIcon_img() {
            return this.icon_img;
        }

        public String getId() {
            return this.id;
        }

        public String getIntroduction() {
            return this.introduction;
        }

        public String getIs_authentication() {
            return this.is_authentication;
        }

        public String getIs_enclosure() {
            return this.is_enclosure;
        }

        public String getIs_long() {
            return this.is_long;
        }

        public String getIs_prohibit() {
            return this.is_prohibit;
        }

        public String getIs_read() {
            return this.is_read;
        }

        public String getIs_rich_text() {
            return this.is_rich_text;
        }

        public String getIs_rights() {
            return this.is_rights;
        }

        public String getIs_show_more() {
            return this.is_show_more;
        }

        public String getIs_svip() {
            return this.is_svip;
        }

        public String getIs_vip() {
            return this.is_vip;
        }

        public String getJson_path() {
            return this.json_path;
        }

        public String getModule_type() {
            return this.module_type;
        }

        public String getNew_img() {
            return this.new_img;
        }

        public String getNickname() {
            return this.nickname;
        }

        public String getNo_access() {
            return this.no_access;
        }

        public String getOrigin_id() {
            return this.origin_id;
        }

        public List<DataBean> getPush_data() {
            return this.push_data;
        }

        public String getRead_count() {
            return this.read_count;
        }

        public String getSchool() {
            return this.school;
        }

        public String getSize() {
            return this.size;
        }

        public int getSort() {
            return this.sort;
        }

        public String getThumbnail() {
            return this.thumbnail;
        }

        public String getTitle() {
            return this.title;
        }

        public String getTop_img() {
            return this.top_img;
        }

        public String getType() {
            return this.type;
        }

        public String getType_name() {
            return this.type_name;
        }

        public String getUrl() {
            return this.url;
        }

        public String getUser_identity() {
            return this.user_identity;
        }

        public String getUser_identity_color() {
            return this.user_identity_color;
        }

        public boolean isSelected() {
            return this.isSelected;
        }

        public void setApp_id(String app_id) {
            this.app_id = app_id;
        }

        public void setAuthor_id(String author_id) {
            this.author_id = author_id;
        }

        public void setAuthor_looked(String author_looked) {
            this.author_looked = author_looked;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public void setBook_review_count(String book_review_count) {
            this.book_review_count = book_review_count;
        }

        public void setCanme(String canme) {
            this.canme = canme;
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

        public void setComment_time(String comment_time) {
            this.comment_time = comment_time;
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

        public void setDescribe(String describe) {
            this.describe = describe;
        }

        public void setDownload_count(String download_count) {
            this.download_count = download_count;
        }

        public void setEbook_data(List<PushBookData> ebook_data) {
            this.ebook_data = ebook_data;
        }

        public void setExp_id(String exp_id) {
            this.exp_id = exp_id;
        }

        public DataBean setH5_path(String h5_path) {
            this.h5_path = h5_path;
            return this;
        }

        public void setHeat_top(String heat_top) {
            this.heat_top = heat_top;
        }

        public void setHot_img(String hot_img) {
            this.hot_img = hot_img;
        }

        public DataBean setHtml_path(String html_path) {
            this.html_path = html_path;
            return this;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public void setIcon_img(List<String> icon_img) {
            this.icon_img = icon_img;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setIntroduction(String introduction) {
            this.introduction = introduction;
        }

        public void setIs_authentication(String is_authentication) {
            this.is_authentication = is_authentication;
        }

        public DataBean setIs_enclosure(String is_enclosure) {
            this.is_enclosure = is_enclosure;
            return this;
        }

        public void setIs_long(String is_long) {
            this.is_long = is_long;
        }

        public void setIs_prohibit(String is_prohibit) {
            this.is_prohibit = is_prohibit;
        }

        public void setIs_read(String is_read) {
            this.is_read = is_read;
        }

        public DataBean setIs_rich_text(String is_rich_text) {
            this.is_rich_text = is_rich_text;
            return this;
        }

        public void setIs_rights(String is_rights) {
            this.is_rights = is_rights;
        }

        public void setIs_show_more(String is_show_more) {
            this.is_show_more = is_show_more;
        }

        public void setIs_svip(String is_svip) {
            this.is_svip = is_svip;
        }

        public void setIs_vip(String is_vip) {
            this.is_vip = is_vip;
        }

        public DataBean setJson_path(String json_path) {
            this.json_path = json_path;
            return this;
        }

        public void setModule_type(String module_type) {
            this.module_type = module_type;
        }

        public void setNew_img(String new_img) {
            this.new_img = new_img;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public void setNo_access(String no_access) {
            this.no_access = no_access;
        }

        public void setOrigin_id(String origin_id) {
            this.origin_id = origin_id;
        }

        public void setPush_data(List<DataBean> push_data) {
            this.push_data = push_data;
        }

        public void setRead_count(String read_count) {
            this.read_count = read_count;
        }

        public void setSchool(String school) {
            this.school = school;
        }

        public void setSelected(boolean selected) {
            this.isSelected = selected;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setTop_img(String top_img) {
            this.top_img = top_img;
        }

        public DataBean setType(String type) {
            this.type = type;
            return this;
        }

        public void setType_name(String type_name) {
            this.type_name = type_name;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public void setUser_identity(String user_identity) {
            this.user_identity = user_identity;
        }

        public void setUser_identity_color(String user_identity_color) {
            this.user_identity_color = user_identity_color;
        }
    }

    public String getCode() {
        return this.code;
    }

    public List<DataBean> getData() {
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

    public void setData(List<DataBean> data) {
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
