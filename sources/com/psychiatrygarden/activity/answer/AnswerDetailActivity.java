package com.psychiatrygarden.activity.answer;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.viewpager.widget.ViewPager;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.TestStatisticsActivity;
import com.psychiatrygarden.bean.AnsweredQuestionBean;
import com.psychiatrygarden.bean.AnsweredQuestionBeanDao;
import com.psychiatrygarden.bean.QuestionInfoBean;
import com.psychiatrygarden.bean.QuestionInfoBeanDao;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.fragmenthome.BaseViewPagerAdapter;
import com.psychiatrygarden.interfaceclass.onDialogShareClickListener;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.widget.CustomDialog;
import com.psychiatrygarden.widget.ViewPagerCompat;
import com.tencent.connect.common.Constants;
import com.yikaobang.yixue.R;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.query.QueryBuilder;
import de.greenrobot.dao.query.WhereCondition;
import de.greenrobot.event.EventBus;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes5.dex */
public class AnswerDetailActivity extends BaseActivity {
    private TextView include_btn_right_tv;
    private TextView include_title_center;
    private long[] list_questionid;
    private BaseViewPagerAdapter mAdapter;
    private ImageView mBtnLeft;
    private Disposable mDisposable;
    private String modletype;
    private TextView questiondetails_tv_title;
    private TextView questiondetails_tv_title_gufen;
    private Double score;
    private Double scoretotal;
    private ViewPagerCompat viewPager;
    private int position = 0;
    private String year = "";

