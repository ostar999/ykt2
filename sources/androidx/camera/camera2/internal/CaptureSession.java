package androidx.camera.camera2.internal;

import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.TotalCaptureResult;
import android.view.Surface;
import androidx.annotation.GuardedBy;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.OptIn;
import androidx.annotation.RequiresApi;
import androidx.camera.camera2.impl.Camera2ImplConfig;
import androidx.camera.camera2.impl.CameraEventCallbacks;
import androidx.camera.camera2.internal.CameraBurstCaptureCallback;
import androidx.camera.camera2.internal.SynchronizedCaptureSession;
import androidx.camera.camera2.internal.SynchronizedCaptureSessionStateCallbacks;
import androidx.camera.camera2.internal.compat.params.InputConfigurationCompat;
import androidx.camera.camera2.internal.compat.params.OutputConfigurationCompat;
import androidx.camera.camera2.internal.compat.params.SessionConfigurationCompat;
import androidx.camera.camera2.internal.compat.workaround.StillCaptureFlow;
import androidx.camera.camera2.internal.compat.workaround.TorchStateReset;
import androidx.camera.camera2.interop.ExperimentalCamera2Interop;
import androidx.camera.core.Logger;
import androidx.camera.core.impl.CameraCaptureCallback;
import androidx.camera.core.impl.CaptureConfig;
import androidx.camera.core.impl.Config;
import androidx.camera.core.impl.DeferrableSurface;
import androidx.camera.core.impl.MutableOptionsBundle;
import androidx.camera.core.impl.OptionsBundle;
import androidx.camera.core.impl.SessionConfig;
import androidx.camera.core.impl.utils.futures.AsyncFunction;
import androidx.camera.core.impl.utils.futures.FutureCallback;
import androidx.camera.core.impl.utils.futures.FutureChain;
import androidx.camera.core.impl.utils.futures.Futures;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import androidx.core.util.Preconditions;
import cn.hutool.core.text.StrPool;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CancellationException;

@RequiresApi(21)
/* loaded from: classes.dex */
final class CaptureSession implements CaptureSessionInterface {
    private static final String TAG = "CaptureSession";
    private static final long TIMEOUT_GET_SURFACE_IN_MS = 5000;

    @GuardedBy("mSessionLock")
    CallbackToFutureAdapter.Completer<Void> mReleaseCompleter;

    @GuardedBy("mSessionLock")
    ListenableFuture<Void> mReleaseFuture;

    @Nullable
    @GuardedBy("mSessionLock")
    SessionConfig mSessionConfig;

    @GuardedBy("mSessionLock")
    State mState;

    @Nullable
    @GuardedBy("mSessionLock")
    SynchronizedCaptureSession mSynchronizedCaptureSession;

    @Nullable
    @GuardedBy("mSessionLock")
    SynchronizedCaptureSessionOpener mSynchronizedCaptureSessionOpener;
    final Object mSessionLock = new Object();

    @GuardedBy("mSessionLock")
    private final List<CaptureConfig> mCaptureConfigs = new ArrayList();
    private final CameraCaptureSession.CaptureCallback mCaptureCallback = new CameraCaptureSession.CaptureCallback() { // from class: androidx.camera.camera2.internal.CaptureSession.1
        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public void onCaptureCompleted(@NonNull CameraCaptureSession cameraCaptureSession, @NonNull CaptureRequest captureRequest, @NonNull TotalCaptureResult totalCaptureResult) {
        }
    };

    @NonNull
    @GuardedBy("mSessionLock")
    Config mCameraEventOnRepeatingOptions = OptionsBundle.emptyBundle();

    @NonNull
    @GuardedBy("mSessionLock")
    CameraEventCallbacks mCameraEventCallbacks = CameraEventCallbacks.createEmptyCallback();

    @GuardedBy("mSessionLock")
    private final Map<DeferrableSurface, Surface> mConfiguredSurfaceMap = new HashMap();

    @GuardedBy("mSessionLock")
    List<DeferrableSurface> mConfiguredDeferrableSurfaces = Collections.emptyList();
    final StillCaptureFlow mStillCaptureFlow = new StillCaptureFlow();
    final TorchStateReset mTorchStateReset = new TorchStateReset();

    @GuardedBy("mSessionLock")
    private final StateCallback mCaptureSessionStateCallback = new StateCallback();

    /* renamed from: androidx.camera.camera2.internal.CaptureSession$4, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass4 {
        static final /* synthetic */ int[] $SwitchMap$androidx$camera$camera2$internal$CaptureSession$State;

