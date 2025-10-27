package com.tencent.liteav.capturer;

import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.util.TXCBuild;
import com.tencent.liteav.basic.util.e;
import com.yikaobang.yixue.R2;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import kotlinx.coroutines.DebugKt;

/* loaded from: classes6.dex */
public class a implements Camera.AutoFocusCallback, Camera.ErrorCallback, Camera.PreviewCallback {

    /* renamed from: c, reason: collision with root package name */
    private Camera f19155c;

    /* renamed from: e, reason: collision with root package name */
    private b f19157e;

    /* renamed from: h, reason: collision with root package name */
    private int f19160h;

    /* renamed from: i, reason: collision with root package name */
    private int f19161i;

    /* renamed from: j, reason: collision with root package name */
    private int f19162j;

    /* renamed from: k, reason: collision with root package name */
    private int f19163k;

    /* renamed from: l, reason: collision with root package name */
    private SurfaceTexture f19164l;

    /* renamed from: m, reason: collision with root package name */
    private boolean f19165m;

    /* renamed from: n, reason: collision with root package name */
    private boolean f19166n;

    /* renamed from: o, reason: collision with root package name */
    private boolean f19167o;

    /* renamed from: q, reason: collision with root package name */
    private int f19169q;

    /* renamed from: r, reason: collision with root package name */
    private int f19170r;

    /* renamed from: a, reason: collision with root package name */
    private Matrix f19153a = new Matrix();

    /* renamed from: b, reason: collision with root package name */
    private int f19154b = 0;

    /* renamed from: d, reason: collision with root package name */
    private boolean f19156d = true;

    /* renamed from: f, reason: collision with root package name */
    private int f19158f = 15;

    /* renamed from: g, reason: collision with root package name */
    private int f19159g = 1;

    /* renamed from: p, reason: collision with root package name */
    private boolean f19168p = false;

    /* renamed from: s, reason: collision with root package name */
    private boolean f19171s = false;

    /* renamed from: t, reason: collision with root package name */
    private boolean f19172t = false;

    /* renamed from: com.tencent.liteav.capturer.a$a, reason: collision with other inner class name */
    public enum EnumC0333a {
        RESOLUTION_INVALID(-1, -1),
        RESOLUTION_180_320(180, 320),
        RESOLUTION_270_480(270, 480),
        RESOLUTION_320_480(320, 480),
        RESOLUTION_360_640(360, 640),
        RESOLUTION_540_960(R2.attr.bl_checked_gradient_centerY, 960),
        RESOLUTION_720_1280(720, 1280),
        RESOLUTION_1080_1920(R2.attr.color_hot_circle_one_end, R2.attr.iconTint),
        RESOLUTION_HIGHEST(R2.attr.color_hot_circle_one_end, R2.attr.iconTint);

        private final int mHeight;
        private final int mWidth;

