package com.easefun.polyv.livecommon.module.modules.chatroom.model.datasource;

import android.util.Pair;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.easefun.polyv.livecommon.module.modules.chatroom.contract.IPLVChatroomContract;
import com.easefun.polyv.livecommon.module.modules.chatroom.view.PLVAbsChatroomView;
import com.easefun.polyv.livecommon.ui.widget.itemview.PLVBaseViewData;
import com.easefun.polyv.livescenes.chatroom.PolyvLocalMessage;
import com.easefun.polyv.livescenes.chatroom.send.img.PolyvSendLocalImgEvent;
import com.plv.foundationsdk.log.PLVCommonLog;
import com.plv.foundationsdk.utils.PLVSugarUtil;
import com.plv.livescenes.chatroom.PLVChatroomManager;
import com.plv.livescenes.chatroom.send.img.PLVSendChatImageListener;
import com.plv.livescenes.chatroom.send.img.PLVSendLocalImgEvent;
import com.plv.socket.event.PLVBaseEvent;
import com.plv.socket.event.chat.PLVChatImgEvent;
import com.plv.socket.event.chat.PLVSpeakEvent;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/* loaded from: classes3.dex */
public class PLVManagerChatOnlineDataSource {
    private static final String TAG = "PLVManagerChatOnlineDataSource";

    @Nullable
    private IPLVChatroomContract.IChatroomPresenter chatroomPresenter;
    private ObservableEmitter<PLVBaseViewData<PLVBaseEvent>> emitter;
    public final Observable<PLVBaseViewData<PLVBaseEvent>> chatEventObservable = Observable.create(new ObservableOnSubscribe<PLVBaseViewData<PLVBaseEvent>>() { // from class: com.easefun.polyv.livecommon.module.modules.chatroom.model.datasource.PLVManagerChatOnlineDataSource.1
        @Override // io.reactivex.ObservableOnSubscribe
        public void subscribe(@NonNull ObservableEmitter<PLVBaseViewData<PLVBaseEvent>> observableEmitter) {
            PLVManagerChatOnlineDataSource.this.emitter = observableEmitter;
        }
    });
    private final PLVAbsChatroomView chatroomView = new PLVAbsChatroomView() { // from class: com.easefun.polyv.livecommon.module.modules.chatroom.model.datasource.PLVManagerChatOnlineDataSource.2
        @Override // com.easefun.polyv.livecommon.module.modules.chatroom.view.PLVAbsChatroomView, com.easefun.polyv.livecommon.module.modules.chatroom.contract.IPLVChatroomContract.IChatroomView
        public void onImgEvent(@NonNull PLVChatImgEvent chatImgEvent) {
            if (!chatImgEvent.isManagerChatMsg() || PLVManagerChatOnlineDataSource.this.emitter == null) {
                return;
            }
            PLVManagerChatOnlineDataSource.this.emitter.onNext(new PLVBaseViewData(chatImgEvent, 0));
        }

        @Override // com.easefun.polyv.livecommon.module.modules.chatroom.view.PLVAbsChatroomView, com.easefun.polyv.livecommon.module.modules.chatroom.contract.IPLVChatroomContract.IChatroomView
        public void onLocalImageMessage(@Nullable PolyvSendLocalImgEvent localImgEvent) {
            if (localImgEvent == null || !localImgEvent.isManagerChatMsg() || PLVManagerChatOnlineDataSource.this.emitter == null) {
                return;
            }
            PLVManagerChatOnlineDataSource.this.emitter.onNext(new PLVBaseViewData(localImgEvent, 0));
        }

        @Override // com.easefun.polyv.livecommon.module.modules.chatroom.view.PLVAbsChatroomView, com.easefun.polyv.livecommon.module.modules.chatroom.contract.IPLVChatroomContract.IChatroomView
        public void onLocalSpeakMessage(@Nullable PolyvLocalMessage localMessage) {
            if (localMessage == null || !localMessage.isManagerChatMsg() || PLVManagerChatOnlineDataSource.this.emitter == null) {
                return;
            }
            PLVManagerChatOnlineDataSource.this.emitter.onNext(new PLVBaseViewData(localMessage, 0));
        }

        @Override // com.easefun.polyv.livecommon.module.modules.chatroom.view.PLVAbsChatroomView, com.easefun.polyv.livecommon.module.modules.chatroom.contract.IPLVChatroomContract.IChatroomView
        public void onSpeakEvent(@NonNull PLVSpeakEvent speakEvent) {
            if (!speakEvent.isManagerChatMsg() || PLVManagerChatOnlineDataSource.this.emitter == null) {
                return;
            }
            PLVManagerChatOnlineDataSource.this.emitter.onNext(new PLVBaseViewData(speakEvent, 0));
        }
    };
    private final PLVSendChatImageListener sendChatImageListener = new PLVSendChatImageListener() { // from class: com.easefun.polyv.livecommon.module.modules.chatroom.model.datasource.PLVManagerChatOnlineDataSource.3
        @Override // com.plv.livescenes.chatroom.send.img.PLVSendChatImageListener
        public void onCheckFail(PLVSendLocalImgEvent localImgEvent, Throwable t2) {
            localImgEvent.setSendStatus(1);
            if (!localImgEvent.isManagerChatMsg() || PLVManagerChatOnlineDataSource.this.emitter == null) {
                return;
            }
            PLVManagerChatOnlineDataSource.this.emitter.onNext(new PLVBaseViewData(localImgEvent, 0));
        }

        @Override // com.plv.livescenes.chatroom.send.img.PLVSendChatImageListener
        public void onProgress(PLVSendLocalImgEvent localImgEvent, float progress) {
            localImgEvent.setSendStatus(2);
            localImgEvent.setSendProgress((int) (progress * 100.0f));
            if (!localImgEvent.isManagerChatMsg() || PLVManagerChatOnlineDataSource.this.emitter == null) {
                return;
            }
            PLVManagerChatOnlineDataSource.this.emitter.onNext(new PLVBaseViewData(localImgEvent, 0));
        }

        @Override // com.plv.livescenes.chatroom.send.img.PLVSendChatImageListener
        public void onSendFail(PLVSendLocalImgEvent localImgEvent, int sendValue) {
            localImgEvent.setSendStatus(1);
            if (!localImgEvent.isManagerChatMsg() || PLVManagerChatOnlineDataSource.this.emitter == null) {
                return;
            }
            PLVManagerChatOnlineDataSource.this.emitter.onNext(new PLVBaseViewData(localImgEvent, 0));
        }

        @Override // com.plv.livescenes.chatroom.send.img.PLVSendChatImageListener
        public void onSuccess(PLVSendLocalImgEvent localImgEvent, String uploadImgUrl, String imgId) {
            localImgEvent.setSendStatus(0);
            if (!localImgEvent.isManagerChatMsg() || PLVManagerChatOnlineDataSource.this.emitter == null) {
                return;
            }
            PLVManagerChatOnlineDataSource.this.emitter.onNext(new PLVBaseViewData(localImgEvent, 0));
        }

        @Override // com.plv.livescenes.chatroom.send.img.PLVSendChatImageListener
        public void onUploadFail(PLVSendLocalImgEvent localImgEvent, Throwable t2) {
            localImgEvent.setSendStatus(1);
            if (!localImgEvent.isManagerChatMsg() || PLVManagerChatOnlineDataSource.this.emitter == null) {
                return;
            }
            PLVManagerChatOnlineDataSource.this.emitter.onNext(new PLVBaseViewData(localImgEvent, 0));
        }
    };

