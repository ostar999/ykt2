package com.plv.livescenes.chatroom;

import com.plv.foundationsdk.log.elog.logcode.chat.PLVErrorCodeChatroomImage;
import com.plv.livescenes.chatroom.PLVChatroomManager;
import com.plv.livescenes.chatroom.send.img.PLVSendChatImageListener;
import com.plv.livescenes.chatroom.send.img.PLVSendLocalImgEvent;
import com.plv.livescenes.log.PLVELogSender;
import com.plv.socket.event.PLVEventHelper;
import com.plv.socket.event.chat.PLVChatImgContent;
import com.plv.socket.event.chat.PLVChatImgEvent;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import java.util.List;

/* loaded from: classes4.dex */
class PLVChatRoomImgOperation extends PLVChatRoomBaseOperation {
    public PLVChatRoomImgOperation(PLVChatroomManager pLVChatroomManager) {
        super(pLVChatroomManager, "CHAT_IMG");
    }

    @Override // com.plv.livescenes.chatroom.PLVChatRoomBaseOperation
    public void operate(String str, String str2) {
        final PLVChatImgEvent pLVChatImgEvent;
        String imageFilePath;
        List<PLVSendChatImageListener> list;
        CompositeDisposable compositeDisposable;
        if (this.chatroomManager == null || (pLVChatImgEvent = (PLVChatImgEvent) PLVEventHelper.toEventModel(str, this.event, str2, PLVChatImgEvent.class)) == null || pLVChatImgEvent.getUser() == null || !this.chatroomManager.getLoginVO().getUserId().equals(pLVChatImgEvent.getUser().getUserId()) || pLVChatImgEvent.getValues() == null || pLVChatImgEvent.getValues().isEmpty()) {
            return;
        }
        final PLVChatImgContent pLVChatImgContent = pLVChatImgEvent.getValues().get(0);
        final PLVSendLocalImgEvent pLVSendLocalImgEvent = this.chatroomManager.sendImgIdMap.get(pLVChatImgContent.getId());
        if (pLVSendLocalImgEvent != null) {
            pLVSendLocalImgEvent.setId(pLVChatImgEvent.getId());
            imageFilePath = pLVSendLocalImgEvent.getImageFilePath();
        } else {
            imageFilePath = null;
        }
        if (imageFilePath == null || (list = this.chatroomManager.sendChatImageListeners) == null || list.isEmpty() || (compositeDisposable = this.chatroomManager.compositeDisposable) == null) {
            return;
        }
        compositeDisposable.add(AndroidSchedulers.mainThread().createWorker().schedule(new Runnable() { // from class: com.plv.livescenes.chatroom.PLVChatRoomImgOperation.1
            @Override // java.lang.Runnable
            public void run() {
                if (!pLVChatImgEvent.isResult()) {
                    PLVELogSender.send(PLVErrorCodeChatroomImage.class, 5, new Exception("图片审核不通过, event is " + pLVChatImgEvent.toString()));
                }
                PLVChatRoomImgOperation.this.chatroomManager.callbackChatImage(new PLVChatroomManager.ChatImageRunnable() { // from class: com.plv.livescenes.chatroom.PLVChatRoomImgOperation.1.1
                    @Override // com.plv.livescenes.chatroom.PLVChatroomManager.ChatImageRunnable
                    public void run(PLVSendChatImageListener pLVSendChatImageListener) {
                        if (!pLVChatImgEvent.isResult()) {
                            pLVSendChatImageListener.onCheckFail(pLVSendLocalImgEvent, new Exception("图片不合法"));
                        } else {
                            AnonymousClass1 anonymousClass1 = AnonymousClass1.this;
                            pLVSendChatImageListener.onSuccess(pLVSendLocalImgEvent, pLVChatImgContent.getUploadImgUrl(), pLVChatImgContent.getId());
                        }
                    }
                });
            }
        }));
    }
}
