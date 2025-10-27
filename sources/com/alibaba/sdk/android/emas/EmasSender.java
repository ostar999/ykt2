package com.alibaba.sdk.android.emas;

import android.app.Application;
import android.text.TextUtils;
import com.alibaba.sdk.android.emas.g;
import com.alibaba.sdk.android.tbrest.utils.LogUtil;
import java.nio.charset.Charset;
import java.util.Map;

/* loaded from: classes2.dex */
public class EmasSender {
    private static final String TAG = "EmasSender";
    private boolean background;
    private a mCacheManager;
    private c mDiskCacheManager;
    private h mSendManager;
    private int singleLogLimitCapacity;

    public static final class Builder {
        private String appId;
        private String appKey;
        private String appSecret;
        private String appVersion;
        private String channel;
        private Application context;
        private String host;
        private boolean openHttp;
        private PreSendHandler preSendHandler;
        private String userNick;
        private String businessKey = "common";
        private boolean cache = true;
        private int cacheLimitCount = 20;
        private int singleLogLimitCapacity = 204800;
        private int cacheLimitCapacity = 2097152;
        private boolean diskCache = true;
        private int diskCacheLimitCount = 50;
        private int diskCacheLimitCapacity = 104857600;
        private int diskCacheLimitDay = 5;

        public Builder appId(String str) {
            this.appId = str;
            return this;
        }

        public Builder appKey(String str) {
            this.appKey = str;
            return this;
        }

        public Builder appSecret(String str) {
            this.appSecret = str;
            return this;
        }

        public Builder appVersion(String str) {
            this.appVersion = str;
            return this;
        }

        public EmasSender build() {
            return new EmasSender(this);
        }

        public Builder businessKey(String str) {
            this.businessKey = str;
            return this;
        }

        public Builder cache(boolean z2) {
            this.cache = z2;
            return this;
        }

        public Builder cacheLimitCount(int i2) {
            this.cacheLimitCount = i2;
            return this;
        }

        public Builder channel(String str) {
            this.channel = str;
            return this;
        }

        public Builder context(Application application) {
            this.context = application;
            return this;
        }

        public Builder diskCache(boolean z2) {
            this.diskCache = z2;
            return this;
        }

        public Builder diskCacheLimitCount(int i2) {
            this.diskCacheLimitCount = i2;
            return this;
        }

        public Builder diskCacheLimitDay(int i2) {
            this.diskCacheLimitDay = i2;
            return this;
        }

        public Builder host(String str) {
            this.host = str;
            return this;
        }

        public Builder openHttp(boolean z2) {
            this.openHttp = z2;
            return this;
        }

        public Builder preSendHandler(PreSendHandler preSendHandler) {
            this.preSendHandler = preSendHandler;
            return this;
        }

        public Builder userNick(String str) {
            this.userNick = str;
            return this;
        }
    }

    public void changeHost(String str) {
        this.mSendManager.setHost(str);
    }

    public boolean isBackground() {
        return this.background;
    }

    public void openHttp(boolean z2) {
        this.mSendManager.openHttp(z2);
    }

    public void send(long j2, String str, int i2, String str2, String str3, String str4, Map<String, String> map) {
        if (TextUtils.isEmpty(this.mSendManager.m29a().getAppKey()) || TextUtils.isEmpty(this.mSendManager.m29a().getChangeHost())) {
            LogUtil.d("EmasSender send failed. appkey or host is empty.");
            return;
        }
        String strA = com.alibaba.sdk.android.tbrest.rest.e.a(this.mSendManager.m29a(), this.mSendManager.m29a().getAppKey(), j2, str, i2, str2, str3, str4, map);
        if (TextUtils.isEmpty(strA)) {
            LogUtil.d("EmasSender send failed. build data is null.");
            return;
        }
        int length = strA.getBytes(Charset.forName("UTF-8")).length;
        if (length > this.singleLogLimitCapacity) {
            LogUtil.d("EmasSender send failed. build data is exceed limit. current length: " + length);
            return;
        }
        e eVar = new e(String.valueOf(i2), strA, j2);
        a aVar = this.mCacheManager;
        if (aVar != null) {
            aVar.add(eVar);
        } else {
            this.mSendManager.b(eVar);
        }
    }

    public void setUserNick(String str) {
        this.mSendManager.setUserNick(str);
    }

    private EmasSender(Builder builder) {
        this.background = false;
        this.singleLogLimitCapacity = builder.singleLogLimitCapacity;
        if (builder.diskCache) {
            c cVar = new c(builder.context, builder.host, builder.appKey, builder.businessKey);
            this.mDiskCacheManager = cVar;
            cVar.a(builder.diskCacheLimitCount, builder.diskCacheLimitCapacity, builder.diskCacheLimitDay);
        }
        h hVar = new h(this, this.mDiskCacheManager);
        this.mSendManager = hVar;
        hVar.init(builder.context, builder.appId, builder.appKey, builder.appVersion, builder.channel, builder.userNick);
        this.mSendManager.setHost(builder.host);
        this.mSendManager.a(builder.appSecret);
        this.mSendManager.openHttp(builder.openHttp);
        this.mSendManager.a(builder.preSendHandler);
        this.mSendManager.d();
        if (builder.cache) {
            this.mCacheManager = new a(this.mSendManager, builder.cacheLimitCount, builder.cacheLimitCapacity);
        }
        if (this.mCacheManager == null && this.mDiskCacheManager == null) {
            return;
        }
        g gVar = new g();
        gVar.a(new g.a() { // from class: com.alibaba.sdk.android.emas.EmasSender.1
            @Override // com.alibaba.sdk.android.emas.g.a
            public void b() {
                EmasSender.this.background = false;
            }

            @Override // com.alibaba.sdk.android.emas.g.a
            public void c() {
                EmasSender.this.background = true;
                EmasSender.this.mCacheManager.flush();
            }
        });
        builder.context.registerActivityLifecycleCallbacks(gVar);
    }
}
