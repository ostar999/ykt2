package com.hyphenate.easeui.modules.chat;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.constants.EaseConstant;
import com.hyphenate.easeui.manager.EaseDingMessageHelper;
import com.hyphenate.easeui.modules.chat.EaseChatMessageListLayout;
import com.hyphenate.easeui.modules.chat.interfaces.OnAddMsgAttrsBeforeSendEvent;
import com.hyphenate.easeui.modules.chat.interfaces.OnChatLayoutListener;
import com.hyphenate.easeui.modules.chat.interfaces.OnChatRecordTouchListener;
import com.hyphenate.easeui.modules.chat.interfaces.OnMenuChangeListener;
import com.hyphenate.easeui.modules.chat.interfaces.OnTranslateMessageListener;
import com.hyphenate.easeui.modules.menu.EasePopupWindowHelper;
import com.hyphenate.easeui.modules.menu.MenuItemBean;
import com.hyphenate.easeui.ui.EaseBaiduMapActivity;
import com.hyphenate.easeui.ui.base.EaseBaseFragment;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.hyphenate.easeui.utils.EaseCompat;
import com.hyphenate.easeui.utils.EaseFileUtils;
import com.hyphenate.util.EMLog;
import com.hyphenate.util.PathUtil;
import com.hyphenate.util.VersionUtils;
import com.psychiatrygarden.utils.MimeTypes;
import java.io.File;
import java.io.IOException;

/* loaded from: classes4.dex */
public class EaseChatFragment extends EaseBaseFragment implements OnChatLayoutListener, OnMenuChangeListener, OnAddMsgAttrsBeforeSendEvent, OnChatRecordTouchListener, OnTranslateMessageListener {
    protected static final int REQUEST_CODE_CAMERA = 2;
    protected static final int REQUEST_CODE_DING_MSG = 4;
    protected static final int REQUEST_CODE_LOCAL = 3;
    protected static final int REQUEST_CODE_MAP = 1;
    protected static final int REQUEST_CODE_SELECT_FILE = 12;
    protected static final int REQUEST_CODE_SELECT_VIDEO = 11;
    private static final String TAG = "EaseChatFragment";
    protected File cameraFile;
    public EaseChatLayout chatLayout;
    public int chatType;
    public String conversationId;
    public String historyMsgId;
    public boolean isMessageInit;
    public boolean isRoam;
    private OnChatLayoutListener listener;

    private int getLayoutId() {
        return R.layout.ease_fragment_chat_list;
    }

