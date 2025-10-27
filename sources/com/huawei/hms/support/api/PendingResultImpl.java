package com.huawei.hms.support.api;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Pair;
import com.huawei.hms.adapter.BaseAdapter;
import com.huawei.hms.common.internal.TransactionIdCreater;
import com.huawei.hms.core.aidl.AbstractMessageEntity;
import com.huawei.hms.core.aidl.IMessageEntity;
import com.huawei.hms.support.api.client.ApiClient;
import com.huawei.hms.support.api.client.InnerPendingResult;
import com.huawei.hms.support.api.client.Result;
import com.huawei.hms.support.api.client.ResultCallback;
import com.huawei.hms.support.api.client.Status;
import com.huawei.hms.support.api.client.SubAppInfo;
import com.huawei.hms.support.api.entity.core.CommonCode;
import com.huawei.hms.support.api.transport.DatagramTransport;
import com.huawei.hms.support.gentyref.GenericTypeReflector;
import com.huawei.hms.support.hianalytics.HiAnalyticsConstant;
import com.huawei.hms.support.hianalytics.HiAnalyticsUtil;
import com.huawei.hms.support.log.HMSLog;
import com.huawei.hms.utils.Util;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes4.dex */
public abstract class PendingResultImpl<R extends Result, T extends IMessageEntity> extends InnerPendingResult<R> {
    private static final String TAG = "PendingResultImpl";
    private WeakReference<ApiClient> api;
    private CountDownLatch countLatch;
    protected DatagramTransport transport = null;
    private R result = null;
    private String url = null;
    private String transId = null;
    private boolean isNeedReport = true;

    public class a implements DatagramTransport.a {
        public a() {
        }

        @Override // com.huawei.hms.support.api.transport.DatagramTransport.a
        public void a(int i2, IMessageEntity iMessageEntity) {
            PendingResultImpl.this.setResult(i2, iMessageEntity);
            PendingResultImpl.this.countLatch.countDown();
        }
    }

    public class b implements DatagramTransport.a {

        /* renamed from: a, reason: collision with root package name */
        final /* synthetic */ AtomicBoolean f8068a;

        public b(AtomicBoolean atomicBoolean) {
            this.f8068a = atomicBoolean;
        }

        @Override // com.huawei.hms.support.api.transport.DatagramTransport.a
        public void a(int i2, IMessageEntity iMessageEntity) {
            if (!this.f8068a.get()) {
                PendingResultImpl.this.setResult(i2, iMessageEntity);
            }
            PendingResultImpl.this.countLatch.countDown();
        }
    }

    public class c implements DatagramTransport.a {

        /* renamed from: a, reason: collision with root package name */
        final /* synthetic */ d f8070a;

        /* renamed from: b, reason: collision with root package name */
        final /* synthetic */ ResultCallback f8071b;

        public c(d dVar, ResultCallback resultCallback) {
            this.f8070a = dVar;
            this.f8071b = resultCallback;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.huawei.hms.support.api.transport.DatagramTransport.a
        public void a(int i2, IMessageEntity iMessageEntity) {
            PendingResultImpl.this.setResult(i2, iMessageEntity);
            this.f8070a.a(this.f8071b, PendingResultImpl.this.result);
        }
    }

    public static class d<R extends Result> extends Handler {
        public d(Looper looper) {
            super(looper);
        }

        public void a(ResultCallback<? super R> resultCallback, R r2) {
            sendMessage(obtainMessage(1, new Pair(resultCallback, r2)));
        }

        /* JADX WARN: Multi-variable type inference failed */
        public void b(ResultCallback<? super R> resultCallback, R r2) {
            resultCallback.onResult(r2);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            if (message.what != 1) {
                return;
            }
            Pair pair = (Pair) message.obj;
            b((ResultCallback) pair.first, (Result) pair.second);
        }
    }

