package com.psychiatrygarden;

import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.node.BaseNode;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.mine.cutquestion.adapter.ChooseChapterAdpNew;
import com.psychiatrygarden.activity.online.bean.QuestionBankNewBean;
import com.psychiatrygarden.activity.online.bean.QuestionBankNewFristBean;
import com.psychiatrygarden.activity.online.bean.QuestionBankNewSecondBean;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.NewToast;
import com.psychiatrygarden.widget.CustomEmptyView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.List;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

/* loaded from: classes5.dex */
public class ChooseChapterAct extends BaseActivity implements ChooseChapterAdpNew.JumpToQList, SwipeRefreshLayout.OnRefreshListener {
    public ChooseChapterAdpNew adapter;
    private CustomEmptyView emptyView;
    private String identityId;
    private ImageView imgBack;
    private TextView mBtnSure;
    private View mLoadDataFailView;
    private RecyclerView mRecyclerView;
    private SmartRefreshLayout mRefresh;
    private TextView tvAllSelect;
    private TextView tvTitle;
    public List<QuestionBankNewBean.DataBean> dataList = new ArrayList();
    private List<String> mChooseChapterIds = new ArrayList();
    private boolean isSelectAllChapter = false;

    private int getChooseQuestionCountList() {
        int size;
        int i2 = 0;
        for (int i3 = 0; i3 < this.dataList.size(); i3++) {
            if ("1".equals(this.dataList.get(i3).getPass())) {
                if (this.dataList.get(i3).getIsSelected() == 1) {
                    size = this.dataList.get(i3).getChildren().size();
                } else if (this.dataList.get(i3).getIsSelected() == 2) {
                    size = this.dataList.get(i3).childIds.size();
                }
                i2 += size;
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

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0() {
        getQuestionBankData(this.identityId);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$1(View view) {
        this.mRefresh.autoRefreshAnimationOnly();
        new Handler().postDelayed(new Runnable() { // from class: com.psychiatrygarden.g
            @Override // java.lang.Runnable
            public final void run() {
                this.f16176c.lambda$init$0();
            }
        }, 500L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$2(View view) {
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$3(RefreshLayout refreshLayout) {
        getQuestionBankData(this.identityId);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$4(View view) {
        getQuestionBankData(this.identityId);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$5(View view) {
        boolean z2 = !this.isSelectAllChapter;
        this.isSelectAllChapter = z2;
        if (z2) {
            this.tvAllSelect.setText("取消全选");
            setAllRefulData(1);
        } else {
            this.tvAllSelect.setText("全选");
            setAllRefulData(0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$6(View view) {
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < this.dataList.size(); i2++) {
            if ("1".equals(this.dataList.get(i2).getPass()) && this.dataList.get(i2).getChildIds().size() > 0) {
                arrayList.addAll(this.dataList.get(i2).getChildIds());
            }
        }
        Intent intent = new Intent();
        if (arrayList.size() > 0) {
            intent.putExtra("chooseChapterId", new Gson().toJson(arrayList));
        } else {
            intent.putExtra("chooseChapterId", "");
        }
        intent.putExtra("chapterCount", arrayList.size());
        intent.putExtra("isChooseAll", this.isSelectAllChapter);
        setResult(-1, intent);
        finish();
    }

    private void setCustomerTitleCount(int count) {
        if (count == 0) {
            this.tvTitle.setText("章节选择");
        } else {
            this.tvTitle.setText("已选中" + count + "个章节");
        }
        this.mBtnSure.setEnabled(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setEmptyView(boolean isError) {
        this.emptyView.setVisibility(0);
        if (isError) {
            this.emptyView.setLoadFileResUi(this);
            this.adapter.setEmptyView(this.emptyView);
        } else {
            this.emptyView.setEmptyTextStr("暂无数据");
            this.emptyView.uploadEmptyViewResUi();
            this.adapter.setEmptyView(this.emptyView);
        }
    }

    @Override // com.psychiatrygarden.activity.mine.cutquestion.adapter.ChooseChapterAdpNew.JumpToQList
    public void getExCo() {
    }

    @Override // com.psychiatrygarden.activity.mine.cutquestion.adapter.ChooseChapterAdpNew.JumpToQList
    public void getPassData(String activity_id) {
        NewToast.showShort(this, "该章节未解锁");
    }

    public void getQuestionBankData(String identityId) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("identity_id", identityId);
        YJYHttpUtils.get(this, NetworkRequestsURL.chooseChapter, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.ChooseChapterAct.2
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                ChooseChapterAct.this.mRefresh.finishRefresh(false);
                ChooseChapterAct.this.setEmptyView(true);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass2) s2);
                try {
                    QuestionBankNewBean questionBankNewBean = (QuestionBankNewBean) new Gson().fromJson(s2, QuestionBankNewBean.class);
                    ChooseChapterAct.this.mRefresh.finishRefresh("200".equals(questionBankNewBean.getCode()));
                    if (!"200".equals(questionBankNewBean.getCode())) {
                        if ("202".equals(questionBankNewBean.getCode())) {
                            ChooseChapterAct.this.setEmptyView(true);
                            return;
                        }
                        return;
                    }
                    if (questionBankNewBean.getData() == null || questionBankNewBean.getData().size() <= 0) {
                        ChooseChapterAct.this.setEmptyView(false);
                        return;
                    }
                    ChooseChapterAct.this.dataList = questionBankNewBean.getData();
                    for (int i2 = 0; i2 < ChooseChapterAct.this.dataList.size(); i2++) {
                        ArrayList arrayList = new ArrayList();
                        if (ChooseChapterAct.this.dataList.get(i2).getChildren() != null && ChooseChapterAct.this.dataList.get(i2).getChildren().size() > 0) {
                            for (int i3 = 0; i3 < ChooseChapterAct.this.dataList.get(i2).getChildren().size(); i3++) {
                                if (ChooseChapterAct.this.mChooseChapterIds.contains(ChooseChapterAct.this.dataList.get(i2).getChildren().get(i3).getChapter_id())) {
                                    ChooseChapterAct.this.dataList.get(i2).getChildren().get(i3).setIsSelected(1);
                                    ChooseChapterAct.this.dataList.get(i2).getChildIds().add(ChooseChapterAct.this.dataList.get(i2).getChildren().get(i3).getChapter_id());
                                }
                                ChooseChapterAct.this.dataList.get(i2).getChildren().get(i3).setPrimary_p_id(ChooseChapterAct.this.dataList.get(i2).getChapter_id() + "");
                                ChooseChapterAct.this.dataList.get(i2).getChildren().get(i3).setGroupPosition(i2);
                                ChooseChapterAct.this.dataList.get(i2).getChildren().get(i3).setParent_title(ChooseChapterAct.this.dataList.get(i2).getTitle());
                                arrayList.add(ChooseChapterAct.this.dataList.get(i2).getChildren().get(i3));
                            }
                            if (ChooseChapterAct.this.dataList.get(i2).getChildIds().size() <= 0) {
                                ChooseChapterAct.this.dataList.get(i2).setIsSelected(0);
                            } else if (ChooseChapterAct.this.dataList.get(i2).getChildIds().size() == ChooseChapterAct.this.dataList.get(i2).getChildren().size()) {
                                ChooseChapterAct.this.dataList.get(i2).setIsSelected(1);
                            } else {
                                ChooseChapterAct.this.dataList.get(i2).setIsSelected(2);
                            }
                        }
                        ChooseChapterAct.this.dataList.get(i2).setChildren(arrayList);
                    }
                    ChooseChapterAct chooseChapterAct = ChooseChapterAct.this;
                    chooseChapterAct.adapter.setList(chooseChapterAct.getEntity(chooseChapterAct.dataList));
                    ChooseChapterAct.this.emptyView.setVisibility(8);
                } catch (Exception e2) {
                    e2.printStackTrace();
                    ChooseChapterAct.this.setEmptyView(true);
                    ChooseChapterAct.this.mRefresh.finishRefresh(false);
                }
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.identityId = getIntent().getStringExtra("identityId");
        String stringExtra = getIntent().getStringExtra("chapterIds");
        boolean booleanExtra = getIntent().getBooleanExtra("isChooseAll", false);
        this.emptyView = (CustomEmptyView) findViewById(R.id.emptyView);
        this.mRefresh = (SmartRefreshLayout) findViewById(R.id.mSwipeLayput);
        this.mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        this.mBtnSure = (TextView) findViewById(R.id.btn_sure);
        this.imgBack = (ImageView) findViewById(R.id.iv_export_back);
        this.tvTitle = (TextView) findViewById(R.id.tv_export_title);
        this.tvAllSelect = (TextView) findViewById(R.id.tv_export_all_select);
        View viewInflate = View.inflate(this.mContext, R.layout.layout_loaddata_fail_view, null);
        this.mLoadDataFailView = viewInflate;
        TextView textView = (TextView) viewInflate.findViewById(R.id.btn_reload);
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        ChooseChapterAdpNew chooseChapterAdpNew = new ChooseChapterAdpNew(this, false, true);
        this.adapter = chooseChapterAdpNew;
        chooseChapterAdpNew.setAnimationWithDefault(BaseQuickAdapter.AnimationType.AlphaIn);
        this.mRecyclerView.setAdapter(this.adapter);
        this.isSelectAllChapter = booleanExtra;
        if (!TextUtils.isEmpty(stringExtra)) {
            this.mChooseChapterIds = (List) new Gson().fromJson(stringExtra, new TypeToken<List<String>>() { // from class: com.psychiatrygarden.ChooseChapterAct.1
            }.getType());
        }
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.c
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15299c.lambda$init$1(view);
            }
        });
        this.imgBack.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.d
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15300c.lambda$init$2(view);
            }
        });
        this.mRefresh.setOnRefreshListener(new OnRefreshListener() { // from class: com.psychiatrygarden.e
            @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
            public final void onRefresh(RefreshLayout refreshLayout) {
                this.f15301c.lambda$init$3(refreshLayout);
            }
        });
        this.emptyView.setOnReloadData(new View.OnClickListener() { // from class: com.psychiatrygarden.f
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15310c.lambda$init$4(view);
            }
        });
        getQuestionBankData(this.identityId);
    }

    @Override // com.psychiatrygarden.activity.mine.cutquestion.adapter.ChooseChapterAdpNew.JumpToQList
    public void mChildSelectClumn(int groupPosition, int childPosition) {
        setRefulData(groupPosition);
    }

    @Override // com.psychiatrygarden.activity.mine.cutquestion.adapter.ChooseChapterAdpNew.JumpToQList
    public void mGroupSelectClumn(int groupPosition) {
        setCustomerTitleCount(getChooseQuestionCountList());
        List<QuestionBankNewBean.DataBean> list = this.dataList;
        if (list == null || list.size() <= 0) {
            return;
        }
        for (int i2 = 0; i2 < this.dataList.size(); i2++) {
            if (this.dataList.get(i2) != null) {
                QuestionBankNewBean.DataBean dataBean = this.dataList.get(i2);
                if (dataBean.getPass().equals("1") && dataBean.getIsSelected() != 1) {
                    this.isSelectAllChapter = false;
                    this.tvAllSelect.setText("全选");
                    return;
                }
            }
        }
        this.isSelectAllChapter = true;
        this.tvAllSelect.setText("取消全选");
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
        if (getChooseQuestionCountList() > 0) {
            setCustomerTitleCount(getChooseQuestionCountList());
        } else {
            setCustomerTitleCount(0);
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        this.mActionBar.hide();
        setContentView(R.layout.layout_choose_chapter);
        setTitle("章节选择");
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.tvAllSelect.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.a
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f10956c.lambda$setListenerForWidget$5(view);
            }
        });
        this.mBtnSure.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.b
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15292c.lambda$setListenerForWidget$6(view);
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
            this.isSelectAllChapter = false;
            this.tvAllSelect.setText("全选");
        } else if (size == 0) {
            this.dataList.get(groupPosition).setIsSelected(0);
            this.isSelectAllChapter = false;
            this.tvAllSelect.setText("全选");
        } else {
            this.dataList.get(groupPosition).setIsSelected(2);
            this.isSelectAllChapter = false;
            this.tvAllSelect.setText("全选");
        }
        setCustomerTitleCount(getChooseQuestionCountList());
        for (int i3 = 0; i3 < this.dataList.size(); i3++) {
            if (this.dataList.get(i3) != null) {
                QuestionBankNewBean.DataBean dataBean = this.dataList.get(i3);
                if (dataBean.getPass().equals("1") && dataBean.getIsSelected() != 1) {
                    return;
                }
            }
        }
        this.isSelectAllChapter = true;
        this.tvAllSelect.setText("取消全选");
    }
}
