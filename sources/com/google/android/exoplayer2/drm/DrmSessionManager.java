package com.google.android.exoplayer2.drm;

import android.os.Looper;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.drm.DrmSession;
import com.google.android.exoplayer2.drm.DrmSessionEventListener;

/* loaded from: classes3.dex */
public interface DrmSessionManager {
    public static final DrmSessionManager DRM_UNSUPPORTED;

    @Deprecated
    public static final DrmSessionManager DUMMY;

    public interface DrmSessionReference {
        public static final DrmSessionReference EMPTY = new DrmSessionReference() { // from class: com.google.android.exoplayer2.drm.r
            @Override // com.google.android.exoplayer2.drm.DrmSessionManager.DrmSessionReference
            public final void release() {
                s.a();
            }
        };

        void release();
    }

    static {
        DrmSessionManager drmSessionManager = new DrmSessionManager() { // from class: com.google.android.exoplayer2.drm.DrmSessionManager.1
            @Override // com.google.android.exoplayer2.drm.DrmSessionManager
            @Nullable
            public DrmSession acquireSession(Looper looper, @Nullable DrmSessionEventListener.EventDispatcher eventDispatcher, Format format) {
                if (format.drmInitData == null) {
                    return null;
                }
                return new ErrorStateDrmSession(new DrmSession.DrmSessionException(new UnsupportedDrmException(1), 6001));
            }

            @Override // com.google.android.exoplayer2.drm.DrmSessionManager
            public int getCryptoType(Format format) {
                return format.drmInitData != null ? 1 : 0;
            }

            @Override // com.google.android.exoplayer2.drm.DrmSessionManager
            public /* synthetic */ DrmSessionReference preacquireSession(Looper looper, DrmSessionEventListener.EventDispatcher eventDispatcher, Format format) {
                return q.a(this, looper, eventDispatcher, format);
            }

            @Override // com.google.android.exoplayer2.drm.DrmSessionManager
            public /* synthetic */ void prepare() {
                q.b(this);
            }

            @Override // com.google.android.exoplayer2.drm.DrmSessionManager
            public /* synthetic */ void release() {
                q.c(this);
            }
        };
        DRM_UNSUPPORTED = drmSessionManager;
        DUMMY = drmSessionManager;
    }

    @Nullable
    DrmSession acquireSession(Looper looper, @Nullable DrmSessionEventListener.EventDispatcher eventDispatcher, Format format);

    int getCryptoType(Format format);

    DrmSessionReference preacquireSession(Looper looper, @Nullable DrmSessionEventListener.EventDispatcher eventDispatcher, Format format);

    void prepare();

    void release();
}