    public PendingResultImpl(ApiClient apiClient, String str, IMessageEntity iMessageEntity) {
        init(apiClient, str, iMessageEntity, getResponseType(), 0);
    }

    private void biReportEvent(int i2, int i3) {
        SubAppInfo subAppInfo;
        HMSLog.i(TAG, "biReportEvent ====== ");
        ApiClient apiClient = this.api.get();
        if (apiClient == null || this.url == null || HiAnalyticsUtil.getInstance().hasError(apiClient.getContext())) {
            return;
        }
        HashMap map = new HashMap();
        map.put("package", apiClient.getPackageName());
        map.put(HiAnalyticsConstant.HaKey.BI_KEY_BASE_VERSION, "6.5.0.300");
        if (i3 == 1) {
            map.put(HiAnalyticsConstant.HaKey.BI_KEY_DIRECTION, HiAnalyticsConstant.Direction.REQUEST);
        } else {
            map.put(HiAnalyticsConstant.HaKey.BI_KEY_DIRECTION, HiAnalyticsConstant.Direction.RESPONSE);
            map.put("result", String.valueOf(i2));
            R r2 = this.result;
            if (r2 != null && r2.getStatus() != null) {
                map.put(HiAnalyticsConstant.HaKey.BI_KEY_RESULT, String.valueOf(this.result.getStatus().getStatusCode()));
            }
        }
        map.put("version", "0");
        String appId = Util.getAppId(apiClient.getContext());
        if (TextUtils.isEmpty(appId) && (subAppInfo = apiClient.getSubAppInfo()) != null) {
            appId = subAppInfo.getSubAppID();
        }
        map.put("appid", appId);
        if (TextUtils.isEmpty(this.transId)) {
            String id = TransactionIdCreater.getId(appId, this.url);
            this.transId = id;
            map.put(HiAnalyticsConstant.HaKey.BI_KEY_TRANSID, id);
        } else {
            map.put(HiAnalyticsConstant.HaKey.BI_KEY_TRANSID, this.transId);
            this.transId = null;
        }
        String[] strArrSplit = this.url.split("\\.");
        if (strArrSplit.length >= 2) {
            map.put("service", strArrSplit[0]);
            map.put("apiName", strArrSplit[1]);
        }
        map.put("callTime", String.valueOf(System.currentTimeMillis()));
        map.put(HiAnalyticsConstant.HaKey.BI_KEY_PHONETYPE, Util.getSystemProperties("ro.logsystem.usertype", ""));
        HiAnalyticsUtil.getInstance().onEvent(apiClient.getContext(), HiAnalyticsConstant.HMS_SDK_BASE_CALL_AIDL, map);
    }

