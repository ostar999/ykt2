package com.psychiatrygarden.fragmenthome;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;
import cn.webdemo.com.supporfragment.tablayout.MagicIndicator;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.CommonNavigator;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.IPagerIndicator;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.IPagerTitleView;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.titles.CommonPagerTitleView;
import com.aliyun.vod.common.utils.UriUtil;
import com.psychiatrygarden.activity.online.fragment.QuestionBankNewFragment;
import com.psychiatrygarden.baseview.BaseFragment;
import com.psychiatrygarden.baseview.ViewHolder;
import com.psychiatrygarden.bean.QuestionCategoryBean;
import com.psychiatrygarden.bean.SelectErrorWrongFragmentEvent;
import com.psychiatrygarden.bean.SelectIdentityBean;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.fragmenthome.BaseViewPagerAdapter;
import com.psychiatrygarden.fragmenthome.knowledge.KnowledgeQuestionListFragment;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.SkinManager;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes5.dex */
public class QuestionWrongCollectionNoteFragment extends BaseFragment {
    public static final String EXTRA_DATA_IS_ERROR_COLLECTION_NOTE = "is_error_collection_note";
    private String identity_id;
    private String is_show_number;
    private MagicIndicator magic_indicator;
    private ViewPager viewpager;
    private List<SelectIdentityBean.DataBean> children = new ArrayList();
    private int currentPosition = 0;
    private String mType = "";
    private String level1Id = "";
    ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() { // from class: com.psychiatrygarden.fragmenthome.QuestionWrongCollectionNoteFragment.2
        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageScrollStateChanged(int state) {
            QuestionWrongCollectionNoteFragment.this.magic_indicator.onPageScrollStateChanged(state);
        }

        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            QuestionWrongCollectionNoteFragment.this.magic_indicator.onPageScrolled(position, positionOffset, positionOffsetPixels);
        }

        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageSelected(int position) {
            QuestionWrongCollectionNoteFragment.this.magic_indicator.onPageSelected(position);
            QuestionWrongCollectionNoteFragment.this.currentPosition = position;
            EventBus.getDefault().post(new SelectErrorWrongFragmentEvent(position));
        }
    };

    /* renamed from: com.psychiatrygarden.fragmenthome.QuestionWrongCollectionNoteFragment$1, reason: invalid class name */
    public class AnonymousClass1 extends CommonNavigatorAdapter {
        public AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$getTitleView$0(int i2, View view) throws Resources.NotFoundException {
            QuestionWrongCollectionNoteFragment.this.viewpager.setCurrentItem(i2);
        }

        @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter
        public int getCount() {
            if (QuestionWrongCollectionNoteFragment.this.children == null) {
                return 0;
            }
            return QuestionWrongCollectionNoteFragment.this.children.size();
        }

        @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter
        public IPagerIndicator getIndicator(Context context) {
            return null;
        }

        @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter
        public IPagerTitleView getTitleView(final Context context, final int index) {
            CommonPagerTitleView commonPagerTitleView = new CommonPagerTitleView(QuestionWrongCollectionNoteFragment.this.getActivity());
            commonPagerTitleView.setContentView(R.layout.item_forum_tab);
            final TextView textView = (TextView) commonPagerTitleView.findViewById(R.id.item_question_column);
            textView.setText(((SelectIdentityBean.DataBean) QuestionWrongCollectionNoteFragment.this.children.get(index)).getTitle());
            commonPagerTitleView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.xb
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) throws Resources.NotFoundException {
                    this.f16118c.lambda$getTitleView$0(index, view);
                }
            });
            commonPagerTitleView.setOnPagerTitleChangeListener(new CommonPagerTitleView.OnPagerTitleChangeListener() { // from class: com.psychiatrygarden.fragmenthome.QuestionWrongCollectionNoteFragment.1.1
                @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.titles.CommonPagerTitleView.OnPagerTitleChangeListener
                public void onDeselected(int index2, int totalCount) {
                    textView.setTypeface(Typeface.DEFAULT);
                    GradientDrawable gradientDrawable = new GradientDrawable();
                    gradientDrawable.setColor(SkinManager.getThemeColor(context, R.attr.new_bg_two_color));
                    gradientDrawable.setCornerRadius(CommonUtil.dip2px(QuestionWrongCollectionNoteFragment.this.requireContext(), 8.0f));
                    textView.setTextColor(SkinManager.getThemeColor(context, R.attr.first_txt_color));
                    textView.setBackground(gradientDrawable);
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
                    GradientDrawable gradientDrawable = new GradientDrawable();
                    gradientDrawable.setColor(SkinManager.getThemeColor(context, R.attr.main_theme_five_deep_color));
                    gradientDrawable.setCornerRadius(CommonUtil.dip2px(QuestionWrongCollectionNoteFragment.this.requireContext(), 8.0f));
                    textView.setTextColor(SkinManager.getThemeColor(context, R.attr.main_theme_color));
                    textView.setBackground(gradientDrawable);
                }
            });
            return commonPagerTitleView;
        }
    }

    public static Bundle getBundle(String type, String identity_id, String export_func_identity_id, String is_show_number) {
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        bundle.putString("identity_id", identity_id);
        bundle.putString("is_show_number", is_show_number);
        bundle.putString("export_func_identity_id", export_func_identity_id);
        return bundle;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initTab$0() {
        if (this.children.isEmpty()) {
            return;
        }
        try {
            this.viewpager.setScrollX(30);
            if (this.viewpager.beginFakeDrag()) {
                this.viewpager.fakeDragBy(20.0f);
                this.viewpager.endFakeDrag();
                this.viewpager.setScrollX(0);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public int getCurrentPosition() {
        return this.currentPosition;
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public int getLayoutId() {
        return R.layout.fragment_question_wrong_collection_note;
    }

    public List<BaseViewPagerAdapter.PagerInfo> getViewPageInfo() {
        ArrayList arrayList = new ArrayList();
        List<SelectIdentityBean.DataBean> list = this.children;
        if (list != null && list.size() > 0) {
            for (int i2 = 0; i2 < this.children.size(); i2++) {
                Bundle bundle = new Bundle();
                bundle.putString("identity_id", "" + this.children.get(i2).getIdentity_id());
                bundle.putString("export_func_identity_id", "" + this.children.get(i2).getExport_func_identity_id());
                bundle.putString(UriUtil.QUERY_CATEGORY, "" + this.children.get(i2).getCategory());
                if (this.identity_id.equals("" + this.children.get(i2).getIdentity_id())) {
                    this.currentPosition = i2;
                }
                bundle.putString("module_type", "" + this.children.get(i2).getModule_type());
                if (this.children.get(i2).getCategory().equals("year")) {
                    SharePreferencesUtils.writeStrConfig(CommonParameter.default_identity_id, this.children.get(i2).getDefault_identity_id() + "", this.mContext);
                }
                bundle.putString("type", this.mType);
                bundle.putString("is_show_number", this.is_show_number);
                bundle.putString(KnowledgeQuestionListFragment.EXTRA_LEVEL1_ID, this.level1Id);
                bundle.putString(EXTRA_DATA_IS_ERROR_COLLECTION_NOTE, EXTRA_DATA_IS_ERROR_COLLECTION_NOTE);
                bundle.putString("show.dialog", "1");
                arrayList.add(new BaseViewPagerAdapter.PagerInfo(this.children.get(i2).getTitle(), QuestionBankNewFragment.class, bundle));
            }
        }
        return arrayList;
    }

    public void initTab() throws Resources.NotFoundException {
        CommonNavigator commonNavigator = new CommonNavigator(this.mContext);
        commonNavigator.setSkimOver(false);
        commonNavigator.setAdapter(new AnonymousClass1());
        this.magic_indicator.setNavigator(commonNavigator);
        if (this.mContext == null) {
            return;
        }
        List<BaseViewPagerAdapter.PagerInfo> viewPageInfo = getViewPageInfo();
        this.magic_indicator.setVisibility(0);
        this.viewpager.setAdapter(new BaseViewPagerAdapter(this.mContext, getChildFragmentManager(), viewPageInfo));
        this.viewpager.setOffscreenPageLimit(3);
        this.viewpager.setCurrentItem(this.currentPosition);
        this.magic_indicator.onPageSelected(this.currentPosition);
        this.viewpager.addOnPageChangeListener(this.onPageChangeListener);
        this.viewpager.postDelayed(new Runnable() { // from class: com.psychiatrygarden.fragmenthome.wb
            @Override // java.lang.Runnable
            public final void run() {
                this.f16099c.lambda$initTab$0();
            }
        }, 200L);
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void initViews(ViewHolder holder, View root) {
        Bundle arguments = getArguments();
        if (arguments != null) {
            this.mType = arguments.getString("type", "");
            this.identity_id = arguments.getString("identity_id", "");
            this.is_show_number = arguments.getString("is_show_number", "");
            List list = (List) arguments.getSerializable("tabList");
            this.level1Id = arguments.getString("id", "");
            if (list == null || list.isEmpty()) {
                return;
            }
            for (int i2 = 0; i2 < list.size(); i2++) {
                if (this.level1Id.equals(((QuestionCategoryBean) list.get(i2)).getId())) {
                    this.children.clear();
                    if (((QuestionCategoryBean) list.get(i2)).getChildren() == null || ((QuestionCategoryBean) list.get(i2)).getChildren().isEmpty()) {
                        return;
                    } else {
                        this.children.addAll(((QuestionCategoryBean) list.get(i2)).getChildren());
                    }
                }
            }
        }
        this.magic_indicator = (MagicIndicator) holder.get(R.id.magic_indicator);
        this.viewpager = (ViewPager) holder.get(R.id.viewpager);
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportFragment, cn.webdemo.com.supporfragment.ISupportFragment
    public void onLazyInitView(@Nullable Bundle savedInstanceState) throws Resources.NotFoundException {
        super.onLazyInitView(savedInstanceState);
        initTab();
        EventBus.getDefault().post(new SelectErrorWrongFragmentEvent(this.currentPosition));
    }
}
