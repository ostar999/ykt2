package io.socket.client;

import io.socket.client.Manager;
import io.socket.client.On;
import io.socket.emitter.Emitter;
import io.socket.engineio.client.Socket;
import io.socket.parser.Packet;
import io.socket.thread.EventThread;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes8.dex */
public class Socket extends Emitter {
    public static final String EVENT_CONNECT = "connect";
    public static final String EVENT_CONNECTING = "connecting";
    public static final String EVENT_CONNECT_ERROR = "connect_error";
    public static final String EVENT_CONNECT_TIMEOUT = "connect_timeout";
    public static final String EVENT_DISCONNECT = "disconnect";
    public static final String EVENT_ERROR = "error";
    public static final String EVENT_MESSAGE = "message";
    public static final String EVENT_PING = "ping";
    public static final String EVENT_PONG = "pong";
    public static final String EVENT_RECONNECT = "reconnect";
    public static final String EVENT_RECONNECTING = "reconnecting";
    public static final String EVENT_RECONNECT_ATTEMPT = "reconnect_attempt";
    public static final String EVENT_RECONNECT_ERROR = "reconnect_error";
    public static final String EVENT_RECONNECT_FAILED = "reconnect_failed";
    private volatile boolean connected;
    String id;
    private int ids;

    /* renamed from: io, reason: collision with root package name */
    private Manager f27371io;
    private String nsp;
    private String query;
    private Queue<On.Handle> subs;
    private static final Logger logger = Logger.getLogger(Socket.class.getName());
    protected static Map<String, Integer> events = new HashMap<String, Integer>() { // from class: io.socket.client.Socket.1
        {
            put("connect", 1);
            put("connect_error", 1);
            put("connect_timeout", 1);
            put(Socket.EVENT_CONNECTING, 1);
            put(Socket.EVENT_DISCONNECT, 1);
            put("error", 1);
            put("reconnect", 1);
            put("reconnect_attempt", 1);
            put("reconnect_failed", 1);
            put("reconnect_error", 1);
            put("reconnecting", 1);
            put("ping", 1);
            put("pong", 1);
        }
    };
    private Map<Integer, Ack> acks = new HashMap();
    private final Queue<List<Object>> receiveBuffer = new LinkedList();
    private final Queue<Packet<JSONArray>> sendBuffer = new LinkedList();

    public Socket(Manager manager, String str, Manager.Options options) {
        this.f27371io = manager;
        this.nsp = str;
        if (options != null) {
            this.query = ((Socket.Options) options).query;
        }
    }

    public static /* synthetic */ int access$708(Socket socket) {
        int i2 = socket.ids;
        socket.ids = i2 + 1;
        return i2;
    }