    private void init(ApiClient apiClient, String str, IMessageEntity iMessageEntity, Class<T> cls, int i2) {
        HMSLog.i(TAG, "init uri:" + str);
        this.url = str;
        if (apiClient == null) {
            HMSLog.e(TAG, "client is null");
            return;
        }
        this.api = new WeakReference<>(apiClient);
        this.countLatch = new CountDownLatch(1);
        try {
            this.transport = (DatagramTransport) Class.forName(apiClient.getTransportName()).getConstructor(String.class, IMessageEntity.class, Class.class, Integer.TYPE).newInstance(str, iMessageEntity, cls, Integer.valueOf(i2));
        } catch (ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InstantiationException | NoSuchMethodException | InvocationTargetException e2) {
            HMSLog.e(TAG, "gen transport error:" + e2.getMessage());
            throw new IllegalStateException("Instancing transport exception, " + e2.getMessage(), e2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setResult(int i2, IMessageEntity iMessageEntity) {
        Status status;
        HMSLog.i(TAG, "setResult:" + i2);
        Status commonStatus = iMessageEntity instanceof AbstractMessageEntity ? ((AbstractMessageEntity) iMessageEntity).getCommonStatus() : null;
        if (i2 == 0) {
            this.result = (R) onComplete(iMessageEntity);
        } else {
            this.result = (R) onError(i2);
        }
        if (this.isNeedReport) {
            biReportEvent(i2, 2);
        }
        R r2 = this.result;
        if (r2 == null || (status = r2.getStatus()) == null || commonStatus == null) {
            return;
        }
        int statusCode = status.getStatusCode();
        String statusMessage = status.getStatusMessage();
        int statusCode2 = commonStatus.getStatusCode();
        String statusMessage2 = commonStatus.getStatusMessage();
        if (statusCode == statusCode2) {
            if (!TextUtils.isEmpty(statusMessage) || TextUtils.isEmpty(statusMessage2)) {
                return;
            }
            HMSLog.i(TAG, "rstStatus msg (" + statusMessage + ") is not equal commonStatus msg (" + statusMessage2 + ")");
            this.result.setStatus(new Status(statusCode, statusMessage2, status.getResolution()));
            return;
        }
        HMSLog.e(TAG, "rstStatus code (" + statusCode + ") is not equal commonStatus code (" + statusCode2 + ")");
        HMSLog.e(TAG, "rstStatus msg (" + statusMessage + ") is not equal commonStatus msg (" + statusMessage2 + ")");
    }

    @Override // com.huawei.hms.support.api.client.PendingResult
    public final R await() {
        HMSLog.i(TAG, "await");
        if (Looper.myLooper() != Looper.getMainLooper()) {
            return (R) awaitOnAnyThread();
        }
        HMSLog.e(TAG, "await in main thread");
        throw new IllegalStateException("await must not be called on the UI thread");
    }

    @Override // com.huawei.hms.support.api.client.InnerPendingResult
    public final R awaitOnAnyThread() throws InterruptedException {
        HMSLog.i(TAG, "awaitOnAnyThread");
        WeakReference<ApiClient> weakReference = this.api;
        if (weakReference == null) {
            HMSLog.e(TAG, "api is null");
            setResult(CommonCode.ErrorCode.CLIENT_API_INVALID, null);
            return this.result;
        }
        ApiClient apiClient = weakReference.get();
        if (!checkApiClient(apiClient)) {
            HMSLog.e(TAG, "client invalid");
            setResult(CommonCode.ErrorCode.CLIENT_API_INVALID, null);
            return this.result;
        }
        if (this.isNeedReport) {
            biReportEvent(0, 1);
        }
        this.transport.send(apiClient, new a());
        try {
            this.countLatch.await();
        } catch (InterruptedException unused) {
            HMSLog.e(TAG, "await in anythread InterruptedException");
            setResult(CommonCode.ErrorCode.INTERNAL_ERROR, null);
        }
        return this.result;
    }

    @Override // com.huawei.hms.support.api.client.PendingResult
    @Deprecated
    public void cancel() {
    }

    public boolean checkApiClient(ApiClient apiClient) {
        return true;
    }

    public Class<T> getResponseType() {
        Type type;
        Type genericSuperclass = getClass().getGenericSuperclass();
        if (genericSuperclass == null || (type = ((ParameterizedType) genericSuperclass).getActualTypeArguments()[1]) == null) {
            return null;
        }
        return (Class) type;
    }

    @Override // com.huawei.hms.support.api.client.PendingResult
    @Deprecated
    public boolean isCanceled() {
        return false;
    }

    public abstract R onComplete(T t2);

    public R onError(int i2) {
        Type genericSuperclass = getClass().getGenericSuperclass();
        Type type = genericSuperclass != null ? ((ParameterizedType) genericSuperclass).getActualTypeArguments()[0] : null;
        Class<?> type2 = type != null ? GenericTypeReflector.getType(type) : null;
        if (type2 != null) {
            try {
                R r2 = (R) type2.newInstance();
                this.result = r2;
                r2.setStatus(new Status(i2));
            } catch (Exception e2) {
                HMSLog.e(TAG, "on Error:" + e2.getMessage());
                return null;
            }
        }
        return this.result;
    }

    @Override // com.huawei.hms.support.api.client.PendingResult
    public void setResultCallback(ResultCallback<R> resultCallback) {
        this.isNeedReport = !(resultCallback instanceof BaseAdapter.BaseRequestResultCallback);
        setResultCallback(Looper.getMainLooper(), resultCallback);
    }

    @Override // com.huawei.hms.support.api.client.PendingResult
    public final void setResultCallback(Looper looper, ResultCallback<R> resultCallback) {
        HMSLog.i(TAG, "setResultCallback");
        if (looper == null) {
            looper = Looper.myLooper();
        }
        d dVar = new d(looper);
        WeakReference<ApiClient> weakReference = this.api;
        if (weakReference == null) {
            HMSLog.e(TAG, "api is null");
            setResult(CommonCode.ErrorCode.CLIENT_API_INVALID, null);
            return;
        }
        ApiClient apiClient = weakReference.get();
        if (!checkApiClient(apiClient)) {
            HMSLog.e(TAG, "client is invalid");
            setResult(CommonCode.ErrorCode.CLIENT_API_INVALID, null);
            dVar.a(resultCallback, this.result);
        } else {
            if (this.isNeedReport) {
                biReportEvent(0, 1);
            }
            this.transport.post(apiClient, new c(dVar, resultCallback));
        }
    }

    @Override // com.huawei.hms.support.api.client.PendingResult
    public R await(long j2, TimeUnit timeUnit) {
        HMSLog.i(TAG, "await timeout:" + j2 + " unit:" + timeUnit.toString());
        if (Looper.myLooper() != Looper.getMainLooper()) {
            return (R) awaitOnAnyThread(j2, timeUnit);
        }
        HMSLog.i(TAG, "await in main thread");
        throw new IllegalStateException("await must not be called on the UI thread");
    }

    public PendingResultImpl(ApiClient apiClient, String str, IMessageEntity iMessageEntity, Class<T> cls) {
        init(apiClient, str, iMessageEntity, cls, 0);
    }

    public PendingResultImpl(ApiClient apiClient, String str, IMessageEntity iMessageEntity, int i2) {
        init(apiClient, str, iMessageEntity, getResponseType(), i2);
    }

    @Override // com.huawei.hms.support.api.client.PendingResult
    @Deprecated
    public void setResultCallback(ResultCallback<R> resultCallback, long j2, TimeUnit timeUnit) {
        setResultCallback(resultCallback);
    }

    @Override // com.huawei.hms.support.api.client.InnerPendingResult
    public final R awaitOnAnyThread(long j2, TimeUnit timeUnit) {
        HMSLog.i(TAG, "awaitOnAnyThread timeout:" + j2 + " unit:" + timeUnit.toString());
        WeakReference<ApiClient> weakReference = this.api;
        if (weakReference == null) {
            HMSLog.e(TAG, "api is null");
            setResult(CommonCode.ErrorCode.CLIENT_API_INVALID, null);
            return this.result;
        }
        ApiClient apiClient = weakReference.get();
        if (!checkApiClient(apiClient)) {
            HMSLog.e(TAG, "client invalid");
            setResult(CommonCode.ErrorCode.CLIENT_API_INVALID, null);
            return this.result;
        }
        AtomicBoolean atomicBoolean = new AtomicBoolean();
        if (this.isNeedReport) {
            biReportEvent(0, 1);
        }
        this.transport.post(apiClient, new b(atomicBoolean));
        try {
            if (!this.countLatch.await(j2, timeUnit)) {
                atomicBoolean.set(true);
                setResult(CommonCode.ErrorCode.EXECUTE_TIMEOUT, null);
            }
        } catch (InterruptedException unused) {
            HMSLog.e(TAG, "awaitOnAnyThread InterruptedException");
            setResult(CommonCode.ErrorCode.INTERNAL_ERROR, null);
        }
        return this.result;
    }
}
