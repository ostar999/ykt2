package com.unity3d.player;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.MediaController;
import java.io.FileInputStream;
import java.io.IOException;

/* loaded from: classes6.dex */
public final class p extends FrameLayout implements MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnVideoSizeChangedListener, SurfaceHolder.Callback, MediaController.MediaPlayerControl {

    /* renamed from: a, reason: collision with root package name */
    private static boolean f24174a = false;

    /* renamed from: b, reason: collision with root package name */
    private final Context f24175b;

    /* renamed from: c, reason: collision with root package name */
    private final SurfaceView f24176c;

    /* renamed from: d, reason: collision with root package name */
    private final SurfaceHolder f24177d;

    /* renamed from: e, reason: collision with root package name */
    private final String f24178e;

    /* renamed from: f, reason: collision with root package name */
    private final int f24179f;

    /* renamed from: g, reason: collision with root package name */
    private final int f24180g;

    /* renamed from: h, reason: collision with root package name */
    private final boolean f24181h;

    /* renamed from: i, reason: collision with root package name */
    private final long f24182i;

    /* renamed from: j, reason: collision with root package name */
    private final long f24183j;

    /* renamed from: k, reason: collision with root package name */
    private final FrameLayout f24184k;

    /* renamed from: l, reason: collision with root package name */
    private final Display f24185l;

    /* renamed from: m, reason: collision with root package name */
    private int f24186m;

    /* renamed from: n, reason: collision with root package name */
    private int f24187n;

    /* renamed from: o, reason: collision with root package name */
    private int f24188o;

    /* renamed from: p, reason: collision with root package name */
    private int f24189p;

    /* renamed from: q, reason: collision with root package name */
    private MediaPlayer f24190q;

    /* renamed from: r, reason: collision with root package name */
    private MediaController f24191r;

    /* renamed from: s, reason: collision with root package name */
    private boolean f24192s;

    /* renamed from: t, reason: collision with root package name */
    private boolean f24193t;

    /* renamed from: u, reason: collision with root package name */
    private int f24194u;

    /* renamed from: v, reason: collision with root package name */
    private boolean f24195v;

    /* renamed from: w, reason: collision with root package name */
    private boolean f24196w;

    /* renamed from: x, reason: collision with root package name */
    private a f24197x;

    /* renamed from: y, reason: collision with root package name */
    private b f24198y;

    /* renamed from: z, reason: collision with root package name */
    private volatile int f24199z;

    public interface a {
        void a(int i2);
    }

    public class b implements Runnable {

        /* renamed from: b, reason: collision with root package name */
        private p f24201b;

        /* renamed from: c, reason: collision with root package name */
        private boolean f24202c = false;

        public b(p pVar) {
            this.f24201b = pVar;
        }

        public final void a() {
            this.f24202c = true;
        }

        @Override // java.lang.Runnable
        public final void run() throws InterruptedException {
            try {
                Thread.sleep(5000L);
            } catch (InterruptedException unused) {
                Thread.currentThread().interrupt();
            }
            if (this.f24202c) {
                return;
            }
            if (p.f24174a) {
                p.b("Stopping the video player due to timeout.");
            }
            this.f24201b.CancelOnPrepare();
        }
    }

    public p(Context context, String str, int i2, int i3, int i4, boolean z2, long j2, long j3, a aVar) {
        super(context);
        this.f24192s = false;
        this.f24193t = false;
        this.f24194u = 0;
        this.f24195v = false;
        this.f24196w = false;
        this.f24199z = 0;
        this.f24197x = aVar;
        this.f24175b = context;
        this.f24184k = this;
        SurfaceView surfaceView = new SurfaceView(context);
        this.f24176c = surfaceView;
        SurfaceHolder holder = surfaceView.getHolder();
        this.f24177d = holder;
        holder.addCallback(this);
        setBackgroundColor(i2);
        addView(surfaceView);
        this.f24185l = ((WindowManager) context.getSystemService("window")).getDefaultDisplay();
        this.f24178e = str;
        this.f24179f = i3;
        this.f24180g = i4;
        this.f24181h = z2;
        this.f24182i = j2;
        this.f24183j = j3;
        if (f24174a) {
            b("fileName: " + str);
        }
        if (f24174a) {
            b("backgroundColor: " + i2);
        }
        if (f24174a) {
            b("controlMode: " + i3);
        }
        if (f24174a) {
            b("scalingMode: " + i4);
        }
        if (f24174a) {
            b("isURL: " + z2);
        }
        if (f24174a) {
            b("videoOffset: " + j2);
        }
        if (f24174a) {
            b("videoLength: " + j3);
        }
        setFocusable(true);
        setFocusableInTouchMode(true);
    }

