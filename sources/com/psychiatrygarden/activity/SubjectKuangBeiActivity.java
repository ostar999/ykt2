package com.psychiatrygarden.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ExpandableListView;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.psychiatrygarden.adapter.SubjectFWNExpandableListAdapter;
import com.psychiatrygarden.bean.oneTitleDb;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.interfaceclass.onDialogShareClickListener;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.LogUtils;
import com.psychiatrygarden.widget.DialogShare;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class SubjectKuangBeiActivity extends BaseActivity {
    private ExpandableListView elv_subject;
    private SubjectFWNExpandableListAdapter mAdapter;
    public ArrayList<oneTitleDb> sListDb;
    private Timer timer;
    private TimerTask timerTask;
    private int tempPosition = 0;
    private int sign = -1;
    private int group_childerpostion = 0;
    String json_kb_ti = "";
    private long time = 0;
    boolean pingfen_jia = true;

    @SuppressLint({"HandlerLeak"})
    private final Handler mHandler = new Handler() { // from class: com.psychiatrygarden.activity.SubjectKuangBeiActivity.1
        @Override // android.os.Handler
        public void handleMessage(Message msg) throws JSONException {
            int i2 = msg.what;
            if (i2 != 1) {
                if (i2 == 2) {
                    SubjectKuangBeiActivity.this.getKBShare();
                    return;
                }
                return;
            }
            if (SubjectKuangBeiActivity.this.time <= 30) {
                SubjectKuangBeiActivity.this.time++;
                return;
            }
            JSONArray jSONArray = new JSONArray();
            for (int i3 = 0; i3 < 5; i3++) {
                try {
                    jSONArray.put(i3, 0);
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
            }
            if (SharePreferencesUtils.readStrConfig(CommonParameter.APP_TIKU_MARK, SubjectKuangBeiActivity.this.mContext, "").equals(CommonParameter.Xueshuo)) {
                try {
                    JSONArray jSONArray2 = new JSONArray(SharePreferencesUtils.readStrConfig(CommonParameter.UNLOCK_XUE_BEI, SubjectKuangBeiActivity.this.mContext, jSONArray.toString()));
                    jSONArray2.put(0, jSONArray2.optInt(0) + 1);
                    SharePreferencesUtils.writeStrConfig(CommonParameter.UNLOCK_XUE_BEI, jSONArray2.toString(), SubjectKuangBeiActivity.this.mContext);
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
            } else {
                try {
                    JSONArray jSONArray3 = new JSONArray(SharePreferencesUtils.readStrConfig(CommonParameter.UNLOCK_ZHUAN_BEI, SubjectKuangBeiActivity.this.mContext, jSONArray.toString()));
                    jSONArray3.put(0, jSONArray3.optInt(0) + 1);
                    SharePreferencesUtils.writeStrConfig(CommonParameter.UNLOCK_ZHUAN_BEI, jSONArray3.toString(), SubjectKuangBeiActivity.this.mContext);
                    SubjectKuangBeiActivity subjectKuangBeiActivity = SubjectKuangBeiActivity.this;
                    subjectKuangBeiActivity.shareData(subjectKuangBeiActivity.sListDb.get(0).getCate_p_id());
                } catch (Exception e4) {
                    e4.printStackTrace();
                }
            }
            SubjectKuangBeiActivity subjectKuangBeiActivity2 = SubjectKuangBeiActivity.this;
            subjectKuangBeiActivity2.pingfen_jia = true;
            subjectKuangBeiActivity2.shareData(subjectKuangBeiActivity2.sListDb.get(0).getCate_p_id());
            SubjectKuangBeiActivity.this.timer.cancel();
            SubjectKuangBeiActivity.this.mAdapter.notifyDataSetChanged();
        }
    };
    private final UMShareListener umShareListener = new UMShareListener() { // from class: com.psychiatrygarden.activity.SubjectKuangBeiActivity.6
        @Override // com.umeng.socialize.UMShareListener
        public void onCancel(SHARE_MEDIA arg0) {
        }

        @Override // com.umeng.socialize.UMShareListener
        public void onError(SHARE_MEDIA arg0, Throwable arg1) {
        }

        @Override // com.umeng.socialize.UMShareListener
        public void onResult(SHARE_MEDIA arg0) throws JSONException {
            SubjectKuangBeiActivity.this.AlertToast("分享成功");
            JSONArray jSONArray = new JSONArray();
            for (int i2 = 0; i2 < 5; i2++) {
                try {
                    jSONArray.put(i2, 0);
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
            }
            if (arg0 == SHARE_MEDIA.QQ) {
                if (SharePreferencesUtils.readStrConfig(CommonParameter.APP_TIKU_MARK, SubjectKuangBeiActivity.this.mContext, "").equals(CommonParameter.Xueshuo)) {
                    try {
                        JSONArray jSONArray2 = new JSONArray(SharePreferencesUtils.readStrConfig(CommonParameter.UNLOCK_XUE_BEI, SubjectKuangBeiActivity.this.mContext, jSONArray.toString()));
                        if (SubjectKuangBeiActivity.this.group_childerpostion == 1) {
                            jSONArray2.put(SubjectKuangBeiActivity.this.group_childerpostion, jSONArray2.optInt(SubjectKuangBeiActivity.this.group_childerpostion) + 1);
                            SharePreferencesUtils.writeStrConfig(CommonParameter.UNLOCK_XUE_BEI, jSONArray2.toString(), SubjectKuangBeiActivity.this.mContext);
                            SubjectKuangBeiActivity subjectKuangBeiActivity = SubjectKuangBeiActivity.this;
                            subjectKuangBeiActivity.shareData(subjectKuangBeiActivity.sListDb.get(subjectKuangBeiActivity.group_childerpostion).getCate_p_id());
                            SubjectKuangBeiActivity subjectKuangBeiActivity2 = SubjectKuangBeiActivity.this;
                            LogUtils.d("------xueke----", subjectKuangBeiActivity2.sListDb.get(subjectKuangBeiActivity2.group_childerpostion).getCate_p_id());
                        }
                    } catch (Exception e3) {
                        e3.printStackTrace();
                    }
                } else {
                    try {
                        JSONArray jSONArray3 = new JSONArray(SharePreferencesUtils.readStrConfig(CommonParameter.UNLOCK_ZHUAN_BEI, SubjectKuangBeiActivity.this.mContext, jSONArray.toString()));
                        if (SubjectKuangBeiActivity.this.group_childerpostion == 1) {
                            jSONArray3.put(SubjectKuangBeiActivity.this.group_childerpostion, jSONArray3.optInt(SubjectKuangBeiActivity.this.group_childerpostion) + 1);
                            SharePreferencesUtils.writeStrConfig(CommonParameter.UNLOCK_ZHUAN_BEI, jSONArray3.toString(), SubjectKuangBeiActivity.this.mContext);
                            SubjectKuangBeiActivity subjectKuangBeiActivity3 = SubjectKuangBeiActivity.this;
                            subjectKuangBeiActivity3.shareData(subjectKuangBeiActivity3.sListDb.get(subjectKuangBeiActivity3.group_childerpostion).getCate_p_id());
                        }
                    } catch (Exception e4) {
                        e4.printStackTrace();
                    }
                }
            }
            SubjectKuangBeiActivity.this.mAdapter.notifyDataSetChanged();
        }

        @Override // com.umeng.socialize.UMShareListener
        public void onStart(SHARE_MEDIA share_media) {
        }
    };

    public class ProgressBarAsyncTask extends AsyncTask<Integer, Integer, String> {
        public ProgressBarAsyncTask() {
        }

        @Override // android.os.AsyncTask
        public String doInBackground(Integer... params) {
            return null;
        }

        @Override // android.os.AsyncTask
        public void onPreExecute() {
        }

        @Override // android.os.AsyncTask
        public void onProgressUpdate(Integer... values) {
        }

        @Override // android.os.AsyncTask
        public void onPostExecute(String result) {
            SubjectKuangBeiActivity subjectKuangBeiActivity = SubjectKuangBeiActivity.this;
            SubjectKuangBeiActivity subjectKuangBeiActivity2 = SubjectKuangBeiActivity.this;
            subjectKuangBeiActivity.mAdapter = new SubjectFWNExpandableListAdapter(subjectKuangBeiActivity2.mContext, subjectKuangBeiActivity2.sListDb, "kuangbei");
            SubjectKuangBeiActivity.this.elv_subject.setAdapter(SubjectKuangBeiActivity.this.mAdapter);
            SubjectKuangBeiActivity.this.mHandler.sendEmptyMessage(2);
            SubjectKuangBeiActivity.this.getKBTi();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getKBShare() {
        YJYHttpUtils.get(this.mContext, "", new AjaxParams(), new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.SubjectKuangBeiActivity.2
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String t2) {
                JSONObject jSONObjectOptJSONObject;
                try {
                    JSONObject jSONObject = new JSONObject(t2);
                    if (!jSONObject.optString("code").equals("200") || (jSONObjectOptJSONObject = jSONObject.optJSONObject("data")) == null) {
                        return;
                    }
                    SharePreferencesUtils.writeStrConfig(CommonParameter.SHARE_CONTENT_XUE_BEI, jSONObjectOptJSONObject.optString("xue_info"), SubjectKuangBeiActivity.this);
                    SharePreferencesUtils.writeStrConfig(CommonParameter.SHARE_CONTENT_ZHUAN_BEI, jSONObjectOptJSONObject.optString("zhuan_info"), SubjectKuangBeiActivity.this);
                    SharePreferencesUtils.writeStrConfig(CommonParameter.UNLOCK_XUE_BEI, jSONObjectOptJSONObject.optString("xue"), SubjectKuangBeiActivity.this);
                    SharePreferencesUtils.writeStrConfig(CommonParameter.UNLOCK_ZHUAN_BEI, jSONObjectOptJSONObject.optString("zhuan"), SubjectKuangBeiActivity.this);
                    SubjectKuangBeiActivity.this.mAdapter.notifyDataSetChanged();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getKBTi() {
        AjaxParams ajaxParams = new AjaxParams();
        String strConfig = SharePreferencesUtils.readStrConfig(CommonParameter.QUESTION_BEI_TIME, this.mContext, "1511245418");
        if (strConfig.equals("")) {
            ajaxParams.put("utime", "1511245418");
        } else {
            ajaxParams.put("utime", strConfig);
        }
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.mQuestionBeiURL, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.SubjectKuangBeiActivity.3
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String t2) {
                LogUtils.e(SubjectKuangBeiActivity.this.TAG, t2);
                SubjectKuangBeiActivity.this.json_kb_ti = t2;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$setListenerForWidget$0(ExpandableListView expandableListView, View view, int i2, int i3, long j2) {
        if (CommonUtil.isFastClick()) {
            return true;
        }
        Intent intent = new Intent(this.mContext, (Class<?>) SubjectKuangBeiListActivity.class);
        intent.putExtra("subject_name", this.sListDb.get(i2).getCate_name());
        intent.putExtra("chapter_name", this.sListDb.get(i2).getChapters().get(i3).getCate_name());
        intent.putExtra("chapter_id", this.sListDb.get(i2).getChapters().get(i3).getCate_id());
        startActivity(intent);
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$setListenerForWidget$1(ExpandableListView expandableListView, View view, int i2, long j2) throws JSONException {
        if (Integer.parseInt(this.sListDb.get(i2).getTotal()) < 1 || CommonUtil.isFastClick()) {
            return true;
        }
        JSONArray jSONArray = new JSONArray();
        for (int i3 = 0; i3 < 5; i3++) {
            try {
                jSONArray.put(i3, 0);
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
        if (SharePreferencesUtils.readStrConfig(CommonParameter.APP_TIKU_MARK, this.mContext, "").equals(CommonParameter.Xueshuo)) {
            try {
                JSONArray jSONArray2 = new JSONArray(SharePreferencesUtils.readStrConfig(CommonParameter.UNLOCK_XUE_BEI, this.mContext, jSONArray.toString()));
                if (i2 != 0) {
                    if (i2 == 1 && jSONArray2.optInt(i2) < 1) {
                        if (isLogin()) {
                            shareDialog(i2);
                        } else {
                            AlertToast("您还没有登录");
                        }
                        return true;
                    }
                } else if (jSONArray2.optInt(i2) < 1) {
                    if (isLogin()) {
                        try {
                            Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + getPackageName()));
                            intent.addFlags(268435456);
                            startActivity(intent);
                            this.pingfen_jia = true;
                            shareData(this.sListDb.get(0).getCate_p_id());
                            Timer timer = this.timer;
                            if (timer != null) {
                                timer.cancel();
                            }
                            this.timer = new Timer();
                            TimerTask timerTask = new TimerTask() { // from class: com.psychiatrygarden.activity.SubjectKuangBeiActivity.4
                                @Override // java.util.TimerTask, java.lang.Runnable
                                public void run() {
                                    Message messageObtain = Message.obtain();
                                    messageObtain.what = 1;
                                    SubjectKuangBeiActivity.this.mHandler.sendMessage(messageObtain);
                                }
                            };
                            this.timerTask = timerTask;
                            this.timer.schedule(timerTask, 1000L, 1000L);
                        } catch (Exception unused) {
                            AlertToast("评分出错了~");
                        }
                    } else {
                        AlertToast("您还没有登录");
                    }
                    return true;
                }
            } catch (Exception e3) {
                e3.printStackTrace();
            }
        } else {
            try {
                JSONArray jSONArray3 = new JSONArray(SharePreferencesUtils.readStrConfig(CommonParameter.UNLOCK_ZHUAN_BEI, this.mContext, jSONArray.toString()));
                if (i2 != 0) {
                    if (i2 == 1 && jSONArray3.optInt(i2) < 1) {
                        if (isLogin()) {
                            shareDialog(i2);
                        } else {
                            AlertToast("您还没有登录");
                        }
                        return true;
                    }
                } else if (jSONArray3.optInt(i2) < 1) {
                    if (isLogin()) {
                        try {
                            Intent intent2 = new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + getPackageName()));
                            intent2.addFlags(268435456);
                            startActivity(intent2);
                            this.pingfen_jia = true;
                            shareData(this.sListDb.get(0).getCate_p_id());
                            Timer timer2 = this.timer;
                            if (timer2 != null) {
                                timer2.cancel();
                            }
                            this.timer = new Timer();
                            TimerTask timerTask2 = new TimerTask() { // from class: com.psychiatrygarden.activity.SubjectKuangBeiActivity.5
                                @Override // java.util.TimerTask, java.lang.Runnable
                                public void run() {
                                    Message messageObtain = Message.obtain();
                                    messageObtain.what = 1;
                                    SubjectKuangBeiActivity.this.mHandler.sendMessage(messageObtain);
                                }
                            };
                            this.timerTask = timerTask2;
                            this.timer.schedule(timerTask2, 1000L, 1000L);
                        } catch (Exception unused2) {
                            AlertToast("评分出错了~");
                        }
                    } else {
                        AlertToast("您还没有登录");
                    }
                    return true;
                }
            } catch (Exception e4) {
                e4.printStackTrace();
            }
        }
        int i4 = this.sign;
        if (i4 == -1) {
            this.elv_subject.expandGroup(i2);
            this.elv_subject.setSelectedGroup(i2);
            this.sign = i2;
        } else if (i4 == i2) {
            this.elv_subject.collapseGroup(i4);
            this.sign = -1;
        } else {
            this.elv_subject.collapseGroup(i4);
            this.elv_subject.expandGroup(i2);
            this.elv_subject.setSelectedGroup(i2);
            this.sign = i2;
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$2(int i2) {
        int i3 = this.tempPosition;
        if (i3 != i2) {
            this.elv_subject.collapseGroup(i3);
            this.tempPosition = i2;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$shareData$4(String str, String str2) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$shareData$5(VolleyError volleyError, String str) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$shareDialog$3(int i2, int i3) {
        this.group_childerpostion = i2;
        shareAppControl(i3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @SuppressLint({"NewApi"})
    public void shareData(String chapter_parent_id) {
        HashMap map = new HashMap();
        if (!this.pingfen_jia) {
            map.put("count", "-1");
        }
        map.put("chapter_parent_id", chapter_parent_id);
        if (SharePreferencesUtils.readStrConfig(CommonParameter.APP_TIKU_MARK, this.mContext, "").equals(CommonParameter.Xueshuo)) {
            map.put("type", "1");
        } else {
            map.put("type", "2");
        }
        map.put("token", UserConfig.getInstance().getUser().getToken());
        map.put("secret", UserConfig.getInstance().getUser().getSecret());
        YJYHttpUtils.post(this, NetworkRequestsURL.mShareUnlockURL, map, new Response.Listener() { // from class: com.psychiatrygarden.activity.qm
            @Override // com.android.volley.Response.Listener
            public final void onResponse(Object obj, String str) {
                SubjectKuangBeiActivity.lambda$shareData$4((String) obj, str);
            }
        }, new Response.ErrorListener() { // from class: com.psychiatrygarden.activity.rm
            @Override // com.android.volley.Response.ErrorListener
            public final void onErrorResponse(VolleyError volleyError, String str) {
                SubjectKuangBeiActivity.lambda$shareData$5(volleyError, str);
            }
        });
    }

    @SuppressLint({"NewApi"})
    private void shareDialog(final int groupPosition) {
        new DialogShare(this.mContext, new onDialogShareClickListener() { // from class: com.psychiatrygarden.activity.mm
            @Override // com.psychiatrygarden.interfaceclass.onDialogShareClickListener
            public final void onclickIntBack(int i2) {
                this.f13022a.lambda$shareDialog$3(groupPosition, i2);
            }
        }).show();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.elv_subject = (ExpandableListView) findViewById(R.id.elv_subject);
        new ProgressBarAsyncTask().execute(1000);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this.mContext).onActivityResult(requestCode, resultCode, data);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        Timer timer = this.timer;
        if (timer != null) {
            timer.cancel();
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() throws JSONException {
        super.onResume();
        if (this.timer != null) {
            if (this.time < 30) {
                JSONArray jSONArray = new JSONArray();
                for (int i2 = 0; i2 < 5; i2++) {
                    try {
                        jSONArray.put(i2, 0);
                    } catch (JSONException e2) {
                        e2.printStackTrace();
                    }
                }
                if (SharePreferencesUtils.readStrConfig(CommonParameter.APP_TIKU_MARK, this.mContext, "").equals(CommonParameter.Xueshuo)) {
                    try {
                        JSONArray jSONArray2 = new JSONArray(SharePreferencesUtils.readStrConfig(CommonParameter.UNLOCK_XUE_BEI, this.mContext, jSONArray.toString()));
                        jSONArray2.put(0, 0);
                        SharePreferencesUtils.writeStrConfig(CommonParameter.UNLOCK_XUE_BEI, jSONArray2.toString(), this.mContext);
                    } catch (Exception e3) {
                        e3.printStackTrace();
                    }
                } else {
                    try {
                        JSONArray jSONArray3 = new JSONArray(SharePreferencesUtils.readStrConfig(CommonParameter.UNLOCK_ZHUAN_BEI, this.mContext, jSONArray.toString()));
                        jSONArray3.put(0, 0);
                        SharePreferencesUtils.writeStrConfig(CommonParameter.UNLOCK_ZHUAN_BEI, jSONArray3.toString(), this.mContext);
                        shareData(this.sListDb.get(0).getCate_p_id());
                    } catch (Exception e4) {
                        e4.printStackTrace();
                    }
                }
                this.pingfen_jia = false;
                shareData(this.sListDb.get(0).getCate_p_id());
                this.mAdapter.notifyDataSetChanged();
            }
            this.timer.cancel();
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setTitle("考点狂背");
        setContentView(R.layout.activity_subject_fwn);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.elv_subject.setOnChildClickListener(new ExpandableListView.OnChildClickListener() { // from class: com.psychiatrygarden.activity.nm
            @Override // android.widget.ExpandableListView.OnChildClickListener
            public final boolean onChildClick(ExpandableListView expandableListView, View view, int i2, int i3, long j2) {
                return this.f13055a.lambda$setListenerForWidget$0(expandableListView, view, i2, i3, j2);
            }
        });
        this.elv_subject.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() { // from class: com.psychiatrygarden.activity.om
            @Override // android.widget.ExpandableListView.OnGroupClickListener
            public final boolean onGroupClick(ExpandableListView expandableListView, View view, int i2, long j2) {
                return this.f13085a.lambda$setListenerForWidget$1(expandableListView, view, i2, j2);
            }
        });
        this.elv_subject.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() { // from class: com.psychiatrygarden.activity.pm
            @Override // android.widget.ExpandableListView.OnGroupExpandListener
            public final void onGroupExpand(int i2) {
                this.f13551a.lambda$setListenerForWidget$2(i2);
            }
        });
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x00ad  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x00cc  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void shareAppControl(int r14) {
        /*
            Method dump skipped, instructions count: 261
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.psychiatrygarden.activity.SubjectKuangBeiActivity.shareAppControl(int):void");
    }
}
