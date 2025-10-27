package com.hyphenate.easeui.modules.chat.interfaces;

/* loaded from: classes4.dex */
public interface IChatInputMenu {
    IChatExtendMenu getChatExtendMenu();

    IChatEmojiconMenu getEmojiconMenu();

    IChatPrimaryMenu getPrimaryMenu();

    void hideExtendContainer();

    void hideSoftKeyboard();

    boolean onBackPressed();

    void setChatInputMenuListener(ChatInputMenuListener chatInputMenuListener);

    void setCustomEmojiconMenu(IChatEmojiconMenu iChatEmojiconMenu);

    void setCustomExtendMenu(IChatExtendMenu iChatExtendMenu);

    void setCustomPrimaryMenu(IChatPrimaryMenu iChatPrimaryMenu);

    void showEmojiconMenu(boolean z2);

    void showExtendMenu(boolean z2);
}
