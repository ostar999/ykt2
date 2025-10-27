package com.psychiatrygarden.activity;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import cn.webdemo.com.supporfragment.tablayout.MagicIndicator;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.CommonNavigator;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.IPagerIndicator;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.IPagerTitleView;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.titles.CommonPagerTitleView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.psychiatrygarden.bean.QuestionIndexBean;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.ToastUtil;
import com.psychiatrygarden.widget.AutoScrollViewPager;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes5.dex */
public class TestActivity extends BaseActivity {
    private LinearLayout llStemContent;
    private GestureDetector mGestureDetector;
    private NestedScrollView nsl;
    private float offsetDiff = 0.0f;
    private RelativeLayout rlTopTitle;
    float startRawY;
    private int stemContentHeight;
    private int titleContentHeight;

    /* renamed from: com.psychiatrygarden.activity.TestActivity$3, reason: invalid class name */
    public class AnonymousClass3 extends CommonNavigatorAdapter {
        final /* synthetic */ List val$data;
        final /* synthetic */ AutoScrollViewPager val$viewpager;

        public AnonymousClass3(final List val$data, final AutoScrollViewPager val$viewpager) {
            this.val$data = val$data;
            this.val$viewpager = val$viewpager;
        }

        @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter
        public int getCount() {
            return this.val$data.size();
        }

        @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter
        public IPagerIndicator getIndicator(Context context) {
            return null;
        }

