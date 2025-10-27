package com.hyphenate.easeui.modules.conversation.delegate;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import androidx.core.content.ContextCompat;
import com.bumptech.glide.Glide;
import com.hyphenate.chat.EMChatRoom;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseIM;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.manager.EaseAtMessageHelper;
import com.hyphenate.easeui.manager.EasePreferenceManager;
import com.hyphenate.easeui.modules.conversation.delegate.EaseDefaultConversationDelegate;
import com.hyphenate.easeui.modules.conversation.model.EaseConversationInfo;
import com.hyphenate.easeui.modules.conversation.model.EaseConversationSetStyle;
import com.hyphenate.easeui.provider.EaseConversationInfoProvider;
import com.hyphenate.easeui.provider.EaseUserProfileProvider;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.hyphenate.easeui.utils.EaseDateUtils;
import com.hyphenate.easeui.utils.EaseSmileUtils;
import java.util.Date;

/* loaded from: classes4.dex */
public class EaseConversationDelegate extends EaseDefaultConversationDelegate {
    public EaseConversationDelegate(EaseConversationSetStyle easeConversationSetStyle) {
        super(easeConversationSetStyle);
    }

    @Override // com.hyphenate.easeui.modules.conversation.delegate.EaseDefaultConversationDelegate
    public void onBindConViewHolder(EaseDefaultConversationDelegate.ViewHolder viewHolder, int i2, EaseConversationInfo easeConversationInfo) {
        int i3;
        String name;
        EaseUserProfileProvider userProvider;
        EaseUser user;
        Drawable defaultTypeAvatar;
        EMConversation eMConversation = (EMConversation) easeConversationInfo.getInfo();
        Context context = viewHolder.itemView.getContext();
        String strConversationId = eMConversation.conversationId();
        viewHolder.listIteaseLayout.setBackground(!TextUtils.isEmpty(eMConversation.getExtField()) ? ContextCompat.getDrawable(context, R.drawable.ease_conversation_top_bg) : null);
        viewHolder.mentioned.setVisibility(8);
        if (eMConversation.getType() == EMConversation.EMConversationType.GroupChat) {
            if (EaseAtMessageHelper.get().hasAtMeMsg(strConversationId)) {
                viewHolder.mentioned.setText(R.string.were_mentioned);
                viewHolder.mentioned.setVisibility(0);
            }
            i3 = R.drawable.ease_group_icon;
            EMGroup group = EMClient.getInstance().groupManager().getGroup(strConversationId);
            name = group != null ? group.getGroupName() : strConversationId;
        } else {
            if (eMConversation.getType() == EMConversation.EMConversationType.ChatRoom) {
                i3 = R.drawable.ease_chat_room_icon;
                EMChatRoom chatRoom = EMClient.getInstance().chatroomManager().getChatRoom(strConversationId);
                if (chatRoom != null && !TextUtils.isEmpty(chatRoom.getName())) {
                    name = chatRoom.getName();
                }
            } else {
                i3 = R.drawable.ease_default_avatar;
            }
        }
        viewHolder.avatar.setImageResource(i3);
        viewHolder.name.setText(name);
        EaseConversationInfoProvider conversationInfoProvider = EaseIM.getInstance().getConversationInfoProvider();
        if (conversationInfoProvider != null && (defaultTypeAvatar = conversationInfoProvider.getDefaultTypeAvatar(eMConversation.getType().name())) != null) {
            Glide.with(viewHolder.mContext).load(defaultTypeAvatar).error(i3).into(viewHolder.avatar);
        }
        if (eMConversation.getType() == EMConversation.EMConversationType.Chat && (userProvider = EaseIM.getInstance().getUserProvider()) != null && (user = userProvider.getUser(strConversationId)) != null) {
            if (!TextUtils.isEmpty(user.getNickname())) {
                viewHolder.name.setText(user.getNickname());
            }
            if (!TextUtils.isEmpty(user.getAvatar())) {
                Glide.with(viewHolder.mContext).load(user.getAvatar()).error(viewHolder.avatar.getDrawable()).into(viewHolder.avatar);
            }
        }
        if (!this.setModel.isHideUnreadDot()) {
            showUnreadNum(viewHolder, eMConversation.getUnreadMsgCount());
        }
        if (eMConversation.getAllMsgCount() != 0) {
            EMMessage lastMessage = eMConversation.getLastMessage();
            viewHolder.message.setText(EaseSmileUtils.getSmiledText(context, EaseCommonUtils.getMessageDigest(lastMessage, context)));
            viewHolder.time.setText(EaseDateUtils.getTimestampString(context, new Date(lastMessage.getMsgTime())));
            if (lastMessage.direct() == EMMessage.Direct.SEND && lastMessage.status() == EMMessage.Status.FAIL) {
                viewHolder.mMsgState.setVisibility(0);
            } else {
                viewHolder.mMsgState.setVisibility(8);
            }
        }
        if (viewHolder.mentioned.getVisibility() != 0) {
            String unSendMsgInfo = EasePreferenceManager.getInstance().getUnSendMsgInfo(strConversationId);
            if (TextUtils.isEmpty(unSendMsgInfo)) {
                return;
            }
            viewHolder.mentioned.setText(R.string.were_not_send_msg);
            viewHolder.message.setText(unSendMsgInfo);
            viewHolder.mentioned.setVisibility(0);
        }
    }

    @Override // com.hyphenate.easeui.adapter.EaseAdapterDelegate
    public boolean isForViewType(EaseConversationInfo easeConversationInfo, int i2) {
        return easeConversationInfo != null && (easeConversationInfo.getInfo() instanceof EMConversation);
    }
}
