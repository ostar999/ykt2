package com.psychiatrygarden.activity.mine.cutquestion;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import cn.webdemo.com.supporfragment.tablayout.MagicIndicator;
import cn.webdemo.com.supporfragment.tablayout.ViewPagerHelper;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.CommonNavigator;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.IPagerIndicator;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.IPagerTitleView;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.titles.CommonPagerTitleView;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.mine.cutquestion.MyCutQuestionNewActivity;
import com.psychiatrygarden.bean.KnowledgeListType;
import com.psychiatrygarden.bean.MyCutQuestionEditEvent;
import com.psychiatrygarden.bean.QuestionCategoryBean;
import com.psychiatrygarden.bean.SelectErrorWrongFragmentEvent;
import com.psychiatrygarden.bean.SelectFragmentEvent;
import com.psychiatrygarden.bean.SelectIdentityBean;
import com.psychiatrygarden.fragmenthome.BaseViewPagerAdapter;
import com.psychiatrygarden.fragmenthome.knowledge.KnowledgeListEditActivity;
import com.psychiatrygarden.fragmenthome.knowledge.KnowledgeQuestionListFragment;
import com.psychiatrygarden.fragmenthome.knowledge.QuestionWrongKnowledgeMapFragment;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.SkinManager;
import com.yikaobang.yixue.R;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.greenrobot.eventbus.Subscribe;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u0000 $2\u00020\u0001:\u0001$B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0014\u001a\u00020\u0015H\u0002J\b\u0010\u0016\u001a\u00020\u0015H\u0016J\b\u0010\u0017\u001a\u00020\u0015H\u0002J\u0010\u0010\u0018\u001a\u00020\u00152\u0006\u0010\u0019\u001a\u00020\bH\u0002J\u0010\u0010\u001a\u001a\u00020\u00152\u0006\u0010\u001b\u001a\u00020\u001cH\u0007J\u0010\u0010\u001a\u001a\u00020\u00152\u0006\u0010\u001b\u001a\u00020\u001dH\u0007J\u0010\u0010\u001a\u001a\u00020\u00152\u0006\u0010\u001b\u001a\u00020\u001eH\u0007J\u0012\u0010\u001a\u001a\u00020\u00152\b\u0010\u001f\u001a\u0004\u0018\u00010\bH\u0016J\u0010\u0010 \u001a\u00020\u00152\u0006\u0010!\u001a\u00020\u0004H\u0002J\b\u0010\"\u001a\u00020\u0015H\u0016J\b\u0010#\u001a\u00020\u0015H\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082.¢\u0006\u0002\n\u0000R\u0010\u0010\u000e\u001a\u0004\u0018\u00010\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u00060\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u00060\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082.¢\u0006\u0002\n\u0000¨\u0006%"}, d2 = {"Lcom/psychiatrygarden/activity/mine/cutquestion/MyCutQuestionNewActivity;", "Lcom/psychiatrygarden/activity/BaseActivity;", "()V", "curPosition", "", "currentPageIsKnowledge", "", "identityId", "", "listTabs", "", "Lcom/psychiatrygarden/bean/QuestionCategoryBean;", "magicIndicator", "Lcn/webdemo/com/supporfragment/tablayout/MagicIndicator;", "moduleType", "showEditMapBaseQuestion", "", "showEditMapKnowledge", "viewPager", "Landroidx/viewpager/widget/ViewPager;", "getTabList", "", "init", "initTab", "isShowEdit", "id", "onEventMainThread", NotificationCompat.CATEGORY_EVENT, "Lcom/psychiatrygarden/bean/MyCutQuestionEditEvent;", "Lcom/psychiatrygarden/bean/SelectErrorWrongFragmentEvent;", "Lcom/psychiatrygarden/bean/SelectFragmentEvent;", "str", "selectFragmentEditChange", "childPosition", "setContentView", "setListenerForWidget", "Companion", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nMyCutQuestionNewActivity.kt\nKotlin\n*S Kotlin\n*F\n+ 1 MyCutQuestionNewActivity.kt\ncom/psychiatrygarden/activity/mine/cutquestion/MyCutQuestionNewActivity\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,336:1\n1#2:337\n1855#3,2:338\n*S KotlinDebug\n*F\n+ 1 MyCutQuestionNewActivity.kt\ncom/psychiatrygarden/activity/mine/cutquestion/MyCutQuestionNewActivity\n*L\n162#1:338,2\n*E\n"})
/* loaded from: classes5.dex */
public final class MyCutQuestionNewActivity extends BaseActivity {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);
    private int curPosition;
    private boolean currentPageIsKnowledge;
    private MagicIndicator magicIndicator;
    private ViewPager viewPager;

    @Nullable
    private String identityId = "";

    @Nullable
    private String moduleType = "";

    @NotNull
    private final List<QuestionCategoryBean> listTabs = new ArrayList();

    @NotNull
    private final Map<String, Boolean> showEditMapBaseQuestion = new LinkedHashMap();

    @NotNull
    private final Map<String, Boolean> showEditMapKnowledge = new LinkedHashMap();

    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"}, d2 = {"Lcom/psychiatrygarden/activity/mine/cutquestion/MyCutQuestionNewActivity$Companion;", "", "()V", "navigationToMyCutQuestion", "", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final void navigationToMyCutQuestion(@NotNull Context context) {
            Intrinsics.checkNotNullParameter(context, "context");
            context.startActivity(new Intent(context, (Class<?>) MyCutQuestionNewActivity.class));
        }
    }

    @Metadata(d1 = {"\u0000%\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H\u0016J\u0012\u0010\u0004\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u0018\u0010\b\u001a\u00020\t2\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\u0003H\u0016¨\u0006\u000b"}, d2 = {"com/psychiatrygarden/activity/mine/cutquestion/MyCutQuestionNewActivity$initTab$2", "Lcn/webdemo/com/supporfragment/tablayout/buildins/commonnavigator/abs/CommonNavigatorAdapter;", "getCount", "", "getIndicator", "Lcn/webdemo/com/supporfragment/tablayout/buildins/commonnavigator/abs/IPagerIndicator;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "getTitleView", "Lcn/webdemo/com/supporfragment/tablayout/buildins/commonnavigator/abs/IPagerTitleView;", "index", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.psychiatrygarden.activity.mine.cutquestion.MyCutQuestionNewActivity$initTab$2, reason: invalid class name */
    public static final class AnonymousClass2 extends CommonNavigatorAdapter {
        public AnonymousClass2() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void getTitleView$lambda$0(MyCutQuestionNewActivity this$0, int i2, View view) throws Resources.NotFoundException {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            ViewPager viewPager = this$0.viewPager;
            if (viewPager == null) {
                Intrinsics.throwUninitializedPropertyAccessException("viewPager");
                viewPager = null;
            }
            viewPager.setCurrentItem(i2);
        }

        @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter
        public int getCount() {
            return MyCutQuestionNewActivity.this.listTabs.size();
        }

        @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter
        @Nullable
        public IPagerIndicator getIndicator(@NotNull Context context) {
            Intrinsics.checkNotNullParameter(context, "context");
            return null;
        }

        @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter
        @NotNull
        public IPagerTitleView getTitleView(@NotNull final Context context, final int index) {
            Intrinsics.checkNotNullParameter(context, "context");
            CommonPagerTitleView commonPagerTitleView = new CommonPagerTitleView(MyCutQuestionNewActivity.this);
            commonPagerTitleView.setContentView(R.layout.item_tabs_zuti);
            commonPagerTitleView.findViewById(R.id.viewStart).setVisibility(index == 0 ? 0 : 8);
            final TextView textView = (TextView) commonPagerTitleView.findViewById(R.id.tv_tabs_name);
            final ImageView imageView = (ImageView) commonPagerTitleView.findViewById(R.id.img_choose);
            textView.setText(((QuestionCategoryBean) MyCutQuestionNewActivity.this.listTabs.get(index)).getTitle());
            final MyCutQuestionNewActivity myCutQuestionNewActivity = MyCutQuestionNewActivity.this;
            commonPagerTitleView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.mine.cutquestion.w
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) throws Resources.NotFoundException {
                    MyCutQuestionNewActivity.AnonymousClass2.getTitleView$lambda$0(myCutQuestionNewActivity, index, view);
                }
            });
            commonPagerTitleView.setOnPagerTitleChangeListener(new CommonPagerTitleView.OnPagerTitleChangeListener() { // from class: com.psychiatrygarden.activity.mine.cutquestion.MyCutQuestionNewActivity$initTab$2$getTitleView$2
                @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.titles.CommonPagerTitleView.OnPagerTitleChangeListener
                public void onDeselected(int index2, int totalCount) {
                    textView.setTypeface(Typeface.DEFAULT);
                    textView.setTextColor(SkinManager.getThemeColor(context, R.attr.third_txt_color));
                    imageView.setVisibility(4);
                    textView.setTextSize(2, 14.0f);
                }

                @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.titles.CommonPagerTitleView.OnPagerTitleChangeListener
                public void onEnter(int index2, int totalCount, float enterPercent, boolean leftToRight) {
                }

                @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.titles.CommonPagerTitleView.OnPagerTitleChangeListener
                public void onLeave(int index2, int totalCount, float leavePercent, boolean leftToRight) {
                }

                @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.titles.CommonPagerTitleView.OnPagerTitleChangeListener
                public void onSelected(int index2, int totalCount) {
                    textView.setTypeface(Typeface.DEFAULT_BOLD);
                    textView.setTextColor(SkinManager.getThemeColor(context, R.attr.first_text_color));
                    imageView.setVisibility(0);
                    textView.setTextSize(2, 16.0f);
                }
            });
            return commonPagerTitleView;
        }
    }

    private final void getTabList() throws Resources.NotFoundException {
        this.listTabs.clear();
        List<QuestionCategoryBean> categoryList = CommonUtil.getQuestionCategoryList(this.mContext);
        Intrinsics.checkNotNullExpressionValue(categoryList, "categoryList");
        if (!categoryList.isEmpty()) {
            ListIterator<QuestionCategoryBean> listIterator = categoryList.listIterator();
            while (listIterator.hasNext()) {
                QuestionCategoryBean next = listIterator.next();
                if (next.getChildren() == null || next.getChildren().isEmpty()) {
                    listIterator.remove();
                }
            }
            this.listTabs.addAll(categoryList);
            initTab();
        }
    }

    private final void initTab() throws Resources.NotFoundException {
        ArrayList arrayList = new ArrayList();
        for (QuestionCategoryBean questionCategoryBean : this.listTabs) {
            Bundle bundle = new Bundle();
            List<QuestionCategoryBean> list = this.listTabs;
            Intrinsics.checkNotNull(list, "null cannot be cast to non-null type java.io.Serializable");
            bundle.putSerializable("tabList", (Serializable) list);
            bundle.putString("id", questionCategoryBean.getId());
            if (Intrinsics.areEqual("1", questionCategoryBean.getType())) {
                bundle.putString(KnowledgeQuestionListFragment.EXTRA_DOMAIN_TYPE, KnowledgeListType.CUT.getType());
                arrayList.add(new BaseViewPagerAdapter.PagerInfo(questionCategoryBean.getTitle(), QuestionWrongKnowledgeMapFragment.class, bundle));
            } else {
                arrayList.add(new BaseViewPagerAdapter.PagerInfo(questionCategoryBean.getTitle(), MyCutQuestionBaseFragment.class, bundle));
            }
        }
        final BaseViewPagerAdapter baseViewPagerAdapter = new BaseViewPagerAdapter(this.mContext, getSupportFragmentManager(), arrayList);
        ViewPager viewPager = this.viewPager;
        ViewPager viewPager2 = null;
        if (viewPager == null) {
            Intrinsics.throwUninitializedPropertyAccessException("viewPager");
            viewPager = null;
        }
        viewPager.setAdapter(baseViewPagerAdapter);
        CommonNavigator commonNavigator = new CommonNavigator(this.mContext);
        commonNavigator.setSkimOver(true);
        commonNavigator.setAdapter(new AnonymousClass2());
        MagicIndicator magicIndicator = this.magicIndicator;
        if (magicIndicator == null) {
            Intrinsics.throwUninitializedPropertyAccessException("magicIndicator");
            magicIndicator = null;
        }
        magicIndicator.setNavigator(commonNavigator);
        MagicIndicator magicIndicator2 = this.magicIndicator;
        if (magicIndicator2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("magicIndicator");
            magicIndicator2 = null;
        }
        ViewPager viewPager3 = this.viewPager;
        if (viewPager3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("viewPager");
            viewPager3 = null;
        }
        ViewPagerHelper.bind(magicIndicator2, viewPager3);
        ViewPager viewPager4 = this.viewPager;
        if (viewPager4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("viewPager");
            viewPager4 = null;
        }
        viewPager4.addOnPageChangeListener(new ViewPager.OnPageChangeListener() { // from class: com.psychiatrygarden.activity.mine.cutquestion.MyCutQuestionNewActivity.initTab.3
            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrollStateChanged(int state) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageSelected(int position) {
                try {
                    MyCutQuestionNewActivity.this.curPosition = position;
                    Fragment curFragment = baseViewPagerAdapter.getCurFragment();
                    MyCutQuestionNewActivity.this.selectFragmentEditChange(curFragment instanceof QuestionWrongKnowledgeMapFragment ? ((QuestionWrongKnowledgeMapFragment) curFragment).currentPosition : curFragment instanceof MyCutQuestionBaseFragment ? ((MyCutQuestionBaseFragment) curFragment).currentPosition : 0);
                } catch (Exception e2) {
                    System.out.println((Object) e2.getMessage());
                }
            }
        });
        ViewPager viewPager5 = this.viewPager;
        if (viewPager5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("viewPager");
            viewPager5 = null;
        }
        viewPager5.setOffscreenPageLimit(this.listTabs.size());
        MagicIndicator magicIndicator3 = this.magicIndicator;
        if (magicIndicator3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("magicIndicator");
            magicIndicator3 = null;
        }
        magicIndicator3.setVisibility(this.listTabs.size() > 1 ? 0 : 8);
        ViewPager viewPager6 = this.viewPager;
        if (viewPager6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("viewPager");
        } else {
            viewPager2 = viewPager6;
        }
        viewPager2.setCurrentItem(0);
        boolean zAreEqual = Intrinsics.areEqual(this.listTabs.get(0).getType(), "1");
        this.currentPageIsKnowledge = zAreEqual;
        this.identityId = zAreEqual ? this.listTabs.get(0).getChildren().get(0).getId() : this.listTabs.get(0).getChildren().get(0).getIdentity_id();
        this.moduleType = this.listTabs.get(0).getChildren().get(0).getModule_type() == null ? "" : this.listTabs.get(0).getChildren().get(0).getModule_type();
        String str = this.identityId;
        if (str != null) {
            isShowEdit(str);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0023  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private final void isShowEdit(java.lang.String r3) {
        /*
            r2 = this;
            boolean r0 = r2.currentPageIsKnowledge
            r1 = 0
            if (r0 == 0) goto L14
            java.util.Map<java.lang.String, java.lang.Boolean> r0 = r2.showEditMapKnowledge
            java.lang.Object r3 = r0.get(r3)
            java.lang.Boolean r3 = (java.lang.Boolean) r3
            if (r3 == 0) goto L23
            boolean r3 = r3.booleanValue()
            goto L24
        L14:
            java.util.Map<java.lang.String, java.lang.Boolean> r0 = r2.showEditMapBaseQuestion
            java.lang.Object r3 = r0.get(r3)
            java.lang.Boolean r3 = (java.lang.Boolean) r3
            if (r3 == 0) goto L23
            boolean r3 = r3.booleanValue()
            goto L24
        L23:
            r3 = r1
        L24:
            if (r3 == 0) goto L39
            android.widget.Button r3 = r2.mBtnActionbarRight
            java.lang.String r0 = "编辑"
            r3.setText(r0)
            android.widget.Button r3 = r2.mBtnActionbarRight
            r0 = 1
            r3.setEnabled(r0)
            android.widget.Button r3 = r2.mBtnActionbarRight
            r3.setVisibility(r1)
            goto L4c
        L39:
            android.widget.Button r3 = r2.mBtnActionbarRight
            java.lang.String r0 = ""
            r3.setText(r0)
            android.widget.Button r3 = r2.mBtnActionbarRight
            r3.setEnabled(r1)
            android.widget.Button r3 = r2.mBtnActionbarRight
            r0 = 8
            r3.setVisibility(r0)
        L4c:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.psychiatrygarden.activity.mine.cutquestion.MyCutQuestionNewActivity.isShowEdit(java.lang.String):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void selectFragmentEditChange(int childPosition) {
        try {
            SelectIdentityBean.DataBean dataBean = this.listTabs.get(this.curPosition).getChildren().get(childPosition);
            boolean zAreEqual = Intrinsics.areEqual(dataBean.getType(), "1");
            this.currentPageIsKnowledge = zAreEqual;
            if (zAreEqual) {
                this.identityId = dataBean.getId();
                this.moduleType = "";
            } else {
                this.identityId = dataBean.getIdentity_id();
                this.moduleType = dataBean.getModule_type();
            }
            String str = this.identityId;
            if (str != null) {
                isShowEdit(str);
            }
        } catch (Exception e2) {
            System.out.println((Object) e2.getMessage());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setListenerForWidget$lambda$0(MyCutQuestionNewActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (this$0.currentPageIsKnowledge) {
            KnowledgeListEditActivity.gotToEditKnowledge(this$0, this$0.identityId, KnowledgeListType.CUT.getType());
        } else {
            EditMyCutQuestionAct.navigationToEditMyCutQuestion(this$0, this$0.identityId, this$0.moduleType);
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() throws Resources.NotFoundException {
        setTitle("已斩试题");
        View viewFindViewById = findViewById(R.id.viewPager);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(R.id.viewPager)");
        this.viewPager = (ViewPager) viewFindViewById;
        View viewFindViewById2 = findViewById(R.id.magicIndicator);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "findViewById(R.id.magicIndicator)");
        this.magicIndicator = (MagicIndicator) viewFindViewById2;
        getTabList();
    }

    @Subscribe
    public final void onEventMainThread(@NotNull MyCutQuestionEditEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (event.isKnowledge()) {
            this.showEditMapKnowledge.put(event.getIdentityId(), Boolean.valueOf(event.isShowEdit()));
        } else {
            this.showEditMapBaseQuestion.put(event.getIdentityId(), Boolean.valueOf(event.isShowEdit()));
        }
        if (Intrinsics.areEqual(this.identityId, event.getIdentityId())) {
            if (event.isShowEdit()) {
                this.mBtnActionbarRight.setText("编辑");
                this.mBtnActionbarRight.setEnabled(true);
                this.mBtnActionbarRight.setVisibility(0);
            } else {
                this.mBtnActionbarRight.setText("");
                this.mBtnActionbarRight.setEnabled(false);
                this.mBtnActionbarRight.setVisibility(8);
            }
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(@Nullable String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_my_cut_question_new);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.mBtnActionbarRight.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.mine.cutquestion.v
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MyCutQuestionNewActivity.setListenerForWidget$lambda$0(this.f12821c, view);
            }
        });
    }

    @Subscribe
    public final void onEventMainThread(@NotNull SelectFragmentEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        selectFragmentEditChange(event.getPosition());
    }

    @Subscribe
    public final void onEventMainThread(@NotNull SelectErrorWrongFragmentEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        selectFragmentEditChange(event.getPosition());
    }
}
