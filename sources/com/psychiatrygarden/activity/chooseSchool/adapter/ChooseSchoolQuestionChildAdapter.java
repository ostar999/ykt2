package com.psychiatrygarden.activity.chooseSchool.adapter;

import android.view.View;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.psychiatrygarden.activity.chooseSchool.ChooseSchoolQuestionDetailActivity;
import com.psychiatrygarden.bean.ChooseSchoolQuestionItem;
import com.yikaobang.yixue.R;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001B\u0005¢\u0006\u0002\u0010\u0004J\u0018\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\u0002H\u0014¨\u0006\t"}, d2 = {"Lcom/psychiatrygarden/activity/chooseSchool/adapter/ChooseSchoolQuestionChildAdapter;", "Lcom/chad/library/adapter/base/BaseQuickAdapter;", "Lcom/psychiatrygarden/bean/ChooseSchoolQuestionItem;", "Lcom/chad/library/adapter/base/viewholder/BaseViewHolder;", "()V", "convert", "", "holder", "item", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final class ChooseSchoolQuestionChildAdapter extends BaseQuickAdapter<ChooseSchoolQuestionItem, BaseViewHolder> {
    public ChooseSchoolQuestionChildAdapter() {
        super(R.layout.item_choose_school_question_child, null, 2, 0 == true ? 1 : 0);
        setOnItemClickListener(new OnItemClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.adapter.h
            @Override // com.chad.library.adapter.base.listener.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
                ChooseSchoolQuestionChildAdapter._init_$lambda$1(this.f11201c, baseQuickAdapter, view, i2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void _init_$lambda$1(ChooseSchoolQuestionChildAdapter this$0, BaseQuickAdapter baseQuickAdapter, View view, int i2) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(baseQuickAdapter, "<anonymous parameter 0>");
        Intrinsics.checkNotNullParameter(view, "<anonymous parameter 1>");
        String id = this$0.getItem(i2).getId();
        if (id != null) {
            ChooseSchoolQuestionDetailActivity.INSTANCE.navigationToChooseSchoolQuestionDetailActivity(this$0.getContext(), id);
        }
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(@NotNull BaseViewHolder holder, @NotNull ChooseSchoolQuestionItem item) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        Intrinsics.checkNotNullParameter(item, "item");
        holder.setText(R.id.questionName, item.getTitle());
        holder.getView(R.id.lineBottom).setVisibility(holder.getBindingAdapterPosition() == getData().size() - 1 ? 8 : 0);
    }
}
