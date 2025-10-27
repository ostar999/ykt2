package com.hyphenate.easeui.modules.chat;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMChatManager;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMCmdMessageBody;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.chat.EMTranslationResult;
import com.hyphenate.easeui.EaseIM;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.domain.EaseEmojicon;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.interfaces.EaseChatRoomListener;
import com.hyphenate.easeui.interfaces.EaseGroupListener;
import com.hyphenate.easeui.interfaces.MessageListItemClickListener;
import com.hyphenate.easeui.manager.EaseAtMessageHelper;
import com.hyphenate.easeui.manager.EaseThreadManager;
import com.hyphenate.easeui.modules.chat.EaseChatMessageListLayout;
import com.hyphenate.easeui.modules.chat.interfaces.ChatInputMenuListener;
import com.hyphenate.easeui.modules.chat.interfaces.IChatLayout;
import com.hyphenate.easeui.modules.chat.interfaces.OnAddMsgAttrsBeforeSendEvent;
import com.hyphenate.easeui.modules.chat.interfaces.OnChatLayoutListener;
import com.hyphenate.easeui.modules.chat.interfaces.OnChatRecordTouchListener;
import com.hyphenate.easeui.modules.chat.interfaces.OnMenuChangeListener;
import com.hyphenate.easeui.modules.chat.interfaces.OnRecallMessageResultListener;
import com.hyphenate.easeui.modules.chat.interfaces.OnTranslateMessageListener;
import com.hyphenate.easeui.modules.chat.presenter.EaseHandleMessagePresenter;
import com.hyphenate.easeui.modules.chat.presenter.EaseHandleMessagePresenterImpl;
import com.hyphenate.easeui.modules.chat.presenter.IHandleMessageView;
import com.hyphenate.easeui.modules.interfaces.IPopupWindow;
import com.hyphenate.easeui.modules.menu.EasePopupWindow;
import com.hyphenate.easeui.modules.menu.EasePopupWindowHelper;
import com.hyphenate.easeui.modules.menu.MenuItemBean;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.hyphenate.easeui.utils.EaseUserUtils;
import com.hyphenate.easeui.widget.EaseAlertDialog;
import com.hyphenate.easeui.widget.EaseVoiceRecorderView;
import com.hyphenate.exceptions.HyphenateException;
import com.hyphenate.util.EMLog;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes4.dex */
public class EaseChatLayout extends RelativeLayout implements IChatLayout, IHandleMessageView, IPopupWindow, ChatInputMenuListener, EMMessageListener, EaseChatMessageListLayout.OnMessageTouchListener, MessageListItemClickListener, EaseChatMessageListLayout.OnChatErrorListener {
    public static final String ACTION_TYPING_BEGIN = "TypingBegin";
    public static final String ACTION_TYPING_END = "TypingEnd";
    public static final String AT_PREFIX = "@";
    public static final String AT_SUFFIX = " ";
    private static final int MSG_OTHER_TYPING_END = 2;
    private static final int MSG_TYPING_END = 1;
    private static final int MSG_TYPING_HEARTBEAT = 0;
    protected static final int OTHER_TYPING_SHOW_TIME = 5000;
    private static final String TAG = "EaseChatLayout";
    protected static final int TYPING_SHOW_TIME = 10000;
    private ChatRoomListener chatRoomListener;
    private int chatType;
    private ClipboardManager clipboard;
    private String conversationId;
    private GroupListener groupListener;
    private EaseChatInputMenu inputMenu;
    private boolean isNotFirstSend;
    private OnChatLayoutListener listener;
    private OnMenuChangeListener menuChangeListener;
    private EasePopupWindowHelper menuHelper;
    private EaseChatMessageListLayout messageListLayout;
    private EaseHandleMessagePresenter presenter;
    private OnRecallMessageResultListener recallMessageListener;
    private OnChatRecordTouchListener recordTouchListener;
    private OnAddMsgAttrsBeforeSendEvent sendMsgEvent;
    private boolean showDefaultMenu;
    private String targetLanguageCode;
    private OnTranslateMessageListener translateListener;
    private boolean turnOnTyping;
    private Handler typingHandler;
    private EaseVoiceRecorderView voiceRecorder;

    /* renamed from: com.hyphenate.easeui.modules.chat.EaseChatLayout$5, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass5 {
        static final /* synthetic */ int[] $SwitchMap$com$hyphenate$chat$EMMessage$Type;