        EnumC0333a(int i2, int i3) {
            this.mWidth = i2;
            this.mHeight = i3;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public int a() {
            return this.mWidth;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public int b() {
            return this.mHeight;
        }
    }

    public void a(b bVar) {
        this.f19157e = bVar;
    }

    public boolean b() {
        Camera.Parameters parametersA;
        return this.f19155c != null && (parametersA = a()) != null && parametersA.getMaxZoom() > 0 && parametersA.isZoomSupported();
    }

    public boolean c() {
        Camera.Parameters parametersA;
        List<String> supportedFlashModes;
        return (this.f19155c == null || (parametersA = a()) == null || (supportedFlashModes = parametersA.getSupportedFlashModes()) == null || !supportedFlashModes.contains("torch")) ? false : true;
    }

    public boolean d() {
        return this.f19165m;
    }

    public boolean e() {
        Camera.Parameters parametersA;
        return (this.f19155c == null || (parametersA = a()) == null || parametersA.getMaxNumDetectedFaces() <= 0) ? false : true;
    }

    public int f() {
        Camera.Parameters parametersA = a();
        if (parametersA == null || parametersA.getMaxZoom() <= 0 || !parametersA.isZoomSupported()) {
            return 0;
        }
        return parametersA.getMaxZoom();
    }

    public void g() {
        TXCLog.i("TXCCameraCapturer", "stopCapture " + this.f19155c);
        Camera camera = this.f19155c;
        if (camera != null) {
            try {
                try {
                    camera.setErrorCallback(null);
                    this.f19155c.setPreviewCallback(null);
                    this.f19155c.stopPreview();
                    this.f19155c.release();
                } catch (Exception e2) {
                    TXCLog.e("TXCCameraCapturer", "stop capture failed.", e2);
                }
            } finally {
                this.f19155c = null;
                this.f19164l = null;
            }
        }
        TXCLog.i("TXCCameraCapturer", "stopCapture end.");
    }

    public int h() {
        return this.f19162j;
    }

    public boolean i() {
        return this.f19156d;
    }

    public int j() {
        return this.f19160h;
    }

    public int k() {
        return this.f19161i;
    }

    public Camera l() {
        return this.f19155c;
    }

    @Override // android.hardware.Camera.AutoFocusCallback
    public void onAutoFocus(boolean z2, Camera camera) {
        if (z2) {
            TXCLog.i("TXCCameraCapturer", "AUTO focus success");
        } else {
            TXCLog.i("TXCCameraCapturer", "AUTO focus failed");
        }
    }

    @Override // android.hardware.Camera.ErrorCallback
    public void onError(int i2, Camera camera) {
        b bVar;
        TXCLog.w("TXCCameraCapturer", "camera catch error " + i2);
        if ((i2 == 1 || i2 == 2 || i2 == 100) && (bVar = this.f19157e) != null) {
            bVar.m();
        }
    }

    @Override // android.hardware.Camera.PreviewCallback
    public void onPreviewFrame(byte[] bArr, Camera camera) {
        b bVar = this.f19157e;
        if (bVar != null) {
            bVar.a(bArr);
        }
    }

    public void a(SurfaceTexture surfaceTexture) {
        this.f19164l = surfaceTexture;
    }

    public int d(boolean z2) {
        try {
            TXCLog.i("TXCCameraCapturer", "trtc_capture: start capture");
            if (this.f19164l == null) {
                TXCLog.i("TXCCameraCapturer", "start capture, but preview SurfaceTexture is null");
                return -2;
            }
            if (this.f19155c != null) {
                g();
            }
            Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
            int i2 = -1;
            int i3 = -1;
            for (int i4 = 0; i4 < Camera.getNumberOfCameras(); i4++) {
                Camera.getCameraInfo(i4, cameraInfo);
                TXCLog.i("TXCCameraCapturer", "camera index " + i4 + ", facing = " + cameraInfo.facing);
                int i5 = cameraInfo.facing;
                if (i5 == 1 && i2 == -1) {
                    i2 = i4;
                }
                if (i5 == 0 && i3 == -1) {
                    i3 = i4;
                }
            }
            TXCLog.i("TXCCameraCapturer", "camera front, id = " + i2);
            TXCLog.i("TXCCameraCapturer", "camera back , id = " + i3);
            if (i2 == -1 && i3 != -1) {
                i2 = i3;
            }
            if (i3 == -1 && i2 != -1) {
                i3 = i2;
            }
            this.f19156d = z2;
            if (z2) {
                this.f19155c = Camera.open(i2);
            } else {
                this.f19155c = Camera.open(i3);
            }
            Camera.Parameters parameters = this.f19155c.getParameters();
            List<String> supportedFocusModes = parameters.getSupportedFocusModes();
            if (this.f19171s && supportedFocusModes != null && supportedFocusModes.contains("auto")) {
                TXCLog.i("TXCCameraCapturer", "support FOCUS_MODE_AUTO");
                parameters.setFocusMode("auto");
            } else if (supportedFocusModes != null && supportedFocusModes.contains("continuous-video")) {
                TXCLog.i("TXCCameraCapturer", "support FOCUS_MODE_CONTINUOUS_VIDEO");
                parameters.setFocusMode("continuous-video");
            }
            if (TXCBuild.VersionInt() >= 14) {
                if (parameters.getMaxNumFocusAreas() > 0) {
                    this.f19165m = true;
                }
                if (parameters.getMaxNumMeteringAreas() > 0) {
                    this.f19166n = true;
                }
            }
            if (this.f19168p) {
                parameters.setPreviewFormat(17);
                this.f19155c.setPreviewCallback(this);
            }
            e eVarB = b(this.f19172t, this.f19169q, this.f19170r);
            e eVarA = a(this.f19172t, parameters, Math.max(eVarB.f18712a, eVarB.f18713b), Math.min(eVarB.f18712a, eVarB.f18713b));
            int i6 = eVarA.f18712a;
            this.f19160h = i6;
            int i7 = eVarA.f18713b;
            this.f19161i = i7;
            parameters.setPreviewSize(i6, i7);
            int[] iArrE = e(this.f19158f);
            if (iArrE != null) {
                parameters.setPreviewFpsRange(iArrE[0], iArrE[1]);
                TXCLog.i("TXCCameraCapturer", "TXCCameraCapturer : startCapture ()parameters.setPreviewFpsRangeMIN:" + iArrE[0] + "MAX" + iArrE[1]);
            } else {
                parameters.setPreviewFrameRate(d(this.f19158f));
                TXCLog.i("TXCCameraCapturer", "TXCCameraCapturer : startCapture ()parameters.setPreviewFrameRate(getSupportedFPS(mFPS));" + d(this.f19158f));
            }
            if (!this.f19156d) {
                i2 = i3;
            }
            int iF = f(i2);
            this.f19163k = iF;
            this.f19162j = (((iF - 90) + (this.f19159g * 90)) + 360) % 360;
            this.f19155c.setDisplayOrientation(0);
            TXCLog.i("TXCCameraCapturer", "vsize camera orientation " + this.f19163k + ", preview " + this.f19162j + ", home orientation " + this.f19159g);
            this.f19155c.setPreviewTexture(this.f19164l);
            this.f19155c.setParameters(parameters);
            this.f19155c.setErrorCallback(this);
            this.f19155c.startPreview();
            return 0;
        } catch (Throwable th) {
            TXCLog.e("TXCCameraCapturer", "open camera failed." + th.getMessage());
            return -1;
        }
    }

    public Camera.Parameters a() {
        Camera camera = this.f19155c;
        if (camera == null) {
            return null;
        }
        try {
            return camera.getParameters();
        } catch (Exception e2) {
            TXCLog.e("TXCCameraCapturer", "getCameraParameters error ", e2);
            return null;
        }
    }

    private int[] e(int i2) {
        List<int[]> supportedPreviewFpsRange;
        int i3 = i2 * 1000;
        String str = "camera supported preview fps range: wantFPS = " + i3 + "\n";
        Camera.Parameters parametersA = a();
        if (parametersA == null || (supportedPreviewFpsRange = parametersA.getSupportedPreviewFpsRange()) == null || supportedPreviewFpsRange.size() <= 0) {
            return null;
        }
        int[] iArr = supportedPreviewFpsRange.get(0);
        Collections.sort(supportedPreviewFpsRange, new Comparator<int[]>() { // from class: com.tencent.liteav.capturer.a.2
            @Override // java.util.Comparator
            /* renamed from: a, reason: merged with bridge method [inline-methods] */
            public int compare(int[] iArr2, int[] iArr3) {
                return iArr2[1] - iArr3[1];
            }
        });
        for (int[] iArr2 : supportedPreviewFpsRange) {
            str = str + "camera supported preview fps range: " + iArr2[0] + " - " + iArr2[1] + "\n";
        }
        Iterator<int[]> it = supportedPreviewFpsRange.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            int[] next = it.next();
            if (next[0] <= i3 && i3 <= next[1]) {
                iArr = next;
                break;
            }
        }
        TXCLog.i("TXCCameraCapturer", str + "choose preview fps range: " + iArr[0] + " - " + iArr[1]);
        return iArr;
    }

