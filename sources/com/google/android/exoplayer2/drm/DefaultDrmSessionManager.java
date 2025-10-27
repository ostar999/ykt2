package com.google.android.exoplayer2.drm;

import android.annotation.SuppressLint;
import android.media.ResourceBusyException;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.drm.DefaultDrmSession;
import com.google.android.exoplayer2.drm.DrmInitData;
import com.google.android.exoplayer2.drm.DrmSession;
import com.google.android.exoplayer2.drm.DrmSessionEventListener;
import com.google.android.exoplayer2.drm.DrmSessionManager;
import com.google.android.exoplayer2.drm.ExoMediaDrm;
import com.google.android.exoplayer2.upstream.DefaultLoadErrorHandlingPolicy;
import com.google.android.exoplayer2.upstream.LoadErrorHandlingPolicy;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.google.common.collect.UnmodifiableIterator;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.checkerframework.checker.nullness.qual.EnsuresNonNull;

@RequiresApi(18)
/* loaded from: classes3.dex */
public class DefaultDrmSessionManager implements DrmSessionManager {
    public static final long DEFAULT_SESSION_KEEPALIVE_MS = 300000;
    public static final int INITIAL_DRM_REQUEST_RETRY_COUNT = 3;
    public static final int MODE_DOWNLOAD = 2;
    public static final int MODE_PLAYBACK = 0;
    public static final int MODE_QUERY = 1;
    public static final int MODE_RELEASE = 3;
    public static final String PLAYREADY_CUSTOM_DATA_KEY = "PRCustomData";
    private static final String TAG = "DefaultDrmSessionMgr";
    private final MediaDrmCallback callback;

    @Nullable
    private ExoMediaDrm exoMediaDrm;
    private final ExoMediaDrm.Provider exoMediaDrmProvider;
    private final Set<DefaultDrmSession> keepaliveSessions;
    private final HashMap<String, String> keyRequestParameters;
    private final LoadErrorHandlingPolicy loadErrorHandlingPolicy;

    @Nullable
    volatile MediaDrmHandler mediaDrmHandler;
    private int mode;
    private final boolean multiSession;

    @Nullable
    private DefaultDrmSession noMultiSessionDrmSession;

    @Nullable
    private byte[] offlineLicenseKeySetId;

    @Nullable
    private DefaultDrmSession placeholderDrmSession;
    private final boolean playClearSamplesWithoutKeys;
    private Handler playbackHandler;
    private Looper playbackLooper;
    private final Set<PreacquiredSessionReference> preacquiredSessionReferences;
    private int prepareCallsCount;
    private final ProvisioningManagerImpl provisioningManagerImpl;
    private final ReferenceCountListenerImpl referenceCountListener;
    private final long sessionKeepaliveMs;
    private final List<DefaultDrmSession> sessions;
    private final int[] useDrmSessionsForClearContentTrackTypes;
    private final UUID uuid;

    public static final class Builder {
        private boolean multiSession;
        private boolean playClearSamplesWithoutKeys;
        private final HashMap<String, String> keyRequestParameters = new HashMap<>();
        private UUID uuid = C.WIDEVINE_UUID;
        private ExoMediaDrm.Provider exoMediaDrmProvider = FrameworkMediaDrm.DEFAULT_PROVIDER;
        private LoadErrorHandlingPolicy loadErrorHandlingPolicy = new DefaultLoadErrorHandlingPolicy();
        private int[] useDrmSessionsForClearContentTrackTypes = new int[0];
        private long sessionKeepaliveMs = 300000;

        public DefaultDrmSessionManager build(MediaDrmCallback mediaDrmCallback) {
            return new DefaultDrmSessionManager(this.uuid, this.exoMediaDrmProvider, mediaDrmCallback, this.keyRequestParameters, this.multiSession, this.useDrmSessionsForClearContentTrackTypes, this.playClearSamplesWithoutKeys, this.loadErrorHandlingPolicy, this.sessionKeepaliveMs);
        }

        public Builder setKeyRequestParameters(@Nullable Map<String, String> map) {
            this.keyRequestParameters.clear();
            if (map != null) {
                this.keyRequestParameters.putAll(map);
            }
            return this;
        }

