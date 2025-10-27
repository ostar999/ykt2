package com.easefun.polyv.livecloudclass.modules.pagemenu;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.viewpager.widget.ViewPager;
import com.easefun.polyv.livecloudclass.R;
import com.easefun.polyv.livecloudclass.modules.chatroom.PLVLCChatFragment;
import com.easefun.polyv.livecloudclass.modules.chatroom.PLVLCQuizFragment;
import com.easefun.polyv.livecloudclass.modules.chatroom.adapter.PLVLCChatCommonMessageList;
import com.easefun.polyv.livecloudclass.modules.chatroom.adapter.holder.PLVLCMessageViewHolder;
import com.easefun.polyv.livecloudclass.modules.pagemenu.IPLVLCLivePageMenuLayout;
import com.easefun.polyv.livecloudclass.modules.pagemenu.desc.PLVLCLiveDescFragment;
import com.easefun.polyv.livecloudclass.modules.pagemenu.iframe.PLVLCIFrameFragment;
import com.easefun.polyv.livecloudclass.modules.pagemenu.previous.PLVLCPlaybackChapterFragment;
import com.easefun.polyv.livecloudclass.modules.pagemenu.previous.PLVLCPlaybackPreviousFragment;
import com.easefun.polyv.livecloudclass.modules.pagemenu.product.PLVLCProductFragment;
import com.easefun.polyv.livecloudclass.modules.pagemenu.question.PLVLCQAFragment;
import com.easefun.polyv.livecloudclass.modules.pagemenu.text.PLVLCTextFragment;
import com.easefun.polyv.livecloudclass.modules.pagemenu.tuwen.PLVLCTuWenFragment;
import com.easefun.polyv.livecommon.module.data.IPLVLiveRoomDataManager;
import com.easefun.polyv.livecommon.module.data.PLVStatefulData;
import com.easefun.polyv.livecommon.module.modules.chatroom.contract.IPLVChatroomContract;
import com.easefun.polyv.livecommon.module.modules.chatroom.presenter.PLVChatroomPresenter;
import com.easefun.polyv.livecommon.module.modules.chatroom.view.PLVAbsChatroomView;
import com.easefun.polyv.livecommon.module.modules.commodity.viewmodel.PLVCommodityViewModel;
import com.easefun.polyv.livecommon.module.modules.interact.cardpush.PLVCardPushManager;
import com.easefun.polyv.livecommon.module.modules.player.live.enums.PLVLiveStateEnum;
import com.easefun.polyv.livecommon.module.modules.previous.contract.IPLVPreviousPlaybackContract;
import com.easefun.polyv.livecommon.module.modules.previous.presenter.PLVPreviousPlaybackPresenter;
import com.easefun.polyv.livecommon.module.modules.socket.IPLVSocketLoginManager;
import com.easefun.polyv.livecommon.module.modules.socket.PLVAbsOnSocketEventListener;
import com.easefun.polyv.livecommon.module.modules.socket.PLVSocketLoginManager;
import com.easefun.polyv.livecommon.module.utils.PLVToast;
import com.easefun.polyv.livecommon.module.utils.imageloader.glide.progress.PLVMyProgressManager;
import com.easefun.polyv.livecommon.module.utils.listener.IPLVOnDataChangedListener;
import com.easefun.polyv.livecommon.module.utils.span.PLVTextFaceLoader;
import com.easefun.polyv.livecommon.ui.widget.itemview.adapter.PLVViewPagerAdapter;
import com.easefun.polyv.livecommon.ui.widget.magicindicator.PLVMagicIndicator;
import com.easefun.polyv.livecommon.ui.widget.magicindicator.PLVViewPagerHelper;
import com.easefun.polyv.livecommon.ui.widget.magicindicator.buildins.commonnavigator.PLVCommonNavigator;
import com.easefun.polyv.livecommon.ui.widget.magicindicator.buildins.commonnavigator.abs.IPLVPagerIndicator;
import com.easefun.polyv.livecommon.ui.widget.magicindicator.buildins.commonnavigator.abs.IPLVPagerTitleView;
import com.easefun.polyv.livecommon.ui.widget.magicindicator.buildins.commonnavigator.abs.PLVCommonNavigatorAdapter;
import com.easefun.polyv.livecommon.ui.widget.magicindicator.buildins.commonnavigator.indicators.PLVLinePagerIndicator;
import com.easefun.polyv.livecommon.ui.widget.magicindicator.buildins.commonnavigator.titles.PLVColorTransitionPagerTitleView;
import com.easefun.polyv.livescenes.model.PolyvLiveClassDetailVO;
import com.google.android.exoplayer2.C;
import com.plv.foundationsdk.component.di.PLVDependManager;
import com.plv.foundationsdk.utils.PLVSugarUtil;
import com.plv.livescenes.model.PLVChatFunctionSwitchVO;
import com.plv.livescenes.model.PLVEmotionImageVO;
import com.plv.livescenes.model.PLVLiveClassDetailVO;
import com.plv.livescenes.model.PLVPlaybackChannelDetailVO;
import com.plv.livescenes.model.interact.PLVWebviewUpdateAppStatusVO;
import com.plv.livescenes.playback.chat.IPLVChatPlaybackGetDataListener;
import com.plv.livescenes.playback.chat.IPLVChatPlaybackManager;
import com.plv.livescenes.playback.chat.PLVChatPlaybackData;
import com.plv.livescenes.playback.chat.PLVChatPlaybackFootDataListener;
import com.plv.livescenes.playback.chat.PLVChatPlaybackManager;
import com.plv.livescenes.socket.PLVSocketWrapper;
import com.plv.socket.event.PLVEventHelper;
import com.plv.socket.event.commodity.PLVProductMenuSwitchEvent;
import com.plv.socket.event.login.PLVKickEvent;
import com.plv.socket.event.login.PLVLoginRefuseEvent;
import com.plv.socket.event.login.PLVReloginEvent;
import com.plv.thirdpart.blankj.utilcode.util.ConvertUtils;
import com.plv.thirdpart.blankj.utilcode.util.ScreenUtils;
import com.plv.thirdpart.blankj.utilcode.util.ToastUtils;
import com.plv.thirdpart.blankj.utilcode.util.Utils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes3.dex */
public class PLVLCLivePageMenuLayout extends FrameLayout implements IPLVLCLivePageMenuLayout {
    private PLVCardPushManager cardPushManager;
    private PLVLCPlaybackChapterFragment chapterFragment;
    private PLVLCChatCommonMessageList chatCommonMessageList;
    private PLVLCChatFragment chatFragment;
    private boolean chatPlaybackEnabled;
    private IPLVChatPlaybackManager chatPlaybackManager;
    private IPLVChatroomContract.IChatroomView chatroomMvpView;
    private IPLVChatroomContract.IChatroomPresenter chatroomPresenter;
    private final PLVCommodityViewModel commodityViewModel;
    private PLVLCIFrameFragment iFrameFragment;
    private PLVLCLiveDescFragment liveDescFragment;
    private IPLVLiveRoomDataManager liveRoomDataManager;
    private List<Pair<String, String>> needAddedChatPlaybackTask;
    private IPLVSocketLoginManager.OnSocketEventListener onSocketEventListener;
    private IPLVLCLivePageMenuLayout.OnViewActionListener onViewActionListener;
    private PLVViewPagerAdapter pageMenuTabAdapter;
    private List<Fragment> pageMenuTabFragmentList;
    private PLVMagicIndicator pageMenuTabIndicator;
    private List<String> pageMenuTabTitleList;
    private ViewPager pageMenuTabViewPager;
    private PLVLCPlaybackPreviousFragment previousFragment;
    private IPLVPreviousPlaybackContract.IPreviousPlaybackPresenter previousPlaybackPresenter;
    private PLVLCProductFragment productFragment;
    private PLVLCQAFragment questionsAndAnswersFragment;
    private PLVLCQuizFragment quizFragment;
    private IPLVSocketLoginManager socketLoginManager;
    private PLVLCTextFragment textFragment;
    private PLVLCTuWenFragment tuWenFragment;

