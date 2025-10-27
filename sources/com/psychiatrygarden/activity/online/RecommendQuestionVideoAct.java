package com.psychiatrygarden.activity.online;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.google.android.exoplayer2.ExoPlayer;
import com.hyphenate.easeui.utils.StatusBarCompat;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.online.RecommendQuestionVideoAct;
import com.psychiatrygarden.fragmenthome.AnswerSheetFragment;
import com.psychiatrygarden.fragmenthome.RecommendVideoFragment;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.StatusBarUtil;
import com.yikaobang.yixue.R;
import com.ykb.ebook.extensions.ViewExtensionsKt;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000j\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\b\u0018\u0000 )2\u00020\u0001:\u0001)B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010!\u001a\u00020\"H\u0016J\b\u0010#\u001a\u00020\"H\u0002J\b\u0010$\u001a\u00020\"H\u0014J\u0012\u0010%\u001a\u00020\"2\b\u0010&\u001a\u0004\u0018\u00010\tH\u0016J\b\u0010'\u001a\u00020\"H\u0016J\b\u0010(\u001a\u00020\"H\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u000bX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082.¢\u0006\u0002\n\u0000R\u001e\u0010\u0013\u001a\u0012\u0012\u0004\u0012\u00020\u00150\u0014j\b\u0012\u0004\u0012\u00020\u0015`\u0016X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0018X\u0082.¢\u0006\u0002\n\u0000R\u0010\u0010\u0019\u001a\u0004\u0018\u00010\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u000eX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u001cX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u001eX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020 X\u0082.¢\u0006\u0002\n\u0000¨\u0006*"}, d2 = {"Lcom/psychiatrygarden/activity/online/RecommendQuestionVideoAct;", "Lcom/psychiatrygarden/activity/BaseActivity;", "()V", "currentPosition", "", "hasQuestion", "", "hasVideo", "id", "", "ivLoading", "Landroid/widget/ImageView;", "ivRedo", "llLoading", "Landroid/view/View;", "mAnimationDrawable", "Landroid/graphics/drawable/AnimationDrawable;", "mAnimator", "Landroid/animation/ValueAnimator;", "mFragments", "Ljava/util/ArrayList;", "Landroidx/fragment/app/Fragment;", "Lkotlin/collections/ArrayList;", "progressBar", "Landroid/widget/ProgressBar;", "question_bank_id", "rootView", "rvTab", "Landroidx/recyclerview/widget/RecyclerView;", "tvLoading", "Landroid/widget/TextView;", "viewpager", "Landroidx/viewpager/widget/ViewPager;", "init", "", "initTab", "onDestroy", "onEventMainThread", "str", "setContentView", "setListenerForWidget", "Companion", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nRecommendQuestionVideoAct.kt\nKotlin\n*S Kotlin\n*F\n+ 1 RecommendQuestionVideoAct.kt\ncom/psychiatrygarden/activity/online/RecommendQuestionVideoAct\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,235:1\n1855#2,2:236\n*S KotlinDebug\n*F\n+ 1 RecommendQuestionVideoAct.kt\ncom/psychiatrygarden/activity/online/RecommendQuestionVideoAct\n*L\n96#1:236,2\n*E\n"})
/* loaded from: classes5.dex */
public final class RecommendQuestionVideoAct extends BaseActivity {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);
    private int currentPosition;
    private boolean hasQuestion;
    private boolean hasVideo;
    private String id;
    private ImageView ivLoading;
    private ImageView ivRedo;
    private View llLoading;
    private AnimationDrawable mAnimationDrawable;
    private ValueAnimator mAnimator;

    @NotNull
    private final ArrayList<Fragment> mFragments = new ArrayList<>();
    private ProgressBar progressBar;

    @Nullable
    private String question_bank_id;
    private View rootView;
    private RecyclerView rvTab;
    private TextView tvLoading;
    private ViewPager viewpager;

    @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002JJ\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\b\b\u0002\u0010\u000b\u001a\u00020\f2\b\b\u0002\u0010\r\u001a\u00020\f2\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\b2\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\b¨\u0006\u0010"}, d2 = {"Lcom/psychiatrygarden/activity/online/RecommendQuestionVideoAct$Companion;", "", "()V", "newIntent", "", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "id", "", "position", "", "hasQuestion", "", "hasVideo", "title", "questionBankId", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final void newIntent(@NotNull Context context, @NotNull String id, int position, boolean hasQuestion, boolean hasVideo, @Nullable String title, @Nullable String questionBankId) {
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(id, "id");
            context.startActivity(new Intent(context, (Class<?>) RecommendQuestionVideoAct.class).putExtra("hasQuestion", hasQuestion).putExtra("question_bank_id", questionBankId).putExtra("title", title).putExtra("hasVideo", hasVideo).putExtra("id", id).putExtra("position", position));
        }
    }

    @Metadata(d1 = {"\u0000\u001b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003*\u0001\u0000\b\n\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001J\u0018\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\u0002H\u0014¨\u0006\b"}, d2 = {"com/psychiatrygarden/activity/online/RecommendQuestionVideoAct$initTab$1", "Lcom/chad/library/adapter/base/BaseQuickAdapter;", "", "Lcom/chad/library/adapter/base/viewholder/BaseViewHolder;", "convert", "", "holder", "item", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.psychiatrygarden.activity.online.RecommendQuestionVideoAct$initTab$1, reason: invalid class name */
    public static final class AnonymousClass1 extends BaseQuickAdapter<String, BaseViewHolder> {
        public AnonymousClass1() {
            super(R.layout.item_recommend_category, null, 2, null);
            ArrayList arrayList = new ArrayList();
            if (RecommendQuestionVideoAct.this.hasVideo && RecommendQuestionVideoAct.this.hasQuestion) {
                arrayList.add("推荐试题");
                arrayList.add("推荐视频");
            } else if (RecommendQuestionVideoAct.this.hasQuestion) {
                arrayList.add("推荐试题");
                RecommendQuestionVideoAct.this.currentPosition = 0;
            } else if (RecommendQuestionVideoAct.this.hasVideo) {
                RecommendQuestionVideoAct.this.currentPosition = 0;
                arrayList.add("推荐视频");
            }
            setList(arrayList);
            setOnItemClickListener(new OnItemClickListener() { // from class: com.psychiatrygarden.activity.online.b2
                @Override // com.chad.library.adapter.base.listener.OnItemClickListener
                public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i2) throws Resources.NotFoundException {
                    RecommendQuestionVideoAct.AnonymousClass1._init_$lambda$0(recommendQuestionVideoAct, baseQuickAdapter, view, i2);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void _init_$lambda$0(RecommendQuestionVideoAct this$0, BaseQuickAdapter adapter, View view, int i2) throws Resources.NotFoundException {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Intrinsics.checkNotNullParameter(adapter, "adapter");
            Intrinsics.checkNotNullParameter(view, "<anonymous parameter 1>");
            ViewPager viewPager = this$0.viewpager;
            if (viewPager == null) {
                Intrinsics.throwUninitializedPropertyAccessException("viewpager");
                viewPager = null;
            }
            viewPager.setCurrentItem(i2);
            this$0.currentPosition = i2;
            adapter.notifyDataSetChanged();
        }

        @Override // com.chad.library.adapter.base.BaseQuickAdapter
        public void convert(@NotNull BaseViewHolder holder, @NotNull String item) {
            Intrinsics.checkNotNullParameter(holder, "holder");
            Intrinsics.checkNotNullParameter(item, "item");
            TypedArray typedArrayObtainStyledAttributes = RecommendQuestionVideoAct.this.getTheme().obtainStyledAttributes(new int[]{R.attr.first_txt_color, R.attr.third_txt_color});
            Intrinsics.checkNotNullExpressionValue(typedArrayObtainStyledAttributes, "theme.obtainStyledAttrib… R.attr.third_txt_color))");
            TextView textView = (TextView) holder.getView(R.id.tv_column_name);
            textView.setText(item);
            textView.setTextSize(holder.getLayoutPosition() == RecommendQuestionVideoAct.this.currentPosition ? 16.0f : 14.0f);
            textView.setTypeface(holder.getLayoutPosition() == RecommendQuestionVideoAct.this.currentPosition ? Typeface.DEFAULT_BOLD : Typeface.DEFAULT);
            boolean z2 = false;
            if (holder.getLayoutPosition() == RecommendQuestionVideoAct.this.currentPosition) {
                textView.setTextColor(typedArrayObtainStyledAttributes.getColor(0, RecommendQuestionVideoAct.this.getColor(R.color.first_txt_color)));
            } else {
                textView.setTextColor(typedArrayObtainStyledAttributes.getColor(1, RecommendQuestionVideoAct.this.getColor(R.color.third_txt_color)));
            }
            if (holder.getLayoutPosition() == RecommendQuestionVideoAct.this.currentPosition && getData().size() > 1) {
                z2 = true;
            }
            holder.setVisible(R.id.img_choose, z2);
            typedArrayObtainStyledAttributes.recycle();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void init$lambda$2(RecommendQuestionVideoAct this$0, ValueAnimator it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(it, "it");
        TextView textView = this$0.tvLoading;
        ProgressBar progressBar = null;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvLoading");
            textView = null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("正在加载中");
        Object animatedValue = it.getAnimatedValue();
        Intrinsics.checkNotNull(animatedValue, "null cannot be cast to non-null type kotlin.Int");
        sb.append(((Integer) animatedValue).intValue());
        sb.append('%');
        textView.setText(sb.toString());
        ProgressBar progressBar2 = this$0.progressBar;
        if (progressBar2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("progressBar");
        } else {
            progressBar = progressBar2;
        }
        Object animatedValue2 = it.getAnimatedValue();
        Intrinsics.checkNotNull(animatedValue2, "null cannot be cast to non-null type kotlin.Int");
        progressBar.setProgress(((Integer) animatedValue2).intValue());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void initTab() throws Resources.NotFoundException {
        RecyclerView recyclerView = this.rvTab;
        ViewPager viewPager = null;
        if (recyclerView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("rvTab");
            recyclerView = null;
        }
        recyclerView.setAdapter(new AnonymousClass1());
        if (this.hasQuestion) {
            ArrayList<Fragment> arrayList = this.mFragments;
            AnswerSheetFragment answerSheetFragment = new AnswerSheetFragment();
            Bundle bundle = new Bundle();
            String str = this.id;
            if (str == null) {
                Intrinsics.throwUninitializedPropertyAccessException("id");
                str = null;
            }
            bundle.putString("knowledge_id", str);
            bundle.putString("type", "recommend");
            bundle.putString("dataType", "knowledge");
            bundle.putBoolean("isKnowledge", true);
            bundle.putString("question_bank_id", this.question_bank_id);
            answerSheetFragment.setArguments(bundle);
            arrayList.add(answerSheetFragment);
        }
        if (this.hasVideo) {
            ArrayList<Fragment> arrayList2 = this.mFragments;
            RecommendVideoFragment recommendVideoFragment = new RecommendVideoFragment();
            Bundle bundle2 = new Bundle();
            String str2 = this.id;
            if (str2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("id");
                str2 = null;
            }
            bundle2.putString("knowledge_id", str2);
            bundle2.putString("type", "recommend");
            recommendVideoFragment.setArguments(bundle2);
            arrayList2.add(recommendVideoFragment);
        }
        ViewPager viewPager2 = this.viewpager;
        if (viewPager2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("viewpager");
            viewPager2 = null;
        }
        viewPager2.addOnPageChangeListener(new ViewPager.OnPageChangeListener() { // from class: com.psychiatrygarden.activity.online.RecommendQuestionVideoAct.initTab.4
            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrollStateChanged(int state) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageSelected(int position) {
                Object obj = RecommendQuestionVideoAct.this.mFragments.get(position);
                Intrinsics.checkNotNullExpressionValue(obj, "mFragments[position]");
                Fragment fragment = (Fragment) obj;
                ImageView imageView = RecommendQuestionVideoAct.this.ivRedo;
                RecyclerView recyclerView2 = null;
                if (imageView == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("ivRedo");
                    imageView = null;
                }
                imageView.setVisibility(fragment instanceof AnswerSheetFragment ? 0 : 8);
                RecommendQuestionVideoAct.this.currentPosition = position;
                RecyclerView recyclerView3 = RecommendQuestionVideoAct.this.rvTab;
                if (recyclerView3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("rvTab");
                } else {
                    recyclerView2 = recyclerView3;
                }
                RecyclerView.Adapter adapter = recyclerView2.getAdapter();
                if (adapter != null) {
                    adapter.notifyDataSetChanged();
                }
            }
        });
        ViewPager viewPager3 = this.viewpager;
        if (viewPager3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("viewpager");
            viewPager3 = null;
        }
        viewPager3.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) { // from class: com.psychiatrygarden.activity.online.RecommendQuestionVideoAct.initTab.5
            @Override // androidx.viewpager.widget.PagerAdapter
            /* renamed from: getCount */
            public int getSize() {
                return RecommendQuestionVideoAct.this.mFragments.size();
            }

            @Override // androidx.fragment.app.FragmentPagerAdapter
            @NotNull
            public Fragment getItem(int position) {
                Object obj = RecommendQuestionVideoAct.this.mFragments.get(position);
                Intrinsics.checkNotNullExpressionValue(obj, "mFragments[position]");
                return (Fragment) obj;
            }
        });
        ViewPager viewPager4 = this.viewpager;
        if (viewPager4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("viewpager");
        } else {
            viewPager = viewPager4;
        }
        viewPager.setCurrentItem(this.currentPosition);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setContentView$lambda$1(RecommendQuestionVideoAct this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        for (Fragment fragment : this$0.mFragments) {
            if (fragment instanceof AnswerSheetFragment) {
                ((AnswerSheetFragment) fragment).showRedoDialog();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setListenerForWidget$lambda$3(RecommendQuestionVideoAct this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.finish();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        View viewFindViewById = findViewById(R.id.ll_loading);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(R.id.ll_loading)");
        this.llLoading = viewFindViewById;
        View viewFindViewById2 = findViewById(R.id.iv_loading);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "findViewById(R.id.iv_loading)");
        this.ivLoading = (ImageView) viewFindViewById2;
        View viewFindViewById3 = findViewById(R.id.tv_loading);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById3, "findViewById(R.id.tv_loading)");
        this.tvLoading = (TextView) viewFindViewById3;
        View viewFindViewById4 = findViewById(R.id.progress_bar);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById4, "findViewById(R.id.progress_bar)");
        this.progressBar = (ProgressBar) viewFindViewById4;
        ImageView imageView = this.ivLoading;
        ValueAnimator valueAnimator = null;
        if (imageView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ivLoading");
            imageView = null;
        }
        Drawable background = imageView.getBackground();
        Intrinsics.checkNotNull(background, "null cannot be cast to non-null type android.graphics.drawable.AnimationDrawable");
        AnimationDrawable animationDrawable = (AnimationDrawable) background;
        this.mAnimationDrawable = animationDrawable;
        if (animationDrawable == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mAnimationDrawable");
            animationDrawable = null;
        }
        animationDrawable.start();
        ValueAnimator valueAnimatorOfInt = ValueAnimator.ofInt(0, 50, 100);
        Intrinsics.checkNotNullExpressionValue(valueAnimatorOfInt, "ofInt(0, 50, 100)");
        this.mAnimator = valueAnimatorOfInt;
        if (valueAnimatorOfInt == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mAnimator");
            valueAnimatorOfInt = null;
        }
        valueAnimatorOfInt.setInterpolator(new LinearInterpolator());
        ValueAnimator valueAnimator2 = this.mAnimator;
        if (valueAnimator2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mAnimator");
            valueAnimator2 = null;
        }
        valueAnimator2.setDuration(ExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS);
        ValueAnimator valueAnimator3 = this.mAnimator;
        if (valueAnimator3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mAnimator");
            valueAnimator3 = null;
        }
        valueAnimator3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.psychiatrygarden.activity.online.a2
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator4) {
                RecommendQuestionVideoAct.init$lambda$2(this.f13091c, valueAnimator4);
            }
        });
        ValueAnimator valueAnimator4 = this.mAnimator;
        if (valueAnimator4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mAnimator");
            valueAnimator4 = null;
        }
        valueAnimator4.addListener(new Animator.AnimatorListener() { // from class: com.psychiatrygarden.activity.online.RecommendQuestionVideoAct.init.2
            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationCancel(@NotNull Animator animation) {
                Intrinsics.checkNotNullParameter(animation, "animation");
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(@NotNull Animator animation) throws Resources.NotFoundException {
                Intrinsics.checkNotNullParameter(animation, "animation");
                View view = RecommendQuestionVideoAct.this.rootView;
                ImageView imageView2 = null;
                if (view == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("rootView");
                    view = null;
                }
                RecommendQuestionVideoAct recommendQuestionVideoAct = RecommendQuestionVideoAct.this;
                view.setBackground(new ColorDrawable(recommendQuestionVideoAct.getColor(SkinManager.getCurrentSkinType(recommendQuestionVideoAct) == 1 ? R.color.app_bg_night : R.color.app_bg)));
                View view2 = RecommendQuestionVideoAct.this.llLoading;
                if (view2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("llLoading");
                    view2 = null;
                }
                ViewExtensionsKt.gone(view2);
                ImageView imageView3 = RecommendQuestionVideoAct.this.ivRedo;
                if (imageView3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("ivRedo");
                } else {
                    imageView2 = imageView3;
                }
                imageView2.setVisibility((RecommendQuestionVideoAct.this.hasQuestion && RecommendQuestionVideoAct.this.currentPosition == 0) ? 0 : 8);
                RecommendQuestionVideoAct.this.initTab();
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationRepeat(@NotNull Animator animation) {
                Intrinsics.checkNotNullParameter(animation, "animation");
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationStart(@NotNull Animator animation) {
                Intrinsics.checkNotNullParameter(animation, "animation");
            }
        });
        ValueAnimator valueAnimator5 = this.mAnimator;
        if (valueAnimator5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mAnimator");
        } else {
            valueAnimator = valueAnimator5;
        }
        valueAnimator.start();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        AnimationDrawable animationDrawable = this.mAnimationDrawable;
        ValueAnimator valueAnimator = null;
        if (animationDrawable == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mAnimationDrawable");
            animationDrawable = null;
        }
        if (animationDrawable.isRunning()) {
            AnimationDrawable animationDrawable2 = this.mAnimationDrawable;
            if (animationDrawable2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mAnimationDrawable");
                animationDrawable2 = null;
            }
            animationDrawable2.stop();
        }
        ValueAnimator valueAnimator2 = this.mAnimator;
        if (valueAnimator2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mAnimator");
            valueAnimator2 = null;
        }
        if (valueAnimator2.isRunning()) {
            ValueAnimator valueAnimator3 = this.mAnimator;
            if (valueAnimator3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mAnimator");
            } else {
                valueAnimator = valueAnimator3;
            }
            valueAnimator.cancel();
        }
        super.onDestroy();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(@Nullable String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() throws IllegalAccessException, NoSuchFieldException, NoSuchMethodException, ClassNotFoundException, SecurityException, IllegalArgumentException, InvocationTargetException {
        setContentView(R.layout.act_recommend_question_video);
        StatusBarUtil.setStatusBarTranslucent(this, false);
        StatusBarCompat.setLightStatusBar(this, true);
        this.mActionBar.hide();
        String stringExtra = getIntent().getStringExtra("id");
        if (stringExtra == null) {
            stringExtra = "";
        }
        this.id = stringExtra;
        this.currentPosition = getIntent().getIntExtra("position", 0);
        this.hasVideo = getIntent().getBooleanExtra("hasVideo", false);
        this.hasQuestion = getIntent().getBooleanExtra("hasQuestion", false);
        this.question_bank_id = getIntent().getStringExtra("question_bank_id");
        View viewFindViewById = findViewById(R.id.rvTab);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(R.id.rvTab)");
        this.rvTab = (RecyclerView) viewFindViewById;
        View viewFindViewById2 = findViewById(R.id.viewpager);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "findViewById(R.id.viewpager)");
        this.viewpager = (ViewPager) viewFindViewById2;
        View viewFindViewById3 = findViewById(R.id.ll_root);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById3, "findViewById(R.id.ll_root)");
        this.rootView = viewFindViewById3;
        View viewFindViewById4 = findViewById(R.id.rl_top);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById4, "findViewById(R.id.rl_top)");
        RelativeLayout relativeLayout = (RelativeLayout) viewFindViewById4;
        ViewGroup.LayoutParams layoutParams = relativeLayout.getLayoutParams();
        Intrinsics.checkNotNull(layoutParams, "null cannot be cast to non-null type android.widget.LinearLayout.LayoutParams");
        LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) layoutParams;
        layoutParams2.topMargin = StatusBarUtil.getStatusBarHeight(this);
        relativeLayout.setLayoutParams(layoutParams2);
        View viewFindViewById5 = findViewById(R.id.iv_redo);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById5, "findViewById(R.id.iv_redo)");
        ImageView imageView = (ImageView) viewFindViewById5;
        this.ivRedo = imageView;
        if (imageView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ivRedo");
            imageView = null;
        }
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.online.y1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                RecommendQuestionVideoAct.setContentView$lambda$1(this.f13507c, view);
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.online.z1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                RecommendQuestionVideoAct.setListenerForWidget$lambda$3(this.f13510c, view);
            }
        });
    }
}
