package com.hyphenate.easeui.modules.chat.interfaces;

import android.view.MotionEvent;
import android.view.View;

/* loaded from: classes4.dex */
public interface EaseChatPrimaryMenuListener {
    void onEditTextClicked();

    void onEditTextHasFocus(boolean z2);

    boolean onPressToSpeakBtnTouch(View view, MotionEvent motionEvent);

    void onSendBtnClicked(String str);

    void onToggleEmojiconClicked(boolean z2);

    void onToggleExtendClicked(boolean z2);

    void onToggleTextBtnClicked();

    void onToggleVoiceBtnClicked();

    void onTyping(CharSequence charSequence, int i2, int i3, int i4);
}
