package com.easefun.polyv.livecommon.module.modules.socket;

import androidx.annotation.NonNull;
import com.easefun.polyv.livecommon.module.config.PLVLiveChannelConfig;
import com.easefun.polyv.livecommon.module.data.IPLVLiveRoomDataManager;
import com.easefun.polyv.livecommon.module.modules.socket.IPLVSocketLoginManager;
import com.easefun.polyv.livescenes.socket.PolyvSocketWrapper;
import com.plv.foundationsdk.log.PLVCommonLog;
import com.plv.socket.event.PLVEventHelper;
import com.plv.socket.event.login.PLVKickEvent;
import com.plv.socket.event.login.PLVLoginRefuseEvent;
import com.plv.socket.event.login.PLVReloginEvent;
import com.plv.socket.impl.PLVSocketMessageObserver;
import com.plv.socket.net.model.PLVSocketLoginVO;
import com.plv.socket.socketio.PLVSocketIOClient;
import com.plv.socket.socketio.PLVSocketIOObservable;
import com.plv.socket.status.PLVSocketStatus;

/* loaded from: classes3.dex */
public class PLVSocketLoginManager implements IPLVSocketLoginManager {
    private static final String TAG = "PLVSocketLoginManager";
    private IPLVLiveRoomDataManager liveRoomDataManager;
    private IPLVSocketLoginManager.OnSocketEventListener onSocketEventListener;

