package com.psychiatrygarden.bean;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes5.dex */
public class ReportParams implements Parcelable {
    public static final Parcelable.Creator<ReportParams> CREATOR = new Parcelable.Creator<ReportParams>() { // from class: com.psychiatrygarden.bean.ReportParams.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ReportParams createFromParcel(Parcel source) {
            return new ReportParams(source);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ReportParams[] newArray(int size) {
            return new ReportParams[size];
        }
    };
    private String article_id;
    private String reason_id;
    private String report_description;
    private String report_img;

    public ReportParams() {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String getArticle_id() {
        return this.article_id;
    }

    public String getReason_id() {
        return this.reason_id;
    }

    public String getReport_description() {
        return this.report_description;
    }

    public String getReport_img() {
        return this.report_img;
    }

    public void readFromParcel(Parcel source) {
        this.article_id = source.readString();
        this.report_img = source.readString();
        this.report_description = source.readString();
        this.reason_id = source.readString();
    }

    public void setArticle_id(String article_id) {
        this.article_id = article_id;
    }

    public void setReason_id(String reason_id) {
        this.reason_id = reason_id;
    }

    public void setReport_description(String report_description) {
        this.report_description = report_description;
    }

    public void setReport_img(String report_img) {
        this.report_img = report_img;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.article_id);
        dest.writeString(this.report_img);
        dest.writeString(this.report_description);
        dest.writeString(this.reason_id);
    }

    public ReportParams(Parcel in) {
        this.article_id = in.readString();
        this.report_img = in.readString();
        this.report_description = in.readString();
        this.reason_id = in.readString();
    }
}
