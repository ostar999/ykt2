package com.easefun.polyv.mediasdk.player;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Rect;
import android.media.MediaCodecInfo;
import android.media.MediaCodecList;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.ParcelFileDescriptor;
import android.os.PowerManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import com.arialyy.aria.core.inf.IOptionConstant;
import com.easefun.polyv.mediasdk.player.IjkMediaMeta;
import com.easefun.polyv.mediasdk.player.annotations.AccessedByNative;
import com.easefun.polyv.mediasdk.player.annotations.CalledByNative;
import com.easefun.polyv.mediasdk.player.misc.IAndroidIO;
import com.easefun.polyv.mediasdk.player.misc.IMediaDataSource;
import com.easefun.polyv.mediasdk.player.misc.IjkTrackInfo;
import com.easefun.polyv.mediasdk.player.pragma.DebugLog;
import java.io.FileDescriptor;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

/* loaded from: classes3.dex */
public final class IjkMediaPlayer extends AbstractMediaPlayer {
    public static final int FFP_PROPV_DECODER_AVCODEC = 1;
    public static final int FFP_PROPV_DECODER_MEDIACODEC = 2;
    public static final int FFP_PROPV_DECODER_UNKNOWN = 0;
    public static final int FFP_PROPV_DECODER_VIDEOTOOLBOX = 3;
    public static final int FFP_PROP_FLOAT_DROP_FRAME_RATE = 10007;
    public static final int FFP_PROP_FLOAT_PLAYBACK_RATE = 10003;
    public static final int FFP_PROP_INT64_ASYNC_STATISTIC_BUF_BACKWARDS = 20201;
    public static final int FFP_PROP_INT64_ASYNC_STATISTIC_BUF_CAPACITY = 20203;
    public static final int FFP_PROP_INT64_ASYNC_STATISTIC_BUF_FORWARDS = 20202;
    public static final int FFP_PROP_INT64_AUDIO_CACHED_BYTES = 20008;
    public static final int FFP_PROP_INT64_AUDIO_CACHED_DURATION = 20006;
    public static final int FFP_PROP_INT64_AUDIO_CACHED_PACKETS = 20010;
    public static final int FFP_PROP_INT64_AUDIO_DECODER = 20004;
    public static final int FFP_PROP_INT64_BIT_RATE = 20100;
    public static final int FFP_PROP_INT64_CACHE_STATISTIC_COUNT_BYTES = 20208;
    public static final int FFP_PROP_INT64_CACHE_STATISTIC_FILE_FORWARDS = 20206;
    public static final int FFP_PROP_INT64_CACHE_STATISTIC_FILE_POS = 20207;
    public static final int FFP_PROP_INT64_CACHE_STATISTIC_PHYSICAL_POS = 20205;
    public static final int FFP_PROP_INT64_IMMEDIATE_RECONNECT = 20211;
    public static final int FFP_PROP_INT64_LATEST_SEEK_LOAD_DURATION = 20300;
    public static final int FFP_PROP_INT64_LOGICAL_FILE_SIZE = 20209;
    public static final int FFP_PROP_INT64_SELECTED_AUDIO_STREAM = 20002;
    public static final int FFP_PROP_INT64_SELECTED_TIMEDTEXT_STREAM = 20011;
    public static final int FFP_PROP_INT64_SELECTED_VIDEO_STREAM = 20001;
    public static final int FFP_PROP_INT64_SHARE_CACHE_DATA = 20210;
    public static final int FFP_PROP_INT64_TCP_SPEED = 20200;
    public static final int FFP_PROP_INT64_TRAFFIC_STATISTIC_BYTE_COUNT = 20204;
    public static final int FFP_PROP_INT64_VIDEO_CACHED_BYTES = 20007;
    public static final int FFP_PROP_INT64_VIDEO_CACHED_DURATION = 20005;
    public static final int FFP_PROP_INT64_VIDEO_CACHED_PACKETS = 20009;
    public static final int FFP_PROP_INT64_VIDEO_DECODER = 20003;
    public static final int IJK_LOG_DEBUG = 3;
    public static final int IJK_LOG_DEFAULT = 1;
    public static final int IJK_LOG_ERROR = 6;
    public static final int IJK_LOG_FATAL = 7;
    public static final int IJK_LOG_INFO = 4;
    public static final int IJK_LOG_SILENT = 8;
    public static final int IJK_LOG_UNKNOWN = 0;
    public static final int IJK_LOG_VERBOSE = 2;
    public static final int IJK_LOG_WARN = 5;
    private static final int MEDIA_BUFFERING_UPDATE = 3;
    private static final int MEDIA_ERROR = 100;
    private static final int MEDIA_INFO = 200;
    private static final int MEDIA_NOP = 0;
    private static final int MEDIA_PLAYBACK_COMPLETE = 2;
    private static final int MEDIA_PREPARED = 1;
    private static final int MEDIA_SEEK_COMPLETE = 4;
    private static final int MEDIA_SEI_DATA = 201;
    protected static final int MEDIA_SET_VIDEO_SAR = 10001;
    private static final int MEDIA_SET_VIDEO_SIZE = 5;
    private static final int MEDIA_TIMED_TEXT = 99;
    public static final int OPT_CATEGORY_CODEC = 2;
    public static final int OPT_CATEGORY_FORMAT = 1;
    public static final int OPT_CATEGORY_PLAYER = 4;
    public static final int OPT_CATEGORY_SWS = 3;
    public static final int PROP_FLOAT_VIDEO_DECODE_FRAMES_PER_SECOND = 10001;
    public static final int PROP_FLOAT_VIDEO_OUTPUT_FRAMES_PER_SECOND = 10002;
    public static final int SDL_FCC_RV16 = 909203026;
    public static final int SDL_FCC_RV32 = 842225234;
    public static final int SDL_FCC_YV12 = 842094169;
    private static final String TAG = "com.easefun.polyv.mediasdk.player.IjkMediaPlayer";
    private String mDataSource;
    private EventHandler mEventHandler;

