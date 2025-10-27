package com.psychiatrygarden.activity.knowledge;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.hyphenate.easeui.utils.StatusBarCompat;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.utils.StatusBarUtil;
import com.psychiatrygarden.widget.CustomEmptyView;
import com.yikaobang.yixue.R;
import com.ykb.ebook.extensions.ViewExtensionsKt;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u0000 \u00122\u00020\u0001:\u0001\u0012B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\t\u001a\u00020\nH\u0016J\u0012\u0010\u000b\u001a\u00020\n2\b\u0010\f\u001a\u0004\u0018\u00010\rH\u0014J\u0012\u0010\u000e\u001a\u00020\n2\b\u0010\u000f\u001a\u0004\u0018\u00010\bH\u0016J\b\u0010\u0010\u001a\u00020\nH\u0016J\b\u0010\u0011\u001a\u00020\nH\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"Lcom/psychiatrygarden/activity/knowledge/KnowledgeRuleActivity;", "Lcom/psychiatrygarden/activity/BaseActivity;", "()V", "emptyView", "Lcom/psychiatrygarden/widget/CustomEmptyView;", "lyBarLayout", "Landroid/widget/RelativeLayout;", "ruleUrl", "", "init", "", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onEventMainThread", "str", "setContentView", "setListenerForWidget", "Companion", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final class KnowledgeRuleActivity extends BaseActivity {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);
    private CustomEmptyView emptyView;
    private RelativeLayout lyBarLayout;

    @Nullable
    private String ruleUrl = "";

    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\"\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\b¨\u0006\n"}, d2 = {"Lcom/psychiatrygarden/activity/knowledge/KnowledgeRuleActivity$Companion;", "", "()V", "navigationToKnowledgeRuleActivity", "", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "ruleUrl", "", "title", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static /* synthetic */ void navigationToKnowledgeRuleActivity$default(Companion companion, Context context, String str, String str2, int i2, Object obj) {
            if ((i2 & 4) != 0) {
                str2 = null;
            }
            companion.navigationToKnowledgeRuleActivity(context, str, str2);
        }

        public final void navigationToKnowledgeRuleActivity(@NotNull Context context, @NotNull String ruleUrl, @Nullable String title) {
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(ruleUrl, "ruleUrl");
            Intent intent = new Intent(context, (Class<?>) KnowledgeRuleActivity.class);
            intent.putExtra("rule_url", ruleUrl);
            if (!(title == null || title.length() == 0)) {
                intent.putExtra("title", title);
            }
            context.startActivity(intent);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void init$lambda$0(KnowledgeRuleActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.finish();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        View viewFindViewById = findViewById(R.id.lyBarLayout);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(R.id.lyBarLayout)");
        this.lyBarLayout = (RelativeLayout) viewFindViewById;
        View viewFindViewById2 = findViewById(R.id.empty_view);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "findViewById(R.id.empty_view)");
        this.emptyView = (CustomEmptyView) viewFindViewById2;
        int statusBarHeight = StatusBarUtil.getStatusBarHeight(this.mContext);
        RelativeLayout relativeLayout = this.lyBarLayout;
        RelativeLayout relativeLayout2 = null;
        if (relativeLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("lyBarLayout");
            relativeLayout = null;
        }
        ViewGroup.LayoutParams layoutParams = relativeLayout.getLayoutParams();
        Intrinsics.checkNotNull(layoutParams, "null cannot be cast to non-null type android.widget.LinearLayout.LayoutParams");
        LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) layoutParams;
        layoutParams2.topMargin = statusBarHeight;
        RelativeLayout relativeLayout3 = this.lyBarLayout;
        if (relativeLayout3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("lyBarLayout");
        } else {
            relativeLayout2 = relativeLayout3;
        }
        relativeLayout2.setLayoutParams(layoutParams2);
        View viewFindViewById3 = findViewById(R.id.actionbarBack);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById3, "findViewById(R.id.actionbarBack)");
        ((ImageView) viewFindViewById3).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.knowledge.k0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                KnowledgeRuleActivity.init$lambda$0(this.f12623c, view);
            }
        });
        String str = this.ruleUrl;
        Intrinsics.checkNotNull(str, "null cannot be cast to non-null type kotlin.String");
        final SubsamplingScaleImageView subsamplingScaleImageView = (SubsamplingScaleImageView) findViewById(R.id.bigImgView);
        Glide.with(ProjectApp.instance()).downloadOnly().load((Object) GlideUtils.generateUrl(str)).listener(new RequestListener<File>() { // from class: com.psychiatrygarden.activity.knowledge.KnowledgeRuleActivity.init.2
            @Override // com.bumptech.glide.request.RequestListener
            public boolean onLoadFailed(@Nullable GlideException e2, @NotNull Object model, @NotNull Target<File> target, boolean isFirstResource) {
                Intrinsics.checkNotNullParameter(model, "model");
                Intrinsics.checkNotNullParameter(target, "target");
                return true;
            }

            @Override // com.bumptech.glide.request.RequestListener
            public boolean onResourceReady(@Nullable File resource, @NotNull Object model, @NotNull Target<File> target, @NotNull DataSource dataSource, boolean isFirstResource) {
                Intrinsics.checkNotNullParameter(model, "model");
                Intrinsics.checkNotNullParameter(target, "target");
                Intrinsics.checkNotNullParameter(dataSource, "dataSource");
                return false;
            }
        }).into((RequestBuilder<File>) new SimpleTarget<File>() { // from class: com.psychiatrygarden.activity.knowledge.KnowledgeRuleActivity.init.3
            @Override // com.bumptech.glide.request.target.Target
            public /* bridge */ /* synthetic */ void onResourceReady(Object obj, Transition transition) {
                onResourceReady((File) obj, (Transition<? super File>) transition);
            }

            public void onResourceReady(@NotNull File resource, @Nullable Transition<? super File> transition) {
                Intrinsics.checkNotNullParameter(resource, "resource");
                subsamplingScaleImageView.setImage(ImageSource.uri(Uri.fromFile(resource)));
                CustomEmptyView customEmptyView = this.emptyView;
                CustomEmptyView customEmptyView2 = null;
                if (customEmptyView == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("emptyView");
                    customEmptyView = null;
                }
                customEmptyView.stopAnim();
                CustomEmptyView customEmptyView3 = this.emptyView;
                if (customEmptyView3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("emptyView");
                } else {
                    customEmptyView2 = customEmptyView3;
                }
                ViewExtensionsKt.gone(customEmptyView2);
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(@Nullable Bundle savedInstanceState) throws IllegalAccessException, NoSuchFieldException, NoSuchMethodException, ClassNotFoundException, SecurityException, IllegalArgumentException, InvocationTargetException {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setStatusBarTranslucent(this, false);
        StatusBarCompat.setLightStatusBar(this, true);
        this.mActionBar.hide();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(@Nullable String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_knowledge_rule);
        setTitle("活动规则");
        String stringExtra = getIntent().getStringExtra("title");
        if (!(stringExtra == null || stringExtra.length() == 0)) {
            ((TextView) findViewById(R.id.actionbarTitle)).setText(stringExtra);
        }
        this.ruleUrl = getIntent().getStringExtra("rule_url");
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}