    private int f(int i2) {
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        Camera.getCameraInfo(i2, cameraInfo);
        StringBuilder sb = new StringBuilder();
        sb.append("vsize camera orientation ");
        sb.append(cameraInfo.orientation);
        sb.append(", front ");
        sb.append(cameraInfo.facing == 1);
        TXCLog.i("TXCCameraCapturer", sb.toString());
        int i3 = cameraInfo.orientation;
        if (i3 == 0 || i3 == 180) {
            i3 += 90;
        }
        if (cameraInfo.facing == 1) {
            return (360 - i3) % 360;
        }
        return (i3 + 360) % 360;
    }

    public void b(boolean z2) {
        this.f19172t = z2;
        TXCLog.i("TXCCameraCapturer", "set performance mode to " + z2);
    }

    public void c(boolean z2) {
        this.f19171s = z2;
    }

    public boolean a(boolean z2) {
        Camera.Parameters parametersA;
        boolean z3;
        this.f19167o = z2;
        if (this.f19155c == null || (parametersA = a()) == null) {
            return false;
        }
        List<String> supportedFlashModes = parametersA.getSupportedFlashModes();
        if (z2) {
            if (supportedFlashModes != null && supportedFlashModes.contains("torch")) {
                TXCLog.i("TXCCameraCapturer", "set FLASH_MODE_TORCH");
                parametersA.setFlashMode("torch");
                z3 = true;
            }
            z3 = false;
        } else {
            if (supportedFlashModes != null && supportedFlashModes.contains(DebugKt.DEBUG_PROPERTY_VALUE_OFF)) {
                TXCLog.i("TXCCameraCapturer", "set FLASH_MODE_OFF");
                parametersA.setFlashMode(DebugKt.DEBUG_PROPERTY_VALUE_OFF);
                z3 = true;
            }
            z3 = false;
        }
        try {
            this.f19155c.setParameters(parametersA);
            return z3;
        } catch (Exception e2) {
            TXCLog.e("TXCCameraCapturer", "setParameters failed.", e2);
            return false;
        }
    }

