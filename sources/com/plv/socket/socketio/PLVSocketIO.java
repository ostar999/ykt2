package com.plv.socket.socketio;

import com.plv.foundationsdk.PLVUAClient;
import com.plv.foundationsdk.log.PLVCommonLog;
import com.plv.foundationsdk.utils.PLVGsonUtil;
import com.plv.socket.socketio.PLVSocketIOObservable;
import io.socket.client.Ack;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import io.socket.engineio.client.Transport;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

/* loaded from: classes5.dex */
public abstract class PLVSocketIO<T extends PLVSocketIOObservable> implements IPLVSocketIOProtocol<T> {
    public static final String TAG = "PLVSocketIO";
    private Socket socket;
    private Emitter.Listener onConnecting = new Emitter.Listener() { // from class: com.plv.socket.socketio.PLVSocketIO.2
        /* JADX WARN: Type inference failed for: r0v3, types: [com.plv.socket.socketio.PLVSocketIOObservable] */
        @Override // io.socket.emitter.Emitter.Listener
        public void call(Object... objArr) {
            PLVCommonLog.d(PLVSocketIO.TAG, "onConnecting: " + Arrays.toString(objArr));
            PLVSocketIO.this.getSocketObserver().dispatchOnMessage(Socket.EVENT_CONNECTING, objArr);
        }
    };
    private Emitter.Listener onError = new Emitter.Listener() { // from class: com.plv.socket.socketio.PLVSocketIO.3
        /* JADX WARN: Type inference failed for: r0v3, types: [com.plv.socket.socketio.PLVSocketIOObservable] */
        @Override // io.socket.emitter.Emitter.Listener
        public void call(Object... objArr) {
            PLVCommonLog.d(PLVSocketIO.TAG, "onError: " + Arrays.toString(objArr));
            PLVSocketIO.this.getSocketObserver().dispatchOnMessage("error", objArr);
        }
    };
    private Emitter.Listener onReconnectAttempt = new Emitter.Listener() { // from class: com.plv.socket.socketio.PLVSocketIO.4
        /* JADX WARN: Type inference failed for: r0v3, types: [com.plv.socket.socketio.PLVSocketIOObservable] */
        @Override // io.socket.emitter.Emitter.Listener
        public void call(Object... objArr) {
            PLVCommonLog.d(PLVSocketIO.TAG, "onReconnectAttempt: " + Arrays.toString(objArr));
            PLVSocketIO.this.getSocketObserver().dispatchOnMessage("reconnect_attempt", objArr);
        }
    };
    private Emitter.Listener onReconnectFailed = new Emitter.Listener() { // from class: com.plv.socket.socketio.PLVSocketIO.5
        /* JADX WARN: Type inference failed for: r0v3, types: [com.plv.socket.socketio.PLVSocketIOObservable] */
        @Override // io.socket.emitter.Emitter.Listener
        public void call(Object... objArr) {
            PLVCommonLog.d(PLVSocketIO.TAG, "onReconnectFailed: " + Arrays.toString(objArr));
            PLVSocketIO.this.getSocketObserver().dispatchOnMessage("reconnect_failed", objArr);
        }
    };
    private Emitter.Listener onReconnectError = new Emitter.Listener() { // from class: com.plv.socket.socketio.PLVSocketIO.6
        /* JADX WARN: Type inference failed for: r0v3, types: [com.plv.socket.socketio.PLVSocketIOObservable] */
        @Override // io.socket.emitter.Emitter.Listener
        public void call(Object... objArr) {
            PLVCommonLog.d(PLVSocketIO.TAG, "onReconnectError: " + Arrays.toString(objArr));
            PLVSocketIO.this.getSocketObserver().dispatchOnMessage("reconnect_error", objArr);
        }
    };
    private Emitter.Listener onReconnect = new Emitter.Listener() { // from class: com.plv.socket.socketio.PLVSocketIO.7
        /* JADX WARN: Type inference failed for: r0v3, types: [com.plv.socket.socketio.PLVSocketIOObservable] */
        @Override // io.socket.emitter.Emitter.Listener
        public void call(Object... objArr) {
            PLVCommonLog.d(PLVSocketIO.TAG, "onReconnect: " + Arrays.toString(objArr));
            PLVSocketIO.this.getSocketObserver().dispatchOnMessage("reconnect", objArr);
        }
    };
    private Emitter.Listener onReconnecting = new Emitter.Listener() { // from class: com.plv.socket.socketio.PLVSocketIO.8
        /* JADX WARN: Type inference failed for: r0v3, types: [com.plv.socket.socketio.PLVSocketIOObservable] */
        @Override // io.socket.emitter.Emitter.Listener
        public void call(Object... objArr) {
            PLVCommonLog.d(PLVSocketIO.TAG, "onReconnecting: " + Arrays.toString(objArr));
            PLVSocketIO.this.getSocketObserver().dispatchOnMessage("reconnecting", objArr);
        }
    };
    private Emitter.Listener onConnect = new Emitter.Listener() { // from class: com.plv.socket.socketio.PLVSocketIO.9
        /* JADX WARN: Type inference failed for: r0v3, types: [com.plv.socket.socketio.PLVSocketIOObservable] */
        @Override // io.socket.emitter.Emitter.Listener
        public void call(Object... objArr) {
            PLVCommonLog.d(PLVSocketIO.TAG, "onConnect: " + Arrays.toString(objArr));
            PLVSocketIO.this.getSocketObserver().dispatchOnMessage("connect", objArr);
            PLVSocketIO.this.loginVerify(objArr);
        }
    };
    private Emitter.Listener onDisconnect = new Emitter.Listener() { // from class: com.plv.socket.socketio.PLVSocketIO.10
        /* JADX WARN: Type inference failed for: r0v3, types: [com.plv.socket.socketio.PLVSocketIOObservable] */
        @Override // io.socket.emitter.Emitter.Listener
        public void call(Object... objArr) {
            PLVCommonLog.d(PLVSocketIO.TAG, "onDisconnect: " + Arrays.toString(objArr));
            PLVSocketIO.this.getSocketObserver().dispatchOnMessage(Socket.EVENT_DISCONNECT, objArr);
        }
    };
    private Emitter.Listener onConnectError = new Emitter.Listener() { // from class: com.plv.socket.socketio.PLVSocketIO.11
        /* JADX WARN: Type inference failed for: r0v3, types: [com.plv.socket.socketio.PLVSocketIOObservable] */
        @Override // io.socket.emitter.Emitter.Listener
        public void call(Object... objArr) {
            PLVCommonLog.d(PLVSocketIO.TAG, "onConnectError: " + Arrays.toString(objArr));
            PLVSocketIO.this.getSocketObserver().dispatchOnMessage("connect_error", objArr);
        }
    };
    private Emitter.Listener onConnectTimeout = new Emitter.Listener() { // from class: com.plv.socket.socketio.PLVSocketIO.12
        /* JADX WARN: Type inference failed for: r0v3, types: [com.plv.socket.socketio.PLVSocketIOObservable] */
        @Override // io.socket.emitter.Emitter.Listener
        public void call(Object... objArr) {
            PLVCommonLog.d(PLVSocketIO.TAG, "onConnectTimeout: " + Arrays.toString(objArr));
            PLVSocketIO.this.getSocketObserver().dispatchOnMessage("connect_timeout", objArr);
        }
    };
    private Emitter.Listener onMessage = new Emitter.Listener() { // from class: com.plv.socket.socketio.PLVSocketIO.13
        /* JADX WARN: Type inference failed for: r0v3, types: [com.plv.socket.socketio.PLVSocketIOObservable] */
        @Override // io.socket.emitter.Emitter.Listener
        public void call(Object... objArr) {
            PLVCommonLog.d(PLVSocketIO.TAG, "onMessage: " + Arrays.toString(objArr));
            PLVSocketIO.this.getSocketObserver().dispatchOnMessage("message", objArr);
            PLVSocketIO.this.acceptMessage(objArr);
        }
    };

