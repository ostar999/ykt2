package com.psychiatrygarden.activity.mine.bean;

import java.util.List;

/* loaded from: classes5.dex */
public class SignInBean {
    private String code;
    private DataBean data;
    private String message;
    private String server_time;
    private boolean success;

    public static class DataBean {
        private String continuous_days;
        private String cumulative_days;
        private String date_str;
        private String days_str;
        private List<SignLogBean> log;
        private String rule_content;
        private ShareBean share;

        public String getContinuous_days() {
            return this.continuous_days;
        }

        public String getCumulative_days() {
            return this.cumulative_days;
        }

        public String getDate_str() {
            return this.date_str;
        }

        public String getDays_str() {
            return this.days_str;
        }

        public List<SignLogBean> getLog() {
            return this.log;
        }

        public String getRule_content() {
            return this.rule_content;
        }

        public ShareBean getShare() {
            return this.share;
        }

        public void setContinuous_days(String continuous_days) {
            this.continuous_days = continuous_days;
        }

        public void setCumulative_days(String cumulative_days) {
            this.cumulative_days = cumulative_days;
        }

        public void setDate_str(String date_str) {
            this.date_str = date_str;
        }

        public void setDays_str(String days_str) {
            this.days_str = days_str;
        }

        public void setLog(List<SignLogBean> log) {
            this.log = log;
        }

        public void setRule_content(String rule_content) {
            this.rule_content = rule_content;
        }

        public void setShare(ShareBean share) {
            this.share = share;
        }
    }

    public static class QrCodeBean {
        private String qr_code_desc;
        private String qr_code_title;
        private String qr_code_url;
        private String status;

        public String getQr_code_desc() {
            return this.qr_code_desc;
        }

        public String getQr_code_title() {
            return this.qr_code_title;
        }

        public String getQr_code_url() {
            return this.qr_code_url;
        }

        public String getStatus() {
            return this.status;
        }

        public void setQr_code_desc(String qr_code_desc) {
            this.qr_code_desc = qr_code_desc;
        }

        public void setQr_code_title(String qr_code_title) {
            this.qr_code_title = qr_code_title;
        }

        public void setQr_code_url(String qr_code_url) {
            this.qr_code_url = qr_code_url;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

    public static class ShareBean {
        private String content;
        private String img;
        private String link;
        private String title;

        public String getContent() {
            return this.content;
        }

        public String getImg() {
            return this.img;
        }

        public String getLink() {
            return this.link;
        }

        public String getTitle() {
            return this.title;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    public static class SignLogBean {
        private String continuous_days;
        private String continuous_reward_day;
        private String daily_reward_day;
        private String date;
        private String day;
        private String have_continuous_reward;
        private String have_qr_code;
        private String is_continuous_reward;
        private String is_full_sign;
        private String is_help_sign;
        private QrCodeBean qr_code;
        private String sign_type;
        private String sign_type_str;

        public String getContinuous_days() {
            return this.continuous_days;
        }

        public String getContinuous_reward_day() {
            return this.continuous_reward_day;
        }

        public String getDaily_reward_day() {
            return this.daily_reward_day;
        }

        public String getDate() {
            return this.date;
        }

        public String getDay() {
            return this.day;
        }

        public String getHave_continuous_reward() {
            return this.have_continuous_reward;
        }

        public String getHave_qr_code() {
            return this.have_qr_code;
        }

        public String getIs_continuous_reward() {
            return this.is_continuous_reward;
        }

        public String getIs_full_sign() {
            return this.is_full_sign;
        }

        public String getIs_help_sign() {
            return this.is_help_sign;
        }

        public QrCodeBean getQr_code() {
            return this.qr_code;
        }

        public String getSign_type() {
            return this.sign_type;
        }

        public String getSign_type_str() {
            return this.sign_type_str;
        }

        public void setContinuous_days(String continuous_days) {
            this.continuous_days = continuous_days;
        }

        public void setContinuous_reward_day(String continuous_reward_day) {
            this.continuous_reward_day = continuous_reward_day;
        }

        public void setDaily_reward_day(String daily_reward_day) {
            this.daily_reward_day = daily_reward_day;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public void setDay(String day) {
            this.day = day;
        }

        public void setHave_continuous_reward(String have_continuous_reward) {
            this.have_continuous_reward = have_continuous_reward;
        }

        public void setHave_qr_code(String have_qr_code) {
            this.have_qr_code = have_qr_code;
        }

        public void setIs_continuous_reward(String is_continuous_reward) {
            this.is_continuous_reward = is_continuous_reward;
        }

        public void setIs_full_sign(String is_full_sign) {
            this.is_full_sign = is_full_sign;
        }

        public void setIs_help_sign(String is_help_sign) {
            this.is_help_sign = is_help_sign;
        }

        public void setQr_code(QrCodeBean qr_code) {
            this.qr_code = qr_code;
        }

        public void setSign_type(String sign_type) {
            this.sign_type = sign_type;
        }

        public void setSign_type_str(String sign_type_str) {
            this.sign_type_str = sign_type_str;
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

    public boolean isSuccess() {
        return this.success;
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

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
