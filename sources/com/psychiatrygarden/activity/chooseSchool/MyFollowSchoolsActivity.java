package com.psychiatrygarden.activity.chooseSchool;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.chooseSchool.bean.FollowMajorsBean;
import com.psychiatrygarden.activity.chooseSchool.bean.FollowSchoolBean;
import com.psychiatrygarden.activity.chooseSchool.fragment.FollowMajorsFragment;
import com.psychiatrygarden.activity.chooseSchool.fragment.FollowSchoolFragment;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.widget.ViewPagerFragmentAdapter;
import com.yikaobang.yixue.R;
import java.util.List;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class MyFollowSchoolsActivity extends BaseActivity implements View.OnClickListener {
    private TextView addTargetSchoolBtnTv;
    private boolean cancelBtnCanClick;
    private LinearLayout followCalcelLl;
    private FollowMajorsFragment followMajorsFragment;
    private FollowSchoolFragment followSchoolFragment;
    private TabLayout mTabLayout;
    private ViewPager2 mViewPager;
    private boolean isEditMode = false;
    private boolean isSelectAll = false;
    private String cancelFollowType = "";

    private void getSelectCount() {
        int i2 = 0;
        if (this.mViewPager.getCurrentItem() == 0) {
            List<FollowSchoolBean.DataBean> detailList = this.followSchoolFragment.getDetailList();
            int i3 = 0;
            while (i2 < detailList.size()) {
                if (detailList.get(i2).isItem_select()) {
                    i3++;
                }
                i2++;
            }
            setTitle("已选" + i3 + "个院校");
            return;
        }
        List<FollowMajorsBean.DataBean> detailList2 = this.followMajorsFragment.getDetailList();
        int i4 = 0;
        while (i2 < detailList2.size()) {
            if (detailList2.get(i2).isItem_select()) {
                i4++;
            }
            i2++;
        }
        setTitle("已选" + i4 + "个专业");
    }

    private void initSelectNumTitle() {
        int i2 = 0;
        if (this.mViewPager.getCurrentItem() == 0) {
            List<FollowSchoolBean.DataBean> detailList = this.followSchoolFragment.getDetailList();
            int i3 = 0;
            while (i2 < detailList.size()) {
                if (detailList.get(i2).isItem_select()) {
                    i3++;
                }
                i2++;
            }
            if (i3 == 0) {
                setTitle("关注院校");
                return;
            }
            setTitle("已选" + i3 + "个院校");
            return;
        }
        this.cancelFollowType = "2";
        List<FollowMajorsBean.DataBean> detailList2 = this.followMajorsFragment.getDetailList();
        int i4 = 0;
        while (i2 < detailList2.size()) {
            if (detailList2.get(i2).isItem_select()) {
                i4++;
            }
            i2++;
        }
        if (i4 == 0) {
            setTitle("关注专业");
            return;
        }
        setTitle("已选" + i4 + "个专业");
    }

    private void initView() throws Resources.NotFoundException {
        this.mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        this.mViewPager = (ViewPager2) findViewById(R.id.viewpager);
        this.followCalcelLl = (LinearLayout) findViewById(R.id.follow_cancel_ll);
        TextView textView = (TextView) findViewById(R.id.add_target_school_btn_tv);
        this.addTargetSchoolBtnTv = textView;
        textView.setOnClickListener(this);
        ViewPagerFragmentAdapter viewPagerFragmentAdapter = new ViewPagerFragmentAdapter(this);
        this.followSchoolFragment = FollowSchoolFragment.newInstance("follow");
        this.followMajorsFragment = FollowMajorsFragment.newInstance("follow");
        viewPagerFragmentAdapter.addFragment(this.followSchoolFragment);
        viewPagerFragmentAdapter.addFragment(this.followMajorsFragment);
        this.mViewPager.setAdapter(viewPagerFragmentAdapter);
        setTabView(this.mTabLayout);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setContentView$0(View view) {
        if (this.isEditMode) {
            boolean z2 = !this.isSelectAll;
            this.isSelectAll = z2;
            this.cancelBtnCanClick = z2;
            if (this.mViewPager.getCurrentItem() == 0) {
                this.followSchoolFragment.selectAll(this.isSelectAll);
            } else {
                this.followMajorsFragment.selectAll(this.isSelectAll);
            }
            this.mTvActionbarRight.setText(this.isSelectAll ? "取消全选" : "全选");
            this.addTargetSchoolBtnTv.setBackground(ContextCompat.getDrawable(this, this.isSelectAll ? R.drawable.shape_btn_red_radius_12_day_night : R.drawable.shape_block_corners_12));
            setBtnTvCorlor();
            initSelectNumTitle();
            return;
        }
        this.isEditMode = true;
        this.cancelBtnCanClick = false;
        this.mTvActionbarRight.setText("全选");
        this.mTabLayout.setVisibility(8);
        setTitle(this.mViewPager.getCurrentItem() == 0 ? "关注院校" : "关注专业");
        this.mViewPager.setUserInputEnabled(false);
        this.mBtnActionbarBack.setImageResource(SkinManager.getCurrentSkinType(this) == 1 ? R.mipmap.ic_push_circle_close_night : R.mipmap.ic_push_circle_close_day);
        this.followCalcelLl.setVisibility(0);
        if (this.mViewPager.getCurrentItem() == 0) {
            this.followSchoolFragment.setEditingState(Boolean.valueOf(this.isEditMode));
        } else {
            this.followMajorsFragment.setEditingState(Boolean.valueOf(this.isEditMode));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setContentView$1(View view) {
        if (!this.isEditMode) {
            finish();
            return;
        }
        this.mTabLayout.setVisibility(0);
        setTitle("我的关注");
        this.mTvActionbarRight.setText("编辑");
        this.mViewPager.setUserInputEnabled(true);
        this.mBtnActionbarBack.setImageResource(SkinManager.getCurrentSkinType(this) == 1 ? R.mipmap.ic_black_back_night : R.mipmap.ic_black_back);
        this.followCalcelLl.setVisibility(8);
        this.isEditMode = false;
        setBtnNoClick();
        if (this.mViewPager.getCurrentItem() == 0) {
            this.followSchoolFragment.setEditingState(Boolean.valueOf(this.isEditMode));
            this.followSchoolFragment.selectAll(false);
        } else {
            this.followMajorsFragment.setEditingState(Boolean.valueOf(this.isEditMode));
            this.followMajorsFragment.selectAll(false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$setTabView$2(TabLayout.Tab tab, int i2) throws Resources.NotFoundException {
        if (i2 == 0) {
            tab.setText("关注院校");
        } else {
            tab.setText("关注专业");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$setTabView$3(View view) {
        return true;
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, (Class<?>) MyFollowSchoolsActivity.class);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setBtnNoClick() {
        this.cancelBtnCanClick = false;
        this.addTargetSchoolBtnTv.setBackground(ContextCompat.getDrawable(this, R.drawable.shape_block_corners_12));
        if (SkinManager.getCurrentSkinType(this) == 1) {
            this.addTargetSchoolBtnTv.setTextColor(ContextCompat.getColor(this, R.color.forth_txt_color_night));
        } else {
            this.addTargetSchoolBtnTv.setTextColor(ContextCompat.getColor(this, R.color.forth_txt_color));
        }
    }

    private void setBtnTvCorlor() {
        if (SkinManager.getCurrentSkinType(this) == 1) {
            this.addTargetSchoolBtnTv.setTextColor(ContextCompat.getColor(this, this.isSelectAll ? R.color.zx_color_blue_night : R.color.forth_txt_color_night));
        } else {
            this.addTargetSchoolBtnTv.setTextColor(ContextCompat.getColor(this, this.isSelectAll ? R.color.white : R.color.forth_txt_color));
        }
    }

    private void setTabView(TabLayout mTabLayout) throws Resources.NotFoundException {
        TabLayout.TabView tabView;
        new TabLayoutMediator(mTabLayout, this.mViewPager, new TabLayoutMediator.TabConfigurationStrategy() { // from class: com.psychiatrygarden.activity.chooseSchool.z2
            @Override // com.google.android.material.tabs.TabLayoutMediator.TabConfigurationStrategy
            public final void onConfigureTab(TabLayout.Tab tab, int i2) throws Resources.NotFoundException {
                MyFollowSchoolsActivity.lambda$setTabView$2(tab, i2);
            }
        }).attach();
        int i2 = 0;
        while (i2 < mTabLayout.getTabCount()) {
            TabLayout.Tab tabAt = mTabLayout.getTabAt(i2);
            if (tabAt != null && (tabView = tabAt.view) != null) {
                tabView.setLongClickable(false);
                tabAt.view.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.a3
                    @Override // android.view.View.OnLongClickListener
                    public final boolean onLongClick(View view) {
                        return MyFollowSchoolsActivity.lambda$setTabView$3(view);
                    }
                });
                TextView textView = (TextView) LayoutInflater.from(this).inflate(R.layout.myfollow_school_tab_item, (ViewGroup) null).findViewById(R.id.tv_tab);
                textView.setText(tabAt.getText());
                textView.setTextSize(i2 == 0 ? 16.0f : 14.0f);
                if (SkinManager.getCurrentSkinType(this.mContext) == 1) {
                    textView.setTextColor(getResources().getColor(i2 == 0 ? R.color.first_text_color_night : R.color.third_txt_color_night));
                } else {
                    textView.setTextColor(getResources().getColor(i2 == 0 ? R.color.first_text_color : R.color.third_txt_color));
                }
                textView.setTypeface(Typeface.DEFAULT_BOLD);
                tabAt.setCustomView(textView);
            }
            i2++;
        }
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() { // from class: com.psychiatrygarden.activity.chooseSchool.MyFollowSchoolsActivity.1
            @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
            public void onTabReselected(TabLayout.Tab tab) {
            }

            @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
            public void onTabSelected(TabLayout.Tab tab) {
                TextView textView2 = (TextView) tab.getCustomView();
                if (textView2 != null) {
                    textView2.setTextSize(16.0f);
                    textView2.setTextColor(MyFollowSchoolsActivity.this.getResources().getColor(SkinManager.getCurrentSkinType(MyFollowSchoolsActivity.this.mContext) == 1 ? R.color.first_text_color_night : R.color.first_text_color));
                    textView2.setTypeface(Typeface.DEFAULT_BOLD);
                }
            }

            @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
            public void onTabUnselected(TabLayout.Tab tab) {
                TextView textView2 = (TextView) tab.getCustomView();
                if (textView2 != null) {
                    textView2.setTextSize(14.0f);
                    textView2.setTextColor(MyFollowSchoolsActivity.this.getResources().getColor(SkinManager.getCurrentSkinType(MyFollowSchoolsActivity.this.mContext) == 1 ? R.color.third_txt_color_night : R.color.third_txt_color));
                    textView2.setTypeface(Typeface.DEFAULT);
                }
            }
        });
    }

    private void toCancelFollow(String ids) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("target_id", ids);
        ajaxParams.put("type", this.cancelFollowType);
        YJYHttpUtils.post(this, NetworkRequestsURL.cancelFollowSchool, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.chooseSchool.MyFollowSchoolsActivity.2
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                MyFollowSchoolsActivity.this.hideProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                MyFollowSchoolsActivity.this.showProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass2) s2);
                MyFollowSchoolsActivity.this.hideProgressDialog();
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if (jSONObject.optInt("code") != 200) {
                        Toast.makeText(MyFollowSchoolsActivity.this, jSONObject.optString("message"), 0).show();
                        return;
                    }
                    int currentItem = MyFollowSchoolsActivity.this.mViewPager.getCurrentItem();
                    if (currentItem == 0) {
                        MyFollowSchoolsActivity.this.followSchoolFragment.reflashList();
                    } else {
                        MyFollowSchoolsActivity.this.followMajorsFragment.reflashList();
                    }
                    MyFollowSchoolsActivity.this.setBtnNoClick();
                    MyFollowSchoolsActivity.this.setTitle(currentItem == 0 ? "关注院校" : "关注专业");
                } catch (Exception unused) {
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
        if (v2.getId() == R.id.add_target_school_btn_tv && this.cancelBtnCanClick) {
            String str = "";
            if (this.mViewPager.getCurrentItem() == 0) {
                this.cancelFollowType = "1";
                List<FollowSchoolBean.DataBean> detailList = this.followSchoolFragment.getDetailList();
                for (int i2 = 0; i2 < detailList.size(); i2++) {
                    FollowSchoolBean.DataBean dataBean = detailList.get(i2);
                    if (dataBean.isItem_select()) {
                        str = str + dataBean.getSchool_id() + ",";
                    }
                }
            } else {
                this.cancelFollowType = "2";
                List<FollowMajorsBean.DataBean> detailList2 = this.followMajorsFragment.getDetailList();
                for (int i3 = 0; i3 < detailList2.size(); i3++) {
                    FollowMajorsBean.DataBean dataBean2 = detailList2.get(i3);
                    if (dataBean2.isItem_select()) {
                        str = str + dataBean2.getMajor_id() + ",";
                    }
                }
            }
            toCancelFollow(str.substring(0, str.length() - 1));
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
        str.hashCode();
        switch (str) {
            case "target_school_no_select":
                setBtnNoClick();
                setTitle(this.mViewPager.getCurrentItem() == 0 ? "关注院校" : "关注专业");
                break;
            case "choose.school.select.update":
                initSelectNumTitle();
                break;
            case "target_school_has_select":
                this.cancelBtnCanClick = true;
                this.addTargetSchoolBtnTv.setBackground(ContextCompat.getDrawable(this, R.drawable.shape_btn_red_radius_12_day_night));
                if (SkinManager.getCurrentSkinType(this) == 1) {
                    this.addTargetSchoolBtnTv.setTextColor(ContextCompat.getColor(this, R.color.zx_color_blue_night));
                } else {
                    this.addTargetSchoolBtnTv.setTextColor(ContextCompat.getColor(this, R.color.white));
                }
                getSelectCount();
                break;
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_myfollow_school);
        setTitle("我的关注");
        this.mTvActionbarRight.setText("编辑");
        this.mTvActionbarRight.setVisibility(0);
        this.mTvActionbarRight.setEnabled(true);
        this.mBtnActionbarRight.setVisibility(8);
        this.mTvActionbarRight.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.b3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11218c.lambda$setContentView$0(view);
            }
        });
        this.mBtnActionbarBack.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.c3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11230c.lambda$setContentView$1(view);
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}
