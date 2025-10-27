package com.psychiatrygarden.activity;

import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import com.aliyun.vod.common.utils.UriUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.google.gson.Gson;
import com.psychiatrygarden.activity.online.bean.QuestionBankNewBean;
import com.psychiatrygarden.adapter.ExportNoteAdapter;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.yikaobang.yixue.R;
import java.util.Iterator;
import java.util.List;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

/* loaded from: classes5.dex */
public class ActExportNote extends BaseActivity implements View.OnClickListener {
    private String mCategory;
    private String mCategoryId;
    private String mExportFuncIdentityId;
    private String mIdentityId;
    private ExportNoteAdapter mNoteAdapter;
    private String mUnitId;
    private String moduleType;
    private boolean selectAll;
    private int totalCount;

    public static /* synthetic */ int access$012(ActExportNote actExportNote, int i2) {
        int i3 = actExportNote.totalCount + i2;
        actExportNote.totalCount = i3;
        return i3;
    }

    private void exportNote() {
    }

    private void getNoteData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put(UriUtil.QUERY_CATEGORY, this.mCategory);
        ajaxParams.put("identity_id", this.mIdentityId);
        ajaxParams.put("type", "note");
        ajaxParams.put("unit_id", this.mUnitId);
        ajaxParams.put("module_type", this.moduleType);
        YJYHttpUtils.post(this, NetworkRequestsURL.getchapterApi, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.ActExportNote.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass1) s2);
                try {
                    QuestionBankNewBean questionBankNewBean = (QuestionBankNewBean) new Gson().fromJson(s2, QuestionBankNewBean.class);
                    if (!"200".equals(questionBankNewBean.code) || questionBankNewBean.getData() == null) {
                        return;
                    }
                    for (QuestionBankNewBean.DataBean dataBean : questionBankNewBean.getData()) {
                        if (dataBean.getChildren() != null) {
                            ActExportNote.access$012(ActExportNote.this, dataBean.getChildren().size());
                        } else if (dataBean.getCount() > 0) {
                            ActExportNote.access$012(ActExportNote.this, 1);
                        }
                        dataBean.setExpanded(false);
                    }
                    ActExportNote.this.mNoteAdapter.setList(questionBankNewBean.getData());
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    private int getSelectCount() {
        int count = 0;
        for (BaseNode baseNode : this.mNoteAdapter.getData()) {
            if (baseNode instanceof QuestionBankNewBean.DataBean) {
                QuestionBankNewBean.DataBean dataBean = (QuestionBankNewBean.DataBean) baseNode;
                List<QuestionBankNewBean.DataBean.ChildrenBean> children = dataBean.getChildren();
                if (children != null) {
                    for (QuestionBankNewBean.DataBean.ChildrenBean childrenBean : children) {
                        if (childrenBean.isSelected == 1) {
                            count += childrenBean.getCount();
                        }
                    }
                } else if (dataBean.getCount() > 0) {
                    count += dataBean.getCount();
                }
            }
        }
        return count;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(int i2) {
        updateExportState();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$1(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
        if (view.getId() == R.id.iv_exp_col) {
            BaseNode item = this.mNoteAdapter.getItem(i2);
            if (item instanceof QuestionBankNewBean.DataBean) {
                if (((QuestionBankNewBean.DataBean) item).getIsExpanded()) {
                    this.mNoteAdapter.collapse(i2);
                } else {
                    this.mNoteAdapter.expand(i2, true);
                }
            }
        }
    }

    private void updateExportState() {
        int selectCount = getSelectCount();
        int i2 = 0;
        findViewById(R.id.tv_export).setEnabled(selectCount > 0);
        findViewById(R.id.tv_export).setClickable(selectCount > 0);
        ((TextView) findViewById(R.id.title)).setText(String.format("已选择%d条笔记", Integer.valueOf(selectCount)));
        for (BaseNode baseNode : this.mNoteAdapter.getData()) {
            if (baseNode instanceof QuestionBankNewBean.DataBean) {
                QuestionBankNewBean.DataBean dataBean = (QuestionBankNewBean.DataBean) baseNode;
                List<QuestionBankNewBean.DataBean.ChildrenBean> children = dataBean.getChildren();
                if (children != null) {
                    Iterator<QuestionBankNewBean.DataBean.ChildrenBean> it = children.iterator();
                    while (it.hasNext()) {
                        if (it.next().getIsSelected() == 1) {
                            i2++;
                        }
                    }
                } else if (dataBean.isSelected == 1) {
                    i2++;
                }
            }
        }
        TextView textView = (TextView) findViewById(R.id.tv_select_all);
        int i3 = this.totalCount;
        if (i2 == i3) {
            this.selectAll = true;
            textView.setText("取消全选");
        } else if (i2 < i3) {
            textView.setText("全选");
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.moduleType = getIntent().getStringExtra("module_type");
        this.mExportFuncIdentityId = getIntent().getStringExtra("export_func_identity_id");
        this.mIdentityId = getIntent().getStringExtra("identity_id");
        this.mUnitId = getIntent().getStringExtra("unit_id");
        this.mCategory = getIntent().getStringExtra(UriUtil.QUERY_CATEGORY);
        this.mCategoryId = getIntent().getStringExtra("category_id");
        this.mNoteAdapter = new ExportNoteAdapter(new ExportNoteAdapter.SelectListener() { // from class: com.psychiatrygarden.activity.j0
            @Override // com.psychiatrygarden.adapter.ExportNoteAdapter.SelectListener
            public final void select(int i2) {
                this.f12541a.lambda$init$0(i2);
            }
        });
        getNoteData();
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (view.getId() == R.id.tv_export) {
            if (getSelectCount() > 0) {
                exportNote();
                return;
            }
            return;
        }
        if (view.getId() == R.id.iv_close_back) {
            if (getSelectCount() <= 0) {
                finish();
                return;
            }
            for (BaseNode baseNode : this.mNoteAdapter.getData()) {
                if (baseNode instanceof QuestionBankNewBean.DataBean) {
                    QuestionBankNewBean.DataBean dataBean = (QuestionBankNewBean.DataBean) baseNode;
                    dataBean.setIsSelected(0);
                    List<QuestionBankNewBean.DataBean.ChildrenBean> children = dataBean.getChildren();
                    if (children != null) {
                        Iterator<QuestionBankNewBean.DataBean.ChildrenBean> it = children.iterator();
                        while (it.hasNext()) {
                            it.next().setIsSelected(0);
                        }
                    }
                } else if (baseNode instanceof QuestionBankNewBean.DataBean.ChildrenBean) {
                    ((QuestionBankNewBean.DataBean.ChildrenBean) baseNode).setIsSelected(0);
                }
            }
            this.mNoteAdapter.notifyDataSetChanged();
            return;
        }
        if (view.getId() == R.id.tv_select_all) {
            this.selectAll = !this.selectAll;
            for (BaseNode baseNode2 : this.mNoteAdapter.getData()) {
                if (baseNode2 instanceof QuestionBankNewBean.DataBean) {
                    QuestionBankNewBean.DataBean dataBean2 = (QuestionBankNewBean.DataBean) baseNode2;
                    dataBean2.setIsSelected(this.selectAll ? 1 : 0);
                    List<QuestionBankNewBean.DataBean.ChildrenBean> children2 = dataBean2.getChildren();
                    if (children2 != null) {
                        Iterator<QuestionBankNewBean.DataBean.ChildrenBean> it2 = children2.iterator();
                        while (it2.hasNext()) {
                            it2.next().setIsSelected(this.selectAll ? 1 : 0);
                        }
                    }
                }
            }
            this.mNoteAdapter.notifyDataSetChanged();
            ((TextView) view).setText(this.selectAll ? "取消全选" : "全选");
            updateExportState();
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_export_note);
        this.mActionBar.hide();
        setNewStyleStatusBarColor();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        findViewById(R.id.tv_select_all).setOnClickListener(this);
        findViewById(R.id.iv_close_back).setOnClickListener(this);
        findViewById(R.id.tv_export).setOnClickListener(this);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rvNotes);
        if (recyclerView.getItemAnimator() instanceof SimpleItemAnimator) {
            recyclerView.setItemAnimator(null);
        }
        recyclerView.setAdapter(this.mNoteAdapter);
        this.mNoteAdapter.setOnItemChildClickListener(new OnItemChildClickListener() { // from class: com.psychiatrygarden.activity.k0
            @Override // com.chad.library.adapter.base.listener.OnItemChildClickListener
            public final void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
                this.f12573c.lambda$setListenerForWidget$1(baseQuickAdapter, view, i2);
            }
        });
    }
}
