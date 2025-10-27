package j;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import core.data.StreamInfo;
import core.interfaces.FirstFrameRendered;
import e.l;
import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONException;
import org.wrtca.api.AudioSink;
import org.wrtca.api.AudioTrack;
import org.wrtca.api.DataChannel;
import org.wrtca.api.IceCandidate;
import org.wrtca.api.MediaConstraints;
import org.wrtca.api.MediaStream;
import org.wrtca.api.PeerConnection;
import org.wrtca.api.PeerConnectionFactory;
import org.wrtca.api.RtpReceiver;
import org.wrtca.api.SdpObserver;
import org.wrtca.api.SessionDescription;
import org.wrtca.api.StatsObserver;
import org.wrtca.api.StatsReport;
import org.wrtca.api.VideoRenderer;
import org.wrtca.api.VideoTrack;
import org.wrtca.video.TextureViewRenderer;

/* loaded from: classes8.dex */
public class c implements AudioSink, FirstFrameRendered {
    public static String T = "PeerConClient";

    /* renamed from: a, reason: collision with root package name */
    public int f27406a;

    /* renamed from: b, reason: collision with root package name */
    public int f27407b;

    /* renamed from: c, reason: collision with root package name */
    public String f27408c;

    /* renamed from: d, reason: collision with root package name */
    public final f f27409d;

    /* renamed from: f, reason: collision with root package name */
    public final k f27411f;

    /* renamed from: g, reason: collision with root package name */
    public final Context f27412g;

    /* renamed from: h, reason: collision with root package name */
    public PeerConnection f27413h;

    /* renamed from: i, reason: collision with root package name */
    public Timer f27414i;

    /* renamed from: j, reason: collision with root package name */
    public VideoTrack f27415j;

    /* renamed from: k, reason: collision with root package name */
    public AudioTrack f27416k;

    /* renamed from: l, reason: collision with root package name */
    public j.g f27417l;

    /* renamed from: m, reason: collision with root package name */
    public j.g f27418m;

    /* renamed from: n, reason: collision with root package name */
    public j.b f27419n;

    /* renamed from: o, reason: collision with root package name */
    public g f27420o;

    /* renamed from: p, reason: collision with root package name */
    public j.d f27421p;

    /* renamed from: q, reason: collision with root package name */
    public int f27422q;

    /* renamed from: r, reason: collision with root package name */
    public int f27423r;

    /* renamed from: s, reason: collision with root package name */
    public int f27424s;

    /* renamed from: t, reason: collision with root package name */
    public Map<Object, Object> f27425t;

    /* renamed from: e, reason: collision with root package name */
    public int f27410e = -1;

    /* renamed from: u, reason: collision with root package name */
    public boolean f27426u = false;

    /* renamed from: v, reason: collision with root package name */
    public boolean f27427v = false;

    /* renamed from: w, reason: collision with root package name */
    public int f27428w = 0;

    /* renamed from: x, reason: collision with root package name */
    public int f27429x = 0;

    /* renamed from: y, reason: collision with root package name */
    public int f27430y = 0;

    /* renamed from: z, reason: collision with root package name */
    public int f27431z = 0;
    public int A = 0;
    public int B = 0;
    public int C = 0;
    public int D = 0;
    public int E = 0;
    public int F = 0;
    public int G = 0;
    public int H = 0;
    public int I = 0;
    public int J = 0;
    public int K = 100;
    public int L = 0;
    public int M = 0;
    public int N = 0;
    public int O = 100;
    public int P = 0;
    public int Q = 0;
    public int R = 0;
    public int S = 100;

    public class a implements StatsObserver {
        public a() {
        }

        @Override // org.wrtca.api.StatsObserver
        public void onComplete(StatsReport[] statsReportArr) throws JSONException, NumberFormatException {
            c.this.a(statsReportArr);
        }
    }

    public class b extends TimerTask {

        public class a implements Runnable {
            public a() {
            }

            @Override // java.lang.Runnable
            public void run() {
                c.this.n();
            }
        }

        public b() {
        }

        @Override // java.util.TimerTask, java.lang.Runnable
        public void run() {
            c.h.a(c.T, " enableStatsEvents+++++++++ ");
            Handler handlerE = l.f().e();
            if (handlerE != null) {
                handlerE.post(new a());
            }
        }
    }

