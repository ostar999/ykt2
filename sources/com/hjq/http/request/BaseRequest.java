package com.hjq.http.request;

import androidx.lifecycle.LifecycleOwner;
import com.hjq.http.EasyConfig;
import com.hjq.http.EasyLog;
import com.hjq.http.EasyUtils;
import com.hjq.http.annotation.HttpHeader;
import com.hjq.http.annotation.HttpIgnore;
import com.hjq.http.annotation.HttpRename;
import com.hjq.http.callback.NormalCallback;
import com.hjq.http.config.IRequestApi;
import com.hjq.http.config.IRequestCache;
import com.hjq.http.config.IRequestClient;
import com.hjq.http.config.IRequestHandler;
import com.hjq.http.config.IRequestHost;
import com.hjq.http.config.IRequestInterceptor;
import com.hjq.http.config.IRequestPath;
import com.hjq.http.config.IRequestServer;
import com.hjq.http.config.IRequestType;
import com.hjq.http.config.RequestApi;
import com.hjq.http.config.RequestServer;
import com.hjq.http.lifecycle.HttpLifecycleManager;
import com.hjq.http.listener.OnHttpListener;
import com.hjq.http.model.BodyType;
import com.hjq.http.model.CacheMode;
import com.hjq.http.model.CallProxy;
import com.hjq.http.model.HttpHeaders;
import com.hjq.http.model.HttpParams;
import com.hjq.http.model.ResponseClass;
import com.hjq.http.request.BaseRequest;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/* loaded from: classes4.dex */
public abstract class BaseRequest<T extends BaseRequest<?>> {
    private CallProxy mCallProxy;
    private long mDelayMillis;
    private final LifecycleOwner mLifecycleOwner;
    private IRequestApi mRequestApi;
    private String mTag;
    private IRequestHandler mRequestHandler = EasyConfig.getInstance().getHandler();
    private IRequestHost mRequestHost = EasyConfig.getInstance().getServer();
    private IRequestPath mRequestPath = EasyConfig.getInstance().getServer();
    private IRequestClient mRequestClient = EasyConfig.getInstance().getServer();
    private IRequestType mRequestType = EasyConfig.getInstance().getServer();
    private IRequestCache mRequestCache = EasyConfig.getInstance().getServer();

