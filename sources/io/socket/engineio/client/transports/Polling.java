package io.socket.engineio.client.transports;

import cn.hutool.core.text.StrPool;
import io.socket.emitter.Emitter;
import io.socket.engineio.client.Transport;
import io.socket.engineio.parser.Packet;
import io.socket.engineio.parser.Parser;
import io.socket.parseqs.ParseQS;
import io.socket.thread.EventThread;
import io.socket.utf8.UTF8Exception;
import io.socket.yeast.Yeast;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/* loaded from: classes8.dex */
public abstract class Polling extends Transport {
    public static final String EVENT_POLL = "poll";
    public static final String EVENT_POLL_COMPLETE = "pollComplete";
    public static final String NAME = "polling";
    private static final Logger logger = Logger.getLogger(Polling.class.getName());
    private boolean polling;

    public Polling(Transport.Options options) {
        super(options);
        this.name = NAME;
    }

    private void _onData(Object obj) throws NumberFormatException {
        Logger logger2 = logger;
        Level level = Level.FINE;
        if (logger2.isLoggable(level)) {
            logger2.fine(String.format("polling got data %s", obj));
        }
        Parser.DecodePayloadCallback decodePayloadCallback = new Parser.DecodePayloadCallback() { // from class: io.socket.engineio.client.transports.Polling.2
            @Override // io.socket.engineio.parser.Parser.DecodePayloadCallback
            public boolean call(Packet packet, int i2, int i3) {
                if (((Transport) this).readyState == Transport.ReadyState.OPENING) {
                    this.onOpen();
                }
                if ("close".equals(packet.type)) {
                    this.onClose();
                    return false;
                }
                this.onPacket(packet);
                return true;
            }
        };
        if (obj instanceof String) {
            Parser.decodePayload((String) obj, (Parser.DecodePayloadCallback<String>) decodePayloadCallback);
        } else if (obj instanceof byte[]) {
            Parser.decodePayload((byte[]) obj, decodePayloadCallback);
        }
        if (this.readyState != Transport.ReadyState.CLOSED) {
            this.polling = false;
            emit(EVENT_POLL_COMPLETE, new Object[0]);
            if (this.readyState == Transport.ReadyState.OPEN) {
                poll();
            } else if (logger2.isLoggable(level)) {
                logger2.fine(String.format("ignoring poll - transport state '%s'", this.readyState));
            }
        }
    }

    private void poll() {
        logger.fine(NAME);
        this.polling = true;
        doPoll();
        emit(EVENT_POLL, new Object[0]);
    }

    @Override // io.socket.engineio.client.Transport
    public void doClose() {
        Emitter.Listener listener = new Emitter.Listener() { // from class: io.socket.engineio.client.transports.Polling.3
            @Override // io.socket.emitter.Emitter.Listener
            public void call(Object... objArr) {
                Polling.logger.fine("writing close packet");
                try {
                    this.write(new Packet[]{new Packet("close")});
                } catch (UTF8Exception e2) {
                    throw new RuntimeException(e2);
                }
            }
        };
        if (this.readyState == Transport.ReadyState.OPEN) {
            logger.fine("transport open - closing");
            listener.call(new Object[0]);
        } else {
            logger.fine("transport not open - deferring close");
            once("open", listener);
        }
    }

    @Override // io.socket.engineio.client.Transport
    public void doOpen() {
        poll();
    }

    public abstract void doPoll();

    public abstract void doWrite(String str, Runnable runnable);

    public abstract void doWrite(byte[] bArr, Runnable runnable);

    @Override // io.socket.engineio.client.Transport
    public void onData(String str) throws NumberFormatException {
        _onData(str);
    }

    public void pause(final Runnable runnable) {
        EventThread.exec(new Runnable() { // from class: io.socket.engineio.client.transports.Polling.1
            @Override // java.lang.Runnable
            public void run() {
                final Polling polling = Polling.this;
                ((Transport) polling).readyState = Transport.ReadyState.PAUSED;
                final Runnable runnable2 = new Runnable() { // from class: io.socket.engineio.client.transports.Polling.1.1
                    @Override // java.lang.Runnable
                    public void run() {
                        Polling.logger.fine("paused");
                        ((Transport) polling).readyState = Transport.ReadyState.PAUSED;
                        runnable.run();
                    }
                };
                if (!Polling.this.polling && Polling.this.writable) {
                    runnable2.run();
                    return;
                }
                final int[] iArr = {0};
                if (Polling.this.polling) {
                    Polling.logger.fine("we are currently polling - waiting to pause");
                    iArr[0] = iArr[0] + 1;
                    Polling.this.once(Polling.EVENT_POLL_COMPLETE, new Emitter.Listener() { // from class: io.socket.engineio.client.transports.Polling.1.2
                        @Override // io.socket.emitter.Emitter.Listener
                        public void call(Object... objArr) {
                            Polling.logger.fine("pre-pause polling complete");
                            int[] iArr2 = iArr;
                            int i2 = iArr2[0] - 1;
                            iArr2[0] = i2;
                            if (i2 == 0) {
                                runnable2.run();
                            }
                        }
                    });
                }
                if (Polling.this.writable) {
                    return;
                }
                Polling.logger.fine("we are currently writing - waiting to pause");
                iArr[0] = iArr[0] + 1;
                Polling.this.once("drain", new Emitter.Listener() { // from class: io.socket.engineio.client.transports.Polling.1.3
                    @Override // io.socket.emitter.Emitter.Listener
                    public void call(Object... objArr) {
                        Polling.logger.fine("pre-pause writing complete");
                        int[] iArr2 = iArr;
                        int i2 = iArr2[0] - 1;
                        iArr2[0] = i2;
                        if (i2 == 0) {
                            runnable2.run();
                        }
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
        String str3 = this.secure ? "https" : "http";
        if (this.timestampRequests) {
            map.put(this.timestampParam, Yeast.yeast());
        }
        String strEncode = ParseQS.encode(map);
        if (this.port <= 0 || ((!"https".equals(str3) || this.port == 443) && (!"http".equals(str3) || this.port == 80))) {
            str = "";
        } else {
            str = ":" + this.port;
        }
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
        final Runnable runnable = new Runnable() { // from class: io.socket.engineio.client.transports.Polling.4
            @Override // java.lang.Runnable
            public void run() {
                Polling polling = this;
                polling.writable = true;
                polling.emit("drain", new Object[0]);
            }
        };
        Parser.encodePayload(packetArr, new Parser.EncodeCallback() { // from class: io.socket.engineio.client.transports.Polling.5
            @Override // io.socket.engineio.parser.Parser.EncodeCallback
            public void call(Object obj) {
                if (obj instanceof byte[]) {
                    this.doWrite((byte[]) obj, runnable);
                    return;
                }
                if (obj instanceof String) {
                    this.doWrite((String) obj, runnable);
                    return;
                }
                Polling.logger.warning("Unexpected data: " + obj);
            }
        });
    }

    @Override // io.socket.engineio.client.Transport
    public void onData(byte[] bArr) throws NumberFormatException {
        _onData(bArr);
    }
}