    private void initPresenter(IPLVChatroomContract.IChatroomPresenter chatroomPresenter) {
        chatroomPresenter.registerView(this.chatroomView);
    }

    private void initSendChatImageListener() {
        PLVChatroomManager.getInstance().addSendChatImageListener(this.sendChatImageListener);
    }

    public void init(IPLVChatroomContract.IChatroomPresenter chatroomPresenter) {
        this.chatroomPresenter = chatroomPresenter;
        initPresenter(chatroomPresenter);
        initSendChatImageListener();
    }

    public void sendImageMessage(PolyvSendLocalImgEvent message) {
        IPLVChatroomContract.IChatroomPresenter iChatroomPresenter = this.chatroomPresenter;
        if (iChatroomPresenter == null) {
            PLVCommonLog.e(TAG, "sendImageMessage: chatroomPresenter is null");
        } else {
            iChatroomPresenter.sendChatImage(message);
        }
    }

    public Pair<Boolean, Integer> sendTextMessage(PolyvLocalMessage message) {
        IPLVChatroomContract.IChatroomPresenter iChatroomPresenter = this.chatroomPresenter;
        if (iChatroomPresenter != null) {
            return iChatroomPresenter.sendChatMessage(message);
        }
        PLVCommonLog.e(TAG, "sendTextMessage: chatroomPresenter is null");
        return PLVSugarUtil.pair(Boolean.FALSE, -2);
    }
}
