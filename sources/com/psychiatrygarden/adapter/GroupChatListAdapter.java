package com.psychiatrygarden.adapter;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
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
public class GroupChatListAdapter extends BaseQuickAdapter<GroupChatListBean.DataDTO, BaseViewHolder> implements LoadMoreModule {
    private String search_content;

    public GroupChatListAdapter(@Nullable List<GroupChatListBean.DataDTO> data) {
        super(R.layout.item_group_chat_child, data);
    }

    @Override // com.chad.library.adapter.base.module.LoadMoreModule
    public /* synthetic */ BaseLoadMoreModule addLoadMoreModule(BaseQuickAdapter baseQuickAdapter) {
        return t0.h.a(this, baseQuickAdapter);
    }

    public void setSearchContent(String search_content) {
        this.search_content = search_content;
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder helper, GroupChatListBean.DataDTO item) {
        ImageView imageView = (ImageView) helper.getView(R.id.iv_group_chat_img);
        TextView textView = (TextView) helper.getView(R.id.tv_name);
        TextView textView2 = (TextView) helper.getView(R.id.tv_member_num);
        TextView textView3 = (TextView) helper.getView(R.id.tv_add_remove);
        TextView textView4 = (TextView) helper.getView(R.id.tv_group_info);
        View view = helper.getView(R.id.view_line);
        textView4.setText(item.getDescription());
        if (item.getIs_join().equals("1")) {
            textView3.setVisibility(8);
            textView3.setText("已加入");
            textView3.setTextColor(ContextCompat.getColor(getContext(), R.color.FF969696));
        } else {
            textView3.setVisibility(0);
            textView3.setText("加入");
            textView3.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
        }
        Glide.with(getContext()).load((Object) GlideUtils.generateUrl(item.getLogo())).placeholder(new ColorDrawable(ContextCompat.getColor(imageView.getContext(), SkinManager.getCurrentSkinType(ProjectApp.instance()) == 0 ? R.color.fourth_line_backgroup_color : R.color.bg_backgroud_night))).into(imageView);
        if (TextUtils.isEmpty(this.search_content)) {
            textView.setText(item.getName());
        } else {
            textView.setText(Html.fromHtml(item.getName().replaceAll(this.search_content, "<font font color='#dd594a'>" + this.search_content + "</font>"), 0));
        }
        textView2.setText(String.format("%s成员", item.getMember_count()));
        if (SkinManager.getCurrentSkinType(getContext()) == 0) {
            view.setBackgroundColor(Color.parseColor("#eeeeee"));
            textView.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
        } else {
            view.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.app_theme_night));
            textView.setTextColor(ContextCompat.getColor(getContext(), R.color.question_color_night));
            textView3.setTextColor(ContextCompat.getColor(getContext(), R.color.line_txt_color_night));
        }
    }
}
