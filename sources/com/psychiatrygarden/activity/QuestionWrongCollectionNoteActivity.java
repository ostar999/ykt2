package com.psychiatrygarden.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
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
import com.aliyun.vod.common.utils.UriUtil;
import com.lxj.xpopup.XPopup;
import com.psychiatrygarden.activity.QuestionWrongCollectionNoteActivity;
import com.psychiatrygarden.activity.vip.MemberCenterActivity;
import com.psychiatrygarden.bean.KnowledgeListType;
import com.psychiatrygarden.bean.MyCutQuestionEditEvent;
import com.psychiatrygarden.bean.QuestionCategoryBean;
import com.psychiatrygarden.bean.SelectErrorWrongFragmentEvent;
import com.psychiatrygarden.bean.SelectIdentityBean;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.fragmenthome.BaseViewPagerAdapter;
import com.psychiatrygarden.fragmenthome.QuestionWrongCollectionNoteFragment;
import com.psychiatrygarden.fragmenthome.knowledge.KnowledgeQuestionListFragment;
import com.psychiatrygarden.fragmenthome.knowledge.QuestionWrongKnowledgeMapFragment;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.AliyunEvent;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.ToastUtil;
import com.psychiatrygarden.widget.ErrorExportPopuWindow;
import com.psychiatrygarden.widget.ExportDescriptionPop;
import com.yikaobang.yixue.R;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt__StringsJVMKt;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.greenrobot.eventbus.Subscribe;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;

