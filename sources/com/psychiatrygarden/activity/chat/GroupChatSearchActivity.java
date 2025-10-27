package com.psychiatrygarden.activity.chat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.listener.OnLoadMoreListener;
import com.google.gson.Gson;
import com.hyphenate.easeui.constants.EaseConstant;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.adapter.GroupChatListAdapter;
import com.psychiatrygarden.bean.GroupChatListBean;
import com.psychiatrygarden.http.ChatRequest;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.interfaceclass.QuestionDataCallBack;
import com.psychiatrygarden.utils.EventBusConstant;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.ToastUtil;
import com.psychiatrygarden.widget.ClearEditText;
import com.psychiatrygarden.widget.CustomLoadMoreView;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class GroupChatSearchActivity extends BaseActivity implements OnItemClickListener, OnItemChildClickListener, OnLoadMoreListener, QuestionDataCallBack<String>, SwipeRefreshLayout.OnRefreshListener {
    private CustomLoadMoreView customLoadMoreView;
    private ClearEditText ed_search;
    private GroupChatListAdapter groupChatNameAdapter;
    private RecyclerView rv_group_chat_name;
    private SwipeRefreshLayout swipe;
    List<GroupChatListBean.DataDTO> groupChatList = new ArrayList();
    private int page = 1;
    private String search_content = "";
    private String id = "";

    private void initRv() {
        this.rv_group_chat_name.setLayoutManager(new LinearLayoutManager(this.mContext));
        this.groupChatNameAdapter = new GroupChatListAdapter(this.groupChatList);
        this.customLoadMoreView = new CustomLoadMoreView();
        this.groupChatNameAdapter.getLoadMoreModule().setLoadMoreView(this.customLoadMoreView);
        this.groupChatNameAdapter.getLoadMoreModule().setEnableLoadMoreIfNotFullPage(false);
        this.rv_group_chat_name.setAdapter(this.groupChatNameAdapter);
        this.groupChatNameAdapter.setOnItemClickListener(new OnItemClickListener() { // from class: com.psychiatrygarden.activity.chat.x
            @Override // com.chad.library.adapter.base.listener.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
                this.f11178c.onItemClick(baseQuickAdapter, view, i2);
            }
        });
        this.groupChatNameAdapter.addChildClickViewIds(R.id.tv_add_remove);
        this.groupChatNameAdapter.getLoadMoreModule().setOnLoadMoreListener(new OnLoadMoreListener() { // from class: com.psychiatrygarden.activity.chat.y
            @Override // com.chad.library.adapter.base.listener.OnLoadMoreListener
            public final void onLoadMore() {
                this.f11179c.onLoadMore();
            }
        });
        this.groupChatNameAdapter.setOnItemChildClickListener(new OnItemChildClickListener() { // from class: com.psychiatrygarden.activity.chat.z
            @Override // com.chad.library.adapter.base.listener.OnItemChildClickListener
            public final void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
                this.f11180c.onItemChildClick(baseQuickAdapter, view, i2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$0(View view) {
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$setListenerForWidget$1(TextView textView, int i2, KeyEvent keyEvent) {
        if (i2 != 3) {
            return false;
        }
        InputMethodManager inputMethodManager = (InputMethodManager) textView.getContext().getSystemService("input_method");
        if (inputMethodManager.isActive()) {
            inputMethodManager.hideSoftInputFromWindow(textView.getApplicationWindowToken(), 0);
        }
        if (!this.ed_search.getText().toString().equals("")) {
            this.search_content = this.ed_search.getText().toString();
            this.page = 1;
            ChatRequest.getIntance(this).chatGroupList("", this.search_content, this.page, this);
        }
        return true;
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.mTxtActionbarTitle.setText("群组搜索");
        this.ed_search = (ClearEditText) findViewById(R.id.ed_search);
        this.swipe = (SwipeRefreshLayout) findViewById(R.id.mSwipeLayput);
        this.rv_group_chat_name = (RecyclerView) findViewById(R.id.rv_group_chat_name);
        initRv();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().clearFlags(131072);
        getWindow().setSoftInputMode(5);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
        if (str.equals(EventBusConstant.EVENT_CHAT_GROUP_EXIT)) {
            this.swipe.setRefreshing(true);
            this.page = 1;
            ChatRequest.getIntance(this).chatGroupList("", this.search_content, this.page, this);
        } else if (str.equals(EventBusConstant.EVENT_UPDATA_GROUP_INFO)) {
            new Handler().postDelayed(new Runnable() { // from class: com.psychiatrygarden.activity.chat.GroupChatSearchActivity.2
                @Override // java.lang.Runnable
                public void run() {
                    GroupChatSearchActivity.this.swipe.setRefreshing(true);
                    GroupChatSearchActivity.this.page = 1;
                    ChatRequest.getIntance(GroupChatSearchActivity.this).chatGroupList("", GroupChatSearchActivity.this.search_content, GroupChatSearchActivity.this.page, GroupChatSearchActivity.this);
                }
            }, 1000L);
        }
    }

    @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
    public void onFailure(Throwable t2, int errorNo, String strMsg, String requstUrl) {
        if (this.swipe.isRefreshing()) {
            this.swipe.setRefreshing(false);
        }
        hideProgressDialog();
    }

    @Override // com.chad.library.adapter.base.listener.OnItemChildClickListener
    public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
        if (isLogin() && view.getId() == R.id.tv_add_remove) {
            this.id = this.groupChatList.get(position).getId();
            ChatRequest.getIntance(this).chatJoin(this.groupChatList.get(position).getId(), this.groupChatList.get(position).getCommunity_id(), this);
        }
    }

    @Override // com.chad.library.adapter.base.listener.OnItemClickListener
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        if (isLogin() && this.groupChatList.get(position).getIs_join().equals("1")) {
            Intent intent = new Intent(this, (Class<?>) ChatActivity.class);
            intent.putExtra(EaseConstant.EXTRA_CHAT_TYPE, 2);
            intent.putExtra("name", this.groupChatList.get(position).getName());
            intent.putExtra("id", this.groupChatList.get(position).getId());
            intent.putExtra("group_img", this.groupChatList.get(position).getLogo());
            intent.putExtra(EaseConstant.EXTRA_CONVERSATION_ID, this.groupChatList.get(position).getCommunity_id());
            startActivity(intent);
        }
    }

    @Override // com.chad.library.adapter.base.listener.OnLoadMoreListener
    public void onLoadMore() {
        if (TextUtils.isEmpty(this.search_content)) {
            return;
        }
        this.page++;
        ChatRequest.getIntance(this).chatGroupList("", this.search_content, this.page, this);
    }

    @Override // androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
    public void onRefresh() {
        if (TextUtils.isEmpty(this.search_content)) {
            return;
        }
        this.swipe.setRefreshing(true);
        this.page = 1;
        ChatRequest.getIntance(this).chatGroupList("", this.search_content, this.page, this);
    }

    @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
    public void onStart(String requstUrl) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_group_chat_search);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.mBtnActionbarBack.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chat.v
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11176c.lambda$setListenerForWidget$0(view);
            }
        });
        this.swipe.setOnRefreshListener(this);
        if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
            this.swipe.setProgressBackgroundColorSchemeColor(this.mContext.getResources().getColor(R.color.white));
            this.swipe.setColorSchemeResources(android.R.color.holo_red_light, android.R.color.holo_red_light, android.R.color.holo_red_light, android.R.color.holo_red_light);
        } else {
            this.swipe.setProgressBackgroundColorSchemeColor(this.mContext.getResources().getColor(R.color.input_night_color));
            this.swipe.setColorSchemeResources(R.color.question_color_night, R.color.question_color_night, R.color.question_color_night, R.color.question_color_night);
        }
        this.swipe.post(new Runnable() { // from class: com.psychiatrygarden.activity.chat.GroupChatSearchActivity.1
            @Override // java.lang.Runnable
            public void run() {
                if (TextUtils.isEmpty(GroupChatSearchActivity.this.search_content)) {
                    return;
                }
                GroupChatSearchActivity.this.swipe.setRefreshing(true);
                GroupChatSearchActivity.this.page = 1;
                ChatRequest.getIntance(GroupChatSearchActivity.this).chatGroupList("", GroupChatSearchActivity.this.search_content, GroupChatSearchActivity.this.page, GroupChatSearchActivity.this);
            }
        });
        this.ed_search.setOnEditorActionListener(new TextView.OnEditorActionListener() { // from class: com.psychiatrygarden.activity.chat.w
            @Override // android.widget.TextView.OnEditorActionListener
            public final boolean onEditorAction(TextView textView, int i2, KeyEvent keyEvent) {
                return this.f11177c.lambda$setListenerForWidget$1(textView, i2, keyEvent);
            }
        });
    }

    @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
    public void onSuccess(String s2, String requstUrl) {
        if (!requstUrl.equals(NetworkRequestsURL.chatListApi)) {
            if (requstUrl.equals(NetworkRequestsURL.chatJoinApi)) {
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if (!jSONObject.optString("code").equals("200")) {
                        ToastUtil.shortToast(this, jSONObject.optString("message"));
                        return;
                    }
                    for (int i2 = 0; i2 < this.groupChatList.size(); i2++) {
                        if (this.id.equals(this.groupChatList.get(i2).getId())) {
                            this.groupChatList.get(i2).setIs_join("1");
                        }
                    }
                    this.groupChatNameAdapter.notifyDataSetChanged();
                    EventBus.getDefault().post(EventBusConstant.EVENT_CHAT_GROUP_ADD);
                    ToastUtil.shortToast(this, "加入成功");
                    return;
                } catch (JSONException e2) {
                    e2.printStackTrace();
                    return;
                }
            }
            return;
        }
        try {
            GroupChatListBean groupChatListBean = (GroupChatListBean) new Gson().fromJson(s2, GroupChatListBean.class);
            if (!groupChatListBean.getCode().equals("200")) {
                if (this.page != 1) {
                    this.groupChatNameAdapter.getLoadMoreModule().loadMoreFail();
                    return;
                } else {
                    if (this.swipe.isRefreshing()) {
                        this.swipe.setRefreshing(false);
                        return;
                    }
                    return;
                }
            }
            if (this.page != 1) {
                this.groupChatList.addAll(groupChatListBean.getData());
                this.groupChatNameAdapter.notifyDataSetChanged();
                if (groupChatListBean.getData().size() > 0) {
                    this.groupChatNameAdapter.getLoadMoreModule().loadMoreComplete();
                    return;
                } else {
                    this.groupChatNameAdapter.getLoadMoreModule().loadMoreEnd();
                    return;
                }
            }
            if (this.swipe.isRefreshing()) {
                this.swipe.setRefreshing(false);
            }
            this.groupChatNameAdapter.setSearchContent(this.search_content);
            List<GroupChatListBean.DataDTO> data = groupChatListBean.getData();
            this.groupChatList = data;
            this.groupChatNameAdapter.setNewInstance(data);
            if (this.groupChatList.size() < 20) {
                this.groupChatNameAdapter.getLoadMoreModule().loadMoreEnd();
                this.groupChatNameAdapter.getLoadMoreModule().setEnableLoadMore(false);
            } else {
                this.groupChatNameAdapter.getLoadMoreModule().setEnableLoadMore(true);
            }
            View viewInflate = LayoutInflater.from(this).inflate(R.layout.adapter_empty_view, (ViewGroup) this.rv_group_chat_name, false);
            ((TextView) viewInflate.findViewById(R.id.tv_content)).setText("暂无群组");
            this.groupChatNameAdapter.setEmptyView(viewInflate);
        } catch (Exception e3) {
            e3.printStackTrace();
        }
    }
}
