package com.nirvana.tools.cache;

/* loaded from: classes4.dex */
public class RepositoryTemplate {
    private int cacheVersion;
    private boolean needEncrypt;

    public RepositoryTemplate(int i2, boolean z2) {
        this.cacheVersion = i2;
        this.needEncrypt = z2;
    }

    public int getCacheVersion() {
        return this.cacheVersion;
    }

    public boolean isNeedEncrypt() {
        return this.needEncrypt;
    }

    public void setCacheVersion(int i2) {
        this.cacheVersion = i2;
    }

    public void setNeedEncrypt(boolean z2) {
        this.needEncrypt = z2;
    }
}
