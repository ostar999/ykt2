package androidx.camera.core;

import android.app.Application;
import android.content.ComponentCallbacks2;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.SystemClock;
import android.util.SparseArray;
import androidx.annotation.GuardedBy;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.camera.core.CameraXConfig;
import androidx.camera.core.impl.CameraDeviceSurfaceManager;
import androidx.camera.core.impl.CameraFactory;
import androidx.camera.core.impl.CameraRepository;
import androidx.camera.core.impl.CameraThreadConfig;
import androidx.camera.core.impl.CameraValidator;
import androidx.camera.core.impl.MetadataHolderService;
import androidx.camera.core.impl.UseCaseConfigFactory;
import androidx.camera.core.impl.utils.ContextUtil;
import androidx.camera.core.impl.utils.futures.Futures;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import androidx.core.os.HandlerCompat;
import androidx.core.util.Preconditions;
import com.google.common.util.concurrent.ListenableFuture;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.Executor;

@RequiresApi(21)
@MainThread
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes.dex */
public final class CameraX {
    private static final long RETRY_SLEEP_MILLIS = 500;
    private static final String RETRY_TOKEN = "retry_token";
    private static final String TAG = "CameraX";
    private static final long WAIT_INITIALIZED_TIMEOUT_MILLIS = 3000;
    private Context mAppContext;
    private final Executor mCameraExecutor;
    private CameraFactory mCameraFactory;
    private final CameraXConfig mCameraXConfig;
    private UseCaseConfigFactory mDefaultConfigFactory;
    private final ListenableFuture<Void> mInitInternalFuture;
    private final Integer mMinLogLevel;
    private final Handler mSchedulerHandler;

    @Nullable
    private final HandlerThread mSchedulerThread;
    private CameraDeviceSurfaceManager mSurfaceManager;
    private static final Object MIN_LOG_LEVEL_LOCK = new Object();

    @GuardedBy("MIN_LOG_LEVEL_LOCK")
    private static final SparseArray<Integer> sMinLogLevelReferenceCountMap = new SparseArray<>();
    final CameraRepository mCameraRepository = new CameraRepository();
    private final Object mInitializeLock = new Object();

    @GuardedBy("mInitializeLock")
    private InternalInitState mInitState = InternalInitState.UNINITIALIZED;

    @GuardedBy("mInitializeLock")
    private ListenableFuture<Void> mShutdownInternalFuture = Futures.immediateFuture(null);

