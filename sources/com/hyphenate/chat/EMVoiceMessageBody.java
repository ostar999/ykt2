package com.hyphenate.chat;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import com.hyphenate.chat.adapter.message.EMAVoiceMessageBody;
import com.hyphenate.util.EMFileHelper;
import com.hyphenate.util.EMLog;
import java.io.File;

/* loaded from: classes4.dex */
public class EMVoiceMessageBody extends EMFileMessageBody implements Parcelable {
    public static final Parcelable.Creator<EMVoiceMessageBody> CREATOR = new Parcelable.Creator<EMVoiceMessageBody>() { // from class: com.hyphenate.chat.EMVoiceMessageBody.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public EMVoiceMessageBody createFromParcel(Parcel parcel) {
            return new EMVoiceMessageBody(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public EMVoiceMessageBody[] newArray(int i2) {
            return new EMVoiceMessageBody[i2];
        }
    };

    /* JADX WARN: Multi-variable type inference failed */
    public EMVoiceMessageBody(Uri uri, int i2) {
        super(uri, 4);
        ((EMAVoiceMessageBody) this.emaObject).setDuration(i2);
        ((EMAVoiceMessageBody) this.emaObject).setDisplayName(EMFileHelper.getInstance().getFilename(uri));
        EMLog.d("voicemsg", "create voice, message body for:" + uri);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private EMVoiceMessageBody(Parcel parcel) {
        super("", 4);
        ((EMAVoiceMessageBody) this.emaObject).setDisplayName(parcel.readString());
        ((EMAVoiceMessageBody) this.emaObject).setLocalPath(parcel.readString());
        ((EMAVoiceMessageBody) this.emaObject).setRemotePath(parcel.readString());
        ((EMAVoiceMessageBody) this.emaObject).setDuration(parcel.readInt());
    }

    public EMVoiceMessageBody(EMAVoiceMessageBody eMAVoiceMessageBody) {
        super(eMAVoiceMessageBody);
    }

    public EMVoiceMessageBody(File file, int i2) {
        this(Uri.fromFile(file), i2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public EMVoiceMessageBody(String str, String str2, int i2) {
        super(str, 4);
        ((EMAVoiceMessageBody) this.emaObject).setLocalPath(str);
        ((EMAVoiceMessageBody) this.emaObject).setRemotePath(str2);
        ((EMAVoiceMessageBody) this.emaObject).setDuration(i2);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public long getFileSize() {
        return ((EMAVoiceMessageBody) this.emaObject).fileLength();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int getLength() {
        return ((EMAVoiceMessageBody) this.emaObject).duration();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public String toString() {
        return "voice:" + ((EMAVoiceMessageBody) this.emaObject).displayName() + ",localurl:" + ((EMAVoiceMessageBody) this.emaObject).getLocalUrl() + ",remoteurl:" + ((EMAVoiceMessageBody) this.emaObject).getRemoteUrl() + ",length:" + ((EMAVoiceMessageBody) this.emaObject).duration();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeString(((EMAVoiceMessageBody) this.emaObject).displayName());
        parcel.writeString(((EMAVoiceMessageBody) this.emaObject).getLocalUrl());
        parcel.writeString(((EMAVoiceMessageBody) this.emaObject).getRemoteUrl());
        parcel.writeInt(((EMAVoiceMessageBody) this.emaObject).duration());
    }
}
