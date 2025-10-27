package com.psychiatrygarden.fragmenthome.chat;

import android.os.Handler;
import android.os.Message;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.easeui.modules.conversation.delegate.EaseDefaultConversationDelegate;
import com.hyphenate.easeui.modules.conversation.model.EaseConversationInfo;
import com.hyphenate.easeui.modules.conversation.model.EaseConversationSetStyle;

/* loaded from: classes5.dex */
public class YkbEaseConversationDelegate extends EaseDefaultConversationDelegate {
    Handler mHandler;

    public YkbEaseConversationDelegate(EaseConversationSetStyle setModel) {
        super(setModel);
        this.mHandler = new Handler() { // from class: com.psychiatrygarden.fragmenthome.chat.YkbEaseConversationDelegate.1
            @Override // android.os.Handler
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                int i2 = msg.what;
                if (i2 != 0) {
                    if (i2 != 1) {
                        return;
                    }
                    YkbEaseConversationDelegate.this.showUnreadNum((EaseDefaultConversationDelegate.ViewHolder) msg.obj, msg.arg1);
                } else {
                    EaseDefaultConversationDelegate.ViewHolder viewHolder = (EaseDefaultConversationDelegate.ViewHolder) msg.obj;
                    viewHolder.unread_msg_point.setVisibility(0);
                    viewHolder.mUnreadMsgNumber.setVisibility(8);
                    viewHolder.unreadMsgNumberRight.setVisibility(8);
                }
            }
        };
    }

    /* JADX WARN: Code restructure failed: missing block: B:28:0x00cb, code lost:
    
        r12.name.setText(r1.getData().get(r4).getName());
     */
    @Override // com.hyphenate.easeui.modules.conversation.delegate.EaseDefaultConversationDelegate
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onBindConViewHolder(final com.hyphenate.easeui.modules.conversation.delegate.EaseDefaultConversationDelegate.ViewHolder r12, int r13, com.hyphenate.easeui.modules.conversation.model.EaseConversationInfo r14) throws android.content.res.Resources.NotFoundException {
        /*
            Method dump skipped, instructions count: 1106
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.psychiatrygarden.fragmenthome.chat.YkbEaseConversationDelegate.onBindConViewHolder(com.hyphenate.easeui.modules.conversation.delegate.EaseDefaultConversationDelegate$ViewHolder, int, com.hyphenate.easeui.modules.conversation.model.EaseConversationInfo):void");
    }

    @Override // com.hyphenate.easeui.adapter.EaseAdapterDelegate
    public boolean isForViewType(EaseConversationInfo item, int position) {
        return item != null && (item.getInfo() instanceof EMConversation);
    }
}
