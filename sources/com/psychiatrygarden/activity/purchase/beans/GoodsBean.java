package com.psychiatrygarden.activity.purchase.beans;

import com.psychiatrygarden.bean.OnlineServiceBean;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes5.dex */
public class GoodsBean implements Serializable {
    private String code;
    private DataBean data;
    private String message;
    private String server_time;

    public static class DataBean implements Serializable {
        private String activityCountDown;
        private String buy_permission;
        private CoumService customer_service;
        private String description;
        private String goods_id;
        private List<String> goods_img;
        private String goods_name;
        private String goods_thumbnail;
        private GVideoBean goods_video;
        private String inventory;
        private String now_price;
        private String original_price;
        private String price;
        private String sales_volume;
        private String statement;
        private List<TeacherBean> teachers;
        private String cat_id = "";
        private String goods_type = "1";
        private List<String> label = new ArrayList();
        private List<MealBean> meal = new ArrayList();
        private String group_purchase = "0";
        private String wechat_corpid = "";
        private String wechat_enterprise_url = "";
        private List<SkusBean> skus = new ArrayList();
        private List<AttributesBean> attributes = new ArrayList();
        private String after_purchase_goto = "0";
        private OnlineServiceBean cs = new OnlineServiceBean();

        public static class AttributesBean implements Serializable {
            public String attr_name = "";
            public List<String> list = new ArrayList();
            public List<String> recommend = new ArrayList();
            public List<AttributesChildBean> labelList = new ArrayList();

            public static class AttributesChildBean implements Serializable {
                public String name;
                private String recommend = "0";
                private int selected = 0;

                public String getName() {
                    return this.name;
                }

                public String getRecommend() {
                    return this.recommend;
                }

                public int getSelected() {
                    return this.selected;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public void setRecommend(String recommend) {
                    this.recommend = recommend;
                }

                public void setSelected(int selected) {
                    this.selected = selected;
                }
            }

            public String getAttr_name() {
                return this.attr_name;
            }

            public List<AttributesChildBean> getLabelList() {
                return this.labelList;
            }

            public List<String> getList() {
                return this.list;
            }

            public List<String> getRecommend() {
                return this.recommend;
            }

            public void setAttr_name(String attr_name) {
                this.attr_name = attr_name;
            }

            public void setLabelList(List<AttributesChildBean> labelList) {
                this.labelList = labelList;
            }

            public void setList(List<String> list) {
                this.list = list;
            }

            public void setRecommend(List<String> recommend) {
                this.recommend = recommend;
            }
        }

        public static class CoumService implements Serializable {
            public String chatid = "";
            public List<String> leyu;
            public List<CustomerBean> qq;
            public List<CustomerBean> qq_group;

            public String getChatid() {
                return this.chatid;
            }

            public List<String> getLeyu() {
                return this.leyu;
            }

            public List<CustomerBean> getQq() {
                return this.qq;
            }

            public List<CustomerBean> getQq_group() {
                return this.qq_group;
            }

            public void setChatid(String chatid) {
                this.chatid = chatid;
            }

            public void setLeyu(List<String> leyu) {
                this.leyu = leyu;
            }

            public void setQq(List<CustomerBean> qq) {
                this.qq = qq;
            }

            public void setQq_group(List<CustomerBean> qq_group) {
                this.qq_group = qq_group;
            }
        }

        public static class CustomerBean implements Serializable {
            public String label;
            public String number;

            public String getLabel() {
                return this.label;
            }

            public String getNumber() {
                return this.number;
            }

            public void setLabel(String label) {
                this.label = label;
            }

            public void setNumber(String number) {
                this.number = number;
            }
        }

        public static class MealBean implements Serializable {
            private String buy_permission;
            private String cat_id;
            private String description;
            private String ebook_id;
            private String enclosure_id;
            private String goods_id;
            private List<String> goods_img;
            private String goods_name;
            private String goods_thumbnail;
            private String inventory;
            private String is_promote;
            private String now_price;
            private String original_price;
            private String price;
            private String sales_volume;
            private String service_month;
            private String goods_type = "1";
            private String quantity = "1";
            private String recommend = "0";
            private String group_purchase = "0";
            private String sku_id = "";

            public String getBuy_permission() {
                return this.buy_permission;
            }

            public String getCat_id() {
                return this.cat_id;
            }

            public String getDescription() {
                return this.description;
            }

            public String getEbook_id() {
                return this.ebook_id;
            }

            public String getEnclosure_id() {
                return this.enclosure_id;
            }

            public String getGoods_id() {
                return this.goods_id;
            }

            public List<String> getGoods_img() {
                return this.goods_img;
            }

            public String getGoods_name() {
                return this.goods_name;
            }

