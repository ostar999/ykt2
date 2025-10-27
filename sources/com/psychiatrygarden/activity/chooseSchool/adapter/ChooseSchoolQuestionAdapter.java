package com.psychiatrygarden.activity.chooseSchool.adapter;

import android.view.View;
import android.widget.ImageView;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.psychiatrygarden.bean.ChooseSchoolQuestionData;
import com.psychiatrygarden.bean.ChooseSchoolQuestionItem;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001B\u0005¢\u0006\u0002\u0010\u0004J\u0018\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\u0002H\u0014¨\u0006\t"}, d2 = {"Lcom/psychiatrygarden/activity/chooseSchool/adapter/ChooseSchoolQuestionAdapter;", "Lcom/chad/library/adapter/base/BaseQuickAdapter;", "Lcom/psychiatrygarden/bean/ChooseSchoolQuestionData;", "Lcom/chad/library/adapter/base/viewholder/BaseViewHolder;", "()V", "convert", "", "holder", "item", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nChooseSchoolQuestionAdapter.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ChooseSchoolQuestionAdapter.kt\ncom/psychiatrygarden/activity/chooseSchool/adapter/ChooseSchoolQuestionAdapter\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,84:1\n1#2:85\n*E\n"})
/* loaded from: classes5.dex */
public final class ChooseSchoolQuestionAdapter extends BaseQuickAdapter<ChooseSchoolQuestionData, BaseViewHolder> {
    public ChooseSchoolQuestionAdapter() {
        super(R.layout.item_choose_school_question, null, 2, null);
        setOnItemClickListener(new OnItemClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.adapter.f
            @Override // com.chad.library.adapter.base.listener.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
                ChooseSchoolQuestionAdapter._init_$lambda$0(this.f11199c, baseQuickAdapter, view, i2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void _init_$lambda$0(ChooseSchoolQuestionAdapter this$0, BaseQuickAdapter baseQuickAdapter, View view, int i2) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(baseQuickAdapter, "<anonymous parameter 0>");
        Intrinsics.checkNotNullParameter(view, "<anonymous parameter 1>");
        this$0.getItem(i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void convert$lambda$3(ChooseSchoolQuestionData item, ChooseSchoolQuestionChildAdapter childAdapter, View view) {
        Intrinsics.checkNotNullParameter(item, "$item");
        Intrinsics.checkNotNullParameter(childAdapter, "$childAdapter");
        if (item.getExpand()) {
            childAdapter.setList(item.getQuestion_list().subList(0, 2));
            item.setExpand(false);
        } else {
            childAdapter.setList(item.getQuestion_list());
            item.setExpand(true);
        }
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(@NotNull BaseViewHolder holder, @NotNull final ChooseSchoolQuestionData item) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        Intrinsics.checkNotNullParameter(item, "item");
        holder.setText(R.id.tvTypeName, item.getTitle());
        ImageView imageView = (ImageView) holder.getView(R.id.imageMore);
        imageView.setVisibility(item.getQuestion_list().size() > 2 ? 0 : 8);
        RecyclerView recyclerView = (RecyclerView) holder.getView(R.id.recyclerView);
        final ChooseSchoolQuestionChildAdapter chooseSchoolQuestionChildAdapter = new ChooseSchoolQuestionChildAdapter();
        if (item.getExpand()) {
            chooseSchoolQuestionChildAdapter.setList(item.getQuestion_list());
        } else if (item.getQuestion_list().size() >= 2) {
            chooseSchoolQuestionChildAdapter.setList(item.getQuestion_list().subList(0, 2));
        } else {
            ArrayList arrayList = new ArrayList();
            arrayList.addAll(item.getQuestion_list());
            ChooseSchoolQuestionItem chooseSchoolQuestionItem = new ChooseSchoolQuestionItem();
            chooseSchoolQuestionItem.setTitle("");
            arrayList.add(chooseSchoolQuestionItem);
            chooseSchoolQuestionChildAdapter.setList(arrayList);
        }
        recyclerView.setAdapter(chooseSchoolQuestionChildAdapter);
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.adapter.e
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ChooseSchoolQuestionAdapter.convert$lambda$3(item, chooseSchoolQuestionChildAdapter, view);
            }
        });
    }
}
