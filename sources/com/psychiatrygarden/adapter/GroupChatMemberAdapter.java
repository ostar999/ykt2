package com.psychiatrygarden.adapter;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.bean.GroupChatDetailBean;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.utils.ScreenUtil;
import com.psychiatrygarden.utils.SkinManager;
import com.yikaobang.yixue.R;
import java.util.List;

/* loaded from: classes5.dex */
public class GroupChatMemberAdapter extends BaseQuickAdapter<GroupChatDetailBean.DataDTO.DefaultMemberDTO, BaseViewHolder> {
    public GroupChatMemberAdapter(@Nullable List<GroupChatDetailBean.DataDTO.DefaultMemberDTO> data) {
        super(R.layout.item_group_chat_member, data);
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder helper, GroupChatDetailBean.DataDTO.DefaultMemberDTO item) {
        ImageView imageView = (ImageView) helper.getView(R.id.iv_group_chat_member);
        ImageView imageView2 = (ImageView) helper.getView(R.id.iv_manager);
        TextView textView = (TextView) helper.getView(R.id.tv_userName);
        RelativeLayout relativeLayout = (RelativeLayout) helper.getView(R.id.rl_image);
        int screenWidth = (ScreenUtil.getScreenWidth((Activity) getContext()) - ScreenUtil.getPxByDp(getContext(), 100)) / 5;
        relativeLayout.setLayoutParams(new LinearLayout.LayoutParams(screenWidth, screenWidth));
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(screenWidth, -2);
        layoutParams.topMargin = ScreenUtil.getPxByDp(getContext(), 5);
        textView.setLayoutParams(layoutParams);
        if (item.isIs_add()) {
            textView.setVisibility(4);
            imageView2.setVisibility(8);
            imageView.setImageResource(R.mipmap.group_remove_member_icon);
            return;
        }
        textView.setVisibility(0);
        textView.setText(item.getNickname());
        Glide.with(getContext()).load((Object) GlideUtils.generateUrl(item.getAvatar())).placeholder(new ColorDrawable(ContextCompat.getColor(imageView.getContext(), SkinManager.getCurrentSkinType(ProjectApp.instance()) == 0 ? R.color.fourth_line_backgroup_color : R.color.bg_backgroud_night))).into(imageView);
        if (item.getIs_owner().equals("1") || item.getIs_owner().equals("2")) {
            imageView2.setVisibility(0);
        } else {
            imageView2.setVisibility(8);
        }
    }
}
