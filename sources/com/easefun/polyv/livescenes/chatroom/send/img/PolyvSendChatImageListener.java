package com.easefun.polyv.livescenes.chatroom.send.img;

import com.plv.foundationsdk.utils.PLVReflectionUtil;
import com.plv.livescenes.chatroom.send.img.PLVSendChatImageListener;
import com.plv.livescenes.chatroom.send.img.PLVSendLocalImgEvent;

@Deprecated
/* loaded from: classes3.dex */
public abstract class PolyvSendChatImageListener implements PLVSendChatImageListener {
    public abstract void onCheckFail(PolyvSendLocalImgEvent polyvSendLocalImgEvent, Throwable th);

    @Override // com.plv.livescenes.chatroom.send.img.PLVSendChatImageListener
    public final void onCheckFail(PLVSendLocalImgEvent pLVSendLocalImgEvent, Throwable th) {
        onCheckFail((PolyvSendLocalImgEvent) PLVReflectionUtil.copyField(pLVSendLocalImgEvent, new PolyvSendLocalImgEvent()), th);
    }

    public abstract void onProgress(PolyvSendLocalImgEvent polyvSendLocalImgEvent, float f2);

    @Override // com.plv.livescenes.chatroom.send.img.PLVSendChatImageListener
    public final void onProgress(PLVSendLocalImgEvent pLVSendLocalImgEvent, float f2) {
        onProgress((PolyvSendLocalImgEvent) PLVReflectionUtil.copyField(pLVSendLocalImgEvent, new PolyvSendLocalImgEvent()), f2);
    }

    public abstract void onSendFail(PolyvSendLocalImgEvent polyvSendLocalImgEvent, int i2);

    @Override // com.plv.livescenes.chatroom.send.img.PLVSendChatImageListener
    public final void onSendFail(PLVSendLocalImgEvent pLVSendLocalImgEvent, int i2) {
        onSendFail((PolyvSendLocalImgEvent) PLVReflectionUtil.copyField(pLVSendLocalImgEvent, new PolyvSendLocalImgEvent()), i2);
    }

    public abstract void onSuccess(PolyvSendLocalImgEvent polyvSendLocalImgEvent, String str, String str2);

    @Override // com.plv.livescenes.chatroom.send.img.PLVSendChatImageListener
    public final void onSuccess(PLVSendLocalImgEvent pLVSendLocalImgEvent, String str, String str2) {
        onSuccess((PolyvSendLocalImgEvent) PLVReflectionUtil.copyField(pLVSendLocalImgEvent, new PolyvSendLocalImgEvent()), str, str2);
    }

    public abstract void onUploadFail(PolyvSendLocalImgEvent polyvSendLocalImgEvent, Throwable th);

    @Override // com.plv.livescenes.chatroom.send.img.PLVSendChatImageListener
    public final void onUploadFail(PLVSendLocalImgEvent pLVSendLocalImgEvent, Throwable th) {
        onUploadFail((PolyvSendLocalImgEvent) PLVReflectionUtil.copyField(pLVSendLocalImgEvent, new PolyvSendLocalImgEvent()), th);
    }
}
