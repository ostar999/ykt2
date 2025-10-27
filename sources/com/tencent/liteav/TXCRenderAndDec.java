package com.tencent.liteav;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.opengl.GLES20;
import android.os.Bundle;
import android.view.Surface;
import cn.hutool.core.text.StrPool;
import com.google.android.exoplayer2.C;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.module.TXCEventRecorderProxy;
import com.tencent.liteav.basic.module.TXCKeyPointReportProxy;
import com.tencent.liteav.basic.opengl.TXCOpenGlUtils;
import com.tencent.liteav.basic.structs.TXSNALPacket;
import com.tencent.liteav.basic.structs.TXSVideoFrame;
import com.tencent.liteav.basic.util.TXCTimeUtil;
import com.tencent.liteav.renderer.a;
import com.tencent.liteav.videodecoder.TXCVideoDecoder;
import com.tencent.rtmp.TXLiveConstants;
import com.yikaobang.yixue.R2;
import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;

/* loaded from: classes6.dex */
public class TXCRenderAndDec extends com.tencent.liteav.basic.module.a implements com.tencent.liteav.basic.b.b, a.InterfaceC0336a, com.tencent.liteav.renderer.f, com.tencent.liteav.videodecoder.h {
    public static final String TAG = "TXCRenderAndDec";
    private Context mContext;
    private WeakReference<com.tencent.liteav.basic.b.b> mNotifyListener;
    private WeakReference<b> mRenderAndDecDelegate;
    private o mVideoFrameListener;
    private h mConfig = null;
    private boolean mEnableLimitHWDecCache = false;
    private TXCVideoDecoder mVideoDecoder = null;
    private boolean mEnableDecoderChange = false;
    private boolean mEnableRestartDecoder = false;
    private com.tencent.liteav.renderer.e mVideoRender = null;
    private com.tencent.liteav.basic.opengl.j mRGBA2YUVFilter = null;
    private int mCustomRenderFrameBufferId = -1;
    private com.tencent.liteav.basic.enums.b mVideoFrameFormat = com.tencent.liteav.basic.enums.b.UNKNOWN;
    private boolean mRealTime = false;
    private boolean mIsRendering = false;
    private int mStreamType = 0;
    private long mFrameDecErrCnt = 0;
    private long mLastReqKeyFrameTS = 0;
    private boolean mFirstRender = false;
    private int mRenderMode = 0;
    private int mRenderRotation = 0;
    private long mLastRenderCalculateTS = 0;
    private long mRenderFrameCount = 0;
    private long mLastRenderFrameCount = 0;
    private long mCurrentRenderPts = 0;
    private a mDecListener = null;

    public interface a {
        void a(SurfaceTexture surfaceTexture);
    }

    public interface b {
        void onRequestKeyFrame(String str, int i2);
    }

    public TXCRenderAndDec(Context context) {
        this.mContext = null;
        this.mContext = context;
        com.tencent.liteav.basic.c.c.a().a(this.mContext);
    }

    private void notifyEvent(int i2, String str) {
        Bundle bundle = new Bundle();
        bundle.putString("EVT_USERID", getID());
        bundle.putInt("EVT_ID", i2);
        bundle.putLong("EVT_TIME", TXCTimeUtil.getTimeTick());
        if (str != null) {
            bundle.putCharSequence(TXLiveConstants.EVT_DESCRIPTION, str);
        }
        bundle.putInt("EVT_STREAM_TYPE", this.mStreamType);
        com.tencent.liteav.basic.util.h.a(this.mNotifyListener, i2, bundle);
    }

    private void requestKeyFrame() {
        b bVar;
        long timeTick = TXCTimeUtil.getTimeTick();
        if (timeTick > this.mLastReqKeyFrameTS + C.DEFAULT_MAX_SEEK_TO_PREVIOUS_POSITION_MS) {
            this.mLastReqKeyFrameTS = timeTick;
            TXCLog.e(TAG, "requestKeyFrame: " + getID());
            WeakReference<b> weakReference = this.mRenderAndDecDelegate;
            if (weakReference == null || (bVar = weakReference.get()) == null) {
                return;
            }
            bVar.onRequestKeyFrame(getID(), this.mStreamType);
        }
    }

