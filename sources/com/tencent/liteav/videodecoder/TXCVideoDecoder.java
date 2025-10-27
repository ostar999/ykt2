package com.tencent.liteav.videodecoder;

import android.graphics.SurfaceTexture;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.view.Surface;
import cn.hutool.core.text.StrPool;
import com.plv.livescenes.model.PLVLiveClassDetailVO;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.module.TXCEventRecorderProxy;
import com.tencent.liteav.basic.module.TXCKeyPointReportProxy;
import com.tencent.liteav.basic.module.TXCStatus;
import com.tencent.liteav.basic.structs.TXSNALPacket;
import com.tencent.liteav.basic.structs.TXSVideoFrame;
import com.tencent.liteav.basic.util.TXCTimeUtil;
import com.tencent.rtmp.TXLiveConstants;
import com.yikaobang.yixue.R2;
import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import org.json.JSONArray;

/* loaded from: classes6.dex */
public class TXCVideoDecoder implements com.tencent.liteav.basic.b.b, h {
    private static final boolean NEW_DECODER = true;
    private static final String TAG = "TXCVideoDecoder";
    private static long mDecodeFirstFrameTS;
    private int mDecoderCacheNum;
    private a mDecoderHandler;
    h mDecoderListener;
    private long mNativeContext;
    private WeakReference<com.tencent.liteav.basic.b.b> mNotifyListener;
    private ByteBuffer mPps;
    private ByteBuffer mSps;
    Surface mSurface;
    private String mUserId;
    b mVideoDecoder;
    private boolean mRestarting = false;
    private int mStreamType = 0;
    private int mVideoWidth = 0;
    private int mVideoHeight = 0;
    private boolean mEnableDecoderChange = false;
    private boolean mEnableRestartDecoder = false;
    private boolean mEnableLimitDecCache = false;
    private JSONArray mDecFormat = null;
    private ArrayList<TXSNALPacket> mNALList = new ArrayList<>();
    boolean mHWDec = true;
    boolean mH265 = false;
    boolean mNeedSortFrame = true;
    boolean mRecvFirstFrame = false;

    static {
        com.tencent.liteav.basic.util.h.d();
    }

    public TXCVideoDecoder() {
        mDecodeFirstFrameTS = 0L;
    }

    private void addOneNalToDecoder(TXSNALPacket tXSNALPacket) {
        boolean z2 = tXSNALPacket.nalType == 0;
        Bundle bundle = new Bundle();
        bundle.putBoolean(PLVLiveClassDetailVO.MENUTYPE_IFRAME, z2);
        bundle.putByteArray("nal", tXSNALPacket.nalData);
        bundle.putLong("pts", tXSNALPacket.pts);
        bundle.putLong("dts", tXSNALPacket.dts);
        bundle.putInt("codecId", tXSNALPacket.codecId);
        Message message = new Message();
        message.what = 101;
        message.setData(bundle);
        a aVar = this.mDecoderHandler;
        if (aVar != null) {
            aVar.sendMessage(message);
        }
        this.mDecoderCacheNum++;
    }

    private void decNALByNewWay(TXSNALPacket tXSNALPacket) {
        if (this.mHWDec) {
            decodeFrame(tXSNALPacket.nalData, tXSNALPacket.pts, tXSNALPacket.dts, tXSNALPacket.rotation, tXSNALPacket.codecId, 0, 0, tXSNALPacket.nalType);
        } else {
            synchronized (this) {
                nativeDecodeFrame(this.mNativeContext, tXSNALPacket.nalData, tXSNALPacket.nalType, tXSNALPacket.pts, tXSNALPacket.dts, tXSNALPacket.rotation, tXSNALPacket.codecId);
            }
        }
    }

