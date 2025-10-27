package com.hyphenate.chat;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import com.hyphenate.chat.adapter.message.EMAFileMessageBody;
import com.hyphenate.util.EMFileHelper;
import com.hyphenate.util.EMLog;
import java.io.File;

/* loaded from: classes4.dex */
public class EMNormalFileMessageBody extends EMFileMessageBody implements Parcelable {
    public static final Parcelable.Creator<EMNormalFileMessageBody> CREATOR = new Parcelable.Creator<EMNormalFileMessageBody>() { // from class: com.hyphenate.chat.EMNormalFileMessageBody.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public EMNormalFileMessageBody createFromParcel(Parcel parcel) {
            return new EMNormalFileMessageBody(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public EMNormalFileMessageBody[] newArray(int i2) {
            return new EMNormalFileMessageBody[i2];
        }
    };

    public EMNormalFileMessageBody() {
        super("");
    }

    /* JADX WARN: Multi-variable type inference failed */
    public EMNormalFileMessageBody(Uri uri) {
        super(uri);
        ((EMAFileMessageBody) this.emaObject).setDisplayName(EMFileHelper.getInstance().getFilename(uri));
    }

    /* JADX WARN: Multi-variable type inference failed */
    private EMNormalFileMessageBody(Parcel parcel) {
        super("");
        ((EMAFileMessageBody) this.emaObject).setDisplayName(parcel.readString());
        ((EMAFileMessageBody) this.emaObject).setLocalPath(parcel.readString());
        ((EMAFileMessageBody) this.emaObject).setRemotePath(parcel.readString());
        ((EMAFileMessageBody) this.emaObject).setFileLength(parcel.readLong());
        ((EMAFileMessageBody) this.emaObject).setSecretKey(parcel.readString());
    }

    public EMNormalFileMessageBody(EMAFileMessageBody eMAFileMessageBody) {
        super(eMAFileMessageBody);
    }

    public EMNormalFileMessageBody(File file) {
        this(Uri.fromFile(file));
    }

    public EMNormalFileMessageBody(String str, String str2) {
        super(str);
        super.setRemoteUrl(str2);
        EMLog.d("EMNormalFileMessageBody", "filename = " + str);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public long getFileSize() {
        return ((EMAFileMessageBody) this.emaObject).fileLength();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public String toString() {
        return "normal file:" + ((EMAFileMessageBody) this.emaObject).displayName() + ",localUrl:" + ((EMAFileMessageBody) this.emaObject).getLocalUrl() + ",remoteUrl:" + ((EMAFileMessageBody) this.emaObject).getRemoteUrl() + ",file size:" + ((EMAFileMessageBody) this.emaObject).fileLength();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeString(((EMAFileMessageBody) this.emaObject).displayName());
        parcel.writeString(((EMAFileMessageBody) this.emaObject).getLocalUrl());
        parcel.writeString(((EMAFileMessageBody) this.emaObject).getRemoteUrl());
        parcel.writeLong(((EMAFileMessageBody) this.emaObject).fileLength());
        parcel.writeString(((EMAFileMessageBody) this.emaObject).getSecret());
    }
}
