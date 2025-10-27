package com.psychiatrygarden.activity.online.fragment;

import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;
import cn.hutool.core.lang.RegexPool;
import com.aliyun.vod.common.utils.UriUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.online.QuestionColumnSortActivity;
import com.psychiatrygarden.adapter.NewViewPager2Adapter;
import com.psychiatrygarden.baseview.BaseFragment;
import com.psychiatrygarden.baseview.ViewHolder;
import com.psychiatrygarden.bean.SelectIdentityBean;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.event.RedDotEvent;
import com.psychiatrygarden.event.ShowQuestionCombineEvent;
import com.psychiatrygarden.fragmenthome.SelfCombineQuestionFragment;
import com.psychiatrygarden.utils.AliyunEvent;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.weblong.SizeUtil;
import com.psychiatrygarden.widget.NestedRecyclerView;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;

/* loaded from: classes5.dex */
public class NormalQuestionBankFragment extends BaseFragment {
    private boolean adStart;
    private boolean closeAd;
    private boolean hasKnowledgeTab;
    private View mIvQuestionSort;
    private ImageView mIvQuestionSortLine;
    private NewViewPager2Adapter mPagerAdapter;
    private String question_bank_id;
    private NestedRecyclerView rvCategoryChild;
    private ViewPager2 viewpager;
    private final List<SelectIdentityBean.DataBean> children = new ArrayList();
    private final Map<Integer, View> titleTagViews = new ArrayMap();
    private List<Fragment> fragmentList = getViewPageInfo();
    private final BaseQuickAdapter<SelectIdentityBean.DataBean, BaseViewHolder> tabAdapter = new BaseQuickAdapter<SelectIdentityBean.DataBean, BaseViewHolder>(R.layout.item_normal_question_category) { // from class: com.psychiatrygarden.activity.online.fragment.NormalQuestionBankFragment.4
        @Override // com.chad.library.adapter.base.BaseQuickAdapter
        public void convert(@NonNull BaseViewHolder holder, SelectIdentityBean.DataBean item) {
            holder.setVisible(R.id.tv_column_tag, (TextUtils.isEmpty(item.getLabel()) || item.isSelect()) ? false : true).setText(R.id.tv_column_tag, item.getLabel());
            TextView textView = (TextView) holder.getView(R.id.tv_column_name);
            textView.setText(TextUtils.isEmpty(item.getTitle()) ? item.getName() : item.getTitle());
            textView.setSelected(item.isSelect());
            textView.setTypeface(item.isSelect() ? Typeface.DEFAULT_BOLD : Typeface.DEFAULT);
            textView.setCompoundDrawablesRelativeWithIntrinsicBounds((Drawable) null, (Drawable) null, (Drawable) null, (Drawable) null);
            int layoutPosition = holder.getLayoutPosition();
            if (NormalQuestionBankFragment.this.titleTagViews.get(Integer.valueOf(layoutPosition)) == null) {
                NormalQuestionBankFragment.this.titleTagViews.put(Integer.valueOf(layoutPosition), holder.getView(R.id.tv_column_tag));
            }
        }
    };
    private final ViewPager2.OnPageChangeCallback onPageChangeListener = new ViewPager2.OnPageChangeCallback() { // from class: com.psychiatrygarden.activity.online.fragment.NormalQuestionBankFragment.5
        @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
        public void onPageScrollStateChanged(int state) {
        }

        @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
        public void onPageSelected(int position) {
            if (NormalQuestionBankFragment.this.children.size() <= 1) {
                return;
            }
            int i2 = 0;
            while (i2 < NormalQuestionBankFragment.this.children.size()) {
                ((SelectIdentityBean.DataBean) NormalQuestionBankFragment.this.children.get(i2)).setSelect(position == i2);
                if (position == i2) {
                    NormalQuestionBankFragment normalQuestionBankFragment = NormalQuestionBankFragment.this;
                    normalQuestionBankFragment.setAliyunLog(((SelectIdentityBean.DataBean) normalQuestionBankFragment.children.get(i2)).getIdentity_id(), ((SelectIdentityBean.DataBean) NormalQuestionBankFragment.this.children.get(i2)).getTitle());
                }
                i2++;
            }
            if (NormalQuestionBankFragment.this.tabAdapter != null) {
                NormalQuestionBankFragment.this.tabAdapter.notifyDataSetChanged();
            }
            if (NormalQuestionBankFragment.this.tabAdapter.getRecyclerView() != null) {
                NormalQuestionBankFragment.this.tabAdapter.getRecyclerView().scrollToPosition(position);
            }
            if (NormalQuestionBankFragment.this.children.isEmpty()) {
                return;
            }
            int i3 = 0;
            while (true) {
                if (i3 >= NormalQuestionBankFragment.this.children.size()) {
                    i3 = -1;
                    break;
                } else if (TextUtils.equals("0", ((SelectIdentityBean.DataBean) NormalQuestionBankFragment.this.children.get(i3)).getIdentity_id())) {
                    EventBus.getDefault().post(new ShowQuestionCombineEvent(i3 == position, false));
                } else {
                    i3++;
                }
            }
            if (position == i3) {
                NormalQuestionBankFragment.this.adStart = false;
                EventBus.getDefault().post(new RedDotEvent(false));
            } else {
                if (NormalQuestionBankFragment.this.adStart) {
                    return;
                }
                EventBus.getDefault().post(new RedDotEvent(true));
            }
        }
    };