    private void decNALByOldWay(TXSNALPacket tXSNALPacket) {
        try {
            boolean z2 = tXSNALPacket.nalType == 0;
            boolean z3 = this.mRecvFirstFrame;
            if (!z3 && !z2) {
                TXCLog.i(TAG, "play:decode: push nal ignore p frame when not got i frame");
                return;
            }
            if (!z3 && z2) {
                TXCLog.w(TAG, "play:decode: push first i frame");
                this.mRecvFirstFrame = true;
            }
            if (!this.mRestarting && tXSNALPacket.codecId == 1 && !this.mHWDec) {
                TXCLog.w(TAG, "play:decode: hevc decode error  ");
                com.tencent.liteav.basic.util.h.a(this.mNotifyListener, -2304, "h265 Decoding failed");
                this.mRestarting = true;
            }
            if (this.mDecoderHandler != null) {
                if (!this.mNALList.isEmpty()) {
                    Iterator<TXSNALPacket> it = this.mNALList.iterator();
                    while (it.hasNext()) {
                        addOneNalToDecoder(it.next());
                    }
                }
                this.mNALList.clear();
                addOneNalToDecoder(tXSNALPacket);
                return;
            }
            if (z2 && !this.mNALList.isEmpty()) {
                this.mNALList.clear();
            }
            this.mNALList.add(tXSNALPacket);
            if (this.mRestarting) {
                return;
            }
            start();
        } catch (Exception e2) {
            TXCLog.e(TAG, "decode NAL By Old way failed.", e2);
        }
    }

    private void decodeFrame(byte[] bArr, long j2, long j3, int i2, int i3, int i4, int i5, int i6) {
        TXSNALPacket tXSNALPacket = new TXSNALPacket();
        tXSNALPacket.nalData = bArr;
        tXSNALPacket.pts = j2;
        tXSNALPacket.dts = j3;
        tXSNALPacket.rotation = i2;
        tXSNALPacket.codecId = i3;
        tXSNALPacket.nalType = i6;
        synchronized (this) {
            if (this.mNativeContext != 0 && this.mVideoDecoder == null) {
                if (i3 == 1) {
                    this.mH265 = true;
                } else {
                    this.mH265 = false;
                }
                g gVar = new g();
                gVar.a(i4, i5);
                gVar.setListener(this);
                gVar.setNotifyListener(new WeakReference<>(this));
                gVar.a(this.mDecFormat);
                gVar.config(this.mSurface);
                gVar.enableLimitDecCache(this.mEnableLimitDecCache);
                gVar.start(this.mSps, this.mPps, this.mNeedSortFrame, this.mH265);
                notifyDecoderStartEvent(true, this.mH265);
                this.mVideoDecoder = gVar;
            }
            b bVar = this.mVideoDecoder;
            if (bVar != null) {
                bVar.decode(tXSNALPacket);
            }
        }
    }

    private boolean hasSurface() {
        return this.mSurface != null;
    }

    private native long nativeCreateContext(boolean z2, Object obj);

    private native void nativeDecCache(long j2);

    private native void nativeDecodeFrame(long j2, byte[] bArr, int i2, long j3, long j4, int i3, int i4);

    private native void nativeDestroyContext(long j2);

    private native void nativeEnableDecodeChange(long j2, boolean z2);

    private native void nativeEnableRestartDecoder(long j2, boolean z2);

    private native void nativeNotifyHWDecoderError(long j2);

    private native void nativeNotifyPts(long j2, long j3, int i2, int i3);

    private native void nativeReStart(long j2, boolean z2);

    private native void nativeSetID(long j2, String str);

    private native void nativeSetStreamType(long j2, int i2);

    private void notifyDecoderStartEvent(boolean z2, boolean z3) {
        TXCEventRecorderProxy.a(this.mUserId, 4005, z2 ? 1L : 0L, -1L, "", this.mStreamType);
        reportDecoderEvent(z2, z3);
        TXCKeyPointReportProxy.a(this.mUserId, 40026, z2 ? 1L : 2L, this.mStreamType);
    }

