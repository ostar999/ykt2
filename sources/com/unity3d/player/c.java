package com.unity3d.player;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureFailure;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.params.MeteringRectangle;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.Image;
import android.media.ImageReader;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Range;
import android.util.Size;
import android.util.SizeF;
import android.view.Surface;
import com.plv.business.model.ppt.PLVPPTAuthentic;
import java.util.Arrays;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/* loaded from: classes6.dex */
public final class c {

    /* renamed from: b, reason: collision with root package name */
    private static CameraManager f24058b;

    /* renamed from: c, reason: collision with root package name */
    private static String[] f24059c;

    /* renamed from: e, reason: collision with root package name */
    private static Semaphore f24060e = new Semaphore(1);

    /* renamed from: a, reason: collision with root package name */
    private e f24061a;

    /* renamed from: d, reason: collision with root package name */
    private CameraDevice f24062d;

    /* renamed from: f, reason: collision with root package name */
    private HandlerThread f24063f;

    /* renamed from: g, reason: collision with root package name */
    private Handler f24064g;

    /* renamed from: h, reason: collision with root package name */
    private Rect f24065h;

    /* renamed from: i, reason: collision with root package name */
    private Rect f24066i;

    /* renamed from: j, reason: collision with root package name */
    private int f24067j;

    /* renamed from: k, reason: collision with root package name */
    private int f24068k;

    /* renamed from: n, reason: collision with root package name */
    private int f24071n;

    /* renamed from: o, reason: collision with root package name */
    private int f24072o;

    /* renamed from: q, reason: collision with root package name */
    private Range f24074q;

    /* renamed from: s, reason: collision with root package name */
    private Image f24076s;

    /* renamed from: t, reason: collision with root package name */
    private CaptureRequest.Builder f24077t;

    /* renamed from: w, reason: collision with root package name */
    private int f24080w;

    /* renamed from: x, reason: collision with root package name */
    private SurfaceTexture f24081x;

    /* renamed from: l, reason: collision with root package name */
    private float f24069l = -1.0f;

    /* renamed from: m, reason: collision with root package name */
    private float f24070m = -1.0f;

    /* renamed from: p, reason: collision with root package name */
    private boolean f24073p = false;

    /* renamed from: r, reason: collision with root package name */
    private ImageReader f24075r = null;

    /* renamed from: u, reason: collision with root package name */
    private CameraCaptureSession f24078u = null;

    /* renamed from: v, reason: collision with root package name */
    private Object f24079v = new Object();

    /* renamed from: y, reason: collision with root package name */
    private Surface f24082y = null;

