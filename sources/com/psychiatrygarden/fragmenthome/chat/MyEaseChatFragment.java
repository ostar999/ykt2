package com.psychiatrygarden.fragmenthome.chat;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hjq.permissions.Permission;
import com.hyphenate.EMMessageListener;
import com.hyphenate.EMValueCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMImageMessageBody;
import com.hyphenate.chat.EMLocationMessageBody;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMUserInfo;
import com.hyphenate.easeui.constants.EaseConstant;
import com.hyphenate.easeui.modules.chat.EaseChatExtendMenu;
import com.hyphenate.easeui.modules.chat.EaseChatFragment;
import com.hyphenate.easeui.modules.chat.EaseChatInputMenu;
import com.hyphenate.easeui.modules.chat.EaseChatMessageListLayout;
import com.hyphenate.easeui.modules.chat.EaseEmojiconMenu;
import com.hyphenate.easeui.modules.chat.interfaces.IChatEmojiconMenu;
import com.hyphenate.easeui.modules.chat.interfaces.IChatExtendMenu;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.hyphenate.exceptions.HyphenateException;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.PopupInfo;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.UserCommentInfoActivity;
import com.psychiatrygarden.activity.chat.ChatBaiduMapActivity;
import com.psychiatrygarden.activity.chat.GroupChatRemoveMemberActivity;
import com.psychiatrygarden.bean.GroupChatDetailBean;
import com.psychiatrygarden.bean.GroupChatListBean;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.http.ChatRequest;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.interfaceclass.QuestionDataCallBack;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.ImageLoaderUtilsCustom;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.widget.ImageViewerPopupViewCustom;
import com.psychiatrygarden.widget.RequestMediaPermissionPop;
import com.psychiatrygarden.widget.RequestRecordAudioPermissionPop;
import com.yikaobang.yixue.R;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class MyEaseChatFragment extends EaseChatFragment implements QuestionDataCallBack<String> {
    private EditText editText;
    private ActivityResultLauncher requestDataLauncher;

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onChatExtendMenuItemClick$2() {
        ActivityCompat.requestPermissions(getActivity(), new String[]{Permission.CAMERA}, 3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreateView$0(ActivityResult activityResult) {
        try {
            if (activityResult.getData() == null || TextUtils.isEmpty(activityResult.getData().getStringExtra("@_member_content"))) {
                return;
            }
            int intExtra = activityResult.getData().getIntExtra("@_start", 0);
            String stringExtra = activityResult.getData().getStringExtra("@_member_content");
            StringBuilder sb = new StringBuilder();
            int i2 = intExtra + 1;
            sb.append(this.editText.getText().toString().substring(0, i2));
            sb.append(stringExtra);
            sb.append(this.editText.getText().toString().substring(i2));
            this.editText.setText(sb.toString());
            this.editText.setSelection((this.editText.getText().toString().substring(0, i2) + stringExtra).length());
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onRecordTouch$1() {
        ActivityCompat.requestPermissions(getActivity(), new String[]{Permission.RECORD_AUDIO}, 4);
    }

    private void resetChatExtendMenu() {
        IChatExtendMenu chatExtendMenu = this.chatLayout.getChatInputMenu().getChatExtendMenu();
        if (chatExtendMenu == null) {
            return;
        }
        int currentSkinType = SkinManager.getCurrentSkinType(this.mContext);
        chatExtendMenu.clear();
        chatExtendMenu.registerMenuItem(R.string.attach_take_pic, currentSkinType == 0 ? R.drawable.ease_chat_takepic_selector : R.drawable.ease_chat_takepic_selector_night, R.id.extend_item_take_picture);
        chatExtendMenu.registerMenuItem(R.string.attach_picture, currentSkinType == 0 ? R.drawable.ease_chat_image_selector : R.drawable.ease_chat_image_selector_night, R.id.extend_item_picture);
        chatExtendMenu.registerMenuItem(R.string.attach_file, currentSkinType == 0 ? R.drawable.em_chat_file_selector : R.drawable.em_chat_file_selector_night, R.id.extend_item_file);
    }

    @Override // com.hyphenate.easeui.modules.chat.EaseChatFragment, com.hyphenate.easeui.modules.chat.interfaces.OnAddMsgAttrsBeforeSendEvent
    public void addMsgAttrsBeforeSend(EMMessage message) {
        message.setAttribute("nickname", UserConfig.getInstance().getUser().getNickname());
        message.setAttribute("avatar", UserConfig.getInstance().getUser().getAvatar());
        super.addMsgAttrsBeforeSend(message);
    }

    @Override // com.hyphenate.easeui.modules.chat.EaseChatFragment
    public void initData() {
        super.initData();
        EMClient.getInstance().chatManager().addMessageListener(new EMMessageListener() { // from class: com.psychiatrygarden.fragmenthome.chat.MyEaseChatFragment.4
            @Override // com.hyphenate.EMMessageListener
            public void onCmdMessageReceived(List<EMMessage> messages) {
            }

            @Override // com.hyphenate.EMMessageListener
            public /* synthetic */ void onGroupMessageRead(List list) {
                d1.e.b(this, list);
            }

            @Override // com.hyphenate.EMMessageListener
            public /* synthetic */ void onMessageChanged(EMMessage eMMessage, Object obj) {
                d1.e.c(this, eMMessage, obj);
            }

            @Override // com.hyphenate.EMMessageListener
            public void onMessageDelivered(List<EMMessage> messages) {
            }

            @Override // com.hyphenate.EMMessageListener
            public void onMessageRead(List<EMMessage> messages) {
            }

            @Override // com.hyphenate.EMMessageListener
            public void onMessageRecalled(List<EMMessage> messages) {
            }

            @Override // com.hyphenate.EMMessageListener
            public void onMessageReceived(List<EMMessage> messages) {
                for (int i2 = 0; i2 < messages.size(); i2++) {
                    MyEaseChatFragment.this.sendReadAck(messages.get(i2));
                }
            }

            @Override // com.hyphenate.EMMessageListener
            public /* synthetic */ void onReactionChanged(List list) {
                d1.e.g(this, list);
            }

            @Override // com.hyphenate.EMMessageListener
            public /* synthetic */ void onReadAckForGroupMessageUpdated() {
                d1.e.h(this);
            }
        });
        if (this.chatType == 2 && TextUtils.isEmpty(getArguments().getString("group_img"))) {
            ChatRequest.getIntance(getActivity()).chatDetail(getArguments().getString(EaseConstant.EXTRA_CONVERSATION_ID), this);
        }
    }

    @Override // com.hyphenate.easeui.modules.chat.EaseChatFragment
    public void initView() {
        super.initView();
        EaseChatMessageListLayout chatMessageListLayout = this.chatLayout.getChatMessageListLayout();
        chatMessageListLayout.setBackground(new ColorDrawable(Color.parseColor("#ededed")));
        chatMessageListLayout.setAvatarDefaultSrc(ContextCompat.getDrawable(this.mContext, R.drawable.ic_launcher));
        chatMessageListLayout.setItemTextSize((int) EaseCommonUtils.sp2px(this.mContext, 16.0f));
        chatMessageListLayout.setItemTextColor(Color.parseColor("#ef000000"));
        chatMessageListLayout.setTimeTextSize((int) EaseCommonUtils.sp2px(this.mContext, 12.0f));
        chatMessageListLayout.setTimeTextColor(Color.parseColor(SkinManager.getCurrentSkinType(this.mContext) == 0 ? "#a6a6a6" : "#575F79"));
        EaseChatInputMenu chatInputMenu = this.chatLayout.getChatInputMenu();
        Drawable drawable = ContextCompat.getDrawable(this.mContext, R.drawable.drawable_chat_send_green);
        if (SkinManager.getCurrentSkinType(this.mContext) == 1) {
            drawable.setColorFilter(Color.parseColor("#171C2D"), PorterDuff.Mode.SRC_IN);
            chatInputMenu.getPrimaryMenu().getEditText().setTextColor(Color.parseColor("#7380A9"));
        } else {
            chatInputMenu.getPrimaryMenu().getEditText().setTextColor(Color.parseColor("#303030"));
        }
        chatInputMenu.getPrimaryMenu().setSendButtonBackground(drawable);
        chatInputMenu.setBackgroundColor(getActivity().getResources().getColor(R.color.white));
        resetChatExtendMenu();
        int i2 = this.chatType;
        if (i2 == 2) {
            ChatRequest.getIntance(getActivity()).chatRecordingTime(getArguments().getString(EaseConstant.EXTRA_CONVERSATION_ID), getArguments().getString("id"), this);
            EMClient.getInstance().userInfoManager().fetchUserInfoByAttribute(new String[]{EMClient.getInstance().getCurrentUser()}, new EMUserInfo.EMUserInfoType[]{EMUserInfo.EMUserInfoType.NICKNAME, EMUserInfo.EMUserInfoType.AVATAR_URL}, new EMValueCallBack<Map<String, EMUserInfo>>() { // from class: com.psychiatrygarden.fragmenthome.chat.MyEaseChatFragment.1
                @Override // com.hyphenate.EMValueCallBack
                public void onError(int error, String errorMsg) {
                }

                @Override // com.hyphenate.EMValueCallBack
                public void onSuccess(Map<String, EMUserInfo> value) {
                    ProjectApp.hxUser.putAll(value);
                }
            });
        } else if (i2 == 1) {
            EMClient.getInstance().userInfoManager().fetchUserInfoByAttribute(new String[]{EMClient.getInstance().getCurrentUser(), getArguments().getString(EaseConstant.EXTRA_CONVERSATION_ID)}, new EMUserInfo.EMUserInfoType[]{EMUserInfo.EMUserInfoType.NICKNAME, EMUserInfo.EMUserInfoType.AVATAR_URL}, new EMValueCallBack<Map<String, EMUserInfo>>() { // from class: com.psychiatrygarden.fragmenthome.chat.MyEaseChatFragment.2
                @Override // com.hyphenate.EMValueCallBack
                public void onError(int error, String errorMsg) {
                }

                @Override // com.hyphenate.EMValueCallBack
                public void onSuccess(Map<String, EMUserInfo> value) {
                    ProjectApp.hxUser.putAll(value);
                }
            });
        }
        EditText editText = this.chatLayout.getChatInputMenu().getPrimaryMenu().getEditText();
        this.editText = editText;
        editText.addTextChangedListener(new TextWatcher() { // from class: com.psychiatrygarden.fragmenthome.chat.MyEaseChatFragment.3
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable s2) {
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence s2, int start, int count, int after) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence s2, int start, int before, int count) {
                if (MyEaseChatFragment.this.chatType == 2 && count == 1 && s2.toString().substring(start, count + start).equals("@")) {
                    Intent intent = new Intent(MyEaseChatFragment.this.getActivity(), (Class<?>) GroupChatRemoveMemberActivity.class);
                    intent.putExtra("id", MyEaseChatFragment.this.getArguments().getString("id"));
                    intent.putExtra("group_img", MyEaseChatFragment.this.getArguments().getString("group_img"));
                    intent.putExtra("@_type", true);
                    intent.putExtra("@_start", start);
                    intent.putExtra("community_id", MyEaseChatFragment.this.getArguments().getString(EaseConstant.EXTRA_CONVERSATION_ID));
                    MyEaseChatFragment.this.requestDataLauncher.launch(intent);
                }
            }
        });
        if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
            this.chatLayout.getChatMessageListLayout().setBackgroundColor(Color.parseColor("#e9e9e9"));
            this.chatLayout.getChatInputMenu().setBackgroundColor(ContextCompat.getColor(this.mContext, R.color.tertiary_backgroup_white_color));
            return;
        }
        this.chatLayout.getChatMessageListLayout().setBackgroundColor(ContextCompat.getColor(this.mContext, R.color.secondary_backgroup_color_night));
        this.chatLayout.getChatInputMenu().setBackgroundColor(ContextCompat.getColor(this.mContext, R.color.secondary_backgroup_color_night));
        IChatExtendMenu chatExtendMenu = this.chatLayout.getChatInputMenu().getChatExtendMenu();
        IChatEmojiconMenu emojiconMenu = this.chatLayout.getChatInputMenu().getEmojiconMenu();
        if (chatExtendMenu instanceof EaseChatExtendMenu) {
            ((EaseChatExtendMenu) chatExtendMenu).setBackground(new ColorDrawable(Color.parseColor("#1C2134")));
        }
        if (emojiconMenu instanceof EaseEmojiconMenu) {
            ((EaseEmojiconMenu) emojiconMenu).setBackground(new ColorDrawable(Color.parseColor("#1C2134")));
        }
    }

    @Override // com.hyphenate.easeui.modules.chat.EaseChatFragment, com.hyphenate.easeui.modules.chat.interfaces.OnChatLayoutListener
    public boolean onBubbleClick(View v2, EMMessage message) {
        if (message.getType() == EMMessage.Type.LOCATION) {
            EMLocationMessageBody eMLocationMessageBody = (EMLocationMessageBody) message.getBody();
            ChatBaiduMapActivity.actionStart(getContext(), eMLocationMessageBody.getLatitude(), eMLocationMessageBody.getLongitude(), eMLocationMessageBody.getAddress());
            return true;
        }
        if (message.getType() != EMMessage.Type.IMAGE) {
            return super.onBubbleClick(v2, message);
        }
        EMImageMessageBody eMImageMessageBody = (EMImageMessageBody) message.getBody();
        try {
            ImageViewerPopupViewCustom xPopupImageLoader = new ImageViewerPopupViewCustom(getActivity()).setSingleSrcView((ImageView) v2.findViewById(R.id.image), eMImageMessageBody.getRemoteUrl()).setXPopupImageLoader(new ImageLoaderUtilsCustom());
            xPopupImageLoader.popupInfo = new PopupInfo();
            xPopupImageLoader.show();
        } catch (Exception e2) {
            ImageViewerPopupViewCustom xPopupImageLoader2 = new ImageViewerPopupViewCustom(getActivity()).setSingleSrcView(null, eMImageMessageBody.getRemoteUrl()).setXPopupImageLoader(new ImageLoaderUtilsCustom());
            xPopupImageLoader2.popupInfo = new PopupInfo();
            xPopupImageLoader2.show();
            e2.printStackTrace();
        }
        return true;
    }

    @Override // com.hyphenate.easeui.modules.chat.EaseChatFragment, com.hyphenate.easeui.modules.chat.interfaces.OnChatLayoutListener
    public void onChatExtendMenuItemClick(View view, int itemId) {
        if (itemId == R.id.extend_item_location) {
            if (ContextCompat.checkSelfPermission(this.mContext, Permission.ACCESS_COARSE_LOCATION) != 0) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Permission.ACCESS_COARSE_LOCATION, Permission.ACCESS_FINE_LOCATION}, 2);
                return;
            } else {
                ChatBaiduMapActivity.actionStartForResult(this, 1);
                return;
            }
        }
        if (itemId != R.id.extend_item_take_picture) {
            super.onChatExtendMenuItemClick(view, itemId);
        } else if (ContextCompat.checkSelfPermission(this.mContext, Permission.CAMERA) != 0) {
            new XPopup.Builder(this.mContext).asCustom(new RequestMediaPermissionPop(this.mContext, new OnConfirmListener() { // from class: com.psychiatrygarden.fragmenthome.chat.a
                @Override // com.lxj.xpopup.interfaces.OnConfirmListener
                public final void onConfirm() {
                    this.f15515a.lambda$onChatExtendMenuItemClick$2();
                }
            })).show();
        } else {
            super.onChatExtendMenuItemClick(view, itemId);
        }
    }

    @Override // com.hyphenate.easeui.modules.chat.EaseChatFragment, androidx.fragment.app.Fragment
    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.requestDataLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback() { // from class: com.psychiatrygarden.fragmenthome.chat.b
            @Override // androidx.activity.result.ActivityResultCallback
            public final void onActivityResult(Object obj) {
                this.f15516a.lambda$onCreateView$0((ActivityResult) obj);
            }
        });
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
    public void onFailure(Throwable t2, int errorNo, String strMsg, String requstUrl) {
    }

    @Override // com.hyphenate.easeui.modules.chat.EaseChatFragment, com.hyphenate.easeui.modules.chat.interfaces.OnChatRecordTouchListener
    public boolean onRecordTouch(View v2, MotionEvent event) {
        if (ContextCompat.checkSelfPermission(this.mContext, Permission.RECORD_AUDIO) == 0) {
            return super.onRecordTouch(v2, event);
        }
        new XPopup.Builder(getContext()).asCustom(new RequestRecordAudioPermissionPop(getContext(), new OnConfirmListener() { // from class: com.psychiatrygarden.fragmenthome.chat.c
            @Override // com.lxj.xpopup.interfaces.OnConfirmListener
            public final void onConfirm() {
                this.f15517a.lambda$onRecordTouch$1();
            }
        })).show();
        return false;
    }

    @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
    public void onStart(String requstUrl) {
    }

    @Override // com.hyphenate.easeui.modules.chat.EaseChatFragment, com.hyphenate.easeui.modules.chat.interfaces.OnChatLayoutListener
    public void onUserAvatarClick(String username) {
        Intent intent = new Intent(getActivity(), (Class<?>) UserCommentInfoActivity.class);
        intent.putExtra("to_user_uuid", username);
        intent.addFlags(67108864);
        startActivity(intent);
        super.onUserAvatarClick(username);
    }

    public void sendReadAck(EMMessage message) {
        EMMessage.Type type;
        if (message.direct() != EMMessage.Direct.RECEIVE || message.isAcked() || message.getChatType() != EMMessage.ChatType.Chat || (type = message.getType()) == EMMessage.Type.VIDEO || type == EMMessage.Type.VOICE || type == EMMessage.Type.FILE) {
            return;
        }
        try {
            EMClient.getInstance().chatManager().ackMessageRead(message.getFrom(), message.getMsgId());
        } catch (HyphenateException e2) {
            e2.printStackTrace();
        }
    }

    @Override // com.hyphenate.easeui.modules.chat.EaseChatFragment, com.hyphenate.easeui.modules.chat.interfaces.OnTranslateMessageListener
    public void translateMessageSuccess(EMMessage message) {
        super.translateMessageSuccess(message);
    }

    @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
    public void onSuccess(String s2, String requstUrl) {
        try {
            if (requstUrl.equals(NetworkRequestsURL.chatDetailApi)) {
                GroupChatDetailBean groupChatDetailBean = (GroupChatDetailBean) new Gson().fromJson(s2, GroupChatDetailBean.class);
                if (groupChatDetailBean.getCode().equals("200")) {
                    getArguments().putString("group_img", groupChatDetailBean.getData().getLogo());
                    GroupChatListBean groupChatListBean = (GroupChatListBean) new Gson().fromJson(SharePreferencesUtils.readStrConfig(CommonParameter.MY_GROUP_CHAT_LIST, getActivity(), ""), GroupChatListBean.class);
                    if (groupChatListBean.getCode().equals("200")) {
                        GroupChatListBean.DataDTO dataDTO = new GroupChatListBean.DataDTO();
                        dataDTO.setName(groupChatDetailBean.getData().getName());
                        dataDTO.setId(groupChatDetailBean.getData().getId());
                        dataDTO.setCommunity_id(groupChatDetailBean.getData().getCommunity_id());
                        dataDTO.setLogo(groupChatDetailBean.getData().getLogo());
                        groupChatListBean.getData().add(dataDTO);
                        SharePreferencesUtils.writeStrConfig(CommonParameter.MY_GROUP_CHAT_LIST, new Gson().toJson(groupChatListBean), getActivity());
                        return;
                    }
                    return;
                }
                return;
            }
            if (requstUrl.equals(NetworkRequestsURL.chatMemberApi)) {
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if (jSONObject.optString("code").equals("200")) {
                        List list = (List) new Gson().fromJson(jSONObject.optString("data"), new TypeToken<List<GroupChatDetailBean.DataDTO.DefaultMemberDTO>>() { // from class: com.psychiatrygarden.fragmenthome.chat.MyEaseChatFragment.5
                        }.getType());
                        HashMap map = new HashMap();
                        for (int i2 = 0; i2 < list.size(); i2++) {
                            EMUserInfo eMUserInfo = new EMUserInfo();
                            eMUserInfo.setNickname(((GroupChatDetailBean.DataDTO.DefaultMemberDTO) list.get(i2)).getNickname());
                            eMUserInfo.setAvatarUrl(((GroupChatDetailBean.DataDTO.DefaultMemberDTO) list.get(i2)).getAvatar());
                            eMUserInfo.setUserId(((GroupChatDetailBean.DataDTO.DefaultMemberDTO) list.get(i2)).getUser_id());
                            map.put(((GroupChatDetailBean.DataDTO.DefaultMemberDTO) list.get(i2)).getUser_uuid(), eMUserInfo);
                        }
                        ProjectApp.hxUser.putAll(map);
                        this.chatLayout.getChatMessageListLayout().getMessageAdapter().notifyDataSetChanged();
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        } catch (Exception e3) {
            e3.printStackTrace();
        }
    }
}
