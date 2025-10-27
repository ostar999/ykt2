package com.psychiatrygarden.bean;

import com.google.gson.annotations.SerializedName;
import com.psychiatrygarden.bean.CourseList2Bean;
import com.psychiatrygarden.bean.QuestioBean;
import java.io.Serializable;

/* loaded from: classes5.dex */
public class EsexBean implements Serializable {
    private String code;
    private DataBean data;
    private String message;
    private String server_time;

    public static class DataBean implements Serializable {
        private String app_name;
        private String bottom_state_description;
        private String close_entrance_timestamp;
        private String comment_count;
        private String computer_based_test;
        private String date_end;
        private String date_end_timestamp;
        private String date_start;
        private String date_start_timestamp;
        private String exam_id;
        private String exam_number_limit;
        private String exam_rights;
        private String exam_time;
        private String flyer;
        private String is_estimate;
        private String is_school_rank;

        @SerializedName("late_minute")
        private String lateMinute;
        private QuestioBean.LiveAbout live_about;
        private String redo;
        private ShareData share;
        private String share_activity_id;
        private String share_activity_rights;
        private int state;

        @SerializedName("submit_minute")
        private String submitMinute;
        private String tips;
        private String title;
        private String top_state_description;
        private String total_points;
        private String type;
        private String type_str;
        private String user_exam_info;
        private String user_exam_rights;
        private String vid;
        private CourseList2Bean.DataBean.DataChildBean vidteaching;
        private String question_number = "0";
        private String user_exam_time = "";
        private String score = "";
        private String acesss_cdkey = "0";
        private long duration_minute = 0;
        private String answer_number = "0";
        private String activity_id = "";
        private String c_id = "";
        private String show_live_button = "0";
        private String live_tips = "";
        private String live_status = "";

        public class ShareData {
            private String id;
            private String share_content;
            private String share_img;
            private String share_title;
            private String share_url;
            private String tips;
            private String type = "0";

            public ShareData() {
            }