    /* renamed from: j.c$c, reason: collision with other inner class name */
    public static /* synthetic */ class C0463c {

        /* renamed from: a, reason: collision with root package name */
        public static final /* synthetic */ int[] f27435a;

        static {
            int[] iArr = new int[PeerConnection.IceConnectionState.values().length];
            f27435a = iArr;
            try {
                iArr[PeerConnection.IceConnectionState.NEW.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f27435a[PeerConnection.IceConnectionState.CHECKING.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f27435a[PeerConnection.IceConnectionState.CONNECTED.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f27435a[PeerConnection.IceConnectionState.COMPLETED.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f27435a[PeerConnection.IceConnectionState.FAILED.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                f27435a[PeerConnection.IceConnectionState.DISCONNECTED.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                f27435a[PeerConnection.IceConnectionState.CLOSED.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
        }
    }

    public enum d {
        NO_RESULT,
        ASCENDING,
        ALTERNATIVE
    }

    public enum e {
        NO_TARGET,
        RTT,
        LOST
    }

    public class f implements PeerConnection.Observer {
        public f() {
        }

        @Override // org.wrtca.api.PeerConnection.Observer
        public void onAddStream(MediaStream mediaStream) {
            List<VideoTrack> list = mediaStream.videoTracks;
            List<AudioTrack> list2 = mediaStream.audioTracks;
            c.h.a(c.T, "onAddStream vtasck size " + list.size() + " atrack size " + list2.size());
            if (c.this.f27407b != 1) {
                c.this.f27415j = list.get(0);
            } else if (list.size() > 1) {
                c.this.f27415j = list.get(1);
            } else if (list.size() > 0) {
                c.this.f27415j = list.get(0);
            }
            c.h.a(c.T, "videoTrack " + c.this.f27415j);
            if (list2.size() > 0) {
                c.this.f27416k = list2.get(0);
                c.this.f27416k.addSink(c.this);
            }
        }

        @Override // org.wrtca.api.PeerConnection.Observer
        public void onAddTrack(RtpReceiver rtpReceiver, MediaStream[] mediaStreamArr) {
        }

        @Override // org.wrtca.api.PeerConnection.Observer
        public void onDataChannel(DataChannel dataChannel) {
            c.h.a(c.T, "New Data channel " + dataChannel.label());
        }

        @Override // org.wrtca.api.PeerConnection.Observer
        public void onIceCandidate(IceCandidate iceCandidate) {
            c.h.a(c.T, " onIceCandidate" + iceCandidate.toString());
        }

        @Override // org.wrtca.api.PeerConnection.Observer
        public void onIceCandidatesRemoved(IceCandidate[] iceCandidateArr) {
        }

        @Override // org.wrtca.api.PeerConnection.Observer
        public void onIceConnectionChange(PeerConnection.IceConnectionState iceConnectionState) {
            int iOrdinal;
            synchronized (this) {
                c.h.a(c.T, " onIceConnectionChange: " + iceConnectionState);
                f.b bVar = f.b.LOGIC_ICE_STATE_NEW;
                int iOrdinal2 = bVar.ordinal();
                switch (C0463c.f27435a[iceConnectionState.ordinal()]) {
                    case 1:
                    case 2:
                        iOrdinal = bVar.ordinal();
                        iOrdinal2 = iOrdinal;
                        break;
                    case 3:
                        iOrdinal = f.b.LOGIC_ICE_STATE_CONNECTED.ordinal();
                        iOrdinal2 = iOrdinal;
                        break;
                    case 4:
                        iOrdinal = f.b.LOGIC_ICE_STATE_COMPLETE.ordinal();
                        iOrdinal2 = iOrdinal;
                        break;
                    case 5:
                        iOrdinal = f.b.LOGIC_ICE_STATE_FAILED.ordinal();
                        iOrdinal2 = iOrdinal;
                        break;
                    case 6:
                        iOrdinal = f.b.LOGIC_ICE_STATE_DISCONNECT.ordinal();
                        iOrdinal2 = iOrdinal;
                        break;
                    case 7:
                        iOrdinal = f.b.LOGIC_ICE_STATE_CLOSED.ordinal();
                        iOrdinal2 = iOrdinal;
                        break;
                }
                c.this.f27410e = iOrdinal2;
                if (iOrdinal2 == f.b.LOGIC_ICE_STATE_CONNECTED.ordinal() || iOrdinal2 == f.b.LOGIC_ICE_STATE_FAILED.ordinal()) {
                    c.this.f27420o.a(c.this.f27408c, c.this.f27406a, c.this.f27407b, iOrdinal2);
                }
            }
        }

        @Override // org.wrtca.api.PeerConnection.Observer
        public void onIceConnectionReceivingChange(boolean z2) {
            c.h.a(c.T, "IceConnectionReceiving changed to " + z2);
        }

        @Override // org.wrtca.api.PeerConnection.Observer
        public void onIceGatheringChange(PeerConnection.IceGatheringState iceGatheringState) {
            c.h.a(c.T, "IceGatheringState: " + iceGatheringState);
        }

        @Override // org.wrtca.api.PeerConnection.Observer
        public void onRemoveStream(MediaStream mediaStream) {
            c.h.a(c.T, "stream remove " + mediaStream.label());
            c.this.f27415j = null;
            c.this.f27416k = null;
        }

        @Override // org.wrtca.api.PeerConnection.Observer
        public void onRenegotiationNeeded() {
        }

        @Override // org.wrtca.api.PeerConnection.Observer
        public void onSignalingChange(PeerConnection.SignalingState signalingState) {
            c.h.a(c.T, "SignalingState: " + signalingState);
        }

        public /* synthetic */ f(c cVar, a aVar) {
            this();
        }
    }

    public interface g {
        void a(int i2, String str, int i3, int i4, int i5, int i6, int i7);

        void a(String str, int i2, int i3);

        void a(String str, int i2, int i3, int i4);

        void a(String str, int i2, int i3, String str2, int i4, int i5, String str3);

        void b(String str, int i2, int i3);

        void c(String str);
    }

    public static class h {

        /* renamed from: a, reason: collision with root package name */
        public boolean f27445a;

        /* renamed from: b, reason: collision with root package name */
        public int f27446b;

        /* renamed from: c, reason: collision with root package name */
        public d f27447c;

        /* renamed from: d, reason: collision with root package name */
        public boolean f27448d;

        public h(boolean z2, int i2) {
            this.f27445a = z2;
            this.f27446b = i2;
        }

        public void a(boolean z2) {
            this.f27448d = z2;
        }

        public void b(boolean z2) {
            this.f27445a = z2;
        }

        public void c() {
            this.f27446b++;
        }

        public boolean d() {
            return this.f27448d;
        }

        public boolean e() {
            return this.f27445a;
        }

        public void f() {
            this.f27446b = 9;
        }

        public String toString() {
            return "PeerNetCompareResult{pendingAscending=" + this.f27445a + ", times=" + this.f27446b + ", result=" + this.f27447c + ", interrupt=" + this.f27448d + '}';
        }

        public void a(int i2) {
            this.f27446b = i2;
        }

        public int b() {
            return this.f27446b;
        }

        public void a() {
            this.f27446b = 0;
            this.f27448d = false;
        }
    }

    public static class i {

        /* renamed from: a, reason: collision with root package name */
        public int f27449a;

        /* renamed from: b, reason: collision with root package name */
        public int f27450b;

        /* renamed from: c, reason: collision with root package name */
        public int f27451c;

        /* renamed from: d, reason: collision with root package name */
        public int f27452d;

        /* renamed from: e, reason: collision with root package name */
        public e f27453e = e.NO_TARGET;

        public i(int i2, int i3, int i4, int i5) {
            this.f27450b = i3;
            this.f27451c = i4;
            this.f27449a = i5;
            this.f27452d = i2;
        }

        public void a(int i2) {
            this.f27450b = i2;
        }

        public int b() {
            return this.f27450b;
        }

        public int c() {
            return this.f27451c;
        }

        public int d() {
            return this.f27449a;
        }

        public int e() {
            return this.f27452d;
        }

        public e f() {
            return this.f27453e;
        }

        public void g() {
            this.f27449a++;
        }

        public String toString() {
            return "PeerNetQuality{sampleSize=" + this.f27449a + ", delayOrRtt=" + this.f27450b + ", lost=" + this.f27451c + ", streamType=" + this.f27452d + ", target=" + this.f27453e + '}';
        }

        public void a() {
            this.f27449a = 0;
        }

        public void b(int i2) {
            this.f27451c = i2;
        }

        public void c(int i2) {
            this.f27449a = i2;
        }

        public void d(int i2) {
            this.f27452d = i2;
        }

        public void a(e eVar) {
            this.f27453e = eVar;
        }
    }

    public static class j {

        /* renamed from: a, reason: collision with root package name */
        public int f27454a;

        /* renamed from: b, reason: collision with root package name */
        public int f27455b;

        /* renamed from: d, reason: collision with root package name */
        public Queue<j.e> f27457d = new LinkedList();

        /* renamed from: c, reason: collision with root package name */
        public j.f f27456c = j.d.d().n();

        public j(int i2) {
            this.f27454a = i2;
        }

        public boolean a(j.e eVar) {
            if (this.f27457d.size() >= this.f27454a && this.f27457d.poll().a(this.f27456c)) {
                this.f27455b--;
            }
            if (eVar.a(this.f27456c)) {
                this.f27455b++;
            }
            return this.f27457d.offer(eVar);
        }
    }

    public class k implements SdpObserver {
        public k() {
        }

        @Override // org.wrtca.api.SdpObserver
        public void onCreateFailure(String str) {
            c.h.a(c.T, "onCreateFailure" + str);
        }

        @Override // org.wrtca.api.SdpObserver
        public void onCreateSuccess(SessionDescription sessionDescription) {
            String strB = sessionDescription.description;
            j.g gVar = c.this.f27417l;
            j.g gVar2 = j.g.U_RTC_INACTIVE;
            if (gVar != gVar2) {
                strB = c.b(strB, j.a.f27386o, true);
            }
            if (c.this.f27418m != gVar2) {
                strB = c.b(strB, j.a.f27381j, false);
            }
            c.this.f27420o.a(c.this.f27408c, c.this.f27406a, c.this.f27407b, sessionDescription.type.toString().toLowerCase(), c.this.f27422q, c.this.f27423r, strB);
        }

        @Override // org.wrtca.api.SdpObserver
        public void onSetFailure(String str) {
            c.h.a(c.T, " onSetFailure****************** " + str);
        }

        @Override // org.wrtca.api.SdpObserver
        public void onSetSuccess() {
            c.h.a(c.T, " onSetSuccess************** ");
        }

        public /* synthetic */ k(c cVar, a aVar) {
            this();
        }
    }

    public c(Context context, int i2, String str, g gVar, j.d dVar) {
        a aVar = null;
        this.f27409d = new f(this, aVar);
        this.f27411f = new k(this, aVar);
        if (context == null) {
            throw new NullPointerException("The application context is null");
        }
        this.f27422q = 0;
        this.f27423r = 0;
        this.f27424s = 0;
        this.f27415j = null;
        this.f27416k = null;
        this.f27406a = i2;
        this.f27408c = str;
        j.g gVar2 = j.g.U_RTC_INACTIVE;
        this.f27417l = gVar2;
        this.f27418m = gVar2;
        this.f27412g = context;
        this.f27419n = null;
        this.f27425t = new ConcurrentHashMap();
        this.f27420o = gVar;
        this.f27421p = dVar;
    }

    public int l() {
        int i2;
        synchronized (this) {
            i2 = this.f27410e;
        }
        return i2;
    }

    public int m() {
        return this.f27407b;
    }

    public final synchronized void n() {
        PeerConnection peerConnection = this.f27413h;
        if (peerConnection != null && this.f27414i != null) {
            if (!peerConnection.getStats(new a(), null)) {
                c.h.a(T, "getStats() returns false!");
            }
        }
    }

    public int o() {
        return this.f27406a;
    }

    @Override // org.wrtca.api.AudioSink
    public void onData(byte[] bArr, int i2, int i3, int i4, int i5) {
    }

    @Override // core.interfaces.FirstFrameRendered
    public void onFirstFrameRender(StreamInfo streamInfo, View view) {
        if (this.f27413h != null) {
            c.h.a(T, "peer receive onFirstFrameRender " + streamInfo);
        }
    }

    @Override // org.wrtca.api.AudioSink
    public void onVolLevel(int i2) {
        this.f27428w = i2;
        int i3 = this.f27406a;
        if (i3 == 1) {
            this.f27420o.a(this.f27408c, this.f27407b, i2);
        } else if (i3 == 2) {
            this.f27420o.b(this.f27408c, this.f27407b, i2);
        }
    }

    public Object p() {
        return null;
    }

    public synchronized void b() {
        c.h.a(T, T + "UnInitialize called");
        g();
    }

    public final void d() {
        MediaStream mediaStreamH;
        if (this.f27413h == null || this.f27406a != 1 || (mediaStreamH = j.d.d().h(j.a.f27379h)) == null) {
            return;
        }
        this.f27413h.addStream(mediaStreamH);
    }

    public final void e() {
        MediaStream mediaStreamH;
        if (this.f27413h == null || this.f27406a != 1 || (mediaStreamH = j.d.d().h(j.a.f27376e)) == null) {
            return;
        }
        List<AudioTrack> list = mediaStreamH.audioTracks;
        if (list.size() > 0) {
            AudioTrack audioTrack = list.get(0);
            this.f27416k = audioTrack;
            audioTrack.addSink(this);
        }
        c.h.a(T, " peerConnection.addStream ");
        this.f27413h.addStream(mediaStreamH);
    }

    public void f(boolean z2) {
        c.h.a(T, T + "stopRender needRelease： " + z2);
        if (this.f27425t.isEmpty() || this.f27415j == null) {
            return;
        }
        for (Object obj : this.f27425t.keySet()) {
            c.h.a(T, "stopRender callBack: " + obj + " VideoRenderer: " + this.f27425t.get(obj));
            boolean z3 = obj instanceof TextureViewRenderer;
            this.f27415j.removeRenderer((VideoRenderer) this.f27425t.get(obj));
            this.f27425t.remove(obj);
        }
    }

    public final void g() {
        Timer timer = this.f27414i;
        if (timer != null) {
            timer.cancel();
            this.f27414i = null;
        }
        AudioTrack audioTrack = this.f27416k;
        if (audioTrack != null) {
            audioTrack.removeSink(this);
            this.f27416k = null;
        }
        if (this.f27415j != null) {
            if (!this.f27425t.isEmpty()) {
                for (Object obj : this.f27425t.keySet()) {
                    c.h.a(T, "stopRender callBack: " + obj + " VideoRenderer: " + this.f27425t.get(obj));
                    this.f27415j.removeRenderer((VideoRenderer) this.f27425t.get(obj));
                    this.f27425t.remove(obj);
                }
                this.f27425t.clear();
                this.f27425t = null;
            }
            this.f27415j = null;
        }
        PeerConnection peerConnection = this.f27413h;
        if (peerConnection != null) {
            peerConnection.dispose();
            this.f27413h = null;
        }
        j.b bVar = this.f27419n;
        if (bVar != null) {
            bVar.a();
            this.f27419n = null;
        }
    }

    public final void h() throws FileNotFoundException {
        j.b bVar = new j.b(this.f27413h);
        this.f27419n = bVar;
        bVar.a(k());
    }

    public void i() {
        PeerConnection peerConnection = this.f27413h;
        if (peerConnection != null) {
            peerConnection.createAnswer(this.f27411f, null);
        }
    }

    public void j() {
        if (this.f27413h != null) {
            MediaConstraints mediaConstraints = new MediaConstraints();
            c.h.a(T, " createOffer " + this.f27418m + " " + this.f27417l);
            j.g gVar = this.f27418m;
            j.g gVar2 = j.g.U_RTC_RECV_ONLY;
            if (gVar == gVar2 || gVar == j.g.U_RTC_SEND_RECV) {
                mediaConstraints.mandatory.add(new MediaConstraints.KeyValuePair("OfferToReceiveVideo", k.a.f27523u));
            } else if (gVar == j.g.U_RTC_SEND_ONLY) {
                mediaConstraints.mandatory.add(new MediaConstraints.KeyValuePair("OfferToReceiveVideo", k.a.f27524v));
            } else {
                mediaConstraints.mandatory.add(new MediaConstraints.KeyValuePair("OfferToReceiveVideo", k.a.f27524v));
            }
            j.g gVar3 = this.f27417l;
            if (gVar3 == gVar2 || gVar3 == j.g.U_RTC_SEND_RECV) {
                mediaConstraints.mandatory.add(new MediaConstraints.KeyValuePair("OfferToReceiveAudio", k.a.f27523u));
            } else if (gVar3 == j.g.U_RTC_SEND_ONLY) {
                mediaConstraints.mandatory.add(new MediaConstraints.KeyValuePair("OfferToReceiveAudio", k.a.f27524v));
            } else {
                mediaConstraints.mandatory.add(new MediaConstraints.KeyValuePair("OfferToReceiveAudio", k.a.f27524v));
            }
            c.h.a(T, " createOffer " + mediaConstraints.toString());
            this.f27413h.createOffer(this.f27411f, mediaConstraints);
        }
    }

    public final File k() {
        return new File(j.a.F, "event_log_" + new SimpleDateFormat("yyyyMMdd_hhmm_ss", Locale.getDefault()).format(new Date()) + ".log");
    }

    public void c(boolean z2) {
        PeerConnection peerConnection = this.f27413h;
        if (peerConnection != null) {
            peerConnection.setAudioRecording(z2);
        }
    }

    public void b(SessionDescription sessionDescription) {
        c.h.a(T, " setRemoteDescription " + sessionDescription.description);
        this.f27413h.setRemoteDescription(this.f27411f, sessionDescription);
    }

    public void b(boolean z2) {
        PeerConnection peerConnection = this.f27413h;
        if (peerConnection != null) {
            peerConnection.setAudioPlayout(z2);
        }
    }

    public void d(boolean z2) {
        AudioTrack audioTrack = this.f27416k;
        if (audioTrack != null) {
            audioTrack.setEnabled(z2);
        }
    }

    public void a(int i2, boolean z2) {
        c.h.a(T, "updateRemoteStates: trackType- " + i2 + " mute: " + z2);
        if (i2 == 2) {
            this.f27427v = z2;
        } else if (i2 == 1) {
            this.f27426u = z2;
        }
    }

    public static String b(String str, String str2, boolean z2) {
        String[] strArrSplit = str.split("\r\n");
        int iA = a(z2, strArrSplit);
        if (iA == -1) {
            c.h.e(T, "No mediaDescription line, so can't prefer " + str2);
            return str;
        }
        ArrayList arrayList = new ArrayList();
        Pattern patternCompile = Pattern.compile("^a=rtpmap:(\\d+) " + str2 + "(/\\d+)+[\r]?$");
        for (String str3 : strArrSplit) {
            Matcher matcher = patternCompile.matcher(str3);
            if (matcher.matches()) {
                arrayList.add(matcher.group(1));
            }
        }
        if (arrayList.isEmpty()) {
            c.h.e(T, "No payload types with name " + str2);
            return str;
        }
        String strA = a(arrayList, strArrSplit[iA]);
        if (strA == null) {
            return str;
        }
        c.h.a(T, "Change media description from: " + strArrSplit[iA] + " to " + strA);
        strArrSplit[iA] = strA;
        return a((Iterable<? extends CharSequence>) Arrays.asList(strArrSplit), "\r\n", true);
    }

    public boolean a(int i2, boolean z2, boolean z3) {
        this.f27407b = i2;
        a(false);
        if (this.f27406a == 1) {
            if (i2 == 1) {
                e();
            } else if (i2 == 2) {
                d();
            }
            if (z2) {
                this.f27418m = j.g.U_RTC_SEND_ONLY;
            }
            if (z3) {
                this.f27417l = j.g.U_RTC_SEND_ONLY;
            }
        } else {
            if (z2) {
                this.f27418m = j.g.U_RTC_RECV_ONLY;
            }
            if (z3) {
                this.f27417l = j.g.U_RTC_RECV_ONLY;
            }
        }
        return this.f27413h != null;
    }

    public final void f() {
        Timer timer = this.f27414i;
        if (timer != null) {
            timer.cancel();
            this.f27414i = null;
        }
        AudioTrack audioTrack = this.f27416k;
        if (audioTrack != null) {
            audioTrack.removeSink(this);
            this.f27416k = null;
        }
        PeerConnection peerConnection = this.f27413h;
        if (peerConnection != null) {
            peerConnection.disconnect();
        }
        j.b bVar = this.f27419n;
        if (bVar != null) {
            bVar.a();
            this.f27419n = null;
        }
    }

    public void e(boolean z2) {
        VideoTrack videoTrack = this.f27415j;
        if (videoTrack != null) {
            videoTrack.setEnabled(z2);
        }
    }

    public synchronized void a() {
        c.h.a(T, T + "Disconnect called");
        f();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:146:0x0335 A[Catch: Exception -> 0x0b53, TryCatch #4 {Exception -> 0x0b53, blocks: (B:7:0x001a, B:12:0x007d, B:14:0x008b, B:16:0x0099, B:18:0x009f, B:20:0x00ad, B:22:0x00b7, B:24:0x00bd, B:26:0x00c9, B:28:0x00d3, B:29:0x00e2, B:32:0x00fe, B:34:0x0106, B:36:0x0112, B:38:0x0120, B:40:0x0126, B:41:0x012c, B:43:0x0136, B:45:0x013c, B:46:0x0142, B:48:0x014c, B:50:0x0152, B:51:0x0158, B:53:0x0162, B:55:0x0168, B:56:0x016e, B:58:0x0184, B:60:0x0195, B:62:0x019a, B:64:0x01a0, B:66:0x01a6, B:67:0x01a8, B:69:0x01de, B:71:0x01e4, B:73:0x01eb, B:75:0x01f5, B:77:0x01fb, B:80:0x0203, B:82:0x020b, B:84:0x0218, B:86:0x021e, B:88:0x0224, B:90:0x022e, B:92:0x0234, B:95:0x023c, B:97:0x0240, B:100:0x0246, B:102:0x024a, B:106:0x0252, B:109:0x026a, B:111:0x026e, B:112:0x0270, B:114:0x027a, B:116:0x0280, B:118:0x0288, B:120:0x0294, B:122:0x0298, B:414:0x0891, B:125:0x02ee, B:127:0x02f6, B:129:0x0303, B:131:0x0309, B:133:0x030f, B:135:0x0319, B:137:0x031f, B:140:0x0327, B:142:0x032d, B:146:0x0335, B:148:0x033b, B:153:0x0345, B:156:0x035d, B:158:0x0361, B:159:0x0363, B:161:0x036d, B:163:0x0373, B:165:0x037b, B:167:0x0387, B:169:0x038b, B:171:0x0395, B:173:0x039b, B:175:0x03a2, B:178:0x03dc, B:180:0x03e4, B:182:0x03f1, B:184:0x03f7, B:187:0x03ff, B:189:0x0405, B:191:0x040b, B:192:0x040d, B:194:0x0417, B:196:0x041d, B:199:0x0425, B:201:0x0429, B:204:0x042f, B:206:0x0433, B:210:0x043b, B:213:0x044f, B:215:0x0453, B:216:0x0455, B:218:0x045f, B:220:0x0465, B:222:0x046d, B:224:0x0479, B:226:0x047d, B:59:0x018f, B:232:0x04dd, B:234:0x04f2, B:236:0x04fa, B:238:0x0506, B:240:0x0514, B:242:0x051a, B:243:0x0520, B:245:0x052a, B:247:0x0530, B:248:0x0536, B:250:0x0540, B:252:0x0546, B:253:0x054c, B:255:0x0556, B:257:0x055c, B:258:0x0562, B:260:0x057a, B:262:0x0581, B:264:0x0595, B:267:0x059d, B:269:0x05a5, B:271:0x05b5, B:273:0x05bb, B:276:0x05c3, B:278:0x05c9, B:280:0x05cf, B:281:0x05d1, B:283:0x05db, B:285:0x05e1, B:288:0x05e9, B:290:0x05ef, B:294:0x05f7, B:296:0x05fd, B:301:0x0607, B:304:0x061b, B:306:0x061f, B:307:0x0621, B:309:0x0627, B:310:0x062d, B:312:0x0637, B:314:0x063d, B:316:0x0645, B:318:0x0651, B:320:0x0655, B:322:0x065f, B:324:0x0665, B:325:0x0669, B:327:0x0675, B:328:0x0679, B:330:0x0685, B:331:0x0689, B:332:0x06f8, B:334:0x0704, B:336:0x0714, B:338:0x071a, B:341:0x0722, B:343:0x0728, B:345:0x072e, B:346:0x0730, B:348:0x073a, B:350:0x0740, B:353:0x0748, B:355:0x074e, B:359:0x0756, B:361:0x075c, B:366:0x0766, B:369:0x077e, B:371:0x0782, B:372:0x0784, B:374:0x078a, B:375:0x0790, B:377:0x079a, B:379:0x07a0, B:381:0x07a8, B:383:0x07b4, B:385:0x07b8, B:387:0x07c2, B:388:0x07c6, B:390:0x07d2, B:392:0x07d8, B:393:0x07dc, B:395:0x07e8, B:397:0x07ee, B:399:0x07f5, B:401:0x07ff, B:403:0x0805, B:405:0x0810, B:415:0x089f, B:417:0x08af, B:419:0x08bd, B:420:0x08c2, B:422:0x08c7, B:424:0x08cd, B:426:0x08d3, B:428:0x08df, B:430:0x08ef, B:432:0x08fd, B:435:0x0906, B:437:0x090c, B:439:0x0914, B:442:0x091b, B:444:0x0921, B:445:0x0928, B:446:0x092e, B:447:0x0934, B:449:0x0943, B:450:0x0949, B:530:0x0b1d, B:532:0x0b3d, B:534:0x0b49, B:529:0x0b12), top: B:545:0x001a }] */
    /* JADX WARN: Removed duplicated region for block: B:149:0x033e  */
    /* JADX WARN: Removed duplicated region for block: B:152:0x0343 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:154:0x0354  */
    /* JADX WARN: Removed duplicated region for block: B:156:0x035d A[Catch: Exception -> 0x0b53, TryCatch #4 {Exception -> 0x0b53, blocks: (B:7:0x001a, B:12:0x007d, B:14:0x008b, B:16:0x0099, B:18:0x009f, B:20:0x00ad, B:22:0x00b7, B:24:0x00bd, B:26:0x00c9, B:28:0x00d3, B:29:0x00e2, B:32:0x00fe, B:34:0x0106, B:36:0x0112, B:38:0x0120, B:40:0x0126, B:41:0x012c, B:43:0x0136, B:45:0x013c, B:46:0x0142, B:48:0x014c, B:50:0x0152, B:51:0x0158, B:53:0x0162, B:55:0x0168, B:56:0x016e, B:58:0x0184, B:60:0x0195, B:62:0x019a, B:64:0x01a0, B:66:0x01a6, B:67:0x01a8, B:69:0x01de, B:71:0x01e4, B:73:0x01eb, B:75:0x01f5, B:77:0x01fb, B:80:0x0203, B:82:0x020b, B:84:0x0218, B:86:0x021e, B:88:0x0224, B:90:0x022e, B:92:0x0234, B:95:0x023c, B:97:0x0240, B:100:0x0246, B:102:0x024a, B:106:0x0252, B:109:0x026a, B:111:0x026e, B:112:0x0270, B:114:0x027a, B:116:0x0280, B:118:0x0288, B:120:0x0294, B:122:0x0298, B:414:0x0891, B:125:0x02ee, B:127:0x02f6, B:129:0x0303, B:131:0x0309, B:133:0x030f, B:135:0x0319, B:137:0x031f, B:140:0x0327, B:142:0x032d, B:146:0x0335, B:148:0x033b, B:153:0x0345, B:156:0x035d, B:158:0x0361, B:159:0x0363, B:161:0x036d, B:163:0x0373, B:165:0x037b, B:167:0x0387, B:169:0x038b, B:171:0x0395, B:173:0x039b, B:175:0x03a2, B:178:0x03dc, B:180:0x03e4, B:182:0x03f1, B:184:0x03f7, B:187:0x03ff, B:189:0x0405, B:191:0x040b, B:192:0x040d, B:194:0x0417, B:196:0x041d, B:199:0x0425, B:201:0x0429, B:204:0x042f, B:206:0x0433, B:210:0x043b, B:213:0x044f, B:215:0x0453, B:216:0x0455, B:218:0x045f, B:220:0x0465, B:222:0x046d, B:224:0x0479, B:226:0x047d, B:59:0x018f, B:232:0x04dd, B:234:0x04f2, B:236:0x04fa, B:238:0x0506, B:240:0x0514, B:242:0x051a, B:243:0x0520, B:245:0x052a, B:247:0x0530, B:248:0x0536, B:250:0x0540, B:252:0x0546, B:253:0x054c, B:255:0x0556, B:257:0x055c, B:258:0x0562, B:260:0x057a, B:262:0x0581, B:264:0x0595, B:267:0x059d, B:269:0x05a5, B:271:0x05b5, B:273:0x05bb, B:276:0x05c3, B:278:0x05c9, B:280:0x05cf, B:281:0x05d1, B:283:0x05db, B:285:0x05e1, B:288:0x05e9, B:290:0x05ef, B:294:0x05f7, B:296:0x05fd, B:301:0x0607, B:304:0x061b, B:306:0x061f, B:307:0x0621, B:309:0x0627, B:310:0x062d, B:312:0x0637, B:314:0x063d, B:316:0x0645, B:318:0x0651, B:320:0x0655, B:322:0x065f, B:324:0x0665, B:325:0x0669, B:327:0x0675, B:328:0x0679, B:330:0x0685, B:331:0x0689, B:332:0x06f8, B:334:0x0704, B:336:0x0714, B:338:0x071a, B:341:0x0722, B:343:0x0728, B:345:0x072e, B:346:0x0730, B:348:0x073a, B:350:0x0740, B:353:0x0748, B:355:0x074e, B:359:0x0756, B:361:0x075c, B:366:0x0766, B:369:0x077e, B:371:0x0782, B:372:0x0784, B:374:0x078a, B:375:0x0790, B:377:0x079a, B:379:0x07a0, B:381:0x07a8, B:383:0x07b4, B:385:0x07b8, B:387:0x07c2, B:388:0x07c6, B:390:0x07d2, B:392:0x07d8, B:393:0x07dc, B:395:0x07e8, B:397:0x07ee, B:399:0x07f5, B:401:0x07ff, B:403:0x0805, B:405:0x0810, B:415:0x089f, B:417:0x08af, B:419:0x08bd, B:420:0x08c2, B:422:0x08c7, B:424:0x08cd, B:426:0x08d3, B:428:0x08df, B:430:0x08ef, B:432:0x08fd, B:435:0x0906, B:437:0x090c, B:439:0x0914, B:442:0x091b, B:444:0x0921, B:445:0x0928, B:446:0x092e, B:447:0x0934, B:449:0x0943, B:450:0x0949, B:530:0x0b1d, B:532:0x0b3d, B:534:0x0b49, B:529:0x0b12), top: B:545:0x001a }] */
    /* JADX WARN: Removed duplicated region for block: B:158:0x0361 A[Catch: Exception -> 0x0b53, TryCatch #4 {Exception -> 0x0b53, blocks: (B:7:0x001a, B:12:0x007d, B:14:0x008b, B:16:0x0099, B:18:0x009f, B:20:0x00ad, B:22:0x00b7, B:24:0x00bd, B:26:0x00c9, B:28:0x00d3, B:29:0x00e2, B:32:0x00fe, B:34:0x0106, B:36:0x0112, B:38:0x0120, B:40:0x0126, B:41:0x012c, B:43:0x0136, B:45:0x013c, B:46:0x0142, B:48:0x014c, B:50:0x0152, B:51:0x0158, B:53:0x0162, B:55:0x0168, B:56:0x016e, B:58:0x0184, B:60:0x0195, B:62:0x019a, B:64:0x01a0, B:66:0x01a6, B:67:0x01a8, B:69:0x01de, B:71:0x01e4, B:73:0x01eb, B:75:0x01f5, B:77:0x01fb, B:80:0x0203, B:82:0x020b, B:84:0x0218, B:86:0x021e, B:88:0x0224, B:90:0x022e, B:92:0x0234, B:95:0x023c, B:97:0x0240, B:100:0x0246, B:102:0x024a, B:106:0x0252, B:109:0x026a, B:111:0x026e, B:112:0x0270, B:114:0x027a, B:116:0x0280, B:118:0x0288, B:120:0x0294, B:122:0x0298, B:414:0x0891, B:125:0x02ee, B:127:0x02f6, B:129:0x0303, B:131:0x0309, B:133:0x030f, B:135:0x0319, B:137:0x031f, B:140:0x0327, B:142:0x032d, B:146:0x0335, B:148:0x033b, B:153:0x0345, B:156:0x035d, B:158:0x0361, B:159:0x0363, B:161:0x036d, B:163:0x0373, B:165:0x037b, B:167:0x0387, B:169:0x038b, B:171:0x0395, B:173:0x039b, B:175:0x03a2, B:178:0x03dc, B:180:0x03e4, B:182:0x03f1, B:184:0x03f7, B:187:0x03ff, B:189:0x0405, B:191:0x040b, B:192:0x040d, B:194:0x0417, B:196:0x041d, B:199:0x0425, B:201:0x0429, B:204:0x042f, B:206:0x0433, B:210:0x043b, B:213:0x044f, B:215:0x0453, B:216:0x0455, B:218:0x045f, B:220:0x0465, B:222:0x046d, B:224:0x0479, B:226:0x047d, B:59:0x018f, B:232:0x04dd, B:234:0x04f2, B:236:0x04fa, B:238:0x0506, B:240:0x0514, B:242:0x051a, B:243:0x0520, B:245:0x052a, B:247:0x0530, B:248:0x0536, B:250:0x0540, B:252:0x0546, B:253:0x054c, B:255:0x0556, B:257:0x055c, B:258:0x0562, B:260:0x057a, B:262:0x0581, B:264:0x0595, B:267:0x059d, B:269:0x05a5, B:271:0x05b5, B:273:0x05bb, B:276:0x05c3, B:278:0x05c9, B:280:0x05cf, B:281:0x05d1, B:283:0x05db, B:285:0x05e1, B:288:0x05e9, B:290:0x05ef, B:294:0x05f7, B:296:0x05fd, B:301:0x0607, B:304:0x061b, B:306:0x061f, B:307:0x0621, B:309:0x0627, B:310:0x062d, B:312:0x0637, B:314:0x063d, B:316:0x0645, B:318:0x0651, B:320:0x0655, B:322:0x065f, B:324:0x0665, B:325:0x0669, B:327:0x0675, B:328:0x0679, B:330:0x0685, B:331:0x0689, B:332:0x06f8, B:334:0x0704, B:336:0x0714, B:338:0x071a, B:341:0x0722, B:343:0x0728, B:345:0x072e, B:346:0x0730, B:348:0x073a, B:350:0x0740, B:353:0x0748, B:355:0x074e, B:359:0x0756, B:361:0x075c, B:366:0x0766, B:369:0x077e, B:371:0x0782, B:372:0x0784, B:374:0x078a, B:375:0x0790, B:377:0x079a, B:379:0x07a0, B:381:0x07a8, B:383:0x07b4, B:385:0x07b8, B:387:0x07c2, B:388:0x07c6, B:390:0x07d2, B:392:0x07d8, B:393:0x07dc, B:395:0x07e8, B:397:0x07ee, B:399:0x07f5, B:401:0x07ff, B:403:0x0805, B:405:0x0810, B:415:0x089f, B:417:0x08af, B:419:0x08bd, B:420:0x08c2, B:422:0x08c7, B:424:0x08cd, B:426:0x08d3, B:428:0x08df, B:430:0x08ef, B:432:0x08fd, B:435:0x0906, B:437:0x090c, B:439:0x0914, B:442:0x091b, B:444:0x0921, B:445:0x0928, B:446:0x092e, B:447:0x0934, B:449:0x0943, B:450:0x0949, B:530:0x0b1d, B:532:0x0b3d, B:534:0x0b49, B:529:0x0b12), top: B:545:0x001a }] */
    /* JADX WARN: Removed duplicated region for block: B:168:0x038a  */
    /* JADX WARN: Removed duplicated region for block: B:174:0x03a0  */
    /* JADX WARN: Removed duplicated region for block: B:294:0x05f7 A[Catch: Exception -> 0x0b53, TryCatch #4 {Exception -> 0x0b53, blocks: (B:7:0x001a, B:12:0x007d, B:14:0x008b, B:16:0x0099, B:18:0x009f, B:20:0x00ad, B:22:0x00b7, B:24:0x00bd, B:26:0x00c9, B:28:0x00d3, B:29:0x00e2, B:32:0x00fe, B:34:0x0106, B:36:0x0112, B:38:0x0120, B:40:0x0126, B:41:0x012c, B:43:0x0136, B:45:0x013c, B:46:0x0142, B:48:0x014c, B:50:0x0152, B:51:0x0158, B:53:0x0162, B:55:0x0168, B:56:0x016e, B:58:0x0184, B:60:0x0195, B:62:0x019a, B:64:0x01a0, B:66:0x01a6, B:67:0x01a8, B:69:0x01de, B:71:0x01e4, B:73:0x01eb, B:75:0x01f5, B:77:0x01fb, B:80:0x0203, B:82:0x020b, B:84:0x0218, B:86:0x021e, B:88:0x0224, B:90:0x022e, B:92:0x0234, B:95:0x023c, B:97:0x0240, B:100:0x0246, B:102:0x024a, B:106:0x0252, B:109:0x026a, B:111:0x026e, B:112:0x0270, B:114:0x027a, B:116:0x0280, B:118:0x0288, B:120:0x0294, B:122:0x0298, B:414:0x0891, B:125:0x02ee, B:127:0x02f6, B:129:0x0303, B:131:0x0309, B:133:0x030f, B:135:0x0319, B:137:0x031f, B:140:0x0327, B:142:0x032d, B:146:0x0335, B:148:0x033b, B:153:0x0345, B:156:0x035d, B:158:0x0361, B:159:0x0363, B:161:0x036d, B:163:0x0373, B:165:0x037b, B:167:0x0387, B:169:0x038b, B:171:0x0395, B:173:0x039b, B:175:0x03a2, B:178:0x03dc, B:180:0x03e4, B:182:0x03f1, B:184:0x03f7, B:187:0x03ff, B:189:0x0405, B:191:0x040b, B:192:0x040d, B:194:0x0417, B:196:0x041d, B:199:0x0425, B:201:0x0429, B:204:0x042f, B:206:0x0433, B:210:0x043b, B:213:0x044f, B:215:0x0453, B:216:0x0455, B:218:0x045f, B:220:0x0465, B:222:0x046d, B:224:0x0479, B:226:0x047d, B:59:0x018f, B:232:0x04dd, B:234:0x04f2, B:236:0x04fa, B:238:0x0506, B:240:0x0514, B:242:0x051a, B:243:0x0520, B:245:0x052a, B:247:0x0530, B:248:0x0536, B:250:0x0540, B:252:0x0546, B:253:0x054c, B:255:0x0556, B:257:0x055c, B:258:0x0562, B:260:0x057a, B:262:0x0581, B:264:0x0595, B:267:0x059d, B:269:0x05a5, B:271:0x05b5, B:273:0x05bb, B:276:0x05c3, B:278:0x05c9, B:280:0x05cf, B:281:0x05d1, B:283:0x05db, B:285:0x05e1, B:288:0x05e9, B:290:0x05ef, B:294:0x05f7, B:296:0x05fd, B:301:0x0607, B:304:0x061b, B:306:0x061f, B:307:0x0621, B:309:0x0627, B:310:0x062d, B:312:0x0637, B:314:0x063d, B:316:0x0645, B:318:0x0651, B:320:0x0655, B:322:0x065f, B:324:0x0665, B:325:0x0669, B:327:0x0675, B:328:0x0679, B:330:0x0685, B:331:0x0689, B:332:0x06f8, B:334:0x0704, B:336:0x0714, B:338:0x071a, B:341:0x0722, B:343:0x0728, B:345:0x072e, B:346:0x0730, B:348:0x073a, B:350:0x0740, B:353:0x0748, B:355:0x074e, B:359:0x0756, B:361:0x075c, B:366:0x0766, B:369:0x077e, B:371:0x0782, B:372:0x0784, B:374:0x078a, B:375:0x0790, B:377:0x079a, B:379:0x07a0, B:381:0x07a8, B:383:0x07b4, B:385:0x07b8, B:387:0x07c2, B:388:0x07c6, B:390:0x07d2, B:392:0x07d8, B:393:0x07dc, B:395:0x07e8, B:397:0x07ee, B:399:0x07f5, B:401:0x07ff, B:403:0x0805, B:405:0x0810, B:415:0x089f, B:417:0x08af, B:419:0x08bd, B:420:0x08c2, B:422:0x08c7, B:424:0x08cd, B:426:0x08d3, B:428:0x08df, B:430:0x08ef, B:432:0x08fd, B:435:0x0906, B:437:0x090c, B:439:0x0914, B:442:0x091b, B:444:0x0921, B:445:0x0928, B:446:0x092e, B:447:0x0934, B:449:0x0943, B:450:0x0949, B:530:0x0b1d, B:532:0x0b3d, B:534:0x0b49, B:529:0x0b12), top: B:545:0x001a }] */
    /* JADX WARN: Removed duplicated region for block: B:297:0x0600  */
    /* JADX WARN: Removed duplicated region for block: B:300:0x0605 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:302:0x0614  */
    /* JADX WARN: Removed duplicated region for block: B:304:0x061b A[Catch: Exception -> 0x0b53, TryCatch #4 {Exception -> 0x0b53, blocks: (B:7:0x001a, B:12:0x007d, B:14:0x008b, B:16:0x0099, B:18:0x009f, B:20:0x00ad, B:22:0x00b7, B:24:0x00bd, B:26:0x00c9, B:28:0x00d3, B:29:0x00e2, B:32:0x00fe, B:34:0x0106, B:36:0x0112, B:38:0x0120, B:40:0x0126, B:41:0x012c, B:43:0x0136, B:45:0x013c, B:46:0x0142, B:48:0x014c, B:50:0x0152, B:51:0x0158, B:53:0x0162, B:55:0x0168, B:56:0x016e, B:58:0x0184, B:60:0x0195, B:62:0x019a, B:64:0x01a0, B:66:0x01a6, B:67:0x01a8, B:69:0x01de, B:71:0x01e4, B:73:0x01eb, B:75:0x01f5, B:77:0x01fb, B:80:0x0203, B:82:0x020b, B:84:0x0218, B:86:0x021e, B:88:0x0224, B:90:0x022e, B:92:0x0234, B:95:0x023c, B:97:0x0240, B:100:0x0246, B:102:0x024a, B:106:0x0252, B:109:0x026a, B:111:0x026e, B:112:0x0270, B:114:0x027a, B:116:0x0280, B:118:0x0288, B:120:0x0294, B:122:0x0298, B:414:0x0891, B:125:0x02ee, B:127:0x02f6, B:129:0x0303, B:131:0x0309, B:133:0x030f, B:135:0x0319, B:137:0x031f, B:140:0x0327, B:142:0x032d, B:146:0x0335, B:148:0x033b, B:153:0x0345, B:156:0x035d, B:158:0x0361, B:159:0x0363, B:161:0x036d, B:163:0x0373, B:165:0x037b, B:167:0x0387, B:169:0x038b, B:171:0x0395, B:173:0x039b, B:175:0x03a2, B:178:0x03dc, B:180:0x03e4, B:182:0x03f1, B:184:0x03f7, B:187:0x03ff, B:189:0x0405, B:191:0x040b, B:192:0x040d, B:194:0x0417, B:196:0x041d, B:199:0x0425, B:201:0x0429, B:204:0x042f, B:206:0x0433, B:210:0x043b, B:213:0x044f, B:215:0x0453, B:216:0x0455, B:218:0x045f, B:220:0x0465, B:222:0x046d, B:224:0x0479, B:226:0x047d, B:59:0x018f, B:232:0x04dd, B:234:0x04f2, B:236:0x04fa, B:238:0x0506, B:240:0x0514, B:242:0x051a, B:243:0x0520, B:245:0x052a, B:247:0x0530, B:248:0x0536, B:250:0x0540, B:252:0x0546, B:253:0x054c, B:255:0x0556, B:257:0x055c, B:258:0x0562, B:260:0x057a, B:262:0x0581, B:264:0x0595, B:267:0x059d, B:269:0x05a5, B:271:0x05b5, B:273:0x05bb, B:276:0x05c3, B:278:0x05c9, B:280:0x05cf, B:281:0x05d1, B:283:0x05db, B:285:0x05e1, B:288:0x05e9, B:290:0x05ef, B:294:0x05f7, B:296:0x05fd, B:301:0x0607, B:304:0x061b, B:306:0x061f, B:307:0x0621, B:309:0x0627, B:310:0x062d, B:312:0x0637, B:314:0x063d, B:316:0x0645, B:318:0x0651, B:320:0x0655, B:322:0x065f, B:324:0x0665, B:325:0x0669, B:327:0x0675, B:328:0x0679, B:330:0x0685, B:331:0x0689, B:332:0x06f8, B:334:0x0704, B:336:0x0714, B:338:0x071a, B:341:0x0722, B:343:0x0728, B:345:0x072e, B:346:0x0730, B:348:0x073a, B:350:0x0740, B:353:0x0748, B:355:0x074e, B:359:0x0756, B:361:0x075c, B:366:0x0766, B:369:0x077e, B:371:0x0782, B:372:0x0784, B:374:0x078a, B:375:0x0790, B:377:0x079a, B:379:0x07a0, B:381:0x07a8, B:383:0x07b4, B:385:0x07b8, B:387:0x07c2, B:388:0x07c6, B:390:0x07d2, B:392:0x07d8, B:393:0x07dc, B:395:0x07e8, B:397:0x07ee, B:399:0x07f5, B:401:0x07ff, B:403:0x0805, B:405:0x0810, B:415:0x089f, B:417:0x08af, B:419:0x08bd, B:420:0x08c2, B:422:0x08c7, B:424:0x08cd, B:426:0x08d3, B:428:0x08df, B:430:0x08ef, B:432:0x08fd, B:435:0x0906, B:437:0x090c, B:439:0x0914, B:442:0x091b, B:444:0x0921, B:445:0x0928, B:446:0x092e, B:447:0x0934, B:449:0x0943, B:450:0x0949, B:530:0x0b1d, B:532:0x0b3d, B:534:0x0b49, B:529:0x0b12), top: B:545:0x001a }] */
    /* JADX WARN: Removed duplicated region for block: B:306:0x061f A[Catch: Exception -> 0x0b53, TryCatch #4 {Exception -> 0x0b53, blocks: (B:7:0x001a, B:12:0x007d, B:14:0x008b, B:16:0x0099, B:18:0x009f, B:20:0x00ad, B:22:0x00b7, B:24:0x00bd, B:26:0x00c9, B:28:0x00d3, B:29:0x00e2, B:32:0x00fe, B:34:0x0106, B:36:0x0112, B:38:0x0120, B:40:0x0126, B:41:0x012c, B:43:0x0136, B:45:0x013c, B:46:0x0142, B:48:0x014c, B:50:0x0152, B:51:0x0158, B:53:0x0162, B:55:0x0168, B:56:0x016e, B:58:0x0184, B:60:0x0195, B:62:0x019a, B:64:0x01a0, B:66:0x01a6, B:67:0x01a8, B:69:0x01de, B:71:0x01e4, B:73:0x01eb, B:75:0x01f5, B:77:0x01fb, B:80:0x0203, B:82:0x020b, B:84:0x0218, B:86:0x021e, B:88:0x0224, B:90:0x022e, B:92:0x0234, B:95:0x023c, B:97:0x0240, B:100:0x0246, B:102:0x024a, B:106:0x0252, B:109:0x026a, B:111:0x026e, B:112:0x0270, B:114:0x027a, B:116:0x0280, B:118:0x0288, B:120:0x0294, B:122:0x0298, B:414:0x0891, B:125:0x02ee, B:127:0x02f6, B:129:0x0303, B:131:0x0309, B:133:0x030f, B:135:0x0319, B:137:0x031f, B:140:0x0327, B:142:0x032d, B:146:0x0335, B:148:0x033b, B:153:0x0345, B:156:0x035d, B:158:0x0361, B:159:0x0363, B:161:0x036d, B:163:0x0373, B:165:0x037b, B:167:0x0387, B:169:0x038b, B:171:0x0395, B:173:0x039b, B:175:0x03a2, B:178:0x03dc, B:180:0x03e4, B:182:0x03f1, B:184:0x03f7, B:187:0x03ff, B:189:0x0405, B:191:0x040b, B:192:0x040d, B:194:0x0417, B:196:0x041d, B:199:0x0425, B:201:0x0429, B:204:0x042f, B:206:0x0433, B:210:0x043b, B:213:0x044f, B:215:0x0453, B:216:0x0455, B:218:0x045f, B:220:0x0465, B:222:0x046d, B:224:0x0479, B:226:0x047d, B:59:0x018f, B:232:0x04dd, B:234:0x04f2, B:236:0x04fa, B:238:0x0506, B:240:0x0514, B:242:0x051a, B:243:0x0520, B:245:0x052a, B:247:0x0530, B:248:0x0536, B:250:0x0540, B:252:0x0546, B:253:0x054c, B:255:0x0556, B:257:0x055c, B:258:0x0562, B:260:0x057a, B:262:0x0581, B:264:0x0595, B:267:0x059d, B:269:0x05a5, B:271:0x05b5, B:273:0x05bb, B:276:0x05c3, B:278:0x05c9, B:280:0x05cf, B:281:0x05d1, B:283:0x05db, B:285:0x05e1, B:288:0x05e9, B:290:0x05ef, B:294:0x05f7, B:296:0x05fd, B:301:0x0607, B:304:0x061b, B:306:0x061f, B:307:0x0621, B:309:0x0627, B:310:0x062d, B:312:0x0637, B:314:0x063d, B:316:0x0645, B:318:0x0651, B:320:0x0655, B:322:0x065f, B:324:0x0665, B:325:0x0669, B:327:0x0675, B:328:0x0679, B:330:0x0685, B:331:0x0689, B:332:0x06f8, B:334:0x0704, B:336:0x0714, B:338:0x071a, B:341:0x0722, B:343:0x0728, B:345:0x072e, B:346:0x0730, B:348:0x073a, B:350:0x0740, B:353:0x0748, B:355:0x074e, B:359:0x0756, B:361:0x075c, B:366:0x0766, B:369:0x077e, B:371:0x0782, B:372:0x0784, B:374:0x078a, B:375:0x0790, B:377:0x079a, B:379:0x07a0, B:381:0x07a8, B:383:0x07b4, B:385:0x07b8, B:387:0x07c2, B:388:0x07c6, B:390:0x07d2, B:392:0x07d8, B:393:0x07dc, B:395:0x07e8, B:397:0x07ee, B:399:0x07f5, B:401:0x07ff, B:403:0x0805, B:405:0x0810, B:415:0x089f, B:417:0x08af, B:419:0x08bd, B:420:0x08c2, B:422:0x08c7, B:424:0x08cd, B:426:0x08d3, B:428:0x08df, B:430:0x08ef, B:432:0x08fd, B:435:0x0906, B:437:0x090c, B:439:0x0914, B:442:0x091b, B:444:0x0921, B:445:0x0928, B:446:0x092e, B:447:0x0934, B:449:0x0943, B:450:0x0949, B:530:0x0b1d, B:532:0x0b3d, B:534:0x0b49, B:529:0x0b12), top: B:545:0x001a }] */
    /* JADX WARN: Removed duplicated region for block: B:309:0x0627 A[Catch: Exception -> 0x0b53, TryCatch #4 {Exception -> 0x0b53, blocks: (B:7:0x001a, B:12:0x007d, B:14:0x008b, B:16:0x0099, B:18:0x009f, B:20:0x00ad, B:22:0x00b7, B:24:0x00bd, B:26:0x00c9, B:28:0x00d3, B:29:0x00e2, B:32:0x00fe, B:34:0x0106, B:36:0x0112, B:38:0x0120, B:40:0x0126, B:41:0x012c, B:43:0x0136, B:45:0x013c, B:46:0x0142, B:48:0x014c, B:50:0x0152, B:51:0x0158, B:53:0x0162, B:55:0x0168, B:56:0x016e, B:58:0x0184, B:60:0x0195, B:62:0x019a, B:64:0x01a0, B:66:0x01a6, B:67:0x01a8, B:69:0x01de, B:71:0x01e4, B:73:0x01eb, B:75:0x01f5, B:77:0x01fb, B:80:0x0203, B:82:0x020b, B:84:0x0218, B:86:0x021e, B:88:0x0224, B:90:0x022e, B:92:0x0234, B:95:0x023c, B:97:0x0240, B:100:0x0246, B:102:0x024a, B:106:0x0252, B:109:0x026a, B:111:0x026e, B:112:0x0270, B:114:0x027a, B:116:0x0280, B:118:0x0288, B:120:0x0294, B:122:0x0298, B:414:0x0891, B:125:0x02ee, B:127:0x02f6, B:129:0x0303, B:131:0x0309, B:133:0x030f, B:135:0x0319, B:137:0x031f, B:140:0x0327, B:142:0x032d, B:146:0x0335, B:148:0x033b, B:153:0x0345, B:156:0x035d, B:158:0x0361, B:159:0x0363, B:161:0x036d, B:163:0x0373, B:165:0x037b, B:167:0x0387, B:169:0x038b, B:171:0x0395, B:173:0x039b, B:175:0x03a2, B:178:0x03dc, B:180:0x03e4, B:182:0x03f1, B:184:0x03f7, B:187:0x03ff, B:189:0x0405, B:191:0x040b, B:192:0x040d, B:194:0x0417, B:196:0x041d, B:199:0x0425, B:201:0x0429, B:204:0x042f, B:206:0x0433, B:210:0x043b, B:213:0x044f, B:215:0x0453, B:216:0x0455, B:218:0x045f, B:220:0x0465, B:222:0x046d, B:224:0x0479, B:226:0x047d, B:59:0x018f, B:232:0x04dd, B:234:0x04f2, B:236:0x04fa, B:238:0x0506, B:240:0x0514, B:242:0x051a, B:243:0x0520, B:245:0x052a, B:247:0x0530, B:248:0x0536, B:250:0x0540, B:252:0x0546, B:253:0x054c, B:255:0x0556, B:257:0x055c, B:258:0x0562, B:260:0x057a, B:262:0x0581, B:264:0x0595, B:267:0x059d, B:269:0x05a5, B:271:0x05b5, B:273:0x05bb, B:276:0x05c3, B:278:0x05c9, B:280:0x05cf, B:281:0x05d1, B:283:0x05db, B:285:0x05e1, B:288:0x05e9, B:290:0x05ef, B:294:0x05f7, B:296:0x05fd, B:301:0x0607, B:304:0x061b, B:306:0x061f, B:307:0x0621, B:309:0x0627, B:310:0x062d, B:312:0x0637, B:314:0x063d, B:316:0x0645, B:318:0x0651, B:320:0x0655, B:322:0x065f, B:324:0x0665, B:325:0x0669, B:327:0x0675, B:328:0x0679, B:330:0x0685, B:331:0x0689, B:332:0x06f8, B:334:0x0704, B:336:0x0714, B:338:0x071a, B:341:0x0722, B:343:0x0728, B:345:0x072e, B:346:0x0730, B:348:0x073a, B:350:0x0740, B:353:0x0748, B:355:0x074e, B:359:0x0756, B:361:0x075c, B:366:0x0766, B:369:0x077e, B:371:0x0782, B:372:0x0784, B:374:0x078a, B:375:0x0790, B:377:0x079a, B:379:0x07a0, B:381:0x07a8, B:383:0x07b4, B:385:0x07b8, B:387:0x07c2, B:388:0x07c6, B:390:0x07d2, B:392:0x07d8, B:393:0x07dc, B:395:0x07e8, B:397:0x07ee, B:399:0x07f5, B:401:0x07ff, B:403:0x0805, B:405:0x0810, B:415:0x089f, B:417:0x08af, B:419:0x08bd, B:420:0x08c2, B:422:0x08c7, B:424:0x08cd, B:426:0x08d3, B:428:0x08df, B:430:0x08ef, B:432:0x08fd, B:435:0x0906, B:437:0x090c, B:439:0x0914, B:442:0x091b, B:444:0x0921, B:445:0x0928, B:446:0x092e, B:447:0x0934, B:449:0x0943, B:450:0x0949, B:530:0x0b1d, B:532:0x0b3d, B:534:0x0b49, B:529:0x0b12), top: B:545:0x001a }] */
    /* JADX WARN: Removed duplicated region for block: B:319:0x0654  */
    /* JADX WARN: Removed duplicated region for block: B:327:0x0675 A[Catch: Exception -> 0x0b53, TryCatch #4 {Exception -> 0x0b53, blocks: (B:7:0x001a, B:12:0x007d, B:14:0x008b, B:16:0x0099, B:18:0x009f, B:20:0x00ad, B:22:0x00b7, B:24:0x00bd, B:26:0x00c9, B:28:0x00d3, B:29:0x00e2, B:32:0x00fe, B:34:0x0106, B:36:0x0112, B:38:0x0120, B:40:0x0126, B:41:0x012c, B:43:0x0136, B:45:0x013c, B:46:0x0142, B:48:0x014c, B:50:0x0152, B:51:0x0158, B:53:0x0162, B:55:0x0168, B:56:0x016e, B:58:0x0184, B:60:0x0195, B:62:0x019a, B:64:0x01a0, B:66:0x01a6, B:67:0x01a8, B:69:0x01de, B:71:0x01e4, B:73:0x01eb, B:75:0x01f5, B:77:0x01fb, B:80:0x0203, B:82:0x020b, B:84:0x0218, B:86:0x021e, B:88:0x0224, B:90:0x022e, B:92:0x0234, B:95:0x023c, B:97:0x0240, B:100:0x0246, B:102:0x024a, B:106:0x0252, B:109:0x026a, B:111:0x026e, B:112:0x0270, B:114:0x027a, B:116:0x0280, B:118:0x0288, B:120:0x0294, B:122:0x0298, B:414:0x0891, B:125:0x02ee, B:127:0x02f6, B:129:0x0303, B:131:0x0309, B:133:0x030f, B:135:0x0319, B:137:0x031f, B:140:0x0327, B:142:0x032d, B:146:0x0335, B:148:0x033b, B:153:0x0345, B:156:0x035d, B:158:0x0361, B:159:0x0363, B:161:0x036d, B:163:0x0373, B:165:0x037b, B:167:0x0387, B:169:0x038b, B:171:0x0395, B:173:0x039b, B:175:0x03a2, B:178:0x03dc, B:180:0x03e4, B:182:0x03f1, B:184:0x03f7, B:187:0x03ff, B:189:0x0405, B:191:0x040b, B:192:0x040d, B:194:0x0417, B:196:0x041d, B:199:0x0425, B:201:0x0429, B:204:0x042f, B:206:0x0433, B:210:0x043b, B:213:0x044f, B:215:0x0453, B:216:0x0455, B:218:0x045f, B:220:0x0465, B:222:0x046d, B:224:0x0479, B:226:0x047d, B:59:0x018f, B:232:0x04dd, B:234:0x04f2, B:236:0x04fa, B:238:0x0506, B:240:0x0514, B:242:0x051a, B:243:0x0520, B:245:0x052a, B:247:0x0530, B:248:0x0536, B:250:0x0540, B:252:0x0546, B:253:0x054c, B:255:0x0556, B:257:0x055c, B:258:0x0562, B:260:0x057a, B:262:0x0581, B:264:0x0595, B:267:0x059d, B:269:0x05a5, B:271:0x05b5, B:273:0x05bb, B:276:0x05c3, B:278:0x05c9, B:280:0x05cf, B:281:0x05d1, B:283:0x05db, B:285:0x05e1, B:288:0x05e9, B:290:0x05ef, B:294:0x05f7, B:296:0x05fd, B:301:0x0607, B:304:0x061b, B:306:0x061f, B:307:0x0621, B:309:0x0627, B:310:0x062d, B:312:0x0637, B:314:0x063d, B:316:0x0645, B:318:0x0651, B:320:0x0655, B:322:0x065f, B:324:0x0665, B:325:0x0669, B:327:0x0675, B:328:0x0679, B:330:0x0685, B:331:0x0689, B:332:0x06f8, B:334:0x0704, B:336:0x0714, B:338:0x071a, B:341:0x0722, B:343:0x0728, B:345:0x072e, B:346:0x0730, B:348:0x073a, B:350:0x0740, B:353:0x0748, B:355:0x074e, B:359:0x0756, B:361:0x075c, B:366:0x0766, B:369:0x077e, B:371:0x0782, B:372:0x0784, B:374:0x078a, B:375:0x0790, B:377:0x079a, B:379:0x07a0, B:381:0x07a8, B:383:0x07b4, B:385:0x07b8, B:387:0x07c2, B:388:0x07c6, B:390:0x07d2, B:392:0x07d8, B:393:0x07dc, B:395:0x07e8, B:397:0x07ee, B:399:0x07f5, B:401:0x07ff, B:403:0x0805, B:405:0x0810, B:415:0x089f, B:417:0x08af, B:419:0x08bd, B:420:0x08c2, B:422:0x08c7, B:424:0x08cd, B:426:0x08d3, B:428:0x08df, B:430:0x08ef, B:432:0x08fd, B:435:0x0906, B:437:0x090c, B:439:0x0914, B:442:0x091b, B:444:0x0921, B:445:0x0928, B:446:0x092e, B:447:0x0934, B:449:0x0943, B:450:0x0949, B:530:0x0b1d, B:532:0x0b3d, B:534:0x0b49, B:529:0x0b12), top: B:545:0x001a }] */
    /* JADX WARN: Removed duplicated region for block: B:330:0x0685 A[Catch: Exception -> 0x0b53, TryCatch #4 {Exception -> 0x0b53, blocks: (B:7:0x001a, B:12:0x007d, B:14:0x008b, B:16:0x0099, B:18:0x009f, B:20:0x00ad, B:22:0x00b7, B:24:0x00bd, B:26:0x00c9, B:28:0x00d3, B:29:0x00e2, B:32:0x00fe, B:34:0x0106, B:36:0x0112, B:38:0x0120, B:40:0x0126, B:41:0x012c, B:43:0x0136, B:45:0x013c, B:46:0x0142, B:48:0x014c, B:50:0x0152, B:51:0x0158, B:53:0x0162, B:55:0x0168, B:56:0x016e, B:58:0x0184, B:60:0x0195, B:62:0x019a, B:64:0x01a0, B:66:0x01a6, B:67:0x01a8, B:69:0x01de, B:71:0x01e4, B:73:0x01eb, B:75:0x01f5, B:77:0x01fb, B:80:0x0203, B:82:0x020b, B:84:0x0218, B:86:0x021e, B:88:0x0224, B:90:0x022e, B:92:0x0234, B:95:0x023c, B:97:0x0240, B:100:0x0246, B:102:0x024a, B:106:0x0252, B:109:0x026a, B:111:0x026e, B:112:0x0270, B:114:0x027a, B:116:0x0280, B:118:0x0288, B:120:0x0294, B:122:0x0298, B:414:0x0891, B:125:0x02ee, B:127:0x02f6, B:129:0x0303, B:131:0x0309, B:133:0x030f, B:135:0x0319, B:137:0x031f, B:140:0x0327, B:142:0x032d, B:146:0x0335, B:148:0x033b, B:153:0x0345, B:156:0x035d, B:158:0x0361, B:159:0x0363, B:161:0x036d, B:163:0x0373, B:165:0x037b, B:167:0x0387, B:169:0x038b, B:171:0x0395, B:173:0x039b, B:175:0x03a2, B:178:0x03dc, B:180:0x03e4, B:182:0x03f1, B:184:0x03f7, B:187:0x03ff, B:189:0x0405, B:191:0x040b, B:192:0x040d, B:194:0x0417, B:196:0x041d, B:199:0x0425, B:201:0x0429, B:204:0x042f, B:206:0x0433, B:210:0x043b, B:213:0x044f, B:215:0x0453, B:216:0x0455, B:218:0x045f, B:220:0x0465, B:222:0x046d, B:224:0x0479, B:226:0x047d, B:59:0x018f, B:232:0x04dd, B:234:0x04f2, B:236:0x04fa, B:238:0x0506, B:240:0x0514, B:242:0x051a, B:243:0x0520, B:245:0x052a, B:247:0x0530, B:248:0x0536, B:250:0x0540, B:252:0x0546, B:253:0x054c, B:255:0x0556, B:257:0x055c, B:258:0x0562, B:260:0x057a, B:262:0x0581, B:264:0x0595, B:267:0x059d, B:269:0x05a5, B:271:0x05b5, B:273:0x05bb, B:276:0x05c3, B:278:0x05c9, B:280:0x05cf, B:281:0x05d1, B:283:0x05db, B:285:0x05e1, B:288:0x05e9, B:290:0x05ef, B:294:0x05f7, B:296:0x05fd, B:301:0x0607, B:304:0x061b, B:306:0x061f, B:307:0x0621, B:309:0x0627, B:310:0x062d, B:312:0x0637, B:314:0x063d, B:316:0x0645, B:318:0x0651, B:320:0x0655, B:322:0x065f, B:324:0x0665, B:325:0x0669, B:327:0x0675, B:328:0x0679, B:330:0x0685, B:331:0x0689, B:332:0x06f8, B:334:0x0704, B:336:0x0714, B:338:0x071a, B:341:0x0722, B:343:0x0728, B:345:0x072e, B:346:0x0730, B:348:0x073a, B:350:0x0740, B:353:0x0748, B:355:0x074e, B:359:0x0756, B:361:0x075c, B:366:0x0766, B:369:0x077e, B:371:0x0782, B:372:0x0784, B:374:0x078a, B:375:0x0790, B:377:0x079a, B:379:0x07a0, B:381:0x07a8, B:383:0x07b4, B:385:0x07b8, B:387:0x07c2, B:388:0x07c6, B:390:0x07d2, B:392:0x07d8, B:393:0x07dc, B:395:0x07e8, B:397:0x07ee, B:399:0x07f5, B:401:0x07ff, B:403:0x0805, B:405:0x0810, B:415:0x089f, B:417:0x08af, B:419:0x08bd, B:420:0x08c2, B:422:0x08c7, B:424:0x08cd, B:426:0x08d3, B:428:0x08df, B:430:0x08ef, B:432:0x08fd, B:435:0x0906, B:437:0x090c, B:439:0x0914, B:442:0x091b, B:444:0x0921, B:445:0x0928, B:446:0x092e, B:447:0x0934, B:449:0x0943, B:450:0x0949, B:530:0x0b1d, B:532:0x0b3d, B:534:0x0b49, B:529:0x0b12), top: B:545:0x001a }] */
    /* JADX WARN: Removed duplicated region for block: B:359:0x0756 A[Catch: Exception -> 0x0b53, TryCatch #4 {Exception -> 0x0b53, blocks: (B:7:0x001a, B:12:0x007d, B:14:0x008b, B:16:0x0099, B:18:0x009f, B:20:0x00ad, B:22:0x00b7, B:24:0x00bd, B:26:0x00c9, B:28:0x00d3, B:29:0x00e2, B:32:0x00fe, B:34:0x0106, B:36:0x0112, B:38:0x0120, B:40:0x0126, B:41:0x012c, B:43:0x0136, B:45:0x013c, B:46:0x0142, B:48:0x014c, B:50:0x0152, B:51:0x0158, B:53:0x0162, B:55:0x0168, B:56:0x016e, B:58:0x0184, B:60:0x0195, B:62:0x019a, B:64:0x01a0, B:66:0x01a6, B:67:0x01a8, B:69:0x01de, B:71:0x01e4, B:73:0x01eb, B:75:0x01f5, B:77:0x01fb, B:80:0x0203, B:82:0x020b, B:84:0x0218, B:86:0x021e, B:88:0x0224, B:90:0x022e, B:92:0x0234, B:95:0x023c, B:97:0x0240, B:100:0x0246, B:102:0x024a, B:106:0x0252, B:109:0x026a, B:111:0x026e, B:112:0x0270, B:114:0x027a, B:116:0x0280, B:118:0x0288, B:120:0x0294, B:122:0x0298, B:414:0x0891, B:125:0x02ee, B:127:0x02f6, B:129:0x0303, B:131:0x0309, B:133:0x030f, B:135:0x0319, B:137:0x031f, B:140:0x0327, B:142:0x032d, B:146:0x0335, B:148:0x033b, B:153:0x0345, B:156:0x035d, B:158:0x0361, B:159:0x0363, B:161:0x036d, B:163:0x0373, B:165:0x037b, B:167:0x0387, B:169:0x038b, B:171:0x0395, B:173:0x039b, B:175:0x03a2, B:178:0x03dc, B:180:0x03e4, B:182:0x03f1, B:184:0x03f7, B:187:0x03ff, B:189:0x0405, B:191:0x040b, B:192:0x040d, B:194:0x0417, B:196:0x041d, B:199:0x0425, B:201:0x0429, B:204:0x042f, B:206:0x0433, B:210:0x043b, B:213:0x044f, B:215:0x0453, B:216:0x0455, B:218:0x045f, B:220:0x0465, B:222:0x046d, B:224:0x0479, B:226:0x047d, B:59:0x018f, B:232:0x04dd, B:234:0x04f2, B:236:0x04fa, B:238:0x0506, B:240:0x0514, B:242:0x051a, B:243:0x0520, B:245:0x052a, B:247:0x0530, B:248:0x0536, B:250:0x0540, B:252:0x0546, B:253:0x054c, B:255:0x0556, B:257:0x055c, B:258:0x0562, B:260:0x057a, B:262:0x0581, B:264:0x0595, B:267:0x059d, B:269:0x05a5, B:271:0x05b5, B:273:0x05bb, B:276:0x05c3, B:278:0x05c9, B:280:0x05cf, B:281:0x05d1, B:283:0x05db, B:285:0x05e1, B:288:0x05e9, B:290:0x05ef, B:294:0x05f7, B:296:0x05fd, B:301:0x0607, B:304:0x061b, B:306:0x061f, B:307:0x0621, B:309:0x0627, B:310:0x062d, B:312:0x0637, B:314:0x063d, B:316:0x0645, B:318:0x0651, B:320:0x0655, B:322:0x065f, B:324:0x0665, B:325:0x0669, B:327:0x0675, B:328:0x0679, B:330:0x0685, B:331:0x0689, B:332:0x06f8, B:334:0x0704, B:336:0x0714, B:338:0x071a, B:341:0x0722, B:343:0x0728, B:345:0x072e, B:346:0x0730, B:348:0x073a, B:350:0x0740, B:353:0x0748, B:355:0x074e, B:359:0x0756, B:361:0x075c, B:366:0x0766, B:369:0x077e, B:371:0x0782, B:372:0x0784, B:374:0x078a, B:375:0x0790, B:377:0x079a, B:379:0x07a0, B:381:0x07a8, B:383:0x07b4, B:385:0x07b8, B:387:0x07c2, B:388:0x07c6, B:390:0x07d2, B:392:0x07d8, B:393:0x07dc, B:395:0x07e8, B:397:0x07ee, B:399:0x07f5, B:401:0x07ff, B:403:0x0805, B:405:0x0810, B:415:0x089f, B:417:0x08af, B:419:0x08bd, B:420:0x08c2, B:422:0x08c7, B:424:0x08cd, B:426:0x08d3, B:428:0x08df, B:430:0x08ef, B:432:0x08fd, B:435:0x0906, B:437:0x090c, B:439:0x0914, B:442:0x091b, B:444:0x0921, B:445:0x0928, B:446:0x092e, B:447:0x0934, B:449:0x0943, B:450:0x0949, B:530:0x0b1d, B:532:0x0b3d, B:534:0x0b49, B:529:0x0b12), top: B:545:0x001a }] */
    /* JADX WARN: Removed duplicated region for block: B:362:0x075f  */
    /* JADX WARN: Removed duplicated region for block: B:365:0x0764 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:367:0x0775  */
    /* JADX WARN: Removed duplicated region for block: B:369:0x077e A[Catch: Exception -> 0x0b53, TryCatch #4 {Exception -> 0x0b53, blocks: (B:7:0x001a, B:12:0x007d, B:14:0x008b, B:16:0x0099, B:18:0x009f, B:20:0x00ad, B:22:0x00b7, B:24:0x00bd, B:26:0x00c9, B:28:0x00d3, B:29:0x00e2, B:32:0x00fe, B:34:0x0106, B:36:0x0112, B:38:0x0120, B:40:0x0126, B:41:0x012c, B:43:0x0136, B:45:0x013c, B:46:0x0142, B:48:0x014c, B:50:0x0152, B:51:0x0158, B:53:0x0162, B:55:0x0168, B:56:0x016e, B:58:0x0184, B:60:0x0195, B:62:0x019a, B:64:0x01a0, B:66:0x01a6, B:67:0x01a8, B:69:0x01de, B:71:0x01e4, B:73:0x01eb, B:75:0x01f5, B:77:0x01fb, B:80:0x0203, B:82:0x020b, B:84:0x0218, B:86:0x021e, B:88:0x0224, B:90:0x022e, B:92:0x0234, B:95:0x023c, B:97:0x0240, B:100:0x0246, B:102:0x024a, B:106:0x0252, B:109:0x026a, B:111:0x026e, B:112:0x0270, B:114:0x027a, B:116:0x0280, B:118:0x0288, B:120:0x0294, B:122:0x0298, B:414:0x0891, B:125:0x02ee, B:127:0x02f6, B:129:0x0303, B:131:0x0309, B:133:0x030f, B:135:0x0319, B:137:0x031f, B:140:0x0327, B:142:0x032d, B:146:0x0335, B:148:0x033b, B:153:0x0345, B:156:0x035d, B:158:0x0361, B:159:0x0363, B:161:0x036d, B:163:0x0373, B:165:0x037b, B:167:0x0387, B:169:0x038b, B:171:0x0395, B:173:0x039b, B:175:0x03a2, B:178:0x03dc, B:180:0x03e4, B:182:0x03f1, B:184:0x03f7, B:187:0x03ff, B:189:0x0405, B:191:0x040b, B:192:0x040d, B:194:0x0417, B:196:0x041d, B:199:0x0425, B:201:0x0429, B:204:0x042f, B:206:0x0433, B:210:0x043b, B:213:0x044f, B:215:0x0453, B:216:0x0455, B:218:0x045f, B:220:0x0465, B:222:0x046d, B:224:0x0479, B:226:0x047d, B:59:0x018f, B:232:0x04dd, B:234:0x04f2, B:236:0x04fa, B:238:0x0506, B:240:0x0514, B:242:0x051a, B:243:0x0520, B:245:0x052a, B:247:0x0530, B:248:0x0536, B:250:0x0540, B:252:0x0546, B:253:0x054c, B:255:0x0556, B:257:0x055c, B:258:0x0562, B:260:0x057a, B:262:0x0581, B:264:0x0595, B:267:0x059d, B:269:0x05a5, B:271:0x05b5, B:273:0x05bb, B:276:0x05c3, B:278:0x05c9, B:280:0x05cf, B:281:0x05d1, B:283:0x05db, B:285:0x05e1, B:288:0x05e9, B:290:0x05ef, B:294:0x05f7, B:296:0x05fd, B:301:0x0607, B:304:0x061b, B:306:0x061f, B:307:0x0621, B:309:0x0627, B:310:0x062d, B:312:0x0637, B:314:0x063d, B:316:0x0645, B:318:0x0651, B:320:0x0655, B:322:0x065f, B:324:0x0665, B:325:0x0669, B:327:0x0675, B:328:0x0679, B:330:0x0685, B:331:0x0689, B:332:0x06f8, B:334:0x0704, B:336:0x0714, B:338:0x071a, B:341:0x0722, B:343:0x0728, B:345:0x072e, B:346:0x0730, B:348:0x073a, B:350:0x0740, B:353:0x0748, B:355:0x074e, B:359:0x0756, B:361:0x075c, B:366:0x0766, B:369:0x077e, B:371:0x0782, B:372:0x0784, B:374:0x078a, B:375:0x0790, B:377:0x079a, B:379:0x07a0, B:381:0x07a8, B:383:0x07b4, B:385:0x07b8, B:387:0x07c2, B:388:0x07c6, B:390:0x07d2, B:392:0x07d8, B:393:0x07dc, B:395:0x07e8, B:397:0x07ee, B:399:0x07f5, B:401:0x07ff, B:403:0x0805, B:405:0x0810, B:415:0x089f, B:417:0x08af, B:419:0x08bd, B:420:0x08c2, B:422:0x08c7, B:424:0x08cd, B:426:0x08d3, B:428:0x08df, B:430:0x08ef, B:432:0x08fd, B:435:0x0906, B:437:0x090c, B:439:0x0914, B:442:0x091b, B:444:0x0921, B:445:0x0928, B:446:0x092e, B:447:0x0934, B:449:0x0943, B:450:0x0949, B:530:0x0b1d, B:532:0x0b3d, B:534:0x0b49, B:529:0x0b12), top: B:545:0x001a }] */
    /* JADX WARN: Removed duplicated region for block: B:371:0x0782 A[Catch: Exception -> 0x0b53, TryCatch #4 {Exception -> 0x0b53, blocks: (B:7:0x001a, B:12:0x007d, B:14:0x008b, B:16:0x0099, B:18:0x009f, B:20:0x00ad, B:22:0x00b7, B:24:0x00bd, B:26:0x00c9, B:28:0x00d3, B:29:0x00e2, B:32:0x00fe, B:34:0x0106, B:36:0x0112, B:38:0x0120, B:40:0x0126, B:41:0x012c, B:43:0x0136, B:45:0x013c, B:46:0x0142, B:48:0x014c, B:50:0x0152, B:51:0x0158, B:53:0x0162, B:55:0x0168, B:56:0x016e, B:58:0x0184, B:60:0x0195, B:62:0x019a, B:64:0x01a0, B:66:0x01a6, B:67:0x01a8, B:69:0x01de, B:71:0x01e4, B:73:0x01eb, B:75:0x01f5, B:77:0x01fb, B:80:0x0203, B:82:0x020b, B:84:0x0218, B:86:0x021e, B:88:0x0224, B:90:0x022e, B:92:0x0234, B:95:0x023c, B:97:0x0240, B:100:0x0246, B:102:0x024a, B:106:0x0252, B:109:0x026a, B:111:0x026e, B:112:0x0270, B:114:0x027a, B:116:0x0280, B:118:0x0288, B:120:0x0294, B:122:0x0298, B:414:0x0891, B:125:0x02ee, B:127:0x02f6, B:129:0x0303, B:131:0x0309, B:133:0x030f, B:135:0x0319, B:137:0x031f, B:140:0x0327, B:142:0x032d, B:146:0x0335, B:148:0x033b, B:153:0x0345, B:156:0x035d, B:158:0x0361, B:159:0x0363, B:161:0x036d, B:163:0x0373, B:165:0x037b, B:167:0x0387, B:169:0x038b, B:171:0x0395, B:173:0x039b, B:175:0x03a2, B:178:0x03dc, B:180:0x03e4, B:182:0x03f1, B:184:0x03f7, B:187:0x03ff, B:189:0x0405, B:191:0x040b, B:192:0x040d, B:194:0x0417, B:196:0x041d, B:199:0x0425, B:201:0x0429, B:204:0x042f, B:206:0x0433, B:210:0x043b, B:213:0x044f, B:215:0x0453, B:216:0x0455, B:218:0x045f, B:220:0x0465, B:222:0x046d, B:224:0x0479, B:226:0x047d, B:59:0x018f, B:232:0x04dd, B:234:0x04f2, B:236:0x04fa, B:238:0x0506, B:240:0x0514, B:242:0x051a, B:243:0x0520, B:245:0x052a, B:247:0x0530, B:248:0x0536, B:250:0x0540, B:252:0x0546, B:253:0x054c, B:255:0x0556, B:257:0x055c, B:258:0x0562, B:260:0x057a, B:262:0x0581, B:264:0x0595, B:267:0x059d, B:269:0x05a5, B:271:0x05b5, B:273:0x05bb, B:276:0x05c3, B:278:0x05c9, B:280:0x05cf, B:281:0x05d1, B:283:0x05db, B:285:0x05e1, B:288:0x05e9, B:290:0x05ef, B:294:0x05f7, B:296:0x05fd, B:301:0x0607, B:304:0x061b, B:306:0x061f, B:307:0x0621, B:309:0x0627, B:310:0x062d, B:312:0x0637, B:314:0x063d, B:316:0x0645, B:318:0x0651, B:320:0x0655, B:322:0x065f, B:324:0x0665, B:325:0x0669, B:327:0x0675, B:328:0x0679, B:330:0x0685, B:331:0x0689, B:332:0x06f8, B:334:0x0704, B:336:0x0714, B:338:0x071a, B:341:0x0722, B:343:0x0728, B:345:0x072e, B:346:0x0730, B:348:0x073a, B:350:0x0740, B:353:0x0748, B:355:0x074e, B:359:0x0756, B:361:0x075c, B:366:0x0766, B:369:0x077e, B:371:0x0782, B:372:0x0784, B:374:0x078a, B:375:0x0790, B:377:0x079a, B:379:0x07a0, B:381:0x07a8, B:383:0x07b4, B:385:0x07b8, B:387:0x07c2, B:388:0x07c6, B:390:0x07d2, B:392:0x07d8, B:393:0x07dc, B:395:0x07e8, B:397:0x07ee, B:399:0x07f5, B:401:0x07ff, B:403:0x0805, B:405:0x0810, B:415:0x089f, B:417:0x08af, B:419:0x08bd, B:420:0x08c2, B:422:0x08c7, B:424:0x08cd, B:426:0x08d3, B:428:0x08df, B:430:0x08ef, B:432:0x08fd, B:435:0x0906, B:437:0x090c, B:439:0x0914, B:442:0x091b, B:444:0x0921, B:445:0x0928, B:446:0x092e, B:447:0x0934, B:449:0x0943, B:450:0x0949, B:530:0x0b1d, B:532:0x0b3d, B:534:0x0b49, B:529:0x0b12), top: B:545:0x001a }] */
    /* JADX WARN: Removed duplicated region for block: B:374:0x078a A[Catch: Exception -> 0x0b53, TryCatch #4 {Exception -> 0x0b53, blocks: (B:7:0x001a, B:12:0x007d, B:14:0x008b, B:16:0x0099, B:18:0x009f, B:20:0x00ad, B:22:0x00b7, B:24:0x00bd, B:26:0x00c9, B:28:0x00d3, B:29:0x00e2, B:32:0x00fe, B:34:0x0106, B:36:0x0112, B:38:0x0120, B:40:0x0126, B:41:0x012c, B:43:0x0136, B:45:0x013c, B:46:0x0142, B:48:0x014c, B:50:0x0152, B:51:0x0158, B:53:0x0162, B:55:0x0168, B:56:0x016e, B:58:0x0184, B:60:0x0195, B:62:0x019a, B:64:0x01a0, B:66:0x01a6, B:67:0x01a8, B:69:0x01de, B:71:0x01e4, B:73:0x01eb, B:75:0x01f5, B:77:0x01fb, B:80:0x0203, B:82:0x020b, B:84:0x0218, B:86:0x021e, B:88:0x0224, B:90:0x022e, B:92:0x0234, B:95:0x023c, B:97:0x0240, B:100:0x0246, B:102:0x024a, B:106:0x0252, B:109:0x026a, B:111:0x026e, B:112:0x0270, B:114:0x027a, B:116:0x0280, B:118:0x0288, B:120:0x0294, B:122:0x0298, B:414:0x0891, B:125:0x02ee, B:127:0x02f6, B:129:0x0303, B:131:0x0309, B:133:0x030f, B:135:0x0319, B:137:0x031f, B:140:0x0327, B:142:0x032d, B:146:0x0335, B:148:0x033b, B:153:0x0345, B:156:0x035d, B:158:0x0361, B:159:0x0363, B:161:0x036d, B:163:0x0373, B:165:0x037b, B:167:0x0387, B:169:0x038b, B:171:0x0395, B:173:0x039b, B:175:0x03a2, B:178:0x03dc, B:180:0x03e4, B:182:0x03f1, B:184:0x03f7, B:187:0x03ff, B:189:0x0405, B:191:0x040b, B:192:0x040d, B:194:0x0417, B:196:0x041d, B:199:0x0425, B:201:0x0429, B:204:0x042f, B:206:0x0433, B:210:0x043b, B:213:0x044f, B:215:0x0453, B:216:0x0455, B:218:0x045f, B:220:0x0465, B:222:0x046d, B:224:0x0479, B:226:0x047d, B:59:0x018f, B:232:0x04dd, B:234:0x04f2, B:236:0x04fa, B:238:0x0506, B:240:0x0514, B:242:0x051a, B:243:0x0520, B:245:0x052a, B:247:0x0530, B:248:0x0536, B:250:0x0540, B:252:0x0546, B:253:0x054c, B:255:0x0556, B:257:0x055c, B:258:0x0562, B:260:0x057a, B:262:0x0581, B:264:0x0595, B:267:0x059d, B:269:0x05a5, B:271:0x05b5, B:273:0x05bb, B:276:0x05c3, B:278:0x05c9, B:280:0x05cf, B:281:0x05d1, B:283:0x05db, B:285:0x05e1, B:288:0x05e9, B:290:0x05ef, B:294:0x05f7, B:296:0x05fd, B:301:0x0607, B:304:0x061b, B:306:0x061f, B:307:0x0621, B:309:0x0627, B:310:0x062d, B:312:0x0637, B:314:0x063d, B:316:0x0645, B:318:0x0651, B:320:0x0655, B:322:0x065f, B:324:0x0665, B:325:0x0669, B:327:0x0675, B:328:0x0679, B:330:0x0685, B:331:0x0689, B:332:0x06f8, B:334:0x0704, B:336:0x0714, B:338:0x071a, B:341:0x0722, B:343:0x0728, B:345:0x072e, B:346:0x0730, B:348:0x073a, B:350:0x0740, B:353:0x0748, B:355:0x074e, B:359:0x0756, B:361:0x075c, B:366:0x0766, B:369:0x077e, B:371:0x0782, B:372:0x0784, B:374:0x078a, B:375:0x0790, B:377:0x079a, B:379:0x07a0, B:381:0x07a8, B:383:0x07b4, B:385:0x07b8, B:387:0x07c2, B:388:0x07c6, B:390:0x07d2, B:392:0x07d8, B:393:0x07dc, B:395:0x07e8, B:397:0x07ee, B:399:0x07f5, B:401:0x07ff, B:403:0x0805, B:405:0x0810, B:415:0x089f, B:417:0x08af, B:419:0x08bd, B:420:0x08c2, B:422:0x08c7, B:424:0x08cd, B:426:0x08d3, B:428:0x08df, B:430:0x08ef, B:432:0x08fd, B:435:0x0906, B:437:0x090c, B:439:0x0914, B:442:0x091b, B:444:0x0921, B:445:0x0928, B:446:0x092e, B:447:0x0934, B:449:0x0943, B:450:0x0949, B:530:0x0b1d, B:532:0x0b3d, B:534:0x0b49, B:529:0x0b12), top: B:545:0x001a }] */
    /* JADX WARN: Removed duplicated region for block: B:384:0x07b7  */
    /* JADX WARN: Removed duplicated region for block: B:387:0x07c2 A[Catch: Exception -> 0x0b53, TryCatch #4 {Exception -> 0x0b53, blocks: (B:7:0x001a, B:12:0x007d, B:14:0x008b, B:16:0x0099, B:18:0x009f, B:20:0x00ad, B:22:0x00b7, B:24:0x00bd, B:26:0x00c9, B:28:0x00d3, B:29:0x00e2, B:32:0x00fe, B:34:0x0106, B:36:0x0112, B:38:0x0120, B:40:0x0126, B:41:0x012c, B:43:0x0136, B:45:0x013c, B:46:0x0142, B:48:0x014c, B:50:0x0152, B:51:0x0158, B:53:0x0162, B:55:0x0168, B:56:0x016e, B:58:0x0184, B:60:0x0195, B:62:0x019a, B:64:0x01a0, B:66:0x01a6, B:67:0x01a8, B:69:0x01de, B:71:0x01e4, B:73:0x01eb, B:75:0x01f5, B:77:0x01fb, B:80:0x0203, B:82:0x020b, B:84:0x0218, B:86:0x021e, B:88:0x0224, B:90:0x022e, B:92:0x0234, B:95:0x023c, B:97:0x0240, B:100:0x0246, B:102:0x024a, B:106:0x0252, B:109:0x026a, B:111:0x026e, B:112:0x0270, B:114:0x027a, B:116:0x0280, B:118:0x0288, B:120:0x0294, B:122:0x0298, B:414:0x0891, B:125:0x02ee, B:127:0x02f6, B:129:0x0303, B:131:0x0309, B:133:0x030f, B:135:0x0319, B:137:0x031f, B:140:0x0327, B:142:0x032d, B:146:0x0335, B:148:0x033b, B:153:0x0345, B:156:0x035d, B:158:0x0361, B:159:0x0363, B:161:0x036d, B:163:0x0373, B:165:0x037b, B:167:0x0387, B:169:0x038b, B:171:0x0395, B:173:0x039b, B:175:0x03a2, B:178:0x03dc, B:180:0x03e4, B:182:0x03f1, B:184:0x03f7, B:187:0x03ff, B:189:0x0405, B:191:0x040b, B:192:0x040d, B:194:0x0417, B:196:0x041d, B:199:0x0425, B:201:0x0429, B:204:0x042f, B:206:0x0433, B:210:0x043b, B:213:0x044f, B:215:0x0453, B:216:0x0455, B:218:0x045f, B:220:0x0465, B:222:0x046d, B:224:0x0479, B:226:0x047d, B:59:0x018f, B:232:0x04dd, B:234:0x04f2, B:236:0x04fa, B:238:0x0506, B:240:0x0514, B:242:0x051a, B:243:0x0520, B:245:0x052a, B:247:0x0530, B:248:0x0536, B:250:0x0540, B:252:0x0546, B:253:0x054c, B:255:0x0556, B:257:0x055c, B:258:0x0562, B:260:0x057a, B:262:0x0581, B:264:0x0595, B:267:0x059d, B:269:0x05a5, B:271:0x05b5, B:273:0x05bb, B:276:0x05c3, B:278:0x05c9, B:280:0x05cf, B:281:0x05d1, B:283:0x05db, B:285:0x05e1, B:288:0x05e9, B:290:0x05ef, B:294:0x05f7, B:296:0x05fd, B:301:0x0607, B:304:0x061b, B:306:0x061f, B:307:0x0621, B:309:0x0627, B:310:0x062d, B:312:0x0637, B:314:0x063d, B:316:0x0645, B:318:0x0651, B:320:0x0655, B:322:0x065f, B:324:0x0665, B:325:0x0669, B:327:0x0675, B:328:0x0679, B:330:0x0685, B:331:0x0689, B:332:0x06f8, B:334:0x0704, B:336:0x0714, B:338:0x071a, B:341:0x0722, B:343:0x0728, B:345:0x072e, B:346:0x0730, B:348:0x073a, B:350:0x0740, B:353:0x0748, B:355:0x074e, B:359:0x0756, B:361:0x075c, B:366:0x0766, B:369:0x077e, B:371:0x0782, B:372:0x0784, B:374:0x078a, B:375:0x0790, B:377:0x079a, B:379:0x07a0, B:381:0x07a8, B:383:0x07b4, B:385:0x07b8, B:387:0x07c2, B:388:0x07c6, B:390:0x07d2, B:392:0x07d8, B:393:0x07dc, B:395:0x07e8, B:397:0x07ee, B:399:0x07f5, B:401:0x07ff, B:403:0x0805, B:405:0x0810, B:415:0x089f, B:417:0x08af, B:419:0x08bd, B:420:0x08c2, B:422:0x08c7, B:424:0x08cd, B:426:0x08d3, B:428:0x08df, B:430:0x08ef, B:432:0x08fd, B:435:0x0906, B:437:0x090c, B:439:0x0914, B:442:0x091b, B:444:0x0921, B:445:0x0928, B:446:0x092e, B:447:0x0934, B:449:0x0943, B:450:0x0949, B:530:0x0b1d, B:532:0x0b3d, B:534:0x0b49, B:529:0x0b12), top: B:545:0x001a }] */
    /* JADX WARN: Removed duplicated region for block: B:398:0x07f3  */
    /* JADX WARN: Removed duplicated region for block: B:404:0x080c  */
    /* JADX WARN: Removed duplicated region for block: B:532:0x0b3d A[Catch: Exception -> 0x0b53, TryCatch #4 {Exception -> 0x0b53, blocks: (B:7:0x001a, B:12:0x007d, B:14:0x008b, B:16:0x0099, B:18:0x009f, B:20:0x00ad, B:22:0x00b7, B:24:0x00bd, B:26:0x00c9, B:28:0x00d3, B:29:0x00e2, B:32:0x00fe, B:34:0x0106, B:36:0x0112, B:38:0x0120, B:40:0x0126, B:41:0x012c, B:43:0x0136, B:45:0x013c, B:46:0x0142, B:48:0x014c, B:50:0x0152, B:51:0x0158, B:53:0x0162, B:55:0x0168, B:56:0x016e, B:58:0x0184, B:60:0x0195, B:62:0x019a, B:64:0x01a0, B:66:0x01a6, B:67:0x01a8, B:69:0x01de, B:71:0x01e4, B:73:0x01eb, B:75:0x01f5, B:77:0x01fb, B:80:0x0203, B:82:0x020b, B:84:0x0218, B:86:0x021e, B:88:0x0224, B:90:0x022e, B:92:0x0234, B:95:0x023c, B:97:0x0240, B:100:0x0246, B:102:0x024a, B:106:0x0252, B:109:0x026a, B:111:0x026e, B:112:0x0270, B:114:0x027a, B:116:0x0280, B:118:0x0288, B:120:0x0294, B:122:0x0298, B:414:0x0891, B:125:0x02ee, B:127:0x02f6, B:129:0x0303, B:131:0x0309, B:133:0x030f, B:135:0x0319, B:137:0x031f, B:140:0x0327, B:142:0x032d, B:146:0x0335, B:148:0x033b, B:153:0x0345, B:156:0x035d, B:158:0x0361, B:159:0x0363, B:161:0x036d, B:163:0x0373, B:165:0x037b, B:167:0x0387, B:169:0x038b, B:171:0x0395, B:173:0x039b, B:175:0x03a2, B:178:0x03dc, B:180:0x03e4, B:182:0x03f1, B:184:0x03f7, B:187:0x03ff, B:189:0x0405, B:191:0x040b, B:192:0x040d, B:194:0x0417, B:196:0x041d, B:199:0x0425, B:201:0x0429, B:204:0x042f, B:206:0x0433, B:210:0x043b, B:213:0x044f, B:215:0x0453, B:216:0x0455, B:218:0x045f, B:220:0x0465, B:222:0x046d, B:224:0x0479, B:226:0x047d, B:59:0x018f, B:232:0x04dd, B:234:0x04f2, B:236:0x04fa, B:238:0x0506, B:240:0x0514, B:242:0x051a, B:243:0x0520, B:245:0x052a, B:247:0x0530, B:248:0x0536, B:250:0x0540, B:252:0x0546, B:253:0x054c, B:255:0x0556, B:257:0x055c, B:258:0x0562, B:260:0x057a, B:262:0x0581, B:264:0x0595, B:267:0x059d, B:269:0x05a5, B:271:0x05b5, B:273:0x05bb, B:276:0x05c3, B:278:0x05c9, B:280:0x05cf, B:281:0x05d1, B:283:0x05db, B:285:0x05e1, B:288:0x05e9, B:290:0x05ef, B:294:0x05f7, B:296:0x05fd, B:301:0x0607, B:304:0x061b, B:306:0x061f, B:307:0x0621, B:309:0x0627, B:310:0x062d, B:312:0x0637, B:314:0x063d, B:316:0x0645, B:318:0x0651, B:320:0x0655, B:322:0x065f, B:324:0x0665, B:325:0x0669, B:327:0x0675, B:328:0x0679, B:330:0x0685, B:331:0x0689, B:332:0x06f8, B:334:0x0704, B:336:0x0714, B:338:0x071a, B:341:0x0722, B:343:0x0728, B:345:0x072e, B:346:0x0730, B:348:0x073a, B:350:0x0740, B:353:0x0748, B:355:0x074e, B:359:0x0756, B:361:0x075c, B:366:0x0766, B:369:0x077e, B:371:0x0782, B:372:0x0784, B:374:0x078a, B:375:0x0790, B:377:0x079a, B:379:0x07a0, B:381:0x07a8, B:383:0x07b4, B:385:0x07b8, B:387:0x07c2, B:388:0x07c6, B:390:0x07d2, B:392:0x07d8, B:393:0x07dc, B:395:0x07e8, B:397:0x07ee, B:399:0x07f5, B:401:0x07ff, B:403:0x0805, B:405:0x0810, B:415:0x089f, B:417:0x08af, B:419:0x08bd, B:420:0x08c2, B:422:0x08c7, B:424:0x08cd, B:426:0x08d3, B:428:0x08df, B:430:0x08ef, B:432:0x08fd, B:435:0x0906, B:437:0x090c, B:439:0x0914, B:442:0x091b, B:444:0x0921, B:445:0x0928, B:446:0x092e, B:447:0x0934, B:449:0x0943, B:450:0x0949, B:530:0x0b1d, B:532:0x0b3d, B:534:0x0b49, B:529:0x0b12), top: B:545:0x001a }] */
    /* JADX WARN: Removed duplicated region for block: B:557:? A[ADDED_TO_REGION, RETURN, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r15v4, types: [j.c$g] */
    /* JADX WARN: Type inference failed for: r22v2 */
    /* JADX WARN: Type inference failed for: r22v3, types: [int] */
    /* JADX WARN: Type inference failed for: r22v4 */
    /* JADX WARN: Type inference failed for: r22v5 */
    /* JADX WARN: Type inference failed for: r22v6 */
    /* JADX WARN: Type inference failed for: r4v12 */
    /* JADX WARN: Type inference failed for: r4v17 */
    /* JADX WARN: Type inference failed for: r4v18, types: [int] */
    /* JADX WARN: Type inference failed for: r4v59 */
    /* JADX WARN: Type inference failed for: r4v60 */
    /* JADX WARN: Type inference failed for: r6v15, types: [java.lang.StringBuilder] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void a(org.wrtca.api.StatsReport[] r55) throws org.json.JSONException, java.lang.NumberFormatException {
        /*
            Method dump skipped, instructions count: 2905
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: j.c.a(org.wrtca.api.StatsReport[]):void");
    }

    public final Map<String, String> a(StatsReport statsReport) {
        HashMap map = new HashMap();
        for (StatsReport.Value value : statsReport.values) {
            map.put(value.name, value.value);
        }
        return map;
    }

    public static String a(String str, boolean z2, String str2, int i2) {
        boolean z3;
        String strGroup;
        String[] strArrSplit = str2.split("\r\n");
        Pattern patternCompile = Pattern.compile("^a=rtpmap:(\\d+) " + str + "(/\\d+)+[\r]?$");
        int i3 = 0;
        while (true) {
            z3 = true;
            if (i3 >= strArrSplit.length) {
                i3 = -1;
                strGroup = null;
                break;
            }
            Matcher matcher = patternCompile.matcher(strArrSplit[i3]);
            if (matcher.matches()) {
                strGroup = matcher.group(1);
                break;
            }
            i3++;
        }
        if (strGroup == null) {
            c.h.e(T, "No rtpmap for " + str + " codec");
            return str2;
        }
        c.h.a(T, "Found " + str + " rtpmap " + strGroup + " at " + strArrSplit[i3]);
        StringBuilder sb = new StringBuilder();
        sb.append("^a=fmtp:");
        sb.append(strGroup);
        sb.append(" \\w+=\\d+.*[\r]?$");
        Pattern patternCompile2 = Pattern.compile(sb.toString());
        int i4 = 0;
        while (true) {
            if (i4 >= strArrSplit.length) {
                z3 = false;
                break;
            }
            if (patternCompile2.matcher(strArrSplit[i4]).matches()) {
                c.h.a(T, "Found " + str + " " + strArrSplit[i4]);
                if (z2) {
                    strArrSplit[i4] = strArrSplit[i4] + "; x-google-start-bitrate=" + i2;
                } else {
                    strArrSplit[i4] = strArrSplit[i4] + "; maxaveragebitrate=" + (i2 * 1000);
                }
                c.h.a(T, "Update remote SDP line: " + strArrSplit[i4]);
            } else {
                i4++;
            }
        }
        StringBuilder sb2 = new StringBuilder();
        for (int i5 = 0; i5 < strArrSplit.length; i5++) {
            sb2.append(strArrSplit[i5]);
            sb2.append("\r\n");
            if (!z3 && i5 == i3) {
                String str3 = z2 ? "a=fmtp:" + strGroup + " " + j.a.f27388q + "=" + i2 : "a=fmtp:" + strGroup + " " + j.a.f27393v + "=" + (i2 * 1000);
                c.h.a(T, "Add remote SDP line: " + str3);
                sb2.append(str3);
                sb2.append("\r\n");
            }
        }
        return sb2.toString();
    }

    public int a(long j2) {
        double d3 = j2;
        if (Double.isNaN(d3) || Double.isInfinite(d3)) {
            return 0;
        }
        return new BigDecimal(j2).divide(new BigDecimal(1000)).intValue();
    }

    public synchronized void a(boolean z2, int i2) {
        if (z2) {
            try {
                if (this.f27414i == null) {
                    this.f27414i = new Timer();
                    c.h.a(T, " enableStatsEvents+++++++++ statsTimer ");
                    this.f27414i.schedule(new b(), 0L, i2);
                }
            } catch (Exception e2) {
                c.h.c(T, "Can not schedule statistics timer " + e2.getMessage());
            }
        }
        if (this.f27414i != null) {
            c.h.a(T, "stop client " + this.f27408c + "status record");
            this.f27414i.cancel();
            this.f27414i = null;
            this.f27429x = 0;
            this.E = 0;
            this.F = 0;
            this.G = 0;
            this.H = 0;
            this.I = 0;
            this.J = 0;
            this.K = 0;
            this.L = 0;
            this.M = 0;
            this.N = 0;
            this.O = 0;
            this.P = 0;
            this.Q = 0;
            this.R = 0;
        }
    }

    public boolean a(j.h hVar) {
        if (this.f27413h == null) {
            return false;
        }
        this.f27422q = hVar.d();
        this.f27423r = hVar.c();
        this.f27424s = hVar.e();
        this.f27413h.setBitrate(Integer.valueOf(hVar.d() * 1000), Integer.valueOf(hVar.e() * 1000), Integer.valueOf(hVar.c() * 1000));
        return true;
    }

    public void a(boolean z2) {
        PeerConnectionFactory peerConnectionFactoryM = j.d.d().m();
        if (peerConnectionFactoryM != null) {
            PeerConnection.RTCConfiguration rTCConfiguration = new PeerConnection.RTCConfiguration(new ArrayList());
            rTCConfiguration.tcpCandidatePolicy = PeerConnection.TcpCandidatePolicy.DISABLED;
            rTCConfiguration.bundlePolicy = PeerConnection.BundlePolicy.BALANCED;
            rTCConfiguration.rtcpMuxPolicy = PeerConnection.RtcpMuxPolicy.REQUIRE;
            rTCConfiguration.continualGatheringPolicy = PeerConnection.ContinualGatheringPolicy.GATHER_ONCE;
            rTCConfiguration.keyType = PeerConnection.KeyType.ECDSA;
            rTCConfiguration.enableDtlsSrtp = Boolean.TRUE;
            this.f27413h = peerConnectionFactoryM.createPeerConnection(rTCConfiguration, this.f27409d);
            this.f27410e = f.b.LOGIC_ICE_STATE_NEW.ordinal();
        }
    }

    public void a(VideoRenderer.Callbacks callbacks) {
        c.h.a(T, " startRender ");
        if (callbacks == null || this.f27415j == null) {
            return;
        }
        if (this.f27425t.containsKey(callbacks)) {
            c.h.a(T, " startRender callBack:" + callbacks + " is already existed");
            return;
        }
        VideoRenderer videoRenderer = new VideoRenderer(callbacks);
        this.f27425t.put(callbacks, videoRenderer);
        this.f27415j.addRenderer(videoRenderer);
        c.h.a(T, " startRender create new renderer " + videoRenderer + "callback " + callbacks);
    }

    public void a(boolean z2, VideoRenderer.Callbacks callbacks) {
        c.h.a(T, "stopRender callback: " + callbacks);
        if (this.f27425t.isEmpty() || this.f27415j == null) {
            return;
        }
        if (this.f27425t.containsKey(callbacks)) {
            c.h.a(T, " stopRender callBack:" + callbacks + " is existed");
            boolean z3 = callbacks instanceof TextureViewRenderer;
            this.f27415j.removeRenderer((VideoRenderer) this.f27425t.get(callbacks));
            this.f27425t.remove(callbacks);
            return;
        }
        c.h.a(T, " stopRender callBack:" + callbacks + " is not existed");
    }

    public void a(SessionDescription sessionDescription) {
        String strB = sessionDescription.description;
        j.g gVar = this.f27417l;
        j.g gVar2 = j.g.U_RTC_INACTIVE;
        if (gVar != gVar2) {
            strB = b(strB, j.a.f27386o, true);
        }
        if (this.f27418m != gVar2) {
            strB = b(strB, j.a.f27381j, false);
        }
        c.h.a(T, " setLocalDescription " + sessionDescription.description);
        this.f27413h.setLocalDescription(this.f27411f, new SessionDescription(sessionDescription.type, strB));
    }

    public void a(double d3) {
        AudioTrack audioTrack = this.f27416k;
        if (audioTrack != null) {
            audioTrack.setVolume(d3);
        }
    }

    public static int a(boolean z2, String[] strArr) {
        String str = z2 ? "m=audio " : "m=video ";
        for (int i2 = 0; i2 < strArr.length; i2++) {
            if (strArr[i2].startsWith(str)) {
                return i2;
            }
        }
        return -1;
    }

    public static String a(Iterable<? extends CharSequence> iterable, String str, boolean z2) {
        Iterator<? extends CharSequence> it = iterable.iterator();
        if (!it.hasNext()) {
            return "";
        }
        StringBuilder sb = new StringBuilder(it.next());
        while (it.hasNext()) {
            sb.append(str);
            sb.append(it.next());
        }
        if (z2) {
            sb.append(str);
        }
        return sb.toString();
    }

    public static String a(List<String> list, String str) {
        List listAsList = Arrays.asList(str.split(" "));
        if (listAsList.size() <= 3) {
            c.h.c(T, "Wrong SDP media description format: " + str);
            return null;
        }
        List listSubList = listAsList.subList(0, 3);
        ArrayList arrayList = new ArrayList(listAsList.subList(3, listAsList.size()));
        arrayList.removeAll(list);
        ArrayList arrayList2 = new ArrayList();
        arrayList2.addAll(listSubList);
        arrayList2.addAll(list);
        arrayList2.addAll(arrayList);
        return a((Iterable<? extends CharSequence>) arrayList2, " ", false);
    }
}
