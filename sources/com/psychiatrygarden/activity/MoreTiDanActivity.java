package com.psychiatrygarden.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.google.gson.Gson;
import com.lxj.xpopup.XPopup;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.vip.Utils.MemInterface;
import com.psychiatrygarden.adapter.QuestionSheetListAdapter;
import com.psychiatrygarden.bean.QuestionSetListBean;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.ImageLoaderUtils;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.widget.CustomEmptyView;
import com.tencent.connect.common.Constants;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class MoreTiDanActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {
    public static ArrayList<SnsPlatform> platformsData = new ArrayList<>();
    private CustomEmptyView emptyView;
    private QuestionSheetListAdapter mDataAdapter;
    private QuestionSetListBean.DataBeanX mMoreBean;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView recyclerView;
    private TextView tv_actionbar_right;
    private String listType = "";
    private String cId = "";
    private String title = "";

    /* renamed from: com.psychiatrygarden.activity.MoreTiDanActivity$1, reason: invalid class name */
    public class AnonymousClass1 extends AjaxCallBack<String> {
        public AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSuccess$0(View view) {
            new XPopup.Builder(MoreTiDanActivity.this).asImageViewer(null, Integer.valueOf(R.drawable.tidannew), new ImageLoaderUtils()).isShowSaveButton(false).show();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSuccess$1(QuestionSetListBean.DataBeanX dataBeanX, int i2) {
            if ("2".equals(dataBeanX.getData().get(i2).getType())) {
                ProjectApp.questExamDataList.clear();
                ProjectApp.questExamList.clear();
                Intent intent = new Intent(MoreTiDanActivity.this.mContext, (Class<?>) EstimateExplainActivity.class);
                intent.putExtra("exam_id", dataBeanX.getData().get(i2).getExam_id() + "");
                intent.putExtra("question_file", dataBeanX.getData().get(i2).getQuestion_file());
                intent.putExtra("title", dataBeanX.getData().get(i2).getTitle());
                intent.putExtra("collection_id", "" + dataBeanX.getData().get(i2).getExam_id());
                MoreTiDanActivity.this.mContext.startActivity(intent);
                return;
            }
            if (dataBeanX.getData().get(i2).getSeries().equals("0")) {
                Intent intent2 = new Intent(MoreTiDanActivity.this.mContext, (Class<?>) RecdationInfoActivity.class);
                intent2.putExtra("collection_id", "" + dataBeanX.getData().get(i2).getId());
                intent2.putExtra("category_id", "" + dataBeanX.getC_id());
                intent2.putExtra("cover_img", dataBeanX.getData().get(i2).getCover_img());
                intent2.putExtra("title", "" + dataBeanX.getData().get(i2).getTitle());
                intent2.putExtra("description", "" + dataBeanX.getData().get(i2).getDescription());
                intent2.putExtra("type", dataBeanX.getData().get(i2).getType() + "");
                intent2.putExtra("is_collected", "" + dataBeanX.getData().get(i2).getIs_collected());
                intent2.putExtra("create_time", "" + dataBeanX.getData().get(i2).getCreate_time());
                intent2.putExtra("update_time", "" + dataBeanX.getData().get(i2).getUpdate_time());
                intent2.putExtra("titleLabel", "" + dataBeanX.getLabel());
                intent2.putExtra(SocializeProtocolConstants.AUTHOR, "" + dataBeanX.getData().get(i2).getAuthor());
                intent2.putExtra("author_id", "" + dataBeanX.getData().get(i2).getAuthor_id());
                intent2.putExtra("series", "" + dataBeanX.getData().get(i2).getSeries());
                intent2.putExtra("is_show_number", "" + dataBeanX.getData().get(i2).getIs_show_number());
                MoreTiDanActivity.this.mContext.startActivity(intent2);
                return;
            }
            Intent intent3 = new Intent(MoreTiDanActivity.this.mContext, (Class<?>) RecdationActivity.class);
            intent3.putExtra("collection_id", "" + dataBeanX.getData().get(i2).getId());
            intent3.putExtra("category_id", "" + dataBeanX.getC_id());
            intent3.putExtra("cover_img", dataBeanX.getData().get(i2).getCover_img());
            intent3.putExtra("title", "" + dataBeanX.getData().get(i2).getTitle());
            intent3.putExtra("description", "" + dataBeanX.getData().get(i2).getDescription());
            intent3.putExtra("type", dataBeanX.getData().get(i2).getType() + "");
            intent3.putExtra("is_collected", "" + dataBeanX.getData().get(i2).getIs_collected());
            intent3.putExtra("create_time", "" + dataBeanX.getData().get(i2).getCreate_time());
            intent3.putExtra("update_time", "" + dataBeanX.getData().get(i2).getUpdate_time());
            intent3.putExtra("titleLabel", "" + dataBeanX.getLabel());
            intent3.putExtra(SocializeProtocolConstants.AUTHOR, "" + dataBeanX.getData().get(i2).getAuthor());
            intent3.putExtra("author_id", "" + dataBeanX.getData().get(i2).getAuthor_id());
            intent3.putExtra("series", "" + dataBeanX.getData().get(i2).getSeries());
            intent3.putExtra("is_show_number", "" + dataBeanX.getData().get(i2).getIs_show_number());
            MoreTiDanActivity.this.mContext.startActivity(intent3);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSuccess$2(final QuestionSetListBean.DataBeanX dataBeanX, final int i2) {
            if (MoreTiDanActivity.this.mDataAdapter.isLogin(MoreTiDanActivity.this.mContext)) {
                MoreTiDanActivity moreTiDanActivity = MoreTiDanActivity.this;
                moreTiDanActivity.pointCount(moreTiDanActivity.mContext, Constants.VIA_REPORT_TYPE_MAKE_FRIEND);
                SharePreferencesUtils.writeStrConfig(CommonParameter.show_restore_img, dataBeanX.getData().get(i2).getShow_restore_img(), MoreTiDanActivity.this.mContext);
                if ("1".equals(dataBeanX.getData().get(i2).getRequire_interface())) {
                    AjaxParams ajaxParams = new AjaxParams();
                    ajaxParams.put(ConstantsAPI.WXWebPage.KEY_ACTIVITY_ID, dataBeanX.getData().get(i2).getActivity_id() + "");
                    if (QuestionSetListActivity.TYPE_VALUE_TI_DAN.equals(MoreTiDanActivity.this.listType)) {
                        ajaxParams.put("module_name", "question_unit");
                        ajaxParams.put("id", dataBeanX.getData().get(i2).getId());
                        ajaxParams.put("c_id", dataBeanX.getC_id());
                        MemInterface.getInstance().getMemData((Activity) MoreTiDanActivity.this.mContext, ajaxParams, false, 0);
                        MemInterface.getInstance().setmUShareListener(new MemInterface.UShareListener() { // from class: com.psychiatrygarden.activity.ud
                            @Override // com.psychiatrygarden.activity.vip.Utils.MemInterface.UShareListener
                            public final void mUShareListener() {
                                this.f13984a.lambda$onSuccess$1(dataBeanX, i2);
                            }
                        });
                        return;
                    }
                    ProjectApp.questExamDataList.clear();
                    ProjectApp.questExamList.clear();
                    Intent intent = new Intent(MoreTiDanActivity.this.mContext, (Class<?>) EstimateExplainActivity.class);
                    intent.putExtra("exam_id", dataBeanX.getData().get(i2).getExam_id() + "");
                    intent.putExtra("question_file", dataBeanX.getData().get(i2).getQuestion_file());
                    intent.putExtra("title", dataBeanX.getData().get(i2).getTitle());
                    intent.putExtra("collection_id", "" + dataBeanX.getData().get(i2).getExam_id());
                    MoreTiDanActivity.this.mContext.startActivity(intent);
                    return;
                }
                if ("2".equals(dataBeanX.getData().get(i2).getType())) {
                    ProjectApp.questExamDataList.clear();
                    ProjectApp.questExamList.clear();
                    Intent intent2 = new Intent(MoreTiDanActivity.this.mContext, (Class<?>) EstimateExplainActivity.class);
                    intent2.putExtra("exam_id", dataBeanX.getData().get(i2).getExam_id() + "");
                    intent2.putExtra("question_file", dataBeanX.getData().get(i2).getQuestion_file());
                    intent2.putExtra("title", dataBeanX.getData().get(i2).getTitle());
                    intent2.putExtra("collection_id", "" + dataBeanX.getData().get(i2).getExam_id());
                    MoreTiDanActivity.this.mContext.startActivity(intent2);
                    return;
                }
                if (dataBeanX.getData().get(i2).getSeries().equals("0")) {
                    Intent intent3 = new Intent(MoreTiDanActivity.this.mContext, (Class<?>) RecdationInfoActivity.class);
                    intent3.putExtra("collection_id", "" + dataBeanX.getData().get(i2).getId());
                    intent3.putExtra("category_id", "" + dataBeanX.getC_id());
                    intent3.putExtra("cover_img", dataBeanX.getData().get(i2).getCover_img());
                    intent3.putExtra("title", "" + dataBeanX.getData().get(i2).getTitle());
                    intent3.putExtra("description", "" + dataBeanX.getData().get(i2).getDescription());
                    intent3.putExtra("type", dataBeanX.getData().get(i2).getType() + "");
                    intent3.putExtra("is_collected", "" + dataBeanX.getData().get(i2).getIs_collected());
                    intent3.putExtra("create_time", "" + dataBeanX.getData().get(i2).getCreate_time());
                    intent3.putExtra("update_time", "" + dataBeanX.getData().get(i2).getUpdate_time());
                    intent3.putExtra("titleLabel", "" + dataBeanX.getLabel());
                    intent3.putExtra(SocializeProtocolConstants.AUTHOR, "" + dataBeanX.getData().get(i2).getAuthor());
                    intent3.putExtra("author_id", "" + dataBeanX.getData().get(i2).getAuthor_id());
                    intent3.putExtra("series", "" + dataBeanX.getData().get(i2).getSeries());
                    intent3.putExtra("is_show_number", "" + dataBeanX.getData().get(i2).getIs_show_number());
                    MoreTiDanActivity.this.mContext.startActivity(intent3);
                    return;
                }
                Intent intent4 = new Intent(MoreTiDanActivity.this.mContext, (Class<?>) RecdationActivity.class);
                intent4.putExtra("collection_id", "" + dataBeanX.getData().get(i2).getId());
                intent4.putExtra("category_id", "" + dataBeanX.getC_id());
                intent4.putExtra("cover_img", dataBeanX.getData().get(i2).getCover_img());
                intent4.putExtra("title", "" + dataBeanX.getData().get(i2).getTitle());
                intent4.putExtra("description", "" + dataBeanX.getData().get(i2).getDescription());
                intent4.putExtra("type", dataBeanX.getData().get(i2).getType() + "");
                intent4.putExtra("is_collected", "" + dataBeanX.getData().get(i2).getIs_collected());
                intent4.putExtra("create_time", "" + dataBeanX.getData().get(i2).getCreate_time());
                intent4.putExtra("update_time", "" + dataBeanX.getData().get(i2).getUpdate_time());
                intent4.putExtra("titleLabel", "" + dataBeanX.getLabel());
                intent4.putExtra(SocializeProtocolConstants.AUTHOR, "" + dataBeanX.getData().get(i2).getAuthor());
                intent4.putExtra("author_id", "" + dataBeanX.getData().get(i2).getAuthor_id());
                intent4.putExtra("series", "" + dataBeanX.getData().get(i2).getSeries());
                intent4.putExtra("is_show_number", "" + dataBeanX.getData().get(i2).getIs_show_number());
                MoreTiDanActivity.this.mContext.startActivity(intent4);
            }
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onFailure(Throwable t2, int errorNo, String strMsg) {
            super.onFailure(t2, errorNo, strMsg);
            if (MoreTiDanActivity.this.mSwipeRefreshLayout.isRefreshing()) {
                MoreTiDanActivity.this.mSwipeRefreshLayout.setRefreshing(false);
            }
            MoreTiDanActivity.this.setEmptyView(true);
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onStart() {
            super.onStart();
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onSuccess(String s2) {
            try {
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if (jSONObject.optInt("code") == 200) {
                        MoreTiDanActivity.this.mMoreBean = (QuestionSetListBean.DataBeanX) new Gson().fromJson(jSONObject.optString("data"), QuestionSetListBean.DataBeanX.class);
                        if (MoreTiDanActivity.this.mMoreBean == null || MoreTiDanActivity.this.mMoreBean.getData() == null || MoreTiDanActivity.this.mMoreBean.getData().isEmpty()) {
                            MoreTiDanActivity.this.setEmptyView(false);
                        } else {
                            MoreTiDanActivity.this.emptyView.setVisibility(8);
                        }
                        ArrayList arrayList = new ArrayList();
                        if (!TextUtils.isEmpty(MoreTiDanActivity.this.mMoreBean.getLabel())) {
                            MoreTiDanActivity moreTiDanActivity = MoreTiDanActivity.this;
                            moreTiDanActivity.setTitle(moreTiDanActivity.mMoreBean.getLabel());
                        }
                        arrayList.add(MoreTiDanActivity.this.mMoreBean);
                        MoreTiDanActivity.this.tv_actionbar_right.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.vd
                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                this.f14026c.lambda$onSuccess$0(view);
                            }
                        });
                        MoreTiDanActivity.this.mDataAdapter = new QuestionSheetListAdapter(arrayList, true);
                        MoreTiDanActivity.this.recyclerView.setAdapter(MoreTiDanActivity.this.mDataAdapter);
                        MoreTiDanActivity.this.mDataAdapter.setOnItemChildClickListenr(new QuestionSheetListAdapter.OnItemChildClickListenr() { // from class: com.psychiatrygarden.activity.wd
                            @Override // com.psychiatrygarden.adapter.QuestionSheetListAdapter.OnItemChildClickListenr
                            public final void onItemChildClickMethod(QuestionSetListBean.DataBeanX dataBeanX, int i2) {
                                this.f14145a.lambda$onSuccess$2(dataBeanX, i2);
                            }
                        });
                    } else {
                        MoreTiDanActivity.this.setEmptyView(true);
                    }
                    if (!MoreTiDanActivity.this.mSwipeRefreshLayout.isRefreshing()) {
                        return;
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                    MoreTiDanActivity.this.setEmptyView(true);
                    if (!MoreTiDanActivity.this.mSwipeRefreshLayout.isRefreshing()) {
                        return;
                    }
                }
                MoreTiDanActivity.this.mSwipeRefreshLayout.setRefreshing(false);
            } catch (Throwable th) {
                if (MoreTiDanActivity.this.mSwipeRefreshLayout.isRefreshing()) {
                    MoreTiDanActivity.this.mSwipeRefreshLayout.setRefreshing(false);
                }
                throw th;
            }
        }
    }

    public static void gotoMoreTiDanActivity(Context context, String cid, String type, String title) {
        Intent intent = new Intent(context, (Class<?>) MoreTiDanActivity.class);
        intent.putExtra("c_id", cid);
        intent.putExtra("titleLabel", title);
        intent.putExtra(QuestionSetListActivity.TYPE_LIST_FLAG, type);
        context.startActivity(intent);
    }

    private void initPlatforms() {
        platformsData.clear();
        ArrayList<SnsPlatform> arrayList = platformsData;
        SHARE_MEDIA share_media = SHARE_MEDIA.QQ;
        arrayList.add(share_media.toSnsPlatform());
        platformsData.add(share_media.toSnsPlatform());
        platformsData.add(SHARE_MEDIA.WEIXIN.toSnsPlatform());
        platformsData.add(SHARE_MEDIA.WEIXIN_CIRCLE.toSnsPlatform());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0() {
        this.mSwipeRefreshLayout.setRefreshing(true);
        getListData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setEmptyView(boolean isError) {
        if (isError) {
            this.emptyView.setLoadFileResUi(this);
        } else {
            this.emptyView.uploadEmptyViewResUi();
        }
        this.emptyView.setVisibility(0);
    }

    public void getListData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("c_id", this.cId);
        YJYHttpUtils.get(this.mContext, QuestionSetListActivity.TYPE_VALUE_TI_DAN.equals(this.listType) ? NetworkRequestsURL.mmoreUrl : NetworkRequestsURL.mExamListSetMoreUrl, ajaxParams, new AnonymousClass1());
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.emptyView = (CustomEmptyView) findViewById(R.id.emptyView);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        this.recyclerView = recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this.mContext));
        this.tv_actionbar_right = (TextView) findViewById(R.id.tv_actionbar_right);
        this.mBtnActionbarRight.setVisibility(8);
        this.tv_actionbar_right.setText("我要上传");
        this.tv_actionbar_right.setVisibility(8);
        SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swip);
        this.mSwipeRefreshLayout = swipeRefreshLayout;
        swipeRefreshLayout.setOnRefreshListener(this);
        if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
            this.mSwipeRefreshLayout.setProgressBackgroundColorSchemeColor(ContextCompat.getColor(this.mContext, R.color.white));
            this.mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_red_light, android.R.color.holo_red_light, android.R.color.holo_red_light, android.R.color.holo_red_light);
        } else {
            this.mSwipeRefreshLayout.setProgressBackgroundColorSchemeColor(ContextCompat.getColor(this.mContext, R.color.input_night_color));
            this.mSwipeRefreshLayout.setColorSchemeResources(R.color.question_color_night, R.color.question_color_night, R.color.question_color_night, R.color.question_color_night);
        }
        this.mSwipeRefreshLayout.post(new Runnable() { // from class: com.psychiatrygarden.activity.td
            @Override // java.lang.Runnable
            public final void run() {
                this.f13950c.lambda$init$0();
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
    public void onRefresh() {
        if (!this.mSwipeRefreshLayout.isRefreshing()) {
            this.mSwipeRefreshLayout.setRefreshing(true);
        }
        getListData();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_more_ti_dan);
        Intent intent = getIntent();
        if (intent != null) {
            this.listType = intent.getStringExtra(QuestionSetListActivity.TYPE_LIST_FLAG);
            this.cId = intent.getStringExtra("c_id");
            this.title = intent.getStringExtra("titleLabel");
        }
        setTitle(this.title);
        initPlatforms();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}
