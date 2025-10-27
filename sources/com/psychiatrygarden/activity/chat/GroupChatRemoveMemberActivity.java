package com.psychiatrygarden.activity.chat;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.UserCommentInfoActivity;
import com.psychiatrygarden.adapter.GroupChatRemoveMemberAdapter;
import com.psychiatrygarden.bean.GroupChatDetailBean;
import com.psychiatrygarden.bean.GroupChatListBean;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.http.ChatRequest;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.interfaceclass.QuestionDataCallBack;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.EventBusConstant;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.ToastUtil;
import com.psychiatrygarden.widget.ClearEditText;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;
import java.util.ArrayList;
import java.util.List;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class GroupChatRemoveMemberActivity extends BaseActivity implements QuestionDataCallBack<String>, OnItemClickListener, OnItemChildClickListener {
    private String community_id;
    private List<GroupChatDetailBean.DataDTO.DefaultMemberDTO> groupChatMemberListAll;
    private GroupChatRemoveMemberAdapter groupChatRemoveMemberAdapter;
    private String group_img;
    private String id;
    private ImageView iv_group_chat_img;
    private ClearEditText mClearEditText;
    private RecyclerView rv_member;
    private boolean type;
    private List<GroupChatDetailBean.DataDTO.DefaultMemberDTO> showList = new ArrayList();
    private List<GroupChatDetailBean.DataDTO.DefaultMemberDTO> searchList = new ArrayList();

    private void getGroupMem(List<GroupChatDetailBean.DataDTO.DefaultMemberDTO> groupChatMemberList) {
        this.showList.clear();
        for (int i2 = 0; i2 < groupChatMemberList.size(); i2++) {
            if (this.type) {
                if (!TextUtils.isEmpty(groupChatMemberList.get(i2).getUser_id()) && !UserConfig.getUserId().equals(groupChatMemberList.get(i2).getUser_id())) {
                    this.showList.add(groupChatMemberList.get(i2));
                }
            } else if (!TextUtils.isEmpty(groupChatMemberList.get(i2).getUser_id()) && groupChatMemberList.get(i2).getIs_owner().equals("0") && !UserConfig.getUserId().equals(groupChatMemberList.get(i2).getUser_id())) {
                this.showList.add(groupChatMemberList.get(i2));
            }
        }
        this.groupChatRemoveMemberAdapter.setNewInstance(this.showList);
    }

    private void initRv() {
        this.rv_member.setLayoutManager(new LinearLayoutManager(this));
        GroupChatRemoveMemberAdapter groupChatRemoveMemberAdapter = new GroupChatRemoveMemberAdapter(new ArrayList());
        this.groupChatRemoveMemberAdapter = groupChatRemoveMemberAdapter;
        groupChatRemoveMemberAdapter.setOnItemChildClickListener(new OnItemChildClickListener() { // from class: com.psychiatrygarden.activity.chat.r
            @Override // com.chad.library.adapter.base.listener.OnItemChildClickListener
            public final void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
                this.f11172c.onItemChildClick(baseQuickAdapter, view, i2);
            }
        });
        this.groupChatRemoveMemberAdapter.setOnItemClickListener(new OnItemClickListener() { // from class: com.psychiatrygarden.activity.chat.s
            @Override // com.chad.library.adapter.base.listener.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
                this.f11173c.onItemClick(baseQuickAdapter, view, i2);
            }
        });
        this.groupChatRemoveMemberAdapter.addChildClickViewIds(R.id.iv_user_info);
        this.rv_member.setAdapter(this.groupChatRemoveMemberAdapter);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$0(View view) {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        int i2 = 0;
        String str = "";
        if (TextUtils.isEmpty(this.mClearEditText.getText().toString())) {
            while (i2 < this.showList.size()) {
                if (this.showList.get(i2).isIs_choice()) {
                    arrayList.add(this.showList.get(i2).getUser_id());
                    arrayList2.add(this.showList.get(i2).getUser_uuid());
                    str = str + "@" + this.showList.get(i2).getNickname() + " ";
                }
                i2++;
            }
        } else {
            while (i2 < this.searchList.size()) {
                if (this.searchList.get(i2).isIs_choice()) {
                    arrayList.add(this.searchList.get(i2).getUser_id());
                    arrayList2.add(this.searchList.get(i2).getUser_uuid());
                    str = str + "@" + this.searchList.get(i2).getNickname() + " ";
                }
                i2++;
            }
        }
        if (arrayList.size() <= 0) {
            AlertToast("您还未选择成员");
            return;
        }
        if (this.type) {
            Intent intent = new Intent();
            intent.putExtra("@_member_content", str.substring(1));
            intent.putExtra("@_start", getIntent().getExtras().getInt("@_start"));
            setResult(-1, intent);
            finish();
            return;
        }
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("community_id", getIntent().getExtras().getString("community_id"));
        ajaxParams.put("id", this.id);
        ajaxParams.put("to_user_id", new Gson().toJson(arrayList));
        ajaxParams.put("to_user_uuid", new Gson().toJson(arrayList2));
        ChatRequest.getIntance(this).removeBatchMember(ajaxParams, this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$setListenerForWidget$1(TextView textView, int i2, KeyEvent keyEvent) {
        if (i2 != 3 && (keyEvent == null || keyEvent.getKeyCode() != 66)) {
            return false;
        }
        this.searchList.clear();
        String string = this.mClearEditText.getText().toString();
        for (int i3 = 0; i3 < this.showList.size(); i3++) {
            if (this.showList.get(i3).getNickname().contains(string)) {
                this.searchList.add(this.showList.get(i3));
            }
        }
        this.groupChatRemoveMemberAdapter.setNewInstance(this.searchList);
        return true;
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        String logo = "";
        this.community_id = getIntent().getExtras().getString("community_id", "");
        this.group_img = getIntent().getExtras().getString("group_img", "");
        int i2 = 0;
        this.type = getIntent().getExtras().getBoolean("@_type", false);
        this.id = getIntent().getExtras().getString("id", "");
        List<GroupChatDetailBean.DataDTO.DefaultMemberDTO> list = (List) getIntent().getExtras().getSerializable("member_list");
        this.groupChatMemberListAll = list;
        if (list == null) {
            this.groupChatMemberListAll = new ArrayList();
        }
        if (this.type) {
            this.mTxtActionbarTitle.setText("选择成员");
            this.mBtnActionbarRight.setText("完成");
        } else {
            this.mTxtActionbarTitle.setText("移除成员");
            this.mBtnActionbarRight.setText("下一步");
        }
        this.rv_member = (RecyclerView) findViewById(R.id.rv_member);
        this.iv_group_chat_img = (ImageView) findViewById(R.id.iv_group_chat_img);
        this.mClearEditText = (ClearEditText) findViewById(R.id.filter_edit);
        boolean zIsEmpty = TextUtils.isEmpty(this.group_img);
        int i3 = R.color.fourth_line_backgroup_color;
        if (zIsEmpty) {
            GroupChatListBean groupChatListBean = (GroupChatListBean) new Gson().fromJson(SharePreferencesUtils.readStrConfig(CommonParameter.MY_GROUP_CHAT_LIST, this, ""), GroupChatListBean.class);
            if (groupChatListBean.getCode().equals("200")) {
                while (true) {
                    if (i2 >= groupChatListBean.getData().size()) {
                        break;
                    }
                    if (groupChatListBean.getData().get(i2).getCommunity_id().equals(this.community_id)) {
                        logo = groupChatListBean.getData().get(i2).getLogo();
                        break;
                    }
                    i2++;
                }
                RequestBuilder<Drawable> requestBuilderLoad = Glide.with((FragmentActivity) this).load((Object) GlideUtils.generateUrl(logo));
                Context context = this.iv_group_chat_img.getContext();
                if (SkinManager.getCurrentSkinType(ProjectApp.instance()) != 0) {
                    i3 = R.color.bg_backgroud_night;
                }
                requestBuilderLoad.placeholder(new ColorDrawable(ContextCompat.getColor(context, i3))).into(this.iv_group_chat_img);
            }
        } else {
            RequestBuilder<Drawable> requestBuilderLoad2 = Glide.with((FragmentActivity) this).load((Object) GlideUtils.generateUrl(this.group_img));
            Context context2 = this.iv_group_chat_img.getContext();
            if (SkinManager.getCurrentSkinType(ProjectApp.instance()) != 0) {
                i3 = R.color.bg_backgroud_night;
            }
            requestBuilderLoad2.placeholder(new ColorDrawable(ContextCompat.getColor(context2, i3))).into(this.iv_group_chat_img);
        }
        initRv();
        ChatRequest.getIntance(this).chatMember(this.community_id, this);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
    public void onFailure(Throwable t2, int errorNo, String strMsg, String requstUrl) {
        hideProgressDialog();
        if (requstUrl.equals(NetworkRequestsURL.chatMemberApi)) {
            getGroupMem(this.groupChatMemberListAll);
        }
    }

    @Override // com.chad.library.adapter.base.listener.OnItemChildClickListener
    public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
        Intent intent = new Intent(this, (Class<?>) UserCommentInfoActivity.class);
        if (TextUtils.isEmpty(this.mClearEditText.getText().toString())) {
            intent.putExtra("user_id", "" + this.showList.get(position).getUser_id());
        } else {
            intent.putExtra("user_id", "" + this.searchList.get(position).getUser_id());
        }
        startActivity(intent);
    }

    @Override // com.chad.library.adapter.base.listener.OnItemClickListener
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        if (TextUtils.isEmpty(this.mClearEditText.getText().toString())) {
            this.showList.get(position).setIs_choice(!this.showList.get(position).isIs_choice());
        } else {
            this.searchList.get(position).setIs_choice(!this.searchList.get(position).isIs_choice());
        }
        this.groupChatRemoveMemberAdapter.notifyDataSetChanged();
    }

    @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
    public void onStart(String requstUrl) {
        showProgressDialog();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_group_chat_remove_member);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.mBtnActionbarRight.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chat.t
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11174c.lambda$setListenerForWidget$0(view);
            }
        });
        this.mClearEditText.addTextChangedListener(new TextWatcher() { // from class: com.psychiatrygarden.activity.chat.GroupChatRemoveMemberActivity.1
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable s2) {
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence s2, int start, int count, int after) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence s2, int start, int before, int count) {
                if (s2.toString().equals("")) {
                    GroupChatRemoveMemberActivity.this.groupChatRemoveMemberAdapter.setNewInstance(GroupChatRemoveMemberActivity.this.showList);
                }
            }
        });
        this.mClearEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() { // from class: com.psychiatrygarden.activity.chat.u
            @Override // android.widget.TextView.OnEditorActionListener
            public final boolean onEditorAction(TextView textView, int i2, KeyEvent keyEvent) {
                return this.f11175c.lambda$setListenerForWidget$1(textView, i2, keyEvent);
            }
        });
    }

    @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
    public void onSuccess(String s2, String requstUrl) {
        hideProgressDialog();
        if (requstUrl.equals(NetworkRequestsURL.chatMemberApi)) {
            try {
                JSONObject jSONObject = new JSONObject(s2);
                if (jSONObject.optString("code").equals("200")) {
                    getGroupMem((List) new Gson().fromJson(jSONObject.optString("data"), new TypeToken<List<GroupChatDetailBean.DataDTO.DefaultMemberDTO>>() { // from class: com.psychiatrygarden.activity.chat.GroupChatRemoveMemberActivity.2
                    }.getType()));
                } else {
                    getGroupMem(this.groupChatMemberListAll);
                }
                return;
            } catch (Exception e2) {
                e2.printStackTrace();
                getGroupMem(this.groupChatMemberListAll);
                return;
            }
        }
        if (requstUrl.equals(NetworkRequestsURL.removeBatchMemberApi)) {
            try {
                JSONObject jSONObject2 = new JSONObject(s2);
                if (jSONObject2.optString("code").equals("200")) {
                    EventBus.getDefault().post(EventBusConstant.EVENT_UPDATA_GROUP_INFO);
                    finish();
                } else {
                    ToastUtil.shortToast(this, jSONObject2.optString("message"));
                }
            } catch (JSONException e3) {
                e3.printStackTrace();
            }
        }
    }
}