    /* renamed from: z, reason: collision with root package name */
    private int f24083z = a.f24091c;
    private CameraCaptureSession.CaptureCallback A = new CameraCaptureSession.CaptureCallback() { // from class: com.unity3d.player.c.1
        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public final void onCaptureCompleted(CameraCaptureSession cameraCaptureSession, CaptureRequest captureRequest, TotalCaptureResult totalCaptureResult) {
            c.this.a(captureRequest.getTag());
        }

        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public final void onCaptureFailed(CameraCaptureSession cameraCaptureSession, CaptureRequest captureRequest, CaptureFailure captureFailure) {
            f.Log(5, "Camera2: Capture session failed " + captureRequest.getTag() + " reason " + captureFailure.getReason());
            c.this.a(captureRequest.getTag());
        }

        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public final void onCaptureSequenceAborted(CameraCaptureSession cameraCaptureSession, int i2) {
        }

        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public final void onCaptureSequenceCompleted(CameraCaptureSession cameraCaptureSession, int i2, long j2) {
        }
    };
    private final CameraDevice.StateCallback B = new CameraDevice.StateCallback() { // from class: com.unity3d.player.c.3
        @Override // android.hardware.camera2.CameraDevice.StateCallback
        public final void onClosed(CameraDevice cameraDevice) {
            c.f24060e.release();
        }

        @Override // android.hardware.camera2.CameraDevice.StateCallback
        public final void onDisconnected(CameraDevice cameraDevice) {
            f.Log(5, "Camera2: CameraDevice disconnected.");
            c.this.a(cameraDevice);
            c.f24060e.release();
        }

        @Override // android.hardware.camera2.CameraDevice.StateCallback
        public final void onError(CameraDevice cameraDevice, int i2) {
            f.Log(6, "Camera2: Error opeining CameraDevice " + i2);
            c.this.a(cameraDevice);
            c.f24060e.release();
        }

        @Override // android.hardware.camera2.CameraDevice.StateCallback
        public final void onOpened(CameraDevice cameraDevice) {
            c.this.f24062d = cameraDevice;
            c.f24060e.release();
        }
    };
    private final ImageReader.OnImageAvailableListener C = new ImageReader.OnImageAvailableListener() { // from class: com.unity3d.player.c.4
        @Override // android.media.ImageReader.OnImageAvailableListener
        public final void onImageAvailable(ImageReader imageReader) {
            if (c.f24060e.tryAcquire()) {
                Image imageAcquireNextImage = imageReader.acquireNextImage();
                if (imageAcquireNextImage != null) {
                    Image.Plane[] planes = imageAcquireNextImage.getPlanes();
                    if (imageAcquireNextImage.getFormat() == 35 && planes != null && planes.length == 3) {
                        c.this.f24061a.a(planes[0].getBuffer(), planes[1].getBuffer(), planes[2].getBuffer(), planes[0].getRowStride(), planes[1].getRowStride(), planes[1].getPixelStride());
                    } else {
                        f.Log(6, "Camera2: Wrong image format.");
                    }
                    if (c.this.f24076s != null) {
                        c.this.f24076s.close();
                    }
                    c.this.f24076s = imageAcquireNextImage;
                }
                c.f24060e.release();
            }
        }
    };
    private final SurfaceTexture.OnFrameAvailableListener D = new SurfaceTexture.OnFrameAvailableListener() { // from class: com.unity3d.player.c.5
        @Override // android.graphics.SurfaceTexture.OnFrameAvailableListener
        public final void onFrameAvailable(SurfaceTexture surfaceTexture) {
            c.this.f24061a.a(surfaceTexture);
        }
    };

    /* JADX WARN: $VALUES field not found */
    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    public static final class a {

        /* renamed from: a, reason: collision with root package name */
        public static final int f24089a = 1;

        /* renamed from: b, reason: collision with root package name */
        public static final int f24090b = 2;

        /* renamed from: c, reason: collision with root package name */
        public static final int f24091c = 3;

        /* renamed from: d, reason: collision with root package name */
        private static final /* synthetic */ int[] f24092d = {1, 2, 3};
    }

    public c(e eVar) {
        this.f24061a = null;
        this.f24061a = eVar;
        g();
    }

    public static int a(Context context) {
        return c(context).length;
    }

    public static int a(Context context, int i2) {
        try {
            return ((Integer) b(context).getCameraCharacteristics(c(context)[i2]).get(CameraCharacteristics.SENSOR_ORIENTATION)).intValue();
        } catch (CameraAccessException e2) {
            f.Log(6, "Camera2: CameraAccessException " + e2);
            return 0;
        }
    }

    private static int a(Range[] rangeArr, int i2) {
        int i3 = -1;
        double d3 = Double.MAX_VALUE;
        for (int i4 = 0; i4 < rangeArr.length; i4++) {
            int iIntValue = ((Integer) rangeArr[i4].getLower()).intValue();
            int iIntValue2 = ((Integer) rangeArr[i4].getUpper()).intValue();
            float f2 = i2;
            if (f2 + 0.1f > iIntValue && f2 - 0.1f < iIntValue2) {
                return i2;
            }
            if (dMin < d3) {
                i3 = i4;
                d3 = dMin;
            }
        }
        return ((Integer) (i2 > ((Integer) rangeArr[i3].getUpper()).intValue() ? rangeArr[i3].getUpper() : rangeArr[i3].getLower())).intValue();
    }

