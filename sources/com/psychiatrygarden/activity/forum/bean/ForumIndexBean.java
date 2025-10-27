package com.psychiatrygarden.activity.forum.bean;

import java.io.Serializable;
import java.util.List;

/* loaded from: classes5.dex */
public class ForumIndexBean implements Serializable {
    private String code;
    private DataBean data;
    private String message;
    private String server_time;

    public static class DataBean implements Serializable {
        private List<ListBean> list;
        private List<ParentBean> parent;
        private String title;

        public static class ListBean implements Serializable {
            private String access = "";
            private String article_count;
            private String comment_count;
            private String id;
            private String is_follow;
            private String join_the_state;
            private String logo;
            private String name;
            private String new_message;
            private String user_count;

            public String getAccess() {
                return this.access;
            }

            public String getArticle_count() {
                return this.article_count;
            }

            public String getComment_count() {
                return this.comment_count;
            }

            public String getId() {
                return this.id;
            }

            public String getIs_follow() {
                return this.is_follow;
            }

            public String getJoin_the_state() {
                return this.join_the_state;
            }

            public String getLogo() {
                return this.logo;
            }

            public String getName() {
                return this.name;
            }

            public String getNew_message() {
                return this.new_message;
            }

            public String getUser_count() {
                return this.user_count;
            }

            public void setAccess(String access) {
                this.access = access;
            }

            public void setArticle_count(String article_count) {
                this.article_count = article_count;
            }

            public void setComment_count(String comment_count) {
                this.comment_count = comment_count;
            }

            public void setId(String id) {
                this.id = id;
            }

            public void setIs_follow(String is_follow) {
                this.is_follow = is_follow;
            }

            public void setJoin_the_state(String join_the_state) {
                this.join_the_state = join_the_state;
            }

            public void setLogo(String logo) {
                this.logo = logo;
            }

            public void setName(String name) {
                this.name = name;
            }

            public void setNew_message(String new_message) {
                this.new_message = new_message;
            }

            public void setUser_count(String user_count) {
                this.user_count = user_count;
            }
        }

        public static class ParentBean implements Serializable {
            private String id;
            private String name;
            private int selected = 0;

            public String getId() {
                return this.id;
            }

            public String getName() {
                return this.name;
            }

            public int getSelected() {
                return this.selected;
            }

            public void setId(String id) {
                this.id = id;
            }

            public void setName(String name) {
                this.name = name;
            }

            public void setSelected(int selected) {
                this.selected = selected;
            }
        }

        public List<ListBean> getList() {
            return this.list;
        }

        public List<ParentBean> getParent() {
            return this.parent;
        }

        public String getTitle() {
            return this.title;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public void setParent(List<ParentBean> parent) {
            this.parent = parent;
        }

        public void setTitle(String title) {
            this.title = title;
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
}
