package com.plv.socket.impl;

import android.text.TextUtils;
import androidx.annotation.Nullable;
import com.heytap.mcssdk.constant.b;
import com.plv.foundationsdk.log.PLVCommonLog;
import com.plv.foundationsdk.log.elog.logcode.socket.PLVErrorCodeSocketApi;
import com.plv.foundationsdk.log.elog.logcode.socket.PLVErrorCodeSocketLogin;
import com.plv.foundationsdk.log.elog.logcode.socket.PLVErrorCodeSocketParam;
import com.plv.foundationsdk.manager.PLVChatDomainManager;
import com.plv.foundationsdk.utils.PLVCheckUtils;
import com.plv.socket.event.PLVEventConstant;
import com.plv.socket.event.PLVEventHelper;
import com.plv.socket.log.PLVELogSender;
import com.plv.socket.log.PLVSocketELog;
import com.plv.socket.net.PLVSocketApiHelper;
import com.plv.socket.net.model.PLVHiClassChatTokenVO;
import com.plv.socket.net.model.PLVNewChatTokenVO;
import com.plv.socket.net.model.PLVSocketLoginVO;
import com.plv.socket.socketio.PLVSocketIO;
import com.plv.socket.socketio.PLVSocketIOClient;
import com.plv.socket.socketio.PLVSocketIOObservable;
import com.plv.socket.status.PLVSocketStatus;
import io.reactivex.functions.Consumer;
import io.socket.client.Ack;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import io.socket.engineio.client.Socket;
import io.socket.engineio.client.transports.WebSocket;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import okhttp3.OkHttpClient;

/* loaded from: classes5.dex */
public final class PLVSocketManager extends PLVSocketIO<PLVSocketMessageObserver> implements IPLVSocketProtocol {
    private static final String TAG = "PLVSocketManager";
    private static volatile PLVSocketManager socketIOManager;
    private boolean allowChildRoom;
    private PLVSocketApiHelper apiHelper = new PLVSocketApiHelper();
    private boolean canGetChildRoomIdStatus;
    private boolean childRoomEnabled;
    private String loginRoomId;
    private PLVSocketLoginVO loginVO;
    private OkHttpClient okHttpClient;
    private PLVSocketIOObservable.OnSocketEventListener onSocketEventListener;
    private PLVSocketMessageObserver socketMessageObserver;
    private int tokenExpiredCount;

