package com.psychiatrygarden.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.core.widget.NestedScrollView;
import cn.hutool.core.lang.RegexPool;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.github.liuyueyi.quick.transfer.dictionary.DictionaryFactory;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.lxj.xpopup.core.PopupInfo;
import com.lxj.xpopup.interfaces.SimpleCallback;
import com.makeramen.roundedimageview.RoundedImageView;
import com.mobile.auth.gatewayauth.Constant;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.ComputerMockTestModeTwoAct;
import com.psychiatrygarden.activity.vip.Utils.MemInterface;
import com.psychiatrygarden.adapter.ComputerOptionsAdp;
import com.psychiatrygarden.bean.AnswerBean;
import com.psychiatrygarden.bean.ExesQuestionBean;
import com.psychiatrygarden.bean.MockPopTipsBean;
import com.psychiatrygarden.config.UserConfig;
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
import com.psychiatrygarden.widget.CaculatorDialog;
import com.psychiatrygarden.widget.ComputerHelpDialog;
import com.psychiatrygarden.widget.ComputerMockPopTipsDialog;
import com.psychiatrygarden.widget.ComputerNextDialog;
import com.psychiatrygarden.widget.ExamTimeHintPop;
import com.psychiatrygarden.widget.ImageViewerPopupViewCustom;
import com.psychiatrygarden.widget.MaxRecyclerView;
import com.psychiatrygarden.widget.MockAnswerSheetView;
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
import java.text.DecimalFormat;
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
public class ComputerMockTestModeTwoAct extends BaseActivity {
    private TimerCount countDownTimer;
    private String dateEnd;
    private long durationMinute;
    private String examId;
    private String file;
    private TextView mBtnCalculator;
    private TextView mBtnCloseTips;
    private TextView mBtnFinish;
    private TextView mBtnHelp;
    private TextView mBtnNext;
    private TextView mBtnPrevious;
    private ProgressBar mDoProgress;
    private ExamTimeHintPop mHintPop;
    private ImageView mImgBack;
    private RoundedImageView mImgTitle;
    private LinearLayout mLyRight;
    private LinearLayout mLyTips;
    private LinearLayout mLyTypeView;
    private ComputerOptionsAdp mOptionAdp;
    private MaxRecyclerView mOptionRecycler;
    private View mPublicLine;
    private NestedScrollView mPublicScroll;
    private TextView mTvAllNumber;
    private TextView mTvDoNumber;
    private TextView mTvDoProgress;
    private TextView mTvNickName;
    private TextView mTvNoAnswerCount;
    private TextView mTvPublicTitle;
    private TextView mTvQuestionTitle;
    private TextView mTvTestNumber;
    private TextView mTvTestTime;
    private TextView mTvTimeFive;
    private TextView mTvTimeFour;
    private TextView mTvTimeOne;
    private TextView mTvTimeSix;
    private TextView mTvTimeThree;
    private TextView mTvTimeTwo;
    private long startTime;
    private int submitMinute;
    private String testNumber;
    private String title;
    private int mLeftWidth = 199;
    private int mNoAnswerQuestionCount = 0;
    private int mCurrentStageIndex = 0;
    private boolean isNoAnswered = true;
    private boolean isShowStepOneTips = true;
    private Map<String, List<ExesQuestionBean>> mGroupList = new LinkedHashMap();
    private List<ExesQuestionBean> mTempDataList = new ArrayList();
    private List<MockPopTipsBean.MockPopTipsDataBean> mTempPopTipsDataList = new ArrayList();
    private List<String> mTypeList = new ArrayList();
    private long startDoTime = 0;
    private int mCurrentPosition = 0;
    private String lastTime = "";

