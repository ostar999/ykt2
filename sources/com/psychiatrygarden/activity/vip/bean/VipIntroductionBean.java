package com.psychiatrygarden.activity.vip.bean;

import com.psychiatrygarden.bean.RedEnvelopeCouponsBean;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes5.dex */
public class VipIntroductionBean implements Serializable {
    private int code;
    private DataDTO data;
    private String message;
    private int server_time;

    public static class DataDTO implements Serializable {
        private String discount_description;
        private String page_title;
        private List<List<String>> rights_vs_table = new ArrayList();
        private String rule;
        private VipDTO svip;
        private VipDTO vip;
        private String wechat_corpid;
        private String wechat_enterprise_url;

        public static class SvipDTO implements Serializable {
            private int available;
            private List<GoodsDTO> goods;
            private int is_vip;
            private List<PrivilegeDTO> privilege;
            private String simple_declaration;
            private VideoDTO video;
            private String vip_deadline;
            private int vip_due_soon;

            public static class GoodsDTO implements Serializable {
                private List<ButtonDTO> button = new ArrayList();
                private String cent_price;
                private String day;
                private int goods_id;
                private String goods_name;
                private int month;
                private String per_month;
                private String price;
                private String price_str;
                private String saving;

                public static class ButtonDTO implements Serializable {
                    private String deduction_id;
                    private String label;
                    private String upgrade_type;
                    private int valid;

                    public String getDeduction_id() {
                        return this.deduction_id;
                    }

                    public String getLabel() {
                        return this.label;
                    }

                    public String getUpgrade_type() {
                        return this.upgrade_type;
                    }

                    public int getValid() {
                        return this.valid;
                    }

                    public void setDeduction_id(String deduction_id) {
                        this.deduction_id = deduction_id;
                    }

                    public void setLabel(String label) {
                        this.label = label;
                    }

                    public void setUpgrade_type(String upgrade_type) {
                        this.upgrade_type = upgrade_type;
                    }

                    public void setValid(int valid) {
                        this.valid = valid;
                    }
                }

                public List<ButtonDTO> getButton() {
                    return this.button;
                }

                public String getCent_price() {
                    return this.cent_price;
                }

                public String getDay() {
                    return this.day;
                }

                public int getGoods_id() {
                    return this.goods_id;
                }

                public String getGoods_name() {
                    return this.goods_name;
                }

