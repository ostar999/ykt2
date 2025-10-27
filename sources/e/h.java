package e;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import com.aliyun.player.alivcplayerexpand.util.database.DatabaseManager;
import com.umeng.socialize.net.dplus.CommonNetImpl;
import e.b;
import h.f;
import j.c;
import org.json.JSONException;
import org.json.JSONObject;
import org.wrtca.api.PeerConnectionFactory;

/* loaded from: classes8.dex */
public class h extends e.b implements c.g {
    public static final String D = "SignalLogicEngine";
    public int C;

    public static class b extends b.AbstractC0448b {
        public b(c cVar, i iVar) {
            this.f26807b = cVar;
            this.f26756c = iVar;
        }

        @Override // e.l.a
        public l a() {
            return new h(this.f26807b, this.f26756c, this.f26806a);
        }

        public b b(PeerConnectionFactory.Options options) {
            this.f26806a = options;
            return this;
        }
    }

    public static h T() {
        return (h) l.f26793n;
    }

    public static h a(c cVar, i iVar) {
        c.h.a(D, " LogicEngine::createInstance start");
        if (l.f26793n == null) {
            b bVar = new b(cVar, iVar);
            bVar.b(new PeerConnectionFactory.Options());
            l.f26793n = bVar.a();
        }
        c.h.a(D, " LogicEngine::createInstance end");
        return (h) l.f26793n;
    }

    public final void U() {
        this.f26801h.put(c.i.A, this.f26797d.a(c.i.A));
        this.f26801h.put(c.i.B, this.f26797d.a(c.i.B));
        this.f26801h.put(c.i.C, this.f26797d.a(c.i.C));
        this.f26801h.put(c.i.D, this.f26797d.a(c.i.D));
        this.f26801h.put(c.i.E, this.f26797d.a(c.i.E));
        this.f26801h.put(c.i.F, this.f26797d.a(c.i.F));
        this.f26801h.put(c.i.G, this.f26797d.a(c.i.G));
        this.f26801h.put(c.i.I, this.f26797d.a(c.i.I));
        this.f26801h.put(c.i.J, this.f26797d.a(c.i.J));
        this.f26801h.put(c.i.K, this.f26797d.a(c.i.K));
    }

    @Override // j.c.g
    public void b(String str, int i2, int i3) throws JSONException {
        e(str, i2, i3);
    }

    @Override // j.c.g
    public void c(String str) {
        f(str);
    }