@Metadata(d1 = {"\u0000t\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 62\u00020\u0001:\u00016B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010 \u001a\u00020!H\u0002J\b\u0010\"\u001a\u00020!H\u0002J\b\u0010#\u001a\u00020!H\u0016J\u0012\u0010$\u001a\u00020!2\b\u0010%\u001a\u0004\u0018\u00010\u0004H\u0002J\b\u0010&\u001a\u00020!H\u0002J\u0010\u0010'\u001a\u00020!2\u0006\u0010(\u001a\u00020)H\u0007J\u0010\u0010'\u001a\u00020!2\u0006\u0010(\u001a\u00020*H\u0007J\u0012\u0010'\u001a\u00020!2\b\u0010+\u001a\u0004\u0018\u00010\u0006H\u0016J\u0010\u0010,\u001a\u00020!2\u0006\u0010-\u001a\u00020\tH\u0002J\b\u0010.\u001a\u00020!H\u0016J\b\u0010/\u001a\u00020!H\u0002J\b\u00100\u001a\u00020!H\u0002J\b\u00101\u001a\u00020!H\u0016J\u001c\u00102\u001a\u00020!2\b\u00103\u001a\u0004\u0018\u00010\u00062\b\u00104\u001a\u0004\u0018\u000105H\u0002R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\f\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\r\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082.¢\u0006\u0002\n\u0000R\u0010\u0010\u0010\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u000fX\u0082.¢\u0006\u0002\n\u0000R\u0014\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00140\u0013X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082.¢\u0006\u0002\n\u0000R\u0010\u0010\u0017\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u0018\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u000b0\u0019X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u001a\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u000b0\u0019X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u001cX\u0082.¢\u0006\u0002\n\u0000R\u0010\u0010\u001d\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u001fX\u0082.¢\u0006\u0002\n\u0000¨\u00067"}, d2 = {"Lcom/psychiatrygarden/activity/QuestionWrongCollectionNoteActivity;", "Lcom/psychiatrygarden/activity/BaseActivity;", "()V", "curBaseItem", "Lcom/psychiatrygarden/bean/SelectIdentityBean$DataBean;", "curIdentityId", "", "curModuleType", "curPosition", "", "currentPageIsKnowledge", "", "exportFuncIdentityId", "identityId", "imageBack", "Landroid/widget/ImageView;", "isShowNumber", "ivActionbarRight", "listTabs", "", "Lcom/psychiatrygarden/bean/QuestionCategoryBean;", "magicIndicator", "Lcn/webdemo/com/supporfragment/tablayout/MagicIndicator;", "moduleType", "showEditMapBaseQuestion", "", "showEditMapKnowledge", "textTitle", "Landroid/widget/TextView;", "type", "viewPager", "Landroidx/viewpager/widget/ViewPager;", "getActivityParams", "", "getTabList", "init", "initSelectItemCategoryData", "itemData", "initTab", "onEventMainThread", NotificationCompat.CATEGORY_EVENT, "Lcom/psychiatrygarden/bean/MyCutQuestionEditEvent;", "Lcom/psychiatrygarden/bean/SelectErrorWrongFragmentEvent;", "str", "selectFragmentChange", "childPosition", "setContentView", "setCustomerRightIcon", "setCustomerTitle", "setListenerForWidget", "settingErrorConfig", "status", "errorSetting", "Landroid/widget/Switch;", "Companion", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nQuestionWrongCollectionNoteActivity.kt\nKotlin\n*S Kotlin\n*F\n+ 1 QuestionWrongCollectionNoteActivity.kt\ncom/psychiatrygarden/activity/QuestionWrongCollectionNoteActivity\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,604:1\n1855#2,2:605\n*S KotlinDebug\n*F\n+ 1 QuestionWrongCollectionNoteActivity.kt\ncom/psychiatrygarden/activity/QuestionWrongCollectionNoteActivity\n*L\n285#1:605,2\n*E\n"})
/* loaded from: classes5.dex */
public final class QuestionWrongCollectionNoteActivity extends BaseActivity {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @Nullable
    private SelectIdentityBean.DataBean curBaseItem;
    private int curPosition;
    private boolean currentPageIsKnowledge;
    private ImageView imageBack;
    private ImageView ivActionbarRight;
    private MagicIndicator magicIndicator;
    private TextView textTitle;
    private ViewPager viewPager;

    @Nullable
    private String type = "";

    @Nullable
    private String identityId = "";

    @Nullable
    private String moduleType = "";

    @Nullable
    private String exportFuncIdentityId = "";

    @Nullable
    private String isShowNumber = "";

    @NotNull
    private final List<QuestionCategoryBean> listTabs = new ArrayList();

    @NotNull
    private final Map<String, Boolean> showEditMapBaseQuestion = new LinkedHashMap();

    @NotNull
    private final Map<String, Boolean> showEditMapKnowledge = new LinkedHashMap();

    @Nullable
    private String curIdentityId = "";

    @Nullable
    private String curModuleType = "";

    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002JJ\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\b2\b\u0010\t\u001a\u0004\u0018\u00010\b2\b\u0010\n\u001a\u0004\u0018\u00010\b2\b\u0010\u000b\u001a\u0004\u0018\u00010\b2\b\u0010\f\u001a\u0004\u0018\u00010\b2\b\u0010\r\u001a\u0004\u0018\u00010\b¨\u0006\u000e"}, d2 = {"Lcom/psychiatrygarden/activity/QuestionWrongCollectionNoteActivity$Companion;", "", "()V", "navigationToQuestionWrongCollectionNote", "", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "type", "", "identityId", "moduleType", UriUtil.QUERY_CATEGORY, "exportFuncIdentityId", "isShowNumber", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final void navigationToQuestionWrongCollectionNote(@NotNull Context context, @Nullable String type, @Nullable String identityId, @Nullable String moduleType, @Nullable String category, @Nullable String exportFuncIdentityId, @Nullable String isShowNumber) {
            Intrinsics.checkNotNullParameter(context, "context");
            Intent intent = new Intent(context, (Class<?>) QuestionWrongCollectionNoteActivity.class);
            intent.putExtra("type", type);
            intent.putExtra("identity_id", identityId);
            intent.putExtra("module_type", moduleType);
            intent.putExtra(UriUtil.QUERY_CATEGORY, category);
            intent.putExtra("export_func_identity_id", exportFuncIdentityId);
            intent.putExtra("is_show_number", isShowNumber);
            context.startActivity(intent);
        }
    }

    @Metadata(d1 = {"\u0000%\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H\u0016J\u0012\u0010\u0004\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u0018\u0010\b\u001a\u00020\t2\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\u0003H\u0016¨\u0006\u000b"}, d2 = {"com/psychiatrygarden/activity/QuestionWrongCollectionNoteActivity$initTab$2", "Lcn/webdemo/com/supporfragment/tablayout/buildins/commonnavigator/abs/CommonNavigatorAdapter;", "getCount", "", "getIndicator", "Lcn/webdemo/com/supporfragment/tablayout/buildins/commonnavigator/abs/IPagerIndicator;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "getTitleView", "Lcn/webdemo/com/supporfragment/tablayout/buildins/commonnavigator/abs/IPagerTitleView;", "index", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.psychiatrygarden.activity.QuestionWrongCollectionNoteActivity$initTab$2, reason: invalid class name */
    public static final class AnonymousClass2 extends CommonNavigatorAdapter {
        public AnonymousClass2() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void getTitleView$lambda$0(QuestionWrongCollectionNoteActivity this$0, int i2, View view) throws Resources.NotFoundException {
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
            return QuestionWrongCollectionNoteActivity.this.listTabs.size();
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
            CommonPagerTitleView commonPagerTitleView = new CommonPagerTitleView(QuestionWrongCollectionNoteActivity.this);
            commonPagerTitleView.setContentView(R.layout.item_tabs_zuti);
            commonPagerTitleView.findViewById(R.id.viewStart).setVisibility(index == 0 ? 0 : 8);
            final TextView textView = (TextView) commonPagerTitleView.findViewById(R.id.tv_tabs_name);
            final ImageView imageView = (ImageView) commonPagerTitleView.findViewById(R.id.img_choose);
            textView.setText(((QuestionCategoryBean) QuestionWrongCollectionNoteActivity.this.listTabs.get(index)).getTitle());
            final QuestionWrongCollectionNoteActivity questionWrongCollectionNoteActivity = QuestionWrongCollectionNoteActivity.this;
            commonPagerTitleView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.wf
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) throws Resources.NotFoundException {
                    QuestionWrongCollectionNoteActivity.AnonymousClass2.getTitleView$lambda$0(questionWrongCollectionNoteActivity, index, view);
                }
            });
            commonPagerTitleView.setOnPagerTitleChangeListener(new CommonPagerTitleView.OnPagerTitleChangeListener() { // from class: com.psychiatrygarden.activity.QuestionWrongCollectionNoteActivity$initTab$2$getTitleView$2
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

    private final void getActivityParams() {
        Intent intent = getIntent();
        if (intent != null) {
            this.type = intent.getStringExtra("type");
            this.identityId = intent.getStringExtra("identity_id");
            this.moduleType = intent.getStringExtra("module_type");
            this.exportFuncIdentityId = intent.getStringExtra("export_func_identity_id");
            this.isShowNumber = intent.getStringExtra("is_show_number");
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

    /* JADX INFO: Access modifiers changed from: private */
    public static final void init$lambda$0(QuestionWrongCollectionNoteActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void initSelectItemCategoryData(SelectIdentityBean.DataBean itemData) {
        if (itemData != null) {
            if (Intrinsics.areEqual("1", itemData.getType())) {
                this.curBaseItem = itemData;
                setCustomerRightIcon();
            } else {
                this.curBaseItem = itemData;
                setCustomerRightIcon();
            }
        }
    }

    private final void initTab() throws Resources.NotFoundException {
        if (this.listTabs.isEmpty()) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        for (QuestionCategoryBean questionCategoryBean : this.listTabs) {
            if (Intrinsics.areEqual("1", questionCategoryBean.getType())) {
                Bundle bundle = new Bundle();
                List<QuestionCategoryBean> list = this.listTabs;
                Intrinsics.checkNotNull(list, "null cannot be cast to non-null type java.io.Serializable");
                bundle.putSerializable("tabList", (Serializable) list);
                bundle.putString("id", questionCategoryBean.getId());
                bundle.putString(KnowledgeQuestionListFragment.EXTRA_DOMAIN_TYPE, this.type);
                arrayList.add(new BaseViewPagerAdapter.PagerInfo(questionCategoryBean.getTitle(), QuestionWrongKnowledgeMapFragment.class, bundle));
            } else {
                Bundle bundle2 = QuestionWrongCollectionNoteFragment.getBundle(this.type, this.identityId, this.exportFuncIdentityId, this.isShowNumber);
                List<QuestionCategoryBean> list2 = this.listTabs;
                Intrinsics.checkNotNull(list2, "null cannot be cast to non-null type java.io.Serializable");
                bundle2.putSerializable("tabList", (Serializable) list2);
                bundle2.putString("id", questionCategoryBean.getId());
                arrayList.add(new BaseViewPagerAdapter.PagerInfo(questionCategoryBean.getTitle(), QuestionWrongCollectionNoteFragment.class, bundle2));
            }
        }
        final BaseViewPagerAdapter baseViewPagerAdapter = new BaseViewPagerAdapter(this.mContext, getSupportFragmentManager(), arrayList);
        ViewPager viewPager = this.viewPager;
        MagicIndicator magicIndicator = null;
        if (viewPager == null) {
            Intrinsics.throwUninitializedPropertyAccessException("viewPager");
            viewPager = null;
        }
        viewPager.setAdapter(baseViewPagerAdapter);
        CommonNavigator commonNavigator = new CommonNavigator(this.mContext);
        commonNavigator.setSkimOver(true);
        commonNavigator.setAdapter(new AnonymousClass2());
        MagicIndicator magicIndicator2 = this.magicIndicator;
        if (magicIndicator2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("magicIndicator");
            magicIndicator2 = null;
        }
        magicIndicator2.setNavigator(commonNavigator);
        MagicIndicator magicIndicator3 = this.magicIndicator;
        if (magicIndicator3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("magicIndicator");
            magicIndicator3 = null;
        }
        ViewPager viewPager2 = this.viewPager;
        if (viewPager2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("viewPager");
            viewPager2 = null;
        }
        ViewPagerHelper.bind(magicIndicator3, viewPager2);
        ViewPager viewPager3 = this.viewPager;
        if (viewPager3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("viewPager");
            viewPager3 = null;
        }
        viewPager3.addOnPageChangeListener(new ViewPager.OnPageChangeListener() { // from class: com.psychiatrygarden.activity.QuestionWrongCollectionNoteActivity.initTab.3
            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrollStateChanged(int state) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageSelected(int position) {
                QuestionWrongCollectionNoteActivity.this.curPosition = position;
                try {
                    Fragment curFragment = baseViewPagerAdapter.getCurFragment();
                    int currentPosition = curFragment instanceof QuestionWrongKnowledgeMapFragment ? ((QuestionWrongKnowledgeMapFragment) curFragment).currentPosition : curFragment instanceof QuestionWrongCollectionNoteFragment ? ((QuestionWrongCollectionNoteFragment) curFragment).getCurrentPosition() : 0;
                    QuestionWrongCollectionNoteActivity questionWrongCollectionNoteActivity = QuestionWrongCollectionNoteActivity.this;
                    questionWrongCollectionNoteActivity.initSelectItemCategoryData(((QuestionCategoryBean) questionWrongCollectionNoteActivity.listTabs.get(position)).getChildren().get(currentPosition));
                    QuestionWrongCollectionNoteActivity.this.selectFragmentChange(currentPosition);
                    QuestionWrongCollectionNoteActivity.this.setCustomerRightIcon();
                } catch (Exception e2) {
                    System.out.println((Object) e2.getMessage());
                }
            }
        });
        ViewPager viewPager4 = this.viewPager;
        if (viewPager4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("viewPager");
            viewPager4 = null;
        }
        viewPager4.setOffscreenPageLimit(this.listTabs.size());
        ViewPager viewPager5 = this.viewPager;
        if (viewPager5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("viewPager");
            viewPager5 = null;
        }
        viewPager5.setCurrentItem(0);
        initSelectItemCategoryData(this.listTabs.get(0).getChildren().get(0));
        MagicIndicator magicIndicator4 = this.magicIndicator;
        if (magicIndicator4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("magicIndicator");
        } else {
            magicIndicator = magicIndicator4;
        }
        magicIndicator.setVisibility(this.listTabs.size() <= 1 ? 8 : 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void selectFragmentChange(int childPosition) {
        try {
            SelectIdentityBean.DataBean dataBean = this.listTabs.get(this.curPosition).getChildren().get(childPosition);
            boolean zAreEqual = Intrinsics.areEqual(dataBean.getType(), "1");
            this.currentPageIsKnowledge = zAreEqual;
            if (zAreEqual) {
                this.curIdentityId = dataBean.getId();
                this.curModuleType = "";
            } else {
                this.curIdentityId = dataBean.getIdentity_id();
                this.curModuleType = dataBean.getModule_type();
            }
        } catch (Exception e2) {
            System.out.println((Object) e2.getMessage());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x006e, code lost:
    
        if (r0.equals(org.apache.http.cookie.ClientCookie.COMMENT_ATTR) == false) goto L55;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x0077, code lost:
    
        if (r0.equals("error") == false) goto L55;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x0080, code lost:
    
        if (r0.equals("note") == false) goto L55;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x0089, code lost:
    
        if (r0.equals("praise") == false) goto L55;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x008c, code lost:
    
        r0 = r7.ivActionbarRight;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x008e, code lost:
    
        if (r0 != null) goto L43;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x0090, code lost:
    
        kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("ivActionbarRight");
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x0094, code lost:
    
        r2 = r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x0095, code lost:
    
        r2.setVisibility(8);
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x009f, code lost:
    
        if (r0.equals("collection") == false) goto L55;
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x00a2, code lost:
    
        r0 = r7.ivActionbarRight;
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x00a4, code lost:
    
        if (r0 != null) goto L51;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x00a6, code lost:
    
        kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("ivActionbarRight");
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x00aa, code lost:
    
        r2 = r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x00ab, code lost:
    
        if (r1 == false) goto L54;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x00ad, code lost:
    
        r3 = 8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x00ae, code lost:
    
        r2.setVisibility(r3);
     */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue
    java.lang.NullPointerException: Cannot invoke "java.util.List.iterator()" because the return value of "jadx.core.dex.visitors.regions.SwitchOverStringVisitor$SwitchData.getNewCases()" is null
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.restoreSwitchOverString(SwitchOverStringVisitor.java:109)
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visitRegion(SwitchOverStringVisitor.java:66)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:77)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:82)
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void setCustomerRightIcon() {
        /*
            r7 = this;
            java.util.List<com.psychiatrygarden.bean.QuestionCategoryBean> r0 = r7.listTabs
            java.util.Collection r0 = (java.util.Collection) r0
            boolean r0 = r0.isEmpty()
            r1 = 1
            r0 = r0 ^ r1
            r2 = 0
            r3 = 0
            if (r0 == 0) goto L30
            com.psychiatrygarden.bean.SelectIdentityBean$DataBean r0 = r7.curBaseItem
            if (r0 != 0) goto L30
            java.util.List<com.psychiatrygarden.bean.QuestionCategoryBean> r0 = r7.listTabs
            java.lang.Object r0 = r0.get(r3)
            com.psychiatrygarden.bean.QuestionCategoryBean r0 = (com.psychiatrygarden.bean.QuestionCategoryBean) r0
            java.util.List r0 = r0.getChildren()
            if (r0 == 0) goto L27
            java.lang.Object r0 = r0.get(r3)
            com.psychiatrygarden.bean.SelectIdentityBean$DataBean r0 = (com.psychiatrygarden.bean.SelectIdentityBean.DataBean) r0
            goto L28
        L27:
            r0 = r2
        L28:
            kotlin.jvm.internal.Intrinsics.checkNotNull(r0)
            java.lang.String r0 = r0.getCategory()
            goto L3e
        L30:
            com.psychiatrygarden.bean.SelectIdentityBean$DataBean r0 = r7.curBaseItem
            if (r0 == 0) goto L3c
            kotlin.jvm.internal.Intrinsics.checkNotNull(r0)
            java.lang.String r0 = r0.getCategory()
            goto L3e
        L3c:
            java.lang.String r0 = ""
        L3e:
            java.lang.String r4 = "points"
            boolean r4 = kotlin.jvm.internal.Intrinsics.areEqual(r4, r0)
            if (r4 != 0) goto L58
            java.lang.String r4 = "cases"
            boolean r4 = kotlin.jvm.internal.Intrinsics.areEqual(r4, r0)
            if (r4 != 0) goto L58
            java.lang.String r4 = "unit"
            boolean r0 = kotlin.jvm.internal.Intrinsics.areEqual(r4, r0)
            if (r0 == 0) goto L57
            goto L58
        L57:
            r1 = r3
        L58:
            java.lang.String r0 = r7.type
            java.lang.String r4 = "ivActionbarRight"
            r5 = 8
            if (r0 == 0) goto Lb2
            int r6 = r0.hashCode()
            switch(r6) {
                case -1741312354: goto L99;
                case -980226692: goto L83;
                case 3387378: goto L7a;
                case 96784904: goto L71;
                case 950398559: goto L68;
                default: goto L67;
            }
        L67:
            goto Lb2
        L68:
            java.lang.String r1 = "comment"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L8c
            goto Lb2
        L71:
            java.lang.String r6 = "error"
            boolean r0 = r0.equals(r6)
            if (r0 != 0) goto La2
            goto Lb2
        L7a:
            java.lang.String r6 = "note"
            boolean r0 = r0.equals(r6)
            if (r0 != 0) goto La2
            goto Lb2
        L83:
            java.lang.String r1 = "praise"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L8c
            goto Lb2
        L8c:
            android.widget.ImageView r0 = r7.ivActionbarRight
            if (r0 != 0) goto L94
            kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException(r4)
            goto L95
        L94:
            r2 = r0
        L95:
            r2.setVisibility(r5)
            goto Lbe
        L99:
            java.lang.String r6 = "collection"
            boolean r0 = r0.equals(r6)
            if (r0 != 0) goto La2
            goto Lb2
        La2:
            android.widget.ImageView r0 = r7.ivActionbarRight
            if (r0 != 0) goto Laa
            kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException(r4)
            goto Lab
        Laa:
            r2 = r0
        Lab:
            if (r1 == 0) goto Lae
            r3 = r5
        Lae:
            r2.setVisibility(r3)
            goto Lbe
        Lb2:
            android.widget.ImageView r0 = r7.ivActionbarRight
            if (r0 != 0) goto Lba
            kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException(r4)
            goto Lbb
        Lba:
            r2 = r0
        Lbb:
            r2.setVisibility(r5)
        Lbe:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.psychiatrygarden.activity.QuestionWrongCollectionNoteActivity.setCustomerRightIcon():void");
    }

    private final void setCustomerTitle() {
        String str = this.type;
        TextView textView = null;
        if (Intrinsics.areEqual(str, KnowledgeListType.ERROR.getType())) {
            TextView textView2 = this.textTitle;
            if (textView2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("textTitle");
            } else {
                textView = textView2;
            }
            textView.setText("我的错题");
            return;
        }
        if (Intrinsics.areEqual(str, KnowledgeListType.COLLECTION.getType())) {
            TextView textView3 = this.textTitle;
            if (textView3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("textTitle");
            } else {
                textView = textView3;
            }
            textView.setText("我的收藏");
            return;
        }
        if (Intrinsics.areEqual(str, KnowledgeListType.NOTE.getType())) {
            TextView textView4 = this.textTitle;
            if (textView4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("textTitle");
            } else {
                textView = textView4;
            }
            textView.setText("我的笔记");
            return;
        }
        if (Intrinsics.areEqual(str, KnowledgeListType.PRAISE.getType())) {
            TextView textView5 = this.textTitle;
            if (textView5 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("textTitle");
            } else {
                textView = textView5;
            }
            textView.setText("我的点赞");
            return;
        }
        if (Intrinsics.areEqual(str, KnowledgeListType.COMMENT.getType())) {
            TextView textView6 = this.textTitle;
            if (textView6 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("textTitle");
            } else {
                textView = textView6;
            }
            textView.setText("我的评论");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setListenerForWidget$lambda$1(final QuestionWrongCollectionNoteActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        boolean z2 = this$0.currentPageIsKnowledge;
        boolean zBooleanValue = false;
        if (z2) {
            Boolean bool = this$0.showEditMapKnowledge.get(this$0.curIdentityId);
            if (bool != null) {
                zBooleanValue = bool.booleanValue();
            }
        } else {
            if (z2) {
                throw new NoWhenBranchMatchedException();
            }
            Boolean bool2 = this$0.showEditMapBaseQuestion.get(this$0.curIdentityId);
            if (bool2 != null) {
                zBooleanValue = bool2.booleanValue();
            }
        }
        new XPopup.Builder(this$0).asCustom(new ErrorExportPopuWindow(this$0, this$0.type, zBooleanValue, new ErrorExportPopuWindow.ClickIml() { // from class: com.psychiatrygarden.activity.QuestionWrongCollectionNoteActivity$setListenerForWidget$1$1
            @Override // com.psychiatrygarden.widget.ErrorExportPopuWindow.ClickIml
            public void mClickIml(@Nullable String isExport, @Nullable Switch errorSetting) {
                if (!StringsKt__StringsJVMKt.equals$default(isExport, "export", false, 2, null)) {
                    if (!StringsKt__StringsJVMKt.equals$default(isExport, "compose_test", false, 2, null)) {
                        this.this$0.settingErrorConfig(isExport, errorSetting);
                        return;
                    }
                    String str = this.this$0.type;
                    KnowledgeListType knowledgeListType = KnowledgeListType.ERROR;
                    CombineQuestionMainNewActivity.gotoCombineQuestionMain(this.this$0.mContext, Intrinsics.areEqual(str, knowledgeListType.getType()) ? knowledgeListType.getType() : KnowledgeListType.COLLECTION.getType());
                    return;
                }
                QuestionWrongCollectionNoteActivity questionWrongCollectionNoteActivity = this.this$0;
                questionWrongCollectionNoteActivity.pointCount(questionWrongCollectionNoteActivity.mContext, questionWrongCollectionNoteActivity.type);
                if (UserConfig.getInstance().getUser().getIs_vip().equals("0")) {
                    this.this$0.startActivity(new Intent(this.this$0, (Class<?>) MemberCenterActivity.class));
                    return;
                }
                SelectIdentityBean.DataBean dataBean = this.this$0.curBaseItem;
                if (!Intrinsics.areEqual(dataBean != null ? dataBean.getType() : null, "1")) {
                    if (this.this$0.curBaseItem != null) {
                        QuestionWrongCollectionNoteActivity questionWrongCollectionNoteActivity2 = this.this$0;
                        Bundle bundle = new Bundle();
                        SelectIdentityBean.DataBean dataBean2 = questionWrongCollectionNoteActivity2.curBaseItem;
                        Intrinsics.checkNotNull(dataBean2);
                        bundle.putString(UriUtil.QUERY_CATEGORY, dataBean2.getCategory());
                        SelectIdentityBean.DataBean dataBean3 = questionWrongCollectionNoteActivity2.curBaseItem;
                        Intrinsics.checkNotNull(dataBean3);
                        bundle.putString("module_type", dataBean3.getModule_type());
                        SelectIdentityBean.DataBean dataBean4 = questionWrongCollectionNoteActivity2.curBaseItem;
                        Intrinsics.checkNotNull(dataBean4);
                        bundle.putString("identity_id", dataBean4.getIdentity_id());
                        SelectIdentityBean.DataBean dataBean5 = questionWrongCollectionNoteActivity2.curBaseItem;
                        Intrinsics.checkNotNull(dataBean5);
                        bundle.putString("export_func_identity_id", dataBean5.getExport_func_identity_id());
                        bundle.putString("type", questionWrongCollectionNoteActivity2.type);
                        new XPopup.Builder(questionWrongCollectionNoteActivity2).asCustom(new ExportDescriptionPop(questionWrongCollectionNoteActivity2, bundle)).toggle();
                        return;
                    }
                    return;
                }
                Bundle bundle2 = new Bundle();
                QuestionWrongCollectionNoteActivity questionWrongCollectionNoteActivity3 = this.this$0;
                SelectIdentityBean.DataBean dataBean6 = questionWrongCollectionNoteActivity3.curBaseItem;
                String category = dataBean6 != null ? dataBean6.getCategory() : null;
                String str2 = "";
                if (category == null) {
                    category = "";
                } else {
                    Intrinsics.checkNotNullExpressionValue(category, "curBaseItem?.category ?: \"\"");
                }
                bundle2.putString(UriUtil.QUERY_CATEGORY, category);
                SelectIdentityBean.DataBean dataBean7 = questionWrongCollectionNoteActivity3.curBaseItem;
                String id = dataBean7 != null ? dataBean7.getId() : null;
                if (id == null) {
                    id = "";
                } else {
                    Intrinsics.checkNotNullExpressionValue(id, "curBaseItem?.id ?: \"\"");
                }
                bundle2.putString("identity_id", id);
                SelectIdentityBean.DataBean dataBean8 = questionWrongCollectionNoteActivity3.curBaseItem;
                String export_func_identity_id = dataBean8 != null ? dataBean8.getExport_func_identity_id() : null;
                if (export_func_identity_id != null) {
                    Intrinsics.checkNotNullExpressionValue(export_func_identity_id, "curBaseItem?.export_func_identity_id ?: \"\"");
                    str2 = export_func_identity_id;
                }
                bundle2.putString("export_func_identity_id", str2);
                bundle2.putString("isKnowledge", "1");
                bundle2.putString(KnowledgeQuestionListFragment.EXTRA_DOMAIN_TYPE, questionWrongCollectionNoteActivity3.type);
                SelectIdentityBean.DataBean dataBean9 = questionWrongCollectionNoteActivity3.curBaseItem;
                Intrinsics.checkNotNull(dataBean9);
                bundle2.putString(KnowledgeQuestionListFragment.EXTRA_TREE_ID, dataBean9.getId());
                new XPopup.Builder(this.this$0).asCustom(new ExportDescriptionPop(this.this$0, bundle2)).toggle();
            }
        })).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void settingErrorConfig(final String status, final Switch errorSetting) {
        if (status == null || status.length() == 0) {
            return;
        }
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("status", status);
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.settingErrorConfig, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.QuestionWrongCollectionNoteActivity.settingErrorConfig.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(@NotNull Throwable t2, int errorNo, @NotNull String strMsg) {
                Intrinsics.checkNotNullParameter(t2, "t");
                Intrinsics.checkNotNullParameter(strMsg, "strMsg");
                super.onFailure(t2, errorNo, strMsg);
                QuestionWrongCollectionNoteActivity.this.hideProgressDialog();
                Switch r2 = errorSetting;
                if (r2 == null) {
                    return;
                }
                r2.setChecked(Intrinsics.areEqual(UserConfig.getInstance().getUser().getError_set(), "1"));
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                QuestionWrongCollectionNoteActivity.this.showProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(@Nullable String s2) {
                if (s2 != null) {
                    try {
                        String str = status;
                        Switch r12 = errorSetting;
                        QuestionWrongCollectionNoteActivity questionWrongCollectionNoteActivity = QuestionWrongCollectionNoteActivity.this;
                        JSONObject jSONObject = new JSONObject(s2);
                        if (Intrinsics.areEqual(jSONObject.optString("code"), "200")) {
                            UserConfig.getInstance().getUser().setError_set(str);
                        } else {
                            String strOptString = jSONObject.optString("message");
                            if (r12 != null) {
                                r12.setChecked(Intrinsics.areEqual(UserConfig.getInstance().getUser().getError_set(), "1"));
                            }
                            ToastUtil.shortToast(questionWrongCollectionNoteActivity, strOptString);
                        }
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
                QuestionWrongCollectionNoteActivity.this.hideProgressDialog();
            }
        });
        AliyunEvent aliyunEvent = AliyunEvent.ErrorQuestionSetting;
        CommonUtil.addLog(aliyunEvent.getKey(), aliyunEvent.getValue(), System.currentTimeMillis() + "", "", "", "", "", "2");
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() throws Resources.NotFoundException {
        View viewFindViewById = findViewById(R.id.iv_actionbar_record);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(R.id.iv_actionbar_record)");
        this.ivActionbarRight = (ImageView) viewFindViewById;
        View viewFindViewById2 = findViewById(R.id.iv_actionbar_back);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "findViewById(R.id.iv_actionbar_back)");
        this.imageBack = (ImageView) viewFindViewById2;
        View viewFindViewById3 = findViewById(R.id.viewPager);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById3, "findViewById(R.id.viewPager)");
        this.viewPager = (ViewPager) viewFindViewById3;
        View viewFindViewById4 = findViewById(R.id.magicIndicator);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById4, "findViewById(R.id.magicIndicator)");
        this.magicIndicator = (MagicIndicator) viewFindViewById4;
        View viewFindViewById5 = findViewById(R.id.txt_actionbar_title);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById5, "findViewById(R.id.txt_actionbar_title)");
        this.textTitle = (TextView) viewFindViewById5;
        ImageView imageView = this.imageBack;
        if (imageView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("imageBack");
            imageView = null;
        }
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.vf
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                QuestionWrongCollectionNoteActivity.init$lambda$0(this.f14028c, view);
            }
        });
        getActivityParams();
        setCustomerTitle();
        getTabList();
        setCustomerRightIcon();
    }

    @Subscribe
    public final void onEventMainThread(@NotNull MyCutQuestionEditEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (event.isKnowledge()) {
            this.showEditMapKnowledge.put(event.getIdentityId(), Boolean.valueOf(event.isShowEdit()));
        } else {
            this.showEditMapBaseQuestion.put(event.getIdentityId(), Boolean.valueOf(event.isShowEdit()));
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(@Nullable String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        this.mActionBar.hide();
        setContentView(R.layout.activity_question_wrong_collection_note);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        ImageView imageView = this.ivActionbarRight;
        if (imageView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ivActionbarRight");
            imageView = null;
        }
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.uf
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                QuestionWrongCollectionNoteActivity.setListenerForWidget$lambda$1(this.f13988c, view);
            }
        });
    }

    @Subscribe
    public final void onEventMainThread(@NotNull SelectErrorWrongFragmentEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        try {
            initSelectItemCategoryData(this.listTabs.get(this.curPosition).getChildren().get(event.getPosition()));
            selectFragmentChange(event.getPosition());
            setCustomerRightIcon();
        } catch (Exception e2) {
            System.out.println((Object) e2.getMessage());
        }
    }
}
