package com.psychiatrygarden.activity.online.bean;

import android.text.TextUtils;
import com.psychiatrygarden.activity.forum.bean.ForumInfoBean;
import java.io.Serializable;
import java.util.List;

/* loaded from: classes5.dex */
public class QuestionStatDataBean implements Serializable {
    private String code;
    private DataBean data;
    private String message;
    private String server_time;

    public static class DataBean implements Serializable {
        private String accuracy;
        private String allDoUseTime;
        private String all_count;
        private AnswerBean answer;
        private int collection_count;
        private int comment_count;
        private String doUseTime;
        private int error_correction_number;
        private List<String> explain_correction_avatar;
        private int explain_correction_number;
        private String explain_correction_tips;
        private String explain_praise;
        private ForumInfoBean.DataBean.UserBean explain_user;
        private int is_collection;
        private int is_comment;
        private int is_note;
        private int is_praise;
        private List<String> option_analysis_correction_avatar;
        private int option_analysis_correction_number;
        private String option_analysis_correction_tips;
        private String option_analysis_praise;
        private ForumInfoBean.DataBean.UserBean option_analysis_user;
        private List<String> restore_correction_avatar;
        private int restore_correction_number;
        private String restore_correction_tips;
        private String restore_praise;
        private ForumInfoBean.DataBean.UserBean restore_user;
        private long right_count;
        private String stat_info;
        private int update_time;
        private long wrong_count;

        public static class AnswerBean implements Serializable {
            private int right_count;
            private int wrong_count;

            public int getRight_count() {
                return this.right_count;
            }

            public int getWrong_count() {
                return this.wrong_count;
            }

            public void setRight_count(int right_count) {
                this.right_count = right_count;
            }

            public void setWrong_count(int wrong_count) {
                this.wrong_count = wrong_count;
            }
        }

        public String getAccuracy() {
            String str = this.accuracy;
            return str == null ? "0" : str;
        }

        public String getAllDoUseTime() {
            return this.allDoUseTime;
        }

        public String getAll_count() {
            String str = this.all_count;
            return str == null ? "0" : str;
        }

        public AnswerBean getAnswer() {
            return this.answer;
        }

        public int getCollection_count() {
            return this.collection_count;
        }

        public int getComment_count() {
            return this.comment_count;
        }

        public String getDoUseTime() {
            return this.doUseTime;
        }

        public int getError_correction_number() {
            return this.error_correction_number;
        }

        public List<String> getExplain_correction_avatar() {
            return this.explain_correction_avatar;
        }

        public int getExplain_correction_number() {
            return this.explain_correction_number;
        }

        public String getExplain_correction_tips() {
            return this.explain_correction_tips;
        }

        public String getExplain_praise() {
            return this.explain_praise;
        }

        public ForumInfoBean.DataBean.UserBean getExplain_user() {
            return this.explain_user;
        }

        public int getIs_collection() {
            return this.is_collection;
        }

        public int getIs_comment() {
            return this.is_comment;
        }

        public int getIs_note() {
            return this.is_note;
        }

        public int getIs_praise() {
            return this.is_praise;
        }

        public List<String> getOption_analysis_correction_avatar() {
            return this.option_analysis_correction_avatar;
        }

        public int getOption_analysis_correction_number() {
            return this.option_analysis_correction_number;
        }

        public String getOption_analysis_correction_tips() {
            return this.option_analysis_correction_tips;
        }

        public String getOption_analysis_praise() {
            return this.option_analysis_praise;
        }

        public ForumInfoBean.DataBean.UserBean getOption_analysis_user() {
            return this.option_analysis_user;
        }

        public List<String> getRestore_correction_avatar() {
            return this.restore_correction_avatar;
        }

        public int getRestore_correction_number() {
            return this.restore_correction_number;
        }

        public String getRestore_correction_tips() {
            return this.restore_correction_tips;
        }

