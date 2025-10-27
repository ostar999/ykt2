package com.psychiatrygarden.bean;

import android.text.TextUtils;
import com.psychiatrygarden.bean.CirclrListBean;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes5.dex */
public class CircleInfoBean implements Serializable {
    private String code;
    private DataBean data = new DataBean();
    private String message;
    private String server_time;

    public static class DataBean implements Serializable {
        private String author_id;
        private String avatar;
        private String content;
        private String ctime;
        private List<PushBookData> ebook;
        private List<Enclosure> enclosure;
        private String group_id;
        private String group_logo;
        private String group_name;
        private String id;
        private String img_position;
        private String is_img;
        private String is_opposition;
        private String is_support;
        private List<DataLink> link;
        private String nickname;
        private OptionsBeanX options;
        private List<CirclrListBean.DataBean> push_article_list;
        private String rich_text;
        private String school_name;
        private String share_url;
        private String source;
        private String title;
        private String type;
        private String view_count;
        private List<CidBean> cid = new ArrayList();
        private String comment_count = "0";
        private String support_count = "0";
        private String opposition_count = "0";
        private String choice = "1";
        public List<ImgBean> img = new ArrayList();
        private String user_identity = "0";
        private String displayed = "0";
        private String topic_id = "";
        private String topic_name = "";
        private String is_anonymous = "";
        private String is_authentication = "";
        public List<ImgBean> rich_img = new ArrayList();
        private String user_identity_color = "#333333";
        private String is_enclosure = "0";
        private String author_is_vip = "0";
        private String author_is_svip = "0";
        private String is_vip = "0";
        private String is_prohibit = "0";
        private String is_comment = "0";
        private String is_praise = "0";
        private String is_collection = "0";

        public static class CidBean implements Serializable {
            public String id;
            public String title;

            public String getId() {
                return this.id;
            }

            public String getTitle() {
                return this.title;
            }

            public void setId(String id) {
                this.id = id;
            }

            public void setTitle(String title) {
                this.title = title;
            }
        }

        public static class DataLink implements Serializable {
            private String id;
            private String title;
            private String url;

            public String getId() {
                return this.id;
            }

            public String getTitle() {
                return this.title;
            }

            public String getUrl() {
                return this.url;
            }

            public void setId(String id) {
                this.id = id;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }

        public static class Enclosure implements Serializable {
            private String ctime;
            private String desc;
            private String download_count;
            private String icon;
            private String id;
            private String is_prohibit;
            private String is_rights;
            private String nickname;
            private String size;
            private String title;
            private String type_name;
            private String url;
            private String view_num = "";

            public String getCtime() {
                return this.ctime;
            }

            public String getDesc() {
                return this.desc;
            }

            public String getDownload_count() {
                return this.download_count;
            }

            public String getIcon() {
                return this.icon;
            }

            public String getId() {
                return this.id;
            }

            public String getIs_prohibit() {
                return this.is_prohibit;
            }

            public String getIs_rights() {
                return this.is_rights;
            }

            public String getNickname() {
                return this.nickname;
            }

            public String getSize() {
                return this.size;
            }

            public String getTitle() {
                return this.title;
            }

            public String getType_name() {
                return this.type_name;
            }

            public String getUrl() {
                return this.url;
            }

            public String getView_num() {
                return this.view_num;
            }

