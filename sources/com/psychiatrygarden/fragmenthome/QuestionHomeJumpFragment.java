package com.psychiatrygarden.fragmenthome;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.Animatable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.psychiatrygarden.activity.HomePageNewActivity;
import com.psychiatrygarden.activity.LiveCalendarActivity;
import com.psychiatrygarden.activity.courselist.CourseSearchActivity;
import com.psychiatrygarden.baseview.BaseFragment;
import com.psychiatrygarden.baseview.ViewHolder;
import com.psychiatrygarden.utils.SkinManager;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes5.dex */
public class QuestionHomeJumpFragment extends BaseFragment implements View.OnClickListener {
    private ViewPager courseViewPage;
    private List<Fragment> fragmentList = new ArrayList();
    private TextView groupNameAllCourse;
    private ImageView groupNameAllCourseImg;
    private TextView groupNameLearnCenter;
    private ImageView groupNameLearnCenterImg;
    private ImageView imgLiveCalendarAnim;
    private ImageView imgSearchAllCourse;
    private ViewPagerAdapter viewPagerAdapter;

    private void initViewPage() throws Resources.NotFoundException {
        this.fragmentList.clear();
        AllCourseFragment allCourseFragment = new AllCourseFragment();
        LearnCenterFragment learnCenterFragment = new LearnCenterFragment();
        ArrayList arrayList = new ArrayList();
        this.fragmentList = arrayList;
        arrayList.add(allCourseFragment);
        this.fragmentList.add(learnCenterFragment);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager(), this.fragmentList);
        this.viewPagerAdapter = viewPagerAdapter;
        this.courseViewPage.setAdapter(viewPagerAdapter);
        showFragment(0);
        this.courseViewPage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() { // from class: com.psychiatrygarden.fragmenthome.QuestionHomeJumpFragment.1
            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrollStateChanged(int state) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageSelected(int position) throws Resources.NotFoundException {
                if (position == 0) {
                    QuestionHomeJumpFragment.this.imgSearchAllCourse.setVisibility(0);
                    QuestionHomeJumpFragment.this.selectAllCourse(true);
                } else if (QuestionHomeJumpFragment.this.isLogin()) {
                    QuestionHomeJumpFragment.this.imgSearchAllCourse.setVisibility(8);
                    QuestionHomeJumpFragment.this.selectAllCourse(false);
                } else {
                    QuestionHomeJumpFragment.this.imgSearchAllCourse.setVisibility(0);
                    QuestionHomeJumpFragment.this.selectAllCourse(true);
                    QuestionHomeJumpFragment.this.courseViewPage.setCurrentItem(0);
                }
            }
        });
        this.courseViewPage.setOffscreenPageLimit(1);
    }

    public static QuestionHomeJumpFragment newInstance() {
        Bundle bundle = new Bundle();
        QuestionHomeJumpFragment questionHomeJumpFragment = new QuestionHomeJumpFragment();
        questionHomeJumpFragment.setArguments(bundle);
        return questionHomeJumpFragment;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void selectAllCourse(boolean allCourse) {
        if (allCourse) {
            this.groupNameAllCourse.setSelected(true);
            this.groupNameAllCourse.getPaint().setFakeBoldText(true);
            this.groupNameAllCourse.setTypeface(Typeface.DEFAULT_BOLD);
            this.groupNameAllCourseImg.setVisibility(0);
            this.groupNameAllCourse.setTextSize(0, getResources().getDimensionPixelSize(R.dimen.sp_16));
            this.groupNameLearnCenter.setSelected(false);
            this.groupNameLearnCenter.getPaint().setFakeBoldText(false);
            this.groupNameLearnCenter.setTypeface(Typeface.DEFAULT);
            this.groupNameLearnCenter.setTextSize(0, getResources().getDimensionPixelSize(R.dimen.sp_14));
            this.groupNameLearnCenterImg.setVisibility(4);
            return;
        }
        this.groupNameAllCourse.setSelected(false);
        this.groupNameAllCourse.getPaint().setFakeBoldText(false);
        this.groupNameAllCourse.setTypeface(Typeface.DEFAULT);
        this.groupNameAllCourse.setTextSize(0, getResources().getDimensionPixelSize(R.dimen.sp_14));
        this.groupNameAllCourseImg.setVisibility(4);
        this.groupNameLearnCenter.setSelected(true);
        this.groupNameLearnCenter.getPaint().setFakeBoldText(true);
        this.groupNameLearnCenter.setTypeface(Typeface.DEFAULT_BOLD);
        this.groupNameLearnCenter.setTextSize(0, getResources().getDimensionPixelSize(R.dimen.sp_16));
        this.groupNameLearnCenterImg.setVisibility(0);
    }

    private void showFragment(int position) throws Resources.NotFoundException {
        this.courseViewPage.setCurrentItem(position);
        if (position == 0) {
            selectAllCourse(true);
        } else if (position == 1) {
            selectAllCourse(false);
        }
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public int getLayoutId() {
        return R.layout.fragment_home_jump;
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void initViews(ViewHolder holder, View root) throws Resources.NotFoundException {
        this.courseViewPage = (ViewPager) holder.get(R.id.courseViewPage);
        this.groupNameAllCourse = (TextView) holder.get(R.id.groupNameAllCourse);
        this.groupNameLearnCenter = (TextView) holder.get(R.id.groupNameLearnCenter);
        this.groupNameAllCourseImg = (ImageView) holder.get(R.id.groupNameAllCourseImg);
        this.groupNameLearnCenterImg = (ImageView) holder.get(R.id.groupNameLearnCenterImg);
        this.imgSearchAllCourse = (ImageView) holder.get(R.id.imgSearchAllCourse);
        RelativeLayout relativeLayout = (RelativeLayout) holder.get(R.id.layoutGroupAllCourse);
        RelativeLayout relativeLayout2 = (RelativeLayout) holder.get(R.id.layoutGroupLearnCenter);
        this.imgLiveCalendarAnim = (ImageView) holder.get(R.id.imgLiveCalendarAnim);
        if (SkinManager.getCurrentSkinType(getActivity()) == 0) {
            this.imgLiveCalendarAnim.setImageResource(R.drawable.live_calendar_live_anim2_day);
        } else {
            this.imgLiveCalendarAnim.setImageResource(R.drawable.live_calendar_live_anim2_night);
        }
        ((Animatable) this.imgLiveCalendarAnim.getDrawable()).start();
        this.imgLiveCalendarAnim.setOnClickListener(this);
        holder.get(R.id.imgLiveCalendarIcon).setOnClickListener(this);
        relativeLayout.setOnClickListener(this);
        relativeLayout2.setOnClickListener(this);
        this.imgSearchAllCourse.setOnClickListener(this);
        selectAllCourse(true);
        initViewPage();
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v2) throws Resources.NotFoundException {
        switch (v2.getId()) {
            case R.id.imgLiveCalendarAnim /* 2131363646 */:
            case R.id.imgLiveCalendarIcon /* 2131363647 */:
                if (isLogin()) {
                    startActivity(LiveCalendarActivity.newIntent(getActivity()));
                    break;
                }
                break;
            case R.id.imgSearchAllCourse /* 2131363661 */:
                startActivity(new Intent(getActivity(), (Class<?>) CourseSearchActivity.class));
                break;
            case R.id.layoutGroupAllCourse /* 2131364398 */:
                selectAllCourse(true);
                this.imgSearchAllCourse.setVisibility(0);
                showFragment(0);
                break;
            case R.id.layoutGroupLearnCenter /* 2131364400 */:
                if (isLogin()) {
                    selectAllCourse(false);
                    this.imgSearchAllCourse.setVisibility(8);
                    showFragment(1);
                    break;
                }
                break;
        }
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportFragment, androidx.fragment.app.Fragment
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void onEventMainThread(String mEveStr) throws Resources.NotFoundException {
        if ("JumpZHibo".equals(mEveStr)) {
            showFragment(0);
        }
        if ("jumpToCourseList".equals(mEveStr)) {
            showFragment(1);
        }
        if (AllCourseFragment.LIVING_FLAG.equals(mEveStr)) {
            this.imgLiveCalendarAnim.setVisibility(0);
        }
        if (AllCourseFragment.LIVING_FLAG_NO_HAVE.equals(mEveStr)) {
            this.imgLiveCalendarAnim.setVisibility(8);
        }
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportFragment, androidx.fragment.app.Fragment
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        HomePageNewActivity homePageNewActivity = (HomePageNewActivity) getActivity();
        if (hidden || homePageNewActivity == null) {
            return;
        }
        homePageNewActivity.setNewStyleStatusBarColor3();
    }

    public static class ViewPagerAdapter extends FragmentPagerAdapter {
        private List<Fragment> fragmentList;

        public ViewPagerAdapter(@NonNull FragmentManager fm, List<Fragment> listFragment) {
            super(fm);
            this.fragmentList = listFragment;
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        /* renamed from: getCount */
        public int getSize() {
            return this.fragmentList.size();
        }

        @Override // androidx.fragment.app.FragmentPagerAdapter
        @NonNull
        public Fragment getItem(int position) {
            return this.fragmentList.get(position);
        }

        public ViewPagerAdapter(FragmentManager fm, int behavior) {
            super(fm, behavior);
        }
    }
}
