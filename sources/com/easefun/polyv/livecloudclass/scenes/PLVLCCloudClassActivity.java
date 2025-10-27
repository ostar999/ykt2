package com.easefun.polyv.livecloudclass.scenes;

import android.animation.Animator;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;
import cn.hutool.core.text.StrPool;
import com.easefun.polyv.livecloudclass.R;
import com.easefun.polyv.livecloudclass.modules.VideoHandout;
import com.easefun.polyv.livecloudclass.modules.chatroom.chatlandscape.PLVLCChatLandscapeLayout;
import com.easefun.polyv.livecloudclass.modules.chatroom.widget.DragLinearLayout;
import com.easefun.polyv.livecloudclass.modules.linkmic.IPLVLCLinkMicLayout;
import com.easefun.polyv.livecloudclass.modules.linkmic.PLVLCLinkMicControlBar;
import com.easefun.polyv.livecloudclass.modules.media.IPLVLCMediaLayout;
import com.easefun.polyv.livecloudclass.modules.media.controller.PLVLCLiveLandscapeChannelController;
import com.easefun.polyv.livecloudclass.modules.media.floating.PLVLCFloatingWindowModule;
import com.easefun.polyv.livecloudclass.modules.pagemenu.IPLVLCLivePageMenuLayout;
import com.easefun.polyv.livecloudclass.modules.ppt.IPLVLCFloatingPPTLayout;
import com.easefun.polyv.livecloudclass.modules.ppt.IPLVLCPPTView;
import com.easefun.polyv.livecommon.module.config.PLVLiveChannelConfigFiller;
import com.easefun.polyv.livecommon.module.config.PLVLiveScene;
import com.easefun.polyv.livecommon.module.data.IPLVLiveRoomDataManager;
import com.easefun.polyv.livecommon.module.data.PLVLiveRoomDataManager;
import com.easefun.polyv.livecommon.module.data.PLVStatefulData;
import com.easefun.polyv.livecommon.module.modules.commodity.di.PLVCommodityModule;
import com.easefun.polyv.livecommon.module.modules.interact.PLVInteractLayout2;
import com.easefun.polyv.livecommon.module.modules.interact.cardpush.PLVCardPushManager;
import com.easefun.polyv.livecommon.module.modules.player.PLVPlayerState;
import com.easefun.polyv.livecommon.module.modules.player.floating.PLVFloatingPlayerManager;
import com.easefun.polyv.livecommon.module.modules.player.live.enums.PLVLiveStateEnum;
import com.easefun.polyv.livecommon.module.modules.player.playback.di.PLVPlaybackCacheModule;
import com.easefun.polyv.livecommon.module.modules.player.playback.model.datasource.database.config.PLVPlaybackCacheConfig;
import com.easefun.polyv.livecommon.module.modules.player.playback.prsenter.config.PLVPlaybackCacheVideoConfig;
import com.easefun.polyv.livecommon.module.modules.player.playback.prsenter.data.PLVPlayInfoVO;
import com.easefun.polyv.livecommon.module.modules.popover.IPLVPopoverLayout;
import com.easefun.polyv.livecommon.module.modules.reward.OnPointRewardListener;
import com.easefun.polyv.livecommon.module.utils.PLVDialogFactory;
import com.easefun.polyv.livecommon.module.utils.PLVViewSwitcher;
import com.easefun.polyv.livecommon.module.utils.listener.IPLVOnDataChangedListener;
import com.easefun.polyv.livecommon.module.utils.result.PLVLaunchResult;
import com.easefun.polyv.livecommon.module.utils.rotaion.PLVOrientationManager;
import com.easefun.polyv.livecommon.ui.widget.PLVPlayerLogoView;
import com.easefun.polyv.livecommon.ui.widget.PLVSwitchViewAnchorLayout;
import com.easefun.polyv.livecommon.ui.window.PLVBaseActivity;
import com.easefun.polyv.livescenes.chatroom.PolyvLocalMessage;
import com.easefun.polyv.livescenes.model.PolyvLiveClassDetailVO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.plv.foundationsdk.component.di.PLVDependManager;
import com.plv.foundationsdk.utils.PLVScreenUtils;
import com.plv.foundationsdk.utils.PLVSugarUtil;
import com.plv.livescenes.config.PLVLiveChannelType;
import com.plv.livescenes.document.model.PLVPPTStatus;
import com.plv.livescenes.linkmic.manager.PLVLinkMicConfig;
import com.plv.livescenes.playback.video.PLVPlaybackListType;
import com.plv.livescenes.video.api.IPLVLiveListenerEvent;
import com.plv.socket.event.interact.PLVShowPushCardEvent;
import com.plv.thirdpart.blankj.utilcode.util.ConvertUtils;
import com.plv.thirdpart.blankj.utilcode.util.ScreenUtils;
import com.plv.thirdpart.blankj.utilcode.util.SizeUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.SdkConstant;
import com.yikaobang.yixue.BuildConfig;
import java.io.File;
import java.util.List;

/* loaded from: classes3.dex */
public class PLVLCCloudClassActivity extends PLVBaseActivity {
    private static final String EXTRA_CHANNEL_ID = "channelId";
    private static final String EXTRA_CHANNEL_TYPE = "channel_type";
    private static final String EXTRA_HANDOUT_LIST = "handout_list";
    private static final String EXTRA_IS_LIVE = "is_live";
    private static final String EXTRA_TEMP_STORE_FILE_ID = "temp_store_file_id";
    private static final String EXTRA_VID = "vid";
    private static final String EXTRA_VIDEO_LIST_TYPE = "video_list_type";
    private static final String EXTRA_VIEWER_AVATAR = "viewerAvatar";
    private static final String EXTRA_VIEWER_ID = "viewerId";
    private static final String EXTRA_VIEWER_NAME = "viewerName";
    private static final String TAG = "PLVLCCloudClassActivity";
    private PLVLCChatLandscapeLayout chatLandscapeLayout;
    private IPLVLCFloatingPPTLayout floatingPPTLayout;
    private String handoutList;
    private boolean hasHandout;

    @Nullable
    private IPLVLCLinkMicLayout linkMicLayout;
    private IPLVLCLivePageMenuLayout livePageMenuLayout;
    private IPLVLiveRoomDataManager liveRoomDataManager;
    private IPLVLCMediaLayout mediaLayout;
    private PLVPlayerLogoView plvPlayerLogoView;
    private IPLVPopoverLayout popoverLayout;
    private boolean showHandout;
    private int[] handoutLocation = new int[2];
    private PLVViewSwitcher pptViewSwitcher = new PLVViewSwitcher();

    /* renamed from: com.easefun.polyv.livecloudclass.scenes.PLVLCCloudClassActivity$27, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass27 {
        static final /* synthetic */ int[] $SwitchMap$com$easefun$polyv$livecommon$module$modules$player$PLVPlayerState;

