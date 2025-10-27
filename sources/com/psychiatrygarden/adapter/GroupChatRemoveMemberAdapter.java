package com.psychiatrygarden.adapter;

import android.graphics.drawable.ColorDrawable;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.bean.GroupChatDetailBean;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.utils.SkinManager;
import com.yikaobang.yixue.R;
import java.util.List;

/* loaded from: classes5.dex */
public class GroupChatRemoveMemberAdapter extends BaseQuickAdapter<GroupChatDetailBean.DataDTO.DefaultMemberDTO, BaseViewHolder> {
    public GroupChatRemoveMemberAdapter(@Nullable List<GroupChatDetailBean.DataDTO.DefaultMemberDTO> data) {
        super(R.layout.item_group_chat_remove_member, data);
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder helper, GroupChatDetailBean.DataDTO.DefaultMemberDTO item) {
        ImageView imageView = (ImageView) helper.getView(R.id.iv_group_chat_member);
        TextView textView = (TextView) helper.getView(R.id.tv_userName);
        ImageView imageView2 = (ImageView) helper.getView(R.id.iv_check);
        textView.setText(item.getNickname());
        Glide.with(getContext()).load((Object) GlideUtils.generateUrl(item.getAvatar())).placeholder(new ColorDrawable(ContextCompat.getColor(getContext(), SkinManager.getCurrentSkinType(ProjectApp.instance()) == 0 ? R.color.fourth_line_backgroup_color : R.color.bg_backgroud_night))).into(imageView);
        if (item.isIs_choice()) {
            imageView2.setImageResource(R.mipmap.chat_chat_member_choice);
        } else {
            imageView2.setImageResource(R.mipmap.chat_chat_member_choice_no);
        }
    }
}
