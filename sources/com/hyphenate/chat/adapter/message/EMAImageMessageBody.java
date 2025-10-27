package com.hyphenate.chat.adapter.message;

import android.net.Uri;
import com.hyphenate.chat.adapter.message.EMAFileMessageBody;
import com.hyphenate.util.EMFileHelper;

/* loaded from: classes4.dex */
public class EMAImageMessageBody extends EMAFileMessageBody {
    public long fileLength;
    public double height;
    public int thumbnailDownloadStatus;
    public String thumbnailLocalPath;
    public String thumbnailRemotePath;
    public String thumbnailSecretKey;
    public double width;

    private EMAImageMessageBody() {
        super("", 1);
        nativeInit("", "");
    }

    public EMAImageMessageBody(Uri uri, Uri uri2) {
        super(uri, 1);
        nativeInit(EMFileHelper.getInstance().uriToString(uri), EMFileHelper.getInstance().uriToString(uri2));
    }

    public EMAImageMessageBody(EMAImageMessageBody eMAImageMessageBody) {
        super("", 1);
        nativeInit(eMAImageMessageBody);
    }

    public EMAImageMessageBody(String str, String str2) {
        super(str, 1);
        nativeInit(str, str2);
    }

    @Override // com.hyphenate.chat.adapter.message.EMAFileMessageBody
    public void finalize() throws Throwable {
        nativeFinalize();
        super.finalize();
    }

    public int height() {
        return nativeheight();
    }

    @Override // com.hyphenate.chat.adapter.message.EMAFileMessageBody
    public native void nativeFinalize();

    public native void nativeInit(EMAImageMessageBody eMAImageMessageBody);

    public native void nativeInit(String str, String str2);

    public native int nativeheight();

    public native void nativesetSize(int i2, int i3);

    public native void nativesetThumbnailDisplayName(String str);

    public native void nativesetThumbnailDownloadStatus(int i2);

    public native void nativesetThumbnailFileLength(long j2);

    public native void nativesetThumbnailLocalPath(String str);

    public native void nativesetThumbnailRemotePath(String str);

    public native void nativesetThumbnailSecretKey(String str);

    public native void nativesetThumbnailSize(int i2, int i3);

    public native String nativethumbnailDisplayName();

    public native int nativethumbnailDownloadStatus();

    public native long nativethumbnailFileLength();

    public native int nativethumbnailHeight();

    public native String nativethumbnailLocalPath();

    public native String nativethumbnailRemotePath();

    public native String nativethumbnailSecretKey();

    public native int nativethumbnailWidth();

    public native int nativewidth();

    public void setSize(int i2, int i3) {
        nativesetSize(i2, i3);
    }

    public void setThumbnailDisplayName(String str) {
        nativesetThumbnailDisplayName(str);
    }

    public void setThumbnailDownloadStatus(EMAFileMessageBody.EMADownloadStatus eMADownloadStatus) {
        nativesetThumbnailDownloadStatus(eMADownloadStatus.ordinal());
    }

    public void setThumbnailFileLength(long j2) {
        nativesetThumbnailFileLength(j2);
    }

    public void setThumbnailLocalPath(String str) {
        nativesetThumbnailLocalPath(str);
    }

    public void setThumbnailRemotePath(String str) {
        nativesetThumbnailRemotePath(str);
    }

    public void setThumbnailSecretKey(String str) {
        nativesetThumbnailSecretKey(str);
    }

    public void setThumbnailSize(int i2, int i3) {
        nativesetThumbnailSize(i2, i3);
    }

    public String thumbnailDisplayName() {
        return nativethumbnailDisplayName();
    }

    public EMAFileMessageBody.EMADownloadStatus thumbnailDownloadStatus() {
        int iNativethumbnailDownloadStatus = nativethumbnailDownloadStatus();
        EMAFileMessageBody.EMADownloadStatus eMADownloadStatus = EMAFileMessageBody.EMADownloadStatus.DOWNLOADING;
        if (iNativethumbnailDownloadStatus == eMADownloadStatus.ordinal()) {
            return eMADownloadStatus;
        }
        EMAFileMessageBody.EMADownloadStatus eMADownloadStatus2 = EMAFileMessageBody.EMADownloadStatus.SUCCESSED;
        if (iNativethumbnailDownloadStatus == eMADownloadStatus2.ordinal()) {
            return eMADownloadStatus2;
        }
        EMAFileMessageBody.EMADownloadStatus eMADownloadStatus3 = EMAFileMessageBody.EMADownloadStatus.FAILED;
        return iNativethumbnailDownloadStatus == eMADownloadStatus3.ordinal() ? eMADownloadStatus3 : EMAFileMessageBody.EMADownloadStatus.PENDING;
    }

    public long thumbnailFileLength() {
        return nativethumbnailFileLength();
    }

    public int thumbnailHeight() {
        return nativethumbnailHeight();
    }

    public String thumbnailLocalPath() {
        return nativethumbnailLocalPath();
    }

    public String thumbnailRemotePath() {
        return nativethumbnailRemotePath();
    }

    public String thumbnailSecretKey() {
        return nativethumbnailSecretKey();
    }

    public int thumbnailWidth() {
        return nativethumbnailWidth();
    }

    public int width() {
        return nativewidth();
    }
}
