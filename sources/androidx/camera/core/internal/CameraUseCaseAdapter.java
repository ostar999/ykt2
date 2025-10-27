package androidx.camera.core.internal;

import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.SurfaceTexture;
import android.util.Size;
import android.view.Surface;
import androidx.annotation.GuardedBy;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.VisibleForTesting;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraControl;
import androidx.camera.core.CameraEffect;
import androidx.camera.core.CameraInfo;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.Logger;
import androidx.camera.core.Preview;
import androidx.camera.core.SurfaceProcessor;
import androidx.camera.core.SurfaceRequest;
import androidx.camera.core.UseCase;
import androidx.camera.core.ViewPort;
import androidx.camera.core.impl.AttachedSurfaceInfo;
import androidx.camera.core.impl.CameraConfig;
import androidx.camera.core.impl.CameraConfigs;
import androidx.camera.core.impl.CameraControlInternal;
import androidx.camera.core.impl.CameraDeviceSurfaceManager;
import androidx.camera.core.impl.CameraInfoInternal;
import androidx.camera.core.impl.CameraInternal;
import androidx.camera.core.impl.Config;
import androidx.camera.core.impl.UseCaseConfig;
import androidx.camera.core.impl.UseCaseConfigFactory;
import androidx.camera.core.impl.utils.executor.CameraXExecutors;
import androidx.camera.core.processing.SurfaceProcessorWithExecutor;
import androidx.core.util.Consumer;
import androidx.core.util.Preconditions;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

@RequiresApi(21)
/* loaded from: classes.dex */
public final class CameraUseCaseAdapter implements Camera {
    private static final String TAG = "CameraUseCaseAdapter";
    private final CameraDeviceSurfaceManager mCameraDeviceSurfaceManager;

    @NonNull
    private CameraInternal mCameraInternal;
    private final LinkedHashSet<CameraInternal> mCameraInternals;
    private final CameraId mId;
    private final UseCaseConfigFactory mUseCaseConfigFactory;

    @Nullable
    @GuardedBy("mLock")
    private ViewPort mViewPort;

    @GuardedBy("mLock")
    private final List<UseCase> mUseCases = new ArrayList();

    @NonNull
    @GuardedBy("mLock")
    private List<CameraEffect> mEffects = Collections.emptyList();

    @NonNull
    @GuardedBy("mLock")
    private CameraConfig mCameraConfig = CameraConfigs.emptyConfig();
    private final Object mLock = new Object();

    @GuardedBy("mLock")
    private boolean mAttached = true;

    @GuardedBy("mLock")
    private Config mInteropConfig = null;

    @GuardedBy("mLock")
    private List<UseCase> mExtraUseCases = new ArrayList();

    public static final class CameraException extends Exception {
        public CameraException() {
        }

        public CameraException(@NonNull String str) {
            super(str);
        }

        public CameraException(@NonNull Throwable th) {
            super(th);
        }
    }

    public static final class CameraId {
        private final List<String> mIds = new ArrayList();

        public CameraId(LinkedHashSet<CameraInternal> linkedHashSet) {
            Iterator<CameraInternal> it = linkedHashSet.iterator();
            while (it.hasNext()) {
                this.mIds.add(it.next().getCameraInfoInternal().getCameraId());
            }
        }

        public boolean equals(Object obj) {
            if (obj instanceof CameraId) {
                return this.mIds.equals(((CameraId) obj).mIds);
            }
            return false;
        }

        public int hashCode() {
            return this.mIds.hashCode() * 53;
        }
    }

    public static class ConfigPair {
        UseCaseConfig<?> mCameraConfig;
        UseCaseConfig<?> mExtendedConfig;

        public ConfigPair(UseCaseConfig<?> useCaseConfig, UseCaseConfig<?> useCaseConfig2) {
            this.mExtendedConfig = useCaseConfig;
            this.mCameraConfig = useCaseConfig2;
        }
    }

    public CameraUseCaseAdapter(@NonNull LinkedHashSet<CameraInternal> linkedHashSet, @NonNull CameraDeviceSurfaceManager cameraDeviceSurfaceManager, @NonNull UseCaseConfigFactory useCaseConfigFactory) {
        this.mCameraInternal = linkedHashSet.iterator().next();
        LinkedHashSet<CameraInternal> linkedHashSet2 = new LinkedHashSet<>(linkedHashSet);
        this.mCameraInternals = linkedHashSet2;
        this.mId = new CameraId(linkedHashSet2);
        this.mCameraDeviceSurfaceManager = cameraDeviceSurfaceManager;
        this.mUseCaseConfigFactory = useCaseConfigFactory;
    }

