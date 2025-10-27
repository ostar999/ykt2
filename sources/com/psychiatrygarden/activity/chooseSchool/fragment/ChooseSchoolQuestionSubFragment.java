package com.psychiatrygarden.activity.chooseSchool.fragment;

import android.os.Bundle;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import com.psychiatrygarden.activity.chooseSchool.adapter.ChooseSchoolQuestionAdapterNew;
import com.psychiatrygarden.baseview.BaseFragment;
import com.psychiatrygarden.baseview.ViewHolder;
import com.psychiatrygarden.bean.ChooseSchoolQuestionItem;
import com.psychiatrygarden.widget.CustomEmptyView;
import com.yikaobang.yixue.R;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\r\u001a\u00020\u000eH\u0014J\u001a\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014H\u0014J\u0012\u0010\u0015\u001a\u00020\u00102\b\u0010\u0016\u001a\u0004\u0018\u00010\u0017H\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.¢\u0006\u0002\n\u0000R\u001e\u0010\u0007\u001a\u0012\u0012\u0004\u0012\u00020\t0\bj\b\u0012\u0004\u0012\u00020\t`\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082.¢\u0006\u0002\n\u0000¨\u0006\u0018"}, d2 = {"Lcom/psychiatrygarden/activity/chooseSchool/fragment/ChooseSchoolQuestionSubFragment;", "Lcom/psychiatrygarden/baseview/BaseFragment;", "()V", "adapter", "Lcom/psychiatrygarden/activity/chooseSchool/adapter/ChooseSchoolQuestionAdapterNew;", "emptyView", "Lcom/psychiatrygarden/widget/CustomEmptyView;", "mData", "Ljava/util/ArrayList;", "Lcom/psychiatrygarden/bean/ChooseSchoolQuestionItem;", "Lkotlin/collections/ArrayList;", "recyclerView", "Landroidx/recyclerview/widget/RecyclerView;", "getLayoutId", "", "initViews", "", "holder", "Lcom/psychiatrygarden/baseview/ViewHolder;", "root", "Landroid/view/View;", "onLazyInitView", "savedInstanceState", "Landroid/os/Bundle;", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final class ChooseSchoolQuestionSubFragment extends BaseFragment {
    private ChooseSchoolQuestionAdapterNew adapter;
    private CustomEmptyView emptyView;

    @NotNull
    private final ArrayList<ChooseSchoolQuestionItem> mData = new ArrayList<>();
    private RecyclerView recyclerView;

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public int getLayoutId() {
        return R.layout.fragment_choose_school_question_sub;
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void initViews(@NotNull ViewHolder holder, @Nullable View root) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        View view = holder.get(R.id.recyclerView);
        Intrinsics.checkNotNullExpressionValue(view, "holder.get(R.id.recyclerView)");
        this.recyclerView = (RecyclerView) view;
        this.emptyView = new CustomEmptyView(getActivity(), 0, "暂无数据");
        ChooseSchoolQuestionAdapterNew chooseSchoolQuestionAdapterNew = new ChooseSchoolQuestionAdapterNew();
        this.adapter = chooseSchoolQuestionAdapterNew;
        CustomEmptyView customEmptyView = this.emptyView;
        ChooseSchoolQuestionAdapterNew chooseSchoolQuestionAdapterNew2 = null;
        if (customEmptyView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("emptyView");
            customEmptyView = null;
        }
        chooseSchoolQuestionAdapterNew.setEmptyView(customEmptyView);
        RecyclerView recyclerView = this.recyclerView;
        if (recyclerView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("recyclerView");
            recyclerView = null;
        }
        ChooseSchoolQuestionAdapterNew chooseSchoolQuestionAdapterNew3 = this.adapter;
        if (chooseSchoolQuestionAdapterNew3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("adapter");
        } else {
            chooseSchoolQuestionAdapterNew2 = chooseSchoolQuestionAdapterNew3;
        }
        recyclerView.setAdapter(chooseSchoolQuestionAdapterNew2);
        Bundle arguments = getArguments();
        if (arguments != null) {
            Serializable serializable = arguments.getSerializable("data");
            Intrinsics.checkNotNull(serializable, "null cannot be cast to non-null type kotlin.collections.List<com.psychiatrygarden.bean.ChooseSchoolQuestionItem>");
            this.mData.addAll((List) serializable);
        }
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportFragment, cn.webdemo.com.supporfragment.ISupportFragment
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        ChooseSchoolQuestionAdapterNew chooseSchoolQuestionAdapterNew = this.adapter;
        if (chooseSchoolQuestionAdapterNew == null) {
            Intrinsics.throwUninitializedPropertyAccessException("adapter");
            chooseSchoolQuestionAdapterNew = null;
        }
        chooseSchoolQuestionAdapterNew.setList(this.mData);
    }
}