    public AnswerDetailActivity() {
        Double dValueOf = Double.valueOf(0.0d);
        this.score = dValueOf;
        this.scoretotal = dValueOf;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$handInDialog$5(CustomDialog customDialog, View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        customDialog.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$handInDialog$6(CustomDialog customDialog, View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        customDialog.dismissNoAnimaltion();
        double dDoubleValue = (this.score.doubleValue() / this.scoretotal.doubleValue()) * 100.0d;
        DecimalFormat decimalFormat = new DecimalFormat("#0.00");
        ProjectApp.listSubmitData.clear();
        Intent intent = new Intent(this.mContext, (Class<?>) TestStatisticsActivity.class);
        intent.putExtra("year", getIntent().getStringExtra("year"));
        intent.putExtra("list", this.list_questionid);
        intent.putExtra("title", getIntent().getStringExtra("title"));
        intent.putExtra("score", "" + decimalFormat.format(this.score));
        intent.putExtra("scoretotal", "" + decimalFormat.format(this.scoretotal));
        intent.putExtra("defenlv", decimalFormat.format(dDoubleValue));
        if (TextUtils.isEmpty(getIntent().getStringExtra("unit"))) {
            intent.putExtra("unit", "");
        } else {
            intent.putExtra("unit", getIntent().getStringExtra("unit"));
        }
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$handInSubDialog$3(CustomDialog customDialog, View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        customDialog.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$handInSubDialog$4(CustomDialog customDialog, View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        customDialog.dismissNoAnimaltion();
        ProjectApp.listSubmitData.clear();
        EventBus.getDefault().post("MatchFetchTat");
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(int i2) {
        if (i2 == 1 && getIntent().getStringExtra("from") == null) {
            AlertToast("已为最后一题");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initTitle$1(View view) {
        handInDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initTitle$2(View view) {
        handInSubDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setDataAdapter$7(ObservableEmitter observableEmitter) throws Exception {
        if (this.list_questionid.length > 0) {
            ArrayList arrayList = new ArrayList();
            for (int i2 = 0; i2 < this.list_questionid.length; i2++) {
                QuestionInfoBean questionInfoBeanLoadByRowId = ProjectApp.mTiKuDaoSession.getQuestionInfoBeanDao().loadByRowId(this.list_questionid[i2]);
                if (questionInfoBeanLoadByRowId != null) {
                    Bundle bundle = new Bundle();
                    bundle.putLong("question_id", questionInfoBeanLoadByRowId.getQuestion_id().longValue());
                    bundle.putString("total", getIntent().getExtras().getString("totalCount"));
                    bundle.putInt("position", i2);
                    bundle.putString("type", "" + getIntent().getExtras().getString("modletype"));
                    bundle.putBoolean("ISPractice", getIntent().getBooleanExtra("ISPractice", false));
                    bundle.putBoolean("isXitong", getIntent().getExtras().getBoolean("isXitong", false));
                    bundle.putString("chapter_id", getIntent().getExtras().getString("chapter_id"));
                    try {
                        bundle.putBoolean("iszhongyibank", getIntent().getBooleanExtra("iszhongyibank", false));
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                    if ("1".equals(questionInfoBeanLoadByRowId.getType()) || "2".equals(questionInfoBeanLoadByRowId.getType())) {
                        arrayList.add(new BaseViewPagerAdapter.PagerInfo("选择题", AnswerQuestionFragment.class, bundle));
                    } else {
                        arrayList.add(new BaseViewPagerAdapter.PagerInfo("主观题", AnswerNQuestionFragment.class, bundle));
                    }
                }
            }
            observableEmitter.onNext(arrayList);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setDataAdapter$8(List list) throws Exception {
        BaseViewPagerAdapter baseViewPagerAdapter = new BaseViewPagerAdapter(this, getSupportFragmentManager(), list);
        this.mAdapter = baseViewPagerAdapter;
        this.viewPager.setAdapter(baseViewPagerAdapter);
        this.viewPager.setCurrentItem(this.position);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$9(View view) {
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:211:0x0440  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void setQuestionExplan() {
        /*
            Method dump skipped, instructions count: 1144
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.psychiatrygarden.activity.answer.AnswerDetailActivity.setQuestionExplan():void");
    }

    public void handInDialog() {
        long jCount;
        int i2;
        long jCount2;
        new ArrayList();
        new ArrayList();
        final CustomDialog customDialog = new CustomDialog(this.mContext, 2);
        customDialog.setCancelable(false);
        customDialog.isOutTouchDismiss(false);
        Double dValueOf = Double.valueOf(0.0d);
        this.score = dValueOf;
        this.scoretotal = dValueOf;
        if (SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, this.mContext).equals(Constants.VIA_REPORT_TYPE_SHARE_TO_QQ)) {
            QueryBuilder<AnsweredQuestionBean> queryBuilder = ProjectApp.mDaoSession.getAnsweredQuestionBeanDao().queryBuilder();
            Property property = AnsweredQuestionBeanDao.Properties.Year;
            List<AnsweredQuestionBean> list = queryBuilder.where(property.eq(this.year), new WhereCondition[0]).list();
            jCount = ProjectApp.mDaoSession.getAnsweredQuestionBeanDao().queryBuilder().where(property.eq(this.year), new WhereCondition[0]).count();
            List<QuestionInfoBean> list2 = ProjectApp.mTiKuDaoSession.getQuestionInfoBeanDao().queryBuilder().where(QuestionInfoBeanDao.Properties.Year.eq(this.year), new WhereCondition[0]).list();
            for (int i3 = 0; i3 < list2.size(); i3++) {
                if (Integer.parseInt(this.year) > 2016) {
                    if (list2.get(i3).getNumber_number().longValue() <= 40) {
                        this.scoretotal = Double.valueOf(this.scoretotal.doubleValue() + 1.5d);
                    } else if (list2.get(i3).getNumber_number().longValue() <= 115) {
                        this.scoretotal = Double.valueOf(this.scoretotal.doubleValue() + 2.0d);
                    } else if (list2.get(i3).getNumber_number().longValue() <= 135) {
                        this.scoretotal = Double.valueOf(this.scoretotal.doubleValue() + 1.5d);
                    } else if (list2.get(i3).getNumber_number().longValue() <= 165) {
                        this.scoretotal = Double.valueOf(this.scoretotal.doubleValue() + 2.0d);
                    }
                } else if (Integer.parseInt(this.year) > 2007) {
                    if (list2.get(i3).getNumber_number().longValue() <= 90) {
                        this.scoretotal = Double.valueOf(this.scoretotal.doubleValue() + 1.5d);
                    } else if (list2.get(i3).getNumber_number().longValue() <= 120) {
                        this.scoretotal = Double.valueOf(this.scoretotal.doubleValue() + 2.0d);
                    } else if (list2.get(i3).getNumber_number().longValue() <= 150) {
                        this.scoretotal = Double.valueOf(this.scoretotal.doubleValue() + 1.5d);
                    } else if (list2.get(i3).getNumber_number().longValue() <= 180) {
                        this.scoretotal = Double.valueOf(this.scoretotal.doubleValue() + 2.0d);
                    }
                } else if (Integer.parseInt(this.year) != 2007) {
                    this.scoretotal = Double.valueOf(this.scoretotal.doubleValue() + 1.0d);
                } else if (list2.get(i3).getNumber_number().longValue() <= 106) {
                    this.scoretotal = Double.valueOf(this.scoretotal.doubleValue() + 1.6d);
                } else if (list2.get(i3).getNumber_number().longValue() <= 128) {
                    this.scoretotal = Double.valueOf(this.scoretotal.doubleValue() + 1.6d);
                } else if (list2.get(i3).getNumber_number().longValue() <= 150) {
                    this.scoretotal = Double.valueOf(this.scoretotal.doubleValue() + 1.6d);
                } else if (list2.get(i3).getNumber_number().longValue() <= 180) {
                    this.scoretotal = Double.valueOf(this.scoretotal.doubleValue() + 2.0d);
                }
            }
            for (int i4 = 0; i4 < list.size(); i4++) {
                if (Integer.parseInt(this.year) > 2016) {
                    if (list.get(i4).getYear_num().longValue() <= 40) {
                        if (list.get(i4).getIs_right().equals("1")) {
                            this.score = Double.valueOf(this.score.doubleValue() + 1.5d);
                        }
                    } else if (list.get(i4).getYear_num().longValue() <= 115) {
                        if (list.get(i4).getIs_right().equals("1")) {
                            this.score = Double.valueOf(this.score.doubleValue() + 2.0d);
                        }
                    } else if (list.get(i4).getYear_num().longValue() <= 135) {
                        if (list.get(i4).getIs_right().equals("1")) {
                            this.score = Double.valueOf(this.score.doubleValue() + 1.5d);
                        }
                    } else if (list.get(i4).getYear_num().longValue() <= 165 && list.get(i4).getIs_right().equals("1")) {
                        this.score = Double.valueOf(this.score.doubleValue() + 2.0d);
                    }
                } else if (Integer.parseInt(this.year) > 2007) {
                    if (list.get(i4).getYear_num().longValue() <= 90) {
                        if (list.get(i4).getIs_right().equals("1")) {
                            this.score = Double.valueOf(this.score.doubleValue() + 1.5d);
                        }
                    } else if (list.get(i4).getYear_num().longValue() <= 120) {
                        if (list.get(i4).getIs_right().equals("1")) {
                            this.score = Double.valueOf(this.score.doubleValue() + 2.0d);
                        }
                    } else if (list.get(i4).getYear_num().longValue() <= 150) {
                        if (list.get(i4).getIs_right().equals("1")) {
                            this.score = Double.valueOf(this.score.doubleValue() + 1.5d);
                        }
                    } else if (list.get(i4).getYear_num().longValue() <= 180 && list.get(i4).getIs_right().equals("1")) {
                        this.score = Double.valueOf(this.score.doubleValue() + 2.0d);
                    }
                } else if (Integer.parseInt(this.year) == 2007) {
                    if (list.get(i4).getYear_num().longValue() <= 106) {
                        if (list.get(i4).getIs_right().equals("1")) {
                            this.score = Double.valueOf(this.score.doubleValue() + 1.6d);
                        }
                    } else if (list.get(i4).getYear_num().longValue() <= 128) {
                        if (list.get(i4).getIs_right().equals("1")) {
                            this.score = Double.valueOf(this.score.doubleValue() + 1.6d);
                        }
                    } else if (list.get(i4).getYear_num().longValue() <= 150) {
                        if (list.get(i4).getIs_right().equals("1")) {
                            this.score = Double.valueOf(this.score.doubleValue() + 1.6d);
                        }
                    } else if (list.get(i4).getYear_num().longValue() <= 180 && list.get(i4).getIs_right().equals("1")) {
                        this.score = Double.valueOf(this.score.doubleValue() + 2.0d);
                    }
                } else if (list.get(i4).getIs_right().equals("1")) {
                    this.score = Double.valueOf(this.score.doubleValue() + 1.0d);
                }
            }
        } else {
            this.score = dValueOf;
            this.scoretotal = dValueOf;
            if (getIntent().getStringExtra("unit") == null) {
                QueryBuilder<AnsweredQuestionBean> queryBuilder2 = ProjectApp.mDaoSession.getAnsweredQuestionBeanDao().queryBuilder();
                Property property2 = AnsweredQuestionBeanDao.Properties.Year;
                jCount = queryBuilder2.where(property2.eq(this.year), new WhereCondition[0]).count();
                List<AnsweredQuestionBean> list3 = ProjectApp.mDaoSession.getAnsweredQuestionBeanDao().queryBuilder().where(property2.eq(this.year), new WhereCondition[0]).list();
                List<QuestionInfoBean> list4 = ProjectApp.mTiKuDaoSession.getQuestionInfoBeanDao().queryBuilder().where(QuestionInfoBeanDao.Properties.Year.eq(this.year), new WhereCondition[0]).list();
                for (int i5 = 0; i5 < list4.size(); i5++) {
                    long jLongValue = list4.get(i5).getNumber_number().longValue();
                    if (Integer.parseInt(this.year) > 2016) {
                        if (jLongValue <= 36) {
                            this.scoretotal = Double.valueOf(this.scoretotal.doubleValue() + 1.5d);
                        } else if (jLongValue <= 81) {
                            this.scoretotal = Double.valueOf(this.scoretotal.doubleValue() + 2.0d);
                        } else if (jLongValue <= 105) {
                            this.scoretotal = Double.valueOf(this.scoretotal.doubleValue() + 1.5d);
                        } else {
                            this.scoretotal = Double.valueOf(this.scoretotal.doubleValue() + 2.0d);
                        }
                    } else if (Integer.parseInt(this.year) > 2007) {
                        if (jLongValue <= 80) {
                            this.scoretotal = Double.valueOf(this.scoretotal.doubleValue() + 1.5d);
                        } else if (jLongValue <= 120) {
                            this.scoretotal = Double.valueOf(this.scoretotal.doubleValue() + 1.5d);
                        } else {
                            this.scoretotal = Double.valueOf(this.scoretotal.doubleValue() + 2.0d);
                        }
                    }
                }
                for (int i6 = 0; i6 < list3.size(); i6++) {
                    long jLongValue2 = list3.get(i6).getYear_num().longValue();
                    if (Integer.parseInt(this.year) <= 2016) {
                        if (Integer.parseInt(this.year) > 2007) {
                            if (jLongValue2 <= 80) {
                                if (list3.get(i6).getIs_right().equals("1")) {
                                    this.score = Double.valueOf(this.score.doubleValue() + 1.5d);
                                }
                            } else if (jLongValue2 <= 120) {
                                if (list3.get(i6).getIs_right().equals("1")) {
                                    this.score = Double.valueOf(this.score.doubleValue() + 1.5d);
                                }
                            } else if (list3.get(i6).getIs_right().equals("1")) {
                                this.score = Double.valueOf(this.score.doubleValue() + 2.0d);
                            }
                        }
                    } else if (jLongValue2 <= 36) {
                        if (list3.get(i6).getIs_right().equals("1")) {
                            this.score = Double.valueOf(this.score.doubleValue() + 1.5d);
                        }
                    } else if (jLongValue2 <= 81) {
                        if (list3.get(i6).getIs_right().equals("1")) {
                            this.score = Double.valueOf(this.score.doubleValue() + 2.0d);
                        }
                    } else if (jLongValue2 <= 105) {
                        if (list3.get(i6).getIs_right().equals("1")) {
                            this.score = Double.valueOf(this.score.doubleValue() + 1.5d);
                        }
                    } else if (list3.get(i6).getIs_right().equals("1")) {
                        this.score = Double.valueOf(this.score.doubleValue() + 2.0d);
                    }
                }
            } else {
                this.score = dValueOf;
                this.scoretotal = dValueOf;
                QueryBuilder<AnsweredQuestionBean> queryBuilder3 = ProjectApp.mDaoSession.getAnsweredQuestionBeanDao().queryBuilder();
                Property property3 = AnsweredQuestionBeanDao.Properties.Year;
                long jCount3 = queryBuilder3.where(property3.eq(this.year), AnsweredQuestionBeanDao.Properties.Unit.eq(getIntent().getExtras().getString("unit"))).count();
                List<AnsweredQuestionBean> list5 = ProjectApp.mDaoSession.getAnsweredQuestionBeanDao().queryBuilder().where(property3.eq(this.year), new WhereCondition[0]).list();
                List<QuestionInfoBean> list6 = ProjectApp.mTiKuDaoSession.getQuestionInfoBeanDao().queryBuilder().where(QuestionInfoBeanDao.Properties.Year.eq(this.year), new WhereCondition[0]).list();
                for (int i7 = 0; i7 < list6.size(); i7++) {
                    this.scoretotal = Double.valueOf(this.scoretotal.doubleValue() + 1.0d);
                }
                for (int i8 = 0; i8 < list5.size(); i8++) {
                    if (list5.get(i8).getIs_right().equals("1")) {
                        this.score = Double.valueOf(this.score.doubleValue() + 1.0d);
                    }
                }
                jCount = jCount3;
            }
        }
        if (getIntent().getExtras().getString("unit") == null) {
            jCount2 = ProjectApp.mTiKuDaoSession.getQuestionInfoBeanDao().queryBuilder().where(QuestionInfoBeanDao.Properties.Year.eq(getIntent().getStringExtra("year")), new WhereCondition[0]).count();
            i2 = 0;
        } else {
            i2 = 0;
            jCount2 = ProjectApp.mTiKuDaoSession.getQuestionInfoBeanDao().queryBuilder().where(QuestionInfoBeanDao.Properties.Year.eq(getIntent().getStringExtra("year")), QuestionInfoBeanDao.Properties.Unit.eq(getIntent().getExtras().getString("unit"))).count();
        }
        if (SharePreferencesUtils.readIntConfig(CommonParameter.ISCESHITIKU, this, i2) == 0) {
            long j2 = jCount2 - jCount;
            if (j2 == 0) {
                customDialog.setMessage("确定要统计吗？");
            } else {
                customDialog.setMessage("您还有" + j2 + "题没做，确定要统计吗？");
            }
        } else {
            long j3 = jCount2 - jCount;
            if (j3 == 0) {
                customDialog.setMessage("确定要交卷吗？");
            } else {
                customDialog.setMessage("您还有" + j3 + "题没做，确定要交卷吗？");
            }
        }
        customDialog.setNegativeBtn(R.string.cancel, new View.OnClickListener() { // from class: com.psychiatrygarden.activity.answer.c
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AnswerDetailActivity.lambda$handInDialog$5(customDialog, view);
            }
        });
        customDialog.setPositiveBtn(R.string.ok, new View.OnClickListener() { // from class: com.psychiatrygarden.activity.answer.d
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11018c.lambda$handInDialog$6(customDialog, view);
            }
        });
        customDialog.show();
    }

    public void handInSubDialog() {
        final CustomDialog customDialog = new CustomDialog(this.mContext, 2);
        customDialog.setCancelable(false);
        customDialog.isOutTouchDismiss(false);
        ArrayList arrayList = new ArrayList();
        for (long j2 : this.list_questionid) {
            AnsweredQuestionBean answeredQuestionBeanLoadByRowId = ProjectApp.mDaoSession.getAnsweredQuestionBeanDao().loadByRowId(j2);
            if (answeredQuestionBeanLoadByRowId != null) {
                arrayList.add(answeredQuestionBeanLoadByRowId);
            }
        }
        if (SharePreferencesUtils.readIntConfig(CommonParameter.ISCESHITIKU, this, 0) == 0 || getIntent().getBooleanExtra("ISPractice", false)) {
            if (this.list_questionid.length == arrayList.size()) {
                customDialog.setMessage("确定要统计吗");
            } else {
                customDialog.setMessage("您还有" + (this.list_questionid.length - arrayList.size()) + "题没做，确定要统计吗？");
            }
        } else if (this.list_questionid.length == arrayList.size()) {
            customDialog.setMessage("确定要交卷吗");
        } else {
            customDialog.setMessage("您还有" + (this.list_questionid.length - arrayList.size()) + "题没做，确定要交卷吗？");
        }
        customDialog.setNegativeBtn(R.string.cancel, new View.OnClickListener() { // from class: com.psychiatrygarden.activity.answer.g
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AnswerDetailActivity.lambda$handInSubDialog$3(customDialog, view);
            }
        });
        customDialog.setPositiveBtn(R.string.ok, new View.OnClickListener() { // from class: com.psychiatrygarden.activity.answer.h
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11029c.lambda$handInSubDialog$4(customDialog, view);
            }
        });
        customDialog.show();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() throws Resources.NotFoundException {
        this.position = getIntent().getIntExtra("position", 0);
        this.list_questionid = getIntent().getLongArrayExtra("list");
        this.modletype = getIntent().getExtras().getString("modletype");
        this.questiondetails_tv_title_gufen = (TextView) findViewById(R.id.questiondetails_tv_title_gufen);
        this.mBtnLeft = (ImageView) findViewById(R.id.include_btn_left);
        this.include_title_center = (TextView) findViewById(R.id.include_title_center);
        this.include_btn_right_tv = (TextView) findViewById(R.id.include_btn_right_tv);
        this.questiondetails_tv_title = (TextView) findViewById(R.id.questiondetails_tv_title);
        ViewPagerCompat viewPagerCompat = (ViewPagerCompat) findViewById(R.id.questiondetails_viewPager);
        this.viewPager = viewPagerCompat;
        viewPagerCompat.setSaveEnabled(false);
        this.viewPager.setOffscreenPageLimit(1);
        this.viewPager.setOnListener(new onDialogShareClickListener() { // from class: com.psychiatrygarden.activity.answer.j
            @Override // com.psychiatrygarden.interfaceclass.onDialogShareClickListener
            public final void onclickIntBack(int i2) {
                this.f11034a.lambda$init$0(i2);
            }
        });
        this.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() { // from class: com.psychiatrygarden.activity.answer.AnswerDetailActivity.1
            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrollStateChanged(int arg0) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageSelected(int arg0) {
                AnswerDetailActivity.this.position = arg0;
                if ("year".equals(AnswerDetailActivity.this.modletype)) {
                    AnswerDetailActivity.this.setQuestionExplan();
                }
                ProjectApp.analysisContent = "";
                ProjectApp.analysisImageStr = new ArrayList();
                ProjectApp.analysisIsHidde = false;
            }
        });
        setDataAdapter();
        initTitle();
    }

    public void initTitle() {
        if (!"year".equals(this.modletype)) {
            this.include_title_center.setText(getIntent().getStringExtra("subject_name"));
            this.questiondetails_tv_title.setVisibility(0);
            this.questiondetails_tv_title_gufen.setVisibility(8);
            this.questiondetails_tv_title.setText(getIntent().getStringExtra("chapter_name"));
            if (getIntent().getBooleanExtra("ISPractice", false)) {
                this.include_btn_right_tv.setVisibility(8);
                this.include_btn_right_tv.setText("统计");
            } else if (SharePreferencesUtils.readIntConfig(CommonParameter.ISCESHITIKU, this, 0) == 0) {
                this.include_btn_right_tv.setVisibility(0);
                this.include_btn_right_tv.setText("统计");
            } else if (SharePreferencesUtils.readIntConfig(CommonParameter.ISCESHITIKU, this, 0) == 2) {
                this.include_btn_right_tv.setVisibility(8);
            } else {
                this.include_btn_right_tv.setVisibility(0);
                this.include_btn_right_tv.setText("交卷");
            }
            this.include_btn_right_tv.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.answer.b
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f11011c.lambda$initTitle$2(view);
                }
            });
            return;
        }
        this.year = getIntent().getStringExtra("year");
        if (getIntent().getBooleanExtra("ISPractice", false) || SharePreferencesUtils.readIntConfig(CommonParameter.ISCESHITIKU, this, 0) == 2) {
            this.questiondetails_tv_title_gufen.setVisibility(8);
            this.include_btn_right_tv.setVisibility(8);
        } else {
            this.questiondetails_tv_title_gufen.setVisibility(0);
            setQuestionExplan();
            if (SharePreferencesUtils.readIntConfig(CommonParameter.ISCESHITIKU, this, 0) == 0) {
                this.include_btn_right_tv.setVisibility(0);
                this.include_btn_right_tv.setText("统计");
            } else {
                this.include_btn_right_tv.setVisibility(0);
                this.include_btn_right_tv.setText("交卷");
            }
            this.include_btn_right_tv.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.answer.a
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f11001c.lambda$initTitle$1(view);
                }
            });
        }
        if (getIntent().getStringExtra("unit") != null && !getIntent().getStringExtra("unit").equals("")) {
            this.questiondetails_tv_title_gufen.setVisibility(8);
            this.questiondetails_tv_title.setVisibility(0);
            if (getIntent().getExtras().getString("unit").equals("U1")) {
                this.questiondetails_tv_title.setText("第一单元");
            } else if (getIntent().getExtras().getString("unit").equals("U2")) {
                this.questiondetails_tv_title.setText("第二单元");
            } else if (getIntent().getExtras().getString("unit").equals("U3")) {
                this.questiondetails_tv_title.setText("第三单元");
            } else {
                this.questiondetails_tv_title.setText("第四单元");
            }
        }
        if (!SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, this.mContext).equals(Constants.VIA_REPORT_TYPE_SHARE_TO_QQ)) {
            this.include_title_center.setText(getIntent().getExtras().getString("year") + "年" + SharePreferencesUtils.readStrConfig(CommonParameter.app_title, this.mContext) + "真题");
            return;
        }
        if (getIntent().getExtras().getString("year").equals("2017") || getIntent().getExtras().getString("year").equals("2018") || getIntent().getExtras().getString("year").equals("2019")) {
            this.include_title_center.setText(getIntent().getStringExtra("year") + "年临床医学综合能力真题");
            return;
        }
        this.include_title_center.setText(getIntent().getExtras().getString("year") + "年西医综合真题");
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActionBar.hide();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        ProjectApp.analysisContent = "";
        ProjectApp.analysisImageStr = new ArrayList();
        ProjectApp.analysisIsHidde = false;
        EventBus.getDefault().post("SubjectActivityThread");
        Disposable disposable = this.mDisposable;
        if (disposable == null || disposable.isDisposed()) {
            return;
        }
        this.mDisposable.dispose();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) throws Resources.NotFoundException {
        if (str.equals("mIndex")) {
            int i2 = this.position;
            if (i2 == this.list_questionid.length - 1) {
                AlertToast("已是最后一题，请交卷");
            } else {
                this.viewPager.setCurrentItem(i2 + 1);
            }
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_answer_detail);
    }

    public void setDataAdapter() {
        this.mDisposable = Observable.create(new ObservableOnSubscribe() { // from class: com.psychiatrygarden.activity.answer.e
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) throws Exception {
                this.f11023a.lambda$setDataAdapter$7(observableEmitter);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.psychiatrygarden.activity.answer.f
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f11025c.lambda$setDataAdapter$8((List) obj);
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.mBtnLeft.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.answer.i
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11032c.lambda$setListenerForWidget$9(view);
            }
        });
    }
}
