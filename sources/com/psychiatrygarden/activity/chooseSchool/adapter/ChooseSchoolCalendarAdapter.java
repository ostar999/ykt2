package com.psychiatrygarden.activity.chooseSchool.adapter;

import android.content.res.Resources;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.psychiatrygarden.bean.ChooseSchoolCalendarListItemBean;
import com.psychiatrygarden.utils.BaseViewHolderUtilKt;
import com.psychiatrygarden.utils.PublicMethodActivity;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.widget.DashedLineView;
import com.yikaobang.yixue.R;
import com.ykb.ebook.extensions.GsonExtensionsKt;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001B\u0005¢\u0006\u0002\u0010\u0004J\u0018\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\u0002H\u0014J\u0018\u0010\t\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\u0002H\u0002J\u0018\u0010\n\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\u0002H\u0002J\b\u0010\u000b\u001a\u00020\fH\u0002¨\u0006\r"}, d2 = {"Lcom/psychiatrygarden/activity/chooseSchool/adapter/ChooseSchoolCalendarAdapter;", "Lcom/chad/library/adapter/base/BaseMultiItemQuickAdapter;", "Lcom/psychiatrygarden/bean/ChooseSchoolCalendarListItemBean;", "Lcom/chad/library/adapter/base/viewholder/BaseViewHolder;", "()V", "convert", "", "holder", "item", "initDetailItem", "initYearItem", "isNight", "", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final class ChooseSchoolCalendarAdapter extends BaseMultiItemQuickAdapter<ChooseSchoolCalendarListItemBean, BaseViewHolder> {
    public ChooseSchoolCalendarAdapter() {
        super(null, 1, null);
        addItemType(0, R.layout.item_choose_school_calendar);
        addItemType(1, R.layout.item_choose_school_calendar2);
    }

    private final void initDetailItem(BaseViewHolder holder, final ChooseSchoolCalendarListItemBean item) {
        Resources resources;
        int i2;
        Resources resources2;
        int i3;
        DashedLineView dashedLineView = (DashedLineView) holder.getView(R.id.line1);
        DashedLineView dashedLineView2 = (DashedLineView) holder.getView(R.id.line2);
        TextView textView = (TextView) holder.getView(R.id.tvStatus);
        TextView textView2 = (TextView) holder.getView(R.id.tvContent);
        TextView textView3 = (TextView) holder.getView(R.id.tvTimeStr);
        ImageView imageView = (ImageView) holder.getView(R.id.imgMore);
        textView.setText(Intrinsics.areEqual(item.getTimeline_status(), "1") ? "未\n开\n始" : "已\n结\n束");
        textView2.setText(item.getTimeline_event_desc());
        textView3.setText(item.getTimeline_desc());
        if (Intrinsics.areEqual("1", item.getTimeline_status())) {
            textView.setBackgroundResource(R.drawable.shape_main_theme_five_deep_color_corners_4);
            if (isNight()) {
                resources2 = getContext().getResources();
                i3 = R.color.main_theme_color_night;
            } else {
                resources2 = getContext().getResources();
                i3 = R.color.main_theme_color;
            }
            textView.setTextColor(resources2.getColor(i3));
        } else {
            textView.setBackgroundResource(R.drawable.shape_new_bg_two_color_round4);
            if (isNight()) {
                resources = getContext().getResources();
                i2 = R.color.forth_txt_color_night;
            } else {
                resources = getContext().getResources();
                i2 = R.color.forth_txt_color;
            }
            textView.setTextColor(resources.getColor(i2));
        }
        dashedLineView.setVisibility(holder.getAdapterPosition() == 0 ? 4 : 0);
        dashedLineView2.setVisibility(holder.getAdapterPosition() != getData().size() - 1 ? 0 : 4);
        final boolean z2 = (item.getExtra() == null || Intrinsics.areEqual(item.getExtra().getPush_type(), "0")) ? false : true;
        imageView.setVisibility(z2 ? 0 : 8);
        holder.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.adapter.b
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ChooseSchoolCalendarAdapter.initDetailItem$lambda$0(z2, item, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initDetailItem$lambda$0(boolean z2, ChooseSchoolCalendarListItemBean item, View view) {
        Intrinsics.checkNotNullParameter(item, "$item");
        if (z2) {
            PublicMethodActivity.getInstance().mToActivity(GsonExtensionsKt.getGSON().toJson(item.getExtra()));
        }
    }

    private final void initYearItem(BaseViewHolder holder, ChooseSchoolCalendarListItemBean item) {
        DashedLineView dashedLineView = (DashedLineView) holder.getView(R.id.line1);
        DashedLineView dashedLineView2 = (DashedLineView) holder.getView(R.id.line2);
        ((TextView) holder.getView(R.id.tvYear)).setText(item.getTimeline_desc());
        dashedLineView.setVisibility(holder.getAdapterPosition() == 0 ? 4 : 0);
        dashedLineView2.setVisibility(holder.getAdapterPosition() != getData().size() + (-1) ? 0 : 4);
    }

    private final boolean isNight() {
        return SkinManager.getCurrentSkinType(getContext()) == 1;
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(@NotNull BaseViewHolder holder, @NotNull ChooseSchoolCalendarListItemBean item) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        Intrinsics.checkNotNullParameter(item, "item");
        int customerItemViewType = BaseViewHolderUtilKt.getCustomerItemViewType(holder);
        if (customerItemViewType == 0) {
            initYearItem(holder, item);
        } else {
            if (customerItemViewType != 1) {
                return;
            }
            initDetailItem(holder, item);
        }
    }
}
