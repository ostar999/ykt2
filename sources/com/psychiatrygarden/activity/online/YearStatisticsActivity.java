package com.psychiatrygarden.activity.online;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.text.StrPool;
import com.aliyun.vod.common.utils.UriUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hjq.permissions.Permission;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.online.adapter.YearStaticAdapter;
import com.psychiatrygarden.activity.online.bean.QuestionDetailBean;
import com.psychiatrygarden.activity.online.bean.YearStatisticsBean;
import com.psychiatrygarden.bean.QuestionListTestDataBean;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.http.QuestionDataRequest;
import com.psychiatrygarden.interfaceclass.QuestionDataCallBack;
import com.psychiatrygarden.interfaceclass.onDialogShareClickListener;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.widget.DialogShare;
import com.psychiatrygarden.widget.RequestMediaPermissionPop;
import com.tencent.connect.common.Constants;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.yikaobang.yixue.R;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Pattern;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class YearStatisticsActivity extends BaseActivity implements QuestionDataCallBack<String> {
    private static final int[] images = {R.drawable.ceping1, R.drawable.ceping2, R.drawable.ceping3, R.drawable.ceping4, R.drawable.ceping5, R.drawable.ceping6, R.drawable.ceping7, R.drawable.ceping8, R.drawable.ceping9};
    private String defenlv;
    private ExpandableListView elv_tiku_question;
    private String mNowTime;
    private String module_type;
    private TextView shujujiazaizhong;
    private TextView totalfen;
    private TextView tv_filtrate;
    private String unit;
    private String year;
    private YearStatisticsBean yearStatisticsBean;
    private TextView zongfenshu;
    private List<QuestionDetailBean> questionList = new ArrayList();
    private String title = "";
    private final UMShareListener umShareListener = new UMShareListener() { // from class: com.psychiatrygarden.activity.online.YearStatisticsActivity.2
        @Override // com.umeng.socialize.UMShareListener
        public void onCancel(SHARE_MEDIA arg0) {
        }

        @Override // com.umeng.socialize.UMShareListener
        public void onError(SHARE_MEDIA arg0, Throwable arg1) {
            YearStatisticsActivity.this.AlertToast("分享失败");
        }

        @Override // com.umeng.socialize.UMShareListener
        public void onResult(SHARE_MEDIA arg0) {
            YearStatisticsActivity.this.AlertToast("分享成功");
        }

        @Override // com.umeng.socialize.UMShareListener
        public void onStart(SHARE_MEDIA share_media) {
        }
    };

    private void initData() throws JSONException {
        this.questionList = (List) new Gson().fromJson(ProjectApp.showQuestionList, new TypeToken<List<QuestionDetailBean>>() { // from class: com.psychiatrygarden.activity.online.YearStatisticsActivity.1
        }.getType());
        JSONArray jSONArray = new JSONArray();
        for (int i2 = 0; i2 < this.questionList.size(); i2++) {
            try {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("question_id", this.questionList.get(i2).getId());
                jSONObject.put("score", this.questionList.get(i2).getScore());
                if (this.questionList.get(i2).getIs_right().equals("1")) {
                    jSONObject.put("is_right", "1");
                } else {
                    jSONObject.put("is_right", "0");
                }
                jSONObject.put("chapter_id", this.questionList.get(i2).getChapter_id());
                jSONArray.put(jSONObject);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("am_pm", SharePreferencesUtils.readStrConfig(CommonParameter.am_pm, this));
        ajaxParams.put("identity_id", SharePreferencesUtils.readStrConfig(CommonParameter.default_identity_id, this, ""));
        ajaxParams.put("answer", jSONArray.toString());
        ajaxParams.put("module_type", this.module_type);
        QuestionDataRequest.getIntance(this).submitStatistical(ajaxParams, this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$0() {
        ActivityCompat.requestPermissions(this, new String[]{Permission.WRITE_EXTERNAL_STORAGE, Permission.READ_EXTERNAL_STORAGE}, 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$setListenerForWidget$1(ExpandableListView expandableListView, View view, int i2, int i3, long j2) {
        ArrayList arrayList = new ArrayList();
        YearStatisticsBean.DataDTO.TreeDTO.ChildrenDTO childrenDTO = this.yearStatisticsBean.getData().getTree().get(i2).getChildren().get(i3);
        for (int i4 = 0; i4 < this.questionList.size(); i4++) {
            int i5 = 0;
            while (true) {
                if (i5 >= childrenDTO.getQuestion_ids().size()) {
                    break;
                }
                if (childrenDTO.getQuestion_ids().get(i5).equals(this.questionList.get(i4).getId())) {
                    arrayList.add(this.questionList.get(i4));
                    break;
                }
                i5++;
            }
        }
        ProjectApp.showQuestionList = new Gson().toJson(arrayList);
        Bundle bundle = new Bundle();
        bundle.putBoolean("isYearStatistics", true);
        bundle.putString(UriUtil.QUERY_CATEGORY, "chapter");
        bundle.putString("module_type", this.module_type);
        bundle.putString("type", "all");
        bundle.putString("subject_title", "" + this.yearStatisticsBean.getData().getTree().get(i2).getTitle());
        bundle.putString("chapter_title", "" + childrenDTO.getTitle());
        AnswerSheetActivity.gotoActivity(this, bundle);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$2(View view) {
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$3(View view) {
        new DialogShare(this.mContext, new onDialogShareClickListener() { // from class: com.psychiatrygarden.activity.online.p2
            @Override // com.psychiatrygarden.interfaceclass.onDialogShareClickListener
            public final void onclickIntBack(int i2) throws UnsupportedEncodingException {
                this.f13468a.shareAppControl(i2);
            }
        }).show();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() throws JSONException {
        this.tv_filtrate = (TextView) findViewById(R.id.tv_filtrate);
        TextView textView = (TextView) findViewById(R.id.txt_actionbar_title);
        this.totalfen = (TextView) findViewById(R.id.totalfen);
        this.zongfenshu = (TextView) findViewById(R.id.zongfenshu);
        this.elv_tiku_question = (ExpandableListView) findViewById(R.id.elv_tiku_question);
        TextView textView2 = (TextView) findViewById(R.id.shujujiazaizhong);
        this.shujujiazaizhong = textView2;
        textView2.setVisibility(0);
        if (!SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, this.mContext).equals(Constants.VIA_REPORT_TYPE_SHARE_TO_QQ)) {
            this.title = this.year + "年" + SharePreferencesUtils.readStrConfig(CommonParameter.app_title, this.mContext) + this.unit + "真题";
        } else if (this.year.equals("2017") || this.year.equals("2018")) {
            this.title = this.year + "年临床医学综合能力真题";
        } else {
            this.title = this.year + "年西医综合真题";
        }
        textView.setText(this.title);
        initData();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int requestCode, int arg1, Intent data) {
        super.onActivityResult(requestCode, arg1, data);
        UMShareAPI.get(this.mContext).onActivityResult(requestCode, arg1, data);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActionBar.hide();
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Permission.WRITE_EXTERNAL_STORAGE) || ContextCompat.checkSelfPermission(this.mContext, Permission.WRITE_EXTERNAL_STORAGE) == 0) {
            return;
        }
        new XPopup.Builder(this).asCustom(new RequestMediaPermissionPop(this, new OnConfirmListener() { // from class: com.psychiatrygarden.activity.online.t2
            @Override // com.lxj.xpopup.interfaces.OnConfirmListener
            public final void onConfirm() {
                this.f13486a.lambda$onCreate$0();
            }
        })).show();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        ProjectApp.showQuestionList = new Gson().toJson(this.questionList);
        super.onDestroy();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
    public void onFailure(Throwable t2, int errorNo, String strMsg, String requstUrl) {
    }

    @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
    public void onStart(String requstUrl) {
    }

    public String replaceBlank(String str) {
        return str != null ? Pattern.compile("\\s*|\t|\r|\n").matcher(str).replaceAll("") : "";
    }

    public String replacevalue(String s2) {
        return (s2 == null || s2.equals("")) ? "0" : s2.indexOf(StrPool.DOT) > 0 ? s2.replaceAll("0+?$", "").replaceAll("[.]$", "") : s2;
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        this.mNowTime = new SimpleDateFormat(DatePattern.CHINESE_DATE_PATTERN, Locale.CHINA).format(new Date(System.currentTimeMillis()));
        this.module_type = getIntent().getStringExtra("module_type");
        this.unit = getIntent().getStringExtra("unit");
        this.year = getIntent().getStringExtra("year");
        setContentView(R.layout.activity_test_statistics);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.elv_tiku_question.setOnChildClickListener(new ExpandableListView.OnChildClickListener() { // from class: com.psychiatrygarden.activity.online.q2
            @Override // android.widget.ExpandableListView.OnChildClickListener
            public final boolean onChildClick(ExpandableListView expandableListView, View view, int i2, int i3, long j2) {
                return this.f13474a.lambda$setListenerForWidget$1(expandableListView, view, i2, i3, j2);
            }
        });
        this.tv_filtrate.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.online.r2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13477c.lambda$setListenerForWidget$2(view);
            }
        });
        findViewById(R.id.iv_share).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.online.s2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13481c.lambda$setListenerForWidget$3(view);
            }
        });
    }

    public void shareAppControl(int i2) throws UnsupportedEncodingException {
        String str;
        String str2;
        Context context = this.mContext;
        int[] iArr = images;
        UMImage uMImage = new UMImage(context, iArr[new Random().nextInt(iArr.length)]);
        QuestionListTestDataBean questionListTestDataBean = new QuestionListTestDataBean();
        questionListTestDataBean.setTitle(this.title);
        questionListTestDataBean.setUser_name(UserConfig.getInstance().getUser().getNickname());
        questionListTestDataBean.setTime(this.mNowTime);
        questionListTestDataBean.setGet_score(this.yearStatisticsBean.getData().getScore());
        questionListTestDataBean.setTotal_score(this.yearStatisticsBean.getData().getFull_mark());
        questionListTestDataBean.setScrore_rate(this.defenlv + "%");
        StringBuilder sb = new StringBuilder("总得分" + this.yearStatisticsBean.getData().getScore() + "分，");
        ArrayList arrayList = new ArrayList();
        for (int i3 = 0; i3 < this.yearStatisticsBean.getData().getTree().size(); i3++) {
            arrayList.add((this.yearStatisticsBean.getData().getTree().get(i3).getChapter_id() + StrPool.UNDERLINE + this.yearStatisticsBean.getData().getTree().get(i3).getScore() + StrPool.UNDERLINE + this.yearStatisticsBean.getData().getTree().get(i3).getFull_mark()).trim());
            sb.append(this.yearStatisticsBean.getData().getTree().get(i3).getTitle());
            sb.append(this.yearStatisticsBean.getData().getTree().get(i3).getScore());
            sb.append("分");
            if (i2 < this.yearStatisticsBean.getData().getTree().size() - 1) {
                sb.append("，");
            } else {
                sb.append("。");
            }
        }
        questionListTestDataBean.setList(arrayList);
        try {
            str = "https://m.yikaobang.com.cn/exams/index.php?app_id=" + SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, this.mContext) + "&data=" + replaceBlank(URLEncoder.encode(new Gson().toJson(questionListTestDataBean).toString().trim(), "UTF-8"));
        } catch (Exception e2) {
            e2.printStackTrace();
            str = "";
        }
        if (!SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, this.mContext).equals(Constants.VIA_REPORT_TYPE_SHARE_TO_QQ)) {
            str2 = this.year + "年" + SharePreferencesUtils.readStrConfig(CommonParameter.app_title, this.mContext) + this.unit + "真题测试评估";
        } else if (this.year.equals("2017") || this.year.equals("2018")) {
            str2 = this.year + "年临床医学综合能力真题测试评估";
        } else {
            str2 = this.year + "年西医综合真题测试评估";
        }
        String str3 = str2 + "测试成绩为" + this.yearStatisticsBean.getData().getScore() + "分";
        if (i2 == 3) {
            UMWeb uMWeb = new UMWeb(str);
            uMWeb.setTitle(str3);
            uMWeb.setDescription(str);
            uMWeb.setThumb(uMImage);
            new ShareAction(this).withMedia(uMWeb).setPlatform(BaseActivity.platforms.get(i2).mPlatform).setCallback(this.umShareListener).share();
            return;
        }
        UMWeb uMWeb2 = new UMWeb(str);
        uMWeb2.setTitle(str3);
        uMWeb2.setDescription(sb.toString());
        uMWeb2.setThumb(uMImage);
        new ShareAction(this).withMedia(uMWeb2).setPlatform(BaseActivity.platforms.get(i2).mPlatform).setCallback(this.umShareListener).share();
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
        this.defenlv = replacevalue(new DecimalFormat("#0.0").format(d3)) + "%";
        this.totalfen.setText(String.format("%s分", this.yearStatisticsBean.getData().getScore()));
        this.zongfenshu.setText(String.format("满分%s，得分率%s", this.yearStatisticsBean.getData().getFull_mark(), this.defenlv));
        this.elv_tiku_question.setAdapter(new YearStaticAdapter(this, this.yearStatisticsBean.getData().getTree(), d3));
    }
}
