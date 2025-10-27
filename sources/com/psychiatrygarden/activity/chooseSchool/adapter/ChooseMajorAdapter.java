package com.psychiatrygarden.activity.chooseSchool.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.psychiatrygarden.bean.ChooseSchoolMajorItemBean;
import com.yikaobang.yixue.R;
import com.ykb.ebook.extensions.ViewExtensionsKt;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001B\r\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0018\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u00032\u0006\u0010\f\u001a\u00020\u0002H\u0014J$\u0010\r\u001a\u00020\n2\u001c\u0010\u000e\u001a\u0018\u0012\u0006\u0012\u0004\u0018\u00010\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u0010\u0012\u0004\u0012\u00020\n0\u000fR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\u0011"}, d2 = {"Lcom/psychiatrygarden/activity/chooseSchool/adapter/ChooseMajorAdapter;", "Lcom/chad/library/adapter/base/BaseQuickAdapter;", "Lcom/psychiatrygarden/bean/ChooseSchoolMajorItemBean;", "Lcom/chad/library/adapter/base/viewholder/BaseViewHolder;", "type", "", "(I)V", "getType", "()I", "convert", "", "holder", "item", "setItemClick", "click", "Lkotlin/Function2;", "", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final class ChooseMajorAdapter extends BaseQuickAdapter<ChooseSchoolMajorItemBean, BaseViewHolder> {
    private final int type;

    public ChooseMajorAdapter(int i2) {
        super(R.layout.item_choose_major, null, 2, null);
        this.type = i2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setItemClick$lambda$0(ChooseMajorAdapter this$0, Function2 click, BaseQuickAdapter baseQuickAdapter, View view, int i2) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(click, "$click");
        Intrinsics.checkNotNullParameter(baseQuickAdapter, "<anonymous parameter 0>");
        Intrinsics.checkNotNullParameter(view, "<anonymous parameter 1>");
        ChooseSchoolMajorItemBean item = this$0.getItem(i2);
        click.invoke(item.getMajor_id(), item.getTitle());
    }

    public final int getType() {
        return this.type;
    }

    public final void setItemClick(@NotNull final Function2<? super String, ? super String, Unit> click) {
        Intrinsics.checkNotNullParameter(click, "click");
        setOnItemClickListener(new OnItemClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.adapter.a
            @Override // com.chad.library.adapter.base.listener.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
                ChooseMajorAdapter.setItemClick$lambda$0(this.f11191c, click, baseQuickAdapter, view, i2);
            }
        });
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(@NotNull BaseViewHolder holder, @NotNull ChooseSchoolMajorItemBean item) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        Intrinsics.checkNotNullParameter(item, "item");
        TextView textView = (TextView) holder.getView(R.id.tvMajorName);
        ImageView imageView = (ImageView) holder.getView(R.id.imgExpand);
        ImageView imageView2 = (ImageView) holder.getView(R.id.imgCheck);
        textView.setText(item.getTitle());
        if (this.type == 0) {
            ViewExtensionsKt.gone(imageView);
            ViewExtensionsKt.gone(imageView2);
        } else {
            ViewExtensionsKt.gone(imageView);
            ViewExtensionsKt.visible(imageView2);
        }
    }
}
