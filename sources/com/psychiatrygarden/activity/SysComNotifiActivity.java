package com.psychiatrygarden.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.fragmenthome.SysComNot2ifiFragment;
import com.psychiatrygarden.fragmenthome.SysComNotifiFragment;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.widget.AlphaImageView;
import com.psychiatrygarden.widget.LazyViewPager;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.List;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class SysComNotifiActivity extends BaseActivity {
    private TextView cropNumtv;
    public FrameLayout fraglaytouid;
    private final List<Fragment> mList = new ArrayList();
    private TextView notifiline;
    private TextView notifitxt;
    private LazyViewPager sys_viewpage;
    private RelativeLayout syscoms;
    private TextView syscomsline;
    private TextView syscomstxt;
    private RelativeLayout sysnotifi;

    public static class TabFragmentPagerAdapter extends FragmentPagerAdapter {
        private List<Fragment> mlist;

        public TabFragmentPagerAdapter(FragmentManager fm, List<Fragment> list) {
            super(fm);
            this.mlist = list == null ? new ArrayList<>() : list;
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        /* renamed from: getCount */
        public int getSize() {
            return this.mlist.size();
        }

        @Override // androidx.fragment.app.FragmentPagerAdapter
        @NonNull
        public Fragment getItem(int arg0) {
            return this.mlist.get(arg0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(View view) {
        goActivity(VestReplyActivity.class);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$1(View view) {
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$2(View view) {
        mPutConversion(0);
        this.sys_viewpage.setCurrentItem(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$3(View view) {
        mPutConversion(1);
        this.sys_viewpage.setCurrentItem(1);
    }

    public void getReadot() {
        YJYHttpUtils.get(this, NetworkRequestsURL.virtualUserReplyRedDotApi, new AjaxParams(), new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.SysComNotifiActivity.2
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass2) s2);
                try {
                    if ("1".equals(new JSONObject(s2).optJSONObject("data").optString("show_dot"))) {
                        SysComNotifiActivity.this.cropNumtv.setVisibility(0);
                    } else {
                        SysComNotifiActivity.this.cropNumtv.setVisibility(8);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.cropNumtv = (TextView) findViewById(R.id.cropNumtv);
        this.fraglaytouid = (FrameLayout) findViewById(R.id.fraglaytouid);
        if (UserConfig.isLogin() && "1".equals(UserConfig.getInstance().getUser().getAdmin())) {
            this.fraglaytouid.setVisibility(0);
        } else {
            this.fraglaytouid.setVisibility(8);
        }
        this.fraglaytouid.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.no
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13057c.lambda$init$0(view);
            }
        });
        ((AlphaImageView) findViewById(R.id.sys_icon_left)).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.oo
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13511c.lambda$init$1(view);
            }
        });
        this.sys_viewpage = (LazyViewPager) findViewById(R.id.sys_viewpage);
        boolean booleanExtra = getIntent().getBooleanExtra("coursePosition", false);
        this.syscoms = (RelativeLayout) findViewById(R.id.syscoms);
        this.sysnotifi = (RelativeLayout) findViewById(R.id.sysnotifi);
        this.syscomstxt = (TextView) findViewById(R.id.syscomstxt);
        this.syscomsline = (TextView) findViewById(R.id.syscomsline);
        this.notifitxt = (TextView) findViewById(R.id.notifitxt);
        this.notifiline = (TextView) findViewById(R.id.notifiline);
        this.mList.add(new SysComNotifiFragment());
        this.mList.add(new SysComNot2ifiFragment());
        this.sys_viewpage.setAdapter(new TabFragmentPagerAdapter(getSupportFragmentManager(), this.mList));
        if (booleanExtra) {
            mPutConversion(0);
            this.sys_viewpage.setCurrentItem(0);
        } else {
            mPutConversion(1);
            this.sys_viewpage.setCurrentItem(1);
        }
    }

    public void mPutConversion(int i2) {
        if (i2 == 0) {
            this.syscomsline.setVisibility(0);
            this.notifiline.setVisibility(4);
            if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
                this.syscomstxt.setTextAppearance(R.style.top_sys_view_item_text_select);
                this.notifitxt.setTextAppearance(R.style.top_sys_view_item_text_select_no);
                this.syscomsline.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
                return;
            } else {
                this.syscomstxt.setTextAppearance(R.style.top_sys_view_item_text_select_night);
                this.notifitxt.setTextAppearance(R.style.top_sys_view_item_text_select_no_night);
                this.syscomsline.setBackgroundColor(ContextCompat.getColor(this, R.color.first_text_color_night));
                return;
            }
        }
        if (i2 != 1) {
            return;
        }
        this.notifiline.setVisibility(0);
        this.syscomsline.setVisibility(4);
        if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
            this.notifitxt.setTextAppearance(R.style.top_sys_view_item_text_select);
            this.syscomstxt.setTextAppearance(R.style.top_sys_view_item_text_select_no);
            this.notifiline.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
        } else {
            this.notifitxt.setTextAppearance(R.style.top_sys_view_item_text_select_night);
            this.syscomstxt.setTextAppearance(R.style.top_sys_view_item_text_select_no_night);
            this.notifiline.setBackgroundColor(ContextCompat.getColor(this, R.color.first_text_color_night));
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActionBar.hide();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        if (UserConfig.isLogin() && "1".equals(UserConfig.getInstance().getUser().getAdmin())) {
            getReadot();
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_sys_com_notifi);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.syscoms.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.lo
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12675c.lambda$setListenerForWidget$2(view);
            }
        });
        this.sysnotifi.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.mo
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13027c.lambda$setListenerForWidget$3(view);
            }
        });
        this.sys_viewpage.setOnPageChangeListener(new LazyViewPager.OnPageChangeListener() { // from class: com.psychiatrygarden.activity.SysComNotifiActivity.1
            @Override // com.psychiatrygarden.widget.LazyViewPager.OnPageChangeListener
            public void onPageScrollStateChanged(int state) {
            }

            @Override // com.psychiatrygarden.widget.LazyViewPager.OnPageChangeListener
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override // com.psychiatrygarden.widget.LazyViewPager.OnPageChangeListener
            public void onPageSelected(int position) {
                SysComNotifiActivity.this.mPutConversion(position);
            }
        });
    }
}
