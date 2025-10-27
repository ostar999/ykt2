package com.arialyy.aria.core.download;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.arialyy.aria.core.common.AbsGroupEntity;
import com.arialyy.aria.orm.annotation.Ignore;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class DownloadGroupEntity extends AbsGroupEntity {
    public static final Parcelable.Creator<DownloadGroupEntity> CREATOR = new Parcelable.Creator<DownloadGroupEntity>() { // from class: com.arialyy.aria.core.download.DownloadGroupEntity.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DownloadGroupEntity createFromParcel(Parcel parcel) {
            return new DownloadGroupEntity(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DownloadGroupEntity[] newArray(int i2) {
            return new DownloadGroupEntity[i2];
        }
    };

    @Ignore
    private List<DownloadEntity> subEntities;

    public DownloadGroupEntity() {
    }

    @Override // com.arialyy.aria.core.common.AbsGroupEntity, com.arialyy.aria.core.common.AbsEntity, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public List<DownloadEntity> getSubEntities() {
        if (this.subEntities == null) {
            this.subEntities = new ArrayList();
        }
        return this.subEntities;
    }

    @Override // com.arialyy.aria.core.common.AbsEntity
    public int getTaskType() {
        if (getSubEntities() == null || getSubEntities().isEmpty() || TextUtils.isEmpty(getSubEntities().get(0).getUrl())) {
            return 0;
        }
        return (this.groupHash.startsWith("ftp") || this.groupHash.startsWith("sftp")) ? 4 : 2;
    }

    public void setGroupHash(String str) {
        this.groupHash = str;
    }

    public void setSubEntities(List<DownloadEntity> list) {
        this.subEntities = list;
    }

    @Override // com.arialyy.aria.core.common.AbsGroupEntity, com.arialyy.aria.core.common.AbsEntity, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        super.writeToParcel(parcel, i2);
        parcel.writeTypedList(this.subEntities);
    }

    public DownloadGroupEntity(Parcel parcel) {
        super(parcel);
        this.subEntities = parcel.createTypedArrayList(DownloadEntity.CREATOR);
    }
}
