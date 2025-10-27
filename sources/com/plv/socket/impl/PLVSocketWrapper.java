package com.plv.socket.impl;

import com.plv.socket.net.model.PLVSocketLoginVO;
import com.plv.socket.socketio.IPLVSocketIOProtocol;
import com.plv.socket.status.PLVSocketStatus;
import io.socket.client.Ack;
import io.socket.client.IO;
import io.socket.emitter.Emitter;
import java.net.URISyntaxException;

/* loaded from: classes5.dex */
public class PLVSocketWrapper implements IPLVSocketProtocol, IPLVSocketIOProtocol<PLVSocketMessageObserver> {
    private PLVSocketManager socketManager = PLVSocketManager.getInstance();

    @Override // com.plv.socket.impl.IPLVSocketProtocol
    public void addObserveSocketEvent(String... strArr) {
        this.socketManager.addObserveSocketEvent(strArr);
    }

    @Override // com.plv.socket.impl.IPLVSocketProtocol
    public boolean canGetChildRoomIdStatus() {
        return this.socketManager.canGetChildRoomIdStatus();
    }

    @Override // com.plv.socket.impl.IPLVSocketProtocol
    public boolean childRoomEnabled() {
        return this.socketManager.childRoomEnabled();
    }

    @Override // com.plv.socket.socketio.IPLVSocketIOProtocol
    public void connect(String str, IO.Options options) throws URISyntaxException {
        this.socketManager.connect(str, options);
    }

    @Override // com.plv.socket.impl.IPLVSocketProtocol
    public void destroy() {
        this.socketManager.destroy();
    }

    @Override // com.plv.socket.socketio.IPLVSocketIOProtocol
    public void disconnect() {
        this.socketManager.disconnect();
    }

    @Override // com.plv.socket.socketio.IPLVSocketIOProtocol
    public void emit(String str, Object... objArr) {
        this.socketManager.emit(str, objArr);
    }

    @Override // com.plv.socket.impl.IPLVSocketProtocol
    public String getLoginRoomId() {
        return this.socketManager.getLoginRoomId();
    }

    @Override // com.plv.socket.impl.IPLVSocketProtocol
    public PLVSocketLoginVO getLoginVO() {
        return this.socketManager.getLoginVO();
    }

    @Override // com.plv.socket.socketio.IPLVSocketIOProtocol
    public String getSocketId() {
        return this.socketManager.getSocketId();
    }

    @Override // com.plv.socket.impl.IPLVSocketProtocol
    public PLVSocketStatus getSocketStatus() {
        return this.socketManager.getSocketStatus();
    }

    @Override // com.plv.socket.impl.IPLVSocketProtocol
    public boolean isAllowChildRoom() {
        return this.socketManager.isAllowChildRoom();
    }

    @Override // com.plv.socket.impl.IPLVSocketProtocol
    public boolean isOnlineStatus() {
        return this.socketManager.isOnlineStatus();
    }

    @Override // com.plv.socket.impl.IPLVSocketProtocol
    public void login(PLVSocketLoginVO pLVSocketLoginVO) {
        this.socketManager.login(pLVSocketLoginVO);
    }

    @Override // com.plv.socket.socketio.IPLVSocketIOProtocol
    public void observeOn(String str, Emitter.Listener listener) {
        this.socketManager.observeOn(str, listener);
    }

    @Override // com.plv.socket.impl.IPLVSocketProtocol
    public void sendLoginEvent(Ack ack) {
        this.socketManager.sendLoginEvent(ack);
    }

    @Override // com.plv.socket.impl.IPLVSocketProtocol
    public void setAllowChildRoom(boolean z2) {
        this.socketManager.setAllowChildRoom(z2);
    }

    @Override // com.plv.socket.impl.IPLVSocketProtocol
    public void setLoginVO(PLVSocketLoginVO pLVSocketLoginVO) {
        this.socketManager.setLoginVO(pLVSocketLoginVO);
    }

    @Override // com.plv.socket.socketio.IPLVSocketIOProtocol
    public void emit(String str, Object[] objArr, Ack ack) {
        this.socketManager.emit(str, objArr, ack);
    }

    @Override // com.plv.socket.socketio.IPLVSocketIOProtocol
    public PLVSocketMessageObserver getSocketObserver() {
        return this.socketManager.getSocketObserver();
    }

    @Override // com.plv.socket.impl.IPLVSocketProtocol
    public void login() {
        this.socketManager.login();
    }
}
