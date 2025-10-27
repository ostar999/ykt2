package com.psychiatrygarden.activity.forum.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.psychiatrygarden.activity.forum.bean.ForumSectionIndexBean;
import com.psychiatrygarden.baseview.BaseFragment;
import com.psychiatrygarden.baseview.ViewHolder;
import com.psychiatrygarden.widget.ClearEditText;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes5.dex */
public class ForumSectionToFragment extends BaseFragment {
    private final List<ForumSectionIndexBean.DataBean> parentList = new ArrayList();
    public RecyclerView recycle;
    public BaseQuickAdapter<ForumSectionIndexBean.DataBean, BaseViewHolder> schooladapter;

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$0(View view) {
        if (getActivity() != null) {
            getActivity().finish();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$1(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
        if (this.parentList.size() <= i2) {
            return;
        }
        Iterator<ForumSectionIndexBean.DataBean> it = this.parentList.iterator();
        while (it.hasNext()) {
            it.next().setSelected(0);
        }
        this.parentList.get(i2).setSelected(1);
        baseQuickAdapter.notifyDataSetChanged();
        showSchoolData(this.parentList.get(i2).getId(), "");
    }

    public static ForumSectionToFragment newInstance() {
        Bundle bundle = new Bundle();
        ForumSectionToFragment forumSectionToFragment = new ForumSectionToFragment();
        forumSectionToFragment.setArguments(bundle);
        return forumSectionToFragment;
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public int getLayoutId() {
        return R.layout.activity_forun_section;
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void initViews(ViewHolder holder, View root) {
        ImageView imageView = (ImageView) holder.get(R.id.icon_left2);
        holder.get(R.id.view).setVisibility(8);
        imageView.setVisibility(8);
        ClearEditText clearEditText = (ClearEditText) holder.get(R.id.editTxt);
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.forum.fragment.y
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12399c.lambda$initViews$0(view);
            }
        });
        clearEditText.addTextChangedListener(new TextWatcher() { // from class: com.psychiatrygarden.activity.forum.fragment.ForumSectionToFragment.1
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable s2) {
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence s2, int start, int count, int after) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence s2, int start, int before, int count) {
                ForumSectionToFragment.this.setEditData(s2.toString());
            }
        });
        RecyclerView recyclerView = (RecyclerView) holder.get(R.id.recycle);
        this.recycle = recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        BaseQuickAdapter<ForumSectionIndexBean.DataBean, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<ForumSectionIndexBean.DataBean, BaseViewHolder>(R.layout.layout_forum_parent, this.parentList) { // from class: com.psychiatrygarden.activity.forum.fragment.ForumSectionToFragment.2
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(@NonNull BaseViewHolder helper, ForumSectionIndexBean.DataBean item) {
                TextView textView = (TextView) helper.getView(R.id.title);
                TextView textView2 = (TextView) helper.getView(R.id.viewimg);
                RelativeLayout relativeLayout = (RelativeLayout) helper.getView(R.id.forumrel);
                textView.setText(item.getName());
                if (item.getSelected() == 1) {
                    relativeLayout.setSelected(true);
                    textView.setSelected(true);
                    textView2.setVisibility(0);
                } else {
                    relativeLayout.setSelected(false);
                    textView.setSelected(false);
                    textView2.setVisibility(4);
                }
            }
        };
        this.schooladapter = baseQuickAdapter;
        this.recycle.setAdapter(baseQuickAdapter);
        this.schooladapter.setOnItemClickListener(new OnItemClickListener() { // from class: com.psychiatrygarden.activity.forum.fragment.z
            @Override // com.chad.library.adapter.base.listener.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i2) {
                this.f12400c.lambda$initViews$1(baseQuickAdapter2, view, i2);
            }
        });
        showSchoolData("1", "");
    }

    public void setEditData(String fifter) {
        if (TextUtils.isEmpty(fifter)) {
            showSchoolData("1", "");
        } else {
            showSchoolData("0", fifter);
        }
    }

    public void showSchoolData(String pid, String fifter) {
        FragmentTransaction fragmentTransactionBeginTransaction = getChildFragmentManager().beginTransaction();
        fragmentTransactionBeginTransaction.replace(R.id.sec_fragment, ForumSectionFragmment.getInstent(pid, fifter));
        fragmentTransactionBeginTransaction.commitNowAllowingStateLoss();
    }
}