    private PLVSocketManager() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void acceptLoginAck(Object... objArr) throws NumberFormatException {
        Exception exc;
        int iSend;
        String strValueOf = String.valueOf(objArr[0]);
        int i2 = Integer.parseInt(strValueOf.substring(0, 1));
        int i3 = Integer.parseInt(strValueOf.substring(1, 2));
        int i4 = Integer.parseInt(strValueOf.substring(2, 3));
        int i5 = Integer.parseInt(strValueOf.substring(3, 4));
        if (2 == i2) {
            this.tokenExpiredCount = 0;
            PLVELogSender.send(PLVSocketELog.class, 3 == getSocketStatus().getStatus() ? "reconnectSuccess" : "loginSuccess", "ackMsg: " + strValueOf);
            dispatchOnStatus(PLVSocketStatus.create(3 == getSocketStatus().getStatus() ? 4 : 2));
            return;
        }
        if (i3 == 0) {
            exc = new Exception("房间不合法" + strValueOf);
            iSend = PLVELogSender.send(PLVErrorCodeSocketLogin.class, 4, exc);
        } else if (i4 == 0) {
            exc = new Exception("头像/昵称不合法" + strValueOf);
            iSend = PLVELogSender.send(PLVErrorCodeSocketLogin.class, 5, exc);
        } else if (i5 == 0) {
            exc = new Exception("已被踢出房间" + strValueOf);
            iSend = PLVELogSender.send(PLVErrorCodeSocketLogin.class, 6, exc);
        } else {
            exc = new Exception("参数非法" + strValueOf);
            iSend = PLVELogSender.send(PLVErrorCodeSocketLogin.class, 3, exc);
        }
        dispatchOnStatus(PLVSocketStatus.fail(iSend, exc));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void acceptOnSocketMessage(String str, Object... objArr) {
        PLVCommonLog.d(TAG, str + ": " + Arrays.toString(objArr));
        if (!"reconnecting".equals(str)) {
            Socket.EVENT_DISCONNECT.equals(str);
            return;
        }
        if (getSocketStatus().getStatus() != 3) {
            PLVELogSender.send(PLVSocketELog.class, "reconnecting", "args: " + Arrays.toString(objArr));
        }
        dispatchOnStatus(PLVSocketStatus.create(3));
    }

    private void addOnSocketEventListener() {
        this.onSocketEventListener = new PLVSocketIOObservable.OnSocketEventListener() { // from class: com.plv.socket.impl.PLVSocketManager.6
            @Override // com.plv.socket.socketio.PLVSocketIOObservable.OnSocketEventListener
            public void onMessage(String str, Object... objArr) {
                PLVSocketManager.this.acceptOnSocketMessage(str, objArr);
            }
        };
        getSocketObserver().addOnSocketEventListener(this.onSocketEventListener);
    }

    private boolean checkAppIdAppSecretCallback() {
        String strCheckEmptyParams;
        if (!TextUtils.isEmpty(PLVSocketIOClient.getInstance().getToken()) || (strCheckEmptyParams = PLVCheckUtils.checkEmptyParams(PLVSocketIOClient.getInstance().getAccountAppId(), "appId", PLVSocketIOClient.getInstance().getAccountAppSecret(), b.A)) == null) {
            return true;
        }
        int i2 = strCheckEmptyParams.startsWith("appId") ? 8 : 9;
        Exception exc = new Exception(strCheckEmptyParams);
        dispatchOnStatus(PLVSocketStatus.fail(PLVELogSender.send(PLVErrorCodeSocketParam.class, i2, exc), exc));
        return false;
    }

    private boolean checkNullCallback(Object obj, String str) {
        String strCheckNullParam = PLVCheckUtils.checkNullParam(obj, str);
        if (strCheckNullParam == null) {
            return true;
        }
        int i2 = str.startsWith("loginVO") ? 1 : -1;
        Exception exc = new Exception(strCheckNullParam);
        dispatchOnStatus(PLVSocketStatus.fail(PLVELogSender.send(PLVErrorCodeSocketParam.class, i2, exc), exc));
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void connect(String str) throws URISyntaxException {
        getSocketObserver().removeOnSocketEventListener(this.onSocketEventListener);
        addOnSocketEventListener();
        IO.Options options = new IO.Options();
        ((Socket.Options) options).query = "token=" + str + "&version=2.0";
        options.transports = new String[]{WebSocket.NAME};
        try {
            if (this.okHttpClient == null) {
                this.okHttpClient = new OkHttpClient.Builder().minWebSocketMessageToCompress(200L).build();
            }
        } catch (Throwable unused) {
            PLVCommonLog.e(TAG, "聊天室开启压缩失败，chat room open compress failed.");
            this.okHttpClient = new OkHttpClient.Builder().build();
            PLVCommonLog.e(TAG, "聊天室 Client reset " + this.okHttpClient);
        }
        OkHttpClient okHttpClient = this.okHttpClient;
        options.callFactory = okHttpClient;
        options.webSocketFactory = okHttpClient;
        PLVELogSender.send(PLVSocketELog.class, "connect", "token: " + str + "**url: " + PLVChatDomainManager.getInstance().getChatDomain().getChatDomain());
        connect(PLVChatDomainManager.getInstance().getChatDomain().getChatDomain(), options);
    }

    private void dispatchOnMessage(String str, String str2, String str3) {
        getSocketObserver().dispatchOnMessage(str, str2, str3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispatchOnStatus(PLVSocketStatus pLVSocketStatus) {
        getSocketObserver().dispatchOnStatus(pLVSocketStatus);
    }

    public static PLVSocketManager getInstance() {
        if (socketIOManager == null) {
            synchronized (PLVSocketManager.class) {
                if (socketIOManager == null) {
                    socketIOManager = new PLVSocketManager();
                }
            }
        }
        return socketIOManager;
    }

    private void processMessage(String str, String str2) {
        if (!"TOKEN_EXPIRED".equals(str)) {
            if ("RELOGIN".equals(str)) {
                PLVELogSender.send(PLVSocketELog.class, "relogin", "message: " + str2);
                return;
            }
            return;
        }
        if (this.tokenExpiredCount >= 3) {
            Exception exc = new Exception("token鉴权失败");
            dispatchOnStatus(PLVSocketStatus.fail(PLVELogSender.send(PLVErrorCodeSocketApi.class, 3, exc), exc));
        } else {
            disconnect(false);
            requestApiConnect();
            this.tokenExpiredCount++;
        }
    }

    private void requestApiConnect() {
        if (TextUtils.isEmpty(PLVSocketIOClient.getInstance().getToken())) {
            this.apiHelper.getChatToken(this.loginVO.getUserId(), this.loginVO.getChannelId(), this.loginVO.getUserType(), new Consumer<PLVNewChatTokenVO>() { // from class: com.plv.socket.impl.PLVSocketManager.4
                @Override // io.reactivex.functions.Consumer
                public void accept(PLVNewChatTokenVO pLVNewChatTokenVO) throws Exception {
                    PLVSocketManager.this.childRoomEnabled = "Y".equals(pLVNewChatTokenVO.getData().getChildRoomEnabled());
                    PLVSocketManager pLVSocketManager = PLVSocketManager.this;
                    pLVSocketManager.loginRoomId = (pLVSocketManager.allowChildRoom && PLVSocketManager.this.childRoomEnabled && !TextUtils.isEmpty(pLVNewChatTokenVO.getData().getRoomId())) ? pLVNewChatTokenVO.getData().getRoomId() : PLVSocketManager.this.loginVO.getChannelId();
                    PLVSocketManager.this.canGetChildRoomIdStatus = true;
                    PLVSocketManager.this.connect(pLVNewChatTokenVO.getData().getToken());
                }
            }, new Consumer<Throwable>() { // from class: com.plv.socket.impl.PLVSocketManager.5
                @Override // io.reactivex.functions.Consumer
                public void accept(Throwable th) throws Exception {
                    PLVCommonLog.exception(th);
                    PLVSocketManager.this.dispatchOnStatus(PLVSocketStatus.fail(PLVELogSender.send(PLVErrorCodeSocketApi.class, 3, th), th));
                }
            });
        } else {
            this.apiHelper.getHiClassChatToken(new Consumer<PLVHiClassChatTokenVO>() { // from class: com.plv.socket.impl.PLVSocketManager.2
                @Override // io.reactivex.functions.Consumer
                public void accept(PLVHiClassChatTokenVO pLVHiClassChatTokenVO) throws Exception {
                    PLVSocketManager.this.connect(pLVHiClassChatTokenVO.getData().getChatToken());
                }
            }, new Consumer<Throwable>() { // from class: com.plv.socket.impl.PLVSocketManager.3
                @Override // io.reactivex.functions.Consumer
                public void accept(Throwable th) throws Exception {
                    PLVCommonLog.exception(th);
                    PLVSocketManager.this.dispatchOnStatus(PLVSocketStatus.fail(PLVELogSender.send(PLVErrorCodeSocketApi.class, 3, th), th));
                }
            });
        }
    }

    @Override // com.plv.socket.socketio.PLVSocketIO
    public void acceptMessage(Object... objArr) {
        try {
            String str = (String) objArr[0];
            String event = PLVEventHelper.getEvent(str);
            dispatchOnMessage("message", event, str);
            processMessage(event, str);
        } catch (Exception e2) {
            PLVCommonLog.exception(e2);
        }
    }

    @Override // com.plv.socket.socketio.PLVSocketIO
    public LinkedHashMap<String, Emitter.Listener> addListenEvent() {
        PLVSocketListenMap pLVSocketListenMap = new PLVSocketListenMap();
        pLVSocketListenMap.put("emotion", PLVEventConstant.Seminar.SEMINAR_EVENT, PLVEventConstant.LinkMic.JOIN_REQUEST_EVENT, PLVEventConstant.LinkMic.JOIN_RESPONSE_EVENT, "joinSuccess", PLVEventConstant.LinkMic.JOIN_LEAVE_EVENT, PLVEventConstant.LinkMic.JOIN_ANSWER_EVENT, PLVEventConstant.Ppt.ON_ASSISTANT_CONTROL, PLVEventConstant.Class.SE_SWITCH_MESSAGE, "customMessage", PLVEventConstant.Chatroom.SE_FOCUS, PLVEventConstant.Interact.TIMER_EVENT, PLVEventConstant.Interact.NEWS_PUSH, "changeMicSite");
        return pLVSocketListenMap.getMap();
    }

    @Override // com.plv.socket.impl.IPLVSocketProtocol
    public void addObserveSocketEvent(String... strArr) {
        PLVSocketListenMap pLVSocketListenMap = new PLVSocketListenMap();
        for (String str : strArr) {
            pLVSocketListenMap.put(str);
        }
        if (pLVSocketListenMap.getMap() != null) {
            for (Map.Entry<String, Emitter.Listener> entry : pLVSocketListenMap.getMap().entrySet()) {
                observeOn(entry.getKey(), entry.getValue());
            }
        }
    }

    @Override // com.plv.socket.impl.IPLVSocketProtocol
    public boolean canGetChildRoomIdStatus() {
        return this.canGetChildRoomIdStatus;
    }

    @Override // com.plv.socket.impl.IPLVSocketProtocol
    public boolean childRoomEnabled() {
        return this.childRoomEnabled;
    }

    @Override // com.plv.socket.impl.IPLVSocketProtocol
    public void destroy() {
        disconnect();
        getSocketObserver().clean();
        this.loginVO = null;
        this.loginRoomId = "";
    }

    @Override // com.plv.socket.socketio.PLVSocketIO, com.plv.socket.socketio.IPLVSocketIOProtocol
    public void disconnect() {
        disconnect(true);
    }

    @Override // com.plv.socket.impl.IPLVSocketProtocol
    public String getLoginRoomId() {
        return this.loginRoomId;
    }

    @Override // com.plv.socket.impl.IPLVSocketProtocol
    public PLVSocketLoginVO getLoginVO() {
        PLVSocketLoginVO pLVSocketLoginVO = this.loginVO;
        return pLVSocketLoginVO != null ? pLVSocketLoginVO : PLVSocketLoginVO.createFromUserClient();
    }

    @Override // com.plv.socket.impl.IPLVSocketProtocol
    public PLVSocketStatus getSocketStatus() {
        return getSocketObserver().getLastDispatchStatus();
    }

    @Override // com.plv.socket.impl.IPLVSocketProtocol
    public boolean isAllowChildRoom() {
        return this.allowChildRoom;
    }

    @Override // com.plv.socket.impl.IPLVSocketProtocol
    public boolean isOnlineStatus() {
        return getSocketStatus().getStatus() == 2 || getSocketStatus().getStatus() == 4;
    }

    @Override // com.plv.socket.impl.IPLVSocketProtocol
    public void login(PLVSocketLoginVO pLVSocketLoginVO) {
        disconnect(false);
        this.loginVO = pLVSocketLoginVO;
        PLVELogSender.send(PLVSocketELog.class, "logining", "loginVO: " + pLVSocketLoginVO.toString());
        dispatchOnStatus(PLVSocketStatus.create(1));
        if (checkNullCallback(pLVSocketLoginVO, "loginVO") && pLVSocketLoginVO.checkParamsCallback(getSocketObserver()) && checkAppIdAppSecretCallback()) {
            this.loginRoomId = pLVSocketLoginVO.getChannelId();
            this.canGetChildRoomIdStatus = false;
            requestApiConnect();
        }
    }

    @Override // com.plv.socket.socketio.PLVSocketIO
    public void loginVerify(@Nullable Object... objArr) {
        loginVerify(null, objArr);
    }

    @Override // com.plv.socket.impl.IPLVSocketProtocol
    public void sendLoginEvent(Ack ack) {
        loginVerify(ack, null);
    }

    @Override // com.plv.socket.impl.IPLVSocketProtocol
    public void setAllowChildRoom(boolean z2) {
        this.allowChildRoom = z2;
    }

    @Override // com.plv.socket.impl.IPLVSocketProtocol
    public void setLoginVO(PLVSocketLoginVO pLVSocketLoginVO) {
        this.loginVO = pLVSocketLoginVO;
    }

    private void disconnect(boolean z2) {
        super.disconnect();
        if (z2) {
            dispatchOnStatus(PLVSocketStatus.create(0));
        }
        this.apiHelper.dispose();
    }

    @Override // com.plv.socket.socketio.IPLVSocketIOProtocol
    public PLVSocketMessageObserver getSocketObserver() {
        if (this.socketMessageObserver == null) {
            this.socketMessageObserver = new PLVSocketMessageObserver();
        }
        return this.socketMessageObserver;
    }

    public void loginVerify(final Ack ack, @Nullable Object... objArr) {
        try {
            emit("message", this.loginVO.createLoginInfo(this.loginRoomId), new Ack() { // from class: com.plv.socket.impl.PLVSocketManager.1
                @Override // io.socket.client.Ack
                public void call(Object... objArr2) {
                    PLVSocketManager.this.getSocketObserver().dispatchOnMessage(PLVEventConstant.LOGIN_ACK_EVENT, objArr2);
                    try {
                        PLVSocketManager.this.acceptLoginAck(objArr2);
                    } catch (Exception e2) {
                        PLVCommonLog.exception(e2);
                        PLVSocketManager.this.dispatchOnStatus(PLVSocketStatus.fail(PLVELogSender.send(PLVErrorCodeSocketLogin.class, 2, e2), e2));
                    }
                    Ack ack2 = ack;
                    if (ack2 != null) {
                        ack2.call(objArr2);
                    }
                }
            });
        } catch (Exception e2) {
            PLVCommonLog.exception(e2);
            dispatchOnStatus(PLVSocketStatus.fail(PLVELogSender.send(PLVErrorCodeSocketParam.class, 7, e2), e2));
            disconnect(false);
        }
    }

    @Override // com.plv.socket.impl.IPLVSocketProtocol
    public void login() {
        PLVSocketLoginVO pLVSocketLoginVOCreateFromUserClient = this.loginVO;
        if (pLVSocketLoginVOCreateFromUserClient == null) {
            pLVSocketLoginVOCreateFromUserClient = PLVSocketLoginVO.createFromUserClient();
        }
        login(pLVSocketLoginVOCreateFromUserClient);
    }
}
