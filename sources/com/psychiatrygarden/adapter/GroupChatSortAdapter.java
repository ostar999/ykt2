package com.psychiatrygarden.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.bean.GroupChatCategoryBean;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.utils.ScreenUtil;
import com.psychiatrygarden.utils.SkinManager;
import com.yikaobang.yixue.R;
import java.util.List;

/* loaded from: classes5.dex */
public class GroupChatSortAdapter extends BaseQuickAdapter<GroupChatCategoryBean.DataDTO, BaseViewHolder> {
    public GroupChatSortAdapter(int layoutResId, @Nullable List<GroupChatCategoryBean.DataDTO> data) {
        super(layoutResId, data);
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder helper, GroupChatCategoryBean.DataDTO dataBean) {
        ImageView imageView = (ImageView) helper.getView(R.id.iv_sort_icon);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams((ScreenUtil.getScreenWidth((Activity) getContext()) - ScreenUtil.getPxByDp(getContext(), 60)) / 6, -2);
        layoutParams.rightMargin = ScreenUtil.getPxByDp(getContext(), 10);
        if (helper.getBindingAdapterPosition() == 0) {
            layoutParams.leftMargin = ScreenUtil.getPxByDp(getContext(), 20);
        } else {
            layoutParams.leftMargin = 0;
        }
        boolean zIsSelected = dataBean.isSelected();
        int i2 = R.color.fourth_line_backgroup_color;
        if (zIsSelected) {
            RequestBuilder<Drawable> requestBuilderLoad = Glide.with(getContext()).load((Object) GlideUtils.generateUrl(dataBean.getSelected_icon()));
            Context context = getContext();
            if (SkinManager.getCurrentSkinType(ProjectApp.instance()) != 0) {
                i2 = R.color.bg_backgroud_night;
            }
            requestBuilderLoad.placeholder(new ColorDrawable(ContextCompat.getColor(context, i2))).into(imageView);
        } else {
            RequestBuilder<Drawable> requestBuilderLoad2 = Glide.with(getContext()).load((Object) GlideUtils.generateUrl(dataBean.getDefault_icon()));
            Context context2 = getContext();
            if (SkinManager.getCurrentSkinType(ProjectApp.instance()) != 0) {
                i2 = R.color.bg_backgroud_night;
            }
            requestBuilderLoad2.placeholder(new ColorDrawable(ContextCompat.getColor(context2, i2))).into(imageView);
        }
        imageView.setLayoutParams(layoutParams);
    }
}
