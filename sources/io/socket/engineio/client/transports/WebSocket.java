package io.socket.engineio.client.transports;

import cn.hutool.core.text.StrPool;
import io.socket.engineio.client.Transport;
import io.socket.engineio.parser.Packet;
import io.socket.engineio.parser.Parser;
import io.socket.parseqs.ParseQS;
import io.socket.thread.EventThread;
import io.socket.utf8.UTF8Exception;
import io.socket.yeast.Yeast;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

/* loaded from: classes8.dex */
public class WebSocket extends Transport {
    public static final String NAME = "websocket";
    private static final Logger logger = Logger.getLogger(PollingXHR.class.getName());
    private okhttp3.WebSocket ws;

    public WebSocket(Transport.Options options) {
        super(options);
        this.name = NAME;
    }

    @Override // io.socket.engineio.client.Transport
    public void doClose() {
        okhttp3.WebSocket webSocket = this.ws;
        if (webSocket != null) {
            webSocket.close(1000, "");
            this.ws = null;
        }
    }

    @Override // io.socket.engineio.client.Transport
    public void doOpen() {
        TreeMap treeMap = new TreeMap(String.CASE_INSENSITIVE_ORDER);
        emit("requestHeaders", treeMap);
        WebSocket.Factory okHttpClient = this.webSocketFactory;
        if (okHttpClient == null) {
            okHttpClient = new OkHttpClient();
        }
        Request.Builder builderUrl = new Request.Builder().url(uri());
        for (Map.Entry entry : treeMap.entrySet()) {
            Iterator it = ((List) entry.getValue()).iterator();
            while (it.hasNext()) {
                builderUrl.addHeader((String) entry.getKey(), (String) it.next());
            }
        }
        this.ws = okHttpClient.newWebSocket(builderUrl.build(), new WebSocketListener() { // from class: io.socket.engineio.client.transports.WebSocket.1
            @Override // okhttp3.WebSocketListener
            public void onClosed(okhttp3.WebSocket webSocket, int i2, String str) {
                EventThread.exec(new Runnable() { // from class: io.socket.engineio.client.transports.WebSocket.1.4
                    @Override // java.lang.Runnable
                    public void run() {
                        this.onClose();
                    }
                });
            }

            @Override // okhttp3.WebSocketListener
            public void onFailure(okhttp3.WebSocket webSocket, final Throwable th, Response response) {
                if (th instanceof Exception) {
                    EventThread.exec(new Runnable() { // from class: io.socket.engineio.client.transports.WebSocket.1.5
                        @Override // java.lang.Runnable
                        public void run() {
                            this.onError("websocket error", (Exception) th);
                        }
                    });
                }
            }

            @Override // okhttp3.WebSocketListener
            public void onMessage(okhttp3.WebSocket webSocket, final String str) {
                if (str == null) {
                    return;
                }
                EventThread.exec(new Runnable() { // from class: io.socket.engineio.client.transports.WebSocket.1.2
                    @Override // java.lang.Runnable
                    public void run() {
                        this.onData(str);
                    }
                });
            }

            @Override // okhttp3.WebSocketListener
            public void onOpen(okhttp3.WebSocket webSocket, Response response) {
                final Map<String, List<String>> multimap = response.headers().toMultimap();
                EventThread.exec(new Runnable() { // from class: io.socket.engineio.client.transports.WebSocket.1.1
                    @Override // java.lang.Runnable
                    public void run() {
                        this.emit("responseHeaders", multimap);
                        this.onOpen();
                    }
                });
            }

            @Override // okhttp3.WebSocketListener
            public void onMessage(okhttp3.WebSocket webSocket, final ByteString byteString) {
                if (byteString == null) {
                    return;
                }
                EventThread.exec(new Runnable() { // from class: io.socket.engineio.client.transports.WebSocket.1.3
                    @Override // java.lang.Runnable
                    public void run() {
                        this.onData(byteString.toByteArray());
                    }
                });
            }
        });
    }

    public String uri() {
        String str;
        String str2;
        Map map = this.query;
        if (map == null) {
            map = new HashMap();
        }
        String str3 = this.secure ? "wss" : "ws";
        if (this.port <= 0 || ((!"wss".equals(str3) || this.port == 443) && (!"ws".equals(str3) || this.port == 80))) {
            str = "";
        } else {
            str = ":" + this.port;
        }
        if (this.timestampRequests) {
            map.put(this.timestampParam, Yeast.yeast());
        }
        String strEncode = ParseQS.encode(map);
        if (strEncode.length() > 0) {
            strEncode = "?" + strEncode;
        }
        boolean zContains = this.hostname.contains(":");
        StringBuilder sb = new StringBuilder();
        sb.append(str3);
        sb.append("://");
        if (zContains) {
            str2 = StrPool.BRACKET_START + this.hostname + StrPool.BRACKET_END;
        } else {
            str2 = this.hostname;
        }
        sb.append(str2);
        sb.append(str);
        sb.append(this.path);
        sb.append(strEncode);
        return sb.toString();
    }

    @Override // io.socket.engineio.client.Transport
    public void write(Packet[] packetArr) throws UTF8Exception {
        this.writable = false;
        final Runnable runnable = new Runnable() { // from class: io.socket.engineio.client.transports.WebSocket.2
            @Override // java.lang.Runnable
            public void run() {
                EventThread.nextTick(new Runnable() { // from class: io.socket.engineio.client.transports.WebSocket.2.1
                    @Override // java.lang.Runnable
                    public void run() {
                        WebSocket webSocket = this;
                        webSocket.writable = true;
                        webSocket.emit("drain", new Object[0]);
                    }
                });
            }
        };
        final int[] iArr = {packetArr.length};
        for (Packet packet : packetArr) {
            Transport.ReadyState readyState = this.readyState;
            if (readyState != Transport.ReadyState.OPENING && readyState != Transport.ReadyState.OPEN) {
                return;
            }
            Parser.encodePacket(packet, new Parser.EncodeCallback() { // from class: io.socket.engineio.client.transports.WebSocket.3
                @Override // io.socket.engineio.parser.Parser.EncodeCallback
                public void call(Object obj) {
                    try {
                        if (obj instanceof String) {
                            this.ws.send((String) obj);
                        } else if (obj instanceof byte[]) {
                            this.ws.send(ByteString.of((byte[]) obj));
                        }
                    } catch (IllegalStateException unused) {
                        WebSocket.logger.fine("websocket closed before we could write");
                    }
                    int[] iArr2 = iArr;
                    int i2 = iArr2[0] - 1;
                    iArr2[0] = i2;
                    if (i2 == 0) {
                        runnable.run();
                    }
                }
            });
        }
    }
}
