package com.hyphenate.easeui.modules.conversation.interfaces;

import android.graphics.drawable.Drawable;
import com.hyphenate.easeui.modules.conversation.model.EaseConversationSetStyle;
import com.hyphenate.easeui.modules.interfaces.IAvatarSet;

/* loaded from: classes4.dex */
public interface IConversationStyle extends IAvatarSet, IConversationTextStyle {
    void hideUnreadDot(boolean z2);

    void setItemBackGround(Drawable drawable);

    void setItemHeight(int i2);

    void showSystemMessage(boolean z2);

    void showUnreadDotPosition(EaseConversationSetStyle.UnreadDotPosition unreadDotPosition);
}
