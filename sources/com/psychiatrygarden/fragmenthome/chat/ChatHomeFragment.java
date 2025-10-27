package com.psychiatrygarden.fragmenthome.chat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.google.gson.Gson;
import com.lxj.xpopup.XPopup;
import com.psychiatrygarden.activity.chat.GroupChatHomeActivity;
import com.psychiatrygarden.baseview.BaseFragment;
import com.psychiatrygarden.baseview.ViewHolder;
import com.psychiatrygarden.bean.GroupChatListBean;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.http.ChatRequest;
import com.psychiatrygarden.interfaceclass.QuestionDataCallBack;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.LogUtils;
import com.psychiatrygarden.widget.ChatGroupRulePopwindow;
import com.yikaobang.yixue.R;

/* loaded from: classes5.dex */
public class ChatHomeFragment extends BaseFragment implements View.OnClickListener {
    private boolean isCanShowing = false;

    private void getGroupChatList() {
        ChatRequest.getIntance(getActivity()).chatGroupList("-1", "", 1, new QuestionDataCallBack<String>() { // from class: com.psychiatrygarden.fragmenthome.chat.ChatHomeFragment.1
            @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg, String requstUrl) {
            }

            @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
            public void onStart(String requstUrl) {
            }

            @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
            public void onSuccess(String s2, String requstUrl) {
                try {
                    GroupChatListBean groupChatListBean = (GroupChatListBean) new Gson().fromJson(s2, GroupChatListBean.class);
                    if (groupChatListBean.getCode().equals("200")) {
                        SharePreferencesUtils.writeStrConfig(CommonParameter.MY_GROUP_CHAT_LIST, s2, ChatHomeFragment.this.getActivity());
                        if (groupChatListBean.getData().isEmpty()) {
                            ChatGroupRulePopwindow chatGroupRulePopwindow = new ChatGroupRulePopwindow(ChatHomeFragment.this.getActivity());
                            XPopup.Builder builder = new XPopup.Builder(ChatHomeFragment.this.getActivity());
                            Boolean bool = Boolean.TRUE;
                            builder.dismissOnTouchOutside(bool).dismissOnBackPressed(bool).asCustom(chatGroupRulePopwindow).show();
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                    LogUtils.e("mengdepeng", "Exception:" + e2);
                }
            }
        });
    }

    public static ChatHomeFragment newInstance() {
        Bundle bundle = new Bundle();
        ChatHomeFragment chatHomeFragment = new ChatHomeFragment();
        chatHomeFragment.setArguments(bundle);
        return chatHomeFragment;
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public int getLayoutId() {
        return R.layout.fragment_chat_home;
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void initViews(ViewHolder holder, View root) {
        ((ImageView) root.findViewById(R.id.iv_chat_group_add)).setOnClickListener(this);
        replaceFragment(MyEaseConversationListFragment.newInstance());
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v2) {
        startActivity(new Intent(getActivity(), (Class<?>) GroupChatHomeActivity.class));
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportFragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void onEventMainThread(String str) {
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportFragment, androidx.fragment.app.Fragment
    public void onHiddenChanged(boolean hidden) {
        Log.e("mengdepeng", "onHiddenChanged: " + hidden);
        if (this.isCanShowing || hidden) {
            return;
        }
        this.isCanShowing = true;
        getGroupChatList();
    }

    public void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransactionBeginTransaction = getParentFragmentManager().beginTransaction();
        fragmentTransactionBeginTransaction.replace(R.id.frame_chat, fragment);
        fragmentTransactionBeginTransaction.addToBackStack(null);
        fragmentTransactionBeginTransaction.commit();
    }
}
