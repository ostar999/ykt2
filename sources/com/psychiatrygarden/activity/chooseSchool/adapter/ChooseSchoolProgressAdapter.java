package com.psychiatrygarden.activity.chooseSchool.adapter;

import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.psychiatrygarden.bean.ChooseSchoolCalendarListItemBean;
import com.psychiatrygarden.utils.SkinManager;
import com.yikaobang.yixue.R;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001B\u0005¢\u0006\u0002\u0010\u0004J\u0018\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u00032\u0006\u0010\u0012\u001a\u00020\u0002H\u0014J\b\u0010\u0013\u001a\u00020\u000fH\u0002J\u0010\u0010\u0014\u001a\u00020\u000f2\u0006\u0010\u0015\u001a\u00020\u0016H\u0016J\u001a\u0010\u0017\u001a\u00020\u000f2\u0012\u0010\u0018\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000f0\rR\u0012\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0004\n\u0002\u0010\u0007R\u0012\u0010\b\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0004\n\u0002\u0010\u0007R\u0012\u0010\t\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0004\n\u0002\u0010\u0007R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000f0\rX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0019"}, d2 = {"Lcom/psychiatrygarden/activity/chooseSchool/adapter/ChooseSchoolProgressAdapter;", "Lcom/chad/library/adapter/base/BaseQuickAdapter;", "Lcom/psychiatrygarden/bean/ChooseSchoolCalendarListItemBean;", "Lcom/chad/library/adapter/base/viewholder/BaseViewHolder;", "()V", "blueColor", "", "Ljava/lang/Integer;", "blueColorDisable", "grayLineColor", "isNight", "", "itemClick", "Lkotlin/Function1;", "", "", "convert", "holder", "item", "initColor", "onAttachedToRecyclerView", "recyclerView", "Landroidx/recyclerview/widget/RecyclerView;", "setTaskClick", "click", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final class ChooseSchoolProgressAdapter extends BaseQuickAdapter<ChooseSchoolCalendarListItemBean, BaseViewHolder> {

    @Nullable
    private Integer blueColor;

    @Nullable
    private Integer blueColorDisable;

    @Nullable
    private Integer grayLineColor;
    private boolean isNight;

    @NotNull
    private Function1<? super String, Unit> itemClick;

    public ChooseSchoolProgressAdapter() {
        super(R.layout.item_zx_calendar_progress, null, 2, null);
        this.itemClick = new Function1<String, Unit>() { // from class: com.psychiatrygarden.activity.chooseSchool.adapter.ChooseSchoolProgressAdapter$itemClick$1
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(String str) {
                invoke2(str);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(@NotNull String i2) {
                Intrinsics.checkNotNullParameter(i2, "i");
                System.out.println((Object) ("点击了taskId " + i2));
            }
        };
    }

    private final void initColor() {
        this.isNight = SkinManager.getCurrentSkinType(getContext()) == 1;
        this.blueColor = Integer.valueOf(getContext().getResources().getColor(R.color.zx_color_blue, null));
        this.grayLineColor = this.isNight ? Integer.valueOf(getContext().getResources().getColor(R.color.new_gray_line_color_night, null)) : Integer.valueOf(getContext().getResources().getColor(R.color.daily_calendar_today_bg_color, null));
        this.blueColorDisable = this.isNight ? Integer.valueOf(getContext().getResources().getColor(R.color.first_txt_color_night, null)) : Integer.valueOf(getContext().getResources().getColor(R.color.first_txt_color, null));
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter, androidx.recyclerview.widget.RecyclerView.Adapter
    public void onAttachedToRecyclerView(@NotNull RecyclerView recyclerView) {
        Intrinsics.checkNotNullParameter(recyclerView, "recyclerView");
        super.onAttachedToRecyclerView(recyclerView);
        initColor();
    }

    public final void setTaskClick(@NotNull Function1<? super String, Unit> click) {
        Intrinsics.checkNotNullParameter(click, "click");
        this.itemClick = click;
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(@NotNull BaseViewHolder holder, @NotNull ChooseSchoolCalendarListItemBean item) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        Intrinsics.checkNotNullParameter(item, "item");
        TextView textView = (TextView) holder.getView(R.id.tvTop);
        View view = holder.getView(R.id.viewProgress1);
        View view2 = holder.getView(R.id.viewProgress2);
        TextView textView2 = (TextView) holder.getView(R.id.viewProgressPoint);
        TextView textView3 = (TextView) holder.getView(R.id.tvTime);
        boolean zAreEqual = Intrinsics.areEqual(item.getTimeline_status(), "2");
        textView.setEnabled(zAreEqual);
        textView.setText(item.getTimeline_event_desc());
        Integer num = zAreEqual ? this.blueColor : this.blueColorDisable;
        Intrinsics.checkNotNull(num);
        textView.setTextColor(num.intValue());
        textView2.setEnabled(zAreEqual);
        Integer num2 = zAreEqual ? this.blueColor : this.grayLineColor;
        Intrinsics.checkNotNull(num2);
        view.setBackgroundColor(num2.intValue());
        Integer num3 = zAreEqual ? this.blueColor : this.grayLineColor;
        Intrinsics.checkNotNull(num3);
        view2.setBackgroundColor(num3.intValue());
        textView3.setText(item.getTimeline_desc());
    }
}
