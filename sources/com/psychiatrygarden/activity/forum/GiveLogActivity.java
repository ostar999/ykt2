package com.psychiatrygarden.activity.forum;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.WebViewActivity;
import com.psychiatrygarden.activity.forum.fragment.GivLogFragment;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class GiveLogActivity extends BaseActivity {
    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$0(View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        getPopTips();
    }

    public void getPopTips() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("type", "notice_for_rules");
        YJYHttpUtils.get(this, NetworkRequestsURL.vipnoticeApi, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.forum.GiveLogActivity.2
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass2) s2);
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if ("200".equals(jSONObject.optString("code"))) {
                        String strOptString = jSONObject.optJSONObject("data").optString("web_url");
                        Intent intent = new Intent(GiveLogActivity.this, (Class<?>) WebViewActivity.class);
                        intent.putExtra("title", "活动规则");
                        intent.putExtra("web_url", strOptString);
                        GiveLogActivity.this.startActivity(intent);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() throws Resources.NotFoundException {
        String[] strArr;
        setTitle("会员奖励");
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab);
        final ArrayList arrayList = new ArrayList();
        boolean zEquals = "2".equals(SharePreferencesUtils.readStrConfig(CommonParameter.REWARD_SHOW_WAY, this, "1"));
        arrayList.add(GivLogFragment.newInstance(0));
        if (zEquals) {
            arrayList.add(GivLogFragment.newInstance(1));
            strArr = new String[]{"VIP", "SVIP"};
        } else {
            strArr = new String[]{"VIP"};
            viewPager.removeView(tabLayout);
        }
        final String[] strArr2 = strArr;
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager(), 1) { // from class: com.psychiatrygarden.activity.forum.GiveLogActivity.1
            @Override // androidx.viewpager.widget.PagerAdapter
            /* renamed from: getCount */
            public int getSize() {
                return arrayList.size();
            }

            @Override // androidx.fragment.app.FragmentPagerAdapter
            @NonNull
            public Fragment getItem(int position) {
                return (Fragment) arrayList.get(position);
            }

            @Override // androidx.viewpager.widget.PagerAdapter
            @NonNull
            public CharSequence getPageTitle(int position) {
                return strArr2[position];
            }
        });
        if (zEquals) {
            tabLayout.setupWithViewPager(viewPager);
        }
        this.mBtnActionbarRight.setVisibility(0);
        this.mTvActionbarRight.setVisibility(8);
        this.mBtnActionbarRight.setText("规则");
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.fragment_forum_list);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.mBtnActionbarRight.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.forum.v
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12415c.lambda$setListenerForWidget$0(view);
            }
        });
    }
}
