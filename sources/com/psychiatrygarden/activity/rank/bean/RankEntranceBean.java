package com.psychiatrygarden.activity.rank.bean;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.ArrayList;

/* loaded from: classes5.dex */
public class RankEntranceBean implements Parcelable {
    public static final Parcelable.Creator<RankEntranceBean> CREATOR = new Parcelable.Creator<RankEntranceBean>() { // from class: com.psychiatrygarden.activity.rank.bean.RankEntranceBean.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RankEntranceBean createFromParcel(Parcel in) {
            return new RankEntranceBean(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RankEntranceBean[] newArray(int size) {
            return new RankEntranceBean[size];
        }
    };
    private int code;
    private DataBean data;
    private String message;
    private int server_time;

    public static class DataBean implements Parcelable {
        public static final Parcelable.Creator<DataBean> CREATOR = new Parcelable.Creator<DataBean>() { // from class: com.psychiatrygarden.activity.rank.bean.RankEntranceBean.DataBean.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public DataBean createFromParcel(Parcel in) {
                return new DataBean(in);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public DataBean[] newArray(int size) {
                return new DataBean[size];
            }
        };
        private AdjustInfoBean adjust_info;
        private String description_1;
        private String description_2;
        private String description_3;
        private String description_4;
        private String major_direction_title;
        private String major_title;
        private int own_rank;
        private String own_total;
        private int participants;
        private ArrayList<RanksBean> ranks;
        private ArrayList<RanksBean> ranks_major;
        private ArrayList<RanksBean> ranks_major_direction;
        private String school_department_title;
        private String school_title;
        private ShareData share_info;
        private int status;
        private String sub_title;
        private String title;
        private String zhuan_xue;

        public static class AdjustInfoBean {
            private String activity;
            private String btn_str;

            @SerializedName("share_info")
            private ShareData share_infoX;

            public String getActivity() {
                return this.activity;
            }

            public String getBtn_str() {
                return this.btn_str;
            }

            public ShareData getShare_infoX() {
                return this.share_infoX;
            }

            public void setActivity(String activity) {
                this.activity = activity;
            }

            public void setBtn_str(String btn_str) {
                this.btn_str = btn_str;
            }

            public void setShare_infoX(ShareData share_infoX) {
                this.share_infoX = share_infoX;
            }
        }

        public static class ShareData implements Serializable {
            private String share_content;
            private String share_img;
            private String share_title;
            private int share_type;
            private String share_url;

            public String getShare_content() {
                return this.share_content;
            }

            public String getShare_img() {
                return this.share_img;
            }

            public String getShare_title() {
                return this.share_title;
            }

            public int getShare_type() {
                return this.share_type;
            }

            public String getShare_url() {
                return this.share_url;
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

            public void setShare_type(int share_type) {
                this.share_type = share_type;
            }

            public void setShare_url(String share_url) {
                this.share_url = share_url;
            }
        }

        public DataBean() {
            this.description_1 = "";
            this.description_2 = "";
            this.description_3 = "";
            this.description_4 = "";
            this.own_total = "";
            this.title = "";
            this.sub_title = "";
            this.own_rank = 0;
            this.participants = 0;
            this.status = 0;
            this.school_title = "";
            this.school_department_title = "";
            this.zhuan_xue = "";
            this.major_title = "";
            this.major_direction_title = "";
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public AdjustInfoBean getAdjust_info() {
            return this.adjust_info;
        }

        public String getDescription_1() {
            return this.description_1;
        }

        public String getDescription_2() {
            return this.description_2;
        }

        public String getDescription_3() {
            return this.description_3;
        }

        public String getDescription_4() {
            return this.description_4;
        }

        public String getMajor_direction_title() {
            return this.major_direction_title;
        }

        public String getMajor_title() {
            return this.major_title;
        }

        public int getOwn_rank() {
            return this.own_rank;
        }

        public String getOwn_total() {
            return this.own_total;
        }

        public int getParticipants() {
            return this.participants;
        }

        public ArrayList<RanksBean> getRanks() {
            return this.ranks;
        }

        public ArrayList<RanksBean> getRanks_major() {
            return this.ranks_major;
        }

        public ArrayList<RanksBean> getRanks_major_direction() {
            return this.ranks_major_direction;
        }

        public String getSchool_department_title() {
            return this.school_department_title;
        }

        public String getSchool_title() {
            return this.school_title;
        }

        public ShareData getShare_info() {
            return this.share_info;
        }

        public int getStatus() {
            return this.status;
        }

        public String getSub_title() {
            return this.sub_title;
        }

        public String getTitle() {
            return this.title;
        }

        public String getZhuan_xue() {
            return this.zhuan_xue;
        }

        public void setAdjust_info(AdjustInfoBean adjust_info) {
            this.adjust_info = adjust_info;
        }

        public void setDescription_1(String description_1) {
            this.description_1 = description_1;
        }

        public void setDescription_2(String description_2) {
            this.description_2 = description_2;
        }

        public void setDescription_3(String description_3) {
            this.description_3 = description_3;
        }

        public void setDescription_4(String description_4) {
            this.description_4 = description_4;
        }

        public void setMajor_direction_title(String major_direction_title) {
            this.major_direction_title = major_direction_title;
        }

        public void setMajor_title(String major_title) {
            this.major_title = major_title;
        }

        public void setOwn_rank(int own_rank) {
            this.own_rank = own_rank;
        }

        public void setOwn_total(String own_total) {
            this.own_total = own_total;
        }

        public void setParticipants(int participants) {
            this.participants = participants;
        }

        public void setRanks(ArrayList<RanksBean> ranks) {
            this.ranks = ranks;
        }

        public void setRanks_major(ArrayList<RanksBean> ranks_major) {
            this.ranks_major = ranks_major;
        }

        public void setRanks_major_direction(ArrayList<RanksBean> ranks_major_direction) {
            this.ranks_major_direction = ranks_major_direction;
        }

        public void setSchool_department_title(String school_department_title) {
            this.school_department_title = school_department_title;
        }

        public void setSchool_title(String school_title) {
            this.school_title = school_title;
        }

        public void setShare_info(ShareData share_info) {
            this.share_info = share_info;
        }

        public DataBean setStatus(int status) {
            this.status = status;
            return this;
        }

        public void setSub_title(String sub_title) {
            this.sub_title = sub_title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setZhuan_xue(String zhuan_xue) {
            this.zhuan_xue = zhuan_xue;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i2) {
            parcel.writeString(this.description_1);
            parcel.writeString(this.description_2);
            parcel.writeString(this.description_3);
            parcel.writeString(this.description_4);
            parcel.writeString(this.own_total);
            parcel.writeString(this.title);
            parcel.writeString(this.sub_title);
            parcel.writeInt(this.own_rank);
            parcel.writeInt(this.participants);
            parcel.writeInt(this.status);
            parcel.writeTypedList(this.ranks);
            parcel.writeTypedList(this.ranks_major);
            parcel.writeTypedList(this.ranks_major_direction);
            parcel.writeString(this.school_title);
            parcel.writeString(this.school_department_title);
            parcel.writeString(this.zhuan_xue);
            parcel.writeString(this.major_title);
            parcel.writeString(this.major_direction_title);
        }

        public static class RanksBean implements Parcelable {
            public static final Parcelable.Creator<RanksBean> CREATOR = new Parcelable.Creator<RanksBean>() { // from class: com.psychiatrygarden.activity.rank.bean.RankEntranceBean.DataBean.RanksBean.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public RanksBean createFromParcel(Parcel in) {
                    return new RanksBean(in);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public RanksBean[] newArray(int size) {
                    return new RanksBean[size];
                }
            };
            private String avatar;
            private String grade_info;
            private String nickname;
            private String status;
            private String total;
            private int user_id;

            public RanksBean() {
                this.status = "0";
            }

            @Override // android.os.Parcelable
            public int describeContents() {
                return 0;
            }

            public String getAvatar() {
                return this.avatar;
            }

            public String getGrade_info() {
                return this.grade_info;
            }

            public String getNickname() {
                return this.nickname;
            }

            public String getStatus() {
                return this.status;
            }

            public String getTotal() {
                return this.total;
            }

            public int getUser_id() {
                return this.user_id;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public void setGrade_info(String grade_info) {
                this.grade_info = grade_info;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public RanksBean setStatus(String status) {
                this.status = status;
                return this;
            }

            public void setTotal(String total) {
                this.total = total;
            }

            public void setUser_id(int user_id) {
                this.user_id = user_id;
            }

            @Override // android.os.Parcelable
            public void writeToParcel(Parcel parcel, int i2) {
                parcel.writeInt(this.user_id);
                parcel.writeString(this.avatar);
                parcel.writeString(this.nickname);
                parcel.writeString(this.total);
                parcel.writeString(this.grade_info);
                parcel.writeString(this.status);
            }

            public RanksBean(Parcel in) {
                this.status = "0";
                this.user_id = in.readInt();
                this.avatar = in.readString();
                this.nickname = in.readString();
                this.total = in.readString();
                this.grade_info = in.readString();
                this.status = in.readString();
            }
        }

        public DataBean(Parcel in) {
            this.description_1 = "";
            this.description_2 = "";
            this.description_3 = "";
            this.description_4 = "";
            this.own_total = "";
            this.title = "";
            this.sub_title = "";
            this.own_rank = 0;
            this.participants = 0;
            this.status = 0;
            this.school_title = "";
            this.school_department_title = "";
            this.zhuan_xue = "";
            this.major_title = "";
            this.major_direction_title = "";
            this.description_1 = in.readString();
            this.description_2 = in.readString();
            this.description_3 = in.readString();
            this.description_4 = in.readString();
            this.own_total = in.readString();
            this.title = in.readString();
            this.sub_title = in.readString();
            this.own_rank = in.readInt();
            this.participants = in.readInt();
            this.status = in.readInt();
            Parcelable.Creator<RanksBean> creator = RanksBean.CREATOR;
            this.ranks = in.createTypedArrayList(creator);
            this.ranks_major = in.createTypedArrayList(creator);
            this.ranks_major_direction = in.createTypedArrayList(creator);
            this.school_title = in.readString();
            this.school_department_title = in.readString();
            this.zhuan_xue = in.readString();
            this.major_title = in.readString();
            this.major_direction_title = in.readString();
        }
    }

    public RankEntranceBean() {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
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

    public int getServer_time() {
        return this.server_time;
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

    public void setServer_time(int server_time) {
        this.server_time = server_time;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeInt(this.code);
        parcel.writeString(this.message);
        parcel.writeParcelable(this.data, i2);
        parcel.writeInt(this.server_time);
    }

    public RankEntranceBean(Parcel in) {
        this.code = in.readInt();
        this.message = in.readString();
        this.data = (DataBean) in.readParcelable(DataBean.class.getClassLoader());
        this.server_time = in.readInt();
    }
}
