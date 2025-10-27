package com.psychiatrygarden.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.psychiatrygarden.fragmenthome.QuestionSetListFragment;
import com.yikaobang.yixue.R;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u0000 \u00102\u00020\u0001:\u0001\u0010B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0007\u001a\u00020\bH\u0016J\u0012\u0010\t\u001a\u00020\b2\b\u0010\n\u001a\u0004\u0018\u00010\u000bH\u0014J\u0012\u0010\f\u001a\u00020\b2\b\u0010\r\u001a\u0004\u0018\u00010\u0006H\u0016J\b\u0010\u000e\u001a\u00020\bH\u0016J\b\u0010\u000f\u001a\u00020\bH\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.¢\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0011"}, d2 = {"Lcom/psychiatrygarden/activity/QuestionSetListActivity;", "Lcom/psychiatrygarden/activity/BaseActivity;", "()V", "actionbarTitle", "Landroid/widget/TextView;", "type", "", "init", "", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onEventMainThread", "str", "setContentView", "setListenerForWidget", "Companion", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final class QuestionSetListActivity extends BaseActivity {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @NotNull
    public static final String TYPE_LIST_FLAG = "type.flag";

    @NotNull
    public static final String TYPE_VALUE_MO_KAO = "type.exam";

    @NotNull
    public static final String TYPE_VALUE_TI_DAN = "type.ti.dan";
    private TextView actionbarTitle;

    @Nullable
    private String type = "";

    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0004R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"Lcom/psychiatrygarden/activity/QuestionSetListActivity$Companion;", "", "()V", "TYPE_LIST_FLAG", "", "TYPE_VALUE_MO_KAO", "TYPE_VALUE_TI_DAN", "navigationToQuestionListActivity", "", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "type", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final void navigationToQuestionListActivity(@NotNull Context context, @NotNull String type) {
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(type, "type");
            Intent intent = new Intent(context, (Class<?>) QuestionSetListActivity.class);
            intent.putExtra(QuestionSetListActivity.TYPE_LIST_FLAG, type);
            context.startActivity(intent);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void init$lambda$1(QuestionSetListActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.finish();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        View viewFindViewById = findViewById(R.id.actionbarTitle);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(R.id.actionbarTitle)");
        this.actionbarTitle = (TextView) viewFindViewById;
        ((ImageView) findViewById(R.id.actionbarBack)).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.tf
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                QuestionSetListActivity.init$lambda$1(this.f13952c, view);
            }
        });
        TextView textView = null;
        if (Intrinsics.areEqual(TYPE_VALUE_TI_DAN, this.type)) {
            TextView textView2 = this.actionbarTitle;
            if (textView2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("actionbarTitle");
            } else {
                textView = textView2;
            }
            textView.setText("题单");
        } else {
            TextView textView3 = this.actionbarTitle;
            if (textView3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("actionbarTitle");
            } else {
                textView = textView3;
            }
            textView.setText("模考");
        }
        QuestionSetListFragment questionSetListFragment = new QuestionSetListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TYPE_LIST_FLAG, this.type);
        questionSetListFragment.setArguments(bundle);
        if (findFragment(QuestionSetListFragment.class) == null) {
            loadRootFragment(R.id.fragment, questionSetListFragment);
        } else {
            replaceFragment(questionSetListFragment, false);
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActionBar.hide();
        setNewStyleStatusBarColor2();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(@Nullable String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_question_set_list);
        Intent intent = getIntent();
        if (intent != null) {
            this.type = intent.getStringExtra(TYPE_LIST_FLAG);
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}
