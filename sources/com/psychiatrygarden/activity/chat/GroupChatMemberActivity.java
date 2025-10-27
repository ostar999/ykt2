package com.psychiatrygarden.activity.chat;

import android.content.Intent;
import android.os.Handler;
import android.view.View;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.UserCommentInfoActivity;
import com.psychiatrygarden.adapter.GroupChatMemberAdapter;
import com.psychiatrygarden.bean.GroupChatDetailBean;
import com.psychiatrygarden.http.ChatRequest;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.interfaceclass.QuestionDataCallBack;
import com.psychiatrygarden.utils.EventBusConstant;
import com.yikaobang.yixue.R;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class GroupChatMemberActivity extends BaseActivity implements QuestionDataCallBack<String>, View.OnClickListener, OnItemClickListener {
    private String community_id;
    private GroupChatMemberAdapter groupChatMemberAdapter;
    List<GroupChatDetailBean.DataDTO.DefaultMemberDTO> groupChatMemberList = new ArrayList();
    private String id;
    private RecyclerView rv_member;

    private void initRv() {
        this.rv_member.setLayoutManager(new GridLayoutManager(this.mContext, 5));
        GroupChatMemberAdapter groupChatMemberAdapter = new GroupChatMemberAdapter(this.groupChatMemberList);
        this.groupChatMemberAdapter = groupChatMemberAdapter;
        this.rv_member.setAdapter(groupChatMemberAdapter);
        this.groupChatMemberAdapter.setOnItemClickListener(new OnItemClickListener() { // from class: com.psychiatrygarden.activity.chat.q
            @Override // com.chad.library.adapter.base.listener.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
                this.f11171c.onItemClick(baseQuickAdapter, view, i2);
            }
        });
        if (!getIntent().getExtras().getString("user_identity", "").equals("0")) {
            GroupChatDetailBean.DataDTO.DefaultMemberDTO defaultMemberDTO = new GroupChatDetailBean.DataDTO.DefaultMemberDTO();
            defaultMemberDTO.setIs_add(true);
            this.groupChatMemberList.add(defaultMemberDTO);
        }
        this.groupChatMemberAdapter.setNewInstance(this.groupChatMemberList);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.id = getIntent().getExtras().getString("id", "");
        this.community_id = getIntent().getExtras().getString("community_id", "");
        this.groupChatMemberList = (List) getIntent().getExtras().getSerializable("member_list");
        this.mTxtActionbarTitle.setText("聊天成员(" + this.groupChatMemberList.size() + ")");
        this.rv_member = (RecyclerView) findViewById(R.id.rv_member);
        initRv();
        ChatRequest.getIntance(this).chatMember(this.community_id, this);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v2) {
        v2.getId();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
        if (str.equals(EventBusConstant.EVENT_UPDATA_GROUP_INFO)) {
            new Handler().postDelayed(new Runnable() { // from class: com.psychiatrygarden.activity.chat.GroupChatMemberActivity.1
                @Override // java.lang.Runnable
                public void run() {
                    ChatRequest.getIntance(GroupChatMemberActivity.this).chatMember(GroupChatMemberActivity.this.community_id, GroupChatMemberActivity.this);
                }
            }, 1000L);
        }
    }

    @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
    public void onFailure(Throwable t2, int errorNo, String strMsg, String requstUrl) {
        hideProgressDialog();
    }

    @Override // com.chad.library.adapter.base.listener.OnItemClickListener
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        if (this.groupChatMemberList.get(position).isIs_add()) {
            Intent intent = new Intent(this, (Class<?>) GroupChatRemoveMemberActivity.class);
            intent.putExtra("id", this.id);
            intent.putExtra("member_list", (Serializable) this.groupChatMemberList);
            intent.putExtra("group_img", getIntent().getExtras().getString("group_img"));
            intent.putExtra("community_id", getIntent().getExtras().getString("community_id"));
            intent.addFlags(67108864);
            startActivity(intent);
            return;
        }
        Intent intent2 = new Intent(this, (Class<?>) UserCommentInfoActivity.class);
        intent2.putExtra("user_id", this.groupChatMemberList.get(position).getUser_id());
        intent2.putExtra("id", this.id);
        intent2.putExtra("is_owner", this.groupChatMemberList.get(position).getIs_owner());
        intent2.putExtra("is_banned", this.groupChatMemberList.get(position).getIs_banned());
        intent2.putExtra("community_id", getIntent().getExtras().getString("community_id"));
        intent2.putExtra("user_identity", getIntent().getExtras().getString("user_identity"));
        intent2.putExtra("position", position);
        intent2.addFlags(67108864);
        startActivity(intent2);
    }

    @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
    public void onStart(String requstUrl) {
        showProgressDialog();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_group_chat_member);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }

    @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
    public void onSuccess(String s2, String requstUrl) {
        hideProgressDialog();
        if (requstUrl.equals(NetworkRequestsURL.chatMemberApi)) {
            try {
                JSONObject jSONObject = new JSONObject(s2);
                if (jSONObject.optString("code").equals("200")) {
                    this.groupChatMemberList.clear();
                    this.groupChatMemberList = (List) new Gson().fromJson(jSONObject.optString("data"), new TypeToken<List<GroupChatDetailBean.DataDTO.DefaultMemberDTO>>() { // from class: com.psychiatrygarden.activity.chat.GroupChatMemberActivity.2
                    }.getType());
                    this.mTxtActionbarTitle.setText("聊天成员(" + this.groupChatMemberList.size() + ")");
                    if (!getIntent().getExtras().getString("user_identity", "").equals("0")) {
                        GroupChatDetailBean.DataDTO.DefaultMemberDTO defaultMemberDTO = new GroupChatDetailBean.DataDTO.DefaultMemberDTO();
                        defaultMemberDTO.setIs_add(true);
                        this.groupChatMemberList.add(defaultMemberDTO);
                    }
                    this.groupChatMemberAdapter.setNewInstance(this.groupChatMemberList);
                }
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
    }
}
