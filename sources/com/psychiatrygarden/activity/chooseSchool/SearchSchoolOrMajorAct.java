package com.psychiatrygarden.activity.chooseSchool;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.aliyun.svideo.common.baseAdapter.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.chooseSchool.bean.FollowSchoolBean;
import com.psychiatrygarden.bean.ForumTopBean;
import com.psychiatrygarden.bean.SearchSchoolOrMajorBean;
import com.psychiatrygarden.bean.TarGetParamBean;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.forum.SearchTypeAdp;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.Constants;
import com.psychiatrygarden.utils.KeyboardInputUtil;
import com.psychiatrygarden.utils.PublicMethodActivity;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.StatusBarUtil;
import com.psychiatrygarden.widget.ClearEditText;
import com.psychiatrygarden.widget.CustomEmptyView;
import com.psychiatrygarden.widget.HistoryLabelsView;
import com.psychiatrygarden.widget.LabelsView;
import com.psychiatrygarden.widget.TagLabelsView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import okhttp3.HttpUrl;

/* loaded from: classes5.dex */
public class SearchSchoolOrMajorAct extends BaseActivity implements OnRefreshLoadMoreListener {
    private CustomEmptyView emptyView;
    private boolean isEditSearch;
    private boolean isExpandHistory;
    private ImageView mBtnBack;
    private ImageView mBtnDelHistory;
    private TextView mBtnSearch;
    private SearchSchoolOrMajorAdp mContentAdp;
    private ClearEditText mEtContent;
    private int mHistoryCollapseHeight;
    private int mHistoryExpandHeight;
    private ImageView mImgMoreArrow;
    private HistoryLabelsView mLbDelHistoryView;
    private LabelsView mLbHistoryView;
    private TagLabelsView mLbHotWordView;
    private LinearLayout mLyHistoryView;
    private LinearLayout mLyHotWord;
    private LinearLayout mLyResultView;
    private LinearLayout mLyShowDelAll;
    private LinearLayout mLyShowMore;
    private RelativeLayout mLyView;
    private TarGetParamBean mParams;
    private RecyclerView mResultRecycler;
    private SmartRefreshLayout mResultRefresh;
    private TextView mTvDelAll;
    private TextView mTvFinish;
    private TextView mTvMoreHistory;
    private SearchTypeAdp mTypeAdapter;
    private RecyclerView mTypeRecycler;
    private String mWord;
    private List<String> historyList = new ArrayList();
    private int searchPage = 1;
    private int page = 1;
    private boolean isClickSearchKey = false;
    private String mType = "2";
    private List<String> mFilterKey = new ArrayList();
    private int mProjectParentPos = 0;
    private int mHistoryMoreHeight = 0;
    private int mHistoryCollHeight = 0;
    private int isFromSerarchType = 0;
    private int height = 0;

    private void addHistoryContent(String content) {
        this.historyList.remove(content);
        this.historyList.add(0, content);
        if (this.historyList.size() > 20) {
            this.historyList = this.historyList.subList(0, 20);
        }
        SharePreferencesUtils.writeStrConfig(CommonParameter.getSchoolSaveSearchHistoryData(), new Gson().toJson(this.historyList), this);
        initHistory();
    }

