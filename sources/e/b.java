package e;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import cn.hutool.core.text.StrPool;
import core.data.AuthInfo;
import d.a;
import e.l;
import j.c;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;
import org.wrtca.api.PeerConnectionFactory;
import org.wrtca.api.VideoRenderer;
import org.wrtca.api.VideoSink;

/* loaded from: classes8.dex */
public abstract class b extends l implements k {
    public static final String B = "LogicEngine";
    public f.i A;

    /* renamed from: o, reason: collision with root package name */
    public i f26743o;

    /* renamed from: p, reason: collision with root package name */
    public f.h f26744p;

    /* renamed from: q, reason: collision with root package name */
    public j.h f26745q;

    /* renamed from: r, reason: collision with root package name */
    public j.h f26746r;

    /* renamed from: s, reason: collision with root package name */
    public d f26747s;

    /* renamed from: t, reason: collision with root package name */
    public d f26748t;

    /* renamed from: u, reason: collision with root package name */
    public Map<String, String> f26749u;

    /* renamed from: v, reason: collision with root package name */
    public Map<String, Object> f26750v;

    /* renamed from: w, reason: collision with root package name */
    public f.c f26751w;

    /* renamed from: x, reason: collision with root package name */
    public Map<String, d> f26752x;

    /* renamed from: y, reason: collision with root package name */
    public AuthInfo f26753y;

    /* renamed from: z, reason: collision with root package name */
    public f.e f26754z;

    public class a implements Runnable {
        public a() {
        }

        @Override // java.lang.Runnable
        public void run() throws IOException {
            int iG = b.this.G();
            b.this.f26754z.e(iG + "MB");
            b.this.f26754z.b(iG);
        }
    }

    /* renamed from: e.b$b, reason: collision with other inner class name */
    public static abstract class AbstractC0448b extends l.a {

        /* renamed from: c, reason: collision with root package name */
        public i f26756c;

        public void a(i iVar) {
            this.f26756c = iVar;
        }
    }

    public class c {

        /* renamed from: a, reason: collision with root package name */
        public Message f26757a;

        public c(Message message) {
            this.f26757a = message;
        }

        public void a() {
            b.this.a(this.f26757a);
        }
    }

    public b(PeerConnectionFactory.Options options) {
        super(options);
        this.f26749u = new HashMap();
        this.f26752x = new HashMap();
        this.f26753y = new AuthInfo();
        this.f26747s = null;
        this.f26748t = null;
        this.f26745q = new j.h();
        this.f26746r = new j.h();
        f.e eVarG = f.e.g();
        this.f26754z = eVarG;
        eVarG.c(Build.MANUFACTURER + StrPool.UNDERLINE + Build.MODEL + StrPool.UNDERLINE + Build.ID);
        f.e eVar = this.f26754z;
        StringBuilder sb = new StringBuilder();
        sb.append("android");
        sb.append(Build.VERSION.RELEASE);
        sb.append(Build.CPU_ABI);
        eVar.h(sb.toString());
        this.f26754z.d(Build.DISPLAY);
        this.f26754z.b(Build.HARDWARE + "_xxx_" + Runtime.getRuntime().availableProcessors());
        int i2 = ((int) (Runtime.getRuntime().totalMemory() / 1024)) / 1024;
        this.f26754z.e(i2 + "MB");
        new Thread(new a()).start();
        this.f26754z.b(i2);
        this.A = new f.i();
        this.f26751w = new f.c();
    }

    public j.h A() {
        return this.f26746r;
    }

    public d B() {
        return this.f26748t;
    }

    public f.i C() {
        return this.A;
    }

    public Map<String, d> D() {
        return this.f26752x;
    }

    public Map<String, String> E() {
        return this.f26749u;
    }

    public f.e F() {
        return this.f26754z;
    }

