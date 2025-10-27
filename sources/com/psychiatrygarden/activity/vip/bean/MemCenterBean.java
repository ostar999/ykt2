package com.psychiatrygarden.activity.vip.bean;

import java.io.Serializable;
import java.util.List;

/* loaded from: classes5.dex */
public class MemCenterBean implements Serializable {
    private int code;
    private DataBeanX data;
    private String message;
    private int server_time;

    public static class DataBeanX {
        private String activity;
        private int id;
        private int is_popup;
        private String knowledge_img;
        private String knowledge_img_height;
        private String knowledge_img_width;
        private String name;
        private String pass = "";
        private String permission = "";
        private List<WaysBean> ways;

        public static class WaysBean implements Serializable {
            private DataBean data;
            private String description;
            private String icon;
            private String price;
            private String recommend;
            private String recommend_label;
            private String title;
            private String way;

            public static class DataBean implements Serializable {
                private String enclosure_id;
                private String is_promotion;
                private String price;
                private String type;
                private String poster_html = "";
                public String join_us_type = "";
                public String community_id = "";
                public String ykb_community_id = "";
                public String popup_img = "";
                public String share_title = "";
                public String share_content = "";
                public String share_img = "";
                public String share_url = "";
                public int share_type = 0;
                public String share_popup = "";
                public String goods_id = "";
                public String course_id = "";
                public String ori_course_id = "";
                public String valid_type = "";
                public String valid_date = "";
                public String book_id = "";

                public String getBook_id() {
                    return this.book_id;
                }

                public String getCommunity_id() {
                    return this.community_id;
                }

                public String getCourse_id() {
                    return this.course_id;
                }

                public String getEnclosure_id() {
                    return this.enclosure_id;
                }

                public String getGoods_id() {
                    return this.goods_id;
                }

                public String getIs_promotion() {
                    return this.is_promotion;
                }

                public String getJoin_us_type() {
                    return this.join_us_type;
                }

                public String getOri_course_id() {
                    return this.ori_course_id;
                }

                public String getPopup_img() {
                    return this.popup_img;
                }

                public String getPoster_html() {
                    return this.poster_html;
                }

                public String getPrice() {
                    return this.price;
                }

                public String getShare_content() {
                    return this.share_content;
                }

                public String getShare_img() {
                    return this.share_img;
                }

                public String getShare_popup() {
                    return this.share_popup;
                }

                public String getShare_title() {
                    return this.share_title;
                }

                public int getShare_type() {
                    return this.share_type;
                }

                public String getShare_url() {
                    return this.share_url;
                }

                public String getType() {
                    return this.type;
                }

                public String getValid_date() {
                    return this.valid_date;
                }

                public String getValid_type() {
                    return this.valid_type;
                }

                public String getYkb_community_id() {
                    return this.ykb_community_id;
                }

                public void setBook_id(String book_id) {
                    this.book_id = book_id;
                }

                public void setCommunity_id(String community_id) {
                    this.community_id = community_id;
                }

                public void setCourse_id(String course_id) {
                    this.course_id = course_id;
                }

                public void setEnclosure_id(String enclosure_id) {
                    this.enclosure_id = enclosure_id;
                }

                public void setGoods_id(String goods_id) {
                    this.goods_id = goods_id;
                }

                public void setIs_promotion(String is_promotion) {
                    this.is_promotion = is_promotion;
                }

                public void setJoin_us_type(String join_us_type) {
                    this.join_us_type = join_us_type;
                }

                public void setOri_course_id(String ori_course_id) {
                    this.ori_course_id = ori_course_id;
                }

                public void setPopup_img(String popup_img) {
                    this.popup_img = popup_img;
                }

                public void setPoster_html(String poster_html) {
                    this.poster_html = poster_html;
                }

                public void setPrice(String price) {
                    this.price = price;
                }

                public void setShare_content(String share_content) {
                    this.share_content = share_content;
                }

                public void setShare_img(String share_img) {
                    this.share_img = share_img;
                }

                public void setShare_popup(String share_popup) {
                    this.share_popup = share_popup;
                }

                public void setShare_title(String share_title) {
                    this.share_title = share_title;
                }

                public void setShare_type(int share_type) {
                    this.share_type = share_type;
                }

                public void setShare_url(String share_url) {
                    this.share_url = share_url;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public void setValid_date(String valid_date) {
                    this.valid_date = valid_date;
                }

                public void setValid_type(String valid_type) {
                    this.valid_type = valid_type;
                }

                public void setYkb_community_id(String ykb_community_id) {
                    this.ykb_community_id = ykb_community_id;
                }
            }

            public DataBean getData() {
                return this.data;
            }

            public String getDescription() {
                return this.description;
            }

            public String getIcon() {
                return this.icon;
            }

            public String getPrice() {
                return this.price;
            }

            public String getRecommend() {
                return this.recommend;
            }

            public String getRecommend_label() {
                return this.recommend_label;
            }

            public String getTitle() {
                return this.title;
            }

            public String getWay() {
                return this.way;
            }

            public void setData(DataBean data) {
                this.data = data;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public void setRecommend(String recommend) {
                this.recommend = recommend;
            }

            public void setRecommend_label(String recommend_label) {
                this.recommend_label = recommend_label;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public void setWay(String way) {
                this.way = way;
            }
        }

        public String getActivity() {
            return this.activity;
        }

        public int getId() {
            return this.id;
        }

        public int getIs_popup() {
            return this.is_popup;
        }

        public String getKnowledge_img() {
            return this.knowledge_img;
        }

        public String getKnowledge_img_height() {
            return this.knowledge_img_height;
        }

        public String getKnowledge_img_width() {
            return this.knowledge_img_width;
        }

        public String getName() {
            return this.name;
        }

        public String getPass() {
            return this.pass;
        }

        public String getPermission() {
            return this.permission;
        }

        public List<WaysBean> getWays() {
            return this.ways;
        }

        public void setActivity(String activity) {
            this.activity = activity;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setIs_popup(int is_popup) {
            this.is_popup = is_popup;
        }

        public void setKnowledge_img(String knowledge_img) {
            this.knowledge_img = knowledge_img;
        }

        public void setKnowledge_img_height(String knowledge_img_height) {
            this.knowledge_img_height = knowledge_img_height;
        }

        public void setKnowledge_img_width(String knowledge_img_width) {
            this.knowledge_img_width = knowledge_img_width;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setPass(String pass) {
            this.pass = pass;
        }

        public void setPermission(String permission) {
            this.permission = permission;
        }

        public void setWays(List<WaysBean> ways) {
            this.ways = ways;
        }
    }

    public int getCode() {
        return this.code;
    }

    public DataBeanX getData() {
        return this.data;
    }

    public String getMessage() {
        return this.message;
    }

    public int getServer_time() {
        return this.server_time;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setData(DataBeanX data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setServer_time(int server_time) {
        this.server_time = server_time;
    }
}
