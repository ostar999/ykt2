package com.psychiatrygarden.activity.chooseSchool.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.psychiatrygarden.bean.ChooseSchoolTargetSchoolBeanItem;
import com.psychiatrygarden.utils.FlowLayout;
import com.psychiatrygarden.utils.FlowLayoutUtil;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.widget.CircleImageView;
import com.yikaobang.yixue.R;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001B\u0005¢\u0006\u0002\u0010\u0004J\u0018\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u00032\u0006\u0010\u0012\u001a\u00020\u0002H\u0014J\b\u0010\u0013\u001a\u00020\u000fH\u0002J\u0010\u0010\u0014\u001a\u00020\u000f2\u0006\u0010\u0015\u001a\u00020\u0016H\u0016J&\u0010\u0017\u001a\u00020\u000f2\u001e\u0010\u0018\u001a\u001a\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000f0\rR\u0012\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0004\n\u0002\u0010\u0007R\u0012\u0010\b\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0004\n\u0002\u0010\u0007R\u0012\u0010\t\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0004\n\u0002\u0010\u0007R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R&\u0010\f\u001a\u001a\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000f0\rX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0019"}, d2 = {"Lcom/psychiatrygarden/activity/chooseSchool/adapter/TargetSchoolAdapter;", "Lcom/chad/library/adapter/base/BaseQuickAdapter;", "Lcom/psychiatrygarden/bean/ChooseSchoolTargetSchoolBeanItem;", "Lcom/chad/library/adapter/base/viewholder/BaseViewHolder;", "()V", "blueColor", "", "Ljava/lang/Integer;", "blueColorDisable", "grayLineColor", "isNight", "", "itemClick", "Lkotlin/Function3;", "", "", "convert", "holder", "item", "initColor", "onAttachedToRecyclerView", "recyclerView", "Landroidx/recyclerview/widget/RecyclerView;", "setItemClick", "click", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final class TargetSchoolAdapter extends BaseQuickAdapter<ChooseSchoolTargetSchoolBeanItem, BaseViewHolder> {

    @Nullable
    private Integer blueColor;

    @Nullable
    private Integer blueColorDisable;

    @Nullable
    private Integer grayLineColor;
    private boolean isNight;

    @NotNull
    private Function3<? super String, ? super String, ? super String, Unit> itemClick;

    public TargetSchoolAdapter() {
        super(R.layout.item_target_school, null, 2, null);
        this.itemClick = new Function3<String, String, String, Unit>() { // from class: com.psychiatrygarden.activity.chooseSchool.adapter.TargetSchoolAdapter$itemClick$1
            @Override // kotlin.jvm.functions.Function3
            public /* bridge */ /* synthetic */ Unit invoke(String str, String str2, String str3) {
                invoke2(str, str2, str3);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(@NotNull String id, @NotNull String type, @NotNull String name) {
                Intrinsics.checkNotNullParameter(id, "id");
                Intrinsics.checkNotNullParameter(type, "type");
                Intrinsics.checkNotNullParameter(name, "name");
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void convert$lambda$1(ChooseSchoolTargetSchoolBeanItem item, TargetSchoolAdapter this$0, View view) {
        Intrinsics.checkNotNullParameter(item, "$item");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        String school_id = item.getSchool_id();
        if (school_id != null) {
            Function3<? super String, ? super String, ? super String, Unit> function3 = this$0.itemClick;
            String title = item.getTitle();
            if (title == null) {
                title = "";
            }
            function3.invoke(school_id, "1", title);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void convert$lambda$3(ChooseSchoolTargetSchoolBeanItem item, TargetSchoolAdapter this$0, View view) {
        Intrinsics.checkNotNullParameter(item, "$item");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        String school_id = item.getSchool_id();
        if (school_id != null) {
            Function3<? super String, ? super String, ? super String, Unit> function3 = this$0.itemClick;
            String title = item.getTitle();
            if (title == null) {
                title = "";
            }
            function3.invoke(school_id, "2", title);
        }
    }

    private final void initColor() {
        this.isNight = SkinManager.getCurrentSkinType(getContext()) == 1;
        this.blueColor = Integer.valueOf(getContext().getResources().getColor(R.color.zx_color_blue, null));
        this.grayLineColor = this.isNight ? Integer.valueOf(getContext().getResources().getColor(R.color.zx_color_blue_night, null)) : Integer.valueOf(getContext().getResources().getColor(R.color.daily_calendar_today_bg_color, null));
        this.blueColorDisable = this.isNight ? Integer.valueOf(getContext().getResources().getColor(R.color.first_txt_color_night, null)) : Integer.valueOf(getContext().getResources().getColor(R.color.first_txt_color, null));
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter, androidx.recyclerview.widget.RecyclerView.Adapter
    public void onAttachedToRecyclerView(@NotNull RecyclerView recyclerView) {
        Intrinsics.checkNotNullParameter(recyclerView, "recyclerView");
        super.onAttachedToRecyclerView(recyclerView);
        initColor();
    }

    public final void setItemClick(@NotNull Function3<? super String, ? super String, ? super String, Unit> click) {
        Intrinsics.checkNotNullParameter(click, "click");
        this.itemClick = click;
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(@NotNull BaseViewHolder holder, @NotNull final ChooseSchoolTargetSchoolBeanItem item) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        Intrinsics.checkNotNullParameter(item, "item");
        RelativeLayout relativeLayout = (RelativeLayout) holder.getView(R.id.layoutSchoolItemRoot);
        CircleImageView circleImageView = (CircleImageView) holder.getView(R.id.imgSchoolIcon);
        TextView textView = (TextView) holder.getView(R.id.tvSchoolName);
        TextView textView2 = (TextView) holder.getView(R.id.tvSchoolAddress);
        TextView textView3 = (TextView) holder.getView(R.id.tvSchoolCode);
        TextView textView4 = (TextView) holder.getView(R.id.tvSchoolGZ);
        TextView textView5 = (TextView) holder.getView(R.id.tvSchoolGZ_7);
        TextView textView6 = (TextView) holder.getView(R.id.tvSchoolSee);
        TextView textView7 = (TextView) holder.getView(R.id.tvSchoolZSCount);
        TextView textView8 = (TextView) holder.getView(R.id.tvSchoolZYCount);
        View view = holder.getView(R.id.gap);
        ImageView imageView = (ImageView) holder.getView(R.id.imgDel);
        FlowLayout flowLayout = (FlowLayout) holder.getView(R.id.schoolFlag);
        Context context = getContext();
        String cover = item.getCover();
        if (cover == null) {
            cover = "";
        }
        GlideUtils.loadImageDef(context, cover, circleImageView);
        textView.setText(item.getTitle());
        textView2.setText("地址：" + item.getLocation());
        textView3.setText("编码：" + item.getCode());
        flowLayout.removeAllViews();
        FlowLayoutUtil.initChildLabelViewsTargetSchool(flowLayout, item.getAttr(), getContext());
        textView4.setText(item.getFollow_count());
        textView5.setText(item.getRecent_7days_follow());
        textView6.setText(item.getView_count());
        textView7.setText(item.getStudent_count());
        textView8.setText(item.getMajor_count());
        view.setVisibility(holder.getAbsoluteAdapterPosition() == getData().size() - 1 ? 8 : 0);
        relativeLayout.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.adapter.j
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                TargetSchoolAdapter.convert$lambda$1(item, this, view2);
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.adapter.k
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                TargetSchoolAdapter.convert$lambda$3(item, this, view2);
            }
        });
    }
}
