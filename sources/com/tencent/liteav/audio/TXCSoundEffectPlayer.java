package com.tencent.liteav.audio;

import android.text.TextUtils;
import cn.hutool.core.text.StrPool;
import com.tencent.liteav.audio.TXAudioEffectManager;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.util.TXCCommonUtil;
import java.io.File;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes6.dex */
public class TXCSoundEffectPlayer implements TXAudioEffectManager.TXMusicPlayObserver {
    private static final String TAG = "AudioCenter:TXCSoundEffectPlayer";
    private static WeakReference<b> mWeakSoundEffectListener;
    private List<Integer> mShortEffectorIDList = new ArrayList();

    public static class a {

        /* renamed from: a, reason: collision with root package name */
        private static final TXCSoundEffectPlayer f18163a = new TXCSoundEffectPlayer();
    }

    static {
        com.tencent.liteav.basic.util.h.d();
    }

    private String checkIfAssetsFile(String str) throws Throwable {
        long length;
        if (!TextUtils.isEmpty(str) && TXCCommonUtil.getAppContext() != null) {
            if (!str.startsWith("/assets/")) {
                return str;
            }
            String strSubstring = str.substring(8);
            try {
                try {
                    length = TXCCommonUtil.getAppContext().getAssets().openFd(strSubstring).getLength();
                } catch (Exception e2) {
                    TXCLog.e(TAG, "playAudioEffect openFd error " + e2.toString());
                    length = 0;
                }
                String effectCachePath = getEffectCachePath();
                File file = new File(effectCachePath);
                if (!file.exists()) {
                    file.mkdirs();
                } else if (file.isFile()) {
                    file.delete();
                    file.mkdirs();
                }
                int iLastIndexOf = strSubstring.lastIndexOf(File.separatorChar);
                if (iLastIndexOf != -1) {
                    str = effectCachePath + File.separator + length + StrPool.UNDERLINE + strSubstring.substring(iLastIndexOf + 1);
                } else {
                    str = effectCachePath + File.separator + length + StrPool.UNDERLINE + strSubstring;
                }
                if (!com.tencent.liteav.basic.util.d.a(str)) {
                    com.tencent.liteav.basic.util.d.a(TXCCommonUtil.getAppContext(), strSubstring, str);
                }
            } catch (Exception e3) {
                TXCLog.e(TAG, "playAudioEffect error " + e3.toString());
            }
        }
        return str;
    }

    private String getEffectCachePath() {
        if (TXCCommonUtil.getAppContext() == null) {
            return "";
        }
        return TXCCommonUtil.getAppContext().getCacheDir() + File.separator + "liteav_effect";
    }

    public static TXCSoundEffectPlayer getInstance() {
        return a.f18163a;
    }

    public void clearCache() {
        if (TXCCommonUtil.getAppContext() == null) {
            return;
        }
        try {
            File file = new File(getEffectCachePath());
            if (file.exists() && file.isDirectory() && com.tencent.liteav.basic.util.d.a(file) > 52428800) {
                for (File file2 : file.listFiles()) {
                    file2.delete();
                }
            }
        } catch (Exception e2) {
            TXCLog.w(TAG, "clearCache error " + e2.toString());
        }
    }

    @Override // com.tencent.liteav.audio.TXAudioEffectManager.TXMusicPlayObserver
    public void onComplete(int i2, int i3) {
        TXCLog.i(TAG, "onMusicPlayFinish -> effect id = " + i2);
        WeakReference<b> weakReference = mWeakSoundEffectListener;
        if (weakReference == null || weakReference.get() == null) {
            return;
        }
        mWeakSoundEffectListener.get().onEffectPlayFinish(i2);
    }

    @Override // com.tencent.liteav.audio.TXAudioEffectManager.TXMusicPlayObserver
    public void onPlayProgress(int i2, long j2, long j3) {
    }

    @Override // com.tencent.liteav.audio.TXAudioEffectManager.TXMusicPlayObserver
    public void onStart(int i2, int i3) {
        TXCLog.i(TAG, "onStart -> effect id = " + i2 + ", errCode = " + i3);
        WeakReference<b> weakReference = mWeakSoundEffectListener;
        if (weakReference == null || weakReference.get() == null) {
            return;
        }
        mWeakSoundEffectListener.get().onEffectPlayStart(i2, i3);
    }

    public void pauseEffectWithId(int i2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        TXCLog.i(TAG, "pauseEffectWithId -> effect id = " + i2);
        TXAudioEffectManagerImpl.getCacheInstance().pausePlayMusic(i2);
    }

    public boolean playEffectWithId(int i2, String str, boolean z2, int i3) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        TXCLog.i(TAG, "playEffectWithId -> effect id = " + i2 + ", path = " + str + ", loop = " + i3);
        TXAudioEffectManager.AudioMusicParam audioMusicParam = new TXAudioEffectManager.AudioMusicParam(i2, checkIfAssetsFile(str));
        audioMusicParam.publish = z2;
        audioMusicParam.loopCount = i3;
        audioMusicParam.isShortFile = true;
        boolean zStartPlayMusic = TXAudioEffectManagerImpl.getCacheInstance().startPlayMusic(audioMusicParam);
        TXAudioEffectManagerImpl.getCacheInstance().setMusicObserver(i2, this);
        this.mShortEffectorIDList.add(Integer.valueOf(i2));
        TXCLog.i(TAG, "playEffectWithId ->effect id = " + i2 + ", startPlayMusic result = " + zStartPlayMusic);
        return zStartPlayMusic;
    }

    public void resumeEffectWithId(int i2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        TXCLog.i(TAG, "resumeEffectWithId -> effect id = " + i2);
        TXAudioEffectManagerImpl.getCacheInstance().resumePlayMusic(i2);
    }

    public void setEffectsVolume(float f2) {
        TXCLog.i(TAG, "setEffectsVolume -> volume = " + f2);
        Iterator<Integer> it = this.mShortEffectorIDList.iterator();
        while (it.hasNext()) {
            TXAudioEffectManagerImpl.getCacheInstance().setMusicVolume(it.next().intValue(), (int) (100.0f * f2));
        }
    }

    public void setSoundEffectListener(b bVar) {
        if (bVar == null) {
            mWeakSoundEffectListener = null;
        } else {
            mWeakSoundEffectListener = new WeakReference<>(bVar);
        }
    }

    public void setVolumeOfEffect(int i2, float f2) {
        TXCLog.i(TAG, "setVolumeOfEffect -> effect id = " + i2 + ", volume = " + f2);
        TXAudioEffectManagerImpl.getCacheInstance().setMusicVolume(i2, (int) (f2 * 100.0f));
    }

    public void stopAllEffect() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        TXCLog.i(TAG, "stopAllEffect -> start");
        Iterator<Integer> it = this.mShortEffectorIDList.iterator();
        while (it.hasNext()) {
            TXAudioEffectManagerImpl.getCacheInstance().stopPlayMusic(it.next().intValue());
        }
        this.mShortEffectorIDList.clear();
        TXCLog.i(TAG, "stopAllEffect -> finish");
    }

    public void stopEffectWithId(int i2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        TXCLog.i(TAG, "stopEffectWithId -> effect id = " + i2);
        TXAudioEffectManagerImpl.getCacheInstance().setMusicObserver(i2, null);
        TXAudioEffectManagerImpl.getCacheInstance().stopPlayMusic(i2);
        int iIndexOf = this.mShortEffectorIDList.indexOf(Integer.valueOf(i2));
        if (iIndexOf >= 0) {
            this.mShortEffectorIDList.remove(iIndexOf);
        }
    }
}
