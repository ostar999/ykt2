package com.arialyy.aria.core.common;

import android.os.Parcel;
import android.os.Parcelable;
import com.arialyy.aria.orm.annotation.Unique;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public abstract class AbsGroupEntity extends AbsEntity implements Parcelable {
    private String alias;

    @Unique
    private String dirPath;

    @Unique
    protected String groupHash;
    private List<String> urls;

    public AbsGroupEntity() {
        this.urls = new ArrayList();
    }

    @Override // com.arialyy.aria.core.common.AbsEntity, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String getAlias() {
        return this.alias;
    }

    public String getDirPath() {
        return this.dirPath;
    }

    public String getGroupHash() {
        return this.groupHash;
    }

    @Override // com.arialyy.aria.core.common.AbsEntity
    public String getKey() {
        return this.groupHash;
    }

    public List<String> getUrls() {
        return this.urls;
    }

    public void setAlias(String str) {
        this.alias = str;
    }

    public void setDirPath(String str) {
        this.dirPath = str;
    }

    public void setUrls(List<String> list) {
        this.urls = list;
    }

    @Override // com.arialyy.aria.core.common.AbsEntity, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        super.writeToParcel(parcel, i2);
        parcel.writeString(this.groupHash);
        parcel.writeString(this.alias);
    }

    public AbsGroupEntity(Parcel parcel) {
        super(parcel);
        this.urls = new ArrayList();
        this.groupHash = parcel.readString();
        this.alias = parcel.readString();
    }
}
