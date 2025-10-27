package com.psychiatrygarden.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.gson.Gson;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.adapter.UserOwernCommAdapter;
import com.psychiatrygarden.bean.QuestionInfoBean;
import com.psychiatrygarden.bean.QuestionInfoBeanDao;
import com.psychiatrygarden.bean.UserOwernCarepreBean;
import com.psychiatrygarden.bean.UserOwnerBean;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.SkinManager;
import com.tencent.connect.common.Constants;
import com.tencent.open.SocialConstants;
import com.umeng.socialize.net.dplus.CommonNetImpl;
import com.yikaobang.yixue.R;
import de.greenrobot.dao.query.WhereCondition;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.apache.http.cookie.ClientCookie;

/* loaded from: classes5.dex */
public class UserOwernCommActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {
    private static final int REFRESH_COMPLETE = 272;
    private ExpandableListView elv_re_live;
    private UserOwnerBean mUserOwnerBean;
    private SwipeRefreshLayout swipe;
    private TextView tv_empty;
    private UserOwernCommAdapter userOwernCommAdapter;
    private List<UserOwnerBean.DataBean> data = new ArrayList();
    private final List<String> mListStr = new ArrayList();
    private int sign = -1;
    private int tempPosition = 0;
    private final List<UserOwernCarepreBean> mUserOwernCarepreBeans = new ArrayList();