        public Builder setLoadErrorHandlingPolicy(LoadErrorHandlingPolicy loadErrorHandlingPolicy) {
            this.loadErrorHandlingPolicy = (LoadErrorHandlingPolicy) Assertions.checkNotNull(loadErrorHandlingPolicy);
            return this;
        }

        public Builder setMultiSession(boolean z2) {
            this.multiSession = z2;
            return this;
        }

        public Builder setPlayClearSamplesWithoutKeys(boolean z2) {
            this.playClearSamplesWithoutKeys = z2;
            return this;
        }

        public Builder setSessionKeepaliveMs(long j2) {
            Assertions.checkArgument(j2 > 0 || j2 == C.TIME_UNSET);
            this.sessionKeepaliveMs = j2;
            return this;
        }

        public Builder setUseDrmSessionsForClearContent(int... iArr) {
            for (int i2 : iArr) {
                boolean z2 = true;
                if (i2 != 2 && i2 != 1) {
                    z2 = false;
                }
                Assertions.checkArgument(z2);
            }
            this.useDrmSessionsForClearContentTrackTypes = (int[]) iArr.clone();
            return this;
        }

        public Builder setUuidAndExoMediaDrmProvider(UUID uuid, ExoMediaDrm.Provider provider) {
            this.uuid = (UUID) Assertions.checkNotNull(uuid);
            this.exoMediaDrmProvider = (ExoMediaDrm.Provider) Assertions.checkNotNull(provider);
            return this;
        }
    }

    public class MediaDrmEventListener implements ExoMediaDrm.OnEventListener {
        private MediaDrmEventListener() {
        }

        @Override // com.google.android.exoplayer2.drm.ExoMediaDrm.OnEventListener
        public void onEvent(ExoMediaDrm exoMediaDrm, @Nullable byte[] bArr, int i2, int i3, @Nullable byte[] bArr2) {
            ((MediaDrmHandler) Assertions.checkNotNull(DefaultDrmSessionManager.this.mediaDrmHandler)).obtainMessage(i2, bArr).sendToTarget();
        }
    }