    public boolean b(int i2) {
        if (this.f19155c == null) {
            return false;
        }
        Camera.Parameters parametersA = a();
        if (parametersA != null && parametersA.getMaxZoom() > 0 && parametersA.isZoomSupported()) {
            if (i2 >= 0 && i2 <= parametersA.getMaxZoom()) {
                try {
                    parametersA.setZoom(i2);
                    this.f19155c.setParameters(parametersA);
                    return true;
                } catch (Exception e2) {
                    TXCLog.e("TXCCameraCapturer", "set zoom failed.", e2);
                    return false;
                }
            }
            TXCLog.e("TXCCameraCapturer", "invalid zoom value : " + i2 + ", while max zoom is " + parametersA.getMaxZoom());
            return false;
        }
        TXCLog.e("TXCCameraCapturer", "camera not support zoom!");
        return false;
    }

    public void c(int i2) {
        TXCLog.w("TXCCameraCapturer", "vsize setHomeOrientation " + i2);
        this.f19159g = i2;
        this.f19162j = (((this.f19163k + (-90)) + (i2 * 90)) + 360) % 360;
    }

    private static e b(boolean z2, int i2, int i3) {
        if (z2) {
            return new e(i2, i3);
        }
        e[] eVarArr = {new e(R2.attr.color_hot_circle_one_end, R2.attr.iconTint)};
        float fMin = Math.min(i2, i3);
        float fMax = Math.max(i2, i3);
        e eVar = eVarArr[0];
        int i4 = eVar.f18712a;
        if (fMin <= i4) {
            int i5 = eVar.f18713b;
            if (fMax <= i5) {
                float fMin2 = Math.min(i4 / fMin, i5 / fMax);
                i2 = (int) (i2 * fMin2);
                i3 = (int) (i3 * fMin2);
            }
        }
        return new e(i2, i3);
    }

    public void a(EnumC0333a enumC0333a) {
        if (enumC0333a != EnumC0333a.RESOLUTION_INVALID) {
            this.f19169q = enumC0333a.a();
            this.f19170r = enumC0333a.b();
        }
        TXCLog.i("TXCCameraCapturer", "set resolution " + enumC0333a);
    }

    public void a(int i2) {
        TXCLog.i("TXCCameraCapturer", "TXCCameraCapturer : setFPS ():" + i2);
        this.f19158f = i2;
    }

    public void a(float f2, float f3) {
        if (this.f19171s) {
            try {
                this.f19155c.cancelAutoFocus();
                Camera.Parameters parameters = this.f19155c.getParameters();
                if (this.f19165m) {
                    ArrayList arrayList = new ArrayList();
                    arrayList.add(new Camera.Area(a(f2, f3, 2.0f), 1000));
                    parameters.setFocusAreas(arrayList);
                }
                if (this.f19166n) {
                    ArrayList arrayList2 = new ArrayList();
                    arrayList2.add(new Camera.Area(a(f2, f3, 3.0f), 1000));
                    parameters.setMeteringAreas(arrayList2);
                }
                this.f19155c.setParameters(parameters);
                this.f19155c.autoFocus(this);
            } catch (Exception unused) {
            }
        }
    }

