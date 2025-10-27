package com.psychiatrygarden.adapter;

import android.graphics.drawable.ColorDrawable;
import android.text.Html;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.BaseLoadMoreModule;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.bean.GroupChatListBean;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.utils.SkinManager;
import com.yikaobang.yixue.R;
import java.util.List;

/* loaded from: classes5.dex */
public class GroupChatNameAdapter extends BaseQuickAdapter<GroupChatListBean.DataDTO, BaseViewHolder> implements LoadMoreModule {
    private String search_content;

    public GroupChatNameAdapter(@Nullable List<GroupChatListBean.DataDTO> data) {
        super(R.layout.item_group_chat_list, data);
    }

    @Override // com.chad.library.adapter.base.module.LoadMoreModule
    public /* synthetic */ BaseLoadMoreModule addLoadMoreModule(BaseQuickAdapter baseQuickAdapter) {
        return t0.h.a(this, baseQuickAdapter);
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder helper, GroupChatListBean.DataDTO item) {
        ImageView imageView = (ImageView) helper.getView(R.id.iv_group_chat_img);
        TextView textView = (TextView) helper.getView(R.id.tv_name);
        TextView textView2 = (TextView) helper.getView(R.id.tv_chat_num);
        TextView textView3 = (TextView) helper.getView(R.id.tv_member_num);
        ImageView imageView2 = (ImageView) helper.getView(R.id.iv_add_remove);
        imageView2.setVisibility(item.getIs_join().equals("1") ? 8 : 0);
        imageView2.setImageResource(item.getIs_join().equals("1") ? R.mipmap.chat_chat_member_choice : R.mipmap.chat_chat_member_choice_no);
        Glide.with(getContext()).load((Object) GlideUtils.generateUrl(item.getLogo())).placeholder(new ColorDrawable(ContextCompat.getColor(imageView.getContext(), SkinManager.getCurrentSkinType(ProjectApp.instance()) == 0 ? R.color.fourth_line_backgroup_color : R.color.bg_backgroud_night))).into(imageView);
        if (TextUtils.isEmpty(this.search_content)) {
            textView.setText(item.getName());
        } else {
            textView.setText(Html.fromHtml(item.getName().replaceAll(this.search_content, "<font font color='#dd594a'>" + this.search_content + "</font>"), 0));
        }
        textView2.setText(item.getNew_message());
        textView3.setText(String.format("%s成员", item.getMember_count()));
    }
}
