package com.psychiatrygarden.activity.mine.cutquestion;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.aliyun.vod.common.utils.UriUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.node.BaseNode;
import com.google.gson.Gson;
import com.lxj.xpopup.XPopup;
import com.psychiatrygarden.activity.mine.cutquestion.MyCutQuestionAdp;
import com.psychiatrygarden.activity.online.AnswerSheetActivity;
import com.psychiatrygarden.activity.online.bean.QuestionBankNewBean;
import com.psychiatrygarden.activity.online.bean.QuestionBankNewFristBean;
import com.psychiatrygarden.activity.online.bean.QuestionBankNewSecondBean;
import com.psychiatrygarden.baseview.BaseFragment;
import com.psychiatrygarden.baseview.ViewHolder;
import com.psychiatrygarden.bean.MyCutQuestionEditEvent;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.event.RefreshCutQuestionEvent;
import com.psychiatrygarden.fragmenthome.knowledge.KnowledgeQuestionListFragment;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.interfaceclass.LocalBroadcastManager;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.widget.CommonLoadingPop;
import com.psychiatrygarden.widget.CustomEmptyView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;
import java.util.ArrayList;
import java.util.List;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;

/* loaded from: classes5.dex */
public class MyCutQuestionFragment extends BaseFragment implements MyCutQuestionAdp.JumpToQList {
    private CustomEmptyView emptyView;
    private boolean loadDataSuccess;
    private CommonLoadingPop loadingPop;
    private MyCutQuestionAdp mAdapter;
    private String mCategory;
    private String mIdentityId;
    private View mLoadDataFailView;
    private String mModuleType;
    private RecyclerView mRecyclerView;
    private SmartRefreshLayout mRefresh;
    public List<QuestionBankNewBean.DataBean> dataList = new ArrayList();
    private String level1Id = "";

