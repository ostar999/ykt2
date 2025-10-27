package com.hyphenate.easeui.modules.chat.interfaces;

import android.view.MotionEvent;
import android.view.View;

/* loaded from: classes4.dex */
public interface ChatInputMenuListener {
    void onChatExtendMenuItemClick(int i2, View view);

    void onExpressionClicked(Object obj);

    boolean onPressToSpeakBtnTouch(View view, MotionEvent motionEvent);

    void onSendMessage(String str);

    void onTyping(CharSequence charSequence, int i2, int i3, int i4);
}
