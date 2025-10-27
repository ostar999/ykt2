package com.psychiatrygarden.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.text.StrPool;
import com.google.gson.Gson;
import com.hjq.permissions.Permission;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.psychiatrygarden.bean.QuestionListTestDataBean;
import com.psychiatrygarden.bean.SelectTikuBeam;
import com.psychiatrygarden.bean.UserOwnerBean;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.db.SharePreferencesUtils;
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
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Pattern;

/* loaded from: classes5.dex */
public class TestStatisticsActivity extends BaseActivity {
    private static final int[] images = {R.drawable.ceping1, R.drawable.ceping2, R.drawable.ceping3, R.drawable.ceping4, R.drawable.ceping5, R.drawable.ceping6, R.drawable.ceping7, R.drawable.ceping8, R.drawable.ceping9};
    private String defenlv;
    private ExpandableListView elv_tiku_question;
    private String mNowTime;
    private String score;
    private String scoretotal;
    private TextView shujujiazaizhong;
    private String title;
    private String year;
    private final List<UserOwnerBean.DataBean> dataBean = new ArrayList();
    private final List<SelectTikuBeam> mSelectTikuBeam = new ArrayList();
    private final List<String> mQuestionInfoStr = new ArrayList();
    private final List<String> mQuestionInfoStrTxt = new ArrayList();
    private final UMShareListener umShareListener = new UMShareListener() { // from class: com.psychiatrygarden.activity.TestStatisticsActivity.2
        @Override // com.umeng.socialize.UMShareListener
        public void onCancel(SHARE_MEDIA arg0) {
        }

        @Override // com.umeng.socialize.UMShareListener
        public void onError(SHARE_MEDIA arg0, Throwable arg1) {
            TestStatisticsActivity.this.AlertToast("分享失败");
        }

        @Override // com.umeng.socialize.UMShareListener
        public void onResult(SHARE_MEDIA arg0) {
            TestStatisticsActivity.this.AlertToast("分享成功");
        }

        @Override // com.umeng.socialize.UMShareListener
        public void onStart(SHARE_MEDIA share_media) {
        }
    };