    private void onDecodeDone(TXSVideoFrame tXSVideoFrame, int i2, int i3, long j2, long j3, int i4, int i5) {
        if (mDecodeFirstFrameTS == 0) {
            mDecodeFirstFrameTS = TXCTimeUtil.getTimeTick();
            TXCLog.i(TAG, "[FirstFramePath][Video][Decoder] TXCVideoDecoder: decode first frame success. instance:" + hashCode() + " isHWAcc:false isH265:" + this.mH265 + " userId:" + this.mUserId + " type:" + this.mStreamType);
            TXCStatus.a(this.mUserId, 5005, this.mStreamType, Long.valueOf(mDecodeFirstFrameTS));
            TXCStatus.a(this.mUserId, 5004, this.mStreamType, Integer.valueOf(this.mH265 ? 2 : 0));
        }
        h hVar = this.mDecoderListener;
        if (hVar != null) {
            tXSVideoFrame.width = i2;
            tXSVideoFrame.height = i3;
            tXSVideoFrame.rotation = i4;
            tXSVideoFrame.pts = j2;
            tXSVideoFrame.frameType = i5;
            hVar.onDecodeFrame(tXSVideoFrame, i2, i3, j2, j3, i4);
            if (this.mVideoWidth == i2 && this.mVideoHeight == i3) {
                return;
            }
            this.mVideoWidth = i2;
            this.mVideoHeight = i3;
            hVar.onVideoSizeChange(i2, i3);
        }
    }

    private void onStartDecoder(boolean z2, boolean z3) {
        this.mH265 = z3;
        this.mHWDec = z2;
        notifyDecoderStartEvent(z2, z3);
    }

    private void reportDecoderEvent(boolean z2, boolean z3) {
        Bundle bundle = new Bundle();
        bundle.putInt("EVT_ID", 2008);
        bundle.putLong("EVT_TIME", TXCTimeUtil.getTimeTick());
        bundle.putLong(TXLiveConstants.EVT_UTC_TIME, TXCTimeUtil.getUtcTimeTick());
        StringBuilder sb = new StringBuilder("Enables ");
        if (z3) {
            sb.append("H265 ");
        } else {
            sb.append("H264 ");
        }
        if (z2) {
            sb.append("hardware ");
        } else {
            sb.append("software ");
        }
        sb.append("decoding");
        bundle.putCharSequence(TXLiveConstants.EVT_DESCRIPTION, sb.toString());
        bundle.putInt("EVT_PARAM1", z2 ? 1 : 2);
        com.tencent.liteav.basic.util.h.a(this.mNotifyListener, this.mUserId, 2008, bundle);
        TXCLog.i(TAG, "start video decoder:" + sb.toString());
    }

    private int startDecodeThread() {
        synchronized (this) {
            if (this.mDecoderHandler != null) {
                TXCLog.e(TAG, "play:decode: start decoder error when decoder is started");
                return -1;
            }
            this.mDecoderCacheNum = 0;
            this.mRestarting = false;
            HandlerThread handlerThread = new HandlerThread("VDecoder");
            handlerThread.start();
            if (this.mHWDec) {
                handlerThread.setName("VideoWDec" + handlerThread.getId());
            } else {
                handlerThread.setName("VideoSWDec" + handlerThread.getId());
            }
            a aVar = new a(handlerThread.getLooper());
            aVar.a(this.mH265, this.mHWDec, this.mSurface, this.mSps, this.mPps, this, this);
            TXCLog.w(TAG, "play:decode: start decode thread");
            Message messageObtain = Message.obtain();
            messageObtain.what = 100;
            messageObtain.obj = Boolean.valueOf(this.mNeedSortFrame);
            aVar.sendMessage(messageObtain);
            this.mDecoderHandler = aVar;
            return 0;
        }
    }

    private void stopDecodeThread() {
        synchronized (this) {
            a aVar = this.mDecoderHandler;
            if (aVar != null) {
                aVar.sendEmptyMessage(102);
            }
            this.mDecoderHandler = null;
        }
    }

    private synchronized void stopHWDecoder() {
        b bVar = this.mVideoDecoder;
        if (bVar != null) {
            bVar.stop();
            this.mVideoDecoder.setListener(null);
            this.mVideoDecoder.setNotifyListener(null);
            this.mVideoDecoder = null;
        }
    }

    public long GetDecodeFirstFrameTS() {
        return mDecodeFirstFrameTS;
    }

