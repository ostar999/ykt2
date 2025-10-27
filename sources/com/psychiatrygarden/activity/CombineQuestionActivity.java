package com.psychiatrygarden.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.lxj.xpopup.enums.PopupAnimation;
import com.lxj.xpopup.enums.PopupPosition;
import com.lxj.xpopup.interfaces.SimpleCallback;
import com.psychiatrygarden.bean.PaperColumnItem;
import com.psychiatrygarden.bean.QuestionCombineTabItem;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.fragmenthome.CombineQuestionFragment;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.LogUtils;
import com.psychiatrygarden.utils.ScreenUtil;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.StatusBarUtil;
import com.psychiatrygarden.widget.CombinRecordEntranceGuidePop;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.List;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONArray;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class CombineQuestionActivity extends BaseActivity {
    private boolean loadDataSuccess;
    private BaseQuickAdapter<QuestionCombineTabItem, BaseViewHolder> mAdapter;
    private AnimationDrawable mAnimationDrawable;
    private final CountDownTimer mCountDownTimer = new CountDownTimer(ExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS, 1000) { // from class: com.psychiatrygarden.activity.CombineQuestionActivity.1
        @Override // android.os.CountDownTimer
        public void onFinish() {
            CombineQuestionActivity.this.mLyTitle.setVisibility(0);
            CombineQuestionActivity.this.mTvLoadingTitle.setVisibility(8);
            CombineQuestionActivity.this.mLyContentView.setVisibility(0);
            CombineQuestionActivity.this.mLyLoadingView.setVisibility(8);
            if (CombineQuestionActivity.this.mAnimationDrawable != null && CombineQuestionActivity.this.mAnimationDrawable.isRunning()) {
                CombineQuestionActivity.this.mAnimationDrawable.stop();
            }
            CombineQuestionActivity.this.mCountDownTimer.cancel();
        }

        @Override // android.os.CountDownTimer
        public void onTick(long millisUntilFinished) {
        }
    };
    private LinearLayout mLyContentView;
    private LinearLayout mLyLoadingView;
    private LinearLayout mLyTitle;
    private TextView mTvLoadingTitle;
    private TextView mTvTitle;
    private String mType;
    private ViewPager mViewPager;
    private RecyclerView rvRab;

    /* renamed from: com.psychiatrygarden.activity.CombineQuestionActivity$3, reason: invalid class name */
    public class AnonymousClass3 extends AjaxCallBack<String> {
        public AnonymousClass3() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSuccess$0(List list, BaseQuickAdapter baseQuickAdapter, View view, int i2) throws Resources.NotFoundException {
            CombineQuestionActivity.this.mViewPager.setCurrentItem(i2);
            int i3 = 0;
            while (i3 < list.size()) {
                ((QuestionCombineTabItem) list.get(i3)).setSelect(i3 == i2);
                i3++;
            }
            CombineQuestionActivity.this.mAdapter.notifyDataSetChanged();
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onFailure(Throwable t2, int errorNo, String strMsg) {
            super.onFailure(t2, errorNo, strMsg);
            if (TextUtils.isEmpty(strMsg)) {
                return;
            }
            CombineQuestionActivity.this.AlertToast(strMsg);
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onSuccess(String s2) {
            super.onSuccess((AnonymousClass3) s2);
            try {
                JSONObject jSONObject = new JSONObject(s2);
                if (!jSONObject.optString("code", "").equals("200")) {
                    CombineQuestionActivity.this.loadDataSuccess = false;
                    String strOptString = jSONObject.optString("message", "");
                    if (TextUtils.isEmpty(strOptString)) {
                        return;
                    }
                    CombineQuestionActivity.this.AlertToast(strOptString);
                    return;
                }
                int i2 = 1;
                CombineQuestionActivity.this.loadDataSuccess = true;
                JSONArray jSONArrayOptJSONArray = jSONObject.optJSONArray("data");
                if (jSONArrayOptJSONArray == null) {
                    jSONArrayOptJSONArray = new JSONArray();
                }
                List list = (List) new Gson().fromJson(jSONArrayOptJSONArray.toString(), new TypeToken<List<PaperColumnItem>>() { // from class: com.psychiatrygarden.activity.CombineQuestionActivity.3.1
                }.getType());
                if (list != null && !list.isEmpty()) {
                    final ArrayList arrayList = new ArrayList();
                    final ArrayList arrayList2 = new ArrayList();
                    int i3 = 0;
                    while (i3 < list.size()) {
                        arrayList.add(CombineQuestionFragment.getInstance(CombineQuestionActivity.this.mType, ((PaperColumnItem) list.get(i3)).getIdentity_id()));
                        arrayList2.add(new QuestionCombineTabItem(i3 == 0, ((PaperColumnItem) list.get(i3)).getTitle()));
                        i3++;
                    }
                    final boolean z2 = SkinManager.getCurrentSkinType(CombineQuestionActivity.this) == 1;
                    CombineQuestionActivity.this.rvRab.setAdapter(CombineQuestionActivity.this.mAdapter = new BaseQuickAdapter<QuestionCombineTabItem, BaseViewHolder>(R.layout.item_tab_combine_question, arrayList2) { // from class: com.psychiatrygarden.activity.CombineQuestionActivity.3.2
                        @Override // com.chad.library.adapter.base.BaseQuickAdapter
                        public void convert(@NonNull BaseViewHolder holder, QuestionCombineTabItem item) {
                            int color;
                            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) holder.itemView.getLayoutParams();
                            ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin = holder.getLayoutPosition() == 0 ? 0 : CommonUtil.dip2px(getContext(), 12.0f);
                            holder.itemView.setLayoutParams(layoutParams);
                            holder.setGone(R.id.iv_down_triangle, !item.isSelect());
                            CheckedTextView checkedTextView = (CheckedTextView) holder.getView(R.id.tv_column_name);
                            checkedTextView.setText(item.getTitle());
                            checkedTextView.setChecked(item.isSelect());
                            if (item.isSelect()) {
                                color = -1;
                            } else {
                                color = Color.parseColor(z2 ? "#7380A9" : "#141516");
                            }
                            checkedTextView.setTextColor(color);
                        }
                    });
                    CombineQuestionActivity.this.mAdapter.setOnItemClickListener(new OnItemClickListener() { // from class: com.psychiatrygarden.activity.f2
                        @Override // com.chad.library.adapter.base.listener.OnItemClickListener
                        public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i4) throws Resources.NotFoundException {
                            this.f12337c.lambda$onSuccess$0(arrayList2, baseQuickAdapter, view, i4);
                        }
                    });
                    CombineQuestionActivity.this.mViewPager.setOffscreenPageLimit(list.size());
                    CombineQuestionActivity.this.mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() { // from class: com.psychiatrygarden.activity.CombineQuestionActivity.3.3
                        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
                        public void onPageScrollStateChanged(int state) {
                        }

                        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
                        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                        }

                        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
                        public void onPageSelected(int position) throws Resources.NotFoundException {
                            CombineQuestionActivity.this.mViewPager.setCurrentItem(position);
                            int i4 = 0;
                            while (i4 < arrayList2.size()) {
                                ((QuestionCombineTabItem) arrayList2.get(i4)).setSelect(i4 == position);
                                i4++;
                            }
                            CombineQuestionActivity.this.mAdapter.notifyDataSetChanged();
                            CombineQuestionActivity.this.rvRab.smoothScrollToPosition(position);
                        }
                    });
                    CombineQuestionActivity.this.mViewPager.setAdapter(new FragmentPagerAdapter(CombineQuestionActivity.this.getSupportFragmentManager(), i2) { // from class: com.psychiatrygarden.activity.CombineQuestionActivity.3.4
                        @Override // androidx.viewpager.widget.PagerAdapter
                        /* renamed from: getCount */
                        public int getSize() {
                            return arrayList.size();
                        }

                        @Override // androidx.fragment.app.FragmentPagerAdapter
                        @NonNull
                        public Fragment getItem(int position) {
                            return (Fragment) arrayList.get(position);
                        }
                    });
                }
            } catch (Exception e2) {
                e2.printStackTrace();
                CombineQuestionActivity.this.loadDataSuccess = false;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(View view) {
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$1(View view) {
        startActivity(new Intent(view.getContext(), (Class<?>) CombineQuestionRecordListActivity.class));
    }

    private void loadPaperColumn() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("parent_id", SharePreferencesUtils.readStrConfig(CommonParameter.identity_id, this, ""));
        YJYHttpUtils.get(this, NetworkRequestsURL.combinePaperColumn, ajaxParams, new AnonymousClass3());
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        setTitle("请选择组题类型");
        findViewById(R.id.iv_actionbar_back).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.d2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12226c.lambda$init$0(view);
            }
        });
        this.rvRab = (RecyclerView) findViewById(R.id.rvTab);
        this.mViewPager = (ViewPager) findViewById(R.id.viewPager);
        this.mTvTitle = (TextView) findViewById(R.id.tv_title);
        this.mLyTitle = (LinearLayout) findViewById(R.id.ly_title);
        this.mTvLoadingTitle = (TextView) findViewById(R.id.tv_loading_title);
        this.mLyContentView = (LinearLayout) findViewById(R.id.ly_content_view);
        this.mLyLoadingView = (LinearLayout) findViewById(R.id.ly_loading_view);
        ImageView imageView = (ImageView) findViewById(R.id.iv_robot);
        if (SkinManager.getCurrentSkinType(this) == 1) {
            imageView.setBackground(ContextCompat.getDrawable(this, R.drawable.anim_combine_question_guide_night));
        }
        this.mAnimationDrawable = (AnimationDrawable) imageView.getBackground();
        findViewById(R.id.iv_actionbar_right).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.e2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12266c.lambda$init$1(view);
            }
        });
        AnimationDrawable animationDrawable = this.mAnimationDrawable;
        if (animationDrawable != null && !animationDrawable.isRunning()) {
            this.mAnimationDrawable.start();
        }
        this.mCountDownTimer.start();
        if (SharePreferencesUtils.readBooleanConfig(CommonParameter.showCombinQuestion, true, this.mContext)) {
            int pxByDp = ScreenUtil.getPxByDp((Context) this, 44);
            LogUtils.e("top_right", "top:" + pxByDp + ";top2==>" + this.mLyLoadingView.getTop());
            XPopup.Builder builderOffsetY = new XPopup.Builder(this).setPopupCallback(new SimpleCallback() { // from class: com.psychiatrygarden.activity.CombineQuestionActivity.2
                @Override // com.lxj.xpopup.interfaces.SimpleCallback, com.lxj.xpopup.interfaces.XPopupCallback
                public void onDismiss(BasePopupView popupView) {
                    SharePreferencesUtils.writeBooleanConfig(CommonParameter.showCombinQuestion, false, CombineQuestionActivity.this.mContext);
                }
            }).isCenterHorizontal(true).atView(this.mLyTitle).popupPosition(PopupPosition.Bottom).popupAnimation(PopupAnimation.ScaleAlphaFromCenter).offsetY(pxByDp);
            Boolean bool = Boolean.FALSE;
            ((CombinRecordEntranceGuidePop) builderOffsetY.hasShadowBg(bool).dismissOnTouchOutside(bool).asCustom(new CombinRecordEntranceGuidePop(this))).show();
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void initStatusBar() {
        if (SkinManager.getCurrentSkinType(this) == 1) {
            getWindow().setNavigationBarColor(Color.parseColor("#121622"));
            StatusBarUtil.setColor(this, Color.parseColor("#1C2134"), 0);
        } else {
            getWindow().setNavigationBarColor(Color.parseColor("#FBFBFB"));
            StatusBarUtil.setColor(this, -1, 0);
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        if (this.loadDataSuccess) {
            return;
        }
        loadPaperColumn();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        this.mActionBar.hide();
        setContentView(R.layout.activity_combine_question);
        this.mType = getIntent().getStringExtra("type");
        if (SkinManager.getCurrentSkinType(this) == 0) {
            getWindow().getDecorView().setSystemUiVisibility(8192);
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}
