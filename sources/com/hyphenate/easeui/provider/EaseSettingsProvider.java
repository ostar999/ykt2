package com.hyphenate.easeui.provider;

import com.hyphenate.chat.EMMessage;

/* loaded from: classes4.dex */
public interface EaseSettingsProvider {
    boolean isMsgNotifyAllowed(EMMessage eMMessage);

    boolean isMsgSoundAllowed(EMMessage eMMessage);

    boolean isMsgVibrateAllowed(EMMessage eMMessage);

    boolean isSpeakerOpened();
}