    public PLVSocketLoginManager(@NonNull IPLVLiveRoomDataManager liveRoomDataManager) {
        this.liveRoomDataManager = liveRoomDataManager;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void acceptConnectStatusChange(PLVSocketStatus status) {
        IPLVSocketLoginManager.OnSocketEventListener onSocketEventListener;
        int status2 = status.getStatus();
        if (status2 == 1) {
            IPLVSocketLoginManager.OnSocketEventListener onSocketEventListener2 = this.onSocketEventListener;
            if (onSocketEventListener2 != null) {
                onSocketEventListener2.handleLoginIng(false);
                return;
            }
            return;
        }
        if (status2 == 2) {
            IPLVSocketLoginManager.OnSocketEventListener onSocketEventListener3 = this.onSocketEventListener;
            if (onSocketEventListener3 != null) {
                onSocketEventListener3.handleLoginSuccess(false);
                return;
            }
            return;
        }
        if (status2 == 3) {
            IPLVSocketLoginManager.OnSocketEventListener onSocketEventListener4 = this.onSocketEventListener;
            if (onSocketEventListener4 != null) {
                onSocketEventListener4.handleLoginIng(true);
                return;
            }
            return;
        }
        if (status2 != 4) {
            if (status2 == 5 && (onSocketEventListener = this.onSocketEventListener) != null) {
                onSocketEventListener.handleLoginFailed(status.getThrowable());
                return;
            }
            return;
        }
        IPLVSocketLoginManager.OnSocketEventListener onSocketEventListener5 = this.onSocketEventListener;
        if (onSocketEventListener5 != null) {
            onSocketEventListener5.handleLoginSuccess(true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void acceptSocketMessage(PLVSocketMessage socketMessage) {
        IPLVSocketLoginManager.OnSocketEventListener onSocketEventListener;
        String event = socketMessage.getEvent();
        String message = socketMessage.getMessage();
        if ("message".equals(socketMessage.getListenEvent())) {
            event.hashCode();
            switch (event) {
                case "KICK":
                    PLVKickEvent pLVKickEvent = (PLVKickEvent) PLVEventHelper.toMessageEventModel(message, PLVKickEvent.class);
                    if (pLVKickEvent != null) {
                        boolean zEquals = PolyvSocketWrapper.getInstance().getLoginVO().getUserId().equals(pLVKickEvent.getUser().getUserId());
                        IPLVSocketLoginManager.OnSocketEventListener onSocketEventListener2 = this.onSocketEventListener;
                        if (onSocketEventListener2 != null) {
                            onSocketEventListener2.onKickEvent(pLVKickEvent, zEquals);
                            break;
                        }
                    }
                    break;
                case "LOGIN_REFUSE":
                    PLVLoginRefuseEvent pLVLoginRefuseEvent = (PLVLoginRefuseEvent) PLVEventHelper.toMessageEventModel(message, PLVLoginRefuseEvent.class);
                    if (pLVLoginRefuseEvent != null) {
                        disconnect();
                        IPLVSocketLoginManager.OnSocketEventListener onSocketEventListener3 = this.onSocketEventListener;
                        if (onSocketEventListener3 != null) {
                            onSocketEventListener3.onLoginRefuseEvent(pLVLoginRefuseEvent);
                            break;
                        }
                    }
                    break;
                case "RELOGIN":
                    PLVReloginEvent pLVReloginEvent = (PLVReloginEvent) PLVEventHelper.toMessageEventModel(message, PLVReloginEvent.class);
                    if (pLVReloginEvent != null && (onSocketEventListener = this.onSocketEventListener) != null) {
                        onSocketEventListener.onReloginEvent(pLVReloginEvent);
                        break;
                    }
                    break;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public PLVLiveChannelConfig getConfig() {
        return this.liveRoomDataManager.getConfig();
    }

    @Override // com.easefun.polyv.livecommon.module.modules.socket.IPLVSocketLoginManager
    public void destroy() {
        this.onSocketEventListener = null;
        PolyvSocketWrapper.getInstance().destroy();
    }

    @Override // com.easefun.polyv.livecommon.module.modules.socket.IPLVSocketLoginManager
    public void disconnect() {
        PolyvSocketWrapper.getInstance().disconnect();
    }

    @Override // com.easefun.polyv.livecommon.module.modules.socket.IPLVSocketLoginManager
    public void init() {
        PolyvSocketWrapper.getInstance().getSocketObserver().addOnConnectStatusListener(new PLVSocketIOObservable.OnConnectStatusListener() { // from class: com.easefun.polyv.livecommon.module.modules.socket.PLVSocketLoginManager.1
            @Override // com.plv.socket.socketio.PLVSocketIOObservable.OnConnectStatusListener
            public void onStatus(PLVSocketStatus status) {
                PLVCommonLog.d(PLVSocketLoginManager.TAG, "socket onStatus: " + status);
                if (PLVSocketLoginManager.this.getConfig().getChannelId().equals(PolyvSocketWrapper.getInstance().getLoginVO().getChannelId())) {
                    PLVSocketLoginManager.this.acceptConnectStatusChange(status);
                }
            }
        });
        PolyvSocketWrapper.getInstance().getSocketObserver().addOnMessageListener(new PLVSocketMessageObserver.OnMessageListener() { // from class: com.easefun.polyv.livecommon.module.modules.socket.PLVSocketLoginManager.2
            @Override // com.plv.socket.impl.PLVSocketMessageObserver.OnMessageListener
            public void onMessage(String listenEvent, String event, String message) {
                PLVCommonLog.d(PLVSocketLoginManager.TAG, "socket receiveMessage: " + message + ", event: " + event + ", listenEvent: " + listenEvent);
                if (PLVSocketLoginManager.this.getConfig().getChannelId().equals(PolyvSocketWrapper.getInstance().getLoginVO().getChannelId())) {
                    PLVSocketLoginManager.this.acceptSocketMessage(new PLVSocketMessage(listenEvent, message, event));
                }
            }
        });
    }

    @Override // com.easefun.polyv.livecommon.module.modules.socket.IPLVSocketLoginManager
    public void login() {
        PLVSocketIOClient.getInstance().setSocketUserId(getConfig().getUser().getViewerId()).setNickName(getConfig().getUser().getViewerName()).setAvatarUrl(getConfig().getUser().getViewerAvatar()).setUserType(getConfig().getUser().getViewerType()).setActor(getConfig().getUser().getActor()).setChannelId(getConfig().getChannelId()).setParam4(getConfig().getUser().getParam4()).setParam5(getConfig().getUser().getParam5());
        PolyvSocketWrapper.getInstance().login(PLVSocketLoginVO.createFromUserClient());
    }

    @Override // com.easefun.polyv.livecommon.module.modules.socket.IPLVSocketLoginManager
    public void setAllowChildRoom(boolean allow) {
        PolyvSocketWrapper.getInstance().setAllowChildRoom(allow);
    }

    @Override // com.easefun.polyv.livecommon.module.modules.socket.IPLVSocketLoginManager
    public void setOnSocketEventListener(IPLVSocketLoginManager.OnSocketEventListener listener) {
        this.onSocketEventListener = listener;
    }
}
