package com.psychiatrygarden.activity.forum;

import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.forum.fragment.ForumIndexFragment;
import com.yikaobang.yixue.R;

/* loaded from: classes5.dex */
public class ForumSearchInfoActivity extends BaseActivity {
    public String group_id;

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.group_id = getIntent().getExtras().getString("group_id");
        if (findFragment(ForumIndexFragment.class) == null) {
            loadRootFragment(R.id.fsi, ForumIndexFragment.newInstance(this.group_id));
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_forum_search_indo);
        this.mActionBar.hide();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}
