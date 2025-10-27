package androidx.camera.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.hardware.display.DisplayManager;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.Rational;
import android.util.Size;
import android.view.Display;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;
import androidx.annotation.AnyThread;
import androidx.annotation.ColorRes;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.OptIn;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.annotation.UiThread;
import androidx.annotation.VisibleForTesting;
import androidx.camera.core.CameraInfo;
import androidx.camera.core.Logger;
import androidx.camera.core.MeteringPointFactory;
import androidx.camera.core.Preview;
import androidx.camera.core.SurfaceRequest;
import androidx.camera.core.ViewPort;
import androidx.camera.core.impl.CameraInfoInternal;
import androidx.camera.core.impl.CameraInternal;
import androidx.camera.core.impl.utils.Threads;
import androidx.camera.core.impl.utils.TransformUtils;
import androidx.camera.view.PreviewViewImplementation;
import androidx.camera.view.internal.compat.quirk.DeviceQuirks;
import androidx.camera.view.internal.compat.quirk.SurfaceViewNotCroppedByParentQuirk;
import androidx.camera.view.internal.compat.quirk.SurfaceViewStretchedQuirk;
import androidx.camera.view.transform.OutputTransform;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicReference;

@RequiresApi(21)
/* loaded from: classes.dex */
public final class PreviewView extends FrameLayout {

    @ColorRes
    static final int DEFAULT_BACKGROUND_COLOR = 17170444;
    private static final ImplementationMode DEFAULT_IMPL_MODE = ImplementationMode.PERFORMANCE;
    private static final String TAG = "PreviewView";

    @Nullable
    final AtomicReference<PreviewStreamStateObserver> mActiveStreamStateObserver;
    CameraController mCameraController;

    @Nullable
    CameraInfoInternal mCameraInfoInternal;

    @NonNull
    private final DisplayRotationListener mDisplayRotationListener;

    @Nullable
    @VisibleForTesting
    PreviewViewImplementation mImplementation;

    @NonNull
    ImplementationMode mImplementationMode;

    @Nullable
    OnFrameUpdateListener mOnFrameUpdateListener;

    @Nullable
    Executor mOnFrameUpdateListenerExecutor;
    private final View.OnLayoutChangeListener mOnLayoutChangeListener;

    @NonNull
    final MutableLiveData<StreamState> mPreviewStreamStateLiveData;

    @NonNull
    final PreviewTransformation mPreviewTransform;

    @NonNull
    PreviewViewMeteringPointFactory mPreviewViewMeteringPointFactory;

    @NonNull
    private final ScaleGestureDetector mScaleGestureDetector;
    final Preview.SurfaceProvider mSurfaceProvider;

    @Nullable
    private MotionEvent mTouchUpEvent;
    boolean mUseDisplayRotation;