    /* renamed from: com.hjq.http.request.BaseRequest$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$hjq$http$model$BodyType;

        static {
            int[] iArr = new int[BodyType.values().length];
            $SwitchMap$com$hjq$http$model$BodyType = iArr;
            try {
                iArr[BodyType.FORM.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$hjq$http$model$BodyType[BodyType.JSON.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    public BaseRequest(LifecycleOwner lifecycleOwner) {
        this.mLifecycleOwner = lifecycleOwner;
        tag(lifecycleOwner);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$request$0(StackTraceElement[] stackTraceElementArr, OnHttpListener onHttpListener) {
        if (!HttpLifecycleManager.isLifecycleActive(this.mLifecycleOwner)) {
            EasyLog.print("宿主已被销毁，请求无法进行");
            return;
        }
        EasyLog.print(stackTraceElementArr);
        this.mCallProxy = new CallProxy(createCall());
        new NormalCallback(this).setListener(onHttpListener).setCall(this.mCallProxy).start();
    }

    public T api(Class<? extends IRequestApi> cls) {
        try {
            return (T) api(cls.newInstance());
        } catch (IllegalAccessException e2) {
            throw new RuntimeException(e2);
        } catch (InstantiationException e3) {
            throw new RuntimeException(e3);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public T cancel() {
        CallProxy callProxy = this.mCallProxy;
        if (callProxy != null) {
            callProxy.cancel();
        }
        return this;
    }

    public Call createCall() throws IllegalAccessException, SecurityException, IllegalArgumentException {
        Object obj;
        HttpRename httpRename;
        String strValue;
        BodyType bodyType;
        BodyType type = this.mRequestType.getType();
        HttpParams httpParams = new HttpParams();
        HttpHeaders httpHeaders = new HttpHeaders();
        ArrayList<Field> arrayList = new ArrayList();
        Class<?> superclass = this.mRequestApi.getClass();
        do {
            arrayList.addAll(0, Arrays.asList(superclass.getDeclaredFields()));
            superclass = superclass.getSuperclass();
            if (superclass == null) {
                break;
            }
        } while (!Object.class.equals(superclass));
        httpParams.setMultipart(EasyUtils.isMultipart(arrayList));
        BodyType bodyType2 = (!httpParams.isMultipart() || type == (bodyType = BodyType.FORM)) ? type : bodyType;
        for (Field field : arrayList) {
            field.setAccessible(true);
            try {
                obj = field.get(this.mRequestApi);
                httpRename = (HttpRename) field.getAnnotation(HttpRename.class);
            } catch (IllegalAccessException e2) {
                EasyLog.print(e2);
            }
            if (httpRename != null) {
                strValue = httpRename.value();
            } else {
                strValue = field.getName();
                if (!strValue.matches("this\\$\\d+") && !"Companion".equals(strValue)) {
                }
            }
            if (field.isAnnotationPresent(HttpIgnore.class)) {
                if (field.isAnnotationPresent(HttpHeader.class)) {
                    httpHeaders.remove(strValue);
                } else {
                    httpParams.remove(strValue);
                }
            } else if (!EasyUtils.isEmpty(obj)) {
                if (!field.isAnnotationPresent(HttpHeader.class)) {
                    int i2 = AnonymousClass1.$SwitchMap$com$hjq$http$model$BodyType[bodyType2.ordinal()];
                    if (i2 != 1) {
                        if (i2 == 2) {
                            if (obj instanceof List) {
                                httpParams.put(strValue, EasyUtils.listToJsonArray((List) obj));
                            } else if (obj instanceof Map) {
                                httpParams.put(strValue, EasyUtils.mapToJsonObject((Map) obj));
                            } else if (EasyUtils.isBeanType(obj)) {
                                httpParams.put(strValue, EasyUtils.mapToJsonObject(EasyUtils.beanToHashMap(obj)));
                            } else {
                                httpParams.put(strValue, obj);
                            }
                        }
                    } else if (obj instanceof Map) {
                        Map map = (Map) obj;
                        for (Object obj2 : map.keySet()) {
                            if (obj2 != null && map.get(obj2) != null) {
                                httpParams.put(String.valueOf(obj2), map.get(obj2));
                            }
                        }
                    } else {
                        httpParams.put(strValue, obj);
                    }
                } else if (obj instanceof Map) {
                    Map map2 = (Map) obj;
                    for (Object obj3 : map2.keySet()) {
                        if (obj3 != null && map2.get(obj3) != null) {
                            httpHeaders.put(String.valueOf(obj3), String.valueOf(map2.get(obj3)));
                        }
                    }
                } else {
                    httpHeaders.put(strValue, String.valueOf(obj));
                }
            }
        }
        String str = this.mRequestHost.getHost() + this.mRequestPath.getPath() + this.mRequestApi.getApi();
        IRequestInterceptor interceptor = EasyConfig.getInstance().getInterceptor();
        if (interceptor != null) {
            interceptor.interceptArguments(this.mRequestApi, httpParams, httpHeaders);
        }
        Request requestCreateRequest = createRequest(str, this.mTag, httpParams, httpHeaders, bodyType2);
        if (requestCreateRequest != null) {
            return this.mRequestClient.getClient().newCall(requestCreateRequest);
        }
        throw new NullPointerException("The request object cannot be empty");
    }

    public abstract Request createRequest(String str, String str2, HttpParams httpParams, HttpHeaders httpHeaders, BodyType bodyType);

    public T delay(long j2, TimeUnit timeUnit) {
        return (T) delay(timeUnit.toMillis(j2));
    }

    public <Bean> Bean execute(ResponseClass<Bean> responseClass) throws Exception {
        long j2 = this.mDelayMillis;
        if (j2 > 0) {
            EasyLog.print("RequestDelay", String.valueOf(j2));
            Thread.sleep(this.mDelayMillis);
        }
        if (!HttpLifecycleManager.isLifecycleActive(this.mLifecycleOwner)) {
            EasyLog.print("宿主已被销毁，请求无法进行");
            throw new IllegalStateException("The host has been destroyed and the request cannot proceed");
        }
        EasyLog.print(new Throwable().getStackTrace());
        Type reflectType = EasyUtils.getReflectType(responseClass);
        this.mCallProxy = new CallProxy(createCall());
        CacheMode mode = getRequestCache().getMode();
        if (mode == CacheMode.USE_CACHE_ONLY || mode == CacheMode.USE_CACHE_FIRST) {
            try {
                Bean bean = (Bean) this.mRequestHandler.readCache(this.mLifecycleOwner, this.mRequestApi, reflectType);
                EasyLog.print("ReadCache result：" + bean);
                if (mode == CacheMode.USE_CACHE_FIRST) {
                    new NormalCallback(this).setCall(this.mCallProxy).start();
                }
                if (bean != null) {
                    return bean;
                }
            } catch (Throwable th) {
                EasyLog.print("ReadCache error");
                EasyLog.print(th);
            }
        }
        try {
            Response responseExecute = this.mCallProxy.execute();
            Bean bean2 = (Bean) this.mRequestHandler.requestSucceed(this.mLifecycleOwner, this.mRequestApi, responseExecute, reflectType);
            if (mode == CacheMode.USE_CACHE_ONLY) {
                try {
                    EasyLog.print("WriteCache result：" + this.mRequestHandler.writeCache(this.mLifecycleOwner, this.mRequestApi, responseExecute, bean2));
                } catch (Throwable th2) {
                    EasyLog.print("WriteCache error");
                    EasyLog.print(th2);
                }
            }
            return bean2;
        } catch (Exception e2) {
            if ((e2 instanceof IOException) && mode == CacheMode.USE_CACHE_AFTER_FAILURE) {
                try {
                    Bean bean3 = (Bean) this.mRequestHandler.readCache(this.mLifecycleOwner, this.mRequestApi, reflectType);
                    EasyLog.print("ReadCache result：" + bean3);
                    if (bean3 != null) {
                        return bean3;
                    }
                } catch (Throwable th3) {
                    EasyLog.print("ReadCache error");
                    EasyLog.print(th3);
                }
            }
            throw this.mRequestHandler.requestFail(this.mLifecycleOwner, this.mRequestApi, e2);
        }
    }

    public long getDelayMillis() {
        return this.mDelayMillis;
    }

    public LifecycleOwner getLifecycleOwner() {
        return this.mLifecycleOwner;
    }

    public IRequestApi getRequestApi() {
        return this.mRequestApi;
    }

    public IRequestCache getRequestCache() {
        return this.mRequestCache;
    }

    public IRequestHandler getRequestHandler() {
        return this.mRequestHandler;
    }

    public abstract String getRequestMethod();

    /* JADX WARN: Multi-variable type inference failed */
    public T handler(IRequestHandler iRequestHandler) {
        this.mRequestHandler = iRequestHandler;
        return this;
    }