        static {
            int[] iArr = new int[PLVPlayerState.values().length];
            $SwitchMap$com$easefun$polyv$livecommon$module$modules$player$PLVPlayerState = iArr;
            try {
                iArr[PLVPlayerState.PREPARED.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$easefun$polyv$livecommon$module$modules$player$PLVPlayerState[PLVPlayerState.LIVE_STOP.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$easefun$polyv$livecommon$module$modules$player$PLVPlayerState[PLVPlayerState.NO_LIVE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$easefun$polyv$livecommon$module$modules$player$PLVPlayerState[PLVPlayerState.LIVE_END.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$easefun$polyv$livecommon$module$modules$player$PLVPlayerState[PLVPlayerState.IDLE.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    public static class HandoutViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv_type;
        private TextView tv_author;
        private TextView tv_file_size;
        private TextView tv_title;

        public HandoutViewHolder(@NonNull View view) {
            super(view);
            this.iv_type = (ImageView) view.findViewById(R.id.iv_type);
            this.tv_title = (TextView) view.findViewById(R.id.tv_title);
            this.tv_author = (TextView) view.findViewById(R.id.tv_author);
            this.tv_file_size = (TextView) view.findViewById(R.id.tv_file_size);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:4:0x0022  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int getIcon(java.lang.String r4) {
        /*
            r3 = this;
            java.lang.String r4 = r4.toUpperCase()
            java.lang.String r0 = "."
            r4.indexOf(r0)
            int r0 = r4.indexOf(r0)
            r1 = 1
            int r0 = r0 + r1
            int r2 = r4.length()
            java.lang.String r4 = r4.substring(r0, r2)
            r4.hashCode()
            int r0 = r4.hashCode()
            r2 = -1
            switch(r0) {
                case 67864: goto L89;
                case 79058: goto L80;
                case 79444: goto L75;
                case 80899: goto L6a;
                case 83536: goto L5f;
                case 87007: goto L54;
                case 88833: goto L49;
                case 2103872: goto L3e;
                case 2462852: goto L32;
                case 2697305: goto L25;
                default: goto L22;
            }
        L22:
            r1 = r2
            goto L93
        L25:
            java.lang.String r0 = "XLSX"
            boolean r4 = r4.equals(r0)
            if (r4 != 0) goto L2e
            goto L22
        L2e:
            r1 = 9
            goto L93
        L32:
            java.lang.String r0 = "PPTX"
            boolean r4 = r4.equals(r0)
            if (r4 != 0) goto L3b
            goto L22
        L3b:
            r1 = 8
            goto L93
        L3e:
            java.lang.String r0 = "DOCX"
            boolean r4 = r4.equals(r0)
            if (r4 != 0) goto L47
            goto L22
        L47:
            r1 = 7
            goto L93
        L49:
            java.lang.String r0 = "ZIP"
            boolean r4 = r4.equals(r0)
            if (r4 != 0) goto L52
            goto L22
        L52:
            r1 = 6
            goto L93
        L54:
            java.lang.String r0 = "XLS"
            boolean r4 = r4.equals(r0)
            if (r4 != 0) goto L5d
            goto L22
        L5d:
            r1 = 5
            goto L93
        L5f:
            java.lang.String r0 = "TXT"
            boolean r4 = r4.equals(r0)
            if (r4 != 0) goto L68
            goto L22
        L68:
            r1 = 4
            goto L93
        L6a:
            java.lang.String r0 = "RAR"
            boolean r4 = r4.equals(r0)
            if (r4 != 0) goto L73
            goto L22
        L73:
            r1 = 3
            goto L93
        L75:
            java.lang.String r0 = "PPT"
            boolean r4 = r4.equals(r0)
            if (r4 != 0) goto L7e
            goto L22
        L7e:
            r1 = 2
            goto L93
        L80:
            java.lang.String r0 = "PDF"
            boolean r4 = r4.equals(r0)
            if (r4 != 0) goto L93
            goto L22
        L89:
            java.lang.String r0 = "DOC"
            boolean r4 = r4.equals(r0)
            if (r4 != 0) goto L92
            goto L22
        L92:
            r1 = 0
        L93:
            switch(r1) {
                case 0: goto La8;
                case 1: goto La5;
                case 2: goto La2;
                case 3: goto L9f;
                case 4: goto L9c;
                case 5: goto L99;
                case 6: goto L9f;
                case 7: goto La8;
                case 8: goto La2;
                case 9: goto L99;
                default: goto L96;
            }
        L96:
            int r4 = com.easefun.polyv.livecloudclass.R.drawable.ic_directory
            return r4
        L99:
            int r4 = com.easefun.polyv.livecloudclass.R.drawable.ic_excel
            return r4
        L9c:
            int r4 = com.easefun.polyv.livecloudclass.R.drawable.ic_txt
            return r4
        L9f:
            int r4 = com.easefun.polyv.livecloudclass.R.drawable.ic_zip_rar
            return r4
        La2:
            int r4 = com.easefun.polyv.livecloudclass.R.drawable.ic_ppt
            return r4
        La5:
            int r4 = com.easefun.polyv.livecloudclass.R.drawable.ic_pdf
            return r4
        La8:
            int r4 = com.easefun.polyv.livecloudclass.R.drawable.ic_word
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.easefun.polyv.livecloudclass.scenes.PLVLCCloudClassActivity.getIcon(java.lang.String):int");
    }

    private void initLiveRoomManager() {
        PLVLiveRoomDataManager pLVLiveRoomDataManager = new PLVLiveRoomDataManager(PLVLiveChannelConfigFiller.generateNewChannelConfig());
        this.liveRoomDataManager = pLVLiveRoomDataManager;
        pLVLiveRoomDataManager.requestPageViewer();
        this.liveRoomDataManager.requestChannelDetail();
        this.liveRoomDataManager.requestChannelSwitch();
    }

    private void initParams() {
        Intent intent = getIntent();
        boolean booleanExtra = intent.getBooleanExtra(EXTRA_IS_LIVE, true);
        PLVLiveChannelType pLVLiveChannelType = (PLVLiveChannelType) intent.getSerializableExtra(EXTRA_CHANNEL_TYPE);
        String stringExtra = intent.getStringExtra("channelId");
        String stringExtra2 = intent.getStringExtra("viewerId");
        String stringExtra3 = intent.getStringExtra(EXTRA_VIEWER_NAME);
        String stringExtra4 = intent.getStringExtra(EXTRA_VIEWER_AVATAR);
        String strFirstNotEmpty = PLVSugarUtil.firstNotEmpty(intent.getStringExtra("vid"), intent.getStringExtra(EXTRA_TEMP_STORE_FILE_ID));
        PLVPlaybackListType pLVPlaybackListType = (PLVPlaybackListType) intent.getSerializableExtra(EXTRA_VIDEO_LIST_TYPE);
        this.handoutList = intent.getStringExtra(EXTRA_HANDOUT_LIST);
        PLVLiveChannelConfigFiller.setIsLive(booleanExtra);
        PLVLiveChannelConfigFiller.setChannelType(pLVLiveChannelType);
        PLVLiveChannelConfigFiller.setupUser(stringExtra2, stringExtra3, stringExtra4, PLVLinkMicConfig.getInstance().getLiveChannelTypeNew() == PLVLiveChannelType.PPT ? "slice" : "student");
        PLVLiveChannelConfigFiller.setupChannelId(stringExtra);
        PLVFloatingPlayerManager.getInstance().saveIntent(intent);
        if (booleanExtra) {
            PLVFloatingPlayerManager.getInstance().setTag(stringExtra + "_live");
        } else {
            PLVLiveChannelConfigFiller.setupVid(strFirstNotEmpty != null ? strFirstNotEmpty : "");
            PLVLiveChannelConfigFiller.setupVideoListType(pLVPlaybackListType != null ? pLVPlaybackListType : PLVPlaybackListType.PLAYBACK);
            PLVFloatingPlayerManager pLVFloatingPlayerManager = PLVFloatingPlayerManager.getInstance();
            StringBuilder sb = new StringBuilder();
            sb.append(stringExtra);
            sb.append(StrPool.UNDERLINE);
            sb.append(strFirstNotEmpty == null ? "playback" : strFirstNotEmpty);
            pLVFloatingPlayerManager.setTag(sb.toString());
        }
        initPlaybackParam(strFirstNotEmpty, stringExtra, stringExtra2, stringExtra3, stringExtra4, pLVLiveChannelType, pLVPlaybackListType);
    }

    private void initPlaybackParam(String str, String str2, String str3, String str4, String str5, PLVLiveChannelType pLVLiveChannelType, PLVPlaybackListType pLVPlaybackListType) {
        ((PLVPlaybackCacheConfig) PLVDependManager.getInstance().get(PLVPlaybackCacheConfig.class)).setApplicationContext(getApplicationContext()).setDatabaseNameByViewerId(str3).setDownloadRootDirectory(new File(PLVPlaybackCacheConfig.defaultPlaybackCacheDownloadDirectory(this)));
        ((PLVPlaybackCacheVideoConfig) PLVDependManager.getInstance().get(PLVPlaybackCacheVideoConfig.class)).setVid(str).setVideoPoolIdByVid(str).setChannelId(str2).setViewerId(str3).setViewerName(str4).setViewerAvatar(str5).setChannelType(pLVLiveChannelType).setPlaybackListType(pLVPlaybackListType);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initPptTurnPageLandLayout() {
        if (this.floatingPPTLayout.isPPTInFloatingLayout()) {
            this.mediaLayout.onTurnPageLayoutChange(false);
        } else {
            this.mediaLayout.onTurnPageLayoutChange(true);
        }
    }

    private void initView() {
        ViewStub viewStub = (ViewStub) findViewById(R.id.plvlc_video_viewstub);
        DragLinearLayout dragLinearLayout = (DragLinearLayout) findViewById(R.id.ll_handout);
        String packageName = getPackageName();
        final LinearLayout linearLayout = (LinearLayout) findViewById(R.id.ll_root_handout);
        findViewById(R.id.iv_close_handout).setOnClickListener(new View.OnClickListener() { // from class: com.easefun.polyv.livecloudclass.scenes.PLVLCCloudClassActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                PLVLCCloudClassActivity.this.showHideHandout(linearLayout, false);
            }
        });
        final boolean z2 = getSharedPreferences(BuildConfig.APPLICATION_ID.equals(packageName) ? SdkConstant.UMENG_ALIS : "hukaobang", 0).getInt(CommonParameter.SkinMananer, 0) == 1;
        if (!TextUtils.isEmpty(this.handoutList)) {
            this.hasHandout = true;
            try {
                final List list = (List) new Gson().fromJson(this.handoutList, new TypeToken<List<VideoHandout>>() { // from class: com.easefun.polyv.livecloudclass.scenes.PLVLCCloudClassActivity.4
                }.getType());
                if (list == null || list.size() <= 0) {
                    dragLinearLayout.setVisibility(8);
                } else {
                    if (z2) {
                        dragLinearLayout.setBackground(ContextCompat.getDrawable(this, R.drawable.ic_video_play_handout_night_live));
                        linearLayout.setBackground(getDrawable(R.drawable.bg_round_corner_top_radius_16_deep));
                    }
                    dragLinearLayout.setVisibility(0);
                    ((RecyclerView) findViewById(R.id.rvList)).setAdapter(new RecyclerView.Adapter<HandoutViewHolder>() { // from class: com.easefun.polyv.livecloudclass.scenes.PLVLCCloudClassActivity.5
                        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
                        public int getItemCount() {
                            return list.size();
                        }

                        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
                        public void onBindViewHolder(@NonNull HandoutViewHolder handoutViewHolder, int i2) {
                            final VideoHandout videoHandout = (VideoHandout) list.get(i2);
                            handoutViewHolder.tv_title.setText(videoHandout.getTitle());
                            if (z2) {
                                handoutViewHolder.tv_title.setTextColor(Color.parseColor("#7380A9"));
                                handoutViewHolder.tv_file_size.setTextColor(Color.parseColor("#575F79"));
                                handoutViewHolder.tv_author.setTextColor(Color.parseColor("#575F79"));
                            }
                            handoutViewHolder.tv_file_size.setText(videoHandout.getSize_info());
                            handoutViewHolder.tv_author.setText(videoHandout.getAuthor());
                            handoutViewHolder.tv_author.setVisibility(!TextUtils.isEmpty(videoHandout.getAuthor()) ? 0 : 8);
                            handoutViewHolder.iv_type.setImageResource(PLVLCCloudClassActivity.this.getIcon(videoHandout.getSuffix()));
                            handoutViewHolder.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.easefun.polyv.livecloudclass.scenes.PLVLCCloudClassActivity.5.1
                                @Override // android.view.View.OnClickListener
                                public void onClick(View view) {
                                    Intent intent = new Intent();
                                    intent.setAction("VIEW_HANDOUT");
                                    intent.putExtra("data", new Gson().toJson(videoHandout));
                                    LocalBroadcastManager.getInstance(PLVLCCloudClassActivity.this).sendBroadcast(intent);
                                }
                            });
                        }

                        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
                        @NonNull
                        public HandoutViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i2) {
                            return new HandoutViewHolder(PLVLCCloudClassActivity.this.getLayoutInflater().inflate(R.layout.item_video_handout_1, (ViewGroup) null));
                        }
                    });
                    dragLinearLayout.setOnClickListener(new View.OnClickListener() { // from class: com.easefun.polyv.livecloudclass.scenes.PLVLCCloudClassActivity.6
                        @Override // android.view.View.OnClickListener
                        public void onClick(View view) {
                            PLVLCCloudClassActivity.this.showHideHandout(linearLayout, true);
                        }
                    });
                }
            } catch (Exception unused) {
                dragLinearLayout.setVisibility(8);
            }
        }
        this.floatingPPTLayout = (IPLVLCFloatingPPTLayout) findViewById(R.id.plvlc_ppt_floating_ppt_layout);
        if (this.liveRoomDataManager.getConfig().isLive()) {
            PLVLCLiveLandscapeChannelController pLVLCLiveLandscapeChannelController = (PLVLCLiveLandscapeChannelController) ((ViewStub) findViewById(R.id.plvlc_ppt_landscape_channel_controller)).inflate();
            viewStub.setLayoutResource(R.layout.plvlc_live_media_layout_view_stub);
            IPLVLCMediaLayout iPLVLCMediaLayout = (IPLVLCMediaLayout) viewStub.inflate();
            this.mediaLayout = iPLVLCMediaLayout;
            iPLVLCMediaLayout.init(this.liveRoomDataManager);
            this.mediaLayout.setLandscapeControllerView(pLVLCLiveLandscapeChannelController);
            this.mediaLayout.startPlay();
            PLVLCLinkMicControlBar pLVLCLinkMicControlBar = (PLVLCLinkMicControlBar) ((ViewStub) findViewById(R.id.plvlc_ppt_linkmic_controller)).inflate();
            IPLVLCLinkMicLayout iPLVLCLinkMicLayout = (IPLVLCLinkMicLayout) ((ViewStub) findViewById(R.id.plvlc_linkmic_viewstub)).inflate();
            this.linkMicLayout = iPLVLCLinkMicLayout;
            iPLVLCLinkMicLayout.init(this.liveRoomDataManager, pLVLCLinkMicControlBar);
            this.linkMicLayout.hideAll();
        } else {
            viewStub.setLayoutResource(R.layout.plvlc_playback_media_layout_view_stub);
            IPLVLCMediaLayout iPLVLCMediaLayout2 = (IPLVLCMediaLayout) viewStub.inflate();
            this.mediaLayout = iPLVLCMediaLayout2;
            iPLVLCMediaLayout2.init(this.liveRoomDataManager);
            this.mediaLayout.setPPTView(this.floatingPPTLayout.getPPTView().getPlaybackPPTViewToBindInPlayer());
            this.mediaLayout.startPlay();
        }
        IPLVPopoverLayout iPLVPopoverLayout = (IPLVPopoverLayout) ((ViewStub) findViewById(R.id.plvlc_popover_layout)).inflate();
        this.popoverLayout = iPLVPopoverLayout;
        iPLVPopoverLayout.init(PLVLiveScene.CLOUDCLASS, this.liveRoomDataManager);
        this.popoverLayout.setOnPointRewardListener(new OnPointRewardListener() { // from class: com.easefun.polyv.livecloudclass.scenes.PLVLCCloudClassActivity.7
            @Override // com.easefun.polyv.livecommon.module.modules.reward.OnPointRewardListener
            public void pointRewardEnable(boolean z3) {
                PLVLCCloudClassActivity.this.liveRoomDataManager.getPointRewardEnableData().postValue(PLVStatefulData.success(Boolean.valueOf(z3)));
            }
        });
        this.popoverLayout.setOnOpenInsideWebViewListener(new PLVInteractLayout2.OnOpenInsideWebViewListener() { // from class: com.easefun.polyv.livecloudclass.scenes.PLVLCCloudClassActivity.8
            boolean needShowControllerOnClosed = false;

            @Override // com.easefun.polyv.livecommon.module.modules.interact.PLVInteractLayout2.OnOpenInsideWebViewListener
            public void onClosed() {
                if (this.needShowControllerOnClosed) {
                    PLVLCCloudClassActivity.this.mediaLayout.showController();
                    this.needShowControllerOnClosed = false;
                }
            }

            @Override // com.easefun.polyv.livecommon.module.modules.interact.PLVInteractLayout2.OnOpenInsideWebViewListener
            public PLVInteractLayout2.OpenUrlParam onOpenWithParam(boolean z3) {
                if (z3) {
                    boolean zHideController = this.needShowControllerOnClosed;
                    if (!zHideController) {
                        zHideController = PLVLCCloudClassActivity.this.mediaLayout.hideController();
                    }
                    this.needShowControllerOnClosed = zHideController;
                }
                return new PLVInteractLayout2.OpenUrlParam(((View) PLVLCCloudClassActivity.this.mediaLayout).getHeight() + ConvertUtils.dp2px(48.0f), (ViewGroup) PLVLCCloudClassActivity.this.findViewById(R.id.plvlc_popup_container));
            }
        });
        IPLVLCLivePageMenuLayout iPLVLCLivePageMenuLayout = (IPLVLCLivePageMenuLayout) findViewById(R.id.plvlc_live_page_menu_layout);
        this.livePageMenuLayout = iPLVLCLivePageMenuLayout;
        iPLVLCLivePageMenuLayout.init(this.liveRoomDataManager);
        this.livePageMenuLayout.getCardPushManager().registerView(this.mediaLayout.getCardEnterView(), this.mediaLayout.getCardEnterCdView(), this.mediaLayout.getCardEnterTipsView());
        this.livePageMenuLayout.getCardPushManager().setOnCardEnterClickListener(new PLVCardPushManager.OnCardEnterClickListener() { // from class: com.easefun.polyv.livecloudclass.scenes.PLVLCCloudClassActivity.9
            @Override // com.easefun.polyv.livecommon.module.modules.interact.cardpush.PLVCardPushManager.OnCardEnterClickListener
            public void onClick(PLVShowPushCardEvent pLVShowPushCardEvent) {
                if (PLVLCCloudClassActivity.this.popoverLayout != null) {
                    PLVLCCloudClassActivity.this.popoverLayout.getInteractLayout().showCardPush(pLVShowPushCardEvent);
                }
            }
        });
        PLVLCChatLandscapeLayout chatLandscapeLayout = this.mediaLayout.getChatLandscapeLayout();
        this.chatLandscapeLayout = chatLandscapeLayout;
        chatLandscapeLayout.init(this.livePageMenuLayout.getChatCommonMessageList());
        this.pptViewSwitcher.registerSwitchView(this.floatingPPTLayout.getPPTSwitchView(), this.mediaLayout.getPlayerSwitchView());
        if (ScreenUtils.isPortrait()) {
            PLVScreenUtils.enterPortrait(this);
        } else {
            PLVScreenUtils.enterLandscape(this);
        }
        this.plvPlayerLogoView = this.mediaLayout.getLogoView();
    }

    private void injectDependency() {
        PLVDependManager.getInstance().switchStore(this).addModule(PLVPlaybackCacheModule.instance).addModule(PLVCommodityModule.instance).addModule(PLVLCFloatingWindowModule.instance);
    }

    @NonNull
    public static PLVLaunchResult launchLive(@NonNull Activity activity, @NonNull String str, @NonNull PLVLiveChannelType pLVLiveChannelType, @NonNull String str2, @NonNull String str3, @NonNull String str4) {
        if (activity == null) {
            return PLVLaunchResult.error("activity 为空，启动云课堂直播页失败！");
        }
        if (TextUtils.isEmpty(str)) {
            return PLVLaunchResult.error("channelId 为空，启动云课堂直播页失败！");
        }
        if (pLVLiveChannelType == null) {
            return PLVLaunchResult.error("channelType 为空，启动云课堂直播页失败！");
        }
        if (TextUtils.isEmpty(str2)) {
            return PLVLaunchResult.error("viewerId 为空，启动云课堂直播页失败！");
        }
        if (TextUtils.isEmpty(str3)) {
            return PLVLaunchResult.error("viewerName 为空，启动云课堂直播页失败！");
        }
        Intent intent = new Intent(activity, (Class<?>) PLVLCCloudClassActivity.class);
        intent.putExtra("channelId", str);
        intent.putExtra(EXTRA_CHANNEL_TYPE, pLVLiveChannelType);
        intent.putExtra("viewerId", str2);
        intent.putExtra(EXTRA_VIEWER_NAME, str3);
        intent.putExtra(EXTRA_VIEWER_AVATAR, str4);
        intent.putExtra(EXTRA_IS_LIVE, true);
        intent.addFlags(268435456);
        activity.startActivity(intent);
        return PLVLaunchResult.success();
    }

    @NonNull
    public static PLVLaunchResult launchPlayback(@NonNull Activity activity, @NonNull String str, @NonNull PLVLiveChannelType pLVLiveChannelType, @Nullable String str2, @Nullable String str3, @NonNull String str4, @NonNull String str5, @NonNull String str6, PLVPlaybackListType pLVPlaybackListType) {
        if (activity == null) {
            return PLVLaunchResult.error("activity 为空，启动云课堂回放页失败！");
        }
        if (TextUtils.isEmpty(str)) {
            return PLVLaunchResult.error("channelId 为空，启动云课堂回放页失败！");
        }
        if (pLVLiveChannelType == null) {
            return PLVLaunchResult.error("channelType 为空，启动云课堂回放页失败！");
        }
        if (TextUtils.isEmpty(str4)) {
            return PLVLaunchResult.error("viewerId 为空，启动云课堂回放页失败！");
        }
        if (TextUtils.isEmpty(str5)) {
            return PLVLaunchResult.error("viewerName 为空，启动云课堂回放页失败！");
        }
        Intent intent = new Intent(activity, (Class<?>) PLVLCCloudClassActivity.class);
        intent.putExtra("channelId", str);
        intent.putExtra(EXTRA_CHANNEL_TYPE, pLVLiveChannelType);
        intent.putExtra("viewerId", str4);
        intent.putExtra(EXTRA_VIEWER_NAME, str5);
        intent.putExtra(EXTRA_VIEWER_AVATAR, str6);
        intent.putExtra("vid", str2);
        intent.putExtra(EXTRA_TEMP_STORE_FILE_ID, str3);
        intent.putExtra(EXTRA_VIDEO_LIST_TYPE, pLVPlaybackListType);
        intent.putExtra(EXTRA_IS_LIVE, false);
        intent.addFlags(268435456);
        activity.startActivity(intent);
        return PLVLaunchResult.success();
    }

    private void observeLinkMicLayout() {
        if (!this.liveRoomDataManager.getConfig().isLive() || this.linkMicLayout == null) {
            return;
        }
        final PLVViewSwitcher pLVViewSwitcher = new PLVViewSwitcher();
        this.linkMicLayout.setLogoView(this.plvPlayerLogoView);
        this.linkMicLayout.setOnPLVLinkMicLayoutListener(new IPLVLCLinkMicLayout.OnPLVLinkMicLayoutListener() { // from class: com.easefun.polyv.livecloudclass.scenes.PLVLCCloudClassActivity.26
            @Override // com.easefun.polyv.livecloudclass.modules.linkmic.IPLVLCLinkMicLayout.OnPLVLinkMicLayoutListener
            public void onCancelRequestJoinLinkMic() {
                PLVLCCloudClassActivity.this.mediaLayout.updateWhenRequestJoinLinkMic(false);
            }

            @Override // com.easefun.polyv.livecloudclass.modules.linkmic.IPLVLCLinkMicLayout.OnPLVLinkMicLayoutListener
            public void onChangeTeacherLocation(PLVViewSwitcher pLVViewSwitcher2, PLVSwitchViewAnchorLayout pLVSwitchViewAnchorLayout) {
                pLVViewSwitcher2.registerSwitchView(pLVSwitchViewAnchorLayout, PLVLCCloudClassActivity.this.mediaLayout.getPlayerSwitchView());
                pLVViewSwitcher2.switchView();
                PLVLCCloudClassActivity.this.mediaLayout.getPlayerSwitchView().post(new Runnable() { // from class: com.easefun.polyv.livecloudclass.scenes.PLVLCCloudClassActivity.26.1
                    @Override // java.lang.Runnable
                    public void run() {
                        if (PLVLCCloudClassActivity.this.mediaLayout == null || PLVLCCloudClassActivity.this.mediaLayout.getPlayerSwitchView() == null) {
                            return;
                        }
                        PLVLCCloudClassActivity.this.mediaLayout.getPlayerSwitchView().requestLayout();
                    }
                });
            }

            @Override // com.easefun.polyv.livecloudclass.modules.linkmic.IPLVLCLinkMicLayout.OnPLVLinkMicLayoutListener
            public void onChannelLinkMicOpenStatusChanged(boolean z2) {
                PLVLCCloudClassActivity.this.mediaLayout.updateWhenLinkMicOpenStatusChanged(z2);
            }

            @Override // com.easefun.polyv.livecloudclass.modules.linkmic.IPLVLCLinkMicLayout.OnPLVLinkMicLayoutListener
            public void onClickSwitchWithMediaOnce(PLVSwitchViewAnchorLayout pLVSwitchViewAnchorLayout) {
                pLVViewSwitcher.registerSwitchView(pLVSwitchViewAnchorLayout, PLVLCCloudClassActivity.this.mediaLayout.getPlayerSwitchView());
                pLVViewSwitcher.switchView();
            }

            @Override // com.easefun.polyv.livecloudclass.modules.linkmic.IPLVLCLinkMicLayout.OnPLVLinkMicLayoutListener
            public void onClickSwitchWithMediaTwice(PLVSwitchViewAnchorLayout pLVSwitchViewAnchorLayout, PLVSwitchViewAnchorLayout pLVSwitchViewAnchorLayout2) {
                pLVViewSwitcher.registerSwitchView(pLVSwitchViewAnchorLayout, PLVLCCloudClassActivity.this.mediaLayout.getPlayerSwitchView());
                pLVViewSwitcher.switchView();
                pLVViewSwitcher.registerSwitchView(pLVSwitchViewAnchorLayout2, PLVLCCloudClassActivity.this.mediaLayout.getPlayerSwitchView());
                pLVViewSwitcher.switchView();
            }

            @Override // com.easefun.polyv.livecloudclass.modules.linkmic.IPLVLCLinkMicLayout.OnPLVLinkMicLayoutListener
            public void onJoinLinkMic() {
                PLVLCCloudClassActivity.this.mediaLayout.updateWhenJoinLinkMic();
            }

            @Override // com.easefun.polyv.livecloudclass.modules.linkmic.IPLVLCLinkMicLayout.OnPLVLinkMicLayoutListener
            public void onJoinRtcChannel() {
                if (PLVLCCloudClassActivity.this.liveRoomDataManager.getConfig().isPPTChannelType() && PLVLCCloudClassActivity.this.floatingPPTLayout.isPPTInFloatingLayout()) {
                    PLVLCCloudClassActivity.this.pptViewSwitcher.switchView();
                    PLVLCCloudClassActivity.this.linkMicLayout.notifySwitchedPptToMainScreenOnJoinChannel();
                }
                PLVLCCloudClassActivity.this.floatingPPTLayout.hide();
                PLVLCCloudClassActivity.this.floatingPPTLayout.getPPTView().notifyJoinRtcChannel();
                PLVLCCloudClassActivity.this.mediaLayout.updateWhenJoinRTC(PLVLCCloudClassActivity.this.linkMicLayout.getLandscapeWidth());
            }

            @Override // com.easefun.polyv.livecloudclass.modules.linkmic.IPLVLCLinkMicLayout.OnPLVLinkMicLayoutListener
            public void onLeaveLinkMic() {
                PLVLCCloudClassActivity.this.mediaLayout.updateWhenLeaveLinkMic();
            }

            @Override // com.easefun.polyv.livecloudclass.modules.linkmic.IPLVLCLinkMicLayout.OnPLVLinkMicLayoutListener
            public void onLeaveRtcChannel() {
                PLVLCCloudClassActivity.this.floatingPPTLayout.show();
                PLVLCCloudClassActivity.this.floatingPPTLayout.getPPTView().notifyLeaveRtcChannel();
                PLVLCCloudClassActivity.this.mediaLayout.updateWhenLeaveRTC();
            }

            @Override // com.easefun.polyv.livecloudclass.modules.linkmic.IPLVLCLinkMicLayout.OnPLVLinkMicLayoutListener
            public void onNetworkQuality(int i2) {
                PLVLCCloudClassActivity.this.mediaLayout.acceptNetworkQuality(i2);
            }

            @Override // com.easefun.polyv.livecloudclass.modules.linkmic.IPLVLCLinkMicLayout.OnPLVLinkMicLayoutListener
            public void onRTCPrepared() {
                PLVLCCloudClassActivity.this.mediaLayout.notifyRTCPrepared();
            }

            @Override // com.easefun.polyv.livecloudclass.modules.linkmic.IPLVLCLinkMicLayout.OnPLVLinkMicLayoutListener
            public void onRequestJoinLinkMic() {
                PLVLCCloudClassActivity.this.mediaLayout.updateWhenRequestJoinLinkMic(true);
            }

            @Override // com.easefun.polyv.livecloudclass.modules.linkmic.IPLVLCLinkMicLayout.OnPLVLinkMicLayoutListener
            public void onShowLandscapeRTCLayout(boolean z2) {
                if (z2) {
                    PLVLCCloudClassActivity.this.mediaLayout.setShowLandscapeRTCLayout();
                } else {
                    PLVLCCloudClassActivity.this.mediaLayout.setHideLandscapeRTCLayout();
                }
            }
        });
    }

    private void observeMediaLayout() {
        this.mediaLayout.setOnViewActionListener(new IPLVLCMediaLayout.OnViewActionListener() { // from class: com.easefun.polyv.livecloudclass.scenes.PLVLCCloudClassActivity.12
            @Override // com.easefun.polyv.livecloudclass.modules.media.IPLVLCMediaLayout.OnViewActionListener
            public boolean isRtcPausing() {
                return PLVLCCloudClassActivity.this.linkMicLayout.isPausing();
            }

            @Override // com.easefun.polyv.livecloudclass.modules.media.IPLVLCMediaLayout.OnViewActionListener
            public void onClickShowOrHideSubTab(boolean z2) {
                if (!PLVLCCloudClassActivity.this.liveRoomDataManager.getConfig().isLive()) {
                    if (z2) {
                        PLVLCCloudClassActivity.this.floatingPPTLayout.show();
                        return;
                    } else {
                        PLVLCCloudClassActivity.this.floatingPPTLayout.hide();
                        return;
                    }
                }
                if (PLVLCCloudClassActivity.this.linkMicLayout == null) {
                    return;
                }
                if (PLVLCCloudClassActivity.this.linkMicLayout.isJoinChannel()) {
                    if (z2) {
                        PLVLCCloudClassActivity.this.linkMicLayout.showAll();
                        return;
                    } else {
                        PLVLCCloudClassActivity.this.linkMicLayout.hideLinkMicList();
                        return;
                    }
                }
                if (z2) {
                    PLVLCCloudClassActivity.this.floatingPPTLayout.show();
                } else {
                    PLVLCCloudClassActivity.this.floatingPPTLayout.hide();
                }
            }

            @Override // com.easefun.polyv.livecloudclass.modules.media.IPLVLCMediaLayout.OnViewActionListener
            public void onPPTTurnPage(String str) {
                if (PLVLCCloudClassActivity.this.floatingPPTLayout == null || PLVLCCloudClassActivity.this.floatingPPTLayout.getPPTView() == null) {
                    return;
                }
                PLVLCCloudClassActivity.this.floatingPPTLayout.getPPTView().turnPagePPT(str);
            }

            @Override // com.easefun.polyv.livecloudclass.modules.media.IPLVLCMediaLayout.OnViewActionListener
            public void onRtcPauseResume(boolean z2) {
                if (PLVLCCloudClassActivity.this.linkMicLayout == null) {
                    return;
                }
                if (z2) {
                    PLVLCCloudClassActivity.this.linkMicLayout.pause();
                } else {
                    PLVLCCloudClassActivity.this.linkMicLayout.resume();
                }
            }

            @Override // com.easefun.polyv.livecloudclass.modules.media.IPLVLCMediaLayout.OnViewActionListener
            public Pair<Boolean, Integer> onSendChatMessageAction(String str) {
                return PLVLCCloudClassActivity.this.livePageMenuLayout.getChatroomPresenter().sendChatMessage(new PolyvLocalMessage(str));
            }

            @Override // com.easefun.polyv.livecloudclass.modules.media.IPLVLCMediaLayout.OnViewActionListener
            public void onSendLikesAction() {
                PLVLCCloudClassActivity.this.livePageMenuLayout.getChatroomPresenter().sendLikeMessage();
            }

            @Override // com.easefun.polyv.livecloudclass.modules.media.IPLVLCMediaLayout.OnViewActionListener
            public void onShowBulletinAction() {
                if (!PLVLCCloudClassActivity.this.liveRoomDataManager.getConfig().isLive() || PLVLCCloudClassActivity.this.popoverLayout == null) {
                    return;
                }
                PLVLCCloudClassActivity.this.popoverLayout.getInteractLayout().showBulletin();
            }

            @Override // com.easefun.polyv.livecloudclass.modules.media.IPLVLCMediaLayout.OnViewActionListener
            public void onShowMediaController(boolean z2) {
                if (!PLVLCCloudClassActivity.this.liveRoomDataManager.getConfig().isLive() || PLVLCCloudClassActivity.this.linkMicLayout == null) {
                    return;
                }
                if (z2) {
                    PLVLCCloudClassActivity.this.linkMicLayout.showControlBar();
                } else {
                    PLVLCCloudClassActivity.this.linkMicLayout.hideControlBar();
                }
            }

            @Override // com.easefun.polyv.livecloudclass.modules.media.IPLVLCMediaLayout.OnViewActionListener
            public void onShowRewardAction() throws Resources.NotFoundException {
                if (!PLVLCCloudClassActivity.this.liveRoomDataManager.getConfig().isLive() || PLVLCCloudClassActivity.this.popoverLayout == null) {
                    return;
                }
                PLVLCCloudClassActivity.this.popoverLayout.getRewardView().showPointRewardDialog(true);
            }

            @Override // com.easefun.polyv.livecloudclass.modules.media.IPLVLCMediaLayout.OnViewActionListener
            public void onWatchLowLatency(boolean z2) {
                PLVLCCloudClassActivity.this.floatingPPTLayout.setIsLowLatencyWatch(z2);
                if (PLVLCCloudClassActivity.this.linkMicLayout != null) {
                    PLVLCCloudClassActivity.this.linkMicLayout.setWatchLowLatency(z2);
                }
            }
        });
        this.mediaLayout.addOnPPTShowStateListener(new IPLVOnDataChangedListener<Boolean>() { // from class: com.easefun.polyv.livecloudclass.scenes.PLVLCCloudClassActivity.13
            @Override // androidx.lifecycle.Observer
            public void onChanged(@Nullable Boolean bool) {
                if (bool == null) {
                    return;
                }
                PLVLCCloudClassActivity.this.floatingPPTLayout.setServerEnablePPT(bool.booleanValue());
            }
        });
        this.mediaLayout.addOnPlayerStateListener(new IPLVOnDataChangedListener<PLVPlayerState>() { // from class: com.easefun.polyv.livecloudclass.scenes.PLVLCCloudClassActivity.14
            @Override // androidx.lifecycle.Observer
            public void onChanged(@Nullable PLVPlayerState pLVPlayerState) {
                if (pLVPlayerState == null) {
                    return;
                }
                if (!PLVLCCloudClassActivity.this.liveRoomDataManager.getConfig().isLive()) {
                    int i2 = AnonymousClass27.$SwitchMap$com$easefun$polyv$livecommon$module$modules$player$PLVPlayerState[pLVPlayerState.ordinal()];
                    if (i2 == 1) {
                        PLVLCCloudClassActivity.this.floatingPPTLayout.show();
                        PLVLCCloudClassActivity.this.livePageMenuLayout.onPlaybackVideoPrepared(PLVLCCloudClassActivity.this.mediaLayout.getSessionId(), PLVLCCloudClassActivity.this.liveRoomDataManager.getConfig().getChannelId());
                        return;
                    } else {
                        if (i2 != 5) {
                            return;
                        }
                        PLVLCCloudClassActivity.this.floatingPPTLayout.hide();
                        return;
                    }
                }
                int i3 = AnonymousClass27.$SwitchMap$com$easefun$polyv$livecommon$module$modules$player$PLVPlayerState[pLVPlayerState.ordinal()];
                if (i3 == 1) {
                    PLVLCCloudClassActivity.this.floatingPPTLayout.show();
                    PLVLCCloudClassActivity.this.livePageMenuLayout.updateLiveStatus(PLVLiveStateEnum.LIVE);
                    if (PLVLCCloudClassActivity.this.linkMicLayout != null) {
                        PLVLCCloudClassActivity.this.linkMicLayout.showAll();
                        return;
                    }
                    return;
                }
                if (i3 == 2) {
                    if (PLVLCCloudClassActivity.this.liveRoomDataManager.getConfig().isPPTChannelType() && !PLVLCCloudClassActivity.this.floatingPPTLayout.isPPTInFloatingLayout()) {
                        PLVLCCloudClassActivity.this.pptViewSwitcher.switchView();
                    }
                    PLVLCCloudClassActivity.this.floatingPPTLayout.hide();
                    PLVLCCloudClassActivity.this.livePageMenuLayout.updateLiveStatus(PLVLiveStateEnum.STOP);
                    if (PLVLCCloudClassActivity.this.linkMicLayout != null) {
                        PLVLCCloudClassActivity.this.linkMicLayout.setLiveEnd();
                        PLVLCCloudClassActivity.this.linkMicLayout.hideAll();
                        return;
                    }
                    return;
                }
                if (i3 == 3 || i3 == 4) {
                    if (PLVLCCloudClassActivity.this.liveRoomDataManager.getConfig().isPPTChannelType() && !PLVLCCloudClassActivity.this.floatingPPTLayout.isPPTInFloatingLayout()) {
                        PLVLCCloudClassActivity.this.pptViewSwitcher.switchView();
                    }
                    PLVLCCloudClassActivity.this.floatingPPTLayout.hide();
                    PLVLCCloudClassActivity.this.livePageMenuLayout.updateLiveStatus(PLVLiveStateEnum.END);
                    if (PLVLCCloudClassActivity.this.linkMicLayout != null) {
                        PLVLCCloudClassActivity.this.linkMicLayout.setLiveEnd();
                        PLVLCCloudClassActivity.this.linkMicLayout.hideAll();
                    }
                }
            }
        });
        if (!this.liveRoomDataManager.getConfig().isLive()) {
            this.mediaLayout.addOnPlayInfoVOListener(new IPLVOnDataChangedListener<PLVPlayInfoVO>() { // from class: com.easefun.polyv.livecloudclass.scenes.PLVLCCloudClassActivity.18
                @Override // androidx.lifecycle.Observer
                public void onChanged(@Nullable PLVPlayInfoVO pLVPlayInfoVO) {
                    if (pLVPlayInfoVO == null) {
                        return;
                    }
                    PLVLCCloudClassActivity.this.floatingPPTLayout.getPPTView().setPlaybackCurrentPosition(pLVPlayInfoVO.getPosition());
                    if (PLVLCCloudClassActivity.this.livePageMenuLayout.getPreviousPresenter() != null) {
                        PLVLCCloudClassActivity.this.livePageMenuLayout.getPreviousPresenter().updatePlaybackCurrentPosition(pLVPlayInfoVO);
                    }
                }
            });
            this.mediaLayout.addOnSeekCompleteListener(new IPLVOnDataChangedListener<Integer>() { // from class: com.easefun.polyv.livecloudclass.scenes.PLVLCCloudClassActivity.19
                @Override // androidx.lifecycle.Observer
                public void onChanged(@Nullable Integer num) {
                    if (num == null) {
                        return;
                    }
                    PLVLCCloudClassActivity.this.livePageMenuLayout.onPlaybackVideoSeekComplete(num.intValue());
                }
            });
        } else {
            this.mediaLayout.addOnLinkMicStateListener(new IPLVOnDataChangedListener<Pair<Boolean, Boolean>>() { // from class: com.easefun.polyv.livecloudclass.scenes.PLVLCCloudClassActivity.15
                @Override // androidx.lifecycle.Observer
                public void onChanged(@Nullable Pair<Boolean, Boolean> pair) {
                    if (pair == null) {
                        return;
                    }
                    boolean zBooleanValue = ((Boolean) pair.first).booleanValue();
                    boolean zBooleanValue2 = ((Boolean) pair.second).booleanValue();
                    if (PLVLCCloudClassActivity.this.linkMicLayout == null) {
                        return;
                    }
                    PLVLCCloudClassActivity.this.linkMicLayout.setIsTeacherOpenLinkMic(zBooleanValue);
                    PLVLCCloudClassActivity.this.linkMicLayout.setIsAudio(zBooleanValue2);
                }
            });
            this.mediaLayout.addOnSeiDataListener(new IPLVOnDataChangedListener<Long>() { // from class: com.easefun.polyv.livecloudclass.scenes.PLVLCCloudClassActivity.16
                @Override // androidx.lifecycle.Observer
                public void onChanged(@Nullable Long l2) {
                    if (l2 == null) {
                        return;
                    }
                    PLVLCCloudClassActivity.this.floatingPPTLayout.getPPTView().sendSEIData(l2.longValue());
                }
            });
            this.mediaLayout.setOnRTCPlayEventListener(new IPLVLiveListenerEvent.OnRTCPlayEventListener() { // from class: com.easefun.polyv.livecloudclass.scenes.PLVLCCloudClassActivity.17
                @Override // com.plv.livescenes.video.api.IPLVLiveListenerEvent.OnRTCPlayEventListener
                public void onRTCLiveEnd() {
                    if (PLVLCCloudClassActivity.this.linkMicLayout != null) {
                        PLVLCCloudClassActivity.this.linkMicLayout.setLiveEnd();
                    }
                }

                @Override // com.plv.livescenes.video.api.IPLVLiveListenerEvent.OnRTCPlayEventListener
                public void onRTCLiveStart() {
                    if (PLVLCCloudClassActivity.this.linkMicLayout != null) {
                        PLVLCCloudClassActivity.this.linkMicLayout.setLiveStart();
                    }
                }
            });
        }
    }

    private void observePPTView() {
        this.floatingPPTLayout.setOnFloatingViewClickListener(new View.OnClickListener() { // from class: com.easefun.polyv.livecloudclass.scenes.PLVLCCloudClassActivity.22
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                PLVLCCloudClassActivity.this.pptViewSwitcher.switchView();
                PLVLCCloudClassActivity.this.initPptTurnPageLandLayout();
            }
        });
        this.floatingPPTLayout.setOnClickCloseListener(new IPLVLCFloatingPPTLayout.IPLVOnClickCloseFloatingView() { // from class: com.easefun.polyv.livecloudclass.scenes.PLVLCCloudClassActivity.23
            @Override // com.easefun.polyv.livecloudclass.modules.ppt.IPLVLCFloatingPPTLayout.IPLVOnClickCloseFloatingView
            public void onClickCloseFloatingView() {
                PLVLCCloudClassActivity.this.mediaLayout.updateOnClickCloseFloatingView();
            }
        });
        if (this.liveRoomDataManager.getConfig().isLive()) {
            this.floatingPPTLayout.getPPTView().initLivePPT(new IPLVLCPPTView.OnPLVLCLivePPTViewListener() { // from class: com.easefun.polyv.livecloudclass.scenes.PLVLCCloudClassActivity.24
                @Override // com.easefun.polyv.livecloudclass.modules.ppt.IPLVLCPPTView.OnPLVLCLivePPTViewListener
                public void onLiveBackTopActivity() {
                    if (ScreenUtils.isLandscape()) {
                        PLVOrientationManager.getInstance().setPortrait(PLVLCCloudClassActivity.this);
                    } else {
                        PLVLCCloudClassActivity.this.finish();
                    }
                }

                @Override // com.easefun.polyv.livecloudclass.modules.ppt.IPLVLCPPTView.OnPLVLCLivePPTViewListener
                public void onLiveChangeToLandscape(boolean z2) {
                    if (z2) {
                        PLVOrientationManager.getInstance().setLandscape(PLVLCCloudClassActivity.this);
                    } else {
                        PLVOrientationManager.getInstance().setPortrait(PLVLCCloudClassActivity.this);
                    }
                }

                @Override // com.easefun.polyv.livecloudclass.modules.ppt.IPLVLCPPTView.OnPLVLCLivePPTViewListener
                public void onLivePPTStatusChange(PLVPPTStatus pLVPPTStatus) {
                    if (PLVLCCloudClassActivity.this.mediaLayout != null) {
                        PLVLCCloudClassActivity.this.mediaLayout.updatePPTStatusChange(pLVPPTStatus);
                    }
                }

                @Override // com.easefun.polyv.livecloudclass.modules.ppt.IPLVLCPPTView.OnPLVLCLivePPTViewListener
                public void onLiveRestartVideoView() {
                    PLVLCCloudClassActivity.this.mediaLayout.startPlay();
                }

                @Override // com.easefun.polyv.livecloudclass.modules.ppt.IPLVLCPPTView.OnPLVLCLivePPTViewListener
                public void onLiveStartOrPauseVideoView(boolean z2) {
                    if (z2) {
                        PLVLCCloudClassActivity.this.mediaLayout.startPlay();
                    } else {
                        PLVLCCloudClassActivity.this.mediaLayout.stop();
                    }
                }

                @Override // com.easefun.polyv.livecloudclass.modules.ppt.IPLVLCPPTView.OnPLVLCLivePPTViewListener
                public void onLiveSwitchPPTViewLocation(boolean z2) {
                    if (PLVLCCloudClassActivity.this.liveRoomDataManager.getConfig().isPPTChannelType()) {
                        PLVLCCloudClassActivity.this.mediaLayout.onTurnPageLayoutChange(z2);
                        if (PLVLCCloudClassActivity.this.linkMicLayout == null || !PLVLCCloudClassActivity.this.linkMicLayout.isJoinChannel()) {
                            if (z2) {
                                if (PLVLCCloudClassActivity.this.pptViewSwitcher.isViewSwitched()) {
                                    return;
                                }
                                PLVLCCloudClassActivity.this.pptViewSwitcher.switchView();
                            } else if (PLVLCCloudClassActivity.this.pptViewSwitcher.isViewSwitched()) {
                                PLVLCCloudClassActivity.this.pptViewSwitcher.switchView();
                            }
                        }
                    }
                }
            });
        } else {
            this.floatingPPTLayout.getPPTView().initPlaybackPPT(new IPLVLCPPTView.OnPLVLCPlaybackPPTViewListener() { // from class: com.easefun.polyv.livecloudclass.scenes.PLVLCCloudClassActivity.25
                @Override // com.easefun.polyv.livecloudclass.modules.ppt.IPLVLCPPTView.OnPLVLCPlaybackPPTViewListener
                public void onPlaybackSwitchPPTViewLocation(boolean z2) {
                    PLVLCCloudClassActivity.this.pptViewSwitcher.switchView();
                }
            });
        }
    }

    private void observePageMenuLayout() {
        this.livePageMenuLayout.setOnViewActionListener(new IPLVLCLivePageMenuLayout.OnViewActionListener() { // from class: com.easefun.polyv.livecloudclass.scenes.PLVLCCloudClassActivity.20
            @Override // com.easefun.polyv.livecloudclass.modules.pagemenu.IPLVLCLivePageMenuLayout.OnViewActionListener
            public int getVideoCurrentPosition() {
                return PLVLCCloudClassActivity.this.mediaLayout.getVideoCurrentPosition();
            }

            @Override // com.easefun.polyv.livecloudclass.modules.pagemenu.IPLVLCLivePageMenuLayout.OnViewActionListener
            public void onAddedChatTab(boolean z2) {
                if (PLVLCCloudClassActivity.this.chatLandscapeLayout != null) {
                    PLVLCCloudClassActivity.this.chatLandscapeLayout.setIsChatPlaybackLayout(z2);
                    PLVLCCloudClassActivity.this.livePageMenuLayout.getChatPlaybackManager().addOnCallDataListener(PLVLCCloudClassActivity.this.chatLandscapeLayout.getChatPlaybackDataListener());
                    PLVLCCloudClassActivity.this.livePageMenuLayout.getChatroomPresenter().registerView(PLVLCCloudClassActivity.this.chatLandscapeLayout.getChatroomView());
                }
                if (PLVLCCloudClassActivity.this.mediaLayout != null) {
                    PLVLCCloudClassActivity.this.mediaLayout.setChatPlaybackEnabled(z2);
                }
            }

            @Override // com.easefun.polyv.livecloudclass.modules.pagemenu.IPLVLCLivePageMenuLayout.OnViewActionListener
            public void onChangeVideoVidAction(String str) {
                PLVLCCloudClassActivity.this.mediaLayout.updatePlayBackVideVidAndPlay(str);
            }

            @Override // com.easefun.polyv.livecloudclass.modules.pagemenu.IPLVLCLivePageMenuLayout.OnViewActionListener
            public void onClickChatMoreDynamicFunction(String str) {
                if (PLVLCCloudClassActivity.this.popoverLayout != null) {
                    PLVLCCloudClassActivity.this.popoverLayout.getInteractLayout().onCallDynamicFunction(str);
                }
            }

            @Override // com.easefun.polyv.livecloudclass.modules.pagemenu.IPLVLCLivePageMenuLayout.OnViewActionListener
            public void onSeekToAction(int i2) {
                PLVLCCloudClassActivity.this.mediaLayout.seekTo(i2 * 1000, PLVLCCloudClassActivity.this.mediaLayout.getDuration());
            }

            @Override // com.easefun.polyv.livecloudclass.modules.pagemenu.IPLVLCLivePageMenuLayout.OnViewActionListener
            public void onSendDanmuAction(CharSequence charSequence) {
                PLVLCCloudClassActivity.this.mediaLayout.sendDanmaku(charSequence);
            }

            @Override // com.easefun.polyv.livecloudclass.modules.pagemenu.IPLVLCLivePageMenuLayout.OnViewActionListener
            public void onShowBulletinAction() {
                if (PLVLCCloudClassActivity.this.popoverLayout != null) {
                    PLVLCCloudClassActivity.this.popoverLayout.getInteractLayout().showBulletin();
                }
            }

            @Override // com.easefun.polyv.livecloudclass.modules.pagemenu.IPLVLCLivePageMenuLayout.OnViewActionListener
            public void onShowEffectAction(boolean z2) {
                if (PLVLCCloudClassActivity.this.mediaLayout != null) {
                    PLVLCCloudClassActivity.this.mediaLayout.setLandscapeRewardEffectVisibility(z2);
                }
            }

            @Override // com.easefun.polyv.livecloudclass.modules.pagemenu.IPLVLCLivePageMenuLayout.OnViewActionListener
            public void onShowRewardAction() throws Resources.NotFoundException {
                if (PLVLCCloudClassActivity.this.popoverLayout != null) {
                    PLVLCCloudClassActivity.this.popoverLayout.getRewardView().showPointRewardDialog(true);
                }
            }
        });
        this.livePageMenuLayout.addOnViewerCountListener(new IPLVOnDataChangedListener<Long>() { // from class: com.easefun.polyv.livecloudclass.scenes.PLVLCCloudClassActivity.21
            @Override // androidx.lifecycle.Observer
            public void onChanged(@Nullable Long l2) {
                if (l2 == null) {
                    return;
                }
                PLVLCCloudClassActivity.this.mediaLayout.updateViewerCount(l2.longValue());
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showHideHandout(final View view, final boolean z2) {
        view.animate().translationY(z2 ? 0 : SizeUtils.dp2px(594.0f)).setDuration(300L).setListener(new Animator.AnimatorListener() { // from class: com.easefun.polyv.livecloudclass.scenes.PLVLCCloudClassActivity.10
            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                if (z2) {
                    if (PLVLCCloudClassActivity.this.handoutLocation[0] == 0 || PLVLCCloudClassActivity.this.handoutLocation[1] == 0) {
                        view.getLocationOnScreen(PLVLCCloudClassActivity.this.handoutLocation);
                    }
                }
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationRepeat(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
            }
        }).setInterpolator(new AccelerateDecelerateInterpolator()).start();
        this.showHandout = z2;
    }

    private void startPlaybackOnHasRecordFile() {
        this.liveRoomDataManager.getClassDetailVO().observe(this, new Observer<PLVStatefulData<PolyvLiveClassDetailVO>>() { // from class: com.easefun.polyv.livecloudclass.scenes.PLVLCCloudClassActivity.11
            @Override // androidx.lifecycle.Observer
            public void onChanged(@Nullable PLVStatefulData<PolyvLiveClassDetailVO> pLVStatefulData) {
                if (pLVStatefulData == null || !pLVStatefulData.isSuccess() || pLVStatefulData.getData() == null || pLVStatefulData.getData().getData() == null) {
                    return;
                }
                PLVLCCloudClassActivity.this.liveRoomDataManager.getClassDetailVO().removeObserver(this);
                PolyvLiveClassDetailVO data = pLVStatefulData.getData();
                if (data.getData().isPlaybackEnabled() && data.getData().getRecordFileSimpleModel() != null) {
                    PLVLCCloudClassActivity.this.mediaLayout.startPlay();
                }
            }
        });
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        if (!this.showHandout || motionEvent.getY() >= this.handoutLocation[1]) {
            return super.dispatchTouchEvent(motionEvent);
        }
        showHideHandout(findViewById(R.id.ll_root_handout), false);
        return true;
    }

    @Override // com.easefun.polyv.livecommon.ui.window.PLVBaseActivity
    public boolean enableRotationObserver() {
        return true;
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        IPLVPopoverLayout iPLVPopoverLayout = this.popoverLayout;
        if (iPLVPopoverLayout == null || !iPLVPopoverLayout.onBackPress()) {
            IPLVLCMediaLayout iPLVLCMediaLayout = this.mediaLayout;
            if (iPLVLCMediaLayout == null || !iPLVLCMediaLayout.onBackPressed()) {
                IPLVLCLivePageMenuLayout iPLVLCLivePageMenuLayout = this.livePageMenuLayout;
                if (iPLVLCLivePageMenuLayout == null || !iPLVLCLivePageMenuLayout.onBackPressed()) {
                    PLVDialogFactory.createConfirmDialog(this, getResources().getString(this.liveRoomDataManager.getConfig().isLive() ? R.string.plv_live_room_dialog_exit_confirm_ask : R.string.plv_playback_room_dialog_exit_confirm_ask), getResources().getString(R.string.plv_common_dialog_exit), new DialogInterface.OnClickListener() { // from class: com.easefun.polyv.livecloudclass.scenes.PLVLCCloudClassActivity.2
                        @Override // android.content.DialogInterface.OnClickListener
                        public void onClick(DialogInterface dialogInterface, int i2) {
                            dialogInterface.dismiss();
                            LocalBroadcastManager.getInstance(PLVLCCloudClassActivity.this).sendBroadcast(new Intent().setAction("QUIT_LIVE"));
                            PLVLCCloudClassActivity.super.onBackPressed();
                        }
                    }).show();
                }
            }
        }
    }

    @Override // com.easefun.polyv.livecommon.ui.window.PLVBaseActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        if (configuration.orientation != 2) {
            PLVScreenUtils.enterPortrait(this);
            if (this.hasHandout) {
                findViewById(R.id.ll_handout).setVisibility(0);
            }
            getWindow().setSoftInputMode(18);
            return;
        }
        PLVScreenUtils.enterLandscape(this);
        getWindow().setSoftInputMode(32);
        if (this.hasHandout) {
            findViewById(R.id.ll_handout).setVisibility(8);
            if (this.showHandout) {
                showHideHandout(findViewById(R.id.ll_root_handout), false);
            }
        }
    }

    @Override // com.easefun.polyv.livecommon.ui.window.PLVBaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        injectDependency();
        setContentView(R.layout.plvlc_cloudclass_activity);
        initParams();
        initLiveRoomManager();
        initView();
        initPptTurnPageLandLayout();
        observeMediaLayout();
        observeLinkMicLayout();
        observePageMenuLayout();
        observePPTView();
    }

    @Override // com.easefun.polyv.livecommon.ui.window.PLVBaseActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        PLVFloatingPlayerManager.getInstance().runOnFloatingWindowClosed(new Runnable() { // from class: com.easefun.polyv.livecloudclass.scenes.PLVLCCloudClassActivity.1
            @Override // java.lang.Runnable
            public void run() {
                PLVFloatingPlayerManager.getInstance().clear();
                if (PLVLCCloudClassActivity.this.mediaLayout != null) {
                    PLVLCCloudClassActivity.this.mediaLayout.destroy();
                }
                if (PLVLCCloudClassActivity.this.linkMicLayout != null) {
                    PLVLCCloudClassActivity.this.linkMicLayout.destroy();
                }
                if (PLVLCCloudClassActivity.this.livePageMenuLayout != null) {
                    PLVLCCloudClassActivity.this.livePageMenuLayout.destroy();
                }
                if (PLVLCCloudClassActivity.this.popoverLayout != null) {
                    PLVLCCloudClassActivity.this.popoverLayout.destroy();
                }
                if (PLVLCCloudClassActivity.this.floatingPPTLayout != null) {
                    PLVLCCloudClassActivity.this.floatingPPTLayout.destroy();
                }
                if (PLVLCCloudClassActivity.this.popoverLayout != null) {
                    PLVLCCloudClassActivity.this.popoverLayout.destroy();
                }
                if (PLVLCCloudClassActivity.this.liveRoomDataManager != null) {
                    PLVLCCloudClassActivity.this.liveRoomDataManager.destroy();
                }
            }
        });
    }

    @NonNull
    public static PLVLaunchResult launchLive(@NonNull Activity activity, @NonNull String str, @NonNull PLVLiveChannelType pLVLiveChannelType, @NonNull String str2, @NonNull String str3, @NonNull String str4, String str5) {
        if (activity == null) {
            return PLVLaunchResult.error("activity 为空，启动云课堂直播页失败！");
        }
        if (TextUtils.isEmpty(str)) {
            return PLVLaunchResult.error("channelId 为空，启动云课堂直播页失败！");
        }
        if (pLVLiveChannelType == null) {
            return PLVLaunchResult.error("channelType 为空，启动云课堂直播页失败！");
        }
        if (TextUtils.isEmpty(str2)) {
            return PLVLaunchResult.error("viewerId 为空，启动云课堂直播页失败！");
        }
        if (TextUtils.isEmpty(str3)) {
            return PLVLaunchResult.error("viewerName 为空，启动云课堂直播页失败！");
        }
        Intent intent = new Intent(activity, (Class<?>) PLVLCCloudClassActivity.class);
        intent.putExtra("channelId", str);
        intent.putExtra(EXTRA_CHANNEL_TYPE, pLVLiveChannelType);
        intent.putExtra("viewerId", str2);
        intent.putExtra(EXTRA_VIEWER_NAME, str3);
        intent.putExtra(EXTRA_VIEWER_AVATAR, str4);
        intent.putExtra(EXTRA_IS_LIVE, true);
        intent.putExtra(EXTRA_HANDOUT_LIST, str5);
        intent.addFlags(268435456);
        activity.startActivity(intent);
        return PLVLaunchResult.success();
    }
}
