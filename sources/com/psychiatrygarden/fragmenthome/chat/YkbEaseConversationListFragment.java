package com.psychiatrygarden.fragmenthome.chat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.hyphenate.easeui.interfaces.OnItemClickListener;
import com.hyphenate.easeui.modules.conversation.interfaces.OnConversationChangeListener;
import com.hyphenate.easeui.modules.conversation.interfaces.OnConversationLoadListener;
import com.hyphenate.easeui.modules.conversation.model.EaseConversationInfo;
import com.hyphenate.easeui.modules.menu.EasePopupMenuHelper;
import com.hyphenate.easeui.modules.menu.OnPopupMenuItemClickListener;
import com.hyphenate.easeui.modules.menu.OnPopupMenuPreShowListener;
import com.hyphenate.easeui.ui.base.EaseBaseFragment;
import com.hyphenate.util.EMLog;
import com.yikaobang.yixue.R;
import java.util.List;

/* loaded from: classes5.dex */
public class YkbEaseConversationListFragment extends EaseBaseFragment implements OnItemClickListener, OnPopupMenuItemClickListener, OnPopupMenuPreShowListener, SwipeRefreshLayout.OnRefreshListener, OnConversationLoadListener, OnConversationChangeListener {
    private static final String TAG = "YkbEaseConversationListFragment";
    public YkbEaseConversationListLayout conversationListLayout;
    public LinearLayout llRoot;
    public SwipeRefreshLayout srlRefresh;

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$finishRefresh$0() {
        this.srlRefresh.setRefreshing(false);
    }

    public void finishRefresh() {
        if (this.mContext.isFinishing() || this.srlRefresh == null) {
            return;
        }
        runOnUiThread(new Runnable() { // from class: com.psychiatrygarden.fragmenthome.chat.d
            @Override // java.lang.Runnable
            public final void run() {
                this.f15518c.lambda$finishRefresh$0();
            }
        });
    }

    public int getLayoutId() {
        return R.layout.ease_fragment_conversations;
    }

    public void initData() {
        this.conversationListLayout.loadDefaultData();
    }

    public void initListener() {
        this.conversationListLayout.setOnItemClickListener(this);
        this.conversationListLayout.setOnPopupMenuItemClickListener(this);
        this.conversationListLayout.setOnPopupMenuPreShowListener(this);
        this.conversationListLayout.setOnConversationLoadListener(this);
        this.conversationListLayout.setOnConversationChangeListener(this);
        this.srlRefresh.setOnRefreshListener(this);
    }

    public void initView(Bundle savedInstanceState) {
        this.llRoot = (LinearLayout) findViewById(R.id.ll_root);
        this.srlRefresh = (SwipeRefreshLayout) findViewById(R.id.srl_refresh);
        YkbEaseConversationListLayout ykbEaseConversationListLayout = (YkbEaseConversationListLayout) findViewById(R.id.list_conversation);
        this.conversationListLayout = ykbEaseConversationListLayout;
        ykbEaseConversationListLayout.init();
    }

    @Override // com.hyphenate.easeui.modules.conversation.interfaces.OnConversationLoadListener
    public void loadDataFail(String message) {
        finishRefresh();
    }

    public void loadDataFinish(List<EaseConversationInfo> data) {
        finishRefresh();
    }

    @Override // com.hyphenate.easeui.modules.conversation.interfaces.OnConversationChangeListener
    public void notifyAllChange() {
    }

    @Override // com.hyphenate.easeui.modules.conversation.interfaces.OnConversationChangeListener
    public void notifyItemChange(int position) {
    }

    @Override // com.hyphenate.easeui.modules.conversation.interfaces.OnConversationChangeListener
    public void notifyItemRemove(int position) {
    }

    @Override // androidx.fragment.app.Fragment
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    @Override // androidx.fragment.app.Fragment
    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getLayoutId(), (ViewGroup) null);
    }

    public void onItemClick(View view, int position) {
    }

    public boolean onMenuItemClick(MenuItem item, int position) {
        EMLog.i(TAG, "click menu position = " + position);
        return false;
    }

    @Override // com.hyphenate.easeui.modules.menu.OnPopupMenuPreShowListener
    public void onMenuPreShow(EasePopupMenuHelper menuHelper, int position) {
    }

    public void onRefresh() {
        this.conversationListLayout.loadDefaultData();
    }

    @Override // androidx.fragment.app.Fragment
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(savedInstanceState);
        initListener();
    }
}