    private void initHistory() {
        this.isExpandHistory = false;
        this.mTvMoreHistory.setText("更多记录");
        this.mImgMoreArrow.setRotation(0.0f);
        this.mLbHistoryView.removeAllViews();
        List<String> list = (List) new Gson().fromJson(SharePreferencesUtils.readStrConfig(CommonParameter.getSchoolSaveSearchHistoryData(), this, HttpUrl.PATH_SEGMENT_ENCODE_SET_URI), new TypeToken<List<String>>() { // from class: com.psychiatrygarden.activity.chooseSchool.SearchSchoolOrMajorAct.1
        }.getType());
        this.historyList = list;
        if (list.size() <= 0) {
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.mLbHistoryView.getLayoutParams();
            layoutParams.height = -2;
            this.mLbHistoryView.setLayoutParams(layoutParams);
            this.mLyHistoryView.setVisibility(8);
            return;
        }
        this.mLyHistoryView.setVisibility(0);
        this.mLbHistoryView.setLabels(this.historyList, new LabelsView.LabelTextProvider() { // from class: com.psychiatrygarden.activity.chooseSchool.o6
            @Override // com.psychiatrygarden.widget.LabelsView.LabelTextProvider
            public final CharSequence getLabelText(TextView textView, int i2, Object obj) {
                return this.f11375a.lambda$initHistory$1(textView, i2, (String) obj);
            }
        });
        this.mLbHistoryView.post(new Runnable() { // from class: com.psychiatrygarden.activity.chooseSchool.p6
            @Override // java.lang.Runnable
            public final void run() {
                this.f11383c.lambda$initHistory$3();
            }
        });
        this.mLbHistoryView.setOnLabelClickListener(new LabelsView.OnLabelClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.q6
            @Override // com.psychiatrygarden.widget.LabelsView.OnLabelClickListener
            public final void onLabelClick(TextView textView, Object obj, int i2) {
                this.f11391a.lambda$initHistory$4(textView, obj, i2);
            }
        });
        this.mLbDelHistoryView.setLabels(this.historyList, new HistoryLabelsView.LabelTextProvider() { // from class: com.psychiatrygarden.activity.chooseSchool.r6
            @Override // com.psychiatrygarden.widget.HistoryLabelsView.LabelTextProvider
            public final CharSequence getLabelText(TextView textView, int i2, Object obj) {
                return this.f11399a.lambda$initHistory$5(textView, i2, (String) obj);
            }
        });
        this.mLbDelHistoryView.setOnLabelClickListener(new HistoryLabelsView.OnLabelClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.s6
            @Override // com.psychiatrygarden.widget.HistoryLabelsView.OnLabelClickListener
            public final void onLabelClick(TextView textView, Object obj, int i2) {
                this.f11409a.lambda$initHistory$6(textView, obj, i2);
            }
        });
    }

    private void initSearch() {
        this.mEtContent.post(new Runnable() { // from class: com.psychiatrygarden.activity.chooseSchool.k6
            @Override // java.lang.Runnable
            public final void run() {
                this.f11340c.lambda$initSearch$8();
            }
        });
        this.mEtContent.setOnEditorActionListener(new TextView.OnEditorActionListener() { // from class: com.psychiatrygarden.activity.chooseSchool.l6
            @Override // android.widget.TextView.OnEditorActionListener
            public final boolean onEditorAction(TextView textView, int i2, KeyEvent keyEvent) {
                return this.f11348c.lambda$initSearch$9(textView, i2, keyEvent);
            }
        });
        this.mEtContent.addTextChangedListener(new TextWatcher() { // from class: com.psychiatrygarden.activity.chooseSchool.SearchSchoolOrMajorAct.2
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable s2) {
                if (s2.length() > 0) {
                    if (SearchSchoolOrMajorAct.this.isClickSearchKey) {
                        return;
                    }
                    SearchSchoolOrMajorAct.this.searchPage = 1;
                } else {
                    SearchSchoolOrMajorAct.this.isClickSearchKey = false;
                    SearchSchoolOrMajorAct.this.mLyResultView.setVisibility(8);
                    SharePreferencesUtils.writeStrConfig("searchCircleKeyWords", "", SearchSchoolOrMajorAct.this.mContext);
                    SearchSchoolOrMajorAct.this.mContentAdp.getData().clear();
                    SearchSchoolOrMajorAct.this.mContentAdp.notifyDataSetChanged();
                }
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence s2, int start, int count, int after) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence s2, int start, int before, int count) {
            }
        });
    }

    private void initTypeData() {
        ArrayList arrayList = new ArrayList();
        ForumTopBean.TopModule topModule = new ForumTopBean.TopModule();
        topModule.setCode("2");
        topModule.setName("专业");
        arrayList.add(topModule);
        ForumTopBean.TopModule topModule2 = new ForumTopBean.TopModule();
        topModule2.setCode("1");
        topModule2.setName("院校");
        arrayList.add(topModule2);
        this.mTypeAdapter.setNewData(arrayList);
        this.mTypeAdapter.setSelectPos(this.isFromSerarchType);
        this.mTypeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.j6
            @Override // com.aliyun.svideo.common.baseAdapter.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
                this.f11332a.lambda$initTypeData$7(baseQuickAdapter, view, i2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(View view) {
        onRefresh(this.mResultRefresh);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ CharSequence lambda$initHistory$1(TextView textView, int i2, String str) {
        return this.historyList.get(i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initHistory$2(int i2) {
        this.mHistoryMoreHeight = this.mHistoryExpandHeight;
        if (this.mHistoryCollHeight == 0) {
            this.mHistoryCollHeight = this.mLbHistoryView.getMeasuredHeight();
        }
        this.mHistoryCollapseHeight = this.mHistoryCollHeight;
        this.mLbHistoryView.setMaxLines(i2);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.mLbHistoryView.getLayoutParams();
        layoutParams.height = this.mHistoryCollapseHeight;
        this.mLbHistoryView.setLayoutParams(layoutParams);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initHistory$3() {
        int measuredHeight = this.mLbHistoryView.getMeasuredHeight();
        this.mHistoryExpandHeight = measuredHeight;
        int i2 = this.mHistoryMoreHeight;
        if (i2 > measuredHeight) {
            this.mHistoryExpandHeight = i2;
        }
        Log.e("view_height", "mHistoryExpandHeight高度====》" + this.mHistoryExpandHeight);
        final int lines = this.mLbHistoryView.getLines();
        if (this.mLbHistoryView.getLines() <= 3) {
            this.mLyShowMore.setVisibility(8);
            return;
        }
        this.mLbHistoryView.setMaxLines(3);
        this.mLyShowMore.setVisibility(0);
        this.mLbHistoryView.post(new Runnable() { // from class: com.psychiatrygarden.activity.chooseSchool.c6
            @Override // java.lang.Runnable
            public final void run() {
                this.f11233c.lambda$initHistory$2(lines);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initHistory$4(TextView textView, Object obj, int i2) {
        this.isClickSearchKey = true;
        this.mLyResultView.setVisibility(0);
        this.mResultRefresh.autoRefreshAnimationOnly();
        this.mEtContent.setText(textView.getText().toString());
        ClearEditText clearEditText = this.mEtContent;
        clearEditText.setSelection(clearEditText.getText().toString().length());
        this.mLbDelHistoryView.setVisibility(8);
        this.mLyShowDelAll.setVisibility(8);
        this.mLyShowDelAll.setVisibility(8);
        this.mBtnDelHistory.setVisibility(0);
        this.page = 1;
        this.isEditSearch = false;
        getSearchData("1");
        hideInputMethod();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ CharSequence lambda$initHistory$5(TextView textView, int i2, String str) {
        return this.historyList.get(i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initHistory$6(TextView textView, Object obj, int i2) {
        String str = (String) obj;
        this.mLbDelHistoryView.removeLabelFromDatas(str);
        this.historyList.remove(str);
        SharePreferencesUtils.writeStrConfig(CommonParameter.getSchoolSaveSearchHistoryData(), new Gson().toJson(this.historyList), this);
        if (this.historyList.size() == 0) {
            this.mLbDelHistoryView.setVisibility(8);
            this.mLyShowDelAll.setVisibility(8);
            initHistory();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initSearch$8() {
        this.mEtContent.clearFocus();
        this.mEtContent.requestFocus();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$initSearch$9(TextView textView, int i2, KeyEvent keyEvent) {
        if (i2 != 3) {
            return false;
        }
        InputMethodManager inputMethodManager = (InputMethodManager) textView.getContext().getSystemService("input_method");
        if (inputMethodManager.isActive()) {
            inputMethodManager.hideSoftInputFromWindow(textView.getApplicationWindowToken(), 0);
        }
        String string = this.mEtContent.getText().toString();
        TarGetParamBean tarGetParamBean = this.mParams;
        if (tarGetParamBean == null || tarGetParamBean.getPush_type().equals("0") || !string.isEmpty()) {
            if (!TextUtils.isEmpty(this.mWord) && this.mEtContent.getText().toString().equals("")) {
                this.mEtContent.setText(this.mWord);
                this.mEtContent.setSelection(this.mWord.length());
            }
            if (!this.mEtContent.getText().toString().equals("")) {
                this.isClickSearchKey = true;
                SharePreferencesUtils.writeStrConfig("searchCircleKeyWords", this.mEtContent.getText().toString().trim(), this.mContext);
                this.mResultRefresh.autoRefreshAnimationOnly();
                this.page = 1;
                this.mLyShowDelAll.setVisibility(8);
                this.mBtnDelHistory.setVisibility(0);
                this.mLbDelHistoryView.setVisibility(8);
                this.isEditSearch = true;
                getSearchData("0");
                this.mLyResultView.setVisibility(0);
            }
        } else if (isLogin()) {
            PublicMethodActivity.getInstance().mToActivity(new Gson().toJson(this.mParams));
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initTypeData$7(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        this.mTypeAdapter.setSelectPos(i2);
        this.mTypeAdapter.notifyDataSetChanged();
        this.mType = this.mTypeAdapter.getItem(i2).getCode();
        this.page = 1;
        this.mResultRefresh.autoRefreshAnimationOnly();
        getSearchData("1");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$10(View view) {
        if (this.mLyResultView.getVisibility() != 0) {
            finish();
            return;
        }
        this.mEtContent.setText("");
        this.mLyResultView.setVisibility(8);
        this.mContentAdp.getData().clear();
        this.mContentAdp.notifyDataSetChanged();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$11(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) this.mEtContent.getContext().getSystemService("input_method");
        if (inputMethodManager.isActive()) {
            inputMethodManager.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
        }
        String string = this.mEtContent.getText().toString();
        TarGetParamBean tarGetParamBean = this.mParams;
        if (tarGetParamBean == null || tarGetParamBean.getPush_type().equals("0") || !string.isEmpty()) {
            this.isEditSearch = true;
            loadSearchData("0");
        } else if (isLogin()) {
            PublicMethodActivity.getInstance().mToActivity(new Gson().toJson(this.mParams));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$12(ValueAnimator valueAnimator) {
        int iIntValue = ((Integer) valueAnimator.getAnimatedValue()).intValue();
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.mLbHistoryView.getLayoutParams();
        layoutParams.height = iIntValue;
        this.mLbHistoryView.setLayoutParams(layoutParams);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$13(ValueAnimator valueAnimator) {
        this.mImgMoreArrow.setRotation(((Integer) valueAnimator.getAnimatedValue()).intValue());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$14(View view) {
        this.isExpandHistory = !this.isExpandHistory;
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setInterpolator(this.isExpandHistory ? new AccelerateInterpolator() : new AccelerateDecelerateInterpolator());
        animatorSet.setDuration(300L);
        int[] iArr = new int[2];
        boolean z2 = this.isExpandHistory;
        iArr[0] = z2 ? this.mHistoryCollapseHeight : this.mHistoryExpandHeight;
        iArr[1] = !z2 ? this.mHistoryCollapseHeight : this.mHistoryExpandHeight;
        ValueAnimator valueAnimatorOfInt = ValueAnimator.ofInt(iArr);
        valueAnimatorOfInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.psychiatrygarden.activity.chooseSchool.m6
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                this.f11356c.lambda$setListenerForWidget$12(valueAnimator);
            }
        });
        int[] iArr2 = new int[2];
        boolean z3 = this.isExpandHistory;
        iArr2[0] = z3 ? 0 : 180;
        iArr2[1] = z3 ? 180 : 0;
        ValueAnimator valueAnimatorOfInt2 = ValueAnimator.ofInt(iArr2);
        valueAnimatorOfInt2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.psychiatrygarden.activity.chooseSchool.n6
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                this.f11367c.lambda$setListenerForWidget$13(valueAnimator);
            }
        });
        animatorSet.playTogether(valueAnimatorOfInt, valueAnimatorOfInt2);
        animatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.psychiatrygarden.activity.chooseSchool.SearchSchoolOrMajorAct.3
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                SearchSchoolOrMajorAct.this.mTvMoreHistory.setText(SearchSchoolOrMajorAct.this.isExpandHistory ? "收起记录" : "更多记录");
            }
        });
        animatorSet.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$15(View view) {
        this.mBtnDelHistory.setVisibility(8);
        this.mLbDelHistoryView.setVisibility(0);
        this.mLyShowDelAll.setVisibility(0);
        this.mLyShowMore.setVisibility(8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$16(View view) {
        this.historyList.clear();
        SharePreferencesUtils.writeStrConfig(CommonParameter.getSchoolSaveSearchHistoryData(), new Gson().toJson(this.historyList), this);
        initHistory();
        this.isExpandHistory = false;
        this.mLyHistoryView.setVisibility(8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$17(View view) {
        this.mBtnDelHistory.setVisibility(0);
        this.mLyShowDelAll.setVisibility(8);
        this.mLbDelHistoryView.setVisibility(8);
        initHistory();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public /* synthetic */ void lambda$setListenerForWidget$18(com.chad.library.adapter.base.BaseQuickAdapter baseQuickAdapter, View view, int i2) {
        SearchSchoolOrMajorBean searchSchoolOrMajorBean = (SearchSchoolOrMajorBean) this.mContentAdp.getItem(i2);
        if (isLogin()) {
            if (searchSchoolOrMajorBean.getItemType() == 1) {
                SchoolDetailsAct.newIntent(this.mContext, searchSchoolOrMajorBean.getInfo().getSchool_id());
            } else {
                startActivity(SchoolByMajorActivity.newIntent(this.mContext, searchSchoolOrMajorBean.getInfo().getMajor_id()));
            }
        }
    }

    private void loadSearchData(String isHot) {
        if (!TextUtils.isEmpty(this.mWord) && this.mEtContent.getText().toString().equals("")) {
            this.mEtContent.setText(this.mWord);
            this.mEtContent.setSelection(this.mWord.length());
        }
        if (this.mEtContent.getText().toString().equals("")) {
            return;
        }
        this.isClickSearchKey = true;
        SharePreferencesUtils.writeStrConfig("searchCircleKeyWords", this.mEtContent.getText().toString().trim(), this.mContext);
        this.mResultRefresh.autoRefreshAnimationOnly();
        this.page = 1;
        getSearchData(isHot);
        this.mBtnDelHistory.setVisibility(0);
        this.mLbDelHistoryView.setVisibility(8);
        this.mLyShowDelAll.setVisibility(8);
        this.mLyResultView.setVisibility(0);
    }

    public static void newIntent(Context context, int isFromMajor) {
        Intent intent = new Intent(context, (Class<?>) SearchSchoolOrMajorAct.class);
        intent.putExtra("isFromMajor", isFromMajor);
        context.startActivity(intent);
    }

    public void getSearchData(String isEditSearch) {
        if (TextUtils.isEmpty(this.mEtContent.getText().toString())) {
            return;
        }
        addHistoryContent(this.mEtContent.getText().toString());
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put(Constants.PARAMS_CONSTANTS.PARAMS_KEYWORD, this.mEtContent.getText().toString());
        ajaxParams.put(Constants.PARAMS_CONSTANTS.PARAMS_PAGE, "" + this.page);
        ajaxParams.put("page_size", com.tencent.connect.common.Constants.VIA_REPORT_TYPE_WPA_STATE);
        ajaxParams.put("is_target", "0");
        ajaxParams.put("search_type", this.mType);
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.searchSchoolOrMajor, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.chooseSchool.SearchSchoolOrMajorAct.4
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                SearchSchoolOrMajorAct.this.mResultRefresh.finishRefresh();
                if (SearchSchoolOrMajorAct.this.page == 1) {
                    SearchSchoolOrMajorAct.this.emptyView.setLoadFileResUi(SearchSchoolOrMajorAct.this);
                }
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                SearchSchoolOrMajorAct.this.mResultRefresh.finishRefresh();
                super.onSuccess((AnonymousClass4) s2);
                SearchSchoolOrMajorAct.this.mContentAdp.getEmptyLayout().setVisibility(0);
                SearchSchoolOrMajorAct.this.emptyView.showEmptyView();
                try {
                    FollowSchoolBean followSchoolBean = (FollowSchoolBean) new Gson().fromJson(s2, FollowSchoolBean.class);
                    if (!followSchoolBean.getCode().equals("200")) {
                        if (SearchSchoolOrMajorAct.this.page == 1) {
                            SearchSchoolOrMajorAct.this.emptyView.setLoadFileResUi(SearchSchoolOrMajorAct.this);
                            return;
                        }
                        return;
                    }
                    if (followSchoolBean.getData() == null || followSchoolBean.getData().size() <= 0) {
                        if (SearchSchoolOrMajorAct.this.page == 1) {
                            SearchSchoolOrMajorAct.this.mContentAdp.setList(new ArrayList());
                            SearchSchoolOrMajorAct.this.emptyView.uploadEmptyViewResUi();
                        }
                        SearchSchoolOrMajorAct.this.mResultRefresh.finishLoadMoreWithNoMoreData();
                        return;
                    }
                    ArrayList arrayList = new ArrayList();
                    for (FollowSchoolBean.DataBean dataBean : followSchoolBean.getData()) {
                        SearchSchoolOrMajorBean searchSchoolOrMajorBean = new SearchSchoolOrMajorBean();
                        if (SearchSchoolOrMajorAct.this.mType.equals("1")) {
                            searchSchoolOrMajorBean.setType(1);
                        } else if (SearchSchoolOrMajorAct.this.mType.equals("2")) {
                            searchSchoolOrMajorBean.setType(2);
                        }
                        searchSchoolOrMajorBean.setInfo(dataBean);
                        arrayList.add(searchSchoolOrMajorBean);
                    }
                    if (SearchSchoolOrMajorAct.this.page == 1) {
                        SearchSchoolOrMajorAct.this.mContentAdp.setSearchContent(SearchSchoolOrMajorAct.this.mEtContent.getText().toString());
                        SearchSchoolOrMajorAct.this.mContentAdp.setList(arrayList);
                        if (arrayList.size() < 20) {
                            SearchSchoolOrMajorAct.this.mResultRefresh.finishLoadMoreWithNoMoreData();
                            return;
                        }
                        return;
                    }
                    SearchSchoolOrMajorAct.this.mContentAdp.addData((Collection) arrayList);
                    if (arrayList.size() < 20) {
                        SearchSchoolOrMajorAct.this.mResultRefresh.finishLoadMoreWithNoMoreData();
                    } else {
                        SearchSchoolOrMajorAct.this.mResultRefresh.finishLoadMore();
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                    if (SearchSchoolOrMajorAct.this.page == 1) {
                        SearchSchoolOrMajorAct.this.emptyView.setLoadFileResUi(SearchSchoolOrMajorAct.this);
                    }
                }
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.mWord = getIntent().getStringExtra("word");
        this.mParams = (TarGetParamBean) getIntent().getSerializableExtra("params");
        int intExtra = getIntent().getIntExtra("isFromMajor", 0);
        this.isFromSerarchType = intExtra;
        this.mType = intExtra == 0 ? "2" : "1";
        this.mLyView = (RelativeLayout) findViewById(R.id.ly_view);
        this.mBtnBack = (ImageView) findViewById(R.id.iv_actionbar_back);
        this.mLyHistoryView = (LinearLayout) findViewById(R.id.ly_history);
        this.mLbDelHistoryView = (HistoryLabelsView) findViewById(R.id.lb_del_history);
        this.mLyShowMore = (LinearLayout) findViewById(R.id.ly_show_more);
        this.mImgMoreArrow = (ImageView) findViewById(R.id.img_arrow);
        this.mBtnDelHistory = (ImageView) findViewById(R.id.btn_del_history);
        this.mLbHistoryView = (LabelsView) findViewById(R.id.lb_history);
        this.mLbHotWordView = (TagLabelsView) findViewById(R.id.lb_hot_word);
        TextView textView = (TextView) findViewById(R.id.btn_search);
        this.mBtnSearch = textView;
        textView.setTextColor(SkinManager.getCurrentSkinType(this) == 0 ? Color.parseColor("#141516") : Color.parseColor("#7380A9"));
        this.mEtContent = (ClearEditText) findViewById(R.id.ed_search);
        this.mTvMoreHistory = (TextView) findViewById(R.id.tv_more_history);
        this.mLyHotWord = (LinearLayout) findViewById(R.id.ly_hot_word);
        this.mLyResultView = (LinearLayout) findViewById(R.id.ly_result);
        this.mTypeRecycler = (RecyclerView) findViewById(R.id.type_recyclerview);
        this.mResultRefresh = (SmartRefreshLayout) findViewById(R.id.result_refresh);
        this.mResultRecycler = (RecyclerView) findViewById(R.id.result_recyclerview);
        this.mTvFinish = (TextView) findViewById(R.id.tv_finish);
        this.mTvDelAll = (TextView) findViewById(R.id.tv_del_all);
        this.mLyShowDelAll = (LinearLayout) findViewById(R.id.ly_show_del_all);
        this.mEtContent.setHint("请输入关键字");
        ClearEditText clearEditText = this.mEtContent;
        clearEditText.setSelection(clearEditText.getText().toString().length());
        KeyboardInputUtil.showKeyBoard(this.mContext);
        this.mContentAdp = new SearchSchoolOrMajorAdp();
        new ArrayList();
        CustomEmptyView customEmptyView = new CustomEmptyView(this, 0, "");
        this.emptyView = customEmptyView;
        customEmptyView.setOnReloadData(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.t6
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11419c.lambda$init$0(view);
            }
        });
        this.mContentAdp.setEmptyView(this.emptyView);
        this.mContentAdp.getEmptyLayout().setVisibility(8);
        SearchTypeAdp searchTypeAdp = new SearchTypeAdp();
        this.mTypeAdapter = searchTypeAdp;
        this.mTypeRecycler.setAdapter(searchTypeAdp);
        this.mResultRecycler.setAdapter(this.mContentAdp);
        this.mResultRefresh.setOnRefreshLoadMoreListener(this);
        initSearch();
        initHistory();
        initTypeData();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void initwriteStatusBar() {
        super.initwriteStatusBar();
        if (this.mBaseTheme == 0) {
            StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.white_color), 0);
            getWindow().setNavigationBarColor(Color.parseColor("#FBFBFB"));
        } else {
            StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.app_bg_night), 0);
            getWindow().setNavigationBarColor(Color.parseColor("#121622"));
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActionBar.hide();
        initwriteStatusBar();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        this.page++;
        getSearchData("1");
    }

    @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        this.page = 1;
        this.mResultRefresh.autoRefreshAnimationOnly();
        getSearchData("1");
    }

    @Override // android.app.Activity
    public void onRestart() {
        super.onRestart();
        getSearchData("");
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.layout_search_school_major);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.mBtnBack.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.u6
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11428c.lambda$setListenerForWidget$10(view);
            }
        });
        this.mBtnSearch.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.d6
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11242c.lambda$setListenerForWidget$11(view);
            }
        });
        this.mLyShowMore.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.e6
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11252c.lambda$setListenerForWidget$14(view);
            }
        });
        this.mBtnDelHistory.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.f6
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11261c.lambda$setListenerForWidget$15(view);
            }
        });
        this.mTvDelAll.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.g6
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11298c.lambda$setListenerForWidget$16(view);
            }
        });
        this.mTvFinish.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.h6
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11308c.lambda$setListenerForWidget$17(view);
            }
        });
        this.mContentAdp.setOnItemClickListener(new OnItemClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.i6
            @Override // com.chad.library.adapter.base.listener.OnItemClickListener
            public final void onItemClick(com.chad.library.adapter.base.BaseQuickAdapter baseQuickAdapter, View view, int i2) {
                this.f11322c.lambda$setListenerForWidget$18(baseQuickAdapter, view, i2);
            }
        });
    }
}
