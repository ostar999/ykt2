package com.psychiatrygarden.fragmenthome.chat;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.listener.OnLoadMoreListener;
import com.google.gson.Gson;
import com.hyphenate.easeui.constants.EaseConstant;
import com.psychiatrygarden.activity.chat.ChatActivity;
import com.psychiatrygarden.adapter.GroupChatListAdapter;
import com.psychiatrygarden.baseview.BaseFragment;
import com.psychiatrygarden.baseview.ViewHolder;
import com.psychiatrygarden.bean.GroupChatListBean;
import com.psychiatrygarden.http.ChatRequest;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.interfaceclass.QuestionDataCallBack;
import com.psychiatrygarden.utils.EventBusConstant;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.ToastUtil;
import com.psychiatrygarden.widget.CustomLoadMoreView;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class GroupChatListFragment extends BaseFragment implements OnItemClickListener, OnItemChildClickListener, OnLoadMoreListener, QuestionDataCallBack<String>, SwipeRefreshLayout.OnRefreshListener {
    private GroupChatListAdapter groupChatListAdapter;
    private RecyclerView rv_group_chat_name;
    private SwipeRefreshLayout swipe;
    private List<GroupChatListBean.DataDTO> groupChatList = new ArrayList();
    private int page = 1;
    private String category_id = "";
    private String id = "";

    private void initRv() {
        this.rv_group_chat_name.setLayoutManager(new LinearLayoutManager(this.mContext));
        this.groupChatListAdapter = new GroupChatListAdapter(this.groupChatList);
        View viewInflate = LayoutInflater.from(getActivity()).inflate(R.layout.adapter_empty_top_view, (ViewGroup) this.rv_group_chat_name, false);
        ((TextView) viewInflate.findViewById(R.id.tv_content)).setText("暂无社群");
        this.groupChatListAdapter.setEmptyView(viewInflate);
        CustomLoadMoreView customLoadMoreView = new CustomLoadMoreView();
        this.groupChatListAdapter.getLoadMoreModule().setEnableLoadMoreIfNotFullPage(false);
        this.groupChatListAdapter.getLoadMoreModule().setLoadMoreView(customLoadMoreView);
        this.rv_group_chat_name.setAdapter(this.groupChatListAdapter);
        this.groupChatListAdapter.setOnItemClickListener(this);
        this.groupChatListAdapter.addChildClickViewIds(R.id.tv_add_remove);
        this.groupChatListAdapter.getLoadMoreModule().setOnLoadMoreListener(this);
        this.groupChatListAdapter.setOnItemChildClickListener(this);
    }

    public static GroupChatListFragment newInstance() {
        Bundle bundle = new Bundle();
        GroupChatListFragment groupChatListFragment = new GroupChatListFragment();
        groupChatListFragment.setArguments(bundle);
        return groupChatListFragment;
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public int getLayoutId() {
        return R.layout.fragment_group_chat_list;
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void initViews(ViewHolder holder, View root) {
        this.category_id = getArguments().getString("id", "");
        this.swipe = (SwipeRefreshLayout) holder.get(R.id.mSwipeLayput);
        this.rv_group_chat_name = (RecyclerView) holder.get(R.id.rv_group_chat_name);
        this.swipe.setOnRefreshListener(this);
        if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
            this.swipe.setProgressBackgroundColorSchemeColor(ContextCompat.getColor(this.mContext, R.color.white));
            this.swipe.setColorSchemeResources(android.R.color.holo_red_light, android.R.color.holo_red_light, android.R.color.holo_red_light, android.R.color.holo_red_light);
        } else {
            this.swipe.setProgressBackgroundColorSchemeColor(ContextCompat.getColor(this.mContext, R.color.input_night_color));
            this.swipe.setColorSchemeResources(R.color.question_color_night, R.color.question_color_night, R.color.question_color_night, R.color.question_color_night);
        }
        initRv();
        ChatRequest.getIntance(getActivity()).chatGroupList(this.category_id, "", this.page, this);
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void onEventMainThread(String str) {
        if (str.equals(EventBusConstant.EVENT_CHAT_GROUP_EXIT) || str.equals(EventBusConstant.EVENT_CHAT_GROUP_ADD)) {
            this.swipe.setRefreshing(true);
            this.page = 1;
            ChatRequest.getIntance(getActivity()).chatGroupList(this.category_id, "", this.page, this);
        }
    }

    @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
    public void onFailure(Throwable t2, int errorNo, String strMsg, String requstUrl) {
        if (this.swipe.isRefreshing()) {
            this.swipe.setRefreshing(false);
        }
    }

    @Override // com.chad.library.adapter.base.listener.OnItemChildClickListener
    public void onItemChildClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
        if (isLogin() && view.getId() == R.id.tv_add_remove) {
            this.id = this.groupChatList.get(position).getId();
            ChatRequest.getIntance(getActivity()).chatJoin(this.groupChatList.get(position).getId(), this.groupChatList.get(position).getCommunity_id(), this);
        }
    }

    @Override // com.chad.library.adapter.base.listener.OnItemClickListener
    public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
        if (isLogin() && this.groupChatList.get(position).getIs_join().equals("1")) {
            Intent intent = new Intent(getActivity(), (Class<?>) ChatActivity.class);
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
        if (TextUtils.isEmpty(this.category_id)) {
            return;
        }
        this.page++;
        ChatRequest.getIntance(getActivity()).chatGroupList(this.category_id, "", this.page, this);
    }

    @Override // androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
    public void onRefresh() {
        if (TextUtils.isEmpty(this.category_id)) {
            return;
        }
        this.swipe.setRefreshing(true);
        this.page = 1;
        ChatRequest.getIntance(getActivity()).chatGroupList(this.category_id, "", this.page, this);
    }

    @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
    public void onStart(String requstUrl) {
    }

    @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
    public void onSuccess(String s2, String requstUrl) {
        try {
            if (!requstUrl.equals(NetworkRequestsURL.chatListApi)) {
                if (requstUrl.equals(NetworkRequestsURL.chatJoinApi)) {
                    try {
                        JSONObject jSONObject = new JSONObject(s2);
                        if (!jSONObject.optString("code").equals("200")) {
                            ToastUtil.shortToast(getActivity(), jSONObject.optString("message"));
                            return;
                        }
                        for (int i2 = 0; i2 < this.groupChatList.size(); i2++) {
                            if (this.id.equals(this.groupChatList.get(i2).getId())) {
                                this.groupChatList.get(i2).setIs_join("1");
                            }
                        }
                        this.groupChatListAdapter.notifyDataSetChanged();
                        ToastUtil.shortToast(getActivity(), "加入成功");
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
                        this.groupChatListAdapter.getLoadMoreModule().loadMoreFail();
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
                    this.groupChatListAdapter.notifyDataSetChanged();
                    if (groupChatListBean.getData().size() > 0) {
                        this.groupChatListAdapter.getLoadMoreModule().loadMoreComplete();
                        return;
                    } else {
                        this.groupChatListAdapter.getLoadMoreModule().loadMoreEnd();
                        return;
                    }
                }
                if (this.swipe.isRefreshing()) {
                    this.swipe.setRefreshing(false);
                }
                List<GroupChatListBean.DataDTO> data = groupChatListBean.getData();
                this.groupChatList = data;
                this.groupChatListAdapter.setNewInstance(data);
                if (this.groupChatList.size() >= 10) {
                    this.groupChatListAdapter.getLoadMoreModule().setEnableLoadMore(true);
                    return;
                } else {
                    this.groupChatListAdapter.getLoadMoreModule().loadMoreEnd();
                    this.groupChatListAdapter.getLoadMoreModule().setEnableLoadMore(false);
                    return;
                }
            } catch (Exception e3) {
                if (this.swipe.isRefreshing()) {
                    this.swipe.setRefreshing(false);
                }
                e3.printStackTrace();
                return;
            }
        } catch (Exception e4) {
            e4.printStackTrace();
        }
        e4.printStackTrace();
    }
}