    private Ack ack(final int i2) {
        final boolean[] zArr = {false};
        return new Ack() { // from class: io.socket.client.Socket.7
            @Override // io.socket.client.Ack
            public void call(final Object... objArr) {
                EventThread.exec(new Runnable() { // from class: io.socket.client.Socket.7.1
                    @Override // java.lang.Runnable
                    public void run() {
                        boolean[] zArr2 = zArr;
                        if (zArr2[0]) {
                            return;
                        }
                        zArr2[0] = true;
                        if (Socket.logger.isLoggable(Level.FINE)) {
                            Logger logger2 = Socket.logger;
                            Object[] objArr2 = objArr;
                            if (objArr2.length == 0) {
                                objArr2 = null;
                            }
                            logger2.fine(String.format("sending ack %s", objArr2));
                        }
                        JSONArray jSONArray = new JSONArray();
                        for (Object obj : objArr) {
                            jSONArray.put(obj);
                        }
                        Packet packet = new Packet(3, jSONArray);
                        AnonymousClass7 anonymousClass7 = AnonymousClass7.this;
                        packet.id = i2;
                        this.packet(packet);
                    }
                });
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void destroy() {
        Queue<On.Handle> queue = this.subs;
        if (queue != null) {
            Iterator<On.Handle> it = queue.iterator();
            while (it.hasNext()) {
                it.next().destroy();
            }
            this.subs = null;
        }
        this.f27371io.destroy(this);
    }

    private void emitBuffered() {
        while (true) {
            List<Object> listPoll = this.receiveBuffer.poll();
            if (listPoll == null) {
                break;
            } else {
                super.emit((String) listPoll.get(0), listPoll.toArray());
            }
        }
        this.receiveBuffer.clear();
        while (true) {
            Packet<JSONArray> packetPoll = this.sendBuffer.poll();
            if (packetPoll == null) {
                this.sendBuffer.clear();
                return;
            }
            packet(packetPoll);
        }
    }

    private void onack(Packet<JSONArray> packet) {
        Ack ackRemove = this.acks.remove(Integer.valueOf(packet.id));
        if (ackRemove != null) {
            Logger logger2 = logger;
            if (logger2.isLoggable(Level.FINE)) {
                logger2.fine(String.format("calling ack %s with %s", Integer.valueOf(packet.id), packet.data));
            }
            ackRemove.call(toArray(packet.data));
            return;
        }
        Logger logger3 = logger;
        if (logger3.isLoggable(Level.FINE)) {
            logger3.fine(String.format("bad ack %s", Integer.valueOf(packet.id)));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onclose(String str) {
        Logger logger2 = logger;
        if (logger2.isLoggable(Level.FINE)) {
            logger2.fine(String.format("close (%s)", str));
        }
        this.connected = false;
        this.id = null;
        emit(EVENT_DISCONNECT, str);
    }

    private void onconnect() {
        this.connected = true;
        emit("connect", new Object[0]);
        emitBuffered();
    }

    private void ondisconnect() {
        Logger logger2 = logger;
        if (logger2.isLoggable(Level.FINE)) {
            logger2.fine(String.format("server disconnect (%s)", this.nsp));
        }
        destroy();
        onclose("io server disconnect");
    }

    private void onevent(Packet<JSONArray> packet) {
        ArrayList arrayList = new ArrayList(Arrays.asList(toArray(packet.data)));
        Logger logger2 = logger;
        if (logger2.isLoggable(Level.FINE)) {
            logger2.fine(String.format("emitting event %s", arrayList));
        }
        if (packet.id >= 0) {
            logger2.fine("attaching ack callback to event");
            arrayList.add(ack(packet.id));
        }
        if (!this.connected) {
            this.receiveBuffer.add(arrayList);
        } else {
            if (arrayList.isEmpty()) {
                return;
            }
            super.emit(arrayList.remove(0).toString(), arrayList.toArray());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onopen() {
        logger.fine("transport is open - connecting");
        if ("/".equals(this.nsp)) {
            return;
        }
        String str = this.query;
        if (str == null || str.isEmpty()) {
            packet(new Packet(0));
            return;
        }
        Packet packet = new Packet(0);
        packet.query = this.query;
        packet(packet);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onpacket(Packet<?> packet) {
        if (this.nsp.equals(packet.nsp)) {
            switch (packet.type) {
                case 0:
                    onconnect();
                    break;
                case 1:
                    ondisconnect();
                    break;
                case 2:
                    onevent(packet);
                    break;
                case 3:
                    onack(packet);
                    break;
                case 4:
                    emit("error", packet.data);
                    break;
                case 5:
                    onevent(packet);
                    break;
                case 6:
                    onack(packet);
                    break;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void packet(Packet packet) {
        packet.nsp = this.nsp;
        this.f27371io.packet(packet);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void subEvents() {
        if (this.subs != null) {
            return;
        }
        this.subs = new LinkedList<On.Handle>(this.f27371io) { // from class: io.socket.client.Socket.2
            final /* synthetic */ Manager val$io;

            {
                this.val$io = manager;
                add(On.on(manager, "open", new Emitter.Listener() { // from class: io.socket.client.Socket.2.1
                    @Override // io.socket.emitter.Emitter.Listener
                    public void call(Object... objArr) {
                        Socket.this.onopen();
                    }
                }));
                add(On.on(manager, "packet", new Emitter.Listener() { // from class: io.socket.client.Socket.2.2
                    @Override // io.socket.emitter.Emitter.Listener
                    public void call(Object... objArr) {
                        Socket.this.onpacket((Packet) objArr[0]);
                    }
                }));
                add(On.on(manager, "close", new Emitter.Listener() { // from class: io.socket.client.Socket.2.3
                    @Override // io.socket.emitter.Emitter.Listener
                    public void call(Object... objArr) {
                        Socket.this.onclose(objArr.length > 0 ? (String) objArr[0] : null);
                    }
                }));
            }
        };
    }

    private static Object[] toArray(JSONArray jSONArray) throws JSONException {
        Object obj;
        int length = jSONArray.length();
        Object[] objArr = new Object[length];
        for (int i2 = 0; i2 < length; i2++) {
            Object obj2 = null;
            try {
                obj = jSONArray.get(i2);
            } catch (JSONException e2) {
                logger.log(Level.WARNING, "An error occured while retrieving data from JSONArray", (Throwable) e2);
                obj = null;
            }
            if (!JSONObject.NULL.equals(obj)) {
                obj2 = obj;
            }
            objArr[i2] = obj2;
        }
        return objArr;
    }

    public Socket close() {
        EventThread.exec(new Runnable() { // from class: io.socket.client.Socket.8
            @Override // java.lang.Runnable
            public void run() {
                if (Socket.this.connected) {
                    if (Socket.logger.isLoggable(Level.FINE)) {
                        Socket.logger.fine(String.format("performing disconnect (%s)", Socket.this.nsp));
                    }
                    Socket.this.packet(new Packet(1));
                }
                Socket.this.destroy();
                if (Socket.this.connected) {
                    Socket.this.onclose("io client disconnect");
                }
            }
        });
        return this;
    }

    public Socket connect() {
        return open();
    }

    public boolean connected() {
        return this.connected;
    }

    public Socket disconnect() {
        return close();
    }

    @Override // io.socket.emitter.Emitter
    public Emitter emit(final String str, final Object... objArr) {
        EventThread.exec(new Runnable() { // from class: io.socket.client.Socket.5
            @Override // java.lang.Runnable
            public void run() {
                Ack ack;
                if (Socket.events.containsKey(str)) {
                    Socket.super.emit(str, objArr);
                    return;
                }
                Object[] objArr2 = objArr;
                int length = objArr2.length - 1;
                if (objArr2.length <= 0 || !(objArr2[length] instanceof Ack)) {
                    ack = null;
                } else {
                    objArr2 = new Object[length];
                    for (int i2 = 0; i2 < length; i2++) {
                        objArr2[i2] = objArr[i2];
                    }
                    ack = (Ack) objArr[length];
                }
                Socket.this.emit(str, objArr2, ack);
            }
        });
        return this;
    }

    public String id() {
        return this.id;
    }

    public Manager io() {
        return this.f27371io;
    }

    public Socket open() {
        EventThread.exec(new Runnable() { // from class: io.socket.client.Socket.3
            @Override // java.lang.Runnable
            public void run() {
                if (Socket.this.connected) {
                    return;
                }
                Socket.this.subEvents();
                Socket.this.f27371io.open();
                if (Manager.ReadyState.OPEN == Socket.this.f27371io.readyState) {
                    Socket.this.onopen();
                }
                Socket.this.emit(Socket.EVENT_CONNECTING, new Object[0]);
            }
        });
        return this;
    }

    public Socket send(final Object... objArr) {
        EventThread.exec(new Runnable() { // from class: io.socket.client.Socket.4
            @Override // java.lang.Runnable
            public void run() {
                Socket.this.emit("message", objArr);
            }
        });
        return this;
    }

    public Emitter emit(final String str, final Object[] objArr, final Ack ack) {
        EventThread.exec(new Runnable() { // from class: io.socket.client.Socket.6
            @Override // java.lang.Runnable
            public void run() {
                JSONArray jSONArray = new JSONArray();
                jSONArray.put(str);
                Object[] objArr2 = objArr;
                if (objArr2 != null) {
                    for (Object obj : objArr2) {
                        jSONArray.put(obj);
                    }
                }
                Packet packet = new Packet(2, jSONArray);
                if (ack != null) {
                    Socket.logger.fine(String.format("emitting packet with ack id %d", Integer.valueOf(Socket.this.ids)));
                    Socket.this.acks.put(Integer.valueOf(Socket.this.ids), ack);
                    packet.id = Socket.access$708(Socket.this);
                }
                if (Socket.this.connected) {
                    Socket.this.packet(packet);
                } else {
                    Socket.this.sendBuffer.add(packet);
                }
            }
        });
        return this;
    }
}