    public void config(JSONArray jSONArray) {
        this.mDecFormat = jSONArray;
    }

    public void enableChange(boolean z2) {
        this.mEnableDecoderChange = z2;
        synchronized (this) {
            nativeEnableDecodeChange(this.mNativeContext, this.mEnableDecoderChange);
        }
    }

    public void enableHWDec(boolean z2) {
        this.mHWDec = z2;
    }

    public void enableLimitDecCache(boolean z2) {
        this.mEnableLimitDecCache = z2;
        b bVar = this.mVideoDecoder;
        if (bVar != null) {
            bVar.enableLimitDecCache(z2);
        }
    }

    public void enableRestart(boolean z2) {
        this.mEnableRestartDecoder = z2;
    }

    public int getDecoderCacheNum() {
        return this.mDecoderCacheNum + this.mNALList.size();
    }

    public boolean isH265() {
        return this.mH265;
    }

    public boolean isHardwareDecode() {
        return this.mVideoDecoder != null;
    }

    @Override // com.tencent.liteav.videodecoder.h
    public void onDecodeFailed(int i2) {
        TXCStatus.a(this.mUserId, 5006, this.mStreamType, Integer.valueOf(i2));
        h hVar = this.mDecoderListener;
        if (hVar != null) {
            hVar.onDecodeFailed(i2);
        }
        synchronized (this) {
            nativeDecCache(this.mNativeContext);
        }
    }

    @Override // com.tencent.liteav.videodecoder.h
    public void onDecodeFrame(TXSVideoFrame tXSVideoFrame, int i2, int i3, long j2, long j3, int i4) {
        if (mDecodeFirstFrameTS == 0) {
            mDecodeFirstFrameTS = TXCTimeUtil.getTimeTick();
            TXCLog.i(TAG, "[FirstFramePath][Video][Decoder] TXCVideoDecoder: decode first frame success. instance:" + hashCode() + " isHWAcc:true isH265:" + this.mH265 + " userId:" + this.mUserId + " type:" + this.mStreamType);
            TXCStatus.a(this.mUserId, 5005, this.mStreamType, Long.valueOf(mDecodeFirstFrameTS));
            TXCStatus.a(this.mUserId, 5004, this.mStreamType, Integer.valueOf(this.mH265 ? 3 : 1));
        }
        h hVar = this.mDecoderListener;
        if (hVar != null) {
            hVar.onDecodeFrame(tXSVideoFrame, i2, i3, j2, j3, i4);
        }
        int i5 = this.mDecoderCacheNum;
        if (i5 > 0) {
            this.mDecoderCacheNum = i5 - 1;
        }
        if (tXSVideoFrame == null) {
            synchronized (this) {
                nativeNotifyPts(this.mNativeContext, j2, i2, i3);
            }
        }
        int iGetDecodeCost = this.mVideoDecoder.GetDecodeCost();
        if (this.mHWDec) {
            TXCStatus.a(this.mUserId, R2.dimen.mtrl_calendar_action_height, this.mStreamType, Integer.valueOf(iGetDecodeCost));
        } else {
            TXCStatus.a(this.mUserId, 8003, this.mStreamType, Integer.valueOf(iGetDecodeCost));
        }
    }

    @Override // com.tencent.liteav.videodecoder.h
    public void onDecoderChange(String str, boolean z2) {
        this.mH265 = z2;
        this.mHWDec = true;
        TXCLog.i(TAG, "onDecoderChange " + str + " , isH265 = " + z2);
        reportDecoderEvent(this.mHWDec, z2);
    }

    @Override // com.tencent.liteav.basic.b.b
    public void onNotifyEvent(int i2, Bundle bundle) {
        if (i2 == 2106 || i2 == -2304) {
            nativeNotifyHWDecoderError(this.mNativeContext);
        }
        com.tencent.liteav.basic.util.h.a(this.mNotifyListener, this.mUserId, i2, bundle);
    }

