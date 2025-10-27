package com.hyphenate.easeui.modules.chat.interfaces;

import com.hyphenate.easeui.domain.EaseEmojiconGroupEntity;
import java.util.List;

/* loaded from: classes4.dex */
public interface IChatEmojiconMenu {
    void addEmojiconGroup(EaseEmojiconGroupEntity easeEmojiconGroupEntity);

    void addEmojiconGroup(List<EaseEmojiconGroupEntity> list);

    void removeEmojiconGroup(int i2);

    void setEmojiconMenuListener(EaseEmojiconMenuListener easeEmojiconMenuListener);

    void setTabBarVisibility(boolean z2);
}
