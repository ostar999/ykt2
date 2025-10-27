package com.easefun.polyv.livecloudclass.modules.chatroom.adapter.holder;

import android.view.View;
import android.widget.TextView;
import com.easefun.polyv.livecloudclass.R;
import com.easefun.polyv.livecloudclass.modules.chatroom.adapter.PLVLCMessageAdapter;
import com.easefun.polyv.livecommon.module.modules.chatroom.holder.PLVChatMessageBaseViewHolder;
import com.easefun.polyv.livecommon.ui.widget.itemview.PLVBaseViewData;
import com.plv.socket.event.chat.PLVRewardEvent;
import com.plv.thirdpart.blankj.utilcode.util.ScreenUtils;

/* loaded from: classes3.dex */
public class PLVLCRewardViewHolder extends PLVChatMessageBaseViewHolder<PLVBaseViewData, PLVLCMessageAdapter> {
    private TextView tvMessageLandscape;
    private TextView tvMessagePortrait;

    public PLVLCRewardViewHolder(View view, PLVLCMessageAdapter pLVLCMessageAdapter) {
        super(view, pLVLCMessageAdapter);
        this.tvMessagePortrait = (TextView) view.findViewById(R.id.plvlc_tv_reward_message_portrait);
        this.tvMessageLandscape = (TextView) view.findViewById(R.id.plvlc_tv_reward_message_land);
    }

    @Override // com.easefun.polyv.livecommon.module.modules.chatroom.holder.PLVChatMessageBaseViewHolder, com.easefun.polyv.livecommon.ui.widget.itemview.holder.PLVBaseViewHolder
    public void processData(PLVBaseViewData pLVBaseViewData, int i2) {
        super.processData(pLVBaseViewData, i2);
        Object obj = this.messageData;
        if (obj instanceof PLVRewardEvent) {
            PLVRewardEvent pLVRewardEvent = (PLVRewardEvent) obj;
            if (this.tvMessagePortrait != null) {
                if (pLVRewardEvent.getObjects() != null) {
                    this.tvMessagePortrait.setText((CharSequence) pLVRewardEvent.getObjects()[0]);
                }
            } else if (ScreenUtils.isLandscape()) {
                this.itemView.setVisibility(8);
            }
        }
    }
}
