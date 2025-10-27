package com.easefun.polyv.livecommon.module.modules.ppt.presenter;

import androidx.annotation.Nullable;
import com.easefun.polyv.livecommon.module.modules.ppt.contract.IPLVLiveFloatingContract;
import com.easefun.polyv.livescenes.socket.PolyvSocketWrapper;
import com.plv.socket.event.PLVEventHelper;
import com.plv.socket.event.chat.PLVOTeacherInfoEvent;
import com.plv.socket.event.login.PLVLoginEvent;
import com.plv.socket.impl.PLVSocketMessageObserver;
import com.plv.socket.user.PLVSocketUserBean;

/* loaded from: classes3.dex */
public class PLVLiveFloatingPresenter implements IPLVLiveFloatingContract.IPLVLiveFloatingPresenter {
    private PLVSocketMessageObserver.OnMessageListener onMessageListener;

    @Nullable
    private IPLVLiveFloatingContract.IPLVLiveFloatingView view;

    @Override // com.easefun.polyv.livecommon.module.modules.ppt.contract.IPLVLiveFloatingContract.IPLVLiveFloatingPresenter
    public void destroy() {
        PolyvSocketWrapper.getInstance().getSocketObserver().removeOnMessageListener(this.onMessageListener);
        this.view = null;
    }

    @Override // com.easefun.polyv.livecommon.module.modules.ppt.contract.IPLVLiveFloatingContract.IPLVLiveFloatingPresenter
    public void init(final IPLVLiveFloatingContract.IPLVLiveFloatingView view) {
        this.view = view;
        this.onMessageListener = new PLVSocketMessageObserver.OnMessageListener() { // from class: com.easefun.polyv.livecommon.module.modules.ppt.presenter.PLVLiveFloatingPresenter.1
            @Override // com.plv.socket.impl.PLVSocketMessageObserver.OnMessageListener
            public void onMessage(String listenEvent, String event, String message) {
                PLVLoginEvent pLVLoginEvent;
                PLVSocketUserBean user;
                PLVSocketUserBean data = null;
                if ("O_TEACHER_INFO".equals(event)) {
                    PLVOTeacherInfoEvent pLVOTeacherInfoEvent = (PLVOTeacherInfoEvent) PLVEventHelper.toMessageEventModel(message, PLVOTeacherInfoEvent.class);
                    if (pLVOTeacherInfoEvent != null) {
                        data = pLVOTeacherInfoEvent.getData();
                    }
                } else if ("LOGIN".equals(event) && (pLVLoginEvent = (PLVLoginEvent) PLVEventHelper.toMessageEventModel(message, PLVLoginEvent.class)) != null && (user = pLVLoginEvent.getUser()) != null && PLVEventHelper.isChatroomTeacher(user.getUserType(), user.getUserSource())) {
                    data = user;
                }
                if (data != null) {
                    String nick = data.getNick();
                    String actor = data.getActor();
                    if (data.getAuthorization() != null) {
                        actor = data.getAuthorization().getActor();
                    }
                    IPLVLiveFloatingContract.IPLVLiveFloatingView iPLVLiveFloatingView = view;
                    if (iPLVLiveFloatingView != null) {
                        iPLVLiveFloatingView.updateTeacherInfo(actor, nick);
                    }
                }
            }
        };
        PolyvSocketWrapper.getInstance().getSocketObserver().addOnMessageListener(this.onMessageListener);
    }
}