    private Object[] toObjectArgs(Object... objArr) {
        int length = objArr.length;
        Object[] objArr2 = new Object[length];
        for (int i2 = 0; i2 < length; i2++) {
            Object obj = objArr[i2];
            if ((obj instanceof String) || (obj instanceof Ack)) {
                objArr2[i2] = obj;
            } else {
                objArr2[i2] = PLVGsonUtil.toJson(obj);
            }
        }
        return objArr2;
    }

    public abstract void acceptMessage(Object... objArr);

    public abstract LinkedHashMap<String, Emitter.Listener> addListenEvent();

    @Override // com.plv.socket.socketio.IPLVSocketIOProtocol
    public void connect(String str, IO.Options options) throws URISyntaxException {
        Socket socket = IO.socket(str, options);
        this.socket = socket;
        try {
            socket.io().on("transport", new Emitter.Listener() { // from class: com.plv.socket.socketio.PLVSocketIO.1
                @Override // io.socket.emitter.Emitter.Listener
                public void call(Object... objArr) {
                    if (objArr == null || objArr.length <= 0) {
                        return;
                    }
                    Object obj = objArr[0];
                    if (obj instanceof Transport) {
                        ((Transport) obj).on("requestHeaders", new Emitter.Listener() { // from class: com.plv.socket.socketio.PLVSocketIO.1.1
                            @Override // io.socket.emitter.Emitter.Listener
                            public void call(Object... objArr2) {
                                try {
                                    ((Map) objArr2[0]).put("User-Agent", Arrays.asList(PLVUAClient.getUserAgent()));
                                } catch (Exception e2) {
                                    PLVCommonLog.e(PLVSocketIO.TAG, "header设置异常" + e2.getMessage());
                                }
                            }
                        });
                    }
                }
            });
        } catch (Exception e2) {
            PLVCommonLog.e(TAG, "header设置异常" + e2.getMessage());
        }
        LinkedHashMap<String, Emitter.Listener> linkedHashMapAddListenEvent = addListenEvent();
        if (linkedHashMapAddListenEvent != null) {
            for (Map.Entry<String, Emitter.Listener> entry : linkedHashMapAddListenEvent.entrySet()) {
                this.socket.on(entry.getKey(), entry.getValue());
            }
        }
        this.socket.on("connect", this.onConnect);
        this.socket.on(Socket.EVENT_DISCONNECT, this.onDisconnect);
        this.socket.on(Socket.EVENT_CONNECTING, this.onConnecting);
        this.socket.on("connect_error", this.onConnectError);
        this.socket.on("connect_timeout", this.onConnectTimeout);
        this.socket.on("reconnect", this.onReconnect);
        this.socket.on("reconnecting", this.onReconnecting);
        this.socket.on("reconnect_error", this.onReconnectError);
        this.socket.on("reconnect_failed", this.onReconnectFailed);
        this.socket.on("reconnect_attempt", this.onReconnectAttempt);
        this.socket.on("error", this.onError);
        this.socket.on("message", this.onMessage);
        this.socket.connect();
    }

