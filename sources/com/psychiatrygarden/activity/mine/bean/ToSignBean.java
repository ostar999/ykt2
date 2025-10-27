package com.psychiatrygarden.activity.mine.bean;

/* loaded from: classes5.dex */
public class ToSignBean {
    private String code;
    private DataBean data;
    private String message;
    private String server_time;
    private boolean success;

    public static class DataBean {
        private String is_full_sign;
        private String msg;
        private String pop_up_type;
        private QrCodeBean qr_code;
        private String reward_day;
        private String reward_notice;
        private String reward_str;

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

        public String getIs_full_sign() {
            return this.is_full_sign;
        }

        public String getMsg() {
            return this.msg;
        }

        public String getPop_up_type() {
            return this.pop_up_type;
        }

        public QrCodeBean getQr_code() {
            return this.qr_code;
        }

        public String getReward_day() {
            return this.reward_day;
        }

        public String getReward_notice() {
            return this.reward_notice;
        }

        public String getReward_str() {
            return this.reward_str;
        }

        public void setIs_full_sign(String is_full_sign) {
            this.is_full_sign = is_full_sign;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public void setPop_up_type(String pop_up_type) {
            this.pop_up_type = pop_up_type;
        }

        public void setQr_code(QrCodeBean qr_code) {
            this.qr_code = qr_code;
        }

        public void setReward_day(String reward_day) {
            this.reward_day = reward_day;
        }

        public void setReward_notice(String reward_notice) {
            this.reward_notice = reward_notice;
        }

        public void setReward_str(String reward_str) {
            this.reward_str = reward_str;
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