    private void a(int i2) {
        this.f24199z = i2;
        a aVar = this.f24197x;
        if (aVar != null) {
            aVar.a(this.f24199z);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void b(String str) {
        Log.i("Video", "VideoPlayer: " + str);
    }

    private void c() throws IllegalStateException, IOException, SecurityException, IllegalArgumentException {
        FileInputStream fileInputStream;
        MediaPlayer mediaPlayer = this.f24190q;
        if (mediaPlayer != null) {
            mediaPlayer.setDisplay(this.f24177d);
            if (this.f24195v) {
                return;
            }
            if (f24174a) {
                b("Resuming playback");
            }
            this.f24190q.start();
            return;
        }
        a(0);
        doCleanUp();
        try {
            MediaPlayer mediaPlayer2 = new MediaPlayer();
            this.f24190q = mediaPlayer2;
            if (this.f24181h) {
                mediaPlayer2.setDataSource(this.f24175b, Uri.parse(this.f24178e));
            } else {
                if (this.f24183j != 0) {
                    fileInputStream = new FileInputStream(this.f24178e);
                    this.f24190q.setDataSource(fileInputStream.getFD(), this.f24182i, this.f24183j);
                } else {
                    try {
                        AssetFileDescriptor assetFileDescriptorOpenFd = getResources().getAssets().openFd(this.f24178e);
                        this.f24190q.setDataSource(assetFileDescriptorOpenFd.getFileDescriptor(), assetFileDescriptorOpenFd.getStartOffset(), assetFileDescriptorOpenFd.getLength());
                        assetFileDescriptorOpenFd.close();
                    } catch (IOException unused) {
                        fileInputStream = new FileInputStream(this.f24178e);
                        this.f24190q.setDataSource(fileInputStream.getFD());
                    }
                }
                fileInputStream.close();
            }
            this.f24190q.setDisplay(this.f24177d);
            this.f24190q.setScreenOnWhilePlaying(true);
            this.f24190q.setOnBufferingUpdateListener(this);
            this.f24190q.setOnCompletionListener(this);
            this.f24190q.setOnPreparedListener(this);
            this.f24190q.setOnVideoSizeChangedListener(this);
            this.f24190q.setAudioStreamType(3);
            this.f24190q.prepareAsync();
            this.f24198y = new b(this);
            new Thread(this.f24198y).start();
        } catch (Exception e2) {
            if (f24174a) {
                b("error: " + e2.getMessage() + e2);
            }
            a(2);
        }
    }

    private void d() throws IllegalStateException {
        if (isPlaying()) {
            return;
        }
        a(1);
        if (f24174a) {
            b("startVideoPlayback");
        }
        updateVideoLayout();
        if (this.f24195v) {
            return;
        }
        start();
    }

    public final void CancelOnPrepare() {
        a(2);
    }

    public final boolean a() {
        return this.f24195v;
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public final boolean canPause() {
        return true;
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public final boolean canSeekBackward() {
        return true;
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public final boolean canSeekForward() {
        return true;
    }

    public final void destroyPlayer() throws IllegalStateException {
        if (f24174a) {
            b("destroyPlayer");
        }
        if (!this.f24195v) {
            pause();
        }
        doCleanUp();
    }

    public final void doCleanUp() {
        b bVar = this.f24198y;
        if (bVar != null) {
            bVar.a();
            this.f24198y = null;
        }
        MediaPlayer mediaPlayer = this.f24190q;
        if (mediaPlayer != null) {
            mediaPlayer.release();
            this.f24190q = null;
        }
        this.f24188o = 0;
        this.f24189p = 0;
        this.f24193t = false;
        this.f24192s = false;
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public final int getAudioSessionId() {
        MediaPlayer mediaPlayer = this.f24190q;
        if (mediaPlayer == null) {
            return 0;
        }
        return mediaPlayer.getAudioSessionId();
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public final int getBufferPercentage() {
        if (this.f24181h) {
            return this.f24194u;
        }
        return 100;
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public final int getCurrentPosition() {
        MediaPlayer mediaPlayer = this.f24190q;
        if (mediaPlayer == null) {
            return 0;
        }
        return mediaPlayer.getCurrentPosition();
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public final int getDuration() {
        MediaPlayer mediaPlayer = this.f24190q;
        if (mediaPlayer == null) {
            return 0;
        }
        return mediaPlayer.getDuration();
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public final boolean isPlaying() {
        boolean z2 = this.f24193t && this.f24192s;
        MediaPlayer mediaPlayer = this.f24190q;
        return mediaPlayer == null ? !z2 : mediaPlayer.isPlaying() || !z2;
    }

    @Override // android.media.MediaPlayer.OnBufferingUpdateListener
    public final void onBufferingUpdate(MediaPlayer mediaPlayer, int i2) {
        if (f24174a) {
            b("onBufferingUpdate percent:" + i2);
        }
        this.f24194u = i2;
    }

    @Override // android.media.MediaPlayer.OnCompletionListener
    public final void onCompletion(MediaPlayer mediaPlayer) throws IllegalStateException {
        if (f24174a) {
            b("onCompletion called");
        }
        destroyPlayer();
        a(3);
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public final boolean onKeyDown(int i2, KeyEvent keyEvent) throws IllegalStateException {
        if (i2 != 4 && (this.f24179f != 2 || i2 == 0 || keyEvent.isSystem())) {
            MediaController mediaController = this.f24191r;
            return mediaController != null ? mediaController.onKeyDown(i2, keyEvent) : super.onKeyDown(i2, keyEvent);
        }
        destroyPlayer();
        a(3);
        return true;
    }

    @Override // android.media.MediaPlayer.OnPreparedListener
    public final void onPrepared(MediaPlayer mediaPlayer) throws IllegalStateException {
        if (f24174a) {
            b("onPrepared called");
        }
        b bVar = this.f24198y;
        if (bVar != null) {
            bVar.a();
            this.f24198y = null;
        }
        int i2 = this.f24179f;
        if (i2 == 0 || i2 == 1) {
            MediaController mediaController = new MediaController(this.f24175b);
            this.f24191r = mediaController;
            mediaController.setMediaPlayer(this);
            this.f24191r.setAnchorView(this);
            this.f24191r.setEnabled(true);
            Context context = this.f24175b;
            if (context instanceof Activity) {
                this.f24191r.setSystemUiVisibility(((Activity) context).getWindow().getDecorView().getSystemUiVisibility());
            }
            this.f24191r.show();
        }
        this.f24193t = true;
        if (this.f24192s) {
            d();
        }
    }

    @Override // android.view.View
    public final boolean onTouchEvent(MotionEvent motionEvent) throws IllegalStateException {
        int action = motionEvent.getAction() & 255;
        if (this.f24179f != 2 || action != 0) {
            MediaController mediaController = this.f24191r;
            return mediaController != null ? mediaController.onTouchEvent(motionEvent) : super.onTouchEvent(motionEvent);
        }
        destroyPlayer();
        a(3);
        return true;
    }

    @Override // android.media.MediaPlayer.OnVideoSizeChangedListener
    public final void onVideoSizeChanged(MediaPlayer mediaPlayer, int i2, int i3) throws IllegalStateException {
        if (f24174a) {
            b("onVideoSizeChanged called " + i2 + "x" + i3);
        }
        if (i2 != 0 && i3 != 0) {
            this.f24192s = true;
            this.f24188o = i2;
            this.f24189p = i3;
            if (this.f24193t) {
                d();
                return;
            }
            return;
        }
        if (f24174a) {
            b("invalid video width(" + i2 + ") or height(" + i3 + ")");
        }
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public final void pause() throws IllegalStateException {
        MediaPlayer mediaPlayer = this.f24190q;
        if (mediaPlayer == null) {
            return;
        }
        if (this.f24196w) {
            mediaPlayer.pause();
        }
        this.f24195v = true;
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public final void seekTo(int i2) throws IllegalStateException {
        MediaPlayer mediaPlayer = this.f24190q;
        if (mediaPlayer == null) {
            return;
        }
        mediaPlayer.seekTo(i2);
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public final void start() throws IllegalStateException {
        if (f24174a) {
            b("Start");
        }
        MediaPlayer mediaPlayer = this.f24190q;
        if (mediaPlayer == null) {
            return;
        }
        if (this.f24196w) {
            mediaPlayer.start();
        }
        this.f24195v = false;
    }

    @Override // android.view.SurfaceHolder.Callback
    public final void surfaceChanged(SurfaceHolder surfaceHolder, int i2, int i3, int i4) {
        if (f24174a) {
            b("surfaceChanged called " + i2 + " " + i3 + "x" + i4);
        }
        if (this.f24186m == i3 && this.f24187n == i4) {
            return;
        }
        this.f24186m = i3;
        this.f24187n = i4;
        if (this.f24196w) {
            updateVideoLayout();
        }
    }

    @Override // android.view.SurfaceHolder.Callback
    public final void surfaceCreated(SurfaceHolder surfaceHolder) throws IllegalStateException, IOException, SecurityException, IllegalArgumentException {
        if (f24174a) {
            b("surfaceCreated called");
        }
        this.f24196w = true;
        c();
    }

    @Override // android.view.SurfaceHolder.Callback
    public final void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        if (f24174a) {
            b("surfaceDestroyed called");
        }
        this.f24196w = false;
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x0051  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0055  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateVideoLayout() {
        /*
            r8 = this;
            boolean r0 = com.unity3d.player.p.f24174a
            if (r0 == 0) goto La
            java.lang.String r0 = "updateVideoLayout"
            b(r0)
        La:
            android.media.MediaPlayer r0 = r8.f24190q
            if (r0 != 0) goto Lf
            return
        Lf:
            int r0 = r8.f24186m
            if (r0 == 0) goto L17
            int r0 = r8.f24187n
            if (r0 != 0) goto L36
        L17:
            android.content.Context r0 = r8.f24175b
            java.lang.String r1 = "window"
            java.lang.Object r0 = r0.getSystemService(r1)
            android.view.WindowManager r0 = (android.view.WindowManager) r0
            android.util.DisplayMetrics r1 = new android.util.DisplayMetrics
            r1.<init>()
            android.view.Display r0 = r0.getDefaultDisplay()
            r0.getMetrics(r1)
            int r0 = r1.widthPixels
            r8.f24186m = r0
            int r0 = r1.heightPixels
            r8.f24187n = r0
        L36:
            int r0 = r8.f24186m
            int r1 = r8.f24187n
            boolean r2 = r8.f24192s
            if (r2 == 0) goto L66
            int r2 = r8.f24188o
            float r3 = (float) r2
            int r4 = r8.f24189p
            float r5 = (float) r4
            float r3 = r3 / r5
            float r5 = (float) r0
            float r6 = (float) r1
            float r5 = r5 / r6
            int r6 = r8.f24180g
            r7 = 1
            if (r6 != r7) goto L59
            int r2 = (r5 > r3 ? 1 : (r5 == r3 ? 0 : -1))
            if (r2 > 0) goto L55
        L51:
            float r1 = (float) r0
            float r1 = r1 / r3
            int r1 = (int) r1
            goto L70
        L55:
            float r0 = (float) r1
            float r0 = r0 * r3
            int r0 = (int) r0
            goto L70
        L59:
            r7 = 2
            if (r6 != r7) goto L61
            int r2 = (r5 > r3 ? 1 : (r5 == r3 ? 0 : -1))
            if (r2 < 0) goto L55
            goto L51
        L61:
            if (r6 != 0) goto L70
            r0 = r2
            r1 = r4
            goto L70
        L66:
            boolean r2 = com.unity3d.player.p.f24174a
            if (r2 == 0) goto L70
            java.lang.String r2 = "updateVideoLayout: Video size is not known yet"
            b(r2)
        L70:
            int r2 = r8.f24186m
            if (r2 != r0) goto L78
            int r2 = r8.f24187n
            if (r2 == r1) goto La3
        L78:
            boolean r2 = com.unity3d.player.p.f24174a
            if (r2 == 0) goto L95
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "frameWidth = "
            r2.<init>(r3)
            r2.append(r0)
            java.lang.String r3 = "; frameHeight = "
            r2.append(r3)
            r2.append(r1)
            java.lang.String r2 = r2.toString()
            b(r2)
        L95:
            android.widget.FrameLayout$LayoutParams r2 = new android.widget.FrameLayout$LayoutParams
            r3 = 17
            r2.<init>(r0, r1, r3)
            android.widget.FrameLayout r0 = r8.f24184k
            android.view.SurfaceView r1 = r8.f24176c
            r0.updateViewLayout(r1, r2)
        La3:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.unity3d.player.p.updateVideoLayout():void");
    }
}