    public static void i(String tag, String msg) {
        int length = 2001 - tag.length();
        while (msg.length() > length) {
            Log.i(tag, msg.substring(0, length));
            msg = msg.substring(length);
        }
        Log.i(tag, msg);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$init$0(ExpandableListView expandableListView, View view, int i2, int i3, long j2) {
        try {
            if (SharePreferencesUtils.readStrConfig(CommonParameter.Student_Type, this.mContext).equals("z")) {
                Intent intent = new Intent(this.mContext, (Class<?>) QuestionListTestActivity.class);
                intent.putExtra("dataBean", (Serializable) this.dataBean);
                intent.putExtra("year", this.year);
                intent.putExtra("groupPosition", i2);
                intent.putExtra("childPosition", i3);
                intent.putExtra("ISPractice", true);
                intent.putExtra("title", this.year + "年" + SharePreferencesUtils.readStrConfig(CommonParameter.app_title, this.mContext) + "真题");
                startActivity(intent);
            } else {
                int size = this.dataBean.get(i2).getChild().get(i3).getmThreadDati().size();
                long[] jArr = new long[size];
                for (int i4 = 0; i4 < this.dataBean.get(i2).getChild().get(i3).getmThreadDati().size(); i4++) {
                    jArr[i4] = Long.parseLong(this.dataBean.get(i2).getChild().get(i3).getmThreadDati().get(i4).getObj());
                }
                if (size == 0) {
                    return true;
                }
                Intent intent2 = new Intent(this.mContext, (Class<?>) YearQuestionListActivity.class);
                intent2.putExtra("year", this.year);
                intent2.putExtra("list", jArr);
                intent2.putExtra("ISPractice", true);
                intent2.putExtra("isChongzuo", true);
                if (!SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, this.mContext).equals(Constants.VIA_REPORT_TYPE_SHARE_TO_QQ)) {
                    intent2.putExtra("title", this.year + "年" + SharePreferencesUtils.readStrConfig(CommonParameter.app_title, this.mContext) + "真题");
                } else if (this.year.equals("2017") || this.year.equals("2018")) {
                    intent2.putExtra("title", this.year + "年临床医学综合能力真题");
                } else {
                    intent2.putExtra("title", this.year + "年西医综合真题");
                }
                startActivity(intent2);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$1(View view) {
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$2(View view) {
        new DialogShare(this.mContext, new onDialogShareClickListener() { // from class: com.psychiatrygarden.activity.pp
            @Override // com.psychiatrygarden.interfaceclass.onDialogShareClickListener
            public final void onclickIntBack(int i2) throws UnsupportedEncodingException {
                this.f13554a.shareAppControl(i2);
            }
        }).show();
    }

    /* JADX WARN: Removed duplicated region for block: B:137:0x03b4  */
    /* JADX WARN: Removed duplicated region for block: B:145:0x03ea  */
    /* JADX WARN: Removed duplicated region for block: B:248:0x06c9  */
    /* JADX WARN: Removed duplicated region for block: B:256:0x06ee  */
    /* JADX WARN: Removed duplicated region for block: B:303:0x07b8  */
    /* JADX WARN: Removed duplicated region for block: B:304:0x07bf  */
    /* JADX WARN: Removed duplicated region for block: B:359:0x0981  */
    /* JADX WARN: Removed duplicated region for block: B:376:0x09c6  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x0119  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0141  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void getData() {
        /*
            Method dump skipped, instructions count: 2940
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.psychiatrygarden.activity.TestStatisticsActivity.getData():void");
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        TextView textView = (TextView) findViewById(R.id.tv_filtrate);
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.iv_search);
        ((TextView) findViewById(R.id.txt_actionbar_title)).setText(this.title);
        TextView textView2 = (TextView) findViewById(R.id.totalfen);
        TextView textView3 = (TextView) findViewById(R.id.zongfenshu);
        this.elv_tiku_question = (ExpandableListView) findViewById(R.id.elv_tiku_question);
        TextView textView4 = (TextView) findViewById(R.id.shujujiazaizhong);
        this.shujujiazaizhong = textView4;
        textView4.setVisibility(0);
        this.elv_tiku_question.setOnChildClickListener(new ExpandableListView.OnChildClickListener() { // from class: com.psychiatrygarden.activity.qp
            @Override // android.widget.ExpandableListView.OnChildClickListener
            public final boolean onChildClick(ExpandableListView expandableListView, View view, int i2, int i3, long j2) {
                return this.f13749a.lambda$init$0(expandableListView, view, i2, i3, j2);
            }
        });
        textView2.setText(String.format("%s分", replacevalue(this.score)));
        textView3.setText(String.format("满分%s，得分率%s%%", replacevalue(this.scoretotal), replacevalue(this.defenlv)));
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.rp
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13807c.lambda$init$1(view);
            }
        });
        relativeLayout.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.sp
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13930c.lambda$init$2(view);
            }
        });
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
        new XPopup.Builder(this).asCustom(new RequestMediaPermissionPop(this, new OnConfirmListener() { // from class: com.psychiatrygarden.activity.TestStatisticsActivity.1
            @Override // com.lxj.xpopup.interfaces.OnConfirmListener
            public void onConfirm() {
                ActivityCompat.requestPermissions(TestStatisticsActivity.this, new String[]{Permission.WRITE_EXTERNAL_STORAGE, Permission.READ_EXTERNAL_STORAGE}, 1);
            }
        })).show();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    public String replaceBlank(String str) {
        return str != null ? Pattern.compile("\\s*|\t|\r|\n").matcher(str).replaceAll("") : "";
    }

    public String replacevalue(String s2) {
        return (s2 == null || s2.equals("")) ? "0" : s2.indexOf(StrPool.DOT) > 0 ? s2.replaceAll("0+?$", "").replaceAll("[.]$", "") : s2;
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_test_statistics);
        this.year = getIntent().getExtras().getString("year");
        this.title = getIntent().getExtras().getString("title");
        this.score = getIntent().getExtras().getString("score");
        this.scoretotal = getIntent().getExtras().getString("scoretotal");
        this.defenlv = getIntent().getExtras().getString("defenlv");
        this.mNowTime = new SimpleDateFormat(DatePattern.CHINESE_DATE_PATTERN, Locale.CHINA).format(new Date(System.currentTimeMillis()));
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        new Handler().postDelayed(new Runnable() { // from class: com.psychiatrygarden.activity.op
            @Override // java.lang.Runnable
            public final void run() {
                this.f13512c.getData();
            }
        }, 500L);
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
        questionListTestDataBean.setGet_score(this.score);
        questionListTestDataBean.setTotal_score(this.scoretotal);
        questionListTestDataBean.setScrore_rate(this.defenlv + "%");
        StringBuilder sb = new StringBuilder("总得分" + this.score + "分，");
        ArrayList arrayList = new ArrayList();
        for (int i3 = 0; i3 < this.dataBean.size(); i3++) {
            arrayList.add((this.dataBean.get(i3).getId() + StrPool.UNDERLINE + this.dataBean.get(i3).getWorngScore() + StrPool.UNDERLINE + this.dataBean.get(i3).getScroe()).trim());
            sb.append(this.dataBean.get(i3).getTitle());
            sb.append(this.dataBean.get(i3).getWorngScore());
            sb.append("分");
            if (i2 < this.dataBean.size() - 1) {
                sb.append("，");
            } else {
                sb.append("。");
            }
        }
        questionListTestDataBean.setList(arrayList);
        try {
            str = "https://m.yikaobang.com.cn/exams/index.php?app_id=" + SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, this.mContext) + "&data=" + replaceBlank(URLEncoder.encode(new Gson().toJson(questionListTestDataBean).trim(), "UTF-8"));
        } catch (Exception e2) {
            e2.printStackTrace();
            str = "";
        }
        if (!SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, this.mContext).equals(Constants.VIA_REPORT_TYPE_SHARE_TO_QQ)) {
            str2 = this.year + "年" + SharePreferencesUtils.readStrConfig(CommonParameter.app_title, this.mContext) + "真题测试评估";
        } else if (this.year.equals("2017") || this.year.equals("2018")) {
            str2 = this.year + "年临床医学综合能力真题测试评估";
        } else {
            str2 = this.year + "年西医综合真题测试评估";
        }
        String str3 = str2 + "测试成绩为" + this.score + "分";
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
}
