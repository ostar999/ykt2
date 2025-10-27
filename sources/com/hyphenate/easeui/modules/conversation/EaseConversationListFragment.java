package com.hyphenate.easeui.modules.conversation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.interfaces.OnItemClickListener;
import com.hyphenate.easeui.modules.conversation.interfaces.OnConversationChangeListener;
import com.hyphenate.easeui.modules.conversation.interfaces.OnConversationLoadListener;
import com.hyphenate.easeui.modules.conversation.model.EaseConversationInfo;
import com.hyphenate.easeui.modules.menu.EasePopupMenuHelper;
import com.hyphenate.easeui.modules.menu.OnPopupMenuItemClickListener;
import com.hyphenate.easeui.modules.menu.OnPopupMenuPreShowListener;
import com.hyphenate.easeui.ui.base.EaseBaseFragment;
import com.hyphenate.util.EMLog;
import java.util.List;

/* loaded from: classes4.dex */
public class EaseConversationListFragment extends EaseBaseFragment implements OnItemClickListener, OnPopupMenuItemClickListener, OnPopupMenuPreShowListener, SwipeRefreshLayout.OnRefreshListener, OnConversationLoadListener, OnConversationChangeListener {
    private static final String TAG = "EaseConversationListFragment";
    public EaseConversationListLayout conversationListLayout;
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
        runOnUiThread(new Runnable() { // from class: com.hyphenate.easeui.modules.conversation.a
            @Override // java.lang.Runnable
            public final void run() {
                this.f8734c.lambda$finishRefresh$0();
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

    public void initView(Bundle bundle) {
        this.llRoot = (LinearLayout) findViewById(R.id.ll_root);
        this.srlRefresh = (SwipeRefreshLayout) findViewById(R.id.srl_refresh);
        EaseConversationListLayout easeConversationListLayout = (EaseConversationListLayout) findViewById(R.id.list_conversation);
        this.conversationListLayout = easeConversationListLayout;
        easeConversationListLayout.init();
    }

    @Override // com.hyphenate.easeui.modules.conversation.interfaces.OnConversationLoadListener
    public void loadDataFail(String str) {
        finishRefresh();
    }

    @Override // com.hyphenate.easeui.modules.conversation.interfaces.OnConversationLoadListener
    public void loadDataFinish(List<EaseConversationInfo> list) {
        finishRefresh();
    }

    @Override // com.hyphenate.easeui.modules.conversation.interfaces.OnConversationChangeListener
    public void notifyAllChange() {
    }

    @Override // com.hyphenate.easeui.modules.conversation.interfaces.OnConversationChangeListener
    public void notifyItemChange(int i2) {
    }

    @Override // com.hyphenate.easeui.modules.conversation.interfaces.OnConversationChangeListener
    public void notifyItemRemove(int i2) {
    }

    @Override // androidx.fragment.app.Fragment
    public void onActivityCreated(@Nullable Bundle bundle) {
        super.onActivityCreated(bundle);
        initData();
    }

    @Override // androidx.fragment.app.Fragment
    @Nullable
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        return layoutInflater.inflate(getLayoutId(), (ViewGroup) null);
    }

    @Override // com.hyphenate.easeui.interfaces.OnItemClickListener
    public void onItemClick(View view, int i2) {
    }

    @Override // com.hyphenate.easeui.modules.menu.OnPopupMenuItemClickListener
    public boolean onMenuItemClick(MenuItem menuItem, int i2) {
        EMLog.i(TAG, "click menu position = " + i2);
        return false;
    }

    @Override // com.hyphenate.easeui.modules.menu.OnPopupMenuPreShowListener
    public void onMenuPreShow(EasePopupMenuHelper easePopupMenuHelper, int i2) {
    }

    @Override // androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
    public void onRefresh() {
        this.conversationListLayout.loadDefaultData();
    }

    @Override // androidx.fragment.app.Fragment
    public void onViewCreated(@NonNull View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);
        initView(bundle);
        initListener();
    }
}
