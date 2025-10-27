package com.beizi.ad.internal.video;

import android.content.Context;
import android.content.MutableContextWrapper;
import android.content.res.AssetFileDescriptor;
import android.graphics.Matrix;
import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RawRes;
import com.beizi.ad.R;
import com.beizi.ad.c.e;
import com.beizi.ad.internal.a.b;
import com.beizi.ad.internal.c.f;
import com.beizi.ad.internal.g;
import com.beizi.ad.internal.network.ServerResponse;
import com.beizi.ad.internal.utilities.HaoboLog;
import com.beizi.ad.internal.utilities.StringUtil;
import com.beizi.ad.internal.utilities.UserEnvInfoUtil;
import com.beizi.ad.internal.utilities.ViewUtil;
import com.beizi.ad.internal.video.a;
import com.beizi.ad.internal.view.AdViewImpl;
import com.beizi.ad.internal.view.AdWebView;
import com.beizi.ad.internal.view.InterstitialAdViewImpl;
import com.beizi.ad.internal.view.c;
import com.google.android.material.badge.BadgeDrawable;
import java.io.FileDescriptor;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class AdVideoView extends TextureView implements MediaPlayer.OnVideoSizeChangedListener, TextureView.SurfaceTextureListener, c {

    /* renamed from: a, reason: collision with root package name */
    protected MediaPlayer f4407a;

    /* renamed from: b, reason: collision with root package name */
    protected a.b f4408b;

    /* renamed from: c, reason: collision with root package name */
    private boolean f4409c;

    /* renamed from: d, reason: collision with root package name */
    private int f4410d;

    /* renamed from: e, reason: collision with root package name */
    private int f4411e;

    /* renamed from: f, reason: collision with root package name */
    private int f4412f;

    /* renamed from: g, reason: collision with root package name */
    private int f4413g;

    /* renamed from: h, reason: collision with root package name */
    private int f4414h;

    /* renamed from: i, reason: collision with root package name */
    private boolean f4415i;

    /* renamed from: j, reason: collision with root package name */
    private boolean f4416j;

    /* renamed from: k, reason: collision with root package name */
    private a f4417k;

    /* renamed from: l, reason: collision with root package name */
    private Pair<String, Integer> f4418l;

    /* renamed from: m, reason: collision with root package name */
    private int f4419m;
    public AdWebView mAdWebView;

    /* renamed from: n, reason: collision with root package name */
    private long f4420n;

    /* renamed from: o, reason: collision with root package name */
    private float f4421o;

    /* renamed from: p, reason: collision with root package name */
    private float f4422p;

    /* renamed from: q, reason: collision with root package name */
    private boolean f4423q;

    public enum a {
        NRF_NONE,
        NRF_START,
        NRF_PAUSE
    }

    public AdVideoView(AdWebView adWebView) {
        super(new MutableContextWrapper(adWebView.getContextFromMutableContext()));
        this.f4409c = false;
        this.f4415i = false;
        this.f4416j = false;
        this.f4417k = a.NRF_NONE;
        this.f4418l = null;
        this.f4408b = a.b.FIT_CENTER;
        this.f4419m = -1;
        this.mAdWebView = adWebView;
    }

    private void setDataSource(@NonNull AssetFileDescriptor assetFileDescriptor) throws IllegalStateException, IOException, IllegalArgumentException {
        setDataSource(assetFileDescriptor.getFileDescriptor(), assetFileDescriptor.getStartOffset(), assetFileDescriptor.getLength());
        assetFileDescriptor.close();
    }

    @Override // com.beizi.ad.internal.view.c
    public void destroy() throws IllegalStateException {
        stop();
        ViewUtil.removeChildFromParent(this);
    }

    @Override // com.beizi.ad.internal.view.c
    public boolean failed() {
        return this.f4409c;
    }

    public AdWebView getAdWebView() {
        return this.mAdWebView;
    }

    @Override // com.beizi.ad.internal.view.c
    public int getCreativeHeight() {
        return this.f4413g;
    }

    public int getCreativeLeft() {
        return this.f4410d;
    }

    public int getCreativeTop() {
        return this.f4411e;
    }

    @Override // com.beizi.ad.internal.view.c
    public int getCreativeWidth() {
        return this.f4412f;
    }

    public int getCurrentPosition() {
        return this.f4407a.getCurrentPosition();
    }

    public int getDuration() {
        return this.f4407a.getDuration();
    }

    @Override // com.beizi.ad.internal.view.c
    public int getRefreshInterval() {
        return this.f4414h;
    }

    public int getVideoHeight() {
        return this.f4407a.getVideoHeight();
    }

    public int getVideoWidth() {
        return this.f4407a.getVideoWidth();
    }

    @Override // com.beizi.ad.internal.view.c
    public View getView() {
        return this;
    }

    public boolean isLooping() {
        return this.f4407a.isLooping();
    }

    public boolean isPlaying() {
        return this.f4407a.isPlaying();
    }

    @Override // com.beizi.ad.internal.view.c
    public void onDestroy() throws IllegalStateException {
        destroy();
    }

    @Override // android.view.View
    public void onDetachedFromWindow() throws IllegalStateException {
        super.onDetachedFromWindow();
        if (this.f4407a == null) {
            return;
        }
        if (isPlaying()) {
            stop();
        }
        release();
    }

    @Override // com.beizi.ad.internal.view.c
    public void onPause() throws IllegalStateException {
        if (this.f4417k == a.NRF_START) {
            pause();
            this.f4417k = a.NRF_PAUSE;
        }
    }

    @Override // com.beizi.ad.internal.view.c
    public void onResume() throws IllegalStateException {
        if (this.f4417k == a.NRF_PAUSE) {
            start(1);
            this.f4417k = a.NRF_START;
        }
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i2, int i3) {
        Surface surface = new Surface(surfaceTexture);
        MediaPlayer mediaPlayer = this.f4407a;
        if (mediaPlayer != null) {
            mediaPlayer.setSurface(surface);
        }
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        return false;
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i2, int i3) {
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        boolean z2;
        AdWebView adWebView;
        AdViewImpl adViewImpl;
        int action = motionEvent.getAction();
        if (action != 0) {
            if (action == 1) {
                long jCurrentTimeMillis = System.currentTimeMillis() - this.f4420n;
                Log.d("lance", "ACTION_UP:" + jCurrentTimeMillis);
                if (jCurrentTimeMillis < 1000 && this.f4423q && (adWebView = this.mAdWebView) != null && (adViewImpl = adWebView.adViewImpl) != null && adViewImpl.getAdDispatcher() != null) {
                    AdViewImpl adViewImpl2 = this.mAdWebView.adViewImpl;
                    adViewImpl2.clickCount++;
                    adViewImpl2.getAdDispatcher().d();
                    AdWebView adWebView2 = this.mAdWebView;
                    adWebView2.ad.setOpenInNativeBrowser(adWebView2.adViewImpl.getOpensNativeBrowser());
                }
            } else if (action == 2 && this.f4423q && a(this.f4421o, this.f4422p, motionEvent.getX(), motionEvent.getY()) > 15.0f) {
                this.f4423q = false;
            }
            z2 = false;
        } else {
            this.f4420n = System.currentTimeMillis();
            this.f4421o = motionEvent.getX();
            this.f4422p = motionEvent.getY();
            this.f4423q = true;
            Log.d("lance", "ACTION_DOWN");
            z2 = true;
        }
        return super.onTouchEvent(motionEvent) || z2;
    }

    @Override // android.media.MediaPlayer.OnVideoSizeChangedListener
    public void onVideoSizeChanged(MediaPlayer mediaPlayer, int i2, int i3) {
    }

    @Override // android.view.TextureView, android.view.View
    public void onVisibilityChanged(View view, int i2) throws IllegalStateException {
        super.onVisibilityChanged(view, i2);
        b(getWindowVisibility(), i2);
    }

    @Override // android.view.View
    public void onWindowVisibilityChanged(int i2) throws IllegalStateException {
        super.onWindowVisibilityChanged(i2);
        b(i2, getVisibility());
    }

    public void pause() throws IllegalStateException {
        MediaPlayer mediaPlayer = this.f4407a;
        if (mediaPlayer != null) {
            mediaPlayer.pause();
            this.mAdWebView.ad.handleOnPause(this);
        }
    }

    public void prepare(@Nullable MediaPlayer.OnPreparedListener onPreparedListener) throws IllegalStateException, IOException {
        this.f4407a.setOnPreparedListener(onPreparedListener);
        this.f4407a.prepare();
    }

    public void prepareAsync(@Nullable MediaPlayer.OnPreparedListener onPreparedListener) throws IllegalStateException {
        this.f4407a.setOnPreparedListener(onPreparedListener);
        this.f4407a.prepareAsync();
    }

    public void release() {
        reset();
        this.f4407a.release();
        this.f4407a = null;
    }

    public void reset() {
        this.f4407a.reset();
    }

    public void seekTo(int i2) throws IllegalStateException {
        MediaPlayer mediaPlayer = this.f4407a;
        if (mediaPlayer != null) {
            mediaPlayer.seekTo(i2);
        }
    }

    public void setAssetData(@NonNull String str) throws IllegalStateException, IOException, IllegalArgumentException {
        setDataSource(getContext().getAssets().openFd(str));
    }

    public void setCreativeLeft(int i2) {
        this.f4410d = i2;
    }

    public void setCreativeTop(int i2) {
        this.f4411e = i2;
    }

    public void setLooping(boolean z2) {
        MediaPlayer mediaPlayer = this.f4407a;
        if (mediaPlayer != null) {
            mediaPlayer.setLooping(z2);
        }
    }

    public void setOnCompletionListener(@Nullable MediaPlayer.OnCompletionListener onCompletionListener) {
        this.f4407a.setOnCompletionListener(onCompletionListener);
    }

    public void setOnErrorListener(@Nullable MediaPlayer.OnErrorListener onErrorListener) {
        this.f4407a.setOnErrorListener(onErrorListener);
    }

    public void setOnInfoListener(@Nullable MediaPlayer.OnInfoListener onInfoListener) {
        this.f4407a.setOnInfoListener(onInfoListener);
    }

    public void setRawData(@RawRes int i2) throws IllegalStateException, IOException, IllegalArgumentException {
        setDataSource(getResources().openRawResourceFd(i2));
    }

    public void setRefreshInterval(int i2) {
        this.f4414h = i2;
    }

    public void setScalableType(a.b bVar) {
        this.f4408b = bVar;
        a(getVideoWidth(), getVideoHeight());
    }

    public void setVolume(float f2, float f3) {
        MediaPlayer mediaPlayer = this.f4407a;
        if (mediaPlayer != null) {
            mediaPlayer.setVolume(f2, f3);
        }
    }

    public void start(int i2) throws IllegalStateException {
        MediaPlayer mediaPlayer = this.f4407a;
        if (mediaPlayer != null) {
            mediaPlayer.start();
            this.mAdWebView.ad.handleOnStart(this, i2);
        }
    }

    public void stop() throws IllegalStateException {
        MediaPlayer mediaPlayer = this.f4407a;
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }

    public boolean toggleMute() {
        boolean z2 = !this.f4416j;
        this.f4416j = z2;
        if (z2) {
            setVolume(0.0f, 0.0f);
        } else {
            setVolume(1.0f, 1.0f);
        }
        return this.f4416j;
    }

    public void transferAd(final AdWebView adWebView, String str) {
        int creativeWidth;
        int creativeHeight;
        if (StringUtil.isEmpty(str)) {
            return;
        }
        this.f4413g = adWebView.getCreativeHeight();
        this.f4412f = adWebView.getCreativeWidth();
        this.f4411e = adWebView.getCreativeTop();
        this.f4410d = adWebView.getCreativeLeft();
        this.f4414h = adWebView.getRefreshInterval();
        try {
            new URI(str);
            this.f4408b = a.b.FIT_CENTER;
            HaoboLog.v(HaoboLog.videoLogTag, HaoboLog.getString(R.string.videoview_loading, str));
            a(adWebView.getAdExtras());
            try {
                f fVarB = g.a().b();
                if (!UserEnvInfoUtil.isUsingWifi(g.a().e()) && this.mAdWebView.IsVideoWifiOnly() && !fVarB.b(str)) {
                    HaoboLog.e(HaoboLog.videoLogTag, HaoboLog.getString(R.string.wifi_video_load, str));
                    failed();
                    return;
                }
                setDataSource(fVarB.a(str));
                boolean zIsMuted = adWebView.isMuted();
                this.f4416j = zIsMuted;
                if (zIsMuted) {
                    setVolume(0.0f, 0.0f);
                } else {
                    setVolume(1.0f, 1.0f);
                }
                float fH = g.a().h();
                float fI = g.a().i();
                if (getCreativeWidth() == 1 && getCreativeHeight() == 1) {
                    creativeHeight = -1;
                    creativeWidth = -1;
                } else {
                    creativeWidth = (int) ((getCreativeWidth() * fH) + 0.5f);
                    creativeHeight = (int) ((getCreativeHeight() * fI) + 0.5f);
                }
                if (getCreativeLeft() == 0 && getCreativeTop() == 0) {
                    setLayoutParams(new FrameLayout.LayoutParams(creativeWidth, creativeHeight, 17));
                } else {
                    FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(creativeWidth, creativeHeight, BadgeDrawable.TOP_START);
                    layoutParams.setMargins((int) ((getCreativeLeft() * fH) + 0.5f), (int) ((getCreativeTop() * fI) + 0.5f), 0, 0);
                    setLayoutParams(layoutParams);
                    setScalableType(a.b.FIT_START);
                }
                setOnCompletionListener(new MediaPlayer.OnCompletionListener() { // from class: com.beizi.ad.internal.video.AdVideoView.1
                    @Override // android.media.MediaPlayer.OnCompletionListener
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        AdVideoView.this.mAdWebView.ad.handleOnCompletion();
                        if (AdVideoView.this.f4418l != null) {
                            AdVideoView.this.mAdWebView.adViewImpl.getAdDispatcher().a((String) AdVideoView.this.f4418l.first, ((Integer) AdVideoView.this.f4418l.second).intValue());
                        }
                        if (!AdVideoView.this.mAdWebView.loadAdBy(1)) {
                            Log.d("lance", "We can't go next, just stand here");
                            return;
                        }
                        AdViewImpl adViewImpl = adWebView.adViewImpl;
                        if (adViewImpl instanceof InterstitialAdViewImpl) {
                            if (((InterstitialAdViewImpl) adViewImpl).getAdImplementation() != null) {
                                ((b) ((InterstitialAdViewImpl) adWebView.adViewImpl).getAdImplementation()).g();
                            } else {
                                Log.d("lance", "Error in incentive video ad adaptation model !");
                            }
                        }
                    }
                });
                setOnInfoListener(new MediaPlayer.OnInfoListener() { // from class: com.beizi.ad.internal.video.AdVideoView.2
                    @Override // android.media.MediaPlayer.OnInfoListener
                    public boolean onInfo(MediaPlayer mediaPlayer, int i2, int i3) {
                        AdWebView adWebView2;
                        AdViewImpl adViewImpl;
                        if (i2 != 3 || (adWebView2 = AdVideoView.this.mAdWebView) == null || (adViewImpl = adWebView2.adViewImpl) == null || adViewImpl.getAdDispatcher() == null) {
                            return false;
                        }
                        AdVideoView.this.mAdWebView.adViewImpl.getAdDispatcher().f();
                        return false;
                    }
                });
                prepareAsync(new MediaPlayer.OnPreparedListener() { // from class: com.beizi.ad.internal.video.AdVideoView.3
                    @Override // android.media.MediaPlayer.OnPreparedListener
                    public void onPrepared(MediaPlayer mediaPlayer) throws IllegalStateException {
                        AdVideoView.this.f4419m = mediaPlayer.getDuration() / 1000;
                        Log.d("lance", "mPlayTime:" + AdVideoView.this.f4419m);
                        if (!AdVideoView.this.f4415i) {
                            AdVideoView.this.f4417k = a.NRF_PAUSE;
                        } else {
                            HaoboLog.e(HaoboLog.baseLogTag, "Video start called!");
                            AdVideoView.this.start(0);
                            AdVideoView.this.f4417k = a.NRF_START;
                        }
                    }
                });
            } catch (IOException | NullPointerException e2) {
                HaoboLog.e(HaoboLog.videoLogTag, HaoboLog.getString(R.string.failed_video_load, str, e2.getMessage()));
                failed();
            }
        } catch (NullPointerException | URISyntaxException unused) {
            HaoboLog.e(HaoboLog.videoLogTag, HaoboLog.getString(R.string.invalid_video_url, str));
            failed();
        }
    }

    @Override // com.beizi.ad.internal.view.c
    public void visible() {
        this.mAdWebView.setVisibility(0);
        this.mAdWebView.adViewImpl.showAdLogo(this);
        AdWebView adWebView = this.mAdWebView;
        if (adWebView == null || !adWebView.shouldDisplayButton()) {
            return;
        }
        AdWebView adWebView2 = this.mAdWebView;
        if (adWebView2.adViewImpl != null) {
            int autoCloseTime = adWebView2.getAutoCloseTime();
            int i2 = this.f4419m;
            if (autoCloseTime > i2) {
                AdWebView adWebView3 = this.mAdWebView;
                adWebView3.adViewImpl.addCloseButton(i2, adWebView3.getShowCloseBtnTime(), this.f4419m, this, this.mAdWebView.ad.getAdType() == e.a.ADP_IVIDEO);
            } else {
                AdWebView adWebView4 = this.mAdWebView;
                adWebView4.adViewImpl.addCloseButton(i2, adWebView4.getShowCloseBtnTime(), this.mAdWebView.getAutoCloseTime(), this, this.mAdWebView.ad.getAdType() == e.a.ADP_IVIDEO);
                this.mAdWebView.adViewImpl.addMuteButton(this, this.f4416j);
            }
        }
        AdViewImpl adViewImpl = this.mAdWebView.adViewImpl;
        if (adViewImpl == null || adViewImpl.getAdDispatcher() == null) {
            return;
        }
        this.mAdWebView.adViewImpl.getAdDispatcher().a();
        AdWebView adWebView5 = this.mAdWebView;
        adWebView5.ad.handleView(this, adWebView5.adViewImpl.getAdParameters().a());
    }

    private void b(int i2, int i3) throws IllegalStateException {
        if (i2 == 0 && i3 == 0) {
            onResume();
            this.f4415i = true;
        } else {
            onPause();
            this.f4415i = false;
        }
    }

    public void prepare() throws IllegalStateException, IOException {
        prepare(null);
    }

    public void prepareAsync() throws IllegalStateException {
        prepareAsync(null);
    }

    public void setDataSource(@NonNull String str) throws IllegalStateException, IOException, SecurityException, IllegalArgumentException {
        a();
        this.f4407a.setDataSource(str);
    }

    private void a(int i2, int i3) {
        Matrix matrixA;
        if (i2 == 0 || i3 == 0 || (matrixA = new com.beizi.ad.internal.video.a(new a.c(getWidth(), getHeight()), new a.c(i2, i3)).a(this.f4408b)) == null) {
            return;
        }
        setTransform(matrixA);
    }

    public void setDataSource(@NonNull Context context, @NonNull Uri uri, @Nullable Map<String, String> map) throws IllegalStateException, IOException, SecurityException, IllegalArgumentException {
        a();
        this.f4407a.setDataSource(context, uri, map);
    }

    public void setDataSource(@NonNull Context context, @NonNull Uri uri) throws IllegalStateException, IOException, SecurityException, IllegalArgumentException {
        a();
        this.f4407a.setDataSource(context, uri);
    }

    private void a() {
        if (this.f4407a == null) {
            MediaPlayer mediaPlayer = new MediaPlayer();
            this.f4407a = mediaPlayer;
            mediaPlayer.setOnVideoSizeChangedListener(this);
            setSurfaceTextureListener(this);
            return;
        }
        reset();
    }

    public void setDataSource(@NonNull FileDescriptor fileDescriptor, long j2, long j3) throws IllegalStateException, IOException, IllegalArgumentException {
        a();
        this.f4407a.setDataSource(fileDescriptor, j2, j3);
    }

    public void setDataSource(@NonNull FileDescriptor fileDescriptor) throws IllegalStateException, IOException, IllegalArgumentException {
        a();
        this.f4407a.setDataSource(fileDescriptor);
    }

    private void a(HashMap map) {
        if (map.isEmpty()) {
            return;
        }
        if (map.containsKey(ServerResponse.EXTRAS_KEY_SCALE)) {
            String str = (String) map.get(ServerResponse.EXTRAS_KEY_SCALE);
            str.hashCode();
            switch (str) {
                case "RIGHT_TOP_CROP":
                    this.f4408b = a.b.RIGHT_TOP_CROP;
                    break;
                case "RIGHT_BOTTOM":
                    this.f4408b = a.b.RIGHT_BOTTOM;
                    break;
                case "RIGHT_CENTER":
                    this.f4408b = a.b.RIGHT_CENTER;
                    break;
                case "LEFT_TOP":
                    this.f4408b = a.b.LEFT_TOP;
                    break;
                case "RIGHT_CENTER_CROP":
                    this.f4408b = a.b.RIGHT_CENTER_CROP;
                    break;
                case "LEFT_TOP_CROP":
                    this.f4408b = a.b.LEFT_TOP_CROP;
                    break;
                case "END_INSIDE":
                    this.f4408b = a.b.END_INSIDE;
                    break;
                case "LEFT_BOTTOM":
                    this.f4408b = a.b.LEFT_BOTTOM;
                    break;
                case "LEFT_CENTER":
                    this.f4408b = a.b.LEFT_CENTER;
                    break;
                case "CENTER_CROP":
                    this.f4408b = a.b.CENTER_CROP;
                    break;
                case "CENTER_BOTTOM_CROP":
                    this.f4408b = a.b.CENTER_BOTTOM_CROP;
                    break;
                case "CENTER_TOP_CROP":
                    this.f4408b = a.b.CENTER_TOP_CROP;
                    break;
                case "LEFT_CENTER_CROP":
                    this.f4408b = a.b.LEFT_CENTER_CROP;
                    break;
                case "FIT_END":
                    this.f4408b = a.b.FIT_END;
                    break;
                case "START_INSIDE":
                    this.f4408b = a.b.START_INSIDE;
                    break;
                case "RIGHT_BOTTOM_CROP":
                    this.f4408b = a.b.RIGHT_BOTTOM_CROP;
                    break;
                case "FIT_START":
                    this.f4408b = a.b.FIT_START;
                    break;
                case "FIT_CENTER":
                    this.f4408b = a.b.FIT_CENTER;
                    break;
                case "RIGHT_TOP":
                    this.f4408b = a.b.RIGHT_TOP;
                    break;
                case "CENTER_BOTTOM":
                    this.f4408b = a.b.CENTER_BOTTOM;
                    break;
                case "CENTER_TOP":
                    this.f4408b = a.b.CENTER_TOP;
                    break;
                case "CENTER_INSIDE":
                    this.f4408b = a.b.CENTER_INSIDE;
                    break;
                case "LEFT_BOTTOM_CROP":
                    this.f4408b = a.b.LEFT_BOTTOM_CROP;
                    break;
                case "CENTER":
                    this.f4408b = a.b.CENTER;
                    break;
                case "FIT_XY":
                    this.f4408b = a.b.FIT_XY;
                    break;
                default:
                    this.f4408b = a.b.FIT_CENTER;
                    break;
            }
        }
        if (map.containsKey(ServerResponse.EXTRAS_KEY_REWARD_ITEM)) {
            String str2 = (String) map.get(ServerResponse.EXTRAS_KEY_REWARD_ITEM);
            try {
                JSONObject jSONObject = new JSONObject(str2);
                String strOptString = jSONObject.optString("type");
                Integer numValueOf = Integer.valueOf(jSONObject.optInt("amount"));
                if (!TextUtils.isEmpty(strOptString)) {
                    this.f4418l = Pair.create(strOptString, numValueOf);
                } else {
                    this.f4418l = Pair.create("coin", 10);
                }
            } catch (JSONException unused) {
                HaoboLog.e(HaoboLog.jsonLogTag, "Error parse rewarded item: " + str2);
                this.f4418l = Pair.create("coin", 10);
            }
        }
    }

    private static float a(float f2, float f3, float f4, float f5) {
        float f6 = f2 - f4;
        float f7 = f3 - f5;
        return a((float) Math.sqrt((f6 * f6) + (f7 * f7)));
    }

    private static float a(float f2) {
        return f2 / g.a().k().density;
    }
}