    public void d(String str, int i2, int i3) throws JSONException {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("streamid", str);
            jSONObject.put("mtype", i2);
            jSONObject.put("vol", i3);
            Message messageObtainMessage = this.f26799f.obtainMessage(f.a.BUSINESS_EVENT_LOCAL_VOL.ordinal());
            f.f fVar = new f.f(jSONObject.toString(), null);
            Bundle bundle = new Bundle();
            bundle.putSerializable("msgdata", fVar);
            messageObtainMessage.setData(bundle);
            this.f26799f.sendMessage(messageObtainMessage);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    public void e(String str, int i2, int i3) throws JSONException {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("streamid", str);
            jSONObject.put("mtype", i2);
            jSONObject.put("vol", i3);
            Message messageObtainMessage = this.f26799f.obtainMessage(f.a.BUSINESS_EVENT_REMOTE_VOL.ordinal());
            f.f fVar = new f.f(jSONObject.toString(), null);
            Bundle bundle = new Bundle();
            bundle.putSerializable("msgdata", fVar);
            messageObtainMessage.setData(bundle);
            this.f26799f.sendMessage(messageObtainMessage);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    public void f(String str) {
        Message messageObtainMessage = this.f26799f.obtainMessage(f.a.BUSINESS_EVENT_PEER_STATS_UPDATE.ordinal());
        f.f fVar = new f.f(str, null);
        Bundle bundle = new Bundle();
        bundle.putSerializable("msgdata", fVar);
        messageObtainMessage.setData(bundle);
        this.f26799f.sendMessage(messageObtainMessage);
    }

    @Override // e.l
    public void n() {
        this.f26744p = f.h.LOGIC_ENGINE_INIT;
    }

    @Override // e.l
    public void o() {
        this.f26795b = new g.c(this);
        this.f26796c = new g.e(this);
        this.f26797d = new g.f(this);
        U();
        this.f26799f = new Handler(this.f26803j.getLooper(), this.f26795b);
    }

    @Override // e.l
    public void r() {
        this.f26752x.clear();
        this.f26751w.a();
        I();
        f.e.a();
        j.d.d();
        j.d.a(true);
    }

    public h(c cVar, i iVar, PeerConnectionFactory.Options options) {
        super(options);
        this.C = -1;
        a(cVar);
        a(iVar);
    }

    public void b(int i2, String str, int i3, int i4, int i5, int i6, int i7) throws JSONException {
        Message messageObtainMessage;
        try {
            int iA = a(i4, i5, i6);
            if (i2 == 1) {
                messageObtainMessage = this.f26799f.obtainMessage(f.a.BUSINESS_EVENT_LOCAL_QUALITY.ordinal());
            } else if (i2 == 2) {
                if (c(i7)) {
                    iA = 0;
                }
                messageObtainMessage = this.f26799f.obtainMessage(f.a.BUSINESS_EVENT_REMOTE_QUALITY.ordinal());
            } else {
                messageObtainMessage = null;
            }
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("streamid", str);
            jSONObject.put(DatabaseManager.QUALITY, iA);
            jSONObject.put("mediatype", i3);
            f.f fVar = new f.f(jSONObject.toString(), null);
            Bundle bundle = new Bundle();
            bundle.putSerializable("msgdata", fVar);
            if (messageObtainMessage != null) {
                messageObtainMessage.setData(bundle);
                this.f26799f.sendMessage(messageObtainMessage);
            }
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    public final boolean c(int i2) {
        c.h.a(D, "packetRecvNumber: " + i2);
        boolean z2 = i2 == this.C;
        this.C = i2;
        return z2;
    }

    @Override // h.f.c
    public h.f a(f.d dVar) {
        return new h.e(this);
    }

    @Override // j.c.g
    public void a(String str, int i2, int i3, int i4) throws JSONException {
        b(str, i2, i3, i4);
    }

    @Override // j.c.g
    public void a(String str, int i2, int i3, String str2, int i4, int i5, String str3) throws JSONException {
        b(str, i2, i3, str2, i4, i5, str3);
    }

    @Override // j.c.g
    public void a(int i2, String str, int i3, int i4, int i5, int i6, int i7) throws JSONException {
        b(i2, str, i3, i4, i5, i6, i7);
    }

    @Override // j.c.g
    public void a(String str, int i2, int i3) throws JSONException {
        d(str, i2, i3);
    }

    public final int a(int i2, int i3, int i4) {
        c.h.a(D, "jitter " + i2 + " delay: " + i3 + " lost: " + i4);
        int i5 = i3 + (i2 * 2) + 10;
        double d3 = (95.0d - (i5 < 200 ? i5 / 40 : (i5 - 120.0d) / 10.0d)) - (i4 * 2.5d);
        c.h.a(D, "totalScore " + d3);
        if (d3 > 90.0d && d3 <= 100.0d) {
            return 1;
        }
        if (d3 > 80.0d && d3 <= 90.0d) {
            return 2;
        }
        if (d3 > 70.0d && d3 <= 80.0d) {
            return 3;
        }
        if (d3 > 60.0d && d3 <= 70.0d) {
            return 4;
        }
        if (d3 <= 50.0d || d3 > 60.0d) {
            return d3 <= 50.0d ? 6 : 0;
        }
        return 5;
    }

    public void b(String str, int i2, int i3, String str2, int i4, int i5, String str3) throws JSONException {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("streamid", str);
            jSONObject.put(CommonNetImpl.STYPE, i2);
            jSONObject.put("mtype", i3);
            jSONObject.put("sdptype", str2);
            jSONObject.put("sdp", str3);
            jSONObject.put("minbitrate", i4);
            jSONObject.put("maxbitrate", i5);
            c.h.a(D, " peeroffercreate " + jSONObject.toString());
            Message messageObtainMessage = this.f26799f.obtainMessage(f.a.BUSINESS_EVENT_CREATESDP_SUC.ordinal());
            f.f fVar = new f.f(jSONObject.toString(), null);
            Bundle bundle = new Bundle();
            bundle.putSerializable("msgdata", fVar);
            messageObtainMessage.setData(bundle);
            this.f26799f.sendMessage(messageObtainMessage);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    public void b(String str, int i2, int i3, int i4) throws JSONException {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("streamid", str);
            jSONObject.put(CommonNetImpl.STYPE, i2);
            jSONObject.put("mtype", i3);
            jSONObject.put("status", i4);
            c.h.a(D, " onPeerIceState " + jSONObject.toString());
            Message messageObtainMessage = this.f26799f.obtainMessage(f.a.BUSINESS_EVENT_ICE_STATE.ordinal());
            f.f fVar = new f.f(jSONObject.toString(), null);
            Bundle bundle = new Bundle();
            bundle.putSerializable("msgdata", fVar);
            messageObtainMessage.setData(bundle);
            this.f26799f.sendMessage(messageObtainMessage);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }
}
