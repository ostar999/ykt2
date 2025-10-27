package com.psychiatrygarden.activity.forum;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.forum.bean.ForumSectionIndexBean;
import com.psychiatrygarden.activity.forum.fragment.ForumSectionFragmment;
import com.psychiatrygarden.widget.ClearEditText;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes5.dex */
public class ForumSectionActivity extends BaseActivity {
    private ClearEditText editTxt;
    private final List<ForumSectionIndexBean.DataBean> parentList = new ArrayList();
    public RecyclerView recycle;
    public BaseQuickAdapter<ForumSectionIndexBean.DataBean, BaseViewHolder> schooladapter;

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(View view) {
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$init$1(View view) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$init$2(TextView textView, int i2, KeyEvent keyEvent) {
        if (i2 != 3) {
            return false;
        }
        InputMethodManager inputMethodManager = (InputMethodManager) textView.getContext().getSystemService("input_method");
        if (inputMethodManager.isActive()) {
            inputMethodManager.hideSoftInputFromWindow(textView.getApplicationWindowToken(), 0);
        }
        if (this.editTxt.getText().toString().equals("")) {
            return true;
        }
        showSchoolData("0", this.editTxt.getText().toString());
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$3(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
        if (this.parentList.size() <= i2) {
            return;
        }
        for (int i3 = 0; i3 < this.parentList.size(); i3++) {
            this.parentList.get(i3).setSelected(0);
        }
        this.parentList.get(i2).setSelected(1);
        baseQuickAdapter.notifyDataSetChanged();
        showSchoolData(this.parentList.get(i2).getId(), "");
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        ImageView imageView = (ImageView) findViewById(R.id.icon_left2);
        this.editTxt = (ClearEditText) findViewById(R.id.editTxt);
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.forum.n
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12408c.lambda$init$0(view);
            }
        });
        this.editTxt.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.forum.o
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ForumSectionActivity.lambda$init$1(view);
            }
        });
        this.editTxt.addTextChangedListener(new TextWatcher() { // from class: com.psychiatrygarden.activity.forum.ForumSectionActivity.1
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable s2) {
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence s2, int start, int count, int after) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence s2, int start, int before, int count) {
                ForumSectionActivity.this.setEditData(s2.toString());
            }
        });
        this.editTxt.setOnEditorActionListener(new TextView.OnEditorActionListener() { // from class: com.psychiatrygarden.activity.forum.p
            @Override // android.widget.TextView.OnEditorActionListener
            public final boolean onEditorAction(TextView textView, int i2, KeyEvent keyEvent) {
                return this.f12409c.lambda$init$2(textView, i2, keyEvent);
            }
        });
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycle);
        this.recycle = recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        BaseQuickAdapter<ForumSectionIndexBean.DataBean, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<ForumSectionIndexBean.DataBean, BaseViewHolder>(R.layout.layout_forum_parent, this.parentList) { // from class: com.psychiatrygarden.activity.forum.ForumSectionActivity.2
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
        this.schooladapter.setOnItemClickListener(new OnItemClickListener() { // from class: com.psychiatrygarden.activity.forum.q
            @Override // com.chad.library.adapter.base.listener.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i2) {
                this.f12410c.lambda$init$3(baseQuickAdapter2, view, i2);
            }
        });
        showSchoolData("1", "");
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("切换版块");
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_forun_section);
    }

    public void setEditData(String fifter) {
        if (TextUtils.isEmpty(fifter)) {
            showSchoolData("1", "");
        } else {
            showSchoolData("0", fifter);
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }

    public void showSchoolData(String pid, String keywords) {
        FragmentTransaction fragmentTransactionBeginTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransactionBeginTransaction.replace(R.id.sec_fragment, ForumSectionFragmment.getInstent(pid, keywords));
        fragmentTransactionBeginTransaction.commitNowAllowingStateLoss();
    }
}