    private void cacheInteropConfig() {
        synchronized (this.mLock) {
            CameraControlInternal cameraControlInternal = this.mCameraInternal.getCameraControlInternal();
            this.mInteropConfig = cameraControlInternal.getInteropConfig();
            cameraControlInternal.clearInteropConfig();
        }
    }

    @NonNull
    private List<UseCase> calculateRequiredExtraUseCases(@NonNull List<UseCase> list, @NonNull List<UseCase> list2) {
        ArrayList arrayList = new ArrayList(list2);
        boolean zIsExtraPreviewRequired = isExtraPreviewRequired(list);
        boolean zIsExtraImageCaptureRequired = isExtraImageCaptureRequired(list);
        UseCase useCase = null;
        UseCase useCase2 = null;
        for (UseCase useCase3 : list2) {
            if (isPreview(useCase3)) {
                useCase = useCase3;
            } else if (isImageCapture(useCase3)) {
                useCase2 = useCase3;
            }
        }
        if (zIsExtraPreviewRequired && useCase == null) {
            arrayList.add(createExtraPreview());
        } else if (!zIsExtraPreviewRequired && useCase != null) {
            arrayList.remove(useCase);
        }
        if (zIsExtraImageCaptureRequired && useCase2 == null) {
            arrayList.add(createExtraImageCapture());
        } else if (!zIsExtraImageCaptureRequired && useCase2 != null) {
            arrayList.remove(useCase2);
        }
        return arrayList;
    }

    @NonNull
    private static Matrix calculateSensorToBufferTransformMatrix(@NonNull Rect rect, @NonNull Size size) {
        Preconditions.checkArgument(rect.width() > 0 && rect.height() > 0, "Cannot compute viewport crop rects zero sized sensor rect.");
        RectF rectF = new RectF(rect);
        Matrix matrix = new Matrix();
        matrix.setRectToRect(new RectF(0.0f, 0.0f, size.getWidth(), size.getHeight()), rectF, Matrix.ScaleToFit.CENTER);
        matrix.invert(matrix);
        return matrix;
    }

    private Map<UseCase, Size> calculateSuggestedResolutions(@NonNull CameraInfoInternal cameraInfoInternal, @NonNull List<UseCase> list, @NonNull List<UseCase> list2, @NonNull Map<UseCase, ConfigPair> map) {
        ArrayList arrayList = new ArrayList();
        String cameraId = cameraInfoInternal.getCameraId();
        HashMap map2 = new HashMap();
        for (UseCase useCase : list2) {
            arrayList.add(AttachedSurfaceInfo.create(this.mCameraDeviceSurfaceManager.transformSurfaceConfig(cameraId, useCase.getImageFormat(), useCase.getAttachedSurfaceResolution()), useCase.getImageFormat(), useCase.getAttachedSurfaceResolution(), useCase.getCurrentConfig().getTargetFramerate(null)));
            map2.put(useCase, useCase.getAttachedSurfaceResolution());
        }
        if (!list.isEmpty()) {
            HashMap map3 = new HashMap();
            for (UseCase useCase2 : list) {
                ConfigPair configPair = map.get(useCase2);
                map3.put(useCase2.mergeConfigs(cameraInfoInternal, configPair.mExtendedConfig, configPair.mCameraConfig), useCase2);
            }
            Map<UseCaseConfig<?>, Size> suggestedResolutions = this.mCameraDeviceSurfaceManager.getSuggestedResolutions(cameraId, arrayList, new ArrayList(map3.keySet()));
            for (Map.Entry entry : map3.entrySet()) {
                map2.put((UseCase) entry.getValue(), suggestedResolutions.get(entry.getKey()));
            }
        }
        return map2;
    }

    private ImageCapture createExtraImageCapture() {
        return new ImageCapture.Builder().setTargetName("ImageCapture-Extra").build();
    }

