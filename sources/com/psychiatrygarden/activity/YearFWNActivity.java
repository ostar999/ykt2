package com.psychiatrygarden.activity;

import android.content.Intent;
import android.database.SQLException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListAdapter;
import androidx.core.content.ContextCompat;
import com.lxj.xpopup.XPopup;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.vip.MemberCenterActivity;
import com.psychiatrygarden.adapter.YearFWNAdapter;
import com.psychiatrygarden.bean.AnsweredQuestionBeanDao;
import com.psychiatrygarden.bean.ComYearFWNItemBean;
import com.psychiatrygarden.bean.QuestionInfoBean;
import com.psychiatrygarden.bean.QuestionYearBean;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.widget.CustomDialog;
import com.psychiatrygarden.widget.ExportDescriptionPop;
import com.yikaobang.yixue.R;
import de.greenrobot.dao.query.WhereCondition;
import de.greenrobot.event.EventBus;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;

/* loaded from: classes5.dex */
public class YearFWNActivity extends BaseActivity {
    private GridView gv_year;
    private YearFWNAdapter mAdapter;
    private ArrayList<QuestionYearBean> mList;
    private final String notefileurl = Environment.getExternalStorageDirectory().getAbsoluteFile() + "/yikaobang/" + SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, this.mContext) + "/";
    private String type;

    public class ProgressBarAsyncTask extends AsyncTask<Integer, Integer, String> {
        public ProgressBarAsyncTask() {
        }

        @Override // android.os.AsyncTask
        public void onPreExecute() {
            YearFWNActivity.this.showProgressDialog("加载中...");
        }

        @Override // android.os.AsyncTask
        public void onProgressUpdate(Integer... values) {
        }

        @Override // android.os.AsyncTask
        public String doInBackground(Integer... params) throws JSONException {
            YearFWNActivity.this.setData();
            return null;
        }

        @Override // android.os.AsyncTask
        public void onPostExecute(String result) {
            YearFWNActivity yearFWNActivity = YearFWNActivity.this;
            YearFWNActivity yearFWNActivity2 = YearFWNActivity.this;
            yearFWNActivity.mAdapter = new YearFWNAdapter(yearFWNActivity2.mContext, yearFWNActivity2.mList, YearFWNActivity.this.type);
            YearFWNActivity.this.gv_year.setAdapter((ListAdapter) YearFWNActivity.this.mAdapter);
            YearFWNActivity.this.hideProgressDialog();
            if (YearFWNActivity.this.mList.size() > 0) {
                YearFWNActivity.this.findViewById(R.id.tv_empty).setVisibility(8);
            } else {
                YearFWNActivity.this.findViewById(R.id.tv_empty).setVisibility(0);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$clearnDialog$0(CustomDialog customDialog, View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        customDialog.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$clearnDialog$1(CustomDialog customDialog, View view) throws SQLException {
        if (CommonUtil.isFastClick()) {
            return;
        }
        customDialog.dismissNoAnimaltion();
        ProjectApp.mDaoSession.getAnsweredQuestionBeanDao().queryBuilder().where(AnsweredQuestionBeanDao.Properties.Is_right.eq("0"), new WhereCondition[0]).buildDelete().executeDeleteWithoutDetachingEntities();
        EventBus.getDefault().post("YearQuestionClearn");
        EventBus.getDefault().post("QuestionYearFragment");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$2(View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        if (!getIntent().getStringExtra("type").equals("note") && !getIntent().getStringExtra("type").equals("error") && !getIntent().getStringExtra("type").equals("collect")) {
            clearnDialog();
        } else if (UserConfig.getInstance().getUser().getIs_vip().equals("0")) {
            startActivity(new Intent(this, (Class<?>) MemberCenterActivity.class));
        } else {
            new XPopup.Builder(this).asCustom(new ExportDescriptionPop(this, getIntent().getExtras())).toggle();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$3(AdapterView adapterView, View view, int i2, long j2) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        int size = this.mList.get(i2).getTopics().size();
        long[] jArr = new long[size];
        for (int i3 = 0; i3 < size; i3++) {
            jArr[i3] = this.mList.get(i2).getTopics().get(i3).getQuestion_id().longValue();
        }
        Intent intent = new Intent(this.mContext, (Class<?>) YearQuestionListActivity.class);
        intent.putExtra("list", jArr);
        intent.putExtra("year", this.mList.get(i2).getYear());
        intent.putExtra("title", this.type);
        intent.putExtra("ISPractice", true);
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:28:0x007e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void setData() throws org.json.JSONException {
        /*
            Method dump skipped, instructions count: 526
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.psychiatrygarden.activity.YearFWNActivity.setData():void");
    }

    public void clearnDialog() {
        final CustomDialog customDialog = new CustomDialog(this.mContext, 2);
        customDialog.setCanceledOnTouchOutside(false);
        customDialog.setMessage("是否确定重做所有的错题");
        customDialog.setNegativeBtn(R.string.cancel, new View.OnClickListener() { // from class: com.psychiatrygarden.activity.lr
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                YearFWNActivity.lambda$clearnDialog$0(customDialog, view);
            }
        });
        customDialog.setPositiveBtn(R.string.ok, new View.OnClickListener() { // from class: com.psychiatrygarden.activity.mr
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) throws SQLException {
                YearFWNActivity.lambda$clearnDialog$1(customDialog, view);
            }
        });
        customDialog.show();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.gv_year = (GridView) findViewById(R.id.gv_year);
        new ProgressBarAsyncTask().execute(1000);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!getIntent().getStringExtra("type").equals("note") && !getIntent().getStringExtra("type").equals("error") && !getIntent().getStringExtra("type").equals("collect")) {
            this.mBtnActionbarRight.setVisibility(8);
            return;
        }
        this.mBtnActionbarRight.setVisibility(0);
        this.mBtnActionbarRight.setText("导出");
        if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
            this.mBtnActionbarRight.setTextColor(ContextCompat.getColor(this, R.color.white));
        } else {
            this.mBtnActionbarRight.setTextColor(ContextCompat.getColor(this, R.color.app_title_color_night));
        }
    }

    public void onEventMainThread(ComYearFWNItemBean mComSubFWNItemBean) {
        if (mComSubFWNItemBean.getmEventStr().equals("YearQuestionClearn") || mComSubFWNItemBean.getmEventStr().equals("YearQuestionClearnEdit")) {
            ArrayList<QuestionYearBean> arrayList = this.mList;
            if (arrayList != null && arrayList.size() > 0) {
                for (int i2 = 0; i2 < this.mList.size(); i2++) {
                    if (this.mList.get(i2).getYear().equals(mComSubFWNItemBean.getYear())) {
                        try {
                            List<QuestionInfoBean> topics = this.mList.get(i2).getTopics();
                            long[] long_questionitems = mComSubFWNItemBean.getLong_questionitems();
                            this.mList.get(i2).setTotal((Integer.parseInt(this.mList.get(i2).getTotal()) - (Integer.parseInt(topics.size() + "") - long_questionitems.length)) + "");
                            if (long_questionitems.length > 0) {
                                ArrayList arrayList2 = new ArrayList();
                                for (long j2 : long_questionitems) {
                                    QuestionInfoBean questionInfoBean = new QuestionInfoBean();
                                    questionInfoBean.setQuestion_id(Long.valueOf(j2));
                                    arrayList2.add(questionInfoBean);
                                }
                                this.mList.get(i2).setTopics(arrayList2);
                            } else {
                                ArrayList<QuestionYearBean> arrayList3 = this.mList;
                                arrayList3.remove(arrayList3.get(i2));
                            }
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }
                    }
                }
            }
            this.mAdapter.setListData(this.mList);
            if (this.mList.size() > 0) {
                findViewById(R.id.tv_empty).setVisibility(8);
            } else {
                findViewById(R.id.tv_empty).setVisibility(0);
            }
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        String stringExtra = getIntent().getStringExtra("type");
        this.type = stringExtra;
        stringExtra.hashCode();
        switch (stringExtra) {
            case "note":
                setTitle("我的笔记");
                break;
            case "error":
                setTitle("我的错题");
                break;
            case "collect":
                setTitle("我的收藏");
                break;
        }
        setContentView(R.layout.activity_year_fwn);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.mBtnActionbarRight.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.jr
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12570c.lambda$setListenerForWidget$2(view);
            }
        });
        this.gv_year.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.psychiatrygarden.activity.kr
            @Override // android.widget.AdapterView.OnItemClickListener
            public final void onItemClick(AdapterView adapterView, View view, int i2, long j2) {
                this.f12646c.lambda$setListenerForWidget$3(adapterView, view, i2, j2);
            }
        });
    }
}