            public void setCtime(String ctime) {
                this.ctime = ctime;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public void setDownload_count(String download_count) {
                this.download_count = download_count;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public void setId(String id) {
                this.id = id;
            }

            public void setIs_prohibit(String is_prohibit) {
                this.is_prohibit = is_prohibit;
            }

            public void setIs_rights(String is_rights) {
                this.is_rights = is_rights;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public void setSize(String size) {
                this.size = size;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public void setType_name(String type_name) {
                this.type_name = type_name;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public void setView_num(String view_num) {
                this.view_num = view_num;
            }
        }

        public static class ImgBean implements Serializable {
            public String image = "";
            private String width = "";
            private String height = "";

            public String getHeight() {
                return this.height;
            }

            public String getImage() {
                return this.image;
            }

            public String getWidth() {
                return this.width;
            }

            public void setHeight(String height) {
                this.height = height;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public void setWidth(String width) {
                this.width = width;
            }
        }

        public static class OptionsBeanX implements Serializable {
            private String is_vote;
            private List<OptionsBean> options;
            private String user_count;
            private String vote_option;

            public static class OptionsBean implements Serializable {
                private String id;
                private boolean isTrue;
                private String option;
                private String ratio;
                private String vote_nums;

                public String getId() {
                    return this.id;
                }

                public String getOption() {
                    return this.option;
                }

                public String getRatio() {
                    return this.ratio;
                }

                public String getVote_nums() {
                    return this.vote_nums;
                }

                public boolean isTrue() {
                    return this.isTrue;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public void setOption(String option) {
                    this.option = option;
                }

                public void setRatio(String ratio) {
                    this.ratio = ratio;
                }

                public void setTrue(boolean aTrue) {
                    this.isTrue = aTrue;
                }

                public void setVote_nums(String vote_nums) {
                    this.vote_nums = vote_nums;
                }
            }

            public String getIs_vote() {
                return this.is_vote;
            }

            public List<OptionsBean> getOptions() {
                return this.options;
            }

            public String getUser_count() {
                return this.user_count;
            }

            public String getVote_option() {
                return this.vote_option;
            }

            public void setIs_vote(String is_vote) {
                this.is_vote = is_vote;
            }

            public void setOptions(List<OptionsBean> options) {
                this.options = options;
            }

            public void setUser_count(String user_count) {
                this.user_count = user_count;
            }

            public void setVote_option(String vote_option) {
                this.vote_option = vote_option;
            }
        }

        public String getAuthor_id() {
            return this.author_id;
        }

        public String getAuthor_is_svip() {
            return this.author_is_svip;
        }

        public String getAuthor_is_vip() {
            return this.author_is_vip;
        }

        public String getAvatar() {
            return this.avatar;
        }

        public String getChoice() {
            return this.choice;
        }

        public List<CidBean> getCid() {
            return this.cid;
        }

        public String getComment_count() {
            return this.comment_count;
        }

        public String getContent() {
            return this.content;
        }

        public String getCtime() {
            return this.ctime;
        }

        public String getDisplayed() {
            return this.displayed;
        }

        public List<PushBookData> getEbook() {
            return this.ebook;
        }

        public List<Enclosure> getEnclosure() {
            return this.enclosure;
        }

        public String getGroup_id() {
            return this.group_id;
        }

        public String getGroup_logo() {
            return this.group_logo;
        }

        public String getGroup_name() {
            return this.group_name;
        }

        public String getId() {
            return this.id;
        }

        public List<ImgBean> getImg() {
            return this.img;
        }

        public String getImg_position() {
            if (TextUtils.isEmpty(this.img_position)) {
                this.img_position = "1";
            }
            return this.img_position;
        }

        public String getIs_anonymous() {
            return this.is_anonymous;
        }

        public String getIs_authentication() {
            return this.is_authentication;
        }

        public String getIs_collection() {
            return this.is_collection;
        }

        public String getIs_comment() {
            return this.is_comment;
        }

        public String getIs_enclosure() {
            return this.is_enclosure;
        }

        public String getIs_img() {
            return this.is_img;
        }

        public String getIs_opposition() {
            return this.is_opposition;
        }

        public String getIs_praise() {
            return this.is_praise;
        }

        public String getIs_prohibit() {
            return this.is_prohibit;
        }

        public String getIs_support() {
            return this.is_support;
        }

        public String getIs_vip() {
            return this.is_vip;
        }

        public List<DataLink> getLink() {
            return this.link;
        }

        public String getNickname() {
            return this.nickname;
        }

        public String getOpposition_count() {
            return this.opposition_count;
        }

        public OptionsBeanX getOptions() {
            return this.options;
        }

        public List<CirclrListBean.DataBean> getPush_article_list() {
            return this.push_article_list;
        }

        public List<ImgBean> getRich_img() {
            return this.rich_img;
        }

        public String getRich_text() {
            if (TextUtils.isEmpty(this.rich_text)) {
                this.rich_text = "";
            }
            return this.rich_text;
        }

        public String getSchool_name() {
            return this.school_name;
        }

        public String getShare_url() {
            return this.share_url;
        }

        public String getSupport_count() {
            return this.support_count;
        }

        public String getTitle() {
            return this.title;
        }

        public String getTopic_id() {
            return this.topic_id;
        }

        public String getTopic_name() {
            return this.topic_name;
        }

        public String getType() {
            return this.type;
        }

        public String getUser_identity() {
            return this.user_identity;
        }

        public String getUser_identity_color() {
            return this.user_identity_color;
        }

        public String getView_count() {
            return this.view_count;
        }

        public void setAuthor_id(String author_id) {
            this.author_id = author_id;
        }

        public void setAuthor_is_svip(String author_is_svip) {
            this.author_is_svip = author_is_svip;
        }

        public void setAuthor_is_vip(String author_is_vip) {
            this.author_is_vip = author_is_vip;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public void setChoice(String choice) {
            this.choice = choice;
        }

        public void setCid(List<CidBean> cid) {
            this.cid = cid;
        }

        public void setComment_count(String comment_count) {
            this.comment_count = comment_count;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public void setCtime(String ctime) {
            this.ctime = ctime;
        }

        public void setDisplayed(String displayed) {
            this.displayed = displayed;
        }

        public void setEbook(List<PushBookData> ebook) {
            this.ebook = ebook;
        }

        public void setEnclosure(List<Enclosure> enclosure) {
            this.enclosure = enclosure;
        }

        public void setGroup_id(String group_id) {
            this.group_id = group_id;
        }

        public void setGroup_logo(String group_logo) {
            this.group_logo = group_logo;
        }

        public void setGroup_name(String group_name) {
            this.group_name = group_name;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setImg(List<ImgBean> img) {
            this.img = img;
        }

        public void setImg_position(String img_position) {
            this.img_position = img_position;
        }

        public void setIs_anonymous(String is_anonymous) {
            this.is_anonymous = is_anonymous;
        }

        public void setIs_authentication(String is_authentication) {
            this.is_authentication = is_authentication;
        }

        public void setIs_collection(String is_collection) {
            this.is_collection = is_collection;
        }

        public void setIs_comment(String is_comment) {
            this.is_comment = is_comment;
        }

        public void setIs_enclosure(String is_enclosure) {
            this.is_enclosure = is_enclosure;
        }

        public void setIs_img(String is_img) {
            this.is_img = is_img;
        }

        public void setIs_opposition(String is_opposition) {
            this.is_opposition = is_opposition;
        }

        public void setIs_praise(String is_praise) {
            this.is_praise = is_praise;
        }

        public void setIs_prohibit(String is_prohibit) {
            this.is_prohibit = is_prohibit;
        }

        public void setIs_support(String is_support) {
            this.is_support = is_support;
        }

        public void setIs_vip(String is_vip) {
            this.is_vip = is_vip;
        }

        public void setLink(List<DataLink> link) {
            this.link = link;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public void setOpposition_count(String opposition_count) {
            this.opposition_count = opposition_count;
        }

        public void setOptions(OptionsBeanX options) {
            this.options = options;
        }

        public void setPush_article_list(List<CirclrListBean.DataBean> push_article_list) {
            this.push_article_list = push_article_list;
        }

        public void setRich_img(List<ImgBean> rich_img) {
            this.rich_img = rich_img;
        }

        public void setRich_text(String rich_text) {
            this.rich_text = rich_text;
        }

        public void setSchool_name(String school_name) {
            this.school_name = school_name;
        }

        public void setShare_url(String share_url) {
            this.share_url = share_url;
        }

        public void setSupport_count(String support_count) {
            this.support_count = support_count;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setTopic_id(String topic_id) {
            this.topic_id = topic_id;
        }

        public void setTopic_name(String topic_name) {
            this.topic_name = topic_name;
        }

        public void setType(String type) {
            this.type = type;
        }

        public void setUser_identity(String user_identity) {
            this.user_identity = user_identity;
        }

        public void setUser_identity_color(String user_identity_color) {
            this.user_identity_color = user_identity_color;
        }

        public void setView_count(String view_count) {
            this.view_count = view_count;
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
