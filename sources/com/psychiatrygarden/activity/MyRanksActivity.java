package com.psychiatrygarden.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.aliyun.vod.log.struct.AliyunLogKey;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.psychiatrygarden.activity.purchase.adapter.CommAdapter;
import com.psychiatrygarden.activity.purchase.adapter.ViewHolder;
import com.psychiatrygarden.bean.MyRankBean;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.utils.LogUtils;
import com.psychiatrygarden.widget.SpringProgressView;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.List;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class MyRanksActivity extends BaseActivity {
    public CommAdapter<MyRankBean> adapter;
    TextView mIvAllJifen;
    ImageView mIvPhoto;
    public ListView mListView;
    public List<MyRankBean> mMyRankBeanList;
    SpringProgressView mSproGrade;
    TextView mTvDengji;
    TextView mTvShengjiJifen;
    TextView mTvTodayFenshu;
    private String mUrlDengji = "";

    @SuppressLint({"NonConstantResourceId"})
    View.OnClickListener mOnclick = new View.OnClickListener() { // from class: com.psychiatrygarden.activity.ge
        @Override // android.view.View.OnClickListener
        public final void onClick(View view) {
            this.f12440c.lambda$new$0(view);
        }
    };

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        int id = view.getId();
        if (id != R.id.rll_dengji) {
            if (id != R.id.rss_jinri) {
                return;
            }
            goActivity(TodayPointsActivity.class);
        } else {
            Intent intent = new Intent(this.mContext, (Class<?>) WebViewActivity.class);
            intent.putExtra("url", this.mUrlDengji);
            intent.putExtra("title", "等级介绍");
            startActivity(intent);
        }
    }

    private void mGetUserScoreInfo() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("user_id", UserConfig.getUserId());
        YJYHttpUtils.get(this.mContext, "", ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.MyRanksActivity.1

            /* renamed from: com.psychiatrygarden.activity.MyRanksActivity$1$2, reason: invalid class name */
            public class AnonymousClass2 extends CommAdapter<MyRankBean> {
                public AnonymousClass2(List mData, Context mcontext, int layoutId) {
                    super(mData, mcontext, layoutId);
                }

                /* JADX INFO: Access modifiers changed from: private */
                public /* synthetic */ void lambda$convert$0(View view) {
                    MyRanksActivity.this.goActivity(MyRegisterCodeActivity.class);
                }

                /* JADX INFO: Access modifiers changed from: private */
                public /* synthetic */ void lambda$convert$1(View view) {
                    MyRanksActivity.this.goActivity(RedBookCodeActivity.class);
                }

                @Override // com.psychiatrygarden.activity.purchase.adapter.CommAdapter
                public void convert(ViewHolder vHolder, MyRankBean t2, int position) {
                    if (t2.getModule_type().equals("inv_user")) {
                        vHolder.setText(R.id.tv_title, Html.fromHtml(t2.getTitle().replace("邀请", "<font  color='#188EEF'>邀请</font>")));
                        vHolder.getView(R.id.tv_title).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.he
                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                this.f12475c.lambda$convert$0(view);
                            }
                        });
                    } else if (t2.getModule_type().equals("red_book")) {
                        vHolder.setText(R.id.tv_title, Html.fromHtml(t2.getTitle().replace("输入", "<font  color='#188EEF'>输入</font>")));
                        vHolder.getView(R.id.tv_title).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.ie
                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                this.f12508c.lambda$convert$1(view);
                            }
                        });
                    } else {
                        vHolder.setText(R.id.tv_title, t2.getTitle());
                    }
                    vHolder.setText(R.id.tv_fenshu, Html.fromHtml(t2.getScore().replace("分", "<font  color='#bababa'>分</font>")));
                }
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                MyRanksActivity.this.hideProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                MyRanksActivity.this.showProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String t2) {
                super.onSuccess((AnonymousClass1) t2);
                try {
                    LogUtils.e("sssss", t2);
                    JSONObject jSONObject = new JSONObject(t2);
                    if (jSONObject.optString("code").equals("1")) {
                        JSONObject jSONObject2 = new JSONObject(jSONObject.optString("data"));
                        MyRanksActivity.this.mUrlDengji = jSONObject2.optString("url");
                        MyRanksActivity.this.mSproGrade.setMaxErrRightCount(100.0f, 0.0f, Float.parseFloat(jSONObject2.optString("diff_score_percentage")) * 100.0f);
                        GlideUtils.loadImage(MyRanksActivity.this.mContext, UserConfig.getInstance().getUser().getAvatar(), MyRanksActivity.this.mIvPhoto);
                        MyRanksActivity.this.mTvShengjiJifen.setText(jSONObject2.optString("diff_score"));
                        MyRanksActivity.this.mIvAllJifen.setText(jSONObject2.optString("my_score"));
                        MyRanksActivity.this.mTvDengji.setText(jSONObject2.optString(AliyunLogKey.KEY_LOG_VERSION));
                        if ("+0".equals(jSONObject2.optString("tody_score"))) {
                            MyRanksActivity.this.mTvTodayFenshu.setVisibility(8);
                        } else {
                            MyRanksActivity.this.mTvTodayFenshu.setVisibility(0);
                            MyRanksActivity.this.mTvTodayFenshu.setText(jSONObject2.optString("tody_score"));
                        }
                        MyRanksActivity.this.mMyRankBeanList = (List) new Gson().fromJson(jSONObject2.optString("how_to_get"), new TypeToken<List<MyRankBean>>() { // from class: com.psychiatrygarden.activity.MyRanksActivity.1.1
                        }.getType());
                        MyRanksActivity myRanksActivity = MyRanksActivity.this;
                        MyRanksActivity myRanksActivity2 = MyRanksActivity.this;
                        myRanksActivity.adapter = new AnonymousClass2(myRanksActivity2.mMyRankBeanList, myRanksActivity2.mContext, R.layout.myranklist);
                        MyRanksActivity myRanksActivity3 = MyRanksActivity.this;
                        myRanksActivity3.mListView.setAdapter((ListAdapter) myRanksActivity3.adapter);
                    } else {
                        MyRanksActivity.this.AlertToast(jSONObject.optString("message"));
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                MyRanksActivity.this.hideProgressDialog();
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.mListView = (ListView) findViewById(R.id.listView1);
        View viewInflate = LayoutInflater.from(this.mContext).inflate(R.layout.activity_myranks_top, (ViewGroup) null);
        this.mIvPhoto = (ImageView) viewInflate.findViewById(R.id.iv_photo);
        this.mSproGrade = (SpringProgressView) viewInflate.findViewById(R.id.spro_grade);
        this.mTvTodayFenshu = (TextView) viewInflate.findViewById(R.id.tv_today_fenshu);
        this.mTvShengjiJifen = (TextView) viewInflate.findViewById(R.id.tv_shengji_jifen);
        this.mIvAllJifen = (TextView) viewInflate.findViewById(R.id.iv_all_jifen);
        this.mTvDengji = (TextView) viewInflate.findViewById(R.id.tv_dengji);
        View viewInflate2 = LayoutInflater.from(this.mContext).inflate(R.layout.activity_myranks_bottom, (ViewGroup) null);
        this.mListView.addHeaderView(viewInflate);
        this.mListView.addFooterView(viewInflate2);
        this.mMyRankBeanList = new ArrayList();
        mGetUserScoreInfo();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setTitle("我的等级");
        setContentView(R.layout.activity_myranks);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        findViewById(R.id.rss_jinri).setOnClickListener(this.mOnclick);
        findViewById(R.id.rll_dengji).setOnClickListener(this.mOnclick);
    }
}
