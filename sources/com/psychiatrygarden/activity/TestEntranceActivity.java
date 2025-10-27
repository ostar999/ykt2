package com.psychiatrygarden.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.hutool.core.text.StrPool;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.online.bean.YearStatisticsBean;
import com.psychiatrygarden.activity.vip.Utils.MemInterface;
import com.psychiatrygarden.adapter.TestEntranceAdapter;
import com.psychiatrygarden.bean.EsexBean;
import com.psychiatrygarden.bean.ExesQuestionBean;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.QuestionDataRequest;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.interfaceclass.QuestionDataCallBack;
import com.psychiatrygarden.interfaceclass.onDialogShareClickListener;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.widget.DialogShare;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.yikaobang.yixue.R;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class TestEntranceActivity extends BaseActivity implements QuestionDataCallBack<String> {
    private ExpandableListView elv_tiku_question;
    private boolean isJumpToDetails;
    private LinearLayout mLyZongfen;
    private String score;
    private TextView shujujiazaizhong;
    private TextView totalfen;
    private String user_exam_time;
    private YearStatisticsBean yearStatisticsBean;
    private TextView zongfe;
    private TextView zongfenshu;
    private List<ExesQuestionBean> questBeans = new ArrayList();
    private String bindSchool = "";
    private final UMShareListener umShareListener = new UMShareListener() { // from class: com.psychiatrygarden.activity.TestEntranceActivity.2
        @Override // com.umeng.socialize.UMShareListener
        public void onCancel(SHARE_MEDIA arg0) {
        }

        @Override // com.umeng.socialize.UMShareListener
        public void onError(SHARE_MEDIA arg0, Throwable arg1) {
            TestEntranceActivity.this.AlertToast("分享失败");
        }

        @Override // com.umeng.socialize.UMShareListener
        public void onResult(SHARE_MEDIA arg0) {
            TestEntranceActivity.this.AlertToast("分享成功");
        }

        @Override // com.umeng.socialize.UMShareListener
        public void onStart(SHARE_MEDIA share_media) {
        }
    };

    private void getExamInfoData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("exam_id", getIntent().getExtras().getString("exam_id"));
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.minfoUrl, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.TestEntranceActivity.3
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass3) s2);
                try {
                    EsexBean esexBean = (EsexBean) new Gson().fromJson(s2, EsexBean.class);
                    if (esexBean.getCode().equals("200")) {
                        SharePreferencesUtils.writeStrConfig("statisticsPermission", esexBean.getData().getShare_activity_rights(), TestEntranceActivity.this);
                        if (esexBean.getData().getShare_activity_rights().equals("1")) {
                            TestEntranceActivity.this.initData();
                            TestEntranceActivity.this.mLyZongfen.setVisibility(0);
                            TestEntranceActivity.this.totalfen.setText(String.format("%s分", TestEntranceActivity.this.score));
                            TestEntranceActivity.this.zongfenshu.setText(String.format("用时%s", TestEntranceActivity.this.user_exam_time));
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initData() throws JSONException {
        this.questBeans = (List) new Gson().fromJson(new Gson().toJson(ProjectApp.questExamList), new TypeToken<List<ExesQuestionBean>>() { // from class: com.psychiatrygarden.activity.TestEntranceActivity.1
        }.getType());
        JSONArray jSONArray = new JSONArray();
        for (int i2 = 0; i2 < this.questBeans.size(); i2++) {
            try {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("question_id", this.questBeans.get(i2).getQuestion_id());
                jSONObject.put("score", this.questBeans.get(i2).getScore());
                jSONObject.put("is_right", this.questBeans.get(i2).getIsRight());
                jSONObject.put("chapter_id", this.questBeans.get(i2).getChapter_id());
                jSONArray.put(jSONObject);
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("am_pm", SharePreferencesUtils.readStrConfig(CommonParameter.am_pm, this));
        ajaxParams.put("answer", jSONArray.toString());
        ajaxParams.put("exam_id", getIntent().getExtras().getString("exam_id", ""));
        QuestionDataRequest.getIntance(this).submitStatisticalExam(ajaxParams, this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(View view) {
        Intent intent = new Intent(this.mContext, (Class<?>) RankingActivity.class);
        intent.putExtra("title", getIntent().getExtras().getString("title"));
        intent.putExtra("exam_id", getIntent().getExtras().getString("exam_id"));
        intent.putExtra("bindSchool", this.bindSchool);
        intent.putExtra("is_estimate", getIntent().getExtras().getString("is_esaydta") + "");
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$1(View view) {
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$2(View view) {
        new DialogShare(this.mContext, new onDialogShareClickListener() { // from class: com.psychiatrygarden.activity.mp
            @Override // com.psychiatrygarden.interfaceclass.onDialogShareClickListener
            public final void onclickIntBack(int i2) throws UnsupportedEncodingException {
                this.f13028a.shareAppControl(i2);
            }
        }).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$3() throws JSONException {
        SharePreferencesUtils.writeStrConfig("statisticsPermission", "1", this);
        initData();
        this.mLyZongfen.setVisibility(0);
        this.totalfen.setText(String.format("%s分", this.score));
        this.zongfenshu.setText(String.format("用时%s", this.user_exam_time));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$setListenerForWidget$4(ExpandableListView expandableListView, View view, int i2, int i3, long j2) {
        ArrayList arrayList = new ArrayList();
        for (int i4 = 0; i4 < this.questBeans.size(); i4++) {
            if (this.yearStatisticsBean.getData().getTree().get(i2).getChildren().get(i3).getChapter_id().equals(this.questBeans.get(i4).getChapter_id())) {
                arrayList.add(this.questBeans.get(i4));
            }
        }
        ProjectApp.questExamList.clear();
        ProjectApp.questExamList.addAll(arrayList);
        Intent intent = new Intent(this.mContext, (Class<?>) SubQuestionCeshiDaActivity.class);
        intent.putExtra("exam_id", getIntent().getExtras().getString("exam_id"));
        intent.putExtra("question_file", "");
        intent.putExtra("title", getIntent().getExtras().getString("title"));
        intent.putExtra("user_exam_time", "");
        intent.putExtra("score", getIntent().getExtras().getString("score"));
        intent.putExtra("isTestEntrance", true);
        intent.putExtra("istongji", true);
        intent.putExtra("is_esaydta", getIntent().getExtras().getString("is_esaydta"));
        startActivity(intent);
        this.isJumpToDetails = true;
        return true;
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() throws JSONException {
        TextView textView = (TextView) findViewById(R.id.tv_filtrate);
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.iv_search);
        TextView textView2 = (TextView) findViewById(R.id.txt_actionbar_title);
        this.zongfe = (TextView) findViewById(R.id.zongfe);
        this.mLyZongfen = (LinearLayout) findViewById(R.id.ly_zongfe);
        textView2.setText("统计");
        this.totalfen = (TextView) findViewById(R.id.totalfen);
        this.zongfenshu = (TextView) findViewById(R.id.zongfenshu);
        this.elv_tiku_question = (ExpandableListView) findViewById(R.id.elv_tiku_question);
        TextView textView3 = (TextView) findViewById(R.id.shujujiazaizhong);
        this.shujujiazaizhong = textView3;
        textView3.setVisibility(0);
        this.bindSchool = getIntent().getExtras().getString("bindSchool");
        this.mLyZongfen.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.ip
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12536c.lambda$init$0(view);
            }
        });
        this.score = getIntent().getExtras().getString("score");
        this.user_exam_time = getIntent().getExtras().getString("user_exam_time");
        String string = getIntent().getExtras().getString("answer_number", "0");
        if ("null".equals(string) || Integer.parseInt(string) != 1) {
            this.mLyZongfen.setVisibility(8);
        } else {
            this.mLyZongfen.setVisibility(0);
            this.user_exam_time += ",";
        }
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.jp
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12568c.lambda$init$1(view);
            }
        });
        relativeLayout.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.kp
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12644c.lambda$init$2(view);
            }
        });
        if (!SharePreferencesUtils.readStrConfig("statisticsPermission", this, "0").equals("0")) {
            this.totalfen.setText(String.format("%s分", this.score));
            this.zongfenshu.setText(String.format("用时%s", this.user_exam_time));
            initData();
            return;
        }
        this.mLyZongfen.setVisibility(8);
        this.shujujiazaizhong.setVisibility(8);
        String strConfig = SharePreferencesUtils.readStrConfig("statisticsActiveId", this, "");
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put(ConstantsAPI.WXWebPage.KEY_ACTIVITY_ID, strConfig);
        MemInterface.getInstance().getMemData(this, ajaxParams, true, 0);
        MemInterface.getInstance().setmUShareListener(new MemInterface.UShareListener() { // from class: com.psychiatrygarden.activity.lp
            @Override // com.psychiatrygarden.activity.vip.Utils.MemInterface.UShareListener
            public final void mUShareListener() throws JSONException {
                this.f12676a.lambda$init$3();
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActionBar.hide();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
        if (str.equals("closePage")) {
            finish();
        } else if (!str.equals("shareClick") && str.equals("bindSuccess")) {
            this.bindSchool = "1";
        }
    }

    @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
    public void onFailure(Throwable t2, int errorNo, String strMsg, String requstUrl) {
        AlertToast(strMsg);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        if (this.isJumpToDetails) {
            return;
        }
        getExamInfoData();
    }

    @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
    public void onStart(String requstUrl) {
    }

    public String replacevalue(String s2) {
        return (s2 == null || s2.equals("")) ? "0" : s2.indexOf(StrPool.DOT) > 0 ? s2.replaceAll("0+?$", "").replaceAll("[.]$", "") : s2;
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_testentrans);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.elv_tiku_question.setOnChildClickListener(new ExpandableListView.OnChildClickListener() { // from class: com.psychiatrygarden.activity.np
            @Override // android.widget.ExpandableListView.OnChildClickListener
            public final boolean onChildClick(ExpandableListView expandableListView, View view, int i2, int i3, long j2) {
                return this.f13058a.lambda$setListenerForWidget$4(expandableListView, view, i2, i3, j2);
            }
        });
    }

    public void shareAppControl(int i2) throws UnsupportedEncodingException {
        String str;
        UMImage uMImage = getIntent().getExtras().getString("is_esaydta").equals("1") ? new UMImage(this.mContext, R.drawable.mokaoshare) : getIntent().getExtras().getString("typeData").equals("1") ? new UMImage(this.mContext, R.drawable.wanrenmokao) : new UMImage(this.mContext, R.drawable.santaojuan);
        ArrayList arrayList = new ArrayList();
        StringBuilder sb = new StringBuilder("总得分" + this.score + "分，其中");
        int i3 = 0;
        while (true) {
            str = "";
            if (i3 < this.yearStatisticsBean.getData().getTree().size()) {
                ArrayList arrayList2 = new ArrayList();
                arrayList2.add(this.yearStatisticsBean.getData().getTree().get(i3).getChapter_id() + "");
                arrayList2.add(this.yearStatisticsBean.getData().getTree().get(i3).getFull_mark());
                arrayList2.add(this.yearStatisticsBean.getData().getTree().get(i3).getScore());
                arrayList.add(arrayList2);
                sb.append(this.yearStatisticsBean.getData().getTree().get(i3).getTitle());
                sb.append(this.yearStatisticsBean.getData().getTree().get(i3).getScore());
                sb.append("分");
                if (i2 < this.questBeans.size() - 1) {
                    sb.append("，");
                } else {
                    sb.append("。");
                }
                i3++;
            } else {
                try {
                    break;
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
        str = NetworkRequestsURL.statisticsShareUrl + "title=" + getIntent().getExtras().getString("title") + "&app_id=" + SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, this.mContext) + "&s=" + this.score + "&t=" + URLEncoder.encode(this.user_exam_time, "UTF-8") + "&list=" + URLEncoder.encode(new Gson().toJson(arrayList).trim(), "UTF-8");
        String str2 = ("我的" + getIntent().getStringExtra("title")) + "测试成绩为" + this.score + "分";
        UMWeb uMWeb = new UMWeb(str);
        uMWeb.setThumb(uMImage);
        if (i2 == 3) {
            uMWeb.setDescription(str2);
            new ShareAction(this).withMedia(uMWeb).setPlatform(BaseActivity.platforms.get(i2).mPlatform).setCallback(this.umShareListener).share();
        } else {
            uMWeb.setTitle(str2);
            uMWeb.setDescription(sb.toString());
            new ShareAction(this).withMedia(uMWeb).setPlatform(BaseActivity.platforms.get(i2).mPlatform).setCallback(this.umShareListener).share();
        }
    }

    @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
    public void onSuccess(String s2, String requstUrl) {
        YearStatisticsBean yearStatisticsBean = (YearStatisticsBean) new Gson().fromJson(s2, YearStatisticsBean.class);
        this.yearStatisticsBean = yearStatisticsBean;
        if (!yearStatisticsBean.getCode().equals("200")) {
            AlertToast(this.yearStatisticsBean.getMessage());
            return;
        }
        this.shujujiazaizhong.setVisibility(8);
        double d3 = Double.parseDouble(this.yearStatisticsBean.getData().getFull_mark()) > 0.0d ? 100.0d * (Double.parseDouble(this.yearStatisticsBean.getData().getScore()) / Double.parseDouble(this.yearStatisticsBean.getData().getFull_mark())) : 0.0d;
        this.totalfen.setText(String.format("%s分", this.yearStatisticsBean.getData().getScore()));
        this.elv_tiku_question.setAdapter(new TestEntranceAdapter(this, this.yearStatisticsBean.getData().getTree(), d3));
    }
}