        static {
            int[] iArr = new int[EMMessage.Type.values().length];
            $SwitchMap$com$hyphenate$chat$EMMessage$Type = iArr;
            try {
                iArr[EMMessage.Type.TXT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$hyphenate$chat$EMMessage$Type[EMMessage.Type.LOCATION.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$hyphenate$chat$EMMessage$Type[EMMessage.Type.FILE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$hyphenate$chat$EMMessage$Type[EMMessage.Type.IMAGE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$hyphenate$chat$EMMessage$Type[EMMessage.Type.VOICE.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$hyphenate$chat$EMMessage$Type[EMMessage.Type.VIDEO.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }

    public class ChatRoomListener extends EaseChatRoomListener {
        private ChatRoomListener() {
        }

        @Override // com.hyphenate.easeui.interfaces.EaseChatRoomListener, com.hyphenate.EMChatRoomChangeListener
        public void onChatRoomDestroyed(String str, String str2) {
            EaseChatLayout.this.finishCurrent();
        }

        @Override // com.hyphenate.easeui.interfaces.EaseChatRoomListener, com.hyphenate.EMChatRoomChangeListener
        public void onMemberExited(String str, String str2, String str3) {
        }

        @Override // com.hyphenate.easeui.interfaces.EaseChatRoomListener, com.hyphenate.EMChatRoomChangeListener
        public void onMemberJoined(String str, String str2) {
        }

        @Override // com.hyphenate.easeui.interfaces.EaseChatRoomListener, com.hyphenate.EMChatRoomChangeListener
        public void onRemovedFromChatRoom(int i2, String str, String str2, String str3) {
            if (TextUtils.equals(str, EaseChatLayout.this.conversationId) && i2 == 0) {
                EaseChatLayout.this.finishCurrent();
            }
        }
    }

    public class GroupListener extends EaseGroupListener {
        private GroupListener() {
        }

        @Override // com.hyphenate.easeui.interfaces.EaseGroupListener, com.hyphenate.EMGroupChangeListener
        public void onGroupDestroyed(String str, String str2) {
            EaseChatLayout.this.finishCurrent();
        }

        @Override // com.hyphenate.easeui.interfaces.EaseGroupListener, com.hyphenate.EMGroupChangeListener
        public void onUserRemoved(String str, String str2) {
            EaseChatLayout.this.finishCurrent();
        }
    }

    public EaseChatLayout(Context context) {
        this(context, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void finishCurrent() {
        if (getContext() instanceof Activity) {
            ((Activity) getContext()).finish();
        }
    }

    private EMChatManager getChatManager() {
        return EMClient.getInstance().chatManager();
    }

    private void initListener() {
        this.messageListLayout.setOnMessageTouchListener(this);
        this.messageListLayout.setMessageListItemClickListener(this);
        this.messageListLayout.setOnChatErrorListener(this);
        this.inputMenu.setChatInputMenuListener(this);
        getChatManager().addMessageListener(this);
    }

    private void initTypingHandler() {
        Handler handler = new Handler() { // from class: com.hyphenate.easeui.modules.chat.EaseChatLayout.1
            @Override // android.os.Handler
            public void handleMessage(@NonNull Message message) {
                int i2 = message.what;
                if (i2 == 0) {
                    EaseChatLayout.this.setTypingBeginMsg(this);
                } else if (i2 == 1) {
                    EaseChatLayout.this.setTypingEndMsg(this);
                } else {
                    if (i2 != 2) {
                        return;
                    }
                    EaseChatLayout.this.setOtherTypingEnd(this);
                }
            }
        };
        this.typingHandler = handler;
        if (this.turnOnTyping) {
            return;
        }
        handler.removeCallbacksAndMessages(null);
    }

    private void initView() {
        this.messageListLayout = (EaseChatMessageListLayout) findViewById(R.id.layout_chat_message);
        this.inputMenu = (EaseChatInputMenu) findViewById(R.id.layout_menu);
        this.voiceRecorder = (EaseVoiceRecorderView) findViewById(R.id.voice_recorder);
        this.presenter.attachView(this);
        this.menuHelper = new EasePopupWindowHelper();
        this.clipboard = (ClipboardManager) getContext().getSystemService("clipboard");
    }

    private void insertText(EditText editText, String str) {
        if (editText.isFocused()) {
            editText.getText().insert(editText.getSelectionStart(), str);
        } else {
            editText.getText().insert(editText.getText().length() - 1, str);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCmdMessageReceived$0(EMMessage eMMessage, EMCmdMessageBody eMCmdMessageBody) {
        if (TextUtils.equals(eMMessage.getFrom(), this.conversationId)) {
            OnChatLayoutListener onChatLayoutListener = this.listener;
            if (onChatLayoutListener != null) {
                onChatLayoutListener.onOtherTyping(eMCmdMessageBody.action());
            }
            Handler handler = this.typingHandler;
            if (handler != null) {
                handler.removeMessages(2);
                this.typingHandler.sendEmptyMessageDelayed(2, 5000L);
            }
        }
    }

    private void refreshMessage(EMMessage eMMessage) {
        if (getChatMessageListLayout() != null) {
            getChatMessageListLayout().refreshMessage(eMMessage);
        }
    }

    private void refreshMessages(List<EMMessage> list) {
        Iterator<EMMessage> it = list.iterator();
        while (it.hasNext()) {
            refreshMessage(it.next());
        }
    }

    private void sendChannelAck() {
        EMConversation conversation;
        if (!EaseIM.getInstance().getConfigsManager().enableSendChannelAck() || (conversation = EMClient.getInstance().chatManager().getConversation(this.conversationId)) == null || conversation.getUnreadMsgCount() <= 0) {
            return;
        }
        try {
            EMClient.getInstance().chatManager().ackConversationRead(this.conversationId);
        } catch (HyphenateException e2) {
            e2.printStackTrace();
        }
    }

    private void sendGroupReadAck(EMMessage eMMessage) {
        if (eMMessage.isNeedGroupAck() && eMMessage.isUnread()) {
            try {
                EMClient.getInstance().chatManager().ackGroupMessageRead(eMMessage.getTo(), eMMessage.getMsgId(), "");
            } catch (HyphenateException e2) {
                e2.printStackTrace();
            }
        }
    }

    private void setMenuByMsgType(View view, EMMessage eMMessage) {
        EMMessage.Type type = eMMessage.getType();
        EasePopupWindowHelper easePopupWindowHelper = this.menuHelper;
        int i2 = R.id.action_chat_copy;
        easePopupWindowHelper.findItemVisible(i2, false);
        EasePopupWindowHelper easePopupWindowHelper2 = this.menuHelper;
        int i3 = R.id.action_chat_recall;
        easePopupWindowHelper2.findItemVisible(i3, false);
        EasePopupWindowHelper easePopupWindowHelper3 = this.menuHelper;
        int i4 = R.id.action_chat_translate;
        easePopupWindowHelper3.findItemVisible(i4, false);
        EasePopupWindowHelper easePopupWindowHelper4 = this.menuHelper;
        int i5 = R.id.action_chat_reTranslate;
        easePopupWindowHelper4.findItemVisible(i5, false);
        EasePopupWindowHelper easePopupWindowHelper5 = this.menuHelper;
        int i6 = R.id.action_chat_hide;
        easePopupWindowHelper5.findItemVisible(i6, false);
        EasePopupWindowHelper easePopupWindowHelper6 = this.menuHelper;
        int i7 = R.id.action_chat_delete;
        easePopupWindowHelper6.findItem(i7).setTitle(getContext().getString(R.string.action_delete));
        switch (AnonymousClass5.$SwitchMap$com$hyphenate$chat$EMMessage$Type[type.ordinal()]) {
            case 1:
                EMTranslationResult translationResult = EMClient.getInstance().translationManager().getTranslationResult(eMMessage.getMsgId());
                if (view.getId() == R.id.subBubble && translationResult != null) {
                    this.menuHelper.findItemVisible(i7, false);
                    if (translationResult.translateCount() < 2) {
                        this.menuHelper.findItemVisible(i5, true);
                    }
                    this.menuHelper.findItemVisible(i6, true);
                    break;
                } else {
                    this.menuHelper.findItemVisible(i2, true);
                    this.menuHelper.findItemVisible(i3, true);
                    this.menuHelper.findItemVisible(i7, true);
                    if (showTranslation(eMMessage)) {
                        this.menuHelper.findItemVisible(i4, true);
                        break;
                    }
                }
                break;
            case 2:
            case 3:
            case 4:
                this.menuHelper.findItemVisible(i3, true);
                break;
            case 5:
                this.menuHelper.findItem(i7).setTitle(getContext().getString(R.string.delete_voice));
                this.menuHelper.findItemVisible(i3, true);
                break;
            case 6:
                this.menuHelper.findItem(i7).setTitle(getContext().getString(R.string.delete_video));
                this.menuHelper.findItemVisible(i3, true);
                break;
        }
        if (eMMessage.direct() == EMMessage.Direct.RECEIVE) {
            this.menuHelper.findItemVisible(i3, false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setOtherTypingEnd(Handler handler) {
        if (this.chatType != 1) {
            return;
        }
        handler.removeMessages(2);
        OnChatLayoutListener onChatLayoutListener = this.listener;
        if (onChatLayoutListener != null) {
            onChatLayoutListener.onOtherTyping(ACTION_TYPING_END);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setTypingBeginMsg(Handler handler) {
        if (this.turnOnTyping && this.chatType == 1) {
            this.presenter.sendCmdMessage(ACTION_TYPING_BEGIN);
            handler.sendEmptyMessageDelayed(0, com.heytap.mcssdk.constant.a.f7153q);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setTypingEndMsg(Handler handler) {
        if (this.turnOnTyping && this.chatType == 1) {
            this.isNotFirstSend = false;
            handler.removeMessages(0);
            handler.removeMessages(1);
        }
    }

    private void showDefaultMenu(View view, final EMMessage eMMessage) {
        this.menuHelper.initMenu(getContext());
        this.menuHelper.setDefaultMenus();
        this.menuHelper.setOutsideTouchable(true);
        setMenuByMsgType(view, eMMessage);
        OnMenuChangeListener onMenuChangeListener = this.menuChangeListener;
        if (onMenuChangeListener != null) {
            onMenuChangeListener.onPreMenu(this.menuHelper, eMMessage, view);
        }
        this.menuHelper.setOnPopupMenuItemClickListener(new EasePopupWindow.OnPopupWindowItemClickListener() { // from class: com.hyphenate.easeui.modules.chat.EaseChatLayout.3
            @Override // com.hyphenate.easeui.modules.menu.EasePopupWindow.OnPopupWindowItemClickListener
            public boolean onMenuItemClick(MenuItemBean menuItemBean) {
                if (EaseChatLayout.this.menuChangeListener != null && EaseChatLayout.this.menuChangeListener.onMenuItemClick(menuItemBean, eMMessage)) {
                    return true;
                }
                if (!EaseChatLayout.this.showDefaultMenu) {
                    return false;
                }
                int itemId = menuItemBean.getItemId();
                if (itemId == R.id.action_chat_copy) {
                    EaseChatLayout.this.clipboard.setPrimaryClip(ClipData.newPlainText(null, ((EMTextMessageBody) eMMessage.getBody()).getMessage()));
                    EMLog.i(EaseChatLayout.TAG, "copy success");
                } else if (itemId == R.id.action_chat_delete) {
                    EaseChatLayout.this.deleteMessage(eMMessage);
                    EMLog.i(EaseChatLayout.TAG, "currentMsgId = " + eMMessage.getMsgId() + " timestamp = " + eMMessage.getMsgTime());
                } else if (itemId == R.id.action_chat_recall) {
                    EaseChatLayout.this.recallMessage(eMMessage);
                } else if (itemId == R.id.action_chat_translate) {
                    EaseChatLayout.this.translateMessage(eMMessage, true);
                } else if (itemId == R.id.action_chat_reTranslate) {
                    EaseChatLayout.this.translateMessage(eMMessage, false);
                } else if (itemId == R.id.action_chat_hide) {
                    EaseChatLayout.this.hideTranslate(eMMessage);
                }
                return true;
            }
        });
        this.menuHelper.setOnPopupMenuDismissListener(new EasePopupWindow.OnPopupWindowDismissListener() { // from class: com.hyphenate.easeui.modules.chat.EaseChatLayout.4
            @Override // com.hyphenate.easeui.modules.menu.EasePopupWindow.OnPopupWindowDismissListener
            public void onDismiss(PopupWindow popupWindow) {
                if (EaseChatLayout.this.menuChangeListener != null) {
                    EaseChatLayout.this.menuChangeListener.onDismiss(popupWindow);
                }
            }
        });
        this.menuHelper.show(this, view);
    }

    private boolean showTranslation(EMMessage eMMessage) {
        if (EMClient.getInstance().translationManager().isInitialized()) {
            return (EMClient.getInstance().translationManager().isTranslationResultForMessage(eMMessage.getMsgId()) && EMClient.getInstance().translationManager().getTranslationResult(eMMessage.getMsgId()).showTranslation()) ? false : true;
        }
        return false;
    }

    @Override // com.hyphenate.easeui.modules.interfaces.IPopupWindow
    public void addItemMenu(MenuItemBean menuItemBean) {
        this.menuHelper.addItemMenu(menuItemBean);
    }

    @Override // com.hyphenate.easeui.modules.chat.interfaces.IChatLayout
    public void addMessageAttributes(EMMessage eMMessage) {
        this.presenter.addMessageAttributes(eMMessage);
    }

    @Override // com.hyphenate.easeui.modules.chat.presenter.IHandleMessageView
    public void addMsgAttrBeforeSend(EMMessage eMMessage) {
        OnAddMsgAttrsBeforeSendEvent onAddMsgAttrsBeforeSendEvent = this.sendMsgEvent;
        if (onAddMsgAttrsBeforeSendEvent != null) {
            onAddMsgAttrsBeforeSendEvent.addMsgAttrsBeforeSend(eMMessage);
        }
    }

    @Override // com.hyphenate.easeui.modules.interfaces.IPopupWindow
    public void clearMenu() {
        this.menuHelper.clear();
    }

    @Override // com.hyphenate.easeui.modules.ILoadDataView
    public Context context() {
        return getContext();
    }

    @Override // com.hyphenate.easeui.modules.chat.presenter.IHandleMessageView
    public void createThumbFileFail(String str) {
        OnChatLayoutListener onChatLayoutListener = this.listener;
        if (onChatLayoutListener != null) {
            onChatLayoutListener.onChatError(-1, str);
        }
    }

    @Override // com.hyphenate.easeui.modules.chat.presenter.IHandleMessageView
    public void deleteLocalMessageSuccess(EMMessage eMMessage) {
        this.messageListLayout.removeMessage(eMMessage);
    }

    @Override // com.hyphenate.easeui.modules.chat.interfaces.IChatLayout
    public void deleteMessage(EMMessage eMMessage) {
        this.messageListLayout.getCurrentConversation().removeMessage(eMMessage.getMsgId());
        this.messageListLayout.removeMessage(eMMessage);
    }

    @Override // com.hyphenate.easeui.modules.interfaces.IPopupWindow
    public MenuItemBean findItem(int i2) {
        return this.menuHelper.findItem(i2);
    }

    @Override // com.hyphenate.easeui.modules.interfaces.IPopupWindow
    public void findItemVisible(int i2, boolean z2) {
        this.menuHelper.findItemVisible(i2, z2);
    }

    @Override // com.hyphenate.easeui.modules.chat.interfaces.IChatLayout
    public EaseChatInputMenu getChatInputMenu() {
        return this.inputMenu;
    }

    @Override // com.hyphenate.easeui.modules.chat.interfaces.IChatLayout
    public EaseChatMessageListLayout getChatMessageListLayout() {
        return this.messageListLayout;
    }

    @Override // com.hyphenate.easeui.modules.chat.interfaces.IChatLayout
    public String getInputContent() {
        return this.inputMenu.getPrimaryMenu().getEditText().getText().toString().trim();
    }

    @Override // com.hyphenate.easeui.modules.interfaces.IPopupWindow
    public EasePopupWindowHelper getMenuHelper() {
        return this.menuHelper;
    }

    @Override // com.hyphenate.easeui.modules.chat.interfaces.IChatLayout
    public void hideTranslate(EMMessage eMMessage) {
        this.presenter.hideTranslate(eMMessage);
        this.messageListLayout.refreshMessage(eMMessage);
    }

    public void init(String str, int i2) {
        init(EaseChatMessageListLayout.LoadDataType.LOCAL, str, i2);
    }

    public void initHistoryModel(String str, int i2) {
        init(EaseChatMessageListLayout.LoadDataType.HISTORY, str, i2);
    }

    public void inputAtUsername(String str, boolean z2) {
        if (EMClient.getInstance().getCurrentUser().equals(str) || !this.messageListLayout.isGroupChat()) {
            return;
        }
        EaseAtMessageHelper.get().addAtUser(str);
        EaseUser userInfo = EaseUserUtils.getUserInfo(str);
        if (userInfo != null) {
            str = userInfo.getNickname();
        }
        EditText editText = this.inputMenu.getPrimaryMenu().getEditText();
        if (!z2) {
            insertText(editText, str + " ");
            return;
        }
        insertText(editText, "@" + str + " ");
    }

    public boolean isChatRoomCon() {
        return EaseCommonUtils.getConversationType(this.chatType) == EMConversation.EMConversationType.ChatRoom;
    }

    public boolean isGroupCon() {
        return EaseCommonUtils.getConversationType(this.chatType) == EMConversation.EMConversationType.GroupChat;
    }

    public void loadData(String str, int i2) {
        sendChannelAck();
        this.messageListLayout.loadData(i2, str);
    }

    public void loadDefaultData() {
        sendChannelAck();
        this.messageListLayout.loadDefaultData();
    }

    @Override // com.hyphenate.easeui.interfaces.MessageListItemClickListener
    public boolean onBubbleClick(View view, EMMessage eMMessage) {
        OnChatLayoutListener onChatLayoutListener = this.listener;
        if (onChatLayoutListener != null) {
            return onChatLayoutListener.onBubbleClick(view, eMMessage);
        }
        return false;
    }

    @Override // com.hyphenate.easeui.interfaces.MessageListItemClickListener
    public boolean onBubbleLongClick(View view, EMMessage eMMessage) {
        if (!this.showDefaultMenu) {
            OnChatLayoutListener onChatLayoutListener = this.listener;
            if (onChatLayoutListener != null) {
                return onChatLayoutListener.onBubbleLongClick(view, eMMessage);
            }
            return false;
        }
        showDefaultMenu(view, eMMessage);
        OnChatLayoutListener onChatLayoutListener2 = this.listener;
        if (onChatLayoutListener2 != null) {
            return onChatLayoutListener2.onBubbleLongClick(view, eMMessage);
        }
        return true;
    }

    @Override // com.hyphenate.easeui.modules.chat.EaseChatMessageListLayout.OnChatErrorListener
    public void onChatError(int i2, String str) {
        OnChatLayoutListener onChatLayoutListener = this.listener;
        if (onChatLayoutListener != null) {
            onChatLayoutListener.onChatError(i2, str);
        }
    }

    @Override // com.hyphenate.easeui.modules.chat.interfaces.ChatInputMenuListener
    public void onChatExtendMenuItemClick(int i2, View view) {
        OnChatLayoutListener onChatLayoutListener = this.listener;
        if (onChatLayoutListener != null) {
            onChatLayoutListener.onChatExtendMenuItemClick(view, i2);
        }
    }

    @Override // com.hyphenate.EMMessageListener
    public void onCmdMessageReceived(List<EMMessage> list) {
        for (final EMMessage eMMessage : list) {
            final EMCmdMessageBody eMCmdMessageBody = (EMCmdMessageBody) eMMessage.getBody();
            EMLog.i(TAG, "Receive cmd message: " + eMCmdMessageBody.action() + " - " + eMCmdMessageBody.isDeliverOnlineOnly());
            EaseThreadManager.getInstance().runOnMainThread(new Runnable() { // from class: com.hyphenate.easeui.modules.chat.a
                @Override // java.lang.Runnable
                public final void run() {
                    this.f8643c.lambda$onCmdMessageReceived$0(eMMessage, eMCmdMessageBody);
                }
            });
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getChatManager().removeMessageListener(this);
        if (this.chatRoomListener != null) {
            EMClient.getInstance().chatroomManager().removeChatRoomListener(this.chatRoomListener);
        }
        if (this.groupListener != null) {
            EMClient.getInstance().groupManager().removeGroupChangeListener(this.groupListener);
        }
        if (isChatRoomCon()) {
            EMClient.getInstance().chatroomManager().leaveChatRoom(this.conversationId);
        }
        if (isGroupCon()) {
            EaseAtMessageHelper.get().removeAtMeGroup(this.conversationId);
            EaseAtMessageHelper.get().cleanToAtUserList();
        }
        Handler handler = this.typingHandler;
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }

    @Override // com.hyphenate.easeui.modules.chat.interfaces.ChatInputMenuListener
    public void onExpressionClicked(Object obj) {
        if (obj instanceof EaseEmojicon) {
            EaseEmojicon easeEmojicon = (EaseEmojicon) obj;
            this.presenter.sendBigExpressionMessage(easeEmojicon.getName(), easeEmojicon.getIdentityCode());
        }
    }

    @Override // com.hyphenate.EMMessageListener
    public /* synthetic */ void onGroupMessageRead(List list) {
        d1.e.b(this, list);
    }

    @Override // com.hyphenate.EMMessageListener
    public void onMessageChanged(EMMessage eMMessage, Object obj) {
        refreshMessage(eMMessage);
    }

    @Override // com.hyphenate.easeui.interfaces.MessageListItemClickListener
    public void onMessageCreate(EMMessage eMMessage) {
        EMLog.i(TAG, "onMessageCreate");
    }

    @Override // com.hyphenate.EMMessageListener
    public void onMessageDelivered(List<EMMessage> list) {
        refreshMessages(list);
    }

    @Override // com.hyphenate.easeui.interfaces.MessageListItemClickListener
    public void onMessageError(EMMessage eMMessage, int i2, String str) {
        OnChatLayoutListener onChatLayoutListener = this.listener;
        if (onChatLayoutListener != null) {
            onChatLayoutListener.onChatError(i2, str);
        }
    }

    @Override // com.hyphenate.easeui.interfaces.MessageListItemClickListener
    public void onMessageInProgress(EMMessage eMMessage, int i2) {
        EMLog.i(TAG, "send message on progress: " + i2);
    }

    @Override // com.hyphenate.EMMessageListener
    public void onMessageRead(List<EMMessage> list) {
        refreshMessages(list);
    }

    @Override // com.hyphenate.EMMessageListener
    public void onMessageRecalled(List<EMMessage> list) {
        if (getChatMessageListLayout() != null) {
            getChatMessageListLayout().refreshMessages();
        }
    }

    @Override // com.hyphenate.EMMessageListener
    public void onMessageReceived(List<EMMessage> list) {
        boolean z2 = false;
        for (EMMessage eMMessage : list) {
            sendGroupReadAck(eMMessage);
            sendReadAck(eMMessage);
            if (((eMMessage.getChatType() == EMMessage.ChatType.GroupChat || eMMessage.getChatType() == EMMessage.ChatType.ChatRoom) ? eMMessage.getTo() : eMMessage.getFrom()).equals(this.conversationId) || eMMessage.getTo().equals(this.conversationId) || eMMessage.conversationId().equals(this.conversationId)) {
                z2 = true;
            }
        }
        if (z2) {
            getChatMessageListLayout().refreshToLatest();
        }
    }

    @Override // com.hyphenate.easeui.interfaces.MessageListItemClickListener
    public void onMessageSuccess(EMMessage eMMessage) {
        EMLog.i(TAG, "send message onMessageSuccess");
        OnChatLayoutListener onChatLayoutListener = this.listener;
        if (onChatLayoutListener != null) {
            onChatLayoutListener.onChatSuccess(eMMessage);
        }
    }

    @Override // com.hyphenate.easeui.modules.chat.presenter.IHandleMessageView
    public void onPresenterMessageError(EMMessage eMMessage, int i2, String str) {
        EMLog.i(TAG, "send message onPresenterMessageError code: " + i2 + " error: " + str);
        refreshMessage(eMMessage);
        OnChatLayoutListener onChatLayoutListener = this.listener;
        if (onChatLayoutListener != null) {
            onChatLayoutListener.onChatError(i2, str);
        }
    }

    @Override // com.hyphenate.easeui.modules.chat.presenter.IHandleMessageView
    public void onPresenterMessageInProgress(EMMessage eMMessage, int i2) {
        EMLog.i(TAG, "send message onPresenterMessageInProgress");
    }

    @Override // com.hyphenate.easeui.modules.chat.presenter.IHandleMessageView
    public void onPresenterMessageSuccess(EMMessage eMMessage) {
        EMLog.i(TAG, "send message onPresenterMessageSuccess");
        OnChatLayoutListener onChatLayoutListener = this.listener;
        if (onChatLayoutListener != null) {
            onChatLayoutListener.onChatSuccess(eMMessage);
        }
    }

    @Override // com.hyphenate.easeui.modules.chat.interfaces.ChatInputMenuListener
    public boolean onPressToSpeakBtnTouch(View view, MotionEvent motionEvent) {
        OnChatRecordTouchListener onChatRecordTouchListener = this.recordTouchListener;
        if (onChatRecordTouchListener == null || onChatRecordTouchListener.onRecordTouch(view, motionEvent)) {
            return this.voiceRecorder.onPressToSpeakBtnTouch(view, motionEvent, new EaseVoiceRecorderView.EaseVoiceRecorderCallback() { // from class: com.hyphenate.easeui.modules.chat.b
                @Override // com.hyphenate.easeui.widget.EaseVoiceRecorderView.EaseVoiceRecorderCallback
                public final void onVoiceRecordComplete(String str, int i2) {
                    this.f8646a.sendVoiceMessage(str, i2);
                }
            });
        }
        return false;
    }

    @Override // com.hyphenate.EMMessageListener
    public /* synthetic */ void onReactionChanged(List list) {
        d1.e.g(this, list);
    }

    @Override // com.hyphenate.EMMessageListener
    public /* synthetic */ void onReadAckForGroupMessageUpdated() {
        d1.e.h(this);
    }

    @Override // com.hyphenate.easeui.interfaces.MessageListItemClickListener
    public boolean onResendClick(final EMMessage eMMessage) {
        EMLog.i(TAG, "onResendClick");
        new EaseAlertDialog(getContext(), R.string.resend, R.string.confirm_resend, (Bundle) null, new EaseAlertDialog.AlertDialogUser() { // from class: com.hyphenate.easeui.modules.chat.EaseChatLayout.2
            @Override // com.hyphenate.easeui.widget.EaseAlertDialog.AlertDialogUser
            public void onResult(boolean z2, Bundle bundle) {
                if (z2) {
                    EaseChatLayout.this.resendMessage(eMMessage);
                }
            }
        }, true).show();
        return true;
    }

    @Override // com.hyphenate.easeui.modules.chat.interfaces.ChatInputMenuListener
    public void onSendMessage(String str) {
        this.presenter.sendTextMessage(str);
    }

    @Override // com.hyphenate.easeui.modules.chat.EaseChatMessageListLayout.OnMessageTouchListener
    public void onTouchItemOutside(View view, int i2) {
        this.inputMenu.hideSoftKeyboard();
        this.inputMenu.showExtendMenu(false);
    }

    @Override // com.hyphenate.easeui.modules.chat.interfaces.ChatInputMenuListener
    public void onTyping(CharSequence charSequence, int i2, int i3, int i4) {
        Handler handler;
        OnChatLayoutListener onChatLayoutListener = this.listener;
        if (onChatLayoutListener != null) {
            onChatLayoutListener.onTextChanged(charSequence, i2, i3, i4);
        }
        if (!this.turnOnTyping || (handler = this.typingHandler) == null) {
            return;
        }
        if (!this.isNotFirstSend) {
            this.isNotFirstSend = true;
            handler.sendEmptyMessage(0);
        }
        this.typingHandler.removeMessages(1);
        this.typingHandler.sendEmptyMessageDelayed(1, com.heytap.mcssdk.constant.a.f7153q);
    }

    @Override // com.hyphenate.easeui.interfaces.MessageListItemClickListener
    public void onUserAvatarClick(String str) {
        OnChatLayoutListener onChatLayoutListener = this.listener;
        if (onChatLayoutListener != null) {
            onChatLayoutListener.onUserAvatarClick(str);
        }
    }

    @Override // com.hyphenate.easeui.interfaces.MessageListItemClickListener
    public void onUserAvatarLongClick(String str) {
        EMLog.i(TAG, "onUserAvatarLongClick");
        inputAtUsername(str, true);
        OnChatLayoutListener onChatLayoutListener = this.listener;
        if (onChatLayoutListener != null) {
            onChatLayoutListener.onUserAvatarLongClick(str);
        }
    }

    @Override // com.hyphenate.easeui.modules.chat.EaseChatMessageListLayout.OnMessageTouchListener
    public void onViewDragging() {
        this.inputMenu.hideSoftKeyboard();
        this.inputMenu.showExtendMenu(false);
    }

    @Override // com.hyphenate.easeui.modules.chat.interfaces.IChatLayout
    public void recallMessage(EMMessage eMMessage) {
        this.presenter.recallMessage(eMMessage);
    }

    @Override // com.hyphenate.easeui.modules.chat.presenter.IHandleMessageView
    public void recallMessageFail(int i2, String str) {
        OnRecallMessageResultListener onRecallMessageResultListener = this.recallMessageListener;
        if (onRecallMessageResultListener != null) {
            onRecallMessageResultListener.recallFail(i2, str);
        }
        OnChatLayoutListener onChatLayoutListener = this.listener;
        if (onChatLayoutListener != null) {
            onChatLayoutListener.onChatError(i2, str);
        }
    }

    @Override // com.hyphenate.easeui.modules.chat.presenter.IHandleMessageView
    public void recallMessageFinish(EMMessage eMMessage) {
        OnRecallMessageResultListener onRecallMessageResultListener = this.recallMessageListener;
        if (onRecallMessageResultListener != null) {
            onRecallMessageResultListener.recallSuccess(eMMessage);
        }
        this.messageListLayout.refreshMessages();
    }

    @Override // com.hyphenate.easeui.modules.chat.interfaces.IChatLayout
    public void resendMessage(EMMessage eMMessage) {
        EMLog.i(TAG, "resendMessage");
        this.presenter.resendMessage(eMMessage);
    }

    @Override // com.hyphenate.easeui.modules.chat.interfaces.IChatLayout
    public void sendAtMessage(String str) {
        this.presenter.sendAtMessage(str);
    }

    @Override // com.hyphenate.easeui.modules.chat.interfaces.IChatLayout
    public void sendBigExpressionMessage(String str, String str2) {
        this.presenter.sendBigExpressionMessage(str, str2);
    }

    @Override // com.hyphenate.easeui.modules.chat.interfaces.IChatLayout
    public void sendFileMessage(Uri uri) {
        this.presenter.sendFileMessage(uri);
    }

    @Override // com.hyphenate.easeui.modules.chat.interfaces.IChatLayout
    public void sendImageMessage(Uri uri) {
        this.presenter.sendImageMessage(uri);
    }

    @Override // com.hyphenate.easeui.modules.chat.interfaces.IChatLayout
    public void sendLocationMessage(double d3, double d4, String str, String str2) {
        this.presenter.sendLocationMessage(d3, d4, str, str2);
    }

    @Override // com.hyphenate.easeui.modules.chat.interfaces.IChatLayout
    public void sendMessage(EMMessage eMMessage) {
        this.presenter.sendMessage(eMMessage);
    }

    @Override // com.hyphenate.easeui.modules.chat.presenter.IHandleMessageView
    public void sendMessageFail(String str) {
        OnChatLayoutListener onChatLayoutListener = this.listener;
        if (onChatLayoutListener != null) {
            onChatLayoutListener.onChatError(-1, str);
        }
    }

    @Override // com.hyphenate.easeui.modules.chat.presenter.IHandleMessageView
    public void sendMessageFinish(EMMessage eMMessage) {
        if (getChatMessageListLayout() != null) {
            getChatMessageListLayout().refreshToLatest();
        }
    }

    public void sendReadAck(EMMessage eMMessage) {
        EMMessage.Type type;
        if (!EaseIM.getInstance().getConfigsManager().enableSendChannelAck() || eMMessage.direct() != EMMessage.Direct.RECEIVE || eMMessage.isAcked() || eMMessage.getChatType() != EMMessage.ChatType.Chat || (type = eMMessage.getType()) == EMMessage.Type.VIDEO || type == EMMessage.Type.VOICE || type == EMMessage.Type.FILE) {
            return;
        }
        try {
            EMClient.getInstance().chatManager().ackMessageRead(eMMessage.getFrom(), eMMessage.getMsgId());
        } catch (HyphenateException e2) {
            e2.printStackTrace();
        }
    }

    @Override // com.hyphenate.easeui.modules.chat.interfaces.IChatLayout
    public void sendTextMessage(String str) {
        this.presenter.sendTextMessage(str);
    }

    @Override // com.hyphenate.easeui.modules.chat.interfaces.IChatLayout
    public void sendVideoMessage(Uri uri, int i2) {
        this.presenter.sendVideoMessage(uri, i2);
    }

    @Override // com.hyphenate.easeui.modules.chat.interfaces.IChatLayout
    public void sendVoiceMessage(String str, int i2) {
        sendVoiceMessage(Uri.parse(str), i2);
    }

    @Override // com.hyphenate.easeui.modules.chat.interfaces.IChatLayout
    public void setOnAddMsgAttrsBeforeSendEvent(OnAddMsgAttrsBeforeSendEvent onAddMsgAttrsBeforeSendEvent) {
        this.sendMsgEvent = onAddMsgAttrsBeforeSendEvent;
    }

    @Override // com.hyphenate.easeui.modules.chat.interfaces.IChatLayout
    public void setOnChatLayoutListener(OnChatLayoutListener onChatLayoutListener) {
        this.listener = onChatLayoutListener;
    }

    @Override // com.hyphenate.easeui.modules.chat.interfaces.IChatLayout
    public void setOnChatRecordTouchListener(OnChatRecordTouchListener onChatRecordTouchListener) {
        this.recordTouchListener = onChatRecordTouchListener;
    }

    @Override // com.hyphenate.easeui.modules.interfaces.IPopupWindow
    public void setOnPopupWindowItemClickListener(OnMenuChangeListener onMenuChangeListener) {
        this.menuChangeListener = onMenuChangeListener;
    }

    @Override // com.hyphenate.easeui.modules.chat.interfaces.IChatLayout
    public void setOnRecallMessageResultListener(OnRecallMessageResultListener onRecallMessageResultListener) {
        this.recallMessageListener = onRecallMessageResultListener;
    }

    @Override // com.hyphenate.easeui.modules.chat.interfaces.IChatLayout
    public void setOnTranslateListener(OnTranslateMessageListener onTranslateMessageListener) {
        this.translateListener = onTranslateMessageListener;
    }

    public void setTargetLanguageCode(String str) {
        this.targetLanguageCode = str;
    }

    @Override // com.hyphenate.easeui.modules.interfaces.IPopupWindow
    public void showItemDefaultMenu(boolean z2) {
        this.showDefaultMenu = z2;
    }

    @Override // com.hyphenate.easeui.modules.chat.interfaces.IChatLayout
    public void translateMessage(EMMessage eMMessage, boolean z2) {
        this.presenter.translateMessage(eMMessage, this.targetLanguageCode, z2);
    }

    @Override // com.hyphenate.easeui.modules.chat.presenter.IHandleMessageView
    public void translateMessageFail(EMMessage eMMessage, int i2, String str) {
        EMLog.i(TAG, "translateMessageFail:" + i2 + ":" + str);
        OnTranslateMessageListener onTranslateMessageListener = this.translateListener;
        if (onTranslateMessageListener != null) {
            onTranslateMessageListener.translateMessageFail(eMMessage, i2, str);
        }
    }

    @Override // com.hyphenate.easeui.modules.chat.presenter.IHandleMessageView
    public void translateMessageSuccess(EMMessage eMMessage) {
        EMLog.i(TAG, "translateMessageSuccess");
        this.messageListLayout.lastMsgScrollToBottom(eMMessage);
        OnTranslateMessageListener onTranslateMessageListener = this.translateListener;
        if (onTranslateMessageListener != null) {
            onTranslateMessageListener.translateMessageSuccess(eMMessage);
        }
    }

    @Override // com.hyphenate.easeui.modules.chat.interfaces.IChatLayout
    public void turnOnTypingMonitor(boolean z2) {
        this.turnOnTyping = z2;
        if (z2) {
            return;
        }
        this.isNotFirstSend = false;
    }

    public EaseChatLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    @Override // com.hyphenate.easeui.modules.interfaces.IPopupWindow
    public void addItemMenu(int i2, int i3, int i4, String str) {
        this.menuHelper.addItemMenu(i2, i3, i4, str);
    }

    public void init(EaseChatMessageListLayout.LoadDataType loadDataType, String str, int i2) {
        this.conversationId = str;
        this.chatType = i2;
        this.messageListLayout.init(loadDataType, str, i2);
        this.presenter.setupWithToUser(i2, this.conversationId);
        if (isChatRoomCon()) {
            this.chatRoomListener = new ChatRoomListener();
            EMClient.getInstance().chatroomManager().addChatRoomChangeListener(this.chatRoomListener);
        } else if (isGroupCon()) {
            EaseAtMessageHelper.get().removeAtMeGroup(str);
            this.groupListener = new GroupListener();
            EMClient.getInstance().groupManager().addGroupChangeListener(this.groupListener);
        }
        initTypingHandler();
    }

    @Override // com.hyphenate.easeui.modules.chat.interfaces.IChatLayout
    public void sendImageMessage(Uri uri, boolean z2) {
        this.presenter.sendImageMessage(uri, z2);
    }

    @Override // com.hyphenate.easeui.modules.chat.interfaces.IChatLayout
    public void sendTextMessage(String str, boolean z2) {
        this.presenter.sendTextMessage(str, z2);
    }

    @Override // com.hyphenate.easeui.modules.chat.interfaces.IChatLayout
    public void sendVoiceMessage(Uri uri, int i2) {
        this.presenter.sendVoiceMessage(uri, i2);
    }

    public EaseChatLayout(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.showDefaultMenu = true;
        this.targetLanguageCode = SocializeProtocolConstants.PROTOCOL_KEY_EN;
        this.presenter = new EaseHandleMessagePresenterImpl();
        if (context instanceof AppCompatActivity) {
            ((AppCompatActivity) context).getLifecycle().addObserver(this.presenter);
        }
        LayoutInflater.from(context).inflate(R.layout.ease_layout_chat, this);
        initView();
        initListener();
    }

    public void loadData(String str) {
        sendChannelAck();
        this.messageListLayout.loadData(str);
    }
}
