package com.psychiatrygarden.activity.online.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import cn.hutool.core.lang.RegexPool;
import cn.webdemo.com.supporfragment.tablayout.MagicIndicator;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.CommonNavigator;
import com.aliyun.vod.common.utils.UriUtil;
import com.psychiatrygarden.activity.online.AnswerQuestionActivity;
import com.psychiatrygarden.activity.online.bean.QuestionDetailBean;
import com.psychiatrygarden.adapter.ShareStemQuestionAdapter;
import com.psychiatrygarden.baseview.BaseFragment;
import com.psychiatrygarden.baseview.ViewHolder;
import com.psychiatrygarden.bean.QuestionIndexBean;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.event.PlayQuestionMediaEvent;
import com.psychiatrygarden.event.QuestionBack2TopEvent;
import com.psychiatrygarden.event.RefreshIdNotifyEvent;
import com.psychiatrygarden.event.ScrollChangeEvent;
import com.psychiatrygarden.event.ScrollDirectionEvent;
import com.psychiatrygarden.event.SwitchStemQuestionEvent;
import com.psychiatrygarden.event.UpdateQuestionIdEvent;
import com.psychiatrygarden.event.UpdateScrollEvent;
import com.psychiatrygarden.utils.CharacterParser;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.StatusBarUtil;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.greenrobot.eventbus.Subscribe;

/* loaded from: classes5.dex */
public class ShareStemQuestionFragment extends BaseFragment {
    private String category;
    private String chapterTitle;
    private int dragViewHeight;
    private SparseArray<Boolean> expandStatusArray;
    private boolean fromQuestionCombine;
    private boolean hasInitPage;
    private int lineHeight;
    private LinearLayout llStemContent;
    private LinearLayout mChildQuestion;
    private int mCurrentPosition;
    private List<Fragment> mFragments;
    private MagicIndicator mMagicIndicator;
    private QuestionDetailBean mQuestionDetailBean;
    private TextView mTitle;
    private ViewPager mViewpager;
    private int maxTopHeight;
    private NestedScrollView nsl;
    private TextView pageNumTv;
    private String publicNumber;
    private String questionId;
    private int questionPosition;
    private RelativeLayout rlTopTitle;
    private boolean showAnim;
    private boolean singleLineTitle;
    private float startRawY;
    private int stemIndexAreaHeight;
    private boolean study_plan;
    private int titleContentHeight;
    private int topContentHeight;
    private String total;
    private String type;
    private TextView typeStr;
    private boolean upScroll;
    private boolean video_summary;
    private float offsetDiff = 0.0f;
    private int pageIndex = -1;
    private long lastUpdate = 0;

