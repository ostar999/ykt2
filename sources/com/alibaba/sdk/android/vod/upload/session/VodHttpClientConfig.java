package com.alibaba.sdk.android.vod.upload.session;

/* loaded from: classes2.dex */
public class VodHttpClientConfig {
    private int connectionTimeout;
    private int maxRetryCount;
    private int socketTimeout;

    public static class Builder {
        int _MaxRetryCount = Integer.MAX_VALUE;
        int _ConnectionTimeout = 15000;
        int _SocketTimeout = 15000;

        public VodHttpClientConfig build() {
            return new VodHttpClientConfig(this);
        }

        public Builder setConnectionTimeout(int i2) {
            this._ConnectionTimeout = i2;
            return this;
        }

        public Builder setMaxRetryCount(int i2) {
            if (i2 > 0) {
                this._MaxRetryCount = i2;
                return this;
            }
            this._MaxRetryCount = 2;
            return this;
        }

        public Builder setSocketTimeout(int i2) {
            this._SocketTimeout = i2;
            return this;
        }
    }

    public VodHttpClientConfig(Builder builder) {
        this.maxRetryCount = Integer.MAX_VALUE;
        this.connectionTimeout = 15000;
        this.socketTimeout = 15000;
        this.maxRetryCount = builder._MaxRetryCount;
        this.connectionTimeout = builder._ConnectionTimeout;
        this.socketTimeout = builder._SocketTimeout;
    }

    public static Builder builder() {
        return new Builder();
    }

    public int getConnectionTimeout() {
        return this.connectionTimeout;
    }

    public int getMaxRetryCount() {
        return this.maxRetryCount;
    }

    public int getSocketTimeout() {
        return this.socketTimeout;
    }
}
