package com.hyphenate.easeui.modules.contact.adapter;

import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import com.bumptech.glide.Glide;
import com.hyphenate.easeui.EaseIM;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.adapter.EaseBaseRecyclerViewAdapter;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.modules.contact.model.EaseContactSetStyle;
import com.hyphenate.easeui.provider.EaseUserProfileProvider;
import com.hyphenate.easeui.utils.EaseUserUtils;
import com.hyphenate.easeui.widget.EaseImageView;

/* loaded from: classes4.dex */
public class EaseContactListAdapter extends EaseBaseRecyclerViewAdapter<EaseUser> {
    private EaseContactSetStyle contactSetModel;
    private int emptyLayoutResource;

    public class ContactViewHolder extends EaseBaseRecyclerViewAdapter.ViewHolder<EaseUser> {
        private ConstraintLayout clUser;
        private EaseImageView mAvatar;
        private TextView mHeader;
        private TextView mName;
        private TextView mSignature;
        private TextView mUnreadMsgNumber;

        public ContactViewHolder(@NonNull View view) {
            super(view);
        }

        @Override // com.hyphenate.easeui.adapter.EaseBaseRecyclerViewAdapter.ViewHolder
        public void initView(View view) {
            this.mHeader = (TextView) findViewById(R.id.header);
            this.mAvatar = (EaseImageView) findViewById(R.id.avatar);
            this.mName = (TextView) findViewById(R.id.name);
            this.mSignature = (TextView) findViewById(R.id.signature);
            this.mUnreadMsgNumber = (TextView) findViewById(R.id.unread_msg_number);
            this.clUser = (ConstraintLayout) findViewById(R.id.cl_user);
            EaseUserUtils.setUserAvatarStyle(this.mAvatar);
            if (EaseContactListAdapter.this.contactSetModel != null) {
                float headerTextSize = EaseContactListAdapter.this.contactSetModel.getHeaderTextSize();
                if (headerTextSize != 0.0f) {
                    this.mHeader.setTextSize(0, headerTextSize);
                }
                int headerTextColor = EaseContactListAdapter.this.contactSetModel.getHeaderTextColor();
                if (headerTextColor != 0) {
                    this.mHeader.setTextColor(headerTextColor);
                }
                Drawable headerBgDrawable = EaseContactListAdapter.this.contactSetModel.getHeaderBgDrawable();
                if (headerBgDrawable != null) {
                    this.mHeader.setBackground(headerBgDrawable);
                }
                float titleTextSize = EaseContactListAdapter.this.contactSetModel.getTitleTextSize();
                if (titleTextSize != 0.0f) {
                    this.mName.setTextSize(0, titleTextSize);
                }
                int titleTextColor = EaseContactListAdapter.this.contactSetModel.getTitleTextColor();
                if (titleTextColor != 0) {
                    this.mName.setTextColor(titleTextColor);
                }
                Drawable avatarDefaultSrc = EaseContactListAdapter.this.contactSetModel.getAvatarDefaultSrc();
                if (avatarDefaultSrc != null) {
                    this.mAvatar.setImageDrawable(avatarDefaultSrc);
                }
                float avatarRadius = EaseContactListAdapter.this.contactSetModel.getAvatarRadius();
                if (avatarRadius != 0.0f) {
                    this.mAvatar.setRadius((int) avatarRadius);
                }
                float borderWidth = EaseContactListAdapter.this.contactSetModel.getBorderWidth();
                if (borderWidth != 0.0f) {
                    this.mAvatar.setBorderWidth((int) borderWidth);
                }
                int borderColor = EaseContactListAdapter.this.contactSetModel.getBorderColor();
                if (borderColor != 0) {
                    this.mAvatar.setBorderColor(borderColor);
                }
                this.mAvatar.setShapeType(EaseContactListAdapter.this.contactSetModel.getShapeType());
                float avatarSize = EaseContactListAdapter.this.contactSetModel.getAvatarSize();
                if (avatarSize != 0.0f) {
                    ViewGroup.LayoutParams layoutParams = this.mAvatar.getLayoutParams();
                    int i2 = (int) avatarSize;
                    layoutParams.height = i2;
                    layoutParams.width = i2;
                }
                float itemHeight = EaseContactListAdapter.this.contactSetModel.getItemHeight();
                if (itemHeight != 0.0f) {
                    this.clUser.getLayoutParams().height = (int) itemHeight;
                }
                this.clUser.setBackground(EaseContactListAdapter.this.contactSetModel.getBgDrawable());
            }
        }

        @Override // com.hyphenate.easeui.adapter.EaseBaseRecyclerViewAdapter.ViewHolder
        public void setData(EaseUser easeUser, int i2) {
            EaseUser user;
            EaseUserProfileProvider userProvider = EaseIM.getInstance().getUserProvider();
            if (userProvider != null && (user = userProvider.getUser(easeUser.getUsername())) != null) {
                easeUser = user;
            }
            String initialLetter = easeUser.getInitialLetter();
            this.mHeader.setVisibility(8);
            if ((i2 == 0 || (initialLetter != null && !initialLetter.equals(EaseContactListAdapter.this.getItem(i2 - 1).getInitialLetter()))) && !TextUtils.isEmpty(initialLetter)) {
                this.mHeader.setVisibility(0);
                if (EaseContactListAdapter.this.contactSetModel != null) {
                    this.mHeader.setVisibility(EaseContactListAdapter.this.contactSetModel.isShowItemHeader() ? 0 : 8);
                }
                this.mHeader.setText(initialLetter);
            }
            this.mName.setText(easeUser.getNickname());
            Glide.with(EaseContactListAdapter.this.mContext).load(easeUser.getAvatar()).error(EaseContactListAdapter.this.contactSetModel.getAvatarDefaultSrc() != null ? EaseContactListAdapter.this.contactSetModel.getAvatarDefaultSrc() : ContextCompat.getDrawable(EaseContactListAdapter.this.mContext, R.drawable.ease_default_avatar)).into(this.mAvatar);
        }
    }

    @Override // com.hyphenate.easeui.adapter.EaseBaseRecyclerViewAdapter
    public int getEmptyLayoutId() {
        int i2 = this.emptyLayoutResource;
        return i2 != 0 ? i2 : R.layout.ease_layout_no_data_show_nothing;
    }

    @Override // com.hyphenate.easeui.adapter.EaseBaseRecyclerViewAdapter
    public EaseBaseRecyclerViewAdapter.ViewHolder getViewHolder(ViewGroup viewGroup, int i2) {
        return new ContactViewHolder(LayoutInflater.from(this.mContext).inflate(R.layout.ease_widget_contact_item, viewGroup, false));
    }

    public void setEmptyLayoutResource(int i2) {
        this.emptyLayoutResource = i2;
    }

    public void setSettingModel(EaseContactSetStyle easeContactSetStyle) {
        this.contactSetModel = easeContactSetStyle;
    }
}