    /* JADX INFO: Access modifiers changed from: private */
    public void execTopMarinAnimation(final boolean showTitle, int... values) {
        if (this.mChildQuestion.getTop() == values[values.length - 1] || getActivity() == null) {
            return;
        }
        ValueAnimator valueAnimatorOfInt = ValueAnimator.ofInt(values);
        boolean zIsShowChapterTitle = ((AnswerQuestionActivity) getActivity()).isShowChapterTitle();
        if (showTitle) {
            if (zIsShowChapterTitle) {
                this.rlTopTitle.setVisibility(0);
                ((AnswerQuestionActivity) getActivity()).showTitleView();
            }
        } else if (zIsShowChapterTitle) {
            this.rlTopTitle.setVisibility(8);
            ((AnswerQuestionActivity) getActivity()).hideTitleView(this.questionId);
        }
        valueAnimatorOfInt.setDuration(this.showAnim ? 300L : 10L);
        if (!this.showAnim) {
            this.showAnim = true;
        }
        valueAnimatorOfInt.setInterpolator(new LinearInterpolator());
        if (!this.singleLineTitle) {
            valueAnimatorOfInt.addListener(new AnimatorListenerAdapter() { // from class: com.psychiatrygarden.activity.online.fragment.ShareStemQuestionFragment.2
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animation) {
                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) ShareStemQuestionFragment.this.nsl.getLayoutParams();
                    layoutParams.height = showTitle ? ShareStemQuestionFragment.this.mChildQuestion.getTop() + ShareStemQuestionFragment.this.lineHeight : 0;
                    ShareStemQuestionFragment.this.nsl.setLayoutParams(layoutParams);
                    if (ShareStemQuestionFragment.this.mTitle.getPaddingBottom() != ShareStemQuestionFragment.this.lineHeight + CommonUtil.dip2px(ShareStemQuestionFragment.this.requireContext(), 32.0f)) {
                        ShareStemQuestionFragment.this.mTitle.setPadding(0, 0, 0, ShareStemQuestionFragment.this.lineHeight + CommonUtil.dip2px(ShareStemQuestionFragment.this.requireContext(), 32.0f));
                    }
                }
            });
        }
        valueAnimatorOfInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.psychiatrygarden.activity.online.fragment.v
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                this.f13365c.lambda$execTopMarinAnimation$1(valueAnimator);
            }
        });
        valueAnimatorOfInt.start();
    }

    public static ShareStemQuestionFragment getInstance(Bundle args) {
        ShareStemQuestionFragment shareStemQuestionFragment = new ShareStemQuestionFragment();
        shareStemQuestionFragment.setArguments(args);
        return shareStemQuestionFragment;
    }

    private List<QuestionDetailBean> getStemQuestionList() {
        return getActivity() == null ? new ArrayList() : ((AnswerQuestionActivity) getActivity()).getShareStemQuestionList(this.publicNumber);
    }

    private void initListener() {
        this.mViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() { // from class: com.psychiatrygarden.activity.online.fragment.ShareStemQuestionFragment.1
            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrollStateChanged(int state) {
                ShareStemQuestionFragment.this.mMagicIndicator.onPageScrollStateChanged(state);
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                ShareStemQuestionFragment.this.mMagicIndicator.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageSelected(int position) {
                int i2;
                ShareStemQuestionFragment.this.mMagicIndicator.onPageSelected(position);
                if (ShareStemQuestionFragment.this.getActivity() == null) {
                    return;
                }
                List<QuestionDetailBean> shareStemQuestionList = ((AnswerQuestionActivity) ShareStemQuestionFragment.this.getActivity()).getShareStemQuestionList(ShareStemQuestionFragment.this.publicNumber);
                ((AnswerQuestionActivity) ShareStemQuestionFragment.this.getActivity()).showHideBack2TopIcon(false);
                ((AnswerQuestionActivity) ShareStemQuestionFragment.this.getActivity()).onStemQuestionPageChange(shareStemQuestionList.get(position).getId());
                if (position > 0 && position - 1 < shareStemQuestionList.size()) {
                    QuestionDetailBean questionDetailBean = shareStemQuestionList.get(i2);
                    if (questionDetailBean.getStem_audio_list() != null && questionDetailBean.getStem_audio_list().size() > 0) {
                        Fragment fragment = (Fragment) ShareStemQuestionFragment.this.mFragments.get(i2);
                        if (fragment instanceof SubChoiceQuestionFragment) {
                            ((SubChoiceQuestionFragment) fragment).pausePlayVideoAudio();
                        }
                    }
                }
                ShareStemQuestionFragment.this.updatePageIndex(position);
                if (position < shareStemQuestionList.size()) {
                    ShareStemQuestionFragment.this.questionId = shareStemQuestionList.get(position).getId();
                }
                Boolean bool = (Boolean) ShareStemQuestionFragment.this.expandStatusArray.get(position);
                if (bool == null) {
                    bool = Boolean.TRUE;
                    ShareStemQuestionFragment.this.expandStatusArray.put(position, bool);
                }
                if (TextUtils.isEmpty(shareStemQuestionList.get(position).getUser_answer())) {
                    ShareStemQuestionFragment.this.getViewHolder().get(R.id.lineviewtype).setVisibility(0);
                    if (position > 0) {
                        Boolean bool2 = (Boolean) ShareStemQuestionFragment.this.expandStatusArray.get(Math.max(position, ShareStemQuestionFragment.this.pageIndex) - 1);
                        if (bool2 == null) {
                            bool2 = Boolean.FALSE;
                        }
                        if (!bool2.booleanValue()) {
                            ShareStemQuestionFragment shareStemQuestionFragment = ShareStemQuestionFragment.this;
                            int[] iArr = new int[1];
                            iArr[0] = shareStemQuestionFragment.singleLineTitle ? ShareStemQuestionFragment.this.topContentHeight : ShareStemQuestionFragment.this.titleContentHeight;
                            shareStemQuestionFragment.execTopMarinAnimation(true, iArr);
                            ShareStemQuestionFragment.this.expandStatusArray.put(position, Boolean.TRUE);
                        }
                    }
                } else {
                    Boolean bool3 = (Boolean) ShareStemQuestionFragment.this.expandStatusArray.get(Math.max(position, ShareStemQuestionFragment.this.pageIndex) - 1);
                    if (bool3 == null) {
                        bool3 = Boolean.TRUE;
                    }
                    if (bool3.booleanValue() == bool.booleanValue()) {
                        return;
                    }
                    int i3 = ShareStemQuestionFragment.this.singleLineTitle ? ShareStemQuestionFragment.this.topContentHeight : ShareStemQuestionFragment.this.titleContentHeight + ShareStemQuestionFragment.this.lineHeight;
                    ShareStemQuestionFragment.this.getViewHolder().get(R.id.lineviewtype).setVisibility(!bool.booleanValue() ? 4 : 0);
                    ShareStemQuestionFragment shareStemQuestionFragment2 = ShareStemQuestionFragment.this;
                    boolean zBooleanValue = bool.booleanValue();
                    int[] iArr2 = new int[2];
                    iArr2[0] = !bool.booleanValue() ? i3 : (-ShareStemQuestionFragment.this.dragViewHeight) - ShareStemQuestionFragment.this.stemIndexAreaHeight;
                    if (!bool.booleanValue()) {
                        i3 = (-ShareStemQuestionFragment.this.dragViewHeight) - ShareStemQuestionFragment.this.stemIndexAreaHeight;
                    }
                    iArr2[1] = i3;
                    shareStemQuestionFragment2.execTopMarinAnimation(zBooleanValue, iArr2);
                    ShareStemQuestionFragment.this.expandStatusArray.put(position, bool);
                }
                if (ShareStemQuestionFragment.this.pageIndex != position) {
                    ShareStemQuestionFragment.this.pageIndex = position;
                }
                QuestionDetailBean questionDetailBean2 = shareStemQuestionList.get(position);
                String str = questionDetailBean2.getChapter_parent_id() + "-" + questionDetailBean2.getChapter_id() + "-" + ShareStemQuestionFragment.this.questionId;
                if (ShareStemQuestionFragment.this.type.equals("all") && !ShareStemQuestionFragment.this.fromQuestionCombine) {
                    SharePreferencesUtils.writeStrConfig(CommonParameter.LAST_DO_QUESTION + SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, ((BaseFragment) ShareStemQuestionFragment.this).mContext) + ShareStemQuestionFragment.this.category, str, ShareStemQuestionFragment.this.requireContext());
                }
                Fragment fragment2 = (Fragment) ShareStemQuestionFragment.this.mFragments.get(position);
                if (fragment2 instanceof SubChoiceQuestionFragment) {
                    ((SubChoiceQuestionFragment) fragment2).onVisible();
                } else if (fragment2 instanceof SubSubjectiveQuestionFragment) {
                    ((SubSubjectiveQuestionFragment) fragment2).onVisible();
                }
            }
        });
        getViewHolder().get(R.id.rl_drag_area).setOnTouchListener(new View.OnTouchListener() { // from class: com.psychiatrygarden.activity.online.fragment.x
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                return this.f13385c.lambda$initListener$0(view, motionEvent);
            }
        });
    }

    private void initQuestionTab() throws Resources.NotFoundException {
        if (getActivity() == null) {
            return;
        }
        List<QuestionDetailBean> stemQuestionList = getStemQuestionList();
        this.mFragments = new ArrayList();
        ArrayList arrayList = new ArrayList();
        Bundle arguments = getArguments();
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        while (true) {
            if (i3 >= stemQuestionList.size()) {
                break;
            }
            Bundle bundle = new Bundle();
            bundle.putAll(arguments);
            bundle.putBoolean("shareStem", true);
            StringBuilder sb = new StringBuilder();
            sb.append("第");
            int i5 = i3 + 1;
            sb.append(i5);
            sb.append("问");
            arrayList.add(new QuestionIndexBean(sb.toString(), String.valueOf(i5), i3 == 0));
            bundle.putInt("position", stemQuestionList.get(i3).getActualStemIndex());
            this.mFragments.add(SubChoiceQuestionFragment.getInstance(bundle));
            if (stemQuestionList.get(i3).getActualStemIndex() == this.questionPosition) {
                i4 = i3;
            }
            i3 = i5;
        }
        if (arrayList.size() >= 5) {
            getViewHolder().get(R.id.iv_shadow_left).setVisibility(0);
            getViewHolder().get(R.id.iv_shadow_right).setVisibility(0);
        }
        CommonNavigator commonNavigator = new CommonNavigator(this.mContext);
        commonNavigator.setSkimOver(false);
        final LinearLayout linearLayout = (LinearLayout) getViewHolder().get(R.id.ll_top_content);
        this.mTitle = (TextView) getViewHolder().get(R.id.titletv);
        commonNavigator.setAdapter(new ShareStemQuestionAdapter(arrayList, this.mViewpager));
        this.mMagicIndicator.setNavigator(commonNavigator);
        this.mTitle.setText(this.mQuestionDetailBean.getPublic_title());
        this.mViewpager.setOffscreenPageLimit(1);
        if (this.questionPosition < stemQuestionList.size()) {
            this.questionId = stemQuestionList.get(this.questionPosition).getId();
        }
        this.mMagicIndicator.onPageSelected(i4);
        updatePageIndex(i4);
        this.pageIndex = i4;
        this.expandStatusArray.put(i4, Boolean.TRUE);
        this.mChildQuestion = (LinearLayout) getViewHolder().get(R.id.ll_child_question);
        linearLayout.post(new Runnable() { // from class: com.psychiatrygarden.activity.online.fragment.y
            @Override // java.lang.Runnable
            public final void run() {
                this.f13392c.lambda$initQuestionTab$2(linearLayout);
            }
        });
        getViewHolder().get(R.id.rl_drag_area).post(new Runnable() { // from class: com.psychiatrygarden.activity.online.fragment.z
            @Override // java.lang.Runnable
            public final void run() {
                this.f13402c.lambda$initQuestionTab$3();
            }
        });
        getViewHolder().get(R.id.rl_stem_area).post(new Runnable() { // from class: com.psychiatrygarden.activity.online.fragment.a0
            @Override // java.lang.Runnable
            public final void run() {
                this.f13173c.lambda$initQuestionTab$4();
            }
        });
        this.mViewpager.setSaveEnabled(false);
        this.mViewpager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager(), i2) { // from class: com.psychiatrygarden.activity.online.fragment.ShareStemQuestionFragment.3
            @Override // androidx.viewpager.widget.PagerAdapter
            /* renamed from: getCount */
            public int getSize() {
                return ShareStemQuestionFragment.this.mFragments.size();
            }

            @Override // androidx.fragment.app.FragmentPagerAdapter
            @NonNull
            public Fragment getItem(int position) {
                return (Fragment) ShareStemQuestionFragment.this.mFragments.get(position);
            }
        });
        this.mViewpager.setCurrentItem(i4);
        Fragment fragment = this.mFragments.get(i4);
        if (fragment instanceof SubChoiceQuestionFragment) {
            ((SubChoiceQuestionFragment) fragment).onVisible();
        } else if (fragment instanceof SubSubjectiveQuestionFragment) {
            ((SubSubjectiveQuestionFragment) fragment).onVisible();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$execTopMarinAnimation$1(ValueAnimator valueAnimator) {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.mChildQuestion.getLayoutParams();
        layoutParams.topMargin = ((Integer) valueAnimator.getAnimatedValue()).intValue();
        this.mChildQuestion.setLayoutParams(layoutParams);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$initListener$0(View view, MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (action == 0) {
            this.startRawY = motionEvent.getRawY();
            return true;
        }
        if (action != 2 || this.singleLineTitle) {
            return false;
        }
        this.offsetDiff = motionEvent.getRawY() - this.startRawY;
        if (motionEvent.getRawY() - this.startRawY <= 0.0f) {
            if (motionEvent.getRawY() - this.startRawY < 0.0f) {
                if (this.mChildQuestion.getTop() > this.titleContentHeight + this.lineHeight) {
                    FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.mChildQuestion.getLayoutParams();
                    layoutParams.topMargin = (int) (layoutParams.topMargin - Math.abs(this.offsetDiff));
                    this.mChildQuestion.setLayoutParams(layoutParams);
                    LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) this.nsl.getLayoutParams();
                    layoutParams2.height = layoutParams.topMargin + this.lineHeight;
                    this.nsl.setLayoutParams(layoutParams2);
                    if (this.mTitle.getPaddingBottom() != this.lineHeight + CommonUtil.dip2px(requireContext(), 32.0f)) {
                        this.mTitle.setPadding(0, 0, 0, this.lineHeight + CommonUtil.dip2px(requireContext(), 32.0f));
                    }
                } else {
                    this.nsl.setScrollbarFadingEnabled(true);
                }
                this.startRawY = motionEvent.getRawY();
                return true;
            }
            return false;
        }
        if (this.mChildQuestion.getTop() > this.maxTopHeight) {
            return false;
        }
        if (this.mChildQuestion.getTop() >= this.topContentHeight) {
            this.nsl.setScrollbarFadingEnabled(true);
            return false;
        }
        FrameLayout.LayoutParams layoutParams3 = (FrameLayout.LayoutParams) this.mChildQuestion.getLayoutParams();
        layoutParams3.topMargin = (int) (layoutParams3.topMargin + this.offsetDiff);
        this.mChildQuestion.setLayoutParams(layoutParams3);
        LinearLayout.LayoutParams layoutParams4 = (LinearLayout.LayoutParams) this.nsl.getLayoutParams();
        layoutParams4.height = layoutParams3.topMargin + this.lineHeight;
        this.nsl.setLayoutParams(layoutParams4);
        if (this.mTitle.getPaddingBottom() != this.lineHeight + CommonUtil.dip2px(requireContext(), 32.0f)) {
            this.mTitle.setPadding(0, 0, 0, this.lineHeight + CommonUtil.dip2px(requireContext(), 32.0f));
        }
        this.startRawY = motionEvent.getRawY();
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initQuestionTab$2(LinearLayout linearLayout) {
        this.topContentHeight = linearLayout.getMeasuredHeight();
        this.lineHeight = this.mTitle.getLineHeight();
        boolean z2 = this.rlTopTitle.getVisibility() == 0;
        this.singleLineTitle = this.mTitle.getLineCount() <= 3;
        if (this.mTitle.getLineCount() <= 3) {
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.mChildQuestion.getLayoutParams();
            layoutParams.topMargin = this.topContentHeight + CommonUtil.dip2px(requireContext(), 10.0f);
            this.mChildQuestion.setLayoutParams(layoutParams);
            return;
        }
        int measuredHeight = getViewHolder().get(R.id.lineviewtype).getMeasuredHeight() + CommonUtil.dip2px(requireContext(), 28.0f);
        int i2 = this.lineHeight;
        int measuredHeight2 = measuredHeight + (i2 * 3) + (i2 / 2);
        if (z2) {
            measuredHeight2 += this.rlTopTitle.getMeasuredHeight();
        }
        LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) this.nsl.getLayoutParams();
        int i3 = this.lineHeight;
        layoutParams2.height = measuredHeight2 + i3;
        this.titleContentHeight = measuredHeight2 - i3;
        this.nsl.setLayoutParams(layoutParams2);
        FrameLayout.LayoutParams layoutParams3 = (FrameLayout.LayoutParams) this.mChildQuestion.getLayoutParams();
        layoutParams3.topMargin = measuredHeight2;
        this.mChildQuestion.setLayoutParams(layoutParams3);
        this.mTitle.setPadding(0, 0, 0, this.lineHeight + CommonUtil.dip2px(requireContext(), 32.0f));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initQuestionTab$3() {
        this.dragViewHeight = getViewHolder().get(R.id.rl_drag_area).getMeasuredHeight();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initQuestionTab$4() {
        this.stemIndexAreaHeight = getViewHolder().get(R.id.rl_stem_area).getMeasuredHeight();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setUserVisibleHint$5(boolean z2) {
        int currentItem = this.mViewpager.getCurrentItem();
        List<QuestionDetailBean> stemQuestionList = getStemQuestionList();
        if (stemQuestionList.size() > 0) {
            QuestionDetailBean questionDetailBean = stemQuestionList.get(currentItem);
            if (questionDetailBean.getStem_audio_list() != null && questionDetailBean.getStem_audio_list().size() > 0) {
                EventBus.getDefault().post(new PlayQuestionMediaEvent(z2, questionDetailBean.getId()));
            }
        }
        if (z2) {
            EventBus.getDefault().post("showStemPage");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updatePageIndex(int currentIndex) {
        String sort;
        List<QuestionDetailBean> stemQuestionList = getStemQuestionList();
        if (currentIndex < stemQuestionList.size()) {
            QuestionDetailBean questionDetailBean = stemQuestionList.get(currentIndex);
            for (QuestionDetailBean questionDetailBean2 : stemQuestionList) {
                if (TextUtils.equals(questionDetailBean2.getId(), questionDetailBean.getId())) {
                    sort = questionDetailBean2.getSort();
                    break;
                }
            }
            sort = null;
        } else {
            sort = null;
        }
        if (!this.video_summary) {
            if (sort == null || !sort.matches(RegexPool.NUMBERS)) {
                String str = SkinManager.getCurrentSkinType(this.mContext) != 1 ? "#000000" : "#64729F";
                StringBuilder sb = new StringBuilder();
                int i2 = currentIndex + 1;
                sb.append(i2);
                sb.append(" /");
                sb.append(this.total);
                this.pageNumTv.setText(CharacterParser.getSpannableColorSize(sb.toString(), 0, String.valueOf(i2).length(), str));
            } else {
                this.pageNumTv.setText(CharacterParser.getSpannableColorSize(sort + " /" + this.total, 0, sort.length(), SkinManager.getCurrentSkinType(this.mContext) != 1 ? "#000000" : "#64729F"));
            }
        }
        this.typeStr.setText(stemQuestionList.get(currentIndex).getType_str());
    }

    public int getCurrentPosition() {
        return this.mViewpager.getCurrentItem();
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public int getLayoutId() {
        return R.layout.fragment_share_stem_question;
    }

    public void handleScrollChange(int verticalDistance) {
        if (getActivity() == null) {
            return;
        }
        if (verticalDistance > 30) {
            if (this.upScroll || verticalDistance < 200 || this.lastUpdate + 400 > System.currentTimeMillis() || this.mChildQuestion.getTop() == (-this.dragViewHeight)) {
                return;
            }
            this.lastUpdate = System.currentTimeMillis();
            getViewHolder().get(R.id.lineviewtype).setVisibility(4);
            execTopMarinAnimation(false, this.mChildQuestion.getTop(), 0, (-this.dragViewHeight) - this.stemIndexAreaHeight);
            this.expandStatusArray.put(this.mViewpager.getCurrentItem(), Boolean.FALSE);
            return;
        }
        if (!this.upScroll || this.lastUpdate + 400 > System.currentTimeMillis()) {
            return;
        }
        this.lastUpdate = System.currentTimeMillis();
        if (this.mChildQuestion.getTop() < (this.singleLineTitle ? this.topContentHeight : this.titleContentHeight)) {
            getViewHolder().get(R.id.lineviewtype).setVisibility(0);
            int[] iArr = new int[4];
            iArr[0] = (-this.dragViewHeight) - this.stemIndexAreaHeight;
            iArr[1] = 0;
            boolean z2 = this.singleLineTitle;
            iArr[2] = (!z2 ? this.titleContentHeight : this.topContentHeight) / 2;
            iArr[3] = !z2 ? this.titleContentHeight : this.topContentHeight + CommonUtil.dip2px(requireContext(), 10.0f);
            execTopMarinAnimation(true, iArr);
            this.expandStatusArray.put(this.mViewpager.getCurrentItem(), Boolean.TRUE);
        }
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void initViews(ViewHolder holder, View root) throws Resources.NotFoundException {
        if (getArguments() != null) {
            this.video_summary = getArguments().getBoolean("video_summary", false);
            this.study_plan = getArguments().getBoolean("study_plan", false);
            this.publicNumber = getArguments().getString("public_number");
            this.chapterTitle = getArguments().getString("chapter_title");
            this.total = getArguments().getString("total");
            this.mCurrentPosition = getArguments().getInt("position");
            this.questionPosition = getArguments().getInt("question_position");
            this.type = getArguments().getString("type", "");
            this.category = getArguments().getString(UriUtil.QUERY_CATEGORY, "");
            this.fromQuestionCombine = getArguments().getBoolean("fromQuestionCombine", false);
        }
        if (getActivity() == null) {
            return;
        }
        this.llStemContent = (LinearLayout) holder.get(R.id.ll_stem_content);
        this.mMagicIndicator = (MagicIndicator) holder.get(R.id.magic_indicator);
        this.pageNumTv = (TextView) holder.get(R.id.pagenumtv);
        this.typeStr = (TextView) holder.get(R.id.typeStr);
        if (this.video_summary) {
            this.pageNumTv.setVisibility(8);
        }
        this.nsl = (NestedScrollView) holder.get(R.id.nsl);
        this.rlTopTitle = (RelativeLayout) holder.get(R.id.rl_top_title);
        this.mViewpager = (ViewPager) holder.get(R.id.viewpager);
        QuestionDetailBean questionDetailBean = ((AnswerQuestionActivity) getActivity()).getQuestionDetailBean(this.mCurrentPosition);
        this.mQuestionDetailBean = questionDetailBean;
        if (this.publicNumber == null || questionDetailBean == null) {
            return;
        }
        TextView textView = (TextView) holder.get(R.id.questiondetails_tv_title);
        if (TextUtils.isEmpty(this.chapterTitle)) {
            holder.get(R.id.rl_top_title).setVisibility(8);
        } else {
            holder.get(R.id.rl_top_title).setVisibility(0);
            textView.setText(this.chapterTitle);
        }
        this.expandStatusArray = new SparseArray<>();
        int statusBarHeight = this.maxTopHeight + StatusBarUtil.getStatusBarHeight(requireContext());
        this.maxTopHeight = statusBarHeight;
        this.maxTopHeight = statusBarHeight + CommonUtil.dip2px(requireContext(), 464.0f);
        initQuestionTab();
        initListener();
        this.hasInitPage = true;
    }

    @Subscribe(sticky = true)
    public void onEventMainThread(RefreshIdNotifyEvent event) {
        List<QuestionDetailBean> stemQuestionList = getStemQuestionList();
        if (stemQuestionList == null || stemQuestionList.size() <= 0) {
            return;
        }
        EventBus.getDefault().post(new UpdateQuestionIdEvent(stemQuestionList.get(this.mViewpager.getCurrentItem()).getId()));
    }

    public void scrollDirectionChange(boolean upScroll) {
        this.upScroll = upScroll;
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportFragment, androidx.fragment.app.Fragment
    public void setUserVisibleHint(final boolean isVisibleToUser) {
        LinearLayout linearLayout;
        if (!this.hasInitPage || (linearLayout = this.mChildQuestion) == null) {
            return;
        }
        linearLayout.postDelayed(new Runnable() { // from class: com.psychiatrygarden.activity.online.fragment.w
            @Override // java.lang.Runnable
            public final void run() {
                this.f13375c.lambda$setUserVisibleHint$5(isVisibleToUser);
            }
        }, 200L);
        super.setUserVisibleHint(isVisibleToUser);
    }

    public void scrollDirectionChange(boolean upScroll, boolean showAnim) {
        this.upScroll = upScroll;
        this.showAnim = showAnim;
    }

    @Subscribe
    public void onEventMainThread(ScrollChangeEvent event) {
        Iterator<QuestionDetailBean> it = getStemQuestionList().iterator();
        while (it.hasNext()) {
            if (TextUtils.equals(it.next().getId(), event.getQuestionId())) {
                scrollDirectionChange(event.isUpScroll());
            }
        }
    }

    @Subscribe
    public void onEventMainThread(UpdateScrollEvent event) {
        Iterator<QuestionDetailBean> it = getStemQuestionList().iterator();
        while (it.hasNext()) {
            if (TextUtils.equals(it.next().getId(), event.getQuestionId())) {
                handleScrollChange(event.getVerticalDistance());
            }
        }
    }

    @Subscribe
    public void onEventMainThread(ScrollDirectionEvent event) {
        Iterator<QuestionDetailBean> it = getStemQuestionList().iterator();
        while (it.hasNext()) {
            if (TextUtils.equals(it.next().getId(), event.getQuestionId())) {
                scrollDirectionChange(event.isUpScroll(), event.isShowAnim());
            }
        }
    }

    @Subscribe
    public void onEventMainThread(SwitchStemQuestionEvent event) throws Resources.NotFoundException {
        if (!event.isNextPage() || this.mViewpager.getAdapter() == null || getActivity() == null || this.mViewpager.getCurrentItem() == this.mViewpager.getAdapter().getSize() - 1 || !TextUtils.equals(event.getPublicNumber(), this.publicNumber)) {
            return;
        }
        ((AnswerQuestionActivity) getActivity()).showHideBack2TopIcon(false);
        ViewPager viewPager = this.mViewpager;
        viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
    }

    @Subscribe
    public void onEventMainThread(QuestionBack2TopEvent event) {
        this.rlTopTitle.setVisibility(0);
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void onEventMainThread(String str) {
        super.onEventMainThread(str);
    }
}
