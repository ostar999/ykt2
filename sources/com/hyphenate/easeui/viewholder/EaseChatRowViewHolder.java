package com.hyphenate.easeui.viewholder;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.adapter.EaseBaseRecyclerViewAdapter;
import com.hyphenate.easeui.interfaces.MessageListItemClickListener;
import com.hyphenate.easeui.widget.chatrow.EaseChatRow;
import java.util.List;

/* loaded from: classes4.dex */
public class EaseChatRowViewHolder extends EaseBaseRecyclerViewAdapter.ViewHolder<EMMessage> implements EaseChatRow.EaseChatRowActionCallback {
    private static final String TAG = "EaseChatRowViewHolder";
    private EaseChatRow chatRow;
    private Context context;
    private MessageListItemClickListener mItemClickListener;
    private EMMessage message;

    public EaseChatRowViewHolder(@NonNull View view, MessageListItemClickListener messageListItemClickListener) {
        super(view);
        view.setLayoutParams(new ViewGroup.LayoutParams(-1, -2));
        this.context = view.getContext();
        this.mItemClickListener = messageListItemClickListener;
    }

    private void handleMessage() {
        if (this.message.direct() == EMMessage.Direct.SEND) {
            handleSendMessage(this.message);
        } else if (this.message.direct() == EMMessage.Direct.RECEIVE) {
            handleReceiveMessage(this.message);
        }
    }

    public EaseChatRow getChatRow() {
        return this.chatRow;
    }

    public Context getContext() {
        return this.context;
    }

    public void handleReceiveMessage(EMMessage eMMessage) {
    }

    public void handleSendMessage(EMMessage eMMessage) {
        getChatRow().updateView(eMMessage);
    }

    @Override // com.hyphenate.easeui.adapter.EaseBaseRecyclerViewAdapter.ViewHolder
    public void initView(View view) {
        this.chatRow = (EaseChatRow) view;
    }

    @Override // com.hyphenate.easeui.widget.chatrow.EaseChatRow.EaseChatRowActionCallback
    public void onBubbleClick(EMMessage eMMessage) {
    }

    @Override // com.hyphenate.easeui.widget.chatrow.EaseChatRow.EaseChatRowActionCallback
    public void onDetachedFromWindow() {
    }

    @Override // com.hyphenate.easeui.widget.chatrow.EaseChatRow.EaseChatRowActionCallback
    public void onResendClick(EMMessage eMMessage) {
    }

    @Override // com.hyphenate.easeui.adapter.EaseBaseRecyclerViewAdapter.ViewHolder
    public void setDataList(List<EMMessage> list, int i2) {
        super.setDataList(list, i2);
        this.chatRow.setTimestamp(i2 == 0 ? null : list.get(i2 - 1));
    }

    @Override // com.hyphenate.easeui.adapter.EaseBaseRecyclerViewAdapter.ViewHolder
    public void setData(EMMessage eMMessage, int i2) throws NumberFormatException {
        this.chatRow.resetViewState();
        this.message = eMMessage;
        this.chatRow.setUpView(eMMessage, i2, this.mItemClickListener, this);
        handleMessage();
    }
}
