package com.psychiatrygarden.activity.chat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hyphenate.EMValueCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMPushManager;
import com.hyphenate.chat.EMSilentModeParam;
import com.hyphenate.chat.EMSilentModeResult;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.UserCommentInfoActivity;
import com.psychiatrygarden.adapter.GroupChatMemberAdapter;
import com.psychiatrygarden.bean.GroupChatDetailBean;
import com.psychiatrygarden.http.ChatRequest;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.interfaceclass.QuestionDataCallBack;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.EventBusConstant;
import com.psychiatrygarden.utils.LogUtils;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.ToastUtil;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes5.dex */
public class GroupChatSetActivity extends BaseActivity implements QuestionDataCallBack<String>, View.OnClickListener, OnItemClickListener {
    private String community_id;
    private GroupChatDetailBean groupChatDetailBean;
    private GroupChatMemberAdapter groupChatMemberAdapter;
    private String id;
    private LinearLayout ll_notice_content;
    private ActivityResultLauncher requestDataLauncher;
    private RecyclerView rv_member;
    private Switch switch_group_info_hide;
    private Switch switch_msg_disturb;
    private TextView tv_exit_group_chat;
    private TextView tv_group_chat_file;
    private TextView tv_group_chat_history;
    private TextView tv_group_chat_name;
    private TextView tv_group_chat_nickname;
    private TextView tv_group_chat_notice;
    private TextView tv_group_chat_remark;
    private TextView tv_look_member;
    private TextView tv_notice_content;
    private List<GroupChatDetailBean.DataDTO.DefaultMemberDTO> groupChatMemberList = new ArrayList();
    private Handler mHandler = new Handler() { // from class: com.psychiatrygarden.activity.chat.GroupChatSetActivity.3
        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what != 0) {
                return;
            }
            GroupChatSetActivity.this.switch_msg_disturb.setChecked(true);
        }
    };

    private void initRv() {
        this.rv_member.setLayoutManager(new GridLayoutManager(this.mContext, 5));
        GroupChatMemberAdapter groupChatMemberAdapter = new GroupChatMemberAdapter(new ArrayList());
        this.groupChatMemberAdapter = groupChatMemberAdapter;
        this.rv_member.setAdapter(groupChatMemberAdapter);
        this.groupChatMemberAdapter.setOnItemClickListener(new OnItemClickListener() { // from class: com.psychiatrygarden.activity.chat.a0
            @Override // com.chad.library.adapter.base.listener.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
                this.f11149c.onItemClick(baseQuickAdapter, view, i2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$0(ActivityResult activityResult) {
        if (activityResult.getData().getExtras().getString("type").equals(CommonParameter.tv_group_chat_name)) {
            this.tv_group_chat_name.setText(activityResult.getData().getExtras().getString("edit_content"));
            this.groupChatDetailBean.getData().setName(activityResult.getData().getExtras().getString("edit_content"));
        } else {
            if (TextUtils.isEmpty(activityResult.getData().getExtras().getString("edit_content"))) {
                this.tv_notice_content.setVisibility(8);
                this.tv_group_chat_notice.setText("未设置");
                return;
            }
            this.tv_group_chat_notice.setText("");
            this.tv_notice_content.setVisibility(0);
            this.tv_notice_content.setText(activityResult.getData().getExtras().getString("edit_content"));
            this.groupChatDetailBean.getData().setAnnouncement(activityResult.getData().getExtras().getString("edit_content"));
            this.groupChatDetailBean.getData().setAnnouncement_image((List) new Gson().fromJson(activityResult.getData().getExtras().getString("announcement_image"), new TypeToken<List<String>>() { // from class: com.psychiatrygarden.activity.chat.GroupChatSetActivity.1
            }.getType()));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onEventMainThread$2() {
        ChatRequest.getIntance(this).chatDetail(this.community_id, this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$1(CompoundButton compoundButton, boolean z2) {
        EMClient.getInstance().pushManager().setSilentModeForConversation(this.community_id, EMConversation.EMConversationType.GroupChat, new EMSilentModeParam(EMSilentModeParam.EMSilentModeParamType.REMIND_TYPE).setRemindType(z2 ? EMPushManager.EMPushRemindType.NONE : EMPushManager.EMPushRemindType.ALL), new EMValueCallBack<EMSilentModeResult>() { // from class: com.psychiatrygarden.activity.chat.GroupChatSetActivity.4
            @Override // com.hyphenate.EMValueCallBack
            public void onError(int error, String errorMsg) {
                LogUtils.e("mengdepeng", "onError:" + errorMsg);
            }

            @Override // com.hyphenate.EMValueCallBack
            public void onSuccess(EMSilentModeResult value) {
                LogUtils.e("mengdepeng", "onSuccess:" + value.getRemindType().toString());
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.community_id = getIntent().getExtras().getString("community_id", "");
        this.id = getIntent().getExtras().getString("id", "");
        this.mTxtActionbarTitle.setText("聊天信息");
        this.rv_member = (RecyclerView) findViewById(R.id.rv_member);
        this.tv_look_member = (TextView) findViewById(R.id.tv_look_member);
        this.tv_group_chat_name = (TextView) findViewById(R.id.tv_group_chat_name);
        this.tv_group_chat_notice = (TextView) findViewById(R.id.tv_group_chat_notice);
        this.ll_notice_content = (LinearLayout) findViewById(R.id.ll_notice_content);
        this.tv_notice_content = (TextView) findViewById(R.id.tv_notice_content);
        this.tv_group_chat_file = (TextView) findViewById(R.id.tv_group_chat_file);
        this.tv_group_chat_history = (TextView) findViewById(R.id.tv_group_chat_history);
        this.switch_group_info_hide = (Switch) findViewById(R.id.switch_group_info_hide);
        this.tv_group_chat_remark = (TextView) findViewById(R.id.tv_group_chat_remark);
        this.tv_group_chat_nickname = (TextView) findViewById(R.id.tv_group_chat_nickname);
        this.tv_exit_group_chat = (TextView) findViewById(R.id.tv_exit_group_chat);
        this.switch_msg_disturb = (Switch) findViewById(R.id.switch_msg_disturb);
        initRv();
        ChatRequest.getIntance(this).chatDetail(this.community_id, this);
        this.switch_msg_disturb.setChecked(false);
        EMClient.getInstance().pushManager().getSilentModeForConversation(this.community_id, EMConversation.EMConversationType.GroupChat, new EMValueCallBack<EMSilentModeResult>() { // from class: com.psychiatrygarden.activity.chat.GroupChatSetActivity.2
            @Override // com.hyphenate.EMValueCallBack
            public void onError(int error, String errorMsg) {
            }

            @Override // com.hyphenate.EMValueCallBack
            public void onSuccess(EMSilentModeResult result) {
                if (result.isConversationRemindTypeEnabled() && result.getRemindType() == EMPushManager.EMPushRemindType.NONE) {
                    GroupChatSetActivity.this.mHandler.sendEmptyMessage(0);
                }
            }
        });
        if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
            findViewById(R.id.nestedscroll_main).setBackgroundColor(getResources().getColor(R.color.gray_bg));
        } else {
            findViewById(R.id.nestedscroll_main).setBackgroundColor(getResources().getColor(R.color.app_theme_night));
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v2) {
        switch (v2.getId()) {
            case R.id.ll_notice_content /* 2131364840 */:
                GroupChatDetailBean groupChatDetailBean = this.groupChatDetailBean;
                if (groupChatDetailBean != null) {
                    if (!TextUtils.isEmpty(groupChatDetailBean.getData().getAnnouncement())) {
                        Intent intent = new Intent(this, (Class<?>) ChatEditInfoActivity.class);
                        intent.putExtra("id", this.groupChatDetailBean.getData().getId());
                        intent.putExtra("community_id", this.community_id);
                        intent.putExtra("content", this.groupChatDetailBean.getData().getAnnouncement());
                        intent.putExtra("user_identity", this.groupChatDetailBean.getData().getUser_identity());
                        intent.putExtra("announcement_image", new Gson().toJson(this.groupChatDetailBean.getData().getAnnouncement_image()));
                        intent.putExtra("type", CommonParameter.tv_group_chat_notice);
                        this.requestDataLauncher.launch(intent);
                        break;
                    } else {
                        AlertToast("暂无公告");
                        break;
                    }
                }
                break;
            case R.id.tv_exit_group_chat /* 2131368003 */:
                new XPopup.Builder(this).asConfirm("", "是否确定退出群聊？", new OnConfirmListener() { // from class: com.psychiatrygarden.activity.chat.GroupChatSetActivity.5
                    @Override // com.lxj.xpopup.interfaces.OnConfirmListener
                    public void onConfirm() {
                        GroupChatSetActivity.this.showProgressDialog();
                        ChatRequest.getIntance(GroupChatSetActivity.this).chatSignOut(GroupChatSetActivity.this.id, GroupChatSetActivity.this.community_id, GroupChatSetActivity.this);
                    }
                }).show();
                break;
            case R.id.tv_group_chat_name /* 2131368073 */:
                GroupChatDetailBean groupChatDetailBean2 = this.groupChatDetailBean;
                if (groupChatDetailBean2 != null) {
                    if (!groupChatDetailBean2.getData().getUser_identity().equals("0")) {
                        Intent intent2 = new Intent(this, (Class<?>) ChatEditInfoActivity.class);
                        intent2.putExtra("id", this.groupChatDetailBean.getData().getId());
                        intent2.putExtra("community_id", this.community_id);
                        intent2.putExtra("content", this.groupChatDetailBean.getData().getName());
                        intent2.putExtra("type", CommonParameter.tv_group_chat_name);
                        this.requestDataLauncher.launch(intent2);
                        break;
                    } else {
                        AlertToast("只有群主和管理员可以编辑");
                        break;
                    }
                }
                break;
            case R.id.tv_look_member /* 2131368181 */:
                if (this.groupChatDetailBean != null) {
                    Intent intent3 = new Intent(this, (Class<?>) GroupChatMemberActivity.class);
                    intent3.putExtra("member_list", (Serializable) this.groupChatDetailBean.getData().getDefault_member());
                    intent3.putExtra("user_identity", this.groupChatDetailBean.getData().getUser_identity());
                    intent3.putExtra("group_img", this.groupChatDetailBean.getData().getLogo());
                    intent3.putExtra("community_id", this.community_id);
                    intent3.putExtra("id", this.groupChatDetailBean.getData().getId());
                    startActivity(intent3);
                    break;
                }
                break;
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        this.requestDataLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback() { // from class: com.psychiatrygarden.activity.chat.c0
            @Override // androidx.activity.result.ActivityResultCallback
            public final void onActivityResult(Object obj) {
                this.f11153a.lambda$onCreate$0((ActivityResult) obj);
            }
        });
        super.onCreate(savedInstanceState);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        this.mHandler.removeCallbacksAndMessages(null);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
        if (str.equals(EventBusConstant.EVENT_UPDATA_GROUP_INFO)) {
            new Handler().postDelayed(new Runnable() { // from class: com.psychiatrygarden.activity.chat.b0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f11151c.lambda$onEventMainThread$2();
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
            intent.putExtra("member_list", (Serializable) this.groupChatDetailBean.getData().getDefault_member());
            intent.putExtra("group_img", this.groupChatDetailBean.getData().getLogo());
            intent.putExtra("community_id", this.community_id);
            startActivity(intent);
            return;
        }
        Intent intent2 = new Intent(this, (Class<?>) UserCommentInfoActivity.class);
        intent2.putExtra("id", this.id);
        intent2.putExtra("community_id", this.community_id);
        intent2.putExtra("user_id", this.groupChatMemberList.get(position).getUser_id());
        intent2.putExtra("is_owner", this.groupChatMemberList.get(position).getIs_owner());
        intent2.putExtra("is_banned", this.groupChatMemberList.get(position).getIs_banned());
        intent2.putExtra("user_identity", this.groupChatDetailBean.getData().getUser_identity());
        intent2.putExtra("position", position);
        intent2.addFlags(67108864);
        startActivity(intent2);
    }

    @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
    public void onStart(String requstUrl) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_group_chat_set);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.tv_look_member.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chat.d0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11155c.onClick(view);
            }
        });
        this.tv_exit_group_chat.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chat.d0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11155c.onClick(view);
            }
        });
        this.tv_group_chat_name.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chat.d0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11155c.onClick(view);
            }
        });
        this.ll_notice_content.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chat.d0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11155c.onClick(view);
            }
        });
        this.switch_msg_disturb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.psychiatrygarden.activity.chat.e0
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z2) {
                this.f11156c.lambda$setListenerForWidget$1(compoundButton, z2);
            }
        });
    }

    @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
    public void onSuccess(String s2, String requstUrl) {
        hideProgressDialog();
        if (!requstUrl.equals(NetworkRequestsURL.chatDetailApi)) {
            if (requstUrl.equals(NetworkRequestsURL.chatSignOutApi)) {
                EventBus.getDefault().post(EventBusConstant.EVENT_CHAT_GROUP_EXIT);
                finish();
                return;
            }
            return;
        }
        this.tv_look_member.setVisibility(8);
        GroupChatDetailBean groupChatDetailBean = (GroupChatDetailBean) new Gson().fromJson(s2, GroupChatDetailBean.class);
        this.groupChatDetailBean = groupChatDetailBean;
        if (!groupChatDetailBean.getCode().equals("200")) {
            ToastUtil.shortToast(this, this.groupChatDetailBean.getMessage());
            return;
        }
        this.id = this.groupChatDetailBean.getData().getId();
        this.groupChatMemberList.clear();
        int i2 = 0;
        while (true) {
            if (i2 < this.groupChatDetailBean.getData().getDefault_member().size()) {
                if (i2 >= (this.groupChatDetailBean.getData().getUser_identity().equals("0") ? 20 : 19)) {
                    this.tv_look_member.setVisibility(0);
                    break;
                } else {
                    this.groupChatMemberList.add(this.groupChatDetailBean.getData().getDefault_member().get(i2));
                    i2++;
                }
            } else {
                break;
            }
        }
        if (!this.groupChatDetailBean.getData().getUser_identity().equals("0")) {
            GroupChatDetailBean.DataDTO.DefaultMemberDTO defaultMemberDTO = new GroupChatDetailBean.DataDTO.DefaultMemberDTO();
            defaultMemberDTO.setIs_add(true);
            this.groupChatMemberList.add(defaultMemberDTO);
        }
        this.groupChatMemberAdapter.setNewInstance(this.groupChatMemberList);
        this.tv_group_chat_name.setText(this.groupChatDetailBean.getData().getName());
        if (TextUtils.isEmpty(this.groupChatDetailBean.getData().getAnnouncement())) {
            this.tv_group_chat_notice.setText("未设置");
            this.tv_notice_content.setText("");
        } else {
            this.tv_notice_content.setVisibility(0);
            this.tv_group_chat_notice.setText("");
            this.tv_notice_content.setText(this.groupChatDetailBean.getData().getAnnouncement());
        }
    }
}
