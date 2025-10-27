package io.socket.engineio.client;

import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import com.yikaobang.yixue.R2;
import io.socket.emitter.Emitter;
import io.socket.engineio.client.Transport;
import io.socket.engineio.client.transports.Polling;
import io.socket.engineio.client.transports.PollingXHR;
import io.socket.engineio.parser.Packet;
import io.socket.parseqs.ParseQS;
import io.socket.thread.EventThread;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.WebSocket;
import org.json.JSONException;

/* loaded from: classes8.dex */
public class Socket extends Emitter {
    public static final String EVENT_CLOSE = "close";
    public static final String EVENT_DATA = "data";
    public static final String EVENT_DRAIN = "drain";
    public static final String EVENT_ERROR = "error";
    public static final String EVENT_FLUSH = "flush";
    public static final String EVENT_HANDSHAKE = "handshake";
    public static final String EVENT_HEARTBEAT = "heartbeat";
    public static final String EVENT_MESSAGE = "message";
    public static final String EVENT_OPEN = "open";
    public static final String EVENT_PACKET = "packet";
    public static final String EVENT_PACKET_CREATE = "packetCreate";
    public static final String EVENT_PING = "ping";
    public static final String EVENT_PONG = "pong";
    public static final String EVENT_TRANSPORT = "transport";
    public static final String EVENT_UPGRADE = "upgrade";
    public static final String EVENT_UPGRADE_ERROR = "upgradeError";
    public static final String EVENT_UPGRADING = "upgrading";
    private static final String PROBE_ERROR = "probe error";
    public static final int PROTOCOL = 3;
    private static Call.Factory defaultCallFactory;
    private static OkHttpClient defaultOkHttpClient;
    private static WebSocket.Factory defaultWebSocketFactory;
    private static final Logger logger = Logger.getLogger(Socket.class.getName());
    private static boolean priorWebsocketSuccess = false;
    private Call.Factory callFactory;
    private ScheduledExecutorService heartbeatScheduler;
    String hostname;
    private String id;
    private final Emitter.Listener onHeartbeatAsListener;
    private String path;
    private long pingInterval;
    private Future pingIntervalTimer;
    private long pingTimeout;
    private Future pingTimeoutTimer;
    private int policyPort;
    int port;
    private int prevBufferLen;
    private Map<String, String> query;
    private ReadyState readyState;
    private boolean rememberUpgrade;
    private boolean secure;
    private String timestampParam;
    private boolean timestampRequests;
    Transport transport;
    private Map<String, Transport.Options> transportOptions;
    private List<String> transports;
    private boolean upgrade;
    private List<String> upgrades;
    private boolean upgrading;
    private WebSocket.Factory webSocketFactory;
    LinkedList<Packet> writeBuffer;

    /* renamed from: io.socket.engineio.client.Socket$7, reason: invalid class name */
    public class AnonymousClass7 implements Emitter.Listener {
        final /* synthetic */ Runnable[] val$cleanup;
        final /* synthetic */ boolean[] val$failed;
        final /* synthetic */ String val$name;
        final /* synthetic */ Socket val$self;
        final /* synthetic */ Transport[] val$transport;

        public AnonymousClass7(boolean[] zArr, String str, Transport[] transportArr, Socket socket, Runnable[] runnableArr) {
            this.val$failed = zArr;
            this.val$name = str;
            this.val$transport = transportArr;
            this.val$self = socket;
            this.val$cleanup = runnableArr;
        }

