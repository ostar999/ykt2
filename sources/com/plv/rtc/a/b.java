package com.plv.rtc.a;

import android.content.Context;
import android.hardware.Camera;
import android.util.Log;
import android.view.WindowManager;
import androidx.annotation.FloatRange;
import io.agora.rtc.gl.RendererCommon;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes5.dex */
public class b extends com.plv.rtc.a.a {

    /* renamed from: l, reason: collision with root package name */
    private static final String f10856l = "b";

    /* renamed from: f, reason: collision with root package name */
    private Context f10857f;

    /* renamed from: g, reason: collision with root package name */
    private WindowManager f10858g;

    /* renamed from: h, reason: collision with root package name */
    private Camera f10859h;

    /* renamed from: i, reason: collision with root package name */
    private Camera.CameraInfo f10860i;

    /* renamed from: j, reason: collision with root package name */
    private int f10861j;

    /* renamed from: k, reason: collision with root package name */
    private boolean f10862k;

    public class a implements Comparator<Camera.Size> {
        @Override // java.util.Comparator
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public int compare(Camera.Size size, Camera.Size size2) {
            int i2 = size.width;
            int i3 = size2.width;
            if (i2 == i3) {
                return 0;
            }
            return i2 < i3 ? -1 : 1;
        }
    }

    public b(Context context, int i2, int i3) {
        super(null, i2, i3);
        this.f10861j = 1;
        this.f10862k = false;
        this.f10857f = context;
        this.f10858g = (WindowManager) context.getSystemService("window");
    }

    private void b(Camera.Parameters parameters) {
        List<int[]> supportedPreviewFpsRange = parameters.getSupportedPreviewFpsRange();
        parameters.setPreviewFpsRange(supportedPreviewFpsRange.get(supportedPreviewFpsRange.size() - 1)[0], supportedPreviewFpsRange.get(supportedPreviewFpsRange.size() - 1)[1]);
    }

    private int n() {
        int rotation = this.f10858g.getDefaultDisplay().getRotation();
        if (rotation == 1) {
            return 90;
        }
        if (rotation != 2) {
            return rotation != 3 ? 0 : 270;
        }
        return 180;
    }

    private void o() {
        if (this.f10859h != null) {
            return;
        }
        this.f10860i = new Camera.CameraInfo();
        int numberOfCameras = Camera.getNumberOfCameras();
        int i2 = 0;
        while (true) {
            if (i2 >= numberOfCameras) {
                break;
            }
            Camera.getCameraInfo(i2, this.f10860i);
            if (this.f10860i.facing == this.f10861j) {
                this.f10859h = Camera.open(i2);
                break;
            }
            i2++;
        }
        if (this.f10859h == null) {
            Log.d(f10856l, "No front-facing camera found; opening default");
            this.f10859h = Camera.open();
        }
        Camera camera = this.f10859h;
        if (camera == null) {
            throw new RuntimeException("Unable to open camera");
        }
        Camera.Parameters parameters = camera.getParameters();
        b(parameters);
        a(parameters);
        parameters.setRecordingHint(true);
        this.f10859h.setParameters(parameters);
        Camera.Size previewSize = parameters.getPreviewSize();
        String str = previewSize.width + "x" + previewSize.height;
        Log.i(f10856l, "Camera config: " + str);
    }

    private boolean p() {
        return this.f10860i.orientation % 180 == n() % 180;
    }

    private void q() throws IOException {
        Camera camera = this.f10859h;
        if (camera != null) {
            camera.stopPreview();
            try {
                this.f10859h.setPreviewTexture(null);
            } catch (Exception unused) {
                Log.e(f10856l, "failed to set Preview Texture");
            }
            this.f10859h.release();
            this.f10859h = null;
            Log.d(f10856l, "releaseCamera -- done");
        }
    }

    public void a(boolean z2) {
        this.f10862k = z2;
    }

    @Override // com.plv.rtc.a.a
    public void d() throws IOException {
        q();
    }

    @Override // com.plv.rtc.a.a
    public boolean e() throws IOException {
        try {
            o();
            this.f10859h.setPreviewTexture(b());
            this.f10859h.startPreview();
            a(this.f10859h);
            return true;
        } catch (Exception unused) {
            Log.e(f10856l, "initialize: failed to initalize camera device");
            return false;
        }
    }