    public void request(final OnHttpListener<?> onHttpListener) {
        long j2 = this.mDelayMillis;
        if (j2 > 0) {
            EasyLog.print("RequestDelay", String.valueOf(j2));
        }
        final StackTraceElement[] stackTrace = new Throwable().getStackTrace();
        EasyUtils.postDelayed(new Runnable() { // from class: com.hjq.http.request.a
            @Override // java.lang.Runnable
            public final void run() {
                this.f7277c.lambda$request$0(stackTrace, onHttpListener);
            }
        }, this.mDelayMillis);
    }

    public T server(Class<? extends IRequestServer> cls) {
        try {
            return (T) server(cls.newInstance());
        } catch (IllegalAccessException e2) {
            throw new RuntimeException(e2);
        } catch (InstantiationException e3) {
            throw new RuntimeException(e3);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public T tag(Object obj) {
        return obj != null ? (T) tag(String.valueOf(obj)) : this;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public T delay(long j2) {
        this.mDelayMillis = j2;
        return this;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public T tag(String str) {
        this.mTag = str;
        return this;
    }

    public T api(String str) {
        return (T) api(new RequestApi(str));
    }

    public T server(String str) {
        return (T) server(new RequestServer(str));
    }

    /* JADX WARN: Multi-variable type inference failed */
    public T api(IRequestApi iRequestApi) {
        this.mRequestApi = iRequestApi;
        if (iRequestApi instanceof IRequestHost) {
            this.mRequestHost = (IRequestHost) iRequestApi;
        }
        if (iRequestApi instanceof IRequestPath) {
            this.mRequestPath = (IRequestPath) iRequestApi;
        }
        if (iRequestApi instanceof IRequestClient) {
            this.mRequestClient = (IRequestClient) iRequestApi;
        }
        if (iRequestApi instanceof IRequestType) {
            this.mRequestType = (IRequestType) iRequestApi;
        }
        if (iRequestApi instanceof IRequestCache) {
            this.mRequestCache = (IRequestCache) iRequestApi;
        }
        if (iRequestApi instanceof IRequestHandler) {
            this.mRequestHandler = (IRequestHandler) iRequestApi;
        }
        return this;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public T server(IRequestServer iRequestServer) {
        this.mRequestHost = iRequestServer;
        this.mRequestPath = iRequestServer;
        this.mRequestClient = iRequestServer;
        this.mRequestType = iRequestServer;
        this.mRequestCache = iRequestServer;
        return this;
    }
}
