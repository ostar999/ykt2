package com.huawei.hms.api;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.text.TextUtils;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlayer;
import com.huawei.hms.api.Api;
import com.huawei.hms.api.HuaweiApiClient;
import com.huawei.hms.common.api.ConnectionPostProcessor;
import com.huawei.hms.common.internal.AutoLifecycleFragment;
import com.huawei.hms.core.aidl.CodecLookup;
import com.huawei.hms.core.aidl.DataBuffer;
import com.huawei.hms.core.aidl.IAIDLCallback;
import com.huawei.hms.core.aidl.IAIDLInvoke;
import com.huawei.hms.core.aidl.IMessageEntity;
import com.huawei.hms.core.aidl.MessageCodec;
import com.huawei.hms.core.aidl.RequestHeader;
import com.huawei.hms.core.aidl.ResponseHeader;
import com.huawei.hms.support.api.PendingResultImpl;
import com.huawei.hms.support.api.ResolveResult;
import com.huawei.hms.support.api.client.ApiClient;
import com.huawei.hms.support.api.client.BundleResult;
import com.huawei.hms.support.api.client.InnerApiClient;
import com.huawei.hms.support.api.client.PendingResult;
import com.huawei.hms.support.api.client.ResultCallback;
import com.huawei.hms.support.api.client.Status;
import com.huawei.hms.support.api.client.SubAppInfo;
import com.huawei.hms.support.api.core.ConnectService;
import com.huawei.hms.support.api.entity.auth.PermissionInfo;
import com.huawei.hms.support.api.entity.auth.Scope;
import com.huawei.hms.support.api.entity.core.CheckConnectInfo;
import com.huawei.hms.support.api.entity.core.CommonCode;
import com.huawei.hms.support.api.entity.core.ConnectInfo;
import com.huawei.hms.support.api.entity.core.ConnectResp;
import com.huawei.hms.support.api.entity.core.DisconnectInfo;
import com.huawei.hms.support.api.entity.core.DisconnectResp;
import com.huawei.hms.support.api.entity.core.JosGetNoticeResp;
import com.huawei.hms.support.log.HMSLog;
import com.huawei.hms.utils.Checker;
import com.huawei.hms.utils.HMSPackageManager;
import com.huawei.hms.utils.PackageManagerHelper;
import com.huawei.hms.utils.UIUtil;
import com.huawei.hms.utils.Util;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/* loaded from: classes4.dex */
public class HuaweiApiClientImpl extends HuaweiApiClient implements InnerApiClient, ServiceConnection {
    public static final String DEFAULT_ACCOUNT = "<<default account>>";
    public static final int SIGN_IN_MODE_OPTIONAL = 2;
    public static final int SIGN_IN_MODE_REQUIRED = 1;

    /* renamed from: b, reason: collision with root package name */
    private final Context f7485b;

    /* renamed from: c, reason: collision with root package name */
    private final String f7486c;

    /* renamed from: d, reason: collision with root package name */
    private String f7487d;

    /* renamed from: e, reason: collision with root package name */
    private String f7488e;

    /* renamed from: f, reason: collision with root package name */
    private volatile IAIDLInvoke f7489f;

    /* renamed from: g, reason: collision with root package name */
    private String f7490g;

    /* renamed from: h, reason: collision with root package name */
    private WeakReference<Activity> f7491h;

    /* renamed from: i, reason: collision with root package name */
    private WeakReference<Activity> f7492i;

    /* renamed from: l, reason: collision with root package name */
    private List<Scope> f7495l;

    /* renamed from: m, reason: collision with root package name */
    private List<PermissionInfo> f7496m;

    /* renamed from: n, reason: collision with root package name */
    private Map<Api<?>, Api.ApiOptions> f7497n;

    /* renamed from: o, reason: collision with root package name */
    private SubAppInfo f7498o;

    /* renamed from: s, reason: collision with root package name */
    private final ReentrantLock f7502s;

    /* renamed from: t, reason: collision with root package name */
    private final Condition f7503t;

    /* renamed from: u, reason: collision with root package name */
    private ConnectionResult f7504u;

    /* renamed from: v, reason: collision with root package name */
    private HuaweiApiClient.ConnectionCallbacks f7505v;

    /* renamed from: w, reason: collision with root package name */
    private HuaweiApiClient.OnConnectionFailedListener f7506w;

    /* renamed from: x, reason: collision with root package name */
    private Handler f7507x;

    /* renamed from: y, reason: collision with root package name */
    private Handler f7508y;

    /* renamed from: z, reason: collision with root package name */
    private static final Object f7483z = new Object();
    private static final Object A = new Object();

    /* renamed from: a, reason: collision with root package name */
    private int f7484a = -1;

    /* renamed from: j, reason: collision with root package name */
    private boolean f7493j = false;

