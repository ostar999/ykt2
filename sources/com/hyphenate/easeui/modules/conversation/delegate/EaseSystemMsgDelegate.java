package com.hyphenate.easeui.modules.conversation.delegate;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import androidx.core.content.ContextCompat;
import com.bumptech.glide.Glide;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseIM;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.constants.EaseConstant;
import com.hyphenate.easeui.manager.EaseSystemMsgManager;
import com.hyphenate.easeui.modules.conversation.delegate.EaseDefaultConversationDelegate;
import com.hyphenate.easeui.modules.conversation.model.EaseConversationInfo;
import com.hyphenate.easeui.modules.conversation.model.EaseConversationSetStyle;
import com.hyphenate.easeui.provider.EaseConversationInfoProvider;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.hyphenate.easeui.utils.EaseDateUtils;
import com.hyphenate.easeui.utils.EaseSmileUtils;
import com.hyphenate.easeui.widget.EaseImageView;
import java.util.Date;

/* loaded from: classes4.dex */
public class EaseSystemMsgDelegate extends EaseDefaultConversationDelegate {
    public EaseSystemMsgDelegate(EaseConversationSetStyle easeConversationSetStyle) {
        super(easeConversationSetStyle);
    }

    @Override // com.hyphenate.easeui.modules.conversation.delegate.EaseDefaultConversationDelegate
    public void onBindConViewHolder(EaseDefaultConversationDelegate.ViewHolder viewHolder, int i2, EaseConversationInfo easeConversationInfo) {
        Drawable defaultTypeAvatar;
        EMConversation eMConversation = (EMConversation) easeConversationInfo.getInfo();
        Context context = viewHolder.itemView.getContext();
        eMConversation.conversationId();
        viewHolder.listIteaseLayout.setBackground(!TextUtils.isEmpty(eMConversation.getExtField()) ? ContextCompat.getDrawable(context, R.drawable.ease_conversation_top_bg) : null);
        viewHolder.mentioned.setVisibility(8);
        EaseConversationInfoProvider conversationInfoProvider = EaseIM.getInstance().getConversationInfoProvider();
        EaseImageView easeImageView = viewHolder.avatar;
        int i3 = R.drawable.em_system_nofinication;
        easeImageView.setImageResource(i3);
        viewHolder.name.setText(viewHolder.mContext.getString(R.string.ease_conversation_system_message));
        if (conversationInfoProvider != null && (defaultTypeAvatar = conversationInfoProvider.getDefaultTypeAvatar(EaseConstant.DEFAULT_SYSTEM_MESSAGE_TYPE)) != null) {
            Glide.with(viewHolder.mContext).load(defaultTypeAvatar).error(i3).into(viewHolder.avatar);
        }
        if (!this.setModel.isHideUnreadDot()) {
            showUnreadNum(viewHolder, eMConversation.getUnreadMsgCount());
        }
        if (eMConversation.getAllMsgCount() != 0) {
            EMMessage lastMessage = eMConversation.getLastMessage();
            viewHolder.message.setText(EaseSmileUtils.getSmiledText(context, EaseCommonUtils.getMessageDigest(lastMessage, context)));
            viewHolder.time.setText(EaseDateUtils.getTimestampString(viewHolder.itemView.getContext(), new Date(lastMessage.getMsgTime())));
        }
    }

    @Override // com.hyphenate.easeui.adapter.EaseAdapterDelegate
    public boolean isForViewType(EaseConversationInfo easeConversationInfo, int i2) {
        return easeConversationInfo != null && (easeConversationInfo.getInfo() instanceof EMConversation) && EaseSystemMsgManager.getInstance().isSystemConversation((EMConversation) easeConversationInfo.getInfo());
    }
}
