package b;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.projection.MediaProjectionManager;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import androidx.core.app.NotificationCompat;
import c.h;
import cn.hutool.core.text.CharPool;
import cn.hutool.core.text.StrPool;
import com.aliyun.player.alivcplayerexpand.util.database.DatabaseManager;
import com.hyphenate.easeui.constants.EaseConstant;
import com.tencent.smtt.sdk.TbsVideoCacheTask;
import com.umeng.socialize.net.dplus.CommonNetImpl;
import com.yikaobang.yixue.R2;
import core.data.AuthInfo;
import core.data.MediaOp;
import core.data.MixProfile;
import core.data.RecordProfile;
import core.data.StreamInfo;
import core.interfaces.CameraEventListener;
import core.interfaces.DataProvider;
import core.interfaces.FirstFrameRendered;
import core.interfaces.RtcNotification;
import core.interfaces.ScreenShot;
import core.interfaces.VideoFramePreProcessListener;
import core.monitor.LogReportManager;
import core.monitor.MonitorService;
import core.renderer.CoreSurfaceViewRenderer;
import core.renderer.CoreTextureViewRenderer;
import core.renderer.SurfaceViewGroup;
import d.a;
import j.d;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;
import k.a;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.wrtca.api.EglRenderer;
import org.wrtca.api.SurfaceViewRenderer;
import org.wrtca.audio.RtcAudioParams;
import org.wrtca.customize.RtcExDevice2YUVCapturer;
import org.wrtca.customize.RtcNativeOperation;
import org.wrtca.util.ContextUtils;
import org.wrtca.video.TextureViewRenderer;

/* loaded from: classes.dex */
public class d implements b.a, e.c, a.d {
    public static d F = null;
    public static Object G = new Object();
    public static final String H = "CoreRtcEngineImpl";
    public static e.i I;
    public static boolean J;
    public static int K;
    public boolean A;
    public Map<String, l3> B;

    /* renamed from: b, reason: collision with root package name */
    public Handler f1903b;

    /* renamed from: c, reason: collision with root package name */
    public HandlerThread f1904c;

    /* renamed from: f, reason: collision with root package name */
    public Object f1907f;

    /* renamed from: g, reason: collision with root package name */
    public Map<String, Object> f1908g;

    /* renamed from: h, reason: collision with root package name */
    public boolean f1909h;

    /* renamed from: i, reason: collision with root package name */
    public boolean f1910i;

    /* renamed from: j, reason: collision with root package name */
    public boolean f1911j;

    /* renamed from: k, reason: collision with root package name */
    public boolean f1912k;

    /* renamed from: l, reason: collision with root package name */
    public boolean f1913l;

    /* renamed from: m, reason: collision with root package name */
    public boolean f1914m;

    /* renamed from: n, reason: collision with root package name */
    public boolean f1915n;

    /* renamed from: o, reason: collision with root package name */
    public boolean f1916o;

    /* renamed from: p, reason: collision with root package name */
    public boolean f1917p;

    /* renamed from: q, reason: collision with root package name */
    public boolean f1918q;

    /* renamed from: r, reason: collision with root package name */
    public k.a f1919r;

    /* renamed from: s, reason: collision with root package name */
    public int f1920s;

    /* renamed from: t, reason: collision with root package name */
    public int f1921t;

    /* renamed from: u, reason: collision with root package name */
    public int f1922u;

    /* renamed from: v, reason: collision with root package name */
    public boolean f1923v;

    /* renamed from: w, reason: collision with root package name */
    public boolean f1924w;

    /* renamed from: x, reason: collision with root package name */
    public d.b f1925x;

    /* renamed from: y, reason: collision with root package name */
    public boolean f1926y;

    /* renamed from: z, reason: collision with root package name */
    public boolean f1927z;

    /* renamed from: d, reason: collision with root package name */
    public b.b f1905d = null;

    /* renamed from: e, reason: collision with root package name */
    public e.c f1906e = null;
    public Messenger C = null;
    public ServiceConnection D = new a();
    public Handler E = new Handler(new l0());

    public class a implements ServiceConnection {

        /* renamed from: b.d$a$a, reason: collision with other inner class name */
        public class RunnableC0012a implements Runnable {
            public RunnableC0012a() {
            }

            @Override // java.lang.Runnable
            public void run() throws RemoteException {
                a.this.a();
            }
        }

        public a() {
        }

        public void a() throws RemoteException {
            if (d.J) {
                Message messageObtain = Message.obtain(null, 0, 0, 0);
                messageObtain.replyTo = new Messenger(d.this.E);
                try {
                    d.this.C.send(messageObtain);
                } catch (RemoteException e2) {
                    e2.printStackTrace();
                }
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.d(d.H, "ServiceConnection-onServiceConnected " + Thread.currentThread());
            d.this.C = new Messenger(iBinder);
            boolean unused = d.J = true;
            new Handler(Looper.getMainLooper()).post(new RunnableC0012a());
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            Log.d(d.H, "ServiceConnection-onServiceDisconnected");
            d.this.C = null;
            boolean unused = d.J = false;
        }
    }

    public class a0 implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ StreamInfo f1930a;

        /* renamed from: b, reason: collision with root package name */
        public final /* synthetic */ Object f1931b;

        /* renamed from: c, reason: collision with root package name */
        public final /* synthetic */ int f1932c;

        /* renamed from: d, reason: collision with root package name */
        public final /* synthetic */ FirstFrameRendered f1933d;

        public class a implements Runnable {

            /* renamed from: a, reason: collision with root package name */
            public final /* synthetic */ Object f1935a;

            public a(Object obj) {
                this.f1935a = obj;
            }

            @Override // java.lang.Runnable
            public void run() {
                ((CoreSurfaceViewRenderer) this.f1935a).setScaleType(a0.this.f1932c);
            }
        }

        public class b implements Runnable {

            /* renamed from: a, reason: collision with root package name */
            public final /* synthetic */ CoreSurfaceViewRenderer f1937a;

            public b(CoreSurfaceViewRenderer coreSurfaceViewRenderer) {
                this.f1937a = coreSurfaceViewRenderer;
            }

            @Override // java.lang.Runnable
            public void run() {
                this.f1937a.setScaleType(a0.this.f1932c);
            }
        }

        public class c implements Runnable {

            /* renamed from: a, reason: collision with root package name */
            public final /* synthetic */ Object f1939a;

            public c(Object obj) {
                this.f1939a = obj;
            }

            @Override // java.lang.Runnable
            public void run() {
                ((CoreTextureViewRenderer) this.f1939a).setScaleType(a0.this.f1932c);
            }
        }

        public a0(StreamInfo streamInfo, Object obj, int i2, FirstFrameRendered firstFrameRendered) {
            this.f1930a = streamInfo;
            this.f1931b = obj;
            this.f1932c = i2;
            this.f1933d = firstFrameRendered;
        }

        @Override // java.lang.Runnable
        public void run() {
            Object obj;
            ArrayList arrayList;
            String strA = a.h.a(this.f1930a.getMediaType());
            c.h.a(d.H, "render local key " + strA);
            c.h.a(d.H, "render local info " + this.f1930a);
            Object obj2 = this.f1931b;
            if (obj2 instanceof SurfaceViewGroup) {
                CoreSurfaceViewRenderer surfaceView = ((SurfaceViewGroup) obj2).getSurfaceView();
                c.h.a(d.H, "render local SurfaceViewGroup");
                surfaceView.setStreamInfo(this.f1930a);
                surfaceView.post(new a(surfaceView));
                e.a.a().g().b(this.f1930a.getMediaType(), surfaceView);
                obj = surfaceView;
            } else if (obj2 instanceof CoreSurfaceViewRenderer) {
                c.h.a(d.H, "render local CoreSurfaceViewRenderer");
                CoreSurfaceViewRenderer coreSurfaceViewRenderer = (CoreSurfaceViewRenderer) obj2;
                coreSurfaceViewRenderer.setStreamInfo(this.f1930a);
                coreSurfaceViewRenderer.setFrameRendered(this.f1933d);
                coreSurfaceViewRenderer.post(new b(coreSurfaceViewRenderer));
                e.a.a().g().b(this.f1930a.getMediaType(), obj2);
                obj = obj2;
            } else if (obj2 instanceof CoreTextureViewRenderer) {
                c.h.a(d.H, "render local CoreTextureViewRenderer");
                CoreTextureViewRenderer coreTextureViewRenderer = (CoreTextureViewRenderer) obj2;
                coreTextureViewRenderer.setStreamInfo(this.f1930a);
                coreTextureViewRenderer.getTextureView().post(new c(obj2));
                e.a.a().g().b(this.f1930a.getMediaType(), obj2);
                obj = obj2;
            } else {
                obj = null;
            }
            d.this.h();
            if (d.this.f1908g.containsKey(strA)) {
                arrayList = (ArrayList) d.this.f1908g.get(strA);
            } else {
                ArrayList arrayList2 = new ArrayList();
                d.this.f1908g.put(strA, arrayList2);
                arrayList = arrayList2;
            }
            if (arrayList != null) {
                if (arrayList.contains(obj)) {
                    c.h.a(d.H, "local already has render" + obj);
                } else {
                    c.h.a(d.H, "local add new render" + obj);
                    arrayList.add(obj);
                }
            }
            c.h.a(d.H, "render local render map");
            d.this.h();
        }
    }

    public class a1 implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ MixProfile f1941a;

        public a1(MixProfile mixProfile) {
            this.f1941a = mixProfile;
        }

