package com.google.android.exoplayer2.drm;

import android.os.ConditionVariable;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Pair;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.drm.DefaultDrmSessionManager;
import com.google.android.exoplayer2.drm.DrmInitData;
import com.google.android.exoplayer2.drm.DrmSession;
import com.google.android.exoplayer2.drm.DrmSessionEventListener;
import com.google.android.exoplayer2.drm.ExoMediaDrm;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.google.android.exoplayer2.util.Assertions;
import java.util.Map;
import java.util.UUID;

@RequiresApi(18)
/* loaded from: classes3.dex */
public final class OfflineLicenseHelper {
    private static final Format FORMAT_WITH_EMPTY_DRM_INIT_DATA = new Format.Builder().setDrmInitData(new DrmInitData(new DrmInitData.SchemeData[0])).build();
    private final ConditionVariable conditionVariable;
    private final DefaultDrmSessionManager drmSessionManager;
    private final DrmSessionEventListener.EventDispatcher eventDispatcher;
    private final HandlerThread handlerThread;

    @Deprecated
    public OfflineLicenseHelper(UUID uuid, ExoMediaDrm.Provider provider, MediaDrmCallback mediaDrmCallback, @Nullable Map<String, String> map, DrmSessionEventListener.EventDispatcher eventDispatcher) {
        this(new DefaultDrmSessionManager.Builder().setUuidAndExoMediaDrmProvider(uuid, provider).setKeyRequestParameters(map).build(mediaDrmCallback), eventDispatcher);
    }

    private byte[] blockingKeyRequest(int i2, @Nullable byte[] bArr, Format format) throws DrmSession.DrmSessionException {
        this.drmSessionManager.prepare();
        DrmSession drmSessionOpenBlockingKeyRequest = openBlockingKeyRequest(i2, bArr, format);
        DrmSession.DrmSessionException error = drmSessionOpenBlockingKeyRequest.getError();
        byte[] offlineLicenseKeySetId = drmSessionOpenBlockingKeyRequest.getOfflineLicenseKeySetId();
        drmSessionOpenBlockingKeyRequest.release(this.eventDispatcher);
        this.drmSessionManager.release();
        if (error == null) {
            return (byte[]) Assertions.checkNotNull(offlineLicenseKeySetId);
        }
        throw error;
    }

    public static OfflineLicenseHelper newWidevineInstance(String str, HttpDataSource.Factory factory, DrmSessionEventListener.EventDispatcher eventDispatcher) {
        return newWidevineInstance(str, false, factory, eventDispatcher);
    }

    private DrmSession openBlockingKeyRequest(int i2, @Nullable byte[] bArr, Format format) {
        Assertions.checkNotNull(format.drmInitData);
        this.drmSessionManager.setMode(i2, bArr);
        this.conditionVariable.close();
        DrmSession drmSessionAcquireSession = this.drmSessionManager.acquireSession(this.handlerThread.getLooper(), this.eventDispatcher, format);
        this.conditionVariable.block();
        return (DrmSession) Assertions.checkNotNull(drmSessionAcquireSession);
    }

    public synchronized byte[] downloadLicense(Format format) throws DrmSession.DrmSessionException {
        Assertions.checkArgument(format.drmInitData != null);
        return blockingKeyRequest(2, null, format);
    }

    public synchronized Pair<Long, Long> getLicenseDurationRemainingSec(byte[] bArr) throws DrmSession.DrmSessionException {
        Assertions.checkNotNull(bArr);
        this.drmSessionManager.prepare();
        DrmSession drmSessionOpenBlockingKeyRequest = openBlockingKeyRequest(1, bArr, FORMAT_WITH_EMPTY_DRM_INIT_DATA);
        DrmSession.DrmSessionException error = drmSessionOpenBlockingKeyRequest.getError();
        Pair<Long, Long> licenseDurationRemainingSec = WidevineUtil.getLicenseDurationRemainingSec(drmSessionOpenBlockingKeyRequest);
        drmSessionOpenBlockingKeyRequest.release(this.eventDispatcher);
        this.drmSessionManager.release();
        if (error == null) {
            return (Pair) Assertions.checkNotNull(licenseDurationRemainingSec);
        }
        if (!(error.getCause() instanceof KeysExpiredException)) {
            throw error;
        }
        return Pair.create(0L, 0L);
    }

