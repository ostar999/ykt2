package com.hyphenate.easeui.modules.contact;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.manager.SidebarPresenter;
import com.hyphenate.easeui.modules.contact.interfaces.IContactLayout;
import com.hyphenate.easeui.modules.contact.interfaces.OnContactLoadListener;
import com.hyphenate.easeui.widget.EaseSidebar;
import java.util.List;

/* loaded from: classes4.dex */
public class EaseContactLayout extends RelativeLayout implements IContactLayout, SwipeRefreshLayout.OnRefreshListener, OnContactLoadListener {
    private boolean canUseRefresh;
    private EaseContactListLayout contactList;
    private TextView floatingHeader;
    private EaseSidebar sideBarContact;
    private SidebarPresenter sidebarPresenter;
    private SwipeRefreshLayout srlContactRefresh;

    public EaseContactLayout(Context context) {
        this(context, null);
    }

    private void finishRefresh() {
        if (this.srlContactRefresh != null) {
            post(new Runnable() { // from class: com.hyphenate.easeui.modules.contact.a
                @Override // java.lang.Runnable
                public final void run() {
                    this.f8723c.lambda$finishRefresh$0();
                }
            });
        }
    }

    private void initListener() {
        this.srlContactRefresh.setOnRefreshListener(this);
        this.contactList.setOnContactLoadListener(this);
    }

    private void initViews() {
        this.srlContactRefresh = (SwipeRefreshLayout) findViewById(R.id.srl_contact_refresh);
        this.contactList = (EaseContactListLayout) findViewById(R.id.contact_list);
        this.sideBarContact = (EaseSidebar) findViewById(R.id.side_bar_contact);
        this.floatingHeader = (TextView) findViewById(R.id.floating_header);
        this.srlContactRefresh.setEnabled(this.canUseRefresh);
        SidebarPresenter sidebarPresenter = new SidebarPresenter();
        this.sidebarPresenter = sidebarPresenter;
        EaseContactListLayout easeContactListLayout = this.contactList;
        sidebarPresenter.setupWithRecyclerView(easeContactListLayout, easeContactListLayout.getListAdapter(), this.floatingHeader);
        this.sideBarContact.setOnTouchEventListener(this.sidebarPresenter);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$finishRefresh$0() {
        this.srlContactRefresh.setRefreshing(false);
    }

    @Override // com.hyphenate.easeui.modules.contact.interfaces.IContactLayout
    public void canUseRefresh(boolean z2) {
        this.canUseRefresh = z2;
        this.srlContactRefresh.setEnabled(z2);
    }

    @Override // com.hyphenate.easeui.modules.contact.interfaces.IContactLayout
    public EaseContactListLayout getContactList() {
        return this.contactList;
    }

    @Override // com.hyphenate.easeui.modules.contact.interfaces.IContactLayout
    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return this.srlContactRefresh;
    }

    @Override // com.hyphenate.easeui.modules.contact.interfaces.OnContactLoadListener
    public void loadDataFail(String str) {
        finishRefresh();
    }

    @Override // com.hyphenate.easeui.modules.contact.interfaces.OnContactLoadListener
    public void loadDataFinish(List<EaseUser> list) {
        finishRefresh();
    }

    public void loadDefaultData() {
        this.contactList.loadDefaultData();
    }

    @Override // androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
    public void onRefresh() {
        this.contactList.loadDefaultData();
    }

    @Override // com.hyphenate.easeui.modules.contact.interfaces.IContactLayout
    public void showNormal() {
        this.contactList.showItemHeader(true);
        this.sideBarContact.setVisibility(0);
    }

    @Override // com.hyphenate.easeui.modules.contact.interfaces.IContactLayout
    public void showSimple() {
        this.contactList.showItemHeader(false);
        this.sideBarContact.setVisibility(8);
    }

    public EaseContactLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public EaseContactLayout(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.canUseRefresh = true;
        LayoutInflater.from(context).inflate(R.layout.ease_layout_contact, this);
        initViews();
        initListener();
    }
}
