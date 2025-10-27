package com.hyphenate.easeui.provider;

import com.hyphenate.easeui.domain.EaseEmojicon;
import java.util.Map;

/* loaded from: classes4.dex */
public interface EaseEmojiconInfoProvider {
    EaseEmojicon getEmojiconInfo(String str);

    Map<String, Object> getTextEmojiconMapping();
}
