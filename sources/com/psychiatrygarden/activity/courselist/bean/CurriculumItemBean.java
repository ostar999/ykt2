package com.psychiatrygarden.activity.courselist.bean;

import android.os.Binder;
import android.text.TextUtils;
import com.aliyun.auth.common.AliyunVodHttpCommon;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.psychiatrygarden.bean.OnlineServiceBean;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes5.dex */
public class CurriculumItemBean implements Serializable {

    @SerializedName("code")
    private String code;

    @SerializedName("data")
    private List<DataDTO> data = new ArrayList();

    @SerializedName("message")
    private String message;

    @SerializedName("server_time")
    private String serverTime;

    public static class CourseLabel {

        @SerializedName(alternate = {"color"}, value = "background_color")
        private String background_color;
        private String font_color;
        private String value;

        public String getBackground_color() {
            return this.background_color;
        }

        public String getFont_color() {
            return this.font_color;
        }

        public String getValue() {
            return this.value;
        }

        public void setBackground_color(String background_color) {
            this.background_color = background_color;
        }

        public void setFont_color(String font_color) {
            this.font_color = font_color;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public static class DataDTO extends Binder implements MultiItemEntity, Serializable {

        @SerializedName(ConstantsAPI.WXWebPage.KEY_ACTIVITY_ID)
        private String activityId;
        private String after_cs;
        private String app_id;
        private String before_cs;
        private String button_text;
        private int button_text_grey;
        private String button_type;
        private String category_id;

        @SerializedName(AliyunVodHttpCommon.ImageType.IMAGETYPE_COVER)
        private String cover;

        @SerializedName("description")
        private String description;

        @SerializedName("id")
        private String id;

        @SerializedName("is_open_qrcode")
        private String isOpenQrcode;
        private String is_alone_sale;
        private String is_end;
        private String is_promotion;
        private String is_sale;
        private List<CourseLabel> label;

        @SerializedName("live_status")
        private int liveStatus;
        private String live_title;
        private String live_within;

        @SerializedName("member_count")
        private String memberCount;
        private String original_price;

        @SerializedName("pass")
        private String pass;

        @SerializedName("price")
        private String price;
        private String promotion_countdown;
        private String promotion_price;

        @SerializedName("qrcode_data")
        private Object qrcodeData;
        private String recommend_label;
        private String sales_volume;
        private boolean select;

        @SerializedName(alternate = {"package"}, value = "set_meal")
        private Object setMeal;

        @SerializedName("teacher")
        private List<TeacherDTO> teacher;

        @SerializedName("title")
        private String title;

        @SerializedName("type")
        private int type;
        private String wechat_number;
        private String wechat_qrcode;
        private String wechat_tips;
        private String have_chapter = "";
        private String is_best_sellers = "";
        private String is_hide_teacher = "";
        private String is_permission = "0";
        private String sales = "0";
        private int isOpen = 0;
        private String no_separate_sales = "0";
        private long live_start_time = 0;
        private long live_end_time = 0;

        @SerializedName("package_count")
        private String courseCount = "0";
        private OnlineServiceBean cs = new OnlineServiceBean();

        public static class QrcodeDataDTO implements Serializable {

            @SerializedName("wechat_qrcode")
            private String wechatQrcode = "";

            @SerializedName("wechat_tips")
            private String wechatTips = "";

            @SerializedName("wechat_number")
            private String wechatNumber = "";

            public String getWechatNumber() {
                return this.wechatNumber;
            }

            public String getWechatQrcode() {
                return this.wechatQrcode;
            }

            public String getWechatTips() {
                return this.wechatTips;
            }

            public void setWechatNumber(String wechatNumber) {
                this.wechatNumber = wechatNumber;
            }

            public void setWechatQrcode(String wechatQrcode) {
                this.wechatQrcode = wechatQrcode;
            }

            public void setWechatTips(String wechatTips) {
                this.wechatTips = wechatTips;
            }
        }

        public static class TeacherDTO implements Serializable {

            @SerializedName("avatar")
            private String avatar;

            @SerializedName("id")
            private String id;
            private List<String> label;

            @SerializedName("name")
            private String name;

            public String getAvatar() {
                return this.avatar;
            }

            public String getId() {
                return this.id;
            }

            public List<String> getLabel() {
                return this.label;
            }

            public String getName() {
                return this.name;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public void setId(String id) {
                this.id = id;
            }

            public void setLabel(List<String> label) {
                this.label = label;
            }

            public void setName(String name) {
                this.name = name;
            }
        }

        public String getActivityId() {
            return this.activityId;
        }

        public String getAfter_cs() {
            return this.after_cs;
        }

        public String getApp_id() {
            return this.app_id;
        }

        public String getBefore_cs() {
            return this.before_cs;
        }

        public String getButton_text() {
            return this.button_text;
        }

        public int getButton_text_grey() {
            return this.button_text_grey;
        }

        public String getButton_type() {
            return this.button_type;
        }

        public String getCategory_id() {
            return this.category_id;
        }

        public String getCourseCount() {
            return this.courseCount;
        }

        public String getCover() {
            return this.cover;
        }

        public OnlineServiceBean getCs() {
            return this.cs;
        }

        public String getDescription() {
            return this.description;
        }

        public String getHave_chapter() {
            return this.have_chapter;
        }

        public String getId() {
            return this.id;
        }

        public int getIsOpen() {
            return this.isOpen;
        }

        public String getIsOpenQrcode() {
            return this.isOpenQrcode;
        }

        public String getIs_alone_sale() {
            return this.is_alone_sale;
        }

        public String getIs_best_sellers() {
            return this.is_best_sellers;
        }

        public String getIs_end() {
            return this.is_end;
        }

        public String getIs_hide_teacher() {
            return this.is_hide_teacher;
        }

        public String getIs_permission() {
            return this.is_permission;
        }

        public String getIs_promotion() {
            return this.is_promotion;
        }

        public String getIs_sale() {
            return this.is_sale;
        }

        @Override // com.chad.library.adapter.base.entity.MultiItemEntity
        public int getItemType() {
            return this.type;
        }

        public List<CourseLabel> getLabel() {
            return this.label;
        }

        public int getLiveStatus() {
            return this.liveStatus;
        }

        public long getLive_end_time() {
            return this.live_end_time;
        }

        public long getLive_start_time() {
            return this.live_start_time;
        }

        public String getLive_title() {
            return this.live_title;
        }

        public String getLive_within() {
            return this.live_within;
        }

        public String getMemberCount() {
            return this.memberCount;
        }

        public String getNo_separate_sales() {
            return this.no_separate_sales;
        }

        public String getOriginal_price() {
            return this.original_price;
        }

        public String getPass() {
            return this.pass;
        }

        public String getPrice() {
            return this.price;
        }

        public String getPromotion_countdown() {
            return this.promotion_countdown;
        }

        public String getPromotion_price() {
            return this.promotion_price;
        }

        public Object getQrcodeData() {
            try {
                if (this.qrcodeData instanceof ArrayList) {
                    return new QrcodeDataDTO();
                }
                String json = new Gson().toJson(this.qrcodeData);
                if (TextUtils.isEmpty(json)) {
                    return new QrcodeDataDTO();
                }
                QrcodeDataDTO qrcodeDataDTO = (QrcodeDataDTO) new Gson().fromJson(json, QrcodeDataDTO.class);
                return qrcodeDataDTO != null ? qrcodeDataDTO : new QrcodeDataDTO();
            } catch (Exception e2) {
                e2.printStackTrace();
                return new QrcodeDataDTO();
            }
        }

        public String getRecommend_label() {
            return this.recommend_label;
        }

        public String getSales() {
            return this.sales;
        }

        public String getSales_volume() {
            return this.sales_volume;
        }

        public Object getSetMeal() {
            try {
                if (this.setMeal instanceof ArrayList) {
                    return (List) new Gson().fromJson(new Gson().toJson(this.setMeal), new TypeToken<List<DataDTO>>() { // from class: com.psychiatrygarden.activity.courselist.bean.CurriculumItemBean.DataDTO.1
                    }.getType());
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            return new ArrayList();
        }

        public List<TeacherDTO> getTeacher() {
            return this.teacher;
        }

        public String getTitle() {
            return this.title;
        }

        public int getType() {
            return this.type;
        }

        public String getWechat_number() {
            return this.wechat_number;
        }

        public String getWechat_qrcode() {
            return this.wechat_qrcode;
        }

        public String getWechat_tips() {
            return this.wechat_tips;
        }

        public boolean isSelect() {
            return this.select;
        }

        public void setActivityId(String activityId) {
            this.activityId = activityId;
        }

        public void setAfter_cs(String after_cs) {
            this.after_cs = after_cs;
        }

        public void setApp_id(String app_id) {
            this.app_id = app_id;
        }

        public void setBefore_cs(String before_cs) {
            this.before_cs = before_cs;
        }

        public void setButton_text(String button_text) {
            this.button_text = button_text;
        }

        public void setButton_text_grey(int button_text_grey) {
            this.button_text_grey = button_text_grey;
        }

        public void setButton_type(String button_type) {
            this.button_type = button_type;
        }

        public void setCategory_id(String category_id) {
            this.category_id = category_id;
        }

        public void setCourseCount(String courseCount) {
            this.courseCount = courseCount;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public void setCs(OnlineServiceBean cs) {
            this.cs = cs;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public void setHave_chapter(String have_chapter) {
            this.have_chapter = have_chapter;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setIsOpen(int isOpen) {
            this.isOpen = isOpen;
        }

        public void setIsOpenQrcode(String isOpenQrcode) {
            this.isOpenQrcode = isOpenQrcode;
        }

        public void setIs_alone_sale(String is_alone_sale) {
            this.is_alone_sale = is_alone_sale;
        }

        public void setIs_best_sellers(String is_best_sellers) {
            this.is_best_sellers = is_best_sellers;
        }

        public void setIs_end(String is_end) {
            this.is_end = is_end;
        }

        public void setIs_hide_teacher(String is_hide_teacher) {
            this.is_hide_teacher = is_hide_teacher;
        }

        public void setIs_permission(String is_permission) {
            this.is_permission = is_permission;
        }

        public void setIs_promotion(String is_promotion) {
            this.is_promotion = is_promotion;
        }

        public void setIs_sale(String is_sale) {
            this.is_sale = is_sale;
        }

        public void setLabel(List<CourseLabel> label) {
            this.label = label;
        }

        public void setLiveStatus(int liveStatus) {
            this.liveStatus = liveStatus;
        }

        public void setLive_end_time(long live_end_time) {
            this.live_end_time = live_end_time;
        }

        public void setLive_start_time(long live_start_time) {
            this.live_start_time = live_start_time;
        }

        public void setLive_title(String live_title) {
            this.live_title = live_title;
        }

        public void setLive_within(String live_within) {
            this.live_within = live_within;
        }

        public void setMemberCount(String memberCount) {
            this.memberCount = memberCount;
        }

        public void setNo_separate_sales(String no_separate_sales) {
            this.no_separate_sales = no_separate_sales;
        }

        public void setOriginal_price(String original_price) {
            this.original_price = original_price;
        }

        public void setPass(String pass) {
            this.pass = pass;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public void setPromotion_countdown(String promotion_countdown) {
            this.promotion_countdown = promotion_countdown;
        }

        public void setPromotion_price(String promotion_price) {
            this.promotion_price = promotion_price;
        }

        public void setQrcodeData(Object qrcodeData) {
            this.qrcodeData = qrcodeData;
        }

        public void setRecommend_label(String recommend_label) {
            this.recommend_label = recommend_label;
        }

        public void setSales(String sales) {
            this.sales = sales;
        }

        public void setSales_volume(String sales_volume) {
            this.sales_volume = sales_volume;
        }

        public void setSelect(boolean select) {
            this.select = select;
        }

        public void setSetMeal(Object setMeal) {
            this.setMeal = setMeal;
        }

        public void setTeacher(List<TeacherDTO> teacher) {
            this.teacher = teacher;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setType(int type) {
            this.type = type;
        }

        public void setWechat_number(String wechat_number) {
            this.wechat_number = wechat_number;
        }

        public void setWechat_qrcode(String wechat_qrcode) {
            this.wechat_qrcode = wechat_qrcode;
        }

        public void setWechat_tips(String wechat_tips) {
            this.wechat_tips = wechat_tips;
        }
    }

    public String getCode() {
        return this.code;
    }

    public List<DataDTO> getData() {
        return this.data;
    }

    public String getMessage() {
        return this.message;
    }

    public String getServerTime() {
        return this.serverTime;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setData(List<DataDTO> data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setServerTime(String serverTime) {
        this.serverTime = serverTime;
    }
}
