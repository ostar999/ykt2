package com.psychiatrygarden.activity;

import android.animation.ValueAnimator;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.CountDownTimer;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;
import cn.hutool.core.lang.RegexPool;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.lxj.xpopup.core.PopupInfo;
import com.lxj.xpopup.enums.PopupAnimation;
import com.lxj.xpopup.interfaces.SimpleCallback;
import com.makeramen.roundedimageview.RoundedImageView;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.ComputerMockTestAct;
import com.psychiatrygarden.activity.online.adapter.ComputerAnswerSheetAdp;
import com.psychiatrygarden.activity.vip.Utils.MemInterface;
import com.psychiatrygarden.adapter.ComputerOptionsAdp;
import com.psychiatrygarden.bean.AnswerBean;
import com.psychiatrygarden.bean.ExesQuestionBean;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.DesUtil;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.utils.ImageLoaderUtilsCustom;
import com.psychiatrygarden.utils.LogUtils;
import com.psychiatrygarden.utils.ScreenUtil;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.StatusBarUtil;
import com.psychiatrygarden.utils.ToastUtil;
import com.psychiatrygarden.widget.ComputerGuideDialog;
import com.psychiatrygarden.widget.ComputerNextDialog;
import com.psychiatrygarden.widget.ExamTimeHintPop;
import com.psychiatrygarden.widget.ImageViewerPopupViewCustom;
import com.psychiatrygarden.widget.MaxRecyclerView;
import com.tencent.connect.common.Constants;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class ComputerMockTestAct extends BaseActivity {
    private TimerCount countDownTimer;
    private String dateEnd;
    private long durationMinute;
    private String file;
    private boolean isHideLeftMenu;
    private TextView mBtnBiaoDoubt;
    private TextView mBtnFinish;
    private TextView mBtnMore;
    private TextView mBtnNext;
    private TextView mBtnNextStage;
    private TextView mBtnPrevious;
    private TextView mBtnToBiaoDoubt;
    private TextView mBtnToNoAnswer;
    private ExamTimeHintPop mHintPop;
    private ImageView mImgArrow;
    private ImageView mImgBack;
    private RoundedImageView mImgTitle;
    private NestedScrollView mLeftScrollView;
    private LinearLayout mLyArrow;
    private LinearLayout mLyRight;
    private LinearLayout mLyTypeView;
    private ComputerOptionsAdp mOptionAdp;
    private MaxRecyclerView mOptionRecycler;
    private View mPublicLine;
    private NestedScrollView mPublicScroll;
    private ComputerAnswerSheetAdp mSheetAdp;
    private RecyclerView mSheetRecycler;
    private TextView mTvBiaoDoubtCount;
    private TextView mTvMockName;
    private TextView mTvNoAnswerCount;
    private TextView mTvPublicTitle;
    private TextView mTvQuestionTips;
    private TextView mTvQuestionTitle;
    private TextView mTvQuestionType;
    private TextView mTvTestTime;
    private TextView mTvTimeFive;
    private TextView mTvTimeFour;
    private TextView mTvTimeOne;
    private TextView mTvTimeSix;
    private TextView mTvTimeThree;
    private TextView mTvTimeTwo;
    private long startTime;
    private int submitMinute;
    private String title;
    private int mCurrentPosition = 0;
    private int mTempCurrentPosition = 0;
    private List<ExesQuestionBean> mTempDataList = new ArrayList();
    private Map<String, List<ExesQuestionBean>> mGroupList = new LinkedHashMap();
    private List<String> mTypeList = new ArrayList();
    private int mCurrentStageIndex = 0;
    private List<List<ExesQuestionBean>> mList = new ArrayList();
    private int mSelectedGroupIndex = 0;
    private int mNoAnswerQuestionCount = 0;
    private int mBiaoDoubtQuestionCount = 0;
    private int mBtnClickType = 0;
    private List<ExesQuestionBean> mTempCurrentStagetList = new ArrayList();
    private List<ExesQuestionBean> mTempHandleDataList = new ArrayList();
    private int mLeftWidth = 199;
    private long startDoTime = 0;

    /* renamed from: com.psychiatrygarden.activity.ComputerMockTestAct$3, reason: invalid class name */
    public class AnonymousClass3 implements ViewTreeObserver.OnGlobalLayoutListener {
        public AnonymousClass3() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onGlobalLayout$0() {
            ComputerMockTestAct.this.mBtnMore.setVisibility(0);
        }

        @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
        public void onGlobalLayout() {
            if (ComputerMockTestAct.this.mTvQuestionTips.getLineCount() > 1) {
                ComputerMockTestAct.this.mTvQuestionTips.setSingleLine(true);
                ComputerMockTestAct.this.mTvQuestionTips.setEllipsize(TextUtils.TruncateAt.END);
                ComputerMockTestAct.this.mBtnMore.postDelayed(new Runnable() { // from class: com.psychiatrygarden.activity.n5
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f13037c.lambda$onGlobalLayout$0();
                    }
                }, 100L);
            } else {
                ComputerMockTestAct.this.mBtnMore.setVisibility(8);
            }
            ComputerMockTestAct.this.mTvQuestionTips.getViewTreeObserver().removeOnGlobalLayoutListener(this);
        }
    }

    /* renamed from: com.psychiatrygarden.activity.ComputerMockTestAct$6, reason: invalid class name */
    public class AnonymousClass6 implements ComputerNextDialog.ClickIml {
        public AnonymousClass6() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$mClickIml$0(ObservableEmitter observableEmitter) throws Exception {
            observableEmitter.onNext(CommonUtil.getNetTime());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$mClickIml$1(Date date) throws Exception {
            ComputerMockTestAct.this.lambda$getScore$16(date);
        }

        @Override // com.psychiatrygarden.widget.ComputerNextDialog.ClickIml
        public void mClickIml() {
            if (ComputerMockTestAct.this.isLogin()) {
                Observable.create(new ObservableOnSubscribe() { // from class: com.psychiatrygarden.activity.o5
                    @Override // io.reactivex.ObservableOnSubscribe
                    public final void subscribe(ObservableEmitter observableEmitter) throws Exception {
                        ComputerMockTestAct.AnonymousClass6.lambda$mClickIml$0(observableEmitter);
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.psychiatrygarden.activity.p5
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj) throws Exception {
                        this.f13532c.lambda$mClickIml$1((Date) obj);
                    }
                });
            }
        }

        @Override // com.psychiatrygarden.widget.ComputerNextDialog.ClickIml
        public void mClickLeftIml() {
        }
    }

    public class TimerCount extends CountDownTimer {
        public TimerCount(long millisInfuture, long countDownInterval) {
            super(millisInfuture, countDownInterval);
        }

        @Override // android.os.CountDownTimer
        public void onFinish() {
            ComputerMockTestAct.this.updateTime(0L);
            ComputerMockTestAct.this.getScore(2);
        }

        @Override // android.os.CountDownTimer
        public void onTick(long millisUntilFinished) {
            ComputerMockTestAct.this.updateTime(millisUntilFinished);
        }
    }

    public static /* synthetic */ int access$708(ComputerMockTestAct computerMockTestAct) {
        int i2 = computerMockTestAct.mCurrentStageIndex;
        computerMockTestAct.mCurrentStageIndex = i2 + 1;
        return i2;
    }

    private void closeDialog() {
        new XPopup.Builder(this.mContext).asCustom(new ComputerNextDialog(this.mContext, new ComputerNextDialog.ClickIml() { // from class: com.psychiatrygarden.activity.ComputerMockTestAct.9
            @Override // com.psychiatrygarden.widget.ComputerNextDialog.ClickIml
            public void mClickIml() {
                SharePreferencesUtils.writeBooleanConfig(CommonParameter.isAddMock, false, ComputerMockTestAct.this.mContext);
                ProjectApp.questExamDataList.clear();
                ProjectApp.questExamList.clear();
                EventBus.getDefault().post("closePage");
                ComputerMockTestAct.this.finish();
            }

            @Override // com.psychiatrygarden.widget.ComputerNextDialog.ClickIml
            public void mClickLeftIml() {
            }
        }, new SpannableStringBuilder("返回将退出此次考试，是否退出？"), "取消", "确定")).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void firtSHowGuide() {
        if (SharePreferencesUtils.readBooleanConfig("isShowedComputerGuide", false, this)) {
            return;
        }
        new XPopup.Builder(this).dismissOnTouchOutside(Boolean.FALSE).popupAnimation(PopupAnimation.ScaleAlphaFromCenter).asCustom(new ComputerGuideDialog(this.mContext, !TextUtils.isEmpty(this.mSheetAdp.getData().get(0).getPublic_number()), new ComputerGuideDialog.ProjectChoosedInterface() { // from class: com.psychiatrygarden.activity.u4
            @Override // com.psychiatrygarden.widget.ComputerGuideDialog.ProjectChoosedInterface
            public final void mItemLinsenter() {
                this.f13975a.lambda$firtSHowGuide$1();
            }
        })).show();
    }

    private int getKeyPosition(String key) {
        Iterator<Map.Entry<String, List<ExesQuestionBean>>> it = this.mGroupList.entrySet().iterator();
        int i2 = 0;
        while (it.hasNext()) {
            if (it.next().getKey().equals(key)) {
                return i2;
            }
            i2++;
        }
        return i2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getLockMethod() {
        if (!SharePreferencesUtils.readStrConfig("statisticsPermission", this, "0").equals("0")) {
            ComputerStatisticsAct.newIntent(this, this.title, getIntent().getStringExtra("examId"), this.file);
            finish();
            return;
        }
        String strConfig = SharePreferencesUtils.readStrConfig("statisticsActiveId", this, "");
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put(ConstantsAPI.WXWebPage.KEY_ACTIVITY_ID, strConfig);
        MemInterface.getInstance().getMemData(this, ajaxParams, true, 2);
        MemInterface.getInstance().setmMoreLockListener(new MemInterface.MoreMethodLisener() { // from class: com.psychiatrygarden.activity.k5
            @Override // com.psychiatrygarden.activity.vip.Utils.MemInterface.MoreMethodLisener
            public final void mMoreMethodLock() {
                this.f12579a.lambda$getLockMethod$18();
            }
        });
    }

    private void getQuestionData() {
        YJYHttpUtils.get(getApplicationContext(), this.file, new AjaxParams(), new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.ComputerMockTestAct.7
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                ComputerMockTestAct.this.hideProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                ComputerMockTestAct.this.showProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String t2) {
                super.onSuccess((AnonymousClass7) t2);
                try {
                    List list = (List) new Gson().fromJson(DesUtil.decode(CommonParameter.DES_KEY_VERIFY, new JSONObject(t2).optString("data")), new TypeToken<List<ExesQuestionBean>>() { // from class: com.psychiatrygarden.activity.ComputerMockTestAct.7.1
                    }.getType());
                    Collections.sort(list);
                    if (!list.isEmpty()) {
                        for (int i2 = 0; i2 < list.size(); i2++) {
                            String number = ((ExesQuestionBean) list.get(i2)).getNumber();
                            if (TextUtils.isEmpty(number) || !number.matches(RegexPool.NUMBERS)) {
                                ((ExesQuestionBean) list.get(i2)).setNumber((i2 + 1) + "");
                            }
                            if (ComputerMockTestAct.this.mGroupList.containsKey(((ExesQuestionBean) list.get(i2)).getType())) {
                                ((List) ComputerMockTestAct.this.mGroupList.get(((ExesQuestionBean) list.get(i2)).getType())).add((ExesQuestionBean) list.get(i2));
                            } else {
                                ComputerMockTestAct.this.mTypeList.add(((ExesQuestionBean) list.get(i2)).getType());
                                ArrayList arrayList = new ArrayList();
                                arrayList.add((ExesQuestionBean) list.get(i2));
                                ComputerMockTestAct.this.mGroupList.put(((ExesQuestionBean) list.get(i2)).getType(), arrayList);
                            }
                        }
                        ComputerMockTestAct.this.mTempDataList.addAll(list);
                        ComputerMockTestAct.this.mCurrentStageIndex = 0;
                        ComputerMockTestAct.this.mSheetAdp.setList(list);
                        ComputerMockTestAct.this.setListData();
                        ComputerMockTestAct.this.startTime = System.currentTimeMillis() / 1000;
                        ComputerMockTestAct computerMockTestAct = ComputerMockTestAct.this;
                        computerMockTestAct.mNoAnswerQuestionCount = computerMockTestAct.mSheetAdp.getData().size();
                        ComputerMockTestAct.this.mTvNoAnswerCount.setText("未答题数：" + ComputerMockTestAct.this.mNoAnswerQuestionCount);
                        ComputerMockTestAct.this.mTvBiaoDoubtCount.setText("标疑题数：" + ComputerMockTestAct.this.mBiaoDoubtQuestionCount);
                        ComputerMockTestAct.this.firtSHowGuide();
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                    ComputerMockTestAct.this.AlertToast("题型解析错误");
                    ComputerMockTestAct.this.finish();
                }
                ComputerMockTestAct.this.hideProgressDialog();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getScore(int type) {
        if (type == 1) {
            long jCurrentTimeMillis = ((System.currentTimeMillis() / 1000) - this.startTime) / 60;
            int i2 = this.submitMinute;
            if (jCurrentTimeMillis < i2) {
                AlertToast(String.format(Locale.CHINA, "考试时长不满足交卷条件，最少做题%d分钟", Integer.valueOf(i2)));
                return;
            }
        }
        if (type == 1) {
            showFinishTestDailog();
        } else {
            Observable.create(new ObservableOnSubscribe() { // from class: com.psychiatrygarden.activity.i5
                @Override // io.reactivex.ObservableOnSubscribe
                public final void subscribe(ObservableEmitter observableEmitter) throws Exception {
                    ComputerMockTestAct.lambda$getScore$15(observableEmitter);
                }
            }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.psychiatrygarden.activity.j5
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) throws Exception {
                    this.f12549c.lambda$getScore$16((Date) obj);
                }
            });
            mCommDialog("答题时间已到，系统自动交卷！", 2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initQuestioData(final ExesQuestionBean data) {
        List<ExesQuestionBean.OptionBean> option;
        this.startDoTime = System.currentTimeMillis() / 1000;
        if (TextUtils.isEmpty(data.getPublic_number())) {
            this.mPublicScroll.setVisibility(8);
            this.mPublicLine.setVisibility(8);
        } else {
            this.mTvPublicTitle.setText(data.getPublic_title());
            this.mPublicScroll.setVisibility(0);
            this.mPublicLine.setVisibility(0);
        }
        if (TextUtils.isEmpty(data.getTitle_img())) {
            this.mImgTitle.setVisibility(8);
        } else {
            this.mImgTitle.setVisibility(0);
            GlideUtils.loadImage(this, data.getTitle_img(), this.mImgTitle);
            this.mImgTitle.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.h5
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f12467c.lambda$initQuestioData$2(data, view);
                }
            });
        }
        this.mTvQuestionType.setText(data.getType_str());
        this.mCurrentPosition = data.getTmpIndex();
        if (TextUtils.isEmpty(data.getShow_number()) || !data.getShow_number().equals("1")) {
            this.mTvQuestionTitle.setText(data.getNumber() + "、" + data.getTitle());
        } else {
            this.mTvQuestionTitle.setText(data.getNumber() + "、" + data.getQuestion_num() + " " + data.getTitle());
        }
        if (TextUtils.equals(Constants.VIA_REPORT_TYPE_DATALINE, data.getType())) {
            option = new ArrayList<>();
            ExesQuestionBean.OptionBean optionBean = new ExesQuestionBean.OptionBean();
            ExesQuestionBean.OptionBean optionBean2 = new ExesQuestionBean.OptionBean();
            optionBean.setKey("正确");
            optionBean2.setKey("错误");
            option.add(optionBean);
            option.add(optionBean2);
            data.setOption(option);
        } else {
            option = data.getOption();
        }
        this.mOptionAdp.setQuestionType(data.getType());
        this.mOptionAdp.setList(option);
        this.mSheetAdp.setCurrentPosition(this.mCurrentPosition);
        this.mSheetAdp.notifyDataSetChanged();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initQuestionTips(String tips) {
        this.mTvQuestionTips.setSingleLine(false);
        this.mTvQuestionTips.setText(tips);
        this.mTvQuestionTips.getViewTreeObserver().addOnGlobalLayoutListener(new AnonymousClass3());
    }

    private void initQuestionType() {
        Resources resources;
        int i2;
        Resources resources2;
        int i3;
        Resources resources3;
        int i4;
        this.mLyTypeView.removeAllViews();
        Iterator<Map.Entry<String, List<ExesQuestionBean>>> it = this.mGroupList.entrySet().iterator();
        while (it.hasNext()) {
            List<ExesQuestionBean> value = it.next().getValue();
            TextView textView = new TextView(this);
            textView.setPadding(ScreenUtil.getPxByDp((Context) this, 16), 0, 0, 0);
            textView.setTextSize(12.0f);
            textView.setGravity(16);
            textView.setText(value.get(0).getType_str() + ":" + value.get(0).getNumber() + "-" + value.get(value.size() - 1).getNumber() + "题");
            if (this.mSelectedGroupIndex == 0) {
                textView.setTypeface(Typeface.defaultFromStyle(1));
                if (SkinManager.getCurrentSkinType(this) == 0) {
                    resources2 = getResources();
                    i3 = R.color.new_bg_one_color;
                } else {
                    resources2 = getResources();
                    i3 = R.color.new_bg_one_color_night;
                }
                textView.setTextColor(resources2.getColor(i3));
                if (SkinManager.getCurrentSkinType(this) == 0) {
                    resources3 = getResources();
                    i4 = R.color.new_fail_color;
                } else {
                    resources3 = getResources();
                    i4 = R.color.new_fail_color_night;
                }
                textView.setBackgroundColor(resources3.getColor(i4));
            } else {
                textView.setTypeface(Typeface.defaultFromStyle(0));
                if (SkinManager.getCurrentSkinType(this) == 0) {
                    resources = getResources();
                    i2 = R.color.first_txt_color;
                } else {
                    resources = getResources();
                    i2 = R.color.first_txt_color_night;
                }
                textView.setTextColor(resources.getColor(i2));
            }
            textView.setLayoutParams(new LinearLayout.LayoutParams(-1, ScreenUtil.getPxByDp((Context) this, 32)));
            this.mSelectedGroupIndex++;
            this.mLyTypeView.addView(textView);
        }
    }

    private boolean isGroupLastData(String questionId) {
        List<ExesQuestionBean> data = this.mSheetAdp.getData();
        return data.get(data.size() - 1).getQuestion_id().equals(questionId);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$firtSHowGuide$1() {
        SharePreferencesUtils.writeBooleanConfig("isShowedComputerGuide", true, this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getLockMethod$18() {
        ComputerStatisticsAct.newIntent(this, this.title, getIntent().getStringExtra("examId"), this.file);
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$getScore$15(ObservableEmitter observableEmitter) throws Exception {
        observableEmitter.onNext(CommonUtil.getNetTime());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
        ExesQuestionBean item = this.mSheetAdp.getItem(this.mCurrentPosition);
        int i3 = Integer.parseInt(item.getNumber()) - 1;
        int length = item.getAnswer().length();
        String type = item.getType();
        List<ExesQuestionBean.OptionBean> option = item.getOption();
        int i4 = 0;
        if ((length > 1 && !TextUtils.equals(type, Constants.VIA_REPORT_TYPE_DATALINE)) || TextUtils.equals(type, Constants.VIA_ACT_TYPE_NINETEEN) || TextUtils.equals(type, Constants.VIA_SHARE_TYPE_PUBLISHVIDEO)) {
            option.get(i2).setType(TextUtils.equals(option.get(i2).getType(), "1") ? "0" : "1");
            StringBuilder sb = new StringBuilder();
            int i5 = 0;
            while (true) {
                if (i5 >= option.size()) {
                    break;
                }
                if (option.get(i5).getType() != null && option.get(i5).getType().equals("1")) {
                    sb.append(option.get(i5).getKey().trim());
                    sb.append(i5 >= option.size() - 1 ? "" : ",");
                }
                i5++;
            }
            char[] charArray = item.getAnswer().replace(",", "").toCharArray();
            char[] charArray2 = sb.toString().replace(",", "").toCharArray();
            Arrays.sort(charArray);
            Arrays.sort(charArray2);
            String string = sb.toString();
            if (!TextUtils.isEmpty(string) && string.endsWith(",")) {
                String strReplace = string.replace(",", "");
                StringBuilder sb2 = new StringBuilder();
                while (i4 < strReplace.length()) {
                    sb2.append(strReplace.charAt(i4));
                    sb2.append(i4 != strReplace.length() - 1 ? "," : "");
                    i4++;
                }
                string = sb2.toString();
            }
            item.setOwnAns(string);
            item.setIsRight(TextUtils.equals(new String(charArray), new String(charArray2)) ? "1" : "0");
        } else {
            int i6 = 0;
            for (int i7 = 0; i7 < option.size(); i7++) {
                if (option.get(i7).getType().equals("1")) {
                    i6++;
                }
            }
            if (i6 <= 0) {
                option.get(i2).setType("1");
                item.setOwnAns(option.get(i2).getKey());
                item.setIsRight(option.get(i2).getKey().equals(item.getAnswer()) ? "1" : "0");
            } else if (!option.get(i2).getType().equals("1")) {
                while (i4 < option.size()) {
                    option.get(i4).setType("0");
                    i4++;
                }
                option.get(i2).setType("1");
                item.setOwnAns(option.get(i2).getKey());
                item.setIsRight(option.get(i2).getKey().equals(item.getAnswer()) ? "1" : "0");
            }
        }
        this.mTempDataList.get(i3).setOption(item.getOption());
        long jCurrentTimeMillis = (System.currentTimeMillis() / 1000) - this.startDoTime;
        this.mTempDataList.get(i3).setDoDuration(jCurrentTimeMillis + "");
        this.mTempCurrentStagetList.get(this.mCurrentPosition).setOption(item.getOption());
        this.mOptionAdp.notifyDataSetChanged();
        updateLeftDataByOptions(item);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initQuestioData$2(ExesQuestionBean exesQuestionBean, View view) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(exesQuestionBean.getTitle_img());
        ImageViewerPopupViewCustom xPopupImageLoader = new ImageViewerPopupViewCustom(this.mContext).setSingleSrcView(null, Integer.valueOf(R.mipmap.ic_order_default)).setXPopupImageLoader(new ImageLoaderUtilsCustom());
        xPopupImageLoader.popupInfo = new PopupInfo();
        xPopupImageLoader.setImageUrls(arrayList).setSrcView(null, 0).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$mCommDialog$17(int i2, AlertDialog alertDialog, View view) {
        if (i2 != 1) {
            alertDialog.dismiss();
        } else {
            alertDialog.dismiss();
            SharePreferencesUtils.writeBooleanConfig(CommonParameter.EXAMIDTRUE, false, this.mContext);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$10(View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        if (this.mCurrentPosition > 0) {
            if (this.mBtnClickType == 0) {
                initQuestioData(this.mSheetAdp.getData().get(this.mCurrentPosition - 1));
                return;
            } else {
                initQuestioData(this.mSheetAdp.getData().get(this.mCurrentPosition - 1));
                return;
            }
        }
        if (this.mBtnClickType == 0) {
            if (this.mCurrentStageIndex == 0) {
                ToastUtil.shortToast(this, "已是第一题");
            } else {
                ToastUtil.shortToast(this, "不可返回上一阶段");
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$11(View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        if (this.mCurrentStageIndex >= this.mTypeList.size() - 1) {
            if (isGroupLastData(this.mSheetAdp.getItem(this.mCurrentPosition).getQuestion_id())) {
                ToastUtil.shortToast(this, "已是最后一题");
                return;
            } else {
                initQuestioData(this.mSheetAdp.getData().get(this.mCurrentPosition + 1));
                return;
            }
        }
        ExesQuestionBean item = this.mSheetAdp.getItem(this.mCurrentPosition);
        if (this.mBtnClickType != 0) {
            if (isGroupLastData(item.getQuestion_id())) {
                return;
            }
            initQuestioData(this.mSheetAdp.getData().get(this.mCurrentPosition + 1));
        } else if (isGroupLastData(item.getQuestion_id())) {
            showNextStageDailog();
        } else {
            initQuestioData(this.mSheetAdp.getData().get(this.mCurrentPosition + 1));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$12(View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        getScore(1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$13(View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        new XPopup.Builder(this.mContext).asCustom(new ComputerNextDialog(this.mContext, new ComputerNextDialog.ClickIml() { // from class: com.psychiatrygarden.activity.ComputerMockTestAct.4
            @Override // com.psychiatrygarden.widget.ComputerNextDialog.ClickIml
            public void mClickIml() {
            }

            @Override // com.psychiatrygarden.widget.ComputerNextDialog.ClickIml
            public void mClickLeftIml() {
            }
        }, new SpannableStringBuilder(this.mTvQuestionTips.getText().toString()), "确定", "")).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$14(View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        showNextStageDailog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$3(View view) {
        closeDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$4(int i2, ValueAnimator valueAnimator) {
        int iIntValue = ((Integer) valueAnimator.getAnimatedValue()).intValue();
        ViewGroup.LayoutParams layoutParams = this.mLeftScrollView.getLayoutParams();
        layoutParams.width = iIntValue;
        this.mLeftScrollView.setLayoutParams(layoutParams);
        ViewGroup.LayoutParams layoutParams2 = this.mLyRight.getLayoutParams();
        int i3 = this.mLeftWidth;
        layoutParams2.width = (i2 - i3) + (i3 - iIntValue);
        this.mLyRight.setLayoutParams(layoutParams2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$5(ValueAnimator valueAnimator) {
        int iIntValue = ((Integer) valueAnimator.getAnimatedValue()).intValue();
        ViewGroup.LayoutParams layoutParams = this.mLeftScrollView.getLayoutParams();
        layoutParams.width = iIntValue;
        ViewGroup.LayoutParams layoutParams2 = this.mLyRight.getLayoutParams();
        layoutParams2.width = ScreenUtil.getScreenWidth(this) - iIntValue;
        this.mLeftScrollView.setLayoutParams(layoutParams);
        this.mLyRight.setLayoutParams(layoutParams2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$6(View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        this.isHideLeftMenu = !this.isHideLeftMenu;
        final int screenWidth = ScreenUtil.getScreenWidth(this);
        if (this.isHideLeftMenu) {
            ValueAnimator valueAnimatorOfInt = ValueAnimator.ofInt(this.mLeftWidth, 0);
            valueAnimatorOfInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.psychiatrygarden.activity.f5
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    this.f12342c.lambda$setListenerForWidget$4(screenWidth, valueAnimator);
                }
            });
            valueAnimatorOfInt.setDuration(300L);
            valueAnimatorOfInt.start();
            return;
        }
        ValueAnimator valueAnimatorOfInt2 = ValueAnimator.ofInt(0, this.mLeftWidth);
        valueAnimatorOfInt2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.psychiatrygarden.activity.g5
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                this.f12427c.lambda$setListenerForWidget$5(valueAnimator);
            }
        });
        valueAnimatorOfInt2.setDuration(300L);
        valueAnimatorOfInt2.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$7(View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        ExesQuestionBean item = this.mSheetAdp.getItem(this.mCurrentPosition);
        if (item.getAnswer_mode() == 1) {
            return;
        }
        updateBiaoDoubtCount(item);
        item.setBiaoDoubt(true);
        this.mSheetAdp.notifyItemChanged(this.mCurrentPosition, 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$8(View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        if (!this.mBtnToBiaoDoubt.getText().toString().equals("返回")) {
            toRestDatList(false);
            return;
        }
        for (int i2 = 0; i2 < this.mTempCurrentStagetList.size(); i2++) {
            this.mTempCurrentStagetList.get(i2).setTmpIndex(i2);
        }
        this.mSheetAdp.setList(this.mTempCurrentStagetList);
        initQuestioData(this.mSheetAdp.getItem(this.mTempCurrentPosition));
        this.mBtnToBiaoDoubt.setText("转到标疑题");
        this.mBtnClickType = 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$9(View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        if (!this.mBtnToNoAnswer.getText().toString().equals("返回")) {
            toRestDatList(true);
            return;
        }
        for (int i2 = 0; i2 < this.mTempCurrentStagetList.size(); i2++) {
            this.mTempCurrentStagetList.get(i2).setTmpIndex(i2);
        }
        this.mSheetAdp.setList(this.mTempCurrentStagetList);
        initQuestioData(this.mSheetAdp.getItem(this.mTempCurrentPosition));
        this.mBtnToNoAnswer.setText("转到未答题");
        this.mBtnClickType = 0;
    }

    public static void newIntent(Context context, String examId, String title, String dateEnd, long durationMin, String submitMinute, String file) {
        Intent intent = new Intent(context, (Class<?>) ComputerMockTestAct.class);
        intent.putExtra("examId", examId);
        intent.putExtra("title", title);
        intent.putExtra("dateEnd", dateEnd);
        intent.putExtra("durationMinute", durationMin);
        intent.putExtra("submitMinute", submitMinute);
        intent.putExtra("file", file);
        context.startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void searchSelectGroupIndex(String key) {
        Resources resources;
        int i2;
        Resources resources2;
        int i3;
        Resources resources3;
        int i4;
        int keyPosition = getKeyPosition(key);
        for (int i5 = 0; i5 < this.mLyTypeView.getChildCount(); i5++) {
            TextView textView = (TextView) this.mLyTypeView.getChildAt(i5);
            if (keyPosition == i5) {
                textView.setTypeface(Typeface.defaultFromStyle(1));
                if (SkinManager.getCurrentSkinType(this) == 0) {
                    resources2 = getResources();
                    i3 = R.color.new_bg_one_color;
                } else {
                    resources2 = getResources();
                    i3 = R.color.new_bg_one_color_night;
                }
                textView.setTextColor(resources2.getColor(i3));
                if (SkinManager.getCurrentSkinType(this) == 0) {
                    resources3 = getResources();
                    i4 = R.color.new_fail_color;
                } else {
                    resources3 = getResources();
                    i4 = R.color.new_fail_color_night;
                }
                textView.setBackgroundColor(resources3.getColor(i4));
            } else {
                textView.setTypeface(Typeface.defaultFromStyle(0));
                if (SkinManager.getCurrentSkinType(this) == 0) {
                    resources = getResources();
                    i2 = R.color.first_txt_color;
                } else {
                    resources = getResources();
                    i2 = R.color.first_txt_color_night;
                }
                textView.setTextColor(resources.getColor(i2));
                textView.setBackgroundColor(0);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setListData() {
        List<ExesQuestionBean> list = this.mGroupList.get(this.mTypeList.get(this.mCurrentStageIndex));
        for (int i2 = 0; i2 < list.size(); i2++) {
            list.get(i2).setTmpIndex(i2);
        }
        this.mTempCurrentStagetList.clear();
        this.mTempCurrentStagetList.addAll(list);
        this.mSheetAdp.setList(list);
    }

    private void showFinishTestDailog() {
        SpannableStringBuilder spannableStringBuilder;
        SpannableStringBuilder spannableStringBuilder2 = new SpannableStringBuilder("确认要交卷吗？");
        if (this.mNoAnswerQuestionCount > 0) {
            SpannableStringBuilder spannableStringBuilder3 = new SpannableStringBuilder(("还有" + this.mNoAnswerQuestionCount + "道题未答，") + "确认要交卷吗？");
            if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
                spannableStringBuilder3.setSpan(new ForegroundColorSpan(Color.parseColor("#F95843")), 2, String.valueOf(this.mNoAnswerQuestionCount).length() + 2, 34);
            } else {
                spannableStringBuilder3.setSpan(new ForegroundColorSpan(Color.parseColor("#B2575C")), 2, String.valueOf(this.mNoAnswerQuestionCount).length() + 2, 34);
            }
            spannableStringBuilder = spannableStringBuilder3;
        } else {
            spannableStringBuilder = spannableStringBuilder2;
        }
        new XPopup.Builder(this.mContext).asCustom(new ComputerNextDialog(this.mContext, new AnonymousClass6(), spannableStringBuilder, "暂不交卷", "确认交卷")).show();
    }

    private void showNextStageDailog() {
        SpannableStringBuilder spannableStringBuilder;
        Iterator<ExesQuestionBean> it = this.mTempCurrentStagetList.iterator();
        int i2 = 0;
        while (it.hasNext()) {
            if (TextUtils.isEmpty(it.next().getOwnAns())) {
                i2++;
            }
        }
        SpannableStringBuilder spannableStringBuilder2 = new SpannableStringBuilder("考试将进入下一阶段，进入后本段试题将无法再做修改，确认要进入下一段考试吗？");
        if (i2 > 0) {
            SpannableStringBuilder spannableStringBuilder3 = new SpannableStringBuilder(("本阶段还有" + i2 + "道题未答，") + "考试将进入下一阶段，进入后本段试题将无法再做修改，确认要进入下一段考试吗？");
            if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
                spannableStringBuilder3.setSpan(new ForegroundColorSpan(Color.parseColor("#F95843")), 5, String.valueOf(i2).length() + 5, 34);
            } else {
                spannableStringBuilder3.setSpan(new ForegroundColorSpan(Color.parseColor("#B2575C")), 5, String.valueOf(i2).length() + 5, 34);
            }
            spannableStringBuilder = spannableStringBuilder3;
        } else {
            spannableStringBuilder = spannableStringBuilder2;
        }
        new XPopup.Builder(this.mContext).asCustom(new ComputerNextDialog(this.mContext, new ComputerNextDialog.ClickIml() { // from class: com.psychiatrygarden.activity.ComputerMockTestAct.5
            @Override // com.psychiatrygarden.widget.ComputerNextDialog.ClickIml
            public void mClickIml() {
                if (ComputerMockTestAct.this.isLogin()) {
                    if (ComputerMockTestAct.this.mBtnClickType != 0) {
                        ComputerMockTestAct.this.mBtnClickType = 0;
                        ComputerMockTestAct.this.mBtnToBiaoDoubt.setText("转到标疑题");
                        ComputerMockTestAct.this.mBtnToNoAnswer.setText("转到未答题");
                    }
                    ComputerMockTestAct.access$708(ComputerMockTestAct.this);
                    ComputerMockTestAct.this.mBiaoDoubtQuestionCount = 0;
                    ComputerMockTestAct.this.setListData();
                    ComputerMockTestAct computerMockTestAct = ComputerMockTestAct.this;
                    computerMockTestAct.initQuestionTips(computerMockTestAct.mSheetAdp.getItem(0).getQuestion_type());
                    ComputerMockTestAct computerMockTestAct2 = ComputerMockTestAct.this;
                    computerMockTestAct2.initQuestioData(computerMockTestAct2.mSheetAdp.getItem(0));
                    ComputerMockTestAct computerMockTestAct3 = ComputerMockTestAct.this;
                    computerMockTestAct3.searchSelectGroupIndex(computerMockTestAct3.mSheetAdp.getItem(0).getType());
                    ComputerMockTestAct.this.mTvBiaoDoubtCount.setText("标疑题数：" + ComputerMockTestAct.this.mBiaoDoubtQuestionCount);
                    if (ComputerMockTestAct.this.mCurrentStageIndex == ComputerMockTestAct.this.mTypeList.size() - 1) {
                        ComputerMockTestAct.this.mBtnNextStage.setVisibility(8);
                    }
                }
            }

            @Override // com.psychiatrygarden.widget.ComputerNextDialog.ClickIml
            public void mClickLeftIml() {
            }
        }, spannableStringBuilder, "继续作答", "进入下一阶段")).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: submitData, reason: merged with bridge method [inline-methods] */
    public void lambda$getScore$16(Date date) {
        ArrayList arrayList = new ArrayList();
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("exam_id", getIntent().getStringExtra("examId"));
        try {
            ajaxParams.put("date_end_timestamp", (date.getTime() / 1000) + "");
            ajaxParams.put("date_start_timestamp", "" + this.startTime);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        int i2 = 0;
        int i3 = 0;
        for (int i4 = 0; i4 < this.mTempDataList.size(); i4++) {
            AnswerBean answerBean = new AnswerBean();
            answerBean.setAnswer(this.mTempDataList.get(i4).getOwnAns());
            boolean z2 = true;
            i3 += (this.mTempDataList.get(i4).getOwnAns() == null || TextUtils.isEmpty(this.mTempDataList.get(i4).getOwnAns())) ? 0 : 1;
            String ownAns = this.mTempDataList.get(i4).getOwnAns();
            String answer = this.mTempDataList.get(i4).getAnswer();
            if (ownAns != null && answer != null) {
                z2 = false;
            }
            answerBean.setIs_right(z2 ? "0" : this.mTempDataList.get(i4).getIsRight());
            i2 += TextUtils.equals(this.mTempDataList.get(i4).getIsRight(), "1") ? 1 : 0;
            answerBean.setQuestion_id(this.mTempDataList.get(i4).getQuestion_id());
            answerBean.setDuration(this.mTempDataList.get(i4).getDoDuration());
            answerBean.setSubject_id(SharePreferencesUtils.readStrConfig(CommonParameter.identity_id, this.mContext));
            answerBean.setKnowledge_id(TextUtils.isEmpty(this.mTempDataList.get(i4).getKnowledge_id()) ? "" : this.mTempDataList.get(i4).getKnowledge_id());
            arrayList.add(answerBean);
        }
        long jCurrentTimeMillis = (System.currentTimeMillis() / 1000) - this.startTime;
        LogUtils.d(getClass().getSimpleName(), "考试时间：" + (jCurrentTimeMillis / 60) + " 分钟");
        ajaxParams.put(CommonParameter.EXAM_TIME, String.valueOf(jCurrentTimeMillis));
        ajaxParams.put("right", String.valueOf(i2));
        ajaxParams.put("answer_num", String.valueOf(i3));
        ajaxParams.put("answer", new Gson().toJson(arrayList));
        showProgressDialog("提交中", false);
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.mPostBatchUrl, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.ComputerMockTestAct.8
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                ComputerMockTestAct.this.hideProgressDialog();
                ComputerMockTestAct.this.AlertToast("答题记录上传失败，请手动交卷，否则答题记录将不被保存！");
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass8) s2);
                try {
                    if (new JSONObject(s2).optString("code").equals("200")) {
                        SharePreferencesUtils.writeBooleanConfig(CommonParameter.isAddMock, false, ComputerMockTestAct.this.mContext);
                        ProjectApp.questExamDataList.clear();
                        ProjectApp.questExamList.clear();
                        ComputerMockTestAct.this.getLockMethod();
                    } else {
                        ComputerMockTestAct.this.hideProgressDialog();
                        ComputerMockTestAct.this.AlertToast(new JSONObject(s2).optString("message"));
                    }
                } catch (Exception e3) {
                    ComputerMockTestAct.this.hideProgressDialog();
                    e3.printStackTrace();
                }
            }
        });
    }

    private void toRestDatList(boolean isNoAnswer) {
        if (this.mBtnClickType == 0) {
            this.mTempCurrentPosition = this.mSheetAdp.getCurrentPosition();
        }
        this.mTempHandleDataList.clear();
        for (int i2 = 0; i2 < this.mTempCurrentStagetList.size(); i2++) {
            if (isNoAnswer) {
                if (this.mTempCurrentStagetList.get(i2).getAnswer_mode() == 0) {
                    this.mTempHandleDataList.add(this.mTempCurrentStagetList.get(i2));
                }
            } else if (this.mTempCurrentStagetList.get(i2).isBiaoDoubt()) {
                this.mTempHandleDataList.add(this.mTempCurrentStagetList.get(i2));
            }
        }
        for (int i3 = 0; i3 < this.mTempHandleDataList.size(); i3++) {
            this.mTempHandleDataList.get(i3).setTmpIndex(i3);
        }
        if (this.mTempHandleDataList.size() <= 0) {
            if (isNoAnswer) {
                ToastUtil.shortToast(this, "此阶段没有未答的题");
                return;
            } else {
                ToastUtil.shortToast(this, "此阶段没有标疑的题");
                return;
            }
        }
        this.mSheetAdp.setList(this.mTempHandleDataList);
        initQuestioData(this.mSheetAdp.getItem(0));
        if (isNoAnswer) {
            this.mBtnToNoAnswer.setText("返回");
            this.mBtnToBiaoDoubt.setText("转到标疑题");
            this.mBtnClickType = 2;
        } else {
            this.mBtnToBiaoDoubt.setText("返回");
            this.mBtnToNoAnswer.setText("转到未答题");
            this.mBtnClickType = 1;
        }
    }

    private void updateBiaoDoubtCount(ExesQuestionBean currentItem) {
        if (currentItem.isBiaoDoubt()) {
            return;
        }
        this.mBiaoDoubtQuestionCount++;
        this.mTvBiaoDoubtCount.setText("标疑题数：" + this.mBiaoDoubtQuestionCount);
    }

    private void updateLeftDataByOptions(ExesQuestionBean currentQuestionItem) {
        if (!currentQuestionItem.isChoosedAnswer()) {
            int i2 = this.mNoAnswerQuestionCount;
            if (i2 > 0) {
                this.mNoAnswerQuestionCount = i2 - 1;
            } else {
                this.mNoAnswerQuestionCount = 0;
            }
            this.mTvNoAnswerCount.setText("未答题数：" + this.mNoAnswerQuestionCount);
            currentQuestionItem.setAnswer_mode(2);
            currentQuestionItem.setChoosedAnswer(true);
        }
        if (currentQuestionItem.isBiaoDoubt()) {
            int i3 = this.mBiaoDoubtQuestionCount;
            if (i3 > 0) {
                this.mBiaoDoubtQuestionCount = i3 - 1;
                this.mTvBiaoDoubtCount.setText("标疑题数：" + this.mBiaoDoubtQuestionCount);
            }
            currentQuestionItem.setBiaoDoubt(false);
        }
        this.mSheetAdp.notifyItemChanged(this.mCurrentPosition, 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateTime(long millisUntilFinished) {
        int i2 = (int) (millisUntilFinished / com.heytap.mcssdk.constant.a.f7141e);
        int i3 = (int) ((millisUntilFinished / 60000) % 60);
        int i4 = (int) ((millisUntilFinished / 1000) % 60);
        this.mTvTimeOne.setText(String.valueOf(i2 / 10));
        this.mTvTimeTwo.setText(String.valueOf(i2 % 10));
        this.mTvTimeThree.setText(String.valueOf(i3 / 10));
        this.mTvTimeFour.setText(String.valueOf(i3 % 10));
        this.mTvTimeFive.setText(String.valueOf(i4 / 10));
        this.mTvTimeSix.setText(String.valueOf(i4 % 10));
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.dateEnd = getIntent().getStringExtra("dateEnd");
        this.durationMinute = getIntent().getLongExtra("durationMinute", 0L);
        this.file = getIntent().getStringExtra("file");
        this.title = getIntent().getStringExtra("title");
        String stringExtra = getIntent().getStringExtra("submitMinute");
        if (stringExtra == null || !stringExtra.matches("^\\d*$")) {
            this.submitMinute = 30;
        } else {
            this.submitMinute = Integer.parseInt(stringExtra);
        }
        this.mTvMockName = (TextView) findViewById(R.id.tv_mock_name);
        this.mImgBack = (ImageView) findViewById(R.id.img_back);
        this.mLeftScrollView = (NestedScrollView) findViewById(R.id.left_view);
        this.mLyRight = (LinearLayout) findViewById(R.id.ly_right);
        this.mLyArrow = (LinearLayout) findViewById(R.id.ly_arrow);
        this.mTvTestTime = (TextView) findViewById(R.id.tv_test_time);
        this.mTvNoAnswerCount = (TextView) findViewById(R.id.tv_no_answer_count);
        this.mTvBiaoDoubtCount = (TextView) findViewById(R.id.tv_biao_doubt_count);
        this.mLyTypeView = (LinearLayout) findViewById(R.id.ly_type_view);
        this.mSheetRecycler = (RecyclerView) findViewById(R.id.recycler_sheet);
        this.mTvTimeOne = (TextView) findViewById(R.id.tv_time_one);
        this.mTvTimeTwo = (TextView) findViewById(R.id.tv_time_two);
        this.mTvTimeThree = (TextView) findViewById(R.id.tv_time_three);
        this.mTvTimeFour = (TextView) findViewById(R.id.tv_time_four);
        this.mTvTimeFive = (TextView) findViewById(R.id.tv_time_five);
        this.mTvTimeSix = (TextView) findViewById(R.id.tv_time_six);
        this.mPublicScroll = (NestedScrollView) findViewById(R.id.sc_public_title);
        this.mTvPublicTitle = (TextView) findViewById(R.id.tv_public_title);
        this.mPublicLine = findViewById(R.id.public_line);
        this.mTvQuestionType = (TextView) findViewById(R.id.tv_question_type);
        this.mTvQuestionTips = (TextView) findViewById(R.id.tv_question_tips);
        this.mTvQuestionTitle = (TextView) findViewById(R.id.tv_question_title);
        this.mBtnBiaoDoubt = (TextView) findViewById(R.id.btn_biao_doubt);
        this.mBtnToBiaoDoubt = (TextView) findViewById(R.id.btn_to_biao_doubt);
        this.mBtnToNoAnswer = (TextView) findViewById(R.id.btn_to_no_answer);
        this.mBtnPrevious = (TextView) findViewById(R.id.btn_previous);
        this.mBtnNext = (TextView) findViewById(R.id.btn_next);
        this.mBtnFinish = (TextView) findViewById(R.id.btn_finish);
        this.mOptionRecycler = (MaxRecyclerView) findViewById(R.id.qlistview);
        this.mImgArrow = (ImageView) findViewById(R.id.img_arrow);
        this.mBtnMore = (TextView) findViewById(R.id.btn_more);
        this.mImgTitle = (RoundedImageView) findViewById(R.id.img_title);
        this.mBtnNextStage = (TextView) findViewById(R.id.btn_next_stage);
        this.mTvMockName.setText(this.title);
        ComputerAnswerSheetAdp computerAnswerSheetAdp = new ComputerAnswerSheetAdp();
        this.mSheetAdp = computerAnswerSheetAdp;
        this.mSheetRecycler.setAdapter(computerAnswerSheetAdp);
        ComputerOptionsAdp computerOptionsAdp = new ComputerOptionsAdp();
        this.mOptionAdp = computerOptionsAdp;
        this.mOptionRecycler.setAdapter(computerOptionsAdp);
        ViewGroup.LayoutParams layoutParams = this.mLyRight.getLayoutParams();
        layoutParams.width = ScreenUtil.getScreenWidth(this) - this.mLeftWidth;
        this.mLyRight.setLayoutParams(layoutParams);
        List<ExesQuestionBean> list = ProjectApp.questExamDataList;
        if (list == null || list.size() <= 0) {
            getQuestionData();
        } else {
            this.mTempDataList.addAll(ProjectApp.questExamDataList);
            for (ExesQuestionBean exesQuestionBean : ProjectApp.questExamDataList) {
                if (this.mGroupList.containsKey(exesQuestionBean.getType())) {
                    this.mGroupList.get(exesQuestionBean.getType()).add(exesQuestionBean);
                } else {
                    this.mTypeList.add(exesQuestionBean.getType());
                    ArrayList arrayList = new ArrayList();
                    arrayList.add(exesQuestionBean);
                    this.mGroupList.put(exesQuestionBean.getType(), arrayList);
                }
            }
            this.mCurrentStageIndex = 0;
            setListData();
            this.startTime = System.currentTimeMillis() / 1000;
            this.mNoAnswerQuestionCount = ProjectApp.questExamDataList.size();
            this.mTvNoAnswerCount.setText("未答题数：" + this.mNoAnswerQuestionCount);
            this.mTvBiaoDoubtCount.setText("标疑题数：" + this.mBiaoDoubtQuestionCount);
            firtSHowGuide();
        }
        long jCurrentTimeMillis = System.currentTimeMillis() / 1000;
        long j2 = !TextUtils.isEmpty(this.dateEnd) ? Long.parseLong(this.dateEnd) : (this.durationMinute * 60) + jCurrentTimeMillis;
        if (j2 > 0) {
            long j3 = j2 - jCurrentTimeMillis;
            if (j3 * 1000 < this.durationMinute * 60 * 1000) {
                this.mHintPop = (ExamTimeHintPop) new XPopup.Builder(this).setPopupCallback(new SimpleCallback() { // from class: com.psychiatrygarden.activity.ComputerMockTestAct.1
                    @Override // com.lxj.xpopup.interfaces.SimpleCallback, com.lxj.xpopup.interfaces.XPopupCallback
                    public void onDismiss(BasePopupView popupView) {
                        super.onDismiss(popupView);
                    }
                }).asCustom(new ExamTimeHintPop(this, j3 / 60, this.durationMinute)).show();
            }
        }
        long j4 = j2 - jCurrentTimeMillis;
        long j5 = this.durationMinute;
        if (j4 > j5 * 60) {
            this.mTvTestTime.setText("考试时间：" + CommonUtil.getHoursByTimes(String.valueOf(jCurrentTimeMillis)) + "-" + CommonUtil.getHoursByTimes(String.valueOf((j5 * 60) + jCurrentTimeMillis)));
            this.countDownTimer = new TimerCount(1000 * this.durationMinute * 60, 1000L);
        } else {
            this.mTvTestTime.setText("考试时间：" + CommonUtil.getHoursByTimes(String.valueOf(jCurrentTimeMillis)) + "-" + CommonUtil.getHoursByTimes(String.valueOf(j2)));
            this.countDownTimer = new TimerCount(1000 * j4, 1000L);
        }
        this.countDownTimer.start();
        this.mSheetAdp.setOnItemClickLisenter(new ComputerAnswerSheetAdp.OnItemClickLisenter() { // from class: com.psychiatrygarden.activity.ComputerMockTestAct.2
            @Override // com.psychiatrygarden.activity.online.adapter.ComputerAnswerSheetAdp.OnItemClickLisenter
            public void itemClick(int position, ExesQuestionBean item) {
                if (position == ComputerMockTestAct.this.mCurrentPosition) {
                    return;
                }
                ComputerMockTestAct.this.mCurrentPosition = position;
                ComputerMockTestAct.this.initQuestioData(item);
            }
        });
        this.mOptionAdp.setOnItemClickListener(new OnItemClickListener() { // from class: com.psychiatrygarden.activity.d5
            @Override // com.chad.library.adapter.base.listener.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
                this.f12229c.lambda$init$0(baseQuickAdapter, view, i2);
            }
        });
        initQuestionType();
        initQuestioData(this.mSheetAdp.getData().get(0));
        initQuestionTips(this.mSheetAdp.getItem(0).getQuestion_type());
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void initStatusBar() {
        if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
            StatusBarUtil.setColor(this, -1, 0);
            getWindow().setNavigationBarColor(Color.parseColor("#FBFBFB"));
        } else {
            StatusBarUtil.setColor(this, Color.parseColor("#121622"), 0);
            getWindow().setNavigationBarColor(Color.parseColor("#171D2D"));
        }
    }

    public void mCommDialog(String textstr, final int type) {
        try {
            if (this.mContext == null) {
                return;
            }
            final AlertDialog alertDialogCreate = new AlertDialog.Builder(this.mContext, R.style.MyDialog).create();
            alertDialogCreate.show();
            WindowManager.LayoutParams attributes = alertDialogCreate.getWindow().getAttributes();
            attributes.width = -1;
            attributes.height = -2;
            alertDialogCreate.getWindow().setAttributes(attributes);
            Window window = alertDialogCreate.getWindow();
            window.setContentView(R.layout.activity_commdialog);
            window.setGravity(17);
            window.setWindowAnimations(R.style.mystyle);
            alertDialogCreate.setCanceledOnTouchOutside(false);
            alertDialogCreate.setCancelable(false);
            TextView textView = (TextView) alertDialogCreate.findViewById(R.id.text);
            TextView textView2 = (TextView) alertDialogCreate.findViewById(R.id.mclick);
            textView.setText(textstr);
            textView2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.e5
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f12269c.lambda$mCommDialog$17(type, alertDialogCreate, view);
                }
            });
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        TimerCount timerCount = this.countDownTimer;
        if (timerCount != null) {
            timerCount.cancel();
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
        if (str.equals("closePage")) {
            finish();
        }
    }

    @Override // androidx.appcompat.app.AppCompatActivity, android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode != 4 || event.getRepeatCount() != 0) {
            return false;
        }
        closeDialog();
        return true;
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        this.mActionBar.hide();
        SharePreferencesUtils.writeBooleanConfig(CommonParameter.isAddMock, true, this.mContext);
        setContentView(R.layout.layout_computer_mock);
        getWindow().addFlags(1024);
        if (SkinManager.getCurrentSkinType(this) == 0) {
            getWindow().getDecorView().setSystemUiVisibility(8192);
        }
        this.mLeftWidth = ScreenUtil.getPxByDp((Context) this, 199);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.mImgBack.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.l5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12655c.lambda$setListenerForWidget$3(view);
            }
        });
        this.mLyArrow.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.m5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12688c.lambda$setListenerForWidget$6(view);
            }
        });
        this.mBtnBiaoDoubt.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.v4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f14011c.lambda$setListenerForWidget$7(view);
            }
        });
        this.mBtnToBiaoDoubt.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.w4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f14138c.lambda$setListenerForWidget$8(view);
            }
        });
        this.mBtnToNoAnswer.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.x4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f14170c.lambda$setListenerForWidget$9(view);
            }
        });
        this.mBtnPrevious.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.y4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f14206c.lambda$setListenerForWidget$10(view);
            }
        });
        this.mBtnNext.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.z4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f14235c.lambda$setListenerForWidget$11(view);
            }
        });
        this.mBtnFinish.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.a5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f10984c.lambda$setListenerForWidget$12(view);
            }
        });
        this.mBtnMore.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.b5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11097c.lambda$setListenerForWidget$13(view);
            }
        });
        this.mBtnNextStage.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.c5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11136c.lambda$setListenerForWidget$14(view);
            }
        });
    }
}
