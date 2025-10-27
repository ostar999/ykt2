package com.psychiatrygarden.activity;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.google.android.material.appbar.AppBarLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.answer.AnswerDetailActivity;
import com.psychiatrygarden.adapter.QuestionListAdapter;
import com.psychiatrygarden.bean.AnsweredQuestionBean;
import com.psychiatrygarden.bean.AnsweredQuestionBeanDao;
import com.psychiatrygarden.bean.ComBackEventBean;
import com.psychiatrygarden.bean.ComYearFWNItemBean;
import com.psychiatrygarden.bean.FavoritesBean;
import com.psychiatrygarden.bean.FavoritesBeanDao;
import com.psychiatrygarden.bean.NotesBean;
import com.psychiatrygarden.bean.NotesBeanDao;
import com.psychiatrygarden.bean.QuestionInfoBean;
import com.psychiatrygarden.bean.QuestionInfoBeanDao;
import com.psychiatrygarden.bean.QuestionLocalBean;
import com.psychiatrygarden.bean.SubmitAnsweredQuestionBean;
import com.psychiatrygarden.bean.SubmitFavoritesBean;
import com.psychiatrygarden.bean.SubmitNotesBean;
import com.psychiatrygarden.bean.WrongBeanDao;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.LogUtils;
import com.psychiatrygarden.utils.NewToast;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.ToastUtil;
import com.psychiatrygarden.widget.CustomDialog;
import com.psychiatrygarden.widget.glideUtil.util.Utils;
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
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import net.tsz.afinal.core.Arrays;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class YearQuestionListActivity extends BaseActivity {
    private boolean ISPractice;
    private QuestionListAdapter adapter;
    private AppBarLayout appBarLayout;
    private RecyclerView gridView;
    private boolean isClickTrue;
    private long[] list_questionid;
    Handler mHandler;
    private RelativeLayout openrel;
    private TextView opentxt;
    BaseQuickAdapter<QuestionLocalBean.QLocalBean, BaseViewHolder> quickAdapter;
    private RecyclerView recycleview;
    private Double score;
    private Double scoretotal;
    String json_question_data = "";
    private List<QuestionLocalBean.QLocalBean.QCLocalBean> ids = new ArrayList();
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private boolean isDestroyed = false;

    public YearQuestionListActivity() {
        Double dValueOf = Double.valueOf(0.0d);
        this.score = dValueOf;
        this.scoretotal = dValueOf;
        this.mHandler = new Handler(new Handler.Callback() { // from class: com.psychiatrygarden.activity.bs
            @Override // android.os.Handler.Callback
            public final boolean handleMessage(Message message) {
                return this.f11130c.lambda$new$10(message);
            }
        });
    }

    private void getNoteDateForDelete(final String sbStr) {
        this.compositeDisposable.add(Observable.create(new ObservableOnSubscribe() { // from class: com.psychiatrygarden.activity.tr
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) throws Exception {
                YearQuestionListActivity.lambda$getNoteDateForDelete$4(sbStr, observableEmitter);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.psychiatrygarden.activity.ur
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f14003c.lambda$getNoteDateForDelete$5((Boolean) obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$changeBtnActionbarRightColor$2(int i2) {
        if (i2 == 1) {
            this.mBtnActionbarRight.setEnabled(false);
            this.mBtnActionbarRight.setClickable(false);
            if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
                this.mBtnActionbarRight.setTextColor(ContextCompat.getColor(this.mContext, R.color.pingeg));
                return;
            } else {
                this.mBtnActionbarRight.setTextColor(ContextCompat.getColor(this.mContext, R.color.jiucuo_night));
                return;
            }
        }
        this.mBtnActionbarRight.setEnabled(true);
        this.mBtnActionbarRight.setClickable(true);
        if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
            this.mBtnActionbarRight.setTextColor(ContextCompat.getColor(this.mContext, R.color.white));
        } else {
            this.mBtnActionbarRight.setTextColor(ContextCompat.getColor(this.mContext, R.color.app_title_color_night));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$clearnDialog$6(CustomDialog customDialog, View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        customDialog.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$clearnDialog$7(CustomDialog customDialog, View view) throws SQLException {
        ProjectApp.mDaoSession.clear();
        String string = Arrays.toString(this.list_questionid);
        if (!this.ISPractice) {
            ProjectApp.mDaoSession.getWrongBeanDao().queryBuilder().where(WrongBeanDao.Properties.Year.eq(getIntent().getStringExtra("year")), new WhereCondition[0]).buildDelete().executeDeleteWithoutDetachingEntities();
            this.mHandler.sendEmptyMessage(3);
        }
        getNoteDateForDelete(string);
        customDialog.dismissNoAnimaltion();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$getNoteDateForDelete$4(String str, ObservableEmitter observableEmitter) throws Exception {
        ProjectApp.mDaoSession.getDatabase().execSQL("delete from ANSWERED_QUESTION_BEAN where question_id in ( " + str.substring(1, str.length() - 1) + " );");
        observableEmitter.onNext(Boolean.TRUE);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getNoteDateForDelete$5(Boolean bool) throws Exception {
        setData(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$handInDialog$8(CustomDialog customDialog, View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        customDialog.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$handInDialog$9(CustomDialog customDialog, View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        customDialog.dismissNoAnimaltion();
        double dDoubleValue = (this.score.doubleValue() / this.scoretotal.doubleValue()) * 100.0d;
        DecimalFormat decimalFormat = new DecimalFormat("#0.0");
        ProjectApp.listSubmitData.clear();
        Intent intent = new Intent(this.mContext, (Class<?>) TestStatisticsActivity.class);
        intent.putExtra("year", getIntent().getStringExtra("year"));
        intent.putExtra("list", this.list_questionid);
        intent.putExtra("title", getIntent().getStringExtra("title"));
        intent.putExtra("score", "" + this.score);
        intent.putExtra("scoretotal", "" + this.scoretotal);
        intent.putExtra("defenlv", decimalFormat.format(dDoubleValue));
        if (TextUtils.isEmpty(getIntent().getStringExtra("unit"))) {
            intent.putExtra("unit", "");
        } else {
            intent.putExtra("unit", getIntent().getStringExtra("unit"));
        }
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$17(View view) {
        this.appBarLayout.setExpanded(true, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$18(View view) {
        List<AnsweredQuestionBean> list = ProjectApp.listSubmitData;
        if (list == null || list.size() <= 0) {
            finish();
        } else {
            handInDialog();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$19(AppBarLayout appBarLayout, int i2) {
        float f2 = i2 * 1.0f;
        this.recycleview.setAlpha(1.0f - Math.abs(f2 / appBarLayout.getTotalScrollRange()));
        if (1.0f - Math.abs(f2 / appBarLayout.getTotalScrollRange()) != 0.0f) {
            this.openrel.setVisibility(8);
        } else if (this.openrel.getVisibility() == 8) {
            shrink(300);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$mChangeData$20(ObservableEmitter observableEmitter) throws Exception {
        long[] jArr = this.list_questionid;
        ArrayList arrayList = new ArrayList();
        if (jArr.length > 0) {
            for (long j2 : jArr) {
                QuestionInfoBean questionInfoBeanLoadByRowId = ProjectApp.mTiKuDaoSession.getQuestionInfoBeanDao().loadByRowId(j2);
                if (getFilter(questionInfoBeanLoadByRowId)) {
                    arrayList.add(questionInfoBeanLoadByRowId);
                }
            }
        }
        long[] jArr2 = new long[arrayList.size()];
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            jArr2[i2] = ((QuestionInfoBean) arrayList.get(i2)).getQuestion_id().longValue();
        }
        observableEmitter.onNext(jArr2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$mChangeData$21(Disposable disposable) throws Exception {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$mChangeData$22(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        Intent intent = new Intent(this, (Class<?>) AnswerDetailActivity.class);
        intent.putExtra("position", i2);
        intent.putExtra("totalCount", "" + this.list_questionid.length);
        if (getIntent().getStringExtra("type") != null) {
            intent.putExtra("type", getIntent().getStringExtra("type"));
        }
        intent.putExtra("modletype", "year");
        intent.putExtra("year", getIntent().getStringExtra("year"));
        intent.putExtra("list", this.adapter.getList_questionid());
        intent.putExtra("title", getIntent().getStringExtra("title"));
        intent.putExtra("ISPractice", this.ISPractice);
        intent.putExtra("json_question_data", this.json_question_data);
        try {
            intent.putExtra("unit", getIntent().getStringExtra("unit"));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$mChangeData$23(long[] jArr) throws Exception {
        QuestionListAdapter questionListAdapter = this.adapter;
        if (questionListAdapter != null) {
            questionListAdapter.setListData(jArr);
        } else {
            QuestionListAdapter questionListAdapter2 = new QuestionListAdapter(this.mContext, jArr, "year", this.ISPractice, false, false);
            this.adapter = questionListAdapter2;
            this.gridView.setAdapter(questionListAdapter2);
        }
        this.adapter.setOnItemClickListener(new OnItemClickListener() { // from class: com.psychiatrygarden.activity.qr
            @Override // com.chad.library.adapter.base.listener.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
                this.f13751c.lambda$mChangeData$22(baseQuickAdapter, view, i2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$new$10(Message message) throws JSONException {
        int i2 = message.what;
        if (i2 == 0) {
            clearNote();
        } else if (i2 == 1) {
            clearCollection();
        } else if (i2 == 2) {
            clearAnswered(true);
        } else if (i2 == 3) {
            clearAnswered(false);
        } else if (i2 == 4) {
            submitData();
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setData$0(ObservableEmitter observableEmitter) throws Exception {
        observableEmitter.onNext(getDataList());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setData$1(boolean z2, long[] jArr) throws Exception {
        this.list_questionid = jArr;
        mChangeData(z2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$3(View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        clearnDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$submitAnswered$15(String str, String str2) throws SQLException {
        try {
            if (new JSONObject(str).optString("code").equals("200")) {
                ProjectApp.mDaoSession.getSubmitAnsweredQuestionBeanDao().deleteAll();
            }
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$submitAnswered$16(VolleyError volleyError, String str) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$submitFavorites$11(String str, String str2) throws SQLException {
        try {
            if (new JSONObject(str).optString("code").equals("200")) {
                ProjectApp.mDaoSession.getSubmitFavoritesBeanDao().deleteAll();
            }
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$submitFavorites$12(VolleyError volleyError, String str) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$submitNotes$13(String str, String str2) {
        try {
            if (new JSONObject(str).optString("code").equals("200")) {
                ProjectApp.mDaoSession.getSubmitNotesBeanDao().deleteAll();
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$submitNotes$14(VolleyError volleyError, String str) {
    }

    private void setData(final boolean isTrue) {
        this.compositeDisposable.add(Observable.create(new ObservableOnSubscribe() { // from class: com.psychiatrygarden.activity.cs
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) throws Exception {
                this.f12221a.lambda$setData$0(observableEmitter);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.psychiatrygarden.activity.ds
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f12261c.lambda$setData$1(isTrue, (long[]) obj);
            }
        }));
    }

    private void submitAnswered(List<SubmitAnsweredQuestionBean> list_SubmitAnsweredQuestionBean) throws JSONException {
        if (list_SubmitAnsweredQuestionBean.size() < SharePreferencesUtils.readIntConfig(CommonParameter.SUBMIT_ANSWER_LIMIT, this.mContext, 1)) {
            return;
        }
        JSONArray jSONArray = new JSONArray();
        for (int i2 = 0; i2 < list_SubmitAnsweredQuestionBean.size(); i2++) {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("answer", list_SubmitAnsweredQuestionBean.get(i2).getAnswer());
                jSONObject.put("question_id", list_SubmitAnsweredQuestionBean.get(i2).getQuestion_id());
                jSONObject.put("is_right", list_SubmitAnsweredQuestionBean.get(i2).getIs_right());
                jSONObject.put("app_id", list_SubmitAnsweredQuestionBean.get(i2).getApp_id());
                jSONArray.put(i2, jSONObject);
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
        HashMap map = new HashMap();
        map.put("user_id", UserConfig.getUserId());
        map.put("answer", jSONArray.toString());
        map.put("module_type", "1");
        YJYHttpUtils.post(this, NetworkRequestsURL.mPostAnsweredURL, map, new Response.Listener() { // from class: com.psychiatrygarden.activity.yr
            @Override // com.android.volley.Response.Listener
            public final void onResponse(Object obj, String str) throws SQLException {
                YearQuestionListActivity.lambda$submitAnswered$15((String) obj, str);
            }
        }, new Response.ErrorListener() { // from class: com.psychiatrygarden.activity.zr
            @Override // com.android.volley.Response.ErrorListener
            public final void onErrorResponse(VolleyError volleyError, String str) {
                YearQuestionListActivity.lambda$submitAnswered$16(volleyError, str);
            }
        });
    }

    private void submitFavorites(List<SubmitFavoritesBean> list_SubmitFavoritesBean) throws JSONException {
        JSONArray jSONArray = new JSONArray();
        for (int i2 = 0; i2 < list_SubmitFavoritesBean.size(); i2++) {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("question_id", list_SubmitFavoritesBean.get(i2).getQuestion_id());
                jSONObject.put("app_id", list_SubmitFavoritesBean.get(i2).getApp_id());
                jSONArray.put(i2, jSONObject);
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
        HashMap map = new HashMap();
        map.put("user_id", UserConfig.getUserId());
        map.put("collection", jSONArray.toString());
        map.put("module_type", "1");
        YJYHttpUtils.post(this, NetworkRequestsURL.mPostCollectionURL, map, new Response.Listener() { // from class: com.psychiatrygarden.activity.ms
            @Override // com.android.volley.Response.Listener
            public final void onResponse(Object obj, String str) throws SQLException {
                YearQuestionListActivity.lambda$submitFavorites$11((String) obj, str);
            }
        }, new Response.ErrorListener() { // from class: com.psychiatrygarden.activity.ns
            @Override // com.android.volley.Response.ErrorListener
            public final void onErrorResponse(VolleyError volleyError, String str) {
                YearQuestionListActivity.lambda$submitFavorites$12(volleyError, str);
            }
        });
    }

    private void submitNotes(List<SubmitNotesBean> list_SubmitNotesBean) throws JSONException {
        JSONArray jSONArray = new JSONArray();
        for (int i2 = 0; i2 < list_SubmitNotesBean.size(); i2++) {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("question_id", list_SubmitNotesBean.get(i2).getQuestion_id());
                jSONObject.put("content", list_SubmitNotesBean.get(i2).getContent());
                jSONObject.put("app_id", list_SubmitNotesBean.get(i2).getApp_id());
                jSONArray.put(i2, jSONObject);
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
        HashMap map = new HashMap();
        map.put("user_id", UserConfig.getUserId());
        map.put("note", jSONArray.toString());
        map.put("module_type", "1");
        YJYHttpUtils.post(this, NetworkRequestsURL.mPostNoteURL, map, new Response.Listener() { // from class: com.psychiatrygarden.activity.es
            @Override // com.android.volley.Response.Listener
            public final void onResponse(Object obj, String str) {
                YearQuestionListActivity.lambda$submitNotes$13((String) obj, str);
            }
        }, new Response.ErrorListener() { // from class: com.psychiatrygarden.activity.fs
            @Override // com.android.volley.Response.ErrorListener
            public final void onErrorResponse(VolleyError volleyError, String str) {
                YearQuestionListActivity.lambda$submitNotes$14(volleyError, str);
            }
        });
    }

    public void changeBtnActionbarRightColor(final int type) {
        runOnUiThread(new Runnable() { // from class: com.psychiatrygarden.activity.ls
            @Override // java.lang.Runnable
            public final void run() {
                this.f12679c.lambda$changeBtnActionbarRightColor$2(type);
            }
        });
    }

    public void clearAnswered(final boolean isFinish) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("type", "year");
        ajaxParams.put("year", getIntent().getStringExtra("year"));
        if (getIntent().getStringExtra("unit") != null && !"".equals(getIntent().getStringExtra("unit"))) {
            ajaxParams.put("unit", getIntent().getStringExtra("unit"));
        }
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.mClearAnsweredURL, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.YearQuestionListActivity.3
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
            }
        });
    }

    public void clearCollection() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("user_id", UserConfig.getUserId());
        ajaxParams.put("year", getIntent().getStringExtra("year"));
        try {
            ajaxParams.put("unit", getIntent().getStringExtra("unit"));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.mClearCollectionURL, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.YearQuestionListActivity.2
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                YearQuestionListActivity.this.hideProgressDialog();
                YearQuestionListActivity.this.finish();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String t2) {
                YearQuestionListActivity.this.finish();
            }
        });
    }

    public void clearNote() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("year", getIntent().getStringExtra("year"));
        try {
            ajaxParams.put("unit", getIntent().getStringExtra("unit"));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        ajaxParams.put("user_id", UserConfig.getUserId());
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.mClearNoteURL, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.YearQuestionListActivity.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                YearQuestionListActivity.this.hideProgressDialog();
                YearQuestionListActivity.this.finish();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String t2) {
                LogUtils.e(YearQuestionListActivity.this.TAG, t2);
                YearQuestionListActivity.this.finish();
            }
        });
    }

    public void clearnDialog() {
        boolean zEquals = TextUtils.equals("note", getIntent().getStringExtra("title"));
        boolean zEquals2 = TextUtils.equals("collect", getIntent().getStringExtra("title"));
        boolean zEquals3 = TextUtils.equals("error", getIntent().getStringExtra("title"));
        final CustomDialog customDialog = new CustomDialog(this.mContext, 2);
        customDialog.setCancelable(false);
        if (zEquals) {
            try {
                if (getIntent().getStringExtra("unit") != null) {
                    customDialog.setMessage("是否确定重做本年份单元下所有的笔记");
                } else {
                    customDialog.setMessage("是否确定重做本年份下所有的笔记");
                }
            } catch (Exception unused) {
                customDialog.setMessage("是否确定重做本年份下所有的笔记");
            }
        } else if (zEquals2) {
            try {
                if (getIntent().getStringExtra("unit") != null) {
                    customDialog.setMessage("是否确定重做本年份单元下所有收藏的题目");
                } else {
                    customDialog.setMessage("是否确定重做本年份下所有收藏的题目");
                }
            } catch (Exception unused2) {
                customDialog.setMessage("是否确定重做本年份下所有收藏的题目");
            }
        } else if (zEquals3) {
            try {
                if (getIntent().getStringExtra("unit") != null) {
                    customDialog.setMessage("是否确定重做本年份单元下所有的错题");
                } else {
                    customDialog.setMessage("是否确定重做本年份下所有的错题");
                }
            } catch (Exception unused3) {
                customDialog.setMessage("是否确定重做本年份下所有的错题");
            }
        } else {
            try {
                if (getIntent().getStringExtra("unit") != null) {
                    customDialog.setMessage("是否确定重做本年份单元下所有题");
                } else {
                    customDialog.setMessage("是否确定重做本年份下所有题");
                }
            } catch (Exception unused4) {
                customDialog.setMessage("是否确定重做本年份下所有题");
            }
            ProjectApp.listSubmitData.clear();
        }
        customDialog.setNegativeBtn(R.string.cancel, new View.OnClickListener() { // from class: com.psychiatrygarden.activity.rr
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                YearQuestionListActivity.lambda$clearnDialog$6(customDialog, view);
            }
        });
        customDialog.setPositiveBtn("确认", new View.OnClickListener() { // from class: com.psychiatrygarden.activity.sr
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) throws SQLException {
                this.f13932c.lambda$clearnDialog$7(customDialog, view);
            }
        });
        customDialog.show();
    }

    public long[] getDataList() {
        List<NotesBean> list;
        long[] longArrayExtra;
        List<FavoritesBean> list2;
        boolean zEquals = TextUtils.equals("note", getIntent().getStringExtra("title"));
        boolean zEquals2 = TextUtils.equals("collect", getIntent().getStringExtra("title"));
        boolean zEquals3 = TextUtils.equals("error", getIntent().getStringExtra("title"));
        if (zEquals) {
            try {
                list = getIntent().getStringExtra("unit") != null ? ProjectApp.mDaoSession.getNotesBeanDao().queryBuilder().where(NotesBeanDao.Properties.Year.eq(getIntent().getStringExtra("year")), NotesBeanDao.Properties.Unit.eq(getIntent().getStringExtra("unit"))).orderAsc(NotesBeanDao.Properties.Year_num).list() : ProjectApp.mDaoSession.getNotesBeanDao().queryBuilder().where(NotesBeanDao.Properties.Year.eq(getIntent().getStringExtra("year")), new WhereCondition[0]).orderAsc(NotesBeanDao.Properties.Year_num).list();
            } catch (Exception unused) {
                list = ProjectApp.mDaoSession.getNotesBeanDao().queryBuilder().where(NotesBeanDao.Properties.Year.eq(getIntent().getStringExtra("year")), new WhereCondition[0]).orderAsc(NotesBeanDao.Properties.Year_num).list();
            }
            longArrayExtra = new long[list.size()];
            for (int i2 = 0; i2 < list.size(); i2++) {
                longArrayExtra[i2] = list.get(i2).getQuestion_id().longValue();
            }
            changeBtnActionbarRightColor(0);
        } else if (zEquals2) {
            try {
                list2 = getIntent().getStringExtra("unit") != null ? ProjectApp.mDaoSession.getFavoritesBeanDao().queryBuilder().where(FavoritesBeanDao.Properties.Year.eq(getIntent().getStringExtra("year")), FavoritesBeanDao.Properties.Unit.eq(getIntent().getStringExtra("unit"))).orderAsc(FavoritesBeanDao.Properties.Year_num).list() : ProjectApp.mDaoSession.getFavoritesBeanDao().queryBuilder().where(FavoritesBeanDao.Properties.Year.eq(getIntent().getStringExtra("year")), new WhereCondition[0]).orderAsc(FavoritesBeanDao.Properties.Year_num).list();
            } catch (Exception unused2) {
                list2 = ProjectApp.mDaoSession.getFavoritesBeanDao().queryBuilder().where(FavoritesBeanDao.Properties.Year.eq(getIntent().getStringExtra("year")), new WhereCondition[0]).orderAsc(FavoritesBeanDao.Properties.Year_num).list();
            }
            longArrayExtra = new long[list2.size()];
            for (int i3 = 0; i3 < list2.size(); i3++) {
                longArrayExtra[i3] = list2.get(i3).getQuestion_id().longValue();
            }
            changeBtnActionbarRightColor(0);
        } else if (zEquals3) {
            longArrayExtra = getIntent().getLongArrayExtra("list");
            if ((SharePreferencesUtils.readStrConfig(CommonParameter.Student_Type, this.mContext).equals("z") ? ProjectApp.mDaoSession.getAnsweredQuestionBeanDao().queryBuilder().where(AnsweredQuestionBeanDao.Properties.Unit.eq(getIntent().getExtras().getString("unit")), AnsweredQuestionBeanDao.Properties.Year.eq(getIntent().getStringExtra("year")), AnsweredQuestionBeanDao.Properties.Is_right.eq(0)).count() : ProjectApp.mDaoSession.getAnsweredQuestionBeanDao().queryBuilder().where(AnsweredQuestionBeanDao.Properties.Year.eq(getIntent().getStringExtra("year")), AnsweredQuestionBeanDao.Properties.Is_right.eq(0)).count()) > 0) {
                changeBtnActionbarRightColor(0);
            } else {
                changeBtnActionbarRightColor(1);
            }
        } else {
            longArrayExtra = getIntent().getLongArrayExtra("list");
            if ((SharePreferencesUtils.readStrConfig(CommonParameter.Student_Type, this.mContext).equals("z") ? ProjectApp.mDaoSession.getAnsweredQuestionBeanDao().queryBuilder().where(AnsweredQuestionBeanDao.Properties.Unit.eq(getIntent().getExtras().getString("unit")), AnsweredQuestionBeanDao.Properties.Year.eq(getIntent().getStringExtra("year"))).count() : ProjectApp.mDaoSession.getAnsweredQuestionBeanDao().queryBuilder().where(AnsweredQuestionBeanDao.Properties.Year.eq(getIntent().getStringExtra("year")), new WhereCondition[0]).count()) > 0) {
                changeBtnActionbarRightColor(0);
            } else {
                changeBtnActionbarRightColor(1);
            }
        }
        return longArrayExtra;
    }

    public boolean getFilter(QuestionInfoBean questionInfoBean) {
        for (QuestionLocalBean.QLocalBean.QCLocalBean qCLocalBean : this.ids) {
            if (!SharePreferencesUtils.readStrConfig(CommonParameter.Student_Type, ProjectApp.instance(), "").equals("z") && "question".equals(qCLocalBean.getSource()) && !"-1".equals(qCLocalBean.getType()) && !getQuestionFilter(qCLocalBean.getType(), questionInfoBean)) {
                return false;
            }
            if ("modeltype".equals(qCLocalBean.getSource()) && !"-1".equals(qCLocalBean.getType()) && !getModelTypeFilter(qCLocalBean.getType(), questionInfoBean)) {
                return false;
            }
        }
        return true;
    }

    public boolean getModelTypeFilter(String type, QuestionInfoBean questionInfoBean) {
        return "0".equals(type) ? questionInfoBean.getIsgaopinkaodian().equals("1") : "1".equals(type) ? questionInfoBean.getIs_hard().equals("1") : !"2".equals(type) || questionInfoBean.getIs_example().equals("1");
    }

    public boolean getQuestionFilter(String type, QuestionInfoBean questionInfoBean) {
        String type2 = questionInfoBean.getType();
        if ("0".equals(type)) {
            return type2.equals("1");
        }
        if ("1".equals(type)) {
            return type2.equals("2");
        }
        if ("2".equals(type)) {
            return type2.equals("1") || type2.equals("2");
        }
        if ("3".equals(type)) {
            return (type2.equals("1") || type2.equals("2")) ? false : true;
        }
        return true;
    }

    public void handInDialog() {
        long jCount;
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
            List<AnsweredQuestionBean> list = queryBuilder.where(property.eq(getIntent().getStringExtra("year")), new WhereCondition[0]).list();
            jCount = ProjectApp.mDaoSession.getAnsweredQuestionBeanDao().queryBuilder().where(property.eq(getIntent().getStringExtra("year")), new WhereCondition[0]).count();
            List<QuestionInfoBean> list2 = ProjectApp.mTiKuDaoSession.getQuestionInfoBeanDao().queryBuilder().where(QuestionInfoBeanDao.Properties.Year.eq(getIntent().getStringExtra("year")), new WhereCondition[0]).list();
            for (int i2 = 0; i2 < list2.size(); i2++) {
                if (Integer.parseInt(getIntent().getStringExtra("year")) > 2016) {
                    if (list2.get(i2).getNumber_number().longValue() <= 40) {
                        this.scoretotal = Double.valueOf(this.scoretotal.doubleValue() + 1.5d);
                    } else if (list2.get(i2).getNumber_number().longValue() <= 115) {
                        this.scoretotal = Double.valueOf(this.scoretotal.doubleValue() + 2.0d);
                    } else if (list2.get(i2).getNumber_number().longValue() <= 135) {
                        this.scoretotal = Double.valueOf(this.scoretotal.doubleValue() + 1.5d);
                    } else if (list2.get(i2).getNumber_number().longValue() <= 165) {
                        this.scoretotal = Double.valueOf(this.scoretotal.doubleValue() + 2.0d);
                    }
                } else if (list2.get(i2).getNumber_number().longValue() <= 90) {
                    this.scoretotal = Double.valueOf(this.scoretotal.doubleValue() + 1.5d);
                } else if (list2.get(i2).getNumber_number().longValue() <= 120) {
                    this.scoretotal = Double.valueOf(this.scoretotal.doubleValue() + 2.0d);
                } else if (list2.get(i2).getNumber_number().longValue() <= 150) {
                    this.scoretotal = Double.valueOf(this.scoretotal.doubleValue() + 1.5d);
                } else if (list2.get(i2).getNumber_number().longValue() <= 180) {
                    this.scoretotal = Double.valueOf(this.scoretotal.doubleValue() + 2.0d);
                }
            }
            for (int i3 = 0; i3 < list.size(); i3++) {
                if (Integer.parseInt(getIntent().getStringExtra("year")) > 2016) {
                    if (list.get(i3).getYear_num().longValue() <= 40) {
                        if (list.get(i3).getIs_right().equals("1")) {
                            this.score = Double.valueOf(this.score.doubleValue() + 1.5d);
                        }
                    } else if (list.get(i3).getYear_num().longValue() <= 115) {
                        if (list.get(i3).getIs_right().equals("1")) {
                            this.score = Double.valueOf(this.score.doubleValue() + 2.0d);
                        }
                    } else if (list.get(i3).getYear_num().longValue() <= 135) {
                        if (list.get(i3).getIs_right().equals("1")) {
                            this.score = Double.valueOf(this.score.doubleValue() + 1.5d);
                        }
                    } else if (list.get(i3).getYear_num().longValue() <= 165 && list.get(i3).getIs_right().equals("1")) {
                        this.score = Double.valueOf(this.score.doubleValue() + 2.0d);
                    }
                } else if (list.get(i3).getYear_num().longValue() <= 90) {
                    if (list.get(i3).getIs_right().equals("1")) {
                        this.score = Double.valueOf(this.score.doubleValue() + 1.5d);
                    }
                } else if (list.get(i3).getYear_num().longValue() <= 120) {
                    if (list.get(i3).getIs_right().equals("1")) {
                        this.score = Double.valueOf(this.score.doubleValue() + 2.0d);
                    }
                } else if (list.get(i3).getYear_num().longValue() <= 150) {
                    if (list.get(i3).getIs_right().equals("1")) {
                        this.score = Double.valueOf(this.score.doubleValue() + 1.5d);
                    }
                } else if (list.get(i3).getYear_num().longValue() <= 180 && list.get(i3).getIs_right().equals("1")) {
                    this.score = Double.valueOf(this.score.doubleValue() + 2.0d);
                }
            }
        } else {
            this.score = dValueOf;
            this.scoretotal = dValueOf;
            if (getIntent().getStringExtra("unit") == null) {
                QueryBuilder<AnsweredQuestionBean> queryBuilder2 = ProjectApp.mDaoSession.getAnsweredQuestionBeanDao().queryBuilder();
                Property property2 = AnsweredQuestionBeanDao.Properties.Year;
                long jCount2 = queryBuilder2.where(property2.eq(getIntent().getStringExtra("year")), new WhereCondition[0]).count();
                List<AnsweredQuestionBean> list3 = ProjectApp.mDaoSession.getAnsweredQuestionBeanDao().queryBuilder().where(property2.eq(getIntent().getStringExtra("year")), new WhereCondition[0]).list();
                List<QuestionInfoBean> list4 = ProjectApp.mTiKuDaoSession.getQuestionInfoBeanDao().queryBuilder().where(QuestionInfoBeanDao.Properties.Year.eq(getIntent().getStringExtra("year")), new WhereCondition[0]).list();
                for (int i4 = 0; i4 < list4.size(); i4++) {
                    long jLongValue = list4.get(i4).getNumber_number().longValue();
                    if (Integer.parseInt(getIntent().getStringExtra("year")) > 2016) {
                        if (jLongValue <= 36) {
                            this.scoretotal = Double.valueOf(this.scoretotal.doubleValue() + 1.5d);
                        } else if (jLongValue <= 81) {
                            this.scoretotal = Double.valueOf(this.scoretotal.doubleValue() + 2.0d);
                        } else if (jLongValue <= 105) {
                            this.scoretotal = Double.valueOf(this.scoretotal.doubleValue() + 1.5d);
                        } else {
                            this.scoretotal = Double.valueOf(this.scoretotal.doubleValue() + 2.0d);
                        }
                    } else if (Integer.parseInt(getIntent().getStringExtra("year")) > 2007) {
                        if (jLongValue <= 80) {
                            this.scoretotal = Double.valueOf(this.scoretotal.doubleValue() + 1.5d);
                        } else if (jLongValue <= 120) {
                            this.scoretotal = Double.valueOf(this.scoretotal.doubleValue() + 1.5d);
                        } else {
                            this.scoretotal = Double.valueOf(this.scoretotal.doubleValue() + 2.0d);
                        }
                    }
                }
                for (int i5 = 0; i5 < list3.size(); i5++) {
                    long jLongValue2 = list3.get(i5).getYear_num().longValue();
                    if (Integer.parseInt(getIntent().getStringExtra("year")) <= 2016) {
                        if (Integer.parseInt(getIntent().getStringExtra("year")) > 2007) {
                            if (jLongValue2 <= 80) {
                                if (list3.get(i5).getIs_right().equals("1")) {
                                    this.score = Double.valueOf(this.score.doubleValue() + 1.5d);
                                }
                            } else if (jLongValue2 <= 120) {
                                if (list3.get(i5).getIs_right().equals("1")) {
                                    this.score = Double.valueOf(this.score.doubleValue() + 1.5d);
                                }
                            } else if (list3.get(i5).getIs_right().equals("1")) {
                                this.score = Double.valueOf(this.score.doubleValue() + 2.0d);
                            }
                        }
                    } else if (jLongValue2 <= 36) {
                        if (list3.get(i5).getIs_right().equals("1")) {
                            this.score = Double.valueOf(this.score.doubleValue() + 1.5d);
                        }
                    } else if (jLongValue2 <= 81) {
                        if (list3.get(i5).getIs_right().equals("1")) {
                            this.score = Double.valueOf(this.score.doubleValue() + 2.0d);
                        }
                    } else if (jLongValue2 <= 105) {
                        if (list3.get(i5).getIs_right().equals("1")) {
                            this.score = Double.valueOf(this.score.doubleValue() + 1.5d);
                        }
                    } else if (list3.get(i5).getIs_right().equals("1")) {
                        this.score = Double.valueOf(this.score.doubleValue() + 2.0d);
                    }
                }
                jCount = jCount2;
            } else {
                this.score = dValueOf;
                this.scoretotal = dValueOf;
                QueryBuilder<AnsweredQuestionBean> queryBuilder3 = ProjectApp.mDaoSession.getAnsweredQuestionBeanDao().queryBuilder();
                Property property3 = AnsweredQuestionBeanDao.Properties.Year;
                jCount = queryBuilder3.where(property3.eq(getIntent().getStringExtra("year")), AnsweredQuestionBeanDao.Properties.Unit.eq(getIntent().getExtras().getString("unit"))).count();
                List<AnsweredQuestionBean> list5 = ProjectApp.mDaoSession.getAnsweredQuestionBeanDao().queryBuilder().where(property3.eq(getIntent().getStringExtra("year")), new WhereCondition[0]).list();
                List<QuestionInfoBean> list6 = ProjectApp.mTiKuDaoSession.getQuestionInfoBeanDao().queryBuilder().where(QuestionInfoBeanDao.Properties.Year.eq(getIntent().getStringExtra("year")), new WhereCondition[0]).list();
                for (int i6 = 0; i6 < list6.size(); i6++) {
                    this.scoretotal = Double.valueOf(this.scoretotal.doubleValue() + 1.0d);
                }
                for (int i7 = 0; i7 < list5.size(); i7++) {
                    if (list5.get(i7).getIs_right().equals("1")) {
                        this.score = Double.valueOf(this.score.doubleValue() + 1.0d);
                    }
                }
            }
        }
        long jCount3 = (getIntent().getExtras().getString("unit") == null ? ProjectApp.mTiKuDaoSession.getQuestionInfoBeanDao().queryBuilder().where(QuestionInfoBeanDao.Properties.Year.eq(getIntent().getStringExtra("year")), new WhereCondition[0]).count() : ProjectApp.mTiKuDaoSession.getQuestionInfoBeanDao().queryBuilder().where(QuestionInfoBeanDao.Properties.Year.eq(getIntent().getStringExtra("year")), QuestionInfoBeanDao.Properties.Unit.eq(getIntent().getExtras().getString("unit"))).count()) - jCount;
        if (jCount3 == 0) {
            customDialog.setMessage("确定要交卷吗？");
        } else {
            customDialog.setMessage("您还有" + jCount3 + "题没做，确定要交卷吗？");
        }
        customDialog.setNegativeBtn(R.string.cancel, new View.OnClickListener() { // from class: com.psychiatrygarden.activity.js
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                YearQuestionListActivity.lambda$handInDialog$8(customDialog, view);
            }
        });
        customDialog.setPositiveBtn(R.string.ok, new View.OnClickListener() { // from class: com.psychiatrygarden.activity.ks
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12647c.lambda$handInDialog$9(customDialog, view);
            }
        });
        customDialog.show();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.ISPractice = getIntent().getBooleanExtra("ISPractice", false);
        this.openrel = (RelativeLayout) findViewById(R.id.openrel);
        this.appBarLayout = (AppBarLayout) findViewById(R.id.appBarLayout);
        this.opentxt = (TextView) findViewById(R.id.opentxt);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycleview);
        this.recycleview = recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.recycleview.setNestedScrollingEnabled(false);
        this.recycleview.setHasFixedSize(false);
        this.openrel.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.vr
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f14132c.lambda$init$17(view);
            }
        });
        initQuestionType();
        this.mBtnActionbarBack.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.wr
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f14165c.lambda$init$18(view);
            }
        });
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.zhangjieTxt);
        if (TextUtils.isEmpty(getIntent().getStringExtra("unit"))) {
            relativeLayout.setVisibility(8);
        } else {
            relativeLayout.setVisibility(0);
        }
        TextView textView = (TextView) findViewById(R.id.questionList_tv_title);
        try {
            if (getIntent().getExtras().getString("unit").equals("U1")) {
                textView.setText("第一单元");
            } else if (getIntent().getExtras().getString("unit").equals("U2")) {
                textView.setText("第二单元");
            } else if (getIntent().getExtras().getString("unit").equals("U3")) {
                textView.setText("第三单元");
            } else {
                textView.setText("第四单元");
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        this.gridView = (RecyclerView) findViewById(R.id.questionList_GridView);
        this.gridView.setLayoutManager(new GridLayoutManager(this.mContext, 5));
        if (!SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, this.mContext).equals(Constants.VIA_REPORT_TYPE_SHARE_TO_QQ)) {
            setTitle(getIntent().getExtras().getString("year") + "年" + SharePreferencesUtils.readStrConfig(CommonParameter.app_title, this.mContext) + "真题");
        } else if (getIntent().getExtras().getString("year").equals("2017") || getIntent().getExtras().getString("year").equals("2018") || getIntent().getExtras().getString("year").equals("2019")) {
            setTitle(getIntent().getStringExtra("year") + "年临床医学综合能力真题");
        } else {
            setTitle(getIntent().getExtras().getString("year") + "年西医综合真题");
        }
        setData(true);
        this.appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() { // from class: com.psychiatrygarden.activity.xr
            @Override // com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener, com.google.android.material.appbar.AppBarLayout.BaseOnOffsetChangedListener
            public final void onOffsetChanged(AppBarLayout appBarLayout, int i2) {
                this.f14198a.lambda$init$19(appBarLayout, i2);
            }
        });
    }

    public void initQuestionType() {
        List<QuestionLocalBean.QLocalBean> listInitLocalData = QuestionLocalBean.getInstance().initLocalData(0, this.ISPractice);
        if (listInitLocalData.size() == 0) {
            this.appBarLayout.setVisibility(8);
            return;
        }
        String strConfig = SharePreferencesUtils.readStrConfig(CommonParameter.questionids, this, "");
        if (!"".equals(strConfig)) {
            this.ids = (List) new Gson().fromJson(strConfig, new TypeToken<List<QuestionLocalBean.QLocalBean.QCLocalBean>>() { // from class: com.psychiatrygarden.activity.YearQuestionListActivity.5
            }.getType());
        }
        for (int i2 = 0; i2 < listInitLocalData.size(); i2++) {
            listInitLocalData.get(i2).getQcLocalBeanList().get(0).setIsSelected(1);
        }
        List<QuestionLocalBean.QLocalBean.QCLocalBean> list = this.ids;
        if (list != null && list.size() > 0) {
            for (int i3 = 0; i3 < this.ids.size(); i3++) {
                int i4 = 0;
                while (true) {
                    if (i4 >= listInitLocalData.size()) {
                        break;
                    }
                    if (this.ids.get(i3).getSource().equals(listInitLocalData.get(i4).getType())) {
                        int i5 = 0;
                        while (true) {
                            if (i5 >= listInitLocalData.get(i4).getQcLocalBeanList().size()) {
                                break;
                            }
                            if (this.ids.get(i3).getType().equals(listInitLocalData.get(i4).getQcLocalBeanList().get(i5).getType())) {
                                listInitLocalData.get(i4).getQcLocalBeanList().get(0).setIsSelected(0);
                                listInitLocalData.get(i4).getQcLocalBeanList().get(i5).setIsSelected(1);
                                break;
                            }
                            i5++;
                        }
                    } else {
                        i4++;
                    }
                }
            }
        }
        BaseQuickAdapter<QuestionLocalBean.QLocalBean, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<QuestionLocalBean.QLocalBean, BaseViewHolder>(R.layout.activity_list_type_item, listInitLocalData) { // from class: com.psychiatrygarden.activity.YearQuestionListActivity.6

            /* renamed from: com.psychiatrygarden.activity.YearQuestionListActivity$6$1, reason: invalid class name */
            public class AnonymousClass1 extends BaseQuickAdapter<QuestionLocalBean.QLocalBean.QCLocalBean, BaseViewHolder> {
                final /* synthetic */ QuestionLocalBean.QLocalBean val$items;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public AnonymousClass1(int layoutResId, List data, final QuestionLocalBean.QLocalBean val$items) {
                    super(layoutResId, data);
                    this.val$items = val$items;
                }

                /* JADX INFO: Access modifiers changed from: private */
                public /* synthetic */ void lambda$convert$0(BaseViewHolder baseViewHolder, QuestionLocalBean.QLocalBean qLocalBean, QuestionLocalBean.QLocalBean.QCLocalBean qCLocalBean, View view) {
                    try {
                        if (baseViewHolder.getAdapterPosition() != -1 && qLocalBean.getQcLocalBeanList().size() > baseViewHolder.getAdapterPosition()) {
                            if (qLocalBean.getQcLocalBeanList().get(baseViewHolder.getAdapterPosition()).getIsSelected() == 1) {
                                ToastUtil.shortToast(YearQuestionListActivity.this.mContext, "已经选中");
                                return;
                            }
                            for (int i2 = 0; i2 < qLocalBean.getQcLocalBeanList().size(); i2++) {
                                qLocalBean.getQcLocalBeanList().get(i2).setIsSelected(0);
                            }
                            qLocalBean.getQcLocalBeanList().get(baseViewHolder.getAdapterPosition()).setIsSelected(1);
                            notifyDataSetChanged();
                            if (YearQuestionListActivity.this.isHaveData(qLocalBean.getType())) {
                                for (int i3 = 0; i3 < YearQuestionListActivity.this.ids.size(); i3++) {
                                    if (((QuestionLocalBean.QLocalBean.QCLocalBean) YearQuestionListActivity.this.ids.get(i3)).getSource().equals(qLocalBean.getType())) {
                                        ((QuestionLocalBean.QLocalBean.QCLocalBean) YearQuestionListActivity.this.ids.get(i3)).setText(qCLocalBean.getText());
                                        ((QuestionLocalBean.QLocalBean.QCLocalBean) YearQuestionListActivity.this.ids.get(i3)).setType(qCLocalBean.getType());
                                    }
                                }
                            } else {
                                QuestionLocalBean.QLocalBean.QCLocalBean qCLocalBean2 = new QuestionLocalBean.QLocalBean.QCLocalBean();
                                qCLocalBean2.setSource(qLocalBean.getType());
                                qCLocalBean2.setType(qCLocalBean.getType());
                                qCLocalBean2.setText(qCLocalBean.getText());
                                YearQuestionListActivity.this.ids.add(qCLocalBean2);
                            }
                            SharePreferencesUtils.writeStrConfig(CommonParameter.questionids, new Gson().toJson(YearQuestionListActivity.this.ids).toString(), YearQuestionListActivity.this);
                            YearQuestionListActivity.this.setOpenText();
                            if (!"model".equals(qLocalBean.getType())) {
                                YearQuestionListActivity.this.isClickTrue = true;
                                YearQuestionListActivity.this.mChangeData(false);
                                return;
                            }
                            if ("0".equals(qCLocalBean.getType())) {
                                ProjectApp.listSubmitData.clear();
                                SharePreferencesUtils.writeIntConfig(CommonParameter.ISCESHITIKU, 0, YearQuestionListActivity.this.mContext);
                            } else if ("1".equals(qCLocalBean.getType())) {
                                SharePreferencesUtils.writeIntConfig(CommonParameter.ISCESHITIKU, 1, YearQuestionListActivity.this.mContext);
                            } else if ("2".equals(qCLocalBean.getType())) {
                                ProjectApp.listSubmitData.clear();
                                SharePreferencesUtils.writeIntConfig(CommonParameter.ISCESHITIKU, 2, YearQuestionListActivity.this.mContext);
                            }
                            YearQuestionListActivity.this.adapter.notifyDataSetChanged();
                            return;
                        }
                        ToastUtil.shortToast(YearQuestionListActivity.this.mContext, "下标异常");
                    } catch (Exception unused) {
                        ToastUtil.shortToast(YearQuestionListActivity.this.mContext, "操作异常，请重新选择！");
                    }
                }

                @Override // com.chad.library.adapter.base.BaseQuickAdapter
                public void convert(@NonNull final BaseViewHolder helper, final QuestionLocalBean.QLocalBean.QCLocalBean item) {
                    TextView textView = (TextView) helper.getView(R.id.labeltext);
                    textView.setText(item.getText());
                    textView.setSelected(item.getIsSelected() == 1);
                    final QuestionLocalBean.QLocalBean qLocalBean = this.val$items;
                    textView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.os
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            this.f13515c.lambda$convert$0(helper, qLocalBean, item, view);
                        }
                    });
                }
            }

            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(@NonNull BaseViewHolder helpers, QuestionLocalBean.QLocalBean items) {
                helpers.setText(R.id.labeid, items.getText() + "：");
                RecyclerView recyclerView = (RecyclerView) helpers.getView(R.id.recychildview);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(YearQuestionListActivity.this);
                int i6 = 0;
                linearLayoutManager.setOrientation(0);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(new AnonymousClass1(R.layout.layout_question_type_item, items.getQcLocalBeanList(), items));
                int i7 = 0;
                while (true) {
                    if (i7 >= items.getQcLocalBeanList().size()) {
                        break;
                    }
                    if (items.getQcLocalBeanList().get(i7).getIsSelected() == 1) {
                        i6 = i7;
                        break;
                    }
                    i7++;
                }
                recyclerView.smoothScrollToPosition(i6);
            }
        };
        this.quickAdapter = baseQuickAdapter;
        this.recycleview.setAdapter(baseQuickAdapter);
    }

    public boolean isHaveData(String source) {
        if (this.ids.size() > 0) {
            for (int i2 = 0; i2 < this.ids.size(); i2++) {
                if (this.ids.get(i2).getSource().equals(source)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void mChangeData(boolean isTrue) {
        setOpenText();
        this.compositeDisposable.add(Observable.create(new ObservableOnSubscribe() { // from class: com.psychiatrygarden.activity.gs
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) throws Exception {
                this.f12458a.lambda$mChangeData$20(observableEmitter);
            }
        }).subscribeOn(Schedulers.io()).doOnSubscribe(new Consumer() { // from class: com.psychiatrygarden.activity.hs
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                YearQuestionListActivity.lambda$mChangeData$21((Disposable) obj);
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.psychiatrygarden.activity.is
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f12539c.lambda$mChangeData$23((long[]) obj);
            }
        }));
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            if (getIntent().getBooleanExtra("isChongzuo", false) || getIntent().getBooleanExtra("isUserComm", false)) {
                this.mBtnActionbarRight.setVisibility(8);
            } else {
                this.mBtnActionbarRight.setVisibility(0);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        this.mBtnActionbarRight.setText("重做");
        this.mHandler.sendEmptyMessage(5);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        onmDestry();
        if (this.compositeDisposable.isDisposed()) {
            return;
        }
        this.compositeDisposable.dispose();
    }

    public void onEventMainThread(ComBackEventBean mComBackEventBean) {
        setData(false);
    }

    @Override // androidx.appcompat.app.AppCompatActivity, android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode != 4 || event.getRepeatCount() != 0) {
            return false;
        }
        List<AnsweredQuestionBean> list = ProjectApp.listSubmitData;
        if (list == null || list.size() <= 0) {
            finish();
            return true;
        }
        handInDialog();
        return true;
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
        if (isFinishing()) {
            onmDestry();
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        this.mHandler.sendEmptyMessage(4);
        if (CommonUtil.isNetworkConnected(this.mContext)) {
            return;
        }
        long jCount = ProjectApp.mDaoSession.getSubmitAnsweredQuestionBeanDao().count();
        if (jCount > 0) {
            NewToast.showShort(this.mContext, "网络已经断开，您还有" + jCount + "题未提交", 0).show();
        }
    }

    public void onmDestry() {
        if (this.isDestroyed) {
            return;
        }
        this.isDestroyed = true;
        boolean zEquals = TextUtils.equals("note", getIntent().getStringExtra("title"));
        boolean zEquals2 = TextUtils.equals("collect", getIntent().getStringExtra("title"));
        boolean zEquals3 = TextUtils.equals("error", getIntent().getStringExtra("title"));
        if (zEquals || zEquals2 || zEquals3) {
            EventBus.getDefault().post(new ComYearFWNItemBean(getIntent().getExtras().getString("year"), getIntent().getExtras().getString("unit"), "YearQuestionClearn", this.list_questionid, getIntent().getStringExtra("title")));
        }
        EventBus.getDefault().post("QuestionYearFragment");
        if (this.isClickTrue) {
            EventBus.getDefault().post("QuestionSubjectFragmenData");
        } else {
            EventBus.getDefault().post("QuestionSubjectFragment");
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_questionlist);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.mBtnActionbarRight.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.as
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11090c.lambda$setListenerForWidget$3(view);
            }
        });
    }

    public void setOpenText() {
        List<QuestionLocalBean.QLocalBean.QCLocalBean> list = this.ids;
        if (list == null || list.size() <= 0 || this.opentxt == null) {
            return;
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (int i2 = 0; i2 < this.ids.size(); i2++) {
            if (!TextUtils.equals(this.ids.get(i2).getText(), "全部") && !"year".equals(this.ids.get(i2).getSource())) {
                stringBuffer.append(this.ids.get(i2).getText());
                stringBuffer.append("-");
            }
        }
        if (stringBuffer.toString().equals("")) {
            this.opentxt.setText("全部");
        } else {
            this.opentxt.setText(stringBuffer.toString().substring(0, stringBuffer.length() - 1));
        }
    }

    public void shrink(int duration) {
        this.openrel.setVisibility(0);
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(this.openrel, "translationY", Utils.dp2px(this, 40.0f), 0.0f);
        objectAnimatorOfFloat.setDuration(duration);
        objectAnimatorOfFloat.setInterpolator(new OvershootInterpolator());
        objectAnimatorOfFloat.addListener(new Animator.AnimatorListener() { // from class: com.psychiatrygarden.activity.YearQuestionListActivity.4
            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationRepeat(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
            }
        });
        objectAnimatorOfFloat.start();
    }

    public void submitData() throws JSONException {
        List<SubmitAnsweredQuestionBean> listLoadAll = ProjectApp.mDaoSession.getSubmitAnsweredQuestionBeanDao().loadAll();
        List<SubmitNotesBean> listLoadAll2 = ProjectApp.mDaoSession.getSubmitNotesBeanDao().loadAll();
        List<SubmitFavoritesBean> listLoadAll3 = ProjectApp.mDaoSession.getSubmitFavoritesBeanDao().loadAll();
        if (listLoadAll.size() > 0) {
            submitAnswered(listLoadAll);
        }
        if (listLoadAll2.size() > 0) {
            submitNotes(listLoadAll2);
        }
        if (listLoadAll3.size() > 0) {
            submitFavorites(listLoadAll3);
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
        if (str.equals("SubjectActivityThread")) {
            setData(false);
        }
    }
}
