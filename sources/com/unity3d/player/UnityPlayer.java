package com.unity3d.player;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.InputEvent;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.OrientationEventListener;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import cn.hutool.core.text.StrPool;
import com.aliyun.vod.log.core.AliyunLogCommon;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.plv.foundationsdk.web.PLVWebview;
import com.umeng.analytics.pro.am;
import com.unity3d.player.UnityPermissions;
import com.unity3d.player.l;
import com.unity3d.player.q;
import com.unity3d.splash.UnityAds;
import com.unity3d.splash.services.core.device.Device;
import com.unity3d.splash.services.core.device.Storage;
import com.unity3d.splash.services.core.device.StorageManager;
import com.unity3d.splash.services.core.log.DeviceLog;
import com.unity3d.splash.services.core.request.WebRequest;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import org.eclipse.jetty.util.URIUtil;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes6.dex */
public class UnityPlayer extends FrameLayout implements IUnityPlayerLifecycleEvents {
    private static final int ANR_TIMEOUT_SECONDS = 4;
    private static final String ARCORE_ENABLE_METADATA_NAME = "unity.arcore-enable";
    private static final String AUTO_REPORT_FULLY_DRAWN_ENABLE_METADATA_NAME = "unity.auto-report-fully-drawn";
    private static final String LAUNCH_FULLSCREEN = "unity.launch-fullscreen";
    private static final int RUN_STATE_CHANGED_MSG_CODE = 2269;
    private static final String SPLASH_ADS_GAME_ID = "unity.splash-ads-game-id";
    private static final String SPLASH_ADS_SLOGAN = "unity.splash-ads-slogan";
    private static final String SPLASH_ADS_SLOGAN_HEIGHT = "unity.splash-ads-slogan-height";
    private static final String SPLASH_CHECK_URL = "https://check.unity.cn/api/check-license";
    private static final String SPLASH_ENABLE_METADATA_NAME = "unity.splash-enable";
    private static final String SPLASH_MODE_METADATA_NAME = "unity.splash-mode";
    private static final String UNITY_BUILDER_ID = "unity.builder";
    public static Activity currentActivity;
    public static String m_AndroidFilesDir;
    private static String m_InstantGameEngine;
    private static String m_InstantGameName;
    AlertDialog ad;
    private boolean finishLaunchScreenAds;
    private Activity mActivity;
    private Context mContext;
    private SurfaceView mGlView;
    Handler mHandler;
    private Handler mHanlder;
    private int mInitialScreenOrientation;
    private boolean mIsFullscreen;
    private BroadcastReceiver mKillingIsMyBusiness;
    private boolean mMainDisplayOverride;
    private int mNaturalOrientation;
    private OrientationEventListener mOrientationListener;
    private boolean mProcessKillRequested;
    private boolean mQuitting;
    i mSoftInputDialog;
    private o mState;
    private q mVideoPlayerProxy;
    private GoogleARCoreApi m_ARCoreApi;
    private boolean m_AddPhoneCallListener;
    private AudioVolumeHandler m_AudioVolumeHandler;
    private Camera2Wrapper m_Camera2Wrapper;
    private ClipboardManager m_ClipboardManager;
    private final ConcurrentLinkedQueue m_Events;
    private a m_FakeListener;
    private HFPStatus m_HFPStatus;
    g m_MainThread;
    private NetworkConnectivity m_NetworkConnectivity;
    private OrientationLockListener m_OrientationLockListener;
    private com.unity3d.player.h m_PersistentUnitySurface;
    private c m_PhoneCallListener;
    private l m_SplashScreen;
    private TelephonyManager m_TelephonyManager;
    private IUnityPlayerLifecycleEvents m_UnityPlayerLifecycleEvents;
    private Uri m_launchUri;
    private k m_splashAdsScreen;
    private boolean shouldShowLaunchScreenAds;
    private Timer timer;
    private TimerTask timerTask;

    public class a implements SensorEventListener {
        public a() {
        }

        @Override // android.hardware.SensorEventListener
        public final void onAccuracyChanged(Sensor sensor, int i2) {
        }

        @Override // android.hardware.SensorEventListener
        public final void onSensorChanged(SensorEvent sensorEvent) {
        }
    }

    /* JADX WARN: $VALUES field not found */
    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    public static final class b {

        /* renamed from: a, reason: collision with root package name */
        public static final int f23989a = 1;

        /* renamed from: b, reason: collision with root package name */
        public static final int f23990b = 2;

        /* renamed from: c, reason: collision with root package name */
        public static final int f23991c = 3;

        /* renamed from: d, reason: collision with root package name */
        private static final /* synthetic */ int[] f23992d = {1, 2, 3};
    }

    public class c extends PhoneStateListener {
        private c() {
        }

        public /* synthetic */ c(UnityPlayer unityPlayer, byte b3) {
            this();
        }

        @Override // android.telephony.PhoneStateListener
        public final void onCallStateChanged(int i2, String str) {
            UnityPlayer.this.nativeMuteMasterAudio(i2 == 1);
        }
    }

    public class d extends AsyncTask {
        public d() {
        }

