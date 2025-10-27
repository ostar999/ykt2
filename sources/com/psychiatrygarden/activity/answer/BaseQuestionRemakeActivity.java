package com.psychiatrygarden.activity.answer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.aliyun.vod.common.utils.UriUtil;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.answer.BaseQuestionRemakeFragment;
import com.yikaobang.yixue.R;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u0000  2\u00020\u00012\u00020\u0002:\u0001 B\u0005¢\u0006\u0002\u0010\u0003J\b\u0010\u0013\u001a\u00020\u0014H\u0016J\u0010\u0010\u0015\u001a\u00020\u00142\u0006\u0010\u0016\u001a\u00020\u0017H\u0016J\u0012\u0010\u0018\u001a\u00020\u00142\b\u0010\u0019\u001a\u0004\u0018\u00010\u001aH\u0014J\u0012\u0010\u001b\u001a\u00020\u00142\b\u0010\u001c\u001a\u0004\u0018\u00010\u0005H\u0016J\b\u0010\u001d\u001a\u00020\u0014H\u0002J\b\u0010\u001e\u001a\u00020\u0014H\u0016J\b\u0010\u001f\u001a\u00020\u0014H\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0082D¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u0004\u0018\u00010\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082.¢\u0006\u0002\n\u0000R\u0010\u0010\r\u001a\u0004\u0018\u00010\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u000e\u001a\u0004\u0018\u00010\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\fX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\fX\u0082.¢\u0006\u0002\n\u0000R\u0010\u0010\u0011\u001a\u0004\u0018\u00010\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0012\u001a\u0004\u0018\u00010\u0005X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006!"}, d2 = {"Lcom/psychiatrygarden/activity/answer/BaseQuestionRemakeActivity;", "Lcom/psychiatrygarden/activity/BaseActivity;", "Landroid/view/View$OnClickListener;", "()V", "ALL_SELECT", "", "CANCEL_ALL_SELECT", UriUtil.QUERY_CATEGORY, "chapterId", "childFragment", "Lcom/psychiatrygarden/activity/answer/BaseQuestionRemakeFragment;", "downTv", "Landroid/widget/TextView;", "identityId", "moduleType", "tvExportAllSelect", "tvExportTitle", "type", "unitId", "init", "", "onClick", "v", "Landroid/view/View;", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onEventMainThread", "str", "selectAll", "setContentView", "setListenerForWidget", "Companion", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final class BaseQuestionRemakeActivity extends BaseActivity implements View.OnClickListener {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @NotNull
    private final String ALL_SELECT = "全选";

    @NotNull
    private final String CANCEL_ALL_SELECT = "取消全选";

    @Nullable
    private String category;

    @Nullable
    private String chapterId;

    @Nullable
    private BaseQuestionRemakeFragment childFragment;
    private TextView downTv;

    @Nullable
    private String identityId;

    @Nullable
    private String moduleType;
    private TextView tvExportAllSelect;
    private TextView tvExportTitle;

    @Nullable
    private String type;

    @Nullable
    private String unitId;

    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002JJ\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\b2\b\u0010\t\u001a\u0004\u0018\u00010\b2\b\u0010\n\u001a\u0004\u0018\u00010\b2\b\u0010\u000b\u001a\u0004\u0018\u00010\b2\b\u0010\f\u001a\u0004\u0018\u00010\b2\b\u0010\r\u001a\u0004\u0018\u00010\b¨\u0006\u000e"}, d2 = {"Lcom/psychiatrygarden/activity/answer/BaseQuestionRemakeActivity$Companion;", "", "()V", "gotToBaseQuestionRemakeActivity", "", com.umeng.analytics.pro.d.R, "Landroid/app/Activity;", "identityId", "", "moduleType", UriUtil.QUERY_CATEGORY, "type", "unitId", "chapterId", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final void gotToBaseQuestionRemakeActivity(@NotNull Activity context, @Nullable String identityId, @Nullable String moduleType, @Nullable String category, @Nullable String type, @Nullable String unitId, @Nullable String chapterId) {
            Intrinsics.checkNotNullParameter(context, "context");
            Intent intent = new Intent(context, (Class<?>) BaseQuestionRemakeActivity.class);
            intent.putExtra("identityId", identityId);
            intent.putExtra("moduleType", moduleType);
            intent.putExtra(UriUtil.QUERY_CATEGORY, category);
            intent.putExtra("type", type);
            intent.putExtra("unitId", unitId);
            intent.putExtra("chapterId", chapterId);
            intent.putExtra("baseQuestionBankRedo", true);
            context.startActivityForResult(intent, 1001);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void init$lambda$0(BaseQuestionRemakeActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void init$lambda$1(BaseQuestionRemakeActivity this$0, int i2) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        BaseQuestionRemakeFragment baseQuestionRemakeFragment = this$0.childFragment;
        Intrinsics.checkNotNull(baseQuestionRemakeFragment);
        boolean z2 = i2 < baseQuestionRemakeFragment.getNoteAllCount();
        TextView textView = this$0.tvExportAllSelect;
        TextView textView2 = null;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvExportAllSelect");
            textView = null;
        }
        textView.setText(z2 ? this$0.ALL_SELECT : this$0.CANCEL_ALL_SELECT);
        if (i2 == 0) {
            TextView textView3 = this$0.tvExportTitle;
            if (textView3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("tvExportTitle");
                textView3 = null;
            }
            textView3.setText("章节选择");
        } else {
            TextView textView4 = this$0.tvExportTitle;
            if (textView4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("tvExportTitle");
                textView4 = null;
            }
            textView4.setText("已选择 " + i2 + " 个章节");
        }
        TextView textView5 = this$0.downTv;
        if (textView5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("downTv");
        } else {
            textView2 = textView5;
        }
        textView2.setEnabled(i2 > 0);
    }

    private final void selectAll() {
        if (this.childFragment != null) {
            TextView textView = this.tvExportAllSelect;
            if (textView == null) {
                Intrinsics.throwUninitializedPropertyAccessException("tvExportAllSelect");
                textView = null;
            }
            if (Intrinsics.areEqual(this.ALL_SELECT, textView.getText().toString())) {
                BaseQuestionRemakeFragment baseQuestionRemakeFragment = this.childFragment;
                Intrinsics.checkNotNull(baseQuestionRemakeFragment);
                baseQuestionRemakeFragment.selectOperaAll(Boolean.TRUE);
            } else {
                BaseQuestionRemakeFragment baseQuestionRemakeFragment2 = this.childFragment;
                Intrinsics.checkNotNull(baseQuestionRemakeFragment2);
                baseQuestionRemakeFragment2.selectOperaAll(Boolean.FALSE);
            }
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        Intent intent = getIntent();
        if (intent != null) {
            this.identityId = intent.getStringExtra("identityId");
            this.moduleType = intent.getStringExtra("moduleType");
            this.category = intent.getStringExtra(UriUtil.QUERY_CATEGORY);
            this.type = intent.getStringExtra("type");
            this.unitId = intent.getStringExtra("unitId");
            this.chapterId = intent.getStringExtra("chapterId");
        }
        ImageView imageView = (ImageView) findViewById(R.id.iv_knowledge_edit_back);
        View viewFindViewById = findViewById(R.id.tv_export_title);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(R.id.tv_export_title)");
        this.tvExportTitle = (TextView) viewFindViewById;
        View viewFindViewById2 = findViewById(R.id.downTv);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "findViewById(R.id.downTv)");
        this.downTv = (TextView) viewFindViewById2;
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.answer.n0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                BaseQuestionRemakeActivity.init$lambda$0(this.f11046c, view);
            }
        });
        View viewFindViewById3 = findViewById(R.id.tv_export_all_select);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById3, "findViewById(R.id.tv_export_all_select)");
        TextView textView = (TextView) viewFindViewById3;
        this.tvExportAllSelect = textView;
        TextView textView2 = null;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvExportAllSelect");
            textView = null;
        }
        textView.setOnClickListener(this);
        TextView textView3 = this.downTv;
        if (textView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("downTv");
        } else {
            textView2 = textView3;
        }
        textView2.setOnClickListener(this);
        BaseQuestionRemakeFragment baseQuestionRemakeFragment = new BaseQuestionRemakeFragment();
        this.childFragment = baseQuestionRemakeFragment;
        Intrinsics.checkNotNull(baseQuestionRemakeFragment);
        Bundle bundle = baseQuestionRemakeFragment.getBundle(this.identityId, this.moduleType, this.category, this.type, this.unitId, this.chapterId);
        BaseQuestionRemakeFragment baseQuestionRemakeFragment2 = this.childFragment;
        Intrinsics.checkNotNull(baseQuestionRemakeFragment2);
        baseQuestionRemakeFragment2.setArguments(bundle);
        if (findFragment(BaseQuestionRemakeFragment.class) == null) {
            BaseQuestionRemakeFragment baseQuestionRemakeFragment3 = this.childFragment;
            Intrinsics.checkNotNull(baseQuestionRemakeFragment3);
            loadRootFragment(R.id.fragmentKnowledgeEdit, baseQuestionRemakeFragment3);
        } else {
            replaceFragment(this.childFragment, false);
        }
        BaseQuestionRemakeFragment baseQuestionRemakeFragment4 = this.childFragment;
        Intrinsics.checkNotNull(baseQuestionRemakeFragment4);
        baseQuestionRemakeFragment4.setSelectNumChangeListener(new BaseQuestionRemakeFragment.SelectNumChangeListener() { // from class: com.psychiatrygarden.activity.answer.o0
            @Override // com.psychiatrygarden.activity.answer.BaseQuestionRemakeFragment.SelectNumChangeListener
            public final void selectNum(int i2) {
                BaseQuestionRemakeActivity.init$lambda$1(this.f11048a, i2);
            }
        });
    }

    @Override // android.view.View.OnClickListener
    public void onClick(@NotNull View v2) {
        Intrinsics.checkNotNullParameter(v2, "v");
        int id = v2.getId();
        if (id != R.id.downTv) {
            if (id != R.id.tv_export_all_select) {
                return;
            }
            selectAll();
        } else {
            BaseQuestionRemakeFragment baseQuestionRemakeFragment = this.childFragment;
            Intrinsics.checkNotNull(baseQuestionRemakeFragment);
            baseQuestionRemakeFragment.btnCommit();
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActionBar.hide();
        setNewStyleStatusBarColor();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(@Nullable String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_base_question_remake);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}
