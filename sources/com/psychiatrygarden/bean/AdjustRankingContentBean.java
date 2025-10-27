package com.psychiatrygarden.bean;

import android.os.Parcel;
import android.os.Parcelable;
import com.psychiatrygarden.activity.rank.bean.RankEntranceBean;

/* loaded from: classes5.dex */
public class AdjustRankingContentBean implements Parcelable {
    public static final Parcelable.Creator<AdjustRankingContentBean> CREATOR = new Parcelable.Creator<AdjustRankingContentBean>() { // from class: com.psychiatrygarden.bean.AdjustRankingContentBean.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AdjustRankingContentBean createFromParcel(Parcel in) {
            return new AdjustRankingContentBean(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AdjustRankingContentBean[] newArray(int size) {
            return new AdjustRankingContentBean[size];
        }
    };
    private int code;
    private RankEntranceBean.DataBean data;
    private String message;
    private int server_time;

    public AdjustRankingContentBean() {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public int getCode() {
        return this.code;
    }

    public RankEntranceBean.DataBean getData() {
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

    public void setData(RankEntranceBean.DataBean data) {
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

    public AdjustRankingContentBean(Parcel in) {
        this.code = in.readInt();
        this.message = in.readString();
        this.data = (RankEntranceBean.DataBean) in.readParcelable(RankEntranceBean.DataBean.class.getClassLoader());
        this.server_time = in.readInt();
    }
}
