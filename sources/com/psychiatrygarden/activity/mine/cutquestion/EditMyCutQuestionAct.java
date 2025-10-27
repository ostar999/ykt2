package com.psychiatrygarden.activity.mine.cutquestion;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.node.BaseNode;
import com.google.gson.Gson;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.mine.cutquestion.adapter.ChooseChapterAdpNew;
import com.psychiatrygarden.activity.online.bean.QuestionBankNewBean;
import com.psychiatrygarden.activity.online.bean.QuestionBankNewFristBean;
import com.psychiatrygarden.activity.online.bean.QuestionBankNewSecondBean;
import com.psychiatrygarden.activity.vip.Utils.MemInterface;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.event.RefreshCutQuestionEvent;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.AliyunEvent;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.ToastUtil;
import com.psychiatrygarden.widget.CustomDialog;
import com.psychiatrygarden.widget.CustomEmptyView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class EditMyCutQuestionAct extends BaseActivity implements ChooseChapterAdpNew.JumpToQList, SwipeRefreshLayout.OnRefreshListener {
    private ChooseChapterAdpNew adapter;
    private CustomEmptyView emptyView;
    private TextView mBtnSure;
    private String mIdentityId;
    private View mLoadDataFailView;
    private String mModuleType;
    private RecyclerView mRecyclerView;
    private SmartRefreshLayout mRefresh;
    private TextView tvAllSelect;
    private TextView tv_title;
    public List<QuestionBankNewBean.DataBean> dataList = new ArrayList();
    private boolean isChooseAll = false;

    private int getChooseQuestionCountList() {
        int childSelectCount;
        int i2 = 0;
        for (int i3 = 0; i3 < this.dataList.size(); i3++) {
            if ("1".equals(this.dataList.get(i3).getPass())) {
                if (this.dataList.get(i3).getHave().equals("0")) {
                    if (this.dataList.get(i3).getIsSelected() == 1) {
                        childSelectCount = this.dataList.get(i3).getCount();
                        i2 += childSelectCount;
                    }
                } else if (this.dataList.get(i3).getChildIds().size() > 0) {
                    childSelectCount = this.dataList.get(i3).getChildSelectCount();
                    i2 += childSelectCount;
                }
            }
        }
        Log.e("choose_count", "选中的试题数量:" + i2);
        return i2;
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

    private void getQuestionBankData(String identityId, String moduleType) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("identity_id", identityId);
        ajaxParams.put("user_id", UserConfig.getUserId());
        ajaxParams.put("module_type", moduleType);
        YJYHttpUtils.post(this, NetworkRequestsURL.myCutQuestionList, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.mine.cutquestion.EditMyCutQuestionAct.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                EditMyCutQuestionAct.this.mRefresh.finishRefresh(false);
                EditMyCutQuestionAct.this.emptyView.stopAnim();
                EditMyCutQuestionAct.this.setEmptyView(true);
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
                    EditMyCutQuestionAct.this.mRefresh.finishRefresh("200".equals(questionBankNewBean.getCode()));
                    if (!"200".equals(questionBankNewBean.getCode())) {
                        if ("202".equals(questionBankNewBean.getCode())) {
                            EditMyCutQuestionAct.this.emptyView.stopAnim();
                            EditMyCutQuestionAct.this.setEmptyView(true);
                            return;
                        }
                        return;
                    }
                    EditMyCutQuestionAct.this.emptyView.stopAnim();
                    EditMyCutQuestionAct.this.emptyView.setVisibility(8);
                    if (questionBankNewBean.getData() == null || questionBankNewBean.getData().size() <= 0) {
                        EditMyCutQuestionAct.this.setEmptyView(false);
                        EditMyCutQuestionAct.this.emptyView.stopAnim();
                        return;
                    }
                    EditMyCutQuestionAct.this.dataList = questionBankNewBean.getData();
                    for (int i2 = 0; i2 < EditMyCutQuestionAct.this.dataList.size(); i2++) {
                        ArrayList arrayList = new ArrayList();
                        if (EditMyCutQuestionAct.this.dataList.get(i2).getChildren() != null && EditMyCutQuestionAct.this.dataList.get(i2).getChildren().size() > 0) {
                            for (int i3 = 0; i3 < EditMyCutQuestionAct.this.dataList.get(i2).getChildren().size(); i3++) {
                                EditMyCutQuestionAct.this.dataList.get(i2).getChildren().get(i3).setPrimary_p_id(EditMyCutQuestionAct.this.dataList.get(i2).getChapter_id() + "");
                                EditMyCutQuestionAct.this.dataList.get(i2).getChildren().get(i3).setGroupPosition(i2);
                                EditMyCutQuestionAct.this.dataList.get(i2).getChildren().get(i3).setParent_title(EditMyCutQuestionAct.this.dataList.get(i2).getTitle());
                                arrayList.add(EditMyCutQuestionAct.this.dataList.get(i2).getChildren().get(i3));
                            }
                            if (EditMyCutQuestionAct.this.dataList.get(i2).getChildIds().size() <= 0) {
                                EditMyCutQuestionAct.this.dataList.get(i2).setIsSelected(0);
                            } else if (EditMyCutQuestionAct.this.dataList.get(i2).getChildIds().size() == EditMyCutQuestionAct.this.dataList.get(i2).getChildren().size()) {
                                EditMyCutQuestionAct.this.dataList.get(i2).setIsSelected(1);
                            } else {
                                EditMyCutQuestionAct.this.dataList.get(i2).setIsSelected(2);
                            }
                        }
                        EditMyCutQuestionAct.this.dataList.get(i2).setChildren(arrayList);
                    }
                    EditMyCutQuestionAct editMyCutQuestionAct = EditMyCutQuestionAct.this;
                    List entity = editMyCutQuestionAct.getEntity(editMyCutQuestionAct.dataList);
                    EditMyCutQuestionAct.this.adapter.setList(entity);
                    if (entity.isEmpty()) {
                        EditMyCutQuestionAct.this.setEmptyView(true);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                    EditMyCutQuestionAct.this.emptyView.stopAnim();
                    EditMyCutQuestionAct.this.setEmptyView(true);
                    EditMyCutQuestionAct.this.mRefresh.finishRefresh(false);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getPassData$9() {
        getQuestionBankData(this.mIdentityId, this.mModuleType);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(View view) {
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$1() {
        getQuestionBankData(this.mIdentityId, this.mModuleType);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$2(View view) {
        this.mRefresh.autoRefreshAnimationOnly();
        new Handler().postDelayed(new Runnable() { // from class: com.psychiatrygarden.activity.mine.cutquestion.d
            @Override // java.lang.Runnable
            public final void run() {
                this.f12798c.lambda$init$1();
            }
        }, 500L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$3() {
        getQuestionBankData(this.mIdentityId, this.mModuleType);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$4(View view) {
        this.mRefresh.autoRefreshAnimationOnly();
        new Handler().postDelayed(new Runnable() { // from class: com.psychiatrygarden.activity.mine.cutquestion.j
            @Override // java.lang.Runnable
            public final void run() {
                this.f12804c.lambda$init$3();
            }
        }, 500L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$5(View view) {
        boolean z2 = !this.isChooseAll;
        this.isChooseAll = z2;
        if (z2) {
            setRightText("取消全选");
            setAllRefulData(1);
        } else {
            setRightText("全选");
            setAllRefulData(0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$setListenerForWidget$6(CustomDialog customDialog, View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        customDialog.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$7(List list, CustomDialog customDialog, View view) {
        submit(list);
        customDialog.dismissNoAnimaltion();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$8(View view) {
        final ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < this.dataList.size(); i2++) {
            if ("1".equals(this.dataList.get(i2).getPass())) {
                if (this.dataList.get(i2).getHave().equals("0")) {
                    if (this.dataList.get(i2).getIsSelected() == 1) {
                        arrayList.add(this.dataList.get(i2).getChapter_id());
                    }
                } else if (this.dataList.get(i2).getChildIds().size() > 0) {
                    arrayList.addAll(this.dataList.get(i2).getChildIds());
                }
            }
        }
        Log.e("choose_count", arrayList.size() + "");
        if (arrayList.size() == 0) {
            ToastUtil.shortToast(this, "请选择章节");
            return;
        }
        final CustomDialog customDialog = new CustomDialog(this.mContext, 2);
        customDialog.setCancelable(false);
        customDialog.setMessage("是否移除所选章节中的已斩试题？");
        customDialog.setNegativeBtn(R.string.cancel, new View.OnClickListener() { // from class: com.psychiatrygarden.activity.mine.cutquestion.a
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                EditMyCutQuestionAct.lambda$setListenerForWidget$6(customDialog, view2);
            }
        });
        customDialog.setPositiveBtn("确定", new View.OnClickListener() { // from class: com.psychiatrygarden.activity.mine.cutquestion.b
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f12794c.lambda$setListenerForWidget$7(arrayList, customDialog, view2);
            }
        });
        customDialog.show();
    }

    public static void navigationToEditMyCutQuestion(Context context, String identityId, String moduleType) {
        Intent intent = new Intent(context, (Class<?>) EditMyCutQuestionAct.class);
        intent.putExtra("identityId", identityId);
        intent.putExtra("module_type", moduleType);
        context.startActivity(intent);
    }

    public static Intent newIntent(Context context, String identityId, String moduleType) {
        Intent intent = new Intent(context, (Class<?>) EditMyCutQuestionAct.class);
        intent.putExtra("identityId", identityId);
        intent.putExtra("module_type", moduleType);
        return intent;
    }

    private void setCustomerTitleCount(int count) {
        this.tv_title.setText("已选中" + count + "个题");
        this.mBtnSure.setEnabled(count > 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setEmptyView(boolean isError) {
        if (isError) {
            this.emptyView.setLoadFileResUi(this);
            this.adapter.setEmptyView(this.emptyView);
        } else {
            this.emptyView.setEmptyTextStr("暂无数据");
            this.emptyView.uploadEmptyViewResUi();
            this.adapter.setEmptyView(this.emptyView);
        }
    }

    private void setRightText(String text) {
        this.tvAllSelect.setText(text);
    }

    private void submit(List<String> chapterIds) {
        Iterator<String> it = chapterIds.iterator();
        String str = "";
        while (it.hasNext()) {
            str = str + it.next() + ",";
        }
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("identity_id", this.mIdentityId);
        ajaxParams.put("user_id", UserConfig.getUserId());
        ajaxParams.put("module_type", this.mModuleType);
        ajaxParams.put("chapter_id", str.substring(0, str.length() - 1));
        YJYHttpUtils.post(this, NetworkRequestsURL.moveToQuestion, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.mine.cutquestion.EditMyCutQuestionAct.2
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass2) s2);
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if (jSONObject.optString("code", "").equals("200")) {
                        EventBus.getDefault().post(new RefreshCutQuestionEvent(EditMyCutQuestionAct.this.mIdentityId));
                        if (jSONObject.optJSONObject("data") != null) {
                            EditMyCutQuestionAct.this.finish();
                        }
                    } else {
                        String strOptString = jSONObject.optString("message", "");
                        if (!TextUtils.isEmpty(strOptString)) {
                            EditMyCutQuestionAct.this.AlertToast(strOptString);
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
        AliyunEvent aliyunEvent = AliyunEvent.CutQuestionMoveBack;
        CommonUtil.addLog(aliyunEvent.getKey(), aliyunEvent.getValue(), System.currentTimeMillis() + "", "", "", "", "", "2");
    }

    @Override // com.psychiatrygarden.activity.mine.cutquestion.adapter.ChooseChapterAdpNew.JumpToQList
    public void getExCo() {
    }

    @Override // com.psychiatrygarden.activity.mine.cutquestion.adapter.ChooseChapterAdpNew.JumpToQList
    public void getPassData(String activity_id) {
        if (isLogin()) {
            AjaxParams ajaxParams = new AjaxParams();
            ajaxParams.put(ConstantsAPI.WXWebPage.KEY_ACTIVITY_ID, "" + activity_id);
            MemInterface.getInstance().getMemData(this, ajaxParams, false, 0);
            MemInterface.getInstance().setmUShareListener(new MemInterface.UShareListener() { // from class: com.psychiatrygarden.activity.mine.cutquestion.c
                @Override // com.psychiatrygarden.activity.vip.Utils.MemInterface.UShareListener
                public final void mUShareListener() {
                    this.f12797a.lambda$getPassData$9();
                }
            });
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.mActionBar.hide();
        findViewById(R.id.iv_export_back).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.mine.cutquestion.g
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12801c.lambda$init$0(view);
            }
        });
        CustomEmptyView customEmptyView = (CustomEmptyView) findViewById(R.id.emptyView);
        this.emptyView = customEmptyView;
        customEmptyView.setOnReloadData(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.mine.cutquestion.h
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12802c.lambda$init$2(view);
            }
        });
        this.tv_title = (TextView) findViewById(R.id.tv_export_title);
        this.tvAllSelect = (TextView) findViewById(R.id.tv_export_all_select);
        this.mIdentityId = getIntent().getStringExtra("identityId");
        this.mModuleType = getIntent().getStringExtra("module_type");
        setTitle("已选择0个题");
        setRightText("全选");
        this.mBtnActionbarBack.setImageResource(R.drawable.icon_del_141516);
        this.mRefresh = (SmartRefreshLayout) findViewById(R.id.mSwipeLayput);
        this.mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        this.mBtnSure = (TextView) findViewById(R.id.btn_sure);
        View viewInflate = View.inflate(this.mContext, R.layout.layout_loaddata_fail_view, null);
        this.mLoadDataFailView = viewInflate;
        TextView textView = (TextView) viewInflate.findViewById(R.id.btn_reload);
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        ChooseChapterAdpNew chooseChapterAdpNew = new ChooseChapterAdpNew(this, true, true);
        this.adapter = chooseChapterAdpNew;
        chooseChapterAdpNew.setAnimationWithDefault(BaseQuickAdapter.AnimationType.AlphaIn);
        this.mRecyclerView.setAdapter(this.adapter);
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.mine.cutquestion.i
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12803c.lambda$init$4(view);
            }
        });
        getQuestionBankData(this.mIdentityId, this.mModuleType);
    }

    @Override // com.psychiatrygarden.activity.mine.cutquestion.adapter.ChooseChapterAdpNew.JumpToQList
    public void mChildSelectClumn(int groupPosition, int childPosition) {
        setRefulData(groupPosition);
    }

    @Override // com.psychiatrygarden.activity.mine.cutquestion.adapter.ChooseChapterAdpNew.JumpToQList
    public void mGroupSelectClumn(int groupPosition) {
        List<QuestionBankNewBean.DataBean> list = this.dataList;
        if (list == null || list.size() <= 0) {
            return;
        }
        for (int i2 = 0; i2 < this.dataList.size(); i2++) {
            if (this.dataList.get(i2) != null && this.dataList.get(i2).getIsSelected() != 1) {
                this.isChooseAll = false;
                if (getChooseQuestionCountList() > 0) {
                    setCustomerTitleCount(getChooseQuestionCountList());
                } else {
                    setCustomerTitleCount(0);
                }
                setRightText("全选");
                return;
            }
        }
        this.isChooseAll = true;
        setRightText("取消全选");
        setCustomerTitleCount(getChooseQuestionCountList());
    }

    @Override // com.psychiatrygarden.activity.mine.cutquestion.adapter.ChooseChapterAdpNew.JumpToQList
    public void mJumpToQList(String primary_id, String unit, String parent_title, String title) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
    public void onRefresh() {
    }

    public void setAllRefulData(int select) {
        List<QuestionBankNewBean.DataBean> list = this.dataList;
        if (list != null && list.size() > 0) {
            for (int i2 = 0; i2 < this.dataList.size(); i2++) {
                if (this.dataList.get(i2) != null && "1".equals(this.dataList.get(i2).getPass())) {
                    QuestionBankNewBean.DataBean dataBean = this.dataList.get(i2);
                    dataBean.setIsSelected(select);
                    this.dataList.get(i2).getChildIds().clear();
                    this.dataList.get(i2).setChildSelectCount(0);
                    if (dataBean.getChildren() != null && dataBean.getChildren().size() > 0) {
                        for (int i3 = 0; i3 < dataBean.getChildren().size(); i3++) {
                            QuestionBankNewBean.DataBean.ChildrenBean childrenBean = dataBean.getChildren().get(i3);
                            childrenBean.setIsSelected(select);
                            if (select == 1) {
                                this.dataList.get(i2).getChildIds().add(childrenBean.getChapter_id());
                                this.dataList.get(i2).setChildSelectCount(this.dataList.get(i2).getChildSelectCount() + childrenBean.getCount());
                            }
                        }
                    }
                }
            }
        }
        this.adapter.notifyDataSetChanged();
        if (getChooseQuestionCountList() > 0) {
            setCustomerTitleCount(getChooseQuestionCountList());
        } else {
            setCustomerTitleCount(0);
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.layout_edit_my_cut_question);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.tvAllSelect.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.mine.cutquestion.e
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12799c.lambda$setListenerForWidget$5(view);
            }
        });
        this.mBtnSure.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.mine.cutquestion.f
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12800c.lambda$setListenerForWidget$8(view);
            }
        });
    }

    public void setRefulData(int groupPosition) {
        for (int i2 = 0; i2 < this.dataList.get(groupPosition).getChildren().size(); i2++) {
            QuestionBankNewBean.DataBean.ChildrenBean childrenBean = this.dataList.get(groupPosition).getChildren().get(i2);
            if (childrenBean.getIsSelected() == 1) {
                if (!this.dataList.get(groupPosition).getChildIds().contains(childrenBean.getChapter_id())) {
                    this.dataList.get(groupPosition).getChildIds().add(childrenBean.getChapter_id());
                    this.dataList.get(groupPosition).setChildSelectCount(this.dataList.get(groupPosition).getChildSelectCount() + childrenBean.getCount());
                }
            } else if (this.dataList.get(groupPosition).getChildIds().contains(childrenBean.getChapter_id())) {
                this.dataList.get(groupPosition).getChildIds().remove(childrenBean.getChapter_id());
                this.dataList.get(groupPosition).setChildSelectCount(this.dataList.get(groupPosition).getChildSelectCount() - childrenBean.getCount());
            }
        }
        int size = this.dataList.get(groupPosition).getChildIds().size();
        Log.e("choose_count", size + ";question_count=" + this.dataList.get(groupPosition).getChildSelectCount());
        if (size == this.dataList.get(groupPosition).children.size()) {
            this.dataList.get(groupPosition).setIsSelected(1);
            this.isChooseAll = false;
            setRightText("全选");
        } else if (size == 0) {
            this.dataList.get(groupPosition).setIsSelected(0);
            this.isChooseAll = false;
            setRightText("全选");
        } else {
            this.dataList.get(groupPosition).setIsSelected(2);
            this.isChooseAll = false;
            setRightText("全选");
        }
        if (getChooseQuestionCountList() > 0) {
            setCustomerTitleCount(getChooseQuestionCountList());
        } else {
            setCustomerTitleCount(0);
        }
        for (int i3 = 0; i3 < this.dataList.size(); i3++) {
            if (this.dataList.get(i3) != null) {
                QuestionBankNewBean.DataBean dataBean = this.dataList.get(i3);
                if (dataBean.getPass().equals("1") && dataBean.getIsSelected() != 1) {
                    return;
                }
            }
        }
        this.isChooseAll = true;
        setRightText("取消全选");
    }
}