        @Override // android.os.AsyncTask
        public final Void doInBackground(String... strArr) throws JSONException {
            String str = strArr[0];
            String str2 = strArr[1];
            try {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("game_bundle_id", str);
                jSONObject.put("game_bundle_hash", str2);
                WebRequest webRequest = new WebRequest(UnityPlayer.SPLASH_CHECK_URL, "POST", null);
                webRequest.setBody(jSONObject.toString());
                String strMakeRequest = webRequest.makeRequest();
                if (webRequest.getResponseCode() == 200 && Boolean.valueOf(new JSONObject(strMakeRequest).optBoolean("registered")).booleanValue()) {
                    com.unity3d.player.f.Log(4, "Game Bundle Registered");
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            return null;
        }
    }

    public class e extends AsyncTask {
        public e() {
        }

        @Override // android.os.AsyncTask
        public final Void doInBackground(String... strArr) throws JSONException {
            String str = strArr[0];
            try {
                String str2 = Build.MANUFACTURER + "/" + Build.MODEL + "/" + Build.DEVICE;
                String strHash_sha256 = UnityPlayer.hash_sha256(str);
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("game_bundle_hash", strHash_sha256);
                jSONObject.put("device_model", str2);
                jSONObject.put("platform", "android");
                jSONObject.put("unity_hash", UnityPlayer.this.getDeviceId());
                jSONObject.put("splash_sdk_version", "2021.3.22f1c1");
                jSONObject.put("builder", UnityPlayer.this.getBuilderUserId());
                WebRequest webRequest = new WebRequest(UnityPlayer.SPLASH_CHECK_URL + UnityPlayer.getQueryString(jSONObject), "GET", null);
                String strMakeRequest = webRequest.makeRequest();
                if (webRequest.getResponseCode() != 200) {
                    return null;
                }
                JSONObject jSONObject2 = new JSONObject(strMakeRequest);
                Boolean boolValueOf = Boolean.valueOf(jSONObject2.optBoolean("blocked"));
                Boolean boolValueOf2 = Boolean.valueOf(jSONObject2.optBoolean("show_ads"));
                String strOptString = jSONObject2.optString("game_id");
                if (!Boolean.valueOf(jSONObject2.optBoolean("registered")).booleanValue()) {
                    UnityPlayer.this.new d().execute(str, strHash_sha256);
                }
                if (UnityPlayer.this.mActivity == null) {
                    return null;
                }
                String splashGameId = UnityPlayer.this.getSplashGameId();
                if (splashGameId != null && splashGameId.length() > 0) {
                    boolValueOf2 = Boolean.TRUE;
                    strOptString = splashGameId;
                }
                SharedPreferences.Editor editorEdit = UnityPlayer.this.mActivity.getSharedPreferences("game_detail", 0).edit();
                editorEdit.putString("game_id", strOptString);
                editorEdit.putBoolean("show_ads", boolValueOf2.booleanValue());
                editorEdit.putBoolean("blocked", boolValueOf.booleanValue());
                editorEdit.putString("url", jSONObject2.optString("url"));
                editorEdit.putString("hash", jSONObject2.optString("hash"));
                editorEdit.putString("version", jSONObject2.optString("version"));
                editorEdit.commit();
                if (boolValueOf.booleanValue() || !boolValueOf2.booleanValue() || strOptString == null || strOptString == "") {
                    return null;
                }
                UnityAds.initialize(UnityPlayer.this.mActivity, strOptString, null);
                return null;
            } catch (Exception e2) {
                e2.printStackTrace();
                return null;
            }
        }
    }

    public enum f {
        PAUSE,
        RESUME,
        QUIT,
        SURFACE_LOST,
        SURFACE_ACQUIRED,
        FOCUS_LOST,
        FOCUS_GAINED,
        NEXT_FRAME,
        URL_ACTIVATED,
        ORIENTATION_ANGLE_CHANGE,
        SPLASH_ADS_DISMISS
    }

    public class g extends Thread {

        /* renamed from: a, reason: collision with root package name */
        Handler f24008a;

        /* renamed from: b, reason: collision with root package name */
        boolean f24009b;

        /* renamed from: c, reason: collision with root package name */
        boolean f24010c;

        /* renamed from: d, reason: collision with root package name */
        int f24011d;

        /* renamed from: e, reason: collision with root package name */
        int f24012e;

        /* renamed from: f, reason: collision with root package name */
        int f24013f;

        /* renamed from: g, reason: collision with root package name */
        int f24014g;

        /* renamed from: h, reason: collision with root package name */
        boolean f24015h;

        /* renamed from: i, reason: collision with root package name */
        int f24016i;

        /* renamed from: j, reason: collision with root package name */
        int f24017j;

        private g() {
            this.f24009b = false;
            this.f24010c = false;
            this.f24011d = b.f23990b;
            this.f24012e = 0;
            this.f24015h = false;
            this.f24016i = 5;
            this.f24017j = 5;
        }

        public /* synthetic */ g(UnityPlayer unityPlayer, byte b3) {
            this();
        }

        private void a(f fVar) {
            Handler handler = this.f24008a;
            if (handler != null) {
                Message.obtain(handler, 2269, fVar).sendToTarget();
            }
        }

        public final void a() {
            a(f.QUIT);
        }

        public final void a(int i2, int i3) {
            this.f24013f = i2;
            this.f24014g = i3;
            a(f.ORIENTATION_ANGLE_CHANGE);
        }

        public final void a(Runnable runnable) {
            if (this.f24008a == null) {
                return;
            }
            a(f.PAUSE);
            Message.obtain(this.f24008a, runnable).sendToTarget();
        }

        public final void b() {
            a(f.RESUME);
        }

        public final void b(Runnable runnable) {
            if (this.f24008a == null) {
                return;
            }
            a(f.SURFACE_LOST);
            Message.obtain(this.f24008a, runnable).sendToTarget();
        }

        public final void c() {
            a(f.FOCUS_GAINED);
        }

        public final void c(Runnable runnable) {
            Handler handler = this.f24008a;
            if (handler == null) {
                return;
            }
            Message.obtain(handler, runnable).sendToTarget();
            a(f.SURFACE_ACQUIRED);
        }

        public final void d() {
            a(f.FOCUS_LOST);
        }

        public final void d(Runnable runnable) {
            Handler handler = this.f24008a;
            if (handler != null) {
                Message.obtain(handler, runnable).sendToTarget();
            }
        }

        public final void e() {
            a(f.URL_ACTIVATED);
        }

        public final void f() {
            a(f.SPLASH_ADS_DISMISS);
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public final void run() {
            setName("UnityMain");
            Looper.prepare();
            this.f24008a = new Handler(new Handler.Callback() { // from class: com.unity3d.player.UnityPlayer.g.1
                private void a() {
                    g gVar = g.this;
                    if (gVar.f24011d == b.f23991c && gVar.f24010c) {
                        UnityPlayer.this.nativeFocusChanged(true);
                        g.this.f24011d = b.f23989a;
                    }
                }

                @Override // android.os.Handler.Callback
                public final boolean handleMessage(Message message) {
                    int i2;
                    if (message.what != 2269) {
                        return false;
                    }
                    f fVar = (f) message.obj;
                    f fVar2 = f.NEXT_FRAME;
                    if (fVar == fVar2) {
                        g gVar = g.this;
                        gVar.f24012e--;
                        UnityPlayer.this.executeGLThreadJobs();
                        g gVar2 = g.this;
                        if (!gVar2.f24009b || !gVar2.f24010c) {
                            return true;
                        }
                        int i3 = gVar2.f24016i;
                        if (i3 >= 0) {
                            if (i3 == 0) {
                                if (UnityPlayer.this.getSplashEnabled()) {
                                    UnityPlayer.this.DisableStaticSplashScreen();
                                }
                                if (UnityPlayer.this.mActivity != null && UnityPlayer.this.getAutoReportFullyDrawnEnabled()) {
                                    UnityPlayer.this.mActivity.reportFullyDrawn();
                                }
                            }
                            g.this.f24016i--;
                        }
                        g gVar3 = g.this;
                        if (gVar3.f24016i == 0 && UnityPlayer.this.shouldShowLaunchScreenAds) {
                            UnityPlayer.this.ShowSplashAdsScreen();
                        }
                        g gVar4 = g.this;
                        if (gVar4.f24015h && (i2 = gVar4.f24017j) >= 0) {
                            if (i2 == 0) {
                                UnityPlayer.this.DisableSplashAdsScreen();
                            }
                            g.this.f24017j--;
                        }
                        if (!UnityPlayer.this.isFinishing() && !UnityPlayer.this.nativeRender()) {
                            UnityPlayer.this.finish();
                        }
                    } else if (fVar == f.QUIT) {
                        Looper.myLooper().quit();
                    } else if (fVar == f.RESUME) {
                        g.this.f24009b = true;
                    } else if (fVar == f.PAUSE) {
                        g.this.f24009b = false;
                    } else if (fVar == f.SURFACE_LOST) {
                        g.this.f24010c = false;
                    } else {
                        if (fVar == f.SURFACE_ACQUIRED) {
                            g.this.f24010c = true;
                        } else if (fVar == f.FOCUS_LOST) {
                            g gVar5 = g.this;
                            if (gVar5.f24011d == b.f23989a) {
                                UnityPlayer.this.nativeFocusChanged(false);
                            }
                            g.this.f24011d = b.f23990b;
                        } else if (fVar == f.FOCUS_GAINED) {
                            g.this.f24011d = b.f23991c;
                        } else if (fVar == f.URL_ACTIVATED) {
                            UnityPlayer unityPlayer = UnityPlayer.this;
                            unityPlayer.nativeSetLaunchURL(unityPlayer.getLaunchURL());
                        } else if (fVar == f.ORIENTATION_ANGLE_CHANGE) {
                            g gVar6 = g.this;
                            UnityPlayer.this.nativeOrientationChanged(gVar6.f24013f, gVar6.f24014g);
                        } else if (fVar == f.SPLASH_ADS_DISMISS) {
                            g.this.f24015h = true;
                        }
                        a();
                    }
                    g gVar7 = g.this;
                    if (gVar7.f24009b && gVar7.f24012e <= 0) {
                        Message.obtain(gVar7.f24008a, 2269, fVar2).sendToTarget();
                        g.this.f24012e++;
                    }
                    return true;
                }
            });
            Looper.loop();
        }
    }

    public abstract class h implements Runnable {
        private h() {
        }

        public /* synthetic */ h(UnityPlayer unityPlayer, byte b3) {
            this();
        }

        public abstract void a();

        @Override // java.lang.Runnable
        public final void run() {
            if (UnityPlayer.this.isFinishing()) {
                return;
            }
            a();
        }
    }

    static {
        new n().a();
    }

    public UnityPlayer(Context context) {
        this(context, null);
    }

    public UnityPlayer(Context context, IUnityPlayerLifecycleEvents iUnityPlayerLifecycleEvents) throws JSONException, IllegalAccessException, NoSuchMethodException, ClassNotFoundException, SecurityException, IllegalArgumentException, InvocationTargetException {
        super(context);
        this.mHandler = new Handler();
        this.mInitialScreenOrientation = -1;
        byte b3 = 0;
        this.mMainDisplayOverride = false;
        this.mIsFullscreen = true;
        this.mState = new o();
        this.m_Events = new ConcurrentLinkedQueue();
        this.mKillingIsMyBusiness = null;
        this.mOrientationListener = null;
        this.m_MainThread = new g(this, b3);
        this.m_AddPhoneCallListener = false;
        this.m_PhoneCallListener = new c(this, b3);
        this.m_ARCoreApi = null;
        this.m_FakeListener = new a();
        this.m_Camera2Wrapper = null;
        this.m_HFPStatus = null;
        this.m_AudioVolumeHandler = null;
        this.m_OrientationLockListener = null;
        this.m_launchUri = null;
        this.m_NetworkConnectivity = null;
        this.finishLaunchScreenAds = false;
        this.shouldShowLaunchScreenAds = false;
        this.m_UnityPlayerLifecycleEvents = null;
        this.mProcessKillRequested = true;
        this.mSoftInputDialog = null;
        this.ad = null;
        this.mHanlder = null;
        this.timer = new Timer();
        this.timerTask = null;
        this.m_UnityPlayerLifecycleEvents = iUnityPlayerLifecycleEvents == null ? this : iUnityPlayerLifecycleEvents;
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            this.mActivity = activity;
            currentActivity = activity;
            this.mInitialScreenOrientation = activity.getRequestedOrientation();
            this.m_launchUri = this.mActivity.getIntent().getData();
            m_InstantGameName = currentActivity.getIntent().getStringExtra("instantGame");
            String stringExtra = currentActivity.getIntent().getStringExtra("engineFolder");
            m_InstantGameEngine = stringExtra;
            if (stringExtra == null) {
                m_InstantGameEngine = "2019";
            }
            m_AndroidFilesDir = context.getFilesDir().getAbsolutePath();
            if (m_InstantGameName != null) {
                String stringExtra2 = currentActivity.getIntent().getStringExtra("unity");
                currentActivity.getIntent().putExtra("unity", (((stringExtra2 == null ? "" : stringExtra2) + " -instantGame " + m_InstantGameName) + " -instantGameEngine " + m_InstantGameEngine) + " -overrideMonoSearchPath " + ((m_AndroidFilesDir + "/UnityPlayers/" + m_InstantGameEngine) + "/Managed"));
            }
        }
        this.mContext = context;
        EarlyEnableFullScreenIfEnabled();
        this.mNaturalOrientation = getNaturalOrientation(getResources().getConfiguration().orientation);
        if (this.mActivity != null && getSplashEnabled()) {
            l lVar = new l(this.mContext, l.a.a()[getSplashMode()]);
            this.m_SplashScreen = lVar;
            addView(lVar);
        }
        hideStatusBar();
        if (currentActivity != null) {
            this.m_PersistentUnitySurface = new com.unity3d.player.h(this.mContext);
        }
        preloadJavaPlugins();
        String strLoadNative = loadNative(getUnityNativeLibraryPath(this.mContext));
        if (!o.c()) {
            com.unity3d.player.f.Log(6, "Your hardware does not support this application.");
            AlertDialog alertDialogCreate = new AlertDialog.Builder(this.mContext).setTitle("Failure to initialize!").setPositiveButton(PLVWebview.MESSAGE_OK, new DialogInterface.OnClickListener() { // from class: com.unity3d.player.UnityPlayer.1
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i2) {
                    UnityPlayer.this.finish();
                }
            }).setMessage("Your hardware does not support this application.\n\n" + strLoadNative + "\n\n Press OK to quit.").create();
            alertDialogCreate.setCancelable(false);
            alertDialogCreate.show();
            return;
        }
        initJni(context);
        this.mState.c(true);
        registerLaunch();
        SurfaceView surfaceViewCreateGlView = CreateGlView();
        this.mGlView = surfaceViewCreateGlView;
        surfaceViewCreateGlView.setContentDescription(GetGlViewContentDescription(context));
        addView(this.mGlView);
        if (this.shouldShowLaunchScreenAds) {
            k kVarGenerateSplashView = generateSplashView(this.mContext);
            this.m_splashAdsScreen = kVarGenerateSplashView;
            if (kVarGenerateSplashView != null) {
                addView(kVarGenerateSplashView);
            } else {
                this.finishLaunchScreenAds = true;
            }
        }
        View view = this.m_SplashScreen;
        if (view != null) {
            bringChildToFront(view);
        }
        this.mQuitting = false;
        hideStatusBar();
        this.m_TelephonyManager = (TelephonyManager) this.mContext.getSystemService(AliyunLogCommon.TERMINAL_TYPE);
        this.m_ClipboardManager = (ClipboardManager) this.mContext.getSystemService("clipboard");
        this.m_Camera2Wrapper = new Camera2Wrapper(this.mContext);
        this.m_HFPStatus = new HFPStatus(this.mContext);
        this.m_MainThread.start();
    }