        @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter
        public IPagerTitleView getTitleView(Context context, final int index) {
            CommonPagerTitleView commonPagerTitleView = new CommonPagerTitleView(context);
            commonPagerTitleView.setContentView(R.layout.item_share_stem_question_column);
            final TextView textView = (TextView) commonPagerTitleView.findViewById(R.id.tv_column_name);
            textView.setText(((QuestionIndexBean) this.val$data.get(index)).getTitle());
            final AutoScrollViewPager autoScrollViewPager = this.val$viewpager;
            commonPagerTitleView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.bp
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) throws Resources.NotFoundException {
                    autoScrollViewPager.setCurrentItem(index);
                }
            });
            commonPagerTitleView.setOnPagerTitleChangeListener(new CommonPagerTitleView.OnPagerTitleChangeListener() { // from class: com.psychiatrygarden.activity.TestActivity.3.1
                @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.titles.CommonPagerTitleView.OnPagerTitleChangeListener
                public void onDeselected(int index2, int totalCount) {
                    textView.setTextColor(Color.parseColor(SkinManager.getCurrentSkinType(TestActivity.this.mContext) == 1 ? "#606A8A" : "#555555"));
                    textView.setTextSize(14.0f);
                }

                @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.titles.CommonPagerTitleView.OnPagerTitleChangeListener
                public void onEnter(int index2, int totalCount, float enterPercent, boolean leftToRight) {
                }

                @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.titles.CommonPagerTitleView.OnPagerTitleChangeListener
                public void onLeave(int index2, int totalCount, float leavePercent, boolean leftToRight) {
                }

                @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.titles.CommonPagerTitleView.OnPagerTitleChangeListener
                public void onSelected(int index2, int totalCount) {
                    textView.setTextColor(Color.parseColor(SkinManager.getCurrentSkinType(TestActivity.this.mContext) == 1 ? "#B2575C" : "#DD594A"));
                    textView.setTextSize(16.0f);
                }
            });
            return commonPagerTitleView;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0() {
        this.stemContentHeight = this.llStemContent.getMeasuredHeight();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$init$1(View view, MotionEvent motionEvent) {
        return this.mGestureDetector.onTouchEvent(motionEvent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$init$2(LinearLayout linearLayout, View view, MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (action == 0) {
            this.startRawY = motionEvent.getRawY();
            return true;
        }
        if (action == 2) {
            this.offsetDiff = motionEvent.getRawY() - this.startRawY;
            if (motionEvent.getRawY() - this.startRawY > 0.0f) {
                if (this.nsl.getHeight() - CommonUtil.dip2px(view.getContext(), 5.0f) >= this.stemContentHeight) {
                    return false;
                }
                if (this.nsl.getHeight() - CommonUtil.dip2px(view.getContext(), 5.0f) >= this.stemContentHeight) {
                    this.nsl.setScrollbarFadingEnabled(true);
                    return false;
                }
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.nsl.getLayoutParams();
                layoutParams.height = (int) (layoutParams.height + Math.abs(this.offsetDiff));
                this.nsl.setLayoutParams(layoutParams);
                FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) linearLayout.getLayoutParams();
                layoutParams2.topMargin = (int) (layoutParams2.topMargin + this.offsetDiff);
                linearLayout.setLayoutParams(layoutParams2);
                this.startRawY = motionEvent.getRawY();
                return true;
            }
            if (motionEvent.getRawY() - this.startRawY < 0.0f) {
                if (this.nsl.getHeight() - CommonUtil.dip2px(view.getContext(), 5.0f) >= this.titleContentHeight) {
                    LinearLayout.LayoutParams layoutParams3 = (LinearLayout.LayoutParams) this.nsl.getLayoutParams();
                    layoutParams3.height = (int) (layoutParams3.height - Math.abs(this.offsetDiff));
                    this.nsl.setLayoutParams(layoutParams3);
                    FrameLayout.LayoutParams layoutParams4 = (FrameLayout.LayoutParams) linearLayout.getLayoutParams();
                    layoutParams4.topMargin = (int) (layoutParams4.topMargin - Math.abs(this.offsetDiff));
                    linearLayout.setLayoutParams(layoutParams4);
                }
                this.startRawY = motionEvent.getRawY();
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$3(TextView textView, LinearLayout linearLayout) {
        this.titleContentHeight = this.rlTopTitle.getMeasuredHeight() + findViewById(R.id.lineviewtype).getMeasuredHeight();
        if (textView.getLineCount() > 3) {
            this.titleContentHeight += textView.getLineHeight() * 3;
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.nsl.getLayoutParams();
            layoutParams.height = this.titleContentHeight;
            this.nsl.setLayoutParams(layoutParams);
            FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) linearLayout.getLayoutParams();
            layoutParams2.topMargin = this.titleContentHeight + CommonUtil.dip2px(this, 5.0f);
            linearLayout.setLayoutParams(layoutParams2);
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() throws Resources.NotFoundException {
        final MagicIndicator magicIndicator = (MagicIndicator) findViewById(R.id.magic_indicator);
        this.llStemContent = (LinearLayout) findViewById(R.id.ll_stem_content);
        this.nsl = (NestedScrollView) findViewById(R.id.nsl);
        this.rlTopTitle = (RelativeLayout) findViewById(R.id.rl_top_title);
        this.llStemContent.post(new Runnable() { // from class: com.psychiatrygarden.activity.xo
            @Override // java.lang.Runnable
            public final void run() {
                this.f14195c.lambda$init$0();
            }
        });
        AutoScrollViewPager autoScrollViewPager = (AutoScrollViewPager) findViewById(R.id.viewpager);
        autoScrollViewPager.setOffscreenPageLimit(1);
        autoScrollViewPager.setAdapter(new PagerAdapter() { // from class: com.psychiatrygarden.activity.TestActivity.1
            @Override // androidx.viewpager.widget.PagerAdapter
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                container.removeView((View) object);
            }

            @Override // androidx.viewpager.widget.PagerAdapter
            /* renamed from: getCount */
            public int getSize() {
                return 10;
            }

            @Override // androidx.viewpager.widget.PagerAdapter
            @NonNull
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                RecyclerView recyclerView = (RecyclerView) View.inflate(container.getContext(), R.layout.item_share_stem_question_options, null);
                ArrayList arrayList = new ArrayList();
                int i2 = 0;
                while (i2 < 30) {
                    StringBuilder sb = new StringBuilder();
                    i2++;
                    sb.append(i2);
                    sb.append("-测试内容测试内容测试内容测试内容测试 ");
                    arrayList.add(sb.toString());
                }
                BaseQuickAdapter<String, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_share_stem_question_options_child) { // from class: com.psychiatrygarden.activity.TestActivity.1.1
                    @Override // com.chad.library.adapter.base.BaseQuickAdapter
                    public void convert(@NonNull BaseViewHolder holder, String item) {
                        ((TextView) holder.itemView).setText(item);
                    }
                };
                baseQuickAdapter.setList(arrayList);
                recyclerView.setAdapter(baseQuickAdapter);
                container.addView(recyclerView);
                return recyclerView;
            }

            @Override // androidx.viewpager.widget.PagerAdapter
            public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
                return view == object;
            }
        });
        ArrayList arrayList = new ArrayList();
        int i2 = 0;
        while (i2 < 10) {
            StringBuilder sb = new StringBuilder();
            sb.append("第");
            int i3 = i2 + 1;
            sb.append(i3);
            sb.append("问");
            arrayList.add(new QuestionIndexBean(sb.toString(), String.valueOf(i3), i2 == 0));
            i2 = i3;
        }
        if (arrayList.size() > 5) {
            findViewById(R.id.iv_shadow_left).setVisibility(0);
            findViewById(R.id.iv_shadow_right).setVisibility(0);
        }
        this.mGestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() { // from class: com.psychiatrygarden.activity.TestActivity.2
            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
            public boolean onFling(MotionEvent e12, MotionEvent e2, float velocityX, float velocityY) {
                float x2 = e12.getX();
                float x3 = e2.getX();
                if (x2 - x3 > 200.0f && Math.abs(velocityX) > 0.0f) {
                    ToastUtil.shortToast(TestActivity.this, "切换下一题");
                    return false;
                }
                if (x3 - x2 <= 200.0f || Math.abs(velocityX) <= 0.0f) {
                    return false;
                }
                ToastUtil.shortToast(TestActivity.this, "切换上一题");
                return false;
            }
        });
        CommonNavigator commonNavigator = new CommonNavigator(this.mContext);
        commonNavigator.setSkimOver(false);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.ll_top_content);
        final TextView textView = (TextView) findViewById(R.id.titletv);
        commonNavigator.setAdapter(new AnonymousClass3(arrayList, autoScrollViewPager));
        magicIndicator.setNavigator(commonNavigator);
        autoScrollViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() { // from class: com.psychiatrygarden.activity.TestActivity.4
            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrollStateChanged(int state) {
                magicIndicator.onPageScrollStateChanged(state);
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                magicIndicator.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageSelected(int position) {
                magicIndicator.onPageSelected(position);
            }
        });
        this.nsl.setOnTouchListener(new View.OnTouchListener() { // from class: com.psychiatrygarden.activity.yo
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                return this.f14225c.lambda$init$1(view, motionEvent);
            }
        });
        final LinearLayout linearLayout2 = (LinearLayout) findViewById(R.id.ll_child_question);
        findViewById(R.id.rl_top).setOnTouchListener(new View.OnTouchListener() { // from class: com.psychiatrygarden.activity.zo
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                return this.f14257c.lambda$init$2(linearLayout2, view, motionEvent);
            }
        });
        linearLayout.post(new Runnable() { // from class: com.psychiatrygarden.activity.ap
            @Override // java.lang.Runnable
            public final void run() {
                this.f11084c.lambda$init$3(textView, linearLayout2);
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.fragment_share_stem_question);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}
