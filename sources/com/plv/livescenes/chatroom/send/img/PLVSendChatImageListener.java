package com.plv.livescenes.chatroom.send.img;

/* loaded from: classes4.dex */
public interface PLVSendChatImageListener {
    void onCheckFail(PLVSendLocalImgEvent pLVSendLocalImgEvent, Throwable th);

    void onProgress(PLVSendLocalImgEvent pLVSendLocalImgEvent, float f2);

    void onSendFail(PLVSendLocalImgEvent pLVSendLocalImgEvent, int i2);

    void onSuccess(PLVSendLocalImgEvent pLVSendLocalImgEvent, String str, String str2);

    void onUploadFail(PLVSendLocalImgEvent pLVSendLocalImgEvent, Throwable th);
}