    public void release() {
        this.handlerThread.quit();
    }

    public synchronized void releaseLicense(byte[] bArr) throws DrmSession.DrmSessionException {
        Assertions.checkNotNull(bArr);
        blockingKeyRequest(3, bArr, FORMAT_WITH_EMPTY_DRM_INIT_DATA);
    }

    public synchronized byte[] renewLicense(byte[] bArr) throws DrmSession.DrmSessionException {
        Assertions.checkNotNull(bArr);
        return blockingKeyRequest(2, bArr, FORMAT_WITH_EMPTY_DRM_INIT_DATA);
    }

    public static OfflineLicenseHelper newWidevineInstance(String str, boolean z2, HttpDataSource.Factory factory, DrmSessionEventListener.EventDispatcher eventDispatcher) {
        return newWidevineInstance(str, z2, factory, null, eventDispatcher);
    }

    public static OfflineLicenseHelper newWidevineInstance(String str, boolean z2, HttpDataSource.Factory factory, @Nullable Map<String, String> map, DrmSessionEventListener.EventDispatcher eventDispatcher) {
        return new OfflineLicenseHelper(new DefaultDrmSessionManager.Builder().setKeyRequestParameters(map).build(new HttpMediaDrmCallback(str, z2, factory)), eventDispatcher);
    }

    public OfflineLicenseHelper(DefaultDrmSessionManager defaultDrmSessionManager, DrmSessionEventListener.EventDispatcher eventDispatcher) {
        this.drmSessionManager = defaultDrmSessionManager;
        this.eventDispatcher = eventDispatcher;
        HandlerThread handlerThread = new HandlerThread("ExoPlayer:OfflineLicenseHelper");
        this.handlerThread = handlerThread;
        handlerThread.start();
        this.conditionVariable = new ConditionVariable();
        eventDispatcher.addEventListener(new Handler(handlerThread.getLooper()), new DrmSessionEventListener() { // from class: com.google.android.exoplayer2.drm.OfflineLicenseHelper.1
            @Override // com.google.android.exoplayer2.drm.DrmSessionEventListener
            public void onDrmKeysLoaded(int i2, @Nullable MediaSource.MediaPeriodId mediaPeriodId) {
                OfflineLicenseHelper.this.conditionVariable.open();
            }

            @Override // com.google.android.exoplayer2.drm.DrmSessionEventListener
            public void onDrmKeysRemoved(int i2, @Nullable MediaSource.MediaPeriodId mediaPeriodId) {
                OfflineLicenseHelper.this.conditionVariable.open();
            }

            @Override // com.google.android.exoplayer2.drm.DrmSessionEventListener
            public void onDrmKeysRestored(int i2, @Nullable MediaSource.MediaPeriodId mediaPeriodId) {
                OfflineLicenseHelper.this.conditionVariable.open();
            }

            @Override // com.google.android.exoplayer2.drm.DrmSessionEventListener
            public /* synthetic */ void onDrmSessionAcquired(int i2, MediaSource.MediaPeriodId mediaPeriodId) {
                j.d(this, i2, mediaPeriodId);
            }

            @Override // com.google.android.exoplayer2.drm.DrmSessionEventListener
            public /* synthetic */ void onDrmSessionAcquired(int i2, MediaSource.MediaPeriodId mediaPeriodId, int i3) {
                j.e(this, i2, mediaPeriodId, i3);
            }

            @Override // com.google.android.exoplayer2.drm.DrmSessionEventListener
            public void onDrmSessionManagerError(int i2, @Nullable MediaSource.MediaPeriodId mediaPeriodId, Exception exc) {
                OfflineLicenseHelper.this.conditionVariable.open();
            }

            @Override // com.google.android.exoplayer2.drm.DrmSessionEventListener
            public /* synthetic */ void onDrmSessionReleased(int i2, MediaSource.MediaPeriodId mediaPeriodId) {
                j.g(this, i2, mediaPeriodId);
            }
        });
    }
}