    private List<Fragment> getViewPageInfo() {
        ArrayList arrayList = new ArrayList();
        if (!this.children.isEmpty()) {
            for (int i2 = 0; i2 < this.children.size(); i2++) {
                Bundle bundle = new Bundle();
                bundle.putString("identity_id", "" + this.children.get(i2).getIdentity_id());
                bundle.putString("identity_title", "" + this.children.get(i2).getTitle());
                bundle.putString("question_bank_id", this.children.get(i2).getQuestion_bank_id());
                bundle.putString("export_func_identity_id", "" + this.children.get(i2).getExport_func_identity_id());
                bundle.putString(UriUtil.QUERY_CATEGORY, "" + this.children.get(i2).getCategory());
                bundle.putString("module_type", "" + this.children.get(i2).getModule_type());
                if ("year".equals(this.children.get(i2).getCategory())) {
                    SharePreferencesUtils.writeStrConfig(CommonParameter.default_identity_id, this.children.get(i2).getDefault_identity_id() + "", this.mContext);
                }
                bundle.putString("type", "all");
                if ("0".equals(this.children.get(i2).getIdentity_id())) {
                    if (i2 == 0) {
                        bundle.putString("identity_id", "" + this.children.get(1).getIdentity_id());
                        bundle.putString(UriUtil.QUERY_CATEGORY, "" + this.children.get(1).getCategory());
                        bundle.putString("module_type", "" + this.children.get(1).getModule_type());
                    } else {
                        bundle.putString("identity_id", "" + this.children.get(0).getIdentity_id());
                        bundle.putString(UriUtil.QUERY_CATEGORY, "" + this.children.get(0).getCategory());
                        bundle.putString("module_type", "" + this.children.get(0).getModule_type());
                    }
                    arrayList.add(SelfCombineQuestionFragment.getInstance(bundle));
                } else {
                    arrayList.add(QuestionBankNewFragmentForHome.newInstance(bundle));
                }
            }
        }
        return arrayList;
    }

