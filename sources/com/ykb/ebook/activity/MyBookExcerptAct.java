package com.ykb.ebook.activity;

import android.content.AppCtxKt;
import android.content.Context;
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
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.ruffian.library.widget.RTextView;
import com.ykb.ebook.R;
import com.ykb.ebook.adapter.CommonViewPagerAdapter;
import com.ykb.ebook.base.BaseActivity;
import com.ykb.ebook.common.ReadConfig;
import com.ykb.ebook.databinding.LayoutMyBookExcerptBinding;
import com.ykb.ebook.extensions.ViewExtensionsKt;
import com.ykb.ebook.fragment.DrawLineFragment;
import com.ykb.ebook.fragment.NotesFragment;
import com.ykb.ebook.fragment.ParagraphCommentFragment;
import com.ykb.ebook.util.Log;
import java.util.ArrayList;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u0000 \"2\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003:\u0001\"B\u0005¢\u0006\u0002\u0010\u0004J\b\u0010\u0017\u001a\u00020\u0018H\u0002J\u0012\u0010\u0019\u001a\u00020\u00182\b\u0010\u001a\u001a\u0004\u0018\u00010\u001bH\u0014J\u0012\u0010\u001c\u001a\u00020\u00182\b\u0010\u001d\u001a\u0004\u0018\u00010\u001eH\u0016J\u0012\u0010\u001f\u001a\u00020\u00182\b\u0010\u001d\u001a\u0004\u0018\u00010\u001eH\u0016J\u0012\u0010 \u001a\u00020\u00182\b\u0010\u001d\u001a\u0004\u0018\u00010\u001eH\u0016J\b\u0010!\u001a\u00020\u0018H\u0002R\u001b\u0010\u0005\u001a\u00020\u00028TX\u0094\u0084\u0002¢\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\u0006\u0010\u0007R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u001e\u0010\u000f\u001a\u0012\u0012\u0004\u0012\u00020\u00110\u0010j\b\u0012\u0004\u0012\u00020\u0011`\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00140\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082.¢\u0006\u0002\n\u0000¨\u0006#"}, d2 = {"Lcom/ykb/ebook/activity/MyBookExcerptAct;", "Lcom/ykb/ebook/base/BaseActivity;", "Lcom/ykb/ebook/databinding/LayoutMyBookExcerptBinding;", "Lcom/google/android/material/tabs/TabLayout$OnTabSelectedListener;", "()V", "binding", "getBinding", "()Lcom/ykb/ebook/databinding/LayoutMyBookExcerptBinding;", "binding$delegate", "Lkotlin/Lazy;", "fragments", "", "Landroidx/fragment/app/Fragment;", "selectPosition", "", "tabViews", "Ljava/util/ArrayList;", "Landroid/view/View;", "Lkotlin/collections/ArrayList;", "tabs", "", "viewPagerAdapter", "Lcom/ykb/ebook/adapter/CommonViewPagerAdapter;", "hideSystemUI", "", "onActivityCreated", "savedInstanceState", "Landroid/os/Bundle;", "onTabReselected", "tab", "Lcom/google/android/material/tabs/TabLayout$Tab;", "onTabSelected", "onTabUnselected", "updateStatusBar", "Companion", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nMyBookExcerptAct.kt\nKotlin\n*S Kotlin\n*F\n+ 1 MyBookExcerptAct.kt\ncom/ykb/ebook/activity/MyBookExcerptAct\n+ 2 ActivityViewBindings.kt\ncom/ykb/ebook/extensions/ActivityViewBindingsKt\n+ 3 ColorResources.kt\nsplitties/resources/ColorResourcesKt\n+ 4 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,243:1\n13#2,10:244\n42#3:254\n42#3:255\n1855#4,2:256\n*S KotlinDebug\n*F\n+ 1 MyBookExcerptAct.kt\ncom/ykb/ebook/activity/MyBookExcerptAct\n*L\n63#1:244,10\n150#1:254\n152#1:255\n196#1:256,2\n*E\n"})
/* loaded from: classes6.dex */
public final class MyBookExcerptAct extends BaseActivity<LayoutMyBookExcerptBinding> implements TabLayout.OnTabSelectedListener {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

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

    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J.\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\b¨\u0006\f"}, d2 = {"Lcom/ykb/ebook/activity/MyBookExcerptAct$Companion;", "", "()V", "newIntent", "", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "bookId", "", "bookName", "bookPic", "bookAuthor", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final void newIntent(@NotNull Context context, @NotNull String bookId, @NotNull String bookName, @NotNull String bookPic, @NotNull String bookAuthor) {
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(bookId, "bookId");
            Intrinsics.checkNotNullParameter(bookName, "bookName");
            Intrinsics.checkNotNullParameter(bookPic, "bookPic");
            Intrinsics.checkNotNullParameter(bookAuthor, "bookAuthor");
            Intent intent = new Intent(context, (Class<?>) MyBookExcerptAct.class);
            intent.putExtra("bookId", bookId);
            intent.putExtra("bookName", bookName);
            intent.putExtra("bookPic", bookPic);
            intent.putExtra("bookAuthor", bookAuthor);
            context.startActivity(intent);
        }
    }

    public MyBookExcerptAct() {
        final boolean z2 = false;
        this.binding = LazyKt__LazyJVMKt.lazy(LazyThreadSafetyMode.SYNCHRONIZED, (Function0) new Function0<LayoutMyBookExcerptBinding>() { // from class: com.ykb.ebook.activity.MyBookExcerptAct$special$$inlined$viewBindingActivity$default$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            @NotNull
            public final LayoutMyBookExcerptBinding invoke() {
                LayoutInflater layoutInflater = this.getLayoutInflater();
                Intrinsics.checkNotNullExpressionValue(layoutInflater, "layoutInflater");
                LayoutMyBookExcerptBinding layoutMyBookExcerptBindingInflate = LayoutMyBookExcerptBinding.inflate(layoutInflater);
                if (z2) {
                    this.setContentView(layoutMyBookExcerptBindingInflate.getRoot());
                }
                return layoutMyBookExcerptBindingInflate;
            }
        });
    }

    private final void hideSystemUI() {
        int iColor;
        Window window = getWindow();
        ReadConfig readConfig = ReadConfig.INSTANCE;
        if (readConfig.getColorMode() == 1) {
            iColor = AppCtxKt.getAppCtx().getColor(R.color.color_EDE2C3);
        } else if (readConfig.getColorMode() != 2) {
            iColor = ColorResourcesKt.color(AppCtxKt.getAppCtx(), R.color.color_f9fafb);
        } else {
            iColor = ColorResourcesKt.color(AppCtxKt.getAppCtx(), R.color.color_171C2D);
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
    public static final void onActivityCreated$lambda$0(MyBookExcerptAct this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onActivityCreated$lambda$3(MyBookExcerptAct this$0, TabLayout.Tab tab, int i2) {
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
    public static final void onActivityCreated$lambda$4(MyBookExcerptAct this$0, AppBarLayout appBarLayout, int i2) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Log.INSTANCE.logD("onOffsetChanged", "verticalOffset = " + i2);
        float fAbs = ((float) Math.abs(i2)) / ((float) appBarLayout.getTotalScrollRange());
        if (Math.abs(i2) == 0) {
            this$0.getBinding().navTitle.setText("");
            RTextView rTextView = this$0.getBinding().navToRead;
            Intrinsics.checkNotNullExpressionValue(rTextView, "binding.navToRead");
            ViewExtensionsKt.invisible(rTextView);
            return;
        }
        if (fAbs >= 0.95f) {
            RTextView rTextView2 = this$0.getBinding().navToRead;
            Intrinsics.checkNotNullExpressionValue(rTextView2, "binding.navToRead");
            ViewExtensionsKt.visible(rTextView2);
            this$0.getBinding().navTitle.setText("显示书籍标题");
        }
    }

    private final void updateStatusBar() {
        getWindow().addFlags(Integer.MIN_VALUE);
        getWindow().clearFlags(67108864);
        getWindow().setStatusBarColor(0);
        getWindow().getDecorView().setSystemUiVisibility(ReadConfig.INSTANCE.getColorMode() < 2 ? 8192 : 256);
    }

    @Override // com.ykb.ebook.base.BaseActivity
    public void onActivityCreated(@Nullable Bundle savedInstanceState) throws Resources.NotFoundException {
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
        getBinding().imgBack.setOnClickListener(new View.OnClickListener() { // from class: com.ykb.ebook.activity.t0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MyBookExcerptAct.onActivityCreated$lambda$0(this.f26198c, view);
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
        new TabLayoutMediator(getBinding().tabLayout, getBinding().viewPager, new TabLayoutMediator.TabConfigurationStrategy() { // from class: com.ykb.ebook.activity.u0
            @Override // com.google.android.material.tabs.TabLayoutMediator.TabConfigurationStrategy
            public final void onConfigureTab(TabLayout.Tab tab, int i2) {
                MyBookExcerptAct.onActivityCreated$lambda$3(this.f26203a, tab, i2);
            }
        }).attach();
        getBinding().tabLayout.addOnTabSelectedListener((TabLayout.OnTabSelectedListener) this);
        getBinding().viewPager.setCurrentItem(this.selectPosition);
        getBinding().appbarlayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() { // from class: com.ykb.ebook.activity.v0
            @Override // com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener, com.google.android.material.appbar.AppBarLayout.BaseOnOffsetChangedListener
            public final void onOffsetChanged(AppBarLayout appBarLayout, int i2) {
                MyBookExcerptAct.onActivityCreated$lambda$4(this.f26207a, appBarLayout, i2);
            }
        });
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

    @Override // com.ykb.ebook.base.BaseActivity
    @NotNull
    public LayoutMyBookExcerptBinding getBinding() {
        return (LayoutMyBookExcerptBinding) this.binding.getValue();
    }
}