    private void startDecode(SurfaceTexture surfaceTexture) {
        TXCVideoDecoder tXCVideoDecoder = this.mVideoDecoder;
        if (tXCVideoDecoder != null) {
            tXCVideoDecoder.stop();
            tXCVideoDecoder.enableHWDec(this.mConfig.f19363h);
            tXCVideoDecoder.config(this.mConfig.f19375t);
            TXCLog.i(TAG, "trtc_ start decode " + surfaceTexture + ", hw: " + this.mConfig.f19363h + ", id " + getID() + StrPool.UNDERLINE + this.mStreamType);
            if (surfaceTexture != null) {
                tXCVideoDecoder.setup(surfaceTexture, (ByteBuffer) null, (ByteBuffer) null, !this.mRealTime);
                tXCVideoDecoder.setUserId(getID());
                tXCVideoDecoder.start();
            } else {
                if (this.mConfig.f19363h) {
                    return;
                }
                tXCVideoDecoder.setup((Surface) null, (ByteBuffer) null, (ByteBuffer) null, !this.mRealTime);
                tXCVideoDecoder.setUserId(getID());
                tXCVideoDecoder.start();
            }
        }
    }

    public void decVideo(TXSNALPacket tXSNALPacket) {
    }

    public void enableDecoderChange(boolean z2) {
        this.mEnableDecoderChange = z2;
    }

    public void enableLimitDecCache(boolean z2) {
        this.mEnableLimitHWDecCache = z2;
        TXCVideoDecoder tXCVideoDecoder = this.mVideoDecoder;
        if (tXCVideoDecoder != null) {
            tXCVideoDecoder.enableLimitDecCache(z2);
        }
    }

    public void enableReport(boolean z2) {
        com.tencent.liteav.renderer.e eVar = this.mVideoRender;
        if (eVar != null) {
            eVar.c(z2);
        }
    }

    public void enableRestartDecoder(boolean z2) {
        this.mEnableRestartDecoder = z2;
    }

    public long getAVNetRecvInterval() {
        return getLongValue(R2.dimen.alivc_common_height_group_100, 2);
    }

    public long getAVPlayInterval() {
        return getLongValue(R2.dimen.alivc_common_height_cv_150, 2);
    }

    public h getConfig() {
        return this.mConfig;
    }

    public long getCurrentRenderPts() {
        return this.mCurrentRenderPts;
    }

    public int getStreamType() {
        return this.mStreamType;
    }

    public long getVideoCacheDuration() {
        return getIntValue(R2.dimen.alivc_common_font_32, 2);
    }

    public long getVideoCacheFrameCount() {
        return getIntValue(R2.dimen.alivc_common_font_7, 2);
    }

    public int getVideoDecCacheFrameCount() {
        return getIntValue(R2.dimen.alivc_common_font_8, 2);
    }

    public int getVideoGop() {
        return getIntValue(R2.dimen.dp_511);
    }

    public com.tencent.liteav.renderer.e getVideoRender() {
        return this.mVideoRender;
    }

    public boolean isRendering() {
        return this.mIsRendering;
    }

    @Override // com.tencent.liteav.videodecoder.h
    public void onDecodeFailed(int i2) {
        TXCVideoDecoder tXCVideoDecoder;
        TXCLog.e(TAG, "video decode failed " + i2);
        if (i2 != -2) {
            if (-4 == i2) {
                notifyEvent(TXLiteAVCode.ERR_HEVC_SOFTDECODER_START_FAIL, "h265 softdecoder start fail.");
                return;
            }
            requestKeyFrame();
            int i3 = this.mStreamType;
            long j2 = this.mFrameDecErrCnt + 1;
            this.mFrameDecErrCnt = j2;
            setStatusValue(17014, i3, Long.valueOf(j2));
            return;
        }
        TXCLog.w(TAG, "use h265 softdecoder but not set h265 softdecoder to sdk, isH265SoftDecodeExist= " + com.tencent.liteav.basic.a.a());
        if (com.tencent.liteav.basic.a.a() || (tXCVideoDecoder = this.mVideoDecoder) == null || !tXCVideoDecoder.isH265()) {
            return;
        }
        this.mConfig.f19363h = true;
        tXCVideoDecoder.restart(true);
    }