    private void initColumns() {
        View view;
        this.fragmentList.clear();
        List<Fragment> viewPageInfo = getViewPageInfo();
        this.fragmentList.addAll(viewPageInfo);
        this.mIvQuestionSort.setVisibility(this.children.size() > 3 ? 0 : 8);
        this.mIvQuestionSortLine.setVisibility(this.children.size() > 3 ? 0 : 8);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.rvCategoryChild.getLayoutParams();
        layoutParams.rightMargin = SizeUtil.dp2px(this.mContext, this.children.size() > 3 ? 40 : 8);
        this.rvCategoryChild.setLayoutParams(layoutParams);
        this.rvCategoryChild.setVisibility(0);
        this.rvCategoryChild.setAdapter(this.tabAdapter);
        this.children.get(0).setSelect(true);
        this.tabAdapter.setList(this.children);
        setAliyunLog(this.children.get(0).getIdentity_id(), this.children.get(0).getTitle());
        if (getActivity() != null && !this.children.isEmpty() && this.children.size() == viewPageInfo.size()) {
            ArrayList arrayList = new ArrayList();
            for (int i2 = 0; i2 < this.children.size(); i2++) {
                String identity_id = this.children.get(i2).getIdentity_id();
                if (identity_id == null || !identity_id.matches(RegexPool.NUMBERS)) {
                    arrayList.add(Long.valueOf(i2));
                } else {
                    arrayList.add(Long.valueOf(Long.parseLong(identity_id)));
                }
            }
            this.mPagerAdapter = new NewViewPager2Adapter(this.fragmentList, arrayList, getChildFragmentManager(), getLifecycle());
            this.viewpager.setSaveEnabled(false);
            this.viewpager.setOffscreenPageLimit(this.fragmentList.size());
            this.viewpager.setAdapter(this.mPagerAdapter);
            this.viewpager.registerOnPageChangeCallback(this.onPageChangeListener);
            this.viewpager.setCurrentItem(0, false);
            if (!TextUtils.isEmpty(this.children.get(0).getLabel()) && (view = this.titleTagViews.get(0)) != null) {
                view.setVisibility(8);
            }
            this.viewpager.postDelayed(new Runnable() { // from class: com.psychiatrygarden.activity.online.fragment.a
                @Override // java.lang.Runnable
                public final void run() {
                    this.f13172c.lambda$initColumns$1();
                }
            }, 200L);
        }
        this.tabAdapter.setOnItemClickListener(new OnItemClickListener() { // from class: com.psychiatrygarden.activity.online.fragment.b
            @Override // com.chad.library.adapter.base.listener.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view2, int i3) {
                this.f13185c.lambda$initColumns$2(baseQuickAdapter, view2, i3);
            }
        });
        ((RecyclerView) this.viewpager.getChildAt(0)).addOnItemTouchListener(new RecyclerView.SimpleOnItemTouchListener() { // from class: com.psychiatrygarden.activity.online.fragment.NormalQuestionBankFragment.3
            private float initialX;
            private float initialY;

            /* JADX WARN: Removed duplicated region for block: B:22:0x006d  */
            @Override // androidx.recyclerview.widget.RecyclerView.SimpleOnItemTouchListener, androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public boolean onInterceptTouchEvent(androidx.recyclerview.widget.RecyclerView r6, android.view.MotionEvent r7) {
                /*
                    r5 = this;
                    int r0 = r7.getAction()
                    r1 = 0
                    r2 = 1
                    if (r0 == 0) goto L75
                    if (r0 == r2) goto L6d
                    r3 = 2
                    if (r0 == r3) goto L12
                    r7 = 3
                    if (r0 == r7) goto L6d
                    goto L88
                L12:
                    float r0 = r7.getX()
                    float r3 = r5.initialX
                    float r0 = r0 - r3
                    float r7 = r7.getY()
                    float r3 = r5.initialY
                    float r7 = r7 - r3
                    float r3 = java.lang.Math.abs(r0)
                    float r7 = java.lang.Math.abs(r7)
                    int r7 = (r3 > r7 ? 1 : (r3 == r7 ? 0 : -1))
                    if (r7 <= 0) goto L88
                    com.psychiatrygarden.activity.online.fragment.NormalQuestionBankFragment r7 = com.psychiatrygarden.activity.online.fragment.NormalQuestionBankFragment.this
                    androidx.viewpager2.widget.ViewPager2 r7 = com.psychiatrygarden.activity.online.fragment.NormalQuestionBankFragment.access$000(r7)
                    int r7 = r7.getCurrentItem()
                    com.psychiatrygarden.activity.online.fragment.NormalQuestionBankFragment r3 = com.psychiatrygarden.activity.online.fragment.NormalQuestionBankFragment.this
                    androidx.viewpager2.widget.ViewPager2 r3 = com.psychiatrygarden.activity.online.fragment.NormalQuestionBankFragment.access$000(r3)
                    androidx.recyclerview.widget.RecyclerView$Adapter r3 = r3.getAdapter()
                    java.util.Objects.requireNonNull(r3)
                    int r3 = r3.getItemCount()
                    int r3 = r3 - r2
                    r4 = 0
                    if (r7 != r3) goto L57
                    int r3 = (r0 > r4 ? 1 : (r0 == r4 ? 0 : -1))
                    if (r3 >= 0) goto L57
                    android.view.ViewParent r6 = r6.getParent()
                    r6.requestDisallowInterceptTouchEvent(r1)
                    goto L88
                L57:
                    if (r7 != 0) goto L65
                    int r7 = (r0 > r4 ? 1 : (r0 == r4 ? 0 : -1))
                    if (r7 <= 0) goto L65
                    android.view.ViewParent r6 = r6.getParent()
                    r6.requestDisallowInterceptTouchEvent(r1)
                    goto L88
                L65:
                    android.view.ViewParent r6 = r6.getParent()
                    r6.requestDisallowInterceptTouchEvent(r2)
                    goto L88
                L6d:
                    android.view.ViewParent r6 = r6.getParent()
                    r6.requestDisallowInterceptTouchEvent(r1)
                    goto L88
                L75:
                    float r0 = r7.getX()
                    r5.initialX = r0
                    float r7 = r7.getY()
                    r5.initialY = r7
                    android.view.ViewParent r6 = r6.getParent()
                    r6.requestDisallowInterceptTouchEvent(r2)
                L88:
                    return r1
                */
                throw new UnsupportedOperationException("Method not decompiled: com.psychiatrygarden.activity.online.fragment.NormalQuestionBankFragment.AnonymousClass3.onInterceptTouchEvent(androidx.recyclerview.widget.RecyclerView, android.view.MotionEvent):boolean");
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initColumns$1() {
        boolean z2;
        if (this.children.isEmpty()) {
            return;
        }
        int i2 = 0;
        while (true) {
            if (i2 >= this.children.size()) {
                i2 = -1;
                z2 = false;
                break;
            } else {
                if (TextUtils.equals("0", this.children.get(i2).getIdentity_id())) {
                    z2 = true;
                    break;
                }
                i2++;
            }
        }
        if (z2 && this.viewpager.getCurrentItem() == i2) {
            EventBus.getDefault().post(new ShowQuestionCombineEvent(true, false));
        } else {
            EventBus.getDefault().post(new ShowQuestionCombineEvent(false, false));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initColumns$2(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
        if (baseQuickAdapter.getData().size() == 1) {
            return;
        }
        this.viewpager.setCurrentItem(i2, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$0(View view) {
        if (isLogin()) {
            Intent intent = new Intent(getActivity(), (Class<?>) QuestionColumnSortActivity.class);
            intent.putExtra("listBean", (Serializable) this.children);
            startActivity(intent);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setAliyunLog(String id, String title) {
        ProjectApp.identity_title = title;
        ProjectApp.identity_id = id;
        ProjectApp.unit_title = "";
        ProjectApp.exam_title = "";
        AliyunEvent aliyunEvent = AliyunEvent.VisitColumn;
        String key = aliyunEvent.getKey();
        String value = aliyunEvent.getValue();
        CommonUtil.addLog(key, value, System.currentTimeMillis() + "", "", "[\"" + id + "\"]", "[\"" + title + "\"]", "", "2");
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public int getLayoutId() {
        return R.layout.fmt_normal_question_bank;
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void initViews(ViewHolder holder, View root) {
        this.mIvQuestionSort = holder.get(R.id.iv_question_sort);
        this.mIvQuestionSortLine = (ImageView) holder.get(R.id.iv_question_sort_line);
        this.rvCategoryChild = (NestedRecyclerView) holder.get(R.id.rvCategoryChild);
        this.viewpager = (ViewPager2) holder.get(R.id.viewpager);
        Bundle arguments = getArguments();
        if (arguments == null) {
            return;
        }
        String string = arguments.getString("children");
        this.question_bank_id = arguments.getString("question_bank_id");
        this.hasKnowledgeTab = arguments.getBoolean("hasKnowledgeTab", true);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.rvCategoryChild.getLayoutParams();
        layoutParams.topMargin = SizeUtil.dp2px(this.mContext, 6);
        this.rvCategoryChild.setLayoutParams(layoutParams);
        if (!TextUtils.isEmpty(string)) {
            this.children.clear();
            List list = (List) new Gson().fromJson(string, new TypeToken<List<SelectIdentityBean.DataBean>>() { // from class: com.psychiatrygarden.activity.online.fragment.NormalQuestionBankFragment.1
            }.getType());
            if (list != null && !list.isEmpty()) {
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    ((SelectIdentityBean.DataBean) it.next()).setQuestion_bank_id(this.question_bank_id);
                }
                this.children.addAll(list);
                initColumns();
            }
        }
        this.mIvQuestionSort.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.online.fragment.c
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13196c.lambda$initViews$0(view);
            }
        });
        this.rvCategoryChild.bindViewPager2(this.viewpager);
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    @Subscribe
    public void onEventMainThread(String str) {
        List list;
        super.onEventMainThread(str);
        if ("upIndexList".equals(str)) {
            String strConfig = SharePreferencesUtils.readStrConfig(CommonParameter.catalogue_q, this.mContext);
            if (TextUtils.isEmpty(strConfig) || (list = (List) new Gson().fromJson(strConfig, new TypeToken<List<SelectIdentityBean.DataBean>>() { // from class: com.psychiatrygarden.activity.online.fragment.NormalQuestionBankFragment.2
            }.getType())) == null) {
                return;
            }
            this.children.clear();
            this.children.addAll(list);
            int i2 = 0;
            while (i2 < this.children.size()) {
                this.children.get(i2).setSelect(i2 == 0);
                i2++;
            }
            initColumns();
        }
    }

    public void refreshQuestion() throws JSONException {
        for (int i2 = 0; i2 < this.fragmentList.size(); i2++) {
            if (i2 == this.viewpager.getCurrentItem()) {
                Fragment fragment = this.fragmentList.get(i2);
                if (fragment instanceof QuestionBankNewFragmentForHome) {
                    ((QuestionBankNewFragmentForHome) fragment).getQuestionBankData();
                    return;
                }
                return;
            }
        }
    }
}
