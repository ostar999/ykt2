package com.psychiatrygarden.activity;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.TextAppearanceSpan;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cn.hutool.core.text.StrPool;
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
import com.psychiatrygarden.bean.ComSubFWNItemBean;
import com.psychiatrygarden.bean.FavoritesBean;
import com.psychiatrygarden.bean.FavoritesBeanDao;
import com.psychiatrygarden.bean.NotesBean;
import com.psychiatrygarden.bean.NotesBeanDao;
import com.psychiatrygarden.bean.QuestionInfoBean;
import com.psychiatrygarden.bean.QuestionLocalBean;
import com.psychiatrygarden.bean.SubmitAnsweredQuestionBean;
import com.psychiatrygarden.bean.SubmitFavoritesBean;
import com.psychiatrygarden.bean.SubmitNotesBean;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.NewToast;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.ToastUtil;
import com.psychiatrygarden.widget.CustomDialog;
import com.psychiatrygarden.widget.glideUtil.util.Utils;
import com.tencent.connect.common.Constants;
import com.umeng.socialize.net.dplus.CommonNetImpl;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.lang.ref.WeakReference;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class SubjectQuestionListActivity extends BaseActivity {
    private AppBarLayout appBarLayout;
    private RecyclerView gridView;
    private boolean isClickTrue;
    private long[] list_questionid;
    private RelativeLayout openrel;
    private TextView opentxt;
    private QuestionListAdapter qadapter;
    BaseQuickAdapter<QuestionLocalBean.QLocalBean, BaseViewHolder> quickAdapter;
    private RecyclerView recycleview;
    private boolean isDestroyed = false;
    private List<QuestionLocalBean.QLocalBean.QCLocalBean> ids = new ArrayList();

    public static class MyThread extends Thread {
        WeakReference<SubjectQuestionListActivity> mSubWeak;

        public MyThread(SubjectQuestionListActivity mSub) {
            this.mSubWeak = new WeakReference<>(mSub);
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() throws JSONException {
            super.run();
            WeakReference<SubjectQuestionListActivity> weakReference = this.mSubWeak;
            if (weakReference == null || weakReference.get() == null) {
                return;
            }
            this.mSubWeak.get().submitData();
        }
    }

    private void getNoteDateForDelete(final String sql, final int type, final String sqlwrong) {
        Observable.create(new ObservableOnSubscribe() { // from class: com.psychiatrygarden.activity.vn
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) throws Exception {
                SubjectQuestionListActivity.lambda$getNoteDateForDelete$5(type, sql, sqlwrong, observableEmitter);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.psychiatrygarden.activity.wn
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f14160c.lambda$getNoteDateForDelete$6((Boolean) obj);
            }
        });
    }

    private void getPingyuData(String zhengquelvValue, final TextView descr) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("scoring_rate", "" + zhengquelvValue);
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.mremarkUrl, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.SubjectQuestionListActivity.2
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass2) s2);
                try {
                    descr.setText(new JSONObject(s2).optJSONObject("data").optString("remark").toString());
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    private void getScoreNewData(String totalValue, String zuoguo, String rightValue, String wrongValue, String zhengquelvValue) {
        ColorStateList colorStateList;
        ColorStateList colorStateList2;
        String str;
        String string;
        SpannableStringBuilder spannableStringBuilder;
        TextView textView;
        final AlertDialog alertDialogCreate = new AlertDialog.Builder(this.mContext, R.style.MyDialog).create();
        alertDialogCreate.show();
        alertDialogCreate.setCanceledOnTouchOutside(false);
        Window window = alertDialogCreate.getWindow();
        window.setGravity(17);
        window.setContentView(R.layout.acticity_xuekeceping);
        WindowManager.LayoutParams attributes = alertDialogCreate.getWindow().getAttributes();
        attributes.height = CommonUtil.getScreenHeight(this.mContext);
        attributes.width = CommonUtil.getScreenWidth(this.mContext);
        alertDialogCreate.getWindow().setAttributes(attributes);
        TextView textView2 = (TextView) alertDialogCreate.findViewById(R.id.xuekeid);
        TextView textView3 = (TextView) alertDialogCreate.findViewById(R.id.chapter_id);
        TextView textView4 = (TextView) alertDialogCreate.findViewById(R.id.total);
        TextView textView5 = (TextView) alertDialogCreate.findViewById(R.id.right);
        TextView textView6 = (TextView) alertDialogCreate.findViewById(R.id.wrong);
        TextView textView7 = (TextView) alertDialogCreate.findViewById(R.id.zhengquelv);
        TextView textView8 = (TextView) alertDialogCreate.findViewById(R.id.descr);
        TextView textView9 = (TextView) alertDialogCreate.findViewById(R.id.zuoguo);
        if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
            colorStateList = ContextCompat.getColorStateList(this.mContext, R.color.app_theme_red);
            colorStateList2 = ContextCompat.getColorStateList(this.mContext, R.color.green);
        } else {
            colorStateList = ContextCompat.getColorStateList(this.mContext, R.color.red_theme_night);
            colorStateList2 = ContextCompat.getColorStateList(this.mContext, R.color.green_theme_night);
        }
        ColorStateList colorStateList3 = colorStateList2;
        ((ImageView) alertDialogCreate.findViewById(R.id.close)).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.nn
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                alertDialogCreate.dismiss();
            }
        });
        getPingyuData(zhengquelvValue + "", textView8);
        String str2 = "做 " + zuoguo + " 题";
        SpannableStringBuilder spannableStringBuilder2 = new SpannableStringBuilder(str2);
        spannableStringBuilder2.setSpan(new TextAppearanceSpan(null, 0, 0, colorStateList3, null), (str2.length() - 2) - zuoguo.length(), str2.length() - 1, 34);
        String str3 = "对 " + rightValue + " 题";
        SpannableStringBuilder spannableStringBuilder3 = new SpannableStringBuilder(str3);
        spannableStringBuilder3.setSpan(new TextAppearanceSpan(null, 0, 0, colorStateList3, null), (str3.length() - 2) - rightValue.length(), str3.length() - 1, 34);
        String str4 = "错 " + wrongValue + " 题";
        SpannableStringBuilder spannableStringBuilder4 = new SpannableStringBuilder(str4);
        spannableStringBuilder4.setSpan(new TextAppearanceSpan(null, 0, 0, colorStateList, null), (str4.length() - 2) - wrongValue.length(), str4.length() - 1, 34);
        if (Double.parseDouble(zhengquelvValue) >= 60.0d) {
            String str5 = "正确率 " + zhengquelvValue + " %";
            spannableStringBuilder = new SpannableStringBuilder(str5);
            spannableStringBuilder.setSpan(new TextAppearanceSpan(null, 0, 0, colorStateList3, null), (str5.length() - 2) - zhengquelvValue.length(), str5.length() - 1, 34);
            textView = textView9;
            str = "";
        } else {
            if (Double.parseDouble(zhengquelvValue) != 0.0d) {
                str = "";
                string = (100.0d - Double.parseDouble(zhengquelvValue)) + str;
            } else if (Integer.parseInt(wrongValue) == 0) {
                string = "0";
                str = "";
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append(100.0d - Double.parseDouble(zhengquelvValue));
                str = "";
                sb.append(str);
                string = sb.toString();
            }
            String str6 = "错误率 " + string + " %";
            spannableStringBuilder = new SpannableStringBuilder(str6);
            textView = textView9;
            spannableStringBuilder.setSpan(new TextAppearanceSpan(null, 0, 0, colorStateList, null), (str6.length() - 2) - string.length(), str6.length() - 1, 34);
        }
        textView2.setText(getIntent().getStringExtra("subject_name") + str);
        textView3.setText(getIntent().getStringExtra("chapter_name"));
        textView4.setText(String.format("共 %s 题", totalValue));
        textView5.setText(spannableStringBuilder3);
        textView6.setText(spannableStringBuilder4);
        textView7.setText(spannableStringBuilder);
        textView.setText(spannableStringBuilder2);
        textView8.setText("革命尚未成功，同志仍需努力！");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$clearnDialog$7(CustomDialog customDialog, View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        customDialog.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$clearnDialog$8(int i2, CustomDialog customDialog, View view) {
        ProjectApp.mDaoSession.clear();
        postRedoValue(i2);
        customDialog.dismissNoAnimaltion();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$getNoteDateForDelete$5(int i2, String str, String str2, ObservableEmitter observableEmitter) throws Exception {
        if (i2 == 0) {
            StringBuilder sb = new StringBuilder();
            try {
                Cursor cursorRawQuery = ProjectApp.mDaoSession.getDatabase().rawQuery(str, new String[0]);
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
            str = "delete from ANSWERED_QUESTION_BEAN where question_id in ( " + sb.substring(0, sb.toString().length() - 1) + " );";
        } else {
            ProjectApp.mDaoSession.getDatabase().execSQL(str2);
        }
        ProjectApp.mDaoSession.getDatabase().execSQL(str);
        observableEmitter.onNext(Boolean.TRUE);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getNoteDateForDelete$6(Boolean bool) throws Exception {
        if (!Constants.VIA_REPORT_TYPE_SET_AVATAR.equals(SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, this.mContext))) {
            if (getIntent().getBooleanExtra("isXitong", false) && !getIntent().getBooleanExtra("ISPractice", false)) {
                SharePreferencesUtils.writeIntConfig(CommonParameter.mrandomValue + getIntent().getStringExtra("chapter_id") + SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, ProjectApp.instance()), new Random().nextInt(100), this);
            }
            if (getIntent().getBooleanExtra("iszhongyibank", false) && !getIntent().getBooleanExtra("ISPractice", false)) {
                SharePreferencesUtils.writeIntConfig(CommonParameter.mrandomValue + getIntent().getStringExtra("chapter_id") + SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, ProjectApp.instance()), new Random().nextInt(100), this);
            }
        }
        setData(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$handInDialog$10(CustomDialog customDialog, int i2, int i3, double d3, DecimalFormat decimalFormat, double d4, View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        customDialog.dismissNoAnimaltion();
        ProjectApp.listSubmitData.clear();
        getScoreNewData(i2 + "", replacevalue(i3 + ""), replacevalue(d3 + ""), replacevalue((((double) i3) - d3) + ""), replacevalue(decimalFormat.format(d4)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$handInDialog$9(CustomDialog customDialog, View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        customDialog.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$13(View view) {
        this.appBarLayout.setExpanded(true, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$14(View view) {
        List<AnsweredQuestionBean> list = ProjectApp.listSubmitData;
        if (list == null || list.size() <= 0) {
            finish();
        } else {
            handInDialog(true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$15(AppBarLayout appBarLayout, int i2) {
        float f2 = i2 * 1.0f;
        this.recycleview.setAlpha(1.0f - Math.abs(f2 / appBarLayout.getTotalScrollRange()));
        if (1.0f - Math.abs(f2 / appBarLayout.getTotalScrollRange()) != 0.0f) {
            this.openrel.setVisibility(8);
        } else if (this.openrel.getVisibility() == 8) {
            shrink(300);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$mChangeData$16(ObservableEmitter observableEmitter) throws Exception {
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
    public static /* synthetic */ void lambda$mChangeData$17(Disposable disposable) throws Exception {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$mChangeData$18(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
        Intent intent = new Intent(this, (Class<?>) AnswerDetailActivity.class);
        intent.putExtra("modletype", "subject");
        intent.putExtra("position", i2);
        intent.putExtra("totalCount", "" + this.list_questionid.length);
        intent.putExtra("list", this.qadapter.getList_questionid());
        intent.putExtra("subject_name", getIntent().getStringExtra("subject_name") + "");
        intent.putExtra("IsLisnxi", getIntent().getBooleanExtra("IsLisnxi", false));
        intent.putExtra("chapter_name", getIntent().getStringExtra("chapter_name"));
        intent.putExtra("chapter_id", getIntent().getStringExtra("chapter_id"));
        intent.putExtra("title", getIntent().getStringExtra("title"));
        intent.putExtra("chapter_p_id", getIntent().getStringExtra("chapter_p_id"));
        intent.putExtra("ISPractice", getIntent().getBooleanExtra("ISPractice", false));
        intent.putExtra("isXitong", getIntent().getExtras().getBoolean("isXitong", false));
        try {
            intent.putExtra("iszhongyibank", getIntent().getBooleanExtra("iszhongyibank", false));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$mChangeData$19(long[] jArr) throws Exception {
        QuestionListAdapter questionListAdapter = this.qadapter;
        if (questionListAdapter != null) {
            questionListAdapter.setListData(jArr);
        } else {
            QuestionListAdapter questionListAdapter2 = new QuestionListAdapter(this.mContext, jArr, "subject", getIntent().getBooleanExtra("ISPractice", false), getIntent().getBooleanExtra("IsLisnxi", false), getIntent().getBooleanExtra("isXitong", false), getIntent().getStringExtra("chapter_id"));
            this.qadapter = questionListAdapter2;
            try {
                questionListAdapter2.setZHONGYI(getIntent().getBooleanExtra("iszhongyibank", false));
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            this.gridView.setAdapter(this.qadapter);
        }
        this.qadapter.setOnItemClickListener(new OnItemClickListener() { // from class: com.psychiatrygarden.activity.xn
            @Override // com.chad.library.adapter.base.listener.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
                this.f14194c.lambda$mChangeData$18(baseQuickAdapter, view, i2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$scoreDialog$12(CustomDialog customDialog, View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        customDialog.dismissNoAnimaltion();
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setData$2(ObservableEmitter observableEmitter) throws Exception {
        observableEmitter.onNext(getDataList());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setData$3(boolean z2, long[] jArr) throws Exception {
        this.list_questionid = jArr;
        mChangeData(z2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$4(View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        clearnDialog(0);
    }

    private void setData(final boolean isTrue) {
        Observable.create(new ObservableOnSubscribe() { // from class: com.psychiatrygarden.activity.in
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) throws Exception {
                this.f12522a.lambda$setData$2(observableEmitter);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.psychiatrygarden.activity.tn
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f13962c.lambda$setData$3(isTrue, (long[]) obj);
            }
        });
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
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("user_id", UserConfig.getUserId());
        ajaxParams.put("answer", jSONArray.toString());
        ajaxParams.put("module_type", "1");
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.mPostAnsweredURL, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.SubjectQuestionListActivity.6
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass6) s2);
                try {
                    try {
                        if (new JSONObject(s2).optString("code").equals("200")) {
                            ProjectApp.mDaoSession.getSubmitAnsweredQuestionBeanDao().deleteAll();
                        }
                    } catch (JSONException e3) {
                        e3.printStackTrace();
                    }
                } catch (Exception e4) {
                    e4.printStackTrace();
                }
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
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("user_id", UserConfig.getUserId());
        ajaxParams.put("collection", jSONArray.toString());
        ajaxParams.put("module_type", "1");
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.mPostCollectionURL, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.SubjectQuestionListActivity.4
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass4) s2);
                try {
                    if (new JSONObject(s2).optString("code").equals("200")) {
                        ProjectApp.mDaoSession.getSubmitFavoritesBeanDao().deleteAll();
                    }
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
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
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("user_id", UserConfig.getUserId());
        ajaxParams.put("note", jSONArray.toString());
        ajaxParams.put("module_type", "1");
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.mPostNoteURL, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.SubjectQuestionListActivity.5
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) throws SQLException {
                super.onSuccess((AnonymousClass5) s2);
                try {
                    if (new JSONObject(s2).optString("code").equals("200")) {
                        ProjectApp.mDaoSession.getSubmitNotesBeanDao().deleteAll();
                    }
                } catch (JSONException e3) {
                    e3.printStackTrace();
                }
            }
        });
    }

    public void clearAnswered(int position) {
        AjaxParams ajaxParams = new AjaxParams();
        if (getIntent().getBooleanExtra("isXitong", false)) {
            ajaxParams.put("type", "part");
            if (position == 0) {
                ajaxParams.put("part_id", getIntent().getStringExtra("chapter_id"));
            } else if (position == 1) {
                ajaxParams.put("part_id", getIntent().getStringExtra("chapter_p_id"));
            } else {
                ajaxParams.put("all", "1");
                if ("40".equals(SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, this))) {
                    try {
                        ajaxParams.put("school_year", getIntent().getStringExtra("benkeposition"));
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                } else if (SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, this).equals(Constants.VIA_REPORT_TYPE_SHARE_TO_QQ)) {
                    if (SharePreferencesUtils.readStrConfig(CommonParameter.App_Type, this.mContext, "").equals(CommonParameter.Xueshuo)) {
                        ajaxParams.put("degree_type", CommonNetImpl.AM);
                    } else {
                        ajaxParams.put("degree_type", "pm");
                    }
                }
            }
        } else {
            ajaxParams.put("type", "chapter");
            if (position == 0) {
                ajaxParams.put("chapter_id", getIntent().getStringExtra("chapter_id"));
            } else if (position == 1) {
                ajaxParams.put("chapter_id", getIntent().getStringExtra("chapter_p_id"));
            } else {
                ajaxParams.put("all", "1");
                if ("40".equals(SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, this))) {
                    try {
                        ajaxParams.put("school_year", getIntent().getStringExtra("benkeposition"));
                    } catch (Exception e3) {
                        e3.printStackTrace();
                    }
                } else if (SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, this).equals(Constants.VIA_REPORT_TYPE_SHARE_TO_QQ)) {
                    if (SharePreferencesUtils.readStrConfig(CommonParameter.App_Type, this.mContext, "").equals(CommonParameter.Xueshuo)) {
                        ajaxParams.put("degree_type", CommonNetImpl.AM);
                    } else {
                        ajaxParams.put("degree_type", "pm");
                    }
                }
            }
        }
        YJYHttpUtils.post(this.mContext.getApplicationContext(), NetworkRequestsURL.mClearAnsweredURL, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.SubjectQuestionListActivity.3
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

    public void clearnDialog(final int position) {
        final CustomDialog customDialog = new CustomDialog(this.mContext, 2);
        customDialog.setCancelable(false);
        if (position == 0) {
            if (getIntent().getStringExtra("title").equals("note")) {
                customDialog.setMessage("是否确定重做本章节下所有做过笔记的题目");
            } else if (getIntent().getStringExtra("title").equals("collect")) {
                customDialog.setMessage("是否确定重做本章节下所有收藏的题目");
            } else if (getIntent().getStringExtra("title").equals("error")) {
                customDialog.setMessage("是否确定重做本章节下所有的错题");
            } else {
                customDialog.setMessage("是否确定重做章节下所有题");
            }
        } else if (position == 1) {
            if (getIntent().getStringExtra("title").equals("note")) {
                customDialog.setMessage("是否确定重做本学科下所有做过笔记的题目");
            } else if (getIntent().getStringExtra("title").equals("collect")) {
                customDialog.setMessage("是否确定重做本学科下所有收藏的题目");
            } else if (getIntent().getStringExtra("title").equals("error")) {
                customDialog.setMessage("是否确定重做本学科下所有的错题");
            } else {
                customDialog.setMessage("是否确定重做本学科下所有题");
            }
        } else if (getIntent().getStringExtra("title").equals("note")) {
            customDialog.setMessage("是否确定重做所有学科做过笔记的题目");
        } else if (getIntent().getStringExtra("title").equals("collect")) {
            customDialog.setMessage("是否确定重做所有学科下收藏的题目");
        } else if (getIntent().getStringExtra("title").equals("error")) {
            customDialog.setMessage("是否确定重做所有学科下所有的错题");
        } else {
            customDialog.setMessage("是否确定重做所有学科下的题");
        }
        customDialog.setNegativeBtn(R.string.cancel, new View.OnClickListener() { // from class: com.psychiatrygarden.activity.ln
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SubjectQuestionListActivity.lambda$clearnDialog$7(customDialog, view);
            }
        });
        customDialog.setPositiveBtn("确定", new View.OnClickListener() { // from class: com.psychiatrygarden.activity.mn
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13024c.lambda$clearnDialog$8(position, customDialog, view);
            }
        });
        customDialog.show();
    }

    public long[] getDataList() {
        long[] longArrayExtra;
        if (getIntent().getStringExtra("title").equals("note")) {
            List<NotesBean> list = getIntent().getBooleanExtra("IsLisnxi", false) ? ProjectApp.mDaoSession.getNotesBeanDao().queryBuilder().where(NotesBeanDao.Properties.Chapter_id.eq(getIntent().getStringExtra("chapter_id")), NotesBeanDao.Properties.Is_practice.eq("1")).orderAsc(NotesBeanDao.Properties.Cate_num).list() : getIntent().getBooleanExtra("isXitong", false) ? ProjectApp.mDaoSession.getNotesBeanDao().queryBuilder().where(NotesBeanDao.Properties.Part_id.eq(getIntent().getStringExtra("chapter_id")), NotesBeanDao.Properties.Is_practice.eq("0")).orderAsc(NotesBeanDao.Properties.Part_num_am).list() : ProjectApp.mDaoSession.getNotesBeanDao().queryBuilder().where(NotesBeanDao.Properties.Chapter_id.eq(getIntent().getStringExtra("chapter_id")), NotesBeanDao.Properties.Is_practice.eq("0")).orderAsc(NotesBeanDao.Properties.Cate_num).list();
            longArrayExtra = new long[list.size()];
            for (int i2 = 0; i2 < list.size(); i2++) {
                longArrayExtra[i2] = list.get(i2).getQuestion_id().longValue();
            }
            try {
                if (getIntent().getBooleanExtra("isXitong", false)) {
                    long[] jArrPutSortData = CommonUtil.putSortData(getIntent().getStringExtra("chapter_id"), longArrayExtra);
                    if (jArrPutSortData.length > 0) {
                        longArrayExtra = jArrPutSortData;
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            runOnUiThread(new Runnable() { // from class: com.psychiatrygarden.activity.co
                @Override // java.lang.Runnable
                public final void run() {
                    this.f11632c.lambda$getDataList$0();
                }
            });
        } else if (getIntent().getStringExtra("title").equals("collect")) {
            new ArrayList();
            List<FavoritesBean> list2 = getIntent().getBooleanExtra("IsLisnxi", false) ? ProjectApp.mDaoSession.getFavoritesBeanDao().queryBuilder().where(FavoritesBeanDao.Properties.Chapter_id.eq(getIntent().getStringExtra("chapter_id")), FavoritesBeanDao.Properties.Is_practice.eq("1")).orderAsc(FavoritesBeanDao.Properties.Cate_num).list() : getIntent().getBooleanExtra("isXitong", false) ? ProjectApp.mDaoSession.getFavoritesBeanDao().queryBuilder().where(FavoritesBeanDao.Properties.Part_id.eq(getIntent().getStringExtra("chapter_id")), FavoritesBeanDao.Properties.Is_practice.eq("0")).orderAsc(FavoritesBeanDao.Properties.Part_num_am).list() : ProjectApp.mDaoSession.getFavoritesBeanDao().queryBuilder().where(FavoritesBeanDao.Properties.Chapter_id.eq(getIntent().getStringExtra("chapter_id")), FavoritesBeanDao.Properties.Is_practice.eq("0")).orderAsc(FavoritesBeanDao.Properties.Cate_num).list();
            longArrayExtra = new long[list2.size()];
            for (int i3 = 0; i3 < list2.size(); i3++) {
                longArrayExtra[i3] = list2.get(i3).getQuestion_id().longValue();
            }
            try {
                if (getIntent().getBooleanExtra("isXitong", false)) {
                    long[] jArrPutSortData2 = CommonUtil.putSortData(getIntent().getStringExtra("chapter_id"), longArrayExtra);
                    if (jArrPutSortData2.length > 0) {
                        longArrayExtra = jArrPutSortData2;
                    }
                }
            } catch (Exception e3) {
                e3.printStackTrace();
            }
            runOnUiThread(new Runnable() { // from class: com.psychiatrygarden.activity.jn
                @Override // java.lang.Runnable
                public final void run() {
                    this.f12566c.lambda$getDataList$0();
                }
            });
        } else if (getIntent().getStringExtra("title").equals("error")) {
            long[] longArrayExtra2 = getIntent().getLongArrayExtra("list");
            try {
                if (getIntent().getBooleanExtra("isXitong", false)) {
                    long[] jArrPutSortData3 = CommonUtil.putSortData(getIntent().getStringExtra("chapter_id"), longArrayExtra2);
                    if (jArrPutSortData3.length > 0) {
                        longArrayExtra2 = jArrPutSortData3;
                    }
                }
            } catch (Exception e4) {
                e4.printStackTrace();
            }
            longArrayExtra = longArrayExtra2;
            final long jCount = getIntent().getBooleanExtra("IsLisnxi", false) ? ProjectApp.mDaoSession.getAnsweredQuestionBeanDao().queryBuilder().where(AnsweredQuestionBeanDao.Properties.Chapter_id.eq(getIntent().getStringExtra("chapter_id")), AnsweredQuestionBeanDao.Properties.Is_right.eq(0), AnsweredQuestionBeanDao.Properties.Is_practice.eq("1")).count() : getIntent().getBooleanExtra("isXitong", false) ? ProjectApp.mDaoSession.getAnsweredQuestionBeanDao().queryBuilder().where(AnsweredQuestionBeanDao.Properties.Part_id.eq(getIntent().getStringExtra("chapter_id")), AnsweredQuestionBeanDao.Properties.Is_right.eq(0), AnsweredQuestionBeanDao.Properties.Is_practice.eq("0")).count() : ProjectApp.mDaoSession.getAnsweredQuestionBeanDao().queryBuilder().where(AnsweredQuestionBeanDao.Properties.Chapter_id.eq(getIntent().getStringExtra("chapter_id")), AnsweredQuestionBeanDao.Properties.Is_right.eq(0), AnsweredQuestionBeanDao.Properties.Is_practice.eq("0")).count();
            runOnUiThread(new Runnable() { // from class: com.psychiatrygarden.activity.SubjectQuestionListActivity.1
                @Override // java.lang.Runnable
                public void run() {
                    SubjectQuestionListActivity.this.lambda$getDataList$1(jCount);
                }
            });
        } else {
            setTitle(getIntent().getStringExtra("subject_name") + "");
            longArrayExtra = getIntent().getLongArrayExtra("list");
            final long jCount2 = getIntent().getBooleanExtra("IsLisnxi", false) ? ProjectApp.mDaoSession.getAnsweredQuestionBeanDao().queryBuilder().where(AnsweredQuestionBeanDao.Properties.Chapter_id.eq(getIntent().getStringExtra("chapter_id")), AnsweredQuestionBeanDao.Properties.Is_practice.eq("1")).count() : getIntent().getBooleanExtra("isXitong", false) ? ProjectApp.mDaoSession.getAnsweredQuestionBeanDao().queryBuilder().where(AnsweredQuestionBeanDao.Properties.Part_id.eq(getIntent().getStringExtra("chapter_id")), AnsweredQuestionBeanDao.Properties.Is_practice.eq("0")).count() : ProjectApp.mDaoSession.getAnsweredQuestionBeanDao().queryBuilder().where(AnsweredQuestionBeanDao.Properties.Chapter_id.eq(getIntent().getStringExtra("chapter_id")), AnsweredQuestionBeanDao.Properties.Is_practice.eq("0")).count();
            runOnUiThread(new Runnable() { // from class: com.psychiatrygarden.activity.kn
                @Override // java.lang.Runnable
                public final void run() {
                    this.f12598c.lambda$getDataList$1(jCount2);
                }
            });
            if ((getIntent().getBooleanExtra("isXitong", false) || getIntent().getBooleanExtra("iszhongyibank", false)) && longArrayExtra.length > 0) {
                if (!Constants.VIA_REPORT_TYPE_SET_AVATAR.equals(SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, this.mContext))) {
                    ArrayList arrayList = new ArrayList();
                    for (long j2 : longArrayExtra) {
                        arrayList.add(Long.valueOf(j2));
                    }
                    Collections.shuffle(arrayList, new Random(SharePreferencesUtils.readIntConfig(CommonParameter.mrandomValue + getIntent().getStringExtra("chapter_id") + SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, ProjectApp.instance()), this, 1)));
                    longArrayExtra = new long[arrayList.size()];
                    for (int i4 = 0; i4 < arrayList.size(); i4++) {
                        longArrayExtra[i4] = ((Long) arrayList.get(i4)).longValue();
                    }
                }
                SharePreferencesUtils.writeStrConfig(CommonParameter.mRandomChapterIdValue + getIntent().getStringExtra("chapter_id") + SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, ProjectApp.instance()), new Gson().toJson(longArrayExtra).toString(), this);
            }
        }
        return longArrayExtra;
    }

    public boolean getFilter(QuestionInfoBean questionInfoBean) {
        for (QuestionLocalBean.QLocalBean.QCLocalBean qCLocalBean : this.ids) {
            if (!SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, ProjectApp.instance(), "").equals("40") && "year".equals(qCLocalBean.getSource()) && !"-1".equals(qCLocalBean.getType()) && !getYearFilter(qCLocalBean.getType(), questionInfoBean)) {
                return false;
            }
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

    public boolean getSourceFilter(String type, QuestionInfoBean questionInfoBean) {
        String source_filter = questionInfoBean.getSource_filter();
        return "0".equals(type) ? "考研真题".equals(source_filter) : "1".equals(type) ? "复试真题".equals(source_filter) : "2".equals(type) ? "名校题库".equals(source_filter) : !"3".equals(type) || "执医真题".equals(source_filter);
    }

    public boolean getYearFilter(String type, QuestionInfoBean questionInfoBean) throws NumberFormatException {
        String year = questionInfoBean.getYear();
        String media_img = questionInfoBean.getMedia_img();
        if (year == null || "".equals(year)) {
            year = "0";
        }
        long j2 = Long.parseLong(year);
        if ("0".equals(type)) {
            if (j2 != 30000 && j2 > 2016) {
                return false;
            }
        } else if ("1".equals(type)) {
            if (j2 < 2017) {
                return false;
            }
        } else if ("2".equals(type)) {
            if (j2 < 2012) {
                return false;
            }
        } else if ("3".equals(type)) {
            if (j2 < 2007) {
                return false;
            }
        } else if ("4".equals(type)) {
            if (j2 < 2002) {
                return false;
            }
        } else if ("5".equals(type)) {
            if (media_img == null || "".equals(media_img)) {
                return false;
            }
        } else if (Constants.VIA_SHARE_TYPE_INFO.equals(type)) {
            if (j2 < 1997) {
                return false;
            }
        } else if ("7".equals(type)) {
            if (j2 != 30000 && j2 > 2018) {
                return false;
            }
        } else if (Constants.VIA_SHARE_TYPE_PUBLISHVIDEO.equals(type)) {
            if (j2 != 30000 && j2 > 2020) {
                return false;
            }
        } else if (Constants.VIA_SHARE_TYPE_MINI_PROGRAM.equals(type) && j2 < 2019) {
            return false;
        }
        return true;
    }

    public void handInDialog(boolean isTrue) {
        ArrayList arrayList = new ArrayList();
        final double d3 = 0.0d;
        for (int i2 = 0; i2 < this.qadapter.getList_questionid().length; i2++) {
            AnsweredQuestionBean answeredQuestionBeanLoadByRowId = ProjectApp.mDaoSession.getAnsweredQuestionBeanDao().loadByRowId(this.qadapter.getList_questionid()[i2]);
            if (answeredQuestionBeanLoadByRowId != null) {
                arrayList.add(answeredQuestionBeanLoadByRowId);
                if (answeredQuestionBeanLoadByRowId.getIs_right().equals("1")) {
                    d3 += 1.0d;
                }
            }
        }
        final int size = arrayList.size();
        final int length = this.qadapter.getList_questionid().length;
        final double d4 = size != 0 ? (d3 / size) * 100.0d : 0.0d;
        final DecimalFormat decimalFormat = new DecimalFormat("#0.0");
        if (isTrue) {
            final CustomDialog customDialog = new CustomDialog(this.mContext, 2);
            customDialog.setCancelable(false);
            customDialog.isOutTouchDismiss(false);
            if (this.list_questionid.length == arrayList.size()) {
                customDialog.setMessage("确定要交卷吗");
            } else {
                customDialog.setMessage("您还有" + (this.qadapter.getList_questionid().length - arrayList.size()) + "题没做，确定要交卷吗？");
            }
            customDialog.setNegativeBtn(R.string.cancel, new View.OnClickListener() { // from class: com.psychiatrygarden.activity.pn
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    SubjectQuestionListActivity.lambda$handInDialog$9(customDialog, view);
                }
            });
            customDialog.setPositiveBtn(R.string.ok, new View.OnClickListener() { // from class: com.psychiatrygarden.activity.qn
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f13741c.lambda$handInDialog$10(customDialog, length, size, d3, decimalFormat, d4, view);
                }
            });
            customDialog.show();
            return;
        }
        getScoreNewData(length + "", replacevalue(size + ""), replacevalue(d3 + ""), replacevalue((((double) size) - d3) + ""), replacevalue(decimalFormat.format(d4)));
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.openrel = (RelativeLayout) findViewById(R.id.openrel);
        this.appBarLayout = (AppBarLayout) findViewById(R.id.appBarLayout);
        this.opentxt = (TextView) findViewById(R.id.opentxt);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycleview);
        this.recycleview = recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.recycleview.setNestedScrollingEnabled(false);
        this.recycleview.setHasFixedSize(true);
        RecyclerView recyclerView2 = (RecyclerView) findViewById(R.id.questionList_GridView);
        this.gridView = recyclerView2;
        recyclerView2.setLayoutManager(new GridLayoutManager(this, 5));
        this.openrel.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.rn
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13805c.lambda$init$13(view);
            }
        });
        initQuestionType();
        setData(true);
        this.mBtnActionbarBack.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.sn
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13928c.lambda$init$14(view);
            }
        });
        this.appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() { // from class: com.psychiatrygarden.activity.un
            @Override // com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener, com.google.android.material.appbar.AppBarLayout.BaseOnOffsetChangedListener
            public final void onOffsetChanged(AppBarLayout appBarLayout, int i2) {
                this.f13996a.lambda$init$15(appBarLayout, i2);
            }
        });
    }

    public void initQuestionType() {
        List<QuestionLocalBean.QLocalBean> listInitLocalData = QuestionLocalBean.getInstance().initLocalData(1, getIntent().getBooleanExtra("ISPractice", false));
        if (listInitLocalData.size() == 0) {
            this.appBarLayout.setVisibility(8);
            return;
        }
        String strConfig = SharePreferencesUtils.readStrConfig(CommonParameter.questionids, this, "");
        if (!"".equals(strConfig)) {
            this.ids = (List) new Gson().fromJson(strConfig, new TypeToken<List<QuestionLocalBean.QLocalBean.QCLocalBean>>() { // from class: com.psychiatrygarden.activity.SubjectQuestionListActivity.8
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
        BaseQuickAdapter<QuestionLocalBean.QLocalBean, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<QuestionLocalBean.QLocalBean, BaseViewHolder>(R.layout.activity_list_type_item, listInitLocalData) { // from class: com.psychiatrygarden.activity.SubjectQuestionListActivity.9

            /* renamed from: com.psychiatrygarden.activity.SubjectQuestionListActivity$9$1, reason: invalid class name */
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
                                ToastUtil.shortToast(SubjectQuestionListActivity.this.mContext, "已经选中");
                                return;
                            }
                            for (int i2 = 0; i2 < qLocalBean.getQcLocalBeanList().size(); i2++) {
                                qLocalBean.getQcLocalBeanList().get(i2).setIsSelected(0);
                            }
                            qLocalBean.getQcLocalBeanList().get(baseViewHolder.getAdapterPosition()).setIsSelected(1);
                            notifyDataSetChanged();
                            if (SubjectQuestionListActivity.this.isHaveData(qLocalBean.getType())) {
                                for (int i3 = 0; i3 < SubjectQuestionListActivity.this.ids.size(); i3++) {
                                    if (((QuestionLocalBean.QLocalBean.QCLocalBean) SubjectQuestionListActivity.this.ids.get(i3)).getSource().equals(qLocalBean.getType())) {
                                        ((QuestionLocalBean.QLocalBean.QCLocalBean) SubjectQuestionListActivity.this.ids.get(i3)).setText(qCLocalBean.getText());
                                        ((QuestionLocalBean.QLocalBean.QCLocalBean) SubjectQuestionListActivity.this.ids.get(i3)).setType(qCLocalBean.getType());
                                    }
                                }
                            } else {
                                QuestionLocalBean.QLocalBean.QCLocalBean qCLocalBean2 = new QuestionLocalBean.QLocalBean.QCLocalBean();
                                qCLocalBean2.setSource(qLocalBean.getType());
                                qCLocalBean2.setType(qCLocalBean.getType());
                                qCLocalBean2.setText(qCLocalBean.getText());
                                SubjectQuestionListActivity.this.ids.add(qCLocalBean2);
                            }
                            SharePreferencesUtils.writeStrConfig(CommonParameter.questionids, new Gson().toJson(SubjectQuestionListActivity.this.ids).toString(), SubjectQuestionListActivity.this);
                            SubjectQuestionListActivity.this.setOpenText();
                            if (!"model".equals(qLocalBean.getType())) {
                                SubjectQuestionListActivity.this.isClickTrue = true;
                                SubjectQuestionListActivity.this.mChangeData(false);
                                return;
                            }
                            if ("0".equals(qCLocalBean.getType())) {
                                ProjectApp.listSubmitData.clear();
                                SharePreferencesUtils.writeIntConfig(CommonParameter.ISCESHITIKU, 0, SubjectQuestionListActivity.this.mContext);
                            } else if ("1".equals(qCLocalBean.getType())) {
                                SharePreferencesUtils.writeIntConfig(CommonParameter.ISCESHITIKU, 1, SubjectQuestionListActivity.this.mContext);
                            } else if ("2".equals(qCLocalBean.getType())) {
                                ProjectApp.listSubmitData.clear();
                                SharePreferencesUtils.writeIntConfig(CommonParameter.ISCESHITIKU, 2, SubjectQuestionListActivity.this.mContext);
                            }
                            SubjectQuestionListActivity.this.qadapter.notifyDataSetChanged();
                            return;
                        }
                        ToastUtil.shortToast(SubjectQuestionListActivity.this.mContext, "下标异常");
                    } catch (Exception unused) {
                        ToastUtil.shortToast(SubjectQuestionListActivity.this.mContext, "操作异常，请重新选择！");
                    }
                }

                @Override // com.chad.library.adapter.base.BaseQuickAdapter
                public void convert(@NonNull final BaseViewHolder helper, final QuestionLocalBean.QLocalBean.QCLocalBean item) {
                    TextView textView = (TextView) helper.getView(R.id.labeltext);
                    textView.setText(item.getText());
                    textView.setSelected(item.getIsSelected() == 1);
                    final QuestionLocalBean.QLocalBean qLocalBean = this.val$items;
                    textView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.do
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            this.f12254c.lambda$convert$0(helper, qLocalBean, item, view);
                        }
                    });
                }
            }

            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helpers, QuestionLocalBean.QLocalBean items) {
                helpers.setText(R.id.labeid, items.getText() + "：");
                RecyclerView recyclerView = (RecyclerView) helpers.getView(R.id.recychildview);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SubjectQuestionListActivity.this);
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

    /* renamed from: mChangeButtomStatus, reason: merged with bridge method [inline-methods] */
    public void lambda$getDataList$1(long answer_num) {
        if (answer_num > 0) {
            this.mBtnActionbarRight.setEnabled(true);
            this.mBtnActionbarRight.setClickable(true);
            if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
                this.mBtnActionbarRight.setTextColor(ContextCompat.getColor(this, R.color.white));
                return;
            } else {
                this.mBtnActionbarRight.setTextColor(ContextCompat.getColor(this, R.color.app_title_color_night));
                return;
            }
        }
        this.mBtnActionbarRight.setEnabled(false);
        this.mBtnActionbarRight.setClickable(false);
        if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
            this.mBtnActionbarRight.setTextColor(ContextCompat.getColor(this, R.color.pingeg));
        } else {
            this.mBtnActionbarRight.setTextColor(ContextCompat.getColor(this, R.color.jiucuo_night));
        }
    }

    /* renamed from: mChangeButtomStatusTwo, reason: merged with bridge method [inline-methods] */
    public void lambda$getDataList$0() {
        if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
            this.mBtnActionbarRight.setTextColor(ContextCompat.getColor(this, R.color.white));
        } else {
            this.mBtnActionbarRight.setTextColor(ContextCompat.getColor(this, R.color.app_title_color_night));
        }
    }

    public void mChangeData(boolean isTrue) {
        setOpenText();
        Observable.create(new ObservableOnSubscribe() { // from class: com.psychiatrygarden.activity.yn
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) throws Exception {
                this.f14224a.lambda$mChangeData$16(observableEmitter);
            }
        }).subscribeOn(Schedulers.io()).doOnSubscribe(new Consumer() { // from class: com.psychiatrygarden.activity.zn
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                SubjectQuestionListActivity.lambda$mChangeData$17((Disposable) obj);
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.psychiatrygarden.activity.ao
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f11083c.lambda$mChangeData$19((long[]) obj);
            }
        });
    }

    public void mOnDestroy() {
        if (this.isDestroyed) {
            return;
        }
        this.isDestroyed = true;
        if (getIntent().getStringExtra("title").equals("note") || getIntent().getStringExtra("title").equals("collect") || getIntent().getStringExtra("title").equals("error")) {
            EventBus.getDefault().post(new ComSubFWNItemBean("SubjectQuestionClearn", this.list_questionid, getIntent().getStringExtra("title"), getIntent().getStringExtra("chapter_id"), getIntent().getBooleanExtra("isLisnxi", false)));
        }
        EventBus.getDefault().post("QuestionYearFragment");
        if (this.isClickTrue) {
            EventBus.getDefault().post("QuestionSubjectFragmenData");
        } else {
            EventBus.getDefault().post("QuestionSubjectFragment");
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSwipeBackEnable(false);
        setTitle(getIntent().getStringExtra("subject_name") + "");
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

    public void onEventMainThread(ComBackEventBean mComBackEventBean) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
        if (!str.equals("SubjectActivityThread")) {
            if (str.equals("MatchFetchTat")) {
                handInDialog(false);
                return;
            }
            return;
        }
        new MyThread(this).start();
        if (!CommonUtil.isNetworkConnected(this.mContext)) {
            long jCount = ProjectApp.mDaoSession.getSubmitAnsweredQuestionBeanDao().count();
            if (jCount > 0) {
                NewToast.showShort(this.mContext, "网络已经断开，您还有" + jCount + "题未提交", 0).show();
            }
        }
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
        } else {
            handInDialog(true);
        }
        return true;
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
        if (isFinishing()) {
            mOnDestroy();
        }
    }

    public void postRedoValue(int position) {
        String str;
        String str2;
        String str3;
        String str4;
        String str5;
        String str6;
        String str7 = "chapter_id";
        String string = getIntent().getExtras().getString("chapter_id");
        String string2 = getIntent().getExtras().getString("chapter_p_id");
        if (getIntent().getBooleanExtra("isXitong", false)) {
            str7 = "part_id";
            str = "part_parent_id";
        } else {
            str = "chapter_parent_id";
        }
        if (getIntent().getStringExtra("title").equals("note")) {
            if (position == 0) {
                str6 = "select question_id from notes_bean where " + str7 + "=" + string + com.alipay.sdk.util.h.f3376b;
            } else if (position == 1) {
                str6 = "select question_id from notes_bean where " + str + "=" + string2 + com.alipay.sdk.util.h.f3376b;
            } else {
                str6 = "select question_id from notes_bean;";
            }
            getNoteDateForDelete(str6, 0, "");
            return;
        }
        if (getIntent().getStringExtra("title").equals("collect")) {
            if (position == 0) {
                str5 = "select question_id from favorites_Bean where " + str7 + "=" + string + com.alipay.sdk.util.h.f3376b;
            } else if (position == 1) {
                str5 = "select question_id from favorites_Bean where " + str + "=" + string2 + com.alipay.sdk.util.h.f3376b;
            } else {
                str5 = "select question_id from favorites_Bean;";
            }
            getNoteDateForDelete(str5, 0, "");
            return;
        }
        if (getIntent().getStringExtra("title").equals("error")) {
            if (position == 0) {
                str4 = "select question_id from WRONG_BEAN where " + str7 + "=" + string + com.alipay.sdk.util.h.f3376b;
            } else if (position == 1) {
                str4 = "select question_id from WRONG_BEAN where " + str + "=" + string2 + com.alipay.sdk.util.h.f3376b;
            } else {
                str4 = "select question_id from WRONG_BEAN;";
            }
            getNoteDateForDelete(str4, 0, "");
            return;
        }
        if (getIntent().getStringExtra("title").equals("year")) {
            if (position == 0) {
                str2 = "delete from ANSWERED_QUESTION_BEAN where " + str7 + "=" + string + com.alipay.sdk.util.h.f3376b;
                str3 = "delete from WRONG_BEAN where " + str7 + "=" + string + com.alipay.sdk.util.h.f3376b;
            } else if (position == 1) {
                str2 = "delete from ANSWERED_QUESTION_BEAN where " + str + "=" + string2 + com.alipay.sdk.util.h.f3376b;
                str3 = "delete from WRONG_BEAN where " + str + "=" + string2 + com.alipay.sdk.util.h.f3376b;
            } else {
                str2 = "delete from ANSWERED_QUESTION_BEAN;";
                str3 = "delete from WRONG_BEAN;";
            }
            getNoteDateForDelete(str2, 1, str3);
            clearAnswered(position);
        }
    }

    public String replacevalue(String s2) {
        return (s2 == null || s2.equals("")) ? "0" : s2.indexOf(StrPool.DOT) > 0 ? s2.replaceAll("0+?$", "").replaceAll("[.]$", "") : s2;
    }

    public void scoreDialog(String score) {
        final CustomDialog customDialog = new CustomDialog(this.mContext, 1);
        customDialog.setCancelable(false);
        customDialog.isOutTouchDismiss(false);
        customDialog.setMessage(score);
        customDialog.setPositiveBtn(R.string.ok, new View.OnClickListener() { // from class: com.psychiatrygarden.activity.bo
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11124c.lambda$scoreDialog$12(customDialog, view);
            }
        });
        customDialog.show();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_questionlist);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.mBtnActionbarRight.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.on
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13086c.lambda$setListenerForWidget$4(view);
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
            if (!TextUtils.equals(this.ids.get(i2).getText(), "全部")) {
                if (!"note".equals(getIntent().getStringExtra("title")) && !"collect".equals(getIntent().getStringExtra("title")) && !"error".equals(getIntent().getStringExtra("title"))) {
                    stringBuffer.append(this.ids.get(i2).getText());
                    stringBuffer.append("-");
                } else if (!TextUtils.equals(this.ids.get(i2).getText(), "测试模式") && !TextUtils.equals(this.ids.get(i2).getText(), "练习模式") && !TextUtils.equals(this.ids.get(i2).getText(), "背题模式")) {
                    stringBuffer.append(this.ids.get(i2).getText());
                    stringBuffer.append("-");
                }
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
        objectAnimatorOfFloat.addListener(new Animator.AnimatorListener() { // from class: com.psychiatrygarden.activity.SubjectQuestionListActivity.7
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
}
