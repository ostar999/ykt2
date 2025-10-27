package com.hyphenate.easeui.modules.chat;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.domain.EaseEmojicon;
import com.hyphenate.easeui.modules.chat.interfaces.ChatInputMenuListener;
import com.hyphenate.easeui.modules.chat.interfaces.EaseChatExtendMenuItemClickListener;
import com.hyphenate.easeui.modules.chat.interfaces.EaseChatPrimaryMenuListener;
import com.hyphenate.easeui.modules.chat.interfaces.EaseEmojiconMenuListener;
import com.hyphenate.easeui.modules.chat.interfaces.IChatEmojiconMenu;
import com.hyphenate.easeui.modules.chat.interfaces.IChatExtendMenu;
import com.hyphenate.easeui.modules.chat.interfaces.IChatInputMenu;
import com.hyphenate.easeui.modules.chat.interfaces.IChatPrimaryMenu;
import com.hyphenate.easeui.utils.EaseSmileUtils;
import com.hyphenate.util.EMLog;

/* loaded from: classes4.dex */
public class EaseChatInputMenu extends LinearLayout implements IChatInputMenu, EaseChatPrimaryMenuListener, EaseEmojiconMenuListener, EaseChatExtendMenuItemClickListener {
    private static final String TAG = "EaseChatInputMenu";
    private LinearLayout chatMenuContainer;
    private IChatEmojiconMenu emojiconMenu;
    private IChatExtendMenu extendMenu;
    private FrameLayout extendMenuContainer;
    private boolean isDarkMode;
    private ChatInputMenuListener menuListener;
    private IChatPrimaryMenu primaryMenu;
    private FrameLayout primaryMenuContainer;

    public EaseChatInputMenu(Context context) {
        this(context, null);
    }

    private void init() throws Resources.NotFoundException {
        showPrimaryMenu();
        if (this.extendMenu == null) {
            EaseChatExtendMenu easeChatExtendMenu = new EaseChatExtendMenu(getContext(), this.isDarkMode);
            this.extendMenu = easeChatExtendMenu;
            easeChatExtendMenu.init();
        }
        if (this.emojiconMenu == null) {
            EaseEmojiconMenu easeEmojiconMenu = new EaseEmojiconMenu(getContext());
            this.emojiconMenu = easeEmojiconMenu;
            easeEmojiconMenu.init();
        }
    }

    private void showPrimaryMenu() {
        if (this.primaryMenu == null) {
            this.primaryMenu = new EaseChatPrimaryMenu(getContext());
        }
        if (this.primaryMenu instanceof View) {
            this.primaryMenuContainer.removeAllViews();
            this.primaryMenuContainer.addView((View) this.primaryMenu);
            this.primaryMenu.setEaseChatPrimaryMenuListener(this);
        }
        if ((this.primaryMenu instanceof Fragment) && (getContext() instanceof AppCompatActivity)) {
            ((AppCompatActivity) getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.primary_menu_container, (Fragment) this.primaryMenu).commitAllowingStateLoss();
            this.primaryMenu.setEaseChatPrimaryMenuListener(this);
        }
    }

    @Override // com.hyphenate.easeui.modules.chat.interfaces.IChatInputMenu
    public IChatExtendMenu getChatExtendMenu() {
        return this.extendMenu;
    }

    @Override // com.hyphenate.easeui.modules.chat.interfaces.IChatInputMenu
    public IChatEmojiconMenu getEmojiconMenu() {
        return this.emojiconMenu;
    }

    @Override // com.hyphenate.easeui.modules.chat.interfaces.IChatInputMenu
    public IChatPrimaryMenu getPrimaryMenu() {
        return this.primaryMenu;
    }

    @Override // com.hyphenate.easeui.modules.chat.interfaces.IChatInputMenu
    public void hideExtendContainer() {
        this.primaryMenu.showNormalStatus();
        this.extendMenuContainer.setVisibility(8);
    }

    @Override // com.hyphenate.easeui.modules.chat.interfaces.IChatInputMenu
    public void hideSoftKeyboard() {
        IChatPrimaryMenu iChatPrimaryMenu = this.primaryMenu;
        if (iChatPrimaryMenu != null) {
            iChatPrimaryMenu.hideSoftKeyboard();
        }
    }

    @Override // com.hyphenate.easeui.modules.chat.interfaces.IChatInputMenu
    public boolean onBackPressed() {
        if (this.extendMenuContainer.getVisibility() != 0) {
            return true;
        }
        this.extendMenuContainer.setVisibility(8);
        return false;
    }