    private Preview createExtraPreview() {
        Preview previewBuild = new Preview.Builder().setTargetName("Preview-Extra").build();
        previewBuild.setSurfaceProvider(new Preview.SurfaceProvider() { // from class: androidx.camera.core.internal.b
            @Override // androidx.camera.core.Preview.SurfaceProvider
            public final void onSurfaceRequested(SurfaceRequest surfaceRequest) throws ExecutionException, InterruptedException {
                CameraUseCaseAdapter.lambda$createExtraPreview$1(surfaceRequest);
            }
        });
        return previewBuild;
    }

    private void detachUnnecessaryUseCases(@NonNull List<UseCase> list) {
        synchronized (this.mLock) {
            if (!list.isEmpty()) {
                this.mCameraInternal.detachUseCases(list);
                for (UseCase useCase : list) {
                    if (this.mUseCases.contains(useCase)) {
                        useCase.onDetach(this.mCameraInternal);
                    } else {
                        Logger.e(TAG, "Attempting to detach non-attached UseCase: " + useCase);
                    }
                }
                this.mUseCases.removeAll(list);
            }
        }
    }

    @NonNull
    public static CameraId generateCameraId(@NonNull LinkedHashSet<CameraInternal> linkedHashSet) {
        return new CameraId(linkedHashSet);
    }

    private Map<UseCase, ConfigPair> getConfigs(List<UseCase> list, UseCaseConfigFactory useCaseConfigFactory, UseCaseConfigFactory useCaseConfigFactory2) {
        HashMap map = new HashMap();
        for (UseCase useCase : list) {
            map.put(useCase, new ConfigPair(useCase.getDefaultConfig(false, useCaseConfigFactory), useCase.getDefaultConfig(true, useCaseConfigFactory2)));
        }
        return map;
    }

    private boolean isCoexistingPreviewImageCaptureRequired() {
        boolean z2;
        synchronized (this.mLock) {
            z2 = true;
            if (this.mCameraConfig.getUseCaseCombinationRequiredRule() != 1) {
                z2 = false;
            }
        }
        return z2;
    }

    private boolean isExtraImageCaptureRequired(@NonNull List<UseCase> list) {
        boolean z2 = false;
        boolean z3 = false;
        for (UseCase useCase : list) {
            if (isPreview(useCase)) {
                z2 = true;
            } else if (isImageCapture(useCase)) {
                z3 = true;
            }
        }
        return z2 && !z3;
    }

    private boolean isExtraPreviewRequired(@NonNull List<UseCase> list) {
        boolean z2 = false;
        boolean z3 = false;
        for (UseCase useCase : list) {
            if (isPreview(useCase)) {
                z3 = true;
            } else if (isImageCapture(useCase)) {
                z2 = true;
            }
        }
        return z2 && !z3;
    }

    private boolean isImageCapture(UseCase useCase) {
        return useCase instanceof ImageCapture;
    }