    private Rect a(float f2, float f3, float f4) {
        float f5 = f4 * 200.0f;
        if (this.f19156d) {
            f2 = 1.0f - f2;
        }
        int i2 = 0;
        while (i2 < this.f19162j / 90) {
            float f6 = (-(-(f3 - 0.5f))) + 0.5f;
            i2++;
            f3 = (-(f2 - 0.5f)) + 0.5f;
            f2 = f6;
        }
        int i3 = (int) ((f2 * 2000.0f) - 1000.0f);
        int i4 = (int) ((f3 * 2000.0f) - 1000.0f);
        if (i3 < -1000) {
            i3 = -1000;
        }
        if (i4 < -1000) {
            i4 = -1000;
        }
        int i5 = (int) f5;
        int i6 = i3 + i5;
        int i7 = i5 + i4;
        if (i6 > 1000) {
            i6 = 1000;
        }
        if (i7 > 1000) {
            i7 = 1000;
        }
        return new Rect(i3, i4, i6, i7);
    }

    public void a(boolean z2, int i2, int i3) {
        this.f19168p = z2;
        this.f19169q = i2;
        this.f19170r = i3;
        TXCLog.i("TXCCameraCapturer", "setCaptureBuffer %b, width: %d, height: %d", Boolean.valueOf(z2), Integer.valueOf(i2), Integer.valueOf(i3));
    }

    private static e a(boolean z2, Camera.Parameters parameters, int i2, int i3) {
        TXCLog.d("TXCCameraCapturer", "camera preview wanted: %d x %d", Integer.valueOf(i2), Integer.valueOf(i3));
        List<Camera.Size> supportedPreviewSizes = parameters.getSupportedPreviewSizes();
        float f2 = (i2 * 1.0f) / i3;
        ArrayList<Camera.Size> arrayList = new ArrayList();
        int i4 = Integer.MAX_VALUE;
        for (Camera.Size size : supportedPreviewSizes) {
            TXCLog.d("TXCCameraCapturer", "camera support preview size: %dx%d", Integer.valueOf(size.width), Integer.valueOf(size.height));
            int iRound = (z2 || (size.width >= 640 && size.height >= 480)) ? Math.round(Math.abs(((size.width * 1.0f) / size.height) - f2) * 10.0f) : Integer.MAX_VALUE;
            if (iRound < i4) {
                arrayList.clear();
                arrayList.add(size);
                i4 = iRound;
            } else if (iRound == i4) {
                arrayList.add(size);
            }
        }
        Collections.sort(arrayList, new Comparator<Camera.Size>() { // from class: com.tencent.liteav.capturer.a.1
            @Override // java.util.Comparator
            /* renamed from: a, reason: merged with bridge method [inline-methods] */
            public int compare(Camera.Size size2, Camera.Size size3) {
                return (size3.width * size3.height) - (size2.width * size2.height);
            }
        });
        Camera.Size size2 = (Camera.Size) arrayList.get(0);
        float f3 = i2 * i3;
        float fAbs = 2.1474836E9f;
        for (Camera.Size size3 : arrayList) {
            TXCLog.i("TXCCameraCapturer", "size in same buck: %dx%d", Integer.valueOf(size3.width), Integer.valueOf(size3.height));
            float f4 = size3.width * size3.height;
            if (f4 / f3 >= 0.9d) {
                float f5 = f4 - f3;
                if (Math.abs(f5) < fAbs) {
                    fAbs = Math.abs(f5);
                    size2 = size3;
                }
            }
        }
        TXCLog.i("TXCCameraCapturer", "best match preview size: %d x %d", Integer.valueOf(size2.width), Integer.valueOf(size2.height));
        return new e(size2.width, size2.height);
    }

    private int d(int i2) {
        Camera.Parameters parametersA = a();
        if (parametersA == null) {
            return 1;
        }
        List<Integer> supportedPreviewFrameRates = parametersA.getSupportedPreviewFrameRates();
        if (supportedPreviewFrameRates == null) {
            TXCLog.e("TXCCameraCapturer", "getSupportedFPS error");
            return 1;
        }
        int iIntValue = supportedPreviewFrameRates.get(0).intValue();
        for (int i3 = 0; i3 < supportedPreviewFrameRates.size(); i3++) {
            int iIntValue2 = supportedPreviewFrameRates.get(i3).intValue();
            if (Math.abs(iIntValue2 - i2) - Math.abs(iIntValue - i2) < 0) {
                iIntValue = iIntValue2;
            }
        }
        TXCLog.i("TXCCameraCapturer", "choose fps=" + iIntValue);
        return iIntValue;
    }
}