    /* renamed from: k, reason: collision with root package name */
    private AtomicInteger f7494k = new AtomicInteger(1);

    /* renamed from: p, reason: collision with root package name */
    private long f7499p = 0;

    /* renamed from: q, reason: collision with root package name */
    private int f7500q = 0;

    /* renamed from: r, reason: collision with root package name */
    private final Object f7501r = new Object();

    public class a implements Handler.Callback {
        public a() {
        }

        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message message) {
            if (message == null || message.what != 2) {
                return false;
            }
            HMSLog.e("HuaweiApiClientImpl", "In connect, bind core service time out");
            if (HuaweiApiClientImpl.this.f7494k.get() == 5) {
                HuaweiApiClientImpl.this.c(1);
                HuaweiApiClientImpl.this.b();
            }
            return true;
        }
    }

    public class b implements Handler.Callback {
        public b() {
        }

        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message message) {
            if (message == null || message.what != 3) {
                return false;
            }
            HMSLog.e("HuaweiApiClientImpl", "In connect, process time out");
            if (HuaweiApiClientImpl.this.f7494k.get() == 2) {
                HuaweiApiClientImpl.this.c(1);
                HuaweiApiClientImpl.this.b();
            }
            return true;
        }
    }

    public class c extends IAIDLCallback.Stub {

        /* renamed from: a, reason: collision with root package name */
        final /* synthetic */ ResultCallback f7511a;

        public c(HuaweiApiClientImpl huaweiApiClientImpl, ResultCallback resultCallback) {
            this.f7511a = resultCallback;
        }

        @Override // com.huawei.hms.core.aidl.IAIDLCallback
        public void call(DataBuffer dataBuffer) {
            if (dataBuffer == null) {
                HMSLog.i("HuaweiApiClientImpl", "Exit asyncRequest onResult -1");
                this.f7511a.onResult(new BundleResult(-1, null));
                return;
            }
            MessageCodec messageCodecFind = CodecLookup.find(dataBuffer.getProtocol());
            ResponseHeader responseHeader = new ResponseHeader();
            messageCodecFind.decode(dataBuffer.header, responseHeader);
            BundleResult bundleResult = new BundleResult(responseHeader.getStatusCode(), dataBuffer.getBody());
            HMSLog.i("HuaweiApiClientImpl", "Exit asyncRequest onResult");
            this.f7511a.onResult(bundleResult);
        }
    }

    public static class d extends PendingResultImpl<Status, IMessageEntity> {
        public d(ApiClient apiClient, String str, IMessageEntity iMessageEntity) {
            super(apiClient, str, iMessageEntity);
        }

        @Override // com.huawei.hms.support.api.PendingResultImpl
        public Status onComplete(IMessageEntity iMessageEntity) {
            return new Status(0);
        }
    }

    public class e implements ResultCallback<ResolveResult<ConnectResp>> {

        public class a implements Runnable {

            /* renamed from: a, reason: collision with root package name */
            final /* synthetic */ ResolveResult f7513a;

            public a(ResolveResult resolveResult) {
                this.f7513a = resolveResult;
            }

            @Override // java.lang.Runnable
            public void run() {
                HuaweiApiClientImpl.this.a((ResolveResult<ConnectResp>) this.f7513a);
            }
        }

        private e() {
        }

        @Override // com.huawei.hms.support.api.client.ResultCallback
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public void onResult(ResolveResult<ConnectResp> resolveResult) {
            new Handler(Looper.getMainLooper()).post(new a(resolveResult));
        }

        public /* synthetic */ e(HuaweiApiClientImpl huaweiApiClientImpl, a aVar) {
            this();
        }
    }

    public class f implements ResultCallback<ResolveResult<DisconnectResp>> {

        public class a implements Runnable {

            /* renamed from: a, reason: collision with root package name */
            final /* synthetic */ ResolveResult f7516a;

            public a(ResolveResult resolveResult) {
                this.f7516a = resolveResult;
            }

            @Override // java.lang.Runnable
            public void run() {
                HuaweiApiClientImpl.this.b((ResolveResult<DisconnectResp>) this.f7516a);
            }
        }

        private f() {
        }

        @Override // com.huawei.hms.support.api.client.ResultCallback
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public void onResult(ResolveResult<DisconnectResp> resolveResult) {
            new Handler(Looper.getMainLooper()).post(new a(resolveResult));
        }

        public /* synthetic */ f(HuaweiApiClientImpl huaweiApiClientImpl, a aVar) {
            this();
        }
    }

    public class g implements ResultCallback<ResolveResult<JosGetNoticeResp>> {
        private g() {
        }

        @Override // com.huawei.hms.support.api.client.ResultCallback
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public void onResult(ResolveResult<JosGetNoticeResp> resolveResult) {
            JosGetNoticeResp value;
            Intent noticeIntent;
            if (resolveResult == null || !resolveResult.getStatus().isSuccess() || (noticeIntent = (value = resolveResult.getValue()).getNoticeIntent()) == null || value.getStatusCode() != 0) {
                return;
            }
            HMSLog.i("HuaweiApiClientImpl", "get notice has intent.");
            Activity validActivity = Util.getValidActivity((Activity) HuaweiApiClientImpl.this.f7491h.get(), HuaweiApiClientImpl.this.getTopActivity());
            if (validActivity == null) {
                HMSLog.e("HuaweiApiClientImpl", "showNotice no valid activity!");
            } else {
                HuaweiApiClientImpl.this.f7493j = true;
                validActivity.startActivity(noticeIntent);
            }
        }

        public /* synthetic */ g(HuaweiApiClientImpl huaweiApiClientImpl, a aVar) {
            this();
        }
    }

    public HuaweiApiClientImpl(Context context) {
        ReentrantLock reentrantLock = new ReentrantLock();
        this.f7502s = reentrantLock;
        this.f7503t = reentrantLock.newCondition();
        this.f7507x = null;
        this.f7508y = null;
        this.f7485b = context;
        String appId = Util.getAppId(context);
        this.f7486c = appId;
        this.f7487d = appId;
        this.f7488e = Util.getCpId(context);
    }

    private DisconnectInfo d() {
        ArrayList arrayList = new ArrayList();
        Map<Api<?>, Api.ApiOptions> map = this.f7497n;
        if (map != null) {
            Iterator<Api<?>> it = map.keySet().iterator();
            while (it.hasNext()) {
                arrayList.add(it.next().getApiName());
            }
        }
        return new DisconnectInfo(this.f7495l, arrayList);
    }

    private int e() {
        int hmsVersion = Util.getHmsVersion(this.f7485b);
        if (hmsVersion != 0 && hmsVersion >= 20503000) {
            return hmsVersion;
        }
        int iF = f();
        if (g()) {
            if (iF < 20503000) {
                return 20503000;
            }
            return iF;
        }
        if (iF < 20600000) {
            return 20600000;
        }
        return iF;
    }

    private int f() {
        Integer num;
        int iIntValue;
        Map<Api<?>, Api.ApiOptions> apiMap = getApiMap();
        int i2 = 0;
        if (apiMap == null) {
            return 0;
        }
        Iterator<Api<?>> it = apiMap.keySet().iterator();
        while (it.hasNext()) {
            String apiName = it.next().getApiName();
            if (!TextUtils.isEmpty(apiName) && (num = HuaweiApiAvailability.getApiMap().get(apiName)) != null && (iIntValue = num.intValue()) > i2) {
                i2 = iIntValue;
            }
        }
        return i2;
    }

    private boolean g() {
        Map<Api<?>, Api.ApiOptions> map = this.f7497n;
        if (map == null) {
            return false;
        }
        Iterator<Api<?>> it = map.keySet().iterator();
        while (it.hasNext()) {
            if (HuaweiApiAvailability.HMS_API_NAME_GAME.equals(it.next().getApiName())) {
                return true;
            }
        }
        return false;
    }

    private void h() {
        Handler handler = this.f7507x;
        if (handler != null) {
            handler.removeMessages(2);
        } else {
            this.f7507x = new Handler(Looper.getMainLooper(), new a());
        }
        this.f7507x.sendEmptyMessageDelayed(2, 5000L);
    }

    private void i() {
        synchronized (A) {
            Handler handler = this.f7508y;
            if (handler != null) {
                handler.removeMessages(3);
            } else {
                this.f7508y = new Handler(Looper.getMainLooper(), new b());
            }
            HMSLog.d("HuaweiApiClientImpl", "sendEmptyMessageDelayed for onConnectionResult 3 seconds. the result is : " + this.f7508y.sendEmptyMessageDelayed(3, C.DEFAULT_MAX_SEEK_TO_PREVIOUS_POSITION_MS));
        }
    }

    private void j() {
        HMSLog.i("HuaweiApiClientImpl", "Enter sendConnectApiServceRequest.");
        ConnectService.connect(this, c()).setResultCallback(new e(this, null));
    }

    private void k() {
        ConnectService.disconnect(this, d()).setResultCallback(new f(this, null));
    }

    private void l() {
        HMSLog.i("HuaweiApiClientImpl", "Enter sendForceConnectApiServceRequest.");
        ConnectService.forceConnect(this, c()).setResultCallback(new e(this, null));
    }

    private void m() {
        if (this.f7493j) {
            HMSLog.i("HuaweiApiClientImpl", "Connect notice has been shown.");
        } else if (HuaweiApiAvailability.getInstance().isHuaweiMobileNoticeAvailable(this.f7485b) == 0) {
            ConnectService.getNotice(this, 0, "6.5.0.300").setResultCallback(new g(this, null));
        }
    }

    private void n() {
        Util.unBindServiceCatchException(this.f7485b, this);
        this.f7489f = null;
    }

    public int asyncRequest(Bundle bundle, String str, int i2, ResultCallback<BundleResult> resultCallback) {
        HMSLog.i("HuaweiApiClientImpl", "Enter asyncRequest.");
        if (resultCallback == null || str == null || bundle == null) {
            HMSLog.e("HuaweiApiClientImpl", "arguments is invalid.");
            return CommonCode.ErrorCode.ARGUMENTS_INVALID;
        }
        if (!innerIsConnected()) {
            HMSLog.e("HuaweiApiClientImpl", "client is unConnect.");
            return CommonCode.ErrorCode.CLIENT_API_INVALID;
        }
        DataBuffer dataBuffer = new DataBuffer(str, i2);
        MessageCodec messageCodecFind = CodecLookup.find(dataBuffer.getProtocol());
        dataBuffer.addBody(bundle);
        RequestHeader requestHeader = new RequestHeader(getAppID(), getPackageName(), 60500300, getSessionId());
        requestHeader.setApiNameList(getApiNameList());
        dataBuffer.header = messageCodecFind.encode(requestHeader, new Bundle());
        try {
            getService().asyncCall(dataBuffer, new c(this, resultCallback));
            return 0;
        } catch (RemoteException e2) {
            HMSLog.e("HuaweiApiClientImpl", "remote exception:" + e2.getMessage());
            return CommonCode.ErrorCode.INTERNAL_ERROR;
        }
    }

    @Override // com.huawei.hms.api.HuaweiApiClient
    public void checkUpdate(Activity activity, CheckUpdatelistener checkUpdatelistener) {
        if (checkUpdatelistener == null) {
            HMSLog.e("HuaweiApiClientImpl", "listener is null!");
        } else {
            checkUpdatelistener.onResult(-1);
        }
    }

    @Override // com.huawei.hms.api.HuaweiApiClient
    public void connect(Activity activity) {
        HMSLog.i("HuaweiApiClientImpl", "====== HMSSDK version: 60500300 ======");
        int i2 = this.f7494k.get();
        HMSLog.i("HuaweiApiClientImpl", "Enter connect, Connection Status: " + i2);
        if (i2 == 3 || i2 == 5 || i2 == 2 || i2 == 4) {
            return;
        }
        if (activity != null) {
            this.f7491h = new WeakReference<>(activity);
            this.f7492i = new WeakReference<>(activity);
        }
        this.f7487d = TextUtils.isEmpty(this.f7486c) ? Util.getAppId(this.f7485b) : this.f7486c;
        int iE = e();
        HMSLog.i("HuaweiApiClientImpl", "connect minVersion:" + iE);
        HuaweiApiAvailability.setServicesVersionCode(iE);
        int iIsHuaweiMobileServicesAvailable = HuaweiMobileServicesUtil.isHuaweiMobileServicesAvailable(this.f7485b, iE);
        HMSLog.i("HuaweiApiClientImpl", "In connect, isHuaweiMobileServicesAvailable result: " + iIsHuaweiMobileServicesAvailable);
        this.f7500q = HMSPackageManager.getInstance(this.f7485b).getHmsMultiServiceVersion();
        if (iIsHuaweiMobileServicesAvailable != 0) {
            if (this.f7506w != null) {
                b(iIsHuaweiMobileServicesAvailable);
                return;
            }
            return;
        }
        c(5);
        if (this.f7489f == null) {
            a();
            return;
        }
        c(2);
        j();
        i();
    }

    @Override // com.huawei.hms.api.HuaweiApiClient
    public void connectForeground() {
        HMSLog.i("HuaweiApiClientImpl", "====== HMSSDK version: 60500300 ======");
        int i2 = this.f7494k.get();
        HMSLog.i("HuaweiApiClientImpl", "Enter forceConnect, Connection Status: " + i2);
        if (i2 == 3 || i2 == 5 || i2 == 2 || i2 == 4) {
            return;
        }
        this.f7487d = TextUtils.isEmpty(this.f7486c) ? Util.getAppId(this.f7485b) : this.f7486c;
        l();
    }

    @Override // com.huawei.hms.api.HuaweiApiClient
    public void disableLifeCycleManagement(Activity activity) {
        if (this.f7484a < 0) {
            throw new IllegalStateException("disableLifeCycleManagement failed");
        }
        AutoLifecycleFragment.getInstance(activity).stopAutoManage(this.f7484a);
    }

    @Override // com.huawei.hms.api.HuaweiApiClient
    public PendingResult<Status> discardAndReconnect() {
        return new d(this, null, null);
    }

    @Override // com.huawei.hms.api.HuaweiApiClient
    public void disconnect() {
        int i2 = this.f7494k.get();
        HMSLog.i("HuaweiApiClientImpl", "Enter disconnect, Connection Status: " + i2);
        if (i2 == 2) {
            c(4);
            return;
        }
        if (i2 == 3) {
            c(4);
            k();
        } else {
            if (i2 != 5) {
                return;
            }
            a(2);
            c(4);
        }
    }

    @Override // com.huawei.hms.api.HuaweiApiClient
    public Map<Api<?>, Api.ApiOptions> getApiMap() {
        return this.f7497n;
    }

    @Override // com.huawei.hms.support.api.client.AidlApiClient
    public List<String> getApiNameList() {
        ArrayList arrayList = new ArrayList();
        Map<Api<?>, Api.ApiOptions> map = this.f7497n;
        if (map != null) {
            Iterator<Api<?>> it = map.keySet().iterator();
            while (it.hasNext()) {
                arrayList.add(it.next().getApiName());
            }
        }
        return arrayList;
    }

    @Override // com.huawei.hms.support.api.client.ApiClient
    public String getAppID() {
        return this.f7487d;
    }

    @Override // com.huawei.hms.api.HuaweiApiClient
    public ConnectionResult getConnectionResult(Api<?> api) {
        if (isConnected()) {
            this.f7504u = null;
            return new ConnectionResult(0, (PendingIntent) null);
        }
        ConnectionResult connectionResult = this.f7504u;
        return connectionResult != null ? connectionResult : new ConnectionResult(13, (PendingIntent) null);
    }

    @Override // com.huawei.hms.support.api.client.ApiClient
    public Context getContext() {
        return this.f7485b;
    }

    @Override // com.huawei.hms.support.api.client.ApiClient
    public String getCpID() {
        return this.f7488e;
    }

    @Override // com.huawei.hms.support.api.client.ApiClient
    public String getPackageName() {
        return this.f7485b.getPackageName();
    }

    @Override // com.huawei.hms.api.HuaweiApiClient
    public List<PermissionInfo> getPermissionInfos() {
        return this.f7496m;
    }

    @Override // com.huawei.hms.api.HuaweiApiClient
    public List<Scope> getScopes() {
        return this.f7495l;
    }

    @Override // com.huawei.hms.support.api.client.AidlApiClient
    public IAIDLInvoke getService() {
        return this.f7489f;
    }

    @Override // com.huawei.hms.support.api.client.ApiClient
    public String getSessionId() {
        return this.f7490g;
    }

    @Override // com.huawei.hms.support.api.client.ApiClient
    public final SubAppInfo getSubAppInfo() {
        return this.f7498o;
    }

    @Override // com.huawei.hms.api.HuaweiApiClient
    public Activity getTopActivity() {
        WeakReference<Activity> weakReference = this.f7492i;
        if (weakReference == null) {
            return null;
        }
        return weakReference.get();
    }

    @Override // com.huawei.hms.support.api.client.ApiClient
    public String getTransportName() {
        return IPCTransport.class.getName();
    }

    @Override // com.huawei.hms.api.HuaweiApiClient
    public boolean hasConnectedApi(Api<?> api) {
        return isConnected();
    }

    @Override // com.huawei.hms.api.HuaweiApiClient
    public boolean hasConnectionFailureListener(HuaweiApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        Checker.checkNonNull(onConnectionFailedListener, "onConnectionFailedListener should not be null");
        synchronized (this.f7501r) {
            return this.f7506w == onConnectionFailedListener;
        }
    }

    @Override // com.huawei.hms.api.HuaweiApiClient
    public boolean hasConnectionSuccessListener(HuaweiApiClient.ConnectionCallbacks connectionCallbacks) {
        Checker.checkNonNull(connectionCallbacks, "connectionCallbacksListener should not be null");
        synchronized (this.f7501r) {
            return this.f7505v == connectionCallbacks;
        }
    }

    @Override // com.huawei.hms.api.HuaweiApiClient
    public ConnectionResult holdUpConnect() {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            throw new IllegalStateException("blockingConnect must not be called on the UI thread");
        }
        this.f7502s.lock();
        try {
            connect((Activity) null);
            while (isConnecting()) {
                this.f7503t.await();
            }
            if (isConnected()) {
                this.f7504u = null;
                return new ConnectionResult(0, (PendingIntent) null);
            }
            ConnectionResult connectionResult = this.f7504u;
            return connectionResult != null ? connectionResult : new ConnectionResult(13, (PendingIntent) null);
        } catch (InterruptedException unused) {
            Thread.currentThread().interrupt();
            return new ConnectionResult(15, (PendingIntent) null);
        } finally {
            this.f7502s.unlock();
        }
    }

    @Override // com.huawei.hms.support.api.client.InnerApiClient
    public boolean innerIsConnected() {
        return this.f7494k.get() == 3 || this.f7494k.get() == 4;
    }

    @Override // com.huawei.hms.api.HuaweiApiClient, com.huawei.hms.support.api.client.ApiClient
    public boolean isConnected() {
        if (this.f7500q == 0) {
            this.f7500q = HMSPackageManager.getInstance(this.f7485b).getHmsMultiServiceVersion();
        }
        if (this.f7500q >= 20504000) {
            return innerIsConnected();
        }
        long jCurrentTimeMillis = System.currentTimeMillis() - this.f7499p;
        if (jCurrentTimeMillis > 0 && jCurrentTimeMillis < 300000) {
            return innerIsConnected();
        }
        if (!innerIsConnected()) {
            return false;
        }
        Status status = ((ResolveResult) ConnectService.checkconnect(this, new CheckConnectInfo()).awaitOnAnyThread(ExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS, TimeUnit.MILLISECONDS)).getStatus();
        if (status.isSuccess()) {
            this.f7499p = System.currentTimeMillis();
            return true;
        }
        int statusCode = status.getStatusCode();
        HMSLog.i("HuaweiApiClientImpl", "isConnected is false, statuscode:" + statusCode);
        if (statusCode == 907135004) {
            return false;
        }
        n();
        c(1);
        this.f7499p = System.currentTimeMillis();
        return false;
    }

    @Override // com.huawei.hms.api.HuaweiApiClient
    public boolean isConnecting() {
        int i2 = this.f7494k.get();
        return i2 == 2 || i2 == 5;
    }

    @Override // com.huawei.hms.api.HuaweiApiClient
    public void onPause(Activity activity) {
        HMSLog.i("HuaweiApiClientImpl", "onPause");
    }

    @Override // com.huawei.hms.api.HuaweiApiClient
    public void onResume(Activity activity) {
        if (activity != null) {
            HMSLog.i("HuaweiApiClientImpl", "onResume");
            this.f7492i = new WeakReference<>(activity);
        }
    }

    @Override // android.content.ServiceConnection
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        HMSLog.i("HuaweiApiClientImpl", "HuaweiApiClientImpl Enter onServiceConnected.");
        a(2);
        this.f7489f = IAIDLInvoke.Stub.asInterface(iBinder);
        if (this.f7489f != null) {
            if (this.f7494k.get() == 5) {
                c(2);
                j();
                i();
                return;
            } else {
                if (this.f7494k.get() != 3) {
                    n();
                    return;
                }
                return;
            }
        }
        HMSLog.e("HuaweiApiClientImpl", "In onServiceConnected, mCoreService must not be null.");
        n();
        c(1);
        if (this.f7506w != null) {
            WeakReference<Activity> weakReference = this.f7491h;
            ConnectionResult connectionResult = new ConnectionResult(10, (weakReference == null || weakReference.get() == null) ? null : HuaweiApiAvailability.getInstance().getResolveErrorPendingIntent(this.f7491h.get(), 10));
            this.f7506w.onConnectionFailed(connectionResult);
            this.f7504u = connectionResult;
        }
    }

    @Override // android.content.ServiceConnection
    public void onServiceDisconnected(ComponentName componentName) {
        HMSLog.i("HuaweiApiClientImpl", "Enter onServiceDisconnected.");
        this.f7489f = null;
        c(1);
        HuaweiApiClient.ConnectionCallbacks connectionCallbacks = this.f7505v;
        if (connectionCallbacks != null) {
            connectionCallbacks.onConnectionSuspended(1);
        }
    }

    @Override // com.huawei.hms.api.HuaweiApiClient
    public void print(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
    }

    @Override // com.huawei.hms.api.HuaweiApiClient
    public void reconnect() {
        disconnect();
        connect((Activity) null);
    }

    @Override // com.huawei.hms.api.HuaweiApiClient
    public void removeConnectionFailureListener(HuaweiApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        Checker.checkNonNull(onConnectionFailedListener, "onConnectionFailedListener should not be null");
        synchronized (this.f7501r) {
            if (this.f7506w != onConnectionFailedListener) {
                HMSLog.w("HuaweiApiClientImpl", "unregisterConnectionFailedListener: this onConnectionFailedListener has not been registered");
            } else {
                this.f7506w = null;
            }
        }
    }

    @Override // com.huawei.hms.api.HuaweiApiClient
    public void removeConnectionSuccessListener(HuaweiApiClient.ConnectionCallbacks connectionCallbacks) {
        Checker.checkNonNull(connectionCallbacks, "connectionCallbacksListener should not be null");
        synchronized (this.f7501r) {
            if (this.f7505v != connectionCallbacks) {
                HMSLog.w("HuaweiApiClientImpl", "unregisterConnectionCallback: this connectionCallbacksListener has not been registered");
            } else {
                this.f7505v = null;
            }
        }
    }

    public void setApiMap(Map<Api<?>, Api.ApiOptions> map) {
        this.f7497n = map;
    }

    public void setAutoLifecycleClientId(int i2) {
        this.f7484a = i2;
    }

    @Override // com.huawei.hms.api.HuaweiApiClient
    public void setConnectionCallbacks(HuaweiApiClient.ConnectionCallbacks connectionCallbacks) {
        this.f7505v = connectionCallbacks;
    }

    @Override // com.huawei.hms.api.HuaweiApiClient
    public void setConnectionFailedListener(HuaweiApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        this.f7506w = onConnectionFailedListener;
    }

    public void setHasShowNotice(boolean z2) {
        this.f7493j = z2;
    }

    public void setPermissionInfos(List<PermissionInfo> list) {
        this.f7496m = list;
    }

    public void setScopes(List<Scope> list) {
        this.f7495l = list;
    }

    @Override // com.huawei.hms.api.HuaweiApiClient
    public boolean setSubAppInfo(SubAppInfo subAppInfo) {
        HMSLog.i("HuaweiApiClientImpl", "Enter setSubAppInfo");
        if (subAppInfo == null) {
            HMSLog.e("HuaweiApiClientImpl", "subAppInfo is null");
            return false;
        }
        String subAppID = subAppInfo.getSubAppID();
        if (TextUtils.isEmpty(subAppID)) {
            HMSLog.e("HuaweiApiClientImpl", "subAppId is empty");
            return false;
        }
        if (subAppID.equals(TextUtils.isEmpty(this.f7486c) ? Util.getAppId(this.f7485b) : this.f7486c)) {
            HMSLog.e("HuaweiApiClientImpl", "subAppId is host appid");
            return false;
        }
        this.f7498o = new SubAppInfo(subAppInfo);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c(int i2) {
        this.f7494k.set(i2);
        if (i2 == 1 || i2 == 3 || i2 == 2) {
            this.f7502s.lock();
            try {
                this.f7503t.signalAll();
            } finally {
                this.f7502s.unlock();
            }
        }
    }

    private void b(int i2) {
        PendingIntent resolveErrorPendingIntent;
        WeakReference<Activity> weakReference = this.f7491h;
        if (weakReference == null || weakReference.get() == null) {
            resolveErrorPendingIntent = null;
        } else {
            resolveErrorPendingIntent = HuaweiApiAvailability.getInstance().getResolveErrorPendingIntent(this.f7491h.get(), i2);
            HMSLog.i("HuaweiApiClientImpl", "connect 2.0 fail: " + i2);
        }
        ConnectionResult connectionResult = new ConnectionResult(i2, resolveErrorPendingIntent);
        this.f7506w.onConnectionFailed(connectionResult);
        this.f7504u = connectionResult;
    }

    private void a() {
        Intent intent = new Intent(HMSPackageManager.getInstance(this.f7485b).getServiceAction());
        HMSPackageManager.getInstance(this.f7485b).refreshForMultiService();
        intent.setPackage(HMSPackageManager.getInstance(this.f7485b).getHMSPackageNameForMultiService());
        synchronized (f7483z) {
            if (this.f7485b.bindService(intent, this, 1)) {
                h();
                return;
            }
            c(1);
            HMSLog.e("HuaweiApiClientImpl", "In connect, bind core service fail");
            b();
        }
    }

    private ConnectInfo c() throws PackageManager.NameNotFoundException {
        String packageSignature = new PackageManagerHelper(this.f7485b).getPackageSignature(this.f7485b.getPackageName());
        if (packageSignature == null) {
            packageSignature = "";
        }
        SubAppInfo subAppInfo = this.f7498o;
        return new ConnectInfo(getApiNameList(), this.f7495l, packageSignature, subAppInfo == null ? null : subAppInfo.getSubAppID());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b() {
        n();
        if (this.f7506w != null) {
            int i2 = UIUtil.isBackground(this.f7485b) ? 7 : 6;
            WeakReference<Activity> weakReference = this.f7491h;
            ConnectionResult connectionResult = new ConnectionResult(i2, (weakReference == null || weakReference.get() == null) ? null : HuaweiApiAvailability.getInstance().getResolveErrorPendingIntent(this.f7491h.get(), i2));
            this.f7506w.onConnectionFailed(connectionResult);
            this.f7504u = connectionResult;
        }
    }

    private void c(ResolveResult<ConnectResp> resolveResult) {
        if (resolveResult.getValue() != null) {
            ProtocolNegotiate.getInstance().negotiate(resolveResult.getValue().protocolVersion);
        }
        c(3);
        this.f7504u = null;
        HuaweiApiClient.ConnectionCallbacks connectionCallbacks = this.f7505v;
        if (connectionCallbacks != null) {
            connectionCallbacks.onConnected();
        }
        if (this.f7491h != null) {
            m();
        }
        for (Map.Entry<Api<?>, Api.ApiOptions> entry : getApiMap().entrySet()) {
            if (entry.getKey().getmConnetctPostList() != null && !entry.getKey().getmConnetctPostList().isEmpty()) {
                HMSLog.i("HuaweiApiClientImpl", "Enter onConnectionResult, get the ConnetctPostList ");
                for (ConnectionPostProcessor connectionPostProcessor : entry.getKey().getmConnetctPostList()) {
                    HMSLog.i("HuaweiApiClientImpl", "Enter onConnectionResult, processor.run");
                    connectionPostProcessor.run(this, this.f7491h);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(ResolveResult<DisconnectResp> resolveResult) {
        HMSLog.i("HuaweiApiClientImpl", "Enter onDisconnectionResult, disconnect from server result: " + resolveResult.getStatus().getStatusCode());
        n();
        c(1);
    }

    private void a(int i2) {
        if (i2 == 2) {
            synchronized (f7483z) {
                Handler handler = this.f7507x;
                if (handler != null) {
                    handler.removeMessages(i2);
                    this.f7507x = null;
                }
            }
        }
        if (i2 == 3) {
            synchronized (A) {
                Handler handler2 = this.f7508y;
                if (handler2 != null) {
                    handler2.removeMessages(i2);
                    this.f7508y = null;
                }
            }
        }
        synchronized (f7483z) {
            Handler handler3 = this.f7507x;
            if (handler3 != null) {
                handler3.removeMessages(2);
                this.f7507x = null;
            }
        }
    }

    @Override // com.huawei.hms.api.HuaweiApiClient
    public ConnectionResult holdUpConnect(long j2, TimeUnit timeUnit) {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            this.f7502s.lock();
            try {
                connect((Activity) null);
                long nanos = timeUnit.toNanos(j2);
                while (isConnecting()) {
                    if (nanos <= 0) {
                        disconnect();
                        return new ConnectionResult(14, (PendingIntent) null);
                    }
                    nanos = this.f7503t.awaitNanos(nanos);
                }
                if (isConnected()) {
                    this.f7504u = null;
                    return new ConnectionResult(0, (PendingIntent) null);
                }
                ConnectionResult connectionResult = this.f7504u;
                return connectionResult != null ? connectionResult : new ConnectionResult(13, (PendingIntent) null);
            } catch (InterruptedException unused) {
                Thread.currentThread().interrupt();
                return new ConnectionResult(15, (PendingIntent) null);
            } finally {
                this.f7502s.unlock();
            }
        }
        throw new IllegalStateException("blockingConnect must not be called on the UI thread");
    }

    @Override // com.huawei.hms.api.HuaweiApiClient
    public void connect(int i2) {
        connect((Activity) null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(ResolveResult<ConnectResp> resolveResult) {
        HMSLog.i("HuaweiApiClientImpl", "Enter onConnectionResult");
        if (this.f7489f != null && this.f7494k.get() == 2) {
            a(3);
            ConnectResp value = resolveResult.getValue();
            if (value != null) {
                this.f7490g = value.sessionId;
            }
            SubAppInfo subAppInfo = this.f7498o;
            PendingIntent resolveErrorPendingIntent = null;
            String subAppID = subAppInfo == null ? null : subAppInfo.getSubAppID();
            if (!TextUtils.isEmpty(subAppID)) {
                this.f7487d = subAppID;
            }
            int statusCode = resolveResult.getStatus().getStatusCode();
            HMSLog.i("HuaweiApiClientImpl", "Enter onConnectionResult, connect to server result: " + statusCode);
            if (Status.SUCCESS.equals(resolveResult.getStatus())) {
                c(resolveResult);
                return;
            }
            if (resolveResult.getStatus() != null && resolveResult.getStatus().getStatusCode() == 1001) {
                n();
                c(1);
                HuaweiApiClient.ConnectionCallbacks connectionCallbacks = this.f7505v;
                if (connectionCallbacks != null) {
                    connectionCallbacks.onConnectionSuspended(3);
                    return;
                }
                return;
            }
            n();
            c(1);
            if (this.f7506w != null) {
                WeakReference<Activity> weakReference = this.f7491h;
                if (weakReference != null && weakReference.get() != null) {
                    resolveErrorPendingIntent = HuaweiApiAvailability.getInstance().getResolveErrorPendingIntent(this.f7491h.get(), statusCode);
                }
                ConnectionResult connectionResult = new ConnectionResult(statusCode, resolveErrorPendingIntent);
                this.f7506w.onConnectionFailed(connectionResult);
                this.f7504u = connectionResult;
                return;
            }
            return;
        }
        HMSLog.e("HuaweiApiClientImpl", "Invalid onConnectionResult");
    }
}
