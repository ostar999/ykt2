package io.agora.rtc;

import io.agora.rtc.audio.AgoraRhythmPlayerConfig;

/* loaded from: classes8.dex */
public interface IAudioEffectManager {
    int adjustEffectPlayoutVolume(int soundId, int volume);

    int adjustEffectPublishVolume(int soundId, int volume);

    int configRhythmPlayer(AgoraRhythmPlayerConfig config);

    int getEffectCurrentPosition(int soundId);

    int getEffectDuration(String filePath);

    int getEffectPlayoutVolume(int soundId);

    int getEffectPublishVolume(int soundId);

    double getEffectsVolume();

    int pauseAllEffects();

    int pauseEffect(int soundId);

    @Deprecated
    int playEffect(int soundId, String filePath, int loop, double pitch, double pan, double gain);

    int playEffect(int soundId, String filePath, int loopCount, double pitch, double pan, double gain, boolean publish, int startPos);

    int preloadEffect(int soundId, String filePath);

    int resumeAllEffects();

    int resumeEffect(int soundId);

    int setEffectPosition(int soundId, int pos);

    int setEffectsVolume(double volume);

    int setVolumeOfEffect(int soundId, double volume);

    int startRhythmPlayer(String sound1, String sound2, AgoraRhythmPlayerConfig config);

    int stopAllEffects();

    int stopEffect(int soundId);

    int stopRhythmPlayer();

    int unloadEffect(int soundId);
}
