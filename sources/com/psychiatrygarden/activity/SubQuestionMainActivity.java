package com.psychiatrygarden.activity;

import android.content.res.Resources;
import android.database.SQLException;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.viewpager.widget.ViewPager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.bean.RecdQuestionBean;
import com.psychiatrygarden.bean.SubmitAnsweredQuestionBean;
import com.psychiatrygarden.bean.SubmitFavoritesBean;
import com.psychiatrygarden.bean.SubmitNotesBean;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.fragmenthome.BaseViewPagerAdapter;
import com.psychiatrygarden.fragmenthome.SubCrazilyFragment;
import com.psychiatrygarden.fragmenthome.SubCrazilyQuestionFragment;
import com.psychiatrygarden.fragmenthome.SubQuestionFragment;
import com.psychiatrygarden.fragmenthome.SubjectiveQuestionFragment;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.interfaceclass.onDialogShareClickListener;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.widget.CustomDialog;
import com.psychiatrygarden.widget.ViewPagerCompat;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.List;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class SubQuestionMainActivity extends BaseActivity {
    private TextView include_btn_right_tv;
    private ViewPagerCompat questiondetails_viewPager;
    private int position = 0;
    public List<RecdQuestionBean.DataBean> mDataList = new ArrayList();
    private String module_type = "1";

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$ObServerData$5(ObservableEmitter observableEmitter) throws Exception {
        submitAnswered();
        submitNotes();
        submitFavorites();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$ObServerData$6(Object obj) throws Exception {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$handInDialog$3(CustomDialog customDialog, View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        customDialog.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$handInDialog$4(CustomDialog customDialog, View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        EventBus.getDefault().post(this.mDataList);
        EventBus.getDefault().post("doFinishPost");
        customDialog.dismissNoAnimaltion();
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(View view) {
        List<RecdQuestionBean.DataBean> list = this.mDataList;
        if (list == null || list.size() <= 0) {
            return;
        }
        int i2 = 0;
        for (int i3 = 0; i3 < this.mDataList.size(); i3++) {
            if (TextUtils.isEmpty(this.mDataList.get(i3).getOwnerAns())) {
                i2++;
            }
        }
        handInDialog(i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$1(View view) {
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$2(int i2) {
        if (i2 == 0) {
            AlertToast("已为第一题");
        } else if (i2 == 1) {
            AlertToast("已为最后一题");
        }
    }

    private void submitAnswered() throws JSONException {
        List<SubmitAnsweredQuestionBean> listLoadAll = ProjectApp.mDaoSession.getSubmitAnsweredQuestionBeanDao().loadAll();
        if (listLoadAll == null || listLoadAll.size() == 0) {
            return;
        }
        JSONArray jSONArray = new JSONArray();
        for (int i2 = 0; i2 < listLoadAll.size(); i2++) {
            if (!TextUtils.isEmpty(listLoadAll.get(i2).getAnswer())) {
                JSONObject jSONObject = new JSONObject();
                try {
                    jSONObject.put("answer", listLoadAll.get(i2).getAnswer().trim());
                    jSONObject.put("question_id", listLoadAll.get(i2).getQuestion_id());
                    jSONObject.put("is_right", listLoadAll.get(i2).getIs_right().trim());
                    jSONObject.put("app_id", SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, ProjectApp.instance()));
                    jSONArray.put(jSONObject);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
        AjaxParams ajaxParams = new AjaxParams();
        if (TextUtils.isEmpty(jSONArray.toString())) {
            return;
        }
        ajaxParams.put("answer", jSONArray.toString());
        ajaxParams.put("module_type", "1");
        YJYHttpUtils.post(ProjectApp.instance(), NetworkRequestsURL.mPostAnsweredURL, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.SubQuestionMainActivity.5
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass5) s2);
                try {
                    if (new JSONObject(s2).optString("code").equals("200")) {
                        ProjectApp.mDaoSession.getSubmitAnsweredQuestionBeanDao().deleteAll();
                    }
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
            }
        });
    }

    private void submitFavorites() throws JSONException {
        List<SubmitFavoritesBean> listLoadAll = ProjectApp.mDaoSession.getSubmitFavoritesBeanDao().loadAll();
        JSONArray jSONArray = new JSONArray();
        for (int i2 = 0; i2 < listLoadAll.size(); i2++) {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("question_id", listLoadAll.get(i2).getQuestion_id());
                jSONObject.put("app_id", listLoadAll.get(i2).getApp_id());
                jSONArray.put(i2, jSONObject);
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("user_id", UserConfig.getUserId());
        ajaxParams.put("collection", jSONArray.toString());
        ajaxParams.put("module_type", "1");
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.mPostCollectionURL, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.SubQuestionMainActivity.3
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass3) s2);
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

    private void submitNotes() throws JSONException {
        List<SubmitNotesBean> listLoadAll = ProjectApp.mDaoSession.getSubmitNotesBeanDao().loadAll();
        JSONArray jSONArray = new JSONArray();
        for (int i2 = 0; i2 < listLoadAll.size(); i2++) {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("question_id", listLoadAll.get(i2).getQuestion_id());
                jSONObject.put("content", listLoadAll.get(i2).getContent());
                jSONObject.put("app_id", listLoadAll.get(i2).getApp_id());
                jSONArray.put(i2, jSONObject);
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("user_id", UserConfig.getUserId());
        ajaxParams.put("note", jSONArray.toString());
        ajaxParams.put("module_type", "1");
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.mPostNoteURL, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.SubQuestionMainActivity.4
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) throws SQLException {
                super.onSuccess((AnonymousClass4) s2);
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

    public void ObServerData() {
        Observable.create(new ObservableOnSubscribe() { // from class: com.psychiatrygarden.activity.dm
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) throws Exception {
                this.f12251a.lambda$ObServerData$5(observableEmitter);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.psychiatrygarden.activity.em
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                SubQuestionMainActivity.lambda$ObServerData$6(obj);
            }
        });
    }

    public void handInDialog(int count) {
        final CustomDialog customDialog = new CustomDialog(this.mContext, 2);
        customDialog.setCancelable(false);
        customDialog.isOutTouchDismiss(false);
        if (SharePreferencesUtils.readBooleanConfig(CommonParameter.isceshitidan, false, this.mContext)) {
            if (count == 0) {
                customDialog.setMessage("确定要交卷吗");
            } else {
                customDialog.setMessage("您还有" + count + "题没做，确定要交卷吗？");
            }
        } else if (count == 0) {
            customDialog.setMessage("确定要统计吗");
        } else {
            customDialog.setMessage("您还有" + count + "题没做，确定要统计吗？");
        }
        customDialog.setNegativeBtn(R.string.cancel, new View.OnClickListener() { // from class: com.psychiatrygarden.activity.fm
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SubQuestionMainActivity.lambda$handInDialog$3(customDialog, view);
            }
        });
        customDialog.setPositiveBtn(R.string.ok, new View.OnClickListener() { // from class: com.psychiatrygarden.activity.gm
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12450c.lambda$handInDialog$4(customDialog, view);
            }
        });
        customDialog.show();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() throws Resources.NotFoundException {
        Bundle bundle;
        if (getIntent().getExtras().getString("module_type") != null) {
            this.module_type = getIntent().getExtras().getString("module_type");
        }
        this.mDataList = (List) new Gson().fromJson(ProjectApp.mUpDataSource, new TypeToken<List<RecdQuestionBean.DataBean>>() { // from class: com.psychiatrygarden.activity.SubQuestionMainActivity.1
        }.getType());
        this.include_btn_right_tv = (TextView) findViewById(R.id.include_btn_right_tv);
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.rl_top_title);
        if (this.module_type.equals("4")) {
            this.include_btn_right_tv.setVisibility(8);
        } else {
            this.include_btn_right_tv.setVisibility(0);
        }
        this.include_btn_right_tv.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.am
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f10999c.lambda$init$0(view);
            }
        });
        if (SharePreferencesUtils.readBooleanConfig(CommonParameter.isceshitidan, false, this.mContext)) {
            this.include_btn_right_tv.setText("交卷");
        } else {
            this.include_btn_right_tv.setText("统计");
        }
        TextView textView = (TextView) findViewById(R.id.include_title_center);
        TextView textView2 = (TextView) findViewById(R.id.questiondetails_tv_title);
        ViewPagerCompat viewPagerCompat = (ViewPagerCompat) findViewById(R.id.questiondetails_viewPager);
        this.questiondetails_viewPager = viewPagerCompat;
        viewPagerCompat.setSaveEnabled(false);
        this.position = getIntent().getIntExtra("position", 0);
        textView.setText(String.format("%s", getIntent().getStringExtra("subject_name")));
        textView2.setText(String.format("%s", getIntent().getStringExtra("chapter_name")));
        if (TextUtils.isEmpty("" + getIntent().getStringExtra("chapter_name"))) {
            relativeLayout.setVisibility(8);
        }
        ImageView imageView = (ImageView) findViewById(R.id.include_btn_left);
        imageView.setVisibility(0);
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.bm
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11123c.lambda$init$1(view);
            }
        });
        ArrayList arrayList = new ArrayList();
        List<RecdQuestionBean.DataBean> list = this.mDataList;
        if (list == null || list.size() <= 0) {
            return;
        }
        for (int i2 = 0; i2 < this.mDataList.size(); i2++) {
            bundle = new Bundle();
            bundle.putInt("positionT", i2);
            bundle.putString("collection_id", "" + getIntent().getExtras().getString("collection_id"));
            bundle.putString("module_type", "" + this.module_type);
            String type = this.mDataList.get(i2).getType();
            type.hashCode();
            switch (type) {
                case "1":
                case "2":
                    arrayList.add(new BaseViewPagerAdapter.PagerInfo("真题", SubQuestionFragment.class, bundle));
                    break;
                case "3":
                    arrayList.add(new BaseViewPagerAdapter.PagerInfo("填空题", SubCrazilyQuestionFragment.class, bundle));
                    break;
                case "kuangbei":
                    arrayList.add(new BaseViewPagerAdapter.PagerInfo("狂背", SubCrazilyFragment.class, bundle));
                    break;
                default:
                    arrayList.add(new BaseViewPagerAdapter.PagerInfo("主观题", SubjectiveQuestionFragment.class, bundle));
                    break;
            }
        }
        this.questiondetails_viewPager.setAdapter(new BaseViewPagerAdapter(this.mContext, getSupportFragmentManager(), arrayList));
        this.questiondetails_viewPager.setCurrentItem(this.position);
        this.questiondetails_viewPager.setOffscreenPageLimit(1);
        this.questiondetails_viewPager.setOnListener(new onDialogShareClickListener() { // from class: com.psychiatrygarden.activity.cm
            @Override // com.psychiatrygarden.interfaceclass.onDialogShareClickListener
            public final void onclickIntBack(int i3) {
                this.f11630a.lambda$init$2(i3);
            }
        });
        this.questiondetails_viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() { // from class: com.psychiatrygarden.activity.SubQuestionMainActivity.2
            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrollStateChanged(int state) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageSelected(int i3) {
                SubQuestionMainActivity.this.position = i3;
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActionBar.hide();
        setSwipeBackEnable(false);
        if (getIntent().getExtras().getString("type").equals("all")) {
            return;
        }
        this.include_btn_right_tv.setVisibility(8);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        try {
            EventBus.getDefault().post(this.mDataList);
            ObServerData();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) throws Resources.NotFoundException {
        if (str.equals("mIndex")) {
            if (this.position == this.mDataList.size() - 1) {
                AlertToast("已是最后一题，请交卷");
            } else {
                this.questiondetails_viewPager.setCurrentItem(this.position + 1);
            }
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_sub_question_main);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}