    @Override // com.plv.rtc.a.a
    public boolean f() {
        this.f10859h.startPreview();
        return true;
    }

    @Override // com.plv.rtc.a.a
    public void g() {
        this.f10859h.stopPreview();
    }

    public int k() {
        return this.f10860i.facing;
    }

    public int l() {
        int iN = n();
        Camera.CameraInfo cameraInfo = this.f10860i;
        if (cameraInfo.facing == 0) {
            iN = 360 - iN;
        }
        return (cameraInfo.orientation + iN) % 360;
    }

    public void m() throws IOException {
        d();
        this.f10861j = (this.f10861j == 1 ? 1 : 0) ^ 1;
        e();
    }

    @Override // com.plv.rtc.a.a, io.agora.rtc.mediaio.SurfaceTextureHelper.OnTextureFrameAvailableListener
    public void onTextureFrameAvailable(int i2, float[] fArr, long j2) {
        super.onTextureFrameAvailable(i2, fArr, j2);
        System.arraycopy(a(fArr), 0, fArr, 0, fArr.length);
    }

    public void a(@FloatRange(from = 1.0d, to = 10.0d) float f2) {
        Camera camera = this.f10859h;
        if (camera == null) {
            return;
        }
        Camera.Parameters parameters = camera.getParameters();
        parameters.setZoom((int) (((f2 - 1.0f) / 9.0f) * parameters.getMaxZoom()));
        this.f10859h.setParameters(parameters);
    }

    private void a(Camera.Parameters parameters) {
        Camera.Size sizeA = a(this.f10859h, this.f10852b, this.f10853c);
        int i2 = sizeA.width;
        this.f10854d = i2;
        int i3 = sizeA.height;
        this.f10855e = i3;
        parameters.setPreviewSize(i2, i3);
    }

    private static Camera.Size a(Camera camera, int i2, int i3) {
        Camera.Size next;
        List<Camera.Size> supportedPreviewSizes = camera.getParameters().getSupportedPreviewSizes();
        Collections.sort(supportedPreviewSizes, new a());
        Iterator<Camera.Size> it = supportedPreviewSizes.iterator();
        while (true) {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
            if (next.width == i2 && next.height == i3) {
                break;
            }
        }
        if (next == null) {
            Iterator<Camera.Size> it2 = supportedPreviewSizes.iterator();
            while (true) {
                if (!it2.hasNext()) {
                    break;
                }
                Camera.Size next2 = it2.next();
                if (next2.width >= i2 && a(next2, i2 / i3)) {
                    next = next2;
                    break;
                }
            }
        }
        return (next != null || supportedPreviewSizes.isEmpty()) ? next : supportedPreviewSizes.get(0);
    }

    private static boolean a(Camera.Size size, float f2) {
        return ((double) Math.abs((((float) size.width) / ((float) size.height)) - f2)) <= 0.2d;
    }

    private static void a(Camera camera) {
        try {
            Camera.Parameters parameters = camera.getParameters();
            List<String> supportedFocusModes = parameters.getSupportedFocusModes();
            if (supportedFocusModes.contains("continuous-video")) {
                parameters.setFocusMode("continuous-video");
                camera.setParameters(parameters);
            } else if (supportedFocusModes.contains("continuous-picture")) {
                parameters.setFocusMode("continuous-picture");
                camera.setParameters(parameters);
            } else if (supportedFocusModes.size() > 0) {
                parameters.setFocusMode(supportedFocusModes.get(0));
                camera.setParameters(parameters);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    private float[] a(float[] fArr) {
        boolean z2 = k() == 1;
        if (z2) {
            fArr = RendererCommon.multiplyMatrices(fArr, RendererCommon.horizontalFlipMatrix());
        }
        if (!this.f10862k || !z2) {
            return fArr;
        }
        if (p()) {
            return RendererCommon.multiplyMatrices(fArr, RendererCommon.horizontalFlipMatrix());
        }
        return RendererCommon.multiplyMatrices(fArr, RendererCommon.verticalFlipMatrix());
    }
}
