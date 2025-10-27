package com.psychiatrygarden.activity.online;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.aliyun.player.alivcplayerexpand.util.database.DatabaseManager;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.google.gson.Gson;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.online.adapter.QuestionErrorCorrectionReplyAdapter;
import com.psychiatrygarden.activity.online.bean.QuestionErroCorrectionBean;
import com.psychiatrygarden.bean.EventBusMessage;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.QuestionDataRequest;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.interfaceclass.QuestionDataCallBack;
import com.psychiatrygarden.interfaceclass.onDialogClickListener;
import com.psychiatrygarden.utils.Constants;
import com.psychiatrygarden.utils.EventBusConstant;
import com.psychiatrygarden.utils.NewToast;
import com.psychiatrygarden.utils.ToastUtil;
import com.psychiatrygarden.widget.DialogInput;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class ErrorCorrectionReplyActivity extends BaseActivity implements QuestionDataCallBack<String>, View.OnClickListener, OnItemChildClickListener {
    private static final int pageSize = 20;
    private QuestionErroCorrectionBean.DataDTO dataDTO;
    private QuestionErrorCorrectionReplyAdapter questionErrorCorrectionReplyAdapter;
    private SmartRefreshLayout refreshLayout;
    private TextView tv_topic_detail_add_comment;
    private String identity_id = "";
    private String type = "";
    private String restore_analyze = "";
    private int page = 1;
    private final List<QuestionErroCorrectionBean.DataDTO> list = new ArrayList();

    /* JADX INFO: Access modifiers changed from: private */
    public void getRestoreListData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("parent_id", "" + this.dataDTO.getId());
        ajaxParams.put(Constants.PARAMS_CONSTANTS.PARAMS_PAGE, "" + this.page);
        ajaxParams.put(DatabaseManager.SIZE, "20");
        QuestionDataRequest.getIntance(this.mContext).errorCorrectionReplyList(ajaxParams, this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onClick$2(String str, String str2, String str3) {
        putComment(str, this.dataDTO.getQuestion_id(), this.dataDTO.getId(), 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onItemChildClick$3(QuestionErroCorrectionBean.DataDTO dataDTO, int i2, String str, String str2, String str3) {
        putComment(str, dataDTO.getQuestion_id(), dataDTO.getId(), i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$0(RefreshLayout refreshLayout) {
        this.page = 1;
        getRestoreListData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$1(RefreshLayout refreshLayout) {
        this.page++;
        getRestoreListData();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        setTitle("回复合集");
        this.identity_id = getIntent().getStringExtra("identity_unit");
        this.restore_analyze = getIntent().getStringExtra("restore_analyze");
        this.type = getIntent().getStringExtra("type");
        this.dataDTO = (QuestionErroCorrectionBean.DataDTO) getIntent().getSerializableExtra("bean");
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_reply);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.mContext));
        this.tv_topic_detail_add_comment = (TextView) findViewById(R.id.tv_topic_detail_add_comment);
        this.refreshLayout = (SmartRefreshLayout) findViewById(R.id.refreshid);
        QuestionErrorCorrectionReplyAdapter questionErrorCorrectionReplyAdapter = new QuestionErrorCorrectionReplyAdapter(this.list);
        this.questionErrorCorrectionReplyAdapter = questionErrorCorrectionReplyAdapter;
        questionErrorCorrectionReplyAdapter.setOnItemChildClickListener(this);
        recyclerView.setAdapter(this.questionErrorCorrectionReplyAdapter);
        this.refreshLayout.autoRefresh();
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v2) {
        if (v2.getId() == R.id.tv_topic_detail_add_comment) {
            new DialogInput(this.mContext, new onDialogClickListener() { // from class: com.psychiatrygarden.activity.online.g1
                @Override // com.psychiatrygarden.interfaceclass.onDialogClickListener
                public final void onclickStringBack(String str, String str2, String str3) {
                    this.f13413a.lambda$onClick$2(str, str2, str3);
                }
            }, "", "回复" + this.dataDTO.getNickname(), false, true).show();
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
    public void onFailure(Throwable t2, int errorNo, String strMsg, String requstUrl) {
        this.refreshLayout.finishRefresh();
        this.refreshLayout.finishLoadMore();
    }

    @Override // com.chad.library.adapter.base.listener.OnItemChildClickListener
    public void onItemChildClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, final int position) {
        final QuestionErroCorrectionBean.DataDTO dataDTO = this.list.get(position);
        if (Integer.parseInt(dataDTO.getReply_num()) > 0 && position > 0) {
            Intent intent = new Intent(this.mContext, (Class<?>) ErrorCorrectionReplyActivity.class);
            intent.putExtra("type", this.type);
            intent.putExtra("identity_unit", this.identity_id);
            intent.putExtra("bean", dataDTO);
            startActivity(intent);
            return;
        }
        new DialogInput(this.mContext, new onDialogClickListener() { // from class: com.psychiatrygarden.activity.online.h1
            @Override // com.psychiatrygarden.interfaceclass.onDialogClickListener
            public final void onclickStringBack(String str, String str2, String str3) {
                this.f13421a.lambda$onItemChildClick$3(dataDTO, position, str, str2, str3);
            }
        }, "", "回复" + dataDTO.getNickname(), false, true).show();
    }

    @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
    public void onStart(String requstUrl) {
    }

    public void putComment(String content, String question_id, String parent_id, final int position) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("content", content);
        ajaxParams.put("parent_id", parent_id);
        ajaxParams.put("question_id", question_id);
        ajaxParams.put("type", this.type);
        ajaxParams.put("identity_unit", this.identity_id);
        YJYHttpUtils.post(this.mContext.getApplicationContext(), NetworkRequestsURL.errorCorrectionSaveURL, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.online.ErrorCorrectionReplyActivity.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                NewToast.showShort(ErrorCorrectionReplyActivity.this.mContext, "请求服务器超时！", 0).show();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass1) s2);
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if (jSONObject.optString("code").equals("200")) {
                        ProjectApp.comment_content = "";
                        ProjectApp.comment_b_img = "";
                        ProjectApp.comment_s_img = "";
                        ProjectApp.commentvideoPath = "";
                        ProjectApp.commentvideoImage = "";
                        ProjectApp.commentvideoId = "";
                        ProjectApp.commentvideoImagePath = "";
                        NewToast.showShort(ProjectApp.instance(), "评论成功");
                        EventBus.getDefault().post(EventBusConstant.EVENT_DIALOG_INPUT_DISMISS);
                        if (position == 0) {
                            ErrorCorrectionReplyActivity.this.dataDTO.setReply_num((Integer.parseInt(ErrorCorrectionReplyActivity.this.dataDTO.getReply_num()) + 1) + "");
                            ErrorCorrectionReplyActivity.this.getRestoreListData();
                            if (!TextUtils.isEmpty(ErrorCorrectionReplyActivity.this.restore_analyze)) {
                                EventBus.getDefault().post(new EventBusMessage(EventBusConstant.EVENT_QUESTION_ERROR_CORRECTION_OK, "", ""));
                            }
                        } else {
                            ((QuestionErroCorrectionBean.DataDTO) ErrorCorrectionReplyActivity.this.list.get(position)).setReply_num((Integer.parseInt(((QuestionErroCorrectionBean.DataDTO) ErrorCorrectionReplyActivity.this.list.get(position)).getReply_num()) + 1) + "");
                            ErrorCorrectionReplyActivity.this.questionErrorCorrectionReplyAdapter.notifyDataSetChanged();
                        }
                    } else {
                        NewToast.showShort(ErrorCorrectionReplyActivity.this.mContext, jSONObject.optString("message"), 0).show();
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_error_correction_replay);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.refreshLayout.setOnRefreshListener(new OnRefreshListener() { // from class: com.psychiatrygarden.activity.online.e1
            @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
            public final void onRefresh(RefreshLayout refreshLayout) {
                this.f13162c.lambda$setListenerForWidget$0(refreshLayout);
            }
        });
        this.refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() { // from class: com.psychiatrygarden.activity.online.f1
            @Override // com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
            public final void onLoadMore(RefreshLayout refreshLayout) {
                this.f13167c.lambda$setListenerForWidget$1(refreshLayout);
            }
        });
        this.tv_topic_detail_add_comment.setOnClickListener(this);
    }

    @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
    public void onSuccess(String s2, String requstUrl) {
        if (requstUrl.equals(NetworkRequestsURL.errorCorrectionReplyListURL)) {
            this.refreshLayout.finishRefresh();
            this.refreshLayout.finishLoadMore();
            try {
                QuestionErroCorrectionBean questionErroCorrectionBean = (QuestionErroCorrectionBean) new Gson().fromJson(s2, QuestionErroCorrectionBean.class);
                if (!questionErroCorrectionBean.getCode().equals("200")) {
                    ToastUtil.shortToast(this.mContext, questionErroCorrectionBean.getMessage());
                } else if (this.page == 1) {
                    this.list.clear();
                    this.questionErrorCorrectionReplyAdapter.setEmptyView(R.layout.adapter_empty_view);
                    this.list.add(this.dataDTO);
                    this.list.addAll(questionErroCorrectionBean.getData());
                    this.questionErrorCorrectionReplyAdapter.setList(this.list);
                } else if (questionErroCorrectionBean.getData() == null || questionErroCorrectionBean.getData().size() <= 0) {
                    this.refreshLayout.finishLoadMoreWithNoMoreData();
                } else {
                    this.questionErrorCorrectionReplyAdapter.addData((Collection) questionErroCorrectionBean.getData());
                    this.refreshLayout.finishLoadMore();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }
}