    /* renamed from: com.psychiatrygarden.activity.ComputerMockTestModeTwoAct$6, reason: invalid class name */
    public class AnonymousClass6 implements ComputerNextDialog.ClickIml {
        public AnonymousClass6() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$mClickIml$0(ObservableEmitter observableEmitter) throws Exception {
            observableEmitter.onNext(CommonUtil.getNetTime());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$mClickIml$1(Date date) throws Exception {
            ComputerMockTestModeTwoAct.this.lambda$getScore$10(date);
        }

        @Override // com.psychiatrygarden.widget.ComputerNextDialog.ClickIml
        public void mClickIml() {
            if (ComputerMockTestModeTwoAct.this.isLogin()) {
                Observable.create(new ObservableOnSubscribe() { // from class: com.psychiatrygarden.activity.p6
                    @Override // io.reactivex.ObservableOnSubscribe
                    public final void subscribe(ObservableEmitter observableEmitter) throws Exception {
                        ComputerMockTestModeTwoAct.AnonymousClass6.lambda$mClickIml$0(observableEmitter);
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.psychiatrygarden.activity.q6
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj) throws Exception {
                        this.f13729c.lambda$mClickIml$1((Date) obj);
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
            ComputerMockTestModeTwoAct.this.updateTime(0L);
            ComputerMockTestModeTwoAct.this.getScore(2);
        }

        @Override // android.os.CountDownTimer
        public void onTick(long millisUntilFinished) {
            ComputerMockTestModeTwoAct.this.updateTime(millisUntilFinished);
        }
    }

    public static /* synthetic */ int access$008(ComputerMockTestModeTwoAct computerMockTestModeTwoAct) {
        int i2 = computerMockTestModeTwoAct.mCurrentStageIndex;
        computerMockTestModeTwoAct.mCurrentStageIndex = i2 + 1;
        return i2;
    }

    private void closeDialog() {
        new XPopup.Builder(this.mContext).asCustom(new ComputerNextDialog(this.mContext, new ComputerNextDialog.ClickIml() { // from class: com.psychiatrygarden.activity.ComputerMockTestModeTwoAct.8
            @Override // com.psychiatrygarden.widget.ComputerNextDialog.ClickIml
            public void mClickIml() {
                SharePreferencesUtils.writeBooleanConfig(CommonParameter.isAddMock, false, ComputerMockTestModeTwoAct.this.mContext);
                ProjectApp.questExamDataList.clear();
                ProjectApp.questExamList.clear();
                EventBus.getDefault().post("closePage");
                ComputerMockTestModeTwoAct.this.finish();
            }

            @Override // com.psychiatrygarden.widget.ComputerNextDialog.ClickIml
            public void mClickLeftIml() {
            }
        }, new SpannableStringBuilder("返回将退出此次考试，是否退出？"), "取消", "确定")).show();
    }

    private int getCurrentSheetViewItemPosition() {
        return ((MockAnswerSheetView) this.mLyTypeView.getChildAt(this.mCurrentStageIndex)).getCurrentPosition();
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
    public void getLockMethod(final long examTime) {
        final String string = this.mTvDoProgress.getText().toString();
        this.mTvTestTime.setText("考试总时间：" + this.durationMinute + "分钟");
        String string2 = this.mTvTestTime.getText().toString();
        final String strSubstring = string2.substring(6, string2.length() - 2);
        final String strSubstring2 = this.mTvDoNumber.getText().toString().substring(3);
        this.mTvNoAnswerCount.getText().toString();
        final String str = this.mNoAnswerQuestionCount + "";
        String string3 = this.mTvTimeOne.getText().toString();
        String string4 = this.mTvTimeTwo.getText().toString();
        String string5 = this.mTvTimeThree.getText().toString();
        String string6 = this.mTvTimeFour.getText().toString();
        String string7 = this.mTvTimeFive.getText().toString();
        String string8 = this.mTvTimeSix.getText().toString();
        int i2 = string3.equals("0") ? 0 : 0 + (Integer.parseInt(string3) * 10 * 60);
        if (!string4.equals("0")) {
            i2 += Integer.parseInt(string4) * 60;
        }
        if (!string5.equals("0")) {
            i2 += Integer.parseInt(string5) * 10;
        }
        if (!string6.equals("0")) {
            i2 += Integer.parseInt(string6);
        }
        if (i2 != 0) {
            this.lastTime += i2 + "分";
        }
        this.lastTime += string7 + string8 + "秒";
        LogUtils.e("last_time", "time===>" + this.lastTime);
        if (!SharePreferencesUtils.readStrConfig("statisticsPermission", this, "0").equals("0")) {
            ComputerMockTestFinishAct.newIntent(this, this.examId, this.title, strSubstring, string, examTime, this.lastTime, strSubstring2, str, this.file, this.testNumber);
            finish();
            return;
        }
        String strConfig = SharePreferencesUtils.readStrConfig("statisticsActiveId", this, "");
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put(ConstantsAPI.WXWebPage.KEY_ACTIVITY_ID, strConfig);
        MemInterface.getInstance().getMemData(this, ajaxParams, true, 2);
        MemInterface.getInstance().setmMoreLockListener(new MemInterface.MoreMethodLisener() { // from class: com.psychiatrygarden.activity.d6
            @Override // com.psychiatrygarden.activity.vip.Utils.MemInterface.MoreMethodLisener
            public final void mMoreMethodLock() {
                this.f12230a.lambda$getLockMethod$14(strSubstring, string, examTime, strSubstring2, str);
            }
        });
    }

    private void getPopTips() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("exam_id", this.examId);
        YJYHttpUtils.get(this, NetworkRequestsURL.getMockPopData, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.ComputerMockTestModeTwoAct.4
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                ComputerMockTestModeTwoAct.this.hideProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                ComputerMockTestModeTwoAct.this.showProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String t2) {
                super.onSuccess((AnonymousClass4) t2);
                try {
                    MockPopTipsBean mockPopTipsBean = (MockPopTipsBean) new Gson().fromJson(t2, MockPopTipsBean.class);
                    if (mockPopTipsBean.getCode().equals("200")) {
                        ComputerMockTestModeTwoAct.this.mTempPopTipsDataList.addAll(mockPopTipsBean.getData());
                        int size = mockPopTipsBean.getData().size();
                        StringBuilder sb = new StringBuilder();
                        sb.append("本试卷有");
                        sb.append(CommonUtil.convertNumbersToUpperCase(size + ""));
                        sb.append("部分组成");
                        String string = sb.toString();
                        for (int i2 = 0; i2 < mockPopTipsBean.getData().size(); i2++) {
                            string = string + "\n第" + CommonUtil.convertNumbersToUpperCase(mockPopTipsBean.getData().get(i2).getSort()) + "部分：" + mockPopTipsBean.getData().get(i2).getPart();
                        }
                        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(string);
                        if (SkinManager.getCurrentSkinType(ComputerMockTestModeTwoAct.this.mContext) == 0) {
                            spannableStringBuilder.setSpan(new ForegroundColorSpan(Color.parseColor("#F95843")), 4, String.valueOf(size).length() + 6, 34);
                        } else {
                            spannableStringBuilder.setSpan(new ForegroundColorSpan(Color.parseColor("#B2575C")), 4, String.valueOf(size).length() + 6, 34);
                        }
                        ComputerMockTestModeTwoAct.this.showPartTipsDailog(spannableStringBuilder, "试卷描述");
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                ComputerMockTestModeTwoAct.this.hideProgressDialog();
            }
        });
    }

    private void getQuestionData() {
        YJYHttpUtils.get(getApplicationContext(), this.file, new AjaxParams(), new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.ComputerMockTestModeTwoAct.3
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                ComputerMockTestModeTwoAct.this.hideProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                ComputerMockTestModeTwoAct.this.showProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String t2) {
                super.onSuccess((AnonymousClass3) t2);
                try {
                    List list = (List) new Gson().fromJson(DesUtil.decode(CommonParameter.DES_KEY_VERIFY, new JSONObject(t2).optString("data")), new TypeToken<List<ExesQuestionBean>>() { // from class: com.psychiatrygarden.activity.ComputerMockTestModeTwoAct.3.1
                    }.getType());
                    Collections.sort(list);
                    if (!list.isEmpty()) {
                        for (int i2 = 0; i2 < list.size(); i2++) {
                            String number = ((ExesQuestionBean) list.get(i2)).getNumber();
                            if (TextUtils.isEmpty(number) || !number.matches(RegexPool.NUMBERS)) {
                                ((ExesQuestionBean) list.get(i2)).setNumber((i2 + 1) + "");
                            }
                            if (ComputerMockTestModeTwoAct.this.mGroupList.containsKey(((ExesQuestionBean) list.get(i2)).getPart())) {
                                ((List) ComputerMockTestModeTwoAct.this.mGroupList.get(((ExesQuestionBean) list.get(i2)).getPart())).add((ExesQuestionBean) list.get(i2));
                            } else {
                                ComputerMockTestModeTwoAct.this.mTypeList.add(((ExesQuestionBean) list.get(i2)).getPart());
                                ArrayList arrayList = new ArrayList();
                                arrayList.add((ExesQuestionBean) list.get(i2));
                                ComputerMockTestModeTwoAct.this.mGroupList.put(((ExesQuestionBean) list.get(i2)).getPart(), arrayList);
                            }
                        }
                        ComputerMockTestModeTwoAct.this.mTempDataList.addAll(list);
                        ComputerMockTestModeTwoAct.this.mCurrentStageIndex = 0;
                        ComputerMockTestModeTwoAct.this.initQuestionType();
                        ComputerMockTestModeTwoAct computerMockTestModeTwoAct = ComputerMockTestModeTwoAct.this;
                        computerMockTestModeTwoAct.initQuestioData((ExesQuestionBean) ((List) computerMockTestModeTwoAct.mGroupList.get(ComputerMockTestModeTwoAct.this.mTypeList.get(ComputerMockTestModeTwoAct.this.mCurrentStageIndex))).get(0));
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                    ComputerMockTestModeTwoAct.this.AlertToast("题型解析错误");
                    ComputerMockTestModeTwoAct.this.finish();
                }
                ComputerMockTestModeTwoAct.this.hideProgressDialog();
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
            Observable.create(new ObservableOnSubscribe() { // from class: com.psychiatrygarden.activity.h6
                @Override // io.reactivex.ObservableOnSubscribe
                public final void subscribe(ObservableEmitter observableEmitter) throws Exception {
                    ComputerMockTestModeTwoAct.lambda$getScore$9(observableEmitter);
                }
            }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.psychiatrygarden.activity.i6
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) throws Exception {
                    this.f12498c.lambda$getScore$10((Date) obj);
                }
            });
            mCommDialog("答题时间已到，系统自动交卷！", 2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public List<ExesQuestionBean> getSheetDataList() {
        return ((MockAnswerSheetView) this.mLyTypeView.getChildAt(this.mCurrentStageIndex)).getDatas();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public ExesQuestionBean getSheetItemBean(int currentPosition) {
        MockAnswerSheetView mockAnswerSheetView = (MockAnswerSheetView) this.mLyTypeView.getChildAt(this.mCurrentStageIndex);
        this.mCurrentPosition = currentPosition;
        return mockAnswerSheetView.getItemBean(currentPosition);
    }

    private boolean getSheetItemQuestionType() {
        return ((MockAnswerSheetView) this.mLyTypeView.getChildAt(this.mCurrentStageIndex)).isSpecialType();
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
            this.mImgTitle.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.e6
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f12272c.lambda$initQuestioData$1(data, view);
                }
            });
        }
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
        updateSheetView(this.mCurrentPosition);
        this.isNoAnswered = TextUtils.isEmpty(data.getOwnAns());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initQuestionType() {
        this.mLyTypeView.removeAllViews();
        Iterator<Map.Entry<String, List<ExesQuestionBean>>> it = this.mGroupList.entrySet().iterator();
        while (it.hasNext()) {
            List<ExesQuestionBean> value = it.next().getValue();
            final MockAnswerSheetView mockAnswerSheetView = new MockAnswerSheetView(this);
            int childCount = this.mLyTypeView.getChildCount();
            boolean z2 = false;
            int i2 = this.mLyTypeView.getChildCount() == 0 ? 0 : -1;
            if (this.mLyTypeView.getChildCount() == 0) {
                z2 = true;
            }
            mockAnswerSheetView.setData(value, childCount, i2, z2);
            mockAnswerSheetView.setOnSheetItemClickLisenter(new MockAnswerSheetView.OnSheetItemClickLisenter() { // from class: com.psychiatrygarden.activity.ComputerMockTestModeTwoAct.2
                @Override // com.psychiatrygarden.widget.MockAnswerSheetView.OnSheetItemClickLisenter
                public void itemSheetClick(int position, int currentStep, ExesQuestionBean item, boolean isSpecialType) {
                    if (ComputerMockTestModeTwoAct.this.mCurrentStageIndex > currentStep) {
                        ComputerMockTestModeTwoAct.this.jumpStageTipsDialog(true);
                        return;
                    }
                    if (ComputerMockTestModeTwoAct.this.mCurrentStageIndex < currentStep) {
                        ComputerMockTestModeTwoAct.this.isDoAllCurrentStepQuestion();
                        return;
                    }
                    if (position == ComputerMockTestModeTwoAct.this.mCurrentPosition) {
                        return;
                    }
                    ComputerMockTestModeTwoAct computerMockTestModeTwoAct = ComputerMockTestModeTwoAct.this;
                    computerMockTestModeTwoAct.getSheetItemBean(computerMockTestModeTwoAct.mCurrentPosition).getType();
                    if (isSpecialType) {
                        ComputerMockTestModeTwoAct.this.jumpStageTipsDialog(false);
                        return;
                    }
                    ComputerMockTestModeTwoAct.this.mCurrentPosition = position;
                    mockAnswerSheetView.setCurrentPosition(position);
                    ComputerMockTestModeTwoAct.this.initQuestioData(item);
                }
            });
            this.mLyTypeView.addView(mockAnswerSheetView);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void isDoAllCurrentStepQuestion() {
        Iterator<ExesQuestionBean> it = getSheetDataList().iterator();
        int i2 = 0;
        while (it.hasNext()) {
            if (TextUtils.isEmpty(it.next().getOwnAns())) {
                i2++;
            }
        }
        if (i2 > 0) {
            jumpStageTipsDialog(true);
            return;
        }
        if (isLogin()) {
            ((MockAnswerSheetView) this.mLyTypeView.getChildAt(this.mCurrentStageIndex)).updateCurrentUi(true);
            this.mCurrentStageIndex++;
            this.mCurrentPosition = 0;
            initQuestioData(getSheetDataList().get(0));
            ((MockAnswerSheetView) this.mLyTypeView.getChildAt(this.mCurrentStageIndex)).updateCurrentUi(false);
            showPartTipsDailog(new SpannableStringBuilder(this.mTempPopTipsDataList.get(this.mCurrentStageIndex).getDesc()), this.mTempPopTipsDataList.get(this.mCurrentStageIndex).getPart());
        }
    }

    private boolean isGroupLastData(String questionId) {
        List<ExesQuestionBean> sheetDataList = getSheetDataList();
        return sheetDataList.get(sheetDataList.size() - 1).getQuestion_id().equals(questionId);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void jumpStageTipsDialog(boolean isNext) {
        showPartTipsDailog(new SpannableStringBuilder(isNext ? "题型间不可回退，请继续完成本部分试题" : "题型间不可回退，请按照顺序作答"), "温馨提示");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getLockMethod$14(String str, String str2, long j2, String str3, String str4) {
        ComputerMockTestFinishAct.newIntent(this, this.examId, this.title, str, str2, j2, this.lastTime, str3, str4, this.file, this.testNumber);
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$getScore$9(ObservableEmitter observableEmitter) throws Exception {
        observableEmitter.onNext(CommonUtil.getNetTime());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
        ExesQuestionBean sheetItemBean = getSheetItemBean(this.mCurrentPosition);
        int i3 = Integer.parseInt(sheetItemBean.getNumber()) - 1;
        int length = sheetItemBean.getAnswer().length();
        String type = sheetItemBean.getType();
        List<ExesQuestionBean.OptionBean> option = sheetItemBean.getOption();
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
            char[] charArray = sheetItemBean.getAnswer().replace(",", "").toCharArray();
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
            sheetItemBean.setOwnAns(string);
            sheetItemBean.setIsRight(TextUtils.equals(new String(charArray), new String(charArray2)) ? "1" : "0");
        } else {
            int i6 = 0;
            for (int i7 = 0; i7 < option.size(); i7++) {
                if (option.get(i7).getType().equals("1")) {
                    i6++;
                }
            }
            if (i6 <= 0) {
                option.get(i2).setType("1");
                sheetItemBean.setOwnAns(option.get(i2).getKey());
                sheetItemBean.setIsRight(option.get(i2).getKey().equals(sheetItemBean.getAnswer()) ? "1" : "0");
            } else if (!option.get(i2).getType().equals("1")) {
                while (i4 < option.size()) {
                    option.get(i4).setType("0");
                    i4++;
                }
                option.get(i2).setType("1");
                sheetItemBean.setOwnAns(option.get(i2).getKey());
                sheetItemBean.setIsRight(option.get(i2).getKey().equals(sheetItemBean.getAnswer()) ? "1" : "0");
            }
        }
        this.mTempDataList.get(i3).setOption(sheetItemBean.getOption());
        long jCurrentTimeMillis = (System.currentTimeMillis() / 1000) - this.startDoTime;
        this.mTempDataList.get(i3).setDoDuration(jCurrentTimeMillis + "");
        getSheetDataList().get(this.mCurrentPosition).setOption(sheetItemBean.getOption());
        this.mOptionAdp.notifyDataSetChanged();
        updateLeftDataByOptions(sheetItemBean);
        this.isNoAnswered = TextUtils.isEmpty(sheetItemBean.getOwnAns());
        LogUtils.e("user_answered", "用户是否选了答案：" + this.isNoAnswered);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initQuestioData$1(ExesQuestionBean exesQuestionBean, View view) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(exesQuestionBean.getTitle_img());
        ImageViewerPopupViewCustom xPopupImageLoader = new ImageViewerPopupViewCustom(this.mContext).setSingleSrcView(null, Integer.valueOf(R.mipmap.ic_order_default)).setXPopupImageLoader(new ImageLoaderUtilsCustom());
        xPopupImageLoader.popupInfo = new PopupInfo();
        xPopupImageLoader.setImageUrls(arrayList).setSrcView(null, 0).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$mCommDialog$11(int i2, AlertDialog alertDialog, View view) {
        if (i2 != 1) {
            alertDialog.dismiss();
        } else {
            alertDialog.dismiss();
            SharePreferencesUtils.writeBooleanConfig(CommonParameter.EXAMIDTRUE, false, this.mContext);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$2(View view) {
        closeDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$3(View view) {
        this.mLyTips.setVisibility(8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$4(View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        if (this.mCurrentPosition <= 0) {
            if (this.mCurrentStageIndex == 0) {
                ToastUtil.shortToast(this, "已是第一题");
                return;
            } else {
                jumpStageTipsDialog(true);
                return;
            }
        }
        if (getSheetItemQuestionType()) {
            jumpStageTipsDialog(false);
            return;
        }
        int currentSheetViewItemPosition = getCurrentSheetViewItemPosition() - 1;
        this.mCurrentPosition = currentSheetViewItemPosition;
        initQuestioData(getSheetItemBean(currentSheetViewItemPosition));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$5(View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        if (this.mCurrentStageIndex < this.mTypeList.size() - 1) {
            if (isGroupLastData(getSheetItemBean(this.mCurrentPosition).getQuestion_id())) {
                isDoAllCurrentStepQuestion();
                return;
            }
            if (getSheetItemQuestionType() && this.isNoAnswered) {
                jumpStageTipsDialog(false);
                return;
            }
            int currentSheetViewItemPosition = getCurrentSheetViewItemPosition() + 1;
            this.mCurrentPosition = currentSheetViewItemPosition;
            initQuestioData(getSheetItemBean(currentSheetViewItemPosition));
            return;
        }
        if (isGroupLastData(getSheetItemBean(this.mCurrentPosition).getQuestion_id())) {
            ToastUtil.shortToast(this, "已是最后一题");
            return;
        }
        if (getSheetItemQuestionType() && this.isNoAnswered) {
            jumpStageTipsDialog(false);
            return;
        }
        int currentSheetViewItemPosition2 = getCurrentSheetViewItemPosition() + 1;
        this.mCurrentPosition = currentSheetViewItemPosition2;
        initQuestioData(getSheetItemBean(currentSheetViewItemPosition2));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$6(View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        getScore(1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$7(View view) {
        showHelpDailog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$8(View view) {
        new XPopup.Builder(this.mContext).dismissOnTouchOutside(Boolean.TRUE).asCustom(new CaculatorDialog(this.mContext)).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$showHelpDailog$13() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showPartTipsDailog$12() {
        int i2 = this.mCurrentStageIndex;
        if (i2 == 0 && this.isShowStepOneTips) {
            showPartTipsDailog(new SpannableStringBuilder(this.mTempPopTipsDataList.get(this.mCurrentStageIndex).getDesc()), this.mTempPopTipsDataList.get(i2).getPart());
            this.isShowStepOneTips = false;
        }
    }

    public static void newIntent(Context context, String examId, String title, String dateEnd, long durationMin, String submitMinute, String file, String number) {
        Intent intent = new Intent(context, (Class<?>) ComputerMockTestModeTwoAct.class);
        intent.putExtra("examId", examId);
        intent.putExtra("title", title);
        intent.putExtra("dateEnd", dateEnd);
        intent.putExtra("durationMinute", durationMin);
        intent.putExtra("submitMinute", submitMinute);
        intent.putExtra("file", file);
        intent.putExtra(Constant.LOGIN_ACTIVITY_NUMBER, number);
        context.startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showAgainFinishTestDailog() {
        new XPopup.Builder(this.mContext).asCustom(new ComputerNextDialog(this.mContext, new AnonymousClass6(), new SpannableStringBuilder("您确定要交卷吗？"), "取消", "确定")).show();
    }

    private void showFinishTestDailog() {
        SpannableStringBuilder spannableStringBuilder;
        SpannableStringBuilder spannableStringBuilder2 = new SpannableStringBuilder("交卷以后不能再继续答题，您确定要交卷吗？");
        if (this.mNoAnswerQuestionCount != this.mTempDataList.size()) {
            if (this.mNoAnswerQuestionCount > 0) {
                SpannableStringBuilder spannableStringBuilder3 = new SpannableStringBuilder(("您还存在未答完的试题：" + this.mNoAnswerQuestionCount + "道题未答，") + "交卷以后不能再继续答题，您确定要交卷吗？");
                if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
                    spannableStringBuilder3.setSpan(new ForegroundColorSpan(Color.parseColor("#F95843")), 11, String.valueOf(this.mNoAnswerQuestionCount).length() + 11, 34);
                } else {
                    spannableStringBuilder3.setSpan(new ForegroundColorSpan(Color.parseColor("#B2575C")), 11, String.valueOf(this.mNoAnswerQuestionCount).length() + 11, 34);
                }
                spannableStringBuilder = spannableStringBuilder3;
            }
            new XPopup.Builder(this.mContext).asCustom(new ComputerNextDialog(this.mContext, new ComputerNextDialog.ClickIml() { // from class: com.psychiatrygarden.activity.ComputerMockTestModeTwoAct.5
                @Override // com.psychiatrygarden.widget.ComputerNextDialog.ClickIml
                public void mClickIml() {
                }

                @Override // com.psychiatrygarden.widget.ComputerNextDialog.ClickIml
                public void mClickLeftIml() {
                    ComputerMockTestModeTwoAct.this.showAgainFinishTestDailog();
                }
            }, spannableStringBuilder, "我要交卷", "暂不交卷")).show();
        }
        spannableStringBuilder2 = new SpannableStringBuilder("您还没有答题，请答题之后再提交！");
        spannableStringBuilder = spannableStringBuilder2;
        new XPopup.Builder(this.mContext).asCustom(new ComputerNextDialog(this.mContext, new ComputerNextDialog.ClickIml() { // from class: com.psychiatrygarden.activity.ComputerMockTestModeTwoAct.5
            @Override // com.psychiatrygarden.widget.ComputerNextDialog.ClickIml
            public void mClickIml() {
            }

            @Override // com.psychiatrygarden.widget.ComputerNextDialog.ClickIml
            public void mClickLeftIml() {
                ComputerMockTestModeTwoAct.this.showAgainFinishTestDailog();
            }
        }, spannableStringBuilder, "我要交卷", "暂不交卷")).show();
    }

    private void showHelpDailog() {
        new XPopup.Builder(this.mContext).asCustom(new ComputerHelpDialog(this.mContext, new ComputerHelpDialog.ClickIml() { // from class: com.psychiatrygarden.activity.j6
            @Override // com.psychiatrygarden.widget.ComputerHelpDialog.ClickIml
            public final void mClickIml() {
                ComputerMockTestModeTwoAct.lambda$showHelpDailog$13();
            }
        }, "确定")).show();
    }

    private void showNextStageDailog() {
        SpannableStringBuilder spannableStringBuilder;
        Iterator<ExesQuestionBean> it = getSheetDataList().iterator();
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
        new XPopup.Builder(this.mContext).asCustom(new ComputerNextDialog(this.mContext, new ComputerNextDialog.ClickIml() { // from class: com.psychiatrygarden.activity.ComputerMockTestModeTwoAct.7
            @Override // com.psychiatrygarden.widget.ComputerNextDialog.ClickIml
            public void mClickIml() {
                if (ComputerMockTestModeTwoAct.this.isLogin()) {
                    ((MockAnswerSheetView) ComputerMockTestModeTwoAct.this.mLyTypeView.getChildAt(ComputerMockTestModeTwoAct.this.mCurrentStageIndex)).updateCurrentUi(true);
                    ComputerMockTestModeTwoAct.access$008(ComputerMockTestModeTwoAct.this);
                    ComputerMockTestModeTwoAct.this.mCurrentPosition = 0;
                    ComputerMockTestModeTwoAct computerMockTestModeTwoAct = ComputerMockTestModeTwoAct.this;
                    computerMockTestModeTwoAct.initQuestioData((ExesQuestionBean) computerMockTestModeTwoAct.getSheetDataList().get(0));
                    ((MockAnswerSheetView) ComputerMockTestModeTwoAct.this.mLyTypeView.getChildAt(ComputerMockTestModeTwoAct.this.mCurrentStageIndex)).updateCurrentUi(false);
                    String part = ((MockPopTipsBean.MockPopTipsDataBean) ComputerMockTestModeTwoAct.this.mTempPopTipsDataList.get(ComputerMockTestModeTwoAct.this.mCurrentStageIndex)).getPart();
                    ComputerMockTestModeTwoAct.this.showPartTipsDailog(new SpannableStringBuilder(((MockPopTipsBean.MockPopTipsDataBean) ComputerMockTestModeTwoAct.this.mTempPopTipsDataList.get(ComputerMockTestModeTwoAct.this.mCurrentStageIndex)).getDesc()), part);
                }
            }

            @Override // com.psychiatrygarden.widget.ComputerNextDialog.ClickIml
            public void mClickLeftIml() {
            }
        }, spannableStringBuilder, "继续作答", "进入下一阶段")).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showPartTipsDailog(SpannableStringBuilder content, String title) {
        new XPopup.Builder(this.mContext).asCustom(new ComputerMockPopTipsDialog(this.mContext, new ComputerMockPopTipsDialog.ClickIml() { // from class: com.psychiatrygarden.activity.f6
            @Override // com.psychiatrygarden.widget.ComputerMockPopTipsDialog.ClickIml
            public final void mClickIml() {
                this.f12344a.lambda$showPartTipsDailog$12();
            }
        }, content, title)).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: submitData, reason: merged with bridge method [inline-methods] */
    public void lambda$getScore$10(Date date) {
        ArrayList arrayList = new ArrayList();
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("exam_id", this.examId);
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
        final long jCurrentTimeMillis = (System.currentTimeMillis() / 1000) - this.startTime;
        LogUtils.d(getClass().getSimpleName(), "考试时间：" + (jCurrentTimeMillis / 60) + " 分钟");
        ajaxParams.put(CommonParameter.EXAM_TIME, String.valueOf(jCurrentTimeMillis));
        ajaxParams.put("right", String.valueOf(i2));
        ajaxParams.put("answer_num", String.valueOf(i3));
        ajaxParams.put("answer", new Gson().toJson(arrayList));
        showProgressDialog("提交中", false);
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.mPostBatchUrl, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.ComputerMockTestModeTwoAct.9
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                ComputerMockTestModeTwoAct.this.hideProgressDialog();
                ComputerMockTestModeTwoAct.this.AlertToast("答题记录上传失败，请手动交卷，否则答题记录将不被保存！");
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass9) s2);
                try {
                    if (new JSONObject(s2).optString("code").equals("200")) {
                        SharePreferencesUtils.writeBooleanConfig(CommonParameter.isAddMock, false, ComputerMockTestModeTwoAct.this.mContext);
                        ProjectApp.questExamDataList.clear();
                        ProjectApp.questExamList.clear();
                        ComputerMockTestModeTwoAct.this.getLockMethod(jCurrentTimeMillis);
                    } else {
                        ComputerMockTestModeTwoAct.this.hideProgressDialog();
                        ComputerMockTestModeTwoAct.this.AlertToast(new JSONObject(s2).optString("message"));
                    }
                } catch (Exception e3) {
                    ComputerMockTestModeTwoAct.this.hideProgressDialog();
                    e3.printStackTrace();
                }
            }
        });
    }

    private void updateLeftDataByOptions(ExesQuestionBean currentQuestionItem) {
        if (!currentQuestionItem.isChoosedAnswer()) {
            int i2 = this.mNoAnswerQuestionCount;
            if (i2 > 0) {
                this.mNoAnswerQuestionCount = i2 - 1;
            } else {
                this.mNoAnswerQuestionCount = 0;
            }
            this.mTvNoAnswerCount.setText("剩余：" + this.mNoAnswerQuestionCount);
            int size = this.mTempDataList.size();
            int i3 = size - this.mNoAnswerQuestionCount;
            this.mTvDoNumber.setText("已答：" + i3);
            currentQuestionItem.setAnswer_mode(2);
            currentQuestionItem.setChoosedAnswer(true);
            this.mDoProgress.setProgress(i3);
            String str = new DecimalFormat(DictionaryFactory.SHARP).format((Double.valueOf(i3).doubleValue() / Double.valueOf(size).doubleValue()) * 100.0d);
            this.mTvDoProgress.setText(str + "%");
        }
        updateSheetView(this.mCurrentPosition);
    }

    private void updateSheetView(int currentPosition) {
        MockAnswerSheetView mockAnswerSheetView = (MockAnswerSheetView) this.mLyTypeView.getChildAt(this.mCurrentStageIndex);
        mockAnswerSheetView.setCurrentPosition(currentPosition);
        mockAnswerSheetView.updateAdp(this.mCurrentPosition);
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
        this.examId = getIntent().getStringExtra("examId");
        this.dateEnd = getIntent().getStringExtra("dateEnd");
        this.durationMinute = getIntent().getLongExtra("durationMinute", 0L);
        this.file = getIntent().getStringExtra("file");
        this.title = getIntent().getStringExtra("title");
        String stringExtra = getIntent().getStringExtra("submitMinute");
        this.testNumber = getIntent().getStringExtra(Constant.LOGIN_ACTIVITY_NUMBER);
        if (stringExtra == null || !stringExtra.matches("^\\d*$")) {
            this.submitMinute = 30;
        } else {
            this.submitMinute = Integer.parseInt(stringExtra);
        }
        this.mImgBack = (ImageView) findViewById(R.id.img_back);
        this.mLyRight = (LinearLayout) findViewById(R.id.ly_right);
        this.mTvTestTime = (TextView) findViewById(R.id.tv_test_time);
        this.mTvNoAnswerCount = (TextView) findViewById(R.id.tv_no_answer_count);
        this.mTvAllNumber = (TextView) findViewById(R.id.tv_all_number);
        this.mTvDoNumber = (TextView) findViewById(R.id.tv_do_number);
        this.mLyTypeView = (LinearLayout) findViewById(R.id.ly_type_view);
        this.mTvTimeOne = (TextView) findViewById(R.id.tv_time_one);
        this.mTvTimeTwo = (TextView) findViewById(R.id.tv_time_two);
        this.mTvTimeThree = (TextView) findViewById(R.id.tv_time_three);
        this.mTvTimeFour = (TextView) findViewById(R.id.tv_time_four);
        this.mTvTimeFive = (TextView) findViewById(R.id.tv_time_five);
        this.mTvTimeSix = (TextView) findViewById(R.id.tv_time_six);
        this.mPublicScroll = (NestedScrollView) findViewById(R.id.sc_public_title);
        this.mTvPublicTitle = (TextView) findViewById(R.id.tv_public_title);
        this.mPublicLine = findViewById(R.id.public_line);
        this.mTvQuestionTitle = (TextView) findViewById(R.id.tv_question_title);
        this.mBtnPrevious = (TextView) findViewById(R.id.btn_previous);
        this.mBtnNext = (TextView) findViewById(R.id.btn_next);
        this.mBtnFinish = (TextView) findViewById(R.id.btn_finish);
        this.mOptionRecycler = (MaxRecyclerView) findViewById(R.id.qlistview);
        this.mImgTitle = (RoundedImageView) findViewById(R.id.img_title);
        this.mTvTestNumber = (TextView) findViewById(R.id.tv_test_number);
        this.mTvNickName = (TextView) findViewById(R.id.tv_nickname);
        this.mBtnHelp = (TextView) findViewById(R.id.btn_help);
        this.mBtnCalculator = (TextView) findViewById(R.id.btn_calculator);
        this.mDoProgress = (ProgressBar) findViewById(R.id.do_progress);
        this.mTvDoProgress = (TextView) findViewById(R.id.tv_do_progress);
        this.mLyTips = (LinearLayout) findViewById(R.id.ly_tips);
        this.mBtnCloseTips = (TextView) findViewById(R.id.btn_close_tips);
        this.mTvTestNumber.setText("准考证号：" + this.testNumber);
        this.mTvNickName.setText("学生姓名：" + UserConfig.getInstance().getUser().getNickname());
        this.mTvTestTime.setText("考试总时间：" + this.durationMinute + "分钟");
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
                if (this.mGroupList.containsKey(exesQuestionBean.getPart())) {
                    this.mGroupList.get(exesQuestionBean.getPart()).add(exesQuestionBean);
                } else {
                    this.mTypeList.add(exesQuestionBean.getPart());
                    ArrayList arrayList = new ArrayList();
                    arrayList.add(exesQuestionBean);
                    this.mGroupList.put(exesQuestionBean.getPart(), arrayList);
                }
            }
            this.mCurrentStageIndex = 0;
            initQuestionType();
            initQuestioData(this.mGroupList.get(this.mTypeList.get(this.mCurrentStageIndex)).get(0));
            this.startTime = System.currentTimeMillis() / 1000;
            this.mNoAnswerQuestionCount = ProjectApp.questExamDataList.size();
            this.mTvNoAnswerCount.setText("未答题数：" + this.mNoAnswerQuestionCount);
            this.mTvAllNumber.setText("总题数：" + this.mTempDataList.size());
            this.mTvDoNumber.setText("已答：0");
            this.mDoProgress.setMax(this.mTempDataList.size());
        }
        long jCurrentTimeMillis = System.currentTimeMillis() / 1000;
        long j2 = !TextUtils.isEmpty(this.dateEnd) ? Long.parseLong(this.dateEnd) : (this.durationMinute * 60) + jCurrentTimeMillis;
        if (j2 > 0) {
            long j3 = j2 - jCurrentTimeMillis;
            if (j3 * 1000 < this.durationMinute * 60 * 1000) {
                this.mHintPop = (ExamTimeHintPop) new XPopup.Builder(this).setPopupCallback(new SimpleCallback() { // from class: com.psychiatrygarden.activity.ComputerMockTestModeTwoAct.1
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
            this.countDownTimer = new TimerCount(1000 * j5 * 60, 1000L);
        } else {
            this.countDownTimer = new TimerCount(1000 * j4, 1000L);
        }
        this.countDownTimer.start();
        this.mOptionAdp.setOnItemClickListener(new OnItemClickListener() { // from class: com.psychiatrygarden.activity.a6
            @Override // com.chad.library.adapter.base.listener.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
                this.f10985c.lambda$init$0(baseQuickAdapter, view, i2);
            }
        });
        getPopTips();
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
            textView2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.g6
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f12428c.lambda$mCommDialog$11(type, alertDialogCreate, view);
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
        setContentView(R.layout.layout_computer_mock_mode_two);
        getWindow().addFlags(1024);
        if (SkinManager.getCurrentSkinType(this) == 0) {
            getWindow().getDecorView().setSystemUiVisibility(8192);
        }
        this.mLeftWidth = ScreenUtil.getPxByDp((Context) this, 199);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.mImgBack.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.k6
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12580c.lambda$setListenerForWidget$2(view);
            }
        });
        this.mBtnCloseTips.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.l6
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12656c.lambda$setListenerForWidget$3(view);
            }
        });
        this.mBtnPrevious.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.m6
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12689c.lambda$setListenerForWidget$4(view);
            }
        });
        this.mBtnNext.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.n6
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13038c.lambda$setListenerForWidget$5(view);
            }
        });
        this.mBtnFinish.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.o6
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13071c.lambda$setListenerForWidget$6(view);
            }
        });
        this.mBtnHelp.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.b6
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11098c.lambda$setListenerForWidget$7(view);
            }
        });
        this.mBtnCalculator.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.c6
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11137c.lambda$setListenerForWidget$8(view);
            }
        });
    }
}