    private SurfaceView CreateGlView() {
        SurfaceView surfaceView = new SurfaceView(this.mContext);
        surfaceView.setId(this.mContext.getResources().getIdentifier("unitySurfaceView", "id", this.mContext.getPackageName()));
        if (IsWindowTranslucent()) {
            surfaceView.getHolder().setFormat(-3);
            surfaceView.setZOrderOnTop(true);
        } else {
            surfaceView.getHolder().setFormat(-1);
        }
        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() { // from class: com.unity3d.player.UnityPlayer.28
            @Override // android.view.SurfaceHolder.Callback
            public final void surfaceChanged(SurfaceHolder surfaceHolder, int i2, int i3, int i4) {
                UnityPlayer.this.updateGLDisplay(0, surfaceHolder.getSurface());
                UnityPlayer.this.sendSurfaceChangedEvent();
            }

            @Override // android.view.SurfaceHolder.Callback
            public final void surfaceCreated(SurfaceHolder surfaceHolder) {
                UnityPlayer.this.updateGLDisplay(0, surfaceHolder.getSurface());
                if (UnityPlayer.this.m_PersistentUnitySurface != null) {
                    UnityPlayer.this.m_PersistentUnitySurface.a(UnityPlayer.this);
                }
            }

            @Override // android.view.SurfaceHolder.Callback
            public final void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                if (UnityPlayer.this.m_PersistentUnitySurface != null) {
                    UnityPlayer.this.m_PersistentUnitySurface.a(UnityPlayer.this.mGlView);
                }
                UnityPlayer.this.updateGLDisplay(0, null);
            }
        });
        surfaceView.setFocusable(true);
        surfaceView.setFocusableInTouchMode(true);
        return surfaceView;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void DisableSplashAdsScreen() {
        if (this.m_splashAdsScreen != null) {
            final AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
            alphaAnimation.setDuration(500L);
            alphaAnimation.setFillAfter(true);
            alphaAnimation.setAnimationListener(new Animation.AnimationListener() { // from class: com.unity3d.player.UnityPlayer.26
                @Override // android.view.animation.Animation.AnimationListener
                public final void onAnimationEnd(Animation animation) {
                    UnityPlayer.this.runOnUiThread(new Runnable() { // from class: com.unity3d.player.UnityPlayer.26.1
                        @Override // java.lang.Runnable
                        public final void run() {
                            UnityPlayer unityPlayer = UnityPlayer.this;
                            unityPlayer.removeView(unityPlayer.m_splashAdsScreen);
                        }
                    });
                }

                @Override // android.view.animation.Animation.AnimationListener
                public final void onAnimationRepeat(Animation animation) {
                }

                @Override // android.view.animation.Animation.AnimationListener
                public final void onAnimationStart(Animation animation) {
                }
            });
            runOnUiThread(new Runnable() { // from class: com.unity3d.player.UnityPlayer.27
                @Override // java.lang.Runnable
                public final void run() {
                    UnityPlayer.this.m_splashAdsScreen.startAnimation(alphaAnimation);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void DisableStaticSplashScreen() {
        runOnUiThread(new Runnable() { // from class: com.unity3d.player.UnityPlayer.22
            @Override // java.lang.Runnable
            public final void run() {
                UnityPlayer unityPlayer = UnityPlayer.this;
                unityPlayer.removeView(unityPlayer.m_SplashScreen);
                UnityPlayer.this.m_SplashScreen = null;
            }
        });
    }

    private void EarlyEnableFullScreenIfEnabled() {
        View decorView;
        Activity activity = this.mActivity;
        if (activity == null || activity.getWindow() == null) {
            return;
        }
        if ((getLaunchFullscreen() || this.mActivity.getIntent().getBooleanExtra("android.intent.extra.VR_LAUNCH", false)) && (decorView = this.mActivity.getWindow().getDecorView()) != null) {
            decorView.setSystemUiVisibility(7);
        }
    }

    private String GetGlViewContentDescription(Context context) {
        return context.getResources().getString(context.getResources().getIdentifier("game_view_content_description", TypedValues.Custom.S_STRING, context.getPackageName()));
    }

    private boolean IsWindowTranslucent() {
        Activity activity = this.mActivity;
        if (activity == null) {
            return false;
        }
        TypedArray typedArrayObtainStyledAttributes = activity.getTheme().obtainStyledAttributes(new int[]{android.R.attr.windowIsTranslucent});
        boolean z2 = typedArrayObtainStyledAttributes.getBoolean(0, false);
        typedArrayObtainStyledAttributes.recycle();
        return z2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void ShowSplashAdsScreen() {
        runOnUiThread(new Runnable() { // from class: com.unity3d.player.UnityPlayer.25
            @Override // java.lang.Runnable
            public final void run() {
                if (UnityPlayer.this.m_splashAdsScreen != null) {
                    UnityPlayer unityPlayer = UnityPlayer.this;
                    unityPlayer.bringChildToFront(unityPlayer.m_splashAdsScreen);
                    UnityPlayer.this.m_splashAdsScreen.a();
                }
            }
        });
    }

    public static void UnitySendMessage(String str, String str2, String str3) {
        if (o.c()) {
            try {
                nativeUnitySendMessage(str, str2, str3.getBytes("UTF-8"));
                return;
            } catch (UnsupportedEncodingException unused) {
                return;
            }
        }
        com.unity3d.player.f.Log(5, "Native libraries not loaded - dropping message for " + str + StrPool.DOT + str2);
    }

    private static String bin2hex(byte[] bArr) {
        StringBuilder sb = new StringBuilder(bArr.length * 2);
        for (byte b3 : bArr) {
            sb.append(String.format("%02x", Integer.valueOf(b3 & 255)));
        }
        return sb.toString();
    }

    private void checkResumePlayer() {
        Activity activity = this.mActivity;
        if (this.mState.e(activity != null ? MultiWindowSupport.getAllowResizableWindow(activity) : false)) {
            this.mState.d(true);
            queueGLThreadEvent(new Runnable() { // from class: com.unity3d.player.UnityPlayer.6
                @Override // java.lang.Runnable
                public final void run() {
                    UnityPlayer.this.nativeResume();
                    UnityPlayer.this.runOnUiThread(new Runnable() { // from class: com.unity3d.player.UnityPlayer.6.1
                        @Override // java.lang.Runnable
                        public final void run() {
                            if (UnityPlayer.this.m_PersistentUnitySurface != null) {
                                UnityPlayer.this.m_PersistentUnitySurface.b(UnityPlayer.this);
                                UnityPlayer.this.m_PersistentUnitySurface.b();
                            }
                        }
                    });
                }
            });
            this.m_MainThread.b();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void finish() {
        Activity activity = this.mActivity;
        if (activity == null || activity.isFinishing()) {
            return;
        }
        this.mActivity.finish();
    }

    private k generateSplashView(Context context) throws JSONException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        StorageManager.init(context);
        Storage storage = StorageManager.getStorage(StorageManager.StorageType.PRIVATE);
        if (storage == null) {
            return null;
        }
        Object obj = storage.get("splash-show");
        if (obj != null) {
            try {
                j jVar = new j(new JSONObject(obj.toString()));
                if (jVar.a()) {
                    DeviceLog.info("splash show");
                    if (jVar.g() >= System.currentTimeMillis()) {
                        DeviceLog.info("splash show");
                        storage.delete("splash-show");
                        return new k(this.mContext, this, jVar);
                    }
                }
            } catch (JSONException unused) {
            }
        }
        Object obj2 = storage.get("splash-show-no-fill");
        if (obj2 != null) {
            try {
                DeviceLog.info("splash show no fill");
                j jVar2 = new j(new JSONObject(obj2.toString()));
                if (jVar2.a()) {
                    return new k(this.mContext, this, jVar2);
                }
            } catch (JSONException unused2) {
            }
        }
        DeviceLog.info("splash show nothing");
        return null;
    }

    private boolean getARCoreEnabled() {
        try {
            return getApplicationInfo().metaData.getBoolean(ARCORE_ENABLE_METADATA_NAME);
        } catch (Exception unused) {
            return false;
        }
    }

    private ApplicationInfo getApplicationInfo() {
        return this.mContext.getPackageManager().getApplicationInfo(this.mContext.getPackageName(), 128);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean getAutoReportFullyDrawnEnabled() {
        try {
            return getApplicationInfo().metaData.getBoolean(AUTO_REPORT_FULLY_DRAWN_ENABLE_METADATA_NAME);
        } catch (Exception unused) {
            return false;
        }
    }

    private boolean getLaunchFullscreen() {
        try {
            return getApplicationInfo().metaData.getBoolean(LAUNCH_FULLSCREEN);
        } catch (Exception unused) {
            return false;
        }
    }

    private int getNaturalOrientation(int i2) {
        int rotation = ((WindowManager) this.mContext.getSystemService("window")).getDefaultDisplay().getRotation();
        if ((rotation == 0 || rotation == 2) && i2 == 2) {
            return 0;
        }
        return ((rotation == 1 || rotation == 3) && i2 == 1) ? 0 : 1;
    }

    private String getProcessName() {
        int iMyPid = Process.myPid();
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = ((ActivityManager) this.mContext.getSystemService(PushConstants.INTENT_ACTIVITY_NAME)).getRunningAppProcesses();
        if (runningAppProcesses == null) {
            return null;
        }
        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
            if (runningAppProcessInfo.pid == iMyPid) {
                return runningAppProcessInfo.processName;
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String getQueryString(JSONObject jSONObject) throws UnsupportedEncodingException {
        if (jSONObject == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        Iterator<String> itKeys = jSONObject.keys();
        String str = "?";
        while (true) {
            sb.append(str);
            while (itKeys.hasNext()) {
                String next = itKeys.next();
                String strOptString = jSONObject.optString(next);
                if (strOptString != "") {
                    try {
                        strOptString = URLEncoder.encode(strOptString, "utf-8");
                    } catch (UnsupportedEncodingException e2) {
                        e2.printStackTrace();
                    }
                    sb.append(next);
                    sb.append("=");
                    sb.append(strOptString);
                    str = "&";
                }
            }
            sb.deleteCharAt(sb.length() - 1);
            return sb.toString();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean getSplashEnabled() {
        try {
            return getApplicationInfo().metaData.getBoolean(SPLASH_ENABLE_METADATA_NAME);
        } catch (Exception unused) {
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getSplashGameId() {
        try {
            return getApplicationInfo().metaData.getString(SPLASH_ADS_GAME_ID);
        } catch (Exception unused) {
            return null;
        }
    }

    private static String getUnityNativeLibraryPath(Context context) {
        return context.getApplicationInfo().nativeLibraryDir;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String hash_sha256(String str) throws NoSuchAlgorithmException {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(str.getBytes());
            return bin2hex(messageDigest.digest());
        } catch (NoSuchAlgorithmException e2) {
            e2.printStackTrace();
            return "";
        }
    }

    private void hideStatusBar() {
        Activity activity = this.mActivity;
        if (activity != null) {
            activity.getWindow().setFlags(1024, 1024);
        }
    }

    private final native void initJni(Context context);

    private static String loadNative(String str) {
        String str2 = str + "/libmain.so";
        try {
            try {
                try {
                    System.load(str2);
                } catch (UnsatisfiedLinkError unused) {
                    System.loadLibrary("main");
                }
                str2 = m_InstantGameName;
                if (str2 != null) {
                    str = m_AndroidFilesDir + "/UnityPlayers/" + m_InstantGameEngine;
                }
                if (NativeLoader.load(str)) {
                    o.a();
                    return "";
                }
                com.unity3d.player.f.Log(6, "NativeLoader.load failure, Unity libraries were not loaded.");
                return "NativeLoader.load failure, Unity libraries were not loaded.";
            } catch (UnsatisfiedLinkError e2) {
                e = e2;
                return logLoadLibMainError(str2, e.toString());
            }
        } catch (SecurityException e3) {
            e = e3;
            return logLoadLibMainError(str2, e.toString());
        }
    }

    private static String logLoadLibMainError(String str, String str2) {
        String str3 = "Failed to load 'libmain.so'\n\n" + str2;
        com.unity3d.player.f.Log(6, str3);
        return str3;
    }

    private final native void nativeApplicationUnload();

    private final native boolean nativeDone();

    /* JADX INFO: Access modifiers changed from: private */
    public final native void nativeFocusChanged(boolean z2);

    private final native boolean nativeInjectEvent(InputEvent inputEvent);

    /* JADX INFO: Access modifiers changed from: private */
    public final native boolean nativeIsAutorotationOn();

    /* JADX INFO: Access modifiers changed from: private */
    public final native void nativeLowMemory();

    /* JADX INFO: Access modifiers changed from: private */
    public final native void nativeMuteMasterAudio(boolean z2);

    /* JADX INFO: Access modifiers changed from: private */
    public final native void nativeOrientationChanged(int i2, int i3);

    /* JADX INFO: Access modifiers changed from: private */
    public final native boolean nativePause();

    /* JADX INFO: Access modifiers changed from: private */
    public final native void nativeRecreateGfxState(int i2, Surface surface);

    /* JADX INFO: Access modifiers changed from: private */
    public final native boolean nativeRender();

    /* JADX INFO: Access modifiers changed from: private */
    public final native void nativeReportKeyboardConfigChanged();

    private final native void nativeRestartActivityIndicator();

    /* JADX INFO: Access modifiers changed from: private */
    public final native void nativeResume();

    /* JADX INFO: Access modifiers changed from: private */
    public final native void nativeSendSurfaceChangedEvent();

    /* JADX INFO: Access modifiers changed from: private */
    public final native void nativeSetInputArea(int i2, int i3, int i4, int i5);

    /* JADX INFO: Access modifiers changed from: private */
    public final native void nativeSetInputSelection(int i2, int i3);

    /* JADX INFO: Access modifiers changed from: private */
    public final native void nativeSetInputString(String str);

    /* JADX INFO: Access modifiers changed from: private */
    public final native void nativeSetKeyboardIsVisible(boolean z2);

    /* JADX INFO: Access modifiers changed from: private */
    public final native void nativeSetLaunchURL(String str);

    /* JADX INFO: Access modifiers changed from: private */
    public final native void nativeSoftInputCanceled();

    /* JADX INFO: Access modifiers changed from: private */
    public final native void nativeSoftInputClosed();

    /* JADX INFO: Access modifiers changed from: private */
    public final native void nativeSoftInputLostFocus();

    private static native void nativeUnitySendMessage(String str, String str2, byte[] bArr);

    private void pauseUnity() throws InterruptedException {
        reportSoftInputStr(null, 1, true);
        if (this.mState.f()) {
            if (o.c()) {
                final Semaphore semaphore = new Semaphore(0);
                this.m_MainThread.a(isFinishing() ? new Runnable() { // from class: com.unity3d.player.UnityPlayer.3
                    @Override // java.lang.Runnable
                    public final void run() {
                        UnityPlayer.this.shutdown();
                        semaphore.release();
                    }
                } : new Runnable() { // from class: com.unity3d.player.UnityPlayer.4
                    @Override // java.lang.Runnable
                    public final void run() {
                        if (!UnityPlayer.this.nativePause()) {
                            semaphore.release();
                            return;
                        }
                        UnityPlayer.this.mQuitting = true;
                        UnityPlayer.this.shutdown();
                        semaphore.release(2);
                    }
                });
                try {
                    if (!semaphore.tryAcquire(4L, TimeUnit.SECONDS)) {
                        com.unity3d.player.f.Log(5, "Timeout while trying to pause the Unity Engine.");
                    }
                } catch (InterruptedException unused) {
                    com.unity3d.player.f.Log(5, "UI thread got interrupted while trying to pause the Unity Engine.");
                }
                if (semaphore.drainPermits() > 0) {
                    destroy();
                }
            }
            this.mState.d(false);
            this.mState.b(true);
            if (this.m_AddPhoneCallListener) {
                this.m_TelephonyManager.listen(this.m_PhoneCallListener, 0);
            }
        }
    }

    private static void preloadJavaPlugins() throws ClassNotFoundException {
        try {
            Class.forName("com.unity3d.JavaPluginPreloader");
        } catch (ClassNotFoundException unused) {
        } catch (LinkageError e2) {
            com.unity3d.player.f.Log(6, "Java class preloading failed: " + e2.getMessage());
        }
    }

    private void queueGLThreadEvent(h hVar) {
        if (isFinishing()) {
            return;
        }
        queueGLThreadEvent((Runnable) hVar);
    }

    private void registerLaunch() {
        Activity activity = this.mActivity;
        if (activity != null) {
            SharedPreferences sharedPreferences = activity.getSharedPreferences("game_detail", 0);
            String string = sharedPreferences.getString("game_id", "");
            Boolean boolValueOf = Boolean.valueOf(sharedPreferences.getBoolean("show_ads", false));
            Boolean boolValueOf2 = Boolean.valueOf(sharedPreferences.getBoolean("blocked", false));
            if (boolValueOf2.booleanValue() || !boolValueOf.booleanValue() || string == "") {
                this.shouldShowLaunchScreenAds = false;
                if (boolValueOf2.booleanValue()) {
                    showBlockDialog();
                }
            } else {
                this.shouldShowLaunchScreenAds = true;
                UnityAds.initialize(this.mActivity, string, null);
            }
        } else {
            this.shouldShowLaunchScreenAds = false;
        }
        new e().execute(getContext().getPackageName());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendSurfaceChangedEvent() {
        if (o.c() && this.mState.e()) {
            this.m_MainThread.d(new Runnable() { // from class: com.unity3d.player.UnityPlayer.29
                @Override // java.lang.Runnable
                public final void run() {
                    UnityPlayer.this.nativeSendSurfaceChangedEvent();
                }
            });
        }
    }

    private void showBlockDialog() {
        this.ad = new AlertDialog.Builder(this.mContext).setTitle("Sorry").setPositiveButton(PLVWebview.MESSAGE_OK, new DialogInterface.OnClickListener() { // from class: com.unity3d.player.UnityPlayer.20
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i2) {
                UnityPlayer.this.mHanlder.sendEmptyMessage(1);
            }
        }).setMessage("The app is using unauthorized engine, please contact the publisher!").setCancelable(false).create();
        this.mHanlder = new Handler() { // from class: com.unity3d.player.UnityPlayer.21
            @Override // android.os.Handler
            public final void handleMessage(Message message) {
                AlertDialog alertDialog;
                int i2 = message.what;
                if (i2 == 0) {
                    AlertDialog alertDialog2 = UnityPlayer.this.ad;
                    if (alertDialog2 != null && !alertDialog2.isShowing()) {
                        UnityPlayer.this.ad.show();
                    }
                } else if (i2 == 1 && (alertDialog = UnityPlayer.this.ad) != null && alertDialog.isShowing()) {
                    UnityPlayer.this.ad.hide();
                }
                super.handleMessage(message);
            }
        };
        TimerTask timerTask = new TimerTask() { // from class: com.unity3d.player.UnityPlayer.23
            @Override // java.util.TimerTask, java.lang.Runnable
            public final void run() {
                UnityPlayer.this.mHanlder.sendEmptyMessage(0);
            }
        };
        this.timerTask = timerTask;
        this.timer.schedule(timerTask, 0L, 600000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void shutdown() {
        this.mProcessKillRequested = nativeDone();
        this.mState.c(false);
    }

    private void swapViews(View view, View view2) {
        boolean z2;
        if (this.mState.d()) {
            z2 = false;
        } else {
            pause();
            z2 = true;
        }
        if (view != null) {
            ViewParent parent = view.getParent();
            if (!(parent instanceof UnityPlayer) || ((UnityPlayer) parent) != this) {
                if (parent instanceof ViewGroup) {
                    ((ViewGroup) parent).removeView(view);
                }
                addView(view);
                bringChildToFront(view);
                view.setVisibility(0);
            }
        }
        if (view2 != null && view2.getParent() == this) {
            view2.setVisibility(8);
            removeView(view2);
        }
        if (z2) {
            resume();
        }
    }

    private static void unloadNative() {
        if (o.c()) {
            if (!NativeLoader.unload()) {
                throw new UnsatisfiedLinkError("Unable to unload libraries from libmain.so");
            }
            o.b();
        }
    }

    private boolean updateDisplayInternal(final int i2, final Surface surface) {
        if (!o.c() || !this.mState.e()) {
            return false;
        }
        final Semaphore semaphore = new Semaphore(0);
        Runnable runnable = new Runnable() { // from class: com.unity3d.player.UnityPlayer.30
            @Override // java.lang.Runnable
            public final void run() {
                UnityPlayer.this.nativeRecreateGfxState(i2, surface);
                semaphore.release();
            }
        };
        if (i2 == 0) {
            g gVar = this.m_MainThread;
            if (surface == null) {
                gVar.b(runnable);
            } else {
                gVar.c(runnable);
            }
        } else {
            runnable.run();
        }
        if (surface != null || i2 != 0) {
            return true;
        }
        try {
            if (semaphore.tryAcquire(4L, TimeUnit.SECONDS)) {
                return true;
            }
            com.unity3d.player.f.Log(5, "Timeout while trying detaching primary window.");
            return true;
        } catch (InterruptedException unused) {
            com.unity3d.player.f.Log(5, "UI thread got interrupted while trying to detach the primary window from the Unity Engine.");
            return true;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateGLDisplay(int i2, Surface surface) {
        if (this.mMainDisplayOverride) {
            return;
        }
        updateDisplayInternal(i2, surface);
    }

    public void NotifySplashAdsFinished() {
        this.finishLaunchScreenAds = true;
        this.m_MainThread.f();
    }

    public void addPhoneCallListener() {
        this.m_AddPhoneCallListener = true;
        this.m_TelephonyManager.listen(this.m_PhoneCallListener, 32);
    }

    public boolean addViewToPlayer(View view, boolean z2) {
        swapViews(view, z2 ? this.mGlView : null);
        boolean z3 = true;
        boolean z4 = view.getParent() == this;
        boolean z5 = z2 && this.mGlView.getParent() == null;
        boolean z6 = this.mGlView.getParent() == this;
        if (!z4 || (!z5 && !z6)) {
            z3 = false;
        }
        if (!z3) {
            if (!z4) {
                com.unity3d.player.f.Log(6, "addViewToPlayer: Failure adding view to hierarchy");
            }
            if (!z5 && !z6) {
                com.unity3d.player.f.Log(6, "addViewToPlayer: Failure removing old view from hierarchy");
            }
        }
        return z3;
    }

    public void configurationChanged(Configuration configuration) {
        SurfaceView surfaceView = this.mGlView;
        if (surfaceView instanceof SurfaceView) {
            surfaceView.getHolder().setSizeFromLayout();
        }
        q qVar = this.mVideoPlayerProxy;
        if (qVar != null) {
            qVar.c();
        }
    }

    public void destroy() throws InterruptedException {
        com.unity3d.player.h hVar = this.m_PersistentUnitySurface;
        if (hVar != null) {
            hVar.a();
            this.m_PersistentUnitySurface = null;
        }
        Camera2Wrapper camera2Wrapper = this.m_Camera2Wrapper;
        if (camera2Wrapper != null) {
            camera2Wrapper.a();
            this.m_Camera2Wrapper = null;
        }
        HFPStatus hFPStatus = this.m_HFPStatus;
        if (hFPStatus != null) {
            hFPStatus.a();
            this.m_HFPStatus = null;
        }
        NetworkConnectivity networkConnectivity = this.m_NetworkConnectivity;
        if (networkConnectivity != null) {
            networkConnectivity.b();
            this.m_NetworkConnectivity = null;
        }
        this.mQuitting = true;
        if (!this.mState.d()) {
            pause();
        }
        this.m_MainThread.a();
        try {
            this.m_MainThread.join(4000L);
        } catch (InterruptedException unused) {
            this.m_MainThread.interrupt();
        }
        BroadcastReceiver broadcastReceiver = this.mKillingIsMyBusiness;
        if (broadcastReceiver != null) {
            this.mContext.unregisterReceiver(broadcastReceiver);
        }
        this.mKillingIsMyBusiness = null;
        if (o.c()) {
            removeAllViews();
        }
        if (this.mProcessKillRequested) {
            this.m_UnityPlayerLifecycleEvents.onUnityPlayerQuitted();
            kill();
        }
        unloadNative();
    }

    public void disableLogger() {
        com.unity3d.player.f.f24093a = true;
    }

    public boolean displayChanged(int i2, Surface surface) {
        if (i2 == 0) {
            this.mMainDisplayOverride = surface != null;
            runOnUiThread(new Runnable() { // from class: com.unity3d.player.UnityPlayer.2
                @Override // java.lang.Runnable
                public final void run() {
                    if (UnityPlayer.this.mMainDisplayOverride) {
                        UnityPlayer unityPlayer = UnityPlayer.this;
                        unityPlayer.removeView(unityPlayer.mGlView);
                    } else {
                        UnityPlayer unityPlayer2 = UnityPlayer.this;
                        unityPlayer2.addView(unityPlayer2.mGlView);
                    }
                }
            });
        }
        return updateDisplayInternal(i2, surface);
    }

    public void executeGLThreadJobs() {
        while (true) {
            Runnable runnable = (Runnable) this.m_Events.poll();
            if (runnable == null) {
                return;
            } else {
                runnable.run();
            }
        }
    }

    public String getBuilderUserId() {
        try {
            return getApplicationInfo().metaData.getString(UNITY_BUILDER_ID);
        } catch (Exception unused) {
            return null;
        }
    }

    public String getClipboardText() {
        ClipData primaryClip = this.m_ClipboardManager.getPrimaryClip();
        return primaryClip != null ? primaryClip.getItemAt(0).coerceToText(this.mContext).toString() : "";
    }

    public String getDeviceId() {
        Activity activity = this.mActivity;
        if (activity == null) {
            return "";
        }
        SharedPreferences sharedPreferences = activity.getSharedPreferences("device_detail", 0);
        String string = sharedPreferences.getString("device_id", null);
        if (string != null) {
            return string;
        }
        String uniqueEventId = Device.getUniqueEventId();
        SharedPreferences.Editor editorEdit = sharedPreferences.edit();
        editorEdit.putString("device_id", uniqueEventId);
        editorEdit.commit();
        return uniqueEventId;
    }

    public String getKeyboardLayout() {
        i iVar = this.mSoftInputDialog;
        if (iVar == null) {
            return null;
        }
        return iVar.a();
    }

    public String getLaunchURL() {
        Uri uri = this.m_launchUri;
        if (uri != null) {
            return uri.toString();
        }
        return null;
    }

    public int getNetworkConnectivity() {
        if (!PlatformSupport.NOUGAT_SUPPORT) {
            return 0;
        }
        if (this.m_NetworkConnectivity == null) {
            this.m_NetworkConnectivity = new NetworkConnectivity(this.mContext);
        }
        return this.m_NetworkConnectivity.a();
    }

    public String getNetworkProxySettings(String str) {
        String str2;
        String str3;
        if (!str.startsWith(URIUtil.HTTP_COLON)) {
            if (str.startsWith(URIUtil.HTTPS_COLON)) {
                str2 = "https.proxyHost";
                str3 = "https.proxyPort";
            }
            return null;
        }
        str2 = "http.proxyHost";
        str3 = "http.proxyPort";
        String property = System.getProperties().getProperty(str2);
        if (property != null && !"".equals(property)) {
            StringBuilder sb = new StringBuilder(property);
            String property2 = System.getProperties().getProperty(str3);
            if (property2 != null && !"".equals(property2)) {
                sb.append(":");
                sb.append(property2);
            }
            String property3 = System.getProperties().getProperty("http.nonProxyHosts");
            if (property3 != null && !"".equals(property3)) {
                sb.append('\n');
                sb.append(property3);
            }
            return sb.toString();
        }
        return null;
    }

    public Bundle getSettings() {
        return Bundle.EMPTY;
    }

    public Boolean getShowSplashSlogan() {
        try {
            return Boolean.valueOf(getApplicationInfo().metaData.getBoolean(SPLASH_ADS_SLOGAN));
        } catch (Exception unused) {
            return Boolean.FALSE;
        }
    }

    public int getShowSplashSloganHeight() {
        try {
            return getApplicationInfo().metaData.getInt(SPLASH_ADS_SLOGAN_HEIGHT, 250);
        } catch (Exception unused) {
            return 150;
        }
    }

    public int getSplashMode() {
        try {
            return getApplicationInfo().metaData.getInt(SPLASH_MODE_METADATA_NAME);
        } catch (Exception unused) {
            return 0;
        }
    }

    public int getUaaLLaunchProcessType() {
        String processName = getProcessName();
        return (processName == null || processName.equals(this.mContext.getPackageName())) ? 0 : 1;
    }

    public View getView() {
        return this;
    }

    public void hideSoftInput() {
        postOnUiThread(new Runnable() { // from class: com.unity3d.player.UnityPlayer.8
            @Override // java.lang.Runnable
            public final void run() {
                UnityPlayer.this.reportSoftInputArea(new Rect());
                UnityPlayer.this.reportSoftInputIsVisible(false);
                i iVar = UnityPlayer.this.mSoftInputDialog;
                if (iVar != null) {
                    iVar.dismiss();
                    UnityPlayer unityPlayer = UnityPlayer.this;
                    unityPlayer.mSoftInputDialog = null;
                    unityPlayer.nativeReportKeyboardConfigChanged();
                }
            }
        });
    }

    public void init(int i2, boolean z2) {
    }

    public boolean initializeGoogleAr() {
        if (this.m_ARCoreApi != null || this.mActivity == null || !getARCoreEnabled()) {
            return false;
        }
        GoogleARCoreApi googleARCoreApi = new GoogleARCoreApi();
        this.m_ARCoreApi = googleARCoreApi;
        googleARCoreApi.initializeARCore(this.mActivity);
        if (this.mState.d()) {
            return false;
        }
        this.m_ARCoreApi.resumeARCore();
        return false;
    }

    public boolean injectEvent(InputEvent inputEvent) {
        if (o.c()) {
            return nativeInjectEvent(inputEvent);
        }
        return false;
    }

    public boolean isFinishing() {
        if (this.mQuitting) {
            return true;
        }
        Activity activity = this.mActivity;
        if (activity != null) {
            this.mQuitting = activity.isFinishing();
        }
        return this.mQuitting;
    }

    public boolean isLaunchScreenAdsFinished() {
        return this.finishLaunchScreenAds || UnityAds.isSkipLaunchScreenAds();
    }

    public boolean isShouldShowLaunchScreenAds() {
        return this.shouldShowLaunchScreenAds;
    }

    public boolean isUaaLUseCase() {
        String callingPackage;
        Activity activity = this.mActivity;
        return (activity == null || (callingPackage = activity.getCallingPackage()) == null || !callingPackage.equals(this.mContext.getPackageName())) ? false : true;
    }

    public void kill() {
        Process.killProcess(Process.myPid());
    }

    public boolean loadLibrary(String str) {
        try {
            System.loadLibrary(str);
            return true;
        } catch (Exception | UnsatisfiedLinkError unused) {
            return false;
        }
    }

    public void lowMemory() {
        if (o.c()) {
            queueGLThreadEvent(new Runnable() { // from class: com.unity3d.player.UnityPlayer.5
                @Override // java.lang.Runnable
                public final void run() {
                    UnityPlayer.this.nativeLowMemory();
                }
            });
        }
    }

    public void newIntent(Intent intent) {
        this.m_launchUri = intent.getData();
        this.m_MainThread.e();
    }

    @Override // android.view.View
    public boolean onGenericMotionEvent(MotionEvent motionEvent) {
        return injectEvent(motionEvent);
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i2, KeyEvent keyEvent) {
        return injectEvent(keyEvent);
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyLongPress(int i2, KeyEvent keyEvent) {
        return injectEvent(keyEvent);
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyMultiple(int i2, int i3, KeyEvent keyEvent) {
        return injectEvent(keyEvent);
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyUp(int i2, KeyEvent keyEvent) {
        return injectEvent(keyEvent);
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        return injectEvent(motionEvent);
    }

    @Override // com.unity3d.player.IUnityPlayerLifecycleEvents
    public void onUnityPlayerQuitted() {
    }

    @Override // com.unity3d.player.IUnityPlayerLifecycleEvents
    public void onUnityPlayerUnloaded() {
    }

    public void pause() {
        GoogleARCoreApi googleARCoreApi = this.m_ARCoreApi;
        if (googleARCoreApi != null) {
            googleARCoreApi.pauseARCore();
        }
        q qVar = this.mVideoPlayerProxy;
        if (qVar != null) {
            qVar.a();
        }
        AudioVolumeHandler audioVolumeHandler = this.m_AudioVolumeHandler;
        if (audioVolumeHandler != null) {
            audioVolumeHandler.a();
            this.m_AudioVolumeHandler = null;
        }
        OrientationLockListener orientationLockListener = this.m_OrientationLockListener;
        if (orientationLockListener != null) {
            orientationLockListener.a();
            this.m_OrientationLockListener = null;
        }
        k kVar = this.m_splashAdsScreen;
        if (kVar != null) {
            kVar.b();
        }
        pauseUnity();
    }

    public void pauseJavaAndCallUnloadCallback() {
        runOnUiThread(new Runnable() { // from class: com.unity3d.player.UnityPlayer.19
            @Override // java.lang.Runnable
            public final void run() {
                UnityPlayer.this.pause();
                UnityPlayer.this.windowFocusChanged(false);
                UnityPlayer.this.m_UnityPlayerLifecycleEvents.onUnityPlayerUnloaded();
            }
        });
    }

    public void postOnUiThread(Runnable runnable) {
        new Handler(Looper.getMainLooper()).post(runnable);
    }

    public void queueGLThreadEvent(Runnable runnable) {
        if (o.c()) {
            if (Thread.currentThread() == this.m_MainThread) {
                runnable.run();
            } else {
                this.m_Events.add(runnable);
            }
        }
    }

    public void quit() throws InterruptedException {
        destroy();
    }

    public void removeViewFromPlayer(View view) {
        swapViews(this.mGlView, view);
        boolean z2 = view.getParent() == null;
        boolean z3 = this.mGlView.getParent() == this;
        if (z2 && z3) {
            return;
        }
        if (!z2) {
            com.unity3d.player.f.Log(6, "removeViewFromPlayer: Failure removing view from hierarchy");
        }
        if (z3) {
            return;
        }
        com.unity3d.player.f.Log(6, "removeVireFromPlayer: Failure agging old view to hierarchy");
    }

    public void reportError(String str, String str2) {
        com.unity3d.player.f.Log(6, str + ": " + str2);
    }

    public void reportSoftInputArea(final Rect rect) {
        queueGLThreadEvent(new h() { // from class: com.unity3d.player.UnityPlayer.15
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(UnityPlayer.this, (byte) 0);
            }

            @Override // com.unity3d.player.UnityPlayer.h
            public final void a() {
                UnityPlayer unityPlayer = UnityPlayer.this;
                Rect rect2 = rect;
                unityPlayer.nativeSetInputArea(rect2.left, rect2.top, rect2.right, rect2.bottom);
            }
        });
    }

    public void reportSoftInputIsVisible(final boolean z2) {
        queueGLThreadEvent(new h() { // from class: com.unity3d.player.UnityPlayer.16
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(UnityPlayer.this, (byte) 0);
            }

            @Override // com.unity3d.player.UnityPlayer.h
            public final void a() {
                UnityPlayer.this.nativeSetKeyboardIsVisible(z2);
            }
        });
    }

    public void reportSoftInputSelection(final int i2, final int i3) {
        queueGLThreadEvent(new h() { // from class: com.unity3d.player.UnityPlayer.14
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(UnityPlayer.this, (byte) 0);
            }

            @Override // com.unity3d.player.UnityPlayer.h
            public final void a() {
                UnityPlayer.this.nativeSetInputSelection(i2, i3);
            }
        });
    }

    public void reportSoftInputStr(final String str, final int i2, final boolean z2) {
        if (i2 == 1) {
            hideSoftInput();
        }
        queueGLThreadEvent(new h() { // from class: com.unity3d.player.UnityPlayer.13
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(UnityPlayer.this, (byte) 0);
            }

            @Override // com.unity3d.player.UnityPlayer.h
            public final void a() {
                if (z2) {
                    UnityPlayer.this.nativeSoftInputCanceled();
                } else {
                    String str2 = str;
                    if (str2 != null) {
                        UnityPlayer.this.nativeSetInputString(str2);
                    }
                }
                if (i2 == 1) {
                    UnityPlayer.this.nativeSoftInputClosed();
                }
            }
        });
    }

    public void requestUserAuthorization(String str) {
        if (str == null || str.isEmpty() || this.mActivity == null) {
            return;
        }
        UnityPermissions.ModalWaitForPermissionResponse modalWaitForPermissionResponse = new UnityPermissions.ModalWaitForPermissionResponse();
        UnityPermissions.requestUserPermissions(this.mActivity, new String[]{str}, modalWaitForPermissionResponse);
        modalWaitForPermissionResponse.waitForResponse();
    }

    public void resume() {
        GoogleARCoreApi googleARCoreApi = this.m_ARCoreApi;
        if (googleARCoreApi != null) {
            googleARCoreApi.resumeARCore();
        }
        this.mState.b(false);
        q qVar = this.mVideoPlayerProxy;
        if (qVar != null) {
            qVar.b();
        }
        k kVar = this.m_splashAdsScreen;
        if (kVar != null) {
            kVar.c();
        }
        checkResumePlayer();
        if (o.c()) {
            nativeRestartActivityIndicator();
        }
        if (this.m_AudioVolumeHandler == null) {
            this.m_AudioVolumeHandler = new AudioVolumeHandler(this.mContext);
        }
        if (this.m_OrientationLockListener == null && o.c()) {
            this.m_OrientationLockListener = new OrientationLockListener(this.mContext);
        }
    }

    public void runOnAnonymousThread(Runnable runnable) {
        new Thread(runnable).start();
    }

    public void runOnUiThread(Runnable runnable) {
        Activity activity = this.mActivity;
        if (activity != null) {
            activity.runOnUiThread(runnable);
        } else if (Thread.currentThread() != Looper.getMainLooper().getThread()) {
            this.mHandler.post(runnable);
        } else {
            runnable.run();
        }
    }

    public void setCharacterLimit(final int i2) {
        runOnUiThread(new Runnable() { // from class: com.unity3d.player.UnityPlayer.10
            @Override // java.lang.Runnable
            public final void run() {
                i iVar = UnityPlayer.this.mSoftInputDialog;
                if (iVar != null) {
                    iVar.a(i2);
                }
            }
        });
    }

    public void setClipboardText(String str) {
        this.m_ClipboardManager.setPrimaryClip(ClipData.newPlainText("Text", str));
    }

    public void setHideInputField(final boolean z2) {
        runOnUiThread(new Runnable() { // from class: com.unity3d.player.UnityPlayer.11
            @Override // java.lang.Runnable
            public final void run() {
                i iVar = UnityPlayer.this.mSoftInputDialog;
                if (iVar != null) {
                    iVar.a(z2);
                }
            }
        });
    }

    public void setSelection(final int i2, final int i3) {
        runOnUiThread(new Runnable() { // from class: com.unity3d.player.UnityPlayer.12
            @Override // java.lang.Runnable
            public final void run() {
                i iVar = UnityPlayer.this.mSoftInputDialog;
                if (iVar != null) {
                    iVar.a(i2, i3);
                }
            }
        });
    }

    public void setSoftInputStr(final String str) {
        runOnUiThread(new Runnable() { // from class: com.unity3d.player.UnityPlayer.9
            @Override // java.lang.Runnable
            public final void run() {
                String str2;
                i iVar = UnityPlayer.this.mSoftInputDialog;
                if (iVar == null || (str2 = str) == null) {
                    return;
                }
                iVar.a(str2);
            }
        });
    }

    public void showSoftInput(final String str, final int i2, final boolean z2, final boolean z3, final boolean z4, final boolean z5, final String str2, final int i3, final boolean z6, final boolean z7) {
        postOnUiThread(new Runnable() { // from class: com.unity3d.player.UnityPlayer.7
            @Override // java.lang.Runnable
            public final void run() {
                UnityPlayer.this.mSoftInputDialog = new i(UnityPlayer.this.mContext, this, str, i2, z2, z3, z4, str2, i3, z6, z7);
                UnityPlayer.this.mSoftInputDialog.setOnCancelListener(new DialogInterface.OnCancelListener() { // from class: com.unity3d.player.UnityPlayer.7.1
                    @Override // android.content.DialogInterface.OnCancelListener
                    public final void onCancel(DialogInterface dialogInterface) {
                        UnityPlayer.this.nativeSoftInputLostFocus();
                        UnityPlayer.this.reportSoftInputStr(null, 1, false);
                    }
                });
                UnityPlayer.this.mSoftInputDialog.show();
                UnityPlayer.this.nativeReportKeyboardConfigChanged();
            }
        });
    }

    public boolean showVideoPlayer(String str, int i2, int i3, int i4, boolean z2, int i5, int i6) throws InterruptedException {
        if (this.mVideoPlayerProxy == null) {
            this.mVideoPlayerProxy = new q(this);
        }
        boolean zA = this.mVideoPlayerProxy.a(this.mContext, str, i2, i3, i4, z2, i5, i6, new q.a() { // from class: com.unity3d.player.UnityPlayer.17
            @Override // com.unity3d.player.q.a
            public final void a() {
                UnityPlayer.this.mVideoPlayerProxy = null;
            }
        });
        if (zA) {
            runOnUiThread(new Runnable() { // from class: com.unity3d.player.UnityPlayer.18
                @Override // java.lang.Runnable
                public final void run() {
                    if (!UnityPlayer.this.nativeIsAutorotationOn() || UnityPlayer.this.mActivity == null) {
                        return;
                    }
                    ((Activity) UnityPlayer.this.mContext).setRequestedOrientation(UnityPlayer.this.mInitialScreenOrientation);
                }
            });
        }
        return zA;
    }

    public boolean skipPermissionsDialog() {
        Activity activity = this.mActivity;
        if (activity != null) {
            return UnityPermissions.skipPermissionsDialog(activity);
        }
        return false;
    }

    public boolean startOrientationListener(int i2) {
        String str;
        if (this.mOrientationListener != null) {
            str = "Orientation Listener already started.";
        } else {
            OrientationEventListener orientationEventListener = new OrientationEventListener(this.mContext, i2) { // from class: com.unity3d.player.UnityPlayer.24
                @Override // android.view.OrientationEventListener
                public final void onOrientationChanged(int i3) {
                    UnityPlayer unityPlayer = UnityPlayer.this;
                    unityPlayer.m_MainThread.a(unityPlayer.mNaturalOrientation, i3);
                }
            };
            this.mOrientationListener = orientationEventListener;
            if (orientationEventListener.canDetectOrientation()) {
                this.mOrientationListener.enable();
                return true;
            }
            str = "Orientation Listener cannot detect orientation.";
        }
        com.unity3d.player.f.Log(5, str);
        return false;
    }

    public boolean stopOrientationListener() {
        OrientationEventListener orientationEventListener = this.mOrientationListener;
        if (orientationEventListener == null) {
            com.unity3d.player.f.Log(5, "Orientation Listener was not started.");
            return false;
        }
        orientationEventListener.disable();
        this.mOrientationListener = null;
        return true;
    }

    public void toggleGyroscopeSensor(boolean z2) {
        SensorManager sensorManager = (SensorManager) this.mContext.getSystemService(am.ac);
        Sensor defaultSensor = sensorManager.getDefaultSensor(11);
        if (z2) {
            sensorManager.registerListener(this.m_FakeListener, defaultSensor, 1);
        } else {
            sensorManager.unregisterListener(this.m_FakeListener);
        }
    }

    public void unload() {
        nativeApplicationUnload();
    }

    public void windowFocusChanged(boolean z2) {
        this.mState.a(z2);
        if (this.mState.e()) {
            i iVar = this.mSoftInputDialog;
            if (iVar == null || iVar.f24111a) {
                if (z2) {
                    this.m_MainThread.c();
                } else {
                    this.m_MainThread.d();
                }
                checkResumePlayer();
            }
        }
    }
}
