package com.hyphenate.easeui.modules.chat.interfaces;

import android.view.View;
import com.hyphenate.chat.EMMessage;

/* loaded from: classes4.dex */
public interface OnChatLayoutListener {
    boolean onBubbleClick(View view, EMMessage eMMessage);

    boolean onBubbleLongClick(View view, EMMessage eMMessage);

    void onChatError(int i2, String str);

    void onChatExtendMenuItemClick(View view, int i2);

    void onChatSuccess(EMMessage eMMessage);

    void onOtherTyping(String str);

    void onTextChanged(CharSequence charSequence, int i2, int i3, int i4);

    void onUserAvatarClick(String str);

    void onUserAvatarLongClick(String str);
}
