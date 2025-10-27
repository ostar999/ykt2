package com.psychiatrygarden.activity.forum.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.psychiatrygarden.baseview.BaseFragment;
import com.psychiatrygarden.baseview.ViewHolder;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.yikaobang.yixue.R;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class ForumActionFragment extends BaseFragment {
    public String group_id;
    public String timer = "";
    public TextView title;

    public static ForumActionFragment newInstance() {
        Bundle bundle = new Bundle();
        ForumActionFragment forumActionFragment = new ForumActionFragment();
        forumActionFragment.setArguments(bundle);
        return forumActionFragment;
    }

    public void getGroupId() {
        YJYHttpUtils.get(getActivity(), NetworkRequestsURL.getforumgroupApi, new AjaxParams(), new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.forum.fragment.ForumActionFragment.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass1) s2);
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if ("200".equals(jSONObject.optString("code"))) {
                        String strOptString = jSONObject.optJSONObject("data").optString("group_id");
                        if (TextUtils.equals("0", strOptString) || TextUtils.equals("", strOptString)) {
                            SharePreferencesUtils.writeStrConfig(CommonParameter.GroupId, "", ForumActionFragment.this.getActivity());
                            ForumActionFragment.this.title.setVisibility(0);
                            ForumActionFragment.this.loadRootFragment(R.id.actionf, ForumSectionToFragment.newInstance());
                        } else {
                            SharePreferencesUtils.writeStrConfig(CommonParameter.GroupId, strOptString, ForumActionFragment.this.getActivity());
                            CommonUtil.putTimeData();
                            ForumActionFragment.this.pushTime();
                            ForumActionFragment.this.title.setVisibility(8);
                            ForumActionFragment.this.loadRootFragment(R.id.actionf, ForumIndexFragment.newInstance(strOptString));
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public int getLayoutId() {
        return R.layout.fragment_forum_action;
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void initViews(ViewHolder holder, View root) {
        this.title = (TextView) holder.get(R.id.title);
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void onEventMainThread(String str) {
        if ("ResultVeriafer".equals(str)) {
            TextView textView = this.title;
            if (textView != null) {
                textView.setVisibility(8);
            }
            String strConfig = SharePreferencesUtils.readStrConfig(CommonParameter.GroupId, getActivity(), "");
            this.group_id = strConfig;
            loadRootFragment(R.id.actionf, ForumIndexFragment.newInstance(strConfig));
        }
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportFragment, androidx.fragment.app.Fragment
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportFragment, cn.webdemo.com.supporfragment.ISupportFragment
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        getGroupId();
    }

    public void pushTime() {
        String str = (System.currentTimeMillis() / 1000) + "";
        this.timer = str;
        SharePreferencesUtils.writeStrConfig(CommonParameter.forum_time, str, getActivity());
    }
}
