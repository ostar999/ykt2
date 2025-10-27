package org.fmod;

import android.media.AudioRecord;
import android.util.Log;
import java.nio.ByteBuffer;

/* loaded from: classes9.dex */
final class a implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    private final FMODAudioDevice f27964a;

    /* renamed from: b, reason: collision with root package name */
    private final ByteBuffer f27965b;

    /* renamed from: c, reason: collision with root package name */
    private final int f27966c;

    /* renamed from: d, reason: collision with root package name */
    private final int f27967d;

    /* renamed from: e, reason: collision with root package name */
    private final int f27968e = 2;

    /* renamed from: f, reason: collision with root package name */
    private volatile Thread f27969f;

    /* renamed from: g, reason: collision with root package name */
    private volatile boolean f27970g;

    /* renamed from: h, reason: collision with root package name */
    private AudioRecord f27971h;

    /* renamed from: i, reason: collision with root package name */
    private boolean f27972i;

    public a(FMODAudioDevice fMODAudioDevice, int i2, int i3) {
        this.f27964a = fMODAudioDevice;
        this.f27966c = i2;
        this.f27967d = i3;
        this.f27965b = ByteBuffer.allocateDirect(AudioRecord.getMinBufferSize(i2, i3, 2));
    }

    private void d() throws IllegalStateException {
        AudioRecord audioRecord = this.f27971h;
        if (audioRecord != null) {
            if (audioRecord.getState() == 1) {
                this.f27971h.stop();
            }
            this.f27971h.release();
            this.f27971h = null;
        }
        this.f27965b.position(0);
        this.f27972i = false;
    }

    public final int a() {
        return this.f27965b.capacity();
    }

    public final void b() throws InterruptedException {
        if (this.f27969f != null) {
            c();
        }
        this.f27970g = true;
        this.f27969f = new Thread(this);
        this.f27969f.start();
    }

    public final void c() throws InterruptedException {
        while (this.f27969f != null) {
            this.f27970g = false;
            try {
                this.f27969f.join();
                this.f27969f = null;
            } catch (InterruptedException unused) {
            }
        }
    }

    @Override // java.lang.Runnable
    public final void run() throws IllegalStateException {
        int i2 = 3;
        while (this.f27970g) {
            if (!this.f27972i && i2 > 0) {
                d();
                AudioRecord audioRecord = new AudioRecord(1, this.f27966c, this.f27967d, this.f27968e, this.f27965b.capacity());
                this.f27971h = audioRecord;
                boolean z2 = audioRecord.getState() == 1;
                this.f27972i = z2;
                if (z2) {
                    this.f27965b.position(0);
                    this.f27971h.startRecording();
                    i2 = 3;
                } else {
                    Log.e("FMOD", "AudioRecord failed to initialize (status " + this.f27971h.getState() + ")");
                    i2 += -1;
                    d();
                }
            }
            if (this.f27972i && this.f27971h.getRecordingState() == 3) {
                AudioRecord audioRecord2 = this.f27971h;
                ByteBuffer byteBuffer = this.f27965b;
                this.f27964a.fmodProcessMicData(this.f27965b, audioRecord2.read(byteBuffer, byteBuffer.capacity()));
                this.f27965b.position(0);
            }
        }
        d();
    }
}