    public PLVLCLivePageMenuLayout(@NonNull Context context) {
        this(context, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean addBuyProductTab(String str) {
        PLVLCProductFragment pLVLCProductFragment = this.productFragment;
        if (pLVLCProductFragment != null && this.pageMenuTabFragmentList.contains(pLVLCProductFragment)) {
            return false;
        }
        if (this.productFragment == null) {
            this.productFragment = new PLVLCProductFragment();
        }
        this.productFragment.init(this.liveRoomDataManager);
        this.pageMenuTabTitleList.add(str);
        this.pageMenuTabFragmentList.add(this.productFragment);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addChapterTab() throws Resources.NotFoundException {
        this.pageMenuTabTitleList.add(getResources().getString(R.string.tab_chapter));
        if (this.chapterFragment == null) {
            this.chapterFragment = new PLVLCPlaybackChapterFragment();
        }
        this.pageMenuTabFragmentList.add(this.chapterFragment);
        if (this.previousPlaybackPresenter == null) {
            this.previousPlaybackPresenter = new PLVPreviousPlaybackPresenter(this.liveRoomDataManager);
        }
        this.chapterFragment.init(this.previousPlaybackPresenter);
        refreshPageMenuTabAdapter();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addChatTab(PLVLiveClassDetailVO.DataBean.ChannelMenusBean channelMenusBean) {
        this.pageMenuTabTitleList.add(channelMenusBean.getName());
        if (this.chatFragment == null) {
            PLVLCChatFragment pLVLCChatFragment = new PLVLCChatFragment();
            this.chatFragment = pLVLCChatFragment;
            pLVLCChatFragment.init(this.chatCommonMessageList);
            this.chatFragment.setCardPushManager(this.cardPushManager);
            this.chatFragment.setIsChatPlaybackLayout(isChatPlaybackEnabled());
            this.chatPlaybackManager.addOnCallDataListener(this.chatFragment.getChatPlaybackDataListener());
            this.chatroomPresenter.registerView(this.chatFragment.getChatroomView());
        }
        this.chatFragment.setIsLiveType(this.liveRoomDataManager.getConfig().isLive());
        this.chatFragment.setOnViewActionListener(new PLVLCChatFragment.OnViewActionListener() { // from class: com.easefun.polyv.livecloudclass.modules.pagemenu.PLVLCLivePageMenuLayout.3
            @Override // com.easefun.polyv.livecloudclass.modules.chatroom.PLVLCChatFragment.OnViewActionListener
            public void onClickDynamicFunction(String str) {
                if (PLVLCLivePageMenuLayout.this.onViewActionListener != null) {
                    PLVLCLivePageMenuLayout.this.onViewActionListener.onClickChatMoreDynamicFunction(str);
                }
            }

            @Override // com.easefun.polyv.livecloudclass.modules.chatroom.PLVLCChatFragment.OnViewActionListener
            public void onShowBulletinAction() {
                if (PLVLCLivePageMenuLayout.this.onViewActionListener != null) {
                    PLVLCLivePageMenuLayout.this.onViewActionListener.onShowBulletinAction();
                }
            }

            @Override // com.easefun.polyv.livecloudclass.modules.chatroom.PLVLCChatFragment.OnViewActionListener
            public void onShowEffectAction(boolean z2) {
                if (PLVLCLivePageMenuLayout.this.onViewActionListener != null) {
                    PLVLCLivePageMenuLayout.this.onViewActionListener.onShowEffectAction(z2);
                }
            }

            @Override // com.easefun.polyv.livecloudclass.modules.chatroom.PLVLCChatFragment.OnViewActionListener
            public void onShowRewardAction() {
                if (PLVLCLivePageMenuLayout.this.onViewActionListener != null) {
                    PLVLCLivePageMenuLayout.this.onViewActionListener.onShowRewardAction();
                }
            }
        });
        this.pageMenuTabFragmentList.add(this.chatFragment);
        IPLVLCLivePageMenuLayout.OnViewActionListener onViewActionListener = this.onViewActionListener;
        if (onViewActionListener != null) {
            onViewActionListener.onAddedChatTab(isChatPlaybackEnabled());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addDescTab(PolyvLiveClassDetailVO polyvLiveClassDetailVO, PLVLiveClassDetailVO.DataBean.ChannelMenusBean channelMenusBean) {
        this.pageMenuTabTitleList.add(channelMenusBean.getName());
        PLVLCLiveDescFragment pLVLCLiveDescFragment = new PLVLCLiveDescFragment();
        this.liveDescFragment = pLVLCLiveDescFragment;
        pLVLCLiveDescFragment.init(polyvLiveClassDetailVO);
        this.pageMenuTabFragmentList.add(this.liveDescFragment);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addIFrameTab(PLVLiveClassDetailVO.DataBean.ChannelMenusBean channelMenusBean) {
        this.pageMenuTabTitleList.add(channelMenusBean.getName());
        PLVLCIFrameFragment pLVLCIFrameFragment = new PLVLCIFrameFragment();
        this.iFrameFragment = pLVLCIFrameFragment;
        pLVLCIFrameFragment.init(channelMenusBean.getContent());
        this.pageMenuTabFragmentList.add(this.iFrameFragment);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addPreviousTab(PLVLiveClassDetailVO.DataBean.ChannelMenusBean channelMenusBean) {
        this.pageMenuTabTitleList.add(channelMenusBean.getName());
        if (this.previousFragment == null) {
            this.previousFragment = new PLVLCPlaybackPreviousFragment();
        }
        this.pageMenuTabFragmentList.add(this.previousFragment);
        if (this.previousPlaybackPresenter == null) {
            this.previousPlaybackPresenter = new PLVPreviousPlaybackPresenter(this.liveRoomDataManager);
        }
        this.previousFragment.init(this.previousPlaybackPresenter);
        observerChapters();
        this.liveRoomDataManager.requestPlaybackChannelStatus();
        observerPreviousData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addQATab(PLVLiveClassDetailVO.DataBean.ChannelMenusBean channelMenusBean) {
        this.pageMenuTabTitleList.add(channelMenusBean.getName());
        this.questionsAndAnswersFragment = new PLVLCQAFragment();
        PLVLiveClassDetailVO.DataBean.QADataBean qADataBean = new PLVLiveClassDetailVO.DataBean.QADataBean();
        qADataBean.setChannelId(this.liveRoomDataManager.getConfig().getChannelId());
        qADataBean.setRoomId(this.liveRoomDataManager.getConfig().getChannelId());
        qADataBean.setSessionId(this.liveRoomDataManager.getSessionId());
        qADataBean.setUserId(this.liveRoomDataManager.getConfig().getUser().getViewerId());
        qADataBean.setUserPic(this.liveRoomDataManager.getConfig().getUser().getViewerAvatar());
        qADataBean.setUserNick(this.liveRoomDataManager.getConfig().getUser().getViewerName());
        qADataBean.setTheme(PLVLiveClassDetailVO.DataBean.QADataBean.THEME_BLACK);
        qADataBean.setSocketMsg();
        this.questionsAndAnswersFragment.init(qADataBean.getSocketMsg());
        this.pageMenuTabFragmentList.add(this.questionsAndAnswersFragment);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addQuizTab(PLVLiveClassDetailVO.DataBean.ChannelMenusBean channelMenusBean) {
        this.pageMenuTabTitleList.add(channelMenusBean.getName());
        if (this.quizFragment == null) {
            PLVLCQuizFragment pLVLCQuizFragment = new PLVLCQuizFragment();
            this.quizFragment = pLVLCQuizFragment;
            this.chatroomPresenter.registerView(pLVLCQuizFragment.getChatroomView());
        }
        this.pageMenuTabFragmentList.add(this.quizFragment);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addTextTab(PLVLiveClassDetailVO.DataBean.ChannelMenusBean channelMenusBean) {
        this.pageMenuTabTitleList.add(channelMenusBean.getName());
        PLVLCTextFragment pLVLCTextFragment = new PLVLCTextFragment();
        this.textFragment = pLVLCTextFragment;
        pLVLCTextFragment.init(channelMenusBean.getContent());
        this.pageMenuTabFragmentList.add(this.textFragment);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addTuWenTab(PLVLiveClassDetailVO.DataBean.ChannelMenusBean channelMenusBean) {
        this.pageMenuTabTitleList.add(channelMenusBean.getName());
        PLVLCTuWenFragment pLVLCTuWenFragment = new PLVLCTuWenFragment();
        this.tuWenFragment = pLVLCTuWenFragment;
        pLVLCTuWenFragment.init(this.liveRoomDataManager.getConfig().getChannelId());
        this.pageMenuTabFragmentList.add(this.tuWenFragment);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkStartChatPlayback() {
        List<Pair<String, String>> list = this.needAddedChatPlaybackTask;
        if (list != null && list.size() > 0) {
            Pair<String, String> pair = this.needAddedChatPlaybackTask.get(r0.size() - 1);
            this.chatPlaybackManager.start((String) pair.first, (String) pair.second);
        }
        this.needAddedChatPlaybackTask = null;
    }

    private void destroySocketLoginManager() {
        IPLVSocketLoginManager iPLVSocketLoginManager = this.socketLoginManager;
        if (iPLVSocketLoginManager != null) {
            iPLVSocketLoginManager.destroy();
        }
    }

    private void initChatPlaybackManager() {
        PLVChatPlaybackManager pLVChatPlaybackManager = new PLVChatPlaybackManager();
        this.chatPlaybackManager = pLVChatPlaybackManager;
        pLVChatPlaybackManager.setOnGetDataListener(new IPLVChatPlaybackGetDataListener() { // from class: com.easefun.polyv.livecloudclass.modules.pagemenu.PLVLCLivePageMenuLayout.4
            @Override // com.plv.livescenes.playback.chat.IPLVChatPlaybackGetDataListener
            public boolean canScrollBottom() {
                return PLVLCLivePageMenuLayout.this.chatCommonMessageList == null || PLVLCLivePageMenuLayout.this.chatCommonMessageList.canScrollVertically(1);
            }

            @Override // com.plv.livescenes.playback.chat.IPLVChatPlaybackGetDataListener
            public int currentTime() {
                if (PLVLCLivePageMenuLayout.this.onViewActionListener != null) {
                    return PLVLCLivePageMenuLayout.this.onViewActionListener.getVideoCurrentPosition();
                }
                return 0;
            }

            @Override // com.plv.livescenes.playback.chat.IPLVChatPlaybackGetDataListener
            public Object[] getParsedEmoObjects(String str) {
                return PLVLCLivePageMenuLayout.this.chatroomPresenter != null ? PLVTextFaceLoader.messageToSpan(PLVChatroomPresenter.convertSpecialString(str), PLVLCLivePageMenuLayout.this.chatroomPresenter.getSpeakEmojiSizes(), Utils.getApp()) : new CharSequence[]{PLVTextFaceLoader.messageToSpan(PLVChatroomPresenter.convertSpecialString(str), ConvertUtils.dp2px(16.0f), Utils.getApp())};
            }
        });
        this.chatPlaybackManager.addOnCallDataListener(new PLVChatPlaybackFootDataListener(10) { // from class: com.easefun.polyv.livecloudclass.modules.pagemenu.PLVLCLivePageMenuLayout.5
            @Override // com.plv.livescenes.playback.chat.PLVChatPlaybackFootDataListener
            public void onFootAtTimeDataInserted(List<PLVChatPlaybackData> list) {
                if (PLVLCLivePageMenuLayout.this.chatFragment != null) {
                    for (PLVChatPlaybackData pLVChatPlaybackData : list) {
                        boolean z2 = PLVEventHelper.isSpecialType(pLVChatPlaybackData.getUserType()) || PLVSocketWrapper.getInstance().getLoginVO().getUserId().equals(pLVChatPlaybackData.getUserId());
                        if (!PLVLCLivePageMenuLayout.this.chatFragment.isDisplaySpecialType() || (PLVLCLivePageMenuLayout.this.chatFragment.isDisplaySpecialType() && z2)) {
                            if (PLVLCLivePageMenuLayout.this.onViewActionListener != null) {
                                PLVLCLivePageMenuLayout.this.onViewActionListener.onSendDanmuAction((CharSequence) pLVChatPlaybackData.getObjects()[0]);
                            }
                        }
                    }
                }
            }
        });
    }

    private void initChatroomMvpView(IPLVChatroomContract.IChatroomPresenter iChatroomPresenter) {
        PLVAbsChatroomView pLVAbsChatroomView = new PLVAbsChatroomView() { // from class: com.easefun.polyv.livecloudclass.modules.pagemenu.PLVLCLivePageMenuLayout.2
            @Override // com.easefun.polyv.livecommon.module.modules.chatroom.view.PLVAbsChatroomView, com.easefun.polyv.livecommon.module.modules.chatroom.contract.IPLVChatroomContract.IChatroomView
            public void onProductMenuSwitchEvent(@NonNull final PLVProductMenuSwitchEvent pLVProductMenuSwitchEvent) {
                PLVLCLivePageMenuLayout.this.post(new Runnable() { // from class: com.easefun.polyv.livecloudclass.modules.pagemenu.PLVLCLivePageMenuLayout.2.1
                    @Override // java.lang.Runnable
                    public void run() throws Resources.NotFoundException {
                        if (((Boolean) PLVSugarUtil.getNullableOrDefault(new PLVSugarUtil.Supplier<Boolean>() { // from class: com.easefun.polyv.livecloudclass.modules.pagemenu.PLVLCLivePageMenuLayout.2.1.1
                            /* JADX WARN: Can't rename method to resolve collision */
                            @Override // com.plv.foundationsdk.utils.PLVSugarUtil.Supplier
                            public Boolean get() {
                                return Boolean.valueOf(pLVProductMenuSwitchEvent.getContent().isEnabled());
                            }
                        }, Boolean.FALSE)).booleanValue() ? PLVLCLivePageMenuLayout.this.addBuyProductTab((String) PLVSugarUtil.getNullableOrDefault(new PLVSugarUtil.Supplier<String>() { // from class: com.easefun.polyv.livecloudclass.modules.pagemenu.PLVLCLivePageMenuLayout.2.1.2
                            @Override // com.plv.foundationsdk.utils.PLVSugarUtil.Supplier
                            public String get() {
                                return pLVProductMenuSwitchEvent.getContent().getName();
                            }
                        }, "")) : PLVLCLivePageMenuLayout.this.removeBuyProductTab()) {
                            PLVLCLivePageMenuLayout.this.refreshPageMenuTabAdapter();
                        }
                    }
                });
            }
        };
        this.chatroomMvpView = pLVAbsChatroomView;
        iChatroomPresenter.registerView(pLVAbsChatroomView);
    }

    private void initSocketLoginManager() {
        PLVSocketLoginManager pLVSocketLoginManager = new PLVSocketLoginManager(this.liveRoomDataManager);
        this.socketLoginManager = pLVSocketLoginManager;
        pLVSocketLoginManager.init();
        this.socketLoginManager.setOnSocketEventListener(this.onSocketEventListener);
        this.socketLoginManager.login();
    }

    private void initView() throws Resources.NotFoundException {
        if (ScreenUtils.isPortrait()) {
            setVisibility(0);
        } else {
            setVisibility(8);
        }
        LayoutInflater.from(getContext()).inflate(R.layout.plvlc_live_page_menu_layout, (ViewGroup) this, true);
        this.pageMenuTabIndicator = (PLVMagicIndicator) findViewById(R.id.chatroom_tab);
        this.pageMenuTabViewPager = (ViewPager) findViewById(R.id.chatroom_vp);
        this.pageMenuTabTitleList = new ArrayList();
        this.pageMenuTabFragmentList = new ArrayList();
        this.pageMenuTabAdapter = new PLVViewPagerAdapter(((AppCompatActivity) getContext()).getSupportFragmentManager(), this.pageMenuTabFragmentList);
        this.pageMenuTabViewPager.setOffscreenPageLimit(Integer.MAX_VALUE);
        this.pageMenuTabViewPager.setAdapter(this.pageMenuTabAdapter);
        PLVCommonNavigator pLVCommonNavigator = new PLVCommonNavigator(getContext());
        pLVCommonNavigator.setAdapter(new PLVCommonNavigatorAdapter() { // from class: com.easefun.polyv.livecloudclass.modules.pagemenu.PLVLCLivePageMenuLayout.1
            @Override // com.easefun.polyv.livecommon.ui.widget.magicindicator.buildins.commonnavigator.abs.PLVCommonNavigatorAdapter
            public int getCount() {
                return PLVLCLivePageMenuLayout.this.pageMenuTabAdapter.getSize();
            }

            @Override // com.easefun.polyv.livecommon.ui.widget.magicindicator.buildins.commonnavigator.abs.PLVCommonNavigatorAdapter
            public IPLVPagerIndicator getIndicator(Context context) {
                PLVLinePagerIndicator pLVLinePagerIndicator = new PLVLinePagerIndicator(context);
                pLVLinePagerIndicator.setMode(1);
                pLVLinePagerIndicator.setLineHeight(ConvertUtils.dp2px(2.0f));
                pLVLinePagerIndicator.setXOffset(0.0f);
                pLVLinePagerIndicator.setRoundRadius(ConvertUtils.dp2px(1.0f));
                pLVLinePagerIndicator.setColors(Integer.valueOf(Color.parseColor("#FFFFFF")));
                return pLVLinePagerIndicator;
            }

            @Override // com.easefun.polyv.livecommon.ui.widget.magicindicator.buildins.commonnavigator.abs.PLVCommonNavigatorAdapter
            public IPLVPagerTitleView getTitleView(Context context, final int i2) {
                if (PLVLCLivePageMenuLayout.this.pageMenuTabTitleList.isEmpty() || PLVLCLivePageMenuLayout.this.pageMenuTabTitleList.size() < i2 + 1) {
                    return null;
                }
                PLVColorTransitionPagerTitleView pLVColorTransitionPagerTitleView = new PLVColorTransitionPagerTitleView(context);
                pLVColorTransitionPagerTitleView.setPadding(ConvertUtils.dp2px(16.0f), 0, ConvertUtils.dp2px(16.0f), 0);
                pLVColorTransitionPagerTitleView.setNormalColor(Color.parseColor("#ADADC0"));
                pLVColorTransitionPagerTitleView.setSelectedColor(Color.parseColor("#FFFFFF"));
                pLVColorTransitionPagerTitleView.setText((CharSequence) PLVLCLivePageMenuLayout.this.pageMenuTabTitleList.get(i2));
                pLVColorTransitionPagerTitleView.setOnClickListener(new View.OnClickListener() { // from class: com.easefun.polyv.livecloudclass.modules.pagemenu.PLVLCLivePageMenuLayout.1.1
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view) throws Resources.NotFoundException {
                        PLVLCLivePageMenuLayout.this.pageMenuTabViewPager.setCurrentItem(i2);
                    }
                });
                return pLVColorTransitionPagerTitleView;
            }
        });
        this.pageMenuTabIndicator.setNavigator(pLVCommonNavigator);
        PLVViewPagerHelper.bind(this.pageMenuTabIndicator, this.pageMenuTabViewPager);
        PLVLCChatCommonMessageList pLVLCChatCommonMessageList = new PLVLCChatCommonMessageList(getContext());
        this.chatCommonMessageList = pLVLCChatCommonMessageList;
        restoreChatTabForMessageList(pLVLCChatCommonMessageList);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void observeChatroomData() {
        this.chatroomPresenter.getData().getLikesCountData().observe((LifecycleOwner) getContext(), new Observer<Long>() { // from class: com.easefun.polyv.livecloudclass.modules.pagemenu.PLVLCLivePageMenuLayout.8
            @Override // androidx.lifecycle.Observer
            public void onChanged(@Nullable Long l2) {
                if (l2 == null) {
                    return;
                }
                if (PLVLCLivePageMenuLayout.this.liveDescFragment != null) {
                    PLVLCLivePageMenuLayout.this.liveDescFragment.setLikesCount(l2.longValue());
                }
                if (PLVLCLivePageMenuLayout.this.chatFragment != null) {
                    PLVLCLivePageMenuLayout.this.chatFragment.setLikesCount(l2.longValue());
                }
            }
        });
        this.chatroomPresenter.getData().getSpeakMessageData().observe((LifecycleOwner) getContext(), new Observer<Pair<CharSequence, Boolean>>() { // from class: com.easefun.polyv.livecloudclass.modules.pagemenu.PLVLCLivePageMenuLayout.9
            @Override // androidx.lifecycle.Observer
            public void onChanged(@Nullable Pair<CharSequence, Boolean> pair) {
                if (pair == null) {
                    return;
                }
                CharSequence charSequence = (CharSequence) pair.first;
                boolean zBooleanValue = ((Boolean) pair.second).booleanValue();
                if (PLVLCLivePageMenuLayout.this.chatFragment == null || PLVLCLivePageMenuLayout.this.isChatPlaybackEnabled()) {
                    return;
                }
                if ((!PLVLCLivePageMenuLayout.this.chatFragment.isDisplaySpecialType() || (PLVLCLivePageMenuLayout.this.chatFragment.isDisplaySpecialType() && zBooleanValue)) && PLVLCLivePageMenuLayout.this.onViewActionListener != null) {
                    PLVLCLivePageMenuLayout.this.onViewActionListener.onSendDanmuAction(charSequence);
                }
            }
        });
        this.chatroomPresenter.getData().getFunctionSwitchData().observe((LifecycleOwner) getContext(), new Observer<List<PLVChatFunctionSwitchVO.DataBean>>() { // from class: com.easefun.polyv.livecloudclass.modules.pagemenu.PLVLCLivePageMenuLayout.10
            @Override // androidx.lifecycle.Observer
            public void onChanged(@Nullable List<PLVChatFunctionSwitchVO.DataBean> list) {
                if (PLVLCLivePageMenuLayout.this.chatroomPresenter != null) {
                    PLVLCLivePageMenuLayout.this.chatroomPresenter.getData().getFunctionSwitchData().removeObserver(this);
                }
                if (PLVLCLivePageMenuLayout.this.chatFragment != null) {
                    PLVLCLivePageMenuLayout.this.chatFragment.acceptFunctionSwitchData(list);
                }
            }
        });
        this.chatroomPresenter.getData().getEmotionImages().observe((LifecycleOwner) getContext(), new Observer<List<PLVEmotionImageVO.EmotionImage>>() { // from class: com.easefun.polyv.livecloudclass.modules.pagemenu.PLVLCLivePageMenuLayout.11
            @Override // androidx.lifecycle.Observer
            public void onChanged(@Nullable List<PLVEmotionImageVO.EmotionImage> list) {
                if (PLVLCLivePageMenuLayout.this.chatroomPresenter != null) {
                    PLVLCLivePageMenuLayout.this.chatroomPresenter.getData().getEmotionImages().removeObserver(this);
                }
                if (PLVLCLivePageMenuLayout.this.chatFragment != null) {
                    PLVLCLivePageMenuLayout.this.chatFragment.acceptEmotionImageData(list);
                }
            }
        });
    }

    private void observeClassDetailVO() {
        this.liveRoomDataManager.getClassDetailVO().observe((LifecycleOwner) getContext(), new Observer<PLVStatefulData<PolyvLiveClassDetailVO>>() { // from class: com.easefun.polyv.livecloudclass.modules.pagemenu.PLVLCLivePageMenuLayout.16
            @Override // androidx.lifecycle.Observer
            @SuppressLint({"SetTextI18n"})
            public void onChanged(@Nullable PLVStatefulData<PolyvLiveClassDetailVO> pLVStatefulData) throws Resources.NotFoundException {
                PolyvLiveClassDetailVO data;
                PLVLCLivePageMenuLayout.this.liveRoomDataManager.getClassDetailVO().removeObserver(this);
                if (pLVStatefulData == null || !pLVStatefulData.isSuccess() || (data = pLVStatefulData.getData()) == null || data.getData() == null) {
                    return;
                }
                PLVLCLivePageMenuLayout.this.chatPlaybackEnabled = data.getData().isChatPlaybackEnabled() && !PLVLCLivePageMenuLayout.this.liveRoomDataManager.getConfig().isLive();
                List<PLVLiveClassDetailVO.DataBean.ChannelMenusBean> channelMenus = data.getData().getChannelMenus();
                if (channelMenus != null) {
                    for (PLVLiveClassDetailVO.DataBean.ChannelMenusBean channelMenusBean : channelMenus) {
                        if (channelMenusBean != null) {
                            if ("desc".equals(channelMenusBean.getMenuType())) {
                                PLVLCLivePageMenuLayout.this.addDescTab(data, channelMenusBean);
                            } else if ("chat".equals(channelMenusBean.getMenuType())) {
                                PLVLCLivePageMenuLayout.this.addChatTab(channelMenusBean);
                            } else if (PLVLiveClassDetailVO.MENUTYPE_QUIZ.equals(channelMenusBean.getMenuType())) {
                                PLVLCLivePageMenuLayout.this.addQuizTab(channelMenusBean);
                            } else if ("text".equals(channelMenusBean.getMenuType())) {
                                PLVLCLivePageMenuLayout.this.addTextTab(channelMenusBean);
                            } else if (PLVLiveClassDetailVO.MENUTYPE_IFRAME.equals(channelMenusBean.getMenuType())) {
                                PLVLCLivePageMenuLayout.this.addIFrameTab(channelMenusBean);
                            } else if (PLVLiveClassDetailVO.MENUTYPE_TUWEN.equals(channelMenusBean.getMenuType())) {
                                PLVLCLivePageMenuLayout.this.addTuWenTab(channelMenusBean);
                            } else if (PLVLiveClassDetailVO.MENUTYPE_QA.equals(channelMenusBean.getMenuType())) {
                                PLVLCLivePageMenuLayout.this.addQATab(channelMenusBean);
                            } else if (PLVLiveClassDetailVO.MENUTYPE_PREVIOUS.equals(channelMenusBean.getMenuType()) && !PLVLCLivePageMenuLayout.this.liveRoomDataManager.getConfig().isLive() && PLVLCLivePageMenuLayout.this.liveRoomDataManager.getConfig().getVid().isEmpty()) {
                                PLVLCLivePageMenuLayout.this.addPreviousTab(channelMenusBean);
                            } else if (PLVLiveClassDetailVO.MENUTYPE_BUY.equals(channelMenusBean.getMenuType())) {
                                PLVLCLivePageMenuLayout.this.commodityViewModel.notifyHasProductLayout(true);
                                PLVLCLivePageMenuLayout.this.addBuyProductTab(channelMenusBean.getName());
                            }
                        }
                    }
                    PLVLCLivePageMenuLayout.this.refreshPageMenuTabAdapter();
                    PLVLCLivePageMenuLayout.this.observeChatroomData();
                    PLVLCLivePageMenuLayout.this.observePointRewardOpen();
                    PLVLCLivePageMenuLayout.this.checkStartChatPlayback();
                }
            }
        });
    }

    private void observeInteractData() {
        this.liveRoomDataManager.getInteractStatusData().observe((LifecycleOwner) getContext(), new Observer<PLVWebviewUpdateAppStatusVO>() { // from class: com.easefun.polyv.livecloudclass.modules.pagemenu.PLVLCLivePageMenuLayout.12
            @Override // androidx.lifecycle.Observer
            public void onChanged(@Nullable PLVWebviewUpdateAppStatusVO pLVWebviewUpdateAppStatusVO) {
                if (PLVLCLivePageMenuLayout.this.chatFragment == null || pLVWebviewUpdateAppStatusVO == null) {
                    return;
                }
                PLVLCLivePageMenuLayout.this.chatFragment.updateChatMoreFunction(pLVWebviewUpdateAppStatusVO);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void observePointRewardOpen() {
        this.liveRoomDataManager.getPointRewardEnableData().observe((LifecycleOwner) getContext(), new Observer<PLVStatefulData<Boolean>>() { // from class: com.easefun.polyv.livecloudclass.modules.pagemenu.PLVLCLivePageMenuLayout.17
            @Override // androidx.lifecycle.Observer
            public void onChanged(@Nullable PLVStatefulData<Boolean> pLVStatefulData) {
                PLVLCLivePageMenuLayout.this.liveRoomDataManager.getPointRewardEnableData().removeObserver(this);
                if (PLVLCLivePageMenuLayout.this.chatFragment == null || pLVStatefulData == null || pLVStatefulData.getData() == null) {
                    return;
                }
                PLVLCLivePageMenuLayout.this.chatFragment.setOpenPointReward(pLVStatefulData.getData().booleanValue());
            }
        });
    }

    private void observerChapters() {
        this.liveRoomDataManager.getPlaybackChannelData().observe((LifecycleOwner) getContext(), new Observer<PLVStatefulData<PLVPlaybackChannelDetailVO>>() { // from class: com.easefun.polyv.livecloudclass.modules.pagemenu.PLVLCLivePageMenuLayout.15
            @Override // androidx.lifecycle.Observer
            public void onChanged(@Nullable PLVStatefulData<PLVPlaybackChannelDetailVO> pLVStatefulData) throws Resources.NotFoundException {
                PLVPlaybackChannelDetailVO data;
                PLVLCLivePageMenuLayout.this.liveRoomDataManager.getPlaybackChannelData().removeObserver(this);
                if (pLVStatefulData == null || !pLVStatefulData.isSuccess() || (data = pLVStatefulData.getData()) == null || data.getData() == null || data.getData().getChannelPlayback() == null) {
                    return;
                }
                PLVPlaybackChannelDetailVO.DataBean.ChannelPlaybackBean channelPlayback = data.getData().getChannelPlayback();
                String sectionEnabled = channelPlayback.getSectionEnabled();
                String hasRecordFile = channelPlayback.getHasRecordFile();
                String hasPlaybackVideo = channelPlayback.getHasPlaybackVideo();
                if (PLVLCLivePageMenuLayout.this.liveRoomDataManager.getConfig().isLive() || !sectionEnabled.equals("Y")) {
                    return;
                }
                if (hasPlaybackVideo.equals("Y") || hasRecordFile.equals("Y")) {
                    PLVLCLivePageMenuLayout.this.addChapterTab();
                }
            }
        });
    }

    private void observerPreviousData() {
        IPLVPreviousPlaybackContract.IPreviousPlaybackPresenter iPreviousPlaybackPresenter = this.previousPlaybackPresenter;
        if (iPreviousPlaybackPresenter != null) {
            iPreviousPlaybackPresenter.getData().getPlaybackVideoVidData().observe((LifecycleOwner) getContext(), new Observer<String>() { // from class: com.easefun.polyv.livecloudclass.modules.pagemenu.PLVLCLivePageMenuLayout.13
                @Override // androidx.lifecycle.Observer
                public void onChanged(@Nullable String str) {
                    if (TextUtils.isEmpty(str)) {
                        return;
                    }
                    PLVLCLivePageMenuLayout.this.onViewActionListener.onChangeVideoVidAction(str);
                }
            });
            this.previousPlaybackPresenter.getData().getPlayBackVidoSeekData().observe((LifecycleOwner) getContext(), new Observer<Integer>() { // from class: com.easefun.polyv.livecloudclass.modules.pagemenu.PLVLCLivePageMenuLayout.14
                @Override // androidx.lifecycle.Observer
                public void onChanged(@Nullable Integer num) {
                    if (num == null) {
                        return;
                    }
                    PLVLCLivePageMenuLayout.this.onViewActionListener.onSeekToAction(num.intValue());
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void refreshPageMenuTabAdapter() throws Resources.NotFoundException {
        this.pageMenuTabAdapter.notifyDataSetChanged();
        this.pageMenuTabIndicator.getNavigator().notifyDataSetChanged();
        if (this.pageMenuTabViewPager.getCurrentItem() >= this.pageMenuTabFragmentList.size() && this.pageMenuTabAdapter.getSize() > 0) {
            this.pageMenuTabViewPager.setCurrentItem(0);
            this.pageMenuTabIndicator.onPageSelected(0);
        }
        if (this.pageMenuTabAdapter.getSize() > 0) {
            this.pageMenuTabIndicator.setBackgroundColor(Color.parseColor("#3E3E4E"));
            findViewById(R.id.split_view).setVisibility(0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean removeBuyProductTab() {
        PLVLCProductFragment pLVLCProductFragment = this.productFragment;
        if (pLVLCProductFragment == null) {
            return false;
        }
        this.pageMenuTabTitleList.remove(this.pageMenuTabFragmentList.indexOf(pLVLCProductFragment));
        this.pageMenuTabFragmentList.remove(this.productFragment);
        this.productFragment = null;
        return true;
    }

    private void restoreChatTabForMessageList(PLVLCChatCommonMessageList pLVLCChatCommonMessageList) {
        if (this.chatFragment == null) {
            this.chatFragment = (PLVLCChatFragment) tryGetRestoreFragment(PLVLCChatFragment.class);
        }
        PLVLCChatFragment pLVLCChatFragment = this.chatFragment;
        if (pLVLCChatFragment == null || pLVLCChatCommonMessageList == null) {
            return;
        }
        pLVLCChatFragment.init(pLVLCChatCommonMessageList);
    }

    private void restoreChatTabForPresenter(IPLVChatroomContract.IChatroomPresenter iChatroomPresenter) {
        if (this.chatFragment == null) {
            this.chatFragment = (PLVLCChatFragment) tryGetRestoreFragment(PLVLCChatFragment.class);
        }
        PLVLCChatFragment pLVLCChatFragment = this.chatFragment;
        if (pLVLCChatFragment == null) {
            return;
        }
        pLVLCChatFragment.setCardPushManager(this.cardPushManager);
        this.chatFragment.setIsChatPlaybackLayout(isChatPlaybackEnabled());
        IPLVChatPlaybackManager iPLVChatPlaybackManager = this.chatPlaybackManager;
        if (iPLVChatPlaybackManager != null) {
            iPLVChatPlaybackManager.addOnCallDataListener(this.chatFragment.getChatPlaybackDataListener());
        }
        if (iChatroomPresenter != null) {
            iChatroomPresenter.registerView(this.chatFragment.getChatroomView());
        }
    }

    private void restoreQuizTabForPresenter(IPLVChatroomContract.IChatroomPresenter iChatroomPresenter) {
        if (this.quizFragment == null) {
            this.quizFragment = (PLVLCQuizFragment) tryGetRestoreFragment(PLVLCQuizFragment.class);
        }
        PLVLCQuizFragment pLVLCQuizFragment = this.quizFragment;
        if (pLVLCQuizFragment == null || iChatroomPresenter == null) {
            return;
        }
        iChatroomPresenter.registerView(pLVLCQuizFragment.getChatroomView());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showExitDialog(int i2) {
        new AlertDialog.Builder(getContext()).setTitle("温馨提示").setMessage(i2).setPositiveButton("确定", new DialogInterface.OnClickListener() { // from class: com.easefun.polyv.livecloudclass.modules.pagemenu.PLVLCLivePageMenuLayout.7
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i3) {
                ((Activity) PLVLCLivePageMenuLayout.this.getContext()).finish();
            }
        }).setCancelable(false).show();
    }

    @Nullable
    private <T extends Fragment> T tryGetRestoreFragment(@NonNull Class<T> cls) {
        if (!(getContext() instanceof FragmentActivity)) {
            return null;
        }
        try {
            Iterator<Fragment> it = ((FragmentActivity) getContext()).getSupportFragmentManager().getFragments().iterator();
            while (it.hasNext()) {
                T t2 = (T) it.next();
                if (cls.equals(t2.getClass())) {
                    return t2;
                }
            }
            return null;
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    @Override // com.easefun.polyv.livecloudclass.modules.pagemenu.IPLVLCLivePageMenuLayout
    public void addOnViewerCountListener(IPLVOnDataChangedListener<Long> iPLVOnDataChangedListener) {
        this.chatroomPresenter.getData().getViewerCountData().observe((LifecycleOwner) getContext(), iPLVOnDataChangedListener);
    }

    @Override // com.easefun.polyv.livecloudclass.modules.pagemenu.IPLVLCLivePageMenuLayout
    public void destroy() {
        destroySocketLoginManager();
        PLVCardPushManager pLVCardPushManager = this.cardPushManager;
        if (pLVCardPushManager != null) {
            pLVCardPushManager.disposeCardPushAllTask();
        }
        IPLVChatroomContract.IChatroomPresenter iChatroomPresenter = this.chatroomPresenter;
        if (iChatroomPresenter != null) {
            iChatroomPresenter.destroy();
        }
        IPLVChatPlaybackManager iPLVChatPlaybackManager = this.chatPlaybackManager;
        if (iPLVChatPlaybackManager != null) {
            iPLVChatPlaybackManager.destroy();
        }
        PLVMyProgressManager.removeModuleListener(PLVLCMessageViewHolder.LOADIMG_MOUDLE_TAG);
    }

    @Override // com.easefun.polyv.livecloudclass.modules.pagemenu.IPLVLCLivePageMenuLayout
    public PLVCardPushManager getCardPushManager() {
        return this.cardPushManager;
    }

    @Override // com.easefun.polyv.livecloudclass.modules.pagemenu.IPLVLCLivePageMenuLayout
    public PLVLCChatCommonMessageList getChatCommonMessageList() {
        return this.chatCommonMessageList;
    }

    @Override // com.easefun.polyv.livecloudclass.modules.pagemenu.IPLVLCLivePageMenuLayout
    public IPLVChatPlaybackManager getChatPlaybackManager() {
        return this.chatPlaybackManager;
    }

    @Override // com.easefun.polyv.livecloudclass.modules.pagemenu.IPLVLCLivePageMenuLayout
    public IPLVChatroomContract.IChatroomPresenter getChatroomPresenter() {
        return this.chatroomPresenter;
    }

    @Override // com.easefun.polyv.livecloudclass.modules.pagemenu.IPLVLCLivePageMenuLayout
    public IPLVPreviousPlaybackContract.IPreviousPlaybackPresenter getPreviousPresenter() {
        return this.previousPlaybackPresenter;
    }

    @Override // com.easefun.polyv.livecloudclass.modules.pagemenu.IPLVLCLivePageMenuLayout
    public void init(IPLVLiveRoomDataManager iPLVLiveRoomDataManager) {
        this.liveRoomDataManager = iPLVLiveRoomDataManager;
        initChatPlaybackManager();
        PLVChatroomPresenter pLVChatroomPresenter = new PLVChatroomPresenter(iPLVLiveRoomDataManager);
        this.chatroomPresenter = pLVChatroomPresenter;
        pLVChatroomPresenter.init();
        this.chatroomPresenter.getChatEmotionImages();
        initChatroomMvpView(this.chatroomPresenter);
        restoreChatTabForPresenter(this.chatroomPresenter);
        restoreQuizTabForPresenter(this.chatroomPresenter);
        initSocketLoginManager();
        observeClassDetailVO();
        observePointRewardOpen();
        observeInteractData();
    }

    @Override // com.easefun.polyv.livecloudclass.modules.pagemenu.IPLVLCLivePageMenuLayout
    public boolean isChatPlaybackEnabled() {
        return this.chatPlaybackEnabled;
    }

    @Override // com.easefun.polyv.livecloudclass.modules.pagemenu.IPLVLCLivePageMenuLayout
    public boolean onBackPressed() {
        PLVLCChatCommonMessageList pLVLCChatCommonMessageList = this.chatCommonMessageList;
        if (pLVLCChatCommonMessageList != null && pLVLCChatCommonMessageList.onBackPressed()) {
            return true;
        }
        PLVViewPagerAdapter pLVViewPagerAdapter = this.pageMenuTabAdapter;
        if (pLVViewPagerAdapter == null || pLVViewPagerAdapter.getSize() <= 1) {
            return false;
        }
        Fragment item = this.pageMenuTabAdapter.getItem(this.pageMenuTabViewPager.getCurrentItem());
        if (item instanceof PLVLCIFrameFragment) {
            return ((PLVLCIFrameFragment) item).onBackPressed();
        }
        if (item instanceof PLVLCChatFragment) {
            return ((PLVLCChatFragment) item).onBackPressed();
        }
        if (item instanceof PLVLCQuizFragment) {
            return ((PLVLCQuizFragment) item).onBackPressed();
        }
        if (item instanceof PLVLCLiveDescFragment) {
            return ((PLVLCLiveDescFragment) item).onBackPressed();
        }
        if (item instanceof PLVLCTextFragment) {
            return ((PLVLCTextFragment) item).onBackPressed();
        }
        return false;
    }

    @Override // android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        if (configuration.orientation == 2) {
            setVisibility(8);
        } else {
            setVisibility(0);
        }
    }

    @Override // com.easefun.polyv.livecloudclass.modules.pagemenu.IPLVLCLivePageMenuLayout
    public void onPlaybackVideoPrepared(String str, String str2) {
        IPLVChatPlaybackManager iPLVChatPlaybackManager;
        List<Pair<String, String>> list = this.needAddedChatPlaybackTask;
        if (list != null) {
            list.add(new Pair<>(str, str2));
        }
        if (!isChatPlaybackEnabled() || (iPLVChatPlaybackManager = this.chatPlaybackManager) == null) {
            return;
        }
        iPLVChatPlaybackManager.start(str, str2);
    }

    @Override // com.easefun.polyv.livecloudclass.modules.pagemenu.IPLVLCLivePageMenuLayout
    public void onPlaybackVideoSeekComplete(int i2) {
        IPLVChatPlaybackManager iPLVChatPlaybackManager;
        if (!isChatPlaybackEnabled() || (iPLVChatPlaybackManager = this.chatPlaybackManager) == null) {
            return;
        }
        iPLVChatPlaybackManager.seek(i2);
    }

    @Override // com.easefun.polyv.livecloudclass.modules.pagemenu.IPLVLCLivePageMenuLayout
    public void setOnViewActionListener(IPLVLCLivePageMenuLayout.OnViewActionListener onViewActionListener) {
        this.onViewActionListener = onViewActionListener;
    }

    @Override // com.easefun.polyv.livecloudclass.modules.pagemenu.IPLVLCLivePageMenuLayout
    public void updateLiveStatus(PLVLiveStateEnum pLVLiveStateEnum) {
        PLVLCLiveDescFragment pLVLCLiveDescFragment = this.liveDescFragment;
        if (pLVLCLiveDescFragment != null) {
            pLVLCLiveDescFragment.updateLiveStatus(pLVLiveStateEnum);
        }
    }

    public PLVLCLivePageMenuLayout(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public PLVLCLivePageMenuLayout(@NonNull Context context, @Nullable AttributeSet attributeSet, int i2) throws Resources.NotFoundException {
        super(context, attributeSet, i2);
        this.commodityViewModel = (PLVCommodityViewModel) PLVDependManager.getInstance().get(PLVCommodityViewModel.class);
        this.cardPushManager = new PLVCardPushManager();
        this.needAddedChatPlaybackTask = new ArrayList();
        this.onSocketEventListener = new PLVAbsOnSocketEventListener() { // from class: com.easefun.polyv.livecloudclass.modules.pagemenu.PLVLCLivePageMenuLayout.6
            @Override // com.easefun.polyv.livecommon.module.modules.socket.PLVAbsOnSocketEventListener, com.easefun.polyv.livecommon.module.modules.socket.IPLVSocketLoginManager.OnSocketEventListener
            public void handleLoginFailed(@NonNull Throwable th) {
                super.handleLoginFailed(th);
                ToastUtils.showShort(PLVLCLivePageMenuLayout.this.getResources().getString(R.string.plv_chat_toast_login_failed) + ":" + th.getMessage());
            }

            @Override // com.easefun.polyv.livecommon.module.modules.socket.PLVAbsOnSocketEventListener, com.easefun.polyv.livecommon.module.modules.socket.IPLVSocketLoginManager.OnSocketEventListener
            public void handleLoginIng(boolean z2) {
                super.handleLoginIng(z2);
                if (z2) {
                    ToastUtils.showShort(R.string.plv_chat_toast_reconnecting);
                } else {
                    ToastUtils.showShort(R.string.plv_chat_toast_logging);
                }
            }

            @Override // com.easefun.polyv.livecommon.module.modules.socket.PLVAbsOnSocketEventListener, com.easefun.polyv.livecommon.module.modules.socket.IPLVSocketLoginManager.OnSocketEventListener
            public void handleLoginSuccess(boolean z2) {
                super.handleLoginSuccess(z2);
                if (z2) {
                    ToastUtils.showShort(R.string.plv_chat_toast_reconnect_success);
                } else {
                    ToastUtils.showShort(R.string.plv_chat_toast_login_success);
                }
            }

            @Override // com.easefun.polyv.livecommon.module.modules.socket.PLVAbsOnSocketEventListener, com.easefun.polyv.livecommon.module.modules.socket.IPLVSocketLoginManager.OnSocketEventListener
            public void onKickEvent(@NonNull PLVKickEvent pLVKickEvent, boolean z2) {
                super.onKickEvent(pLVKickEvent, z2);
                if (z2) {
                    PLVToast.Builder.context(Utils.getApp()).shortDuration().setText(R.string.plv_chat_toast_been_kicked).build().show();
                    ((Activity) PLVLCLivePageMenuLayout.this.getContext()).finish();
                }
            }

            @Override // com.easefun.polyv.livecommon.module.modules.socket.PLVAbsOnSocketEventListener, com.easefun.polyv.livecommon.module.modules.socket.IPLVSocketLoginManager.OnSocketEventListener
            public void onLoginRefuseEvent(@NonNull PLVLoginRefuseEvent pLVLoginRefuseEvent) {
                super.onLoginRefuseEvent(pLVLoginRefuseEvent);
                PLVLCLivePageMenuLayout.this.showExitDialog(R.string.plv_chat_toast_been_kicked);
            }

            @Override // com.easefun.polyv.livecommon.module.modules.socket.PLVAbsOnSocketEventListener, com.easefun.polyv.livecommon.module.modules.socket.IPLVSocketLoginManager.OnSocketEventListener
            public void onReloginEvent(@NonNull PLVReloginEvent pLVReloginEvent) {
                super.onReloginEvent(pLVReloginEvent);
                PLVToast.Builder.context(Utils.getApp()).shortDuration().setText(R.string.plv_chat_toast_account_login_elsewhere).build().show();
                PLVLCLivePageMenuLayout.this.postDelayed(new Runnable() { // from class: com.easefun.polyv.livecloudclass.modules.pagemenu.PLVLCLivePageMenuLayout.6.1
                    @Override // java.lang.Runnable
                    public void run() {
                        if (((Activity) PLVLCLivePageMenuLayout.this.getContext()) != null) {
                            ((Activity) PLVLCLivePageMenuLayout.this.getContext()).finish();
                        }
                    }
                }, C.DEFAULT_MAX_SEEK_TO_PREVIOUS_POSITION_MS);
            }
        };
        initView();
    }
}
