package com.plv.socket.socketio;

import com.plv.socket.socketio.PLVSocketIOObservable;
import io.socket.client.Ack;
import io.socket.client.IO;
import io.socket.emitter.Emitter;
import java.net.URISyntaxException;

/* loaded from: classes5.dex */
public interface IPLVSocketIOProtocol<T extends PLVSocketIOObservable> {
    void connect(String str, IO.Options options) throws URISyntaxException;

    void disconnect();

    void emit(String str, Object... objArr);

    void emit(String str, Object[] objArr, Ack ack);

    String getSocketId();

    T getSocketObserver();

    void observeOn(String str, Emitter.Listener listener);
}