    @Override // com.plv.socket.socketio.IPLVSocketIOProtocol
    public void disconnect() {
        Socket socket = this.socket;
        if (socket != null) {
            socket.off();
            this.socket.disconnect();
            this.socket = null;
        }
    }

    @Override // com.plv.socket.socketio.IPLVSocketIOProtocol
    public void emit(String str, Object... objArr) {
        if (this.socket == null || objArr == null) {
            return;
        }
        if (objArr.length > 1 && objArr[objArr.length - 1] == null) {
            objArr = Arrays.copyOfRange(objArr, 0, objArr.length - 1);
        }
        PLVCommonLog.d(TAG, "send: " + str + ": " + Arrays.toString(toObjectArgs(objArr)));
        this.socket.emit(str, toObjectArgs(objArr));
    }

    @Override // com.plv.socket.socketio.IPLVSocketIOProtocol
    public String getSocketId() {
        Socket socket = this.socket;
        if (socket != null) {
            return socket.id();
        }
        return null;
    }

    public abstract void loginVerify(Object... objArr);

    @Override // com.plv.socket.socketio.IPLVSocketIOProtocol
    public void observeOn(String str, Emitter.Listener listener) {
        if (this.socket != null) {
            PLVCommonLog.d(TAG, "on: " + str);
            this.socket.on(str, listener);
        }
    }

    @Override // com.plv.socket.socketio.IPLVSocketIOProtocol
    public void emit(String str, Object[] objArr, Ack ack) {
        if (this.socket == null || objArr == null) {
            return;
        }
        PLVCommonLog.d(TAG, "send: " + str + ": " + Arrays.toString(toObjectArgs(objArr)) + ", ack: " + ack);
        this.socket.emit(str, toObjectArgs(objArr), ack);
    }
}
