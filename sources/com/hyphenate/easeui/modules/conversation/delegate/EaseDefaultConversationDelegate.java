package com.hyphenate.easeui.modules.conversation.delegate;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.adapter.EaseBaseRecyclerViewAdapter;
import com.hyphenate.easeui.modules.conversation.model.EaseConversationInfo;
import com.hyphenate.easeui.modules.conversation.model.EaseConversationSetStyle;
import com.hyphenate.easeui.utils.DarkModeHelper;
import com.hyphenate.easeui.utils.EaseUserUtils;
import com.hyphenate.easeui.widget.EaseImageView;

/* loaded from: classes4.dex */
public abstract class EaseDefaultConversationDelegate extends EaseBaseConversationDelegate<EaseConversationInfo, ViewHolder> {

    public static class ViewHolder extends EaseBaseRecyclerViewAdapter.ViewHolder<EaseConversationInfo> {
        public EaseImageView avatar;
        private final Drawable bgDrawable;
        public ConstraintLayout listIteaseLayout;
        public Context mContext;
        public ImageView mMsgState;
        public TextView mUnreadMsgNumber;
        public TextView mentioned;
        public TextView message;
        public TextView name;
        public TextView time;
        public TextView unreadMsgNumberRight;
        public TextView unread_msg_point;
        public View view_line;

        public ViewHolder(@NonNull View view, EaseConversationSetStyle easeConversationSetStyle) {
            super(view);
            this.mContext = view.getContext();
            this.listIteaseLayout = (ConstraintLayout) findViewById(R.id.list_itease_layout);
            this.avatar = (EaseImageView) findViewById(R.id.avatar);
            this.mUnreadMsgNumber = (TextView) findViewById(R.id.unread_msg_number);
            this.unreadMsgNumberRight = (TextView) findViewById(R.id.unread_msg_number_right);
            this.unread_msg_point = (TextView) findViewById(R.id.unread_msg_point);
            this.name = (TextView) findViewById(R.id.name);
            this.time = (TextView) findViewById(R.id.time);
            this.mMsgState = (ImageView) findViewById(R.id.msg_state);
            this.mentioned = (TextView) findViewById(R.id.mentioned);
            this.message = (TextView) findViewById(R.id.message);
            this.view_line = findViewById(R.id.view_line);
            EaseUserUtils.setUserAvatarStyle(this.avatar);
            if (easeConversationSetStyle != null) {
                float titleTextSize = easeConversationSetStyle.getTitleTextSize();
                if (titleTextSize != 0.0f) {
                    this.name.setTextSize(0, titleTextSize);
                }
                int titleTextColor = easeConversationSetStyle.getTitleTextColor();
                if (titleTextColor != 0) {
                    this.name.setTextColor(titleTextColor);
                }
                float contentTextSize = easeConversationSetStyle.getContentTextSize();
                if (contentTextSize != 0.0f) {
                    this.message.setTextSize(0, contentTextSize);
                }
                int contentTextColor = easeConversationSetStyle.getContentTextColor();
                if (contentTextColor != 0) {
                    this.message.setTextColor(contentTextColor);
                }
                float dateTextSize = easeConversationSetStyle.getDateTextSize();
                if (dateTextSize != 0.0f) {
                    this.time.setTextSize(0, dateTextSize);
                }
                int dateTextColor = easeConversationSetStyle.getDateTextColor();
                if (dateTextColor != 0) {
                    this.time.setTextColor(dateTextColor);
                }
                float mentionTextSize = easeConversationSetStyle.getMentionTextSize();
                if (mentionTextSize != 0.0f) {
                    this.mentioned.setTextSize(0, mentionTextSize);
                }
                int mentionTextColor = easeConversationSetStyle.getMentionTextColor();
                if (mentionTextColor != 0) {
                    this.mentioned.setTextColor(mentionTextColor);
                }
                float avatarSize = easeConversationSetStyle.getAvatarSize();
                if (avatarSize != 0.0f) {
                    ViewGroup.LayoutParams layoutParams = this.avatar.getLayoutParams();
                    int i2 = (int) avatarSize;
                    layoutParams.height = i2;
                    layoutParams.width = i2;
                }
                this.avatar.setShapeType(easeConversationSetStyle.getShapeType());
                float borderWidth = easeConversationSetStyle.getBorderWidth();
                if (borderWidth != 0.0f) {
                    this.avatar.setBorderWidth((int) borderWidth);
                }
                int borderColor = easeConversationSetStyle.getBorderColor();
                if (borderColor != 0) {
                    this.avatar.setBorderColor(borderColor);
                }
                float avatarRadius = easeConversationSetStyle.getAvatarRadius();
                if (avatarRadius != 0.0f) {
                    this.avatar.setRadius((int) avatarRadius);
                }
                float itemHeight = easeConversationSetStyle.getItemHeight();
                if (itemHeight != 0.0f) {
                    view.getLayoutParams().height = (int) itemHeight;
                }
                Drawable bgDrawable = easeConversationSetStyle.getBgDrawable();
                if (bgDrawable != null) {
                    view.setBackground(bgDrawable);
                }
                this.mUnreadMsgNumber.setVisibility(easeConversationSetStyle.isHideUnreadDot() ? 8 : 0);
                if (easeConversationSetStyle.getUnreadDotPosition() == EaseConversationSetStyle.UnreadDotPosition.LEFT) {
                    this.mUnreadMsgNumber.setVisibility(0);
                    this.unreadMsgNumberRight.setVisibility(8);
                } else {
                    this.mUnreadMsgNumber.setVisibility(8);
                    this.unreadMsgNumberRight.setVisibility(0);
                }
            }
            this.bgDrawable = view.getBackground();
        }