    private void onActivityResultForLocalVideos(Intent intent) throws IllegalStateException, IOException, SecurityException, IllegalArgumentException {
        if (intent != null) {
            Uri data = intent.getData();
            MediaPlayer mediaPlayer = new MediaPlayer();
            try {
                mediaPlayer.setDataSource(this.mContext, data);
                mediaPlayer.prepare();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
            int duration = mediaPlayer.getDuration();
            EMLog.d(TAG, "path = " + data.getPath() + ",duration=" + duration);
            this.chatLayout.sendVideoMessage(data, duration);
        }
    }

    @Override // com.hyphenate.easeui.modules.chat.interfaces.OnAddMsgAttrsBeforeSendEvent
    public void addMsgAttrsBeforeSend(EMMessage eMMessage) {
    }

    public boolean checkSdCardExist() {
        return EaseCommonUtils.isSdcardExist();
    }

    public void initArguments() {
        Bundle arguments = getArguments();
        if (arguments != null) {
            this.conversationId = arguments.getString(EaseConstant.EXTRA_CONVERSATION_ID);
            this.chatType = arguments.getInt(EaseConstant.EXTRA_CHAT_TYPE, 1);
            this.historyMsgId = arguments.getString(EaseConstant.HISTORY_MSG_ID);
            this.isRoam = arguments.getBoolean(EaseConstant.EXTRA_IS_ROAM, false);
        }
    }

    public void initData() {
        if (TextUtils.isEmpty(this.historyMsgId)) {
            if (this.isRoam) {
                this.chatLayout.init(EaseChatMessageListLayout.LoadDataType.ROAM, this.conversationId, this.chatType);
            } else {
                this.chatLayout.init(this.conversationId, this.chatType);
            }
            this.chatLayout.loadDefaultData();
        } else {
            this.chatLayout.init(EaseChatMessageListLayout.LoadDataType.HISTORY, this.conversationId, this.chatType);
            this.chatLayout.loadData(this.historyMsgId);
        }
        this.isMessageInit = true;
    }

    public void initListener() {
        this.chatLayout.setOnChatLayoutListener(this);
        this.chatLayout.setOnPopupWindowItemClickListener(this);
        this.chatLayout.setOnAddMsgAttrsBeforeSendEvent(this);
        this.chatLayout.setOnChatRecordTouchListener(this);
        this.chatLayout.setOnTranslateListener(this);
    }

    public void initView() {
        EaseChatLayout easeChatLayout = (EaseChatLayout) findViewById(R.id.layout_chat);
        this.chatLayout = easeChatLayout;
        easeChatLayout.getChatMessageListLayout().setItemShowType(EaseChatMessageListLayout.ShowType.NORMAL);
        this.chatLayout.getChatMessageListLayout().setBackgroundColor(ContextCompat.getColor(this.mContext, R.color.gray));
    }

    @Override // androidx.fragment.app.Fragment
    public void onActivityCreated(@Nullable Bundle bundle) {
        super.onActivityCreated(bundle);
        initData();
    }

    @Override // androidx.fragment.app.Fragment
    public void onActivityResult(int i2, int i3, @Nullable Intent intent) throws IllegalStateException, IOException, SecurityException, IllegalArgumentException {
        super.onActivityResult(i2, i3, intent);
        if (i3 == -1) {
            this.chatLayout.getChatInputMenu().hideExtendContainer();
            if (i2 == 2) {
                onActivityResultForCamera(intent);
                return;
            }
            if (i2 == 3) {
                onActivityResultForLocalPhotos(intent);
                return;
            }
            if (i2 == 1) {
                onActivityResultForMapLocation(intent);
                return;
            }
            if (i2 == 4) {
                onActivityResultForDingMsg(intent);
            } else if (i2 == 12) {
                onActivityResultForLocalFiles(intent);
            } else if (i2 == 11) {
                onActivityResultForLocalVideos(intent);
            }
        }
    }

    public void onActivityResultForCamera(Intent intent) {
        File file = this.cameraFile;
        if (file == null || !file.exists()) {
            return;
        }
        this.chatLayout.sendImageMessage(Uri.parse(this.cameraFile.getAbsolutePath()));
    }

    public void onActivityResultForDingMsg(@Nullable Intent intent) {
        if (intent != null) {
            String stringExtra = intent.getStringExtra("msg");
            EMLog.i(TAG, "To send the ding-type msg, content: " + stringExtra);
            this.chatLayout.sendMessage(EaseDingMessageHelper.get().createDingMessage(this.conversationId, stringExtra));
        }
    }

    public void onActivityResultForLocalFiles(@Nullable Intent intent) {
        Uri data;
        if (intent == null || (data = intent.getData()) == null) {
            return;
        }
        String filePath = EaseFileUtils.getFilePath(this.mContext, data);
        if (!TextUtils.isEmpty(filePath) && new File(filePath).exists()) {
            this.chatLayout.sendFileMessage(Uri.parse(filePath));
        } else {
            EaseFileUtils.saveUriPermission(this.mContext, data, intent);
            this.chatLayout.sendFileMessage(data);
        }
    }

    public void onActivityResultForLocalPhotos(@Nullable Intent intent) {
        Uri data;
        if (intent == null || (data = intent.getData()) == null) {
            return;
        }
        String filePath = EaseFileUtils.getFilePath(this.mContext, data);
        if (!TextUtils.isEmpty(filePath) && new File(filePath).exists()) {
            this.chatLayout.sendImageMessage(Uri.parse(filePath));
        } else {
            EaseFileUtils.saveUriPermission(this.mContext, data, intent);
            this.chatLayout.sendImageMessage(data);
        }
    }

    public void onActivityResultForMapLocation(@Nullable Intent intent) {
        if (intent != null) {
            double doubleExtra = intent.getDoubleExtra("latitude", 0.0d);
            double doubleExtra2 = intent.getDoubleExtra("longitude", 0.0d);
            String stringExtra = intent.getStringExtra("address");
            String stringExtra2 = intent.getStringExtra("buildingName");
            if (stringExtra != null && !stringExtra.equals("")) {
                this.chatLayout.sendLocationMessage(doubleExtra, doubleExtra2, stringExtra, stringExtra2);
                return;
            }
            OnChatLayoutListener onChatLayoutListener = this.listener;
            if (onChatLayoutListener != null) {
                onChatLayoutListener.onChatError(-1, getResources().getString(R.string.unable_to_get_loaction));
            }
        }
    }

    @Override // com.hyphenate.easeui.modules.chat.interfaces.OnChatLayoutListener
    public boolean onBubbleClick(View view, EMMessage eMMessage) {
        OnChatLayoutListener onChatLayoutListener = this.listener;
        if (onChatLayoutListener != null) {
            return onChatLayoutListener.onBubbleClick(view, eMMessage);
        }
        return false;
    }

    @Override // com.hyphenate.easeui.modules.chat.interfaces.OnChatLayoutListener
    public boolean onBubbleLongClick(View view, EMMessage eMMessage) {
        OnChatLayoutListener onChatLayoutListener = this.listener;
        if (onChatLayoutListener != null) {
            return onChatLayoutListener.onBubbleLongClick(view, eMMessage);
        }
        return false;
    }

    @Override // com.hyphenate.easeui.modules.chat.interfaces.OnChatLayoutListener
    public void onChatError(int i2, String str) {
        OnChatLayoutListener onChatLayoutListener = this.listener;
        if (onChatLayoutListener != null) {
            onChatLayoutListener.onChatError(i2, str);
        }
    }

    @Override // com.hyphenate.easeui.modules.chat.interfaces.OnChatLayoutListener
    public void onChatExtendMenuItemClick(View view, int i2) {
        if (i2 == R.id.extend_item_take_picture) {
            selectPicFromCamera();
            return;
        }
        if (i2 == R.id.extend_item_picture) {
            selectPicFromLocal();
            return;
        }
        if (i2 == R.id.extend_item_location) {
            startMapLocation(1);
        } else if (i2 == R.id.extend_item_video) {
            selectVideoFromLocal();
        } else if (i2 == R.id.extend_item_file) {
            selectFileFromLocal();
        }
    }

    @Override // com.hyphenate.easeui.modules.chat.interfaces.OnChatLayoutListener
    public void onChatSuccess(EMMessage eMMessage) {
    }

    @Override // androidx.fragment.app.Fragment
    @Nullable
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        initArguments();
        return layoutInflater.inflate(getLayoutId(), (ViewGroup) null);
    }

