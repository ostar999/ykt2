package com.gxx.audioplaylibrary.inter;

import com.meizu.cloud.pushsdk.constants.PushConstants;
import kotlin.Metadata;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0007\bf\u0018\u00002\u00020\u0001J\u0012\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H&J\"\u0010\u0006\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\bH&J\u0012\u0010\n\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H&J\"\u0010\u000b\u001a\u00020\u00032\u0006\u0010\f\u001a\u00020\b2\u0006\u0010\r\u001a\u00020\b2\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H&J\u0012\u0010\u000e\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H&¨\u0006\u000f"}, d2 = {"Lcom/gxx/audioplaylibrary/inter/OnAudioPlayListener;", "", "onVoiceComplete", "", "voiceId", "", "onVoiceError", "what", "", PushConstants.EXTRA, "onVoiceFocusLoss", "onVoicePlayPosition", "progress", "duration", "onVoicePrepared", "mvvm_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes4.dex */
public interface OnAudioPlayListener {
    void onVoiceComplete(@Nullable String voiceId);

    void onVoiceError(@Nullable String voiceId, int what, int extra);

    void onVoiceFocusLoss(@Nullable String voiceId);

    void onVoicePlayPosition(int progress, int duration, @Nullable String voiceId);

    void onVoicePrepared(@Nullable String voiceId);
}