    @AccessedByNative
    private int mListenerContext;

    @AccessedByNative
    private long mNativeAndroidIO;

    @AccessedByNative
    private long mNativeMediaDataSource;

    @AccessedByNative
    private long mNativeMediaPlayer;

    @AccessedByNative
    private int mNativeSurfaceTexture;
    private OnControlMessageListener mOnControlMessageListener;
    private OnMediaCodecSelectListener mOnMediaCodecSelectListener;
    private OnNativeInvokeListener mOnNativeInvokeListener;
    private boolean mScreenOnWhilePlaying;
    private boolean mStayAwake;
    private SurfaceHolder mSurfaceHolder;
    private int mVideoHeight;
    private int mVideoSarDen;
    private int mVideoSarNum;
    private int mVideoWidth;
    private PowerManager.WakeLock mWakeLock;
    private static final IjkLibLoader sLocalLibLoader = new IjkLibLoader() { // from class: com.easefun.polyv.mediasdk.player.IjkMediaPlayer.1
        @Override // com.easefun.polyv.mediasdk.player.IjkLibLoader
        public void loadLibrary(String str) throws SecurityException, UnsatisfiedLinkError {
            System.loadLibrary(str);
        }
    };
    private static volatile boolean mIsLibLoaded = false;
    private static volatile boolean mIsNativeInitialized = false;

    public static class DefaultMediaCodecSelector implements OnMediaCodecSelectListener {
        public static final DefaultMediaCodecSelector sInstance = new DefaultMediaCodecSelector();