        @Override // io.socket.emitter.Emitter.Listener
        public void call(Object... objArr) {
            if (this.val$failed[0]) {
                return;
            }
            if (Socket.logger.isLoggable(Level.FINE)) {
                Socket.logger.fine(String.format("probe transport '%s' opened", this.val$name));
            }
            this.val$transport[0].send(new Packet[]{new Packet("ping", "probe")});
            this.val$transport[0].once("packet", new Emitter.Listener() { // from class: io.socket.engineio.client.Socket.7.1
                @Override // io.socket.emitter.Emitter.Listener
                public void call(Object... objArr2) {
                    if (AnonymousClass7.this.val$failed[0]) {
                        return;
                    }
                    Packet packet = (Packet) objArr2[0];
                    if (!"pong".equals(packet.type) || !"probe".equals(packet.data)) {
                        if (Socket.logger.isLoggable(Level.FINE)) {
                            Socket.logger.fine(String.format("probe transport '%s' failed", AnonymousClass7.this.val$name));
                        }
                        EngineIOException engineIOException = new EngineIOException(Socket.PROBE_ERROR);
                        AnonymousClass7 anonymousClass7 = AnonymousClass7.this;
                        engineIOException.transport = anonymousClass7.val$transport[0].name;
                        anonymousClass7.val$self.emit(Socket.EVENT_UPGRADE_ERROR, engineIOException);
                        return;
                    }
                    Logger logger = Socket.logger;
                    Level level = Level.FINE;
                    if (logger.isLoggable(level)) {
                        Socket.logger.fine(String.format("probe transport '%s' pong", AnonymousClass7.this.val$name));
                    }
                    AnonymousClass7.this.val$self.upgrading = true;
                    AnonymousClass7 anonymousClass72 = AnonymousClass7.this;
                    anonymousClass72.val$self.emit(Socket.EVENT_UPGRADING, anonymousClass72.val$transport[0]);
                    Transport transport = AnonymousClass7.this.val$transport[0];
                    if (transport == null) {
                        return;
                    }
                    boolean unused = Socket.priorWebsocketSuccess = io.socket.engineio.client.transports.WebSocket.NAME.equals(transport.name);
                    if (Socket.logger.isLoggable(level)) {
                        Socket.logger.fine(String.format("pausing current transport '%s'", AnonymousClass7.this.val$self.transport.name));
                    }
                    ((Polling) AnonymousClass7.this.val$self.transport).pause(new Runnable() { // from class: io.socket.engineio.client.Socket.7.1.1
                        @Override // java.lang.Runnable
                        public void run() {
                            AnonymousClass7 anonymousClass73 = AnonymousClass7.this;
                            if (anonymousClass73.val$failed[0] || ReadyState.CLOSED == anonymousClass73.val$self.readyState) {
                                return;
                            }
                            Socket.logger.fine("changing transport and sending upgrade packet");
                            AnonymousClass7.this.val$cleanup[0].run();
                            AnonymousClass7 anonymousClass74 = AnonymousClass7.this;
                            anonymousClass74.val$self.setTransport(anonymousClass74.val$transport[0]);
                            AnonymousClass7.this.val$transport[0].send(new Packet[]{new Packet("upgrade")});
                            AnonymousClass7 anonymousClass75 = AnonymousClass7.this;
                            anonymousClass75.val$self.emit("upgrade", anonymousClass75.val$transport[0]);
                            AnonymousClass7 anonymousClass76 = AnonymousClass7.this;
                            anonymousClass76.val$transport[0] = null;
                            anonymousClass76.val$self.upgrading = false;
                            AnonymousClass7.this.val$self.flush();
                        }
                    });
                }
            });
        }
    }

    public static class Options extends Transport.Options {
        public String host;
        public String query;
        public boolean rememberUpgrade;
        public Map<String, Transport.Options> transportOptions;
        public String[] transports;
        public boolean upgrade = true;

        /* JADX INFO: Access modifiers changed from: private */
        public static Options fromURI(URI uri, Options options) {
            if (options == null) {
                options = new Options();
            }
            options.host = uri.getHost();
            options.secure = "https".equals(uri.getScheme()) || "wss".equals(uri.getScheme());
            options.port = uri.getPort();
            String rawQuery = uri.getRawQuery();
            if (rawQuery != null) {
                options.query = rawQuery;
            }
            return options;
        }
    }

    public enum ReadyState {
        OPENING,
        OPEN,
        CLOSING,
        CLOSED;

        @Override // java.lang.Enum
        public String toString() {
            return super.toString().toLowerCase();
        }
    }