    @Override // com.tencent.liteav.videodecoder.h
    public void onVideoSizeChange(int i2, int i3) {
        h hVar = this.mDecoderListener;
        if (hVar != null) {
            if (this.mVideoWidth == i2 && this.mVideoHeight == i3) {
                return;
            }
            this.mVideoWidth = i2;
            this.mVideoHeight = i3;
            hVar.onVideoSizeChange(i2, i3);
        }
    }

    public void pushNAL(TXSNALPacket tXSNALPacket) {
        decNALByNewWay(tXSNALPacket);
    }

    public void restart(boolean z2) {
        synchronized (this) {
            this.mHWDec = z2;
            nativeReStart(this.mNativeContext, z2);
        }
    }

    public void setListener(h hVar) {
        this.mDecoderListener = hVar;
    }

    public void setNotifyListener(com.tencent.liteav.basic.b.b bVar) {
        this.mNotifyListener = new WeakReference<>(bVar);
    }

    public void setStreamType(int i2) {
        this.mStreamType = i2;
        synchronized (this) {
            nativeSetStreamType(this.mNativeContext, this.mStreamType);
        }
    }

    public void setUserId(String str) {
        this.mUserId = str;
        synchronized (this) {
            nativeSetID(this.mNativeContext, this.mUserId);
        }
    }

    public int setup(SurfaceTexture surfaceTexture, ByteBuffer byteBuffer, ByteBuffer byteBuffer2, boolean z2) {
        synchronized (this) {
            Surface surface = this.mSurface;
            if (surface != null) {
                surface.release();
                this.mSurface = null;
            }
        }
        return setup(new Surface(surfaceTexture), byteBuffer, byteBuffer2, z2);
    }

    public synchronized int start() {
        if (this.mHWDec && this.mSurface == null) {
            TXCLog.i(TAG, "play:decode: start decoder error when not setup surface, id " + this.mUserId + StrPool.UNDERLINE + this.mStreamType);
            return -1;
        }
        if (this.mNativeContext != 0) {
            TXCLog.w(TAG, "play:decode: start decoder error when decoder is started, id " + this.mUserId + StrPool.UNDERLINE + this.mStreamType);
            return -1;
        }
        TXCLog.i(TAG, "[FirstFramePath][Video][Decoder] TXCVideoDecoder: start decode. instance:" + this + " userId:" + this.mUserId + " type:" + this.mStreamType);
        long jNativeCreateContext = nativeCreateContext(this.mHWDec, com.tencent.liteav.basic.a.b());
        this.mNativeContext = jNativeCreateContext;
        nativeSetID(jNativeCreateContext, this.mUserId);
        nativeSetStreamType(this.mNativeContext, this.mStreamType);
        nativeEnableDecodeChange(this.mNativeContext, this.mEnableDecoderChange);
        nativeEnableRestartDecoder(this.mNativeContext, this.mEnableRestartDecoder);
        return 0;
    }

    public synchronized void stop() {
        if (this.mNativeContext == 0) {
            TXCLog.w(TAG, "play:decode: stop decoder ignore when decoder is stopped, id " + this.mUserId + StrPool.UNDERLINE + this.mStreamType);
            return;
        }
        TXCLog.w(TAG, "play:decode: stop decoder java id " + this.mUserId + StrPool.UNDERLINE + this.mStreamType + " " + hashCode());
        nativeDestroyContext(this.mNativeContext);
        this.mNativeContext = 0L;
        this.mNALList.clear();
        this.mRecvFirstFrame = false;
        this.mDecoderCacheNum = 0;
        mDecodeFirstFrameTS = 0L;
        synchronized (this) {
            b bVar = this.mVideoDecoder;
            if (bVar != null) {
                bVar.stop();
                this.mVideoDecoder.setListener(null);
                this.mVideoDecoder.setNotifyListener(null);
                this.mVideoDecoder = null;
            }
            Surface surface = this.mSurface;
            if (surface != null) {
                surface.release();
                this.mSurface = null;
            }
        }
    }

    public static class a extends Handler {

        /* renamed from: a, reason: collision with root package name */
        b f20126a;

        /* renamed from: b, reason: collision with root package name */
        h f20127b;

