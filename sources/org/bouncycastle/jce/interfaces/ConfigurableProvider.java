package org.bouncycastle.jce.interfaces;

/* loaded from: classes9.dex */
public interface ConfigurableProvider {
    public static final String EC_IMPLICITLY_CA = "ecImplicitlyCa";
    public static final String THREAD_LOCAL_EC_IMPLICITLY_CA = "threadLocalEcImplicitlyCa";

    void setParameter(String str, Object obj);
}
