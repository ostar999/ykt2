package com.hyphenate.easeui.delegate;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import com.hyphenate.chat.EMChatRoom;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseIM;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.adapter.EaseBaseDelegate;
import com.hyphenate.easeui.adapter.EaseBaseRecyclerViewAdapter;
import com.hyphenate.easeui.domain.EaseAvatarOptions;
import com.hyphenate.easeui.manager.EaseAtMessageHelper;
import com.hyphenate.easeui.manager.EasePreferenceManager;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.hyphenate.easeui.utils.EaseDateUtils;
import com.hyphenate.easeui.utils.EaseSmileUtils;
import com.hyphenate.easeui.widget.EaseImageView;
import java.util.Date;

/* loaded from: classes4.dex */
public class ConversationDelegate extends EaseBaseDelegate<EMConversation, ViewHolder> {

    public class ViewHolder extends EaseBaseRecyclerViewAdapter.ViewHolder<EMConversation> {
        private EaseImageView avatar;
        private ConstraintLayout listIteaseLayout;
        private Context mContext;
        private ImageView mMsgState;
        private TextView mUnreadMsgNumber;
        private TextView mentioned;
        private TextView message;
        private TextView name;
        private TextView time;

        public ViewHolder(@NonNull View view) {
            super(view);
        }

        @Override // com.hyphenate.easeui.adapter.EaseBaseRecyclerViewAdapter.ViewHolder
        public void initView(View view) {
            this.mContext = view.getContext();
            this.listIteaseLayout = (ConstraintLayout) findViewById(R.id.list_itease_layout);
            this.avatar = (EaseImageView) findViewById(R.id.avatar);
            this.mUnreadMsgNumber = (TextView) findViewById(R.id.unread_msg_number);
            this.name = (TextView) findViewById(R.id.name);
            this.time = (TextView) findViewById(R.id.time);
            this.mMsgState = (ImageView) findViewById(R.id.msg_state);
            this.mentioned = (TextView) findViewById(R.id.mentioned);
            this.message = (TextView) findViewById(R.id.message);
            EaseAvatarOptions avatarOptions = EaseIM.getInstance().getAvatarOptions();
            if (avatarOptions != null) {
                this.avatar.setShapeType(avatarOptions.getAvatarShape());
            }
        }

        @Override // com.hyphenate.easeui.adapter.EaseBaseRecyclerViewAdapter.ViewHolder
        public void setData(EMConversation eMConversation, int i2) {
            String strConversationId = eMConversation.conversationId();
            this.listIteaseLayout.setBackground(!TextUtils.isEmpty(eMConversation.getExtField()) ? ContextCompat.getDrawable(this.mContext, R.drawable.ease_conversation_top_bg) : null);
            this.mentioned.setVisibility(8);
            if (eMConversation.getType() == EMConversation.EMConversationType.GroupChat) {
                if (EaseAtMessageHelper.get().hasAtMeMsg(strConversationId)) {
                    this.mentioned.setText(R.string.were_mentioned);
                    this.mentioned.setVisibility(0);
                }
                this.avatar.setImageResource(R.drawable.ease_group_icon);
                EMGroup group = EMClient.getInstance().groupManager().getGroup(strConversationId);
                this.name.setText(group != null ? group.getGroupName() : strConversationId);
            } else if (eMConversation.getType() == EMConversation.EMConversationType.ChatRoom) {
                this.avatar.setImageResource(R.drawable.ease_chat_room_icon);
                EMChatRoom chatRoom = EMClient.getInstance().chatroomManager().getChatRoom(strConversationId);
                this.name.setText((chatRoom == null || TextUtils.isEmpty(chatRoom.getName())) ? strConversationId : chatRoom.getName());
            } else {
                this.avatar.setImageResource(R.drawable.ease_default_avatar);
                this.name.setText(strConversationId);
            }
            if (eMConversation.getUnreadMsgCount() > 0) {
                this.mUnreadMsgNumber.setText(String.valueOf(eMConversation.getUnreadMsgCount()));
                this.mUnreadMsgNumber.setVisibility(0);
            } else {
                this.mUnreadMsgNumber.setVisibility(8);
            }
            if (eMConversation.getAllMsgCount() != 0) {
                EMMessage lastMessage = eMConversation.getLastMessage();
                TextView textView = this.message;
                Context context = this.mContext;
                textView.setText(EaseSmileUtils.getSmiledText(context, EaseCommonUtils.getMessageDigest(lastMessage, context)));
                this.time.setText(EaseDateUtils.getTimestampString(this.mContext, new Date(lastMessage.getMsgTime())));
                if (lastMessage.direct() == EMMessage.Direct.SEND && lastMessage.status() == EMMessage.Status.FAIL) {
                    this.mMsgState.setVisibility(0);
                } else {
                    this.mMsgState.setVisibility(8);
                }
            }
            if (this.mentioned.getVisibility() != 0) {
                String unSendMsgInfo = EasePreferenceManager.getInstance().getUnSendMsgInfo(strConversationId);
                if (TextUtils.isEmpty(unSendMsgInfo)) {
                    return;
                }
                this.mentioned.setText(R.string.were_not_send_msg);
                this.message.setText(unSendMsgInfo);
                this.mentioned.setVisibility(0);
            }
        }
    }

    @Override // com.hyphenate.easeui.adapter.EaseBaseDelegate
    public int getLayoutId() {
        return R.layout.ease_item_row_chat_history;
    }

    @Override // com.hyphenate.easeui.adapter.EaseAdapterDelegate
    public boolean isForViewType(EMConversation eMConversation, int i2) {
        return eMConversation != null;
    }

    @Override // com.hyphenate.easeui.adapter.EaseBaseDelegate
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }
}
