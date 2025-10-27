package com.psychiatrygarden.bean;

/* loaded from: classes5.dex */
public class AdjustInfoBean {
    private String code;
    private DataBean data;
    private String message;
    private String server_time;

    public static class DataBean {
        private AdjustVol1Bean adjust_vol_1;
        private AdjustVol1Bean adjust_vol_2;
        private AdjustVol1Bean adjust_vol_3;
        private String title;

        public static class AdjustVol1Bean {
            private String btn_str;
            private String is_fill;
            private String title;
            private VolDataBean vol_data;

            public static class VolDataBean {
                private String adjust_rank;
                private String adjust_rank_count;
                private String adjust_rank_title;
                private String all_rank;
                private String all_rank_count;
                private String all_rank_title;
                private String major_direction_title;
                private String major_title;
                private String school_department_title;
                private String school_title;
                private String zhuan_xue;

                public String getAdjust_rank() {
                    return this.adjust_rank;
                }

                public String getAdjust_rank_count() {
                    return this.adjust_rank_count;
                }

                public String getAdjust_rank_title() {
                    return this.adjust_rank_title;
                }

                public String getAll_rank() {
                    return this.all_rank;
                }

                public String getAll_rank_count() {
                    return this.all_rank_count;
                }

                public String getAll_rank_title() {
                    return this.all_rank_title;
                }

                public String getMajor_direction_title() {
                    return this.major_direction_title;
                }

                public String getMajor_title() {
                    return this.major_title;
                }

                public String getSchool_department_title() {
                    return this.school_department_title;
                }

                public String getSchool_title() {
                    return this.school_title;
                }

                public String getZhuan_xue() {
                    return this.zhuan_xue;
                }

                public void setAdjust_rank(String adjust_rank) {
                    this.adjust_rank = adjust_rank;
                }

                public void setAdjust_rank_count(String adjust_rank_count) {
                    this.adjust_rank_count = adjust_rank_count;
                }

                public void setAdjust_rank_title(String adjust_rank_title) {
                    this.adjust_rank_title = adjust_rank_title;
                }

                public void setAll_rank(String all_rank) {
                    this.all_rank = all_rank;
                }

                public void setAll_rank_count(String all_rank_count) {
                    this.all_rank_count = all_rank_count;
                }

                public void setAll_rank_title(String all_rank_title) {
                    this.all_rank_title = all_rank_title;
                }

                public void setMajor_direction_title(String major_direction_title) {
                    this.major_direction_title = major_direction_title;
                }

                public void setMajor_title(String major_title) {
                    this.major_title = major_title;
                }

                public void setSchool_department_title(String school_department_title) {
                    this.school_department_title = school_department_title;
                }

                public void setSchool_title(String school_title) {
                    this.school_title = school_title;
                }

                public void setZhuan_xue(String zhuan_xue) {
                    this.zhuan_xue = zhuan_xue;
                }
            }

            public String getBtn_str() {
                return this.btn_str;
            }

            public String getIs_fill() {
                return this.is_fill;
            }

            public String getTitle() {
                return this.title;
            }

            public VolDataBean getVol_data() {
                return this.vol_data;
            }

            public void setBtn_str(String btn_str) {
                this.btn_str = btn_str;
            }

            public void setIs_fill(String is_fill) {
                this.is_fill = is_fill;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public void setVol_data(VolDataBean vol_data) {
                this.vol_data = vol_data;
            }
        }

        public AdjustVol1Bean getAdjust_vol_1() {
            return this.adjust_vol_1;
        }

        public AdjustVol1Bean getAdjust_vol_2() {
            return this.adjust_vol_2;
        }

        public AdjustVol1Bean getAdjust_vol_3() {
            return this.adjust_vol_3;
        }

        public String getTitle() {
            return this.title;
        }

        public void setAdjust_vol_1(AdjustVol1Bean adjust_vol_1) {
            this.adjust_vol_1 = adjust_vol_1;
        }

        public void setAdjust_vol_2(AdjustVol1Bean adjust_vol_2) {
            this.adjust_vol_2 = adjust_vol_2;
        }

        public void setAdjust_vol_3(AdjustVol1Bean adjust_vol_3) {
            this.adjust_vol_3 = adjust_vol_3;
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