            public String getGoods_thumbnail() {
                return this.goods_thumbnail;
            }

            public String getGoods_type() {
                return this.goods_type;
            }

            public String getGroup_purchase() {
                return this.group_purchase;
            }

            public String getInventory() {
                return this.inventory;
            }

            public String getIs_promote() {
                return this.is_promote;
            }

            public String getNow_price() {
                return this.now_price;
            }

            public String getOriginal_price() {
                return this.original_price;
            }

            public String getPrice() {
                return this.price;
            }

            public String getQuantity() {
                return this.quantity;
            }

            public String getRecommend() {
                return this.recommend;
            }

            public String getSales_volume() {
                return this.sales_volume;
            }

            public String getService_month() {
                return this.service_month;
            }

            public String getSku_id() {
                return this.sku_id;
            }

            public void setBuy_permission(String buy_permission) {
                this.buy_permission = buy_permission;
            }

            public void setCat_id(String cat_id) {
                this.cat_id = cat_id;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public void setEbook_id(String ebook_id) {
                this.ebook_id = ebook_id;
            }

            public void setEnclosure_id(String enclosure_id) {
                this.enclosure_id = enclosure_id;
            }

            public void setGoods_id(String goods_id) {
                this.goods_id = goods_id;
            }

            public void setGoods_img(List<String> goods_img) {
                this.goods_img = goods_img;
            }

            public void setGoods_name(String goods_name) {
                this.goods_name = goods_name;
            }

            public void setGoods_thumbnail(String goods_thumbnail) {
                this.goods_thumbnail = goods_thumbnail;
            }

            public void setGoods_type(String goods_type) {
                this.goods_type = goods_type;
            }

            public void setGroup_purchase(String group_purchase) {
                this.group_purchase = group_purchase;
            }

            public void setInventory(String inventory) {
                this.inventory = inventory;
            }

            public void setIs_promote(String is_promote) {
                this.is_promote = is_promote;
            }

            public void setNow_price(String now_price) {
                this.now_price = now_price;
            }

            public void setOriginal_price(String original_price) {
                this.original_price = original_price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public void setQuantity(String quantity) {
                this.quantity = quantity;
            }

            public void setRecommend(String recommend) {
                this.recommend = recommend;
            }

            public void setSales_volume(String sales_volume) {
                this.sales_volume = sales_volume;
            }

            public void setService_month(String service_month) {
                this.service_month = service_month;
            }

            public void setSku_id(String sku_id) {
                this.sku_id = sku_id;
            }
        }

        public static class SkusBean implements Serializable {
            public List<String> attr_values;
            public int inventory;
            public String sku_id = "";
            public String now_price = "";
            public String price = "";
            public String sales_volume = "";
            public String use_apple_pay = "";
            public ApplePayInfo apple_pay_info = new ApplePayInfo();
            private String recommend = "0";
            private String labelName = "";

            public static class ApplePayInfo implements Serializable {
                public String apple_pay_goods_name;
                public String price;

                public String getApple_pay_goods_name() {
                    return this.apple_pay_goods_name;
                }

                public String getPrice() {
                    return this.price;
                }

                public void setApple_pay_goods_name(String apple_pay_goods_name) {
                    this.apple_pay_goods_name = apple_pay_goods_name;
                }

                public void setPrice(String price) {
                    this.price = price;
                }
            }

            public ApplePayInfo getApple_pay_info() {
                return this.apple_pay_info;
            }

            public List<String> getAttr_values() {
                return this.attr_values;
            }

            public int getInventory() {
                return this.inventory;
            }

            public String getLabelName() {
                return this.labelName;
            }

            public String getNow_price() {
                return this.now_price;
            }

            public String getPrice() {
                return this.price;
            }

            public String getRecommend() {
                return this.recommend;
            }

            public String getSales_volume() {
                return this.sales_volume;
            }

            public String getSku_id() {
                return this.sku_id;
            }

            public String getUse_apple_pay() {
                return this.use_apple_pay;
            }

            public void setApple_pay_info(ApplePayInfo apple_pay_info) {
                this.apple_pay_info = apple_pay_info;
            }

            public void setAttr_values(List<String> attr_values) {
                this.attr_values = attr_values;
            }

            public void setInventory(int inventory) {
                this.inventory = inventory;
            }

            public void setLabelName(String labelName) {
                this.labelName = labelName;
            }

            public void setNow_price(String now_price) {
                this.now_price = now_price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public void setRecommend(String recommend) {
                this.recommend = recommend;
            }

            public void setSales_volume(String sales_volume) {
                this.sales_volume = sales_volume;
            }

            public void setSku_id(String sku_id) {
                this.sku_id = sku_id;
            }

            public void setUse_apple_pay(String use_apple_pay) {
                this.use_apple_pay = use_apple_pay;
            }
        }

        public String getActivityCountDown() {
            return this.activityCountDown;
        }

        public String getAfter_purchase_goto() {
            return this.after_purchase_goto;
        }

        public List<AttributesBean> getAttributes() {
            return this.attributes;
        }

        public String getBuy_permission() {
            return this.buy_permission;
        }

        public String getCat_id() {
            return this.cat_id;
        }

        public OnlineServiceBean getCs() {
            return this.cs;
        }

        public CoumService getCustomer_service() {
            return this.customer_service;
        }

        public String getDescription() {
            return this.description;
        }

        public String getGoods_id() {
            return this.goods_id;
        }

        public List<String> getGoods_img() {
            return this.goods_img;
        }

        public String getGoods_name() {
            return this.goods_name;
        }

        public String getGoods_thumbnail() {
            return this.goods_thumbnail;
        }

        public String getGoods_type() {
            return this.goods_type;
        }

        public GVideoBean getGoods_video() {
            return this.goods_video;
        }

        public String getGroup_purchase() {
            return this.group_purchase;
        }

        public String getInventory() {
            return this.inventory;
        }

        public List<String> getLabel() {
            return this.label;
        }

        public List<MealBean> getMeal() {
            return this.meal;
        }

        public String getNow_price() {
            return this.now_price;
        }

        public String getOriginal_price() {
            return this.original_price;
        }

        public String getPrice() {
            return this.price;
        }

        public String getSales_volume() {
            return this.sales_volume;
        }

        public List<SkusBean> getSkus() {
            return this.skus;
        }

        public String getStatement() {
            return this.statement;
        }

        public List<TeacherBean> getTeachers() {
            return this.teachers;
        }

        public String getWechat_corpid() {
            return this.wechat_corpid;
        }

        public String getWechat_enterprise_url() {
            return this.wechat_enterprise_url;
        }

        public void setActivityCountDown(String activityCountDown) {
            this.activityCountDown = activityCountDown;
        }

        public void setAfter_purchase_goto(String after_purchase_goto) {
            this.after_purchase_goto = after_purchase_goto;
        }

        public void setAttributes(List<AttributesBean> attributes) {
            this.attributes = attributes;
        }

        public void setBuy_permission(String buy_permission) {
            this.buy_permission = buy_permission;
        }

        public void setCat_id(String cat_id) {
            this.cat_id = cat_id;
        }

        public void setCs(OnlineServiceBean cs) {
            this.cs = cs;
        }

        public void setCustomer_service(CoumService customer_service) {
            this.customer_service = customer_service;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public void setGoods_id(String goods_id) {
            this.goods_id = goods_id;
        }

        public void setGoods_img(List<String> goods_img) {
            this.goods_img = goods_img;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public void setGoods_thumbnail(String goods_thumbnail) {
            this.goods_thumbnail = goods_thumbnail;
        }

        public void setGoods_type(String goods_type) {
            this.goods_type = goods_type;
        }

        public void setGoods_video(GVideoBean goods_video) {
            this.goods_video = goods_video;
        }

        public void setGroup_purchase(String group_purchase) {
            this.group_purchase = group_purchase;
        }

        public void setInventory(String inventory) {
            this.inventory = inventory;
        }

        public void setLabel(List<String> label) {
            this.label = label;
        }

        public void setMeal(List<MealBean> meal) {
            this.meal = meal;
        }

        public void setNow_price(String now_price) {
            this.now_price = now_price;
        }

        public void setOriginal_price(String original_price) {
            this.original_price = original_price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public void setSales_volume(String sales_volume) {
            this.sales_volume = sales_volume;
        }

        public void setSkus(List<SkusBean> skus) {
            this.skus = skus;
        }

        public void setStatement(String statement) {
            this.statement = statement;
        }

        public void setTeachers(List<TeacherBean> teachers) {
            this.teachers = teachers;
        }

        public void setWechat_corpid(String wechat_corpid) {
            this.wechat_corpid = wechat_corpid;
        }

        public void setWechat_enterprise_url(String wechat_enterprise_url) {
            this.wechat_enterprise_url = wechat_enterprise_url;
        }
    }

    public static class GVideoBean implements Serializable {
        private String img;
        private String video;

        public String getImg() {
            return this.img;
        }

        public String getVideo() {
            return this.video;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public void setVideo(String video) {
            this.video = video;
        }
    }

    public static class TeacherBean {
        private String avatar;
        private String description;
        private String id;
        private String name;

        public String getAvatar() {
            return this.avatar;
        }

        public String getDescription() {
            return this.description;
        }

        public String getId() {
            return this.id;
        }

        public String getName() {
            return this.name;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
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
