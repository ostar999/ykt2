package com.psychiatrygarden.fragmenthome;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.Gson;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.EstimateExplainActivity;
import com.psychiatrygarden.activity.QuestionSetListActivity;
import com.psychiatrygarden.activity.RecdationActivity;
import com.psychiatrygarden.activity.RecdationInfoActivity;
import com.psychiatrygarden.activity.vip.Utils.MemInterface;
import com.psychiatrygarden.adapter.QuestionSheetListAdapter;
import com.psychiatrygarden.baseview.BaseFragment;
import com.psychiatrygarden.baseview.ViewHolder;
import com.psychiatrygarden.bean.QuestionSetListBean;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.EventBusConstant;
import com.psychiatrygarden.utils.UrlsConfig;
import com.psychiatrygarden.widget.CustomEmptyView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tencent.connect.common.Constants;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.List;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

/* loaded from: classes5.dex */
public class QuestionSetListFragment extends BaseFragment {
    private CustomEmptyView emptyView;
    private QuestionSheetListAdapter mDataAdapter;
    private QuestionSetListBean mQuestionSetListBean;
    private SmartRefreshLayout mSwipeRefreshLayout;
    private RecyclerView recyclerView;
    private String type;
    private List<QuestionSetListBean.DataBeanX> data = new ArrayList();
    private final UMShareListener umShareListener = new UMShareListener() { // from class: com.psychiatrygarden.fragmenthome.QuestionSetListFragment.2
        @Override // com.umeng.socialize.UMShareListener
        public void onCancel(SHARE_MEDIA arg0) {
        }

        @Override // com.umeng.socialize.UMShareListener
        public void onError(SHARE_MEDIA arg0, Throwable arg1) {
            QuestionSetListFragment.this.AlertToast("分享成功");
        }

        @Override // com.umeng.socialize.UMShareListener
        public void onResult(SHARE_MEDIA arg0) {
            QuestionSetListFragment.this.AlertToast("分享成功");
        }

        @Override // com.umeng.socialize.UMShareListener
        public void onStart(SHARE_MEDIA share_media) {
        }
    };

