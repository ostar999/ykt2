package com.hyphenate.easeui.modules.chat.interfaces;

/* loaded from: classes4.dex */
public interface IChatExtendMenu {
    void clear();

    void registerMenuItem(int i2, int i3, int i4);

    void registerMenuItem(int i2, int i3, int i4, int i5);

    void registerMenuItem(String str, int i2, int i3);

    void registerMenuItem(String str, int i2, int i3, int i4);

    void setEaseChatExtendMenuItemClickListener(EaseChatExtendMenuItemClickListener easeChatExtendMenuItemClickListener);

    void setMenuOrder(int i2, int i3);
}
