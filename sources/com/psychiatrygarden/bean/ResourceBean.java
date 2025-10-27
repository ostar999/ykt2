package com.psychiatrygarden.bean;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes5.dex */
public class ResourceBean implements Parcelable {
    public static final Parcelable.Creator<ResourceBean> CREATOR = new Parcelable.Creator<ResourceBean>() { // from class: com.psychiatrygarden.bean.ResourceBean.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ResourceBean createFromParcel(Parcel source) {
            return new ResourceBean(source);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ResourceBean[] newArray(int size) {
            return new ResourceBean[size];
        }
    };
    private String author;
    private int downloadCount;
    private String fileSize;
    private String id;
    private String name;
    private String saveFileName;
    private String showFileName;
    private String url;

    public ResourceBean(String author, int downloadCount, String url, String id, String name, String fileSize) {
        this.author = author;
        this.name = name;
        this.saveFileName = name;
        this.downloadCount = downloadCount;
        this.url = url;
        this.id = id;
        this.fileSize = fileSize;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String getAuthor() {
        return this.author;
    }

    public int getDownloadCount() {
        return this.downloadCount;
    }

    public String getFileSize() {
        return this.fileSize;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getSaveFileName() {
        return this.saveFileName;
    }

    public String getShowFileName() {
        return this.showFileName;
    }

    public String getUrl() {
        return this.url;
    }

    public void readFromParcel(Parcel source) {
        this.name = source.readString();
        this.author = source.readString();
        this.downloadCount = source.readInt();
        this.url = source.readString();
        this.id = source.readString();
        this.fileSize = source.readString();
        this.saveFileName = source.readString();
        this.showFileName = source.readString();
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setDownloadCount(int downloadCount) {
        this.downloadCount = downloadCount;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSaveFileName(String saveFileName) {
        this.saveFileName = saveFileName;
    }

    public void setShowFileName(String showFileName) {
        this.showFileName = showFileName;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.author);
        dest.writeInt(this.downloadCount);
        dest.writeString(this.url);
        dest.writeString(this.id);
        dest.writeString(this.fileSize);
        dest.writeString(this.saveFileName);
        dest.writeString(this.showFileName);
    }

    public ResourceBean(Parcel in) {
        this.name = in.readString();
        this.author = in.readString();
        this.downloadCount = in.readInt();
        this.url = in.readString();
        this.id = in.readString();
        this.fileSize = in.readString();
        this.saveFileName = in.readString();
        this.showFileName = in.readString();
    }
}
