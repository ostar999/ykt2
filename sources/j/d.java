package j;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.media.projection.MediaProjection;
import android.os.Build;
import android.os.Bundle;
import cn.hutool.core.text.StrPool;
import com.huawei.hms.push.constant.RemoteMessageConst;
import core.data.WrappedVideoFrame;
import core.interfaces.CameraEventListener;
import core.interfaces.DataProvider;
import core.interfaces.RtcNotification;
import core.interfaces.VideoFramePreProcessListener;
import core.services.CaptureScreenService;
import j.c;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.wrtca.api.AudioSource;
import org.wrtca.api.AudioTrack;
import org.wrtca.api.Camera1Enumerator;
import org.wrtca.api.Camera2Enumerator;
import org.wrtca.api.CameraEnumerator;
import org.wrtca.api.CameraVideoCapturer;
import org.wrtca.api.DefaultVideoDecoderFactory;
import org.wrtca.api.EglBase;
import org.wrtca.api.MediaConstraints;
import org.wrtca.api.MediaStream;
import org.wrtca.api.PeerConnectionFactory;
import org.wrtca.api.ScreenCapturerAndroid;
import org.wrtca.api.SessionDescription;
import org.wrtca.api.SurfaceViewRenderer;
import org.wrtca.api.VideoCapturer;
import org.wrtca.api.VideoFrame;
import org.wrtca.api.VideoRenderer;
import org.wrtca.api.VideoSink;
import org.wrtca.api.VideoSource;
import org.wrtca.api.VideoTrack;
import org.wrtca.customize.RtcExDevice2YUVCapturer;
import org.wrtca.log.Logging;
import org.wrtca.util.ContextUtils;
import org.wrtca.util.NativeLibrary;
import org.wrtca.video.CameraCapturer;
import org.wrtca.video.CameraSession;
import org.wrtca.video.RtcCameraRTSPCapturer;
import org.wrtca.video.RtcCameraRTSPEnumerator;
import org.wrtca.video.TextureViewRenderer;
import org.wrtca.video.processor.VideoPreProcessor;

/* loaded from: classes8.dex */
public class d {
    public static final String U = "PeerManager";
    public static final String V = "core.services.CaptureScreenService";
    public static d W;
    public static Intent X;
    public static DataProvider Y;
    public static RtcNotification Z;
    public ArrayList<VideoSink> A;
    public VideoSink B;
    public EglBase C;
    public int D;
    public String E;
    public boolean F;
    public boolean G;
    public boolean H;
    public boolean I;
    public EnumC0464d K;
    public int M;
    public int N;
    public MediaProjection.Callback O;
    public b P;
    public e Q;
    public VideoFramePreProcessListener R;
    public CameraEventListener S;
    public VideoPreProcessor T;

    /* renamed from: m, reason: collision with root package name */
    public j.f f27471m;

    /* renamed from: p, reason: collision with root package name */
    public AudioTrack f27474p;

    /* renamed from: q, reason: collision with root package name */
    public AudioSource f27475q;

    /* renamed from: r, reason: collision with root package name */
    public VideoTrack f27476r;

    /* renamed from: s, reason: collision with root package name */
    public VideoSource f27477s;

    /* renamed from: t, reason: collision with root package name */
    public VideoCapturer f27478t;

    /* renamed from: u, reason: collision with root package name */
    public VideoTrack f27479u;

    /* renamed from: v, reason: collision with root package name */
    public VideoSource f27480v;

    /* renamed from: w, reason: collision with root package name */
    public VideoCapturer f27481w;

    /* renamed from: x, reason: collision with root package name */
    public h f27482x;

    /* renamed from: y, reason: collision with root package name */
    public h f27483y;

    /* renamed from: z, reason: collision with root package name */
    public h f27484z;

    /* renamed from: a, reason: collision with root package name */
    public Map<String, j.c> f27459a = new HashMap();

    /* renamed from: b, reason: collision with root package name */
    public Map<String, c.i> f27460b = new HashMap();

    /* renamed from: c, reason: collision with root package name */
    public Map<String, c.i> f27461c = new HashMap();

    /* renamed from: d, reason: collision with root package name */
    public Map<String, c.h> f27462d = new HashMap();

    /* renamed from: e, reason: collision with root package name */
    public Map<String, Integer> f27463e = new HashMap();

    /* renamed from: f, reason: collision with root package name */
    public int f27464f = 0;

    /* renamed from: g, reason: collision with root package name */
    public int f27465g = 10;

    /* renamed from: h, reason: collision with root package name */
    public int f27466h = 10;

    /* renamed from: i, reason: collision with root package name */
    public int f27467i = 15;

    /* renamed from: j, reason: collision with root package name */
    public int f27468j = 10;

    /* renamed from: k, reason: collision with root package name */
    public int f27469k = 10;

    /* renamed from: l, reason: collision with root package name */
    public Map<String, c.j> f27470l = new HashMap();

    /* renamed from: n, reason: collision with root package name */
    public PeerConnectionFactory f27472n = null;

    /* renamed from: o, reason: collision with root package name */
    public Map<String, MediaStream> f27473o = new HashMap();
    public boolean J = false;
    public f L = f.NQ_CONTINUOUS;

    public class a extends MediaProjection.Callback {
        public a() {
        }

        @Override // android.media.projection.MediaProjection.Callback
        public void onStop() {
            c.h.a(d.U, "PeerManager onStop ");
            super.onStop();
        }
    }

