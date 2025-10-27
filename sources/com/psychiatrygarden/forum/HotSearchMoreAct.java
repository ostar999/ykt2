package com.psychiatrygarden.forum;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.bean.ForumTopBean;
import com.psychiatrygarden.bean.HotSearchRankTabItem;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.utils.LogUtils;
import com.psychiatrygarden.utils.ScreenUtil;
import com.psychiatrygarden.utils.SkinManager;
import com.yikaobang.yixue.R;
import com.yikaobang.yixue.R2;
import com.ykb.ebook.extensions.ContextExtensionsKt;
import com.ykb.ebook.util.Log;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt__StringsJVMKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000g\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004*\u0001\r\u0018\u00002\u00020\u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J\b\u0010\u0013\u001a\u00020\u0014H\u0016J\u0010\u0010\u0015\u001a\u00020\u00142\u0006\u0010\u0016\u001a\u00020\u0017H\u0016J\u0012\u0010\u0018\u001a\u00020\u00142\b\u0010\u0019\u001a\u0004\u0018\u00010\u001aH\u0016J(\u0010\u001b\u001a\u00020\u00142\u000e\u0010\u001c\u001a\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u001d2\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!H\u0016J\u0010\u0010\"\u001a\u00020\u00142\u0006\u0010 \u001a\u00020!H\u0002J\b\u0010#\u001a\u00020\u0014H\u0016J\b\u0010$\u001a\u00020\u0014H\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082.¢\u0006\u0002\n\u0000R\u0010\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u000eR\u000e\u0010\u000f\u001a\u00020\u0010X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082.¢\u0006\u0002\n\u0000¨\u0006%"}, d2 = {"Lcom/psychiatrygarden/forum/HotSearchMoreAct;", "Lcom/psychiatrygarden/activity/BaseActivity;", "Lcom/chad/library/adapter/base/listener/OnItemClickListener;", "()V", "appbarlayout", "Lcom/google/android/material/appbar/AppBarLayout;", "collLayout", "Lcom/google/android/material/appbar/CollapsingToolbarLayout;", "ivImg", "Landroid/widget/ImageView;", "mTabAdapter", "Lcom/psychiatrygarden/forum/HotSearchRankTabAdapter;", "pageCallBack", "com/psychiatrygarden/forum/HotSearchMoreAct$pageCallBack$1", "Lcom/psychiatrygarden/forum/HotSearchMoreAct$pageCallBack$1;", "rvHotSearchRankTab", "Landroidx/recyclerview/widget/RecyclerView;", "viewPagerHotSearchRank", "Landroidx/viewpager/widget/ViewPager;", "init", "", "onConfigurationChanged", "newConfig", "Landroid/content/res/Configuration;", "onEventMainThread", "str", "", "onItemClick", "adapter", "Lcom/chad/library/adapter/base/BaseQuickAdapter;", "view", "Landroid/view/View;", "position", "", "pageChange", "setContentView", "setListenerForWidget", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nHotSearchMoreAct.kt\nKotlin\n*S Kotlin\n*F\n+ 1 HotSearchMoreAct.kt\ncom/psychiatrygarden/forum/HotSearchMoreAct\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,253:1\n1864#2,3:254\n350#2,7:257\n*S KotlinDebug\n*F\n+ 1 HotSearchMoreAct.kt\ncom/psychiatrygarden/forum/HotSearchMoreAct\n*L\n228#1:254,3\n240#1:257,7\n*E\n"})
/* loaded from: classes5.dex */
public final class HotSearchMoreAct extends BaseActivity implements OnItemClickListener {
    private AppBarLayout appbarlayout;
    private CollapsingToolbarLayout collLayout;
    private ImageView ivImg;
    private HotSearchRankTabAdapter mTabAdapter;

    @NotNull
    private final HotSearchMoreAct$pageCallBack$1 pageCallBack = new ViewPager.OnPageChangeListener() { // from class: com.psychiatrygarden.forum.HotSearchMoreAct$pageCallBack$1
        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageScrollStateChanged(int state) {
        }

        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageSelected(int position) throws Resources.NotFoundException {
            this.this$0.pageChange(position);
        }
    };
    private RecyclerView rvHotSearchRankTab;
    private ViewPager viewPagerHotSearchRank;

    /* JADX INFO: Access modifiers changed from: private */
    public static final void init$lambda$0(HotSearchMoreAct this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void init$lambda$1(HotSearchMoreAct this$0, ImageView imgBack, TextView navTitle, AppBarLayout appBarLayout, int i2) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(imgBack, "$imgBack");
        Intrinsics.checkNotNullParameter(navTitle, "$navTitle");
        Log.INSTANCE.logD("onOffsetChanged", "verticalOffset = " + i2);
        int totalScrollRange = appBarLayout.getTotalScrollRange();
        Math.abs(i2);
        ImageView imageView = this$0.ivImg;
        if (imageView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ivImg");
            imageView = null;
        }
        imageView.setAlpha(1 - Math.abs((i2 * 1.0f) / appBarLayout.getTotalScrollRange()));
        if (i2 == 0) {
            imgBack.setImageResource(R.drawable.icon_left_back);
            navTitle.setVisibility(8);
        } else if (totalScrollRange >= i2) {
            imgBack.setImageResource(SkinManager.getCurrentSkinType(this$0.mContext) == 1 ? R.mipmap.ic_black_back_night : R.mipmap.ic_black_back);
            navTitle.setVisibility(0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    public final void pageChange(int position) throws Resources.NotFoundException {
        HotSearchRankTabAdapter hotSearchRankTabAdapter = this.mTabAdapter;
        ViewPager viewPager = null;
        if (hotSearchRankTabAdapter == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mTabAdapter");
            hotSearchRankTabAdapter = null;
        }
        String code = hotSearchRankTabAdapter.getItem(position).getCode();
        switch (code.hashCode()) {
            case 97331:
                if (code.equals("bbs")) {
                    ImageView imageView = this.ivImg;
                    if (imageView == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("ivImg");
                        imageView = null;
                    }
                    imageView.setImageResource(R.drawable.bg_circle_hot_rank);
                    break;
                }
                break;
            case 100893:
                if (code.equals("exp")) {
                    ImageView imageView2 = this.ivImg;
                    if (imageView2 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("ivImg");
                        imageView2 = null;
                    }
                    imageView2.setImageResource(R.drawable.bg_exp_hot_rank);
                    break;
                }
                break;
            case 3143036:
                if (code.equals("file")) {
                    ImageView imageView3 = this.ivImg;
                    if (imageView3 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("ivImg");
                        imageView3 = null;
                    }
                    imageView3.setImageResource(R.drawable.bg_information_hot_rank);
                    break;
                }
                break;
            case 96305358:
                if (code.equals("ebook")) {
                    ImageView imageView4 = this.ivImg;
                    if (imageView4 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("ivImg");
                        imageView4 = null;
                    }
                    imageView4.setImageResource(R.drawable.bg_book_hot_rank);
                    break;
                }
                break;
        }
        RecyclerView recyclerView = this.rvHotSearchRankTab;
        if (recyclerView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("rvHotSearchRankTab");
            recyclerView = null;
        }
        recyclerView.scrollToPosition(position);
        HotSearchRankTabAdapter hotSearchRankTabAdapter2 = this.mTabAdapter;
        if (hotSearchRankTabAdapter2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mTabAdapter");
            hotSearchRankTabAdapter2 = null;
        }
        int i2 = 0;
        for (Object obj : hotSearchRankTabAdapter2.getData()) {
            int i3 = i2 + 1;
            if (i2 < 0) {
                CollectionsKt__CollectionsKt.throwIndexOverflow();
            }
            ((HotSearchRankTabItem) obj).setSelect(i2 == position);
            i2 = i3;
        }
        HotSearchRankTabAdapter hotSearchRankTabAdapter3 = this.mTabAdapter;
        if (hotSearchRankTabAdapter3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mTabAdapter");
            hotSearchRankTabAdapter3 = null;
        }
        hotSearchRankTabAdapter3.notifyDataSetChanged();
        ViewPager viewPager2 = this.viewPagerHotSearchRank;
        if (viewPager2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("viewPagerHotSearchRank");
        } else {
            viewPager = viewPager2;
        }
        viewPager.setCurrentItem(position);
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue
    java.lang.NullPointerException: Cannot invoke "java.util.List.iterator()" because the return value of "jadx.core.dex.visitors.regions.SwitchOverStringVisitor$SwitchData.getNewCases()" is null
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.restoreSwitchOverString(SwitchOverStringVisitor.java:109)
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visitRegion(SwitchOverStringVisitor.java:66)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:77)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:82)
     */
    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() throws Resources.NotFoundException {
        AppBarLayout appBarLayout;
        this.mActionBar.hide();
        int intExtra = getIntent().getIntExtra("position", 0);
        View viewFindViewById = findViewById(R.id.iv_actionbar_back);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(R.id.iv_actionbar_back)");
        final ImageView imageView = (ImageView) viewFindViewById;
        View viewFindViewById2 = findViewById(R.id.nav_title);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "findViewById(R.id.nav_title)");
        final TextView textView = (TextView) viewFindViewById2;
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.forum.h0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                HotSearchMoreAct.init$lambda$0(this.f15356c, view);
            }
        });
        Toolbar toolbar = (Toolbar) findViewById(R.id.toobars1);
        ViewGroup.LayoutParams layoutParams = toolbar.getLayoutParams();
        Intrinsics.checkNotNull(layoutParams, "null cannot be cast to non-null type android.widget.FrameLayout.LayoutParams");
        FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) layoutParams;
        layoutParams2.topMargin = ContextExtensionsKt.getStatusBarHeight(this);
        toolbar.setLayoutParams(layoutParams2);
        HotSearchRankTabAdapter hotSearchRankTabAdapter = new HotSearchRankTabAdapter();
        this.mTabAdapter = hotSearchRankTabAdapter;
        hotSearchRankTabAdapter.setOnItemClickListener(this);
        View viewFindViewById3 = findViewById(R.id.rvHotSearchRankTab);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById3, "findViewById(R.id.rvHotSearchRankTab)");
        this.rvHotSearchRankTab = (RecyclerView) viewFindViewById3;
        View viewFindViewById4 = findViewById(R.id.viewPagerHotSearchRank);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById4, "findViewById(R.id.viewPagerHotSearchRank)");
        this.viewPagerHotSearchRank = (ViewPager) viewFindViewById4;
        View viewFindViewById5 = findViewById(R.id.appbarlayout);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById5, "findViewById(R.id.appbarlayout)");
        this.appbarlayout = (AppBarLayout) viewFindViewById5;
        View viewFindViewById6 = findViewById(R.id.collapse);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById6, "findViewById(R.id.collapse)");
        this.collLayout = (CollapsingToolbarLayout) viewFindViewById6;
        View viewFindViewById7 = findViewById(R.id.iv_type);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById7, "findViewById(R.id.iv_type)");
        this.ivImg = (ImageView) viewFindViewById7;
        int screenWidth = ScreenUtil.getScreenWidth(this);
        int i2 = (int) (screenWidth / 1.99d);
        CollapsingToolbarLayout collapsingToolbarLayout = this.collLayout;
        if (collapsingToolbarLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("collLayout");
            collapsingToolbarLayout = null;
        }
        collapsingToolbarLayout.getLayoutParams().height = i2;
        LogUtils.e("width===", "width===>" + screenWidth + ";height=" + i2);
        ViewPager viewPager = this.viewPagerHotSearchRank;
        if (viewPager == null) {
            Intrinsics.throwUninitializedPropertyAccessException("viewPagerHotSearchRank");
            viewPager = null;
        }
        viewPager.addOnPageChangeListener(this.pageCallBack);
        RecyclerView recyclerView = this.rvHotSearchRankTab;
        if (recyclerView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("rvHotSearchRankTab");
            recyclerView = null;
        }
        HotSearchRankTabAdapter hotSearchRankTabAdapter2 = this.mTabAdapter;
        if (hotSearchRankTabAdapter2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mTabAdapter");
            hotSearchRankTabAdapter2 = null;
        }
        recyclerView.setAdapter(hotSearchRankTabAdapter2);
        String strConfig = SharePreferencesUtils.readStrConfig("top_grid_data", this);
        ArrayList arrayList = new ArrayList();
        ForumTopBean.TopModule topModule = new ForumTopBean.TopModule();
        topModule.setName("帖子热搜榜");
        topModule.setCode("bbs");
        arrayList.add(topModule);
        if (!TextUtils.isEmpty(strConfig)) {
            List dataList = (List) new Gson().fromJson(strConfig, new TypeToken<List<? extends ForumTopBean.TopModule>>() { // from class: com.psychiatrygarden.forum.HotSearchMoreAct$init$dataList$1
            }.getType());
            Intrinsics.checkNotNullExpressionValue(dataList, "dataList");
            arrayList.addAll(dataList);
        }
        final ArrayList arrayList2 = new ArrayList();
        boolean z2 = true;
        if (arrayList.size() > 1) {
            RecyclerView recyclerView2 = this.rvHotSearchRankTab;
            if (recyclerView2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("rvHotSearchRankTab");
                recyclerView2 = null;
            }
            recyclerView2.setVisibility(0);
            int size = arrayList.size();
            int i3 = 0;
            while (i3 < size) {
                HotSearchRankTabAdapter hotSearchRankTabAdapter3 = this.mTabAdapter;
                if (hotSearchRankTabAdapter3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("mTabAdapter");
                    hotSearchRankTabAdapter3 = null;
                }
                String name = ((ForumTopBean.TopModule) arrayList.get(i3)).getName();
                Intrinsics.checkNotNullExpressionValue(name, "mTabList[i].name");
                String strReplace$default = StringsKt__StringsJVMKt.replace$default(name, "热搜榜", "", false, 4, (Object) null);
                boolean z3 = i3 == 0 ? z2 : false;
                String code = ((ForumTopBean.TopModule) arrayList.get(i3)).getCode();
                int i4 = size;
                Intrinsics.checkNotNullExpressionValue(code, "mTabList[i].code");
                hotSearchRankTabAdapter3.addData((HotSearchRankTabAdapter) new HotSearchRankTabItem(strReplace$default, z3, 1, code));
                String code2 = ((ForumTopBean.TopModule) arrayList.get(i3)).getCode();
                if (code2 != null) {
                    switch (code2.hashCode()) {
                        case 97331:
                            if (!code2.equals("bbs")) {
                                break;
                            } else {
                                arrayList2.add(CircleHotSearchRankFragment.INSTANCE.newInstance(1, "bbs", true, i3, false));
                                break;
                            }
                        case 100893:
                            if (!code2.equals("exp")) {
                                break;
                            } else {
                                arrayList2.add(CircleHotSearchRankFragment.INSTANCE.newInstance(2, "exp", true, i3, false));
                                break;
                            }
                        case 3143036:
                            if (!code2.equals("file")) {
                                break;
                            } else {
                                arrayList2.add(CircleHotSearchRankFragment.INSTANCE.newInstance(4, "file", true, i3, false));
                                break;
                            }
                        case 96305358:
                            if (!code2.equals("ebook")) {
                                break;
                            } else {
                                arrayList2.add(CircleHotSearchRankFragment.INSTANCE.newInstance(3, "ebook", true, i3, false));
                                break;
                            }
                    }
                }
                i3++;
                z2 = true;
                size = i4;
            }
        } else {
            RecyclerView recyclerView3 = this.rvHotSearchRankTab;
            if (recyclerView3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("rvHotSearchRankTab");
                recyclerView3 = null;
            }
            recyclerView3.setVisibility(8);
            arrayList2.add(CircleHotSearchRankFragment.INSTANCE.newInstance(1, "bbs", true, 0, false));
        }
        ViewPager viewPager2 = this.viewPagerHotSearchRank;
        if (viewPager2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("viewPagerHotSearchRank");
            viewPager2 = null;
        }
        viewPager2.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) { // from class: com.psychiatrygarden.forum.HotSearchMoreAct.init.2
            @Override // androidx.viewpager.widget.PagerAdapter
            /* renamed from: getCount */
            public int getSize() {
                return arrayList2.size();
            }

            @Override // androidx.fragment.app.FragmentPagerAdapter
            @NotNull
            public Fragment getItem(int position) {
                return arrayList2.get(position);
            }
        });
        AppBarLayout appBarLayout2 = this.appbarlayout;
        if (appBarLayout2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("appbarlayout");
            appBarLayout = null;
        } else {
            appBarLayout = appBarLayout2;
        }
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() { // from class: com.psychiatrygarden.forum.i0
            @Override // com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener, com.google.android.material.appbar.AppBarLayout.BaseOnOffsetChangedListener
            public final void onOffsetChanged(AppBarLayout appBarLayout3, int i5) {
                HotSearchMoreAct.init$lambda$1(this.f15359a, imageView, textView, appBarLayout3, i5);
            }
        });
        pageChange(intExtra);
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(@NotNull Configuration newConfig) {
        Intrinsics.checkNotNullParameter(newConfig, "newConfig");
        super.onConfigurationChanged(newConfig);
        int screenWidth = ScreenUtil.getScreenWidth(this);
        int i2 = (int) (screenWidth / 1.99d);
        CollapsingToolbarLayout collapsingToolbarLayout = this.collLayout;
        if (collapsingToolbarLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("collLayout");
            collapsingToolbarLayout = null;
        }
        collapsingToolbarLayout.getLayoutParams().height = i2;
        LogUtils.e("width===", "width===>" + screenWidth + ";height=" + i2);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(@Nullable String str) {
    }

    @Override // com.chad.library.adapter.base.listener.OnItemClickListener
    public void onItemClick(@NotNull BaseQuickAdapter<?, ?> adapter, @NotNull View view, int position) throws Resources.NotFoundException {
        Intrinsics.checkNotNullParameter(adapter, "adapter");
        Intrinsics.checkNotNullParameter(view, "view");
        StringBuilder sb = new StringBuilder();
        sb.append("current = ");
        ViewPager viewPager = this.viewPagerHotSearchRank;
        HotSearchRankTabAdapter hotSearchRankTabAdapter = null;
        if (viewPager == null) {
            Intrinsics.throwUninitializedPropertyAccessException("viewPagerHotSearchRank");
            viewPager = null;
        }
        sb.append(viewPager.getCurrentItem());
        sb.append(" click = ");
        sb.append(position);
        LogUtils.d("onItemClick", sb.toString());
        HotSearchRankTabAdapter hotSearchRankTabAdapter2 = this.mTabAdapter;
        if (hotSearchRankTabAdapter2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mTabAdapter");
        } else {
            hotSearchRankTabAdapter = hotSearchRankTabAdapter2;
        }
        Iterator<HotSearchRankTabItem> it = hotSearchRankTabAdapter.getData().iterator();
        int i2 = 0;
        while (true) {
            if (!it.hasNext()) {
                i2 = -1;
                break;
            } else if (it.next().getSelect()) {
                break;
            } else {
                i2++;
            }
        }
        if (i2 == position || i2 == -1) {
            return;
        }
        pageChange(position);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        getWindow().addFlags(Integer.MIN_VALUE);
        getWindow().clearFlags(67108864);
        getWindow().clearFlags(134217728);
        getWindow().getDecorView().setSystemUiVisibility(R2.drawable.ee_19);
        getWindow().setStatusBarColor(0);
        setContentView(R.layout.act_hot_search_more);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}
