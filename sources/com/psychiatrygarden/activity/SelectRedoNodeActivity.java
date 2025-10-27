package com.psychiatrygarden.activity;

import android.content.Intent;
import android.os.Bundle;
import com.psychiatrygarden.fragmenthome.SelectKnowledgeNodeFragment;
import com.psychiatrygarden.fragmenthome.knowledge.KnowledgeQuestionListFragment;
import com.yikaobang.yixue.R;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\b\u001a\u00020\tH\u0016J\u0012\u0010\n\u001a\u00020\t2\b\u0010\u000b\u001a\u0004\u0018\u00010\u0006H\u0016J\b\u0010\f\u001a\u00020\tH\u0016J\b\u0010\r\u001a\u00020\tH\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lcom/psychiatrygarden/activity/SelectRedoNodeActivity;", "Lcom/psychiatrygarden/activity/BaseActivity;", "()V", "studyPlanSelect", "", "treeId", "", "type", "init", "", "onEventMainThread", "str", "setContentView", "setListenerForWidget", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final class SelectRedoNodeActivity extends BaseActivity {
    private boolean studyPlanSelect;

    @NotNull
    private String type = "";

    @NotNull
    private String treeId = "";

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        Intent intent = getIntent();
        String stringExtra = intent != null ? intent.getStringExtra(KnowledgeQuestionListFragment.EXTRA_TREE_ID) : null;
        if (stringExtra == null) {
            stringExtra = "";
        }
        this.treeId = stringExtra;
        Intent intent2 = getIntent();
        String stringExtra2 = intent2 != null ? intent2.getStringExtra("type") : null;
        this.type = stringExtra2 != null ? stringExtra2 : "";
        Intent intent3 = getIntent();
        this.studyPlanSelect = intent3 != null ? intent3.getBooleanExtra("studyPlanSelect", false) : false;
        SelectKnowledgeNodeFragment selectKnowledgeNodeFragment = new SelectKnowledgeNodeFragment();
        Bundle bundle = new Bundle();
        if (getIntent().getExtras() != null) {
            Bundle extras = getIntent().getExtras();
            Intrinsics.checkNotNull(extras);
            if (!extras.isEmpty()) {
                bundle.putAll(getIntent().getExtras());
            }
        }
        bundle.putString(KnowledgeQuestionListFragment.EXTRA_TREE_ID, this.treeId);
        bundle.putString("type", this.type);
        bundle.putBoolean("studyPlanSelect", this.studyPlanSelect);
        selectKnowledgeNodeFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().add(R.id.fl_container, selectKnowledgeNodeFragment).commit();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(@Nullable String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.act_select_redo_node);
        this.mActionBar.hide();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}