    /* JADX INFO: Access modifiers changed from: private */
    public void dialogDismiss() {
        CommonLoadingPop commonLoadingPop = this.loadingPop;
        if (commonLoadingPop != null) {
            commonLoadingPop.dismiss();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: getDataList, reason: merged with bridge method [inline-methods] and merged with bridge method [inline-methods] */
    public void lambda$setUserVisibleHint$0() throws JSONException {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("identity_id", this.mIdentityId);
        ajaxParams.put("user_id", UserConfig.getUserId());
        ajaxParams.put("module_type", this.mModuleType);
        try {
            JSONArray jSONArray = new JSONArray(SharePreferencesUtils.readStrConfig(CommonParameter.SEARCH_CUT_QUESTION_ID + SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, this.mContext) + this.mCategory, getActivity(), new JSONArray().toString()));
            JSONArray jSONArray2 = new JSONArray();
            for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                String strOptString = jSONArray.optJSONObject(i2).optString("field");
                String strOptString2 = jSONArray.optJSONObject(i2).optString("id");
                if (!strOptString.equals("pattern") && !strOptString2.equals("-1")) {
                    if (strOptString2.equals("free_year")) {
                        jSONArray.getJSONObject(i2).put("id", jSONArray.optJSONObject(i2).optString("free_year"));
                    }
                    jSONArray2.put(jSONArray.get(i2));
                }
            }
            ajaxParams.put("search_where", jSONArray2.toString());
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        showDialog();
        YJYHttpUtils.post(getActivity(), NetworkRequestsURL.myCutQuestionList, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.mine.cutquestion.MyCutQuestionFragment.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                MyCutQuestionFragment.this.mRefresh.finishRefresh(false);
                MyCutQuestionFragment.this.setEmptyView(true);
                MyCutQuestionFragment.this.dialogDismiss();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass1) s2);
                MyCutQuestionFragment.this.loadDataSuccess = true;
                MyCutQuestionFragment.this.dialogDismiss();
                try {
                    QuestionBankNewBean questionBankNewBean = (QuestionBankNewBean) new Gson().fromJson(s2, QuestionBankNewBean.class);
                    MyCutQuestionFragment.this.mRefresh.finishRefresh("200".equals(questionBankNewBean.getCode()));
                    if (!"200".equals(questionBankNewBean.getCode())) {
                        if ("202".equals(questionBankNewBean.getCode())) {
                            MyCutQuestionFragment.this.setEmptyView(true);
                            return;
                        }
                        return;
                    }
                    Intent intent = new Intent("isShowEditCut");
                    intent.putExtra("identityId", MyCutQuestionFragment.this.mIdentityId);
                    intent.putExtra("isShowEdit", questionBankNewBean.getData().size() > 0);
                    EventBus.getDefault().post(new MyCutQuestionEditEvent(MyCutQuestionFragment.this.mIdentityId, !questionBankNewBean.getData().isEmpty(), false));
                    LocalBroadcastManager.getInstance(MyCutQuestionFragment.this.getContext()).sendBroadcast(intent);
                    if (questionBankNewBean.getData() == null || questionBankNewBean.getData().size() <= 0) {
                        MyCutQuestionFragment.this.mAdapter.setList(new ArrayList());
                        MyCutQuestionFragment.this.setEmptyView(false);
                        return;
                    }
                    MyCutQuestionFragment.this.dataList = questionBankNewBean.getData();
                    for (int i3 = 0; i3 < MyCutQuestionFragment.this.dataList.size(); i3++) {
                        ArrayList arrayList = new ArrayList();
                        if (MyCutQuestionFragment.this.dataList.get(i3).getChildren() != null && MyCutQuestionFragment.this.dataList.get(i3).getChildren().size() > 0) {
                            for (int i4 = 0; i4 < MyCutQuestionFragment.this.dataList.get(i3).getChildren().size(); i4++) {
                                MyCutQuestionFragment.this.dataList.get(i3).getChildren().get(i4).setPrimary_p_id(MyCutQuestionFragment.this.dataList.get(i3).getChapter_id() + "");
                                MyCutQuestionFragment.this.dataList.get(i3).getChildren().get(i4).setGroupPosition(i3);
                                MyCutQuestionFragment.this.dataList.get(i3).getChildren().get(i4).setParent_title(MyCutQuestionFragment.this.dataList.get(i3).getTitle());
                                arrayList.add(MyCutQuestionFragment.this.dataList.get(i3).getChildren().get(i4));
                            }
                            if (MyCutQuestionFragment.this.dataList.get(i3).getChildIds().size() <= 0) {
                                MyCutQuestionFragment.this.dataList.get(i3).setIsSelected(0);
                            } else if (MyCutQuestionFragment.this.dataList.get(i3).getChildIds().size() == MyCutQuestionFragment.this.dataList.get(i3).getChildren().size()) {
                                MyCutQuestionFragment.this.dataList.get(i3).setIsSelected(1);
                            } else {
                                MyCutQuestionFragment.this.dataList.get(i3).setIsSelected(2);
                            }
                        }
                        MyCutQuestionFragment.this.dataList.get(i3).setChildren(arrayList);
                    }
                    MyCutQuestionAdp myCutQuestionAdp = MyCutQuestionFragment.this.mAdapter;
                    MyCutQuestionFragment myCutQuestionFragment = MyCutQuestionFragment.this;
                    myCutQuestionAdp.setList(myCutQuestionFragment.getEntity(myCutQuestionFragment.dataList));
                } catch (Exception e3) {
                    e3.printStackTrace();
                    MyCutQuestionFragment.this.setEmptyView(true);
                    MyCutQuestionFragment.this.mRefresh.finishRefresh(false);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public List<BaseNode> getEntity(List<QuestionBankNewBean.DataBean> dataList) {
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < dataList.size(); i2++) {
            ArrayList arrayList2 = new ArrayList();
            for (int i3 = 0; i3 < dataList.get(i2).getChildren().size(); i3++) {
                arrayList2.add(new QuestionBankNewSecondBean(dataList.get(i2).getChildren().get(i3)));
            }
            QuestionBankNewFristBean questionBankNewFristBean = new QuestionBankNewFristBean(arrayList2, dataList.get(i2));
            try {
                if (!this.mAdapter.getData().isEmpty() && (this.mAdapter.getData().get(i2) instanceof QuestionBankNewFristBean)) {
                    questionBankNewFristBean.setExpanded(((QuestionBankNewFristBean) this.mAdapter.getData().get(i2)).getIsExpanded());
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            arrayList.add(questionBankNewFristBean);
        }
        return arrayList;
    }

    private void initEmptyView() {
        CustomEmptyView customEmptyView = new CustomEmptyView(getActivity(), 0, "暂无数据");
        this.emptyView = customEmptyView;
        customEmptyView.setOnReloadData(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.mine.cutquestion.t
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) throws JSONException {
                this.f12819c.lambda$initEmptyView$4(view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initEmptyView$4(View view) throws JSONException {
        lambda$setUserVisibleHint$0();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$2(View view) {
        this.mRefresh.autoRefreshAnimationOnly();
        new Handler().postDelayed(new Runnable() { // from class: com.psychiatrygarden.activity.mine.cutquestion.q
            @Override // java.lang.Runnable
            public final void run() throws JSONException {
                this.f12816c.lambda$initViews$1();
            }
        }, 500L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$3(RefreshLayout refreshLayout) throws JSONException {
        lambda$setUserVisibleHint$0();
    }

    public static MyCutQuestionFragment newInstance() {
        Bundle bundle = new Bundle();
        MyCutQuestionFragment myCutQuestionFragment = new MyCutQuestionFragment();
        myCutQuestionFragment.setArguments(bundle);
        return myCutQuestionFragment;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setEmptyView(boolean isError) {
        if (isError) {
            this.emptyView.setLoadFileResUi(getActivity());
            this.mAdapter.setEmptyView(this.emptyView);
        } else {
            this.emptyView.uploadEmptyViewResUi();
            this.mAdapter.setEmptyView(this.emptyView);
        }
    }

    private void showDialog() {
        if (this.loadingPop == null) {
            XPopup.Builder builder = new XPopup.Builder(getActivity());
            Boolean bool = Boolean.FALSE;
            this.loadingPop = (CommonLoadingPop) builder.dismissOnBackPressed(bool).dismissOnTouchOutside(bool).asCustom(new CommonLoadingPop(getActivity()));
        }
        this.loadingPop.show();
    }

    @Override // com.psychiatrygarden.activity.mine.cutquestion.MyCutQuestionAdp.JumpToQList
    public void getExCo() {
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public int getLayoutId() {
        return R.layout.frag_my_cut_question;
    }

    @Override // com.psychiatrygarden.activity.mine.cutquestion.MyCutQuestionAdp.JumpToQList
    public void getPassData(String activity_id) {
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void initViews(ViewHolder holder, View root) {
        this.mIdentityId = getArguments().getString("identity_id");
        this.mCategory = getArguments().getString(UriUtil.QUERY_CATEGORY);
        this.mModuleType = getArguments().getString("module_type");
        this.level1Id = getArguments().getString(KnowledgeQuestionListFragment.EXTRA_LEVEL1_ID);
        initEmptyView();
        this.mRefresh = (SmartRefreshLayout) holder.get(R.id.mSwipeLayput);
        this.mRecyclerView = (RecyclerView) holder.get(R.id.recyclerview);
        MyCutQuestionAdp myCutQuestionAdp = new MyCutQuestionAdp(this);
        this.mAdapter = myCutQuestionAdp;
        myCutQuestionAdp.setAnimationWithDefault(BaseQuickAdapter.AnimationType.AlphaIn);
        this.mRecyclerView.setAdapter(this.mAdapter);
        View viewInflate = View.inflate(this.mContext, R.layout.layout_loaddata_fail_view, null);
        this.mLoadDataFailView = viewInflate;
        ((TextView) viewInflate.findViewById(R.id.btn_reload)).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.mine.cutquestion.r
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12817c.lambda$initViews$2(view);
            }
        });
        this.mRefresh.setOnRefreshListener(new OnRefreshListener() { // from class: com.psychiatrygarden.activity.mine.cutquestion.s
            @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
            public final void onRefresh(RefreshLayout refreshLayout) throws JSONException {
                this.f12818c.lambda$initViews$3(refreshLayout);
            }
        });
    }

    @Override // com.psychiatrygarden.activity.mine.cutquestion.MyCutQuestionAdp.JumpToQList
    public void mChildSelectClumn(int groupPosition, int childPosition) {
    }

    @Override // com.psychiatrygarden.activity.mine.cutquestion.MyCutQuestionAdp.JumpToQList
    public void mGroupSelectClumn(int groupPosition) {
    }

    @Override // com.psychiatrygarden.activity.mine.cutquestion.MyCutQuestionAdp.JumpToQList
    public void mJumpToQList(String primary_id, String unit, String parent_title, String title) {
        if (isLogin()) {
            Bundle bundle = new Bundle();
            bundle.putString("primary_id", primary_id);
            bundle.putString("unit", unit);
            bundle.putString(UriUtil.QUERY_CATEGORY, "" + this.mCategory);
            bundle.putString("module_type", this.mModuleType);
            bundle.putString("type", "all");
            bundle.putString("subject_title", "" + parent_title);
            bundle.putString("chapter_title", "" + title);
            bundle.putString("identity_id", this.mIdentityId);
            bundle.putBoolean("isCutQuestion", true);
            if (!TextUtils.isEmpty(this.level1Id)) {
                bundle.putString("question_bank_id", this.level1Id);
            }
            AnswerSheetActivity.gotoActivity(getActivity(), bundle);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(RefreshCutQuestionEvent event) throws JSONException {
        if (event.identityId.equals(this.mIdentityId)) {
            lambda$setUserVisibleHint$0();
        }
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportFragment, androidx.fragment.app.Fragment
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (!isVisibleToUser || this.loadDataSuccess) {
            return;
        }
        new Handler().postDelayed(new Runnable() { // from class: com.psychiatrygarden.activity.mine.cutquestion.u
            @Override // java.lang.Runnable
            public final void run() throws JSONException {
                this.f12820c.lambda$setUserVisibleHint$0();
            }
        }, 500L);
    }
}
