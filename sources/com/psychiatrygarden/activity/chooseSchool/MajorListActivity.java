package com.psychiatrygarden.activity.chooseSchool;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.chooseSchool.fragment.FollowMajorsFragment;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.widget.ViewPagerFragmentAdapter;
import com.yikaobang.yixue.R;

/* loaded from: classes5.dex */
public class MajorListActivity extends BaseActivity implements View.OnClickListener {
    private ImageView ivActionbarBack;
    private TabLayout mTabLayout;
    private ViewPager2 mViewPager;
    private View toSearchView;
    private LinearLayout topLlSearch;
    private TextView topSearchTv;

    private void initView() throws Resources.NotFoundException {
        this.mActionBar.hide();
        this.mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        this.mViewPager = (ViewPager2) findViewById(R.id.viewpager);
        this.ivActionbarBack = (ImageView) findViewById(R.id.iv_actionbar_back);
        this.topLlSearch = (LinearLayout) findViewById(R.id.top_ll_search);
        this.topSearchTv = (TextView) findViewById(R.id.top_search_tv);
        this.toSearchView = findViewById(R.id.toSearchView);
        ViewPagerFragmentAdapter viewPagerFragmentAdapter = new ViewPagerFragmentAdapter(this);
        viewPagerFragmentAdapter.addFragment(FollowMajorsFragment.newInstance("MajorList", "1"));
        viewPagerFragmentAdapter.addFragment(FollowMajorsFragment.newInstance("MajorList", "2"));
        this.mViewPager.setAdapter(viewPagerFragmentAdapter);
        setTabView(this.mTabLayout);
        this.ivActionbarBack.setOnClickListener(this);
        this.topLlSearch.setOnClickListener(this);
        this.topSearchTv.setOnClickListener(this);
        this.toSearchView.setOnClickListener(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$setTabView$0(TabLayout.Tab tab, int i2) throws Resources.NotFoundException {
        if (i2 == 0) {
            tab.setText("专硕");
        } else {
            tab.setText("学硕");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$setTabView$1(View view) {
        return true;
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, (Class<?>) MajorListActivity.class);
    }

    private void setTabView(TabLayout mTabLayout) throws Resources.NotFoundException {
        TabLayout.TabView tabView;
        new TabLayoutMediator(mTabLayout, this.mViewPager, new TabLayoutMediator.TabConfigurationStrategy() { // from class: com.psychiatrygarden.activity.chooseSchool.x2
            @Override // com.google.android.material.tabs.TabLayoutMediator.TabConfigurationStrategy
            public final void onConfigureTab(TabLayout.Tab tab, int i2) throws Resources.NotFoundException {
                MajorListActivity.lambda$setTabView$0(tab, i2);
            }
        }).attach();
        int i2 = 0;
        while (i2 < mTabLayout.getTabCount()) {
            TabLayout.Tab tabAt = mTabLayout.getTabAt(i2);
            if (tabAt != null && (tabView = tabAt.view) != null) {
                tabView.setLongClickable(false);
                tabAt.view.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.y2
                    @Override // android.view.View.OnLongClickListener
                    public final boolean onLongClick(View view) {
                        return MajorListActivity.lambda$setTabView$1(view);
                    }
                });
                TextView textView = (TextView) LayoutInflater.from(this).inflate(R.layout.myfollow_school_tab_item, (ViewGroup) null).findViewById(R.id.tv_tab);
                textView.setText(tabAt.getText());
                textView.setTextSize(i2 == 0 ? 16.0f : 14.0f);
                textView.setTextColor(i2 == 0 ? SkinManager.getThemeColor(this, R.attr.first_text_color) : SkinManager.getThemeColor(this, R.attr.third_txt_color));
                textView.setTypeface(Typeface.DEFAULT_BOLD);
                tabAt.setCustomView(textView);
            }
            i2++;
        }
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() { // from class: com.psychiatrygarden.activity.chooseSchool.MajorListActivity.1
            @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
            public void onTabReselected(TabLayout.Tab tab) {
            }

            @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
            public void onTabSelected(TabLayout.Tab tab) {
                TextView textView2 = (TextView) tab.getCustomView();
                if (textView2 != null) {
                    textView2.setTextSize(16.0f);
                    textView2.setTextColor(SkinManager.getThemeColor(MajorListActivity.this, R.attr.first_text_color));
                    textView2.setTypeface(Typeface.DEFAULT_BOLD);
                }
            }

            @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
            public void onTabUnselected(TabLayout.Tab tab) {
                TextView textView2 = (TextView) tab.getCustomView();
                if (textView2 != null) {
                    textView2.setTextSize(14.0f);
                    textView2.setTextColor(SkinManager.getThemeColor(MajorListActivity.this, R.attr.third_txt_color));
                    textView2.setTypeface(Typeface.DEFAULT);
                }
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() throws Resources.NotFoundException {
        initView();
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v2) {
        int id = v2.getId();
        if (id == R.id.iv_actionbar_back) {
            finish();
        } else {
            if (id != R.id.toSearchView) {
                return;
            }
            SearchSchoolOrMajorAct.newIntent(this, 0);
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.major_list_activity);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}