    @Override // com.tencent.liteav.videodecoder.h
    public void onDecodeFrame(TXSVideoFrame tXSVideoFrame, int i2, int i3, long j2, long j3, int i4) {
        com.tencent.liteav.basic.enums.b bVar;
        int i5 = i4;
        this.mCurrentRenderPts = j2;
        if (i5 == 0 || i5 == 1 || i5 == 2 || i5 == 3) {
            i5 = 360 - (i5 * 90);
        }
        o oVar = this.mVideoFrameListener;
        if (oVar != null && tXSVideoFrame != null && ((bVar = this.mVideoFrameFormat) == com.tencent.liteav.basic.enums.b.I420 || bVar == com.tencent.liteav.basic.enums.b.NV21)) {
            TXSVideoFrame tXSVideoFrameM116clone = this.mVideoRender != null ? tXSVideoFrame.m116clone() : tXSVideoFrame;
            tXSVideoFrameM116clone.rotation = (this.mRenderRotation + i5) % 360;
            if (this.mVideoFrameFormat == com.tencent.liteav.basic.enums.b.NV21) {
                tXSVideoFrameM116clone.loadNV21BufferFromI420Buffer();
            }
            oVar.onRenderVideoFrame(getID(), this.mStreamType, tXSVideoFrameM116clone);
        }
        if (!this.mFirstRender) {
            this.mFirstRender = true;
            TXCEventRecorderProxy.a(getID(), 5007, -1L, -1L, "", this.mStreamType);
            if (this.mVideoRender == null) {
                TXCKeyPointReportProxy.a(getID(), 40022, 0L, this.mStreamType);
            }
            if (this.mVideoDecoder != null) {
                TXCKeyPointReportProxy.a(getID(), 40029, this.mVideoDecoder.GetDecodeFirstFrameTS(), this.mStreamType);
            }
            if (this.mStreamType == 2) {
                TXCKeyPointReportProxy.a(getID(), 32004);
            }
        }
        com.tencent.liteav.renderer.e eVar = this.mVideoRender;
        if (eVar != null) {
            eVar.a(tXSVideoFrame, i2, i3, i5);
        } else {
            if (this.mLastRenderCalculateTS != 0) {
                this.mRenderFrameCount++;
                return;
            }
            this.mLastRenderCalculateTS = System.currentTimeMillis();
            this.mLastRenderFrameCount = 0L;
            this.mRenderFrameCount = 0L;
        }
    }

    @Override // com.tencent.liteav.videodecoder.h
    public void onDecoderChange(String str, boolean z2) {
    }

    @Override // com.tencent.liteav.basic.b.b
    public void onNotifyEvent(int i2, Bundle bundle) {
        if (i2 == 2106) {
            this.mConfig.f19363h = false;
            TXCVideoDecoder tXCVideoDecoder = this.mVideoDecoder;
            if (tXCVideoDecoder != null) {
                tXCVideoDecoder.restart(false);
            }
        } else if (i2 == -2304) {
            if (com.tencent.liteav.basic.a.a()) {
                this.mConfig.f19363h = false;
                TXCVideoDecoder tXCVideoDecoder2 = this.mVideoDecoder;
                if (tXCVideoDecoder2 != null) {
                    tXCVideoDecoder2.restart(false);
                    return;
                }
                return;
            }
        } else if (i2 == 2020) {
            TXCLog.e(TAG, "decoding too many frame(>40) without output! request key frame now.");
            requestKeyFrame();
            return;
        }
        bundle.putInt("EVT_STREAM_TYPE", this.mStreamType);
        com.tencent.liteav.basic.util.h.a(this.mNotifyListener, i2, bundle);
    }