    @SuppressLint({"HandlerLeak"})
    public class MediaDrmHandler extends Handler {
        public MediaDrmHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            byte[] bArr = (byte[]) message.obj;
            if (bArr == null) {
                return;
            }
            for (DefaultDrmSession defaultDrmSession : DefaultDrmSessionManager.this.sessions) {
                if (defaultDrmSession.hasSessionId(bArr)) {
                    defaultDrmSession.onMediaDrmEvent(message.what);
                    return;
                }
            }
        }
    }

    public static final class MissingSchemeDataException extends Exception {
        private MissingSchemeDataException(UUID uuid) {
            String strValueOf = String.valueOf(uuid);
            StringBuilder sb = new StringBuilder(strValueOf.length() + 29);
            sb.append("Media does not support uuid: ");
            sb.append(strValueOf);
            super(sb.toString());
        }
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface Mode {
    }

    public class PreacquiredSessionReference implements DrmSessionManager.DrmSessionReference {

        @Nullable
        private final DrmSessionEventListener.EventDispatcher eventDispatcher;
        private boolean isReleased;

        @Nullable
        private DrmSession session;

        public PreacquiredSessionReference(@Nullable DrmSessionEventListener.EventDispatcher eventDispatcher) {
            this.eventDispatcher = eventDispatcher;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$acquire$0(Format format) {
            if (DefaultDrmSessionManager.this.prepareCallsCount == 0 || this.isReleased) {
                return;
            }
            DefaultDrmSessionManager defaultDrmSessionManager = DefaultDrmSessionManager.this;
            this.session = defaultDrmSessionManager.acquireSession((Looper) Assertions.checkNotNull(defaultDrmSessionManager.playbackLooper), this.eventDispatcher, format, false);
            DefaultDrmSessionManager.this.preacquiredSessionReferences.add(this);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$release$1() {
            if (this.isReleased) {
                return;
            }
            DrmSession drmSession = this.session;
            if (drmSession != null) {
                drmSession.release(this.eventDispatcher);
            }
            DefaultDrmSessionManager.this.preacquiredSessionReferences.remove(this);
            this.isReleased = true;
        }

        public void acquire(final Format format) {
            ((Handler) Assertions.checkNotNull(DefaultDrmSessionManager.this.playbackHandler)).post(new Runnable() { // from class: com.google.android.exoplayer2.drm.g
                @Override // java.lang.Runnable
                public final void run() {
                    this.f6754c.lambda$acquire$0(format);
                }
            });
        }

        @Override // com.google.android.exoplayer2.drm.DrmSessionManager.DrmSessionReference
        public void release() {
            Util.postOrRun((Handler) Assertions.checkNotNull(DefaultDrmSessionManager.this.playbackHandler), new Runnable() { // from class: com.google.android.exoplayer2.drm.f
                @Override // java.lang.Runnable
                public final void run() {
                    this.f6753c.lambda$release$1();
                }
            });
        }
    }

    public class ProvisioningManagerImpl implements DefaultDrmSession.ProvisioningManager {

        @Nullable
        private DefaultDrmSession provisioningSession;
        private final Set<DefaultDrmSession> sessionsAwaitingProvisioning = new HashSet();

        public ProvisioningManagerImpl(DefaultDrmSessionManager defaultDrmSessionManager) {
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.google.android.exoplayer2.drm.DefaultDrmSession.ProvisioningManager
        public void onProvisionCompleted() {
            this.provisioningSession = null;
            ImmutableList immutableListCopyOf = ImmutableList.copyOf((Collection) this.sessionsAwaitingProvisioning);
            this.sessionsAwaitingProvisioning.clear();
            UnmodifiableIterator it = immutableListCopyOf.iterator();
            while (it.hasNext()) {
                ((DefaultDrmSession) it.next()).onProvisionCompleted();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.google.android.exoplayer2.drm.DefaultDrmSession.ProvisioningManager
        public void onProvisionError(Exception exc, boolean z2) {
            this.provisioningSession = null;
            ImmutableList immutableListCopyOf = ImmutableList.copyOf((Collection) this.sessionsAwaitingProvisioning);
            this.sessionsAwaitingProvisioning.clear();
            UnmodifiableIterator it = immutableListCopyOf.iterator();
            while (it.hasNext()) {
                ((DefaultDrmSession) it.next()).onProvisionError(exc, z2);
            }
        }

        public void onSessionFullyReleased(DefaultDrmSession defaultDrmSession) {
            this.sessionsAwaitingProvisioning.remove(defaultDrmSession);
            if (this.provisioningSession == defaultDrmSession) {
                this.provisioningSession = null;
                if (this.sessionsAwaitingProvisioning.isEmpty()) {
                    return;
                }
                DefaultDrmSession next = this.sessionsAwaitingProvisioning.iterator().next();
                this.provisioningSession = next;
                next.provision();
            }
        }

        @Override // com.google.android.exoplayer2.drm.DefaultDrmSession.ProvisioningManager
        public void provisionRequired(DefaultDrmSession defaultDrmSession) {
            this.sessionsAwaitingProvisioning.add(defaultDrmSession);
            if (this.provisioningSession != null) {
                return;
            }
            this.provisioningSession = defaultDrmSession;
            defaultDrmSession.provision();
        }
    }

    public class ReferenceCountListenerImpl implements DefaultDrmSession.ReferenceCountListener {
        private ReferenceCountListenerImpl() {
        }

        @Override // com.google.android.exoplayer2.drm.DefaultDrmSession.ReferenceCountListener
        public void onReferenceCountDecremented(final DefaultDrmSession defaultDrmSession, int i2) {
            if (i2 == 1 && DefaultDrmSessionManager.this.prepareCallsCount > 0 && DefaultDrmSessionManager.this.sessionKeepaliveMs != C.TIME_UNSET) {
                DefaultDrmSessionManager.this.keepaliveSessions.add(defaultDrmSession);
                ((Handler) Assertions.checkNotNull(DefaultDrmSessionManager.this.playbackHandler)).postAtTime(new Runnable() { // from class: com.google.android.exoplayer2.drm.h
                    @Override // java.lang.Runnable
                    public final void run() {
                        defaultDrmSession.release(null);
                    }
                }, defaultDrmSession, SystemClock.uptimeMillis() + DefaultDrmSessionManager.this.sessionKeepaliveMs);
            } else if (i2 == 0) {
                DefaultDrmSessionManager.this.sessions.remove(defaultDrmSession);
                if (DefaultDrmSessionManager.this.placeholderDrmSession == defaultDrmSession) {
                    DefaultDrmSessionManager.this.placeholderDrmSession = null;
                }
                if (DefaultDrmSessionManager.this.noMultiSessionDrmSession == defaultDrmSession) {
                    DefaultDrmSessionManager.this.noMultiSessionDrmSession = null;
                }
                DefaultDrmSessionManager.this.provisioningManagerImpl.onSessionFullyReleased(defaultDrmSession);
                if (DefaultDrmSessionManager.this.sessionKeepaliveMs != C.TIME_UNSET) {
                    ((Handler) Assertions.checkNotNull(DefaultDrmSessionManager.this.playbackHandler)).removeCallbacksAndMessages(defaultDrmSession);
                    DefaultDrmSessionManager.this.keepaliveSessions.remove(defaultDrmSession);
                }
            }
            DefaultDrmSessionManager.this.maybeReleaseMediaDrm();
        }

        @Override // com.google.android.exoplayer2.drm.DefaultDrmSession.ReferenceCountListener
        public void onReferenceCountIncremented(DefaultDrmSession defaultDrmSession, int i2) {
            if (DefaultDrmSessionManager.this.sessionKeepaliveMs != C.TIME_UNSET) {
                DefaultDrmSessionManager.this.keepaliveSessions.remove(defaultDrmSession);
                ((Handler) Assertions.checkNotNull(DefaultDrmSessionManager.this.playbackHandler)).removeCallbacksAndMessages(defaultDrmSession);
            }
        }
    }

    private static boolean acquisitionFailedIndicatingResourceShortage(DrmSession drmSession) {
        return drmSession.getState() == 1 && (Util.SDK_INT < 19 || (((DrmSession.DrmSessionException) Assertions.checkNotNull(drmSession.getError())).getCause() instanceof ResourceBusyException));
    }

    private boolean canAcquireSession(DrmInitData drmInitData) {
        if (this.offlineLicenseKeySetId != null) {
            return true;
        }
        if (getSchemeDatas(drmInitData, this.uuid, true).isEmpty()) {
            if (drmInitData.schemeDataCount != 1 || !drmInitData.get(0).matches(C.COMMON_PSSH_UUID)) {
                return false;
            }
            String strValueOf = String.valueOf(this.uuid);
            StringBuilder sb = new StringBuilder(strValueOf.length() + 72);
            sb.append("DrmInitData only contains common PSSH SchemeData. Assuming support for: ");
            sb.append(strValueOf);
            Log.w(TAG, sb.toString());
        }
        String str = drmInitData.schemeType;
        if (str == null || C.CENC_TYPE_cenc.equals(str)) {
            return true;
        }
        return C.CENC_TYPE_cbcs.equals(str) ? Util.SDK_INT >= 25 : (C.CENC_TYPE_cbc1.equals(str) || C.CENC_TYPE_cens.equals(str)) ? false : true;
    }

    private DefaultDrmSession createAndAcquireSession(@Nullable List<DrmInitData.SchemeData> list, boolean z2, @Nullable DrmSessionEventListener.EventDispatcher eventDispatcher) {
        Assertions.checkNotNull(this.exoMediaDrm);
        DefaultDrmSession defaultDrmSession = new DefaultDrmSession(this.uuid, this.exoMediaDrm, this.provisioningManagerImpl, this.referenceCountListener, list, this.mode, this.playClearSamplesWithoutKeys | z2, z2, this.offlineLicenseKeySetId, this.keyRequestParameters, this.callback, (Looper) Assertions.checkNotNull(this.playbackLooper), this.loadErrorHandlingPolicy);
        defaultDrmSession.acquire(eventDispatcher);
        if (this.sessionKeepaliveMs != C.TIME_UNSET) {
            defaultDrmSession.acquire(null);
        }
        return defaultDrmSession;
    }

    private DefaultDrmSession createAndAcquireSessionWithRetry(@Nullable List<DrmInitData.SchemeData> list, boolean z2, @Nullable DrmSessionEventListener.EventDispatcher eventDispatcher, boolean z3) {
        DefaultDrmSession defaultDrmSessionCreateAndAcquireSession = createAndAcquireSession(list, z2, eventDispatcher);
        if (acquisitionFailedIndicatingResourceShortage(defaultDrmSessionCreateAndAcquireSession) && !this.keepaliveSessions.isEmpty()) {
            releaseAllKeepaliveSessions();
            undoAcquisition(defaultDrmSessionCreateAndAcquireSession, eventDispatcher);
            defaultDrmSessionCreateAndAcquireSession = createAndAcquireSession(list, z2, eventDispatcher);
        }
        if (!acquisitionFailedIndicatingResourceShortage(defaultDrmSessionCreateAndAcquireSession) || !z3 || this.preacquiredSessionReferences.isEmpty()) {
            return defaultDrmSessionCreateAndAcquireSession;
        }
        releaseAllPreacquiredSessions();
        if (!this.keepaliveSessions.isEmpty()) {
            releaseAllKeepaliveSessions();
        }
        undoAcquisition(defaultDrmSessionCreateAndAcquireSession, eventDispatcher);
        return createAndAcquireSession(list, z2, eventDispatcher);
    }

    private static List<DrmInitData.SchemeData> getSchemeDatas(DrmInitData drmInitData, UUID uuid, boolean z2) {
        ArrayList arrayList = new ArrayList(drmInitData.schemeDataCount);
        for (int i2 = 0; i2 < drmInitData.schemeDataCount; i2++) {
            DrmInitData.SchemeData schemeData = drmInitData.get(i2);
            if ((schemeData.matches(uuid) || (C.CLEARKEY_UUID.equals(uuid) && schemeData.matches(C.COMMON_PSSH_UUID))) && (schemeData.data != null || z2)) {
                arrayList.add(schemeData);
            }
        }
        return arrayList;
    }

    @EnsuresNonNull({"this.playbackLooper", "this.playbackHandler"})
    private synchronized void initPlaybackLooper(Looper looper) {
        Looper looper2 = this.playbackLooper;
        if (looper2 == null) {
            this.playbackLooper = looper;
            this.playbackHandler = new Handler(looper);
        } else {
            Assertions.checkState(looper2 == looper);
            Assertions.checkNotNull(this.playbackHandler);
        }
    }

    @Nullable
    private DrmSession maybeAcquirePlaceholderSession(int i2, boolean z2) {
        ExoMediaDrm exoMediaDrm = (ExoMediaDrm) Assertions.checkNotNull(this.exoMediaDrm);
        if ((exoMediaDrm.getCryptoType() == 2 && FrameworkCryptoConfig.WORKAROUND_DEVICE_NEEDS_KEYS_TO_CONFIGURE_CODEC) || Util.linearSearch(this.useDrmSessionsForClearContentTrackTypes, i2) == -1 || exoMediaDrm.getCryptoType() == 1) {
            return null;
        }
        DefaultDrmSession defaultDrmSession = this.placeholderDrmSession;
        if (defaultDrmSession == null) {
            DefaultDrmSession defaultDrmSessionCreateAndAcquireSessionWithRetry = createAndAcquireSessionWithRetry(ImmutableList.of(), true, null, z2);
            this.sessions.add(defaultDrmSessionCreateAndAcquireSessionWithRetry);
            this.placeholderDrmSession = defaultDrmSessionCreateAndAcquireSessionWithRetry;
        } else {
            defaultDrmSession.acquire(null);
        }
        return this.placeholderDrmSession;
    }

    private void maybeCreateMediaDrmHandler(Looper looper) {
        if (this.mediaDrmHandler == null) {
            this.mediaDrmHandler = new MediaDrmHandler(looper);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void maybeReleaseMediaDrm() {
        if (this.exoMediaDrm != null && this.prepareCallsCount == 0 && this.sessions.isEmpty() && this.preacquiredSessionReferences.isEmpty()) {
            ((ExoMediaDrm) Assertions.checkNotNull(this.exoMediaDrm)).release();
            this.exoMediaDrm = null;
        }
    }

    private void releaseAllKeepaliveSessions() {
        UnmodifiableIterator it = ImmutableSet.copyOf((Collection) this.keepaliveSessions).iterator();
        while (it.hasNext()) {
            ((DrmSession) it.next()).release(null);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void releaseAllPreacquiredSessions() {
        UnmodifiableIterator it = ImmutableSet.copyOf((Collection) this.preacquiredSessionReferences).iterator();
        while (it.hasNext()) {
            ((PreacquiredSessionReference) it.next()).release();
        }
    }

    private void undoAcquisition(DrmSession drmSession, @Nullable DrmSessionEventListener.EventDispatcher eventDispatcher) {
        drmSession.release(eventDispatcher);
        if (this.sessionKeepaliveMs != C.TIME_UNSET) {
            drmSession.release(null);
        }
    }

    @Override // com.google.android.exoplayer2.drm.DrmSessionManager
    @Nullable
    public DrmSession acquireSession(Looper looper, @Nullable DrmSessionEventListener.EventDispatcher eventDispatcher, Format format) {
        Assertions.checkState(this.prepareCallsCount > 0);
        initPlaybackLooper(looper);
        return acquireSession(looper, eventDispatcher, format, true);
    }

    @Override // com.google.android.exoplayer2.drm.DrmSessionManager
    public int getCryptoType(Format format) {
        int cryptoType = ((ExoMediaDrm) Assertions.checkNotNull(this.exoMediaDrm)).getCryptoType();
        DrmInitData drmInitData = format.drmInitData;
        if (drmInitData != null) {
            if (canAcquireSession(drmInitData)) {
                return cryptoType;
            }
            return 1;
        }
        if (Util.linearSearch(this.useDrmSessionsForClearContentTrackTypes, MimeTypes.getTrackType(format.sampleMimeType)) != -1) {
            return cryptoType;
        }
        return 0;
    }

    @Override // com.google.android.exoplayer2.drm.DrmSessionManager
    public DrmSessionManager.DrmSessionReference preacquireSession(Looper looper, @Nullable DrmSessionEventListener.EventDispatcher eventDispatcher, Format format) {
        Assertions.checkState(this.prepareCallsCount > 0);
        initPlaybackLooper(looper);
        PreacquiredSessionReference preacquiredSessionReference = new PreacquiredSessionReference(eventDispatcher);
        preacquiredSessionReference.acquire(format);
        return preacquiredSessionReference;
    }

    @Override // com.google.android.exoplayer2.drm.DrmSessionManager
    public final void prepare() {
        int i2 = this.prepareCallsCount;
        this.prepareCallsCount = i2 + 1;
        if (i2 != 0) {
            return;
        }
        if (this.exoMediaDrm == null) {
            ExoMediaDrm exoMediaDrmAcquireExoMediaDrm = this.exoMediaDrmProvider.acquireExoMediaDrm(this.uuid);
            this.exoMediaDrm = exoMediaDrmAcquireExoMediaDrm;
            exoMediaDrmAcquireExoMediaDrm.setOnEventListener(new MediaDrmEventListener());
        } else if (this.sessionKeepaliveMs != C.TIME_UNSET) {
            for (int i3 = 0; i3 < this.sessions.size(); i3++) {
                this.sessions.get(i3).acquire(null);
            }
        }
    }

    @Override // com.google.android.exoplayer2.drm.DrmSessionManager
    public final void release() {
        int i2 = this.prepareCallsCount - 1;
        this.prepareCallsCount = i2;
        if (i2 != 0) {
            return;
        }
        if (this.sessionKeepaliveMs != C.TIME_UNSET) {
            ArrayList arrayList = new ArrayList(this.sessions);
            for (int i3 = 0; i3 < arrayList.size(); i3++) {
                ((DefaultDrmSession) arrayList.get(i3)).release(null);
            }
        }
        releaseAllPreacquiredSessions();
        maybeReleaseMediaDrm();
    }

    public void setMode(int i2, @Nullable byte[] bArr) {
        Assertions.checkState(this.sessions.isEmpty());
        if (i2 == 1 || i2 == 3) {
            Assertions.checkNotNull(bArr);
        }
        this.mode = i2;
        this.offlineLicenseKeySetId = bArr;
    }

    @Deprecated
    public DefaultDrmSessionManager(UUID uuid, ExoMediaDrm exoMediaDrm, MediaDrmCallback mediaDrmCallback, @Nullable HashMap<String, String> map) {
        this(uuid, exoMediaDrm, mediaDrmCallback, map == null ? new HashMap<>() : map, false, 3);
    }

    @Deprecated
    public DefaultDrmSessionManager(UUID uuid, ExoMediaDrm exoMediaDrm, MediaDrmCallback mediaDrmCallback, @Nullable HashMap<String, String> map, boolean z2) {
        this(uuid, exoMediaDrm, mediaDrmCallback, map == null ? new HashMap<>() : map, z2, 3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @Nullable
    public DrmSession acquireSession(Looper looper, @Nullable DrmSessionEventListener.EventDispatcher eventDispatcher, Format format, boolean z2) {
        List<DrmInitData.SchemeData> schemeDatas;
        maybeCreateMediaDrmHandler(looper);
        DrmInitData drmInitData = format.drmInitData;
        if (drmInitData == null) {
            return maybeAcquirePlaceholderSession(MimeTypes.getTrackType(format.sampleMimeType), z2);
        }
        DefaultDrmSession defaultDrmSessionCreateAndAcquireSessionWithRetry = null;
        byte b3 = 0;
        if (this.offlineLicenseKeySetId == null) {
            schemeDatas = getSchemeDatas((DrmInitData) Assertions.checkNotNull(drmInitData), this.uuid, false);
            if (schemeDatas.isEmpty()) {
                MissingSchemeDataException missingSchemeDataException = new MissingSchemeDataException(this.uuid);
                Log.e(TAG, "DRM error", missingSchemeDataException);
                if (eventDispatcher != null) {
                    eventDispatcher.drmSessionManagerError(missingSchemeDataException);
                }
                return new ErrorStateDrmSession(new DrmSession.DrmSessionException(missingSchemeDataException, 6003));
            }
        } else {
            schemeDatas = null;
        }
        if (!this.multiSession) {
            defaultDrmSessionCreateAndAcquireSessionWithRetry = this.noMultiSessionDrmSession;
        } else {
            Iterator<DefaultDrmSession> it = this.sessions.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                DefaultDrmSession next = it.next();
                if (Util.areEqual(next.schemeDatas, schemeDatas)) {
                    defaultDrmSessionCreateAndAcquireSessionWithRetry = next;
                    break;
                }
            }
        }
        if (defaultDrmSessionCreateAndAcquireSessionWithRetry == null) {
            defaultDrmSessionCreateAndAcquireSessionWithRetry = createAndAcquireSessionWithRetry(schemeDatas, false, eventDispatcher, z2);
            if (!this.multiSession) {
                this.noMultiSessionDrmSession = defaultDrmSessionCreateAndAcquireSessionWithRetry;
            }
            this.sessions.add(defaultDrmSessionCreateAndAcquireSessionWithRetry);
        } else {
            defaultDrmSessionCreateAndAcquireSessionWithRetry.acquire(eventDispatcher);
        }
        return defaultDrmSessionCreateAndAcquireSessionWithRetry;
    }

    @Deprecated
    public DefaultDrmSessionManager(UUID uuid, ExoMediaDrm exoMediaDrm, MediaDrmCallback mediaDrmCallback, @Nullable HashMap<String, String> map, boolean z2, int i2) {
        this(uuid, new ExoMediaDrm.AppManagedProvider(exoMediaDrm), mediaDrmCallback, map == null ? new HashMap<>() : map, z2, new int[0], false, new DefaultLoadErrorHandlingPolicy(i2), 300000L);
    }

    private DefaultDrmSessionManager(UUID uuid, ExoMediaDrm.Provider provider, MediaDrmCallback mediaDrmCallback, HashMap<String, String> map, boolean z2, int[] iArr, boolean z3, LoadErrorHandlingPolicy loadErrorHandlingPolicy, long j2) {
        Assertions.checkNotNull(uuid);
        Assertions.checkArgument(!C.COMMON_PSSH_UUID.equals(uuid), "Use C.CLEARKEY_UUID instead");
        this.uuid = uuid;
        this.exoMediaDrmProvider = provider;
        this.callback = mediaDrmCallback;
        this.keyRequestParameters = map;
        this.multiSession = z2;
        this.useDrmSessionsForClearContentTrackTypes = iArr;
        this.playClearSamplesWithoutKeys = z3;
        this.loadErrorHandlingPolicy = loadErrorHandlingPolicy;
        this.provisioningManagerImpl = new ProvisioningManagerImpl(this);
        this.referenceCountListener = new ReferenceCountListenerImpl();
        this.mode = 0;
        this.sessions = new ArrayList();
        this.preacquiredSessionReferences = Sets.newIdentityHashSet();
        this.keepaliveSessions = Sets.newIdentityHashSet();
        this.sessionKeepaliveMs = j2;
    }
}
