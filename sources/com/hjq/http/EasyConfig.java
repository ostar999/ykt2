package com.hjq.http;

import com.google.android.exoplayer2.ExoPlayer;
import com.hjq.http.config.ILogStrategy;
import com.hjq.http.config.IRequestHandler;
import com.hjq.http.config.IRequestInterceptor;
import com.hjq.http.config.IRequestServer;
import com.hjq.http.config.LogStrategy;
import com.hjq.http.config.RequestServer;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import okhttp3.OkHttpClient;

/* loaded from: classes4.dex */
public final class EasyConfig {
    private static volatile EasyConfig sConfig;
    private OkHttpClient mClient;
    private IRequestHandler mHandler;
    private IRequestInterceptor mInterceptor;
    private ILogStrategy mLogStrategy;
    private int mRetryCount;
    private IRequestServer mServer;
    private boolean mLogEnabled = true;
    private String mLogTag = "EasyHttp";
    private long mRetryTime = ExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS;
    private HashMap<String, Object> mParams = new HashMap<>();
    private HashMap<String, String> mHeaders = new HashMap<>();

    private EasyConfig(OkHttpClient okHttpClient) {
        this.mClient = okHttpClient;
    }

    public static EasyConfig getInstance() {
        if (sConfig != null) {
            return sConfig;
        }
        throw new IllegalStateException("You haven't initialized the configuration yet");
    }

    private static void setInstance(EasyConfig easyConfig) {
        sConfig = easyConfig;
    }

    public static EasyConfig with(OkHttpClient okHttpClient) {
        return new EasyConfig(okHttpClient);
    }

    public EasyConfig addHeader(String str, String str2) {
        if (str != null && str2 != null) {
            this.mHeaders.put(str, str2);
        }
        return this;
    }

    public EasyConfig addParam(String str, String str2) {
        if (str != null && str2 != null) {
            this.mParams.put(str, str2);
        }
        return this;
    }

    public OkHttpClient getClient() {
        return this.mClient;
    }

    public IRequestHandler getHandler() {
        return this.mHandler;
    }

    public HashMap<String, String> getHeaders() {
        return this.mHeaders;
    }

    public IRequestInterceptor getInterceptor() {
        return this.mInterceptor;
    }

    public ILogStrategy getLogStrategy() {
        return this.mLogStrategy;
    }

    public String getLogTag() {
        return this.mLogTag;
    }

    public HashMap<String, Object> getParams() {
        return this.mParams;
    }

    public int getRetryCount() {
        return this.mRetryCount;
    }

    public long getRetryTime() {
        return this.mRetryTime;
    }

    public IRequestServer getServer() {
        return this.mServer;
    }

    public void into() {
        if (this.mClient == null) {
            throw new IllegalArgumentException("The OkHttp client object cannot be empty");
        }
        if (this.mServer == null) {
            throw new IllegalArgumentException("The host configuration cannot be empty");
        }
        if (this.mHandler == null) {
            throw new IllegalArgumentException("The object being processed by the request cannot be empty");
        }
        try {
            new URL(this.mServer.getHost() + this.mServer.getPath());
            if (this.mLogStrategy == null) {
                this.mLogStrategy = new LogStrategy();
            }
            setInstance(this);
        } catch (MalformedURLException unused) {
            throw new IllegalArgumentException("The configured host path url address is not correct");
        }
    }

    public boolean isLogEnabled() {
        return this.mLogEnabled && this.mLogStrategy != null;
    }

    public EasyConfig removeHeader(String str) {
        if (str != null) {
            this.mHeaders.remove(str);
        }
        return this;
    }

    public EasyConfig removeParam(String str) {
        if (str != null) {
            this.mParams.remove(str);
        }
        return this;
    }

    public EasyConfig setClient(OkHttpClient okHttpClient) {
        this.mClient = okHttpClient;
        if (okHttpClient != null) {
            return this;
        }
        throw new IllegalArgumentException("The OkHttp client object cannot be empty");
    }

    public EasyConfig setHandler(IRequestHandler iRequestHandler) {
        this.mHandler = iRequestHandler;
        return this;
    }

    public EasyConfig setHeaders(HashMap<String, String> map) {
        if (map == null) {
            map = new HashMap<>();
        }
        this.mHeaders = map;
        return this;
    }

    public EasyConfig setInterceptor(IRequestInterceptor iRequestInterceptor) {
        this.mInterceptor = iRequestInterceptor;
        return this;
    }

    public EasyConfig setLogEnabled(boolean z2) {
        this.mLogEnabled = z2;
        return this;
    }

    public EasyConfig setLogStrategy(ILogStrategy iLogStrategy) {
        this.mLogStrategy = iLogStrategy;
        return this;
    }

    public EasyConfig setLogTag(String str) {
        this.mLogTag = str;
        return this;
    }

    public EasyConfig setParams(HashMap<String, Object> map) {
        if (map == null) {
            map = new HashMap<>();
        }
        this.mParams = map;
        return this;
    }

    public EasyConfig setRetryCount(int i2) {
        if (i2 < 0) {
            throw new IllegalArgumentException("The number of retries must be greater than 0");
        }
        this.mRetryCount = i2;
        return this;
    }

    public EasyConfig setRetryTime(long j2) {
        if (j2 < 0) {
            throw new IllegalArgumentException("The retry time must be greater than 0");
        }
        this.mRetryTime = j2;
        return this;
    }

    public EasyConfig setServer(String str) {
        return setServer(new RequestServer(str));
    }

    public EasyConfig setServer(IRequestServer iRequestServer) {
        this.mServer = iRequestServer;
        return this;
    }
}