        public String getRestore_praise() {
            return this.restore_praise;
        }

        public ForumInfoBean.DataBean.UserBean getRestore_user() {
            return this.restore_user;
        }

        public long getRight_count() {
            return this.right_count;
        }

        public String getStat_info() {
            return TextUtils.isEmpty(this.stat_info) ? "" : this.stat_info;
        }

        public int getUpdate_time() {
            return this.update_time;
        }

        public long getWrong_count() {
            return this.wrong_count;
        }

        public void setAccuracy(String accuracy) {
            this.accuracy = accuracy;
        }

        public void setAllDoUseTime(String allDoUseTime) {
            this.allDoUseTime = allDoUseTime;
        }

        public void setAll_count(String all_count) {
            this.all_count = all_count;
        }

        public void setAnswer(AnswerBean answer) {
            this.answer = answer;
        }

        public void setCollection_count(int collection_count) {
            this.collection_count = collection_count;
        }

        public void setComment_count(int comment_count) {
            this.comment_count = comment_count;
        }

        public void setDoUseTime(String doUseTime) {
            this.doUseTime = doUseTime;
        }

        public void setError_correction_number(int error_correction_number) {
            this.error_correction_number = error_correction_number;
        }

        public void setExplain_correction_avatar(List<String> explain_correction_avatar) {
            this.explain_correction_avatar = explain_correction_avatar;
        }

        public void setExplain_correction_number(int explain_correction_number) {
            this.explain_correction_number = explain_correction_number;
        }

        public void setExplain_correction_tips(String explain_correction_tips) {
            this.explain_correction_tips = explain_correction_tips;
        }

        public void setExplain_praise(String explain_praise) {
            this.explain_praise = explain_praise;
        }

        public void setExplain_user(ForumInfoBean.DataBean.UserBean explain_user) {
            this.explain_user = explain_user;
        }

        public void setIs_collection(int is_collection) {
            this.is_collection = is_collection;
        }

        public void setIs_comment(int is_comment) {
            this.is_comment = is_comment;
        }

        public void setIs_note(int is_note) {
            this.is_note = is_note;
        }

        public void setIs_praise(int is_praise) {
            this.is_praise = is_praise;
        }

        public void setOption_analysis_correction_avatar(List<String> option_analysis_correction_avatar) {
            this.option_analysis_correction_avatar = option_analysis_correction_avatar;
        }

        public void setOption_analysis_correction_number(int option_analysis_correction_number) {
            this.option_analysis_correction_number = option_analysis_correction_number;
        }

        public void setOption_analysis_correction_tips(String option_analysis_correction_tips) {
            this.option_analysis_correction_tips = option_analysis_correction_tips;
        }

        public void setOption_analysis_praise(String option_analysis_praise) {
            this.option_analysis_praise = option_analysis_praise;
        }

        public void setOption_analysis_user(ForumInfoBean.DataBean.UserBean option_analysis_user) {
            this.option_analysis_user = option_analysis_user;
        }

        public void setRestore_correction_avatar(List<String> restore_correction_avatar) {
            this.restore_correction_avatar = restore_correction_avatar;
        }

        public void setRestore_correction_number(int restore_correction_number) {
            this.restore_correction_number = restore_correction_number;
        }

        public void setRestore_correction_tips(String restore_correction_tips) {
            this.restore_correction_tips = restore_correction_tips;
        }

        public void setRestore_praise(String restore_praise) {
            this.restore_praise = restore_praise;
        }

        public void setRestore_user(ForumInfoBean.DataBean.UserBean restore_user) {
            this.restore_user = restore_user;
        }

        public void setRight_count(long right_count) {
            this.right_count = right_count;
        }

        public void setStat_info(String stat_info) {
            this.stat_info = stat_info;
        }

        public void setUpdate_time(int update_time) {
            this.update_time = update_time;
        }

        public void setWrong_count(long wrong_count) {
            this.wrong_count = wrong_count;
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