        @Override // com.hyphenate.easeui.adapter.EaseBaseRecyclerViewAdapter.ViewHolder
        public void initView(View view) {
        }

        @Override // com.hyphenate.easeui.adapter.EaseBaseRecyclerViewAdapter.ViewHolder
        public void setData(final EaseConversationInfo easeConversationInfo, int i2) {
            easeConversationInfo.setOnSelectListener(new EaseConversationInfo.OnSelectListener() { // from class: com.hyphenate.easeui.modules.conversation.delegate.EaseDefaultConversationDelegate.ViewHolder.1
                @Override // com.hyphenate.easeui.modules.conversation.model.EaseConversationInfo.OnSelectListener
                public void onSelect(boolean z2) {
                    if (z2) {
                        if (DarkModeHelper.isDarkMode(ViewHolder.this.mContext)) {
                            return;
                        }
                        ViewHolder.this.itemView.setBackgroundResource(R.drawable.ease_conversation_item_selected);
                    } else if (!easeConversationInfo.isTop()) {
                        ViewHolder viewHolder = ViewHolder.this;
                        viewHolder.itemView.setBackground(viewHolder.bgDrawable);
                    } else if (DarkModeHelper.isDarkMode(ViewHolder.this.mContext)) {
                        ViewHolder.this.itemView.setBackgroundResource(R.drawable.ease_conversation_top_bg_night);
                    } else {
                        ViewHolder.this.itemView.setBackgroundResource(R.drawable.ease_conversation_top_bg);
                    }
                }
            });
        }
    }

    public EaseDefaultConversationDelegate(EaseConversationSetStyle easeConversationSetStyle) {
        super(easeConversationSetStyle);
    }

    public String handleBigNum(int i2) {
        return i2 <= 99 ? String.valueOf(i2) : "···";
    }

    public abstract void onBindConViewHolder(ViewHolder viewHolder, int i2, EaseConversationInfo easeConversationInfo);

    public void showUnreadNum(ViewHolder viewHolder, int i2) {
        if (i2 <= 0) {
            viewHolder.mUnreadMsgNumber.setVisibility(8);
            viewHolder.unreadMsgNumberRight.setVisibility(8);
            return;
        }
        viewHolder.mUnreadMsgNumber.setVisibility(0);
        viewHolder.unreadMsgNumberRight.setVisibility(0);
        viewHolder.mUnreadMsgNumber.setText(handleBigNum(i2));
        viewHolder.unreadMsgNumberRight.setText(handleBigNum(i2));
        showUnreadRight(viewHolder, this.setModel.getUnreadDotPosition() == EaseConversationSetStyle.UnreadDotPosition.RIGHT);
    }

    public void showUnreadRight(ViewHolder viewHolder, boolean z2) {
        if (z2) {
            viewHolder.mUnreadMsgNumber.setVisibility(8);
            viewHolder.unreadMsgNumberRight.setVisibility(0);
        } else {
            viewHolder.mUnreadMsgNumber.setVisibility(0);
            viewHolder.unreadMsgNumberRight.setVisibility(8);
        }
    }

    @Override // com.hyphenate.easeui.adapter.EaseAdapterDelegate
    public void onBindViewHolder(ViewHolder viewHolder, int i2, EaseConversationInfo easeConversationInfo) {
        super.onBindViewHolder((EaseDefaultConversationDelegate) viewHolder, i2, (int) easeConversationInfo);
        onBindConViewHolder(viewHolder, i2, easeConversationInfo);
    }

    @Override // com.hyphenate.easeui.adapter.EaseAdapterDelegate
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, String str) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.ease_item_row_chat_history, viewGroup, false), this.setModel);
    }
}
