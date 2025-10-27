package com.arialyy.aria.core.upload;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.arialyy.aria.core.common.AbsNormalEntity;
import com.arialyy.aria.orm.annotation.Primary;

/* loaded from: classes2.dex */
public class UploadEntity extends AbsNormalEntity implements Parcelable {
    public static final Parcelable.Creator<UploadEntity> CREATOR = new Parcelable.Creator<UploadEntity>() { // from class: com.arialyy.aria.core.upload.UploadEntity.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public UploadEntity createFromParcel(Parcel parcel) {
            return new UploadEntity(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public UploadEntity[] newArray(int i2) {
            return new UploadEntity[i2];
        }
    };

    @Primary
    private String filePath;
    private String responseStr;

    public UploadEntity() {
        this.responseStr = "";
    }

    @Override // com.arialyy.aria.core.common.AbsNormalEntity, com.arialyy.aria.core.common.AbsEntity, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // com.arialyy.aria.core.common.AbsNormalEntity
    public String getFilePath() {
        return this.filePath;
    }

    @Override // com.arialyy.aria.core.common.AbsEntity
    public String getKey() {
        return this.filePath;
    }

    public String getResponseStr() {
        return this.responseStr;
    }

    @Override // com.arialyy.aria.core.common.AbsEntity
    public int getTaskType() {
        if (TextUtils.isEmpty(getUrl())) {
            return 0;
        }
        return getUrl().startsWith("ftp") ? 6 : 5;
    }

    public void setFilePath(String str) {
        this.filePath = str;
    }

    public void setResponseStr(String str) {
        this.responseStr = str;
    }

    @Override // com.arialyy.aria.core.common.AbsNormalEntity, com.arialyy.aria.core.common.AbsEntity, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        super.writeToParcel(parcel, i2);
        parcel.writeString(this.filePath);
    }

    public UploadEntity(Parcel parcel) {
        super(parcel);
        this.responseStr = "";
        this.filePath = parcel.readString();
    }
}
