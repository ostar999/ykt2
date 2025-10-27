package com.tencent.rtmp;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.Surface;
import com.tencent.rtmp.ui.TXCloudVideoView;
import com.tencent.ugc.TXRecordCommon;
import org.json.JSONException;

/* loaded from: classes6.dex */
public class TXLivePlayer {
    public static final int PLAY_TYPE_LIVE_FLV = 1;
    public static final int PLAY_TYPE_LIVE_RTMP = 0;
    public static final int PLAY_TYPE_LIVE_RTMP_ACC = 5;
    public static final int PLAY_TYPE_LOCAL_VIDEO = 6;
    public static final int PLAY_TYPE_VOD_FLV = 2;
    public static final int PLAY_TYPE_VOD_HLS = 3;
    public static final int PLAY_TYPE_VOD_MP4 = 4;
    public static final String TAG = "TXLivePlayer";
    private a mTXLivePlayerImpl;

    public interface ITXAudioRawDataListener {
        void onAudioInfoChanged(int i2, int i3, int i4);

        void onPcmDataAvailable(byte[] bArr, long j2);
    }

    public interface ITXAudioVolumeEvaluationListener {
        void onAudioVolumeEvaluationNotify(int i2);
    }

    public interface ITXLivePlayVideoRenderListener {
        void onRenderVideoFrame(TXLiteAVTexture tXLiteAVTexture);
    }

    public interface ITXSnapshotListener {
        void onSnapshot(Bitmap bitmap);
    }

    public interface ITXVideoRawDataListener {
        void onVideoRawDataAvailable(byte[] bArr, int i2, int i3, int i4);
    }

    public static class TXLiteAVTexture {
        public Object eglContext;
        public int height;
        public int textureId;
        public int width;
    }

    public TXLivePlayer(Context context) {
        this.mTXLivePlayerImpl = new a(context);
    }

    public boolean addVideoRawData(byte[] bArr) {
        return this.mTXLivePlayerImpl.a(bArr);
    }

    public void callExperimentalAPI(String str) throws JSONException {
        this.mTXLivePlayerImpl.b(str);
    }

    public void enableAudioVolumeEvaluation(int i2) {
        this.mTXLivePlayerImpl.e(i2);
    }

    public boolean enableHardwareDecode(boolean z2) {
        return this.mTXLivePlayerImpl.b(z2);
    }

    public long getCurrentRenderPts() {
        return this.mTXLivePlayerImpl.f();
    }

    public boolean isPlaying() {
        return this.mTXLivePlayerImpl.a();
    }

    public void pause() {
        this.mTXLivePlayerImpl.b();
    }

    public int prepareLiveSeek(String str, int i2) {
        return this.mTXLivePlayerImpl.b(str, i2);
    }

    public void resume() {
        this.mTXLivePlayerImpl.c();
    }

    public int resumeLive() {
        return this.mTXLivePlayerImpl.e();
    }

    public void seek(int i2) {
        this.mTXLivePlayerImpl.g(i2);
    }

    public void setAudioRawDataListener(ITXAudioRawDataListener iTXAudioRawDataListener) {
        this.mTXLivePlayerImpl.a(iTXAudioRawDataListener);
    }

    public void setAudioRoute(int i2) {
        this.mTXLivePlayerImpl.d(i2);
    }

    public void setAudioVolumeEvaluationListener(ITXAudioVolumeEvaluationListener iTXAudioVolumeEvaluationListener) {
        this.mTXLivePlayerImpl.a(iTXAudioVolumeEvaluationListener);
    }

    @Deprecated
    public void setAutoPlay(boolean z2) {
        this.mTXLivePlayerImpl.d(z2);
    }

    public void setConfig(TXLivePlayConfig tXLivePlayConfig) {
        this.mTXLivePlayerImpl.a(tXLivePlayConfig);
    }

    public void setMute(boolean z2) {
        this.mTXLivePlayerImpl.c(z2);
    }

    public void setPlayListener(ITXLivePlayListener iTXLivePlayListener) {
        this.mTXLivePlayerImpl.a(iTXLivePlayListener);
    }

    public void setPlayerView(TXCloudVideoView tXCloudVideoView) {
        this.mTXLivePlayerImpl.a(tXCloudVideoView);
    }

    @Deprecated
    public void setRate(float f2) {
        this.mTXLivePlayerImpl.a(f2);
    }

    public void setRenderMode(int i2) {
        this.mTXLivePlayerImpl.a(i2);
    }

    public void setRenderRotation(int i2) {
        this.mTXLivePlayerImpl.b(i2);
    }

    public void setSurface(Surface surface) {
        this.mTXLivePlayerImpl.a(surface);
    }

    public void setSurfaceSize(int i2, int i3) {
        this.mTXLivePlayerImpl.a(i2, i3);
    }

    public void setVideoRawDataListener(ITXVideoRawDataListener iTXVideoRawDataListener) {
        this.mTXLivePlayerImpl.a(iTXVideoRawDataListener);
    }

    public void setVideoRecordListener(TXRecordCommon.ITXVideoRecordListener iTXVideoRecordListener) {
        this.mTXLivePlayerImpl.a(iTXVideoRecordListener);
    }

    public int setVideoRenderListener(ITXLivePlayVideoRenderListener iTXLivePlayVideoRenderListener, Object obj) {
        return this.mTXLivePlayerImpl.a(iTXLivePlayVideoRenderListener, obj);
    }

    public void setVolume(int i2) {
        this.mTXLivePlayerImpl.c(i2);
    }

    public void snapshot(ITXSnapshotListener iTXSnapshotListener) {
        this.mTXLivePlayerImpl.a(iTXSnapshotListener);
    }

    public int startPlay(String str, int i2) {
        return this.mTXLivePlayerImpl.a(str, i2);
    }

    public int startRecord(int i2) {
        return this.mTXLivePlayerImpl.f(i2);
    }

    public int stopPlay(boolean z2) {
        return this.mTXLivePlayerImpl.a(z2);
    }

    public int stopRecord() {
        return this.mTXLivePlayerImpl.d();
    }

    public int switchStream(String str) {
        return this.mTXLivePlayerImpl.a(str);
    }
}
