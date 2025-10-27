package com.psychiatrygarden.bean;

import android.os.Parcel;
import android.os.Parcelable;
import java.io.Serializable;
import java.util.ArrayList;

/* loaded from: classes5.dex */
public class RankingContentBean implements Serializable {
    private String code;
    private DataBean data;
    private String message;
    private String server_time;

    public static class DataBean implements Serializable {
        private String exam_time;
        private String major_direction_title;
        private String major_title;
        private String own_rank;
        private String own_rank_2;
        private String own_rank_3;
        private ArrayList<RankingBean> ranking;
        private ArrayList<RankingBean> ranking_major;
        private ArrayList<RankingBean> ranking_major_direction;
        private String school_department_title;
        private String school_title;
        private String score;
        private String zhuan_xue;
        private String number_of_test = "0";
        private String number_of_test_2 = "0";
        private String number_of_test_3 = "0";
        private boolean isAll = true;

        public static class RankingBean implements Parcelable {
            private String avatar;
            private String exam_time;
            private String nickname;
            private String score;
            private String user_id;

            @Override // android.os.Parcelable
            public int describeContents() {
                return 0;
            }

            public String getAvatar() {
                return this.avatar;
            }

            public String getExam_time() {
                return this.exam_time;
            }

            public String getNickname() {
                return this.nickname;
            }

            public String getScore() {
                return this.score;
            }

            public String getUser_id() {
                return this.user_id;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public void setExam_time(String exam_time) {
                this.exam_time = exam_time;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public void setScore(String score) {
                this.score = score;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }

            @Override // android.os.Parcelable
            public void writeToParcel(Parcel parcel, int i2) {
            }
        }

        public String getExam_time() {
            return this.exam_time;
        }

        public String getMajor_direction_title() {
            return this.major_direction_title;
        }

        public String getMajor_title() {
            return this.major_title;
        }

        public String getNumber_of_test() {
            return this.number_of_test;
        }

        public String getNumber_of_test_2() {
            return this.number_of_test_2;
        }

        public String getNumber_of_test_3() {
            return this.number_of_test_3;
        }

        public String getOwn_rank() {
            return this.own_rank;
        }

        public String getOwn_rank_2() {
            return this.own_rank_2;
        }

        public String getOwn_rank_3() {
            return this.own_rank_3;
        }

        public ArrayList<RankingBean> getRanking() {
            ArrayList<RankingBean> arrayList = this.ranking;
            return arrayList == null ? new ArrayList<>() : arrayList;
        }

        public ArrayList<RankingBean> getRanking_major() {
            return this.ranking_major;
        }

        public ArrayList<RankingBean> getRanking_major_direction() {
            return this.ranking_major_direction;
        }

        public String getSchool_department_title() {
            return this.school_department_title;
        }

        public String getSchool_title() {
            return this.school_title;
        }

        public String getScore() {
            return this.score;
        }

        public String getZhuan_xue() {
            return this.zhuan_xue;
        }

        public boolean isAll() {
            return this.isAll;
        }

        public void setAll(boolean all) {
            this.isAll = all;
        }

        public void setExam_time(String exam_time) {
            this.exam_time = exam_time;
        }

        public void setMajor_direction_title(String major_direction_title) {
            this.major_direction_title = major_direction_title;
        }

        public void setMajor_title(String major_title) {
            this.major_title = major_title;
        }

        public void setNumber_of_test(String number_of_test) {
            this.number_of_test = number_of_test;
        }

        public void setNumber_of_test_2(String number_of_test_2) {
            this.number_of_test_2 = number_of_test_2;
        }

        public void setNumber_of_test_3(String number_of_test_3) {
            this.number_of_test_3 = number_of_test_3;
        }

        public void setOwn_rank(String own_rank) {
            this.own_rank = own_rank;
        }

        public void setOwn_rank_2(String own_rank_2) {
            this.own_rank_2 = own_rank_2;
        }

        public void setOwn_rank_3(String own_rank_3) {
            this.own_rank_3 = own_rank_3;
        }

        public void setRanking(ArrayList<RankingBean> ranking) {
            this.ranking = ranking;
        }

        public void setRanking_major(ArrayList<RankingBean> ranking_major) {
            this.ranking_major = ranking_major;
        }

        public void setRanking_major_direction(ArrayList<RankingBean> ranking_major_direction) {
            this.ranking_major_direction = ranking_major_direction;
        }

        public void setSchool_department_title(String school_department_title) {
            this.school_department_title = school_department_title;
        }

        public void setSchool_title(String school_title) {
            this.school_title = school_title;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public void setZhuan_xue(String zhuan_xue) {
            this.zhuan_xue = zhuan_xue;
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
