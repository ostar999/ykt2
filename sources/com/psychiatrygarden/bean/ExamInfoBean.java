package com.psychiatrygarden.bean;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

/* loaded from: classes5.dex */
public class ExamInfoBean {
    private int code;
    private DataBean data;
    private String message;
    private int prc_time;

    public static class DataBean implements Parcelable {
        public static final Parcelable.Creator<DataBean> CREATOR = new Parcelable.Creator<DataBean>() { // from class: com.psychiatrygarden.bean.ExamInfoBean.DataBean.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public DataBean createFromParcel(Parcel source) {
                return new DataBean(source);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public DataBean[] newArray(int size) {
                return new DataBean[size];
            }
        };
        private int answer_number;
        private int answer_remain_times;
        private int answer_time;

        @SerializedName("app_name")
        private String appName;
        private int app_id;
        private String created_admin;
        private String created_at;
        private String deleted_at;
        private String description;
        private String ent_time;
        private int exam_number;
        private String exam_qr_code;
        private int exam_time;
        private int hospital_id;
        private int id;

        @SerializedName("late_minute")
        private int lateMinute;
        private String participants;
        private int pass_score;
        private String question_file;
        private int question_number;
        private int score;
        private String start_time;
        private int status;

        @SerializedName("submit_minute")
        private int submitMinute;
        private int test_paper_id;
        private int time;
        private String title;
        private String updated_at;
        private String user_group;
        private int user_score;

