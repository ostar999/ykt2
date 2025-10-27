package com.psychiatrygarden.activity.rank.bean;

import java.io.Serializable;

/* loaded from: classes5.dex */
public class ActivityBean implements Serializable {
    private String code;
    private DataBean data;
    private String message;
    private String server_time;

    public static class DataBean implements Serializable {
        private String activity_description;
        private String buy_it;
        private String goods_id;
        private String id;
        private String join_us;
        private String name;
        private String pass;
        private String poster_html;
        private String purchase_description;
        private String share;
        private ShareInfoBean share_info;
        private String take_part_in;

        public static class ShareInfoBean {
            private String share_title = "";
            private String share_content = "";
            private String share_img = "";
            private String share_url = "";
            private int share_type = 0;
            public String item_id = "";
            public String category = "";

            public String getCategory() {
                return this.category;
            }

            public String getItem_id() {
                return this.item_id;
            }

            public String getShare_content() {
                return this.share_content;
            }

            public String getShare_img() {
                return this.share_img;
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

            public void setCategory(String category) {
                this.category = category;
            }

            public void setItem_id(String item_id) {
                this.item_id = item_id;
            }

            public void setShare_content(String share_content) {
                this.share_content = share_content;
            }

            public void setShare_img(String share_img) {
                this.share_img = share_img;
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
        }

        public String getActivity_description() {
            return this.activity_description;
        }

        public String getBuy_it() {
            return this.buy_it;
        }

        public String getGoods_id() {
            return this.goods_id;
        }

        public String getId() {
            return this.id;
        }

        public String getJoin_us() {
            return this.join_us;
        }

        public String getName() {
            return this.name;
        }

        public String getPass() {
            return this.pass;
        }

        public String getPoster_html() {
            return this.poster_html;
        }

        public String getPurchase_description() {
            return this.purchase_description;
        }

        public String getShare() {
            return this.share;
        }

        public ShareInfoBean getShare_info() {
            return this.share_info;
        }

        public String getTake_part_in() {
            return this.take_part_in;
        }

        public void setActivity_description(String activity_description) {
            this.activity_description = activity_description;
        }

        public void setBuy_it(String buy_it) {
            this.buy_it = buy_it;
        }

        public void setGoods_id(String goods_id) {
            this.goods_id = goods_id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setJoin_us(String join_us) {
            this.join_us = join_us;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setPass(String pass) {
            this.pass = pass;
        }

        public void setPoster_html(String poster_html) {
            this.poster_html = poster_html;
        }

        public void setPurchase_description(String purchase_description) {
            this.purchase_description = purchase_description;
        }

        public void setShare(String share) {
            this.share = share;
        }

        public void setShare_info(ShareInfoBean share_info) {
            this.share_info = share_info;
        }

        public void setTake_part_in(String take_part_in) {
            this.take_part_in = take_part_in;
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