        static {
            int[] iArr = new int[State.values().length];
            $SwitchMap$androidx$camera$camera2$internal$CaptureSession$State = iArr;
            try {
                iArr[State.UNINITIALIZED.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$androidx$camera$camera2$internal$CaptureSession$State[State.INITIALIZED.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$androidx$camera$camera2$internal$CaptureSession$State[State.GET_SURFACE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$androidx$camera$camera2$internal$CaptureSession$State[State.OPENING.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$androidx$camera$camera2$internal$CaptureSession$State[State.OPENED.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$androidx$camera$camera2$internal$CaptureSession$State[State.CLOSED.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$androidx$camera$camera2$internal$CaptureSession$State[State.RELEASING.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$androidx$camera$camera2$internal$CaptureSession$State[State.RELEASED.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
        }
    }

    public enum State {
        UNINITIALIZED,
        INITIALIZED,
        GET_SURFACE,
        OPENING,
        OPENED,
        CLOSED,
        RELEASING,
        RELEASED
    }

    public final class StateCallback extends SynchronizedCaptureSession.StateCallback {
        public StateCallback() {
        }

        @Override // androidx.camera.camera2.internal.SynchronizedCaptureSession.StateCallback
        public void onConfigureFailed(@NonNull SynchronizedCaptureSession synchronizedCaptureSession) {
            synchronized (CaptureSession.this.mSessionLock) {
                switch (AnonymousClass4.$SwitchMap$androidx$camera$camera2$internal$CaptureSession$State[CaptureSession.this.mState.ordinal()]) {
                    case 1:
                    case 2:
                    case 3:
                    case 5:
                        throw new IllegalStateException("onConfigureFailed() should not be possible in state: " + CaptureSession.this.mState);
                    case 4:
                    case 6:
                    case 7:
                        CaptureSession.this.finishClose();
                        break;
                    case 8:
                        Logger.d(CaptureSession.TAG, "ConfigureFailed callback after change to RELEASED state");
                        break;
                }
                Logger.e(CaptureSession.TAG, "CameraCaptureSession.onConfigureFailed() " + CaptureSession.this.mState);
            }
        }

        @Override // androidx.camera.camera2.internal.SynchronizedCaptureSession.StateCallback
        public void onConfigured(@NonNull SynchronizedCaptureSession synchronizedCaptureSession) {
            synchronized (CaptureSession.this.mSessionLock) {
                switch (AnonymousClass4.$SwitchMap$androidx$camera$camera2$internal$CaptureSession$State[CaptureSession.this.mState.ordinal()]) {
                    case 1:
                    case 2:
                    case 3:
                    case 5:
                    case 8:
                        throw new IllegalStateException("onConfigured() should not be possible in state: " + CaptureSession.this.mState);
                    case 4:
                        CaptureSession captureSession = CaptureSession.this;
                        captureSession.mState = State.OPENED;
                        captureSession.mSynchronizedCaptureSession = synchronizedCaptureSession;
                        if (captureSession.mSessionConfig != null) {
                            List<CaptureConfig> listOnEnableSession = captureSession.mCameraEventCallbacks.createComboCallback().onEnableSession();
                            if (!listOnEnableSession.isEmpty()) {
                                CaptureSession captureSession2 = CaptureSession.this;
                                captureSession2.issueBurstCaptureRequest(captureSession2.setupConfiguredSurface(listOnEnableSession));
                            }
                        }
                        Logger.d(CaptureSession.TAG, "Attempting to send capture request onConfigured");
                        CaptureSession captureSession3 = CaptureSession.this;
                        captureSession3.issueRepeatingCaptureRequests(captureSession3.mSessionConfig);
                        CaptureSession.this.issuePendingCaptureRequest();
                        break;
                    case 6:
                        CaptureSession.this.mSynchronizedCaptureSession = synchronizedCaptureSession;
                        break;
                    case 7:
                        synchronizedCaptureSession.close();
                        break;
                }
                Logger.d(CaptureSession.TAG, "CameraCaptureSession.onConfigured() mState=" + CaptureSession.this.mState);
            }
        }

        @Override // androidx.camera.camera2.internal.SynchronizedCaptureSession.StateCallback
        public void onReady(@NonNull SynchronizedCaptureSession synchronizedCaptureSession) {
            synchronized (CaptureSession.this.mSessionLock) {
                if (AnonymousClass4.$SwitchMap$androidx$camera$camera2$internal$CaptureSession$State[CaptureSession.this.mState.ordinal()] == 1) {
                    throw new IllegalStateException("onReady() should not be possible in state: " + CaptureSession.this.mState);
                }
                Logger.d(CaptureSession.TAG, "CameraCaptureSession.onReady() " + CaptureSession.this.mState);
            }
        }

        @Override // androidx.camera.camera2.internal.SynchronizedCaptureSession.StateCallback
        public void onSessionFinished(@NonNull SynchronizedCaptureSession synchronizedCaptureSession) {
            synchronized (CaptureSession.this.mSessionLock) {
                if (CaptureSession.this.mState == State.UNINITIALIZED) {
                    throw new IllegalStateException("onSessionFinished() should not be possible in state: " + CaptureSession.this.mState);
                }
                Logger.d(CaptureSession.TAG, "onSessionFinished()");
                CaptureSession.this.finishClose();
            }
        }
    }

    public CaptureSession() {
        this.mState = State.UNINITIALIZED;
        this.mState = State.INITIALIZED;
    }

    @GuardedBy("mSessionLock")
    private CameraCaptureSession.CaptureCallback createCamera2CaptureCallback(List<CameraCaptureCallback> list, CameraCaptureSession.CaptureCallback... captureCallbackArr) {
        ArrayList arrayList = new ArrayList(list.size() + captureCallbackArr.length);
        Iterator<CameraCaptureCallback> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(CaptureCallbackConverter.toCaptureCallback(it.next()));
        }
        Collections.addAll(arrayList, captureCallbackArr);
        return Camera2CaptureCallbacks.createComboCallback(arrayList);
    }

    @NonNull
    private OutputConfigurationCompat getOutputConfigurationCompat(@NonNull SessionConfig.OutputConfig outputConfig, @NonNull Map<DeferrableSurface, Surface> map, @Nullable String str) {
        Surface surface = map.get(outputConfig.getSurface());
        Preconditions.checkNotNull(surface, "Surface in OutputConfig not found in configuredSurfaceMap.");
        OutputConfigurationCompat outputConfigurationCompat = new OutputConfigurationCompat(outputConfig.getSurfaceGroupId(), surface);
        if (str != null) {
            outputConfigurationCompat.setPhysicalCameraId(str);
        } else {
            outputConfigurationCompat.setPhysicalCameraId(outputConfig.getPhysicalCameraId());
        }
        if (!outputConfig.getSharedSurfaces().isEmpty()) {
            outputConfigurationCompat.enableSurfaceSharing();
            Iterator<DeferrableSurface> it = outputConfig.getSharedSurfaces().iterator();
            while (it.hasNext()) {
                Surface surface2 = map.get(it.next());
                Preconditions.checkNotNull(surface2, "Surface in OutputConfig not found in configuredSurfaceMap.");
                outputConfigurationCompat.addSurface(surface2);
            }
        }
        return outputConfigurationCompat;
    }

    @NonNull
    private List<OutputConfigurationCompat> getUniqueOutputConfigurations(@NonNull List<OutputConfigurationCompat> list) {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (OutputConfigurationCompat outputConfigurationCompat : list) {
            if (!arrayList.contains(outputConfigurationCompat.getSurface())) {
                arrayList.add(outputConfigurationCompat.getSurface());
                arrayList2.add(outputConfigurationCompat);
            }
        }
        return arrayList2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$issueBurstCaptureRequest$2(CameraCaptureSession cameraCaptureSession, int i2, boolean z2) {
        synchronized (this.mSessionLock) {
            if (this.mState == State.OPENED) {
                issueRepeatingCaptureRequests(this.mSessionConfig);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Object lambda$release$1(CallbackToFutureAdapter.Completer completer) throws Exception {
        String str;
        synchronized (this.mSessionLock) {
            Preconditions.checkState(this.mReleaseCompleter == null, "Release completer expected to be null");
            this.mReleaseCompleter = completer;
            str = "Release[session=" + this + StrPool.BRACKET_END;
        }
        return str;
    }

    @NonNull
    private static Config mergeOptions(List<CaptureConfig> list) {
        MutableOptionsBundle mutableOptionsBundleCreate = MutableOptionsBundle.create();
        Iterator<CaptureConfig> it = list.iterator();
        while (it.hasNext()) {
            Config implementationOptions = it.next().getImplementationOptions();
            for (Config.Option<?> option : implementationOptions.listOptions()) {
                Object objRetrieveOption = implementationOptions.retrieveOption(option, null);
                if (mutableOptionsBundleCreate.containsOption(option)) {
                    Object objRetrieveOption2 = mutableOptionsBundleCreate.retrieveOption(option, null);
                    if (!Objects.equals(objRetrieveOption2, objRetrieveOption)) {
                        Logger.d(TAG, "Detect conflicting option " + option.getId() + " : " + objRetrieveOption + " != " + objRetrieveOption2);
                    }
                } else {
                    mutableOptionsBundleCreate.insertOption(option, objRetrieveOption);
                }
            }
        }
        return mutableOptionsBundleCreate;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @NonNull
    @OptIn(markerClass = {ExperimentalCamera2Interop.class})
    /* renamed from: openCaptureSession, reason: merged with bridge method [inline-methods] */
    public ListenableFuture<Void> lambda$open$0(@NonNull List<Surface> list, @NonNull SessionConfig sessionConfig, @NonNull CameraDevice cameraDevice) {
        synchronized (this.mSessionLock) {
            int i2 = AnonymousClass4.$SwitchMap$androidx$camera$camera2$internal$CaptureSession$State[this.mState.ordinal()];
            if (i2 != 1 && i2 != 2) {
                if (i2 == 3) {
                    this.mConfiguredSurfaceMap.clear();
                    for (int i3 = 0; i3 < list.size(); i3++) {
                        this.mConfiguredSurfaceMap.put(this.mConfiguredDeferrableSurfaces.get(i3), list.get(i3));
                    }
                    this.mState = State.OPENING;
                    Logger.d(TAG, "Opening capture session.");
                    SynchronizedCaptureSession.StateCallback stateCallbackCreateComboCallback = SynchronizedCaptureSessionStateCallbacks.createComboCallback(this.mCaptureSessionStateCallback, new SynchronizedCaptureSessionStateCallbacks.Adapter(sessionConfig.getSessionStateCallbacks()));
                    Camera2ImplConfig camera2ImplConfig = new Camera2ImplConfig(sessionConfig.getImplementationOptions());
                    CameraEventCallbacks cameraEventCallback = camera2ImplConfig.getCameraEventCallback(CameraEventCallbacks.createEmptyCallback());
                    this.mCameraEventCallbacks = cameraEventCallback;
                    List<CaptureConfig> listOnInitSession = cameraEventCallback.createComboCallback().onInitSession();
                    CaptureConfig.Builder builderFrom = CaptureConfig.Builder.from(sessionConfig.getRepeatingCaptureConfig());
                    Iterator<CaptureConfig> it = listOnInitSession.iterator();
                    while (it.hasNext()) {
                        builderFrom.addImplementationOptions(it.next().getImplementationOptions());
                    }
                    ArrayList arrayList = new ArrayList();
                    String physicalCameraId = camera2ImplConfig.getPhysicalCameraId(null);
                    Iterator<SessionConfig.OutputConfig> it2 = sessionConfig.getOutputConfigs().iterator();
                    while (it2.hasNext()) {
                        OutputConfigurationCompat outputConfigurationCompat = getOutputConfigurationCompat(it2.next(), this.mConfiguredSurfaceMap, physicalCameraId);
                        Config implementationOptions = sessionConfig.getImplementationOptions();
                        Config.Option<Long> option = Camera2ImplConfig.STREAM_USE_CASE_OPTION;
                        if (implementationOptions.containsOption(option)) {
                            outputConfigurationCompat.setStreamUseCase(((Long) sessionConfig.getImplementationOptions().retrieveOption(option)).longValue());
                        }
                        arrayList.add(outputConfigurationCompat);
                    }
                    SessionConfigurationCompat sessionConfigurationCompatCreateSessionConfigurationCompat = this.mSynchronizedCaptureSessionOpener.createSessionConfigurationCompat(0, getUniqueOutputConfigurations(arrayList), stateCallbackCreateComboCallback);
                    if (sessionConfig.getTemplateType() == 5 && sessionConfig.getInputConfiguration() != null) {
                        sessionConfigurationCompatCreateSessionConfigurationCompat.setInputConfiguration(InputConfigurationCompat.wrap(sessionConfig.getInputConfiguration()));
                    }
                    try {
                        CaptureRequest captureRequestBuildWithoutTarget = Camera2CaptureRequestBuilder.buildWithoutTarget(builderFrom.build(), cameraDevice);
                        if (captureRequestBuildWithoutTarget != null) {
                            sessionConfigurationCompatCreateSessionConfigurationCompat.setSessionParameters(captureRequestBuildWithoutTarget);
                        }
                        return this.mSynchronizedCaptureSessionOpener.openCaptureSession(cameraDevice, sessionConfigurationCompatCreateSessionConfigurationCompat, this.mConfiguredDeferrableSurfaces);
                    } catch (CameraAccessException e2) {
                        return Futures.immediateFailedFuture(e2);
                    }
                }
                if (i2 != 5) {
                    return Futures.immediateFailedFuture(new CancellationException("openCaptureSession() not execute in state: " + this.mState));
                }
            }
            return Futures.immediateFailedFuture(new IllegalStateException("openCaptureSession() should not be possible in state: " + this.mState));
        }
    }

    public void abortCaptures() {
        synchronized (this.mSessionLock) {
            if (this.mState == State.OPENED) {
                try {
                    this.mSynchronizedCaptureSession.abortCaptures();
                } catch (CameraAccessException e2) {
                    Logger.e(TAG, "Unable to abort captures.", e2);
                }
            } else {
                Logger.e(TAG, "Unable to abort captures. Incorrect state:" + this.mState);
            }
        }
    }

    @Override // androidx.camera.camera2.internal.CaptureSessionInterface
    public void cancelIssuedCaptureRequests() {
        ArrayList arrayList;
        synchronized (this.mSessionLock) {
            if (this.mCaptureConfigs.isEmpty()) {
                arrayList = null;
            } else {
                arrayList = new ArrayList(this.mCaptureConfigs);
                this.mCaptureConfigs.clear();
            }
        }
        if (arrayList != null) {
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                Iterator<CameraCaptureCallback> it2 = ((CaptureConfig) it.next()).getCameraCaptureCallbacks().iterator();
                while (it2.hasNext()) {
                    it2.next().onCaptureCancelled();
                }
            }
        }
    }

    @Override // androidx.camera.camera2.internal.CaptureSessionInterface
    public void close() {
        synchronized (this.mSessionLock) {
            int i2 = AnonymousClass4.$SwitchMap$androidx$camera$camera2$internal$CaptureSession$State[this.mState.ordinal()];
            if (i2 == 1) {
                throw new IllegalStateException("close() should not be possible in state: " + this.mState);
            }
            if (i2 == 2) {
                this.mState = State.RELEASED;
            } else if (i2 == 3) {
                Preconditions.checkNotNull(this.mSynchronizedCaptureSessionOpener, "The Opener shouldn't null in state:" + this.mState);
                this.mSynchronizedCaptureSessionOpener.stop();
                this.mState = State.RELEASED;
            } else if (i2 == 4) {
                Preconditions.checkNotNull(this.mSynchronizedCaptureSessionOpener, "The Opener shouldn't null in state:" + this.mState);
                this.mSynchronizedCaptureSessionOpener.stop();
                this.mState = State.CLOSED;
                this.mSessionConfig = null;
            } else if (i2 == 5) {
                if (this.mSessionConfig != null) {
                    List<CaptureConfig> listOnDisableSession = this.mCameraEventCallbacks.createComboCallback().onDisableSession();
                    if (!listOnDisableSession.isEmpty()) {
                        try {
                            issueCaptureRequests(setupConfiguredSurface(listOnDisableSession));
                        } catch (IllegalStateException e2) {
                            Logger.e(TAG, "Unable to issue the request before close the capture session", e2);
                        }
                    }
                }
                Preconditions.checkNotNull(this.mSynchronizedCaptureSessionOpener, "The Opener shouldn't null in state:" + this.mState);
                this.mSynchronizedCaptureSessionOpener.stop();
                this.mState = State.CLOSED;
                this.mSessionConfig = null;
            }
        }
    }

    @GuardedBy("mSessionLock")
    public void finishClose() {
        State state = this.mState;
        State state2 = State.RELEASED;
        if (state == state2) {
            Logger.d(TAG, "Skipping finishClose due to being state RELEASED.");
            return;
        }
        this.mState = state2;
        this.mSynchronizedCaptureSession = null;
        CallbackToFutureAdapter.Completer<Void> completer = this.mReleaseCompleter;
        if (completer != null) {
            completer.set(null);
            this.mReleaseCompleter = null;
        }
    }

    @Override // androidx.camera.camera2.internal.CaptureSessionInterface
    @NonNull
    public List<CaptureConfig> getCaptureConfigs() {
        List<CaptureConfig> listUnmodifiableList;
        synchronized (this.mSessionLock) {
            listUnmodifiableList = Collections.unmodifiableList(this.mCaptureConfigs);
        }
        return listUnmodifiableList;
    }

    @Override // androidx.camera.camera2.internal.CaptureSessionInterface
    @Nullable
    public SessionConfig getSessionConfig() {
        SessionConfig sessionConfig;
        synchronized (this.mSessionLock) {
            sessionConfig = this.mSessionConfig;
        }
        return sessionConfig;
    }

    public State getState() {
        State state;
        synchronized (this.mSessionLock) {
            state = this.mState;
        }
        return state;
    }

    public int issueBurstCaptureRequest(List<CaptureConfig> list) {
        CameraBurstCaptureCallback cameraBurstCaptureCallback;
        ArrayList arrayList;
        boolean z2;
        boolean z3;
        synchronized (this.mSessionLock) {
            if (list.isEmpty()) {
                return -1;
            }
            try {
                cameraBurstCaptureCallback = new CameraBurstCaptureCallback();
                arrayList = new ArrayList();
                Logger.d(TAG, "Issuing capture request.");
                z2 = false;
                for (CaptureConfig captureConfig : list) {
                    if (captureConfig.getSurfaces().isEmpty()) {
                        Logger.d(TAG, "Skipping issuing empty capture request.");
                    } else {
                        Iterator<DeferrableSurface> it = captureConfig.getSurfaces().iterator();
                        while (true) {
                            if (!it.hasNext()) {
                                z3 = true;
                                break;
                            }
                            DeferrableSurface next = it.next();
                            if (!this.mConfiguredSurfaceMap.containsKey(next)) {
                                Logger.d(TAG, "Skipping capture request with invalid surface: " + next);
                                z3 = false;
                                break;
                            }
                        }
                        if (z3) {
                            if (captureConfig.getTemplateType() == 2) {
                                z2 = true;
                            }
                            CaptureConfig.Builder builderFrom = CaptureConfig.Builder.from(captureConfig);
                            if (captureConfig.getTemplateType() == 5 && captureConfig.getCameraCaptureResult() != null) {
                                builderFrom.setCameraCaptureResult(captureConfig.getCameraCaptureResult());
                            }
                            SessionConfig sessionConfig = this.mSessionConfig;
                            if (sessionConfig != null) {
                                builderFrom.addImplementationOptions(sessionConfig.getRepeatingCaptureConfig().getImplementationOptions());
                            }
                            builderFrom.addImplementationOptions(this.mCameraEventOnRepeatingOptions);
                            builderFrom.addImplementationOptions(captureConfig.getImplementationOptions());
                            CaptureRequest captureRequestBuild = Camera2CaptureRequestBuilder.build(builderFrom.build(), this.mSynchronizedCaptureSession.getDevice(), this.mConfiguredSurfaceMap);
                            if (captureRequestBuild == null) {
                                Logger.d(TAG, "Skipping issuing request without surface.");
                                return -1;
                            }
                            ArrayList arrayList2 = new ArrayList();
                            Iterator<CameraCaptureCallback> it2 = captureConfig.getCameraCaptureCallbacks().iterator();
                            while (it2.hasNext()) {
                                CaptureCallbackConverter.toCaptureCallback(it2.next(), arrayList2);
                            }
                            cameraBurstCaptureCallback.addCamera2Callbacks(captureRequestBuild, arrayList2);
                            arrayList.add(captureRequestBuild);
                        }
                    }
                }
            } catch (CameraAccessException e2) {
                Logger.e(TAG, "Unable to access camera: " + e2.getMessage());
                Thread.dumpStack();
            }
            if (arrayList.isEmpty()) {
                Logger.d(TAG, "Skipping issuing burst request due to no valid request elements");
                return -1;
            }
            if (this.mStillCaptureFlow.shouldStopRepeatingBeforeCapture(arrayList, z2)) {
                this.mSynchronizedCaptureSession.stopRepeating();
                cameraBurstCaptureCallback.setCaptureSequenceCallback(new CameraBurstCaptureCallback.CaptureSequenceCallback() { // from class: androidx.camera.camera2.internal.z0
                    @Override // androidx.camera.camera2.internal.CameraBurstCaptureCallback.CaptureSequenceCallback
                    public final void onCaptureSequenceCompletedOrAborted(CameraCaptureSession cameraCaptureSession, int i2, boolean z4) {
                        this.f1447a.lambda$issueBurstCaptureRequest$2(cameraCaptureSession, i2, z4);
                    }
                });
            }
            if (this.mTorchStateReset.isTorchResetRequired(arrayList, z2)) {
                cameraBurstCaptureCallback.addCamera2Callbacks((CaptureRequest) arrayList.get(arrayList.size() - 1), Collections.singletonList(new CameraCaptureSession.CaptureCallback() { // from class: androidx.camera.camera2.internal.CaptureSession.3
                    @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
                    public void onCaptureCompleted(@NonNull CameraCaptureSession cameraCaptureSession, @NonNull CaptureRequest captureRequest, @NonNull TotalCaptureResult totalCaptureResult) {
                        synchronized (CaptureSession.this.mSessionLock) {
                            SessionConfig sessionConfig2 = CaptureSession.this.mSessionConfig;
                            if (sessionConfig2 == null) {
                                return;
                            }
                            CaptureConfig repeatingCaptureConfig = sessionConfig2.getRepeatingCaptureConfig();
                            Logger.d(CaptureSession.TAG, "Submit FLASH_MODE_OFF request");
                            CaptureSession captureSession = CaptureSession.this;
                            captureSession.issueCaptureRequests(Collections.singletonList(captureSession.mTorchStateReset.createTorchResetRequest(repeatingCaptureConfig)));
                        }
                    }
                }));
            }
            return this.mSynchronizedCaptureSession.captureBurstRequests(arrayList, cameraBurstCaptureCallback);
        }
    }

    @Override // androidx.camera.camera2.internal.CaptureSessionInterface
    public void issueCaptureRequests(@NonNull List<CaptureConfig> list) {
        synchronized (this.mSessionLock) {
            switch (AnonymousClass4.$SwitchMap$androidx$camera$camera2$internal$CaptureSession$State[this.mState.ordinal()]) {
                case 1:
                    throw new IllegalStateException("issueCaptureRequests() should not be possible in state: " + this.mState);
                case 2:
                case 3:
                case 4:
                    this.mCaptureConfigs.addAll(list);
                    break;
                case 5:
                    this.mCaptureConfigs.addAll(list);
                    issuePendingCaptureRequest();
                    break;
                case 6:
                case 7:
                case 8:
                    throw new IllegalStateException("Cannot issue capture request on a closed/released session.");
            }
        }
    }

    @GuardedBy("mSessionLock")
    public void issuePendingCaptureRequest() {
        if (this.mCaptureConfigs.isEmpty()) {
            return;
        }
        try {
            issueBurstCaptureRequest(this.mCaptureConfigs);
        } finally {
            this.mCaptureConfigs.clear();
        }
    }

    public int issueRepeatingCaptureRequests(@Nullable SessionConfig sessionConfig) {
        synchronized (this.mSessionLock) {
            if (sessionConfig == null) {
                Logger.d(TAG, "Skipping issueRepeatingCaptureRequests for no configuration case.");
                return -1;
            }
            CaptureConfig repeatingCaptureConfig = sessionConfig.getRepeatingCaptureConfig();
            if (repeatingCaptureConfig.getSurfaces().isEmpty()) {
                Logger.d(TAG, "Skipping issueRepeatingCaptureRequests for no surface.");
                try {
                    this.mSynchronizedCaptureSession.stopRepeating();
                } catch (CameraAccessException e2) {
                    Logger.e(TAG, "Unable to access camera: " + e2.getMessage());
                    Thread.dumpStack();
                }
                return -1;
            }
            try {
                Logger.d(TAG, "Issuing request for session.");
                CaptureConfig.Builder builderFrom = CaptureConfig.Builder.from(repeatingCaptureConfig);
                Config configMergeOptions = mergeOptions(this.mCameraEventCallbacks.createComboCallback().onRepeating());
                this.mCameraEventOnRepeatingOptions = configMergeOptions;
                builderFrom.addImplementationOptions(configMergeOptions);
                CaptureRequest captureRequestBuild = Camera2CaptureRequestBuilder.build(builderFrom.build(), this.mSynchronizedCaptureSession.getDevice(), this.mConfiguredSurfaceMap);
                if (captureRequestBuild == null) {
                    Logger.d(TAG, "Skipping issuing empty request for session.");
                    return -1;
                }
                return this.mSynchronizedCaptureSession.setSingleRepeatingRequest(captureRequestBuild, createCamera2CaptureCallback(repeatingCaptureConfig.getCameraCaptureCallbacks(), this.mCaptureCallback));
            } catch (CameraAccessException e3) {
                Logger.e(TAG, "Unable to access camera: " + e3.getMessage());
                Thread.dumpStack();
                return -1;
            }
        }
    }

    @Override // androidx.camera.camera2.internal.CaptureSessionInterface
    @NonNull
    public ListenableFuture<Void> open(@NonNull final SessionConfig sessionConfig, @NonNull final CameraDevice cameraDevice, @NonNull SynchronizedCaptureSessionOpener synchronizedCaptureSessionOpener) {
        synchronized (this.mSessionLock) {
            if (AnonymousClass4.$SwitchMap$androidx$camera$camera2$internal$CaptureSession$State[this.mState.ordinal()] == 2) {
                this.mState = State.GET_SURFACE;
                ArrayList arrayList = new ArrayList(sessionConfig.getSurfaces());
                this.mConfiguredDeferrableSurfaces = arrayList;
                this.mSynchronizedCaptureSessionOpener = synchronizedCaptureSessionOpener;
                FutureChain futureChainTransformAsync = FutureChain.from(synchronizedCaptureSessionOpener.startWithDeferrableSurface(arrayList, 5000L)).transformAsync(new AsyncFunction() { // from class: androidx.camera.camera2.internal.a1
                    @Override // androidx.camera.core.impl.utils.futures.AsyncFunction
                    public final ListenableFuture apply(Object obj) {
                        return this.f1230a.lambda$open$0(sessionConfig, cameraDevice, (List) obj);
                    }
                }, this.mSynchronizedCaptureSessionOpener.getExecutor());
                Futures.addCallback(futureChainTransformAsync, new FutureCallback<Void>() { // from class: androidx.camera.camera2.internal.CaptureSession.2
                    @Override // androidx.camera.core.impl.utils.futures.FutureCallback
                    public void onFailure(@NonNull Throwable th) {
                        synchronized (CaptureSession.this.mSessionLock) {
                            CaptureSession.this.mSynchronizedCaptureSessionOpener.stop();
                            int i2 = AnonymousClass4.$SwitchMap$androidx$camera$camera2$internal$CaptureSession$State[CaptureSession.this.mState.ordinal()];
                            if ((i2 == 4 || i2 == 6 || i2 == 7) && !(th instanceof CancellationException)) {
                                Logger.w(CaptureSession.TAG, "Opening session with fail " + CaptureSession.this.mState, th);
                                CaptureSession.this.finishClose();
                            }
                        }
                    }

                    @Override // androidx.camera.core.impl.utils.futures.FutureCallback
                    public void onSuccess(@Nullable Void r12) {
                    }
                }, this.mSynchronizedCaptureSessionOpener.getExecutor());
                return Futures.nonCancellationPropagating(futureChainTransformAsync);
            }
            Logger.e(TAG, "Open not allowed in state: " + this.mState);
            return Futures.immediateFailedFuture(new IllegalStateException("open() should not allow the state: " + this.mState));
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0056 A[Catch: all -> 0x00af, TryCatch #1 {, blocks: (B:4:0x0003, B:5:0x000d, B:28:0x00a8, B:7:0x0012, B:10:0x0018, B:14:0x0024, B:13:0x001d, B:15:0x0029, B:17:0x0056, B:18:0x005a, B:20:0x005e, B:21:0x0069, B:22:0x006b, B:24:0x006d, B:25:0x008a, B:26:0x008f, B:27:0x00a7), top: B:36:0x0003, inners: #0 }] */
    /* JADX WARN: Removed duplicated region for block: B:20:0x005e A[Catch: all -> 0x00af, TryCatch #1 {, blocks: (B:4:0x0003, B:5:0x000d, B:28:0x00a8, B:7:0x0012, B:10:0x0018, B:14:0x0024, B:13:0x001d, B:15:0x0029, B:17:0x0056, B:18:0x005a, B:20:0x005e, B:21:0x0069, B:22:0x006b, B:24:0x006d, B:25:0x008a, B:26:0x008f, B:27:0x00a7), top: B:36:0x0003, inners: #0 }] */
    @Override // androidx.camera.camera2.internal.CaptureSessionInterface
    @androidx.annotation.NonNull
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public com.google.common.util.concurrent.ListenableFuture<java.lang.Void> release(boolean r4) {
        /*
            r3 = this;
            java.lang.Object r0 = r3.mSessionLock
            monitor-enter(r0)
            int[] r1 = androidx.camera.camera2.internal.CaptureSession.AnonymousClass4.$SwitchMap$androidx$camera$camera2$internal$CaptureSession$State     // Catch: java.lang.Throwable -> Laf
            androidx.camera.camera2.internal.CaptureSession$State r2 = r3.mState     // Catch: java.lang.Throwable -> Laf
            int r2 = r2.ordinal()     // Catch: java.lang.Throwable -> Laf
            r1 = r1[r2]     // Catch: java.lang.Throwable -> Laf
            switch(r1) {
                case 1: goto L8f;
                case 2: goto L8a;
                case 3: goto L6d;
                case 4: goto L29;
                case 5: goto L12;
                case 6: goto L12;
                case 7: goto L5a;
                default: goto L10;
            }     // Catch: java.lang.Throwable -> Laf
        L10:
            goto La8
        L12:
            androidx.camera.camera2.internal.SynchronizedCaptureSession r1 = r3.mSynchronizedCaptureSession     // Catch: java.lang.Throwable -> Laf
            if (r1 == 0) goto L29
            if (r4 == 0) goto L24
            r1.abortCaptures()     // Catch: android.hardware.camera2.CameraAccessException -> L1c java.lang.Throwable -> Laf
            goto L24
        L1c:
            r4 = move-exception
            java.lang.String r1 = "CaptureSession"
            java.lang.String r2 = "Unable to abort captures."
            androidx.camera.core.Logger.e(r1, r2, r4)     // Catch: java.lang.Throwable -> Laf
        L24:
            androidx.camera.camera2.internal.SynchronizedCaptureSession r4 = r3.mSynchronizedCaptureSession     // Catch: java.lang.Throwable -> Laf
            r4.close()     // Catch: java.lang.Throwable -> Laf
        L29:
            androidx.camera.camera2.impl.CameraEventCallbacks r4 = r3.mCameraEventCallbacks     // Catch: java.lang.Throwable -> Laf
            androidx.camera.camera2.impl.CameraEventCallbacks$ComboCameraEventCallback r4 = r4.createComboCallback()     // Catch: java.lang.Throwable -> Laf
            r4.onDeInitSession()     // Catch: java.lang.Throwable -> Laf
            androidx.camera.camera2.internal.CaptureSession$State r4 = androidx.camera.camera2.internal.CaptureSession.State.RELEASING     // Catch: java.lang.Throwable -> Laf
            r3.mState = r4     // Catch: java.lang.Throwable -> Laf
            androidx.camera.camera2.internal.SynchronizedCaptureSessionOpener r4 = r3.mSynchronizedCaptureSessionOpener     // Catch: java.lang.Throwable -> Laf
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> Laf
            r1.<init>()     // Catch: java.lang.Throwable -> Laf
            java.lang.String r2 = "The Opener shouldn't null in state:"
            r1.append(r2)     // Catch: java.lang.Throwable -> Laf
            androidx.camera.camera2.internal.CaptureSession$State r2 = r3.mState     // Catch: java.lang.Throwable -> Laf
            r1.append(r2)     // Catch: java.lang.Throwable -> Laf
            java.lang.String r1 = r1.toString()     // Catch: java.lang.Throwable -> Laf
            androidx.core.util.Preconditions.checkNotNull(r4, r1)     // Catch: java.lang.Throwable -> Laf
            androidx.camera.camera2.internal.SynchronizedCaptureSessionOpener r4 = r3.mSynchronizedCaptureSessionOpener     // Catch: java.lang.Throwable -> Laf
            boolean r4 = r4.stop()     // Catch: java.lang.Throwable -> Laf
            if (r4 == 0) goto L5a
            r3.finishClose()     // Catch: java.lang.Throwable -> Laf
            goto La8
        L5a:
            com.google.common.util.concurrent.ListenableFuture<java.lang.Void> r4 = r3.mReleaseFuture     // Catch: java.lang.Throwable -> Laf
            if (r4 != 0) goto L69
            androidx.camera.camera2.internal.b1 r4 = new androidx.camera.camera2.internal.b1     // Catch: java.lang.Throwable -> Laf
            r4.<init>()     // Catch: java.lang.Throwable -> Laf
            com.google.common.util.concurrent.ListenableFuture r4 = androidx.concurrent.futures.CallbackToFutureAdapter.getFuture(r4)     // Catch: java.lang.Throwable -> Laf
            r3.mReleaseFuture = r4     // Catch: java.lang.Throwable -> Laf
        L69:
            com.google.common.util.concurrent.ListenableFuture<java.lang.Void> r4 = r3.mReleaseFuture     // Catch: java.lang.Throwable -> Laf
            monitor-exit(r0)     // Catch: java.lang.Throwable -> Laf
            return r4
        L6d:
            androidx.camera.camera2.internal.SynchronizedCaptureSessionOpener r4 = r3.mSynchronizedCaptureSessionOpener     // Catch: java.lang.Throwable -> Laf
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> Laf
            r1.<init>()     // Catch: java.lang.Throwable -> Laf
            java.lang.String r2 = "The Opener shouldn't null in state:"
            r1.append(r2)     // Catch: java.lang.Throwable -> Laf
            androidx.camera.camera2.internal.CaptureSession$State r2 = r3.mState     // Catch: java.lang.Throwable -> Laf
            r1.append(r2)     // Catch: java.lang.Throwable -> Laf
            java.lang.String r1 = r1.toString()     // Catch: java.lang.Throwable -> Laf
            androidx.core.util.Preconditions.checkNotNull(r4, r1)     // Catch: java.lang.Throwable -> Laf
            androidx.camera.camera2.internal.SynchronizedCaptureSessionOpener r4 = r3.mSynchronizedCaptureSessionOpener     // Catch: java.lang.Throwable -> Laf
            r4.stop()     // Catch: java.lang.Throwable -> Laf
        L8a:
            androidx.camera.camera2.internal.CaptureSession$State r4 = androidx.camera.camera2.internal.CaptureSession.State.RELEASED     // Catch: java.lang.Throwable -> Laf
            r3.mState = r4     // Catch: java.lang.Throwable -> Laf
            goto La8
        L8f:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException     // Catch: java.lang.Throwable -> Laf
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> Laf
            r1.<init>()     // Catch: java.lang.Throwable -> Laf
            java.lang.String r2 = "release() should not be possible in state: "
            r1.append(r2)     // Catch: java.lang.Throwable -> Laf
            androidx.camera.camera2.internal.CaptureSession$State r2 = r3.mState     // Catch: java.lang.Throwable -> Laf
            r1.append(r2)     // Catch: java.lang.Throwable -> Laf
            java.lang.String r1 = r1.toString()     // Catch: java.lang.Throwable -> Laf
            r4.<init>(r1)     // Catch: java.lang.Throwable -> Laf
            throw r4     // Catch: java.lang.Throwable -> Laf
        La8:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> Laf
            r4 = 0
            com.google.common.util.concurrent.ListenableFuture r4 = androidx.camera.core.impl.utils.futures.Futures.immediateFuture(r4)
            return r4
        Laf:
            r4 = move-exception
            monitor-exit(r0)     // Catch: java.lang.Throwable -> Laf
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.camera2.internal.CaptureSession.release(boolean):com.google.common.util.concurrent.ListenableFuture");
    }

    @Override // androidx.camera.camera2.internal.CaptureSessionInterface
    public void setSessionConfig(@Nullable SessionConfig sessionConfig) {
        synchronized (this.mSessionLock) {
            switch (AnonymousClass4.$SwitchMap$androidx$camera$camera2$internal$CaptureSession$State[this.mState.ordinal()]) {
                case 1:
                    throw new IllegalStateException("setSessionConfig() should not be possible in state: " + this.mState);
                case 2:
                case 3:
                case 4:
                    this.mSessionConfig = sessionConfig;
                    break;
                case 5:
                    this.mSessionConfig = sessionConfig;
                    if (sessionConfig != null) {
                        if (!this.mConfiguredSurfaceMap.keySet().containsAll(sessionConfig.getSurfaces())) {
                            Logger.e(TAG, "Does not have the proper configured lists");
                            return;
                        } else {
                            Logger.d(TAG, "Attempting to submit CaptureRequest after setting");
                            issueRepeatingCaptureRequests(this.mSessionConfig);
                            break;
                        }
                    } else {
                        return;
                    }
                case 6:
                case 7:
                case 8:
                    throw new IllegalStateException("Session configuration cannot be set on a closed/released session.");
            }
        }
    }

    @GuardedBy("mSessionLock")
    public List<CaptureConfig> setupConfiguredSurface(List<CaptureConfig> list) {
        ArrayList arrayList = new ArrayList();
        Iterator<CaptureConfig> it = list.iterator();
        while (it.hasNext()) {
            CaptureConfig.Builder builderFrom = CaptureConfig.Builder.from(it.next());
            builderFrom.setTemplateType(1);
            Iterator<DeferrableSurface> it2 = this.mSessionConfig.getRepeatingCaptureConfig().getSurfaces().iterator();
            while (it2.hasNext()) {
                builderFrom.addSurface(it2.next());
            }
            arrayList.add(builderFrom.build());
        }
        return arrayList;
    }

    public void stopRepeating() {
        synchronized (this.mSessionLock) {
            if (this.mState == State.OPENED) {
                try {
                    this.mSynchronizedCaptureSession.stopRepeating();
                } catch (CameraAccessException e2) {
                    Logger.e(TAG, "Unable to stop repeating.", e2);
                }
            } else {
                Logger.e(TAG, "Unable to stop repeating. Incorrect state:" + this.mState);
            }
        }
    }
}