    public class b implements Application.ActivityLifecycleCallbacks {
        public b() {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityCreated(Activity activity, Bundle bundle) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityDestroyed(Activity activity) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityPaused(Activity activity) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityResumed(Activity activity) {
            c.h.a(d.U, "onActivityResumed called mCameraState: " + d.this.K);
            if (d.this.K == EnumC0464d.EVICTED) {
                c.h.a(d.U, "onActivityResumed startCapture");
                d.this.A();
            }
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityStarted(Activity activity) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityStopped(Activity activity) {
        }

        public /* synthetic */ b(d dVar, a aVar) {
            this();
        }
    }

    public class c implements CameraVideoCapturer.CameraEventsHandler {
        public c() {
        }

        @Override // org.wrtca.api.CameraVideoCapturer.CameraEventsHandler
        public void onCameraClosed() {
            c.h.a(d.U, "onCameraClosed : ");
            if (d.this.K != EnumC0464d.EVICTED) {
                d.this.K = EnumC0464d.STOPPED;
            }
            if (d.this.S != null) {
                d.this.S.onCameraClosed();
            }
        }

        @Override // org.wrtca.api.CameraVideoCapturer.CameraEventsHandler
        public void onCameraDisconnected() {
            c.h.a(d.U, "onCameraDisconnected : ");
            d.this.K = EnumC0464d.EVICTED;
            if (d.this.S != null) {
                d.this.S.onCameraDisconnected();
            }
        }

        @Override // org.wrtca.api.CameraVideoCapturer.CameraEventsHandler
        public void onCameraError(int i2, String str) {
            c.h.a(d.U, "onCameraError: " + str);
            d.this.I = true;
            d.this.K = EnumC0464d.STOPPED;
            if (d.this.S != null) {
                d.this.S.onCameraError(i2, str);
            }
        }

        @Override // org.wrtca.api.CameraVideoCapturer.CameraEventsHandler
        public void onCameraFreezed(String str) {
            c.h.a(d.U, "onCameraFreezed errorDescription : " + str);
            if (d.this.S != null) {
                d.this.S.onCameraFreezed(str);
            }
        }

        @Override // org.wrtca.api.CameraVideoCapturer.CameraEventsHandler
        public void onCameraOpening(String str) {
            c.h.a(d.U, "onCameraOpening camera: " + str);
            d.this.K = EnumC0464d.OPENING;
            if (d.this.S != null) {
                d.this.S.onCameraOpening(str);
            }
        }

        @Override // org.wrtca.api.CameraVideoCapturer.CameraEventsHandler
        public VideoFrame onCaptureFrame(CameraSession cameraSession, VideoFrame videoFrame) {
            if (d.this.R == null || d.this.T == null) {
                return videoFrame;
            }
            WrappedVideoFrame wrappedVideoFrame = new WrappedVideoFrame(videoFrame);
            WrappedVideoFrame wrappedVideoFrameCreateEmptyDstFrameFromSource = wrappedVideoFrame.createEmptyDstFrameFromSource();
            d.this.R.onPreProcessVideoFrame(wrappedVideoFrame, wrappedVideoFrameCreateEmptyDstFrameFromSource);
            return d.this.T.processWriteBack(cameraSession.requestCamera(), cameraSession.getDeviceOrientation(), cameraSession.getSurfaceTextureHelper(), wrappedVideoFrame, wrappedVideoFrameCreateEmptyDstFrameFromSource);
        }

        @Override // org.wrtca.api.CameraVideoCapturer.CameraEventsHandler
        public void onFirstFrameAvailable() {
            c.h.a(d.U, "onFirstFrameAvailable : ");
            d.this.K = EnumC0464d.STARTED;
            if (d.this.Q != null) {
                d.this.Q.onFirstLocalVideoFrame();
            }
        }

        public /* synthetic */ c(d dVar, a aVar) {
            this();
        }
    }

    /* renamed from: j.d$d, reason: collision with other inner class name */
    public enum EnumC0464d {
        OPENING,
        STARTED,
        EVICTED,
        STOPPED
    }

    public interface e {
        void onFirstLocalVideoFrame();
    }

    public enum f {
        NQ_CONTINUOUS,
        NQ_SPLIT
    }

    public d() {
        this.O = null;
        c.h.a(U, " PeerManager ");
        this.f27482x = new h(320, 240, 30, 200, 300, 300);
        this.f27483y = new h(640, 480, 10, 200, 300, 300);
        this.f27484z = new h();
        this.f27475q = null;
        this.f27474p = null;
        this.f27477s = null;
        this.f27476r = null;
        this.f27478t = null;
        this.f27479u = null;
        this.f27480v = null;
        this.f27481w = null;
        this.D = 1;
        this.E = "1";
        this.F = false;
        this.G = false;
        this.H = false;
        this.A = new ArrayList<>();
        this.B = null;
        this.K = EnumC0464d.STOPPED;
        this.M = 0;
        this.N = 0;
        this.C = org.wrtca.api.a.a();
        c.h.a(U, " PeerManager " + this.C);
        int i2 = Build.VERSION.SDK_INT;
        this.O = new a();
        this.P = new b(this, null);
        Context contextB = c.e.b();
        if (contextB instanceof Activity) {
            if (i2 >= 29) {
                ((Activity) contextB).registerActivityLifecycleCallbacks(this.P);
            }
        } else if (contextB instanceof Application) {
            ((Application) contextB).registerActivityLifecycleCallbacks(this.P);
        }
    }

    public void A() {
        if (this.f27478t == null) {
            c.h.a(U, "startCapture failed for videoCapture is null");
            return;
        }
        c.h.a(U, " startCapture get_width: " + this.f27484z.f() + " get_height: " + this.f27484z.b() + " videoCapturer: " + this.f27478t);
        this.f27478t.startCapture(this.f27484z.f(), this.f27484z.b(), this.f27482x.a());
    }

    public void B() {
        VideoCapturer videoCapturer = this.f27478t;
        if (videoCapturer != null) {
            try {
                videoCapturer.stopCapture();
            } catch (InterruptedException e2) {
                throw new RuntimeException(e2);
            }
        }
    }

    public void C() {
        this.f27472n.stopPlayAudioFile();
    }

    public final void D() {
        try {
            if (Build.VERSION.SDK_INT >= 29) {
                if (c.e.a(ContextUtils.getApplicationContext(), V)) {
                    c.h.a(U, "stopService for screen capture");
                    ContextUtils.getApplicationContext().stopService(new Intent(d.b.a(), (Class<?>) CaptureScreenService.class));
                } else {
                    c.h.a(U, "CaptureScreenService is stopped");
                }
            }
            if (this.f27481w != null) {
                c.h.a(U, "screenCapturer.stopCapture() calling");
                this.f27481w.stopCapture();
                this.f27481w.dispose();
                ScreenCapturerAndroid.DeleteInstance();
                this.f27481w = null;
            }
        } catch (InterruptedException e2) {
            e2.printStackTrace();
            c.h.a(U, c.e.a(e2.getCause()));
        }
    }

    public void E() {
        VideoSink videoSink;
        VideoTrack videoTrack = this.f27479u;
        if (videoTrack == null || (videoSink = this.B) == null) {
            return;
        }
        videoTrack.removeSink(videoSink);
        this.B = null;
    }

    public void F() {
        VideoCapturer videoCapturer = this.f27478t;
        if (videoCapturer instanceof CameraVideoCapturer) {
            ((CameraVideoCapturer) videoCapturer).turnFlashLightOff();
        }
    }

    public void G() {
        VideoCapturer videoCapturer = this.f27478t;
        if (videoCapturer instanceof CameraVideoCapturer) {
            ((CameraVideoCapturer) videoCapturer).turnFlashLightOn();
        }
    }

    public void f(String str) {
        j.c cVar = this.f27459a.get(str);
        if (cVar != null) {
            c.h.a(U, " createOffer" + str);
            cVar.j();
        }
    }

    public void g(boolean z2) {
        VideoTrack videoTrack = this.f27476r;
        if (videoTrack != null) {
            videoTrack.setEnabled(!z2);
        }
    }

    public MediaStream h(String str) {
        return this.f27473o.get(str);
    }

    public void i(String str) {
        j.c cVar = this.f27459a.get(str);
        c.h.a(U, "PeerManagerreleasePeerClient streamid " + str + " peerclient: " + cVar);
        if (cVar != null) {
            cVar.f(true);
            cVar.b();
        }
    }

    public void j(boolean z2) {
        if (this.f27476r == null || this.A == null) {
            return;
        }
        c.h.a(U, "stopRender mlocalCamviews before: " + this.A.size());
        Iterator<VideoSink> it = this.A.iterator();
        while (it.hasNext()) {
            VideoSink next = it.next();
            boolean z3 = next instanceof TextureViewRenderer;
            this.f27476r.removeSink(next);
        }
        this.A.clear();
        c.h.a(U, "stopRender mlocalCamviews after: " + this.A.size());
    }

    public void k(boolean z2) {
        VideoCapturer videoCapturer = this.f27478t;
        if (videoCapturer instanceof CameraVideoCapturer) {
            ((CameraVideoCapturer) videoCapturer).switchCamera(z2, null);
        }
    }

    public EnumC0464d l() {
        return this.K;
    }

    public PeerConnectionFactory m() {
        return this.f27472n;
    }

    public j.f n() {
        return this.f27471m;
    }

    public EglBase.Context o() {
        c.h.a(U, "rootegl " + this.C);
        return this.C.getEglBaseContext();
    }

    public VideoFramePreProcessListener p() {
        return this.R;
    }

    public void q() {
        e.a.a().J();
    }

    public void r() {
        this.f27472n.pauseAudioFile();
    }

    public boolean s() {
        AudioTrack audioTrack = this.f27474p;
        return audioTrack != null && audioTrack.enabled();
    }

    public boolean t() {
        VideoTrack videoTrack = this.f27479u;
        return videoTrack != null && videoTrack.enabled();
    }

    public boolean u() {
        VideoTrack videoTrack = this.f27476r;
        return videoTrack != null && videoTrack.enabled();
    }

    public void v() {
        this.J = false;
    }

    public void w() {
        ArrayList arrayList = new ArrayList();
        for (Map.Entry<String, j.c> entry : this.f27459a.entrySet()) {
            j.c value = entry.getValue();
            if (value != null && value.o() == 2) {
                c.h.a(U, " release releaseAllRemotePeer " + value);
                d().i(entry.getKey());
                arrayList.add(entry.getKey());
            }
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            c.h.a(U, " remove subinfo from mPeerMap key is:" + str);
            this.f27459a.remove(str);
        }
    }

    public CameraSession.CameraParam x() {
        VideoCapturer videoCapturer = this.f27478t;
        if (videoCapturer == null || !(videoCapturer instanceof CameraCapturer)) {
            return null;
        }
        return ((CameraCapturer) videoCapturer).requestCameraParam();
    }

    public boolean y() {
        VideoCapturer videoCapturer = this.f27478t;
        CameraSession.CameraParam cameraParamRequestCameraParam = (videoCapturer == null || !(videoCapturer instanceof CameraCapturer)) ? null : ((CameraCapturer) videoCapturer).requestCameraParam();
        if (cameraParamRequestCameraParam != null) {
            return cameraParamRequestCameraParam.isFrontCamera();
        }
        return true;
    }

    public void z() {
        this.f27472n.resumeAudioFile();
    }

    public static void b(boolean z2) {
        d dVar = W;
        if (dVar != null) {
            dVar.i(z2);
            if (z2) {
                W = null;
            }
        }
    }

    public static d d() {
        if (W == null) {
            W = new d();
        }
        return W;
    }

    public void c(String str) {
        j.c cVar = this.f27459a.get(str);
        c.h.a(U, "PeerManagerDeletePeerClientStillLocalRender streamid: " + str + "peerclient: " + cVar);
        if (cVar != null) {
            if (cVar.o() == 2) {
                cVar.f(true);
            }
            b(str, false);
            d(str);
            cVar.a();
            this.f27459a.remove(str);
        }
    }

    public void e(String str) {
        j.c cVar = this.f27459a.get(str);
        if (cVar != null) {
            cVar.i();
        }
    }

    public final VideoTrack h() {
        this.f27480v = this.f27472n.createVideoSource(this.f27481w, 0, false);
        if (Build.VERSION.SDK_INT < 29) {
            this.f27481w.startCapture(this.f27483y.f(), this.f27483y.b(), this.f27483y.a());
        } else if (c.e.a(ContextUtils.getApplicationContext(), V)) {
            c.h.a(U, "CaptureScreenService is running!");
        } else {
            c.h.a(U, "createScreenTrack startForegroundService for screen capture");
            Intent intent = new Intent(d.b.a(), (Class<?>) CaptureScreenService.class);
            intent.putExtra("width", this.f27483y.f());
            intent.putExtra("height", this.f27483y.b());
            intent.putExtra("frame_rate", this.f27483y.a());
            intent.putExtra("data", X);
            RtcNotification rtcNotification = Z;
            if (rtcNotification != null) {
                intent.putExtra(RemoteMessageConst.NOTIFICATION, rtcNotification.createNotificationChannel());
            }
            ContextUtils.getApplicationContext().startForegroundService(intent);
        }
        VideoTrack videoTrackCreateVideoTrack = this.f27472n.createVideoTrack(j.a.f27380i, this.f27480v);
        this.f27479u = videoTrackCreateVideoTrack;
        videoTrackCreateVideoTrack.setEnabled(true);
        return this.f27479u;
    }

    public j.c g(String str) {
        return this.f27459a.get(str);
    }

    public static void a(boolean z2) {
        d dVar = W;
        if (dVar != null) {
            dVar.h(z2);
            if (z2) {
                W = null;
            }
        }
    }

    public void e(boolean z2) {
        AudioTrack audioTrack = this.f27474p;
        if (audioTrack != null) {
            audioTrack.setEnabled(!z2);
        }
    }

    public void f(boolean z2) {
        VideoTrack videoTrack = this.f27479u;
        if (videoTrack != null) {
            videoTrack.setEnabled(!z2);
        }
    }

    public final AudioTrack g() {
        AudioSource audioSourceCreateAudioSource = this.f27472n.createAudioSource(new MediaConstraints());
        this.f27475q = audioSourceCreateAudioSource;
        AudioTrack audioTrackCreateAudioTrack = this.f27472n.createAudioTrack(j.a.f27378g, audioSourceCreateAudioSource);
        this.f27474p = audioTrackCreateAudioTrack;
        audioTrackCreateAudioTrack.setEnabled(true);
        return this.f27474p;
    }

    public int k() {
        return this.D;
    }

    public void b(PeerConnectionFactory.Options options) {
        a(options);
    }

    public void d(boolean z2) {
        Map<String, j.c> map = this.f27459a;
        if (map == null || map.size() <= 0) {
            return;
        }
        Iterator<String> it = this.f27459a.keySet().iterator();
        if (it.hasNext()) {
            j.c cVar = this.f27459a.get(it.next());
            cVar.c(z2);
            c.h.a(U, "peerClient " + cVar + " op audio record:" + z2);
        }
    }

    public void i() {
        if (d.b.d() == 0) {
            boolean zIsSupported = Camera2Enumerator.isSupported(c.e.b());
            c.h.a(U, "createVideoCapture cam2 is: " + zIsSupported);
            if (zIsSupported) {
                this.f27478t = a(new Camera2Enumerator(c.e.b()));
                return;
            } else {
                this.f27478t = a(new Camera1Enumerator(false));
                return;
            }
        }
        if (d.b.d() == 1) {
            this.f27478t = new RtcExDevice2YUVCapturer(Y);
        } else {
            d.b.d();
        }
    }

    public void b(String str) {
        j.c cVar = this.f27459a.get(str);
        c.h.a(U, "PeerManagerDeletePeerClient streamid: " + str + "peerclient: " + cVar);
        if (cVar != null) {
            if (cVar.o() == 1) {
                if (cVar.m() == 1) {
                    d().j(true);
                } else if (cVar.m() == 2) {
                    d().E();
                } else {
                    c.h.a(U, "MediaType is not support: " + cVar.m());
                }
            } else if (cVar.o() == 2) {
                cVar.f(true);
            }
            b(str, false);
            d(str);
            cVar.b();
            this.f27459a.remove(str);
        }
    }

    public void e(String str, boolean z2) {
        j.c cVar = this.f27459a.get(str);
        c.h.a(U, " stopRemoteRender streamId: " + str + " client: " + cVar);
        if (cVar != null) {
            cVar.f(z2);
        }
    }

    public void f() {
        Map<String, Integer> map = this.f27463e;
        if (map != null) {
            map.clear();
        }
        Map<String, c.i> map2 = this.f27460b;
        if (map2 != null) {
            map2.clear();
        }
        Map<String, c.i> map3 = this.f27461c;
        if (map3 != null) {
            map3.clear();
        }
        Map<String, c.h> map4 = this.f27462d;
        if (map4 != null) {
            map4.clear();
        }
    }

    public static void a(Intent intent) {
        X = intent;
    }

    public static void a(DataProvider dataProvider) {
        Y = dataProvider;
    }

    public static void a(RtcNotification rtcNotification) {
        Z = rtcNotification;
    }

    public final void e() {
        h(false);
    }

    public Object j(String str) {
        Iterator<String> it = this.f27459a.keySet().iterator();
        while (it.hasNext()) {
            c.h.a(U, " requestRender key " + it.next());
        }
        j.c cVar = this.f27459a.get(str);
        c.h.a(U, " requestRender streamid: " + str + " client: " + cVar);
        if (cVar != null) {
            return cVar.p();
        }
        return null;
    }

    public void a(e eVar) {
        this.Q = eVar;
    }

    public void c(boolean z2) {
        c.h.a(U, "PeerManagerenableLocalAudioPlayOut: " + z2);
        Map<String, j.c> map = this.f27459a;
        if (map == null || map.size() <= 0) {
            return;
        }
        Iterator<String> it = this.f27459a.keySet().iterator();
        if (it.hasNext()) {
            j.c cVar = this.f27459a.get(it.next());
            cVar.b(z2);
            c.h.a(U, "peerClient: " + cVar + " op audio playout: " + z2);
        }
    }

    public void a(VideoFramePreProcessListener videoFramePreProcessListener) {
        this.R = videoFramePreProcessListener;
    }

    public void d(String str, boolean z2) {
        j.c cVar = this.f27459a.get(str);
        if (cVar != null) {
            cVar.e(!z2);
        }
    }

    public void a(CameraEventListener cameraEventListener) {
        this.S = cameraEventListener;
    }

    public void a(String str, int i2, int i3, boolean z2, boolean z3, c.g gVar) {
        j.c cVar = this.f27459a.get(str);
        Iterator<String> it = this.f27459a.keySet().iterator();
        while (it.hasNext()) {
            c.h.a(U, "key: " + it.next() + " value: " + cVar);
        }
        c.h.a(U, " want to CreatePeerClient " + str + " already has peer " + cVar);
        if (cVar == null) {
            j.c cVar2 = new j.c(c.e.b(), i2, str, gVar, this);
            cVar2.a(i3, z3, z2);
            this.f27459a.put(str, cVar2);
            c.h.a(U, " CreatePeerClient  peer:" + cVar2);
        }
    }

    public void d(String str) {
        Map<String, Integer> map = this.f27463e;
        if (map != null) {
            map.remove(str);
        }
        Map<String, c.i> map2 = this.f27460b;
        if (map2 != null) {
            map2.remove(str);
        }
        Map<String, c.i> map3 = this.f27461c;
        if (map3 != null) {
            map3.remove(str);
        }
        Map<String, c.h> map4 = this.f27462d;
        if (map4 != null) {
            map4.remove(str);
        }
        c.h.a(U, "clear peer checkdata stream id " + str);
    }

    public final void i(boolean z2) {
        c.h.a(U, "releaseWithOutLocalCamera peer manager");
        for (Map.Entry<String, j.c> entry : this.f27459a.entrySet()) {
            j.c value = entry.getValue();
            if (value != null && value.m() != 1 && value.o() != 1) {
                c.h.a(U, " release PeerConClient " + value);
                d().i(entry.getKey());
                this.f27459a.remove(entry.getKey());
            }
        }
        this.B = null;
        this.f27473o.remove(j.a.f27379h);
        D();
        VideoSource videoSource = this.f27480v;
        if (videoSource != null) {
            videoSource.dispose();
            this.f27480v = null;
        }
        VideoTrack videoTrack = this.f27479u;
        if (videoTrack != null) {
            videoTrack.dispose();
            this.f27479u = null;
        }
        this.B = null;
        this.f27479u = null;
        if (!z2 || this.f27472n == null) {
            return;
        }
        c.h.a(U, "Closing video factory ");
        this.f27472n.dispose();
        this.f27472n = null;
    }

    public final VideoTrack j() {
        VideoCapturer videoCapturer = this.f27478t;
        if (videoCapturer != null) {
            this.f27477s = this.f27472n.createVideoSource(videoCapturer, d.b.p(), d.b.e());
            c.h.a(U, "videowidth " + this.f27482x.f() + " videoheight " + this.f27482x.b() + " videoCapturer: " + this.f27478t);
            if (d.b.q()) {
                c.h.a(U, "open camera when createVideoTrack");
                this.f27478t.startCapture(this.f27482x.f(), this.f27482x.b(), this.f27482x.a());
            } else {
                c.h.a(U, "do not open camera when createVideoTrack");
            }
            VideoTrack videoTrackCreateVideoTrack = this.f27472n.createVideoTrack(j.a.f27377f, this.f27477s);
            this.f27476r = videoTrackCreateVideoTrack;
            videoTrackCreateVideoTrack.setEnabled(true);
            if (d.b.e()) {
                b(this.M, this.N);
            }
        } else {
            c.h.a(U, "create video track failed ,videoCapturer is null");
        }
        return this.f27476r;
    }

    public void c(String str, boolean z2) {
        j.c cVar = this.f27459a.get(str);
        if (cVar != null) {
            cVar.d(!z2);
        }
    }

    public void c() {
        c.h.a(U, "DeleteVideoStream.");
        this.f27473o.remove(j.a.f27376e);
        c.h.a(U, "Stopping capture.");
        VideoCapturer videoCapturer = this.f27478t;
        if (videoCapturer != null) {
            try {
                videoCapturer.stopCapture();
                this.f27478t.dispose();
                this.f27478t = null;
                VideoPreProcessor videoPreProcessor = this.T;
                if (videoPreProcessor != null) {
                    videoPreProcessor.release();
                }
            } catch (InterruptedException e2) {
                throw new RuntimeException(e2);
            }
        }
        c.h.a(U, "Closing video source.");
        VideoSource videoSource = this.f27477s;
        if (videoSource != null) {
            videoSource.dispose();
            this.f27477s = null;
        }
        ArrayList<VideoSink> arrayList = this.A;
        if (arrayList != null && !arrayList.isEmpty()) {
            this.A.clear();
        }
        this.A = null;
        this.f27476r = null;
        this.F = false;
    }

    public final void h(boolean z2) {
        c.h.a(U, "release peer manager");
        for (Map.Entry<String, j.c> entry : this.f27459a.entrySet()) {
            j.c value = entry.getValue();
            if (value != null) {
                c.h.a(U, " release PeerConClient " + value);
                d().i(entry.getKey());
            }
        }
        this.f27459a.clear();
        this.f27473o.remove(j.a.f27376e);
        AudioSource audioSource = this.f27475q;
        if (audioSource != null) {
            audioSource.dispose();
            this.f27475q = null;
        }
        c.h.a(U, "Stopping capture.");
        VideoCapturer videoCapturer = this.f27478t;
        if (videoCapturer != null) {
            try {
                videoCapturer.stopCapture();
                this.f27478t.dispose();
                this.f27478t = null;
            } catch (InterruptedException e2) {
                throw new RuntimeException(e2);
            }
        }
        c.h.a(U, "Closing video source.");
        VideoSource videoSource = this.f27477s;
        if (videoSource != null) {
            videoSource.dispose();
            this.f27477s = null;
        }
        VideoTrack videoTrack = this.f27476r;
        if (videoTrack != null) {
            videoTrack.dispose();
            this.f27476r = null;
        }
        AudioTrack audioTrack = this.f27474p;
        if (audioTrack != null) {
            audioTrack.dispose();
            this.f27474p = null;
        }
        ArrayList<VideoSink> arrayList = this.A;
        if (arrayList != null && !arrayList.isEmpty()) {
            this.A.clear();
        }
        this.B = null;
        this.F = false;
        this.G = false;
        this.f27473o.remove(j.a.f27379h);
        D();
        VideoSource videoSource2 = this.f27480v;
        if (videoSource2 != null) {
            videoSource2.dispose();
            this.f27480v = null;
        }
        VideoTrack videoTrack2 = this.f27479u;
        if (videoTrack2 != null) {
            videoTrack2.dispose();
            this.f27479u = null;
        }
        this.B = null;
        this.f27479u = null;
        Context contextB = c.e.b();
        if (contextB instanceof Activity) {
            if (Build.VERSION.SDK_INT >= 29) {
                ((Activity) contextB).unregisterActivityLifecycleCallbacks(this.P);
            }
        } else if (contextB instanceof Application) {
            ((Application) contextB).unregisterActivityLifecycleCallbacks(this.P);
        }
        if (z2) {
            if (this.f27472n != null) {
                c.h.a(U, "Closing video factory ");
                this.f27472n.dispose();
                this.f27472n = null;
            }
            c.h.a(U, "egl root ");
            if (this.C != null) {
                c.h.a(U, "releasing egl root ");
                this.C.release();
                this.C = null;
            }
        }
    }

    public void b(String str, boolean z2) {
        j.c cVar = this.f27459a.get(str);
        if (cVar != null) {
            cVar.a(z2, 2000);
        }
    }

    public void a(String str, int i2, boolean z2) {
        if (this.f27459a.containsKey(str)) {
            this.f27459a.get(str).a(i2, z2);
        }
    }

    public void b(String str, String str2, String str3) {
        j.c cVar = this.f27459a.get(str);
        if (cVar != null) {
            SessionDescription.Type type = SessionDescription.Type.OFFER;
            if (!str2.equals("offer")) {
                type = SessionDescription.Type.ANSWER;
            }
            cVar.b(new SessionDescription(type, str3));
        }
    }

    public void a(String str) {
        j.c cVar = this.f27459a.get(str);
        c.h.a(U, "PeerManagerDeletePeerClient streamid: " + str + "peerclient: " + cVar);
        if (cVar != null) {
            b(str, false);
            d(str);
            cVar.a();
            this.f27459a.remove(str);
        }
    }

    public void b(int i2, int i3) {
        if (i3 < this.f27482x.b() && i2 < this.f27482x.f()) {
            if (this.f27477s != null) {
                c.h.a(U, "setCropSize cropX " + i2 + " cropY: " + i3);
                this.f27477s.setCropSize(i2, i3);
            } else {
                c.h.a(U, "localVideoSource is null.");
            }
        } else {
            c.h.a(U, "cropX or cropY is out of range." + this.f27482x.b() + StrPool.UNDERLINE + this.f27482x.f());
        }
        this.M = i2;
        this.N = i3;
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x0061  */
    /* JADX WARN: Removed duplicated region for block: B:34:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void a(java.lang.String r6, int r7, org.json.JSONObject r8) throws org.json.JSONException {
        /*
            r5 = this;
            java.lang.String r0 = "video"
            java.lang.String r1 = "audio"
            java.lang.String r2 = "PeerManager"
            if (r8 == 0) goto L86
            java.lang.String r3 = "data"
            boolean r4 = r8.has(r3)
            if (r4 == 0) goto L86
            r4 = 0
            org.json.JSONObject r8 = r8.getJSONObject(r3)     // Catch: org.json.JSONException -> L50
            boolean r3 = r8.has(r1)     // Catch: org.json.JSONException -> L50
            if (r3 == 0) goto L20
            org.json.JSONObject r0 = r8.getJSONObject(r1)     // Catch: org.json.JSONException -> L50
            goto L2c
        L20:
            boolean r1 = r8.has(r0)     // Catch: org.json.JSONException -> L50
            if (r1 == 0) goto L2b
            org.json.JSONObject r0 = r8.getJSONObject(r0)     // Catch: org.json.JSONException -> L50
            goto L2c
        L2b:
            r0 = r4
        L2c:
            if (r0 == 0) goto L5f
            j.e r1 = new j.e     // Catch: org.json.JSONException -> L50
            r1.<init>()     // Catch: org.json.JSONException -> L50
            r1.a(r6)     // Catch: org.json.JSONException -> L4d
            r1.d(r7)     // Catch: org.json.JSONException -> L4d
            java.lang.String r6 = "lostpre"
            int r6 = r0.getInt(r6)     // Catch: org.json.JSONException -> L4d
            r1.b(r6)     // Catch: org.json.JSONException -> L4d
            java.lang.String r6 = "rtt"
            int r6 = r8.getInt(r6)     // Catch: org.json.JSONException -> L4d
            r1.c(r6)     // Catch: org.json.JSONException -> L4d
            r4 = r1
            goto L5f
        L4d:
            r6 = move-exception
            r4 = r1
            goto L51
        L50:
            r6 = move-exception
        L51:
            r6.printStackTrace()
            java.lang.Throwable r6 = r6.getCause()
            java.lang.String r6 = c.e.a(r6)
            c.h.a(r2, r6)
        L5f:
            if (r4 == 0) goto L86
            boolean r6 = d.b.a.f26725k
            if (r6 == 0) goto L6f
            r6 = 200(0xc8, float:2.8E-43)
            r4.c(r6)
            r6 = 80
            r4.b(r6)
        L6f:
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r7 = "get a sample "
            r6.append(r7)
            r6.append(r4)
            java.lang.String r6 = r6.toString()
            c.h.a(r2, r6)
            r5.a(r4)
        L86:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: j.d.a(java.lang.String, int, org.json.JSONObject):void");
    }

    public void b(VideoSink videoSink) {
        VideoTrack videoTrack;
        VideoSink videoSink2 = this.B;
        if (videoSink2 != null) {
            VideoTrack videoTrack2 = this.f27479u;
            if (videoTrack2 != null) {
                videoTrack2.removeSink(videoSink2);
            }
            this.B = null;
        }
        if (videoSink == null || (videoTrack = this.f27479u) == null) {
            return;
        }
        this.B = videoSink;
        videoTrack.addSink(videoSink);
    }

    public void c(int i2, int i3) {
        this.f27484z.f(i2);
        this.f27484z.b(i3);
    }

    public void b() {
        c.h.a(U, "DeleteStream.");
        this.f27473o.remove(j.a.f27376e);
        AudioSource audioSource = this.f27475q;
        if (audioSource != null) {
            audioSource.dispose();
            this.f27475q = null;
        }
        c.h.a(U, "Stopping capture.");
        VideoCapturer videoCapturer = this.f27478t;
        if (videoCapturer != null) {
            try {
                videoCapturer.stopCapture();
                this.f27478t.dispose();
                this.f27478t = null;
            } catch (InterruptedException e2) {
                throw new RuntimeException(e2);
            }
        }
        c.h.a(U, "Closing video source.");
        VideoSource videoSource = this.f27477s;
        if (videoSource != null) {
            videoSource.dispose();
            this.f27477s = null;
        }
        ArrayList<VideoSink> arrayList = this.A;
        if (arrayList != null && !arrayList.isEmpty()) {
            this.A.clear();
        }
        this.A = null;
        this.f27476r = null;
        this.f27474p = null;
        this.F = false;
    }

    public final void a(j.e eVar) {
        int iB;
        boolean z2;
        boolean z3;
        c.i iVar;
        boolean z4;
        int iC;
        boolean z5;
        Map<String, c.i> map;
        if (!this.J) {
            Map<String, c.i> map2 = this.f27460b;
            if (map2 != null) {
                if (!map2.containsKey(eVar.f27496a)) {
                    iVar = eVar.c() > this.f27467i ? new c.i(eVar.e(), eVar.c(), eVar.b(), 1) : null;
                    if (iVar != null) {
                        c.h.a(U, "create qualifiedSample " + iVar);
                        this.f27460b.put(eVar.f27496a, iVar);
                        z4 = false;
                    } else {
                        c.h.a(U, "ignore unqualifiedSample with empty record");
                        z4 = true;
                    }
                } else {
                    iVar = this.f27460b.get(eVar.f27496a);
                    if (eVar.c() == 0 || eVar.c() < this.f27467i) {
                        z4 = true;
                    } else {
                        if (this.L == f.NQ_SPLIT) {
                            iVar.a((int) ((iVar.b() * 0.7d) + (eVar.c() * 0.3d)));
                        } else {
                            c.i iVar2 = this.f27461c.get(eVar.d());
                            if (iVar2 != null) {
                                c.h.a(U, "nq_continuous oldDatum " + iVar2);
                                iVar2.a((int) ((((double) iVar2.b()) * 0.8d) + (((double) eVar.c()) * 0.2d)));
                                c.h.a(U, "nq_continuous nowDatum " + iVar2);
                            }
                        }
                        z4 = false;
                    }
                    if (z4) {
                        c.h.a(U, "unqualifiedSample " + iVar + "ignore1");
                    } else {
                        iVar.g();
                        c.h.a(U, "increment qualifiedSample " + iVar);
                    }
                }
                if (z4) {
                    c.h.a(U, "unqualifiedSample " + iVar + "ignore2");
                } else {
                    if (iVar != null && iVar.d() == this.f27465g && (map = this.f27461c) != null) {
                        f fVar = this.L;
                        if (fVar == f.NQ_SPLIT) {
                            c.i iVarRemove = this.f27460b.remove(eVar.d());
                            iVarRemove.a(c.e.RTT);
                            c.i iVar3 = this.f27461c.get(eVar.d());
                            if (iVar3 != null) {
                                c.h.a(U, "oldDatum " + iVar3 + " pendingDatum: " + iVarRemove);
                                iVarRemove.a((int) ((((double) iVar3.b()) * 0.7d) + (((double) iVarRemove.b()) * 0.3d)));
                            }
                            this.f27461c.put(eVar.d(), iVarRemove);
                            c.h.a(U, "transfer " + iVarRemove + " to datum");
                        } else if (fVar == f.NQ_CONTINUOUS && !map.containsKey(eVar.d())) {
                            c.i iVarRemove2 = this.f27460b.remove(eVar.d());
                            iVarRemove2.a(c.e.RTT);
                            this.f27461c.put(eVar.d(), iVarRemove2);
                        }
                    }
                    Map<String, c.i> map3 = this.f27461c;
                    if (map3 != null && map3.size() > 0 && this.f27461c.get(eVar.d()) != null) {
                        c.i iVar4 = this.f27461c.get(eVar.d());
                        c.h.a(U, "stream: " + eVar.d() + " get a datum " + iVar4);
                        if (iVar4.f() == c.e.RTT && eVar.c() > this.f27467i) {
                            int iB2 = iVar4.b();
                            int iC2 = eVar.c();
                            if (eVar.e() == 1) {
                                iC = this.f27471m.f();
                            } else {
                                iC = this.f27471m.c();
                            }
                            c.h.a(U, " thresholdRtt: " + iC);
                            if (iC2 - iB2 > this.f27469k) {
                                c.h.a(U, "ascending for sample: " + iC2 + " datum: " + iB2);
                                z5 = true;
                            } else {
                                z5 = false;
                            }
                            if (this.f27462d.get(eVar.d()) != null) {
                                c.h hVar = this.f27462d.get(eVar.d());
                                if (!z5 || iC2 < iC) {
                                    hVar.f27448d = true;
                                    hVar.f27447c = c.d.ALTERNATIVE;
                                    c.h.a(U, "alternative to top ");
                                    hVar.f();
                                    c.h.a(U, "set a result " + hVar + " to PeerConClient.NetCompareResult.ALTERNATIVE");
                                }
                                hVar.b(z5);
                                hVar.c();
                                c.h.a(U, "compareResult info " + hVar);
                                if (hVar.f27446b >= this.f27466h) {
                                    if (!hVar.f27448d && hVar.f27445a) {
                                        hVar.f27447c = c.d.ASCENDING;
                                        c.h.a(U, "set a result " + hVar + " to PeerConClient.NetCompareResult.ASCENDING");
                                    } else {
                                        hVar.f27447c = c.d.ALTERNATIVE;
                                    }
                                    c.h.a(U, "clear result ");
                                    hVar.a();
                                }
                            } else {
                                c.h hVar2 = new c.h(z5, 1);
                                if (z5 && iC2 >= iC) {
                                    hVar2.f27445a = true;
                                } else {
                                    hVar2.f27448d = true;
                                    hVar2.f27445a = false;
                                    hVar2.f27446b = 0;
                                    hVar2.f27447c = c.d.ALTERNATIVE;
                                    c.h.a(U, "create a compare result " + hVar2 + " with interrupt ");
                                }
                                this.f27462d.put(eVar.d(), hVar2);
                                c.h.a(U, "has new compare result " + hVar2);
                            }
                        }
                    }
                }
            }
            if (eVar.e() == 1) {
                iB = this.f27471m.e();
            } else {
                iB = this.f27471m.b();
            }
            if (iB > 0 && eVar.b() > iB) {
                Map<String, Integer> map4 = this.f27463e;
                if (map4 != null) {
                    if (map4.get(eVar.d()) == null) {
                        this.f27463e.put(eVar.d(), 1);
                        c.h.a(U, " lost over threshold ,create a record ");
                    } else {
                        this.f27463e.put(eVar.d(), Integer.valueOf(this.f27463e.get(eVar.d()).intValue() + 1));
                        c.h.a(U, " lost over threshold ,update a record ");
                    }
                }
            } else if (this.f27463e.get(eVar.d()) != null) {
                this.f27463e.remove(eVar.d());
                c.h.a(U, " lost less than threshold ,remove a record ");
            }
            if (this.f27463e.size() > 0) {
                z2 = true;
                for (String str : this.f27463e.keySet()) {
                    c.h.a(U, "sid: " + str + " lost over threshold times " + this.f27463e.get(str));
                    if (this.f27463e.get(str).intValue() < this.f27468j) {
                        z2 = false;
                    }
                }
            } else {
                z2 = false;
            }
            if (this.f27462d.size() > 0) {
                z3 = true;
                for (String str2 : this.f27462d.keySet()) {
                    c.h hVar3 = this.f27462d.get(str2);
                    c.h.a(U, "sid: " + str2 + " rtt result: " + hVar3);
                    if (hVar3.f27447c != c.d.ASCENDING) {
                        z3 = false;
                    }
                }
            } else {
                z3 = false;
            }
            c.h.a(U, " rttTrigger: " + z3 + " lostTrigger: " + z2);
            if (z3 || z2) {
                c.h.a(U, " trigger leave channel by all peers defective ");
                b.d.k(0).f();
                this.J = true;
                return;
            }
            return;
        }
        c.h.a(U, " ignore for already triggered");
    }

    public void b(h hVar) {
        this.f27482x = new h(hVar);
    }

    public void a(String str, h hVar) {
        c.h.a(U, "min " + hVar.d() + " start " + hVar.e() + " max " + hVar.c());
        j.c cVar = this.f27459a.get(str);
        if (cVar != null) {
            if (hVar.c() > 0) {
                this.f27482x.c(hVar.c());
            }
            if (hVar.d() > 0) {
                this.f27482x.d(hVar.d());
            }
            if (hVar.e() > 0) {
                this.f27482x.e(hVar.e());
            }
            cVar.a(hVar);
        }
    }

    public void a(String str, String str2, String str3) {
        j.c cVar = this.f27459a.get(str);
        if (cVar != null) {
            SessionDescription.Type type = SessionDescription.Type.OFFER;
            if (!str2.equals("offer")) {
                type = SessionDescription.Type.ANSWER;
            }
            cVar.a(new SessionDescription(type, str3));
        }
    }

    public void a(int i2, int i3) {
        if (this.f27477s != null) {
            c.h.a(U, "adaptOutputFormat width " + i2 + " height: " + i3);
            c(i2, i3);
            if (i3 > this.f27482x.b() && i2 > this.f27482x.f()) {
                B();
                this.f27477s.adaptOutputFormat(i2, i3, this.f27482x.a());
                A();
                return;
            }
            this.f27477s.adaptOutputFormat(i2, i3, this.f27482x.a());
            return;
        }
        c.h.a(U, "localVideoSource is null.");
    }

    public void a(String str, boolean z2) {
        j.c cVar = this.f27459a.get(str);
        if (cVar != null) {
            c.h.a(U, "peerClient " + cVar + " opAudio " + z2);
            cVar.c(z2);
            cVar.b(z2);
        }
    }

    public void a(String str, double d3) {
        j.c cVar = this.f27459a.get(str);
        if (cVar != null) {
            c.h.a(U, "peerClient " + cVar + " streamId: " + str + " volume:" + d3);
            cVar.a(d3);
            return;
        }
        c.h.a(U, "peerClient is not found, streamId is: " + str);
    }

    public void a(double d3) {
        Map<String, j.c> map = this.f27459a;
        if (map == null || map.size() <= 0) {
            return;
        }
        Iterator<String> it = this.f27459a.keySet().iterator();
        while (it.hasNext()) {
            j.c cVar = this.f27459a.get(it.next());
            if (cVar.o() == 2) {
                c.h.a(U, "peerClient " + cVar + " volume:" + d3);
                cVar.a(d3);
            }
        }
    }

    public String a(h hVar, boolean z2, boolean z3) {
        c.h.a(U, " CreateStream mStreamCreate: " + this.F + " mReOpenCamera: " + this.I);
        if (this.F && this.I) {
            this.I = false;
            j();
        }
        if (!this.F && this.f27473o.get(j.a.f27376e) == null) {
            if (z2 || z3) {
                if (hVar != null) {
                    this.f27482x = hVar;
                }
                c.h.a(U, " add audio/video " + z3 + " " + z2);
                MediaStream mediaStreamCreateLocalMediaStream = this.f27472n.createLocalMediaStream(j.a.f27376e);
                if (z3) {
                    g();
                    if (this.f27474p != null) {
                        c.h.a(U, " createAudioTrack " + this.f27474p);
                        mediaStreamCreateLocalMediaStream.addTrack(this.f27474p);
                    } else {
                        c.h.c(U, " CreateStream failed localAudioTrack is null! ");
                    }
                }
                if (z2) {
                    this.f27472n.setVideoHwAccelerationOptions(this.C.getEglBaseContext(), this.C.getEglBaseContext());
                    i();
                    if (this.f27478t == null) {
                        c.h.a(U, "create stream aborted for create video capture failed");
                        return "video capture create failed";
                    }
                    if (this.T == null) {
                        this.T = new VideoPreProcessor();
                    }
                    this.T.load(this.f27478t);
                    j();
                    VideoTrack videoTrack = this.f27476r;
                    if (videoTrack != null) {
                        mediaStreamCreateLocalMediaStream.addTrack(videoTrack);
                    } else {
                        c.h.c(U, " CreateStream failed localVideoTrack is null! ");
                    }
                }
                this.f27473o.put(j.a.f27376e, mediaStreamCreateLocalMediaStream);
                this.F = true;
                h hVar2 = this.f27484z;
                if (hVar2 != null) {
                    a(hVar2.f(), this.f27484z.b());
                }
            }
            c.h.a(U, " CreateStream finish: ");
            return "";
        }
        c.h.a(U, " stream " + this.f27473o.get(j.a.f27376e) + "already created");
        return "";
    }

    public void a(h hVar) {
        if (this.f27473o.get(j.a.f27379h) != null) {
            return;
        }
        this.f27483y = hVar;
        MediaStream mediaStreamCreateLocalMediaStream = this.f27472n.createLocalMediaStream(j.a.f27379h);
        this.f27472n.setVideoHwAccelerationOptions(this.C.getEglBaseContext(), this.C.getEglBaseContext());
        a(this.O);
        h();
        mediaStreamCreateLocalMediaStream.addTrack(this.f27479u);
        this.f27473o.put(j.a.f27379h, mediaStreamCreateLocalMediaStream);
    }

    public void a(VideoSink videoSink) {
        boolean z2;
        if (videoSink == null || this.f27476r == null) {
            return;
        }
        if (this.A == null) {
            this.A = new ArrayList<>();
        }
        if (videoSink instanceof SurfaceViewRenderer) {
            if (!this.A.contains(videoSink)) {
                this.A.add(videoSink);
                this.f27476r.addSink(videoSink);
                return;
            } else {
                c.h.a(U, "mlocalCamviews surfaceview already has render " + this.A.size());
                return;
            }
        }
        boolean z3 = videoSink instanceof TextureViewRenderer;
        if (z3) {
            Iterator<VideoSink> it = this.A.iterator();
            while (true) {
                if (!it.hasNext()) {
                    z2 = true;
                    break;
                }
                VideoSink next = it.next();
                if (z3 && ((TextureViewRenderer) next).getTextureView().equals(((TextureViewRenderer) videoSink).getTextureView())) {
                    c.h.a(U, "already has same texture view " + this.A.size());
                    z2 = false;
                    break;
                }
            }
            if (z2) {
                this.A.add(videoSink);
                this.f27476r.addSink(videoSink);
            }
        }
    }

    public void a(VideoSink videoSink, boolean z2) {
        boolean z3;
        if (this.f27476r == null || this.A == null) {
            return;
        }
        c.h.a(U, "stopRender specified before mlocalCamviews : " + this.A.size());
        Iterator<VideoSink> it = this.A.iterator();
        while (true) {
            if (!it.hasNext()) {
                z3 = false;
                break;
            }
            VideoSink next = it.next();
            if (next.equals(videoSink)) {
                this.f27476r.removeSink(videoSink);
                boolean z4 = next instanceof TextureViewRenderer;
                z3 = true;
                break;
            }
        }
        if (z3) {
            c.h.a(U, "stopRender mlocalCamviews specified remove : " + videoSink);
            this.A.remove(videoSink);
        }
        c.h.a(U, "stopRender specified after mlocalCamviews : " + this.A.size());
    }

    public void a(String str, VideoRenderer.Callbacks callbacks) {
        j.c cVar = this.f27459a.get(str);
        c.h.a(U, " startRemoteRenderCallback " + callbacks + " client:" + cVar);
        if (cVar != null) {
            c.h.a(U, " client ice state " + cVar.l());
            if (callbacks instanceof TextureViewRenderer) {
                ((TextureViewRenderer) callbacks).setPeerConnectionCallBack(cVar);
                if (cVar.l() != f.b.LOGIC_ICE_STATE_CONNECTED.ordinal() && cVar.l() != f.b.LOGIC_ICE_STATE_COMPLETE.ordinal()) {
                    c.h.a(U, " ice state not = connected or complete ,do not add render");
                    return;
                } else {
                    c.h.a(U, "start texture render,add render");
                    cVar.a(callbacks);
                    return;
                }
            }
            if (callbacks instanceof SurfaceViewRenderer) {
                ((SurfaceViewRenderer) callbacks).setPeerConnectionCallBack(cVar);
                if (cVar.l() == f.b.LOGIC_ICE_STATE_CONNECTED.ordinal() || cVar.l() == f.b.LOGIC_ICE_STATE_COMPLETE.ordinal()) {
                    c.h.a(U, "start surface render,add render");
                    cVar.a(callbacks);
                    return;
                }
                return;
            }
            return;
        }
        c.h.a(U, "peer client is null ,startRemoteRender failed");
    }

    public void a(String str, boolean z2, VideoRenderer.Callbacks callbacks) {
        j.c cVar = this.f27459a.get(str);
        c.h.a(U, " stopRemoteRender streamId: " + str + " client: " + cVar);
        if (cVar != null) {
            cVar.a(z2, callbacks);
        }
    }

    public void a() {
        c.h.a(U, "DeleteScreenStream ");
        this.f27473o.remove(j.a.f27379h);
        D();
        VideoSource videoSource = this.f27480v;
        if (videoSource != null) {
            videoSource.dispose();
            this.f27480v = null;
        }
        this.B = null;
        this.f27479u = null;
    }

    public void a(int i2) {
        VideoCapturer videoCapturer = this.f27478t;
        if (videoCapturer instanceof CameraVideoCapturer) {
            ((CameraVideoCapturer) videoCapturer).setCameraId(i2, null);
        }
    }

    public final RtcCameraRTSPCapturer a(RtcCameraRTSPEnumerator rtcCameraRTSPEnumerator) {
        return (RtcCameraRTSPCapturer) rtcCameraRTSPEnumerator.createCapturer("rtspCamera", new c(this, null));
    }

    public final VideoCapturer a(CameraEnumerator cameraEnumerator) {
        String[] deviceNames = cameraEnumerator.getDeviceNames();
        c.h.a(U, "Start createCameraCapturer.");
        int iC = d.b.c();
        this.D = iC;
        int i2 = 0;
        a aVar = null;
        if (iC == 1) {
            int length = deviceNames.length;
            while (i2 < length) {
                String str = deviceNames[i2];
                if (cameraEnumerator.isFrontFacing(str)) {
                    c.h.a(U, "Creating front camera capturer. name is:" + str);
                    CameraVideoCapturer cameraVideoCapturerCreateCapturer = cameraEnumerator.createCapturer(str, new c(this, aVar));
                    if (cameraVideoCapturerCreateCapturer != null) {
                        return cameraVideoCapturerCreateCapturer;
                    }
                }
                i2++;
            }
        } else if (iC == 2) {
            c.h.a(U, "Looking for back cameras.");
            int length2 = deviceNames.length;
            while (i2 < length2) {
                String str2 = deviceNames[i2];
                if (cameraEnumerator.isBackFacing(str2)) {
                    c.h.a(U, "Creating back camera capturer. name is: " + str2);
                    CameraVideoCapturer cameraVideoCapturerCreateCapturer2 = cameraEnumerator.createCapturer(str2, new c(this, aVar));
                    if (cameraVideoCapturerCreateCapturer2 != null) {
                        return cameraVideoCapturerCreateCapturer2;
                    }
                }
                i2++;
            }
        } else {
            this.E = d.b.g();
            c.h.a(U, "Start to create video on CameraId:" + this.E);
            if (Arrays.asList(deviceNames).contains(this.E)) {
                c.h.a(U, "Creating specific camera capturer. name is: " + this.E);
                CameraVideoCapturer cameraVideoCapturerCreateCapturer3 = cameraEnumerator.createCapturer(this.E, new c(this, aVar));
                if (cameraVideoCapturerCreateCapturer3 != null) {
                    return cameraVideoCapturerCreateCapturer3;
                }
            } else {
                c.h.a(U, "Creating specific camera failed, name is: " + this.E);
            }
        }
        return null;
    }

    public void a(MediaProjection.Callback callback) {
        Intent intent = X;
        if (intent != null) {
            this.f27481w = ScreenCapturerAndroid.getInstance(intent, callback);
        }
        c.h.a(U, "screenCapturer is: " + this.f27481w);
    }

    public final void a(PeerConnectionFactory.Options options) {
        PeerConnectionFactory.initialize(PeerConnectionFactory.InitializationOptions.builder(c.e.b()).setEnableVideoHwAcceleration(d.b.o()).setEnableInternalTracer(true).createInitializationOptions());
        PeerConnectionFactory.Builder builder = PeerConnectionFactory.builder();
        if (options != null) {
            builder.setOptions(options);
        }
        c.h.a(U, "InitPeerConnectionFactory CoreEnvHelper.getVideoHardWareAcceleration(): " + d.b.o());
        if (d.b.o()) {
            builder.setVideoDecoderFactory(new DefaultVideoDecoderFactory(this.C.getEglBaseContext()));
        }
        this.f27472n = builder.createPeerConnectionFactory();
        if (NativeLibrary.isLoaded()) {
            if (d.b.x()) {
                Logging.enableLogToDebugOutput(Logging.Severity.LS_WARNING);
            } else {
                Logging.enableLogToDebugOutput(Logging.Severity.LS_NONE);
            }
        }
    }

    public void a(j.f fVar) {
        this.f27471m = fVar;
    }

    public void a(String str, boolean z2, boolean z3) {
        this.f27472n.startPlayAudioFile(str, z2, z3);
    }
}