    @Override // com.hyphenate.easeui.modules.chat.interfaces.EaseChatExtendMenuItemClickListener
    public void onChatExtendMenuItemClick(int i2, View view) {
        EMLog.i(TAG, "onChatExtendMenuItemClick itemId = " + i2);
        ChatInputMenuListener chatInputMenuListener = this.menuListener;
        if (chatInputMenuListener != null) {
            chatInputMenuListener.onChatExtendMenuItemClick(i2, view);
        }
    }

    @Override // com.hyphenate.easeui.modules.chat.interfaces.EaseEmojiconMenuListener
    public void onDeleteImageClicked() {
        EMLog.i(TAG, "onDeleteImageClicked");
        this.primaryMenu.onEmojiconDeleteEvent();
    }

    @Override // com.hyphenate.easeui.modules.chat.interfaces.EaseChatPrimaryMenuListener
    public void onEditTextClicked() {
        EMLog.i(TAG, "onEditTextClicked");
    }

    @Override // com.hyphenate.easeui.modules.chat.interfaces.EaseChatPrimaryMenuListener
    public void onEditTextHasFocus(boolean z2) {
        EMLog.i(TAG, "onEditTextHasFocus: hasFocus = " + z2);
    }

    @Override // com.hyphenate.easeui.modules.chat.interfaces.EaseEmojiconMenuListener
    public void onExpressionClicked(Object obj) {
        EMLog.i(TAG, "onExpressionClicked");
        if (!(obj instanceof EaseEmojicon)) {
            ChatInputMenuListener chatInputMenuListener = this.menuListener;
            if (chatInputMenuListener != null) {
                chatInputMenuListener.onExpressionClicked(obj);
                return;
            }
            return;
        }
        EaseEmojicon easeEmojicon = (EaseEmojicon) obj;
        if (easeEmojicon.getType() != EaseEmojicon.Type.BIG_EXPRESSION) {
            if (easeEmojicon.getEmojiText() != null) {
                this.primaryMenu.onEmojiconInputEvent(EaseSmileUtils.getSmiledText(getContext(), easeEmojicon.getEmojiText()));
            }
        } else {
            ChatInputMenuListener chatInputMenuListener2 = this.menuListener;
            if (chatInputMenuListener2 != null) {
                chatInputMenuListener2.onExpressionClicked(obj);
            }
        }
    }

    @Override // android.view.View
    public void onFinishInflate() throws Resources.NotFoundException {
        super.onFinishInflate();
        this.chatMenuContainer = (LinearLayout) findViewById(R.id.chat_menu_container);
        this.primaryMenuContainer = (FrameLayout) findViewById(R.id.primary_menu_container);
        this.extendMenuContainer = (FrameLayout) findViewById(R.id.extend_menu_container);
        init();
    }

    @Override // com.hyphenate.easeui.modules.chat.interfaces.EaseChatPrimaryMenuListener
    public boolean onPressToSpeakBtnTouch(View view, MotionEvent motionEvent) {
        ChatInputMenuListener chatInputMenuListener = this.menuListener;
        if (chatInputMenuListener != null) {
            return chatInputMenuListener.onPressToSpeakBtnTouch(view, motionEvent);
        }
        return false;
    }

    @Override // com.hyphenate.easeui.modules.chat.interfaces.EaseChatPrimaryMenuListener
    public void onSendBtnClicked(String str) {
        EMLog.i(TAG, "onSendBtnClicked content:" + str);
        ChatInputMenuListener chatInputMenuListener = this.menuListener;
        if (chatInputMenuListener != null) {
            chatInputMenuListener.onSendMessage(str);
        }
    }

    @Override // com.hyphenate.easeui.modules.chat.interfaces.EaseChatPrimaryMenuListener
    public void onToggleEmojiconClicked(boolean z2) throws Resources.NotFoundException {
        EMLog.i(TAG, "onToggleEmojiconClicked extend:" + z2);
        showEmojiconMenu(z2);
    }

    @Override // com.hyphenate.easeui.modules.chat.interfaces.EaseChatPrimaryMenuListener
    public void onToggleExtendClicked(boolean z2) {
        EMLog.i(TAG, "onToggleExtendClicked extend:" + z2);
        showExtendMenu(z2);
    }

    @Override // com.hyphenate.easeui.modules.chat.interfaces.EaseChatPrimaryMenuListener
    public void onToggleTextBtnClicked() {
        EMLog.i(TAG, "onToggleTextBtnClicked");
        showExtendMenu(false);
    }

    @Override // com.hyphenate.easeui.modules.chat.interfaces.EaseChatPrimaryMenuListener
    public void onToggleVoiceBtnClicked() {
        Log.e("TAG", "onToggleVoiceBtnClicked");
        showExtendMenu(false);
    }

