package com.psychiatrygarden.fragmenthome.chat;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import androidx.annotation.MainThread;
import com.google.gson.Gson;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMConversationListener;
import com.hyphenate.EMValueCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMUserInfo;
import com.hyphenate.easeui.constants.EaseConstant;
import com.hyphenate.easeui.modules.conversation.model.EaseConversationInfo;
import com.hyphenate.easeui.modules.conversation.model.EaseConversationSetStyle;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.hyphenate.easeui.widget.EaseImageView;
import com.hyphenate.exceptions.HyphenateException;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.chat.ChatActivity;
import com.psychiatrygarden.bean.GroupChatListBean;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.http.ChatRequest;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.interfaceclass.QuestionDataCallBack;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.EventBusConstant;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* loaded from: classes5.dex */
public class MyEaseConversationListFragment extends YkbEaseConversationListFragment implements QuestionDataCallBack<String> {
    private void getChatUserInfo(String[] userId) {
        EMClient.getInstance().userInfoManager().fetchUserInfoByUserId(userId, new EMValueCallBack<Map<String, EMUserInfo>>() { // from class: com.psychiatrygarden.fragmenthome.chat.MyEaseConversationListFragment.3
            @Override // com.hyphenate.EMValueCallBack
            public void onError(int error, String errorMsg) {
            }

            @Override // com.hyphenate.EMValueCallBack
            public void onSuccess(Map<String, EMUserInfo> value) {
                ProjectApp.hxUser.putAll(value);
            }
        });
    }

    public static MyEaseConversationListFragment newInstance() {
        Bundle bundle = new Bundle();
        MyEaseConversationListFragment myEaseConversationListFragment = new MyEaseConversationListFragment();
        myEaseConversationListFragment.setArguments(bundle);
        return myEaseConversationListFragment;
    }