                public int getMonth() {
                    return this.month;
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

                public void setButton(List<ButtonDTO> button) {
                    this.button = button;
                }

                public void setCent_price(String cent_price) {
                    this.cent_price = cent_price;
                }

                public void setDay(String day) {
                    this.day = day;
                }

                public void setGoods_id(int goods_id) {
                    this.goods_id = goods_id;
                }

                public void setGoods_name(String goods_name) {
                    this.goods_name = goods_name;
                }

                public void setMonth(int month) {
                    this.month = month;
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
            }

            public static class PrivilegeDTO implements Serializable {
                private int app_id;
                private String explain;
                private String icon;
                private int id;
                private int is_basic;
                private String related_icon;
                private int sort;
                private String title;
                private String usage;

                public int getApp_id() {
                    return this.app_id;
                }

                public String getExplain() {
                    return this.explain;
                }

                public String getIcon() {
                    return this.icon;
                }

                public int getId() {
                    return this.id;
                }

                public int getIs_basic() {
                    return this.is_basic;
                }

                public String getRelated_icon() {
                    return this.related_icon;
                }

                public int getSort() {
                    return this.sort;
                }

                public String getTitle() {
                    return this.title;
                }

                public String getUsage() {
                    return this.usage;
                }

                public void setApp_id(int app_id) {
                    this.app_id = app_id;
                }

                public void setExplain(String explain) {
                    this.explain = explain;
                }

                public void setIcon(String icon) {
                    this.icon = icon;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public void setIs_basic(int is_basic) {
                    this.is_basic = is_basic;
                }

                public void setRelated_icon(String related_icon) {
                    this.related_icon = related_icon;
                }

                public void setSort(int sort) {
                    this.sort = sort;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public void setUsage(String usage) {
                    this.usage = usage;
                }
            }

            public static class VideoDTO implements Serializable {
                private String cover;
                private String description;
                private String url;

                public String getCover() {
                    return this.cover;
                }

                public String getDescription() {
                    return this.description;
                }

                public String getUrl() {
                    return this.url;
                }

                public void setCover(String cover) {
                    this.cover = cover;
                }

                public void setDescription(String description) {
                    this.description = description;
                }

                public void setUrl(String url) {
                    this.url = url;
                }
            }

            public int getAvailable() {
                return this.available;
            }

            public List<GoodsDTO> getGoods() {
                return this.goods;
            }

            public int getIs_vip() {
                return this.is_vip;
            }

            public List<PrivilegeDTO> getPrivilege() {
                return this.privilege;
            }

            public String getSimple_declaration() {
                return this.simple_declaration;
            }

            public VideoDTO getVideo() {
                return this.video;
            }

            public String getVip_deadline() {
                return this.vip_deadline;
            }

            public int getVip_due_soon() {
                return this.vip_due_soon;
            }

            public void setAvailable(int available) {
                this.available = available;
            }

            public void setGoods(List<GoodsDTO> goods) {
                this.goods = goods;
            }

            public void setIs_vip(int is_vip) {
                this.is_vip = is_vip;
            }

            public void setPrivilege(List<PrivilegeDTO> privilege) {
                this.privilege = privilege;
            }

            public void setSimple_declaration(String simple_declaration) {
                this.simple_declaration = simple_declaration;
            }

            public void setVideo(VideoDTO video) {
                this.video = video;
            }

            public void setVip_deadline(String vip_deadline) {
                this.vip_deadline = vip_deadline;
            }

            public void setVip_due_soon(int vip_due_soon) {
                this.vip_due_soon = vip_due_soon;
            }
        }

        public static class VipDTO implements Serializable {
            private String available;
            private int is_vip;
            private String simple_declaration;
            private String vip_deadline;
            private int vip_due_soon;
            private List<PrivilegeDTO> privilege = new ArrayList();
            private List<GoodsDTO> goods = new ArrayList();
            private VideoDTO video = new VideoDTO();
            private String label = "";

            public static class GoodsDTO implements Serializable {
                private String cent_price;
                private RedEnvelopeCouponsBean.RedEnvelopeCouponsDataItem coupon;
                private String day;
                private int goods_id;
                private String goods_name;
                private String is_promotion;
                private String label;
                private int month;
                private String per_month;
                private String price;
                private String price_str;
                private String saving;
                private List<ButtonDTO> button = new ArrayList();
                private ReCommend recommend = new ReCommend();
                private int isSelect = 0;

                public static class ButtonDTO implements Serializable {
                    private String label;
                    private int valid;
                    private String upgrade_type = "";
                    private String deduction_id = "";

                    public String getDeduction_id() {
                        return this.deduction_id;
                    }

                    public String getLabel() {
                        return this.label;
                    }

                    public String getUpgrade_type() {
                        return this.upgrade_type;
                    }

                    public int getValid() {
                        return this.valid;
                    }

                    public void setDeduction_id(String deduction_id) {
                        this.deduction_id = deduction_id;
                    }

                    public void setLabel(String label) {
                        this.label = label;
                    }

                    public void setUpgrade_type(String upgrade_type) {
                        this.upgrade_type = upgrade_type;
                    }

                    public void setValid(int valid) {
                        this.valid = valid;
                    }
                }

                public static class ReCommend implements Serializable {
                    public String goods_id = "0";
                    public String goods_name = "";
                    public String goods_description = "";
                    public String label = "";

                    public String getGoods_description() {
                        return this.goods_description;
                    }

                    public String getGoods_id() {
                        return this.goods_id;
                    }

                    public String getGoods_name() {
                        return this.goods_name;
                    }

                    public String getLabel() {
                        return this.label;
                    }

                    public void setGoods_description(String goods_description) {
                        this.goods_description = goods_description;
                    }

                    public void setGoods_id(String goods_id) {
                        this.goods_id = goods_id;
                    }

                    public void setGoods_name(String goods_name) {
                        this.goods_name = goods_name;
                    }

                    public void setLabel(String label) {
                        this.label = label;
                    }
                }

                public GoodsDTO(String label) {
                    this.label = label;
                }

                public List<ButtonDTO> getButton() {
                    return this.button;
                }

                public String getCent_price() {
                    return this.cent_price;
                }

                public RedEnvelopeCouponsBean.RedEnvelopeCouponsDataItem getCoupon() {
                    return this.coupon;
                }

                public String getDay() {
                    return this.day;
                }

                public int getGoods_id() {
                    return this.goods_id;
                }

                public String getGoods_name() {
                    return this.goods_name;
                }

                public int getIsSelect() {
                    return this.isSelect;
                }

                public String getIs_promotion() {
                    return this.is_promotion;
                }

                public String getLabel() {
                    return this.label;
                }

                public int getMonth() {
                    return this.month;
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

                public ReCommend getRecommend() {
                    return this.recommend;
                }

                public String getSaving() {
                    return this.saving;
                }

                public void setButton(List<ButtonDTO> button) {
                    this.button = button;
                }

                public void setCent_price(String cent_price) {
                    this.cent_price = cent_price;
                }

                public void setCoupon(RedEnvelopeCouponsBean.RedEnvelopeCouponsDataItem coupon) {
                    this.coupon = coupon;
                }

                public void setDay(String day) {
                    this.day = day;
                }

                public void setGoods_id(int goods_id) {
                    this.goods_id = goods_id;
                }

                public void setGoods_name(String goods_name) {
                    this.goods_name = goods_name;
                }

                public void setIsSelect(int isSelect) {
                    this.isSelect = isSelect;
                }

                public void setIs_promotion(String is_promotion) {
                    this.is_promotion = is_promotion;
                }

                public void setLabel(String label) {
                    this.label = label;
                }

                public void setMonth(int month) {
                    this.month = month;
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

                public void setRecommend(ReCommend recommend) {
                    this.recommend = recommend;
                }

                public void setSaving(String saving) {
                    this.saving = saving;
                }
            }

            public static class PrivilegeDTO implements Serializable {
                private int app_id;
                private String explain;
                private String icon;
                private int id;
                private int is_basic;
                private String related_icon;
                private int sort;
                private String title;
                private String usage;

                public int getApp_id() {
                    return this.app_id;
                }

                public String getExplain() {
                    return this.explain;
                }

                public String getIcon() {
                    return this.icon;
                }

                public int getId() {
                    return this.id;
                }

                public int getIs_basic() {
                    return this.is_basic;
                }

                public String getRelated_icon() {
                    return this.related_icon;
                }

                public int getSort() {
                    return this.sort;
                }

                public String getTitle() {
                    return this.title;
                }

                public String getUsage() {
                    return this.usage;
                }

                public void setApp_id(int app_id) {
                    this.app_id = app_id;
                }

                public void setExplain(String explain) {
                    this.explain = explain;
                }

                public void setIcon(String icon) {
                    this.icon = icon;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public void setIs_basic(int is_basic) {
                    this.is_basic = is_basic;
                }

                public void setRelated_icon(String related_icon) {
                    this.related_icon = related_icon;
                }

                public void setSort(int sort) {
                    this.sort = sort;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public void setUsage(String usage) {
                    this.usage = usage;
                }
            }

            public static class VideoDTO implements Serializable {
                private String cover;
                private String description;
                private String url;

                public String getCover() {
                    return this.cover;
                }

                public String getDescription() {
                    return this.description;
                }

                public String getUrl() {
                    return this.url;
                }

                public void setCover(String cover) {
                    this.cover = cover;
                }

                public void setDescription(String description) {
                    this.description = description;
                }

                public void setUrl(String url) {
                    this.url = url;
                }
            }

            public String getAvailable() {
                return this.available;
            }

            public List<GoodsDTO> getGoods() {
                return this.goods;
            }

            public int getIs_vip() {
                return this.is_vip;
            }

            public String getLabel() {
                return this.label;
            }

            public List<PrivilegeDTO> getPrivilege() {
                return this.privilege;
            }

            public String getSimple_declaration() {
                return this.simple_declaration;
            }

            public VideoDTO getVideo() {
                return this.video;
            }

            public String getVip_deadline() {
                return this.vip_deadline;
            }

            public int getVip_due_soon() {
                return this.vip_due_soon;
            }

            public void setAvailable(String available) {
                this.available = available;
            }

            public void setGoods(List<GoodsDTO> goods) {
                this.goods = goods;
            }

            public void setIs_vip(int is_vip) {
                this.is_vip = is_vip;
            }

            public void setLabel(String label) {
                this.label = label;
            }

            public void setPrivilege(List<PrivilegeDTO> privilege) {
                this.privilege = privilege;
            }

            public void setSimple_declaration(String simple_declaration) {
                this.simple_declaration = simple_declaration;
            }

            public void setVideo(VideoDTO video) {
                this.video = video;
            }

            public void setVip_deadline(String vip_deadline) {
                this.vip_deadline = vip_deadline;
            }

            public void setVip_due_soon(int vip_due_soon) {
                this.vip_due_soon = vip_due_soon;
            }
        }

        public String getDiscount_description() {
            return this.discount_description;
        }

        public String getPage_title() {
            return this.page_title;
        }

        public List<List<String>> getRights_vs_table() {
            return this.rights_vs_table;
        }

        public String getRule() {
            return this.rule;
        }

        public VipDTO getSvip() {
            return this.svip;
        }

        public VipDTO getVip() {
            return this.vip;
        }

        public String getWechat_corpid() {
            return this.wechat_corpid;
        }

        public String getWechat_enterprise_url() {
            return this.wechat_enterprise_url;
        }

        public void setDiscount_description(String discount_description) {
            this.discount_description = discount_description;
        }

        public void setPage_title(String page_title) {
            this.page_title = page_title;
        }

        public void setRights_vs_table(List<List<String>> rights_vs_table) {
            this.rights_vs_table = rights_vs_table;
        }

        public void setRule(String rule) {
            this.rule = rule;
        }

        public void setSvip(VipDTO svip) {
            this.svip = svip;
        }

        public void setVip(VipDTO vip) {
            this.vip = vip;
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

    public DataDTO getData() {
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

    public void setData(DataDTO data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setServer_time(int server_time) {
        this.server_time = server_time;
    }
}