    private boolean isPreview(UseCase useCase) {
        return useCase instanceof Preview;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$createExtraPreview$0(Surface surface, SurfaceTexture surfaceTexture, SurfaceRequest.Result result) {
        surface.release();
        surfaceTexture.release();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$createExtraPreview$1(SurfaceRequest surfaceRequest) throws ExecutionException, InterruptedException {
        final SurfaceTexture surfaceTexture = new SurfaceTexture(0);
        surfaceTexture.setDefaultBufferSize(surfaceRequest.getResolution().getWidth(), surfaceRequest.getResolution().getHeight());
        surfaceTexture.detachFromGLContext();
        final Surface surface = new Surface(surfaceTexture);
        surfaceRequest.provideSurface(surface, CameraXExecutors.directExecutor(), new Consumer() { // from class: androidx.camera.core.internal.a
            @Override // androidx.core.util.Consumer
            public final void accept(Object obj) {
                CameraUseCaseAdapter.lambda$createExtraPreview$0(surface, surfaceTexture, (SurfaceRequest.Result) obj);
            }
        });
    }

    private void restoreInteropConfig() {
        synchronized (this.mLock) {
            if (this.mInteropConfig != null) {
                this.mCameraInternal.getCameraControlInternal().addInteropConfig(this.mInteropConfig);
            }
        }
    }

    @VisibleForTesting
    public static void updateEffects(@NonNull List<CameraEffect> list, @NonNull Collection<UseCase> collection) {
        HashMap map = new HashMap();
        for (CameraEffect cameraEffect : list) {
            map.put(Integer.valueOf(cameraEffect.getTargets()), cameraEffect);
        }
        for (UseCase useCase : collection) {
            if (useCase instanceof Preview) {
                Preview preview = (Preview) useCase;
                CameraEffect cameraEffect2 = (CameraEffect) map.get(1);
                if (cameraEffect2 == null) {
                    preview.setProcessor(null);
                } else {
                    SurfaceProcessor surfaceProcessor = cameraEffect2.getSurfaceProcessor();
                    Objects.requireNonNull(surfaceProcessor);
                    preview.setProcessor(new SurfaceProcessorWithExecutor(surfaceProcessor, cameraEffect2.getProcessorExecutor()));
                }
            }
        }
    }

    private void updateViewPort(@NonNull Map<UseCase, Size> map, @NonNull Collection<UseCase> collection) {
        synchronized (this.mLock) {
            if (this.mViewPort != null) {
                Integer lensFacing = this.mCameraInternal.getCameraInfoInternal().getLensFacing();
                boolean z2 = true;
                if (lensFacing == null) {
                    Logger.w(TAG, "The lens facing is null, probably an external.");
                } else if (lensFacing.intValue() != 0) {
                    z2 = false;
                }
                Map<UseCase, Rect> mapCalculateViewPortRects = ViewPorts.calculateViewPortRects(this.mCameraInternal.getCameraControlInternal().getSensorRect(), z2, this.mViewPort.getAspectRatio(), this.mCameraInternal.getCameraInfoInternal().getSensorRotationDegrees(this.mViewPort.getRotation()), this.mViewPort.getScaleType(), this.mViewPort.getLayoutDirection(), map);
                for (UseCase useCase : collection) {
                    useCase.setViewPortCropRect((Rect) Preconditions.checkNotNull(mapCalculateViewPortRects.get(useCase)));
                    useCase.setSensorToBufferTransformMatrix(calculateSensorToBufferTransformMatrix(this.mCameraInternal.getCameraControlInternal().getSensorRect(), map.get(useCase)));
                }
            }
        }
    }

    public void addUseCases(@NonNull Collection<UseCase> collection) throws CameraException {
        synchronized (this.mLock) {
            ArrayList<UseCase> arrayList = new ArrayList();
            for (UseCase useCase : collection) {
                if (this.mUseCases.contains(useCase)) {
                    Logger.d(TAG, "Attempting to attach already attached UseCase");
                } else {
                    arrayList.add(useCase);
                }
            }
            List<UseCase> arrayList2 = new ArrayList<>(this.mUseCases);
            List<UseCase> listEmptyList = Collections.emptyList();
            List<UseCase> listEmptyList2 = Collections.emptyList();
            if (isCoexistingPreviewImageCaptureRequired()) {
                arrayList2.removeAll(this.mExtraUseCases);
                arrayList2.addAll(arrayList);
                listEmptyList = calculateRequiredExtraUseCases(arrayList2, new ArrayList<>(this.mExtraUseCases));
                ArrayList arrayList3 = new ArrayList(listEmptyList);
                arrayList3.removeAll(this.mExtraUseCases);
                arrayList.addAll(arrayList3);
                listEmptyList2 = new ArrayList<>(this.mExtraUseCases);
                listEmptyList2.removeAll(listEmptyList);
            }
            Map<UseCase, ConfigPair> configs = getConfigs(arrayList, this.mCameraConfig.getUseCaseConfigFactory(), this.mUseCaseConfigFactory);
            try {
                List<UseCase> arrayList4 = new ArrayList<>(this.mUseCases);
                arrayList4.removeAll(listEmptyList2);
                Map<UseCase, Size> mapCalculateSuggestedResolutions = calculateSuggestedResolutions(this.mCameraInternal.getCameraInfoInternal(), arrayList, arrayList4, configs);
                updateViewPort(mapCalculateSuggestedResolutions, collection);
                updateEffects(this.mEffects, collection);
                this.mExtraUseCases = listEmptyList;
                detachUnnecessaryUseCases(listEmptyList2);
                for (UseCase useCase2 : arrayList) {
                    ConfigPair configPair = configs.get(useCase2);
                    useCase2.onAttach(this.mCameraInternal, configPair.mExtendedConfig, configPair.mCameraConfig);
                    useCase2.updateSuggestedResolution((Size) Preconditions.checkNotNull(mapCalculateSuggestedResolutions.get(useCase2)));
                }
                this.mUseCases.addAll(arrayList);
                if (this.mAttached) {
                    this.mCameraInternal.attachUseCases(arrayList);
                }
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    ((UseCase) it.next()).notifyState();
                }
            } catch (IllegalArgumentException e2) {
                throw new CameraException(e2.getMessage());
            }
        }
    }

    public void attachUseCases() {
        synchronized (this.mLock) {
            if (!this.mAttached) {
                this.mCameraInternal.attachUseCases(this.mUseCases);
                restoreInteropConfig();
                Iterator<UseCase> it = this.mUseCases.iterator();
                while (it.hasNext()) {
                    it.next().notifyState();
                }
                this.mAttached = true;
            }
        }
    }

    public void detachUseCases() {
        synchronized (this.mLock) {
            if (this.mAttached) {
                this.mCameraInternal.detachUseCases(new ArrayList(this.mUseCases));
                cacheInteropConfig();
                this.mAttached = false;
            }
        }
    }

    @Override // androidx.camera.core.Camera
    @NonNull
    public CameraControl getCameraControl() {
        return this.mCameraInternal.getCameraControlInternal();
    }

    @NonNull
    public CameraId getCameraId() {
        return this.mId;
    }

    @Override // androidx.camera.core.Camera
    @NonNull
    public CameraInfo getCameraInfo() {
        return this.mCameraInternal.getCameraInfoInternal();
    }

    @Override // androidx.camera.core.Camera
    @NonNull
    public LinkedHashSet<CameraInternal> getCameraInternals() {
        return this.mCameraInternals;
    }

    @Override // androidx.camera.core.Camera
    @NonNull
    public CameraConfig getExtendedConfig() {
        CameraConfig cameraConfig;
        synchronized (this.mLock) {
            cameraConfig = this.mCameraConfig;
        }
        return cameraConfig;
    }

    @NonNull
    public List<UseCase> getUseCases() {
        ArrayList arrayList;
        synchronized (this.mLock) {
            arrayList = new ArrayList(this.mUseCases);
        }
        return arrayList;
    }

    public boolean isEquivalent(@NonNull CameraUseCaseAdapter cameraUseCaseAdapter) {
        return this.mId.equals(cameraUseCaseAdapter.getCameraId());
    }

    @Override // androidx.camera.core.Camera
    public boolean isUseCasesCombinationSupported(@NonNull UseCase... useCaseArr) {
        synchronized (this.mLock) {
            try {
                try {
                    calculateSuggestedResolutions(this.mCameraInternal.getCameraInfoInternal(), Arrays.asList(useCaseArr), Collections.emptyList(), getConfigs(Arrays.asList(useCaseArr), this.mCameraConfig.getUseCaseConfigFactory(), this.mUseCaseConfigFactory));
                } catch (IllegalArgumentException unused) {
                    return false;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return true;
    }

    public void removeUseCases(@NonNull Collection<UseCase> collection) {
        synchronized (this.mLock) {
            detachUnnecessaryUseCases(new ArrayList(collection));
            if (isCoexistingPreviewImageCaptureRequired()) {
                this.mExtraUseCases.removeAll(collection);
                try {
                    addUseCases(Collections.emptyList());
                } catch (CameraException unused) {
                    throw new IllegalArgumentException("Failed to add extra fake Preview or ImageCapture use case!");
                }
            }
        }
    }

    public void setActiveResumingMode(boolean z2) {
        this.mCameraInternal.setActiveResumingMode(z2);
    }

    public void setEffects(@Nullable List<CameraEffect> list) {
        synchronized (this.mLock) {
            this.mEffects = list;
        }
    }

    @Override // androidx.camera.core.Camera
    public void setExtendedConfig(@Nullable CameraConfig cameraConfig) {
        synchronized (this.mLock) {
            if (cameraConfig == null) {
                cameraConfig = CameraConfigs.emptyConfig();
            }
            if (!this.mUseCases.isEmpty() && !this.mCameraConfig.getCompatibilityId().equals(cameraConfig.getCompatibilityId())) {
                throw new IllegalStateException("Need to unbind all use cases before binding with extension enabled");
            }
            this.mCameraConfig = cameraConfig;
            this.mCameraInternal.setExtendedConfig(cameraConfig);
        }
    }

    public void setViewPort(@Nullable ViewPort viewPort) {
        synchronized (this.mLock) {
            this.mViewPort = viewPort;
        }
    }
}
