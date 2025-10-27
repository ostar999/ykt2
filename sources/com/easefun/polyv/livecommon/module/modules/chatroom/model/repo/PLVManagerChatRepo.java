package com.easefun.polyv.livecommon.module.modules.chatroom.model.repo;

import android.util.Pair;
import com.easefun.polyv.livecommon.module.modules.chatroom.contract.IPLVChatroomContract;
import com.easefun.polyv.livecommon.module.modules.chatroom.model.datasource.PLVManagerChatHistoryDataSource;
import com.easefun.polyv.livecommon.module.modules.chatroom.model.datasource.PLVManagerChatOnlineDataSource;
import com.easefun.polyv.livecommon.module.modules.chatroom.model.vo.PLVManagerChatHistoryLoadStatus;
import com.easefun.polyv.livecommon.ui.widget.itemview.PLVBaseViewData;
import com.easefun.polyv.livescenes.chatroom.PolyvLocalMessage;
import com.easefun.polyv.livescenes.chatroom.send.img.PolyvSendLocalImgEvent;
import com.plv.socket.event.PLVBaseEvent;
import io.reactivex.Observable;

/* loaded from: classes3.dex */
public class PLVManagerChatRepo {
    private Observable<PLVBaseViewData<PLVBaseEvent>> managerChatObservable;
    private final PLVManagerChatHistoryDataSource historyDataSource = new PLVManagerChatHistoryDataSource();
    private final PLVManagerChatOnlineDataSource onlineDataSource = new PLVManagerChatOnlineDataSource();

    public PLVManagerChatRepo() {
        observeDataSource();
    }

    private void observeDataSource() {
        this.managerChatObservable = Observable.merge(this.historyDataSource.chatEventObservable, this.onlineDataSource.chatEventObservable);
    }

    public Observable<PLVManagerChatHistoryLoadStatus> getHistoryLoadStatusObservable() {
        return this.historyDataSource.loadStatusObservable;
    }

    public Observable<PLVBaseViewData<PLVBaseEvent>> getManagerChatObservable() {
        return this.managerChatObservable;
    }

    public void init(IPLVChatroomContract.IChatroomPresenter chatroomPresenter) {
        this.onlineDataSource.init(chatroomPresenter);
    }

    public void requestChatHistory(final String roomId, final int start, final int end) {
        this.historyDataSource.requestChatHistory(roomId, start, end);
    }

    public void sendImageMessage(PolyvSendLocalImgEvent message) {
        this.onlineDataSource.sendImageMessage(message);
    }

    public Pair<Boolean, Integer> sendTextMessage(PolyvLocalMessage message) {
        return this.onlineDataSource.sendTextMessage(message);
    }
}