    /* renamed from: com.psychiatrygarden.fragmenthome.QuestionSetListFragment$1, reason: invalid class name */
    public class AnonymousClass1 extends AjaxCallBack<String> {
        public AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSuccess$0(QuestionSetListBean.DataBeanX dataBeanX, int i2) {
            if ("2".equals(dataBeanX.getData().get(i2).getType())) {
                ProjectApp.questExamDataList.clear();
                ProjectApp.questExamList.clear();
                Intent intent = new Intent(((BaseFragment) QuestionSetListFragment.this).mContext, (Class<?>) EstimateExplainActivity.class);
                intent.putExtra("exam_id", dataBeanX.getData().get(i2).getExam_id() + "");
                intent.putExtra("question_file", dataBeanX.getData().get(i2).getQuestion_file());
                intent.putExtra("title", dataBeanX.getData().get(i2).getTitle());
                intent.putExtra("collection_id", "" + dataBeanX.getData().get(i2).getExam_id());
                ((BaseFragment) QuestionSetListFragment.this).mContext.startActivity(intent);
                return;
            }
            if (dataBeanX.getData().get(i2).getSeries().equals("0")) {
                Intent intent2 = new Intent(((BaseFragment) QuestionSetListFragment.this).mContext, (Class<?>) RecdationInfoActivity.class);
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
                ((BaseFragment) QuestionSetListFragment.this).mContext.startActivity(intent2);
                return;
            }
            Intent intent3 = new Intent(((BaseFragment) QuestionSetListFragment.this).mContext, (Class<?>) RecdationActivity.class);
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
            ((BaseFragment) QuestionSetListFragment.this).mContext.startActivity(intent3);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSuccess$1(final QuestionSetListBean.DataBeanX dataBeanX, final int i2) {
            if (QuestionSetListFragment.this.mDataAdapter.isLogin(((BaseFragment) QuestionSetListFragment.this).mContext)) {
                QuestionSetListFragment questionSetListFragment = QuestionSetListFragment.this;
                questionSetListFragment.pointCount(((BaseFragment) questionSetListFragment).mContext, Constants.VIA_REPORT_TYPE_MAKE_FRIEND);
                SharePreferencesUtils.writeStrConfig(CommonParameter.show_restore_img, dataBeanX.getData().get(i2).getShow_restore_img(), ((BaseFragment) QuestionSetListFragment.this).mContext);
                if ("1".equals(dataBeanX.getData().get(i2).getRequire_interface())) {
                    AjaxParams ajaxParams = new AjaxParams();
                    ajaxParams.put(ConstantsAPI.WXWebPage.KEY_ACTIVITY_ID, dataBeanX.getData().get(i2).getActivity_id() + "");
                    if (QuestionSetListActivity.TYPE_VALUE_TI_DAN.equals(QuestionSetListFragment.this.type)) {
                        ajaxParams.put("module_name", "question_unit");
                        ajaxParams.put("id", dataBeanX.getData().get(i2).getId());
                        ajaxParams.put("c_id", dataBeanX.getC_id());
                        MemInterface.getInstance().getMemData((Activity) ((BaseFragment) QuestionSetListFragment.this).mContext, ajaxParams, false, 0);
                        MemInterface.getInstance().setmUShareListener(new MemInterface.UShareListener() { // from class: com.psychiatrygarden.fragmenthome.vb
                            @Override // com.psychiatrygarden.activity.vip.Utils.MemInterface.UShareListener
                            public final void mUShareListener() {
                                this.f16075a.lambda$onSuccess$0(dataBeanX, i2);
                            }
                        });
                        return;
                    }
                    ProjectApp.questExamDataList.clear();
                    ProjectApp.questExamList.clear();
                    Intent intent = new Intent(((BaseFragment) QuestionSetListFragment.this).mContext, (Class<?>) EstimateExplainActivity.class);
                    intent.putExtra("exam_id", dataBeanX.getData().get(i2).getExam_id() + "");
                    intent.putExtra("question_file", dataBeanX.getData().get(i2).getQuestion_file());
                    intent.putExtra("title", dataBeanX.getData().get(i2).getTitle());
                    intent.putExtra("collection_id", "" + dataBeanX.getData().get(i2).getExam_id());
                    ((BaseFragment) QuestionSetListFragment.this).mContext.startActivity(intent);
                    return;
                }
                if ("2".equals(dataBeanX.getData().get(i2).getType())) {
                    ProjectApp.questExamDataList.clear();
                    ProjectApp.questExamList.clear();
                    Intent intent2 = new Intent(((BaseFragment) QuestionSetListFragment.this).mContext, (Class<?>) EstimateExplainActivity.class);
                    intent2.putExtra("exam_id", dataBeanX.getData().get(i2).getExam_id() + "");
                    intent2.putExtra("question_file", dataBeanX.getData().get(i2).getQuestion_file());
                    intent2.putExtra("title", dataBeanX.getData().get(i2).getTitle());
                    intent2.putExtra("collection_id", "" + dataBeanX.getData().get(i2).getExam_id());
                    ((BaseFragment) QuestionSetListFragment.this).mContext.startActivity(intent2);
                    return;
                }
                if ("0".equals(dataBeanX.getData().get(i2).getSeries())) {
                    Intent intent3 = new Intent(((BaseFragment) QuestionSetListFragment.this).mContext, (Class<?>) RecdationInfoActivity.class);
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
                    ((BaseFragment) QuestionSetListFragment.this).mContext.startActivity(intent3);
                    return;
                }
                Intent intent4 = new Intent(((BaseFragment) QuestionSetListFragment.this).mContext, (Class<?>) RecdationActivity.class);
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
                ((BaseFragment) QuestionSetListFragment.this).mContext.startActivity(intent4);
            }
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onFailure(Throwable t2, int errorNo, String strMsg) {
            super.onFailure(t2, errorNo, strMsg);
            if (CommonUtil.isNetworkConnected(((BaseFragment) QuestionSetListFragment.this).mContext)) {
                QuestionSetListFragment.this.setEmptyView(false);
            } else {
                QuestionSetListFragment.this.setEmptyView(true);
            }
            QuestionSetListFragment.this.recyclerView.setVisibility(8);
            QuestionSetListFragment.this.mSwipeRefreshLayout.finishRefresh();
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onStart() {
            super.onStart();
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onSuccess(String s2) {
            try {
                try {
                    QuestionSetListFragment.this.mQuestionSetListBean = (QuestionSetListBean) new Gson().fromJson(s2, QuestionSetListBean.class);
                    if (QuestionSetListFragment.this.mQuestionSetListBean.getCode() == 200) {
                        QuestionSetListFragment questionSetListFragment = QuestionSetListFragment.this;
                        questionSetListFragment.data = questionSetListFragment.mQuestionSetListBean.getData();
                        ArrayList arrayList = new ArrayList();
                        for (int i2 = 0; i2 < QuestionSetListFragment.this.data.size(); i2++) {
                            if (((QuestionSetListBean.DataBeanX) QuestionSetListFragment.this.data.get(i2)).getData().size() > 0) {
                                arrayList.add((QuestionSetListBean.DataBeanX) QuestionSetListFragment.this.data.get(i2));
                            }
                        }
                        if (arrayList.size() == 0) {
                            QuestionSetListFragment.this.setEmptyView(false);
                            QuestionSetListFragment.this.recyclerView.setVisibility(8);
                            return;
                        }
                        QuestionSetListFragment.this.emptyView.setVisibility(8);
                        QuestionSetListFragment.this.recyclerView.setVisibility(0);
                        QuestionSetListFragment.this.mDataAdapter = new QuestionSheetListAdapter(arrayList);
                        QuestionSetListFragment.this.mDataAdapter.setTiDan(QuestionSetListActivity.TYPE_VALUE_TI_DAN.equals(QuestionSetListFragment.this.type));
                        QuestionSetListFragment.this.recyclerView.setAdapter(QuestionSetListFragment.this.mDataAdapter);
                        QuestionSetListFragment.this.mDataAdapter.setOnItemChildClickListenr(new QuestionSheetListAdapter.OnItemChildClickListenr() { // from class: com.psychiatrygarden.fragmenthome.ub
                            @Override // com.psychiatrygarden.adapter.QuestionSheetListAdapter.OnItemChildClickListenr
                            public final void onItemChildClickMethod(QuestionSetListBean.DataBeanX dataBeanX, int i3) {
                                this.f16047a.lambda$onSuccess$1(dataBeanX, i3);
                            }
                        });
                    } else {
                        QuestionSetListFragment.this.setEmptyView(true);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                    QuestionSetListFragment.this.setEmptyView(true);
                }
            } finally {
                QuestionSetListFragment.this.mSwipeRefreshLayout.finishRefresh();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$0(RefreshLayout refreshLayout) {
        getListData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$1(View view) {
        getListData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onLazyInitView$2() {
        this.mSwipeRefreshLayout.autoRefresh();
    }

    public static QuestionSetListFragment newInstance() {
        Bundle bundle = new Bundle();
        QuestionSetListFragment questionSetListFragment = new QuestionSetListFragment();
        questionSetListFragment.setArguments(bundle);
        return questionSetListFragment;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setEmptyView(boolean isError) {
        if (isError) {
            this.emptyView.setLoadFileResUi(getActivity());
        } else {
            this.emptyView.uploadEmptyViewResUi();
        }
        this.emptyView.setVisibility(0);
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public int getLayoutId() {
        return R.layout.fragment_setlist;
    }

    public void getListData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("identity_id", "" + SharePreferencesUtils.readStrConfig(CommonParameter.identity_id, getActivity()));
        YJYHttpUtils.get(this.mContext, QuestionSetListActivity.TYPE_VALUE_TI_DAN.equals(this.type) ? NetworkRequestsURL.mListSetUrl : NetworkRequestsURL.mExamListSetUrl, ajaxParams, new AnonymousClass1());
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void initViews(ViewHolder holder, View root) {
        Bundle arguments = getArguments();
        if (arguments != null) {
            this.type = arguments.getString(QuestionSetListActivity.TYPE_LIST_FLAG);
        }
        RecyclerView recyclerView = (RecyclerView) holder.get(R.id.recyclerView);
        this.recyclerView = recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.mSwipeRefreshLayout = (SmartRefreshLayout) holder.get(R.id.swip);
        this.emptyView = (CustomEmptyView) holder.get(R.id.iv_network_load_fail);
        this.mSwipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() { // from class: com.psychiatrygarden.fragmenthome.rb
            @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
            public final void onRefresh(RefreshLayout refreshLayout) {
                this.f15970c.lambda$initViews$0(refreshLayout);
            }
        });
        this.emptyView.setOnReloadData(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.sb
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15988c.lambda$initViews$1(view);
            }
        });
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void onEventMainThread(final String str) {
        if (str.equals("BuySuccess") || str.equals(EventBusConstant.EVENT_QUESTION_SHEET_COLLECT)) {
            getListData();
        }
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportFragment, cn.webdemo.com.supporfragment.ISupportFragment
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        this.mSwipeRefreshLayout.post(new Runnable() { // from class: com.psychiatrygarden.fragmenthome.tb
            @Override // java.lang.Runnable
            public final void run() {
                this.f16014c.lambda$onLazyInitView$2();
            }
        });
    }

    public void shareAppControl(int i2) {
        String str = UrlsConfig.shareUrl;
        String str2 = ProjectApp.instance().getResources().getString(R.string.app_name) + "“执业医师”估分系统开通！全网首发！";
        UMImage uMImage = new UMImage(getActivity(), UrlsConfig.shareImageUrl);
        UMWeb uMWeb = new UMWeb(str);
        uMWeb.setTitle(str2);
        uMWeb.setDescription("2019执业医师真题出炉，考点还原与答案解析陆续上线！");
        uMWeb.setThumb(uMImage);
        new ShareAction(getActivity()).withMedia(uMWeb).setPlatform(BaseFragment.platformsData.get(i2).mPlatform).setCallback(this.umShareListener).share();
    }
}
