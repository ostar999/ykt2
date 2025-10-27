package com.google.android.exoplayer2.drm;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.decoder.CryptoConfig;
import com.google.android.exoplayer2.drm.DrmSessionEventListener;
import java.io.IOException;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Map;
import java.util.UUID;

/* loaded from: classes3.dex */
public interface DrmSession {
    public static final int STATE_ERROR = 1;
    public static final int STATE_OPENED = 3;
    public static final int STATE_OPENED_WITH_KEYS = 4;
    public static final int STATE_OPENING = 2;
    public static final int STATE_RELEASED = 0;

    public static class DrmSessionException extends IOException {
        public final int errorCode;

        public DrmSessionException(Throwable th, int i2) {
            super(th);
            this.errorCode = i2;
        }
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface State {
    }

    void acquire(@Nullable DrmSessionEventListener.EventDispatcher eventDispatcher);

    @Nullable
    CryptoConfig getCryptoConfig();

    @Nullable
    DrmSessionException getError();

    @Nullable
    byte[] getOfflineLicenseKeySetId();

    UUID getSchemeUuid();

    int getState();

    boolean playClearSamplesWithoutKeys();

    @Nullable
    Map<String, String> queryKeyStatus();

    void release(@Nullable DrmSessionEventListener.EventDispatcher eventDispatcher);

    boolean requiresSecureDecoder(String str);
}