        @Override // java.lang.Runnable
        public void run() {
            e.a.a().g().a(this.f1941a);
        }
    }

    public class a2 implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ String f1943a;

        public a2(String str) {
            this.f1943a = str;
        }

        @Override // java.lang.Runnable
        public void run() throws JSONException {
            try {
                c.h.a(d.H, "onLogicServerReconnected");
                JSONObject jSONObject = new JSONObject(this.f1943a);
                jSONObject.getString("uid");
                String string = jSONObject.getString("roomid");
                if (d.this.f1905d != null) {
                    d.this.f1923v = true;
                    d.this.f1924w = false;
                    d.this.f1905d.onRejoinRoomResult(string);
                }
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
    }

    public class a3 implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ int f1945a;

        /* renamed from: b, reason: collision with root package name */
        public final /* synthetic */ String f1946b;

        public a3(int i2, String str) {
            this.f1945a = i2;
            this.f1946b = str;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (d.this.f1905d != null) {
                d.this.f1905d.onRecordStart(this.f1945a, this.f1946b);
            }
        }
    }

    public class b implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ String f1948a;

        public b(String str) {
            this.f1948a = str;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (d.this.f1905d != null) {
                c.h.a(d.H, "onStartLocalRenderFailed: " + this.f1948a);
                d.this.f1905d.onStartLocalRenderFailed(this.f1948a);
            }
        }
    }

    public class b0 implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ int f1950a;

        public b0(int i2) {
            this.f1950a = i2;
        }

        @Override // java.lang.Runnable
        public void run() {
            e.a.a().g().a(this.f1950a);
            d.this.f1908g.remove(a.h.a(this.f1950a));
            c.h.a(d.H, " stop preview finish print");
            d.this.h();
        }
    }

    public class b1 implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ String[] f1952a;

        public b1(String[] strArr) {
            this.f1952a = strArr;
        }

        @Override // java.lang.Runnable
        public void run() {
            e.a.a().g().a(1, this.f1952a);
        }
    }

    public class b2 implements Runnable {
        public b2() {
        }

        @Override // java.lang.Runnable
        public void run() {
            if (d.this.f1905d != null) {
                d.this.f1923v = false;
                d.this.f1924w = false;
                d.this.f1905d.onServerDisconnect();
            }
        }
    }

    public class b3 implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ int f1955a;

        public b3(int i2) {
            this.f1955a = i2;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (d.this.f1905d != null) {
                d.this.f1905d.onRecordStop(this.f1955a);
            }
        }
    }

    public class c implements Runnable {
        public c() {
        }

        @Override // java.lang.Runnable
        public void run() {
            if (d.this.f1905d != null) {
                d.this.f1905d.onAudioFileFinish();
            }
        }
    }

    public class c0 implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ int f1958a;

        /* renamed from: b, reason: collision with root package name */
        public final /* synthetic */ Object f1959b;

        public c0(int i2, Object obj) {
            this.f1958a = i2;
            this.f1959b = obj;
        }

        @Override // java.lang.Runnable
        public void run() {
            Object next;
            c.h.a(d.H, "stopPreview preview mediatype : " + this.f1958a + " render: " + this.f1959b);
            String strA = a.h.a(this.f1958a);
            Object obj = d.this.f1908g.get(strA);
            d.this.h();
            if (obj != null) {
                ArrayList arrayList = (ArrayList) obj;
                Iterator it = arrayList.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        next = null;
                        break;
                    }
                    next = it.next();
                    if (!(next instanceof CoreSurfaceViewRenderer)) {
                        if ((next instanceof CoreTextureViewRenderer) && this.f1959b.equals((CoreTextureViewRenderer) next)) {
                            c.h.a(d.H, "find sink to remove " + next);
                            break;
                        }
                    } else {
                        CoreSurfaceViewRenderer coreSurfaceViewRenderer = (CoreSurfaceViewRenderer) next;
                        Object obj2 = this.f1959b;
                        if (!(obj2 instanceof SurfaceViewGroup)) {
                            if ((obj2 instanceof CoreSurfaceViewRenderer) && obj2.equals(next)) {
                                next = this.f1959b;
                                break;
                            }
                        } else if (((SurfaceViewGroup) obj2).getSurfaceView().equals(coreSurfaceViewRenderer)) {
                            next = this.f1959b;
                            break;
                        }
                    }
                }
                if (next != null) {
                    arrayList.remove(next);
                    c.h.a(d.H, "");
                    if (arrayList.isEmpty()) {
                        c.h.a(d.H, "sink list is empty,remove it");
                        d.this.f1908g.remove(strA);
                    }
                    e.a.a().g().a(this.f1958a, next);
                }
            }
        }
    }

    public class c1 implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ MixProfile f1961a;

        public c1(MixProfile mixProfile) {
            this.f1961a = mixProfile;
        }

        @Override // java.lang.Runnable
        public void run() {
            e.a.a().g().a(this.f1961a);
        }
    }

    public class c2 implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ String f1963a;

        public c2(String str) {
            this.f1963a = str;
        }

        @Override // java.lang.Runnable
        public void run() throws JSONException {
            String string;
            String string2;
            c.h.a(d.H, " onLogicJoinRoom " + this.f1963a);
            try {
                JSONObject jSONObject = new JSONObject(this.f1963a);
                int iE = jSONObject.getInt("code");
                String string3 = jSONObject.getString("msg");
                JSONObject jSONObject2 = jSONObject.getJSONObject("data");
                if (jSONObject2 != null) {
                    string = jSONObject2.getString("roomid");
                    string2 = jSONObject2.getString("uid");
                } else {
                    string = "";
                    string2 = "";
                }
                if (iE == 0) {
                    d.this.f1923v = true;
                    d.this.f1924w = false;
                    c.h.a(d.H, "autoPublish " + d.this.f1912k + " pubcam " + d.this.f1915n + " mAudiomode: " + d.this.f1909h);
                    iE = d.this.e();
                }
                if (d.this.f1905d != null) {
                    c.h.a(d.H, " onJoinRoomResult begin resultcode " + iE);
                    d.this.f1905d.onJoinRoomResult(iE, string3, string, string2);
                    c.h.a(d.H, " onJoinRoomResult end");
                }
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
    }

    public class c3 implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ String f1965a;

        /* renamed from: b, reason: collision with root package name */
        public final /* synthetic */ boolean f1966b;

        public c3(String str, boolean z2) {
            this.f1965a = str;
            this.f1966b = z2;
        }

        @Override // java.lang.Runnable
        public void run() throws JSONException {
            try {
                JSONObject jSONObject = new JSONObject(this.f1965a);
                JSONObject jSONObject2 = jSONObject.getJSONObject("data");
                int i2 = jSONObject.getInt(NotificationCompat.CATEGORY_ERROR);
                String string = jSONObject.getString("msg");
                if (d.this.f1905d != null) {
                    if (!this.f1966b) {
                        d.this.f1905d.onQueryMix(i2, string, 0, "", "");
                    } else {
                        d.this.f1905d.onQueryMix(i2, string, jSONObject2.getInt("type"), jSONObject2.getString("mix_id"), jSONObject2.has(TbsVideoCacheTask.KEY_VIDEO_CACHE_PARAM_FILENAME) ? jSONObject2.getString(TbsVideoCacheTask.KEY_VIDEO_CACHE_PARAM_FILENAME) : "");
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    /* renamed from: b.d$d, reason: collision with other inner class name */
    public class RunnableC0013d implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ String f1968a;

        public RunnableC0013d(String str) {
            this.f1968a = str;
        }

        @Override // java.lang.Runnable
        public void run() throws JSONException {
            try {
                JSONObject jSONObject = new JSONObject(this.f1968a);
                int i2 = jSONObject.getInt("cmdtype");
                String string = jSONObject.getString("userid");
                if (d.this.f1905d != null) {
                    d.this.f1905d.onLogOffNotify(i2, string);
                }
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
    }

    public class d0 implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ int f1970a;

        /* renamed from: b, reason: collision with root package name */
        public final /* synthetic */ boolean f1971b;

        /* renamed from: c, reason: collision with root package name */
        public final /* synthetic */ boolean f1972c;

        /* renamed from: d, reason: collision with root package name */
        public final /* synthetic */ boolean f1973d;

        /* renamed from: e, reason: collision with root package name */
        public final /* synthetic */ boolean f1974e;

        public d0(int i2, boolean z2, boolean z3, boolean z4, boolean z5) {
            this.f1970a = i2;
            this.f1971b = z2;
            this.f1972c = z3;
            this.f1973d = z4;
            this.f1974e = z5;
        }

        @Override // java.lang.Runnable
        public void run() {
            ArrayList arrayList;
            MediaOp mediaOp = new MediaOp();
            mediaOp.setMediaType(this.f1970a);
            String strA = a.h.a(this.f1970a);
            if (d.this.f1909h) {
                mediaOp.setEnableVideo(false);
                mediaOp.setEnableAudio(true);
            } else {
                mediaOp.setEnableVideo(this.f1971b && this.f1972c);
                mediaOp.setEnableAudio(this.f1973d && this.f1974e);
            }
            e.a.a().g().a(mediaOp);
            if (d.this.f1907f != null) {
                if (d.this.f1908g.containsKey(strA)) {
                    arrayList = (ArrayList) d.this.f1908g.get(strA);
                } else {
                    arrayList = new ArrayList();
                    d.this.f1908g.put(strA, arrayList);
                }
                arrayList.add(d.this.f1907f);
                d.this.f1907f = null;
            }
        }
    }

    public class d1 implements Runnable {
        public d1() {
        }

        @Override // java.lang.Runnable
        public void run() {
            e.a.a().g().queryMix();
        }
    }

    public class d2 implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ boolean f1977a;

        public d2(boolean z2) {
            this.f1977a = z2;
        }

        @Override // java.lang.Runnable
        public void run() {
            d.this.f1913l = this.f1977a;
        }
    }

    public class d3 implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ String f1979a;

        /* renamed from: b, reason: collision with root package name */
        public final /* synthetic */ boolean f1980b;

        /* renamed from: c, reason: collision with root package name */
        public final /* synthetic */ int f1981c;

        public d3(String str, boolean z2, int i2) {
            this.f1979a = str;
            this.f1980b = z2;
            this.f1981c = i2;
        }

        @Override // java.lang.Runnable
        public void run() throws JSONException {
            if (d.this.f1905d != null) {
                try {
                    JSONObject jSONObject = new JSONObject(this.f1979a);
                    int i2 = jSONObject.getInt(NotificationCompat.CATEGORY_ERROR);
                    String string = jSONObject.getString("msg");
                    JSONObject jSONObject2 = jSONObject.getJSONObject("data");
                    if (this.f1980b) {
                        String string2 = jSONObject2.getString("mix_id");
                        String string3 = jSONObject2.getString("user_id");
                        String string4 = jSONObject2.getString("room_id");
                        int i3 = this.f1981c;
                        if (i3 == 2) {
                            d.this.f1905d.onRecordStatusNotify(i2 == 0 ? 4 : 7, i2, string, string3, string4, string2, jSONObject2.getString(TbsVideoCacheTask.KEY_VIDEO_CACHE_PARAM_FILENAME));
                        } else if (i3 == 1) {
                            d.this.f1905d.onRelayStatusNotify(i2 == 0 ? 0 : 3, i2, string, string3, string4, string2, null);
                        } else if (i3 == 4) {
                            if (i2 == 0) {
                                d.this.f1905d.onRelayStatusNotify(9, i2, string, string3, string4, string2, null);
                                d.this.f1905d.onRecordStatusNotify(9, i2, string, string3, string4, string2, null);
                            } else {
                                d.this.f1905d.onRelayStatusNotify(11, i2, string, string3, string4, string2, null);
                                d.this.f1905d.onRecordStatusNotify(11, i2, string, string3, string4, string2, null);
                            }
                        }
                    } else {
                        JSONObject jSONObject3 = jSONObject2.getJSONObject("data").getJSONObject("data");
                        int i4 = jSONObject3.getInt("type");
                        String string5 = jSONObject3.getString("user_id");
                        String string6 = jSONObject3.getString("room_id");
                        if (i4 == 2) {
                            d.this.f1905d.onRecordStatusNotify(7, i2, string, string5, string6, "", "");
                        } else if (i4 == 1) {
                            d.this.f1905d.onRelayStatusNotify(3, i2, string, string5, string6, "", null);
                        } else if (i4 == 4) {
                            d.this.f1905d.onRelayStatusNotify(11, i2, string, string5, string6, "", null);
                            d.this.f1905d.onRecordStatusNotify(11, i2, string, string5, string6, "", null);
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
    }

    public class e implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ String f1983a;

        public e(String str) {
            this.f1983a = str;
        }

        @Override // java.lang.Runnable
        public void run() throws JSONException {
            try {
                JSONObject jSONObject = new JSONObject(this.f1983a);
                int i2 = jSONObject.getInt("code");
                String string = jSONObject.getString("msg");
                if (d.this.f1905d != null) {
                    d.this.f1905d.onMessageNotify(i2, string);
                }
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
    }

    public class e0 implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ int f1985a;

        public e0(int i2) {
            this.f1985a = i2;
        }

        @Override // java.lang.Runnable
        public void run() {
            e.a.a().g().c(this.f1985a, d.this.f1908g.remove(a.h.a(this.f1985a)));
            d.this.f1914m = false;
        }
    }

    public class e1 implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ StreamInfo[] f1987a;

        public e1(StreamInfo[] streamInfoArr) {
            this.f1987a = streamInfoArr;
        }

        @Override // java.lang.Runnable
        public void run() {
            e.a.a().g().b(this.f1987a);
        }
    }

    public class e2 implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ String f1989a;

        public e2(String str) {
            this.f1989a = str;
        }

        @Override // java.lang.Runnable
        public void run() throws JSONException {
            d.this.f1924w = false;
            if (d.this.f1905d != null) {
                try {
                    JSONObject jSONObject = new JSONObject(this.f1989a);
                    d.this.f1905d.onLeaveRoomResult(jSONObject.getInt("code"), jSONObject.getString("msg"), jSONObject.getString("roomid"));
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
            }
        }
    }

    public class e3 implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ String f1991a;

        /* renamed from: b, reason: collision with root package name */
        public final /* synthetic */ boolean f1992b;

        /* renamed from: c, reason: collision with root package name */
        public final /* synthetic */ int f1993c;

        public e3(String str, boolean z2, int i2) {
            this.f1991a = str;
            this.f1992b = z2;
            this.f1993c = i2;
        }

        @Override // java.lang.Runnable
        public void run() throws JSONException {
            String[] strArr;
            if (d.this.f1905d != null) {
                try {
                    JSONObject jSONObject = new JSONObject(this.f1991a);
                    c.h.a(d.H, "onLogicStopMix " + jSONObject);
                    int i2 = jSONObject.getInt(NotificationCompat.CATEGORY_ERROR);
                    String string = jSONObject.getString("msg");
                    JSONObject jSONObject2 = jSONObject.getJSONObject("data");
                    int i3 = 0;
                    if (!this.f1992b) {
                        JSONObject jSONObject3 = jSONObject2.getJSONObject("data").getJSONObject("data");
                        int i4 = jSONObject3.getInt("type");
                        String string2 = jSONObject3.getString("user_id");
                        String string3 = jSONObject3.getString("room_id");
                        if (i4 == 2) {
                            d.this.f1905d.onRecordStatusNotify(7, i2, string, string2, string3, "", "");
                            return;
                        }
                        if (i4 == 1) {
                            JSONArray jSONArray = jSONObject3.getJSONArray("pushurl");
                            int length = jSONArray.length();
                            String[] strArr2 = new String[length];
                            while (i3 < length) {
                                strArr2[i3] = jSONArray.getString(i3);
                                i3++;
                            }
                            d.this.f1905d.onRelayStatusNotify(3, i2, string, string2, string3, "", strArr2);
                            return;
                        }
                        return;
                    }
                    String string4 = jSONObject2.getString("user_id");
                    String string5 = jSONObject2.getString("room_id");
                    String string6 = jSONObject2.getString("mix_id");
                    int i5 = this.f1993c;
                    if (i5 == 2) {
                        if (i2 == 0) {
                            d.this.f1905d.onRecordStatusNotify(5, i2, string, string4, string5, string6, "");
                            return;
                        } else {
                            d.this.f1905d.onRecordStatusNotify(7, i2, string, string4, string5, string6, "");
                            return;
                        }
                    }
                    if (i5 == 1) {
                        if (jSONObject2.has("pushurl")) {
                            JSONArray jSONArray2 = jSONObject2.getJSONArray("pushurl");
                            int length2 = jSONArray2.length();
                            String[] strArr3 = new String[length2];
                            while (i3 < length2) {
                                strArr3[i3] = jSONArray2.getString(i3);
                                i3++;
                            }
                            strArr = strArr3;
                        } else {
                            strArr = null;
                        }
                        if (i2 == 0) {
                            d.this.f1905d.onRelayStatusNotify(1, i2, string, string4, string5, string6, strArr);
                        } else {
                            d.this.f1905d.onRelayStatusNotify(3, i2, string, string4, string5, string6, strArr);
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
    }

    public class f implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ String f1995a;

        public f(String str) {
            this.f1995a = str;
        }

        @Override // java.lang.Runnable
        public void run() throws JSONException {
            try {
                JSONObject jSONObject = new JSONObject(this.f1995a);
                int i2 = jSONObject.getInt("mediatype");
                String string = jSONObject.getString("uid");
                boolean z2 = jSONObject.getBoolean("audio");
                boolean z3 = jSONObject.getBoolean("video");
                boolean z4 = jSONObject.getBoolean("muteaudio");
                boolean z5 = jSONObject.getBoolean("mutevideo");
                int i3 = jSONObject.getInt("streamtype");
                StreamInfo streamInfo = new StreamInfo();
                streamInfo.setMediaType(i2);
                streamInfo.setUid(string);
                streamInfo.setHasAudio(z2);
                streamInfo.setHasVideo(z3);
                streamInfo.setMuteAudio(z4);
                streamInfo.setMuteVideo(z5);
                c.h.a(d.H, "onLogicPeerLostConnection: " + streamInfo.toString());
                if (d.this.f1905d != null) {
                    d.this.f1905d.onPeerLostConnection(i3, streamInfo);
                }
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
    }

    public class f0 implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ int f1997a;

        public f0(int i2) {
            this.f1997a = i2;
        }

        @Override // java.lang.Runnable
        public void run() {
            e.a.a().g().c(this.f1997a, d.this.f1908g.remove(a.h.a(this.f1997a)));
            d.this.f1914m = true;
        }
    }

    public class f1 implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ StreamInfo[] f1999a;

        public f1(StreamInfo[] streamInfoArr) {
            this.f1999a = streamInfoArr;
        }

        @Override // java.lang.Runnable
        public void run() {
            e.a.a().g().a(this.f1999a);
        }
    }

    public class f2 implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ String f2001a;

        public f2(String str) {
            this.f2001a = str;
        }

        @Override // java.lang.Runnable
        public void run() throws JSONException {
            try {
                JSONObject jSONObject = new JSONObject(this.f2001a).getJSONObject("data");
                c.h.a(d.H, " onLogicUserSt " + this.f2001a);
                if (jSONObject != null) {
                    int i2 = jSONObject.getInt(EaseConstant.MESSAGE_TYPE_CMD);
                    String string = jSONObject.getString("uid");
                    if (i2 != 1) {
                        if (i2 == 2 && d.this.f1905d != null) {
                            d.this.f1905d.onRemoteUserLeave(string, 0);
                        }
                    } else if (d.this.f1905d != null) {
                        c.h.a(d.H, " onRemoteUserJoin " + this.f2001a);
                        d.this.f1905d.onRemoteUserJoin(string);
                    }
                }
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
    }

    public class f3 implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ int f2003a;

        /* renamed from: b, reason: collision with root package name */
        public final /* synthetic */ String f2004b;

        public f3(int i2, String str) {
            this.f2003a = i2;
            this.f2004b = str;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (d.this.f1905d != null) {
                d.this.f1905d.onAddStreams(this.f2003a, this.f2004b);
            }
        }
    }

    public class g implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ String f2006a;

        public g(String str) {
            this.f2006a = str;
        }

        @Override // java.lang.Runnable
        public void run() throws JSONException {
            try {
                JSONObject jSONObject = new JSONObject(this.f2006a);
                String string = jSONObject.getString("msgfrom");
                String string2 = jSONObject.getString("msg");
                if (d.this.f1905d != null) {
                    d.this.f1905d.onServerBroadcastMessage(string, string2);
                }
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
    }

    public class g0 implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ boolean f2008a;

        public g0(boolean z2) {
            this.f2008a = z2;
        }

        @Override // java.lang.Runnable
        public void run() {
            e.a.a().g().a(1, this.f2008a);
        }
    }

    public class g1 implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ int f2010a;

        public g1(int i2) {
            this.f2010a = i2;
        }

        @Override // java.lang.Runnable
        public void run() {
            int i2 = this.f2010a;
            if (i2 < 0) {
                i2 = 0;
            }
            if (i2 > 400) {
                i2 = 400;
            }
            c.h.a(d.H, "adjustRecordVolume: " + i2);
            RtcAudioParams.adjustRecordVolume(i2);
        }
    }

    public class g2 implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ String f2012a;

        public g2(String str) {
            this.f2012a = str;
        }

        @Override // java.lang.Runnable
        public void run() throws JSONException {
            c.h.a(d.H, " onLogicStreamSt " + this.f2012a);
            try {
                JSONObject jSONObject = new JSONObject(this.f2012a).getJSONObject("data");
                if (jSONObject != null) {
                    int i2 = jSONObject.getInt(EaseConstant.MESSAGE_TYPE_CMD);
                    String string = jSONObject.getString("uid");
                    int i3 = jSONObject.getInt("mtype");
                    boolean z2 = jSONObject.getBoolean("audio");
                    boolean z3 = jSONObject.getBoolean("video");
                    boolean z4 = jSONObject.getBoolean("muteaudio");
                    boolean z5 = jSONObject.getBoolean("mutevideo");
                    boolean z6 = jSONObject.has("data") ? jSONObject.getBoolean("data") : false;
                    StreamInfo streamInfo = new StreamInfo();
                    streamInfo.setUid(string);
                    streamInfo.setMediaType(i3);
                    streamInfo.setHasAudio(z2);
                    streamInfo.setHasVideo(z3);
                    streamInfo.setHasData(z6);
                    streamInfo.setMuteAudio(z4);
                    streamInfo.setMuteVideo(z5);
                    c.h.a(d.H, "stream st " + i2);
                    if (i2 == 1) {
                        if (d.this.f1913l) {
                            d.this.d(streamInfo);
                        }
                        if (d.this.f1905d != null) {
                            d.this.f1905d.onRemotePublish(streamInfo);
                            return;
                        }
                        return;
                    }
                    if (i2 != 2) {
                        return;
                    }
                    String str = streamInfo.getUId() + a.h.a(streamInfo.getMediaType());
                    if (d.this.f1908g.containsKey(str)) {
                        d.this.f1908g.remove(str);
                        d.this.a(streamInfo);
                        c.h.a(d.H, "remove render for stream remove ");
                    }
                    if (d.this.f1905d != null) {
                        c.h.a(d.H, "stream remove ");
                        d.this.f1905d.onRemoteUnPublish(streamInfo);
                    }
                }
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
    }

    public class g3 implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ int f2014a;

        /* renamed from: b, reason: collision with root package name */
        public final /* synthetic */ String f2015b;

        public g3(int i2, String str) {
            this.f2014a = i2;
            this.f2015b = str;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (d.this.f1905d != null) {
                d.this.f1905d.onDelStreams(this.f2014a, this.f2015b);
            }
        }
    }

    public class h implements Runnable {
        public h() {
        }

        @Override // java.lang.Runnable
        public void run() {
            if (d.this.f1905d != null) {
                d.this.f1905d.onFirstLocalVideoFrame();
            }
        }
    }

    public class h0 implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ boolean f2018a;

        public h0(boolean z2) {
            this.f2018a = z2;
        }

        @Override // java.lang.Runnable
        public void run() {
            e.a.a().g().a(1, this.f2018a);
        }
    }

    public class h1 implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ DataProvider f2020a;

        public h1(DataProvider dataProvider) {
            this.f2020a = dataProvider;
        }

        @Override // java.lang.Runnable
        public void run() {
            e.a.a().a(this.f2020a);
        }
    }

    public class h2 implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ String f2022a;

        public h2(String str) {
            this.f2022a = str;
        }

        @Override // java.lang.Runnable
        public void run() throws JSONException {
            c.h.a(d.H, " onLogicTrackSt " + this.f2022a);
            try {
                JSONObject jSONObject = new JSONObject(this.f2022a);
                String string = jSONObject.getString("uid");
                int i2 = jSONObject.getInt("mtype");
                int i3 = jSONObject.getInt("ttype");
                boolean z2 = jSONObject.getBoolean(c.i.f2279j);
                if (d.this.f1905d != null) {
                    d.this.f1905d.onRemoteTrackNotify(string, i2, i3, z2);
                }
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
    }

    public class h3 implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ String f2024a;

        public h3(String str) {
            this.f2024a = str;
        }

        @Override // java.lang.Runnable
        public void run() throws JSONException {
            try {
                JSONObject jSONObject = new JSONObject(this.f2024a);
                int i2 = jSONObject.getInt("code");
                String string = jSONObject.getString("msg");
                if (d.this.f1905d != null) {
                    d.this.f1905d.onLogOffUsers(i2, string);
                }
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
    }

    public class i implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ Handler f2026a;

        public i(Handler handler) {
            this.f2026a = handler;
        }

        @Override // java.lang.Runnable
        public void run() throws RemoteException {
            c.h.a(d.H, "start unbind monitor service");
            Message messageObtain = Message.obtain(null, 1, 0, 0);
            messageObtain.replyTo = new Messenger(this.f2026a);
            try {
                d.this.C.send(messageObtain);
            } catch (RemoteException e2) {
                e2.printStackTrace();
            }
            if (d.b.a() != null) {
                d.b.a().unbindService(d.this.D);
            }
            c.h.a(d.H, "unbind monitor service finish");
        }
    }

    public class i0 implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ boolean f2028a;

        public i0(boolean z2) {
            this.f2028a = z2;
        }

        @Override // java.lang.Runnable
        public void run() {
            e.a.a().g().a(2, this.f2028a);
        }
    }

    public class i1 implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ String f2030a;

        public i1(String str) {
            this.f2030a = str;
        }

        @Override // java.lang.Runnable
        public void run() {
            e.a.a().g().messageNotify(this.f2030a);
        }
    }

    public class i2 implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ String f2032a;

        public i2(String str) {
            this.f2032a = str;
        }

        @Override // java.lang.Runnable
        public void run() throws JSONException {
            c.h.a(d.H, " onLogicUserListNotify " + this.f2032a);
            try {
                JSONArray jSONArray = new JSONArray(this.f2032a);
                int length = jSONArray.length();
                c.h.a(d.H, " onLogicUserListNotify " + length);
                for (int i2 = 0; i2 < length; i2++) {
                    String string = jSONArray.getJSONObject(i2).getString("user_id");
                    if (d.this.f1905d != null) {
                        c.h.a(d.H, "onRemoteUserJoin: " + string);
                        d.this.f1905d.onRemoteUserJoin(string);
                    }
                }
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
    }

    public class i3 implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ String f2034a;

        public i3(String str) {
            this.f2034a = str;
        }

        @Override // java.lang.Runnable
        public void run() throws JSONException {
            try {
                JSONObject jSONObject = new JSONObject(this.f2034a).getJSONObject("data");
                e.a.a().g().a(jSONObject.getInt("cmdtype"), jSONObject.getString("user_id"));
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
    }

    public class j implements Runnable {
        public j() {
        }

        @Override // java.lang.Runnable
        public void run() {
            if (d.this.f1905d != null) {
                d.this.f1905d.onAudioDeviceChanged(d.this.f1920s);
            }
        }
    }

    public class j0 implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ boolean f2037a;

        public j0(boolean z2) {
            this.f2037a = z2;
        }

        @Override // java.lang.Runnable
        public void run() {
            e.a.a().g().b(1, this.f2037a);
        }
    }

    public class j1 implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ StreamInfo f2039a;

        /* renamed from: b, reason: collision with root package name */
        public final /* synthetic */ ScreenShot f2040b;

        /* renamed from: c, reason: collision with root package name */
        public final /* synthetic */ boolean f2041c;

        public class a implements EglRenderer.FrameListener {

            /* renamed from: a, reason: collision with root package name */
            public ScreenShot f2043a;

            public a() {
                this.f2043a = j1.this.f2040b;
            }

            @Override // org.wrtca.api.EglRenderer.FrameListener
            public void onFrame(ByteBuffer byteBuffer, int i2, int i3) {
                c.h.a(d.H, "CoreRtcEngineImplonFrame called rtcScreenShotCallBack bytebuffer.length " + byteBuffer.limit() + " width: " + i2 + " height: " + i3);
                this.f2043a.onReceiveRGBAData(byteBuffer, i2, i3);
            }
        }

        public j1(StreamInfo streamInfo, ScreenShot screenShot, boolean z2) {
            this.f2039a = streamInfo;
            this.f2040b = screenShot;
            this.f2041c = z2;
        }

        @Override // java.lang.Runnable
        public void run() {
            String strA;
            StreamInfo streamInfo = this.f2039a;
            if (streamInfo == null || this.f2040b == null) {
                c.h.a(d.H, "CoreRtcEngineImpltakeSnapShot param invalid");
                return;
            }
            if (this.f2041c) {
                strA = a.h.a(streamInfo.getMediaType());
            } else {
                strA = this.f2039a.getUId() + a.h.a(this.f2039a.getMediaType());
            }
            Log.d(d.H, "takeSnapShot impl key: " + strA + " size: " + d.this.f1908g.size());
            if (!d.this.f1908g.containsKey(strA)) {
                c.h.a(d.H, " do not find render attach with stream info " + this.f2039a);
                return;
            }
            Log.d(d.H, "takeSnapShot has key: " + strA);
            Object obj = d.this.f1908g.get(strA);
            a aVar = new a();
            if (!this.f2041c) {
                if (obj instanceof SurfaceViewRenderer) {
                    ((SurfaceViewRenderer) obj).addFrameListener(aVar, 1.0f);
                    return;
                } else {
                    if (obj instanceof TextureViewRenderer) {
                        ((TextureViewRenderer) obj).addFrameListener(aVar, 1.0f);
                        return;
                    }
                    return;
                }
            }
            if (obj instanceof ArrayList) {
                ArrayList arrayList = (ArrayList) obj;
                if (arrayList.isEmpty()) {
                    c.h.a(d.H, " do not find local snap render");
                    return;
                }
                Object obj2 = arrayList.get(0);
                if (obj2 instanceof SurfaceViewRenderer) {
                    ((SurfaceViewRenderer) obj2).addFrameListener(aVar, 1.0f);
                } else if (obj2 instanceof TextureViewRenderer) {
                    ((TextureViewRenderer) obj2).addFrameListener(aVar, 1.0f);
                }
            }
        }
    }

    public class j2 implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ String f2045a;

        public j2(String str) {
            this.f2045a = str;
        }

        @Override // java.lang.Runnable
        public void run() throws JSONException {
            try {
                JSONArray jSONArray = new JSONArray(this.f2045a);
                c.h.a(d.H, " streamList " + jSONArray);
                int length = jSONArray.length();
                for (int i2 = 0; i2 < length; i2++) {
                    JSONObject jSONObject = jSONArray.getJSONObject(i2);
                    if (jSONObject != null) {
                        String string = jSONObject.getString("uid");
                        StreamInfo streamInfo = new StreamInfo();
                        streamInfo.setUid(string);
                        streamInfo.setMediaType(jSONObject.getInt("media_type"));
                        streamInfo.setHasAudio(jSONObject.getBoolean("audio"));
                        streamInfo.setHasVideo(jSONObject.getBoolean("video"));
                        streamInfo.setHasData(jSONObject.getBoolean("data"));
                        streamInfo.setMuteAudio(jSONObject.getBoolean("muteaudio"));
                        streamInfo.setMuteVideo(jSONObject.getBoolean("mutevideo"));
                        if (d.this.f1905d != null) {
                            d.this.f1905d.onRemotePublish(streamInfo);
                        }
                    }
                }
                if (d.this.f1913l) {
                    d.this.J(this.f2045a);
                }
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
    }

    public class j3 implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ String f2047a;

        public j3(String str) {
            this.f2047a = str;
        }

        @Override // java.lang.Runnable
        public void run() throws JSONException {
            String[] strArr;
            JSONArray jSONArray;
            try {
                c.h.a(d.H, "on receivce mix notify: " + this.f2047a);
                JSONObject jSONObject = new JSONObject(this.f2047a);
                int i2 = jSONObject.getInt(NotificationCompat.CATEGORY_ERROR);
                String string = jSONObject.getString("msg");
                JSONObject jSONObject2 = jSONObject.getJSONObject("data");
                String string2 = jSONObject2.getString("user_id");
                String string3 = jSONObject2.getString("room_id");
                String string4 = jSONObject2.getString("mix_id");
                if (jSONObject2.has(TbsVideoCacheTask.KEY_VIDEO_CACHE_PARAM_FILENAME)) {
                    String string5 = jSONObject2.getString(TbsVideoCacheTask.KEY_VIDEO_CACHE_PARAM_FILENAME);
                    if (d.this.f1905d != null) {
                        if (i2 == 0) {
                            d.this.f1905d.onRecordStatusNotify(6, i2, string, string2, string3, string4, string5);
                            return;
                        }
                        if (i2 == 24151) {
                            d.this.f1905d.onRecordStatusNotify(10, i2, string, string2, string3, string4, string5);
                            return;
                        } else if (i2 == 24149) {
                            d.this.f1905d.onRecordStatusNotify(8, i2, string, string2, string3, string4, string5);
                            return;
                        } else {
                            d.this.f1905d.onRecordStatusNotify(7, i2, string, string2, string3, string4, string5);
                            return;
                        }
                    }
                    return;
                }
                if (!jSONObject2.has("pushurl") || (jSONArray = jSONObject2.getJSONArray("pushurl")) == null || jSONArray.length() <= 0) {
                    strArr = null;
                } else {
                    String[] strArr2 = new String[jSONArray.length()];
                    for (int i3 = 0; i3 < jSONArray.length(); i3++) {
                        strArr2[i3] = jSONArray.getString(i3);
                    }
                    strArr = strArr2;
                }
                if (d.this.f1905d != null) {
                    if (i2 == 0) {
                        d.this.f1905d.onRelayStatusNotify(2, i2, string, string2, string3, string4, strArr);
                        return;
                    }
                    if (i2 == 24151) {
                        d.this.f1905d.onRelayStatusNotify(10, i2, string, string2, string3, string4, strArr);
                    } else if (i2 == 24149) {
                        d.this.f1905d.onRelayStatusNotify(8, i2, string, string2, string3, string4, strArr);
                    } else {
                        d.this.f1905d.onRelayStatusNotify(3, i2, string, string2, string3, string4, strArr);
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public class k implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ RtcNotification f2049a;

        public k(RtcNotification rtcNotification) {
            this.f2049a = rtcNotification;
        }

        @Override // java.lang.Runnable
        public void run() {
            e.a.a().a(this.f2049a);
        }
    }

    public class k0 implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ boolean f2051a;

        public k0(boolean z2) {
            this.f2051a = z2;
        }

        @Override // java.lang.Runnable
        public void run() {
            e.a.a().g().b(2, this.f2051a);
        }
    }

    public class k1 implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ StreamInfo f2053a;

        /* renamed from: b, reason: collision with root package name */
        public final /* synthetic */ boolean f2054b;

        /* renamed from: c, reason: collision with root package name */
        public final /* synthetic */ int f2055c;

        public k1(StreamInfo streamInfo, boolean z2, int i2) {
            this.f2053a = streamInfo;
            this.f2054b = z2;
            this.f2055c = i2;
        }

        @Override // java.lang.Runnable
        public void run() {
            StreamInfo streamInfo = this.f2053a;
            if (streamInfo == null) {
                c.h.a(d.H, "CoreRtcEngineImpltakeSnapShot param invalid");
                return;
            }
            String strA = this.f2054b ? a.h.a(streamInfo.getMediaType()) : this.f2053a.getUId() + a.h.a(this.f2053a.getMediaType());
            Log.d(d.H, "setRenderViewMode isLocal" + this.f2054b + " key: " + strA + " mapSize: " + d.this.f1908g.size());
            if (d.this.f1908g.containsKey(strA)) {
                Log.d(d.H, "setRenderViewMode has key: " + strA);
                ArrayList arrayList = (ArrayList) d.this.f1908g.get(strA);
                if (arrayList.isEmpty()) {
                    return;
                }
                for (int i2 = 0; i2 < arrayList.size(); i2++) {
                    Object obj = arrayList.get(i2);
                    if (!(obj instanceof SurfaceViewRenderer) && (obj instanceof TextureViewRenderer)) {
                        ((TextureViewRenderer) obj).setScaleType(this.f2055c);
                    }
                }
            }
        }
    }

    public class k2 implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ String f2057a;

        public k2(String str) {
            this.f2057a = str;
        }

        @Override // java.lang.Runnable
        public void run() throws JSONException {
            int scaleType;
            StreamInfo streamInfo;
            if (d.this.f1905d != null) {
                try {
                    JSONObject jSONObject = new JSONObject(this.f2057a);
                    jSONObject.getString("msg");
                    JSONObject jSONObject2 = jSONObject.getJSONObject("data");
                    String string = jSONObject2.getString("uid");
                    jSONObject.getInt("code");
                    int i2 = jSONObject2.getInt("mtype");
                    int i3 = jSONObject2.getInt(CommonNetImpl.STYPE);
                    c.h.a(d.H, "mRenderViewMap: " + d.this.f1908g.size());
                    if (i3 == 1) {
                        boolean z2 = jSONObject2.getBoolean("audio");
                        boolean z3 = jSONObject2.getBoolean("video");
                        boolean z4 = jSONObject2.getBoolean("muteaudio");
                        boolean z5 = jSONObject2.getBoolean("mutevideo");
                        StreamInfo streamInfo2 = new StreamInfo();
                        streamInfo2.setMediaType(i2);
                        streamInfo2.setUid(string);
                        streamInfo2.setHasVideo(z3);
                        streamInfo2.setHasAudio(z2);
                        streamInfo2.setMuteAudio(z4);
                        streamInfo2.setMuteVideo(z5);
                        String strA = a.h.a(i2);
                        if (d.this.f1908g.containsKey(strA)) {
                            c.h.a(d.H, " onLogicStreamConnect startPreview ");
                            Object obj = d.this.f1908g.get(strA);
                            if (obj instanceof ArrayList) {
                                Iterator it = ((ArrayList) obj).iterator();
                                while (it.hasNext()) {
                                    Object next = it.next();
                                    if (next instanceof CoreSurfaceViewRenderer) {
                                        streamInfo = ((CoreSurfaceViewRenderer) next).getStreamInfo();
                                        scaleType = ((CoreSurfaceViewRenderer) next).getScaleType();
                                    } else if (next instanceof CoreTextureViewRenderer) {
                                        streamInfo = ((CoreTextureViewRenderer) next).getStreamInfo();
                                        scaleType = ((CoreTextureViewRenderer) next).getScaleType();
                                    } else {
                                        scaleType = -1;
                                        next = null;
                                        streamInfo = null;
                                    }
                                    c.h.a(d.H, " onLogicStreamConnect stream info  " + streamInfo);
                                    if (streamInfo != null) {
                                        c.h.a(d.H, " onLogicStreamConnect stream restartpreview  ");
                                        d.this.a(streamInfo, next, scaleType, null);
                                    } else {
                                        e.a.a().g().b(1, next);
                                    }
                                }
                            }
                        }
                        d.this.l(i2);
                        c.h.a(d.H, "onPeerReconnect called " + i3 + " info: " + streamInfo2);
                        d.this.f1905d.onPeerReconnect(i3, streamInfo2);
                        return;
                    }
                    if (i3 == 2) {
                        boolean z6 = jSONObject2.getBoolean("audio");
                        boolean z7 = jSONObject2.getBoolean("video");
                        boolean z8 = jSONObject2.getBoolean("muteaudio");
                        boolean z9 = jSONObject2.getBoolean("mutevideo");
                        StreamInfo streamInfo3 = new StreamInfo();
                        streamInfo3.setMediaType(i2);
                        streamInfo3.setUid(string);
                        streamInfo3.setHasVideo(z7);
                        streamInfo3.setHasAudio(z6);
                        streamInfo3.setMuteAudio(z8);
                        streamInfo3.setMuteVideo(z9);
                        String str = streamInfo3.getUId() + a.h.a(streamInfo3.getMediaType());
                        if (d.this.f1908g.containsKey(str)) {
                            c.h.a(d.H, " find remoteView key " + str);
                            Object obj2 = d.this.f1908g.get(str);
                            if (obj2 != null) {
                                ArrayList arrayList = (ArrayList) obj2;
                                c.h.a(d.H, "Remote view sinks size: " + arrayList.size());
                                Iterator it2 = arrayList.iterator();
                                while (it2.hasNext()) {
                                    Object next2 = it2.next();
                                    c.h.a(d.H, " find remoteView recover sink" + next2);
                                    e.a.a().g().b(streamInfo3, next2);
                                }
                            }
                        }
                        d.this.K(string + StrPool.UNDERLINE + i2);
                        c.h.a(d.H, "onPeerReconnect called " + i3 + " info: " + streamInfo3);
                        d.this.f1905d.onPeerReconnect(i3, streamInfo3);
                    }
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
            }
        }
    }

    public class k3 implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ int f2059a;

        public k3(int i2) {
            this.f2059a = i2;
        }

        @Override // java.lang.Runnable
        public void run() {
            j.h hVar = new j.h();
            int i2 = this.f2059a;
            if (i2 == 5) {
                hVar.f(1280);
                hVar.b(720);
                hVar.a(25);
                hVar.e(1500);
                hVar.d(1500);
                hVar.c(2000);
            } else if (i2 != 6) {
                hVar.f(640);
                hVar.b(480);
                hVar.a(20);
                hVar.e(300);
                hVar.d(100);
                hVar.c(600);
            } else {
                hVar.f(R2.attr.iconTint);
                hVar.b(R2.attr.color_hot_circle_one_end);
                hVar.a(20);
                hVar.e(2000);
                hVar.d(2000);
                hVar.c(2500);
            }
            e.a.a().a(hVar);
            d.b.b(false);
        }
    }

    public class l implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ a.w f2061a;

        public l(a.w wVar) {
            this.f2061a = wVar;
        }

        @Override // java.lang.Runnable
        public void run() {
            j.h hVar = new j.h();
            hVar.f(this.f2061a.f());
            hVar.b(this.f2061a.b());
            hVar.a(this.f2061a.a());
            hVar.e(this.f2061a.e());
            hVar.d(this.f2061a.d());
            hVar.c(this.f2061a.c());
            e.a.a().b(hVar);
            d.b.b(true);
        }
    }

    public class l0 implements Handler.Callback {
        public l0() {
        }

        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message message) throws NumberFormatException {
            int i2 = message.what;
            if (i2 == 2) {
                c.h.a(d.H, "MonitorService started.");
            } else if (i2 == 4 && message.getData() != null) {
                try {
                    String[] strArrSplit = message.getData().getString("data").split(":");
                    int i3 = Integer.parseInt(strArrSplit[0]);
                    int i4 = Integer.parseInt(strArrSplit[1]);
                    f.e.g().a(i3);
                    f.e.g().c(i4);
                    if (i3 > 90) {
                        LogReportManager.getInstance().sendErrorMsg(e.a.a(), 6);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
            return false;
        }
    }

    public class l1 implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ boolean f2064a;

        public l1(boolean z2) {
            this.f2064a = z2;
        }

        @Override // java.lang.Runnable
        public void run() {
            c.h.a(d.H, "CoreRtcEngineImplcontrol audio module" + this.f2064a);
            e.a.a().g().a(this.f2064a);
        }
    }

    public class l2 implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ String f2066a;

        public class a implements Runnable {
            public a() {
            }

            @Override // java.lang.Runnable
            public void run() {
                if (d.this.f1919r != null) {
                    d.this.f1919r.a(3);
                }
            }
        }

        public l2(String str) {
            this.f2066a = str;
        }

        @Override // java.lang.Runnable
        public void run() throws JSONException {
            StreamInfo streamInfo;
            if (d.this.f1905d != null) {
                try {
                    JSONObject jSONObject = new JSONObject(this.f2066a);
                    String string = jSONObject.getString("msg");
                    int i2 = jSONObject.getInt("code");
                    c.h.a(d.H, " onLogicPublishStream code is: " + i2);
                    if (jSONObject.has("data")) {
                        JSONObject jSONObject2 = jSONObject.getJSONObject("data");
                        String string2 = jSONObject2.getString("uid");
                        int i3 = jSONObject2.getInt("mtype");
                        boolean z2 = jSONObject2.getBoolean("audio");
                        boolean z3 = jSONObject2.getBoolean("video");
                        streamInfo = new StreamInfo();
                        streamInfo.setMediaType(i3);
                        streamInfo.setUid(string2);
                        streamInfo.setHasAudio(z2);
                        streamInfo.setHasVideo(z3);
                        d.this.f1918q = z3;
                        new Handler(Looper.getMainLooper()).post(new a());
                    } else {
                        streamInfo = null;
                    }
                    d.this.f1905d.onLocalPublish(i2, string, streamInfo);
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
            }
        }
    }

    public class l3 {

        /* renamed from: a, reason: collision with root package name */
        public String f2069a;

        /* renamed from: b, reason: collision with root package name */
        public int f2070b;

        /* renamed from: c, reason: collision with root package name */
        public boolean f2071c = false;

        /* renamed from: d, reason: collision with root package name */
        public boolean f2072d = false;

        /* renamed from: e, reason: collision with root package name */
        public boolean f2073e = false;

        public l3() {
        }

        public void a(String str) {
            this.f2069a = str;
        }

        public String b() {
            return this.f2069a;
        }

        public void c(boolean z2) {
            this.f2071c = z2;
        }

        public boolean d() {
            return this.f2073e;
        }

        public boolean e() {
            return this.f2071c;
        }

        public String toString() {
            return "RemoteMuteRecord{uid='" + this.f2069a + CharPool.SINGLE_QUOTE + ", mediaType=" + this.f2070b + ", videoMute=" + this.f2071c + ", audioMute=" + this.f2072d + ", screenMute=" + this.f2073e + '}';
        }

        public int a() {
            return this.f2070b;
        }

        public void b(boolean z2) {
            this.f2073e = z2;
        }

        public boolean c() {
            return this.f2072d;
        }

        public void a(int i2) {
            this.f2070b = i2;
        }

        public void a(boolean z2) {
            this.f2072d = z2;
        }
    }

    public class m implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ boolean f2075a;

        public m(boolean z2) {
            this.f2075a = z2;
        }

        @Override // java.lang.Runnable
        public void run() {
            c.h.a(d.H, "setFlashOn is: " + this.f2075a);
            if (this.f2075a) {
                e.a.a().g().a();
            } else {
                e.a.a().g().c();
            }
        }
    }

    public class m0 implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ StreamInfo f2077a;

        /* renamed from: b, reason: collision with root package name */
        public final /* synthetic */ Object f2078b;

        /* renamed from: c, reason: collision with root package name */
        public final /* synthetic */ FirstFrameRendered f2079c;

        /* renamed from: d, reason: collision with root package name */
        public final /* synthetic */ int f2080d;

        public class a implements Runnable {

            /* renamed from: a, reason: collision with root package name */
            public final /* synthetic */ CoreSurfaceViewRenderer f2082a;

            public a(CoreSurfaceViewRenderer coreSurfaceViewRenderer) {
                this.f2082a = coreSurfaceViewRenderer;
            }

            @Override // java.lang.Runnable
            public void run() {
                this.f2082a.setScaleType(m0.this.f2080d);
            }
        }

        public class b implements Runnable {

            /* renamed from: a, reason: collision with root package name */
            public final /* synthetic */ CoreSurfaceViewRenderer f2084a;

            public b(CoreSurfaceViewRenderer coreSurfaceViewRenderer) {
                this.f2084a = coreSurfaceViewRenderer;
            }

            @Override // java.lang.Runnable
            public void run() {
                c.h.a(d.H, " start +++remoteview+++ renderview setScaleType" + m0.this.f2080d);
                this.f2084a.setScaleType(m0.this.f2080d);
            }
        }

        public class c implements Runnable {

            /* renamed from: a, reason: collision with root package name */
            public final /* synthetic */ CoreTextureViewRenderer f2086a;

            public c(CoreTextureViewRenderer coreTextureViewRenderer) {
                this.f2086a = coreTextureViewRenderer;
            }

            @Override // java.lang.Runnable
            public void run() {
                this.f2086a.setScaleType(m0.this.f2080d);
            }
        }

        public m0(StreamInfo streamInfo, Object obj, FirstFrameRendered firstFrameRendered, int i2) {
            this.f2077a = streamInfo;
            this.f2078b = obj;
            this.f2079c = firstFrameRendered;
            this.f2080d = i2;
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Removed duplicated region for block: B:18:0x00f8  */
        @Override // java.lang.Runnable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void run() {
            /*
                Method dump skipped, instructions count: 357
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: b.d.m0.run():void");
        }
    }

    public class m1 implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ boolean f2088a;

        public m1(boolean z2) {
            this.f2088a = z2;
        }

        @Override // java.lang.Runnable
        public void run() {
            c.h.a(d.H, "CoreRtcEngineImplcontrol audio playout" + this.f2088a);
            e.a.a().g().b(this.f2088a);
        }
    }

    public class m2 implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ String f2090a;

        public m2(String str) {
            this.f2090a = str;
        }

        @Override // java.lang.Runnable
        public void run() throws JSONException {
            if (d.this.f1905d != null) {
                try {
                    d.this.f1905d.onError(new JSONObject(this.f2090a).getInt("code"));
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
    }

    public class m3 implements e.i {
        public m3() {
        }

        @Override // e.i
        public int a() {
            return d.this.f1922u;
        }

        @Override // e.i
        public int b() {
            return d.this.f1921t;
        }

        @Override // e.i
        public int c() {
            return d.this.f1920s;
        }
    }

    public class n implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ String f2093a;

        /* renamed from: b, reason: collision with root package name */
        public final /* synthetic */ double f2094b;

        public n(String str, double d3) {
            this.f2093a = str;
            this.f2094b = d3;
        }

        @Override // java.lang.Runnable
        public void run() {
            c.h.a(d.H, "adjustUserPlaybackSignalVolume id is: " + this.f2093a + " volume: " + this.f2094b);
            e.a.a().g().adjustUserPlaybackSignalVolume(this.f2093a, this.f2094b);
        }
    }

    public class n0 implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ String f2096a;

        public n0(String str) {
            this.f2096a = str;
        }

        @Override // java.lang.Runnable
        public void run() throws JSONException {
            try {
                c.h.a(d.H, "onStartRemoteRender : " + this.f2096a);
                JSONObject jSONObject = new JSONObject(this.f2096a);
                jSONObject.getInt("mediatype");
                jSONObject.getString("uid");
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
    }

    public class n1 implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ boolean f2098a;

        public n1(boolean z2) {
            this.f2098a = z2;
        }

        @Override // java.lang.Runnable
        public void run() {
            c.h.a(d.H, "CoreRtcEngineImplcontrol audio record" + this.f2098a);
            e.a.a().g().c(this.f2098a);
        }
    }

    public class n2 implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ String f2100a;

        public class a implements Runnable {
            public a() {
            }

            @Override // java.lang.Runnable
            public void run() {
                if (d.this.f1919r != null) {
                    d.this.f1919r.a(0);
                }
            }
        }

        public n2(String str) {
            this.f2100a = str;
        }

        @Override // java.lang.Runnable
        public void run() throws JSONException {
            if (d.this.f1905d != null) {
                try {
                    JSONObject jSONObject = new JSONObject(this.f2100a);
                    int i2 = jSONObject.getInt("code");
                    c.h.a(d.H, " onLogicUnPublishStream code is: " + i2);
                    String string = jSONObject.getString("msg");
                    int i3 = jSONObject.getJSONObject("data").getInt("mtype");
                    StreamInfo streamInfo = new StreamInfo();
                    streamInfo.setMediaType(i3);
                    if (d.this.f1914m) {
                        c.h.a(d.H, " onLocalUnPublishOnly mediaType is: " + i3);
                        d.this.f1905d.onLocalUnPublishOnly(i2, string, streamInfo);
                    } else {
                        c.h.a(d.H, " onLocalUnPublish and stopPreview mediaType is: " + i3);
                        d.this.a(i3);
                        d.this.f1905d.onLocalUnPublish(i2, string, streamInfo);
                    }
                    new Handler(Looper.getMainLooper()).post(new a());
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
            }
        }
    }

    public class o implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ double f2103a;

        public o(double d3) {
            this.f2103a = d3;
        }

        @Override // java.lang.Runnable
        public void run() {
            c.h.a(d.H, "adjustPlaybackSignalVolume is: " + this.f2103a);
            e.a.a().g().adjustPlaybackSignalVolume(this.f2103a);
        }
    }

    public class o0 implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ StreamInfo f2105a;

        public o0(StreamInfo streamInfo) {
            this.f2105a = streamInfo;
        }

        @Override // java.lang.Runnable
        public void run() {
            c.h.a(d.H, "stop remote view " + this.f2105a);
            c.h.a(d.H, "stop remote render " + d.this.f1908g.remove(this.f2105a.getUId() + a.h.a(this.f2105a.getMediaType())));
            e.a.a().g().a(this.f2105a);
        }
    }

    public class o1 implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ boolean f2107a;

        public o1(boolean z2) {
            this.f2107a = z2;
        }

        @Override // java.lang.Runnable
        public void run() {
            c.h.a(d.H, "CoreRtcEngineImplhandler control video module" + this.f2107a);
            e.a.a().g().f(this.f2107a);
        }
    }

    public class o2 implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ boolean f2109a;

        public o2(boolean z2) {
            this.f2109a = z2;
        }

        @Override // java.lang.Runnable
        public void run() {
            d.this.f1909h = this.f2109a;
        }
    }

    public class p implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ int f2111a;

        /* renamed from: b, reason: collision with root package name */
        public final /* synthetic */ int f2112b;

        public p(int i2, int i3) {
            this.f2111a = i2;
            this.f2112b = i3;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (!d.b.e()) {
                c.h.a(d.H, "setCustomEncodeResolution is false.");
                return;
            }
            c.h.a(d.H, "setCropSize x is: " + this.f2111a + " y is: " + this.f2112b);
            e.a.a().g().cropPushResolution(this.f2111a, this.f2112b);
        }
    }

    public class p0 implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ StreamInfo f2114a;

        /* renamed from: b, reason: collision with root package name */
        public final /* synthetic */ Object f2115b;

        public p0(StreamInfo streamInfo, Object obj) {
            this.f2114a = streamInfo;
            this.f2115b = obj;
        }

        @Override // java.lang.Runnable
        public void run() {
            Object next;
            c.h.a(d.H, "stop remote view " + this.f2114a);
            String str = this.f2114a.getUId() + a.h.a(this.f2114a.getMediaType());
            Object obj = d.this.f1908g.get(str);
            d.this.h();
            if (obj != null) {
                ArrayList arrayList = (ArrayList) obj;
                Iterator it = arrayList.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        next = null;
                        break;
                    }
                    next = it.next();
                    if (!(next instanceof CoreSurfaceViewRenderer)) {
                        if ((next instanceof CoreTextureViewRenderer) && this.f2115b.equals((CoreTextureViewRenderer) next)) {
                            c.h.a(d.H, "find sink to remove " + next);
                            break;
                        }
                    } else {
                        CoreSurfaceViewRenderer coreSurfaceViewRenderer = (CoreSurfaceViewRenderer) next;
                        Object obj2 = this.f2115b;
                        if (!(obj2 instanceof SurfaceViewGroup)) {
                            if ((obj2 instanceof CoreSurfaceViewRenderer) && obj2.equals(next)) {
                                next = this.f2115b;
                                break;
                            }
                        } else if (((SurfaceViewGroup) obj2).getSurfaceView().equals(coreSurfaceViewRenderer)) {
                            next = this.f2115b;
                            break;
                        }
                    }
                }
                if (next != null) {
                    arrayList.remove(next);
                    c.h.a(d.H, "");
                    if (arrayList.isEmpty()) {
                        c.h.a(d.H, "sink list is empty,remove it");
                        d.this.f1908g.remove(str);
                    }
                    d.this.h();
                    e.a.a().g().a(this.f2114a, next);
                }
            }
        }
    }

    public class p1 implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ List f2117a;

        /* renamed from: b, reason: collision with root package name */
        public final /* synthetic */ int f2118b;

        public p1(List list, int i2) {
            this.f2117a = list;
            this.f2118b = i2;
        }

        @Override // java.lang.Runnable
        public void run() {
            List list = this.f2117a;
            if (list == null || list.size() <= 0) {
                c.h.a(d.H, "CoreRtcEngineImpllogoff users can not be null or empty");
            } else {
                e.a.a().g().kickOffOthers(this.f2118b, this.f2117a);
            }
        }
    }

    public class p2 implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ String f2120a;

        public class a implements Runnable {

            /* renamed from: a, reason: collision with root package name */
            public final /* synthetic */ int f2122a;

            /* renamed from: b, reason: collision with root package name */
            public final /* synthetic */ StreamInfo f2123b;

            /* renamed from: c, reason: collision with root package name */
            public final /* synthetic */ String f2124c;

            public a(int i2, StreamInfo streamInfo, String str) {
                this.f2122a = i2;
                this.f2123b = streamInfo;
                this.f2124c = str;
            }

            @Override // java.lang.Runnable
            public void run() {
                c.h.a(d.H, " onLogicSubscribeStream auto remote preview " + this.f2122a);
                e.a.a().g().b(this.f2123b, d.this.f1908g.get(this.f2124c));
            }
        }

        public p2(String str) {
            this.f2120a = str;
        }

        @Override // java.lang.Runnable
        public void run() throws JSONException {
            if (d.this.f1905d != null) {
                c.h.a(d.H, " onLogicSubscribeStream ");
                try {
                    JSONObject jSONObject = new JSONObject(this.f2120a);
                    JSONObject jSONObject2 = jSONObject.getJSONObject("data");
                    int i2 = jSONObject.getInt("code");
                    String string = jSONObject.getString("msg");
                    int i3 = jSONObject2.getInt("mtype");
                    String string2 = jSONObject2.getString("uid");
                    StreamInfo streamInfo = new StreamInfo();
                    streamInfo.setUid(string2);
                    streamInfo.setMediaType(i3);
                    if (i2 == 0) {
                        streamInfo.setHasVideo(jSONObject2.getBoolean("video"));
                        streamInfo.setHasAudio(jSONObject2.getBoolean("audio"));
                        streamInfo.setMuteVideo(jSONObject2.getBoolean("mutevideo"));
                        streamInfo.setMuteAudio(jSONObject2.getBoolean("muteaudio"));
                        String str = string2 + a.h.a(streamInfo.getMediaType());
                        if (d.this.f1908g.containsKey(str)) {
                            d.this.f1903b.post(new a(i2, streamInfo, str));
                        }
                    }
                    c.h.a(d.H, " onLogicSubscribeStream to ui " + i2);
                    d.this.f1905d.onSubscribeResult(i2, string, streamInfo);
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
            }
        }
    }

    public class q implements Runnable {
        public q() {
        }

        @Override // java.lang.Runnable
        public void run() {
            if (d.this.f1919r != null) {
                d.this.f1919r.g();
            }
        }
    }

    public class q0 implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ StreamInfo f2127a;

        public q0(StreamInfo streamInfo) {
            this.f2127a = streamInfo;
        }

        @Override // java.lang.Runnable
        public void run() {
            e.a.a().g().b(this.f2127a);
        }
    }

    public class q1 implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ int f2129a;

        public q1(int i2) {
            this.f2129a = i2;
        }

        @Override // java.lang.Runnable
        public void run() {
            j.h hVar = new j.h();
            switch (this.f2129a) {
                case 0:
                    hVar.f(320);
                    hVar.b(180);
                    hVar.a(25);
                    hVar.e(100);
                    hVar.d(100);
                    hVar.c(300);
                    break;
                case 1:
                    hVar.f(R2.attr.arcLabelPaddingRight);
                    hVar.b(R2.attr.alignContent);
                    hVar.a(25);
                    hVar.e(200);
                    hVar.d(100);
                    hVar.c(400);
                    break;
                case 2:
                    hVar.f(480);
                    hVar.b(360);
                    hVar.a(25);
                    hVar.e(200);
                    hVar.d(100);
                    hVar.c(400);
                    break;
                case 3:
                    hVar.f(640);
                    hVar.b(360);
                    hVar.a(25);
                    hVar.e(300);
                    hVar.d(100);
                    hVar.c(500);
                    break;
                case 4:
                    hVar.f(640);
                    hVar.b(480);
                    hVar.a(25);
                    hVar.e(300);
                    hVar.d(100);
                    hVar.c(600);
                    break;
                case 5:
                    hVar.f(1280);
                    hVar.b(720);
                    hVar.a(20);
                    hVar.e(600);
                    hVar.d(500);
                    hVar.c(1000);
                    break;
                case 6:
                    hVar.f(R2.attr.iconTint);
                    hVar.b(R2.attr.color_hot_circle_one_end);
                    hVar.a(20);
                    hVar.e(2000);
                    hVar.d(1500);
                    hVar.c(2000);
                    break;
                default:
                    hVar.f(320);
                    hVar.b(180);
                    break;
            }
            if (hVar.f() <= 0 || hVar.b() <= 0) {
                c.h.a(d.H, " changePushResolution failed wrong video profile: " + this.f2129a);
                return;
            }
            c.h.a(d.H, " changePushResolution width: " + hVar.f() + " height: " + hVar.b());
            e.a.a().g().a(hVar);
        }
    }

    public class q2 implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ String f2131a;

        public q2(String str) {
            this.f2131a = str;
        }

        @Override // java.lang.Runnable
        public void run() throws JSONException {
            if (d.this.f1905d != null) {
                c.h.a(d.H, " onLogicUnSubscribeStream ");
                try {
                    JSONObject jSONObject = new JSONObject(this.f2131a);
                    JSONObject jSONObject2 = jSONObject.getJSONObject("data");
                    int i2 = jSONObject.getInt("code");
                    String string = jSONObject.getString("msg");
                    int i3 = jSONObject2.getInt("mtype");
                    String string2 = jSONObject2.getString("uid");
                    StreamInfo streamInfo = new StreamInfo();
                    streamInfo.setUid(string2);
                    streamInfo.setMediaType(i3);
                    c.h.a(d.H, " onLogicUnSubscribeStream to ui " + i2);
                    String str = string2 + StrPool.UNDERLINE + i3;
                    c.h.a(d.H, "  onLogicUnSubscribeStream clear mute record: ");
                    d.this.B.remove(str);
                    d.this.f1905d.onUnSubscribeResult(i2, string, streamInfo);
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
            }
        }
    }

    public static /* synthetic */ class r {

        /* renamed from: a, reason: collision with root package name */
        public static final /* synthetic */ int[] f2133a;

        static {
            int[] iArr = new int[a.c.values().length];
            f2133a = iArr;
            try {
                iArr[a.c.NONE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f2133a[a.c.EARPIECE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f2133a[a.c.BLUETOOTH.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f2133a[a.c.SPEAKER_PHONE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f2133a[a.c.WIRED_HEADSET.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    public class r0 implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ StreamInfo f2134a;

        public r0(StreamInfo streamInfo) {
            this.f2134a = streamInfo;
        }

        @Override // java.lang.Runnable
        public void run() {
            e.a.a().g().c(this.f2134a, d.this.f1908g.remove(this.f2134a.getUId() + a.h.a(this.f2134a.getMediaType())));
        }
    }

    public class r1 implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ String f2136a;

        public r1(String str) {
            this.f2136a = str;
        }

        @Override // java.lang.Runnable
        public void run() {
            e.a.a().g().startPlayAudioFile(this.f2136a, false, false);
        }
    }

    public class r2 implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ String f2138a;

        public r2(String str) {
            this.f2138a = str;
        }

        @Override // java.lang.Runnable
        public void run() throws JSONException {
            try {
                JSONObject jSONObject = new JSONObject(this.f2138a);
                int i2 = jSONObject.getInt("code");
                String string = jSONObject.getString("msg");
                JSONObject jSONObject2 = jSONObject.getJSONObject("data");
                int i3 = jSONObject2.getInt("mtype");
                int i4 = jSONObject2.getInt("ttype");
                boolean z2 = jSONObject2.getBoolean(c.i.f2279j);
                c.h.a(d.H, "  onLogicMuteLocalMedia ");
                if (d.this.f1905d != null) {
                    if (i3 == 1) {
                        if (i4 == 1) {
                            d.this.A = z2;
                        } else if (i4 == 2) {
                            d.this.f1926y = z2;
                        }
                    } else if (i3 == 2) {
                        d.this.f1927z = z2;
                    }
                    d.this.f1905d.onLocalStreamMuteRsp(i2, string, i3, i4, z2);
                }
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
    }

    public class s implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ AuthInfo f2140a;

        public s(AuthInfo authInfo) {
            this.f2140a = authInfo;
        }

        @Override // java.lang.Runnable
        public void run() throws JSONException {
            try {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("user_id", this.f2140a.getUId());
                jSONObject.put("app_id", this.f2140a.getAppId());
                jSONObject.put("room_id", this.f2140a.getRoomId());
                String strA = c.e.a(this.f2140a.getUId(), this.f2140a.getRoomId(), this.f2140a.getAppId(), c.e.g());
                if (strA.length() == 0) {
                    return;
                }
                String str = new String(Base64.encode(jSONObject.toString().getBytes(), 2));
                Log.i(d.H, "jsonbady " + str);
                String str2 = str + StrPool.DOT + strA;
                c.h.d(d.H, "generate tokenur " + str2);
                this.f2140a.setToken(str2);
                e.a.a().g().a(this.f2140a);
            } catch (NullPointerException e2) {
                e2.printStackTrace();
            } catch (JSONException e3) {
                e3.printStackTrace();
            }
        }
    }

    public class s0 implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ String f2142a;

        /* renamed from: b, reason: collision with root package name */
        public final /* synthetic */ boolean f2143b;

        public s0(String str, boolean z2) {
            this.f2142a = str;
            this.f2143b = z2;
        }

        @Override // java.lang.Runnable
        public void run() {
            e.a.a().g().muteRemoteAudio(this.f2142a, this.f2143b);
        }
    }

    public class s1 implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ boolean f2145a;

        public s1(boolean z2) {
            this.f2145a = z2;
        }

        @Override // java.lang.Runnable
        public void run() {
            d.this.f1912k = this.f2145a;
            c.h.a(d.H, "setAutoPublish: " + d.this.f1912k);
        }
    }

    public class s2 implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ String f2147a;

        public s2(String str) {
            this.f2147a = str;
        }

        @Override // java.lang.Runnable
        public void run() throws JSONException {
            l3 l3Var;
            try {
                JSONObject jSONObject = new JSONObject(this.f2147a);
                int i2 = jSONObject.getInt("code");
                String string = jSONObject.getString("msg");
                JSONObject jSONObject2 = jSONObject.getJSONObject("data");
                String string2 = jSONObject2.getString("uid");
                int i3 = jSONObject2.getInt("mtype");
                int i4 = jSONObject2.getInt("ttype");
                boolean z2 = jSONObject2.getBoolean(c.i.f2279j);
                if (d.this.f1905d != null) {
                    if (i2 == 0) {
                        String str = string2 + StrPool.UNDERLINE + i3;
                        if (d.this.B == null || d.this.B.size() <= 0 || !d.this.B.containsKey(str)) {
                            l3Var = d.this.new l3();
                        } else {
                            c.h.a(d.H, " " + str + " is existed, update the cache.");
                            l3Var = (l3) d.this.B.get(str);
                        }
                        if (i3 == 1) {
                            if (i4 == 1) {
                                l3Var.a(z2);
                            } else if (i4 == 2) {
                                l3Var.c(z2);
                            }
                        } else if (i3 == 2) {
                            l3Var.b(z2);
                        }
                        l3Var.a(string2);
                        l3Var.a(i3);
                        c.h.a(d.H, "  onLogicMuteRemoteMedia save one record: " + l3Var);
                        d.this.B.put(str, l3Var);
                    }
                    c.h.a(d.H, "  onRemoteStreamMuteRsp called " + i2 + " msg: " + string + " uid: " + string2 + " mediaType: " + i3);
                    d.this.f1905d.onRemoteStreamMuteRsp(i2, string, string2, i3, i4, z2);
                }
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
    }

    public class t implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ AuthInfo f2149a;

        public t(AuthInfo authInfo) {
            this.f2149a = authInfo;
        }

        @Override // java.lang.Runnable
        public void run() {
            e.a.a().g().a(this.f2149a);
        }
    }

    public class t0 implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ String f2151a;

        /* renamed from: b, reason: collision with root package name */
        public final /* synthetic */ boolean f2152b;

        public t0(String str, boolean z2) {
            this.f2151a = str;
            this.f2152b = z2;
        }

        @Override // java.lang.Runnable
        public void run() {
            e.a.a().g().muteRemoteScreenSound(this.f2151a, this.f2152b);
        }
    }

    public class t1 implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ String f2154a;

        /* renamed from: b, reason: collision with root package name */
        public final /* synthetic */ boolean f2155b;

        /* renamed from: c, reason: collision with root package name */
        public final /* synthetic */ boolean f2156c;

        public t1(String str, boolean z2, boolean z3) {
            this.f2154a = str;
            this.f2155b = z2;
            this.f2156c = z3;
        }

        @Override // java.lang.Runnable
        public void run() {
            e.a.a().g().startPlayAudioFile(this.f2154a, this.f2155b, this.f2156c);
        }
    }

    public class t2 implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ String f2158a;

        public t2(String str) {
            this.f2158a = str;
        }

        @Override // java.lang.Runnable
        public void run() throws JSONException {
            if (d.this.f1905d != null) {
                try {
                    JSONObject jSONObject = new JSONObject(this.f2158a);
                    jSONObject.getInt("mtype");
                    d.this.f1905d.onRemoteAudioLevel(jSONObject.getString("uid"), jSONObject.getInt("vol"));
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
            }
        }
    }

    public class u implements Runnable {
        public u() {
        }

        @Override // java.lang.Runnable
        public void run() {
            c.h.a(d.H, "send leave room msg to engine start");
            d.this.B.clear();
            d.this.f1908g.clear();
            e.a.a().g().d(false);
            d.this.f1923v = false;
            d.this.f1924w = false;
            c.h.a(d.H, "send leave room msg to engine end");
        }
    }

    public class u0 implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ String f2161a;

        /* renamed from: b, reason: collision with root package name */
        public final /* synthetic */ boolean f2162b;

        public u0(String str, boolean z2) {
            this.f2161a = str;
            this.f2162b = z2;
        }

        @Override // java.lang.Runnable
        public void run() {
            e.a.a().g().muteRemoteVideo(this.f2161a, this.f2162b);
        }
    }

    public class u1 implements Runnable {
        public u1() {
        }

        @Override // java.lang.Runnable
        public void run() {
            e.a.a().g().stopPlayAudioFile();
        }
    }

    public class u2 implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ String f2165a;

        public u2(String str) {
            this.f2165a = str;
        }

        @Override // java.lang.Runnable
        public void run() throws JSONException {
            if (d.this.f1905d != null) {
                try {
                    d.this.f1905d.onLocalAudioLevel(new JSONObject(this.f2165a).getInt("vol"));
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
            }
        }
    }

    public class v implements Runnable {
        public v() {
        }

        @Override // java.lang.Runnable
        public void run() {
            c.h.a(d.H, "send leave room still local render msg to engine start");
            d.this.B.clear();
            if (d.this.f1918q) {
                e.a.a().g().d(true);
            } else {
                e.a.a().g().d(false);
            }
            d.this.f1923v = false;
            d.this.f1924w = false;
            c.h.a(d.H, "send leave room msg to engine end");
        }
    }

    public class v0 implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ String f2168a;

        /* renamed from: b, reason: collision with root package name */
        public final /* synthetic */ boolean f2169b;

        public v0(String str, boolean z2) {
            this.f2168a = str;
            this.f2169b = z2;
        }

        @Override // java.lang.Runnable
        public void run() {
            e.a.a().g().muteRemoteScreen(this.f2168a, this.f2169b);
        }
    }

    public class v1 implements Runnable {
        public v1() {
        }

        @Override // java.lang.Runnable
        public void run() {
            e.a.a().g().resumeAudioFile();
        }
    }

    public class v2 implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ String f2172a;

        public v2(String str) {
            this.f2172a = str;
        }

        @Override // java.lang.Runnable
        public void run() throws JSONException {
            if (d.this.f1905d != null) {
                try {
                    JSONObject jSONObject = new JSONObject(this.f2172a);
                    d.this.f1905d.onNetWorkQuality(jSONObject.getString("userid"), 1, jSONObject.getInt("mtype"), jSONObject.getInt(DatabaseManager.QUALITY));
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
            }
        }
    }

    public class w implements Runnable {
        public w() {
        }

        @Override // java.lang.Runnable
        public void run() {
            e.a.a().g().switchCamera();
        }
    }

    public class w0 implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ Intent f2175a;

        public w0(Intent intent) {
            this.f2175a = intent;
        }

        @Override // java.lang.Runnable
        public void run() {
            e.a.a().a(this.f2175a);
        }
    }

    public class w1 implements Runnable {
        public w1() {
        }

        @Override // java.lang.Runnable
        public void run() {
            e.a.a().g().pauseAudioFile();
        }
    }

    public class w2 implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ String f2178a;

        public w2(String str) {
            this.f2178a = str;
        }

        @Override // java.lang.Runnable
        public void run() throws JSONException {
            if (d.this.f1905d != null) {
                try {
                    JSONObject jSONObject = new JSONObject(this.f2178a);
                    d.this.f1905d.onNetWorkQuality(jSONObject.getString("uid"), 2, jSONObject.getInt("mtype"), jSONObject.getInt(DatabaseManager.QUALITY));
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
            }
        }
    }

    public class x implements Runnable {
        public x() {
        }

        @Override // java.lang.Runnable
        public void run() {
            e.a.a().g().switchCameraSkipSameSide();
        }
    }

    public class x0 implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ RecordProfile f2181a;

        public x0(RecordProfile recordProfile) {
            this.f2181a = recordProfile;
        }

        @Override // java.lang.Runnable
        public void run() {
            e.a.a().g().a(this.f2181a);
        }
    }

    public class x1 implements Runnable {
        public x1() {
        }

        @Override // java.lang.Runnable
        public void run() {
            e.a.a().g().b();
        }
    }

    public class x2 implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ String f2184a;

        public x2(String str) {
            this.f2184a = str;
        }

        /* JADX WARN: Removed duplicated region for block: B:15:0x00a3 A[Catch: JSONException -> 0x0116, TryCatch #0 {JSONException -> 0x0116, blocks: (B:3:0x0018, B:6:0x004c, B:8:0x0062, B:13:0x0092, B:15:0x00a3, B:11:0x0072, B:12:0x007f, B:17:0x00af, B:19:0x00b5, B:21:0x00cb, B:26:0x00fb, B:28:0x010c, B:24:0x00db, B:25:0x00e8), top: B:33:0x0018 }] */
        /* JADX WARN: Removed duplicated region for block: B:28:0x010c A[Catch: JSONException -> 0x0116, TRY_LEAVE, TryCatch #0 {JSONException -> 0x0116, blocks: (B:3:0x0018, B:6:0x004c, B:8:0x0062, B:13:0x0092, B:15:0x00a3, B:11:0x0072, B:12:0x007f, B:17:0x00af, B:19:0x00b5, B:21:0x00cb, B:26:0x00fb, B:28:0x010c, B:24:0x00db, B:25:0x00e8), top: B:33:0x0018 }] */
        /* JADX WARN: Removed duplicated region for block: B:34:? A[RETURN, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:38:? A[RETURN, SYNTHETIC] */
        @Override // java.lang.Runnable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void run() throws org.json.JSONException {
            /*
                Method dump skipped, instructions count: 283
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: b.d.x2.run():void");
        }
    }

    public class y implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ int f2186a;

        public y(int i2) {
            this.f2186a = i2;
        }

        @Override // java.lang.Runnable
        public void run() {
            e.a.a().g().setCameraId(this.f2186a);
        }
    }

    public class y0 implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ MixProfile f2188a;

        public y0(MixProfile mixProfile) {
            this.f2188a = mixProfile;
        }

        @Override // java.lang.Runnable
        public void run() {
            e.a.a().g().a(this.f2188a);
        }
    }

    public class y1 implements Runnable {
        public y1() {
        }

        @Override // java.lang.Runnable
        public void run() {
            if (d.this.f1905d != null) {
                d.this.f1924w = true;
                d.this.f1923v = false;
            }
        }
    }

    public class y2 implements Runnable {
        public y2() {
        }

        @Override // java.lang.Runnable
        public void run() {
            d.this.f1923v = false;
            d.this.f1924w = false;
            d.this.f1908g.clear();
            d.this.B.clear();
            c.h.a(d.H, "mEventliser.onKickoff(-1);");
            if (d.this.f1905d != null) {
                d.this.f1905d.onKickoff(-1);
            }
        }
    }

    public class z implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ Object f2192a;

        /* renamed from: b, reason: collision with root package name */
        public final /* synthetic */ int f2193b;

        /* renamed from: c, reason: collision with root package name */
        public final /* synthetic */ FirstFrameRendered f2194c;

        public class a implements Runnable {

            /* renamed from: a, reason: collision with root package name */
            public final /* synthetic */ Object f2196a;

            public a(Object obj) {
                this.f2196a = obj;
            }

            @Override // java.lang.Runnable
            public void run() {
                ((CoreSurfaceViewRenderer) this.f2196a).setScaleType(z.this.f2193b);
            }
        }

        public class b implements Runnable {

            /* renamed from: a, reason: collision with root package name */
            public final /* synthetic */ Object f2198a;

            public b(Object obj) {
                this.f2198a = obj;
            }

            @Override // java.lang.Runnable
            public void run() {
                ((CoreSurfaceViewRenderer) this.f2198a).setScaleType(z.this.f2193b);
            }
        }

        public class c implements Runnable {

            /* renamed from: a, reason: collision with root package name */
            public final /* synthetic */ Object f2200a;

            public c(Object obj) {
                this.f2200a = obj;
            }

            @Override // java.lang.Runnable
            public void run() {
                ((CoreTextureViewRenderer) this.f2200a).setScaleType(z.this.f2193b);
            }
        }

        public z(Object obj, int i2, FirstFrameRendered firstFrameRendered) {
            this.f2192a = obj;
            this.f2193b = i2;
            this.f2194c = firstFrameRendered;
        }

        @Override // java.lang.Runnable
        public void run() {
            Object obj;
            Object obj2 = this.f2192a;
            if (obj2 instanceof SurfaceViewGroup) {
                CoreSurfaceViewRenderer surfaceView = ((SurfaceViewGroup) obj2).getSurfaceView();
                c.h.a(d.H, "render local SurfaceViewGroup");
                surfaceView.post(new a(surfaceView));
                e.a.a().g().b(1, surfaceView);
                obj = surfaceView;
            } else if (obj2 instanceof CoreSurfaceViewRenderer) {
                c.h.a(d.H, "render local CoreSurfaceViewRenderer");
                CoreSurfaceViewRenderer coreSurfaceViewRenderer = (CoreSurfaceViewRenderer) obj2;
                coreSurfaceViewRenderer.setFrameRendered(this.f2194c);
                coreSurfaceViewRenderer.post(new b(obj2));
                e.a.a().g().b(1, obj2);
                obj = obj2;
            } else if (obj2 instanceof CoreTextureViewRenderer) {
                c.h.a(d.H, "render local CoreTextureViewRenderer");
                ((CoreTextureViewRenderer) obj2).getTextureView().post(new c(obj2));
                e.a.a().g().b(1, obj2);
                obj = obj2;
            } else {
                obj = null;
            }
            d.this.f1907f = obj;
        }
    }

    public class z0 implements Runnable {
        public z0() {
        }

        @Override // java.lang.Runnable
        public void run() {
            e.a.a().g().a(2, (String[]) null);
        }
    }

    public class z1 implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ String f2203a;

        public z1(String str) {
            this.f2203a = str;
        }

        @Override // java.lang.Runnable
        public void run() throws JSONException {
            try {
                c.h.a(d.H, "onLogicServerReconnecting");
                JSONObject jSONObject = new JSONObject(this.f2203a);
                jSONObject.getString("uid");
                String string = jSONObject.getString("uid");
                if (d.this.f1905d != null) {
                    d.this.f1924w = true;
                    d.this.f1923v = false;
                    d.this.f1905d.onRejoiningRoom(string);
                }
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
    }

    public class z2 implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ int f2205a;

        public z2(int i2) {
            this.f2205a = i2;
        }

        @Override // java.lang.Runnable
        public void run() {
            j.h hVar = new j.h();
            switch (this.f2205a) {
                case 0:
                    hVar.f(320);
                    hVar.b(180);
                    hVar.a(20);
                    hVar.e(100);
                    hVar.d(100);
                    hVar.c(300);
                    break;
                case 1:
                    hVar.f(R2.attr.arcLabelPaddingRight);
                    hVar.b(R2.attr.alignContent);
                    hVar.a(20);
                    hVar.e(200);
                    hVar.d(100);
                    hVar.c(400);
                    break;
                case 2:
                    hVar.f(480);
                    hVar.b(360);
                    hVar.a(20);
                    hVar.e(200);
                    hVar.d(100);
                    hVar.c(400);
                    break;
                case 3:
                    hVar.f(640);
                    hVar.b(360);
                    hVar.a(20);
                    hVar.e(300);
                    hVar.d(100);
                    hVar.c(500);
                    break;
                case 4:
                    hVar.f(640);
                    hVar.b(480);
                    hVar.a(30);
                    hVar.e(300);
                    hVar.d(100);
                    hVar.c(800);
                    break;
                case 5:
                    hVar.f(1280);
                    hVar.b(720);
                    hVar.a(20);
                    hVar.e(600);
                    hVar.d(500);
                    hVar.c(1000);
                    break;
                case 6:
                    hVar.f(R2.attr.iconTint);
                    hVar.b(R2.attr.color_hot_circle_one_end);
                    hVar.a(20);
                    hVar.e(2000);
                    hVar.d(1500);
                    hVar.c(2000);
                    break;
                case 7:
                    hVar.f(640);
                    hVar.b(360);
                    hVar.a(30);
                    hVar.e(400);
                    hVar.d(100);
                    hVar.c(600);
                    break;
                case 8:
                    hVar.f(1280);
                    hVar.b(720);
                    hVar.a(30);
                    hVar.e(1000);
                    hVar.d(300);
                    hVar.c(R2.attr.home_tab_course);
                    break;
                case 9:
                    c.h.a(d.H, "use setCustomizedVideoParam");
                default:
                    hVar.f(R2.attr.arcLabelPaddingRight);
                    hVar.b(R2.attr.alignContent);
                    hVar.a(20);
                    hVar.e(200);
                    hVar.d(100);
                    hVar.c(300);
                    break;
            }
            e.a.a().b(hVar);
            d.b.b(false);
        }
    }

    public d() {
        this.f1903b = null;
        this.f1904c = null;
        this.f1908g = null;
        this.f1919r = null;
        c.h.a(H, " CoreRtcEngineImpl ");
        this.f1908g = new ConcurrentHashMap();
        this.B = new ConcurrentHashMap();
        this.f1921t = 2;
        this.f1922u = 0;
        this.f1909h = false;
        this.f1912k = false;
        this.f1913l = false;
        this.f1914m = false;
        this.f1915n = true;
        this.f1917p = true;
        this.f1916o = true;
        this.f1923v = false;
        this.f1924w = false;
        this.f1926y = false;
        this.f1927z = false;
        this.A = false;
        this.f1918q = false;
        I = new m3();
        this.f1904c = new HandlerThread(H);
        int iB = d.b.b();
        if (iB == 1) {
            this.f1910i = true;
            this.f1911j = false;
        } else if (iB != 2) {
            this.f1910i = true;
            this.f1911j = true;
        } else {
            this.f1910i = false;
            this.f1911j = true;
        }
        c.h.a(H, "mEnableAudioModule: " + this.f1910i);
        if (this.f1910i) {
            k.a aVarA = k.a.a(c.e.b());
            this.f1919r = aVarA;
            aVarA.a(this);
            this.f1919r.c(a.c.SPEAKER_PHONE);
            this.f1920s = getDefaultAudioDevice();
            c.h.a(H, "AudioManageraudio device init with : " + this.f1920s);
        }
        this.f1904c.start();
        this.f1903b = new Handler(this.f1904c.getLooper());
        if (d.b.r()) {
            l();
        }
    }

    public static void destroy() {
        synchronized (G) {
            d dVar = F;
            if (dVar != null) {
                k.a aVar = dVar.f1919r;
                if (aVar != null) {
                    aVar.h();
                    F.f1919r = null;
                }
                final d dVar2 = F;
                dVar2.f1903b.post(new Runnable() { // from class: p.b
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f28148c.g();
                    }
                });
                try {
                    c.h.a(H, "SDK ENGINE WAIT DESTROY");
                    G.wait();
                    c.h.a(H, "SDK ENGINE DESTROY FINISH");
                } catch (InterruptedException e4) {
                    e4.printStackTrace();
                }
                F = null;
            }
        }
    }

    @Override // e.c
    public void A(String str) {
        c.h.a(H, " onLogicStreamListNotify " + str);
        this.f1903b.post(new j2(str));
    }

    @Override // e.c
    public void B(String str) {
        Handler handler = this.f1903b;
        if (handler != null) {
            handler.post(new f(str));
        }
    }

    @Override // e.c
    public void C(String str) {
        Handler handler = this.f1903b;
        if (handler != null) {
            handler.post(new q2(str));
        }
    }

    @Override // e.c
    public void D(String str) {
        Handler handler = this.f1903b;
        if (handler != null) {
            handler.post(new n2(str));
        }
    }

    @Override // e.c
    public void E(String str) {
        Handler handler = this.f1903b;
        if (handler != null) {
            handler.post(new m2(str));
        }
    }

    @Override // e.c
    public void F(String str) {
        c.h.a(H, " onLogicJoinRoom " + str);
        this.f1903b.post(new c2(str));
    }

    @Override // e.c
    public void G(String str) {
        Handler handler = this.f1903b;
        if (handler != null) {
            handler.post(new f2(str));
        }
    }

    @Override // e.c
    public void H(String str) {
        Handler handler = this.f1903b;
        if (handler != null) {
            handler.post(new p2(str));
        }
    }

    @Override // e.c
    public void I(String str) {
        this.f1903b.post(new RunnableC0013d(str));
    }

    public final void J(String str) throws JSONException {
        c.h.a(H, " isAutoSub " + this.f1913l);
        if (this.f1913l) {
            try {
                JSONArray jSONArray = new JSONArray(str);
                int length = jSONArray.length();
                c.h.a(H, " streamList len " + length);
                for (int i4 = 0; i4 < length; i4++) {
                    JSONObject jSONObject = jSONArray.getJSONObject(i4);
                    if (jSONObject != null) {
                        StreamInfo streamInfo = new StreamInfo();
                        streamInfo.setUid(jSONObject.getString("uid"));
                        streamInfo.setHasAudio(jSONObject.getBoolean("audio"));
                        streamInfo.setHasVideo(jSONObject.getBoolean("video"));
                        streamInfo.setHasData(jSONObject.getBoolean("data"));
                        streamInfo.setMuteAudio(jSONObject.getBoolean("muteaudio"));
                        streamInfo.setMuteVideo(jSONObject.getBoolean("mutevideo"));
                        streamInfo.setMediaType(jSONObject.getInt("media_type"));
                        e.a.a().g().b(streamInfo);
                    }
                }
            } catch (JSONException e4) {
                e4.printStackTrace();
            }
        }
    }

    public void K(String str) {
        Map<String, l3> map = this.B;
        if (map == null || map.size() <= 0 || !this.B.containsKey(str)) {
            return;
        }
        l3 l3Var = this.B.get(str);
        if (l3Var.c()) {
            c.h.a(H, "reconnect mute audio : " + str);
            muteRemoteAudio(l3Var.b(), true);
        }
        if (l3Var.e()) {
            c.h.a(H, "reconnect mute video : " + str);
            muteRemoteVideo(l3Var.b(), true);
        }
        if (l3Var.d()) {
            c.h.a(H, "reconnect mute screen : " + str);
            muteRemoteScreen(l3Var.b(), true);
        }
    }

    @Override // b.a
    public int adjustPlaybackSignalVolume(double d4) {
        if (!this.f1910i) {
            c.h.a(H, "Audio module is disable.");
            return 5038;
        }
        Handler handler = this.f1903b;
        if (handler == null) {
            return 5022;
        }
        handler.post(new o(d4));
        return 5015;
    }

    @Override // b.a
    public void adjustRecordVolume(int i4) {
        if (!this.f1910i) {
            c.h.a(H, "Audio module is disable.");
            return;
        }
        Handler handler = this.f1903b;
        if (handler != null) {
            handler.post(new g1(i4));
        }
    }

    @Override // b.a
    public int adjustUserPlaybackSignalVolume(String str, double d4) {
        if (!this.f1910i) {
            c.h.a(H, "Audio module is disable.");
            return 5038;
        }
        Handler handler = this.f1903b;
        if (handler == null) {
            return 5022;
        }
        handler.post(new n(str, d4));
        return 5015;
    }

    @Override // b.a
    public int configLocalAudioPublish(boolean z3) {
        c.h.a(H, "configLocalAudioPublish " + z3);
        if (this.f1923v) {
            return 5004;
        }
        if (!z3) {
            this.f1917p = false;
        } else {
            if (!this.f1910i) {
                c.h.a(H, "Audio module is disable,can not set local audio publish = true");
                return 5038;
            }
            this.f1917p = true;
        }
        c.h.a(H, "mIsCanPubAudio " + this.f1917p);
        return 5015;
    }

    @Override // b.a
    public int configLocalCameraPublish(boolean z3) {
        if (this.f1923v) {
            return 5004;
        }
        this.f1915n = z3;
        return 5015;
    }

    @Override // b.a
    public int configLocalScreenPublish(boolean z3) {
        if (this.f1923v) {
            return 5004;
        }
        this.f1916o = z3;
        return 5015;
    }

    @Override // b.a
    public void controlAudio(boolean z3) {
        if (!this.f1910i) {
            c.h.a(H, "Audio module is disable.");
            return;
        }
        Handler handler = this.f1903b;
        if (handler != null) {
            handler.post(new l1(z3));
        }
    }

    @Override // b.a
    public void controlAudioPlayOut(boolean z3) {
        if (!this.f1910i) {
            c.h.a(H, "Audio module is disable.");
            return;
        }
        Handler handler = this.f1903b;
        if (handler != null) {
            handler.post(new m1(z3));
        }
    }

    @Override // b.a
    public void controlAudioRecord(boolean z3) {
        if (!this.f1910i) {
            c.h.a(H, "Audio module is disable.");
            return;
        }
        Handler handler = this.f1903b;
        if (handler != null) {
            handler.post(new n1(z3));
        }
    }

    @Override // b.a
    public void controlLocalVideo(boolean z3) {
        c.h.a(H, "CoreRtcEngineImplcontrolLocalVideo called " + z3);
        Handler handler = this.f1903b;
        if (handler != null) {
            handler.post(new o1(z3));
        }
    }

    @Override // b.a
    public String copyAssetsFileToSdcard(String str) throws IOException {
        String str2 = d.b.a().getExternalFilesDir("") + "/assets";
        File file = new File(str2);
        if (!file.exists()) {
            file.mkdir();
        }
        String str3 = str2 + "/" + str;
        if (new File(str3).exists()) {
            return str3;
        }
        try {
            InputStream inputStreamOpen = ContextUtils.getApplicationContext().getResources().getAssets().open(str);
            byte[] bArr = new byte[inputStreamOpen.available()];
            inputStreamOpen.read(bArr);
            FileOutputStream fileOutputStream = new FileOutputStream(str3);
            fileOutputStream.write(bArr);
            fileOutputStream.flush();
            fileOutputStream.close();
            inputStreamOpen.close();
            return str3;
        } catch (IOException e4) {
            e4.printStackTrace();
            c.h.c(H, " AssetsFile is not exists, name is: " + str);
            return null;
        }
    }

    @Override // b.a
    public int cropPushResolution(int i4, int i5) {
        Handler handler = this.f1903b;
        if (handler == null) {
            return 5022;
        }
        handler.post(new p(i4, i5));
        return 5015;
    }

    @Override // b.a
    public int getDefaultAudioDevice() {
        if (!this.f1910i) {
            c.h.a(H, "Audio module is disable.");
            return -1;
        }
        int i4 = r.f2133a[this.f1919r.b().ordinal()];
        int i5 = i4 != 2 ? i4 != 3 ? i4 != 4 ? i4 != 5 ? 0 : 2 : 1 : 4 : 3;
        c.h.a(H, "getDefaultAudioDevice: " + i5);
        return i5;
    }

    @Override // b.a
    public RtcNativeOperation getNativeOpInterface() {
        return c.j.a();
    }

    @Override // b.a
    public boolean getSpeakerOn() {
        k.a aVar = this.f1919r;
        if (aVar == null || !this.f1910i) {
            return false;
        }
        boolean zC = aVar.c();
        c.h.a(H, "getSpeakerOn isOn: " + zC);
        return zC;
    }

    @Override // b.a
    public boolean isAudioOnlyMode() {
        return this.f1909h;
    }

    @Override // b.a
    public boolean isAutoPublish() {
        return this.f1912k;
    }

    @Override // b.a
    public boolean isAutoSubscribe() {
        return this.f1913l;
    }

    @Override // b.a
    public boolean isLocalAudioPublishEnabled() {
        return this.f1917p;
    }

    @Override // b.a
    public boolean isLocalCameraPublishEnabled() {
        return this.f1915n;
    }

    @Override // b.a
    public boolean isLocalScreenPublishEnabled() {
        return this.f1916o;
    }

    @Override // b.a
    public void kickOffOthers(int i4, List<String> list) {
        Handler handler = this.f1903b;
        if (handler != null) {
            handler.post(new p1(list, i4));
        }
    }

    @Override // b.a
    public int leaveChannel() {
        if (!this.f1923v && !this.f1924w) {
            return 5037;
        }
        Handler handler = this.f1903b;
        if (handler == null) {
            return 5022;
        }
        handler.post(new u());
        return 5015;
    }

    @Override // b.a
    public int leaveChannelNonStopLocalPreview() {
        if (!this.f1923v && !this.f1924w) {
            return 5037;
        }
        Handler handler = this.f1903b;
        if (handler == null) {
            return 5022;
        }
        handler.post(new v());
        return 5015;
    }

    @Override // b.a
    public void lockExtendDeviceInputBuffer() {
        ReentrantLock reentrantLock = RtcExDevice2YUVCapturer.reentrantLock;
        if (reentrantLock != null) {
            reentrantLock.lock();
            c.h.a("YUVCapture", "YUVCapture: lockExtendDeviceInputBuffer: ");
        }
    }

    @Override // b.a
    public void messageNotify(String str) {
        Handler handler = this.f1903b;
        if (handler != null) {
            handler.post(new i1(str));
        }
    }

    @Override // b.a
    public int muteLocalMic(boolean z3) {
        if (!this.f1923v) {
            return 5037;
        }
        if (!this.f1910i) {
            c.h.a(H, "Audio module is disable.");
            return 5038;
        }
        if (!this.f1915n && !this.f1917p) {
            return 5030;
        }
        Handler handler = this.f1903b;
        if (handler == null) {
            return 5022;
        }
        handler.post(new g0(z3));
        return 5015;
    }

    @Override // b.a
    public int muteRemoteAudio(String str, boolean z3) {
        if (!this.f1923v) {
            return 5037;
        }
        if (!this.f1910i) {
            c.h.a(H, "Audio module is disable.");
            return 5038;
        }
        Handler handler = this.f1903b;
        if (handler == null) {
            return 5022;
        }
        handler.post(new s0(str, z3));
        return 5015;
    }

    @Override // b.a
    public int muteRemoteScreen(String str, boolean z3) {
        if (!this.f1923v) {
            return 5037;
        }
        Handler handler = this.f1903b;
        if (handler == null) {
            return 5022;
        }
        handler.post(new v0(str, z3));
        return 5015;
    }

    @Override // b.a
    public int muteRemoteScreenSound(String str, boolean z3) {
        if (!this.f1923v) {
            return 5037;
        }
        if (!this.f1910i) {
            c.h.a(H, "Audio module is disable.");
            return 5038;
        }
        Handler handler = this.f1903b;
        if (handler == null) {
            return 5022;
        }
        handler.post(new t0(str, z3));
        return 5015;
    }

    @Override // b.a
    public int muteRemoteVideo(String str, boolean z3) {
        if (!this.f1923v) {
            return 5037;
        }
        Handler handler = this.f1903b;
        if (handler == null) {
            return 5022;
        }
        handler.post(new u0(str, z3));
        return 5015;
    }

    @Override // e.c
    public void onAudioFileFinish() {
        Handler handler = this.f1903b;
        if (handler != null) {
            handler.post(new c());
        }
        stopPlayAudioFile();
    }

    @Override // e.c
    public void onFirstLocalVideoFrame() {
        Handler handler = this.f1903b;
        if (handler != null) {
            handler.post(new h());
        }
    }

    @Override // b.a
    public void pauseAudioFile() {
        if (!this.f1910i) {
            c.h.a(H, "Audio module is disable.");
            return;
        }
        Handler handler = this.f1903b;
        if (handler != null) {
            handler.post(new w1());
        }
    }

    @Override // b.a
    public void queryMix() {
        this.f1903b.post(new d1());
    }

    @Override // b.a
    public void releaseExtendDeviceInputBuffer() {
        ReentrantLock reentrantLock = RtcExDevice2YUVCapturer.reentrantLock;
        if (reentrantLock != null) {
            c.h.a("YUVCapture", "YUVCapture: releaseExtendDeviceInputBuffer: ");
            reentrantLock.unlock();
        }
    }

    @TargetApi(21)
    public void requestScreenCapture(Activity activity) {
        if (d.b.a() != null) {
            activity.startActivityForResult(((MediaProjectionManager) d.b.a().getSystemService("media_projection")).createScreenCaptureIntent(), 1000);
        }
    }

    @Override // b.a
    public void resumeAudioFile() {
        if (!this.f1910i) {
            c.h.a(H, "Audio module is disable.");
            return;
        }
        Handler handler = this.f1903b;
        if (handler != null) {
            handler.post(new v1());
        }
    }

    @Override // e.c
    public void s(String str) {
        Handler handler = this.f1903b;
        if (handler != null) {
            handler.post(new r2(str));
        }
    }

    @Override // b.a
    public int setAudioOnlyMode(boolean z3) {
        if (this.f1923v) {
            return 5004;
        }
        Handler handler = this.f1903b;
        if (handler == null) {
            return 5022;
        }
        handler.post(new o2(z3));
        return 5015;
    }

    @Override // b.a
    public int setAutoPublish(boolean z3) {
        if (this.f1923v) {
            return 5004;
        }
        Handler handler = this.f1903b;
        if (handler == null) {
            return 5022;
        }
        handler.post(new s1(z3));
        return 5015;
    }

    @Override // b.a
    public int setAutoSubscribe(boolean z3) {
        if (this.f1923v) {
            return 5004;
        }
        Handler handler = this.f1903b;
        if (handler == null) {
            return 5022;
        }
        handler.post(new d2(z3));
        return 5015;
    }

    @Override // b.a
    public int setCameraId(int i4) {
        Handler handler = this.f1903b;
        if (handler == null) {
            return 5022;
        }
        handler.post(new y(i4));
        return 5015;
    }

    @Override // b.a
    public int setFlashOn(boolean z3) {
        Handler handler = this.f1903b;
        if (handler == null) {
            return 5022;
        }
        handler.post(new m(z3));
        return 5015;
    }

    @Override // b.a
    public void setSpeakerOn(boolean z3) {
        if (this.f1919r == null || !this.f1910i) {
            return;
        }
        c.h.a(H, "audioManager.setSpeakerOn: " + z3);
        this.f1919r.b(z3);
    }

    @Override // b.a
    public boolean startPlayAudioFile(String str) {
        if (!this.f1910i) {
            c.h.a(H, "Audio module is disable.");
            return false;
        }
        if (str == null) {
            c.h.c(H, " filePath is null");
            return false;
        }
        if (new File(str).exists()) {
            Handler handler = this.f1903b;
            if (handler == null) {
                return false;
            }
            handler.post(new r1(str));
            return true;
        }
        c.h.a(H, " File is not exists, name is: " + str);
        return false;
    }

    @Override // b.a
    public void stopPlayAudioFile() {
        if (!this.f1910i) {
            c.h.a(H, "Audio module is disable.");
            return;
        }
        Handler handler = this.f1903b;
        if (handler != null) {
            handler.post(new u1());
        }
    }

    @Override // b.a
    public void stopRecord() {
        if (this.f1910i) {
            this.f1903b.post(new z0());
        } else {
            c.h.a(H, "Audio module is disable.");
        }
    }

    @Override // b.a
    public void stopRelay(String[] strArr) {
        this.f1903b.post(new b1(strArr));
    }

    @Override // b.a
    public int switchCamera() {
        Handler handler = this.f1903b;
        if (handler == null) {
            return 5022;
        }
        handler.post(new w());
        return 5015;
    }

    @Override // b.a
    public int switchCameraSkipSameSide() {
        Handler handler = this.f1903b;
        if (handler == null) {
            return 5022;
        }
        handler.post(new x());
        return 5015;
    }

    @Override // e.c
    public void t(String str) {
        this.f1903b.post(new j3(str));
    }

    @Override // e.c
    public void u(String str) {
        Handler handler = this.f1903b;
        if (handler != null) {
            handler.post(new u2(str));
        }
    }

    @Override // e.c
    public void v(String str) {
        Handler handler = this.f1903b;
        if (handler != null) {
            handler.post(new y2());
        }
    }

    @Override // e.c
    public void w(String str) {
        Handler handler = this.f1903b;
        if (handler != null) {
            handler.post(new b2());
        }
    }

    @Override // e.c
    public void x(String str) {
        this.f1903b.post(new i3(str));
    }

    @Override // e.c
    public void y(String str) {
        Handler handler = this.f1903b;
        if (handler != null) {
            handler.post(new w2(str));
        }
    }

    @Override // e.c
    public void z(String str) {
        this.f1903b.post(new e2(str));
    }

    public static d k(int i4) {
        synchronized (G) {
            K = i4;
            if (F == null) {
                F = new d();
            }
        }
        e.a.a(K, F, I);
        return F;
    }

    public void l() {
        c.h.a(H, "start monitor service");
        if (d.b.a() != null) {
            c.h.a(H, "start bind service");
            d.b.a().bindService(new Intent(d.b.a(), (Class<?>) MonitorService.class), this.D, 1);
        }
    }

    @Override // e.c
    public void m(String str) {
        Handler handler = this.f1903b;
        if (handler != null) {
            handler.post(new h2(str));
        }
    }

    @Override // e.c
    public void n(String str) {
        Handler handler = this.f1903b;
        if (handler != null) {
            handler.post(new g2(str));
        }
    }

    @Override // e.c
    public void p(String str) {
        Handler handler = this.f1903b;
        if (handler != null) {
            handler.post(new z1(str));
        }
    }

    @Override // e.c
    public void q(String str) {
        c.h.a(H, " onLogicUserListNotify " + str);
        this.f1903b.post(new i2(str));
    }

    @Override // e.c
    public void r(String str) {
        Handler handler = this.f1903b;
        if (handler != null) {
            handler.post(new x2(str));
        }
    }

    @Override // b.a
    public void b(b.b bVar) {
        this.f1905d = bVar;
    }

    @Override // b.a
    public int c(int i4) {
        Handler handler = this.f1903b;
        if (handler == null) {
            return 5022;
        }
        handler.post(new k3(i4));
        return 5015;
    }

    @Override // b.a
    public int e(int i4) {
        c.h.a(H, "app called  unPublish: " + i4);
        if (!this.f1923v) {
            return 5037;
        }
        c.h.a(H, " unPublish mediaType is: " + i4);
        if (this.f1903b == null) {
            return 5022;
        }
        if (this.f1912k) {
            c.h.a(H, "AutoPublish is true.");
        }
        int i5 = this.f1921t;
        if (i5 != 2 && i5 != 0) {
            return 5028;
        }
        this.f1903b.post(new e0(i4));
        return 5015;
    }

    @Override // e.c
    public void f(String str) {
        Handler handler = this.f1903b;
        if (handler != null) {
            handler.post(new n0(str));
        }
    }

    @Override // b.a
    public int g(int i4) {
        c.h.a(H, "app called unPublishOnly: " + i4);
        if (!this.f1923v) {
            return 5037;
        }
        c.h.a(H, " unPublishOnly mediaType is: " + i4);
        if (this.f1903b == null) {
            return 5022;
        }
        if (this.f1912k) {
            c.h.a(H, "AutoPublish is true.");
        }
        int i5 = this.f1921t;
        if (i5 != 2 && i5 != 0) {
            return 5028;
        }
        this.f1903b.post(new f0(i4));
        return 5015;
    }

    public final void h() {
        Map<String, Object> map = this.f1908g;
        if (map != null) {
            for (String str : map.keySet()) {
                c.h.a(H, "render map key: " + str + " value: " + this.f1908g.get(str));
            }
        }
    }

    @Override // b.a
    public int i(int i4) {
        if (this.f1923v) {
            return 5004;
        }
        Handler handler = this.f1903b;
        if (handler == null) {
            return 5022;
        }
        handler.post(new z2(i4));
        return 5015;
    }

    @Override // b.a
    public void j(int i4) {
        if (this.f1910i) {
            a.c cVar = a.c.NONE;
            if (i4 == 1) {
                cVar = a.c.SPEAKER_PHONE;
            } else if (i4 == 2) {
                cVar = a.c.WIRED_HEADSET;
            } else if (i4 == 3) {
                cVar = a.c.EARPIECE;
            } else if (i4 == 4) {
                cVar = a.c.BLUETOOTH;
            }
            if (this.f1919r != null) {
                c.h.a(H, "audioManager.selectAudioDevice: " + cVar);
                this.f1919r.a(cVar);
            }
            this.f1920s = getDefaultAudioDevice();
            return;
        }
        c.h.a(H, "Audio module is disable.");
    }

    public int b(DataProvider dataProvider) {
        Handler handler = this.f1903b;
        if (handler == null) {
            return 5022;
        }
        handler.post(new h1(dataProvider));
        return 5015;
    }

    @Override // b.a
    public int d(int i4) {
        if (this.f1923v) {
            return 5004;
        }
        this.f1922u = i4;
        return 5015;
    }

    public final void m(int i4) {
        int i5 = this.f1921t;
        if (i5 == 2 || i5 == 0) {
            e.a.a().g().c(i4, this.f1908g.remove(a.h.a(i4)));
        }
    }

    @Override // b.a
    public int c(StreamInfo streamInfo) {
        if (!this.f1923v) {
            return 5037;
        }
        Handler handler = this.f1903b;
        if (handler == null) {
            return 5022;
        }
        handler.post(new r0(streamInfo));
        return 5015;
    }

    @Override // b.a
    public void f(int i4) {
        Handler handler = this.f1903b;
        if (handler != null) {
            handler.post(new q1(i4));
        }
    }

    @Override // b.a
    public int b(int i4) {
        this.f1921t = i4;
        return 5015;
    }

    @Override // e.c
    public void d(String str) {
        Handler handler = this.f1903b;
        if (handler != null) {
            handler.post(new t2(str));
        }
    }

    @Override // e.c
    public void h(int i4) {
        Handler handler = this.f1903b;
        if (handler != null) {
            handler.post(new b3(i4));
        }
    }

    @Override // e.c
    public void i(String str) {
        Handler handler = this.f1903b;
        if (handler != null) {
            handler.post(new v2(str));
        }
    }

    @Override // b.a
    public int b(boolean z3, int i4) {
        if (!this.f1923v) {
            return 5037;
        }
        if (i4 == 1) {
            if (!this.f1915n) {
                return 5030;
            }
            Handler handler = this.f1903b;
            if (handler == null) {
                return 5022;
            }
            handler.post(new j0(z3));
            return 5015;
        }
        if (i4 != 2) {
            return 5024;
        }
        if (!this.f1916o) {
            return 5031;
        }
        Handler handler2 = this.f1903b;
        if (handler2 == null) {
            return 5022;
        }
        handler2.post(new k0(z3));
        return 5015;
    }

    public void f() {
        Handler handler = this.f1903b;
        if (handler != null) {
            handler.post(new x1());
        }
    }

    @Override // e.c
    public void l(String str) {
        c.h.a(H, " onLogicStreamConnect " + str);
        Handler handler = this.f1903b;
        if (handler != null) {
            handler.post(new k2(str));
        }
    }

    @Override // b.a
    public void a(VideoFramePreProcessListener videoFramePreProcessListener) {
        j.d.d().a(videoFramePreProcessListener);
    }

    @Override // b.a
    public void c(MixProfile mixProfile) {
        this.f1903b.post(new c1(mixProfile));
    }

    public final void d(StreamInfo streamInfo) {
        if (this.f1913l) {
            e.a.a().g().b(streamInfo);
        }
    }

    @Override // e.c
    public void h(String str) {
        Handler handler = this.f1903b;
        if (handler != null) {
            handler.post(new g(str));
        }
    }

    public void i() {
        if (this.f1905d == null || !this.f1926y) {
            return;
        }
        c.h.a(H, "AudioManager start mute camera.");
        this.f1905d.onLocalStreamMuteRsp(0, "", 1, 2, true);
        b(true, 1);
    }

    @Override // b.a
    public void a(CameraEventListener cameraEventListener) {
        j.d.d().a(cameraEventListener);
    }

    @Override // e.c
    public void c(String str) {
        Handler handler = this.f1903b;
        if (handler != null) {
            handler.post(new e(str));
        }
    }

    @Override // b.a
    public boolean startPlayAudioFile(String str, boolean z3, boolean z4) {
        if (!this.f1910i) {
            c.h.a(H, "Audio module is disable.");
            return false;
        }
        if (str == null) {
            c.h.c(H, " filePath is null");
            return false;
        }
        File file = new File(str);
        if (!c.e.a(str) && !file.exists()) {
            c.h.a(H, " Invalid local or remote file. Path is: " + str);
            return false;
        }
        Handler handler = this.f1903b;
        if (handler == null) {
            return false;
        }
        handler.post(new t1(str, z3, z4));
        return true;
    }

    public int a(Intent intent) {
        Handler handler = this.f1903b;
        if (handler == null) {
            return 5022;
        }
        handler.post(new w0(intent));
        return 5015;
    }

    @Override // e.c
    public void k(String str) {
        this.f1903b.post(new l2(str));
    }

    public final void l(int i4) {
        j();
        if (i4 == 1) {
            i();
        } else if (i4 == 2) {
            k();
        }
    }

    @Override // b.a
    public void c() {
        new Handler(Looper.getMainLooper()).post(new q());
    }

    @Override // e.c
    public void e(String str) {
        Handler handler = this.f1903b;
        if (handler != null) {
            handler.post(new s2(str));
        }
    }

    @Override // e.c
    public void g(String str) {
        Handler handler = this.f1903b;
        if (handler != null) {
            handler.post(new b(str));
        }
    }

    public void k() {
        if (this.f1905d == null || !this.f1927z) {
            return;
        }
        c.h.a(H, "AudioManagerstart mute screen.");
        this.f1905d.onLocalStreamMuteRsp(0, "", 2, 0, true);
        b(true, 2);
    }

    @Override // b.a
    public int a(a.w wVar) {
        if (this.f1923v) {
            return 5004;
        }
        Handler handler = this.f1903b;
        if (handler == null) {
            return 5022;
        }
        handler.post(new l(wVar));
        return 5015;
    }

    public final int e() {
        if (this.f1912k) {
            boolean z3 = j.d.d().l() == d.EnumC0464d.STARTED || c.d.a(ContextUtils.getApplicationContext());
            boolean zA = c.a.a(ContextUtils.getApplicationContext());
            if (!d.b.v()) {
                if (this.f1915n && !z3) {
                    c.h.a(H, " Video permission denied");
                    return 5037;
                }
                if (this.f1917p && !zA) {
                    c.h.a(H, " Audio permission denied");
                    return 5037;
                }
            }
            int i4 = this.f1921t;
            if (i4 != 2 && i4 != 0) {
                c.h.a(H, "auto pub failed for has no pub role");
            } else if (this.f1909h) {
                if (this.f1917p) {
                    c.h.a(H, " start audio only mAudioMode is: " + this.f1909h + " mIsCanPubAudio is: " + this.f1917p);
                    MediaOp mediaOp = new MediaOp();
                    mediaOp.setEnableAudio(true);
                    mediaOp.setEnableVideo(false);
                    mediaOp.setMediaType(1);
                    e.a.a().g().a(mediaOp);
                }
            } else {
                if (this.f1915n) {
                    MediaOp mediaOp2 = new MediaOp();
                    c.h.a(H, " start camera video audioPermission is: " + zA + " cameraPermission is: " + z3 + " mIsCanPubAudio is:" + this.f1917p);
                    mediaOp2.setEnableAudio(this.f1917p && zA);
                    mediaOp2.setEnableVideo(z3);
                    mediaOp2.setMediaType(1);
                    e.a.a().g().a(mediaOp2);
                } else if (this.f1917p) {
                    c.h.a(H, " start audio only mIsCanPubCam is: " + this.f1915n + " mIsCanPubAudio is: " + this.f1917p);
                    MediaOp mediaOp3 = new MediaOp();
                    mediaOp3.setEnableAudio(true);
                    mediaOp3.setEnableVideo(false);
                    mediaOp3.setMediaType(1);
                    e.a.a().g().a(mediaOp3);
                }
                if (this.f1916o) {
                    MediaOp mediaOp4 = new MediaOp();
                    c.h.a(H, " start screen video audioPermission is: " + zA + " cameraPermission is: " + z3);
                    mediaOp4.setEnableAudio(false);
                    mediaOp4.setEnableVideo(true);
                    mediaOp4.setMediaType(2);
                    e.a.a().g().a(mediaOp4);
                }
            }
        }
        return 0;
    }

    public final void g() {
        c.h.a(H, "SDK ENGINE DESTROY SDKIMPL TASK START");
        this.f1923v = false;
        this.f1924w = false;
        this.f1908g.clear();
        this.B.clear();
        e.l.c();
        if (J) {
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new i(handler));
            J = false;
        }
        this.f1903b.getLooper().quit();
        synchronized (G) {
            c.h.a(H, "SDK ENGINE DESTROY NOTITY START");
            G.notifyAll();
        }
        c.h.a(H, "SDK ENGINE DESTROY SDKIMPL TASK FINISH");
    }

    @Override // b.a
    public int b(StreamInfo streamInfo, Object obj, int i4, FirstFrameRendered firstFrameRendered) {
        if (!this.f1923v) {
            return 5037;
        }
        if (!(obj instanceof SurfaceViewGroup) && !(obj instanceof CoreSurfaceViewRenderer) && !(obj instanceof CoreTextureViewRenderer)) {
            c.h.a(H, " start view in wrong view object: " + obj);
            return 5025;
        }
        c.h.a(H, " CoreRtcEngineImpl start remoteview " + streamInfo + "renderview: " + obj + "scaleType: " + i4);
        Handler handler = this.f1903b;
        if (handler == null) {
            return 5022;
        }
        handler.post(new m0(streamInfo, obj, firstFrameRendered, i4));
        return 5015;
    }

    @Override // e.c
    public void j(String str) {
        Handler handler = this.f1903b;
        if (handler != null) {
            handler.post(new h3(str));
        }
    }

    @Override // b.a
    public int a(AuthInfo authInfo) {
        if (this.f1923v) {
            return 5004;
        }
        if (this.f1924w) {
            c.h.a(H, "joinChannel failed in connecting state.");
            return 5006;
        }
        c.h.a(h.c.BUNDLE_JOIN_ROOM, authInfo.toString());
        if (c.e.f() == 1) {
            if (TextUtils.isEmpty(authInfo.getAppId()) || TextUtils.isEmpty(authInfo.getRoomId()) || TextUtils.isEmpty(authInfo.getUId())) {
                return 5024;
            }
            if (c.e.g().length() == 0) {
                return 5033;
            }
            Handler handler = this.f1903b;
            if (handler == null) {
                return 5022;
            }
            handler.post(new s(authInfo));
            return 5015;
        }
        if (authInfo.getAppId().length() == 0 || authInfo.getRoomId().length() == 0 || authInfo.getToken().length() == 0 || authInfo.getUId().length() == 0) {
            return 5024;
        }
        Handler handler2 = this.f1903b;
        if (handler2 == null) {
            return 5022;
        }
        handler2.post(new t(authInfo));
        return 5015;
    }

    public void j() {
        if (this.f1905d == null || !this.A) {
            return;
        }
        c.h.a(H, "AudioManager start mute mic.");
        this.f1905d.onLocalStreamMuteRsp(0, "", 1, 1, true);
        muteLocalMic(true);
    }

    @Override // b.a
    public int b(StreamInfo streamInfo) {
        if (!this.f1923v) {
            return 5037;
        }
        if (this.f1903b == null) {
            return 5022;
        }
        if (this.f1913l) {
            return 5021;
        }
        if (!streamInfo.isHasAudio() && !streamInfo.isHasVideo()) {
            return 5027;
        }
        this.f1903b.post(new q0(streamInfo));
        return 5015;
    }

    @Override // b.a
    public void b(MixProfile mixProfile) {
        if (!this.f1910i) {
            c.h.a(H, "Audio module is disable.");
        } else {
            this.f1903b.post(new y0(mixProfile));
        }
    }

    @Override // b.a
    public void b(StreamInfo[] streamInfoArr) {
        this.f1903b.post(new e1(streamInfoArr));
    }

    @Override // e.c
    public void b(String str) {
        Handler handler = this.f1903b;
        if (handler != null) {
            handler.post(new a2(str));
        }
    }

    @Override // b.a
    public int a(Object obj, int i4, FirstFrameRendered firstFrameRendered) {
        if (!(j.d.d().l() == d.EnumC0464d.STARTED || c.d.a(ContextUtils.getApplicationContext()))) {
            c.h.a(H, "No camera permission");
            return 5037;
        }
        if (!(obj instanceof SurfaceViewGroup) && !(obj instanceof CoreSurfaceViewRenderer) && !(obj instanceof CoreTextureViewRenderer)) {
            c.h.a(H, " start view in wrong view object: " + obj);
            return 5025;
        }
        Handler handler = this.f1903b;
        if (handler == null) {
            return 5015;
        }
        handler.post(new z(obj, i4, firstFrameRendered));
        return 5015;
    }

    @Override // e.c
    public void b(String str, int i4, boolean z3) {
        Handler handler = this.f1903b;
        if (handler != null) {
            handler.post(new e3(str, z3, i4));
        }
    }

    @Override // e.c
    public void b(int i4, String str, String str2) {
        Handler handler = this.f1903b;
        if (handler != null) {
            handler.post(new g3(i4, str));
        }
    }

    public int b(RtcNotification rtcNotification) {
        Handler handler = this.f1903b;
        if (handler == null) {
            return 5022;
        }
        handler.post(new k(rtcNotification));
        return 5015;
    }

    @Override // b.a
    public int a(StreamInfo streamInfo, Object obj, int i4, FirstFrameRendered firstFrameRendered) {
        c.h.a(H, " CoreRtcEngineImpl start localview media type: " + streamInfo.getMediaType() + " view: " + obj + " scaleType: " + i4);
        if (!(obj instanceof SurfaceViewGroup) && !(obj instanceof CoreSurfaceViewRenderer) && !(obj instanceof CoreTextureViewRenderer)) {
            c.h.a(H, " start view in wrong view object: " + obj);
            return 5025;
        }
        Handler handler = this.f1903b;
        if (handler == null) {
            return 5022;
        }
        handler.post(new a0(streamInfo, obj, i4, firstFrameRendered));
        return 5015;
    }

    @Override // b.a
    public int a(int i4) {
        c.h.a(H, "stopPreview preview mediatype: " + i4);
        Handler handler = this.f1903b;
        if (handler == null) {
            return 5022;
        }
        handler.post(new b0(i4));
        return 5015;
    }

    @Override // b.a
    public int a(int i4, Object obj) {
        Handler handler = this.f1903b;
        if (handler == null) {
            return 5022;
        }
        handler.post(new c0(i4, obj));
        return 5015;
    }

    @Override // b.a
    public int a(int i4, boolean z3, boolean z4) {
        boolean z5 = j.d.d().l() == d.EnumC0464d.STARTED || c.d.a(ContextUtils.getApplicationContext());
        boolean zA = c.a.a(ContextUtils.getApplicationContext());
        if (!d.b.v()) {
            if (z3 && !z5) {
                c.h.a(H, " Video permission denied");
                return 5037;
            }
            if (z4 && !zA) {
                c.h.a(H, " Audio permission denied");
                return 5037;
            }
        }
        c.h.a(H, " publish mediaType is: " + i4);
        if (!this.f1923v) {
            return 5037;
        }
        Handler handler = this.f1903b;
        if (handler == null) {
            return 5022;
        }
        if (this.f1912k) {
            c.h.a(H, "AutoPublish is true.");
            return 5020;
        }
        if (i4 == 2 && this.f1909h) {
            return 5032;
        }
        int i5 = this.f1921t;
        if (i5 != 2 && i5 != 0) {
            return 5028;
        }
        handler.post(new d0(i4, z3, z5, z4, zA));
        return 5015;
    }

    public final void e(StreamInfo streamInfo) {
        if (this.f1913l) {
            e.a.a().g().c(streamInfo, this.f1908g.remove(streamInfo.getUId() + a.h.a(streamInfo.getMediaType())));
        }
    }

    @Override // b.a
    public int a(boolean z3, int i4) {
        if (!this.f1923v) {
            return 5037;
        }
        if (!this.f1910i) {
            c.h.a(H, "Audio module is disable.");
            return 5038;
        }
        if (i4 == 1) {
            if (!this.f1915n && !this.f1917p) {
                return 5030;
            }
            Handler handler = this.f1903b;
            if (handler == null) {
                return 5022;
            }
            handler.post(new h0(z3));
            return 5015;
        }
        if (i4 != 2) {
            return 5024;
        }
        if (!this.f1916o || !this.f1917p) {
            return 5031;
        }
        Handler handler2 = this.f1903b;
        if (handler2 == null) {
            return 5022;
        }
        handler2.post(new i0(z3));
        return 5015;
    }

    @Override // b.a
    public int a(StreamInfo streamInfo) {
        if (!this.f1923v) {
            return 5037;
        }
        Handler handler = this.f1903b;
        if (handler == null) {
            return 5022;
        }
        handler.post(new o0(streamInfo));
        return 5015;
    }

    @Override // b.a
    public int a(StreamInfo streamInfo, Object obj) {
        Handler handler = this.f1903b;
        if (handler == null) {
            return 5022;
        }
        handler.post(new p0(streamInfo, obj));
        return 5015;
    }

    public void a(RecordProfile recordProfile) {
        if (!this.f1910i) {
            c.h.a(H, "Audio module is disable.");
        } else {
            this.f1903b.post(new x0(recordProfile));
        }
    }

    @Override // b.a
    public void a(MixProfile mixProfile) {
        this.f1903b.post(new a1(mixProfile));
    }

    @Override // b.a
    public void a(StreamInfo[] streamInfoArr) {
        this.f1903b.post(new f1(streamInfoArr));
    }

    @Override // b.a
    public void a(boolean z3, StreamInfo streamInfo, ScreenShot screenShot) {
        Handler handler = this.f1903b;
        if (handler != null) {
            handler.post(new j1(streamInfo, screenShot, z3));
        }
    }

    @Override // b.a
    public void a(boolean z3, StreamInfo streamInfo, int i4) {
        Handler handler = this.f1903b;
        if (handler != null) {
            handler.post(new k1(streamInfo, z3, i4));
        }
    }

    @Override // e.c
    public void a(String str) {
        Handler handler = this.f1903b;
        if (handler != null) {
            handler.post(new y1());
        }
    }

    @Override // e.c
    public void a(int i4, String str) {
        Handler handler = this.f1903b;
        if (handler != null) {
            handler.post(new a3(i4, str));
        }
    }

    @Override // e.c
    public void a(String str, boolean z3) {
        Handler handler = this.f1903b;
        if (handler != null) {
            handler.post(new c3(str, z3));
        }
    }

    @Override // e.c
    public void a(String str, int i4, boolean z3) {
        Handler handler = this.f1903b;
        if (handler != null) {
            handler.post(new d3(str, z3, i4));
        }
    }

    @Override // e.c
    public void a(int i4, String str, String str2) {
        Handler handler = this.f1903b;
        if (handler != null) {
            handler.post(new f3(i4, str));
        }
    }

    @Override // k.a.d
    public void a(a.c cVar, Set<a.c> set) {
        this.f1920s = getDefaultAudioDevice();
        Handler handler = this.f1903b;
        if (handler != null && this.f1923v) {
            handler.post(new j());
        }
        c.h.a(H, "AudioManager audio device changeto : " + this.f1920s);
    }
}