            public String getId() {
                return this.id;
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

            public String getShare_url() {
                return this.share_url;
            }

            public String getTips() {
                return this.tips;
            }

            public String getType() {
                return this.type;
            }

            public void setId(String id) {
                this.id = id;
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

            public void setShare_url(String share_url) {
                this.share_url = share_url;
            }

            public void setTips(String tips) {
                this.tips = tips;
            }

            public void setType(String type) {
                this.type = type;
            }
        }

        public String getAcesss_cdkey() {
            return this.acesss_cdkey;
        }

        public String getActivity_id() {
            return this.activity_id;
        }

        public String getAnswer_number() {
            return this.answer_number;
        }

        public String getApp_name() {
            return this.app_name;
        }

        public String getBottom_state_description() {
            return this.bottom_state_description;
        }

        public String getC_id() {
            return this.c_id;
        }

        public String getClose_entrance_timestamp() {
            return this.close_entrance_timestamp;
        }

        public String getComment_count() {
            return this.comment_count;
        }

        public String getComputer_based_test() {
            return this.computer_based_test;
        }

        public String getDate_end() {
            return this.date_end;
        }

        public String getDate_end_timestamp() {
            return this.date_end_timestamp;
        }

        public String getDate_start() {
            return this.date_start;
        }

        public String getDate_start_timestamp() {
            return this.date_start_timestamp;
        }

        public long getDuration_minute() {
            return this.duration_minute;
        }

        public String getExam_id() {
            return this.exam_id;
        }

        public String getExam_number_limit() {
            return this.exam_number_limit;
        }

        public String getExam_rights() {
            return this.exam_rights;
        }

        public String getExam_time() {
            return this.exam_time;
        }

        public String getFlyer() {
            return this.flyer;
        }

        public String getIs_estimate() {
            return this.is_estimate;
        }

        public String getIs_school_rank() {
            return this.is_school_rank;
        }

        public String getLateMinute() {
            return this.lateMinute;
        }

        public QuestioBean.LiveAbout getLive_about() {
            return this.live_about;
        }

        public String getLive_status() {
            return this.live_status;
        }

        public String getLive_tips() {
            return this.live_tips;
        }

        public String getQuestion_number() {
            return this.question_number;
        }

        public String getRedo() {
            return this.redo;
        }

        public String getScore() {
            return this.score;
        }

        public ShareData getShare() {
            return this.share;
        }

        public String getShare_activity_id() {
            return this.share_activity_id;
        }

        public String getShare_activity_rights() {
            return this.share_activity_rights;
        }

        public String getShow_live_button() {
            return this.show_live_button;
        }

        public int getState() {
            return this.state;
        }

        public String getSubmitMinute() {
            return this.submitMinute;
        }

        public String getTips() {
            return this.tips;
        }

        public String getTitle() {
            return this.title;
        }

        public String getTop_state_description() {
            return this.top_state_description;
        }

        public String getTotal_points() {
            return this.total_points;
        }

        public String getType() {
            return this.type;
        }

        public String getType_str() {
            return this.type_str;
        }

        public String getUser_exam_info() {
            return this.user_exam_info;
        }

        public String getUser_exam_rights() {
            return this.user_exam_rights;
        }

        public String getUser_exam_time() {
            return this.user_exam_time;
        }

        public String getVid() {
            return this.vid;
        }

        public CourseList2Bean.DataBean.DataChildBean getVidteaching() {
            return this.vidteaching;
        }

        public void setAcesss_cdkey(String acesss_cdkey) {
            this.acesss_cdkey = acesss_cdkey;
        }

        public void setActivity_id(String activity_id) {
            this.activity_id = activity_id;
        }

        public void setAnswer_number(String answer_number) {
            this.answer_number = answer_number;
        }

        public void setApp_name(String app_name) {
            this.app_name = app_name;
        }

        public void setBottom_state_description(String bottom_state_description) {
            this.bottom_state_description = bottom_state_description;
        }

        public void setC_id(String c_id) {
            this.c_id = c_id;
        }

        public void setClose_entrance_timestamp(String close_entrance_timestamp) {
            this.close_entrance_timestamp = close_entrance_timestamp;
        }

        public void setComment_count(String comment_count) {
            this.comment_count = comment_count;
        }

        public void setComputer_based_test(String computer_based_test) {
            this.computer_based_test = computer_based_test;
        }

        public void setDate_end(String date_end) {
            this.date_end = date_end;
        }

        public void setDate_end_timestamp(String date_end_timestamp) {
            this.date_end_timestamp = date_end_timestamp;
        }

        public void setDate_start(String date_start) {
            this.date_start = date_start;
        }

        public void setDate_start_timestamp(String date_start_timestamp) {
            this.date_start_timestamp = date_start_timestamp;
        }

        public DataBean setDuration_minute(long duration_minute) {
            this.duration_minute = duration_minute;
            return this;
        }

        public void setExam_id(String exam_id) {
            this.exam_id = exam_id;
        }

        public void setExam_number_limit(String exam_number_limit) {
            this.exam_number_limit = exam_number_limit;
        }

        public void setExam_rights(String exam_rights) {
            this.exam_rights = exam_rights;
        }

        public void setExam_time(String exam_time) {
            this.exam_time = exam_time;
        }

        public void setFlyer(String flyer) {
            this.flyer = flyer;
        }

        public void setIs_estimate(String is_estimate) {
            this.is_estimate = is_estimate;
        }

        public void setIs_school_rank(String is_school_rank) {
            this.is_school_rank = is_school_rank;
        }

        public void setLateMinute(String lateMinute) {
            this.lateMinute = lateMinute;
        }

        public void setLive_about(QuestioBean.LiveAbout live_about) {
            this.live_about = live_about;
        }

        public DataBean setLive_status(String live_status) {
            this.live_status = live_status;
            return this;
        }

        public DataBean setLive_tips(String live_tips) {
            this.live_tips = live_tips;
            return this;
        }

        public void setQuestion_number(String question_number) {
            this.question_number = question_number;
        }

        public void setRedo(String redo) {
            this.redo = redo;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public void setShare(ShareData share) {
            this.share = share;
        }

        public void setShare_activity_id(String share_activity_id) {
            this.share_activity_id = share_activity_id;
        }

        public void setShare_activity_rights(String share_activity_rights) {
            this.share_activity_rights = share_activity_rights;
        }

        public DataBean setShow_live_button(String show_live_button) {
            this.show_live_button = show_live_button;
            return this;
        }

        public void setState(int state) {
            this.state = state;
        }

        public void setSubmitMinute(String submitMinute) {
            this.submitMinute = submitMinute;
        }

        public void setTips(String tips) {
            this.tips = tips;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setTop_state_description(String top_state_description) {
            this.top_state_description = top_state_description;
        }

        public void setTotal_points(String total_points) {
            this.total_points = total_points;
        }

        public void setType(String type) {
            this.type = type;
        }

        public void setType_str(String type_str) {
            this.type_str = type_str;
        }

        public void setUser_exam_info(String user_exam_info) {
            this.user_exam_info = user_exam_info;
        }

        public void setUser_exam_rights(String user_exam_rights) {
            this.user_exam_rights = user_exam_rights;
        }

        public void setUser_exam_time(String user_exam_time) {
            this.user_exam_time = user_exam_time;
        }

        public DataBean setVid(String vid) {
            this.vid = vid;
            return this;
        }

        public DataBean setVidteaching(CourseList2Bean.DataBean.DataChildBean vidteaching) {
            this.vidteaching = vidteaching;
            return this;
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