    public final int G() throws IOException {
        String str = null;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("/proc/meminfo"), 8192);
            str = bufferedReader.readLine().split("\\s+")[1];
            bufferedReader.close();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        if (str != null) {
            return (int) Math.ceil(new Float(Float.valueOf(str).floatValue() / 1024.0f).doubleValue());
        }
        return 0;
    }

    public void H() {
        c.h.a(B, "leaveRoomLocalRendering start");
        O();
        L();
        M();
        this.A.b(false);
        c.h.a(B, "leaveRoomLocalRendering finish");
    }

    public void I() {
        c.h.a(B, "leaveRoomRelease start");
        N();
        L();
        M();
        this.A.b(false);
        c.h.a(B, "leaveRoomRelease finish");
    }

    public void J() {
        Handler handler = this.f26799f;
        if (handler != null) {
            Message messageObtainMessage = handler.obtainMessage();
            messageObtainMessage.what = f.a.BUSINESS_EVENT_AUDIO_FILE_FINISH.ordinal();
            Bundle bundle = new Bundle();
            bundle.putString("msgdata", "audiofilefinish");
            messageObtainMessage.setData(bundle);
            this.f26799f.sendMessage(messageObtainMessage);
            c.h.a(B, " onAudioFileFinish");
        }
    }

    public void K() {
        j.d.d().r();
    }

    public final void L() {
        j.d.d().w();
        this.f26752x.clear();
        this.f26749u.clear();
    }

    public final void M() {
        this.f26753y.setToken("");
        this.f26753y.setUId("");
        this.f26753y.setRoomId("");
        this.f26753y.setAppId("");
        j.h hVar = this.f26745q;
        if (hVar != null) {
            hVar.a(20);
            this.f26745q.b(240);
            this.f26745q.f(320);
            this.f26745q.c(300);
            this.f26745q.d(200);
            this.f26745q.e(200);
        }
        this.f26751w.a();
    }

    public void N() {
        if (this.f26747s != null) {
            c.h.a(B, "LogicEnginereleaseSelfStream " + this.f26747s.f26759a);
            j.d.d().b(this.f26747s.f26759a);
            this.f26747s = null;
        } else {
            j.d.d().j(false);
        }
        if (this.f26748t != null) {
            j.d.d().b(this.f26748t.f26759a);
            this.f26748t = null;
        } else {
            j.d.d().E();
        }
        j.d.d().b();
        j.d.d().a();
    }

    public void O() {
        if (this.f26747s != null) {
            c.h.a(B, "LogicEnginereleaseSelfStreamStillLocalRender " + this.f26747s.f26759a);
            j.d.d().c(this.f26747s.f26759a);
            this.f26747s = null;
        }
        if (this.f26748t != null) {
            j.d.d().b(this.f26748t.f26759a);
            this.f26748t = null;
        }
        j.d.d().a();
    }

    public void P() {
        j.d.d().z();
    }

    public void Q() {
        Handler handler = this.f26799f;
        if (handler != null) {
            Message messageObtainMessage = handler.obtainMessage();
            messageObtainMessage.what = f.a.BUSINESS_EVENT_PING_FOR_ROOM_VERSION.ordinal();
            this.f26799f.sendMessage(messageObtainMessage);
            c.h.a(B, " sendPingForRoomVersion");
        }
    }

    public void R() {
        j.d.d().C();
    }

    public void S() {
        Handler handler = this.f26799f;
        if (handler != null) {
            Message messageObtainMessage = handler.obtainMessage();
            messageObtainMessage.what = f.a.BUSINESS_EVENT_SYNC_ROOM_INFO.ordinal();
            this.f26799f.sendMessage(messageObtainMessage);
            c.h.a(B, " syncRoomInfo");
        }
    }

    public void b(boolean z2) {
        j.d.d().d(z2);
    }

    public void c(String str, int i2, int i3) {
        j.d.d().b(str);
        if (i2 == 1) {
            if (i3 == 1) {
                j.d.d().b();
            } else if (i3 == 2) {
                j.d.d().a();
            }
        }
    }

    public void d(String str) {
        j.d.d().a(str);
    }

    public String e(String str) {
        return this.f26752x.get(this.f26751w.a(str)).f26759a;
    }

    public AuthInfo u() {
        return this.f26753y;
    }

    public d v() {
        return this.f26747s;
    }

    public j.h w() {
        return this.f26745q;
    }

    public i x() {
        return this.f26743o;
    }

    public f.c y() {
        return this.f26751w;
    }

    public f.h z() {
        return this.f26744p;
    }

    @Override // e.k
    public void a(Message message) {
        this.f26799f.sendMessage(message);
    }

    public void b(j.h hVar) {
        if (hVar != null) {
            c.h.a(B, "setVideoConfig and update dynamicVideoConfig  with " + hVar);
            this.f26745q = hVar;
            j.d.d().b(hVar);
            j.d.d().c(this.f26745q.f(), this.f26745q.b());
        }
    }

    public void a(String str, f fVar) {
        this.f26800g.put(str, new e(str, fVar));
    }

    public void c(boolean z2) {
        if (z2) {
            j.d.d().A();
        } else {
            j.d.d().B();
        }
    }

    public void a(String str, f fVar, Object obj) {
        e eVar = new e(str, fVar);
        eVar.a(obj);
        this.f26800g.put(str, eVar);
    }

    public void b(d dVar) {
        this.f26748t = dVar;
    }

    @Override // h.f.d
    public void b(int i2) {
        Handler handler = this.f26799f;
        if (handler != null) {
            Message messageObtainMessage = handler.obtainMessage();
            messageObtainMessage.what = f.a.BUSINESS_EVENT_MEDIASERVER_DISCON.ordinal();
            this.f26799f.sendMessage(messageObtainMessage);
        }
    }

    public void a(String str, int i2, int i3, boolean z2, boolean z3, c.g gVar) {
        j.d.d().a(str, i2, i3, z2, z3, gVar);
        if (i2 == 1) {
            if (i3 == 1) {
                j.d.d().a(str, this.f26745q);
                return;
            }
            if (i3 == 2) {
                j.h hVar = new j.h();
                hVar.e(400);
                hVar.d(400);
                hVar.c(500);
                j.d.d().a(str, hVar);
            }
        }
    }

    @Override // h.f.d
    public void b() {
        Handler handler = this.f26799f;
        if (handler != null) {
            Message messageObtainMessage = handler.obtainMessage();
            messageObtainMessage.what = f.a.BUSINESS_EVENT_MEDIASERVER_RECONING.ordinal();
            this.f26799f.sendMessage(messageObtainMessage);
        }
    }

    public void a(String str, boolean z2) {
        j.d.d().a(str, z2);
    }

    public void a(boolean z2) {
        j.d.d().c(z2);
    }

    public void a(String str, double d3) {
        String strA = this.f26751w.a(str + a.h.f26636f);
        if (!TextUtils.isEmpty(strA)) {
            d dVar = this.f26752x.get(strA);
            if (dVar != null) {
                j.d.d().a(dVar.f26759a, d3);
                return;
            }
            c.h.a(B, " LogicStreamClient is null streamId is:" + strA);
            return;
        }
        c.h.a(B, " streamId is not found.");
    }

    @Override // h.f.d
    public void b(String str) {
        Handler handler = this.f26799f;
        if (handler != null) {
            Message messageObtainMessage = handler.obtainMessage();
            messageObtainMessage.what = f.a.BUSINESS_EVENT_MESSAGE_ERROR.ordinal();
            Bundle bundle = new Bundle();
            bundle.putString("msgdata", str);
            messageObtainMessage.setData(bundle);
            this.f26799f.sendMessage(messageObtainMessage);
            c.h.a(B, " onWebSocketError for: " + str);
        }
    }

    public void a(double d3) {
        j.d.d().a(d3);
    }

    public String a(int i2, VideoSink videoSink, boolean z2, boolean z3) {
        c.h.a(B, " startLocalRender mediaType: " + i2 + " addVideo: " + z2 + " addAudio: " + z3);
        if (i2 != 1) {
            if (i2 != 2) {
                return "";
            }
            j.d.d().a(this.f26746r);
            if (videoSink == null) {
                return "";
            }
            j.d.d().b(videoSink);
            return "";
        }
        String strA = j.d.d().a(this.f26745q, z2, z3);
        c.h.a(B, "start local render result  " + strA);
        if (TextUtils.isEmpty(strA)) {
            if (videoSink == null) {
                return "";
            }
            j.d.d().a(videoSink);
            return "";
        }
        c.h.a(B, "start local render failed for " + strA);
        return strA;
    }

    public void a(int i2, String str, VideoRenderer.Callbacks callbacks) throws JSONException {
        String str2;
        String strA = this.f26751w.a(str + a.h.a(i2));
        c.h.a(B, " startRemoteRender streamId " + strA);
        boolean z2 = false;
        if (TextUtils.isEmpty(strA)) {
            str2 = " send res for not sub stream yet ";
        } else {
            d dVar = this.f26752x.get(strA);
            if (dVar != null) {
                c.h.a(B, "subYet = true, startRemoteRender selfsubid " + dVar.f26759a);
                j.d.d().a(dVar.f26759a, callbacks);
                try {
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put("mediatype", i2);
                    jSONObject.put("uid", str);
                    jSONObject.put("code", true);
                    h().f(jSONObject.toString());
                    c.h.a(B, " send  res " + jSONObject.toString());
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
                str2 = "";
                z2 = true;
            } else {
                str2 = "send res for not sub stream yet ,uclient == null";
            }
        }
        if (z2) {
            return;
        }
        try {
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("mediatype", i2);
            jSONObject2.put("uid", str);
            jSONObject2.put("code", 1);
            h().f(jSONObject2.toString());
            c.h.a(B, "noSubReason: " + str2 + "detail: " + jSONObject2.toString());
        } catch (JSONException e3) {
            e3.printStackTrace();
        }
    }

    public void a(int i2, String str, boolean z2) {
        d dVar;
        String strA = this.f26751w.a(str + a.h.a(i2));
        c.h.a(B, " want to stopRemoteRender streamId " + strA);
        if (TextUtils.isEmpty(strA) || (dVar = this.f26752x.get(strA)) == null) {
            return;
        }
        c.h.a(B, " stopRemoteRender selfsubid " + dVar.f26759a);
        j.d.d().e(dVar.f26759a, z2);
    }

    public void a(int i2, String str, boolean z2, VideoRenderer.Callbacks callbacks) {
        d dVar;
        String strA = this.f26751w.a(str + a.h.a(i2));
        c.h.a(B, " want to stopRemoteRender streamId " + strA);
        if (TextUtils.isEmpty(strA) || (dVar = this.f26752x.get(strA)) == null) {
            return;
        }
        c.h.a(B, " stopRemoteRender selfsubid " + dVar.f26759a);
        j.d.d().a(dVar.f26759a, z2, callbacks);
    }

    public void a(int i2, boolean z2) {
        if (i2 == 1) {
            j.d.d().j(z2);
        } else if (i2 == 2) {
            j.d.d().E();
        }
    }

    public void a(int i2, VideoSink videoSink, boolean z2) {
        if (i2 == 1) {
            j.d.d().a(videoSink, z2);
        } else if (i2 == 2) {
            j.d.d().E();
        }
    }

    public void a(j.h hVar) {
        if (hVar != null) {
            c.h.a(B, "setScreen config");
            this.f26746r = hVar;
        }
    }

    public void a(String str, boolean z2, boolean z3) {
        if (str != null) {
            c.h.a(B, "startPlayAudioFile path: " + str);
            j.d.d().a(str, z2, z3);
        }
    }

    public void a(f.h hVar) {
        this.f26744p = hVar;
    }

    public void a(d dVar) {
        this.f26747s = dVar;
    }

    public void a(f.c cVar) {
        this.f26751w = cVar;
    }

    public void a(i iVar) {
        this.f26743o = iVar;
    }

    @Override // h.f.d
    public void a() {
        Handler handler = this.f26799f;
        if (handler != null) {
            this.f26799f.sendMessage(handler.obtainMessage(f.a.BUSINESS_EVENT_MEDIASERVER_CON_SUC.ordinal()));
        }
    }

    @Override // h.f.d
    public void a(int i2) {
        Handler handler = this.f26799f;
        if (handler != null) {
            Message messageObtainMessage = handler.obtainMessage();
            messageObtainMessage.what = f.a.BUSINESS_EVENT_MEDIASERVER_CON_FAIL.ordinal();
            this.f26799f.sendMessage(messageObtainMessage);
        }
    }

    @Override // h.f.d
    public void a(h.d dVar) {
        StringBuilder sb = new StringBuilder();
        sb.append("onWebSocketReconnected called ");
        sb.append(dVar);
        sb.append("mEventHandler: ");
        sb.append(this.f26799f);
        sb.append(" what :");
        f.a aVar = f.a.BUSINESS_EVENT_MEDIASERVER_RECONED;
        sb.append(aVar.ordinal());
        c.h.a(B, sb.toString());
        Handler handler = this.f26799f;
        if (handler != null) {
            Message messageObtainMessage = handler.obtainMessage();
            messageObtainMessage.what = aVar.ordinal();
            Bundle bundle = new Bundle();
            bundle.putInt("reason", dVar.ordinal());
            messageObtainMessage.setData(bundle);
            this.f26799f.sendMessage(messageObtainMessage);
        }
    }

    @Override // h.f.d
    public void a(String str) {
        Handler handler = this.f26799f;
        if (handler != null) {
            Message messageObtainMessage = handler.obtainMessage();
            messageObtainMessage.what = f.a.BUSINESS_EVENT_MESSAGE_FROM_SIGNAL.ordinal();
            Bundle bundle = new Bundle();
            bundle.putString("msgdata", str);
            messageObtainMessage.setData(bundle);
            this.f26799f.sendMessage(messageObtainMessage);
        }
    }
}
