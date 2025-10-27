package com.psychiatrygarden.fragmenthome.chat;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import androidx.core.content.ContextCompat;
import com.bumptech.glide.Glide;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseIM;
import com.hyphenate.easeui.constants.EaseConstant;
import com.hyphenate.easeui.manager.EaseSystemMsgManager;
import com.hyphenate.easeui.modules.conversation.delegate.EaseDefaultConversationDelegate;
import com.hyphenate.easeui.modules.conversation.model.EaseConversationInfo;
import com.hyphenate.easeui.modules.conversation.model.EaseConversationSetStyle;
import com.hyphenate.easeui.provider.EaseConversationInfoProvider;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.hyphenate.easeui.utils.EaseSmileUtils;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.YKBDateUtils;
import com.yikaobang.yixue.R;
import java.util.Date;

/* loaded from: classes5.dex */
public class YkbEaseSystemMsgDelegate extends EaseDefaultConversationDelegate {
    public YkbEaseSystemMsgDelegate(EaseConversationSetStyle setModel) {
        super(setModel);
    }

    @Override // com.hyphenate.easeui.modules.conversation.delegate.EaseDefaultConversationDelegate
    public void onBindConViewHolder(EaseDefaultConversationDelegate.ViewHolder holder, int position, EaseConversationInfo bean) {
        Drawable defaultTypeAvatar;
        EMConversation eMConversation = (EMConversation) bean.getInfo();
        Context context = holder.itemView.getContext();
        eMConversation.conversationId();
        holder.listIteaseLayout.setBackground(!TextUtils.isEmpty(eMConversation.getExtField()) ? ContextCompat.getDrawable(context, R.drawable.ease_conversation_top_bg) : null);
        holder.mentioned.setVisibility(8);
        EaseConversationInfoProvider conversationInfoProvider = EaseIM.getInstance().getConversationInfoProvider();
        holder.avatar.setImageResource(R.drawable.em_system_nofinication);
        holder.name.setText(holder.mContext.getString(R.string.ease_conversation_system_message));
        if (conversationInfoProvider != null && (defaultTypeAvatar = conversationInfoProvider.getDefaultTypeAvatar(EaseConstant.DEFAULT_SYSTEM_MESSAGE_TYPE)) != null) {
            Glide.with(holder.mContext).load(defaultTypeAvatar).placeholder(new ColorDrawable(ContextCompat.getColor(holder.mContext, SkinManager.getCurrentSkinType(ProjectApp.instance()) == 0 ? R.color.fourth_line_backgroup_color : R.color.bg_backgroud_night))).error(R.drawable.em_system_nofinication).into(holder.avatar);
        }
        if (!this.setModel.isHideUnreadDot()) {
            showUnreadNum(holder, eMConversation.getUnreadMsgCount());
        }
        if (eMConversation.getAllMsgCount() != 0) {
            EMMessage lastMessage = eMConversation.getLastMessage();
            holder.message.setText(EaseSmileUtils.getSmiledText(context, EaseCommonUtils.getMessageDigest(lastMessage, context)));
            holder.time.setText(YKBDateUtils.getTimestampString(holder.itemView.getContext(), new Date(lastMessage.getMsgTime())));
        }
    }

    @Override // com.hyphenate.easeui.adapter.EaseAdapterDelegate
    public boolean isForViewType(EaseConversationInfo item, int position) {
        return item != null && (item.getInfo() instanceof EMConversation) && EaseSystemMsgManager.getInstance().isSystemConversation((EMConversation) item.getInfo());
    }
}