    @Override // com.hyphenate.easeui.modules.chat.interfaces.OnMenuChangeListener
    public /* synthetic */ void onDismiss(PopupWindow popupWindow) {
        e1.c.a(this, popupWindow);
    }

    @Override // com.hyphenate.easeui.modules.chat.interfaces.OnMenuChangeListener
    public boolean onMenuItemClick(MenuItemBean menuItemBean, EMMessage eMMessage) {
        return false;
    }

    @Override // com.hyphenate.easeui.modules.chat.interfaces.OnChatLayoutListener
    public /* synthetic */ void onOtherTyping(String str) {
        e1.b.b(this, str);
    }

    @Override // com.hyphenate.easeui.modules.chat.interfaces.OnMenuChangeListener
    public void onPreMenu(EasePopupWindowHelper easePopupWindowHelper, EMMessage eMMessage, View view) {
    }

    @Override // com.hyphenate.easeui.modules.chat.interfaces.OnChatRecordTouchListener
    public boolean onRecordTouch(View view, MotionEvent motionEvent) {
        return true;
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        if (this.isMessageInit) {
            this.chatLayout.getChatMessageListLayout().refreshMessages();
        }
    }

    @Override // com.hyphenate.easeui.modules.chat.interfaces.OnChatLayoutListener
    public void onTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
    }

    @Override // com.hyphenate.easeui.modules.chat.interfaces.OnChatLayoutListener
    public void onUserAvatarClick(String str) {
        OnChatLayoutListener onChatLayoutListener = this.listener;
        if (onChatLayoutListener != null) {
            onChatLayoutListener.onUserAvatarClick(str);
        }
    }

    @Override // com.hyphenate.easeui.modules.chat.interfaces.OnChatLayoutListener
    public void onUserAvatarLongClick(String str) {
        OnChatLayoutListener onChatLayoutListener = this.listener;
        if (onChatLayoutListener != null) {
            onChatLayoutListener.onUserAvatarLongClick(str);
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onViewCreated(@NonNull View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);
        initView();
        initListener();
    }

    public void selectFileFromLocal() {
        Intent intent = new Intent();
        if (!VersionUtils.isTargetQ(getActivity()) && Build.VERSION.SDK_INT < 24) {
            intent.setAction("android.intent.action.GET_CONTENT");
        } else {
            intent.setAction("android.intent.action.OPEN_DOCUMENT");
        }
        intent.addFlags(3);
        intent.addCategory("android.intent.category.OPENABLE");
        intent.setType(MimeTypes.ANY_TYPE);
        startActivityForResult(intent, 12);
    }

    public void selectPicFromCamera() {
        if (checkSdCardExist()) {
            File file = new File(PathUtil.getInstance().getImagePath(), EMClient.getInstance().getCurrentUser() + System.currentTimeMillis() + ".jpg");
            this.cameraFile = file;
            file.getParentFile().mkdirs();
            startActivityForResult(new Intent("android.media.action.IMAGE_CAPTURE").putExtra("output", EaseCompat.getUriForFile(getContext(), this.cameraFile)), 2);
        }
    }

    public void selectPicFromLocal() {
        EaseCompat.openImage(this, 3);
    }

    public void selectVideoFromLocal() {
        Intent intent = new Intent();
        if (!VersionUtils.isTargetQ(getActivity()) && Build.VERSION.SDK_INT < 24) {
            intent.setAction("android.intent.action.GET_CONTENT");
        } else {
            intent.setAction("android.intent.action.OPEN_DOCUMENT");
        }
        intent.addFlags(3);
        intent.addCategory("android.intent.category.OPENABLE");
        intent.setType(MimeTypes.VIDEO_ALL);
        startActivityForResult(intent, 11);
    }

    public void setOnChatLayoutListener(OnChatLayoutListener onChatLayoutListener) {
        this.listener = onChatLayoutListener;
    }

    public void startMapLocation(int i2) {
        EaseBaiduMapActivity.actionStartForResult(this, i2);
    }

    @Override // com.hyphenate.easeui.modules.chat.interfaces.OnTranslateMessageListener
    public void translateMessageFail(EMMessage eMMessage, int i2, String str) {
    }

    @Override // com.hyphenate.easeui.modules.chat.interfaces.OnTranslateMessageListener
    public void translateMessageSuccess(EMMessage eMMessage) {
    }
}
