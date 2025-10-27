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
import com.psychiatrygarden.activity.chat.GroupChatSearchActivity;
import com.psychiatrygarden.adapter.GroupChatClassifyAdapter;
import com.psychiatrygarden.adapter.GroupChatNameAdapter;
import com.psychiatrygarden.baseview.BaseFragment;
import com.psychiatrygarden.baseview.ViewHolder;
import com.psychiatrygarden.bean.GroupChatClassifyBean;
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
public class GroupChatFragment extends BaseFragment implements OnItemClickListener, OnItemChildClickListener, OnLoadMoreListener, QuestionDataCallBack<String>, SwipeRefreshLayout.OnRefreshListener {
    private GroupChatClassifyAdapter groupChatClassifyAdapter;
    private GroupChatNameAdapter groupChatNameAdapter;
    private RecyclerView rv_group_chat_classify;
    private RecyclerView rv_group_chat_name;
    private SwipeRefreshLayout swipe;
    private List<GroupChatClassifyBean.DataDTO> groupChatClassifyBeanList = new ArrayList();
    private List<GroupChatListBean.DataDTO> groupChatList = new ArrayList();
    private int page = 1;
    private String category_id = "";
    private String id = "";

    private void initRv() {
        this.rv_group_chat_classify.setLayoutManager(new LinearLayoutManager(this.mContext));
        this.rv_group_chat_name.setLayoutManager(new LinearLayoutManager(this.mContext));
        this.groupChatNameAdapter = new GroupChatNameAdapter(this.groupChatList);
        View viewInflate = LayoutInflater.from(getActivity()).inflate(R.layout.adapter_empty_view, (ViewGroup) this.rv_group_chat_name, false);
        ((TextView) viewInflate.findViewById(R.id.tv_content)).setText("暂无社群");
        this.groupChatNameAdapter.setEmptyView(viewInflate);
        CustomLoadMoreView customLoadMoreView = new CustomLoadMoreView();
        this.groupChatNameAdapter.getLoadMoreModule().setEnableLoadMoreIfNotFullPage(false);
        this.groupChatNameAdapter.getLoadMoreModule().setLoadMoreView(customLoadMoreView);
        GroupChatClassifyAdapter groupChatClassifyAdapter = new GroupChatClassifyAdapter(this.groupChatClassifyBeanList);
        this.groupChatClassifyAdapter = groupChatClassifyAdapter;
        this.rv_group_chat_classify.setAdapter(groupChatClassifyAdapter);
        this.rv_group_chat_name.setAdapter(this.groupChatNameAdapter);
        this.groupChatNameAdapter.setOnItemClickListener(this);
        this.groupChatClassifyAdapter.setOnItemClickListener(this);
        this.groupChatNameAdapter.addChildClickViewIds(R.id.iv_add_remove);
        this.groupChatNameAdapter.getLoadMoreModule().setOnLoadMoreListener(this);
        this.groupChatNameAdapter.setOnItemChildClickListener(this);
    }