        public DataBean() {
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public int getAnswer_number() {
            return this.answer_number;
        }

        public int getAnswer_remain_times() {
            return this.answer_remain_times;
        }

        public int getAnswer_time() {
            return this.answer_time;
        }

        public String getAppName() {
            return this.appName;
        }

        public int getApp_id() {
            return this.app_id;
        }

        public String getCreated_admin() {
            return this.created_admin;
        }

        public String getCreated_at() {
            return this.created_at;
        }

        public String getDeleted_at() {
            return this.deleted_at;
        }

        public String getDescription() {
            return this.description;
        }

        public String getEnt_time() {
            return this.ent_time;
        }

        public int getExam_number() {
            return this.exam_number;
        }

        public String getExam_qr_code() {
            return this.exam_qr_code;
        }

        public int getExam_time() {
            return this.exam_time;
        }

        public int getHospital_id() {
            return this.hospital_id;
        }

        public int getId() {
            return this.id;
        }

        public int getLateMinute() {
            return this.lateMinute;
        }

        public String getParticipants() {
            return this.participants;
        }

        public int getPass_score() {
            return this.pass_score;
        }

        public String getQuestion_file() {
            return this.question_file;
        }

        public int getQuestion_number() {
            return this.question_number;
        }

        public int getScore() {
            return this.score;
        }

        public String getStart_time() {
            return this.start_time;
        }

        public int getStatus() {
            return this.status;
        }

        public int getSubmitMinute() {
            return this.submitMinute;
        }

        public int getTest_paper_id() {
            return this.test_paper_id;
        }

        public int getTime() {
            return this.time;
        }

        public String getTitle() {
            return this.title;
        }

        public String getUpdated_at() {
            return this.updated_at;
        }

        public String getUser_group() {
            return this.user_group;
        }

        public int getUser_score() {
            return this.user_score;
        }

        public void readFromParcel(Parcel source) {
            this.id = source.readInt();
            this.app_id = source.readInt();
            this.hospital_id = source.readInt();
            this.title = source.readString();
            this.description = source.readString();
            this.test_paper_id = source.readInt();
            this.score = source.readInt();
            this.pass_score = source.readInt();
            this.start_time = source.readString();
            this.ent_time = source.readString();
            this.created_admin = source.readString();
            this.answer_number = source.readInt();
            this.answer_time = source.readInt();
            this.status = source.readInt();
            this.question_file = source.readString();
            this.exam_number = source.readInt();
            this.exam_qr_code = source.readString();
            this.participants = source.readString();
            this.user_group = source.readString();
            this.created_at = source.readString();
            this.updated_at = source.readString();
            this.deleted_at = (String) source.readParcelable(Object.class.getClassLoader());
            this.time = source.readInt();
        }

        public void setAnswer_number(int answer_number) {
            this.answer_number = answer_number;
        }

        public void setAnswer_remain_times(int answer_remain_times) {
            this.answer_remain_times = answer_remain_times;
        }

        public void setAnswer_time(int answer_time) {
            this.answer_time = answer_time;
        }

        public void setAppName(String appName) {
            this.appName = appName;
        }

        public void setApp_id(int app_id) {
            this.app_id = app_id;
        }

        public void setCreated_admin(String created_admin) {
            this.created_admin = created_admin;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public void setDeleted_at(String deleted_at) {
            this.deleted_at = deleted_at;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public void setEnt_time(String ent_time) {
            this.ent_time = ent_time;
        }

        public void setExam_number(int exam_number) {
            this.exam_number = exam_number;
        }

        public void setExam_qr_code(String exam_qr_code) {
            this.exam_qr_code = exam_qr_code;
        }

        public void setExam_time(int exam_time) {
            this.exam_time = exam_time;
        }

        public void setHospital_id(int hospital_id) {
            this.hospital_id = hospital_id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setLateMinute(int lateMinute) {
            this.lateMinute = lateMinute;
        }

        public void setParticipants(String participants) {
            this.participants = participants;
        }

        public void setPass_score(int pass_score) {
            this.pass_score = pass_score;
        }

        public void setQuestion_file(String question_file) {
            this.question_file = question_file;
        }

        public void setQuestion_number(int question_number) {
            this.question_number = question_number;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public void setStart_time(String start_time) {
            this.start_time = start_time;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public void setSubmitMinute(int submitMinute) {
            this.submitMinute = submitMinute;
        }

        public void setTest_paper_id(int test_paper_id) {
            this.test_paper_id = test_paper_id;
        }

        public void setTime(int time) {
            this.time = time;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }

        public void setUser_group(String user_group) {
            this.user_group = user_group;
        }

        public void setUser_score(int user_score) {
            this.user_score = user_score;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.id);
            dest.writeInt(this.app_id);
            dest.writeInt(this.hospital_id);
            dest.writeString(this.title);
            dest.writeString(this.description);
            dest.writeInt(this.test_paper_id);
            dest.writeInt(this.score);
            dest.writeInt(this.pass_score);
            dest.writeString(this.start_time);
            dest.writeString(this.ent_time);
            dest.writeString(this.created_admin);
            dest.writeInt(this.answer_number);
            dest.writeInt(this.answer_time);
            dest.writeInt(this.status);
            dest.writeString(this.question_file);
            dest.writeInt(this.exam_number);
            dest.writeString(this.exam_qr_code);
            dest.writeString(this.participants);
            dest.writeString(this.user_group);
            dest.writeString(this.created_at);
            dest.writeString(this.updated_at);
            dest.writeString(this.deleted_at);
            dest.writeInt(this.time);
        }

        public DataBean(Parcel in) {
            this.id = in.readInt();
            this.app_id = in.readInt();
            this.hospital_id = in.readInt();
            this.title = in.readString();
            this.description = in.readString();
            this.test_paper_id = in.readInt();
            this.score = in.readInt();
            this.pass_score = in.readInt();
            this.start_time = in.readString();
            this.ent_time = in.readString();
            this.created_admin = in.readString();
            this.answer_number = in.readInt();
            this.answer_time = in.readInt();
            this.status = in.readInt();
            this.question_file = in.readString();
            this.exam_number = in.readInt();
            this.exam_qr_code = in.readString();
            this.participants = in.readString();
            this.user_group = in.readString();
            this.created_at = in.readString();
            this.updated_at = in.readString();
            this.deleted_at = (String) in.readParcelable(Object.class.getClassLoader());
            this.time = in.readInt();
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

    public int getPrc_time() {
        return this.prc_time;
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

    public void setPrc_time(int prc_time) {
        this.prc_time = prc_time;
    }
}
