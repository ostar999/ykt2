package h;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import com.google.android.exoplayer2.C;
import io.crossbar.autobahn.websocket.WebSocketConnection;
import io.crossbar.autobahn.websocket.exceptions.WebSocketException;
import io.crossbar.autobahn.websocket.interfaces.IWebSocketConnectionHandler;
import io.crossbar.autobahn.websocket.types.ConnectionResponse;
import io.crossbar.autobahn.websocket.types.WebSocketOptions;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes8.dex */
public class f implements Handler.Callback {

    /* renamed from: y, reason: collision with root package name */
    public static final String f27039y = "VirtualWSClient";

    /* renamed from: a, reason: collision with root package name */
    public final d f27040a;

    /* renamed from: b, reason: collision with root package name */
    public final Handler f27041b;

    /* renamed from: c, reason: collision with root package name */
    public WebSocketConnection f27042c;

    /* renamed from: d, reason: collision with root package name */
    public WebSocketOptions f27043d;

    /* renamed from: e, reason: collision with root package name */
    public String f27044e;

    /* renamed from: f, reason: collision with root package name */
    public String f27045f;

    /* renamed from: g, reason: collision with root package name */
    public e f27046g;

    /* renamed from: h, reason: collision with root package name */
    public C0453f f27047h;

    /* renamed from: i, reason: collision with root package name */
    public int f27048i;

    /* renamed from: j, reason: collision with root package name */
    public int f27049j;

    /* renamed from: k, reason: collision with root package name */
    public int f27050k;

    /* renamed from: l, reason: collision with root package name */
    public int f27051l;

    /* renamed from: m, reason: collision with root package name */
    public int f27052m;

    /* renamed from: n, reason: collision with root package name */
    public int f27053n;

    /* renamed from: o, reason: collision with root package name */
    public boolean f27054o;

    /* renamed from: p, reason: collision with root package name */
    public boolean f27055p;

    /* renamed from: q, reason: collision with root package name */
    public boolean f27056q;

    /* renamed from: r, reason: collision with root package name */
    public boolean f27057r;

    /* renamed from: s, reason: collision with root package name */
    public long f27058s;

    /* renamed from: t, reason: collision with root package name */
    public long f27059t;

    /* renamed from: u, reason: collision with root package name */
    public h.d f27060u;

    /* renamed from: v, reason: collision with root package name */
    public boolean f27061v;

    /* renamed from: w, reason: collision with root package name */
    public boolean f27062w;

    /* renamed from: x, reason: collision with root package name */
    public final Map<String, h> f27063x = new HashMap();

    public class a implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ String f27064a;

        public a(String str) {
            this.f27064a = str;
        }