    public static GroupChatFragment newInstance() {
        Bundle bundle = new Bundle();
        GroupChatFragment groupChatFragment = new GroupChatFragment();
        groupChatFragment.setArguments(bundle);
        return groupChatFragment;
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public int getLayoutId() {
        return R.layout.fragment_group_chat;
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void initViews(ViewHolder holder, View root) {
        TextView textView = (TextView) holder.get(R.id.tv_search);
        this.swipe = (SwipeRefreshLayout) holder.get(R.id.mSwipeLayput);
        this.rv_group_chat_classify = (RecyclerView) holder.get(R.id.rv_group_chat_classify);
        this.rv_group_chat_name = (RecyclerView) holder.get(R.id.rv_group_chat_name);
        ChatRequest.getIntance(getActivity()).chatCategory(this);
        this.swipe.setOnRefreshListener(this);
        if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
            this.swipe.setProgressBackgroundColorSchemeColor(ContextCompat.getColor(this.mContext, R.color.white));
            this.swipe.setColorSchemeResources(android.R.color.holo_red_light, android.R.color.holo_red_light, android.R.color.holo_red_light, android.R.color.holo_red_light);
        } else {
            this.swipe.setProgressBackgroundColorSchemeColor(ContextCompat.getColor(this.mContext, R.color.input_night_color));
            this.swipe.setColorSchemeResources(R.color.question_color_night, R.color.question_color_night, R.color.question_color_night, R.color.question_color_night);
        }
        this.swipe.post(new Runnable() { // from class: com.psychiatrygarden.fragmenthome.chat.GroupChatFragment.1
            @Override // java.lang.Runnable
            public void run() {
                if (TextUtils.isEmpty(GroupChatFragment.this.category_id)) {
                    return;
                }
                GroupChatFragment.this.swipe.setRefreshing(true);
                GroupChatFragment.this.page = 1;
                ChatRequest.getIntance(GroupChatFragment.this.getActivity()).chatGroupList(GroupChatFragment.this.category_id, "", GroupChatFragment.this.page, GroupChatFragment.this);
            }
        });
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.chat.GroupChatFragment.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v2) {
                GroupChatFragment.this.getActivity().startActivity(new Intent(GroupChatFragment.this.getActivity(), (Class<?>) GroupChatSearchActivity.class));
            }
        });
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
        hideProgressDialog();
    }

    @Override // com.chad.library.adapter.base.listener.OnItemChildClickListener
    public void onItemChildClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
        if (isLogin() && view.getId() == R.id.iv_add_remove) {
            this.id = this.groupChatList.get(position).getId();
            ChatRequest.getIntance(getActivity()).chatJoin(this.groupChatList.get(position).getId(), this.groupChatList.get(position).getCommunity_id(), this);
        }
    }

    @Override // com.chad.library.adapter.base.listener.OnItemClickListener
    public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
        if (adapter instanceof GroupChatNameAdapter) {
            if (isLogin() && this.groupChatList.get(position).getIs_join().equals("1")) {
                Intent intent = new Intent(getActivity(), (Class<?>) ChatActivity.class);
                intent.putExtra(EaseConstant.EXTRA_CHAT_TYPE, 2);
                intent.putExtra("name", this.groupChatList.get(position).getName());
                intent.putExtra("id", this.groupChatList.get(position).getId());
                intent.putExtra("group_img", this.groupChatList.get(position).getLogo());
                intent.putExtra(EaseConstant.EXTRA_CONVERSATION_ID, this.groupChatList.get(position).getCommunity_id());
                startActivity(intent);
                return;
            }
            return;
        }
        if (adapter instanceof GroupChatClassifyAdapter) {
            this.page = 1;
            this.category_id = this.groupChatClassifyBeanList.get(position).getId();
            ChatRequest.getIntance(getActivity()).chatGroupList(this.category_id, "", this.page, this);
            for (int i2 = 0; i2 < this.groupChatClassifyBeanList.size(); i2++) {
                this.groupChatClassifyBeanList.get(i2).setIs_select("0");
            }
            this.groupChatClassifyBeanList.get(position).setIs_select("1");
            this.groupChatClassifyAdapter.notifyDataSetChanged();
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
        if (requstUrl.equals(NetworkRequestsURL.chatCategoryApi)) {
            showProgressDialog();
        }
    }

    @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
    public void onSuccess(String s2, String requstUrl) {
        int i2;
        try {
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        if (requstUrl.equals(NetworkRequestsURL.chatCategoryApi)) {
            hideProgressDialog();
            GroupChatClassifyBean groupChatClassifyBean = (GroupChatClassifyBean) new Gson().fromJson(s2, GroupChatClassifyBean.class);
            if (!groupChatClassifyBean.getCode().equals("200")) {
                ToastUtil.shortToast(getActivity(), groupChatClassifyBean.getMessage());
                return;
            }
            if (groupChatClassifyBean.getData().size() > 0) {
                this.page = 1;
                this.category_id = groupChatClassifyBean.getData().get(0).getId();
                List<GroupChatClassifyBean.DataDTO> data = groupChatClassifyBean.getData();
                this.groupChatClassifyBeanList = data;
                data.get(0).setIs_select("1");
                initRv();
                ChatRequest.getIntance(getActivity()).chatGroupList(this.category_id, "", this.page, this);
                return;
            }
            return;
        }
        if (!requstUrl.equals(NetworkRequestsURL.chatListApi)) {
            if (requstUrl.equals(NetworkRequestsURL.chatJoinApi)) {
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if (!jSONObject.optString("code").equals("200")) {
                        ToastUtil.shortToast(getActivity(), jSONObject.optString("message"));
                        return;
                    }
                    for (i2 = 0; i2 < this.groupChatList.size(); i2++) {
                        if (this.id.equals(this.groupChatList.get(i2).getId())) {
                            this.groupChatList.get(i2).setIs_join("1");
                        }
                    }
                    this.groupChatNameAdapter.notifyDataSetChanged();
                    ToastUtil.shortToast(getActivity(), "加入成功");
                    return;
                } catch (JSONException e3) {
                    e3.printStackTrace();
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
            List<GroupChatListBean.DataDTO> data2 = groupChatListBean.getData();
            this.groupChatList = data2;
            this.groupChatNameAdapter.setNewInstance(data2);
            if (this.groupChatList.size() >= 20) {
                this.groupChatNameAdapter.getLoadMoreModule().setEnableLoadMore(true);
                return;
            } else {
                this.groupChatNameAdapter.getLoadMoreModule().loadMoreEnd();
                this.groupChatNameAdapter.getLoadMoreModule().setEnableLoadMore(false);
                return;
            }
        } catch (Exception e4) {
            e4.printStackTrace();
            return;
        }
        e2.printStackTrace();
    }
}
