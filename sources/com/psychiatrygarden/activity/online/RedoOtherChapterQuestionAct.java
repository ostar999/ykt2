package com.psychiatrygarden.activity.online;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.aliyun.vod.common.utils.UriUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.node.BaseNode;
import com.google.gson.Gson;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.impl.LoadingPopupView;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.online.adapter.ChooseChapterAdp;
import com.psychiatrygarden.activity.online.bean.QuestionBankNewBean;
import com.psychiatrygarden.activity.online.bean.QuestionBankNewFristBean;
import com.psychiatrygarden.activity.online.bean.QuestionBankNewSecondBean;
import com.psychiatrygarden.activity.online.bean.QuestionDetailBean;
import com.psychiatrygarden.activity.vip.Utils.MemInterface;
import com.psychiatrygarden.event.RedoOtherQuestionEvent;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.QuestionDataRequest;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.interfaceclass.QuestionDataCallBack;
import com.psychiatrygarden.utils.AliyunEvent;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.ToastUtil;
import com.psychiatrygarden.widget.CustomDialog;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

/* loaded from: classes5.dex */
public class RedoOtherChapterQuestionAct extends BaseActivity implements ChooseChapterAdp.JumpToQList, QuestionDataCallBack<String> {
    public ChooseChapterAdp adapter;
    private String category;
    private String chapterId;
    private List<String> chooseChapterId;
    private List<String> chooseChapterTitleList;
    private String identityId;
    private LoadingPopupView loadingView;
    private TextView mBtnAllRedo;
    private TextView mBtnErrorRedo;
    private View mLoadDataFailView;
    private RecyclerView mRecyclerView;
    private SmartRefreshLayout mRefresh;
    private String moudleType;
    private String type;
    private String unitId;
    public List<QuestionBankNewBean.DataBean> dataList = new ArrayList();
    private List<String> mChooseChapterIds = new ArrayList();
    private boolean isCheckedAll = false;
    private String isRedoType = "";
    private boolean isChoosedCurrentChapter = false;

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
                if (!this.adapter.getData().isEmpty() && (this.adapter.getData().get(i2) instanceof QuestionBankNewFristBean)) {
                    questionBankNewFristBean.setExpanded(((QuestionBankNewFristBean) this.adapter.getData().get(i2)).getIsExpanded());
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            arrayList.add(questionBankNewFristBean);
        }
        return arrayList;
    }

    private void hideLoading() {
        LoadingPopupView loadingPopupView = this.loadingView;
        if (loadingPopupView == null || !loadingPopupView.isShow()) {
            return;
        }
        runOnUiThread(new Runnable() { // from class: com.psychiatrygarden.activity.online.e2
            @Override // java.lang.Runnable
            public final void run() {
                this.f13163c.lambda$hideLoading$8();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getPassData$7() {
        getQuestionBankData(this.identityId, this.moudleType, this.type);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$hideLoading$8() {
        this.loadingView.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0() {
        getQuestionBankData(this.identityId, this.moudleType, this.type);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$1(View view) {
        this.mRefresh.autoRefreshAnimationOnly();
        new Handler().postDelayed(new Runnable() { // from class: com.psychiatrygarden.activity.online.k2
            @Override // java.lang.Runnable
            public final void run() {
                this.f13436c.lambda$init$0();
            }
        }, 500L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$init$2(RefreshLayout refreshLayout) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$3(View view) {
        boolean z2 = !this.isCheckedAll;
        this.isCheckedAll = z2;
        if (z2) {
            this.mBtnActionbarRight.setText("取消全选");
            setAllRefulData(1);
        } else {
            this.mBtnActionbarRight.setText("全选");
            setAllRefulData(0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$4(View view) {
        this.chooseChapterId = new ArrayList();
        this.chooseChapterTitleList = new ArrayList();
        for (int i2 = 0; i2 < this.dataList.size(); i2++) {
            if ("1".equals(this.dataList.get(i2).getPass())) {
                if (!this.dataList.get(i2).getHave().equals("0")) {
                    if (this.dataList.get(i2).getChildIds().size() > 0) {
                        this.chooseChapterId.addAll(this.dataList.get(i2).getChildIds());
                    }
                    for (int i3 = 0; i3 < this.dataList.get(i2).getChildren().size(); i3++) {
                        this.chooseChapterTitleList.add(this.dataList.get(i2).getChildren().get(i3).getTitle());
                    }
                } else if (this.dataList.get(i2).getIsSelected() == 1) {
                    this.chooseChapterId.add(this.dataList.get(i2).getChapter_id());
                    this.chooseChapterTitleList.add(this.dataList.get(i2).getTitle());
                }
            }
        }
        if (this.chooseChapterId.size() == 0) {
            ToastUtil.shortToast(this, "请选择章节");
            return;
        }
        Iterator<String> it = this.chooseChapterId.iterator();
        String str = "";
        while (it.hasNext()) {
            str = str + it.next() + ",";
        }
        if (str.contains(this.chapterId)) {
            this.isChoosedCurrentChapter = true;
        } else {
            this.isChoosedCurrentChapter = false;
        }
        showRedoDialog(str.substring(0, str.length() - 1), "0", "是否重做所选章节所有试题？");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$showRedoDialog$5(CustomDialog customDialog, View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        customDialog.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showRedoDialog$6(String str, String str2, CustomDialog customDialog, View view) {
        if (str.equals("0")) {
            this.isRedoType = "all";
        } else {
            this.isRedoType = "error";
        }
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("is_wrong", str);
        ajaxParams.put("module_type", this.moudleType);
        ajaxParams.put("obj_id", str2);
        ajaxParams.put("identity_id", this.identityId);
        if (this.category.equals("unit")) {
            ajaxParams.put(UriUtil.QUERY_CATEGORY, "unit");
        } else {
            ajaxParams.put(UriUtil.QUERY_CATEGORY, "chapter");
        }
        if (!TextUtils.isEmpty(this.unitId)) {
            ajaxParams.put("unit_id", this.unitId);
        }
        if (this.type.equals("all")) {
            ajaxParams.put("type", "chapter");
        } else {
            ajaxParams.put("type", this.type);
        }
        QuestionDataRequest.getIntance(this).redoAnswer(ajaxParams, NetworkRequestsURL.questionRedo, this);
        customDialog.dismissNoAnimaltion();
        QuestionDetailBean questionDetailBean = new QuestionDetailBean();
        questionDetailBean.setModule_type(this.moudleType);
        questionDetailBean.setUnit_title(ProjectApp.unit_title);
        questionDetailBean.setUnit_id(ProjectApp.unit_id);
        questionDetailBean.setExam_title(ProjectApp.exam_title);
        questionDetailBean.setIdentity_title(ProjectApp.identity_title);
        questionDetailBean.setIdentity_id(ProjectApp.identity_id);
        questionDetailBean.setChapter_title(ProjectApp.chapter_title);
        questionDetailBean.setChapter_id(ProjectApp.chapter_id);
        questionDetailBean.setChapter_parent_title(ProjectApp.chapter_parent_title);
        questionDetailBean.setChapter_parent_id(ProjectApp.chapter_parent_id);
        String json = ProjectApp.gson.toJson(questionDetailBean);
        String str3 = "[\"" + com.psychiatrygarden.activity.q2.a("\",\"", this.chooseChapterId) + "\"]";
        String str4 = "[\"" + com.psychiatrygarden.activity.q2.a("\",\"", this.chooseChapterTitleList) + "\"]";
        AliyunEvent aliyunEvent = AliyunEvent.RedoAnswer_Chapter;
        CommonUtil.addLog(aliyunEvent.getKey(), aliyunEvent.getValue(), System.currentTimeMillis() + "", "", str3, str4, json, "2");
    }

    public static Intent newIntent(Context context, String identityId, String moudleType, String category, String type, String unitId, String chapterId) {
        Intent intent = new Intent(context, (Class<?>) RedoOtherChapterQuestionAct.class);
        intent.putExtra("identityId", identityId);
        intent.putExtra("moudleType", moudleType);
        intent.putExtra(UriUtil.QUERY_CATEGORY, category);
        intent.putExtra("type", type);
        intent.putExtra("unitId", unitId);
        intent.putExtra("chapterId", chapterId);
        return intent;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setEmptyView(boolean isError) {
        if (isError) {
            this.adapter.setEmptyView(this.mLoadDataFailView);
        } else {
            this.adapter.setEmptyView(R.layout.layout_empty_view);
        }
    }

    private void showLoading() {
        LoadingPopupView loadingPopupView = this.loadingView;
        if (loadingPopupView == null || loadingPopupView.isShow()) {
            return;
        }
        this.loadingView.show();
    }

    private void showRedoDialog(final String strChapterId, final String isWrong, String message) {
        final CustomDialog customDialog = new CustomDialog(this.mContext, 2);
        customDialog.setCancelable(false);
        customDialog.setMessage(message);
        customDialog.setNegativeBtn(R.string.cancel, new View.OnClickListener() { // from class: com.psychiatrygarden.activity.online.f2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                RedoOtherChapterQuestionAct.lambda$showRedoDialog$5(customDialog, view);
            }
        });
        customDialog.setPositiveBtn("确定", new View.OnClickListener() { // from class: com.psychiatrygarden.activity.online.g2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13414c.lambda$showRedoDialog$6(isWrong, strChapterId, customDialog, view);
            }
        });
        customDialog.show();
    }

    @Override // com.psychiatrygarden.activity.online.adapter.ChooseChapterAdp.JumpToQList
    public void getExCo() {
    }

    @Override // com.psychiatrygarden.activity.online.adapter.ChooseChapterAdp.JumpToQList
    public void getPassData(String activity_id) {
        if (isLogin()) {
            AjaxParams ajaxParams = new AjaxParams();
            ajaxParams.put(ConstantsAPI.WXWebPage.KEY_ACTIVITY_ID, "" + activity_id);
            MemInterface.getInstance().getMemData(this, ajaxParams, false, 0);
            MemInterface.getInstance().setmUShareListener(new MemInterface.UShareListener() { // from class: com.psychiatrygarden.activity.online.h2
                @Override // com.psychiatrygarden.activity.vip.Utils.MemInterface.UShareListener
                public final void mUShareListener() {
                    this.f13424a.lambda$getPassData$7();
                }
            });
        }
    }

    public void getQuestionBankData(String identityId, String moudleType, String type) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("identity_id", identityId);
        ajaxParams.put("module_type", moudleType);
        ajaxParams.put("type", type);
        if (this.category.equals("unit")) {
            ajaxParams.put(UriUtil.QUERY_CATEGORY, "unit");
        } else {
            ajaxParams.put(UriUtil.QUERY_CATEGORY, "chapter");
        }
        if (!TextUtils.isEmpty(this.unitId)) {
            ajaxParams.put("unit_id", this.unitId);
        }
        YJYHttpUtils.post(this, NetworkRequestsURL.redoOtherChapterList, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.online.RedoOtherChapterQuestionAct.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                RedoOtherChapterQuestionAct.this.mRefresh.finishRefresh(false);
                RedoOtherChapterQuestionAct.this.setEmptyView(true);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass1) s2);
                try {
                    QuestionBankNewBean questionBankNewBean = (QuestionBankNewBean) new Gson().fromJson(s2, QuestionBankNewBean.class);
                    RedoOtherChapterQuestionAct.this.mRefresh.finishRefresh("200".equals(questionBankNewBean.getCode()));
                    if (!"200".equals(questionBankNewBean.getCode())) {
                        if ("202".equals(questionBankNewBean.getCode())) {
                            RedoOtherChapterQuestionAct.this.setEmptyView(true);
                            return;
                        }
                        return;
                    }
                    if (questionBankNewBean.getData() == null || questionBankNewBean.getData().size() <= 0) {
                        RedoOtherChapterQuestionAct.this.setEmptyView(false);
                        return;
                    }
                    RedoOtherChapterQuestionAct.this.dataList = questionBankNewBean.getData();
                    for (int i2 = 0; i2 < RedoOtherChapterQuestionAct.this.dataList.size(); i2++) {
                        ArrayList arrayList = new ArrayList();
                        if (RedoOtherChapterQuestionAct.this.dataList.get(i2).getChildren() != null && RedoOtherChapterQuestionAct.this.dataList.get(i2).getChildren().size() > 0) {
                            for (int i3 = 0; i3 < RedoOtherChapterQuestionAct.this.dataList.get(i2).getChildren().size(); i3++) {
                                if (RedoOtherChapterQuestionAct.this.mChooseChapterIds.contains(RedoOtherChapterQuestionAct.this.dataList.get(i2).getChildren().get(i3).getChapter_id())) {
                                    RedoOtherChapterQuestionAct.this.dataList.get(i2).getChildren().get(i3).setIsSelected(1);
                                    RedoOtherChapterQuestionAct.this.dataList.get(i2).getChildIds().add(RedoOtherChapterQuestionAct.this.dataList.get(i2).getChildren().get(i3).getChapter_id());
                                }
                                RedoOtherChapterQuestionAct.this.dataList.get(i2).getChildren().get(i3).setPrimary_p_id(RedoOtherChapterQuestionAct.this.dataList.get(i2).getChapter_id() + "");
                                RedoOtherChapterQuestionAct.this.dataList.get(i2).getChildren().get(i3).setGroupPosition(i2);
                                RedoOtherChapterQuestionAct.this.dataList.get(i2).getChildren().get(i3).setParent_title(RedoOtherChapterQuestionAct.this.dataList.get(i2).getTitle());
                                arrayList.add(RedoOtherChapterQuestionAct.this.dataList.get(i2).getChildren().get(i3));
                            }
                            if (RedoOtherChapterQuestionAct.this.dataList.get(i2).getChildIds().size() <= 0) {
                                RedoOtherChapterQuestionAct.this.dataList.get(i2).setIsSelected(0);
                            } else if (RedoOtherChapterQuestionAct.this.dataList.get(i2).getChildIds().size() == RedoOtherChapterQuestionAct.this.dataList.get(i2).getChildren().size()) {
                                RedoOtherChapterQuestionAct.this.dataList.get(i2).setIsSelected(1);
                            } else {
                                RedoOtherChapterQuestionAct.this.dataList.get(i2).setIsSelected(2);
                            }
                        }
                        RedoOtherChapterQuestionAct.this.dataList.get(i2).setChildren(arrayList);
                    }
                    RedoOtherChapterQuestionAct redoOtherChapterQuestionAct = RedoOtherChapterQuestionAct.this;
                    redoOtherChapterQuestionAct.adapter.setList(redoOtherChapterQuestionAct.getEntity(redoOtherChapterQuestionAct.dataList));
                } catch (Exception e2) {
                    e2.printStackTrace();
                    RedoOtherChapterQuestionAct.this.setEmptyView(true);
                    RedoOtherChapterQuestionAct.this.mRefresh.finishRefresh(false);
                }
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.identityId = getIntent().getStringExtra("identityId");
        this.moudleType = getIntent().getStringExtra("moudleType");
        this.category = getIntent().getStringExtra(UriUtil.QUERY_CATEGORY);
        this.type = getIntent().getStringExtra("type");
        this.unitId = getIntent().getStringExtra("unitId");
        this.chapterId = getIntent().getStringExtra("chapterId");
        this.mRefresh = (SmartRefreshLayout) findViewById(R.id.mSwipeLayput);
        this.mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        this.mBtnAllRedo = (TextView) findViewById(R.id.btn_all_redo);
        this.mBtnErrorRedo = (TextView) findViewById(R.id.btn_error_redo);
        View viewInflate = View.inflate(this.mContext, R.layout.layout_loaddata_fail_view, null);
        this.mLoadDataFailView = viewInflate;
        TextView textView = (TextView) viewInflate.findViewById(R.id.btn_reload);
        this.loadingView = new XPopup.Builder(this).isViewMode(true).asLoading("加载中", R.layout.layout_loading);
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        ChooseChapterAdp chooseChapterAdp = new ChooseChapterAdp(this, false);
        this.adapter = chooseChapterAdp;
        chooseChapterAdp.setAnimationWithDefault(BaseQuickAdapter.AnimationType.AlphaIn);
        this.mRecyclerView.setAdapter(this.adapter);
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.online.c2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13151c.lambda$init$1(view);
            }
        });
        this.mRefresh.setOnRefreshListener(new OnRefreshListener() { // from class: com.psychiatrygarden.activity.online.d2
            @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
            public final void onRefresh(RefreshLayout refreshLayout) {
                RedoOtherChapterQuestionAct.lambda$init$2(refreshLayout);
            }
        });
        getQuestionBankData(this.identityId, this.moudleType, this.type);
    }

    @Override // com.psychiatrygarden.activity.online.adapter.ChooseChapterAdp.JumpToQList
    public void mChildSelectClumn(int groupPosition, int childPosition) {
        setRefulData(groupPosition);
    }

    @Override // com.psychiatrygarden.activity.online.adapter.ChooseChapterAdp.JumpToQList
    public void mGroupSelectClumn(int groupPosition) {
        List<QuestionBankNewBean.DataBean> list = this.dataList;
        if (list == null || list.size() <= 0) {
            return;
        }
        for (int i2 = 0; i2 < this.dataList.size(); i2++) {
            if (this.dataList.get(i2) != null) {
                QuestionBankNewBean.DataBean dataBean = this.dataList.get(i2);
                if (dataBean.getPass().equals("1") && dataBean.getIsSelected() != 1) {
                    this.isCheckedAll = false;
                    this.mBtnActionbarRight.setText("全选");
                    return;
                }
            }
        }
        this.isCheckedAll = true;
        this.mBtnActionbarRight.setText("取消全选");
    }

    @Override // com.psychiatrygarden.activity.online.adapter.ChooseChapterAdp.JumpToQList
    public void mJumpToQList(String primary_id, String unit, String parent_title, String title) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
    public void onFailure(Throwable t2, int errorNo, String strMsg, String requstUrl) {
    }

    @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
    public void onStart(String requstUrl) {
    }

    public void setAllRefulData(int select) {
        List<QuestionBankNewBean.DataBean> list = this.dataList;
        if (list != null && list.size() > 0) {
            for (int i2 = 0; i2 < this.dataList.size(); i2++) {
                if (this.dataList.get(i2) != null && "1".equals(this.dataList.get(i2).getPass())) {
                    QuestionBankNewBean.DataBean dataBean = this.dataList.get(i2);
                    dataBean.setIsSelected(select);
                    this.dataList.get(i2).getChildIds().clear();
                    if (dataBean.getChildren() != null && dataBean.getChildren().size() > 0) {
                        for (int i3 = 0; i3 < dataBean.getChildren().size(); i3++) {
                            QuestionBankNewBean.DataBean.ChildrenBean childrenBean = dataBean.getChildren().get(i3);
                            childrenBean.setIsSelected(select);
                            if (select == 1) {
                                this.dataList.get(i2).getChildIds().add(childrenBean.getChapter_id());
                            }
                        }
                    }
                }
            }
        }
        this.adapter.notifyDataSetChanged();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.layout_redo_other_chapter_question);
        setTitle("重做");
        this.mBtnActionbarRight.setVisibility(0);
        this.mBtnActionbarRight.setText("全选");
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.mBtnActionbarRight.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.online.i2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13428c.lambda$setListenerForWidget$3(view);
            }
        });
        this.mBtnAllRedo.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.online.j2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13433c.lambda$setListenerForWidget$4(view);
            }
        });
    }

    public void setRefulData(int groupPosition) {
        for (int i2 = 0; i2 < this.dataList.get(groupPosition).getChildren().size(); i2++) {
            QuestionBankNewBean.DataBean.ChildrenBean childrenBean = this.dataList.get(groupPosition).getChildren().get(i2);
            if (childrenBean.getIsSelected() == 1) {
                if (!this.dataList.get(groupPosition).getChildIds().contains(childrenBean.getChapter_id())) {
                    this.dataList.get(groupPosition).getChildIds().add(childrenBean.getChapter_id());
                }
            } else if (this.dataList.get(groupPosition).getChildIds().contains(childrenBean.getChapter_id())) {
                this.dataList.get(groupPosition).getChildIds().remove(childrenBean.getChapter_id());
            }
        }
        int size = this.dataList.get(groupPosition).getChildIds().size();
        Log.e("choose_count", size + "");
        if (size == this.dataList.get(groupPosition).children.size()) {
            this.dataList.get(groupPosition).setIsSelected(1);
            this.isCheckedAll = false;
            this.mBtnActionbarRight.setText("全选");
        } else if (size == 0) {
            this.dataList.get(groupPosition).setIsSelected(0);
            this.isCheckedAll = false;
            this.mBtnActionbarRight.setText("全选");
        } else {
            this.dataList.get(groupPosition).setIsSelected(2);
            this.isCheckedAll = false;
            this.mBtnActionbarRight.setText("全选");
        }
        for (int i3 = 0; i3 < this.dataList.size(); i3++) {
            if (this.dataList.get(i3) != null) {
                QuestionBankNewBean.DataBean dataBean = this.dataList.get(i3);
                if (dataBean.getPass().equals("1") && dataBean.getIsSelected() != 1) {
                    return;
                }
            }
        }
        this.isCheckedAll = true;
        this.mBtnActionbarRight.setText("取消全选");
    }

    @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
    public void onSuccess(String s2, String requstUrl) {
        if (requstUrl.equals(NetworkRequestsURL.questionRedo)) {
            if (this.isChoosedCurrentChapter) {
                EventBus.getDefault().post(new RedoOtherQuestionEvent("all", false));
            }
            hideLoading();
            finish();
        }
    }
}