    @Override // com.hyphenate.easeui.modules.chat.interfaces.EaseChatPrimaryMenuListener
    public void onTyping(CharSequence charSequence, int i2, int i3, int i4) {
        EMLog.i(TAG, "onTyping: s = " + ((Object) charSequence));
        ChatInputMenuListener chatInputMenuListener = this.menuListener;
        if (chatInputMenuListener != null) {
            chatInputMenuListener.onTyping(charSequence, i2, i3, i4);
        }
    }

    @Override // com.hyphenate.easeui.modules.chat.interfaces.IChatInputMenu
    public void setChatInputMenuListener(ChatInputMenuListener chatInputMenuListener) {
        this.menuListener = chatInputMenuListener;
    }

    @Override // com.hyphenate.easeui.modules.chat.interfaces.IChatInputMenu
    public void setCustomEmojiconMenu(IChatEmojiconMenu iChatEmojiconMenu) {
        this.emojiconMenu = iChatEmojiconMenu;
    }

    @Override // com.hyphenate.easeui.modules.chat.interfaces.IChatInputMenu
    public void setCustomExtendMenu(IChatExtendMenu iChatExtendMenu) {
        this.extendMenu = iChatExtendMenu;
    }

    @Override // com.hyphenate.easeui.modules.chat.interfaces.IChatInputMenu
    public void setCustomPrimaryMenu(IChatPrimaryMenu iChatPrimaryMenu) {
        this.primaryMenu = iChatPrimaryMenu;
        showPrimaryMenu();
    }

    public void setDarkMode(boolean z2) {
        this.isDarkMode = z2;
    }

    @Override // com.hyphenate.easeui.modules.chat.interfaces.IChatInputMenu
    public void showEmojiconMenu(boolean z2) throws Resources.NotFoundException {
        if (z2) {
            showEmojiconMenu();
        } else {
            this.extendMenuContainer.setVisibility(8);
        }
    }

    @Override // com.hyphenate.easeui.modules.chat.interfaces.IChatInputMenu
    public void showExtendMenu(boolean z2) {
        if (z2) {
            showExtendMenu();
            return;
        }
        this.extendMenuContainer.setVisibility(8);
        IChatPrimaryMenu iChatPrimaryMenu = this.primaryMenu;
        if (iChatPrimaryMenu != null) {
            iChatPrimaryMenu.hideExtendStatus();
        }
    }

    public EaseChatInputMenu(Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public EaseChatInputMenu(Context context, @Nullable AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        LayoutInflater.from(context).inflate(R.layout.ease_widget_chat_input_menu_container, this);
    }

    private void showEmojiconMenu() throws Resources.NotFoundException {
        if (this.emojiconMenu == null) {
            EaseEmojiconMenu easeEmojiconMenu = new EaseEmojiconMenu(getContext());
            this.emojiconMenu = easeEmojiconMenu;
            easeEmojiconMenu.init();
        }
        if (this.emojiconMenu instanceof View) {
            this.extendMenuContainer.setVisibility(0);
            this.extendMenuContainer.removeAllViews();
            this.extendMenuContainer.addView((View) this.emojiconMenu);
            this.emojiconMenu.setEmojiconMenuListener(this);
        }
        if ((this.emojiconMenu instanceof Fragment) && (getContext() instanceof AppCompatActivity)) {
            this.extendMenuContainer.setVisibility(0);
            ((AppCompatActivity) getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.extend_menu_container, (Fragment) this.emojiconMenu).commitAllowingStateLoss();
            this.emojiconMenu.setEmojiconMenuListener(this);
        }
    }

    private void showExtendMenu() {
        if (this.extendMenu == null) {
            EaseChatExtendMenu easeChatExtendMenu = new EaseChatExtendMenu(getContext(), false);
            this.extendMenu = easeChatExtendMenu;
            easeChatExtendMenu.init();
        }
        if (this.extendMenu instanceof View) {
            this.extendMenuContainer.setVisibility(0);
            this.extendMenuContainer.removeAllViews();
            this.extendMenuContainer.addView((View) this.extendMenu);
            this.extendMenu.setEaseChatExtendMenuItemClickListener(this);
        }
        if ((this.extendMenu instanceof Fragment) && (getContext() instanceof AppCompatActivity)) {
            this.extendMenuContainer.setVisibility(0);
            ((AppCompatActivity) getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.extend_menu_container, (Fragment) this.extendMenu).commitAllowingStateLoss();
            this.extendMenu.setEaseChatExtendMenuItemClickListener(this);
        }
    }
}
