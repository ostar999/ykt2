package com.psychiatrygarden.fragmenthome;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import androidx.annotation.Nullable;
import cn.hutool.core.text.StrPool;
import cn.webdemo.com.supporfragment.base.SupportFragment;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.lxj.xpopup.enums.PopupAnimation;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.lxj.xpopup.interfaces.SimpleCallback;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.setting.AppTestInfoAct;
import com.psychiatrygarden.baseview.BaseFragment;
import com.psychiatrygarden.baseview.ViewHolder;
import com.psychiatrygarden.bean.HomeTabStatus;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.event.ShowStatisticsEvent;
import com.psychiatrygarden.event.VideoBuryPointEvent;
import com.psychiatrygarden.forum.PushCircleAndArticleAct;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.AndroidBaseUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.LogUtils;
import com.psychiatrygarden.utils.PopupShowManager;
import com.psychiatrygarden.widget.BottomTabLayout;
import com.psychiatrygarden.widget.CircleEntranceGuidePop;
import com.psychiatrygarden.widget.QuestionStatisticsPop;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;
import java.util.ArrayList;
import java.util.List;
import kotlin.Triple;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class HomePageNewFragment extends BaseFragment {
    private int mCurrentPosition;
    private View mHomePageMask;
    private ImageView mImgAppTest;
    private BottomTabLayout mTabNav;
    public int position = 0;
    private SupportFragment[] mFragments = new SupportFragment[5];

    /* renamed from: com.psychiatrygarden.fragmenthome.HomePageNewFragment$1, reason: invalid class name */
    public class AnonymousClass1 implements BottomTabLayout.OnTabMenuClickListener {
        public AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onMenuItemClick$0(SupportFragment supportFragment) {
            PopupShowManager.getInstance(((BaseFragment) HomePageNewFragment.this).mContext).checkShowCoupon(((BaseFragment) HomePageNewFragment.this).mContext, PopupShowManager.FLAG_ENTER_APP_HOME, "2", supportFragment instanceof QuestionHomeJumpFragment ? "2" : "1", null);
        }

        private /* synthetic */ void lambda$onMenuItemClick$1() {
            PopupShowManager.getInstance(((BaseFragment) HomePageNewFragment.this).mContext).checkShowCoupon(((BaseFragment) HomePageNewFragment.this).mContext, PopupShowManager.FLAG_ENTER_APP_HOME, "0", null, null);
        }

        @Override // com.psychiatrygarden.widget.BottomTabLayout.OnTabMenuClickListener
        public void jump2PushCircle() {
            if (HomePageNewFragment.this.isLogin()) {
                PushCircleAndArticleAct.newIntent(((BaseFragment) HomePageNewFragment.this).mContext, false, "", "", HomePageNewFragment.this.getArguments().getInt("module_type", 12));
            }
        }

        @Override // com.psychiatrygarden.widget.BottomTabLayout.OnTabMenuClickListener
        public void onMenuItemClick(int currentPosition, int prePosition, String circle) {
            HomePageNewFragment homePageNewFragment = HomePageNewFragment.this;
            homePageNewFragment.showHideFragment(homePageNewFragment.mFragments[currentPosition], HomePageNewFragment.this.mFragments[prePosition]);
            if ("".equals(circle)) {
                EventBus.getDefault().post(new HomeTabStatus("homeTab", currentPosition));
            } else if ("isCircle".equals(circle)) {
                HomePageNewFragment.this.showCircleGuide();
                SharePreferencesUtils.writeStrConfig(CommonParameter.forum_time, String.valueOf(System.currentTimeMillis() / 1000), HomePageNewFragment.this.getActivity());
                EventBus.getDefault().post(new HomeTabStatus("homeTab3", currentPosition));
            }
            SharePreferencesUtils.writeIntConfig(CommonParameter.HOME_CURRENT_POSITION, currentPosition, ((BaseFragment) HomePageNewFragment.this).mContext);
            final SupportFragment supportFragment = HomePageNewFragment.this.mFragments[currentPosition];
            if (HomePageNewFragment.this.mCurrentPosition != currentPosition) {
                if ((supportFragment instanceof QuestionHomeJumpFragment) || (supportFragment instanceof StoreNewFragment)) {
                    HomePageNewFragment.this.mTabNav.postDelayed(new Runnable() { // from class: com.psychiatrygarden.fragmenthome.m7
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f15833c.lambda$onMenuItemClick$0(supportFragment);
                        }
                    }, 200L);
                }
                if (!(supportFragment instanceof QuestionComFragment)) {
                    EventBus.getDefault().post("dismiss_home_bot_mask");
                }
            }
            if (ProjectApp.newHomeStyle && currentPosition == 0) {
                EventBus.getDefault().post(new HomeTabStatus("homeTab", 0));
            }
            if (HomePageNewFragment.this.mFragments[currentPosition] instanceof QuestionComFragment) {
                EventBus.getDefault().post(new HomeTabStatus("homeTab", -1));
            }
            HomePageNewFragment.this.mCurrentPosition = currentPosition;
        }
    }

    /* renamed from: com.psychiatrygarden.fragmenthome.HomePageNewFragment$2, reason: invalid class name */
    public class AnonymousClass2 implements EMMessageListener {
        public AnonymousClass2() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onMessageReceived$0() {
            HomePageNewFragment.this.mTabNav.isChatReadMessage();
        }

        @Override // com.hyphenate.EMMessageListener
        public void onCmdMessageReceived(List<EMMessage> messages) {
        }

        @Override // com.hyphenate.EMMessageListener
        public /* synthetic */ void onGroupMessageRead(List list) {
            d1.e.b(this, list);
        }

        @Override // com.hyphenate.EMMessageListener
        public /* synthetic */ void onMessageChanged(EMMessage eMMessage, Object obj) {
            d1.e.c(this, eMMessage, obj);
        }

        @Override // com.hyphenate.EMMessageListener
        public void onMessageDelivered(List<EMMessage> messages) {
        }

        @Override // com.hyphenate.EMMessageListener
        public void onMessageRead(List<EMMessage> messages) {
        }

        @Override // com.hyphenate.EMMessageListener
        public void onMessageRecalled(List<EMMessage> messages) {
        }

        @Override // com.hyphenate.EMMessageListener
        public void onMessageReceived(List<EMMessage> messages) {
            EventBus.getDefault().post("PushIMReadEvent");
            HomePageNewFragment.this.mTabNav.post(new Runnable() { // from class: com.psychiatrygarden.fragmenthome.n7
                @Override // java.lang.Runnable
                public final void run() {
                    this.f15867c.lambda$onMessageReceived$0();
                }
            });
        }

        @Override // com.hyphenate.EMMessageListener
        public /* synthetic */ void onReactionChanged(List list) {
            d1.e.g(this, list);
        }

        @Override // com.hyphenate.EMMessageListener
        public /* synthetic */ void onReadAckForGroupMessageUpdated() {
            d1.e.h(this);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$0() {
        this.mTabNav.isChatReadMessage();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$1(View view) {
        this.mContext.startActivity(new Intent(this.mContext, (Class<?>) AppTestInfoAct.class).putExtra("web_url", NetworkRequestsURL.appTestFeedBack).putExtra("title", "医考帮试用中心"));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$initViews$2(View view) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$onEventMainThread$3() {
    }

    public static HomePageNewFragment newInstance(int position) {
        HomePageNewFragment homePageNewFragment = new HomePageNewFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        homePageNewFragment.setArguments(bundle);
        return homePageNewFragment;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showCircleGuide() {
        if (SharePreferencesUtils.readBooleanConfig(CommonParameter.SHOW_CIRCLE_ENTRANCE_GUIDE, false, getActivity())) {
            return;
        }
        Triple<View, Integer, Integer> circleView = this.mTabNav.getCircleView();
        if (circleView.getFirst() != null) {
            ((CircleEntranceGuidePop) new XPopup.Builder(getActivity()).setPopupCallback(new SimpleCallback() { // from class: com.psychiatrygarden.fragmenthome.HomePageNewFragment.4
                @Override // com.lxj.xpopup.interfaces.SimpleCallback, com.lxj.xpopup.interfaces.XPopupCallback
                public void onDismiss(BasePopupView popupView) {
                    SharePreferencesUtils.writeBooleanConfig(CommonParameter.SHOW_CIRCLE_ENTRANCE_GUIDE, true, HomePageNewFragment.this.getActivity());
                }
            }).isCenterHorizontal(true).popupAnimation(PopupAnimation.ScaleAlphaFromCenter).hasShadowBg(Boolean.TRUE).dismissOnTouchOutside(Boolean.FALSE).asCustom(new CircleEntranceGuidePop(getActivity(), circleView.getSecond().intValue()))).show();
        }
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public int getLayoutId() {
        return R.layout.activity_homepage_new;
    }

    public void getReadRot() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("ver", "v2");
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.msgUnredNumber, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.fragmenthome.HomePageNewFragment.5
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass5) s2);
                try {
                    if (new JSONObject(s2).optString("code").equals("200")) {
                        String strOptString = new JSONObject(s2).optJSONObject("data").optString("show_dot");
                        String strOptString2 = new JSONObject(s2).optJSONObject("data").optString("count");
                        if (TextUtils.isEmpty(strOptString2)) {
                            SharePreferencesUtils.writeIntConfig(CommonParameter.SYStem_UnRead_Msg_Count, 0, ((BaseFragment) HomePageNewFragment.this).mContext);
                        } else {
                            SharePreferencesUtils.writeIntConfig(CommonParameter.SYStem_UnRead_Msg_Count, Integer.parseInt(strOptString2), ((BaseFragment) HomePageNewFragment.this).mContext);
                        }
                        if (!strOptString.equals("1")) {
                            EventBus.getDefault().post(CommonParameter.SYStem_Red_Dot);
                        } else {
                            SharePreferencesUtils.writeBooleanConfig(CommonParameter.SYStem_Red_Dot, true, ((BaseFragment) HomePageNewFragment.this).mContext);
                            EventBus.getDefault().post(CommonParameter.SYStem_Red_Dot);
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void initViews(ViewHolder holder, View root) {
        this.mTabNav = (BottomTabLayout) holder.get(R.id.tabnav);
        this.mHomePageMask = holder.get(R.id.homepage_mask);
        this.mImgAppTest = (ImageView) holder.get(R.id.img_app_test);
        this.mTabNav.setOnTabMenuClickListener(new AnonymousClass1());
        if (!UserConfig.getUserId().equals("")) {
            EMClient.getInstance().chatManager().addMessageListener(new AnonymousClass2());
            this.mTabNav.postDelayed(new Runnable() { // from class: com.psychiatrygarden.fragmenthome.j7
                @Override // java.lang.Runnable
                public final void run() {
                    this.f15687c.lambda$initViews$0();
                }
            }, 1000L);
        }
        this.mImgAppTest.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.k7
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15714c.lambda$initViews$1(view);
            }
        });
        this.mHomePageMask.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.l7
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                HomePageNewFragment.lambda$initViews$2(view);
            }
        });
        jumpToFragment();
    }

    public void jumpToFragment() {
        ArrayList arrayList = new ArrayList();
        if (getArguments() != null) {
            this.position = getArguments().getInt("position");
        }
        this.mTabNav.setType(this.position);
        boolean zEquals = "1".equals(SharePreferencesUtils.readStrConfig(CommonParameter.is_hidden_shop, getActivity()));
        boolean zEquals2 = "1".equals(SharePreferencesUtils.readStrConfig(CommonParameter.is_hidden_course, getActivity()));
        arrayList.add(!ProjectApp.newHomeStyle ? QuestionComFragment.newInstance() : new HomeNewStyleFragment());
        if (ProjectApp.newHomeStyle) {
            arrayList.add(QuestionComFragment.newInstance());
        }
        if (!zEquals2 && !ProjectApp.newHomeStyle) {
            arrayList.add(QuestionHomeJumpFragment.newInstance());
        }
        arrayList.add(QuestionDatabaseFragmentNew.newInstance());
        if (!zEquals2 && ProjectApp.newHomeStyle) {
            arrayList.add(QuestionHomeJumpFragment.newInstance());
        }
        if (!zEquals && !ProjectApp.newHomeStyle) {
            arrayList.add(StoreNewFragment.newInstance());
        }
        arrayList.add(PersonalCenter2Fragment.newInstance());
        this.mFragments = new SupportFragment[arrayList.size()];
        int i2 = 0;
        for (int i3 = 0; i3 < arrayList.size(); i3++) {
            this.mFragments[i3] = (SupportFragment) arrayList.get(i3);
        }
        int i4 = this.position;
        SupportFragment[] supportFragmentArr = this.mFragments;
        if (i4 > supportFragmentArr.length - 1) {
            this.position = 0;
        }
        loadMultipleRootFragment(R.id.framlayout_main, this.position, supportFragmentArr);
        try {
            AndroidBaseUtils.getAppVersion(this.mContext);
            SharePreferencesUtils.readBooleanConfig("isOpenAppVersion", false, this.mContext);
            String strConfig = SharePreferencesUtils.readStrConfig("appTestVersion", this.mContext);
            if (!strConfig.contains(StrPool.DOT)) {
                StringBuilder sb = new StringBuilder();
                while (i2 < strConfig.length()) {
                    int i5 = i2 + 1;
                    sb.append(strConfig.substring(i2, i5));
                    if (i2 < strConfig.length() - 1) {
                        sb.append(StrPool.DOT);
                    }
                    i2 = i5;
                }
            }
            this.mImgAppTest.setVisibility(8);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportFragment, androidx.fragment.app.Fragment
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:157:0x030f  */
    /* JADX WARN: Removed duplicated region for block: B:160:0x031c  */
    @Override // com.psychiatrygarden.baseview.BaseFragment
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onEventMainThread(java.lang.String r9) {
        /*
            Method dump skipped, instructions count: 894
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.psychiatrygarden.fragmenthome.HomePageNewFragment.onEventMainThread(java.lang.String):void");
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportFragment, androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        try {
            this.mTabNav.isChatReadMessage();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        this.mTabNav.post(new Runnable() { // from class: com.psychiatrygarden.fragmenthome.i7
            @Override // java.lang.Runnable
            public final void run() {
                this.f15664c.getReadRot();
            }
        });
        EventBus.getDefault().post("PushIMReadEvent");
    }

    @Subscribe
    public void onEventMainThread(ShowStatisticsEvent event) {
        new XPopup.Builder(this.mContext).asCustom(new QuestionStatisticsPop(this.mContext, event.getData(), new OnConfirmListener() { // from class: com.psychiatrygarden.fragmenthome.h7
            @Override // com.lxj.xpopup.interfaces.OnConfirmListener
            public final void onConfirm() {
                HomePageNewFragment.lambda$onEventMainThread$3();
            }
        })).show();
    }

    @Subscribe
    public void onEventMainThread(VideoBuryPointEvent event) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("current_time", String.valueOf(System.currentTimeMillis() / 1000));
        if (!TextUtils.isEmpty(event.getCourse_id())) {
            ajaxParams.put("course_id", event.getCourse_id());
        } else {
            String strConfig = SharePreferencesUtils.readStrConfig(CommonParameter.LIVE_COURSE_ID, getContext());
            if (!TextUtils.isEmpty(strConfig)) {
                ajaxParams.put("course_id", strConfig);
            }
        }
        ajaxParams.put("obj_id", event.getObj_id());
        ajaxParams.put("obj_type", event.getObj_type());
        ajaxParams.put("type", event.getType());
        if ("1".equals(event.getType())) {
            if (!TextUtils.isEmpty(event.getFree_watch())) {
                ajaxParams.put("free_watch", event.getFree_watch());
            }
            if (!TextUtils.isEmpty(event.getIs_permission())) {
                ajaxParams.put("is_permission", event.getIs_permission());
            }
        }
        if ("2".equals(event.getType()) && event.isQuitPlay()) {
            SharePreferencesUtils.writeStrConfig(CommonParameter.LIVE_COURSE_ID, "", getContext());
        }
        YJYHttpUtils.post(requireContext(), NetworkRequestsURL.videoBuryPoint, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.fragmenthome.HomePageNewFragment.3
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass3) s2);
                try {
                    if ("200".equals(new JSONObject(s2).optString("code"))) {
                        LogUtils.d("onSuccess", "buryPoint success 2");
                    }
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
            }
        });
    }
}
