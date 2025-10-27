package com.plv.socket.impl;

import com.plv.socket.net.model.PLVSocketLoginVO;
import com.plv.socket.status.PLVSocketStatus;
import io.socket.client.Ack;

/* loaded from: classes5.dex */
public interface IPLVSocketProtocol {
    void addObserveSocketEvent(String... strArr);

    boolean canGetChildRoomIdStatus();

    boolean childRoomEnabled();

    void destroy();

    String getLoginRoomId();

    PLVSocketLoginVO getLoginVO();

    PLVSocketStatus getSocketStatus();

    boolean isAllowChildRoom();

    boolean isOnlineStatus();

    void login();

    void login(PLVSocketLoginVO pLVSocketLoginVO);

    void sendLoginEvent(Ack ack);

    void setAllowChildRoom(boolean z2);

    void setLoginVO(PLVSocketLoginVO pLVSocketLoginVO);
}
