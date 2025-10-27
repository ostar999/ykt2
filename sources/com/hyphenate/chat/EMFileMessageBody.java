package com.hyphenate.chat;

import android.net.Uri;
import com.hyphenate.chat.adapter.message.EMAFileMessageBody;
import com.hyphenate.chat.adapter.message.EMAImageMessageBody;
import com.hyphenate.chat.adapter.message.EMAVideoMessageBody;
import com.hyphenate.chat.adapter.message.EMAVoiceMessageBody;
import com.hyphenate.util.EMFileHelper;
import java.io.File;

/* loaded from: classes4.dex */
public abstract class EMFileMessageBody extends EMMessageBody {

    /* renamed from: com.hyphenate.chat.EMFileMessageBody$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$hyphenate$chat$adapter$message$EMAFileMessageBody$EMADownloadStatus;

        static {
            int[] iArr = new int[EMAFileMessageBody.EMADownloadStatus.values().length];
            $SwitchMap$com$hyphenate$chat$adapter$message$EMAFileMessageBody$EMADownloadStatus = iArr;
            try {
                iArr[EMAFileMessageBody.EMADownloadStatus.DOWNLOADING.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$hyphenate$chat$adapter$message$EMAFileMessageBody$EMADownloadStatus[EMAFileMessageBody.EMADownloadStatus.SUCCESSED.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$hyphenate$chat$adapter$message$EMAFileMessageBody$EMADownloadStatus[EMAFileMessageBody.EMADownloadStatus.FAILED.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$hyphenate$chat$adapter$message$EMAFileMessageBody$EMADownloadStatus[EMAFileMessageBody.EMADownloadStatus.PENDING.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public enum EMDownloadStatus {
        DOWNLOADING,
        SUCCESSED,
        FAILED,
        PENDING
    }

    public EMFileMessageBody(Uri uri) {
        this(uri, 5);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v5, types: [T, com.hyphenate.chat.adapter.message.EMAFileMessageBody] */
    public EMFileMessageBody(Uri uri, int i2) {
        T eMAImageMessageBody;
        if (i2 == 1) {
            eMAImageMessageBody = new EMAImageMessageBody(uri, (Uri) null);
        } else if (i2 == 2) {
            eMAImageMessageBody = new EMAVideoMessageBody(uri, (Uri) null);
        } else {
            if (i2 != 4) {
                if (i2 != 5) {
                    return;
                }
                this.emaObject = new EMAFileMessageBody(uri, i2);
                setFileLength(EMFileHelper.getInstance().getFileLength(uri));
            }
            eMAImageMessageBody = new EMAVoiceMessageBody(uri, 0);
        }
        this.emaObject = eMAImageMessageBody;
        setFileLength(EMFileHelper.getInstance().getFileLength(uri));
    }

    /* JADX WARN: Multi-variable type inference failed */
    public EMFileMessageBody(EMAFileMessageBody eMAFileMessageBody) {
        this.emaObject = eMAFileMessageBody;
    }

    public EMFileMessageBody(String str) {
        this(EMFileHelper.getInstance().formatInUri(str));
    }

    public EMFileMessageBody(String str, int i2) {
        this(Uri.fromFile(new File(str)), i2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public String displayName() {
        return ((EMAFileMessageBody) this.emaObject).displayName();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public EMDownloadStatus downloadStatus() {
        int i2 = AnonymousClass1.$SwitchMap$com$hyphenate$chat$adapter$message$EMAFileMessageBody$EMADownloadStatus[((EMAFileMessageBody) this.emaObject).downloadStatus().ordinal()];
        return i2 != 1 ? i2 != 2 ? i2 != 3 ? i2 != 4 ? EMDownloadStatus.SUCCESSED : EMDownloadStatus.PENDING : EMDownloadStatus.FAILED : EMDownloadStatus.SUCCESSED : EMDownloadStatus.DOWNLOADING;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public String getFileName() {
        return ((EMAFileMessageBody) this.emaObject).displayName();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public Uri getLocalUri() {
        return EMFileHelper.getInstance().formatOutUri(((EMAFileMessageBody) this.emaObject).getLocalUrl());
    }

    /* JADX WARN: Multi-variable type inference failed */
    public String getLocalUrl() {
        return EMFileHelper.getInstance().formatOutLocalUrl(((EMAFileMessageBody) this.emaObject).getLocalUrl());
    }

    /* JADX WARN: Multi-variable type inference failed */
    public String getRemoteUrl() {
        return ((EMAFileMessageBody) this.emaObject).getRemoteUrl();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public String getSecret() {
        return ((EMAFileMessageBody) this.emaObject).getSecret();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void setDownloadStatus(EMDownloadStatus eMDownloadStatus) {
        ((EMAFileMessageBody) this.emaObject).setDownloadStatus(EMAFileMessageBody.EMADownloadStatus.valueOf(eMDownloadStatus.name()));
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void setFileLength(long j2) {
        ((EMAFileMessageBody) this.emaObject).setFileLength(j2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void setFileName(String str) {
        ((EMAFileMessageBody) this.emaObject).setDisplayName(str);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void setLocalUrl(Uri uri) {
        ((EMAFileMessageBody) this.emaObject).setLocalPath(EMFileHelper.getInstance().formatInUriToString(uri));
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void setLocalUrl(String str) {
        ((EMAFileMessageBody) this.emaObject).setLocalPath(EMFileHelper.getInstance().formatInUriToString(str));
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void setRemoteUrl(String str) {
        ((EMAFileMessageBody) this.emaObject).setRemotePath(str);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void setSecret(String str) {
        ((EMAFileMessageBody) this.emaObject).setSecretKey(str);
    }
}
