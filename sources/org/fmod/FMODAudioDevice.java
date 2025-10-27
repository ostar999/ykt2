package org.fmod;

import android.media.AudioTrack;
import android.util.Log;
import java.nio.ByteBuffer;

/* loaded from: classes9.dex */
public class FMODAudioDevice implements Runnable {

    /* renamed from: h, reason: collision with root package name */
    private static int f27952h = 0;

    /* renamed from: i, reason: collision with root package name */
    private static int f27953i = 1;

    /* renamed from: j, reason: collision with root package name */
    private static int f27954j = 2;

    /* renamed from: k, reason: collision with root package name */
    private static int f27955k = 3;

    /* renamed from: l, reason: collision with root package name */
    private static int f27956l = 4;

    /* renamed from: a, reason: collision with root package name */
    private volatile Thread f27957a = null;

    /* renamed from: b, reason: collision with root package name */
    private volatile boolean f27958b = false;

    /* renamed from: c, reason: collision with root package name */
    private AudioTrack f27959c = null;

    /* renamed from: d, reason: collision with root package name */
    private boolean f27960d = false;

    /* renamed from: e, reason: collision with root package name */
    private ByteBuffer f27961e = null;

    /* renamed from: f, reason: collision with root package name */
    private byte[] f27962f = null;

    /* renamed from: g, reason: collision with root package name */
    private volatile a f27963g;

    private native int fmodGetInfo(int i2);

    private native int fmodProcess(ByteBuffer byteBuffer);

    private void releaseAudioTrack() throws IllegalStateException {
        AudioTrack audioTrack = this.f27959c;
        if (audioTrack != null) {
            if (audioTrack.getState() == 1) {
                this.f27959c.stop();
            }
            this.f27959c.release();
            this.f27959c = null;
        }
        this.f27961e = null;
        this.f27962f = null;
        this.f27960d = false;
    }

    public synchronized void close() {
        stop();
    }

    public native int fmodProcessMicData(ByteBuffer byteBuffer, int i2);

    public boolean isRunning() {
        return this.f27957a != null && this.f27957a.isAlive();
    }

    @Override // java.lang.Runnable
    public void run() throws IllegalStateException {
        int i2 = 3;
        while (this.f27958b) {
            if (!this.f27960d && i2 > 0) {
                releaseAudioTrack();
                int iFmodGetInfo = fmodGetInfo(f27952h);
                int i3 = fmodGetInfo(f27956l) == 1 ? 4 : 12;
                int minBufferSize = AudioTrack.getMinBufferSize(iFmodGetInfo, i3, 2);
                int iFmodGetInfo2 = 2 * fmodGetInfo(f27956l);
                int iRound = Math.round(minBufferSize * 1.1f) & (~(iFmodGetInfo2 - 1));
                int iFmodGetInfo3 = fmodGetInfo(f27953i);
                int iFmodGetInfo4 = fmodGetInfo(f27954j) * iFmodGetInfo3 * iFmodGetInfo2;
                AudioTrack audioTrack = new AudioTrack(3, iFmodGetInfo, i3, 2, iFmodGetInfo4 > iRound ? iFmodGetInfo4 : iRound, 1);
                this.f27959c = audioTrack;
                boolean z2 = audioTrack.getState() == 1;
                this.f27960d = z2;
                if (z2) {
                    ByteBuffer byteBufferAllocateDirect = ByteBuffer.allocateDirect(iFmodGetInfo3 * iFmodGetInfo2);
                    this.f27961e = byteBufferAllocateDirect;
                    this.f27962f = new byte[byteBufferAllocateDirect.capacity()];
                    this.f27959c.play();
                    i2 = 3;
                } else {
                    Log.e("FMOD", "AudioTrack failed to initialize (status " + this.f27959c.getState() + ")");
                    releaseAudioTrack();
                    i2 += -1;
                }
            }
            if (this.f27960d) {
                if (fmodGetInfo(f27955k) == 1) {
                    fmodProcess(this.f27961e);
                    ByteBuffer byteBuffer = this.f27961e;
                    byteBuffer.get(this.f27962f, 0, byteBuffer.capacity());
                    this.f27959c.write(this.f27962f, 0, this.f27961e.capacity());
                    this.f27961e.position(0);
                } else {
                    releaseAudioTrack();
                }
            }
        }
        releaseAudioTrack();
    }

    public synchronized void start() {
        if (this.f27957a != null) {
            stop();
        }
        this.f27957a = new Thread(this, "FMODAudioDevice");
        this.f27957a.setPriority(10);
        this.f27958b = true;
        this.f27957a.start();
        if (this.f27963g != null) {
            this.f27963g.b();
        }
    }

    public synchronized int startAudioRecord(int i2, int i3, int i4) {
        if (this.f27963g == null) {
            this.f27963g = new a(this, i2, i3);
            this.f27963g.b();
        }
        return this.f27963g.a();
    }

    public synchronized void stop() {
        while (this.f27957a != null) {
            this.f27958b = false;
            try {
                this.f27957a.join();
                this.f27957a = null;
            } catch (InterruptedException unused) {
            }
        }
        if (this.f27963g != null) {
            this.f27963g.c();
        }
    }

    public synchronized void stopAudioRecord() {
        if (this.f27963g != null) {
            this.f27963g.c();
            this.f27963g = null;
        }
    }
}
