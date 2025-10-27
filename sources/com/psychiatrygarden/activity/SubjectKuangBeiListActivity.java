package com.psychiatrygarden.activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import com.google.gson.Gson;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.bean.AnsweredQuestionBeanBei;
import com.psychiatrygarden.bean.AnsweredQuestionBeanBeiDao;
import com.psychiatrygarden.bean.QuestionKuangBeiInfoBean;
import com.psychiatrygarden.bean.SubFavDataBean;
import com.psychiatrygarden.bean.SubNotDataBeiBean;
import com.psychiatrygarden.bean.SubWorDataBean;
import com.psychiatrygarden.bean.SubmitAnsweredQuestionBeanBei;
import com.psychiatrygarden.bean.SubmitFavoritesBeanBei;
import com.psychiatrygarden.bean.SubmitNotesBeanBei;
import com.psychiatrygarden.bean.WrongBeanBei;
import com.psychiatrygarden.bean.WrongBeanBeiDao;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.LogUtils;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.widget.CustomDialog;
import com.yikaobang.yixue.R;
import de.greenrobot.dao.query.WhereCondition;
import de.greenrobot.event.EventBus;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class SubjectKuangBeiListActivity extends BaseActivity {
    private static List<SubmitAnsweredQuestionBeanBei> list_SubmitAnsweredQuestionBeanBei;
    private static List<SubmitFavoritesBeanBei> list_SubmitFavoritesBeanBei;
    private static List<SubmitNotesBeanBei> list_SubmitNotesBeanBei;
    private long[] list_questionid;
    private GridView lv_bei_list;
    private final List<QuestionKuangBeiInfoBean> list_QuestionKuangBeiInfoBean = new ArrayList();
    private final ContentAdapter mContentAdapter = new ContentAdapter();
    private final String json_question_data = "";
    private boolean isDestroyed = false;
    Handler mHandler = new Handler(new Handler.Callback() { // from class: com.psychiatrygarden.activity.en
        @Override // android.os.Handler.Callback
        public final boolean handleMessage(Message message) {
            return this.f12326c.lambda$new$5(message);
        }
    });

    public class ContentAdapter extends BaseAdapter {

        public class ViewHoder {
            private TextView question_bei_list_title;

            private ViewHoder() {
            }
        }

        private ContentAdapter() {
        }

        @Override // android.widget.Adapter
        public int getCount() {
            return SubjectKuangBeiListActivity.this.list_QuestionKuangBeiInfoBean.size();
        }

        @Override // android.widget.Adapter
        public Object getItem(int position) {
            return SubjectKuangBeiListActivity.this.list_QuestionKuangBeiInfoBean.get(position);
        }

        @Override // android.widget.Adapter
        public long getItemId(int position) {
            return position;
        }

        @Override // android.widget.Adapter
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHoder viewHoder;
            if (convertView == null) {
                convertView = LayoutInflater.from(SubjectKuangBeiListActivity.this.mContext).inflate(R.layout.adapter_questionlist_gridview, (ViewGroup) null);
                viewHoder = new ViewHoder();
                viewHoder.question_bei_list_title = (TextView) convertView.findViewById(R.id.questionList_item_tv);
                convertView.setTag(viewHoder);
            } else {
                viewHoder = (ViewHoder) convertView.getTag();
            }
            ProjectApp.mDaoSession.clear();
            AnsweredQuestionBeanBei answeredQuestionBeanBeiLoad = ProjectApp.mDaoSession.getAnsweredQuestionBeanBeiDao().load(((QuestionKuangBeiInfoBean) SubjectKuangBeiListActivity.this.list_QuestionKuangBeiInfoBean.get(position)).getRecite_id());
            if (answeredQuestionBeanBeiLoad == null) {
                if (SkinManager.getCurrentSkinType(SubjectKuangBeiListActivity.this.mContext) == 0) {
                    viewHoder.question_bei_list_title.setBackgroundResource(R.drawable.drawable_gray);
                    viewHoder.question_bei_list_title.setTextColor(ContextCompat.getColor(SubjectKuangBeiListActivity.this.mContext, R.color.gray_font));
                } else {
                    viewHoder.question_bei_list_title.setBackgroundResource(R.drawable.drawable_gray_night);
                    viewHoder.question_bei_list_title.setTextColor(ContextCompat.getColor(SubjectKuangBeiListActivity.this.mContext, R.color.font_com_night));
                }
            } else if (answeredQuestionBeanBeiLoad.getIs_right() == null) {
                if (SkinManager.getCurrentSkinType(SubjectKuangBeiListActivity.this.mContext) == 0) {
                    viewHoder.question_bei_list_title.setBackgroundResource(R.drawable.drawable_gray);
                    viewHoder.question_bei_list_title.setTextColor(ContextCompat.getColor(SubjectKuangBeiListActivity.this.mContext, R.color.gray_font));
                } else {
                    viewHoder.question_bei_list_title.setBackgroundResource(R.drawable.drawable_gray_night);
                    viewHoder.question_bei_list_title.setTextColor(ContextCompat.getColor(SubjectKuangBeiListActivity.this.mContext, R.color.font_com_night));
                }
            } else if (answeredQuestionBeanBeiLoad.getIs_right().equals("0")) {
                if (SkinManager.getCurrentSkinType(SubjectKuangBeiListActivity.this.mContext) == 0) {
                    viewHoder.question_bei_list_title.setBackgroundResource(R.drawable.drawable_quetionlist_red);
                } else {
                    viewHoder.question_bei_list_title.setBackgroundResource(R.drawable.drawable_red_night);
                }
                viewHoder.question_bei_list_title.setTextColor(ContextCompat.getColor(SubjectKuangBeiListActivity.this.mContext, R.color.white));
            } else {
                if (SkinManager.getCurrentSkinType(SubjectKuangBeiListActivity.this.mContext) == 0) {
                    viewHoder.question_bei_list_title.setBackgroundResource(R.drawable.drawable_quetionlist_green);
                } else {
                    viewHoder.question_bei_list_title.setBackgroundResource(R.drawable.drawable_green_night);
                }
                viewHoder.question_bei_list_title.setTextColor(ContextCompat.getColor(SubjectKuangBeiListActivity.this.mContext, R.color.white));
            }
            viewHoder.question_bei_list_title.invalidate();
            QuestionKuangBeiInfoBean questionKuangBeiInfoBean = (QuestionKuangBeiInfoBean) SubjectKuangBeiListActivity.this.list_QuestionKuangBeiInfoBean.get(position);
            viewHoder.question_bei_list_title.setText(questionKuangBeiInfoBean.getNumber() + "");
            int screenWidth = CommonUtil.getScreenWidth(SubjectKuangBeiListActivity.this.mContext);
            AbsListView.LayoutParams layoutParams = (AbsListView.LayoutParams) viewHoder.question_bei_list_title.getLayoutParams();
            if (layoutParams == null) {
                int i2 = screenWidth / 7;
                layoutParams = new AbsListView.LayoutParams(i2, i2);
            } else {
                int i3 = screenWidth / 7;
                layoutParams.width = i3;
                layoutParams.height = i3;
            }
            viewHoder.question_bei_list_title.setLayoutParams(layoutParams);
            return convertView;
        }
    }

    public static class MyThread extends Thread {
        WeakReference<SubjectKuangBeiListActivity> mWeak;

        public MyThread(SubjectKuangBeiListActivity mLogin) {
            this.mWeak = new WeakReference<>(mLogin);
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            super.run();
            WeakReference<SubjectKuangBeiListActivity> weakReference = this.mWeak;
            if (weakReference == null || weakReference.get() == null) {
                return;
            }
            List unused = SubjectKuangBeiListActivity.list_SubmitAnsweredQuestionBeanBei = ProjectApp.mDaoSession.getSubmitAnsweredQuestionBeanBeiDao().loadAll();
            List unused2 = SubjectKuangBeiListActivity.list_SubmitNotesBeanBei = ProjectApp.mDaoSession.getSubmitNotesBeanBeiDao().loadAll();
            List unused3 = SubjectKuangBeiListActivity.list_SubmitFavoritesBeanBei = ProjectApp.mDaoSession.getSubmitFavoritesBeanBeiDao().loadAll();
            if (SubjectKuangBeiListActivity.list_SubmitAnsweredQuestionBeanBei.size() > 0) {
                this.mWeak.get().subBeiWrongOrNotOrFavData(1);
            }
            if (SubjectKuangBeiListActivity.list_SubmitNotesBeanBei.size() > 0) {
                this.mWeak.get().subBeiWrongOrNotOrFavData(2);
            }
            if (SubjectKuangBeiListActivity.list_SubmitFavoritesBeanBei.size() > 0) {
                this.mWeak.get().subBeiWrongOrNotOrFavData(3);
            }
        }
    }

    public class ProgressBarAsyncTask extends AsyncTask<Integer, Integer, String> {
        public ProgressBarAsyncTask() {
        }

        @Override // android.os.AsyncTask
        public void onPreExecute() {
        }

        @Override // android.os.AsyncTask
        public void onProgressUpdate(Integer... values) {
        }

        @Override // android.os.AsyncTask
        public String doInBackground(Integer... params) {
            SubjectKuangBeiListActivity.this.list_QuestionKuangBeiInfoBean.clear();
            if (SubjectKuangBeiListActivity.this.getIntent().getExtras().getString("type").equals("error")) {
                List<WrongBeanBei> list = ProjectApp.mDaoSession.getWrongBeanBeiDao().queryBuilder().where(WrongBeanBeiDao.Properties.Chapter_id.eq(SubjectKuangBeiListActivity.this.getIntent().getStringExtra("chapter_id")), new WhereCondition[0]).orderAsc(WrongBeanBeiDao.Properties.Year_num).list();
                if (list == null || list.size() <= 0) {
                    SubjectKuangBeiListActivity.this.mHandler.sendEmptyMessage(101);
                } else {
                    SubjectKuangBeiListActivity.this.mHandler.sendEmptyMessage(100);
                }
            } else {
                try {
                    if (ProjectApp.mDaoSession.getAnsweredQuestionBeanBeiDao().queryBuilder().where(AnsweredQuestionBeanBeiDao.Properties.Chapter_id.eq(SubjectKuangBeiListActivity.this.getIntent().getStringExtra("chapter_id")), new WhereCondition[0]).count() > 0) {
                        SubjectKuangBeiListActivity.this.mHandler.sendEmptyMessage(100);
                    } else {
                        SubjectKuangBeiListActivity.this.mHandler.sendEmptyMessage(101);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
            SubjectKuangBeiListActivity subjectKuangBeiListActivity = SubjectKuangBeiListActivity.this;
            subjectKuangBeiListActivity.list_questionid = new long[subjectKuangBeiListActivity.list_QuestionKuangBeiInfoBean.size()];
            for (int i2 = 0; i2 < SubjectKuangBeiListActivity.this.list_questionid.length; i2++) {
                SubjectKuangBeiListActivity.this.list_questionid[i2] = ((QuestionKuangBeiInfoBean) SubjectKuangBeiListActivity.this.list_QuestionKuangBeiInfoBean.get(i2)).getRecite_id().longValue();
            }
            return null;
        }

        @Override // android.os.AsyncTask
        public void onPostExecute(String result) {
            SubjectKuangBeiListActivity.this.lv_bei_list.setAdapter((ListAdapter) SubjectKuangBeiListActivity.this.mContentAdapter);
        }
    }

    private void getNoteDateForDelete(String sql) throws SQLException {
        StringBuilder sb = new StringBuilder();
        try {
            Cursor cursorRawQuery = ProjectApp.mDaoSession.getDatabase().rawQuery(sql, new String[0]);
            if (cursorRawQuery != null) {
                try {
                    if (cursorRawQuery.moveToFirst()) {
                        do {
                            if (!TextUtils.isEmpty(cursorRawQuery.getString(0))) {
                                sb.append(cursorRawQuery.getString(0));
                                sb.append(",");
                            }
                        } while (cursorRawQuery.moveToNext());
                    }
                } finally {
                }
            }
            if (cursorRawQuery != null) {
                cursorRawQuery.close();
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        ProjectApp.mDaoSession.getDatabase().execSQL("delete from ANSWERED_QUESTION_BEAN_BEI where question_id in ( " + sb.substring(0, sb.toString().length() - 1) + " );");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$clearnDialog$3(CustomDialog customDialog, View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        customDialog.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$clearnDialog$4(CustomDialog customDialog, View view) throws SQLException {
        if (CommonUtil.isFastClick()) {
            return;
        }
        String stringExtra = getIntent().getStringExtra("chapter_id");
        customDialog.dismissNoAnimaltion();
        if (getIntent().getStringExtra("type").equals("note")) {
            getNoteDateForDelete("select question_id from NOTES_BEAN_BEI where chapter_id=" + stringExtra + com.alipay.sdk.util.h.f3376b);
        } else if (getIntent().getStringExtra("type").equals("collect")) {
            getNoteDateForDelete("select question_id from FAVORITES_BEAN_BEI where chapter_id=" + stringExtra + com.alipay.sdk.util.h.f3376b);
        } else if (getIntent().getStringExtra("type").equals("error")) {
            ProjectApp.mDaoSession.getAnsweredQuestionBeanBeiDao().queryBuilder().where(AnsweredQuestionBeanBeiDao.Properties.Chapter_id.eq(stringExtra), AnsweredQuestionBeanBeiDao.Properties.Is_right.eq("0")).buildDelete().executeDeleteWithoutDetachingEntities();
        } else {
            Message message = new Message();
            ProjectApp.mDaoSession.getAnsweredQuestionBeanBeiDao().queryBuilder().where(AnsweredQuestionBeanBeiDao.Properties.Chapter_id.eq(Long.valueOf(Long.parseLong(stringExtra))), new WhereCondition[0]).buildDelete().executeDeleteWithoutDetachingEntities();
            ProjectApp.mDaoSession.getWrongBeanBeiDao().queryBuilder().where(WrongBeanBeiDao.Properties.Chapter_id.eq(Long.valueOf(Long.parseLong(stringExtra))), new WhereCondition[0]).buildDelete().executeDeleteWithoutDetachingEntities();
            message.obj = "";
            message.what = 3;
            this.mHandler.sendMessage(message);
        }
        new ProgressBarAsyncTask().execute(new Integer[0]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$2(View view) {
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$new$5(Message message) {
        int i2 = message.what;
        if (i2 == 0) {
            clearNote();
        } else if (i2 == 1) {
            clearCollection();
        } else if (i2 == 3) {
            clearAnswered();
        } else if (i2 == 100) {
            this.mBtnActionbarRight.setEnabled(true);
            this.mBtnActionbarRight.setClickable(true);
            if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
                this.mBtnActionbarRight.setTextColor(ContextCompat.getColor(this, R.color.white));
            } else {
                this.mBtnActionbarRight.setTextColor(ContextCompat.getColor(this, R.color.app_title_color_night));
            }
        } else if (i2 == 101) {
            this.mBtnActionbarRight.setEnabled(false);
            this.mBtnActionbarRight.setClickable(false);
            if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
                this.mBtnActionbarRight.setTextColor(ContextCompat.getColor(this, R.color.pingeg));
            } else {
                this.mBtnActionbarRight.setTextColor(ContextCompat.getColor(this, R.color.jiucuo_night));
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$0(View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        clearnDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$1(AdapterView adapterView, View view, int i2, long j2) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        Intent intent = new Intent(this.mContext, (Class<?>) SubjectKuangBeiDetailActivity.class);
        intent.putExtra("list", this.list_questionid);
        intent.putExtra("position", i2);
        intent.putExtra("subject_name", getIntent().getStringExtra("subject_name"));
        intent.putExtra("chapter_name", getIntent().getStringExtra("chapter_name"));
        intent.putExtra("chapter_id", getIntent().getStringExtra("chapter_id"));
        intent.putExtra("json_question_data", "");
        intent.putExtra("ISPractice", true);
        startActivity(intent);
    }

    public void clearAnswered() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("user_id", UserConfig.getUserId());
        ajaxParams.put("module_type", "4");
        ajaxParams.put("chapter_id", getIntent().getStringExtra("chapter_id"));
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.mClearAnsweredURL, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.SubjectKuangBeiListActivity.4
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String t2) {
            }
        });
    }

    public void clearCollection() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("user_id", UserConfig.getUserId());
        ajaxParams.put("chapter_id", getIntent().getStringExtra("chapter_id"));
        ajaxParams.put("module_type", "4");
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.mClearCollectionURL, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.SubjectKuangBeiListActivity.3
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String t2) {
                LogUtils.e(SubjectKuangBeiListActivity.this.TAG, t2);
                SubjectKuangBeiListActivity.this.finish();
            }
        });
    }

    public void clearNote() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("chapter_id", getIntent().getStringExtra("chapter_id"));
        ajaxParams.put("module_type", "4");
        ajaxParams.put("user_id", UserConfig.getUserId());
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.mClearNoteURL, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.SubjectKuangBeiListActivity.2
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String t2) {
                LogUtils.e(SubjectKuangBeiListActivity.this.TAG, t2);
                SubjectKuangBeiListActivity.this.finish();
            }
        });
    }

    public void clearnDialog() {
        final CustomDialog customDialog = new CustomDialog(this.mContext, 2);
        customDialog.setCancelable(false);
        if (getIntent().getStringExtra("type").equals("note")) {
            customDialog.setMessage("是否确定重做本章节下所有的笔记");
        } else if (getIntent().getStringExtra("type").equals("collect")) {
            customDialog.setMessage("是否确定重做本章节下所有收藏的题目");
        } else if (getIntent().getStringExtra("type").equals("error")) {
            customDialog.setMessage("是否确定重做本章节下所有的错题");
        } else {
            customDialog.setMessage("是否确定重做章节下所有题");
        }
        customDialog.setNegativeBtn(R.string.cancel, new View.OnClickListener() { // from class: com.psychiatrygarden.activity.cn
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SubjectKuangBeiListActivity.lambda$clearnDialog$3(customDialog, view);
            }
        });
        customDialog.setPositiveBtn(R.string.ok, new View.OnClickListener() { // from class: com.psychiatrygarden.activity.dn
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) throws SQLException {
                this.f12252c.lambda$clearnDialog$4(customDialog, view);
            }
        });
        customDialog.show();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        TextView textView = (TextView) findViewById(R.id.questiondetails_tv_title);
        this.lv_bei_list = (GridView) findViewById(R.id.lv_bei_list);
        setTitle(getIntent().getStringExtra("subject_name"));
        textView.setText(getIntent().getStringExtra("chapter_name"));
        this.mBtnActionbarBack.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.fn
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12363c.lambda$init$2(view);
            }
        });
        new ProgressBarAsyncTask().execute(new Integer[0]);
    }

    public void mOnDestroy() {
        if (this.isDestroyed) {
            return;
        }
        this.isDestroyed = true;
        EventBus.getDefault().post("SubKuangisRefulf");
        EventBus.getDefault().post("SubjectQuestionClearn_bei");
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            if (getIntent().getBooleanExtra("isUserComm", false)) {
                this.mBtnActionbarRight.setVisibility(8);
            } else {
                this.mBtnActionbarRight.setVisibility(0);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        this.mBtnActionbarRight.setText("重做");
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        mOnDestroy();
        super.onDestroy();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
        if (!str.equals("SubKuangisRefulf1")) {
            return;
        }
        this.list_QuestionKuangBeiInfoBean.clear();
        int i2 = 0;
        if (getIntent().getExtras().getString("type").equals("error")) {
            List<WrongBeanBei> list = ProjectApp.mDaoSession.getWrongBeanBeiDao().queryBuilder().where(WrongBeanBeiDao.Properties.Chapter_id.eq(getIntent().getStringExtra("chapter_id")), new WhereCondition[0]).orderAsc(WrongBeanBeiDao.Properties.Year_num).list();
            if (list == null || list.size() <= 0) {
                this.mHandler.sendEmptyMessage(101);
            } else {
                this.mHandler.sendEmptyMessage(100);
            }
        } else {
            try {
                if (ProjectApp.mDaoSession.getAnsweredQuestionBeanBeiDao().queryBuilder().where(AnsweredQuestionBeanBeiDao.Properties.Chapter_id.eq(getIntent().getStringExtra("chapter_id")), new WhereCondition[0]).count() > 0) {
                    this.mHandler.sendEmptyMessage(100);
                } else {
                    this.mHandler.sendEmptyMessage(101);
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        this.list_questionid = new long[this.list_QuestionKuangBeiInfoBean.size()];
        while (true) {
            long[] jArr = this.list_questionid;
            if (i2 >= jArr.length) {
                this.mContentAdapter.notifyDataSetChanged();
                this.lv_bei_list.invalidate();
                return;
            } else {
                jArr[i2] = this.list_QuestionKuangBeiInfoBean.get(i2).getRecite_id().longValue();
                i2++;
            }
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        if (isFinishing()) {
            mOnDestroy();
        }
        super.onPause();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        new MyThread(this).start();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_kuangbei_list);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.mBtnActionbarRight.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.gn
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12452c.lambda$setListenerForWidget$0(view);
            }
        });
        this.lv_bei_list.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.psychiatrygarden.activity.hn
            @Override // android.widget.AdapterView.OnItemClickListener
            public final void onItemClick(AdapterView adapterView, View view, int i2, long j2) {
                this.f12484c.lambda$setListenerForWidget$1(adapterView, view, i2, j2);
            }
        });
    }

    public void subBeiWrongOrNotOrFavData(final int type) {
        String str;
        String str2;
        AjaxParams ajaxParams = new AjaxParams();
        int i2 = 0;
        if (type == 1) {
            str = NetworkRequestsURL.mPostAnsweredURL;
            ArrayList arrayList = new ArrayList();
            while (i2 < list_SubmitAnsweredQuestionBeanBei.size()) {
                SubWorDataBean subWorDataBean = new SubWorDataBean();
                subWorDataBean.setAnswer(list_SubmitAnsweredQuestionBeanBei.get(i2).getIs_right());
                subWorDataBean.setIs_right(list_SubmitAnsweredQuestionBeanBei.get(i2).getIs_right());
                subWorDataBean.setQuestion_id(list_SubmitAnsweredQuestionBeanBei.get(i2).getQuestion_id() + "");
                subWorDataBean.setApp_id(list_SubmitAnsweredQuestionBeanBei.get(i2).getApp_id());
                arrayList.add(subWorDataBean);
                i2++;
            }
            ajaxParams.put("answer", new Gson().toJson(arrayList));
        } else if (type == 2) {
            str = NetworkRequestsURL.mPostNoteURL;
            ArrayList arrayList2 = new ArrayList();
            while (i2 < list_SubmitNotesBeanBei.size()) {
                SubNotDataBeiBean subNotDataBeiBean = new SubNotDataBeiBean();
                subNotDataBeiBean.setApp_id(list_SubmitNotesBeanBei.get(i2).getApp_id() + "");
                subNotDataBeiBean.setQuestion_id(list_SubmitNotesBeanBei.get(i2).getQuestion_id() + "");
                subNotDataBeiBean.setContent(list_SubmitNotesBeanBei.get(i2).getContent());
                arrayList2.add(subNotDataBeiBean);
                i2++;
            }
            ajaxParams.put("note", new Gson().toJson(arrayList2));
        } else {
            if (type != 3) {
                str2 = null;
                ajaxParams.put("module_type", "4");
                YJYHttpUtils.post(this.mContext.getApplicationContext(), str2, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.SubjectKuangBeiListActivity.1
                    @Override // net.tsz.afinal.http.AjaxCallBack
                    public void onFailure(Throwable t2, int errorNo, String strMsg) {
                        super.onFailure(t2, errorNo, strMsg);
                    }

                    @Override // net.tsz.afinal.http.AjaxCallBack
                    public void onSuccess(String s2) {
                        super.onSuccess((AnonymousClass1) s2);
                        try {
                            if (new JSONObject(s2).optString("code").equals("200")) {
                                int i3 = type;
                                if (i3 == 1) {
                                    ProjectApp.mDaoSession.getSubmitAnsweredQuestionBeanBeiDao().deleteAll();
                                } else if (i3 == 2) {
                                    ProjectApp.mDaoSession.getSubmitNotesBeanBeiDao().deleteAll();
                                } else if (i3 == 3) {
                                    ProjectApp.mDaoSession.getSubmitFavoritesBeanBeiDao().deleteAll();
                                }
                            }
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }
                    }
                });
            }
            str = NetworkRequestsURL.mPostCollectionURL;
            ArrayList arrayList3 = new ArrayList();
            while (i2 < list_SubmitFavoritesBeanBei.size()) {
                SubFavDataBean subFavDataBean = new SubFavDataBean();
                subFavDataBean.setApp_id(list_SubmitFavoritesBeanBei.get(i2).getApp_id());
                subFavDataBean.setQuestion_id(list_SubmitFavoritesBeanBei.get(i2).getQuestion_id() + "");
                arrayList3.add(subFavDataBean);
                i2++;
            }
            ajaxParams.put("collection", new Gson().toJson(arrayList3));
        }
        str2 = str;
        ajaxParams.put("module_type", "4");
        YJYHttpUtils.post(this.mContext.getApplicationContext(), str2, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.SubjectKuangBeiListActivity.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass1) s2);
                try {
                    if (new JSONObject(s2).optString("code").equals("200")) {
                        int i3 = type;
                        if (i3 == 1) {
                            ProjectApp.mDaoSession.getSubmitAnsweredQuestionBeanBeiDao().deleteAll();
                        } else if (i3 == 2) {
                            ProjectApp.mDaoSession.getSubmitNotesBeanBeiDao().deleteAll();
                        } else if (i3 == 3) {
                            ProjectApp.mDaoSession.getSubmitFavoritesBeanBeiDao().deleteAll();
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }
}