    /* renamed from: androidx.camera.view.PreviewView$1, reason: invalid class name */
    public class AnonymousClass1 implements Preview.SurfaceProvider {
        public AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSurfaceRequested$0(SurfaceRequest surfaceRequest) {
            PreviewView.this.mSurfaceProvider.onSurfaceRequested(surfaceRequest);
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX WARN: Removed duplicated region for block: B:18:0x0051  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public /* synthetic */ void lambda$onSurfaceRequested$1(androidx.camera.core.impl.CameraInternal r4, androidx.camera.core.SurfaceRequest r5, androidx.camera.core.SurfaceRequest.TransformationInfo r6) {
            /*
                r3 = this;
                java.lang.StringBuilder r0 = new java.lang.StringBuilder
                r0.<init>()
                java.lang.String r1 = "Preview transformation info updated. "
                r0.append(r1)
                r0.append(r6)
                java.lang.String r0 = r0.toString()
                java.lang.String r1 = "PreviewView"
                androidx.camera.core.Logger.d(r1, r0)
                androidx.camera.core.impl.CameraInfoInternal r4 = r4.getCameraInfoInternal()
                java.lang.Integer r4 = r4.getLensFacing()
                r0 = 0
                r2 = 1
                if (r4 != 0) goto L29
                java.lang.String r4 = "The lens facing is null, probably an external."
                androidx.camera.core.Logger.w(r1, r4)
            L27:
                r4 = r2
                goto L31
            L29:
                int r4 = r4.intValue()
                if (r4 != 0) goto L30
                goto L27
            L30:
                r4 = r0
            L31:
                androidx.camera.view.PreviewView r1 = androidx.camera.view.PreviewView.this
                androidx.camera.view.PreviewTransformation r1 = r1.mPreviewTransform
                android.util.Size r5 = r5.getResolution()
                r1.setTransformationInfo(r6, r5, r4)
                int r4 = r6.getTargetRotation()
                r5 = -1
                if (r4 == r5) goto L51
                androidx.camera.view.PreviewView r4 = androidx.camera.view.PreviewView.this
                androidx.camera.view.PreviewViewImplementation r5 = r4.mImplementation
                if (r5 == 0) goto L4e
                boolean r5 = r5 instanceof androidx.camera.view.SurfaceViewImplementation
                if (r5 == 0) goto L4e
                goto L51
            L4e:
                r4.mUseDisplayRotation = r0
                goto L55
            L51:
                androidx.camera.view.PreviewView r4 = androidx.camera.view.PreviewView.this
                r4.mUseDisplayRotation = r2
            L55:
                androidx.camera.view.PreviewView r4 = androidx.camera.view.PreviewView.this
                r4.updateDisplayRotationIfNeeded()
                androidx.camera.view.PreviewView r4 = androidx.camera.view.PreviewView.this
                r4.redrawPreview()
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.camera.view.PreviewView.AnonymousClass1.lambda$onSurfaceRequested$1(androidx.camera.core.impl.CameraInternal, androidx.camera.core.SurfaceRequest, androidx.camera.core.SurfaceRequest$TransformationInfo):void");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSurfaceRequested$2(PreviewStreamStateObserver previewStreamStateObserver, CameraInternal cameraInternal) {
            if (j.a(PreviewView.this.mActiveStreamStateObserver, previewStreamStateObserver, null)) {
                previewStreamStateObserver.updatePreviewStreamState(StreamState.IDLE);
            }
            previewStreamStateObserver.clear();
            cameraInternal.getCameraState().removeObserver(previewStreamStateObserver);
        }

        @Override // androidx.camera.core.Preview.SurfaceProvider
        @AnyThread
        public void onSurfaceRequested(@NonNull final SurfaceRequest surfaceRequest) {
            PreviewViewImplementation surfaceViewImplementation;
            Executor executor;
            if (!Threads.isMainThread()) {
                ContextCompat.getMainExecutor(PreviewView.this.getContext()).execute(new Runnable() { // from class: androidx.camera.view.k
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f1741c.lambda$onSurfaceRequested$0(surfaceRequest);
                    }
                });
                return;
            }
            Logger.d(PreviewView.TAG, "Surface requested by Preview.");
            final CameraInternal camera = surfaceRequest.getCamera();
            PreviewView.this.mCameraInfoInternal = camera.getCameraInfoInternal();
            surfaceRequest.setTransformationInfoListener(ContextCompat.getMainExecutor(PreviewView.this.getContext()), new SurfaceRequest.TransformationInfoListener() { // from class: androidx.camera.view.l
                @Override // androidx.camera.core.SurfaceRequest.TransformationInfoListener
                public final void onTransformationInfoUpdate(SurfaceRequest.TransformationInfo transformationInfo) {
                    this.f1743a.lambda$onSurfaceRequested$1(camera, surfaceRequest, transformationInfo);
                }
            });
            PreviewView previewView = PreviewView.this;
            if (PreviewView.shouldUseTextureView(surfaceRequest, previewView.mImplementationMode)) {
                PreviewView previewView2 = PreviewView.this;
                surfaceViewImplementation = new TextureViewImplementation(previewView2, previewView2.mPreviewTransform);
            } else {
                PreviewView previewView3 = PreviewView.this;
                surfaceViewImplementation = new SurfaceViewImplementation(previewView3, previewView3.mPreviewTransform);
            }
            previewView.mImplementation = surfaceViewImplementation;
            CameraInfoInternal cameraInfoInternal = camera.getCameraInfoInternal();
            PreviewView previewView4 = PreviewView.this;
            final PreviewStreamStateObserver previewStreamStateObserver = new PreviewStreamStateObserver(cameraInfoInternal, previewView4.mPreviewStreamStateLiveData, previewView4.mImplementation);
            PreviewView.this.mActiveStreamStateObserver.set(previewStreamStateObserver);
            camera.getCameraState().addObserver(ContextCompat.getMainExecutor(PreviewView.this.getContext()), previewStreamStateObserver);
            PreviewView.this.mImplementation.onSurfaceRequested(surfaceRequest, new PreviewViewImplementation.OnSurfaceNotInUseListener() { // from class: androidx.camera.view.m
                @Override // androidx.camera.view.PreviewViewImplementation.OnSurfaceNotInUseListener
                public final void onSurfaceNotInUse() {
                    this.f1746a.lambda$onSurfaceRequested$2(previewStreamStateObserver, camera);
                }
            });
            PreviewView previewView5 = PreviewView.this;
            OnFrameUpdateListener onFrameUpdateListener = previewView5.mOnFrameUpdateListener;
            if (onFrameUpdateListener == null || (executor = previewView5.mOnFrameUpdateListenerExecutor) == null) {
                return;
            }
            previewView5.mImplementation.setFrameUpdateListener(executor, onFrameUpdateListener);
        }
    }

    /* renamed from: androidx.camera.view.PreviewView$2, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$androidx$camera$view$PreviewView$ImplementationMode;
        static final /* synthetic */ int[] $SwitchMap$androidx$camera$view$PreviewView$ScaleType;

        static {
            int[] iArr = new int[ImplementationMode.values().length];
            $SwitchMap$androidx$camera$view$PreviewView$ImplementationMode = iArr;
            try {
                iArr[ImplementationMode.COMPATIBLE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$androidx$camera$view$PreviewView$ImplementationMode[ImplementationMode.PERFORMANCE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            int[] iArr2 = new int[ScaleType.values().length];
            $SwitchMap$androidx$camera$view$PreviewView$ScaleType = iArr2;
            try {
                iArr2[ScaleType.FILL_END.ordinal()] = 1;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$androidx$camera$view$PreviewView$ScaleType[ScaleType.FILL_CENTER.ordinal()] = 2;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$androidx$camera$view$PreviewView$ScaleType[ScaleType.FILL_START.ordinal()] = 3;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$androidx$camera$view$PreviewView$ScaleType[ScaleType.FIT_END.ordinal()] = 4;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$androidx$camera$view$PreviewView$ScaleType[ScaleType.FIT_CENTER.ordinal()] = 5;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$androidx$camera$view$PreviewView$ScaleType[ScaleType.FIT_START.ordinal()] = 6;
            } catch (NoSuchFieldError unused8) {
            }
        }
    }

    public class DisplayRotationListener implements DisplayManager.DisplayListener {
        public DisplayRotationListener() {
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public void onDisplayAdded(int i2) {
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public void onDisplayChanged(int i2) {
            Display display = PreviewView.this.getDisplay();
            if (display == null || display.getDisplayId() != i2) {
                return;
            }
            PreviewView.this.updateDisplayRotationIfNeeded();
            PreviewView.this.redrawPreview();
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public void onDisplayRemoved(int i2) {
        }
    }

    @RequiresApi(21)
    public enum ImplementationMode {
        PERFORMANCE(0),
        COMPATIBLE(1);

        private final int mId;

        ImplementationMode(int i2) {
            this.mId = i2;
        }

        public static ImplementationMode fromId(int i2) {
            for (ImplementationMode implementationMode : values()) {
                if (implementationMode.mId == i2) {
                    return implementationMode;
                }
            }
            throw new IllegalArgumentException("Unknown implementation mode id " + i2);
        }

        public int getId() {
            return this.mId;
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public interface OnFrameUpdateListener {
        void onFrameUpdate(long j2);
    }

    public class PinchToZoomOnScaleGestureListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        public PinchToZoomOnScaleGestureListener() {
        }

        @Override // android.view.ScaleGestureDetector.SimpleOnScaleGestureListener, android.view.ScaleGestureDetector.OnScaleGestureListener
        public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
            CameraController cameraController = PreviewView.this.mCameraController;
            if (cameraController == null) {
                return true;
            }
            cameraController.onPinchToZoom(scaleGestureDetector.getScaleFactor());
            return true;
        }
    }

    @RequiresApi(21)
    public enum ScaleType {
        FILL_START(0),
        FILL_CENTER(1),
        FILL_END(2),
        FIT_START(3),
        FIT_CENTER(4),
        FIT_END(5);

        private final int mId;

        ScaleType(int i2) {
            this.mId = i2;
        }

        public static ScaleType fromId(int i2) {
            for (ScaleType scaleType : values()) {
                if (scaleType.mId == i2) {
                    return scaleType;
                }
            }
            throw new IllegalArgumentException("Unknown scale type id " + i2);
        }

        public int getId() {
            return this.mId;
        }
    }

    public enum StreamState {
        IDLE,
        STREAMING
    }

    @UiThread
    public PreviewView(@NonNull Context context) {
        this(context, null);
    }

    @MainThread
    private void attachToControllerIfReady(boolean z2) {
        Threads.checkMainThread();
        Display display = getDisplay();
        ViewPort viewPort = getViewPort();
        if (this.mCameraController == null || viewPort == null || !isAttachedToWindow() || display == null) {
            return;
        }
        try {
            this.mCameraController.attachPreviewSurface(getSurfaceProvider(), viewPort, display);
        } catch (IllegalStateException e2) {
            if (!z2) {
                throw e2;
            }
            Logger.e(TAG, e2.toString(), e2);
        }
    }

    @Nullable
    private DisplayManager getDisplayManager() {
        Context context = getContext();
        if (context == null) {
            return null;
        }
        return (DisplayManager) context.getApplicationContext().getSystemService("display");
    }

    private int getViewPortScaleType() {
        switch (AnonymousClass2.$SwitchMap$androidx$camera$view$PreviewView$ScaleType[getScaleType().ordinal()]) {
            case 1:
                return 2;
            case 2:
                return 1;
            case 3:
                return 0;
            case 4:
            case 5:
            case 6:
                return 3;
            default:
                throw new IllegalStateException("Unexpected scale type: " + getScaleType());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(View view, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9) {
        if ((i4 - i2 == i8 - i6 && i5 - i3 == i9 - i7) ? false : true) {
            redrawPreview();
            attachToControllerIfReady(true);
        }
    }

    public static boolean shouldUseTextureView(@NonNull SurfaceRequest surfaceRequest, @NonNull ImplementationMode implementationMode) {
        int i2;
        boolean zEquals = surfaceRequest.getCamera().getCameraInfoInternal().getImplementationType().equals(CameraInfo.IMPLEMENTATION_TYPE_CAMERA2_LEGACY);
        boolean z2 = (DeviceQuirks.get(SurfaceViewStretchedQuirk.class) == null && DeviceQuirks.get(SurfaceViewNotCroppedByParentQuirk.class) == null) ? false : true;
        if (surfaceRequest.isRGBA8888Required() || Build.VERSION.SDK_INT <= 24 || zEquals || z2 || (i2 = AnonymousClass2.$SwitchMap$androidx$camera$view$PreviewView$ImplementationMode[implementationMode.ordinal()]) == 1) {
            return true;
        }
        if (i2 == 2) {
            return false;
        }
        throw new IllegalArgumentException("Invalid implementation mode: " + implementationMode);
    }

    private void startListeningToDisplayChange() {
        DisplayManager displayManager = getDisplayManager();
        if (displayManager == null) {
            return;
        }
        displayManager.registerDisplayListener(this.mDisplayRotationListener, new Handler(Looper.getMainLooper()));
    }

    private void stopListeningToDisplayChange() {
        DisplayManager displayManager = getDisplayManager();
        if (displayManager == null) {
            return;
        }
        displayManager.unregisterDisplayListener(this.mDisplayRotationListener);
    }

    @Nullable
    @UiThread
    public Bitmap getBitmap() {
        Threads.checkMainThread();
        PreviewViewImplementation previewViewImplementation = this.mImplementation;
        if (previewViewImplementation == null) {
            return null;
        }
        return previewViewImplementation.getBitmap();
    }

    @Nullable
    @UiThread
    public CameraController getController() {
        Threads.checkMainThread();
        return this.mCameraController;
    }

    @NonNull
    @UiThread
    public ImplementationMode getImplementationMode() {
        Threads.checkMainThread();
        return this.mImplementationMode;
    }

    @NonNull
    @UiThread
    public MeteringPointFactory getMeteringPointFactory() {
        Threads.checkMainThread();
        return this.mPreviewViewMeteringPointFactory;
    }

    @Nullable
    @TransformExperimental
    public OutputTransform getOutputTransform() {
        Matrix surfaceToPreviewViewMatrix;
        Threads.checkMainThread();
        try {
            surfaceToPreviewViewMatrix = this.mPreviewTransform.getSurfaceToPreviewViewMatrix(new Size(getWidth(), getHeight()), getLayoutDirection());
        } catch (IllegalStateException unused) {
            surfaceToPreviewViewMatrix = null;
        }
        Rect surfaceCropRect = this.mPreviewTransform.getSurfaceCropRect();
        if (surfaceToPreviewViewMatrix == null || surfaceCropRect == null) {
            Logger.d(TAG, "Transform info is not ready");
            return null;
        }
        surfaceToPreviewViewMatrix.preConcat(TransformUtils.getNormalizedToBuffer(surfaceCropRect));
        if (this.mImplementation instanceof TextureViewImplementation) {
            surfaceToPreviewViewMatrix.postConcat(getMatrix());
        } else {
            Logger.w(TAG, "PreviewView needs to be in COMPATIBLE mode for the transform to work correctly.");
        }
        return new OutputTransform(surfaceToPreviewViewMatrix, new Size(surfaceCropRect.width(), surfaceCropRect.height()));
    }

    @NonNull
    public LiveData<StreamState> getPreviewStreamState() {
        return this.mPreviewStreamStateLiveData;
    }

    @NonNull
    @UiThread
    public ScaleType getScaleType() {
        Threads.checkMainThread();
        return this.mPreviewTransform.getScaleType();
    }

    @NonNull
    @UiThread
    public Preview.SurfaceProvider getSurfaceProvider() {
        Threads.checkMainThread();
        return this.mSurfaceProvider;
    }

    @Nullable
    @UiThread
    public ViewPort getViewPort() {
        Threads.checkMainThread();
        if (getDisplay() == null) {
            return null;
        }
        return getViewPort(getDisplay().getRotation());
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        updateDisplayRotationIfNeeded();
        startListeningToDisplayChange();
        addOnLayoutChangeListener(this.mOnLayoutChangeListener);
        PreviewViewImplementation previewViewImplementation = this.mImplementation;
        if (previewViewImplementation != null) {
            previewViewImplementation.onAttachedToWindow();
        }
        attachToControllerIfReady(true);
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        removeOnLayoutChangeListener(this.mOnLayoutChangeListener);
        PreviewViewImplementation previewViewImplementation = this.mImplementation;
        if (previewViewImplementation != null) {
            previewViewImplementation.onDetachedFromWindow();
        }
        CameraController cameraController = this.mCameraController;
        if (cameraController != null) {
            cameraController.clearPreviewSurface();
        }
        stopListeningToDisplayChange();
    }

    @Override // android.view.View
    public boolean onTouchEvent(@NonNull MotionEvent motionEvent) {
        if (this.mCameraController == null) {
            return super.onTouchEvent(motionEvent);
        }
        boolean z2 = motionEvent.getPointerCount() == 1;
        boolean z3 = motionEvent.getAction() == 1;
        boolean z4 = motionEvent.getEventTime() - motionEvent.getDownTime() < ((long) ViewConfiguration.getLongPressTimeout());
        if (!z2 || !z3 || !z4) {
            return this.mScaleGestureDetector.onTouchEvent(motionEvent) || super.onTouchEvent(motionEvent);
        }
        this.mTouchUpEvent = motionEvent;
        performClick();
        return true;
    }

    @Override // android.view.View
    public boolean performClick() {
        if (this.mCameraController != null) {
            MotionEvent motionEvent = this.mTouchUpEvent;
            float x2 = motionEvent != null ? motionEvent.getX() : getWidth() / 2.0f;
            MotionEvent motionEvent2 = this.mTouchUpEvent;
            this.mCameraController.onTapToFocus(this.mPreviewViewMeteringPointFactory, x2, motionEvent2 != null ? motionEvent2.getY() : getHeight() / 2.0f);
        }
        this.mTouchUpEvent = null;
        return super.performClick();
    }

    @OptIn(markerClass = {TransformExperimental.class})
    @MainThread
    public void redrawPreview() {
        Threads.checkMainThread();
        PreviewViewImplementation previewViewImplementation = this.mImplementation;
        if (previewViewImplementation != null) {
            previewViewImplementation.redrawPreview();
        }
        this.mPreviewViewMeteringPointFactory.recalculate(new Size(getWidth(), getHeight()), getLayoutDirection());
        CameraController cameraController = this.mCameraController;
        if (cameraController != null) {
            cameraController.updatePreviewViewTransform(getOutputTransform());
        }
    }

    @UiThread
    public void setController(@Nullable CameraController cameraController) {
        Threads.checkMainThread();
        CameraController cameraController2 = this.mCameraController;
        if (cameraController2 != null && cameraController2 != cameraController) {
            cameraController2.clearPreviewSurface();
        }
        this.mCameraController = cameraController;
        attachToControllerIfReady(false);
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void setFrameUpdateListener(@NonNull Executor executor, @NonNull OnFrameUpdateListener onFrameUpdateListener) {
        if (this.mImplementationMode == ImplementationMode.PERFORMANCE) {
            throw new IllegalArgumentException("PERFORMANCE mode doesn't support frame update listener");
        }
        this.mOnFrameUpdateListener = onFrameUpdateListener;
        this.mOnFrameUpdateListenerExecutor = executor;
        PreviewViewImplementation previewViewImplementation = this.mImplementation;
        if (previewViewImplementation != null) {
            previewViewImplementation.setFrameUpdateListener(executor, onFrameUpdateListener);
        }
    }

    @UiThread
    public void setImplementationMode(@NonNull ImplementationMode implementationMode) {
        Threads.checkMainThread();
        this.mImplementationMode = implementationMode;
        if (implementationMode == ImplementationMode.PERFORMANCE && this.mOnFrameUpdateListener != null) {
            throw new IllegalArgumentException("PERFORMANCE mode doesn't support frame update listener");
        }
    }

    @UiThread
    public void setScaleType(@NonNull ScaleType scaleType) {
        Threads.checkMainThread();
        this.mPreviewTransform.setScaleType(scaleType);
        redrawPreview();
        attachToControllerIfReady(false);
    }

    public void updateDisplayRotationIfNeeded() {
        Display display;
        CameraInfoInternal cameraInfoInternal;
        if (!this.mUseDisplayRotation || (display = getDisplay()) == null || (cameraInfoInternal = this.mCameraInfoInternal) == null) {
            return;
        }
        this.mPreviewTransform.overrideWithDisplayRotation(cameraInfoInternal.getSensorRotationDegrees(display.getRotation()), display.getRotation());
    }

    @UiThread
    public PreviewView(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    @UiThread
    public PreviewView(@NonNull Context context, @Nullable AttributeSet attributeSet, int i2) {
        this(context, attributeSet, i2, 0);
    }

    @UiThread
    public PreviewView(@NonNull Context context, @Nullable AttributeSet attributeSet, int i2, int i3) {
        super(context, attributeSet, i2, i3);
        ImplementationMode implementationMode = DEFAULT_IMPL_MODE;
        this.mImplementationMode = implementationMode;
        PreviewTransformation previewTransformation = new PreviewTransformation();
        this.mPreviewTransform = previewTransformation;
        this.mUseDisplayRotation = true;
        this.mPreviewStreamStateLiveData = new MutableLiveData<>(StreamState.IDLE);
        this.mActiveStreamStateObserver = new AtomicReference<>();
        this.mPreviewViewMeteringPointFactory = new PreviewViewMeteringPointFactory(previewTransformation);
        this.mDisplayRotationListener = new DisplayRotationListener();
        this.mOnLayoutChangeListener = new View.OnLayoutChangeListener() { // from class: androidx.camera.view.i
            @Override // android.view.View.OnLayoutChangeListener
            public final void onLayoutChange(View view, int i4, int i5, int i6, int i7, int i8, int i9, int i10, int i11) {
                this.f1740c.lambda$new$0(view, i4, i5, i6, i7, i8, i9, i10, i11);
            }
        };
        this.mSurfaceProvider = new AnonymousClass1();
        Threads.checkMainThread();
        Resources.Theme theme = context.getTheme();
        int[] iArr = R.styleable.PreviewView;
        TypedArray typedArrayObtainStyledAttributes = theme.obtainStyledAttributes(attributeSet, iArr, i2, i3);
        ViewCompat.saveAttributeDataForStyleable(this, context, iArr, attributeSet, typedArrayObtainStyledAttributes, i2, i3);
        try {
            setScaleType(ScaleType.fromId(typedArrayObtainStyledAttributes.getInteger(R.styleable.PreviewView_scaleType, previewTransformation.getScaleType().getId())));
            setImplementationMode(ImplementationMode.fromId(typedArrayObtainStyledAttributes.getInteger(R.styleable.PreviewView_implementationMode, implementationMode.getId())));
            typedArrayObtainStyledAttributes.recycle();
            this.mScaleGestureDetector = new ScaleGestureDetector(context, new PinchToZoomOnScaleGestureListener());
            if (getBackground() == null) {
                setBackgroundColor(ContextCompat.getColor(getContext(), 17170444));
            }
        } catch (Throwable th) {
            typedArrayObtainStyledAttributes.recycle();
            throw th;
        }
    }

    @Nullable
    @SuppressLint({"WrongConstant"})
    @UiThread
    public ViewPort getViewPort(int i2) {
        Threads.checkMainThread();
        if (getWidth() == 0 || getHeight() == 0) {
            return null;
        }
        return new ViewPort.Builder(new Rational(getWidth(), getHeight()), i2).setScaleType(getViewPortScaleType()).setLayoutDirection(getLayoutDirection()).build();
    }
}
