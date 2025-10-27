package com.arialyy.aria.core.common;

import android.os.Parcel;
import android.os.Parcelable;
import com.arialyy.aria.orm.annotation.Default;
import k.a;

/* loaded from: classes2.dex */
public abstract class AbsNormalEntity extends AbsEntity implements Parcelable {
    private String fileName;

    @Default(a.f27524v)
    private boolean isGroupChild;

    @Default(a.f27524v)
    private boolean isRedirect;
    private String redirectUrl;
    private int taskType;
    private String url;

    public AbsNormalEntity() {
        this.isGroupChild = false;
        this.isRedirect = false;
    }

    @Override // com.arialyy.aria.core.common.AbsEntity, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String getFileName() {
        return this.fileName;
    }

    public abstract String getFilePath();

    public String getRedirectUrl() {
        return this.redirectUrl;
    }

    public String getUrl() {
        return this.url;
    }

    public boolean isGroupChild() {
        return this.isGroupChild;
    }

    public boolean isRedirect() {
        return this.isRedirect;
    }

    public void setFileName(String str) {
        this.fileName = str;
    }

    public void setGroupChild(boolean z2) {
        this.isGroupChild = z2;
    }

    public void setRedirect(boolean z2) {
        this.isRedirect = z2;
    }

    public void setRedirectUrl(String str) {
        this.redirectUrl = str;
    }

    public void setTaskType(int i2) {
        this.taskType = i2;
    }

    public void setUrl(String str) {
        this.url = str;
    }

    @Override // com.arialyy.aria.core.common.AbsEntity, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        super.writeToParcel(parcel, i2);
        parcel.writeString(this.url);
        parcel.writeString(this.fileName);
        parcel.writeByte(this.isGroupChild ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.isRedirect ? (byte) 1 : (byte) 0);
        parcel.writeString(this.redirectUrl);
    }

    public AbsNormalEntity(Parcel parcel) {
        super(parcel);
        this.isGroupChild = false;
        this.isRedirect = false;
        this.url = parcel.readString();
        this.fileName = parcel.readString();
        this.isGroupChild = parcel.readByte() != 0;
        this.isRedirect = parcel.readByte() != 0;
        this.redirectUrl = parcel.readString();
    }
}
