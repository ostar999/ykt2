package com.psychiatrygarden.activity.vip.bean;

import java.io.Serializable;
import java.util.List;

/* loaded from: classes5.dex */
public class MemPurchasrBean implements Serializable {
    private int code;
    private DataBean data;
    private String message;
    private int server_time;

    public static class DataBean implements Serializable {
        private String desc_url;
        private List<GoodsBean> goods;
        private int is_vip;
        private List<PrivilegeBean> privilege;
        private String vip_deadLine;
        private String page_title = "";
        public String wechat_corpid = "";
        public String wechat_enterprise_url = "";

        public static class GoodsBean implements Serializable {
            private String cent_price;
            private String goods_id;
            private String goods_name;
            private String per_month;
            private String price;
            private String price_str;
            private String saving;
            private int type = 0;

            public String getCent_price() {
                return this.cent_price;
            }

            public String getGoods_id() {
                return this.goods_id;
            }

            public String getGoods_name() {
                return this.goods_name;
            }

            public String getPer_month() {
                return this.per_month;
            }

            public String getPrice() {
                return this.price;
            }

            public String getPrice_str() {
                return this.price_str;
            }

            public String getSaving() {
                return this.saving;
            }

            public int getType() {
                return this.type;
            }

            public void setCent_price(String cent_price) {
                this.cent_price = cent_price;
            }

            public void setGoods_id(String goods_id) {
                this.goods_id = goods_id;
            }

            public void setGoods_name(String goods_name) {
                this.goods_name = goods_name;
            }

            public void setPer_month(String per_month) {
                this.per_month = per_month;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public void setPrice_str(String price_str) {
                this.price_str = price_str;
            }

            public void setSaving(String saving) {
                this.saving = saving;
            }

            public void setType(int type) {
                this.type = type;
            }
        }

        public static class PrivilegeBean implements Serializable {
            private String img;
            private String img_1;
            private String label;
            private String rights;
            private String usage;
            private String value;

            public String getImg() {
                return this.img;
            }

            public String getImg_1() {
                return this.img_1;
            }

            public String getLabel() {
                return this.label;
            }

            public String getRights() {
                return this.rights;
            }

            public String getUsage() {
                return this.usage;
            }

            public String getValue() {
                return this.value;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public void setImg_1(String img_1) {
                this.img_1 = img_1;
            }

            public void setLabel(String label) {
                this.label = label;
            }

            public void setRights(String rights) {
                this.rights = rights;
            }

            public void setUsage(String usage) {
                this.usage = usage;
            }

            public void setValue(String value) {
                this.value = value;
            }
        }

        public String getDesc_url() {
            return this.desc_url;
        }

        public List<GoodsBean> getGoods() {
            return this.goods;
        }

        public int getIs_vip() {
            return this.is_vip;
        }

        public String getPage_title() {
            return this.page_title;
        }

        public List<PrivilegeBean> getPrivilege() {
            return this.privilege;
        }

        public String getVip_deadLine() {
            return this.vip_deadLine;
        }

        public String getWechat_corpid() {
            return this.wechat_corpid;
        }

        public String getWechat_enterprise_url() {
            return this.wechat_enterprise_url;
        }

        public void setDesc_url(String desc_url) {
            this.desc_url = desc_url;
        }

        public void setGoods(List<GoodsBean> goods) {
            this.goods = goods;
        }

        public void setIs_vip(int is_vip) {
            this.is_vip = is_vip;
        }

        public void setPage_title(String page_title) {
            this.page_title = page_title;
        }

        public void setPrivilege(List<PrivilegeBean> privilege) {
            this.privilege = privilege;
        }

        public void setVip_deadLine(String vip_deadLine) {
            this.vip_deadLine = vip_deadLine;
        }

        public void setWechat_corpid(String wechat_corpid) {
            this.wechat_corpid = wechat_corpid;
        }

        public void setWechat_enterprise_url(String wechat_enterprise_url) {
            this.wechat_enterprise_url = wechat_enterprise_url;
        }
    }

    public int getCode() {
        return this.code;
    }

    public DataBean getData() {
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

    public void setData(DataBean data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setServer_time(int server_time) {
        this.server_time = server_time;
    }
}