    public Socket() {
        this(new Options());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Transport createTransport(String str) {
        Transport pollingXHR;
        Logger logger2 = logger;
        if (logger2.isLoggable(Level.FINE)) {
            logger2.fine(String.format("creating transport '%s'", str));
        }
        HashMap map = new HashMap(this.query);
        map.put("EIO", String.valueOf(3));
        map.put("transport", str);
        String str2 = this.id;
        if (str2 != null) {
            map.put(SocializeProtocolConstants.PROTOCOL_KEY_SID, str2);
        }
        Transport.Options options = this.transportOptions.get(str);
        Transport.Options options2 = new Transport.Options();
        options2.query = map;
        options2.socket = this;
        options2.hostname = options != null ? options.hostname : this.hostname;
        options2.port = options != null ? options.port : this.port;
        options2.secure = options != null ? options.secure : this.secure;
        options2.path = options != null ? options.path : this.path;
        options2.timestampRequests = options != null ? options.timestampRequests : this.timestampRequests;
        options2.timestampParam = options != null ? options.timestampParam : this.timestampParam;
        options2.policyPort = options != null ? options.policyPort : this.policyPort;
        options2.callFactory = options != null ? options.callFactory : this.callFactory;
        options2.webSocketFactory = options != null ? options.webSocketFactory : this.webSocketFactory;
        if (io.socket.engineio.client.transports.WebSocket.NAME.equals(str)) {
            pollingXHR = new io.socket.engineio.client.transports.WebSocket(options2);
        } else {
            if (!Polling.NAME.equals(str)) {
                throw new RuntimeException();
            }
            pollingXHR = new PollingXHR(options2);
        }
        emit("transport", pollingXHR);
        return pollingXHR;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void flush() {
        if (this.readyState == ReadyState.CLOSED || !this.transport.writable || this.upgrading || this.writeBuffer.size() == 0) {
            return;
        }
        Logger logger2 = logger;
        if (logger2.isLoggable(Level.FINE)) {
            logger2.fine(String.format("flushing %d packets in socket", Integer.valueOf(this.writeBuffer.size())));
        }
        this.prevBufferLen = this.writeBuffer.size();
        Transport transport = this.transport;
        LinkedList<Packet> linkedList = this.writeBuffer;
        transport.send((Packet[]) linkedList.toArray(new Packet[linkedList.size()]));
        emit(EVENT_FLUSH, new Object[0]);
    }

    private ScheduledExecutorService getHeartbeatScheduler() {
        ScheduledExecutorService scheduledExecutorService = this.heartbeatScheduler;
        if (scheduledExecutorService == null || scheduledExecutorService.isShutdown()) {
            this.heartbeatScheduler = Executors.newSingleThreadScheduledExecutor();
        }
        return this.heartbeatScheduler;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onClose(String str) {
        onClose(str, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onDrain() {
        for (int i2 = 0; i2 < this.prevBufferLen; i2++) {
            this.writeBuffer.poll();
        }
        this.prevBufferLen = 0;
        if (this.writeBuffer.size() == 0) {
            emit("drain", new Object[0]);
        } else {
            flush();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onError(Exception exc) {
        Logger logger2 = logger;
        if (logger2.isLoggable(Level.FINE)) {
            logger2.fine(String.format("socket error %s", exc));
        }
        priorWebsocketSuccess = false;
        emit("error", exc);
        onClose("transport error", exc);
    }

    private void onHandshake(HandshakeData handshakeData) {
        emit(EVENT_HANDSHAKE, handshakeData);
        String str = handshakeData.sid;
        this.id = str;
        this.transport.query.put(SocializeProtocolConstants.PROTOCOL_KEY_SID, str);
        this.upgrades = filterUpgrades(Arrays.asList(handshakeData.upgrades));
        this.pingInterval = handshakeData.pingInterval;
        this.pingTimeout = handshakeData.pingTimeout;
        onOpen();
        if (ReadyState.CLOSED == this.readyState) {
            return;
        }
        setPing();
        off(EVENT_HEARTBEAT, this.onHeartbeatAsListener);
        on(EVENT_HEARTBEAT, this.onHeartbeatAsListener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onHeartbeat(long j2) {
        Future future = this.pingTimeoutTimer;
        if (future != null) {
            future.cancel(false);
        }
        if (j2 <= 0) {
            j2 = this.pingInterval + this.pingTimeout;
        }
        this.pingTimeoutTimer = getHeartbeatScheduler().schedule(new Runnable() { // from class: io.socket.engineio.client.Socket.14
            @Override // java.lang.Runnable
            public void run() {
                EventThread.exec(new Runnable() { // from class: io.socket.engineio.client.Socket.14.1
                    @Override // java.lang.Runnable
                    public void run() {
                        if (this.readyState == ReadyState.CLOSED) {
                            return;
                        }
                        this.onClose("ping timeout");
                    }
                });
            }
        }, j2, TimeUnit.MILLISECONDS);
    }

    private void onOpen() {
        Logger logger2 = logger;
        logger2.fine("socket open");
        ReadyState readyState = ReadyState.OPEN;
        this.readyState = readyState;
        priorWebsocketSuccess = io.socket.engineio.client.transports.WebSocket.NAME.equals(this.transport.name);
        emit("open", new Object[0]);
        flush();
        if (this.readyState == readyState && this.upgrade && (this.transport instanceof Polling)) {
            logger2.fine("starting upgrade probes");
            Iterator<String> it = this.upgrades.iterator();
            while (it.hasNext()) {
                probe(it.next());
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public void onPacket(Packet packet) {
        ReadyState readyState = this.readyState;
        if (readyState != ReadyState.OPENING && readyState != ReadyState.OPEN && readyState != ReadyState.CLOSING) {
            Logger logger2 = logger;
            if (logger2.isLoggable(Level.FINE)) {
                logger2.fine(String.format("packet received with socket readyState '%s'", this.readyState));
                return;
            }
            return;
        }
        Logger logger3 = logger;
        if (logger3.isLoggable(Level.FINE)) {
            logger3.fine(String.format("socket received: type '%s', data '%s'", packet.type, packet.data));
        }
        emit("packet", packet);
        emit(EVENT_HEARTBEAT, new Object[0]);
        if ("open".equals(packet.type)) {
            try {
                onHandshake(new HandshakeData((String) packet.data));
                return;
            } catch (JSONException e2) {
                emit("error", new EngineIOException(e2));
                return;
            }
        }
        if ("pong".equals(packet.type)) {
            setPing();
            emit("pong", new Object[0]);
        } else if ("error".equals(packet.type)) {
            EngineIOException engineIOException = new EngineIOException("server error");
            engineIOException.code = packet.data;
            onError(engineIOException);
        } else if ("message".equals(packet.type)) {
            emit("data", packet.data);
            emit("message", packet.data);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void ping() {
        EventThread.exec(new Runnable() { // from class: io.socket.engineio.client.Socket.16
            @Override // java.lang.Runnable
            public void run() {
                Socket.this.sendPacket("ping", new Runnable() { // from class: io.socket.engineio.client.Socket.16.1
                    @Override // java.lang.Runnable
                    public void run() {
                        Socket.this.emit("ping", new Object[0]);
                    }
                });
            }
        });
    }

    private void probe(final String str) {
        Logger logger2 = logger;
        if (logger2.isLoggable(Level.FINE)) {
            logger2.fine(String.format("probing transport '%s'", str));
        }
        final Transport[] transportArr = {createTransport(str)};
        final boolean[] zArr = {false};
        priorWebsocketSuccess = false;
        final Runnable[] runnableArr = {new Runnable() { // from class: io.socket.engineio.client.Socket.13
            @Override // java.lang.Runnable
            public void run() {
                transportArr[0].off("open", anonymousClass7);
                transportArr[0].off("error", listener);
                transportArr[0].off("close", listener);
                this.off("close", listener);
                this.off(Socket.EVENT_UPGRADING, listener);
            }
        }};
        final Emitter.Listener anonymousClass7 = new AnonymousClass7(zArr, str, transportArr, this, runnableArr);
        final Emitter.Listener listener = new Emitter.Listener() { // from class: io.socket.engineio.client.Socket.8
            @Override // io.socket.emitter.Emitter.Listener
            public void call(Object... objArr) {
                boolean[] zArr2 = zArr;
                if (zArr2[0]) {
                    return;
                }
                zArr2[0] = true;
                runnableArr[0].run();
                transportArr[0].close();
                transportArr[0] = null;
            }
        };
        final Emitter.Listener listener2 = new Emitter.Listener() { // from class: io.socket.engineio.client.Socket.9
            @Override // io.socket.emitter.Emitter.Listener
            public void call(Object... objArr) {
                EngineIOException engineIOException;
                Object obj = objArr[0];
                if (obj instanceof Exception) {
                    engineIOException = new EngineIOException(Socket.PROBE_ERROR, (Exception) obj);
                } else if (obj instanceof String) {
                    engineIOException = new EngineIOException("probe error: " + ((String) obj));
                } else {
                    engineIOException = new EngineIOException(Socket.PROBE_ERROR);
                }
                engineIOException.transport = transportArr[0].name;
                listener.call(new Object[0]);
                if (Socket.logger.isLoggable(Level.FINE)) {
                    Socket.logger.fine(String.format("probe transport \"%s\" failed because of error: %s", str, obj));
                }
                this.emit(Socket.EVENT_UPGRADE_ERROR, engineIOException);
            }
        };
        final Emitter.Listener listener3 = new Emitter.Listener() { // from class: io.socket.engineio.client.Socket.10
            @Override // io.socket.emitter.Emitter.Listener
            public void call(Object... objArr) {
                listener2.call("transport closed");
            }
        };
        final Emitter.Listener listener4 = new Emitter.Listener() { // from class: io.socket.engineio.client.Socket.11
            @Override // io.socket.emitter.Emitter.Listener
            public void call(Object... objArr) {
                listener2.call("socket closed");
            }
        };
        final Emitter.Listener listener5 = new Emitter.Listener() { // from class: io.socket.engineio.client.Socket.12
            @Override // io.socket.emitter.Emitter.Listener
            public void call(Object... objArr) {
                Transport transport = (Transport) objArr[0];
                Transport transport2 = transportArr[0];
                if (transport2 == null || transport.name.equals(transport2.name)) {
                    return;
                }
                if (Socket.logger.isLoggable(Level.FINE)) {
                    Socket.logger.fine(String.format("'%s' works - aborting '%s'", transport.name, transportArr[0].name));
                }
                listener.call(new Object[0]);
            }
        };
        transportArr[0].once("open", anonymousClass7);
        transportArr[0].once("error", listener2);
        transportArr[0].once("close", listener3);
        once("close", listener4);
        once(EVENT_UPGRADING, listener5);
        transportArr[0].open();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendPacket(String str, Runnable runnable) {
        sendPacket(new Packet(str), runnable);
    }

    public static void setDefaultOkHttpCallFactory(Call.Factory factory) {
        defaultCallFactory = factory;
    }

    public static void setDefaultOkHttpWebSocketFactory(WebSocket.Factory factory) {
        defaultWebSocketFactory = factory;
    }

    private void setPing() {
        Future future = this.pingIntervalTimer;
        if (future != null) {
            future.cancel(false);
        }
        this.pingIntervalTimer = getHeartbeatScheduler().schedule(new Runnable() { // from class: io.socket.engineio.client.Socket.15
            @Override // java.lang.Runnable
            public void run() {
                EventThread.exec(new Runnable() { // from class: io.socket.engineio.client.Socket.15.1
                    @Override // java.lang.Runnable
                    public void run() {
                        if (Socket.logger.isLoggable(Level.FINE)) {
                            Socket.logger.fine(String.format("writing ping packet - expecting pong within %sms", Long.valueOf(this.pingTimeout)));
                        }
                        this.ping();
                        Socket socket = this;
                        socket.onHeartbeat(socket.pingTimeout);
                    }
                });
            }
        }, this.pingInterval, TimeUnit.MILLISECONDS);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setTransport(Transport transport) {
        Logger logger2 = logger;
        Level level = Level.FINE;
        if (logger2.isLoggable(level)) {
            logger2.fine(String.format("setting transport %s", transport.name));
        }
        if (this.transport != null) {
            if (logger2.isLoggable(level)) {
                logger2.fine(String.format("clearing existing transport %s", this.transport.name));
            }
            this.transport.off();
        }
        this.transport = transport;
        transport.on("drain", new Emitter.Listener() { // from class: io.socket.engineio.client.Socket.6
            @Override // io.socket.emitter.Emitter.Listener
            public void call(Object... objArr) {
                this.onDrain();
            }
        }).on("packet", new Emitter.Listener() { // from class: io.socket.engineio.client.Socket.5
            @Override // io.socket.emitter.Emitter.Listener
            public void call(Object... objArr) {
                this.onPacket(objArr.length > 0 ? (Packet) objArr[0] : null);
            }
        }).on("error", new Emitter.Listener() { // from class: io.socket.engineio.client.Socket.4
            @Override // io.socket.emitter.Emitter.Listener
            public void call(Object... objArr) {
                this.onError(objArr.length > 0 ? (Exception) objArr[0] : null);
            }
        }).on("close", new Emitter.Listener() { // from class: io.socket.engineio.client.Socket.3
            @Override // io.socket.emitter.Emitter.Listener
            public void call(Object... objArr) {
                this.onClose("transport close");
            }
        });
    }

    public Socket close() {
        EventThread.exec(new Runnable() { // from class: io.socket.engineio.client.Socket.20
            @Override // java.lang.Runnable
            public void run() {
                if (Socket.this.readyState == ReadyState.OPENING || Socket.this.readyState == ReadyState.OPEN) {
                    Socket.this.readyState = ReadyState.CLOSING;
                    final Socket socket = Socket.this;
                    final Runnable runnable = new Runnable() { // from class: io.socket.engineio.client.Socket.20.1
                        @Override // java.lang.Runnable
                        public void run() {
                            socket.onClose("forced close");
                            Socket.logger.fine("socket closing - telling transport to close");
                            socket.transport.close();
                        }
                    };
                    final Emitter.Listener[] listenerArr = new Emitter.Listener[1];
                    listenerArr[0] = new Emitter.Listener() { // from class: io.socket.engineio.client.Socket.20.2
                        @Override // io.socket.emitter.Emitter.Listener
                        public void call(Object... objArr) {
                            socket.off("upgrade", listenerArr[0]);
                            socket.off(Socket.EVENT_UPGRADE_ERROR, listenerArr[0]);
                            runnable.run();
                        }
                    };
                    final Runnable runnable2 = new Runnable() { // from class: io.socket.engineio.client.Socket.20.3
                        @Override // java.lang.Runnable
                        public void run() {
                            socket.once("upgrade", listenerArr[0]);
                            socket.once(Socket.EVENT_UPGRADE_ERROR, listenerArr[0]);
                        }
                    };
                    if (Socket.this.writeBuffer.size() > 0) {
                        Socket.this.once("drain", new Emitter.Listener() { // from class: io.socket.engineio.client.Socket.20.4
                            @Override // io.socket.emitter.Emitter.Listener
                            public void call(Object... objArr) {
                                if (Socket.this.upgrading) {
                                    runnable2.run();
                                } else {
                                    runnable.run();
                                }
                            }
                        });
                    } else if (Socket.this.upgrading) {
                        runnable2.run();
                    } else {
                        runnable.run();
                    }
                }
            }
        });
        return this;
    }

    public List<String> filterUpgrades(List<String> list) {
        ArrayList arrayList = new ArrayList();
        for (String str : list) {
            if (this.transports.contains(str)) {
                arrayList.add(str);
            }
        }
        return arrayList;
    }

    public String id() {
        return this.id;
    }

    public Socket open() {
        EventThread.exec(new Runnable() { // from class: io.socket.engineio.client.Socket.2
            /* JADX WARN: Removed duplicated region for block: B:9:0x001d  */
            @Override // java.lang.Runnable
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public void run() {
                /*
                    r3 = this;
                    io.socket.engineio.client.Socket r0 = io.socket.engineio.client.Socket.this
                    boolean r0 = io.socket.engineio.client.Socket.access$200(r0)
                    if (r0 == 0) goto L1d
                    boolean r0 = io.socket.engineio.client.Socket.access$300()
                    if (r0 == 0) goto L1d
                    io.socket.engineio.client.Socket r0 = io.socket.engineio.client.Socket.this
                    java.util.List r0 = io.socket.engineio.client.Socket.access$400(r0)
                    java.lang.String r1 = "websocket"
                    boolean r0 = r0.contains(r1)
                    if (r0 == 0) goto L1d
                    goto L42
                L1d:
                    io.socket.engineio.client.Socket r0 = io.socket.engineio.client.Socket.this
                    java.util.List r0 = io.socket.engineio.client.Socket.access$400(r0)
                    int r0 = r0.size()
                    if (r0 != 0) goto L34
                    io.socket.engineio.client.Socket r0 = io.socket.engineio.client.Socket.this
                    io.socket.engineio.client.Socket$2$1 r1 = new io.socket.engineio.client.Socket$2$1
                    r1.<init>()
                    io.socket.thread.EventThread.nextTick(r1)
                    return
                L34:
                    io.socket.engineio.client.Socket r0 = io.socket.engineio.client.Socket.this
                    java.util.List r0 = io.socket.engineio.client.Socket.access$400(r0)
                    r1 = 0
                    java.lang.Object r0 = r0.get(r1)
                    r1 = r0
                    java.lang.String r1 = (java.lang.String) r1
                L42:
                    io.socket.engineio.client.Socket r0 = io.socket.engineio.client.Socket.this
                    io.socket.engineio.client.Socket$ReadyState r2 = io.socket.engineio.client.Socket.ReadyState.OPENING
                    io.socket.engineio.client.Socket.access$502(r0, r2)
                    io.socket.engineio.client.Socket r0 = io.socket.engineio.client.Socket.this
                    io.socket.engineio.client.Transport r0 = io.socket.engineio.client.Socket.access$600(r0, r1)
                    io.socket.engineio.client.Socket r1 = io.socket.engineio.client.Socket.this
                    io.socket.engineio.client.Socket.access$700(r1, r0)
                    r0.open()
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: io.socket.engineio.client.Socket.AnonymousClass2.run():void");
            }
        });
        return this;
    }

    public void send(String str) {
        send(str, (Runnable) null);
    }

    public void write(String str) {
        write(str, (Runnable) null);
    }

    public Socket(String str) throws URISyntaxException {
        this(str, (Options) null);
    }

    private void onClose(String str, Exception exc) {
        ReadyState readyState = ReadyState.OPENING;
        ReadyState readyState2 = this.readyState;
        if (readyState == readyState2 || ReadyState.OPEN == readyState2 || ReadyState.CLOSING == readyState2) {
            Logger logger2 = logger;
            if (logger2.isLoggable(Level.FINE)) {
                logger2.fine(String.format("socket close with reason: %s", str));
            }
            Future future = this.pingIntervalTimer;
            if (future != null) {
                future.cancel(false);
            }
            Future future2 = this.pingTimeoutTimer;
            if (future2 != null) {
                future2.cancel(false);
            }
            ScheduledExecutorService scheduledExecutorService = this.heartbeatScheduler;
            if (scheduledExecutorService != null) {
                scheduledExecutorService.shutdown();
            }
            this.transport.off("close");
            this.transport.close();
            this.transport.off();
            this.readyState = ReadyState.CLOSED;
            this.id = null;
            emit("close", str, exc);
            this.writeBuffer.clear();
            this.prevBufferLen = 0;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendPacket(String str, String str2, Runnable runnable) {
        sendPacket(new Packet(str, str2), runnable);
    }

    public void send(byte[] bArr) {
        send(bArr, (Runnable) null);
    }

    public void write(String str, Runnable runnable) {
        send(str, runnable);
    }

    public Socket(URI uri) {
        this(uri, (Options) null);
    }

    public void send(final String str, final Runnable runnable) {
        EventThread.exec(new Runnable() { // from class: io.socket.engineio.client.Socket.17
            @Override // java.lang.Runnable
            public void run() {
                Socket.this.sendPacket("message", str, runnable);
            }
        });
    }

    public void write(byte[] bArr) {
        write(bArr, (Runnable) null);
    }

    public Socket(String str, Options options) throws URISyntaxException {
        this(str == null ? null : new URI(str), options);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendPacket(String str, byte[] bArr, Runnable runnable) {
        sendPacket(new Packet(str, bArr), runnable);
    }

    public void send(final byte[] bArr, final Runnable runnable) {
        EventThread.exec(new Runnable() { // from class: io.socket.engineio.client.Socket.18
            @Override // java.lang.Runnable
            public void run() {
                Socket.this.sendPacket("message", bArr, runnable);
            }
        });
    }

    public void write(byte[] bArr, Runnable runnable) {
        send(bArr, runnable);
    }

    public Socket(URI uri, Options options) {
        this(uri != null ? Options.fromURI(uri, options) : options);
    }

    public Socket(Options options) {
        this.writeBuffer = new LinkedList<>();
        this.onHeartbeatAsListener = new Emitter.Listener() { // from class: io.socket.engineio.client.Socket.1
            @Override // io.socket.emitter.Emitter.Listener
            public void call(Object... objArr) {
                Socket.this.onHeartbeat(objArr.length > 0 ? ((Long) objArr[0]).longValue() : 0L);
            }
        };
        String strSubstring = options.host;
        if (strSubstring != null) {
            if (strSubstring.split(":").length > 2) {
                int iIndexOf = strSubstring.indexOf(91);
                strSubstring = iIndexOf != -1 ? strSubstring.substring(iIndexOf + 1) : strSubstring;
                int iLastIndexOf = strSubstring.lastIndexOf(93);
                if (iLastIndexOf != -1) {
                    strSubstring = strSubstring.substring(0, iLastIndexOf);
                }
            }
            options.hostname = strSubstring;
        }
        boolean z2 = options.secure;
        this.secure = z2;
        if (options.port == -1) {
            options.port = z2 ? R2.attr.banner_indicator_selected_color : 80;
        }
        String str = options.hostname;
        this.hostname = str == null ? "localhost" : str;
        this.port = options.port;
        String str2 = options.query;
        this.query = str2 != null ? ParseQS.decode(str2) : new HashMap<>();
        this.upgrade = options.upgrade;
        StringBuilder sb = new StringBuilder();
        String str3 = options.path;
        sb.append((str3 == null ? "/engine.io" : str3).replaceAll("/$", ""));
        sb.append("/");
        this.path = sb.toString();
        String str4 = options.timestampParam;
        this.timestampParam = str4 == null ? "t" : str4;
        this.timestampRequests = options.timestampRequests;
        String[] strArr = options.transports;
        this.transports = new ArrayList(Arrays.asList(strArr == null ? new String[]{Polling.NAME, io.socket.engineio.client.transports.WebSocket.NAME} : strArr));
        Map<String, Transport.Options> map = options.transportOptions;
        this.transportOptions = map == null ? new HashMap<>() : map;
        int i2 = options.policyPort;
        this.policyPort = i2 == 0 ? R2.attr.buttonBarNegativeButtonStyle : i2;
        this.rememberUpgrade = options.rememberUpgrade;
        Call.Factory factory = options.callFactory;
        factory = factory == null ? defaultCallFactory : factory;
        this.callFactory = factory;
        WebSocket.Factory factory2 = options.webSocketFactory;
        this.webSocketFactory = factory2 == null ? defaultWebSocketFactory : factory2;
        if (factory == null) {
            if (defaultOkHttpClient == null) {
                defaultOkHttpClient = new OkHttpClient();
            }
            this.callFactory = defaultOkHttpClient;
        }
        if (this.webSocketFactory == null) {
            if (defaultOkHttpClient == null) {
                defaultOkHttpClient = new OkHttpClient();
            }
            this.webSocketFactory = defaultOkHttpClient;
        }
    }

    private void sendPacket(Packet packet, final Runnable runnable) {
        ReadyState readyState = ReadyState.CLOSING;
        ReadyState readyState2 = this.readyState;
        if (readyState == readyState2 || ReadyState.CLOSED == readyState2) {
            return;
        }
        emit(EVENT_PACKET_CREATE, packet);
        this.writeBuffer.offer(packet);
        if (runnable != null) {
            once(EVENT_FLUSH, new Emitter.Listener() { // from class: io.socket.engineio.client.Socket.19
                @Override // io.socket.emitter.Emitter.Listener
                public void call(Object... objArr) {
                    runnable.run();
                }
            });
        }
        flush();
    }
}