        @Override // com.easefun.polyv.mediasdk.player.IjkMediaPlayer.OnMediaCodecSelectListener
        @TargetApi(16)
        public String onMediaCodecSelect(IMediaPlayer iMediaPlayer, String str, int i2, int i3) {
            String[] supportedTypes;
            IjkMediaCodecInfo ijkMediaCodecInfo;
            if (TextUtils.isEmpty(str)) {
                return null;
            }
            Log.i(IjkMediaPlayer.TAG, String.format(Locale.US, "onSelectCodec: mime=%s, profile=%d, level=%d", str, Integer.valueOf(i2), Integer.valueOf(i3)));
            ArrayList arrayList = new ArrayList();
            int codecCount = MediaCodecList.getCodecCount();
            for (int i4 = 0; i4 < codecCount; i4++) {
                MediaCodecInfo codecInfoAt = MediaCodecList.getCodecInfoAt(i4);
                Log.d(IjkMediaPlayer.TAG, String.format(Locale.US, "  found codec: %s", codecInfoAt.getName()));
                if (!codecInfoAt.isEncoder() && (supportedTypes = codecInfoAt.getSupportedTypes()) != null) {
                    for (String str2 : supportedTypes) {
                        if (!TextUtils.isEmpty(str2)) {
                            String str3 = IjkMediaPlayer.TAG;
                            Locale locale = Locale.US;
                            Log.d(str3, String.format(locale, "    mime: %s", str2));
                            if (str2.equalsIgnoreCase(str) && (ijkMediaCodecInfo = IjkMediaCodecInfo.setupCandidate(codecInfoAt, str)) != null) {
                                arrayList.add(ijkMediaCodecInfo);
                                Log.i(IjkMediaPlayer.TAG, String.format(locale, "candidate codec: %s rank=%d", codecInfoAt.getName(), Integer.valueOf(ijkMediaCodecInfo.mRank)));
                                ijkMediaCodecInfo.dumpProfileLevels(str);
                            }
                        }
                    }
                }
            }
            if (arrayList.isEmpty()) {
                return null;
            }
            IjkMediaCodecInfo ijkMediaCodecInfo2 = (IjkMediaCodecInfo) arrayList.get(0);
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                IjkMediaCodecInfo ijkMediaCodecInfo3 = (IjkMediaCodecInfo) it.next();
                if (ijkMediaCodecInfo3.mRank > ijkMediaCodecInfo2.mRank) {
                    ijkMediaCodecInfo2 = ijkMediaCodecInfo3;
                }
            }
            if (ijkMediaCodecInfo2.mRank < 600) {
                Log.w(IjkMediaPlayer.TAG, String.format(Locale.US, "unaccetable codec: %s", ijkMediaCodecInfo2.mCodecInfo.getName()));
                return null;
            }
            Log.i(IjkMediaPlayer.TAG, String.format(Locale.US, "selected codec: %s rank=%d", ijkMediaCodecInfo2.mCodecInfo.getName(), Integer.valueOf(ijkMediaCodecInfo2.mRank)));
            return ijkMediaCodecInfo2.mCodecInfo.getName();
        }
    }

    public static class EventHandler extends Handler {
        private final WeakReference<IjkMediaPlayer> mWeakPlayer;

        public EventHandler(IjkMediaPlayer ijkMediaPlayer, Looper looper) {
            super(looper);
            this.mWeakPlayer = new WeakReference<>(ijkMediaPlayer);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            IjkMediaPlayer ijkMediaPlayer = this.mWeakPlayer.get();
            if (ijkMediaPlayer != null) {
                if (ijkMediaPlayer.mNativeMediaPlayer != 0) {
                    int i2 = message.what;
                    if (i2 == 99) {
                        if (message.obj == null) {
                            ijkMediaPlayer.notifyOnTimedText(null);
                            return;
                        } else {
                            ijkMediaPlayer.notifyOnTimedText(new IjkTimedText(new Rect(0, 0, 1, 1), (String) message.obj));
                            return;
                        }
                    }
                    if (i2 == 100) {
                        DebugLog.e(IjkMediaPlayer.TAG, "Error (" + message.arg1 + "," + message.arg2 + ")");
                        if (!ijkMediaPlayer.notifyOnError(message.arg1, message.arg2)) {
                            ijkMediaPlayer.notifyOnCompletion();
                        }
                        ijkMediaPlayer.stayAwake(false);
                        return;
                    }
                    if (i2 == 200) {
                        if (message.arg1 == 3) {
                            DebugLog.i(IjkMediaPlayer.TAG, "Info: MEDIA_INFO_VIDEO_RENDERING_START\n");
                        }
                        ijkMediaPlayer.notifyOnInfo(message.arg1, message.arg2);
                        return;
                    }
                    if (i2 == 201) {
                        ijkMediaPlayer.notifyOnSEIRefresh(message.arg1, (byte[]) message.obj);
                        return;
                    }
                    if (i2 == 10001) {
                        ijkMediaPlayer.mVideoSarNum = message.arg1;
                        ijkMediaPlayer.mVideoSarDen = message.arg2;
                        ijkMediaPlayer.notifyOnVideoSizeChanged(ijkMediaPlayer.mVideoWidth, ijkMediaPlayer.mVideoHeight, ijkMediaPlayer.mVideoSarNum, ijkMediaPlayer.mVideoSarDen);
                        return;
                    }
                    if (i2 != 0) {
                        if (i2 == 1) {
                            ijkMediaPlayer.notifyOnPrepared();
                            return;
                        }
                        if (i2 == 2) {
                            ijkMediaPlayer.stayAwake(false);
                            ijkMediaPlayer.notifyOnCompletion();
                            return;
                        }
                        if (i2 == 3) {
                            long j2 = message.arg1;
                            if (j2 < 0) {
                                j2 = 0;
                            }
                            long duration = ijkMediaPlayer.getDuration();
                            long j3 = duration > 0 ? (j2 * 100) / duration : 0L;
                            ijkMediaPlayer.notifyOnBufferingUpdate((int) (j3 < 100 ? j3 : 100L));
                            return;
                        }
                        if (i2 == 4) {
                            ijkMediaPlayer.notifyOnSeekComplete();
                            return;
                        }
                        if (i2 == 5) {
                            ijkMediaPlayer.mVideoWidth = message.arg1;
                            ijkMediaPlayer.mVideoHeight = message.arg2;
                            ijkMediaPlayer.notifyOnVideoSizeChanged(ijkMediaPlayer.mVideoWidth, ijkMediaPlayer.mVideoHeight, ijkMediaPlayer.mVideoSarNum, ijkMediaPlayer.mVideoSarDen);
                            return;
                        } else {
                            DebugLog.e(IjkMediaPlayer.TAG, "Unknown message type " + message.what);
                            return;
                        }
                    }
                    return;
                }
            }
            DebugLog.w(IjkMediaPlayer.TAG, "IjkMediaPlayer went away with unhandled events");
        }
    }

    public interface OnControlMessageListener {
        String onControlResolveSegmentUrl(int i2);
    }

    public interface OnMediaCodecSelectListener {
        String onMediaCodecSelect(IMediaPlayer iMediaPlayer, String str, int i2, int i3);
    }

    public interface OnNativeInvokeListener {
        public static final String ARG_ERROR = "error";
        public static final String ARG_FAMILIY = "family";
        public static final String ARG_FD = "fd";
        public static final String ARG_FILE_SIZE = "file_size";
        public static final String ARG_HTTP_CODE = "http_code";
        public static final String ARG_IP = "ip";
        public static final String ARG_OFFSET = "offset";
        public static final String ARG_PORT = "port";
        public static final String ARG_RETRY_COUNTER = "retry_counter";
        public static final String ARG_SEGMENT_INDEX = "segment_index";
        public static final String ARG_URL = "url";
        public static final int CTRL_DID_TCP_OPEN = 131074;
        public static final int CTRL_WILL_CONCAT_RESOLVE_SEGMENT = 131079;
        public static final int CTRL_WILL_HTTP_OPEN = 131075;
        public static final int CTRL_WILL_LIVE_OPEN = 131077;
        public static final int CTRL_WILL_TCP_OPEN = 131073;
        public static final int EVENT_DID_HTTP_OPEN = 2;
        public static final int EVENT_DID_HTTP_SEEK = 4;
        public static final int EVENT_WILL_HTTP_OPEN = 1;
        public static final int EVENT_WILL_HTTP_SEEK = 3;

        boolean onNativeInvoke(int i2, Bundle bundle);
    }

    public IjkMediaPlayer() {
        this(sLocalLibLoader);
    }

    private native String _getAudioCodecInfo();

    private static native String _getColorFormatName(int i2);

    private native int _getLoopCount();

    private native Bundle _getMediaMeta();

    private native float _getPropertyFloat(int i2, float f2);

    private native long _getPropertyLong(int i2, long j2);

    private native String _getVideoCodecInfo();

    private native void _pause() throws IllegalStateException;

    private native void _release();

    private native void _reset();

    private native void _setAndroidIOCallback(IAndroidIO iAndroidIO) throws IllegalStateException, SecurityException, IllegalArgumentException;

    private native void _setDataM3U8Source(String str, byte[] bArr, int i2);

    private native void _setDataSource(IMediaDataSource iMediaDataSource) throws IllegalStateException, SecurityException, IllegalArgumentException;

    private native void _setDataSource(String str, String[] strArr, String[] strArr2) throws IllegalStateException, IOException, SecurityException, IllegalArgumentException;

    private native void _setDataSourceFd(int i2) throws IllegalStateException, IOException, SecurityException, IllegalArgumentException;

    private native void _setFrameAtTime(String str, long j2, long j3, int i2, int i3) throws IllegalStateException, IllegalArgumentException;

    private native void _setLoopCount(int i2);

    private native void _setOption(int i2, String str, long j2);

    private native void _setOption(int i2, String str, String str2);

    private native void _setPropertyFloat(int i2, float f2);

    private native void _setPropertyLong(int i2, long j2);

    private native void _setStreamSelected(int i2, boolean z2);

    private native void _setVideoSurface(Surface surface);

    private native void _start() throws IllegalStateException;

    private native void _stop() throws IllegalStateException;

    public static String getColorFormatName(int i2) {
        return _getColorFormatName(i2);
    }

    private static void initNativeOnce() {
        synchronized (IjkMediaPlayer.class) {
            if (!mIsNativeInitialized) {
                native_init();
                mIsNativeInitialized = true;
            }
        }
    }

    private void initPlayer(IjkLibLoader ijkLibLoader) {
        loadLibrariesOnce(ijkLibLoader);
        initNativeOnce();
        Looper looperMyLooper = Looper.myLooper();
        if (looperMyLooper != null) {
            this.mEventHandler = new EventHandler(this, looperMyLooper);
        } else {
            Looper mainLooper = Looper.getMainLooper();
            if (mainLooper != null) {
                this.mEventHandler = new EventHandler(this, mainLooper);
            } else {
                this.mEventHandler = null;
            }
        }
        native_setup(new WeakReference(this));
    }

    public static void loadLibrariesOnce(IjkLibLoader ijkLibLoader) {
        synchronized (IjkMediaPlayer.class) {
            if (!mIsLibLoaded) {
                if (ijkLibLoader == null) {
                    ijkLibLoader = sLocalLibLoader;
                }
                ijkLibLoader.loadLibrary("polyvffmpeg");
                ijkLibLoader.loadLibrary("polyvsdl");
                ijkLibLoader.loadLibrary("polyvplayer");
                mIsLibLoaded = true;
            }
        }
    }

    public static native long nativeGetWebrtcNackCount(Object obj);

    public static native long nativeGetWebrtcVideoPacketsReceived(Object obj);

    private native void native_finalize();

    private static native void native_init();

    private native void native_message_loop(Object obj);

    public static native void native_profileBegin(String str);

    public static native void native_profileEnd();

    public static native void native_setLogLevel(int i2);

    private native void native_setup(Object obj);

    @CalledByNative
    private static boolean onNativeInvoke(Object obj, int i2, Bundle bundle) {
        OnControlMessageListener onControlMessageListener;
        DebugLog.ifmt(TAG, "onNativeInvoke %d", Integer.valueOf(i2));
        if (obj == null || !(obj instanceof WeakReference)) {
            throw new IllegalStateException("<null weakThiz>.onNativeInvoke()");
        }
        IjkMediaPlayer ijkMediaPlayer = (IjkMediaPlayer) ((WeakReference) obj).get();
        if (ijkMediaPlayer == null) {
            throw new IllegalStateException("<null weakPlayer>.onNativeInvoke()");
        }
        OnNativeInvokeListener onNativeInvokeListener = ijkMediaPlayer.mOnNativeInvokeListener;
        if (onNativeInvokeListener != null && onNativeInvokeListener.onNativeInvoke(i2, bundle)) {
            return true;
        }
        if (i2 != 131079 || (onControlMessageListener = ijkMediaPlayer.mOnControlMessageListener) == null) {
            return false;
        }
        int i3 = bundle.getInt(OnNativeInvokeListener.ARG_SEGMENT_INDEX, -1);
        if (i3 < 0) {
            throw new InvalidParameterException("onNativeInvoke(invalid segment index)");
        }
        String strOnControlResolveSegmentUrl = onControlMessageListener.onControlResolveSegmentUrl(i3);
        if (strOnControlResolveSegmentUrl == null) {
            throw new RuntimeException(new IOException("onNativeInvoke() = <NULL newUrl>"));
        }
        bundle.putString("url", strOnControlResolveSegmentUrl);
        return true;
    }

    @CalledByNative
    private static String onSelectCodec(Object obj, String str, int i2, int i3) {
        IjkMediaPlayer ijkMediaPlayer;
        if (obj == null || !(obj instanceof WeakReference) || (ijkMediaPlayer = (IjkMediaPlayer) ((WeakReference) obj).get()) == null) {
            return null;
        }
        OnMediaCodecSelectListener onMediaCodecSelectListener = ijkMediaPlayer.mOnMediaCodecSelectListener;
        if (onMediaCodecSelectListener == null) {
            onMediaCodecSelectListener = DefaultMediaCodecSelector.sInstance;
        }
        return onMediaCodecSelectListener.onMediaCodecSelect(ijkMediaPlayer, str, i2, i3);
    }

    @CalledByNative
    private static void postEventFromNative(Object obj, int i2, int i3, int i4, Object obj2) throws IllegalStateException {
        IjkMediaPlayer ijkMediaPlayer;
        if (obj == null || (ijkMediaPlayer = (IjkMediaPlayer) ((WeakReference) obj).get()) == null) {
            return;
        }
        if (i2 == 200 && i3 == 2) {
            ijkMediaPlayer.start();
        }
        EventHandler eventHandler = ijkMediaPlayer.mEventHandler;
        if (eventHandler != null) {
            ijkMediaPlayer.mEventHandler.sendMessage(eventHandler.obtainMessage(i2, i3, i4, obj2));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @SuppressLint({"Wakelock"})
    public void stayAwake(boolean z2) {
        PowerManager.WakeLock wakeLock = this.mWakeLock;
        if (wakeLock != null) {
            if (z2 && !wakeLock.isHeld()) {
                this.mWakeLock.acquire();
            } else if (!z2 && this.mWakeLock.isHeld()) {
                this.mWakeLock.release();
            }
        }
        this.mStayAwake = z2;
        updateSurfaceScreenOn();
    }

    private void updateSurfaceScreenOn() {
        SurfaceHolder surfaceHolder = this.mSurfaceHolder;
        if (surfaceHolder != null) {
            surfaceHolder.setKeepScreenOn(this.mScreenOnWhilePlaying && this.mStayAwake);
        }
    }

    public native void _prepareAsync() throws IllegalStateException;

    public void deselectTrack(int i2) {
        _setStreamSelected(i2, false);
    }

    public void finalize() throws Throwable {
        super.finalize();
        native_finalize();
    }

    public long getAsyncStatisticBufBackwards() {
        return _getPropertyLong(20201, 0L);
    }

    public long getAsyncStatisticBufCapacity() {
        return _getPropertyLong(20203, 0L);
    }

    public long getAsyncStatisticBufForwards() {
        return _getPropertyLong(20202, 0L);
    }

    public long getAudioCachedBytes() {
        return _getPropertyLong(20008, 0L);
    }

    public long getAudioCachedDuration() {
        return _getPropertyLong(20006, 0L);
    }

    public long getAudioCachedPackets() {
        return _getPropertyLong(20010, 0L);
    }

    @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer
    public native int getAudioSessionId();

    public long getBitRate() {
        return _getPropertyLong(20100, 0L);
    }

    public long getCacheStatisticCountBytes() {
        return _getPropertyLong(20208, 0L);
    }

    public long getCacheStatisticFileForwards() {
        return _getPropertyLong(20206, 0L);
    }

    public long getCacheStatisticFilePos() {
        return _getPropertyLong(20207, 0L);
    }

    public long getCacheStatisticPhysicalPos() {
        return _getPropertyLong(20205, 0L);
    }

    @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer
    public native long getCurrentPosition();

    @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer
    public String getDataSource() {
        return this.mDataSource;
    }

    public float getDropFrameRate() {
        return _getPropertyFloat(10007, 0.0f);
    }

    @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer
    public native long getDuration();

    public long getFileSize() {
        return _getPropertyLong(20209, 0L);
    }

    @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer
    public MediaInfo getMediaInfo() {
        MediaInfo mediaInfo = new MediaInfo();
        mediaInfo.mMediaPlayerName = "ijkplayer";
        String str_getVideoCodecInfo = _getVideoCodecInfo();
        if (!TextUtils.isEmpty(str_getVideoCodecInfo)) {
            String[] strArrSplit = str_getVideoCodecInfo.split(",");
            if (strArrSplit.length >= 2) {
                mediaInfo.mVideoDecoder = strArrSplit[0];
                mediaInfo.mVideoDecoderImpl = strArrSplit[1];
            } else if (strArrSplit.length >= 1) {
                mediaInfo.mVideoDecoder = strArrSplit[0];
                mediaInfo.mVideoDecoderImpl = "";
            }
        }
        String str_getAudioCodecInfo = _getAudioCodecInfo();
        if (!TextUtils.isEmpty(str_getAudioCodecInfo)) {
            String[] strArrSplit2 = str_getAudioCodecInfo.split(",");
            if (strArrSplit2.length >= 2) {
                mediaInfo.mAudioDecoder = strArrSplit2[0];
                mediaInfo.mAudioDecoderImpl = strArrSplit2[1];
            } else if (strArrSplit2.length >= 1) {
                mediaInfo.mAudioDecoder = strArrSplit2[0];
                mediaInfo.mAudioDecoderImpl = "";
            }
        }
        try {
            mediaInfo.mMeta = IjkMediaMeta.parse(_getMediaMeta());
        } catch (Throwable th) {
            th.printStackTrace();
        }
        return mediaInfo;
    }

    public Bundle getMediaMeta() {
        return _getMediaMeta();
    }

    public long getSeekLoadDuration() {
        return _getPropertyLong(20300, 0L);
    }

    public int getSelectedTrack(int i2) {
        long j_getPropertyLong;
        if (i2 == 1) {
            j_getPropertyLong = _getPropertyLong(20001, -1L);
        } else if (i2 == 2) {
            j_getPropertyLong = _getPropertyLong(20002, -1L);
        } else {
            if (i2 != 3) {
                return -1;
            }
            j_getPropertyLong = _getPropertyLong(20011, -1L);
        }
        return (int) j_getPropertyLong;
    }

    public float getSpeed(float f2) {
        return _getPropertyFloat(10003, 0.0f);
    }

    public long getTcpSpeed() {
        return _getPropertyLong(20200, 0L);
    }

    public long getTrafficStatisticByteCount() {
        return _getPropertyLong(20204, 0L);
    }

    public long getVideoCachedBytes() {
        return _getPropertyLong(20007, 0L);
    }

    public long getVideoCachedDuration() {
        return _getPropertyLong(20005, 0L);
    }

    public long getVideoCachedPackets() {
        return _getPropertyLong(20009, 0L);
    }

    public float getVideoDecodeFramesPerSecond() {
        return _getPropertyFloat(10001, 0.0f);
    }

    public int getVideoDecoder() {
        return (int) _getPropertyLong(20003, 0L);
    }

    @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer
    public int getVideoHeight() {
        return this.mVideoHeight;
    }

    public float getVideoOutputFramesPerSecond() {
        return _getPropertyFloat(10002, 0.0f);
    }

    @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer
    public int getVideoSarDen() {
        return this.mVideoSarDen;
    }

    @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer
    public int getVideoSarNum() {
        return this.mVideoSarNum;
    }

    @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer
    public int getVideoWidth() {
        return this.mVideoWidth;
    }

    public long getWebrtcNackCount() {
        return nativeGetWebrtcNackCount(this);
    }

    public long getWebrtcVideoPacketReceived() {
        return nativeGetWebrtcVideoPacketsReceived(this);
    }

    public void httphookReconnect() {
        _setPropertyLong(20211, 1L);
    }

    @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer
    public boolean isLooping() {
        return _getLoopCount() != 1;
    }

    @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer
    public boolean isPlayable() {
        return true;
    }

    @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer
    public native boolean isPlaying();

    @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer
    public void pause() throws IllegalStateException {
        stayAwake(false);
        _pause();
    }

    @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer
    public void prepareAsync() throws IllegalStateException {
        _prepareAsync();
    }

    @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer
    public void release() {
        stayAwake(false);
        updateSurfaceScreenOn();
        resetListeners();
        _release();
    }

    @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer
    public void reset() {
        stayAwake(false);
        _reset();
        this.mEventHandler.removeCallbacksAndMessages(null);
        this.mVideoWidth = 0;
        this.mVideoHeight = 0;
    }

    @Override // com.easefun.polyv.mediasdk.player.AbstractMediaPlayer
    public void resetListeners() {
        super.resetListeners();
        this.mOnMediaCodecSelectListener = null;
    }

    @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer
    public native void seekTo(long j2) throws IllegalStateException;

    public void selectTrack(int i2) {
        _setStreamSelected(i2, true);
    }

    public void setAndroidIOCallback(IAndroidIO iAndroidIO) throws IllegalStateException, SecurityException, IllegalArgumentException {
        _setAndroidIOCallback(iAndroidIO);
    }

    @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer
    public void setAudioStreamType(int i2) {
    }

    public void setCacheShare(int i2) {
        _setPropertyLong(20210, i2);
    }

    @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer
    public void setDataSource(Context context, Uri uri) throws IllegalStateException, IOException, SecurityException, IllegalArgumentException {
        setDataSource(context, uri, (Map<String, String>) null);
    }

    @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer
    public void setDisplay(SurfaceHolder surfaceHolder) {
        this.mSurfaceHolder = surfaceHolder;
        _setVideoSurface(surfaceHolder != null ? surfaceHolder.getSurface() : null);
        updateSurfaceScreenOn();
    }

    @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer
    public void setKeepInBackground(boolean z2) {
    }

    @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer
    public void setLogEnabled(boolean z2) {
    }

    @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer
    public void setLooping(boolean z2) {
        int i2 = !z2 ? 1 : 0;
        setOption(4, "loop", i2);
        _setLoopCount(i2);
    }

    public void setOnControlMessageListener(OnControlMessageListener onControlMessageListener) {
        this.mOnControlMessageListener = onControlMessageListener;
    }

    public void setOnMediaCodecSelectListener(OnMediaCodecSelectListener onMediaCodecSelectListener) {
        this.mOnMediaCodecSelectListener = onMediaCodecSelectListener;
    }

    public void setOnNativeInvokeListener(OnNativeInvokeListener onNativeInvokeListener) {
        this.mOnNativeInvokeListener = onNativeInvokeListener;
    }

    public void setOption(int i2, String str, String str2) {
        _setOption(i2, str, str2);
    }

    @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer
    public void setScreenOnWhilePlaying(boolean z2) {
        if (this.mScreenOnWhilePlaying != z2) {
            if (z2 && this.mSurfaceHolder == null) {
                DebugLog.w(TAG, "setScreenOnWhilePlaying(true) is ineffective without a SurfaceHolder");
            }
            this.mScreenOnWhilePlaying = z2;
            updateSurfaceScreenOn();
        }
    }

    public void setSpeed(float f2) {
        _setPropertyFloat(10003, f2);
    }

    @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer
    public void setSurface(Surface surface) {
        if (this.mScreenOnWhilePlaying && surface != null) {
            DebugLog.w(TAG, "setScreenOnWhilePlaying(true) is ineffective for Surface");
        }
        this.mSurfaceHolder = null;
        _setVideoSurface(surface);
        updateSurfaceScreenOn();
    }

    @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer
    public native void setVolume(float f2, float f3);

    @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer
    @SuppressLint({"Wakelock"})
    public void setWakeMode(Context context, int i2) {
        boolean z2;
        PowerManager.WakeLock wakeLock = this.mWakeLock;
        if (wakeLock != null) {
            if (wakeLock.isHeld()) {
                this.mWakeLock.release();
                z2 = true;
            } else {
                z2 = false;
            }
            this.mWakeLock = null;
        } else {
            z2 = false;
        }
        PowerManager.WakeLock wakeLockNewWakeLock = ((PowerManager) context.getSystemService("power")).newWakeLock(i2 | 536870912, IjkMediaPlayer.class.getName());
        this.mWakeLock = wakeLockNewWakeLock;
        wakeLockNewWakeLock.setReferenceCounted(false);
        if (z2) {
            this.mWakeLock.acquire();
        }
    }

    @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer
    public void start() throws IllegalStateException {
        stayAwake(true);
        _start();
    }

    @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer
    public void stop() throws IllegalStateException {
        stayAwake(false);
        _stop();
    }

    public IjkMediaPlayer(IjkLibLoader ijkLibLoader) {
        this.mWakeLock = null;
        initPlayer(ijkLibLoader);
    }

    @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer
    public IjkTrackInfo[] getTrackInfo() {
        IjkMediaMeta ijkMediaMeta;
        Bundle mediaMeta = getMediaMeta();
        if (mediaMeta == null || (ijkMediaMeta = IjkMediaMeta.parse(mediaMeta)) == null || ijkMediaMeta.mStreams == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        Iterator<IjkMediaMeta.IjkStreamMeta> it = ijkMediaMeta.mStreams.iterator();
        while (it.hasNext()) {
            IjkMediaMeta.IjkStreamMeta next = it.next();
            IjkTrackInfo ijkTrackInfo = new IjkTrackInfo(next);
            if (next.mType.equalsIgnoreCase("video")) {
                ijkTrackInfo.setTrackType(1);
            } else if (next.mType.equalsIgnoreCase("audio")) {
                ijkTrackInfo.setTrackType(2);
            } else if (next.mType.equalsIgnoreCase("timedtext")) {
                ijkTrackInfo.setTrackType(3);
            }
            arrayList.add(ijkTrackInfo);
        }
        return (IjkTrackInfo[]) arrayList.toArray(new IjkTrackInfo[arrayList.size()]);
    }

    /* JADX WARN: Removed duplicated region for block: B:34:0x0080  */
    @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer
    @android.annotation.TargetApi(14)
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void setDataSource(android.content.Context r8, android.net.Uri r9, java.util.Map<java.lang.String, java.lang.String> r10) throws java.lang.IllegalStateException, java.io.IOException, java.lang.SecurityException, java.lang.IllegalArgumentException {
        /*
            r7 = this;
            java.lang.String r0 = r9.getScheme()
            java.lang.String r1 = "file"
            boolean r1 = r1.equals(r0)
            if (r1 == 0) goto L14
            java.lang.String r8 = r9.getPath()
            r7.setDataSource(r8)
            return
        L14:
            java.lang.String r1 = "content"
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L3b
            java.lang.String r0 = r9.getAuthority()
            java.lang.String r1 = "settings"
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L3b
            int r9 = android.media.RingtoneManager.getDefaultType(r9)
            android.net.Uri r9 = android.media.RingtoneManager.getActualDefaultRingtoneUri(r8, r9)
            if (r9 == 0) goto L33
            goto L3b
        L33:
            java.io.FileNotFoundException r8 = new java.io.FileNotFoundException
            java.lang.String r9 = "Failed to resolve default ringtone"
            r8.<init>(r9)
            throw r8
        L3b:
            r0 = 0
            android.content.ContentResolver r8 = r8.getContentResolver()     // Catch: java.lang.Throwable -> L74 java.io.IOException -> L7b java.lang.SecurityException -> L7e
            java.lang.String r1 = "r"
            android.content.res.AssetFileDescriptor r0 = r8.openAssetFileDescriptor(r9, r1)     // Catch: java.lang.Throwable -> L74 java.io.IOException -> L7b java.lang.SecurityException -> L7e
            if (r0 != 0) goto L4e
            if (r0 == 0) goto L4d
            r0.close()
        L4d:
            return
        L4e:
            long r1 = r0.getDeclaredLength()     // Catch: java.lang.Throwable -> L74 java.io.IOException -> L7b java.lang.SecurityException -> L7e
            r3 = 0
            int r8 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
            if (r8 >= 0) goto L60
            java.io.FileDescriptor r8 = r0.getFileDescriptor()     // Catch: java.lang.Throwable -> L74 java.io.IOException -> L7b java.lang.SecurityException -> L7e
            r7.setDataSource(r8)     // Catch: java.lang.Throwable -> L74 java.io.IOException -> L7b java.lang.SecurityException -> L7e
            goto L70
        L60:
            java.io.FileDescriptor r2 = r0.getFileDescriptor()     // Catch: java.lang.Throwable -> L74 java.io.IOException -> L7b java.lang.SecurityException -> L7e
            long r3 = r0.getStartOffset()     // Catch: java.lang.Throwable -> L74 java.io.IOException -> L7b java.lang.SecurityException -> L7e
            long r5 = r0.getDeclaredLength()     // Catch: java.lang.Throwable -> L74 java.io.IOException -> L7b java.lang.SecurityException -> L7e
            r1 = r7
            r1.setDataSource(r2, r3, r5)     // Catch: java.lang.Throwable -> L74 java.io.IOException -> L7b java.lang.SecurityException -> L7e
        L70:
            r0.close()
            return
        L74:
            r8 = move-exception
            if (r0 == 0) goto L7a
            r0.close()
        L7a:
            throw r8
        L7b:
            if (r0 == 0) goto L83
            goto L80
        L7e:
            if (r0 == 0) goto L83
        L80:
            r0.close()
        L83:
            java.lang.String r8 = com.easefun.polyv.mediasdk.player.IjkMediaPlayer.TAG
            java.lang.String r0 = "Couldn't open file on client side, trying server side"
            android.util.Log.d(r8, r0)
            java.lang.String r8 = r9.toString()
            r7.setDataSource(r8, r10)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.easefun.polyv.mediasdk.player.IjkMediaPlayer.setDataSource(android.content.Context, android.net.Uri, java.util.Map):void");
    }

    public void setOption(int i2, String str, long j2) {
        _setOption(i2, str, j2);
    }

    @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer
    public void setDataSource(String str) throws IllegalStateException, IOException, SecurityException, IllegalArgumentException {
        this.mDataSource = str;
        _setDataSource(str, null, null);
    }

    @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer
    public void setDataSource(String str, byte[] bArr, Map<String, String> map) {
        this.mDataSource = str;
        if (map != null && !map.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, String> entry : map.entrySet()) {
                sb.append(entry.getKey());
                sb.append(":");
                if (!TextUtils.isEmpty(entry.getValue())) {
                    sb.append(entry.getValue());
                }
                sb.append("\r\n");
                setOption(1, IOptionConstant.headers, sb.toString());
                setOption(1, "protocol_whitelist", "async,cache,crypto,file,http,https,ijkhttphook,ijkinject,ijklivehook,ijklongurl,ijksegment,ijktcphook,pipe,rtp,tcp,tls,udp,ijkurlhook,data");
            }
        }
        if (bArr == null) {
            new NullPointerException("m3u8Data==null").printStackTrace();
        } else {
            _setDataM3U8Source(str, bArr, bArr.length);
        }
    }

    public void setDataSource(String str, Map<String, String> map) throws IllegalStateException, IOException, SecurityException, IllegalArgumentException {
        if (map != null && !map.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, String> entry : map.entrySet()) {
                sb.append(entry.getKey());
                sb.append(":");
                if (!TextUtils.isEmpty(entry.getValue())) {
                    sb.append(entry.getValue());
                }
                sb.append("\r\n");
                setOption(1, IOptionConstant.headers, sb.toString());
                setOption(1, "protocol_whitelist", "async,cache,crypto,file,http,https,ijkhttphook,ijkinject,ijklivehook,ijklongurl,ijksegment,ijktcphook,pipe,rtp,tcp,tls,udp,ijkurlhook,data");
            }
        }
        setDataSource(str);
    }

    @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer
    @TargetApi(13)
    public void setDataSource(FileDescriptor fileDescriptor) throws IllegalStateException, IOException, IllegalArgumentException {
        ParcelFileDescriptor parcelFileDescriptorDup = ParcelFileDescriptor.dup(fileDescriptor);
        try {
            _setDataSourceFd(parcelFileDescriptorDup.getFd());
        } finally {
            parcelFileDescriptorDup.close();
        }
    }

    private void setDataSource(FileDescriptor fileDescriptor, long j2, long j3) throws IllegalStateException, IOException, IllegalArgumentException {
        setDataSource(fileDescriptor);
    }

    @Override // com.easefun.polyv.mediasdk.player.AbstractMediaPlayer, com.easefun.polyv.mediasdk.player.IMediaPlayer
    public void setDataSource(IMediaDataSource iMediaDataSource) throws IllegalStateException, SecurityException, IllegalArgumentException {
        _setDataSource(iMediaDataSource);
    }
}
