package com.psychiatrygarden.activity.chooseSchool.adapter;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.hutool.core.text.CharPool;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.psychiatrygarden.activity.chooseSchool.SchoolDetailsAct;
import com.psychiatrygarden.bean.ChooseSchoolSchoolListBean;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.widget.CircleImageView;
import com.yikaobang.yixue.R;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001B\u0005¢\u0006\u0002\u0010\u0004J\u0018\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u00032\u0006\u0010\u000e\u001a\u00020\u0002H\u0014R\u001c\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\n¨\u0006\u000f"}, d2 = {"Lcom/psychiatrygarden/activity/chooseSchool/adapter/ChooseSchoolListAdapter;", "Lcom/chad/library/adapter/base/BaseQuickAdapter;", "Lcom/psychiatrygarden/bean/ChooseSchoolSchoolListBean;", "Lcom/chad/library/adapter/base/viewholder/BaseViewHolder;", "()V", "type", "", "getType", "()Ljava/lang/String;", "setType", "(Ljava/lang/String;)V", "convert", "", "holder", "item", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final class ChooseSchoolListAdapter extends BaseQuickAdapter<ChooseSchoolSchoolListBean, BaseViewHolder> {

    @Nullable
    private String type;

    public ChooseSchoolListAdapter() {
        super(R.layout.item_choose_school_list, null, 2, null);
        setOnItemClickListener(new OnItemClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.adapter.c
            @Override // com.chad.library.adapter.base.listener.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
                ChooseSchoolListAdapter._init_$lambda$0(this.f11195c, baseQuickAdapter, view, i2);
            }
        });
        this.type = "";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void _init_$lambda$0(ChooseSchoolListAdapter this$0, BaseQuickAdapter baseQuickAdapter, View view, int i2) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(baseQuickAdapter, "<anonymous parameter 0>");
        Intrinsics.checkNotNullParameter(view, "<anonymous parameter 1>");
        SchoolDetailsAct.newIntent(this$0.getContext(), this$0.getItem(i2).getSchool_id());
    }

    @Nullable
    public final String getType() {
        return this.type;
    }

    public final void setType(@Nullable String str) {
        this.type = str;
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue
    java.lang.NullPointerException: Cannot invoke "java.util.List.iterator()" because the return value of "jadx.core.dex.visitors.regions.SwitchOverStringVisitor$SwitchData.getNewCases()" is null
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.restoreSwitchOverString(SwitchOverStringVisitor.java:109)
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visitRegion(SwitchOverStringVisitor.java:66)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:77)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:82)
     */
    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(@NotNull BaseViewHolder holder, @NotNull ChooseSchoolSchoolListBean item) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        Intrinsics.checkNotNullParameter(item, "item");
        TextView textView = (TextView) holder.getView(R.id.tvStatus);
        RelativeLayout relativeLayout = (RelativeLayout) holder.getView(R.id.layoutRate);
        CircleImageView circleImageView = (CircleImageView) holder.getView(R.id.schoolIcon);
        Context context = getContext();
        String cover = item.getCover();
        if (cover == null) {
            cover = "";
        }
        GlideUtils.loadImageDef(context, cover, circleImageView);
        holder.setText(R.id.tvSchoolName, item.getTitle());
        List<String> attr = item.getAttr();
        if (attr == null || attr.isEmpty()) {
            holder.setText(R.id.tvSchoolTag, "");
        } else {
            holder.setText(R.id.tvSchoolTag, CollectionsKt___CollectionsKt.joinToString$default(item.getAttr(), " · ", null, null, 0, null, null, 62, null));
        }
        holder.setText(R.id.tvSchoolZyName, item.getMajor_code() + CharPool.DASHED + item.getMajor_title());
        holder.setText(R.id.tvRate, item.getRate());
        holder.setText(R.id.tvSchoolGZ, item.getMax_score());
        holder.setText(R.id.tvSchoolGZ_7, item.getMin_score());
        holder.setText(R.id.tvSchoolSee, item.getAvg_score());
        holder.setText(R.id.tvSchoolZYCount, item.getStudent_count());
        String str = this.type;
        if (str != null) {
            switch (str.hashCode()) {
                case 49:
                    if (str.equals("1")) {
                        relativeLayout.setBackgroundResource(R.drawable.shape_choose_school_gray_corners4);
                        textView.setText("难");
                        break;
                    }
                    break;
                case 50:
                    if (str.equals("2")) {
                        relativeLayout.setBackgroundResource(R.drawable.shape_choose_school_red_corners4);
                        textView.setText("冲");
                        break;
                    }
                    break;
                case 51:
                    if (str.equals("3")) {
                        relativeLayout.setBackgroundResource(R.drawable.shape_choose_school_blue_corners4);
                        textView.setText("稳");
                        break;
                    }
                    break;
                case 52:
                    if (str.equals("4")) {
                        relativeLayout.setBackgroundResource(R.drawable.shape_choose_school_green_corners4);
                        textView.setText("保");
                        break;
                    }
                    break;
            }
        }
    }
}