        /* renamed from: c, reason: collision with root package name */
        WeakReference<com.tencent.liteav.basic.b.b> f20128c;

        /* renamed from: d, reason: collision with root package name */
        boolean f20129d;

        /* renamed from: e, reason: collision with root package name */
        boolean f20130e;

        /* renamed from: f, reason: collision with root package name */
        Surface f20131f;

        /* renamed from: g, reason: collision with root package name */
        private ByteBuffer f20132g;

        /* renamed from: h, reason: collision with root package name */
        private ByteBuffer f20133h;

        public a(Looper looper) {
            super(looper);
        }

        public void a(boolean z2, boolean z3, Surface surface, ByteBuffer byteBuffer, ByteBuffer byteBuffer2, h hVar, com.tencent.liteav.basic.b.b bVar) {
            this.f20130e = z2;
            this.f20129d = z3;
            this.f20131f = surface;
            this.f20132g = byteBuffer;
            this.f20133h = byteBuffer2;
            this.f20127b = hVar;
            this.f20128c = new WeakReference<>(bVar);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            switch (message.what) {
                case 100:
                    a(((Boolean) message.obj).booleanValue());
                    break;
                case 101:
                    try {
                        Bundle data = message.getData();
                        a(data.getByteArray("nal"), data.getLong("pts"), data.getLong("dts"), data.getInt("codecId"));
                        break;
                    } catch (Exception e2) {
                        TXCLog.e(TXCVideoDecoder.TAG, "decode frame failed." + e2.getMessage());
                        return;
                    }
                case 102:
                    a();
                    break;
                case 103:
                    a(message.arg1 == 1, message.arg2 == 1);
                    break;
            }
        }

        private void a(byte[] bArr, long j2, long j3, int i2) {
            TXSNALPacket tXSNALPacket = new TXSNALPacket();
            tXSNALPacket.nalData = bArr;
            tXSNALPacket.pts = j2;
            tXSNALPacket.dts = j3;
            tXSNALPacket.codecId = i2;
            b bVar = this.f20126a;
            if (bVar != null) {
                bVar.decode(tXSNALPacket);
            }
        }

        private void a() {
            b bVar = this.f20126a;
            if (bVar != null) {
                bVar.stop();
                this.f20126a.setListener(null);
                this.f20126a.setNotifyListener(null);
                this.f20126a = null;
            }
            Looper.myLooper().quit();
            TXCLog.w(TXCVideoDecoder.TAG, "play:decode: stop decode hwdec: " + this.f20129d);
        }

        private void a(boolean z2, boolean z3) {
            this.f20129d = z2;
            TXCLog.w(TXCVideoDecoder.TAG, "play:decode: restart decode hwdec: " + this.f20129d);
            b bVar = this.f20126a;
            if (bVar != null) {
                bVar.stop();
                this.f20126a.setListener(null);
                this.f20126a.setNotifyListener(null);
                this.f20126a = null;
            }
            a(z3);
        }

        private void a(boolean z2) {
            if (this.f20126a != null) {
                TXCLog.i(TXCVideoDecoder.TAG, "play:decode: start decode ignore hwdec: " + this.f20129d);
                return;
            }
            if (this.f20129d) {
                this.f20126a = new f();
            } else {
                this.f20126a = new TXCVideoFfmpegDecoder();
            }
            this.f20126a.setListener(this.f20127b);
            this.f20126a.setNotifyListener(this.f20128c);
            this.f20126a.config(this.f20131f);
            this.f20126a.start(this.f20132g, this.f20133h, z2, this.f20130e);
            TXCLog.w(TXCVideoDecoder.TAG, "play:decode: start decode hwdec: " + this.f20129d + ", h265: " + this.f20130e);
        }
    }

    public int setup(Surface surface, ByteBuffer byteBuffer, ByteBuffer byteBuffer2, boolean z2) {
        synchronized (this) {
            this.mSurface = surface;
        }
        this.mSps = byteBuffer;
        this.mPps = byteBuffer2;
        this.mNeedSortFrame = z2;
        return 0;
    }
}
