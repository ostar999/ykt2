package a.a.a.a.f;

import android.graphics.Bitmap;
import android.opengl.EGL14;
import android.opengl.EGLSurface;
import android.opengl.GLES20;
import android.util.Log;
import com.yikaobang.yixue.R2;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/* loaded from: classes.dex */
public class c {

    /* renamed from: e, reason: collision with root package name */
    protected static final String f1157e = "mqi";

    /* renamed from: a, reason: collision with root package name */
    protected b f1158a;

    /* renamed from: b, reason: collision with root package name */
    private EGLSurface f1159b = EGL14.EGL_NO_SURFACE;

    /* renamed from: c, reason: collision with root package name */
    private int f1160c = -1;

    /* renamed from: d, reason: collision with root package name */
    private int f1161d = -1;

    public c(b bVar) {
        this.f1158a = bVar;
    }

    public void a(Object obj) {
        if (this.f1159b != EGL14.EGL_NO_SURFACE) {
            throw new IllegalStateException("surface already created");
        }
        this.f1159b = this.f1158a.a(obj);
    }

    public int b() {
        int i2 = this.f1160c;
        return i2 < 0 ? this.f1158a.a(this.f1159b, R2.drawable.shape_discuss_right_press) : i2;
    }

    public void c() {
        this.f1158a.b(this.f1159b);
    }

    public void d() {
        this.f1158a.c(this.f1159b);
        this.f1159b = EGL14.EGL_NO_SURFACE;
        this.f1161d = -1;
        this.f1160c = -1;
    }

    public boolean e() {
        boolean zD = this.f1158a.d(this.f1159b);
        if (!zD) {
            Log.d("mqi", "WARNING: swapBuffers() failed");
        }
        return zD;
    }

    public void a(int i2, int i3) {
        if (this.f1159b == EGL14.EGL_NO_SURFACE) {
            this.f1159b = this.f1158a.a(i2, i3);
            this.f1160c = i2;
            this.f1161d = i3;
            return;
        }
        throw new IllegalStateException("surface already created");
    }

    public int a() {
        int i2 = this.f1161d;
        return i2 < 0 ? this.f1158a.a(this.f1159b, R2.drawable.shape_discuss_right_default) : i2;
    }

    public void a(c cVar) {
        this.f1158a.a(this.f1159b, cVar.f1159b);
    }

    public void a(long j2) {
        this.f1158a.a(this.f1159b, j2);
    }

    public void a(File file) throws Throwable {
        BufferedOutputStream bufferedOutputStream;
        if (this.f1158a.a(this.f1159b)) {
            String string = file.toString();
            int iB = b();
            int iA = a();
            ByteBuffer byteBufferAllocateDirect = ByteBuffer.allocateDirect(iB * iA * 4);
            byteBufferAllocateDirect.order(ByteOrder.LITTLE_ENDIAN);
            GLES20.glReadPixels(0, 0, iB, iA, R2.dimen.dm_200, R2.color.m3_ref_palette_dynamic_tertiary100, byteBufferAllocateDirect);
            e.a("glReadPixels");
            byteBufferAllocateDirect.rewind();
            try {
                bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(string));
            } catch (Throwable th) {
                th = th;
                bufferedOutputStream = null;
            }
            try {
                Bitmap bitmapCreateBitmap = Bitmap.createBitmap(iB, iA, Bitmap.Config.ARGB_8888);
                bitmapCreateBitmap.copyPixelsFromBuffer(byteBufferAllocateDirect);
                bitmapCreateBitmap.compress(Bitmap.CompressFormat.PNG, 90, bufferedOutputStream);
                bitmapCreateBitmap.recycle();
                bufferedOutputStream.close();
                Log.d("mqi", "Saved " + iB + "x" + iA + " frame as '" + string + "'");
                return;
            } catch (Throwable th2) {
                th = th2;
                if (bufferedOutputStream != null) {
                    bufferedOutputStream.close();
                }
                throw th;
            }
        }
        throw new RuntimeException("Expected EGL context/surface is not current");
    }
}
