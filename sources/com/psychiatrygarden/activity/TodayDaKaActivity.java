package com.psychiatrygarden.activity;

import android.content.Intent;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.material.timepicker.TimeModel;
import com.mobile.auth.gatewayauth.ResultCode;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.bean.AnsweredQuestionBean;
import com.psychiatrygarden.bean.AnsweredQuestionBeanDao;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.NewToast;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.yikaobang.yixue.R;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.query.QueryBuilder;
import de.greenrobot.dao.query.WhereCondition;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class TodayDaKaActivity extends BaseActivity {
    Button bt_duihuan;
    LinearLayout llay_fuxi_zangjie;
    TextView tv_all_lianxu_day;
    TextView tv_beat_examinee;
    TextView tv_dati_right;
    TextView tv_dati_zangjie_more;
    TextView tv_lianxu_day;
    TextView tv_my_jifen;
    TextView tv_today_date;
    JSONObject json_info = new JSONObject();
    View.OnClickListener mOnclick = new View.OnClickListener() { // from class: com.psychiatrygarden.activity.TodayDaKaActivity.3
        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Removed duplicated region for block: B:29:0x018c  */
        /* JADX WARN: Type inference failed for: r0v6, types: [com.umeng.socialize.ShareAction] */
        /* JADX WARN: Type inference failed for: r14v11, types: [com.umeng.socialize.ShareAction] */
        /* JADX WARN: Type inference failed for: r14v15, types: [java.lang.String] */
        /* JADX WARN: Type inference failed for: r14v2 */
        /* JADX WARN: Type inference failed for: r14v3 */
        /* JADX WARN: Type inference failed for: r14v4 */
        /* JADX WARN: Type inference failed for: r2v0, types: [java.lang.String] */
        /* JADX WARN: Type inference failed for: r2v1 */
        /* JADX WARN: Type inference failed for: r2v2 */
        /* JADX WARN: Type inference failed for: r2v5, types: [com.umeng.socialize.media.BaseMediaObject, com.umeng.socialize.media.UMWeb] */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:23:0x0163 -> B:26:0x0168). Please report as a decompilation issue!!! */
        @Override // android.view.View.OnClickListener
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void onClick(android.view.View r14) throws java.io.UnsupportedEncodingException {
            /*
                Method dump skipped, instructions count: 457
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.psychiatrygarden.activity.TodayDaKaActivity.AnonymousClass3.onClick(android.view.View):void");
        }
    };
    private final UMShareListener shareListener = new UMShareListener() { // from class: com.psychiatrygarden.activity.TodayDaKaActivity.4
        @Override // com.umeng.socialize.UMShareListener
        public void onCancel(SHARE_MEDIA platform) {
            NewToast.showShort(TodayDaKaActivity.this, "取消了", 1).show();
        }

        @Override // com.umeng.socialize.UMShareListener
        public void onError(SHARE_MEDIA platform, Throwable t2) {
            NewToast.showShort(TodayDaKaActivity.this, ResultCode.MSG_FAILED + t2.getMessage(), 1).show();
        }

        @Override // com.umeng.socialize.UMShareListener
        public void onResult(SHARE_MEDIA platform) {
            TodayDaKaActivity.this.hideInputMethod();
            TodayDaKaActivity.this.getSignDaka();
            NewToast.showShort(TodayDaKaActivity.this, "成功了", 1).show();
        }

        @Override // com.umeng.socialize.UMShareListener
        public void onStart(SHARE_MEDIA share_media) {
        }
    };

    /* JADX INFO: Access modifiers changed from: private */
    public void getSignDaka() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("user_id", UserConfig.getUserId());
        YJYHttpUtils.getmd5(this.mContext, "", ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.TodayDaKaActivity.2
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                TodayDaKaActivity.this.AlertToast("请求失败");
                TodayDaKaActivity.this.hideProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                TodayDaKaActivity.this.showProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String t2) {
                super.onSuccess((AnonymousClass2) t2);
                try {
                    JSONObject jSONObject = new JSONObject(t2);
                    TodayDaKaActivity.this.AlertToast(jSONObject.optString("message"));
                    if (jSONObject.optString("code").equals("1")) {
                        TodayDaKaActivity.this.finish();
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                TodayDaKaActivity.this.hideProgressDialog();
            }
        });
    }

    private void getSignInfoDaka(String right_num, String total_num) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("user_id", UserConfig.getUserId());
        ajaxParams.put("right_num", right_num);
        ajaxParams.put("total_num", total_num);
        YJYHttpUtils.getmd5(this.mContext, "", ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.TodayDaKaActivity.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                TodayDaKaActivity.this.AlertToast("请求失败");
                TodayDaKaActivity.this.hideProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                TodayDaKaActivity.this.showProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String t2) throws JSONException {
                super.onSuccess((AnonymousClass1) t2);
                try {
                    JSONObject jSONObject = new JSONObject(t2);
                    if (jSONObject.optString("code").equals("1")) {
                        JSONObject jSONObject2 = new JSONObject(jSONObject.optString("data"));
                        if (jSONObject2.optString("beat").equals("")) {
                            TodayDaKaActivity.this.tv_beat_examinee.setText(Html.fromHtml("击败 <big><big><font color='#B2575C'>0%</font></big></big> 考生"));
                        } else {
                            TodayDaKaActivity.this.tv_beat_examinee.setText(Html.fromHtml("击败 <big><big><font color='#B2575C'>" + jSONObject2.optString("beat") + "</font></big></big> 考生"));
                        }
                        TodayDaKaActivity.this.tv_today_date.setText(String.format("%s\t\t%s", jSONObject2.optString("date"), jSONObject2.optString("week")));
                        TodayDaKaActivity.this.tv_lianxu_day.setText(jSONObject2.optString("sign_count"));
                        TodayDaKaActivity.this.tv_all_lianxu_day.setText(String.format("已经连续打卡%s天", jSONObject2.optString("all_sign_count")));
                        TodayDaKaActivity.this.json_info.put("sign_count", jSONObject2.optString("sign_count"));
                        TodayDaKaActivity.this.json_info.put("all_sign_count", jSONObject2.optString("all_sign_count"));
                        TodayDaKaActivity.this.json_info.put("beat", jSONObject2.optString("beat"));
                        TodayDaKaActivity.this.json_info.put("week", jSONObject2.optString("date") + "\t\t" + jSONObject2.optString("week"));
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                TodayDaKaActivity.this.hideProgressDialog();
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() throws JSONException {
        int i2;
        double size;
        double d3;
        int i3;
        List<AnsweredQuestionBean> list;
        double d4;
        double d5;
        int i4;
        this.tv_today_date = (TextView) findViewById(R.id.tv_today_date);
        this.tv_all_lianxu_day = (TextView) findViewById(R.id.tv_all_lianxu_day);
        this.tv_lianxu_day = (TextView) findViewById(R.id.tv_lianxu_day);
        TextView textView = (TextView) findViewById(R.id.tv_my_jifen);
        this.tv_my_jifen = textView;
        textView.getPaint().setFlags(8);
        this.tv_my_jifen.getPaint().setAntiAlias(true);
        this.llay_fuxi_zangjie = (LinearLayout) findViewById(R.id.llay_fuxi_zangjie);
        this.tv_dati_zangjie_more = (TextView) findViewById(R.id.tv_dati_zangjie_more);
        this.tv_dati_right = (TextView) findViewById(R.id.tv_dati_right);
        this.tv_beat_examinee = (TextView) findViewById(R.id.tv_beat_examinee);
        this.bt_duihuan = (Button) findViewById(R.id.bt_duihuan);
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        int i5 = calendar.get(1);
        int i6 = calendar.get(2) + 1;
        int i7 = calendar.get(5);
        QueryBuilder<AnsweredQuestionBean> queryBuilder = ProjectApp.mDaoSession.getAnsweredQuestionBeanDao().queryBuilder();
        Property property = AnsweredQuestionBeanDao.Properties.Answer_year;
        WhereCondition whereConditionEq = property.eq(Integer.valueOf(i5));
        Property property2 = AnsweredQuestionBeanDao.Properties.Answer_month;
        Property property3 = AnsweredQuestionBeanDao.Properties.Answer_day;
        List<AnsweredQuestionBean> list2 = queryBuilder.where(whereConditionEq, property2.eq(Integer.valueOf(i6)), property3.eq(Integer.valueOf(i7))).list();
        double dCount = ProjectApp.mDaoSession.getAnsweredQuestionBeanDao().queryBuilder().where(property.eq(Integer.valueOf(i5)), property2.eq(Integer.valueOf(i6)), property3.eq(Integer.valueOf(i7)), AnsweredQuestionBeanDao.Properties.Is_right.eq("1")).count();
        if (list2.size() > 0) {
            i2 = i6;
            size = (100.0d * dCount) / list2.size();
        } else {
            i2 = i6;
            size = 0.0d;
        }
        this.tv_dati_right.setText(Html.fromHtml(((int) dCount) + "/" + list2.size() + "<big><big><font color='#B2575C'> " + CommonUtil.getNumber(size) + "%</font></big></big>"));
        LinkedList linkedList = new LinkedList();
        for (int i8 = 0; i8 < list2.size(); i8++) {
            if (!linkedList.contains(list2.get(i8).getChapter_id())) {
                linkedList.add(list2.get(i8).getChapter_id());
            }
        }
        int size2 = linkedList.size();
        int i9 = 10;
        if (size2 > 10) {
            this.tv_dati_zangjie_more.setVisibility(0);
        } else {
            i9 = size2;
        }
        JSONArray jSONArray = new JSONArray();
        int i10 = 0;
        while (i10 < i9) {
            JSONObject jSONObject = new JSONObject();
            try {
                i3 = i9;
                try {
                    d5 = size;
                    try {
                        jSONObject.put("name", ProjectApp.mTiKuDaoSession.getSectionBeanDao().load(Long.valueOf(Long.parseLong((String) linkedList.get(i10)))).getTitle());
                        StringBuilder sb = new StringBuilder();
                        list = list2;
                        try {
                            d4 = dCount;
                            try {
                                sb.append(ProjectApp.mDaoSession.getAnsweredQuestionBeanDao().queryBuilder().where(AnsweredQuestionBeanDao.Properties.Answer_year.eq(Integer.valueOf(i5)), AnsweredQuestionBeanDao.Properties.Answer_month.eq(Integer.valueOf(i2)), AnsweredQuestionBeanDao.Properties.Answer_day.eq(Integer.valueOf(i7)), AnsweredQuestionBeanDao.Properties.Chapter_id.eq(linkedList.get(i10))).count());
                                sb.append("");
                                jSONObject.put("count", sb.toString());
                                jSONArray.put(jSONObject);
                            } catch (Exception e2) {
                                e = e2;
                                e.printStackTrace();
                                try {
                                    View viewInflate = getLayoutInflater().inflate(R.layout.item_jinrifuxi_view, (ViewGroup) this.llay_fuxi_zangjie, false);
                                    TextView textView2 = (TextView) viewInflate.findViewById(R.id.tv_dati_zangjie);
                                    TextView textView3 = (TextView) viewInflate.findViewById(R.id.tv_dati_num);
                                    textView2.setText(ProjectApp.mTiKuDaoSession.getSectionBeanDao().load(Long.valueOf(Long.parseLong((String) linkedList.get(i10)))).getTitle());
                                    Locale locale = Locale.CHINA;
                                    Object[] objArr = new Object[1];
                                    QueryBuilder<AnsweredQuestionBean> queryBuilder2 = ProjectApp.mDaoSession.getAnsweredQuestionBeanDao().queryBuilder();
                                    WhereCondition whereConditionEq2 = AnsweredQuestionBeanDao.Properties.Answer_year.eq(Integer.valueOf(i5));
                                    WhereCondition[] whereConditionArr = new WhereCondition[3];
                                    i4 = i5;
                                    try {
                                        whereConditionArr[0] = AnsweredQuestionBeanDao.Properties.Answer_month.eq(Integer.valueOf(i2));
                                        whereConditionArr[1] = AnsweredQuestionBeanDao.Properties.Answer_day.eq(Integer.valueOf(i7));
                                        try {
                                            whereConditionArr[2] = AnsweredQuestionBeanDao.Properties.Chapter_id.eq(linkedList.get(i10));
                                            try {
                                                objArr[0] = Long.valueOf(queryBuilder2.where(whereConditionEq2, whereConditionArr).count());
                                                textView3.setText(String.format(locale, TimeModel.NUMBER_FORMAT, objArr));
                                                this.llay_fuxi_zangjie.addView(viewInflate);
                                            } catch (Exception unused) {
                                                AlertToast("打卡异常");
                                                i10++;
                                                list2 = list;
                                                i9 = i3;
                                                dCount = d4;
                                                size = d5;
                                                i5 = i4;
                                            }
                                        } catch (Exception unused2) {
                                        }
                                    } catch (Exception unused3) {
                                        AlertToast("打卡异常");
                                        i10++;
                                        list2 = list;
                                        i9 = i3;
                                        dCount = d4;
                                        size = d5;
                                        i5 = i4;
                                    }
                                } catch (Exception unused4) {
                                    i4 = i5;
                                }
                                i10++;
                                list2 = list;
                                i9 = i3;
                                dCount = d4;
                                size = d5;
                                i5 = i4;
                            }
                        } catch (Exception e3) {
                            e = e3;
                            d4 = dCount;
                            e.printStackTrace();
                            View viewInflate2 = getLayoutInflater().inflate(R.layout.item_jinrifuxi_view, (ViewGroup) this.llay_fuxi_zangjie, false);
                            TextView textView22 = (TextView) viewInflate2.findViewById(R.id.tv_dati_zangjie);
                            TextView textView32 = (TextView) viewInflate2.findViewById(R.id.tv_dati_num);
                            textView22.setText(ProjectApp.mTiKuDaoSession.getSectionBeanDao().load(Long.valueOf(Long.parseLong((String) linkedList.get(i10)))).getTitle());
                            Locale locale2 = Locale.CHINA;
                            Object[] objArr2 = new Object[1];
                            QueryBuilder<AnsweredQuestionBean> queryBuilder22 = ProjectApp.mDaoSession.getAnsweredQuestionBeanDao().queryBuilder();
                            WhereCondition whereConditionEq22 = AnsweredQuestionBeanDao.Properties.Answer_year.eq(Integer.valueOf(i5));
                            WhereCondition[] whereConditionArr2 = new WhereCondition[3];
                            i4 = i5;
                            whereConditionArr2[0] = AnsweredQuestionBeanDao.Properties.Answer_month.eq(Integer.valueOf(i2));
                            whereConditionArr2[1] = AnsweredQuestionBeanDao.Properties.Answer_day.eq(Integer.valueOf(i7));
                            whereConditionArr2[2] = AnsweredQuestionBeanDao.Properties.Chapter_id.eq(linkedList.get(i10));
                            objArr2[0] = Long.valueOf(queryBuilder22.where(whereConditionEq22, whereConditionArr2).count());
                            textView32.setText(String.format(locale2, TimeModel.NUMBER_FORMAT, objArr2));
                            this.llay_fuxi_zangjie.addView(viewInflate2);
                            i10++;
                            list2 = list;
                            i9 = i3;
                            dCount = d4;
                            size = d5;
                            i5 = i4;
                        }
                    } catch (Exception e4) {
                        e = e4;
                        list = list2;
                    }
                } catch (Exception e5) {
                    e = e5;
                    list = list2;
                    d4 = dCount;
                    d5 = size;
                    e.printStackTrace();
                    View viewInflate22 = getLayoutInflater().inflate(R.layout.item_jinrifuxi_view, (ViewGroup) this.llay_fuxi_zangjie, false);
                    TextView textView222 = (TextView) viewInflate22.findViewById(R.id.tv_dati_zangjie);
                    TextView textView322 = (TextView) viewInflate22.findViewById(R.id.tv_dati_num);
                    textView222.setText(ProjectApp.mTiKuDaoSession.getSectionBeanDao().load(Long.valueOf(Long.parseLong((String) linkedList.get(i10)))).getTitle());
                    Locale locale22 = Locale.CHINA;
                    Object[] objArr22 = new Object[1];
                    QueryBuilder<AnsweredQuestionBean> queryBuilder222 = ProjectApp.mDaoSession.getAnsweredQuestionBeanDao().queryBuilder();
                    WhereCondition whereConditionEq222 = AnsweredQuestionBeanDao.Properties.Answer_year.eq(Integer.valueOf(i5));
                    WhereCondition[] whereConditionArr22 = new WhereCondition[3];
                    i4 = i5;
                    whereConditionArr22[0] = AnsweredQuestionBeanDao.Properties.Answer_month.eq(Integer.valueOf(i2));
                    whereConditionArr22[1] = AnsweredQuestionBeanDao.Properties.Answer_day.eq(Integer.valueOf(i7));
                    whereConditionArr22[2] = AnsweredQuestionBeanDao.Properties.Chapter_id.eq(linkedList.get(i10));
                    objArr22[0] = Long.valueOf(queryBuilder222.where(whereConditionEq222, whereConditionArr22).count());
                    textView322.setText(String.format(locale22, TimeModel.NUMBER_FORMAT, objArr22));
                    this.llay_fuxi_zangjie.addView(viewInflate22);
                    i10++;
                    list2 = list;
                    i9 = i3;
                    dCount = d4;
                    size = d5;
                    i5 = i4;
                }
            } catch (Exception e6) {
                e = e6;
                i3 = i9;
            }
            try {
                View viewInflate222 = getLayoutInflater().inflate(R.layout.item_jinrifuxi_view, (ViewGroup) this.llay_fuxi_zangjie, false);
                TextView textView2222 = (TextView) viewInflate222.findViewById(R.id.tv_dati_zangjie);
                TextView textView3222 = (TextView) viewInflate222.findViewById(R.id.tv_dati_num);
                textView2222.setText(ProjectApp.mTiKuDaoSession.getSectionBeanDao().load(Long.valueOf(Long.parseLong((String) linkedList.get(i10)))).getTitle());
                Locale locale222 = Locale.CHINA;
                Object[] objArr222 = new Object[1];
                QueryBuilder<AnsweredQuestionBean> queryBuilder2222 = ProjectApp.mDaoSession.getAnsweredQuestionBeanDao().queryBuilder();
                WhereCondition whereConditionEq2222 = AnsweredQuestionBeanDao.Properties.Answer_year.eq(Integer.valueOf(i5));
                WhereCondition[] whereConditionArr222 = new WhereCondition[3];
                i4 = i5;
            } catch (Exception unused5) {
                i4 = i5;
            }
            try {
                whereConditionArr222[0] = AnsweredQuestionBeanDao.Properties.Answer_month.eq(Integer.valueOf(i2));
                whereConditionArr222[1] = AnsweredQuestionBeanDao.Properties.Answer_day.eq(Integer.valueOf(i7));
                whereConditionArr222[2] = AnsweredQuestionBeanDao.Properties.Chapter_id.eq(linkedList.get(i10));
                objArr222[0] = Long.valueOf(queryBuilder2222.where(whereConditionEq2222, whereConditionArr222).count());
                textView3222.setText(String.format(locale222, TimeModel.NUMBER_FORMAT, objArr222));
                this.llay_fuxi_zangjie.addView(viewInflate222);
            } catch (Exception unused6) {
                AlertToast("打卡异常");
                i10++;
                list2 = list;
                i9 = i3;
                dCount = d4;
                size = d5;
                i5 = i4;
            }
            i10++;
            list2 = list;
            i9 = i3;
            dCount = d4;
            size = d5;
            i5 = i4;
        }
        List<AnsweredQuestionBean> list3 = list2;
        double d6 = dCount;
        double d7 = size;
        try {
            this.json_info.put("chapter", jSONArray);
            d3 = d6;
            try {
                this.json_info.put("right", d3);
                this.json_info.put("total", list3.size() + "");
                this.json_info.put("Accuracy", CommonUtil.getNumber(d7) + "%");
            } catch (Exception e7) {
                e = e7;
                e.printStackTrace();
                getSignInfoDaka(d3 + "", list3.size() + "");
            }
        } catch (Exception e8) {
            e = e8;
            d3 = d6;
        }
        getSignInfoDaka(d3 + "", list3.size() + "");
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this.mContext).onActivityResult(requestCode, resultCode, data);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setTitle("今日打卡");
        setContentView(R.layout.activity_today_daka);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.tv_my_jifen.setOnClickListener(this.mOnclick);
        this.bt_duihuan.setOnClickListener(this.mOnclick);
    }
}