    @SuppressLint({"HandlerLeak"})
    private final Handler mHandler = new Handler() { // from class: com.psychiatrygarden.activity.UserOwernCommActivity.1
        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            if (msg.what == 272) {
                UserOwernCommActivity.this.getNewData();
            }
        }
    };

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setContentView$0() {
        this.swipe.setRefreshing(true);
        getNewData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$1(int i2) {
        int i3 = this.tempPosition;
        if (i3 != i2) {
            this.elv_re_live.collapseGroup(i3);
            this.tempPosition = i2;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$setListenerForWidget$2(ExpandableListView expandableListView, View view, int i2, long j2) {
        if (isLogin()) {
            if (getIntent().getExtras().getInt("typeValue", 0) == 2) {
                this.mUserOwernCarepreBeans.clear();
                new ArrayList();
                List<QuestionInfoBean> list = ProjectApp.mTiKuDaoSession.getQuestionInfoBeanDao().queryBuilder().where(QuestionInfoBeanDao.Properties.Year.eq(this.data.get(i2).getId()), new WhereCondition[0]).orderAsc(QuestionInfoBeanDao.Properties.Number_number).list();
                for (int i3 = 0; i3 < list.size(); i3++) {
                    this.mListStr.add(list.get(i3).getQuestion_id() + "");
                }
                List<String> list2 = this.mListStr;
                if (list2 != null && list2.size() > 0) {
                    for (int i4 = 0; i4 < this.data.get(i2).getObj_id().size(); i4++) {
                        forEachData(this.data.get(i2).getObj_id().get(i4), this.mListStr);
                    }
                }
                if (this.mUserOwernCarepreBeans.size() > 0) {
                    Collections.sort(this.mUserOwernCarepreBeans);
                    int size = this.mUserOwernCarepreBeans.size();
                    long[] jArr = new long[size];
                    if (size < 1) {
                        return true;
                    }
                    for (int i5 = 0; i5 < size; i5++) {
                        jArr[i5] = Long.parseLong(this.mUserOwernCarepreBeans.get(i5).getObj_id());
                    }
                    Intent intent = new Intent(this.mContext, (Class<?>) YearQuestionListActivity.class);
                    intent.putExtra("year", this.data.get(i2).getTitle());
                    intent.putExtra("list", jArr);
                    intent.putExtra("isUserComm", true);
                    intent.putExtra("ISPractice", true);
                    if (!SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, this.mContext).equals(Constants.VIA_REPORT_TYPE_SHARE_TO_QQ)) {
                        intent.putExtra("title", this.data.get(i2).getTitle() + SharePreferencesUtils.readStrConfig(CommonParameter.app_title, this.mContext) + "真题");
                    } else if (this.data.get(i2).getTitle().equals("2017") || this.data.get(i2).getTitle().equals("2018")) {
                        intent.putExtra("title", this.data.get(i2).getTitle() + "临床医学综合能力真题");
                    } else {
                        intent.putExtra("title", this.data.get(i2).getTitle() + "西医综合真题");
                    }
                    startActivity(intent);
                }
            } else {
                int i6 = this.sign;
                if (i6 == -1) {
                    this.elv_re_live.expandGroup(i2);
                    this.elv_re_live.setSelectedGroup(i2);
                    this.sign = i2;
                } else if (i6 == i2) {
                    this.elv_re_live.collapseGroup(i6);
                    this.sign = -1;
                } else {
                    this.elv_re_live.collapseGroup(i6);
                    this.elv_re_live.expandGroup(i2);
                    this.elv_re_live.setSelectedGroup(i2);
                    this.sign = i2;
                }
            }
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$setListenerForWidget$3(ExpandableListView expandableListView, View view, int i2, int i3, long j2) {
        if (CommonUtil.isFastClick()) {
            return true;
        }
        if (UserConfig.getUserId().equals("")) {
            startActivity(new Intent(this.mContext, (Class<?>) LoginActivity.class));
            return true;
        }
        try {
            this.mUserOwernCarepreBeans.clear();
            int i4 = getIntent().getExtras().getInt("typeValue", 1);
            if (i4 == 4) {
                return true;
            }
            if (i4 == 5) {
                List<QuestionInfoBean> list = ProjectApp.mTiKuDaoSession.getQuestionInfoBeanDao().queryBuilder().where(QuestionInfoBeanDao.Properties.Year.eq(this.data.get(i2).getId()), QuestionInfoBeanDao.Properties.Unit.eq(this.data.get(i2).getChild().get(i3).getId())).orderAsc(QuestionInfoBeanDao.Properties.Number_number).list();
                for (int i5 = 0; i5 < list.size(); i5++) {
                    this.mListStr.add(list.get(i5).getQuestion_id() + "");
                }
                List<String> list2 = this.mListStr;
                if (list2 != null && list2.size() > 0) {
                    for (int i6 = 0; i6 < this.data.get(i2).getChild().get(i3).getObj_id().size(); i6++) {
                        forEachData(this.data.get(i2).getChild().get(i3).getObj_id().get(i6), this.mListStr);
                    }
                }
                if (this.mUserOwernCarepreBeans.size() <= 0) {
                    return true;
                }
                Collections.sort(this.mUserOwernCarepreBeans);
                int size = this.mUserOwernCarepreBeans.size();
                long[] jArr = new long[size];
                if (size < 1) {
                    return true;
                }
                for (int i7 = 0; i7 < size; i7++) {
                    jArr[i7] = Long.parseLong(this.mUserOwernCarepreBeans.get(i7).getObj_id());
                }
                Intent intent = new Intent(this.mContext, (Class<?>) YearQuestionListActivity.class);
                intent.putExtra("list", jArr);
                intent.putExtra("year", this.data.get(i2).getId());
                intent.putExtra("title", this.data.get(i2).getTitle() + SharePreferencesUtils.readStrConfig(CommonParameter.app_title, this.mContext) + "真题");
                intent.putExtra("unit", this.data.get(i2).getChild().get(i3).getId());
                intent.putExtra("isUserComm", true);
                intent.putExtra("ISPractice", true);
                startActivity(intent);
                return true;
            }
            List<QuestionInfoBean> list3 = SharePreferencesUtils.readStrConfig(CommonParameter.APP_TIKU_MARK, this.mContext, "").equals(CommonParameter.Xueshuo) ? getIntent().getExtras().getInt("typeValue", 1) == 6 ? ProjectApp.mTiKuDaoSession.getQuestionInfoBeanDao().queryBuilder().where(QuestionInfoBeanDao.Properties.Part_id.eq(this.data.get(i2).getChild().get(i3).getId()), new WhereCondition[0]).orderAsc(QuestionInfoBeanDao.Properties.Part_num_am).build().list() : ProjectApp.mTiKuDaoSession.getQuestionInfoBeanDao().queryBuilder().where(QuestionInfoBeanDao.Properties.Chapter_id.eq(this.data.get(i2).getChild().get(i3).getId()), new WhereCondition[0]).orderAsc(QuestionInfoBeanDao.Properties.S_num_xue).build().list() : getIntent().getExtras().getInt("typeValue", 1) == 6 ? ProjectApp.mTiKuDaoSession.getQuestionInfoBeanDao().queryBuilder().where(QuestionInfoBeanDao.Properties.Part_id.eq(this.data.get(i2).getChild().get(i3).getId()), new WhereCondition[0]).orderAsc(QuestionInfoBeanDao.Properties.Part_num_pm).build().list() : ProjectApp.mTiKuDaoSession.getQuestionInfoBeanDao().queryBuilder().where(QuestionInfoBeanDao.Properties.Chapter_id.eq(this.data.get(i2).getChild().get(i3).getId()), new WhereCondition[0]).orderAsc(QuestionInfoBeanDao.Properties.S_num).build().list();
            for (int i8 = 0; i8 < list3.size(); i8++) {
                this.mListStr.add(list3.get(i8).getQuestion_id() + "");
            }
            List<String> list4 = this.mListStr;
            if (list4 != null && list4.size() > 0) {
                for (int i9 = 0; i9 < this.data.get(i2).getChild().get(i3).getObj_id().size(); i9++) {
                    forEachData(this.data.get(i2).getChild().get(i3).getObj_id().get(i9), this.mListStr);
                }
            }
            if (this.mUserOwernCarepreBeans.size() <= 0) {
                return true;
            }
            Collections.sort(this.mUserOwernCarepreBeans);
            int size2 = this.mUserOwernCarepreBeans.size();
            long[] jArr2 = new long[size2];
            if (size2 < 1) {
                return true;
            }
            for (int i10 = 0; i10 < size2; i10++) {
                jArr2[i10] = Long.parseLong(this.mUserOwernCarepreBeans.get(i10).getObj_id());
            }
            Intent intent2 = new Intent(this.mContext, (Class<?>) SubjectQuestionListActivity.class);
            intent2.putExtra("list", jArr2);
            intent2.putExtra("title", "year");
            intent2.putExtra("subject_name", this.data.get(i2).getTitle());
            intent2.putExtra("chapter_name", this.data.get(i2).getChild().get(i3).getTitle());
            intent2.putExtra("chapter_id", this.data.get(i2).getChild().get(i3).getId());
            intent2.putExtra("chapter_p_id", "");
            intent2.putExtra("isUserComm", true);
            intent2.putExtra("ISPractice", true);
            intent2.putExtra("isXitong", getIntent().getExtras().getInt("typeValue", 1) == 6);
            if (SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, this.mContext).equals("20") && getIntent().getExtras().getInt("position") == 2) {
                intent2.putExtra("iszhongyibank", true);
            }
            intent2.putExtra("IsLisnxi", getIntent().getBooleanExtra("IsLisnxi", false));
            startActivity(intent2);
            return true;
        } catch (Exception e2) {
            e2.printStackTrace();
            return true;
        }
    }

    public void forEachData(String strValue, List<String> obj_id) {
        int i2 = 0;
        for (String str : obj_id) {
            if (TextUtils.equals(strValue, str)) {
                UserOwernCarepreBean userOwernCarepreBean = new UserOwernCarepreBean();
                userOwernCarepreBean.setObj_id(str);
                userOwernCarepreBean.setPostion(i2);
                this.mUserOwernCarepreBeans.add(userOwernCarepreBean);
                return;
            }
            i2++;
        }
    }

    public void getNewData() {
        AjaxParams ajaxParams = new AjaxParams();
        if (SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, this).equals(Constants.VIA_REPORT_TYPE_SHARE_TO_QQ)) {
            if (SharePreferencesUtils.readStrConfig(CommonParameter.App_Type, this.mContext, "").equals(CommonParameter.Xueshuo)) {
                ajaxParams.put("degree_type", CommonNetImpl.AM);
            } else {
                ajaxParams.put("degree_type", "pm");
            }
        }
        ajaxParams.put(SocialConstants.PARAM_SOURCE, getIntent().getExtras().getString(com.alipay.sdk.authjs.a.f3174g));
        ajaxParams.put("type", getIntent().getExtras().getString("typestr"));
        if ("40".equals(SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, this))) {
            ajaxParams.put("school_year", "" + getIntent().getExtras().getInt("position"));
        }
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.myCommentFilterUrl, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.UserOwernCommActivity.2
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                UserOwernCommActivity.this.AlertToast("请求失败");
                if (UserOwernCommActivity.this.swipe.isRefreshing()) {
                    UserOwernCommActivity.this.swipe.setRefreshing(false);
                }
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass2) s2);
                try {
                    if (UserOwernCommActivity.this.swipe.isRefreshing()) {
                        UserOwernCommActivity.this.swipe.setRefreshing(false);
                    }
                    UserOwernCommActivity.this.mUserOwnerBean = (UserOwnerBean) new Gson().fromJson(s2, UserOwnerBean.class);
                    if (!UserOwernCommActivity.this.mUserOwnerBean.getCode().equals("200")) {
                        UserOwernCommActivity userOwernCommActivity = UserOwernCommActivity.this;
                        userOwernCommActivity.AlertToast(userOwernCommActivity.mUserOwnerBean.getMessage());
                        return;
                    }
                    UserOwernCommActivity.this.data.clear();
                    UserOwernCommActivity userOwernCommActivity2 = UserOwernCommActivity.this;
                    userOwernCommActivity2.data = userOwernCommActivity2.mUserOwnerBean.getData();
                    UserOwernCommActivity userOwernCommActivity3 = UserOwernCommActivity.this;
                    UserOwernCommActivity userOwernCommActivity4 = UserOwernCommActivity.this;
                    userOwernCommActivity3.userOwernCommAdapter = new UserOwernCommAdapter(userOwernCommActivity4.mContext, userOwernCommActivity4.data, UserOwernCommActivity.this.getIntent().getExtras().getInt("typeValue", 1), UserOwernCommActivity.this.getIntent().getExtras().getString(com.alipay.sdk.authjs.a.f3174g));
                    UserOwernCommActivity.this.elv_re_live.setAdapter(UserOwernCommActivity.this.userOwernCommAdapter);
                    if (UserOwernCommActivity.this.data.size() > 0) {
                        UserOwernCommActivity.this.tv_empty.setVisibility(8);
                    } else {
                        UserOwernCommActivity.this.tv_empty.setVisibility(0);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            if (getIntent().getExtras().getString(com.alipay.sdk.authjs.a.f3174g).equals(ClientCookie.COMMENT_ATTR)) {
                setTitle("我的评论");
            } else {
                setTitle("我的点赞");
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
    public void onRefresh() {
        this.mHandler.sendEmptyMessageDelayed(272, ExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_user_owern_comm);
        this.tv_empty = (TextView) findViewById(R.id.tv_empty);
        this.elv_re_live = (ExpandableListView) findViewById(R.id.elv_re_live);
        SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.mSwipeLayput);
        this.swipe = swipeRefreshLayout;
        swipeRefreshLayout.setOnRefreshListener(this);
        if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
            this.swipe.setProgressBackgroundColorSchemeColor(ContextCompat.getColor(this, R.color.white));
            this.swipe.setColorSchemeResources(android.R.color.holo_red_light, android.R.color.holo_red_light, android.R.color.holo_red_light, android.R.color.holo_red_light);
        } else {
            this.swipe.setProgressBackgroundColorSchemeColor(ContextCompat.getColor(this, R.color.input_night_color));
            this.swipe.setColorSchemeResources(R.color.question_color_night, R.color.question_color_night, R.color.question_color_night, R.color.question_color_night);
        }
        this.swipe.post(new Runnable() { // from class: com.psychiatrygarden.activity.jq
            @Override // java.lang.Runnable
            public final void run() {
                this.f12569c.lambda$setContentView$0();
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.elv_re_live.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() { // from class: com.psychiatrygarden.activity.kq
            @Override // android.widget.ExpandableListView.OnGroupExpandListener
            public final void onGroupExpand(int i2) {
                this.f12645a.lambda$setListenerForWidget$1(i2);
            }
        });
        this.elv_re_live.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() { // from class: com.psychiatrygarden.activity.lq
            @Override // android.widget.ExpandableListView.OnGroupClickListener
            public final boolean onGroupClick(ExpandableListView expandableListView, View view, int i2, long j2) {
                return this.f12677a.lambda$setListenerForWidget$2(expandableListView, view, i2, j2);
            }
        });
        this.elv_re_live.setOnChildClickListener(new ExpandableListView.OnChildClickListener() { // from class: com.psychiatrygarden.activity.mq
            @Override // android.widget.ExpandableListView.OnChildClickListener
            public final boolean onChildClick(ExpandableListView expandableListView, View view, int i2, int i3, long j2) {
                return this.f13029a.lambda$setListenerForWidget$3(expandableListView, view, i2, i3, j2);
            }
        });
    }
}
