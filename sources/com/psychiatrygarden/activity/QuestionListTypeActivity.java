package com.psychiatrygarden.activity;

import android.content.Intent;
import android.text.Html;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.google.gson.Gson;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.bean.ContactCustomerBean;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.Constants;
import com.psychiatrygarden.utils.ToastUtil;
import com.psychiatrygarden.widget.ClearEditText;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

/* loaded from: classes5.dex */
public class QuestionListTypeActivity extends BaseActivity {
    public BaseQuickAdapter<ContactCustomerBean.DataDTO, BaseViewHolder> adapterQuestion;
    public ClearEditText ed_search;
    private View mEmptyView;
    public RecyclerView recycleid;
    public SmartRefreshLayout refreshviewid;
    public int page = 1;
    public String category_id = "";
    public String keyword = "";

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$init$0(TextView textView, int i2, KeyEvent keyEvent) {
        if (i2 != 3) {
            return false;
        }
        InputMethodManager inputMethodManager = (InputMethodManager) textView.getContext().getSystemService("input_method");
        if (inputMethodManager.isActive()) {
            inputMethodManager.hideSoftInputFromWindow(textView.getApplicationWindowToken(), 0);
        }
        if (!this.ed_search.getText().toString().equals("")) {
            this.page = 1;
            this.adapterQuestion.setList(new ArrayList());
            getQuestionList();
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$1(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
        Intent intent = new Intent(this, (Class<?>) QuestionDetailActivity.class);
        intent.putExtra("id", "" + this.adapterQuestion.getData().get(i2).getId());
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$2(RefreshLayout refreshLayout) {
        this.page = 1;
        getQuestionList();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$3(RefreshLayout refreshLayout) {
        this.page++;
        getQuestionList();
    }

    public void getQuestionList() {
        AjaxParams ajaxParams = new AjaxParams();
        if (!TextUtils.isEmpty(this.category_id)) {
            ajaxParams.put("category_id", this.category_id);
        }
        if (!TextUtils.isEmpty(this.ed_search.getText().toString())) {
            ajaxParams.put(Constants.PARAMS_CONSTANTS.PARAMS_KEYWORD, "" + this.ed_search.getText().toString());
        }
        ajaxParams.put(Constants.PARAMS_CONSTANTS.PARAMS_PAGE, "" + this.page);
        ajaxParams.put("limit", "50");
        YJYHttpUtils.get(this, NetworkRequestsURL.missueApi, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.QuestionListTypeActivity.2
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                ProjectApp.instance().hideDialogWindow();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                ProjectApp.instance().showDialogWindow();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass2) s2);
                ProjectApp.instance().hideDialogWindow();
                try {
                    ContactCustomerBean contactCustomerBean = (ContactCustomerBean) new Gson().fromJson(s2, ContactCustomerBean.class);
                    if (!contactCustomerBean.getCode().equals("200")) {
                        ToastUtil.shortToast(QuestionListTypeActivity.this, contactCustomerBean.getMessage() + "");
                    } else if (contactCustomerBean.getData().size() > 0) {
                        QuestionListTypeActivity.this.adapterQuestion.setList(contactCustomerBean.getData());
                    } else {
                        ToastUtil.shortToast(QuestionListTypeActivity.this, "未找到相关搜索结果");
                    }
                    QuestionListTypeActivity.this.mEmptyView.setVisibility(0);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.refreshviewid = (SmartRefreshLayout) findViewById(R.id.refreshviewid);
        this.category_id = getIntent().getExtras().getString("category_id", "");
        this.keyword = getIntent().getExtras().getString(Constants.PARAMS_CONSTANTS.PARAMS_KEYWORD, "");
        ClearEditText clearEditText = (ClearEditText) findViewById(R.id.ed_search);
        this.ed_search = clearEditText;
        clearEditText.setVisibility(0);
        this.ed_search.setText(this.keyword);
        this.refreshviewid.setEnableRefresh(false);
        this.refreshviewid.setEnableLoadMore(false);
        this.ed_search.setOnEditorActionListener(new TextView.OnEditorActionListener() { // from class: com.psychiatrygarden.activity.pf
            @Override // android.widget.TextView.OnEditorActionListener
            public final boolean onEditorAction(TextView textView, int i2, KeyEvent keyEvent) {
                return this.f13542c.lambda$init$0(textView, i2, keyEvent);
            }
        });
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycleid);
        this.recycleid = recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.adapterQuestion = new BaseQuickAdapter<ContactCustomerBean.DataDTO, BaseViewHolder>(R.layout.layout_contact_question) { // from class: com.psychiatrygarden.activity.QuestionListTypeActivity.1
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(@NonNull BaseViewHolder holder, ContactCustomerBean.DataDTO item) {
                String title = item.getTitle();
                if (!QuestionListTypeActivity.this.ed_search.getText().toString().equals("")) {
                    title = title.replaceAll(QuestionListTypeActivity.this.ed_search.getText().toString(), "<font color='#E25D49'>" + QuestionListTypeActivity.this.ed_search.getText().toString() + "</font>");
                }
                holder.setText(R.id.questionTitle, Html.fromHtml(title));
            }
        };
        this.mEmptyView = View.inflate(this.mContext, R.layout.layout_empty_view, null);
        this.recycleid.setAdapter(this.adapterQuestion);
        this.adapterQuestion.setEmptyView(this.mEmptyView);
        this.adapterQuestion.setOnItemClickListener(new OnItemClickListener() { // from class: com.psychiatrygarden.activity.qf
            @Override // com.chad.library.adapter.base.listener.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
                this.f13735c.lambda$init$1(baseQuickAdapter, view, i2);
            }
        });
        this.refreshviewid.setOnRefreshListener(new OnRefreshListener() { // from class: com.psychiatrygarden.activity.rf
            @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
            public final void onRefresh(RefreshLayout refreshLayout) {
                this.f13798c.lambda$init$2(refreshLayout);
            }
        });
        this.refreshviewid.setOnLoadMoreListener(new OnLoadMoreListener() { // from class: com.psychiatrygarden.activity.sf
            @Override // com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
            public final void onLoadMore(RefreshLayout refreshLayout) {
                this.f13919c.lambda$init$3(refreshLayout);
            }
        });
        this.mEmptyView.setVisibility(8);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_question_list_type);
        setTitle("" + getIntent().getExtras().getString("title"));
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}
