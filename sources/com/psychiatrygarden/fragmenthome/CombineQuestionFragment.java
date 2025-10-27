package com.psychiatrygarden.fragmenthome;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.DigitsKeyListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import cn.hutool.core.lang.RegexPool;
import cn.hutool.core.util.RandomUtil;
import com.aliyun.vod.log.core.AliyunLogCommon;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lxj.xpopup.XPopup;
import com.psychiatrygarden.ChooseChapterAct;
import com.psychiatrygarden.activity.online.AnswerSheetActivity;
import com.psychiatrygarden.activity.online.ChartAnswerSheetActivity;
import com.psychiatrygarden.activity.online.bean.QuestionListBean;
import com.psychiatrygarden.activity.vip.Utils.MemInterface;
import com.psychiatrygarden.adapter.QuestionYearFilterAdp;
import com.psychiatrygarden.baseview.BaseFragment;
import com.psychiatrygarden.baseview.ViewHolder;
import com.psychiatrygarden.bean.ChapterOptions;
import com.psychiatrygarden.bean.CombineQuestionBean;
import com.psychiatrygarden.bean.CombineQuestionMode;
import com.psychiatrygarden.bean.CombineQuestionParams;
import com.psychiatrygarden.bean.CombineQuestionSource;
import com.psychiatrygarden.bean.FilterOptions;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.event.BuyVipSuccessEvent;
import com.psychiatrygarden.event.RefreshHomePaperListEvent;
import com.psychiatrygarden.event.TabBuySuccess;
import com.psychiatrygarden.fragmenthome.knowledge.KnowledgeListEditZuTiActivity;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.AliyunEvent;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.ToastUtil;
import com.psychiatrygarden.widget.CombineQuestionLoadingPop;
import com.psychiatrygarden.widget.CustomEmptyView;
import com.psychiatrygarden.widget.LabelsView;
import com.psychiatrygarden.widget.SimpleTextWatcher;
import com.psychiatrygarden.widget.english.PopQuestionYearFilter;
import com.tencent.connect.common.Constants;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class CombineQuestionFragment extends BaseFragment {
    public static final String EXTRA_DATA_LEVEL1_ID = "tabLevel1";
    private ConstraintLayout ctlRenZhi;
    private RelativeLayout ctlYear;
    private CustomEmptyView customerView;
    private EditText etCount;
    private boolean expandSubject;
    private FrameLayout flRoot;
    private boolean hasChapter;
    private boolean hasCognition;
    private boolean hasFrequency;
    private boolean hasInitDefaultParams;
    private boolean hasMode;
    private boolean hasNumber;
    private boolean hasOutLine;
    private boolean hasQuestionType;
    private boolean hasSource;
    private boolean hasYearsData;
    private String identityId;
    private boolean isInit;
    private boolean isVisibleToUser;
    private ImageView ivArrowSubject;
    private FrameLayout llEcSubject;
    private LabelsView lvFrequency;
    private LabelsView lvMode;
    private LabelsView lvOutline;
    private LabelsView lvQuestionType;
    private LabelsView lvRenZhi;
    private LabelsView lvSource;
    private LabelsView lvSubjects;
    private QuestionYearFilterAdp mAdapter;
    private CombineQuestionParams mCombineQuestionParams;
    private ImageView mImgChooseChapter;
    private ImageView mImgChooseChapterRed;
    private CombineQuestionLoadingPop mLoadingPop;
    private ConstraintLayout mLyFrequency;
    private ConstraintLayout mLyOutlines;
    private TextView mTvChooseChapter;
    private TextView mTvCountFour;
    private TextView mTvCountOne;
    private TextView mTvCountThree;
    private TextView mTvCountTwo;
    private String mType;
    private int questionCount;
    private int subjectCollapseHeight;
    private int subjectExpandHeight;
    private String tabType;
    private TextView tvEcSubject;
    private TextView tvQuestionCount;
    private TextView tv_subject;
    private TextView tv_subject_duo_xuan;
    private String mChooseChapterIds = "";
    private boolean chapterChooseAll = false;
    private int filterCount = 0;
    private String level1Id = "";
    private boolean havePermission = false;
    private boolean isKnowledge = false;
    private boolean isCustomerShowToUser = false;
    private String mChooseYearId = "";
    private boolean isPublcStem = false;

    /* renamed from: com.psychiatrygarden.fragmenthome.CombineQuestionFragment$7, reason: invalid class name */
    public class AnonymousClass7 extends AjaxCallBack<String> {
        public AnonymousClass7() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSuccess$0(String str, String str2) {
            CombineQuestionFragment.this.mLoadingPop.dismiss();
            if (CombineQuestionFragment.this.isKnowledge) {
                if (TextUtils.isEmpty(str)) {
                    return;
                }
                ChartAnswerSheetActivity.INSTANCE.startActivity(CombineQuestionFragment.this.requireContext(), str, str2, "", true, CombineQuestionFragment.this.level1Id);
            } else {
                if (TextUtils.isEmpty(str)) {
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putBoolean("fromQuestionCombine", true);
                bundle.putString("question_bank_id", CombineQuestionFragment.this.level1Id);
                bundle.putString("paperId", str);
                if (TextUtils.isEmpty(CombineQuestionFragment.this.mCombineQuestionParams.getTitle())) {
                    bundle.putString("title", str2);
                } else {
                    bundle.putString("title", CombineQuestionFragment.this.mCombineQuestionParams.getTitle());
                }
                AnswerSheetActivity.gotoActivity(CombineQuestionFragment.this.requireContext(), bundle);
            }
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onFailure(Throwable t2, int errorNo, String strMsg) {
            super.onFailure(t2, errorNo, strMsg);
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onSuccess(String s2) {
            super.onSuccess((AnonymousClass7) s2);
            try {
                JSONObject jSONObject = new JSONObject(s2);
                if (!jSONObject.optString("code", "").equals("200")) {
                    String strOptString = jSONObject.optString("message", "");
                    if (TextUtils.isEmpty(strOptString)) {
                        return;
                    }
                    CombineQuestionFragment.this.AlertToast(strOptString);
                    return;
                }
                JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("data");
                if (jSONObjectOptJSONObject != null) {
                    EventBus.getDefault().post(new RefreshHomePaperListEvent());
                    final String strOptString2 = jSONObjectOptJSONObject.optString("paper_id");
                    final String strOptString3 = jSONObjectOptJSONObject.optString("title", "");
                    String strOptString4 = jSONObjectOptJSONObject.optString("actual_question_number");
                    if (strOptString4.matches(RegexPool.NUMBERS)) {
                        String strOptString5 = (!CombineQuestionFragment.this.hasNumber || Integer.parseInt(strOptString4) >= Integer.parseInt(CombineQuestionFragment.this.mCombineQuestionParams.getCount())) ? "" : jSONObjectOptJSONObject.optString(AliyunLogCommon.LogLevel.INFO);
                        CombineQuestionFragment combineQuestionFragment = CombineQuestionFragment.this;
                        combineQuestionFragment.mLoadingPop = (CombineQuestionLoadingPop) new XPopup.Builder(combineQuestionFragment.requireContext()).asCustom(new CombineQuestionLoadingPop(CombineQuestionFragment.this.requireContext(), strOptString5));
                        CombineQuestionFragment.this.mLoadingPop.show();
                        CombineQuestionFragment.this.mLoadingPop.postDelayed(new Runnable() { // from class: com.psychiatrygarden.fragmenthome.l1
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f15798c.lambda$onSuccess$0(strOptString2, strOptString3);
                            }
                        }, ExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS);
                    }
                    AliyunEvent aliyunEvent = AliyunEvent.MakePaper;
                    String key = aliyunEvent.getKey();
                    String value = aliyunEvent.getValue();
                    CommonUtil.addLog(key, value, System.currentTimeMillis() + "", "", "[\"" + strOptString2 + "\"]", "[\"" + strOptString3 + "\"]", "", "2");
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public static /* synthetic */ int access$612(CombineQuestionFragment combineQuestionFragment, int i2) {
        int i3 = combineQuestionFragment.filterCount + i2;
        combineQuestionFragment.filterCount = i3;
        return i3;
    }

    private void getFilterOptionData() {
        if (this.isCustomerShowToUser) {
            showProgressDialog();
        }
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("identity_id", this.identityId);
        ajaxParams.put("type", this.tabType);
        ajaxParams.put("question_category_id", this.level1Id);
        YJYHttpUtils.get(requireContext(), NetworkRequestsURL.combineQuestionFilterOptions, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.fragmenthome.CombineQuestionFragment.5
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                CombineQuestionFragment.this.hideProgressDialog();
                CombineQuestionFragment.this.getViewHolder().get(R.id.tv_start_combine_question).setVisibility(8);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                CombineQuestionFragment.this.hideProgressDialog();
                super.onSuccess((AnonymousClass5) s2);
                try {
                    CombineQuestionBean combineQuestionBean = (CombineQuestionBean) new Gson().fromJson(s2, CombineQuestionBean.class);
                    if (!combineQuestionBean.getCode().equals("200")) {
                        CombineQuestionFragment.this.AlertToast(new JSONObject(s2).optString("message", ""));
                        CombineQuestionFragment.this.getViewHolder().get(R.id.tv_start_combine_question).setVisibility(8);
                        return;
                    }
                    CombineQuestionFragment.this.filterCount = 0;
                    if (combineQuestionBean.getData() != null) {
                        if (CombineQuestionFragment.this.flRoot.getVisibility() != 0) {
                            CombineQuestionFragment.this.flRoot.setVisibility(0);
                        }
                        List<ChapterOptions> chapterList = combineQuestionBean.getData().getChapterList();
                        CombineQuestionFragment.this.hasChapter = (chapterList == null || chapterList.isEmpty()) ? false : true;
                        CombineQuestionFragment.this.hasQuestionType = (combineQuestionBean.getData().getQuestionType() == null || combineQuestionBean.getData().getQuestionType().isEmpty()) ? false : true;
                        CombineQuestionFragment.this.hasSource = (combineQuestionBean.getData().getSourceList() == null || combineQuestionBean.getData().getSourceList().isEmpty()) ? false : true;
                        CombineQuestionFragment.this.hasMode = (combineQuestionBean.getData().getModeList() == null || combineQuestionBean.getData().getModeList().isEmpty()) ? false : true;
                        CombineQuestionFragment.this.hasNumber = (combineQuestionBean.getData().getNumberList() == null || combineQuestionBean.getData().getNumberList().isEmpty()) ? false : true;
                        CombineQuestionFragment.this.hasOutLine = (combineQuestionBean.getData().getOutlineList() == null || combineQuestionBean.getData().getOutlineList().isEmpty()) ? false : true;
                        CombineQuestionFragment.this.hasFrequency = (combineQuestionBean.getData().getFrequencyList() == null || combineQuestionBean.getData().getFrequencyList().isEmpty()) ? false : true;
                        CombineQuestionFragment.this.hasCognition = (combineQuestionBean.getData().getCognitionList() == null || combineQuestionBean.getData().getCognitionList().isEmpty()) ? false : true;
                        if (CombineQuestionFragment.this.hasChapter) {
                            CombineQuestionFragment.this.getViewHolder().get(R.id.ll_subjects).setVisibility(0);
                            CombineQuestionFragment.this.initQuestionSubjects(combineQuestionBean.getData().getChapterList());
                            CombineQuestionFragment.access$612(CombineQuestionFragment.this, 1);
                        } else if (CombineQuestionFragment.this.isKnowledge) {
                            CombineQuestionFragment.this.getViewHolder().get(R.id.ll_subjects).setVisibility(0);
                        } else {
                            CombineQuestionFragment.this.getViewHolder().get(R.id.ll_subjects).setVisibility(8);
                        }
                        List<QuestionListBean.DataDTO.SearchDTO.SearchDataDTO> yearsList = combineQuestionBean.getData().getYearsList();
                        CombineQuestionFragment.this.hasYearsData = (yearsList == null || yearsList.isEmpty()) ? false : true;
                        if (CombineQuestionFragment.this.hasYearsData) {
                            CombineQuestionFragment.access$612(CombineQuestionFragment.this, 1);
                            CombineQuestionFragment.this.ctlYear.setVisibility(0);
                            CombineQuestionFragment.this.initQuestionYears(yearsList);
                        } else {
                            CombineQuestionFragment.this.ctlYear.setVisibility(8);
                        }
                        if (CombineQuestionFragment.this.hasQuestionType) {
                            CombineQuestionFragment.access$612(CombineQuestionFragment.this, 1);
                            CombineQuestionFragment.this.getViewHolder().get(R.id.ctl_question_type).setVisibility(0);
                            CombineQuestionFragment.this.initQuestionType(combineQuestionBean.getData().getQuestionType());
                        } else {
                            CombineQuestionFragment.this.getViewHolder().get(R.id.ctl_question_type).setVisibility(8);
                        }
                        if (CombineQuestionFragment.this.hasSource) {
                            CombineQuestionFragment.access$612(CombineQuestionFragment.this, 1);
                            CombineQuestionFragment.this.getViewHolder().get(R.id.ctl_source).setVisibility(0);
                            CombineQuestionFragment.this.initQuestionSource(combineQuestionBean.getData().getSourceList());
                        } else {
                            CombineQuestionFragment.this.getViewHolder().get(R.id.ctl_source).setVisibility(8);
                        }
                        if (CombineQuestionFragment.this.hasMode) {
                            CombineQuestionFragment.access$612(CombineQuestionFragment.this, 1);
                            CombineQuestionFragment.this.getViewHolder().get(R.id.ctl_mode).setVisibility(0);
                            CombineQuestionFragment.this.initQuestionMode(combineQuestionBean.getData().getModeList());
                        } else {
                            CombineQuestionFragment.this.getViewHolder().get(R.id.ctl_mode).setVisibility(8);
                        }
                        if (CombineQuestionFragment.this.hasOutLine) {
                            CombineQuestionFragment.this.mLyOutlines.setVisibility(0);
                            CombineQuestionFragment.this.initQuestionOutline(combineQuestionBean.getData().getOutlineList());
                            CombineQuestionFragment.access$612(CombineQuestionFragment.this, 1);
                        } else {
                            CombineQuestionFragment.this.mLyOutlines.setVisibility(8);
                        }
                        if (CombineQuestionFragment.this.hasFrequency) {
                            CombineQuestionFragment.this.mLyFrequency.setVisibility(0);
                            CombineQuestionFragment.this.initQuestionFrequency(combineQuestionBean.getData().getFrequencyList());
                            CombineQuestionFragment.access$612(CombineQuestionFragment.this, 1);
                        } else {
                            CombineQuestionFragment.this.mLyFrequency.setVisibility(8);
                        }
                        if (CombineQuestionFragment.this.hasCognition) {
                            CombineQuestionFragment.this.initRenZhi(combineQuestionBean.getData().getCognitionList());
                        } else {
                            CombineQuestionFragment.this.initRenZhi(null);
                        }
                        if (CombineQuestionFragment.this.hasNumber) {
                            CombineQuestionFragment.access$612(CombineQuestionFragment.this, 1);
                            CombineQuestionFragment.this.getViewHolder().get(R.id.ctl_number).setVisibility(0);
                            CombineQuestionFragment.this.initQuestionCount();
                        } else {
                            CombineQuestionFragment.this.getViewHolder().get(R.id.ctl_number).setVisibility(8);
                        }
                        if (!CombineQuestionFragment.this.hasInitDefaultParams) {
                            CombineQuestionFragment.this.initDefaultParamsValue();
                            CombineQuestionFragment.this.hasInitDefaultParams = true;
                        }
                        CombineQuestionFragment.this.isInit = true;
                        CombineQuestionFragment.this.getViewHolder().get(R.id.tv_start_combine_question).setVisibility(CombineQuestionFragment.this.filterCount <= 0 ? 8 : 0);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                    CombineQuestionFragment.this.getViewHolder().get(R.id.tv_start_combine_question).setVisibility(8);
                }
            }
        });
    }

    public static CombineQuestionFragment getInstance(String type, String identityId) {
        CombineQuestionFragment combineQuestionFragment = new CombineQuestionFragment();
        Bundle bundle = new Bundle();
        if (type != null) {
            bundle.putString("type", type);
        }
        if (identityId != null) {
            bundle.putString("identity_id", identityId);
        }
        combineQuestionFragment.setArguments(bundle);
        return combineQuestionFragment;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initDefaultParamsValue() {
        CombineQuestionParams combineQuestionParams = new CombineQuestionParams();
        this.mCombineQuestionParams = combineQuestionParams;
        if (this.hasNumber) {
            if (this.isPublcStem) {
                combineQuestionParams.setCount("5");
            } else {
                combineQuestionParams.setCount(Constants.VIA_REPORT_TYPE_WPA_STATE);
            }
        }
        if (this.hasYearsData) {
            ArrayList arrayList = new ArrayList();
            arrayList.add("0");
            this.mCombineQuestionParams.setYears(arrayList);
        }
        if (this.hasMode) {
            this.mCombineQuestionParams.setMode(CombineQuestionMode.PRACTISE.getType() + "");
        }
        if (TextUtils.equals("error", this.mType)) {
            this.mCombineQuestionParams.setSource(CombineQuestionSource.WRONG.getType() + "");
            return;
        }
        if (TextUtils.equals("collection", this.mType)) {
            this.mCombineQuestionParams.setSource(CombineQuestionSource.COLLECTION.getType() + "");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initQuestionCount() {
        final LinearLayout linearLayout = (LinearLayout) getViewHolder().get(R.id.ll_count);
        linearLayout.getChildAt(0).setSelected(true);
        for (int i2 = 0; i2 < linearLayout.getChildCount(); i2++) {
            linearLayout.getChildAt(i2).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.z0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) throws NumberFormatException {
                    this.f16153c.lambda$initQuestionCount$17(linearLayout, view);
                }
            });
        }
        this.etCount.setKeyListener(DigitsKeyListener.getInstance(RandomUtil.BASE_NUMBER));
        this.etCount.setSingleLine();
        this.etCount.setFilters(new InputFilter[]{new InputFilter.LengthFilter(3)});
        this.etCount.addTextChangedListener(new SimpleTextWatcher() { // from class: com.psychiatrygarden.fragmenthome.CombineQuestionFragment.3
            @Override // com.psychiatrygarden.widget.SimpleTextWatcher, android.text.TextWatcher
            public void afterTextChanged(Editable s2) {
                if (CombineQuestionFragment.this.hasNumber) {
                    if (TextUtils.isEmpty(s2)) {
                        CombineQuestionFragment.this.mCombineQuestionParams.setCount("");
                        return;
                    }
                    for (int i3 = 0; i3 < linearLayout.getChildCount(); i3++) {
                        linearLayout.getChildAt(i3).setSelected(false);
                    }
                    if (s2.toString().matches(RegexPool.NUMBERS)) {
                        CombineQuestionFragment.this.questionCount = Integer.parseInt(s2.toString());
                        if (CombineQuestionFragment.this.isPublcStem) {
                            if (CombineQuestionFragment.this.questionCount <= 0 || CombineQuestionFragment.this.questionCount > 40) {
                                ToastUtil.shortToast(CombineQuestionFragment.this.requireContext(), "题量必须在1到40之间，请重新输入");
                                CombineQuestionFragment.this.etCount.setText("");
                                CombineQuestionFragment.this.mCombineQuestionParams.setCount("");
                            } else {
                                CombineQuestionFragment.this.mCombineQuestionParams.setCount(CombineQuestionFragment.this.questionCount + "");
                            }
                        } else if (CombineQuestionFragment.this.questionCount <= 0 || CombineQuestionFragment.this.questionCount > 200) {
                            ToastUtil.shortToast(CombineQuestionFragment.this.requireContext(), "题量必须在1到200之间，请重新输入");
                            CombineQuestionFragment.this.etCount.setText("");
                            CombineQuestionFragment.this.mCombineQuestionParams.setCount("");
                        } else {
                            CombineQuestionFragment.this.mCombineQuestionParams.setCount(CombineQuestionFragment.this.questionCount + "");
                        }
                    }
                    CombineQuestionFragment.this.etCount.setSelected(true);
                }
            }
        });
        this.etCount.setOnEditorActionListener(new TextView.OnEditorActionListener() { // from class: com.psychiatrygarden.fragmenthome.CombineQuestionFragment.4
            @Override // android.widget.TextView.OnEditorActionListener
            public boolean onEditorAction(TextView v2, int actionId, KeyEvent event) {
                if (actionId != 6 && actionId != 0) {
                    return false;
                }
                ((InputMethodManager) v2.getContext().getSystemService("input_method")).hideSoftInputFromWindow(v2.getWindowToken(), 0);
                String string = CombineQuestionFragment.this.etCount.getText().toString();
                CombineQuestionFragment.this.etCount.clearFocus();
                CombineQuestionFragment.this.etCount.setSelected(true);
                if (TextUtils.isEmpty(string)) {
                    CombineQuestionFragment.this.mCombineQuestionParams.setCount("");
                } else {
                    for (int i3 = 0; i3 < linearLayout.getChildCount(); i3++) {
                        linearLayout.getChildAt(i3).setSelected(false);
                    }
                    if (string.toString().matches(RegexPool.NUMBERS)) {
                        CombineQuestionFragment.this.questionCount = Integer.parseInt(string);
                        if (CombineQuestionFragment.this.isPublcStem) {
                            if (CombineQuestionFragment.this.questionCount <= 0 || CombineQuestionFragment.this.questionCount > 40) {
                                ToastUtil.shortToast(CombineQuestionFragment.this.requireContext(), "题量必须在1到40之间，请重新输入");
                                CombineQuestionFragment.this.etCount.setText("");
                                CombineQuestionFragment.this.mCombineQuestionParams.setCount("");
                            } else {
                                CombineQuestionFragment.this.mCombineQuestionParams.setCount(CombineQuestionFragment.this.questionCount + "");
                            }
                        } else if (CombineQuestionFragment.this.questionCount <= 0 || CombineQuestionFragment.this.questionCount > 200) {
                            ToastUtil.shortToast(CombineQuestionFragment.this.requireContext(), "题量必须在1到200之间，请重新输入");
                            CombineQuestionFragment.this.etCount.setText("");
                            CombineQuestionFragment.this.mCombineQuestionParams.setCount("");
                        } else {
                            CombineQuestionFragment.this.mCombineQuestionParams.setCount(CombineQuestionFragment.this.questionCount + "");
                        }
                    }
                }
                return true;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initQuestionFrequency(final List<FilterOptions> options) {
        this.lvFrequency.setColumns(4);
        this.lvFrequency.setLabels(options, new LabelsView.LabelTextProvider() { // from class: com.psychiatrygarden.fragmenthome.a1
            @Override // com.psychiatrygarden.widget.LabelsView.LabelTextProvider
            public final CharSequence getLabelText(TextView textView, int i2, Object obj) {
                return CombineQuestionFragment.lambda$initQuestionFrequency$22(options, textView, i2, (FilterOptions) obj);
            }
        });
        this.lvFrequency.setOnLabelClickListener(new LabelsView.OnLabelClickListener() { // from class: com.psychiatrygarden.fragmenthome.b1
            @Override // com.psychiatrygarden.widget.LabelsView.OnLabelClickListener
            public final void onLabelClick(TextView textView, Object obj, int i2) {
                this.f15453a.lambda$initQuestionFrequency$23(options, textView, obj, i2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initQuestionMode(final List<FilterOptions> options) {
        this.lvMode.setLabels(options, new LabelsView.LabelTextProvider() { // from class: com.psychiatrygarden.fragmenthome.s0
            @Override // com.psychiatrygarden.widget.LabelsView.LabelTextProvider
            public final CharSequence getLabelText(TextView textView, int i2, Object obj) {
                return CombineQuestionFragment.lambda$initQuestionMode$26(options, textView, i2, (FilterOptions) obj);
            }
        });
        this.lvMode.setOnLabelClickListener(new LabelsView.OnLabelClickListener() { // from class: com.psychiatrygarden.fragmenthome.t0
            @Override // com.psychiatrygarden.widget.LabelsView.OnLabelClickListener
            public final void onLabelClick(TextView textView, Object obj, int i2) {
                this.f16002a.lambda$initQuestionMode$27(options, textView, obj, i2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initQuestionOutline(final List<FilterOptions> options) {
        this.lvOutline.setColumns(4);
        this.lvOutline.setLabels(options, new LabelsView.LabelTextProvider() { // from class: com.psychiatrygarden.fragmenthome.v0
            @Override // com.psychiatrygarden.widget.LabelsView.LabelTextProvider
            public final CharSequence getLabelText(TextView textView, int i2, Object obj) {
                return CombineQuestionFragment.lambda$initQuestionOutline$20(options, textView, i2, (FilterOptions) obj);
            }
        });
        this.lvOutline.setOnLabelClickListener(new LabelsView.OnLabelClickListener() { // from class: com.psychiatrygarden.fragmenthome.w0
            @Override // com.psychiatrygarden.widget.LabelsView.OnLabelClickListener
            public final void onLabelClick(TextView textView, Object obj, int i2) {
                this.f16084a.lambda$initQuestionOutline$21(options, textView, obj, i2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initQuestionSource(final List<FilterOptions> options) {
        this.lvSource.setLabels(options, new LabelsView.LabelTextProvider() { // from class: com.psychiatrygarden.fragmenthome.q0
            @Override // com.psychiatrygarden.widget.LabelsView.LabelTextProvider
            public final CharSequence getLabelText(TextView textView, int i2, Object obj) {
                return CombineQuestionFragment.lambda$initQuestionSource$18(options, textView, i2, (FilterOptions) obj);
            }
        });
        String str = this.mType;
        if (str != null) {
            if (TextUtils.equals("error", str)) {
                for (int i2 = 0; i2 < options.size(); i2++) {
                    if ("1".equals(options.get(i2).getId())) {
                        this.lvSource.setSelects(i2);
                    }
                }
            } else if (TextUtils.equals("collection", this.mType)) {
                for (int i3 = 0; i3 < options.size(); i3++) {
                    if ("2".equals(options.get(i3).getId())) {
                        this.lvSource.setSelects(i3);
                    }
                }
            }
        }
        this.lvSource.setOnLabelClickListener(new LabelsView.OnLabelClickListener() { // from class: com.psychiatrygarden.fragmenthome.r0
            @Override // com.psychiatrygarden.widget.LabelsView.OnLabelClickListener
            public final void onLabelClick(TextView textView, Object obj, int i4) {
                this.f15953a.lambda$initQuestionSource$19(options, textView, obj, i4);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initQuestionSubjects(final List<ChapterOptions> chapterOptions) {
        final ArrayList arrayList = new ArrayList();
        this.lvSubjects.setLabels(chapterOptions, new LabelsView.LabelTextProvider() { // from class: com.psychiatrygarden.fragmenthome.u0
            @Override // com.psychiatrygarden.widget.LabelsView.LabelTextProvider
            public final CharSequence getLabelText(TextView textView, int i2, Object obj) {
                return CombineQuestionFragment.lambda$initQuestionSubjects$6(chapterOptions, textView, i2, (ChapterOptions) obj);
            }
        });
        final int i2 = 0;
        int i3 = 0;
        for (int i4 = 0; i4 < chapterOptions.size(); i4++) {
            ChapterOptions chapterOptions2 = chapterOptions.get(i4);
            if (TextUtils.equals("0", chapterOptions2.getChapterId())) {
                i3 = i4;
            } else if ("0".equals(chapterOptions2.getPass())) {
                i2++;
            }
        }
        if (i2 < chapterOptions.size() - 1) {
            this.lvSubjects.setSelects(i3);
        }
        this.lvSubjects.setOnLabelClickListener(new LabelsView.OnLabelClickListener() { // from class: com.psychiatrygarden.fragmenthome.d1
            @Override // com.psychiatrygarden.widget.LabelsView.OnLabelClickListener
            public final void onLabelClick(TextView textView, Object obj, int i5) {
                this.f15529a.lambda$initQuestionSubjects$10(chapterOptions, arrayList, i2, textView, obj, i5);
            }
        });
        this.lvSubjects.post(new Runnable() { // from class: com.psychiatrygarden.fragmenthome.e1
            @Override // java.lang.Runnable
            public final void run() {
                this.f15556c.lambda$initQuestionSubjects$12();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initQuestionType(final List<FilterOptions> options) {
        final ArrayList arrayList = new ArrayList();
        this.lvQuestionType.setLabels(options, new LabelsView.LabelTextProvider() { // from class: com.psychiatrygarden.fragmenthome.x0
            @Override // com.psychiatrygarden.widget.LabelsView.LabelTextProvider
            public final CharSequence getLabelText(TextView textView, int i2, Object obj) {
                return CombineQuestionFragment.lambda$initQuestionType$15(options, textView, i2, (FilterOptions) obj);
            }
        });
        this.lvQuestionType.setSelects(0);
        this.lvQuestionType.setOnLabelClickListener(new LabelsView.OnLabelClickListener() { // from class: com.psychiatrygarden.fragmenthome.y0
            @Override // com.psychiatrygarden.widget.LabelsView.OnLabelClickListener
            public final void onLabelClick(TextView textView, Object obj, int i2) {
                this.f16127a.lambda$initQuestionType$16(arrayList, options, textView, obj, i2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initQuestionYears(final List<QuestionListBean.DataDTO.SearchDTO.SearchDataDTO> options) {
        options.get(0).setIsSelected(1);
        this.mAdapter.setList(options);
        this.mChooseYearId = options.get(0).getId();
        this.mAdapter.setOnItemClickListener(new OnItemClickListener() { // from class: com.psychiatrygarden.fragmenthome.j0
            @Override // com.chad.library.adapter.base.listener.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
                this.f15678c.lambda$initQuestionYears$14(options, baseQuickAdapter, view, i2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initRenZhi(final List<FilterOptions> options) {
        if (options == null || options.isEmpty()) {
            this.ctlRenZhi.setVisibility(8);
        } else {
            this.ctlRenZhi.setVisibility(0);
        }
        this.lvRenZhi.setColumns(4);
        this.lvRenZhi.setLabels(options, new LabelsView.LabelTextProvider() { // from class: com.psychiatrygarden.fragmenthome.h1
            @Override // com.psychiatrygarden.widget.LabelsView.LabelTextProvider
            public final CharSequence getLabelText(TextView textView, int i2, Object obj) {
                return CombineQuestionFragment.lambda$initRenZhi$24(options, textView, i2, (FilterOptions) obj);
            }
        });
        this.lvRenZhi.setOnLabelClickListener(new LabelsView.OnLabelClickListener() { // from class: com.psychiatrygarden.fragmenthome.i1
            @Override // com.psychiatrygarden.widget.LabelsView.OnLabelClickListener
            public final void onLabelClick(TextView textView, Object obj, int i2) {
                this.f15657a.lambda$initRenZhi$25(options, textView, obj, i2);
            }
        });
    }

    private void initView(ViewHolder holder) {
        this.tv_subject_duo_xuan = (TextView) holder.get(R.id.tv_subject_duo_xuan);
        this.lvRenZhi = (LabelsView) holder.get(R.id.lvRenZhi);
        this.ctlRenZhi = (ConstraintLayout) holder.get(R.id.ctlRenZhi);
        this.tv_subject = (TextView) holder.get(R.id.tv_subject);
        this.tvQuestionCount = (TextView) holder.get(R.id.tvQuestionCount);
        this.mTvChooseChapter = (TextView) holder.get(R.id.tv_choose_chapter);
        if (this.isKnowledge) {
            this.tv_subject.setText("章节");
            this.mTvChooseChapter.setText("按考点选择");
            this.tvQuestionCount.setText("其他");
        } else {
            this.tv_subject.setText("学科");
            this.mTvChooseChapter.setText("按章节选择");
            this.tvQuestionCount.setText("题量");
        }
        EditText editText = (EditText) holder.get(R.id.et_question_title);
        editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(15), new InputFilter() { // from class: com.psychiatrygarden.fragmenthome.j1
            @Override // android.text.InputFilter
            public final CharSequence filter(CharSequence charSequence, int i2, int i3, Spanned spanned, int i4, int i5) {
                return CombineQuestionFragment.lambda$initView$3(charSequence, i2, i3, spanned, i4, i5);
            }
        }});
        editText.addTextChangedListener(new SimpleTextWatcher() { // from class: com.psychiatrygarden.fragmenthome.CombineQuestionFragment.2
            @Override // com.psychiatrygarden.widget.SimpleTextWatcher, android.text.TextWatcher
            public void afterTextChanged(Editable s2) {
                super.afterTextChanged(s2);
                if (CombineQuestionFragment.this.isInit) {
                    if (TextUtils.isEmpty(s2)) {
                        CombineQuestionFragment.this.mCombineQuestionParams.setTitle("");
                    } else {
                        CombineQuestionFragment.this.mCombineQuestionParams.setTitle(s2.toString());
                    }
                }
            }
        });
        this.flRoot = (FrameLayout) holder.get(R.id.fl_root);
        this.tvEcSubject = (TextView) holder.get(R.id.tv_expand_collapse_subject);
        this.llEcSubject = (FrameLayout) holder.get(R.id.ll_ec_subject);
        this.ivArrowSubject = (ImageView) holder.get(R.id.iv_arrow_subject);
        LabelsView labelsView = (LabelsView) holder.get(R.id.lv_subject);
        this.lvSubjects = labelsView;
        if (this.isKnowledge) {
            labelsView.setVisibility(8);
            this.llEcSubject.setVisibility(8);
        }
        this.lvQuestionType = (LabelsView) holder.get(R.id.lv_question_type);
        this.lvSource = (LabelsView) holder.get(R.id.lv_source);
        this.lvOutline = (LabelsView) holder.get(R.id.lv_outline);
        this.lvFrequency = (LabelsView) holder.get(R.id.lv_frequency);
        this.lvMode = (LabelsView) holder.get(R.id.lv_mode);
        this.etCount = (EditText) holder.get(R.id.et_input);
        this.ctlYear = (RelativeLayout) holder.get(R.id.ctl_year);
        this.mLyOutlines = (ConstraintLayout) holder.get(R.id.ly_outlines);
        this.mLyFrequency = (ConstraintLayout) holder.get(R.id.ctl_frequency);
        LinearLayout linearLayout = (LinearLayout) holder.get(R.id.ly_choose_chapter);
        this.mImgChooseChapter = (ImageView) holder.get(R.id.img_choose_chapter);
        this.mImgChooseChapterRed = (ImageView) holder.get(R.id.img_choose_chapter_red);
        this.mTvCountOne = (TextView) holder.get(R.id.tv_count_one);
        this.mTvCountTwo = (TextView) holder.get(R.id.tv_count_two);
        this.mTvCountThree = (TextView) holder.get(R.id.tv_count_three);
        this.mTvCountFour = (TextView) holder.get(R.id.tv_count_four);
        RecyclerView recyclerView = (RecyclerView) holder.get(R.id.yearRecyclerView);
        QuestionYearFilterAdp questionYearFilterAdp = new QuestionYearFilterAdp(R.layout.item_test_creation_question_year);
        this.mAdapter = questionYearFilterAdp;
        recyclerView.setAdapter(questionYearFilterAdp);
        holder.get(R.id.tv_start_combine_question).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.k1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15703c.lambda$initView$4(view);
            }
        });
        linearLayout.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.k0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15702c.lambda$initView$5(view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initQuestionCount$17(LinearLayout linearLayout, View view) throws NumberFormatException {
        int i2;
        ((InputMethodManager) view.getContext().getSystemService("input_method")).hideSoftInputFromWindow(view.getWindowToken(), 0);
        this.etCount.clearFocus();
        this.etCount.setSelected(false);
        this.etCount.setText("");
        if (this.isPublcStem) {
            String string = ((TextView) view).getText().toString();
            i2 = Integer.parseInt(string.substring(0, string.length() - 1));
        } else {
            i2 = Integer.parseInt(((TextView) view).getText().toString());
        }
        this.mCombineQuestionParams.setCount(i2 + "");
        for (int i3 = 0; i3 < linearLayout.getChildCount(); i3++) {
            View childAt = linearLayout.getChildAt(i3);
            childAt.setSelected(childAt.getTag() == view.getTag());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ CharSequence lambda$initQuestionFrequency$22(List list, TextView textView, int i2, FilterOptions filterOptions) {
        return ((FilterOptions) list.get(i2)).getTitle();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initQuestionFrequency$23(List list, TextView textView, Object obj, int i2) {
        this.mCombineQuestionParams.setSource(((FilterOptions) list.get(i2)).getId());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ CharSequence lambda$initQuestionMode$26(List list, TextView textView, int i2, FilterOptions filterOptions) {
        return ((FilterOptions) list.get(i2)).getTitle();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initQuestionMode$27(List list, TextView textView, Object obj, int i2) {
        this.mCombineQuestionParams.setMode(((FilterOptions) list.get(i2)).getId());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ CharSequence lambda$initQuestionOutline$20(List list, TextView textView, int i2, FilterOptions filterOptions) {
        return ((FilterOptions) list.get(i2)).getTitle();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initQuestionOutline$21(List list, TextView textView, Object obj, int i2) {
        this.mCombineQuestionParams.setSource(((FilterOptions) list.get(i2)).getId());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ CharSequence lambda$initQuestionSource$18(List list, TextView textView, int i2, FilterOptions filterOptions) {
        return ((FilterOptions) list.get(i2)).getTitle();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initQuestionSource$19(List list, TextView textView, Object obj, int i2) {
        this.mCombineQuestionParams.setSource(((FilterOptions) list.get(i2)).getId());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initQuestionSubjects$10(final List list, List list2, int i2, TextView textView, Object obj, final int i3) {
        if (!this.isKnowledge) {
            if ("0".equals(((ChapterOptions) list.get(i3)).getPass())) {
                textView.setSelected(false);
                AjaxParams ajaxParams = new AjaxParams();
                ajaxParams.put(ConstantsAPI.WXWebPage.KEY_ACTIVITY_ID, ((ChapterOptions) list.get(i3)).getActivityId());
                MemInterface.getInstance().getMemData(getActivity(), ajaxParams, false, 0);
                MemInterface.getInstance().setmUShareListener(new MemInterface.UShareListener() { // from class: com.psychiatrygarden.fragmenthome.n0
                    @Override // com.psychiatrygarden.activity.vip.Utils.MemInterface.UShareListener
                    public final void mUShareListener() {
                        this.f15857a.lambda$initQuestionSubjects$9(list, i3);
                    }
                });
            }
            List<Integer> selectLabels = this.lvSubjects.getSelectLabels();
            this.lvSubjects.clearAllSelect();
            list2.clear();
            if (selectLabels.isEmpty()) {
                return;
            }
            if (TextUtils.equals("0", ((ChapterOptions) list.get(i3)).getChapterId())) {
                if (i2 == list.size() - 1) {
                    this.lvSubjects.clearAllSelect();
                    return;
                } else {
                    this.lvSubjects.setSelects(i3);
                    return;
                }
            }
            for (Integer num : selectLabels) {
                if (!"0".equals(((ChapterOptions) list.get(num.intValue())).getChapterId())) {
                    list2.add(num);
                }
            }
            this.lvSubjects.setSelects((List<Integer>) list2);
            return;
        }
        if ("0".equals(((ChapterOptions) list.get(i3)).getPass())) {
            textView.setSelected(false);
            AjaxParams ajaxParams2 = new AjaxParams();
            ajaxParams2.put(ConstantsAPI.WXWebPage.KEY_ACTIVITY_ID, ((ChapterOptions) list.get(i3)).getActivityId());
            ajaxParams2.put("alwaysShow", "1");
            MemInterface.getInstance().getMemData((Activity) getActivity(), ajaxParams2, NetworkRequestsURL.vipApi, 0, false);
            MemInterface.getInstance().setmUShareListener(new MemInterface.UShareListener() { // from class: com.psychiatrygarden.fragmenthome.l0
                @Override // com.psychiatrygarden.activity.vip.Utils.MemInterface.UShareListener
                public final void mUShareListener() {
                    this.f15795a.lambda$initQuestionSubjects$7(list, i3);
                }
            });
            MemInterface.getInstance().setShareSuccessListener(new MemInterface.ShareSuccessListener() { // from class: com.psychiatrygarden.fragmenthome.m0
                @Override // com.psychiatrygarden.activity.vip.Utils.MemInterface.ShareSuccessListener
                public final void shareSuccess(String str) {
                    this.f15819a.lambda$initQuestionSubjects$8(list, i3, str);
                }
            });
        }
        List<Integer> selectLabels2 = this.lvSubjects.getSelectLabels();
        this.lvSubjects.clearAllSelect();
        list2.clear();
        if (selectLabels2.isEmpty()) {
            return;
        }
        if (TextUtils.equals("0", ((ChapterOptions) list.get(i3)).getChapterId())) {
            if (i2 == list.size() - 1) {
                this.lvSubjects.clearAllSelect();
                return;
            } else {
                this.lvSubjects.setSelects(i3);
                return;
            }
        }
        for (Integer num2 : selectLabels2) {
            if (!"0".equals(((ChapterOptions) list.get(num2.intValue())).getChapterId())) {
                list2.add(num2);
            }
        }
        this.lvSubjects.setSelects((List<Integer>) list2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initQuestionSubjects$11(int i2) {
        this.subjectCollapseHeight = this.lvSubjects.getMeasuredHeight();
        this.lvSubjects.setMaxLines(i2);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.lvSubjects.getLayoutParams();
        layoutParams.height = this.subjectCollapseHeight;
        this.lvSubjects.setLayoutParams(layoutParams);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initQuestionSubjects$12() {
        this.subjectExpandHeight = this.lvSubjects.getMeasuredHeight();
        final int lines = this.lvSubjects.getLines();
        if (this.lvSubjects.getLines() <= 5) {
            this.llEcSubject.setVisibility(8);
        } else {
            this.lvSubjects.setMaxLines(5);
            this.lvSubjects.post(new Runnable() { // from class: com.psychiatrygarden.fragmenthome.p0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f15902c.lambda$initQuestionSubjects$11(lines);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ CharSequence lambda$initQuestionSubjects$6(List list, TextView textView, int i2, ChapterOptions chapterOptions) {
        return ((ChapterOptions) list.get(i2)).getTitle();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initQuestionSubjects$7(List list, int i2) {
        ((ChapterOptions) list.get(i2)).setPass("1");
        this.lvSubjects.clearAllSelect();
        initQuestionSubjects(list);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initQuestionSubjects$8(List list, int i2, String str) {
        ((ChapterOptions) list.get(i2)).setPass("1");
        this.lvSubjects.clearAllSelect();
        initQuestionSubjects(list);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initQuestionSubjects$9(List list, int i2) {
        ((ChapterOptions) list.get(i2)).setPass("1");
        this.lvSubjects.clearAllSelect();
        initQuestionSubjects(list);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ CharSequence lambda$initQuestionType$15(List list, TextView textView, int i2, FilterOptions filterOptions) {
        return ((FilterOptions) list.get(i2)).getTitle();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initQuestionType$16(List list, List list2, TextView textView, Object obj, int i2) {
        list.clear();
        List<Integer> selectLabels = this.lvQuestionType.getSelectLabels();
        this.lvQuestionType.clearAllSelect();
        if (selectLabels.isEmpty()) {
            this.isPublcStem = false;
            return;
        }
        if (TextUtils.equals("0", ((FilterOptions) list2.get(i2)).getId())) {
            this.lvQuestionType.setSelects(i2);
            this.isPublcStem = false;
            setCount();
            return;
        }
        for (Integer num : selectLabels) {
            if (!TextUtils.equals("0", ((FilterOptions) list2.get(num.intValue())).getId())) {
                list.add(num);
            }
        }
        this.lvQuestionType.setSelects((List<Integer>) list);
        Log.e("selected_type", "选中题型：" + list);
        List<FilterOptions> selectLabelDatas = this.lvQuestionType.getSelectLabelDatas();
        ArrayList arrayList = new ArrayList();
        for (FilterOptions filterOptions : selectLabelDatas) {
            if (filterOptions.getId().equals(Constants.VIA_SHARE_TYPE_PUBLISHVIDEO) || filterOptions.getId().equals(Constants.VIA_REPORT_TYPE_JOININ_GROUP) || filterOptions.getId().equals(Constants.VIA_REPORT_TYPE_MAKE_FRIEND) || filterOptions.getId().equals(Constants.VIA_REPORT_TYPE_WPA_STATE)) {
                arrayList.add(filterOptions.getId());
            }
        }
        if (arrayList.size() == selectLabelDatas.size()) {
            this.isPublcStem = true;
        } else {
            this.isPublcStem = false;
        }
        setCount();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initQuestionYears$13(List list, int i2, QuestionListBean.DataDTO.SearchDTO.SearchDataDTO searchDataDTO, QuestionListBean.DataDTO.SearchDTO.SearchDataDTO searchDataDTO2) {
        for (int i3 = 0; i3 < list.size(); i3++) {
            ((QuestionListBean.DataDTO.SearchDTO.SearchDataDTO) list.get(i3)).setIsSelected(0);
        }
        ((QuestionListBean.DataDTO.SearchDTO.SearchDataDTO) list.get(i2)).setIsSelected(1);
        ((QuestionListBean.DataDTO.SearchDTO.SearchDataDTO) list.get(i2)).setYearTitle(searchDataDTO2.getYearTitle());
        searchDataDTO.setIsSelected(1);
        searchDataDTO.setYearTitle(searchDataDTO2.getYearTitle());
        this.mChooseYearId = searchDataDTO2.getYearTitle();
        this.mAdapter.notifyDataSetChanged();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initQuestionYears$14(final List list, BaseQuickAdapter baseQuickAdapter, View view, final int i2) {
        final QuestionListBean.DataDTO.SearchDTO.SearchDataDTO item = this.mAdapter.getItem(i2);
        if (item.getId().equals("free_year")) {
            new XPopup.Builder(getActivity()).autoDismiss(Boolean.FALSE).asCustom(new PopQuestionYearFilter(getActivity(), item, new PopQuestionYearFilter.OnClickBtnListener() { // from class: com.psychiatrygarden.fragmenthome.c1
                @Override // com.psychiatrygarden.widget.english.PopQuestionYearFilter.OnClickBtnListener
                public final void onChooseYear(QuestionListBean.DataDTO.SearchDTO.SearchDataDTO searchDataDTO) {
                    this.f15488a.lambda$initQuestionYears$13(list, i2, item, searchDataDTO);
                }
            })).show();
            return;
        }
        for (int i3 = 0; i3 < list.size(); i3++) {
            ((QuestionListBean.DataDTO.SearchDTO.SearchDataDTO) list.get(i3)).setYearTitle("");
            if (((QuestionListBean.DataDTO.SearchDTO.SearchDataDTO) list.get(i3)).getId().equals(item.getId())) {
                ((QuestionListBean.DataDTO.SearchDTO.SearchDataDTO) list.get(i3)).setIsSelected(1);
            } else {
                ((QuestionListBean.DataDTO.SearchDTO.SearchDataDTO) list.get(i3)).setIsSelected(0);
            }
        }
        this.mChooseYearId = item.getId();
        this.mAdapter.notifyDataSetChanged();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ CharSequence lambda$initRenZhi$24(List list, TextView textView, int i2, FilterOptions filterOptions) {
        return ((FilterOptions) list.get(i2)).getTitle();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initRenZhi$25(List list, TextView textView, Object obj, int i2) {
        this.mCombineQuestionParams.setSource(((FilterOptions) list.get(i2)).getId());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ CharSequence lambda$initView$3(CharSequence charSequence, int i2, int i3, Spanned spanned, int i4, int i5) {
        while (i2 < i3) {
            if (Character.isWhitespace(charSequence.charAt(i2))) {
                return "";
            }
            i2++;
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$4(View view) {
        submitCombinationQuestion();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$5(View view) {
        if (this.isKnowledge) {
            Intent intent = new Intent(this.mContext, (Class<?>) KnowledgeListEditZuTiActivity.class);
            intent.putExtra("treeId", this.identityId);
            intent.putExtra("listData", this.mChooseChapterIds);
            startActivityForResult(intent, 1001);
            return;
        }
        Intent intent2 = new Intent(this.mContext, (Class<?>) ChooseChapterAct.class);
        intent2.putExtra("identityId", this.identityId);
        intent2.putExtra("chapterIds", this.mChooseChapterIds);
        intent2.putExtra("isChooseAll", this.chapterChooseAll);
        startActivityForResult(intent2, 1001);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$0(ValueAnimator valueAnimator) {
        int iIntValue = ((Integer) valueAnimator.getAnimatedValue()).intValue();
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.lvSubjects.getLayoutParams();
        layoutParams.height = iIntValue;
        this.lvSubjects.setLayoutParams(layoutParams);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$1(ValueAnimator valueAnimator) {
        this.ivArrowSubject.setRotation(((Integer) valueAnimator.getAnimatedValue()).intValue());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$2(View view) {
        this.expandSubject = !this.expandSubject;
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setInterpolator(this.expandSubject ? new AccelerateInterpolator() : new AccelerateDecelerateInterpolator());
        animatorSet.setDuration(300L);
        int[] iArr = new int[2];
        boolean z2 = this.expandSubject;
        iArr[0] = z2 ? this.subjectCollapseHeight : this.subjectExpandHeight;
        iArr[1] = !z2 ? this.subjectCollapseHeight : this.subjectExpandHeight;
        ValueAnimator valueAnimatorOfInt = ValueAnimator.ofInt(iArr);
        valueAnimatorOfInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.psychiatrygarden.fragmenthome.f1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                this.f15581c.lambda$initViews$0(valueAnimator);
            }
        });
        int[] iArr2 = new int[2];
        boolean z3 = this.expandSubject;
        iArr2[0] = z3 ? 0 : 180;
        iArr2[1] = z3 ? 180 : 0;
        ValueAnimator valueAnimatorOfInt2 = ValueAnimator.ofInt(iArr2);
        valueAnimatorOfInt2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.psychiatrygarden.fragmenthome.g1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                this.f15607c.lambda$initViews$1(valueAnimator);
            }
        });
        animatorSet.playTogether(valueAnimatorOfInt, valueAnimatorOfInt2);
        animatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.psychiatrygarden.fragmenthome.CombineQuestionFragment.1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                CombineQuestionFragment.this.tvEcSubject.setText(CombineQuestionFragment.this.expandSubject ? "收起" : "展开更多");
            }
        });
        animatorSet.start();
    }

    private void setCount() {
        if (this.isKnowledge) {
            this.isPublcStem = false;
        }
        if (this.isPublcStem) {
            this.mTvCountOne.setText("5组");
            this.mTvCountTwo.setText("10组");
            this.mTvCountThree.setText("15组");
            this.mTvCountFour.setText("20组");
            return;
        }
        this.mTvCountOne.setText(Constants.VIA_REPORT_TYPE_WPA_STATE);
        this.mTvCountTwo.setText("30");
        this.mTvCountThree.setText("50");
        this.mTvCountFour.setText("100");
    }

    private void submitCombinationQuestion() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("type", this.tabType);
        ajaxParams.put("question_category_id", this.level1Id);
        if (this.isKnowledge) {
            if (TextUtils.isEmpty(this.mChooseChapterIds)) {
                ajaxParams.put("is_cate", "1");
            } else {
                ajaxParams.put("is_cate", "0");
            }
            ajaxParams.put("identity_id", SharePreferencesUtils.readStrConfig(CommonParameter.identity_id, requireContext(), ""));
            ajaxParams.put("user_id", UserConfig.getUserId());
            ajaxParams.put("app_id", SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, requireContext(), "1"));
            ajaxParams.put("part_id", this.identityId);
        } else {
            ajaxParams.put("identity_id", this.identityId);
            ajaxParams.put("user_id", UserConfig.getUserId());
            ajaxParams.put("app_id", SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, requireContext(), "1"));
        }
        if (TextUtils.isEmpty(this.mCombineQuestionParams.getTitle())) {
            AlertToast("请输入组题名称~");
            return;
        }
        if (this.mCombineQuestionParams.getTitle().length() < 2 || this.mCombineQuestionParams.getTitle().length() > 15) {
            ToastUtil.shortToast(requireContext(), "组题名称的长度必须在2-15个字之间");
            return;
        }
        if (this.mCombineQuestionParams.getTitle().matches("\\s+$")) {
            ToastUtil.shortToast(requireContext(), "请输入正确的组题名称~");
            return;
        }
        ajaxParams.put("title", this.mCombineQuestionParams.getTitle());
        if (this.hasChapter || this.isKnowledge) {
            List selectLabelDatas = this.lvSubjects.getSelectLabelDatas();
            StringBuilder sb = new StringBuilder();
            if (selectLabelDatas.size() != 1) {
                int i2 = 0;
                while (i2 < selectLabelDatas.size()) {
                    if ("1".equals(((ChapterOptions) selectLabelDatas.get(i2)).getPass())) {
                        sb.append(((ChapterOptions) selectLabelDatas.get(i2)).getChapterId());
                        sb.append(i2 == selectLabelDatas.size() - 1 ? "" : ",");
                    }
                    i2++;
                }
            } else if ("0".equals(((ChapterOptions) selectLabelDatas.get(0)).getChapterId())) {
                List labels = this.lvSubjects.getLabels();
                int i3 = 0;
                while (i3 < labels.size()) {
                    if ("1".equals(((ChapterOptions) labels.get(i3)).getPass()) && !"0".equals(((ChapterOptions) labels.get(i3)).getChapterId())) {
                        sb.append(((ChapterOptions) labels.get(i3)).getChapterId());
                        sb.append(i3 == labels.size() - 1 ? "" : ",");
                    }
                    i3++;
                }
            } else if ("1".equals(((ChapterOptions) selectLabelDatas.get(0)).getPass())) {
                sb.append(((ChapterOptions) selectLabelDatas.get(0)).getChapterId());
            }
            if (TextUtils.isEmpty(this.mChooseChapterIds) && sb.toString().equals("")) {
                ToastUtil.shortToast(getActivity(), "请选择章节");
                return;
            }
            if (TextUtils.isEmpty(this.mChooseChapterIds)) {
                ajaxParams.put("chapter_id", sb.toString());
            } else {
                Iterator it = ((List) new Gson().fromJson(this.mChooseChapterIds, new TypeToken<List<String>>() { // from class: com.psychiatrygarden.fragmenthome.CombineQuestionFragment.6
                }.getType())).iterator();
                String str = "";
                while (it.hasNext()) {
                    str = str + ((String) it.next()) + ",";
                }
                ajaxParams.put("chapter_id", str.substring(0, str.length() - 1));
            }
        }
        if (this.hasYearsData) {
            ajaxParams.put("year", this.mChooseYearId);
        }
        if (this.hasQuestionType) {
            List selectLabelDatas2 = this.lvQuestionType.getSelectLabelDatas();
            if (!selectLabelDatas2.isEmpty()) {
                if (selectLabelDatas2.size() == 1) {
                    ajaxParams.put("question_type", ((FilterOptions) selectLabelDatas2.get(0)).getId());
                } else {
                    StringBuilder sb2 = new StringBuilder();
                    int i4 = 0;
                    while (i4 < selectLabelDatas2.size()) {
                        sb2.append(((FilterOptions) selectLabelDatas2.get(i4)).getId());
                        sb2.append(i4 == selectLabelDatas2.size() - 1 ? "" : ",");
                        i4++;
                    }
                    ajaxParams.put("question_type", sb2.toString());
                }
            }
        }
        if (this.hasSource) {
            List selectLabelDatas3 = this.lvSource.getSelectLabelDatas();
            if (!selectLabelDatas3.isEmpty()) {
                ajaxParams.put("question_source", ((FilterOptions) selectLabelDatas3.get(0)).getId());
            }
        }
        if (this.hasNumber) {
            if (TextUtils.isEmpty(this.mCombineQuestionParams.getCount()) || !this.mCombineQuestionParams.getCount().matches(RegexPool.NUMBERS)) {
                AlertToast("请输入或者选择正确的题量~");
                return;
            }
            ajaxParams.put("user_question_number", this.mCombineQuestionParams.getCount());
        }
        List selectLabelDatas4 = this.lvOutline.getSelectLabelDatas();
        if (!selectLabelDatas4.isEmpty()) {
            ajaxParams.put("outlines_mastery", ((FilterOptions) selectLabelDatas4.get(0)).getId());
        }
        List selectLabelDatas5 = this.lvFrequency.getSelectLabelDatas();
        if (!selectLabelDatas5.isEmpty()) {
            ajaxParams.put("frequency", ((FilterOptions) selectLabelDatas5.get(0)).getId());
        }
        List selectLabelDatas6 = this.lvRenZhi.getSelectLabelDatas();
        if (!selectLabelDatas6.isEmpty()) {
            ajaxParams.put("cognition", ((FilterOptions) selectLabelDatas6.get(0)).getId());
        }
        if (this.hasMode) {
            ajaxParams.put("mode", this.mCombineQuestionParams.getMode());
        }
        YJYHttpUtils.post(requireContext(), NetworkRequestsURL.combineQuestion, ajaxParams, new AnonymousClass7());
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public int getLayoutId() {
        return R.layout.fragment_combine_question;
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void initViews(ViewHolder holder, View root) {
        CustomEmptyView customEmptyView = (CustomEmptyView) holder.get(R.id.customerView);
        this.customerView = customEmptyView;
        customEmptyView.uploadEmptyViewResUi();
        this.mLoadingPop = (CombineQuestionLoadingPop) new XPopup.Builder(requireContext()).asCustom(new CombineQuestionLoadingPop(requireContext(), ""));
        if (getArguments() != null) {
            this.mType = getArguments().getString("type", null);
            this.identityId = getArguments().getString("identity_id", null);
            this.tabType = getArguments().getString("tabType", null);
            this.level1Id = getArguments().getString(EXTRA_DATA_LEVEL1_ID, null);
            this.havePermission = getArguments().getBoolean("havePermission", false);
            this.isKnowledge = "1".equals(this.tabType);
        }
        this.customerView.setVisibility(8);
        initView(holder);
        this.llEcSubject.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.o0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15883c.lambda$initViews$2(view);
            }
        });
    }

    @Override // androidx.fragment.app.Fragment
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1001 && resultCode == -1 && data != null) {
            String stringExtra = data.getStringExtra("chooseChapterId");
            int intExtra = data.getIntExtra("chapterCount", 0);
            this.chapterChooseAll = data.getBooleanExtra("isChooseAll", false);
            if (intExtra <= 0) {
                this.mChooseChapterIds = "";
                this.lvSubjects.setVisibility(this.isKnowledge ? 8 : 0);
                this.mImgChooseChapterRed.setVisibility(8);
                this.mImgChooseChapter.setVisibility(0);
                if (!this.isKnowledge && this.lvSubjects.getLines() > 5) {
                    this.llEcSubject.setVisibility(0);
                }
                this.mTvChooseChapter.setText(this.isKnowledge ? "按考点选择" : "按章节选择");
                this.mTvChooseChapter.setTextColor(SkinManager.getThemeColor(getActivity(), R.attr.forth_txt_color));
                return;
            }
            this.lvSubjects.setVisibility(8);
            this.mChooseChapterIds = stringExtra;
            this.mImgChooseChapterRed.setVisibility(0);
            this.mImgChooseChapter.setVisibility(8);
            if (this.isKnowledge) {
                this.mTvChooseChapter.setText("已选" + intExtra + "个考点");
            } else {
                this.mTvChooseChapter.setText("已选" + intExtra + "个章节");
            }
            this.llEcSubject.setVisibility(8);
            this.mTvChooseChapter.setTextColor(SkinManager.getThemeColor(getActivity(), R.attr.main_theme_color));
        }
    }

    @Subscribe
    public void onEventMainThread(BuyVipSuccessEvent event) {
        if (event.getSuccess() && this.isVisibleToUser) {
            Iterator it = this.lvSubjects.getLabels().iterator();
            int i2 = 0;
            while (it.hasNext()) {
                if ("0".equals(((ChapterOptions) it.next()).getPass())) {
                    i2++;
                }
            }
            if (i2 > 0) {
                getFilterOptionData();
            }
        }
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportFragment, cn.webdemo.com.supporfragment.ISupportFragment
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportFragment, androidx.fragment.app.Fragment
    public void onPause() {
        super.onPause();
        this.isVisibleToUser = false;
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportFragment, androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        this.isVisibleToUser = true;
        if (this.isInit || this.identityId == null) {
            return;
        }
        getFilterOptionData();
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportFragment, androidx.fragment.app.Fragment
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isCustomerShowToUser = isVisibleToUser;
    }

    @Subscribe
    public void onEventMainThread(TabBuySuccess event) {
        CustomEmptyView customEmptyView;
        if (!event.getId().equals(this.identityId) || (customEmptyView = this.customerView) == null) {
            return;
        }
        customEmptyView.setVisibility(8);
        if (this.identityId != null) {
            getFilterOptionData();
        }
    }
}