    @Override // com.psychiatrygarden.fragmenthome.chat.YkbEaseConversationListFragment
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        this.conversationListLayout.setAvatarSize(EaseCommonUtils.dip2px(this.mContext, 50.0f));
        this.conversationListLayout.setAvatarShapeType(EaseImageView.ShapeType.RECTANGLE);
        this.conversationListLayout.setAvatarRadius((int) EaseCommonUtils.dip2px(this.mContext, 3.0f));
        this.conversationListLayout.hideUnreadDot(false);
        this.conversationListLayout.showUnreadDotPosition(EaseConversationSetStyle.UnreadDotPosition.LEFT);
        this.conversationListLayout.getListAdapter().setEmptyLayoutId(R.layout.layout_chat_empty_view);
        EMClient.getInstance().chatManager().addConversationListener(new EMConversationListener() { // from class: com.psychiatrygarden.fragmenthome.chat.MyEaseConversationListFragment.1
            @Override // com.hyphenate.EMConversationListener
            public void onConversationRead(String from, String to) {
                MyEaseConversationListFragment.this.conversationListLayout.loadDefaultData();
            }

            @Override // com.hyphenate.EMConversationListener
            public void onCoversationUpdate() {
            }
        });
    }

    @Override // com.psychiatrygarden.fragmenthome.chat.YkbEaseConversationListFragment, com.hyphenate.easeui.modules.conversation.interfaces.OnConversationLoadListener
    public void loadDataFinish(List<EaseConversationInfo> data) {
        super.loadDataFinish(data);
        try {
            ArrayList arrayList = new ArrayList();
            for (int i2 = 0; i2 < data.size(); i2++) {
                if (!data.get(i2).isGroup()) {
                    arrayList.add(((EMConversation) data.get(i2).getInfo()).conversationId());
                }
            }
            if (arrayList.size() > 0) {
                getChatUserInfo((String[]) arrayList.toArray(new String[arrayList.size()]));
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @MainThread
    public void onEventMainThread(String str) {
        if (str.equals("PushIMReadEvent")) {
            this.conversationListLayout.loadDefaultData();
        } else if (str.equals(EventBusConstant.EVENT_HX_LOGIN_SUCCESS)) {
            onRefresh();
        }
    }

    @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
    public void onFailure(Throwable t2, int errorNo, String strMsg, String requstUrl) {
    }

    @Override // com.psychiatrygarden.fragmenthome.chat.YkbEaseConversationListFragment, com.hyphenate.easeui.interfaces.OnItemClickListener
    public void onItemClick(View view, int position) {
        super.onItemClick(view, position);
        EventBus.getDefault().post(CommonParameter.SYStem_Red_Dot);
        EMConversation eMConversation = (EMConversation) this.conversationListLayout.getItem(position).getInfo();
        try {
            EMClient.getInstance().chatManager().ackConversationRead(eMConversation.conversationId());
            if (EMClient.getInstance().chatManager().getConversation(eMConversation.conversationId()).getUnreadMsgCount() > 0) {
                onRefresh();
            }
        } catch (HyphenateException e2) {
            e2.printStackTrace();
        }
        Intent intent = new Intent(getActivity(), (Class<?>) ChatActivity.class);
        if (eMConversation.isGroup()) {
            intent.putExtra(EaseConstant.EXTRA_CHAT_TYPE, 2);
            GroupChatListBean groupChatListBean = (GroupChatListBean) new Gson().fromJson(SharePreferencesUtils.readStrConfig(CommonParameter.MY_GROUP_CHAT_LIST, getActivity(), ""), GroupChatListBean.class);
            if (groupChatListBean.getCode().equals("200")) {
                int i2 = 0;
                while (true) {
                    if (i2 >= groupChatListBean.getData().size()) {
                        break;
                    }
                    if (groupChatListBean.getData().get(i2).getCommunity_id().equals(eMConversation.conversationId())) {
                        intent.putExtra("group_img", groupChatListBean.getData().get(i2).getLogo());
                        intent.putExtra("name", groupChatListBean.getData().get(i2).getName());
                        break;
                    }
                    i2++;
                }
            }
        } else {
            intent.putExtra(EaseConstant.EXTRA_CHAT_TYPE, 1);
        }
        intent.putExtra(EaseConstant.EXTRA_CONVERSATION_ID, eMConversation.conversationId());
        startActivity(intent);
    }

    @Override // com.psychiatrygarden.fragmenthome.chat.YkbEaseConversationListFragment, com.hyphenate.easeui.modules.menu.OnPopupMenuItemClickListener
    public boolean onMenuItemClick(MenuItem item, int position) {
        EaseConversationInfo item2 = this.conversationListLayout.getItem(position);
        Object info = item2.getInfo();
        if (info instanceof EMConversation) {
            switch (item.getItemId()) {
                case R.id.action_con_cancel_top /* 2131361930 */:
                    this.conversationListLayout.cancelConversationTop(position, item2);
                    break;
                case R.id.action_con_delete /* 2131361931 */:
                    EMConversation eMConversation = (EMConversation) info;
                    EMClient.getInstance().chatManager().deleteConversationFromServer(eMConversation.conversationId(), eMConversation.getType(), true, new EMCallBack() { // from class: com.psychiatrygarden.fragmenthome.chat.MyEaseConversationListFragment.2
                        @Override // com.hyphenate.EMCallBack
                        public void onError(int code, String error) {
                        }

                        @Override // com.hyphenate.EMCallBack
                        public /* synthetic */ void onProgress(int i2, String str) {
                            d1.a.a(this, i2, str);
                        }

                        @Override // com.hyphenate.EMCallBack
                        public void onSuccess() {
                        }
                    });
                    this.conversationListLayout.deleteConversation(position, item2);
                    break;
                case R.id.action_con_make_read /* 2131361932 */:
                    this.conversationListLayout.makeConversionRead(position, item2);
                    break;
                case R.id.action_con_make_top /* 2131361933 */:
                    this.conversationListLayout.makeConversationTop(position, item2);
                    break;
            }
            return true;
        }
        return super.onMenuItemClick(item, position);
    }

    @Override // com.psychiatrygarden.fragmenthome.chat.YkbEaseConversationListFragment, androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
    public void onRefresh() {
        ChatRequest.getIntance(getActivity()).chatGroupList("-1", "", 1, this);
        super.onRefresh();
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        this.conversationListLayout.loadDefaultData();
    }

    @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
    public void onStart(String requstUrl) {
    }

    @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
    public void onSuccess(String s2, String requstUrl) {
        if (requstUrl.equals(NetworkRequestsURL.chatListApi)) {
            try {
                if (((GroupChatListBean) new Gson().fromJson(s2, GroupChatListBean.class)).getCode().equals("200")) {
                    SharePreferencesUtils.writeStrConfig(CommonParameter.MY_GROUP_CHAT_LIST, s2, getActivity());
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }
}
