package com.hyphenate.easeui.modules.contact.adapter;

import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.bumptech.glide.Glide;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.adapter.EaseBaseRecyclerViewAdapter;
import com.hyphenate.easeui.modules.contact.model.EaseContactCustomBean;
import com.hyphenate.easeui.modules.contact.model.EaseContactSetStyle;
import com.hyphenate.easeui.widget.EaseImageView;

/* loaded from: classes4.dex */
public class EaseContactCustomAdapter extends EaseBaseRecyclerViewAdapter<EaseContactCustomBean> {
    private EaseContactSetStyle contactSetModel;

    public class CustomViewHolder extends EaseBaseRecyclerViewAdapter.ViewHolder<EaseContactCustomBean> {
        private ConstraintLayout clUser;
        private EaseImageView mAvatar;
        private TextView mHeader;
        private TextView mName;

        public CustomViewHolder(@NonNull View view) {
            super(view);
        }

        @Override // com.hyphenate.easeui.adapter.EaseBaseRecyclerViewAdapter.ViewHolder
        public void initView(View view) {
            this.mHeader = (TextView) findViewById(R.id.header);
            this.mAvatar = (EaseImageView) findViewById(R.id.avatar);
            this.mName = (TextView) findViewById(R.id.name);
            this.clUser = (ConstraintLayout) findViewById(R.id.cl_user);
            if (EaseContactCustomAdapter.this.contactSetModel != null) {
                float titleTextSize = EaseContactCustomAdapter.this.contactSetModel.getTitleTextSize();
                if (titleTextSize != 0.0f) {
                    this.mName.setTextSize(0, titleTextSize);
                }
                int titleTextColor = EaseContactCustomAdapter.this.contactSetModel.getTitleTextColor();
                if (titleTextColor != 0) {
                    this.mName.setTextColor(titleTextColor);
                }
                Drawable avatarDefaultSrc = EaseContactCustomAdapter.this.contactSetModel.getAvatarDefaultSrc();
                if (avatarDefaultSrc != null) {
                    this.mAvatar.setImageDrawable(avatarDefaultSrc);
                }
                float avatarRadius = EaseContactCustomAdapter.this.contactSetModel.getAvatarRadius();
                if (avatarRadius != 0.0f) {
                    this.mAvatar.setRadius((int) avatarRadius);
                }
                float borderWidth = EaseContactCustomAdapter.this.contactSetModel.getBorderWidth();
                if (borderWidth != 0.0f) {
                    this.mAvatar.setBorderWidth((int) borderWidth);
                }
                int borderColor = EaseContactCustomAdapter.this.contactSetModel.getBorderColor();
                if (borderColor != 0) {
                    this.mAvatar.setBorderColor(borderColor);
                }
                this.mAvatar.setShapeType(EaseContactCustomAdapter.this.contactSetModel.getShapeType());
                float avatarSize = EaseContactCustomAdapter.this.contactSetModel.getAvatarSize();
                if (avatarSize != 0.0f) {
                    ViewGroup.LayoutParams layoutParams = this.mAvatar.getLayoutParams();
                    int i2 = (int) avatarSize;
                    layoutParams.height = i2;
                    layoutParams.width = i2;
                }
                float itemHeight = EaseContactCustomAdapter.this.contactSetModel.getItemHeight();
                if (itemHeight != 0.0f) {
                    this.clUser.getLayoutParams().height = (int) itemHeight;
                }
                this.clUser.setBackground(EaseContactCustomAdapter.this.contactSetModel.getBgDrawable());
            }
        }

        @Override // com.hyphenate.easeui.adapter.EaseBaseRecyclerViewAdapter.ViewHolder
        public void setData(EaseContactCustomBean easeContactCustomBean, int i2) {
            this.mHeader.setVisibility(8);
            this.mName.setText(easeContactCustomBean.getName());
            if (easeContactCustomBean.getResourceId() != 0) {
                this.mAvatar.setImageResource(easeContactCustomBean.getResourceId());
            } else if (TextUtils.isEmpty(easeContactCustomBean.getImage())) {
                Glide.with(this.itemView.getContext()).load(easeContactCustomBean.getImage()).into(this.mAvatar);
            }
        }
    }

    public void addItem(int i2, int i3, String str) {
        EaseContactCustomBean easeContactCustomBean = new EaseContactCustomBean();
        easeContactCustomBean.setId(i2);
        easeContactCustomBean.setResourceId(i3);
        easeContactCustomBean.setName(str);
        addData((EaseContactCustomAdapter) easeContactCustomBean);
    }

    @Override // com.hyphenate.easeui.adapter.EaseBaseRecyclerViewAdapter
    public EaseBaseRecyclerViewAdapter.ViewHolder getViewHolder(ViewGroup viewGroup, int i2) {
        return new CustomViewHolder(LayoutInflater.from(this.mContext).inflate(R.layout.ease_widget_contact_custom_item, viewGroup, false));
    }

    public void setSettingModel(EaseContactSetStyle easeContactSetStyle) {
        this.contactSetModel = easeContactSetStyle;
    }

    public void addItem(int i2, String str, String str2) {
        EaseContactCustomBean easeContactCustomBean = new EaseContactCustomBean();
        easeContactCustomBean.setId(i2);
        easeContactCustomBean.setImage(str);
        easeContactCustomBean.setName(str2);
        addData((EaseContactCustomAdapter) easeContactCustomBean);
    }
}