    /* renamed from: androidx.camera.core.CameraX$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$androidx$camera$core$CameraX$InternalInitState;

        static {
            int[] iArr = new int[InternalInitState.values().length];
            $SwitchMap$androidx$camera$core$CameraX$InternalInitState = iArr;
            try {
                iArr[InternalInitState.UNINITIALIZED.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$androidx$camera$core$CameraX$InternalInitState[InternalInitState.INITIALIZING.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$androidx$camera$core$CameraX$InternalInitState[InternalInitState.INITIALIZING_ERROR.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$androidx$camera$core$CameraX$InternalInitState[InternalInitState.INITIALIZED.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$androidx$camera$core$CameraX$InternalInitState[InternalInitState.SHUTDOWN.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    public enum InternalInitState {
        UNINITIALIZED,
        INITIALIZING,
        INITIALIZING_ERROR,
        INITIALIZED,
        SHUTDOWN
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public CameraX(@NonNull Context context, @Nullable CameraXConfig.Provider provider) {
        if (provider != null) {
            this.mCameraXConfig = provider.getCameraXConfig();
        } else {
            CameraXConfig.Provider configProvider = getConfigProvider(context);
            if (configProvider == null) {
                throw new IllegalStateException("CameraX is not configured properly. The most likely cause is you did not include a default implementation in your build such as 'camera-camera2'.");
            }
            this.mCameraXConfig = configProvider.getCameraXConfig();
        }
        Executor cameraExecutor = this.mCameraXConfig.getCameraExecutor(null);
        Handler schedulerHandler = this.mCameraXConfig.getSchedulerHandler(null);
        this.mCameraExecutor = cameraExecutor == null ? new CameraExecutor() : cameraExecutor;
        if (schedulerHandler == null) {
            HandlerThread handlerThread = new HandlerThread("CameraX-scheduler", 10);
            this.mSchedulerThread = handlerThread;
            handlerThread.start();
            this.mSchedulerHandler = HandlerCompat.createAsync(handlerThread.getLooper());
        } else {
            this.mSchedulerThread = null;
            this.mSchedulerHandler = schedulerHandler;
        }
        Integer num = (Integer) this.mCameraXConfig.retrieveOption(CameraXConfig.OPTION_MIN_LOGGING_LEVEL, null);
        this.mMinLogLevel = num;
        increaseMinLogLevelReference(num);
        this.mInitInternalFuture = initInternal(context);
    }

    private static void decreaseMinLogLevelReference(@Nullable Integer num) {
        synchronized (MIN_LOG_LEVEL_LOCK) {
            if (num == null) {
                return;
            }
            SparseArray<Integer> sparseArray = sMinLogLevelReferenceCountMap;
            int iIntValue = sparseArray.get(num.intValue()).intValue() - 1;
            if (iIntValue == 0) {
                sparseArray.remove(num.intValue());
            } else {
                sparseArray.put(num.intValue(), Integer.valueOf(iIntValue));
            }
            updateOrResetMinLogLevel();
        }
    }

    @Nullable
    private static CameraXConfig.Provider getConfigProvider(@NonNull Context context) {
        ComponentCallbacks2 applicationFromContext = ContextUtil.getApplicationFromContext(context);
        if (applicationFromContext instanceof CameraXConfig.Provider) {
            return (CameraXConfig.Provider) applicationFromContext;
        }
        try {
            Context applicationContext = ContextUtil.getApplicationContext(context);
            Bundle bundle = applicationContext.getPackageManager().getServiceInfo(new ComponentName(applicationContext, (Class<?>) MetadataHolderService.class), 640).metaData;
            String string = bundle != null ? bundle.getString("androidx.camera.core.impl.MetadataHolderService.DEFAULT_CONFIG_PROVIDER") : null;
            if (string != null) {
                return (CameraXConfig.Provider) Class.forName(string).getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
            }
            Logger.e(TAG, "No default CameraXConfig.Provider specified in meta-data. The most likely cause is you did not include a default implementation in your build such as 'camera-camera2'.");
            return null;
        } catch (PackageManager.NameNotFoundException | ClassNotFoundException | IllegalAccessException | InstantiationException | NoSuchMethodException | NullPointerException | InvocationTargetException e2) {
            Logger.e(TAG, "Failed to retrieve default CameraXConfig.Provider from meta-data", e2);
            return null;
        }
    }

    private static void increaseMinLogLevelReference(@Nullable Integer num) {
        synchronized (MIN_LOG_LEVEL_LOCK) {
            if (num == null) {
                return;
            }
            Preconditions.checkArgumentInRange(num.intValue(), 3, 6, "minLogLevel");
            SparseArray<Integer> sparseArray = sMinLogLevelReferenceCountMap;
            sparseArray.put(num.intValue(), Integer.valueOf(sparseArray.get(num.intValue()) != null ? 1 + sparseArray.get(num.intValue()).intValue() : 1));
            updateOrResetMinLogLevel();
        }
    }

    private void initAndRetryRecursively(@NonNull final Executor executor, final long j2, @NonNull final Context context, @NonNull final CallbackToFutureAdapter.Completer<Void> completer) {
        executor.execute(new Runnable() { // from class: androidx.camera.core.j
            @Override // java.lang.Runnable
            public final void run() throws InitializationException {
                this.f1582c.lambda$initAndRetryRecursively$2(context, executor, completer, j2);
            }
        });
    }

    private ListenableFuture<Void> initInternal(@NonNull final Context context) {
        ListenableFuture<Void> future;
        synchronized (this.mInitializeLock) {
            Preconditions.checkState(this.mInitState == InternalInitState.UNINITIALIZED, "CameraX.initInternal() should only be called once per instance");
            this.mInitState = InternalInitState.INITIALIZING;
            future = CallbackToFutureAdapter.getFuture(new CallbackToFutureAdapter.Resolver() { // from class: androidx.camera.core.k
                @Override // androidx.concurrent.futures.CallbackToFutureAdapter.Resolver
                public final Object attachCompleter(CallbackToFutureAdapter.Completer completer) {
                    return this.f1590a.lambda$initInternal$0(context, completer);
                }
            });
        }
        return future;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initAndRetryRecursively$1(Executor executor, long j2, CallbackToFutureAdapter.Completer completer) {
        initAndRetryRecursively(executor, j2, this.mAppContext, completer);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initAndRetryRecursively$2(Context context, final Executor executor, final CallbackToFutureAdapter.Completer completer, final long j2) throws InitializationException {
        try {
            Application applicationFromContext = ContextUtil.getApplicationFromContext(context);
            this.mAppContext = applicationFromContext;
            if (applicationFromContext == null) {
                this.mAppContext = ContextUtil.getApplicationContext(context);
            }
            CameraFactory.Provider cameraFactoryProvider = this.mCameraXConfig.getCameraFactoryProvider(null);
            if (cameraFactoryProvider == null) {
                throw new InitializationException(new IllegalArgumentException("Invalid app configuration provided. Missing CameraFactory."));
            }
            CameraThreadConfig cameraThreadConfigCreate = CameraThreadConfig.create(this.mCameraExecutor, this.mSchedulerHandler);
            CameraSelector availableCamerasLimiter = this.mCameraXConfig.getAvailableCamerasLimiter(null);
            this.mCameraFactory = cameraFactoryProvider.newInstance(this.mAppContext, cameraThreadConfigCreate, availableCamerasLimiter);
            CameraDeviceSurfaceManager.Provider deviceSurfaceManagerProvider = this.mCameraXConfig.getDeviceSurfaceManagerProvider(null);
            if (deviceSurfaceManagerProvider == null) {
                throw new InitializationException(new IllegalArgumentException("Invalid app configuration provided. Missing CameraDeviceSurfaceManager."));
            }
            this.mSurfaceManager = deviceSurfaceManagerProvider.newInstance(this.mAppContext, this.mCameraFactory.getCameraManager(), this.mCameraFactory.getAvailableCameraIds());
            UseCaseConfigFactory.Provider useCaseConfigFactoryProvider = this.mCameraXConfig.getUseCaseConfigFactoryProvider(null);
            if (useCaseConfigFactoryProvider == null) {
                throw new InitializationException(new IllegalArgumentException("Invalid app configuration provided. Missing UseCaseConfigFactory."));
            }
            this.mDefaultConfigFactory = useCaseConfigFactoryProvider.newInstance(this.mAppContext);
            if (executor instanceof CameraExecutor) {
                ((CameraExecutor) executor).init(this.mCameraFactory);
            }
            this.mCameraRepository.init(this.mCameraFactory);
            CameraValidator.validateCameras(this.mAppContext, this.mCameraRepository, availableCamerasLimiter);
            setStateToInitialized();
            completer.set(null);
        } catch (InitializationException | CameraValidator.CameraIdListIncorrectException | RuntimeException e2) {
            if (SystemClock.elapsedRealtime() - j2 < 2500) {
                Logger.w(TAG, "Retry init. Start time " + j2 + " current time " + SystemClock.elapsedRealtime(), e2);
                HandlerCompat.postDelayed(this.mSchedulerHandler, new Runnable() { // from class: androidx.camera.core.h
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f1507c.lambda$initAndRetryRecursively$1(executor, j2, completer);
                    }
                }, RETRY_TOKEN, 500L);
                return;
            }
            synchronized (this.mInitializeLock) {
                this.mInitState = InternalInitState.INITIALIZING_ERROR;
            }
            if (e2 instanceof CameraValidator.CameraIdListIncorrectException) {
                Logger.e(TAG, "The device might underreport the amount of the cameras. Finish the initialize task since we are already reaching the maximum number of retries.");
                completer.set(null);
            } else if (e2 instanceof InitializationException) {
                completer.setException(e2);
            } else {
                completer.setException(new InitializationException(e2));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Object lambda$initInternal$0(Context context, CallbackToFutureAdapter.Completer completer) throws Exception {
        initAndRetryRecursively(this.mCameraExecutor, SystemClock.elapsedRealtime(), context, completer);
        return "CameraX initInternal";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$shutdownInternal$3(CallbackToFutureAdapter.Completer completer) {
        if (this.mSchedulerThread != null) {
            Executor executor = this.mCameraExecutor;
            if (executor instanceof CameraExecutor) {
                ((CameraExecutor) executor).deinit();
            }
            this.mSchedulerThread.quit();
        }
        completer.set(null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Object lambda$shutdownInternal$4(final CallbackToFutureAdapter.Completer completer) throws Exception {
        this.mCameraRepository.deinit().addListener(new Runnable() { // from class: androidx.camera.core.i
            @Override // java.lang.Runnable
            public final void run() {
                this.f1513c.lambda$shutdownInternal$3(completer);
            }
        }, this.mCameraExecutor);
        return "CameraX shutdownInternal";
    }

    private void setStateToInitialized() {
        synchronized (this.mInitializeLock) {
            this.mInitState = InternalInitState.INITIALIZED;
        }
    }

    @NonNull
    private ListenableFuture<Void> shutdownInternal() {
        synchronized (this.mInitializeLock) {
            this.mSchedulerHandler.removeCallbacksAndMessages(RETRY_TOKEN);
            int i2 = AnonymousClass1.$SwitchMap$androidx$camera$core$CameraX$InternalInitState[this.mInitState.ordinal()];
            if (i2 == 1) {
                this.mInitState = InternalInitState.SHUTDOWN;
                return Futures.immediateFuture(null);
            }
            if (i2 == 2) {
                throw new IllegalStateException("CameraX could not be shutdown when it is initializing.");
            }
            if (i2 == 3 || i2 == 4) {
                this.mInitState = InternalInitState.SHUTDOWN;
                decreaseMinLogLevelReference(this.mMinLogLevel);
                this.mShutdownInternalFuture = CallbackToFutureAdapter.getFuture(new CallbackToFutureAdapter.Resolver() { // from class: androidx.camera.core.g
                    @Override // androidx.concurrent.futures.CallbackToFutureAdapter.Resolver
                    public final Object attachCompleter(CallbackToFutureAdapter.Completer completer) {
                        return this.f1503a.lambda$shutdownInternal$4(completer);
                    }
                });
            }
            return this.mShutdownInternalFuture;
        }
    }

    @GuardedBy("MIN_LOG_LEVEL_LOCK")
    private static void updateOrResetMinLogLevel() {
        SparseArray<Integer> sparseArray = sMinLogLevelReferenceCountMap;
        if (sparseArray.size() == 0) {
            Logger.resetMinLogLevel();
            return;
        }
        if (sparseArray.get(3) != null) {
            Logger.setMinLogLevel(3);
            return;
        }
        if (sparseArray.get(4) != null) {
            Logger.setMinLogLevel(4);
        } else if (sparseArray.get(5) != null) {
            Logger.setMinLogLevel(5);
        } else if (sparseArray.get(6) != null) {
            Logger.setMinLogLevel(6);
        }
    }

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public CameraDeviceSurfaceManager getCameraDeviceSurfaceManager() {
        CameraDeviceSurfaceManager cameraDeviceSurfaceManager = this.mSurfaceManager;
        if (cameraDeviceSurfaceManager != null) {
            return cameraDeviceSurfaceManager;
        }
        throw new IllegalStateException("CameraX not initialized yet.");
    }

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public CameraFactory getCameraFactory() {
        CameraFactory cameraFactory = this.mCameraFactory;
        if (cameraFactory != null) {
            return cameraFactory;
        }
        throw new IllegalStateException("CameraX not initialized yet.");
    }

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public CameraRepository getCameraRepository() {
        return this.mCameraRepository;
    }

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public UseCaseConfigFactory getDefaultConfigFactory() {
        UseCaseConfigFactory useCaseConfigFactory = this.mDefaultConfigFactory;
        if (useCaseConfigFactory != null) {
            return useCaseConfigFactory;
        }
        throw new IllegalStateException("CameraX not initialized yet.");
    }

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public ListenableFuture<Void> getInitializeFuture() {
        return this.mInitInternalFuture;
    }

    public boolean isInitialized() {
        boolean z2;
        synchronized (this.mInitializeLock) {
            z2 = this.mInitState == InternalInitState.INITIALIZED;
        }
        return z2;
    }

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public ListenableFuture<Void> shutdown() {
        return shutdownInternal();
    }
}