        @Override // java.lang.Runnable
        public void run() {
            f.this.e("POST", this.f27064a);
        }
    }

    public static /* synthetic */ class b {

        /* renamed from: a, reason: collision with root package name */
        public static final /* synthetic */ int[] f27066a;

        static {
            int[] iArr = new int[h.d.values().length];
            f27066a = iArr;
            try {
                iArr[h.d.RECON_RS_SWITCH_SIGNAL.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f27066a[h.d.RECON_RS_REQUEST_SIGNAL.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f27066a[h.d.RECON_RS_LOGOFF_RECON.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public interface c {
        f a(d dVar);
    }

    public interface d {
        void a();

        void a(int i2);

        void a(h.d dVar);

        void a(String str);

        void b();

        void b(int i2);

        void b(String str);
    }

    public enum e {
        NEW,
        CONNECTING,
        CONNECTED,
        RECONNECTING
    }

    /* renamed from: h.f$f, reason: collision with other inner class name */
    public class C0453f implements IWebSocketConnectionHandler {
        public C0453f() {
        }

        @Override // io.crossbar.autobahn.websocket.interfaces.IWebSocketConnectionHandler
        public void a(ConnectionResponse connectionResponse) {
            c.h.a(f.f27039y, "WebSocket connection onConnect to: " + f.this.f27044e + " response " + connectionResponse.toString());
        }

        @Override // io.crossbar.autobahn.websocket.interfaces.IWebSocketConnectionHandler
        public void a(byte[] bArr) {
        }

        @Override // io.crossbar.autobahn.websocket.interfaces.IWebSocketConnectionHandler
        public void a(byte[] bArr, boolean z2) {
        }

        @Override // io.crossbar.autobahn.websocket.interfaces.IWebSocketConnectionHandler
        public void b() {
            c.h.a(f.f27039y, "recv msg ws onPong ");
        }

        @Override // io.crossbar.autobahn.websocket.interfaces.IWebSocketConnectionHandler
        public void c() {
            c.h.a(f.f27039y, "recv msg ws ping ");
        }

        @Override // io.crossbar.autobahn.websocket.interfaces.IWebSocketConnectionHandler
        public void b(byte[] bArr) {
            c.h.a(f.f27039y, "recv msg ws onPong payload ");
        }

        @Override // io.crossbar.autobahn.websocket.interfaces.IWebSocketConnectionHandler
        public void a() {
            c.h.a(f.f27039y, "WebSocket connection opened to: " + f.this.f27044e + " signal state " + f.this.f27046g);
            if (f.this.f27041b.getLooper().getThread().isAlive()) {
                f.this.f27041b.sendEmptyMessage(1005);
            }
        }

        @Override // io.crossbar.autobahn.websocket.interfaces.IWebSocketConnectionHandler
        public void a(int i2, String str) {
            c.h.a(f.f27039y, " onclose code " + i2 + " reason " + str);
            if (f.this.f27041b.getLooper().getThread().isAlive()) {
                if (i2 == 1) {
                    f.this.f27041b.sendEmptyMessage(1008);
                } else {
                    f.this.f27041b.sendEmptyMessage(1013);
                }
            }
        }

        @Override // io.crossbar.autobahn.websocket.interfaces.IWebSocketConnectionHandler
        public void a(String str) {
            c.h.a(f.f27039y, " recv msg : " + str);
            Message messageObtainMessage = f.this.f27041b.obtainMessage();
            messageObtainMessage.what = 1010;
            Bundle bundle = new Bundle();
            bundle.putString("msgdata", str);
            messageObtainMessage.setData(bundle);
            if (f.this.f27041b.getLooper().getThread().isAlive()) {
                f.this.f27041b.sendMessage(messageObtainMessage);
            }
        }

        @Override // io.crossbar.autobahn.websocket.interfaces.IWebSocketConnectionHandler
        public void a(WebSocketConnection webSocketConnection) {
            c.h.a(f.f27039y, " setConnection " + webSocketConnection);
        }
    }

    public enum g {
        OVER_DELAY
    }

    public f(d dVar) {
        HandlerThread handlerThread = new HandlerThread(f27039y);
        handlerThread.start();
        this.f27041b = new Handler(handlerThread.getLooper(), this);
        this.f27040a = dVar;
        this.f27046g = e.NEW;
        this.f27044e = "";
        this.f27048i = d.b.j() == -1 ? Integer.MAX_VALUE : d.b.j();
        this.f27049j = 0;
        this.f27050k = 5000;
        this.f27051l = 15000;
        this.f27052m = h.g.f27080f;
        this.f27053n = 5000;
        this.f27054o = false;
        this.f27055p = false;
        this.f27056q = false;
        this.f27057r = false;
        this.f27058s = -1L;
        this.f27059t = -1L;
        WebSocketOptions webSocketOptions = new WebSocketOptions();
        this.f27043d = webSocketOptions;
        webSocketOptions.c(true);
        this.f27043d.f(this.f27050k);
        this.f27043d.a(this.f27051l);
        this.f27043d.b(this.f27052m);
        this.f27060u = h.d.RECON_RS_NULL;
        this.f27042c = null;
        this.f27047h = null;
        this.f27061v = false;
        this.f27062w = true;
    }

    public void A() {
        if (this.f27055p) {
            this.f27055p = false;
            this.f27041b.removeMessages(1001);
        }
    }

    public void B() {
        if (this.f27056q) {
            this.f27056q = false;
            this.f27041b.removeMessages(1007);
        }
    }

    public void C() {
        this.f27041b.removeMessages(1010);
    }

    public void D() {
        this.f27041b.removeMessages(1009);
    }

    public void b(String str) throws JSONException {
        try {
            JSONObject jSONObject = new JSONObject(str);
            this.f27058s = System.currentTimeMillis();
            if (jSONObject.getString("method").equals("pong")) {
                return;
            }
            if (jSONObject.has("rpc_id")) {
                this.f27063x.remove(jSONObject.getString("rpc_id"));
            }
            if (this.f27040a != null) {
                c.h.a(f27039y, "callback msg to app ");
                this.f27040a.a(str);
            }
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    public void c(int i2) {
        this.f27051l = i2;
    }

    public void d(int i2) {
        this.f27052m = i2;
    }

    public void e(int i2) {
        this.f27048i = i2;
    }

    public void f(int i2) {
        this.f27050k = i2;
    }

    public void g() {
        Message messageObtainMessage = this.f27041b.obtainMessage();
        messageObtainMessage.what = 1015;
        messageObtainMessage.setData(new Bundle());
        this.f27041b.sendMessage(messageObtainMessage);
    }

    public boolean h() {
        return this.f27046g == e.CONNECTED;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // android.os.Handler.Callback
    public boolean handleMessage(Message message) throws JSONException, InterruptedException {
        if (message != null) {
            switch (message.what) {
                case 1001:
                    p();
                    break;
                case 1002:
                    j();
                    break;
                case 1003:
                    c(message.getData().getString("wsurl"), message.getData().getString("ip"));
                    break;
                case 1004:
                    a("");
                    break;
                case 1005:
                    k();
                    break;
                case 1006:
                    l();
                    break;
                case 1007:
                    o();
                    break;
                case 1008:
                    n();
                    break;
                case 1009:
                    h hVar = (h) message.getData().getSerializable("msgdata");
                    if (hVar != null) {
                        a(hVar);
                        break;
                    }
                    break;
                case 1010:
                    String string = message.getData().getString("msgdata");
                    if (string.length() > 0) {
                        b(string);
                        break;
                    }
                    break;
                case 1012:
                    q();
                    break;
                case 1013:
                    m();
                    break;
                case 1015:
                    i();
                    break;
                case 1016:
                    a(message);
                    break;
                case 1017:
                    this.f27062w = message.getData().getBoolean("flag", true);
                    break;
            }
        }
        return true;
    }

    public void i() {
        if (h()) {
            r();
        }
    }

    public void j() throws InterruptedException {
        long jCurrentTimeMillis = System.currentTimeMillis() - this.f27058s;
        c.h.a(f27039y, " recvdealy: " + jCurrentTimeMillis);
        if (jCurrentTimeMillis < 45000) {
            long j2 = 45000 - jCurrentTimeMillis;
            if (j2 < 10) {
                j2 = 10;
            }
            this.f27041b.sendEmptyMessageDelayed(1002, j2 <= 45000 ? j2 : 45000L);
            return;
        }
        if (this.f27042c != null) {
            c.h.a(f27039y, " recvdealy:" + jCurrentTimeMillis + " > WebSocketDefine.RTCWS_CON_KEEPLIVE_TIME ");
            this.f27042c.b(8, "receive delay over");
        }
    }

    public void k() {
        c.h.a(f27039y, " onHandleOnConnect " + this.f27061v + " state: " + this.f27046g);
        if (this.f27061v && this.f27046g == e.RECONNECTING) {
            d dVar = this.f27040a;
            if (dVar != null) {
                dVar.a(this.f27060u);
                c.h.a(f27039y, " onWebSocketReconnected ");
                this.f27060u = h.d.RECON_RS_NULL;
            }
        } else {
            d dVar2 = this.f27040a;
            if (dVar2 != null) {
                dVar2.a();
            }
        }
        s();
        u();
        t();
        B();
        this.f27049j = 0;
        this.f27061v = true;
        this.f27046g = e.CONNECTED;
        this.f27060u = h.d.RECON_RS_NULL;
        this.f27059t = System.currentTimeMillis();
        this.f27058s = System.currentTimeMillis();
    }

    public void l() {
        int i2 = this.f27049j;
        if (i2 > this.f27048i) {
            c.h.a(f27039y, " onHandleOnConnectError disconnectServer");
            f();
            d dVar = this.f27040a;
            if (dVar != null) {
                dVar.a(h.d.RECON_RS_NULL.ordinal());
                return;
            }
            return;
        }
        this.f27049j = i2 + 1;
        e eVar = this.f27046g;
        e eVar2 = e.RECONNECTING;
        if (eVar != eVar2) {
            this.f27046g = eVar2;
            this.f27060u = h.d.RECON_RS_CONFAIL;
            d dVar2 = this.f27040a;
            if (dVar2 != null) {
                dVar2.b();
            }
        }
    }

    public void m() {
        c.h.a(f27039y, " onHandleOnConnectLost " + this.f27046g);
        if (this.f27046g == e.RECONNECTING) {
            c.h.a(f27039y, " reconnect now processing " + this.f27046g);
            return;
        }
        c.h.a(f27039y, "mCurconcounts is: " + this.f27049j + " mMaxrecon is: " + this.f27048i);
        d dVar = this.f27040a;
        if (dVar != null && this.f27049j > this.f27048i) {
            dVar.a(h.d.RECON_RS_CONFAIL.ordinal());
        }
        h.d dVar2 = this.f27060u;
        if (dVar2 == h.d.RECON_RS_NULL) {
            dVar2 = this.f27061v ? h.d.RECON_RS_DISCONNECT : h.d.RECON_RS_CONFAIL;
        }
        a(false, dVar2);
    }

    public void n() {
        this.f27046g = e.NEW;
        d();
        this.f27063x.clear();
        d dVar = this.f27040a;
        if (dVar != null) {
            dVar.b(0);
        }
    }

    public void o() {
        int i2 = this.f27049j;
        if (i2 <= this.f27048i) {
            this.f27049j = i2 + 1;
            c.h.a(f27039y, " onHandleOnReconct now counts is:" + this.f27049j);
            b(this.f27044e, this.f27045f);
            B();
            v();
            return;
        }
        boolean z2 = this.f27061v;
        c.h.a(f27039y, " onHandleOnReconct disconnectServer");
        f();
        d dVar = this.f27040a;
        if (dVar != null) {
            if (z2) {
                dVar.b(h.d.RECON_RS_DISCONNECT.ordinal());
            } else {
                dVar.a(h.d.RECON_RS_CONFAIL.ordinal());
            }
        }
        this.f27060u = h.d.RECON_RS_NULL;
    }

    public void p() {
        if (h()) {
            long jCurrentTimeMillis = System.currentTimeMillis() - this.f27058s;
            if (jCurrentTimeMillis >= 14000) {
                e.a.a().Q();
                jCurrentTimeMillis = C.DEFAULT_SEEK_FORWARD_INCREMENT_MS;
            }
            this.f27041b.sendEmptyMessageDelayed(1001, jCurrentTimeMillis);
        }
    }

    public void q() throws JSONException {
        if (this.f27063x.isEmpty()) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        Iterator<Map.Entry<String, h>> it = this.f27063x.entrySet().iterator();
        while (it.hasNext()) {
            h value = it.next().getValue();
            if (value != null) {
                long jCurrentTimeMillis = System.currentTimeMillis() - value.f27091e;
                if (jCurrentTimeMillis >= com.heytap.mcssdk.constant.a.f7153q && this.f27040a != null) {
                    try {
                        JSONObject jSONObject = new JSONObject(value.f27089c);
                        JSONObject jSONObject2 = new JSONObject();
                        jSONObject2.put("code", 5019);
                        jSONObject2.put("msg", "msg req timeout");
                        jSONObject2.put("rpc_id", value.f27088b);
                        jSONObject2.put("data", jSONObject);
                        arrayList.add(value.f27088b);
                        c.h.a(f27039y, "  remove rpcId msg: " + value.f27088b + "delay: " + jCurrentTimeMillis);
                        this.f27040a.b(jSONObject2.toString());
                    } catch (JSONException e2) {
                        e2.printStackTrace();
                    }
                }
            }
        }
        if (!arrayList.isEmpty()) {
            Iterator it2 = arrayList.iterator();
            while (it2.hasNext()) {
                this.f27063x.remove((String) it2.next());
            }
        }
        this.f27041b.sendEmptyMessageDelayed(1012, 5000L);
    }

    public void r() {
        for (Map.Entry<String, h> entry : this.f27063x.entrySet()) {
            c.h.a(f27039y, " sendCacheMsg" + entry.getValue());
            h value = entry.getValue();
            value.f27090d = System.currentTimeMillis();
            value.f27091e = System.currentTimeMillis();
            WebSocketConnection webSocketConnection = this.f27042c;
            if (webSocketConnection != null) {
                webSocketConnection.a(value.f27089c);
            }
        }
    }

    public void s() {
        if (this.f27054o) {
            return;
        }
        this.f27054o = true;
        Message messageObtainMessage = this.f27041b.obtainMessage();
        messageObtainMessage.what = 1002;
        this.f27041b.sendMessageDelayed(messageObtainMessage, this.f27052m);
    }

    public void t() {
        if (this.f27057r) {
            return;
        }
        this.f27057r = true;
        Message messageObtainMessage = this.f27041b.obtainMessage();
        messageObtainMessage.what = 1012;
        this.f27041b.sendMessageDelayed(messageObtainMessage, this.f27053n);
    }

    public void u() {
        if (this.f27055p) {
            return;
        }
        this.f27055p = true;
        Message messageObtainMessage = this.f27041b.obtainMessage();
        messageObtainMessage.what = 1001;
        this.f27041b.sendMessageDelayed(messageObtainMessage, this.f27051l);
    }

    public void v() {
        if (this.f27056q) {
            return;
        }
        this.f27056q = true;
        Message messageObtainMessage = this.f27041b.obtainMessage();
        messageObtainMessage.what = 1007;
        c.h.a(f27039y, " startRecon " + this.f27050k);
        this.f27041b.sendMessageDelayed(messageObtainMessage, (long) this.f27050k);
    }

    public void w() {
        y();
        A();
        B();
        z();
        x();
        D();
        C();
        this.f27041b.getLooper().quit();
    }

    public void x() {
        if (this.f27057r) {
            this.f27057r = false;
            this.f27041b.removeMessages(1003);
        }
    }

    public void y() {
        if (this.f27054o) {
            this.f27054o = false;
            this.f27041b.removeMessages(1002);
        }
    }

    public void z() {
        if (this.f27056q) {
            this.f27056q = false;
            this.f27041b.removeMessages(1012);
        }
    }

    public void a(String str, String str2) {
        Message messageObtainMessage = this.f27041b.obtainMessage();
        messageObtainMessage.what = 1003;
        Bundle bundle = new Bundle();
        bundle.putString("wsurl", str);
        bundle.putString("ip", str2);
        messageObtainMessage.setData(bundle);
        this.f27041b.sendMessage(messageObtainMessage);
    }

    public void c(String str) {
        this.f27041b.post(new a(str));
    }

    public void d(String str) throws JSONException {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("method", "ping");
            jSONObject.put("rpc_id", str);
            jSONObject.put("version", 1.0d);
            if (this.f27042c != null) {
                c.h.a(f27039y, "send ping" + jSONObject);
                this.f27042c.a(jSONObject.toString());
            }
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    public void e() {
        Message messageObtainMessage = this.f27041b.obtainMessage();
        messageObtainMessage.what = 1004;
        this.f27041b.sendMessage(messageObtainMessage);
    }

    public void f() {
        w();
        this.f27046g = e.NEW;
        WebSocketConnection webSocketConnection = this.f27042c;
        if (webSocketConnection != null) {
            webSocketConnection.d();
            this.f27042c = null;
        }
        this.f27063x.clear();
        this.f27044e = "";
        this.f27045f = "";
        this.f27049j = 0;
        this.f27058s = -1L;
        this.f27060u = h.d.RECON_RS_NULL;
        this.f27059t = -1L;
        this.f27061v = false;
    }

    public void c(String str, String str2) {
        d dVar;
        c.h.a(f27039y, "onHandleConnect start:");
        if (!h() && !this.f27056q) {
            if (this.f27047h == null) {
                this.f27047h = new C0453f();
            }
            this.f27046g = e.CONNECTING;
            b(str, str2);
        }
        if (h() && (dVar = this.f27040a) != null) {
            dVar.a();
        }
        c.h.a(f27039y, "onHandleConnect finish:");
    }

    public final void e(String str, String str2) {
        c.h.a(f27039y, "WS " + str + " :  : " + str2);
    }

    public void g(boolean z2) {
        Message messageObtainMessage = this.f27041b.obtainMessage();
        messageObtainMessage.what = 1017;
        Bundle bundle = new Bundle();
        bundle.putBoolean("flag", z2);
        messageObtainMessage.setData(bundle);
        c.h.a(f27039y, "setReconnectFlag is " + z2);
        this.f27041b.sendMessage(messageObtainMessage);
    }

    public void a(String str, String str2, h.d dVar) {
        this.f27044e = str;
        this.f27045f = str2;
        c.h.a(f27039y, "recon for new signal state is " + this.f27046g);
        a(false, dVar);
    }

    public void d() {
        c.h.a(f27039y, "close called");
        B();
        y();
        A();
        z();
        C();
        D();
        x();
        this.f27059t = -1L;
        this.f27058s = -1L;
        this.f27060u = h.d.RECON_RS_NULL;
    }

    public void b(String str, String str2) {
        this.f27044e = str;
        this.f27045f = str2;
        if (this.f27047h == null) {
            this.f27047h = new C0453f();
        }
        if (this.f27042c == null) {
            this.f27042c = new WebSocketConnection();
        }
        try {
            this.f27042c.a(this.f27044e, str2, this.f27047h, this.f27043d);
        } catch (WebSocketException e2) {
            e2.printStackTrace();
        }
    }

    public void a(String str, String str2, h.d dVar, int i2) {
        if (this.f27046g == e.CONNECTED) {
            this.f27060u = dVar;
            Message messageObtainMessage = this.f27041b.obtainMessage();
            messageObtainMessage.what = 1016;
            Bundle bundle = new Bundle();
            if (dVar == h.d.RECON_RS_SWITCH_SIGNAL || dVar == h.d.RECON_RS_REQUEST_SIGNAL) {
                bundle.putString("wsurl", str);
                bundle.putString("ip", str2);
            }
            bundle.putInt("reason", dVar.ordinal());
            messageObtainMessage.setData(bundle);
            c.h.a(f27039y, " failConnection ");
            this.f27041b.sendMessageDelayed(messageObtainMessage, i2);
            return;
        }
        this.f27060u = h.d.RECON_RS_NULL;
        c.h.a(f27039y, "failConnection faild for state is " + this.f27046g);
    }

    public void d(String str, String str2) {
        h hVar = new h("", str, str2, true);
        Message messageObtainMessage = this.f27041b.obtainMessage();
        messageObtainMessage.what = 1009;
        Bundle bundle = new Bundle();
        bundle.putSerializable("msgdata", hVar);
        messageObtainMessage.setData(bundle);
        c.h.a(f27039y, " wsclient::sendMessageTask: " + str2);
        this.f27041b.sendMessage(messageObtainMessage);
    }

    public void a(boolean z2, h.d dVar) {
        d dVar2;
        c.h.a(f27039y, " doReconnect state: " + this.f27046g + " mReconnectFlag: " + this.f27062w);
        e eVar = this.f27046g;
        e eVar2 = e.CONNECTED;
        if (eVar != eVar2 && eVar != e.CONNECTING) {
            this.f27060u = h.d.RECON_RS_NULL;
            c.h.a(f27039y, "recon faild for state is " + this.f27046g);
            return;
        }
        c.h.a(f27039y, "do reconnect: " + this.f27042c);
        d();
        c.h.a(f27039y, " dorenconnect wsurl " + this.f27044e + " reason: " + this.f27060u);
        c.h.a(f27039y, " mPrivateConnect " + this.f27061v + " state: " + this.f27046g);
        if (((this.f27061v && this.f27046g == eVar2) || this.f27046g == e.CONNECTING) && (dVar2 = this.f27040a) != null) {
            dVar2.b();
        }
        this.f27046g = e.RECONNECTING;
        this.f27060u = dVar;
        v();
    }

    public void a(e eVar) {
        this.f27046g = eVar;
    }

    public void a(String str, String str2, boolean z2) {
        h hVar = new h("", str, str2, z2);
        Message messageObtainMessage = this.f27041b.obtainMessage();
        messageObtainMessage.what = 1009;
        Bundle bundle = new Bundle();
        bundle.putSerializable("msgdata", hVar);
        messageObtainMessage.setData(bundle);
        c.h.a(f27039y, " wsclient::sendMessageTask is: " + hVar);
        this.f27041b.sendMessage(messageObtainMessage);
    }

    public void a(Message message) throws InterruptedException {
        String str;
        int i2 = message.getData().getInt("reason");
        int i3 = b.f27066a[h.d.a(i2).ordinal()];
        if (i3 == 1) {
            this.f27044e = message.getData().getString("wsurl");
            this.f27045f = message.getData().getString("ip");
            str = "switch signal";
        } else if (i3 != 2) {
            str = i3 != 3 ? "" : "log off recon";
        } else {
            this.f27044e = message.getData().getString("wsurl");
            this.f27045f = message.getData().getString("ip");
            str = "request signal from region";
        }
        this.f27060u = h.d.a(i2);
        c.h.a(f27039y, "failConnection for new signal state is " + this.f27046g);
        this.f27042c.b(8, str);
    }

    public void a(String str) {
        c.h.a(f27039y, " onHandleDisConnect " + this.f27040a);
        f();
    }

    public void a(h hVar) throws JSONException {
        if (this.f27042c != null) {
            c.h.d(f27039y, " onHandleSendMsg " + this.f27046g);
            int iOrdinal = this.f27046g.ordinal();
            e eVar = e.CONNECTING;
            if (iOrdinal < eVar.ordinal()) {
                if (this.f27040a != null) {
                    this.f27059t = System.currentTimeMillis();
                    try {
                        JSONObject jSONObject = new JSONObject(hVar.f27089c);
                        if (this.f27046g.ordinal() > eVar.ordinal() || this.f27040a == null) {
                            return;
                        }
                        JSONObject jSONObject2 = new JSONObject();
                        jSONObject2.put("code", 5001);
                        jSONObject2.put("msg", "server disconnect");
                        jSONObject2.put("rpc_id", hVar.f27088b);
                        jSONObject2.put("data", jSONObject);
                        this.f27040a.b(jSONObject2.toString());
                        return;
                    } catch (JSONException e2) {
                        e2.printStackTrace();
                        return;
                    }
                }
                return;
            }
            if (hVar.g()) {
                h hVar2 = this.f27063x.get(hVar.f27088b);
                if (hVar2 == null) {
                    hVar.f27091e = System.currentTimeMillis();
                    this.f27063x.put(hVar.f27088b, hVar);
                } else {
                    hVar2.f27091e = System.currentTimeMillis();
                    hVar2.f27089c = hVar.f27089c;
                    hVar2.f27088b = hVar.f27088b;
                }
            } else {
                c.h.a(f27039y, "message not saved " + hVar);
            }
            if (h()) {
                c.h.a(f27039y, " wsobj " + this.f27042c);
                if (this.f27042c != null) {
                    c.h.a(f27039y, "sendmsg " + hVar.f27089c);
                    this.f27042c.a(hVar.f27089c);
                }
            }
        }
    }
}
