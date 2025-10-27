package com.hyphenate.easeui.modules.chat.interfaces;

import android.graphics.drawable.Drawable;
import android.widget.EditText;
import com.hyphenate.easeui.modules.chat.EaseInputMenuStyle;

/* loaded from: classes4.dex */
public interface IChatPrimaryMenu {
    EditText getEditText();

    void hideExtendStatus();

    void hideSoftKeyboard();

    void onEmojiconDeleteEvent();

    void onEmojiconInputEvent(CharSequence charSequence);

    void onTextInsert(CharSequence charSequence);

    void setEaseChatPrimaryMenuListener(EaseChatPrimaryMenuListener easeChatPrimaryMenuListener);

    void setMenuBackground(Drawable drawable);

    void setMenuShowType(EaseInputMenuStyle easeInputMenuStyle);

    void setSendButtonBackground(Drawable drawable);

    void showEmojiconStatus();

    void showMoreStatus();

    void showNormalStatus();

    void showTextStatus();

    void showVoiceStatus();
}