    private static Rect a(Size[] sizeArr, double d3, double d4) {
        double d5 = Double.MAX_VALUE;
        int i2 = 0;
        int i3 = 0;
        for (int i4 = 0; i4 < sizeArr.length; i4++) {
            int width = sizeArr[i4].getWidth();
            int height = sizeArr[i4].getHeight();
            double dAbs = Math.abs(Math.log(d3 / width)) + Math.abs(Math.log(d4 / height));
            if (dAbs < d5) {
                i2 = width;
                i3 = height;
                d5 = dAbs;
            }
        }
        return new Rect(0, 0, i2, i3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(CameraDevice cameraDevice) {
        synchronized (this.f24079v) {
            this.f24078u = null;
        }
        cameraDevice.close();
        this.f24062d = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Object obj) {
        if (obj != "Focus") {
            if (obj == "Cancel focus") {
                synchronized (this.f24079v) {
                    if (this.f24078u != null) {
                        j();
                    }
                }
                return;
            }
            return;
        }
        this.f24073p = false;
        synchronized (this.f24079v) {
            if (this.f24078u != null) {
                try {
                    this.f24077t.set(CaptureRequest.CONTROL_AF_TRIGGER, 0);
                    this.f24077t.setTag("Regular");
                    this.f24078u.setRepeatingRequest(this.f24077t.build(), this.A, this.f24064g);
                } catch (CameraAccessException e2) {
                    f.Log(6, "Camera2: CameraAccessException " + e2);
                }
            }
        }
    }

    private static Size[] a(CameraCharacteristics cameraCharacteristics) {
        StreamConfigurationMap streamConfigurationMap = (StreamConfigurationMap) cameraCharacteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
        if (streamConfigurationMap == null) {
            f.Log(6, "Camera2: configuration map is not available.");
            return null;
        }
        Size[] outputSizes = streamConfigurationMap.getOutputSizes(35);
        if (outputSizes == null || outputSizes.length == 0) {
            return null;
        }
        return outputSizes;
    }

    private static CameraManager b(Context context) {
        if (f24058b == null) {
            f24058b = (CameraManager) context.getSystemService(PLVPPTAuthentic.PermissionType.CAMERA);
        }
        return f24058b;
    }

    private void b(CameraCharacteristics cameraCharacteristics) {
        int iIntValue = ((Integer) cameraCharacteristics.get(CameraCharacteristics.CONTROL_MAX_REGIONS_AF)).intValue();
        this.f24068k = iIntValue;
        if (iIntValue > 0) {
            this.f24066i = (Rect) cameraCharacteristics.get(CameraCharacteristics.SENSOR_INFO_ACTIVE_ARRAY_SIZE);
            float fWidth = this.f24065h.width() / this.f24065h.height();
            if (fWidth > r4.width() / this.f24066i.height()) {
                this.f24071n = 0;
                this.f24072o = (int) ((this.f24066i.height() - (this.f24066i.width() / fWidth)) / 2.0f);
            } else {
                this.f24072o = 0;
                this.f24071n = (int) ((this.f24066i.width() - (this.f24066i.height() * fWidth)) / 2.0f);
            }
            this.f24067j = Math.min(this.f24066i.width(), this.f24066i.height()) / 20;
        }
    }

    public static boolean b(Context context, int i2) {
        try {
            return ((Integer) b(context).getCameraCharacteristics(c(context)[i2]).get(CameraCharacteristics.LENS_FACING)).intValue() == 0;
        } catch (CameraAccessException e2) {
            f.Log(6, "Camera2: CameraAccessException " + e2);
            return false;
        }
    }

    public static boolean c(Context context, int i2) {
        try {
            return ((Integer) b(context).getCameraCharacteristics(c(context)[i2]).get(CameraCharacteristics.CONTROL_MAX_REGIONS_AF)).intValue() > 0;
        } catch (CameraAccessException e2) {
            f.Log(6, "Camera2: CameraAccessException " + e2);
            return false;
        }
    }

    private static String[] c(Context context) {
        if (f24059c == null) {
            try {
                f24059c = b(context).getCameraIdList();
            } catch (CameraAccessException e2) {
                f.Log(6, "Camera2: CameraAccessException " + e2);
                f24059c = new String[0];
            }
        }
        return f24059c;
    }

    public static int d(Context context, int i2) throws CameraAccessException {
        try {
            CameraCharacteristics cameraCharacteristics = b(context).getCameraCharacteristics(c(context)[i2]);
            float[] fArr = (float[]) cameraCharacteristics.get(CameraCharacteristics.LENS_INFO_AVAILABLE_FOCAL_LENGTHS);
            SizeF sizeF = (SizeF) cameraCharacteristics.get(CameraCharacteristics.SENSOR_INFO_PHYSICAL_SIZE);
            if (fArr.length > 0) {
                return (int) ((fArr[0] * 36.0f) / sizeF.getWidth());
            }
        } catch (CameraAccessException e2) {
            f.Log(6, "Camera2: CameraAccessException " + e2);
        }
        return 0;
    }

    public static int[] e(Context context, int i2) {
        try {
            Size[] sizeArrA = a(b(context).getCameraCharacteristics(c(context)[i2]));
            if (sizeArrA == null) {
                return null;
            }
            int[] iArr = new int[sizeArrA.length * 2];
            for (int i3 = 0; i3 < sizeArrA.length; i3++) {
                int i4 = i3 * 2;
                iArr[i4] = sizeArrA[i3].getWidth();
                iArr[i4 + 1] = sizeArrA[i3].getHeight();
            }
            return iArr;
        } catch (CameraAccessException e2) {
            f.Log(6, "Camera2: CameraAccessException " + e2);
            return null;
        }
    }

    private void g() {
        HandlerThread handlerThread = new HandlerThread("CameraBackground");
        this.f24063f = handlerThread;
        handlerThread.start();
        this.f24064g = new Handler(this.f24063f.getLooper());
    }

    private void h() throws InterruptedException {
        this.f24063f.quit();
        try {
            this.f24063f.join(4000L);
            this.f24063f = null;
            this.f24064g = null;
        } catch (InterruptedException e2) {
            this.f24063f.interrupt();
            f.Log(6, "Camera2: Interrupted while waiting for the background thread to finish " + e2);
        }
    }

    private void i() {
        try {
            Semaphore semaphore = f24060e;
            TimeUnit timeUnit = TimeUnit.SECONDS;
            if (!semaphore.tryAcquire(4L, timeUnit)) {
                f.Log(5, "Camera2: Timeout waiting to lock camera for closing.");
                return;
            }
            this.f24062d.close();
            try {
                if (!f24060e.tryAcquire(4L, timeUnit)) {
                    f.Log(5, "Camera2: Timeout waiting to close camera.");
                }
            } catch (InterruptedException e2) {
                f.Log(6, "Camera2: Interrupted while waiting to close camera " + e2);
            }
            this.f24062d = null;
            f24060e.release();
        } catch (InterruptedException e3) {
            f.Log(6, "Camera2: Interrupted while trying to lock camera for closing " + e3);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void j() throws CameraAccessException {
        try {
            if (this.f24068k != 0) {
                float f2 = this.f24069l;
                if (f2 >= 0.0f && f2 <= 1.0f) {
                    float f3 = this.f24070m;
                    if (f3 >= 0.0f && f3 <= 1.0f) {
                        this.f24073p = true;
                        int iWidth = this.f24066i.width();
                        int i2 = (int) (((iWidth - (r2 * 2)) * this.f24069l) + this.f24071n);
                        int iHeight = this.f24066i.height();
                        int i3 = (int) (((iHeight - (r3 * 2)) * (1.0d - this.f24070m)) + this.f24072o);
                        int iMax = Math.max(this.f24067j + 1, Math.min(i2, (this.f24066i.width() - this.f24067j) - 1));
                        int iMax2 = Math.max(this.f24067j + 1, Math.min(i3, (this.f24066i.height() - this.f24067j) - 1));
                        CaptureRequest.Builder builder = this.f24077t;
                        CaptureRequest.Key key = CaptureRequest.CONTROL_AF_REGIONS;
                        int i4 = this.f24067j;
                        builder.set(key, new MeteringRectangle[]{new MeteringRectangle(iMax - i4, iMax2 - i4, i4 * 2, i4 * 2, 999)});
                        this.f24077t.set(CaptureRequest.CONTROL_AF_MODE, 1);
                        this.f24077t.set(CaptureRequest.CONTROL_AF_TRIGGER, 1);
                        this.f24077t.setTag("Focus");
                        this.f24078u.capture(this.f24077t.build(), this.A, this.f24064g);
                        return;
                    }
                }
            }
            this.f24077t.set(CaptureRequest.CONTROL_AF_MODE, 4);
            this.f24077t.setTag("Regular");
            CameraCaptureSession cameraCaptureSession = this.f24078u;
            if (cameraCaptureSession != null) {
                cameraCaptureSession.setRepeatingRequest(this.f24077t.build(), this.A, this.f24064g);
            }
        } catch (CameraAccessException e2) {
            f.Log(6, "Camera2: CameraAccessException " + e2);
        }
    }

    private void k() throws CameraAccessException {
        try {
            CameraCaptureSession cameraCaptureSession = this.f24078u;
            if (cameraCaptureSession != null) {
                cameraCaptureSession.stopRepeating();
                this.f24077t.set(CaptureRequest.CONTROL_AF_TRIGGER, 2);
                this.f24077t.set(CaptureRequest.CONTROL_AF_MODE, 0);
                this.f24077t.setTag("Cancel focus");
                this.f24078u.capture(this.f24077t.build(), this.A, this.f24064g);
            }
        } catch (CameraAccessException e2) {
            f.Log(6, "Camera2: CameraAccessException " + e2);
        }
    }

    public final Rect a() {
        return this.f24065h;
    }

    public final boolean a(float f2, float f3) {
        if (this.f24068k <= 0) {
            return false;
        }
        if (this.f24073p) {
            f.Log(5, "Camera2: Setting manual focus point already started.");
            return false;
        }
        this.f24069l = f2;
        this.f24070m = f3;
        synchronized (this.f24079v) {
            if (this.f24078u != null && this.f24083z != a.f24090b) {
                k();
            }
        }
        return true;
    }

    public final boolean a(Context context, int i2, int i3, int i4, int i5, int i6) throws CameraAccessException {
        try {
            CameraCharacteristics cameraCharacteristics = f24058b.getCameraCharacteristics(c(context)[i2]);
            if (((Integer) cameraCharacteristics.get(CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL)).intValue() == 2) {
                f.Log(5, "Camera2: only LEGACY hardware level is supported.");
                return false;
            }
            Size[] sizeArrA = a(cameraCharacteristics);
            if (sizeArrA != null && sizeArrA.length != 0) {
                this.f24065h = a(sizeArrA, i3, i4);
                Range[] rangeArr = (Range[]) cameraCharacteristics.get(CameraCharacteristics.CONTROL_AE_AVAILABLE_TARGET_FPS_RANGES);
                if (rangeArr != null && rangeArr.length != 0) {
                    int iA = a(rangeArr, i5);
                    this.f24074q = new Range(Integer.valueOf(iA), Integer.valueOf(iA));
                    try {
                        Semaphore semaphore = f24060e;
                        TimeUnit timeUnit = TimeUnit.SECONDS;
                        if (!semaphore.tryAcquire(4L, timeUnit)) {
                            f.Log(5, "Camera2: Timeout waiting to lock camera for opening.");
                            return false;
                        }
                        try {
                            f24058b.openCamera(c(context)[i2], this.B, this.f24064g);
                            try {
                            } catch (InterruptedException e2) {
                                f.Log(6, "Camera2: Interrupted while waiting to open camera " + e2);
                            }
                            if (!f24060e.tryAcquire(4L, timeUnit)) {
                                f.Log(5, "Camera2: Timeout waiting to open camera.");
                                return false;
                            }
                            f24060e.release();
                            this.f24080w = i6;
                            b(cameraCharacteristics);
                            return this.f24062d != null;
                        } catch (CameraAccessException e3) {
                            f.Log(6, "Camera2: CameraAccessException " + e3);
                            f24060e.release();
                            return false;
                        }
                    } catch (InterruptedException e4) {
                        f.Log(6, "Camera2: Interrupted while trying to lock camera for opening " + e4);
                        return false;
                    }
                }
                f.Log(6, "Camera2: target FPS ranges are not avialable.");
            }
            return false;
        } catch (CameraAccessException e5) {
            f.Log(6, "Camera2: CameraAccessException " + e5);
            return false;
        }
    }

    public final void b() throws InterruptedException {
        if (this.f24062d != null) {
            e();
            i();
            this.A = null;
            this.f24082y = null;
            this.f24081x = null;
            Image image = this.f24076s;
            if (image != null) {
                image.close();
                this.f24076s = null;
            }
            ImageReader imageReader = this.f24075r;
            if (imageReader != null) {
                imageReader.close();
                this.f24075r = null;
            }
        }
        h();
    }

    public final void c() throws CameraAccessException {
        if (this.f24075r == null) {
            ImageReader imageReaderNewInstance = ImageReader.newInstance(this.f24065h.width(), this.f24065h.height(), 35, 2);
            this.f24075r = imageReaderNewInstance;
            imageReaderNewInstance.setOnImageAvailableListener(this.C, this.f24064g);
            this.f24076s = null;
            if (this.f24080w != 0) {
                SurfaceTexture surfaceTexture = new SurfaceTexture(this.f24080w);
                this.f24081x = surfaceTexture;
                surfaceTexture.setDefaultBufferSize(this.f24065h.width(), this.f24065h.height());
                this.f24081x.setOnFrameAvailableListener(this.D, this.f24064g);
                this.f24082y = new Surface(this.f24081x);
            }
        }
        try {
            CameraCaptureSession cameraCaptureSession = this.f24078u;
            if (cameraCaptureSession == null) {
                CameraDevice cameraDevice = this.f24062d;
                Surface surface = this.f24082y;
                cameraDevice.createCaptureSession(surface != null ? Arrays.asList(surface, this.f24075r.getSurface()) : Arrays.asList(this.f24075r.getSurface()), new CameraCaptureSession.StateCallback() { // from class: com.unity3d.player.c.2
                    @Override // android.hardware.camera2.CameraCaptureSession.StateCallback
                    public final void onConfigureFailed(CameraCaptureSession cameraCaptureSession2) {
                        f.Log(6, "Camera2: CaptureSession configuration failed.");
                    }

                    @Override // android.hardware.camera2.CameraCaptureSession.StateCallback
                    public final void onConfigured(CameraCaptureSession cameraCaptureSession2) {
                        String str;
                        if (c.this.f24062d == null) {
                            return;
                        }
                        synchronized (c.this.f24079v) {
                            c.this.f24078u = cameraCaptureSession2;
                            try {
                                c cVar = c.this;
                                cVar.f24077t = cVar.f24062d.createCaptureRequest(1);
                                if (c.this.f24082y != null) {
                                    c.this.f24077t.addTarget(c.this.f24082y);
                                }
                                c.this.f24077t.addTarget(c.this.f24075r.getSurface());
                                c.this.f24077t.set(CaptureRequest.CONTROL_AE_TARGET_FPS_RANGE, c.this.f24074q);
                                c.this.j();
                            } catch (CameraAccessException e2) {
                                str = "Camera2: CameraAccessException " + e2;
                                f.Log(6, str);
                            } catch (IllegalStateException e3) {
                                str = "Camera2: IllegalStateException " + e3;
                                f.Log(6, str);
                            }
                        }
                    }
                }, this.f24064g);
            } else if (this.f24083z == a.f24090b) {
                cameraCaptureSession.setRepeatingRequest(this.f24077t.build(), this.A, this.f24064g);
            }
            this.f24083z = a.f24089a;
        } catch (CameraAccessException e2) {
            f.Log(6, "Camera2: CameraAccessException " + e2);
        }
    }

    public final void d() {
        synchronized (this.f24079v) {
            CameraCaptureSession cameraCaptureSession = this.f24078u;
            if (cameraCaptureSession != null) {
                try {
                    cameraCaptureSession.stopRepeating();
                    this.f24083z = a.f24090b;
                } catch (CameraAccessException e2) {
                    f.Log(6, "Camera2: CameraAccessException " + e2);
                }
            }
        }
    }

    public final void e() {
        synchronized (this.f24079v) {
            CameraCaptureSession cameraCaptureSession = this.f24078u;
            if (cameraCaptureSession != null) {
                try {
                    cameraCaptureSession.abortCaptures();
                } catch (CameraAccessException e2) {
                    f.Log(6, "Camera2: CameraAccessException " + e2);
                }
                this.f24078u.close();
                this.f24078u = null;
                this.f24083z = a.f24091c;
            }
        }
    }
}
