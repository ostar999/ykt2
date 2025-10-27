package com.opensource.svgaplayer;

import android.media.AudioAttributes;
import android.media.SoundPool;
import com.easefun.polyv.mediasdk.player.IjkMediaPlayer;
import com.google.android.exoplayer2.source.rtsp.SessionDescription;
import com.huawei.hms.push.constant.RemoteMessageConst;
import com.opensource.svgaplayer.entities.SVGAAudioEntity;
import com.opensource.svgaplayer.utils.log.LogUtils;
import java.io.FileDescriptor;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.Nullable;

@Metadata(bv = {1, 0, 3}, d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\u0006\bÆ\u0002\u0018\u00002\u00020\u0001:\u0001.B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\u000e\u001a\u00020\u000fH\u0002J\u0018\u0010\u0010\u001a\n \u0005*\u0004\u0018\u00010\u000b0\u000b2\u0006\u0010\u0011\u001a\u00020\bH\u0002J\u0006\u0010\u0012\u001a\u00020\u0013J\u000e\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0011\u001a\u00020\bJ\r\u0010\u0014\u001a\u00020\u000fH\u0000¢\u0006\u0002\b\u0015J9\u0010\u0016\u001a\u00020\b2\b\u0010\u0017\u001a\u0004\u0018\u00010\t2\b\u0010\u0018\u001a\u0004\u0018\u00010\u00192\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001b2\u0006\u0010\u001d\u001a\u00020\bH\u0000¢\u0006\u0002\b\u001eJ\u0015\u0010\u001f\u001a\u00020\u00132\u0006\u0010 \u001a\u00020\bH\u0000¢\u0006\u0002\b!J\u0015\u0010\"\u001a\u00020\b2\u0006\u0010 \u001a\u00020\bH\u0000¢\u0006\u0002\b#J\u0006\u0010$\u001a\u00020\u0013J\u0015\u0010%\u001a\u00020\u00132\u0006\u0010 \u001a\u00020\bH\u0000¢\u0006\u0002\b&J\u001a\u0010'\u001a\u00020\u00132\u0006\u0010\f\u001a\u00020\r2\n\b\u0002\u0010(\u001a\u0004\u0018\u00010)J\u0015\u0010*\u001a\u00020\u00132\u0006\u0010 \u001a\u00020\bH\u0000¢\u0006\u0002\b+J\u0015\u0010,\u001a\u00020\u00132\u0006\u0010 \u001a\u00020\bH\u0000¢\u0006\u0002\b-R\u0016\u0010\u0003\u001a\n \u0005*\u0004\u0018\u00010\u00040\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t0\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006/"}, d2 = {"Lcom/opensource/svgaplayer/SVGASoundManager;", "", "()V", "TAG", "", "kotlin.jvm.PlatformType", "soundCallBackMap", "", "", "Lcom/opensource/svgaplayer/SVGASoundManager$SVGASoundCallBack;", "soundPool", "Landroid/media/SoundPool;", "volume", "", "checkInit", "", "getSoundPool", "maxStreams", "init", "", "isInit", "isInit$com_opensource_svgaplayer", "load", "callBack", IjkMediaPlayer.OnNativeInvokeListener.ARG_FD, "Ljava/io/FileDescriptor;", "offset", "", SessionDescription.ATTR_LENGTH, RemoteMessageConst.Notification.PRIORITY, "load$com_opensource_svgaplayer", "pause", "soundId", "pause$com_opensource_svgaplayer", "play", "play$com_opensource_svgaplayer", "release", "resume", "resume$com_opensource_svgaplayer", "setVolume", "entity", "Lcom/opensource/svgaplayer/SVGAVideoEntity;", "stop", "stop$com_opensource_svgaplayer", "unload", "unload$com_opensource_svgaplayer", "SVGASoundCallBack", BuildConfig.LIBRARY_PACKAGE_NAME}, k = 1, mv = {1, 1, 15})
/* loaded from: classes4.dex */
public final class SVGASoundManager {
    private static SoundPool soundPool;
    public static final SVGASoundManager INSTANCE = new SVGASoundManager();
    private static final String TAG = SVGASoundManager.class.getSimpleName();
    private static final Map<Integer, SVGASoundCallBack> soundCallBackMap = new LinkedHashMap();
    private static float volume = 1.0f;

    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\b`\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&J\u0010\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0005\u001a\u00020\u0006H&¨\u0006\u0007"}, d2 = {"Lcom/opensource/svgaplayer/SVGASoundManager$SVGASoundCallBack;", "", "onComplete", "", "onVolumeChange", "value", "", BuildConfig.LIBRARY_PACKAGE_NAME}, k = 1, mv = {1, 1, 15})
    public interface SVGASoundCallBack {
        void onComplete();

        void onVolumeChange(float value);
    }

    private SVGASoundManager() {
    }

    public static final /* synthetic */ Map access$getSoundCallBackMap$p(SVGASoundManager sVGASoundManager) {
        return soundCallBackMap;
    }

    public static final /* synthetic */ String access$getTAG$p(SVGASoundManager sVGASoundManager) {
        return TAG;
    }

    private final boolean checkInit() {
        boolean zIsInit$com_opensource_svgaplayer = isInit$com_opensource_svgaplayer();
        if (!zIsInit$com_opensource_svgaplayer) {
            LogUtils logUtils = LogUtils.INSTANCE;
            String TAG2 = TAG;
            Intrinsics.checkExpressionValueIsNotNull(TAG2, "TAG");
            logUtils.error(TAG2, "soundPool is null, you need call init() !!!");
        }
        return zIsInit$com_opensource_svgaplayer;
    }

    private final SoundPool getSoundPool(int maxStreams) {
        return new SoundPool.Builder().setAudioAttributes(new AudioAttributes.Builder().setUsage(1).build()).setMaxStreams(maxStreams).build();
    }

    public static /* synthetic */ void setVolume$default(SVGASoundManager sVGASoundManager, float f2, SVGAVideoEntity sVGAVideoEntity, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            sVGAVideoEntity = null;
        }
        sVGASoundManager.setVolume(f2, sVGAVideoEntity);
    }

    public final void init() {
        init(20);
    }

    public final boolean isInit$com_opensource_svgaplayer() {
        return soundPool != null;
    }

    public final int load$com_opensource_svgaplayer(@Nullable SVGASoundCallBack callBack, @Nullable FileDescriptor fd, long offset, long length, int priority) {
        if (!checkInit()) {
            return -1;
        }
        SoundPool soundPool2 = soundPool;
        if (soundPool2 == null) {
            Intrinsics.throwNpe();
        }
        int iLoad = soundPool2.load(fd, offset, length, priority);
        LogUtils logUtils = LogUtils.INSTANCE;
        String TAG2 = TAG;
        Intrinsics.checkExpressionValueIsNotNull(TAG2, "TAG");
        logUtils.debug(TAG2, "load soundId=" + iLoad + " callBack=" + callBack);
        if (callBack != null) {
            Map<Integer, SVGASoundCallBack> map = soundCallBackMap;
            if (!map.containsKey(Integer.valueOf(iLoad))) {
                map.put(Integer.valueOf(iLoad), callBack);
            }
        }
        return iLoad;
    }

    public final void pause$com_opensource_svgaplayer(int soundId) {
        if (checkInit()) {
            LogUtils logUtils = LogUtils.INSTANCE;
            String TAG2 = TAG;
            Intrinsics.checkExpressionValueIsNotNull(TAG2, "TAG");
            logUtils.debug(TAG2, "pause soundId=" + soundId);
            SoundPool soundPool2 = soundPool;
            if (soundPool2 == null) {
                Intrinsics.throwNpe();
            }
            soundPool2.pause(soundId);
        }
    }

    public final int play$com_opensource_svgaplayer(int soundId) {
        if (!checkInit()) {
            return -1;
        }
        LogUtils logUtils = LogUtils.INSTANCE;
        String TAG2 = TAG;
        Intrinsics.checkExpressionValueIsNotNull(TAG2, "TAG");
        logUtils.debug(TAG2, "play soundId=" + soundId);
        SoundPool soundPool2 = soundPool;
        if (soundPool2 == null) {
            Intrinsics.throwNpe();
        }
        float f2 = volume;
        return soundPool2.play(soundId, f2, f2, 1, 0, 1.0f);
    }

    public final void release() {
        LogUtils logUtils = LogUtils.INSTANCE;
        String TAG2 = TAG;
        Intrinsics.checkExpressionValueIsNotNull(TAG2, "TAG");
        logUtils.debug(TAG2, "**************** release ****************");
        Map<Integer, SVGASoundCallBack> map = soundCallBackMap;
        if (!map.isEmpty()) {
            map.clear();
        }
    }

    public final void resume$com_opensource_svgaplayer(int soundId) {
        if (checkInit()) {
            LogUtils logUtils = LogUtils.INSTANCE;
            String TAG2 = TAG;
            Intrinsics.checkExpressionValueIsNotNull(TAG2, "TAG");
            logUtils.debug(TAG2, "stop soundId=" + soundId);
            SoundPool soundPool2 = soundPool;
            if (soundPool2 == null) {
                Intrinsics.throwNpe();
            }
            soundPool2.resume(soundId);
        }
    }

    public final void setVolume(float volume2, @Nullable SVGAVideoEntity entity) {
        Integer playID;
        if (checkInit()) {
            if (volume2 < 0.0f || volume2 > 1.0f) {
                LogUtils logUtils = LogUtils.INSTANCE;
                String TAG2 = TAG;
                Intrinsics.checkExpressionValueIsNotNull(TAG2, "TAG");
                logUtils.error(TAG2, "The volume level is in the range of 0 to 1 ");
                return;
            }
            if (entity == null) {
                volume = volume2;
                Iterator<Map.Entry<Integer, SVGASoundCallBack>> it = soundCallBackMap.entrySet().iterator();
                while (it.hasNext()) {
                    it.next().getValue().onVolumeChange(volume2);
                }
                return;
            }
            SoundPool soundPool2 = soundPool;
            if (soundPool2 != null) {
                Iterator<T> it2 = entity.getAudioList$com_opensource_svgaplayer().iterator();
                while (it2.hasNext() && (playID = ((SVGAAudioEntity) it2.next()).getPlayID()) != null) {
                    soundPool2.setVolume(playID.intValue(), volume2, volume2);
                }
            }
        }
    }

    public final void stop$com_opensource_svgaplayer(int soundId) {
        if (checkInit()) {
            LogUtils logUtils = LogUtils.INSTANCE;
            String TAG2 = TAG;
            Intrinsics.checkExpressionValueIsNotNull(TAG2, "TAG");
            logUtils.debug(TAG2, "stop soundId=" + soundId);
            SoundPool soundPool2 = soundPool;
            if (soundPool2 == null) {
                Intrinsics.throwNpe();
            }
            soundPool2.stop(soundId);
        }
    }

    public final void unload$com_opensource_svgaplayer(int soundId) {
        if (checkInit()) {
            LogUtils logUtils = LogUtils.INSTANCE;
            String TAG2 = TAG;
            Intrinsics.checkExpressionValueIsNotNull(TAG2, "TAG");
            logUtils.debug(TAG2, "unload soundId=" + soundId);
            SoundPool soundPool2 = soundPool;
            if (soundPool2 == null) {
                Intrinsics.throwNpe();
            }
            soundPool2.unload(soundId);
            soundCallBackMap.remove(Integer.valueOf(soundId));
        }
    }

    public final void init(int maxStreams) {
        LogUtils logUtils = LogUtils.INSTANCE;
        String TAG2 = TAG;
        Intrinsics.checkExpressionValueIsNotNull(TAG2, "TAG");
        logUtils.debug(TAG2, "**************** init **************** " + maxStreams);
        if (soundPool != null) {
            return;
        }
        SoundPool soundPool2 = getSoundPool(maxStreams);
        soundPool = soundPool2;
        if (soundPool2 != null) {
            soundPool2.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() { // from class: com.opensource.svgaplayer.SVGASoundManager.init.1
                @Override // android.media.SoundPool.OnLoadCompleteListener
                public final void onLoadComplete(SoundPool soundPool3, int i2, int i3) {
                    SVGASoundCallBack sVGASoundCallBack;
                    LogUtils logUtils2 = LogUtils.INSTANCE;
                    SVGASoundManager sVGASoundManager = SVGASoundManager.INSTANCE;
                    String TAG3 = SVGASoundManager.access$getTAG$p(sVGASoundManager);
                    Intrinsics.checkExpressionValueIsNotNull(TAG3, "TAG");
                    logUtils2.debug(TAG3, "SoundPool onLoadComplete soundId=" + i2 + " status=" + i3);
                    if (i3 == 0 && SVGASoundManager.access$getSoundCallBackMap$p(sVGASoundManager).containsKey(Integer.valueOf(i2)) && (sVGASoundCallBack = (SVGASoundCallBack) SVGASoundManager.access$getSoundCallBackMap$p(sVGASoundManager).get(Integer.valueOf(i2))) != null) {
                        sVGASoundCallBack.onComplete();
                    }
                }
            });
        }
    }
}
