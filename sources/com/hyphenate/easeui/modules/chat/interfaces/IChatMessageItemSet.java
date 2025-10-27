package com.hyphenate.easeui.modules.chat.interfaces;

import android.graphics.drawable.Drawable;
import androidx.annotation.ColorInt;
import com.hyphenate.easeui.modules.chat.EaseChatMessageListLayout;

/* loaded from: classes4.dex */
public interface IChatMessageItemSet {
    void setAvatarDefaultSrc(Drawable drawable);

    void setAvatarShapeType(int i2);

    void setItemReceiverBackground(Drawable drawable);

    void setItemSenderBackground(Drawable drawable);

    void setItemShowType(EaseChatMessageListLayout.ShowType showType);

    void setItemTextColor(@ColorInt int i2);

    void setItemTextSize(int i2);

    void setTimeBackground(Drawable drawable);

    void setTimeTextColor(int i2);

    void setTimeTextSize(int i2);

    void showNickname(boolean z2);
}
