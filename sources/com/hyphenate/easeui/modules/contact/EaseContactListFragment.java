package com.hyphenate.easeui.modules.contact;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.interfaces.OnItemClickListener;
import com.hyphenate.easeui.modules.menu.EasePopupMenuHelper;
import com.hyphenate.easeui.modules.menu.OnPopupMenuItemClickListener;
import com.hyphenate.easeui.modules.menu.OnPopupMenuPreShowListener;
import com.hyphenate.easeui.ui.base.EaseBaseFragment;

/* loaded from: classes4.dex */
public class EaseContactListFragment extends EaseBaseFragment implements OnPopupMenuItemClickListener, OnPopupMenuPreShowListener, OnItemClickListener {
    public EaseContactLayout contactLayout;
    public LinearLayout llRoot;

    public int getLayoutId() {
        return R.layout.ease_fragment_contacts;
    }

    public void initData() {
        this.contactLayout.loadDefaultData();
    }

    public void initListener() {
        this.contactLayout.getContactList().setOnPopupMenuPreShowListener(this);
        this.contactLayout.getContactList().setOnPopupMenuItemClickListener(this);
        this.contactLayout.getContactList().setOnItemClickListener(this);
    }

    public void initView(Bundle bundle) {
        this.llRoot = (LinearLayout) findViewById(R.id.ll_root);
        this.contactLayout = (EaseContactLayout) findViewById(R.id.contact_layout);
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
        return false;
    }

    @Override // com.hyphenate.easeui.modules.menu.OnPopupMenuPreShowListener
    public void onMenuPreShow(EasePopupMenuHelper easePopupMenuHelper, int i2) {
    }

    @Override // androidx.fragment.app.Fragment
    public void onViewCreated(@NonNull View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);
        initView(bundle);
        initListener();
    }
}
