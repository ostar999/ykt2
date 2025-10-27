package com.ykb.ebook.activity;

import android.annotation.SuppressLint;
import android.content.AppCtxKt;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.ColorResourcesKt;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.view.WindowManager;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.mobile.auth.gatewayauth.Constant;
import com.ykb.ebook.R;
import com.ykb.ebook.adapter.CommonViewPagerAdapter;
import com.ykb.ebook.base.BaseActivity;
import com.ykb.ebook.common.EventBus;
import com.ykb.ebook.common.EventCallback;
import com.ykb.ebook.common.ReadConfig;
import com.ykb.ebook.databinding.ActivityAllTagBinding;
import com.ykb.ebook.event.ImageUploadSuccessEvent;
import com.ykb.ebook.fragment.DrawLineFragment;
import com.ykb.ebook.fragment.NotesFragment;
import com.ykb.ebook.fragment.ParagraphCommentFragment;
import java.util.ArrayList;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.apache.commons.codec.language.bm.Languages;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000j\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u00032\u00020\u0004B\u0005¢\u0006\u0002\u0010\u0005J\b\u0010\u0018\u001a\u00020\u0019H\u0002J\u0012\u0010\u001a\u001a\u00020\u00192\b\u0010\u001b\u001a\u0004\u0018\u00010\u001cH\u0015J\"\u0010\u001d\u001a\u00020\u00192\u0006\u0010\u001e\u001a\u00020\u000f2\u0006\u0010\u001f\u001a\u00020\u000f2\b\u0010 \u001a\u0004\u0018\u00010!H\u0014J\b\u0010\"\u001a\u00020\u0019H\u0014J\u001a\u0010#\u001a\u00020\u00192\u0006\u0010$\u001a\u00020\u000f2\b\u0010%\u001a\u0004\u0018\u00010&H\u0016J\b\u0010'\u001a\u00020\u0019H\u0014J\u0012\u0010(\u001a\u00020\u00192\b\u0010)\u001a\u0004\u0018\u00010*H\u0016J\u0012\u0010+\u001a\u00020\u00192\b\u0010)\u001a\u0004\u0018\u00010*H\u0016J\u0012\u0010,\u001a\u00020\u00192\b\u0010)\u001a\u0004\u0018\u00010*H\u0016J\u0006\u0010-\u001a\u00020\u0019J\b\u0010.\u001a\u00020\u0019H\u0002R\u001b\u0010\u0006\u001a\u00020\u00028TX\u0094\u0084\u0002¢\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u001e\u0010\u0010\u001a\u0012\u0012\u0004\u0012\u00020\u00120\u0011j\b\u0012\u0004\u0012\u00020\u0012`\u0013X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00150\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082.¢\u0006\u0002\n\u0000¨\u0006/"}, d2 = {"Lcom/ykb/ebook/activity/AllTagActivity;", "Lcom/ykb/ebook/base/BaseActivity;", "Lcom/ykb/ebook/databinding/ActivityAllTagBinding;", "Lcom/google/android/material/tabs/TabLayout$OnTabSelectedListener;", "Lcom/ykb/ebook/common/EventCallback;", "()V", "binding", "getBinding", "()Lcom/ykb/ebook/databinding/ActivityAllTagBinding;", "binding$delegate", "Lkotlin/Lazy;", "fragments", "", "Landroidx/fragment/app/Fragment;", "selectPosition", "", "tabViews", "Ljava/util/ArrayList;", "Landroid/view/View;", "Lkotlin/collections/ArrayList;", "tabs", "", "viewPagerAdapter", "Lcom/ykb/ebook/adapter/CommonViewPagerAdapter;", "hideSystemUI", "", "onActivityCreated", "savedInstanceState", "Landroid/os/Bundle;", "onActivityResult", Constant.LOGIN_ACTIVITY_REQUEST_CODE, "resultCode", "data", "Landroid/content/Intent;", "onDestroy", "onMessage", "what", Languages.ANY, "", "onResume", "onTabReselected", "tab", "Lcom/google/android/material/tabs/TabLayout$Tab;", "onTabSelected", "onTabUnselected", "upSystemUiVisibility", "updateStatusBar", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nAllTagActivity.kt\nKotlin\n*S Kotlin\n*F\n+ 1 AllTagActivity.kt\ncom/ykb/ebook/activity/AllTagActivity\n+ 2 ActivityViewBindings.kt\ncom/ykb/ebook/extensions/ActivityViewBindingsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 4 ColorResources.kt\nsplitties/resources/ColorResourcesKt\n+ 5 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,251:1\n13#2,10:252\n1#3:262\n42#4:263\n1855#5,2:264\n*S KotlinDebug\n*F\n+ 1 AllTagActivity.kt\ncom/ykb/ebook/activity/AllTagActivity\n*L\n43#1:252,10\n150#1:263\n191#1:264,2\n*E\n"})
/* loaded from: classes6.dex */
public final class AllTagActivity extends BaseActivity<ActivityAllTagBinding> implements TabLayout.OnTabSelectedListener, EventCallback {

    /* renamed from: binding$delegate, reason: from kotlin metadata */
    @NotNull
    private final Lazy binding;
    private int selectPosition;
    private CommonViewPagerAdapter viewPagerAdapter;

    @NotNull
    private final List<Fragment> fragments = CollectionsKt__CollectionsKt.listOf((Object[]) new Fragment[]{new DrawLineFragment(), new NotesFragment(), new ParagraphCommentFragment()});

    @NotNull
    private final List<String> tabs = CollectionsKt__CollectionsKt.listOf((Object[]) new String[]{"划线", "笔记", "段评"});

    @NotNull
    private final ArrayList<View> tabViews = new ArrayList<>();

    public AllTagActivity() {
        final boolean z2 = false;
        this.binding = LazyKt__LazyJVMKt.lazy(LazyThreadSafetyMode.SYNCHRONIZED, (Function0) new Function0<ActivityAllTagBinding>() { // from class: com.ykb.ebook.activity.AllTagActivity$special$$inlined$viewBindingActivity$default$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            @NotNull
            public final ActivityAllTagBinding invoke() {
                LayoutInflater layoutInflater = this.getLayoutInflater();
                Intrinsics.checkNotNullExpressionValue(layoutInflater, "layoutInflater");
                ActivityAllTagBinding activityAllTagBindingInflate = ActivityAllTagBinding.inflate(layoutInflater);
                if (z2) {
                    this.setContentView(activityAllTagBindingInflate.getRoot());
                }
                return activityAllTagBindingInflate;
            }
        });
    }

    private final void hideSystemUI() {
        int iColor;
        Window window = getWindow();
        ReadConfig readConfig = ReadConfig.INSTANCE;
        if (readConfig.getColorMode() == 1) {
            iColor = AppCtxKt.getAppCtx().getColor(R.color.color_EDE2C3);
        } else {
            iColor = ColorResourcesKt.color(AppCtxKt.getAppCtx(), readConfig.getColorMode() != 2 ? R.color.color_f9fafb : R.color.color_171C2D);
        }
        window.setNavigationBarColor(iColor);
        int i2 = Build.VERSION.SDK_INT;
        if (i2 >= 30) {
            getWindow().setDecorFitsSystemWindows(false);
            WindowInsetsController insetsController = getWindow().getInsetsController();
            if (insetsController != null) {
                insetsController.hide(WindowInsets.Type.statusBars() | WindowInsets.Type.navigationBars());
                insetsController.setSystemBarsBehavior(2);
            }
        } else {
            getWindow().getDecorView().setSystemUiVisibility(4098);
        }
        if (i2 >= 28) {
            WindowManager.LayoutParams attributes = getWindow().getAttributes();
            Intrinsics.checkNotNullExpressionValue(attributes, "getWindow().getAttributes()");
            attributes.layoutInDisplayCutoutMode = 1;
            getWindow().setAttributes(attributes);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onActivityCreated$lambda$0(AllTagActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onActivityCreated$lambda$3(AllTagActivity this$0, TabLayout.Tab tab, int i2) {
        TextView textView;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(tab, "tab");
        tab.setCustomView(R.layout.tab_all_tag);
        View customView = tab.getCustomView();
        if (customView != null) {
            this$0.tabViews.add(customView);
        }
        if (customView == null || (textView = (TextView) customView.findViewById(R.id.tv_title)) == null) {
            return;
        }
        textView.setText(this$0.tabs.get(i2));
        if (ReadConfig.INSTANCE.getColorMode() == 2) {
            textView.setTextColor(this$0.getResources().getColor(R.color.color_7380a9));
        } else {
            textView.setTextColor(this$0.getResources().getColor(R.color.color_909090));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onActivityCreated$lambda$4(AllTagActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (this$0.getBinding().viewPager.getCurrentItem() == 1) {
            SearchNoteAct.INSTANCE.newIntent(this$0);
        } else {
            SearchParagraphAct.INSTANCE.newIntent(this$0);
        }
    }

    private final void updateStatusBar() {
        getWindow().addFlags(Integer.MIN_VALUE);
        getWindow().clearFlags(67108864);
        getWindow().setStatusBarColor(0);
        getWindow().getDecorView().setSystemUiVisibility(ReadConfig.INSTANCE.getColorMode() < 2 ? 8192 : 256);
    }

    @Override // com.ykb.ebook.base.BaseActivity
    @SuppressLint({"UseCompatLoadingForDrawables"})
    public void onActivityCreated(@Nullable Bundle savedInstanceState) throws Resources.NotFoundException {
        EventBus.INSTANCE.register(this);
        this.selectPosition = getIntent().getIntExtra("position", 0);
        updateStatusBar();
        hideSystemUI();
        ReadConfig readConfig = ReadConfig.INSTANCE;
        int colorMode = readConfig.getColorMode();
        if (colorMode == 0) {
            getBinding().getRoot().setBackground(new ColorDrawable(ContextCompat.getColor(this, R.color.white)));
            getBinding().imgBack.setColorFilter(ContextCompat.getColor(this, R.color.black), PorterDuff.Mode.SRC_IN);
        } else if (colorMode == 1) {
            getBinding().getRoot().setBackground(getDrawable(R.drawable.bg_content_view));
            getBinding().imgBack.setColorFilter(ContextCompat.getColor(this, R.color.black), PorterDuff.Mode.SRC_IN);
        } else if (colorMode == 2) {
            getBinding().getRoot().setBackground(new ColorDrawable(ContextCompat.getColor(this, R.color.color_121622)));
            getBinding().imgBack.setColorFilter(ContextCompat.getColor(this, R.color.color_7380a9), PorterDuff.Mode.SRC_IN);
        }
        getBinding().imgBack.setOnClickListener(new View.OnClickListener() { // from class: com.ykb.ebook.activity.a
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AllTagActivity.onActivityCreated$lambda$0(this.f26094c, view);
            }
        });
        List<Fragment> list = this.fragments;
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        Intrinsics.checkNotNullExpressionValue(supportFragmentManager, "supportFragmentManager");
        Lifecycle lifecycle = getLifecycle();
        Intrinsics.checkNotNullExpressionValue(lifecycle, "lifecycle");
        this.viewPagerAdapter = new CommonViewPagerAdapter(list, supportFragmentManager, lifecycle);
        ViewPager2 viewPager2 = getBinding().viewPager;
        CommonViewPagerAdapter commonViewPagerAdapter = this.viewPagerAdapter;
        if (commonViewPagerAdapter == null) {
            Intrinsics.throwUninitializedPropertyAccessException("viewPagerAdapter");
            commonViewPagerAdapter = null;
        }
        viewPager2.setAdapter(commonViewPagerAdapter);
        if (readConfig.getColorMode() == 2) {
            getBinding().line.setBackground(new ColorDrawable(getColor(R.color.color_1C2134)));
        } else if (readConfig.getColorMode() == 1) {
            getBinding().line.setBackground(new ColorDrawable(getColor(R.color.color_EDE2C3)));
        }
        new TabLayoutMediator(getBinding().tabLayout, getBinding().viewPager, new TabLayoutMediator.TabConfigurationStrategy() { // from class: com.ykb.ebook.activity.b
            @Override // com.google.android.material.tabs.TabLayoutMediator.TabConfigurationStrategy
            public final void onConfigureTab(TabLayout.Tab tab, int i2) {
                AllTagActivity.onActivityCreated$lambda$3(this.f26099a, tab, i2);
            }
        }).attach();
        getBinding().tabLayout.addOnTabSelectedListener((TabLayout.OnTabSelectedListener) this);
        getBinding().viewPager.setCurrentItem(this.selectPosition);
        getBinding().btnSearch.setOnClickListener(new View.OnClickListener() { // from class: com.ykb.ebook.activity.c
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AllTagActivity.onActivityCreated$lambda$4(this.f26105c, view);
            }
        });
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        String stringExtra;
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != 999 || resultCode != -1 || data == null || (stringExtra = data.getStringExtra("imageUrl")) == null) {
            return;
        }
        org.greenrobot.eventbus.EventBus.getDefault().post(new ImageUploadSuccessEvent(stringExtra));
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        EventBus.INSTANCE.unregister(this);
    }

    @Override // com.ykb.ebook.common.EventCallback
    public void onMessage(int what, @Nullable Object any) {
        if (what == 33) {
            finish();
        }
    }

    @Override // com.ykb.ebook.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        upSystemUiVisibility();
    }

    @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
    public void onTabReselected(@Nullable TabLayout.Tab tab) {
    }

    @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
    public void onTabSelected(@Nullable TabLayout.Tab tab) {
        TextView textView;
        View customView = tab != null ? tab.getCustomView() : null;
        if (customView != null && (textView = (TextView) customView.findViewById(R.id.tv_title)) != null) {
            textView.setTextSize(18.0f);
            textView.getPaint().setStrokeWidth(2.5f);
            if (ReadConfig.INSTANCE.getColorMode() == 2) {
                textView.setTextColor(getResources().getColor(R.color.color_7380a9));
            } else {
                textView.setTextColor(getResources().getColor(R.color.color_303030));
            }
            textView.setTypeface(Typeface.DEFAULT_BOLD);
            Drawable drawable = ContextCompat.getDrawable(this, R.mipmap.line_white);
            if (drawable != null) {
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            }
            textView.setCompoundDrawables(null, null, null, drawable);
            textView.setCompoundDrawablePadding(20);
            textView.invalidate();
        }
        if (tab != null) {
            tab.getPosition();
        }
        getBinding().btnSearch.setVisibility(tab != null && tab.getPosition() == 0 ? 8 : 0);
        for (View view : this.tabViews) {
            TextView textView2 = (TextView) view.findViewById(R.id.tv_title);
            textView2.setTextSize(Intrinsics.areEqual(view, customView) ? 18.0f : 16.0f);
            textView2.getPaint().setStrokeWidth(Intrinsics.areEqual(view, customView) ? 2.5f : 1.0f);
            if (ReadConfig.INSTANCE.getColorMode() == 2) {
                textView2.setTextColor(getResources().getColor(Intrinsics.areEqual(view, customView) ? R.color.color_7380a9 : R.color.color_575F79));
            } else {
                textView2.setTextColor(getResources().getColor(Intrinsics.areEqual(view, customView) ? R.color.color_303030 : R.color.color_909090));
            }
            textView2.setTypeface(Intrinsics.areEqual(view, customView) ? Typeface.DEFAULT_BOLD : Typeface.DEFAULT);
            Drawable drawable2 = Intrinsics.areEqual(view, customView) ? ContextCompat.getDrawable(this, R.mipmap.line_white) : null;
            if (drawable2 != null) {
                drawable2.setBounds(0, 0, drawable2.getIntrinsicWidth(), drawable2.getIntrinsicHeight());
            }
            textView2.setCompoundDrawables(null, null, null, drawable2);
            textView2.setCompoundDrawablePadding(20);
            textView2.invalidate();
        }
    }

    @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
    public void onTabUnselected(@Nullable TabLayout.Tab tab) {
        TextView textView;
        View customView = tab != null ? tab.getCustomView() : null;
        if (customView == null || (textView = (TextView) customView.findViewById(R.id.tv_title)) == null) {
            return;
        }
        textView.setTextSize(16.0f);
        textView.getPaint().setStrokeWidth(1.0f);
        textView.setTypeface(textView.getTypeface(), 0);
        if (ReadConfig.INSTANCE.getColorMode() == 2) {
            textView.setTextColor(getColor(R.color.color_575F79));
        } else {
            textView.setTextColor(getColor(R.color.color_909090));
        }
        textView.setCompoundDrawables(null, null, null, null);
        textView.invalidate();
    }

    public final void upSystemUiVisibility() {
        if (Build.VERSION.SDK_INT >= 30) {
            WindowInsetsController insetsController = getWindow().getInsetsController();
            if (insetsController != null) {
                insetsController.show(WindowInsets.Type.statusBars());
                return;
            }
            return;
        }
        int systemUiVisibility = getWindow().getDecorView().getSystemUiVisibility() | 2 | 4096 | 1024;
        getWindow().getDecorView().setSystemUiVisibility(systemUiVisibility);
        getWindow().clearFlags(1024);
        getWindow().getDecorView().setSystemUiVisibility(getWindow().getDecorView().getSystemUiVisibility() & (-5));
        if (ReadConfig.INSTANCE.getColorMode() != 2) {
            getWindow().clearFlags(67108864);
            getWindow().addFlags(Integer.MIN_VALUE);
            getWindow().getDecorView().setSystemUiVisibility(systemUiVisibility | 8192);
        }
    }

    @Override // com.ykb.ebook.base.BaseActivity
    @NotNull
    public ActivityAllTagBinding getBinding() {
        return (ActivityAllTagBinding) this.binding.getValue();
    }
}