    @Override // com.tencent.liteav.renderer.f
    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture) {
        TXCLog.w(TAG, "play decode when surface texture create hw " + this.mConfig.f19363h);
        TXCVideoDecoder tXCVideoDecoder = this.mVideoDecoder;
        if (tXCVideoDecoder != null) {
            tXCVideoDecoder.setup(surfaceTexture, (ByteBuffer) null, (ByteBuffer) null, !this.mRealTime);
        }
        if (this.mConfig.f19363h) {
            startDecode(surfaceTexture);
        }
        resetPeriodStatistics();
        enableReport(true);
    }

    @Override // com.tencent.liteav.renderer.f
    public void onSurfaceTextureDestroy(SurfaceTexture surfaceTexture) {
        TXCVideoDecoder tXCVideoDecoder;
        try {
            TXCLog.w(TAG, "play:stop decode when surface texture release");
            if (this.mConfig.f19363h && (tXCVideoDecoder = this.mVideoDecoder) != null) {
                tXCVideoDecoder.stop();
            }
            int i2 = this.mCustomRenderFrameBufferId;
            if (i2 != -1) {
                TXCOpenGlUtils.b(i2);
                this.mCustomRenderFrameBufferId = -1;
            }
            com.tencent.liteav.basic.opengl.j jVar = this.mRGBA2YUVFilter;
            if (jVar != null) {
                jVar.d();
                this.mRGBA2YUVFilter = null;
            }
            a aVar = this.mDecListener;
            if (aVar != null) {
                aVar.a(surfaceTexture);
            }
            resetPeriodStatistics();
            enableReport(false);
        } catch (Exception e2) {
            TXCLog.e(TAG, "onSurfaceTextureDestroy failed.", e2);
        }
    }

    @Override // com.tencent.liteav.renderer.a.InterfaceC0336a
    public void onTextureProcess(int i2, int i3, int i4, int i5) {
        o oVar = this.mVideoFrameListener;
        if (oVar == null) {
            return;
        }
        TXSVideoFrame tXSVideoFrame = new TXSVideoFrame();
        tXSVideoFrame.width = i3;
        tXSVideoFrame.height = i4;
        tXSVideoFrame.pts = TXCTimeUtil.getTimeTick();
        tXSVideoFrame.rotation = (i5 + this.mRenderRotation) % 360;
        com.tencent.liteav.basic.enums.b bVar = this.mVideoFrameFormat;
        if (bVar == com.tencent.liteav.basic.enums.b.RGBA) {
            tXSVideoFrame.textureId = i2;
            tXSVideoFrame.eglContext = TXCOpenGlUtils.e();
            if (this.mCustomRenderFrameBufferId == -1) {
                this.mCustomRenderFrameBufferId = TXCOpenGlUtils.d();
            }
            TXCOpenGlUtils.a(i2, this.mCustomRenderFrameBufferId);
            GLES20.glBindFramebuffer(36160, this.mCustomRenderFrameBufferId);
            this.mVideoFrameListener.onRenderVideoFrame(getID(), this.mStreamType, tXSVideoFrame);
            TXCOpenGlUtils.d(this.mCustomRenderFrameBufferId);
            return;
        }
        if (bVar == com.tencent.liteav.basic.enums.b.TEXTURE_2D) {
            tXSVideoFrame.textureId = i2;
            com.tencent.liteav.renderer.e eVar = this.mVideoRender;
            if (eVar instanceof com.tencent.liteav.renderer.a) {
                tXSVideoFrame.eglContext = ((com.tencent.liteav.renderer.a) eVar).b();
            }
            oVar.onRenderVideoFrame(getID(), this.mStreamType, tXSVideoFrame);
            return;
        }
        TXCVideoDecoder tXCVideoDecoder = this.mVideoDecoder;
        if (tXCVideoDecoder == null || tXCVideoDecoder.isHardwareDecode()) {
            if (this.mRGBA2YUVFilter == null) {
                if (this.mVideoFrameFormat == com.tencent.liteav.basic.enums.b.NV21) {
                    this.mRGBA2YUVFilter = new com.tencent.liteav.beauty.b.o(3);
                } else {
                    this.mRGBA2YUVFilter = new com.tencent.liteav.beauty.b.o(1);
                }
                this.mRGBA2YUVFilter.a(true);
                if (this.mRGBA2YUVFilter.a()) {
                    this.mRGBA2YUVFilter.a(i3, i4);
                } else {
                    TXCLog.i(TAG, "throwVideoFrame->release mVideoFrameFilter");
                    this.mRGBA2YUVFilter = null;
                }
            }
            if (this.mRGBA2YUVFilter != null) {
                GLES20.glViewport(0, 0, i3, i4);
                this.mRGBA2YUVFilter.a(i3, i4);
                this.mRGBA2YUVFilter.b(i2);
                GLES20.glBindFramebuffer(36160, this.mRGBA2YUVFilter.m());
                oVar.onRenderVideoFrame(getID(), this.mStreamType, tXSVideoFrame);
                GLES20.glBindFramebuffer(36160, 0);
            }
        }
    }

    @Override // com.tencent.liteav.videodecoder.h
    public void onVideoSizeChange(int i2, int i3) {
        com.tencent.liteav.renderer.e eVar = this.mVideoRender;
        if (eVar != null) {
            eVar.c(i2, i3);
        }
        Bundle bundle = new Bundle();
        bundle.putCharSequence(TXLiveConstants.EVT_DESCRIPTION, "Resolution changed to" + i2 + "x" + i3);
        bundle.putInt("EVT_PARAM1", i2);
        bundle.putInt("EVT_PARAM2", i3);
        bundle.putString("EVT_USERID", getID());
        bundle.putLong("EVT_TIME", TXCTimeUtil.getTimeTick());
        bundle.putLong(TXLiveConstants.EVT_UTC_TIME, TXCTimeUtil.getUtcTimeTick());
        onNotifyEvent(2009, bundle);
        setStatusValue(5003, this.mStreamType, Integer.valueOf((i2 << 16) | i3));
        long j2 = i2;
        long j3 = i3;
        TXCEventRecorderProxy.a(getID(), 4003, j2, j3, "", this.mStreamType);
        TXCKeyPointReportProxy.a(getID(), 40002, j2, this.mStreamType);
        TXCKeyPointReportProxy.a(getID(), 40003, j3, this.mStreamType);
    }

    public void resetPeriodFeelingStatistics() {
        com.tencent.liteav.renderer.e eVar = this.mVideoRender;
        if (eVar != null) {
            eVar.n();
        }
    }

    public void resetPeriodStatistics() {
        com.tencent.liteav.renderer.e eVar = this.mVideoRender;
        if (eVar != null) {
            eVar.m();
        }
    }

    public void restartDecoder() {
        TXCVideoDecoder tXCVideoDecoder = this.mVideoDecoder;
        if (tXCVideoDecoder == null || !tXCVideoDecoder.isH265()) {
            return;
        }
        tXCVideoDecoder.restart(true);
    }

    public void setBlockInterval(int i2) {
        com.tencent.liteav.renderer.e eVar = this.mVideoRender;
        if (eVar != null) {
            eVar.f(i2);
        }
    }

    public void setConfig(h hVar) {
        this.mConfig = hVar;
        com.tencent.liteav.renderer.e eVar = this.mVideoRender;
        if (eVar != null) {
            eVar.b(hVar.f19359d);
        }
    }

    public void setDecListener(a aVar) {
        this.mDecListener = aVar;
    }

    @Override // com.tencent.liteav.basic.module.a
    public void setID(String str) {
        super.setID(str);
        com.tencent.liteav.renderer.e eVar = this.mVideoRender;
        if (eVar != null) {
            eVar.setID(getID());
        }
        TXCVideoDecoder tXCVideoDecoder = this.mVideoDecoder;
        if (tXCVideoDecoder != null) {
            tXCVideoDecoder.setUserId(str);
        }
    }

    public void setNotifyListener(com.tencent.liteav.basic.b.b bVar) {
        this.mNotifyListener = new WeakReference<>(bVar);
    }

    public void setRenderAndDecDelegate(b bVar) {
        this.mRenderAndDecDelegate = new WeakReference<>(bVar);
    }

    public void setRenderMirrorType(int i2) {
        TXCLog.i(TAG, "setRenderMirrorType " + i2);
        com.tencent.liteav.renderer.e eVar = this.mVideoRender;
        if (eVar != null) {
            eVar.d(i2);
        }
    }

    public void setRenderMode(int i2) {
        this.mRenderMode = i2;
        com.tencent.liteav.renderer.e eVar = this.mVideoRender;
        if (eVar != null) {
            eVar.c(i2);
        }
    }

    public void setRenderRotation(int i2) {
        TXCLog.i(TAG, "vrotation setRenderRotation " + i2);
        this.mRenderRotation = i2;
        com.tencent.liteav.renderer.e eVar = this.mVideoRender;
        if (eVar != null) {
            eVar.e(i2);
        }
    }

    public void setStreamType(int i2) {
        this.mStreamType = i2;
        com.tencent.liteav.renderer.e eVar = this.mVideoRender;
        if (eVar != null) {
            eVar.a(i2);
        }
        TXCVideoDecoder tXCVideoDecoder = this.mVideoDecoder;
        if (tXCVideoDecoder != null) {
            tXCVideoDecoder.setStreamType(this.mStreamType);
        }
    }

    public void setVideoFrameListener(o oVar, com.tencent.liteav.basic.enums.b bVar) {
        this.mVideoFrameListener = oVar;
        this.mVideoFrameFormat = bVar;
        TXCLog.i(TAG, "setVideoFrameListener->enter listener: " + oVar + ", format: " + bVar);
        com.tencent.liteav.renderer.e eVar = this.mVideoRender;
        if (eVar == null || !(eVar instanceof com.tencent.liteav.renderer.a)) {
            return;
        }
        if (oVar == null) {
            TXCLog.i(TAG, "setCustomRenderListener-> clean listener.");
            ((com.tencent.liteav.renderer.a) this.mVideoRender).b((a.InterfaceC0336a) null);
        } else {
            TXCLog.i(TAG, "setCustomRenderListener-> set listener.");
            ((com.tencent.liteav.renderer.a) this.mVideoRender).b((a.InterfaceC0336a) this);
        }
    }

    public void setVideoRender(com.tencent.liteav.renderer.e eVar) {
        TXCLog.i(TAG, "set video render " + eVar + " id " + getID() + ", " + this.mStreamType);
        this.mVideoRender = eVar;
        if (eVar == null) {
            return;
        }
        eVar.setID(getID());
        this.mVideoRender.a(this.mStreamType);
        this.mVideoRender.a((com.tencent.liteav.basic.b.b) this);
        this.mVideoRender.c(this.mRenderMode);
        this.mVideoRender.e(this.mRenderRotation);
        if (this.mVideoFrameListener != null) {
            com.tencent.liteav.renderer.e eVar2 = this.mVideoRender;
            if (eVar2 instanceof com.tencent.liteav.renderer.a) {
                ((com.tencent.liteav.renderer.a) eVar2).b((a.InterfaceC0336a) this);
            }
        }
        h hVar = this.mConfig;
        if (hVar != null) {
            this.mVideoRender.b(hVar.f19359d);
        }
    }

    public void start(boolean z2) {
        TXCLog.i(TAG, "start render dec " + getID() + ", " + this.mStreamType);
        this.mRealTime = z2;
        this.mFrameDecErrCnt = 0L;
        this.mLastReqKeyFrameTS = 0L;
        com.tencent.liteav.renderer.e eVar = this.mVideoRender;
        if (eVar != null) {
            eVar.a((com.tencent.liteav.renderer.f) this);
            this.mVideoRender.e();
            this.mVideoRender.setID(getID());
        }
        TXCVideoDecoder tXCVideoDecoder = new TXCVideoDecoder();
        this.mVideoDecoder = tXCVideoDecoder;
        tXCVideoDecoder.setUserId(getID());
        this.mVideoDecoder.setStreamType(this.mStreamType);
        this.mVideoDecoder.setListener(this);
        this.mVideoDecoder.setNotifyListener(this);
        this.mVideoDecoder.enableChange(this.mEnableDecoderChange);
        this.mVideoDecoder.enableLimitDecCache(this.mEnableLimitHWDecCache);
        this.mVideoDecoder.enableRestart(this.mEnableRestartDecoder);
        startDecode();
        this.mIsRendering = true;
    }

    public void startVideo() {
        stopVideo();
        this.mRealTime = true;
        this.mFrameDecErrCnt = 0L;
        this.mLastReqKeyFrameTS = 0L;
        this.mCurrentRenderPts = 0L;
        com.tencent.liteav.renderer.e eVar = this.mVideoRender;
        if (eVar != null) {
            eVar.a((com.tencent.liteav.renderer.f) this);
            this.mVideoRender.e();
            this.mVideoRender.setID(getID());
        }
        TXCLog.i(TAG, "start video dec " + getID() + ", " + this.mStreamType);
        TXCVideoDecoder tXCVideoDecoder = new TXCVideoDecoder();
        this.mVideoDecoder = tXCVideoDecoder;
        tXCVideoDecoder.setUserId(getID());
        this.mVideoDecoder.setStreamType(this.mStreamType);
        this.mVideoDecoder.setListener(this);
        this.mVideoDecoder.setNotifyListener(this);
        this.mVideoDecoder.enableChange(this.mEnableDecoderChange);
        this.mVideoDecoder.enableRestart(this.mEnableRestartDecoder);
        this.mVideoDecoder.enableLimitDecCache(this.mEnableLimitHWDecCache);
        startDecode();
        this.mIsRendering = true;
    }

    public void stop() {
        TXCLog.i(TAG, "stop video render dec " + getID() + ", " + this.mStreamType);
        this.mIsRendering = false;
        this.mRealTime = false;
        TXCVideoDecoder tXCVideoDecoder = this.mVideoDecoder;
        if (tXCVideoDecoder != null) {
            tXCVideoDecoder.setListener(null);
            this.mVideoDecoder.setNotifyListener(null);
            this.mVideoDecoder.stop();
        }
        com.tencent.liteav.renderer.e eVar = this.mVideoRender;
        if (eVar != null) {
            eVar.a(true);
            this.mVideoRender.a((com.tencent.liteav.renderer.f) null);
        }
    }

    public void stopVideo() {
        this.mIsRendering = false;
        if (this.mVideoDecoder != null) {
            TXCLog.i(TAG, "stop video dec " + getID() + ", " + this.mStreamType);
            this.mVideoDecoder.setListener(null);
            this.mVideoDecoder.setNotifyListener(null);
            this.mVideoDecoder.stop();
        }
        com.tencent.liteav.renderer.e eVar = this.mVideoRender;
        if (eVar != null) {
            eVar.a(false);
            this.mVideoRender.a((com.tencent.liteav.renderer.f) null);
        }
    }

    public void updateLoadInfo() {
        TXCVideoDecoder tXCVideoDecoder = this.mVideoDecoder;
        if (tXCVideoDecoder != null) {
            setStatusValue(5002, this.mStreamType, Long.valueOf(tXCVideoDecoder.isHardwareDecode() ? 1L : 0L));
        }
        com.tencent.liteav.renderer.e eVar = this.mVideoRender;
        if (eVar != null) {
            eVar.o();
            return;
        }
        long jCurrentTimeMillis = System.currentTimeMillis();
        long j2 = jCurrentTimeMillis - this.mLastRenderCalculateTS;
        if (j2 >= 1000) {
            this.mLastRenderFrameCount = this.mRenderFrameCount;
            this.mLastRenderCalculateTS = jCurrentTimeMillis;
            setStatusValue(6002, this.mStreamType, Double.valueOf(((r4 - this.mLastRenderFrameCount) * 1000.0d) / j2));
        }
    }

    private void startDecode() {
        com.tencent.liteav.renderer.e eVar = this.mVideoRender;
        startDecode(eVar != null ? eVar.a() : null);
    }
}
